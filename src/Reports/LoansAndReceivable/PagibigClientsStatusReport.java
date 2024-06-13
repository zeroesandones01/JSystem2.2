package Reports.LoansAndReceivable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.poi.hssf.record.FnGroupCountRecord;
import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncExport;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class PagibigClientsStatusReport extends _JInternalFrame implements
		ActionListener {

	private static final long serialVersionUID = -7121186895884520454L;
	static String title = "PAGIBIG Clients Status";
	Dimension frameSize = new Dimension(650, 350);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	
	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
			
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	
	private _JDateChooser dteDateFrom;
	
	private JButton btnPreview;
	private JButton btnExport;
	
	private JComboBox cboStatus;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public PagibigClientsStatusReport() {
		super(title, true, true, true, true);
		InitGUI();
	}

	public PagibigClientsStatusReport(String title) {
		super(title);
		InitGUI();
	}

	public PagibigClientsStatusReport(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		InitGUI();
	}

	public void InitGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel pnlCenter = new JXPanel(new GridBagLayout());
			panMain.add(pnlCenter,BorderLayout.CENTER);
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				{
					gbc.weightx = 1;
					gbc.weighty = 2;

					gbc.gridx = 0;
					gbc.gridy = 0;
					
					JXPanel panNorth1 = new JXPanel(new GridBagLayout());
					pnlCenter.add(panNorth1, gbc);
					panNorth1.setBorder(JTBorderFactory.createTitleBorder("Project and Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagOne = new GridBagConstraints();
						bagOne.fill = GridBagConstraints.BOTH;
						bagOne.insets = new Insets(5,3,5,3);
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 0;
							
							lblCompany = new JLabel("Company");
							panNorth1.add(lblCompany,bagOne);
							lblCompany.setHorizontalAlignment(JTextField.LEFT);
							lblCompany.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 0;
							
							txtCoID = new _JLookup("");
							panNorth1.add(txtCoID,bagOne);
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.setLookupSQL(CompanySQL());
							txtCoID.setReturnColumn(0);
							txtCoID.setFont(FncGlobal.sizeTextValue);
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText(data[1].toString());
										txtProID.setLookupSQL(ProjectSQL(txtCoID.getValue()));
										
										btnPreview.setEnabled(true);
										btnExport.setEnabled(true);
									} else {
										btnPreview.setEnabled(false);
										btnExport.setEnabled(false);									
									}
								}
							});
							txtCoID.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtCoID.setValue("");
										txtCompany.setText("All Companies");
									}
								}
								
								public void keyReleased(KeyEvent e) {
									
								}
								
								public void keyPressed(KeyEvent e) {

								}
							});
						}
						{
							bagOne.weightx = 1.25;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 0;
							
							txtCompany = new JTextField("");
							panNorth1.add(txtCompany, bagOne);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
							txtCompany.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 1;
							
							lblProject = new JLabel("Project");
							panNorth1.add(lblProject,bagOne);
							lblProject.setHorizontalAlignment(JTextField.LEFT);
							lblProject.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 1;
							
							txtProID = new _JLookup("");
							panNorth1.add(txtProID,bagOne);
							txtProID.setHorizontalAlignment(JTextField.CENTER);
							txtProID.setLookupSQL(ProjectSQL(""));
							txtProID.setReturnColumn(0);
							txtProID.setFont(FncGlobal.sizeTextValue);
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtProject.setText(data[2].toString());
										txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
									}
								}
							});
							txtProID.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtProID.setValue("");
										txtProject.setText("All Projects");
									}
								}

								public void keyReleased(KeyEvent e) {
																		
								}
								
								public void keyPressed(KeyEvent e) {
																		
								}
							});
						}
						{
							bagOne.weightx = 1.25;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 1;
							
							txtProject = new JTextField("");
							panNorth1.add(txtProject,bagOne);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
							txtProject.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 2;
							
							lblPhase = new JLabel("Phase");
							panNorth1.add(lblPhase,bagOne);
							lblPhase.setHorizontalAlignment(JTextField.LEFT);
							lblPhase.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 2;
							
							txtPhaseID = new _JLookup("");
							panNorth1.add(txtPhaseID,bagOne);
							txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
							txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
							txtPhaseID.setReturnColumn(0);
							txtPhaseID.setFont(FncGlobal.sizeTextValue);
							txtPhaseID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtPhase.setText(data[1].toString());
									}
								}
							});
							txtPhaseID.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtPhaseID.setValue("");
										txtPhase.setText("All Phase");
									}
								}
								
								public void keyReleased(KeyEvent e) {
									
								}
								
								public void keyPressed(KeyEvent e) {
									
								}
							});
						}
						{
							bagOne.weightx = 1.25;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 2;
							
							txtPhase = new JTextField("");
							panNorth1.add(txtPhase,bagOne);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
							txtPhase.setFont(FncGlobal.sizeTextValue);
						}
					}
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 0.25;

					gbc.gridx = 0;
					gbc.gridy = 1;
					
					JXPanel pnlCon = new JXPanel(new GridBagLayout());
					pnlCenter.add(pnlCon,gbc);
					{
						GridBagConstraints bagCon = new GridBagConstraints();
						bagCon.fill = GridBagConstraints.BOTH;
						bagCon.insets = new Insets(5,5,5,5);
						{
							bagCon.weightx = 1;
							bagCon.weighty = 1;

							bagCon.gridx = 0;
							bagCon.gridy = 0;
							
							JXPanel panDate = new JXPanel(new GridBagLayout());
							pnlCon.add(panDate, bagCon);
							panDate.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								GridBagConstraints conDate = new GridBagConstraints();
								conDate.fill = GridBagConstraints.BOTH;
								conDate.insets = new Insets(5,3,5,3);
								{
									conDate.weightx = 1;
									conDate.weighty = 1;

									conDate.gridx= 0;
									conDate.gridy = 0; 
									
									dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panDate.add(dteDateFrom, conDate);
									dteDateFrom.setDate(null);
									dteDateFrom.setEnabled(true);
									dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dteDateFrom.setFont(FncGlobal.sizeTextValue);
								}
							}
						}
						{
							bagCon.weightx = 1;
							bagCon.weighty = 1;

							bagCon.gridx = 1;
							bagCon.gridy = 0;
							
							JXPanel panStatus = new JXPanel(new GridBagLayout());
							pnlCon.add(panStatus,bagCon);
							panStatus.setBorder(JTBorderFactory.createTitleBorder("Status", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								GridBagConstraints conSatus = new GridBagConstraints();
								conSatus.fill = GridBagConstraints.BOTH;
								conSatus.insets = new Insets(5,3,5,3);
								{
									conSatus.weightx = 1;
									conSatus.weighty = 1;

									conSatus.gridx= 0;
									conSatus.gridy = 0;
									
									String strCombo[] =	{"All", "56 - TCT Forwarded to RD for Annotation", "17 - Temporary Reserved", "01 - Officially Reserved", "18 - Documents Complete", 
											"31 - Loan Filed (PagIbig)", "43 - Loan Rtnd (First Filing w/ Findings)", "35 - Notice Of Approval (NOA) Released", 
											"56 - TCT Forwarded to RD for Annotation", "46 - TCT Annotated", "48 - Post Approval (Refiling)", "144 - Post Approval (Refiling G to G)", "32 - Loan Released"};
									
									cboStatus = new JComboBox(strCombo);
									panStatus.add(cboStatus,conSatus);
									cboStatus.setFont(FncGlobal.sizeTextValue);
								}
							}
						}
					}
				}
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.SOUTH);
			{
				JXPanel panButton = new JXPanel(new GridLayout(1, 4, 5, 5));
				panCenter.add(panButton, BorderLayout.CENTER);
				panButton.setPreferredSize(new Dimension(0,30));
				{
					panButton.add(Box.createHorizontalBox());
					panButton.add(Box.createHorizontalBox());
					panButton.add(Box.createHorizontalBox());
				}
				{
					btnPreview = new JButton("Preview");
					panButton.add(btnPreview);
					btnPreview.setActionCommand("Preview");;
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(true);
					btnPreview.setFont(FncGlobal.sizeControls);
				}
				{
					btnExport = new JButton("Export");
					//panButton.add(btnExport);
					btnExport.setActionCommand("Export");;
					btnExport.addActionListener(this);
					btnExport.setEnabled(true);
				}
			}
		}
		
		Setdefaults();
	}
	
	private static String CompanySQL() {
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			   "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}
	
	public static String ProjectSQL(String strCo){
		return "SELECT proj_id, proj_alias, proj_name\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"') \n"+
			   "AND co_id != '03' \n" +
			   "AND proj_id NOT IN ('005', '021') \n"+
			   "ORDER BY proj_name";
	}
	
    public void actionPerformed(final ActionEvent e) {
		FncGlobal.startProgress("Generating report...");
		
		if (e.getActionCommand().equals("Preview")) {
			GenerateReport();
		} else if (e.getActionCommand().equals("Export")) {
			String strStatus = "";			
			strStatus = (String) cboStatus.getSelectedItem();
			strStatus = strStatus.substring(0, 2);
			
			if (strStatus.equals("Al")) {
				strStatus = "";
			}
			
			//CreateXLS(strStatus);
			Export(strStatus);
		}

		
		btnPreview.setEnabled(true);
		btnExport.setEnabled(true);
		FncGlobal.stopProgress();
    }
    
	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;
		
		/*	Company Default	*/
		strCount = GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtCoID.setText("");
			txtCompany.setText("All Companies");
		} else {
			txtCoID.setValue(GetValue("SELECT co_id FROM mf_company LIMIT 1"));
			txtCompany.setText(GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		}
		
		/*	Project Default	*/
		strCount = GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtProID.setText("");
			txtProject.setText("All Projects");
		} else {
			txtProID.setValue(GetValue("SELECT proj_id FROM mf_project LIMIT 1"));
			txtProject.setText(GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProID.getText()+"'"));
		}
		
		txtPhaseID.setValue("");
		txtPhase.setText("All Phase");
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
	public static String Padme(String str, Integer int_i) {
		String strPad = "";
		Integer i = int_i - str.length();
		
		while (i > 0) {
			strPad = strPad + "0";
			i--;
		}
		strPad = strPad + str;
		return strPad;
	}
	
	private String sqlPhase(String strProject) {
		return "select distinct x.phase, 'PHASE ' || phase \n" +
			"from mf_unit_info x \n" +
			"inner join mf_project y on x.proj_id = y.proj_id \n" +
			"where (x.proj_id = '"+strProject+"' or '"+strProject+"' = '') \n" +
			"order by x.phase";
	}
	
	private void GenerateReport() {
		new Thread(new Runnable() {
			public void run() {
				String strStatus = "";
				String strProject = "";
				String strPhase = "";
				
				strStatus = (String) cboStatus.getSelectedItem();
				strStatus = strStatus.substring(0, 2);
				
				if (strStatus.equals("Al")) {
					strStatus = "";
				}
				if (strStatus.equals("14")) {
					strStatus = "144";
				}
				
				if (txtProject.getText() == "All Projects") {
					strProject = "";
				} else {
					strProject = txtProID.getValue();
				}
				
				if (strProject == null) {
					strProject = "";
				}
				
				if (txtPhase.getText() == "All Phase") {
					strPhase = "";
				} else {
					strPhase = txtPhaseID.getValue();
				}
				
				String strDate = "";
				
				strDate = df.format(dteDateFrom.getDate());
				strDate = "As Of: " + strDate;
				
				System.out.println("");
				System.out.println("co_id: " + txtCoID.getText());
				System.out.println("co_name: " + txtCompany.getText());
				System.out.println("date_from: " + dteDateFrom.getText());
				System.out.println("date_param: " + strDate);
				System.out.println("project_id: " + strProject);
				System.out.println("user_name: " + UserInfo.EmployeeCode);
				System.out.println("strStatus: " + strStatus);
				
				System.out.println("");
				System.out.println("SELECT * \n" +
					"FROM view_hdmf_status('"+txtCoID.getText()+"', '"+strProject+"', '"+strPhase+"', '"+dteDateFrom.getText()+"', '"+strStatus+"') A\n" +
					"ORDER BY A.c_status");
				
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("co_id", txtCoID.getText());
				mapParameters.put("co_name", txtCompany.getText());
				mapParameters.put("dateFrom", dteDateFrom.getText());
				mapParameters.put("dateParam", strDate);
				mapParameters.put("project_id", strProject);
				mapParameters.put("user_name", UserInfo.EmployeeCode);
				mapParameters.put("project", txtProject.getText());
				mapParameters.put("status", strStatus);
				mapParameters.put("phase", strPhase);
				FncReport.generateReport("/Reports/rpt_hdmf_status_report_v2.jasper", "PAGIBIG Clients Status", "", mapParameters);
			}
		}).start();
	}
	
	private void Export(String strStatus) {
		String strCoID = "";
		String strProjectID = "";
		String strPhaseID = "";
		
		if (txtCoID.getValue()==null || txtCoID.getValue()=="null" || txtCoID.getValue() == "") {
			strCoID = "";
		} else {
			strCoID = txtCoID.getValue();
		}
		
		if (txtProID.getValue()==null || txtProID.getValue()=="null" || txtProID.getValue() == "") {
			strProjectID = "";
		} else {
			strProjectID = txtProID.getValue();
		}
		
		if (txtPhaseID.getValue()==null || txtPhaseID.getValue()=="null" || txtPhaseID.getValue() == "") {
			strPhaseID = "";
		} else {
			strPhaseID = txtPhaseID.getValue();
		}
		
		String[] strHead = {
				"DEVELOPER'S NAME: " + txtCompany.getText(), 
				"PROJECT NAME: " + txtProject.getText(), 
				"DATE: " + FncGlobal.getDateSQL().toString(), 
				"PAGIBIG CLIENTS STATUS"
		};

		String col_names[] = {
				"phase", "block", "lot", "name", "model", "loanable amount", "payment status", "payment type", "dp term", "ntc date",  
				"ntp date", "end duration", "house pctg", "house date", "pagibig insp", "tct date", "tax dec lot date", "tax dec house date",  
				"tr date", "or date", "complete date", "notarized date", "filed hdmf", "noa rlsd", "noa signed", "noa ext", "doa forwarded to hdmf",  
				"doa signed", "tct forwarded to rd", "epeb date", "status"
		};
		
		String strSQL = "select c_phase, c_block, c_lot, c_name, c_model, c_loanable_amount, c_payment_status, c_payment_type, c_dp_term, c_ntc_date, c_ntp_date, \n" + 
				"c_end_duration, c_house_pctg, c_house_date, \n" +
				"c_pagibig_insp, c_tct_date, c_taxdec_lot, c_taxdec_house, c_tr_date, c_or_date, c_complete_date, \n" +
				"c_notarized_date, c_filed_hdmf, c_noa_rlsd, c_noa_signed, c_noa_ext, c_doa_fwd_hdmf, c_doa_signed, c_tct_fwd_rd, c_epeb_date, c_status \n" +
				"FROM view_hdmf_status('"+strCoID+"', '"+strProjectID+"', '"+strPhaseID+"', '"+dteDateFrom.getDate().toString()+"', '"+strStatus+"') A \n" +
				"ORDER BY A.c_name, A.c_status;";
		
		FncGlobal.startProgress("Creating spreadsheet.");
		FncExport.CreateXLSwithHeaders(col_names, strSQL, "PAGIBIG Clients Status", 4, strHead);
		FncGlobal.stopProgress();
	}
}