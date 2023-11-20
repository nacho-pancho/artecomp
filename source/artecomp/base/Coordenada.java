package artecomp.base;
/**
 * Clase propia para representar coordenadas 2D
 * Se podría usar java.awt.geom.Point2D.Double pero se me cae uno.
 * 
 * @author nacho
 *
 */
public class Coordenada {
	public double x=0.0,y=0.0;
	public Coordenada(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordenada(java.awt.Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public Coordenada() {
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
