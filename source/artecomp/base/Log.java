package artecomp.base;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
/**
 * Utilitario para sacar mensajes de logging y depuración a consola
 * con nivel controlable de verbosidad.
 * 
 * @author nacho
 *
 */
public class Log {
	public final static short TODO=0;
	public final static short DEBUG=32;
	public final static short TRACE=48;
	public final static short INFO=64;
	public final static short WARNING=96;
	public final static short ERROR=128;
	public final static short NADA=255;
	
	private static short logLevel=INFO;
	
	public static void log(int level, Object msg) {
		if (level >= logLevel)
			System.out.println(msg);
	}
	
	public static void setLevel(short l) {
		logLevel = l;
	}
	
	public static void debug(Object msg) {
		log(DEBUG,msg);
	}

	public static void info(Object msg) {
		log(INFO,msg);
	}

	public static void warning(Object msg) {
		log(WARNING,msg);
	}

	public static void trace(Object msg) {
		log(TRACE,msg);
	}

	public static void error(Object msg) {
		log(ERROR,msg);
	}
	
	public static void errorGUI(JFrame parent,Object msg) {
		if (logLevel >= ERROR)
		JOptionPane.showMessageDialog(parent,msg,"Error",JOptionPane.ERROR_MESSAGE);
	}

	public static void warningGUI(JFrame parent,Object msg) {
		if (logLevel >= WARNING)
			JOptionPane.showMessageDialog(parent,msg,"Aviso",JOptionPane.WARNING_MESSAGE);
	}

	public static void infoGUI(JFrame parent,Object msg) {
		if (logLevel >= INFO)
		JOptionPane.showMessageDialog(parent,msg,"Informacion",JOptionPane.INFORMATION_MESSAGE);
	}
}
