package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
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

public class summaryCashCheck extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = 4004247854013057557L;
	static String title = "Cash and Check Summary Report";
	Dimension frameSize = new Dimension(500, 305);
	
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	
	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
	private _JLookup txtBranchID;
			
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	private JTextField txtBranch;
	
	private _JDateChooser dteDateFrom;
	private _JDateChooser dteDateTo;
	
	private JButton btnPreview;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public summaryCashCheck() {
		super(title, true, true, false, true);
		initGUI();
	}

	public summaryCashCheck(String title) {
		super(title);
		initGUI();
	}

	public summaryCashCheck(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
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
			panNorth.setPreferredSize(new Dimension(0, 230));
			{
				JXPanel panNorth1 = new JXPanel(new BorderLayout(5, 5));
				panNorth.add(panNorth1, BorderLayout.PAGE_START);
				panNorth1.setPreferredSize(new Dimension(0, 165));
				panNorth1.setBorder(JTBorderFactory.createTitleBorder("Report Parameters", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panLabel = new JXPanel(new GridLayout(4, 2, 5, 5));
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
										} else {
											btnPreview.setEnabled(false);									
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
						{
							JLabel lblBranch = new JLabel("Branch:");
							panLabel.add(lblBranch);
							lblBranch.setHorizontalAlignment(JTextField.LEFT);
						}
						{
							txtBranchID = new _JLookup("");
							panLabel.add(txtBranchID);
							txtBranchID.setHorizontalAlignment(JTextField.CENTER);
							txtBranchID.setLookupSQL("select branch_id, branch_name from mf_office_branch order by branch_id");
							txtBranchID.setReturnColumn(0);
							txtBranchID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtBranch.setText(data[1].toString());
									}
								}
							});
							txtBranchID.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtBranchID.setValue("");
										txtBranch.setText("All Branches");
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
						JXPanel panBox = new JXPanel(new GridLayout(4, 1, 5, 5));
						panNorth1.add(panBox, BorderLayout.CENTER);
						{
							txtCompany = new JTextField("");
							panBox.add(txtCompany);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
							txtCompany.setEditable(false);
						}
						{
							txtProject = new JTextField("");
							panBox.add(txtProject);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
							txtProject.setEditable(false);
						}
						{
							txtPhase = new JTextField("");
							panBox.add(txtPhase);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
							txtPhase.setEditable(false);
						}
						{
							txtBranch = new JTextField("");
							panBox.add(txtBranch);
							txtBranch.setHorizontalAlignment(JTextField.CENTER);
							txtBranch.setEditable(false);
						}
					}
				}
				{
					JXPanel panNorth2 = new JXPanel(new GridLayout(1, 2, 5, 5));
					panNorth.add(panNorth2, BorderLayout.CENTER);
					panNorth2.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							JXPanel panDateFrom = new JXPanel(new BorderLayout(5, 5));
							panNorth2.add(panDateFrom, BorderLayout.LINE_START);
							panDateFrom.setPreferredSize(new Dimension(200, 0));
							{
								{
									JLabel lblFrom = new JLabel("From:"); 
									panDateFrom.add(lblFrom, BorderLayout.LINE_START);
									lblFrom.setHorizontalAlignment(JTextField.LEADING);
									lblFrom.setPreferredSize(new Dimension(50, 0));
								}
								{
									dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panDateFrom.add(dteDateFrom, BorderLayout.CENTER);
									dteDateFrom.setEnabled(true);
									dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}	
							}
						}
						{
							JXPanel panDateTo = new JXPanel(new BorderLayout(5, 5));
							panNorth2.add(panDateTo, BorderLayout.LINE_START);
							panDateTo.setPreferredSize(new Dimension(200, 0));
							{
								{
									JLabel lblTo = new JLabel("To:"); 
									panDateTo.add(lblTo, BorderLayout.LINE_START); 
									lblTo.setHorizontalAlignment(JTextField.CENTER);
									lblTo.setPreferredSize(new Dimension(50, 0));
								}
								{
									dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panDateTo.add(dteDateTo, BorderLayout.CENTER);
									dteDateTo.setEnabled(true);
									dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
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
					JXPanel panButton = new JXPanel(new GridLayout(1, 1, 5, 5));
					panCenter.add(panButton, BorderLayout.CENTER);
					{
						btnPreview = new JButton("Preview");
						panButton.add(btnPreview);
						btnPreview.setEnabled(true);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String strCoID = "";
								String strProID = "";
								String strPhaseID = "";
								String strBranchID = "";
								
								String strDate = "";
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								String company_logo = RequestForPayment.sql_getCompanyLogo();
								
								if (dteDateFrom.getDate()==dteDateTo.getDate()) {
									strDate = df.format(dteDateFrom.getDate());
								} else {
									strDate = df.format(dteDateFrom.getDate()).toString() + " to " + df.format(dteDateTo.getDate()).toString();  
								}

								if (!txtCoID.getValue().equals("")) {
									strCoID = txtCoID.getValue();
								} else {
									strCoID = "ALL";
								}
								
								if (!txtProID.getValue().equals("")) {
									strProID = txtProID.getValue();
								} else {
									strProID = "ALL"; 
								}
								
								if (!txtPhaseID.getValue().equals("")) {
									strPhaseID = txtPhaseID.getValue();
								} else {
									strPhaseID = "ALL"; 
								}
								
								if (!txtBranchID.getValue().equals("")) {
									strBranchID = txtBranchID.getValue();
								} else {
									strBranchID = "ALL"; 
								}
								
								pgUpdate dbExec = new pgUpdate(); 
								dbExec.executeUpdate("delete from tmp_check_summary where c_user = '"+UserInfo.EmployeeCode+"'", true);
								dbExec.commit();
								
								dbExec = new pgUpdate(); 
								dbExec.executeUpdate("delete from tmp_check_summary_2 where c_user = '"+UserInfo.EmployeeCode+"'", true);
								dbExec.commit();
								
								dbExec = new pgUpdate();
								dbExec.executeUpdate("insert into tmp_check_summary \n" +
										"select *, '"+UserInfo.EmployeeCode+"' \n" +
										"from view_check_summary('"+strPhaseID+"', '"+strBranchID+"', '"+strCoID+"', 'trans_date', '"+dteDateFrom.getDate().toString()+"', '"+dteDateTo.getDate().toString()+"');", true);
								dbExec.commit();
								
								dbExec = new pgUpdate();
								dbExec.executeUpdate("insert into tmp_check_summary_2 \n" +
										"select *, '"+UserInfo.EmployeeCode+"' \n" +
										"from view_check_summary_v2('"+strPhaseID+"', '"+strBranchID+"', '"+strCoID+"', 'trans_date', '"+dteDateFrom.getDate().toString()+"', '"+dteDateTo.getDate().toString()+"');", true);
								dbExec.commit();								
								
								Map<String, Object> mapParameters2 = new HashMap<String, Object>();
								mapParameters2.put("co_name", txtCompany.getText());
								mapParameters2.put("dateParam", strDate);
								mapParameters2.put("user_id", UserInfo.EmployeeCode);
								FncReport.generateReport("/Reports/rpt_summary_check_v2.jasper", "Check Summary 2", "", mapParameters2);
								//FncReport.generateReport("/Reports/rpt_summary_check.jasper", "Check Summary", "", mapParameters2);
								FncReport.generateReport("/Reports/rpt_summary_cash.jasper", "Cash Summary", "", mapParameters2);
							}
						});
					}
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
		
		txtCoID.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		
		txtProID.setValue("");
		txtProject.setText("All Projects");
		
		txtPhaseID.setValue("");
		txtPhase.setText("All Phase");
		
		txtBranchID.setValue("");
		txtBranch.setText("All Branches");
	}
	
	private String sqlPhase(String strProject) {
		return "select distinct x.phase, 'PHASE ' || phase \n" +
			"from mf_unit_info x \n" +
			"inner join mf_project y on x.proj_id = y.proj_id \n" +
			"where (x.proj_id = '"+strProject+"' or '"+strProject+"' = '') \n" +
			"order by x.phase";
	}
}
