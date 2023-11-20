package artecomp.base;

import javax.swing.JFileChooser;

/**
 * La clásica clase utilitaria compuestas por funciones varias.
 * 
 * @author nacho
 *
 */
public class Util {

	public static String elegirArchivo(java.awt.Component parent, String nombre) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(nombre);
		int r = fc.showOpenDialog(parent);
		if (r == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().getPath();
		} else {
			return null;
		}
	}
}
