package br.com.robson.costa.algoritmo.dijkstra.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import br.com.robson.costa.algoritmo.dijkstra.model.Grafo;
import br.com.robson.costa.algoritmo.dijkstra.service.AlgoritmoDeDijkstraService;

/**
 * 19 de set de 2020
 * @author robson.costa
 */
public class JanelaPrincipal extends JPanel {
	
	
	private Grafo grafo;
    private GrafoPanel graphPanel;
    
    public JanelaPrincipal() {
    	super.setLayout(new BorderLayout());
    	this.setBackground(Color.getHSBColor(9f, 57f, 49f));
        setGraphPanel();
    }
	
    private void setGraphPanel(){
        grafo = new Grafo();
        graphPanel = new GrafoPanel(grafo);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
        setTopPanel();
        setButtons();
    }
    
    private void setTopPanel() {
        JLabel info = new JLabel("Simulador de menor caminho, usando o Algoritmo de Dijkstra - By -> Robson H. R. Costa ");
        info.setForeground(new Color(230, 220, 250));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(130, 50, 250));
        panel.add(info);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(panel, BorderLayout.NORTH);
    }
    
    private void setButtons(){
        JButton start = new JButton();
        setupIcon(start, "start");
        JButton reset = new JButton();
        setupIcon(reset, "reset");
        final JButton info = new JButton();
        setupIcon(info, "info");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(DrawUtils.parseColor("#DDDDDD"));
        buttonPanel.add(reset);
        buttonPanel.add(start);
        buttonPanel.add(info);

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.reset();
            }
        });

        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Clique em um espaço vazio para criar um NOVO NÓ! \n" +
                        "Arraste de um nó a outro para criar uma ARESTA! \n" +
                        "Clique nas arestas para definir o PESO! \n\n" +
                        
                        "COMBINAÇÕES/COMANDOS: \n" +
                        "Shift + Clique Esquerdo 	:	Define um nó como a ORIGEM! \n" +
                        "Shift + Clique Direito 	:	Define um nó como o DESTINO! \n" +
                        "Ctrl  + Arrastar       	: 	Reposiciona o nó! \n" +
                        "Ctrl  + Clique         	:   Obtém o caminho do nó! \n" +
                        "Ctrl  + Shift + Clique 	:	Deleta o Nó/Aresta \n");
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlgoritmoDeDijkstraService algoritmoDeDijkstraService = new AlgoritmoDeDijkstraService(grafo);
                try{
                    algoritmoDeDijkstraService.run();
                    graphPanel.setCaminho(algoritmoDeDijkstraService.getCaminhoDoDestino());
                } catch (IllegalStateException ise){
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupIcon(JButton button, String img){
        try {
            Image icon = ImageIO.read(getClass().getResource(
                    "/icones/" + img + ".png"));
            ImageIcon imageIcon = new ImageIcon(icon);
            button.setIcon(imageIcon);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
