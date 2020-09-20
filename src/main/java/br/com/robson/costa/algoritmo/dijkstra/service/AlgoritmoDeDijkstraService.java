package br.com.robson.costa.algoritmo.dijkstra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import br.com.robson.costa.algoritmo.dijkstra.model.Aresta;
import br.com.robson.costa.algoritmo.dijkstra.model.Grafo;
import br.com.robson.costa.algoritmo.dijkstra.model.Noh;

/**
 * 19 de set de 2020
 * 
 * @author robson.costa
 */
public class AlgoritmoDeDijkstraService {

	private boolean isSafe = false;
	private String message = null;

	private Grafo grafo;
	private Map<Noh, Noh> antecessores;
	private Map<Noh, Integer> distancias;

	private PriorityQueue<Noh> nohsNaoVisitados;
	private HashSet<Noh> nohsVisitados;

    public class NodeComparator implements Comparator<Noh>  {
        @Override
        public int compare(Noh nohUm, Noh nohDois) {
            return distancias.get(nohUm) - distancias.get(nohDois);
        }
    };
    
    
    public AlgoritmoDeDijkstraService(Grafo grafo) {
        this.grafo = grafo;
        antecessores = new HashMap<>();
        distancias = new HashMap<>();

        for(Noh noh : grafo.getListNoh()) {
            distancias.put(noh, Integer.MAX_VALUE);
        }
        nohsVisitados = new HashSet<>();

        isSafe = verificaSeEstaSeguro();	
   }
    
   
    private boolean verificaSeEstaSeguro(){
        if(grafo.getOrigem() == null){
            this.message = "A origem PRECISA estar presente no grafo";
            return false;
        }

        if(grafo.getDestino() == null){
        	this.message = "O destino PRECISA estar presente no grafo";
            return false;
        }

        for(Noh noh : grafo.getListNoh()){
            if(!grafo.isNohAlcancavel(noh)) {
            	this.message = "O grafo contém nós inacessiveis !";
                return false;
            }
        }
        return true;
    }

    public void run() throws IllegalStateException {
        if(!isSafe) {
            throw new IllegalStateException(this.message);
        }

        this.nohsNaoVisitados = new PriorityQueue<>(grafo.getListNoh().size(), new NodeComparator());

        Noh origem = grafo.getOrigem();
        distancias.put(origem, 0);
        nohsVisitados.add(origem);

        for (Aresta vizinho : getVizinhos(origem)){
            Noh nohAdjacente = getAdjacente(vizinho, origem);
            if(nohAdjacente == null)
                continue;

            distancias.put(nohAdjacente, vizinho.getPeso());
            antecessores.put(nohAdjacente, origem);
            nohsNaoVisitados.add(nohAdjacente);
        }

        while (!nohsNaoVisitados.isEmpty()){
        	Noh nohAtual = nohsNaoVisitados.poll();

            updateDistancia(nohAtual);

            nohsNaoVisitados.remove(nohAtual);
            nohsVisitados.add(nohAtual);
        }

        for(Noh noh : grafo.getListNoh()) {
            noh.setCaminho(getCaminho(noh));
        }

        grafo.setResolvido(true);
    }
    
    private void updateDistancia(Noh noh) {
        int distancia = distancias.get(noh);

        for (Aresta vizinho : getVizinhos(noh)){
            Noh nohAdjacente = getAdjacente(vizinho, noh);
            if(nohsVisitados.contains(nohAdjacente))
                continue;

            int distanciaAtual = distancias.get(nohAdjacente);
            int newDistancia = distancia + vizinho.getPeso();

            if(newDistancia < distanciaAtual) {
                distancias.put(nohAdjacente, newDistancia);
                antecessores.put(nohAdjacente, noh);
                nohsNaoVisitados.add(nohAdjacente);
            }
        }
    }
    

    private Noh getAdjacente(Aresta aresta, Noh noh) {
        if(aresta.getNohUm() != noh && aresta.getNohDois() != noh) {
            return null;
        }
        return noh == aresta.getNohDois() ? aresta.getNohUm() : aresta.getNohDois();
    }

    
    private List<Aresta> getVizinhos(Noh noh) {
        List<Aresta> vizinhos = new ArrayList<>();

        for(Aresta aresta : grafo.getListAresta()){
            if(aresta.getNohUm() == noh || aresta.getNohDois() == noh)
                vizinhos.add(aresta);
        }

        return vizinhos;
    }
    
    public Integer getDistanciaDoDestino(){
        return distancias.get(grafo.getDestino());
    }
    
    public Integer getDistancia(Noh node){
        return distancias.get(node);
    }
    
    public List<Noh> getCaminhoDoDestino() {
        return getCaminho(grafo.getDestino());
    }

    public List<Noh> getCaminho(Noh node){
        List<Noh> caminho = new ArrayList<>();

        Noh nohAtual = node;
        caminho.add(nohAtual);
        while (nohAtual != grafo.getOrigem()) {
            nohAtual = antecessores.get(nohAtual);
            caminho.add(nohAtual);
        }

        Collections.reverse(caminho);

        return caminho;
    }
    
    
}
