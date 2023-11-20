package artecomp.texturas;
import artecomp.base.*;

import artecomp.base.Catalogo;

public class CatalogoTexturas extends Catalogo {
	public CatalogoTexturas() {
		super("Textura");
		put("patron",new TexturaPatron());
		put("imagen",new TexturaImagen());
		setSeleccion("patron");
	}
	
	public Textura getTextura(String nombre) {
		return (Textura) get(nombre);
	}

	public Textura getTexturaNuevo(String nombre) {
		return (Textura) getNuevo(nombre);
	}

	public Textura getTexturaSeleccionado(String nombre) {
		return (Textura) getSeleccionado();
	}
}
