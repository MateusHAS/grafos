package trabalho_grafos;

/**
 *
 * @author mateus
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GrafoOrientado {
    
    private final List<Vertice> vertices;
    private final List<Aresta> arestas;
    private boolean hasCycle = false;
    
    public boolean isHasCycle() {
	return hasCycle;
    }

    public void setHasCycle(boolean hasCycle) {
	this.hasCycle = hasCycle;
    }
    
    public class Vertice {
        String nome;
        Boolean visitado = false;
        List<Aresta> adjacentes;

        Vertice(String nome) {
            this.nome = nome;
            this.adjacentes = new ArrayList<Aresta>();
        }

        void addAdj(Aresta e) {
            adjacentes.add(e);
        }

        public Boolean getVisitado() {
            return visitado;
        }

        public void setVisitado(Boolean visitado) {
            this.visitado = visitado;
        }
        
        
    }

    public class Aresta {
        Vertice origem;
        Vertice destino;

        Aresta(Vertice origem, Vertice destino) {
            this.origem = origem;
            this.destino = destino;
        }
    }

    public GrafoOrientado() {
        vertices = new ArrayList<Vertice>();
        arestas = new ArrayList<Aresta>();
    }

    Vertice addVertice(String nome) {
        Vertice v = new Vertice(nome);
        vertices.add(v);
        return v;
    }

    Aresta addAresta(Vertice origem, Vertice destino) {
        Aresta e = new Aresta(origem, destino);
        origem.addAdj(e);
        arestas.add(e);
        return e;
    }
    
    public void ImprimeGrafo(GrafoOrientado grafo){
        int a, b, tamAre, tam = grafo.vertices.size();
        for(a = 0; a < tam; a++){
            tamAre = grafo.vertices.get(a).adjacentes.size();
            System.out.print(""+grafo.vertices.get(a).nome+ " -> ");
            for(b=0; b < tamAre; b++){
                System.out.print(""+grafo.vertices.get(a).adjacentes.get(b).destino.nome);
                if(b+1 < tamAre){
                     System.out.print(" ,");
                }                
            }
            System.out.print("\n");
        }
    }
    
    public boolean possuiCiclo(GrafoOrientado grafo){
        int n = grafo.vertices.size();
        int x, y, aux;
        Vertice vert;
        while(n > 0){
            for(aux = n-1; n > 0; n--){
                int tam = grafo.vertices.get(n-1).adjacentes.size();
                for(x = 0; x < tam; x++){
                    int a = 0;
                     vert = grafo.vertices.get(n-1).adjacentes.get(x).destino;
                     while(vert != grafo.vertices.get(a)){
                         a++;
                     }
                     int tamAresta = grafo.vertices.get(a).adjacentes.size();
                     for(y = 0; y < tamAresta; y++){
                         if((grafo.vertices.get(a).adjacentes.get(y).destino) == (grafo.vertices.get(n-1))){
                             grafo.hasCycle = true;
                             return true;
                         }
                     }
                }
            }              
        }
    return false;
    }
    
    public void DFS(Vertice v, ArrayList<Vertice> l) {
    	Vertice visinho;
        int x;
        v.setVisitado(true);
    	
        for(x=0; x < v.adjacentes.size(); x++){
            visinho = v.adjacentes.get(x).destino;
            if(!visinho.visitado){
    		DFS(visinho, l);
            }
    	}
    	
    	l.add(v);
    }
    
    public ArrayList<Vertice> topologicalSort() {
    	ArrayList<Vertice> order = new ArrayList<Vertice>();
    	if(this.hasCycle)
    		System.out.println("Nao e possivel obter uma ordenacao topologica, pois este grafo possui ciclo(s)");
    	for(Vertice v:vertices){
    		if(!v.visitado){
                    DFS(v, order);
                }
    	}
    	Collections.reverse(order);
    	return order;
    }
    
    public void ImprimiOrd(ArrayList<Vertice> v){
        int x, aux = v.size();
        System.out.print("Uma das ordenacao topologica desse grafo eh: \n");
        for(x = 0; x < aux; x++){
           System.out.print("" +v.get(x).nome); 
           if(x+1 < aux){
               System.out.print(" ,");
           }
        }   
    }
    
    public boolean ComponenteForteConexo(GrafoOrientado gra, Vertice s, Vertice t){ 
       int x, numVert = gra.vertices.size(),
              numAdj = s.adjacentes.size(); 
       for (x = 0; x < numVert; x++){
          gra.vertices.get(x).visitado = false;
       }
       for(x = 0; x < numAdj; x++){
           s.adjacentes.get(x).destino.visitado = true;
       }
       return t.visitado != false;
    }


    public static void main(String[] args) {
        ArrayList<Vertice> ord_topologica = new ArrayList<Vertice>();
        GrafoOrientado g = new GrafoOrientado();
        Vertice a = g.addVertice("a");
        Vertice b = g.addVertice("b");
        Vertice c = g.addVertice("c");
        Vertice d = g.addVertice("d");
        Vertice e = g.addVertice("e");
        Vertice f = g.addVertice("f");
        Vertice h = g.addVertice("h");
        Aresta ad = g.addAresta(a, d);
        Aresta ae = g.addAresta(a, e);
        Aresta ah = g.addAresta(a, h);
        Aresta bc = g.addAresta(b, c);
        Aresta be = g.addAresta(b, e);
        Aresta bf = g.addAresta(b, f);
        Aresta cd = g.addAresta(c, d);
        Aresta ce = g.addAresta(c, e);
        Aresta de = g.addAresta(d, e);
        Aresta ef = g.addAresta(e, f);
        Aresta eh = g.addAresta(e, h);
        //Aresta he = g.addAresta(h, e); //caso de teste fortemente conexo
        Aresta fh = g.addAresta(f, h);
        //Aresta hf = g.addAresta(h, f); //caso de teste com ciclo
        
        g.ImprimeGrafo(g);
        g.possuiCiclo(g);
        
        if(g.isHasCycle()== true){
            System.out.println("O grafo possue ciclo! \n");
        }else{
            System.out.println("O grafo nao possue ciclo! \n");
        }
        ord_topologica = g.topologicalSort();
        g.ImprimiOrd(ord_topologica);
        
        if(g.ComponenteForteConexo(g, h, e)){
             System.out.println("\n\nExiste componente fortemente conexo nesse grafo!");
        }else{
            System.out.println("\n\nNao existe componente fortemente conexo nesse grafo!");
        }
        
        
    }
}

