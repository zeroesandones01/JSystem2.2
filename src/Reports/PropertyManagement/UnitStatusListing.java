package Reports.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.LoansManagement._PagibigStatusMonitoring;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelUnitStatusListing;

public class UnitStatusListing extends _JInternalFrame implements ActionListener, _GUI {
	
	private static final long serialVersionUID = 3504250922424093906L;
	static String title = "Unit Status Listing";
	Dimension frameSize = new Dimension(500, 500);
	
	private JPanel pnlNorth;
	private JPanel pnlCenter;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblStatus;
	private JLabel lblStatusDate;

	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private _JLookup lookupPhase;
	private _JLookup lookupStatus;

	private _JXTextField txtCompany;
	private _JXTextField txtProject;
	private _JXTextField txtPhase;
	private _JXTextField txtStatus;
	private JComboBox cmbDateCondition;
	private _JDateChooser dteStatus;
	
	private JButton btnAddStatus;
	
	private _JTableMain tblUnitStatusListing;
	private modelUnitStatusListing modelUnitStatusListing;
	private JScrollPane scrollUnitStatusListing;
	private JList rowHeaderUnitStatusListing;

	private JButton btnPreview;
	private JButton btnCancel;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

	public UnitStatusListing() {
		super(title, true, true, false, true);
		initGUI();
	}

	public UnitStatusListing(String title) {
		super(title);
		initGUI();
	}

