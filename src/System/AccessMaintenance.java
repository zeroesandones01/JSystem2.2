package System;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_AccessMain_Position;
import tablemodel.model_AccessMain_Modules;
import tablemodel.model_AccessMain_Position_a;
import tablemodel.model_AccessMain_Modules_a;;
/*import tablemodel.model_AccessMain_Modules_a;
import tablemodel.model_AccessMain_Position;
import tablemodel.model_AccessMain_Position_a;
*/


public class AccessMaintenance extends _JInternalFrame implements _GUI, ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 3360002219207819752L;
	static String title = "Access Maintenance";
	static Dimension SIZE = new Dimension(1000, 600);
	
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthlabels;
	private JLabel lblDivision;
	private JLabel lblDepartment;
	private JTabbedPane tab1;
	private JPanel pnlCenter;
	private JPanel pnlCentertables;
	private JPanel pnlPosition;
	private JPanel pnlNorthcomponents;
	private JPanel pnlPositionTable;
	private JScrollPane scrollPosition;
	private model_AccessMain_Position modelPosition;
	private _JTableMain tblPosition;
	private JList rowHeaderPosition;
	private JPanel pnlModule;
	private JPanel pnlSearchPosition;
	private JPanel pnlSearchModule;
	private JLabel lblSearchPosition;
	private JTextField txtSearchPosition;
	private JTextField txtSearchModule;
	private JPanel pnlModuleTable;
	private JScrollPane scrollModule;
	private _JTableMain tblModule;
	private model_AccessMain_Modules modelModule;
	private JList rowHeaderModule;
	private JPanel pnlSouth;
	private JButton saveButton;
	private JButton editButton;
	private JButton previewButton;
	private JButton refreshButton;
	private _JLookup lookupDivision;
	private JLabel lblDiv;
	private JPanel pnlDivisionfield;
	private JPanel pnlDepartmentfield;
	private _JLookup lookupDepartment;
	private JLabel lblDept;
	private JPanel pnlCentertables_a;
	private JPanel pnlPosition_a;
	private JPanel pnlSearchPosition_a;
	private JLabel lblSearchPosition_a;
	private JTextField txtSearchPosition_a;
	private JPanel pnlPositionTable_a;
	private JScrollPane scrollPosition_a;
	private _JTableMain tblPosition_a;
	private model_AccessMain_Position_a modelPosition_a;
	private JList rowHeaderPosition_a;
	private JPanel pnlModule_a;
	private JPanel pnlSearchModule_a;
	private JLabel lblSearchModule;
	private JLabel lblSearchModule_a;
	private JTextField txtSearchModule_a;
	private JPanel pnlModuleTable_a;
	private JScrollPane scrollModule_a;
	private model_AccessMain_Modules_a modelModule_a;
	private _JTableMain tblModule_a;
	private JList rowHeaderModule_a;
	private String div_code;
	private String dept_code;
	private Integer pos_code;
	private String pos_name;
	private pgUpdate db;
	private JPanel pnlPositionLabel;
	private JPanel pnlPositionName;
	private JLabel lblPositionName;
	private JPanel pnlSelectedPosition;
	private JLabel lblSelectedPosition;
	private Integer submodule_id;
	private String submodule_desc;
	private JButton cancelButton;
	private JPanel pnlModuleLabel;
	private JPanel pnlModuleName;
	private JLabel lblModuleName;
	private JPanel pnlSelectedModule;
	private JLabel lblSelectedModule;
	private JButton cancelButton_a;
	private JPanel pnlCheckBox;
	private JPanel pnlSearchComponents;
	private JPanel pnlTextComponents;
	private JCheckBox cboxSavedModule;
	private JCheckBox cboxSavedPosition;

	public AccessMaintenance() {
		super(title, true, true, true, true);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(550, 600));
		this.setBounds(0, 0, 550, 600);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			{
				pnlNorth = new JPanel(new BorderLayout(20, 20));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 50));
				{
					pnlNorthlabels = new JPanel(new GridLayout(2, 1, 20, 20));
					pnlNorth.add(pnlNorthlabels, BorderLayout.WEST);
					pnlNorthlabels.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
					{
						lblDivision = new JLabel("Div:");
						pnlNorthlabels.add(lblDivision);
						lblDivision.setFont(new Font("SanSerif", Font.PLAIN, 13));
					}
					{
						lblDepartment = new JLabel("Dept:");
						pnlNorthlabels.add(lblDepartment);
						lblDepartment.setFont(new Font("SanSerif", Font.PLAIN, 13));
					}
					{
						pnlNorthcomponents = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlNorth.add(pnlNorthcomponents, BorderLayout.CENTER);
						{
							pnlDivisionfield = new JPanel(new BorderLayout(5, 5));
							pnlNorthcomponents.add(pnlDivisionfield, BorderLayout.CENTER);
							{
								lookupDivision = new _JLookup(null, "Division");
								pnlDivisionfield.add(lookupDivision, BorderLayout.WEST);
								lookupDivision.setLookupSQL(sqlDivision());
								lookupDivision.setReturnColumn(0);
								lookupDivision.setPreferredSize(new Dimension(50, 0));
								lookupDivision.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											FncSystem.out("Display sql for description:", lookupDivision.getLookupSQL());
											String div_name = (String) data[1];
											String div_code = (String) data[0];

											lblDiv.setText(String.format("[ %s ]", div_name));
											lookupDepartment.setLookupSQL(sqlDepartment(div_code));
											lookupDepartment.setEnabled(true);

											if (tab1.getSelectedIndex() == 0) {

												modelPosition.clear();
												modelModule.clear();

											}

											if (tab1.getSelectedIndex() == 1) {

												modelPosition_a.clear();
												modelModule_a.clear();

											}
										}
									}
								});
							}
							{
								lblDiv = new JLabel("[ ]");
								pnlDivisionfield.add(lblDiv, BorderLayout.CENTER);
							}
							{
								pnlDepartmentfield = new JPanel(new BorderLayout(5, 5));
								pnlNorthcomponents.add(pnlDepartmentfield, BorderLayout.CENTER);
								{
									lookupDepartment = new _JLookup(null, "Department");
									pnlDepartmentfield.add(lookupDepartment, BorderLayout.WEST);
									lookupDepartment.setReturnColumn(0);
									lookupDepartment.setPreferredSize(new Dimension(50, 0));
									lookupDepartment.setEnabled(false);
									lookupDepartment.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												FncSystem.out("Display sql for description:", lookupDepartment.getLookupSQL());
												String dept_name = (String) data[1];
												div_code = lookupDivision.getValue();
												dept_code = lookupDepartment.getValue();
												lblDept.setText(String.format("[ %s ]", dept_name));

												if (tab1.getSelectedIndex() == 0) {

													txtSearchPosition.setEditable(true);
													tblPosition.setEnabled(true);
													lookupDivision.setEnabled(false);
													lookupDepartment.setEnabled(false);
													tab1.setEnabled(false);

													displayPosition(modelPosition, rowHeaderPosition, div_code, dept_code);

												}

												if (tab1.getSelectedIndex() == 1) {

													txtSearchModule_a.setEditable(true);
													tblModule_a.setEnabled(true);
													lookupDivision.setEnabled(false);
													lookupDepartment.setEnabled(false);
													tab1.setEnabled(false);

													displayAllModules(modelModule_a, rowHeaderModule_a);

												}
											}
										}
									});
								}
								{
									lblDept = new JLabel("[ ]");
									pnlDepartmentfield.add(lblDept, BorderLayout.CENTER);
								}
							}
						}
					}
				}

				// panel for the 1st tab
				{
					pnlCenter = new JPanel(new BorderLayout(5, 5));
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					{
						tab1 = new JTabbedPane();
						pnlCenter.add(tab1, BorderLayout.CENTER);
						{
							pnlCentertables = new JPanel(new GridLayout(2, 1, 10, 10));
							tab1.addTab("Tab1", pnlCentertables);
							{
								pnlPosition = new JPanel(new BorderLayout(5, 5));
								pnlCentertables.add(pnlPosition, BorderLayout.NORTH);
								pnlPosition.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
								{
									pnlSearchPosition = new JPanel(new BorderLayout(5, 5));
									pnlPosition.add(pnlSearchPosition, BorderLayout.NORTH);
									// pnlSearchPosition.setPreferredSize(new Dimension(0, 40));
									pnlSearchPosition.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
									{
										lblSearchPosition = new JLabel("*Search:");
										pnlSearchPosition.add(lblSearchPosition, BorderLayout.WEST);
									}
									{
										txtSearchPosition = new _JXTextField("Search Name");
										pnlSearchPosition.add(txtSearchPosition, BorderLayout.CENTER);
										// txtSearchPosition.setEnabled(false);
										txtSearchPosition.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent e) {
												searchPosition(modelPosition, rowHeaderPosition, txtSearchPosition.getText(), div_code, dept_code);
											}
										});
									}
									{
										pnlPositionTable = new JPanel(new BorderLayout(5, 5));
										pnlPosition.add(pnlPositionTable, BorderLayout.CENTER);
										pnlPositionTable.setBorder(JTBorderFactory.createTitleBorder("List of Positions"));
										{
											scrollPosition = new JScrollPane();
											pnlPositionTable.add(scrollPosition);
											scrollPosition.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										}
										{
											modelPosition = new model_AccessMain_Position();
											tblPosition = new _JTableMain(modelPosition);

											scrollPosition.setViewportView(tblPosition);
											tblPosition.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
											tblPosition.setSortable(false);
											tblPosition.setEnabled(false);
											tblPosition.getColumn(0).setPreferredWidth(160);
											tblPosition.getColumn(1).setPreferredWidth(190);
											tblPosition.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

												@Override
												public void valueChanged(ListSelectionEvent e) {
													if (!e.getValueIsAdjusting()) {
														if (tblPosition.getSelectedRows().length > 0) {
															int rw = tblPosition.getSelectedRow();
															pos_code = Integer.parseInt(tblPosition.getValueAt(rw, 0).toString());
															pos_name = tblPosition.getValueAt(rw, 1).toString();

															displayModules(modelModule, rowHeaderModule, pos_code, pos_name, div_code, dept_code);

															editButton.setEnabled(true);

															txtSearchModule.setEditable(true);

															cboxSavedModule.setEnabled(true);

															cboxSavedModule.setSelected(false);

														}
													}
												}
											});
										}
										{
											rowHeaderPosition = tblPosition.getRowHeader();
											rowHeaderPosition.setModel(new DefaultListModel());
											scrollPosition.setRowHeaderView(rowHeaderPosition);
											scrollPosition.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
										}
									}
									{
										pnlPositionLabel = new JPanel(new BorderLayout(5, 5));
										pnlPosition.add(pnlPositionLabel, BorderLayout.SOUTH);
										pnlPositionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 3, 3));
										{
											pnlPositionName = new JPanel(new BorderLayout(5, 5));
											pnlPositionLabel.add(pnlPositionName, BorderLayout.WEST);
											{
												lblPositionName = new JLabel("Selected POSITION:");
												pnlPositionName.add(lblPositionName);
											}
										}
										{
											pnlSelectedPosition = new JPanel(new BorderLayout(5, 5));
											pnlPositionLabel.add(pnlSelectedPosition, BorderLayout.CENTER);
											pnlSelectedPosition.setPreferredSize(new Dimension(0, 20));
											{
												lblSelectedPosition = new JLabel("[ ]");
												pnlSelectedPosition.add(lblSelectedPosition);
											}
											{
												cancelButton = new JButton("Cancel");
												pnlSelectedPosition.add(cancelButton, BorderLayout.EAST);
												cancelButton.setActionCommand("Cancel Selection");
												cancelButton.addActionListener(this);
												cancelButton.setEnabled(false);
											}
										}
									}
								}
							}
							{
								pnlModule = new JPanel(new BorderLayout(5, 5));
								pnlCentertables.add(pnlModule, BorderLayout.SOUTH);
								pnlModule.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
								{
									pnlSearchModule = new JPanel(new BorderLayout(5, 5));
									pnlModule.add(pnlSearchModule, BorderLayout.NORTH);
									pnlSearchModule.setPreferredSize(new Dimension(0, 35));
									pnlSearchModule.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
									{
										pnlSearchComponents = new JPanel(new GridLayout(1, 1, 5, 5));
										pnlSearchModule.add(pnlSearchComponents, BorderLayout.WEST);
										// pnlSearchComponents.setBorder(BorderFactory.createEmptyBorder(20,5,5,5));
										{
											lblSearchModule = new JLabel("*Search:");
											pnlSearchComponents.add(lblSearchModule);
										}
									}
									{
										pnlTextComponents = new JPanel(new GridLayout(1, 1, 5, 5));
										pnlSearchModule.add(pnlTextComponents, BorderLayout.CENTER);
										{
											txtSearchModule = new _JXTextField("Search Module");
											pnlTextComponents.add(txtSearchModule, BorderLayout.CENTER);
											// txtSearchModule.setEnabled(false);
											// txtSearchModule.setPreferredSize(new
											// Dimension (20,0));
											txtSearchModule.addKeyListener(new KeyAdapter() {
												public void keyReleased(KeyEvent e) {
													searchModuleName();
												}
											});
										}
									}
									{
										pnlCheckBox = new JPanel(new GridLayout(1, 1, 5, 5));
										pnlSearchModule.add(pnlCheckBox, BorderLayout.EAST);
										{
											cboxSavedModule = new JCheckBox("Show Saved Module");
											pnlCheckBox.add(cboxSavedModule);
											cboxSavedModule.setEnabled(false);
											cboxSavedModule.addItemListener(new ItemListener() {

												@Override
												public void itemStateChanged(ItemEvent e) {

													Boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);

													showSavedData(isSelected);

												}
											});
										}
									}
									{
										pnlModuleTable = new JPanel(new BorderLayout(5, 5));
										pnlModule.add(pnlModuleTable, BorderLayout.CENTER);
										pnlModuleTable.setBorder(JTBorderFactory.createTitleBorder("List of Modules"));
										{
											scrollModule = new JScrollPane();
											pnlModuleTable.add(scrollModule);
											scrollModule.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										}
										{
											modelModule = new model_AccessMain_Modules();
											tblModule = new _JTableMain(modelModule);

											scrollModule.setViewportView(tblModule);
											tblModule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
											tblModule.setSortable(false);
											tblModule.setEnabled(false);
											tblModule.hideColumns("Parent");
											tblModule.hideColumns("Module");
											tblModule.getColumn(0).setPreferredWidth(160);
											tblModule.getColumn(1).setPreferredWidth(160);
											tblModule.getColumn(2).setPreferredWidth(200);
										}
										{
											rowHeaderModule = tblModule.getRowHeader();
											rowHeaderModule.setModel(new DefaultListModel());
											scrollModule.setRowHeaderView(rowHeaderModule);
											scrollModule.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
										}
									}
								}
							}
						}

						// panel for the 2nd tab
						{
							pnlCentertables_a = new JPanel(new GridLayout(2, 1, 5, 5));
							tab1.add("Tab2", pnlCentertables_a);
							{
								pnlModule_a = new JPanel(new BorderLayout(5, 5));
								pnlCentertables_a.add(pnlModule_a, BorderLayout.NORTH);
								pnlModule_a.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
								{
									pnlSearchModule_a = new JPanel(new BorderLayout(5, 5));
									pnlModule_a.add(pnlSearchModule_a, BorderLayout.NORTH);
									// pnlSearchModule.setPreferredSize(new
									// Dimension(0, 40));
									pnlSearchModule_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
									{
										lblSearchModule_a = new JLabel("*Search:");
										pnlSearchModule_a.add(lblSearchModule_a, BorderLayout.WEST);
									}
									{
										txtSearchModule_a = new _JXTextField("Search Module");
										pnlSearchModule_a.add(txtSearchModule_a, BorderLayout.CENTER);
										// txtSearchModule_a.setEnabled(false);
										txtSearchModule_a.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent e) {
												searchModuleName();
											}
										});
									}
									{
										pnlModuleTable_a = new JPanel(new BorderLayout(5, 5));
										pnlModule_a.add(pnlModuleTable_a, BorderLayout.CENTER);
										pnlModuleTable_a.setBorder(JTBorderFactory.createTitleBorder("List of Modules"));
										{
											scrollModule_a = new JScrollPane();
											pnlModuleTable_a.add(scrollModule_a);
											scrollModule_a.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

										}
										{
											modelModule_a = new model_AccessMain_Modules_a();
											tblModule_a = new _JTableMain(modelModule_a);
											tblModule_a.setEnabled(false);

											scrollModule_a.setViewportView(tblModule_a);
											tblModule_a.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
											tblModule_a.setSortable(false);
											tblModule_a.hideColumns("Parent");
											tblModule_a.hideColumns("Module");
											tblModule_a.getColumn(0).setPreferredWidth(160);
											tblModule_a.getColumn(1).setPreferredWidth(180);
											tblModule_a.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

												@Override
												public void valueChanged(ListSelectionEvent e) {
													if (!e.getValueIsAdjusting()) {
														if (tblModule_a.getSelectedRows().length > 0) {
															int rw = tblModule_a.getSelectedRow();
															submodule_id = Integer.parseInt(tblModule_a.getValueAt(rw, 0).toString());
															submodule_desc = tblModule_a.getValueAt(rw, 1).toString();

															displaySelectedPosition(modelPosition_a, rowHeaderPosition_a, submodule_id, submodule_desc, div_code, dept_code);

															editButton.setEnabled(true);

															txtSearchPosition_a.setEditable(true);

															cboxSavedPosition.setEnabled(true);

															cboxSavedPosition.setSelected(false);

														}
													}
												}
											});
										}
										{
											rowHeaderModule_a = tblModule_a.getRowHeader();
											rowHeaderModule_a.setModel(new DefaultListModel());
											scrollModule_a.setRowHeaderView(rowHeaderModule_a);
											scrollModule_a.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
										}
									}
									{
										pnlModuleLabel = new JPanel(new BorderLayout(5, 5));
										pnlModule_a.add(pnlModuleLabel, BorderLayout.SOUTH);
										pnlModuleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 3, 3));
										{
											pnlModuleName = new JPanel(new BorderLayout(5, 5));
											pnlModuleLabel.add(pnlModuleName, BorderLayout.WEST);
											{
												lblModuleName = new JLabel("Selected MODULE NAME:");
												pnlModuleName.add(lblModuleName);
											}
										}
										{
											pnlSelectedModule = new JPanel(new BorderLayout(5, 5));
											pnlModuleLabel.add(pnlSelectedModule, BorderLayout.CENTER);
											{
												lblSelectedModule = new JLabel("[ ]");
												pnlSelectedModule.add(lblSelectedModule);
											}
											{
												cancelButton_a = new JButton("Cancel");
												pnlSelectedModule.add(cancelButton_a, BorderLayout.EAST);
												cancelButton_a.setActionCommand("Cancel Selection");
												cancelButton_a.addActionListener(this);
												cancelButton_a.setEnabled(false);
											}
										}
									}
								}
							}
							{
								pnlPosition_a = new JPanel(new BorderLayout(5, 5));
								pnlCentertables_a.add(pnlPosition_a, BorderLayout.NORTH);
								pnlPosition_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
								{
									pnlSearchPosition_a = new JPanel(new BorderLayout(5, 5));
									pnlPosition_a.add(pnlSearchPosition_a, BorderLayout.NORTH);
									// pnlSearchPosition.setPreferredSize(new Dimension(0, 40));
									pnlSearchPosition_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
									{
										lblSearchPosition_a = new JLabel("*Search:");
										pnlSearchPosition_a.add(lblSearchPosition_a, BorderLayout.WEST);
									}
									{
										txtSearchPosition_a = new _JXTextField("Search Name");
										pnlSearchPosition_a.add(txtSearchPosition_a, BorderLayout.CENTER);
										// txtSearchPosition_a.setEnabled(false);
										txtSearchPosition_a.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent e) {
												searchPositionName();
											}
										});
									}
									{
										cboxSavedPosition = new JCheckBox("Show Saved Position");
										pnlSearchPosition_a.add(cboxSavedPosition, BorderLayout.EAST);
										cboxSavedPosition.setEnabled(false);
										cboxSavedPosition.addItemListener(new ItemListener() {

											@Override
											public void itemStateChanged(ItemEvent e) {

												Boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);

												showSavedData(isSelected);

											}
										});
									}
									{
										pnlPositionTable_a = new JPanel(new BorderLayout(5, 5));
										pnlPosition_a.add(pnlPositionTable_a, BorderLayout.CENTER);
										pnlPositionTable_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
										pnlPositionTable_a.setBorder(JTBorderFactory.createTitleBorder("List of Positions"));
										{
											scrollPosition_a = new JScrollPane();
											pnlPositionTable_a.add(scrollPosition_a);
											scrollPosition_a.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										}
										{
											modelPosition_a = new model_AccessMain_Position_a();
											tblPosition_a = new _JTableMain(modelPosition_a);
											tblPosition_a.setEnabled(false);

											scrollPosition_a.setViewportView(tblPosition_a);
											tblPosition_a.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
											tblPosition_a.setSortable(false);
											tblPosition_a.getColumn(0).setPreferredWidth(160);
											tblPosition_a.getColumn(1).setPreferredWidth(160);
											tblPosition_a.getColumn(2).setPreferredWidth(190);

										}
										{
											rowHeaderPosition_a = tblPosition_a.getRowHeader();
											rowHeaderPosition_a.setModel(new DefaultListModel());
											scrollPosition_a.setRowHeaderView(rowHeaderPosition_a);
											scrollPosition_a.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
										}
									}
								}
							}
						}
						{
							pnlSouth = new JPanel(new GridLayout(1, 4, 5, 5));
							pnlMain.add(pnlSouth, BorderLayout.SOUTH);
							pnlSouth.setPreferredSize(new Dimension(0, 30));
							{
								saveButton = new JButton("Save");
								pnlSouth.add(saveButton);
								saveButton.setActionCommand("Save Modules");
								saveButton.addActionListener(this);
								saveButton.setEnabled(false);
							}
							{
								editButton = new JButton("Edit");
								pnlSouth.add(editButton);
								editButton.setActionCommand("Edit Modules");
								editButton.addActionListener(this);
								editButton.setEnabled(false);
							}
							{
								previewButton = new JButton("Preview");
								pnlSouth.add(previewButton);
							}
							{
								refreshButton = new JButton("Refresh");
								pnlSouth.add(refreshButton);
								refreshButton.setActionCommand("Refresh");
								refreshButton.addActionListener(this);
							}
						}
					}
				}
			}
		}
	}

	private String sqlDivision() {
		String sql = "Select\n" + 
				"division_code as \"ID\",\n" + 
				"division_name as \"Division Name\",\n" + 
				"division_alias as \"Alias\"\n" + 
				"from mf_division\n" + 
				"where status_id = 'A' ";

		return sql;
	}

	private String sqlDepartment(String div_code) {
		String sql = "select \n" + 
				"dept_code as \"ID\",\n" + 
				"dept_name as \"Department Name\",\n" + 
				"dept_alias as \"Alias\"\n" + 
				"from mf_department\n" + 
				"where status_id = 'A'\n" + 
				"and division_code = '"+div_code+"' ";

		return sql;
	}

	// Display tables

	private void displayPosition(DefaultTableModel modelMain, JList rowHeader, String div_code, String dept_code) {

		FncTables.clearTable(modelMain); // Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel(); // Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); // Setting of DefaultListModel into rowHeader.

		String sql = "select distinct on (position_name)\n" + 
				"position_code,\n" + 
				"TRIM(position_name)\n" + 
				"from mf_position \n" + 
				"where div_code = '"+div_code+"' and dept_code = '"+dept_code+"' and status_id = 'A' ";

		FncSystem.out("SQL for displaying all position", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) { 
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
	}

	private void displaySelectedPosition(DefaultTableModel modelMain, JList rowHeader, Integer submodule_id,
			String submodule_desc, String div_code, String dept_code) {

		FncTables.clearTable(modelMain); // Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel(); // Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); // Setting of DefaultListModel into rowHeader.

		String sql = "select distinct on (a.position_name, a.position_code)\n" + 
				"(select exists(select position_name from mf_position_modules where position_code = a.position_code and submodule_id = '"+submodule_id+"' and submodule_desc = '"+submodule_desc+"' and div_code = '"+div_code+"' and dept_code = '"+dept_code+"' and status_id = 'A')),\n" + 
				"a.position_code,\n" + 
				"a.position_name\n" + 
				"from mf_position a\n" + 
				"left join mf_position_modules b on a.position_code = b.position_code\n" + 
				"where a.div_code = '"+div_code+"' and a.dept_code = '"+dept_code+"' \n" +
				"order by a.position_code ";

		FncSystem.out("SQL for displaying available position per module", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
	}

	private void displaySavedPositions(DefaultTableModel modelMain, JList rowHeader, Integer submodule_id,
			String submodule_desc, String div_code, String dept_code) {

		FncTables.clearTable(modelMain); // Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel(); // Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); // Setting of DefaultListModel into rowHeader.

		String sql = 
				"select\n" + 
				"true,\n" + 
				"position_code,\n" + 
				"position_name\n" + 
				"from mf_position_modules\n" + 
				"where submodule_id = '"+submodule_id+"' \n" + 
				"and submodule_desc = '"+submodule_desc+"' \n" + 
				"and div_code = '"+div_code+"' \n" + 
				"and dept_code = '"+dept_code+"' \n" + 
				"and status_id = 'A'\n" +
				"order by position_code" ;

		FncSystem.out("SQL for displaying all granted positions per module", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
	}

	public static void searchPosition(DefaultTableModel modelMain, JList rowHeader, String str, String div_code,
			String dept_code) {

		FncTables.clearTable(modelMain); // Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel(); // Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); // Setting of DefaultListModel into rowHeader.

		String sql = 
				"select distinct on (a.position_name)\n" + 
				"position_code,\n" + 
				"TRIM(a.position_name)\n" + 
				"from mf_position a\n" + 
				"left join em_employee b on a.position_name = b.emp_pos\n" + 
				"where a.position_name ~* '"+str.toUpperCase()+"' \n" + 
				"and a.div_code = '"+div_code+"' \n" + 
				"and a.dept_code = '"+dept_code+"' ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());

			}
		}
	}

	private void displayModules(DefaultTableModel modelMain, JList rowHeader, Integer pos_code, String pos_name,
			String div_code, String dept_code) {

		FncTables.clearTable(modelMain); // Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel(); // Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); // Setting of DefaultListModel into rowHeader.

		String sql = 
				"select distinct on (a.submodule_desc, a.submodule_id)\n" + 
				"(SELECT EXISTS(SELECT * FROM mf_position_modules b where submodule_id = a.submodule_id and position_code = '"+pos_code+"' and position_name = '"+pos_name+"' and div_code = '"+div_code+"' and dept_code = '"+dept_code+"' and status_id = 'A')),\n" + 
				"a.submodule_id,\n" + 
				"a.submodule_desc,\n" + 
				"c.module,\n" + 
				"c.parent\n" + 
				"from mf_system_submodules a\n" + 
				"left join mf_position_modules b on a.submodule_id = b.submodule_id\n" + 
				"left join mf_privileges c on a.submodule_desc = c.privileges\n" + 
				"where a.status_id = 'A' \n" + 
				"order by a.submodule_id ";

		FncSystem.out("SQL for displaying all modules", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
		tblModule.packAll();
	}

	private void displaySavedModules(DefaultTableModel modelMain, JList rowHeader, Integer pos_code, String pos_name,
			String div_code, String dept_code) {

		FncTables.clearTable(modelMain); // Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel(); // Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); // Setting of DefaultListModel into rowHeader.

		String sql = 
				"select distinct on (a.submodule_id) \n" + 
				"true,\n" + 
				"a.submodule_id,\n" + 
				"a.submodule_desc,\n" + 
				"b.module,\n" + 
				"b.parent\n" + 
				"from mf_position_modules a\n" + 
				"left join mf_privileges b on a.submodule_desc = b.privileges\n" + 
				"where a.position_code = '"+pos_code+"'\n" + 
				"and a.position_name = '"+pos_name+"'\n" + 
				"and a.div_code = '"+div_code+"'\n" + 
				"and a.dept_code = '"+dept_code+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"order by a.submodule_id ";

		FncSystem.out("SQL for displaying all saved modules per position", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
		tblModule.packAll();
	}

	private void displayAllModules(DefaultTableModel modelMain, JList rowHeader) {

		FncTables.clearTable(modelMain); // Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel(); // Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); // Setting of DefaultListModel into rowHeader.

		String sql = 
				"select distinct on (submodule_id)\n" +
				"submodule_id, \n" + 
				"submodule_desc,\n" + 
				"b.module,\n" + 
				"b.parent\n" + 
				"from mf_system_submodules a\n" + 
				"left join mf_privileges b on b.privileges = a.submodule_desc\n" + 
				"where status_id = 'A'\n" + 
				"order by submodule_id ";

		FncSystem.out("SQL for displaying available modules", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
		tblModule_a.packAll();
	}
	

	// for granting privileges

	private static Integer getNumberOfUnits(String pos_name, Integer pos_code, String div_code, String dept_code) {

		Integer x = 0;

		String sql = 
				"Select sum(a.count)::INT from\n" + 
				"(select distinct on (b.emp_code) 1 as count,\n" + 
				"b.emp_code,\n" + 
				"c.entity_name,\n" + 
				"e.position_code,\n" + 
				"b.emp_pos\n" + 
				"from mf_privileges a\n" + 
				"left join em_employee b on a.emp_code = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"left join mf_employee_status d on b.emp_status = d.empstatus_code\n" + 
				"left JOIN mf_position e on e.position_name = b.emp_pos\n" + 
				"left join mf_system_submodules f on a.privileges = f.submodule_desc\n" + 
				"where b.emp_pos = '"+pos_name+"'\n" +
				"and e.position_code = '"+pos_code+"'\n" + 
				"and b.div_code = '"+div_code+"'\n" + 
				"and b.dept_code = '"+dept_code+"'\n"+ 
				"and not d.empstatus_desc = 'Inactive' and not d.empstatus_desc = 'End of Contract') a ";

		FncSystem.out("The number of units is:", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {

			if ((Integer) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				x = 0;
			} else {
				x = (Integer) db.getResult()[0][0];
			}
		} else {
			x = 0;
		}

		return x;
	}

	private static Object[][] getListofEmployees(String pos_name, Integer pos_code, String div_code, String dept_code) {

		String sql = 
				"select distinct on (b.emp_code)\n" + 
				"b.emp_code,\n" + 
				"c.entity_name,\n" + 
				"e.position_code,\n" + 
				"b.emp_pos\n" + 
				"from mf_privileges a\n" + 
				"left join em_employee b on a.emp_code = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"left join mf_employee_status d on b.emp_status = d.empstatus_code\n" + 
				"left JOIN mf_position e on e.position_name = b.emp_pos\n" + 
				"left join mf_system_submodules f on a.privileges = f.submodule_desc\n" + 
				"where b.emp_pos = '"+pos_name+"'\n" + 
				"and e.position_code = '"+pos_code+"'\n" + 
				"and b.div_code = '"+div_code+"'\n" + 
				"and b.dept_code = '"+dept_code+"'\n" + 
				"and not d.empstatus_desc = 'Inactive' and not d.empstatus_desc = 'End of Contract'";

		// System.out.printf("List of Employees : " + sql + "\n");
		FncSystem.out("List of Employees:", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {
			return db.getResult();
		} else {
			return null;
		}
	}

	// for untagging privileges

	private static Integer getNumberOfUnitsToBeDelete(String pos_name, Integer submodule_id, String submodule_desc, Integer pos_code, String div_code, String dept_code) {

		Integer x = 0;

		String sql = 
				"SELECT sum(a.count)::INT from\n" + 
				"(select distinct on (b.entity_id) 1 as count,\n" + 
				"b.emp_code, \n" + 
				"c.entity_name, \n" + 
				"b.emp_pos, \n" + 
				"a.module, \n" + 
				"a.privileges \n" + 
				"from mf_privileges a \n" + 
				"left join em_employee b on a.emp_code = b.emp_code \n" + 
				"left join rf_entity c on b.entity_id = c.entity_id \n" + 
				"left join mf_employee_status d on b.emp_status = d.empstatus_code \n" + 
				"left JOIN mf_position e on e.position_name = b.emp_pos \n" + 
				"left join mf_system_submodules f on a.privileges = f.submodule_desc \n" + 
				"where b.emp_pos = '"+pos_name+"' \n" + 
				"and f.submodule_id = '"+submodule_id+"' \n" + 
				"and a.privileges = '"+submodule_desc+"' \n" + 
				"and e.position_code = '"+pos_code+"' \n" + 
				"and b.div_code = '"+div_code+"'\n" + 
				"and b.dept_code = '"+dept_code+"'\n" + 
				"and not d.empstatus_desc = 'Inactive' and not d.empstatus_desc = 'End of Contract') a	";

		// System.out.println("The number of units to be untag is: \n" + sql + "\n");
		FncSystem.out("The number of units to be untag is:", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {

			if ((Integer) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				x = 0;
			} else {
				x = (Integer) db.getResult()[0][0];
			}
		} else {
			x = 0;
		}

		return x;
	}

	private static Object[][] getListOfEmployeesToBeDelete(String pos_name, Integer submodule_id, String submodule_desc, Integer pos_code, String div_code, String dept_code) {

		String sql = 
				"select distinct on (b.entity_id)\n" + 
				"b.emp_code, \n" + 
				"c.entity_name, \n" +
				"b.emp_pos, \n" + 
				"a.module, \n" + 
				"a.privileges \n" + 
				"from mf_privileges a \n" + 
				"left join em_employee b on a.emp_code = b.emp_code \n" + 
				"left join rf_entity c on b.entity_id = c.entity_id \n" + 
				"left join mf_employee_status d on b.emp_status = d.empstatus_code \n" + 
				"left JOIN mf_position e on e.position_name = b.emp_pos \n" + 
				"left join mf_system_submodules f on a.privileges = f.submodule_desc \n" + 
				"where b.emp_pos = '"+pos_name+"' \n" + 
				"and f.submodule_id = '"+submodule_id+"' \n" + 
				"and a.privileges = '"+submodule_desc+"' \n" + 
				"and e.position_code = '"+pos_code+"' \n" + 
				"and b.div_code = '"+div_code+"' \n" + 
				"and b.dept_code = '"+dept_code+"' \n"+ 
				"and not d.empstatus_desc = 'Inactive' and not d.empstatus_desc = 'End of Contract'";

		// System.out.printf("List of Employees for untagging : " + sql + "\n");
		FncSystem.out("List of Employees for untagging:", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {
			return db.getResult();
		} else {
			return null;
		}
	}

	private void showSavedData(Boolean isSelected) {

		if (tab1.getSelectedIndex() == 0) {
			modelModule.clear();
			if (isSelected) {

				displaySavedModules(modelModule, rowHeaderModule, pos_code, pos_name, div_code, dept_code);

			} else {

				displayModules(modelModule, rowHeaderModule, pos_code, pos_name, div_code, dept_code);

			}
		}

		if (tab1.getSelectedIndex() == 1) {
			modelPosition_a.clear();
			if (isSelected) {

				displaySavedPositions(modelPosition_a, rowHeaderPosition_a, submodule_id, submodule_desc, div_code, dept_code);

			} else {

				displaySelectedPosition(modelPosition_a, rowHeaderPosition_a, submodule_id, submodule_desc, div_code, dept_code);

			}
		}
	}

	private void searchModuleName() {

		if (tab1.getSelectedIndex() == 0) {

			int rw = tblModule.getModel().getRowCount();
			int x = 0;

			while (x < rw) {

				String name = "";

				try {
					name = tblModule.getValueAt(x, 2).toString().toUpperCase();
				} catch (NullPointerException e) {
					name = "";
				}

				String module_name = txtSearchModule.getText().trim().toUpperCase();
				// Boolean match = name.indexOf(acct_name)>0;
				Boolean start = name.contains(module_name);
				// Boolean end = name.endsWith(module_name);

				if (start == true) {
					tblModule.setRowSelectionInterval(x, x);
					tblModule.changeSelection(x, 2, false, false);
					break;
				} else {
					tblModule.setRowSelectionInterval(0, 0);
					tblModule.changeSelection(0, 2, false, false);
				}

				x++;
			}
		}

		if (tab1.getSelectedIndex() == 1) {

			int rw = tblModule_a.getModel().getRowCount();
			int x = 0;

			while (x < rw) {

				String name = "";

				try {
					name = tblModule_a.getValueAt(x, 1).toString().toUpperCase();
				} catch (NullPointerException e) {
					name = "";
				}

				String module_name = txtSearchModule_a.getText().trim().toUpperCase();
				// Boolean match = name.indexOf(acct_name)>0;
				Boolean start = name.contains(module_name);
				// Boolean end = name.endsWith(module_name);

				if (start == true) {
					tblModule_a.setRowSelectionInterval(x, x);
					tblModule_a.changeSelection(x, 1, false, false);
					break;
				} else {
					tblModule_a.setRowSelectionInterval(0, 0);
					tblModule_a.changeSelection(0, 1, false, false);
				}

				x++;
			}
		}
	}

	private void searchPositionName() {

		if (tab1.getSelectedIndex() == 1) {

			int rw = tblPosition_a.getModel().getRowCount();
			int x = 0;

			while (x < rw) {

				String name = "";

				try {
					name = tblPosition_a.getValueAt(x, 2).toString().toUpperCase();
				} catch (NullPointerException e) {
					name = "";
				}

				String pos_name = txtSearchPosition_a.getText().trim().toUpperCase();
				// Boolean match = name.indexOf(acct_name)>0;
				Boolean start = name.contains(pos_name);
				// Boolean end = name.endsWith(module_name);

				if (start == true) {
					tblPosition_a.setRowSelectionInterval(x, x);
					tblPosition_a.changeSelection(x, 2, false, false);
					break;
				} else {
					tblPosition_a.setRowSelectionInterval(0, 0);
					tblPosition_a.changeSelection(0, 2, false, false);
				}

				x++;
			}
		}
	}

	// action Performed (save, edit, cancel, refresh)

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Save Modules")) { Save();}

		if (e.getActionCommand().equals("Edit Modules")) { Edit();}

		if (e.getActionCommand().equals("Cancel Selection")) { Cancel();}

		if (e.getActionCommand().equals("Refresh")) { Refresh();}
		
	}

	private void Save() {
		db = new pgUpdate();
		if (tab1.getSelectedIndex() == 0) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				if (tblPosition.getSelectedRows().length > 0) {
					int rw = tblPosition.getSelectedRow();

					Integer pos_code = (Integer) tblPosition.getValueAt(rw, 0);
					String pos_name = tblPosition.getValueAt(rw, 1).toString();
					String div_code = lookupDivision.getValue();
					String dept_code = lookupDepartment.getValue();

					for (int x = 0; x < modelModule.getRowCount(); x++) {

						Integer submodule_id = (Integer) modelModule.getValueAt(x, 1);
						String submodule_desc = modelModule.getValueAt(x, 2).toString().replace("'", "''");
						String module = modelModule.getValueAt(x, 3).toString();
						String parent = modelModule.getValueAt(x, 4).toString();
						Boolean isSelected = (Boolean) modelModule.getValueAt(x, 0);

						if (isSelected) {

							if (moduleExist(pos_code, pos_name, submodule_id, div_code, dept_code).equals("")
									|| moduleExist(pos_code, pos_name, submodule_id, div_code, dept_code).equals(null)) {

								String sqlDetail = 
										"INSERT into mf_position_modules values(" + 
										"'"+pos_code+"', \n" + 				// 1
										"'"+pos_name+"', \n" + 				// 2
										"'"+submodule_id+"', \n" + 			// 3
										"'"+submodule_desc+"', \n" + 		// 4
										"'A',\n" +							// 5
										"'"+div_code+"', \n" + 				// 6
										"'"+dept_code+"', \n" + 			// 7
										"'"+UserInfo.EmployeeCode+"', \n" + // 8
										"now()" + 							// 9
										")  ";

								System.out.printf("SQL #1: %s\n", sqlDetail);
								db.executeUpdate(sqlDetail, false);
							}

							if (privilegeExist(pos_code, pos_name, submodule_id, submodule_desc, div_code, dept_code).equals("")
									|| privilegeExist(pos_code, pos_name, submodule_id, submodule_desc, div_code,dept_code).equals(null)) {

//								int no_of_units = getNumberOfUnits(pos_name, pos_code, div_code, dept_code);
//								Object[][] units_dtl = getListofEmployees(pos_name, pos_code, div_code, dept_code);
//								int a = 0;

//								for (a = 0; a < no_of_units; a++) {
//									String emp_code = units_dtl[a][0].toString();
//
//									String sqlDetail = 
//											"INSERT into mf_privileges (module, privileges, parent, emp_code, in_charge) values(" + 
//											"'"+module+"', \n" + 
//											"'"+submodule_desc+"', \n" + 
//											"'"+parent+"', \n" + 
//											"'"+emp_code+"', \n" + 
//											"'"+UserInfo.EmployeeCode+"') ";
//
//									System.out.printf("SQL #2: %s\n", sqlDetail);
//									db.executeUpdate(sqlDetail, false);
//								}
							}
						} 
						else {
//							{
//								String sqlDetail = 
//										"UPDATE mf_position_modules SET \n" + 
//										"status_id = 'I'\n" + 
//										"where position_code = '"+pos_code+"' \n" + 
//										"and position_name = '"+pos_name+"' \n" + 
//										"and submodule_id = '"+submodule_id+"' \n"+ 
//										"and div_code = '"+div_code+"' \n" + 
//										"and dept_code = '"+dept_code+"' ";
//
//								System.out.printf("SQL #3: %s \n", sqlDetail);
//								db.executeUpdate(sqlDetail, false);
//							}

							if (privilegeExist(pos_code, pos_name, submodule_id, submodule_desc, div_code, dept_code).equals("")
									|| privilegeExist(pos_code, pos_name, submodule_id, submodule_desc, div_code, dept_code).equals(null)) {

//								System.out.println("PRIVILEGE DOESN'T EXIST!!!!!!!!!!!!!!!!!!! \n");

							} 
							else {

//								int no_of_units_to_be_delete = getNumberOfUnitsToBeDelete(pos_name, submodule_id, submodule_desc, pos_code, div_code, dept_code);
//								Object[][] units_dtl_to_be_delete = getListOfEmployeesToBeDelete(pos_name, submodule_id, submodule_desc, pos_code, div_code, dept_code);
//								int b = 0;

//								for (b = 0; b < no_of_units_to_be_delete; b++) {
//									String emp_code = units_dtl_to_be_delete[b][0].toString();
//
//									String sqlDetail = 
//											"DELETE from mf_privileges\n" + 
//											"WHERE emp_code = '"+emp_code+"'\n" +
//											"AND privileges = '"+submodule_desc+"' ";
//
//									FncSystem.out("The sql Description for untagging is: ", sqlDetail);
//
//									// System.out.printf("SQL #1: %s", sqlDetail);
//									db.executeUpdate(sqlDetail, false);
//								}
							}
						}
					}
				}
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "All data saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
				tblModule.setEnabled(false);
				tblPosition.setEnabled(true);
				// txtSearchPosition.setEditable(true);
				txtSearchPosition.setEnabled(true);
				txtSearchModule.setText("");
				lblSelectedPosition.setText("[ ]");
				lblSelectedPosition.setFont(getFont());
				saveButton.setEnabled(false);
				editButton.setEnabled(false);
				cancelButton.setEnabled(false);

			}
		}

//		if (tab1.getSelectedIndex() == 1) {
//
//			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
//					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
//
//				if (tblModule_a.getSelectedRows().length > 0) {
//					int rw = tblModule_a.getSelectedRow();
//
//					Integer submodule_id = (Integer) modelModule_a.getValueAt(rw, 0);
//					String submodule_desc = modelModule_a.getValueAt(rw, 1).toString();
//					String div_code = lookupDivision.getValue();
//					String dept_code = lookupDepartment.getValue();
//					String module = modelModule_a.getValueAt(rw, 2).toString();
//					String parent = modelModule_a.getValueAt(rw, 3).toString();
//
//					for (int x = 0; x < modelPosition_a.getRowCount(); x++) {
//
//						Integer pos_code = (Integer) modelPosition_a.getValueAt(x, 1);
//						String pos_name = modelPosition_a.getValueAt(x, 2).toString();
//						Boolean isSelected = (Boolean) modelPosition_a.getValueAt(x, 0);
//
//						if (isSelected) {
//
//							if (moduleExist(pos_code, pos_name, submodule_id, div_code, dept_code).equals("")
//									|| moduleExist(pos_code, pos_name, submodule_id, div_code, dept_code).equals(null)) {
//
//								String sqlDetail = 
//										"INSERT into mf_position_modules values(" + 
//										"'"+pos_code+"', \n"+ 				// 1
//										"'"+pos_name+"', \n" + 				// 2
//										"'"+submodule_id+"', \n" + 			// 3
//										"'"+submodule_desc+"', \n" + 		// 4
//										"'A', \n" + 						// 5
//										"'"+div_code+"', \n" + 				// 6
//										"'"+dept_code+"', \n" + 			// 7
//										"'"+UserInfo.EmployeeCode+"', \n" + // 8
//										"now()" + 							// 9
//										") ";
//
//								System.out.printf("SQL #1: %s", sqlDetail);
//								db.executeUpdate(sqlDetail, false);
//							}
//
//							if (privilegeExist(pos_code, pos_name, submodule_id, submodule_desc, div_code, dept_code).equals("")
//									|| privilegeExist(pos_code, pos_name, submodule_id, submodule_desc, div_code, dept_code).equals(null)) {
//
//								int no_of_units = getNumberOfUnits(pos_name, pos_code, div_code, dept_code);
//								Object[][] units_dtl = getListofEmployees(pos_name, pos_code, div_code, dept_code);
//								int a = 0;
//
//								for (a = 0; a < no_of_units; a++) {
//									String emp_code = units_dtl[a][0].toString();
//
//									String sqlDetail = 
//											"INSERT into mf_privileges(module, privileges, parent, emp_code, in_charge) values(" + 
//											"'"+module+"', \n" + 
//											"'"+submodule_desc+"', \n" + 
//											"'"+parent+"', \n" + 
//											"'"+emp_code+"', \n" + 
//											"'"+UserInfo.EmployeeCode +"') ";
//
//									System.out.printf("SQL #2: %s\n", sqlDetail);
//									db.executeUpdate(sqlDetail, false);
//								}
//							}
//						} else {
//							{
//								String sqlDetail = "UPDATE mf_position_modules SET \n" + 
//										"status_id = 'I'\n" + 
//										"where position_code = '"+pos_code+"' \n" + 
//										"and position_name = '"+pos_name+"' \n" + 
//										"and submodule_id = '"+submodule_id+"' \n" + 
//										"and div_code = '"+div_code+"' \n" + "and dept_code = '"+dept_code+"' ";
//
//								System.out.printf("SQL #3: %s \n", sqlDetail);
//								db.executeUpdate(sqlDetail, false);
//							}
//
//							if (privilegeExist(pos_code, pos_name, submodule_id, submodule_desc, div_code, dept_code).equals("")
//									|| privilegeExist(pos_code, pos_name, submodule_id, submodule_desc, div_code, dept_code).equals(null)) {
//
//								System.out.println("PRIVILEGE DOESN'T EXIST!!!!!!!!!!!!!!!!!!! \n");
//
//							} else {
//
//								int no_of_units_to_be_delete = getNumberOfUnitsToBeDelete(pos_name, submodule_id, submodule_desc, pos_code, div_code, dept_code);
//								Object[][] units_dtl_to_be_delete = getListOfEmployeesToBeDelete(pos_name, submodule_id, submodule_desc, pos_code, div_code, dept_code);
//								int b = 0;
//
//								for (b = 0; b < no_of_units_to_be_delete; b++) {
//									String emp_code = units_dtl_to_be_delete[b][0].toString();
//
//									String sqlDetail = 
//											"DELETE from mf_privileges\n" + 
//											"WHERE emp_code = '"+emp_code+"'\n" + 
//											"AND privileges = '"+submodule_desc+"' ";
//
//									FncSystem.out("The sql Description for untagging is: ", sqlDetail);
//
//									// System.out.printf("SQL #1: %s", sqlDetail);
//									db.executeUpdate(sqlDetail, false);
//								}
//							}
//						}
//					}
//				}
//				db.commit();
//				JOptionPane.showMessageDialog(getContentPane(), "All data saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
//				tblModule_a.setEnabled(true);
//				tblPosition_a.setEnabled(false);
//				txtSearchPosition_a.setEditable(true);
//				// txtSearchModule_a.setEditable(true);
//				txtSearchModule_a.setEnabled(true);
//				lblSelectedModule.setText("[ ]");
//				lblSelectedModule.setFont(getFont());
//				cboxSavedModule.setSelected(false);
//				saveButton.setEnabled(false);
//				editButton.setEnabled(false);
//				cancelButton_a.setEnabled(false);
//
//			}
//		}
	}

	private void Edit() {

		if (tab1.getSelectedIndex() == 0) {

			tblModule.setEnabled(true);
			tblPosition.setEnabled(false);
			// txtSearchPosition.setEditable(false);
			txtSearchPosition.setEnabled(false);
			editButton.setEnabled(false);
			saveButton.setEnabled(true);
			cancelButton.setEnabled(true);

			Font font = new Font("Times New Roman", Font.BOLD, 16);
			lblSelectedPosition.setFont(font);
			lblSelectedPosition.setText(String.format("[ %s ]", pos_name));

		}

		if (tab1.getSelectedIndex() == 1) {

			tblModule_a.setEnabled(false);
			tblPosition_a.setEnabled(true);
			// txtSearchModule_a.setEditable(false);
			txtSearchModule_a.setEnabled(false);
			editButton.setEnabled(false);
			saveButton.setEnabled(true);
			cancelButton_a.setEnabled(true);

			Font font = new Font("Times New Roman", Font.BOLD, 16);
			lblSelectedModule.setFont(font);
			lblSelectedModule.setText(String.format("[ %s ]", submodule_desc));

		}
	}

	private void Cancel() {

		if (tab1.getSelectedIndex() == 0) {

			tblPosition.setEnabled(true);
			tblModule.setEnabled(false);
			// txtSearchPosition.setEditable(true);
			txtSearchPosition.setEnabled(true);
			cancelButton.setEnabled(false);
			saveButton.setEnabled(false);
			lblSelectedPosition.setText("[ ]");
			lblSelectedPosition.setFont(getFont());
		}

		if (tab1.getSelectedIndex() == 1) {

			tblPosition_a.setEnabled(false);
			tblModule_a.setEnabled(true);
			txtSearchPosition_a.setEditable(false);
			// txtSearchModule_a.setEditable(true);
			txtSearchModule_a.setEnabled(true);
			cancelButton_a.setEnabled(false);
			saveButton.setEnabled(false);
			lblSelectedModule.setText("[ ]");
			lblSelectedModule.setFont(getFont());
		}

	}

	private void Refresh() {

		lookupDivision.setText("");
		lookupDivision.setEnabled(true);
		lookupDepartment.setText("");
		lookupDepartment.setEnabled(false);
		lblDiv.setText("[ ]");
		lblDept.setText("[ ]");
		lblSelectedPosition.setText("[ ]");
		lblSelectedPosition.setFont(getFont());
		lblSelectedModule.setText("[ ]");
		lblSelectedModule.setFont(getFont());
		txtSearchPosition.setText("");
		txtSearchPosition.setEditable(false);
		txtSearchModule.setText("");
		txtSearchModule.setEditable(false);
		modelPosition.clear();
		modelModule.clear();
		tblPosition.setEnabled(false);
		tblModule.setEnabled(false);
		txtSearchPosition_a.setText("");
		txtSearchPosition_a.setEditable(false);
		txtSearchModule_a.setText("");
		txtSearchModule_a.setEditable(false);
		modelPosition_a.clear();
		modelModule_a.clear();
		tblPosition_a.setEnabled(false);
		tblModule_a.setEnabled(false);
		cboxSavedModule.setEnabled(false);
		cboxSavedModule.setSelected(false);
		cboxSavedPosition.setEnabled(false);
		cboxSavedPosition.setSelected(false);
		editButton.setEnabled(false);
		saveButton.setEnabled(false);
		cancelButton.setEnabled(false);
		cancelButton_a.setEnabled(false);
		tab1.setEnabled(true);
	}

	// Validation if module name exists

	private static String moduleExist(Integer pos_code, String pos_name, Integer submodule_id, String div_code, String dept_code) {

		String moduleExist = "";
		String SQL = 
				"select position_name from mf_position_modules\n" + 
				"where position_code = '"+pos_code+"' \n" + 
				"and position_name = '"+pos_name+"' \n" + 
				"and submodule_id = '"+submodule_id+"' \n" + 
				"and div_code = '"+div_code+"' \n" + 
				"and dept_code = '"+dept_code+"' \n" + 
				"and status_id = 'A' ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				moduleExist = (String) db.getResult()[0][0].toString();
			}
		} else {
			moduleExist = "";
		}

		return moduleExist;
	}

	private static String privilegeExist(Integer pos_code, String pos_name, Integer submodule_id, String submodule_desc, String div_code, String dept_code) {

		String privilegeExist = "";
		String SQL = 
				"select distinct on (b.entity_id)\n" + 
				"b.emp_code,\n" + 
				"c.entity_name,\n" + 
				"b.emp_pos,\n" + 
				"a.module,\n" + 
				"a.privileges\n" + 
				"from mf_privileges a\n" +
				"left join em_employee b on a.emp_code = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"left join mf_employee_status d on b.emp_status = d.empstatus_code\n" + 
				"left JOIN mf_position e on e.position_name = b.emp_pos\n" + 
				"left join mf_system_submodules f on a.privileges = f.submodule_desc\n" + 
				"where b.emp_pos = '"+pos_name+"' \n" + 
				"and f.submodule_id = '"+submodule_id+"' \n" + 
				"and a.privileges = '"+submodule_desc+"' \n" + 
				"and e.position_code = '"+pos_code+"' \n" + 
				"and b.div_code = '"+div_code+"' \n" + 
				"and b.dept_code = '"+dept_code+"' \n"+ 
				"and not d.empstatus_desc = 'Inactive' and not d.empstatus_desc = 'End of Contract' ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				privilegeExist = (String) db.getResult()[0][0].toString();
			}
		} else {
			privilegeExist = "";
		}

		return privilegeExist;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
