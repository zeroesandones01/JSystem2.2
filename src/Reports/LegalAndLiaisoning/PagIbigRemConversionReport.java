package Reports.LegalAndLiaisoning;

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

public class PagIbigRemConversionReport extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = 4824765883993218355L;
	static String title = "Pag-Ibig Rem Conversion Report";
	Dimension frameSize = new Dimension(530, 349);

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
	
	public PagIbigRemConversionReport() {
		super(title, false, true, true, true);
		initGUI(); 
	}

	public PagIbigRemConversionReport(String title) {
		super(title);
		initGUI();
	}

	public PagIbigRemConversionReport(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
					gbc.weightx =1;
					gbc.weighty = 2;
					
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.gridheight = 2;
					
					JXPanel panNorth1 = new JXPanel(new GridBagLayout());
					pnlCenter.add(panNorth1,gbc);
					panNorth1.setBorder(JTBorderFactory.createTitleBorder("Project and Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagOne = new GridBagConstraints();
						bagOne.fill = GridBagConstraints.BOTH;
						bagOne.insets = new Insets(5, 3, 5, 3);
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
							txtCoID.setLookupSQL(SQL_COMPANY());
							txtCoID.setReturnColumn(0);
							txtCoID.setFont(FncGlobal.sizeTextValue);
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText(data[1].toString());
										txtProID.setLookupSQL(SQL_PROJECT(txtCoID.getValue()));
										txtProID.setValue("");
										
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
						//	txtProID.setLookupSQL(ProjectSQL(""));
							txtProID.setReturnColumn(0);
							txtProID.setFont(FncGlobal.sizeTextValue);
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtProject.setText(data[2].toString());
										txtPhaseID.setLookupSQL(SQL_PHASE(txtProID.getValue()));
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
							panNorth1.add(txtProject, bagOne);
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
					gbc.weightx =1;
					gbc.weighty = 1;
					
					gbc.gridx = 0;
					gbc.gridy = 3;
					gbc.gridheight = 1;
					
					JXPanel panDate = new JXPanel(new GridBagLayout());
					pnlCenter.add(panDate, gbc);
					panDate.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagTwo = new GridBagConstraints();
						bagTwo.fill = GridBagConstraints.BOTH;
						bagTwo.insets = new Insets(5, 3, 5, 3);
						{
							bagTwo.weightx = 0;
							bagTwo.weighty = 1;
							
							bagTwo.gridx = 0;
							bagTwo.gridy = 0;
							
							JLabel lblFrom = new JLabel("From: "); 
							panDate.add(lblFrom, bagTwo); 
							lblFrom.setHorizontalAlignment(JTextField.LEADING);
							lblFrom.setFont(FncGlobal.sizeLabel);
						}
						{
							bagTwo.weightx = 1;
							bagTwo.weighty = 1;
							
							bagTwo.gridx = 1;
							bagTwo.gridy = 0;
							
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panDate.add(dteDateFrom,bagTwo);
							dteDateFrom.setDate(null);
							dteDateFrom.setEnabled(true);
							dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.GetString("select '2017-01-01'::date::varchar")));
							dteDateFrom.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagTwo.weightx = 0;
							bagTwo.weighty = 1;
							
							bagTwo.gridx = 2;
							bagTwo.gridy = 0;
							
							JLabel lblTo = new JLabel("To: "); 
							panDate.add(lblTo, bagTwo); 
							lblTo.setHorizontalAlignment(JTextField.CENTER);
							lblTo.setFont(FncGlobal.sizeLabel);
						}
						{
							bagTwo.weightx = 1;
							bagTwo.weighty = 1;
							
							bagTwo.gridx = 3;
							bagTwo.gridy = 0;
							
							dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panDate.add(dteDateTo,bagTwo);
							dteDateTo.setDate(null);
							dteDateTo.setEnabled(true);
							dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteDateTo.setFont(FncGlobal.sizeTextValue);
						}
					}
				}
			/*	{
					gbc.weightx =1;
					gbc.weighty = 1;
					
					gbc.gridx = 0;
					gbc.gridy = 4;
					
					JXPanel panReport = new JXPanel(new GridBagLayout());
					pnlCenter.add(panReport,gbc);
					panReport.setBorder(JTBorderFactory.createTitleBorder("Report Name", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagThree = new GridBagConstraints();
						bagThree.fill = GridBagConstraints.BOTH;
						bagThree.insets = new Insets(5, 3, 5, 3);
						{
							bagThree.weightx =1;
							bagThree.weighty = 1;
							
							bagThree.gridx = 0;
							bagThree.gridy = 0;
							
							cboReport = new JComboBox();
							panReport.add(cboReport,bagThree);
							
							pgSelect db = new pgSelect(); 
							db.select("select other_report_id::varchar || ' - ' || report_name from mf_hdmf_other_reports");
							
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
							cboReport.setFont(FncGlobal.sizeTextValue);
						}
					}
				}*/
			}
			
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.SOUTH);
				{
					JXPanel panButton = new JXPanel(new GridLayout(1, 2, 5, 5));
					panCenter.add(panButton, BorderLayout.CENTER);
					panButton.setPreferredSize(new Dimension(0,30));
					{
						{
							btnPreview = new JButton("Preview");
							panButton.add(btnPreview);
							btnPreview.setActionCommand("Preview");
							btnPreview.addActionListener(this);
							btnPreview.setEnabled(true);
							btnPreview.setFont(FncGlobal.sizeControls);
						}
						{
							btnExport = new JButton("Export");
							panButton.add(btnExport);
							btnExport.setActionCommand("Export");;
							btnExport.addActionListener(this);
							btnExport.setEnabled(false);
							btnExport.setFont(FncGlobal.sizeControls);
						}	
					}
				}	
			}
		}
		
	//	Setdefaults();
	}

	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;
		
		/*	Company Default	*/
		strCount = FncGlobal.GetString("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtCoID.setText("");
			txtCompany.setText("All Companies");
		} else {
			txtCoID.setValue(FncGlobal.GetString("SELECT co_id FROM mf_company LIMIT 1"));
			txtCompany.setText(FncGlobal.GetString("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		}
		
		txtCoID.setValue("");
		txtCompany.setText(FncGlobal.GetString("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		
		/*	Project Default	*/
		txtProID.setText("");
		txtProject.setText("All Projects");
		strCount = FncGlobal.GetString("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtProID.setText("");
			txtProject.setText("All Projects");
		} else {
			txtProID.setValue(FncGlobal.GetString("SELECT proj_id FROM mf_project LIMIT 1"));
			txtProject.setText(FncGlobal.GetString("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProID.getText()+"'"));
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
			
			Generate();
			
		} else if (e.getActionCommand().equals("Export")) {

		}
    }
	
	private void Generate() {
		String strStatus = "";
		String strProject = "";
		String strPhase = "";
		
		Integer strYearFrom = FncGlobal.GetInteger("select date_part('year', '"+dteDateFrom.getDate()+"'::date)::int");
		Integer strYearTo = FncGlobal.GetInteger("select date_part('year', '"+dteDateTo.getDate()+"'::date)::int");
		
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
		
		
		if (df.format(dteDateFrom.getDate()).equals(df.format(dteDateTo.getDate()))) {
			strDate = "As Of: " + strDate;
		} else {
			strDate = "As Of: " + df.format(dteDateFrom.getDate()) + " to " + df.format(dteDateTo.getDate());
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strTo = (String) sdf.format(dteDateTo.getDate());
		
		String company_logo = sql_getCompanyLogo(txtCoID.getValue());
		
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();  
		
			mapParameters.put("co_id", txtCoID.getText());
			mapParameters.put("co_name", txtCompany.getText());
			mapParameters.put("dateFrom", dteDateFrom.getText());
			mapParameters.put("dateTo", dteDateTo.getText());
			mapParameters.put("dateParam", strDate);
			mapParameters.put("project_id", strProject);
			mapParameters.put("user_name", UserInfo.EmployeeCode);
			mapParameters.put("project", txtProject.getText());
			mapParameters.put("phase", strPhase);
			mapParameters.put("emp_code", UserInfo.EmployeeCode);
			
			
			FncReport.generateReport("/Reports/rpt_hdmf_rem_conversion_movement_reportLLD.jasper", "Pag-Ibig REM Conversion Report", "", mapParameters);
			

	}
	
	private String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}
}
