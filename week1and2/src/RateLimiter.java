import java.util.concurrent.*;

public class RateLimiter {

    static class TokenBucket {
        int tokens = 5; // reduced for demo (instead of 1000)
        long lastRefillTime = System.currentTimeMillis();
    }

    private ConcurrentHashMap<String, TokenBucket> clients = new ConcurrentHashMap<>();

    public synchronized boolean checkRateLimit(String clientId) {
        TokenBucket bucket = clients.computeIfAbsent(clientId, k -> new TokenBucket());
        long now = System.currentTimeMillis();

        // Refill tokens every 5 seconds (demo instead of 1 hour)
        if (now - bucket.lastRefillTime > 5000) {
            bucket.tokens = 5;
            bucket.lastRefillTime = now;
            System.out.println("Refilled tokens for " + clientId);
        }

        if (bucket.tokens > 0) {
            bucket.tokens--;
            return true; // Allowed
        }
        return false; // Denied
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) throws InterruptedException {
        RateLimiter limiter = new RateLimiter();
        String clientId = "client-1";

        System.out.println("Sending requests...\n");

        // Simulate 10 rapid requests
        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.checkRateLimit(clientId);
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "DENIED"));
            Thread.sleep(500); // small delay
        }

        // Wait for refill
        System.out.println("\nWaiting for refill...\n");
        Thread.sleep(6000);

        // After refill
        for (int i = 11; i <= 15; i++) {
            boolean allowed = limiter.checkRateLimit(clientId);
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "DENIED"));
        }

        // Multi-client simulation
        System.out.println("\n--- Multi-client test ---");
        String[] clients = {"A", "B"};

        for (String c : clients) {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Client " + c + " -> " +
                        (limiter.checkRateLimit(c) ? "ALLOWED" : "DENIED"));
            }
        }
    }
}