package artecomp.base;
import java.awt.Color;
import java.util.ArrayList;
/**
 * Mapea un índice entre [0,1) a un color en RGB interpolando linealmente una tabla
 * de pares (x_i,c_i) donde x_i son indices entre 0 y 1 y c es un color RGB y x_i < x_(i+1)
 *
 * @author nacho
 *
 */
public class Paleta {
	final static int MAXBANDAS = 8;

	final static int MINBANDAS = 4;
	
	public final static int MODO_RGB = 0;
	public final static int MODO_TONO = 1;
	public final static int MODO_SATURACION = 2;
	public final static int MODO_INTENSIDAD = 3;
	
	ArrayList<Color> colores;

	ArrayList<Double> indices;

    static java.util.Random rng = new java.util.Random(6128765617L);

	public static Paleta crearPaleta(int bandas, int tipo, Color base) {
		ArrayList<Color> colores = new ArrayList<Color>(bandas);
		ArrayList<Double> indices = new ArrayList<Double>(bandas);
		float[] HSB = new float[3];
		if (base != null) {
			Color.RGBtoHSB(base.getRed(), base.getGreen(), base.getBlue(), HSB);
		} else {
			HSB[0] = rng.nextFloat();
			HSB[1] = 0.6f;
			HSB[2] = 0.8f;
		}
		
		for (int i = 0; i < bandas; i++) {
			Color c = null;
			switch (tipo) {
			case MODO_RGB: default:
				c = new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
			break;
			case MODO_TONO:
				c = Color.getHSBColor(rng.nextFloat(), HSB[1], HSB[2]);
				break;
			case MODO_SATURACION:
				c = Color.getHSBColor(HSB[0], rng.nextFloat(), HSB[2]);
				break;
			case MODO_INTENSIDAD:
				c = Color.getHSBColor(HSB[0], HSB[1], rng.nextFloat());
				break;
			}
			colores.add(c);
			indices.add(rng.nextDouble());
		}
		java.util.Collections.sort(indices);
		return new Paleta(indices,colores);
	}
	
	public Paleta(ArrayList<Double> indices, ArrayList<Color> colores) {
		this.indices = indices;
		this.colores = colores;
	}

	public int getNumColores() {
		return colores.size();
	}

	public Color getColor(int i) {
		return colores.get(i);
	}

	public double getIndice(int i) {
		return indices.get(i).doubleValue();
	}

	public Color getColorAt(double x) {
		Log.debug("Paleta.getColorAt("+x+")");
		int a, b;
		int N = getNumColores();
		x -= Math.floor(x);
		if (N == 0)
			return Color.black;
		else if (N == 1)
			return colores.get(0);

		for (b = 0; b < N; b++) {
			if (getIndice(b) > x)
				break;
		}
		if (b >= N) { // x esta por encima del ultimo valor, interpola con
						// primero
			a = N - 1;
			b = 0;
		} else if (b == 0) {
			a = N - 1;
		} else {
			a = b - 1;
		}
		double ia = getIndice(a);
		double ib = getIndice(b);
		if (b == 0) {
			ib += 1.0; // se interpola circularmente
			if (x < ia)
				x += 1.0;
		}
		// System.out.println("ia="+ia+", ib="+ib+", x="+x+",
		// L="+indices.length);
		Color ca = getColor(a);
		Color cb = getColor(b);
		int R = (int) interp(ia, ca.getRed(), ib, cb.getRed(), x);
		int G = (int) interp(ia, ca.getGreen(), ib, cb.getGreen(), x);
		int B = (int) interp(ia, ca.getBlue(), ib, cb.getBlue(), x);
		// System.out.println("ca="+ca+", cb="+cb);
		// System.out.println("R="+R+", G="+G+", B="+B);
		Color cx = new Color(R, G, B);
		return cx;
	}

	public void insert(int i, double x, Color c) {
		colores.add(i, c);
		indices.add(i, new Double(x));
	}

	public void setColor(int i, Color c) {
		colores.set(i, c);
	}

	public void setIndice(int i, double x) {
		indices.set(i, new Double(x));
	}

	public void remove(int i) {
		indices.remove(i);
		colores.remove(i);
	}

	public Paleta derivar() {
		Paleta derivado = new Paleta( (ArrayList<Double>) indices.clone(), (ArrayList<Color>) colores.clone());
		shuffle(derivado.indices);
		return derivado;
	}

	static double interp(double a, double fa, double b, double fb, double x) {
		return fa + (fb - fa) * (x - a) / (b - a);
	}

	static void shuffle(java.util.ArrayList v) {
		int N = v.size();
		for (int i = 0; i < (10 * N); i++) {
			int a = rng.nextInt(N);
			int b = rng.nextInt(N);
			if (a != b) {
				Object tmp = v.get(a);
				v.set(a, v.get(b));
				v.set(b, tmp);
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("Paleta de "+indices.size() + "colores:\n");
		for (int i = 0; i < indices.size(); i++) {
			sb.append(indices.get(i)).append(':').append(colores.get(i)).append('\n');
		}
		return sb.toString();
	}
}
