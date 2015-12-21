package aufg1;

import java.awt.GridLayout;

import javax.swing.*;

public class GuiDictionary extends JFrame {

	private static final long serialVersionUID = 1L;
	private Dictionary<String, String> dictionary;

	private JLabel typDictLabel;
	private JLabel sizeDictLabel;
	private JTextArea outputTextArea;

	/**
	 * default creates SortedArrayDictionary<String,String>
	 */
	public GuiDictionary() {
		new GuiDictionary(new SortedArrayDictionary<String, String>());
	}

	public GuiDictionary(Dictionary<String, String> dict) {
		dictionary = dict;

		// Menubar
		this.setJMenuBar(new GuiMenubar(this));

		// create MainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		this.setContentPane(mainPanel);// setzt mainPanel als Panel des
										// Hauptfensters ein

		// add Information Panel
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		infoPanel.setLayout(new GridLayout(2, 2));
		infoPanel.add(new JLabel("Dictionary Typ:"));
		typDictLabel = new JLabel("");
		refreshTypDict();
		infoPanel.add(typDictLabel);
		infoPanel.add(new JLabel("Anzahl Eintraege:"));
		sizeDictLabel = new JLabel("");
		refreshInfoSize();
		infoPanel.add(sizeDictLabel);
		mainPanel.add(infoPanel);

		// add Performance Panel
		mainPanel.add(new GuiPerformance(this));

		// add Search Insert Find Panel
		mainPanel.add(new GuiSearchInsertFind(this));

		// Output TextArea################
		JPanel outputPanel = new JPanel();
		this.add(outputPanel);
		outputPanel.setBorder(BorderFactory.createTitledBorder("Ausgabe"));
		outputTextArea = new JTextArea(20, 50);
		outputTextArea.setEditable(false);
		outputPanel.add(outputTextArea);
		outputPanel.add(new JScrollPane(outputTextArea));

		// other preparations for mainframe
		this.setTitle("Woerterbuch");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public void refreshInfoSize() {
		sizeDictLabel.setText(Integer.toString(dictionary.size()));
	}

	public void refreshTypDict() {
		String type;
		if (dictionary instanceof SortedArrayDictionary<?, ?>) {
			type = "Sorted-Array Dictionary";
		} else if (dictionary instanceof HashDictionary<?,?>) {
			type = "Hash Dictionary";
		} else if (dictionary instanceof TreeDictionary<?,?>) {
			type = "Tree Dictionary";
		} else if (dictionary instanceof MapDictionary<?,?>) {
			type = "Map Dictionary " + ((MapDictionary<?, ?>)dictionary).getInternalClassName();
		} else {
			type = "No Type";
		}
		typDictLabel.setText(type);
	}
	
	public String getTypDict() {
		return typDictLabel.getText();
	}

	public void setOutputTextArea(String s) {
		outputTextArea.setText(s);
	}

	public void setDictionary(Dictionary<String, String> dict) {
		dictionary = dict;
	}

	public Dictionary<String, String> getDictionary() {
		return dictionary;
	}

	public static void main(final String[] args) {
		new GuiDictionary();
	}

}
