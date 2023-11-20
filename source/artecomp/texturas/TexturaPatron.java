package artecomp.texturas;
import java.awt.Color;

import artecomp.base.*;
import artecomp.patrones.*;
/**
 * Define la textura de un objeto pintable a partir de un Patron y una PaletaColor
 * 
 * @see Paleta
 * @see Patron
 * @author nacho
 *
 */
public class TexturaPatron extends Textura {

	Paleta paleta;
	CatalogoPatrones catalogoPatrones;
	Patron patron;
	
	public TexturaPatron() {
		catalogoPatrones = new CatalogoPatrones();
		paleta = Paleta.crearPaleta(6,Paleta.MODO_SATURACION,Color.blue);
		patron = catalogoPatrones.getPatronSeleccionado();
	}

	public Configuracion getConfiguracion() {
		Configuracion conf= super.getConfiguracion();
		Opcion op = new Opcion(Opcion.PALETA,this.paleta); // 0 = auto
		conf.addOpcion("paleta",op);
		op = new Opcion(Opcion.CATALOGO, catalogoPatrones);
		conf.addOpcion("patron", op);
		return conf;
	}

	public void setConfiguracion(Configuracion conf) {
		super.setConfiguracion(conf);
		paleta = (Paleta) conf.getOpcion("paleta").getValor();
		catalogoPatrones = (CatalogoPatrones) conf.getOpcion("patron").getCatalogo();
		patron = catalogoPatrones.getPatronSeleccionado();
		
	}
	
	

	public Color getColor(Coordenada p) {
		Log.debug("\tTexturaPatron.getColor("+p+")=");
		p = transformar(p);
		double val = patron.eval(p.x, p.y);
		return paleta.getColorAt(val);
	}

	public Catalogable nuevaInstancia() { return new TexturaPatron(); }

}
