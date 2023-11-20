package artecomp.pintables;
/**
 * Imitacion de Boxfit (xscreensaver) en Java
 * La idea es que genere un SVG y de ahi un PDF para imprimir en alta resolucion
 * La primera prueba es sobre un Canvas comun para ver como anda.
 *
 * @author nacho
 *
 */
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import artecomp.base.*;
import artecomp.texturas.*;

import java.awt.Dimension;
import java.awt.Color;

public class BoxFit extends artecomp.base.Pintable {

    java.util.Random rng = new java.util.Random(6128765617L);
	
	//
	// mascara que dice cual lugar está ocupado y cual no
	//
	FillMask mask;

	Box[] cuadrados;

	// se guardan instancias de cada textura
	// y se tiene una como seleccionada en cada momento
	//
	// FALTA TERMINAR
	//
	Catalogo catalogoBorde;
	Catalogo catalogoRelleno;

	int cantidad, rafaga;
	double trazo;
	int margen;
	boolean solapar;
	Color fondo;
	
	public BoxFit() {
		this(new Dimension(2048,1024));
	}
	
	public BoxFit(Dimension d) {
		super(d);
		Log.trace("BoxFit()");
		mask = new FillMask(dimensiones.height, dimensiones.width);
		cantidad = 3000;
		rafaga = 400;
		trazo=2.0;
		margen = 0;
		solapar = false;
		fondo = Color.gray;
		
		catalogoBorde = new CatalogoTexturas();
		catalogoBorde.setSeleccion("patron");
		catalogoRelleno = new CatalogoTexturas();
		catalogoRelleno.setSeleccion("patron");
		Configuracion cfg = catalogoBorde.getSeleccionado().getConfiguracion();
		Paleta paleta = Paleta.crearPaleta(6,Paleta.MODO_INTENSIDAD,Color.BLUE);
		cfg.setOpcion("escalaX",Double.toString(1.0)); // ancho de A4 a 150 dpi
		cfg.setOpcion("escalaY",Double.toString(1.0)); // largo de A4 a 150 dpi
		cfg.setOpcion("paleta",paleta);
		catalogoBorde.getSeleccionado().setConfiguracion(cfg);
		System.out.println(paleta);
		paleta = paleta.derivar();
		System.out.println(paleta);
		cfg.setOpcion("paleta",paleta);
		catalogoRelleno.getSeleccionado().setConfiguracion(cfg);
		System.out.println("listo.");
		// faltaria un fondo también
	}

	public void setPaletaRelleno(Paleta p) {
		Configuracion cfg = catalogoRelleno.getSeleccionado().getConfiguracion();
		cfg.setOpcion("paleta", p);
		catalogoRelleno.getSeleccionado().setConfiguracion(cfg);		
	}
	
	public void setPaletaBorde(Paleta p) {
		Configuracion cfg = catalogoBorde.getSeleccionado().getConfiguracion();
		cfg.setOpcion("paleta", p);
		catalogoBorde.getSeleccionado().setConfiguracion(cfg);	
	}
	
	public Configuracion getConfiguracion() {
		Configuracion conf = super.getConfiguracion();
		conf.setNombre("BoxFit");
		Opcion op = new Opcion(Opcion.ENTERO,Integer.toString(cantidad));
		conf.addOpcion("cantidad",op);
		op = new Opcion(Opcion.ENTERO,Integer.toString(rafaga));
		conf.addOpcion("rafaga",op);
		op = new Opcion(Opcion.DECIMAL,Double.toString(trazo));
		conf.addOpcion("trazo",op);
		op = new Opcion(Opcion.COLOR, fondo);
		conf.addOpcion("fondo", op);		
		op = new Opcion(Opcion.CATALOGO, catalogoRelleno);
		conf.addOpcion("relleno", op);
		op = new Opcion(Opcion.CATALOGO, catalogoBorde);
		conf.addOpcion("borde", op);
		op = new Opcion(Opcion.ENTERO,Integer.toString(margen));
		conf.addOpcion("margen",op);
		op = new Opcion(Opcion.BOOLEAN,Boolean.toString(solapar));
		conf.addOpcion("solapar",op);
		Log.debug("Cuadraditos.getConfiguracion");
		Log.debug("conf=\n"+conf);
		return conf;
	}

	public void setConfiguracion(Configuracion conf) {
		super.setConfiguracion(conf);
		Log.trace("Cuadraditos.setConfiguracion()");
		Log.trace("Conf=\n"+conf);
		trazo = conf.getOpcion("trazo").getValorDouble();
		rafaga = conf.getOpcion("rafaga").getValorInt();
		cantidad = conf.getOpcion("cantidad").getValorInt();
		margen = conf.getOpcion("margen").getValorInt();
		solapar = conf.getOpcion("solapar").getValorBoolean();
		catalogoBorde = conf.getOpcion("borde").getCatalogo();
		catalogoRelleno = conf.getOpcion("relleno").getCatalogo();
		fondo = conf.getOpcion("fondo").getValorColor();
		if ((dimensiones.height != mask.getRows()) || (dimensiones.width != mask.getCols()))
			mask = new FillMask(dimensiones.height, dimensiones.width);
		
		}

