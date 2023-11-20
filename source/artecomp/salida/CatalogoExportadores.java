package artecomp.salida;
import artecomp.base.Catalogo;

public class CatalogoExportadores extends Catalogo {
	public CatalogoExportadores() {
		super("Formatos");
		put("svg",new ExportadorSVG());
		put("png",new ExportadorPNG());
		setSeleccion("png");
	}
	
	public Exportador getExportador(String nombre) {
		return (Exportador) get(nombre);
	}

	public Exportador getExportadorNuevo(String nombre) {
		return (Exportador) getNuevo(nombre);
	}

	public Exportador getExportadorSeleccionado() {
		return (Exportador) getSeleccionado();
	}
}
