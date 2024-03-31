import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loader {
    private final Map<String, Integer> labelMapping;

    public Loader() {
        labelMapping = new HashMap<>();
        labelMapping.put("Iris-versicolor", 1);
        labelMapping.put("Iris-virginica", 0);
        labelMapping.put("1", 1);
        labelMapping.put("0", 0);
    }

    public List<Observation> loadData(String path) throws IOException {
        List<Observation> observations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split(",");
                double[] features = new double[splitted.length - 1];
                for (int i = 0; i < splitted.length - 1; i++) {
                    features[i] = Double.parseDouble(splitted[i]);
                }
                String labelString = splitted[splitted.length - 1];

                int label;
                try {
                    label = Integer.parseInt(labelString);
                    if (!labelMapping.containsValue(label)) {
                        throw new IllegalArgumentException("Unknown numeric label: " + label);
                    }
                } catch (NumberFormatException e) {
                    if (!labelMapping.containsKey(labelString)) {
                        throw new IllegalArgumentException("Unknown label: " + labelString);
                    }
                    label = labelMapping.get(labelString);
                }
                observations.add(new Observation(features, label));
            }
        }
        return observations;
    }
}
