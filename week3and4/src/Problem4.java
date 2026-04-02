public class Problem4 {
    public static void main(String[] args) {
        double[] returns = {12.0, 8.0, 15.0};
        quickSort(returns, 0, returns.length - 1);
        System.out.println("QuickSort (desc): " + java.util.Arrays.toString(returns));
    }

    static void quickSort(double[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    static int partition(double[] arr, int low, int high) {
        double pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] > pivot) { // Descending
                double temp = arr[++i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        double temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
