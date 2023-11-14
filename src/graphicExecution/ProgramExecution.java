package graphicExecution;

import grafos.*;
import java.util.Scanner;
import graphsFunctions.*;

public class ProgramExecution {
	public static void main(String args[]) throws Exception {
		GraphAlgorithms ga = new GraphAlgorithms();
		TipoDeRepresentacao tr = null;
		
		//ask the file containing graph and the type of representation
		Scanner scn = new Scanner(System.in);
		System.out.println("Digite o caminho para o arquivo:");
		String file = scn.nextLine();
		System.out.println("Escolha um tipo de representacao:\n[1]MATRIZ_DE_ADJACENCIA\n[2]MATRIZ_DE_INCIDENCIA\n[3]LISTA_DE_ADJACENCIA");
		String type = scn.nextLine();
					
		switch(type) {
			case "1":
				tr = TipoDeRepresentacao.MATRIZ_DE_ADJACENCIA;
				break;
			case "2":
				tr = TipoDeRepresentacao.MATRIZ_DE_INCIDENCIA;
				break;
			case "3":
				tr = TipoDeRepresentacao.LISTA_DE_ADJACENCIA;
				break;
		}
		Grafo grafo = ga.carregarGrafo(file, tr);
		
		//ask the function to run
		String function = null;
		System.out.println("Escolha a função a ser executada:\n[1]Busca em profundidade\n[2]Busca em largura\n[3]Árvore geradora mínima\n[4]Caminho mínimo\n[5]Fluxo máximo\n[0]Encerra execucao");
		function = scn.nextLine();
		ga.forwardFunction(function);
		scn.close();
		System.out.println("Fim da execucao");
	}
}
