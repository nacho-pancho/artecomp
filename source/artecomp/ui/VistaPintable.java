package artecomp.ui;
import java.awt.*;
import java.awt.image.*;
import javax.swing.JPanel;

import artecomp.base.Log;
import artecomp.base.Pintable;

import java.awt.geom.AffineTransform;
/**
 * Clase utilitaria que permite desplegar en pantalla el resultado de un objeto Pintable.
 *
 * @see Pintable
 * @author nacho
 *
 */
public class VistaPintable extends JPanel implements java.util.Observer {
	static final long serialVersionUID = 1;

	BufferedImage salida;

	Dimension dim;

	Pintable pintable;

	AffineTransform ajusteDeEscala;

	public VistaPintable(Pintable c, Dimension dim) {
		this.pintable = c;
		this.dim = dim;
		double sx = this.dim.getWidth()/c.getDimensiones().getWidth();
		double sy = this.dim.getHeight()/c.getDimensiones().getHeight();
		ajusteDeEscala = AffineTransform.getScaleInstance(sx,sy);
		this.pintable.addObserver(this);
	}

	public VistaPintable(Pintable c) {
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
		double ancho_gui = getWidth();
		double alto_gui = getHeight();
		double ancho_pintable = pintable.getDimensiones().getWidth();
		double alto_pintable = pintable.getDimensiones().getHeight();
		double aspecto_pintable = ancho_pintable/alto_pintable;
		double aspecto_gui = ancho_gui/alto_gui;
		double alto,ancho;
		int ox , oy;
		if (aspecto_gui > aspecto_pintable) {
			// gui es mas alargado que pintable:
			// la imagen mostrada ocupa todo el alto  y el ancho lo determina
			// la rel. de aspecto del pintable
			alto = alto_gui;
			ancho = (int) Math.floor(aspecto_pintable*alto);
			ox = (int) ((ancho_gui - ancho)/2.0);
			oy = 0;
			//a = w/h, w= ah, h = w/a
		} else {
			// pintable es mas alargado que gui
			// la imagen mostrada ocupa todo el ancho  y el alto lo determina
			// la rel. de aspecto del pintable
			ancho = ancho_gui;
			alto = (int) Math.floor(ancho/aspecto_pintable);
			ox = 0;
			oy = (int) ((alto_gui - alto)/2.0);
		}
		double sx = ancho/ancho_pintable;
		double sy = alto/alto_pintable;
		if ((salida == null) || (salida.getHeight() != (int) alto) || (salida.getWidth() != (int) ancho)) {
			salida = new BufferedImage((int) ancho,(int) alto, BufferedImage.TYPE_INT_RGB);
		}
		ajusteDeEscala = AffineTransform.getScaleInstance(sx,sy);
		Graphics2D g2 = (Graphics2D) salida.getGraphics();
		g2.setTransform(ajusteDeEscala);
		pintable.pintar(g2);
		g.drawImage(salida, ox, oy, salida.getWidth(), salida.getHeight(),
				this);
	}
}