package artecomp.texturas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

import artecomp.base.Configuracion;
import artecomp.base.Opcion;
import artecomp.base.Textura;
import artecomp.base.*;

/**
 * Textura que obtiene los colores de los puntos a partir de una imagen digital.
 * La imagen es mapeada en un rectángulo entre (0,0) y (escalax,escalay), y repetida periódicamente en todas
 * las direcciones.
 * 
 * @author nacho
 *
 */
public class TexturaImagen extends Textura {
	BufferedImage imagen;
	String rutaImagen;
	
	public Configuracion getConfiguracion() {
		Configuracion conf = super.getConfiguracion();
		Opcion op = new Opcion(Opcion.ARCHIVO,rutaImagen);
		conf.addOpcion("imagen",op);
		return conf;
	}
	
	public void setConfiguracion(Configuracion cf) {
		super.setConfiguracion(cf);
		String ruta = cf.getOpcion("imagen").getValorString();
		try {
			imagen = ImageIO.read(new File(ruta));
		} catch (IOException ex) {
			//throw new IllegalArgumentException("Imagen " + ruta + " no legible.");
			Log.error("Imagen " + ruta + " no legible.");
		}
		
	}
	
	public TexturaImagen() {
		super();		
		imagen = new BufferedImage(128,128,BufferedImage.TYPE_INT_RGB);
	}
	
	public TexturaImagen(String ruta,double escalax,double escalay) throws IOException {
		this.rutaImagen = ruta;
		imagen = ImageIO.read(new File(ruta));
	}

	public Color getColor(Coordenada p) {
		Log.debug("TexturaImagen.getColor("+p+")");
		p = transformar(p);
		p.x -= Math.floor(p.x);
		p.y -= Math.floor(p.y);
		Log.debug(p);
		int rgb = imagen.getRGB((int)(((double)imagen.getWidth())*p.x),(int)(((double)imagen.getHeight())*p.y));// TODO Auto-generated method stub
		return new Color((rgb >>16)&0xff,(rgb >>8)&0xff,rgb & 0xff);
		
	}

	public Catalogable nuevaInstancia() { return new TexturaImagen(); }
	
}
