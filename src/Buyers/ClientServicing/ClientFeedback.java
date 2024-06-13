/**
 * 
 */
package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import tablemodel.modelClientComplaints;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class ClientFeedback extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 5616261506123582059L;
	static Dimension SIZE = new Dimension(1000, 630);

	private static String title = "Client Feedback";
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JPanel pnlNWlabel;
	private JLabel lblClient;
	private JLabel lblUnit;
	private JLabel lblProject;
	private JLabel lblSeqNo;

	private JPanel pnlNWCenter;
	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;
	private JPanel pnlUnit;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;
	private JPanel pnlProject;
	private _JXTextField txtProjID;
	private _JXTextField txtProject;
	private JPanel pnlSeqNo;
	private _JXTextField txtSeqNo;
	private JLabel lblStatus;
	private _JXTextField txtUnitStatus;

	private JPanel pnlNorthEast;
	private JPanel pnlNELabel;
	private JLabel lblModel;
	private JLabel lblConstructed;
	private JLabel lblContractor;

	private JPanel pnlNECenter;
	private JPanel pnlModel;
	private _JXTextField txtModelID;
	private _JXTextField txtModel;
	private _JXTextField txtConstructed;
	private JPanel pnlContractor;
	private _JXTextField txtContractorCode;
	private _JXTextField txtContractor;
	private _JXTextField txtComplaintNo; //REMOVE THIS

	private JPanel pnlCenter;

	private JPanel pnlComplaintList;
	private JScrollPane scrollComplaintList;
	private _JTableMain tblComplaintList;
	private JList rowHeaderComplaintList;
	private modelClientComplaints modelComplaintList;

	private JSplitPane splitComplaintDetails;
	private JPanel pnlComplaintDetails;

	private JPanel pnlCDWest;
	private JPanel pnlCDWestLabel;
	private JLabel lblComplaintType;
	private JLabel lblComplaintSource;
	private JLabel lblConcernedDept;
	private JLabel lblComplainantContactNo;

	private JPanel pnlCDWestComponents;
	private JPanel pnlComplaintType;
	private _JLookup lookupComplaintType;
	private _JXTextField txtComplaintType;

	private JPanel pnlComplaintSource;
	private _JLookup lookupComplaintSource;
	private _JXTextField txtComplaintSource;

	private JPanel pnlConcernedDept;
	private _JLookup lookupConcernedDept;
	private _JXTextField txtConcernedDept;

	private _JXTextField txtConctactNo;

	private JPanel pnlCDEast;
	private JScrollPane scrollComplaintDetails;
	private JTextArea txtAreaComplaintDetails;

	private JPanel pnlCCNorth;
	private JPanel pnlCCNorthWest;
	private JPanel pnlCCNorthWestLabel;
	private JPanel pnlCCNorthWestComponents;

	private JPanel pnlCCNorthEast;

	private JPanel pnlCCCenter;

	private _JTabbedPane tabCenter;
	private pnlClientComplaint pnlClientComp;
	private ClientFollowUp pnlPhoneFollow;
	private pnlActionPlan pnlAction;
	private pnlResolvedby pnlResolved;

	private JPanel pnlSouth;
	private JPanel pnlSouthWest;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSearch;
	private JButton btnSave;
	private JButton btnCancel;

	public ClientFeedback() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ClientFeedback(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public ClientFeedback(String entity_id, String proj_id, String pbl_id, String seq_no){
		super(title, true, true, true, true);
		initGUI();
		displayClientDetails(entity_id, pbl_id, proj_id, seq_no);
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);

		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Client Details"));
			{
				pnlNorthWest = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				{
					pnlNWlabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorthWest.add(pnlNWlabel, BorderLayout.WEST);
					{
						lblClient = new JLabel("Client");
						pnlNWlabel.add(lblClient);
					}
					{
						lblUnit = new JLabel("Unit");
						pnlNWlabel.add(lblUnit);
					}
					{
						lblProject = new JLabel("Project");
						pnlNWlabel.add(lblProject);
					}
					{
						lblSeqNo = new JLabel("Seq. No");
						pnlNWlabel.add(lblSeqNo);
					}
				}
				{
					pnlNWCenter = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorthWest.add(pnlNWCenter, BorderLayout.CENTER);
					{
						pnlClient = new JPanel(new BorderLayout(5, 5));
						pnlNWCenter.add(pnlClient);
						{
							lookupClient = new _JLookup(null, "Select Client", 0);
							pnlClient.add(lookupClient, BorderLayout.WEST);
							lookupClient.setFilterName(true);
							lookupClient.setPreferredSize(new Dimension(100, 0));
							lookupClient.setLookupSQL(sqlClientID());
							lookupClient.addLookupListener(new LookupListener() {

								@Override
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									FncSystem.out("Display sql for Clients", sqlClientID());

									if(data != null){
										String entity_id = (String) data[0];
										String entity_name = (String) data[1];
										String unit_id = (String) data[2];
										String proj_id = (String) data[3];
										Integer seq_no = (Integer) data[4];
										String proj_name = (String) data[5];
										String unit_desc = (String) data[6];
										String unit_status = (String) data[7];
										String model_id =(String) data[8];
										String model_desc = (String) data[9];

										txtClient.setText(entity_name);
										txtUnitID.setText(unit_id);
										txtUnitDesc.setText(unit_desc);
										txtProjID.setText(proj_id);
										txtProject.setText(proj_name);
										txtSeqNo.setText(seq_no.toString());
										txtUnitStatus.setText(unit_status);
										txtModelID.setText(model_id);
										txtModel.setText(model_desc);

										btnState(true, true, false, false, false);
										tabCenter.setEnabled(true);

										displayComplaintList(entity_id, proj_id, unit_id, seq_no.toString());

										//txtComplaintNo.setText("");

										//pnlClientComp.clearClientComplaint();
										/*pnlClientComp.displayComplaintList(entity_id, proj_id, unit_id, seq_no.toString());
										//pnlAction.clearResolvedBy();
										//pnlAction.clearTargetDate();
										pnlAction.clearFeedbackThrough(false, false, false, false, false, false);*/
										/*pnlPhoneFollow.clearPhoneFollowUp();
										pnlPhoneFollow.displayPhoneFollowUp(entity_id, unit_id, proj_id, seq_no.toString());*/
									}
								}
							});
						}
						{
							txtClient = new _JXTextField("Name");
							pnlClient.add(txtClient, BorderLayout.CENTER);
						}
					}
					{
						pnlUnit = new JPanel(new BorderLayout(5, 5));
						pnlNWCenter.add(pnlUnit);
						{
							txtUnitID = new _JXTextField("ID");
							pnlUnit.add(txtUnitID, BorderLayout.WEST);
							txtUnitID.setPreferredSize(new Dimension(100, 0));
							txtUnitID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtUnitDesc = new _JXTextField("Description");
							pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
						}
					}
					{
						pnlProject = new JPanel(new BorderLayout(5, 5));
						pnlNWCenter.add(pnlProject);
						{
							txtProjID = new _JXTextField("ID");
							pnlProject.add(txtProjID, BorderLayout.WEST);
							txtProjID.setPreferredSize(new Dimension(100, 0));
							txtProjID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtProject = new _JXTextField("Decription");
							pnlProject.add(txtProject, BorderLayout.CENTER);
						}
					}
					{
						pnlSeqNo = new JPanel(new BorderLayout(5, 5));
						pnlNWCenter.add(pnlSeqNo);
						{
							txtSeqNo = new _JXTextField("Seq.");
							pnlSeqNo.add(txtSeqNo, BorderLayout.WEST);
							txtSeqNo.setPreferredSize(new Dimension(50, 0));
							txtSeqNo.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lblStatus = new JLabel("Unit Status", JLabel.TRAILING);
							pnlSeqNo.add(lblStatus);
						}
						{
							txtUnitStatus = new _JXTextField("Status");
							pnlSeqNo.add(txtUnitStatus, BorderLayout.EAST);
							txtUnitStatus.setPreferredSize(new Dimension(200, 0));
						}
					}
				}
			}
			{
				pnlNorthEast = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
				{
					pnlNELabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorthEast.add(pnlNELabel, BorderLayout.WEST);
					{
						lblModel = new JLabel("Model");
						pnlNELabel.add(lblModel);
					}
					{
						lblConstructed = new JLabel("Constructed");
						pnlNELabel.add(lblConstructed);
					}
					{
						lblContractor = new JLabel("Contractor");
						pnlNELabel.add(lblContractor);
					}
					/*{
						lblComplaintNo = new JLabel("Complaint No.");
						pnlNELabel.add(lblComplaintNo);
					}*/
				}
				{
					pnlNECenter = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorthEast.add(pnlNECenter, BorderLayout.CENTER);
					{
						pnlModel = new JPanel(new BorderLayout(5, 5));
						pnlNECenter.add(pnlModel);
						{
							txtModelID = new _JXTextField("ID");
							pnlModel.add(txtModelID, BorderLayout.WEST);
							txtModelID.setPreferredSize(new Dimension(100, 0));
							txtModelID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtModel = new _JXTextField("Description");
							pnlModel.add(txtModel, BorderLayout.CENTER);
						}
					}
					{
						txtConstructed = new _JXTextField();
						pnlNECenter.add(txtConstructed);
					}
					{
						pnlContractor = new JPanel(new BorderLayout(5, 5));
						pnlNECenter.add(pnlContractor);
						{
							txtContractorCode = new _JXTextField("ID");
							pnlContractor.add(txtContractorCode, BorderLayout.WEST);
							txtContractorCode.setPreferredSize(new Dimension(100, 0));
							txtContractorCode.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtContractor = new _JXTextField("Description");
							pnlContractor.add(txtContractor, BorderLayout.CENTER);
						}
					}
					/*{
						txtComplaintNo = new _JXTextField("Complaint No.");
						pnlNECenter.add(txtComplaintNo);
					}*/
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(3, 3));
			pnlMain.add(pnlCenter, BorderLayout.CENTER);
			{
				pnlComplaintList = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlComplaintList, BorderLayout.WEST);
				pnlComplaintList.setBorder(JTBorderFactory.createTitleBorder("Complaint List"));
				pnlComplaintList.setPreferredSize(new Dimension(300, 0));
				{
					scrollComplaintList = new JScrollPane();
					pnlComplaintList.add(scrollComplaintList, BorderLayout.CENTER);
					scrollComplaintList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelComplaintList = new modelClientComplaints();
						tblComplaintList = new _JTableMain(modelComplaintList);
						scrollComplaintList.setViewportView(tblComplaintList);
						tblComplaintList.hideColumns("Category Code", "Source Code", "Addressed to Code", "Complaint Details");
						tblComplaintList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblComplaintList.setSortable(false);

						modelComplaintList.addTableModelListener(new TableModelListener() {

							@Override
							public void tableChanged(TableModelEvent e) {
								((DefaultListModel) rowHeaderComplaintList.getModel()).addElement(modelComplaintList.getRowCount());

								if(modelComplaintList.getRowCount() == 0){
									rowHeaderComplaintList.setModel(new DefaultListModel());
								}
							}
						});

						tblComplaintList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

							@Override
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){
									if(tblComplaintList.getSelectedRows().length == 1){
										int selected_row = tblComplaintList.convertRowIndexToModel(tblComplaintList.getSelectedRow());

										String comp_no = (String) modelComplaintList.getValueAt(selected_row, 0);
										String ctgry_code = (String) modelComplaintList.getValueAt(selected_row, 1);
										String ctgry_desc = (String) modelComplaintList.getValueAt(selected_row, 2);
										String src_id = (String) modelComplaintList.getValueAt(selected_row, 3);
										String src_desc = (String) modelComplaintList.getValueAt(selected_row, 4);
										String dept_code = (String) modelComplaintList.getValueAt(selected_row, 5);
										String dept_name = (String) modelComplaintList.getValueAt(selected_row, 6);
										String contact_no = (String) modelComplaintList.getValueAt(selected_row, 7);
										String comp_details = (String) modelComplaintList.getValueAt(selected_row, 8);

										/*lookupComplaintType.setValue(ctgry_code);
										txtComplaintType.setText(ctgry_desc);
										lookupComplaintSource.setValue(src_id);
										txtComplaintSource.setText(src_desc);
										lookupConcernedDept.setValue(dept_code);
										txtConcernedDept.setText(dept_name);
										txtConctactNo.setText(contact_no);
										txtAreaComplaintDetails.setText(comp_details);*/
										
										if(isEditable(comp_no)){
											System.out.println("Editable");
											btnState(true, true, false, false);
										}else{
											System.out.println("Not Editable");
											btnState(true, false, false, false);
										}
										
										pnlState(true, true, true);
										
										pnlClientComp.displayComplaints(comp_no);
										pnlAction.displayActionPlan(comp_no);
										pnlResolved.displayResolvedby(comp_no);
										
									}
								}
							}
						});
					}
					{
						rowHeaderComplaintList = tblComplaintList.getRowHeader();
						rowHeaderComplaintList.setModel(new DefaultListModel());
						scrollComplaintList.setRowHeaderView(rowHeaderComplaintList);
						scrollComplaintList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				tabCenter = new _JTabbedPane();
				pnlCenter.add(tabCenter, BorderLayout.CENTER);
				{
					pnlClientComp = new pnlClientComplaint(this);
					tabCenter.add("Complaints", pnlClientComp);
				}
				{
					pnlAction = new pnlActionPlan(this);
					tabCenter.add("Action Plan", pnlAction);
				}
				{
					pnlResolved = new pnlResolvedby(this);
					tabCenter.add("Resolved By", pnlResolved);
				}
				
				tabCenter.addChangeListener(new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						int selected_tab = ((_JTabbedPane) e.getSource()).getSelectedIndex();
						
						if(selected_tab == 0){
							
							lookupClient.setEditable(true);
							tblComplaintList.setEnabled(true);
							rowHeaderComplaintList.setEnabled(true);
							tblComplaintList.clearSelection();
							pnlClientComp.cancelClientComplaint();
							
							btnState(true, false, false, false);
							pnlState(true, false, false);
						}
						if(selected_tab == 1){
							
							lookupClient.setEditable(false);
							tblComplaintList.setEnabled(false);
							rowHeaderComplaintList.setEnabled(false);
							pnlAction.cancelActionplan();
							
							btnState(true, false, false, false);
							
						}
						if(selected_tab == 2){
							lookupClient.setEditable(false);
							tblComplaintList.setEnabled(false);
							rowHeaderComplaintList.setEnabled(false);
							pnlResolved.cancelResolved();
							
							btnState(true, false, false, false);
						}
					}
				});
			}
		}
		{
			pnlSouth = new JPanel(new GridLayout(1, 4, 5, 5));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			{
				btnNew = new JButton("New");
				pnlSouth.add(btnNew);
				btnNew.setActionCommand(btnNew.getText());
				btnNew.setMnemonic(KeyEvent.VK_N);
				btnNew.addActionListener(this);
				btnNew.setPreferredSize(new Dimension(150, 35));
			}
			{
				btnEdit = new JButton("Edit");
				pnlSouth.add(btnEdit);
				btnEdit.setActionCommand(btnEdit.getText());
				btnEdit.setMnemonic(KeyEvent.VK_E);
				btnEdit.addActionListener(this);
				btnEdit.setPreferredSize(new Dimension(150, 35));
			}
			{
				btnSave = new JButton("Save");
				pnlSouth.add(btnSave);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.setMnemonic(KeyEvent.VK_S);
				btnSave.addActionListener(this);
				btnSave.setPreferredSize(new Dimension(150, 35));
			}
			{
				btnCancel = new JButton("Cancel");
				pnlSouth.add(btnCancel);
				btnCancel.setActionCommand(btnCancel.getText());
				btnCancel.setMnemonic(KeyEvent.VK_C);
				btnCancel.addActionListener(this);
				btnCancel.setPreferredSize(new Dimension(150, 35));
			}
		}
		this.setComponentsEditable(pnlNorth, false);
		lookupClient.setEditable(true);
		btnState(false, false, false, false);
		pnlState(true, false, false);
		
	}//XXX END OF INIT GUI

	public void pnlState(Boolean CC, Boolean AP, Boolean RB ,Boolean PFU){
		tabCenter.setEnabledAt(0, CC);
		tabCenter.setEnabledAt(1, AP);
		tabCenter.setEnabledAt(2, RB);
	}

	public void pnlState(Boolean CC ,Boolean AP, Boolean RB){
		tabCenter.setEnabledAt(0, CC);
		tabCenter.setEnabledAt(1, AP);
		tabCenter.setEnabledAt(2, RB);
	}

	public void btnState(Boolean sNew, Boolean sSearch, Boolean sEdit, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
		//btnSearch.setEnabled(sSearch);
	}

	public void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private String getNewComplaintNo() {//SETS THE NEW COMPLAINT NUMBER
		
		String comp_no = "";
		
		String sql = "select to_char(max(compl_no)::int + 1, 'FM00000000') from rf_client_complaints";

		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display sql for New Complaint No: %s%n", sql);

		if(db.isNotNull()){
			if(db.getResult()[0][0] == null){
				comp_no = "00000001";
			}else{
				comp_no = (String) db.getResult()[0][0];
			}
		}
		
		return comp_no;
	}

	public boolean isComplaintExisting(String complaint_no){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_client_complaints where compl_no = '"+complaint_no+"'";
		db.select(sql);
		FncSystem.out("Display is complaint existing", sql);

		return db.isNotNull();
	}

	public boolean isComplaintsInspected(String complaint_no){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_complaint_punchlist where compl_no = '"+complaint_no+"' and date_inspection is not null";
		db.select(sql);
		FncSystem.out("Display is complaint inspected", sql);

		return db.isNotNull();
	}

	public String getComplaintCategory(String complaint_no){//CHECKS THE CATEGORY OF THE COMPLAINT
		pgSelect db = new pgSelect();
		String sql = "select category_code from rf_client_complaints where compl_no = '"+complaint_no+"'";
		db.select(sql);
		FncSystem.out("Check complaint category", sql);

		return (String) db.getResult()[0][0];
	}

	public boolean isActionPlanExisting(String complaint_no){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_complain_action_plan where compl_no = '"+complaint_no+"'";
		db.select(sql);
		FncSystem.out("Display is action plan existing", sql);

		return db.isNotNull();
	}

	public boolean isComplaintResolved(String complaint_no){//CHECKS IF COMPLAINT IS ALREADY RESOLVED 
		pgSelect db = new pgSelect();
		String sql = "select * from rf_complain_accomplishment where compl_no = '"+complaint_no+"' and resolve_date is not null and action_taken != ''";
		db.select(sql);
		FncSystem.out("Display is Complaint Resolved", sql);
		return db.isNotNull();
	}

	private void displayComplaintList(String entity_id, String proj_id, String unit_id, String seq_no){
		modelComplaintList.clear();
		
		pgSelect db = new pgSelect();

		String sql = "select a.compl_no, a.category_code, b.ctgry_desc, \n" + 
				"a.src_id, c.complain_source, a.comp_addressed_to, \n" + 
				"d.dept_name, a.contact_no, a.complaint_details\n" + 
				"from rf_client_complaints a\n" + 
				"LEFT JOIN mf_complaint_category b on b.ctgry_code = a.category_code\n" + 
				"LEFT JOIN mf_complaint_sources c on c.src_id = a.src_id\n" + 
				"LEFT JOIN mf_department d on d.dept_code = a.comp_addressed_to\n" + 
				"where a.entity_id = '"+entity_id+"'\n" + 
				"and a.projcode = '"+proj_id+"'\n" + 
				"and a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n" + 
				"and seq_no = "+seq_no+" \n"+
				"ORDER BY a.add_date DESC";
		db.select(sql);

		FncSystem.out("Display Complaint List", sql);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				modelComplaintList.addRow(db.getResult()[x]);
			}
			scrollComplaintList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblComplaintList.getRowCount())));
			tblComplaintList.packAll();
		}
	}

	private void displayClientDetails(String entity_id, String pbl_id, String proj_id , String seq_no){
		//tabCenter.setSelectedIndex(3);
		//tabCenter.setEnabled(true);

		pgSelect db = new pgSelect();

		String sql = "select a.entity_id, get_client_name(a.entity_id), get_unit_id('"+proj_id+"', '"+pbl_id+"') , \n"+
				"get_merge_unit_desc(get_unit_id('"+proj_id+"', '"+pbl_id+"')), a.projcode, get_project_name(a.projcode),\n" + 
				"b.status_desc, a.model_id ,get_merge_model_desc(get_unit_id('"+proj_id+"', '"+pbl_id+"'))\n" + 
				"from rf_sold_unit a\n" + 
				"left join mf_buyer_status b on b.byrstatus_id = a.currentstatus\n" + 
				"where a.entity_id = '"+entity_id+"'\n" + 
				"and a.projcode = '"+proj_id+"'\n" + 
				"and a.pbl_id = '"+pbl_id+"' \n" + 
				"and a.seq_no = "+seq_no+"";
		db.select(sql);

		if(db.isNotNull()){
			String entity_name = (String) db.getResult()[0][1];
			String unit_id = (String) db.getResult()[0][2];
			String unit_desc = (String) db.getResult()[0][3];
			String proj_desc = (String) db.getResult()[0][5];
			String unit_stat = (String) db.getResult()[0][6];
			String model_id = (String) db.getResult()[0][7];
			String model_desc = (String) db.getResult()[0][8];

			lookupClient.setValue(entity_id);
			txtClient.setText(entity_name);
			txtProjID.setText(proj_id);
			txtProject.setText(proj_desc);
			txtUnitID.setText(unit_id);
			txtUnitDesc.setText(unit_desc);
			txtSeqNo.setText(seq_no.toString());
			txtUnitStatus.setText(unit_stat);
			txtModelID.setText(model_id);
			txtModel.setText(model_desc);

			//pnlPhoneFollow.displayFollowUp(entity_id, unit_id, proj_id, seq_no);
		}
	}

	public String getCompl_No(){
		String comp_no = txtComplaintNo.getText();
		return comp_no;
	}

	private String sqlClientID(){ //sql for lookup of clients --display contractor 

		return "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as \"Name\", \n" + 
		"trim(b.unit_id) as \"Unit Id\", trim(a.projcode) as \"Proj. ID\",\n" + 
		"a.seq_no as \"Seq\", get_project_name(a.projcode) as \"Project Name\",\n" + 
		"trim(b.description) as \"Unit Desc\", trim(c.status_desc) as \"Unit Status\", \n" + 
		"trim(a.model_id) as \"Model ID\", trim(get_model_name(a.model_id)) as \"Model Name\"\n" + 
		"from rf_sold_unit a\n" + 
		"left join mf_unit_info b on b.pbl_id = a.pbl_id and b.proj_id = a.projcode\n" + 
		"left join mf_buyer_status c on c.byrstatus_id = a.currentstatus";

	}

	private String sqlSearch(){//SQL FOR THE LOOKUP OF THE COMPLAINTS
		String entity_id = lookupClient.getValue();
		String unit_id = txtUnitID.getText();
		String proj_id = txtProjID.getText();
		String seq_no = txtSeqNo.getText();

		String sql = "select trim(compl_no) as \"Complaint No.\", trim(entity_id) as \"Entity ID\", trim(get_client_name(entity_id)) as \"Entity Name\", \n" + 
				"trim(get_unit_id(projcode, pbl_id)) as \"Unit ID\",  trim(projcode) as \"Proj. ID\", seq_no as \"Seq. No\", date_complained as \"Date Complained\"\n" + 
				"from rf_client_complaints \n";
		//"where compl_no not in (select compl_no from rf_complain_accomplishment where action_taken != '') \n";

		if(lookupClient.getValue() != null){
			sql = sql + "where entity_id = '"+entity_id+"' and pbl_id = getinteger('"+unit_id+"')::VARCHAR and projcode = '"+proj_id+"' and seq_no = "+seq_no+"";
		}
		return sql;
	}

	public void clearFields(){//CLEARS THE FIELDS FOR THE CLIENT DETAILS
		this.setComponentsEditable(pnlNorth, false);
		lookupClient.setEditable(true);
	}

	public boolean toSave(){ //TRY ADDING HERE
		if(lookupClient.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Client"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	private Boolean isAuthorizeToEdit(){
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT CASE WHEN EXISTS (SELECT dept_head_code FROM mf_department WHERE dept_head_code = '"+UserInfo.EmployeeCode+"' \n"+
				     "UNION SELECT div_head_code FROM mf_division WHERE div_head_code = '"+UserInfo.EmployeeCode+"') THEN TRUE ELSE FALSE END;\n";
		db.select(sql);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public Boolean isEditable(String comp_no){
		pgSelect db = new pgSelect();
		
		String sql = "SELECT CASE WHEN EXISTS (SELECT * FROM rf_complain_accomplishment WHERE compl_no = '"+comp_no+"') THEN FALSE ELSE TRUE END";
		db.select(sql);
		FncSystem.out("Display sql If Editable", sql);
		
		return (Boolean) db.getResult()[0][0];
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		Integer selectedTab = tabCenter.getSelectedIndex();

		if(actionCommand.equals("New")){
			if(selectedTab == 0){
				pnlClientComp.newClientComplaint();
				tblComplaintList.clearSelection();
				lookupClient.setEditable(false);
				
				pnlState(true, false, false);
				btnState(false, false, true, true);
			}
			
			if(selectedTab == 1){
				if(tblComplaintList.getSelectedRows().length == 1){
					int selected_row = tblComplaintList.getSelectedRow();
					String comp_no = (String) modelComplaintList.getValueAt(selected_row, 0);
					
					pnlAction.newActionPlan(comp_no);
					
					pnlState(false, true, false);
					btnState(false, false, true, true);
				}
			}
			
			if(selectedTab == 2){
				
				int selected_row = tblComplaintList.getSelectedRow();
				String comp_no = (String) modelComplaintList.getValueAt(selected_row, 0);
				
				pnlResolved.newResolvedby(comp_no);
				
				
				pnlState(false, false, true);
				btnState(false, false, true, true);
			}
		}
		
		if (actionCommand.equals("Edit")){
			
			if(selectedTab == 0){ //Editing for the Client Complaint
				if(tblComplaintList.getSelectedRows().length == 1){
					if(isAuthorizeToEdit() || UserInfo.ADMIN){
						tblComplaintList.setEnabled(false);
						rowHeaderComplaintList.setEnabled(false);
						pnlClientComp.editClientComplaint();
					
						btnState(false, false, true, true);
						pnlState(true, false, false);
					}else{
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Sorry you are not authorized to edit this complaint", actionCommand, JOptionPane.WARNING_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select complaint to edit", actionCommand, JOptionPane.WARNING_MESSAGE);
				}
			}

			if(selectedTab == 1){//Editing for the Action Plan
				if(isAuthorizeToEdit() || UserInfo.ADMIN){
					
					pnlAction.editActionPlan();
					
					btnState(false, false, true, true);
					pnlState(false, true, false);
				}else{
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Sorry you are not authorized to edit this entry", actionCommand, JOptionPane.WARNING_MESSAGE);
				}
			}
		}

		if(actionCommand.equals("Save")){ //TODO TAGGING OF EMAIL ADDRESS SA CLIENT COMPLAINT //SENDS EMAIL TO THE DEPARTMENT HEAD OF THE ADDRESSED TO 
			if(this.toSave()){
				if (selectedTab == 0){//SAVING FOR THE CLIENT COMPLAINT PANEL
					if(pnlClientComp.toSave()){
						if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							if(tblComplaintList.getSelectedRows().length == 1){
								int selected_row = tblComplaintList.getSelectedRow();
								String compl_no = (String) modelComplaintList.getValueAt(selected_row, 0);
								
								pnlClientComp.saveClientComplaints(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), compl_no);
								lookupClient.setEditable(true);
								pnlClientComp.cancelClientComplaint();
								
								displayComplaintList(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText());
								scrollComplaintList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblComplaintList.getRowCount())));
								tblComplaintList.packAll();
								
								tblComplaintList.clearSelection();
								tblComplaintList.setEnabled(true);
								rowHeaderComplaintList.setEnabled(true);
								
								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Complaint has been Update", actionCommand, JOptionPane.INFORMATION_MESSAGE);
								
							}else{
								
								pnlClientComp.saveClientComplaints(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), getNewComplaintNo());
								lookupClient.setEditable(true);
								pnlClientComp.cancelClientComplaint();
								
								displayComplaintList(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText());
								scrollComplaintList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblComplaintList.getRowCount())));
								tblComplaintList.packAll();
								
								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Complaint has been saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
								
							}
							pnlState(true, false, false);
							btnState(true, false, false, false);
						}
					}
				}

				if (selectedTab == 1){//SAVING FOR THE ACTION PLAN PANEL
					if(pnlAction.tosave()){
						if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							int selected_row = tblComplaintList.getSelectedRow();
							String compl_no = (String) modelComplaintList.getValueAt(selected_row, 0);
							String ctgry_code = (String) modelComplaintList.getValueAt(selected_row, 1);
							
							pnlAction.saveActionPlan(compl_no, ctgry_code);
							pnlAction.displayActionPlan(compl_no);
							
							clearFields();
							pnlAction.cancelActionplan();
							
							btnState(true, true, false, false, false);
							pnlState(true, true, true, true);
							
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Action plan has been saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

				if(tabCenter.getSelectedIndex() == 2){//RESOLVED BY
					if(pnlResolved.toSave()){
						if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							int selected_row = tblComplaintList.getSelectedRow();
							String compl_no = (String) modelComplaintList.getValueAt(selected_row, 0);
							
							pnlResolved.saveResolvedDate(compl_no);
							this.setComponentsEditable(pnlResolved, false);
							clearFields();
							
							btnState(false, false, false, false);
							pnlState(true, true, true);
							/*pnlState(true, true, true, true);
							btnState(false, true, true, false, false);*/
						}
					}
				}
			}
		}

		if(actionCommand.equals("Cancel")){//CANCELATION OF FEEDBACK
			if (selectedTab == 0){
				
				tblComplaintList.clearSelection();
				tblComplaintList.setEnabled(true);
				rowHeaderComplaintList.setEnabled(true);
				pnlClientComp.cancelClientComplaint();
				
				pnlState(true, false, false);
				btnState(true, false, false, false);
				
			}
			
			if(selectedTab == 1){
				pnlAction.cancelActionplan();
				
				pnlState(true, true, true);
				btnState(true, false, false, false);
			}
			
			if(selectedTab == 2){
				//pnlResolved.cancelResolved(txtComplaintNo.getText());
				pnlResolved.cancelResolved();
				
				pnlState(true, true, true);
				btnState(true, false, false, false);
			}
		}
	}
}
