import java.util.Scanner;

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
}
