/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grafos;

/**
 *
 * @author humberto e douglas
 */
public class Aresta {

    private Vertice origem;
    private Vertice destino;
    private double peso;
    private int id;
    
    public Aresta( Vertice origem, Vertice destino, int id ){
        this.origem = origem;
        this.destino = destino;
        this.peso = 1;
        this.id = id;
    }
    
    public Aresta( Vertice origem, Vertice destino, int id, double peso ){
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
        this.id = id;
    }
    
    public int getId() {
    	return this.id;
    }

    public Vertice origem() {
        return origem;
    }

    public void setarOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice destino() {
        return destino;
    }

    public void setarDestino(Vertice destino) {
        this.destino = destino;
    }

    public double peso() {
        return peso;
    }

    public void setarPeso(double peso) {
        this.peso = peso;
    }
    
}
