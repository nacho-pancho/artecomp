package artecomp.ui;
import artecomp.base.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.awt.GridBagConstraints;

public class PanelConfiguracion extends JPanel {

	static final long serialVersionUID = 1;
	Configurable configurable;
	Configuracion conf;
	HashMap<String,JComponent> componentes = new HashMap<String,JComponent>();
	ArrayList<PanelConfiguracion> panelesSubconf = new ArrayList<PanelConfiguracion>(); 
	
	
	public PanelConfiguracion(Configurable _c) {
		super();
		this.configurable = _c;
		this.conf = _c.getConfiguracion();
		inicializar();
	}
	
	public void inicializar() { 
		//JPanel principal = new JPanel(new GridLayout(0, 2));
		JPanel principal = new JPanel(new GridBagLayout());
		JTabbedPane lenguetas = null; // si hay subconfiguraciones, se inicializa
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		java.util.Iterator it = conf.getOpciones().iterator();
		ActionListener oaal = new OpcionArchivoAL();
		while (it.hasNext()) {
			String nombre = (String) it.next();
			Opcion opcion = conf.getOpcion(nombre);
			Object tipo = opcion.getTipo();
			//
			JComponent c = null;
			//
			if ((tipo != Opcion.CATALOGO) && (tipo != Opcion.PALETA)) {
				gbc.weightx = 0.1;
				gbc.gridwidth = 1;
				principal.add(new JLabel(nombre),gbc);
			}
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1.0;
			if (tipo == Opcion.ARCHIVO) {
				JButton b = new JButton("Elegir");
				b.setActionCommand(nombre);
				b.addActionListener(oaal);
				gbc.gridwidth = 1;
				gbc.weightx = 0.5;
				principal.add(b,gbc);
				c = new JTextField("./salida.png");
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.weightx = 1.0;
				principal.add(c,gbc);
			} else if (tipo == Opcion.BOOLEAN) {
				c = new JCheckBox();
				((JCheckBox)c).setSelected(opcion.getValorBoolean());
				principal.add(c,gbc);
			} else if (tipo == Opcion.DECIMAL ||
					   tipo == Opcion.ENTERO  ||
					   tipo == Opcion.TEXTO) {
				c= new JTextField(opcion.getValorString(),10);
				principal.add(c,gbc);
			} else if (tipo == Opcion.COLOR) {
				c = new VistaColor(opcion.getValorColor());
				principal.add(c,gbc);
			} else if (tipo == Opcion.CATALOGO) {
				Catalogo cat = opcion.getCatalogo();
				Object[] nom_cat = cat.getNombres();
				//
				// cada catalogo se muestra en una lengueta con su nombre
				// en donde aparece un panel con 2 items: 
				// - un combo box con el item elegido
				// - un panel con la conf. del item elegido
				//
				// si hay al menos una opción de este tipo en la configuración
				// toda la configuración "general" del objeto se pasa a una lengueta "principal"
				//
				if (lenguetas == null) {
					lenguetas = new JTabbedPane();
				}
				//
				// - panel de conf. de los items
				//
				CardLayout cl = new CardLayout();
				JPanel subp= new JPanel(cl);
				for (int i = 0; i < nom_cat.length; i++) {
					// se agrega la conf de cada elemento del catalogo como nueva lengueta
					String nombre_item =(String) nom_cat[i];
					Configurable conf_item = cat.get(nombre_item);
					PanelConfiguracion panel_item = new PanelConfiguracion(conf_item);
					subp.add(panel_item,nombre_item);
					panelesSubconf.add(panel_item);
				}
				if (cat.getSeleccion() != null) {
					cl.show(subp,cat.getSeleccion());
				}
				//
				// - combo box
				//
				JComboBox cb = new JComboBox(nom_cat);
				cb.addActionListener(new OpcionCatalogoAL(cat,subp));
				if (opcion.getValor() != null)
					cb.setSelectedItem(cat.getSeleccion());
				//
				// panel de la lengueta
				//
				JPanel p = new JPanel(new BorderLayout());
				p.add(cb,BorderLayout.NORTH);
				p.add(subp,BorderLayout.CENTER);
				lenguetas.addTab(nombre, p);
				c = null;
			} else if (tipo == Opcion.PALETA) {
				Paleta p = (Paleta)opcion.getValor();
				JButton b = new JButton("Editar");
				c = new PanelPaleta(p);
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.weightx = 1.0;
				gbc.gridheight = 2;
				principal.add(c,gbc);
				gbc.gridheight = 1;
			}
			if (c != null) {
				componentes.put(nombre,c);
			}
		}
		// 
		// si hay lenguetas, entonces el principal pasa a ser una de ellas
		//
		if (lenguetas != null) {
			lenguetas.add(conf.getNombre(),principal);
			lenguetas.setSelectedComponent(principal);
			add(lenguetas);
		} else {
			add(principal);
		}
	}


