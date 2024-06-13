package Utilities;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.FncLookAndFeel;
import components.JTBorderFactory;
import components._JInternalFrame;

public class code_break extends _JInternalFrame implements ActionListener {

	private static final long serialVersionUID = -8167400139849514089L;
	static String title = "Code Break";
	Dimension frameSize = new Dimension(600, 600);
	
	private static JLabel lblDir;
	private static JTextField txtDir;
	
	private JTextArea txtLog;
	private JTextArea txtCode;
	private JTextArea txtSub;
	
	private JScrollPane scrLog;
	private JScrollPane scrCode;
	private JScrollPane scrSub;
	
	private final static String newline = "\n";
	
	private JButton btnRead;
	private JButton btnBreak;
	
	public code_break() {
		super(title, true, true, false, false);
		initGui();
	}

	public code_break(String title) {
		super(title);
		initGui();
	}

	public code_break(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGui();
	}
	
	private void initGui() {
		this.setSize(frameSize);
		this.setTitle(title);
		
		JXPanel Main = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(Main);
		Main.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel MainDiv = new JXPanel(new GridLayout(2, 1, 5, 5));
			Main.add(MainDiv, BorderLayout.CENTER);
			{
				txtLog = new JTextArea();
				txtLog.setFont(new Font("Tahoma", Font.PLAIN, 9));
				txtLog.setBackground(Color.BLACK);
				txtLog.setForeground(Color.WHITE);
				txtLog.setEditable(false);
			}
			{
				scrLog = new JScrollPane(txtLog);
				MainDiv.add(scrLog, BorderLayout.CENTER);
			}
			JXPanel pnlRes = new JXPanel(new GridLayout(1, 2, 5, 5));
			MainDiv.add(pnlRes, BorderLayout.CENTER);
			{
				JXPanel pnlTab = new JXPanel(new BorderLayout(5, 5));
				pnlRes.add(pnlTab, BorderLayout.CENTER);
				pnlTab.setBorder(JTBorderFactory.createTitleBorder("Database Objects", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						txtCode = new JTextArea();
						txtCode.setFont(new Font("Tahoma", Font.PLAIN, 9));
						txtCode.setBackground(Color.BLACK);
						txtCode.setForeground(Color.WHITE);
						txtCode.setEditable(false);
					}
					{
						scrCode = new JScrollPane(txtCode);
						pnlTab.add(scrCode, BorderLayout.CENTER);
					}
				}
				JXPanel pnlSubTab = new JXPanel(new BorderLayout(5, 5));
				pnlRes.add(pnlSubTab, BorderLayout.CENTER);
				pnlSubTab.setBorder(JTBorderFactory.createTitleBorder("Inner Objects", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						txtSub = new JTextArea();
						txtSub.setFont(new Font("Tahoma", Font.PLAIN, 9));
						txtSub.setBackground(Color.BLACK);
						txtSub.setForeground(Color.WHITE);
						txtSub.setEditable(false);
					}
					{
						scrSub = new JScrollPane(txtSub);
						pnlSubTab.add(scrSub, BorderLayout.CENTER);
					}
				}
			}
		}
		JXPanel pnlEnd = new JXPanel(new BorderLayout(5, 5));
		Main.add(pnlEnd, BorderLayout.PAGE_END);
		pnlEnd.setPreferredSize(new Dimension(0, 100));
		{
			JXPanel pnlDir = new JXPanel(new BorderLayout(5, 5));
			pnlEnd.add(pnlDir, BorderLayout.CENTER);
			pnlDir.setBorder(JTBorderFactory.createTitleBorder("Directory", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				lblDir = new JLabel("Directory");
				pnlDir.add(lblDir, BorderLayout.LINE_START);
			}
			{
				txtDir = new JTextField("");
				pnlDir.add(txtDir, BorderLayout.CENTER);
				txtDir.setHorizontalAlignment(JTextField.LEADING);
				txtDir.setEditable(false);
			}
			JXPanel pnlButtons = new JXPanel(new GridLayout(1, 2, 5, 5));
			pnlEnd.add(pnlButtons, BorderLayout.PAGE_END);
			pnlButtons.setPreferredSize(new Dimension(0, 30));
			{
				btnRead = new JButton("Read");
				pnlButtons.add(btnRead, BorderLayout.CENTER);
				btnRead.setActionCommand("Read");
				btnRead.addActionListener(this);
			}
			{
				btnBreak = new JButton("Break");
				pnlButtons.add(btnBreak, BorderLayout.CENTER);
				btnBreak.setActionCommand("Break");
				btnBreak.addActionListener(this);
			}	
		}
	}
    
