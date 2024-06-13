package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Projects.ConstructionManagement.noticetoconstruct;
import System.pnlBookmarks;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelOrderOfPayments_CreditPayments;
import tablemodel.model_occupancy_monitoring;


public class OccupancyMonitoring extends _JInternalFrame implements _GUI, MouseListener, KeyListener {
	
	private static final long serialVersionUID = 6500003584501090022L;
	
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;
	
	JLabel lblCompany;
	JLabel lblProject;
	JLabel lblBlock;
	JLabel lblBatch2;
	JLabel lblOrientationDate;
	JLabel lblClientName;
	JLabel lblFilterToLotOnly;
	JLabel lblPhase;
	
	JXTextField txtOrientationDate;
	JXTextField txtClientName;
	JXTextField txtCompany;
	JXTextField txtProject;
	JXTextField txtOccupancyStatus;
	JXTextField txtSearch;
	JXTextField txtPhase;
	JXTextField txtBlock;
	
	_JLookup lookupCompany;
	_JLookup lookupProject;
	_JLookup lookupOccupancyStatus;
	_JLookup lookupBlock;
	_JLookup lookupClient;
	_JLookup lookupPhase;
	
	_JDateChooser dteFrom;
	_JDateChooser dteTo;
	
	JComboBox cmbOccupancyStatus;
	
	JButton btnRetrieve;
	JButton btnEdit;
	JButton btnSave;
	JButton btnPreview;
	JButton btnCancel;
	
	JCheckBox chkboxFilter;
	JCheckBox chkwithBPermit;
	JCheckBox chkwithCEINo;
	JCheckBox chkwithOccuNo;
	JCheckBox chkwithoutBPermit;
	JCheckBox chkwithoutCEINo;
	JCheckBox chkwithoutOccuNo;
	
	model_occupancy_monitoring modelOccupancy;
	JScrollPane scrollOccupancy;
	_JTableMain tblOccupancy;
	JList rowheaderOccupancy;
	
	String projcode = "";
	public static String company_logo;
	public static String co_id 	= "02";
	
	static String title = "Occupancy Monitoring";
	Dimension frameSize = new Dimension(1000, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);


	public OccupancyMonitoring() {
		super(title, true, true, false, true);
		initGUI();
	}

	public OccupancyMonitoring(String title) {
		super(title, true, true, false, true);
		initGUI();
	}

