package Reports.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import org.apache.poi.ss.formula.functions.Now;
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

public class propertyManagementReports extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -3667322035844162984L;
	static String title = "Property Management Reports";
	Dimension frameSize = new Dimension(650, 477);

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
	private _JDateChooser dteDue;
	private _JDateChooser dtePayment;
	
	private JButton btnPreview;
	private JButton btnExport;
	
	private JComboBox cboReport;
	private JComboBox cboFilter;
	private JComboBox cboSort;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public propertyManagementReports() {
		super(title, true, true, true, true);
		initGUI(); 
	}

	public propertyManagementReports(String title) {
		super(title);
		initGUI(); 
	}

	public propertyManagementReports(String title, boolean resizable, boolean closable, boolean maximizable,
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
			JXPanel pnlCenter = new JXPanel(new GridBagLayout());
			panMain.add(pnlCenter,BorderLayout.CENTER);
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				{
					gbc.weightx = 1;
					gbc.weighty = 1;
					
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
							txtPhaseID.addFocusListener(new FocusListener() {
								
								@Override
								public void focusLost(FocusEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void focusGained(FocusEvent e) {
									txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
									
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
					gbc.weighty = 1;
					
					gbc.gridx = 0;
					gbc.gridy = 3;
					gbc.gridheight = 1;
					
					JXPanel panReport = new JXPanel(new GridBagLayout());
					pnlCenter.add(panReport, gbc);
					panReport.setBorder(JTBorderFactory.createTitleBorder("Report Name", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagTwo = new GridBagConstraints();
						bagTwo.fill = GridBagConstraints.BOTH;
						bagTwo.insets = new  Insets(5, 3, 5, 3);
						{
							bagTwo.weightx = 1;
							bagTwo.weighty = 1;
							
							bagTwo.gridx = 0;
							bagTwo.gridy = 0;
							
							cboReport = new JComboBox();
							panReport.add(cboReport,bagTwo);
							
							pgSelect db = new pgSelect(); 
							db.select("select other_report_id::varchar || ' - ' || report_name from mf_pmd_other_reports");
							
							if (db.isNotNull()) {
								for (int x = 0; x < db.getRowCount(); x++) {
									cboReport.addItem((String) db.getResult()[x][0].toString());
								}
								cboReport.addItem((String) "9 - Notices of Assumed Turn Over");
							}
							cboReport.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									if (cboReport.getSelectedIndex()==3) {
										ControlMode(false);
									} else {
										ControlMode(true); 
									}
									
									if (cboReport.getSelectedIndex()==2 || cboReport.getSelectedIndex()==1) {
										dteDateFrom.setEnabled(true);
										dteDateTo.setEnabled(true);
										cboSort.setEnabled(true);
									}
									
									if (cboReport.getSelectedIndex()==8) {
										dteDateFrom.setEnabled(false);
										dteDateTo.setEnabled(false);
									}
									if(UserInfo.EmployeeCode.equals("901108")) {
										if(cboReport.getSelectedIndex() == 1 || cboReport.getSelectedIndex() == 2) {
											btnPreview.setEnabled(true);
										}else {
											btnPreview.setEnabled(false);
										}
										
									}
								}
							});
							cboReport.setFont(FncGlobal.sizeTextValue);
						}
					}
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 1;
					
					gbc.gridx = 0;
					gbc.gridy = 4;
					
					JXPanel panDate = new JXPanel(new GridBagLayout());
					pnlCenter.add(panDate, gbc);
					panDate.setBorder(JTBorderFactory.createTitleBorder("Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagThree = new GridBagConstraints();
						bagThree.fill = GridBagConstraints.BOTH;
						bagThree.insets = new  Insets(5, 3, 5, 3);
						{
							bagThree.weightx = 0.5;
							bagThree.weighty = 1;

							bagThree.gridx = 0;
							bagThree.gridy = 0;
							
							
							JLabel lblFrom = new JLabel("From: "); 
							panDate.add(lblFrom, bagThree); 
							lblFrom.setHorizontalAlignment(JTextField.LEADING);
							lblFrom.setFont(FncGlobal.sizeLabel);
						}
						{
							bagThree.weightx = 1;
							bagThree.weighty = 1;

							bagThree.gridx = 1;
							bagThree.gridy = 0;

							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panDate.add(dteDateFrom,bagThree);
							dteDateFrom.setDate(null);
							dteDateFrom.setEnabled(true);
							dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteDateFrom.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagThree.weightx = 0;
							bagThree.weighty = 1;

							bagThree.gridx = 2;
							bagThree.gridy = 0;

							JLabel lblTo = new JLabel("To: "); 
							panDate.add(lblTo, bagThree); 
							lblTo.setHorizontalAlignment(JTextField.CENTER);
							lblTo.setFont(FncGlobal.sizeLabel);
						}
						{
							bagThree.weightx = 1;
							bagThree.weighty = 1;

							bagThree.gridx = 3;
							bagThree.gridy = 0;

							dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panDate.add(dteDateTo,bagThree);
							dteDateTo.setDate(null);
							dteDateTo.setEnabled(true);
							dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteDateTo.setFont(FncGlobal.sizeTextValue);
						}
					}
					
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 1;
					
					gbc.gridx = 0;
					gbc.gridy = 5;
					
					JXPanel panDuePay = new JXPanel(new GridBagLayout());
					pnlCenter.add(panDuePay,gbc);
					panDuePay.setBorder(JTBorderFactory.createTitleBorder("Cut-Off", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagFour = new GridBagConstraints();
						bagFour.fill = GridBagConstraints.BOTH;
						bagFour.insets = new  Insets(5, 3, 5, 3);
						{
							bagFour.weightx = 0.5;
							bagFour.weighty = 1;

							bagFour.gridx = 0;
							bagFour.gridy = 0;
							
							
							JLabel lblDue = new JLabel("Due: "); 
							panDuePay.add(lblDue, bagFour); 
							lblDue.setHorizontalAlignment(JTextField.LEADING);
							lblDue.setFont(FncGlobal.sizeLabel);
						}
						{
							bagFour.weightx = 1;
							bagFour.weighty = 1;

							bagFour.gridx = 1;
							bagFour.gridy = 0;

							dteDue = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panDuePay.add(dteDue, bagFour);
							dteDue.setDate(null);
							dteDue.setEnabled(true);
							dteDue.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteDue.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagFour.weightx = 0;
							bagFour.weighty = 1;

							bagFour.gridx = 2;
							bagFour.gridy = 0;

							JLabel lblPayment = new JLabel("Pmt: "); 
							panDuePay.add(lblPayment, bagFour); 
							lblPayment.setHorizontalAlignment(JTextField.CENTER);
							lblPayment.setFont(FncGlobal.sizeLabel);
						}
						{
							bagFour.weightx = 1;
							bagFour.weighty = 1;

							bagFour.gridx = 3;
							bagFour.gridy = 0;

							dtePayment = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panDuePay.add(dtePayment,bagFour);
							dtePayment.setDate(null);
							dtePayment.setEnabled(true);
							dtePayment.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dtePayment.setFont(FncGlobal.sizeTextValue);
						}
					}
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 1;
					
					gbc.gridx = 0;
					gbc.gridy = 6;
					
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
							
							JXPanel panFilter = new JXPanel(new GridBagLayout()); 
							pnlCon.add(panFilter, bagCon); 
							panFilter.setBorder(JTBorderFactory.createTitleBorder("Filter", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								GridBagConstraints conFilter = new GridBagConstraints();
								conFilter.fill = GridBagConstraints.BOTH;
								conFilter.insets = new Insets(5,3,5,3);
								{
									conFilter.weightx = 1;
									conFilter.weighty = 1;

									conFilter.gridx= 0;
									conFilter.gridy = 0; 
									
									String[] items = {
											"turned-over; moved-in",
											"turned-over; not moved-in",
											"not turned-over; moved-in",
											"not turned-over; not moved-in" 
										};
										
										cboFilter = new JComboBox(items); 
										panFilter.add(cboFilter, conFilter); 
										cboFilter.setFont(FncGlobal.sizeTextValue);
										//cboFilter.setEnabled(true);
								}
							}
						}
						{
							bagCon.weightx = 1;
							bagCon.weighty = 1;

							bagCon.gridx = 1;
							bagCon.gridy = 0;
							
							JXPanel panSort = new JXPanel(new GridBagLayout()); 
							pnlCon.add(panSort, bagCon); 
							panSort.setBorder(JTBorderFactory.createTitleBorder("Sort", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								GridBagConstraints conSort = new GridBagConstraints();
								conSort.fill = GridBagConstraints.BOTH;
								conSort.insets = new Insets(5,3,5,3);
								{
									conSort.weightx = 1;
									conSort.weighty = 1;

									conSort.gridx= 0;
									conSort.gridy = 0;
									
									String[] items = {
											"Name",
											"Unit",
											"Balance" 
										};
									
									cboSort = new JComboBox(items); 
									panSort.add(cboSort, conSort);
									cboSort.setFont(FncGlobal.sizeTextValue);
									cboSort.setSelectedIndex(1);
								}
							}
						}
					}
				}
				
			}
			
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.SOUTH);
				{
					JXPanel panButton = new JXPanel(new GridLayout(1, 2, 5, 5));
					panCenter.add(panButton, BorderLayout.CENTER);
					panButton.setPreferredSize(new Dimension(0, 30));
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
		
		Setdefaults();
		ControlMode(true); 
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
			txtProID.setValue(null);
			txtProject.setText("");
		}
		
		txtCoID.setValue("02");
		txtCompany.setText(GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		
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
			   "AND status_id = 'A'\n" +
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
		if (!dteDateFrom.getDate().toString().equals(dteDateTo.getDate().toString())) {
			strDate = df.format(dteDateFrom.getDate()) + " to " + df.format(dteDateTo.getDate());
		} else {
			strDate = df.format(dteDateTo.getDate());
		}
		
		strDate = "As Of: " + strDate;
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();  
		mapParameters.put("co_name", txtCompany.getText());
		mapParameters.put("dateParam", strDate);
		mapParameters.put("user_name", UserInfo.FullName);
		mapParameters.put("project", txtProject.getText());
		
		mapParameters.put("01_coid", txtCoID.getValue());
		mapParameters.put("02_proj_id", strProject);
		mapParameters.put("03_phase", strPhase);
		mapParameters.put("04_dteFrom", dteDateFrom.getDate());
		mapParameters.put("05_dteTo", dteDateTo.getDate());
		

		if (strReport.equals("1 - Water Consumption")) { 
			pgUpdate dbExec = new pgUpdate();
			dbExec.executeUpdate("delete from tmpwaterconsumption", true); 
			dbExec.commit();
			
			dbExec = new pgUpdate();
			dbExec.executeUpdate("insert into tmpwaterconsumption \n" +
					"select * \n" +
					"from  view_pmd_water_consumption('"+txtCoID.getValue()+"', '"+strProject+"', '"+strPhase+"', '"+dteDateFrom.getDate().toString()+"','"+dteDateTo.getDate().toString()+"')", true);
			dbExec.commit();
			
			FncReport.generateReport("/Reports/rpt_pmd_waterConsumption.jasper", strReport, "", mapParameters);
		} else if (strReport.equals("2 - List of Turned Over Units")) {
			mapParameters.put("09_sort", cboSort.getSelectedIndex());
			FncReport.generateReport("/Reports/rpt_pmd_ListOfTurnedOverUnits.jasper", strReport, "", mapParameters);
		} else if (strReport.equals("3 - List of Move-In Units")) {
			mapParameters.put("09_sort", cboSort.getSelectedIndex());
			FncReport.generateReport("/Reports/rpt_pmd_ListOfMovedInUnits.jasper", strReport, "", mapParameters);
		} else if (strReport.equals("4 - Utility Balances")) {
			mapParameters.put("06_due_cut_off", df.format(dteDue.getDate()).toString());
			mapParameters.put("07_pay_cut_off", df.format(dtePayment.getDate()).toString());
			mapParameters.put("08_filter", cboFilter.getSelectedIndex());
			mapParameters.put("09_sort", cboSort.getSelectedIndex());
			FncReport.generateReport("/Reports/rpt_pmd_UtilityBalances.jasper", strReport, "", mapParameters);
		} else if (strReport.equals("5 - Water Payment Collection")) {
			//added: 5/23/2019 - Refer to DCRF #985
			mapParameters.put("co_id", txtCoID.getValue());
			mapParameters.put("proj_id", strProject);
			mapParameters.put("phase_no", strPhase);
			mapParameters.put("date_fr", dteDateFrom.getDate());
			mapParameters.put("date_to", dteDateTo.getDate());
			FncReport.generateReport("/Reports/rptWaterCollectionSummary.jasper", strReport, "", mapParameters); 
		} else if (strReport.equals("6 - Garbage Fee Collection")) {
			System.out.println("Dumaan dito sa garbage collection");
			System.out.printf("Value of Co_id: %s%n", txtCoID.getValue());
			System.out.printf("Value of Proj ID: %s%n", strProject);
			System.out.printf("Value of Phase No: %s%n", strPhase);
			System.out.printf("Value of dtefrom: %s%n", dteDateFrom.getDate());
			System.out.printf("Value of dteto: %s%n", dteDateTo.getDate());
			
			mapParameters.put("co_id", txtCoID.getValue());
			mapParameters.put("proj_id", strProject);
			mapParameters.put("phase_no", strPhase);
			mapParameters.put("date_fr", dteDateFrom.getDate());
			mapParameters.put("date_to", dteDateTo.getDate());
			FncReport.generateReport("/Reports/rptGarbageCollectionSummary.jasper", strReport, "", mapParameters); 
		} else if (strReport.equals("7 - Monitoring of Accounts with PAGIBIG Inspection")) {
			mapParameters.put("date_fr", dteDateFrom.getDate());
			mapParameters.put("date_to", dteDateTo.getDate());
			FncReport.generateReport("/Reports/rpt_pmd_inspection.jasper", strReport, "", mapParameters); 
		} else if (strReport.equals("8 - Under Renovation")) {
			mapParameters.put("date_fr", dteDateFrom.getDate());
			mapParameters.put("date_to", dteDateTo.getDate());
			FncReport.generateReport("/Reports/rpt_pmd_renovation.jasper", strReport, "", mapParameters); 
		} else if (strReport.equals("9 - Notices of Assumed Turn Over")) {  
			/*System.out.println("co_name " + txtCompany.getText());
			System.out.println("dateParam " + strDate);
			System.out.println("user_name " + UserInfo.FullName);
			System.out.println("project " + txtProject.getText());
			
			System.out.println("01_coid " + txtCoID.getValue());
			System.out.println("02_proj_id " + strProject);
			System.out.println("03_phase " + strPhase);
			System.out.println("04_dteFrom " + dteDateFrom.getDate());
			System.out.println("05_dteTo " + dteDateTo.getDate());*/
			FncReport.generateReport("/Reports/rpt_pmd_assumed_turn_over.jasper", strReport, "", mapParameters);
		}
	} 
	
	private void ControlMode(Boolean blnDo) {
		dteDateFrom.setEnabled(blnDo);
		dteDateTo.setEnabled(blnDo);
		dteDue.setEnabled(!blnDo);
		dtePayment.setEnabled(!blnDo);
		
		cboFilter.setEnabled(!blnDo);
		cboSort.setEnabled(!blnDo);
		if(UserInfo.EmployeeCode.equals("901108")) {
			btnPreview.setEnabled(false);
		}
	}
	
	private void ControlModeSpecial() {
		dteDateFrom.setEnabled(false);
		dteDateTo.setEnabled(false);
		dteDue.setEnabled(false);
		dtePayment.setEnabled(false);
		
		cboFilter.setEnabled(false);
		cboSort.setEnabled(false);
	}
}
