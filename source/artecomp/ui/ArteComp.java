package artecomp.ui;

import javax.swing.*;

import artecomp.base.*;
import artecomp.pintables.*;
import artecomp.salida.*;

import java.awt.event.*;
import java.io.IOException;
import java.awt.Dimension;

import java.awt.BorderLayout;

public class ArteComp extends JFrame {
	static final long serialVersionUID = 1;

	PanelPrincipal panelPrincipal;


	Catalogo exportadores = new CatalogoExportadores();

	Catalogo pintables = new CatalogoPintables();

	Pintable pintable;
	static ArteComp instance;
	
	public ArteComp() {
		super("ArteComp v1");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		getContentPane().setLayout(new BorderLayout());
		Dimension dim_real = new Dimension(2048, 1024);
		Dimension dim_gui = new Dimension(1024, 512);
		pintable = new BoxFit(dim_real);
		JPanel canvas = new VistaPintable(pintable, dim_gui);
		panelPrincipal = new PanelPrincipal(this, pintable);
		pintable.regenerar();
		canvas.addMouseListener(new ML());
		getContentPane().add(BorderLayout.CENTER,canvas);
		getContentPane().add(BorderLayout.EAST,panelPrincipal);
		pack();
		instance = this;
	}

	public void regenerar() {
		pintable.regenerar();
	}

	public void configurar(Configuracion cfg) {
		pintable.setConfiguracion(cfg);
	}

	public PanelPrincipal getPanelPrincipal() {
		return panelPrincipal;
	}

	public void setPanelAux(String titulo, JComponent c) {
		panelPrincipal.setPanelAux(titulo, c);	
		repaint();
	}

	public void exportar() {
		String fname = Util.elegirArchivo(ArteComp.this, "Export to file...");
		String[] parts = fname.split("\\.");
		Exportador ex = (Exportador) exportadores.get(parts[parts.length-1]);
		try {
			ex.exportar(pintable, fname);
		} catch (IOException ioe) {
			error(ioe.getMessage());
		}
	}

	class ML extends java.awt.event.MouseAdapter {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			panelPrincipal.setVisible(true);
		}
	}

	public static void main(String[] args) {
		try {
			Log.setLevel(Log.TRACE);
			JFrame f = new ArteComp();
			f.pack();
			f.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void error(String e) {
		JOptionPane.showMessageDialog(this, e, e, JOptionPane.ERROR_MESSAGE);
	}

}
