package graphsFunctions;

import java.util.ArrayList;
import java.util.List;

import grafos.*;

public class AdjacencyList implements Grafo {
	
	public String[] line = null;
	public ArrayList<Vertice> v = new ArrayList<Vertice>();
	public List<ArrayList<Cell>> listAdj;
	
	public AdjacencyList(ArrayList<String> file) throws Exception {
		int numVertices = Integer.parseInt(file.get(0));
		this.listAdj = new ArrayList<ArrayList<Cell>>();
		
		for(int i = 1; i < file.size(); i++) {
			line = file.get(i).split(" ");
			v.add(new Vertice(Integer.parseInt(line[0])));
		}
		
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
		arestasEntre(v.get(0), v.get(1));
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		this.listAdj.get(origem.id()).add(new Cell(destino, 1.0));
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		this.listAdj.get(origem.id()).add(new Cell(destino, peso));
	}

	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
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
		
		for(Cell cell : listAdj.get(vertice.id())) {
            cont++;
        }
		
		return cont;
	}

	@Override
	public int numeroDeVertices() {
		int cont = 0;
		
		for (ArrayList<Cell> listOfCells : listAdj) {
            cont++;
        }
		
		return cont;
	}

	@Override
	public int numeroDeArestas() {
		int cont = 0;
		
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

		for(Cell cell : listAdj.get(vertice.id())) {
            adjacentVertices.add(cell.getVertex());
        }

		return adjacentVertices;
	}

	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		for(Cell cell : listAdj.get(origem.id())) {
            if(cell.getVertex() == destino) {
            	cell.setWeight(peso);
            }
        }
	}

	@Override
	public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
		ArrayList<Aresta> edgesBetweenVertices = new ArrayList<>();
	    int numVertices = numeroDeVertices();

	    for(Cell cell : listAdj.get(origem.id())) {
            if(cell.getVertex() == destino) {
            	edgesBetweenVertices.add(new Aresta(origem, destino));
            }
        }

	    return edgesBetweenVertices;
	}

	@Override
	public ArrayList<Vertice> vertices() {
		return v;
	}



}