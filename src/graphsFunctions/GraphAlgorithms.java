package graphsFunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

import grafos.*;

public class GraphAlgorithms implements AlgoritmosEmGrafos {
	
	public Grafo graph;
	private int n = 0;
	private int d[] = null;
	private int t[] = null;
	private int predecessor[] = null;
	public static final byte branco = 0;
	public static byte cinza = 1;
	public static byte preto = 2;

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
		
		this.n = graph.numeroDeVertices();
		this.d = new int[n];
		this.t = new int[n];
		this.predecessor = new int[n];

		return null;
	}
	
	//run chosen function
	public void forwardFunction(String function) {
		int vertice;
		switch(function) {
			case "1":
				vertice = scannerFunctionForVertice();
				buscaEmProfundidade(graph, vertice);
				break;
			case "2":
				vertice = scannerFunctionForVertice();
				buscaEmLargura(graph, vertice);
				break;
			case "3":
				//agmUsandoKruskall(graph);
				break;
			case "4":
				int v[] = scannerFunctionForOriginAndDestiny();
	        	caminhoMaisCurto(this.graph, this.graph.vertices().get(v[0]), this.graph.vertices().get(v[1]));
				break;
			case "5":
				//custoDoCaminho();
				break;
		}
	}
	
	//get information from keyboard
	public int scannerFunctionForVertice() {
		int answer = 0;
		Scanner ask = new Scanner(System.in);
		System.out.println("Digite o vertice:");
		if(ask.hasNextLine()) {
		    String v = ask.nextLine();
		    int vertex = Integer.parseInt(v);
		    //checks whether the inserted vertex are valid
	        if(vertex >= 0 && vertex < this.graph.vertices().size()) {
	            answer = vertex;
	        }
	        else {
	            System.out.println("Vertice invalido.");
	        }
		}
		else {
		    System.out.println("Entrada invalida para o vertice.");
		}

		ask.close();
		
		return answer;
	}
	
	//get information from keyboard
	public int[] scannerFunctionForOriginAndDestiny() {
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
		            System.out.println("Vertice invalido.");
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
	public Collection<Aresta> buscaEmLargura(Grafo g, int v) {
		int color[] = new int[this.graph.numeroDeVertices()];
		
		for(int u = v; u < graph.numeroDeVertices(); u++) {
			color[u] = branco;
			this.d[u] = Integer.MAX_VALUE;
			this.predecessor[u] = -1;
		}
		
		for(int u = v; u < graph.numeroDeVertices(); u++) {
			if(color[u] == branco) {
				this.visitBfs(u, color);
			}
		}
		
		//prints discovery time and father of the vertex
		for(int i = v; i < this.graph.numeroDeVertices(); i++) {
			System.out.println("Vertice " + i + "\nTempo de descoberta: " + this.d[i] + "\nPai do vertice: " + this.predecessor[i] + "\n");
		}
		return null;
	}
	
	//obtains the smallest number of edges between the vertex v and every vertex that can be reached
	public void visitBfs(int u, int color[]) {
		color[u] = cinza;
		this.d[u] = 0;
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(u);
		
		while(!queue.isEmpty()) {
			Integer aux = queue.poll();
			u = aux.intValue();
			
			if(!this.graph.adjListEmpty(u)) {
				Aresta a = this.graph.firstAdj(u);
				
				while(a != null) {
					int v = a.destino().id();
					
					if(color[v] == branco) {
						color[v] = cinza;
						this.d[v] = this.d[v] + 1;
						this.predecessor[v] = u;
						queue.add(v);
					}
					a = this.graph.nextAdj(u);
				}
			}
			color[u] = preto;
		}
	}

	@Override
	public Collection<Aresta> buscaEmProfundidade(Grafo g, int v) {
		int tempo = 0;
		int color[] = new int[this.graph.numeroDeVertices()];
		
		for(int u = v; u < graph.numeroDeVertices(); u++) {
			color[u] = branco;
			this.predecessor[u] = -1;
		}
		
		for(int u = v; u < graph.numeroDeVertices(); u++) {
			if(color[u] == branco) {
				tempo = this.visitDfs(u, tempo, color);
			}
		}
		
		//prints discovery and completion times
		for(int i = v; i < this.graph.numeroDeVertices(); i++) {
			System.out.println("Vertice " + i + "\nTempo de descoberta: " + this.d[i] + "\nTempo de finalização: " + this.t[i] + "\n");
		}

		return null;
	}
	
	//visits blank vertex and returns time to DFS
	private int visitDfs(int u, int tempo, int color[]) {
		color[u] = cinza;
		this.d[u] = ++tempo;
		
		if(!this.graph.adjListEmpty(u)) {
			Aresta a = this.graph.firstAdj(u);
			
			while(a != null) {
				int v = a.destino().id();
				
				if(color[v] == branco) {
					this.predecessor[v] = u;
					tempo = this.visitDfs(v, tempo, color);
				}
				a = this.graph.nextAdj(u);
			}
		}
		color[u] = preto;
		this.t[u] = ++tempo;
		
		return tempo;
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
