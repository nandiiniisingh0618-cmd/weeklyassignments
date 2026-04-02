public class Problem6 {
    public static void main(String[] args) {
        int[] risks = {10, 25, 50, 100};
        int target = 30;

        int floor = -1, ceil = -1;
        int low = 0, high = risks.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (risks[mid] == target) { floor = ceil = risks[mid]; break; }
            else if (risks[mid] < target) { floor = risks[mid]; low = mid + 1; }
            else { ceil = risks[mid]; high = mid - 1; }
        }
        System.out.println("Floor: " + floor + ", Ceiling: " + ceil);
    }
}