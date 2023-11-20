package artecomp.ui;

import artecomp.base.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.BorderLayout;

class PanelPrincipal extends JPanel {

	static final long serialVersionUID = 1;

	ArteComp gui;

	PanelConfiguracion panelConf;

	JComponent panelAux = new JPanel();

	public PanelPrincipal(ArteComp gui, Configurable conf) {
		super(new BorderLayout());
		this.gui = gui;
		panelConf = new PanelConfiguracion(conf);
		this.add(panelConf,BorderLayout.NORTH);
		JPanel panelCom = new JPanel(new java.awt.GridLayout(0, 2));
		//
		// teclas
		//
		String n = "Generar";
		Action a = new Generar();
		agregarAcelerador("G",n,a);
		JButton b = new JButton(n);
		b.setAction(a); panelCom.add(b);
		
		n = "Redibujar";
		a = new Redibujar();
		agregarAcelerador("R",n,a);
		b = new JButton(n);
		b.setAction(a); panelCom.add(b);

		n = "Salvar";
		a = new Salvar();
		agregarAcelerador("S",n,a);
		b = new JButton(n);
		b.setAction(a); panelCom.add(b);

		n = "Salir";
		a = new Salir();
		agregarAcelerador("F12",n,a);
		b = new JButton(n);
		b.setAction(a); panelCom.add(b);

		this.add(panelAux,BorderLayout.CENTER);
		this.add(panelCom,BorderLayout.SOUTH);
	}

	void agregarAcelerador(String k, String n, Action a) {
		//String n = (String) a.getValue("name");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(k),n);
		getActionMap().put(n, a);
	}
	
	public void setPanelAux(String titulo, JComponent c) {
		c.setBorder(new javax.swing.border.TitledBorder(titulo));
		this.getLayout().removeLayoutComponent(panelAux);
		this.add(c,BorderLayout.CENTER);
		this.validate();
		repaint();
	}

	class Salir extends AbstractAction {
		Salir() { super("Salir"); }
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	class Generar extends AbstractAction {
		private static final long serialVersionUID = 1L;
		Generar() { super("Generar"); }
		public void actionPerformed(ActionEvent e) {
			panelConf.aceptarCambios();
			gui.configurar(panelConf.getConfiguracion());
			gui.regenerar();
			getParent().repaint();
		}		
	}

	class Redibujar extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Redibujar() { super("Redibujar"); }
		public void actionPerformed(ActionEvent e) {
			panelConf.aceptarCambios();
			gui.configurar(panelConf.getConfiguracion());
			getParent().repaint();
		}		
	}
	
	class Salvar extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Salvar() { super("Salvar"); }
		public void actionPerformed(ActionEvent e) {
			gui.exportar();			
		}
	}
}
