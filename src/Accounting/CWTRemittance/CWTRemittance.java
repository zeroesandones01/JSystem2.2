package Accounting.CWTRemittance;

import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/***import org.apache.poi.hssf.util.HSSFColor.RED;	***/
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.table.TableColumnExt;

import com.toedter.calendar.JDateChooser;
import com.vdc.glasspane.JGlassLoading;

import tablemodel.Model_CWTRemittedTable;
import tablemodel.Model_cwt_projected25;
import tablemodel.model_CWTtable;
import components.JTBorderFactory;
import components._JInternalFrame;

/***	import components._JTabbedPane;		***/
import components._JTableMain;
/***	import components._JXTextField;		***/

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;

public class CWTRemittance extends _JInternalFrame implements _GUI,
		ActionListener {
	
	Dimension frameSize = new Dimension(670, 620);
	private static final long serialVersionUID = -4179492788356661660L;

	static String title = "CWT Remittance";
	static String Mode = "";
	
	static Integer ParamHeight = 70;
	static Integer ParamWidth = 70;
	static Integer ParamFontSize = 12;
	static Integer LabelHeight = 30;
	static Integer LabelWidth = 300;
	static Integer LabelFontSize = 12;
	
	static Boolean isOpaque = false;
	
	private JXPanel pnlMain;
	private JXPanel pnlContent1;
	private JXPanel pnlProjected25;
	private JXPanel Panel1;
	private JXPanel Panel2;
	private JXPanel pnlComPro;;
	private JXPanel pnlDates;
	private JXPanel pnlGen;
	private JXPanel pnlButtons;
	
	private JGlassLoading glassLoad;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblFrom;
	private JLabel lblTo;
	
	private JTextField txtProjectName;
	private JTextField txtCoName;
	private JTextField txtRPLF;
	
	private static _JLookup txtCoID;
	private static _JLookup txtProjectID;
	
	private static JDateChooser dteFrom;
	private static JDateChooser dteTo;
	private static _JDateChooser dteRefDate;

	private JButton btnGenerate;
	private JButton btnLoad;
	private JButton btnPreview;
	private JButton btnPost;
	private JButton btnCancel;
	private JButton btnExport;

	private static JTabbedPane jtpList;

	private model_CWTtable modelCWT;
	private model_CWTtable modelCWTRemitted;
	private Model_CWTRemittedTable modelCWTRemitted_v2;

	private _JTableMain tblCWT;
	private _JTableMain tblCWTRemitted;
	private _JTableMain tblCWTRemitted_v2;

	public static JList rowHeaderCWT;
	public static JList rowHeaderCWTRemitted;
	public static JList rowHeaderCWTRemitted_v2;

	private JScrollPane scrollMainCenter;
	private JScrollPane scrollMainCenter1;

	private JXPanel panDate;

	JProgressBar progressbar = new JProgressBar();
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private Model_cwt_projected25 modelCWT25;
	private _JTableMain tblCWT25;
	private JList rowHeaderCWT25;
	private JScrollPane scrollMainCenter25;
	private JTextField txtRPLF25;

	static Border lineBorderRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBorderBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineBorderYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineBorderGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineBorderGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBorderBlack = BorderFactory.createLineBorder(Color.BLACK);

	private static Boolean blnDP;
	private static Boolean blnAsof = true; 

	public CWTRemittance() {
		super(title, true, true, false, false);
		initGUI();
	}

	public CWTRemittance(String title) {
		super(title);
		initGUI();
	}

	public CWTRemittance(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		glassLoad = new JGlassLoading(getJMenuBar(), getContentPane(), this.getWidth(), this.getWidth());
		this.setGlassPane(glassLoad);
		{
			pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlContent1 = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlContent1, BorderLayout.PAGE_START);
				pnlContent1.setPreferredSize(new Dimension(500, 190));
				pnlContent1.setOpaque(isOpaque());
				{
					JXPanel pnlSubCont1 = new JXPanel(new GridLayout(2, 1, 0, 5));
					pnlContent1.add(pnlSubCont1, BorderLayout.CENTER);
					{
						pnlComPro = new JXPanel(new BorderLayout(5, 5));
						pnlSubCont1.add(pnlComPro, BorderLayout.CENTER);
						pnlComPro.setPreferredSize(new Dimension(550, 75));
						pnlComPro.setBorder(JTBorderFactory.createTitleBorder("Company and Project", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel pnlParamsLabel = new JXPanel(new GridLayout(2, 2, 5, 5));
							pnlComPro.add(pnlParamsLabel, BorderLayout.LINE_START);
							pnlParamsLabel.setPreferredSize(new Dimension(200, 75));
							{
								{
									lblCompany = new JLabel("Company:	", JLabel.LEADING);
									pnlParamsLabel.add(lblCompany);
									lblCompany.setBorder(new EmptyBorder(5, 5, 5, 5));
									lblCompany.setPreferredSize(new Dimension(ParamWidth, ParamHeight));
								}
								{
									txtCoID = new _JLookup(null, "Company", 0, 2);
									pnlParamsLabel.add(txtCoID, BorderLayout.CENTER);
									txtCoID.setPreferredSize(new Dimension(100, ParamHeight));
									txtCoID.setHorizontalAlignment(JTextField.CENTER);
									txtCoID.setLookupSQL(SQL_COMPANY());
									txtCoID.setReturnColumn(0);
									txtCoID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												txtCoName.setText(data[1].toString());
												txtProjectID.setLookupSQL(SQL_PROJECT());
												txtProjectID.setValue("");
												txtProjectName.setText("All Projects");
											}
										}
									});
									txtCoID.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {
											if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
												txtCoID.setValue("");
												txtCoName.setText("All Companies");
											}
										}
										
										public void keyReleased(KeyEvent e) {
											
										}
										
										public void keyPressed(KeyEvent arg0) {
											
										}
									});
								}
								{
									lblProject = new JLabel("Project:	", JLabel.LEADING);
									pnlParamsLabel.add(lblProject);
									lblProject.setBorder(new EmptyBorder(5, 5, 5, 5));
									lblProject.setPreferredSize(new Dimension(ParamWidth, ParamHeight));
								}
								{
									txtProjectID = new _JLookup(null, "Project", 0, 2);
									pnlParamsLabel.add(txtProjectID, BorderLayout.CENTER);
									txtProjectID.setPreferredSize(new Dimension(100, ParamHeight));
									txtProjectID.setHorizontalAlignment(JTextField.CENTER);
									txtProjectID.setReturnColumn(0);
									txtProjectID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												txtProjectName.setText(data[1].toString());
											}
										}
									});
									txtProjectID.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {
											if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
												txtProjectID.setValue("");
												txtProjectName.setText("All Projects");
											}
										}
										
										public void keyReleased(KeyEvent e) {
											
										}
										
										public void keyPressed(KeyEvent e) {
											
										}
									});
								}
							}
							JXPanel pnlParamsBox = new JXPanel(new GridLayout(2, 1, 5, 5));
							pnlComPro.add(pnlParamsBox, BorderLayout.CENTER);
							pnlParamsBox.setPreferredSize(new Dimension(200, 130));
							{
								{
									txtCoName = new JTextField("");
									pnlParamsBox.add(txtCoName, BorderLayout.CENTER);
									txtCoName.setPreferredSize(new Dimension(ParamWidth, ParamHeight));
									txtCoName.setHorizontalAlignment(JTextField.CENTER);
									txtCoName.setEditable(false);
								}
								{
									txtProjectName = new JTextField("");
									pnlParamsBox.add(txtProjectName, BorderLayout.CENTER);
									txtProjectName.setPreferredSize(new Dimension(ParamWidth, ParamHeight));
									txtProjectName.setHorizontalAlignment(JTextField.CENTER);
									txtProjectName.setEditable(false);
								}
							}
							Setdefaults();
						}
						JXPanel pnlDateButtonDiv = new JXPanel(new GridLayout(1, 2, 5, 0));
						pnlSubCont1.add(pnlDateButtonDiv, BorderLayout.CENTER);
						{
							pnlDates = new JXPanel(new BorderLayout(5, 5));
							pnlDateButtonDiv.add(pnlDates, BorderLayout.LINE_START);
							pnlDates.setPreferredSize(new Dimension(550, 75));
							pnlDates.setBorder(JTBorderFactory.createTitleBorder("Date Coverage", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								JXPanel pnlDatesDiv = new JXPanel(new GridLayout(2, 2, 5, 5));
								pnlDates.add(pnlDatesDiv, BorderLayout.CENTER);
								{
									{
										lblFrom = new JLabel("Date From:	", JLabel.LEADING);
										pnlDatesDiv.add(lblFrom);
										lblFrom.setBorder(new EmptyBorder(5, 5, 5, 5));
										lblFrom.setPreferredSize(new Dimension(ParamWidth, ParamHeight));
									}
									{
										dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlDatesDiv.add(dteFrom);
										dteFrom.getCalendarButton().setVisible(true);
										dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}
									{
										lblTo = new JLabel("Date To:	", JLabel.LEADING);
										pnlDatesDiv.add(lblTo);
										lblTo.setBorder(new EmptyBorder(5, 5, 5, 5));
										lblTo.setPreferredSize(new Dimension(ParamWidth, ParamHeight));
									}
									{
										dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlDatesDiv.add(dteTo);
										dteTo.getCalendarButton().setVisible(true);
										dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}
								}
							}
							pnlGen = new JXPanel(new BorderLayout(5, 5));
							pnlDateButtonDiv.add(pnlGen, BorderLayout.CENTER);
							pnlGen.setPreferredSize(new Dimension(550, 75));
							{
								btnGenerate = new JButton("Generate");
								pnlGen.add(btnGenerate);
								btnGenerate.setActionCommand("Generate");
								btnGenerate.addActionListener(this);
							}
						}
					}
				}
				BoxLock(true);
				{
					jtpList = new JTabbedPane();
					pnlMain.add(jtpList, BorderLayout.CENTER);
					jtpList.setPreferredSize(new Dimension(0, 600));
					{
						CreatePages();
						CWTRemittance_BIRDetails bir = new CWTRemittance_BIRDetails(new BorderLayout(5, 5));  
						
						jtpList.addTab("Pending Remittance", Panel1);
						jtpList.addTab("Remitted To BIR", Panel2);
						jtpList.addTab("BIR Accounts", bir);
						jtpList.addTab("Projected 25%", pnlProjected25);
					}
					jtpList.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							if(Mode.equals("Posting")){
								JOptionPane.showMessageDialog(null, "The list is already primed for posting.\nYou can no longer switch tabs.");
							}
							else{
								if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Pending Remittance")) {
									switch (Mode) {
										case "":
											ButtonLock("");
											break;
										case "Posting":
											ButtonLock("Posting");
											break;
										case "Posted Viewing":
											ButtonLock("Posted Viewing");
											break;
										}
								} else {
									ButtonLock("Remitted Viewing");
								};	
							}
						}
					});
					bindColumnTables();
				}
				{
					JXPanel pnlSubCont3 = new JXPanel(new BorderLayout(5, 5));
					pnlMain.add(pnlSubCont3, BorderLayout.PAGE_END);
					{
						pnlButtons = new JXPanel(new GridLayout(1, 5, 5, 5));
						pnlSubCont3.add(pnlButtons, BorderLayout.CENTER);
						pnlButtons.setPreferredSize(new Dimension(0, 30));
						{
							btnPreview = new JButton("Preview");
							pnlButtons.add(btnPreview);
							btnPreview.setActionCommand("Preview");
							btnPreview.addActionListener(this);
						}
						{
							btnPost = new JButton("Remit");
							pnlButtons.add(btnPost);
							btnPost.setActionCommand("Remit");
							btnPost.addActionListener(this);
						}
						{
							btnCancel = new JButton("Refresh");
							pnlButtons.add(btnCancel);
							btnCancel.setActionCommand("Cancel");
							btnCancel.addActionListener(this);
						}
						{
							btnExport = new JButton("Export");
							pnlButtons.add(btnExport);
							btnExport.setActionCommand("Export");
							btnExport.addActionListener(this);
						}
						{
							JXPanel pnlRPLF = new JXPanel(new GridLayout(1, 1, 10, 10));
							pnlButtons.add(pnlRPLF, BorderLayout.CENTER);
							pnlRPLF.setBorder(new EmptyBorder(5, 5, 5, 5));
							{
								txtRPLF = new JTextField("RPLF No.:");
								pnlRPLF.add(txtRPLF, BorderLayout.CENTER);
								txtRPLF.setPreferredSize(new Dimension(ParamWidth, ParamHeight));
								txtRPLF.setHorizontalAlignment(JTextField.CENTER);
								txtRPLF.setBackground(Color.DARK_GRAY);
								txtRPLF.setForeground(Color.WHITE);
								txtRPLF.setEditable(false);
							}
						}
						ButtonLock("");
					}
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Generate":
			FncGlobal.startProgress("Please wait...");
			
			if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Projected 25%")) {
				if (displayCWT(modelCWT25, rowHeaderCWT25)) {
					ButtonLock("Posting");					
				} else {
					ButtonLock("");
				};
			} else {
				
				if ((JOptionPane.showConfirmDialog(getContentPane(), "Include full DP clients?", "Confirm", JOptionPane.YES_NO_OPTION))==JOptionPane.YES_OPTION) {
					blnDP = true;
					this.setTitle(title + "(Full DP Clients Included)");
				} else {
					blnDP = false;
					this.setTitle(title);
				}

				if ((JOptionPane.showOptionDialog(getContentPane(), "Select date filter type", "Date Filter", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
						new Object[] {"  As of   ", "Date Range"}, JOptionPane.YES_OPTION))==JOptionPane.YES_OPTION) {
					blnAsof = true; 
				} else {
					blnAsof = false; 
				}
				
				if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Pending Remittance")) {
					if (displayCWT(modelCWT, rowHeaderCWT)) {
						ButtonLock("Posting");					
					} else {
						ButtonLock("");
					};
				} else {
					displayCWTRemitted(modelCWTRemitted_v2, rowHeaderCWTRemitted_v2);
					ButtonLock("Remitted Viewing");
				};
				
			}
			
			FncGlobal.stopProgress();
			break;
		case "Preview":
			FncGlobal.startProgress("Generating preview...");
			if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Pending Remittance") && btnPost.isEnabled()) {
				GenReportRemit(false);	
			}
			else if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Projected 25%") && btnPost.isEnabled()) {
				GenReportRemit(false);	
				System.out.println("Projected 25%");
			} else {
				GenReportRemitted(); 
			}
			FncGlobal.stopProgress();
			break;
		case "Remit":
			FncGlobal.startProgress("Processing...");
			
			CWTRemittance_BIRTagging bir = new CWTRemittance_BIRTagging(); 
			
			int option = 0; 
			option = JOptionPane.showOptionDialog(null, bir, "BIR Tagging", 
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
					new Object[] {"  Skip  ", "Continue"}, JOptionPane.CANCEL_OPTION);
			
			if (option==JOptionPane.NO_OPTION) {
				if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Projected 25%")) {
					saveBIR25();
				}
				else {
					saveBIR();
				}
			}
			if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Projected 25%")) {
				ComputeTaxable25();
				RemitCWT25();
			}
			else {
				ComputeTaxable();
				RemitCWT();
			}
			FncGlobal.stopProgress();
			break;
		case "Refresh":
			JOptionPane.showMessageDialog(this,	"Sorry. This part is not finished.");
			ButtonLock("");
			break;
		case "Cancel":
			try{
				FncTables.clearTable(modelCWT);
				FncTables.clearTable(modelCWT25);
			}
			catch(IndexOutOfBoundsException iobe){
				System.out.println("");
				System.out.println("Out of bounds exception caught.");
			}
			
			ButtonLock("");
			break;
		case "Export":
			FncGlobal.startProgress("Ceating spreadsheet");
			//Export();
			FncGlobal.stopProgress();
		default:
			FncTables.clearTable(modelCWT);
			FncTables.clearTable(modelCWT25);
			ButtonLock("");
			break;
		}
	}

	public void BoxLock(Boolean DoMode) {
		txtCoID.setEnabled(DoMode);
		txtProjectID.setEnabled(DoMode);
		dteFrom.setEnabled(DoMode);
		dteTo.setEnabled(DoMode);
	}
	
	public void RemitCWT(){
		if(tblCWT.getRowCount() < 1){
			{JOptionPane.showMessageDialog(getContentPane(), "There are no selected items.", "Caution", JOptionPane.INFORMATION_MESSAGE);}
		}
		else{
			
			String sdteTran = "";
			String sdteActual = "";
			
			SimpleDateFormat ssdfTo = new SimpleDateFormat("MM-dd-yyyy");
			
			sdteTran = (String) ssdfTo.format(CWTRemittance_BIRTagging.getReleasedDate().getDate());
			sdteActual = (String) ssdfTo.format(CWTRemittance_BIRTagging.getReleasedDate().getDate());
			
			JXPanel panConfirm = new JXPanel(new BorderLayout(5,5));
			{
				JPanel panMessage = new JPanel(new GridLayout(2,1));
				{
					JLabel lblMessage = new JLabel("You are about to remit these "+selected_count()+" selected clients");
					panMessage.add(new JLabel("Released Date: "+sdteTran+""));
					panMessage.add(lblMessage);
				}
				panConfirm.add(panMessage, BorderLayout.NORTH);
				
				JScrollPane scrollPanMessage = new JScrollPane(selected_clients(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
				scrollPanMessage.setPreferredSize(new Dimension(400, 200));
				
				panConfirm.add(scrollPanMessage, BorderLayout.CENTER);
			}
			
			
			
			int scanOption = JOptionPane.showOptionDialog(getContentPane(), panConfirm, "Remit Selected Clients ?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {"Confirm", "Cancel"}, null);
			
			if (scanOption == JOptionPane.YES_OPTION){
				
				Boolean blnSel = false;
				for(int w = 0; w < tblCWT.getRowCount(); w++){
					if((Boolean)tblCWT.getValueAt(w,0).equals(true)){
						blnSel = true;
					}
				}
				
				if(!blnSel){
					JOptionPane.showMessageDialog(null, "No entry was selected.");
				}
				else{
					try{
						for(int w = 0; w < tblCWT.getRowCount(); w++){
							if((Boolean)tblCWT.getValueAt(w,0).equals(true)){
								String strRPLF = GetNextRPLFno();
								Object stramount = tblCWT.getValueAt(w, (14+1));
								Double amount = 0.00;
								if(stramount != null) {
									amount = Double.parseDouble(tblCWT.getValueAt(w, (14+1)).toString());
								}
								
								
								String strID = "";
								String strProject = "";
								String strUnit = "";
								String strSeq = "";
								
								strID = tblCWT.getValueAt(w, (15+1)).toString();
								strProject = tblCWT.getValueAt(w, (16+1)).toString();
								strUnit = tblCWT.getValueAt(w, (17+1)).toString();
								strSeq = tblCWT.getValueAt(w, (18+1)).toString();
								
								System.out.println("");
								System.out.println("RPLF:			" + strRPLF);
								System.out.println("Client ID:		" + tblCWT.getValueAt(w, (15+1)).toString());
								System.out.println("Project ID:		" + tblCWT.getValueAt(w, (16+1)).toString());
								System.out.println("Unit/PBL ID:		" + tblCWT.getValueAt(w, (17+1)).toString());
								System.out.println("Sequence No.:		" + tblCWT.getValueAt(w, (18+1)).toString());
								System.out.println("Type:			" + tblCWT.getValueAt(w, (2+1)).toString());
								
								
								
								String strStat = "";
								Boolean blnLog = false;
								Boolean blnTaxable = false;
								
								if (tblCWT.getValueAt(w, (2+1)).toString().equals("Socialized") && !(Boolean) tblCWT.getValueAt(w, (4+1))) {
									strStat = "123";
								} else if (!tblCWT.getValueAt(w, (2+1)).toString().equals("Socialized") && !(Boolean) tblCWT.getValueAt(w, (4+1))) {
									strStat = "122";
								} else {
									strStat = "112";
								}
								
								if((Boolean) tblCWT.getValueAt(w, (3+1)).equals(true)) {
									blnLog = true;
								} else {
									blnLog = false;
								};
								
								/****************************************/
								/***								  ***/
								/***	rf_sold_unit update block  	  ***/
								/***								  ***/
								/****************************************/
								/****************************************/
								/***								  ***/
								/***	Insert into buyer's status 	  ***/
								/***								  ***/
								/****************************************/
								/*
								String dteTran = dateFormat.format(Calendar.getInstance().getTime()).toString();
								String dteActual = dateFormat.format(Calendar.getInstance().getTime()).toString();
								*/
								/***	***/
								String dteTran = "";
								String dteActual = "";
								
								SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
								
								dteTran = (String) sdfTo.format(CWTRemittance_BIRTagging.getReleasedDate().getDate());
								dteActual = (String) sdfTo.format(CWTRemittance_BIRTagging.getReleasedDate().getDate());
								
								System.out.println("dteTran: "+dteTran);
								System.out.println("dteActual: "+dteActual);
								
								/*
								 * Commented by jari cruz asof feb 27 2023
								panDate = new JXPanel(new GridLayout(3, 1));
								//panDate.setPreferredSize(new Dimension(100, 30));
								{
									dteRefDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									String clientName = (String) tblCWT.getValueAt(w, 2);
									String clientLot = (String) tblCWT.getValueAt(w, 10);
									panDate.add(new JLabel("Name: "+clientName+""));
									panDate.add(new JLabel("Lot: "+clientLot+""));
									panDate.add(dteRefDate);
									//String msg = "Name: '"+clientName+"'\nLot:'"+clientLot+"'\n";
									dteRefDate.getCalendarButton().setVisible(true);
									dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dteRefDate.setPreferredSize(new Dimension(0, 30));
								}
								*/
								System.out.println("");
								System.out.println("dteTran: " + dteTran);
								System.out.println("dteActual: " + dteActual);
								/***	***/
								
								if(tblCWT.getValueAt(w, (2+1)).toString().equals("Socialized")){
									modelCWT.setValueAt(strRPLF, w, (22+1));
									updateSoldUnits(strRPLF, strID, strProject, strUnit, strSeq, amount);
									InsertBuyerStatus(strRPLF, strID, strProject, strUnit, strSeq, strStat, blnLog, dteTran, dteActual);
								}
								else{
									modelCWT.setValueAt("---", w, (22+1));
									updateSoldUnits("", strID, strProject, strUnit, strSeq, amount);
									InsertBuyerStatus("", strID, strProject, strUnit, strSeq, strStat, blnLog, dteTran, dteActual);
								}
															
								/****************************************/
								/***								  ***/
								/***	Insert to header table		  ***/
								/***								  ***/
								/****************************************/
								if(tblCWT.getValueAt(w, (2+1)).toString().equals("Socialized") || (Boolean) tblCWT.getValueAt(w, (4+1))){
									Date date_liq_ext	= null;
									String rplf_type_id = "01";
									String entity_id1	= "9925872879";						//To be changed to Landbank Marcos Highway FAO; Bureau Of Internal Revenue
									String entity_id2	= strID;
									String ent_type_id 	= "12";
									String sd_no		= "";
									String doc_id		= "09";
									Integer proc_id		= 0;	
									String branch_id	= "";	
									String justif		= "";	
									String remarks		= "Payment for remittance of creditable withholding tax for the month of ";	
									String status_id	= "A";	
									String created_by	= UserInfo.EmployeeCode;
									String edited_by	= "";
									String pay_type		= "B";
									Date edited_date	= null;
									
									String sqlHeader = "INSERT into rf_request_header values (" +
									"'"+txtCoID.getText()+"', " +												//1
									"'"+txtCoID.getText()+"', " +												//2
									"'"+strRPLF+"', " +															//3
									"'"+dateFormat.format(Calendar.getInstance().getTime())+"', " +				//4
									"'"+dateFormat.format(Calendar.getInstance().getTime())+"', " +  			//5
									""+date_liq_ext+", " +	 													//6
									"'"+rplf_type_id+"', " +													//7
									"'"+entity_id1+"', " +														//8
									"'"+entity_id2+"', " +														//9
									"'"+ent_type_id+"', \n" +													//10
									"'"+sd_no+"', " +															//11
									"'"+doc_id+"', " +															//12
									""+proc_id+", " +															//13
									"'"+branch_id+"', " +														//14
									"'"+justif+"', " +															//15
									"'"+remarks+"', " +															//16
									"'"+status_id+"', " +														//17
									"'"+created_by+"', " +														//18
									"'"+dateFormat.format(Calendar.getInstance().getTime())+"', " +				//19
									"'"+edited_by+"', " +														//20
									""+edited_date+", " +														//21
									"'"+pay_type+"'" +															//22
									")";
									
									System.out.println("");
									System.out.println("*************************************");
									System.out.println("***				  ***");
									System.out.println("*** Insert to header table        ***");
									System.out.println("***				  ***");
									System.out.println("*************************************");
									System.out.println("RPLF No.:	" + strRPLF);
									System.out.println("");
									System.out.println(sqlHeader);
									pgUpdate dbHeader = new pgUpdate();
									dbHeader.executeUpdate(sqlHeader, false);
									dbHeader.commit();
									
									/****************************************/
									/***								  ***/
									/***	Insert to detail table		  ***/
									/***								  ***/
									/****************************************/
			
									String ref_doc_id 	= "";
									String ref_doc_no 	= "";
									Boolean	with_budget	= false;
									Boolean	isprojVatable	= false;
									Boolean	entityVatable	= false;
									Boolean	istaxPaidbyco	= false;
									String part_desc	= "Payment for remittance of creditable withholding tax for the month of ";
									String acct_id		= "03-01-06-003";
									String entity_id	= "";
									String entity_type_id = "12";
									String proj_id		= tblCWT.getValueAt(w, (16+1)).toString();
									String subproj_id	= GetSubProject(proj_id, tblCWT.getValueAt(w, (8+1)).toString());
									String div_id		= "";
									String dept_id		= "";
									String sec_id		= "";
									String inter_bus_id	= "";
									String inter_co_id	= "";
									String asset_no		= "";
									String old_acct_id	= "";	
									String wtax_id		= "";	
			
									acct_id		= "03-01-06-002"; //withholding tax payable-expanded
									entity_id	= GetEntityID(UserInfo.EmployeeCode);			
									entity_type_id	= "02";	
									Double wtax_rate	= 0.00;	
									Double wtax_amount	= 0.00;
									Double vat_rate		= 0.00;
									Double vat_amount	= 0.00;
									Double exp_amount	= amount;
									Double pv_amount	= amount;
									
									String sqlDetail = "INSERT into rf_request_detail values (" +
										"'"+txtCoID.getText()+"', " +
										"'"+txtCoID.getText()+"', " +
										"'"+strRPLF+"', " +
										"1, " +
										"'"+ref_doc_id+"', " +					
										"'"+ref_doc_no+"', " +				
										"null, " +								
										""+with_budget+", " +
										"'"+part_desc+"', " +
										"'"+acct_id+"', \n" +
										"'"+remarks+"', " +	
										""+amount+", " +
										"'"+entity_id+"', " +			
										"'"+entity_type_id+"', " +	
										"'"+proj_id+"', " +			
										"'"+subproj_id+"', " +
										"'"+div_id+"', " +
										"'"+dept_id+"', " +
										"'"+sec_id+"', " +	
										"'"+inter_bus_id+"', " +
										"'"+inter_co_id+"', " +
										""+isprojVatable+", " +
										""+entityVatable+", " +
										""+istaxPaidbyco+", " +
										"false, \n" +
										"'"+wtax_id+"', " +
										""+wtax_rate+", " +
										""+wtax_amount+", " +
										"null, " +
										""+vat_rate+", " +
										""+vat_amount+", " +
										""+exp_amount+", " +		
										""+pv_amount+", " +
										"'"+sd_no+"', " +
										"'', " +
										"'"+asset_no+"', " +
										"'"+old_acct_id+"', " +				
										"'A', " +
										"'"+UserInfo.EmployeeCode+"', " +
										"'"+dateFormat.format(Calendar.getInstance().getTime())+"', " +
										"'', null)";
									
									System.out.println("");
									System.out.println("*************************************");
									System.out.println("***				  ***");
									System.out.println("*** Insert to detail table        ***");
									System.out.println("***				  ***");
									System.out.println("*************************************");
									System.out.println("Value of variable Amount:		" + amount);
									System.out.println("Project ID:				" + tblCWT.getValueAt(w, (15+1)).toString());
									System.out.println("Phase:					" + tblCWT.getValueAt(w, (6+1)).toString());
									System.out.println("Project Sub ID:				" + GetSubProject(proj_id, tblCWT.getValueAt(w, (8+1)).toString()));
									System.out.println("");
									System.out.println(sqlDetail);
									pgUpdate dbDetail = new pgUpdate();
									dbDetail.executeUpdate(sqlDetail, false);
									dbDetail.commit();
								}
							}				
						}
						JOptionPane.showMessageDialog(null, "Posting finished.");
					}
					catch (IndexOutOfBoundsException e) {
					    System.err.println("IndexOutOfBoundsException: " + e.getMessage());
					}
					DeleteRows(modelCWT);
					ButtonLock("Posted Viewing");
				}
			}	
		}
	}
	
	public void RemitCWT25(){
		if(tblCWT25.getRowCount() < 1){
			{JOptionPane.showMessageDialog(getContentPane(), "There are no selected items.", "Caution", JOptionPane.INFORMATION_MESSAGE);}
		}
		else{
			
			String sdteTran = "";
			String sdteActual = "";
			
			SimpleDateFormat ssdfTo = new SimpleDateFormat("MM-dd-yyyy");
			
			sdteTran = (String) ssdfTo.format(CWTRemittance_BIRTagging.getReleasedDate().getDate());
			sdteActual = (String) ssdfTo.format(CWTRemittance_BIRTagging.getReleasedDate().getDate());
			
			JXPanel panConfirm = new JXPanel(new BorderLayout(5,5));
			{
				JPanel panMessage = new JPanel(new GridLayout(2,1));
				{
					JLabel lblMessage = new JLabel("You are about to remit these "+selected_count_25()+" selected clients");
					panMessage.add(new JLabel("Released Date: "+sdteTran+""));
					panMessage.add(lblMessage);
				}

				panConfirm.add(panMessage, BorderLayout.NORTH);
				
				JScrollPane scrollPanMessage = new JScrollPane(selected_clients_25(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
				scrollPanMessage.setPreferredSize(new Dimension(400, 200));
				
				panConfirm.add(scrollPanMessage, BorderLayout.CENTER);
			}
			
			int scanOption = JOptionPane.showOptionDialog(getContentPane(), panConfirm, "Remit Selected Clients ?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {"Confirm", "Cancel"}, null);
			
			if (scanOption == JOptionPane.YES_OPTION){
				
				Boolean blnSel = false;
				for(int w = 0; w < tblCWT25.getRowCount(); w++){
					if((Boolean)tblCWT25.getValueAt(w,0).equals(true)){
						blnSel = true;
					}
				}
				
				if(!blnSel){
					JOptionPane.showMessageDialog(null, "No entry was selected.");
				}
				else{
					try{
						for(int w = 0; w < tblCWT25.getRowCount(); w++){
							if((Boolean)tblCWT25.getValueAt(w,0).equals(true)){
								//String strRPLF = GetNextRPLFno();
								String strRPLF = "";
								Object stramount = tblCWT25.getValueAt(w, (15));
								Double amount = 0.00;
								if(stramount != null) {
									amount = Double.parseDouble(tblCWT25.getValueAt(w, (15)).toString());
								}
								
								
								String strID = "";
								String strProject = "";
								String strUnit = "";
								String strSeq = "";
								
								strID = tblCWT25.getValueAt(w, (16)).toString();
								strProject = tblCWT25.getValueAt(w, (17)).toString();
								strUnit = tblCWT25.getValueAt(w, (18)).toString();
								strSeq = tblCWT25.getValueAt(w, (19)).toString();
								
								System.out.println("");
								System.out.println("RPLF:			" + strRPLF);
								System.out.println("Client ID:		" + tblCWT25.getValueAt(w, (16)).toString());
								System.out.println("Project ID:		" + tblCWT25.getValueAt(w, (17)).toString());
								System.out.println("Unit/PBL ID:		" + tblCWT25.getValueAt(w, (18)).toString());
								System.out.println("Sequence No.:		" + tblCWT25.getValueAt(w, (19)).toString());
								System.out.println("Type:			" + tblCWT25.getValueAt(w, (3)).toString());
								
								
								
								String strStat = "";
								Boolean blnLog = false;
								Boolean blnTaxable = false;
								
								if (tblCWT25.getValueAt(w, (3)).toString().equals("Socialized") && !(Boolean) tblCWT25.getValueAt(w, (5))) {
									strStat = "123";
								} else if (!tblCWT25.getValueAt(w, (3)).toString().equals("Socialized") && !(Boolean) tblCWT25.getValueAt(w, (5))) {
									strStat = "122";
								} else {
									strStat = "112";
								}
								
								if((Boolean) tblCWT25.getValueAt(w, (4)).equals(true)) {
									blnLog = true;
								} else {
									blnLog = false;
								};
								
								/****************************************/
								/***								  ***/
								/***	rf_sold_unit update block  	  ***/
								/***								  ***/
								/****************************************/
								/****************************************/
								/***								  ***/
								/***	Insert into buyer's status 	  ***/
								/***								  ***/
								/****************************************/
								/*
								String dteTran = dateFormat.format(Calendar.getInstance().getTime()).toString();
								String dteActual = dateFormat.format(Calendar.getInstance().getTime()).toString();
								*/
								/***	***/
								String dteTran = "";
								String dteActual = "";
								
								SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
								
								dteTran = (String) sdfTo.format(CWTRemittance_BIRTagging.getReleasedDate().getDate());
								dteActual = (String) sdfTo.format(CWTRemittance_BIRTagging.getReleasedDate().getDate());
								
								/*
								 * Commented by jari cruz asof feb 27 2023
								panDate = new JXPanel(new GridLayout(3, 1));
								//panDate.setPreferredSize(new Dimension(100, 30));
								{
									dteRefDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									String clientName = (String) tblCWT25.getValueAt(w, 2);
									String clientLot = (String) tblCWT25.getValueAt(w, 10);
									panDate.add(new JLabel("Name: "+clientName+""));
									panDate.add(new JLabel("Lot: "+clientLot+""));
									panDate.add(dteRefDate);
									//String msg = "Name: '"+clientName+"'\nLot:'"+clientLot+"'\n";
									dteRefDate.getCalendarButton().setVisible(true);
									dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dteRefDate.setPreferredSize(new Dimension(0, 30));
								}
								*/
								
								System.out.println("");
								System.out.println("dteTran: " + dteTran);
								System.out.println("dteActual: " + dteActual);
								/***	***/
								
								if(tblCWT25.getValueAt(w, (3)).toString().equals("Socialized")){
									modelCWT25.setValueAt(strRPLF, w, (23));
									updateSoldUnits(strRPLF, strID, strProject, strUnit, strSeq, amount);
									InsertBuyerStatus(strRPLF, strID, strProject, strUnit, strSeq, strStat, blnLog, dteTran, dteActual);
								}
								else{
									modelCWT25.setValueAt("---", w, (23));
									updateSoldUnits("", strID, strProject, strUnit, strSeq, amount);
									InsertBuyerStatus("", strID, strProject, strUnit, strSeq, strStat, blnLog, dteTran, dteActual);
								}
							}				
						}
						JOptionPane.showMessageDialog(null, "Posting finished.");
					}
					catch (IndexOutOfBoundsException e) {
					    System.err.println("IndexOutOfBoundsException: " + e.getMessage());
					}
					DeleteRows(modelCWT25);
					ButtonLock("Posted Viewing");
				}
			}	
		}
	}
	
	/****************************************/
	/***								  ***/
	/***	rf_sold_unit update block  	  ***/
	/***								  ***/
	/****************************************/
	public void updateSoldUnits(String strRPLF, String strID, String strProject, String strUnit, String strSeq, Double dblAmt){
		pgUpdate db = new pgUpdate();
		String sqlSold;
		if(chechIfOtherLot(strID,strProject,strUnit,strSeq) != null) {
			System.out.println("other lot");
			strUnit = chechIfOtherLot(strID,strProject,strUnit,strSeq);
			BigDecimal current_amt = cwt_amt_remitted(strID,strProject,strUnit,strSeq);
			sqlSold = "UPDATE rf_sold_unit SET cwt_amt_remitted = ("+current_amt+"+"+Double.toString(dblAmt)+"), \n" +
					 "cwt_rplf_no = '"+strRPLF+"' \n" + 
					 "WHERE entity_id = '"+strID+"' \n" +
					 "AND projcode = '"+strProject+"' \n" +
					 "AND pbl_id = '"+strUnit+"' \n" +
					 "and seq_no = "+strSeq+" and status_id = 'A'";
		} else {
			sqlSold = "UPDATE rf_sold_unit SET cwt_amt_remitted = "+Double.toString(dblAmt)+", \n" +
					 "cwt_rplf_no = '"+strRPLF+"' \n" + 
					 "WHERE entity_id = '"+strID+"' \n" +
					 "AND projcode = '"+strProject+"' \n" +
					 "AND pbl_id = '"+strUnit+"' \n" +
					 "and seq_no = "+strSeq+" and status_id = 'A'";
		}

		System.out.println("");
		System.out.println("*************************************");
		System.out.println("***				  ***");
		System.out.println("*** rf_sold_unit update block     ***");
		System.out.println("***				  ***");
		System.out.println("*************************************");
		System.out.println(sqlSold);
		db.executeUpdate(sqlSold, false);
		db.commit();
	};
	
	/****************************************/
	/***								  ***/
	/***	Insert into buyer's status 	  ***/
	/***								  ***/
	/****************************************/
	public void InsertBuyerStatus(String strRPLF, String strID, String strProject, String strUnit, String strSeq, String strStat, Boolean blnLog, String dteTran, String dteActual){
		pgUpdate db = new pgUpdate();
		
		
		/*	As mandated by Orly; 
		String sqlStat = "INSERT INTO rf_buyer_status \n" + 
				"(\"entity_id\", \"proj_id\", \"pbl_id\", \"seq_no\", \"byrstatus_id\", \"tran_date\", \"actual_date\", \"status_id\", \"trans_no\", \"created_by\")\n" + 
				"VALUES\n" + 
				"('"+strID+"', '"+strProject+"', '"+strUnit+"', "+strSeq+", '"+strStat+"', \n"+
				"'"+dteTran+"', '"+dteActual+"', 'A','"+strRPLF+"', '"+UserInfo.EmployeeCode+"')";
		*/
		
		String sqlStat = "INSERT INTO rf_buyer_status \n" + 
				"(\"entity_id\", \"proj_id\", \"pbl_id\", \"seq_no\", \"byrstatus_id\", \"tran_date\", \"actual_date\", \"status_id\", \"trans_no\", \"created_by\")\n" + 
				"VALUES\n" + 
				"('"+strID+"', '"+strProject+"', '"+strUnit+"', "+strSeq+", '"+strStat+"', \n"+
				"now()::date, '"+dteActual+"', 'A','"+strRPLF+"', '"+UserInfo.EmployeeCode+"')";
		
		System.out.println("");
		System.out.println("*************************************");
		System.out.println("***				  ***");
		System.out.println("*** Insert into buyer's status    ***");
		System.out.println("***				  ***");
		System.out.println("*************************************");
		System.out.println("Status:			" + strStat);
		System.out.println("Log:			" + blnLog);
		System.out.println("sqlStat: "+sqlStat);
		db.executeUpdate(sqlStat, false);
		db.commit();
		
		if (blnLog) {
			pgUpdate dbLog = new pgUpdate();
			String sqlLog = "insert into rf_buyer_status \n" + 
					"(\"entity_id\", \"proj_id\", \"pbl_id\", \"seq_no\", \"byrstatus_id\", \"tran_date\", \"actual_date\", \"status_id\", \"trans_no\", \"created_by\")\n" + 
					"VALUES\n" + 
					"('"+strID+"', '"+strProject+"', '"+strUnit+"', "+strSeq+", '"+"124"+"', \n"+
					"'"+dteTran+"', '"+dteActual+"', 'A','"+strRPLF+"', '"+UserInfo.EmployeeCode+"')";
			
			dbLog.executeUpdate(sqlLog, false);
			dbLog.commit();
		}
	};
	
	public void InsertHeader(){
		
	};
	
	public void InsertDetail(){
		
	};

	public void GenReport(String strRPLF){
		String strCompany = txtCoName.getText().toUpperCase();
		String strDate = "";
		
		SimpleDateFormat sdfFrom = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		
		String strFrom = (String)sdfFrom.format(dteFrom.getDate());
		String strTo = (String)sdfTo.format(dteTo.getDate());
		
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		
		if(strFrom.equals(strTo)){
			strDate = "Report as of: " + strFrom;
		}
		else{
			strDate = "As Of: " + strFrom + " to " + strTo;
		};
		
		try{
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", strCompany);
			mapParameters.put("02_AsOfDate", strDate);
			mapParameters.put("03_Title", "Creditable Withholding Tax Report");
			mapParameters.put("04_dFrom", strFrom);
			mapParameters.put("05_dTo", strTo);
			mapParameters.put("06_PendiRemi", "P");
			mapParameters.put("07_User", GetName(UserInfo.EmployeeCode));
			mapParameters.put("08_CoID", txtCoID.getValue());
			mapParameters.put("09_ProjectID", txtProjectID.getText());
			mapParameters.put("10_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("11_includedp", GetName(UserInfo.EmployeeCode));
			FncReport.generateReport("/Reports/rptCWT_Final.jasper", "Creditable Withholding Tax", "", mapParameters);
		}
		catch(NullPointerException e){
			JOptionPane.showMessageDialog(null, "Null pointer exception caught.\nContact the program author.");
		};
	};
	
	public void GenReportRemit(Boolean blnRemitted){
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		String strCompany = txtCoName.getText().toUpperCase();
		String strDate = "";
		
		SimpleDateFormat sdfFrom = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		
		String strFrom = (String) sdfFrom.format(dteFrom.getDate());
		String strTo = (String) sdfTo.format(dteTo.getDate());
		
		if (strFrom==strTo) {
			strDate = "Report as of: " + strFrom;
		} else {
			strDate = "As Of: " + strFrom + " to " + strTo;
		};
		
		System.out.println("");
		System.out.println("01_Company: " + strCompany);
		System.out.println("02_AsOfDate: " + strDate);
		System.out.println("03_Title: " + "Creditable Withholding Tax Report");
		System.out.println("04_dFrom: " + strFrom);
		System.out.println("05_dTo: " + strTo);
		System.out.println("06_PendiRemi: " + "P");
		System.out.println("07_User: " + GetName(UserInfo.EmployeeCode));
		System.out.println("08_CoID: " + txtCoID.getValue());
		System.out.println("09_ProjectID: " + txtProjectID.getText());
		System.out.println("11_includedp: " + blnDP);
		System.out.println("12_includedp: " + blnAsof);
		
		try {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", strCompany);
			mapParameters.put("02_AsOfDate", strDate);
			mapParameters.put("03_Title", "Creditable Withholding Tax Report");
			mapParameters.put("04_dFrom", strFrom);
			mapParameters.put("05_dTo", strTo);
			mapParameters.put("06_PendiRemi", blnRemitted);
			mapParameters.put("07_User", GetName(UserInfo.EmployeeCode));
			mapParameters.put("08_CoID", txtCoID.getValue());
			mapParameters.put("09_ProjectID", txtProjectID.getValue());
			mapParameters.put("10_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters.put("11_includedp", blnDP);
			mapParameters.put("12_asof", blnAsof);
			if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Projected 25%")) {
				FncReport.generateReport("/Reports/rptCWT_Final25.jasper", "Creditable Withholding Tax", "", mapParameters);
			} 
			else {
				FncReport.generateReport("/Reports/rptCWT_Final.jasper", "Creditable Withholding Tax", "", mapParameters);
			}
		} catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Null pointer exception caught.\nContact the program author.");
		};
	};
	
	public void DeleteRows(DefaultTableModel modelMain){
		for (int x = 0; x < modelMain.getRowCount() - 1; x++) {
			if (modelMain.getValueAt(x, 0).equals(false)) {
				modelMain.removeRow(x);
			}
			System.out.println("");
			System.out.println("Row: " + x);
		}
		for (int x = modelMain.getRowCount() - 1; x > 0; x--) {
			if (modelMain.getValueAt(x, 0).equals(false)) {
				modelMain.removeRow(x);
			}
		}
	}
	
	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;
		
		/*	Company Default	*/
		strCount = FncGlobal.GetString("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtCoID.setValue("");
			txtCoName.setText("All Companies");
		} else {
			txtCoID.setValue(FncGlobal.GetString("SELECT co_id FROM mf_company LIMIT 1"));
			
			try {
				String strCo = FncGlobal.GetString("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getValue()+"'");
				System.out.println(strCo);
				txtCoName.setText(strCo);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtCoID: " + txtCoID.getValue());
				System.out.println("txtCoName: " + FncGlobal.GetString("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getValue()+"'"));
			}
		}
		
		/*	Project Default	*/
		strCount = FncGlobal.GetString("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtProjectID.setValue("");
			txtProjectName.setText("All Projects");
		} else {
			/* commented by jari cruz asof august 26 2022, reason this set random value without regarding what company is chosen
			 * txtProjectID.setValue(FncGlobal.GetString("SELECT proj_id FROM mf_project LIMIT 1"));*/
			
			try {
				String strPro = FncGlobal.GetString("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProjectID.getValue()+"'");
				System.out.println(strPro);
				txtProjectName.setText(strPro);	
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtProjectID: " + txtProjectID.getValue());
				System.out.println(FncGlobal.GetString("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProjectID.getValue()+"'"));
			}
		}
	}
	
	public static String GetEntityID(String emp_code){
		String entityid = "";

		String SQL = "SELECT entity_id FROM em_employee WHERE emp_code = '"+emp_code+"' \r\n";

		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			entityid = (String) sqlExec.getResult()[0][0];
		}else{
			entityid = "";
		}

		return entityid;
	}
	
	public static String GetName(String emp_code){
		String entityid = "";

		String SQL = "SELECT B.entity_name\n" + 
		"FROM em_employee A\n" + 
		"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + 
		"WHERE a.emp_code = '"+emp_code+"'";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			entityid = (String) sqlExec.getResult()[0][0];
		}else{
			entityid = "";
		}

		return entityid;
	}
	
	public static String GetSubProject(String strProj, String strPhase){
		String strID = "";
		
		String SQL = "SELECT sub_proj_id FROM mf_sub_project WHERE proj_id = '"+strProj+"' and phase = '"+strPhase+"' AND status_id = 'A'";//ADDED STATUS ID BY LESTER DCRF 2718
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);
		
		if(sqlExec.isNotNull()){
			strID = (String)sqlExec.getResult()[0][0];
		}
		else{
			strID = "";
		}
		
		return strID;
	}
	
	public static String GetNextRPLFno() {//EDITED BY JED 2021-05-17 : CHANGE THE GENERATION OF RPLF INSIDE THE FUNCTION 
		String rplf ="";
		//String SQL = "select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+txtCoID.getText()+"' " ;
		String SQL = "select * from fn_get_rplf_no('"+txtCoID.getText()+"')" ;
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			rplf = (String) db.getResult()[0][0];
		}
		else{
			rplf = "000000001";
		}

		return rplf;
	}
	
	public static String SQL_PROJECT() {
		String SQL = "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\" \n"
				+ "FROM mf_project \n"
				+ "WHERE (co_id = '"
				+ txtCoID.getText()
				+ "' OR '"
				+ txtCoID.getText()
				+ "' = '') \n"
				+ "ORDER BY proj_id;";
		
		System.out.println(SQL);
		return SQL;
	}

	public static String getEntityList(String strType) {
		String SQL = "SELECT \r\n"
				+ "DISTINCT on (a.entity_id) "
				+ "a.entity_id AS \"Entity ID\", \r\n"
				+ "upper(trim(a.entity_name))  AS \"Entity Name\", \r\n"
				+ "upper(trim(a.entity_alias)) AS \"Entity Alias\" \r\n"
				+ "FROM rf_entity a\r\n"
				+ "LEFT JOIN rf_entity_assigned_type b ON a.entity_id=b.entity_id\r\n"
				+ "LEFT JOIN em_employee c ON a.entity_id = c.entity_id\r\n"
				+ "WHERE a.status_id = 'A' AND (b.entity_type_id = '" + strType
				+ "' OR '" + strType + "' = '')\r\n";

		return SQL;
	}

	public static String getPV_no(String strCoID) {
		return "SELECT \r\n"
				+ "a.pv_no as \"PV No.\" , \r\n"
				+ "a.pv_date as \"PV Date\"      ,\r\n"
				+ "TRIM(b.entity_name) AS \"Payee\"    ,\r\n"
				+ "c.status_desc AS \"status\"  ,\r\n"
				+ "a.date_posted AS \"Date Posted\"  \r\n"
				+ "FROM rf_pv_header a  \r\n"
				+ "LEFT JOIN rf_entity b ON a.entity_id1 = b.entity_id\r\n"
				+ "LEFT JOIN mf_record_status c ON a.status_id = c.status_id \n"
				+ "WHERE (a.co_id = '" + strCoID + "' OR '\"+strCoID+\"' = '')"
				+ "ORDER BY a.pv_no desc";
	}

	public static Boolean displayCWT(DefaultTableModel modelMain, JList rowHeader) {
		Boolean blnRev = false;
		
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 

		String SQL = null;
		SQL = "SELECT * \n" +
				 "FROM view_cwt_remittance_v2 ('"+ txtCoID.getText()+ "'::VARCHAR(2), '"+ txtProjectID.getText()+ "'::VARCHAR(3), '"+dteFrom.getDate().toString()+"'::VARCHAR(30), '"+dteTo.getDate().toString()+"'::VARCHAR(30), false, "+blnDP+", "+blnAsof+") \n" +
				 "ORDER BY c_name";
		if (jtpList.getTitleAt(jtpList.getSelectedIndex()).equals("Projected 25%")) {
			SQL = "select * from view_cwt_remittance_25('"+txtCoID.getText()+"','"+txtProjectID.getText()+"','"+dteFrom.getDate().toString()+"','"+dteTo.getDate().toString()+"',false) ORDER BY c_name";
		}

		System.out.println("");
		System.out.println("*************************************");
		System.out.println("***				  ***");
		System.out.println("***	To Be Remitted                ***");
		System.out.println("***				  ***");
		System.out.println("*************************************");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			System.out.println("row count: "+modelMain.getRowCount());
			blnRev = true;
		}
		else{
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			blnRev = false;
		};
		
		return blnRev;
	};

	public static Boolean displayCWTRemitted(DefaultTableModel modelMain, JList rowHeader) {
		Boolean blnRev = false;
		
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		/*old SQL*/
//		String SQL = "SELECT c_name, c_mark, c_type, c_wlog, c_taxable ,c_project, c_status, c_Release_Batch, c_phase, c_block, c_lot, c_vatable, c_lotarea, \n" + 
//				"c_model_code, c_gross, c_discount, c_net, c_rate, c_amount, c_entity_id, c_projcode, c_pbl_id, c_seq_no, c_com_tin1, \n" +
//				"c_com_tin2, c_com_tin3, c_com_tin4, c_company_name, c_company_address, c_tin, \n" +
//				"c_name_1, c_address, c_zipcode, c_ipsewt, c_atc, c_1st_qtr, c_2nd_qtr, c_3rd_qtr, c_4th_qtr, c_tct, c_rplf, c_remitted_date \n" +
//				"FROM view_cwt_remittance ('"+ txtCoID.getText()+ "'::VARCHAR(2), '"+ txtProjectID.getText()+ "'::VARCHAR(3), '"+dteFrom.getDate().toString()+"'::VARCHAR(30), '"+dteTo.getDate().toString()+"'::VARCHAR(30), true) \n" +
//				"ORDER BY c_name";
		
		//SQL to para sa mga remitted
		String SQL = "SELECT c_or_number, c_name, c_type, c_wlog, c_taxable, c_project, c_status, c_phase, c_block, c_lot, c_model_code, c_gross, c_net, c_rate, c_amount, c_entity_id, c_projcode, c_pbl_id, c_seq_no, c_ipsewt, c_atc, c_4th_qtr, c_tct, c_rplf from view_cwt_remittance ('"+ txtCoID.getText()+ "'::VARCHAR(2), '"+ txtProjectID.getText()+ "'::VARCHAR(3), '"+dteFrom.getDate().toString()+"'::VARCHAR(30), '"+dteTo.getDate().toString()+"'::VARCHAR(30), true) \n" +
				"ORDER BY c_name";

		System.out.println("");
		System.out.println("*************************************");
		System.out.println("***				  ***");
		System.out.println("***	Remitted                      ***");
		System.out.println("***				  ***");
		System.out.println("*************************************");
		System.out.println(SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			blnRev = true;
		}
		else {
			JOptionPane.showMessageDialog(null, "No remitted records were returned.");
			blnRev = false;
		};
		
		return blnRev;
	}

	private void bindColumnTables() {
		for (int x = 0; x < tblCWT.getColumnCount(); x++) {
			String headerValue = (String) tblCWT.getColumnExt(x).getHeaderValue();
			
			tblCWT.getColumnExt(headerValue).addPropertyChangeListener(
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							
						}
					});
		}
	}

	public void LockCtrl(Boolean Do) {
		btnLoad.setEnabled(!Do);
		//btnPreview.setEnabled(Do);
		btnPost.setEnabled(Do);
		
	};
	
	public void ButtonLock(String strMode){		
		
		switch (strMode) {
		case "Posting":
			jtpList.setEnabledAt(1, false);
			BoxLock(false);
			Mode = "Posting";
			btnGenerate.setEnabled(false);
			btnPreview.setEnabled(true);
			btnPost.setEnabled(true);
			btnCancel.setEnabled(true);
			btnExport.setEnabled(true);
			break;
		case "Posted Viewing":
			jtpList.setEnabledAt(1, false);
			BoxLock(false);
			Mode = "Posted Viewing";
			btnGenerate.setEnabled(false);
			btnPreview.setEnabled(true);
			btnPost.setEnabled(false);
			btnCancel.setEnabled(true);
			btnExport.setEnabled(true);
			break;
		case "Remitted Viewing":
			BoxLock(true);
			btnGenerate.setEnabled(true);
			btnPreview.setEnabled(true);
			btnPost.setEnabled(false);
			btnCancel.setEnabled(false);
			btnExport.setEnabled(true);
			break;
		case "Export":

			break;
		case "":
			jtpList.setEnabledAt(1, true);
			BoxLock(true);
			Mode = "";
			btnGenerate.setEnabled(true);
			btnPreview.setEnabled(false);
			btnPost.setEnabled(false);
			btnCancel.setEnabled(false);
			btnExport.setEnabled(false);
			break;
		}
	}

	public void CreatePages() {
		Panel1 = new JXPanel(new GridLayout(1, 1, 0, 0));
		Panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
		Panel1.setOpaque(isOpaque());
		{
			scrollMainCenter = new JScrollPane();
			Panel1.add(scrollMainCenter, BorderLayout.CENTER);
			scrollMainCenter.setBorder(lineBorderGray);
			scrollMainCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollMainCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			{
				modelCWT = new model_CWTtable();
				tblCWT = new _JTableMain(modelCWT);
				tblCWT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if(!e.getValueIsAdjusting()){
							Integer intRow = tblCWT.convertRowIndexToModel(tblCWT.getSelectedRow());
							
							System.out.println("");
							System.out.println((String) tblCWT.getValueAt(intRow, (2+1)).toString());
							
							if (tblCWT.getValueAt(intRow, (2+1)).toString().equals("BOI")) {
								
								txtRPLF.setText("Not Applicable");
								
							} else {
								
								try{
									String strRPLF = (String)tblCWT.getValueAt(intRow, (22+1)).toString();
									if(strRPLF.equals("---")||(strRPLF.equals(""))){
										txtRPLF.setText("Not Applicable");
									} else {
										txtRPLF.setText("RPLF No.: " + strRPLF);
									}
								} catch(NullPointerException err) {
									txtRPLF.setText("RPLF No.: ");
									System.out.println("");
									System.out.println("Null pointer exception caught.");
								}
								
							}
						}
					}
				});
				
				rowHeaderCWT = tblCWT.getRowHeader();
				scrollMainCenter.setViewportView(tblCWT);

				tblCWT.getColumnModel().getColumn(0).setPreferredWidth(50);
				tblCWT.getColumnModel().getColumn(1).setPreferredWidth(80);
				tblCWT.getColumnModel().getColumn(1+1).setPreferredWidth(250);
				tblCWT.getColumnModel().getColumn(2+1).setPreferredWidth(80);

				tblCWT.getColumnModel().getColumn(5+1).setPreferredWidth(175);
				tblCWT.getColumnModel().getColumn(6+1).setPreferredWidth(150);
				tblCWT.getColumnModel().getColumn(8+1).setPreferredWidth(60);
				tblCWT.getColumnModel().getColumn(9+1).setPreferredWidth(60);
				tblCWT.getColumnModel().getColumn(10+1).setPreferredWidth(60);
				tblCWT.getColumnModel().getColumn(14+1).setPreferredWidth(100);
				tblCWT.getColumnModel().getColumn(16+1).setPreferredWidth(100);
				tblCWT.getColumnModel().getColumn(17+1).setPreferredWidth(50);
				tblCWT.getColumnModel().getColumn(18+1).setPreferredWidth(80);
				tblCWT.getColumnModel().getColumn(36+1).setPreferredWidth(250);
			}
			{
				rowHeaderCWT = tblCWT.getRowHeader();
				rowHeaderCWT.setModel(new DefaultListModel());
				scrollMainCenter.setRowHeaderView(rowHeaderCWT);
				scrollMainCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
			}
			{
				tblCWT.hideColumns("Release Batch", "Vatable", "Lot Area",
						"Discount", "com_tin1", "com_tin2", "com_tin3",
						"com_tin4", "Company", "CompanyAddress", "cl_tin1",
						"cl_tin2", "cl_tin3", "cl_tin4", "Payor", "ClientAddress",
						"Zip Code", "1st_qtr", "2nd_qtr", "3rd_qtr", "TIN");
			}
		}
		
		/*
		 * ADDED BY JARI CRUZ ASOF JAN 27 2023 DCRF#2445
		 * PROJECTED 25% UNITS
		 * */

		pnlProjected25 = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlProjected25.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlProjected25.setOpaque(isOpaque());
		{

			scrollMainCenter25 = new JScrollPane();
			pnlProjected25.add(scrollMainCenter25, BorderLayout.CENTER);
			scrollMainCenter25.setBorder(lineBorderGray);
			scrollMainCenter25.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollMainCenter25.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			{
				modelCWT25 = new Model_cwt_projected25();
				tblCWT25 = new _JTableMain(modelCWT25);
				/*
				tblCWT25.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if(!e.getValueIsAdjusting()){
							Integer intRow = tblCWT25.convertRowIndexToModel(tblCWT25.getSelectedRow());
							
							System.out.println("");
							System.out.println((String) tblCWT25.getValueAt(intRow, (2+1)).toString());
							
							if (tblCWT25.getValueAt(intRow, (2+1)).toString().equals("BOI")) {
								
								txtRPLF25.setText("Not Applicable");
								
							} else {
								
								try{
									String strRPLF = (String)tblCWT25.getValueAt(intRow, (22+1)).toString();
									if(strRPLF.equals("---")||(strRPLF.equals(""))){
										txtRPLF25.setText("Not Applicable");
									} else {
										txtRPLF25.setText("RPLF No.: " + strRPLF);
									}
								} catch(NullPointerException err) {
									txtRPLF25.setText("RPLF No.: ");
									System.out.println("");
									System.out.println("Null pointer exception caught.");
								}
								
							}
						}
					}
				});
				*/
				
				rowHeaderCWT25 = tblCWT25.getRowHeader();
				scrollMainCenter25.setViewportView(tblCWT25);

				tblCWT25.getColumnModel().getColumn(0).setPreferredWidth(50);
				tblCWT25.getColumnModel().getColumn(1).setPreferredWidth(80);
				tblCWT25.getColumnModel().getColumn(2).setPreferredWidth(250);
				tblCWT25.getColumnModel().getColumn(3).setPreferredWidth(80);

				tblCWT25.getColumnModel().getColumn(6).setPreferredWidth(175);
				tblCWT25.getColumnModel().getColumn(7).setPreferredWidth(150);
				tblCWT25.getColumnModel().getColumn(9).setPreferredWidth(60);
				tblCWT25.getColumnModel().getColumn(10).setPreferredWidth(60);
				tblCWT25.getColumnModel().getColumn(11).setPreferredWidth(60);
				tblCWT25.getColumnModel().getColumn(15).setPreferredWidth(100);
				tblCWT25.getColumnModel().getColumn(17).setPreferredWidth(100);
				tblCWT25.getColumnModel().getColumn(18).setPreferredWidth(50);
				tblCWT25.getColumnModel().getColumn(19).setPreferredWidth(80);
				tblCWT25.getColumnModel().getColumn(37).setPreferredWidth(250);
			}
			{
				rowHeaderCWT25 = tblCWT25.getRowHeader();
				rowHeaderCWT25.setModel(new DefaultListModel());
				scrollMainCenter25.setRowHeaderView(rowHeaderCWT25);
				scrollMainCenter25.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
			}
			{
				tblCWT25.hideColumns("Release Batch", "Vatable", "Lot Area",
						"Discount", "com_tin1", "com_tin2", "com_tin3",
						"com_tin4", "Company", "CompanyAddress", "cl_tin1",
						"cl_tin2", "cl_tin3", "cl_tin4", "Payor", "ClientAddress",
						"Zip Code", "1st_qtr", "2nd_qtr", "3rd_qtr", "TIN");
			}


		}
		/*END OF DCRF#2445*/

		Panel2 = new JXPanel(new GridLayout(1, 1, 0, 0));
		Panel2.setBorder(new EmptyBorder(5, 5, 5, 5));
		Panel2.setOpaque(isOpaque());
		{
			scrollMainCenter1 = new JScrollPane();
			Panel2.add(scrollMainCenter1, BorderLayout.CENTER);
			scrollMainCenter1.setBorder(lineBorderGray);
			scrollMainCenter1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollMainCenter1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			{
				modelCWTRemitted_v2 = new Model_CWTRemittedTable();
				tblCWTRemitted_v2 = new _JTableMain(modelCWTRemitted_v2);
				rowHeaderCWTRemitted_v2 = tblCWTRemitted_v2.getRowHeader();
				scrollMainCenter1.setViewportView(tblCWTRemitted_v2);

				tblCWTRemitted_v2.getColumnModel().getColumn(0).setPreferredWidth(50);
				tblCWTRemitted_v2.getColumnModel().getColumn(1).setPreferredWidth(250);
				tblCWTRemitted_v2.getColumnModel().getColumn(2).setPreferredWidth(50);
				tblCWTRemitted_v2.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblCWTRemitted_v2.getColumnModel().getColumn(4).setPreferredWidth(50);
				tblCWTRemitted_v2.getColumnModel().getColumn(5).setPreferredWidth(150);
				tblCWTRemitted_v2.getColumnModel().getColumn(6).setPreferredWidth(100);
				tblCWTRemitted_v2.getColumnModel().getColumn(7).setPreferredWidth(50);
				tblCWTRemitted_v2.getColumnModel().getColumn(8).setPreferredWidth(50);
				tblCWTRemitted_v2.getColumnModel().getColumn(9).setPreferredWidth(50);
				tblCWTRemitted_v2.getColumnModel().getColumn(10).setPreferredWidth(80);
				tblCWTRemitted_v2.getColumnModel().getColumn(11).setPreferredWidth(100);
				tblCWTRemitted_v2.getColumnModel().getColumn(12).setPreferredWidth(100);
				tblCWTRemitted_v2.getColumnModel().getColumn(13).setPreferredWidth(100);
				tblCWTRemitted_v2.getColumnModel().getColumn(14).setPreferredWidth(100);
				tblCWTRemitted_v2.getColumnModel().getColumn(19).setPreferredWidth(300);
				tblCWTRemitted_v2.getColumnModel().getColumn(22).setPreferredWidth(150);
			}
			{				
				rowHeaderCWTRemitted_v2 = tblCWTRemitted_v2.getRowHeader();
				rowHeaderCWTRemitted_v2.setModel(new DefaultListModel());
				scrollMainCenter1.setRowHeaderView(rowHeaderCWTRemitted);
				scrollMainCenter1.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}
	
	private void ComputeTaxable() {
		FncGlobal.startProgress("Computing.. Please wait...");
		System.out.println("modelCWT.getRowCount(): "+modelCWT.getRowCount());
		for (int x=0; x<modelCWT.getRowCount(); x++) {
			
			if ((Boolean) modelCWT.getValueAt(x, 4+1) && (Boolean) modelCWT.getValueAt(x, 0)) {
				System.out.println("");
				System.out.println("Taxable 1: "+(Boolean) modelCWT.getValueAt(x, 0));
				System.out.println("Taxable 2: "+(Boolean) modelCWT.getValueAt(x, 4+1));
				
				BigDecimal bd = FncGlobal.GetDecimal("select ('"+modelCWT.getValueAt(x, 16+1).toString()+"'::numeric(19, 2) * " +
						"(case when '"+modelCWT.getValueAt(x, 16+1).toString()+"'::numeric(19, 2) <= 500000::numeric(19, 2) then 0.015 \n" + 
						"when '"+modelCWT.getValueAt(x, 16+1).toString()+"'::numeric(19, 2) between 500000::numeric(19, 2) and 2000000::numeric(19, 2) then 0.03 \n" + 
						"when '"+modelCWT.getValueAt(x, 16+1).toString()+"'::numeric(19, 2) > 2000000::numeric(19, 2) then 0.05 end))::numeric(19, 2)");
				
				System.out.println("Amount: "+bd);
				
				modelCWT.setValueAt(new BigDecimal(bd.toString()) , x, 18+1);
				
				System.out.println("modelCWT Amount: "+modelCWT.getValueAt(x, 18+1));
				System.out.println("tblCWT Amount: "+tblCWT.getValueAt(x, 14+1));
			}	
		}
		FncGlobal.stopProgress();
	}
	
	private void ComputeTaxable25() {
		FncGlobal.startProgress("Computing.. Please wait...");
		System.out.println("modelCWT25.getRowCount(): "+modelCWT25.getRowCount());
		for (int x=0; x<modelCWT25.getRowCount(); x++) {
			
			if ((Boolean) modelCWT25.getValueAt(x, 5) && (Boolean) modelCWT25.getValueAt(x, 0)) {
				System.out.println("");
				System.out.println("Taxable 1: "+(Boolean) modelCWT25.getValueAt(x, 0));
				System.out.println("Taxable 2: "+(Boolean) modelCWT25.getValueAt(x, 5));
				
				BigDecimal bd = FncGlobal.GetDecimal("select ('"+modelCWT25.getValueAt(x, 17).toString()+"'::numeric(19, 2) * " +
						"(case when '"+modelCWT25.getValueAt(x, 17).toString()+"'::numeric(19, 2) <= 500000::numeric(19, 2) then 0.015 \n" + 
						"when '"+modelCWT25.getValueAt(x, 17).toString()+"'::numeric(19, 2) between 500000::numeric(19, 2) and 2000000::numeric(19, 2) then 0.03 \n" + 
						"when '"+modelCWT25.getValueAt(x, 17).toString()+"'::numeric(19, 2) > 2000000::numeric(19, 2) then 0.05 end))::numeric(19, 2)");
				
				System.out.println("Amount: "+bd);
				
				modelCWT25.setValueAt(new BigDecimal(bd.toString()) , x, 19);
				
				System.out.println("modelCWT25 Amount: "+modelCWT25.getValueAt(x, 19));
				System.out.println("tblCWT25 Amount: "+tblCWT25.getValueAt(x, 15));
			}	
		}
		FncGlobal.stopProgress();
	}
	
	private void saveBIR() {
		String projectID;
		String pblID;
		String seqno;
		FncGlobal.startProgress("Computing.. Please wait...");
		for (int x=0; x<modelCWT.getRowCount(); x++) {
			Boolean remitted = FncGlobal.GetBoolean("select exists(select * from rf_cwt_bir_details "
					+ "where entity_id = '"+modelCWT.getValueAt(x, 19)+"' and rdo = '"+CWTRemittance_BIRTagging.getRDO()+"' )");

			projectID = modelCWT.getValueAt(x, (20+1)).toString();
			pblID = modelCWT.getValueAt(x, (21+1)).toString();
			seqno = modelCWT.getValueAt(x, (22+1)).toString();

			if ((Boolean) modelCWT.getValueAt(x, 0)) {
				if(remitted) {

				} 
				else {
					pgUpdate dbExec = new pgUpdate(); 
					System.out.println("");
					System.out.println("*************************************");
					System.out.println("***				  ***");
					System.out.println("***	SAVE BIR      ***");
					System.out.println("***				  ***");
					System.out.println("*************************************");
					dbExec.executeUpdate("insert into rf_cwt_bir_details (entity_id, rdo, bank_id, bank_branch_id, status_id, created_by, date_created, or_number, proj_id, pbl_id, seq_no, amount)\n" + 
							"values ('"+modelCWT.getValueAt(x, (19+1))+"', '"+CWTRemittance_BIRTagging.getRDO()+"', '"+CWTRemittance_BIRTagging.getBankID()+"', '"+CWTRemittance_BIRTagging.getBankBranchID()+"', 'A', '"+UserInfo.EmployeeCode+"', now(), '"+modelCWT.getValueAt(x, 1)+"', '"+projectID+"', '"+pblID+"', "+seqno+", coalesce("+modelCWT.getValueAt(x, 18+1)+",0) ); ", true);
					dbExec.commit();
				}
				
			}
		}
		FncGlobal.stopProgress();
	}
	
	private void saveBIR25() {
		String projectID;
		String pblID;
		String seqno;
		FncGlobal.startProgress("Computing.. Please wait...");
		for (int x=0; x<modelCWT25.getRowCount(); x++) {
			Boolean remitted = FncGlobal.GetBoolean("select exists(select * from rf_cwt_bir_details "
					+ "where entity_id = '"+modelCWT25.getValueAt(x, 19)+"' and rdo = '"+CWTRemittance_BIRTagging.getRDO()+"' )");

			projectID = modelCWT25.getValueAt(x, (21)).toString();
			pblID = modelCWT25.getValueAt(x, (22)).toString();
			seqno = modelCWT25.getValueAt(x, (23)).toString();

			if ((Boolean) modelCWT25.getValueAt(x, 0)) {
				if(remitted) {

				} 
				else {
					pgUpdate dbExec = new pgUpdate(); 
					System.out.println("");
					System.out.println("*************************************");
					System.out.println("***				  ***");
					System.out.println("***	SAVE BIR      ***");
					System.out.println("***				  ***");
					System.out.println("*************************************");
					dbExec.executeUpdate("insert into rf_cwt_bir_details (entity_id, rdo, bank_id, bank_branch_id, status_id, created_by, date_created, or_number, proj_id, pbl_id, seq_no, amount)\n" + 
							"values ('"+modelCWT25.getValueAt(x, (20))+"', '"+CWTRemittance_BIRTagging.getRDO()+"', '"+CWTRemittance_BIRTagging.getBankID()+"', '"+CWTRemittance_BIRTagging.getBankBranchID()+"', 'A', '"+UserInfo.EmployeeCode+"', now(), '"+modelCWT25.getValueAt(x, 1)+"', '"+projectID+"', '"+pblID+"', "+seqno+", coalesce("+modelCWT25.getValueAt(x, 19)+",0) ); ", true);
					dbExec.commit();
				}
				
			}
		}
		FncGlobal.stopProgress();
	}
	
	public void GenReportRemitted() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		int opt = JOptionPane.showOptionDialog(null, "Select date option", "Date", 
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
				new Object[] {"Actual Date", "Trans. Date"}, JOptionPane.YES_OPTION);
		
		mapParameters.put("co_id", txtCoID.getValue());
		mapParameters.put("proj_id", txtProjectID.getValue());
		mapParameters.put("date_opt", opt);
		mapParameters.put("date_from", dteFrom.getDate());
		mapParameters.put("date_to", dteTo.getDate());

		FncReport.generateReport("/Reports/rptCWTRemittanceMonitoring.jasper", "Creditable Withholding Tax", "", mapParameters);
	};
	
	private String chechIfOtherLot(String strID, String strProject, String strUnit, String strSeq) {
		String SQL = "select pbl_id from hs_sold_other_lots "
				+ "where entity_id = '"+strID+"' "
				+ "and seq_no = "+strSeq+" "
				+ "and proj_id = '"+strProject+"' "
				+ "and oth_pbl_id = '"+strUnit+"' "
				+ "and status_id = 'A'";
		pgSelect db = new pgSelect();
		db.select(SQL);
		try {
			return (String) db.getResult()[0][0];
		}catch (Exception e) {
			return null;
		}
	}
	
	private BigDecimal cwt_amt_remitted(String strID, String strProject, String strUnit, String strSeq) {
		String SQL = "select cwt_amt_remitted from rf_sold_unit "
				+ "where entity_id = '"+strID+"' "
				+ "and seq_no = "+strSeq+" "
				+ "and projcode = '"+strProject+"' "
				+ "and pbl_id = '"+strUnit+"' "
				+ "and status_id = 'A'";
		pgSelect db = new pgSelect();
		db.select(SQL);
		return (BigDecimal) db.getResult()[0][0];
	}
	
	private int selected_count() {
		int count = 0;
		for (int i = 0; i < modelCWT.getRowCount(); i++) {
			boolean tag = (boolean) modelCWT.getValueAt(i, 0);
			if(tag) {
				count += 1;
			}
		}
		return count;
	}
	
	private int selected_count_25() {
		int count = 0;
		for (int i = 0; i < modelCWT25.getRowCount(); i++) {
			boolean tag = (boolean) modelCWT25.getValueAt(i, 0);
			if(tag) {
				count += 1;
			}
		}
		return count;
	}
	
	private JPanel selected_clients() {
		JPanel panSelectedClients = new JPanel(new GridLayout(selected_count(),1,5,5));
		for(int w = 0; w < tblCWT.getRowCount(); w++){
			if((Boolean)tblCWT.getValueAt(w,0).equals(true)){
				String clientName = (String) tblCWT.getValueAt(w, 2);
				String clientPhase = (String) tblCWT.getValueAt(w, 8);
				System.out.println("clientPhase.equals(): "+clientPhase.equals(""));
				System.out.println("txtCoID.equals(): "+txtCoID.getValue().equals("02"));
				if(clientPhase.equals("") && txtCoID.getValue().equals("02")) {
					clientPhase = "EVE";
				}
				String clientBlock = (String) tblCWT.getValueAt(w, 9);
				String clientLot = (String) tblCWT.getValueAt(w, 10);
				JLabel lblClient = new JLabel(""+clientName+" [PH "+clientPhase+" BK "+clientBlock+" LT "+clientLot+"]");
				lblClient.setForeground(Color.BLACK);
				panSelectedClients.add(lblClient);
			}				
		}
		
		return panSelectedClients;
	}
	
	private JPanel selected_clients_25() {
		JPanel panSelectedClients = new JPanel(new GridLayout(selected_count_25(),1,5,5));
		for(int w = 0; w < tblCWT25.getRowCount(); w++){
			if((Boolean)tblCWT25.getValueAt(w,0).equals(true)){
				String clientName = (String) tblCWT25.getValueAt(w, 2);
				String clientPhase = (String) tblCWT25.getValueAt(w, 8);
				if(clientPhase.equals("") && txtCoID.equals("02")) {
					clientPhase = "EVE";
				}
				String clientBlock = (String) tblCWT25.getValueAt(w, 9);
				String clientLot = (String) tblCWT25.getValueAt(w, 10);
				JLabel lblClient = new JLabel(""+clientName+" [PH "+clientPhase+" BK "+clientBlock+" LT "+clientLot+"]");
				lblClient.setForeground(Color.BLACK);
				panSelectedClients.add(lblClient);
			}				
		}
		
		return panSelectedClients;
	}
}







