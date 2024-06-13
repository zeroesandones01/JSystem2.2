package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.border.Border;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JCheckListItem;
import components._JCheckListRenderer;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;

public class SOR_ACCOUNTS extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3025706259808794893L;

	static String title = "SOR Accounts Report";
	Dimension frameSize = new Dimension(585, 310);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	_JDateChooser dateCutOff;
	JComboBox<String> cmbReport;
	JCheckBox chkProject;
	JLabel lblProject;
	_JLookup lookupProject;
	_JXTextField txtProjectName;
	JCheckBox chkCompany;
	JLabel lblCompany;
	_JLookup lookupCompany;
	_JXTextField txtCompanyName;
	JCheckBox chkAllPhase;
	JScrollPane scrollPhase;
	JList listPhase;
	JComboBox<String> cmbSellingPrice;
	
	String company_id;
	String project_id;

	public SOR_ACCOUNTS() {
		super(title, false, true, false, true);
		initGUI();
	}

	public SOR_ACCOUNTS(String title) {
		super(title);
		initGUI();
	}

	public SOR_ACCOUNTS(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(frameSize));
		this.setLayout(new BorderLayout());
		{
			JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlCenter = new JPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(null);
				pnlCenter.setBorder(_LINE_BORDER);
				{
					JLabel lblReport = new JLabel("Report  ", JLabel.TRAILING);
					pnlCenter.add(lblReport);
					lblReport.setBounds(10, 10, 90, 25);
				}
				{
					cmbReport = new JComboBox<String>(new String[]{
							"SOR Report"
							});
					pnlCenter.add(cmbReport);
					cmbReport.setSelectedIndex(0);
					cmbReport.setBounds(103, 10, 250, 25);
				}
				{
					JLabel lblCutOff = new JLabel("Cut-Off  ", JLabel.TRAILING);
					pnlCenter.add(lblCutOff);
					lblCutOff.setBounds(333, 10, 100, 25);
				}
				{
					dateCutOff = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
					pnlCenter.add(dateCutOff);
					dateCutOff.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dateCutOff.setBounds(438, 10, 120, 25);
				}
				{
					lblCompany = new JLabel(" Company");
					pnlCenter.add(lblCompany);
					lblCompany.setBounds(10, 40, 90, 25);
				}
				{
					lookupCompany = new _JLookup(null, "Company");
					pnlCenter.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(sql_company());
					lookupCompany.setPrompt("ID");
					lookupCompany.setEnabled(true);
					lookupCompany.setBounds(103, 40, 60, 25);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								company_id = (String) data[0];
								txtCompanyName.setText((String) data[1]);
								
								lookupProject.setLookupSQL(sql_project(company_id));
								project_id		= "000";
							}
						}
					});
				}
				{
					txtCompanyName = new _JXTextField("Company Name");
					pnlCenter.add(txtCompanyName);
					txtCompanyName.setBounds(168, 40, 390, 25);
				}
				{
					lblProject = new JLabel(" Project");
					pnlCenter.add(lblProject);
					lblProject.setBounds(10, 70, 90, 25);
				}
				{
					lookupProject = new _JLookup(null, "Project");
					pnlCenter.add(lookupProject);
					lookupProject.setReturnColumn(0);
					lookupProject.setPrompt("ID");
					lookupProject.setEnabled(true);
					lookupProject.setBounds(103, 70, 60, 25);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								project_id = (String) data[0];
								txtProjectName.setText((String) data[1]);
								displayPhase((String) data[0]);
							}
						}
					});
				}
				{
					txtProjectName = new _JXTextField("Project Name");
					pnlCenter.add(txtProjectName);
					txtProjectName.setBounds(168, 70, 390, 25);
				}
				{
					chkAllPhase = new JCheckBox("All Phase");
					pnlCenter.add(chkAllPhase);
					chkAllPhase.setBounds(10, 100, 90, 25);
					chkAllPhase.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							boolean selected = e.getStateChange() == ItemEvent.SELECTED;

							if(selected){
								int rowCount = listPhase.getModel().getSize();
								for(int x=0; x < rowCount; x++){
									_JCheckListItem item = (_JCheckListItem) listPhase.getModel().getElementAt(x);
									item.setSelected(selected);
									listPhase.repaint(listPhase.getCellBounds(x, x));
								}
							}
						}
					});
				}
				{
					scrollPhase = new JScrollPane();
					pnlCenter.add(scrollPhase);
					scrollPhase.setBorder(lineBorder);
					scrollPhase.setBounds(103, 100, 305, 100);
					{
						listPhase = new JList();
						scrollPhase.setViewportView(listPhase);
						listPhase.setCellRenderer(new _JCheckListRenderer());
						listPhase.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						listPhase.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent event) {
								JList list = (JList) event.getSource();

								// Get index of item clicked
								int index = list.locationToIndex(event.getPoint());
								_JCheckListItem item = (_JCheckListItem) list.getModel().getElementAt(index);
								//System.out.printf("%s - %s%n", index, item.toString());

								// Toggle selected state
								item.setSelected(!item.isSelected());

								// Repaint cell
								list.repaint(list.getCellBounds(index, index));
								
								checkPhases();
							}
						});
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					JButton btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
			}
		}
	}

	private String sql_project(String co_id) {
		String SQL = "SELECT '000' as \"ID\", 'ALL' as \"Project Name\", 'ALL' as \"Alias\"\n"
				+ "UNION\n"
				+ "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\"\n"
				+ "FROM mf_project\n"
				+ "WHERE co_id = '"+co_id+"'\n"
				+ "AND status_id = 'A'\n"
				+ "order by \"ID\"";
		return SQL;
	}
	
	private String sql_company() {
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\" FROM mf_company order by co_id ";
		return SQL;
	}

	private void displayPhase(String project_id) {
		String SQL = "SELECT *\n" + 
				"FROM mf_sub_project\n" + 
				"WHERE proj_id = '"+ project_id +"' AND status_id = 'A'\n" + 
				"ORDER BY getinteger(phase), gettext(phase);";

		pgSelect db = new pgSelect();
		db.select(SQL);

		ArrayList<_JCheckListItem> listCheckItem = new ArrayList<_JCheckListItem>();
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				_JCheckListItem item = new _JCheckListItem((String) db.getResult()[x][2]);
				item.setSelected(chkAllPhase.isSelected());
				listCheckItem.add(item);
			}
		}
		listPhase.setModel(new DefaultComboBoxModel(listCheckItem.toArray()));
	}
	
	private void checkPhases() {
		ArrayList<Boolean> listSelectedphase = new ArrayList<Boolean>();

		for(int x=0; x < listPhase.getModel().getSize(); x++){
			_JCheckListItem item = (_JCheckListItem) listPhase.getModel().getElementAt(x);
			Boolean isSelected = item.isSelected();
			listSelectedphase.add(isSelected);
		}
		
		chkAllPhase.setSelected(!listSelectedphase.contains(false));
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String action = evt.getActionCommand();		
		
		if(action.equals("Preview")){		
			if (cmbReport.getSelectedIndex()==0){
				preview_sor_report();
			}else if (cmbReport.getSelectedIndex()==1){
				/*para sa hinaharap*/
			}
		}
	}
	
	private void preview_sor_report(){
		
		ArrayList<String> phases = new ArrayList<String>();
		for(int x=0; x < listPhase.getModel().getSize(); x++){
			_JCheckListItem item = (_JCheckListItem) listPhase.getModel().getElementAt(x);
			Boolean isSelected = item.isSelected();
			String phase = item.toString();
			if(isSelected){
				phases.add(phase);
			}						
		}	
		
		String criteria = "sor account report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		System.out.printf("Display Proj Name: %s%n", txtProjectName.getText());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company_id);	
		mapParameters.put("proj_id", project_id);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("phase", phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ","));
		mapParameters.put("date",dateCutOff.getDate());
		
		//String SQL = "select * from view_aging_report_summary_v4('','"+lookupProject.getValue()+"','"+dateCutOff.getDate()+"'::timestamp,'"+hse_model+"','"+phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ",")+"')";
		String SQL = "select * from view_sor_report('"+company_id+"','"+project_id+"','"+phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ",")+"','"+dateCutOff.getDate()+"'::date)";
		FncSystem.out("view_sor_report", SQL);
		
		System.out.printf("Display value of Phase: %s%n", phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ","));
		
		FncReport.generateReport("/Reports/rpt_sor_report.jasper", reportTitle, "", mapParameters);		
	}
	
	private void preview_Aging_detailed(){

		String criteria = "Aging Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		String proj_name = "All";
		
		String hse_model = "", hse_model_name = "";
		if (lookupCompany.getText().equals("")){hse_model = ""; hse_model_name = "All";} 
		else {hse_model = lookupCompany.getText(); hse_model_name = txtCompanyName.getText();}
		
		ArrayList<String> phases = new ArrayList<String>();
		for(int x=0; x < listPhase.getModel().getSize(); x++){
			_JCheckListItem item = (_JCheckListItem) listPhase.getModel().getElementAt(x);
			Boolean isSelected = item.isSelected();
			String phase = item.toString();
			//System.out.printf("%s: %s%n", phase, isSelected);
			if(isSelected){
				phases.add(phase);
			}						
		}
		
		if(lookupProject.getValue() != null) {
			proj_name = txtProjectName.getText();
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", "");	
		mapParameters.put("project", "");	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", null);
		mapParameters.put("date_to", dateCutOff.getDate());
		mapParameters.put("model_id", hse_model);
		mapParameters.put("model_name", hse_model_name);
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("phase", phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ","));
		mapParameters.put("project", proj_name);
		mapParameters.put("date",dateCutOff.getDate());
		/*mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));	
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("dept_id", sales_grp_id.trim());
		mapParameters.put("co_id", co_id);
		mapParameters.put("agent_id", agent_id.trim());
		mapParameters.put("div_id", div_id);
		mapParameters.put("posn_id", posn_id);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", posn_name);
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());
		mapParameters.put("year", df2.format(dateDateTo.getDate()).substring(6));
		mapParameters.put("month", df.format(dateDateTo.getDate()).substring(8));
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("sales_type_name", sales_type_name);
		
		System.out.printf("SQL #1 getCV_no : %s", df.format(dateDateTo.getDate()).substring(8));*/
		System.out.printf("Display value of date to: %s%n", dateCutOff.getDate());
		System.out.printf("Display value of model_id: %s%n", hse_model);
		System.out.printf("Display value of phase: %s%n", phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ","));

		String SQL = "select * from view_aging_report_detailed_v5(\n"
				+ "'"+lookupProject.getValue()+"',\n"
				+ "'"+dateCutOff.getDate()+"'::timestamp,\n"
				+ "'"+hse_model+"',\n"
				+ "'"+phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ",")+"'\n"
				+ ") order by c_sort,c_stage_desc";
		FncSystem.out("view_aging_report_detailed_v5", SQL);
		FncReport.generateReport("/Reports/rptAgingReport_detailed.jasper", reportTitle, "", mapParameters);		
	}

}
