import java.util.*;

public class DNSCache extends LinkedHashMap<String, DNSCache.DNSEntry> {

    static class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, long ttlSeconds) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }
    }

    private int capacity;

    public DNSCache(int capacity) {
        super(capacity, 0.75f, true); // access-order = true (LRU)
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
        return size() > capacity;
    }

    public String resolve(String domain) {
        DNSEntry entry = get(domain);
        if (entry != null && System.currentTimeMillis() < entry.expiryTime) {
            return entry.ip; // Cache HIT
        }
        remove(domain); // expired or not found
        return null; // Cache MISS
    }

    // Helper method to simulate DNS fetch + insert into cache
    public String fetchAndCache(String domain, String ip, long ttlSeconds) {
        DNSEntry entry = new DNSEntry(ip, ttlSeconds);
        put(domain, entry);
        return ip;
    }

    // Debug print
    public void printCache() {
        System.out.println("\nCurrent Cache:");
        for (Map.Entry<String, DNSEntry> e : entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue().ip);
        }
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) throws InterruptedException {
        DNSCache cache = new DNSCache(3);

        // Initial population
        cache.fetchAndCache("google.com", "142.250.190.78", 2); // TTL 2 sec
        cache.fetchAndCache("openai.com", "104.18.12.123", 5);
        cache.fetchAndCache("github.com", "140.82.114.3", 5);

        cache.printCache();

        // Cache HIT
        System.out.println("\nResolving google.com: " + cache.resolve("google.com"));

        // Wait for expiry
        Thread.sleep(2500);

        // Expired entry
        System.out.println("Resolving google.com after expiry: " + cache.resolve("google.com"));

        // Add new entry (triggers LRU eviction if needed)
        cache.fetchAndCache("stackoverflow.com", "151.101.1.69", 5);

        cache.printCache();

        // Access openai.com to make it recently used
        System.out.println("\nResolving openai.com: " + cache.resolve("openai.com"));

        // Add another entry → should evict least recently used
        cache.fetchAndCache("reddit.com", "151.101.65.140", 5);

        cache.printCache();
    }
}