package artecomp.ui;

import javax.swing.*;
import java.awt.event.*;

import artecomp.base.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;

class PanelPaleta extends JPanel {
	
	static final long serialVersionUID = 1;
	static final String[] tipos = {"rgb","tono","sat","int"}; 
	                    
	Paleta paleta;
	JComboBox tiposCB;
	JTextField estriasTF;
	VistaPaleta vistaPaleta;
	VistaColor vistaColor;
	Color colorBase = new Color(0,128,255);
	
	PanelPaleta(Paleta pal) {
		super(new BorderLayout());
		setBorder(new javax.swing.border.TitledBorder("Paleta"));
		this.paleta = pal;
		vistaPaleta = new VistaPaleta(this.paleta);
		this.add(vistaPaleta,BorderLayout.SOUTH);
		JPanel cmd = new JPanel(new GridLayout(0,2));
		vistaColor = new VistaColor(colorBase);
		cmd.add(vistaColor);
		JButton b = new JButton("Regenerar");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				int estrias = Integer.parseInt(estriasTF.getText());
				int modo = tiposCB.getSelectedIndex();
				if (modo < 0) {
					return;
				}
				colorBase = vistaColor.getColor();
				paleta = Paleta.crearPaleta(estrias, modo, colorBase);
				vistaPaleta.setPaleta(paleta);
				} catch (NumberFormatException ex) {
					return;
				}
			}
		});
		cmd.add(b);
		cmd.add(new JLabel("Tipo"));
		tiposCB = new JComboBox(tipos);
		cmd.add(tiposCB);
		cmd.add(new JLabel("Estrias"));
		estriasTF = new JTextField("5",3);
		cmd.add(estriasTF);
		this.add(cmd,BorderLayout.NORTH);
	}

	Paleta getPaleta() {
		return paleta;
	}

}

class VistaPaleta extends JPanel implements MouseListener, MouseMotionListener {
	static final long serialVersionUID = 1;
	Paleta paleta;
	int indiceSeleccionado = -1;
	
	VistaPaleta(Paleta pal) {
		this.paleta = pal;
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(200, 30));
	}
	
	void setPaleta(Paleta p) {
		this.paleta = p;
		repaint();
	}
	
	
	public void paintComponent(Graphics g) {
		int ancho = getWidth();
		int alto = getHeight();
		double dx = 1.0 / (double) ancho;
		double x = 0.0;
		// paleta mostrada como gradiente
		for (int i = 0; i < ancho; i++, x += dx) {
			g.setColor(paleta.getColorAt(x));
			g.drawLine(i, 0, i, alto);
		}
		// puntitos editables
		for (int i = 0; i < paleta.getNumColores(); i++) {
			x = paleta.getIndice(i);
			if (i == indiceSeleccionado) {
				g.setColor(Color.RED);				
			} else {
				g.setColor(Color.GRAY);
			}
			g.fillRect((int) (x * (double) ancho)+1, 1, 4, 8);
			g.setColor(Color.WHITE);
			g.drawRect((int) (x * (double) ancho), 0, 4, 8);
		}
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public int getIndiceColor(double x) {
		double mind = Double.MAX_VALUE;
		int mini = -1;
		for (int i = 0; i < paleta.getNumColores(); i++) {
			double d = Math.abs(paleta.getIndice(i)-x);
			if ( d < mind ) {
				mini = i;
				mind = d;
			}
		}
		return mini;
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int i = indiceSeleccionado;
		if (e.getClickCount() > 1) {
			Color c = paleta.getColor(i);
			c = JColorChooser.showDialog(this, "Cambiar color", c);
			if (c != null) {
				paleta.setColor(i, c);
				repaint();
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		//Log.trace("mousePressed()");
		double x = ((double)e.getX())/((double)this.getWidth());
		int i = getIndiceColor(x);
		indiceSeleccionado = i;
		repaint();
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		//Log.trace("mouseReleased()");
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		//Log.trace("mouseDragged()");
		double x = ((double)e.getX())/((double)this.getWidth());
		double prev = (indiceSeleccionado > 0)? paleta.getIndice(indiceSeleccionado-1): 0.0;
		double sig = (indiceSeleccionado < paleta.getNumColores()-1)? paleta.getIndice(indiceSeleccionado+1): 1.0;
		if ((x > prev) && (x < sig)) {
			paleta.setIndice(indiceSeleccionado, x);
			repaint();
		}
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
