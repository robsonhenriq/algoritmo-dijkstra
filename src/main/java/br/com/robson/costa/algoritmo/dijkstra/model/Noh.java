package br.com.robson.costa.algoritmo.dijkstra.model;

import java.awt.Point;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 19 de set de 2020
 * robson.costa
 * 
 * "Nó" / Node
 */
//@Getter
//@Setter
public class Noh {
	  
	@Getter private Point coordenadas = new Point();
	@Getter @Setter private int id;
	@Getter @Setter private List<Noh> caminho;

    public Noh(){}

    public Noh(int id){
        this.id = id;
    }

    public Noh(Point p){
        this.coordenadas = p;
    }
    
    public void setCoordenadas(int x, int y) {
    	coordenadas.setLocation(x, y);
    }
    
    public int getX(){
        return (int) coordenadas.getX();
    }

    public int getY(){
        return (int) coordenadas.getY();
    }
    
    @Override
    public String toString() {
        return "Nó: " + id;
    }
    
}
