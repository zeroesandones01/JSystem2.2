package Reports.ClientServicing;

import interfaces._GUI;

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
import java.util.Calendar;
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

/**
 * @author Alvin Gonzales
 */
public class AgingReport extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3025706259808794893L;

	static String title = "Aging Report";
	Dimension frameSize = new Dimension(585, 310);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	_JDateChooser dateCutOff;
	JComboBox<String> cmbReport;
	JCheckBox chkProject;
	_JLookup lookupProject;
	_JXTextField txtProjectName;
	JCheckBox chkModel;
	_JLookup lookupModel;
	_JXTextField txtModelDescription;
	JCheckBox chkAllPhase;
	JScrollPane scrollPhase;
	JList listPhase;
	JComboBox<String> cmbSellingPrice;

	public AgingReport() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AgingReport(String title) {
		super(title);
		initGUI();
	}

	public AgingReport(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
							"Weekly Aging Report (Summary)",
							"Weekly Aging Report (Breakdown)"
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
					chkProject = new JCheckBox("   Project");
					pnlCenter.add(chkProject);
					chkProject.setBounds(10, 40, 90, 25);
					chkProject.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							boolean selected = e.getStateChange() == ItemEvent.SELECTED;
							lookupProject.setEnabled(selected);
							lookupProject.requestFocus();

							/*if(selected){
								displayPhase(lookupProject.getValue());
							}else{
								displayPhase(null);
							}*/
						}
					});
				}
				{
					lookupProject = new _JLookup(null, "Project");
					pnlCenter.add(lookupProject);
					lookupProject.setReturnColumn(0);
					lookupProject.setLookupSQL(sqlProject());
					lookupProject.setPrompt("ID");
					lookupProject.setEnabled(false);
					lookupProject.setBounds(103, 40, 60, 25);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								FncSystem.out("Display SQL for lookupProject", lookupProject.getLookupSQL());
								txtProjectName.setText((String) data[1]);
								lookupModel.setLookupSQL(SQL_MODEL((String) data[0]));

								displayPhase((String) data[0]);
							}
						}
					});
				}
				{
					txtProjectName = new _JXTextField("Project Name");
					pnlCenter.add(txtProjectName);
					txtProjectName.setBounds(168, 40, 390, 25);
				}
				{
					chkModel = new JCheckBox("    Model");
					pnlCenter.add(chkModel);
					chkModel.setBounds(10, 70, 90, 25);
					chkModel.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							boolean selected = e.getStateChange() == ItemEvent.SELECTED;
							lookupModel.setEnabled(selected);
							lookupModel.requestFocus();
						}
					});
				}
				{
					lookupModel = new _JLookup(null, "Model");
					pnlCenter.add(lookupModel);
					lookupModel.setReturnColumn(0);
					lookupModel.setLookupSQL(null);
					lookupModel.setPrompt("ID");
					lookupModel.setEnabled(false);
					lookupModel.setBounds(103, 70, 60, 25);
					lookupModel.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								txtModelDescription.setText((String) data[1]);
							}
						}
					});
				}
				{
					txtModelDescription = new _JXTextField("Model Description");
					pnlCenter.add(txtModelDescription);
					txtModelDescription.setBounds(168, 70, 390, 25);
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
				{
					JLabel lblSellingPrice = new JLabel("Selling Price  ", JLabel.TRAILING);
					pnlCenter.add(lblSellingPrice);
					lblSellingPrice.setBounds(10, 205, 90, 25);
				}
				{
					cmbSellingPrice = new JComboBox<String>(new String[]{"Net", "Gross"});
					pnlCenter.add(cmbSellingPrice);
					cmbSellingPrice.setBounds(103, 205, 100, 25);
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
	
	private String sqlProject() {
		String SQL = "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\"\n" + 
				"FROM mf_project\n" + 
				"WHERE co_id IN ('02', '01', '05', '04')\n" + 
				"AND status_id = 'A' \n"+
				"order by proj_id;";
		return SQL;
	}

	private String SQL_MODEL(String proj_id) {
		String SQL = "SELECT TRIM(model_id)::VARCHAR as \"ID\", TRIM(model_desc) as \"Description\", model_cost as \"Cost\", TRIM(firewall_type)::VARCHAR as \"Firewall Type\", floor_area as \"Floor Area\"\n" + 
				"FROM mf_product_model\n" + 
				"WHERE model_id IN (SELECT DISTINCT model_id\n" + 
				"                   FROM mf_unit_info a\n" + 
				"                   WHERE proj_id = '"+ proj_id +"')\n" + 
				"AND status_id = 'A';";
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
				preview_Aging();
			}else if (cmbReport.getSelectedIndex()==1){
				preview_Aging_detailed();
			}
			
			//System.out.printf("%n%n%s", phases.toString().replaceAll("\\[|\\]", ""));

			/*Map<String, Object> mapAging = new HashMap<String, Object>();
			mapAging.put("period", dateCutOff.getDate());
			mapAging.put("company_name", "CENQHOMES DEVELOPMENT CORPORATION");
			//mapAging.put("project_filter", chkProject.isSelected());
			//mapAging.put("project_id", lookupProject.getValue());
			//mapAging.put("project_name", txtProjectName.getText());
			//mapAging.put("model_filter", chkModel.isSelected());
			mapAging.put("model_id", hse_model);
			mapAging.put("model_name", hse_model_name);
			//mapAging.put("phase", phases.toString().replaceAll("\\[|\\]", ""));
			//mapAging.put("phase_filter", !chkAllPhase.isSelected());
			//mapAging.put("selling_price", (String) cmbSellingPrice.getSelectedItem());
			mapAging.put("prepared_by", UserInfo.Alias);*/

			//FncReport.generateReport("/Reports/rptAgingReport.jasper", "Weekly Aging Report (Breakdown)", mapAging);
			
			//FncReport.generateReport("/Reports/rptAgingReport_2.jasper", "Weekly Aging Report 2 (Breakdown)", mapAging);*/
			
			
			
			
		}
	}
	
	private void preview_Aging(){
		
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
		
		String criteria = "Aging Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		String hse_model = "", hse_model_name = "";
		if (lookupModel.getText().equals("")){
			hse_model = ""; hse_model_name = "All";} 
		else {hse_model = lookupModel.getText(); hse_model_name = txtModelDescription.getText();}
		System.out.printf("Display Proj Name: %s%n", txtProjectName.getText());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", "");	
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("proj_name", txtProjectName.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", null);
		mapParameters.put("date_to", dateCutOff.getDate());
		mapParameters.put("model_id", hse_model);
		mapParameters.put("model_name", hse_model_name);	
		mapParameters.put("model_name", hse_model_name);	
		mapParameters.put("phase", phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ","));
		System.out.printf("Display value of Phase: %s%n", phases.toString().replaceAll("\\[|\\]", "").replaceAll(", ", ","));
		
		FncReport.generateReport("/Reports/rptAgingReport_2.jasper", reportTitle, "", mapParameters);		
	}
	
	private void preview_Aging_detailed(){

		String criteria = "Aging Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		String proj_name = "All";
		
		String hse_model = "", hse_model_name = "";
		if (lookupModel.getText().equals("")){hse_model = ""; hse_model_name = "All";} 
		else {hse_model = lookupModel.getText(); hse_model_name = txtModelDescription.getText();}
		
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
		/*mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());		
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

		FncReport.generateReport("/Reports/rptAgingReport_detailed.jasper", reportTitle, "", mapParameters);		
	}

}