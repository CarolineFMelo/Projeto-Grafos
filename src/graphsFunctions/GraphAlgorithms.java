package graphsFunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

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
	
	public void forwardFunction(String function) {
		switch(function) {
			case "1":
				buscaEmProfundidade(graph);
				break;
			case "2":
				//buscaEmLargura(graph);
				break;
			case "3":
				//agmUsandoKruskall(graph);
				break;
			case "4":
				//int v[] = scannerFunction();
	        	//caminhoMaisCurto(this.graph, this.graph.vertices().get(v[0]), this.graph.vertices().get(v[1]));
				break;
			case "5":
				//custoDoCaminho();
				break;
		}
	}
	
	public int[] scannerFunction() {
		int answer[] = new int[2];
		Scanner ask = new Scanner(System.in);
		System.out.println("Digite o vertice de origem:");
		if(ask.hasNextLine()) {
		    String origemInput = ask.nextLine();
		    System.out.println("Digite o vertice de destino:");
		    if(ask.hasNextLine()) {
		        String destinoInput = ask.nextLine();
		        int origem = Integer.parseInt(origemInput);
		        int destino = Integer.parseInt(destinoInput);

		        //checks whether the inserted vertices are valid
		        if(origem >= 0 && origem < this.graph.vertices().size() && destino >= 0 && destino < this.graph.vertices().size()) {
		            answer[0] = origem;
		            answer[1] = destino;
		        }
		        else {
		            System.out.println("Vertice invalidos.");
		        }
		    }
		    else {
		        System.out.println("Entrada invalida para o vertice de destino.");
		    }
		}
		else {
		    System.out.println("Entrada invalida para o vertice de origem.");
		}

		ask.close();

		return answer;
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
