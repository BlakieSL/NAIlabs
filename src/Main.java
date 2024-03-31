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
}
