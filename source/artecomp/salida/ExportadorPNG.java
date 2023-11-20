package artecomp.salida;

import artecomp.base.*;

import java.io.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class ExportadorPNG extends Exportador {

	public ExportadorPNG() {
		super();
	}

	public void exportar(Pintable pintable, String fname) throws IOException {
		java.awt.Dimension dim = pintable.getDimensiones();
		BufferedImage salida = new BufferedImage(dim.width,dim.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) salida.getGraphics();
		pintable.pintar(g2);
		javax.imageio.ImageIO.write(salida,"png",new File(fname));
	}

	public Catalogable nuevaInstancia() { return new ExportadorPNG(); }
}
