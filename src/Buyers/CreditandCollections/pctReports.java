package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
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
import interfaces._GUI;

public class pctReports extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -8226845219095095811L;
	static String title = "PAGIBIG Collection Reports";
	Dimension frameSize = new Dimension(500, 329);

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
	
	private JComboBox cboReport;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public pctReports() {
		super(title, false, true, false, false);
		initGUI(); 
	}

	public pctReports(String title) {
		super(title);
		initGUI(); 
	}

	public pctReports(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
			panNorth.setPreferredSize(new Dimension(0, 255));
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
				{
					JXPanel panNorth2 = new JXPanel(new GridLayout(2, 1, 5, 5));
					panNorth.add(panNorth2, BorderLayout.CENTER);
					{
						{
							JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5));
							panNorth2.add(panDate, BorderLayout.LINE_START);
							panDate.setPreferredSize(new Dimension(200, 0));
							panDate.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									JXPanel panFrom = new JXPanel(new BorderLayout(5, 5));
									panDate.add(panFrom, BorderLayout.CENTER);
									{
										{
											JLabel lblFrom = new JLabel("From: "); 
											panFrom.add(lblFrom, BorderLayout.LINE_START); 
											lblFrom.setHorizontalAlignment(JTextField.LEADING);
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
											JLabel lblTo = new JLabel("To: "); 
											panTo.add(lblTo, BorderLayout.LINE_START); 
											lblTo.setHorizontalAlignment(JTextField.CENTER);
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
						{
							JXPanel panReport = new JXPanel(new BorderLayout(5, 5));
							panNorth2.add(panReport, BorderLayout.CENTER);
							panReport.setBorder(JTBorderFactory.createTitleBorder("Report Name", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{	
								cboReport = new JComboBox();
								panReport.add(cboReport, BorderLayout.CENTER);
								
								pgSelect db = new pgSelect(); 
								db.select("select pct_row_id::varchar || ' - ' || report_name from mf_pct_reports order by pct_row_id");
								
								if (db.isNotNull()) {
									for (int x = 0; x < db.getRowCount(); x++) {
										System.out.println("True: " + (String) db.getResult()[x][0].toString()==("10 - -------------------------"));
										if (x==9) {
											cboReport.addItem("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
										} else {
											cboReport.addItem((String) db.getResult()[x][0].toString());
										}
									}
								}
							}
						}
					}	
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					JXPanel panButton = new JXPanel(new GridLayout(1, 2, 5, 5));
					panCenter.add(panButton, BorderLayout.CENTER);
					{
						{
							btnPreview = new JButton("Preview");
							panButton.add(btnPreview);
							btnPreview.setActionCommand("Preview");
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
			}
		}
		
		Setdefaults();
	}
	
	public String GetValue(String SQL) {
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
		return "select distinct x.phase, 'PHASE ' || phase \n" +
			"from mf_unit_info x \n" +
			"inner join mf_project y on x.proj_id = y.proj_id \n" +
			"where (x.proj_id = '"+strProject+"' or '"+strProject+"' = '') \n" +
			"order by x.phase";
	}
	
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals("Preview")) {
			Generate(cboReport.getSelectedItem().toString());
		} else if (e.getActionCommand().equals("Export")) {

		}
    }
	
	private void Generate(String strReport) {
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
		strDate = df.format(dteDateFrom.getDate());
		strDate = "As Of: " + strDate;
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strTo = (String) sdf.format(dteDateTo.getDate());
		
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();  
		
		if (strReport.equals("1 - Loan Released MA Payments")) {
			mapParameters.put("co_id", txtCoID.getText());
			mapParameters.put("co_name", txtCompany.getText());
			mapParameters.put("dateFrom", strDate);
			mapParameters.put("project_id", strProject);
			mapParameters.put("user_name", UserInfo.EmployeeCode);
			mapParameters.put("project", txtProject.getText());
			mapParameters.put("phase", strPhase);
			FncReport.generateReport("/Reports/rpt_pct_loanReleased.jasper", strReport, "", mapParameters);
		} else if (strReport.equals("2 - Loan Released Accounts with Arrears")) {
			mapParameters.put("co_id", txtCoID.getText());
			mapParameters.put("co_name", txtCompany.getText());
			mapParameters.put("dateFrom", strDate);
			mapParameters.put("project_id", strProject);
			mapParameters.put("user_name", UserInfo.EmployeeCode);
			mapParameters.put("project", txtProject.getText());
			mapParameters.put("phase", strPhase);
			FncReport.generateReport("/Reports/rpt_pct_loanreleased_arrears.jasper", strReport, "", mapParameters);
		}
	}
}
