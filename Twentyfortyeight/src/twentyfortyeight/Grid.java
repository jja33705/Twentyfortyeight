package twentyfortyeight;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Grid extends JPanel {
	public Grid() {
		this.repaint();
		this.setVisible(true);
	}
	
	public void paintComponenet(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 400, 400);
		g.drawLine(100, 0, 100, 400);
		g.drawLine(200, 0, 200, 400);
		g.drawLine(300, 0, 300, 400);
		g.drawLine(0, 100, 400, 100);
		g.drawLine(0, 200, 400, 200);
		g.drawLine(0, 300, 400, 300);
	}
}
