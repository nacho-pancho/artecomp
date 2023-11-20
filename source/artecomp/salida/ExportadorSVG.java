package artecomp.salida;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

import artecomp.base.*;

import java.io.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ExportadorSVG extends Exportador {

	public ExportadorSVG() { super(); }

	public void exportar(Pintable pintable, String fname)
			throws IOException {
		java.awt.Dimension dim = pintable.getDimensiones();

		// Get a DOMImplementation
		DOMImplementation domImpl = GenericDOMImplementation
				.getDOMImplementation();

		// Create an instance of org.w3c.dom.Document
		Document document = domImpl.createDocument(null, "svg", null);

		// Create an instance of the SVG Generator
		SVGGraphics2D svgGraphics = new SVGGraphics2D(document);
		// Ask the test to render into the SVG Graphics2D implementation
		pintable.pintar(svgGraphics);

		// Finally, stream out SVG to the standard output using UTF-8
		// character to byte encoding
		boolean useCSS = true; // we want to use CSS style attribute
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(fname)), "UTF-8"));
		svgGraphics.stream(out, useCSS);
	}

	public Catalogable nuevaInstancia() { return new ExportadorSVG(); }

}