	public Configuracion getConfiguracion() { return conf; }

	/** actualiza configuracion con valores de los campos */
	public void aceptarCambios() {
		// redefine valores de conf desde los campos
		java.util.Iterator it = conf.getOpciones().iterator();
		while (it.hasNext()) {
			String n = (String) it.next();
			Opcion o = conf.getOpcion(n);
			Object t = o.getTipo();
			JComponent c = componentes.get(n);
			Object val = "";
			if (t == Opcion.BOOLEAN) {
				val = new Boolean(((JCheckBox)c).isSelected());
			} else if (t == Opcion.ARCHIVO||
					   t == Opcion.DECIMAL ||
					   t == Opcion.ENTERO  ||
					   t == Opcion.TEXTO) {
				val = ((JTextField)c).getText();
			} else if (t == Opcion.CATALOGO) {
				//val = ((JComboBox)c).getSelectedItem().toString();
			} else if (t == Opcion.PALETA) {
				val = ((PanelPaleta)c).getPaleta();
			} else if (t == Opcion.COLOR) {
				// falta implementar
				val = ((VistaColor)c).getColor();
			}
			Log.info(n + "<-"  + val );
			if (t != Opcion.CATALOGO)
				conf.setOpcion(n,val);
		}
		// subconfiguraciones
		Iterator<PanelConfiguracion> pit = panelesSubconf.iterator();
//		ActionListener scal = new SubconfAL();
		while (pit.hasNext()) {
			PanelConfiguracion pc = pit.next();
			pc.aceptarCambios();			
		}
		configurable.setConfiguracion(conf);
		/*
		getTopLevelAncestor();
		if (getTopLevelAncestor()!= null)
			getTopLevelAncestor().setVisible(false);
			*/
	}
	/** rechaza cambios efectuados en los campos y restaura valores almacenados en la configuracion */
	public void rechazarCambios() {
		// redefine valores de los campos desde conf
		java.util.Iterator it = conf.getOpciones().iterator();
		while (it.hasNext()) {
			String n = (String) it.next();
			Opcion o = conf.getOpcion(n);
			Object t = o.getTipo();
			JComponent c = componentes.get(n);
			if (t == Opcion.BOOLEAN) {
				((JCheckBox)c).setSelected(o.getValorBoolean());
			} else if (t == Opcion.ARCHIVO||
					   t == Opcion.DECIMAL ||
					   t == Opcion.ENTERO  ||
					   t == Opcion.TEXTO) {
				((JTextField)c).setText(o.getValorString());
			} else if (t == Opcion.CATALOGO) {
				((JComboBox)c).setSelectedItem(o.getValor());
				// falta implementar
			}
		}
	}

	public JDialog getDialogo(JFrame padre,String titulo) {
		JDialog d = new JDialog(padre,titulo,true);
		d.getContentPane().add(this);
		d.pack();
		d.setLocationRelativeTo(getParent());
		return d;
	}

	class OpcionArchivoAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			String fname = Util.elegirArchivo(getParent(), c);
			((JTextField)componentes.get(c)).setText(fname);
		}
	}
	
	/*
	 * cuando se selecciona un item de un catálogo, se hacen 2 cosas:
	 * 1- se selecciona el item en el catálogo.
	 * 2- se muestra su conf. en el panel correspondiente 
	 */
	class OpcionCatalogoAL implements ActionListener {
		Catalogo catalogo;
		JPanel panel;
		
		OpcionCatalogoAL(Catalogo c, JPanel p) {
			this.catalogo = c;
			this.panel = p;
		}
		public void actionPerformed(ActionEvent e) {
			//String c = e.getActionCommand();
			JComboBox combo = (JComboBox) e.getSource();
			String sel = combo.getSelectedItem().toString();
			catalogo.setSeleccion(sel);
			CardLayout layout = (CardLayout) panel.getLayout();
			layout.show(panel,sel);
			repaint();
		}
	}

}
