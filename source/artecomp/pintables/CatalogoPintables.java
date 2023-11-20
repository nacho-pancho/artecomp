package artecomp.pintables;
import artecomp.base.*;

import artecomp.base.Catalogo;

public class CatalogoPintables extends Catalogo {
	public CatalogoPintables() {
		super("Pintable");
		put("boxfit",new BoxFit());
		setSeleccion("boxfit");
	}

	public Pintable getPintable(String nombre) {
		return (Pintable) get(nombre);
	}

	public Pintable getPintableNuevo(String nombre) {
		return (Pintable) getNuevo(nombre);
	}

	public Pintable getPintableSeleccionado() {
		return (Pintable) getSeleccionado();
	}
}