	public String OpenDir(String strType) {
		String strDir = "";
		
		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Folder");
		
		if (strType.equals("Folder")) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		else {
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}
		
		chooser.setAcceptAllFileFilterUsed(false);
    
		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){ 
			strDir = chooser.getSelectedFile().toString();
		}
		else{
			System.out.println("No Selection ");
		}

		return strDir;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Read")) {
			peruser(OpenDir(""));
		}
		else if (e.getActionCommand().equals("Break")) {
			usurper(txtDir.getText());
		}
	}
	
    public void peruser(String strDir) {
    	System.out.println("");
    	System.out.println(strDir);
    	txtDir.setText(strDir);
    	
    	File peruse = new File(strDir);
    	Scanner scan = null;
		try {
			scan = new Scanner(peruse);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
    	
		String strLine = "";
		txtLog.setText("");
		txtCode.setText("");
		txtSub.setText("");
    	while(scan.hasNextLine()) {
    		strLine = scan.nextLine();
    		txtLog.append(strLine + newline);
    	}
    	
    	scan.close();
    	txtLog.moveCaretPosition(0);
    }
    
    public void usurper(String strDir) {
    	System.out.println("");
    	System.out.println(strDir);
    	txtDir.setText(strDir);
    	
    	File usurp = new File(strDir);
    	Scanner scan = null;
		try {
			scan = new Scanner(usurp);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
    	
		String strCode = "";
		txtCode.setText("");
    	while(scan.hasNextLine()) {
    		strCode = strCode + scan.nextLine();
    	}
    	
    	scan.close();
    	breaker(strCode);
    	txtCode.moveCaretPosition(0);
    }
    
    public void breaker(String strCode) {
    	String SQL = "";
		pgSelect sqlTable = new pgSelect();
		pgSelect sqlRoutine = new pgSelect();
		
    	SQL = "SELECT x.table_name\n" +
    		  "FROM information_schema.tables x\n" +
    		  "ORDER BY x.table_name";
    	sqlTable.select(SQL);
    	
		System.out.println("");
		if(sqlTable.isNotNull()){
			txtCode.append("Tables" + newline + "------------------------------------------------------------------" + newline);
			for(int x = 0; x < sqlTable.getRowCount(); x++) {
				if (strCode.indexOf((String)sqlTable.getResult()[x][0]) > 0) {
					System.out.println((String)sqlTable.getResult()[x][0]);
					txtCode.append((String)sqlTable.getResult()[x][0] + newline);
				}
			}
		}
		
		txtCode.append(newline);
		
    	SQL = "SELECT proname\n" +
    		  "FROM pg_catalog.pg_namespace n\n" +
    		  "JOIN pg_catalog.pg_proc p ON pronamespace = n.oid\n" +
    		  "WHERE nspname = 'public'\n" +
    		  "ORDER BY proname";
    	sqlRoutine.select(SQL);
		if(sqlRoutine.isNotNull()){
			txtCode.append("Routine" + newline + "------------------------------------------------------------------" + newline);
			for(int x = 0; x < sqlRoutine.getRowCount(); x++) {
				if (strCode.indexOf((String)sqlRoutine.getResult()[x][0]) > 0) {
					System.out.println((String)sqlRoutine.getResult()[x][0]);
					txtCode.append((String)sqlRoutine.getResult()[x][0] + newline);
				}
			}
		}
    }
    
    public void blasphemy(String strObj) {
    	pgSelect sqlBaphomet = new pgSelect();
    }
}
