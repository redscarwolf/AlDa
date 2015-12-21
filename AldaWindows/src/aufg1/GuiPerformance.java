package aufg1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GuiPerformance extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private GuiDictionary guiDict;
	private JComboBox<String> perfSizeComboBox;
	private JComboBox<String> perfTypeComboBox;
	private JButton perfButton;
	private JLabel perfOutput;

	public GuiPerformance(GuiDictionary guiD) {
		guiDict = guiD;

		// Performance Panel #############
		JPanel perfPanel = new JPanel();
		// perfBorder
		Border perfBorder = BorderFactory
				.createTitledBorder("Performance-Untersuchen");
		perfPanel.setBorder(perfBorder);
		this.add(perfPanel);
		perfPanel.add(new JLabel("Waehle Parameter:"));
		// Size
		String[] s = { "16.000", "8.000" };
		perfSizeComboBox = new JComboBox<>(s);
		perfPanel.add(perfSizeComboBox);
		// Type of testing
		String[] s2 = { "Aufbau eines Wörterbuchs", "Erfolgreiche Suche",
				"Nicht erfolgreiche Suche" };
		perfTypeComboBox = new JComboBox<>(s2);
		perfPanel.add(perfTypeComboBox);
		// Test Button
		perfButton = new JButton("Testen");
		perfButton.addActionListener(this);
		perfPanel.add(perfButton);
		// Output Label
		perfOutput = new JLabel();
		perfPanel.add(perfOutput);
	}

	private void read(File f) {
		LineNumberReader in = null;
		try {
			in = new LineNumberReader(new FileReader(f));
			String line;
			while ((line = in.readLine()) != null) {
				String[] sf = line.split(" ");
				if (sf.length == 2) {
					guiDict.getDictionary().insert(sf[0], sf[1]);
					guiDict.refreshInfoSize();
				} else {
					// TODO error in File , not two values pop up window
				}
			}
			in.close();
		} catch (IOException ex) {
			Logger.getLogger(guiDict.getDictionary().getClass().getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	private void searchR(File f) {
		LineNumberReader in = null;
		try {
			in = new LineNumberReader(new FileReader(f));
			String line;
			while ((line = in.readLine()) != null) {
				String[] sf = line.split(" ");
				if (sf.length == 2) {
					guiDict.getDictionary().search(sf[0]);
					guiDict.refreshInfoSize();
				} else {
					// TODO error in File , not two values pop up window
				}
			}
			in.close();
		} catch (IOException ex) {
			Logger.getLogger(guiDict.getDictionary().getClass().getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	private void searchW(File f) {
		LineNumberReader in = null;
		try {
			in = new LineNumberReader(new FileReader(f));
			String line;
			while ((line = in.readLine()) != null) {
				String[] sf = line.split(" ");
				if (sf.length == 2) {
					guiDict.getDictionary().search(sf[1]);
					guiDict.refreshInfoSize();
				} else {
					// TODO error in File , not two values pop up window
				}
			}
			in.close();
		} catch (IOException ex) {
			Logger.getLogger(guiDict.getDictionary().getClass().getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	private void evalTime(double start, double end) {
		perfOutput.setText(Double.toString((end - start) / 1000000000) + " s");
		guiDict.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Hash capacity
		int hashCap;
		// select File
		File testFile;
		if (perfSizeComboBox.getSelectedIndex() == 0) { // 16.000
			testFile = new File("src/aufg1/dtengl.txt");
			hashCap = 16001; // first primer number over 16.000
		} else { // 8.000
			testFile = new File("src/aufg1/dtengl8000.txt");
			hashCap = 8009; // first primer number over 8.000
		}
		// restart selected Dictionary
		if (guiDict.getDictionary() instanceof SortedArrayDictionary<?, ?>) {
			guiDict.setDictionary(new SortedArrayDictionary<String, String>());
		} else if (guiDict.getDictionary() instanceof HashDictionary<?, ?>) {
			guiDict.setDictionary(new HashDictionary<String, String>(hashCap));
		} else if (guiDict.getDictionary() instanceof TreeDictionary<?, ?>) {
			guiDict.setDictionary(new TreeDictionary<String, String>());
		} else if (guiDict.getDictionary() instanceof MapDictionary<?, ?>) {
			if ("Map Dictionary java.util.HashMap".equals(guiDict.getTypDict())) {
				guiDict.setDictionary(new MapDictionary<String, String>(
						new HashMap<String, String>()));
			} else {
				guiDict.setDictionary(new MapDictionary<String, String>(
						new TreeMap<String, String>()));
			}
		}

		// different Types
		if (perfTypeComboBox.getSelectedIndex() == 0) {
			// read
			double start = (double) System.nanoTime();
			read(testFile);
			double end = (double) System.nanoTime();
			evalTime(start, end);
		} else if (perfTypeComboBox.getSelectedIndex() == 1) {
			// Prepare: fill Dictionary
			read(testFile);
			// search right
			double start = (double) System.nanoTime();
			searchR(testFile);
			double end = (double) System.nanoTime();
			evalTime(start, end);

		} else if (perfTypeComboBox.getSelectedIndex() == 2) {
			// Prepare: fill Dictionary
			read(testFile);
			// search wrong
			double start = (double) System.nanoTime();
			searchW(testFile);
			double end = (double) System.nanoTime();
			evalTime(start, end);
		}
	}
}
