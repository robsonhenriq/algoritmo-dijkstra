package br.com.robson.costa.algoritmo.dijkstra.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 19 de set de 2020
 * robson.costa
 */
@Getter
@Setter
public class Grafo {
	
    private int contador = 1;
    private List<Noh> listNoh = new ArrayList<>();
    private List<Aresta> listAresta = new ArrayList<>();
    
    private Noh origem;
    private Noh destino;

    private boolean resolvido = false;
   
    public boolean isNohAlcancavel(Noh noh) {
    	boolean anyMatch = listAresta.stream().anyMatch(aresta -> aresta.getNohUm() == noh || aresta.getNohDois() == noh);
    	return anyMatch;
    }
    
    public boolean isOrigem(Noh noh) {
    	return noh == origem;
    }
    
    public boolean isDestino(Noh noh) {
		return noh == destino;
	}
    
    /**
     * 
     * @param coordenadas
     */
    public void addNoh(Point coordenadas) {
    	Noh noh = new Noh(coordenadas);
    	addNoh(noh);
    }
    
    /**
     * 
     * @param noh
     */
    public void addNoh(Noh noh) {
    	noh.setId(contador++);
    	this.listNoh.add(noh);
    	if(noh.getId() == 1) {
    		this.origem = noh;
    	}
    }
    
    /**
     * 
     * @param novaAresta
     */
    public void addAresta(Aresta novaAresta) {
    	boolean adicionado = false;
    	
    	for (Aresta aresta : listAresta) {
			if (aresta.equals(novaAresta)) {
				adicionado = true;
				break;
			}
		}
    	
    	if (!adicionado) {
    		this.listAresta.add(novaAresta);
    	}
    }
    
    
    /**
     * 
     * @param noh
     */
    public void deleteNoh(Noh noh) {
		List<Aresta> delete = new ArrayList<>();
		
		listAresta.stream().forEach(aresta -> {
			if (aresta.temNoh(noh)) {
				delete.add(aresta);
			}
		});
		
		for (Aresta aresta : delete) {
			this.listAresta.remove(aresta);
		}
		listNoh.remove(noh);
    }
    
    public void clear() {
    	contador = 1;
    	listNoh.clear();
    	listAresta.clear();
    	resolvido = false;
    	
    	origem = null;
    	destino = null;
    }
    
}