	public UnitStatusListing(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(panMain, BorderLayout.CENTER);
			panMain.setPreferredSize(frameSize);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 200));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Status Details"));
				{
					JPanel pnlNorthLabel = new JPanel(new GridLayout(6, 1, 3, 3));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					{
						lblCompany = new JLabel("Company");
						pnlNorthLabel.add(lblCompany);
					}
					{
						lblProject = new JLabel("Project");
						pnlNorthLabel.add(lblProject);
					}
					{
						lblPhase = new JLabel("Phase");
						pnlNorthLabel.add(lblPhase);
					}
					{
						lblStatus = new JLabel("Status");
						pnlNorthLabel.add(lblStatus);
					}
					{
						lblStatusDate = new JLabel("Date");
						pnlNorthLabel.add(lblStatusDate);
					}
					{
						pnlNorthLabel.add(Box.createHorizontalBox());
					}
				}
				{
					JPanel pnlNorthComponent = new JPanel(new GridLayout(6, 1, 3, 3));
					pnlNorth.add(pnlNorthComponent, BorderLayout.CENTER);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlCompany);
						{
							lookupCompany = new _JLookup("");
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setHorizontalAlignment(JTextField.CENTER);
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.setReturnColumn(0);
							//lookupCompany.setValue("02");
							lookupCompany.setPreferredSize(new Dimension(50, 0));
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText(data[1].toString());
										lookupProject.setLookupSQL(sqlProject(lookupCompany.getValue()));

										//btnPreview.setEnabled(true);
									} else {
										//btnPreview.setEnabled(false);									
									}
								}
							});
						}
						{
							txtCompany = new _JXTextField("");
							pnlCompany.add(txtCompany, BorderLayout.CENTER);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
							//txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
						}
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlProject);
						{
							lookupProject = new _JLookup("");
							pnlProject.add(lookupProject, BorderLayout.WEST);
							lookupProject.setHorizontalAlignment(JTextField.CENTER);
							lookupProject.setLookupSQL(_PagibigStatusMonitoring.sqlProject(""));
							lookupProject.setReturnColumn(0);
							//lookupProject.setValue("015");
							lookupProject.setPreferredSize(new Dimension(50, 0));
							lookupProject.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtProject.setText(data[1].toString());
										lookupPhase.setLookupSQL(sqlPhase(data[0].toString()));
									}
								}
							});
						}
						{
							txtProject = new _JXTextField("");
							pnlProject.add(txtProject, BorderLayout.CENTER);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
							//txtProject.setText("TERRAVERDE RESIDENCES");
						}
					}
					{
						JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlPhase);
						{
							lookupPhase = new _JLookup();
							pnlPhase.add(lookupPhase, BorderLayout.WEST);
							lookupPhase.setHorizontalAlignment(JTextField.CENTER);
							lookupPhase.setReturnColumn(1);
							lookupPhase.setPreferredSize(new Dimension(50, 0));
							lookupPhase.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();

									if (data!=null) {
										
										txtPhase.setText(data[2].toString());
									}
								}
							});
						}
						{
							txtPhase = new _JXTextField("");
							pnlPhase.add(txtPhase, BorderLayout.CENTER);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
						}
					}
					{
						JPanel pnlStatus = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlStatus);
						{
							lookupStatus = new _JLookup();
							pnlStatus.add(lookupStatus, BorderLayout.CENTER);
							lookupStatus.setHorizontalAlignment(JTextField.CENTER);
							lookupStatus.setReturnColumn(0);
							lookupStatus.setLookupSQL(sqlStatus());
							//lookupStatus.setPreferredSize(new Dimension(50, 0));
							lookupStatus.addLookupListener(new LookupListener() {
								
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									if (data!=null) {
										
										cmbDateCondition.setSelectedIndex(0);
										dteStatus.setDate(null);
									}
								}
							});
						}
						/*{
							txtStatus = new _JXTextField();
							pnlStatus.add(txtStatus, BorderLayout.CENTER);
							txtStatus.setHorizontalAlignment(JTextField.CENTER);
						}*/
					}
					{
						JPanel pnlDateCondition = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlDateCondition);
						{
							JPanel pnlDateConditionWest = new JPanel(new BorderLayout(3, 3));
							pnlDateCondition.add(pnlDateConditionWest, BorderLayout.WEST);
							pnlDateConditionWest.setPreferredSize(new Dimension(210, 0));
							{
								cmbDateCondition = new JComboBox(new String[] {"", ">", "<", "=", "<=", ">="});
								pnlDateConditionWest.add(cmbDateCondition, BorderLayout.WEST);
								cmbDateCondition.setPreferredSize(new Dimension(70, 0));
							}
							{
								dteStatus = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDateConditionWest.add(dteStatus, BorderLayout.CENTER);
								dteStatus.setPreferredSize(new Dimension(140, 0));
							}
						}
						{
							JPanel pnlDateConditionCenter = new JPanel(new BorderLayout(3, 3));
							pnlDateCondition.add(pnlDateConditionCenter, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlAddStatus = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlAddStatus);
						{
							btnAddStatus = new JButton("Add Status");
							pnlAddStatus.add(btnAddStatus, BorderLayout.WEST);
							btnAddStatus.setActionCommand("Add Status");
							btnAddStatus.addActionListener(this);
							btnAddStatus.setPreferredSize(new Dimension(150, 0));
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Status List"));
				{
					scrollUnitStatusListing = new JScrollPane();
					pnlCenter.add(scrollUnitStatusListing, BorderLayout.CENTER);
					{

						modelUnitStatusListing = new modelUnitStatusListing();

						tblUnitStatusListing = new _JTableMain(modelUnitStatusListing);
						scrollUnitStatusListing.setViewportView(tblUnitStatusListing);
						scrollUnitStatusListing.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						tblUnitStatusListing.setSortable(false);
						tblUnitStatusListing.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
					}
					{
						rowHeaderUnitStatusListing = tblUnitStatusListing.getRowHeader();
						rowHeaderUnitStatusListing.setModel(new DefaultListModel());
						scrollUnitStatusListing.setRowHeaderView(rowHeaderUnitStatusListing);
						scrollUnitStatusListing.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(3, 3));
				panMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 40));
				{
					JPanel pnlSouthLower = new JPanel(new GridLayout(1, 3));
					pnlSouth.add(pnlSouthLower);
					{
						pnlSouthLower.add(Box.createHorizontalBox());
					}
					{
						btnPreview = new JButton("Preview");
						pnlSouthLower.add(btnPreview);
						btnPreview.setActionCommand("Preview");
						btnPreview.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlSouthLower.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.addActionListener(this);
					}
				}
			}
		}
		//btnState(false, false, false);

	}//XXX END OF INIT GUI
	
	private String sqlCompany(){
		return "SELECT co_id as \"ID\", \n" + 
			   "company_name as \"Company Name\", \n" + 
			   "company_alias as \"Alias\" \n" + 
			   "FROM mf_company \n"+
			   "ORDER BY co_id";
	}
	
	private String sqlProject(String co_id){
		return "SELECT \n" + 
			   "proj_id as \"ID\", \n" + 
			   "proj_name as \"Project\", \n" + 
			   "proj_alias as \"Alias\"\n" + 
			   "FROM mf_project\n" + 
			   "WHERE co_id = '"+co_id+"' \n"+
			   "ORDER BY proj_id;";
	}
	
	private String sqlPhase(String proj_id){
		
		return "SELECT sub_proj_id as \"ID\", \n" + 
				"phase as \"Phase\",\n" + 
				"sub_proj_name as \"Phase Name\",\n" + 
				"sub_proj_alias as \"Alias\"\n" + 
				"FROM mf_sub_project\n" + 
				"WHERE proj_id = '"+proj_id+"' \n"+
				"ORDER BY phase";
	}
	
	private String sqlStatus(){
		return "SELECT 'Attended TO Orientaton' as \"Status Desc\"\n" + 
				"UNION \n" + 
				"SELECT 'Turned Over' as \"Status Desc\"\n" + 
				"UNION \n" + 
				"SELECT 'Move In' as \"Status Desc\";";
	}
	
	
	
	private void clearFields(){
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupPhase.setValue(null);
		txtPhase.setText("");
		lookupStatus.setValue(null);
		txtStatus.setText("");
		cmbDateCondition.setSelectedIndex(0);
		dteStatus.setDate(null);
		
		modelUnitStatusListing.clear();
		rowHeaderUnitStatusListing.setModel(new DefaultListModel());
		scrollUnitStatusListing.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
	}
	
	private boolean isInTable(String status_desc, modelUnitStatusListing model){
		Boolean isInTable = false;
		
		for(int x = 0; x<model.getRowCount(); x++){
			if(status_desc.equals(model.getValueAt(x, 0))){
				isInTable = true;
			}
		}
		//System.out.printf("Display return: %s%n", isInTable);		
		return isInTable;
	}
	
	private boolean toSave(){
		if(lookupStatus.getValue() == null){
			JOptionPane.showMessageDialog(pnlCenter, String.format("Please select status to add"), "Add Status", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(cmbDateCondition.getSelectedIndex() == 0){
			JOptionPane.showMessageDialog(pnlCenter, String.format("Please add date condition"), "Add Status", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(dteStatus.getDate() == null){
			JOptionPane.showMessageDialog(pnlCenter, String.format("Please input date"), "Add Status", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private void addStatus(){
		String status_desc = lookupStatus.getValue();
		String date_condition = (String) cmbDateCondition.getSelectedItem();
		Date status_date = dteStatus.getDate();
		
		if(isInTable(status_desc, modelUnitStatusListing) == false){
			modelUnitStatusListing.addRow(new Object []{status_desc, date_condition, status_date});

			rowHeaderUnitStatusListing.setModel(new DefaultListModel());

			for(int y =1; y<=modelUnitStatusListing.getRowCount(); y++){
				((DefaultListModel) rowHeaderUnitStatusListing.getModel()).addElement(y);
			}
			tblUnitStatusListing.packAll();
			scrollUnitStatusListing.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblUnitStatusListing.getRowCount())));
		}
		
		lookupStatus.setValue(null);
		cmbDateCondition.setSelectedIndex(0);
		dteStatus.setDate(null);
		
	}
	
	private void previewStatusListing(){
		
		ArrayList<String> listByrstatusId = new ArrayList<String>();
		ArrayList<String> listStatusDesc = new ArrayList<String>();
		ArrayList<String> listDateCondition = new ArrayList<String>();
		ArrayList<String> listDateStatus = new ArrayList<String>();
		
		for(int x= 0; x<modelUnitStatusListing.getRowCount(); x++){
			String status_desc = (String) modelUnitStatusListing.getValueAt(x, 0);
			String date_condition = (String) modelUnitStatusListing.getValueAt(x, 1);
			Date date_status = (Date) modelUnitStatusListing.getValueAt(x, 2);
			
			listStatusDesc.add(String.format("%s", status_desc));
			listDateCondition.add(String.format("%s", date_condition));
			listDateStatus.add(String.format("%s", date_status));
		}
		
		String status_desc = listStatusDesc.toString().replaceAll("\\[|\\]", "");
		String date_condition = listDateCondition.toString().replaceAll("\\[|\\]", "");
		String date_status = listDateStatus.toString().replaceAll("\\[|\\]", "");
		
		System.out.printf("Display value of statusdesc: %s%n", status_desc);
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("company_name", txtCompany.getText());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("phase", lookupPhase.getValue());
		mapParameters.put("status_desc", status_desc);
		mapParameters.put("date_condition", date_condition);
		mapParameters.put("date_status", date_status);
		mapParameters.put("inactive_accts", false);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		
		FncReport.generateReport("/Reports/rptUnitStatusListing.jasper", "Status Listing", mapParameters);
	}
	
	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();
		
		if(actionCommand.equals("Add Status")){
			if(modelUnitStatusListing.getRowCount() > 4){
				JOptionPane.showMessageDialog(pnlCenter, "Maximum number of status exceeded", "Add Status", JOptionPane.WARNING_MESSAGE);
			}else{
				if(toSave()){
					addStatus();
				}
			}
		}
		
		if(actionCommand.equals("Preview")){
			if(modelUnitStatusListing.getRowCount() == 0){
				JOptionPane.showMessageDialog(pnlCenter, "Cannot preview without status added", "Preview", JOptionPane.WARNING_MESSAGE);
			}else{
				FncGlobal.startProgress("Generating Status");
				previewStatusListing();
			}
		}
		
		if(actionCommand.equals("Cancel")){
			if(JOptionPane.showConfirmDialog(UnitStatusListing.this, "Are you sure you want to cancel?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				clearFields();
			}
		}
	}
}
