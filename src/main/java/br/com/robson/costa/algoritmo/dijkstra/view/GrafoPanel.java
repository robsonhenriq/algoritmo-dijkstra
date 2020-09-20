package br.com.robson.costa.algoritmo.dijkstra.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.robson.costa.algoritmo.dijkstra.model.Aresta;
import br.com.robson.costa.algoritmo.dijkstra.model.Grafo;
import br.com.robson.costa.algoritmo.dijkstra.model.Noh;

/**
 * 20 de set de 2020
 * 
 * @author robson.costa
 */
public class GrafoPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -1471918682492685756L;

	private DrawUtils drawUtils;

	private Grafo grafo;

	private Noh nohSelecionado = null;
	private Noh nohHovered = null;
	private Aresta ArestaHovered = null;

	private List<Noh> listCaminhos = null;

	private Point cursor;

	public GrafoPanel(Grafo grafo) {
		this.grafo = grafo;

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setCaminho(List<Noh> caminhos) {
		this.listCaminhos = caminhos;
		ArestaHovered = null;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		drawUtils = new DrawUtils(graphics2d);

		if (grafo.isResolvido()) {
			drawUtils.drawPath(listCaminhos);
		}

		if (nohSelecionado != null && cursor != null) {
			Aresta aresta = new Aresta(nohSelecionado, new Noh(cursor));
			drawUtils.drawEdge(aresta);
		}

		for (Aresta aresta : grafo.getListAresta()) {
			if (aresta == ArestaHovered)
				drawUtils.drawHoveredEdge(aresta);
			drawUtils.drawEdge(aresta);
		}

		for (Noh noh : grafo.getListNoh()) {
			if (noh == nohSelecionado || noh == nohHovered)
				drawUtils.drawHalo(noh);
			if (grafo.isOrigem(noh))
				drawUtils.drawSourceNode(noh);
			else if (grafo.isDestino(noh))
				drawUtils.drawDestinationNode(noh);
			else
				drawUtils.drawNode(noh);
		}
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

		Noh nohSelecionado = null;
		for (Noh node : grafo.getListNoh()) {
			if (DrawUtils.isWithinBounds(evt, node.getCoordenadas())) {
				nohSelecionado = node;
				break;
			}
		}

		if (nohSelecionado != null) {
			if (evt.isControlDown() && evt.isShiftDown()) {
				grafo.deleteNoh(nohSelecionado);
				grafo.setResolvido(false);
				repaint();
				return;
			} else if (evt.isControlDown() && grafo.isResolvido()) {
				listCaminhos = nohSelecionado.getCaminho();
				repaint();
				return;
			} else if (evt.isShiftDown()) {
				if (SwingUtilities.isLeftMouseButton(evt)) {
					if (!grafo.isDestino(nohSelecionado))
						grafo.setOrigem(nohSelecionado);
					else
						JOptionPane.showMessageDialog(null, "O destino NÃO pode ser definido como a origem! ");
				} else if (SwingUtilities.isRightMouseButton(evt)) {
					if (!grafo.isOrigem(nohSelecionado))
						grafo.setDestino(nohSelecionado);
					else
						JOptionPane.showMessageDialog(null, "A origem NÃO pode ser definida como o destino! ");
				} else
					return;

				grafo.setResolvido(false);
				repaint();
				return;
			}
		}

		if (ArestaHovered != null) {
			if (evt.isControlDown() && evt.isShiftDown()) {
				grafo.getListAresta().remove(ArestaHovered);
				ArestaHovered = null;
				grafo.setResolvido(false);
				repaint();
				return;
			}

			String input = JOptionPane.showInputDialog("Insira o peso para " + ArestaHovered.toString() + " : ");
			try {
				int peso = Integer.parseInt(input);
				if (peso > 0) {
					ArestaHovered.setPeso(peso);
					grafo.setResolvido(false);
					repaint();
				} else {
					JOptionPane.showMessageDialog(null, "O peso deve ser positivo! ");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Algo está errado com o valor informado => " +input+ " |\n\n " +ex.getMessage());
			}
			return;
		}

		for (Noh node : grafo.getListNoh()) {
			if (DrawUtils.isOverlapping(evt, node.getCoordenadas())) {
				JOptionPane.showMessageDialog(null, "Nó sobreposto NÃO pode ser criado !");
				return;
			}
		}

		grafo.addNoh(evt.getPoint());
		grafo.setResolvido(false);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		for (Noh node : grafo.getListNoh()) {
			if (nohSelecionado != null && node != nohSelecionado && DrawUtils.isWithinBounds(evt, node.getCoordenadas())) {
				Aresta novaAresta = new Aresta(nohSelecionado, node);
				grafo.addAresta(novaAresta);
				grafo.setResolvido(false);
			}
		}
		nohSelecionado = null;
		nohHovered = null;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		nohHovered = null;

		for (Noh node : grafo.getListNoh()) {
			if (nohSelecionado == null && DrawUtils.isWithinBounds(evt, node.getCoordenadas())) {
				nohSelecionado = node;
			} else if (DrawUtils.isWithinBounds(evt, node.getCoordenadas())) {
				nohHovered = node;
			}
		}

		if (nohSelecionado != null) {
			if (evt.isControlDown()) {
				nohSelecionado.setCoordenadas(evt.getX(), evt.getY());
				cursor = null;
				repaint();
				return;
			}

			cursor = new Point(evt.getX(), evt.getY());
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent evt) {

		if (evt.isControlDown()) {
			nohHovered = null;
			for (Noh noh : grafo.getListNoh()) {
				if (DrawUtils.isWithinBounds(evt, noh.getCoordenadas())) {
					nohHovered = noh;
				}
			}
		}

		ArestaHovered = null;

		for (Aresta aresta : grafo.getListAresta()) {
			if (DrawUtils.isOnEdge(evt, aresta)) {
				ArestaHovered = aresta;
			}
		}

		repaint();
	}

	public void reset() {
		grafo.clear();
		nohSelecionado = null;
		nohHovered = null;
		ArestaHovered = null;
		repaint();
	}
}
