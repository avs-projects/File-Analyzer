
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected JTextField pathField;
	
	protected JButton choseFolder;
	protected JButton startButton;
	protected JButton clearButton;
	
	protected JTable table;
	
	protected JScrollPane scroll;
	
	private JPanel contentPane;
	
	private String [][] donnees ;
	
	private final AnalyseSaveInterface explore;
	
	public GUI (String title) {
		
		super (title);
		
		explore = new AnalyseSave() ;
		
		initLayout();
		
		initActions();
		
	}
	
	/*
	 * Allows you to initiate program buttons
	 */
	
	protected void initActions () {
		choseFolder.addActionListener(new ChoseFolder());
		startButton.addActionListener(new ListFolder());
		clearButton.addActionListener(new Clear());
	}
	
	/**
	 * Allows to initiate a table of data
	 * @param donnees File information
	 * @param entetes Title of his information
	 * @return a table
	 */
	
	protected DefaultTableModel initModel (String [][] donnees, String [] entetes) {
		
		DefaultTableModel model =  new DefaultTableModel(donnees, entetes) {
  			private static final long serialVersionUID = 1L; 
		};
		return model;
	}
	
	/**
	 * Allows you to initiate the display
	 */
	
	protected void initLayout () {
		
		contentPane = new JPanel(new BorderLayout());
		pathField = new JTextField(30);
		choseFolder = new JButton ("Rechercher dans...");
		startButton = new JButton("Lancer");
		clearButton = new JButton("Clear");
		
		donnees = new String[][] {};
		String[] entetes = new String[] {"Name","Type","Ext","Mime","Octet(s)","Check"};
		
		DefaultTableModel model = initModel (donnees, entetes);
    	
		table = new JTable(model);
		table.scrollRectToVisible(getBounds());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scroll = new JScrollPane(table);
		
		JPanel choice = new JPanel ();
		choice.add(pathField);
		choice.add(choseFolder);
		
		JPanel start = new JPanel ();
		start.add(startButton);
		start.add(clearButton);
		
		JPanel tab = new JPanel ();
		tab.add(scroll);
		
		contentPane.add(choice, BorderLayout.NORTH);
		contentPane.add(start, BorderLayout.SOUTH);
		contentPane.add(tab, BorderLayout.CENTER);
		
 		setContentPane(contentPane);
    	pack();
    	
		setLocationRelativeTo(null); 
  		setVisible(true); 
  		setDefaultCloseOperation(EXIT_ON_CLOSE);
  		
    }
	
	/*
	 * Allows you to select a file
	 */

	private class ChoseFolder implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser ();
			chooser.setFileSelectionMode (JFileChooser.FILES_AND_DIRECTORIES);
			
			int press = chooser.showOpenDialog(null);
			if (press ==JFileChooser.APPROVE_OPTION) {
				File folder = chooser.getSelectedFile();
				pathField.setText(folder.getAbsolutePath());
				
			}
		}
	}
	
	/*
	 * Allows you to analyze files
	 */
	
	private class ListFolder implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			if (new File(pathField.getText()).isFile()) {
				try {
					explore.file(pathField.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				try {
					explore.list(pathField.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			explore.textSave("informations.txt");
			explore.serializationSave("informations.ser");
			
			for (Map.Entry<String,Information> entry : explore.getInformations().entrySet()) {
				Tab (entry.getValue(),entry.getKey());
				
			}			
		}
	}
	
	/*
	 * Allows you to redo the analysis with another file
	 */
	
	public class Clear implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			pathField.setText("");
			
			initLayout();
			
			initActions();
		}
	}
	
	/** 
	 * Allows you to put file information in a Table
	 * @param info File information
	 * @param path The file path
	 */
	
	private void Tab (Information info, String path) {
		
		DefaultTableModel model =(DefaultTableModel) table.getModel();
  		File f = new File(path);
		String[] tab = new String[] {
			info.getName(),
			f.isFile() ? "FILE" : "DIR",
			info.getExt(),
			info.getMime(),
			String.valueOf(info.getSize()),
			info.getInfo(),
		};
		model.addRow(tab);		
	}
	
	public static void main(String[] args) {
		new GUI("Analyse");
	}
}