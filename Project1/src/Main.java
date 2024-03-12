import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the filename: ");
        String filepath =  System.getProperty("user.dir")+ File.separator +scanner.nextLine();
        System.out.println("Enter k: ");
        int k = scanner.nextInt();
        KNN knn = new KNN(filepath, k);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("a) Classify all observations from a test set");
            System.out.println("b) Classify a single observation");
            System.out.println("c) Change K");
            System.out.println("d) Exit");
            String option = scanner.nextLine();

            switch (option) {
                case "a":
                    System.out.println("Enter the filename: ");
                    String filename1 = scanner.nextLine();
                    ArrayList<Observation> data = knn.loadData(filename1);

                    int correctPredictions = 0;
                    for (Observation testObservation : data) {
                        String predictedLabel = knn.classification(testObservation.getValues());
                        String actualLabel = testObservation.getLabel();
                        System.out.println("Predicted: " + predictedLabel + ", Actual: " + actualLabel);
                        if (predictedLabel.equals(actualLabel)) {
                            correctPredictions++;
                        }
                    }
                    double accuracy = (double) correctPredictions / data.size();
                    System.out.println("Accuracy: " + accuracy);
                    break;
                case "b":
                    System.out.println("Enter an observation: ");
                    String observation = scanner.nextLine();
                    double[] values = Arrays.stream(observation.split(","))
                            .mapToDouble(Double::parseDouble)
                            .toArray();
                    String label = knn.classification(values);
                    System.out.println("The label is: " + label);
                    break;
                case "c":
                    System.out.println("Enter a new value for K:");
                    knn.setK(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case "d":
                    System.exit(0);
                default:
                    System.out.println("error");
                    break;

            }
        }
    }
}