package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncDate;
import Functions.SpringUtilities;
import Functions.UserInfo;
import components._JCheckBox;
import components._JInternalFrame;
import components._JXTextField;

public class pnlCreateNewEntity extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6665150826326054566L;

	private JTabbedPane tabEntityKind;

	private JPanel pnlIndividual;

	private JPanel pnlIndividualInfo;
	private JLabel lblLastName;
	private JTextField txtLastName;
	private JLabel lblFirstName;
	private JTextField txtFirstName;
	private JLabel lblMiddleName;
	private JTextField txtMiddleName;
	private JLabel lblBirthDate;
	private _JDateChooser dateBirthDate;
	private JLabel lblGender;
	private JComboBox cmbGender;
	private JLabel lblCivilStatus;
	private JComboBox cmbCivilStatus;
	private JLabel lblBirthPlace;
	private JTextField txtBirthPlace;
	private JLabel lblCitizenship;
	private JComboBox cmbCitizenship;
	private JComboBox cmbCityMunicipality;
	private JComboBox cmbProvince;

	private JPanel pnlIndividualEast;

	private JPanel pnlIndividualContactInfo;
	private JLabel lblTelephoneNo;
	private JTextField txtTelephoneNo;
	private JLabel lblCellphoneNo;
	private JTextField txtCellphoneNo;
	private JLabel lblFaxNo;
	private JTextField txtFaxNo;
	private JLabel lblEmail;
	private JTextField txtEmail;

	private JPanel pnlIndividualGovernmentInfo;
	private JLabel lblSSSNo;
	private JTextField txtSSSNo;
	private JLabel lblCTCNo;
	private JTextField txtCTCNo;
	private JLabel lblTINNo;
	private JTextField txtTINNo;

	private JPanel pnlIndividualSouth;

	private JPanel pnlCorporate;
	private JPanel pnlCorporateNorth;
	private JPanel pnlCorporateNorthWest;
	private JPanel pnlCorporateNorthCenter;

	private JLabel lblCorporationType;
	private JPanel pnlCorporationType;
	private JComboBox cmbCorporationType;

	private JLabel lblCorporationAlias;
	private _JXTextField txtCorporationAlias;

	private JLabel lblCorporationName;
	private _JXTextField txtCorporationName;

	private JLabel lblContactPerson;
	private _JXTextField txtContactPerson;

	private JLabel lblPosition;
	private _JXTextField txtPosition;

	private JLabel lblTINCorp;
	private JPanel pnlTINCorp;
	private _JXTextField txtTINCorp;
	private _JCheckBox chkVATRegistered;

	private JPanel pnlCorporateCenter;
	private JPanel pnlCorporateCenterWest;
	private JPanel pnlCityMunicipality;
	private JPanel pnlCorporateCenterCenter;

	private JLabel lblProvince;
	private JPanel pnlProvince;

	private JLabel lblNoBlkLot;
	private _JXTextField txtNoBlkLot;

	private JLabel lblStreetSubd;
	private _JXTextField txtStreetSubd;

	private JLabel lblDistrictTown;
	private _JXTextField txtDistrictTown;

	private JLabel lblCityMunicipality;
	
	public pnlCreateNewEntity() {
		initGUI();
	}

	public pnlCreateNewEntity(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlCreateNewEntity(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlCreateNewEntity(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(3, 3));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(800, 600));
		{
			tabEntityKind = new JTabbedPane();
			add(tabEntityKind, BorderLayout.CENTER);
			//tabEntityKind.setBorder(lineBorder);
			tabEntityKind.setTabPlacement(JTabbedPane.BOTTOM);
			{
				pnlIndividual = new JPanel(new BorderLayout(3, 3));
				tabEntityKind.addTab("  Individual  ", null, pnlIndividual, null);
				pnlIndividual.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlIndividualInfo = new JPanel(new SpringLayout());
					pnlIndividual.add(pnlIndividualInfo, BorderLayout.CENTER);
					pnlIndividualInfo.setBorder(components.JTBorderFactory.createTitleBorder("Individual Info."));
					{
						lblLastName = new JLabel("* Last Name", JLabel.TRAILING);
						pnlIndividualInfo.add(lblLastName);
					}
					{
						txtLastName = new JTextField();
						pnlIndividualInfo.add(txtLastName);
						txtLastName.setPreferredSize(new Dimension(120, 20));
					}
					{
						lblFirstName = new JLabel("* First Name", JLabel.TRAILING);
						pnlIndividualInfo.add(lblFirstName);
					}
					{
						txtFirstName = new JTextField();
						pnlIndividualInfo.add(txtFirstName);
						txtFirstName.setPreferredSize(new Dimension(120, 20));
					}
					{
						lblMiddleName = new JLabel("Middle Name", JLabel.TRAILING);
						pnlIndividualInfo.add(lblMiddleName);
					}
					{
						txtMiddleName = new JTextField();
						pnlIndividualInfo.add(txtMiddleName);
						txtMiddleName.setPreferredSize(new Dimension(120, 20));
					}
					{
						lblGender = new JLabel("Gender", JLabel.TRAILING);
						pnlIndividualInfo.add(lblGender);
					}
					{
						JPanel pnlGender = new JPanel(new BorderLayout());
						pnlIndividualInfo.add(pnlGender);
						{
							cmbGender = new JComboBox(new String[] { "Male", "Female" });
							pnlGender.add(cmbGender, BorderLayout.WEST);
							cmbGender.setPreferredSize(new Dimension(120, 20));
						}
					}
					{
						lblBirthDate = new JLabel("Birth Date", JLabel.TRAILING);
						pnlIndividualInfo.add(lblBirthDate);
					}
					{
						JPanel pnlBirthDate = new JPanel(new BorderLayout());
						pnlIndividualInfo.add(pnlBirthDate);
						{
							dateBirthDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlBirthDate.add(dateBirthDate, BorderLayout.WEST);
							//dateBirthDate.setDate(newBirthdate());
							dateBirthDate.setPreferredSize(new Dimension(120, 20));
							dateBirthDate.addDateListener(new DateListener() {
								public void datePerformed(DateEvent event) {//XXX
									validateBirthdate(event);
								}
							});
						}
					}
					{
						lblBirthPlace = new JLabel("Birth Place", JLabel.TRAILING);
						pnlIndividualInfo.add(lblBirthPlace);
					}
					{
						txtBirthPlace = new JTextField();
						pnlIndividualInfo.add(txtBirthPlace);
						txtBirthPlace.setPreferredSize(new Dimension(120, 20));
					}
					{
						lblCivilStatus = new JLabel("Civil Status", JLabel.TRAILING);
						pnlIndividualInfo.add(lblCivilStatus);
					}
					{
						JPanel pnlCivilStatus = new JPanel(new BorderLayout());
						pnlIndividualInfo.add(pnlCivilStatus);
						{
							cmbCivilStatus = new JComboBox(_JInternalFrame.CIVIL_STATUS().values().toArray());
							pnlCivilStatus.add(cmbCivilStatus, BorderLayout.WEST);
							cmbCivilStatus.setPreferredSize(new Dimension(200, 20));
						}
					}
					{
						lblCitizenship = new JLabel("Citizenship", JLabel.TRAILING);
						pnlIndividualInfo.add(lblCitizenship);
					}
					{
						JPanel pnlCitizenship = new JPanel(new BorderLayout());
						pnlIndividualInfo.add(pnlCitizenship);
						{
							cmbCitizenship = new JComboBox(_JInternalFrame.CITIZENSHIP().values().toArray());
							pnlCitizenship.add(cmbCitizenship, BorderLayout.WEST);
							cmbCitizenship.setSelectedItem("FILIPINO");
							cmbCitizenship.setPreferredSize(new Dimension(200, 20));
						}
					}
					SpringUtilities.makeCompactGrid(pnlIndividualInfo, 8, 2, 5, 5, 5, 5, false);//XXX
				}
				{
					pnlIndividualEast = new JPanel(new SpringLayout());
					//pnlIndividual.add(pnlIndividualEast, BorderLayout.EAST);
					pnlIndividualEast.setPreferredSize(new Dimension(274, 0));
					{
						pnlIndividualContactInfo = new JPanel(new SpringLayout());
						pnlIndividualEast.add(pnlIndividualContactInfo);
						pnlIndividualContactInfo.setBorder(components.JTBorderFactory.createTitleBorder("Contact Info."));
						{
							lblTelephoneNo = new JLabel("Tel. No.", JLabel.TRAILING);
							pnlIndividualContactInfo.add(lblTelephoneNo);
						}
						{
							txtTelephoneNo = new JTextField();
							pnlIndividualContactInfo.add(txtTelephoneNo);
						}
						{
							lblCellphoneNo = new JLabel("Cell. No.", JLabel.TRAILING);
							pnlIndividualContactInfo.add(lblCellphoneNo);
						}
						{
							txtCellphoneNo = new JTextField();
							pnlIndividualContactInfo.add(txtCellphoneNo);
						}
						{
							lblFaxNo = new JLabel("Fax No.", JLabel.TRAILING);
							pnlIndividualContactInfo.add(lblFaxNo);
						}
						{
							txtFaxNo = new JTextField(10);
							pnlIndividualContactInfo.add(txtFaxNo);
						}
						{
							lblEmail = new JLabel("Email", JLabel.TRAILING);
							pnlIndividualContactInfo.add(lblEmail);
						}
						{
							txtEmail = new JTextField();
							pnlIndividualContactInfo.add(txtEmail);
						}
						SpringUtilities.makeCompactGrid(pnlIndividualContactInfo, 4, 2, 5, 5, 5, 5, false);//XXX
					}
					{
						pnlIndividualGovernmentInfo = new JPanel(new SpringLayout());
						pnlIndividualEast.add(pnlIndividualGovernmentInfo);
						pnlIndividualGovernmentInfo.setBorder(components.JTBorderFactory.createTitleBorder("Government Info."));
						{
							lblSSSNo = new JLabel("SSS No.", JLabel.TRAILING);
							pnlIndividualGovernmentInfo.add(lblSSSNo);
						}
						{
							txtSSSNo = new JTextField();
							pnlIndividualGovernmentInfo.add(txtSSSNo);
						}
						{
							lblCTCNo = new JLabel("CTC No.", JLabel.TRAILING);
							pnlIndividualGovernmentInfo.add(lblCTCNo);
						}
						{
							txtCTCNo = new JTextField();
							pnlIndividualGovernmentInfo.add(txtCTCNo);
						}
						{
							lblTINNo = new JLabel("TIN No.", JLabel.TRAILING);
							pnlIndividualGovernmentInfo.add(lblTINNo);
						}
						{
							txtTINNo = new JTextField();
							pnlIndividualGovernmentInfo.add(txtTINNo);
						}
						SpringUtilities.makeCompactGrid(pnlIndividualGovernmentInfo, 3, 2, 5, 5, 5, 5, false);//XXX
					}
					SpringUtilities.makeCompactGrid(pnlIndividualEast, 2, 1, 0, 0, 5, 5, false);//XXX
				}
				{
					pnlIndividualSouth = new JPanel(new BorderLayout());
					pnlIndividual.add(pnlIndividualSouth, BorderLayout.SOUTH);
					pnlIndividualSouth.setPreferredSize(new Dimension(0, 30));
					{
						JSeparator sep = new JSeparator();
						pnlIndividualSouth.add(sep, BorderLayout.NORTH);
					}
				}
			}
			{
				pnlCorporate = new JPanel(new GridLayout(2, 1));
				tabEntityKind.addTab("  Corporate  ", null, pnlCorporate, null);
				pnlCorporate.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlCorporateNorth = new JPanel(new BorderLayout(3, 3));
					pnlCorporate.add(pnlCorporateNorth);
					pnlCorporateNorth.setBorder(components.JTBorderFactory.createTitleBorder("Main Information"));
					//pnlCorporateNorth.setPreferredSize(new Dimension(717, 180));
					{
						pnlCorporateNorthWest = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlCorporateNorth.add(pnlCorporateNorthWest, BorderLayout.WEST);
						pnlCorporateNorthWest.setPreferredSize(new Dimension(130, 167));
						{
							lblCorporationType = new JLabel("Corporation Type");
							pnlCorporateNorthWest.add(lblCorporationType);
						}
						{
							lblCorporationName = new JLabel("* Corporation Name");
							pnlCorporateNorthWest.add(lblCorporationName);
						}
						{
							lblContactPerson = new JLabel("Contact Person");
							pnlCorporateNorthWest.add(lblContactPerson);
						}
						{
							lblPosition = new JLabel("Position");
							pnlCorporateNorthWest.add(lblPosition);
						}
						{
							lblTINCorp = new JLabel("TIN No.");
							pnlCorporateNorthWest.add(lblTINCorp);
						}
					}
					{
						pnlCorporateNorthCenter = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlCorporateNorth.add(pnlCorporateNorthCenter, BorderLayout.CENTER);
						{
							pnlCorporationType = new JPanel(new BorderLayout(3, 3));
							pnlCorporateNorthCenter.add(pnlCorporationType);
							{
								cmbCorporationType = new JComboBox(new DefaultComboBoxModel(_HoldingAndReservation.getCorporationType()));
								pnlCorporationType.add(cmbCorporationType, BorderLayout.WEST);
								cmbCorporationType.setActionCommand("CorporationType");
								cmbCorporationType.setPreferredSize(new Dimension(300, 31));
								cmbCorporationType.addActionListener(this);
							}
							{
								JPanel pnlCorporationAlias = new JPanel(new BorderLayout(3, 3));
								pnlCorporationType.add(pnlCorporationAlias, BorderLayout.CENTER);
								{
									lblCorporationAlias = new JLabel("Corp. Alias ", JLabel.TRAILING);
									pnlCorporationAlias.add(lblCorporationAlias, BorderLayout.WEST);
									lblCorporationAlias.setPreferredSize(new Dimension(90, 46));
								}
								{
									txtCorporationAlias = new _JXTextField();
									pnlCorporationAlias.add(txtCorporationAlias, BorderLayout.CENTER);
									txtCorporationAlias.setEditable(true);
								}
							}
						}
						{
							txtCorporationName = new _JXTextField();
							pnlCorporateNorthCenter.add(txtCorporationName);
							txtCorporationName.setEditable(true);
						}
						{
							txtContactPerson = new _JXTextField();
							pnlCorporateNorthCenter.add(txtContactPerson);
							txtContactPerson.setEditable(true);
						}
						{
							txtPosition = new _JXTextField();
							pnlCorporateNorthCenter.add(txtPosition);
							txtPosition.setEditable(true);
						}
						{
							pnlTINCorp = new JPanel(new BorderLayout(10, 3));
							pnlCorporateNorthCenter.add(pnlTINCorp);
							{
								txtTINCorp = new _JXTextField();
								pnlTINCorp.add(txtTINCorp, BorderLayout.WEST);
								txtTINCorp.setEditable(true);
								txtTINCorp.setPreferredSize(new Dimension(300, 31));
							}
							{
								chkVATRegistered = new _JCheckBox("VAT Registered");
								pnlTINCorp.add(chkVATRegistered, BorderLayout.CENTER);
							}
						}
					}
				}
				{
					pnlCorporateCenter = new JPanel(new BorderLayout(5, 5));
					pnlCorporate.add(pnlCorporateCenter);
					pnlCorporateCenter.setBorder(components.JTBorderFactory.createTitleBorder("Address"));
					{
						pnlCorporateCenterWest = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlCorporateCenter.add(pnlCorporateCenterWest, BorderLayout.WEST);
						pnlCorporateCenterWest.setPreferredSize(new Dimension(130, 334));
						{
							lblProvince = new JLabel("Province");
							pnlCorporateCenterWest.add(lblProvince);
						}
						{
							lblNoBlkLot = new JLabel("No / Blk-Lot");
							pnlCorporateCenterWest.add(lblNoBlkLot);
						}
						{
							lblStreetSubd = new JLabel("Street / Subd");
							pnlCorporateCenterWest.add(lblStreetSubd);
						}
						{
							lblDistrictTown = new JLabel("District / Town");
							pnlCorporateCenterWest.add(lblDistrictTown);
						}
						{
							lblCityMunicipality = new JLabel("City / Municipality");
							pnlCorporateCenterWest.add(lblCityMunicipality);
						}
					}
					{
						pnlCorporateCenterCenter = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlCorporateCenter.add(pnlCorporateCenterCenter, BorderLayout.CENTER);
						{
							pnlProvince = new JPanel(new BorderLayout());
							pnlCorporateCenterCenter.add(pnlProvince);
							{
								cmbProvince = new JComboBox(_HoldingAndReservation.getProvince());
								pnlProvince.add(cmbProvince, BorderLayout.WEST);
								cmbProvince.setActionCommand("Province");
								cmbProvince.setPreferredSize(new Dimension(300, 64));
								cmbProvince.addActionListener(this);
							}
						}
						{
							txtNoBlkLot = new _JXTextField();
							pnlCorporateCenterCenter.add(txtNoBlkLot);
							txtNoBlkLot.setEditable(true);
						}
						{
							txtStreetSubd = new _JXTextField();
							pnlCorporateCenterCenter.add(txtStreetSubd);
							txtStreetSubd.setEditable(true);
						}
						{
							txtDistrictTown = new _JXTextField();
							pnlCorporateCenterCenter.add(txtDistrictTown);
							txtDistrictTown.setEditable(true);
						}
						{
							pnlCityMunicipality = new JPanel(new BorderLayout());
							pnlCorporateCenterCenter.add(pnlCityMunicipality);
							{
								cmbCityMunicipality = new JComboBox(_HoldingAndReservation.getCity());
								pnlCityMunicipality.add(cmbCityMunicipality, BorderLayout.WEST);
								cmbCityMunicipality.setPreferredSize(new Dimension(300, 64));
							}
						}
					}
				}
			}
		}

		setHoldingEnabled(false);
	}

	public Integer getSelectedEntityType() {
		return tabEntityKind.getSelectedIndex();
	}

	public void clearEntities() {
		txtLastName.setText("");
		txtFirstName.setText("");
		txtMiddleName.setText("");
		cmbGender.setSelectedIndex(0);
		dateBirthDate.setDate(newBirthdate());
		txtBirthPlace.setText("");
		cmbCivilStatus.setSelectedIndex(0);
		cmbCitizenship.setSelectedItem("FILIPINO");
	}

	public void clearCorporation() {
		cmbCorporationType.setSelectedIndex(0);
		txtCorporationName.setText("");
		txtCorporationAlias.setText("");
		txtContactPerson.setText("");
		txtPosition.setText("");
		txtTINCorp.setText("");

		cmbProvince.setSelectedIndex(0);
		txtNoBlkLot.setText("");
		txtStreetSubd.setText("");
		txtDistrictTown.setText("");
	}

	public void setHoldingEnabled(boolean enable) {
		panelLooper(this, enable);
	}

	private void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				if (comp.getName().equals("scrollPhase")) {
					panelLooper((JScrollPane) comp, enable);
				}
			} else if (comp instanceof JTabbedPane) {
				panelLooper((JTabbedPane) comp, enable);
			} else {
				// System.out.printf("Panel: %-50s Component: %s%n",
				// panel.getName(), comp.getName());
				if (comp.getName() != null) {
					/*if (!comp.getName().equals("datePrepared")) {
						if (comp.getName().equals("dateCOC")) {
							comp.setEnabled(chkCOCDate.isEnabled() && chkCOCDate.isSelected());
						} else if (comp.getName().equals("dateCOCExpiry")) {
							comp.setEnabled(chkCOCExpiry.isEnabled() && chkCOCExpiry.isSelected());
						} else {
							comp.setEnabled(!enable);
						}
					}*/
				} else {
					if (panel instanceof JScrollPane) {
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					} else {
						comp.setEnabled(enable);
					}
				}
			}
		}
	}

	public Boolean toSave() {

		//Check if last name is blank
		if(txtLastName.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Last Name", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		//Check if first name is blank
		if(txtFirstName.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input First Name", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		//Check if middle name is blank
		if(txtMiddleName.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Middle Name", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		//Check if client is in not a minor
		/*if((int) FncDate.getAge(dateBirthDate.getTimestamp()) < 18){
			return false;
		}*/

		return true;
	}

	public Boolean toSaveCorporation() {

		if(txtCorporationName.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Corporation Name", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtCorporationAlias.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Corporation Alias", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtContactPerson.getText().contains("*") && txtContactPerson.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Contact Person", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtPosition.getText().contains("*") && txtPosition.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Position", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtTINCorp.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input TIN No.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtNoBlkLot.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input No / Blk-Lot", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtStreetSubd.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Street / Subd", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		/*if(txtDistrictTown.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input District / Town", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/

		return true;
	}

	public Boolean save() {
		if(toSave()){
			String entity_id = null;
			String entity_kind = (tabEntityKind.getSelectedIndex() == 0 ? "I":"C");
			String first_name = txtFirstName.getText().trim().replace("'", "''").toUpperCase();
			String last_name = txtLastName.getText().trim().replace("'", "''").toUpperCase();
			String middle_name = txtMiddleName.getText().trim().replace("'", "''").toUpperCase();
			String entity_name = String.format("%s, %s %s", last_name, first_name, middle_name).toUpperCase();
			String entity_alias = null;
			try {
				entity_alias = String.format("%s%s%s", first_name.substring(0, 1), middle_name.substring(0, 1), last_name).toUpperCase();
			} catch (StringIndexOutOfBoundsException e) { }
			Timestamp date_of_birth = dateBirthDate.getTimestamp();
			int age = (int) FncDate.getAge(date_of_birth);
			String gender = cmbGender.getSelectedItem() == "Male" ? "M":"F";
			String civil_status_code = _JInternalFrame.GET_CIVIL_STATUS_CODE((String) cmbCivilStatus.getSelectedItem());
			String citizenship_code = _JInternalFrame.GET_CITIZENSHIP_CODE((String) cmbCitizenship.getSelectedItem());

			// Check if the entity is already exist
			pgSelect db = new pgSelect();
			db.select("select * from rf_entity where last_name ~* '"+ last_name +"' and first_name ~* '"+ first_name +"' and middle_name ~* '"+ middle_name +"'/*and gender = '"+ gender +"' and date_of_birth = NULLIF('"+ date_of_birth +"', 'null')::TIMESTAMP*/;");
			if(db.isNotNull()){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Entity already exists.", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			db.select("select get_new_entity_id();");
			if(db.isNotNull()){
				entity_id = (String) db.getResult()[0][0];
			}
			
			//Finally we can save the entity
			return saveNewEntity(entity_id, entity_kind, entity_name, first_name, last_name, middle_name, entity_alias, date_of_birth, age, gender, civil_status_code, citizenship_code);
		}else{
			return false;
		}
	}

	public Boolean saveNewEntity(String entity_id, String entity_kind, String entity_name, String first_name, String last_name, String middle_name,
			String entity_alias, Timestamp date_of_birth, Integer age, String gender, String civil_status_code,
			String citizenship_code) {

		pgUpdate db = new pgUpdate();

		/*System.out.printf("Kind: %s%n", entity_kind);
		System.out.printf("Name: %s%n", entity_name);
		System.out.printf("First Name: %s%n", first_name);
		System.out.printf("Last Name: %s%n", last_name);
		System.out.printf("Middle Name: %s%n", middle_name);
		System.out.printf("Alias: %s%n", entity_alias);
		System.out.printf("Birth Date: %s%n", date_of_birth);
		System.out.printf("Age: %s%n", age);
		System.out.printf("Gender: %s%n", gender);
		System.out.printf("Civil Status: %s%n", civil_status_code);
		System.out.printf("Citizenship: %s%n", citizenship_code);*/

		String rf_entity = "INSERT INTO rf_entity(\n" + 
				"            entity_id, entity_kind, entity_name, first_name, last_name, middle_name, \n" + 
				"            entity_alias, date_of_birth, age, gender, civil_status_code, \n" + 
				"            citizenship_code, status_id, created_by, date_created)\n" + 
				"    VALUES ('"+ entity_id +"', '"+ entity_kind +"', '"+ entity_name +"', '"+ first_name +"', '"+ last_name +"', '"+ middle_name +"', \n" + 
				"            NULLIF('"+ entity_alias +"', 'null'), NULLIF('"+ date_of_birth +"', 'null')::TIMESTAMP, "+ age +", '"+ gender +"', '"+ civil_status_code +"', \n" + 
				"            '"+ citizenship_code +"', 'A', '"+ UserInfo.EmployeeCode +"', now());";
		db.executeUpdate(rf_entity, false);


		String rf_entity_assigned_type = "INSERT INTO rf_entity_assigned_type(\n" + 
				"            entity_id, entity_type_id, status_id, created_by, date_created)\n" + 
				"    VALUES ('"+ entity_id +"', '01', 'A', '"+ UserInfo.EmployeeCode +"', now());";
		db.executeUpdate(rf_entity_assigned_type, false);

		db.commit();

		//Return Successfully
		JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Entity has been created.", "Save", JOptionPane.INFORMATION_MESSAGE);

		//Clear all fields
		clearEntities();

		return true;
	}

	public Boolean saveNewCorporation() {

		if(toSaveCorporation()){
			String p_entity_type_id  = ((String)cmbCorporationType.getSelectedItem()).split("-")[0].trim();
			String p_entity_name     = txtCorporationName.getText();
			String p_entity_alias    = txtCorporationAlias.getText();
			String p_contact_person  = txtContactPerson.getText();
			String p_position        = txtPosition.getText();
			String p_tin_no          = txtTINCorp.getText();
			Boolean vat_registered   = chkVATRegistered.isSelected();
			String p_addr_no         = txtNoBlkLot.getText();
			String p_street          = txtStreetSubd.getText();
			String p_barangay        = txtDistrictTown.getText();
			String p_province_id     = ((String)cmbProvince.getSelectedItem()).split("-")[0].trim();
			String p_zip_code        = "";
			String p_city_id = null;
			String p_municipality_id = null;

			if(p_province_id.equals("NONE")){
				p_city_id = ((String)cmbCityMunicipality.getSelectedItem()).split("-")[0].trim();
			}else{
				p_municipality_id = ((String)cmbCityMunicipality.getSelectedItem()).split("-")[0].trim();
			}

			String SQL = "SELECT sp_save_new_corporation('"+ p_entity_type_id +"', '"+ p_entity_name +"', '"+ p_entity_alias +"', '"+ p_contact_person +"',\n" + 
					"  '"+ p_position +"', '"+ p_tin_no +"', "+ vat_registered +", '"+ p_addr_no +"', '"+ p_street +"', '"+ p_barangay +"', '"+ p_city_id +"',\n" + 
					"  '"+ p_municipality_id +"', '"+ p_province_id +"', '"+ p_zip_code +"', '"+ UserInfo.EmployeeCode +"')";

			pgSelect db = new pgSelect();
			db.select(SQL);

			//Return Successfully
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Entity has been created.", "Save", JOptionPane.INFORMATION_MESSAGE);

			//Clear all fields
			clearCorporation();

			return true;
		}else{
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {//XXX actions
		String action = arg0.getActionCommand();

		if(action.equals("Province")){
			JComboBox combo = (JComboBox) arg0.getSource();
			String item = (String) combo.getSelectedItem();
			String province_id = item.split("-")[0].trim();

			System.out.printf("Province ID: %s%n", province_id);

			if(province_id.equals("NONE")){
				cmbCityMunicipality.setModel(new DefaultComboBoxModel(_HoldingAndReservation.getCity()));
			}else{
				//EDITED 2016-05-11 LESTER
				if(_HoldingAndReservation.getCityMunicipality(province_id) != null){
					cmbCityMunicipality.setModel(new DefaultComboBoxModel(_HoldingAndReservation.getCityMunicipality(province_id)));
				}else{
					cmbCityMunicipality.setModel(new DefaultComboBoxModel(new String[]{"NONE"}));
				}
			}
		}

		if(action.equals("CorporationType")){
			JComboBox combo = (JComboBox) arg0.getSource();
			String item = (String) combo.getSelectedItem();
			//String type_id = item.split("-")[0].trim();
			String type_desc = item.split("-")[1].trim();

			if(type_desc.equals("CONTRACTOR")){
				lblContactPerson.setText("* Contact Person");
				lblPosition.setText("* Position");
			}else{
				lblContactPerson.setText("Contact Person");
				lblPosition.setText("Position");
			}
		}
	}

	public static Date newBirthdate() {
		int year/*, month, day*/;

		Calendar today = Calendar.getInstance();

		year = today.get(Calendar.YEAR) - 18;
		//month = today.get(Calendar.MONTH);
		//day = today.get(Calendar.DAY_OF_MONTH);

		today.set(Calendar.YEAR, year);
		return today.getTime();
	}

	private void validateBirthdate(DateEvent event) {
		Calendar today = Calendar.getInstance();

		_JDateChooser date = (_JDateChooser) event.getSource();
		Calendar dob = Calendar.getInstance();  
		dob.setTime(date.getDate());

		if(dob.after(today)){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Can't be born in the future", "Birth Date", JOptionPane.WARNING_MESSAGE);
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
				date.setDate(newBirthdate());
			}
		}
	}

}
