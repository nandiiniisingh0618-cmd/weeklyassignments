import java.util.concurrent.*;
import java.util.*;

public class AnalyticsDashboard {
    private ConcurrentHashMap<String, Integer> pageViews = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Set<String>> uniqueVisitors = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> trafficSources = new ConcurrentHashMap<>();

    public void processEvent(String url, String userId, String source) {
        // Atomic increments
        pageViews.merge(url, 1, Integer::sum);
        trafficSources.merge(source, 1, Integer::sum);

        // Thread-safe set
        uniqueVisitors
                .computeIfAbsent(url, k -> ConcurrentHashMap.newKeySet())
                .add(userId);
    }

    public int getUniqueVisitorCount(String url) {
        Set<String> visitors = uniqueVisitors.get(url);
        return visitors != null ? visitors.size() : 0;
    }

    // Helper methods
    public int getPageViews(String url) {
        return pageViews.getOrDefault(url, 0);
    }

    public void printStats() {
        System.out.println("\n--- DASHBOARD STATS ---");

        System.out.println("\nPage Views:");
        for (String url : pageViews.keySet()) {
            System.out.println(url + " -> " + pageViews.get(url));
        }

        System.out.println("\nUnique Visitors:");
        for (String url : uniqueVisitors.keySet()) {
            System.out.println(url + " -> " + uniqueVisitors.get(url).size());
        }

        System.out.println("\nTraffic Sources:");
        for (String src : trafficSources.keySet()) {
            System.out.println(src + " -> " + trafficSources.get(src));
        }
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) throws InterruptedException {
        AnalyticsDashboard dashboard = new AnalyticsDashboard();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        String[] urls = {"/home", "/product", "/cart"};
        String[] users = {"U1", "U2", "U3", "U4", "U5"};
        String[] sources = {"Google", "Direct", "Facebook"};

        Random random = new Random();

        // Simulate 20 concurrent events
        for (int i = 0; i < 20; i++) {
            executor.submit(() -> {
                String url = urls[random.nextInt(urls.length)];
                String user = users[random.nextInt(users.length)];
                String source = sources[random.nextInt(sources.length)];

                dashboard.processEvent(url, user, source);

                System.out.println(Thread.currentThread().getName()
                        + " -> Event: " + url + ", " + user + ", " + source);
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Print results
        dashboard.printStats();
    }
}
