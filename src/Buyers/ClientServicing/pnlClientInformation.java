package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Dialogs.dlg_AddContactInfo;
import Dialogs.dlg_MiddleInitial;
import Dialogs.dlg_MotherMaidenName;
import FormattedTextField._JXFormattedTextField;
import Functions.FncDate;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import tablemodel.modelCI_EntityTypes;

public class pnlClientInformation extends JXPanel implements ActionListener {

	/**
	 * @author JLF
	 */
	private static final long serialVersionUID = -7827388528126074417L;
    
	private JPanel pnlIndividual;
	private JPanel pnlLeftIndividual;
	private JPanel pnlPersonalInfo;
	private JPanel pnlPersonalInfoLabel;
	private JPanel pnlPersonalInfoComponent;
	private JLabel lblClientType;
	private JComboBox cmbClientType;
	public static JTabbedPane tabEntityKind;
	private JLabel lblEntityKind;
	private JTextField txtEntityKind;
	private JLabel lblFirstName;
	private JTextField txtFirstName;
	private JLabel lblMiddleName;
	private JTextField txtMiddleName;
	private JLabel lblLastName;
	private JTextField txtLastName;
	private JLabel lblStatus;
	private JComboBox cmbStatus;
	private JButton btnMotherMaidenName;
	private JPanel pnlMotherMaidenName;
	private JPanel pnlMotherMaidenNameLabel;
	private JPanel pnlMotherMaidenNameComponent;

	private JTextField txtMotherMaidenFirstName;
	private JTextField txtMotherMaidenMiddleName;
	private JTextField txtMotherMaidenLastName;
    
	private JPanel pnlRightIndividual;
	private JPanel pnlBusinessInfo;
	private JPanel pnlBusinessInfoLabel;
	private JLabel lblTIN_No;
	private JPanel pnlBusinessInfoComponent;
	private JLabel lblNatureOfBusiness;
	private JComboBox cmbNatureOfBusiness;
	private JLabel lblBusinessClass;
	private JComboBox cmbBusinessClass;
	private JLabel lblBusinessTelNo;
	private JLabel lblOCW;
	private JTextField txtBusinessTelNo;
	private JCheckBox chkLocal;
	private JCheckBox chkPOP;
	private JTextField txtTIN;
	private JLabel lblMIDNo;
	private JTextField txtMIDNo;
	private JLabel lblGSISNo;
	private JTextField txtGSISNo;
	private JLabel lblSSSNo;
	private JTextField txtSSSNo;

	private JComboBox cmbCivilStatus;
	private JLabel lblCivilStatus;
	private JComboBox cmbGender;
	private JLabel lblGender;
	private _JLookup lookupCitizenship;
	private JTextField txtCitizenship;
	private JLabel lblCitizenship;
	private _JLookup lookupPlaceofBirth;
	private JTextField txtBirthPlace;

	private JPanel pnlBirthPlaceType;
	private JCheckBox chkCity;
	private JCheckBox chkMunicipality;
	private JCheckBox chkOtherCountry;
	private JLabel lblPlaceOfBirth;
	private _JDateChooser dateDateOfBirth;
	private JLabel lblDateOfBirth;
	private JPanel pnlOther;
	private JPanel pnlOtherLabel;
	private JPanel pnlOtherComponent;

	private JTextField txtCTCNo;
	private _JDateChooser dateDateIssued;
	private JTextField txtPlaceIssued;
	
	private _JDateChooser dateDateTINEncoded;
	private JCheckBox chkVATRegistered;

	private JPanel pnlContactInfo;
	private JPanel pnlContactInfoLabel;
	private JPanel pnlContactInfoComponent;
	private JLabel lblTelephoneNo;
	private _JXTextField txtTelephoneNo;
	private JButton btnAddContactInfo;
	private JLabel lblCellphoneNo;
	private _JXTextField txtCellphoneNo;
	private JLabel lblFaxNo;
	private _JXTextField txtFaxNo;
	private JLabel lblEmailAddress;
	private _JXTextField txtEmailAddress;
	private JLabel lblMailingAddress;
	private _JXTextField txtMailingAddress;
	private JLabel lblFacebookAcct;
	private _JXTextField txtFacebookAcct;
	
	
	//private JTextField txtGSISNo;
	private JTextField txtHDMFNo;
	private JTextField txtHLIDNo;
	//private JLabel lblPagibigContribution;
	//private _JXFormattedTextField txtPagibigContribution;
	private JLabel lblNoOfDependents;
	private JTextField txtNoOfDependents;
	
	private JPanel pnlCorporation;
	private JPanel pnlCorpMainInfo;
	private JSplitPane splitCorpInfo;
	private JPanel pnlCorpInfoLeft;
	
	private JPanel pnlCorpInfoLabel;
	private JLabel lblCorpType;
	private JLabel lblCorporationName;
	private JLabel lblCorpContactPerson;
	private JLabel lblCorpPosition;
	private JLabel lblCorpTIN;
	private JLabel lblCorpBusinessNature;
	private JLabel lblCorpBusinessClass;
	
	private JPanel pnlCorpInfoComponents;
	private JPanel pnlCorpTypes;
	private JPanel pnlCorpInfoRight;
	private modelCI_EntityTypes modelEntityTypes;
	private JScrollPane scrollEntityTypes;
	private _JTableMain tblEntityTypes;
	private JList rowHeaderEntityTypes;
	
	private JComboBox cmbCorpType;
	private _JXTextField txtCorpType;
	private _JXTextField txtCorpPosition;
	private _JXTextField txtCorpContactPerson;
	private _JXTextField txtCorpAlias;
	private _JXTextField txtCorpName;
	private _JXTextField txtCorpTIN;
	private JCheckBox chkCorpVAT;
	private JComboBox cmbCorpBusinessNature;
	private JComboBox cmbCorpBusinessClass;
	
	private JPanel pnlCorpContact;
	private JPanel pnlCorpContactLabel;
	private JLabel lblCorpBusinessTelNo;
	private JLabel lblCorpMobileNo;
	private JLabel lblCorpFaxNo;
	private JLabel lblCorpEmail;
	private JLabel lblCorpFacebookAcct;
	
	private JPanel pnlCorpContactComponents;
	private _JXTextField txtCorpBusinessTelNo;
	private _JXTextField txtCorpMobileNo;
	private _JXTextField txtCorpFaxNo;
	private _JXTextField txtCorpEmail;
	private _JXTextField txtCorpFacebookAcct;
	private JButton btnAddCorpContactInfo;
	
	private dlg_AddContactInfo addContact;
	private dlg_MotherMaidenName addMotherMaidenName;
	
	private ClientInformation ci;
	private String entity_id;
	
	public pnlClientInformation(ClientInformation ci) {
		this.ci = ci;
		initGUI();
	}

