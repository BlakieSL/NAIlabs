public record Observation(double[] values, String label) {
    public double calcDist(double[] val2) {
        double sum = 0.0;
        for (int i = 0; i < this.values.length; i++) {
            sum += Math.sqrt(Math.pow(this.values[i] - val2[i], 2));
        }
        return sum;
    }
}
