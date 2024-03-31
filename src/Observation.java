public class Observation {
    double[] features;
    int label;

    public Observation(double[] features, int label) {
        this.features = features;
        this.label = label;
    }

    public double[] getFeatures() {
        return features;
    }

    public int getLabel() {
        return label;
    }
}
