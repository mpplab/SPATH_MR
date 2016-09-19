package kmeans;

public class kmeans_data {
	public double[][] data;
	public int length;
	public int dim;
	public int[] labels;
	public double[][] centers;
	public int[] centerCounts;
	
	public kmeans_data(double[][] data, int length, int dim) {
		this.data = data;
		this.length = length;
		this.dim = dim;
	}
}
