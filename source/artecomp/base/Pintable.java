package artecomp.base;

import java.awt.Dimension;
import java.util.Observable;

/**
 * Las principales clases manejadas por este Framework heredan de Pintable. Esta clase abstracta
 * tiene la funcionalidad básica y exige la definición de un conjunto de métodos que permiten
 * al objeto pintarse en un objeto del tipo Graphics2D.
 * 
 * @see java.awt.Graphics2D
 * @author nacho
 *
 */
public abstract class Pintable extends Observable implements Catalogable {

	protected Dimension dimensiones;
	
	protected Pintable(Dimension d) {
		this.dimensiones = d;
	}
	
	public Configuracion getConfiguracion() {
		Configuracion conf = new Configuracion("Pintable");
		Opcion op = new Opcion(Opcion.ENTERO,Integer.toString(dimensiones.height));
		conf.addOpcion("alto",op);
		op = new Opcion(Opcion.ENTERO,Integer.toString(dimensiones.width));
		conf.addOpcion("ancho",op);
		return conf;
	}

	
	public void setConfiguracion(Configuracion conf) {
		this.dimensiones.width = conf.getOpcion("ancho").getValorInt();
		this.dimensiones.height = conf.getOpcion("alto").getValorInt();
	}

	public java.awt.Dimension getDimensiones() { return dimensiones; }

	public void setDimensiones(Dimension dimensiones) {
		this.dimensiones = dimensiones;
	}

	public abstract void pintar(java.awt.Graphics2D g);

	public abstract void regenerar();   

	public Object clone() { 
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			return null;
		}
	}

}
