public class Perceptron {
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
