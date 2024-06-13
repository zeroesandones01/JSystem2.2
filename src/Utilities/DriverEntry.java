package Utilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import FormattedTextField.NumericPlainDocument;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JButton;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_Assigned_Meeting_Place;

public class DriverEntry extends _JInternalFrame implements _GUI, ActionListener, MouseListener,MouseMotionListener,KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Driver Entry";
	public static Dimension frameSize = new Dimension(750, 600);
	private JXPanel pnlNorth;
	private JXPanel pnlNorth_N;
	private JLabel lblCode;
	private JXPanel pnlNorth_C;
	private JXPanel pnlNorth_N_E;
	private _JLookup lookupCode;
	private JLabel lblFirstName;
	private JLabel lblMiddleName;
	private JLabel lblLastName;
	private JLabel lblAlias;
	private _JXTextField txtFirstName;
	private _JXTextField txtMiddleName;
	private _JXTextField txtLastName;
	private _JXTextField txtAlias;
	private _JXTextField txtAddress;
	private JLabel lblAddress;
	private JLabel lblGender;
	private JLabel lblMobileNo;
	private JLabel lblStatus;
	private JLabel lblTin;
	private DefaultComboBoxModel cmbGenderModel;
	private JComboBox cmbGender;
	private _JXTextField txtMobileNo;
	private DefaultComboBoxModel cmbStatusModel;
	private JComboBox cmbStatus;
	private _JXTextField txtTinNo;
	private _JXTextField txtATMNo;
	private JXPanel pnlNorth_S;
	private JXPanel pnlTableMeetingPlace;
	private JScrollPane scrollMeetingPlace;
	private model_Assigned_Meeting_Place modelMeetingPlaceModel;
	private _JTableMain tblMeetingPlace;
	private JList rowHeaderMeetingPlace;
	private JXPanel pnlCenter;
	private _JButton btnNew;
	private _JButton btnSave;
	private _JButton btnEdit;
	private _JButton btnCancel;
	private JXPanel pnlTextName;
	private JLabel lblLotBlk;
	private _JXTextField txtLotBlk;
	private JLabel lblStreet;
	private _JXTextField txtStreet;
	private JLabel lblBrgy;
	private _JXTextField txtBrgy;
	private ButtonGroup grpButton = new ButtonGroup();
	private String entity_id;

	public DriverEntry() {
		super(title, true, true, false, true);
		initGUI();
		FormLoad();
	}

	public DriverEntry(String title) {
		super(title);
		initGUI();
		FormLoad();
	}

	public DriverEntry(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		FormLoad();
	}

	@Override
	public void initGUI() {
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new java.awt.Dimension(frameSize));
			this.getContentPane().setLayout(new BorderLayout());

			{
				JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlNorth = new JXPanel();
					pnlMain.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlNorth.setLayout(new BorderLayout(5, 5));
					pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 514));
					{
						{
							pnlNorth_N = new JXPanel();
							pnlNorth.add(pnlNorth_N,BorderLayout.NORTH);
							pnlNorth_N.setLayout(new BorderLayout(5, 5));
							pnlNorth_N.setPreferredSize(new Dimension(this.getWidth(), 25));
							//pnlNorth_N.setBorder(FncGlobal.lineBorder);
							{
								{
									pnlNorth_N_E = new JXPanel();
									pnlNorth_N.add(pnlNorth_N_E,BorderLayout.WEST);
									pnlNorth_N_E.setLayout(new BorderLayout(5, 5));
									pnlNorth_N_E.setPreferredSize(new Dimension(170, 25));
									{
										lblCode = new JLabel("Code : ");
										pnlNorth_N_E.add(lblCode,BorderLayout.WEST);
										lblCode.setPreferredSize(new Dimension(50, 25));
									}
									{

										lookupCode = new _JLookup("", "Code", 0) ; /// XXX lookupCode 
										pnlNorth_N_E.add(lookupCode,BorderLayout.CENTER);
										lookupCode.setReturnColumn(0);
										lookupCode.setLookupSQL("SELECT a.entity_id AS \"Entity ID \",a.entity_name AS \"Entity Name\" FROM rf_entity a\n" + 
												"LEFT JOIN  rf_entity_assigned_type b ON a.entity_id = b.entity_id\n" + 
												"WHERE a.status_id = 'A'\n" + 
												"AND b.entity_type_id = '39'");
										lookupCode.setFilterName(true);
										lookupCode.addLookupListener(new LookupListener() {



											@Override
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup)e.getSource()).getDataSet();

												if(data != null){
													entity_id = data[0].toString();
													EditProcess();
													btnState(false, true, false, true);

												}
											}
										});

									}
								}
							}

						}//pnlNorth_N

						{
							pnlNorth_C = new JXPanel();
							pnlNorth.add(pnlNorth_C,BorderLayout.CENTER);
							pnlNorth_C.setLayout(new BorderLayout(3, 3));
							pnlNorth_C.setPreferredSize(new Dimension(this.getWidth(), 250));
							pnlNorth_C.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
							{
								{
									JXPanel pnlNorth = new JXPanel();
									pnlNorth_C.add(pnlNorth,BorderLayout.NORTH);
									pnlNorth.setLayout(new BorderLayout(3, 3));
									pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 100));
									{
										{
											JXPanel pnlLabelName= new JXPanel();
											pnlNorth.add(pnlLabelName,BorderLayout.WEST);
											pnlLabelName.setLayout(new GridLayout(4, 1, 3, 3));
											pnlLabelName.setPreferredSize(new Dimension(80, 100));
											{
												lblFirstName = new JLabel("First Name");
												pnlLabelName.add(lblFirstName);

												lblMiddleName = new JLabel("Middle Name");
												pnlLabelName.add(lblMiddleName);

												lblLastName = new JLabel("Last Name");
												pnlLabelName.add(lblLastName);

												lblAlias = new JLabel("Alias");
												pnlLabelName.add(lblAlias);

											}
										}
										{
											pnlTextName = new JXPanel();
											pnlNorth.add(pnlTextName,BorderLayout.CENTER);
											pnlTextName.setLayout(new GridLayout(4, 1, 3, 3));
											pnlTextName.setPreferredSize(new Dimension(200, 100));
											{
												txtFirstName = new _JXTextField("First Name");
												pnlTextName.add(txtFirstName);

												txtMiddleName = new _JXTextField("Middle Name");
												pnlTextName.add(txtMiddleName);

												txtLastName = new _JXTextField("Last Name");
												pnlTextName.add(txtLastName);

												txtAlias = new _JXTextField("Alias");
												pnlTextName.add(txtAlias);

											}
										}
										{
											JXPanel pnlNone = new JXPanel();
											pnlNorth.add(pnlNone,BorderLayout.EAST);
											pnlNone.setLayout(new GridLayout(4, 1, 3, 3));
											pnlNone.setPreferredSize(new Dimension(200, 100));
										}
									}
								}//pnlNorth
								{
									JXPanel pnlCenter = new JXPanel();
									pnlNorth_C.add(pnlCenter,BorderLayout.CENTER);
									pnlCenter.setLayout(new BorderLayout(3, 3)) ;
									//pnlCenter.setPreferredSize(new Dimension(this.getWidth(), 0));

									{
										JXPanel pnlAddress = new JXPanel();
										pnlCenter.add(pnlAddress,BorderLayout.NORTH);
										pnlAddress.setLayout(new BorderLayout(3, 3)) ;
										pnlAddress.setPreferredSize(new Dimension(this.getWidth(), 22));
										{
											JXPanel pnlAdd = new JXPanel();
											pnlAddress.add(pnlAdd,BorderLayout.WEST);
											pnlAdd.setLayout(new BorderLayout(3, 3)) ;
											pnlAdd.setPreferredSize(new Dimension(200, 22));

											{
												lblLotBlk = new JLabel("Lot No. /Blk");
												pnlAdd.add(lblLotBlk,BorderLayout.WEST);
												lblLotBlk.setPreferredSize(new Dimension(80, 25));
											}
											{
												txtLotBlk = new _JXTextField("Lot No./ Blk");
												pnlAdd.add(txtLotBlk,BorderLayout.CENTER);
											}
										}
										{
											JXPanel pnlLotBlk = new JXPanel();
											pnlAddress.add(pnlLotBlk,BorderLayout.CENTER);
											pnlLotBlk.setLayout(new BorderLayout(3, 3)) ;
											pnlLotBlk.setPreferredSize(new Dimension(90, 22));

											{
												lblStreet = new JLabel("Street / Subd.");
												pnlLotBlk.add(lblStreet,BorderLayout.WEST);
												lblStreet.setPreferredSize(new Dimension(90, 25));
											}
											{
												txtStreet = new _JXTextField("Street / Subd.");
												pnlLotBlk.add(txtStreet,BorderLayout.CENTER);
											}
										}
										{
											JXPanel pnlBrgy = new JXPanel();
											pnlAddress.add(pnlBrgy,BorderLayout.EAST);
											pnlBrgy.setLayout(new BorderLayout(3, 3)) ;
											pnlBrgy.setPreferredSize(new Dimension(250, 22));

											{
												lblBrgy = new JLabel("Disrt. / Brgy.");
												pnlBrgy.add(lblBrgy,BorderLayout.WEST);
												lblBrgy.setPreferredSize(new Dimension(80, 25));
											}
											{
												txtBrgy = new _JXTextField("Disrt. / Brgy.");
												pnlBrgy.add(txtBrgy,BorderLayout.CENTER);

											}
										}
									}

									{
										JXPanel pnl_center = new JXPanel();
										pnlCenter.add(pnl_center,BorderLayout.CENTER);
										pnl_center.setLayout(new BorderLayout(3, 3)) ;
										//pnl_center.setPreferredSize(new Dimension(this.getWidth(), 22));
										{
											JXPanel pnl_label = new JXPanel();
											pnl_center.add(pnl_label,BorderLayout.NORTH);
											pnl_label.setLayout(new GridLayout(1, 2, 3, 3)) ;
											pnl_label.setPreferredSize(new Dimension(80, 50));

											{
												JXPanel pnlLabelWest= new JXPanel();
												pnl_label.add(pnlLabelWest);
												pnlLabelWest.setLayout(new BorderLayout(3,3));
												pnlLabelWest.setPreferredSize(new Dimension(77, 50));
												{
													{
														JXPanel pnlLabelName= new JXPanel();
														pnlLabelWest.add(pnlLabelName,BorderLayout.WEST);
														pnlLabelName.setLayout(new GridLayout(2, 1, 3, 3));
														pnlLabelName.setPreferredSize(new Dimension(80, 50));
														{
															lblGender = new JLabel("Gender");
															pnlLabelName.add(lblGender);

															lblMobileNo = new JLabel("Mobile No");
															pnlLabelName.add(lblMobileNo);

														}
													}
													{
														JXPanel pnlTxtField = new JXPanel();
														pnlLabelWest.add(pnlTxtField,BorderLayout.CENTER);
														pnlTxtField.setLayout(new GridLayout(2, 1, 3, 3));
														pnlTxtField.setPreferredSize(new Dimension(80, 50));
														{
															cmbGenderModel = new DefaultComboBoxModel(new String[] {"","Male", "Female"});
															cmbGender = new JComboBox();
															pnlTxtField.add(cmbGender);
															cmbGender.setModel(cmbGenderModel);
															cmbGender.addActionListener(this);
														}
														{
															txtMobileNo = new _JXTextField("Mobile No.");
															pnlTxtField.add(txtMobileNo,BorderLayout.CENTER);
															txtMobileNo.setDocument(new NumericPlainDocument(new DecimalFormat("#")));
															txtMobileNo.addKeyListener(new KeyAdapter() {
																public void keyTyped(KeyEvent e) {
																	if (txtMobileNo.getText().length() == 11) {
																		e.consume();
																	}
																}
															});
														}
													}
												}
											}
											{
												JXPanel pnlLabelEast= new JXPanel();
												pnl_label.add(pnlLabelEast);
												pnlLabelEast.setLayout(new BorderLayout());
												pnlLabelEast.setPreferredSize(new Dimension(80, 50));
												{

													{
														JXPanel pnlLabel= new JXPanel();
														pnlLabelEast.add(pnlLabel,BorderLayout.WEST);
														pnlLabel.setLayout(new GridLayout(2, 1, 3, 3));
														pnlLabel.setPreferredSize(new Dimension(50, 50));
														{
															lblStatus = new JLabel("Status");
															pnlLabel.add(lblStatus);

															lblTin = new JLabel("Tin No.");
															pnlLabel.add(lblTin);

														}

													}
													{
														JXPanel pnlTxtField = new JXPanel();
														pnlLabelEast.add(pnlTxtField,BorderLayout.CENTER);
														pnlTxtField.setLayout(new GridLayout(2, 1, 3, 3));
														pnlTxtField.setPreferredSize(new Dimension(80, 50));
														{
															cmbStatusModel = new DefaultComboBoxModel(new String[] {"Active", "Inactive"});
															cmbStatus = new JComboBox();
															pnlTxtField.add(cmbStatus);
															cmbStatus.setModel(cmbStatusModel);
															cmbStatus.addActionListener(this);
														}
														{
															txtTinNo = new _JXTextField("Tin No.");
															pnlTxtField.add(txtTinNo);
															txtTinNo.setDocument(new NumericPlainDocument(new DecimalFormat("#")));
															txtTinNo.addKeyListener(new KeyAdapter() {
																public void keyTyped(KeyEvent e) {
																	if (txtTinNo.getText().length() == 9) {
																		e.consume();
																	}
																}
															});
														}
													}
												}
											}

										}
									}

								}//pnlCenter
								{
									JXPanel pnlSouth = new JXPanel();
									pnlNorth_C.add(pnlSouth,BorderLayout.SOUTH);
									pnlSouth.setLayout(new BorderLayout(3, 3)) ;
									{
										JXPanel pnlATM= new JXPanel();
										pnlSouth.add(pnlATM,BorderLayout.WEST);
										pnlATM.setLayout(new BorderLayout(5, 5));
										pnlATM.setPreferredSize(new Dimension(350, 25));
										{
											lblCode = new JLabel("ATM No. ");
											pnlATM.add(lblCode,BorderLayout.WEST);
											lblCode.setPreferredSize(new Dimension(78, 25));
										}
										{
											txtATMNo = new _JXTextField("ATM No.");
											pnlATM.add(txtATMNo,BorderLayout.CENTER);
											txtATMNo.setPreferredSize(new Dimension(78, 25));
											txtATMNo.setDocument(new NumericPlainDocument(new DecimalFormat("#")));
										}
									}
								}
							}
						}
						{
							pnlNorth_S = new JXPanel();
							pnlNorth.add(pnlNorth_S,BorderLayout.SOUTH);
							pnlNorth_S.setLayout(new BorderLayout(3, 3));
							pnlNorth_S.setPreferredSize(new Dimension(this.getWidth(), 230));
							pnlNorth_S.setBorder(components.JTBorderFactory.createTitleBorder("Assigned Meeting Place/s"));
							{

								pnlTableMeetingPlace = new JXPanel(new BorderLayout(3,3));
								pnlNorth_S.add(pnlTableMeetingPlace,BorderLayout.CENTER);
								{


									scrollMeetingPlace = new JScrollPane();
									pnlTableMeetingPlace.add(scrollMeetingPlace, BorderLayout.CENTER);
									scrollMeetingPlace.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									scrollMeetingPlace.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e) {
											tblMeetingPlace.clearSelection();
										}
									});

									{

										modelMeetingPlaceModel = new model_Assigned_Meeting_Place();
										modelMeetingPlaceModel.addTableModelListener(new TableModelListener() {
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowHeaderMeetingPlace.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel)rowHeaderMeetingPlace.getModel()).addElement(modelMeetingPlaceModel.getRowCount());
												}
											}
										});

										tblMeetingPlace = new _JTableMain(modelMeetingPlaceModel);
										scrollMeetingPlace.setViewportView(tblMeetingPlace);
										tblMeetingPlace.setHorizontalScrollEnabled(true);
										tblMeetingPlace.addMouseMotionListener(this);
										tblMeetingPlace.addMouseListener(this);
										tblMeetingPlace.addKeyListener(this);
										modelMeetingPlaceModel.setEditable(true);
										tblMeetingPlace.packAll();
										tblMeetingPlace.hideColumns("Code");

										/** Repaint for Highlight **/
										tblMeetingPlace.getTableHeader().addMouseListener(new MouseAdapter() {
											public void mouseClicked(MouseEvent evt) {
												tblMeetingPlace.repaint();
											}
										});
									}
									{

										rowHeaderMeetingPlace = tblMeetingPlace.getRowHeader();
										rowHeaderMeetingPlace.setModel(new DefaultListModel());
										scrollMeetingPlace.setRowHeaderView(rowHeaderMeetingPlace);
										scrollMeetingPlace.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
							}
						}
					}
				}
				{
					pnlCenter = new JXPanel();
					pnlMain.add(pnlCenter,BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(3, 3));
					pnlCenter.setPreferredSize(new Dimension(this.getWidth(), 150));
					{
						JXPanel pnlButton= new JXPanel();
						pnlCenter.add(pnlButton,BorderLayout.CENTER);
						pnlButton.setLayout(new GridLayout(1, 4, 3, 3));
						{

							{
								btnNew = new _JButton("New");
								pnlButton.add(btnNew);
								btnNew.addActionListener(this);
								btnNew.setActionCommand("New");
								grpButton.add(btnNew);
							}
							{
								btnEdit = new _JButton("Edit");
								pnlButton.add(btnEdit);
								btnEdit.addActionListener(this);
								btnEdit.setActionCommand("Edit");
								grpButton.add(btnEdit);
							}
							{
								btnSave = new _JButton("Save");
								pnlButton.add(btnSave);
								btnSave.addActionListener(this);
							}
							{
								btnCancel = new _JButton("Cancel");
								pnlButton.add(btnCancel);
								btnCancel.addActionListener(this);
							}

						}
					}
				}
			}
		} catch (Exception e) {
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("New")) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			NewProcess();
		}

		if (e.getSource().equals(btnCancel)) {


			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields?   ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				CancelProcess();			
				}
		
			
		}

		if (e.getActionCommand().equals("Edit")) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
		
			this.setComponentsEnabled(pnlNorth_C, true);
			this.setComponentsEditable(pnlNorth_C, true);
			this.setComponentsEditable(pnlNorth_S, true);
			this.setComponentsEnabled(pnlNorth_S, true);

			lookupCode.setEditable(false);
			btnState(false, false, true, true);
			 
		}

		if (e.getSource().equals(btnSave)) {

			if (checkRequiredFields()) {
				if (hasSelectedTagged()) {
					if (hasPriority()) {
						if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							Save();	
							FormLoad();
							CancelProcess();
							JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Successfully saved", "Save", JOptionPane.INFORMATION_MESSAGE);
						}

					}

				}else{
					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please assign preferred meeting place/s for this driver.", "Save", JOptionPane.WARNING_MESSAGE);
					return;
				}

			}

		}

	}//actionPerformed

	private void FormLoad(){
		this.setComponentsEnabled(pnlNorth_C, false);
		this.setComponentsEnabled(pnlNorth_S, false);
		lookupCode.setEditable(true);
		btnState(true, false, false, false);

		rowHeaderMeetingPlace.setModel(new DefaultListModel());
		modelMeetingPlaceModel.clear();
		scrollMeetingPlace.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblMeetingPlace.getRowCount())));
	}

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCance){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCance);

	}

	private void NewProcess(){

		this.setComponentsEnabled(pnlNorth_C, true);
		this.setComponentsEditable(pnlNorth_C, true);
		this.setComponentsEditable(pnlNorth_S, true);
		this.setComponentsEnabled(pnlNorth_S, true);
		btnState(false, false, true, true);

		lookupCode.setEditable(false);

		rowHeaderMeetingPlace.setModel(new DefaultListModel());
		GenerateMeetingPlace(modelMeetingPlaceModel);
		scrollMeetingPlace.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblMeetingPlace.getRowCount())));

		tblMeetingPlace.packAll();
	}

	private void EditProcess(){
		pgSelect db = new pgSelect();

		db.select("select \n" + 
				"first_name,\n" + 
				"last_name,\n" + 
				"middle_name,\n" + 
				"entity_alias,\n" + 
				"COALESCE(addr_no, ''),\n" + 
				"COALESCE(street, ''),\n" + 
				"COALESCE(barangay, ''),\n" + 
				"gender,\n" + 
				"a.status_id,\n" + 
				"UNNEST(mobile),\n" + 
				"COALESCE(tin_no, ''),\n" + 
				"COALESCE(atm_no, '')\n" + 
				"\n" + 
				" from rf_entity a\n" + 
				"LEFT JOIN rf_entity_address b ON a.entity_id = b.entity_id\n" + 
				"LEFT JOIN rf_contacts c ON a.entity_id = c.entity_id\n" + 
				"LEFT JOIN rf_entity_id_no d ON a.entity_id = d.entity_id\n" + 
				"where a.entity_id = '"+entity_id+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"LIMIT 1");

		if (db.isNotNull()) {
			String entity_id = lookupCode.getValue().isEmpty() ? "" :lookupCode.getValue().toString().trim();
			String first_name =  db.Result[0][0].toString().toUpperCase();
			String last_name =  db.Result[0][1].toString().toUpperCase();
			String middle_name = db.Result[0][2].toString().toUpperCase();
			//String entity_name = String.format("%s, %s %s", last_name, first_name, middle_name).toUpperCase();
			String entity_alias = db.Result[0][3].equals("") || db.Result[0][3] == null ? "" : db.Result[0][3].toString().toUpperCase();
			String lotblk = db.Result[0][4].equals("") || db.Result[0][4] == null ? "" : db.Result[0][4].toString().toUpperCase();
			String street = db.Result[0][5].equals("") || db.Result[0][5] == null ? "" : db.Result[0][5].toString().toUpperCase();
			String brgy = db.Result[0][6].equals("") || db.Result[0][6] == null ? "" : db.Result[0][6].toString().toUpperCase();

			String gender = db.Result[0][7].equals("") || db.Result[0][7] == null ? "" : db.Result[0][7].toString().toUpperCase();;
			String status_id = db.Result[0][8].equals("") || db.Result[0][8] == null ? "" : db.Result[0][8].toString().toUpperCase();;


			String mobileNo = db.Result[0][9].equals("") || db.Result[0][9] == null ? "" : db.Result[0][9].toString().toUpperCase();
			String tinNo = db.Result[0][10].equals("") || db.Result[0][10] == null ? "" : db.Result[0][10].toString().toUpperCase();
			String ATMNo = db.Result[0][11].equals("") || db.Result[0][11] == null ? "" : db.Result[0][11].toString().toUpperCase();



			txtFirstName.setText(first_name);
			txtLastName.setText(last_name);
			txtMiddleName.setText(middle_name);
			txtAlias.setText(entity_alias);
			txtLotBlk.setText(lotblk);
			txtStreet.setText(street);
			txtBrgy.setText(brgy);

			if (status_id.equals("A")) {
				cmbStatus.setSelectedIndex(0);
			}else{
				cmbStatus.setSelectedIndex(1);
			}

			if (gender.equals("M")) {
				cmbGender.setSelectedIndex(1);
			}
			if (gender.equals("F")) {
				cmbGender.setSelectedIndex(2);
			}


			txtMobileNo.setText(mobileNo);
			txtTinNo.setText(tinNo);
			txtATMNo.setText(ATMNo);

			ArrayList<String> list_mp_code = new ArrayList<String>(); 
			db.select("SELECT mp_code,priority FROM rf_tripping_driver_assigned_meetplace WHERE entity_id = '"+entity_id+"'");

			for (int i = 0; i < db.getRowCount(); i++) {
				list_mp_code.add(db.Result[i][0].toString());
			}

			rowHeaderMeetingPlace.setModel(new DefaultListModel());
			GenerateMeetingPlace_Edit(modelMeetingPlaceModel,entity_id);
			scrollMeetingPlace.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblMeetingPlace.getRowCount())));

			tblMeetingPlace.packAll();

			for (int i = 0; i < modelMeetingPlaceModel.getRowCount(); i++) {
				if (list_mp_code.contains(modelMeetingPlaceModel.getValueAt(i, 3))) {
					modelMeetingPlaceModel.setValueAt(true, i, 0);
				}
			}
		}
		
	}

	private void CancelProcess(){

		FormLoad();

		for (Component component : pnlTextName.getComponents()){
			if(component instanceof JTextField){
				((JTextField) component).setText("");
			}
		}
		txtLotBlk.setText("");
		txtStreet.setText("");
		txtBrgy.setText("");
		txtMobileNo.setText("");
		txtTinNo.setText("");
		txtATMNo.setText("");

		lookupCode.setValue("");


	}

	private void GenerateMeetingPlace(model_Assigned_Meeting_Place model){
		pgSelect db = new pgSelect();
		model.clear();

		String SQL = "";

		SQL = "select false, mp_desc, 0, mp_code from mf_tripping_meeting_place order by mp_desc";
		FncSystem.out("Client Schedule", SQL);
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{
			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}

		tblMeetingPlace.packAll();
	}
	
	private void GenerateMeetingPlace_Edit(model_Assigned_Meeting_Place model, String entity_id){
		pgSelect db = new pgSelect();
		model.clear();

		String SQL = "";

		SQL = 	"select false, mp_desc, 0, mp_code \n" + 
				"from mf_tripping_meeting_place \n" + 
				"where mp_code NOT IN (SELECT mp_code FROM rf_tripping_driver_assigned_meetplace WHERE entity_id = '"+entity_id+"')\n" + 
				"\n" + 
				"UNION\n" + 
				"\n" + 
				"\n" + 
				"select false, mp_desc, priority, a.mp_code from mf_tripping_meeting_place a\n" + 
				"inner JOIN rf_tripping_driver_assigned_meetplace b ON a.mp_code = b.mp_code\n" + 
				"WHERE b.entity_id = '"+entity_id+"'\n" + 
				"order by mp_desc";
		FncSystem.out("Client Schedule", SQL);
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{
			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}

		tblMeetingPlace.packAll();
	}
	@Override
	public void mouseClicked(MouseEvent e) {


		if (e.getSource().equals(tblMeetingPlace)) {
			if ((Boolean) modelMeetingPlaceModel.getValueAt(tblMeetingPlace.getSelectedRow(), 0)) {
				tblMeetingPlace.editCellAt(tblMeetingPlace.getSelectedRow(), 2);
				
				/** Listener for boolean can Edit Priority **/
				if (tblMeetingPlace.getSelectedColumn()== 2) {
					Boolean isSelected = (Boolean) modelMeetingPlaceModel.getValueAt(tblMeetingPlace.getSelectedRow(), 0);
					if (isSelected) { 
						System.out.println("Enabled : " +isSelected );
						int row = tblMeetingPlace.getSelectedRow();
						int col = tblMeetingPlace.getSelectedColumn();
						modelMeetingPlaceModel.isCellEditable(row, col);

					}
				}




				/*

				System.out.println("Enabled 1: " +isSelected );
				if (isSelected) { 
					System.out.println("Enabled : " +isSelected );
					modelMeetingPlaceModel.setCellEditable(tblMeetingPlace.getSelectedRow(), 2, true);

				}*/


			} //

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
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource().equals(tblMeetingPlace)) {
			if ( e.getKeyCode()==KeyEvent.VK_UP || 
					e.getKeyCode()==KeyEvent.VK_DOWN || 
					e.getKeyCode()==KeyEvent.VK_ENTER || 
					e.getKeyCode()==KeyEvent.VK_LEFT || 
					e.getKeyCode()==KeyEvent.VK_RIGHT ) {
				if (tblMeetingPlace.getSelectedColumn() == 2) {
					modelMeetingPlaceModel.setEditable(( modelMeetingPlaceModel.getValueAt(tblMeetingPlace.getSelectedRow(),0).equals(true)) );
				} // col 17,18
			} // getKeyCode

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (tblMeetingPlace.columnAtPoint(e.getPoint())== 2	 ){ 
			if (tblMeetingPlace.getValueAt(tblMeetingPlace.rowAtPoint(e.getPoint()),0).equals(true))
				tblMeetingPlace.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			else tblMeetingPlace.setCursor(null);

		}else tblMeetingPlace.setCursor(null);
	}
	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;

		if(txtFirstName.getText().isEmpty() || txtFirstName.getText().equals("")){
			required_fields = required_fields + "First Name \n";
			toSave = false;
		}
		if(txtLastName.getText().isEmpty() || txtLastName.getText().equals("")){
			required_fields = required_fields + "Last Name \n";
			toSave = false;
		}
		if(txtMiddleName.getText().isEmpty() || txtMiddleName.getText().equals("")){
			required_fields = required_fields + "Middle Name \n";
			toSave = false;
		}

		if(txtAlias.getText().isEmpty() || txtAlias.getText().equals("")){
			required_fields = required_fields + "Alias \n";
			toSave = false;
		}
		if(cmbGender.getSelectedIndex() == 0){
			required_fields = required_fields + "Gender \n";
			toSave = false;
		}


		if(txtMobileNo.getText().isEmpty() || txtMobileNo.getText().equals("")){
			required_fields = required_fields + "Mobile No. \n";
			toSave = false;
		}

		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}


	private boolean hasPriority(){
		Boolean toSave = true;
		for (int i = 0; i < modelMeetingPlaceModel.getRowCount(); i++) {
			Boolean isSelected = (Boolean) modelMeetingPlaceModel.getValueAt(i, 0);
			Integer priority = (Integer) modelMeetingPlaceModel.getValueAt(i, 2);

			if (isSelected) {
				if (priority == 0) {
					toSave = false;

				}
			}
		}

		if(toSave == false){
			JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please assign priority meeting place for this driver", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void Save(){

		pgSelect db = new pgSelect();

		String entity_id = lookupCode.getValue().isEmpty() ? "" :lookupCode.getValue().toString().trim();
		String first_name = txtFirstName.getText().trim().replace("'", "''").toUpperCase();
		String last_name = txtLastName.getText().trim().replace("'", "''").toUpperCase();
		String middle_name = txtMiddleName.getText().trim().replace("'", "''").toUpperCase();
		//String entity_name = String.format("%s, %s %s", last_name, first_name, middle_name).toUpperCase();
		String entity_alias = txtAlias.getText().trim().replace("'", "''").toUpperCase();
		String lotblk = txtLotBlk.getText().trim().replace("'", "''").toUpperCase();
		String street = txtStreet.getText().trim().replace("'", "''").toUpperCase();
		String brgy = txtBrgy.getText().trim().replace("'", "''").toUpperCase();

		String mobileNo = txtMobileNo.getText().trim().toString();
		String tinNo = txtTinNo.getText().trim().toString();
		String ATMNo = txtATMNo.getText().trim().toString();

		String status_id = "";
		String gender = "";

		String current_user = UserInfo.EmployeeCode;

		if (cmbGender.getSelectedIndex() == 1) {
			status_id = "A";
		}
		if (cmbGender.getSelectedIndex() == 2) {
			status_id = "I";
		}

		if (cmbStatus.getSelectedIndex() == 0) {
			gender = "M";
		}
		if (cmbStatus.getSelectedIndex() == 1) {
			gender = "F";
		}
		Boolean is_add_edit = false;
		String SQL = "";
		
		ArrayList<Integer> listPriority = new ArrayList<Integer>();
		ArrayList<String> listMpCode = new ArrayList<String>();

		for(int x=0; x < modelMeetingPlaceModel.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelMeetingPlaceModel.getValueAt(x, 0);
			Integer priority = (Integer) modelMeetingPlaceModel.getValueAt(x, 2);
			String mp_code = (String) modelMeetingPlaceModel.getValueAt(x, 3);
			if(isSelected){
				listPriority.add(priority);
				listMpCode.add(String.format("'%s'", mp_code));
			}
		}

		String mpcode = listMpCode.toString().replaceAll("\\[|\\]", "");
		String priority = listPriority.toString().replaceAll("\\[|\\]", "");
		
		if (grpButton.getSelection().getActionCommand().equals("New")) {
			is_add_edit = true;


			SQL = "SELECT sp_save_tripping_driver_entry(\n" + 
					"'"+entity_id+"',---p_entity_id character varying,\n" + 
					"'"+first_name+"',---p_first_name character varying,\n" + 
					"'"+last_name+"',---p_last_name character varying,\n" + 
					"'"+middle_name+"',---p_middle_name character varying,\n" + 
					"'"+entity_alias+"',---p_entity_alias character varying,\n" + 
					"'"+lotblk+"',---p_lot_blk character varying,\n" + 
					"'"+street+"',---p_street character varying,\n" + 
					"'"+brgy+"',---p_brgy character varying,\n" + 
					"'"+status_id+"',---p_status_id character varying,\n" + 
					"'"+gender+"',---p_gender character varying,\n" + 
					"'"+mobileNo+"',---p_mobileno character varying,\n" + 
					"'"+tinNo+"',---p_tin_no character varying,\n" + 
					"'"+ATMNo+"',---p_atm_no character varying,\n" + 
					"'"+current_user+"',---p_current_user character varying,\n" +
					"ARRAY["+ mpcode +"]::VARCHAR[],---character varying[],\n" + 
					"ARRAY["+ priority +"]::INT[],---integer[],\n" + 
					""+is_add_edit+"---is_add_edit Boolean \n"+ 
					")";

		}
		if (grpButton.getSelection().getActionCommand().equals("Edit")) {
			is_add_edit = false;

			SQL = "SELECT sp_save_tripping_driver_entry(\n" + 
					"'"+entity_id+"',---p_entity_id character varying,\n" + 
					"'"+first_name+"',---p_first_name character varying,\n" + 
					"'"+last_name+"',---p_last_name character varying,\n" + 
					"'"+middle_name+"',---p_middle_name character varying,\n" + 
					"'"+entity_alias+"',---p_entity_alias character varying,\n" + 
					"'"+lotblk+"',---p_lot_blk character varying,\n" + 
					"'"+street+"',---p_street character varying,\n" + 
					"'"+brgy+"',---p_brgy character varying,\n" + 
					"'"+status_id+"',---p_status_id character varying,\n" + 
					"'"+gender+"',---p_gender character varying,\n" + 
					"'"+mobileNo+"',---p_mobileno character varying,\n" + 
					"'"+tinNo+"',---p_tin_no character varying,\n" +
					"'"+ATMNo+"',---p_atm_no character varying,\n" + 
					"'"+current_user+"',---p_current_user character varying,\n" + 
					"ARRAY["+ mpcode +"]::VARCHAR[],---character varying[],\n" + 
					"ARRAY["+ priority +"]::INT[],---integer[],\n" + 
					""+is_add_edit+"---is_add_edit Boolean \n"+ 
					")";
		}

		FncSystem.out("QUERY", SQL);
		db.select(SQL);
		
	}

	private Boolean hasSelectedTagged() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelMeetingPlaceModel.getRowCount(); x++){
			listSelected.add((Boolean) modelMeetingPlaceModel.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

}

