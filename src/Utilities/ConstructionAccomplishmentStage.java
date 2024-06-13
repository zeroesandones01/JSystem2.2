package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelUtilities_Days;
import tablemodel.modelUtilities_Stage;
import tablemodel.modelUtilities_SubStage;

public class ConstructionAccomplishmentStage extends _JInternalFrame implements _GUI, ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = -3061284418918863916L;
	protected static final Home_JSystem Home_JSystem = null;

	static String title = "Construction Accomplishment Stage";
	static Dimension SIZE = new Dimension(1000, 600);
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel panHouse;
	private JPanel pnlCenterComponents;
	private _JLookup lookupHouseMod;
	private JLabel lblHouse;
	private JPanel pnlCenter;
	private JPanel pnlNorthtable;
	private JScrollPane scrollStage;
	private modelUtilities_Stage modelStage;
	private _JTableMain tblStage;
	private JPanel pnlCentertable;
	private JPanel pnlSouthtable;
	private modelUtilities_SubStage modelSubStage;
	private _JTableMain tblSubStage;
	private JScrollPane scrollSubStage;
	private JScrollPane scrollDays;
	private modelUtilities_Days modelDays;
	private _JTableMain tblDays;
	private JPanel pnlSouthwest;
	private JList rowHeaderStage;
	private JList rowHeaderSubStage;
	private JList rowHeaderDays;
	private JPopupMenu menu;
	private JMenuItem mniAdd;
	private JMenuItem mniEdit;
	private JPanel pnlAddNew;
	private JPanel pnlWestLabel;
	private JLabel lblStage;
	private JLabel lblStageDesc;
	private JLabel lblPercentAcc;
	private JLabel lblStatus;
	private JPanel pnlStage;
	private JComboBox cmbStage;
	private JPanel pnlStageDesc;
	private JTextField txtStageDesc;
	private JPanel pnlPercent;
	private _JXFormattedTextField txtPercent;
	private JPanel pnlStatus;
	private JRadioButton option1;
	private JRadioButton option2;
	private JPanel pnlSouthButton;
	private JButton saveButton;
	private String to_do; //for updating
	private JPanel pnlsubAddNew;
	private JPanel pnlsubWestLabel;
	private JLabel lblSubStage;
	private JLabel lblSubStageDesc;
	private JLabel lblSubPercentAcc;
	private JLabel lblSubStatus;
	private JPanel pnlSubCenterComponents;
	private JPanel pnlSubStage;
	private JComboBox cmbSubStage;
	private JPanel pnlSubStageDesc;
	private JTextField txtSubStageDesc;
	private JPanel pnlSubPercent;
	private JPanel pnlSubStatus;
	private JRadioButton suboption1;
	private JRadioButton suboption2;
	private JPanel pnlSubSouthButton;
	private JButton saveButton1;
	private JPopupMenu menu2;
	private JMenuItem mniEdit_a;
	private _JXFormattedTextField txtSubPercent;
	private JPanel pnldayAddNew;
	private JPanel pnldayWestLabel;
	private JLabel lblDay;
	private JLabel lblDayPercentAcc;
	private JLabel lblDayStatus;
	private JPanel pnlDayCenterComponents;
	private JPanel pnlDay;
	private JTextField txtDayNo;
	private JPanel pnlDayPercent;
	private _JXFormattedTextField txtDayPercent;
	private JPanel pnlDayStatus;
	private JRadioButton dayoption1;
	private JRadioButton dayoption2;
	private JPanel pnlDaySouthButton;
	private JButton saveButton2;
	private JPopupMenu menu3;
	private JMenuItem mniEdit_b;
	private JMenuItem mniAddSubstage;
	private JMenuItem mniAddDays;
	private JPanel pnlPreview;
	private JButton previewButton;


	public ConstructionAccomplishmentStage() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ConstructionAccomplishmentStage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public ConstructionAccomplishmentStage(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	public void initGUI() {
		this.setLayout(new BorderLayout (5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(1000,500));
		this.setBounds(0,0,800,600);
		{
			pnlMain = new JPanel (new BorderLayout (5,5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				menu = new JPopupMenu("Popup");
				mniAdd = new JMenuItem ("Add New Stage     ");
				mniEdit = new JMenuItem("Edit Stage     ");
				mniAddSubstage = new JMenuItem("Add New Substage     ");
				JSeparator sp = new JSeparator();
				menu.add(mniAdd);
				menu.add(mniEdit);
				menu.add(sp);
				menu.add(mniAddSubstage);
				mniAdd.setEnabled(false);
				mniEdit.setEnabled(false);
				mniAddSubstage.setEnabled(false);

				mniAdd.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						AddRow();

					}

				});

				mniEdit.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						Edit();

					}

				});

				mniAddSubstage.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						AddRow_a();

					}

				});
			}
			{
				menu2 = new JPopupMenu("Popup");
				mniEdit_a = new JMenuItem("Edit Substage     ");
				mniAddDays = new JMenuItem ("Add New Day     ");
				JSeparator sp = new JSeparator();
				//menu2.add(mniAdd_a);
				menu2.add(mniEdit_a);
				menu2.add(sp);
				menu2.add(mniAddDays);
				mniEdit_a.setEnabled(false);
				mniAddDays.setEnabled(false);

				mniEdit_a.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {

						Edit_a();

					}

				});

				mniAddDays.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {

						AddRow_b();

					}

				});
			}
			{
				menu3 = new JPopupMenu("Popup");
				mniEdit_b = new JMenuItem("Edit Day     ");
				//menu3.add(mniAdd_b);
				menu3.add(mniEdit_b);
				mniEdit_b.setEnabled(false);

				mniEdit_b.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						Edit_b();

					}

				});
			}
			{
				pnlAddNew = new JPanel (new BorderLayout(5,5));
				pnlAddNew.setPreferredSize(new Dimension(400,200));
				pnlAddNew.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
				{
					pnlWestLabel = new JPanel (new GridLayout(4,1,3,3));
					pnlAddNew.add(pnlWestLabel, BorderLayout.WEST);
					{
						lblStage = new JLabel ("Stage No.:");
						pnlWestLabel.add(lblStage);
						lblStage.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						lblStageDesc = new JLabel ("Stage Description:");
						pnlWestLabel.add(lblStageDesc);
						lblStageDesc.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						lblPercentAcc = new JLabel ("Percent Accomp.:");
						pnlWestLabel.add(lblPercentAcc);
						lblPercentAcc.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						lblStatus = new JLabel ("Status:");
						pnlWestLabel.add(lblStatus);
						lblStatus.setHorizontalAlignment(JLabel.RIGHT);
					}
				}
				{
					pnlCenterComponents = new JPanel (new GridLayout (4,1,3,3));
					pnlAddNew.add(pnlCenterComponents, BorderLayout.CENTER);
					{
						pnlStage = new JPanel (new BorderLayout(5,5));
						pnlCenterComponents.add(pnlStage, BorderLayout.CENTER);
						{
							cmbStage = new JComboBox (new String[]{"",
									"1","2","3","4","5",
									"6","7","8","9","10",
									"11","12", "13", "14", "15"
							});

							pnlStage.add(cmbStage, BorderLayout.WEST);
							cmbStage.setPreferredSize(new Dimension(100,50));
						}
					}
					{
						pnlStageDesc = new JPanel (new BorderLayout(5,5));
						pnlCenterComponents.add(pnlStageDesc);
						{
							txtStageDesc = new JTextField ("");
							pnlStageDesc.add(txtStageDesc);
						}
					}
					{
						pnlPercent = new JPanel (new BorderLayout(5,5));
						pnlCenterComponents.add(pnlPercent, BorderLayout.CENTER);
						{
							txtPercent = new _JXFormattedTextField(JXFormattedTextField.CENTER);
							pnlPercent.add(txtPercent, BorderLayout.WEST);
							txtPercent.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							//txtPercent.setText("0");
							txtPercent.setPreferredSize(new Dimension (100,0));
						}
					}
					{
						pnlStatus = new JPanel (new BorderLayout(5,5));
						pnlCenterComponents.add(pnlStatus);
						{
							ButtonGroup group = new ButtonGroup();
							{
								option1 = new JRadioButton ("Active");
								pnlStatus.add(option1, BorderLayout.WEST);
								option1.setSelected(true);
								group.add(option1);
							}
							{
								option2 = new JRadioButton ("Inactive");
								pnlStatus.add(option2, BorderLayout.CENTER);
								option2.setSelected(false);
								group.add(option2);
							}
						}
					}
					{
						pnlSouthButton = new JPanel (new BorderLayout(5,5));
						pnlAddNew.add(pnlSouthButton, BorderLayout.SOUTH);
						pnlSouthButton.setPreferredSize(new Dimension (100,50));
						{
							saveButton = new JButton ("Save");
							pnlSouthButton.add(saveButton);
							saveButton.setActionCommand("Save Accomplishment Stage");
							saveButton.addActionListener(this);
						}
					}
				}
			}
			{
				pnlsubAddNew = new JPanel (new BorderLayout(5,5));
				pnlsubAddNew.setPreferredSize(new Dimension(520,200));
				pnlsubAddNew.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
				{
					pnlsubWestLabel = new JPanel (new GridLayout(4,1,3,3));
					pnlsubAddNew.add(pnlsubWestLabel, BorderLayout.WEST);
					{
						lblSubStage = new JLabel ("Substage No.:");
						pnlsubWestLabel.add(lblSubStage);
						lblSubStage.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						lblSubStageDesc = new JLabel ("Substage Description:");
						pnlsubWestLabel.add(lblSubStageDesc);
						lblSubStageDesc.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						lblSubPercentAcc = new JLabel ("Percent Accomp.:");
						pnlsubWestLabel.add(lblSubPercentAcc);
						lblSubPercentAcc.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						lblSubStatus = new JLabel ("Status:");
						pnlsubWestLabel.add(lblSubStatus);
						lblSubStatus.setHorizontalAlignment(JLabel.RIGHT);
					}
				}
				{
					pnlSubCenterComponents = new JPanel (new GridLayout (4,1,3,3));
					pnlsubAddNew.add(pnlSubCenterComponents, BorderLayout.CENTER);
					{
						pnlSubStage = new JPanel (new BorderLayout(5,5));
						pnlSubCenterComponents.add(pnlSubStage, BorderLayout.CENTER);
						{
							cmbSubStage = new JComboBox (new String[]{"",
									"1","2","3","4","5",
									"6","7","8","9","10",
									"11","12", "13", "14", "15", 
									"16", "17", "18", "19", "20"
							});

							pnlSubStage.add(cmbSubStage, BorderLayout.WEST);
							cmbSubStage.setPreferredSize(new Dimension(100,50));
						}
					}
					{
						pnlSubStageDesc = new JPanel (new BorderLayout(5,5));
						pnlSubCenterComponents.add(pnlSubStageDesc);
						{
							txtSubStageDesc = new JTextField ("");
							pnlSubStageDesc.add(txtSubStageDesc);
						}
					}
					{
						pnlSubPercent = new JPanel (new BorderLayout(5,5));
						pnlSubCenterComponents.add(pnlSubPercent, BorderLayout.CENTER);
						{
							txtSubPercent = new _JXFormattedTextField(JXFormattedTextField.CENTER);
							pnlSubPercent.add(txtSubPercent, BorderLayout.WEST);
							txtSubPercent.setPreferredSize(new Dimension (100,0));
							txtSubPercent.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtSubPercent.setText("0");
						}
					}
					{
						pnlSubStatus = new JPanel (new BorderLayout(5,5));
						pnlSubCenterComponents.add(pnlSubStatus);
						{
							ButtonGroup group = new ButtonGroup();
							{
								suboption1 = new JRadioButton ("Active");
								pnlSubStatus.add(suboption1, BorderLayout.WEST);
								suboption1.setSelected(true);
								group.add(suboption1);
							}
							{
								suboption2 = new JRadioButton ("Inactive");
								pnlSubStatus.add(suboption2, BorderLayout.CENTER);
								suboption2.setSelected(false);
								group.add(suboption2);
							}
						}
					}
					{
						pnlSubSouthButton = new JPanel (new BorderLayout(5,5));
						pnlsubAddNew.add(pnlSubSouthButton, BorderLayout.SOUTH);
						pnlSubSouthButton.setPreferredSize(new Dimension (100,50));
						{
							saveButton1 = new JButton ("Save");
							pnlSubSouthButton.add(saveButton1);
							saveButton1.setActionCommand("Save Accomplishment SubStage");
							saveButton1.addActionListener(this);
						}
					}
				}
			}
			{
				pnldayAddNew = new JPanel (new BorderLayout(5,5));
				pnldayAddNew.setPreferredSize(new Dimension(400,200));
				pnldayAddNew.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
				{
					pnldayWestLabel = new JPanel (new GridLayout(3,1,3,3));
					pnldayAddNew.add(pnldayWestLabel, BorderLayout.WEST);
					{
						lblDay = new JLabel ("Day No.:");
						pnldayWestLabel.add(lblDay);
						lblDay.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						lblDayPercentAcc = new JLabel ("Percent Accomp.:");
						pnldayWestLabel.add(lblDayPercentAcc);
						lblDayPercentAcc.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						lblDayStatus = new JLabel ("Status:");
						pnldayWestLabel.add(lblDayStatus);
						lblDayStatus.setHorizontalAlignment(JLabel.RIGHT);
					}
				}
				{
					pnlDayCenterComponents = new JPanel (new GridLayout (3,1,3,3));
					pnldayAddNew.add(pnlDayCenterComponents, BorderLayout.CENTER);
					{
						pnlDay = new JPanel (new BorderLayout(5,5));
						pnlDayCenterComponents.add(pnlDay, BorderLayout.CENTER);
						{
							txtDayNo = new JTextField ("");
							pnlDay.add(txtDayNo, BorderLayout.WEST);
							txtDayNo.setHorizontalAlignment(JXTextField.CENTER);
							txtDayNo.setPreferredSize(new Dimension (70,0));
						}
					}
					{
						pnlDayPercent = new JPanel (new BorderLayout(5,5));
						pnlDayCenterComponents.add(pnlDayPercent, BorderLayout.CENTER);
						{
							txtDayPercent = new _JXFormattedTextField(JXFormattedTextField.CENTER);
							pnlDayPercent.add(txtDayPercent, BorderLayout.WEST);
							txtDayPercent.setPreferredSize(new Dimension (70,0));
							txtDayPercent.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtDayPercent.setText("0");
						}
					}
					{
						pnlDayStatus = new JPanel (new BorderLayout(5,5));
						pnlDayCenterComponents.add(pnlDayStatus);
						{
							ButtonGroup group = new ButtonGroup();
							{
								dayoption1 = new JRadioButton ("Active");
								pnlDayStatus.add(dayoption1, BorderLayout.WEST);
								dayoption1.setSelected(true);
								group.add(dayoption1);
							}
							{
								dayoption2 = new JRadioButton ("Inactive");
								pnlDayStatus.add(dayoption2, BorderLayout.CENTER);
								dayoption2.setSelected(false);
								group.add(dayoption2);
							}
						}
					}
					{
						pnlDaySouthButton = new JPanel (new BorderLayout(5,5));
						pnldayAddNew.add(pnlDaySouthButton, BorderLayout.SOUTH);
						pnlDaySouthButton.setPreferredSize(new Dimension (100,50));
						{
							saveButton2 = new JButton ("Save");
							pnlDaySouthButton.add(saveButton2);
							saveButton2.setActionCommand("Save Accomplishment Days");
							saveButton2.addActionListener(this);
						}
					}
				}
			}
			{
				pnlNorth = new JPanel (new BorderLayout (5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension (0,50));
				pnlNorth.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				{
					JLabel lblHouse = new JLabel ("House Model:");
					pnlNorth.add(lblHouse, BorderLayout.WEST);
					lblHouse.setFont(new Font("Courier New", Font.BOLD, 12));
				}
				{
					pnlCenterComponents = new JPanel (new BorderLayout (5,5));
					pnlNorth.add(pnlCenterComponents , BorderLayout.CENTER);
					{
						panHouse = new JPanel (new BorderLayout (5,5));
						pnlCenterComponents.add(panHouse, BorderLayout.WEST);
						{
							lookupHouseMod = new _JLookup (null, "House Model");
							panHouse.add(lookupHouseMod, BorderLayout.WEST);
							lookupHouseMod.setLookupSQL(HouseModel());
							lookupHouseMod.setReturnColumn(0);
							lookupHouseMod.setPreferredSize(new Dimension (100,0));
							lookupHouseMod.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event){
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null){
										FncSystem.out("Display sql for description", lookupHouseMod.getLookupSQL());

										String model_id = (String) data [0];

										lookupHouseMod.setValue(model_id);

										displayAccomplishmentStage(modelStage, rowHeaderStage, model_id);

										lblHouse.setText(String.format("[ %s ]", (String) data[1]));

										modelSubStage.clear();

										modelDays.clear();

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							{
								lblHouse = new JLabel ("[ ]");
								pnlCenterComponents.add(lblHouse, BorderLayout.CENTER);
							}
						}
					}
				}
			}
			{
				pnlCenter = new JPanel (new GridLayout(3,1,5,5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
				{
					pnlNorthtable = new JPanel (new BorderLayout(5,5));
					pnlCenter.add(pnlNorthtable, BorderLayout.NORTH);
					pnlNorthtable.setBorder(components.JTBorderFactory.createTitleBorder("Accomplishment Stage"));
					{
						scrollStage = new JScrollPane ();
						pnlNorthtable.add(scrollStage);
						scrollStage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					}
					{
						modelStage = new modelUtilities_Stage();
						tblStage = new _JTableMain(modelStage);

						scrollStage.setViewportView(tblStage);
						tblStage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblStage.getColumn(0).setPreferredWidth(80);
						tblStage.getColumn(1).setPreferredWidth(150);
						tblStage.getColumn(2).setPreferredWidth(130);
						tblStage.getColumn(3).setPreferredWidth(70);
						tblStage.getColumn(4).setPreferredWidth(150);
						tblStage.getColumn(5).setPreferredWidth(100);
						tblStage.getTableHeader().setEnabled(false);
						tblStage.addMouseListener(new PopupTriggerListener_panel());
						tblStage.hideColumns("Set No.");
						tblStage.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

							@Override
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){
									displayAccomplishmentSubStageTab();

									modelDays.clear();

								}

								if (tblStage.getSelectedRows().length>0){
									mniAdd.setEnabled(true);
									mniEdit.setEnabled(true);
									mniAddSubstage.setEnabled(true);
									previewButton.setEnabled(true);
								}
								else{
									mniAdd.setEnabled(false);
									mniEdit.setEnabled(false);
									mniAddSubstage.setEnabled(false);
									previewButton.setEnabled(false);
								}
							}
						});
					}
				}
				{
					rowHeaderStage = tblStage.getRowHeader();
					rowHeaderStage .setModel(new DefaultListModel());
					scrollStage.setRowHeaderView(rowHeaderStage);
					scrollStage.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
				{
					pnlCentertable = new JPanel (new BorderLayout (5,5));
					pnlCenter.add(pnlCentertable, BorderLayout.CENTER);
					pnlCentertable.setBorder(components.JTBorderFactory.createTitleBorder("Accomplishment Sub-Stage (Stage No.1 - Wall Footing)"));
					{
						scrollSubStage = new JScrollPane ();
						pnlCentertable.add(scrollSubStage);
						scrollSubStage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					}
					{
						modelSubStage = new modelUtilities_SubStage();
						tblSubStage = new _JTableMain(modelSubStage);

						scrollSubStage.setViewportView(tblSubStage);
						tblSubStage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblSubStage.getColumn(0).setPreferredWidth(80);
						tblSubStage.getColumn(1).setPreferredWidth(150);
						tblSubStage.getColumn(2).setPreferredWidth(130);
						tblSubStage.getColumn(3).setPreferredWidth(70);
						tblSubStage.getColumn(4).setPreferredWidth(150);
						tblSubStage.getColumn(5).setPreferredWidth(100);
						tblSubStage.getTableHeader().setEnabled(false);
						tblSubStage.addMouseListener(new PopupTriggerListener_panel2());
						tblSubStage.hideColumns("Set No.");
						tblSubStage.hideColumns("Stage No.");
						tblSubStage.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

							@Override
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){
									displayAccomplishmentDaysTab();
								}

								if (tblSubStage.getSelectedRows().length>0){
									mniEdit_a.setEnabled(true);
									mniAddDays.setEnabled(true);
								}
								else{
									mniEdit_a.setEnabled(false);
									mniAddDays.setEnabled(false);
								}
							}
						});
					}
					{
						rowHeaderSubStage = tblSubStage.getRowHeader();
						rowHeaderSubStage.setModel(new DefaultListModel());
						scrollSubStage.setRowHeaderView(rowHeaderSubStage);
						scrollSubStage.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					pnlSouthtable = new JPanel (new BorderLayout (5,5));
					pnlCenter.add(pnlSouthtable, BorderLayout.SOUTH);
					{
						pnlSouthwest = new JPanel (new BorderLayout(5,5));
						pnlSouthtable.add(pnlSouthwest, BorderLayout.WEST);
						pnlSouthwest.setBorder(components.JTBorderFactory.createTitleBorder("Days [Sub-Stage No.2 - Excavation (Depth & Width)]"));
						{
							scrollDays = new JScrollPane ();
							pnlSouthwest.add(scrollDays);
							scrollDays.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						}
						{
							modelDays = new modelUtilities_Days();
							tblDays = new _JTableMain (modelDays);

							scrollDays.setViewportView(tblDays);
							tblDays.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblDays.getColumn(0).setPreferredWidth(80);
							tblDays.getColumn(1).setPreferredWidth(150);
							tblDays.getColumn(2).setPreferredWidth(70);
							tblDays.getColumn(3).setPreferredWidth(170);
							tblDays.getColumn(4).setPreferredWidth(100);
							tblDays.getTableHeader().setEnabled(false);
							tblDays.addMouseListener(new PopupTriggerListener_panel3());
							tblDays.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

								@Override
								public void valueChanged(ListSelectionEvent e) {
									if(!e.getValueIsAdjusting()){

										if (tblDays.getSelectedRows().length>0){
											mniEdit_b.setEnabled(true);
										}
										else{
											mniEdit_b.setEnabled(false);
										}
									}
								}
							});

						}
						{
							rowHeaderDays= tblDays.getRowHeader();
							rowHeaderDays.setModel(new DefaultListModel());
							scrollDays.setRowHeaderView(rowHeaderDays);
							scrollDays.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							TitledBorder centerBorder = BorderFactory.createTitledBorder("Click Here!");
							centerBorder.setTitleJustification(TitledBorder.CENTER);
							pnlPreview = new JPanel (new BorderLayout (5,5));
							pnlSouthtable.add(pnlPreview, BorderLayout.CENTER);
							pnlPreview.setBorder(centerBorder);
							{
								previewButton = new JButton ("Preview");
								pnlPreview.add(previewButton, BorderLayout.CENTER);
								previewButton.setActionCommand("Preview");
								previewButton.addActionListener(this);
								previewButton.setEnabled(false);
							}
						}
					}
				}
			}
		}
	}


	//SQL

	private String HouseModel() {
		String sql =  "select model_id as \"Model ID\", model_desc as \"Model\", status_id as \"Status\" \n" + 
				"from mf_product_model\n" + 
				"where status_id = 'A' and model_id not in ('001', '002', '003', '004', '005', '006', '017') order by model_id ASC\n";
		return sql;
	}

	private void insertAccomplishmentStageDetails(pgUpdate db){

		Integer set_no = null;
		String model_id = "";
		Integer stage_no = null;
		String stage_desc	= "";
		Double percent_acc 	= null;	
		String status = "";
		int rw = tblStage.getSelectedRow();

		set_no = (Integer) modelStage.getValueAt(rw,6);
		model_id = lookupHouseMod.getText();
		stage_no = Integer.parseInt(cmbStage.getSelectedItem().toString());
		stage_desc = txtStageDesc.getText();
		percent_acc = Double.parseDouble(txtPercent.getText());
		if(option1.isSelected()==true){status = "A";}
		else {status = "I";}

		String sqlDetail =
				"INSERT into mf_accomp_stage_main values(" + 
						"'"+set_no+"',  \n" +  				 //1
						"'"+model_id+"',  \n" +  			 //2		
						"'"+stage_no+"',  \n" +  			 //3
						"'"+stage_desc+"',  \n" +			 //4
						"'"+percent_acc+"',  \n" +			 //5
						"'"+status+"',   \n" +   			 //6
						"'"+UserInfo.EmployeeCode+"', \n"  + //7
						"now()"  +	 						 //8
						")  " ;
		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	private void updateAccomplishmentStageDetails(pgUpdate db){

		Integer set_no = null;
		String model_id = "";
		Integer stage_no = null;
		String stage_desc	= "";
		String stagename = "";
		Double stage_perc 	= null;	
		String status = "";
		int rw = tblStage.getSelectedRow();

		set_no = (Integer) modelStage.getValueAt(rw,6);
		model_id = lookupHouseMod.getText();
		stage_no = Integer.parseInt(cmbStage.getSelectedItem().toString());
		stage_desc = txtStageDesc.getText();
		stagename = tblStage.getValueAt(rw,1).toString().trim();
		stage_perc = Double.parseDouble(txtPercent.getText());
		if(option1.isSelected()==true){status = "A";}
		else {status = "I";}

		String sqlDetail =
				"UPDATE mf_accomp_stage_main set " + 		
						"stage_no = '"+stage_no+"',  \n" +  			 //1
						"stage_desc = '"+stage_desc+"',  \n" +			 //2
						"stage_perc = '"+stage_perc+"',  \n" +			 //3
						"status_id = '"+status+"',   \n" +   			 //4
						"edited_by = '"+UserInfo.EmployeeCode+"', \n"  + //5
						"edited_date = now() \n"  +	 					 //6
						"where set_no = '"+set_no+"' and " +
						"model_id = '"+model_id+"' and " +
						"stage_desc = '"+stagename+"' ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	private void updateAccomplishmentSubStageDetails(pgUpdate db){

		Integer set_no = null;
		Integer stage_no = null;
		Integer substage_no = null;
		String substage_desc	= "";
		String substagename = "";
		Double substage_perc = null;	
		String status = "";
		int rw = tblSubStage.getSelectedRow();

		set_no = (Integer) modelSubStage.getValueAt(rw,6);
		stage_no = (Integer) modelSubStage.getValueAt(rw,7);
		substage_no = Integer.parseInt(cmbSubStage.getSelectedItem().toString());
		substage_desc = txtSubStageDesc.getText();
		substagename = tblSubStage.getValueAt(rw,1).toString().trim();
		substage_perc = Double.parseDouble(txtSubPercent.getText());
		if(suboption1.isSelected()==true){status = "A";}
		else {status = "I";}

		String sqlDetail =
				"UPDATE mf_accomp_stage_dtl set " + 		
						"stage_no = '"+stage_no+"',  \n" +  			 //1
						"substage_no = '"+substage_no+"',  \n" +  		 //2
						"substage_desc = '"+substage_desc+"',  \n" +	 //3
						"substage_perc = '"+substage_perc+"',  \n" +	 //4
						"status_id = '"+status+"',   \n" +   			 //5
						"edited_by = '"+UserInfo.EmployeeCode+"', \n"  + //6
						"edited_date = now() \n"  +	 					 //7
						"where set_no = '"+set_no+"' and " +
						"stage_no = '"+stage_no+"' and " +
						"substage_desc = '"+substagename+"' ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	private void insertAccomplishmentSubStageDetails(pgUpdate db){

		Integer set_no = null;
		Integer stage_no = null;
		Integer substage_no = null;
		String substage_desc = "";
		Double substage_perc = null;	
		String status = "";
		int rw = tblStage.getSelectedRow();

		set_no = (Integer) modelStage.getValueAt(rw,6);
		stage_no = (Integer) tblStage.getValueAt(rw,0);
		substage_no = Integer.parseInt(cmbSubStage.getSelectedItem().toString());
		substage_desc = txtSubStageDesc.getText();
		substage_perc = Double.parseDouble(txtSubPercent.getText());
		if(suboption1.isSelected()==true){status = "A";}
		else {status = "I";}

		String sqlDetail =
				"INSERT into mf_accomp_stage_dtl values(" + 
						"'"+set_no+"',  \n" +  				 //1		
						"'"+stage_no+"',  \n" +  			 //2
						"'"+substage_no+"',  \n" +  		 //3
						"'"+substage_desc+"',  \n" +	     //4
						"'"+substage_perc+"',  \n" +		 //5
						"'1', \n" +                          //6
						"'0', \n" +                          //7
						"'"+status+"',   \n" +   			 //8
						"'"+UserInfo.EmployeeCode+"', \n"  + //9
						"now()"  +	 						 //10
						")  " ;
		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	private void insertAccomplishmentDayDetails(pgUpdate db){

		Integer set_no = null;
		Integer stage_no = null;
		Integer substage_no = null;
		String substage_desc = "";
		Double substage_perc = null;
		Integer day_no = null;
		Double day_perc = null;
		String status = "";
		int rw = tblSubStage.getSelectedRow();

		set_no = (Integer) modelSubStage.getValueAt(rw,6);
		stage_no = (Integer) modelSubStage.getValueAt(rw,7);
		substage_no = (Integer) tblSubStage.getValueAt(rw,0);
		substage_desc = tblSubStage.getValueAt(rw,1).toString();
		substage_perc = Double.parseDouble(tblSubStage.getValueAt(rw,2).toString());
		day_no = Integer.parseInt(txtDayNo.getText().toString());
		day_perc = Double.parseDouble(txtDayPercent.getText().toString());
		if(dayoption1.isSelected()==true){status = "A";}
		else {status = "I";}

		String sqlDetail =
				"INSERT into mf_accomp_stage_dtl values(" + 
						"'"+set_no+"',  \n" +  				 //1		
						"'"+stage_no+"',  \n" +  			 //2
						"'"+substage_no+"',  \n" +  		 //3
						"'"+substage_desc+"',  \n" +	     //4
						"'"+substage_perc+"',  \n" +		 //5
						"'"+day_no+"', \n" +                 //6
						"'"+day_perc+"', \n" +               //7
						"'"+status+"',   \n" +   			 //8
						"'"+UserInfo.EmployeeCode+"', \n"  + //9
						"now()"  +	 						 //10
						")  " ;
		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	private void updateAccomplishmentDayDetails(pgUpdate db){

		Integer set_no = null;
		Integer stage_no = null;
		Integer substage_no = null;
		String substage_desc = "";
		Integer day_no = null;
		Double day_perc = null;	
		Integer day = null;
		String status = "";
		int rw = tblSubStage.getSelectedRow();
		int rw1 = tblDays.getSelectedRow();

		set_no = (Integer) modelSubStage.getValueAt(rw,6);
		stage_no = (Integer) modelSubStage.getValueAt(rw,7);
		substage_no = (Integer) tblSubStage.getValueAt(rw,0);
		substage_desc = tblSubStage.getValueAt(rw,1).toString().trim();
		day_no = Integer.parseInt(txtDayNo.getText().toString());
		day_perc = Double.parseDouble(txtDayPercent.getText());
		day = (Integer) tblDays.getValueAt(rw1,0);
		if(dayoption1.isSelected()==true){status = "A";}
		else {status = "I";}

		String sqlDetail =
				"UPDATE mf_accomp_stage_dtl set " + 
						"day_no = '"+day_no+"',  \n" +	    	         //1
						"day_perc = '"+day_perc+"',  \n" +	 		     //2
						"status_id = '"+status+"',   \n" +   			 //3
						"edited_by = '"+UserInfo.EmployeeCode+"', \n"  + //4
						"edited_date = now() \n"  +	 					 //5
						"where set_no = '"+set_no+"' and " +
						"stage_no = '"+stage_no+"' and " +
						"substage_no = '"+substage_no+"' and " +
						"substage_desc = '"+substage_desc+"' and " +
						"day_no = '"+day+"' ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}


	//display tables

	private void displayAccomplishmentStage(DefaultTableModel modelMain, JList rowHeader, String model_id){

		FncTables.clearTable(modelMain); //Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel(); //Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); //Setting of DefaultListModel into rowHeader.

		String sql = "select\n" + 
				"stage_no, \n" + 
				"stage_desc,\n" + 
				"stage_perc,\n" + 
				"a.status_id,\n" + 
				"c.entity_name,\n" + 
				"a.created_date,\n" + 
				"a.set_no\n"+
				"from mf_accomp_stage_main a\n" + 
				"left join em_employee b on a.created_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where model_id = '"+model_id+"' \n" + 
				"order by stage_no ASC";

		FncSystem.out("Display sql for description", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}
	}

	private void displayAccomplishmentSubStage(DefaultTableModel modelMain, JList rowHeader, Integer set_no, Integer stage_no){

		FncTables.clearTable(modelMain); //Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel(); //Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); //Setting of DefaultListModel into rowHeader.

		String sql = "select distinct on (substage_no)\n" + 
				"substage_no,\n" + 
				"substage_desc,\n" + 
				"substage_perc,\n" + 
				"a.status_id,\n" + 
				"c.entity_name as created_by,\n" + 
				"a.created_date,\n" +
				"a.set_no,\n" +
				"a.stage_no\n" +
				"from mf_accomp_stage_dtl a\n" + 
				"left join em_employee b on a.created_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where a.set_no = '"+set_no+"' and a.stage_no = '"+stage_no+"'";

		FncSystem.out("Display sql for description", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}
	}

	private void displayAccomplishmentDays(DefaultTableModel modelMain, JList rowHeader, Integer set_no, Integer stage_no, Integer substage_no){

		FncTables.clearTable(modelMain); //Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel(); //Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); //Setting of DefaultListModel into rowHeader.

		String sql = "select\n" + 
				"day_no,\n" + 
				"day_perc,\n" + 
				"a.status_id,\n" + 
				"c.entity_name as created_by,\n" + 
				"a.created_date\n" + 
				"from mf_accomp_stage_dtl a\n" + 
				"left join em_employee b on a.created_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where set_no = '"+set_no+"' and stage_no = '"+stage_no+"' and substage_no = '"+substage_no+"' \n" +
				"order by day_no ASC" ;

		FncSystem.out("Display sql for description", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}
	}

	private void displayAccomplishmentSubStageTab(){
		if (tblStage.getSelectedRows().length>0){
			int rw = tblStage.getSelectedRow();

			Integer set_no = (Integer) modelStage.getValueAt(rw,6);
			Integer stage_no = (Integer) modelStage.getValueAt(rw,0);

			displayAccomplishmentSubStage(modelSubStage, rowHeaderSubStage, set_no, stage_no);

		}
	}

	private void displayAccomplishmentDaysTab(){
		if (tblSubStage.getSelectedRows().length>0){
			int rw = tblSubStage.getSelectedRow();

			Integer set_no = (Integer) modelSubStage.getValueAt(rw,6);
			Integer stage_no = (Integer) modelSubStage.getValueAt(rw,7);
			Integer substage_no = (Integer) modelSubStage.getValueAt(rw,0);

			displayAccomplishmentDays(modelDays, rowHeaderDays, set_no, stage_no, substage_no);

		}
	}

	private static Object [] getAccomplishmentStageDetails(String stage_no, String stage_desc, String status_id, String model_id, String set_no) {			

		String strSQL = 
				"select * from mf_accomp_stage_main where set_no = '"+set_no+"' and " +
						"model_id = '"+model_id+"' and " +
						"stage_no = '"+stage_no+"' and " +
						"stage_desc = '"+stage_desc+"' and " +
						"status_id = '"+status_id+"' ";

		FncSystem.out("Display sql for description", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static Object [] getAccomplishmentSubStageDetails(String set_no, String stage_no, String substage_no, String substage_desc) {			

		String strSQL = 
				"select * from mf_accomp_stage_dtl \n" + 
						"where set_no = '"+set_no+"' and " +
						"stage_no = '"+stage_no+"' and " +
						"substage_no = '"+substage_no+"' and " +
						"substage_desc = '"+substage_desc+"' ";

		FncSystem.out("Display sql for description", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static Object [] getAccomplishmentDayDetails(String day_no, String set_no, String stage_no, String substage_no, String substage_desc) {			

		String strSQL = 
				"select * from mf_accomp_stage_dtl \n" + 
						"where set_no = '"+set_no+"' and " +
						"stage_no = '"+stage_no+"' and " +
						"substage_no = '"+substage_no+"' and " +
						"day_no = '"+day_no+"' and " +
						"substage_desc = '"+substage_desc+"' ";

		FncSystem.out("Display sql for description", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}


	//action

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	class PopupTriggerListener_panel3 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu3.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu3.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Save Accomplishment Stage")) { Save(); }

		if (e.getActionCommand().equals("Save Accomplishment SubStage")) { Save_a(); }

		if (e.getActionCommand().equals("Save Accomplishment Days")) { Save_b(); }

		if (e.getActionCommand().equals("Preview")) { preview(); }

	}

	private void AddRow(){

		refresh_fields();

		to_do = "stage_save";

		JOptionPane.showOptionDialog(getContentPane(), pnlAddNew, "Add New Accomplishment Stage",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

	}

	private void AddRow_a(){

		refresh_fields();

		to_do = "substage_save";

		JOptionPane.showOptionDialog(getContentPane(), pnlsubAddNew, "Add New Accomplishment Substage",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

	}

	private void AddRow_b(){

		refresh_fields();

		to_do = "day_save";

		JOptionPane.showOptionDialog(getContentPane(), pnldayAddNew, "Add New Accomplishment Substage No.2 (Days)",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

	}

	private void Edit(){	

		to_do = "stage_update";

		if (tblStage.getSelectedRows().length>0){
			int rw = tblStage.getSelectedRow();
			String stage_no = tblStage.getValueAt(rw,0).toString().trim();
			String stage_desc = tblStage.getValueAt(rw,1).toString().trim();
			String status_id = tblStage.getValueAt(rw,3).toString().trim();
			String model_id = lookupHouseMod.getValue();
			String set_no = modelStage.getValueAt(rw,6).toString().trim();

			Object [] ch_ord = getAccomplishmentStageDetails(stage_no, stage_desc, status_id, model_id, set_no);

			cmbStage.setSelectedItem(((Integer) ch_ord[2]).toString());
			txtStageDesc.setText((String) ch_ord[3]);
			txtPercent.setText(((BigDecimal) ch_ord[4]).toString());
			String status = (String) ch_ord [5];
			if (status.equals("A"))
			{option1.setSelected(true); option2.setSelected(false);} 
			else
			{option1.setSelected(false); option2.setSelected(true);}

		}

		JOptionPane.showOptionDialog(getContentPane(), pnlAddNew, "Edit Accomplishment Stage",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

	}

	private void Edit_a(){	

		to_do = "substage_update";

		if (tblSubStage.getSelectedRows().length>0){
			int rw = tblSubStage.getSelectedRow();
			String set_no = modelSubStage.getValueAt(rw, 6).toString();
			String stage_no = modelSubStage.getValueAt(rw, 7).toString();
			String substage_no = tblSubStage.getValueAt(rw, 0).toString();
			String substage_desc = tblSubStage.getValueAt(rw, 1).toString();

			Object [] ch_ord = getAccomplishmentSubStageDetails(set_no, stage_no, substage_no, substage_desc);

			cmbSubStage.setSelectedItem(((Integer) ch_ord[2]).toString());
			txtSubStageDesc.setText((String) ch_ord[3]);
			txtSubPercent.setText(((BigDecimal) ch_ord[4]).toString());
			String status = (String) ch_ord [7];
			if (status.equals("A"))
			{suboption1.setSelected(true); suboption2.setSelected(false);} 
			else
			{suboption1.setSelected(false); suboption2.setSelected(true);}

		}

		JOptionPane.showOptionDialog(getContentPane(), pnlsubAddNew, "Edit Accomplishment SubStage",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

	}

	private void Edit_b(){

		to_do = "day_update";

		if (tblDays.getSelectedRows().length>0){
			int rw = tblDays.getSelectedRow();
			int rw1 = tblSubStage.getSelectedRow();
			String set_no = modelSubStage.getValueAt(rw1, 6).toString();
			String stage_no = modelSubStage.getValueAt(rw1, 7).toString();
			String substage_no = tblSubStage.getValueAt(rw1, 0).toString();
			String substage_desc = tblSubStage.getValueAt(rw1, 1).toString();
			String day_no = tblDays.getValueAt(rw, 0).toString();


			Object [] ch_ord = getAccomplishmentDayDetails(day_no, set_no, stage_no, substage_no, substage_desc);


			txtDayNo.setText(((Integer) ch_ord[5]).toString());
			txtDayPercent.setText(((BigDecimal) ch_ord[6]).toString());
			String status = (String) ch_ord [7];
			if (status.equals("A"))
			{dayoption1.setSelected(true); dayoption2.setSelected(false);} 
			else
			{dayoption1.setSelected(false); dayoption2.setSelected(true);}

		}

		JOptionPane.showOptionDialog(getContentPane(), pnldayAddNew, "Edit Accomplishment SubStage No.2 (Days)",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

	}

	private void Save(){
		if(checkAccomplishmentDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please complete filling up the form.", "Incomplete", 
				JOptionPane.WARNING_MESSAGE);}

		else {

			if(to_do.equals("stage_save")){

				Integer set_no = null;
				Integer stage_no = null;
				int rw = tblStage.getSelectedRow();

				set_no = (Integer) modelStage.getValueAt(rw, 6);
				stage_no = Integer.parseInt(cmbStage.getSelectedItem().toString());

				if(stageExistAdd(set_no,stage_no).equals("") || stageExistAdd(set_no,stage_no).equals(null)) {


					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();
						insertAccomplishmentStageDetails(db);
						db.commit();
						JOptionPane.showMessageDialog(getContentPane(),"All data saved.","Information",JOptionPane.INFORMATION_MESSAGE);
						String model_id = lookupHouseMod.getValue();
						displayAccomplishmentStage(modelStage, rowHeaderStage, model_id);
						refresh_fields();
						modelSubStage.clear();
						modelDays.clear();
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlAddNew);
						optionPaneWindow.dispose();
					}
				}
				else

				{
					JOptionPane.showMessageDialog(getContentPane(),"Stage/Substage already exists.","ERROR",JOptionPane.ERROR_MESSAGE);							
				}
			}

			if(to_do.equals("stage_update")) {

				Integer set_no = null;
				Integer stage_no = null;
				String stage_desc	= "";
				Double stage_perc = null;
				String status = "";
				int rw = tblStage.getSelectedRow();

				set_no = (Integer) modelStage.getValueAt(rw, 6);
				stage_no = Integer.parseInt(cmbStage.getSelectedItem().toString());
				stage_desc = txtStageDesc.getText();
				stage_perc = Double.parseDouble(txtPercent.getText());
				if(option1.isSelected()==true){status = "A";}
				else {status = "I";}

				if(stageExistEdit(set_no,stage_no, stage_desc, stage_perc, status).equals("") || stageExistEdit(set_no,stage_no, stage_desc, stage_perc, status).equals(null)) {

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();	//1
						updateAccomplishmentStageDetails(db);//2
						db.commit();	//3
						JOptionPane.showMessageDialog(getContentPane(),"All data updated","Information",JOptionPane.INFORMATION_MESSAGE);
						String model_id = lookupHouseMod.getValue();
						displayAccomplishmentStage(modelStage, rowHeaderStage, model_id);
						refresh_fields();
						modelSubStage.clear();
						modelDays.clear();
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlAddNew);
						optionPaneWindow.dispose();
					}

				}
				else

				{
					JOptionPane.showMessageDialog(getContentPane(),"Stage/Substage already exists.","ERROR",JOptionPane.ERROR_MESSAGE);							
				}
			}
		}
	}

	private void Save_a(){
		if(checkAccomplishmentSubStageDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please complete filling up the form.", "Incomplete", 
				JOptionPane.WARNING_MESSAGE);}

		else {

			if(to_do.equals("substage_save")){

				Integer set_no = null;
				Integer stage_no = null;
				Integer substage_no = null;
				int rw = tblStage.getSelectedRow();

				set_no = (Integer) modelStage.getValueAt(rw,6);
				stage_no = (Integer) modelStage.getValueAt(rw,0);
				substage_no = Integer.parseInt(cmbSubStage.getSelectedItem().toString());


				if(substageExistAdd(set_no, stage_no, substage_no).equals("") || substageExistAdd(set_no, stage_no, substage_no).equals(null)) {


					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();
						insertAccomplishmentSubStageDetails(db);
						db.commit();
						JOptionPane.showMessageDialog(getContentPane(),"All data saved.","Information",JOptionPane.INFORMATION_MESSAGE);
						displayAccomplishmentSubStage(modelSubStage, rowHeaderSubStage, set_no, stage_no);
						refresh_fields();
						modelDays.clear();
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlsubAddNew);
						optionPaneWindow.dispose();
					}
				}
				else

				{
					JOptionPane.showMessageDialog(getContentPane(),"Stage/Substage already exists.","ERROR",JOptionPane.ERROR_MESSAGE);							
				}
			}

			if(to_do.equals("substage_update")) {

				Integer set_no = null;
				Integer stage_no = null;
				Integer substage_no = null;
				String substage_desc	= "";
				Double substage_perc = null;
				String status = "";
				int rw = tblSubStage.getSelectedRow();

				set_no = (Integer) modelSubStage.getValueAt(rw,6);
				stage_no = (Integer) modelSubStage.getValueAt(rw,7);
				substage_no = Integer.parseInt(cmbSubStage.getSelectedItem().toString());
				substage_desc = txtSubStageDesc.getText();
				substage_perc = Double.parseDouble(txtSubPercent.getText());
				if(suboption1.isSelected()==true){status = "A";}
				else {status = "I";}

				if(substageExistEdit(set_no, stage_no, substage_no, substage_desc, substage_perc, status).equals("") || substageExistEdit(set_no, stage_no, substage_no, substage_desc, substage_perc, status).equals(null)) {

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();	//1
						updateAccomplishmentSubStageDetails(db);//2
						db.commit();	//3
						JOptionPane.showMessageDialog(getContentPane(),"All data updated","Information",JOptionPane.INFORMATION_MESSAGE);
						displayAccomplishmentSubStage(modelSubStage, rowHeaderSubStage, set_no, stage_no);
						refresh_fields();
						modelDays.clear();
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlsubAddNew);
						optionPaneWindow.dispose();
					}

				}
				else

				{
					JOptionPane.showMessageDialog(getContentPane(),"Stage/Substage already exists.","ERROR",JOptionPane.ERROR_MESSAGE);							
				}
			}
		}
	}

	private void Save_b(){
		if(checkAccomplishmentDayDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please complete filling up the form.", "Incomplete", 
				JOptionPane.WARNING_MESSAGE);}

		else {

			if(to_do.equals("day_save")){

				Integer day_no = null;
				Integer set_no = null;
				Integer stage_no = null;
				Integer substage_no = null;
				int rw = tblSubStage.getSelectedRow();

				day_no = Integer.parseInt(txtDayNo.getText().toString());
				set_no = (Integer) modelSubStage.getValueAt(rw,6);
				stage_no = (Integer) modelSubStage.getValueAt(rw,7);
				substage_no = (Integer) modelSubStage.getValueAt(rw,0);

				if(dayExistAdd(day_no, set_no, stage_no, substage_no).equals("") || dayExistAdd(day_no, set_no, stage_no, substage_no).equals(null)) {

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();
						insertAccomplishmentDayDetails(db);
						db.commit();
						JOptionPane.showMessageDialog(getContentPane(),"All data saved.","Information",JOptionPane.INFORMATION_MESSAGE);
						displayAccomplishmentDays(modelDays, rowHeaderDays, set_no, stage_no, substage_no);
						refresh_fields();
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnldayAddNew);
						optionPaneWindow.dispose();

					}
				}

				else

				{
					JOptionPane.showMessageDialog(getContentPane(),"Day number already exists.","ERROR",JOptionPane.ERROR_MESSAGE);							
				}
			}

			if(to_do.equals("day_update")) {

				Integer day_no = null;
				Double day_perc = null;
				String status = "";
				Integer set_no = null;
				Integer stage_no = null;
				Integer substage_no = null;
				int rw = tblSubStage.getSelectedRow();

				day_no = Integer.parseInt(txtDayNo.getText().toString());
				day_perc = Double.parseDouble(txtDayPercent.getText().toString());
				if(dayoption1.isSelected()==true){status = "A";}
				else {status = "I";}
				set_no = (Integer) modelSubStage.getValueAt(rw,6);
				stage_no = (Integer) modelSubStage.getValueAt(rw,7);
				substage_no = (Integer) modelSubStage.getValueAt(rw,0);

				if(dayExistEdit(day_no, day_perc, status, set_no, stage_no, substage_no).equals("") || dayExistEdit(day_no, day_perc, status, set_no, stage_no, substage_no).equals(null)) {

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();	//1
						updateAccomplishmentDayDetails(db);//2
						db.commit();	//3
						JOptionPane.showMessageDialog(getContentPane(),"All data updated","Information",JOptionPane.INFORMATION_MESSAGE);
						displayAccomplishmentSubStage(modelSubStage, rowHeaderSubStage, set_no, stage_no);
						refresh_fields();
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnldayAddNew);
						optionPaneWindow.dispose();

					}

				}
				else

				{
					JOptionPane.showMessageDialog(getContentPane(),"Day number already exists.","ERROR",JOptionPane.ERROR_MESSAGE);							
				}
			}
		}
	}

	public void preview(){	

		String criteria = "Construction Accomplishment Stage";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		int rw = tblStage.getSelectedRow();
		String set_no = modelStage.getValueAt(rw, 6).toString();

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("house_name", lblHouse.getText());
		mapParameters.put("model_id", lookupHouseMod.getValue());
		mapParameters.put("set_no", set_no);

		FncReport.generateReport("/Reports/rptConstructionAccompStage.jasper", reportTitle, null, mapParameters);

	}

	private void refresh_fields(){

		cmbStage.setSelectedIndex(0);
		txtStageDesc.setText("");
		txtPercent.setText("0");
		option1.setSelected(true);
		option2.setSelected(false);
		cmbSubStage.setSelectedIndex(0);
		txtSubStageDesc.setText("");
		txtSubPercent.setText("0");
		suboption1.setSelected(true);
		suboption2.setSelected(false);
		txtDayNo.setText("");
		txtDayPercent.setText("0");
		dayoption1.setSelected(true);
		dayoption2.setSelected(false);

	}


	//Boolean

	private Boolean checkAccomplishmentDetails(){

		boolean x = false;

		String stage_no, stage_desc, percent_acc;

		stage_no = cmbStage.getSelectedItem().toString();
		stage_desc = txtStageDesc.getText();
		percent_acc = txtPercent.getText();

		if (stage_no.equals("") || stage_desc.equals("") || percent_acc.equals("")) {x=false;}
		else {x=true;}

		return x;
	}

	private Boolean checkAccomplishmentSubStageDetails(){

		boolean x = false;

		String stage_no, stage_desc, percent_acc;

		stage_no = cmbSubStage.getSelectedItem().toString();
		stage_desc = txtSubStageDesc.getText();
		percent_acc = txtSubPercent.getText();

		if (stage_no.equals("") || stage_desc.equals("") || percent_acc.equals("")) {x=false;}
		else {x=true;}

		return x;
	}

	private Boolean checkAccomplishmentDayDetails(){

		boolean x = false;

		String day_no, percent_accomp;

		day_no = txtDayNo.getText();
		percent_accomp = txtDayPercent.getText();

		if (day_no.equals("") || percent_accomp.equals("")) {x=false;}
		else {x=true;}

		return x;
	}


	//Validation

	private static String stageExistAdd(Integer set_no, Integer stage_no) {

		String stageExist = "";
		String SQL = 
				"select stage_desc from mf_accomp_stage_main "
						+ "where set_no = '"+set_no+"' and "
						+ "stage_no = '"+stage_no+"' ";		

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {}
			else {stageExist = (String)db.getResult()[0][0].toString() ;}
		}else{
			stageExist = "";
		}

		return stageExist;
	}

	private static String substageExistAdd(Integer set_no, Integer stage_no, Integer substage_no) {

		String substageExist = "";
		String SQL = 
				"select substage_desc from mf_accomp_stage_dtl "
						+ "where set_no = '"+set_no+"' and "
						+ "stage_no = '"+stage_no+"' and "
						+ "substage_no = '"+substage_no+"' ";		

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {}
			else {substageExist = (String)db.getResult()[0][0].toString() ;}
		}else{
			substageExist = "";
		}

		return substageExist;
	}

	private static String dayExistAdd(Integer day_no, Integer set_no, Integer stage_no, Integer substage_no) {

		String dayExist = "";
		String SQL = 
				"select substage_desc from mf_accomp_stage_dtl "
						+ "where day_no = '"+day_no+"' and "
						+ "set_no = '"+set_no+"' and "
						+ "stage_no = '"+stage_no+"' and "
						+ "substage_no = '"+substage_no+"' ";		

		FncSystem.out("Sql", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {}
			else {dayExist = (String)db.getResult()[0][0].toString() ;}
		}else{
			dayExist = "";
		}



		return dayExist;
	}

	private static String stageExistEdit(Integer set_no, Integer stage_no, String stage_desc, Double stage_perc, String status ) {

		String stageExist = "";
		String SQL = 
				"select stage_desc from mf_accomp_stage_main "
						+ "where set_no = '"+set_no+"' and "
						+ "stage_no = '"+stage_no+"' and "
						+ "stage_desc = '"+stage_desc+"' and "
						+ "stage_perc = '"+stage_perc+"' and "
						+ "status_id = '"+status+"' ";			

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {}
			else {stageExist = (String)db.getResult()[0][0].toString() ;}
		}else{
			stageExist = "";
		}

		return stageExist;
	}

	private static String substageExistEdit(Integer set_no, Integer stage_no, Integer substage_no, String substage_desc, Double substage_perc, String status ) {

		String substageExist = "";
		String SQL = 
				"select substage_desc from mf_accomp_stage_dtl "
						+ "where set_no = '"+set_no+"' and "
						+ "stage_no = '"+stage_no+"' and "
						+ "substage_no = '"+substage_no+"' and "
						+ "substage_desc = '"+substage_desc+"' and "
						+ "substage_perc = '"+substage_perc+"' and "
						+ "status_id = '"+status+"' ";			

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {}
			else {substageExist = (String)db.getResult()[0][0].toString() ;}
		}else{
			substageExist = "";
		}

		return substageExist;
	}

	private static String dayExistEdit(Integer day_no, Double day_perc, String status, Integer set_no, Integer stage_no, Integer substage_no) {

		String dayExist = "";
		String SQL = 
				"select substage_desc from mf_accomp_stage_dtl "
						+ "where day_no = '"+day_no+"' and "
						+ "day_perc = '"+day_perc+"' and "
						+ "status_id = '"+status+"' and "
						+ "set_no = '"+set_no+"' and "
						+ "stage_no = '"+stage_no+"' and "
						+ "substage_no = '"+substage_no+"' ";		

		FncSystem.out("Sql", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {}
			else {dayExist = (String)db.getResult()[0][0].toString() ;}
		}else{
			dayExist = "";
		}



		return dayExist;
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
