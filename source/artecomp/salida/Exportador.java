package artecomp.salida;
import java.awt.Dimension;

import artecomp.base.*;

public abstract class Exportador implements Catalogable {
	
	public Exportador () {}
	
	public void setConfiguracion(Configuracion c) {	
	}
	
	public Configuracion getConfiguracion() { return new Configuracion("Exportador"); }
	
	public abstract void exportar(Pintable p, String fname) throws java.io.IOException;
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) { return null; }
	}
}
