import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Loader {
    public Map<String, List<double[]>> loadTrainingData() {
        Map<String, List<double[]>> trainingData = new HashMap<>();
        File dir = new File("data");
        Text processor = new Text();

        for (File languageDir : Objects.requireNonNull(dir.listFiles())) {
            String language = languageDir.getName();
            List<double[]> languageData = new ArrayList<>();

            for (File file : Objects.requireNonNull(languageDir.listFiles())) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder text = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        text.append(line).append(" ");
                    }
                    double[] features = processor.processText(text.toString());
                    languageData.add(features);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            trainingData.put(language, languageData);
        }
        return trainingData;
    }
}