	public OccupancyMonitoring(String title,
			boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
				pnlMain = new JPanel();
				this.getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setLayout(new BorderLayout(5, 5));
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlNorth = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					//pnlNorth.setLayout(null);
					pnlNorth.setBorder(JTBorderFactory.createTitleBorder(""));
					
					//pnlNorth.setPreferredSize(new Dimension(1000, 200));// XXX
					{
						JPanel pnlNorthWest = new JPanel(new BorderLayout(5, 5));
						pnlNorth.add(pnlNorthWest);
						{
							JPanel pnlNorthWestLabel = new JPanel(new GridLayout(3, 1, 5, 5));
							pnlNorthWest.add(pnlNorthWestLabel, BorderLayout.WEST);
							{
								lblCompany = new JLabel("Company");
								pnlNorthWestLabel.add(lblCompany);
								//lblCompany.setHorizontalAlignment(JLabel.LEFT);
								//lblCompany.setBounds(10, 10, 120, 22);
							}
							{
								lblProject = new JLabel("Project");
								pnlNorthWestLabel.add(lblProject);
								//lblProject.setHorizontalAlignment(JLabel.LEFT);
								//lblProject.setBounds(10, 35, 120, 22);
							}
							{
								JLabel lblOccupancyStatus = new JLabel("Occupany Status");
								pnlNorthWestLabel.add(lblOccupancyStatus);
							}
						}
						{
							JPanel pnlNorthWestComponent = new JPanel(new GridLayout(3, 1, 5, 5));
							pnlNorthWest.add(pnlNorthWestComponent);
							{
								JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
								pnlNorthWestComponent.add(pnlCompany);
								{
									lookupCompany = new _JLookup(null, "Company");
									pnlCompany.add(lookupCompany, BorderLayout.WEST);
									lookupCompany.setReturnColumn(0);
									lookupCompany.setLookupSQL(SQL_COMPANY());
									lookupCompany.setPreferredSize(new Dimension(100, 0));
									lookupCompany.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												txtCompany.setText(String.format("%s", (String) data[1]));
												lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));
												company_logo = (String) data[3];

												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
								}
								{
									txtCompany = new JXTextField(" ");
									pnlCompany.add(txtCompany, BorderLayout.CENTER);
									txtCompany.setHorizontalAlignment(JLabel.LEFT);
									txtCompany.setBounds(165, 10, 300, 22);
									txtCompany.setEditable(false);
								}
							}
							{
								JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
								pnlNorthWestComponent.add(pnlProject);
								{
									lookupProject = new _JLookup(null, "Project", "Please select company.");
									pnlProject.add(lookupProject, BorderLayout.WEST);
									lookupProject.setReturnColumn(0);
									lookupProject.setPreferredSize(new Dimension(100, 0));
									lookupProject.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											final Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												//displayAccounts((String) data[0], lookupBlock.getValue(), lookupPhase.getValue() );
												System.out.printf("Display lookupblock: %s%n", lookupBlock.getValue());
												System.out.printf("Display lookupPhase: %s%n", lookupPhase.getValue());
												//displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), cmbOccupancyStatus.getSelectedIndex(), (Date) dteFrom.getDate(), (Date) dteTo.getDate());
												
												txtProject.setText(String.format("%s", (String) data[1]));
												lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
												btnSave.setEnabled(false);
												btnPreview.setEnabled(true);

												KEYBOARD_MANAGER.focusNextComponent();
												/*chkwithBPermit.setSelected(false);
												chkwithoutBPermit.setSelected(false);
												chkwithCEINo.setSelected(false);
												chkwithoutCEINo.setSelected(false);
												chkwithOccuNo.setSelected(false);
												chkwithoutOccuNo.setSelected(false);*/
											}
										}
									});
								}
								{
									txtProject = new JXTextField(" ");
									pnlProject.add(txtProject, BorderLayout.CENTER);
									txtProject.setHorizontalAlignment(JLabel.LEFT);
									txtProject.setBounds(165, 35, 300, 22);
									txtProject.setEditable(false);
								}
							}
							{
								JPanel pnlOccupancyStatus = new JPanel(new BorderLayout(5,5));
								pnlNorthWestComponent.add(pnlOccupancyStatus);
								/*{
									lookupOccupancyStatus = new _JLookup(null, "Occupancy Status");
									pnlOccupancyStatus.add(lookupOccupancyStatus, BorderLayout.WEST);
									lookupOccupancyStatus.setPreferredSize(new Dimension(100, 0));
									lookupOccupancyStatus.setLookupSQL("");
									
								}
								{
									txtOccupancyStatus = new JXTextField();
									pnlOccupancyStatus.add(txtOccupancyStatus, BorderLayout.CENTER);
								}*/
								{
									cmbOccupancyStatus = new JComboBox(new String[] {"All", "w/ Building Permit", "w/out Building Permit", "w/ CEI No.", "w/out CEI No.", "w/ Occupancy No.", "w/out Occupancy No."});
									pnlOccupancyStatus.add(cmbOccupancyStatus, BorderLayout.WEST);
									cmbOccupancyStatus.setPreferredSize(new Dimension(200, 0));
									cmbOccupancyStatus.addItemListener(new ItemListener() {
										
										@Override
										public void itemStateChanged(ItemEvent e) {
											int selected_item = cmbOccupancyStatus.getSelectedIndex();
											Date date_from = (Date) dteFrom.getDate();
											Date date_to = (Date) dteTo.getDate();
											
											if(selected_item == 0){
												displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), selected_item, date_from, date_to);
												btnSave.setEnabled(false);
											}
											
											if(selected_item == 1){
												dteFrom.setDate(null);
												dteTo.setDate(null);
												displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), selected_item, date_from, date_to);
												btnSave.setEnabled(true);
											}
											
											if(selected_item == 2){
												dteFrom.setDate(null);
												dteTo.setDate(null);
												displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), selected_item, date_from,date_to);
												btnSave.setEnabled(true);
											}
											
											if(selected_item == 3){
												dteFrom.setDate(null);
												dteTo.setDate(null);
												displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), selected_item, date_from,date_to);
												btnSave.setEnabled(true);
											}
											
											if(selected_item == 4){
												dteFrom.setDate(null);
												dteTo.setDate(null);
												displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), selected_item, date_from,date_to);
												btnSave.setEnabled(true);
											}
											
											if(selected_item == 5){
												dteFrom.setDate(null);
												dteTo.setDate(null);
												displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), selected_item, date_from,date_to);
												btnSave.setEnabled(true);
											}
											
											if(selected_item == 6){
												dteFrom.setDate(null);
												dteTo.setDate(null);
												displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), selected_item, date_from,date_to);
												btnSave.setEnabled(true);
											}
										}
									});
								}
							}
						}
					}
					{
						JPanel pnlNorthEast = new JPanel(new BorderLayout(5, 5));
						pnlNorth.add(pnlNorthEast);
						{
							JPanel pnlNorthEastLabel = new JPanel(new GridLayout(3, 1, 5, 5));
							pnlNorthEast.add(pnlNorthEastLabel, BorderLayout.WEST);
							{
								lblPhase = new JLabel("Phase");
								pnlNorthEastLabel.add(lblPhase);
								lblPhase.setHorizontalAlignment(JLabel.LEFT);
								//lblPhase.setBounds(530, 10, 120, 22);
							}
							{
								lblBlock = new JLabel("Block");
								pnlNorthEastLabel.add(lblBlock);
								lblBlock.setHorizontalAlignment(JLabel.LEFT);
								//lblBlock.setBounds(530, 35, 200, 22);
							}
							{
								JLabel lblDate = new JLabel("Date");
								pnlNorthEastLabel.add(lblDate);
							}
						}
						{
							JPanel pnlNorthEastComponent = new JPanel(new GridLayout(3, 1, 5, 5));
							pnlNorthEast.add(pnlNorthEastComponent, BorderLayout.CENTER);
							{
								JPanel pnlPhase = new JPanel(new BorderLayout(5, 5));
								pnlNorthEastComponent.add(pnlPhase);
								{
									lookupPhase = new _JLookup(null, "Phase", "Please select project.");
									pnlPhase.add(lookupPhase, BorderLayout.WEST);
									lookupPhase.setReturnColumn(0);
									lookupPhase.setPreferredSize(new Dimension(100, 0));
									lookupPhase.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											final Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												displayAccounts(lookupProject.getValue(), lookupBlock.getValue(), (String) data[0] );
												txtPhase.setText(String.format("%s", (String) data[1]));
												btnSave.setEnabled(false);
												
												KEYBOARD_MANAGER.focusNextComponent();
												btnState(false, false, true, false);
											}
										}
									});
								}
								{
									txtPhase = new JXTextField(" ");
									pnlPhase.add(txtPhase, BorderLayout.CENTER);
									txtPhase.setHorizontalAlignment(JLabel.LEFT);
									//txtPhase.setBounds(670, 10, 300, 22);
									txtPhase.setEditable(false);
								}
							}
							{
								JPanel pnlBlock = new JPanel(new BorderLayout(5, 5));
								pnlNorthEastComponent.add(pnlBlock);
								{
									lookupBlock = new _JLookup(null, "Block");
									pnlBlock.add(lookupBlock, BorderLayout.WEST);
									lookupBlock.setReturnColumn(0);
									lookupBlock.setLookupSQL(SQL_BLOCK(lookupPhase.getValue()));
									lookupBlock.setPreferredSize(new Dimension(100, 0));
									lookupBlock.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											final Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												displayAccounts(lookupProject.getValue(), (String) data[0], lookupPhase.getValue() );
												txtBlock.setText(String.format("%s", (String) data[1]));
												btnSave.setEnabled(false);
												
												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
								}
								{
									txtBlock = new JXTextField(" ");
									pnlBlock.add(txtBlock, BorderLayout.CENTER);
									txtBlock.setHorizontalAlignment(JLabel.LEFT);
									//txtBlock.setBounds(670, 35, 300, 22);
									txtBlock.setEditable(false);
								}
							}
							{
								JPanel pnlDateFromTo = new JPanel(new GridLayout(1, 2, 5, 5));
								pnlNorthEastComponent.add(pnlDateFromTo);
								{
									JPanel pnlDateFrom = new JPanel(new BorderLayout(2, 2));
									pnlDateFromTo.add(pnlDateFrom);
									{
										JLabel lblDateFrom = new JLabel("From");
										pnlDateFrom.add(lblDateFrom, BorderLayout.WEST);
									}
									{
										dteFrom = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlDateFrom.add(dteFrom, BorderLayout.CENTER);
										dteFrom.setPreferredSize(new Dimension(150, 0));
										dteFrom.addActionListener(new ActionListener() {
											
											@Override
											public void actionPerformed(ActionEvent e) {
												//displayOccupancyMonitoringAccts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBlock.getValue(), cmbOccupancyStatus.getSelectedIndex(), (Date) dteFrom.getDate(), (Date) dteTo.getDate());
												
											}
										});
									}
								}
								{
									JPanel pnlDateTo = new JPanel(new BorderLayout(2, 2));
									pnlDateFromTo.add(pnlDateTo);
									{
										JLabel lblDateTo = new JLabel("To");
										pnlDateTo.add(lblDateTo, BorderLayout.WEST);
									}
									{
										dteTo = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlDateTo.add(dteTo, BorderLayout.CENTER);
										dteTo.setPreferredSize(new Dimension(150, 0));
										dteTo.addActionListener(new ActionListener() {
											
											@Override
											public void actionPerformed(ActionEvent e) {
												// TODO Auto-generated method stub
												
											}
										});
									}
								}
							}
						}
					}
				
					{
						pnlWest = new JPanel();
						//pnlMain.add(pnlWest, BorderLayout.WEST);
						pnlWest.setLayout(new BorderLayout(5, 5));
						pnlWest.setBorder(lineBorder);
						pnlWest.setPreferredSize(new Dimension(200, 400));// XXX	
						{
							JLabel lblWest = new JLabel("WEST");
							pnlWest.add(lblWest, BorderLayout.CENTER);
							lblWest.setHorizontalAlignment(JLabel.CENTER);
						}
					}
					{
						pnlEast = new JPanel();
						pnlEast.setLayout(new BorderLayout(5, 5));
						pnlEast.setBorder(lineBorder);
						pnlEast.setPreferredSize(new Dimension(200, 400));// XXX
						{
							JLabel lblEast = new JLabel("EAST");
							pnlEast.add(lblEast, BorderLayout.CENTER);
							lblEast.setHorizontalAlignment(JLabel.CENTER);
						}
					}
					{
						pnlCenter = new JPanel();
						pnlMain.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setLayout(new BorderLayout(5, 5));
						pnlCenter.setBorder(lineBorder);
						{
							scrollOccupancy = new JScrollPane();
							pnlCenter.add(scrollOccupancy, BorderLayout.CENTER);
							scrollOccupancy.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollOccupancy.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							
							modelOccupancy = new model_occupancy_monitoring();
							modelOccupancy.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderOccupancy.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderOccupancy.getModel()).addElement(modelOccupancy.getRowCount());
									}
								}
							});
							

							tblOccupancy = new _JTableMain(modelOccupancy);
							tblOccupancy.addMouseListener(this);
							tblOccupancy.addKeyListener(this);
							tblOccupancy.hideColumns("Entity ID");
							tblOccupancy.hideColumns("PBL");
							tblOccupancy.setSortable(false);
							scrollOccupancy.setViewportView(tblOccupancy);
							
							rowheaderOccupancy = tblOccupancy.getRowHeader();
							rowheaderOccupancy.setModel(new DefaultListModel());
							scrollOccupancy.setRowHeaderView(rowheaderOccupancy);
							scrollOccupancy.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							
						}
					}
					{
						pnlSouth = new JPanel();
						pnlMain.add(pnlSouth, BorderLayout.SOUTH);
						pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
						pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX
						{
							pnlSouth.add(Box.createHorizontalBox());
							pnlSouth.add(Box.createHorizontalBox());
							pnlSouth.add(Box.createHorizontalBox());
							pnlSouth.add(Box.createHorizontalBox());
						}
						{
							btnEdit = new JButton("Edit");
							pnlSouth.add(btnEdit);
							btnEdit.setActionCommand("Edit");
							btnEdit.setMnemonic(KeyEvent.VK_E);
							btnEdit.addActionListener(this);
							
						}
						{
							btnSave = new JButton("Save");
							pnlSouth.add(btnSave);
							btnSave.setActionCommand("Save");
							btnSave.setMnemonic(KeyEvent.VK_S);
							btnSave.addActionListener(this);
						}
						{
							btnPreview = new JButton("Preview");
							pnlSouth.add(btnPreview);
							btnPreview.setActionCommand("Preview");
							btnPreview.setMnemonic(KeyEvent.VK_P);
							btnPreview.addActionListener(this);
						}
						{
							btnCancel = new JButton("Cancel");
							pnlSouth.add(btnCancel);
							btnCancel.setActionCommand("Cancel");
							btnCancel.setMnemonic(KeyEvent.VK_C);
							btnCancel.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									int response = JOptionPane.showConfirmDialog(OccupancyMonitoring.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
											"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
									if (response == JOptionPane.YES_OPTION) {
										lookupProject.setText("");
										refreshTO();
									}
								}
							});
						}
					}
				}

			
		}
		
		btnState(false, false, false, false);
		company_logo = sql_getCompanyLogo();
	}
	
	private void btnState(Boolean sEdit, Boolean sSave, Boolean sPreview, Boolean sCancel){
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnPreview.setEnabled(sPreview);
		btnCancel.setEnabled(sCancel);
	}
	
	private void displayAccounts(String proj_id, String block, String phase) {

		FncTables.clearTable(modelOccupancy);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT * FROM sp_generate_occupancy_monitoring_v2('"+proj_id+"', NULLIF('"+block+"', 'null'), NULLIF('"+phase+"', 'null')) order by c_client_name"; 

		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display sql for Tag Accounts", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelOccupancy.addRow(db.getResult()[x]);
				listModel.addElement(modelOccupancy.getRowCount());
			}	
		}		
		tblOccupancy.packAll();
		btnSave.setEnabled(true);
	}
	private void displayAccountswithBP(String proj_id, String block, String phase) {

		FncTables.clearTable(modelOccupancy);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT * FROM sp_generate_occupancy_monitoring_withBP('"+proj_id+"', NULLIF('"+block+"', 'null'), NULLIF('"+phase+"', 'null')) order by c_client_name"; 

		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display sql for Tag Accounts", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelOccupancy.addRow(db.getResult()[x]);
				listModel.addElement(modelOccupancy.getRowCount());
			}	
		}		
		tblOccupancy.packAll();
		btnSave.setEnabled(true);
	}
	
	private void displayAccountswithoutBP(String proj_id, String block, String phase) {

		FncTables.clearTable(modelOccupancy);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT * FROM sp_generate_occupancy_monitoring_withoutBP('"+proj_id+"', NULLIF('"+block+"', 'null'), NULLIF('"+phase+"', 'null')) order by c_client_name"; 

		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display sql for Tag Accounts", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelOccupancy.addRow(db.getResult()[x]);
				listModel.addElement(modelOccupancy.getRowCount());
			}	
		}		
		tblOccupancy.packAll();
		btnSave.setEnabled(true);
	}
	private void displayAccountswithCEI(String proj_id, String block, String phase) {

		FncTables.clearTable(modelOccupancy);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT * FROM sp_generate_occupancy_monitoring_withCEI('"+proj_id+"', NULLIF('"+block+"', 'null'), NULLIF('"+phase+"', 'null')) order by c_client_name"; 

		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display sql for Tag Accounts", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelOccupancy.addRow(db.getResult()[x]);
				listModel.addElement(modelOccupancy.getRowCount());
			}	
		}		
		tblOccupancy.packAll();
		btnSave.setEnabled(true);
	}
	private void displayAccountswithoutCEI(String proj_id, String block, String phase) {

		FncTables.clearTable(modelOccupancy);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT * FROM sp_generate_occupancy_monitoring_withoutCEI('"+proj_id+"', NULLIF('"+block+"', 'null'), NULLIF('"+phase+"', 'null')) order by c_client_name"; 

		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display sql for Tag Accounts", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelOccupancy.addRow(db.getResult()[x]);
				listModel.addElement(modelOccupancy.getRowCount());
			}	
		}		
		tblOccupancy.packAll();
		btnSave.setEnabled(true);
	}
	private void displayAccountswithOccu(String proj_id, String block, String phase) {

		FncTables.clearTable(modelOccupancy);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT * FROM sp_generate_occupancy_monitoring_withOccu('"+proj_id+"', '"+block+"', '"+phase+"') order by c_client_name"; 

		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display sql for Tag Accounts", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelOccupancy.addRow(db.getResult()[x]);
				listModel.addElement(modelOccupancy.getRowCount());
			}	
		}		
		tblOccupancy.packAll();
		btnSave.setEnabled(true);
	}
	private void displayAccountswithoutOccu(String proj_id, String block, String phase) {

		FncTables.clearTable(modelOccupancy);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT * FROM sp_generate_occupancy_monitoring_withoutOccu('"+proj_id+"', '"+block+"', '"+phase+"') order by c_client_name"; 

		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display sql for Tag Accounts", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelOccupancy.addRow(db.getResult()[x]);
				listModel.addElement(modelOccupancy.getRowCount());
			}	
		}		
		tblOccupancy.packAll();
		btnSave.setEnabled(true);
	}
	
	private void displayOccupancyMonitoringAccts(String co_id, String proj_id, String phase, String block, Integer status_index, Date date_from, Date date_to){
		FncTables.clearTable(modelOccupancy);
		DefaultListModel listModel = new DefaultListModel();
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM sp_generate_occupancy_monitoring_v3(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+block+"', 'null'), "+status_index+", NULLIF('"+date_from+"', 'null')::TIMESTAMP, NULLIF('"+date_to+"', 'null')::TIMESTAMP)";
		db.select(SQL);
		
		FncSystem.out("Display SQL for Tag Accouts", SQL);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				modelOccupancy.addRow(db.getResult()[x]);
				listModel.addElement(modelOccupancy.getRowCount());
			}
		}
		tblOccupancy.packAll();
		btnSave.setEnabled(true);
	}
	
	private void refreshTO() {
		
		if (lookupProject.getText().equals("")) {
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupBlock.setValue("");
			lookupPhase.setValue("");
			txtCompany.setText(" ");
			txtProject.setText(" ");
			modelOccupancy.setRowCount(0);
			btnSave.setEnabled(false);
			chkwithBPermit.setSelected(false);
			chkwithoutBPermit.setSelected(false);
			chkwithCEINo.setSelected(false);
			chkwithoutCEINo.setSelected(false);
			chkwithOccuNo.setSelected(false);
			chkwithoutOccuNo.setSelected(false);
			//modelOccupancy.setEditable(false);
		}
	} // refreshTO()
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		ArrayList<String> iftrue = new ArrayList<String>();
		String SQL ="";
		if(actionCommand.equals("Preview")){
			ArrayList<noticetoconstruct> listnoticetoconstruct = new ArrayList<noticetoconstruct>();
			for(int x =0; x < modelOccupancy.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelOccupancy.getValueAt(x, 0);
				String client_name = ((String) modelOccupancy.getValueAt(x, 1)).replace("'", "''");
				String phase = (String) modelOccupancy.getValueAt(x, 2);
				String block = (String) modelOccupancy.getValueAt(x, 3);
				String lot = (String) modelOccupancy.getValueAt(x, 4);
				if(isSelected){
					listnoticetoconstruct.add(new noticetoconstruct(client_name, phase, block, lot));
					System.out.printf("%s: Phase %s Block %s Lot %s\n", client_name, phase, block, lot);
				}
			}

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("company", txtCompany.getText());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));



			for (int i = 0; i < modelOccupancy.getRowCount(); i++) {
				//**EDITED BY JED 2019-08-04 DCRF NO. 1012 : ADD HOUSE MODEL FOR DISPLAY**//
				Boolean SelectedItem = (Boolean) modelOccupancy.getValueAt(i, 0);

				if (SelectedItem) {
					iftrue.add(modelOccupancy.getValueAt(i, 1).toString());
					SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +

							"SELECT \n" +
							"'"+((String)modelOccupancy.getValueAt(i, 1)).replace("'", "''")+"' AS client_name,\n" + 
							"NULLIF('"+modelOccupancy.getValueAt(i, 2)+"','null') AS phase,\n" + 
							"NULLIF('"+modelOccupancy.getValueAt(i, 3)+"','null') AS block,\n" + 
							"NULLIF('"+modelOccupancy.getValueAt(i, 4) +"','null') AS lot,\n" + 
							"NULLIF('"+modelOccupancy.getValueAt(i, 5)+"','null') AS ntc_date,\n" + 
							"NULLIF('"+modelOccupancy.getValueAt(i, 6)+"','null') AS house_mod,\n" +
							"NULLIF('"+modelOccupancy.getValueAt(i, 7)+"','null') AS bpermit_no,\n" + 
							"NULLIF('"+modelOccupancy.getValueAt(i, 8)+"','null') AS bpermit_date,\n" +
							"NULLIF('"+modelOccupancy.getValueAt(i, 9)+"','null') AS tax_dec_house_no,\n" +
							"NULLIF('"+modelOccupancy.getValueAt(i, 10)+"','null') AS tax_dec_lot_no,\n" +
							"NULLIF('"+modelOccupancy.getValueAt(i, 11)+"','null') AS cei_no,\n" + 
							"NULLIF('"+modelOccupancy.getValueAt(i, 12)+"','null') AS cei_date, \n"+
							"NULLIF('"+modelOccupancy.getValueAt(i, 13)+"','null') AS occu_no,\n" + 
							"NULLIF('"+modelOccupancy.getValueAt(i, 14)+"','null') AS occu_date\n";

				}
			}

			if (iftrue.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
				return;
			}

			FncReport.generateReport("/Reports/rptOccupancyMonitoring.jasper", "Occupancy Monitoring", mapParameters,SQL);
		}

	if(actionCommand.equals("Edit")){

			/*int row = tblOccupancy.getSelectedRow();

		if (modelOccupancy.getValueAt(row, 6) != null) {
			modelOccupancy.setEditable(false);
		} else {
			modelOccupancy.setEditable(true);
		}
		modelOccupancy.getValueAt(row,6);*/
			btnEdit.setEnabled(false);
			btnSave.setEnabled(true);
		}

	if(actionCommand.equals("Save")){//**EDITED BY JED 2019-03-22 : ENABLE SAVE AFTER EDIT VALUES IN TABLE**//
		//if (validateSaving()) {
		//if (cmbOccupancyStatus.getSelectedIndex() == 1 || cmbOccupancyStatus.getSelectedIndex() == 2) {
		ArrayList<String> ifSelected = new ArrayList<String>();
		for (int i = 0; i < modelOccupancy.getRowCount(); i++) {
			Boolean SelectedItem = (Boolean) modelOccupancy.getValueAt(i, 0);

			if(SelectedItem){
				ifSelected.add(modelOccupancy.getValueAt(i, 1).toString());

				if(JOptionPane.showConfirmDialog(getContentPane(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					for(int x =0; x < modelOccupancy.getRowCount(); x++){
						Boolean isSelected = (Boolean) modelOccupancy.getValueAt(x, 0);
						String pbl_id = (String) modelOccupancy.getValueAt(x, 16);
						String bp = (String) modelOccupancy.getValueAt(x, 7);
						String  cei = (String) modelOccupancy.getValueAt(x, 11);
						String  occupancy = (String) modelOccupancy.getValueAt(x, 13);

						if(isSelected){

							System.out.printf("cei:%s\n", cei);
							System.out.printf("occupancy:%s\n", occupancy);

							SQL = "UPDATE mf_unit_info SET building_permit_no = nullif('"+bp+"', 'null'), cei_no = nullif('"+cei+"', 'null'), occupancy_no = nullif('"+occupancy+"', 'null'), edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
									"WHERE pbl_id = '"+pbl_id+"' \n";

							FncSystem.out("Display SQL", SQL);

							pgUpdate db = new pgUpdate();
							db.executeUpdate(SQL, false);
							db.commit();

						}
					}

					JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);

					btnSave.setEnabled(false);
					modelOccupancy.setRowCount(0);
					lookupCompany.setValue(null);
					lookupProject.setValue(null);
					lookupBlock.setValue(null);
					lookupPhase.setValue(null);
					txtCompany.setText("");
					txtProject.setText("");
					txtPhase.setText("");
					modelOccupancy.setRowCount(0);
					/*chkwithBPermit.setSelected(false);
					chkwithoutBPermit.setSelected(false);
					chkwithCEINo.setSelected(false);
					chkwithoutCEINo.setSelected(false);
					chkwithOccuNo.setSelected(false);
					chkwithoutOccuNo.setSelected(false);*/
				}
			}
		}if(ifSelected.isEmpty()){
			JOptionPane.showMessageDialog(getContentPane(),"Please select a row first! ","Save", JOptionPane.OK_OPTION);
		}


		/*if(JOptionPane.showConfirmDialog(getContentPane(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){


				for(int x =0; x < modelOccupancy.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelOccupancy.getValueAt(x, 0);

					String pbl_id = (String) modelOccupancy.getValueAt(x, 15);
					String bp = (String) modelOccupancy.getValueAt(x, 6);
					String  cei = (String) modelOccupancy.getValueAt(x, 10);
					String  occupancy = (String) modelOccupancy.getValueAt(x, 12);

					if(isSelected){

						System.out.printf("cei:%s\n", cei);
						System.out.printf("occupancy:%s\n", occupancy);

						SQL = "UPDATE mf_unit_info SET building_permit_no = nullif('"+bp+"', 'null'), cei_no = nullif('"+cei+"', 'null'), occupancy_no = nullif('"+occupancy+"', 'null'), edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
								"WHERE pbl_id = '"+pbl_id+"' \n";

						FncSystem.out("Display SQL", SQL);

						pgUpdate db = new pgUpdate();
						db.executeUpdate(SQL, false);
						db.commit();

						JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);

						btnSave.setEnabled(false);
						modelOccupancy.setRowCount(0);
						lookupCompany.setValue(null);
						lookupProject.setValue(null);
						lookupBlock.setValue(null);
						lookupPhase.setValue(null);
						txtCompany.setText("");
						txtProject.setText("");
						txtPhase.setText("");
						modelOccupancy.setRowCount(0);
						chkwithBPermit.setSelected(false);
					chkwithoutBPermit.setSelected(false);
					chkwithCEINo.setSelected(false);
					chkwithoutCEINo.setSelected(false);
					chkwithOccuNo.setSelected(false);
					chkwithoutOccuNo.setSelected(false);
					}
				}

			};*/

		/*if (cmbOccupancyStatus.getSelectedIndex() == 3 || cmbOccupancyStatus.getSelectedIndex() == 4) {
			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
			}

			for(int x =0; x < modelOccupancy.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelOccupancy.getValueAt(x, 0);
				String pbl_id = (String) modelOccupancy.getValueAt(x, 15);
				String cei = (String) modelOccupancy.getValueAt(x, 10);
				//String occu = (String) modelOccupancy.getValueAt(x, 10);
				if(isSelected){

					SQL = "UPDATE mf_unit_info SET cei_no = '"+cei+"', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
							"WHERE pbl_id = '"+pbl_id+"' \n";

					pgUpdate db = new pgUpdate();
					db.executeUpdate(SQL, false);
					db.commit();
				}
			}
		}
		if (cmbOccupancyStatus.getSelectedIndex() == 5 || cmbOccupancyStatus.getSelectedIndex() == 6) {
			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
			}

			for(int x =0; x < modelOccupancy.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelOccupancy.getValueAt(x, 0);
				String pbl_id = (String) modelOccupancy.getValueAt(x, 15);
				String occu = (String) modelOccupancy.getValueAt(x, 12);
				if(isSelected){

					SQL = "UPDATE mf_unit_info SET occupancy_no = '"+occu+"', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
							"WHERE pbl_id = '"+pbl_id+"' \n";

					pgUpdate db = new pgUpdate();
					db.executeUpdate(SQL, false);
					db.commit();
				}
			}
		}*/

	}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblOccupancy)) {
			//int row = tblOccupancy.convertRowIndexToModel(tblOccupancy.getSelectedRow());

			for(int x = 0; x < modelOccupancy.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelOccupancy.getValueAt(x, 0);
				if (isSelected) {
					btnSave.setEnabled(true);
				}
			}
			if ((e.getClickCount() >= 2)) {
				clickTableColumn();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F2) {
			clickTableColumn();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { }
	
	private void clickTableColumn() {//used
		int column = tblOccupancy.getSelectedColumn();
		int row = tblOccupancy.getSelectedRow();

		if (column == 9) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Color Scheme", getColorScheme(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelOccupancy.setValueAt(data[0], row, column);
			}
		}
	}
	private String getColorScheme(){//used
		
		String sql = "select hse_color as \"Color Scheme\" from rf_marketing_scheme_pricelist group by hse_color order by hse_color";		

		FncSystem.out("Color", sql);

		return sql;

	}
	private static String sql_getCompanyLogo() {

		String a = "";

		String SQL = 
			"select company_logo from mf_company \n" + 
			"where co_id = '"+co_id+"' " ;

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {a = "";}
			else {a = (String) db.getResult()[0][0]; }

		}else{
			a = "";
		}

		return a;
	}
	private void checkAllClientList(){//

		int rw = tblOccupancy.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";
			
			try { name	= tblOccupancy.getValueAt(x,1).toString().toUpperCase();}
			 catch (NullPointerException e) { name	= ""; }
			
			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			//Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			//Boolean	end			= name.endsWith(acct_name);

			if (start==true) {
				tblOccupancy.setRowSelectionInterval(x, x); 
				tblOccupancy.changeSelection(x, 1, false, false);				
				break;			
			}
			else {
				tblOccupancy.setRowSelectionInterval(0, 0); 
				tblOccupancy.changeSelection(0, 1, false, false);					
			}

			x++;

		}		
	}
}