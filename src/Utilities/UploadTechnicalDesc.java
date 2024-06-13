package Utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.vdc.glasspane.JGlassLoading;

import Database.pgSelect;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class UploadTechnicalDesc extends _JInternalFrame implements _GUI {
	
	private static final long serialVersionUID = -3323972109208953923L;
	private JButton btnSelectFile;
	private JFileChooser fileChooser;
	private String selectedFile;
	private JScrollPane scrollTD;
	private JTextField txtFileName;
	private JCheckBox chkFacilities;
	private JButton btnCancel;
	private JButton btnSave;
	private JLabel tagProject;
	private _JLookup lookupPhase;
	private _JLookup lookupProject;
	private JLabel lblPhase;
	private JLabel lblProject;
	private JLabel lblFile;
	private JPanel pnlTD;
	private JTextArea txtArea;
	private ArrayList<String> txt = new ArrayList<String>();
	private String NL = System.getProperty("line.separator");
	
	private JGlassLoading glass;
	
	
	static String title = "Technical Description Import Utility";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	public UploadTechnicalDesc() {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadTechnicalDesc(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadTechnicalDesc(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setPreferredSize(new java.awt.Dimension(600, 450));
		this.setBounds(0, 0, 600, 450);
		this.setVisible(true);
		this.setTitle("Technical Description Import Utility");
		this.setClosable(true);
		this.getContentPane().setLayout(null);
		this.setIconifiable(true);
		
		glass = new JGlassLoading(this.getJMenuBar(), this.getContentPane(), this.getWidth(), this.getHeight());
		this.setGlassPane(glass);
		
		{
			pnlTD = new JPanel();
			getContentPane().add(pnlTD);
			pnlTD.setLayout(null);
			pnlTD.setBorder(new LineBorder(new java.awt.Color(0,0,0),1,false));
			pnlTD.setBounds(5, 5, 583, 412);
			{
				scrollTD = new JScrollPane();
				pnlTD.add(scrollTD);
				scrollTD.setBounds(9, 103, 565, 264);
				scrollTD.setBorder(new LineBorder(new java.awt.Color(0,0,0),1,false));
				{
					txtArea = new JTextArea();
					scrollTD.setViewportView(txtArea);
					txtArea.setEditable(false);
					txtArea.setFont(new Font("DIALOG", Font.PLAIN, 12));
					txtArea.setName("txtArea");
				}
			}
			{
				btnSelectFile = new JButton("Select");
				pnlTD.add(btnSelectFile);
				btnSelectFile.setFocusable(false);
				btnSelectFile.setBounds(481, 73, 93, 25);
				btnSelectFile.setName("btnSelectFile");
				btnSelectFile.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(fileChooser==null){
							fileChooser = new JFileChooser(new File("/backup/Technical Description/"));
							fileChooser.addChoosableFileFilter(new MyFilter());
							fileChooser.setAcceptAllFileFilterUsed(false);
						}else ;

						fileChooser.setSelectedFile(new File(""));
						//System.out.println(fileChooser.getFileView());
						int status = fileChooser.showOpenDialog(null);
						getContentPane().requestFocus();
						if (status == JFileChooser.APPROVE_OPTION) {
							if(fileChooser.getSelectedFile().equals(null)){
								JOptionPane.showMessageDialog(getParent(), "No Selected Document");
								btnState(false, true);
							}else{
								new Thread(new Runnable() {
									public void run() {
										glass.start();

										txtArea.setText("");
										selectedFile = fileChooser.getSelectedFile().toString();
										txtFileName.setText(selectedFile);
										try {
											read(selectedFile, "UTF-8");
										} catch (IOException e) {
											e.printStackTrace();
										}
										
										btnState(true, true);
										
										glass.stop();
									}
								}).start();
							}
						}else if (status == JFileChooser.CANCEL_OPTION) ;
					}
				});
			}
			{
				txtFileName = new JTextField();
				pnlTD.add(txtFileName);
				txtFileName.setEditable(false);
				txtFileName.setBounds(56, 73, 425, 24);
				txtFileName.setName("txtFileName");
			}
			{
				lblFile = new JLabel("File");
				pnlTD.add(lblFile);
				lblFile.setBounds(9, 73, 43, 24);
				lblFile.setName("lblFile");
			}
			{
				lblProject = new JLabel("Project");
				pnlTD.add(lblProject);
				lblProject.setBounds(9, 10, 43, 25);
				lblProject.setName("lblProject");
			}
			{
				lblPhase = new JLabel("Phase");
				pnlTD.add(lblPhase);
				lblPhase.setBounds(9, 41, 43, 25);
				lblPhase.setName("lblPhase");
			}
			{
				lookupProject = new _JLookup();
				pnlTD.add(lookupProject);
				lookupProject.setLookupSQL(SQL_PROJECT());
				lookupProject.setHorizontalAlignment(JLabel.CENTER);
				lookupProject.setReturnColumn(0);
				lookupProject.setBounds(56, 10, 70, 25);
				lookupProject.setName("lookupProject");
				lookupProject.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup) event.getSource()).getDataSet();
						if (data != null) {

							lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
							lookupPhase.setText("");
							lookupPhase.setEnabled(true);
							
							tagProject.setText(String.format("[ %s ]", data[1]));

							KEYBOARD_MANAGER.focusNextComponent();
						}
					}
				});
			}
			{
				lookupPhase = new _JLookup();
				pnlTD.add(lookupPhase);
				lookupPhase.setEnabled(false);
				lookupPhase.setReturnColumn(0);
				lookupPhase.setHorizontalAlignment(JLabel.CENTER);
				lookupPhase.setBounds(56, 41, 70, 25);
				lookupPhase.setName("lookupProject");
			}
			{
				tagProject = new JLabel("[ ]");
				pnlTD.add(tagProject);
				tagProject.setForeground(Color.BLUE);
				tagProject.setBounds(130, 10, 267, 25);
				tagProject.setName("tagProject");
			}
			{
				btnSave = new JButton("SAVE");
				pnlTD.add(btnSave);
				btnSave.setFocusable(false);
				btnSave.setEnabled(false);
				btnSave.setBounds(349, 375, 110, 30);
				btnSave.setName("btnSave");
				btnSave.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						String[ ] temp;
						String lot = null, block = null;
						int startWith = 0;
						//String txtToSave = "";
						String page_string1 = "";
						for(int x = 0; x < txt.size(); x++){
							if(txt.get(x).startsWith("Block") && txt.get(x).contains(":")){
								temp = txt.get(x).split(" ");
							
								for(int a=0; a < temp.length; a++){
									if(a==20){
										System.out.println("Block: "+temp[a]);
										block = temp[a];
									}
								}
								
							} else if(txt.get(x).startsWith("Lot") && txt.get(x).contains(":")){
								temp = txt.get(x).split(" ");
							
								for(int a=0; a < temp.length; a++){
									if(a==22){
										System.out.println("Lot: "+temp[a]);
										lot = temp[a];
									}
								}
							}
							if(block != null && lot != null){
								if(block.equals("36") && lot.equals("16")){
									System.out.printf("Display value of x: %s%n", txt.get(x));
								}
							}
							
							/*if(txt.get(x).startsWith("TECHNICAL DESCRIPTIONS"){
								
							}*/
							
							if(txt.get(x).startsWith("TECHNICAL DESCRIPTIONS")){
								System.out.println("Start********************"+NL);
								startWith = x;

							}
							String txtToSave = "";
							/*if(txt.get(x).endsWith("EDGARDO A. AGBADA")){
								for(int y = startWith; y <= x; y++){
									System.out.println(txt.get(y));
									txtToSave = txtToSave + txt.get(y) + "\n";
								}
								save(txtToSave.replace("'", "''").replace("\t", "        "), lookupPhase.getValue(), block, lot);
							}*/
							
							if(txt.get(x).endsWith("Notes:")){
								for(int y = startWith; y <= x; y++){
									//System.out.println(txt.get(y));
									
									if(block != null && lot != null){
										if(block.equals("11") && lot.equals("1")){
											System.out.printf("Display value of y: %s%n", txt.get(y));
										}
									}
									
									txtToSave = txtToSave + txt.get(y) + "\n";
								}
								if(block.equals("11") && lot.equals("1")){
									System.out.printf("Display value of tech desc to be saved: %s%n", txtToSave.replace("'", "''").replace("\t", "        "));
								}
								save(txtToSave.replace("'", "''"), lookupProject.getValue() ,lookupPhase.getValue(), block, lot);
								//save(txtToSave.replace("'", "''").replace("\t", "        "), lookupPhase.getValue(), block, lot);
							}
							
							if(txt.get(x).startsWith("Prepared and Verified by:")){
								System.out.println("Start********************"+NL);
								startWith = x;

							}
							
							String end_of_tech_desc = "";
							//Change value of this string depending on end string of tech desc per phase
							if(lookupProject.getValue().equals("015")) {
								end_of_tech_desc = "Verified by___________             Chief, Aggregates and Correction Section";
								if(lookupPhase.getValue().equals("5")) {
									end_of_tech_desc = "Verified by___________              Chief, Original & Other Survey Section";
								}
							}
							if(lookupProject.getValue().equals("017")){
								end_of_tech_desc = "Verified by___________              Chief, Original & Other Survey Section";
							}
							
							if(lookupProject.getValue().equals("019")) {
								end_of_tech_desc = "Verified by___________              Chief, Original & Other Survey Section";
							}
							
							if(lookupProject.getValue().equals("018")) {
								end_of_tech_desc = "Verified by___________              Chief, Original & Other Survey Section";
							}

							
							if(txt.get(x).endsWith(end_of_tech_desc)){
								Integer page1of2_index = null;
								/*if(txt.get(x).startsWith("Prepared and Verified by:")){
									System.out.println("Start********************"+NL);*/
									page1of2_index = x;

								//}
									
								System.out.printf("Display value of index: %s%n", page1of2_index);
								System.out.println("Dumaan dito sa page 2");
								System.out.printf("Display value of ending x: %s%n", txt.get(x));
								System.out.printf("DIsplay value of Statement %s%n", txt.get(x).trim().startsWith("DENR-Reg IV-A (CALABARZON)"));
								System.out.printf("Display value of Statement 2 %s%n: ", txt.get(x).trim().endsWith("DENR-Reg IV-A (CALABARZON)"));
								System.out.printf("Display value of Statement 2 %s%n: ", txt.get(x).trim().equals("DENR-Reg IV-A (CALABARZON)"));
								
								String txtToSave2 = "";
								
								//if(txt.get(x).equals("DENR-Reg IV-A (CALABARZON)")){
									System.out.println("Dumaan dito sa Before For Loop");
									for(int y = startWith; y <= x; y++){
										//System.out.println(txt.get(y));
										System.out.println("Dumaan dito sa For Loop");
										txtToSave2 = txtToSave2 + txt.get(y) + "\n";
									}
									
									saveTechDescPage2(txtToSave2.replace("'", "''"), lookupProject.getValue(),lookupPhase.getValue(), block, lot);
									//save(txtToSave.replace("'", "''").replace("\t", "        "), lookupPhase.getValue(), block, lot);
								//}
							}
							cancelState();
							
						}
						JOptionPane.showMessageDialog(UploadTechnicalDesc.this.getTopLevelAncestor(), "Technical Description Saved", "Save", JOptionPane.INFORMATION_MESSAGE);
					}
				});
			}
			{
				btnCancel = new JButton("CANCEL");
				pnlTD.add(btnCancel);
				btnCancel.setFocusable(false);
				btnCancel.setBounds(464, 375, 110, 30);
				btnCancel.setName("btnCancel");
				btnCancel.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						cancelState();
					}
				});
			}
			{
				chkFacilities = new JCheckBox("Include project facilities(Roads, Alleys, etc...)");
				pnlTD.add(chkFacilities);
				chkFacilities.setFocusable(false);
				chkFacilities.setBounds(132, 41, 349, 25);
				chkFacilities.setName("chkFacilities");
			}
		}
		//Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
	}

	private class MyFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File file) {
			String filename = file.getName();
			return file.isDirectory() || filename.endsWith(".txt");
		}

		public String getDescription() {
			return "*.txt";
		}
	}

	// Appends text to the document and ensure that it is visible
	private void appendText(String text) {
		try {
			Document doc = txtArea.getDocument();

			// Move the insertion point to the end
			txtArea.setCaretPosition(doc.getLength());

			// Insert the text
			txtArea.replaceSelection(text);

			// Convert the new end location
			// to view co-ordinates
			Rectangle r = txtArea.modelToView(doc.getLength());

			// Finally, scroll so that the new text is visible
			if (r != null) {
				scrollRectToVisible(r);
			}
		} catch (BadLocationException e) {
			System.out.println("Failed to append text: " + e);
		}
	}

	/** Read the contents of the given file. */
	private void read(String aFileName, String aEncoding) throws IOException {
		//TODO
		//log("Reading from file.");
		txt.clear();
		StringBuilder text = new StringBuilder();
		Scanner scanner = new Scanner(new FileInputStream(aFileName), aEncoding);
		String line;
		try {
			while(scanner.hasNextLine()){
				String aTxt = scanner.nextLine();
				text.append(aTxt + NL);
				line = aTxt;
				line = line.trim();
				if(line.length() > 0){
					txt.add(line);
				}
			}
		}
		finally{
			scanner.close();
		}
		//log(""+text);
		appendText(""+text);
	}

	/*private void log(String aMessage){
		System.out.println(aMessage);
	}*/

	private void btnState(Boolean bSave, Boolean bCancel){
		btnSave.setEnabled(bSave);
		btnCancel.setEnabled(bCancel);
	}

	private void cancelState(){
		lookupProject.setText("");
		tagProject.setText("[ ]");
		lookupPhase.setText("");
		lookupPhase.setEnabled(false);
		txtFileName.setText("");
		txtArea.setText("");
		chkFacilities.setSelected(false);

		btnState(false, true);
	}

/*	private static String getUnitID(String phase, String block, String lot){
		String strSQL ="select unit_id \n"+
				"from mf_unit_info \n"+
				"where phase='"+phase+"' \n"+
				"and block='"+block+"' \n"+
				"and lot='"+lot+"'";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}*/
	private void save(String techdesc, String proj_id ,String ph, String blk, String lt) {
			
			//if(ph.equals("2") && blk.equals("19") && lt.equals("1")){
				
				System.out.println("Dumaan dito sa new Tech Desc");
				String strSQL = "SELECT sp_update_techdesc('"+techdesc+"','"+proj_id+"' ,'"+ph+"', '"+blk+"', '"+lt+"');"; 
				FncSystem.out("Display saving of techdesc", strSQL);
				
				pgSelect db = new pgSelect();
				db.select(strSQL);
				
				//cancelState();
			//}
			
	}
	
	private void saveTechDescPage2(String proj_id,String techdesc, String ph, String blk, String lt){
		String strSQL = "SELECT sp_update_techdesc_v2('"+proj_id+"','"+techdesc+"', '"+ph+"', '"+blk+"', '"+lt+"');";
		
		FncSystem.out("Display saving of page 2", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);
		
		//cancelState();
	}
	
}
