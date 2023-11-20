package artecomp.base;
import java.util.*;
/**
 * Define el conjunto de opciones que modifican el funcionamiento de un algoritmo Configurable.
 * Las son asociadas con un nombre que las identifica y que debe usarse para accederlas.
 * La estructura es jerárquica, permitiendo tener también una cantidad arbitraria de subconfiguraciones
 * con sus propias opciones.
 *
 * @see Configurable
 * @see Opcion
 * @author nacho
 *
 */
public class Configuracion {
	HashMap<String,Opcion> opciones;
	HashMap<String,Configuracion> subconfiguraciones;
	String nombre;

	public Configuracion(String nombre) {
		opciones = new HashMap<String,Opcion>();
		subconfiguraciones = new HashMap<String,Configuracion>();
		this.nombre = nombre;
	}


	public void addOpcion(String nombre, Opcion o) {
		if (!opciones.containsKey(nombre))
			opciones.put(nombre,o);
	}

	public void addSubconfiguracion(String nombre, Configuracion c) {
		if (!subconfiguraciones.containsKey(nombre))
			subconfiguraciones.put(nombre,c);
	}

	/** @return la Opcion asociada con el nombre */
	public Opcion getOpcion(String nombre) {
		int i = nombre.indexOf('.');
		if (i <0) { // es opcion simple
			if (opciones.containsKey(nombre)) {
				return opciones.get(nombre);
			} else {
				throw new IllegalArgumentException(nombre);
			}
		} else { // es subopcion
			String subconf = nombre.substring(0,i);
			String subop = nombre.substring(i+1);
			return getSubconfiguracion(subconf).getOpcion(subop);
		}
	}

	/** @return la Subconfiguracion asociada con el nombre */
	public Configuracion getSubconfiguracion(String nombre) {
		if (subconfiguraciones.containsKey(nombre)) {
			return subconfiguraciones.get(nombre);
		} else {
			throw new IllegalArgumentException(nombre);
		}
	}

	public void setOpcion(String nombre, Object val) {

		Opcion op = opciones.get(nombre);
		if (op != null)
			op.setValor(val);
		else
			throw new IllegalArgumentException(nombre);
	}

	public Collection getOpciones() { return getOpciones(false); }

	/** @return el conjunto de opcionesde esta configuracion, opcionalmente de las subconfiguraciones tambien */
	public Collection getOpciones(boolean recursivo) {
		ArrayList<String> ops = new ArrayList<String>();
		ops.addAll(opciones.keySet());
		if (recursivo) {
			Set subconfs = getSubconfiguraciones();
			Iterator it = subconfs.iterator();
			while (it.hasNext()) {
				String nombre = (String) it.next();
				Configuracion subconf = getSubconfiguracion(nombre);
				String prefijo = nombre+'.';
				Iterator it2 = subconf.getOpciones(true).iterator();
				while (it2.hasNext()) {
					ops.add(prefijo + it2.next().toString()); // it2.next() es un String
				}
			}
		}
		Collections.sort(ops);
		return ops;
	}

	public void merge(Configuracion cfg2) {
		opciones.putAll(cfg2.opciones);
		subconfiguraciones.putAll(cfg2.subconfiguraciones);
	}

	/** @return el conjunto de subconfiguraciones bajo esta configuracion (no es recursivo) */
	public Set getSubconfiguraciones() {
		return subconfiguraciones.keySet();
	}

	public String toString() {
		Iterator it = getOpciones(true).iterator();
		StringBuffer sb=new StringBuffer();
		sb.append("Opciones:\n");
		while (it.hasNext()) {
			String n =  (String) it.next();
			Object v= getOpcion(n).getValor();
			if (v == null) v= "(null)";
			sb.append(n).append(':').append(v).append('\n');
		}
		return sb.toString();
	}
	
	public String getNombre() { return nombre; }
	
	public void setNombre(String n) { nombre = n; }
}
