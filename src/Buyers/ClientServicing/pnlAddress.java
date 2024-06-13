package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelAddress;

/**
 * @author JOHN LESTER FATALLO
 */

public class pnlAddress extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -112512527936397963L;
	Dimension SIZE = new Dimension(800, 600);

	private JPanel pnlUpper;
	private JPanel pnlNorth;

	private JPanel pnlAddressDetails;
	private JLabel lblCountry;
	private JComboBox cmbCountry;
	private JLabel lblProvince;
	private JComboBox cmbProvince;
	private JLabel lblNoBlockLot;
	private JXTextField txtNoBlockLot;
	private JLabel lblStreetSubd;
	private JXTextField txtStreetSubd;
	private JLabel lblDistrictTown;
	private JXTextField txtDistrictTown;
	private JLabel lblCityProvince;
	private JComboBox cmbMunicipality;
	private JLabel lblZipCode;
	private _JLookup lookupZipCode;
	private JXTextField txtZipCode;
	private JComboBox cmbZipCode;

	private JPanel pnlLocationSketch;
	private JLabel lblLocationSketch;

	private JPanel pnlCenter;

	private JPanel pnlAddress;
	private JPanel pnlAddressType;
	private JLabel lblAddressType;
	private JComboBox cmbAddressType;
	private JLabel lblRecStatus;
	private JPanel pnlRecStatus;
	private JComboBox cmbRecStatus;
	private JCheckBox chkPreferredMailingAdd;
	private JCheckBox chkPreferredCTSAdd;
	private JPanel pnlPreferredMailAdd;

	private JPanel pnlOwnershipInfo;
	private JPanel pnlRentAmount;
	private JLabel lblOwnership;
	private JComboBox cmbOwnership;
	private JLabel lblRentAmount;
	private JPanel pnlOwnership;
	private _JXFormattedTextField txtRentAmount;
	private JLabel lblLengthOfStay;
	private _JDateChooser dateFrom;
	private _JDateChooser dateTo;
	private JPanel pnldateFromTo;
	private JSpinner spinnerYearStay;
	private JSpinner spinnerMonthsStay;
	
	private JPanel pnlRTS;
	private JLabel lblDateRTS;
	private _JDateChooser dateRTS;
	private JLabel lblReturnReason;
	private JTextField txtReturnReason;
	/*private JLabel lblRTSSeparator;
	private JLabel lblRTSSeparator2;*/

	private _JTableMain tblAddress;
	private JScrollPane scrollAddress;
	private modelAddress modelAdd;
	private JList rowHeaderAddress;

	private ClientInformation ci;

	public pnlAddress(ClientInformation ci) {
		this.ci = ci;
		initGUI();
	}

	public pnlAddress(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlAddress(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlAddress(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setLayout(new BorderLayout(5, 5));
		{
			pnlUpper = new JPanel(new BorderLayout(1, 1));	
			this.add(pnlUpper, BorderLayout.NORTH);
			//pnlUpper.setLayout(new SpringLayout());
			{
				pnlNorth = new JPanel(new BorderLayout(1,1));
				pnlUpper.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new SpringLayout());
				pnlNorth.setPreferredSize(new Dimension(800, 240));
				{
					pnlAddressDetails = new JPanel();
					pnlNorth.add(pnlAddressDetails, BorderLayout.WEST);
					pnlAddressDetails.setLayout(new SpringLayout());
					pnlAddressDetails.setBorder(JTBorderFactory.createTitleBorder("Address Details"));
					pnlAddressDetails.setPreferredSize(new Dimension(200, 0));
					{
						lblCountry = new JLabel("Country", JLabel.TRAILING);
						pnlAddressDetails.add(lblCountry);
					}
					{
						cmbCountry = new JComboBox(FncGlobal.listCountry());
						pnlAddressDetails.add(cmbCountry);
						cmbCountry.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								String selected_item = (String) cmbCountry.getSelectedItem();
								
								if(selected_item.equals("PHILIPPINES (REPUBLIC OF THE)") == false){
									cmbProvince.setSelectedItem("NONE");
									cmbMunicipality.setSelectedItem(null);
									cmbZipCode.setSelectedItem(null);
									
								}
							}
						});
					}
					{
						lblProvince = new JLabel("Province", JLabel.TRAILING);
						pnlAddressDetails.add(lblProvince);
					}
					{
						cmbProvince = new JComboBox();
						pnlAddressDetails.add(cmbProvince);
						cmbProvince.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(cmbCountry.getSelectedItem().equals("PHILIPPINES (REPUBLIC OF THE)")){
									String province_name = (String) ((JComboBox)arg0.getSource()).getSelectedItem();
									String province_id = getProvinceID(province_name);

									mapCityMunicipality(province_id);
									cmbMunicipality.setModel(new DefaultComboBoxModel(listCityMunicipality(province_id)));
									
								}
							}
						});
					}
					{
						lblNoBlockLot = new JLabel("No. / Blk-Lot", JLabel.TRAILING);
						pnlAddressDetails.add(lblNoBlockLot);
					}
					{
						txtNoBlockLot = new JXTextField();
						pnlAddressDetails.add(txtNoBlockLot);
						txtNoBlockLot.addKeyListener(new KeyAdapter() {
							
							public void keyTyped(KeyEvent e){
								
								char c = e.getKeyChar();
								
								System.out.printf("Display character typed: %s%n", c);
								
								char[] invalidChars = "[$&+,:;=?@#|'<>.-^*()%!]".toCharArray();
								
								for(int x = 0; x < invalidChars.length; x++) {
									if(c == invalidChars[x]) {
										System.out.printf("Display matching invalid character: %s%n", invalidChars[x]);
										e.consume();
									}
								}
							}
						});
					}
					{
						lblStreetSubd = new JLabel("*Street / Subd.", JLabel.TRAILING);
						pnlAddressDetails.add(lblStreetSubd);
					}
					{
						txtStreetSubd = new JXTextField();
						pnlAddressDetails.add(txtStreetSubd);
						txtStreetSubd.addKeyListener(new KeyAdapter() {
							
							public void keyTyped(KeyEvent e){
								
								char c = e.getKeyChar();
								
								char[] invalidChars = "[$&+,:;=?@#|'<>.-^*()%!]".toCharArray();
								
								for(int x = 0; x<invalidChars.length; x++){
									if(c== invalidChars[x]){
										e.consume();
									}
								}
							}
						});
					}
					{
						lblDistrictTown = new JLabel("*District / Barangay", JLabel.TRAILING);
						pnlAddressDetails.add(lblDistrictTown);
					}
					{
						txtDistrictTown = new JXTextField();
						pnlAddressDetails.add(txtDistrictTown);
					}
					{
						lblCityProvince = new JLabel("City/Municipality", JLabel.TRAILING);
						pnlAddressDetails.add(lblCityProvince);
					}
					{
						cmbMunicipality = new JComboBox();
						pnlAddressDetails.add(cmbMunicipality);
						cmbMunicipality.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(cmbCountry.getSelectedItem().equals("PHILIPPINES (REPUBLIC OF THE)")){
									String province_name = (String) cmbProvince.getSelectedItem();
									String province_id = getProvinceID(province_name);
									
									String city_municipality = (String) cmbMunicipality.getSelectedItem();
									String city_municipality_id = getCityMunicipalityID(province_id, city_municipality);
									
									String district_brgy = txtDistrictTown.getText();
									
									try{
										cmbZipCode.setModel(new DefaultComboBoxModel(getZipCode(province_id, city_municipality_id ,district_brgy)));
									}catch (NullPointerException a){
										cmbZipCode.setModel(new DefaultComboBoxModel());
									}
								}
							}
						});
					}
					{
						lblZipCode = new JLabel("Zip Code", JLabel.TRAILING);
						pnlAddressDetails.add(lblZipCode);
					}
					{
						cmbZipCode = new JComboBox();
						pnlAddressDetails.add(cmbZipCode);
						cmbZipCode.setEnabled(false);
					}
					SpringUtilities.makeCompactGrid(pnlAddressDetails, 7, 2, 5, 5, 5, 5, false);
				}
				{
					pnlLocationSketch = new JPanel();
					pnlNorth.add(pnlLocationSketch, BorderLayout.EAST);
					pnlLocationSketch.setLayout(new BorderLayout());
					pnlLocationSketch.setBorder(JTBorderFactory.createTitleBorder("Location Sketch"));
					{
						lblLocationSketch = new JLabel();
						pnlLocationSketch.add(lblLocationSketch, BorderLayout.CENTER);
						lblLocationSketch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

						Image img = new ImageIcon(( this.getClass().getClassLoader().getResource( String.valueOf( "Images/Question_Mark-300x236.jpg" ) ))).getImage() ;
						img = img.getScaledInstance( ImageObserver.WIDTH, ImageObserver.HEIGHT, Image.SCALE_SMOOTH );
						lblLocationSketch.setIcon(new ImageIcon(img));
					}
				}
				SpringUtilities.makeCompactGrid(pnlNorth, 1, 2, 5, 5, 5, 5, false);
			}
			{
				pnlCenter = new JPanel(new GridLayout(1, 3, 1, 1));
				pnlUpper.add(pnlCenter, BorderLayout.SOUTH);
				//pnlCenter.setPreferredSize(new Dimension(786, 188));
				{
					pnlAddress = new JPanel(new GridLayout(4, 1, 1, 1));
					pnlCenter.add(pnlAddress, BorderLayout.WEST);
					pnlAddress.setBorder(components.JTBorderFactory.createTitleBorder("Address Status Info"));
					{
						pnlAddressType = new JPanel();
						pnlAddress.add(pnlAddressType);
						pnlAddressType.setLayout(new SpringLayout());
						{
							lblAddressType = new JLabel();
							pnlAddressType.add(lblAddressType);
							lblAddressType.setText("Address Type");
							{
								cmbAddressType = new JComboBox();
								pnlAddressType.add(cmbAddressType);
								cmbAddressType.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										//String address_id = (String) ((JComboBox)arg0.getSource()).getSelectedItem();
										//String city_id = getAddressID(address_id);
										//mapAddressType(address_id);
									}
								});
							}
						}
						SpringUtilities.makeCompactGrid(pnlAddressType, 1, 2, 3, 3, 3, 3, false);
					}
					{
						pnlRecStatus = new JPanel();
						pnlAddress.add(pnlRecStatus);
						pnlRecStatus.setLayout(new SpringLayout());
						{
							lblRecStatus = new JLabel("Rec. Status");
							pnlRecStatus.add(lblRecStatus);
						}
						{
							ComboBoxModel cmbRecStat = 	new DefaultComboBoxModel(
											new String[] { "Active", "Inactive" });
							cmbRecStatus = new JComboBox();
							pnlRecStatus.add(cmbRecStatus);
							cmbRecStatus.setModel(cmbRecStat);
						}
						SpringUtilities.makeCompactGrid(pnlRecStatus, 1, 2, 3, 3, 3, 3, false);
					}
					{
						JPanel pnlPreferredCTSAdd = new JPanel();
						pnlAddress.add(pnlPreferredCTSAdd);
						{
							/*chkPreferredCTSAdd = new JCheckBox("Permanent Address");
							pnlPreferredCTSAdd.add(chkPreferredCTSAdd);*/
							chkPreferredCTSAdd = new JCheckBox("Permanent Address");
							pnlPreferredCTSAdd.add(chkPreferredCTSAdd);
						}
					}
					{
						pnlPreferredMailAdd = new JPanel();
						pnlAddress.add(pnlPreferredMailAdd);
						{
							/*chkPreferredMailingAdd = new JCheckBox("Preferred Mailing Address");
							pnlPreferredMailAdd.add(chkPreferredMailingAdd);*/
							chkPreferredMailingAdd = new JCheckBox("Preferred Mailing Address");
							pnlPreferredMailAdd.add(chkPreferredMailingAdd);
							//chkPreferredMailingAdd.setEnabled(false);
						}
					}
					
				}
				{
					pnlOwnershipInfo = new JPanel(new GridLayout(4, 1, 1, 1));
					pnlCenter.add(pnlOwnershipInfo);
					pnlOwnershipInfo.setBorder(components.JTBorderFactory.createTitleBorder("Ownership Info"));
					{
						pnlOwnership = new JPanel();
						pnlOwnershipInfo.add(pnlOwnership);
						pnlOwnership.setLayout(new SpringLayout());
						{
							{
								lblOwnership = new JLabel("Ownership");
								pnlOwnership.add(lblOwnership);
							}
							{
								cmbOwnership = new JComboBox(getOwnershipType());
								pnlOwnership.add(cmbOwnership);
								cmbOwnership.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent arg0) {
										
										txtRentAmount.setEnabled(cmbOwnership.getSelectedItem().toString().equals("Rented"));
										
									}
								});
							}
						}
						
						SpringUtilities.makeCompactGrid(pnlOwnership, 1, 2, 1, 1, 1, 1, false);
					}
					{
						pnlRentAmount = new JPanel();
						pnlOwnershipInfo.add(pnlRentAmount);
						pnlRentAmount.setLayout(new SpringLayout());
						{
							lblRentAmount = new JLabel("Rent Amount");
							pnlRentAmount.add(lblRentAmount);
						}
						{
							txtRentAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlRentAmount.add(txtRentAmount);
							txtRentAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtRentAmount.setText("0.00");
						}
						SpringUtilities.makeCompactGrid(pnlRentAmount, 1, 2, 1, 1, 1, 1, false);
					}
					{
						JPanel pnlYearsStay = new JPanel(new BorderLayout(3, 3));
						pnlOwnershipInfo.add(pnlYearsStay);
						{
							JLabel lblYearsStay = new JLabel("Years of Stay", JLabel.TRAILING);
							pnlYearsStay.add(lblYearsStay);
						}
						{
							spinnerYearStay = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
							pnlYearsStay.add(spinnerYearStay, BorderLayout.EAST);
							spinnerYearStay.setPreferredSize(new Dimension(50, 0));
						}
					}
					{
						JPanel pnlMonthsStay = new JPanel(new BorderLayout(3, 3));
						pnlOwnershipInfo.add(pnlMonthsStay);
						{
							JLabel lblMonthsStay = new JLabel("Months of Stay", JLabel.TRAILING);
							pnlMonthsStay.add(lblMonthsStay);
						}
						{
							spinnerMonthsStay = new JSpinner(new SpinnerNumberModel(0, 0, 12, 1));
							pnlMonthsStay.add(spinnerMonthsStay, BorderLayout.EAST);
							spinnerMonthsStay.setPreferredSize(new Dimension(50, 0));
						}
					}
					/*{
						lblLengthOfStay = new JLabel("Length of Stay(From/To)");
						pnlOwnershipInfo.add(lblLengthOfStay);
					}
					{
						pnldateFromTo = new JPanel();
						pnlOwnershipInfo.add(pnldateFromTo);
						pnldateFromTo.setLayout(new SpringLayout());
						{
							dateFrom = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnldateFromTo.add(dateFrom);
						}
						{
							dateTo = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnldateFromTo.add(dateTo);
						}
						SpringUtilities.makeCompactGrid(pnldateFromTo, 1, 2, 1, 1, 1, 1, false);
					}*/
				}
				{
					pnlRTS = new JPanel(new GridLayout(4, 1, 5, 5));
					pnlCenter.add(pnlRTS, BorderLayout.EAST);
					pnlRTS.setBorder(components.JTBorderFactory.createTitleBorder("RTS Info"));
					//pnlRTS.setPreferredSize(new Dimension(234, 170));
					{
						lblDateRTS = new JLabel("RTS Date");
						pnlRTS.add(lblDateRTS);
					}
					{
						dateRTS = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlRTS.add(dateRTS);
					}
					{
						lblReturnReason = new JLabel("Return to Sender Reason");
						pnlRTS.add(lblReturnReason);
					}
					{
						txtReturnReason = new JTextField();
						pnlRTS.add(txtReturnReason);
					}
				}
			}
			//SpringUtilities.makeCompactGrid(pnlUpper, 2, 1, 5, 5, 5, 5, false);
		}
		{
			scrollAddress = new JScrollPane();
			this.add(scrollAddress, BorderLayout.CENTER);
			scrollAddress.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelAdd = new modelAddress();
				//modelAdd.set

				tblAddress = new _JTableMain(modelAdd);
				scrollAddress.setViewportView(tblAddress);
				tblAddress.hideColumns("Rec. ID");
				tblAddress.setSortable(false);
				//tblAddress.setSelectionMode(1);
				tblAddress.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				//Process after row add in tables
				modelAdd.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {

						((DefaultListModel)rowHeaderAddress.getModel()).addElement(modelAdd.getRowCount());

						if(modelAdd.getRowCount() == 0){
							rowHeaderAddress.setModel(new DefaultListModel());
						}
					}
				});
				
				tblAddress.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if (!arg0.getValueIsAdjusting()){
							try {
								if(tblAddress.getSelectedRows().length ==1){
									int row = tblAddress.getSelectedRow();

									String BlkLotNo = (String) modelAdd.getValueAt(row, 1);
									String Street_Sub = (String) modelAdd.getValueAt(row, 2);
									String district_town = (String) modelAdd.getValueAt(row, 3);
									String city_municipality = (String) modelAdd.getValueAt(row, 4);
									String province = (String) modelAdd.getValueAt(row, 5);
									String country = (String) modelAdd.getValueAt(row, 6);
									String zip_code_dtl = (String) modelAdd.getValueAt(row, 7);
									String address_type = (String) modelAdd.getValueAt(row, 8);
									String rec_status = (String) modelAdd.getValueAt(row, 9);
									Boolean pref_mail_add = (Boolean) modelAdd.getValueAt(row, 10);
									Boolean pref_cts_add = (Boolean) modelAdd.getValueAt(row, 11);
									String ownership_desc = (String) modelAdd.getValueAt(row, 12);
									BigDecimal rent_amt = (BigDecimal) modelAdd.getValueAt(row, 13);
									Integer years_stay = (Integer) modelAdd.getValueAt(row, 14);
									Integer months_stay = (Integer) modelAdd.getValueAt(row, 15);
									/*Date dteFrom = (Date) modelAdd.getValueAt(row, 13);
									Date dteTo = (Date) modelAdd.getValueAt(row, 14);*/

									txtNoBlockLot.setText(BlkLotNo);
									txtStreetSubd.setText(Street_Sub);
									txtDistrictTown.setText(district_town);
									
									
									cmbAddressType.setSelectedItem(address_type);
									chkPreferredMailingAdd.setSelected(pref_mail_add);
									chkPreferredCTSAdd.setSelected(pref_cts_add);
									cmbOwnership.setSelectedItem(ownership_desc);
									spinnerYearStay.setValue(years_stay);
									spinnerMonthsStay.setValue(months_stay);
									/*dateFrom.setDate(dteFrom);
									dateTo.setDate(dteTo);*/
									cmbCountry.setSelectedItem(country);

									if(rec_status.equals("I")){
										cmbRecStatus.setSelectedItem("Inactive");
										ci.btnState(true, true, false, false, false);
									}else{
										cmbRecStatus.setSelectedItem("Active");
										ci.btnState(true, true, true, false, false);
									}

									if(rent_amt == null){
										txtRentAmount.setText("0.00");
									}else{
										txtRentAmount.setValue(rent_amt);
										txtRentAmount.setEnabled(false);
									}

									if(province == null){
										cmbProvince.setSelectedItem("NONE");
										cmbMunicipality.setSelectedItem(city_municipality);
									}else{
										cmbProvince.setSelectedItem(province);
										cmbMunicipality.setSelectedItem(city_municipality);
									}
									
									System.out.printf("Display zip_code detail: %s%n", zip_code_dtl);
									
									cmbZipCode.setSelectedItem(zip_code_dtl);
									
									if(ci.canEdit() == false){
										ci.btnState(false, false, false, false, false);
									}
									
								}
							} catch (ArrayIndexOutOfBoundsException e) { }
						}
					}
				});
			}
			{
				rowHeaderAddress = tblAddress.getRowHeader();
				rowHeaderAddress.setModel(new DefaultListModel());
				scrollAddress.setRowHeaderView(rowHeaderAddress);
				scrollAddress.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}

		ci.setComponentsEditable(pnlUpper, false);
		setComponentsEnabled(false, false, false, false, false, false, false, false, false, false, false);

		mapProvinces();
		cmbProvince.setModel(new DefaultComboBoxModel(listProvince()));

		mapCityMunicipality("NONE");
		cmbMunicipality.setModel(new DefaultComboBoxModel(listCityMunicipality("NONE")));

		mapAddressType();
		cmbAddressType.setModel(new DefaultComboBoxModel(listAddressType()));

		mapCountries();
		cmbCountry.setModel(new DefaultComboBoxModel(listCountries()));
		cmbCountry.setSelectedIndex(172);

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbCountry, cmbProvince, txtNoBlockLot, txtStreetSubd, txtDistrictTown, cmbMunicipality,
				txtZipCode, cmbAddressType, cmbRecStatus, chkPreferredMailingAdd, cmbOwnership, txtRentAmount, dateFrom, dateTo, dateRTS, txtReturnReason));

	}//XXX End of initGUI

	private void setComponentsEnabled(Boolean country, Boolean province, Boolean city_mun, Boolean add_type, Boolean rec_stat, 
			Boolean  pref_mail,Boolean pref_cts , Boolean own_type , Boolean years,Boolean months ,Boolean rts){
		cmbCountry.setEnabled(country);
		cmbProvince.setEnabled(province);
		cmbMunicipality.setEnabled(city_mun);
		cmbAddressType.setEnabled(add_type);
		cmbRecStatus.setEnabled(rec_stat);
		chkPreferredMailingAdd.setEnabled(pref_mail);
		chkPreferredCTSAdd.setEnabled(pref_cts);
		cmbOwnership.setEnabled(own_type);
		spinnerYearStay.setEnabled(years);
		spinnerMonthsStay.setEnabled(months);
		/*dateFrom.setEnabled(from);
		dateTo.setEnabled(to);*/
		dateRTS.setEnabled(rts);	
	}

	private LinkedHashMap<String, String>mapCountries(){ //Hashmap for Countries
		LinkedHashMap<String, String> mapCountries = new LinkedHashMap<String, String>();

		String sql = "select trim(country_id), trim(country_name) from mf_country";
		pgSelect db = new pgSelect();
		db.select(sql);
		//FncSystem.out("Display sql for Countries", sql);
		if(db.isNotNull()){
			for(int x = 0; x < db.getRowCount(); x++){
				String country_code = (String) db.getResult()[x][0];
				String country_name = (String) db.getResult()[x][1];
				mapCountries.put(country_code, country_name);
			}
			return mapCountries;
		}else{
			return null;
		}
	}
	private Object[] listCountries(){
		ArrayList<String> arrayCountries = new ArrayList<String>();

		for(Entry<String, String> entry : mapCountries().entrySet()) {
			//String country_code = entry.getKey();
			String country_name = entry.getValue();

			arrayCountries.add(country_name);
			//System.out.printf("%s - %s%n", province_id, province_name);
		}
		return arrayCountries.toArray();
	}
	
	private LinkedHashMap<String, String> mapProvinces() {//HASHMAP FOR THE PROVINCES
		LinkedHashMap<String, String> mapProvince = new LinkedHashMap<String, String>();

		String sql = "select trim(province_id), trim(province_name) from mf_province where status_id = 'A' order by province_name;";
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x = 0; x < db.getRowCount(); x++){
				String province_id = (String) db.getResult()[x][0];
				String province_name = (String) db.getResult()[x][1];

				mapProvince.put(province_id, province_name);
			}
			return mapProvince;
		}else{
			return null;
		}
	}

	private Object[] listProvince() {
		ArrayList<String> arrayProvince = new ArrayList<String>();
		arrayProvince.add("NONE");

		for(Entry<String, String> entry : mapProvinces().entrySet()) {
			//String province_id = entry.getKey();
			String province_name = entry.getValue();

			arrayProvince.add(province_name);

			//System.out.printf("%s - %s%n", province_id, province_name);
		}

		return arrayProvince.toArray();
	}

	private String getProvinceID(String province_name) {
		String province_id = "";
		for(Entry<String, String> entry : mapProvinces().entrySet()){
			if(entry.getValue().equals(province_name)){
				province_id = entry.getKey();
			}
		}
		
		if(province_name.equals("NONE") || province_name == null){
			System.out.printf("Display province name %s%n", province_name);
			System.out.printf("Display Province ID %s%n", province_id);
			return province_name;

		}else{
			return province_id;
		}
	}
	
	private LinkedHashMap<String, String> mapCityMunicipality(String province_id){
		LinkedHashMap<String, String>mapCM = new LinkedHashMap<String, String>();

		String sql = "select TRIM(a.city_id) AS city_id, TRIM(a.city_name) as city_name, TRIM(a.province_id) AS province_id, TRIM(b.province_name) AS province_name, a.cm\n" + 
					 "from (select city_id, city_name, province_id, 'City' as cm from mf_city WHERE status_id = 'A'\n" + 
					 "union\n" + 
					 "select municipality_id, municipality_name, province_id, 'Municipality' from mf_municipality WHERE status_id = 'A') a\n" + 
					 "left join mf_province b on b.province_id = a.province_id\n" + 
					 "where "+ (province_id.equals("NONE") ? "a.province_id is null":"a.province_id = '"+ province_id +"'") +"\n" + 
					 "order by city_name;";

		FncSystem.out("Display HashMap", sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x = 0; x < db.getRowCount(); x++){
				String city_id = (String) db.getResult()[x][0];
				String city_name = (String) db.getResult()[x][1];

				mapCM.put(city_id, city_name);
			}
			return mapCM;
		}else{
			return null;
		}
	}

	private String getCityMunicipalityID(String province_id, String city_name) {
		String city_id = "";
		try {
			for(Entry<String, String> entry : mapCityMunicipality(province_id).entrySet()){
				if(entry.getValue().equals(city_name)){
					city_id = entry.getKey();
				}
			}
		} catch (Exception e) { }
		return city_id;
	}

	private Object[] listCityMunicipality(String province_id) {
		ArrayList<String> arrayCM = new ArrayList<String>();

		try {
			for(Entry<String, String> entry : mapCityMunicipality(province_id).entrySet()) {
				//String city_id = entry.getKey();
				String city_name = entry.getValue();

				arrayCM.add(city_name.trim());
				//System.out.printf("%s - %s%n", city_id, city_name);
			}
		} catch (NullPointerException e) { }

		return arrayCM.toArray();
	}

	private LinkedHashMap<String, String> mapAddressType(){//MAP FOR THE  ADDRESS TYPE
		LinkedHashMap<String, String>mapAddType = new LinkedHashMap<String, String>();

		String sql = "select * from mf_address_type where status_id = 'A';";
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x = 0; x < db.getRowCount(); x++){
				String address_id = (String) db.getResult()[x][0];
				String address_desc = (String) db.getResult()[x][1];

				mapAddType.put(address_id, address_desc);
			}
			return mapAddType;
		}else{
			return null;
		}
	}

	private Object[] listAddressType() {
		ArrayList<String> arrayAddType = new ArrayList<String>();

		for(Entry<String, String> entry : mapAddressType().entrySet()) {
			//String address_id = entry.getKey();
			String address_desc = entry.getValue();

			arrayAddType.add(address_desc);
			//System.out.printf("%s - %s%n", city_id, city_name);
		}
		return arrayAddType.toArray();
	}

	private Object[] getOwnershipType() {
		ArrayList<Object> ownership_type = new ArrayList<Object>();

		pgSelect db = new pgSelect();
		db.select("select trim(ownership_desc) from mf_home_ownership_type;");
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				ownership_type.add(db.getResult()[x][0]);
			}
		}
		return ownership_type.toArray();
	}
	
	private String [] getZipCode(String province_id, String city_municipality_id ,String district_town){
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT FORMAT('%s - %s', TRIM(a.zip_code), TRIM(a.branch_name))\n" + 
					 "FROM mf_zip_codes a\n" + 
					 "WHERE (case when '"+province_id+"' = 'NONE' THEN a.province_id is null else province_id = '"+province_id+"' END)\n" + 
					 "AND (CASE WHEN EXISTS (SELECT * FROM mf_city where city_id = '"+city_municipality_id+"') then a.city_id = '"+city_municipality_id+"' \n" + 
					 "	  WHEN EXISTS (SELECT * FROM mf_municipality where municipality_id = '"+city_municipality_id+"') THEN a.municipality_id = '"+city_municipality_id+"'\n" + 
					 "	  END )\n"+
					 "AND a.status_id = 'A' \n" + 
					 "ORDER BY a.branch_name;";
		
		/*String SQL = "SELECT FORMAT('%s - %s', TRIM(b.zip_code), TRIM(b.branch_name)) FROM (select DISTINCT on (a.zip_code) a.*  \n" + 
					 "FROM mf_zip_codes a\n" + 
					 "WHERE (case when '"+province_id+"' = 'NONE' THEN a.province_id is null else province_id = '"+province_id+"' END)\n" + 
					 "AND (CASE WHEN EXISTS (SELECT * FROM mf_city where city_id = '"+city_municipality_id+"') then a.city_id = '"+city_municipality_id+"' \n" + 
					 "	  WHEN EXISTS (SELECT * FROM mf_municipality where municipality_id = '"+city_municipality_id+"') THEN a.municipality_id = '"+city_municipality_id+"'\n" + 
					 "	  END)\n" + 
					 "	  order by a.zip_code) b\n" + 
					 "ORDER BY b.branch_name;";*/
		
		db.select(SQL);
		
		//FncSystem.out("Display SQL For Zip Code", SQL);
		
		if(db.isNotNull()){
			String [] zip_code = new String[db.getRowCount()];
			
			for(int x = 0; x<db.getRowCount(); x++){
				zip_code[x] = (String) db.getResult()[x][0];
			}
			return zip_code;
		}else{
			return null;
		}
	}
	
	public boolean toSave() {
		/*if(txtNoBlockLot.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input No/Blk-Lot"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		if(txtStreetSubd.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Street/Subd"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtDistrictTown.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input District/Town"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if(cmbMunicipality.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input City/Municipality"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		return true;
	}
	
	private boolean isPreferredMailAdd(String entity_id){//CHECKS IF THERE IS ALREADY A PREF. MAILING ADDRESS
		pgSelect db = new pgSelect();
		db.select("Select * from rf_entity_address \n"+
				"where entity_id = '"+entity_id+"' \n"+
				"and pref_billing = true \n"+
				"and status_id = 'A'");

		return db.isNotNull();
	}

	private boolean isAddressPreferredMailing(String entity_id, String address_no, String street, String barangay){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_entity_address \n"+
				"where entity_id = '"+entity_id+"' \n"+
				"and addr_no = '"+address_no+"'"+
				"and street = '"+street+"' \n"+
				"and barangay = '"+barangay+"' \n"+
				"and pref_billing = true";
		db.select(sql);
		
		FncSystem.out("Display SQL For Pref Mailing", sql);
		return db.isNotNull();
	}

	private boolean isAddressPreferredCTS(String entity_id, String address_no, String street, String barangay){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_entity_address \n"+
				"where entity_id = '"+entity_id+"' \n"+
				"and addr_no = '"+address_no+"'"+
				"and street = '"+street+"' \n"+
				"and barangay = '"+barangay+"' \n"+
				"and pref_cts_address = true \n"+
				"and status_id = 'A'";
		db.select(sql);
		FncSystem.out("Display if is address the preferred", sql);
		return db.isNotNull();
	}

	private boolean isPreferredCTSAdd(String entity_id){//CHECKS WHETHER THERE IS ALREADY A PREFERRED CTS ADDRESS
		pgSelect db = new pgSelect();
		db.select("Select * from rf_entity_address " + 
				"where entity_id = '"+entity_id+"' " + 
				"and pref_cts_address = true \n"+
				"and status_id = 'A'");
		return db.isNotNull();
	}

	private boolean isAddressExist(String entity_id,String address_no, String street, String barangay) {//CHECKS WHETHER THE ADDRESS IS ALREADY EXISTING

		pgSelect db = new pgSelect();
		String sql = "select * from rf_entity_address "
				+ "where entity_id = '"+ entity_id +"'"
				+ "and addr_no = '"+address_no+"' "
				+ "and street = '"+street+"' "
				+ "and barangay = '"+barangay+"'";
		
		db.select(sql);
		
		FncSystem.out("Display address existing", sql);
		
		return db.isNotNull();
	}

	private boolean isCity(String city_id){//CHECKS IF CITY
		pgSelect db = new pgSelect();
		String sql = "select * from mf_city where city_id = '"+city_id+"'";
		db.select(sql);
		FncSystem.out("DIsplay is CIty", sql);
		return db.isNotNull();
	}

	private boolean isMunicipality(String mun_id){//CHECKS IF MUNICIPALITY
		pgSelect db = new pgSelect();
		String sql = "select * from mf_municipality where municipality_id = '"+mun_id+"'";
		db.select(sql);
		FncSystem.out("Display is Municipality", sql);
		return db.isNotNull();
	}

	public void displayAddressList(String entity_id){//DISPLAYS THE DATA IN THE ADDRESS PANEL BASED ON THE SELECTED ENTITY ID
		modelAdd.clear();

		String sql = "select trim(a.addr_no) as addr_no, trim(a.street) as street, trim(a.barangay) as barangay," +
					 "trim(coalesce(b.city_name, g.municipality_name)) as city_municipality_name, trim(d.province_name) as province_name, trim(e.country_name) as country_name, \n"+
					 "NULLIF(FORMAT('%s - %s', a.zip_code, h.branch_name), ' - '), trim(c.addr_desc) as addr_desc, a.status_id, \n" + 
					 "a.pref_billing, a.pref_cts_address,trim(f.ownership_desc),a.rent_amount, a.stay_from , a.stay_to, null, null \n" + 
					 "from rf_entity_address a \n" + 
					 "left join mf_city b on b.city_id = a.city_id \n" + 
					 "left join mf_address_type c on c.addr_type_id = a.addr_type \n" +
					 "left join mf_province d on d.province_id = a.province_id \n"+
					 "left join mf_country e on e.country_id = a.country_id \n"+
					 "left join mf_home_ownership_type f on f.ownership_id = a.ownership_id \n"+
					 "left join mf_municipality g on g.municipality_id = a.municipality_id \n"+
					 "LEFT JOIN mf_zip_codes h on h.zip_code = a.zip_code \n"+
					 "where a.entity_id = '"+entity_id+"' \n"+
					 "and a.country_id is not null";
						//"and a.status_id = 'A'";
		
		String SQL = "SELECT * FROM view_ci_address('"+entity_id+"')";

		FncSystem.out("Display Address List", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelAdd.addRow(db.getResult()[x]);
			}
			scrollAddress.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAddress.getRowCount())));
			tblAddress.packAll();
		}
	}
	
	public void displayAddress(String entity_id){
		modelAdd.clear();
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM view_ci_address_v2('"+entity_id+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For Address List", SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelAdd.addRow(db.getResult()[x]);
			}
			scrollAddress.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAddress.getRowCount())));
			tblAddress.packAll();
		}
	}
	
	public void newAddress(){//NEW ADDRESS
		/*ci.btnState(false, false, false, true, true);
		ci.setComponentsEnabled(pnlNorth, true);
		ci.setComponentsEnabled(pnlAddress, true);
		ci.setComponentsEnabled(pnldateFromTo, true);
		ci.setComponentsEnabled(pnlOwnership, true);
		ci.setComponentsEnabled(pnlRTS, true);*/
		ci.setComponentsEditable(pnlUpper, true);
		setComponentsEnabled(true, true, true, true, true, true, true, true, true, true, true);
		cmbZipCode.setEnabled(true);
		clearAddressFields();
		tblAddress.clearSelection();
		tblAddress.setEnabled(false);
		rowHeaderAddress.setEnabled(false);
	}
	
	public void editAddress(){//EDITING OF ADDRESS
		if (tblAddress.getSelectedRows().length == 1){
			if(cmbOwnership.getSelectedItem().equals("Rented")){
				txtRentAmount.setEnabled(true);
				//ci.btnState(false, false, false, true, true);f
			}else{
				txtRentAmount.setEnabled(false);
				txtRentAmount.setText("0.00");
				//ci.btnState(false, false, false, true, true);
			}
			//ci.btnState(false, false, false, true, true);
			ci.setComponentsEditable(pnlUpper, true);
			setComponentsEnabled(true, true, true, true, true, true, true, true, true, true, true);
			cmbZipCode.setEnabled(true);
			tblAddress.setEnabled(false);
			rowHeaderAddress.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollAddress, "Please select only one row to edit");
			tblAddress.clearSelection();
			ci.btnState(true, false, false, false, false);
		}
	}

	public boolean saveAddress(String entity_id){ //TODO FIND A TABLE TO INSERT RTS DATE AND RTS TO 
		//tblAddress.clearSelection();
		pgUpdate db = new pgUpdate();

		String country_name = (String) cmbCountry.getSelectedItem();
		String province_id = getProvinceID((String)cmbProvince.getSelectedItem());
		String address_no = txtNoBlockLot.getText().trim().replace("'", "''");
		String street = txtStreetSubd.getText().trim().replace("'", "''");
		String barangay = txtDistrictTown.getText().trim().replace("'", "''");
		
		String zip_code = "";
		
		if(country_name.equals("PHILIPPINES (REPUBLIC OF THE)")){
			zip_code = ((String) cmbZipCode.getSelectedItem()).split("-")[0].trim();
		}else{
			zip_code = "";
		};
		
		//String zip_code = txtZipCode.getText().trim();
		
		String add_type = (String) cmbAddressType.getSelectedItem();
		String ownership_type = (String) cmbOwnership.getSelectedItem();
		Boolean prefmailadd = chkPreferredMailingAdd.isSelected();
		Boolean prefcts = chkPreferredCTSAdd.isSelected();
		BigDecimal rent_amnt = (BigDecimal) txtRentAmount.getValued();
		//rent_amnt.round(new MathContext(2));
		/*Date stay_from = dateFrom.getDate();
		Date stay_to = dateTo.getDate();*/
		String rec_status = (String) cmbRecStatus.getSelectedItem();
		Integer years_of_stay = (Integer) spinnerYearStay.getValue();
		Integer months_stay = (Integer) spinnerMonthsStay.getValue();

		//Date rts_date = dateRTS.getDate();
		//String rts = txtReturnReason.getText();
		String cit_mun = getCityMunicipalityID(province_id, (String) cmbMunicipality.getSelectedItem());
		String city_id = "";
		//System.out.printf("DIsplay city_mun %s%n", cit_mun);
		String municipality_id = "";

		if(isCity(getCityMunicipalityID(province_id, (String) cmbMunicipality.getSelectedItem()))){

			city_id = getCityMunicipalityID(province_id, (String) cmbMunicipality.getSelectedItem());
			municipality_id = "";
		}
		if(isMunicipality(getCityMunicipalityID(province_id, (String) cmbMunicipality.getSelectedItem()))){
			municipality_id = getCityMunicipalityID(province_id, (String) cmbMunicipality.getSelectedItem());
			city_id = "";
		}
		if(isCity(cit_mun) == false && isMunicipality(cit_mun) == false){
			city_id = "";
			municipality_id = "";
		}
		
		System.out.printf("Display city_id %s%n", city_id);
		System.out.printf("Display mun_id %s%n", municipality_id);
		//UPDATE 
		if(tblAddress.getSelectedRows().length ==1){
			int row = tblAddress.getSelectedRow();
			String prev_addr_no = (String) modelAdd.getValueAt(row, 0);
			String prev_Street_Sub = (String) modelAdd.getValueAt(row, 1);
			String prev_district_town = (String) modelAdd.getValueAt(row, 2);

			if (isAddressExist(entity_id, prev_addr_no, prev_Street_Sub,prev_district_town)){ 
				/*if(isAddressPreferredMailing(entity_id, prev_addr_no, prev_Street_Sub, prev_district_town)){
					String sql = "update rf_entity_address set addr_type = (select addr_type_id from mf_address_type where addr_desc = '"+ add_type +"'), addr_no = '"+ address_no +"', province_id = NULLIF('"+ province_id +"', 'NONE') ,"+
							"city_id = '"+ city_id +"', municipality_id = '"+municipality_id+"' ,street = '"+ street +"', barangay = '"+ barangay +"', zip_code = '"+zip_code+"', "+
							"country_id =  (select country_id from mf_country where country_name = '"+ country_name +"'), "+
							"stay_from = nullif('"+ stay_from +"', 'null')::TIMESTAMP, stay_to = NULLIF('"+stay_to+"', 'null')::TIMESTAMP ,status_id = (case when '"+ rec_status +"' = 'Active' then 'A' else 'I' end), pref_billing = '" + prefmailadd +"', "+
							"ownership_id = (select ownership_id from mf_home_ownership_type where ownership_desc = '"+ownership_type+"'), "+
							"rent_amount = (case when '"+ownership_type+"' = 'Rented' then "+ rent_amnt +" else 0.00 end) , edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now(), pref_cts_address = '"+prefcts+"'" +
							"where entity_id = '"+ entity_id +"' and addr_no = '"+prev_addr_no+"' "+
							"and street = '"+prev_Street_Sub+"' and barangay = '"+prev_district_town+"'";
					db.executeUpdate(sql, true);
					System.out.println("Update ng Pref Mailing Address Successful");
				}else if (isPreferredMailAdd(entity_id) && chkPreferredMailingAdd.isSelected()){//DAPAT UNA 2
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("There is already a Preferred Mailing Address"), "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}

				if(isAddressPreferredCTS(entity_id, prev_addr_no, prev_Street_Sub, prev_district_town)){
					String sql = "update rf_entity_address set addr_type = (select addr_type_id from mf_address_type where addr_desc = '"+ add_type +"'), addr_no = '"+ address_no +"', province_id = NULLIF('"+ province_id +"', 'NONE') ,"+
							"city_id = '"+ city_id +"', municipality_id = '"+municipality_id+"' ,street = '"+ street +"', barangay = '"+ barangay +"', zip_code = '"+zip_code+"', "+
							"country_id =  (select country_id from mf_country where country_name = '"+ country_name +"'), "+
							"stay_from = nullif('"+ stay_from +"', 'null')::TIMESTAMP, stay_to = NULLIF('"+stay_to+"', 'null')::TIMESTAMP ,status_id = (case when '"+ rec_status +"' = 'Active' then 'A' else 'I' end), pref_billing = '" + prefmailadd +"', "+
							"ownership_id = (select ownership_id from mf_home_ownership_type where ownership_desc = '"+ownership_type+"'), "+
							"rent_amount = (case when '"+ownership_type+"' = 'Rented' then "+ rent_amnt +" else 0.00 end) , edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now(), pref_cts_address = '"+prefcts+"'" +
							"where entity_id = '"+ entity_id +"' and addr_no = '"+prev_addr_no+"' "+
							"and street = '"+prev_Street_Sub+"' and barangay = '"+prev_district_town+"'";
					db.executeUpdate(sql, true);
					System.out.println("Update ng Pref CTS Address Successful");
				}else if(isPreferredCTSAdd(entity_id) && chkPreferredCTSAdd.isSelected()){//DAPAT UNA 2
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("There is already a Preferred CTS Address"), "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}*/
				
				if(isPreferredCTSAdd(entity_id) && prefcts && isAddressPreferredCTS(entity_id, address_no, prev_Street_Sub, barangay) /*== false*/){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("There is already a Permanent Address"), "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(isPreferredMailAdd(entity_id) && prefmailadd && isAddressPreferredMailing(entity_id, address_no, prev_Street_Sub, barangay) /*== false*/){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("There is already a Preferred Mailing Address"), "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}else{
					System.out.println("Update address");
					
					String sql = "update rf_entity_address set addr_type = (select addr_type_id from mf_address_type where addr_desc = '"+ add_type +"'), addr_no = '"+ address_no +"', province_id = NULLIF('"+ province_id +"', 'NONE') ,"+
							"city_id = '"+ city_id +"', municipality_id = '"+municipality_id+"' ,street = '"+ street +"', barangay = '"+ barangay +"', zip_code = '"+zip_code+"', "+
							"country_id =  (select country_id from mf_country where country_name = '"+ country_name +"'), "+
							//"/*stay_from = nullif('"+ stay_from +"', 'null')::TIMESTAMP, stay_to = NULLIF('"+stay_to+"', 'null')::TIMESTAMP ,*/ "
							"status_id = (case when '"+ rec_status +"' = 'Active' then 'A' else 'I' end), pref_billing = '" + prefmailadd +"', "+
							"ownership_id = (select ownership_id from mf_home_ownership_type where ownership_desc = '"+ownership_type+"'), "+
							"rent_amount = (case when '"+ownership_type+"' = 'Rented' then "+ rent_amnt +" else 0.00 end) , edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now(), pref_cts_address = '"+prefcts+"', years_stay = "+years_of_stay+", months_stay = "+months_stay+"" +
							"where entity_id = '"+ entity_id +"' and addr_no = '"+prev_addr_no+"' "+
							"and street = '"+prev_Street_Sub+"' and barangay = '"+prev_district_town+"'";
					db.executeUpdate(sql, true);
				}
			}
		}else{//SAVING
			if(isPreferredMailAdd(entity_id) && chkPreferredMailingAdd.isSelected()){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("There is already a Preferred Mailing Address"), "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(isPreferredCTSAdd(entity_id) && chkPreferredCTSAdd.isSelected()){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("There is already a Preferred CTS Address"), "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			String sql = "INSERT INTO rf_entity_address(entity_id, addr_type, addr_no, street, barangay ,zip_code, city_id,municipality_id ,\n"+
					"province_id,country_id ,pref_billing,ownership_id, rent_amount, status_id, created_by, \n"+
					"date_created, pref_cts_address, years_stay, months_stay) \n" + 
					"VALUES ('"+entity_id+"', (select addr_type_id from mf_address_type where addr_desc = '"+ add_type +"'),'"+ address_no +"', \n"+
					"'"+street+"', '"+ barangay +"', nullif('"+ zip_code +"', ''),  '"+ city_id +"', '"+municipality_id+"', NULLIF('"+ province_id +"', 'NONE'), \n"+
					"(select country_id from mf_country where country_name = '"+ country_name +"') ,'"+ prefmailadd +"', \n"+
					"(select ownership_id from mf_home_ownership_type where ownership_desc = '"+ownership_type+"'), \n"+
					"(case when '"+ownership_type+"' = 'Rented' then "+ rent_amnt +" else 0.00 end), \n"+
					//"/*nullif('"+ stay_from +"', 'null')::TIMESTAMP, NULLIF('"+stay_to+"', 'null')::TIMESTAMP,*/ \n"+
					"(case when '"+ rec_status +"' = 'Active' then 'A' else 'I' end), '"+ UserInfo.EmployeeCode +"', now(), '"+prefcts+"', "+years_of_stay+", "+months_stay+");";
			db.executeUpdate(sql, true);
			//}
		}
		db.commit();
		ci.setComponentsEditable(pnlUpper, false);
		setComponentsEnabled(false, false, false, false, false, false, false, false, false, false, false);
		tblAddress.clearSelection();
		tblAddress.setEnabled(true);
		rowHeaderAddress.setEnabled(true);
		return true;
	}
	
	public boolean saveAddress2(String entity_id){
		String country_name = (String) cmbCountry.getSelectedItem();
		String province_id = getProvinceID((String)cmbProvince.getSelectedItem());
		String address_no = txtNoBlockLot.getText().trim().replace("'", "''");
		String street = txtStreetSubd.getText().trim().replace("'", "''");
		String barangay = txtDistrictTown.getText().trim().replace("'", "''");
		
		String zip_code = "";
		String zip_code_desc = "";
		Integer rec_id = null;
		
		if(country_name.equals("PHILIPPINES (REPUBLIC OF THE)")){
			zip_code = ((String) cmbZipCode.getSelectedItem()).split("-")[0].trim();
			zip_code_desc = ((String) cmbZipCode.getSelectedItem()).split("-")[1].trim().trim().replace("'", "''");
			//zip_code = (String) cmbZipCode.getSelectedItem();
		}else{
			zip_code = "";
			zip_code_desc = "";
		};
		
		//String zip_code = txtZipCode.getText().trim();
		
		String add_type = (String) cmbAddressType.getSelectedItem();
		String ownership_type = (String) cmbOwnership.getSelectedItem();
		Boolean prefmailadd = chkPreferredMailingAdd.isSelected();
		Boolean prefcts = chkPreferredCTSAdd.isSelected();
		BigDecimal rent_amnt = (BigDecimal) txtRentAmount.getValued();
		//rent_amnt.round(new MathContext(2));
		/*Date stay_from = dateFrom.getDate();
		Date stay_to = dateTo.getDate();*/
		String status = ((String) cmbRecStatus.getSelectedItem()).equals("Active") ? "A":"I";
		Integer years_of_stay = (Integer) spinnerYearStay.getValue();
		Integer months_stay = (Integer) spinnerMonthsStay.getValue();
		
		if(tblAddress.getSelectedRows().length == 1){
			int selected_row = tblAddress.convertRowIndexToModel(tblAddress.getSelectedRow());
			rec_id = (Integer) modelAdd.getValueAt(selected_row, 0);
		}

		//Date rts_date = dateRTS.getDate();
		//String rts = txtReturnReason.getText();
		String cit_mun = getCityMunicipalityID(province_id, (String) cmbMunicipality.getSelectedItem());
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT sp_save_ci_address('"+entity_id+"', "+rec_id+", '"+country_name+"', '"+province_id+"', '"+address_no+"', \n"+
					 "'"+street+"', '"+barangay+"', '"+cit_mun+"', '"+zip_code+"', '"+zip_code_desc+"' ,'"+add_type+"', '"+ownership_type+"', "+prefmailadd+", \n"+
					 ""+prefcts+", "+rent_amnt+", "+years_of_stay+", "+months_stay+", '"+status+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL, "Save", true);
		
		FncSystem.out("Display SQL for Saving of Address", SQL);
		
		if((boolean) db.getResult()[0][0]){
			ci.setComponentsEditable(pnlUpper, false);
			setComponentsEnabled(false, false, false, false, false, false, false, false, false, false, false);
			tblAddress.clearSelection();
			tblAddress.setEnabled(true);
			rowHeaderAddress.setEnabled(true);
		}
		
		return (boolean) db.getResult()[0][0];
	}

	public boolean deleteAddress(String entity_id){//DELETE ADDRESS
		pgUpdate db = new pgUpdate();

		if(tblAddress.getSelectedRows().length ==1){
			int row = tblAddress.getSelectedRow();
			String prev_addr_no = (String) modelAdd.getValueAt(row, 0);
			String prev_Street_Sub = (String) modelAdd.getValueAt(row, 1);
			String prev_district_town = (String) modelAdd.getValueAt(row, 2);

			if (isAddressExist(entity_id, prev_addr_no, prev_Street_Sub,prev_district_town)){ 
				String sql = "update rf_entity_address set status_id ='I', edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now()" +
							 "where entity_id = '"+ entity_id +"' and addr_no = '"+prev_addr_no+"' "+
							 "and street = '"+prev_Street_Sub+"' and barangay = '"+prev_district_town+"'";
				db.executeUpdate(sql, true);
			}
		}else{
			JOptionPane.showMessageDialog(scrollAddress, "Please select only one row");
			tblAddress.clearSelection();
		}
		db.commit();
		return true;
	}

	public void cancelAddress() {//CANCELATION FOR THE ADDRESS PANEL

		ci.setComponentsEditable(pnlUpper, false);
		setComponentsEnabled(false, false, false, false, false, false, false, false, false, false, false);
		cmbZipCode.setEnabled(false);
		clearAddressFields();
		tblAddress.clearSelection();
		tblAddress.setEnabled(true);
		rowHeaderAddress.setEnabled(true);
	}

	public void clearAddressFields(){ //CLEARS FIELDS FROM THE ADDRESS PANEL
		cmbCountry.setSelectedItem("");
		txtNoBlockLot.setText("");
		txtStreetSubd.setText("");
		txtDistrictTown.setText("");
		cmbProvince.setSelectedItem("NONE");
		cmbOwnership.setSelectedItem("Owned");
		cmbMunicipality.setSelectedItem("");
		cmbZipCode.setSelectedItem(null);
		//txtZipCode.setText("");
		txtRentAmount.setText("0.00");
		spinnerYearStay.setValue(0);
		spinnerMonthsStay.setValue(0);
		/*dateFrom.setText("");
		dateTo.setText("");*/
		cmbRecStatus.setSelectedIndex(0);
		chkPreferredMailingAdd.setSelected(false);
		chkPreferredCTSAdd.setSelected(false);
		chkPreferredMailingAdd.setSelected(false);
		tblAddress.clearSelection();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}

}