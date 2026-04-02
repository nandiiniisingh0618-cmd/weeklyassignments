class Client {
    String name;
    int riskScore;
    Client(String name, int riskScore) { this.name = name; this.riskScore = riskScore; }
    @Override
    public String toString() { return name + ":" + riskScore; }
}

public class Problem2 {
    public static void main(String[] args) {
        Client[] clients = { new Client("C", 80), new Client("A", 20), new Client("B", 50) };

        // Insertion Sort DESC
        for (int i = 1; i < clients.length; i++) {
            Client key = clients[i];
            int j = i - 1;
            while (j >= 0 && clients[j].riskScore < key.riskScore) {
                clients[j + 1] = clients[j--];
            }
            clients[j + 1] = key;
        }

        System.out.println("Insertion (desc): " + java.util.Arrays.toString(clients));
    }
}