	public void regenerar() {
		mask.clear();
		Log.trace("regenerar()");
		int ancho = dimensiones.width;
		int alto = dimensiones.height;
		Log.debug("regenerar()");
		cuadrados = new Box[cantidad];
		for (int i = 0; i < cuadrados.length; i += rafaga) {
			System.out.print(".");
			for (int r = 0; (i+r) < (cuadrados.length) && (r < rafaga); r++) {
				int x,y;
				do {
					x =  rng.nextInt(ancho);
					y =  rng.nextInt(alto);
				} while (mask.getVal(y,x));
				//System.out.print("x=" + x + " y=" + y + "\n");
				cuadrados[r+i] = new Box(x,y);
				mask.setVal(y,x,true);
			}
			boolean hay_vida = true;
			int k = 0;
			System.out.print("*");
			while (hay_vida) {
				//System.out.println(mask);
				hay_vida = false;
				int j = 0;
				for (int r = 0; (i+r) < (cuadrados.length) && (r < rafaga); r++) {
					Box b = cuadrados[i+r];
					if (!b.estancado && hayLugar(b)) {
						hay_vida = true;
						b.agrandar();
						marcar(b);
						j++;
					} else {
						b.estancado = true;
					}
				}
				//System.out.println("Iter " + k + " Vivos: " + j);
				k++;
			} // rafaga
			if (solapar) {
				mask.clear();
			}
		} // todo
		setChanged();
		notifyObservers();
	}

	boolean hayLugar(Box box) {
		int ancho = dimensiones.width;
		int alto = dimensiones.height;
		for (int m = 0; m <= margen; m++) {
			FillMask.Cursor c = mask.cursor(box.y,box.x);
			int w1=box.w+1+m;
			if (box.x + w1 >= ancho)
				return false;
			else if (box.x - w1 <= 0)
				return false;
			else if (box.y + w1 >= alto)
				return false;
			else if (box.y -w1 <= 0)
				return false;
	
			c.left(w1);
			//System.out.println("cl:"+ c);
			c.up(w1);
			//System.out.println("tl: "+ c);
			int w2 = 2*w1;
			// left to right
			for (int i = 0; i < w2; i++) {
				if (c.getVal()) {
					return false;
				}
				c.right();
			}
			//System.out.println("tr:" + c);
			// top-right downwards to bottom-right
			for (int i = 0; i < w2; i++) {
				if (c.getVal()) {
					return false;
				}
				c.down();
			}
			//System.out.println("br:" + c);
			// bottom-right to bottom-left
			for (int i = 0; i < w2; i++) {
				if (c.getVal()) {
					return false;
				}
				c.left();
			}
			//System.out.println("bl:" + c);
			// bottom-left to top-left
			for (int i = 0; i < w2; i++) {
				if (c.getVal()) {
					return false;
				}
				c.up();
			}
			//System.out.println("tl:" + c);
		}
		return true;
	}

	void marcar(Box box) {
		FillMask.Cursor c = mask.cursor(box.y,box.x);
		int w1 = box.w;
		c.left(w1);
		c.up(w1);
		int w2 = 2*w1;
		// left to right
		for (int i = 0; i < w2; i++) {
			c.setVal(true);
			c.right();
		}
		// top-right downwards to bottom-right
		for (int i = 0; i < w2; i++) {
			c.setVal(true);
			c.down();
		}
		// bottom-right to bottom-left
		for (int i = 0; i < w2; i++) {
			c.setVal(true);
			c.left();
		}
		// bottom-left to top-left
		for (int i = 0; i < w2; i++) {
			c.setVal(true);
			c.up();
		}
	}

	public void pintar(Graphics2D g2) {
		Log.trace("BoxFitFast.pintar()");
		int ancho = dimensiones.width;
		int alto = dimensiones.height;
		g2.setColor(fondo);
		g2.fillRect(0, 0, ancho, alto);
		BasicStroke stroke = new java.awt.BasicStroke((float)trazo);
		g2.setStroke(stroke);
		Coordenada c = new Coordenada();
		Textura texturaBorde = (Textura)catalogoBorde.getSeleccionado();
		Textura texturaRelleno = (Textura)catalogoRelleno.getSeleccionado();
		for (int i = 0; i < cuadrados.length; i++) {
			Box t = cuadrados[i];
			if (t == null) continue;
			c.x = ((double)t.x)/((double)ancho);
			c.y = ((double)t.y)/((double)alto);
			Log.debug(i+":Coordenada:"+c);
			Color col = texturaRelleno.getColor(c);
			Log.debug("\tRelleno:"+c);
			g2.setColor(col);
			int w=2*t.w+1;
			g2.fillRect(t.x-t.w, t.y-t.w, w, w);
			col = texturaBorde.getColor(c);
			Log.debug("\tBorde:"+c);
			g2.setColor(col);
			g2.drawRect(t.x-t.w, t.y-t.w, w, w);
		}
	}

	public Catalogable nuevaInstancia() { return new BoxFit(); }

}
