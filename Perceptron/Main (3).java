import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Loader loader = new Loader();
        String trainingDataPath = System.getProperty("user.dir") + File.separator +
                "data" + File.separator + "example2" + File.separator + "train.txt";
        String testDataPath = System.getProperty("user.dir") + File.separator +
                "data" + File.separator + "example2" + File.separator + "test.txt";

        System.out.print("Enter learning rate: ");
        double learningRate = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter number of epochs: ");
        int epochs = Integer.parseInt(scanner.nextLine());

        List<Observation> trainingData = loader.loadData(trainingDataPath);
        List<Observation> testData = loader.loadData(testDataPath);


        double[][] trainingInputs = new double[trainingData.size()][];
        int[] trainingLabels = new int[trainingData.size()];
        for (int i = 0; i < trainingData.size(); i++) {
            trainingInputs[i] = trainingData.get(i).getFeatures();
            trainingLabels[i] = trainingData.get(i).getLabel();
        }

        double[][] testInputs = new double[testData.size()][];
        int[] testLabels = new int[testData.size()];
        for (int i = 0; i < testData.size(); i++) {
            testInputs[i] = testData.get(i).getFeatures();
            testLabels[i] = testData.get(i).getLabel();
        }



        Perceptron perceptron = new Perceptron(trainingInputs[0].length);
        perceptron.train(trainingInputs, trainingLabels, learningRate, epochs);

        int correctPredictions = 0;
        for (int i = 0; i < testInputs.length; i++) {
            if (perceptron.predict(testInputs[i]) == testLabels[i]) {
                correctPredictions++;
            }
        }
        System.out.println("Test accuracy: " + 100.0 * correctPredictions / testInputs.length + "%");



        while (true) {
            System.out.println("Enter new observation: ");
            String line = scanner.nextLine();
            if (line.equals("exit")) break;

            String[] parts = line.split(",");
            double[] observation = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                observation[i] = Double.parseDouble(parts[i]);
            }

            int prediction = perceptron.predict(observation);
            System.out.println("Prediction: " + (prediction == 1 ? "Iris-versicolor(1)" : "Iris-virginica(0)"));
        }
    }

    public static class Perceptron {
        private final double[] weights;
        private double threshold;

        public Perceptron(int numInputs) {
            weights = new double[numInputs];
            for (int i = 0; i < numInputs; i++) {
                weights[i] = Math.random() * 0.01;
            }
            threshold = Math.random() * 0.01;
        }
        public int predict(double[] input) {//activation function(step function)
            double sum = 0.0;
            for (int i = 0; i < input.length; i++) {
                sum += weights[i] * input[i];
            }
            return sum >= threshold ? 1 : 0;
        }

        public void train(double[][] inputs, int[] labels, double learningRate, int epochs) {
            for (int epoch = 0; epoch < epochs; epoch++) {
                double errors = 0;
                for (int i = 0; i < inputs.length; i++) {
                    double error = calculateError(inputs[i], labels[i]);
                    updateWeightsAndThreshold(inputs[i], error, learningRate);
                    errors += Math.abs(error);
                }
                printAccuracy(epoch, errors, inputs.length);
            }
        }
        private double calculateError(double[] input, int label) {
            double output = predict(input);
            return label - output;
        }

        private void updateWeightsAndThreshold(double[] input, double error, double learningRate) {//delta rule itself
            for (int j = 0; j < weights.length; j++) {
                weights[j] += learningRate * error * input[j];
            }
            threshold -= learningRate * error;
        }
        private void printAccuracy(int epoch, double errors, int numInputs) {
            double accuracy = (1 - errors / numInputs) * 100;
            System.out.println("Epoch " + (epoch + 1) + ": Accuracy = " + accuracy + "%");
        }
    }

    public static class Observation {
        double[] features;
        int label;

        public Observation(double[] features, int label) {
            this.features = features;
            this.label = label;
        }

        public double[] getFeatures() {
            return features;
        }

        public int getLabel() {
            return label;
        }
    }

    public static class Loader {
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
}
