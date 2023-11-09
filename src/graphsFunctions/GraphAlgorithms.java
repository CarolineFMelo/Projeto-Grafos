package graphsFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

import grafos.*;

public class GraphAlgorithms implements AlgoritmosEmGrafos {
	
	public Grafo graph;
	private int n = 0;
	private int d[] = null;
	private int t[] = null;
	private int tempoDescoberta[];
	private int predecessor[] = null;
	public static final byte branco = 0;
	public static byte cinza = 1;
	public static byte preto = 2;
	public List<Aresta> treeEdges;
	public List<Aresta> returnEdges;
	public List<Aresta> advanceEdges;
	public List<Aresta> crossingEdges;
	private Collection<Aresta> aux = new ArrayList<Aresta>();
	private double[][] capacidade;
    private double[][] fluxo;

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
		this.capacidade = new double[n][n];
	    this.fluxo = new double[n][n];
		
		treeEdges = new ArrayList<>();
		returnEdges = new ArrayList<>();
		advanceEdges = new ArrayList<>();
		crossingEdges = new ArrayList<>();

		return null;
	}
	
	//run chosen function
	public void forwardFunction(String function) throws Exception {
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
				agmUsandoKruskall(graph);
				break;
			case "4":
				int v[] = scannerFunctionForOriginAndDestiny();
	        	caminhoMaisCurto(this.graph, this.graph.vertices().get(v[0]), this.graph.vertices().get(v[1]));
				break;
			case "5":
				int x[] = scannerFunctionForOriginAndDestiny();
				fluxoMaximo(this.graph.vertices().get(x[0]), this.graph.vertices().get(x[1]));
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
	    tempoDescoberta = new int[this.graph.numeroDeVertices()];

	    for(int u = v; u < graph.numeroDeVertices(); u++) {
	        color[u] = branco;
	        tempoDescoberta[u] = -1;
	        this.d[u] = Integer.MAX_VALUE;
	        this.predecessor[u] = -1;
	    }
	    int tempo = 0;

	    for(int u = v; u < graph.numeroDeVertices(); u++) {
	        if(color[u] == branco) {
	            tempo = this.visitBfs(u, color, tempo, tempoDescoberta);
	        }
	    }
	    
	    printBfs(v);

	    return null;
	}

	//obtains the smallest number of edges between the vertex v and every vertex that can be reached and return discovery time
	public int visitBfs(int u, int color[], int tempo, int tempoDescoberta[]) {
	    color[u] = cinza;
	    this.d[u] = 0;
	    Queue<Integer> queue = new LinkedList<Integer>();
	    queue.add(u);

	    while(!queue.isEmpty()) {
	        Integer aux = queue.poll();
	        u = aux.intValue();
	        tempoDescoberta[u] = tempo;
	        tempo++;

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

	    return tempo;
	}
	
	public void printBfs(int v) {
		for(int i = v; i < this.graph.numeroDeVertices(); i++) {
	        System.out.println("Vertice " + i + "\nTempo de descoberta: " + tempoDescoberta[i] + "\nPai do vertice: " + this.predecessor[i] + "\n");
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
		
		printDfs(v);
		
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
					treeEdges.add(a);
					this.predecessor[v] = u;
					tempo = this.visitDfs(v, tempo, color);
				}
				else if(color[v] == cinza) {
					returnEdges.add(a);
                } 
				else {
                    if(d[u] < d[v]) {
                    	advanceEdges.add(a);
                    } else {
                    	crossingEdges.add(a);
                    }
                }
				
				a = this.graph.nextAdj(u);
			}
		}
		color[u] = preto;
		this.t[u] = ++tempo;
		
		return tempo;
	}
	
	public void printDfs(int v) {
		//prints discovery and completion times
		for(int i = v; i < this.graph.numeroDeVertices(); i++) {
			System.out.println("Vertice " + i + "\nTempo de descoberta: " + this.d[i] + "\nTempo de finalização: " + this.t[i] + "\n");
		}
				
		if(treeEdges != null) {
			for (Aresta aresta : treeEdges) {
				System.out.println("Aresta de arvore: " + aresta.origem().id() + " - " + aresta.destino().id());
			}
		}
				
		if(returnEdges != null) {
			for (Aresta aresta : returnEdges) {
				System.out.println("Aresta de retorno: " + aresta.origem().id() + " - " + aresta.destino().id());
			}
		}
				
		if(advanceEdges != null) {
			for (Aresta aresta : advanceEdges) {
				System.out.println("Aresta de avanco: " + aresta.origem().id() + " - " + aresta.destino().id());
			}
		}
				
		if(crossingEdges != null) {
			for (Aresta aresta : crossingEdges) {
				System.out.println("Aresta de cruzamento: " + aresta.origem().id() + " - " + aresta.destino().id());
			}	
		}
	}

	@Override
	public ArrayList<Aresta> menorCaminho(Grafo g, Vertice origem, Vertice destino) throws Exception {
		Queue<Vertice> queue = new LinkedList<>();
        Map<Vertice, Aresta> arestaAnterior = new HashMap<Vertice, Aresta>();
        Set<Vertice> visited = new HashSet<>();

        queue.add(origem);
        visited.add(origem);

        while (!queue.isEmpty()) {
            Vertice atual = queue.poll();
            if (atual.equals(destino)) {
                break;
            }

            for (Vertice vizinho : graph.adjacentesDe(atual)) {
                if (!visited.contains(vizinho)) {
                	queue.add(vizinho);
                	visited.add(vizinho);
                    arestaAnterior.put(vizinho, new Aresta(g.vertices().get(atual.id()), g.vertices().get(vizinho.id()), 0));
                }
            }
        }

        ArrayList<Aresta> minimumRoad = new ArrayList<>();
        Vertice verticeAtual = destino;

        while (arestaAnterior.containsKey(verticeAtual)) {
            Aresta aresta = arestaAnterior.get(verticeAtual);
            minimumRoad.add(aresta);
            verticeAtual = new Vertice(aresta.origem().id());
        }

        Collections.reverse(minimumRoad);
        
        for (Aresta a : minimumRoad) {
            System.out.println("Aresta: Origem: " + a.origem().id() + " Destino: " + a.destino().id());
        }
        
        return minimumRoad;
	}

	@Override
	public boolean existeCiclo(Grafo g) {
		Collection<Aresta> aux = new ArrayList<Aresta>();
		aux = arestasDeRetorno(g);
		if(aux != null) {
			return true;
		}
		return false;
	}

	@Override
	public Collection<Aresta> agmUsandoKruskall(Grafo g) throws Exception {
		List<Aresta> todasArestas = new ArrayList<>(g.arestas());
        todasArestas.sort(Comparator.comparingDouble(a -> a.peso()));

        Set<Set<Vertice>> componentesConexas = new HashSet<>();
        Collection<Aresta> agm = new ArrayList<>();

        for (Aresta aresta : todasArestas) {
            Vertice origem = new Vertice(aresta.origem().id());
            Vertice destino = new Vertice(aresta.destino().id());

            Set<Vertice> componenteOrigem = encontreComponenteConexa(componentesConexas, origem);
            Set<Vertice> componenteDestino = encontreComponenteConexa(componentesConexas, destino);

            if (!componenteOrigem.equals(componenteDestino)) {
                agm.add(aresta);
                componenteOrigem.addAll(componenteDestino);
                componentesConexas.remove(componenteDestino);
            }
        }

        for (Aresta a : agm) {
            System.out.println("Aresta: Origem: " + a.origem().id() + " Destino: " + a.destino().id());
        }

        return agm;
	}
	
	private Set<Vertice> encontreComponenteConexa(Set<Set<Vertice>> componentesConexas, Vertice v) {
        for (Set<Vertice> componente : componentesConexas) {
            if (componente.contains(v)) {
                return componente;
            }
        }

        Set<Vertice> novaComponente = new HashSet<>();
        novaComponente.add(v);
        componentesConexas.add(novaComponente);
        
        return novaComponente;
    }

	@Override
	public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception {
		double total = 0.0;

		for(Aresta aresta : arestas) {
			total += aresta.peso();
		}
		
		System.out.println("Custo total = " + total);

		return total;
	}

	@Override
	public boolean ehArvoreGeradora(Grafo g, Collection<Aresta> arestas) {
	    Set<Vertice> vertexTree = new HashSet<>();
	    for (Aresta aresta : arestas) {
	        vertexTree.add(aresta.origem());
	        vertexTree.add(aresta.destino());
	    }

	    if (vertexTree.size() != g.numeroDeVertices()) {
	        return false;
	    }

	    Set<Vertice> visited = new HashSet<>();
	    for (Aresta aresta : arestas) {
	        Vertice origem = aresta.origem();
	        Vertice destino = aresta.destino();

	        if (visited.contains(origem) && visited.contains(destino)) {
	            return false;
	        }

	        visited.add(origem);
	        visited.add(destino);
	    }

	    return true;
	}

	@Override
	public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) {
		int numVertices = graph.numeroDeVertices();
        List<Aresta> arestas = graph.arestas();

        double[] distancia = new double[numVertices];
        Arrays.fill(distancia, Double.MAX_VALUE);
        distancia[origem.id()] = 0.0;

        Aresta[] arestaAnterior = new Aresta[numVertices];

        PriorityQueue<Vertice> filaDePrioridade = new PriorityQueue<>(Comparator.comparingDouble(v -> distancia[v.id()]));
        filaDePrioridade.offer(origem);

        while (!filaDePrioridade.isEmpty()) {
            Vertice u = filaDePrioridade.poll();

            for (Aresta aresta : arestas) {
                if (aresta.origem() == graph.vertices().get(u.id())) {
                    int v = aresta.destino().id();
                    double pesoAresta = aresta.peso();

                    if (distancia[v] > distancia[u.id()] + pesoAresta) {
                        distancia[v] = distancia[u.id()] + pesoAresta;
                        arestaAnterior[v] = aresta;

                        filaDePrioridade.offer(new Vertice(v));
                    }
                }
            }
        }

        ArrayList<Aresta> minimumRoad = new ArrayList<>();
        int v = destino.id();
        while (arestaAnterior[v] != null) {
        	minimumRoad.add(arestaAnterior[v]);
            v = arestaAnterior[v].origem().id();
        }
        Collections.reverse(minimumRoad);

        for (Aresta a : minimumRoad) {
            System.out.println("Aresta: Origem: " + a.origem().id() + " Destino: " + a.destino().id());
        }
        
        return minimumRoad;
	}

	@Override
	public double custoDoCaminho(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) throws Exception {
		double total = 0;
        int atual = origem.id();

        for (Aresta aresta : arestas) {
            if (aresta.origem().id() == atual) {
                total += aresta.peso();
                atual = aresta.destino().id();
            }
        }

        if (atual != destino.id()) {
            throw new IllegalArgumentException("O destino do caminho não corresponde ao vertice de destino fornecido.");
        }
        
        System.out.println("Custo total do caminho = " + total);

        return total;
	}

	@Override
	public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
		int atual = origem.id();

	    for(Aresta aresta : arestas) {
		    if(aresta.origem().id() == atual) {
		    	atual = aresta.destino().id();
		    } 
		    else {
		    	return false;
		    }
	    }

	    return atual == destino.id();
	}

	@Override
	public Collection<Aresta> arestasDeArvore(Grafo g) {
		aux = buscaEmProfundidade(g, 0);
	    return treeEdges;
	}

	@Override
	public Collection<Aresta> arestasDeRetorno(Grafo g) {
		aux = buscaEmProfundidade(g, 0);
        return returnEdges;
	}

	@Override
	public Collection<Aresta> arestasDeAvanco(Grafo g) {
		aux = buscaEmProfundidade(g, 0);
        return advanceEdges;
	}

	@Override
	public Collection<Aresta> arestasDeCruzamento(Grafo g) {
		aux = buscaEmProfundidade(g, 0);
		return crossingEdges;
	}
	
	public double fluxoMaximo(Vertice origem, Vertice destino) {
		double fluxoMaximo = 0;

		while(true) {
			double[] caminho = encontrarCaminho(origem, destino);

			if(caminho == null) {
				break;
			}

			double capacidadeMinima = Double.MAX_VALUE;

			for(int v = destino.id(); v != origem.id(); v = (int) caminho[v]) {
				int u = (int) caminho[v];
				capacidadeMinima = Math.min(capacidadeMinima, capacidade[u][v] - fluxo[u][v]);
			}

			for(int v = destino.id(); v != origem.id(); v = (int) caminho[v]) {
				int u = (int) caminho[v];
				fluxo[u][v] += capacidadeMinima;
				fluxo[v][u] -= capacidadeMinima;
			}

			fluxoMaximo += capacidadeMinima;
		}
		
		System.out.println("Fluxo maximo: " + fluxoMaximo);

		return fluxoMaximo;
	}

	private double[] encontrarCaminho(Vertice origem, Vertice destino) {
		boolean[] visited = new boolean[graph.numeroDeVertices()];
		double[] pai = new double[graph.numeroDeVertices()];
		Queue<Integer> fila = new LinkedList<>();
		fila.offer(origem.id());
		visited[origem.id()] = true;

		while(!fila.isEmpty()) {
			int u = fila.poll();

			for(int v = 0; v < graph.numeroDeVertices(); v++) {
				if(!visited[v] && capacidade[u][v] - fluxo[u][v] > 0) {
					pai[v] = u;
					visited[v] = true;
					fila.offer(v);

					if(v == destino.id()) {
						return pai;
					}
				}
			}
		}

		return null;
	}

}
