package graphicInterface;

import grafos.*;
import java.util.Scanner;
import graphsFunctions.*;

public class ProgramExecution {
	public static void main(String args[]) throws Exception {
		GraphAlgorithms ga = new GraphAlgorithms();
		TipoDeRepresentacao tr = null;
		
		//ask the file containing graph and the type of representation
		//C:\Users\cferr\workspace\6-periodo\projeto_analise_algoritmos\Grafos\src\grafos\teste.txt
		Scanner scn = new Scanner(System.in);
		System.out.println("Digite o caminho para o arquivo:");
		String file = scn.nextLine();
		System.out.println("Escolha um tipo de representacao:\n[1]MATRIZ_DE_ADJACENCIA\n[2]MATRIZ_DE_INCIDENCIA\n[3]LISTA_DE_ADJACENCIA");
		String type = scn.nextLine();
		
		file = "C:\\Users\\cferr\\workspace\\6-periodo\\projeto_analise_algoritmos\\Grafos\\src\\grafos\\teste.txt";

			
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
		
		ga.carregarGrafo(file, tr);
	}
}
