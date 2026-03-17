import java.util.*;

public class ParkingLot {
    private String[] spots;
    private int capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.spots = new String[capacity]; // null = empty
    }

    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % capacity;
    }

    public int parkVehicle(String licensePlate) {
        int homeSpot = hash(licensePlate);

        // Linear probing
        for (int i = 0; i < capacity; i++) {
            int currentSpot = (homeSpot + i) % capacity;

            if (spots[currentSpot] == null) {
                spots[currentSpot] = licensePlate;
                return currentSpot;
            }
        }
        return -1; // Full
    }

    // Optional: remove vehicle
    public boolean removeVehicle(String licensePlate) {
        int homeSpot = hash(licensePlate);

        for (int i = 0; i < capacity; i++) {
            int currentSpot = (homeSpot + i) % capacity;

            if (spots[currentSpot] == null) return false;

            if (spots[currentSpot].equals(licensePlate)) {
                spots[currentSpot] = null;
                return true;
            }
        }
        return false;
    }

    // Print parking lot status
    public void printLot() {
        System.out.println("\nParking Lot Status:");
        for (int i = 0; i < capacity; i++) {
            System.out.println("Spot " + i + ": " +
                    (spots[i] == null ? "Empty" : spots[i]));
        }
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(5);

        String[] vehicles = {
                "TN01AB1234",
                "TN02CD5678",
                "KA03EF1111",
                "MH04GH2222",
                "DL05IJ3333",
                "AP06KL4444" // extra (should fail)
        };

        // Park vehicles
        for (String v : vehicles) {
            int spot = lot.parkVehicle(v);
            if (spot != -1) {
                System.out.println(v + " parked at spot " + spot);
            } else {
                System.out.println(v + " -> Parking Full!");
            }
        }

        lot.printLot();

        // Remove a vehicle
        System.out.println("\nRemoving TN02CD5678...");
        lot.removeVehicle("TN02CD5678");

        lot.printLot();

        // Try parking again after removal
        System.out.println("\nParking new vehicle AP07MN5555...");
        int newSpot = lot.parkVehicle("AP07MN5555");
        System.out.println("AP07MN5555 parked at spot " + newSpot);

        lot.printLot();
    }
}