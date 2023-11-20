package artecomp.patrones;
import artecomp.base.*;

import artecomp.base.Catalogo;

public class CatalogoPatrones extends Catalogo {
	
	public CatalogoPatrones() {
		super("Patron");
		put("gradiente",new PatronGradiente());
		setSeleccion("gradiente");
	}
	
	public Patron getPatron(String nombre) {
		return (Patron) get(nombre);
	}

	public Patron getPatronNuevo(String nombre) {
		return (Patron) getNuevo(nombre);
	}

	public Patron getPatronSeleccionado() {
		return (Patron) getSeleccionado();
	}
}
