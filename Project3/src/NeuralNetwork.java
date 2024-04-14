import java.util.*;

public class NeuralNetwork {
    private double[][] weights;
    private String[] languages;
    public void train(Map<String, List<double[]>> trainingData) {
        Set<String> languageSet = trainingData.keySet();
        int numLanguages = languageSet.size();
        languages = languageSet.toArray(new String[0]);
        int numFeatures = trainingData.values().iterator().next().get(0).length;
        weights = new double[numLanguages][numFeatures];

        final double learningRate = 0.01;
        final int epochs = 100000;

        Random random = new Random();
        for (int i = 0; i < numLanguages; i++) {
            for (int j = 0; j < numFeatures; j++) {
                weights[i][j] = random.nextDouble() * 0.01;
            }
        }

        for (int epoch = 0; epoch < epochs; epoch++) {
            for (String language : languages) {
                List<double[]> inputs = trainingData.get(language);
                for (double[] input : inputs) {
                    int targetIndex = Arrays.asList(languages).indexOf(language);
                    double[] output = predictRaw(input);
                    for (int i = 0; i < output.length; i++) {
                        for (int j = 0; j < numFeatures; j++) {
                            double error = (i == targetIndex ? 1.0 : 0.0) - output[i];
                            weights[i][j] += learningRate * error * input[j];
                        }
                    }
                }
            }
        }
        System.out.println("trained");
    }

    public String predict(double[] input) {
        double[] outputs = predictRaw(input);
        int maxIndex = 0;
        for (int i = 1; i < outputs.length; i++) {
            if (outputs[i] > outputs[maxIndex]) {
                maxIndex = i;
            }
        }
        return languages[maxIndex];
    }

    private double[] predictRaw(double[] input) {
        double[] outputs = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            outputs[i] = 0;
            for (int j = 0; j < input.length; j++) {
                outputs[i] += weights[i][j] * input[j];
            }
        }
        return outputs;
    }
}
