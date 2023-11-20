package artecomp.patrones;

import artecomp.base.*;

/** Patron en el cual el valor devuelto aumenta linealmente desde el origen en  una dirección y se mantiene constante 
 * al moverse en forma perpendicular a ella.  
 * El valor es truncado entre 0 y 1, obteniéndose un comportamiento periódico.
 */
public class PatronGradiente extends Patron {
	double dirx, diry;

	public PatronGradiente(double dirx, double diry) {
		this.dirx = dirx;
		this.diry = diry;
	}

	public PatronGradiente(double ang) {
		this(Math.cos(ang), Math.sin(ang));
	}

	public PatronGradiente() {
		this(1.0,0.0);
	}

	public double eval(double x, double y) {
		// mientras mas grande mag mas lento el gradiente		
		double v= (x * dirx + y * diry);
		Log.debug("\t Patron.eval("+x+","+y+")="+v);
		return v;
	}
	
	public Catalogable nuevaInstancia() { return new PatronGradiente(); }
}
