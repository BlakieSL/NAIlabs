import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Loader {
    public Map<String, List<double[]>> load() {
        Map<String, List<double[]>> data = new HashMap<>();
        File directory = new File("data");
        Text processor = new Text();
            for (File languageDirectory : Objects.requireNonNull(directory.listFiles())) {
                String language = languageDirectory.getName();
                List<double[]> languageData = new ArrayList<>();

                for (File file : Objects.requireNonNull(languageDirectory.listFiles())) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        StringBuilder text = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            text.append(line).append(" ");
                        }
                        double[] features = processor.process(text.toString());
                        languageData.add(features);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                data.put(language, languageData);
            }

        return data;
    }
}
