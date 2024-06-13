package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import interfaces._GUI;

import org.jdesktop.swingx.JXPanel;

import com.linuxense.javadbf.DBFException;

import tablemodel.modelPagibigStatusMonitoring_MSVSMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncExport;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JTableMain;

public class hdmfMon_msvsMonitoring extends JXPanel implements _GUI {

	private static final long serialVersionUID = 5475910247779052434L;
	private static PagibigStatusMonitoring_v2 hdmfMainMod;
	static Dimension size = new Dimension(738, 351);

	private static _JDateChooser dteTo;
	
	private static JComboBox cmbStage;
	private static JComboBox cmbCircular;
	
	private static JXPanel panGrid;
	public static JList rowMSVS;
	private static _JTableMain tblMSVS;
	private static JScrollPane scrollMSVS;
	private static modelPagibigStatusMonitoring_MSVSMonitoring modelMSVS;
	
	public static _JLookup txtBatch; 
	
	private static Boolean blnTag = false;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	
	public hdmfMon_msvsMonitoring(PagibigStatusMonitoring_v2 psm) {
		this.hdmfMainMod = psm;
		initGUI();
	}

	public hdmfMon_msvsMonitoring(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public hdmfMon_msvsMonitoring(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public hdmfMon_msvsMonitoring(LayoutManager layout, boolean isDoubleBuffered) {
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
				JXPanel panPage = new JXPanel(new GridLayout(1, 4, 5, 5));
				panMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 60));
				{
					{
						JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5));
						panPage.add(panDate, BorderLayout.LINE_START);
						panDate.setBorder(JTBorderFactory.createTitleBorder("Date Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							panDate.add(dteTo, BorderLayout.CENTER);
							dteTo.getCalendarButton().setVisible(true);
							dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteTo.setEnabled(false);
						}	
					}
					{
						JXPanel panStage = new JXPanel(new BorderLayout(5, 5));
						panPage.add(panStage, BorderLayout.CENTER);
						panStage.setBorder(JTBorderFactory.createTitleBorder("Select Stage", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							String[] stage = {
								"MSVS for Verification", 
								"MSVS for Reverification", 
								"MSVS for HL Verification"
							};
							
							cmbStage = new JComboBox(stage);
							panStage.add(cmbStage, BorderLayout.CENTER);
							cmbStage.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent arg0) {
									txtBatch.setLookupSQL(lookupMethods.getMSVSBatch(cmbStage.getSelectedItem().toString()));
								}
							});
						}	
					}
					{
						JXPanel panCircular = new JXPanel(new GridLayout(1, 2, 5, 5));
						panPage.add(panCircular, BorderLayout.LINE_END);
						panCircular.setBorder(JTBorderFactory.createTitleBorder("Circular", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							String [] strCirc = {"310/349", "379"};
							cmbCircular = new JComboBox(strCirc);
							panCircular.add(cmbCircular, BorderLayout.CENTER);
						}	
					}
					{
						JXPanel panBatch = new JXPanel(new GridLayout(1, 2, 5, 5));
						panPage.add(panBatch, BorderLayout.LINE_END);
						panBatch.setBorder(JTBorderFactory.createTitleBorder("Date Evaluated", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtBatch = new _JLookup(""); 
							panBatch.add(txtBatch, BorderLayout.CENTER); 
							txtBatch.setReturnColumn(0);
							txtBatch.setLookupSQL(lookupMethods.getMSVSBatch(""));
							txtBatch.setEnabled(true);
							txtBatch.setHorizontalAlignment(_JLookup.CENTER);
							txtBatch.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Generate();
								}
							});
						}	
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					CreateMSVSGrid();
					panCenter.add(panGrid, BorderLayout.CENTER);
				}	
			}
		}
	}
	
	private void CreateMSVSGrid() {
		panGrid = new JXPanel(new GridLayout(1, 1, 0, 0));
		panGrid.setOpaque(isOpaque());
		{
			scrollMSVS = new JScrollPane();
			panGrid.add(scrollMSVS, BorderLayout.CENTER);
			{
				modelMSVS = new modelPagibigStatusMonitoring_MSVSMonitoring();
				tblMSVS = new _JTableMain(modelMSVS);
				scrollMSVS.setViewportView(tblMSVS);
				scrollMSVS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				
				tblMSVS.getColumnModel().getColumn(0).setPreferredWidth(25);
				tblMSVS.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblMSVS.getColumnModel().getColumn(2).setPreferredWidth(50);
				tblMSVS.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblMSVS.getColumnModel().getColumn(4).setPreferredWidth(100);
				tblMSVS.getColumnModel().getColumn(5).setPreferredWidth(125);
				tblMSVS.getColumnModel().getColumn(6).setPreferredWidth(125);
				tblMSVS.getColumnModel().getColumn(7).setPreferredWidth(125);
				tblMSVS.getColumnModel().getColumn(8).setPreferredWidth(125);		/***	EXT				***/
				tblMSVS.getColumnModel().getColumn(9).setPreferredWidth(125);		/***	Maiden			***/
				tblMSVS.getColumnModel().getColumn(10).setPreferredWidth(125);		/***	Loanable		***/
				tblMSVS.getColumnModel().getColumn(11).setPreferredWidth(100);
				tblMSVS.getColumnModel().getColumn(12).setPreferredWidth(100);
				tblMSVS.getColumnModel().getColumn(13).setPreferredWidth(75);
				tblMSVS.getColumnModel().getColumn(14).setPreferredWidth(100);		/***	NTC				***/
				tblMSVS.getColumnModel().getColumn(15).setPreferredWidth(100);		/***	NTP				***/
				tblMSVS.getColumnModel().getColumn(16).setPreferredWidth(100);		/***	Compliance		***/
				tblMSVS.getColumnModel().getColumn(17).setPreferredWidth(900);		/***	Findings		***/
				
				tblMSVS.hideColumns("entity_id", "projcode", "pbl_id", "seq_no", "Findings");
				tblMSVS.getTableHeader().setEnabled(false);
			}
			{
				rowMSVS = tblMSVS.getRowHeader();
				rowMSVS.setModel(new DefaultListModel());
				scrollMSVS.setRowHeaderView(rowMSVS);
				scrollMSVS.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}
	
	private void Execute(String entity_id, String proj_id, String pbl_id, Integer seq_no, Integer i){
		Object[] client_dtl = getClientDetails(entity_id, proj_id, pbl_id, seq_no);	
		pgUpdate db = new pgUpdate();
		
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdfTo.format(dteTo.getDate());
		
		String sql = "";
		String strFin = "";
		
		System.out.println("");
		System.out.println("Selected Index: " + i.toString());
		
		try {
			if (i==0) {
				strFin = "MSVS for verification at HDMF";
				sql = "insert into dm_gen_findings (entity_id, proj_id, pbl_id, seq_no, date_eval, eval_by, gen_findings, status_id, created_by) \n" +
					"values ('"+(String) client_dtl[0]+"', '"+(String) client_dtl[1]+"', '"+pbl_id+"', "+seq_no.toString()+", '"+strDate+"'::date, \n" +
					"'"+UserInfo.Alias+"', '"+strFin+"', 'A', '"+UserInfo.EmployeeCode+"');";
			} else if (i==1) {
				strFin = "MSVS for reverification at HDMF";
				sql = "insert into dm_gen_findings (entity_id, proj_id, pbl_id, seq_no, date_eval, eval_by, gen_findings, status_id, created_by) \n" +
					"values ('"+(String) client_dtl[0]+"', '"+(String) client_dtl[1]+"', '"+pbl_id+"', "+seq_no.toString()+", '"+strDate+"'::date, \n" +
					"'"+UserInfo.Alias+"', '"+strFin+"', 'A', '"+UserInfo.EmployeeCode+"');";
			} else if (i==2) {
				strFin = "MSVS for HL verification at HDMF";
				sql = "insert into dm_gen_findings (entity_id, proj_id, pbl_id, seq_no, date_eval, eval_by, gen_findings, status_id, created_by) \n" +
					"values ('"+(String) client_dtl[0]+"', '"+(String) client_dtl[1]+"', '"+pbl_id+"', "+seq_no.toString()+", '"+strDate+"'::date, \n" +
					"'"+UserInfo.Alias+"', '"+strFin+"', 'A', '"+UserInfo.EmployeeCode+"');";
			} 
			
			db.executeUpdate(sql, false);
			db.commit();
		} catch (NullPointerException ex) {
			
		}
		
		System.out.println("");
		System.out.println("sql: " + sql);
	}
	
	public static Object [] getClientDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String strSQL = "select entity_id, projcode from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and server_id is null " ;

		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}
	
	public void DisplayPosted() {
		new Thread(new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating preview");
				
				FncTables.clearTable(modelMSVS);		
				DefaultListModel listModel = new DefaultListModel();
				rowMSVS.setModel(listModel);
				
				String sql = "";
				String strDate = "";
				
				SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
				strDate = (String) sdfTo.format(dteTo.getDate());
				
				if (cmbStage.getSelectedIndex()==0) {
					sql = "select * from view_hdmf_msvs_verification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
				} else if (cmbStage.getSelectedIndex()==1) {
					sql = "select * from view_hdmf_msvs_reverification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
				} else if (cmbStage.getSelectedIndex()==2) {
					sql = "select * from view_hdmf_msvs_hlverification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
				}
				/*
				else if (cmbStage.getSelectedIndex()==3) {
					sql = "select * from view_hdmf_msvs_reverified('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
				}
				*/
				
				System.out.println("");
				System.out.println("SQL: " + sql);
				pgSelect db = new pgSelect();
				db.select(sql);

				if (db.isNotNull()) {
					for (int x=0; x < db.getRowCount(); x++) {
						modelMSVS.addRow(db.getResult()[x]);
						listModel.addElement(modelMSVS.getRowCount());		
					}
				}
				FncGlobal.stopProgress();
			}
		}).run();
	}

	public void Post() {
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strTo = (String) sdfTo.format(dteTo.getDate());
		
		if (JOptionPane.showConfirmDialog(null, strTo + " will be used as the transaction date." + "\nAre you sure you want to tag selected client(s)?", "Confirm", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			Integer x = 0;
			String pbl_id = "";
			String entity_id = "";
			String proj_id = "";
			Integer seq_no= null;
			Integer row = modelMSVS.getRowCount();

			while (x < row) {
				Boolean isTrue = false;
				if (modelMSVS.getValueAt(x, 0) instanceof String) {
					isTrue = new Boolean((String) modelMSVS.getValueAt(x,0));
				}
				
				if (modelMSVS.getValueAt(x, 0) instanceof Boolean) {
					isTrue = (Boolean) modelMSVS.getValueAt(x, 0);
				}				

				if (isTrue) {				
					try {
						entity_id = modelMSVS.getValueAt(x, 18).toString().trim();
					} catch (NullPointerException e) {
						entity_id = "";
					}	
					
					try {
						proj_id = modelMSVS.getValueAt(x, 19).toString().trim();
					} catch (NullPointerException e) {
						proj_id = "";
					}
					
					try {
						pbl_id = modelMSVS.getValueAt(x, 20).toString().trim();
					} catch (NullPointerException e) {
						pbl_id = "";
					}
					
					try {
						seq_no = Integer.parseInt(modelMSVS.getValueAt(x, 21).toString().trim());
					} catch (NullPointerException e) {
						seq_no = null;
					}
					Execute(entity_id, proj_id, pbl_id, seq_no, cmbStage.getSelectedIndex());
				}	
				x++;
			}
			
			if (cmbStage.getSelectedIndex()==0 || cmbStage.getSelectedIndex()==1 || cmbStage.getSelectedIndex()==2) {
				hdmfMainMod.CtrlLock_1(3);
				hdmfMainMod.intMSVS = 3;				
			} else {
				hdmfMainMod.CtrlLock_1(4);
				hdmfMainMod.intMSVS = 4;				
			}
			
			blnTag = true;
			//DisplayPosted();
			_PagibigStatusMonitoring.DeleteRows(modelMSVS, 0);
			JOptionPane.showMessageDialog(null,"Client(s) tagged.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public void Preview() {
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strTo = (String) sdfTo.format(dteTo.getDate());
		String strTmp = "";
		
		pgUpdate del = new pgUpdate();
		strTmp = "delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'";
		del.executeUpdate(strTmp, false);
		del.commit();
		
		System.out.println("");
		System.out.println(strTmp);
		
		for (int x = 0; x < modelMSVS.getRowCount(); x++) {
			pgUpdate ins = new pgUpdate();
			String strName = "";
			
			strName = modelMSVS.getValueAt(x, 5).toString() + ", " + modelMSVS.getValueAt(x, 6).toString() + " " + modelMSVS.getValueAt(x, 7).toString();
			strName = strName.trim();
			
			strTmp = "insert into tmp_hdmf (client_name, emp_code) values ('"+strName+"', '"+UserInfo.EmployeeCode+"')";
			ins.executeUpdate(strTmp, false);
			ins.commit();
		}
		
		if (JOptionPane.showConfirmDialog(null, "Create DEV entry?", "MSVS", 
				JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
			CreateDevEntry();
		}
		
		if (JOptionPane.showConfirmDialog(null, "Generate MSVS Slip?", "MSVS", 
				JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
			Map<String, Object> mapParameters_slip = new HashMap<String, Object>();
			mapParameters_slip.put("emp_code",  UserInfo.EmployeeCode);
			mapParameters_slip.put("co_id", hdmfMainMod.txtCoID.getText());
			mapParameters_slip.put("logo",  this.getClass().getClassLoader().getResourceAsStream("Images/" + "pagibiglogo.jpg"));
			System.out.println("co_id" + hdmfMainMod.txtCoID.getText());
			FncReport.generateReport("/Reports/rpt_hdmf_msvs_slip_v2.jasper", "MSVS Slip", "", mapParameters_slip);
		} 
		
		FncGlobal.startProgress("MSVS");
		
		if (!txtBatch.getValue().equals("")) {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
			mapParameters.put("02_AsOfDate", sdf.format(new Date()));
			mapParameters.put("03_User", UserInfo.FullName);
			mapParameters.put("04_UserID", UserInfo.EmployeeCode);
			System.out.println("comppp:" + hdmfMainMod.txtCo.getText());
			System.out.println("pasokkkk 1" );

			FncReport.generateReport("/Reports/rpt_hdmf_msvs_ci.jasper", "", "MSVS", mapParameters);
			
			Integer intCount = FncGlobal.GetInteger("select count(*)::int \n" + 
					"from\n" + 
					"(\n" + 
					"select CONCAT('CEN', LPAD(date_part('month', now())::text, 2, '0'::text), LPAD(date_part('day', now())::text, 2, '0'::text), 'E') as batch, ROW_NUMBER() OVER(order by b.last_name, b.first_name, b.middle_name)::int as row, b.last_name, b.first_name, b.middle_name,\n" + 
					"(select y.entity_name from em_employee x inner join rf_entity y on x.entity_id = y.entity_id where x.emp_code = '"+UserInfo.EmployeeCode+"') as emp_name, a.*, now()::date asofdate\n" + 
					"from view_hdmf_ci_msvs('', '', '', true, '"+UserInfo.EmployeeCode+"') a\n" + 
					"inner join rf_entity b on a.c_entity_id = b.entity_id\n" + 
					"order by b.last_name, b.first_name, b.middle_name\n" + 
					") a");
			
			FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_a.jasper", "Advanced Eligibility Check 1", "", mapParameters);

			if (intCount>10) {
				FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_b.jasper", "Advanced Eligibility Check 2", "", mapParameters);
			}
			
			if (intCount>20) {
				FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_c.jasper", "Advanced Eligibility Check 3", "", mapParameters);
			}
			
			if (intCount>30) {
				FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_d.jasper", "Advanced Eligibility Check 4", "", mapParameters);
			}
			
			if (intCount>40) {
				FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_e.jasper", "Advanced Eligibility Check 5", "", mapParameters);
			}
		} else {
			if (cmbStage.getSelectedIndex()==0) {
				if (blnTag) {
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
					mapParameters.put("02_AsOfDate", sdf.format(new Date()));
					mapParameters.put("03_User", UserInfo.FullName);
					mapParameters.put("04_UserID", UserInfo.EmployeeCode);
					System.out.println("comppp:" + hdmfMainMod.txtCo.getText());
					System.out.println("pasokkkk 2" );
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_ci.jasper", "", "MSVS", mapParameters);
					
					Integer intCount = FncGlobal.GetInteger("select count(*)::int \n" + 
							"from\n" + 
							"(\n" + 
							"select CONCAT('CEN', LPAD(date_part('month', now())::text, 2, '0'::text), LPAD(date_part('day', now())::text, 2, '0'::text), 'E') as batch, ROW_NUMBER() OVER(order by b.last_name, b.first_name, b.middle_name)::int as row, b.last_name, b.first_name, b.middle_name,\n" + 
							"(select y.entity_name from em_employee x inner join rf_entity y on x.entity_id = y.entity_id where x.emp_code = '"+UserInfo.EmployeeCode+"') as emp_name, a.*, now()::date asofdate\n" + 
							"from view_hdmf_ci_msvs('', '', '', true, '"+UserInfo.EmployeeCode+"') a\n" + 
							"inner join rf_entity b on a.c_entity_id = b.entity_id\n" + 
							"order by b.last_name, b.first_name, b.middle_name\n" + 
							") a");
					
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_a.jasper", "Advanced Eligibility Check 1", "", mapParameters);

					if (intCount>10) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_b.jasper", "Advanced Eligibility Check 2", "", mapParameters);
					}
					
					if (intCount>20) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_c.jasper", "Advanced Eligibility Check 3", "", mapParameters);
					}
					
					if (intCount>30) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_d.jasper", "Advanced Eligibility Check 4", "", mapParameters);
					}
					
					if (intCount>40) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_e.jasper", "Advanced Eligibility Check 5", "", mapParameters);
					}
				} else {
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
					mapParameters.put("02_AsOfDate", strTo);
					mapParameters.put("03_CoID", hdmfMainMod.txtCoID.getText());
					mapParameters.put("04_ProID", hdmfMainMod.txtProID.getText());
					mapParameters.put("06_Project", hdmfMainMod.txtPro.getText());
					mapParameters.put("07_User",  _RealTimeDebit.GetName(UserInfo.EmployeeCode));
					mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
					mapParameters.put("09_Phase", hdmfMainMod.txtPhaseID.getText());
					mapParameters.put("11_To", dteTo.getDate());
					mapParameters.put("12_Boolean", blnTag);
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_status_verification.jasper", "Status of MSVS Reverification", "", mapParameters);
				}
			} else if (cmbStage.getSelectedIndex()==1) {
				if (blnTag) {
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
					mapParameters.put("02_AsOfDate", sdf.format(new Date()));
					mapParameters.put("03_User", UserInfo.FullName);
					mapParameters.put("04_UserID", UserInfo.EmployeeCode);
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_ci.jasper", "", "MSVS", mapParameters);
					System.out.println("pasokkkk 3" );
					Integer intCount = FncGlobal.GetInteger("select count(*)::int \n" + 
							"from\n" + 
							"(\n" + 
							"select CONCAT('CEN', LPAD(date_part('month', now())::text, 2, '0'::text), LPAD(date_part('day', now())::text, 2, '0'::text), 'E') as batch, ROW_NUMBER() OVER(order by b.last_name, b.first_name, b.middle_name)::int as row, b.last_name, b.first_name, b.middle_name,\n" + 
							"(select y.entity_name from em_employee x inner join rf_entity y on x.entity_id = y.entity_id where x.emp_code = '"+UserInfo.EmployeeCode+"') as emp_name, a.*, now()::date asofdate\n" + 
							"from view_hdmf_ci_msvs('', '', '', true, '"+UserInfo.EmployeeCode+"') a\n" + 
							"inner join rf_entity b on a.c_entity_id = b.entity_id\n" + 
							"order by b.last_name, b.first_name, b.middle_name\n" + 
							") a");
					
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_a.jasper", "Advanced Eligibility Check 1", "", mapParameters);

					if (intCount>10) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_b.jasper", "Advanced Eligibility Check 2", "", mapParameters);
					}
					
					if (intCount>20) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_c.jasper", "Advanced Eligibility Check 3", "", mapParameters);
					}
					
					if (intCount>30) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_d.jasper", "Advanced Eligibility Check 4", "", mapParameters);
					}
					
					if (intCount>40) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_e.jasper", "Advanced Eligibility Check 5", "", mapParameters);
					}	
				} else {
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
					mapParameters.put("02_AsOfDate", strTo);
					mapParameters.put("03_CoID", hdmfMainMod.txtCoID.getText());
					mapParameters.put("04_ProID", hdmfMainMod.txtProID.getText());
					mapParameters.put("06_Project", hdmfMainMod.txtPro.getText());
					mapParameters.put("07_User",  _RealTimeDebit.GetName(UserInfo.EmployeeCode));
					mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
					mapParameters.put("09_Phase", hdmfMainMod.txtPhaseID.getText());
					mapParameters.put("11_To", dteTo.getDate());
					mapParameters.put("12_Boolean", blnTag);
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_status_reverification.jasper", "Status of MSVS Reverification", "", mapParameters);	
				}
			} else if (cmbStage.getSelectedIndex()==2) {
				if (blnTag) {
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
					mapParameters.put("02_AsOfDate", sdf.format(new Date()));
					mapParameters.put("03_User", UserInfo.FullName);
					mapParameters.put("04_UserID", UserInfo.EmployeeCode);
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_ci.jasper", "", "MSVS", mapParameters);
					System.out.println("pasokkkk 4" );
					Integer intCount = FncGlobal.GetInteger("select count(*)::int \n" + 
							"from\n" + 
							"(\n" + 
							"select CONCAT('CEN', LPAD(date_part('month', now())::text, 2, '0'::text), LPAD(date_part('day', now())::text, 2, '0'::text), 'E') as batch, ROW_NUMBER() OVER(order by b.last_name, b.first_name, b.middle_name)::int as row, b.last_name, b.first_name, b.middle_name,\n" + 
							"(select y.entity_name from em_employee x inner join rf_entity y on x.entity_id = y.entity_id where x.emp_code = '"+UserInfo.EmployeeCode+"') as emp_name, a.*, now()::date asofdate\n" + 
							"from view_hdmf_ci_msvs('', '', '', true, '"+UserInfo.EmployeeCode+"') a\n" + 
							"inner join rf_entity b on a.c_entity_id = b.entity_id\n" + 
							"order by b.last_name, b.first_name, b.middle_name\n" + 
							") a");
					
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_a.jasper", "Advanced Eligibility Check 1", "", mapParameters);

					if (intCount>10) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_b.jasper", "Advanced Eligibility Check 2", "", mapParameters);
					}
					
					if (intCount>20) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_c.jasper", "Advanced Eligibility Check 3", "", mapParameters);
					}
					
					if (intCount>30) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_d.jasper", "Advanced Eligibility Check 4", "", mapParameters);
					}
					
					if (intCount>40) {
						FncReport.generateReport("/Reports/rpt_hdmf_msvs_aec_e.jasper", "Advanced Eligibility Check 5", "", mapParameters);
					}	
				} else {
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
					mapParameters.put("02_AsOfDate", strTo);
					mapParameters.put("03_CoID", hdmfMainMod.txtCoID.getText());
					mapParameters.put("04_ProID", hdmfMainMod.txtProID.getText());
					mapParameters.put("06_Project", hdmfMainMod.txtPro.getText());
					mapParameters.put("07_User",  _RealTimeDebit.GetName(UserInfo.EmployeeCode));
					mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
					mapParameters.put("09_Phase", hdmfMainMod.txtPhaseID.getText());
					mapParameters.put("11_To", dteTo.getDate());
					mapParameters.put("12_Boolean", blnTag);
					FncReport.generateReport("/Reports/rpt_hdmf_msvs_status_verification.jasper", "Status of MSVS Reverification", "", mapParameters);				
					System.out.println("pasokkkk 5" );
				}
			}	
		}

		FncGlobal.stopProgress();
	}
	
	public void Export() {
		String strTmp = "";
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strTo = (String) sdfTo.format(dteTo.getDate());
		
		FncGlobal.startProgress("Status of MSVS");
		
		pgUpdate del = new pgUpdate();
		strTmp = "delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'";
		del.executeUpdate(strTmp, false);
		del.commit();
		
		if (cmbStage.getSelectedIndex()==0) {
			/*		MSVS Verification		*/
			if (blnTag) {
				pgUpdate ins = new pgUpdate();
				strTmp = "insert into tmp_hdmf (client_name, emp_code) \n" +
					"select a.name, '"+UserInfo.EmployeeCode+"' \n" +
					"from view_hdmf_msvs_report_verification ('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', " +
					"'"+hdmfMainMod.txtPhaseID.getText()+"', '"+strTo+"', "+blnTag+") a";
				ins.executeUpdate(strTmp, true);
				ins.commit();
				
				String[] strHead = {
						"Pag-IBIG FUND",
						"Circular " + cmbCircular.getSelectedItem().toString(), 
						"WINDOW II", 
						"", 
						"DEVELOPER'S NAME: " + hdmfMainMod.txtCo.getText().toString().trim(), 
						"PROJECT NAME: " + hdmfMainMod.txtPro.getText().toString().trim(), 
						"" 
				};
				
				String[] strCol = {"Last Name", "First Name", "Middle Name", "EXT", "Maiden Name", "Project", 
						"Phase", "Block", "Lot", "Loanable Amount", "w/ CI", "w/ BVS", "w/ CAR", "w/ Co-Borrower"};
				
				String strSQL = "select last_name, first_name, middle_name, ext, maiden_name, proj_alias, phase, block, lot, loanable_amount, wci, wbvs, wcar, wcobor \n" +
					"from view_hdmf_transmittal('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"') a \n" +
					"where trim(a.last_name) || ', ' || trim(a.first_name) || ' ' || trim(a.middle_name) in \n" +
					"(select x.client_name from tmp_hdmf x where x.emp_code = '"+UserInfo.EmployeeCode+"') \n" +
					"order by a.last_name, a.first_name, a.middle_name";
				
				FncGlobal.CreateXLSwithHeaders(strCol, strSQL, "MSVS Status", 7, strHead);			
			} else {
				String[] strCol = {"Phase", "Block",  "Lot", "Name", "OR Date", "Date Forwarded to PAGIBIG",  "Reverified Date", "Reverified Findings", 
						"Date of MSVS Reverified (OK MSVS)", "Date Docs Complied", "Notice Ready for Filing w/ Findings", 
						"Final Notice for Document Compliance", "House Completion Pctg.",  "DP Term", "DP Stage", "Payment Status"};
				String strSQL = "select * from view_hdmf_msvs_report_verification ('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+dteTo.getDate().toString()+"', "+blnTag+")";
				FncGlobal.CreateXLS(strCol, strSQL, "MSVS Status");	
			}
		} else if (cmbStage.getSelectedIndex()==1) {
			/*		MSVS Reverification		*/
			if (blnTag) {
				pgUpdate ins = new pgUpdate();
				strTmp = "insert into tmp_hdmf (client_name, emp_code) \n" +
					"select a.name, '"+UserInfo.EmployeeCode+"' \n" +
					"from view_hdmf_msvs_report_reverification ('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', " +
					"'"+hdmfMainMod.txtPhaseID.getText()+"', '"+strTo+"', "+blnTag+") a";
				ins.executeUpdate(strTmp, true);
				ins.commit();
				
				String[] strHead = {
						"Pag-IBIG FUND",
						"Circular " + cmbCircular.getSelectedItem().toString(), 
						"WINDOW II", 
						"", 
						"DEVELOPER'S NAME: " + hdmfMainMod.txtCo.getText().toString().trim(), 
						"PROJECT NAME: " + hdmfMainMod.txtPro.getText().toString().trim(), 
						"" 
				};
				
				String[] strCol = {"Last Name", "First Name", "Middle Name", "EXT", "Maiden Name", "Project", 
						"Phase", "Block", "Lot", "Loanable Amount", "w/ CI", "w/ BVS", "w/ CAR", "w/ Co-Borrower"};

				String strSQL = "select last_name, first_name, middle_name, ext, maiden_name, proj_alias, phase, block, lot, loanable_amount, wci, wbvs, wcar, wcobor \n" +
					"from view_hdmf_transmittal('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"') a \n" +
					"where trim(a.last_name) || ', ' || trim(a.first_name) || ' ' || trim(a.middle_name) in \n" +
					"(select x.client_name from tmp_hdmf x where x.emp_code = '"+UserInfo.EmployeeCode+"') \n" +
					"order by a.last_name, a.first_name, a.middle_name";
				
				FncGlobal.CreateXLSwithHeaders(strCol, strSQL, "MSVS Status", 7, strHead);
			} else {
				String[] strCol = {"Phase", "Block",  "Lot", "Name", "OR Date", "Date Forwarded to PAGIBIG",  "Reverified Date", "Reverified Findings", 
						"Date of MSVS Reverified (OK MSVS)", "Date Docs Complied", "Notice Ready for Filing w/ Findings", 
						"Final Notice for Document Compliance", "House Completion Pctg.",  "DP Term", "DP Stage", "Payment Status"};
				String strSQL = "select * from view_hdmf_msvs_report_reverification ('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+dteTo.getDate().toString()+"', "+blnTag+")";
				FncGlobal.CreateXLS(strCol, strSQL, "MSVS Status");	
			}
		} else if (cmbStage.getSelectedIndex()==2) {
			/*		MSVS HL Verification	*/
			if (blnTag) {
				pgUpdate ins = new pgUpdate();
				strTmp = "insert into tmp_hdmf (client_name, emp_code) \n" +
					"select a.name, '"+UserInfo.EmployeeCode+"' \n" +
					"from view_hdmf_msvs_report_hlverification ('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', " +
					"'"+hdmfMainMod.txtPhaseID.getText()+"', '"+strTo+"', "+blnTag+") a";
				ins.executeUpdate(strTmp, true);
				ins.commit();
				
				String[] strHead = {
						"Pag-IBIG FUND",
						"Circular " + cmbCircular.getSelectedItem().toString(), 
						"WINDOW II", 
						"", 
						"DEVELOPER'S NAME: " + hdmfMainMod.txtCo.getText().toString().trim(), 
						"PROJECT NAME: " + hdmfMainMod.txtPro.getText().toString().trim(), 
						"" 
				};
				
				String[] strCol = {"Last Name", "First Name", "Middle Name", "EXT", "Maiden Name", "Project", 
						"Phase", "Block", "Lot", "Loanable Amount", "w/ CI", "w/ BVS", "w/ CAR", "w/ Co-Borrower"};
				
				String strSQL = "select last_name, first_name, middle_name, ext, maiden_name, proj_alias, phase, block, lot, loanable_amount, wci, wbvs, wcar, wcobor \n" +
					"from view_hdmf_transmittal('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"') a \n" +
					"where trim(a.last_name) || ', ' || trim(a.first_name) || ' ' || trim(a.middle_name) in \n" +
					"(select x.client_name from tmp_hdmf x where x.emp_code = '"+UserInfo.EmployeeCode+"') \n" +
					"order by a.last_name, a.first_name, a.middle_name";
				
				FncGlobal.CreateXLSwithHeaders(strCol, strSQL, "MSVS Status", 7, strHead);			
			} else {
				String[] strCol = {"Phase", "Block",  "Lot", "Name", "OR Date", "Date Forwarded to PAGIBIG",  "Reverified Date", "Reverified Findings", 
						"Date of MSVS Reverified (OK MSVS)", "Date Docs Complied", "Notice Ready for Filing w/ Findings", 
						"Final Notice for Document Compliance", "House Completion Pctg.",  "DP Term", "DP Stage", "Payment Status"};
				String strSQL = "select * from view_hdmf_msvs_report_hlverification ('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+dteTo.getDate().toString()+"', "+blnTag+")";
				FncGlobal.CreateXLS(strCol, strSQL, "MSVS Status");	
			}
		}
		FncGlobal.stopProgress();
	}
	
	private void CreateDevEntry() {
		String strDir = FncGlobal.OpenDir("Folder");
		
		try {
			createADDRESSB(strDir);
			createADDRESSA(strDir);
			createADDRESSE(strDir);
			createAPMASTER(strDir);		
			createCHAR_REF(strDir);			
			createDOA_DATA(strDir);
			createINSURE(strDir);
		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createAPMASTER(String strDir) throws DBFException, IOException{
		String col_names [] = {"DEVCODE", "PROJCODE", "APPLICNO", "APPLICDATE", "PE", "GUIDELINE", "SHLF" , "PROG_CD", "LNAME", "FNAME", 
				"MID", "EXTNAME", "PURP_CD", "PAYMODE", "AMTAPPLIED", "TERM", "REGHOLDER", "ISMORT", "LANDAREA", 
				"EXIST_STRY", "PROP_STRY", "EXIST_FLR", "PROP_FLR", "TCTNO", "TAXDECLOT", "TAXDECBLDG", "LOT_UNIT", 
				"BLK_BLDG", 
				"SURVEY_NO", 
				"HOUSEAGE", "HDMFID", "BDAY", "GENDER", "STATUS", "OTHER", "EMAILADDR", "OWNERSHIP", "RENT", 
				"YRSTAY", "SSS_GSISID", "TIN", "CELLPHONE", "BEEPERNO","NATURE_BUS", "POSITION", "YREMP_BUS", "FILIPINO", 
				"DEPENDENTS"};
		
		Integer char_columns [] = {2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 23, 24, 25, 26, 27, 28, 30, 32, 33, 34, 35, 36, 39, 40, 41, 42, 43, 44};
		Integer numeric_columns [] = {0, 1, 14, 15, 18, 19, 20, 21, 22, 29, 37, 38, 45, 47};
		Integer bool_columns [] = {4, 17, 46};
		Integer date_columns [] = {3, 31};
		
		FncExport.createDBF_FromModelWithQuery_MSVS(col_names, char_columns, bool_columns, date_columns, numeric_columns, modelMSVS, "APMASTER.DBF", strDir);
		
	}
	
	private void createADDRESSA(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "UNIT_NO", "BLDG_NAME", "STREET", "VILLAGE", "TOWN_CITY", "STATE", "COUNTRY", "ZIPCODE"};
		
		Integer char_columns [] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer numeric_columns [] = {};
		
		FncExport.createADDRESSA_DBF_FromModelWithQuery_MSVS(col_names, char_columns, numeric_columns, modelMSVS, "ADDRESSA.DBF", strDir);
		
	}
	
	private void createADDRESSB(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "TELNO", "HOUSE_NO", "STREET", "SUBD", "BRGY_VILL", "TOWN_CITY", "PROVINCE", "REGION", "ZIPCODE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
		Integer numeric_columns [] = {1};
		
		FncExport.createADDRESSB_DBF_FromModelWithQuery_MSVS(col_names, char_columns, numeric_columns, modelMSVS, "ADDRESSB.DBF", strDir);
	}
	
	private void createADDRESSE(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "EYERID", "BUS_NAME", "TELNO", "RM_FLR", "BLDG_NAME", "STREET", "BRGY_VILL", "TOWN_CITY", "PROVINCE", "REGION", "ZIPCODE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		Integer numeric_columns [] = {1};
		
		FncExport.createADDRESSE_DBF_FromModelWithQuery_MSVS(col_names, char_columns, numeric_columns, modelMSVS, "ADDRESSE.DBF", strDir);
	}
	
	private void createCHAR_REF(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "SEQ", "NAME", "ADDRESS", "TELNO"};
		
		Integer char_columns [] = {0, 3, 4, 5};
		Integer numeric_columns [] = {1, 2};
		
		FncExport.createCHAR_REF_DBF_FromModelWithQuery_MSVS(col_names, char_columns, numeric_columns, modelMSVS, "CHAR_REF.DBF", strDir);
	}
	
	private void createDOA_DATA(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "SEL_DATE", "SEL_DOCNO", "SEL_PGNO", "SEL_BOOKNO", "SEL_SERIES", "NOTARY_PUB", "ADA_DATE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6};
		Integer date_columns [] = {1, 7};
		
		FncExport.createDOADATA_DBF_FromModelWithQuery_MSVS(col_names, char_columns, date_columns, modelMSVS, "DOADATA.DBF", strDir);
	}
	
	private void createINSURE(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "GBCODE", "GBTYPE", "REFERNO", "REFDATE"};
		
		Integer char_columns [] = {0, 2, 3};
		Integer numeric_columns [] = {1};
		Integer date_columns [] = {4};
		
		FncExport.createINSURE_DBF_FromModelWithQuery_MSVS(col_names, char_columns, date_columns, numeric_columns, modelMSVS, "INSURE.DBF", strDir);
	}
	
    public static void Generate() {
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Generating Accounts");
				blnTag = false;
				FncTables.clearTable(modelMSVS);		
				DefaultListModel listModel = new DefaultListModel();
				rowMSVS.setModel(listModel);
				
				String sql = "";
				String strDate = "";
				
				SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
				strDate = (String) sdfTo.format(dteTo.getDate());
				
				try {
					if (!txtBatch.getValue().equals("")) {
						sql = "select * from view_hdmf_msvs_batch('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+cmbStage.getSelectedItem().toString()+"', '"+txtBatch.getValue()+"')";
						hdmfMainMod.CtrlLock_1(3);
						hdmfMainMod.intMSVS = 3;
					} else {
						if (cmbStage.getSelectedIndex()==0) {
							sql = "select * from view_hdmf_msvs_verification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
							hdmfMainMod.CtrlLock_1(2);
							hdmfMainMod.intMSVS = 2;
						} else if (cmbStage.getSelectedIndex()==1) {
							sql = "select * from view_hdmf_msvs_reverification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
							hdmfMainMod.CtrlLock_1(2);
							hdmfMainMod.intMSVS = 2;
						} else if (cmbStage.getSelectedIndex()==2) {
							sql = "select * from view_hdmf_msvs_hlverification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
							hdmfMainMod.CtrlLock_1(2);
							hdmfMainMod.intMSVS = 2;
						} else if (cmbStage.getSelectedIndex()==3) {
							sql = "select * from view_hdmf_msvs_reverified('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
							hdmfMainMod.CtrlLock_1(5);
							hdmfMainMod.intMSVS = 5;
						} 	
					}	
				} catch (NullPointerException ex) {
					if (cmbStage.getSelectedIndex()==0) {
						sql = "select * from view_hdmf_msvs_verification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
						hdmfMainMod.CtrlLock_1(2);
						hdmfMainMod.intMSVS = 2;
					} else if (cmbStage.getSelectedIndex()==1) {
						sql = "select * from view_hdmf_msvs_reverification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
						hdmfMainMod.CtrlLock_1(2);
						hdmfMainMod.intMSVS = 2;
					} else if (cmbStage.getSelectedIndex()==2) {
						sql = "select * from view_hdmf_msvs_hlverification('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
						hdmfMainMod.CtrlLock_1(2);
						hdmfMainMod.intMSVS = 2;
					} else if (cmbStage.getSelectedIndex()==3) {
						sql = "select * from view_hdmf_msvs_reverified('"+hdmfMainMod.txtCoID.getText()+"', '"+hdmfMainMod.txtProID.getText()+"', '"+hdmfMainMod.txtPhaseID.getText()+"', '"+strDate+"', "+blnTag+")";
						hdmfMainMod.CtrlLock_1(5);
						hdmfMainMod.intMSVS = 5;
					} 	
				}
				
				System.out.println("");
				System.out.println("strDate: "+strDate);
				System.out.println("SQL: " + sql);
				
				pgSelect db = new pgSelect();
				db.select(sql);	
		
				if (db.isNotNull()) {
					for (int x=0; x < db.getRowCount(); x++) {
						modelMSVS.addRow(db.getResult()[x]);
						listModel.addElement(modelMSVS.getRowCount());		
					}
				} else {
					hdmfMainMod.CtrlLock_1(1);
					hdmfMainMod.intMSVS = 1;
					JOptionPane.showMessageDialog(null, "There were no records retrieved.");
				}
				FncGlobal.stopProgress();
				return null;
			}
    	}; 
    	sw.execute(); 
    }
}
