package Projects.ConstructionManagement;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.JXTitledSeparator;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import tablemodel.modelBankFinanceTO;
import tablemodel.modelCashTO;
import tablemodel.modelPagibigNotices;
import tablemodel.model_unit_turnover;
import tablemodel.model_unit_turnoverSent;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class GenerateForUnitTurnOverOrientation extends _JInternalFrame
		implements _GUI {
	
	private static final long serialVersionUID = 1696231374073396297L;
	
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlCenter1;
	JPanel pnlSouth;
	
	JLabel lblNorth;
	JLabel lblCenter;
	//JLabel lblBatch;
	JLabel lblOrientBatch;
	JLabel lblListFormat;
	JLabel lblLotOnly;
	JLabel lblVenue;
	
	JXTextField txtPhase;
	JXTextField txtBlock;
	JXTextField txtCompany;
	JXTextField txtProject;
	JXTextField txtSearch;
	
	_JLookup lookupProject;
	_JLookup lookupBatch;
	_JLookup lookupPhase;
	_JLookup lookupCompany;
	_JLookup lookupBlock;
	
	JComboBox cmbVenue;
	JComboBox cmbTime;
	
	JButton btnPreview;
	JButton btnSave;
	JButton btnCancel;
	
	model_unit_turnover modelUnitTO;
	JScrollPane scrollUnitTO;
	_JTableMain tblUnitTO;
	JList rowheaderUnitTO;
	
	model_unit_turnoverSent modelUnitTOSent;
	JScrollPane scrollUnitTOSent;
	_JTableMain tblUnitTOSent;
	JList rowheaderUnitTOSent;
	
	_JDateChooser dateSched;
	JSpinner time;
	
	JTabbedPane tabCenter;
	
	String co_id = "";
	String proj_id = "";
	String venue_id = "";
	String venue = "";
	
	static String title = "Generate For Unit Turn Over/Orientation";
	Dimension frameSize = new Dimension(550, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	private JPanel pnlBankFinance;
	private JScrollPane scrollBankFinance;
	private _JTableMain tblBankFinance;
	private JList rowHeaderBankFinance;
	private modelBankFinanceTO modelBankFinance;
	
	private JPanel pnlCash;
	private JScrollPane scrollCash;
	private _JTableMain tblCash;
	private JList rowHeaderCash;
	private modelCashTO modelCash;

	public GenerateForUnitTurnOverOrientation() {
		super(title, false, true, false, true);
		initGUI();
	}

	public GenerateForUnitTurnOverOrientation(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public GenerateForUnitTurnOverOrientation(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
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
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				pnlNorth.setBorder(lineBorder);
				pnlNorth.setPreferredSize(new Dimension(750, 300));// XXX
				{
					lblNorth = new JLabel("Company");
					pnlNorth.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 10, 120, 22);
				}
				{
					lookupCompany = new _JLookup(null, "Company");
					pnlNorth.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.setBounds(90, 10, 120, 22);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								String co_id = (String) data[0];
								txtCompany.setText(String.format("%s", (String) data[1]));
								lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));
								lookupBatch.setValue(null);
								modelUnitTO.clear();
								modelUnitTOSent.clear();
								
								//lookupProject.setValue(null);
								//txtProject.setText("");
								//listPhase.setModel(new DefaultComboBoxModel(new ArrayList<_JCheckListItem>().toArray()));

								//generateContractNo();
								KEYBOARD_MANAGER.focusNextComponent();
								/*
								if(tabCenter.getSelectedIndex() == 2){
									_GenerateForUnitTurnOverOrientation.displayQualifiedBankFinanceTO(modelBankFinance, rowHeaderBankFinance,  co_id, null, null, null);
									scrollBankFinance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBankFinance.getRowCount())));
									tblBankFinance.packAll();
								}*/
							}
						}
					});
				}
				{
					txtCompany = new JXTextField("");
					pnlNorth.add(txtCompany);
					txtCompany.setHorizontalAlignment(JLabel.LEFT);
					txtCompany.setBounds(215, 10, 280, 22);
					txtCompany.setEnabled(false);
				}
					{
						lblNorth = new JLabel("Project");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 35, 120, 22);
					}
					{
						lookupProject = new _JLookup(null, "Project", "Please select company.");
						pnlNorth.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setBounds(90, 35, 120, 22);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									//String co_id = lookupCompany.getValue();

									//String project_id = (String) data[0];
									//String project_name = (String) data[1];
									//String project_alias = (String) data[2];

									txtProject.setText(String.format("%s", (String) data[1]));
									lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
									//displayUnitSent(lookupCompany.getValue(), (String) data[0] );
									String proj_id = (String) data[0];
									
									KEYBOARD_MANAGER.focusNextComponent();
									
									if(tabCenter.getSelectedIndex() == 0){
										displayUnit(proj_id, null, null );
									}									
									if(tabCenter.getSelectedIndex() == 2){
										_GenerateForUnitTurnOverOrientation.displayQualifiedBankFinanceTO(modelBankFinance, rowHeaderBankFinance, lookupCompany.getValue(), proj_id, null, null);
										scrollBankFinance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBankFinance.getRowCount())));
										tblBankFinance.packAll();
									}
									if(tabCenter.getSelectedIndex() == 3){
										_GenerateForUnitTurnOverOrientation.displayQualifiedCashTO(modelCash, rowHeaderCash, lookupCompany.getValue(), proj_id, null, null);
										scrollCash.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCash.getRowCount())));
										tblCash.packAll();
									}
								}
							}
						});
					}
					{
						txtProject = new JXTextField("");
						pnlNorth.add(txtProject);
						txtProject.setHorizontalAlignment(JLabel.LEFT);
						txtProject.setBounds(215, 35, 280, 22);
						txtProject.setEnabled(false);
					}
					{
						lblNorth = new JLabel("Phase");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 60, 120, 22);
					}
					{
						lookupPhase = new _JLookup(null, "Phase", "Please select project.");
						pnlNorth.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setBounds(90, 60, 120, 22);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									
									String phase = (String) data[0];
									txtPhase.setText(String.format("Phase %s", (String) data[0]));
									lookupBlock.setLookupSQL(BLOCK((String) data[0]));
									if(tabCenter.getSelectedIndex() == 0){
										displayUnit(lookupProject.getValue(), phase, null );
									}									
									if(tabCenter.getSelectedIndex() == 2){
										_GenerateForUnitTurnOverOrientation.displayQualifiedBankFinanceTO(modelBankFinance, rowHeaderBankFinance, lookupCompany.getValue(), lookupProject.getValue(), phase, null);
										scrollBankFinance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBankFinance.getRowCount())));
										tblBankFinance.packAll();
									}
									if(tabCenter.getSelectedIndex() == 3){
										_GenerateForUnitTurnOverOrientation.displayQualifiedCashTO(modelCash, rowHeaderCash, lookupCompany.getValue(), lookupProject.getValue(), phase, null);
										scrollCash.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCash.getRowCount())));
										tblCash.packAll();
									}
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						txtPhase = new JXTextField("");
						pnlNorth.add(txtPhase);
						txtPhase.setHorizontalAlignment(JLabel.LEFT);
						txtPhase.setBounds(215, 60, 280, 22);
						txtPhase.setEnabled(false);
					}
					{
						lblNorth = new JLabel("Block");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 85, 120, 22);
					}
					{
						lookupBlock = new _JLookup(null, "Block", "Please select phase.");
						pnlNorth.add(lookupBlock);
						lookupBlock.setReturnColumn(0);
						lookupBlock.setBounds(90, 85, 120, 22);
						lookupBlock.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									txtBlock.setText(String.format("Block %s", (String) data[0]));
									if(tabCenter.getSelectedIndex() == 0){
										displayUnit(lookupProject.getValue(), lookupPhase.getValue(), (String) data[0] );
									}									
									if(tabCenter.getSelectedIndex() == 2){
										_GenerateForUnitTurnOverOrientation.displayQualifiedBankFinanceTO(modelBankFinance, rowHeaderBankFinance, lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), (String) data[0]);
										scrollBankFinance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBankFinance.getRowCount())));
										tblBankFinance.packAll();
									}
									if(tabCenter.getSelectedIndex() == 3){
										_GenerateForUnitTurnOverOrientation.displayQualifiedCashTO(modelCash, rowHeaderCash, lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), (String) data[0]);
										scrollCash.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCash.getRowCount())));
										tblCash.packAll();
									}
									
								}
							}
						});
					}
					{
						txtBlock = new JXTextField("");
						pnlNorth.add(txtBlock);
						txtBlock.setHorizontalAlignment(JLabel.LEFT);
						txtBlock.setBounds(215, 85, 280, 22);
						txtBlock.setEnabled(false);
					}
					{
						JXTitledSeparator separator = new JXTitledSeparator("");
						pnlNorth.add(separator);
						separator.setBounds(7, 115, 667, 10);
					}
					{
						JPanel pnlSearch = new JPanel(new BorderLayout(5, 5));
						pnlNorth.add(pnlSearch);
						pnlSearch.setBorder(JTBorderFactory.createTitleBorder("Search Buyer's Name"));
						pnlSearch.setBounds(10, 130, 500, 70);
						//pnlSearch.setPreferredSize(new Dimension(450, 95));
						{
							JPanel pnlUploading = new JPanel(new GridLayout(1, 1, 10, 0));
							pnlSearch.add(pnlUploading, BorderLayout.WEST);
							/*{
								JLabel lblDateTo = new JLabel("Search Client Name");
								pnlUploading.add(lblDateTo);
								lblDateTo.setPreferredSize(new java.awt.Dimension(20, 0));
							}*/
							{
								txtSearch = new JXTextField();
								pnlUploading.add(txtSearch);
								txtSearch.setPreferredSize(new java.awt.Dimension(480, 0));
								txtSearch.setHorizontalAlignment(JTextField.CENTER);	
								txtSearch.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {	
										checkAllClientList();
									}				 
								});	
							}
							
						}
					}
					{
						lblNorth = new JLabel("Orientation Schedule:");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 205, 250, 22);
					}
					{
						lblNorth = new JLabel("Date");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(40, 230, 120, 22);
					}
					{
						dateSched = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlNorth.add(dateSched);
						dateSched.setBounds(85, 230, 148, 22);
						dateSched.setDate(null);
						dateSched.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dateSched.getDateEditor()).setEditable(false);
						dateSched.setDate(Calendar.getInstance().getTime());
					}
					{
						lblNorth = new JLabel("Time");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(305, 230, 120, 22);
					}
					/*{
						time = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
						pnlNorth.add(time, BorderLayout.CENTER);
						time.setEditor(new JSpinner.DateEditor(time, "hh:mm a"));
						time.setEnabled(true);
						time.setBounds(347, 230, 148, 22);
						

						JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)time.getEditor();
						spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
					}*/
					{
						cmbTime = new JComboBox();
						pnlNorth.add(cmbTime);
						cmbTime.setActionCommand("Time");
						cmbTime.setBounds(347, 230, 148, 22);
					}
					{
						lblNorth = new JLabel("Venue");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(40, 265, 120, 22);
					}
					{
						cmbVenue = new JComboBox();
						pnlNorth.add(cmbVenue);
						cmbVenue.setActionCommand("Venue");
						cmbVenue.setBounds(85, 265, 411, 22);
					}
				{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
					{
						pnlCenter = new JPanel();
						tabCenter.addTab("For Sending - PagIbig", null, pnlCenter, null);
						pnlCenter.setLayout(new BorderLayout(5, 5));
						//pnlCenter.setBorder(lineBorder);
						{
							scrollUnitTO = new JScrollPane();
							pnlCenter.add(scrollUnitTO, BorderLayout.CENTER);
							scrollUnitTO.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollUnitTO.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							
							modelUnitTO = new model_unit_turnover();
							modelUnitTO.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderUnitTO.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderUnitTO.getModel()).addElement(modelUnitTO.getRowCount());
									}
								}
							});
							

							tblUnitTO = new _JTableMain(modelUnitTO);
							scrollUnitTO.setViewportView(tblUnitTO);
							tblUnitTO.hideColumns("Addr_no");
							tblUnitTO.hideColumns("Street");
							tblUnitTO.hideColumns("City");
							tblUnitTO.hideColumns("Province");
							tblUnitTO.hideColumns("Entity ID");
							tblUnitTO.hideColumns("Company Alias");
							tblUnitTO.hideColumns("Notice Alias");
							tblUnitTO.hideColumns("Municipality");
							tblUnitTO.hideColumns("Zip Code");
							tblUnitTO.hideColumns("PBL ID");
							tblUnitTO.hideColumns("Seq No");
							tblUnitTO.hideColumns("Color Scheme");
							tblUnitTO.hideColumns("With NTC");
							tblUnitTO.hideColumns("House Const");
							tblUnitTO.hideColumns("Move-in Date");
							tblUnitTO.hideColumns("Turn-over Date");
							tblUnitTO.hideColumns("Final Orientation");
							tblUnitTO.hideColumns("NTC Batch No");
							tblUnitTO.hideColumns("Model");
							tblUnitTO.hideColumns("Batch no");
							tblUnitTO.hideColumns("NTC");
							tblUnitTO.hideColumns("House constructed");
							
							rowheaderUnitTO = tblUnitTO.getRowHeader();
							rowheaderUnitTO.setModel(new DefaultListModel());
							scrollUnitTO.setRowHeaderView(rowheaderUnitTO);
							scrollUnitTO.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							
						}
					}
					{
						pnlCenter1 = new JPanel();
						tabCenter.addTab("Sent - PagIbig", null, pnlCenter1, null);
						pnlCenter1.setLayout(new BorderLayout(5, 5));
						//pnlCenter.setBorder(lineBorder);
						{
							scrollUnitTOSent = new JScrollPane();
							pnlCenter1.add(scrollUnitTOSent, BorderLayout.CENTER);
							scrollUnitTOSent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollUnitTOSent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							
							modelUnitTOSent = new model_unit_turnoverSent();
							modelUnitTOSent.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderUnitTOSent.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderUnitTOSent.getModel()).addElement(modelUnitTOSent.getRowCount());
									}
								}
							});
							

							tblUnitTOSent = new _JTableMain(modelUnitTOSent);
							scrollUnitTOSent.setViewportView(tblUnitTOSent);
							tblUnitTOSent.hideColumns("Addr_no");
							tblUnitTOSent.hideColumns("Street");
							tblUnitTOSent.hideColumns("City");
							tblUnitTOSent.hideColumns("Province");
							tblUnitTOSent.hideColumns("Entity ID");
							tblUnitTOSent.hideColumns("Company Alias");
							tblUnitTOSent.hideColumns("Notice Alias");
							tblUnitTOSent.hideColumns("Municipality");
							tblUnitTOSent.hideColumns("Zip Code");
							tblUnitTOSent.hideColumns("PBL ID");
							tblUnitTOSent.hideColumns("Seq No");
							tblUnitTOSent.hideColumns("Color Scheme");
							tblUnitTOSent.hideColumns("With NTC");
							tblUnitTOSent.hideColumns("House Const");
							tblUnitTOSent.hideColumns("Move-in Date");
							tblUnitTOSent.hideColumns("Turn-over Date");
							tblUnitTOSent.hideColumns("Final Orientation");
							tblUnitTOSent.hideColumns("NTC Batch No");
							tblUnitTOSent.hideColumns("NTC");
							tblUnitTOSent.hideColumns("House constructed");
							tblUnitTOSent.hideColumns("Venue");
							tblUnitTOSent.hideColumns("Date");
							tblUnitTOSent.hideColumns("Time");
							tblUnitTOSent.hideColumns("Proj");
							tblUnitTOSent.hideColumns("Proj Alias");
							
							rowheaderUnitTOSent = tblUnitTOSent.getRowHeader();
							rowheaderUnitTOSent.setModel(new DefaultListModel());
							scrollUnitTOSent.setRowHeaderView(rowheaderUnitTOSent);
							scrollUnitTOSent.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							
						}
					}
					{
						pnlBankFinance = new JPanel(new BorderLayout(3, 3));
						tabCenter.addTab("Bank Finance", null, pnlBankFinance, null);
						{
							scrollBankFinance = new JScrollPane();
							pnlBankFinance.add(scrollBankFinance, BorderLayout.CENTER);
							{

								modelBankFinance = new modelBankFinanceTO();

								tblBankFinance = new _JTableMain(modelBankFinance);
								scrollBankFinance.setViewportView(tblBankFinance);
								scrollBankFinance.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								tblBankFinance.setSortable(false);
								tblBankFinance.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq No");
							}
							{
								rowHeaderBankFinance = tblBankFinance.getRowHeader();
								rowHeaderBankFinance.setModel(new DefaultListModel());
								scrollBankFinance.setRowHeaderView(rowHeaderBankFinance);
								scrollBankFinance.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
					{
						pnlCash = new JPanel(new BorderLayout(3, 3));
						tabCenter.addTab("Cash", null, pnlCash, null);
						{
							scrollCash = new JScrollPane();
							pnlCash.add(scrollCash, BorderLayout.CENTER);
							{

								modelCash = new modelCashTO();

								tblCash = new _JTableMain(modelCash);
								scrollCash.setViewportView(tblCash);
								scrollCash.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								tblCash.setSortable(false);
								tblCash.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq No");
							}
							{
								rowHeaderCash = tblCash.getRowHeader();
								rowHeaderCash.setModel(new DefaultListModel());
								scrollCash.setRowHeaderView(rowHeaderCash);
								scrollCash.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
					tabCenter.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();

							if(selectedTab == 0){ // Enabling of buttons for the Client Info Panel
								btnPreview.setEnabled(true);
								btnSave.setEnabled(true);
								lookupBatch.setVisible(false);
							}
							if(selectedTab == 1){//Enabling of buttons for the Submitted ID Panel
								btnPreview.setEnabled(true);
								btnSave.setEnabled(false);
								lookupBatch.setVisible(true);
							}
							if(selectedTab == 2){
								lookupBatch.setValue(null);
								lookupBatch.setVisible(true);
								lookupBatch.setLookupSQL(_GenerateForUnitTurnOverOrientation.sqlBatchNo("132"));
							}
							if(selectedTab == 2){
								btnPreview.setEnabled(true);
								btnSave.setEnabled(true);
								lookupBatch.setVisible(false);
							}
							if(selectedTab == 3){
								btnPreview.setEnabled(false);
								btnSave.setEnabled(true);
								lookupBatch.setVisible(false);
							}
						}
					});
				}
					{
						pnlSouth = new JPanel();
						pnlMain.add(pnlSouth, BorderLayout.SOUTH);
						pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
						//pnlSouth.setBorder(lineBorder);
						pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX
						{
							
							lookupBatch = new _JLookup(null, "Batch");
							pnlSouth.add(lookupBatch);
							lookupBatch.setReturnColumn(0);
							lookupBatch.setVisible(false);
							lookupBatch.setLookupSQL(BATCHNO());
							lookupBatch.setBounds(120, 130, 120, 22);
							lookupBatch.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										KEYBOARD_MANAGER.focusNextComponent();
										String batch_no = (String) data[0];

										displayUnitSent(lookupProject.getValue(), (String) data[0] );
										displayCompanyDetails(batch_no);
										
										btnPreview.setEnabled(true);
										
										if(tabCenter.getSelectedIndex() == 2){
											_GenerateForUnitTurnOverOrientation.displayQualifiedBankFinanceTO(modelBankFinance, rowHeaderBankFinance, lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), lookupBatch.getValue());
											scrollBankFinance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBankFinance.getRowCount())));
											tblBankFinance.packAll();
										}
									}
								}
							});
						}
						{
							pnlSouth.add(Box.createHorizontalBox());
						}
						{
							btnPreview = new JButton("Preview");
							pnlSouth.add(btnPreview);
							btnPreview.setActionCommand("Preview");
							btnPreview.setMnemonic(KeyEvent.VK_P);
							btnPreview.addActionListener(this);
						}
						{
							btnSave = new JButton("Save");
							pnlSouth.add(btnSave);
							btnSave.setActionCommand("Save");
							btnSave.setMnemonic(KeyEvent.VK_P);
							btnSave.addActionListener(this);
						}
						{
							btnCancel = new JButton("Cancel");
							pnlSouth.add(btnCancel);
							btnCancel.setActionCommand("Cancel");
							btnCancel.setMnemonic(KeyEvent.VK_C);
							btnCancel.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									int response = JOptionPane.showConfirmDialog(GenerateForUnitTurnOverOrientation.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
											"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
									if (response == JOptionPane.YES_OPTION) {
										lookupProject.setText("...");
										refreshTO();
										
										btnPreview.setEnabled(false);
									}
								}
							});
						}
					}
			}
		}
		listVenue();
		listTime();
		//btnPreview.setEnabled(false);
	}
	private void listVenue() {
		pgSelect db = new pgSelect();
		db.select("select FORMAT('%s ~ %s', TRIM(venue_id), TRIM(venue_desc)) as venue_description from mf_orientation_venue;");
		
		if(db.isNotNull()){
			ArrayList<Object> listModel = new ArrayList<Object>();
			for(int x=0; x < db.getRowCount(); x++) {
				listModel.add(db.getResult()[x][0]);
			}
			cmbVenue.setModel(new DefaultComboBoxModel(listModel.toArray()));
		}
	}
	private void listTime() {
		pgSelect db = new pgSelect();
		db.select("select '09:00 AM' union all    \n" +
				  "select '09:30 AM' union all    \n" +
				  "SELECT '12:30 PM'  union all   \n"+
				  "select '01:00 PM' \n" );
		
		if(db.isNotNull()){
			ArrayList<Object> listModel = new ArrayList<Object>();
			for(int x=0; x < db.getRowCount(); x++) {
				listModel.add(db.getResult()[x][0]);
			}
			cmbTime.setModel(new DefaultComboBoxModel(listModel.toArray()));
		}
	}
	
	public void displayUnit(String proj, String phase, String block) {

		FncTables.clearTable(modelUnitTO);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"select distinct on (client_name, a.pbl_id) false, trim(a.description), c.model_desc, get_client_name(b.entity_id) as client_name, \n" +
				"d.addr_no, d.street, (case when e.city_name is null then h.municipality_name else e.city_name end) as city_name, (case when f.province_name is null then 'Metro Manila' else f.province_name end) as province_name, b.entity_id, 'CENQ' as company_alias," +
				"g.batch_no, i.notice_alias, h.municipality_name, d.zip_code, b.pbl_id, b.seq_no, j.hse_color as color_scheme,\n" +
				"(case when a.ntc is not null then true else false end) as with_ntc, k.ntc_batchno, null, \n" +
				"null, null, null, a.ntc, a.house_constructed, m.actual_date \n" +

				"from mf_unit_info a \n" +
				
				"left join rf_sold_unit b on a.pbl_id = b.pbl_id and a.proj_id = b.projcode \n" +
				"left join mf_product_model c on a.model_id = c.model_id \n" +
				"left join rf_entity_address d on  b.entity_id = d.entity_id and d.pref_billing and d.status_id ='A'\n" +
				"left join mf_city e on d.city_id = e.city_id \n" +
				"left join mf_province f on d.province_id = f.province_id \n" +
				"left join rf_client_notices g on b.entity_id = g.entity_id and g.pbl_id = a.pbl_id and g.projcode = a.proj_id and g.notice_id = '34' \n" +
				"left join mf_municipality h on d.municipality_id = h.municipality_id \n" +
				"left join mf_notice_type i on g.notice_id = i.notice_id \n" +
				"left join (select * from rf_marketing_scheme_pricelist where status_id = 'A') j on a.proj_id = j.proj_id and a.phase = j.phase and a.block = j.block and a.lot = j.lot \n" +
				"left join rf_buyer_status k on b.entity_id = k.entity_id and b.projcode = k.proj_id and b.pbl_id = k.pbl_id and b.seq_no = k.seq_no \n" +
				"left join (select * from co_ntp_detail where work_percent = 100 and status_id = 'A' and ntp_no in (select ntp_no from co_ntp_header where co_id = '02' and status_id = 'A')) l on a.pbl_id = l.pbl_id \n" +
				"left join rf_buyer_status m on b.entity_id = m.entity_id and b.projcode = m.proj_id and b.pbl_id = m.pbl_id and b.seq_no = m.seq_no  \n" +
				
				"where (CASE WHEN ('"+block+"' = 'All' OR NULLIF(UPPER('"+block+"'), 'NULL') IS NULL) THEN TRUE ELSE a.block ~* '"+block+"' END) \n" +
				"and (CASE WHEN ('"+phase+"' = 'All' OR NULLIF(UPPER('"+phase+"'), 'NULL') IS NULL) THEN TRUE ELSE a.phase ~* '"+phase+"' END) \n" +
				"and (CASE WHEN ('"+proj+"' = 'All' OR NULLIF(UPPER('"+proj+"'), 'NULL') IS NULL) THEN TRUE ELSE a.proj_id ~* '"+proj+"' END) \n" +
				//"and (CASE WHEN ('"+lookupBatch.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupBatch.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE g.batch_no ~* '"+lookupBatch.getValue()+"' END) \n" +
				"and get_client_name(b.entity_id) not in ('')" +
				//"and a.unit_id not in ('')" +
				//"and g.batch_no not in ('')" +
				//"and d.street not in ('')" +
				"and b.status_id='A'\n" +
				//"and case when (a.proj_id = '015' and a.pbl_id IN ('6494', '6492')) then true else a.ntc is not null end\n" +
				"and m.byrstatus_id = '32' \n"+
				"and not exists (SELECT * FROM rf_qualified4orientation where entity_id = b.entity_id and projcode = b.projcode and pbl_id = b.pbl_id and seq_no= b.seq_no and status_id = 'A') \n"+
				//"and a.pbl_id not in (select pbl_id from rf_qualified4orientation where status_id = 'A')\n" +
				//"group by a.house_constructed, k.ntc_batchno, b.pbl_id, a.description, model_desc, client_name, addr_no, street, city_name, province_name, b.entity_id, company_alias, g.batch_no, municipality_name, notice_alias, a.pbl_id, b.seq_no, j.hse_color, a.ntc, d.zip_code\n" +
				"order by client_name, a.pbl_id \n";
		

		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display SQL ", sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelUnitTO.addRow(db.getResult()[x]);
				listModel.addElement(modelUnitTO.getRowCount());				
			}	
		}		

		tblUnitTO.packAll();		
	}
	public void displayUnitSent(String phase, String block) {

		FncTables.clearTable(modelUnitTOSent);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"select distinct on (client_name, a.pbl_id) true, trim(a.description), c.model_desc, get_client_name(b.entity_id) as client_name, \n" +
				"d.addr_no, d.street, (case when e.city_name is null then h.municipality_name else e.city_name end) as city_name, (case when f.province_name is null then 'Metro Manila' else f.province_name end) as province_name, b.entity_id, 'CENQ' as company_alias," +
				"g.batch_no, i.notice_alias, h.municipality_name, d.zip_code, b.pbl_id, b.seq_no, j.hse_color as color_scheme,\n" +
				"(case when a.ntc is not null then true else false end) as with_ntc, k.ntc_batchno, null, \n" +
				"null, null, null, a.ntc, a.house_constructed, venue_desc, g.orientation_date, g.orientation_time, TRIM(n.proj_name), TRIM(n.proj_alias)\n" +

				"from mf_unit_info a \n" +
				
				"left join rf_sold_unit b on a.pbl_id = b.pbl_id and a.proj_id = b.projcode \n" +
				"left join mf_product_model c on a.model_id = c.model_id \n" +
				"left join rf_entity_address d on  b.entity_id = d.entity_id and d.pref_billing and d.status_id ='A'\n" +
				"left join mf_city e on d.city_id = e.city_id \n" +
				"left join mf_province f on d.province_id = f.province_id \n" +
				"left join rf_client_notices g on b.entity_id = g.entity_id and g.pbl_id = a.pbl_id and g.projcode = a.proj_id and g.notice_id = '34' \n" +
				"left join mf_municipality h on d.municipality_id = h.municipality_id \n" +
				"left join mf_notice_type i on g.notice_id = i.notice_id \n" +
				"left join (select * from rf_marketing_scheme_pricelist where status_id = 'A') j on a.proj_id = j.proj_id and a.phase = j.phase and a.block = j.block and a.lot = j.lot \n" +
				"left join rf_buyer_status k on b.entity_id = k.entity_id and b.projcode = k.proj_id and b.pbl_id = k.pbl_id and b.seq_no = k.seq_no \n" +
				"left join (select * from co_ntp_detail where work_percent = 100 and status_id = 'A' and ntp_no in (select ntp_no from co_ntp_header where co_id = '02' and status_id = 'A')) l on a.pbl_id = l.pbl_id \n" +
				"left join mf_orientation_venue m on g.venue_id = m.venue_id \n" +
				"left join mf_project n on g.projcode = n.proj_id \n" +
				
				"where g.batch_no = '"+lookupBatch.getText()+"'" +
				//"and g.batch_no not in ('')" +
				//"and d.street not in ('')" +
				"and b.status_id='A'\n" +
				"and a.ntc is not null\n" +
				"and a.pbl_id in (select pbl_id from rf_qualified4orientation where status_id = 'A')\n" +
				//"group by a.house_constructed, k.ntc_batchno, b.pbl_id, a.description, model_desc, client_name, addr_no, street, city_name, province_name, b.entity_id, company_alias, g.batch_no, municipality_name, notice_alias, a.pbl_id, b.seq_no, j.hse_color, a.ntc, d.zip_code\n" +
				"order by client_name, a.pbl_id \n";
		

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelUnitTOSent.addRow(db.getResult()[x]);
				listModel.addElement(modelUnitTOSent.getRowCount());				
			}	
		}		

		tblUnitTOSent.packAll();		
	}
	
	private void displayCompanyDetails(String batch_no) {
		pgSelect db = new pgSelect();
		String SQL = "select b.co_id, c.company_name\n"
				+ "from rf_client_notices a\n"
				+ "LEFT JOIN mf_project b on b.proj_id = a.projcode\n"
				+ "LEFT JOIN mf_company c on c.co_id = b.co_id\n"
				+ "where a.batch_no = '"+batch_no+"'\n"
				+ "LIMIT 1;";
		
		db.select(SQL);
		
		if(db.isNotNull()) {
			
			String co_id = (String) db.getResult()[0][0];
			String company_name = (String) db.getResult()[0][1];
			
			lookupCompany.setValue(co_id);
			txtCompany.setText(company_name);
		
		}
	}
	
	private void refreshTO() {
		
		if (lookupProject.getText().equals("...")) {
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupPhase.setValue("");
			lookupBlock.setValue("");
			lblOrientBatch.setText("");
			txtCompany.setText("");
			txtProject.setText("");
			txtPhase.setText("");
			txtBlock.setText("");
			//dateSched.setText("");
			//time.setValue("");
			modelUnitTO.setRowCount(0);
			
		}
	} // refreshTO()
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Preview")){
			
			if(tabCenter.getSelectedIndex() == 2){
				previewNoticeTurnoverBankFinance();
			} else {
				preview();
			}
		}
		if(actionCommand.equals("Save")){
			
			
			if(tabCenter.getSelectedIndex() == 0){
				preview2();
				
				save();
			}
			if(tabCenter.getSelectedIndex() == 2){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					String batch_no = _GenerateForUnitTurnOverOrientation.saveNoticeTurnover_BankFinance(modelBankFinance, dateSched.getDate(), cmbTime.getSelectedItem().toString() ,cmbVenue.getSelectedItem().toString().split("~")[0].trim());
					previewNoticeTurnoverBankFinance();
					previewTransmittal(batch_no);
					modelBankFinance.clear();
					scrollBankFinance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderBankFinance.setModel(new DefaultListModel());
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if(tabCenter.getSelectedIndex() == 3){
				save2();
			}
		}
	}

	private String BATCHNO() {
		 return "SELECT batch_no as \"Batch No.\", get_employee_name(preparedby) as \"Prepared By\", dateprep::DATE::TIMESTAMP as \"Date Prepared\"\n" + 
				"FROM rf_client_notices\n" + 
				//"LEFT JOIN rf_sold_unit b ON b.entity_id = a.entity_id and a.pbl_id = b.pbl_id and a.projcode = b.projcode\n" + 
				"WHERE notice_id = '34' and status_id = 'A' \n" + 
				"GROUP BY batch_no, get_employee_name(preparedby), dateprep::DATE::TIMESTAMP\n" + 
				"ORDER BY batch_no::INT DESC;";
	}
	private static String generateBatchNo() {
		String strSQL = "SELECT to_char(COALESCE(MAX(batch_no::INT), 0) + 1, 'FM0000000000') FROM rf_client_notices;";

		//FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	private void preview(){	
		ArrayList<String> iftrue = new ArrayList<String>();
		String SQL ="";
		
		venue_id = (String) cmbVenue.getSelectedItem();
		venue = venue_id.split("~")[1].trim();
		
		ArrayList<String> listentity = new ArrayList<String>();
		
		if (tabCenter.getSelectedIndex() == 0) {
		for (int i = 0; i < modelUnitTO.getRowCount(); i++) {
			Boolean SelectedItem = (Boolean) modelUnitTO.getValueAt(i, 0);
			String entity = (String) modelUnitTO.getValueAt(i, 8);
			
			if (SelectedItem) {
				listentity.add(entity);
				iftrue.add(modelUnitTO.getValueAt(i, 1).toString());
				SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +
						
						"SELECT \n" +
						"get_client_name('"+modelUnitTO.getValueAt(i, 8)+"', false) AS client_name,\n" + 
						"NULLIF('"+modelUnitTO.getValueAt(i, 4)+"','null') AS addr_no, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 5)+"','null') AS street, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 6)+"','null') AS city_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 7)+"','null') AS province_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 12)+"','null') AS municipality_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 13)+"','null') AS zip_code, \n" +
						"'"+modelUnitTO.getValueAt(i, 8)+"' AS entity_id, \n" +
						"'"+modelUnitTO.getValueAt(i, 9)+"' AS co_alias, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 10)+"','null') AS batch_no, \n" +
						"(select proj_alias from mf_project where proj_id = '"+lookupProject.getValue()+"') AS ref3, \n" +
						"'"+modelUnitTO.getValueAt(i, 11)+"' AS title, \n" +
						//"'' AS title, \n" +
						"get_client_address_billing('"+modelUnitTO.getValueAt(i, 8)+"') AS address, \n" +
						"get_client_name('"+modelUnitTO.getValueAt(i, 8)+"') AS addressee, \n" +
						"NULLIF(_get_client_mobileno('"+modelUnitTO.getValueAt(i, 8)+"'),'null') AS mobile_no, \n" +
						"NULLIF(_get_client_email('"+modelUnitTO.getValueAt(i, 8)+"'),'null') AS email_add, \n" +
						"'"+modelUnitTO.getValueAt(i, 1)+"' AS unit \n";
			}

		}

		if (iftrue.isEmpty()) {
			JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
			return;
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		System.out.printf("Display SQL Value: %s%n", SQL);
		
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("proj_name", txtProject.getText());
		mapParameters.put("venue", venue);
		mapParameters.put("date", dateSched.getDate());
		mapParameters.put("time", cmbTime.getSelectedItem());
		mapParameters.put("emp_alias", UserInfo.Alias);
		mapParameters.put("emp_fname", UserInfo.FirstName);
		mapParameters.put("emp_lname", UserInfo.LastName);
		mapParameters.put("emp_name", UserInfo.FullName);
		mapParameters.put("entity", listentity.toString().replace("[", "").replace("]", ""));
		//mapParameters.put("batch_no", listBatches.toString().replace("[", "").replace("]", ""));
		
		FncReport.generateReport("/Reports/rptNotcTO.jasper", "Notice for TurnOver Orientation", mapParameters, SQL);
		FncReport.generateReport("/Reports/rptTransmittalForNTConstruct.jasper", "Transmittal For TurnOver Orientation - Metro Manila", mapParameters, SQL);
		FncReport.generateReport("/Reports/rptTransmittalForNTConstructP.jasper", "Transmittal For TurnOver Orientation - Provincial", mapParameters, SQL);
		//FncReport.generateReport("/Reports/rptMBTCTO.jasper", "MBTC Enrollee List", mapParameters);
		}
		
		if (tabCenter.getSelectedIndex() == 1) {
			for (int i = 0; i < modelUnitTOSent.getRowCount(); i++) {
				Boolean SelectedItem = (Boolean) modelUnitTOSent.getValueAt(i, 0);
				String entity = (String) modelUnitTOSent.getValueAt(i, 8);
				
				if (SelectedItem) {
					listentity.add(entity);
					iftrue.add(modelUnitTOSent.getValueAt(i, 1).toString());
					SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +
							
							"SELECT \n" +
							"get_client_name('"+modelUnitTOSent.getValueAt(i, 8)+"', false) AS client_name,\n" + 
							"NULLIF('"+modelUnitTOSent.getValueAt(i, 4)+"','null') AS addr_no, \n" +
							"NULLIF('"+modelUnitTOSent.getValueAt(i, 5)+"','null') AS street, \n" +
							"NULLIF('"+modelUnitTOSent.getValueAt(i, 6)+"','null') AS city_name, \n" +
							"NULLIF('"+modelUnitTOSent.getValueAt(i, 7)+"','null') AS province_name, \n" +
							"NULLIF('"+modelUnitTOSent.getValueAt(i, 12)+"','null') AS municipality_name, \n" +
							"NULLIF('"+modelUnitTOSent.getValueAt(i, 13)+"','null') AS zip_code, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 8)+"' AS entity_id, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 9)+"' AS co_alias, \n" +
							"NULLIF('"+modelUnitTOSent.getValueAt(i, 10)+"','null') AS batch_no, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 29)+"' AS ref3, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 11)+"' AS title, \n" +
							//"'' AS title, \n" +
							"get_client_address_billing('"+modelUnitTOSent.getValueAt(i, 8)+"') AS address, \n" +
							"get_client_name('"+modelUnitTOSent.getValueAt(i, 8)+"') AS addressee, \n" +
							"NULLIF(_get_client_mobileno('"+modelUnitTOSent.getValueAt(i, 8)+"'),'null') AS mobile_no, \n" +
							"NULLIF(_get_client_email('"+modelUnitTOSent.getValueAt(i, 8)+"'),'null') AS email_add, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 25)+"' AS venue, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 26)+"' AS date, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 27)+"' AS time, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 28)+"' AS proj, \n" +
							"'"+modelUnitTOSent.getValueAt(i, 1)+"' AS unit \n";
				}
				FncSystem.out("Display TOSent", SQL);

			}

			if (iftrue.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
				return;
			}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			
			mapParameters.put("company", txtCompany.getText());
			mapParameters.put("proj_name", txtProject.getText());
			mapParameters.put("venue", venue);
			mapParameters.put("date", dateSched.getDate());
			mapParameters.put("time", cmbTime.getSelectedItem());
			mapParameters.put("emp_alias", UserInfo.Alias);
			mapParameters.put("emp_fname", UserInfo.FirstName);
			mapParameters.put("emp_lname", UserInfo.LastName);
			mapParameters.put("emp_name", UserInfo.FullName);
			mapParameters.put("entity", listentity.toString().replace("[", "").replace("]", ""));
			mapParameters.put("batch_no", lookupBatch.getValue().toString());
			
			FncReport.generateReport("/Reports/rptNotcTOSent.jasper", "Notice for TurnOver Orientation", mapParameters, SQL);
			//FncReport.generateReport("/Reports/rptTransmittalForNTConstructSent.jasper", "Transmittal For TurnOver Orientation - Metro Manila", mapParameters, SQL);
			//FncReport.generateReport("/Reports/rptTransmittalForNTConstructPSent.jasper", "Transmittal For TurnOver Orientation - Provincial", mapParameters, SQL);
			//FncReport.generateReport("/Reports/rptMBTCTO.jasper", "MBTC Enrollee List", mapParameters);
			
			previewTransmittal(lookupBatch.getValue());
			FncSystem.out("Display Batch No.",lookupBatch.getValue().toString());
			}
			FncSystem.out("Display Entity", listentity.toString().replace("[", "").replace("]", ""));
			


	}
	private void preview2(){	
		ArrayList<String> iftrue = new ArrayList<String>();
		String SQL ="";
		
		venue_id = (String) cmbVenue.getSelectedItem();
		venue = venue_id.split("~")[1].trim();
		
		ArrayList<String> listentity = new ArrayList<String>();
		
		if (tabCenter.getSelectedIndex() == 0) {
		String batch_no = batch();
		for (int i = 0; i < modelUnitTO.getRowCount(); i++) {
			Boolean SelectedItem = (Boolean) modelUnitTO.getValueAt(i, 0);
			String entity = (String) modelUnitTO.getValueAt(i, 8);
			
			if (SelectedItem) {
				listentity.add(entity);
				iftrue.add(modelUnitTO.getValueAt(i, 1).toString());
				SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +
						
						"SELECT \n" +
						"get_client_name('"+modelUnitTO.getValueAt(i, 8)+"', false) AS client_name,\n" + 
						"NULLIF('"+modelUnitTO.getValueAt(i, 4)+"','null') AS addr_no, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 5)+"','null') AS street, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 6)+"','null') AS city_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 7)+"','null') AS province_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 12)+"','null') AS municipality_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 13)+"','null') AS zip_code, \n" +
						"'"+modelUnitTO.getValueAt(i, 8)+"' AS entity_id, \n" +
						"'"+modelUnitTO.getValueAt(i, 9)+"' AS co_alias, \n" +
						"'"+batch_no+"' AS batch_no, \n" +
						"(select proj_alias from mf_project where proj_id = '"+lookupProject.getValue()+"') AS ref3, \n" +
						"'"+modelUnitTO.getValueAt(i, 11)+"' AS title, \n" +
						//"'' AS title, \n" +
						"get_client_address_billing('"+modelUnitTO.getValueAt(i, 8)+"') AS address, \n" +
						"get_client_name('"+modelUnitTO.getValueAt(i, 8)+"') AS addressee, \n" +
						"NULLIF(_get_client_mobileno('"+modelUnitTO.getValueAt(i, 8)+"'),'null') AS mobile_no, \n" +
						"NULLIF(_get_client_email('"+modelUnitTO.getValueAt(i, 8)+"'),'null') AS email_add, \n" +
						"'"+modelUnitTO.getValueAt(i, 1)+"' AS unit \n";
			}
			
		}

		if (iftrue.isEmpty()) {
			JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
			return;
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("proj_name", txtProject.getText());
		mapParameters.put("venue", venue);
		mapParameters.put("date", dateSched.getDate());
		mapParameters.put("time", cmbTime.getSelectedItem());
		mapParameters.put("emp_alias", UserInfo.Alias);
		mapParameters.put("emp_fname", UserInfo.FirstName);
		mapParameters.put("emp_lname", UserInfo.LastName);
		mapParameters.put("emp_name", UserInfo.FullName);
		mapParameters.put("entity", listentity.toString().replace("[", "").replace("]", ""));
		//mapParameters.put("batch_no", listBatches.toString().replace("[", "").replace("]", ""));
		
		FncReport.generateReport("/Reports/rptNotcTO.jasper", "Notice for TurnOver Orientation", mapParameters, SQL);
		
		//FncReport.generateReport("/Reports/rptTransmittalForNTConstruct.jasper", "Transmittal For TurnOver Orientation - Metro Manila", mapParameters, SQL);
		//FncReport.generateReport("/Reports/rptTransmittalForNTConstructP.jasper", "Transmittal For TurnOver Orientation - Provincial", mapParameters, SQL);
		//FncReport.generateReport("/Reports/rptMBTCTO.jasper", "MBTC Enrollee List", mapParameters);
		}
		
	}
	private void save(){
		
		if (tabCenter.getSelectedIndex() == 0) {
		String batch_no = batch();
		String time_ = "";
		if (cmbTime.getSelectedIndex() == 0) {
			time_ = "1970-01-01 09:00:00";
		} else if (cmbTime.getSelectedIndex() == 1){
			time_ = "1970-01-01 09:30:00";
		}else if (cmbTime.getSelectedIndex() == 2) {
			time_ = "1970-01-01 12:30:00";
		}else {
			time_ = "1970-01-01 13:00:00";
		}
		
		for(int x =0; x < modelUnitTO.getRowCount(); x++){
			String entity = (String) modelUnitTO.getValueAt(x, 8);
			Boolean isSelected = (Boolean) modelUnitTO.getValueAt(x, 0);
			String projcode = (String) lookupProject.getValue(); 
			String pbl_id = (String) modelUnitTO.getValueAt(x, 14); //
			Integer seq_no = (Integer) modelUnitTO.getValueAt(x, 15); //
 			//String batch_no = (String) lblBatch.getText();
 			//String orient_batch_no = (String) lblBatch.getText();
 			//Date date_qualified = null; 
 			//Date attended = null;
			Date date_attended = (Date) dateSched.getDate();
			String time_attended = (String) time_;
			//String tagfordel = null; 
			String assigned_by = (String) UserInfo.EmployeeCode;
			String color_scheme_id = (String) modelUnitTO.getValueAt(x, 16); //
			Boolean with_ntc = (Boolean) modelUnitTO.getValueAt(x, 17); //
			String ntc_batchno = (String) modelUnitTO.getValueAt(x, 18); //
			//String remarks = null; //
			Date ntc_date = (Date) modelUnitTO.getValueAt(x, 23); //
			//Date hcons_date = (Date) modelUnitTO.getValueAt(x, 24); //
			//Date movein_date = null; //
			//String tagfortover = null; //
			//Date tover_date = null; //
			//Date final_orientation = null; //
			Date date_orient_assigned = (Date) dateSched.getDate();
			String created_by = UserInfo.EmployeeCode;
			
			venue_id = (String) cmbVenue.getSelectedItem();
			venue = venue_id.split("~")[0].trim();
			
			if(isSelected){
				
				String strSQL = "SELECT sp_save_to_orientation_pagibig('"+entity+"', '"+projcode+"', '"+pbl_id+"', "+seq_no+","
								+ "'"+date_attended+"'::timestamp, '"+time_attended+"'::timestamp, '"+venue+"',"
								+ "'"+assigned_by+"', '"+color_scheme_id+"', '"+with_ntc+"', '"+ntc_batchno+"',"
								+ "nullif('"+ntc_date+"', 'null')::timestamp,"
								+ "'"+date_orient_assigned+"'::timestamp, '"+created_by+"','"+batch_no+"');"; 
				
				pgSelect db = new pgSelect();
				db.select(strSQL);
			}
			
		}
		previewTransmittal(batch_no);
		JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
		
			modelUnitTO.setRowCount(0);
			displayUnitSent(lookupCompany.getValue(), lookupProject.getValue());
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupPhase.setValue("");
			lookupBlock.setValue("");
			txtCompany.setText("");
			txtProject.setText("");
			txtPhase.setText("");
			txtBlock.setText("");
		}
	}
	private void save2(){
		
		String batch_no = batch();
		String time_ = "";
		if (cmbTime.getSelectedIndex() == 0) {
			time_ = "1970-01-01 09:00:00";
		} else if (cmbTime.getSelectedIndex() == 1){
			time_ = "1970-01-01 09:30:00";
		}else {
			time_ = "1970-01-01 13:00:00";
		}
		
		
		for(int x =0; x < modelCash.getRowCount(); x++){
			String entity = (String) modelCash.getValueAt(x, 7);
			Boolean isSelected = (Boolean) modelCash.getValueAt(x, 0);
			String projcode = (String) lookupProject.getValue(); 
			String pbl_id = (String) modelCash.getValueAt(x, 9); //
			Integer seq_no = (Integer) modelCash.getValueAt(x, 10); //
			Date date_attended = (Date) dateSched.getDate();
			String time_attended = (String) time_;
			String assigned_by = (String) UserInfo.EmployeeCode;
			
			Date date_orient_assigned = (Date) dateSched.getDate();
			String created_by = UserInfo.EmployeeCode;
			
			venue_id = (String) cmbVenue.getSelectedItem();
			venue = venue_id.split("~")[0].trim();
			
			if(isSelected){
				
				String strSQL = "SELECT sp_save_to_orientation_cash('"+entity+"', '"+projcode+"', '"+pbl_id+"', "+seq_no+","
								+ "'"+date_attended+"'::timestamp, '"+time_attended+"'::timestamp, '"+venue+"', '"+assigned_by+"',"
								+ "'"+date_orient_assigned+"'::timestamp, '"+created_by+"','"+batch_no+"');"; 
				
				pgSelect db = new pgSelect();
				db.select(strSQL);
				FncSystem.out("Display SQL for", strSQL);
			}
		}
		
		JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
		
			modelCash.setRowCount(0);
			displayUnitSent(lookupCompany.getValue(), lookupProject.getValue());
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupPhase.setValue("");
			lookupBlock.setValue("");
			txtCompany.setText("");
			txtProject.setText("");
			txtPhase.setText("");
			txtBlock.setText("");
	}
	private void checkAllClientList(){//

		int rw = tblUnitTO.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";
			
			try { name	= tblUnitTO.getValueAt(x,3).toString().toUpperCase();}
			 catch (NullPointerException e) { name	= ""; }
			
			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			//Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			//Boolean	end			= name.endsWith(acct_name);

			if (start==true) {
				tblUnitTO.setRowSelectionInterval(x, x); 
				tblUnitTO.changeSelection(x, 3, false, false);				
				break;			
			}
			else {
				tblUnitTO.setRowSelectionInterval(0, 0); 
				tblUnitTO.changeSelection(0, 3, false, false);					
			}

			x++;

		}		
	}
	private static String batch() {
		String batch = "";

		String SQL = "select trim(to_char(max(coalesce(batch_no::int,0))+1,'0000000000')) from rf_client_notices where status_id = 'A' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {batch = "0000000001";}
			else {batch = (String) db.getResult()[0][0]; }

		}else{
			batch = "0000000001";
		}
	
		return batch;
	}
	public static String BLOCK(String phase) {//XXX Block
		String SQL = "select block as \"Block\" from mf_unit_info where phase = '"+ phase +"' and status_id = 'A' group by block order by block::varchar;";
		return SQL;
	}
	
	private void previewNoticeTurnoverBankFinance(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelBankFinance.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelBankFinance.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelBankFinance.getValueAt(x, 7);
				String proj_id = (String) modelBankFinance.getValueAt(x, 8);
				String pbl_id = (String) modelBankFinance.getValueAt(x, 9);
				Integer seq_no = (Integer) modelBankFinance.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("orientation_date", dateSched.getDate());
		mapParameters.put("orientation_time", cmbTime.getSelectedItem());
		mapParameters.put("venue_id", cmbVenue.getSelectedItem().toString().split("~")[0].trim());
		mapParameters.put("batch_no", lookupBatch.getValue());

		//System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeTurnoverOrientation_BankFinance.jasper", "Notice for Turnover Orientation (Bank Financing)", mapParameters);
	}
	
	private void previewTransmittal(String batch_no){

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("batch_no", batch_no);
		FncReport.generateReport("/Reports/subrptTransmittalForPostOffice.jasper", "Notices Transmittal", mapParameters);

	}
}
