import java.util.*;

public class NeuralNetwork {
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
