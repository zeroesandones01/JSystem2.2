package Reports.BiddingandAwarding;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.OverlayLayout;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Database.pgSelect;
import DateChooser._JDateChooser;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import Functions.FncFocusTraversalPolicy;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;

public class SuretyBondMonitoring extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Surety Bond Monitoring";
	static Dimension frameSize = new Dimension(500, 190);//XXX 510, 250
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;

	private JPanel pnlCenter;

	private JPanel pnlCriteria;
	private JLabel lblCriteria;
	private JComboBox cmbCriteria;
	
	private JPanel pnlNTPPeriod;
	private JLabel lblNTPPeriod;
	private _JDateChooser dateFrom;
	private _JDateChooser dateTo;

	private JPanel pnlSorting;
	private JLabel lblSorting;
	private JComboBox cmbSorting;

	private JPanel pnlSouth;
	private JButton btnPreview;

	String company;
	
	String server = null;
	String dbase = null;

	public SuretyBondMonitoring() {
		super(title, false, true, false, true);
		initGUI();
	}

	public SuretyBondMonitoring(String title) {
		super(title);
		initGUI();
	}

	public SuretyBondMonitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(488, 25));
				{
					pnlNorthWest = new JPanel(new GridLayout(1, 2));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new Dimension(116, 25));
					{
						lblProject = new JLabel("Project");
						pnlNorthWest.add(lblProject);
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setLookupSQL(SQL_PROJ());
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtProject.setText((String) data[1]);
									//company = (String) data[4];
									//company_logo = (String) data[5];
									//server = (String) data[7];
									//dbase = (String) data[8];
								}
							}
						});
					}
				}
				{
					txtProject = new JTextField();
					pnlNorth.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
				}
			}
			{
				pnlCenter = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlCriteria = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlCriteria);
					//pnlCriteria.setBorder(components.JTBorderFactory.createTitleBorder("Criteria"));
					{
						lblCriteria = new JLabel("Criteria");
						pnlCriteria.add(lblCriteria, BorderLayout.WEST);
						lblCriteria.setPreferredSize(new Dimension(55, 0));
					}
					{
						cmbCriteria = new JComboBox(new DefaultComboBoxModel(getCriteria()));
						pnlCriteria.add(cmbCriteria, BorderLayout.CENTER);
						/*cmbCriteria.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent arg0) {
								int selectedIndex = ((JComboBox)arg0.getSource()).getSelectedIndex();
								setPeriodEnabled(selectedIndex == 1);
							}
						});*/
					}
				}
				{
					pnlNTPPeriod = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlNTPPeriod);
					{
						lblNTPPeriod = new JLabel("Period");
						pnlNTPPeriod.add(lblNTPPeriod, BorderLayout.WEST);
						lblNTPPeriod.setPreferredSize(new Dimension(55, 0));
					}
					{
						JPanel pnlDates = new JPanel(new GridLayout(1, 2, 10, 10));
						pnlNTPPeriod.add(pnlDates, BorderLayout.CENTER);
						{
							dateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlDates.add(dateFrom);
							dateFrom.setDate(Calendar.getInstance().getTime());
						}
						{
							dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlDates.add(dateTo);
							dateTo.setDate(Calendar.getInstance().getTime());
						}
					}
				}
				{
					pnlSorting = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlSorting);
					//pnlSorting.setBorder(components.JTBorderFactory.createTitleBorder("Sorting"));
					{
						lblSorting = new JLabel("Sort By");
						pnlSorting.add(lblSorting, BorderLayout.WEST);
						lblSorting.setPreferredSize(new Dimension(55, 0));
					}
					{
						cmbSorting = new JComboBox(new DefaultComboBoxModel(
								new String[] { "Project", "Contractor", "Contrac No." }));
						pnlSorting.add(cmbSorting, BorderLayout.CENTER);
						cmbSorting.setSelectedIndex(2);
					}
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(588, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupProject, cmbCriteria, cmbSorting, btnPreview));
	}

	//private String getProject() {
		//return "SELECT * FROM lookup_project;";
	//}

	private String[] getCriteria() {
		return new String[] {
				"All Contracts",
				"Contracts w/ Surety Bond",
				"On-going Contracts (Not Yet 100% Completed)"
		};
	}

	private List<JRSortField> sortBy(String sort_by) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("Project", new String[]{"c_project"});
		map.put("Contractor", new String[]{"c_contractor"});
		map.put("Contrac No.", new String[]{"c_contract_no"});

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
	public void ancestorAdded(AncestorEvent event) {
		lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String criteria = (String) cmbCriteria.getSelectedItem();
		String sort_by = (String) cmbSorting.getSelectedItem();
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		
		pgSelect db = new pgSelect();
		db.select("select b.company_name, b.company_logo\n" + 
				  "from mf_project a\n" + 
				  "LEFT JOIN mf_company b on b.co_id = a.co_id\n" + 
				  "where a.proj_id = '"+lookupProject.getValue()+"';");
		
		String company_logo = null;
		String company_name = null;
		if(db.isNotNull()){
			company_name = (String) db.getResult()[0][0];
			company_logo = (String) db.getResult()[0][1];
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company_name);
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("_server", server);
		mapParameters.put("_dbase", dbase);
		mapParameters.put("criteria", criteria);
		mapParameters.put("dateFrom", dateFrom.getTimestamp());
		mapParameters.put("dateTo", dateTo.getTimestamp());
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));

		FncReport.generateReport("/Reports/rptSuretyBondMonitoring.jasper", reportTitle, txtProject.getText(), mapParameters);
	}
	private static String SQL_PROJ() {//XXX Project
		String SQL = "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\", get_company_alias(co_id) as \"Company\" FROM mf_project ORDER BY proj_id;";
		return SQL;
	}
}
