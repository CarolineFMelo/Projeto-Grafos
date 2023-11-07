package graphsFunctions;

import java.util.ArrayList;
import java.util.Collection;

import grafos.*;

public class GraphAlgorithms implements AlgoritmosEmGrafos {
	
	public Grafo graph;

	@Override
	public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {
		//open the file containing graph
		ArrayList<String> file = FileManager.stringReader(path);
		if (file == null) {
        	System.out.println("file not found");
        }
		System.out.println(file);
		
		//creates graph of the chosen type
		switch(t) {
		case MATRIZ_DE_ADJACENCIA:
			this.graph = new AdjacencyMatrix(file);
			break;
		case MATRIZ_DE_INCIDENCIA:
			this.graph = new IncidenceMatrix(file);
			break;
		case LISTA_DE_ADJACENCIA:
			this.graph = new AdjacencyList(file);
			break;
		}
		
		return null;
	}

	@Override
	public Collection<Aresta> buscaEmLargura(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Aresta> buscaEmProfundidade(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Aresta> menorCaminho(Grafo g, Vertice origem, Vertice destino) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existeCiclo(Grafo g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Aresta> agmUsandoKruskall(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean ehArvoreGeradora(Grafo g, Collection<Aresta> arestas) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double custoDoCaminho(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Aresta> arestasDeArvore(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Aresta> arestasDeRetorno(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Aresta> arestasDeAvanco(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Aresta> arestasDeCruzamento(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

}
