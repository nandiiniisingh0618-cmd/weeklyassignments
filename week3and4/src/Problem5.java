import java.util.Arrays;

public class Problem5 {
    public static void main(String[] args) {
        String[] logs = {"accA", "accB", "accB", "accC"}; // Must be sorted for Binary
        String target = "accB";

        // Linear
        int linearIdx = -1;
        for(int i=0; i<logs.length; i++) if(logs[i].equals(target)) { linearIdx = i; break; }

        // Binary
        int binIdx = Arrays.binarySearch(logs, target);

        System.out.println("Linear first " + target + ": index " + linearIdx);
        System.out.println("Binary " + target + ": index " + binIdx);
    }
}