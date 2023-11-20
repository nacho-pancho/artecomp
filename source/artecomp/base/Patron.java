package artecomp.base;

/**
 * Define entre puntos 2D definidos por pares (x,y) e índices de color entre 0 y 1. Estos son a su vez
 * utilizados como argumento en la paleta de color para devolver el color asociado a un punto en el espacio.
 * 
 * @see Paleta
 * @author nacho
 *
 */
public abstract class Patron implements Catalogable {
	
	public abstract double eval(double x, double y);

	public void setConfiguracion(Configuracion c) {
		Log.trace("Patron.setConfiguracion()");
		
	}
	
	public Configuracion getConfiguracion() { return new Configuracion("Patron"); }
	public Object clone() { 
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			return null;
		}
	}
	
}
