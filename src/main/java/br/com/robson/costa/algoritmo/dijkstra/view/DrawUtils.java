package br.com.robson.costa.algoritmo.dijkstra.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import br.com.robson.costa.algoritmo.dijkstra.model.Aresta;
import br.com.robson.costa.algoritmo.dijkstra.model.Noh;


/**
 * 20 de set de 2020
 * @author robson.costa
 */
public class DrawUtils {
	
	 private Graphics2D g;
	 private static int radius = 20;

	    public DrawUtils(Graphics2D graphics2D){
	        g = graphics2D;
	    }

	    public static boolean isWithinBounds(MouseEvent e, Point p) {
	        int x = e.getX();
	        int y = e.getY();

	        int boundX = (int) p.getX();
	        int boundY = (int) p.getY();

	        return (x <= boundX + radius && x >= boundX - radius) && (y <= boundY + radius && y >= boundY - radius);
	    }

	    public static boolean isOverlapping(MouseEvent e, Point p) {
	        int x = e.getX();
	        int y = e.getY();

	        int boundX = (int) p.getX();
	        int boundY = (int) p.getY();

	        return (x <= boundX + 2.5*radius && x >= boundX - 2.5*radius) && (y <= boundY + 2.5*radius && y >= boundY - 2.5*radius);
	    }

	    public static boolean isOnEdge(MouseEvent e, Aresta aresta) {

	        int dist = distToSegment( e.getPoint(),
	                                  aresta.getNohUm().getCoordenadas(),
	                                  aresta.getNohDois().getCoordenadas() );
	        if (dist<6)
	            return true;
	        return false;
	    }

	    public static Color parseColor(String colorStr) {
	        return new Color(
	                Integer.valueOf(colorStr.substring(1, 3), 16),
	                Integer.valueOf(colorStr.substring(3, 5), 16),
	                Integer.valueOf(colorStr.substring(5, 7), 16));
	    }

	    public void drawWeight(Aresta aresta) {
	        Point from = aresta.getNohUm().getCoordenadas();
	        Point to = aresta.getNohDois().getCoordenadas();
	        int x = (from.x + to.x)/2;
	        int y = (from.y + to.y)/2;

	        int rad = radius/2;
	        g.fillOval(x-rad, y-rad, 2*rad, 2*rad);
	        drawWeightText(String.valueOf(aresta.getPeso()), x, y);
	    }

	    public void drawPath(java.util.List<Noh> path) {
	        List<Aresta> edges = new ArrayList<>();
	        for(int i = 0; i < path.size()-1; i++) {
	            edges.add(new Aresta(path.get(i), path.get(i+1)));
	        }

	        for(Aresta aresta : edges) {
	            drawPath(aresta);
	        }
	    }

	    public void drawPath(Aresta aresta) {
	        g.setColor(parseColor("#00BCD4"));
	        drawBoldEdge(aresta);
	    }

	    public void drawHoveredEdge(Aresta aresta) {
	        g.setColor(parseColor("#E1BEE7"));
	        drawBoldEdge(aresta);
	    }

	    private void drawBoldEdge(Aresta aresta){
	        Point from = aresta.getNohUm().getCoordenadas();
	        Point to = aresta.getNohDois().getCoordenadas();
	        g.setStroke(new BasicStroke(8));
	        g.drawLine(from.x, from.y, to.x, to.y);
	        int x = (from.x + to.x)/2;
	        int y = (from.y + to.y)/2;

	        int rad = 13;
	        g.fillOval(x-rad, y-rad, 2*rad, 2*rad);
	    }

	    public void drawEdge(Aresta aresta) {
	        g.setColor(parseColor("#555555"));
	        drawBaseEdge(aresta);
	        drawWeight(aresta);
	    }

	    private void drawBaseEdge(Aresta aresta){
	        Point from = aresta.getNohUm().getCoordenadas();
	        Point to = aresta.getNohDois().getCoordenadas();
	        g.setStroke(new BasicStroke(3));
	        g.drawLine(from.x, from.y, to.x, to.y);
	    }

	    public void drawHalo(Noh node){
	        g.setColor(parseColor("#E91E63"));
	        radius+=5;
	        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);
	        radius-=5;
	    }

	    public void drawSourceNode(Noh node){
	        g.setColor(parseColor("#00BCD4"));
	        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

	        radius-=5;
	        g.setColor(parseColor("#B2EBF2"));
	        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

	        radius+=5;
	        g.setColor(parseColor("#00BCD4"));
	        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
	    }

	    public void drawDestinationNode(Noh node){
	        g.setColor(parseColor("#F44336"));
	        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

	        radius-=5;
	        g.setColor(parseColor("#FFCDD2"));
	        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

	        radius+=5;
	        g.setColor(parseColor("#F44336"));
	        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
	    }

	    public void drawNode(Noh node){
	        g.setColor(parseColor("#9C27B0"));
	        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

	        radius-=5;
	        g.setColor(parseColor("#E1BEE7"));
	        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

	        radius+=5;
	        g.setColor(parseColor("#9C27B0"));
	        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
	    }

	    public void drawWeightText(String text, int x, int y) {
	        g.setColor(parseColor("#cccccc"));
	        FontMetrics fm = g.getFontMetrics();
	        double t_width = fm.getStringBounds(text, g).getWidth();
	        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
	    }

	    public void drawCentreText(String text, int x, int y) {
	        FontMetrics fm = g.getFontMetrics();
	        double t_width = fm.getStringBounds(text, g).getWidth();
	        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
	    }


	    // Calculos
	    private static int sqr(int x) {
	        return x * x;
	    }
	    private static int dist2(Point v, Point w) {
	        return sqr(v.x - w.x) + sqr(v.y - w.y);
	    }
	    private static int distToSegmentSquared(Point p, Point v, Point w) {
	        double l2 = dist2(v, w);
	        if (l2 == 0) return dist2(p, v);
	        double t = ((p.x - v.x) * (w.x - v.x) + (p.y - v.y) * (w.y - v.y)) / l2;
	        if (t < 0) return dist2(p, v);
	        if (t > 1) return dist2(p, w);
	        return dist2(p, new Point(
	                (int)(v.x + t * (w.x - v.x)),
	                (int)(v.y + t * (w.y - v.y))
	        ));
	    }
	    private static int distToSegment(Point p, Point v, Point w) {
	        return (int) Math.sqrt(distToSegmentSquared(p, v, w));
	    }

    
}
