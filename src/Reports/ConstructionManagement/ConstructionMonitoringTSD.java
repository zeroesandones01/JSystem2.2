package Reports.ConstructionManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Functions.FncFocusTraversalPolicy;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;

public class ConstructionMonitoringTSD extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Construction Monitoring (TSD)";
	static Dimension frameSize = new Dimension(500, 200);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlSouth;

	private JLabel lblProject;
	private JLabel lblCompany;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupPhase;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtPhase;
	
	private JButton btnPreview;
	private JButton btnCancel;
	
	private JComboBox cmbSorting;
	private JComboBox cmbCriteria;

	String company;
	String company_logo;	
	String co_id 		= "";
	String branch_name	= "";
	String branch_id 	= "";
	String proj_id 		= "";	
	String proj_name 	= "";
	String bank_id 	= "";
	
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");



	public ConstructionMonitoringTSD() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ConstructionMonitoringTSD(String title) {
		super(title);
		initGUI();
	}

	public ConstructionMonitoringTSD(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(545, 150));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(541, 150));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(531, 140));
				
				{
					pnlNorthWest = new JPanel(new GridLayout(5,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					
					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlNorthWest.add(lblCompany);
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlNorthWest.add(lblProject);
					}
					{
						JLabel lblPhase = new JLabel("Phase", JLabel.TRAILING);
						pnlNorthWest.add(lblPhase);
					}
					{
						JLabel lblCriteria = new JLabel("Criteria", JLabel.TRAILING);
						pnlNorthWest.add(lblCriteria);
					}
					{
						JLabel lblSorting = new JLabel("Sort By", JLabel.TRAILING);
						pnlNorthWest.add(lblSorting);
					}
					
				}
				pnlNorthCenter = new JPanel(new GridLayout(5,1, 5, 5));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				pnlNorthCenter.setPreferredSize(new java.awt.Dimension(70, 80));
				{
					lookupCompany = new _JLookup(null, "Company");
					pnlNorthCenter.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								
								co_id = (String) data[0];	
								company		= (String) data[1];	
								company_logo = (String) data[3];

								refreshFields();
								
								String name = (String) data[1];						
								txtCompany.setText(name);
								lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
								
								lblProject.setEnabled(true);	
								lookupProject.setText("");
								lookupProject.setEnabled(true);
								txtProject.setEnabled(true);
								txtProject.setText("");
								
								lookupPhase.setText("");
								lookupPhase.setEnabled(true);
								txtPhase.setEnabled(true);
								txtPhase.setText("");


								KEYBOARD_MANAGER.focusNextComponent();
								btnCancel.setEnabled(true);
								btnPreview.setEnabled(true);
							}
						}
					});
				}
				{
					lookupProject = new _JLookup(null, "Project");
					pnlNorthCenter.add(lookupProject, BorderLayout.WEST);
					lookupProject.setReturnColumn(0);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								
								proj_id = (String) data[0];		
								String name = (String) data[1];	
								txtProject.setText(name);
								
								
								lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
								lookupPhase.setValue(null);
								txtPhase.setText("");
								lookupPhase.setEnabled(true);
								
								
								btnPreview.setEnabled(true);
								
								KEYBOARD_MANAGER.focusNextComponent();
								btnCancel.setEnabled(true);
							}
						}
					});
				}
				{
					lookupPhase = new _JLookup(null, "Phase");
					pnlNorthCenter.add(lookupPhase);
					lookupPhase.setReturnColumn(0);
					lookupPhase.setLookupSQL(SQL_PHASE("015"));
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								txtPhase.setText(String.format("Phase %s", (String) data[0]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				pnlNorthEast = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(400, 80));
				
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.EAST);
					txtCompany.setEditable(false);
				}
				{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.EAST);
					txtProject.setEditable(false);
				}
				{
					txtPhase = new JTextField();
					pnlNorthEast.add(txtPhase, BorderLayout.EAST);
					txtPhase.setEditable(false);
				}
				{
					cmbCriteria = new JComboBox(new DefaultComboBoxModel(getCriteria()));
					pnlNorthEast.add(cmbCriteria, BorderLayout.CENTER);
				}
				{
					
					String sortby[] = {
							"Unit",
							"Contract No.",
							"Date Turned Over to CMD", 
							"Date Turned Over to PMD", 
							"As-of-Date" };

					cmbSorting = new JComboBox(sortby);
									
					pnlNorthEast.add(cmbSorting, BorderLayout.CENTER);
					cmbSorting.setSelectedIndex(0);
				}
				
			}	
			{				
				pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new java.awt.Dimension(413, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setActionCommand("Preview");
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, btnPreview));
		this.setBounds(0, 0, 545, 220);  //174
		
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		//lookupCompany.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }
	
	//enable, disable
	public void refreshFields(){
		
		lookupProject.setText("");
		txtProject.setText("");
		lookupPhase.setText("");
		txtPhase.setText("");

	}
	private List<JRSortField> sortBy(String sort_by) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("Unit", new String[]{"c_phase", "c_block_no", "c_lot_no"});
		map.put("Client", new String[]{"c_client"});
		map.put("Contractor", new String[]{"c_contractor"});
		map.put("Contract No.", new String[]{"c_contract_no"});
		map.put("Work Detail", new String[]{"c_work_detail"});
		
		List<JRSortField> sortList = new ArrayList<JRSortField>();
		for(String fields : map.get(sort_by)){
			JRDesignSortField sortField = new JRDesignSortField();
			sortField.setName(fields);
			sortField.setOrder(SortOrderEnum.ASCENDING);
			sortField.setType(SortFieldTypeEnum.FIELD);
			
			sortList.add(sortField);
		}
		
		return sortList;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("Preview")){
			String criteria = (String) cmbCriteria.getSelectedItem();
			String sort_by = (String) cmbSorting.getSelectedItem();
			
			if(criteria.equals("All Units")){
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("company", company);	
				mapParameters.put("proj_id", lookupProject.getText());
				mapParameters.put("phase", lookupPhase.getText());
				mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);
				//mapParameters.put("sortby", cmbSorting.getSelectedItem());
				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));


				FncReport.generateReport("/Reports/rptConstructionMonitoringReportTSD.jasper", "Construction Monitoring for TSD", mapParameters);
			}
		}

		
		if (e.getActionCommand().equals("Cancel")){ cancel(); }
	}
	
	private void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";		
		proj_id 	= "";	
		proj_name	= "";						
		company_logo = RequestForPayment.sql_getCompanyLogo();	
		
		refreshFields();		
					
		txtCompany.setText(company);
		lookupCompany.setValue(co_id);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		
		txtProject.setText(proj_name);
		lookupProject.setValue(proj_id);
	
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);
	}
	
	private void cancel(){		
		lookupCompany.setValue("");
		txtCompany.setText("");
		
		lookupProject.setValue("");
		txtProject.setText("");
	
		lookupPhase.setValue("");
		txtPhase.setText("");
		
		btnPreview.setEnabled(true);	
		
		co_id 	= "";
		proj_id 	= "";	
		proj_name 	= "";
	}

	private String[] getCriteria() {
		return new String[] {
				"All Units",
		};
	}
	

}