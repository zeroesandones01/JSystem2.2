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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
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

public class LoanReleasedReport extends _JInternalFrame implements
ActionListener {

	private static final long serialVersionUID = -6841192302204282636L;
	static String title = "Loan Released Report";
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
	private _JDateChooser dteDateTo;

	private JButton btnPreview;
	private JButton btnExport;

	private JComboBox cboRep; 

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public LoanReleasedReport() {
		super(title, true, true, true, true);
		InitGUI();
	}

	public LoanReleasedReport(String title) {
		super(title);
		InitGUI();
	}

	public LoanReleasedReport(String title, boolean resizable,
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
						bagOne.insets = new Insets(5,5,5,5);
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
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 0;
							
							txtCompany = new JTextField("");
							panNorth1.add(txtCompany,bagOne);
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
							bagOne.weightx = 1;
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
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 2;
							
							txtPhase = new JTextField("");
							panNorth1.add(txtPhase,bagOne);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
							txtPhase.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 3;
							
							JLabel lblReportType = new JLabel("Report:");
							panNorth1.add(lblReportType,bagOne);
							lblReportType.setHorizontalAlignment(JTextField.LEFT);
							lblReportType.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 3;
							
							String strCombo[] =	{
									"1 - Loan Released Report (Full)",
									"2 - Loan Released Report (Per Division)",
									"3 - Retained Amount from PAGIBIG",
									"4 - Turnover Orientation Attendance",
									"5 - Loan Released Report by MA",
									"6 - EWT Compliance Report", 
							}; 

							cboRep = new JComboBox(strCombo);
							panNorth1.add(cboRep,bagOne); 
							cboRep.setFont(FncGlobal.sizeTextValue);
							cboRep.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

									if (cboRep.getSelectedIndex()==0 || cboRep.getSelectedIndex()==1 || cboRep.getSelectedIndex()==5) {
										dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									} else {
										dteDateFrom.setDate(FncGlobal.dateFormat(new String("2017-08-01")));
									} 
								}
							});

						}
					}
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 0.25;

					gbc.gridx = 0;
					gbc.gridy = 1;
					
					JXPanel panNorth2 = new JXPanel(new GridBagLayout());
					pnlCenter.add(panNorth2,gbc);
					panNorth2.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagTwo = new GridBagConstraints();
						bagTwo.fill = GridBagConstraints.BOTH;
						bagTwo.insets = new Insets(5,5,5,5);
						{
							bagTwo.weightx = 0;
							bagTwo.weighty = 0;
							
							bagTwo.gridx = 0;
							bagTwo.gridy = 0;
							
							JLabel lblFrom = new JLabel("From:"); 
							panNorth2.add(lblFrom,bagTwo);
							lblFrom.setHorizontalAlignment(JTextField.LEADING);
							lblFrom.setFont(FncGlobal.sizeLabel);
							
						}
						{
							bagTwo.weightx = 1;
							bagTwo.weighty = 0.1;
							
							bagTwo.gridx = 1;
							bagTwo.gridy = 0;
							
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panNorth2.add(dteDateFrom, bagTwo);
							dteDateFrom.setEnabled(true);
							dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteDateFrom.setFont(FncGlobal.sizeTextValue);
							
						}
						{
							bagTwo.weightx = 0;
							bagTwo.weighty = 0;
							
							bagTwo.gridx = 2;
							bagTwo.gridy = 0;
							
							JLabel lblTo = new JLabel("To:"); 
							panNorth2.add(lblTo,bagTwo); 
							lblTo.setHorizontalAlignment(JTextField.CENTER);
							lblTo.setFont(FncGlobal.sizeLabel);
							
						}
						{
							bagTwo.weightx = 1;
							bagTwo.weighty = 0.1;
							
							bagTwo.gridx = 3;
							bagTwo.gridy = 0;
							
							dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panNorth2.add(dteDateTo, bagTwo);
							dteDateTo.setEnabled(true);
							dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteDateTo.setFont(FncGlobal.sizeTextValue);

						}
					}
					
				}
			}
		}
		
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.SOUTH);
				{
					JXPanel panButton = new JXPanel(new GridLayout(1, 1, 5, 5));
					panCenter.add(panButton, BorderLayout.CENTER);
					panButton.setPreferredSize(new Dimension(0,30));
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
						panButton.add(btnExport);
						btnExport.setActionCommand("Export");;
						btnExport.addActionListener(this);
						btnExport.setEnabled(true);
						btnExport.setFont(FncGlobal.sizeControls);
					}
				}
			}
		

		//Setdefaults();
	}

	private static String CompanySQL() {
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}

	public static String ProjectSQL(String strCo){
		return "SELECT proj_id, proj_alias, proj_name\n" +
				"FROM mf_project\n" +
				"WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
				"AND proj_id NOT IN ('020', '021') \n"+
				"ORDER BY proj_name";
	}

	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;

		/*	Company Default	*/
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			txtCoID.setText("");
			txtCompany.setText("All Companies");
		} else {
			txtCoID.setValue(_RealTimeDebit.GetValue("SELECT co_id FROM mf_company LIMIT 1"));
			txtCompany.setText(_RealTimeDebit.GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		}

		/*	Project Default	*/
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			txtProID.setText("");
			txtProject.setText("All Projects");
		} else {
			txtProID.setValue(_RealTimeDebit.GetValue("SELECT proj_id FROM mf_project LIMIT 1"));
			txtProject.setText(_RealTimeDebit.GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProID.getText()+"'"));
		}

		txtPhaseID.setValue("");
		txtPhase.setText("All Phase");
	}

	private String sqlPhase(String strProject) {
		return "select distinct x.phase, 'PHASE ' || phase \n" +
				"from mf_unit_info x \n" +
				"inner join mf_project y on x.proj_id = y.proj_id \n" +
				"where (x.proj_id = '"+strProject+"' or '"+strProject+"' = '') \n" +
				"order by x.phase";
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
				"DATE: " + df.format(dteDateFrom.getDate()) + " to " + df.format(dteDateTo.getDate()), 
				"LIST OF LOAN RELEASED ACCOUNTS"
		};

		String col_names[] = {
				"NAME", "PROJECT", "PHASE", "BLOCK", "LOT", "LOAN RELEASED DATE", "CHECK DATE", "LOANABLE AMOUNT", "MODEL", "CONTRACTOR TO CMD", "CMD TO PMD", "HOUSE PERCENTAGE"
		};

		String strSQL = "select c_name, c_proj_alias, c_phase, c_block, c_lot, c_loan_released, c_checkdate, c_loanable_amount, c_model, c_contractor_to_cmd, c_cmd_to_pmd, c_house_pctg::int \n" + 
				"from view_hdmf_loanreleased('"+strCoID+"', '"+strProjectID+"', '"+strPhaseID+"', '"+dteDateFrom.getDate().toString()+"'::date, '"+dteDateTo.getDate().toString()+"'::date) \n" + 
				"order by c_name"; 

		FncGlobal.startProgress("Creating spreadsheet.");
		FncExport.CreateXLSwithHeaders(col_names, strSQL, "List of Loan Released Accounts", 4, strHead);
		FncGlobal.stopProgress();
	}

	public void actionPerformed(final ActionEvent e) {
		FncGlobal.startProgress("Generating report...");

		if (e.getActionCommand().equals("Preview")) {
			GenerateReport();
		} else if (e.getActionCommand().equals("Export")) {
			Export("");
		}

		btnPreview.setEnabled(true);
		btnExport.setEnabled(true);
		FncGlobal.stopProgress();
	}

	private void GenerateReport() {
		new Thread(new Runnable() {
			public void run() {
				String strStatus = "";
				String strProject = "";
				String strPhase = "";

				if (strStatus.equals("Al")) {
					strStatus = "";
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

				if (dteDateFrom.getDate()==dteDateTo.getDate()) {
					strDate = df.format(dteDateFrom.getDate());
				} else {
					strDate = df.format(dteDateFrom.getDate()).toString() + " to " + df.format(dteDateTo.getDate()).toString();  
				}

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
						"FROM view_hdmf_loanreleased('"+txtCoID.getText()+"', '"+strProject+"', '"+strPhase+"', '"+dteDateFrom.getText()+"', '"+dteDateTo.getText()+"') a \n" +
						"ORDER BY a.c_name");

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("co_id", txtCoID.getText());
				mapParameters.put("co_name", txtCompany.getText());
				mapParameters.put("dateFrom", dteDateFrom.getDate());
				mapParameters.put("dateTo", dteDateTo.getDate());
				mapParameters.put("dateParam", strDate);
				mapParameters.put("project_id", strProject);
				mapParameters.put("user_name", UserInfo.EmployeeCode);
				mapParameters.put("project", txtProject.getText());
				mapParameters.put("phase", strPhase);

				if (cboRep.getSelectedIndex()==0) {
					FncReport.generateReport("/Reports/rpt_hdmf_loanReleased.jasper", "Loan Released Report Full", "", mapParameters);
					btnExport.setEnabled(true);
				} else if (cboRep.getSelectedIndex()==1) {
					FncReport.generateReport("/Reports/rpt_hdmf_loanReleased_v3.jasper", "Loan Released Report Part", "", mapParameters);
					btnExport.setEnabled(false);
				} else if (cboRep.getSelectedIndex()==2) {
					FncReport.generateReport("/Reports/rpt_hdmf_retainedamount.jasper", "Retained Amount", "", mapParameters);
					btnExport.setEnabled(false);
				} else if (cboRep.getSelectedIndex()==3) {
					FncReport.generateReport("/Reports/rpt_hdmf_to_orientation_attendance.jasper", "Retained Amount", "", mapParameters);
					btnExport.setEnabled(false);
				} else if (cboRep.getSelectedIndex()==4) {
					FncReport.generateReport("/Reports/rpt_hdmf_loanReleased_v4.jasper", "Loan Released Report by MA", "", mapParameters);
					btnExport.setEnabled(false);
				} else if (cboRep.getSelectedIndex()==5) {
//					FncReport.generateReport("/Reports/rpt_hdmf_ewtReport.jasper", "EWT Compliance Report", "", mapParameters);
					FncReport.generateReport("/Reports/rpt_hdmf_ewtReport_new1.jasper", "EWT Compliance Report", "", mapParameters);
					btnExport.setEnabled(false);
				}

				/*
				if (JOptionPane.showConfirmDialog(null, "Press 'YES' for the full report and 'NO' for the basic report format.", 
						"Option", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)==JOptionPane.YES_OPTION) {

				} else {

				}
				 */
			}
		}).start();
	}
}