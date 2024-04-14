import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Loader loader = new Loader();
        Text textProcessor = new Text();

        int featureSize = textProcessor.getFeatureSize();
        NeuralNetwork network = new NeuralNetwork();

        network.train(loader.loadTrainingData());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter text or exit");
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }

            double[] vector = textProcessor.processText(input);
            String language = network.predict(vector);
            System.out.println("Predicted language: " + language);
        }
        scanner.close();
    }
}
