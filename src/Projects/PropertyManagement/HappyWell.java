package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
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
import Functions.FncGlobal;
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

public class HappyWell extends _JInternalFrame implements ActionListener, AncestorListener, _GUI{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Happy Well";
	static Dimension frameSize = new Dimension(600, 230);
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
	private JLabel lblSortedBy;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupPhase;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtPhase;


	private JButton btnExport;

	private JComboBox cmbSorting;
	private JComboBox cmbCriteria;

	String company;
	String company_logo;	
	String co_id 		= "";
	String branch_name	= "";
	String branch_id 	= "";
	String proj_id		="";
	String proj_name 	= "";
	String bank_id 	= "";

	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");



	public HappyWell() {
		super(title, false, true, false, true);
		initGUI();
	}

	public HappyWell(String title) {
		super(title);
		initGUI();
	}

	public HappyWell(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
					pnlNorthWest = new JPanel(new GridLayout(3,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					//pnlNorthWest.setPreferredSize(new java.awt.Dimension(135, 100));

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

				}
				pnlNorthCenter = new JPanel(new GridLayout(3,1, 5, 5));
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

								co_id 		= (String) data[0];	
								company		= (String) data[1];	
								company_logo = (String) data[3];

								String name = (String) data[1];						
								txtCompany.setText(name);
								lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));

								lblProject.setEnabled(true);	


								KEYBOARD_MANAGER.focusNextComponent();

							}
						}
					});
				}
				{
					lookupProject = new _JLookup(null, "Project");
					pnlNorthCenter.add(lookupProject, BorderLayout.WEST);
					//lookupProject.setReturnColumn(0);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								proj_id = (String) data[0];	
								proj_name = (String) data[1];						
								txtProject.setText(proj_name);
								lookupProject.setValue(proj_id);

								//lookupPhase.setLookupSQL(SQL_PHASE(proj_id)); 
								lookupPhase.setLookupSQL(getPhase(proj_id)); // Added by Erick dated 2021-07-08
								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
					lookupProject.addFocusListener(new FocusListener() {

						@Override
						public void focusLost(FocusEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void focusGained(FocusEvent e) {
							lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(lookupCompany.getValue()));

						}
					});
				}
				{
					lookupPhase = new _JLookup(null, "Phase");
					pnlNorthCenter.add(lookupPhase);
					lookupPhase.setReturnColumn(0);
					//lookupPhase.setLookupSQL(SQL_PHASE("015"));// Comment by Erick 2021-07-08
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								txtPhase.setText(String.format("Phase %s", (String) data[0]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
					lookupPhase.addFocusListener(new FocusListener() {

						@Override
						public void focusLost(FocusEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void focusGained(FocusEvent e) {
							lookupPhase.setLookupSQL(getPhase(lookupProject.getValue())); // Added by Erick dated 2021-07-08

						}
					});
				}
				pnlNorthEast = new JPanel(new GridLayout(3, 1, 5, 5));
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

			}				
			{				
				pnlSouth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				//				pnlSouth.setPreferredSize(new java.awt.Dimension(413, 30));
				{
					btnExport = new JButton("Export");
					btnExport.setPreferredSize(new Dimension(0,30));
					pnlSouth.add(btnExport,BorderLayout.CENTER);
					//					btnExport.setAlignmentX(0.5f);
					//					btnExport.setAlignmentY(0.5f);
					//					btnExport.setMaximumSize(new Dimension(100, 30));
					btnExport.setMnemonic(KeyEvent.VK_P);
					btnExport.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
//							String col_names [] = {"Client", "Unit", "Water Balance", "Garbage Balance"};
//
//							String SQL = "select * from view_happywell('"+lookupCompany.getValue()+"','"+lookupProject.getValue()+"','"+lookupPhase.getValue()+"')";
							
							String col_names [] = {"Client", "Unit", "Previouse Balance April Below", "Payment May to June", "To Charge Garbage"};

							String SQL = "select * from view_happywell_v3('"+lookupCompany.getValue()+"','"+lookupProject.getValue()+"','"+lookupPhase.getValue()+"')";
							
							FncGlobal.startProgress("Creating Spreadsheet");
							FncGlobal.CreateXLS(col_names, SQL, "Happy Well");
							FncGlobal.stopProgress();

						}
					});
				}
			}
		}


		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		//lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }
	private List<JRSortField> sortBy(String sort_by) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("--", new String[]{});
		map.put("Phase", new String[]{"c_phase"});
		map.put("Contractor", new String[]{"c_contractor", "c_contract_no"});
		map.put("Accomplishment Percentage", new String[]{"c_percent_accomplish"});
		map.put("NTC Date", new String[]{"c_ntc_date", "c_contract_no"});
		map.put("Contract No.", new String[]{"c_contract_no"});
		map.put("CMD Date", new String[]{"c_to_cmd"});
		map.put("PMD Date", new String[]{"c_to_pmd"});
		map.put("Turned-over date to client", new String[]{"c_to_client","c_pbl"});
		map.put("Move-in Date", new String[]{"c_move_in","c_pbl"});

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



	private static String getPhase(String projid) {
		String strsql = "select\n" + 
				"				a.phase as \"Phase\",\n" + 
				"				'Phase ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" + 
				"				b.proj_alias || a.phase as \"Alias\"\n" + 
				"				from mf_sub_project a\n" + 
				"				left join mf_project b on a.proj_id = b.proj_id\n" + 
				"				where a.proj_id = '"+projid+"'\n" + 
				"				and a.status_id = 'A'\n" + 
				//				"				and a.phase != ''\n" + 
				"				group by a.phase, b.proj_alias\n" + 
				"				order by a.phase";
		return strsql;
	}

	private void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";		
		proj_id 	= "015";	
		proj_name	= "TERRAVERDE RESIDENCES";						
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		txtCompany.setText(company);
		lookupCompany.setValue(co_id);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

		txtProject.setText(proj_name);
		lookupProject.setValue(proj_id);

		KEYBOARD_MANAGER.focusNextComponent();


	}




}
