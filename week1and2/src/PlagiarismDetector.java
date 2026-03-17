import java.util.*;

public class PlagiarismDetector {
    private Map<String, Set<String>> ngramDatabase = new HashMap<>();

    public void addDocument(String docId, String text, int n) {
        String[] words = text.toLowerCase().split("\\s+");
        for (int i = 0; i <= words.length - n; i++) {
            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + n));
            ngramDatabase.computeIfAbsent(ngram, k -> new HashSet<>()).add(docId);
        }
    }

    public double calculateSimilarity(String newDocText, String targetDocId, int n) {
        String[] words = newDocText.toLowerCase().split("\\s+");
        int totalNgrams = 0;
        int matches = 0;

        for (int i = 0; i <= words.length - n; i++) {
            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + n));
            totalNgrams++;
            if (ngramDatabase.getOrDefault(ngram, Collections.emptySet()).contains(targetDocId)) {
                matches++;
            }
        }
        return totalNgrams == 0 ? 0 : ((double) matches / totalNgrams) * 100;
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) {
        PlagiarismDetector detector = new PlagiarismDetector();
        int n = 3; // trigram comparison

        // Original documents
        String doc1 = "Java is a powerful programming language used for building applications";
        String doc2 = "Python is widely used for data science and machine learning";

        detector.addDocument("DOC1", doc1, n);
        detector.addDocument("DOC2", doc2, n);

        // New document to check
        String newDoc = "Java is a powerful programming language used for web applications";

        double similarityWithDoc1 = detector.calculateSimilarity(newDoc, "DOC1", n);
        double similarityWithDoc2 = detector.calculateSimilarity(newDoc, "DOC2", n);

        System.out.println("Similarity with DOC1: " + similarityWithDoc1 + "%");
        System.out.println("Similarity with DOC2: " + similarityWithDoc2 + "%");
    }
}