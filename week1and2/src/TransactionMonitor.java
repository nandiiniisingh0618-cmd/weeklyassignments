import java.util.*;

public class TransactionMonitor {

    static class Transaction {
        int id;
        int amount;

        Transaction(int id, int amount) {
            this.id = id;
            this.amount = amount;
        }
    }

    public List<int[]> findTwoSum(List<Transaction> transactions, int targetAmount) {
        Map<Integer, Integer> complements = new HashMap<>(); // amount -> transaction_id
        List<int[]> suspiciousPairs = new ArrayList<>();

        for (Transaction tx : transactions) {
            int neededAmount = targetAmount - tx.amount;

            if (complements.containsKey(neededAmount)) {
                suspiciousPairs.add(new int[]{complements.get(neededAmount), tx.id});
            }

            complements.put(tx.amount, tx.id);
        }
        return suspiciousPairs;
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) {
        TransactionMonitor monitor = new TransactionMonitor();

        List<Transaction> transactions = Arrays.asList(
                new Transaction(1, 500),
                new Transaction(2, 1500),
                new Transaction(3, 700),
                new Transaction(4, 1300),
                new Transaction(5, 1000),
                new Transaction(6, 500)
        );

        int targetAmount = 2000;

        List<int[]> result = monitor.findTwoSum(transactions, targetAmount);

        System.out.println("Suspicious transaction pairs (sum = " + targetAmount + "):");

        if (result.isEmpty()) {
            System.out.println("No suspicious pairs found.");
        } else {
            for (int[] pair : result) {
                System.out.println("Transaction IDs: " + pair[0] + " & " + pair[1]);
            }
        }
    }
}