package ir.ai.gui;

import ir.ai.main.Main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

@SuppressWarnings("javadoc")
public class FileChooser extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel inputFileLabel;
	private JLabel comboLabel;
	
	private JTextField inputTextField;
	private JComboBox<String> algorithmCombo;
	
	private JPanel inputPanel;
	private JPanel comboPanel;

	private JButton inputButton;
	private JButton runButton;
	private JButton runAllButton;
	
	private Main mainClass;
	private String[] algorithms = new String[]{"BM25", "Skip Bi-Grams", "N-Grams", "Passage Term Matching", "Textual Alignment"};

	public FileChooser(Main mainClass) {
		super("AI - Information Retrieval");
		setSize(350, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.mainClass = mainClass;
		this.initComponents();

		this.setLayout(new GridLayout(5, 1));
		
		this.add(this.comboPanel, 0);
		this.add(this.inputFileLabel, 1);
		this.add(this.inputPanel, 2);
		this.add(this.runButton, 3);
		this.add(this.runAllButton, 4);
	}

	public void initComponents() {
		this.inputFileLabel = new JLabel("Select Corpus Folder:");
		this.comboLabel = new JLabel("Select Algorithm: ");

		this.inputPanel = new JPanel();
		this.comboPanel = new JPanel();
		this.inputPanel.setLayout(new BorderLayout());
		this.comboPanel.setLayout(new GridLayout(1, 2));

		this.inputTextField = new JTextField("Presidents");
		this.inputTextField.setSize(20, 130);
		
		this.algorithmCombo = new JComboBox<String>();
		for (String algorithm: algorithms){
			this.algorithmCombo.addItem(algorithm);
		}

		this.inputButton = new JButton("Browse..");
		this.runButton = new JButton("Hit it!");
		this.runAllButton = new JButton("Gotta Run'em All");
		this.inputButton.setSize(20, 50);
		this.inputButton.addActionListener(this);
		this.runButton.addActionListener(this);
		this.runAllButton.addActionListener(this);

		this.inputPanel.add(this.inputTextField, BorderLayout.CENTER);
		this.inputPanel.add(this.inputButton, BorderLayout.EAST);
		
		this.comboPanel.add(this.comboLabel);
		this.comboPanel.add(this.algorithmCombo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.inputButton)) {
			String fileName = this.getFileName();
			this.inputTextField.setText(fileName);
		}
		else if (e.getSource().equals(this.runButton)) {
			this.runButton.setText("Please wait ...");
			String selectedAlgorithm = this.algorithmCombo.getSelectedItem().toString();
			this.mainClass.runAlgorithmFor(selectedAlgorithm, this.inputTextField.getText());
			this.runButton.setText("Hit It!");
		} else {
			for (String algorithm: algorithms){
				this.runButton.setText("Please wait ...");
				this.mainClass.runAlgorithmFor(algorithm, this.inputTextField.getText());
				this.runButton.setText("Hit It!");
			}
		}
	}

	public String getFileName() {
		String fileName = "";
		String userDirLocation = System.getProperty("user.dir");
		JFileChooser chooser = new JFileChooser(userDirLocation);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int option = chooser.showOpenDialog(FileChooser.this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File sf = chooser.getSelectedFile();
			fileName = sf.getAbsolutePath();
		}
		return fileName;
	}
}
