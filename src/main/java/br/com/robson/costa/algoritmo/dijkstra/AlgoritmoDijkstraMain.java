package br.com.robson.costa.algoritmo.dijkstra;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

import br.com.robson.costa.algoritmo.dijkstra.view.JanelaPrincipal;

/**
 * 19 de set de 2020 robson.costa
 * 
 */
public class AlgoritmoDijkstraMain {

	public static void main(String[] args) {

		try {
//          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception ex) {
			System.err.println("Deu ruim ->" +ex.getStackTrace());
		}

		JFrame j = new JFrame();
		j.setTitle("Algoritmo de Dijkstra - Robson Henrique R. Costa - PEO");

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setSize(new Dimension(900, 600));
		j.add(new JanelaPrincipal());
		j.setVisible(true);
		
//		j.setBackground(Color.cyan);
	}

}