import java.util.*;

class Transaction {
    String id;
    double fee;
    String ts;

    Transaction(String id, double fee, String ts) {
        this.id = id;
        this.fee = fee;
        this.ts = ts;
    }

    @Override
    public String toString() { return id + ":" + fee + "@" + ts; }
}

public class Problem1 {
    public static void main(String[] args) {
        List<Transaction> txns = new ArrayList<>(Arrays.asList(
                new Transaction("id1", 10.5, "10:00"),
                new Transaction("id2", 25.0, "09:30"),
                new Transaction("id3", 5.0, "10:15")
        ));

        int n = txns.size();
        int swaps = 0, passes = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (txns.get(j).fee > txns.get(j + 1).fee) {
                    Collections.swap(txns, j, j + 1);
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        System.out.println("BubbleSort (fees): " + txns + " // " + passes + " passes, " + swaps + " swaps");
        System.out.print("High-fee outliers: ");
        txns.stream().filter(t -> t.fee > 50).forEach(System.out::print);
    }
}