	public pnlClientInformation(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlClientInformation(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlClientInformation(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	private void initGUI() {
		//this.setLayout(new SpringLayout())
		this.setLayout(new GridLayout(1, 2));
		//this.registerKeyboardAction(this, "Add Contact", KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			tabEntityKind = new JTabbedPane();
			this.add(tabEntityKind, BorderLayout.CENTER);
			tabEntityKind.setTabPlacement(JTabbedPane.BOTTOM);
			{
				pnlIndividual = new JPanel(new GridLayout(1, 2, 3, 3));
				tabEntityKind.addTab("Individual", null, pnlIndividual, null);
				{
					pnlLeftIndividual = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlIndividual.add(pnlLeftIndividual);
					{
						pnlPersonalInfo = new JPanel(new BorderLayout(3, 3));
						pnlLeftIndividual.add(pnlPersonalInfo);
						//pnlPersonalInfo.setLayout(new SpringLayout());
						pnlPersonalInfo.setBorder(JTBorderFactory.createTitleBorder("Personal"));
						{
							pnlPersonalInfoLabel = new JPanel(new GridLayout(6, 1, 3, 3));
							pnlPersonalInfo.add(pnlPersonalInfoLabel, BorderLayout.WEST);
							/*{
								lblClientType = new JLabel("Client Type", JLabel.TRAILING);
								pnlPersonalInfoLabel.add(lblClientType);
							}*/
							{
								lblEntityKind = new JLabel("Entity Kind", JLabel.TRAILING);
								pnlPersonalInfoLabel.add(lblEntityKind);
							}
							{
								lblLastName = new JLabel("Last Name", JLabel.TRAILING);
								pnlPersonalInfoLabel.add(lblLastName);
							}
							{
								lblFirstName = new JLabel("First Name", JLabel.TRAILING);
								pnlPersonalInfoLabel.add(lblFirstName);
							}
							{
								lblMiddleName = new JLabel("Middle Name", JLabel.TRAILING);
								pnlPersonalInfoLabel.add(lblMiddleName);
							}
							
							{
								lblStatus = new JLabel("Status", JLabel.TRAILING);
								pnlPersonalInfoLabel.add(lblStatus);
							}
							{
								pnlPersonalInfoLabel.add(Box.createHorizontalBox());
							}
						}
						{
							pnlPersonalInfoComponent = new JPanel(new GridLayout(6, 1, 3, 3));
							pnlPersonalInfo.add(pnlPersonalInfoComponent, BorderLayout.CENTER);
							/*{
								cmbClientType = new JComboBox(new String[] { "Individual", "Corporate" });
								//cmbClientType = new JComboBox(Locale.getISOLanguages());
								pnlPersonalInfoComponent.add(cmbClientType);
								cmbClientType.setEnabled(false);
								cmbClientType.setSelectedItem(null);
								cmbClientType.addItemListener(new ItemListener() {
									
									public void itemStateChanged(ItemEvent e) {
										int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();
										
										if(selected_index == 0){
											System.out.println("Client is Individual");
											//pnlLeft.add(pnlOther);
										}
										
										if(selected_index == 1){
											System.out.println("Client is Corporation");
											
											pnlLeft.remove(pnlOther);
											pnlLeft.repaint();
										}
									}
								});
							}*/
							{
								txtEntityKind = new JTextField();
								pnlPersonalInfoComponent.add(txtEntityKind);
							}
							{
								txtLastName = new JTextField(10);
								pnlPersonalInfoComponent.add(txtLastName);
							}
							{
								txtFirstName = new JTextField(10);
								pnlPersonalInfoComponent.add(txtFirstName);
							}
							{
								txtMiddleName = new JTextField(10);
								pnlPersonalInfoComponent.add(txtMiddleName);
							}
							
							{
								cmbStatus = new JComboBox(new String[] { "Active", "Inactive" });
								pnlPersonalInfoComponent.add(cmbStatus);
								cmbStatus.setEnabled(false);
								cmbStatus.setSelectedItem(null);
							}
							{
								btnMotherMaidenName = new JButton("Mother's Maiden Name");
								pnlPersonalInfoComponent.add(btnMotherMaidenName);
								btnMotherMaidenName.setActionCommand(btnMotherMaidenName.getText());
								btnMotherMaidenName.addActionListener(this);
								btnMotherMaidenName.setEnabled(false);
							}
							/*{
								txtMotherMaidenFirstName = new JTextField();
								pnlPersonalInfoComponent.add(txtMotherMaidenFirstName);
							}
							{
								txtMotherMaidenMiddleName = new JTextField();
								pnlPersonalInfoComponent.add(txtMotherMaidenMiddleName);
							}
							{
								txtMotherMaidenLastName = new JTextField();
								pnlPersonalInfoComponent.add(txtMotherMaidenLastName);
							}*/
						}
					}
					{
						pnlOther = new JPanel(new BorderLayout(3, 3));
						pnlLeftIndividual.add(pnlOther);
						//pnlOther.setLayout(new SpringLayout());
						pnlOther.setBorder(JTBorderFactory.createTitleBorder("Other"));
						{
							pnlOtherLabel = new JPanel(new GridLayout(7, 1, 3, 3));
							pnlOther.add(pnlOtherLabel, BorderLayout.WEST);
							{
								lblGender = new JLabel("Gender", JLabel.TRAILING);
								pnlOtherLabel.add(lblGender);
							}
							{
								lblCivilStatus = new JLabel("Civil Status", JLabel.TRAILING);
								pnlOtherLabel.add(lblCivilStatus);
							}
							{
								lblDateOfBirth = new JLabel("Date of Birth", JLabel.TRAILING);
								pnlOtherLabel.add(lblDateOfBirth);
							}
							/*{
								pnlOtherLabel.add(Box.createHorizontalBox());
							}*/
							{
								JPanel pnlCity = new JPanel(new BorderLayout(3, 3));
								pnlOtherLabel.add(pnlCity, BorderLayout.EAST);
								{
									chkCity = new JCheckBox("City");
									pnlCity.add(chkCity, BorderLayout.EAST);
									chkCity.setEnabled(false);
									chkCity.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent arg0) {
											boolean isSelected = (arg0.getStateChange() == ItemEvent.SELECTED);
											if(isSelected){
												lookupPlaceofBirth.setLookupSQL(sqlCity());
												chkMunicipality.setSelected(false);
												chkOtherCountry.setSelected(false);
											}
										}
									});
								}
							}
							
							{
								lblPlaceOfBirth = new JLabel("Place of Birth", JLabel.TRAILING);
								pnlOtherLabel.add(lblPlaceOfBirth);
							}
							{
								lblCitizenship = new JLabel("Citizenship", JLabel.TRAILING);
								pnlOtherLabel.add(lblCitizenship);
							}
							{
								lblNoOfDependents = new JLabel("No. of Dependents", JLabel.TRAILING);
								pnlOtherLabel.add(lblNoOfDependents);
							}
						}
						{
							pnlOtherComponent = new JPanel(new GridLayout(7, 1, 3, 3));
							pnlOther.add(pnlOtherComponent, BorderLayout.CENTER);
							{
								cmbGender = new JComboBox(new String[] { "Male", "Female" });
								pnlOtherComponent.add(cmbGender);
								cmbGender.setEnabled(false);
								cmbGender.setSelectedItem(null);
							}
							{
								cmbCivilStatus = new JComboBox(getCivilStatus());
								pnlOtherComponent.add(cmbCivilStatus);
								cmbCivilStatus.setEnabled(true);
							}
							{
								dateDateOfBirth = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlOtherComponent.add(dateDateOfBirth);
								//dateDateOfBirth.setEnabled(false);
								dateDateOfBirth.setEditable(false);
								dateDateOfBirth.getCalendarButton().setEnabled(false);
								dateDateOfBirth.addDateListener(new DateListener() {

									@Override
									public void datePerformed(DateEvent event) {
										validateBirthdate(event);
									}
								});
								//dateDateOfBirth.setMinSelectableDate(min);
							}
							{
								pnlBirthPlaceType = new JPanel();
								pnlOtherComponent.add(pnlBirthPlaceType);

								/*{
									chkCity = new JCheckBox("City");
									pnlBirthPlaceType.add(chkCity);
									chkCity.setEnabled(false);
									chkCity.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent arg0) {
											boolean isSelected = (arg0.getStateChange() == ItemEvent.SELECTED);
											if(isSelected){
												lookupPlaceofBirth.setLookupSQL(sqlCity());
												chkMunicipality.setSelected(false);
												chkOtherCountry.setSelected(false);
											}
										}
									});
								}*/
								{
									chkMunicipality = new JCheckBox("Municipality");
									pnlBirthPlaceType.add(chkMunicipality);
									chkMunicipality.setEnabled(false);
									chkMunicipality.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent arg0) {
											boolean isSelected = (arg0.getStateChange() == ItemEvent.SELECTED);
											if(isSelected){
												lookupPlaceofBirth.setLookupSQL(sqlMunicipality());
												chkCity.setSelected(false);
												chkOtherCountry.setSelected(false);
											}
										}
									});
								}
								{
									chkOtherCountry = new JCheckBox("Other Country");
									pnlBirthPlaceType.add(chkOtherCountry);
									chkOtherCountry.setEnabled(false);
									chkOtherCountry.addItemListener(new ItemListener() {
										
										public void itemStateChanged(ItemEvent e) {
											boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
											if(isSelected){
												lookupPlaceofBirth.setLookupSQL(sqlCountry());
												chkCity.setSelected(false);
												chkMunicipality.setSelected(false);
											}
										}
									});
								}
							}
							{
								JPanel pnlbirthplace = new JPanel(new BorderLayout(3, 3));
								pnlOtherComponent.add(pnlbirthplace);
								{
									lookupPlaceofBirth = new _JLookup(null, "Place of Birth", 0, "Please Select whether city or municipality");
									pnlbirthplace.add(lookupPlaceofBirth, BorderLayout.WEST);
									lookupPlaceofBirth.setPreferredSize(new Dimension(40, 0));
									lookupPlaceofBirth.setFilterName(true);
									lookupPlaceofBirth.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												FncSystem.out("Display SQL For Place of Birth", lookupPlaceofBirth.getLookupSQL());
												
												String birthplace = (String) data[1];
												txtBirthPlace.setText(birthplace);
											}
										}
									});
								}
								{
									txtBirthPlace = new JTextField();
									pnlbirthplace.add(txtBirthPlace);
								}
							}
							{
								JPanel pnlCitizenship = new JPanel(new BorderLayout(3, 3));
								pnlOtherComponent.add(pnlCitizenship);
								{
									lookupCitizenship = new _JLookup(null, "Citizenship", 0);
									pnlCitizenship.add(lookupCitizenship, BorderLayout.WEST);
									lookupCitizenship.setPreferredSize(new Dimension(40, 0));
									lookupCitizenship.setLookupSQL(sqlCitizenship());
									lookupCitizenship.setFilterName(true);
									lookupCitizenship.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												String citizenship = (String) data[1];
												txtCitizenship.setText(citizenship);
											}
										}
									});
								}
								{
									txtCitizenship = new JTextField();
									pnlCitizenship.add(txtCitizenship);
								}
							}
							{
								txtNoOfDependents = new JTextField();
								pnlOtherComponent.add(txtNoOfDependents);
							}
						}
					}
					//SpringUtilities.makeCompactGrid(pnlLeft, 2, 1, 5, 5, 0, 0, false);
				}
				{
					pnlRightIndividual = new JPanel(new SpringLayout());
					pnlIndividual.add(pnlRightIndividual);
					{
						pnlBusinessInfo = new JPanel(new BorderLayout(3, 3));
						pnlRightIndividual.add(pnlBusinessInfo);
						//pnlBusinessInfo.setLayout(new SpringLayout());
						pnlBusinessInfo.setBorder(JTBorderFactory.createTitleBorder("Business"));
						{
							pnlBusinessInfoLabel = new JPanel(new GridLayout(9, 1, 3, 3));
							pnlBusinessInfo.add(pnlBusinessInfoLabel, BorderLayout.WEST);
							{
								lblNatureOfBusiness = new JLabel("*Nature of Business", JLabel.TRAILING);
								pnlBusinessInfoLabel.add(lblNatureOfBusiness);
							}
							{
								lblBusinessClass = new JLabel("*Business Class", JLabel.TRAILING);
								pnlBusinessInfoLabel.add(lblBusinessClass);
							}
							{
								lblBusinessTelNo = new JLabel("Business Tel. No.", JLabel.TRAILING);
								pnlBusinessInfoLabel.add(lblBusinessTelNo);
							}
							{
								//pnlBusinessInfoLabel.add(Box.createHorizontalBox());
								lblOCW = new JLabel("(For OCW Only)", JLabel.TRAILING);
								pnlBusinessInfoLabel.add(lblOCW);
							}
							/*{
								lblPagibigContribution = new JLabel("Pagibig Contribution", JLabel.TRAILING);
								pnlBusinessInfoLabel.add(lblPagibigContribution);
							}*/
							{
								pnlBusinessInfoLabel.add(Box.createHorizontalBox());
							}
							{
								lblTIN_No = new JLabel("TIN No");
								pnlBusinessInfoLabel.add(lblTIN_No);
							}
							{
								lblMIDNo = new JLabel("MID No");
								pnlBusinessInfoLabel.add(lblMIDNo);
							}
							{
								lblSSSNo = new JLabel("SSS No");
								pnlBusinessInfoLabel.add(lblSSSNo);
							}
							{
								lblGSISNo = new JLabel("GSIS No");
								pnlBusinessInfoLabel.add(lblGSISNo);
							}
						}
						{
							pnlBusinessInfoComponent = new JPanel(new GridLayout(9, 1, 3, 3));
							pnlBusinessInfo.add(pnlBusinessInfoComponent, BorderLayout.CENTER);
							{
								cmbNatureOfBusiness = new JComboBox(getBusinessNature());
								pnlBusinessInfoComponent.add(cmbNatureOfBusiness);
								cmbNatureOfBusiness.setEnabled(false);
							}
							{
								cmbBusinessClass = new JComboBox(getBusinessClass());
								pnlBusinessInfoComponent.add(cmbBusinessClass);
								cmbBusinessClass.setEnabled(false);
								cmbBusinessClass.addItemListener(new ItemListener() {

									public void itemStateChanged(ItemEvent e) {
										String business_class = ((String) cmbBusinessClass.getSelectedItem()).split("-")[1].trim();

										if(business_class != null && cmbBusinessClass.isEnabled()){
											if(business_class.equals("03")){
												chkLocal.setEnabled(true);
												chkPOP.setEnabled(true);
												//txtPagibigContribution.setEditable(false);
												//txtPagibigContribution.setEditable(true);
											}else if(business_class.equals("09") || business_class.equals("04") || business_class.equals("07") || business_class.equals("08")){
												chkLocal.setEnabled(false);//ADDED BY LESTER REFER TO DCRF 145
												chkPOP.setEnabled(false);
												chkLocal.setSelected(false);
												chkPOP.setSelected(false);
												//txtPagibigContribution.setEditable(true);
												
											}else{
												chkLocal.setEnabled(false);
												chkPOP.setEnabled(false);
												chkLocal.setSelected(false);
												chkPOP.setSelected(false);
												/*txtPagibigContribution.setValue(new BigDecimal("0.00"));
												txtPagibigContribution.setEditable(false);*/
											}
											
										}
									}
								});
							}
							
							{
								txtBusinessTelNo = new JTextField(5);
								pnlBusinessInfoComponent.add(txtBusinessTelNo);
							}
							{
								JPanel pnlLocalPOP = new JPanel(new BorderLayout(3, 3));
								pnlBusinessInfoComponent.add(pnlLocalPOP);
								{
									chkLocal = new JCheckBox("LOCAL");
									pnlLocalPOP.add(chkLocal, BorderLayout.WEST);
									chkLocal.setEnabled(false);
									chkLocal.addItemListener(new ItemListener() {

										@Override
										public void itemStateChanged(ItemEvent arg0) {
											Boolean isSelected = (arg0.getStateChange() == ItemEvent.SELECTED);

											if(ci.lookupClient.isEditable() == false){
												System.out.printf("Display if Client is Editable: %s%n", ci.lookupClient.isEditable());
												if(isSelected){
													/*txtPagibigContribution.setEditable(false);
													txtPagibigContribution.setValue(new BigDecimal("0.00"));*/
													chkPOP.setSelected(false);
													System.out.println("Pagibig Contri is Editable sa Local");
												}else{
													/*txtPagibigContribution.setEditable(false);
													txtPagibigContribution.setValue(new BigDecimal("0.00"));*/
												}
											}
										}
									});
								}
								{
									chkPOP = new JCheckBox("POP");
									pnlLocalPOP.add(chkPOP, BorderLayout.EAST);
									chkPOP.setEnabled(false);
									chkPOP.addItemListener(new ItemListener() {

										@Override
										public void itemStateChanged(ItemEvent e) {
											Boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);

											if(ci.lookupClient.isEditable() == false){
												
												if(isSelected){
													//txtPagibigContribution.setEditable(true);
													chkLocal.setSelected(false);
													System.out.println("Pagibig Contri is Editable sa POP");
												}else{
													/*txtPagibigContribution.setEditable(false);
													txtPagibigContribution.setValue(new BigDecimal("0.00"));*/
												}
											}
										}
									});
								}
							}
							/*{
								txtPagibigContribution = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlBusinessInfoComponent.add(txtPagibigContribution);
								txtPagibigContribution.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtPagibigContribution.setText("0.00");
							}*/
							{
								chkVATRegistered = new JCheckBox("VAT Registered");
								pnlBusinessInfoComponent.add(chkVATRegistered);
								chkVATRegistered.setEnabled(false);
							}
							{
								txtTIN = new JTextField();
								pnlBusinessInfoComponent.add(txtTIN);
							}
							{
								txtMIDNo = new JTextField();
								pnlBusinessInfoComponent.add(txtMIDNo);
							}
							{
								txtSSSNo = new JTextField();
								pnlBusinessInfoComponent.add(txtSSSNo);
							}
							{
								txtGSISNo = new JTextField();
								pnlBusinessInfoComponent.add(txtGSISNo);
							}
						}
					}
					{
						pnlContactInfo = new JPanel(new BorderLayout(3, 3));
						pnlRightIndividual.add(pnlContactInfo);
						//pnlContactInfo.setLayout(new SpringLayout());
						pnlContactInfo.setBorder(JTBorderFactory.createTitleBorder("Contacts"));
						{
							pnlContactInfoLabel = new JPanel(new GridLayout(6, 1, 3, 3));
							pnlContactInfo.add(pnlContactInfoLabel, BorderLayout.WEST);
							{
								lblTelephoneNo = new JLabel("Telephone Nos.", JLabel.TRAILING);
								pnlContactInfoLabel.add(lblTelephoneNo);
							}
							{
								lblCellphoneNo = new JLabel("Cellphone Nos.", JLabel.TRAILING);
								pnlContactInfoLabel.add(lblCellphoneNo);
							}
							{
								lblFaxNo = new JLabel("Fax No.", JLabel.TRAILING);
								pnlContactInfoLabel.add(lblFaxNo);
							}
							{
								lblEmailAddress = new JLabel("Email Address", JLabel.TRAILING);
								pnlContactInfoLabel.add(lblEmailAddress);
							}
							/*{
								lblMailingAddress = new JLabel("Mailing Address", JLabel.TRAILING);
								pnlContactInfoLabel.add(lblMailingAddress);
							}*/
							{
								lblFacebookAcct = new JLabel("Facebook Account", JLabel.TRAILING);
								pnlContactInfoLabel.add(lblFacebookAcct);
							}
							{
								pnlContactInfoLabel.add(Box.createHorizontalBox());
							}
						}
						{
							pnlContactInfoComponent = new JPanel(new GridLayout(6, 1, 3, 3));
							pnlContactInfo.add(pnlContactInfoComponent);
							{
								txtTelephoneNo = new _JXTextField();
								pnlContactInfoComponent.add(txtTelephoneNo, BorderLayout.CENTER);
							}
							{
								txtCellphoneNo = new _JXTextField();
								pnlContactInfoComponent.add(txtCellphoneNo, BorderLayout.CENTER);
							}
							{
								txtFaxNo = new _JXTextField();
								pnlContactInfoComponent.add(txtFaxNo, BorderLayout.CENTER);
							}
							{
								txtEmailAddress = new _JXTextField();
								pnlContactInfoComponent.add(txtEmailAddress, BorderLayout.CENTER);
							}
							/*{
								txtMailingAddress = new _JXTextField();
								pnlContactInfoComponent.add(txtMailingAddress, BorderLayout.CENTER);
							}*/
							{
								txtFacebookAcct = new _JXTextField();
								pnlContactInfoComponent.add(txtFacebookAcct, BorderLayout.CENTER);
							}
							{
								btnAddContactInfo = new JButton("Add Contact Info");
								pnlContactInfoComponent.add(btnAddContactInfo);
								btnAddContactInfo.setActionCommand("Add Contact Info");
								btnAddContactInfo.addActionListener(this);
								btnAddContactInfo.setEnabled(false);
							}
						}
						//SpringUtilities.makeCompactGrid(pnlContactInfo, 6, 2, 5, 5, 5, 5, false);
					}
					SpringUtilities.makeCompactGrid(pnlRightIndividual, 2, 1, 0, 5, 0, 0, false);
				}
			}
			
			{
				pnlCorporation = new JPanel(new GridLayout(2, 1, 3, 3));
				tabEntityKind.addTab("Corporation", null, pnlCorporation, null);
				{
					pnlCorpMainInfo = new JPanel(new BorderLayout(3, 3));
					pnlCorporation.add(pnlCorpMainInfo);
					pnlCorpMainInfo.setBorder(JTBorderFactory.createTitleBorder("Main Info"));
					{
						splitCorpInfo = new JSplitPane();
						pnlCorpMainInfo.add(splitCorpInfo, BorderLayout.CENTER);
						splitCorpInfo.setOneTouchExpandable(true);
						splitCorpInfo.setResizeWeight(.3d);
						{
							JPanel pnlCorpInfoLeft = new JPanel(new BorderLayout(3, 3));
							splitCorpInfo.add(pnlCorpInfoLeft, JSplitPane.LEFT);
							{
								pnlCorpInfoLabel = new JPanel(new GridLayout(7, 1, 5, 5));
								pnlCorpInfoLeft.add(pnlCorpInfoLabel, BorderLayout.WEST);
								/*{
									lblCorpType = new JLabel("Corporation Type");
									pnlCorpInfoLabel.add(lblCorpType);
								}*/
								{
									lblCorporationName = new JLabel("*Corporation Name");
									pnlCorpInfoLabel.add(lblCorporationName);
								}
								{
									JLabel lblCorpAlias = new JLabel("Corp Alias");
									pnlCorpInfoLabel.add(lblCorpAlias);
								}
								{
									lblCorpContactPerson = new JLabel("Authorized Person");
									pnlCorpInfoLabel.add(lblCorpContactPerson);
								}
								{
									lblCorpPosition = new JLabel("Position");
									pnlCorpInfoLabel.add(lblCorpPosition);
								}
								{
									lblCorpTIN = new JLabel("TIN");
									pnlCorpInfoLabel.add(lblCorpTIN);
								}
								{
									lblCorpBusinessNature = new JLabel("Business Nature");
									pnlCorpInfoLabel.add(lblCorpBusinessNature);
								}
								{
									lblCorpBusinessClass = new JLabel("Business Class");
									pnlCorpInfoLabel.add(lblCorpBusinessClass);
								}
							}
							{
								pnlCorpInfoComponents = new JPanel(new GridLayout(7, 1, 5, 5));
								pnlCorpInfoLeft.add(pnlCorpInfoComponents, BorderLayout.CENTER);
								{
									txtCorpName = new _JXTextField();
									pnlCorpInfoComponents.add(txtCorpName);
								}
								{
									txtCorpAlias = new _JXTextField();
									pnlCorpInfoComponents.add(txtCorpAlias);
								}
								{
									txtCorpContactPerson = new _JXTextField();
									pnlCorpInfoComponents.add(txtCorpContactPerson);
								}
								{
									txtCorpPosition = new _JXTextField();
									pnlCorpInfoComponents.add(txtCorpPosition);
								}
								{
									JPanel pnlCorpTIN = new JPanel(new BorderLayout(3, 3));
									pnlCorpInfoComponents.add(pnlCorpTIN);
									{
										txtCorpTIN = new _JXTextField();
										pnlCorpTIN.add(txtCorpTIN, BorderLayout.WEST);
										txtCorpTIN.setPreferredSize(new Dimension(150, 0));
									}
									{
										chkCorpVAT = new JCheckBox("VAT Registered");
										pnlCorpTIN.add(chkCorpVAT, BorderLayout.EAST);
										chkCorpVAT.setEnabled(false);
									}
								}
								{
									JPanel pnlCorpBusinessNature = new JPanel(new BorderLayout(3, 3));
									pnlCorpInfoComponents.add(pnlCorpBusinessNature);
									{
										cmbCorpBusinessNature = new JComboBox(getBusinessNature());
										pnlCorpBusinessNature.add(cmbCorpBusinessNature, BorderLayout.WEST);
										cmbCorpBusinessNature.setPreferredSize(new Dimension(150, 0));
										cmbCorpBusinessNature.setEnabled(false);
									}
								}
								{
									JPanel pnlCorpBusinessClass = new JPanel(new BorderLayout(3, 3));
									pnlCorpInfoComponents.add(pnlCorpBusinessClass);
									{
										cmbCorpBusinessClass = new JComboBox(getBusinessClass());
										pnlCorpBusinessClass.add(cmbCorpBusinessClass, BorderLayout.WEST);
										cmbCorpBusinessClass.setPreferredSize(new Dimension(150, 0));
										cmbCorpBusinessClass.setEnabled(false);
									}
								}
							}
						}
						{
							pnlCorpTypes = new JPanel(new BorderLayout(3, 3));
							splitCorpInfo.add(pnlCorpTypes, JSplitPane.RIGHT);
							
							{
								scrollEntityTypes = new JScrollPane();
								pnlCorpTypes.add(scrollEntityTypes, BorderLayout.CENTER);
								scrollEntityTypes.setBorder(JTBorderFactory.createTitleBorder("Entity Types"));
								scrollEntityTypes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								{
									modelEntityTypes = new modelCI_EntityTypes();

									tblEntityTypes = new _JTableMain(modelEntityTypes);
									scrollEntityTypes.setViewportView(tblEntityTypes);
									tblEntityTypes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tblEntityTypes.hideColumns("WTAX ID", "WTAX Description", "WTAX Rate");
									tblEntityTypes.setSortable(false);
									
									//Process after row add in tables
									modelEntityTypes.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {

											((DefaultListModel)rowHeaderEntityTypes.getModel()).addElement(modelEntityTypes.getRowCount());

											if(modelEntityTypes.getRowCount() == 0){
												rowHeaderEntityTypes.setModel(new DefaultListModel());
											}
										}
									});
								}
								
								{
									rowHeaderEntityTypes = tblEntityTypes.getRowHeader();
									rowHeaderEntityTypes.setModel(new DefaultListModel());
									scrollEntityTypes.setRowHeaderView(rowHeaderEntityTypes);
									scrollEntityTypes.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
							
							/*{
								cmbCorpType = new JComboBox(_HoldingAndReservation.getCorporationType());
								pnlCorpType.add(cmbCorpType, BorderLayout.WEST);
								cmbCorpType.setPreferredSize(new Dimension(250, 0));
							}
							{
								JPanel pnlCorpEntityType = new JPanel(new BorderLayout(5, 5));
								pnlCorpType.add(pnlCorpEntityType, BorderLayout.WEST);
								pnlCorpEntityType.setPreferredSize(new Dimension(250, 0));
								{
									txtCorpType = new _JXTextField();
									pnlCorpEntityType.add(txtCorpType, BorderLayout.CENTER);
									txtCorpType.setPreferredSize(new Dimension(300, 0));
								}
								{
									JButton btnCorpEntityType = new JButton(getSearchIcon());
									pnlCorpEntityType.add(btnCorpEntityType, BorderLayout.EAST);
									btnCorpEntityType.setActionCommand("Add Corp Type");
									btnCorpEntityType.addActionListener(this);
								}
							}
							
							{
								JLabel lblCorpAlias = new JLabel("Corp Alias", JLabel.TRAILING);
								pnlCorpType.add(lblCorpAlias);
							}
							{
								txtCorpAlias = new _JXTextField();
								pnlCorpType.add(txtCorpAlias, BorderLayout.EAST);
								txtCorpAlias.setPreferredSize(new Dimension(150, 0));
							}*/
						}
					}
				}
				{
					pnlCorpContact = new JPanel(new BorderLayout(5, 5));
					pnlCorporation.add(pnlCorpContact);
					pnlCorpContact.setBorder(JTBorderFactory.createTitleBorder("Contact Details"));
					{
						pnlCorpContactLabel = new JPanel(new GridLayout(6, 1, 5, 5));
						pnlCorpContact.add(pnlCorpContactLabel, BorderLayout.WEST);
						{
							lblCorpBusinessTelNo = new JLabel("Business Tel. No");
							pnlCorpContactLabel.add(lblCorpBusinessTelNo);
						}
						{
							lblCorpMobileNo = new JLabel("Cellphone No.");
							pnlCorpContactLabel.add(lblCorpMobileNo);
						}
						{
							lblCorpFaxNo = new JLabel("Fax No.");
							pnlCorpContactLabel.add(lblCorpFaxNo);
						}
						{
							lblCorpEmail = new JLabel("Email");
							pnlCorpContactLabel.add(lblCorpEmail);
						}
						{
							lblCorpFacebookAcct = new JLabel("Facebook Acct");
							pnlCorpContactLabel.add(lblCorpFacebookAcct);
						}
						{
							pnlCorpContactLabel.add(Box.createHorizontalBox());
						}
					}
					{
						pnlCorpContactComponents = new JPanel(new GridLayout(6, 1, 5, 5));
						pnlCorpContact.add(pnlCorpContactComponents, BorderLayout.CENTER);
						{
							txtCorpBusinessTelNo = new _JXTextField();
							pnlCorpContactComponents.add(txtCorpBusinessTelNo);
						}
						{
							txtCorpMobileNo = new _JXTextField();
							pnlCorpContactComponents.add(txtCorpMobileNo);
						}
						{
							txtCorpFaxNo = new _JXTextField();
							pnlCorpContactComponents.add(txtCorpFaxNo);
						}
						{
							txtCorpEmail = new _JXTextField();
							pnlCorpContactComponents.add(txtCorpEmail);
						}
						{
							txtCorpFacebookAcct = new _JXTextField();
							pnlCorpContactComponents.add(txtCorpFacebookAcct);
						}
						{
							JPanel pnlAddCorpContact = new JPanel(new BorderLayout(5, 5));
							pnlCorpContactComponents.add(pnlAddCorpContact);
							{
								btnAddCorpContactInfo = new JButton("Add Corp. Contact Info");
								pnlAddCorpContact.add(btnAddCorpContactInfo, BorderLayout.EAST);
								btnAddCorpContactInfo.setPreferredSize(new Dimension(250, 0));
								btnAddCorpContactInfo.setActionCommand("Add Corp Contact Info");
								btnAddCorpContactInfo.addActionListener(this);
								btnAddCorpContactInfo.setEnabled(false);
							}
						}
					}
				}
			}
			
			tabEntityKind.addChangeListener(new ChangeListener() {
				
				public void stateChanged(ChangeEvent e) {
					int selected_tab = ((JTabbedPane) e.getSource()).getSelectedIndex();
					
					if(selected_tab == 0){
						
					}
					
					if(selected_tab == 1){
						
					}
					
				}
			});
		}
		
		
		{
			pnlMotherMaidenName = new JPanel(new BorderLayout(3, 3));
			pnlMotherMaidenName.setPreferredSize(new Dimension(400, 200));
			{
				pnlMotherMaidenNameLabel = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlMotherMaidenName.add(pnlMotherMaidenNameLabel);
				{
					JLabel lblMotherMaidenFName = new JLabel("First Name");
					pnlMotherMaidenNameLabel.add(lblMotherMaidenFName);
				}
				{
					JLabel lblMotherMaidenMName = new JLabel("Middlle Name");
					pnlMotherMaidenNameLabel.add(lblMotherMaidenMName);
				}
				{
					JLabel lblMotherMaidenLName = new JLabel("Last Name");
					pnlMotherMaidenNameLabel.add(lblMotherMaidenLName);
				}
			}
			{
				pnlMotherMaidenNameComponent = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlMotherMaidenName.add(pnlMotherMaidenNameComponent);
				{
					txtMotherMaidenFirstName = new JTextField();
					pnlMotherMaidenNameComponent.add(txtMotherMaidenFirstName);
				}
				{
					txtMotherMaidenMiddleName = new JTextField();
					pnlMotherMaidenNameComponent.add(txtMotherMaidenMiddleName);
				}
				{
					txtMotherMaidenLastName = new JTextField();
					pnlMotherMaidenNameComponent.add(txtMotherMaidenLastName);
				}
			}
		}

		ci.btnState(false, true, false, false, false);
		disableComponents();
		//setClientInformationEnabled(false);

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbClientType, txtFirstName, txtMiddleName, txtLastName, cmbStatus, cmbGender,
				cmbCivilStatus, dateDateOfBirth, lookupPlaceofBirth, lookupCitizenship, txtTelephoneNo, txtCellphoneNo, txtFaxNo, txtEmailAddress,
				txtMailingAddress, cmbNatureOfBusiness, cmbBusinessClass, txtBusinessTelNo, txtCTCNo, dateDateIssued, txtPlaceIssued, txtTIN,
				dateDateTINEncoded, chkVATRegistered, txtSSSNo, txtGSISNo, txtHDMFNo, txtHLIDNo/*, txtPagibigContribution*/, txtNoOfDependents));
	}//XXX END OF INIT GUI

	private Object[] getCivilStatus() {//ARRAYLIST FOR THE CIVIL STATUS
		ArrayList<Object> civilstatus = new ArrayList<Object>();

		pgSelect db = new pgSelect();
		db.select("select civil_status_desc from mf_civil_status group by civil_status_desc, sort_order order by sort_order;");
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				civilstatus.add(db.getResult()[x][0]);
			}
		}
		return civilstatus.toArray();
	}

	private Object[] getBusinessNature() {//ARRAYLIST FOR THE BUSINESS NATURE
		ArrayList<Object> civilstatus = new ArrayList<Object>();

		pgSelect db = new pgSelect();
		String SQL = "select FORMAT('%s - %s', TRIM(initcap(nob_desc)), TRIM(nob_code)) from mf_nature_business where status_id = 'A' order by nob_desc";
		db.select(SQL);
		
		FncSystem.out("Display SQL FOr Business Nature", SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				civilstatus.add(db.getResult()[x][0]);
			}
		}
		
		return civilstatus.toArray();
	}

	private Object[] getBusinessClass() {//ARRAYLIST FOR THE BUSINESS CLASS
		ArrayList<Object> civilstatus = new ArrayList<Object>();

		pgSelect db = new pgSelect();
		db.select("SELECT FORMAT('%s - %s', TRIM(class_name), TRIM(class_id)) from mf_business_class where status_id = 'A' ORDER BY TRIM(class_name)");
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				civilstatus.add(db.getResult()[x][0]);
			}
		}
		return civilstatus.toArray();
	}

	private String sqlCitizenship(){//SQL FOR THE CITIZENSHIP
		return "Select trim(citizenship_code) as \"ID\", trim(citizenship_desc) as \"Description\" from mf_citizen where status_id = 'A'";
	}

	private String sqlCity(){//SQL FOR THE CITY
		return "select trim(a.city_id) as \"ID\", \n" + 
				"trim(a.city_name) as \"City Name\", \n" + 
				"TRIM(b.province_name) as \"Province\" \n" + 
				"from mf_city a\n" + 
				"LEFT JOIN mf_province b on b.province_id = a.province_id\n" + 
				"where a.status_id ='A' \n" + 
				"order by a.city_name;\n";
	}

	private String sqlMunicipality(){//SQL FOR THE MUNICIPALITY
		return "Select trim(a.municipality_id) as \"ID\", \n" + 
				"trim(a.municipality_name) as \"Municipality\",\n" + 
				"trim(b.province_name) as \"Province\"\n" + 
				"from mf_municipality a\n" + 
				"LEFT JOIN mf_province b on b.province_id = a.province_id \n" + 
				"where a.status_id ='A' \n" + 
				"order by trim(a.municipality_name);";
	}
	
	private String sqlCountry(){
		return "SELECT TRIM(country_id) as \"ID\", \n" + 
			   "TRIM(country_name) as \"Country\"\n" + 
			   "FROM mf_country\n" + 
			   "WHERE status_id = 'A'\n" + 
			   "ORDER BY trim(country_name);";
	}

	public void setClientInformationEnabled(boolean enable) {
		for(Component panel : this.getComponents()){
			if(panel instanceof JPanel){
				for(Component comp : ((JPanel) panel).getComponents()){
					//if(comp instanceof JLabel == false){
					comp.setEnabled(enable);
					//}
				}
			}
		}
	}

	public void displayDetails(String entity_id) {
		this.entity_id = entity_id;
		
		displayClientInfo(entity_id);
		displayEntityTypes(entity_id, modelEntityTypes);
		scrollEntityTypes.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblEntityTypes.getRowCount())));
		tblEntityTypes.packAll();
		
		btnAddContactInfo.setText("Add/Edit Contact Info");
		/*displayPersonal(entity_id);
		displayEntityKind(entity_id);
		displayOther(entity_id);
		displayContacts(entity_id);
		displayBusiness(entity_id);
		displayBIR(entity_id);
		displayGovernment(entity_id);*/
		
	}
	
	public void displayClientInfo(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM view_ci_client_info('"+entity_id+"')";
		db.select(SQL);
		
		FncSystem.out("Display Client Information", SQL);
		
		if(db.isNotNull()){
			
			String entity_name = (String) db.getResult()[0][0];
			String entity_alias = (String) db.getResult()[0][1];
			String first_name = (String) db.getResult()[0][2];
			String middle_name = (String) db.getResult()[0][3];
			String last_name = (String) db.getResult()[0][4];
			String entity_kind = (String) db.getResult()[0][5];
			String status_id = (String) db.getResult()[0][6];
			String entity_type = (String) db.getResult()[0][7];
			String gender = (String) db.getResult()[0][8];
			String civil_status = (String) db.getResult()[0][9];
			Date date_or_birth = (Date) db.getResult()[0][10];
			String birthplace_id = (String) db.getResult()[0][11];
			String birthplace = (String) db.getResult()[0][12];
			String citizenship_id = (String) db.getResult()[0][13];
			String citizenship = (String) db.getResult()[0][14];
			String dependent_no = (String) db.getResult()[0][15];
			String tel_no = (String) db.getResult()[0][16];
			String mobile_no = (String) db.getResult()[0][17];
			String fax_no = (String) db.getResult()[0][18];
			String email = (String) db.getResult()[0][19];
			String contact_person = (String) db.getResult()[0][20];
			String position = (String) db.getResult()[0][21];
			String tin_no = (String) db.getResult()[0][22];
			String business_nature = (String) db.getResult()[0][23];
			String business_class = (String) db.getResult()[0][24];
			String business_no = (String) db.getResult()[0][25];
			Boolean local = (Boolean) db.getResult()[0][26];
			Boolean pop = (Boolean) db.getResult()[0][27];
			Boolean vat_registered = (Boolean) db.getResult()[0][28];
			BigDecimal pagibig_contri = (BigDecimal) db.getResult()[0][29];
			String facebook_acct = (String) db.getResult()[0][30];
			String mid_no = (String) db.getResult()[0][31];
			String sss_no = (String) db.getResult()[0][32];
			String gsis_no = (String) db.getResult()[0][33];
			
			clearIndividualCI();
			
			System.out.println("****************************************************************");
			System.out.println("civil_status: " + civil_status);
			System.out.println("entity_kind: " + entity_kind.toString());
			System.out.println("db.getResult()[0][9]" + (String) db.getResult()[0][9]); 
			
			if(entity_kind.equals("I")){
				tabEntityKind.setSelectedIndex(0);
				
				if(status_id.equals("A")){
					cmbStatus.setSelectedIndex(0);
				}
				
				if(status_id.equals("I")){
					cmbStatus.setSelectedIndex(1);;
				}
				
				txtFirstName.setText(first_name);
				txtMiddleName.setText(middle_name);
				txtLastName.setText(last_name);
				txtEntityKind.setText(entity_type);
				cmbGender.setSelectedItem(gender);
				cmbCivilStatus.setSelectedItem(civil_status);
				dateDateOfBirth.setDate(date_or_birth);
				
				if(birthplace_id.equals("") == false){
					
					/*if(birthplace_id.startsWith("5")){
						chkMunicipality.setSelected(true);
					}
					if(birthplace_id.startsWith("0") || birthplace_id.startsWith("1")){
						chkCity.setSelected(true);
					}*/
					
					if(isBirthplaceCity(birthplace_id)){
						chkCity.setSelected(true);
					}
					if(isBirthplaceMunicipality(birthplace_id)){
						chkMunicipality.setSelected(true);
					}
					
					if(isBirthplaceOtherCountry(birthplace_id)){
						chkOtherCountry.setSelected(true);
					}
					
					lookupPlaceofBirth.setValue(birthplace_id);
					txtBirthPlace.setText(birthplace);
				}
				
				lookupCitizenship.setValue(citizenship_id);
				txtCitizenship.setText(citizenship);
				txtNoOfDependents.setText(dependent_no.toString());
				cmbNatureOfBusiness.setSelectedItem(business_nature);
				cmbBusinessClass.setSelectedItem(business_class);
				txtBusinessTelNo.setText(business_no);
				chkLocal.setSelected(local);
				chkPOP.setSelected(pop);
				chkVATRegistered.setSelected(vat_registered);
				//txtPagibigContribution.setValue(pagibig_contri);
				txtTelephoneNo.setText(tel_no);
				txtCellphoneNo.setText(mobile_no);
				txtFaxNo.setText(fax_no);
				txtEmailAddress.setText(email);
				txtFacebookAcct.setText(facebook_acct);
				txtTIN.setText(tin_no);
				txtMIDNo.setText(mid_no);
				txtSSSNo.setText(sss_no);
				txtGSISNo.setText(gsis_no);
				
				System.out.println("Display Individual Information");
			}
			
			if(entity_kind.equals("C")){
				tabEntityKind.setSelectedIndex(1);
				
				//cmbCorpType.setSelectedItem(entity_type); //XXX UNCOMMENT ME AFTER EDIT
				
				txtCorpAlias.setText(entity_alias);
				txtCorpName.setText(entity_name);
				txtCorpContactPerson.setText(contact_person);
				txtCorpPosition.setText(position);
				txtCorpTIN.setText(tin_no);
				cmbCorpBusinessNature.setSelectedItem(business_nature);
				cmbCorpBusinessClass.setSelectedItem(business_class);
				txtCorpBusinessTelNo.setText(business_no);
				txtCorpMobileNo.setText(mobile_no);
				txtCorpFaxNo.setText(fax_no);
				txtCorpEmail.setText(email);
				txtFacebookAcct.setText(facebook_acct);
				chkCorpVAT.setSelected(vat_registered);
				
				displayEntityTypes(entity_id, modelEntityTypes);
				scrollEntityTypes.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblEntityTypes.getRowCount())));
				tblEntityTypes.packAll();
				
				System.out.println("Display Corporation Information");
			}
		}
	}

	public void displayPersonal(String entity_id){//DISPLAYS THE DATA IN THE PERSONAL PANEL 
		String SQL = "select (case when trim(entity_kind) = 'I' then 'Individual' else 'Corporate' end) as entity_kind,\n " +
				"trim(first_name), trim(middle_name), trim(last_name),\n " +
				"(case when status_id = 'A' then 'Active' else 'Inactive' end) as status_id\n " +
				"from rf_entity\n " +
				"where entity_id = '"+ entity_id +"';";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			cmbClientType.setSelectedItem(db.getResult()[0][0]);
			txtFirstName.setText((String) db.getResult()[0][1]);
			txtMiddleName.setText((String) db.getResult()[0][2]);
			txtLastName.setText((String) db.getResult()[0][3]);
			cmbStatus.setSelectedItem(db.getResult()[0][4]);
		}
	}

	public void displayEntityKind(String entity_id){
		String entity_kind = "";

		pgSelect db = new pgSelect();

		String sql = "SELECT entity_type_desc\n" + 
					 "FROM rf_entity_assigned_type a\n" + 
					 "LEFT JOIN mf_entity_type b on b.entity_type_id = a.entity_type_id\n" + 
					 "WHERE a.entity_id = '"+entity_id+"';";
		db.select(sql);

		FncSystem.out("Display sql for Entity Kind",sql);

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				if(x == 0){
					entity_kind = (String) db.getResult()[x][0];
				}else{
					entity_kind = String.format("%s / %s", entity_kind ,db.getResult()[x][0]);
				}
			}
		}
		txtEntityKind.setText(entity_kind);
	}

	public void displayOther(String entity_id) {//DISPLAYS THE DATA IN THE OTHER PANEL

		String SQL = "select \n" + 
				"(case when trim(gender) = 'M' then 'Male' when gender = 'F' then 'Female' else null end) as gender,\n" + 
				"trim(coalesce(b.civil_status_desc, null)), \n" + 
				"a.date_of_birth, trim(coalesce(a.birth_place, '')) as birth_place,\n" + 
				"trim(coalesce(d.city_name, e.municipality_name)) as birthplace_name,\n" + 
				"trim(nullif(a.citizenship_code, 'null')) ,trim(c.citizenship_desc)\n" + 
				"from rf_entity a \n" + 
				"left join mf_civil_status b on b.civil_status_code = a.civil_status_code \n" + 
				"left join mf_citizen c on c.citizenship_code = a.citizenship_code \n" + 
				"left join mf_city d on d.city_id = a.birth_place\n" + 
				"left join mf_municipality e on e.municipality_id = a.birth_place\n" + 
				"where entity_id = '"+ entity_id +"' ";

		FncSystem.out("Display Other", SQL);		
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){

			cmbGender.setSelectedItem(db.getResult()[0][0]);
			
			System.out.println("=====================================================");
			System.out.println("Civil Status: " + db.getResult()[0][1].toString());
			
			
			cmbCivilStatus.setSelectedItem(db.getResult()[0][1]);
			dateDateOfBirth.setDate((Date) db.getResult()[0][2]);
			lookupPlaceofBirth.setValue((String) db.getResult()[0][3]);
			txtBirthPlace.setText((String) db.getResult()[0][4]);
			lookupCitizenship.setValue((String) db.getResult()[0][5]);
			txtCitizenship.setText((String) db.getResult()[0][6]);

		}
		
		chkMunicipality.setSelected((db.getResult()[0][3].toString().startsWith("5")));
		chkCity.setSelected((db.getResult()[0][3].toString().startsWith("0")) || (db.getResult()[0][3].toString().startsWith("1")));
		
		chkCity.setSelected(true);
	}

	public void displayContacts(String entity_id) {
		//Object [] data = displayContactInformation(entity_id);

		pgSelect db = new pgSelect();

		String SQL = "select array_to_string(telephone, ','), array_to_string(mobile, ','), array_to_string(fax, ','), array_to_string(email, ',') from rf_contacts where entity_id = '"+entity_id+"'";

		FncSystem.out("Display Contacts", SQL);		

		db.select(SQL);
		if(db.isNotNull()){
			/*tagTelNo.setTag((String) db.getResult()[0][0]);
			tagCellPhoneNo.setTag((String) db.getResult()[0][1]);
			tagFaxNo.setTag((String) db.getResult()[0][2]);
			tagEmailAddress.setTag((String) db.getResult()[0][3]);*/

			txtTelephoneNo.setText((String) db.getResult()[0][0]);
			txtCellphoneNo.setText((String) db.getResult()[0][1]);
			txtFaxNo.setText((String) db.getResult()[0][2]);
			txtEmailAddress.setText((String) db.getResult()[0][3]);

		}else{

			txtTelephoneNo.setText("");
			txtCellphoneNo.setText("");
			txtFaxNo.setText("");
			txtEmailAddress.setText("");

		}
	}

	public void displayBusiness(String entity_id){//DISPLAYS THE DATA BUSINESS PANEL

		String sql = "select initcap(coalesce(b.nob_desc, '')), coalesce(c.class_name, ''), array_to_string(business_no, ','), \n"+
				"coalesce(local, false), coalesce(pop, false) \n" + 
				"from rf_corp_type a\n" + 
				"left join mf_nature_business b on b.nob_code = a.nob_id\n" + 
				"left join mf_business_class c on c.class_id = a.business_class_id\n" + 
				"where a.entity_id = '"+ entity_id +"' and a.status_id = 'A'";

		FncSystem.out("Display Business", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			cmbNatureOfBusiness.setSelectedItem(db.getResult()[0][0]);
			cmbBusinessClass.setSelectedItem(db.getResult()[0][1]);
			txtBusinessTelNo.setText((String) db.getResult()[0][2]);
			chkLocal.setSelected((Boolean) db.getResult()[0][3]);
			chkPOP.setSelected((Boolean) db.getResult()[0][4]);
		}else{
			cmbNatureOfBusiness.setSelectedItem(null);
			cmbBusinessClass.setSelectedItem(null);
			txtBusinessTelNo.setText("");
			chkLocal.setSelected(false);
			chkPOP.setSelected(false);
		}
	}

	public void displayBIR(String entity_id) {//DISPLAYS THE DATA IN THE BIR PANEL
		String SQL = "select coalesce(a.ctc_no, ''), coalesce(a.date_ctc_no, null), "+
				"nullif(a.ctc_city_id, ''), trim(coalesce(b.city_name, '')), coalesce(a.tin_no, ''), "+
				"coalesce(a.date_tin_no, null), c.vat_registered "+
				"from rf_entity_id_no a "+
				"left join mf_city b on b.city_id = a.ctc_city_id "+
				"left join rf_entity c on c.entity_id = a.entity_id "+
				"where a.entity_id = '"+entity_id+"' "; 

		FncSystem.out("Display BIR", SQL);	
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){

			//txtCTCNo.setText((String) db.getResult()[0][0]);
			//dateDateIssued.setDate((Date) db.getResult()[0][1]);
			//lookupPlaceIssued.setValue((String) db.getResult()[0][2]);
			//txtPlaceIssued.setText((String) db.getResult()[0][3]);
			/*txtTIN.setText((String) db.getResult()[0][4]);
			dateDateTINEncoded.setDate((Date) db.getResult()[0][5]);*/
			chkVATRegistered.setSelected((Boolean) db.getResult()[0][6]);
		}else{
			//txtCTCNo.setText("");
			//dateDateIssued.setDate(null);
			//lookupPlaceIssued.setValue(null);
			//txtPlaceIssued.setText("");

			/*txtTIN.setText("");
			dateDateTINEncoded.setDate(null);*/
		}
	}

	public void displayGovernment(String entity_id) { 
		String SQL = "select a.sss_no,a.gsis_no, a.hdmf_no,a.hlid_no, COALESCE(b.dependent_no::VARCHAR , ''), trim(a.passport_no), a.ump_id, a.prc_id, COALESCE(a.pagibig_contribution, 0.00) \n"+
				"from rf_entity_id_no a \n" +
				"left join rf_entity b on b.entity_id = a.entity_id \n"+
				"where a.entity_id = '"+ entity_id +"';";

		FncSystem.out("Display Government", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			/*txtSSSNo.setText((String) db.getResult()[0][0]);
			txtGSISNo.setText((String) db.getResult()[0][1]);
			txtHDMFNo.setText((String) db.getResult()[0][2]);
			txtHLIDNo.setText((String) db.getResult()[0][3]);*/
			txtNoOfDependents.setText((String) db.getResult()[0][4]);
			//txtPassportNo.setText((String) db.getResult()[0][5]);
			/*txtUnifiedMultiPurposeID.setText((String) db.getResult()[0][6]);
			txtPRCID.setText((String) db.getResult()[0][7]);*/
			//txtPagibigContribution.setValue(db.getResult()[0][8]);
		}else{
			/*txtSSSNo.setText("");
			txtGSISNo.setText("");
			txtHDMFNo.setText("");
			txtHLIDNo.setText("");*/
			txtNoOfDependents.setText("");
			//txtPassportNo.setText("");
			//txtPagibigContribution.setValue(new BigDecimal("0.00"));
		}
	}
	
	private void validateBirthdate(DateEvent event) {
		Calendar today = Calendar.getInstance();

		_JDateChooser date = (_JDateChooser) event.getSource();
		Calendar dob = Calendar.getInstance();  
		dob.setTime(date.getDate());

		if(dob.after(today)){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Can't be born in the future", "Birth Date", JOptionPane.WARNING_MESSAGE);
			dateDateOfBirth.setDate(null);
		}else{
			int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
			if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
				age--;  
			} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
					&& today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
				age--;  
			}

			if(age < 18){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client must be 18 years old and above.", "Birth Date", JOptionPane.INFORMATION_MESSAGE);
				date.setDate(pnlCreateNewEntity.newBirthdate());
			}
		}
	}

	public boolean toSave() {//CHECKING OF THE REQUIRED FIELDS BEFORE SAVING

		if(cmbClientType.getSelectedItem().equals("Individual")){
			if (cmbCivilStatus.getSelectedItem() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Civil Status"), "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		//if(cmbClientType.getSelectedItem().equals("Corporate")){
		if(txtTelephoneNo.getText().equals("") && txtCellphoneNo.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Telephone No/Cellphone No."), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(cmbNatureOfBusiness.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select your nature of business"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(cmbBusinessClass.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select business class"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtTIN.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input TIN no"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (dateDateTINEncoded.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input date TIN Encoded"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		/*if(txtSSSNo.getText().length() < 10){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "SSS No should have more than allowed characters", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/

		return true;
	}

	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;

		if(txtCellphoneNo.getText().isEmpty() || txtCellphoneNo.getText().equals("")){
			required_fields = required_fields + "Cellphone No.\n";
			toSave = false;
		}
		if(cmbNatureOfBusiness.getSelectedItem() == null){
			required_fields = required_fields + "Nature of Business\n";
			toSave = false;
		}
		if(cmbBusinessClass.getSelectedItem() == null){
			required_fields = required_fields + "Business Class\n";
			toSave = false;
		}

		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	private boolean isContactExisting(String entity_id) {
		pgSelect db = new pgSelect();
		db.select("select * from rf_contacts where entity_id = '"+ entity_id +"'");
		return db.isNotNull();
	}

	public boolean saveContacts(String entity_id){//SAVING OF CONTACTS PANEL
		pgUpdate db = new pgUpdate();

		String telephone = txtTelephoneNo.getText().trim().replace("/", ",");
		String mobile_no = txtCellphoneNo.getText().trim().replace("/", ",");
		String fax = txtFaxNo.getText().trim();
		String email = txtEmailAddress.getText().trim();

		if(isContactExisting(entity_id)){//UPDATING
			String sql = "update rf_contacts set telephone = '{"+ telephone +"}', fax = '{"+ fax +"}', mobile = '{"+mobile_no+"}', email = '{"+email+"}', \n"+
					"edited_by = '"+ UserInfo.EmployeeCode +"', date_edited =  now() where entity_id = '"+entity_id+"'\n";
			db.executeUpdate(sql, true);
		}else{//SAVING
			String sql = "INSERT INTO rf_contacts(entity_id, telephone, mobile, fax, email, status_id, created_by, date_created)" + 
					"VALUES ('"+ entity_id +"', '{"+ telephone +"}', '{"+ mobile_no +"}' , '{"+ fax +"}', '{"+ email +"}', 'A', \n" + 
					"'"+ UserInfo.EmployeeCode +"', now());\n" ; 
			db.executeUpdate(sql, true);
		}

		db.commit();
		return true;
	}

	private boolean isGovermentExisting(String entity_id){
		pgSelect db = new pgSelect();
		db.select("select * from rf_entity_id_no where entity_id = '"+ entity_id +"'");
		return db.isNotNull();
	}

	public boolean isAccountHolding(String entity_id){// PUT SQL HERE TO CHECK WHETHER ACCOUNT IN HOLDING STAGE
		Boolean isHolding = false;

		pgSelect db = new pgSelect();

		String sql = "SELECT CASE \n" + 
				"WHEN EXISTS (SELECT * FROM rf_buyer_status where byrstatus_id = '17' and entity_id = '"+entity_id+"') \n" + 
				"AND EXISTS (SELECT * FROM rf_sold_unit where entity_id = '"+entity_id+"') THEN FALSE\n" + 
				"ELSE TRUE END;\n";
		db.select(sql);

		FncSystem.out("Check if Holding Sql", sql);

		if(db.isNotNull()){
			isHolding = (Boolean) db.getResult()[0][0];
		}
		return isHolding;
	}
	
	public boolean isCoapplicantTR(String entity_id){
		Boolean isCoapp = false;
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT CASE \n"+
					 "WHEN EXISTS (SELECT get_client_name(a.entity_id), *\n" + 
					 "FROM rf_sold_unit a\n" + 
					 "LEFT JOIN rf_entity_connect b on b.entity_id = a.entity_id AND b.status_id = 'A' \n" + 
					 "WHERE a.currentstatus != '02'\n" + 
					 "and NULLIF(b.connect_id, '') IS NOT NULL\n" + 
					 "AND b.connect_type IN ('SC', 'CO')\n" + 
					 "and b.connect_id = '"+entity_id+"') \n"+
					 "THEN TRUE ELSE FALSE END";
		db.select(SQL);
		
		FncSystem.out("Check if Coapplicant", SQL);
		
		if(db.isNotNull()){
			isCoapp = (Boolean) db.getResult()[0][0];
			
		}
		return isCoapp;
	}
	
	private boolean isBirthplaceMunicipality(String birthplace_id){
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM mf_municipality WHERE municipality_id = '"+birthplace_id+"' AND status_id = 'A'";
		db.select(SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isBirthplaceCity(String birthplace_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM mf_city WHERE city_id = '"+birthplace_id+"' AND status_id = 'A'";
		db.select(SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isBirthplaceOtherCountry(String birthplace_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM mf_country WHERE country_id = '"+birthplace_id+"' AND status_id = 'A'";
		db.select(SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean canEdit(String entity_id){
		Boolean can_edit = false;
		
		if(UserInfo.Department.equals("09") && forSMD(entity_id)){
			can_edit = true;
		}else if(UserInfo.Department.equals("09") && forSMD(entity_id) == false){
			can_edit = false;
			System.out.println("Dumaan dito sa False Editing ng SMD");
		}
		
		if(UserInfo.Division.equals("31") && forASD(entity_id)){
			can_edit = true;
		}else if(UserInfo.Division.equals("31") && forASD(entity_id) == false){
			can_edit = false;
			System.out.println("Dumaan dito sa False editing ng ASD");
		}
		
		if(UserInfo.Division.equals("02") && forASD(entity_id)){
			can_edit = true;
		}else if(UserInfo.Division.equals("02") && forASD(entity_id) == false){
			can_edit = false;
		}
		
		if(UserInfo.Department.equals("04") && forFAD(entity_id)){
			can_edit = true;
		}else if(UserInfo.Department.equals("04") && forFAD(entity_id) == false){
			can_edit = false;
			System.out.println("Dumaan dito sa False editing ng FAD");
		}
		
		//ADD edit for HAD
		
		if(UserInfo.Department.equals("98")){
			can_edit = true;
		}
		
		return can_edit;
	}
	
	private Boolean forASD(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT EXISTS (SELECT * FROM rf_entity_assigned_type WHERE entity_type_id IN ('01', '31') AND entity_id = '"+entity_id+"' AND status_id = 'A')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For ASD", SQL);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}
	
	private Boolean forSMD(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT EXISTS (SELECT * FROM rf_entity_assigned_type WHERE entity_type_id IN ('03', '04', '23','24','34','14', '35', '38', '39' ,'40', '41') AND entity_id = '"+entity_id+"' AND status_id = 'A')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For SMD", SQL);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}
	
	private Boolean forFAD(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT EXISTS (SELECT * FROM rf_entity_assigned_type WHERE entity_type_id IN ('11', '16', '33', '17', '09', '07', '08', '10', '12', '19', '24', '32', '33', '34', '20', '31', '36', '25', '35', '15', '43') AND entity_id = '"+entity_id+"' AND status_id = 'A')";
		db.select(SQL);
		
		FncSystem.out("DIsplay SQL FOR FAD", SQL);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}
	
	public void newCI(String entity_id){
		clearIndividualCI();
		clearCorporationCI();
		
		tabEntityKind.setEnabledAt(0, true);
		tabEntityKind.setEnabledAt(1, true);
		
		ci.setComponentsEditable(pnlIndividual, true);
		ci.setComponentsEnabled(pnlIndividual, true);
		
		//txtPagibigContribution.setEditable(true);
		
		//cmbClientType.setEnabled(true);
		txtFirstName.setEditable(true);
		cmbStatus.setEnabled(true);
		cmbGender.setEnabled(true);
		cmbCivilStatus.setEnabled(true);
		dateDateOfBirth.setEnabled(true);
		
		cmbNatureOfBusiness.setEnabled(true);
		cmbBusinessClass.setEnabled(true);
		cmbStatus.setSelectedIndex(0);
		cmbStatus.setEnabled(false);
		chkLocal.setEnabled(false);
		chkPOP.setEnabled(false);
		//txtPagibigContribution.setValue(new BigDecimal("0.00"));
		
		txtEntityKind.setEditable(false);
		txtBirthPlace.setEditable(false);
		txtCitizenship.setEditable(false);
		//txtPagibigContribution.setEditable(false);
		txtTelephoneNo.setEditable(false);
		txtCellphoneNo.setEditable(false);
		txtFaxNo.setEditable(false);
		txtEmailAddress.setEditable(false);
		//txtMailingAddress.setEditable(false);
		
		//ENABLED COMPONENTS FOR CORPORATION
		ci.setComponentsEnabled(pnlCorporation, true);
		ci.setComponentsEnabled(pnlCorpInfoComponents, true);
		ci.setComponentsEditable(pnlCorpInfoComponents, true);
		
		btnAddCorpContactInfo.setEnabled(true);
		btnAddCorpContactInfo.setText("Add Contact Info");
		tblEntityTypes.setEditable(true);
		System.out.println("Dumaan dito sa New CI");
		this.entity_id = entity_id;
	}

	public void editCI(String entity_id){ //Enabling of Components of when edit button is clicked
		
		
		tabEntityKind.setEnabledAt(tabEntityKind.getSelectedIndex(), true);
		
		if(tabEntityKind.getSelectedIndex() == 0){
			ci.setComponentsEditable(pnlIndividual, true);
			ci.setComponentsEnabled(pnlOtherComponent, true);
			ci.setComponentsEnabled(pnlPersonalInfoComponent, true);
			ci.setComponentsEnabled(pnlOtherComponent, true);
			ci.setComponentsEnabled(pnlBusinessInfoComponent, true);
			
			//txtPagibigContribution.setEditable(false);
			
			ci.setComponentsEditable(pnlContactInfo, false);

			if(isAccountHolding(entity_id)){
				if(isCoapplicantTR(entity_id)){
					txtFirstName.setEditable(false);
					txtLastName.setEditable(false);
					txtMiddleName.setEditable(false);
					cmbCivilStatus.setEnabled(false);
				}else{
					txtFirstName.setEditable(true);
					txtLastName.setEditable(true);
					txtMiddleName.setEditable(true);
					cmbCivilStatus.setEnabled(true);
				}
			}else{
				txtFirstName.setEditable(false);
				txtLastName.setEditable(false);
				txtMiddleName.setEditable(false);
				cmbCivilStatus.setEnabled(false);
			}

			if(cmbBusinessClass.getSelectedItem() != null){
				//XXX CHANGE THIS
				String business_class =((String) cmbBusinessClass.getSelectedItem()).split("-")[1].trim();
				
				if(business_class.equals("03")){
					
					System.out.printf("Business_class: %s%n", business_class);
					chkLocal.setEnabled(true);
					chkPOP.setEnabled(true);
					
					System.out.printf("Display Check Local: %s%n", chkLocal.isSelected());
					System.out.printf("Display Check POP: %s%n", chkPOP.isSelected());
					
					if(/*chkLocal.isSelected() ||*/ chkPOP.isSelected()){
						System.out.println("Pagibig Contribution is Editable");
						//txtPagibigContribution.setEditable(true);
					}else{
						//txtPagibigContribution.setEditable(false);
					}
				}else if(business_class.equals("09") || business_class.equals("04") || business_class.equals("07") || business_class.equals("08")){
					chkLocal.setEnabled(false);//ADDED BY LESTER REFER TO DCRF 145
					chkPOP.setEnabled(false);
					chkLocal.setSelected(false);
					chkPOP.setSelected(false);
					//txtPagibigContribution.setEditable(true);	
				}else{
					chkLocal.setEnabled(false);
					chkPOP.setEnabled(false);
					//txtPagibigContribution.setEditable(false);
				}
				
				/*if(business_class.equals("04") || business_class.equals("07") || business_class.equals("08") || business_class.equals("09")){
					txtPagibigContribution.setEditable(true);
				}*/
			}

			cmbStatus.setEnabled(false);
			//txtMailingAddress.setEditable(false);
			cmbGender.setEnabled(true);
			dateDateOfBirth.setEnabled(true);
			chkCity.setEnabled(true);
			chkMunicipality.setEnabled(true);
			cmbBusinessClass.setEnabled(true);
			cmbNatureOfBusiness.setEnabled(true);
			chkVATRegistered.setEnabled(true);
			txtBirthPlace.setEditable(false);
			txtCitizenship.setEditable(false);
			txtEntityKind.setEditable(false);

			if(UserInfo.Division.equals("31")){
				chkVATRegistered.setEnabled(false);
			}

			//txtPagibigContribution.setEditable(false);
			txtBirthPlace.setEditable(false);
			txtCitizenship.setEditable(false);

			btnAddContactInfo.setEnabled(true);
			btnMotherMaidenName.setEnabled(true);
		}

		if(tabEntityKind.getSelectedIndex() == 1){
			ci.setComponentsEnabled(pnlCorpInfoComponents, true);
			ci.setComponentsEditable(pnlCorpInfoComponents, true);
			tblEntityTypes.setEditable(true);
			//ci.setComponentsEnabled(pnlCorpTypes, true);
			
			//cmbCorpType.setEnabled(false);
			btnAddCorpContactInfo.setEnabled(true);
		}
	}

	public boolean saveGovernment(String entity_id){//SAVING AND UPDATING FOR THE GOVERNMENT PANEL
		pgUpdate db = new pgUpdate();

		//String SSS_no = txtSSSNo.getText().trim();
		//String GSIS_no = txtGSISNo.getText().trim();
		//String HDMF_no = txtHDMFNo.getText().trim();
		//String HLID_no = txtHLIDNo.getText().trim();
		//String ump_id = txtUnifiedMultiPurposeID.getText().trim();
		//String prc_id = txtPRCID.getText();
		//String TIN_no = txtTIN.getText().trim();
		//String CTC_no = txtCTCNo.getText().trim();
		//Date date_tin = dateDateTINEncoded.getDate();
		//Date date_ctc = dateDateIssued.getDate();
		//String ctc_place_issued = lookupPlaceIssued.getValue();
		//String passport_no = txtPassportNo.getText().trim();
		//BigDecimal pagibig_contri = (BigDecimal) txtPagibigContribution.getValued();

		if(isGovermentExisting(entity_id)){//UPDATING 
			/*String sql = "update rf_entity_id_no set sss_no = '"+ SSS_no +"',gsis_no = '"+ GSIS_no +"', hdmf_no = '"+ HDMF_no +"' "
					+ ",hlid_no = '"+ HLID_no +"', ump_id = '"+ump_id+"',prc_id = '"+prc_id+"', tin_no = '"+TIN_no+"', date_tin_no = nullif('"+ date_tin +"', 'null')::TIMESTAMP "
					+ ", edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now(), passport_no = '"+passport_no+"', pagibig_contribution = "+pagibig_contri+" "
					+ "where entity_id = '" + entity_id +"' ";

			db.executeUpdate(sql, true);*/

			String sql = "UPDATE rf_entity_id_no SET pagibig_contribution = COALESCE(0.00, 0.00), edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n"+
					"WHERE entity_id = '"+entity_id+"' \n";
			db.executeUpdate(sql, true);

		}else{//SAVING

			String sql = "INSERT INTO rf_entity_id_no(entity_id, sss_no, gsis_no, hdmf_no, tin_no, date_tin_no, \n"+
					"hlid_no, ump_id, prc_id, status_id, created_by, date_created, passport_no, pagibig_contribution)\n" + 
					"VALUES ('"+entity_id+"', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'A', \n" + 
					"'"+UserInfo.EmployeeCode+"', now() , NULL, 0.00);";
			db.executeUpdate(sql, true);
		}

		db.commit();
		return true;
	}

	public boolean saveOther(String entity_id){//SAVING FOR THE OTHER PANEL IN THE client information panel
		pgUpdate db = new pgUpdate();

		String client_type = ((String) cmbClientType.getSelectedItem()).trim();
		String civil_status = (String) cmbCivilStatus.getSelectedItem();
		String citizenship_code = lookupCitizenship.getValue();
		String birth_place_id = lookupPlaceofBirth.getValue();
		Boolean vat_registered = chkVATRegistered.isSelected();
		Timestamp date_of_birth = dateDateOfBirth.getTimestamp();
		String first_name = txtFirstName.getText().trim().replace("'", "''").toUpperCase();
		String middle_name = txtMiddleName.getText().trim().replace("'", "''").toUpperCase();
		String last_name = txtLastName.getText().trim().replace("'", "''").toUpperCase();
		String entity_name = String.format("%s, %s %s",  last_name, first_name, middle_name).toUpperCase();
		String entity_alias = "";
		try{
			entity_alias = String.format("%s%s%s", first_name.substring(0, 1), middle_name.substring(0, 1), last_name).toUpperCase();
		}catch (IndexOutOfBoundsException e){}

		int age = (int) FncDate.getAge(date_of_birth);

		String gender = null;

		if(cmbGender.getSelectedItem() != null){
			gender = ((String) cmbGender.getSelectedItem()).trim();
		}

		String dependent_no = null;

		if(txtNoOfDependents.getText().isEmpty() == false){
			dependent_no = txtNoOfDependents.getText();
		}

		String sql = "UPDATE rf_entity set \n"+
				"entity_name = '"+entity_name+"', first_name = '"+first_name+"', last_name = '"+last_name+"', middle_name = '"+middle_name+"', entity_alias = '"+entity_alias+"', \n"+
				"civil_status_code = (case when '"+client_type+"' = 'Individual' then (select civil_status_code from mf_civil_status where civil_status_desc = '"+ civil_status +"') else null end), \n"+
				"date_of_birth = (case when '"+client_type+"' = 'Individual' then (nullif('"+date_of_birth+"', 'null')::TIMESTAMP) else null end) , \n"+
				"age = (case when '"+client_type+"' = 'Individual' then "+age+" else null end), \n"+
				"citizenship_code = (case when '"+client_type+"' = 'Individual' then (NULLIF('"+ citizenship_code +"', 'null')) else null end), \n"+
				"vat_registered = "+ vat_registered +", \n"+
				"birth_place = (case when '"+client_type+"' = 'Individual' then (NULLIF('"+ birth_place_id +"', 'null')) else null end), \n"+
				"edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now(), \n"+
				"dependent_no = "+dependent_no+", \n"+
				"gender = (case when '"+client_type+"' = 'Individual' then (case when '"+gender+"' = 'Male' then 'M' else 'F' end) else null end) \n"+
				"where entity_id = '"+ entity_id +"' \n";
		db.executeUpdate(sql, true);

		String sqlClientDependent = "UPDATE rf_client_depedent set noofdep = coalesce("+dependent_no+", 0) where entity_id = '"+entity_id+"' \n";
		db.executeUpdate(sqlClientDependent, true);

		db.commit();

		return true;
	}

	public boolean isBusinessExisting(String entity_id){
		pgSelect db = new pgSelect();
		db.select("select * from rf_corp_type where entity_id = '"+ entity_id +"'");
		return db.isNotNull();
	}

	public boolean saveBusiness(String entity_id){//SAVING AND UPDATING FOR THE BUSINESS PANEL
		pgUpdate db = new pgUpdate();

		String business_nature = (String) cmbNatureOfBusiness.getSelectedItem();
		String business_class = (String) cmbBusinessClass.getSelectedItem();
		String business_no = txtBusinessTelNo.getText();
		Boolean local = chkLocal.isSelected();
		Boolean pop = chkPOP.isSelected();

		if(isBusinessExisting(entity_id)){//UPDATING
			String sql = "update rf_corp_type set nob_id = (select nob_code from mf_nature_business where nob_desc = UPPER('"+ business_nature +"')), \n"+
					"business_no = '{"+business_no+"}',business_class_id = (select class_id from mf_business_class where class_name = '"+ business_class +"'), \n"+
					"edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now() ,local = "+local+", pop = "+pop+"\n"+
					"where entity_id = '"+ entity_id +"' \n";
			db.executeUpdate(sql, true);
		}else{//SAVING
			String sql = "INSERT INTO rf_corp_type(entity_id, nob_id, business_no, business_class_id, status_id, created_by, date_created, local, pop)\n" + 
					"VALUES ('"+ entity_id +"', (select nob_code from mf_nature_business where nob_desc = UPPER('"+ business_nature +"')), \n"+
					"'{"+business_no+"}' ,(select class_id from mf_business_class where class_name = '"+ business_class +"'), 'A', '"+UserInfo.EmployeeCode+"', \n"+
					"now(), "+local+", "+pop+")";
			db.executeUpdate(sql, true);
		}

		db.commit();
		return true;
		
	}
	
	public Boolean saveCI_Info(String entity_id){
		
		String entity_kind = (tabEntityKind.getSelectedIndex() == 0 ? "I":"C");
		String entity_type = "01"; //(tabEntityKind.getSelectedIndex() == 0 ? "01":((String) cmbCorpType.getSelectedItem()).split("-")[0].trim());
		entity_type = "01";
		String entity_name = (tabEntityKind.getSelectedIndex() == 0 ? String.format("%s, %s %s", txtLastName.getText().replace("'", "''"), txtFirstName.getText().replace("'", "''"), txtMiddleName.getText().replace("'", "''")).toUpperCase().trim():txtCorpName.getText().toUpperCase().trim().replace("'", "''"));
		String fname = txtFirstName.getText().toUpperCase().trim().replace("'", "''");
		String mname = txtMiddleName.getText().toUpperCase().trim().replace("'", "''");
		String lname = txtLastName.getText().toUpperCase().trim().replace("'", "''");
		String entity_alias = null;
		try{
		entity_alias = (tabEntityKind.getSelectedIndex() == 0 ? String.format("%s%s%s", txtFirstName.getText().trim().substring(0, 1), txtMiddleName.getText().trim().substring(0, 1), txtLastName.getText()).toUpperCase():txtCorpAlias.getText().toUpperCase().trim());
		}catch (StringIndexOutOfBoundsException e){}
		Timestamp date_of_birth = dateDateOfBirth.getTimestamp(); 
		
		int age = (int) FncDate.getAge(date_of_birth);
		String gender = cmbGender.getSelectedItem() == "Male" ? "M":"F";
		String civil_status = _JInternalFrame.GET_CIVIL_STATUS_CODE((String) cmbCivilStatus.getSelectedItem());
		
		String dependent_no = txtNoOfDependents.getText().isEmpty() ? null:txtNoOfDependents.getText();
		String citizenship = lookupCitizenship.getValue();
		Boolean vat_registered = tabEntityKind.getSelectedIndex() == 0 ? chkVATRegistered.isSelected():chkCorpVAT.isSelected();
		String birthplace = lookupPlaceofBirth.getValue();
		String business_nature = (tabEntityKind.getSelectedIndex() == 0 ? ((String) cmbNatureOfBusiness.getSelectedItem()).split("-")[1].trim():((String) cmbCorpBusinessNature.getSelectedItem()).split("-")[1].trim());
		String business_class_id = (tabEntityKind.getSelectedIndex() == 0 ? ((String) cmbBusinessClass.getSelectedItem()).split("-")[1].trim():((String) cmbCorpBusinessClass.getSelectedItem()).split("-")[1].trim());
		String business_no = tabEntityKind.getSelectedIndex() == 0 ? txtBusinessTelNo.getText().trim():txtCorpBusinessTelNo.getText().trim();
		Boolean local = chkLocal.isSelected();
		Boolean pop = chkPOP.isSelected();
		String telephone = txtTelephoneNo.getText().trim();
		String mobile_no = tabEntityKind.getSelectedIndex() == 0 ? txtCellphoneNo.getText().trim():txtCorpMobileNo.getText().trim();
		String fax_no = tabEntityKind.getSelectedIndex() == 0 ? txtFaxNo.getText().trim():txtCorpFaxNo.getText().trim();
		String email = tabEntityKind.getSelectedIndex() == 0 ? txtEmailAddress.getText().trim():txtCorpEmail.getText().trim();
		String facebook_acct = tabEntityKind.getSelectedIndex() == 0 ? txtFacebookAcct.getText().trim():txtCorpFacebookAcct.getText().trim();
		String contact_person = txtCorpContactPerson.getText();
		String position = txtCorpPosition.getText();
		String tin_no = tabEntityKind.getSelectedIndex() == 0 ? txtTIN.getText().trim():txtCorpTIN.getText().trim();
		//BigDecimal pagibig_contri = (BigDecimal) txtPagibigContribution.getValued();
		String mid_no = txtMIDNo.getText().trim();
		String sss_no = txtSSSNo.getText().trim(); //XXX PUT IN FUNCTION FOR SAVING
		String gsis_no = txtGSISNo.getText().trim(); //XXX PUT IN FUNCTION FOR SAVING
		
		System.out.printf("Display dependent_no: (%s)%n", dependent_no);
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT sp_save_ci_information('"+entity_id+"', '"+entity_kind+"', '"+entity_type+"' ,'"+entity_name+"', '"+fname+"', '"+mname+"', '"+lname+"', '"+entity_alias+"', NULLIF('"+date_of_birth+"', 'null')::DATE::TIMESTAMP, "+age+", '"+gender+"' ,'"+civil_status+"', "+dependent_no+", \n"+
					 "NULLIF('"+citizenship+"', 'null'), "+vat_registered+", NULLIF('"+birthplace+"', 'null'), NULLIF(COALESCE(NULLIF('"+business_nature+"', 'Non Profit'), '098'), 'null'), NULLIF('"+business_class_id+"', 'null'), '"+business_no+"', "+local+", "+pop+", '"+ telephone +"' , '"+mobile_no+"', '"+fax_no+"', '"+email+"', \n"+
					 "'"+ facebook_acct +"','"+contact_person+"', '"+position+"', '"+tin_no+"', '"+mid_no+"' , '"+sss_no+"', '"+gsis_no+"', 0.00, '"+UserInfo.EmployeeCode+"')";
	
		db.select(SQL, "Save", true);
		
		FncSystem.out("Display Client Info Saving", SQL);
		
		if((boolean) db.getResult()[0][0] && entity_kind.equals("I")){
			dlg_MiddleInitial dlg_mi = new dlg_MiddleInitial(entity_id);
			dlg_mi.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg_mi.setVisible(true);
		}
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public boolean saveEntityTypes(String entity_id) {
		Boolean saved = false;
		
		if(tabEntityKind.getSelectedIndex() == 1){
			ArrayList<Boolean> isTagged = new ArrayList<Boolean>();
			
			for(int x=0; x<modelEntityTypes.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelEntityTypes.getValueAt(x, 0);
				if(isSelected){
					isTagged.add(isSelected);
				}
			}
			
			if(isTagged.contains(true)){
				for(int x=0; x < modelEntityTypes.getRowCount(); x++){
					Boolean selected = (Boolean) modelEntityTypes.getValueAt(x, 0);
					String entity_type_id = (String) modelEntityTypes.getValueAt(x, 1);

					String SQL = "SELECT sp_update_entity_types('"+ entity_id +"', '"+ entity_type_id +"', "+ selected +", '"+ UserInfo.EmployeeCode +"');";

					pgSelect db = new pgSelect();
					db.select(SQL);
					
					FncSystem.out("Display ", SQL);
					saved = true;
				}
			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select entity type", "Save", JOptionPane.WARNING_MESSAGE);
				saved = false;
			}
		}else{
			saved = true;
		}
		
		return saved;
	}
	
	private void displayEntityTypes(String entity_id, modelCI_EntityTypes model){
		if(model != null){
			model.clear();
			
			pgSelect db = new pgSelect();
			
			String SQL = "SELECT EXISTS(SELECT * FROM rf_entity_assigned_type WHERE entity_id = '"+ entity_id +"' AND entity_type_id = a.entity_type_id AND status_id = 'A'),\n" + 
						 "  TRIM(entity_type_id) as type_id, " +
						 "TRIM(entity_type_desc) as type_desc, " +
						 "TRIM(a.wtax_id) as wtax_id, " +
						 "TRIM(b.wtax_desc) as wtax_desc," +
						 "b.wtax_rate \n" + 
						 "FROM mf_entity_type a\n" + 
						 "LEFT JOIN rf_withholding_tax b ON b.wtax_id = a.wtax_id\n" + 
						 "ORDER BY entity_type_desc;";
			db.select(SQL);
			
			FncSystem.out("Display Entity Types", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}
	
	/*public boolean saveCI(String entity_id){
		
		saveBusiness(entity_id);
		saveGovernment(entity_id);
		saveContacts(entity_id);
		saveOther(entity_id);

		cancelCI(entity_id);

		ci.setComponentsEditable(this, false);
		return true;
	}*/

	public void cancelCI(String entity_id){
		
		tabEntityKind.setEnabledAt(0, false);
		tabEntityKind.setEnabledAt(1, false);
		
		disableComponents();
		
		cmbStatus.setEnabled(false);
		cmbGender.setEnabled(false);
		btnMotherMaidenName.setEnabled(false);
		cmbCivilStatus.setEnabled(false);
		//dateDateOfBirth.setEnabled(false);
		dateDateOfBirth.setEditable(false);
		dateDateOfBirth.getCalendarButton().setEnabled(false);
		chkCity.setEnabled(false);
		chkMunicipality.setEnabled(false);
		cmbNatureOfBusiness.setEnabled(false);
		cmbBusinessClass.setEnabled(false);
		chkLocal.setEnabled(false);
		chkPOP.setEnabled(false);
		chkVATRegistered.setEnabled(false);
		btnAddContactInfo.setEnabled(false);
		
		txtCorpAlias.setEditable(false);
		txtCorpContactPerson.setEditable(false);
		txtCorpPosition.setEditable(false);
		txtCorpTIN.setEditable(false);
		chkCorpVAT.setEnabled(false);
		cmbCorpBusinessNature.setEnabled(false);
		cmbCorpBusinessClass.setEnabled(false);
		btnAddCorpContactInfo.setEnabled(false);
		/*ci.setComponentsEditable(pnlIndividual, false);
		ci.setComponentsEditable(pnlCorporation, false);
		
		cmbStatus.setEnabled(false);
		cmbGender.setEnabled(false);
		cmbCivilStatus.setEnabled(false);
		dateDateOfBirth.setEnabled(false);
		chkCity.setEnabled(false);
		chkMunicipality.setEnabled(false);
		cmbNatureOfBusiness.setEnabled(false);
		cmbBusinessClass.setEnabled(false);

		chkVATRegistered.setEnabled(false);
		btnAddContactInfo.setEnabled(false);
		btnMotherMaidenName.setEnabled(false);
		chkLocal.setEnabled(false);
		chkPOP.setEnabled(false);
		
		txtPagibigContribution.setEditable(false);
		
		ci.setComponentsEditable(pnlCorporation, false);
		ci.setComponentsEnabled(pnlCorporation, false);*/
		
		displayDetails(entity_id);
	}

	public void clearCIFields(){//CLEARS THE FIELDS IN THIS PANEL 
		
		lookupPlaceofBirth.setValue(null);
		txtBirthPlace.setText("");
		lookupCitizenship.setValue(null);
		lookupPlaceofBirth.setLookupSQL(null);
		txtCitizenship.setText("");
		chkCity.setSelected(false);
		chkMunicipality.setSelected(false);
		dateDateOfBirth.setDate(null);
		
	}
	
	public void clearIndividualCI(){
		
		//cmbClientType.setSelectedItem(null);
		
		txtEntityKind.setText("");
		txtFirstName.setText("");
		txtMiddleName.setText("");
		txtLastName.setText("");
		cmbStatus.setModel(new DefaultComboBoxModel(new String[] { "Active", "Inactive" }));
		cmbGender.setModel(new DefaultComboBoxModel(new String[] { "Male", "Female" }));
		cmbCivilStatus.setModel(new DefaultComboBoxModel(getCivilStatus()));
		
		/*cmbStatus.setSelectedItem(null);
		cmbGender.setSelectedItem(null);
		cmbCivilStatus.setSelectedItem(null);*/
		
		dateDateOfBirth.setDate(null);
		chkCity.setSelected(false);
		chkMunicipality.setSelected(false);
		lookupPlaceofBirth.setValue(null);
		txtBirthPlace.setText("");
		lookupCitizenship.setValue(null);
		txtCitizenship.setText("");
		txtNoOfDependents.setText("");
		cmbNatureOfBusiness.setModel(new DefaultComboBoxModel(getBusinessNature()));
		cmbBusinessClass.setModel(new DefaultComboBoxModel(getBusinessClass()));
		/*cmbNatureOfBusiness.setSelectedItem(null);
		cmbBusinessClass.setSelectedItem(null);*/
		txtBusinessTelNo.setText("");
		chkLocal.setSelected(false);
		chkPOP.setSelected(false);
		chkVATRegistered.setSelected(false);
		//txtPagibigContribution.setValue(new BigDecimal("0.00"));
		txtTelephoneNo.setText("");
		txtCellphoneNo.setText("");
		txtEmailAddress.setText("");
		txtFaxNo.setText("");
		//txtMailingAddress.setText("");
		txtFacebookAcct.setText("");
		txtTIN.setText("");
		txtMIDNo.setText("");
		txtSSSNo.setText("");
		txtGSISNo.setText("");
		
	}
	
	public void clearCorporationCI(){
		
		//cmbCorpType.setSelectedItem(null); //XXX UNCOMMENT ME AFTER TESTING
		txtCorpAlias.setText("");
		txtCorpName.setText("");
		txtCorpContactPerson.setText("");
		txtCorpPosition.setText("");
		txtCorpTIN.setText("");
		chkCorpVAT.setSelected(false);
		cmbCorpBusinessNature.setSelectedItem(null);
		cmbCorpBusinessClass.setSelectedItem(null);
		cmbCorpBusinessClass.setModel(new DefaultComboBoxModel(getBusinessClass()));
		cmbCorpBusinessNature.setModel(new DefaultComboBoxModel(getBusinessNature()));
		txtCorpBusinessTelNo.setText("");
		txtCorpMobileNo.setText("");
		txtCorpFaxNo.setText("");
		txtCorpEmail.setText("");
		txtCorpFacebookAcct.setText("");
	
	}
	
	public void disableComponents(){
		
		ci.setComponentsEditable(pnlIndividual, false);
		//ci.setComponentsEnabled(pnlPersonalInfoComponent, false);
		//ci.setComponentsEnabled(pnlBusinessInfoComponent, false);
		//ci.setComponentsEnabled(pnlOtherComponent, false);
		//ci.setComponentsEnabled(pnlContactInfoComponent, false);
		
		ci.setComponentsEditable(pnlCorporation, false);
		//ci.setComponentsEnabled(pnlCorpInfoComponents, false);
		//ci.setComponentsEnabled(pnlCorpContactComponents, false);
		tblEntityTypes.setEditable(false);
		
		tabEntityKind.setEnabledAt(0, false);
		tabEntityKind.setEnabledAt(1, false);
		
	}

	public void setAddedContactInfo(List<String> listContactInfo ,Integer selected_index){
		
		int selected_tab = tabEntityKind.getSelectedIndex();
		
		if(selected_tab == 0){
			if(selected_index == 0){
				txtTelephoneNo.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
			if(selected_index == 1){
				txtCellphoneNo.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
			if(selected_index == 2){
				txtFaxNo.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
			if(selected_index == 3){
				txtEmailAddress.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
			if(selected_index == 4){
				txtFacebookAcct.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
		}
		
		if(selected_tab == 1){
			if(selected_index == 0){
				txtCorpBusinessTelNo.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
			if(selected_index == 1){
				txtCorpMobileNo.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
			if(selected_index == 2){
				txtCorpFaxNo.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
			if(selected_index == 3){
				txtCorpEmail.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
			if(selected_index == 4){
				txtCorpFacebookAcct.setText(listContactInfo.toString().replaceAll("\\[|\\]", "")/*.replace(" ", "")*/);
			}
		}
		
	}
	
	public ArrayList<String> setContactNo(Integer selected_index){
		int selected_tab = tabEntityKind.getSelectedIndex();
		
		ArrayList<String> listTelephone = new ArrayList<String>(Arrays.asList(txtTelephoneNo.getText().split(",")));
		ArrayList<String> listCellphoneNo = new ArrayList<String>(Arrays.asList(txtCellphoneNo.getText().split(",")));
		ArrayList<String> listFaxNo = new ArrayList<String>(Arrays.asList(txtFaxNo.getText().split(",")));
		ArrayList<String> listEmailAdd = new ArrayList<String>(Arrays.asList(txtEmailAddress.getText().split(",")));
		ArrayList<String> listFacebookAcct = new ArrayList<String>(Arrays.asList(txtFacebookAcct.getText().split(",")));
		
		ArrayList<String> listCorpBusinessNo = new ArrayList<String>(Arrays.asList(txtCorpBusinessTelNo.getText().split(",")));
		ArrayList<String> listCorpMobileNo = new ArrayList<String>(Arrays.asList(txtCorpMobileNo.getText().split(",")));
		ArrayList<String> listCorpFaxNo = new ArrayList<String>(Arrays.asList(txtCorpFaxNo.getText().split(",")));
		ArrayList<String> listCorpEmailAdd = new ArrayList<String>(Arrays.asList(txtCorpEmail.getText().split(",")));
		ArrayList<String> listCorpFacebookAcct = new ArrayList<String>(Arrays.asList(txtCorpFacebookAcct.getText().split(",")));
		
		if(selected_tab == 0){
			if(selected_index == 0){
				return listTelephone;
			}
			if(selected_index == 1){
				return listCellphoneNo;
			}
			if(selected_index == 2){
				return listFaxNo;
			}
			if(selected_index == 3){
				return listEmailAdd;
			}
			if(selected_index == 4){
				return listFacebookAcct;
			}
		}
		
		if(selected_tab == 1){
			if(selected_index == 0){
				return listCorpBusinessNo;
			}
			if(selected_index == 1){
				return listCorpMobileNo;
			}
			if(selected_index == 2){
				return listCorpFaxNo;
			}
			if(selected_index == 3){
				return listCorpEmailAdd;
			}
			if(selected_index == 4){
				return listCorpFacebookAcct;
			}
		}

		return null;
	}
	
	private Icon getSearchIcon() {
		Image img = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/search1.png")).getImage();
		img = img.getScaledInstance(19, 19, Image.SCALE_SMOOTH);

		return new ImageIcon(img);
	}
	
	private String sqlCorpType(){
		String SQL = "SELECT FALSE, TRIM(entity_type_id) as \"Type ID\", \n" + 
					"TRIM(entity_type_desc) as \"Type Desc\"\n" + 
					"from mf_entity_type\n" + 
					"where entity_type_id != '02'\n" + 
					"order by trim(entity_type_id);";
		
		return SQL;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Mother's Maiden Name")){
			
			addMotherMaidenName = new dlg_MotherMaidenName(FncGlobal.homeMDI, "Mother's Maiden Name", this, entity_id);
			addMotherMaidenName.setLocationRelativeTo(FncGlobal.homeMDI);
			addMotherMaidenName.setVisible(true);

		}

		if(actionCommand.equals("Add Contact Info")){
			addContact = new dlg_AddContactInfo(FncGlobal.homeMDI, "Add Contact Info", this);
			addContact.setLocationRelativeTo(FncGlobal.homeMDI);
			addContact.setVisible(true);
		}
		
		if(actionCommand.equals("Add Corp Contact Info")){
			addContact = new dlg_AddContactInfo(FncGlobal.homeMDI, "Add Contact Info", this);
			addContact.setLocationRelativeTo(FncGlobal.homeMDI);
			addContact.setVisible(true);
		}
		
		if(actionCommand.equals("Add Corp Type")){
			
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null , "Request", sqlCorpType(), true);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			
			Object [][] data = dlg.getResult();
			
			if(data != null){
				try {
					if(data.length > 0){
						ArrayList<String> listTypeID = new ArrayList<String>();
						ArrayList<String> listTypeDesc = new ArrayList<String>();
						for (int x = 0; x < data.length; x++) {
							String type_id = (String) data[x][0];
							String type_desc = (String) data[x][1];
							listTypeID.add(type_id);
							listTypeDesc.add(type_desc);
						}
						//txtCorpType.setText(listTypeID.toString());
						txtCorpType.setText(String.format("[ %s ]", listTypeID));
						
					}else{
						txtCorpType.setText("");
					}
				} catch (NullPointerException e) { }
			}
		}
	}
}
