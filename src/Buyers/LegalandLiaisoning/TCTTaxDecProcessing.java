package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import Buyers.ClientServicing._OtherRequest;
import Database.pgSelect;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Projects.BiddingandAwarding._NoticeToProceed;
import components._ButtonGroup;
import components._JInternalFrame;
import tablemodel.modelDocStatus;

public class TCTTaxDecProcessing extends _JInternalFrame implements ActionListener, AncestorListener {
	
	/*CHANGES AS OF 2021-09-20
	 * 
	 * 1. DCRF NO. 1486 : CHANGE THE LAYOUT OF ENTRIES DUE TO ADDITIONAL FIELDS FOR PIN NUMBER 2020-10-15
	 * 2. pnlDocProcStatus IS MODIFIED AS PER DCRF NO 1726 - AUTO TAGGING OF SALE(TCOST), IT FEE AND DST MORTGAGE WHEN CAR APPLICATION IS TAG 2021-09-20
	 * 3. txtfield txttctno change to txtOldTaxDecNo for showing old tax dec number (house and lot) 2021-11-02
	 * 4. DCRF NO. 1992 : lookup doctype has been change for controlling doctype for VDC units 2022-03-08
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1696231374073396297L;

	static String title = "TCT/Tax Dec Processing (Mother/Individual)";
	Dimension frameSize = new Dimension(800, 630);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;

	static _JLookup lookupDocType;
	static _JLookup lookupPBL;
	private _JLookup lookupCompany;
	//private _JLookup lookupProject;
	static _JLookup lookupProject;
	//static _JLookup lookupDocNo;

	private JTextField txtPBL;
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtHouseModel;
	static JTextField txtDocNo;
	private JTextField txtMDocNo;
	private JTextField txtDocType;

	private _JLookup lookupHouseModel;

	private JTextField txtFacility;

	//private JXTextField txttctno;/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
	private JXTextField txtOldTaxDecNo;
	private JXTextField txtpageno;
	private _JXFormattedTextField txtvalue;
	private JXTextField txtserialno;
	private JXTextField txtbookno;

	private static JTabbedPane tabCenter;

	private DocStatus pnlDocStat;

	private JButton btnAddUnit;
	static JButton btnPreview;
	static JButton btnNew;
	static JButton btnEdit;
	static JButton btnSearch;
	static JButton btnSave;
	static JButton btnCancel;

	private _ButtonGroup grpNewEdit = new _ButtonGroup();
	
	private JLabel lblClientID;
	private JLabel lblClient;
	private JLabel lblDocType;
	private JLabel lblLotArea;
	
	private Object[] clientDetails;

	private modelDocStatus modelDocStatus;

	private tablemodel.modelDocStatus modelDocStatus_a;
	private Integer rec_id;

	private DocProcStatus pnlProcStat;

	private String co_id;

	private String company;

	private String proj_id;

	private String proj_name;

	private JPanel pnlNorthEast;

	private JXTextField txtpin;
	
	static String strEntityid;

	//private ArrayList<Integer> listDeletedItems;

	public TCTTaxDecProcessing() {
		super(title, true, true, true, true);
		initGUI();

	}

	public TCTTaxDecProcessing(String title) {
		super(title);
		initGUI();
	}

	public TCTTaxDecProcessing(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	/*
	 * 
	 MODIFIED BY JED VICEDO 2020-10-15 DCRF NO. 1486 : CHANGE THE LAYOUT OF ENTRIES DUE TO ADDITIONAL FIELDS FOR PIN NUMBER
	 * 
	 */
	private void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			//pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setBorder(lineBorder);
				pnlNorth.setPreferredSize(new Dimension(788, 230));// XXX
				//pnlNorth.setPreferredSize(new Dimension(788, 240));// XXX
				{
					JPanel pnlUpperNorth = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlUpperNorth, BorderLayout.CENTER);
					{
						JPanel pnlNorthCenter = new JPanel(new BorderLayout(5,5));
						pnlUpperNorth.add(pnlNorthCenter, BorderLayout.CENTER);
						pnlNorthCenter.setBorder(BorderFactory.createLineBorder(Color.RED));
						pnlNorthCenter.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
						{
							JPanel pnlNorthCenterLabels = new JPanel(new GridLayout(7,1,5,5));
							pnlNorthCenter.add(pnlNorthCenterLabels, BorderLayout.WEST);
							{
								JLabel lblContractNo = new JLabel("Doc Number");
								pnlNorthCenterLabels.add(lblContractNo);
							}
							{
								lblDocType = new JLabel("Doc Type");
								pnlNorthCenterLabels.add(lblDocType);
								lblDocType.setEnabled(true);
							}
							{
								JLabel lblCompany = new JLabel("Company");
								pnlNorthCenterLabels.add(lblCompany);
							}
							{
								JLabel lblProject = new JLabel("Project");
								pnlNorthCenterLabels.add(lblProject);
							}
							{
								JLabel lblPBL = new JLabel("PBL");
								pnlNorthCenterLabels.add(lblPBL);
							}
							{
								JLabel lblHouseModel = new JLabel("House Model");
								pnlNorthCenterLabels.add(lblHouseModel);
							}
							{
								JLabel lblFacility = new JLabel("Facility");
								pnlNorthCenterLabels.add(lblFacility);
							}
						}
						{
							JPanel pnlNorthCenterComponents = new JPanel(new GridLayout(7,1,5,5));
							pnlNorthCenter.add(pnlNorthCenterComponents, BorderLayout.CENTER);
							{
								JPanel pnlDocNo = new JPanel(new BorderLayout(5,5));
								pnlNorthCenterComponents.add(pnlDocNo, BorderLayout.CENTER);
								{
									JPanel pnlDocComponent = new JPanel(new BorderLayout(5,5));
									pnlDocNo.add(pnlDocComponent, BorderLayout.WEST);
									pnlDocComponent.setPreferredSize(new Dimension(115,0));
									{
										txtDocNo = new JXTextField("");
										pnlDocComponent.add(txtDocNo);
										txtDocNo.setEditable(false);
										txtDocNo.setHorizontalAlignment(JTextField.CENTER);
										//txtDocNo.setVisible(false);
									}
								}
								{
									JPanel pnlMotherDoc = new JPanel(new BorderLayout(5,5));
									pnlDocNo.add(pnlMotherDoc, BorderLayout.CENTER);
									{
										JPanel pnlMotherDocLabel = new JPanel(new GridLayout(1,2,5,5));
										pnlMotherDoc.add(pnlMotherDocLabel, BorderLayout.WEST);
										{
											pnlMotherDocLabel.add(Box.createHorizontalBox());
										}
										{
											JLabel lblContractNo = new JLabel("Mother DocNo");
											pnlMotherDocLabel.add(lblContractNo);
										}
									}
									{
										JPanel pnlMotherDocComponent = new JPanel(new BorderLayout(5,5));
										pnlMotherDoc.add(pnlMotherDocComponent, BorderLayout.CENTER);
										{
											txtMDocNo = new JXTextField("");
											pnlMotherDocComponent.add(txtMDocNo);
											txtMDocNo.setEditable(false);
											txtMDocNo.setHorizontalAlignment(JTextField.CENTER);
										}
									}
								}
							}
							{
								JPanel pnlDocType = new JPanel(new BorderLayout(5,5));
								pnlNorthCenterComponents.add(pnlDocType, BorderLayout.CENTER);
								{
									JPanel pnlDocTypeWest = new JPanel(new BorderLayout(5,5));
									pnlDocType.add(pnlDocTypeWest, BorderLayout.WEST);
									pnlDocTypeWest.setPreferredSize(new Dimension(115,0));
									{
										lookupDocType = new _JLookup(null, "Doc Type");
										pnlDocTypeWest.add(lookupDocType);
										lookupDocType.setReturnColumn(0);
										lookupDocType.setName("lookupDocType");
										//lookupDocType.setEnabled(false);
										lookupDocType.setEditable(false);
										//lookupDocType.setLookupSQL(DocID());/*commented by jed 2022-03-08 dcrf no. 1992 : to control VDC doc type*/	
										lookupDocType.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {

													txtDocType.setText((String) data[1]);
												}
											}
										});
									}
								}
								{
									JPanel pnlDocTypeCenter = new JPanel(new BorderLayout(5,5));
									pnlDocType.add(pnlDocTypeCenter, BorderLayout.CENTER);
									{
										txtDocType = new JTextField();
										pnlDocTypeCenter.add(txtDocType);
										txtDocType.setEditable(false);
										txtDocType.setHorizontalAlignment(JTextField.LEFT);
									}
								}
							}
							{
								JPanel pnlCompany = new JPanel(new BorderLayout(5,5));
								pnlNorthCenterComponents.add(pnlCompany, BorderLayout.CENTER);
								{
									JPanel pnlCompanyWest = new JPanel(new BorderLayout(5,5));
									pnlCompany.add(pnlCompanyWest, BorderLayout.WEST);
									pnlCompanyWest.setPreferredSize(new Dimension(115,0));
									{
										lookupCompany = new _JLookup(null, "Company");
										pnlCompanyWest.add(lookupCompany);
										lookupCompany.setReturnColumn(0);
										//lookupCompany.setEnabled(false);
										lookupCompany.setEditable(false);
										lookupCompany.setLookupSQL(SQL_COMPANY());
										lookupCompany.setBounds(120, 0, 100, 22);
										lookupCompany.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {
													co_id = (String) data[0];
													txtCompany.setText((String) data[1]);
													lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));
													lookupDocType.setLookupSQL(DocID(co_id));/*added by jed 2022-03-08 dcrf no. 1992 : to control VDC doc type*/
													
													lookupProject.setValue(null);
													txtProject.setText("");
													lookupPBL.setValue(null);
													lookupHouseModel.setValue(null);
													txtHouseModel.setText("");
			
													KEYBOARD_MANAGER.focusNextComponent();
													lookupPBL.setLookupSQL(sqlClients(co_id, lookupProject.getValue()));
												}
											}
										});
									}
								}
								{
									JPanel pnlCompanyCenter = new JPanel(new BorderLayout(5,5));
									pnlCompany.add(pnlCompanyCenter, BorderLayout.CENTER);
									{
										txtCompany = new JTextField();
										pnlCompanyCenter.add(txtCompany);
										txtCompany.setEditable(false);
										txtCompany.setHorizontalAlignment(JTextField.LEFT);
									}
								}
							}
							{
								JPanel pnlProject = new JPanel(new BorderLayout(5,5));
								pnlNorthCenterComponents.add(pnlProject, BorderLayout.CENTER);
								{
									JPanel pnlProjectWest = new JPanel(new BorderLayout(5,5));
									pnlProject.add(pnlProjectWest, BorderLayout.WEST);
									pnlProjectWest.setPreferredSize(new Dimension(115,0));
									{
										lookupProject = new _JLookup(null, "Project", "Please select company.");
										pnlProjectWest.add(lookupProject);
										lookupProject.setReturnColumn(0);
										//lookupProject.setEnabled(false);
										lookupProject.setEditable(false);
										lookupProject.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {

													String proj_id = (String) data[0];
													String project_name = (String) data[1];
													String project_alias = (String) data[2];

													txtProject.setText(String.format("%s (%s)", project_name, project_alias));

													lookupPBL.setValue(null);
													txtPBL.setText("");
													lookupHouseModel.setValue(null);
													txtHouseModel.setText("");

													KEYBOARD_MANAGER.focusNextComponent();
													lookupPBL.setLookupSQL(sqlClients(lookupCompany.getValue(), proj_id));
												}
											}
										});
									}
								}
								{
									JPanel pnlProjectCenter = new JPanel(new BorderLayout(5,5));
									pnlProject.add(pnlProjectCenter, BorderLayout.CENTER);
									{
										txtProject = new JTextField();
										pnlProjectCenter.add(txtProject);
										txtProject.setEditable(false);
										txtProject.setHorizontalAlignment(JTextField.LEFT);
									}
								}
							}
							{
								JPanel pnlPBL = new JPanel(new BorderLayout(5,5));
								pnlNorthCenterComponents.add(pnlPBL, BorderLayout.CENTER);
								{
									JPanel pnlPBLWest = new JPanel(new BorderLayout(5,5));
									pnlPBL.add(pnlPBLWest, BorderLayout.WEST);
									pnlPBLWest.setPreferredSize(new Dimension(115,0));
									{
										lookupPBL = new _JLookup(null, "PBL");
										pnlPBLWest.add(lookupPBL);
										lookupPBL.setReturnColumn(0);
										//lookupPBL.setEnabled(false);
										lookupPBL.setFilterName(true);
										lookupPBL.setEditable(false);
										lookupPBL.setLookupSQL(sqlClients(lookupCompany.getValue(), lookupProject.getValue()));
										lookupPBL.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												FncSystem.out("Display SQL for PBL", lookupPBL.getLookupSQL());
												String co_id = (String) lookupCompany.getValue();
												String proj_id = (String) lookupProject.getValue();
												String pbl_id = (String) data[0];
												
												txtPBL.setText((String) data[1]);
												lblClientID.setText((String) String.format("<html><b>Client ID: %s</b></html>", data[2]));
												lblClient.setText((String) String.format("<html><b>Client Name: %s</b></html>", data[3]));
												lblLotArea.setText((String) String.format("<html><b>Lot Area: %s</b></html>", data[6]));
												lookupHouseModel.setText((String) data[4]);
												txtHouseModel.setText((String) data[5]);
												
												//displayClientDetails(pbl_id, co_id, proj_id);
												DocStatus.btnAdd.setEnabled(true);
												
												/*if (pbl() == true) {
													btnState(true, false, false, false, true);
												} else {
													btnState(true, false, false, true, true);
												}*/
												
												
											}
										});
									}
								}
								{
									JPanel pnlPBLCenter = new JPanel(new BorderLayout(5,5));
									pnlPBL.add(pnlPBLCenter, BorderLayout.CENTER);
									{
										txtPBL = new JTextField();
										pnlPBLCenter.add(txtPBL);
										txtPBL.setEditable(false);
										txtPBL.setHorizontalAlignment(JTextField.LEFT);
									}
								}
							}
							{
								JPanel pnlHouseMod = new JPanel(new BorderLayout(5,5));
								pnlNorthCenterComponents.add(pnlHouseMod, BorderLayout.CENTER);
								{
									JPanel pnlHouseModWest = new JPanel(new BorderLayout(5,5));
									pnlHouseMod.add(pnlHouseModWest, BorderLayout.WEST);
									pnlHouseModWest.setPreferredSize(new Dimension(115,0));
									{
										lookupHouseModel = new _JLookup(null, "House Model");
										pnlHouseModWest.add(lookupHouseModel);
										lookupHouseModel.setLookupSQL(_NoticeToProceed.Contractor());
										lookupHouseModel.setReturnColumn(0);
										//lookupHouseModel.setEnabled(false);
										lookupHouseModel.setEditable(false);
										lookupHouseModel.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {
													txtHouseModel.setText((String) data[1]);
			
													KEYBOARD_MANAGER.focusNextComponent();
												}
											}
										});
									}
								}
								{
									JPanel pnlHouseModCenter = new JPanel(new BorderLayout(5,5));
									pnlHouseMod.add(pnlHouseModCenter, BorderLayout.CENTER);
									{
										txtHouseModel = new JTextField();
										pnlHouseModCenter.add(txtHouseModel);
										txtHouseModel.setEditable(false);
										txtHouseModel.setHorizontalAlignment(JTextField.LEFT);
									}
								}
							}
							{
								txtFacility = new JTextField();
								pnlNorthCenterComponents.add(txtFacility);
								txtFacility.setEditable(false);
							}
						}
					}
					{
						pnlNorthEast = new JPanel(new BorderLayout(5,5));
						pnlUpperNorth.add(pnlNorthEast, BorderLayout.EAST);
						pnlNorthEast.setPreferredSize(new Dimension(250,0));
						pnlNorthEast.setBorder(components.JTBorderFactory.createTitleBorder("Tax Dec"));// XXX
						{
							JPanel pnlNorthEastLabel = new JPanel(new GridLayout(6,1,5,5));
							pnlNorthEast.add(pnlNorthEastLabel, BorderLayout.WEST);
							{
								JLabel lblOldTaxDecNo = new JLabel("Old Tax Dec No.");
								pnlNorthEastLabel.add(lblOldTaxDecNo);
							}

							{
								//JLabel lblDateFinish = new JLabel("<html>Assessed\nValue</html>");
								JLabel lblAssessedValue = new JLabel("Assessed Value");
								pnlNorthEastLabel.add(lblAssessedValue);
							}
							{
								JLabel lblSerial = new JLabel("Serial No.");
								pnlNorthEastLabel.add(lblSerial);
							}
							{
								JLabel lblBook = new JLabel("Book No.");
								pnlNorthEastLabel.add(lblBook);
							}

							{
								JLabel lblPage = new JLabel("Page No.");
								pnlNorthEastLabel.add(lblPage);
							}
							{/*ADDED BY JED 2020-10-15 DCRF NO 1486 : ADDITIONAL FIELDS FOR PIN NUMBER*/
								JLabel lblPin = new JLabel("Pin No.");
								pnlNorthEastLabel.add(lblPin);
							}
						}
						{
							JPanel pnlNorthEastComponents = new JPanel(new GridLayout(6,1,5,5));
							pnlNorthEast.add(pnlNorthEastComponents, BorderLayout.CENTER);
							{
//								txttctno = new JXTextField();
//								pnlNorthEastComponents.add(txttctno);
//								txttctno.setEditable(false);
								
								txtOldTaxDecNo = new JXTextField();
								pnlNorthEastComponents.add(txtOldTaxDecNo);
								txtOldTaxDecNo.setEditable(false);
							}
							{
								txtvalue = new _JXFormattedTextField("0.00");
								pnlNorthEastComponents.add(txtvalue);
								txtvalue.setHorizontalAlignment(JLabel.RIGHT);
								txtvalue.setEditable(false);
							}
							{
								txtserialno = new JXTextField();
								pnlNorthEastComponents.add(txtserialno);
								txtserialno.setEditable(false);
							}
							{
								txtbookno = new JXTextField();
								pnlNorthEastComponents.add(txtbookno);
								txtbookno.setEditable(false);
							}
							{
								txtpageno = new JXTextField();
								pnlNorthEastComponents.add(txtpageno);
								txtpageno.setEditable(false);
							}
							{/*ADDED BY JED 2020-10-15 DCRF NO 1486 : ADDITIONAL FIELDS FOR PIN NUMBER*/
								txtpin = new JXTextField();
								pnlNorthEastComponents.add(txtpin);
								txtpin.setEditable(false);
							}
						}
					}
				}
				{
					JPanel pnlLowerNorth = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlLowerNorth, BorderLayout.SOUTH);
					pnlLowerNorth.setPreferredSize(new Dimension(0,30));
					{
						JPanel pnlClientID = new JPanel(new BorderLayout(5,5));
						pnlLowerNorth.add(pnlClientID, BorderLayout.WEST);
						pnlClientID.setPreferredSize(new Dimension(170,0));
						{
							lblClientID = new JLabel("");
							pnlClientID.add(lblClientID);
						}
					}
					{
						JPanel pnlClient = new JPanel(new BorderLayout(5,5));
						pnlLowerNorth.add(pnlClient, BorderLayout.CENTER);
						{
							lblClient = new JLabel("");
							pnlClient.add(lblClient);
						}
					}
					{
						JPanel pnlLot = new JPanel(new BorderLayout(5,5));
						pnlLowerNorth.add(pnlLot, BorderLayout.EAST);
						pnlLot.setPreferredSize(new Dimension(250,0));
						{
							lblLotArea = new JLabel("");
							pnlLot.add(lblLotArea);
						}
					}	
				}
			}
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				{
					pnlDocStat = new DocStatus();
					tabCenter.addTab("Document Location", null, pnlDocStat, null);

				}
				{
					pnlProcStat = new DocProcStatus();
					tabCenter.addTab("Document Processing Status", null, pnlProcStat, null);

				}
			}
			{
				JPanel pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				// pnlSouth.setBorder(lineBorder);
				pnlSouth.setPreferredSize(new Dimension(500, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnNew = new JButton("New");
					pnlSouth.add(btnNew);
					btnNew.setActionCommand("New");
					btnNew.setMnemonic(KeyEvent.VK_N);
					btnNew.addActionListener(this);
					grpNewEdit.add(btnNew);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
					grpNewEdit.add(btnEdit);

					btnEdit.addPropertyChangeListener("enabled", new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent arg0) {

						}
					});
				}
				{
					btnSearch = new JButton("Search");
					pnlSouth.add(btnSearch);
					btnSearch.setActionCommand("Search");
					btnSearch.setMnemonic(KeyEvent.VK_D);
					btnSearch.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}
		
		tabCenter.addChangeListener(new ChangeListener() {//**ADDED BY JED 2019-01-10***//
			public void stateChanged(ChangeEvent arg0) {
				int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();

				if(selectedTab == 0){
					//pnlDocStat.displayDocStatus(lookupPBL.getValue(), lookupDocType.getValue(), txtDocNo.getText());
				}
				if(selectedTab == 1){
					//pnlProcStat.displayProcStatus(lookupPBL.getValue(), lookupDocType.getValue(), txtDocNo.getText());
				}
			}
		});

		//setComponentsEnabled(false);
		btnState(true, false, true, false, false);
		tabCenter.setSelectedIndex(1);

		this.grabFocus();
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtDocNo, lookupCompany, lookupProject, lookupPBL, lookupHouseModel, txtFacility));

	}// XXX initGUI

	private void refresh(boolean isNew) {
		txtDocNo.setText("");
		lookupDocType.setValue(null);
		txtDocType.setText("");
		txtMDocNo.setText("");
		lookupPBL.setValue(null);
		txtPBL.setText("");
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupHouseModel.setValue(null);
		txtHouseModel.setText("");

		//txttctno.setText("");/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
		txtOldTaxDecNo.setText("");
		txtpageno.setText("");
		txtbookno.setText("");
		txtserialno.setText("");
		txtvalue.setText("0.00");
		txtpin.setText("");/*ADDED BY JED 2020-10-15 DCRF NO 1486 : ADDITIONAL FIELDS FOR PIN NUMBER*/
		DocStatus.btnState(false, false, false, false, false);
		DocProcStatus.btnState(false, false, false, false, false);
		//lookupHouseModel.setEnabled(false);
		txtFacility.setText("");
		
		txtFacility.setEditable(true);
		//txttctno.setEditable(true);/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
		txtOldTaxDecNo.setEditable(true);
		txtvalue.setEditable(true);
		txtserialno.setEditable(true);
		txtbookno.setEditable(true);
		txtpageno.setEditable(true);
		txtpin.setEditable(true);/*ADDED BY JED 2020-10-15 DCRF NO 1486 : ADDITIONAL FIELDS FOR PIN NUMBER*/
		
		pnlDocStat.cancelStatus();
		pnlProcStat.cancelStatus();


		if (isNew) {
		}
	}

	public void btnState(Boolean sNew, Boolean sEdit, Boolean sSearch, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSearch.setEnabled(sSearch);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void setComponentsEnabled(boolean enable) {
		panelLooper(pnlNorth, enable);
		//lookupHouseModel.setEnabled(false);
	}

	public void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else {
				// System.out.printf("Panel: %-50s Component: %s%n",
				// panel.getName(), comp.getName());
				if (comp.getName() != null) {
					if (!comp.getName().equals("lookupDocType")) {
						if (comp.getName().equals("lookupDocType")) {
							comp.setEnabled(lookupDocType.isEnabled());
						} else if (comp.getName().equals("lblDocType")) {
							comp.setEnabled(lblDocType.isEnabled());
						} else {
							comp.setEnabled(!enable);
						}
					}
				} else {
					if (panel instanceof JScrollPane) {
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					} else {
						//System.out.printf("Comp: %s%n", comp.getClass().getName());
						/*if(comp instanceof JTextField){
							((JTextField) comp).setEditable(enable);
						}else{
							comp.setEnabled(enable);
						}*/
						comp.setEnabled(enable);
					}
				}
			}
		}
	}


	private boolean validateSaving() {
		if (lookupCompany.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Company", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupProject.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtDocNo.getText() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Doc No", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupDocType.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Doc Type", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	public void ancestorAdded(AncestorEvent event) {
		lookupDocType.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent arg0) {// XXX action
		String actionCommand = arg0.getActionCommand();

		if (actionCommand.equals("New")) {
			co_id 		= "";	
			company		= "";	
			proj_id		= "";
			proj_name	= "";
			lookupProject.setLookupSQL(SQL_PROJECT(co_id));
			lookupDocType.setLookupSQL(DocID(co_id));/*added by jed 2022-03-08 : to control VDC doc type*/
			
			
			grpNewEdit.setSelectedButton(arg0);
			lookupDocType.setEditable(true);
			lookupCompany.setEditable(true);
			lookupProject.setEditable(true);
			lookupPBL.setEditable(true);
			//lookupHouseModel.setEditable(true);
			txtDocNo.setEditable(true);
			//lookupDocNo.setVisible(false);
			txtMDocNo.setEditable(true);
			rec_id = null;
			refresh(true);
			lookupCompany.setValue(co_id);
			txtCompany.setText(company);
			lookupProject.setValue(proj_id);
			txtProject.setText(proj_name);
			//setComponentsEnabled(true);
		
			pnlState (false, false);
			btnState(false, false, false, true, true);

			/*if (pbl() == true) {
				btnState(true, false, false, false, true);
			} else {
				btnState(true, false, false, true, true);
			}*/
			
		}

		if (actionCommand.equals("Edit")) {
			grpNewEdit.setSelectedButton(arg0);
			
			//---------------------------EDITED by jed 8/22/18--------------------------//
			
			btnState(false, false, false, true, true);
			
			pnlState(false, false);
			txtDocNo.setEditable(true);
			txtMDocNo.setEnabled(true);
			txtMDocNo.setEditable(true);
			lookupDocType.setEditable(true);
			lookupCompany.setEditable(true);
			lookupProject.setEditable(true);
			lookupPBL.setEditable(true);
			
			DocStatus.btnAdd.setEnabled(false);
			DocStatus.btnEdit.setEnabled(false);
			DocStatus.btnDelete.setEnabled(false);
			DocStatus.tblDocStatus.setEnabled(false);
			DocProcStatus.btnAdd.setEnabled(false);	
			DocProcStatus.btnEdit.setEnabled(false);
			DocProcStatus.btnDelete.setEnabled(false);
			DocProcStatus.tblProcStatus.setEnabled(false);
			
			txtFacility.setEditable(true);
			//txttctno.setEditable(true);/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
			txtOldTaxDecNo.setEditable(true);
			txtvalue.setEditable(true);
			txtserialno.setEditable(true);
			txtbookno.setEditable(true);
			txtpageno.setEditable(true);
			txtpin.setEditable(true);/*ADDED BY JED 2020-10-15 DCRF NO 1486 : ADDITIONAL FIELDS FOR PIN NUMBER*/
			
		}

		if (actionCommand.equals("Search")) {
			
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null , "Docs", sqlDocNo(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			
			
			Object [] data = dlg.getReturnDataSet();
			
			if(data != null){
				FncSystem.out("Search", sqlDocNo());
				String doc_no = (String) data[0];
				String doc_type = (String) data[6];
				rec_id = (Integer) data[5];/*ADDED BY JED 2021-03-16 : TO GET THE CORRECT DOC INFORMATION(DOUBLE DOC NO WITH DIFF PBL ID)*/
				
				txtPBL.setText((String) data[1]);
				rec_id = (Integer) data[5];
				System.out.printf("Value of rec_id sa Search: %s%n", rec_id);
				
				displayDocDetails(doc_no, doc_type, rec_id);
				DocStatus.btnAdd.setEnabled(true);
				DocProcStatus.btnAdd.setEnabled(true);
				btnState(true, true, false, false, true);
			}
		}

		if (actionCommand.equals("Save")) {
			/*MODIFIED BY JED DCRF NO. 1486 : ADDITIONAL FIELD FOR SAVING PIN NO*/
			String selection = grpNewEdit.getActionCommand();

			if (validateSaving()) {

				//System.out.printf("Display value of selection: %s%n", selection);
				if (selection.equals("New")) {

					int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) {
						refresh(true);
					}
					String doc_no = (String) txtDocNo.getText();
					String mdoc_no = (String) txtMDocNo.getText();
					String co_id = (String) lookupCompany.getText();
					String proj_id = (String) lookupProject.getText();
					String pbl_id = (String) lookupPBL.getText();
					String model_id = (String) lookupHouseModel.getText();
					String doc_type = (String) lookupDocType.getText();
					String facility = (String) txtFacility.getText();
					//String tctno = (String) txttctno.getText();/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
					String oldTaxDecNo = (String) txtOldTaxDecNo.getText();
					String serial_no = (String) txtserialno.getText();
					String book_no = (String) txtbookno.getText();
					String page_no = (String) txtpageno.getText();
					String emp_code = (String) UserInfo.EmployeeCode;
					BigDecimal amount = new BigDecimal (txtvalue.getText());
					String pin_no = txtpin.getText();

					String strSQL = "SELECT sp_save_tctno_v3('"+co_id+"', '"+proj_id+"', '"+doc_type+"', '"+doc_no+"', '"+mdoc_no+"',"
							+ "'"+proj_id+"', NULLIF('"+pbl_id+"','null'),"
							+ "NULLIF('"+facility+"','null'),"
							+ "NULLIF('"+oldTaxDecNo+"','null'), NULLIF('"+serial_no+"','null'), NULLIF('"+book_no+"','null'), NULLIF('"+page_no+"','null'), "+amount+", NULLIF('"+model_id+"','null'), 'A', '"+emp_code+"', NULLIF('"+pin_no+"','null'));"; 

					
					FncSystem.out("TIMZ: dito", strSQL);
					pgSelect db = new pgSelect();
					db.select(strSQL, null, true);
					
					if(db.isNotNull()){
						if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")){
							System.out.println("DUMAAN NA DITO!!!!!!!!!!!!!!!!!!!");
							
							btnState(false, false, false, true, true);
							txtDocNo.setEditable(true);
							txtMDocNo.setEditable(true);
							lookupDocType.setEditable(true);
							lookupCompany.setEditable(true);
							lookupProject.setEditable(true);
							lookupPBL.setEditable(true);
							pnlState(true, true);
							
						}else{
							rec_id = (Integer) db.getResult()[0][0];
							
							System.out.printf("Display value of rec_id sa Save: %s%n", rec_id);
							
							//JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New TCT No. has been created.", "Save", JOptionPane.INFORMATION_MESSAGE);
							DocStatus.tblDocStatus.setEnabled(true);
							DocProcStatus.tblProcStatus.setEditable(true);
							
							grpNewEdit.clearSelection();
							txtDocNo.setEditable(false);
							txtMDocNo.setEditable(false);
							lookupDocType.setEditable(false);
							lookupCompany.setEditable(false);
							lookupProject.setEditable(false);
							lookupPBL.setEditable(false);
							lookupHouseModel.setEditable(false);
							
							btnState(true, true, true, false, true);
							DocProcStatus.btnState(true, false, false, false, false);
							DocStatus.btnState(true, false, false, false, false);
							
							pnlState(true, true);
						}
					}
					
					txtFacility.setEditable(false);
					txtOldTaxDecNo.setEditable(false);
					txtvalue.setEditable(false);
					txtserialno.setEditable(false);
					txtbookno.setEditable(false);
					txtpageno.setEditable(false);
					txtpin.setEditable(false);
				}

				//--------added by jed 8/23/18 for editting---------------//

				if (selection.equals("Edit")) {
					/*MODIFIED BY JED DCRF NO. 1486 : ADDITIONAL FIELD FOR SAVING PIN NO*/
					int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) {
						refresh(true);
					}

					String doc_no = (String) txtDocNo.getText();
					String mdoc_no = (String) txtMDocNo.getText();
					String co_id = (String) lookupCompany.getText();
					String proj_id = (String) lookupProject.getText();
					String pbl_id = (String) lookupPBL.getText();
					String model_id = (String) lookupHouseModel.getText();
					String doc_type = (String) lookupDocType.getText();
					String facility = (String) txtFacility.getText();
					//String tctno = (String) txttctno.getText();/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
					String oldTaxDecNo = (String) txtOldTaxDecNo.getText();
					String serial_no = (String) txtserialno.getText();
					String book_no = (String) txtbookno.getText();
					String page_no = (String) txtpageno.getText();
					String emp_code = (String) UserInfo.EmployeeCode;
					BigDecimal amount = new BigDecimal (txtvalue.getText());
					String pin_no = txtpin.getText();

					String strSQL = "SELECT sp_save_tctno_update_v3('"+co_id+"', '"+proj_id+"', '"+doc_type+"', '"+doc_no+"', '"+mdoc_no+"',"
							+ "'"+proj_id+"', NULLIF('"+pbl_id+"','null'),"
							+ "NULLIF('"+facility+"','null'),"
							+ "NULLIF('"+oldTaxDecNo+"','null'), NULLIF('"+serial_no+"','null'), NULLIF('"+book_no+"','null'), NULLIF('"+page_no+"','null'), "+amount+", NULLIF('"+model_id+"','null'), 'A', '"+emp_code+"', "+rec_id+", NULLIF('"+pin_no+"','null'));"; 

					FncSystem.out("SQL for updating the table is", strSQL);
					pgSelect db = new pgSelect();
					db.select(strSQL, null, true);


					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "TCT No. has been edited.", "Save", JOptionPane.INFORMATION_MESSAGE);
					DocStatus.tblDocStatus.setEnabled(true);
					DocProcStatus.tblProcStatus.setEnabled(true);
					
					grpNewEdit.clearSelection();
					txtDocNo.setEditable(false);
					txtMDocNo.setEditable(false);
					lookupDocType.setEditable(false);
					lookupCompany.setEditable(false);
					lookupProject.setEditable(false);
					lookupPBL.setEditable(false);
					lookupHouseModel.setEditable(false);
					
					txtFacility.setEditable(false);
					txtOldTaxDecNo.setEditable(false);
					txtvalue.setEditable(false);
					txtserialno.setEditable(false);
					txtbookno.setEditable(false);
					txtpageno.setEditable(false);
					txtpin.setEditable(false);
					
					displayDocDetails(doc_no, doc_type, rec_id);
					btnState(true, true, true, false, true);
					DocProcStatus.btnState(true, false, false, false, false);
					DocStatus.btnState(true, false, false, false, false);
					
					pnlState(true, true);
				}

			}

		}

		if (actionCommand.equals("Cancel")) {
			String selection = grpNewEdit.getActionCommand();   //edited by jed 8/22/18
			txtDocNo.setEditable(false);
			txtMDocNo.setEditable(false);
			lookupDocType.setEditable(false);
			lookupCompany.setEditable(false);
			lookupProject.setEditable(false);
			lookupPBL.setEditable(false);
			lookupHouseModel.setEditable(false);
		
			lblClient.setText("");
			lblClientID.setText("");
			lblLotArea.setText("");			
			//---------------------------added by jed 8/22/18--------------------------//
			
			lookupDocType.setValue(null);
			refresh(true);
			pnlState (true, true);
			txtFacility.setEditable(false);
			//txttctno.setEditable(false);/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
			txtOldTaxDecNo.setEditable(false);
			txtvalue.setEditable(false);
			txtserialno.setEditable(false);
			txtbookno.setEditable(false);
			txtpageno.setEditable(false);
			txtpin.setEditable(false);/*ADDED BY JED 2020-10-15 DCRF NO 1486 : ADDITIONAL FIELDS FOR PIN NUMBER*/
			rec_id = null;
			System.out.printf("Value of rec id sa cancel: %s%n", rec_id);
			DocStatus.tblDocStatus.setEnabled(true);
			DocProcStatus.tblProcStatus.setEnabled(true);

			btnState(true, false, true, false, false);
			
			grpNewEdit.clearSelection();
		}
	}
	private static String DocID(String co_id) {/*edited by jed 2022-03-08 : to control VDC doc type*/
		
		if(co_id.equals("01")) {
			String sql = "select doc_id as \"Doc ID\", trim(doc_desc) as \"Doc Desc\" from mf_system_doc where tct_taxdec_doc = true and status_id = 'A' and doc_id not in ('64', '92', '181') and server_id is null order by doc_desc;";
			System.out.printf("SQL: %s%n", sql);
			return sql;
		}else {
			String sql = "select doc_id as \"Doc ID\", trim(doc_desc) as \"Doc Desc\" from mf_system_doc where tct_taxdec_doc = true and status_id = 'A' and doc_id not in ('228', '229', '230') and server_id is null order by doc_desc;";
			System.out.printf("SQL: %s%n", sql);
			return sql;
		}
		
		
	}
	private static String sqlClients(String co_id, String proj_id) {
		
		String sql = "SELECT a.pbl_id as \"PBL\", FORMAT('%s-%s-%s', REPLACE(a.phase, '-', ''), a.block, a.lot) as \"Description\", (case when a.pbl_id = c.oth_pbl_id then c.entity_id else b.entity_id end) as \"Client ID\", \n" + 
				"(case when a.pbl_id = c.oth_pbl_id then e.entity_name else d.entity_name end) as \"Client\",(case when a.pbl_id = c.oth_pbl_id then a.model_id else b.model_id end) as \"Model ID\", \n" + 
				"(CASE WHEN b.entity_id = '1185209653' THEN 'LOT ONLY - ' ELSE (case when a.pbl_id = c.oth_pbl_id then get_model_name_v2(a.proj_id, a.model_id) else get_model_name_v2(a.proj_id, a.model_id) end) END) as \"Model Desc\", \n" + 
				"format('%s-%s-%s'::text, btrim(a.phase::text),btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\", a.lotarea AS \"Lot Area\" \n" + 
				"FROM mf_unit_info a \n" + 
				"left JOIN rf_sold_unit b ON a.proj_id = b.projcode AND a.pbl_id = b.pbl_id  and b.status_id = 'A'\n" + 
				"left JOIN hs_sold_other_lots c ON a.proj_id = c.proj_id and a.pbl_id = c.oth_pbl_id and c.status_id = 'A'\n" + 
				"LEFT JOIN rf_entity d on d.entity_id = b.entity_id \n" + 
				" left join rf_entity e on e.entity_id = c.entity_id  \n" + 
				"LEFT JOIN mf_project f on f.proj_id = (CASE WHEN b.entity_id = '1185209653' THEN '019' ELSE a.proj_id END)\n" + 
				"WHERE  (CASE WHEN NULLIF('"+co_id+"', 'null') IS NULL THEN TRUE ELSE f.co_id = '"+co_id+"' END)\n" + 
				"AND (CASE WHEN b.entity_id = '1185209653' THEN TRUE ELSE (CASE WHEN NULLIF('"+proj_id+"', 'null') IS NULL THEN TRUE ELSE a.proj_id = '"+proj_id+"' END) END)\n" + 
				"and (CASE WHEN  b.entity_id = '1185209653' THEN TRUE ELSE (CASE WHEN b.server_id ='itsreal' then TRUE ELSE (select exists (select * \n" + 
				"	      from rf_marketing_scheme_main \n" + 
				"	      where sub_proj_id = a.sub_proj_id \n" + 
				"	      and status_id = 'P')) end)END) \n" + 
				"UNION \n" + 
				"select a.pbl_id as \"PBL\", FORMAT('%s-%s-%s', REPLACE(a.phase, '-', ''), a.block, a.lot) as \"Description\", '' as \"Client ID\", \n" + 
				"'' as \"Client\",a.model_id as \"Model ID\", \n" + 
				"get_model_name_v2(a.proj_id, a.model_id) as \"Model Desc\", \n" + 
				"format('%s-%s-%s'::text, btrim(a.phase::text),btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\", a.lotarea AS \"Lot Area\" \n" + 
				"from mf_unit_info_pending a\n" + 
				"LEFT JOIN mf_project b on b.proj_id = a.proj_id \n" + 
				"where not exists (select *\n" + 
				"		  from mf_unit_info \n" + 
				"		  where sub_proj_id = a.sub_proj_id \n" + 
				"		  and phase = a.phase \n" + 
				"		  and block = a.block \n" + 
				"		  and lot = a.lot)\n" + 
				"AND (CASE WHEN NULLIF('"+co_id+"', 'null') IS NULL THEN TRUE ELSE b.co_id = '"+co_id+"' END)\n" + 
				"AND (CASE WHEN NULLIF('"+proj_id+"', 'null') IS NULL THEN TRUE ELSE a.proj_id = '"+proj_id+"' END) \n" + 
				"and a.status_id = 'A' \n" + 
				"ORDER BY \"Client\",\"PBL\" ,\"Ph-Bl-Lt\";\n";

		FncSystem.out("PBL CLIENT", sql);
		return sql;
	}
	
	
	private static String sqlDocNo() {
		String sql = "select trim(a.doc_no) as \"Doc No\", \n" + 
				"(CASE WHEN c.description is null then FORMAT('%s-%s-%s', e.phase, e.block, e.lot) else \n" + 
				"FORMAT('%s-%s-%s', c.phase, c.block, c.lot) end) as \"Unit\", \n" + 
				"(case when d.entity_name is null then g.entity_name else d.entity_name end) as \"Client Name\", \n" + 
				"a.facilities as \"Facilities\", \n" + 
				"b.doc_desc as \"Doc Desc\", \n" + 
				"a.rec_id as \"Rec ID\" , \n" + 
				"trim(a.doc_type) as \"Doc Type\"\n" + 
				"from rf_tct_taxdec_monitoring_hd a\n" + 
				"left join mf_system_doc b on trim(a.doc_type) = b.doc_id and b.status_id ='A'--and coalesce(a.server_id, '') = coalesce(b.server_id, '')\n" + 
				"left join mf_unit_info c on a.proj_id = c.proj_id and a.pbl_id = c.pbl_id -- and c.status_id ='A' and coalesce(a.server_id, '') = coalesce(c.server_id, '')\n" + 
				"LEft join rf_entity d on d.entity_id = a.entity_id --and coalesce(a.server_id, '') = coalesce(d.server_id, '')\n" + 
				"left join mf_unit_info_pending e on e.proj_id = a.proj_id and e.pbl_id = a.pbl_id --and e.status_id = 'A'\n" + 
				"left join hs_sold_other_lots f on f.oth_pbl_id = a.pbl_id and a.proj_id = f.proj_id--and coalesce(a.server_id, '') = coalesce(f.server_id, '')\n" + 
				"left join rf_entity g on g.entity_id = f.entity_id --and coalesce(a.server_id, '') = coalesce(g.server_id, '')\n" + 
				"where a.status_id = 'A'\n" +
				//"AND CASE WHEN "+UserInfo.EmployeeCode.equals("900965")+" THEN a.server_id IS NOT NULL ELSE TRUE END \n"+
				//"and a.co_id = '02' \n"+
				"order by d.entity_name;";
		
		FncSystem.out("DOC NO", sql);
		
		return sql;
	}
	
	private String generateTAG(Object text) {
		return String.format("%s", text);
	}
	private void displayClientDetails(String pbl_id, String co_id, String proj_id) {
		clientDetails = displayClientInformation(pbl_id, co_id, proj_id);
		if(clientDetails != null){
			
			txtDocNo.setText(generateTAG((String) clientDetails[0])); 
			lookupDocType.setText((String) clientDetails[2]);
			txtDocType.setText(generateTAG((String) clientDetails[3])); 
			txtMDocNo.setText(generateTAG((String) clientDetails[1])); 
			txtFacility.setText(generateTAG((String) clientDetails[4])); 
			//txttctno.setText(generateTAG((String) clientDetails[5]));/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
			txtOldTaxDecNo.setText(generateTAG((String) clientDetails[5]));
			txtserialno.setText(generateTAG((String) clientDetails[6]));
			txtbookno.setText(generateTAG((String) clientDetails[7]));
			txtpageno.setText(generateTAG((String) clientDetails[8]));
			txtvalue.setValue(generateTAG((Double) clientDetails[9]));
			
			pnlDocStat.displayDocStatus(lookupPBL.getValue(), lookupDocType.getValue(), txtDocNo.getText());
		}
	}
	private void displayDocDetails(String doc_no, String doc_type, Integer rec_id) {
		/*MODIFIED BY JED DCRF NO 1486 : ADDITIONAL FIELD FOR DISPLAYING PIN NO*/
		/*MODIFIED BY JED 2021-03-16 : ADDITIONAL FILTER DUE TO DOUBLE DOC NO WITH DIFF. PBL*/
		clientDetails = displayDocInformation(doc_no, doc_type, rec_id);
		if(clientDetails != null){
			
			strEntityid = (String) clientDetails[11];
			
			txtDocNo.setText(doc_no);
			lookupPBL.setValue(generateTAG((String) clientDetails[0])); 
			lookupDocType.setValue((String) clientDetails[2]);
			txtDocType.setText(generateTAG((String) clientDetails[3])); 
			txtMDocNo.setText(generateTAG((String) clientDetails[1])); 
			txtFacility.setText(generateTAG((String) clientDetails[4])); 
			//txttctno.setText(generateTAG((String) clientDetails[5]));/*COMMENTED BY JED 2021-11-02: CHANGE COMPONENT NAME*/
			txtOldTaxDecNo.setText(generateTAG((String) clientDetails[5]));
			txtserialno.setText(generateTAG((String) clientDetails[6]));
			txtbookno.setText(generateTAG((String) clientDetails[7]));
			txtpageno.setText(generateTAG((String) clientDetails[8]));
			txtvalue.setValue(generateTAG((Double) clientDetails[9]));
			lblClientID.setText((String) String.format("<html><b>Client ID: %s</b></html>", clientDetails[11]));
			lblClient.setText((String) String.format("<html><b>Client Name: %s</b></html>", clientDetails[10]));
			lblLotArea.setText((String) String.format("<html><b>Lot Area: %s</b></html>", clientDetails[18]));
			lookupHouseModel.setValue((String) clientDetails[12]);
			txtHouseModel.setText((String) clientDetails[13]);
			lookupCompany.setValue((String) clientDetails[14]);
			txtCompany.setText((String) clientDetails[15]);
			lookupProject.setValue((String) clientDetails[16]);
			txtProject.setText((String) clientDetails[17]);
			txtpin.setText((String) clientDetails[19]);
			
			pnlDocStat.displayDocStatus(lookupPBL.getValue(), lookupDocType.getValue(), txtDocNo.getText());
			pnlProcStat.displayProcStatus(lookupPBL.getValue(), lookupDocType.getValue(), txtDocNo.getText());
		}
	}

	private static Object[] displayClientInformation(String pbl_id, String co_id, String proj_id) {
		String SQL = "select trim(a.doc_no), a.mother_doc_no, trim(a.doc_type), b.doc_desc, a.facilities, a.tct_no, a.serial_no, a.book_no, a.page_no, a.assessed_value \n"
				+ "from rf_tct_taxdec_monitoring_hd a \n"
				+ "left join mf_system_doc b on trim(a.doc_type) = b.doc_id and coalesce(a.server_id, '') = coalesce(b.server_id, '')\n" 
				+ "where a.pbl_id = '"+pbl_id+"' and a.co_id = '"+co_id+"' and a.proj_id = '"+proj_id+"' and a.status_id = 'A' and a.server_id = b.server_id;";

		FncSystem.out("ITO PO", SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	private static Object[] displayDocInformation(String doc_no, String doc_type, Integer rec_id) {
		String SQL = "select \n" + 
				"a.pbl_id, \n" + 
				"a.mother_doc_no, \n" + 
				"trim(a.doc_type), \n" + 
				"b.doc_desc, \n" + 
				"a.facilities, \n" + 
				"--a.tct_no,\n" + 
				"coalesce(i.old_doc_no,''),\n" + 
				"a.serial_no, \n" + 
				"a.book_no,\n" + 
				"a.page_no, \n" + 
				"a.assessed_value, \n" + 
				"(case when h.entity_name is null then g.entity_name else h.entity_name end),\n" + 
				"(case when a.entity_id is null then g.entity_id else a.entity_id end), \n" + 
				"c.model_id, \n" + 
				"j.model_desc,\n" + 
				"a.co_id, \n" + 
				"d.company_name, \n" + 
				"a.proj_id, \n" + 
				"get_project_name(a.proj_id), \n" + 
				"(case when a.doc_type = '73' then a.lotarea else c.lotarea end),\n" + 
				"a.pin_no\n" + 
				"from rf_tct_taxdec_monitoring_hd a\n" + 
				"left join mf_system_doc b on trim(a.doc_type) = b.doc_id --and coalesce(a.server_id, '') = coalesce(b.server_id, '')\n" + 
				"left join mf_unit_info c on a.pbl_id = c.pbl_id and c.proj_id = a.projcode  --and coalesce(a.server_id, '') = coalesce(c.server_id, '')\n" + 
				"left join mf_company d on a.co_id = d.co_id\n" + 
				"left join rf_sold_unit e on a.pbl_id = e.pbl_id  and e.projcode = a.projcode and e.entity_id = a.entity_id -- and e.status_id = 'A' and coalesce(a.server_id, '') = coalesce(e.server_id, '')\n" + 
				"left join hs_sold_other_lots f on f.oth_pbl_id = a.pbl_id and f.proj_id = a.projcode --and coalesce(a.server_id, '') = coalesce(f.server_id, '')\n" + 
				"left join rf_entity g on g.entity_id = f.entity_id and g.status_id ='A' \n" + 
				"left join rf_entity h on h.entity_id = a.entity_id and h.status_id ='A' \n" + 
				"left join (select * from tmp_old_taxdec_no where status_id = 'A') i on a.rec_id = i.old_rec_id\n" + 
				"left join mf_house_model j on j.model_id = c.model_id\n" +
				"where trim(a.doc_no) = '"+doc_no+"' and a.status_id = 'A' and trim(a.doc_type) = '"+doc_type+"' and a.rec_id = "+rec_id+"";

		FncSystem.out("displayDocInformation DocsInfo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	private boolean pbl() {
		
		boolean x = false;
		String pblid = "";
		
		String strSQL = "select pbl_id from rf_tct_taxdec_monitoring_hd where pbl_id and server_id is null= '"+lookupPBL.getValue()+"'";
		
		FncSystem.out("PBL ID", strSQL);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if(db.isNotNull()){
			pblid = (String) db.getResult()[0][0];
		}
		
		if (pblid.equals(null) || pblid.isEmpty()) {
			x=false;
			System.out.println(x);
			} else {x=true;}
		
		return x;
	}
	
	public static void pnlState (Boolean LOC, Boolean STAT){
		
		tabCenter.setEnabledAt(0, LOC);
		tabCenter.setEnabledAt(1, STAT);
		
	}
}