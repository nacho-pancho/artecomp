package artecomp.base;

/**
 * Instancias de esta clase definen qué color se le asigna a cada punto en el espacio 2D
 * en donde se trazan los objetos que componen a una escena dibujada por un objeto Pintable.
 * 
 * @see Pintable
 * @author nacho
 *
 */
public abstract class Textura implements Catalogable {
	Coordenada escala,translacion;
	double rotacion;
		
	public Textura() {
		escala = new Coordenada(1.0,1.0);
		translacion = new Coordenada(0.0,0.0);
		rotacion = 0.0;
	}
	
	
	public Configuracion getConfiguracion() {
		Log.trace("Textura.getConfiguracion()");
		Configuracion cnf = new Configuracion("Textura");
		Opcion op = new Opcion(Opcion.DECIMAL,escala.x);
		cnf.addOpcion("escalaX",op);
		op = new Opcion(Opcion.DECIMAL,escala.y);
		cnf.addOpcion("escalaY",op);
		op = new Opcion(Opcion.DECIMAL,translacion.x);
		cnf.addOpcion("transX",op);
		op = new Opcion(Opcion.DECIMAL,translacion.y);
		cnf.addOpcion("transY",op);
		op = new Opcion(Opcion.DECIMAL,rotacion);
		cnf.addOpcion("rotacion",op);
		return cnf;
	}

	public void setConfiguracion(Configuracion conf) {
		escala.x = conf.getOpcion("escalaX").getValorDouble();
		escala.y = conf.getOpcion("escalaY").getValorDouble();
		translacion.x = conf.getOpcion("transX").getValorDouble();
		translacion.y = conf.getOpcion("transY").getValorDouble();
		rotacion = conf.getOpcion("rotacion").getValorDouble();
	}

	protected Coordenada transformar(Coordenada p) {
		Coordenada q = new Coordenada();
		if (rotacion != 0.0) {
			double r = rotacion * Math.PI/180.0;
			q.x = p.x*Math.cos(r)-p.y*Math.sin(r);
			q.y = p.x*Math.sin(r)+p.y*Math.cos(r);
		} else {
			q.x = p.x;
			q.y = p.y;
		}
		q.x /= escala.x;
		q.y /= escala.y;
		q.x += translacion.x;
		q.y += translacion.y;
		Log.debug("\ttransformar("+p+")="+q);
		return q;
	}
	
	public abstract java.awt.Color getColor(Coordenada p);

	public Object clone() { 
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			return null;
		}
	}

}
