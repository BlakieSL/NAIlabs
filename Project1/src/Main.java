import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        /*
        System.out.println("Enter the filename: ");
        String filepath =  System.getProperty("user.dir")+ File.separator + scanner.nextLine();
       */
        String filepath = System.getProperty("user.dir")+ File.separator + "train.txt";
        System.out.println("Enter k: ");
        KNN knn = new KNN(filepath, scanner.nextInt());
        scanner.nextLine();

        Map<Integer, Double> accmap = new LinkedHashMap<>();

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("a) classify a file");
            System.out.println("b) classify a single obs");
            System.out.println("c) change K");
            System.out.println("d) exit");
            System.out.println("e) plot");
            String option = scanner.nextLine();

            switch (option) {
                case "a":
                    /*
                    System.out.println("Enter the filename: ");
                    String filepath1 = System.getProperty("user.dir")+ File.separator + scanner.nextLine();
                     */
                    String filepath1 = System.getProperty("user.dir")+ File.separator + "test.txt";
                    ArrayList<Observation> data = knn.loadData(filepath1);

                    int correct = 0;
                    for (Observation obs : data) {
                        String predict = knn.classification(obs.values());
                        String actual = obs.label();
                        System.out.println("Predicted: " + predict + ", Actual: " + actual);
                        if (predict.equals(actual)) {
                            correct++;
                        }else{
                            System.out.println("INCORRECT PREDICTION");
                        }
                    }
                    double accuracy = (double) correct / data.size() * 100;
                    System.out.println("Accuracy: " + accuracy + "%");
                    accmap.putIfAbsent(knn.getK(), accuracy);
                    break;

                case "b":
                    System.out.println("Enter an observation: ");
                    String observation = scanner.nextLine();
                    scanner.nextLine();
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
                    System.out.println(knn.getK());
                    break;
                case "d":
                    System.exit(0);
                case "e":
                    plotAccuracy(accmap);
                default:
                    System.out.println("error");
                    break;
            }
        }
    }
    public static void plotAccuracy(Map<Integer, Double> accmap) {
        double[] ks = new double[accmap.size()];
        double[] accuracies = new double[accmap.size()];
        int index = 0;
        for (Map.Entry<Integer, Double> entry : accmap.entrySet()) {
            ks[index] = entry.getKey();
            accuracies[index] = entry.getValue();
            index++;
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).title("KNN Accuracy plot").xAxisTitle("K").yAxisTitle("Accuracy").build();
        chart.getStyler().setMarkerSize(10);
        chart.getStyler().setYAxisMin(80.0);
        chart.getStyler().setYAxisMax(100.0);

        XYSeries series = chart.addSeries("Accuracy", ks, accuracies);
        series.setMarker(SeriesMarkers.CIRCLE);
        new SwingWrapper(chart).displayChart();
    }

}