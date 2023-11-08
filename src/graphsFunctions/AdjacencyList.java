package graphsFunctions;

import java.util.ArrayList;
import java.util.List;

import grafos.*;

public class AdjacencyList implements Grafo {
	
	public String[] line = null;
	public ArrayList<Vertice> v = new ArrayList<Vertice>();
	public ArrayList<Aresta> a = new ArrayList<Aresta>();
	public List<ArrayList<Cell>> listAdj;
	public int numEdges = 0;
	
	public AdjacencyList(ArrayList<String> file) throws Exception {
		int numVertices = Integer.parseInt(file.get(0));
		this.listAdj = new ArrayList<ArrayList<Cell>>();
		
		//add the vertices to the ArrayList
		for(int i = 1; i < file.size(); i++) {
			line = file.get(i).split(" ");
			v.add(new Vertice(Integer.parseInt(line[0])));
		}
		
		//initialize the list
		for (int i = 0; i < numVertices; i++) {
		    listAdj.add(new ArrayList<Cell>());
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
		a.add(new Aresta(origem, destino, numEdges));
		this.listAdj.get(origem.id()).add(new Cell(destino, 1.0));
		numEdges++;
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		a.add(new Aresta(origem, destino, numEdges, peso));
		this.listAdj.get(origem.id()).add(new Cell(destino, peso));
		numEdges++;
	}

	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		//traverses the arrayList of cells
		for(Cell cell : listAdj.get(origem.id())) {
            if(cell.getVertex() == destino) {
            	return true;
            }
        }

		return false;
	}

	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int cont = 0;
		
		//traverses the arrayList of cells
		for(Cell cell : listAdj.get(vertice.id())) {
            cont++;
        }
		
		return cont;
	}

	@Override
	public int numeroDeVertices() {
		int cont = 0;
		
		//traverses the adjacent list
		for (ArrayList<Cell> listOfCells : listAdj) {
            cont++;
        }
		
		return cont;
	}

	@Override
	public int numeroDeArestas() {
		int cont = 0;
		
		//traverses the adjacent list
		for (ArrayList<Cell> listOfCells : listAdj) {
            for (Cell cell : listOfCells) {
                cont++;
            }
        }
		
		return cont;
	}

	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		ArrayList<Vertice> adjacentVertices = new ArrayList<Vertice>();
		int numVertices = numeroDeVertices();

		//traverses the arrayList of cells
		for(Cell cell : listAdj.get(vertice.id())) {
            adjacentVertices.add(cell.getVertex());
        }

		return adjacentVertices;
	}

	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		//traverses the arrayList of cells
		for(Cell cell : listAdj.get(origem.id())) {
            if(cell.getVertex() == destino) {
            	cell.setWeight(peso);
            	
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
	    int numVertices = numeroDeVertices();
	    int numEdges = 0;
	    
	    //traverses the arrayList of cells
	    for(Cell cell : listAdj.get(origem.id())) {
            if(cell.getVertex() == destino) {
            	edgesBetweenVertices.add(new Aresta(origem, destino, numEdges));
            	numEdges++;
            }
        }

	    return edgesBetweenVertices;
	}

	@Override
	public ArrayList<Vertice> vertices() {
		return v;
	}



}