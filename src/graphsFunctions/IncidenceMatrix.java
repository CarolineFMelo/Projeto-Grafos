package graphsFunctions;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class IncidenceMatrix implements Grafo {
	
	public double weights[][];
	public String[] line = null;
	public ArrayList<Vertice> v = new ArrayList<Vertice>();
	public ArrayList<Aresta> a = new ArrayList<Aresta>();
	public int numEdges = 0;
	
	public IncidenceMatrix(ArrayList<String> file) throws Exception {
		int numVertices = Integer.parseInt(file.get(0));
		
		//add the vertices to the ArrayList
		for(int i = 1; i < file.size(); i++) {
			line = file.get(i).split(" ");
			v.add(new Vertice(Integer.parseInt(line[0])));
		}
		
		//counts number of edges
		for(int i = 1; i < file.size(); i++) {
			line = file.get(i).split(" ");
			for(int j = 1; j < line.length; j++) {
				numEdges++;
			}
		}
		this.weights = new double[numVertices][numEdges];
		
		numEdges = 0;
		
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
		a.add(new Aresta(origem, destino, numEdges));
		this.weights[origem.id()][numEdges] = 1;
		numEdges++;
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		a.add(new Aresta(origem, destino, numEdges, peso));
		this.weights[origem.id()][numEdges] = peso;
		numEdges++;
	}

	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		//traverses the matrix
		for(int i = 0; i < a.size(); i++) {
			if((a.get(i).origem() == origem) && (a.get(i).destino() == destino)) {
				return (this.weights[origem.id()][i] > 0);
			}
		}
		
		return false;
	}

	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int grau = 0;
		
		//calculates the degree of the vertex
		for(int i = 0; i < a.size(); i++) {
            if(weights[vertice.id()][i] > 0) {
                grau++;
            }
        }
				
		return grau;
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
		int cont = 0;
		
		//traverses the arrayList of edges
		for(int i = 0; i < a.size(); i++) {
			cont++;
		}
		
		return cont;
	}

	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		ArrayList<Vertice> adjacentVertices = new ArrayList<Vertice>();

		//traverses the matrix in the line corresponding to the vertex
		for(int i = 0; i < a.size(); i++) {
			if(this.weights[vertice.id()][i] > 0) {
				for(Aresta edge : a) {
					if(edge.origem() == vertice) {
						adjacentVertices.add(new Vertice(edge.destino().id()));
					}
				}
				return adjacentVertices;
			}
		}

		return null;
	}

	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		//traverses the matrix
		for(int i = 0; i < a.size(); i++) {
			if((a.get(i).origem() == origem) && (a.get(i).destino() == destino)) {
				this.weights[origem.id()][i] = peso;
				
				//changes weight on object edge
				for(Aresta edge : a) {
					if((edge.origem() == origem) && (edge.destino() == destino)) {
						edge.setarPeso(peso);
					}
				}
			}
		}
	}

	@Override
	public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
		ArrayList<Aresta> edgesBetweenVertices = new ArrayList<>();

		//traverses the arrayList of edges
		for(Aresta edge : a) {
			if((edge.origem() == origem) && (edge.destino() == destino)) {
				edgesBetweenVertices.add(new Aresta(origem, destino, edge.getId(), edge.peso()));
			}
		}

	    return edgesBetweenVertices;
	}

	@Override
	public ArrayList<Vertice> vertices() {
		return v;
	}

}
