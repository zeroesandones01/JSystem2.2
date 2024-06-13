package Reports.LoansAndReceivable;

import interfaces._GUI;

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
import java.net.InetAddress;
import java.net.UnknownHostException;
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

public class PagibigClientsStatusReportSummary extends _JInternalFrame
		implements ActionListener, _GUI {

	private static final long serialVersionUID = -4441875545865294L;
	static String title = "PAGIBIG Clients Status Summary";
	Dimension frameSize = new Dimension(650, 350);

	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	
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
	protected static String proj_alias;
	
	
	private static String strComName = getHostName();
	
	public PagibigClientsStatusReportSummary() {
		super(title, true, true, true, true);
		initGUI();
	}

	public PagibigClientsStatusReportSummary(String title) {
		super(title);
		initGUI();
	}

	public PagibigClientsStatusReportSummary(String title, boolean resizable,
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
					pnlCenter.add(panNorth1,gbc);
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
							
							JLabel lblCompany = new JLabel("Company");
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
									
								}
								
								public void keyReleased(KeyEvent e) {
									
								}
								
								public void keyPressed(KeyEvent e) {
									txtCoID.setValue("");
									txtCompany.setText("ALL COMPANY");
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
							
							JLabel lblProject = new JLabel("Project");
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
							//txtProID.setLookupSQL(ProjectSQL(""));
							txtProID.setReturnColumn(0);
							txtProID.setFont(FncGlobal.sizeTextValue);
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										proj_alias = (String)data [1];
										txtProject.setText(data[2].toString());
										txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
									}
								}
							});
							txtProID.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {
									
								}
								
								public void keyReleased(KeyEvent e) {
									
								}
								
								public void keyPressed(KeyEvent e) {
									txtProID.setValue("");
									txtProject.setText("ALL PROJECTS");
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
							
							JLabel lblPhase = new JLabel("Phase");
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
									
								}
								
								public void keyReleased(KeyEvent e) {
									
								}
								
								public void keyPressed(KeyEvent e) {
									txtPhaseID.setValue("");
									txtPhase.setText("ALL PHASES");											
								}
							});
							
						}
						{
							bagOne.weightx = 0;
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
					
					JXPanel panDate = new JXPanel(new GridBagLayout());
					pnlCenter.add(panDate, gbc);
					panDate.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagTwo = new GridBagConstraints();
						bagTwo.fill = GridBagConstraints.BOTH;
						bagTwo.insets = new Insets(5,5,5,5);
						{
							bagTwo.weightx = 1;
							bagTwo.weighty = 1;
							
							bagTwo.gridx = 0;
							bagTwo.gridy = 0;
							
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panDate.add(dteDateFrom,bagTwo );
							dteDateFrom.setDate(null);
							dteDateFrom.setEnabled(true);
							dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteDateFrom.setFont(FncGlobal.sizeTextValue);
						}
					}
				}
			}
	
			{
				
				{
				
					{
						
						JXPanel panStatus = new JXPanel(new BorderLayout(5, 5));
						/*	panNorth2.add(panStatus, BorderLayout.CENTER);	*/
						panStatus.setBorder(JTBorderFactory.createTitleBorder("Status", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							String strCombo[] =	{"All", "56 - TCT Forwarded to RD for Annotation", "17 - Temporary Reserved", "01 - Officially Reserved", "18 - Documents Complete", 
									"31 - Loan Filed (PagIbig)", "43 - Loan Rtnd (First Filing w/ Findings)", "35 - Notice Of Approval (NOA) Released", 
									"56 - TCT Forwarded to RD for Annotation", "46 - TCT Annotated", "48 - Post Approval (Refiling)", "32 - Loan Released"};
							
							cboStatus = new JComboBox(strCombo);
							panStatus.add(cboStatus, BorderLayout.CENTER);
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new GridLayout(1, 2, 5, 5));
				panMain.add(panCenter, BorderLayout.SOUTH);
				panCenter.setPreferredSize(new Dimension(0,30));
				{
					{
						btnPreview = new JButton("Preview");
						panCenter.add(btnPreview);
						btnPreview.setActionCommand("Preview");;
						btnPreview.addActionListener(this);
						btnPreview.setEnabled(true);
						btnPreview.setFont(FncGlobal.sizeControls);
					}
					{
						btnExport = new JButton("Export");
						panCenter.add(btnExport);
						btnExport.setActionCommand("Export");;
						btnExport.addActionListener(this);
						btnExport.setEnabled(false);
						btnExport.setFont(FncGlobal.sizeControls);

					}
				}
			}
		}
		{
			//Setdefaults();
			txtPhaseID.setValue("");
			txtPhase.setText("ALL PHASES");
		}
	}
	
	private static String CompanySQL() {
		System.out.println("SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				   "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ");
		
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			   "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}
	
	public static String ProjectSQL(String strCo){
		System.out.println("SELECT proj_id, proj_alias, proj_name\n" +
				   "FROM mf_project\n" +
				   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
				   "ORDER BY proj_name");
		
		return "SELECT proj_id, proj_alias, proj_name\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
			   "ORDER BY proj_name";
	}
	
	private String sqlPhase(String strProject) {
		System.out.println("select distinct x.phase, 'PHASE ' || phase \n" +
			"from mf_unit_info x \n" +
			"inner join mf_project y on x.proj_id = y.proj_id \n" +
			"where (x.proj_id = '"+strProject+"' or '"+strProject+"' = '') \n" +
			"order by x.phase");
		
		return "select distinct x.phase, 'PHASE ' || phase \n" +
			"from mf_unit_info x \n" +
			"inner join mf_project y on x.proj_id = y.proj_id \n" +
			"where (x.proj_id = '"+strProject+"' or '"+strProject+"' = '' or '"+strProject+"' = 'null') \n" +
			"order by x.phase";
	}
	
	private void Setdefaults() {
		txtCoID.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		
		txtProID.setValue("015");
		txtProject.setText("TERRAVERDE RESIDENCES");
		
		txtPhaseID.setValue("");
		txtPhase.setText("ALL PHASES");
	}
	
    public void actionPerformed(final ActionEvent e) {
		new Thread(new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating report...");
				
				if (e.getActionCommand().equals("Preview")) {
					String strSQL_Zero = "";
					String company_logo = RequestForPayment.sql_getCompanyLogo();
					
					pgUpdate dbExec1 = new pgUpdate();
					dbExec1.executeUpdate("delete from tmp_hdmfstatussummary where host = '"+strComName+"';", true);
					dbExec1.commit();
					
					pgUpdate dbExec2 = new pgUpdate();
					dbExec2.executeUpdate("delete from tmp_hdmfstatussummary_crosstable where host = '"+strComName+"';", true);
					dbExec2.commit();
					
					strSQL_Zero = "insert into tmp_hdmfstatussummary \n" +
						"select *, '"+strComName+"' \n" +
						"from view_hdmf_all_status('"+txtCoID.getValue()+"', '"+txtProID.getValue()+"', '"+txtPhaseID.getValue()+"', '"+dteDateFrom.getDate().toString()+"')";
					
					System.out.println("");
					System.out.println("insert into tmp_hdmfstatussummary :"+strSQL_Zero );
					pgUpdate dbExec3 = new pgUpdate();
					dbExec3.executeUpdate(strSQL_Zero, true);
					dbExec3.commit();
					
					String strSQL_Alpha = "insert into tmp_hdmfstatussummary_crosstable \n" +
							"select *, '"+strComName+"' from view_hdmf_status_summary_primary ('"+strComName+"')where project_phase ~* '"+proj_alias+"' ";
					
					System.out.println("");
					System.out.println("proj_alias: "+ proj_alias);
					System.out.println("insert into  tmp_hdmfstatussummary_crosstable :"+strSQL_Alpha );
					pgUpdate dbExec4 = new pgUpdate();
					dbExec4.executeUpdate(strSQL_Alpha, true);
					dbExec4.commit();
					
					String strSQL_Bravo = "SELECT * FROM ( \n" +
					"SELECT * FROM ( \n" +
					"select x.status::varchar(50) as group_zero, project_phase as group_alpha, 'A. Units'::varchar(20) as group_bravo, \n" +
					"x.group::varchar(20) as group_delta, x.status_sequence, \n" +
					"(CASE WHEN (select sum(y.count) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) is null THEN 0 ELSE \n" +
					"(select sum(y.count) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) END) as group_charlie \n" +
					"FROM tmp_hdmfstatussummary_crosstable x \n" +
					"UNION \n" +
					"select x.status::varchar(50) as group_zero, project_phase as group_alpha, 'B. L.A.'::varchar(20) as group_bravo, \n" +
					"x.group::varchar(20) as group_delta, x.status_sequence, \n" +
					"(CASE WHEN (select sum(y.loan) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) is null THEN 0 ELSE \n" +
					"(select sum(y.loan) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) END) as group_charlie \n" +
					"FROM tmp_hdmfstatussummary_crosstable x \n" +
					"UNION \n" +
					"select x.status::varchar(50) as group_zero, project_phase as group_alpha, 'C. Pctg(%)'::varchar(20) as group_bravo, \n" +
					"x.group::varchar(20) as group_delta, x.status_sequence, \n" +
					"(CASE WHEN (select sum(y.pctg)::numeric(19, 2) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) is null THEN 0 ELSE \n" +
					"(select sum(y.pctg)::numeric(19, 2) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) END) as group_charlie \n" +
					"FROM tmp_hdmfstatussummary_crosstable x \n" +
					") a \n" +
					"union ALL \n" + 
					"SELECT * \n" +
					"FROM \n" +
					"( \n" +
					"select x.status::varchar(50) as group_zero, 'Z. Total' as group_alpha, 'A. Units'::varchar(20) as group_bravo, \n" +
					"x.group::varchar(20) as group_delta, x.status_sequence, \n" +
					"(CASE WHEN (select sum(y.count) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) is null THEN 0 ELSE \n" +
					"(select sum(y.count) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) END) as group_charlie \n" +
					"FROM tmp_hdmfstatussummary_crosstable x \n" +
					"UNION \n" +
					"select x.status::varchar(50) as group_zero, 'Z. Total' as group_alpha, 'B. L.A.'::varchar(20) as group_bravo, \n" +
					"x.group::varchar(20) as group_delta, x.status_sequence, \n" +
					"(CASE WHEN (select sum(y.loan) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) is null THEN 0 ELSE \n" +
					"(select sum(y.loan) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) END) as group_charlie \n" +
					"FROM tmp_hdmfstatussummary_crosstable x \n" +
					"UNION \n" +
					"select x.status::varchar(50) as group_zero, 'Z. Total' as group_alpha, 'C. Pctg(%)'::varchar(20) as group_bravo, \n" +
					"x.group::varchar(20) as group_delta, x.status_sequence, \n" +
					"(CASE WHEN (select sum(y.pctg)::numeric(19, 2) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) is null THEN 0 ELSE \n" +
					"(select sum(y.pctg)::numeric(19, 2) from tmp_hdmfstatussummary_crosstable y where y.status = x.status and y.project_phase = x.project_phase) END) as group_charlie \n" +
					"FROM tmp_hdmfstatussummary_crosstable x \n" +
					") a \n" +
					") a \n" +
					"order by a.status_sequence, a.group_alpha, a.group_bravo";
					
					System.out.println("");
					System.out.println("strSQL_Bravo: " + strSQL_Bravo);
					
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("01_Company", txtCompany.getText());
					mapParameters.put("02_Date", FncGlobal .getDateSQL());
					mapParameters.put("03_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
					mapParameters.put("04_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
					mapParameters.put("05_Host", strComName);
					FncReport.generateReport("/Reports/rpt_hdmf_status_summary.jasper", "Pagibig Client Status Report Summary", "", mapParameters);
					
				} else if (e.getActionCommand().equals("Export")) {

				}
				FncGlobal.stopProgress();
			}
		}).start();
    }
    
	private static String getHostName() {
        InetAddress ip;
        String hostname = null;
        
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
 
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }
        
		return hostname;
	}
}
