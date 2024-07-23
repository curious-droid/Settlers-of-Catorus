import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener {

	GamePanel panel;

	public ClickListener(GamePanel panel) {
		super();
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		System.out.print("here");
//		System.out.print(e.getX());
//		System.out.print(e.getY());
//		System.out.println();
		for (int i = -20; i <= 20; i++) {
			for (int j = -20; j <= 20; j++) {
				int newi = i * Region.length + 250, newj = j * Region.length + 400;
				int x = (int) (newi + newj / 2.0);
				int y = (int) (newj * Math.sqrt(3) / 2.0);
				if ((e.getX() - x) * (e.getX() - x) + (e.getY() - y) * (e.getY() - y) < newj) {
					addSettlement(i, j);
					return;
				}
			}
		}
		for (int i = -20; i <= 20; i++) {
			for (int j = -20; j <= 20; j++) {
				int newi = i * Region.length + 250, newj = j * Region.length + 400;
				int x = (int) (newi + newj / 2.0);
				int y = (int) (newj * Math.sqrt(3) / 2.0);
				if (checkdist(e.getX(), e.getY(), newi, newj, 60)) {
//					System.out.print(i);
//					System.out.print(" ");
//					System.out.print(j);
//					System.out.print(" ");
//					System.out.println("checking");
					if (checkdist(e.getX(), e.getY(), newi + Region.length, newj, 60)) {
						addRoad(i, j, i + 1, j);
						return;
					} else if (checkdist(e.getX(), e.getY(), newi + Region.length, newj - Region.length, 60)) {
						addRoad(i, j, i + 1, j - 1);
						return;
					} else if (checkdist(e.getX(), e.getY(), newi, newj - Region.length, 60)) {
						addRoad(i, j, i, j - 1);
						return;
					} else if (checkdist(e.getX(), e.getY(), newi + Region.length, newj, 60)) {
						addRoad(i, j, i - 1, j);
						return;
					} else if (checkdist(e.getX(), e.getY(), newi - Region.length, newj + Region.length, 60)) {
						addRoad(i, j, i - 1, j + 1);
						return;
					} else if (checkdist(e.getX(), e.getY(), newi, newj + Region.length, 60)) {
						addRoad(i, j, i, j + 1);
						return;
					}
				}
			}
		}
	}

	private boolean checkdist(int x1, int y1, int i, int j, int radius) {
		int x2 = (int) (i + j / 2.0);
		int y2 = (int) (j * Math.sqrt(3) / 2.0);
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= radius * radius;
	}

	private void addRoad(int x1, int y1, int x2, int y2) {
		if(good(x1, y1)) {
			return;
		}
		if(good(x2, y2)) {
			return;
		}
//		System.out.println("ADDING ROAD");
		for (int i = -5; i <= 5; i++) {
			for (int j = -5; j <= 5; j++) {
				int newi1 = x1 + (4 * i + 5 * j), newj1 = y1 + (i - 4 * j);
				int newi2 = x2 + (4 * i + 5 * j), newj2 = y2 + (i - 4 * j);
				if (newi1 < -20 || newi1 > 20 || newj1 < -20 || newj1 > 20) {
					continue;
				}
				if (newi2 < -20 || newi2 > 20 || newj2 < -20 || newj2 > 20) {
					continue;
				}
				if (panel.roads[20 + newi1][20 + newj1][20 + newi2][20 + newj2] == 0) {
					panel.roads[20 + newi1][20 + newj1][20 + newi2][20 + newj2] = panel.player;
				} else {
					panel.roads[20 + newi1][20 + newj1][20 + newi2][20 + newj2] = 0;
				}
			}
		}
		panel.repaint();
	}

	private boolean good(int x, int y) {
		return (3*1434+x-y) % 3 == 0;
	}
	
	private void addSettlement(int x, int y) {

		if(good(x, y)) {
			return;
		}
//		System.out.print(x-y);
		boolean update = false;
		for (int i = -5; i <= 5; i++) {
			for (int j = -5; j <= 5; j++) {
				int newi = x + (4 * i + 5 * j), newj = y + (i - 4 * j);
				if (newi < -20 || newi > 20 || newj < -20 || newj > 20) {
					continue;
				}
				if (panel.settlements[20 + newi][20 + newj] == 0) {
//					System.out.println(panel.player);
					panel.settlements[20 + newi][20 + newj] = panel.player;
//					System.out.println(panel.player);
					if (!update) {
						update = true;
						for (int k = 0; k < 6; k++) {
							GameRunner.resources[k][panel.player - 1] += calc(x, y, k + 1);
						}
					}
				} else {
					if (!update) {
						update = true;
						for (int k = 0; k < 6; k++) {
							GameRunner.resources[k][panel.settlements[20 + newi][20 + newj] - 1] -= calc(x, y, k + 1);
						}
					}
					panel.settlements[20 + newi][20 + newj] = 0;
				}
			}
		}
		GameRunner.table.repaint();
		panel.repaint();
	}

	private int calc(int x, int y, int k) {
		int count = 0;
		if (panel.regions[k].inHex(x, y)) {
			count++;
		}
//		else {
//			System.out.print("Not in hex ");
//			System.out.println(k);
//		}
		return count;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
