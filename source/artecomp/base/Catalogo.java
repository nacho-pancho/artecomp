package artecomp.base;
import java.util.*;

public class Catalogo implements Cloneable,Configurable {

	HashMap<String,Catalogable> mapa = new HashMap<String,Catalogable>();

	String seleccion = null;
	Catalogable seleccionado = null;
	String nombre;

	public Catalogo(String n) {
		this.nombre = n;
	}

	public Configuracion getConfiguracion() {
		// se compone del tipo seleccionado y las opciones para ese tipo
		Configuracion conf = new Configuracion(nombre);		
		Object[] nombres = getNombres();
		for (int i = 0; i < nombres.length; i++) {
			conf.addSubconfiguracion((String) nombres[i],get((String) nombres[i]).getConfiguracion());
		}
		Opcion op = new Opcion(Opcion.TEXTO,getSeleccion());
		conf.addOpcion("seleccion", op);		
		return conf;
	}

	public void setConfiguracion(Configuracion cfg) {
		Log.trace("Catalogo.setConfiguracion()");
		if (seleccion == null)
			return;
		Catalogable c = get(seleccion);
		if (c != null) {
			Configuracion cfgTipo = cfg.getSubconfiguracion(this.seleccion);
			this.seleccionado = c;
			c.setConfiguracion(cfgTipo);
		}
	}

	public String getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(String sel) {
		if (sel != null && !mapa.containsKey(sel))
			throw new IllegalArgumentException("Seleccion " + sel + " invalida.");
		else {
			this.seleccion = sel;
			if (this.seleccion == null) {
				seleccionado = null;
			} else {
				seleccionado = mapa.get(this.seleccion);
			}
		}
	}

	public Catalogable getSeleccionado() { return seleccionado; }

	public Catalogable get(String nombre) {
		return mapa.get(nombre);
	}

	public Catalogable getNuevo(String nombre) {
		Catalogable o = get(nombre);
		return o.nuevaInstancia();
	}

	public void put(String nombre, Catalogable obj) {
		mapa.put(nombre,obj);
		if (mapa.size() == 1) {
			seleccion = nombre;
		}
	}

	public boolean contains(String nombre) {
		return mapa.containsKey(nombre);
	}

	public Object[] getNombres() {
		return mapa.keySet().toArray();
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			return null;
		}
	}
}

