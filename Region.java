import java.awt.Color;
import java.awt.Graphics;

public class Region {
	public static final Color BROWN = new Color(160, 82, 45);
	public static final Color RED = new Color(250, 128, 114);
	public static final Color BLUE = new Color(51, 153, 255);
	public static final Color YELLOW = new Color(255, 255, 0);
	private static final double scale = 0.5;
	public static final Color GRAYBROWN = new Color((int) (scale * 160), (int) (scale * 82), (int) (scale * 45));
	public static final Color GRAYRED = new Color((int) (scale * 250), (int) (scale * 128), (int) (scale * 114));
	public static final Color GRAYBLUE = new Color((int) (scale * 51), (int) (scale * 153), (int) (scale * 255));
	public static final Color GRAYYELLOW = new Color((int) (scale * 255), (int) (scale * 255), (int) (scale * 0));
	private Color[] colors = { BLUE, YELLOW, BROWN, RED, YELLOW, BROWN, RED };
	private Color[] graycolors = { GRAYBLUE, GRAYYELLOW, GRAYBROWN, GRAYRED, GRAYYELLOW, GRAYBROWN, GRAYRED };

	public static final int length = 85;
	public int type;
	private int x, y;

	public Region(int type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		for (int i = -5; i <= 5; i++) {
			for (int j = -5; j <= 5; j++) {
				drawHex(g, x + length * (4 * i + 5 * j), y + length * (i - 4 * j));
			}
		}
	}

	private void drawHex(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawPolygon(
				new int[] { (int) (x + y / 2.0 + length), (int) (x + (y + length) / 2.0),
						(int) (x + (y + length) / 2.0 - length), (int) (x + (y) / 2.0 - length),
						(int) (x + (y - length) / 2.0), (int) (x + (y - length) / 2.0 + length) },
				new int[] { (int) (y * Math.sqrt(3) / 2), (int) ((y + length) * Math.sqrt(3) / 2),
						(int) ((y + length) * Math.sqrt(3) / 2), (int) ((y) * Math.sqrt(3) / 2),
						(int) ((y - length) * Math.sqrt(3) / 2), (int) ((y - length) * Math.sqrt(3) / 2) },
				6);
//		System.out.print(String.valueOf(type));
		if (x == this.x && y == this.y) {
			g.setColor(colors[type]);
		} else {
			g.setColor(graycolors[type]);
		}
		g.fillPolygon(
				new int[] { (int) (x + y / 2.0 + length), (int) (x + (y + length) / 2.0),
						(int) (x + (y + length) / 2.0 - length), (int) (x + (y) / 2.0 - length),
						(int) (x + (y - length) / 2.0), (int) (x + (y - length) / 2.0 + length) },
				new int[] { (int) (y * Math.sqrt(3) / 2), (int) ((y + length) * Math.sqrt(3) / 2),
						(int) ((y + length) * Math.sqrt(3) / 2), (int) ((y) * Math.sqrt(3) / 2),
						(int) ((y - length) * Math.sqrt(3) / 2), (int) ((y - length) * Math.sqrt(3) / 2) },
				6);
		g.setColor(Color.BLACK);
		if (type != 0) {
			g.drawString(String.valueOf(type), (int) (x + y / 2.0) - 10, (int) (y * Math.sqrt(3) / 2) + 10);
		}
	}

	public boolean inHex(int i1, int j1) {
		for (int i = -5; i <= 5; i++) {
			for (int j = -5; j <= 5; j++) {
				int x1 = x + length * (4 * i + 5 * j) - 250;
				int y1 = y + length * (i - 4 * j) - 400;
				int dx = x1 - i1 * length;
				int dy = y1 - j1 * length;
//				System.out.print(dx);
//				System.out.print(" ");
//				System.out.print(dy);
//				System.out.println();
//				if (dx >= -1 && dx <= 1 && dy >= -1 && dy <= -1) {
//					return true;
//				}
				if (dx * dx + dy * dy <= (length * 1.5) * (length * 1.5)) {
					return true;
				}
			}
		}
		return false;
	}
}
