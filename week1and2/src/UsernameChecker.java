import java.util.*;

public class UsernameChecker {
    private Map<String, String> users = new HashMap<>(); // username -> userId
    private Map<String, Integer> attempts = new HashMap<>(); // username -> frequency

    public boolean checkAvailability(String username) {
        if (users.containsKey(username)) {
            attempts.put(username, attempts.getOrDefault(username, 0) + 1);
            return false;
        }
        return true;
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int counter = 1;
        while (suggestions.size() < 3) {
            String alt = username + counter;
            if (!users.containsKey(alt)) {
                suggestions.add(alt);
            }
            counter++;
        }
        return suggestions;
    }

    public String getMostAttempted() {
        return attempts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // 🔹 MAIN METHOD (Entry point)
    public static void main(String[] args) {
        UsernameChecker checker = new UsernameChecker();

        // Simulate existing users
        checker.users.put("john", "U1");
        checker.users.put("alice", "U2");

        String username = "john";

        System.out.println("Checking username: " + username);

        if (checker.checkAvailability(username)) {
            System.out.println("Username is available!");
        } else {
            System.out.println("Username is taken.");
            System.out.println("Suggestions: " + checker.suggestAlternatives(username));
        }

        // More attempts
        checker.checkAvailability("john");
        checker.checkAvailability("alice");
        checker.checkAvailability("john");

        System.out.println("Most attempted username: " + checker.getMostAttempted());
    }
}