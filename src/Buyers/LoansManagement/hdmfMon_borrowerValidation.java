package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import interfaces._GUI;

import org.jdesktop.swingx.JXPanel;

import tablemodel.model_hdmf_borrower_val;
import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
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
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;

public class hdmfMon_borrowerValidation extends JXPanel implements _GUI {
	private static final long serialVersionUID = -9152004394053447026L;
	private PagibigStatusMonitoring_v2 hdmfMainMod;
	static Dimension size = new Dimension(738, 351);
	
	private JLabel lblFrom;
	private JLabel lblBat;
	
	private _JDateChooser dteFrom;
	private _JLookup txtBat;
	
	public static JList rowBorVal;
	private _JTableMain tblBorVal;
	private JScrollPane scrollBorVal;
	private model_hdmf_borrower_val modelBorVal;
	
	private JXPanel panGrid;
	private String strBat = "";
	
	private JCheckBox chkff;
	private JCheckBox chkall;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public hdmfMon_borrowerValidation(PagibigStatusMonitoring_v2 psm) {
		this.hdmfMainMod = psm;
		initGUI();
	}

	public hdmfMon_borrowerValidation(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public hdmfMon_borrowerValidation(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public hdmfMon_borrowerValidation(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			this.add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				JXPanel panPage = new JXPanel(new GridLayout(1, 2, 5, 5));
				panMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 60));
				{
					{
						JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
						panPage.add(panDate, BorderLayout.LINE_START);
						panDate.setPreferredSize(new Dimension(450, 0));
						panDate.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel panDate1 = new JXPanel(new BorderLayout(5, 5));
							panDate.add(panDate1, BorderLayout.CENTER);
							{
								lblFrom = new JLabel("Attended Date");
								panDate1.add(lblFrom, BorderLayout.LINE_START);
								lblFrom.setHorizontalAlignment(JTextField.LEADING);
								lblFrom.setPreferredSize(new Dimension(150, 0));
							}
							{
								dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panDate1.add(dteFrom, BorderLayout.CENTER);
								dteFrom.getCalendarButton().setVisible(true);
								dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}
						}
					}
					{
						JXPanel panBatch = new JXPanel(new BorderLayout(5, 5));
						//panPage.add(panBatch, BorderLayout.CENTER);
						panBatch.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							lblBat = new JLabel("Batch No.");
							panBatch.add(lblBat, BorderLayout.LINE_START);
							lblBat.setHorizontalAlignment(JTextField.LEADING);
							lblBat.setPreferredSize(new Dimension(150, 0));
						}
						{
							txtBat = new _JLookup();
							panBatch.add(txtBat, BorderLayout.CENTER);
							txtBat.setReturnColumn(0);
							txtBat.setLookupSQL(_PagibigStatusMonitoring.SQL_BATCH("Validation"));
							txtBat.setHorizontalAlignment(JTextField.CENTER);
							txtBat.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data!=null) {
										DisplayBorVal();
										hdmfMainMod.CtrlLock_0(3);
										hdmfMainMod.intBorVal=3;
									}
								}
							});
						}
					}
					{
						JXPanel panCheck = new JXPanel(new BorderLayout(5, 5));
						panPage.add(panCheck, BorderLayout.CENTER);
						panCheck.setBorder(JTBorderFactory.createTitleBorder("Print/View Options", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								chkall = new JCheckBox("Select All");
								panCheck.add(chkall, BorderLayout.LINE_START);
								chkall.setHorizontalAlignment(JTextField.TRAILING);
								chkall.setSelected(false);
								chkall.setPreferredSize(new Dimension(100, 0));
								chkall.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										if (chkall.isSelected()) {
											for (int x = 0; x < modelBorVal.getRowCount(); x++) {
												modelBorVal.setValueAt(true, x, 1);
											}
										} else {
											for (int x = 0; x < modelBorVal.getRowCount(); x++) {
												modelBorVal.setValueAt(false, x, 1);
											}											
										}
									}
								});
							}
							{
								chkff = new JCheckBox("Display Loan Filed Accounts");
								panCheck.add(chkff, BorderLayout.CENTER);
								chkff.setHorizontalAlignment(JTextField.TRAILING);
								chkff.setSelected(false);
								chkff.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										Generate();
									}
								});
							}
						}
					}
				}
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					CreateBorrowerValidationGrid();
					panCenter.add(panGrid, BorderLayout.CENTER);
				}
			}
		}
	}
	
	private void CreateBorrowerValidationGrid() {
		panGrid = new JXPanel(new GridLayout(1, 1, 0, 0));
		panGrid.setOpaque(isOpaque());
		{
			scrollBorVal = new JScrollPane();
			panGrid.add(scrollBorVal, BorderLayout.CENTER);
			scrollBorVal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelBorVal = new model_hdmf_borrower_val();
				modelBorVal.setEditable(false);
				
				tblBorVal = new _JTableMain(modelBorVal);
				tblBorVal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblBorVal.getSelectedRow());
						}
					}
				});
				
				rowBorVal = tblBorVal.getRowHeader();
				scrollBorVal.setViewportView(tblBorVal);
				
				tblBorVal.getColumnModel().getColumn(0).setPreferredWidth(254);
				tblBorVal.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblBorVal.getColumnModel().getColumn(2).setPreferredWidth(150);
				tblBorVal.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblBorVal.getColumnModel().getColumn(4).setPreferredWidth(50);
				tblBorVal.getColumnModel().getColumn(5).setPreferredWidth(50);
				tblBorVal.getColumnModel().getColumn(6).setPreferredWidth(150);
				tblBorVal.getColumnModel().getColumn(7).setPreferredWidth(85);
				tblBorVal.getColumnModel().getColumn(8).setPreferredWidth(85);
				tblBorVal.getColumnModel().getColumn(9).setPreferredWidth(85);
				
				tblBorVal.getColumnModel().getColumn(7).setCellRenderer(new DateRenderer(sdf));
				tblBorVal.getColumnModel().getColumn(8).setCellRenderer(new DateRenderer(sdf));
				tblBorVal.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf));

				tblBorVal.hideColumns("Date", "entity_id", "proj_id", "pbl_id", "seq_no", "Docs Complete");
				tblBorVal.getTableHeader().setEnabled(false);
				
				rowBorVal = tblBorVal.getRowHeader();
				rowBorVal.setModel(new DefaultListModel());
				scrollBorVal.setRowHeaderView(rowBorVal);
				scrollBorVal.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}
	
	private void DisplayBorVal() {
		//String SQL = "select * from view_hdmf_bor_validation('"+txtBat.getText()+"');";
		Boolean blnFiled = false;
		
		if (chkff.isSelected()) {
			blnFiled = true;
		} else {
			blnFiled = false;
		}
		/*MODIFIED:ALMAR 08-24-23 YASMELYN REQUEST FILTER FOR COMPANY/PROJECT/PHASE*/
		//String SQL = "select * from view_hdmf_bor_validation('"+txtBat.getText()+"', "+blnFiled+");";
		String SQL = "select * from view_hdmf_bor_validation_v2('"+txtBat.getText()+"', "+blnFiled+", '"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		FncTables.clearTable(modelBorVal);
		DefaultListModel listModel = new DefaultListModel();
		rowBorVal.setModel(listModel); 
		
		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelBorVal.addRow(db.getResult()[x]);
				listModel.addElement(modelBorVal.getRowCount());
			}
		}
	}
	
	public void Generate() {
		new Thread(new Runnable() {
			public void run() {
				strBat = "";
				String company_logo = RequestForPayment.sql_getCompanyLogo();
				System.out.println(this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				
				FncGlobal.startProgress("Generating List of Accounts");
				hdmfMainMod.CtrlLock_1(4);
				txtBat.setText("");
				DisplayBorVal();
				txtBat.setEnabled(true);
				hdmfMainMod.CtrlLock_0(2);
				hdmfMainMod.intBorVal = 2;
				FncGlobal.stopProgress();
			}
		}).start();
	}
	
	public void Post() {
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdfTo.format(dteFrom.getDate());
		
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed? \n" + 
				"If so, " + strDate + " will be set as date attended. Continue?", 
				"Confirmation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			
			Boolean blnChk = false;
			strBat = hdmfMainMod.GetBatch("Validation");
			txtBat.setValue(strBat);
			
			System.out.println("");
			System.out.println("strBat: " + strBat);
			
			for (int x = 0; x < modelBorVal.getRowCount(); x++) {
				if ((Boolean) modelBorVal.getValueAt(x, 1)) {
					blnChk = true;
					String strEntID = (String) modelBorVal.getValueAt(x, 10).toString();
					String strProID = (String) modelBorVal.getValueAt(x, 11).toString();
					String strUnitID = (String) modelBorVal.getValueAt(x, 12).toString();
					String strSeq = (String) modelBorVal.getValueAt(x, 13).toString();
					String strStatus = "107";
					String strUser = UserInfo.EmployeeCode;
					
					pgUpdate dbBor = new pgUpdate();
					String strSQL = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
							"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+strStatus+"', '"+strDate+"', '"+strDate+"', 'A', '"+strBat+"', '"+strUser+"');";
					System.out.println("");
					System.out.println(strSQL);
					
					dbBor.executeUpdate(strSQL, false);
					dbBor.commit();
				}
			}
			
			if (!blnChk) {
				JOptionPane.showMessageDialog(null, "No row was selected.");
			} else {
				DisplayBorVal();
				hdmfMainMod.CtrlLock_0(3);
				hdmfMainMod.intBorVal = 3;
				txtBat.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Attendees are successfully tagged.");
			}
		}
	}
	
	public void Preview() {
		pgUpdate db = new pgUpdate();
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		Date dateObj = new Date();
		String strDate = (String) sdfTo.format(dateObj);
		
		strDate = "As Of: " + strDate;
		
		String strBatchLabel = "";
		if (strBat=="") {
			strBatchLabel = "";
		} else {
			strBatchLabel = "Batch No. " + strBat;
		}
		
		db = new pgUpdate();
		db.executeUpdate("delete from tmp_hdmf where emp_code = 'borval'", false);
		db.commit();
		
		for (int x = 0; x < modelBorVal.getRowCount(); x++) {
			if ((Boolean) modelBorVal.getValueAt(x, 1)) {
				db = new pgUpdate();
				db.executeUpdate("insert into tmp_hdmf (client_name, emp_code) values ('"+modelBorVal.getValueAt(x, 0)+"', 'borval')", false);
				db.commit();	
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
		mapParameters.put("02_AsOfDate", strDate);
		mapParameters.put("03_CoID", hdmfMainMod.txtCoID.getValue());
		mapParameters.put("04_ProID", hdmfMainMod.txtProID.getValue());
		mapParameters.put("06_Project", hdmfMainMod.txtPro.getText());
		mapParameters.put("07_User",  _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("09_Batch", strBat);
		mapParameters.put("10_filed", chkff.isSelected());
		mapParameters.put("11_BatchParam", strBatchLabel);
		FncReport.generateReport("/Reports/rpt_hdmf_borrower_validation.jasper", "Borrower's Validation", "", mapParameters);
	}
	
	public void Export() {
		String col_names [] = {"Name", "Phase", "Block", "Lot", "Date Attended", "Loan Filed", "NOA Released", "NOA Signed"};
		String strSQL = "select name, phase, block, lot, date, loanfiled, noareleased, noasigned from view_hdmf_bor_validation('"+strBat+"');";
		
		FncGlobal.startProgress("Creating spreadsheet.");
		FncGlobal.CreateXLS(col_names, strSQL, "Borrower's Validation");
		FncGlobal.stopProgress();
	}
}
