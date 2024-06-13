package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class RealTimeDebitTransactions extends _JInternalFrame implements
		ActionListener, _GUI {

	private static final long serialVersionUID = 253536133162663595L;
	static String title = "Real-Time Debit Transactions";
	Dimension frameSize = new Dimension(500, 264);
	
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

	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public RealTimeDebitTransactions() {
		super(title, false, true, false, true);
		initGUI();
	}

	public RealTimeDebitTransactions(String title) {
		super(title);
		initGUI();
	}

	public RealTimeDebitTransactions(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panNorth = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panNorth, BorderLayout.PAGE_START);
			panNorth.setPreferredSize(new Dimension(0, 189));
			{
				JXPanel panNorth1 = new JXPanel(new BorderLayout(5, 5));
				panNorth.add(panNorth1, BorderLayout.PAGE_START);
				panNorth1.setPreferredSize(new Dimension(0, 124));
				panNorth1.setBorder(JTBorderFactory.createTitleBorder("Project and Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
						panNorth1.add(panLabel, BorderLayout.LINE_START);
						panLabel.setPreferredSize(new Dimension(160, 90));
						{
							lblCompany = new JLabel("Company");
							panLabel.add(lblCompany);
							lblCompany.setHorizontalAlignment(JTextField.LEFT);
						}
						{
							txtCoID = new _JLookup("");
							panLabel.add(txtCoID);
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.setLookupSQL(CompanySQL());
							txtCoID.setReturnColumn(0);
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
						}
						{
							{
								lblProject = new JLabel("Project");
								panLabel.add(lblProject);
								lblProject.setHorizontalAlignment(JTextField.LEFT);
							}
							{
								txtProID = new _JLookup("");
								panLabel.add(txtProID);
								txtProID.setHorizontalAlignment(JTextField.CENTER);
								txtProID.setLookupSQL(ProjectSQL(""));
								txtProID.setReturnColumn(0);
								txtProID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();
										if (data != null) {
											txtProject.setText(data[2].toString());
											txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
										}
									}
								});
							}
						}
						{
							{
								lblPhase = new JLabel("Phase");
								panLabel.add(lblPhase);
								lblPhase.setHorizontalAlignment(JTextField.LEFT);
							}
							{
								txtPhaseID = new _JLookup("");
								panLabel.add(txtPhaseID);
								txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
								txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
								txtPhaseID.setReturnColumn(0);
								txtPhaseID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();
										if (data != null) {
											txtPhase.setText(data[2].toString());
										}
									}
								});
							}
						}
					}
					{
						JXPanel panBox = new JXPanel(new GridLayout(3, 1, 5, 5));
						panNorth1.add(panBox, BorderLayout.CENTER);
						{
							txtCompany = new JTextField("");
							panBox.add(txtCompany, BorderLayout.CENTER);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
						}
						{
							txtProject = new JTextField("");
							panBox.add(txtProject, BorderLayout.CENTER);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
						}
						{
							txtPhase = new JTextField("");
							panBox.add(txtPhase, BorderLayout.CENTER);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
						}
					}
				}
				
				JXPanel panNorth2 = new JXPanel(new BorderLayout(5, 5));
				panNorth.add(panNorth2, BorderLayout.CENTER);
				{
					JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5));
					panNorth2.add(panDate, BorderLayout.CENTER);
					panDate.setPreferredSize(new Dimension(200, 0));
					panDate.setBorder(JTBorderFactory.createTitleBorder("Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							JXPanel panFrom = new JXPanel(new BorderLayout(5, 5));
							panDate.add(panFrom, BorderLayout.CENTER);
							{
								{
									JLabel lblFrom = new JLabel("From:");
									panFrom.add(lblFrom, BorderLayout.LINE_START);
									lblFrom.setHorizontalAlignment(JTextField.LEADING);
									lblFrom.setPreferredSize(new Dimension(50, 0));
								}
								{
									dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panFrom.add(dteDateFrom, BorderLayout.CENTER);
									dteDateFrom.setDate(null);
									dteDateFrom.setEnabled(true);
									dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
						}
						{
							JXPanel panTo = new JXPanel(new BorderLayout(5, 5));
							panDate.add(panTo, BorderLayout.CENTER);
							{
								{
									JLabel lblTo = new JLabel("To:");
									panTo.add(lblTo, BorderLayout.LINE_START);
									lblTo.setHorizontalAlignment(JTextField.CENTER);
									lblTo.setPreferredSize(new Dimension(50, 0));
								}
								{
									dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panTo.add(dteDateTo, BorderLayout.CENTER);
									dteDateTo.setDate(null);
									dteDateTo.setEnabled(true);
									dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
						}
					}
				}
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			{
				JXPanel panButton = new JXPanel(new GridLayout(1, 2, 5, 5));
				panCenter.add(panButton, BorderLayout.CENTER);
				{
					btnPreview = new JButton("Preview");
					panButton.add(btnPreview);
					btnPreview.setActionCommand("Preview");;
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(true);
				}
				{
					btnExport = new JButton("Export");
					panButton.add(btnExport);
					btnExport.setActionCommand("Export");;
					btnExport.addActionListener(this);
					btnExport.setEnabled(false);
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
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
			   "ORDER BY proj_name";
	}
	
	private String sqlPhase(String strProject) {
		return "select x.phase, x.sub_proj_name \n" +
			"from mf_sub_project x \n" +
			"where x.phase in (select distinct a.phase from mf_unit_info a) \n" +
			"and (proj_id = '"+strProject+"' or '"+strProject+"' = 'null') \n" +
			"and status_id = 'A' \n" +
			"order by x.phase::int";
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
	
	public void actionPerformed(final ActionEvent e) {
		FncGlobal.startProgress("Generating report...");
		
		if (e.getActionCommand().equals("Preview")) {
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
			GenerateReport();
			btnPreview.setEnabled(true);
			btnExport.setEnabled(false);
		} else if (e.getActionCommand().equals("Export")) {

		}
		
		FncGlobal.stopProgress();
    }
	
	private void GenerateReport() {
		String strDate = "";
		String strDateFrom = "";
		String strDateTo = "";
		
		strDateFrom = df.format(dteDateFrom.getDate());
		strDateTo = df.format(dteDateTo.getDate());
		
		if (dteDateFrom.getDate()!=dteDateTo.getDate()) {
			strDate =  strDateFrom + " to " + strDateTo;
		} else {
			strDate = strDateTo;
		}
		
		String strCoID = "";
		String strProID = "";
		String strPhase = "";
		
		if (txtCompany.getText().equals("All Companies")) {
			strCoID = "";
		} else {
			strCoID = txtCoID.getText();
		}
		
		if (txtProject.getText().equals("All Projects")) {
			strProID = "";
		} else {
			strProID = txtProID.getText();
		}
		
		if (txtPhaseID.getText().equals("All Phase")) {
			strPhase = "";
		} else {
			strPhase = txtPhaseID.getText();
		}
		
		System.out.println("");
		System.out.println("select * \n" +
				"from view_rtd_transactions('"+txtCoID.getValue()+"', '"+txtProID.getValue()+"', '"+txtPhaseID.getValue()+"', '"+dteDateFrom.getDate().toString()+"', '"+dteDateTo.getDate().toString()+"');");
		
		
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", txtCompany.getText());
		mapParameters.put("02_AsOfDate", strDate);
		mapParameters.put("03_CoID", strCoID);
		mapParameters.put("04_ProID", strProID);
		mapParameters.put("05_Phase", strPhase);
		mapParameters.put("06_Project", txtProject.getText());
		mapParameters.put("07_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("09_Tagged", "");
		mapParameters.put("10_empcode", UserInfo.EmployeeCode);
		mapParameters.put("11_dateFrom", dteDateFrom.getDate().toString());
		mapParameters.put("12_dateTo", dteDateTo.getDate().toString());
		FncReport.generateReport("/Reports/rpt_RealTimeDebitTransactions.jasper", "RTD Transactions", "", mapParameters);
	}
}
