import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCode {
    public Node buildHuffmanTree(String data) {

        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : data.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        PriorityQueueList<Node> pq = new PriorityQueueList<>(Comparator.comparingInt(node -> node.frequency));
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.insert(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() > 1) {
            Node left = pq.extractMin();
            Node right = pq.extractMin();
            Node newNode = new Node(left.frequency + right.frequency);
            newNode.left = left;
            newNode.right = right;
            pq.insert(newNode);
        }
        return pq.extractMin();
    }
    public Map<Character, String> generateHuffmanCodes(Node root) {
        Map<Character, String> codes = new HashMap<>();
        generateCodesHelper(root, "", codes);
        return codes;
    }

    private void generateCodesHelper(Node node, String currentCode, Map<Character, String> codes) {
        if (node == null) {
            return;
        }
        if (node.character != '\0') {//leaf node
            codes.put(node.character, currentCode);
            return;
        }
        generateCodesHelper(node.left, currentCode + '0', codes);
        generateCodesHelper(node.right, currentCode + '1', codes);
    }


    public String encode(String data, Map<Character, String> huffmanCodes) {
        StringBuilder encodedData = new StringBuilder();
        for (char c : data.toCharArray()) {
            encodedData.append(huffmanCodes.get(c));
        }
        return encodedData.toString();
    }

    public String decode(String encodedData, Node root) {
        StringBuilder decodedData = new StringBuilder();
        Node currentNode = root;
        for (char bit : encodedData.toCharArray()) {
            currentNode = (bit == '0') ? currentNode.left : currentNode.right;
            if (currentNode.character != '\0') {
                decodedData.append(currentNode.character);
                currentNode = root;
            }
        }
        return decodedData.toString();
    }
}
