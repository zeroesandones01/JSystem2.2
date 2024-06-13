package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Dialogs.dlg_ImageViewer;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncImageFileChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelConnectionList;

/**
 * @author JOHN LESTER FATALLO
 */
public class pnlConnection extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = 2220185165276412832L;
	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JLabel lblConnectionName;
	private JLabel lblConnectionType;
	private JLabel lblRelation;
	private JLabel lblEmployer;
	private JLabel lblEmployerAddress;
	private JLabel lblUnit;
	private JLabel lblConnectionStatus;
	private JCheckBox chkEnabledUnit;

	private _JLookup lookupConnection;
	private _JLookup lookupConnectType;
	private _JLookup lookupRelation;
	private _JLookup lookupUnit;

	private JPanel pnlNorthCenter;
	private JTextField txtConnectionName;
	private JTextField txtConnectionType;
	private JTextField txtRelation;
	private JTextField txtEmployer;
	private JTextField txtEmployerAddress;
	private JTextField txtUnit;
	private JComboBox cmbConnectionStatus;
	protected FncImageFileChooser lblConnectionSignatureFileChooser;
	private _JLookup lookupCitizenship;
	private JTextField txtCitizenship;
	private JTextField txtConnectionMIDNo;
	private JTextField txtConnectionTINNo;
	private JTextField txtYearsInService;
	private JTextField txtConnectionWorkPosition;
	private JTextField txtConnectionWorkDept;
	private JTextField txtBusinessTelNo;

	private _JTableMain tblConnectionList;
	private JScrollPane scrollConnectionList;
	private modelConnectionList modelConnList;
	private JList rowHeaderConnList;
	
	private _JXTextField txtConnectionFirstName;
	private _JXTextField txtConnectionMiddleName;
	private _JXTextField txtConnectionLastName;
	private _JDateChooser dteConnectionDOB;
	private JComboBox cmbConnectionCivilStatus;
	private JComboBox cmbConnectionGender;
	private _JXTextField txtConnectionEmail;
	private _JXTextField txtConnectionMobileNo;
	private _JXTextField txtConnectionAddress;
	private _JLookup lookupIDType;
	private _JXTextField txtIDType;
	private _JXTextField txtID_No;
	private _JDateChooser dteIDExpiry;
	
	
	private ClientInformation ci;
	
	private String entity_id;
	
	public pnlConnection(ClientInformation ci) {
		this.ci = ci;
		initGUI();
	}

	public pnlConnection(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlConnection(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlConnection(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}
	
	@Override
	public void initGUI() {

		this.setLayout(new BorderLayout(3, 3));
		this.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setPreferredSize(new Dimension(786, 350));
			pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Connection Details"));
			//pnlNorth.setPreferredSize(new Dimension(786, 170));
			{
				pnlNorthWest = new JPanel(new GridLayout(15, 1, 2, 2));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				//pnlNorthWest.setPreferredSize(new Dimension(180, 120));
				{
					lblConnectionName = new JLabel("Connection Name");
					pnlNorthWest.add(lblConnectionName);
					lblConnectionName.setSize(0, 20);
				}
				{
					JLabel lblConnectionDOB = new JLabel("Date of Birth");
					pnlNorthWest.add(lblConnectionDOB);
				}
				{
					JLabel lblConnectionMobileNo = new JLabel("Mobile No");
					pnlNorthWest.add(lblConnectionMobileNo);
				}
				{
					JLabel lblConnectionAddress = new JLabel("Address");
					pnlNorthWest.add(lblConnectionAddress);
				}
				{
					lblConnectionType = new JLabel("*Connection Type");
					pnlNorthWest.add(lblConnectionType);
				}
				{
					lblRelation = new JLabel("*Relation to Main Applicant");
					pnlNorthWest.add(lblRelation);
				}
				{
					lblEmployer = new JLabel("Employer");
					pnlNorthWest.add(lblEmployer);
				}
				{
					lblEmployerAddress = new JLabel("Employer Address");
					pnlNorthWest.add(lblEmployerAddress);
				}
				{
					JLabel lblConnectionCitizenship = new JLabel("Citizenship");
					pnlNorthWest.add(lblConnectionCitizenship);
				}
				{
					JLabel lblConnectionMID_No = new JLabel("MID No");
					pnlNorthWest.add(lblConnectionMID_No);
				}
				{
					JLabel lblConnectionYearsInService = new JLabel("Years in Service");
					pnlNorthWest.add(lblConnectionYearsInService);
				}
				{
					JLabel lblPosition = new JLabel("Position");
					pnlNorthWest.add(lblPosition);
				}
				{
					lblConnectionStatus = new JLabel("Connection Status");
					pnlNorthWest.add(lblConnectionStatus);
				}
				{
					JLabel lblID_Type = new JLabel("ID Type");
					pnlNorthWest.add(lblID_Type);
				}
				{
					JLabel lblID_No = new JLabel("ID No.");
					pnlNorthWest.add(lblID_No);
				}
				/*{
					JLabel lblConnectionSignature = new JLabel("Connection Signature");
					pnlNorthWest.add(lblConnectionSignature);
				}*/
			}
			{
				pnlNorthCenter = new JPanel(new GridLayout(15, 1, 2, 2));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				//pnlNorthCenter.setPreferredSize(new Dimension(176, 120));
				{
					JPanel pnlClient = new JPanel(new GridLayout(1, 2, 2, 2));
					pnlNorthCenter.add(pnlClient);
					{
						txtConnectionLastName = new _JXTextField("Last Name");
						pnlClient.add(txtConnectionLastName);
					}
					{
						txtConnectionFirstName = new _JXTextField("First Name");
						pnlClient.add(txtConnectionFirstName);
					}
					{
						txtConnectionMiddleName = new _JXTextField("Middle Name");
						pnlClient.add(txtConnectionMiddleName);
					}
				}
				{
					JPanel pnlConnectionDOB = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlConnectionDOB);
					{
						dteConnectionDOB = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlConnectionDOB.add(dteConnectionDOB, BorderLayout.WEST);
						dteConnectionDOB.setPreferredSize(new Dimension(150, 0));
						dteConnectionDOB.setEditable(false);
						dteConnectionDOB.getCalendarButton().setEnabled(false);
					}
					{
						JLabel lblConnectionGender = new JLabel("Gender", JLabel.TRAILING);
						pnlConnectionDOB.add(lblConnectionGender);
					}
					{
						cmbConnectionGender = new JComboBox(new String[] {"Male", "Female"});
						pnlConnectionDOB.add(cmbConnectionGender, BorderLayout.EAST);
						cmbConnectionGender.setPreferredSize(new Dimension(150, 0));
						cmbConnectionGender.setSelectedItem(null);
						cmbConnectionGender.setEnabled(false);
						/*cmbConnectionGender.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();
								
								String gender = "";
								
								if(selected_index == 0){
									gender = "M";
								}
								if(selected_index == 1){
									gender = "F";
								}
								
								lookupConnectType.setLookupSQL(sqlConnectionType(gender));
								lookupRelation.setLookupSQL(sqlRelation(gender));
							}
						});*/
					}
				}
				{
					JPanel pnlConnectionContactInfo = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlConnectionContactInfo);
					{
						txtConnectionMobileNo = new _JXTextField();
						pnlConnectionContactInfo.add(txtConnectionMobileNo, BorderLayout.WEST);
						txtConnectionMobileNo.setPreferredSize(new Dimension(200, 0));
					}
					{
						JLabel lblConnectionEmail = new JLabel("Email", JLabel.TRAILING);
						pnlConnectionContactInfo.add(lblConnectionEmail);
					}
					{
						txtConnectionEmail = new _JXTextField();
						pnlConnectionContactInfo.add(txtConnectionEmail, BorderLayout.EAST);
						txtConnectionEmail.setPreferredSize(new Dimension(300, 0));
					}
				}
				{
					txtConnectionAddress = new _JXTextField();
					pnlNorthCenter.add(txtConnectionAddress);
				}
				{
					JPanel pnlConnectType = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlConnectType);
					{
						//LOOKUP FOR THE CONNECTION TYPE 
						lookupConnectType = new _JLookup(null, "Connection Type", 0);
						pnlConnectType.add(lookupConnectType, BorderLayout.WEST);
						lookupConnectType.setFilterName(true);
						//lookupConnection.setFilterName(true);
						//lookupConnectType.setLookupSQL(sqlConnectionType());
						lookupConnectType.setPreferredSize(new Dimension(100, 0));
						lookupConnectType.addFocusListener(new FocusListener() {
							
							@Override
							public void focusLost(FocusEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void focusGained(FocusEvent e) {
								int selected_index = cmbConnectionGender.getSelectedIndex();
								
								String gender = "";
								
								if(selected_index == 0){
									gender = "M";
								}
								if(selected_index == 1){
									gender = "F";
								}
								
								((_JLookup) e.getSource()).setLookupSQL(sqlConnectionType(gender));
								
								
							}
						});
						lookupConnectType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null){
									String connect_id = (String) data[0];
									String connect_type = (String) data[1];
									txtConnectionType.setText(connect_type);
									
									if(checkIfWithSpouse(entity_id) && connect_id.equals("SP")){
										JOptionPane.showMessageDialog(pnlConnection.this, "Client already has an active spouse", "Connection", JOptionPane.WARNING_MESSAGE);
									}
									System.out.printf("Display entity_id: %s%n", ci.getEntityID());
									
									FncSystem.out("Display return sql for Connection type", lookupConnectType.getLookupSQL());
								}
							}
						});
					}
					{
						txtConnectionType = new JTextField();
						pnlConnectType.add(txtConnectionType);
					}
				}
				{
					JPanel pnlRelation = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlRelation);
					{
						//LOOKUP FOR THE RELATION OF THE MAIN APPLICANT TO THE SELECTED CONNECTION
						lookupRelation = new _JLookup(null, "Relation to Main Applicant", 0);
						pnlRelation.add(lookupRelation, BorderLayout.WEST);
						lookupRelation.setFilterName(true);
						//lookupRelation.setLookupSQL(sqlRelation());
						lookupRelation.setPreferredSize(new Dimension(100, 0));
						lookupRelation.addFocusListener(new FocusListener() {
							
							@Override
							public void focusLost(FocusEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void focusGained(FocusEvent e) {
								int selected_index = cmbConnectionGender.getSelectedIndex();
								String gender = "";
								
								if(selected_index == 0){
									gender = "M";
								}
								if(selected_index == 1){
									gender = "F";
								}
								
								lookupRelation.setLookupSQL(sqlRelation(gender));
							}
						});
						lookupRelation.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								FncSystem.out("Display sql for lookup of Relation", lookupRelation.getLookupSQL());
								
								if (data != null){
									String relation = (String) data[1];
									txtRelation.setText(relation);
								}
							}
						});
					}
					{
						txtRelation = new JTextField();
						pnlRelation.add(txtRelation);
					}
				}
				{
					txtEmployer = new JTextField();
					pnlNorthCenter.add(txtEmployer);
				}
				{
					txtEmployerAddress = new JTextField();
					pnlNorthCenter.add(txtEmployerAddress);
				}
				
				{
					JPanel pnlConnectionCitizenship = new JPanel(new BorderLayout(2, 2));
					pnlNorthCenter.add(pnlConnectionCitizenship);
					{
						lookupCitizenship = new _JLookup(null, "Citizenship", 0);
						pnlConnectionCitizenship.add(lookupCitizenship, BorderLayout.WEST);
						lookupCitizenship.setPreferredSize(new Dimension(50, 0));
						lookupCitizenship.setLookupSQL(sqlCitizenship());
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
						JPanel pnlCitizenship = new JPanel(new BorderLayout(2, 2));
						pnlConnectionCitizenship.add(pnlCitizenship, BorderLayout.CENTER);
						pnlCitizenship.setPreferredSize(new Dimension(200, 0));
						{
							txtCitizenship = new JTextField();
							pnlCitizenship.add(txtCitizenship, BorderLayout.WEST);
							txtCitizenship.setPreferredSize(new Dimension(200, 0));
						}
					}
				}
				{
					JPanel pnlConnection_MID_No = new JPanel(new BorderLayout(2, 2));
					pnlNorthCenter.add(pnlConnection_MID_No);
					{
						txtConnectionMIDNo = new JTextField();
						pnlConnection_MID_No.add(txtConnectionMIDNo, BorderLayout.WEST);
						txtConnectionMIDNo.setPreferredSize(new Dimension(150, 0));
					}
					{
						JLabel lblConnection_TIN_No = new JLabel("TIN. No", JLabel.TRAILING);
						pnlConnection_MID_No.add(lblConnection_TIN_No);
					}
					{
						txtConnectionTINNo = new JTextField();
						pnlConnection_MID_No.add(txtConnectionTINNo, BorderLayout.EAST);
						txtConnectionTINNo.setPreferredSize(new Dimension(150, 0));
					}
				}
				{
					JPanel pnlConnectionYearsService = new JPanel(new BorderLayout(2, 2));
					pnlNorthCenter.add(pnlConnectionYearsService);
					{
						txtYearsInService = new JTextField();
						pnlConnectionYearsService.add(txtYearsInService, BorderLayout.WEST);
						txtYearsInService.setPreferredSize(new Dimension(50, 0));
					}
					{
						JLabel lblBusinessTelNo = new JLabel("Business Tel. No", JLabel.TRAILING);
						pnlConnectionYearsService.add(lblBusinessTelNo);
					}
					{
						txtBusinessTelNo = new JTextField();
						pnlConnectionYearsService.add(txtBusinessTelNo, BorderLayout.EAST);
						txtBusinessTelNo.setPreferredSize(new Dimension(150, 0));
					}
				}
				{
					JPanel pnlConnectionPosition = new JPanel(new BorderLayout(2, 2));
					pnlNorthCenter.add(pnlConnectionPosition);
					{
						txtConnectionWorkPosition = new JTextField();
						pnlConnectionPosition.add(txtConnectionWorkPosition, BorderLayout.WEST);
						txtConnectionWorkPosition.setPreferredSize(new Dimension(150, 0));
					}
					{
						JLabel lblConnectionWorkDept = new JLabel("Department", JLabel.TRAILING);
						pnlConnectionPosition.add(lblConnectionWorkDept);
					}
					{
						txtConnectionWorkDept = new JTextField();
						pnlConnectionPosition.add(txtConnectionWorkDept, BorderLayout.EAST);
						txtConnectionWorkDept.setPreferredSize(new Dimension(150, 0));
					}
				}
				{
					JPanel pnlConnectionStatus = new JPanel(new BorderLayout(5, 5));
					pnlNorthCenter.add(pnlConnectionStatus);
					//pnlConnectionStatus.setPreferredSize(new Dimension(200, 30));
					{
						cmbConnectionStatus = new JComboBox(new String[] { "Active", "Inactive" });
						pnlConnectionStatus.add(cmbConnectionStatus, BorderLayout.WEST);
						cmbConnectionStatus.setPreferredSize(new Dimension(120, 0));
					}
					{
						JPanel pnlConnectionSignature = new JPanel(new BorderLayout(5, 5));
						pnlConnectionStatus.add(pnlConnectionSignature, BorderLayout.EAST);
						{
							JLabel lblConnectionSignature = new JLabel("Connection Signature");
							pnlConnectionSignature.add(lblConnectionSignature, BorderLayout.WEST);
						}
						{
							lblConnectionSignatureFileChooser = new FncImageFileChooser(145, 30);
							pnlConnectionSignature.add(lblConnectionSignatureFileChooser, BorderLayout.CENTER);
							lblConnectionSignatureFileChooser.setClickable(false);
							lblConnectionSignatureFileChooser.setDefaultClientSignature();
							lblConnectionSignatureFileChooser.addMouseListener(new MouseListener() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mousePressed(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mouseExited(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mouseEntered(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mouseClicked(MouseEvent e) {
									if(lblConnectionSignatureFileChooser.isClickable() == false){
										if(e.getClickCount() >= 2){
											if(tblConnectionList.getSelectedRows().length == 1){
												try{
													Integer selected_row = tblConnectionList.convertRowIndexToModel(tblConnectionList.getSelectedRow());
													Integer rec_id = (Integer) modelConnList.getValueAt(selected_row, 0);
														
													dlg_ImageViewer img_dlg = new dlg_ImageViewer(FncGlobal.homeMDI, "Client Sginature", displayConnectionSignature(lblConnectionSignatureFileChooser, rec_id), new Dimension(500, 500));
													img_dlg.setLocationRelativeTo(null);
													img_dlg.setVisible(true);
													
													} catch (IOException a) {
														a.printStackTrace();
													} catch (OutOfMemoryError er){
														JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
												
													}
											}
										}
									}
									
									if(lblConnectionSignatureFileChooser.isClickable()){
										if(e.getClickCount() >= 2 && lblConnectionSignatureFileChooser.getIsOk()){
											
										}
									}
								}
							});
						}
					}
					/*{
						JPanel pnlConnectionSignature = new JPanel(new BorderLayout(5, 5));
						pnlConnectionStatus.add(pnlConnectionSignature, BorderLayout.EAST);
						{
							lblConnectionSignatureFileChooser = new FncImageFileChooser(145, 30);
							pnlConnectionSignature.add(lblConnectionSignatureFileChooser);
							lblConnectionSignatureFileChooser.setClickable(false);
							lblConnectionSignatureFileChooser.setDefaultClientSignature();
							lblConnectionSignatureFileChooser.addMouseListener(new MouseListener() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mousePressed(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mouseExited(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mouseEntered(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mouseClicked(MouseEvent e) {
									if(lblConnectionSignatureFileChooser.isClickable() && tblConnectionList.getSelectedRowCount() == 1){
										if(e.getClickCount() >= 2){
											//XXX CHECK UPLOADING OF CONNECTION SIGNATURE HERE
											try{
												Integer selected_row = tblConnectionList.convertRowIndexToModel(tblConnectionList.getSelectedRow());
												Integer rec_id = (Integer) modelConnList.getValueAt(selected_row, 0);
												System.out.printf("Display selected row: %s%n", selected_row);
												System.out.printf("Display value of Rec ID: %s%n", rec_id);
												
												dlg_ImageViewer img_dlg = new dlg_ImageViewer(FncGlobal.homeMDI, "Connection Signature", displayConnectionSignature(lblConnectionSignatureFileChooser, rec_id));
												img_dlg.setLocationRelativeTo(null);
												img_dlg.setVisible(true);
												
											} catch (IOException a) {
												a.printStackTrace();
											} catch (OutOfMemoryError er){
												JOptionPane.showMessageDialog(getTopLevelAncestor(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
											}
										}
									}
									
									if(lblConnectionSignatureFileChooser.isClickable()){
										if(e.getClickCount() >= 2 && lblConnectionSignatureFileChooser.getIsOk()){
											
										}
									}
								}
							});
						}
					}*/
				}
				{
					JPanel pnlID_Type = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlID_Type);
					{
						lookupIDType = new _JLookup(null, "ID Type", 0);
						pnlID_Type.add(lookupIDType, BorderLayout.WEST);
						lookupIDType.setPreferredSize(new Dimension(150, 0));
						lookupIDType.setLookupSQL(sqlIDType());
						lookupIDType.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object data[] = ((_JLookup)event.getSource()).getDataSet();
								
								if(data != null){
									//String id_code = (String) data[0];
									String id_type = (String) data[1];
									
									txtIDType.setText(id_type);
								}
							}
						});
					}
					{
						txtIDType = new _JXTextField();
						pnlID_Type.add(txtIDType, BorderLayout.CENTER);
					}
				}
				{
					JPanel pnlID_No = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlID_No);
					{
						txtID_No = new _JXTextField();
						pnlID_No.add(txtID_No, BorderLayout.WEST);
						txtID_No.setPreferredSize(new Dimension(150, 0));
					}
					{
						JLabel lblExpiration = new JLabel("Expiration", JLabel.TRAILING);
						pnlID_No.add(lblExpiration);
						
					}
					{
						dteIDExpiry = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlID_No.add(dteIDExpiry, BorderLayout.EAST);
						dteIDExpiry.setPreferredSize(new Dimension(100, 0));
						dteIDExpiry.getCalendarButton().setEnabled(false);
					}
				}
			}
		}
		{
			scrollConnectionList = new JScrollPane();
			this.add(scrollConnectionList, BorderLayout.CENTER);
			scrollConnectionList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelConnList = new modelConnectionList();

				tblConnectionList = new _JTableMain(modelConnList);
				scrollConnectionList.setViewportView(tblConnectionList);
				tblConnectionList.setSortable(false);
				tblConnectionList.hideColumns("Rec. ID", "First Name", "Middle Name", "Last Name", "Gender" ,"Type ID", "Relation ID", "Unit ID", 
						"Citizenship Code", 
						"Citizenship", 
						"MID No", 
						"TIN No", 
						"Yrs In Srvc", 
						"Business No.", 
						"Position", 
						"Department");
				tblConnectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				//Process after row add in tables
				modelConnList.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {

						((DefaultListModel)rowHeaderConnList.getModel()).addElement(modelConnList.getRowCount());

						if(modelConnList.getRowCount() == 0){
							rowHeaderConnList.setModel(new DefaultListModel());
							//ci.btnState(true, false, false, false, false);
						}
					}
				});
				
				tblConnectionList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if(!arg0.getValueIsAdjusting()){
							try {
								int row = tblConnectionList.getSelectedRow();
								
								Integer rec_id = (Integer) modelConnList.getValueAt(row, 0);
								String connection_name = (String) modelConnList.getValueAt(row, 1);
								String connection_fname = (String) modelConnList.getValueAt(row, 2);
								String connection_mname = (String) modelConnList.getValueAt(row, 3);
								String connection_lname = (String) modelConnList.getValueAt(row, 4);
								Timestamp birthdate = (Timestamp) modelConnList.getValueAt(row, 5);
								String gender = (String) modelConnList.getValueAt(row, 6);
								String mobile_no = (String) modelConnList.getValueAt(row, 7);
								String email = (String) modelConnList.getValueAt(row, 8);
								String address = (String) modelConnList.getValueAt(row, 9);
								String connect_id = (String) modelConnList.getValueAt(row, 10);
								String connection_type = (String) modelConnList.getValueAt(row, 11);
								String relation_id = (String) modelConnList.getValueAt(row, 12);
								String relation = (String) modelConnList.getValueAt(row, 13);
								String connection_employer = (String) modelConnList.getValueAt(row, 14);
								String connection_employer_address = (String) modelConnList.getValueAt(row, 15);
								String connection_status = (String) modelConnList.getValueAt(row, 16);
								String citizenship_code = (String) modelConnList.getValueAt(row, 17);
								String citizenship = (String) modelConnList.getValueAt(row, 18);
								String mid_no = (String) modelConnList.getValueAt(row, 19);
								String tin_no = (String) modelConnList.getValueAt(row, 20);
								String yrs_srvc = (String) modelConnList.getValueAt(row, 21);
								String business_tel_no = (String) modelConnList.getValueAt(row, 22);
								String position = (String) modelConnList.getValueAt(row, 23);
								String department = (String) modelConnList.getValueAt(row, 24);
								String id_code = (String) modelConnList.getValueAt(row, 25);
								String id_desc = (String) modelConnList.getValueAt(row, 26);
								String id_no = (String) modelConnList.getValueAt(row, 27);
								Timestamp id_expiration = (Timestamp) modelConnList.getValueAt(row, 28);
								
								txtConnectionFirstName.setText(connection_fname);
								txtConnectionMiddleName.setText(connection_mname);
								txtConnectionLastName.setText(connection_lname);
								dteConnectionDOB.setDate(birthdate);
								txtConnectionMobileNo.setText(mobile_no);
								txtConnectionEmail.setText(email);
								txtConnectionAddress.setText(address);
								lookupConnectType.setValue(connect_id);
								txtConnectionType.setText(connection_type);
								lookupRelation.setValue(relation_id);
								txtRelation.setText(relation);
								txtEmployer.setText(connection_employer);
								txtEmployerAddress.setText(connection_employer_address);
								lookupCitizenship.setValue(citizenship_code);
								txtCitizenship.setText(citizenship);
								txtConnectionMIDNo.setText(mid_no);
								txtConnectionTINNo.setText(tin_no);
								txtYearsInService.setText(yrs_srvc);
								txtBusinessTelNo.setText(business_tel_no);
								txtConnectionWorkPosition.setText(position);
								txtConnectionWorkDept.setText(department);
								lookupIDType.setValue(id_code);
								txtIDType.setText(id_desc);
								txtID_No.setText(id_no);
								dteIDExpiry.setDate(id_expiration);
								
								if(gender.equals("F")){
									cmbConnectionGender.setSelectedIndex(1);
								}
								
								if(gender.equals("M")){
									cmbConnectionGender.setSelectedIndex(0);
								}
								
								
								try {
									displayConnectionSignature(lblConnectionSignatureFileChooser, rec_id);
								} catch (IOException e) {
									e.printStackTrace();
								} catch (OutOfMemoryError er){
										JOptionPane.showMessageDialog(getTopLevelAncestor(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
								}
								
								//DO EIDTING ON MAIN CLIENT INFO WHEN EDITING COAPP
								/*if(connect_id.equals("CO")){
									ci.btnState(true, false, false, false, false);
								}else{*/
									if(connection_status.equals("A")){
										cmbConnectionStatus.setSelectedItem("Active");
										ci.btnState(true, true, true, false, false);
									}else{
										cmbConnectionStatus.setSelectedItem("Inactive");
										ci.btnState(true, true, false, false, false);
									}
									
									if(ci.canEdit() == false){
										ci.btnState(false, false, false, false, false);
									}
								//}

							} catch (ArrayIndexOutOfBoundsException e) { }
						}
					}
				});
			}
			{
				rowHeaderConnList = tblConnectionList.getRowHeader();
				rowHeaderConnList.setModel(new DefaultListModel());
				scrollConnectionList.setRowHeaderView(rowHeaderConnList);
				scrollConnectionList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		setComponentsEnabled(false, false, false, false, false, false);
		ci.setComponentsEditable(pnlNorth, false);
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtConnectionName, txtConnectionType, txtRelation, txtUnit, cmbConnectionStatus ));
	}//XXX END OF INIT GUI
	
	public void displayConnectionList(String entity_id){
		this.entity_id = entity_id;
		modelConnList.clear();
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM view_ci_connections('"+entity_id+"')";
		db.select(SQL);
		
		FncSystem.out("Display Connection List", SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelConnList.addRow(db.getResult()[x]);
			}
			scrollConnectionList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblConnectionList.getRowCount())));
			tblConnectionList.packAll();
			lblConnectionSignatureFileChooser.setDefaultClientSignature();
		}
	}
	
	public void displayConnectionList2(String entity_id){
		this.entity_id = entity_id;
		modelConnList.clear();
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM view_ci_connections('"+entity_id+"')";
		db.select(SQL);
		
		FncSystem.out("Display Connection List", SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelConnList.addRow(db.getResult()[x]);
			}
			scrollConnectionList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblConnectionList.getRowCount())));
			tblConnectionList.packAll();
		}
	}
	
	public static byte[] displayConnectionSignature(FncImageFileChooser label, Integer rec_id) throws IOException{
		byte bt[]=null;

		String strSQL = "SELECT connection_signature FROM rf_entity_connect WHERE rec_id = "+rec_id+"";
		
		FncSystem.out("Display connection signature:", strSQL);
		
		try{
			Functions.FncSelectRecords.selectV2(strSQL, false);
			Functions.FncSelectRecords.rs.last();
			Functions.FncSelectRecords.rs.beforeFirst();
			//int x = 0;

			while (Functions.FncSelectRecords.rs.next()) {
				bt = Functions.FncSelectRecords.rs.getBytes(1);

				//check if picture exist in column
				System.out.printf("Display Value of bt: %s%n", bt);
				
				if(bt!=null) {
					label.setByteImageIcon(bt);
				}else{
					System.out.println("Dumaan dito sa display ng default Connection signature");
					label.setDefaultClientSignature();
				}
				//x++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Functions.FncSelectRecords.disconnect();
		
		return bt;
	}
	
	private String clientGender(String entity_id){
		String gender = "";
		pgSelect db = new pgSelect();
		
		String sql = "SELECT gender FROM rf_entity WHERE entity_id = '"+entity_id+"'";
		db.select(sql);
		
		FncSystem.out("Display checking of gender", sql);
		
		if(db.isNotNull()){
			gender = (String) db.getResult()[0][0];
		}
		
		return gender;
	}
	
	private Boolean checkIfWithSpouse(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM rf_entity_connect where entity_id = '"+entity_id+"' and connect_type = 'SP' AND status_id = 'A';";
		db.select(SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	private Boolean checkIfMarried(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM rf_entity WHERE entity_id = '"+entity_id+"' AND civil_status_code = 'M'";
		db.select(SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	/*private String sqlConnectionType(String connection_gender){
		
		return "SELECT \n" + 
			   "TRIM(connect_type) AS \"ID\", \n" + 
			   "TRIM(connect_type_desc) AS \"Name\" \n" + 
			   "FROM mf_connect_type\n" + 
			   "WHERE status_id = 'A' \n" + 
			   "AND '"+clientGender(entity_id)+"' != '"+connection_gender+"' \n" + 
			   "THEN status_id = 'A' ELSE connect_type != 'SP' END);";
	}*/
	
	private String sqlConnectionType(String connection_gender){
		
		return "SELECT TRIM(connect_type) AS \"ID\", \n" + 
			   "TRIM(connect_type_desc) AS \"Name\" \n" + 
			   "FROM mf_connect_type\n" + 
			   "WHERE status_id = 'A' \n" + 
			   //"and connect_type != 'CO'\n" + 
			   "AND (CASE WHEN '"+clientGender(entity_id)+"' != '"+connection_gender+"' AND '"+checkIfMarried(entity_id)+"'\n" + 
			   "THEN status_id = 'A' ELSE connect_type NOT IN ('SP', 'SC') END) \n"+
			   "ORDER BY TRIM(connect_type_desc)";
		
	}
	
	/*private String sqlRelation(String connection_gender){
		return "select trim(relation_code) as \"ID\", \n" + 
				"trim(relation_desc) as \"Description\" \n" + 
				"from mf_relation \n" + 
				"where status_id = 'A'\n" + 
				"and (case when 'FATALLO' = 'DE LEON'\n" + 
				"AND 'M' != 'F'\n" + 
				"THEN status_id = 'A' else relation_code != '20' END); ";
	}*/
	
	private String sqlRelation(String connection_gender){
		return "select trim(relation_code) as \"ID\", \n" + 
				"trim(relation_desc) as \"Description\" \n" + 
				"from mf_relation \n" + 
				"where status_id = 'A'\n" + 
				"AND (CASE WHEN '"+clientGender(entity_id)+"' != '"+connection_gender+"' AND '"+checkIfMarried(entity_id)+"'\n" + 
				"THEN status_id = 'A' ELSE relation_code != '20' END) \n"+
				"ORDER BY TRIM(relation_desc)";
				
				/*"AND '"+clientGender(entity_id)+"' != '"+connection_gender+"'\n" + 
				"AND( THEN status_id = 'A' else relation_code != '20' END);";*/
	}
	
	private String sqlCitizenship(){//SQL FOR THE CITIZENSHIP
		return "Select trim(citizenship_code) as \"ID\", trim(citizenship_desc) as \"Description\" from mf_citizen where status_id = 'A'";
	}
	
	private String sqlIDType() {
		
		return "SELECT id_no as \"ID Code\", TRIM(id_desc) as \"ID Desc\"\n" + 
			   "FROM mf_client_valid_id \n" + 
			   "ORDER BY id_desc";
		
	}
	
	private boolean isConnectionExist(String connect_id, String entity_id) {//CHECKS WHETHER THE CONNECTION IS EXISTING
		pgSelect db = new pgSelect();
		db.select("select * from rf_entity_connect where connect_id = '"+ connect_id +"' and entity_id = '"+entity_id+"'");
		return db.isNotNull();
	}
	
	private void setComponentsEnabled(Boolean connect_name,Boolean enabled_unit ,Boolean connect_type, Boolean relation,Boolean unit ,Boolean connect_stat){
		cmbConnectionStatus.setEnabled(connect_stat);
		//chkEnabledUnit.setEnabled(enabled_unit);
		//txtConnectionName.setEditable(connect_name);
		txtConnectionType.setEditable(connect_type);
		txtRelation.setEditable(relation);
		//txtUnit.setEditable(unit);
		//lookupConnection.setLookupSQL(sqlClients());
		//lookupConnectType.setLookupSQL(sqlConnectionType());
	}
	
	public void newConnection(){//NEW CONNECTION
		
		clearConnectionFields();
		ci.setComponentsEditable(pnlNorth, true);
		setComponentsEnabled(false, true, false, false, false, true);
		
		lblConnectionName.setText("*Connection Name");
		lblConnectionType.setText("*Connection Type");
		lblConnectionSignatureFileChooser.setDefaultClientSignature();
		lblConnectionSignatureFileChooser.setClickable(true);
		lblRelation.setText("*Relation to Main Applicant");
		
		dteConnectionDOB.setEditable(true);
		dteConnectionDOB.getCalendarButton().setEnabled(true);
		cmbConnectionGender.setEnabled(true);
		dteIDExpiry.setEditable(true);
		dteIDExpiry.getCalendarButton().setEnabled(true);
		tblConnectionList.clearSelection();
		tblConnectionList.setEnabled(false);
		rowHeaderConnList.setEnabled(false);
		
	}
	
	public void editConnection() { //Enabling of Components when edit button is clicked
		if(tblConnectionList.getSelectedRows().length == 1){
			ci.setComponentsEditable(pnlNorth, true);
			//ci.setComponentsEnabled(pnlNorth, true);
			
			setComponentsEnabled(false, true, false, false, false, true);
			lblConnectionName.setText("*Connection Name");
			lblConnectionType.setText("*Connection Type");
			lblRelation.setText("*Relation to Main Applicant");
			
			dteConnectionDOB.setEditable(true);;
			dteConnectionDOB.getCalendarButton().setEnabled(true);
			cmbConnectionGender.setEnabled(true);
			tblConnectionList.setEnabled(false);
			rowHeaderConnList.setEnabled(false);
			dteIDExpiry.setEditable(true);
			dteIDExpiry.getCalendarButton().setEnabled(true);
			lblConnectionSignatureFileChooser.setClickable(true);
		}else{
			JOptionPane.showMessageDialog(scrollConnectionList, "Please select only one row to edit");
			tblConnectionList.clearSelection();
		}
	}
	
	/*public Boolean saveClientConnection(String entity_id){
		
		String connection_fname = txtConnectionFirstName.getText().toUpperCase().replace("'", "''");
		String connection_mname = txtConnectionMiddleName.getText().toUpperCase().replace("'", "''");
		String connection_lname = txtConnectionLastName.getText().toUpperCase().replace("'", "''");
		Timestamp connection_dob = dteConnectionDOB.getTimestamp();
		String connection_email = txtConnectionEmail.getText().trim().replace("/", ",");
		String connection_address = txtConnectionAddress.getText();
		String connection_gender = cmbConnectionGender.getSelectedItem() == "Male" ? "M":"F";
		//String connection_civil_status = (String) cmbConnectionCivilStatus.getSelectedItem();
		String connection_mobile_no = txtConnectionMobileNo.getText().trim().replace("/", ",");
		String connect_type = lookupConnectType.getValue();
		String relation = lookupRelation.getValue();
		String connection_employer = txtEmployer.getText().trim();
		String connection_employer_address = txtEmployerAddress.getText().trim();
		String connection_status = cmbConnectionStatus.getSelectedItem() == "Active" ? "A":"I";
		String connection_citizenship = lookupCitizenship.getValue();
		String connection_mid_no = txtConnectionMIDNo.getText().trim();
		String connection_tin_no = txtConnectionTINNo.getText().trim();
		Integer yrs_in_service = Integer.valueOf(txtYearsInService.getText().trim().equals("") ? "0":txtYearsInService.getText().trim());
		String business_tel_no = txtBusinessTelNo.getText().trim();
		String connection_work_position = txtConnectionWorkPosition.getText().trim();
		String connection_work_dept = txtConnectionWorkDept.getText().trim();
		
		Integer rec_id = null;
		
		pgSelect db = new pgSelect();
		
		if(tblConnectionList.getSelectedRows().length == 1){
			int selected_row = tblConnectionList.convertRowIndexToModel(tblConnectionList.getSelectedRow());
			
			rec_id = (Integer) modelConnList.getValueAt(selected_row, 0);
		}
		
		//System.out.printf("Value of rec_id: %s%n", rec_id);
		
		String SQL = "SELECT sp_save_ci_connections('"+ entity_id +"' , '"+connection_fname+"' , '"+connection_mname+"' , '"+connection_lname+"' , \n"+
					 "NULLIF('"+connection_dob+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, '"+connection_gender+"', '"+connection_mobile_no+"' , '"+connection_email+"' , \n"+
					 "'"+connection_address+"', NULLIF('"+connect_type+"', 'null'), NULLIF('"+relation+"', 'null'), '"+connection_employer+"', '"+connection_employer_address+"', \n"+
					 "'"+connection_status+"' , '"+connection_citizenship+"', '"+connection_mid_no+"', '"+connection_tin_no+"', "+yrs_in_service+", '"+business_tel_no+"', '"+connection_work_position+"', "+
					 "'"+connection_work_dept+"' ,"+rec_id+", '"+ UserInfo.EmployeeCode +"')";
		db.select(SQL, "Save", true);
		
		FncSystem.out("Save Connection", SQL);
		
		if((Boolean) db.getResult()[0][0]){
			//if(tblConnectionList.getSelectedRows().length ==1){
				
			//}else{
			
			//}
			//saveConnectionSignature();
			saveConnectionSignature(entity_id);
			lblConnectionSignatureFileChooser.setDefaultClientSignature();
			lblConnectionSignatureFileChooser.setClickable(false);
			ci.setComponentsEditable(pnlNorth, false);
			setComponentsEnabled(false, false, false, false, false, false);
			tblConnectionList.clearSelection();
			tblConnectionList.setEnabled(true);
			rowHeaderConnList.setEnabled(true);
			
		}
		
		return (Boolean) db.getResult()[0][0];
	}*/
	
	/*public void saveConnectionSignature(String entity_id){
		if(tblConnectionList.getSelectedRows().length != 1){
			displayConnectionList2(entity_id);
		}
		
		Integer selected_row = 0;
		
		if(tblConnectionList.getSelectedRows().length == 0){
			selected_row = 0;
		}else{
			selected_row= tblConnectionList.convertRowIndexToModel(tblConnectionList.getSelectedRow());
		}
		
		System.out.printf("Display Index of Selected Row: %s%n", selected_row);
		
		Integer rec_id = (Integer) modelConnList.getValueAt(selected_row, 0);
		
		System.out.printf("Display value of rec id: %s%n", rec_id);
		if(tblConnectionList.getSelectedRows().length == 1){
			displayConnectionList(entity_id);
			rec_id = 0;
		}
		
		byte[] signature = lblConnectionSignatureFileChooser.getImageByte();
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			connection.setAutoCommit(true);

			PreparedStatement ps = connection.prepareStatement("UPDATE rf_entity_connect SET connection_signature = ? WHERE rec_id = "+rec_id+"");
			ps.setBytes(1, signature);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}*/
	
	public Boolean saveClientConnection(String entity_id){
		String connection_fname = txtConnectionFirstName.getText().toUpperCase().replace("'", "''");
		String connection_mname = txtConnectionMiddleName.getText().toUpperCase().replace("'", "''");
		String connection_lname = txtConnectionLastName.getText().toUpperCase().replace("'", "''");
		Timestamp connection_dob = dteConnectionDOB.getTimestamp();
		String connection_email = txtConnectionEmail.getText().trim().replace("/", ",");
		String connection_address = txtConnectionAddress.getText();
		String connection_gender = cmbConnectionGender.getSelectedItem() == "Male" ? "M":"F";
		//String connection_civil_status = (String) cmbConnectionCivilStatus.getSelectedItem();
		String connection_mobile_no = txtConnectionMobileNo.getText().trim().replace("/", ",");
		String connect_type = lookupConnectType.getValue();
		String relation = lookupRelation.getValue();
		String connection_employer = txtEmployer.getText().trim();
		String connection_employer_address = txtEmployerAddress.getText().trim();
		String connection_status = cmbConnectionStatus.getSelectedItem() == "Active" ? "A":"I";
		String connection_citizenship = lookupCitizenship.getValue();
		String connection_mid_no = txtConnectionMIDNo.getText().trim();
		String connection_tin_no = txtConnectionTINNo.getText().trim();
		Integer yrs_in_service = Integer.valueOf(txtYearsInService.getText().trim().equals("") ? "0":txtYearsInService.getText().trim());
		String business_tel_no = txtBusinessTelNo.getText().trim();
		String connection_work_position = txtConnectionWorkPosition.getText().trim();
		String connection_work_dept = txtConnectionWorkDept.getText().trim();
		String id_type = lookupIDType.getValue();
		String id_no = txtID_No.getText();
		Timestamp expiry_date = dteIDExpiry.getTimestamp();
		
		Integer rec_id = null;
		
		pgSelect db = new pgSelect();
		
		if(tblConnectionList.getSelectedRows().length == 1){
			int selected_row = tblConnectionList.convertRowIndexToModel(tblConnectionList.getSelectedRow());
			
			rec_id = (Integer) modelConnList.getValueAt(selected_row, 0);
		}
		
		//System.out.printf("Value of rec_id: %s%n", rec_id);
		
		String SQL = "SELECT * FROM sp_save_ci_connections_v2('"+ entity_id +"' , '"+connection_fname+"' , '"+connection_mname+"' , '"+connection_lname+"' , \n"+
					 "NULLIF('"+connection_dob+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, '"+connection_gender+"', '"+connection_mobile_no+"' , '"+connection_email+"' , \n"+
					 "'"+connection_address+"', NULLIF('"+connect_type+"', 'null'), NULLIF('"+relation+"', 'null'), '"+connection_employer+"', '"+connection_employer_address+"', \n"+
					 "'"+connection_status+"' , '"+connection_citizenship+"', '"+connection_mid_no+"', '"+connection_tin_no+"', "+yrs_in_service+", '"+business_tel_no+"', '"+connection_work_position+"', "+
					 "'"+connection_work_dept+"', '"+id_type+"', '"+id_no+"',"+
					 "NULLIF('"+expiry_date+"', 'null')::TIMESTAMP WITHOUT TIME ZONE ,"+rec_id+", '"+ UserInfo.EmployeeCode +"')";
		db.select(SQL, "Save", true);
		
		FncSystem.out("Save Connection", SQL);
		
		if((Boolean) db.getResult()[0][0]){
			//if(tblConnectionList.getSelectedRows().length ==1){
				
			//}else{
			
			//}
			//saveConnectionSignature();
			
			//saveConnectionSignature(entity_id);
			System.out.printf("Display value of rec id dito sa UPdate: %s", db.getResult()[0][1]);
			saveConnectionSignature(entity_id, (Integer) db.getResult()[0][1]);
			lblConnectionSignatureFileChooser.setDefaultClientSignature();
			lblConnectionSignatureFileChooser.setClickable(false);
			ci.setComponentsEditable(pnlNorth, false);
			setComponentsEnabled(false, false, false, false, false, false);
			tblConnectionList.clearSelection();
			tblConnectionList.setEnabled(true);
			rowHeaderConnList.setEnabled(true);
			
		}
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public void saveConnectionSignature(String entity_id, Integer rec_id){
		if(tblConnectionList.getSelectedRows().length != 1){
			displayConnectionList2(entity_id);
		}
		
		Integer selected_row = 0;
		
		if(tblConnectionList.getSelectedRows().length == 0){
			selected_row = 0;
		}else{
			selected_row= tblConnectionList.convertRowIndexToModel(tblConnectionList.getSelectedRow());
			rec_id = (Integer) modelConnList.getValueAt(selected_row, 0);
		}
		
		System.out.printf("Display Index of Selected Row: %s%n", selected_row);
		
		System.out.printf("Display value of rec id: %s%n", rec_id);
		/*if(tblConnectionList.getSelectedRows().length == 1){
			displayConnectionList(entity_id);
			rec_id = 0;
		}*/
		
		byte[] signature = lblConnectionSignatureFileChooser.getImageByte();
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			connection.setAutoCommit(true);

			PreparedStatement ps = connection.prepareStatement("UPDATE rf_entity_connect SET connection_signature = ? WHERE entity_id = '"+entity_id+"' AND rec_id = "+rec_id+"");
			ps.setBytes(1, signature);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public boolean deleteConnection(String entity_id){//UPDATING OF THE CONNECTION
		pgUpdate db = new pgUpdate();

		String connect_id = lookupConnection.getValue();
		String connect_type = lookupConnectType.getValue();
		String relation = txtRelation.getText();
		String unit = lookupUnit.getValue();

		if(tblConnectionList.getSelectedRows().length ==1){
			int row = tblConnectionList.getSelectedRow();
			String slctd_connect_id = (String) modelConnList.getValueAt(row, 0);
			String unit_id = (String) modelConnList.getValueAt(row, 6);

			if(isConnectionExist(slctd_connect_id, entity_id)){ 

				String sql = "update rf_entity_connect set connect_id = '"+connect_id+"', connect_type = '"+ connect_type +"', "+
						"relation = (select relation_code from mf_relation where relation_desc = '"+ relation + "'), "+
						"proj_id = (select a.projcode from rf_sold_unit a \n" + 
						"left join mf_unit_info b on b.pbl_id = a.pbl_id \n" + 
						"where b.unit_id = '"+unit+"'), "+
						"pbl_id = (select a.pbl_id from rf_sold_unit a \n" + 
						"left join mf_unit_info b on b.pbl_id = a.pbl_id \n" + 
						"where b.unit_id = '"+unit+"'), "+
						"seq_no = (select a.seq_no from rf_sold_unit a \n" + 
						"left join mf_unit_info b on b.pbl_id = a.pbl_id \n" + 
						"where b.unit_id = '"+unit+"') "+
						",status_id = 'I', edited_by = '"+ UserInfo.Alias +"', date_edited = now() "+
						"where entity_id = '"+ entity_id +"' "+
						/*"and pbl_id = (select a.pbl_id from rf_sold_unit a \n" + 
						"left join mf_unit_info b on b.pbl_id = a.pbl_id \n" + 
						"where b.unit_id = '"+unit_id+"') "+*/
						"and connect_id = '"+ slctd_connect_id +"' ";
				db.executeUpdate(sql, true);
			}
		}else{
			JOptionPane.showMessageDialog(scrollConnectionList, "Please select only one row");
		}
		db.commit();
		
		return true;
	}
	
	public void cancelConnection() {//CANCELLATION OF THE CONNECTION
		clearConnectionFields();
		ci.setComponentsEditable(pnlNorth, false);
		
		setComponentsEnabled(false, false, false, false, false, false);
		dteConnectionDOB.setEditable(false);
		dteConnectionDOB.getCalendarButton().setEnabled(false);
		cmbConnectionGender.setEnabled(false);
		dteIDExpiry.setEditable(false);
		dteIDExpiry.getCalendarButton().setEnabled(false);
		tblConnectionList.clearSelection();
		tblConnectionList.setEnabled(true);
		rowHeaderConnList.setEnabled(true);
		lblConnectionSignatureFileChooser.setClickable(false);
	}

	public void clearConnectionFields(){ //CLEAR FIELDS FROM THE CONNECTION PANEL
		System.out.println("Dumaan dito sa clear ng Connection Fields");
		//lookupConnection.setValue(null);
		//txtConnectionName.setText("");
		lblConnectionName.setText("Connection Name");
		lblConnectionType.setText("Connection Type");
		lblRelation.setText("Relation to Main Applicant");
		lblConnectionSignatureFileChooser.setDefaultClientSignature();
		lblConnectionSignatureFileChooser.setClickable(false);
		
		txtConnectionFirstName.setText("");
		txtConnectionMiddleName.setText("");
		txtConnectionLastName.setText("");
		txtConnectionMobileNo.setText("");
		txtConnectionEmail.setText("");
		txtConnectionAddress.setText("");
		
		lookupConnectType.setValue(null);
		txtConnectionType.setText("");
		lookupRelation.setValue(null);
		txtRelation.setText("");
		dteConnectionDOB.setDate(null);
		cmbConnectionGender.setSelectedItem(null);
		txtEmployer.setText("");
		txtEmployerAddress.setText("");
		tblConnectionList.clearSelection();
		
		lookupCitizenship.setValue(null);
		txtCitizenship.setText("");
		txtConnectionMIDNo.setText("");
		txtConnectionTINNo.setText("");
		txtYearsInService.setText("");
		txtBusinessTelNo.setText("");
		txtConnectionWorkPosition.setText("");
		txtConnectionWorkDept.setText("");
		txtID_No.setText("");
		lookupIDType.setValue(null);
		txtIDType.setText("");
		dteIDExpiry.setDate(null);
		/*lookupUnit.setText(null);
		txtUnit.setText("");*/
		//chkEnabledUnit.setSelected(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}
