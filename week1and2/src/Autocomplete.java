import java.util.*;

public class Autocomplete {

    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        Map<String, Integer> prefixFrequencies = new HashMap<>();
    }

    private TrieNode root = new TrieNode();

    public void insert(String query) {
        TrieNode curr = root;
        for (char c : query.toCharArray()) {
            curr = curr.children.computeIfAbsent(c, k -> new TrieNode());
            curr.prefixFrequencies.put(query,
                    curr.prefixFrequencies.getOrDefault(query, 0) + 1);
        }
    }

    public List<String> search(String prefix) {
        TrieNode curr = root;

        for (char c : prefix.toCharArray()) {
            curr = curr.children.get(c);
            if (curr == null) return new ArrayList<>();
        }

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(curr.prefixFrequencies.entrySet());

        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            result.add(list.get(i).getKey());
        }
        return result;
    }

    // 🔹 MAIN METHOD
    public static void main(String[] args) {
        Autocomplete ac = new Autocomplete();

        // Simulate user queries (with repetitions to build frequency)
        String[] queries = {
                "apple", "app", "application", "app",
                "apple", "apex", "apply", "appetite",
                "banana", "band", "bandana"
        };

        for (String q : queries) {
            ac.insert(q);
        }

        // Test searches
        System.out.println("Search 'app': " + ac.search("app"));
        System.out.println("Search 'ap': " + ac.search("ap"));
        System.out.println("Search 'ban': " + ac.search("ban"));
        System.out.println("Search 'z': " + ac.search("z")); // no results
    }
}