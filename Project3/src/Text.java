import java.util.HashMap;
import java.util.Map;

public class Text {
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzäöüßàáâãäåçèéêëìíîïðñòóôõöùúûüýÿąćęłńóśźż";

    public double[] processText(String text) {
        text = text.toLowerCase();
        Map<Character, Integer> frequencies = new HashMap<>();
        int totalChars = 0;


        for (char c : ALLOWED_CHARS.toCharArray()) {
            frequencies.put(c, 0);
        }

        for (char c : text.toCharArray()) {
            if (frequencies.containsKey(c)) {
                frequencies.put(c, frequencies.get(c) + 1);
                totalChars++;
            }
        }

        double[] features = new double[ALLOWED_CHARS.length()];
        for (int i = 0; i < ALLOWED_CHARS.length(); i++) {
            char c = ALLOWED_CHARS.charAt(i);
            features[i] = (totalChars > 0) ? (double) frequencies.get(c) / totalChars : 0;
        }

        return features;
    }

    public int getFeatureSize() {
        return ALLOWED_CHARS.length();
    }
}
