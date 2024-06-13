package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import tablemodel.model_CTSNotarization;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import Accounting.Disbursements.RequestForPayment;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class CTS_Notarization extends _JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 2726959568793073317L;
	static String title = "CTS Notarization";
	Dimension frameSize = new Dimension(700, 600);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	private JLabel lblCo;
	private JLabel lblPro;
	private JLabel lblDate;
	private JLabel lblType;
	private JLabel lblBatch;
	
	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtBatch;
	
	private JTextField txtCo;
	private JTextField txtPro;
	
	private JButton btnGen;
	private JButton btnNot;
	private JButton btnPreview;
	private JButton btnExport;
	
	private JComboBox cboType;
	
	private _JDateChooser dteDate;
	
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	private model_CTSNotarization modelCTS;	
	static _JTableMain tblCTS;
	static JList rowCTS;
	private String company_logo;
	
	private JCheckBox chkCTS;
	
	public CTS_Notarization() {
		super(title, true, true, false, false);
		initGUI();
	}

	public CTS_Notarization(String title) {
		super(title);
		initGUI();
	}

	public CTS_Notarization(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panNorth = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panNorth, BorderLayout.PAGE_START);
			panNorth.setPreferredSize(new Dimension(0, 200));
			{
				JXPanel panNorthDiv = new JXPanel(new GridLayout(2, 1, 5, 5));
				panNorth.add(panNorthDiv, BorderLayout.CENTER);
				{
					JXPanel panCoPro = new JXPanel(new BorderLayout(5, 5));
					panNorthDiv.add(panCoPro, BorderLayout.CENTER);
					panCoPro.setBorder(JTBorderFactory.createTitleBorder("Company and Project", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panCoProLabel_Alpha = new JXPanel(new GridLayout(2, 2, 5, 5));
						panCoPro.add(panCoProLabel_Alpha, BorderLayout.LINE_START);
						panCoProLabel_Alpha.setPreferredSize(new Dimension(200, 0));
						{
							{
								lblCo = new JLabel("Company:");
								panCoProLabel_Alpha.add(lblCo, BorderLayout.CENTER);
								lblCo.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								txtCoID = new _JLookup();
								panCoProLabel_Alpha.add(txtCoID, BorderLayout.CENTER);
								txtCoID.setHorizontalAlignment(JTextField.CENTER);
								txtCoID.setReturnColumn(0);
								txtCoID.setLookupSQL(_JInternalFrame.SQL_COMPANY());
								txtCoID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data!=null) {
											txtCo.setText(data[1].toString());
											company_logo = (String) data[3];
										}
									}
								});
							}
							{
								lblPro = new JLabel("Project:");
								panCoProLabel_Alpha.add(lblPro, BorderLayout.CENTER);
								lblPro.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								txtProID = new _JLookup();
								panCoProLabel_Alpha.add(txtProID, BorderLayout.CENTER);
								txtProID.setHorizontalAlignment(JTextField.CENTER);
								txtProID.setReturnColumn(0);
								txtProID.setLookupSQL(_JInternalFrame.SQL_PROJECT());
								txtProID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data!=null) {
											txtPro.setText(data[1].toString());
										}
									}
								});
							}
							JXPanel panCoProLabel_Beta = new JXPanel(new GridLayout(2, 1, 5, 5));
							panCoPro.add(panCoProLabel_Beta, BorderLayout.CENTER);
							{
								txtCo = new JTextField();
								panCoProLabel_Beta.add(txtCo, BorderLayout.CENTER);
								txtCo.setHorizontalAlignment(JTextField.CENTER);
								txtCo.setEditable(false);
							}
							{
								txtPro = new JTextField();
								panCoProLabel_Beta.add(txtPro, BorderLayout.CENTER);
								txtPro.setHorizontalAlignment(JTextField.CENTER);
								txtPro.setEditable(false);
							}
						}
					}
					JXPanel panHDMF_IHF_Gen = new JXPanel(new BorderLayout(5, 5));
					panNorthDiv.add(panHDMF_IHF_Gen, BorderLayout.CENTER);
					{
						JXPanel panHDMF_IHF = new JXPanel(new BorderLayout(5, 5));
						panHDMF_IHF_Gen.add(panHDMF_IHF, BorderLayout.LINE_START);
						panHDMF_IHF.setPreferredSize(new Dimension(350, 0));
						panHDMF_IHF.setBorder(JTBorderFactory.createTitleBorder("Client Type", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel panHDMF_IHF_Label = new JXPanel(new GridLayout(2, 1, 5, 5));
							panHDMF_IHF.add(panHDMF_IHF_Label, BorderLayout.LINE_START);
							panHDMF_IHF_Label.setPreferredSize(new Dimension(100, 0));
							{
								lblType = new JLabel("Type:");
								panHDMF_IHF_Label.add(lblType, BorderLayout.CENTER);
								lblType.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								lblDate = new JLabel("Batch No.:");
								panHDMF_IHF_Label.add(lblDate, BorderLayout.CENTER);
								lblDate.setHorizontalAlignment(JTextField.LEADING);
							}
							JXPanel panHDMF_IHF_Box = new JXPanel(new GridLayout(2, 1, 5, 5));
							panHDMF_IHF.add(panHDMF_IHF_Box, BorderLayout.CENTER);
							{
								String strOpt[] = {"04 - PAGIBIG", "02 - In-House Financing"};
								cboType = new JComboBox(strOpt);
								panHDMF_IHF_Box.add(cboType);
							}
							{
								txtBatch = new _JLookup("");
								panHDMF_IHF_Box.add(txtBatch, BorderLayout.CENTER);
								txtBatch.setHorizontalAlignment(JTextField.CENTER);
								txtBatch.setReturnColumn(0);
								txtBatch.setLookupSQL(BatchSQL());
								txtBatch.addFocusListener(new FocusListener() {
									public void focusLost(FocusEvent e) {
										
									}
									
									public void focusGained(FocusEvent e) {
										chkCTS.setSelected(false);
									}
								});
								txtBatch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data!=null) {
											DisplayCTS(modelCTS, rowCTS, txtBatch.getText(), chkCTS.isSelected());
											ButtonMode(2);
										}
									}
								});
							}
							/*
							{
								dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panHDMF_IHF_Box.add(dteDate);
								dteDate.getCalendarButton().setVisible(true);
								dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}
							*/
						}
						JXPanel panGen = new JXPanel(new BorderLayout(5, 5));
						panHDMF_IHF_Gen.add(panGen, BorderLayout.CENTER);
						{
							{
								btnGen = new JButton("Generate");
								panGen.add(btnGen, BorderLayout.CENTER);
								btnGen.setActionCommand("Generate");
								btnGen.addActionListener(this);
							}
							{
								chkCTS = new JCheckBox("For renewal of notarized date");
								panGen.add(chkCTS, BorderLayout.PAGE_END);
								chkCTS.setPreferredSize(new Dimension(0, 30));
								chkCTS.setSelected(false);
								chkCTS.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										txtBatch.setValue("");
										DisplayCTS(modelCTS, rowCTS, txtBatch.getText(), chkCTS.isSelected());
										ButtonMode(3);
									}
								});
							}
						}
					}
				}
				Setdefaults();
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			{
				CreateTable();
				panCenter.add(pnlTab);
			}
			JXPanel panEnd = new JXPanel(new GridLayout(1, 2, 5, 5));
			panMain.add(panEnd, BorderLayout.PAGE_END);
			panEnd.setPreferredSize(new Dimension(0, 60));
			{
				/*
				chkNotarized = new JCheckBox("Show all notarized");
				panEnd.add(chkNotarized, BorderLayout.CENTER);
				chkNotarized.setSelected(false);
				chkNotarized.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							btnNot.setEnabled(false);
						} else {
							btnNot.setEnabled(true);
						}
						DisplayCTS(modelCTS, rowCTS);
					}
				});
				*/
				JXPanel panBat = new JXPanel(new BorderLayout(5, 5));
				panEnd.add(panBat, BorderLayout.CENTER);
				panBat.setBorder(JTBorderFactory.createTitleBorder("Date Parameter", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						lblBatch = new JLabel("Notarized Date:");
						panBat.add(lblBatch, BorderLayout.LINE_START);
						lblBatch.setHorizontalAlignment(JTextField.LEADING);
						lblBatch.setPreferredSize(new Dimension(100, 0));
					}
					{
						dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						panBat.add(dteDate);
						dteDate.getCalendarButton().setVisible(true);
						dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
				}
			}
			JXPanel panBut = new JXPanel(new GridLayout(1, 3, 5, 5));
			panEnd.add(panBut, BorderLayout.CENTER);
			{
				{
					btnNot = new JButton("Notarize");
					panBut.add(btnNot, BorderLayout.CENTER);
					btnNot.setActionCommand("Notarize");
					btnNot.addActionListener(this);
				}
				{
					btnPreview = new JButton("Preview");
					panBut.add(btnPreview, BorderLayout.CENTER);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
				}
				{
					btnExport = new JButton("Export");
					panBut.add(btnExport, BorderLayout.CENTER);
					btnExport.setActionCommand("Export");
					btnExport.addActionListener(this);
				}
			}
			ButtonMode(1);
		}
	}
	
	public void CreateTable() {
		pnlTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			pnlTab.add(scrTab, BorderLayout.CENTER);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelCTS = new model_CTSNotarization();
				modelCTS.setEditable(false);
				
				tblCTS = new _JTableMain(modelCTS);
				tblCTS.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblCTS.getSelectedRow());
						}
					}
				});
				
				rowCTS = tblCTS.getRowHeader();
				scrTab.setViewportView(tblCTS);
				tblCTS.getTableHeader().setEnabled(false);
				tblCTS.getColumnModel().getColumn(0).setPreferredWidth(255);
				tblCTS.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblCTS.getColumnModel().getColumn(2).setPreferredWidth(75);
				tblCTS.getColumnModel().getColumn(3).setPreferredWidth(150);
				tblCTS.getColumnModel().getColumn(4).setPreferredWidth(200);
				tblCTS.getColumnModel().getColumn(5).setPreferredWidth(150);

				rowCTS = tblCTS.getRowHeader();
				rowCTS.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowCTS);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblCTS.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no", "doc_id");
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Generate")) {
			System.out.println("");
			System.out.println("Generate");
			
			txtBatch.setValue("");
			DisplayCTS(modelCTS, rowCTS, txtBatch.getText(), chkCTS.isSelected());
			ButtonMode(3);
		} else if (e.getActionCommand().equals("Notarize")) {
			System.out.println("");
			System.out.println("Notarize");
			
			if (tblCTS.getRowCount() > 0) {
				Notarize();
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Generate the list first before\npressing the notarize button.");
			}
		} else if (e.getActionCommand().equals("Preview")) {
			Generate();	
		} else if (e.getActionCommand().equals("Export")) {
			Export();
		}
	}
	
	public static String GetValue(String SQL) {
		String strValue = "";
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			strValue = (String) sqlExec.getResult()[0][0];
		} else {
			strValue = "";
		}
		
		return strValue;
	}
	
	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;
		
		/*	Company Default	*/
		strCount = GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtCoID.setText("");
			txtCo.setText("");
		} else {
			txtCoID.setValue(GetValue("SELECT co_id FROM mf_company LIMIT 1"));
			
			try {
				String strCo =GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getValue()+"'");
				System.out.println(strCo);
				txtCo.setText(strCo);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtCoID: " + txtCoID.getValue());
				System.out.println("txtCoName: " + GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getValue()+"'"));
			}
		}
		
		/*	Project Default	*/
		strCount = GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtProID.setText("");
			txtPro.setText("[  Project  ]");
		} else {
			txtProID.setValue(GetValue("SELECT proj_id FROM mf_project LIMIT 1"));
			
			try {
				String strPro = GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProID.getValue()+"'");
				System.out.println(strPro);
				txtPro.setText(strPro);	
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtProjectID: " + txtProID.getValue());
				System.out.println(GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProID.getValue()+"'"));
			}
		}
	}
	
	private void Notarize() {
		String SQL = "";
		String SQL_Batch = "";
		String strBatch = "";
		Boolean blnSel = false;
		
		if (JOptionPane.showConfirmDialog(getContentPane(), "Notarize the selected accounts?\nNotarized date will be set as " + 
				dteDate.getText() + ".\nProceed?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
			strBatch = GetBatch();
			
			System.out.println("");
			System.out.println("strBatch: " + strBatch);
			
			for (int x = 0; x < tblCTS.getRowCount(); x++) {
				if ((Boolean) tblCTS.getValueAt(x, 1)) {
					String id = (String) modelCTS.getValueAt(x, 8);
					String project = (String) modelCTS.getValueAt(x, 9);
					String unit = (String) modelCTS.getValueAt(x, 10);
					Integer sequence = (Integer) modelCTS.getValueAt(x, 11);
					String doc = (String) modelCTS.getValueAt(x, 12);
					
					SQL = "update rf_buyer_documents \n" +
						"set date_notarized = '"+dteDate.getDate().toString()+"'::timestamp \n" +
						"where entity_id = '"+id+"' and projcode = '"+project+"' and pbl_id = '"+unit+"' \n" +
						"and seq_no = "+sequence.toString()+" and status_id != 'I' and doc_id = '"+doc+"'";
					
					System.out.println("");
					System.out.println(SQL);
					
					pgUpdate db_cts = new pgUpdate();
					db_cts.executeUpdate(SQL, false);
					db_cts.commit();
					
					blnSel = true;
					
					SQL_Batch = "INSERT INTO rf_cts_batch (cts_rec_id, entity_id, proj_id, pbl_id, seq_no, \n" +
								"notarizeddate, date_posted, batch_no, user_id) \n" +
								"VALUES ("+GetRecID()+", '"+id+"', '"+project+"', '"+unit+"', "+sequence+", \n" +
								"'"+dteDate.getDate().toString()+"', '"+FncGlobal.getDateSQL()+"', '"+strBatch+"', '"+UserInfo.EmployeeCode+"')";
					
					System.out.println("");
					System.out.println("SQL_Batch: " + SQL_Batch);
					System.out.println("id: " + id);

					
					pgUpdate dbBatch = new pgUpdate();
					dbBatch.executeUpdate(SQL_Batch, false);
					dbBatch.commit();
				}
			}
		}
		
		if (!blnSel) {
			JOptionPane.showMessageDialog(getContentPane(), "Tagging did not proceed.");
		} else {
			chkCTS.setSelected(false);
			txtBatch.setText(strBatch);
			DisplayCTS(modelCTS, rowCTS, txtBatch.getText(), chkCTS.isSelected());
			ButtonMode(2);
			JOptionPane.showMessageDialog(getContentPane(), "The account's documents were successfully notarized.");
		}
	}
	
	private String GetBatch() {
		String strBat = "";
		String strSQL = "SELECT TRIM(to_char(max(COALESCE(batch_no::INT, 0)) + 1, '000000')) FROM rf_cts_batch";
		
		pgSelect dbBatch = new pgSelect();
		dbBatch.select(strSQL);
		
		if (dbBatch.isNotNull()) {
			strBat = (String) dbBatch.getResult()[0][0];
		} else {
			strBat = "000001";
		}
		
		if (strBat==null) {
			strBat = "000001";
		}
		
		return strBat;
	}
	
	private Integer GetRecID() {
		Integer intRec = 0;
		String strSQL = "SELECT TRIM(to_char(max(COALESCE(cts_rec_id::INT, 0)) + 1, '000000'))::int FROM rf_cts_batch";
		
		pgSelect dbRec = new pgSelect();
		dbRec.select(strSQL);
		
		if (dbRec.isNotNull()) {
			System.out.println("");
			System.out.println("Not Null");
			intRec = (Integer) dbRec.getResult()[0][0];
		} else {
			System.out.println("");
			System.out.println("Null");
			intRec = Integer.parseInt("1");
		}
		
		if (intRec==null) {
			intRec = 1;
		}
		
		return intRec;
	}
	
	private String BatchSQL() {
		return "select distinct batch_no, notarizeddate::date, date_posted::date from rf_cts_batch order by batch_no";
	}
	
	private void ButtonMode(Integer i) {
		if (i==1) {								/*	Initial State				*/
			btnGen.setEnabled(true);
			btnNot.setEnabled(false);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		} else if (i==2) {						/*	Notarized or Retrieve Batch	*/
			btnGen.setEnabled(true);
			btnNot.setEnabled(false);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(true);
		} else if (i==3) {						/*	Generate is pressed			*/
			btnGen.setEnabled(true);
			btnNot.setEnabled(true);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		}
	}
	
	private void Generate() {
		String strType = cboType.getSelectedItem().toString();
		strType = strType.substring(0, 2);
		//String company_logo = RequestForPayment.sql_getCompanyLogo();
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", txtCo.getText());
		mapParameters.put("02_Project", txtPro.getText());
		mapParameters.put("03_dFrom", "");
		mapParameters.put("04_CoID", txtCoID.getText());
		mapParameters.put("05_ProID", txtProID.getText());
		mapParameters.put("06_Type", strType);
		mapParameters.put("07_User", UserInfo.FullName);
		mapParameters.put("08_Batch", txtBatch.getText());
		mapParameters.put("09_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		FncReport.generateReport("/Reports/rptNotarizedCTS.jasper", "Notarized CTS List", "", mapParameters);
	}
	
	private void Export() {
		String strType = cboType.getSelectedItem().toString();
		strType = strType.substring(0, 2);
		
		String col_names [] = {"Name", "Project", "Unit", "Model", "Loanable Amount", "Notarized Date", "OR Date", "Buyer Type"};
		String strSQL = "select * from view_cts_list('"+txtCoID.getText()+"', '"+txtProID.getText()+"', '"+strType+"', true, '"+txtBatch.getText()+"')";
		
		FncGlobal.startProgress("Creating spreadsheet.");
		FncGlobal.CreateXLS(col_names, strSQL, "Notarized CTS");
		FncGlobal.stopProgress();
	}
	
	private void DisplayCTS(DefaultTableModel modelMain, JList rowHeader, String strBatch, Boolean blnRenewal) {
		String strType = cboType.getSelectedItem().toString();
		strType = strType.substring(0, 2);
		boolean blnNot = false;
		
		if (strBatch.equals("")) {
			blnNot = false;
		} else {
			blnNot = true;
		}
		
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "";
		if (blnRenewal) {
			/*
			SQL = "select distinct c_name, false, c_project, c_unit, c_type_desc, c_doc_desc, c_date_submitted, c_notarizeddate, \n" +
					"c_entity_id, c_proj_id, c_pbl_id, c_seq_no, c_doc_id, a.c_name \n" +
					"from view_cts_notarization('"+txtCoID.getText()+"', '"+txtProID.getText()+"', '"+strType+"', true) a \n" +
					"left join rf_cts_batch b on a.c_entity_id = b.entity_id and a.c_proj_id = b.proj_id and a.c_pbl_id = b.pbl_id and a.c_seq_no = b.seq_no \n" +
					"where b.batch_no != '' order by a.c_name";
			*/
			SQL = "select distinct c_name, false, c_project, c_unit, c_type_desc, c_doc_desc, c_date_submitted, c_notarizeddate, \n" + 
					"c_entity_id, c_proj_id, c_pbl_id, c_seq_no, c_doc_id, a.c_name \n" +
					"from view_cts_notarization('', '', '"+strType+"', true) a \n" +
					"left join rf_cts_batch b on a.c_entity_id = b.entity_id and a.c_proj_id = b.proj_id and a.c_pbl_id = b.pbl_id and a.c_seq_no = b.seq_no \n" + 
					"where b.batch_no != '' or \n" +
					"exists(SELECT a.* FROM view_card_client_request_history_v2(a.c_entity_id, a.c_proj_id, a.c_pbl_id, a.c_seq_no) a \n" + 
					"inner join req_rt_request_header b on a.c_request_no = b.request_no inner join rf_cts_batch c on c.entity_id = b.old_entity_id \n" +
					"where c_stat_desc ~* 'Change Of Principal Applicant' ORDER BY c_request_date) order by a.c_name"; 
		} else {
			/*
			SQL = "select distinct *, a.c_name \n" +
					"from view_cts_notarization('"+txtCoID.getText()+"', '"+txtProID.getText()+"', '"+strType+"', "+blnNot+") a \n" +
					"left join rf_cts_batch b on a.c_entity_id = b.entity_id and a.c_proj_id = b.proj_id and a.c_pbl_id = b.pbl_id and a.c_seq_no = b.seq_no \n" +
					"where b.batch_no = '"+strBatch+"' OR '"+strBatch+"' = '' order by a.c_name";
			*/
			
			SQL = "select distinct c_name, c_tag, c_project, c_unit, c_type_desc, c_doc_desc, c_date_submitted, \n" + 
					"(case when '"+strBatch+"' != '' then (select x.notarizeddate::date::varchar from rf_cts_batch x where x.batch_no = '"+strBatch+"' limit 1) else c_notarizeddate end) as c_notarizeddate, \n" + 
					"c_entity_id, c_proj_id, c_pbl_id, c_seq_no, c_doc_id, a.c_name  \n" +
					"from view_cts_notarization('"+txtCoID.getText()+"', '"+txtProID.getText()+"', '"+strType+"', "+blnNot+") a \n" + 
					"left join rf_cts_batch b on a.c_entity_id = b.entity_id and a.c_proj_id = b.proj_id and a.c_pbl_id = b.pbl_id and a.c_seq_no = b.seq_no \n" + 
					"where b.batch_no = '"+strBatch+"' OR '"+strBatch+"' = '' order by a.c_name"; 

		}
		
		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
}