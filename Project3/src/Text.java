import java.util.HashMap;
import java.util.Map;

public class Text {
    public double[] process(String text) {
        text = text.toLowerCase();
        Map<Character, Integer> frequencies = new HashMap<>();
        int totalChars = 0;


        String allowed = "abcdefghijklmnopqrstuvwxyzäöüßàáâãäåçèéêëìíîïðñòóôõöùúûüýÿąćęłńóśźż";
        for (char character : allowed.toCharArray()) {
            frequencies.put(character, 0);
        }

        for (char character : text.toCharArray()) {
            if (frequencies.containsKey(character)) {
                frequencies.put(character, frequencies.get(character) + 1);
                totalChars++;
            }
        }

        double[] features = new double[allowed.length()];
        for (int i = 0; i < allowed.length(); i++) {
            char c = allowed.charAt(i);
            features[i] = (totalChars > 0) ? (double) frequencies.get(c) / totalChars : 0;//proportion of each letter in given text
        }
        return features;
    }
}
