package graphsFunctions;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class AdjacencyMatrix implements Grafo {
	
	public double weights[][];
	public String[] line = null;
	public ArrayList<Vertice> v = new ArrayList<Vertice>();
	
	public AdjacencyMatrix(ArrayList<String> file) throws Exception {
		int numVertices = Integer.parseInt(file.get(0));
		this.weights = new double[numVertices][numVertices];
		
		//add the vertices to the ArrayList
		for(int i = 1; i < file.size(); i++) {
			line = file.get(i).split(" ");
			v.add(new Vertice(Integer.parseInt(line[0])));
		}
		
		for(int i = 1; i < file.size(); i++) {
			line = file.get(i).split(" ");
			
			for(int j = 1; j < line.length; j++) {
				String[] edge = line[j].split("-");
				Vertice v_origin = v.get(Integer.parseInt(line[0]));
				Vertice v_destiny = v.get(Integer.parseInt(edge[0]));
				int weight = Integer.parseInt(edge[1].replace(";", ""));
				
				if(weight <= 1) {
					adicionarAresta(v_origin, v_destiny);
				}
				else {
					adicionarAresta(v_origin, v_destiny, weight);
				}
			}
		}
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		this.weights[origem.id()][destino.id()] = 1;
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		this.weights[origem.id()][destino.id()] = peso;
	}

	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		return (this.weights[origem.id()][destino.id()] > 0);
	}

	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int degree = 0;
		int numVertices = numeroDeVertices();

		//calculates the exit degree of the vertex
		for(int i = 0; i < numVertices; i++) {
			if(this.weights[vertice.id()][i] > 0) {
				degree++;
			}
		}

		//calculates the in-degree of the vertex
		for(int i = 0; i < numVertices; i++) {
			if(this.weights[i][vertice.id()] > 0) {
				degree++;
			}
		}

		return degree;
	}

	@Override
	public int numeroDeVertices() {
		int cont = 0;
		
		//traverses the arrayList of vertices
		for(int i = 0; i < v.size(); i++) {
			cont++;
		}
		
		return cont;
	}

	@Override
	public int numeroDeArestas() {
		int numEdges = 0;
		int numVertices = numeroDeVertices();

		//traverses the matrix
		for(int i = 0; i < numVertices; i++) {
			for(int j = 0; j < numVertices; j++) {
				if(this.weights[i][j] > 0) {
					numEdges++;
				}
			}
		}

		return numEdges;
	}

	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		ArrayList<Vertice> adjacentVertices = new ArrayList<Vertice>();
		int numVertices = numeroDeVertices();

		//traverses the matrix in the line corresponding to the vertex
		for(int i = 0; i < numVertices; i++) {
			if(this.weights[vertice.id()][i] > 0) {
				adjacentVertices.add(new Vertice(i));
			}
		}

		return adjacentVertices;
	}

	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		this.weights[origem.id()][destino.id()] = peso;
	}

	@Override
	public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
		ArrayList<Aresta> edgesBetweenVertices = new ArrayList<>();
	    int numVertices = numeroDeVertices();
	    int numEdges = 0;

	    //traverses the matrix in the line corresponding to the vertex
	    for(int i = 0; i < numVertices; i++) {
	        if(this.weights[origem.id()][i] > 0) {
	            if(i == destino.id()) {
	                edgesBetweenVertices.add(new Aresta(origem, destino, numEdges));
	                numEdges++;
	            }
	        }
	    }

	    return edgesBetweenVertices;
	}

	@Override
	public ArrayList<Vertice> vertices() {
		return v;
	}

}
