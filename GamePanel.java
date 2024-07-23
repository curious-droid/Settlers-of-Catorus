import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.*;

public class GamePanel extends JPanel implements ChangeListener {

	public Region[] regions;
	public int[][] settlements;
	public int[][][][] roads;
	private JSlider slider;
	public int player = 2;
	public static final Color ORANGE = new Color(255,165,0);
	public static final Color GREEN = new Color(0, 153, 0);
	public static final Color PURPLE = new Color(138,43,226);
	private Color[] colors = { ORANGE, GREEN, PURPLE };
	private static final double scale = 0.5;
	public static final Color GRAYORANGE = new Color((int)(scale*255),(int)(scale*165),0);
	public static final Color GRAYGREEN = new Color(0, (int)(scale*153), 0);
	public static final Color GRAYPURPLE = new Color((int)(scale*138),(int)(scale*43),(int)(scale*226));
	private Color[] graycolors = { GRAYORANGE, GRAYGREEN, GRAYPURPLE };

	public GamePanel(JSlider slider) {
		player = 2;
		this.addMouseListener(new ClickListener(this));
		this.slider = slider;
		settlements = new int[41][41];
		roads = new int[41][41][41][41];
		regions = new Region[7];
		regions[0] = new Region(0, 250, 400);
		regions[1] = new Region(1, 250 - Region.length, 400 + 2 * Region.length);
		regions[2] = new Region(2, 250 + Region.length, 400 + Region.length);
		regions[3] = new Region(3, 250 + 2 * Region.length, 400 - Region.length);
		regions[4] = new Region(4, 250 + Region.length, 400 - 2 * Region.length);
		regions[5] = new Region(5, 250 - Region.length, 400 - Region.length);
		regions[6] = new Region(6, 250 - 2 * Region.length, 400 + Region.length);
	}

	private static int[] outlinex = {2,2,1,0,-1,-2,-2,-3, -3, -2, -2, -1, 0,  1, 2,  2, 3, 3};
	private static int[] outliney = {0,1,2,2, 3, 3,2, 2, 1,    0, -1, -2,-2, -3,-3, -2, -2, -1};

	private boolean center(int x, int y) {
//		int newi = x * Region.length + 250, newj = y * Region.length + 400;
//		int i = (int) (newi + newj / 2.0);
//		int j = (int) (newj * Math.sqrt(3) / 2.0);
		return (Math.abs(x) <= 3) && (Math.abs(y) <= 3) && (Math.abs(x+y) <= 3);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		super.paintComponent(g);
		for (int i = 0; i < 7; i++) {
			regions[i].draw(g);
		}

		for(int i = 0; i < 18; i++) {
			int j = (i+1) % 18;
			int newi = outlinex[i] * Region.length + 250, newj = outliney[i] * Region.length + 400;
			int x = (int) (newi + newj / 2.0);
			int y = (int) (newj * Math.sqrt(3) / 2.0);
			int newi2 = outlinex[j] * Region.length + 250, newj2 = outliney[j] * Region.length + 400;
			int x2 = (int) (newi2 + newj2 / 2.0);
			int y2 = (int) (newj2 * Math.sqrt(3) / 2.0);
			g.setColor(new Color(0, 255, 51));
//			g.drawLine(x, y, x2, y2);
	        Graphics2D g2 = (Graphics2D) g;
	        Stroke oldStroke = g2.getStroke();
	        g2.setStroke(new BasicStroke(5));
	        g2.draw(new Line2D.Float(x, y, x2, y2));
	        g2.setStroke(oldStroke);
			g.setColor(Color.BLACK);
		}

		for (int i = -20; i <= 20; i++) {
			for (int j = -20; j <= 20; j++) {
				for (int i2 = -20; i2 <= 20; i2++) {
					for (int j2 = -20; j2 <= 20; j2++) {
						if(roads[20+i][20+j][20+i2][20+j2] != 0) {
							int newi = i * Region.length + 250, newj = j * Region.length + 400;
							int x = (int) (newi + newj / 2.0);
							int y = (int) (newj * Math.sqrt(3) / 2.0);
							int newi2 = i2 * Region.length + 250, newj2 = j2 * Region.length + 400;
							int x2 = (int) (newi2 + newj2 / 2.0);
							int y2 = (int) (newj2 * Math.sqrt(3) / 2.0);
							if(center(i,j) && center(i2, j2)) {
								g.setColor(colors[roads[20+i][20+j][20+i2][20+j2] - 1]);
							}
							else {
								g.setColor(graycolors[roads[20+i][20+j][20+i2][20+j2] - 1]);
							}
//							g.drawLine(x, y, x2, y2);
			                Graphics2D g2 = (Graphics2D) g;
			                Stroke oldStroke = g2.getStroke();
			                g2.setStroke(new BasicStroke(10));
			                g2.draw(new Line2D.Float(x, y, x2, y2));
			                g2.setStroke(oldStroke);
							g.setColor(Color.BLACK);
						}
					}
				}
			}
		}
		for (int i = -20; i <= 20; i++) {
			for (int j = -20; j <= 20; j++) {
				if (settlements[20 + i][20 + j] != 0) {
					int newi = i * Region.length + 250, newj = j * Region.length + 400;
					int x = (int) (newi + newj / 2.0);
					int y = (int) (newj * Math.sqrt(3) / 2.0);
					g.setColor(Color.BLACK);
					g.drawOval(x - 20, y - 20, 40, 40);
					if(center(i, j)) {
						g.setColor(colors[settlements[20 + i][20 + j] - 1]);
					}
					else {
						g.setColor(graycolors[settlements[20 + i][20 + j] - 1]);
					}
					g.fillOval(x - 20, y - 20, 40, 40);
					g.setColor(Color.BLACK);
				}
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		player = slider.getValue();
	}
}
