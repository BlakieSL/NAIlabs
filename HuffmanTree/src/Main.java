import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String data = "testing string23";

        HuffmanCode huffman = new HuffmanCode();
        Node huffmanTree = huffman.buildHuffmanTree(data);
        Map<Character, String> huffmanCodes = huffman.generateHuffmanCodes(huffmanTree);
        String encodedData = huffman.encode(data, huffmanCodes);

        System.out.println("Huffman Codes:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nEncoded Data:");
        System.out.println(encodedData);

        String decodedData = huffman.decode(encodedData, huffmanTree);
        System.out.println("\nDecoded Data:");
        System.out.println(decodedData);
    }
}
