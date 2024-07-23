import java.awt.*;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.table.*;

public class GameRunner {

	private static GamePanel panel;
	public static JTable table;
	private static JPanel main;
	public static int[][] resources;

	public static void main(String[] args) {
		JFrame window = new JFrame("Settlers of Catorus Assistant");
		JSlider slider = new JSlider(1, 3, 2);
		slider.setFont(new Font("Arial", Font.PLAIN, 50));
		panel = new GamePanel(slider);
		main = new JPanel();
		resources = new int[6][3];
		window.setContentPane(main);
		main.setLayout(new BorderLayout());
		main.add(panel, BorderLayout.CENTER);

		TableModel dataModel = new AbstractTableModel() {
			public int getColumnCount() {
				return 4;
			}

			public int getRowCount() {
				return 7;
			}

			public Object getValueAt(int row, int col) {
				if (row == 0) {
					if (col == 1) {
						return "Orange";
					}
					if (col == 2) {
						return "Green";
					}
					if (col == 3) {
						return "Purple";
					}
					return "Roll:";
				} else if (col == 0) {
					return row;
				} else {
					if (resources[row - 1][col - 1] == 0) {
						return " ";
					} else {
						return resources[row - 1][col - 1];
					}
				}
			}
		};
		table = new JTable(dataModel);
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		table.setRowHeight(table.getRowHeight() + 10);
		main.add(table, BorderLayout.LINE_END);

//		main.add(new JButton("Reset"), BorderLayout.PAGE_END);
		main.add(slider, BorderLayout.PAGE_END);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1200, 800);
		window.setResizable(false);
		window.setVisible(true);

		slider.addChangeListener(panel);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);

		Hashtable labelTable = new Hashtable();
		labelTable.put(1, new JLabel("Orange"));
		labelTable.put(2, new JLabel("Green"));
		labelTable.put(3, new JLabel("Purple"));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.setGridColor(Color.BLACK);
		slider.setLabelTable(labelTable);

		slider.setPaintLabels(true);

	}
}
