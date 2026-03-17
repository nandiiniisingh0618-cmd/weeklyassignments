import java.util.*;

public class FlashSaleInventory {
    private int stock = 5; // reduced for demo
    private Set<Integer> purchases = new HashSet<>();
    private LinkedHashMap<Integer, Boolean> waitlist = new LinkedHashMap<>();

    public synchronized String purchaseItem(String productId, int userId) {
        if (purchases.contains(userId)) {
            return "User " + userId + " already purchased";
        }

        if (stock > 0) {
            stock--;
            purchases.add(userId);
            return "User " + userId + " SUCCESS, " + stock + " units remaining";
        } else {
            waitlist.putIfAbsent(userId, true);
            int position = new ArrayList<>(waitlist.keySet()).indexOf(userId) + 1;
            return "User " + userId + " added to waiting list, position #" + position;
        }
    }

    // Optional helper to print state
    public void printStatus() {
        System.out.println("\n--- FINAL STATUS ---");
        System.out.println("Remaining stock: " + stock);
        System.out.println("Purchased users: " + purchases);
        System.out.println("Waitlist: " + waitlist.keySet());
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) {
        FlashSaleInventory sale = new FlashSaleInventory();
        String productId = "P1001";

        // Simulating multiple users
        int[] users = {1, 2, 3, 4, 5, 6, 7, 3, 8, 2};

        for (int userId : users) {
            String result = sale.purchaseItem(productId, userId);
            System.out.println(result);
        }

        sale.printStatus();
    }
}