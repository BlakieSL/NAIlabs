import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class KNN {
    private final ArrayList<Observation> data;
    private int k;
    public KNN(String filename, int k) {
        this.k = k;
        this.data = loadData(filename);
    }
    public ArrayList<Observation> loadData(String filename){
        ArrayList<Observation> data = new ArrayList<>();
        String line;
        double[]values;
        String label;
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            while((line = reader.readLine()) != null){
                String[] splitted = line.split(",");
                values = new double[splitted.length-1];
                for(int i=0; i<values.length;i++){
                    values[i] = Double.parseDouble(splitted[i]);
                }
                label = splitted[splitted.length-1];
                data.add(new Observation(values, label));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return data;
    }
    public void setK(int k){
        this.k = k;
    }
    public int getK(){
        return this.k;
    }
    public String classification(double[] values) {
        ArrayList<Observation> nearest = findNearest(values);
        return getMajor(nearest);
    }
    private ArrayList<Observation> findNearest(double[] values) {
        return (ArrayList<Observation>) data
                .stream()
                .sorted(Comparator.comparingDouble(obs -> obs.calcDist(values)))//sort according to comparator specifications(calc dist, then compare)
                .limit(k)
                .collect(Collectors.toList());
    }
    private String getMajor(ArrayList<Observation> nearest) {

        HashMap<String, Integer> count = new HashMap<>();

        for (Observation obs : nearest) {
            String label = obs.label();
            count.put(label, count.getOrDefault(label, 0) + 1);
        }

        String majorityLabel = null;
        int maxCount = 0;

        for (HashMap.Entry<String, Integer> entry : count.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                majorityLabel = entry.getKey();
            }
        }
        return majorityLabel;
    }
}