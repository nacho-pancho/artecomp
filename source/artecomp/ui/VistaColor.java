package artecomp.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JColorChooser;
import java.awt.event.*;

public class VistaColor extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Color color;
	
	private static java.awt.Dimension prefdim =new java.awt.Dimension(100,20);

	private static java.awt.Dimension mindim =new java.awt.Dimension(30,10);

	public VistaColor(Color c) {
		this.color = c;
		this.addMouseListener(this);
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public java.awt.Dimension getPreferredSize() {
		return prefdim;
	}

	public java.awt.Dimension getMinimumSize() {
		return mindim;
	}

	public void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		g.setColor(color);
		g.fillRect(0, 0, w, h);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Color c = JColorChooser.showDialog(this, "Elegir", color);
		if (c != null) {
			color = c;
			repaint();
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
