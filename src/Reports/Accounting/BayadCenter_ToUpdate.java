package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JButton;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelAmountToUpdate;

public class BayadCenter_ToUpdate extends _JInternalFrame implements _GUI, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String title = "Generate To Update";
	Dimension frameSize = new Dimension(750, 450);
	private JScrollPane scrTab;
	private static modelAmountToUpdate modeltoupdate;
	private static _JTableMain tbl_toupdate;
	private static JList rowheadertoupdate;
	private static JFileChooser chooser;
	private static _JButton btngenerate;
	private static JTextField txtcompany;
	private static JTextField txtdirectory;
	private static _JButton btnpreview;
	private static _JButton btnsave;
	private static _JButton btncancel;
	private static _JButton btnset;
	private static _JLookup lookupcomp;
	protected static String co_id= "";
	protected String company;
	protected static  String co_logo;
	private static JProgressBar progressBar;
	

	
	
	public BayadCenter_ToUpdate() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public BayadCenter_ToUpdate(String title) {
		super(title);
		initGUI(); 
	}

	public BayadCenter_ToUpdate(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}
	public void initGUI() {
		this.setSize(frameSize);
		this.setTitle(title);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JPanel pnlnorth = new JPanel(new BorderLayout(5,5));
			panMain.add(pnlnorth, BorderLayout.NORTH);
			pnlnorth.setPreferredSize(new Dimension(0,120));
			{
				JPanel pnlfilter = new JPanel(new BorderLayout(5, 5));
				pnlnorth.add(pnlfilter, BorderLayout.CENTER);
				pnlfilter.setBorder(JTBorderFactory.createTitleBorder("Set Filter"));
				{
					JPanel pnl_label = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlfilter.add(pnl_label, BorderLayout.WEST);
					pnl_label.setPreferredSize(new Dimension(80, 0));
					{
						JLabel lblcompany = new JLabel("Comapny");
						pnl_label.add(lblcompany);
					}
					{
						JLabel lbldirectory = new JLabel("Directory");
						pnl_label.add(lbldirectory);
					}
					{
						JLabel lblall = new JLabel("");
						pnl_label.add(lblall);
					}
				}
				{
					JPanel pnl_fields = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlfilter.add(pnl_fields, BorderLayout.CENTER);
					{
						JPanel pnlcompany = new JPanel(new BorderLayout(5, 5));
						pnl_fields.add(pnlcompany);
						{
							lookupcomp = new _JLookup();
							lookupcomp.setReturnColumn(0);
							pnlcompany.add(lookupcomp, BorderLayout.WEST);
							lookupcomp.setPreferredSize(new Dimension(60, 0));
							lookupcomp.setLookupSQL(SQL_COMPANY());
							lookupcomp.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null) {
										co_id 		= (String) data[0];	
										company		= (String) data[1];
										co_logo		= (String) data[3];
										
										txtcompany.setText(company);
										btngenerate.setEnabled(true);
									}
								}
							});
						}
						{
							txtcompany = new JTextField();
							pnlcompany.add(txtcompany, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnldirectory = new JPanel(new BorderLayout(5, 5));
						pnl_fields.add(pnldirectory);
						{
							txtdirectory = new JTextField();
							txtdirectory.setText("");
							pnldirectory.add(txtdirectory, BorderLayout.CENTER);
						}
						{
							btnset = new _JButton("Set");
							btnset.setActionCommand("set");
							btnset.setEnabled(false);
							pnldirectory.add(btnset, BorderLayout.EAST);
							btnset.setPreferredSize(new Dimension(50, 0));
							btnset.addActionListener(this);
						}
					}
					{
						JLabel lbl = new JLabel("");
						pnl_fields.add(lbl);
					}
				}
			}
			{
				JPanel pnlNort_button = new JPanel(new BorderLayout(5, 5));
				pnlnorth.add(pnlNort_button, BorderLayout.EAST);
				pnlNort_button.setPreferredSize(new Dimension(150, 0));
				//pnlNort_button.setBorder(JTBorderFactory.createTitleBorder(""));
				{
					btngenerate = new _JButton("GENERATE");
					btngenerate.setEnabled(true);
					btngenerate.setActionCommand("generate");
					pnlNort_button.add(btngenerate);
					btngenerate.addActionListener(this);
				}
			}
		}
		{
			JPanel pnlCenter = new JPanel(new BorderLayout(5,5));
			panMain.add( pnlCenter, BorderLayout.CENTER);
			pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Listing"));
			{
				scrTab = new JScrollPane();
				pnlCenter.add(scrTab, BorderLayout.CENTER);
				{
					modeltoupdate = new modelAmountToUpdate();
					tbl_toupdate = new _JTableMain(modeltoupdate);
					scrTab.setViewportView(tbl_toupdate);
					modeltoupdate.setEditable(false);
					
					tbl_toupdate.getTableHeader().setEnabled(false);
					tbl_toupdate.getColumnModel().getColumn(0).setPreferredWidth(200);
					tbl_toupdate.getColumnModel().getColumn(1).setPreferredWidth(80);
					tbl_toupdate.getColumnModel().getColumn(2).setPreferredWidth(150);
					tbl_toupdate.getColumnModel().getColumn(3).setPreferredWidth(170);
					tbl_toupdate.getColumnModel().getColumn(4).setPreferredWidth(60);
					tbl_toupdate.getColumnModel().getColumn(5).setPreferredWidth(100);
					//tbl_toupdate.hideColumns("ID");
				}
				{
					rowheadertoupdate = tbl_toupdate.getRowHeader();
					scrTab.setRowHeaderView(rowheadertoupdate);
					scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			
		}
		{
			JPanel pnlsouth = new JPanel(new GridLayout(1, 5, 5, 5));
			panMain.add( pnlsouth, BorderLayout.SOUTH);
			pnlsouth.setPreferredSize(new Dimension(0, 30));
			{
				JLabel lbl = new JLabel();
				pnlsouth.add(lbl);
			}
			{
				btnpreview = new _JButton("Preview");
				btnpreview.setActionCommand("preview");
				btnpreview.setEnabled(true);
				pnlsouth.add(btnpreview);
				btnpreview.addActionListener(this);
			}
			{
				btnsave = new _JButton("Export");
				btnsave.setEnabled(false);
				btnsave.setActionCommand("export");
				pnlsouth.add(btnsave);
				btnsave.addActionListener(this);
			}
			{
				btncancel = new _JButton("Cancel");
				btncancel.setActionCommand("cancel");
				btncancel.setEnabled(false);
				pnlsouth.add(btncancel);
				btncancel.addActionListener(this);
			}
			{
				JLabel lbl = new JLabel();
				pnlsouth.add(lbl);
			}
		}
		
		enable_buttons(false, true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("generate")) {
			System.out.println("co_id: "+co_id);
			new Thread(new Runnable() {
				public void run() {
					FncGlobal.startProgress("Displaying list...");
					displaytoupdate(co_id);
					btnset.setEnabled(true);
					btngenerate.setEnabled(false);
					enable_buttons(true, true);
					FncGlobal.stopProgress();
					
				}
			}).run();
			Integer r = modeltoupdate.getRowCount();
			JOptionPane.showMessageDialog(getTopLevelAncestor(), "Generated "+r+" rows.", "", JOptionPane.INFORMATION_MESSAGE);
		}
		if(e.getActionCommand().equals("set")) {
			OpenDir("Folder");
		}
		
		if(e.getActionCommand().equals("export")) {
			if(txtdirectory.getText() == "") {
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select Directory", "", JOptionPane.WARNING_MESSAGE);
			}else {
				FncGlobal.startProgress("Creating file...");
				export_toupdte();
				FncGlobal.stopProgress();
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "Export successful.", "", JOptionPane.INFORMATION_MESSAGE);
				enable_buttons(false, true);
				btnset.setEnabled(false);
				txtdirectory.setText("");
			}
		}
		if(e.getActionCommand().equals("cancel")) {
			refresh();
			btngenerate.setEnabled(true);
			enable_buttons(false, true);
		}
		if(e.getActionCommand().equals("preview")) {
			preview(co_logo);
		}
	}
	
	public static void enable_buttons( Boolean export, Boolean cancel) {
		btnsave.setEnabled(export);
		btncancel.setEnabled(cancel);
	}
	
	public static void refresh() {
		FncTables.clearTable(modeltoupdate);
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheadertoupdate.setModel(listModel);//Setting of listModel into rowHeader.
		co_id = "";
		lookupcomp.setValue("");
		txtcompany.setText("");
		txtdirectory.setText("");
		
		
	}
	
	public static String SQL_COMPANY() {//XXX Company
		
		String strSQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		return strSQL;
	}
	
	public static void displaytoupdate(String comp_id) {
		FncTables.clearTable(modeltoupdate);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowheadertoupdate.setModel(listModel);// Setting of DefaultListModel into rowHeader.
		
		String strsql="select b.entity_name,\n" + 
				"a.entity_id, \n" + 
				"c.proj_name, \n" + 
				"c.proj_alias || ' ' || d.description as description,\n" + 
				"a.duedate, \n" + 
				"a.amt_toupdate\n" + 
				"from rf_card_pmt_toupdate a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"left join mf_project c on a.proj_id = c.proj_id\n" + 
				"left join mf_unit_info d on a.pbl_id = d.pbl_id\n" + 
				"where (case when '"+comp_id+"' != ''  then c.co_id = '"+comp_id+"' else true end) \n" + 
				"order by b.entity_name, c.proj_name";
		
		System.out.println("displaytoupdate: "+strsql);
		pgSelect db = new pgSelect();
		db.select(strsql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modeltoupdate.addRow(db.getResult()[x]);
				listModel.addElement(modeltoupdate.getRowCount());
			}
		}	
	}
	
	private void preview(String co_logo) {
		
		String sql = "select b.entity_name,\n" + 
				"a.entity_id,\n" + 
				"c.proj_name,\n" + 
				"c.proj_alias || ' ' || d.description as description,\n" + 
				"a.duedate,\n" + 
				"a.amt_toupdate\n" + 
				"from rf_card_pmt_toupdate a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"left join mf_project c on a.proj_id = c.proj_id\n" + 
				"left join mf_unit_info d on a.pbl_id = d.pbl_id\n" + 
				"where (case when '"+co_id+"' != '' then c.co_id = '"+co_id+"' else true end)\n" + 
				"order by b.entity_name, c.proj_name";
		
		System.out.println("");
		System.out.println("preview: "+sql);
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company_name", txtcompany.getText());
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ co_logo));
		FncReport.generateReport("/Reports/preview-toupdate.jasper", "Not found Asset Card", mapParameters);
		
	}
	
	public void export_toupdte() {
		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		String strParamDate = (String) sdfTo.format(dateObj);
		
    	String strFile = "";
    	String strWrite = "";
    	
    	File f = new File(txtdirectory.getText());
    	
    	if (!f.exists() && !f.isDirectory()) {
    		JOptionPane.showMessageDialog(getContentPane(), "Folder does not exist.\nSet default directory.");
    	}
    	else {
    		
    		strFile =  strParamDate + ".csv";
        	File FileText = new File(txtdirectory.getText() + "/" + strFile);
        	Boolean blnProceed = false;
        	
    		if(FileText.exists()) {
    			if(JOptionPane.showConfirmDialog(null, "A file with the same name exists in the specified directory.\nOverwrite?", "Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
    				blnProceed = true;
    			}
    		}
    		else {
    			blnProceed = true;
    		}
    		
    		if (blnProceed) {
    			FileText.delete();
    			strWrite = write_toupdate();
            	
                try {
                    FileOutputStream is = new FileOutputStream(FileText);
                    OutputStreamWriter osw = new OutputStreamWriter(is);    
                    Writer w = new BufferedWriter(osw);
                    w.write(strWrite);
                    w.close();
                } 
                catch (IOException e) {
                    System.err.println("Problem writing to the file statsTest.txt");
                }
    		}
    	}
    }
	
	 public static String write_toupdate() {
	    	String strWrite = "";
	    	String nl = System.getProperty("line.separator");
	    	Date date = new Date();
			SimpleDateFormat sdfNow = new SimpleDateFormat("MMddyy");
			String strNow = (String)sdfNow.format(date);
	    	
	    	System.out.println("");
	    	System.out.println("Current Date: " + strNow);
	    	
	    	strWrite = strWrite + "Date: '"+strNow+"'";
	    	strWrite = strWrite + nl;
	    	strWrite = strWrite + "Testing Only";	
	    	strWrite = strWrite + nl; 
	    	strWrite = strWrite + "Client Name: 20";
	    	strWrite = strWrite + nl; 
	    	strWrite = strWrite + "Reference No.: 20";
	    	strWrite = strWrite + nl; 
	    	strWrite = strWrite + "Amount to Update: 20";
	    	strWrite = strWrite + nl;  
	    	strWrite = strWrite + nl; 
	    	
	    	System.out.println("");
	    	System.out.println("tbl_toupdate.getRowCount(): " + tbl_toupdate.getRowCount());
	    	
	    	for(int x = 0;x < tbl_toupdate.getRowCount();x++) {
	    		
    			System.out.println("");
    			System.out.println("Row: " + x);
    			
    			String strName = (String)tbl_toupdate.getValueAt(x, 0);
    			System.out.println("strName: " + strName);

    			BigDecimal Amt;
    			
    			try {
    				Amt = (BigDecimal)tbl_toupdate.getValueAt(x, 5);
    			} catch(ClassCastException ex) {
    				try {
    					Amt = new BigDecimal((Long)tbl_toupdate.getValueAt(x, 5));
    				} catch (ClassCastException ey) {
    					Amt = new BigDecimal((Double)tbl_toupdate.getValueAt(x, 5));
    					}
    			} 
    			System.out.print("");
    			System.out.print("Amt: " + Amt);

    			String strID =  (String)  tbl_toupdate.getValueAt(x, 1);
    			//String strproj =  (String)  tbl_toupdate.getValueAt(x, 2);
    			//String strUnit = (String) tbl_toupdate.getValueAt(x, 3);
    			//String strduedate =  (String)  tbl_toupdate.getValueAt(x, 4);
    			
    			strWrite = strWrite + GetID(0, strID, lookupcomp.getValue());    //0 client_name
    			strWrite = strWrite + GetID(1, strID, lookupcomp.getValue());	 //1  reference
    			strWrite = strWrite + GetID(3, strID, lookupcomp.getValue());    //2 particular
    			strWrite = strWrite + GetID(2, strID, lookupcomp.getValue());	 //3  amt_toupdate							
    			strWrite = strWrite + nl;
    																	
    			
    			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    			System.out.println("Amt: " + Amt);
    		}
	    	return strWrite;
	    }
	 
	 public static String GetID(Integer i, String strid, String co_id){
			String ID = "";

			String SQL = "select rpad(b.entity_name,20, ' ') as client_name,\n" + 
					"				(right(a.entity_id,10) || lpad(to_char(a.pbl_id::int,'FM00000'),5) || lpad(to_char(c.proj_id::int,'FM00000'),5)) as reference, \n" + 
					"				to_char(replace(a.amt_toupdate::text,'.','')::int, 'FM0000000000') as amt_toupdate,\n" + 
					"				'       BDP' as particular,\n" + 
					"				right(a.entity_id,10) as id,\n" + 
					"				lpad(to_char(a.pbl_id::int,'FM00000'),5) as pbl_id,\n" + 
					"				lpad(to_char(c.proj_id::int,'FM00000'),5) as proj_id\n" + 
					"				from rf_card_pmt_toupdate a \n" + 
					"				left join rf_entity b on a.entity_id = b.entity_id \n" + 
					"				left join mf_project c on a.proj_id = c.proj_id\n" + 
					"				left join mf_unit_info d on a.pbl_id = d.pbl_id\n" + 
					"				where    a.entity_id = '"+strid+"' \n" +
					"				and case when '"+co_id+"' != '' then c.co_id = '"+co_id+"' else true end  \n"+ 		
					"				order by b.entity_name, c.proj_name";
			
			//System.out.println("GetID: "+SQL);
			pgSelect sqlExec = new pgSelect();
			sqlExec.select(SQL);

			if(sqlExec.isNotNull()){
				ID = (String) sqlExec.getResult()[0][i];
				System.out.println(ID);
			}else{
				ID = "";
			}

			return ID;
		}
	 
	 public static String OpenDir(String strType) {
		String strDir = "";
		
		chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Directory");
		
		if (strType.equals("Folder")) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		else {
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}
		
		chooser.setAcceptAllFileFilterUsed(false);
    
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){ 
			System.out.println(chooser.getSelectedFile().toString());
			strDir = chooser.getSelectedFile().toString();
			txtdirectory.setText(strDir);
		}
		else{
			System.out.println("No Selection ");
		}
		
		return strDir;
	}
}
