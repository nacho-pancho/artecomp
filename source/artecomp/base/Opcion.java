package artecomp.base;

import java.awt.Color;

/**
 * Opcion de configuracion.
 * 
 * @author nacho
 *
 */
public class Opcion {
	
	private static class Tipo {};
	
	public static final Tipo DECIMAL = new Tipo();
	public static final Tipo TEXTO = new Tipo();
	public static final Tipo ENTERO = new Tipo();
	public static final Tipo PALETA = new Tipo();
	public static final Tipo ARCHIVO = new Tipo();
	public static final Tipo CATALOGO = new Tipo();
	public static final Tipo BOOLEAN = new Tipo();
	public static final Tipo COLOR = new Tipo();
	
	private Object valor;
	private Tipo tipo;
	
	public void setValor(Object obj) {
		this.valor = obj;
	}
	
	public Opcion (Tipo tipo, Object val) {
		this.tipo = tipo;
		this.valor = val;
	}
	
	public Opcion(Tipo tipo, double val) {
		this(tipo,Double.toString(val));
	}

	public Opcion(Tipo tipo, int val) {
		this(tipo,Integer.toString(val));
	}

	public Opcion(Tipo tipo, boolean val) {
		this(tipo,Boolean.toString(val));
	}

	public Object getValor() { return valor; }
	
	public String getValorString() { return (valor!=null)?valor.toString():""; }
	
	public int getValorInt() { 
		return Integer.parseInt(valor.toString());
	}
	
	public double getValorDouble() { 
		return Double.parseDouble(valor.toString());
	}
	
	public boolean getValorBoolean() {
		if (valor instanceof String) {
		if (valor == null) return false;
		if (valor.toString().length() == 0) return false;
		char c = valor.toString().toLowerCase().charAt(0);
		return (c == 's') || (c == '1') || (c == 'y') || (c == 't');
		} else if (valor instanceof Boolean){
			return (Boolean) valor;
		} else {
			return valor != null;
		}
	}
	
	public Color getValorColor() { 
		return (Color) valor; 
	}
	
	public Catalogo getCatalogo() { 
		return (Catalogo) valor;
	}
	
	public Tipo getTipo() { return tipo; }
	
}
