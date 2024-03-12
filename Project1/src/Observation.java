public class Observation {
    private String label;
    private double[] values;
    public Observation(double[]values, String label) {
        this.values = values;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public double[] getValues() {
        return values;
    }
    public double calcDist(double[]val2){
        double sum = 0.0;
        for(int i = 0; i<this.values.length; i++){
            sum += Math.sqrt(Math.pow(this.values[i] - val2[i],2));
        }
        return sum;
    }
}
