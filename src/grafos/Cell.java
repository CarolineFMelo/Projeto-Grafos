package grafos;

public class Cell {

	private Vertice vertex;
	private double weight;
	
	public Cell(Vertice v, double p) {
		this.vertex = v;
		this.weight = p;
	}

	public Vertice getVertex() {
		return vertex;
	}

	public void setVertex(Vertice vertex) {
		this.vertex = vertex;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}
