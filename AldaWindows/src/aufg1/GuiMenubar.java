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

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;


public class GuiMenubar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private GuiDictionary guiDict;
	private JMenuItem readDict;
	private JFileChooser fc;
	private JRadioButtonMenuItem selectSortedArray;
	private JRadioButtonMenuItem selectHash;
	private JRadioButtonMenuItem selectTree;
	private JRadioButtonMenuItem selectHashMap;
	private JRadioButtonMenuItem selectTreeMap;
	
	public GuiMenubar(GuiDictionary guiD) {
		//initialize Dictionary
		guiDict = guiD;
		
		//Menubar data "Datei"
		JMenu data = new JMenu("Datei");
		this.add(data);
		
		//read Dictionary from file
		readDict = new JMenuItem("Woerterbuch einlesen");
		readDict.addActionListener(this);
		data.add(readDict);
		//create file chooser for read-button
		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("TXT Dateien", "txt"));
		
		//select Dictionary
		data.addSeparator();
		ButtonGroup selectDictGroup = new ButtonGroup();
		//firstButton
		selectSortedArray = new JRadioButtonMenuItem("Sorted-Array Dictionary");
		selectSortedArray.addActionListener(this);
		selectSortedArray.setSelected(true); //default is SortedArray
		selectDictGroup.add(selectSortedArray);
		data.add(selectSortedArray);
		//second Button
		selectHash = new JRadioButtonMenuItem("Hash Dictionary");
		selectHash.addActionListener(this);
		selectDictGroup.add(selectHash);
		data.add(selectHash);
		//thirdButton
		selectTree = new JRadioButtonMenuItem("Tree Dictionary");
		selectTree.addActionListener(this);
		selectDictGroup.add(selectTree);
		data.add(selectTree);
		//fourthButton
		selectHashMap = new JRadioButtonMenuItem("HashMap Dictionary");
		selectHashMap.addActionListener(this);
		selectDictGroup.add(selectHashMap);
		data.add(selectHashMap);
		//fifthButton
		selectTreeMap = new JRadioButtonMenuItem("TreeMap Dictionary");
		selectTreeMap.addActionListener(this);
		selectDictGroup.add(selectTreeMap);
		data.add(selectTreeMap);
		
	}
	
	 private void read(File f) {
	     LineNumberReader in = null; 
	     try {
	         in = new LineNumberReader(new FileReader(f));
	         String line;
	         while ((line = in.readLine()) != null) {
	             String[] sf = line.split(" ");
	             if (sf.length == 2) {
	            	 guiDict.getDictionary().insert( sf[0], sf[1]);
	            	 guiDict.refreshInfoSize();
	             } else {
	                 //TODO error in File , not two values pop up window
	             }
	         }
	         in.close();
	     } catch (IOException ex) {
	         Logger.getLogger(guiDict.getDictionary().getClass().
	        		 getName()).log(Level.SEVERE, null, ex);
	     }
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == readDict) {
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// selected file is delivered to the read method which puts the values into the dictionary
				read(fc.getSelectedFile());
			} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				//TODO cancel window
			}
		} else {
			if (source == selectSortedArray) {
				guiDict.setDictionary(new SortedArrayDictionary<String,String>());
			} else if (source == selectHash) {
				guiDict.setDictionary(new HashDictionary<String,String>(7));
			} else if (source == selectTree) {
				guiDict.setDictionary(new TreeDictionary<String,String>());			
			} else if (source == selectHashMap) {
				guiDict.setDictionary(new MapDictionary<String,String>(new HashMap<String,String>()));				
			} else if (source == selectTreeMap) {
				guiDict.setDictionary(new MapDictionary<String,String>(new TreeMap<String,String>()));	
			}

			//refresh Dictionary Type and Size 
			guiDict.refreshTypDict();
			guiDict.refreshInfoSize();
		}
		
	}
}
