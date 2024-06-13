package Reports.BiddingandAwarding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXApplet;
import org.jdesktop.swingx.JXPanel;

import Accounting.Collections.checkStatusMonitoring_pdcWarehousing;
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
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_weeklyContractorsAndAwardingMonitoring;

public class weeklyContractorsAndAwardingMonitoring extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -6168486534702598001L;
	static String title = "Weekly Contractors and Awarding Monitoring";
	Dimension frameSize = new Dimension(600, 550);
	
	private _JLookup txtCoID;
	private _JLookup txtProjID;
	private _JLookup txtPhaseID;
	
	private JTextField txtCo;
	private JTextField txtProj;
	private JTextField txtPhase;
	private JTextField txtSearch;
	
	private JButton btnSave;
	private JButton btnPreview; 
	
	private JCheckBox chkAll;
	private JCheckBox chkWithOnGoing; 
	
	private _JDateChooser dteDate; 
	
	private JScrollPane scrWCAA;
	private model_weeklyContractorsAndAwardingMonitoring modelWCAA;	
	public static _JTableMain tblWCAA;
	public static JList rowWCAA;
	
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	
	public weeklyContractorsAndAwardingMonitoring() {
		super(title, false, true, false, true);
		initGUI();
	}

	public weeklyContractorsAndAwardingMonitoring(String title) {
		super(title);
		initGUI();
	}

	public weeklyContractorsAndAwardingMonitoring(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5)); 
		getContentPane().add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new GridLayout(2, 1, 5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 120));
				{
					{
						JPanel panDiv0 = new JPanel(new BorderLayout(5, 5));
						panPage.add(panDiv0); 
						panDiv0.setBorder(JTBorderFactory.createTitleBorder("Project", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								txtProjID = new _JLookup(""); 
								panDiv0.add(txtProjID, BorderLayout.LINE_START); 
								txtProjID.setLookupSQL(lookupMethods.getProject(""));
								txtProjID.setReturnColumn(0);
								txtProjID.setFocusable(false);
								txtProjID.setPreferredSize(new Dimension(125, 0));
								txtProjID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
										
										if (data!=null) {
											txtProj.setText((String) data[1]);
										}
									}
								});
							}
							{
								txtProj = new JTextField(""); 
								panDiv0.add(txtProj, BorderLayout.CENTER); 
								txtProj.setHorizontalAlignment(JTextField.CENTER);
								txtProj.setEditable(false);
							}
						}
					}
					{
						JPanel panDiv1 = new JPanel(new GridLayout(1, 2, 5, 5));
						panPage.add(panDiv1); 
						{
							{
								JXPanel panAsOf = new JXPanel(new BorderLayout(5, 5)); 
								panDiv1.add(panAsOf); 
								panAsOf.setBorder(JTBorderFactory.createTitleBorder("As of", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									panAsOf.add(dteDate, BorderLayout.CENTER);
									dteDate.getCalendarButton().setVisible(true);
									dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
							{
								JXPanel panInclude = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panDiv1.add(panInclude); 
								panInclude.setBorder(JTBorderFactory.createTitleBorder("Include", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										chkAll = new JCheckBox("All"); 
										panInclude.add(chkAll); 
										chkAll.setHorizontalAlignment(JCheckBox.LEADING);
										chkAll.setFont(font11);
										chkAll.setSelected(true);
										chkAll.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												if (chkAll.isSelected()) {
													chkWithOnGoing.setSelected(false);											
												} else {
													chkWithOnGoing.setSelected(true);
												}
											}
										});
									}
									{
										chkWithOnGoing = new JCheckBox("With On-going"); 
										panInclude.add(chkWithOnGoing); 
										chkWithOnGoing.setHorizontalAlignment(JCheckBox.LEADING);
										chkWithOnGoing.setFont(font11);
										chkWithOnGoing.setSelected(false);
										chkWithOnGoing.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												if (chkWithOnGoing.isSelected()) {
													chkAll.setSelected(false);											
												} else {
													chkAll.setSelected(true);
												}
											}
										});
									}
								}						
							}							
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(2, 2)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				panCenter.setBorder(JTBorderFactory.createTitleBorder("Contractors", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panGrid = new JXPanel(new BorderLayout(2, 2)); 
						panCenter.add(panGrid, BorderLayout.CENTER); 
						{
							scrWCAA = new JScrollPane();
							panGrid.add(scrWCAA, BorderLayout.CENTER);
							scrWCAA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							{
								modelWCAA = new model_weeklyContractorsAndAwardingMonitoring();
								modelWCAA.setEditable(false);
								
								tblWCAA = new _JTableMain(modelWCAA);
								tblWCAA.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent event) {
										if (!event.getValueIsAdjusting()) {
											btnSave.setEnabled(true);
										}
									}
								});
								
								rowWCAA = tblWCAA.getRowHeader();
								scrWCAA.setViewportView(tblWCAA);
								
								tblWCAA.getColumnModel().getColumn(0).setPreferredWidth(286);
								tblWCAA.getColumnModel().getColumn(1).setPreferredWidth(56);
								tblWCAA.getColumnModel().getColumn(2).setPreferredWidth(56);
								tblWCAA.getColumnModel().getColumn(3).setPreferredWidth(56);
								tblWCAA.getColumnModel().getColumn(4).setPreferredWidth(56);
								tblWCAA.getColumnModel().getColumn(5).setPreferredWidth(75);
								tblWCAA.setSortable(false);

								tblWCAA.hideColumns("entity_id");
								
								rowWCAA = tblWCAA.getRowHeader();
								rowWCAA.setModel(new DefaultListModel());
								scrWCAA.setRowHeaderView(rowWCAA);
								scrWCAA.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
				}
			}
			{
				JXPanel panButton = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panButton, BorderLayout.PAGE_END);
				panButton.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnPreview = new JButton("Preview");  
						panButton.add(btnPreview, BorderLayout.LINE_START);
						btnPreview.setPreferredSize(new Dimension(100, 0));
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent act) {
								Map<String, Object> mapParameters = new HashMap<String, Object>();
								mapParameters.put("username", FncGlobal.GetString("select y.entity_name from em_employee x inner join rf_entity y on x.entity_id = y.entity_id where x.emp_code = '"+UserInfo.EmployeeCode+"'"));
								mapParameters.put("date", FncGlobal.GetDate("select '"+dteDate.getDate()+"'::date"));
								mapParameters.put("option", (chkAll.isSelected()?Integer.parseInt("0"):Integer.parseInt("1")));
								mapParameters.put("project", txtProj.getText());
								mapParameters.put("project_id", txtProjID.getValue());
								FncReport.generateReport("/Reports/rpt_ntp_contractorsandawarding_monitoring.jasper", "Contractors and Awarding Monitoring", "", mapParameters); 
							}
						});
					}
					
					{
						txtSearch = new JTextField(">> search contractor name <<"); 
						panButton.add(txtSearch, BorderLayout.CENTER); 
						txtSearch.setHorizontalAlignment(JTextField.CENTER);
						txtSearch.setFont(font11);
						txtSearch.setForeground(Color.GRAY);
						txtSearch.addFocusListener(new FocusListener() {
							public void focusLost(FocusEvent e) {
								if (txtSearch.getText().equals("")) {
									txtSearch.setText(">> search contractor name <<");
									txtSearch.setForeground(Color.GRAY);	
								}
							}
							
							public void focusGained(FocusEvent e) {
								txtSearch.setText("");
								txtSearch.setForeground(Color.BLACK);
							}
						});
						txtSearch.getDocument().addDocumentListener(new DocumentListener() {
							public void removeUpdate(DocumentEvent e) {
								focusonthis(); 
							}
							
							public void insertUpdate(DocumentEvent e) {
								focusonthis();  
							}
							
							public void changedUpdate(DocumentEvent e) {
								focusonthis(); 
							}
							
							void focusonthis() {
								try {
									if (txtSearch.getText().equals("")) {
										tblWCAA.changeSelection(0, 0, false, false);
									} else {
										for (int x=0; x<tblWCAA.getRowCount(); x++) {
											if (tblWCAA.getValueAt(x, 0).toString().toLowerCase().contains(txtSearch.getText()) ||
													tblWCAA.getValueAt(x, 0).toString().substring(0, txtSearch.getText().length()).equals(txtSearch.getText())) {
												tblWCAA.changeSelection(x, 0, false, false);
												x=tblWCAA.getRowCount(); 
											}
										}
									}
								} catch (StringIndexOutOfBoundsException ex) {
									
								}
							}
						});
					}
					
					{
						btnSave = new JButton("Save"); 
						panButton.add(btnSave, BorderLayout.LINE_END);
						btnSave.setPreferredSize(new Dimension(100, 0));
						btnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								pgUpdate dbexec = new pgUpdate(); 
								String strCAP = ""; 
								String strPCAB = "";
								String strOtherFactor = ""; 
								Boolean blnReliable = false; 
								
								try {
									if (JOptionPane.showConfirmDialog(null, "Save set values?", "Confirm", 
											JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
										
										for (int x=0; x<modelWCAA.getRowCount(); x++) {
											strCAP = ((modelWCAA.getValueAt(x, 3).toString().isEmpty())?"0":(String) modelWCAA.getValueAt(x, 3).toString());
											strPCAB = ((modelWCAA.getValueAt(x, 2).toString().isEmpty())?"":(String) modelWCAA.getValueAt(x, 2).toString()); 
											strOtherFactor = ((modelWCAA.getValueAt(x, 4).toString().isEmpty())?"0":(String) modelWCAA.getValueAt(x, 4).toString());
											blnReliable = (Boolean) modelWCAA.getValueAt(x, 1); 
											
											dbexec.executeUpdate("update rf_contractor_sup_details\n" + 
													"set reliable = "+blnReliable+", maxcap_prev = maxcap_curr, \n" + 
													"maxcap_curr = '"+strCAP+"'::int, pcab = '"+strPCAB+"', other_factor = '"+strOtherFactor+"'::numeric(19, 2) \n" + 
													"where con_name = '"+modelWCAA.getValueAt(x, 5).toString()+"'", false);	
										}
										
										dbexec.commit();
										
										Load();
										JOptionPane.showMessageDialog(null, "Values saved!");
									}
									
									btnSave.setEnabled(false);
								} catch (ArrayIndexOutOfBoundsException ex) {
									btnSave.setEnabled(true);
									dbexec.rollback();
									ex.printStackTrace();
									JOptionPane.showMessageDialog(null, "You did not select any row.");
								}
							}
						});
						btnSave.setEnabled(false);
					}
				}
			}
		}
		
		txtProjID.setValue("015");
		txtProj.setText("TERRAVERDE RESIDENCES");
		
		SetFont(panMain); 
		Reload();
		Load(); 
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
	
	private void SetFont(JXPanel panel) {
		for (Component c : panel.getComponents()) {
			if (c instanceof JXPanel) {
				SetFont((JXPanel) c); 
			} else {
				c.setFont(font11);
			}
		}
	}
	
	private void Load() {
		FncGlobal.startProgress("Refreshing...");

		FncTables.clearTable(modelWCAA); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowWCAA.setModel(listModel); 

		String strSQL = "select b.entity_name, coalesce(a.reliable, false) as reliable, \n" + 
				"coalesce(pcab, '') as pcab, coalesce(maxcap_curr, 0) as maxcap_curr, coalesce(a.other_factor, 0) AS other_factor, a.con_name \n" + 
				"from rf_contractor_sup_details a \n" + 
				"inner join rf_entity b on a.con_name = b.entity_id \n" + 
				"where a.status_id = 'A' \n" + 
				"order by (case when a.reliable then 0 else 1 end), b.entity_name";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelWCAA.addRow(db.getResult()[x]);
				listModel.addElement(modelWCAA.getRowCount());
			}			
		}

		FncGlobal.stopProgress();
	}
	
	private void Reload() {
		pgUpdate dbExec = new pgUpdate(); 
		
		FncGlobal.startProgress("Refreshing...");

		dbExec.executeUpdate("insert into rf_contractor_sup_details\n" + 
				"select distinct entity_id, 'Yes', 0::numeric, '', 0::numeric, '"+UserInfo.EmployeeCode+"', \n" +
				"now(), 'A', 0::int, 0::int, '', false \n" + 
				"from co_ntp_header a\n" + 
				"where not exists(select * from rf_contractor_sup_details x where x.con_name = a.entity_id and x.status_id = 'A'); ", false);
		dbExec.commit();
		
		FncGlobal.stopProgress();
	}
}
