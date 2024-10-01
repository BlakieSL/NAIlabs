import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Loader loader = new Loader();
        Text textProcessor = new Text();
        NeuralNetwork network = new NeuralNetwork();

        network.train(loader.load());

        while (true) {
            System.out.println("Enter text or exit");
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }

            double[] vector = textProcessor.process(input);
            String language = network.predict(vector);
            System.out.println("Predicted language: " + language);
        }
        scanner.close();
    }

    public static class NeuralNetwork {
        private double[][] weights;
        private String[] languages;

        public void train(Map<String, List<double[]>> trainingData) {
            initializeNetwork(trainingData);
            trainNetwork(trainingData);
            System.out.println("trained");
        }

        private void initializeNetwork(Map<String, List<double[]>> trainingData) {
            Set<String> languagesSet = trainingData.keySet();
            languages = languagesSet.toArray(new String[0]);
            int numFeatures = trainingData.values().iterator().next().get(0).length;
            weights = new double[languages.length][numFeatures];
            initializeWeights(languages.length, numFeatures);
        }

        private void initializeWeights(int numLanguages, int numFeatures) {
            Random random = new Random();
            for (int i = 0; i < numLanguages; i++) {
                for (int j = 0; j < numFeatures; j++) {
                    weights[i][j] = random.nextDouble() * 0.01;
                }
            }
        }
        private void trainNetwork(Map<String, List<double[]>> trainingData) {
            final double learningRate = 0.01;
            final int epochs = 100000;
            for (int epoch = 0; epoch < epochs; epoch++) {
                iterateEpoch(trainingData, learningRate);
            }
        }
        private void iterateEpoch(Map<String, List<double[]>> trainingData, double learningRate) {
            for (String language : languages) {
                List<double[]> inputs = trainingData.get(language);
                int targetIndex = Arrays.asList(languages).indexOf(language);
                for (double[] input : inputs) {
                    updateWeights(input, targetIndex, learningRate);
                }
            }
        }
        private void updateWeights(double[] input, int targetIndex, double learningRate) {
                double[] outputs = predictRaw(input);
            for (int i = 0; i < outputs.length; i++) {
                double error = (i == targetIndex ? 1.0 : 0.0) - outputs[i];
                for (int j = 0; j < input.length; j++) {
                    weights[i][j] += learningRate * error * input[j];
                }
            }
        }
        private double[] predictRaw(double[] input) {
            double[] neurons = new double[weights.length];
            for (int i = 0; i < weights.length; i++) {
                neurons[i] = 0;
                for (int j = 0; j < input.length; j++) {
                    neurons[i] += weights[i][j] * input[j];//weighted sum
                }
            }
            return neurons;
        }

        public String predict(double[] input) {
            double[] outputs = predictRaw(input);
            int maxIndex = findMaxIndex(outputs);
            return languages[maxIndex];
        }

        private int findMaxIndex(double[] outputs) {
            int maxIndex = 0;
            for (int i = 1; i < outputs.length; i++) {
                if (outputs[i] > outputs[maxIndex]) {
                    maxIndex = i;
                }
            }
            return maxIndex;
        }

    }

    public static class Text {
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

    public static class Loader {
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
}
