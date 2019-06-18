package trabalho_grafos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateus
 */
public class GrafoNaoOrientado {
    
    private final List<Vertice> vertices;
    private final List<Aresta> arestas;
    
    public class Vertice {
        String nome;
        String cor = "branco";
        int low = 0;
        int tempDesc = 0,
            tempFinal= 0;
        List<Aresta> adj;

        Vertice(String nome) {
            this.nome = nome;
            this.adj = new ArrayList<Aresta>();
        }

        void addAdj(Aresta e) {
            adj.add(e);
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

    public GrafoNaoOrientado() {
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
    
    public void ImprimeGrafo(GrafoNaoOrientado grafo){
        int a, b, tamAre, tam = grafo.vertices.size();
        for(a = 0; a < tam; a++){
            tamAre = grafo.vertices.get(a).adj.size();
            System.out.print(""+grafo.vertices.get(a).nome+ " -> ");
            for(b=0; b < tamAre; b++){
                System.out.print(""+grafo.vertices.get(a).adj.get(b).destino.nome);
                if(b+1 < tamAre){
                     System.out.print(" ,");
                }                
            }
            System.out.print("\n");
        }
    }
    
    public void ImprimePontes(GrafoNaoOrientado grafo){
        int a, b, tamAre, tam = grafo.vertices.size();
        for(a = 0; a < tam; a++){
            tamAre = grafo.vertices.get(a).adj.size();
            System.out.print("\nVertice: "+grafo.vertices.get(a).nome);
            System.out.print("\nLow: "+grafo.vertices.get(a).low);
            System.out.print("\nTempDesc: "+grafo.vertices.get(a).tempDesc+"\n");
        }
    }
    
    public static int n;
    
    private void Pontes(Vertice v){
        int y, z, m;
        boolean visit = false;
        v.tempDesc = n;
        v.cor = "cinza";
        v.low = v.tempDesc;
        n++;
        
        for(y = 0; y < v.adj.size(); y++){
            if("branco".equals(v.adj.get(y).destino.cor)){
                Pontes(v.adj.get(y).destino);

            }                                   
        }     
        for(Vertice u:vertices){
            for(m = 0; m < u.adj.size(); m++){
                visit = "preto".equals(u.adj.get(m).destino.cor);
            }
        }
         
        if(visit == true){
            for(Vertice u:vertices){
               for(z = 0; z < u.adj.size(); z++){
                   if("preto".equals(u.cor)){
                        if((u.adj.get(z).destino.tempDesc < u.tempDesc) && (u.tempFinal < u.adj.get(z).destino.tempFinal)){
                                u.low = u.adj.get(z).destino.low;
                            }                   
                   }
                } 
            }
            if(visit == true && "a".equals(v.nome)){
                for(Vertice u:vertices){
                   for(z = 0; z < u.adj.size(); z++){
                        if(u.adj.get(z).destino.low > u.tempDesc){
                                       System.out.print("\nA aresta "+u.nome+ " -> "+u.adj.get(z).destino.nome+ " eh ponte");
                                   }
                   }
                }
            }
        }
        v.cor = "preto";
        v.tempFinal = n;
        n++;

    }
    
        private void PontoArticulacao(Vertice v){
        int z;
            for(Vertice u:vertices){
                for(z = 0; z < u.adj.size(); z++){
                    if(u.adj.get(z).destino.low >= u.tempDesc){
                        System.out.print("\nO vertice "+u.nome+ " eh ponto de articulacao");
                    }
                }
            }
        }



    public static void main(String[] args) {
        GrafoNaoOrientado g = new GrafoNaoOrientado();
        n = 1;
        Vertice a = g.addVertice("a");
        Vertice b = g.addVertice("b");
        Vertice c = g.addVertice("c");
        Vertice d = g.addVertice("d");
        Vertice e = g.addVertice("e");
        Aresta ab = g.addAresta(a, b);
        Aresta ac = g.addAresta(a, c);
        Aresta ba = g.addAresta(b, a);       
        Aresta ca = g.addAresta(c, a);
        Aresta cd = g.addAresta(c, d);
        Aresta ce = g.addAresta(c, e);
        Aresta dc = g.addAresta(d, c);
        Aresta de = g.addAresta(d, e);
        Aresta ec = g.addAresta(e, c);
        Aresta ed = g.addAresta(e, d);
        
        g.ImprimeGrafo(g);
        
        g.Pontes(a);
        
        g.ImprimePontes(g);

        g.PontoArticulacao(a);

        
    }
}