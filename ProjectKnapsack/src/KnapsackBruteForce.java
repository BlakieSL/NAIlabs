import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnapsackBruteForce {

    private static final List<Feature> features = new ArrayList<>();
    private static int totalCapacity;
    private static void readInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("data//knapsack_data//15"))) {
            totalCapacity = Integer.parseInt(br.readLine().trim());
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int weight = Integer.parseInt(parts[0]);
                int value = Integer.parseInt(parts[1]);
                features.add(new Feature(weight, value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void print(long executionTime,int[] bestSolution, int bestWeight, int bestValue){
        System.out.println("Best solution:");
        for (int i : bestSolution) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Best total weight: " + bestWeight);
        System.out.println("Best total value: " + bestValue);
        System.out.println("Execution time(milliseconds): " + executionTime);
    }

    public static void main(String[] args) {
        readInput();
        int n = features.size();
        int bestWeight = 0;
        int bestValue = 0;
        int[] bestVector = new int[n];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < Math.pow(2,n); i++) {
            int[] currentVector = new int[n];
            int currentValue = 0;
            int currentWeight = 0;

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    currentVector[j] = 1;
                    currentWeight += features.get(j).getWeight();
                    currentValue += features.get(j).getValue();
                } else {
                    currentVector[j] = 0;
                }
            }
            if (currentWeight <= totalCapacity && currentValue > bestValue) {
                bestVector = currentVector.clone();
                bestValue = currentValue;
                bestWeight = currentWeight;
            }
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        print(executionTime, bestVector, bestWeight, bestValue);
    }
}
