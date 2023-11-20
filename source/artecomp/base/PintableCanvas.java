package artecomp.base;
import java.awt.*;
import java.awt.image.*;
import javax.swing.JPanel;
import java.awt.geom.AffineTransform;
/**
 * Clase utilitaria que permite desplegar en pantalla el resultado de un objeto Pintable.
 *
 * @see Pintable
 * @author nacho
 *
 */
public class PintableCanvas extends JPanel implements java.util.Observer {
	static final long serialVersionUID = 1;

	BufferedImage salida;

	Dimension dim;

	Pintable pintable;

	AffineTransform ajusteDeEscala;

	public PintableCanvas(Pintable c, Dimension dim) {
		this.pintable = c;
		this.dim = dim;
		double sx = this.dim.getWidth()/c.getDimensiones().getWidth();
		double sy = this.dim.getHeight()/c.getDimensiones().getHeight();
		ajusteDeEscala = AffineTransform.getScaleInstance(sx,sy);
		salida = new BufferedImage(dim.width,dim.height,
				BufferedImage.TYPE_INT_RGB);
		this.pintable.addObserver(this);
	}

	public PintableCanvas(Pintable c) {
		this(c,c.getDimensiones());
	}

	public Dimension getMinimumSize() {
		return dim;
	}

	public Dimension getPreferredSize() {
		return dim;
	}

	public void update(java.util.Observable obs,Object arg) {
		Log.trace("update()");
		repaint();
	}
    /*
	public void paint(Graphics g) {
	  Log.trace("PintableCanvas.paint()");
	  paintComponent(g);
	}
	*/
	public void paintComponent(Graphics g) {
		Log.trace("PintableCanvas.paintComponent()");
		double sx = this.dim.getWidth()/pintable.getDimensiones().getWidth();
		double sy = this.dim.getHeight()/pintable.getDimensiones().getHeight();
		ajusteDeEscala = AffineTransform.getScaleInstance(sx,sy);
		Graphics2D g2 = (Graphics2D) salida.getGraphics();
		g2.setTransform(ajusteDeEscala);
		pintable.pintar(g2);
		g.drawImage(salida, 0, 0, (int) dim.getWidth(), (int) dim.getHeight(),
				this);
	}
}
