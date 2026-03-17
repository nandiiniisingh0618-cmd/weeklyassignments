import java.util.*;

public class MultiLevelCache {

    // L1 Cache (LRU)
    private LinkedHashMap<String, String> l1Cache =
            new LinkedHashMap<>(10000, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                    return size() > 5; // reduced for demo (instead of 10000)
                }
            };

    // Tracks global access frequency
    private Map<String, Integer> accessCounts = new HashMap<>();

    public String getVideo(String videoId) {

        // L1 Cache check
        if (l1Cache.containsKey(videoId)) {
            return "L1 Cache HIT for " + videoId;
        }

        // Simulate L2/L3 fetch
        accessCounts.put(videoId, accessCounts.getOrDefault(videoId, 0) + 1);

        // Promotion logic
        if (accessCounts.get(videoId) > 3) { // reduced threshold for demo
            l1Cache.put(videoId, "Video_Data_Payload");
            return "L2 HIT -> Promoted to L1 for " + videoId;
        }

        return "L3 Database HIT for " + videoId;
    }

    // Debug method
    public void printL1Cache() {
        System.out.println("\n--- L1 Cache Contents ---");
        for (String key : l1Cache.keySet()) {
            System.out.println(key);
        }
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();

        String[] requests = {
                "video1", "video2", "video1", "video1",
                "video2", "video2", "video2",
                "video3", "video3", "video3", "video3",
                "video1", "video4", "video5", "video6"
        };

        for (String video : requests) {
            String result = cache.getVideo(video);
            System.out.println(result);
        }

        cache.printL1Cache();

        // Access again to show L1 hits
        System.out.println("\n--- Re-accessing videos ---");
        System.out.println(cache.getVideo("video1"));
        System.out.println(cache.getVideo("video2"));
        System.out.println(cache.getVideo("video3"));
    }
}