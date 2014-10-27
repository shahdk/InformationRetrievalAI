package ir.ai.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;


public class DisplayTable extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel topPanel;
	private JTable table;
	private JScrollPane scrollPane;

	
	public DisplayTable(String[][] dataValues, String title, String[] keywords, final String[][] scores) {

		setTitle(title);
		setSize(1500, 400);
		setBackground(Color.gray);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel);
		
		// Create a new table instance
		table = new JTable(dataValues, keywords);
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int row = DisplayTable.this.table.getSelectedRow();
				int column = DisplayTable.this.table.getSelectedColumn();
				JOptionPane.showMessageDialog(DisplayTable.this, "Score: "+scores[row][column]);
			}
		});

		scrollPane = new JScrollPane(table);
		topPanel.add(scrollPane, BorderLayout.CENTER);
	}
}
