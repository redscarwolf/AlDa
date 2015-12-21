package aufg1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GuiSearchInsertFind extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private GuiDictionary guiDict;
	
	private JButton searchButton;
	private JComboBox<String> searchComboBox;
	private JTextField wordTextField;
	private JTextField valueTextField;
	private JButton showAllButton;
	
	
	
	public GuiSearchInsertFind(GuiDictionary guiD) {
		//initialize Dictionary
		guiDict = guiD;
		
		//this Panel preparation
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		//Search Area###################
		JPanel searchPanel = new JPanel();
		//Search Border
		Border border = BorderFactory.
			createTitledBorder("Suche Deutsch - Englisch");
		searchPanel.setBorder(border);
		this.add(searchPanel);
		wordTextField = new JTextField("",10);
		searchPanel.add(wordTextField);
		valueTextField = new JTextField("",10);
		valueTextField.setVisible(false);
		searchPanel.add(valueTextField);
		String[] s = {"suchen","einfuegen","loeschen"};
		searchComboBox = new JComboBox<>(s);
		searchComboBox.addActionListener(this);
		searchPanel.add(searchComboBox);
		searchButton = new JButton("Anwenden");
		searchButton.addActionListener(this);
		searchPanel.add(searchButton);
		
		//showAll ######################
		JPanel showAllPanel = new JPanel();
		//showAll Border
		Border showAllBorder = BorderFactory.
			createTitledBorder("Alles Anzeigen");
		showAllPanel.setBorder(showAllBorder);
		this.add(showAllPanel);
		showAllButton = new JButton("Alles anzeigen");
		showAllButton.addActionListener(this);
		showAllPanel.add(showAllButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == searchButton) {
			if (searchComboBox.getSelectedIndex() == 0) { //search
				String word = wordTextField.getText();
				String s = guiDict.getDictionary().search(word);
				if (s == null) {
					s = "Es wurde kein Eintrag fuer \"" + word + "\" gefunden.";
				}
				guiDict.setOutputTextArea(s);
			} else if (searchComboBox.getSelectedIndex() == 1) { //insert
				String word = wordTextField.getText();
				String value = valueTextField.getText();
				String s = guiDict.getDictionary().insert(word, value);
				//refresh size in InfoPanel
				guiDict.refreshInfoSize();
				//String Output
				StringBuffer sbuf = new StringBuffer();
				sbuf.append("Deu: "+ word + " Eng: "+ value 
						+" wurde eingefuegt. \n");
				if (s != null) {
					sbuf.append("(Es wurde "+ s +" durch "+ value +" ersetzt)");
				}
				guiDict.setOutputTextArea(sbuf.toString());
			} else if (searchComboBox.getSelectedIndex() == 2) { //remove
				String word = wordTextField.getText();
				String s = guiDict.getDictionary().remove(word);
				//refresh size in InfoPanel
				guiDict.refreshInfoSize();
				//String Output
				StringBuffer sbuf = new StringBuffer();
				if (s != null) {
					sbuf.append("Deu: "+ word + " Eng: "+ s 
							+" wurden geloescht. \n");
				} else {
					sbuf.append("(Es wurde kein Eintrag fuer "
							+ word +" gefunden)");
				}
				guiDict.setOutputTextArea(sbuf.toString());
			} 
		} else if (source == searchComboBox) {
			if (searchComboBox.getSelectedIndex() == 1) {
				//show 
				valueTextField.setVisible(true);
				guiDict.pack();
			} else {
				//invisible
				valueTextField.setText("");
				valueTextField.setVisible(false);
				guiDict.pack();
			}
		} else if (source == showAllButton) {
			guiDict.setOutputTextArea(guiDict.getDictionary().toString());
		}
	}
}
