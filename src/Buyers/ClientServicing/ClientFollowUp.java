/**
 * 
 */
package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DateFormatter;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JTextAreaRenderer;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelClientFollowup;
/**
 * @author John Lester Fatallo
 */
public class ClientFollowUp extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -697685799849405763L;
	private static String title = "Client Follow Up";
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlMain;

	private JPanel pnlNorth;
	private JPanel pnlCLientDetails;

	private JPanel pnlCDWest;

	private JPanel pnlCDWestLabel;
	private JLabel lblClient;
	private JLabel lblUnit;

	private JPanel pnlCDWestComponents;

	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;

	private JPanel pnlUnit; 
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;

	private JPanel pnlCDEast;
	private JPanel pnlCDEastLabel;
	private JLabel lblProject;
	private JLabel lblSeqNo;

	private JPanel pnlCDEastComponents;

	private JPanel pnlProject;
	private _JXTextField txtProjID;
	private _JXTextField txtProject;

	private JPanel pnlSeqNo;
	private _JXTextField txtSeqNo;
	private JCheckBox chkFilter;

	private JPanel pnlFollowUpDetails;

	private JPanel pnlFDLabel;
	private JLabel lblPurposeofCall;
	private JLabel lblContactPerson;
	private JLabel lblContactNo;
	private JLabel lblCallStart;

	private JPanel pnlFDComponents;

	private JPanel pnlPurposeOfCall;
	private JComboBox cmbPurposeOfCall;
	private JLabel lblOnCallDate;
	private _JDateChooser dateOnCall;

	private JTextField txtContactPerson;

	private JPanel pnlContactNo;
	private _JXTextField txtContactNo;
	private JLabel lblEmail;
	private _JXTextField txtEmail;

	private JPanel pnlCallDuration;
	private JSpinner spinnerCallStart;
	private JCheckBox chkCallDuration;
	private _JDateChooser dteFrom;
	private JLabel lblCallEnd;
	private JSpinner spinnerCallEnd;
	private JCheckBox chkCallEnd;
	private _JDateChooser dteTo;

	private JPanel pnlCenter;
	private JSplitPane splitCenter;
	private JPanel pnlConversationDetails;
	private JScrollPane scrollConversation;
	private JTextArea txtAreaConversation;

	private JPanel pnlFollowUp;
	private _JTableMain tblFollowUp;
	private JScrollPane scrollFollowUp;
	private modelClientFollowup modelFollowUp;
	private JList rowHeaderFollowUp;

	private JPanel pnlSouth;

	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	
	private String entity_id;
	private String proj_id;
	private String pbl_id;
	private String seq_no;

	public ClientFollowUp() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ClientFollowUp(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public ClientFollowUp(String entity_id, String proj_id, String pbl_id, String seq_no){
		super(title, true, true, true, true);
		initGUI();
		displayFollowUpDetails(entity_id, proj_id, pbl_id, seq_no, true);
	}

	@Override
	public void initGUI() {
		//this.setLayout(new SpringLayout());
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setLayout(new GridLayout(2, 1, 3, 3));
				{
					pnlCLientDetails = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlNorth.add(pnlCLientDetails, BorderLayout.NORTH);
					pnlCLientDetails.setBorder(JTBorderFactory.createTitleBorder("Client Details"));
					{
						pnlCDWest = new JPanel(new BorderLayout(3, 3));
						pnlCLientDetails.add(pnlCDWest, BorderLayout.WEST);
						{
							pnlCDWestLabel = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlCDWest.add(pnlCDWestLabel, BorderLayout.WEST);
							{
								lblClient = new JLabel("Client");
								pnlCDWestLabel.add(lblClient);
							}
							{
								lblUnit = new JLabel("Unit ID");
								pnlCDWestLabel.add(lblUnit);
							}
						}
						{
							pnlCDWestComponents = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlCDWest.add(pnlCDWestComponents, BorderLayout.CENTER);
							{
								pnlClient = new JPanel(new BorderLayout(3, 3));
								pnlCDWestComponents.add(pnlClient);
								{
									lookupClient = new _JLookup(null, "Select", 0);
									pnlClient.add(lookupClient, BorderLayout.WEST);
									lookupClient.setPreferredSize(new Dimension(100, 0));
									lookupClient.setLookupSQL(sqlClient());
									lookupClient.setFilterName(true);
									lookupClient.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
												Object [] data = ((_JLookup) event.getSource()).getDataSet();
												FncSystem.out("Display sql for Lookup of CLient", lookupClient.getLookupSQL());

											if(data != null){
												entity_id = (String) data[0];
												String entity_name = (String) data[1];
												proj_id = (String) data[3];
												String proj_desc = (String) data[5];
												String unit_id = (String) data[2];
												String unit_desc = (String) data[6];
												seq_no = data[4].toString();
												pbl_id = (String) data[10];

												txtClient.setText(entity_name);
												txtProjID.setText(proj_id);
												txtProject.setText(proj_desc);
												txtUnitID.setText(unit_id);
												txtUnitDesc.setText(unit_desc);
												txtSeqNo.setText(seq_no);

												cancelFollowUp();

												displayFollowUpDetails(entity_id, proj_id, pbl_id, seq_no, false);
												
												btnState(true, false, false, false);
											}
										}
									});
								}
								{
									txtClient = new _JXTextField();
									pnlClient.add(txtClient, BorderLayout.CENTER);
								}
							}
							{
								pnlUnit = new JPanel(new BorderLayout(3, 3));
								pnlCDWestComponents.add(pnlUnit);
								{
									txtUnitID = new _JXTextField();
									pnlUnit.add(txtUnitID, BorderLayout.WEST);
									txtUnitID.setPreferredSize(new Dimension(100, 0));
									txtUnitID.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									txtUnitDesc = new _JXTextField();
									pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
								}
							}
						}
					}
					{
						pnlCDEast = new JPanel(new BorderLayout(3, 3));
						pnlCLientDetails.add(pnlCDEast, BorderLayout.EAST);
						{
							pnlCDEastLabel = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlCDEast.add(pnlCDEastLabel, BorderLayout.WEST);
							{
								lblProject = new JLabel("Project");
								pnlCDEastLabel.add(lblProject);
							}
							{
								lblSeqNo = new JLabel("Seq. No");
								pnlCDEastLabel.add(lblSeqNo);
							}
						}
						{
							pnlCDEastComponents = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlCDEast.add(pnlCDEastComponents, BorderLayout.CENTER);
							{
								pnlProject = new JPanel(new BorderLayout(3, 3));
								pnlCDEastComponents.add(pnlProject);
								{
									txtProjID = new _JXTextField();
									pnlProject.add(txtProjID, BorderLayout.WEST);
									txtProjID.setPreferredSize(new Dimension(50, 0));
									txtProjID.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									txtProject = new _JXTextField();
									pnlProject.add(txtProject, BorderLayout.CENTER);
								}
							}
							{
								pnlSeqNo = new JPanel(new BorderLayout(3, 3));
								pnlCDEastComponents.add(pnlSeqNo);
								{
									txtSeqNo = new _JXTextField();
									pnlSeqNo.add(txtSeqNo, BorderLayout.WEST);
									txtSeqNo.setPreferredSize(new Dimension(50, 0));
									txtSeqNo.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									chkFilter = new JCheckBox("Filter By Dept.");
									pnlSeqNo.add(chkFilter, BorderLayout.EAST);
									chkFilter.addItemListener(new ItemListener() {
										
										@Override
										public void itemStateChanged(ItemEvent e) {
											//displayFollowUp(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getE, seq_no, from_card);
											displayFollowUpDetails(entity_id, proj_id, pbl_id, seq_no, false);
										}
									});
								}
							}
						}
					}
				}
				{
					pnlFollowUpDetails = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlFollowUpDetails, BorderLayout.SOUTH);
					pnlFollowUpDetails.setBorder(JTBorderFactory.createTitleBorder("Follow Up Details"));
					{
						pnlFDLabel = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlFollowUpDetails.add(pnlFDLabel, BorderLayout.WEST);
						{
							lblPurposeofCall = new JLabel("Purpose of Call");
							pnlFDLabel.add(lblPurposeofCall);
						}
						{
							lblContactPerson = new JLabel("Contact Person");
							pnlFDLabel.add(lblContactPerson);
						}
						{
							lblContactNo = new JLabel("Contact No");
							pnlFDLabel.add(lblContactNo);
						}
						{
							lblCallStart = new JLabel("Call Start");
							pnlFDLabel.add(lblCallStart);
						}
					}
					{
						pnlFDComponents = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlFollowUpDetails.add(pnlFDComponents, BorderLayout.CENTER);
						{
							pnlPurposeOfCall = new JPanel(new BorderLayout(3, 3));
							pnlFDComponents.add(pnlPurposeOfCall);
							{
								cmbPurposeOfCall = new JComboBox(getPurposeofCall());
								pnlPurposeOfCall.add(cmbPurposeOfCall, BorderLayout.WEST);
								cmbPurposeOfCall.setPreferredSize(new Dimension(150, 0));
							}
							{
								lblOnCallDate = new JLabel("Follow On Call Date", JLabel.TRAILING);
								pnlPurposeOfCall.add(lblOnCallDate);
							}
							{
								dateOnCall = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlPurposeOfCall.add(dateOnCall, BorderLayout.EAST);
								dateOnCall.setPreferredSize(new Dimension(150, 0));
							}
						}
						{
							txtContactPerson = new _JXTextField();
							pnlFDComponents.add(txtContactPerson);
						}
						{
							pnlContactNo = new JPanel(new BorderLayout(3, 3));
							pnlFDComponents.add(pnlContactNo);
							{
								txtContactNo = new _JXTextField();
								pnlContactNo.add(txtContactNo, BorderLayout.WEST);
								txtContactNo.setPreferredSize(new Dimension(150, 0));
							}
							{
								lblEmail = new JLabel("Email", JLabel.TRAILING);
								pnlContactNo.add(lblEmail);
							}
							{
								txtEmail = new _JXTextField();
								pnlContactNo.add(txtEmail, BorderLayout.EAST);
								txtEmail.setPreferredSize(new Dimension(150, 0));

							}
						}
						{
							pnlCallDuration = new JPanel(new BorderLayout(3, 3));
							pnlFDComponents.add(pnlCallDuration);
							/*{
								dteFrom = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlCallDuration.add(dteFrom, BorderLayout.WEST);
								dteFrom.setPreferredSize(new Dimension(150, 0));
							}*/
							{
								JPanel pnlCallStart = new JPanel(new BorderLayout(3, 3));
								pnlCallDuration.add(pnlCallStart, BorderLayout.WEST);
								{
									chkCallDuration = new JCheckBox();
									pnlCallStart.add(chkCallDuration, BorderLayout.WEST);
									chkCallDuration.setToolTipText("Select this when no call duration");
									chkCallDuration.addItemListener(new ItemListener() {
										
										@Override
										public void itemStateChanged(ItemEvent e) {
											Boolean selected = e.getStateChange() == ItemEvent.SELECTED;
											
											if(selected && chkCallDuration.isEnabled()){
												spinnerCallStart.setEnabled(false);
												spinnerCallEnd.setEnabled(false);
											}else{
												spinnerCallStart.setEnabled(true);
												spinnerCallEnd.setEnabled(true);
											}
											
										}
									});
								}
								{
									SpinnerDateModel model = new SpinnerDateModel(new Date(),  null, null, Calendar.MINUTE);

									spinnerCallStart = new JSpinner(model);

									JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerCallStart,"hh:mm a");
									
									DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
									formatter.setAllowsInvalid(true); 
									formatter.setOverwriteMode(true);

									spinnerCallStart.setEditor(editor);

									pnlCallStart.add(spinnerCallStart,BorderLayout.CENTER);
									spinnerCallStart.setPreferredSize(new Dimension(100,30 ));
								}
							}
							
							{
								lblCallEnd = new JLabel("Call End", JLabel.TRAILING);
								pnlCallDuration.add(lblCallEnd);
							}
							{
								SpinnerDateModel model = new SpinnerDateModel(new Date(),  null, null, Calendar.MINUTE);

								spinnerCallEnd = new JSpinner(model);

								JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerCallEnd,"hh:mm a");
								DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
								formatter.setAllowsInvalid(true); 
								formatter.setOverwriteMode(true);

								spinnerCallEnd.setEditor(editor);

								pnlCallDuration.add(spinnerCallEnd,BorderLayout.EAST); 
								spinnerCallEnd.setPreferredSize(new Dimension(100, 30));
							}
							/*{
								JPanel pnlCallEnd = new JPanel(new BorderLayout(3, 3));
								pnlCallDuration.add(pnlCallEnd, BorderLayout.EAST);
								{
									chkCallEnd = new JCheckBox();
									pnlCallEnd.add(chkCallEnd, BorderLayout.WEST);
								}
								{
									SpinnerDateModel model = new SpinnerDateModel(new Date(),  null, null, Calendar.MINUTE);

									spinnerCallEnd = new JSpinner(model);

									JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerCallEnd,"hh:mm a");
									DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
									formatter.setAllowsInvalid(true); 
									formatter.setOverwriteMode(true);

									spinnerCallEnd.setEditor(editor);

									pnlCallEnd.add(spinnerCallEnd,BorderLayout.EAST); 
									spinnerCallEnd.setPreferredSize(new Dimension(100,30 ));
								}
							}*/
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				//pnlCenter.setLayout(new GridLayout(2, 1, 3, 3));
				{
					splitCenter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
					pnlCenter.add(splitCenter);
					splitCenter.setOneTouchExpandable(true);
					splitCenter.setResizeWeight(.3d);
					{
						pnlConversationDetails = new JPanel(new BorderLayout(3, 3));
						splitCenter.add(pnlConversationDetails, JSplitPane.LEFT);
						pnlConversationDetails.setBorder(JTBorderFactory.createTitleBorder("Conversation Details"));
						pnlConversationDetails.setPreferredSize(new Dimension(0, 250));
						{
							scrollConversation = new JScrollPane();
							pnlConversationDetails.add(scrollConversation);
							{
								txtAreaConversation = new JTextArea();
								scrollConversation.setViewportView(txtAreaConversation);
								txtAreaConversation.setLineWrap(true);
								txtAreaConversation.setBorder(lineBorder);
								txtAreaConversation.setEditable(false);
							}
						}
					}
					{
						pnlFollowUp = new JPanel(new BorderLayout(3, 3));
						splitCenter.add(pnlFollowUp, JSplitPane.RIGHT);
						pnlFollowUp.setBorder(JTBorderFactory.createTitleBorder("Follow Up List"));
						{
							scrollFollowUp = new JScrollPane();
							pnlFollowUp.add(scrollFollowUp);
							scrollFollowUp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							{
								modelFollowUp = new modelClientFollowup();
								tblFollowUp = new _JTableMain(modelFollowUp);
								scrollFollowUp.setViewportView(tblFollowUp);
								tblFollowUp.hideColumns("Rec. ID", "Emp. ID");
								tblFollowUp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								tblFollowUp.setSortable(false);
								/*TableColumnModel colModel = tblFollowUp.getColumnModel();
						        TableColumn col = colModel.getColumn(1);
						        col.setCellRenderer(new _JTextAreaRenderer());
						        tblFollowUp.setRowHeight(100);*/

								modelFollowUp.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										
										((DefaultListModel) rowHeaderFollowUp.getModel()).addElement(modelFollowUp.getRowCount());
										
										if(modelFollowUp.getRowCount() == 0){
											rowHeaderFollowUp.setModel(new DefaultListModel());
										}
									}
								});

								tblFollowUp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

									public void valueChanged(ListSelectionEvent arg0) {
										if(!arg0.getValueIsAdjusting()){
											try {
												int row = tblFollowUp.getSelectedRow();

												String purpose_of_call = (String) modelFollowUp.getValueAt(row, 1);
												String contact_person = (String) modelFollowUp.getValueAt(row, 3);
												String contact_no = (String) modelFollowUp.getValueAt(row, 4);
												Date date_created = (Date) modelFollowUp.getValueAt(row, 5);
												Date follow_oncall_date = (Date) modelFollowUp.getValueAt(row, 6);
												String email = (String) modelFollowUp.getValueAt(row, 7);
												Time call_start = (Time) modelFollowUp.getValueAt(row, 8);
												Time call_end = (Time) modelFollowUp.getValueAt(row, 9);
												String conversation_details = (String) modelFollowUp.getValueAt(row, 2);

												cmbPurposeOfCall.setSelectedItem(purpose_of_call);
												txtContactPerson.setText(contact_person);
												txtContactNo.setText(contact_no);
												dateOnCall.setDate(follow_oncall_date);
												txtEmail.setText(email);
												/*dteFrom.setDate(date_from);
												dteTo.setDate(date_to);*/
												
												/*spinnerCallStart.setValue(call_start);
												spinnerCallEnd.setValue(call_end);*/
												
												if(call_start != null){
													spinnerCallStart.setValue(call_start);
												}
												
												if(call_end != null){
													spinnerCallEnd.setValue(call_end);
												}
												
												txtAreaConversation.setText(conversation_details);

												btnState(true, true, false, false);
											} catch (ArrayIndexOutOfBoundsException e) {}
										}
									}
								});
							}
							{
								rowHeaderFollowUp = tblFollowUp.getRowHeader();
								//rowHeaderFollowUp.setFixedCellHeight(100);
								rowHeaderFollowUp.setModel(new DefaultListModel());
								scrollFollowUp.setRowHeaderView(rowHeaderFollowUp);
								scrollFollowUp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 4, 3, 3));
				{
					btnNew = new JButton("New");
					pnlSouth.add(btnNew);
					btnNew.setActionCommand(btnNew.getText());
					btnNew.setMnemonic(KeyEvent.VK_N);
					btnNew.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand(btnEdit.getText());
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand(btnSave.getText());
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand(btnCancel.getText());
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}
		cancelFollowUp();
	} //XXX END OF INIT GUI

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private String sqlClient(){

		return "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as \"Name\", \n" + 
				"trim(b.unit_id) as \"Unit Id\", trim(a.projcode) as \"Proj. ID\",\n" + 
				"a.seq_no as \"Seq\", get_project_name(a.projcode) as \"Project Name\",\n" + 
				"trim(b.description) as \"Unit Desc\", trim(c.status_desc) as \"Unit Status\", \n" + 
				"trim(a.model_id) as \"Model ID\", trim(get_model_name(a.model_id)) as \"Model Name\", \n"+
				"trim(a.pbl_id) as \"PBL\" \n" + 
				"from rf_sold_unit a\n" + 
				"left join mf_unit_info b on TRIM(b.pbl_id) = a.pbl_id and b.proj_id = a.projcode\n" + 
				"and coalesce(b.server_id, '') = coalesce(a.server_id, '')\n" +
				"and coalesce(b.proj_server, '') = coalesce(a.proj_server, '')\n" +
				"left join mf_buyer_status c on c.byrstatus_id = a.currentstatus \n"+
				"and coalesce(c.server_id, '') = coalesce(a.server_id, '')\n" + 
				"where a.currentstatus is not null";

	}
	
	public static Date dateFormat(String time){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("hh:mm:ss a");
		
		//System.out.printf("Display time formatter: %s%n", formatter);
		try {
			date = (Date)formatter.parse(time);
		} catch (ParseException e) {
		} 

		System.out.println("********************" + date);
		return date;
	}

	private Object[] getPurposeofCall() {
		ArrayList<Object> purposeofcall = new ArrayList<Object>();

		pgSelect db = new pgSelect();
		db.select("select trim(description) from mf_purpose_of_call where status = 'A' ");
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				purposeofcall.add(db.getResult()[x][0]);
			}
		}
		return purposeofcall.toArray();
	}

	public void newPhoneFollowup(){//NEW BUTTON ACTIONS FOR THIS PANEL

		clearPhoneFollowUp();

		cmbPurposeOfCall.setEnabled(true);

		dateOnCall.setEditable(true);
		dateOnCall.setDate(Calendar.getInstance().getTime());
		dateOnCall.getCalendarButton().setEnabled(true);

		txtContactPerson.setEditable(true);
		txtContactNo.setEditable(true);
		txtEmail.setEditable(true);
		/*dteFrom.setEditable(true);
		dteFrom.getCalendarButton().setEnabled(true);

		dteTo.setEditable(true);
		dteTo.getCalendarButton().setEnabled(true);*/
		txtAreaConversation.setEditable(true);
		chkCallDuration.setEnabled(true);
		spinnerCallStart.setEnabled(true);
		spinnerCallEnd.setEnabled(true);

		try{
			tblFollowUp.clearSelection();
		} catch (ArrayIndexOutOfBoundsException e) {}

		tblFollowUp.setEnabled(false);
		rowHeaderFollowUp.setEnabled(false);
		
		btnState(false, false, true, true);
	}

	public void editPhoneFollowUp(){//PUT CONTROL HERE FOR THE EDITING OF THE PHONE FOLLOW UP THAT ONLY EDITS A DAY AFTER 

		int row = tblFollowUp.convertRowIndexToModel(tblFollowUp.getSelectedRow());

		Integer rec_id = (Integer) modelFollowUp.getValueAt(row, 0);
		String emp_code = (String) modelFollowUp.getValueAt(row, 10);

		if(isFollowupEditable(rec_id)){
			if(isAuthorizedToEdit(emp_code)){
				cmbPurposeOfCall.setEnabled(true);
				txtContactPerson.setEditable(true);
				txtContactNo.setEditable(true);
				txtAreaConversation.setEditable(true);
				tblFollowUp.setEnabled(false);
				rowHeaderFollowUp.setEnabled(false);

				btnState(false, false, true, true);

			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Sorry you are not authorized to edit this entry", "Edit", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot edit", "Edit", JOptionPane.WARNING_MESSAGE);
		}
	}

	public boolean toSave(){//CHECKING OF THE REQUIRED FIELDS BEFORE SAVING
		
		/*if (txtContactPerson.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Contact Person"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/

		if (txtAreaConversation.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Conversation Details"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean saveFollowUp(String entity_id, String unit_id, String proj_code, String seq_no){//SAVING FOR THE PHONE FOLLOW UP 

		pgUpdate db = new pgUpdate();

		String purpose_call = (String) cmbPurposeOfCall.getSelectedItem();
		String contact_person = txtContactPerson.getText().replace("'", "''").trim();
		Date oncall_date = dateOnCall.getDate();
		String convo_detail = txtAreaConversation.getText().replace("'", "''");
		String contact_no = txtContactNo.getText();
		String email = txtEmail.getText();
		/*Date date_from = dteFrom.getDate();
		Date date_to = dteTo.getDate();*/
		Boolean call_duration = chkCallDuration.isSelected();
		String call_start = "";
		String call_end = "";
		
		if(call_duration){
			call_start = null;
			call_end = null;
		}else{
			call_start = new SimpleDateFormat("hh:mm:ss a").format(spinnerCallStart.getValue());
			call_end = new SimpleDateFormat("hh:mm:ss a").format(spinnerCallEnd.getValue());
		}
		
		if(tblFollowUp.getSelectedRows().length ==1){//UPDATE HERE
			int row = tblFollowUp.getSelectedRow();
			Integer rec_id = (Integer) modelFollowUp.getValueAt(row, 0);

			String sql = "UPDATE rf_complaint_phone_followup\n" + 
					"SET purpose_of_call_id= (select rec_id from mf_purpose_of_call where description = '"+purpose_call+"'), \n"+
					"contact_person= '"+contact_person+"', follow_on_call_date= NULLIF('"+oncall_date+"', 'null')::TIMESTAMP, \n" + 
					"conversation= '"+convo_detail+"', contact_no= '"+contact_no+"'\n" + 
					"WHERE entity_id = '"+entity_id+"' \n"+
					"AND proj_id = '"+proj_code+"' \n"+
					"AND unit_id = '"+unit_id+"' \n"+
					"AND seq_no = "+seq_no+" \n"+
					"AND rec_id = "+rec_id+" \n";
			db.executeUpdate(sql, true);
		}else{
			String sql = "INSERT INTO rf_complaint_phone_followup(entity_id, pbl_id, seq_no, unit_id, proj_id, purpose_of_call_id, \n" + 
					"contact_person, follow_on_call_date, status_id, created_by, date_created, conversation, contact_no, \n"+
					"email, call_start, call_end)\n" + 
					"VALUES ('"+entity_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+unit_id+"', '"+proj_code+"', \n"+
					"(select rec_id from mf_purpose_of_call where description = '"+purpose_call+"'), \n" + 
					"'"+contact_person+"', nullif('"+oncall_date+"', 'null')::TIMESTAMP, 'A', '"+UserInfo.EmployeeCode+"', now(), \n" + 
					"'"+convo_detail+"', '"+contact_no+"', '"+email+"', NULLIF('"+call_start+"', 'null')::TIME WITHOUT TIME ZONE, \n"+
					"NULLIF('"+call_end+"', 'null')::TIME WITHOUT TIME ZONE);\n";
			db.executeUpdate(sql, true);
		}

		db.commit();
		cancelFollowUp();

		return true;
	}

	private void cancelFollowUp(){

		cmbPurposeOfCall.setEnabled(false);
		dateOnCall.setDate(null);
		txtContactPerson.setText("");
		txtContactNo.setText("");
		txtEmail.setText("");
		/*dteFrom.setDate(null);
		dteTo.setDate(null);*/
		/*spinnerCallStart.setValue(null);
		spinnerCallEnd.setValue(null);*/
		dateOnCall.setEditable(false);
		dateOnCall.getCalendarButton().setEnabled(false);
		chkCallDuration.setEnabled(false);
		spinnerCallStart.setEnabled(false);
		spinnerCallEnd.setEnabled(false);
		txtContactPerson.setEditable(false);
		txtContactNo.setEditable(false);
		txtEmail.setEditable(false);
		/*dteFrom.setEditable(false);
		dteFrom.getCalendarButton().setEnabled(false);
		dteTo.setEditable(false);
		dteTo.getCalendarButton().setEnabled(false);*/

		txtAreaConversation.setText("");
		tblFollowUp.clearSelection();
		txtAreaConversation.setEditable(false);
		tblFollowUp.setEnabled(true);
		rowHeaderFollowUp.setEnabled(true);
		//rowHeaderFollowUp.setModel(new DefaultListModel());
		
		if(lookupClient.getValue() != null){
			btnState(true, false, false, false);
		}else{
			btnState(false, false, false, false);
		}
	}
	
	private void displayFollowUpDetails(String entity_id, String proj_id, String pbl_id, String seq_no, Boolean from_card){
		this.entity_id = entity_id;
		this.proj_id = proj_id;
		this.pbl_id = pbl_id;
		this.seq_no = seq_no;
		
		Boolean isSelected = chkFilter.isSelected();
		
		if(from_card){
			String sql = "SELECT get_client_name('"+entity_id+"'), get_project_name('"+proj_id+"'), get_unit_id('"+proj_id+"', '"+pbl_id+"'), \n"+
						 "get_merge_unit_desc(get_unit_id('"+proj_id+"', '"+pbl_id+"'))";
			
			pgSelect db = new pgSelect();
			db.select(sql);
			
			//FncSystem.out("Display ", strSQL);
			
			if(db.isNotNull()){
				String entity_name = (String) db.getResult()[0][0];
				String proj_name = (String) db.getResult()[0][1];
				String unit_id = (String) db.getResult()[0][2];
				String unit_desc = (String) db.getResult()[0][3];
				
				lookupClient.setValue(entity_id);
				txtClient.setText(entity_name);
				txtProjID.setText(proj_id);
				txtProject.setText(proj_name);
				txtUnitID.setText(unit_id);
				txtUnitDesc.setText(unit_desc);
				txtSeqNo.setText(seq_no);
			}
			
			btnState(true, false, false, false);
		}
		//modelFollowUp.clear();
		
		FncTables.clearTable(modelFollowUp);
		
		String sql = "SELECT * FROM view_client_followup('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+isSelected+", '"+UserInfo.Department+"')";

		FncSystem.out("Display Phone Follow Up", sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelFollowUp.addRow(db.getResult()[x]);
			}
			scrollFollowUp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblFollowUp.getRowCount())));
			tblFollowUp.packAll();
			//tblFollowUp.getColumn(1).setPreferredWidth(500);
		}
	}

	//CHECKS IF THE NUMBER OF DAYS PAST SINCE THE PHONE FOLLOW UP IS MORE THAN 1
	private Boolean isFollowupEditable(Integer rec_id){

		pgSelect db = new pgSelect();
		String sql = "select case when daterange_subdiff(now()::DATE, date_created::DATE) <= 1 THEN TRUE ELSE FALSE END \n"+
				"from rf_complaint_phone_followup where rec_id = "+rec_id+";\n";

		db.select(sql);

		return (Boolean) db.getResult()[0][0];
	}

	//CHECKS IF THE USER LOGGED IN IS AUTHORIZED TO EDIT THE PHONE FOLLOW UP
	private Boolean isAuthorizedToEdit(String emp_code){
		pgSelect db = new pgSelect();

		String sql = "select case when '"+UserInfo.EmployeeCode+"' = '"+emp_code+"' then true else false end";
		db.select(sql);

		return (Boolean) db.getResult()[0][0];
	}

	public void clearPhoneFollowUp(){//CLEARS THE COMPONENTS IN THIS PANEL
		txtContactPerson.setText("");
		dateOnCall.setDate(null);
		txtAreaConversation.setText("");
		txtContactNo.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("New")){
			newPhoneFollowup();
		}

		if(actionCommand.equals("Edit")){
			editPhoneFollowUp();
		}

		if(actionCommand.equals("Save")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					saveFollowUp(lookupClient.getValue(), txtUnitID.getText(), txtProjID.getText(), txtSeqNo.getText());
					displayFollowUpDetails(entity_id, proj_id, pbl_id, seq_no, false);
					tblFollowUp.setEnabled(true);
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Phone Follow Up has Been Saved", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		if(actionCommand.equals("Cancel")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Clear CLient Follow Up?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				cancelFollowUp();
			}
		}
	}
}
