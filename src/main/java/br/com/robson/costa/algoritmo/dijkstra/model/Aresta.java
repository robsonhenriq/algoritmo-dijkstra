package br.com.robson.costa.algoritmo.dijkstra.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 19 de set de 2020
 * robson.costa
 * 
 * Aresta/Edge
 */
//@Getter
//@Setter
public class Aresta {
	
	@Getter private Noh nohUm;
	@Getter private Noh nohDois;
	@Getter @Setter private int peso = 1;

    public Aresta(Noh nohUm, Noh nohDois){
        this.nohUm = nohUm;
        this.nohDois = nohDois;
    }

    public boolean temNoh(Noh node){
        return nohUm==node || nohDois==node;
    }

    public boolean equals(Aresta aresta) {
        return (nohUm == aresta.nohUm && nohDois ==aresta.nohDois) || (nohUm ==aresta.nohDois && nohDois ==aresta.nohUm) ;
    }

    @Override
    public String toString() {
        return "Aresta ~ "
                + getNohUm().getId() + " - "
                + getNohDois().getId();
    }
	
	
}
