package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import javax.swing.text.JTextComponent;

import com.toedter.calendar.JTextFieldDateEditor;

import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import tablemodel.modelDocStatus;
import tablemodel.modelProcStatus;

public class DocProcStatus extends JPanel implements ActionListener {
	/*
	 * CHANGES AS OF 2021-09-20
	 * 
	 * 1. DCRF NO 1726 - AUTO TAGGING OF SALE(TCOST), IT FEE AND DST MORTGAGE WHEN CAR APPLICATION IS TAG 2021-09-20
	 * 2. DCRF NO 1726 - AUTO TAGGING OF REGISTRATION OF MORTGAGE AND IT FEE WHEN RD SALE IS TAG 2021-09-20
	 * */

	/**
	 * 
	 */
	private static final long serialVersionUID = -8600135017373881213L;

	private JPanel pnlMenu;
	public static JButton btnAdd;
	public static JButton btnEdit;
	public static JButton btnDelete;
	public static JButton btnSave;
	public static JButton btnCancel;

	private JPanel pnlNorth;
	private JPanel pnlNorthNorth;

	private JPanel pnlNorthWest;

	private JLabel lblRecipient;

	private JLabel lblRemarks;

	private JPanel pnlNorthCenter;
	private JTextField txtDoneBy;
	private JTextField txtRecipient;
	private JTextField txtRemarks;

	private JPanel pnlCenter;
	
	private DocStatus pnlDocStat;

	private JScrollPane scrollCenter;
	public static _JTableMain tblProcStatus;

	private JList rowHeaderProcStatus;

	private _JDateChooser dateTo;

	private ButtonGroup grpButton = new ButtonGroup();
	private KeyboardFocusManager KEYBOARD_MANAGER = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	private _JLookup lookupProcStatus;

	private JLabel lblProcStatus;

	private JTextComponent txtProcStatus;

	private JLabel lblStatusDate;

	private JLabel lblDocStatus;

	private _JLookup lookupDocLocation;

	private _JLookup lookupDoneBy;

	private modelProcStatus modelProcStatus;

	private JLabel lblDoneBy;

	private JPanel pnlWestLabel;

	private JPanel pnlCenterComponents;

	private JPanel pnlProcField;

	private JPanel pnlStatusField;

	private JPanel pnlRecipientField;

	private JPanel pnlDoneBy;

	private JPanel pnlDoneByField;

	private _JLookup lookupRecipient;

	private JPanel pnlDocLocationField;

	private JTextField txtDocLocation;

	private JPanel pnlRemarkField;

	private JPanel pnlProcTable;
	
	private String status_code;
	
	private String stat_code;

	private String to_do;
	
	private Integer rec_id;

	public DocProcStatus() {
		initThis();
	}

	public DocProcStatus(LayoutManager layout) {
		super(layout);
		initThis();
	}

	public DocProcStatus(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initThis();
	}

	public DocProcStatus(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initThis();
	}

	private void initThis() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(800, 250));
		{
			pnlCenter = new JPanel();
			this.add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setLayout(new BorderLayout(5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout (5,5));
				pnlCenter.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setLayout(new BorderLayout(3, 3));
				pnlNorth.setPreferredSize(new Dimension(416, 140));
				{
					pnlWestLabel = new JPanel (new GridLayout(6,5,5,5));
					pnlNorth.add(pnlWestLabel, BorderLayout.WEST);
					{
						lblProcStatus = new JLabel ("Proc. Status");
						pnlWestLabel.add(lblProcStatus);
					}
					{
						lblStatusDate = new JLabel ("Status Date");
						pnlWestLabel.add(lblStatusDate);
					}
					{
						lblDoneBy = new JLabel ("Done By");
						pnlWestLabel.add(lblDoneBy);
					}
					{
						lblRecipient =  new JLabel ("Recipient");
						pnlWestLabel.add(lblRecipient);
					}
					{
						lblDocStatus = new JLabel ("Doc. Location");
						pnlWestLabel.add(lblDocStatus);
					}
					{
						lblRemarks = new JLabel ("Remarks");
						pnlWestLabel.add(lblRemarks);
					}
				}
				{
					pnlCenterComponents = new JPanel(new GridLayout (6,5,5,5));
					pnlNorth.add(pnlCenterComponents, BorderLayout.CENTER);
					{
						pnlProcField = new JPanel (new BorderLayout (5,5));
						pnlCenterComponents.add(pnlProcField, BorderLayout.CENTER);
						{
							lookupProcStatus = new _JLookup(null, "Proc. Status");
							pnlProcField.add(lookupProcStatus, BorderLayout.WEST);
							lookupProcStatus.setPreferredSize(new Dimension(120,0));
							lookupProcStatus.setReturnColumn(0);
							//lookupProcStatus.setLookupSQL(Description(TCTTaxDecProcessing.lookupDocType.getValue().toString()));
							lookupProcStatus.setEditable(false);
							lookupProcStatus.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										status_code = (String) data [0];
										txtProcStatus.setText((String) data[1]);
										
										stat_code = getStatusCode(status_code);
										System.out.printf("Status code: %s\n", getStatusCode(status_code));


										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							txtProcStatus = new JTextField();
							pnlProcField.add(txtProcStatus, BorderLayout.CENTER);
							txtProcStatus.setEditable(false);
						}
					}
					{
						pnlStatusField = new JPanel(new BorderLayout (5,5));
						pnlCenterComponents.add(pnlStatusField, BorderLayout.CENTER);
						{
							dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlStatusField.add(dateTo, BorderLayout.WEST);
							dateTo.setDate(null);
							dateTo.setPreferredSize(new Dimension(120,0));
							dateTo.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dateTo.getDateEditor()).setEditable(false);
							dateTo.setDate(Calendar.getInstance().getTime());
							dateTo.getCalendarButton().setEnabled(false);
							dateTo.setEditable(false);	
						}
					}
					{
						pnlDoneByField = new JPanel(new BorderLayout (5,5));
						pnlCenterComponents.add(pnlDoneByField, BorderLayout.CENTER);
						{
							lookupDoneBy = new _JLookup(null, "Done By");
							pnlDoneByField.add(lookupDoneBy, BorderLayout.WEST);
							lookupDoneBy.setPreferredSize(new Dimension(120,0));
							lookupDoneBy.setReturnColumn(0);
							//lookupDoneBy.setEnabled(false);
							lookupDoneBy.setEditable(false);
							lookupDoneBy.setLookupSQL(Entity());
							lookupDoneBy.setBounds(110, 0, 100, 22);
							lookupDoneBy.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtDoneBy.setText((String) data[1]);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							txtDoneBy = new JTextField();
							pnlDoneByField.add(txtDoneBy, BorderLayout.CENTER);
							txtDoneBy.setEditable(false);
						}
					}
					{
						pnlRecipientField = new JPanel (new BorderLayout (5,5));
						pnlCenterComponents.add(pnlRecipientField, BorderLayout.CENTER);
						{
							lookupRecipient = new _JLookup(null, "Recipient");
							pnlRecipientField.add(lookupRecipient, BorderLayout.WEST);
							lookupRecipient.setPreferredSize(new Dimension(120,0));
							lookupRecipient.setReturnColumn(0);
							lookupRecipient.setLookupSQL(Recipient());
							lookupRecipient.setEditable(false);
							lookupRecipient.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtRecipient.setText((String) data[1]);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							txtRecipient = new JTextField();
							pnlRecipientField.add(txtRecipient, BorderLayout.CENTER);
							txtRecipient.setEditable(false);
						}
					}
					{
						pnlDocLocationField = new JPanel (new BorderLayout (5,5));
						pnlCenterComponents.add(pnlDocLocationField, BorderLayout.CENTER);
						{
							lookupDocLocation= new _JLookup(null, "Doc. Location");
							pnlDocLocationField.add(lookupDocLocation, BorderLayout.WEST);
							lookupDocLocation.setPreferredSize(new Dimension(120,0));
							lookupDocLocation.setReturnColumn(0);
							lookupDocLocation.setFilterIndex(1);
							lookupDocLocation.setLookupSQL(Entity());
							lookupDocLocation.setEditable(false);
							lookupDocLocation.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtDocLocation.setText((String) data[1]);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							txtDocLocation = new JTextField();
							pnlDocLocationField.add(txtDocLocation, BorderLayout.CENTER);
							txtDocLocation.setEditable(false);
						}
					}
					{
						pnlRemarkField	= new JPanel (new BorderLayout (5,5));
						pnlCenterComponents.add(pnlRemarkField, BorderLayout.CENTER);
						{
							txtRemarks = new JTextField();
							pnlRemarkField.add(txtRemarks, BorderLayout.CENTER);
							txtRemarks.setEditable(false);
						}
					}
				}
//				{
//					pnlNorthNorth = new JPanel();
//					pnlNorth.add(pnlNorthNorth, BorderLayout.NORTH);
//					pnlNorthNorth.setLayout(null);
//					pnlNorthNorth.setPreferredSize(new Dimension(416, 47));
//					{
//						lblProcStatus = new JLabel("Proc. Status");
//						pnlNorthNorth.add(lblProcStatus);
//						lblProcStatus.setBounds(0, 0, 150, 22);
//					}
//					{
//						lookupProcStatus = new _JLookup(null, "Proc. Status");
//						pnlNorthNorth.add(lookupProcStatus);
//						lookupProcStatus.setReturnColumn(0);
//						lookupProcStatus.setLookupSQL(Description());
//						lookupProcStatus.setBounds(110, 0, 100, 22);
//						lookupProcStatus.setEditable(false);
//						lookupProcStatus.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){
//									txtProcStatus.setText((String) data[1]);
//
//									KEYBOARD_MANAGER.focusNextComponent();
//								}
//							}
//						});
//					}
//					{
//						txtProcStatus = new JTextField();
//						pnlNorthNorth.add(txtProcStatus);
//						txtProcStatus.setBounds(213, 0, 475, 22);
//						txtProcStatus.setEditable(false);
//					}
//					{
//						lblStatusDate = new JLabel("Status Date");
//						pnlNorthNorth.add(lblStatusDate);
//						lblStatusDate.setBounds(0, 25, 100, 22);
//					}
//					{
//						dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
//						pnlNorthNorth.add(dateTo);
//						dateTo.setDate(null);
//						dateTo.setDateFormatString("yyyy-MM-dd");
//						((JTextFieldDateEditor)dateTo.getDateEditor()).setEditable(false);
//						dateTo.setDate(Calendar.getInstance().getTime());
//						dateTo.setBounds(110, 25, 150, 22);
//						dateTo.getCalendarButton().setEnabled(false);
//						dateTo.setEditable(false);
//					}
//				}
//				{
//					pnlNorthWest = new JPanel();
//					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
//					pnlNorthWest.setLayout(null);
//					pnlNorthWest.setPreferredSize(new Dimension(210, 100));
//					{
//						lblRecipient = new JLabel("Recipient");
//						pnlNorthWest.add(lblRecipient);
//						lblRecipient.setBounds(0, 0, 110, 22);
//					}
//					{
//						lookupDoneBy = new _JLookup(null, "Recipient");
//						pnlNorthWest.add(lookupDoneBy);
//						lookupDoneBy.setReturnColumn(0);
//						//lookupDoneBy.setEnabled(false);
//						lookupDoneBy.setEditable(false);
//						lookupDoneBy.setLookupSQL(Entity());
//						lookupDoneBy.setBounds(110, 0, 100, 22);
//						lookupDoneBy.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){
//									txtDoneBy.setText((String) data[1]);
//
//									KEYBOARD_MANAGER.focusNextComponent();
//								}
//							}
//						});
//					}
//					{
//						lblDocStatus = new JLabel("Doc. Location");
//						pnlNorthWest.add(lblDocStatus);
//						lblDocStatus.setBounds(0, 25, 110, 22);
//					}
//					{
//						lookupDocLocation= new _JLookup(null, "Doc. Location");
//						pnlNorthWest.add(lookupDocLocation);
//						lookupDocLocation.setReturnColumn(0);
//						lookupDocLocation.setFilterIndex(1);
//						lookupDocLocation.setLookupSQL(Entity());
//						lookupDocLocation.setBounds(110, 25, 100, 22);
//						lookupDocLocation.setEditable(false);
//						lookupDocLocation.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){
//									txtRecipient.setText((String) data[1]);
//
//									KEYBOARD_MANAGER.focusNextComponent();
//								}
//							}
//						});
//					}
//					{
//						lblRemarks = new JLabel("Remarks");
//						pnlNorthWest.add(lblRemarks);
//						lblRemarks.setBounds(0, 50, 110, 22);
//					}
//				}
//				{
//					pnlNorthCenter = new JPanel();
//					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
//					pnlNorthCenter.setLayout(new GridLayout(3, 1, 3, 3));
//					{
//						txtDoneBy = new JTextField();
//						pnlNorthCenter.add(txtDoneBy);
//						txtDoneBy.setEditable(false);
//					}
//					{
//						txtRecipient = new JTextField();
//						pnlNorthCenter.add(txtRecipient);
//						txtRecipient.setEditable(false);
//					}
//				}
//				{
//					JPanel pnlNorthSouth = new JPanel();
//					pnlNorth.add(pnlNorthSouth, BorderLayout.SOUTH);
//					//pnlNorthSouth.setPreferredSize(new Dimension(210, 25));
//					pnlNorthSouth.setLayout(new GridLayout(1, 1, 3, 3));
//					{
//						txtRemarks = new JTextField();
//						pnlNorthCenter.add(txtRemarks);
//						txtRemarks.setEditable(false);
//					}
//				}
			}
			{
				pnlProcTable = new JPanel(new BorderLayout (5,5));
				pnlCenter.add(pnlProcTable, BorderLayout.SOUTH);
				pnlProcTable.setPreferredSize(new Dimension (0,120));
				{
					scrollCenter = new JScrollPane();
					pnlProcTable.add(scrollCenter, BorderLayout.CENTER);
					{
						modelProcStatus = new modelProcStatus();

						tblProcStatus = new _JTableMain(modelProcStatus);
						scrollCenter.setViewportView(tblProcStatus);
						tblProcStatus.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblProcStatus.hideColumns("Status ID", "Done/Accomplish By ID", "Doc ID", "Recipient ID", "Rec ID");

						tblProcStatus.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							

							public void valueChanged(ListSelectionEvent arg0) {
								try {
									if(!arg0.getValueIsAdjusting()){
										if (tblProcStatus.getSelectedRows().length>0){
											int row = tblProcStatus.convertRowIndexToModel(tblProcStatus.getSelectedRow());

											String status = (String) modelProcStatus.getValueAt(row, 0);
											Date status_date = (Date) modelProcStatus.getValueAt(row, 1);
											String doneby = (String) modelProcStatus.getValueAt(row, 2);
											String recipient = (String) modelProcStatus.getValueAt(row, 3);
											String location = (String) modelProcStatus.getValueAt(row, 4);
											String remarks = (String) modelProcStatus.getValueAt(row, 5);
											String statusid = (String) modelProcStatus.getValueAt(row, 6);
											String donebyid = (String) modelProcStatus.getValueAt(row, 7);
											String recipientid = (String) modelProcStatus.getValueAt(row, 8);
											String doc_id = (String) modelProcStatus.getValueAt(row, 9);
											rec_id = (Integer) modelProcStatus.getValueAt(row, 10);
											String doc_no = TCTTaxDecProcessing.txtDocNo.getText();
											String doc_type = TCTTaxDecProcessing.lookupDocType.getValue();
											String pbl_id = TCTTaxDecProcessing.lookupPBL.getValue();

											btnState(true, true, true, false, false);
											
											Object[] ch_ord = getLatestLocation(doc_no, doc_type, pbl_id);
											
											String curr_loc = "";
											String lbl_loc	= "";
											
											if(ch_ord == null) {
												curr_loc = "";
												lbl_loc	= "";
											}else {
												curr_loc = (String) ch_ord[0];
												lbl_loc	= (String) ch_ord[1];
											}
										
											lookupProcStatus.setValue(statusid);
											lookupRecipient.setValue(recipientid);
											txtRecipient.setText(recipient);
											txtProcStatus.setText(status);
											dateTo.setDate(status_date);
											lookupDoneBy.setValue(donebyid);
											txtDoneBy.setText(doneby);
											//lookupDocLocation.setValue(doc_id);
											//txtDocLocation.setText(location);
											//lookupDocLocation.setValue((String) ch_ord[0]);
											//txtDocLocation.setText((String) ch_ord[1]);
											if(curr_loc.equals("") || curr_loc.equals("null")) {
												lookupDocLocation.setValue(null);
												txtDocLocation.setText("");
											}else {
												lookupDocLocation.setValue(curr_loc);
												txtDocLocation.setText(lbl_loc);
											}
											txtRemarks.setText(remarks);
										}
									}
								} catch (ArrayIndexOutOfBoundsException e) { }
							}
						});
						tblProcStatus.setSortable(false);
					}
					{
						rowHeaderProcStatus = tblProcStatus.getRowHeader();
						rowHeaderProcStatus.setModel(new DefaultListModel());
						scrollCenter.setRowHeaderView(rowHeaderProcStatus);
						scrollCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				
				}
			}
		}
		{
			pnlMenu = new JPanel();
			this.add(pnlMenu, BorderLayout.WEST);
			pnlMenu.setLayout(new GridLayout(5, 1, 5, 5));
			pnlMenu.setBorder(components.JTBorderFactory.createTitleBorder("Menu"));
			pnlMenu.setPreferredSize(new Dimension(74, 240));
			{
				btnAdd = new JButton("Add");
				pnlMenu.add(btnAdd);
				btnAdd.setActionCommand(btnAdd.getText());
				btnAdd.addActionListener(this);
				grpButton.add(btnAdd);
			}
			{
				btnEdit = new JButton("Edit");
				pnlMenu.add(btnEdit);
				btnEdit.setActionCommand(btnEdit.getText());
				btnEdit.addActionListener(this);
				grpButton.add(btnEdit);
			}
			{
				btnDelete = new JButton("Delete");
				pnlMenu.add(btnDelete);
				btnDelete.setActionCommand(btnDelete.getText());
				btnDelete.addActionListener(this);
			}
			{
				btnSave = new JButton("Save");
				pnlMenu.add(btnSave);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.addActionListener(this);
			}
			{
				btnCancel = new JButton("Cancel");
				pnlMenu.add(btnCancel);
				btnCancel.setActionCommand(btnCancel.getText());
				btnCancel.addActionListener(this);
			}
		}

		//setComponentsEnabled(false) ;
		btnState(false, false, false, false, false);

		//this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtBondNo, txtAmount, lookupInsuranceCo, lookupDepartmentConcerned, lookupPresentLocation, (JTextField) dateFrom.getDateEditor(), (JTextField) dateTo.getDateEditor()));
	}

	public static void btnState(Boolean sAdd, Boolean sEdit, Boolean sDelete, Boolean sSave, Boolean sCancel) {
		btnAdd.setEnabled(sAdd);
		btnEdit.setEnabled(sEdit);
		btnDelete.setEnabled(sDelete);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	public void refresh(boolean isNewSuretyBond) {
		lookupProcStatus.setText("");	
		txtProcStatus.setText("");
		lookupDoneBy.setValue(null);
		txtDoneBy.setText("");
		txtDocLocation.setText("");
		lookupDocLocation.setValue(null);
		lookupRecipient.setValue(null);
		txtRecipient.setText("");
		txtRemarks.setText("");
		//lookupDoneBy.setEnabled(false);

		if(isNewSuretyBond){
			((DefaultListModel)rowHeaderProcStatus.getModel()).removeAllElements();
			modelProcStatus.clear();
			scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblProcStatus.getRowCount())));
			tblProcStatus.packAll();
		}
	}

	public void setComponentsEnabled(boolean enable) {
		panelLooper(pnlNorth, enable);
		lookupDoneBy.setEnabled(false);
	}

	public void panelLooper(Container panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			if(comp instanceof JPanel){
				panelLooper((JPanel) comp, enable);
			}else if(comp instanceof JScrollPane){
				if(comp.getName().equals("scrollPhase")){
					panelLooper((JScrollPane) comp, enable);
				}
			}else{
				if(comp.getName() != null){
					comp.setEnabled(!enable);
				}else{
					if(panel instanceof JScrollPane){
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					}else{
						comp.setEnabled(enable);
					}
				}
			}
		}
	}
	public void newSuretyBond() {
		refresh(false);
		setComponentsEnabled(true);
		btnState(true, false, false, false, false);
	}

	public void cancelSuretyBond() {
		refresh(false);
		setComponentsEnabled(false) ;
		btnState(false, false, false, false, false);
	}

	public void disableSuretyBond() {
		refresh(true);
		setComponentsEnabled(false) ;
		btnState(false, false, false, false, false);
	}
	public void editSuretyBond() {
		refresh(true);
		setComponentsEnabled(false) ;
		btnState(true, false, false, false, false);
	}


	public boolean validatingDocStatus(){
		if(lookupDocLocation.getValue() == ""){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select recipient", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupDoneBy.getValue() == ""){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select description.", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int rw = tblProcStatus.getSelectedRow();
		String actionCommand = arg0.getActionCommand();
		String emp_code = (String) UserInfo.EmployeeCode;
		String emp_name = (String) UserInfo.FullName;
		String doc_no = (String) TCTTaxDecProcessing.txtDocNo.getText();
		String pbl_id = (String) TCTTaxDecProcessing.lookupPBL.getValue();
		String doc_type = TCTTaxDecProcessing.lookupDocType.getValue();
		String proc_status = (String) lookupProcStatus.getText();
		Date status_date = (Date) dateTo.getDate();
		//String recipient = (String) lookupDoneBy.getValue();
		String done_by = (String) lookupDoneBy.getValue();
		String recipient = (String) lookupRecipient.getValue();
		String doc_location = (String) lookupDocLocation.getValue();
		String remarks = (String) txtRemarks.getText();
		String entity_id = TCTTaxDecProcessing.strEntityid;
		String proj_id = TCTTaxDecProcessing.lookupProject.getValue();
		
		if(actionCommand.equals("Add")){
			to_do = "add";
			refresh(false);
			//setComponentsEnabled(true);
			lookupProcStatus.setLookupSQL(Description(TCTTaxDecProcessing.lookupDocType.getValue().toString()));
			lookupProcStatus.setEditable(true);
			dateTo.getCalendarButton().setEnabled(true);
			dateTo.setEditable(true);
			dateTo.setDate(FncGlobal.getDateToday());
			//lookupDoneBy.setEditable(true);
			lookupDocLocation.setEditable(true);
			txtRemarks.setEditable(true);
			
			tblProcStatus.clearSelection();
			tblProcStatus.setEnabled(false);
			lookupDoneBy.setValue(emp_code);
			txtDoneBy.setText(String.format("%s", emp_name));
			lookupRecipient.setEditable(true);
			btnState(false, false, false, true, true);

			//---added by jed 2018-09-14 : to disable buttons when adding particular---//
			TCTTaxDecProcessing.btnNew.setEnabled(false);
			TCTTaxDecProcessing.btnEdit.setEnabled(false);
			TCTTaxDecProcessing.btnCancel.setEnabled(false);
			TCTTaxDecProcessing.btnSearch.setEnabled(false);
			TCTTaxDecProcessing.pnlState(false, true);

			grpButton.setSelected(((JButton)arg0.getSource()).getModel(), true);
		}

		if(actionCommand.equals("Edit")){
			
			String doc_id = (String) modelProcStatus.getValueAt(rw, 9);
			String location = (String) modelProcStatus.getValueAt(rw, 4);
			
			to_do = "edit";
			//setComponentsEnabled(true);
			dateTo.getCalendarButton().setEnabled(true);
			lookupDocLocation.setEditable(true);
			lookupDocLocation.setValue(doc_id);
			txtDocLocation.setText(location);
			lookupRecipient.setEditable(true);
			txtRemarks.setEditable(true);
			tblProcStatus.setEnabled(false);
			btnState(false, false, false, true, true);

			grpButton.setSelected(((JButton)arg0.getSource()).getModel(), true);

			//---added by jed 2018-09-14 : to disable buttons when editing particular---//
			//lookupProcStatus.setEnabled(false);

			TCTTaxDecProcessing.btnNew.setEnabled(false);
			TCTTaxDecProcessing.btnEdit.setEnabled(false);
			TCTTaxDecProcessing.btnCancel.setEnabled(false);
			TCTTaxDecProcessing.btnSearch.setEnabled(false);
			TCTTaxDecProcessing.pnlState(false, true);

		}

		if(actionCommand.equals("Delete")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to delete this entry?", "Delete", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate dbPgUpdate = new pgUpdate();
				String pgSQL = "";

				pgSQL = "UPDATE rf_tct_taxdec_monitoring_dl\n" + 
						"   SET status_id='I', edited_by='"+UserInfo.EmployeeCode+"', date_edited=now()\n" + 
						" WHERE pbl_id = '"+pbl_id+"' and status_id = 'A' and trim(doc_no) = '"+doc_no+"' and trim(doc_status) = '"+proc_status+"' and rec_id = '"+rec_id+"'";

				FncSystem.out("DELETE", pgSQL);
				
				dbPgUpdate.executeUpdate(pgSQL, false);
				dbPgUpdate.commit();

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Document Status. has been deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);
				btnCancel.doClick();

				displayProcStatus(modelProcStatus, rowHeaderProcStatus, pbl_id, doc_type, doc_no); //---added by jed 2018-09-14: to display tables after deleting---//
				scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblProcStatus.getRowCount())));
				tblProcStatus.packAll();
			}
		}

		if(actionCommand.equals("Save")){
			
			if (to_do.equals("add")){
				
				if (validateSaving()) {
					//boolean isExisting = grpButton.getSelection().getActionCommand().equals("Edit");
					//int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (JOptionPane.showConfirmDialog(this, "Are all entries correct?", "Confirmation",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						
						//**STATUS CODE THAT DOESN'T NEED FILTER FOR SAVING**//
						//**ADDITIONAL STATUS CODE 206, 207, 208 BY JED VICEDO DCRF NO. 1124**//
						//**DCRF NO. 1726 2021-09-20 : AUTO TAGGING OF SALE(TCOST), IT FEE AND DST MORTGAGE WHEN CAR APPLICATION IS TAG**//
						//**DCRF NO. 1726 2021-09-20 : AUTO TAGGING OF REGISTRATION OF MORTGAGE AND IT FEE WHEN RD SALE IS TAG 2021-09-20**//
						if(status_code.equals("177") || status_code.equals("184") || status_code.equals("188") || status_code.equals("189") || status_code.equals("190") || status_code.equals("191")
								|| status_code.equals("195") || status_code.equals("196") || status_code.equals("197") || status_code.equals("198") || status_code.equals("201") || status_code.equals("202") || status_code.equals("203") 
									|| status_code.equals("204") || status_code.equals("205") || status_code.equals("206") || status_code.equals("207") || status_code.equals("208") || status_code.equals("209") || status_code.equals("100")
										|| status_code.equals("210")|| status_code.equals("215") || status_code.equals("216") || status_code.equals("217") || status_code.equals("194") || status_code.equals("218") || status_code.equals("227")){

							if(tblProcStatus.getSelectedRows().length == 0) {

								System.out.printf("The value of status code is:%s\n", checkStatusCode(stat_code, doc_no, doc_type, pbl_id));

								String strSQL = "SELECT sp_save_docstatus_v3('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+proc_status+"',\n"
										+ "'"+status_date+"', '"+done_by+"', '"+doc_location+"', '"+remarks+"', '"+recipient+"');"; 
								/*String strSQL = "SELECT sp_save_docstatus_v2('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+proc_status+"',\n"
										+ "'"+status_date+"', '"+done_by+"', '"+doc_location+"', '"+remarks+"', '"+recipient+"');"; */						
								/*String strSQL = "SELECT sp_save_docstatus('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+doc_status+"',\n"
										+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');"; 
								/*String strSQL = "SELECT sp_save_docstatus('"+doc_status+"',\n"
										+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');";*/

								FncSystem.out("Display SQL for Saving doc status", strSQL);

								pgSelect db = new pgSelect();
								db.select(strSQL,"SAVE", true);

								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Document Status has been created.", "Save", JOptionPane.INFORMATION_MESSAGE);
								btnCancel.doClick();

								//---added by jed 2018-09-14: to display tables after edit---//
								//displayProcStatus(modelProcStatus, rowHeaderProcStatus, pbl);
								TCTTaxDecProcessing.btnNew.setEnabled(true);
								TCTTaxDecProcessing.btnEdit.setEnabled(true);
								TCTTaxDecProcessing.btnCancel.setEnabled(true);
								TCTTaxDecProcessing.btnSearch.setEnabled(true);
								TCTTaxDecProcessing.pnlState(true, true);

							}
							
						}else{
							//**DCRF NO 1726 2021-09-20 : AUTO TAGGING OF SALE(TCOST), IT FEE AND DST MORTGAGE WHEN CAR APPLICATION IS TAG**//
							//**DCRF NO. 1726 2021-09-20 : AUTO TAGGING OF REGISTRATION OF MORTGAGE AND IT FEE WHEN RD SALE IS TAG 2021-09-20**//
							if(checkStatusCode(stat_code, doc_no, doc_type, pbl_id).equals("") || checkStatusCode(stat_code, doc_no, doc_type, pbl_id).equals(null)){//**CHECK NEW STATUS CODE**//
								
								System.out.printf("The value of status code is:%s\n", checkStatusCode(stat_code, doc_no, doc_type, pbl_id));
								
								if(checkOtherCode(stat_code, doc_no, doc_type, pbl_id).equals("") || checkOtherCode(stat_code, doc_no, doc_type, pbl_id).equals(null)){//**CHECK TTHE OLD STATUS CODE**//
									
										System.out.printf("The value of other code is:%s\n", checkOtherCode(stat_code, doc_no, doc_type, pbl_id));
										JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Code " + stat_code + " is still not being tag!", "Save", JOptionPane.ERROR_MESSAGE);
									
								}else{

									if(tblProcStatus.getSelectedRows().length == 0) {

										//System.out.printf("The value of status code is:%s", checkStatusCode(stat_code, doc_no, doc_type, pbl_id));
										//System.out.printf("The value of other code is:%s", checkOtherCode(stat_code, doc_no, doc_type, pbl_id));
										
										String strSQL = "SELECT sp_save_docstatus_v3('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+proc_status+"',\n"
												+ "'"+status_date+"', '"+done_by+"', '"+doc_location+"', '"+remarks+"', '"+recipient+"');"; 
										/*String strSQL = "SELECT sp_save_docstatus_v2('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+proc_status+"',\n"
												+ "'"+status_date+"', '"+done_by+"', '"+doc_location+"', '"+remarks+"', '"+recipient+"');";*/ 						
										/*String strSQL = "SELECT sp_save_docstatus('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+doc_status+"',\n"
												+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');"; 
										/*String strSQL = "SELECT sp_save_docstatus('"+doc_status+"',\n"
												+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');";*/

										FncSystem.out("Display SQL for Saving doc status", strSQL);

										pgSelect db = new pgSelect();
										db.select(strSQL);

										JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Document Status has been created.", "Save", JOptionPane.INFORMATION_MESSAGE);
										btnCancel.doClick();

										//---added by jed 2018-09-14: to display tables after edit---//
										//displayProcStatus(modelProcStatus, rowHeaderProcStatus, pbl);
										TCTTaxDecProcessing.btnNew.setEnabled(true);
										TCTTaxDecProcessing.btnEdit.setEnabled(true);
										TCTTaxDecProcessing.btnCancel.setEnabled(true);
										TCTTaxDecProcessing.btnSearch.setEnabled(true);
										TCTTaxDecProcessing.pnlState(true, true);

									}
								}
								
							}else{
								//**DCRF NO 1726 2021-09-20 : AUTO TAGGING OF SALE(TCOST), IT FEE AND DST MORTGAGE WHEN CAR APPLICATION IS TAG**//
								//**DCRF NO. 1726 2021-09-20 : AUTO TAGGING OF REGISTRATION OF MORTGAGE AND IT FEE WHEN RD SALE IS TAG 2021-09-20**//
								if(tblProcStatus.getSelectedRows().length == 0) {

									System.out.printf("The value of status code is:%s", checkStatusCode(stat_code, doc_no, doc_type, pbl_id));
									
									String strSQL = "SELECT sp_save_docstatus_v3('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+proc_status+"',\n"
											+ "'"+status_date+"', '"+done_by+"', '"+doc_location+"', '"+remarks+"', '"+recipient+"')"; 
									/*String strSQL = "SELECT sp_save_docstatus_v2('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+proc_status+"',\n"
											+ "'"+status_date+"', '"+done_by+"', '"+doc_location+"', '"+remarks+"', '"+recipient+"');";*/						
									/*String strSQL = "SELECT sp_save_docstatus('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+doc_status+"',\n"
											+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');"; 
									/*String strSQL = "SELECT sp_save_docstatus('"+doc_status+"',\n"
											+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');";*/

									FncSystem.out("Display SQL for Saving doc status", strSQL);

									pgSelect db = new pgSelect();
									db.select(strSQL);

									JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Document Status has been created.", "Save", JOptionPane.INFORMATION_MESSAGE);
									btnCancel.doClick();

									//---added by jed 2018-09-14: to display tables after edit---//
									//displayProcStatus(modelProcStatus, rowHeaderProcStatus, pbl);
									TCTTaxDecProcessing.btnNew.setEnabled(true);
									TCTTaxDecProcessing.btnEdit.setEnabled(true);
									TCTTaxDecProcessing.btnCancel.setEnabled(true);
									TCTTaxDecProcessing.btnSearch.setEnabled(true);
									TCTTaxDecProcessing.pnlState(true, true);

								}
							}	
						}
						
						displayProcStatus(modelProcStatus, rowHeaderProcStatus, pbl_id, doc_type, doc_no);
					}
				}
				
			}
			
			if(to_do.equals("edit")){
				/*EDITED BY JED 2021-03-01 : ADD FILTER REC ID ON UPDATE*/

				if (validateSaving()) {
					//boolean isExisting = grpButton.getSelection().getActionCommand().equals("Edit");
					//int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (JOptionPane.showConfirmDialog(this, "Are all entries correct?", "Confirmation",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

						String strSQL = "SELECT sp_update_docstatus_v2('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+proc_status+"',\n"
								+ "'"+status_date+"', '"+done_by+"', '"+doc_location+"', '"+remarks+"', '"+recipient+"', '"+rec_id+"');"; 
						/*String strSQL = "SELECT sp_update_docstatus('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+doc_status+"',\n"
						+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');"; 
						/*String strSQL = "SELECT sp_save_docstatus('"+doc_status+"',\n"
						+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');";*/


						pgSelect db = new pgSelect();
						db.select(strSQL);

						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Document Status has been updated.", "Save", JOptionPane.INFORMATION_MESSAGE);
						btnCancel.doClick();

						//---added by jed 2018-09-14: to display tables after edit---//
						//displayProcStatus(modelProcStatus, rowHeaderProcStatus, pbl);
						TCTTaxDecProcessing.btnNew.setEnabled(true);
						TCTTaxDecProcessing.btnEdit.setEnabled(true);
						TCTTaxDecProcessing.btnCancel.setEnabled(true);
						TCTTaxDecProcessing.btnSearch.setEnabled(true);
						TCTTaxDecProcessing.pnlState(true, true);
					}
				}

			}

			//checkStatusCode(stat_code, doc_no, doc_type, pbl_id);
			//System.out.printf("The value of status code is:%s", checkStatusCode(stat_code, doc_no, doc_type, pbl_id));

		}

		if(actionCommand.equals("Cancel")){
			refresh(false);
			//setComponentsEnabled(false);
			lookupProcStatus.setEditable(false);
			dateTo.getCalendarButton().setEnabled(false);
			dateTo.setEditable(false);
			lookupDoneBy.setEditable(false);
			lookupDocLocation.setEditable(false);
			lookupRecipient.setEditable(false);
			txtRemarks.setEditable(false);
			
			tblProcStatus.setEnabled(true);
			btnState(true, false, false, false, false);
			displayProcStatus(pbl_id, doc_type, doc_no);

			//---added by jed 2018-09-14: to enable buttons---//
			TCTTaxDecProcessing.btnNew.setEnabled(true);
			TCTTaxDecProcessing.btnEdit.setEnabled(true);
			TCTTaxDecProcessing.btnCancel.setEnabled(true);
			TCTTaxDecProcessing.btnSearch.setEnabled(true);
			TCTTaxDecProcessing.pnlState(true, true);

		}
	}
	public static String Description(String doc_id) {
		if(doc_id.equals("64")){//**TCT - Mother Subdivided (under CDC name)**//
		String sql = "select trim(status_code) as \"ID\", trim(status_desc) as \"Name\", trim(Status_alias) as \"Alias\"\n" + 
				"from mf_tct_taxdec_status \n" + 
				"where status_id = 'A'\n" + 
				"and status_type = 'S'\n" + 
				"and status_code not in ('204', '205', '217','218')\n" +
				"order by trim(status_desc);";
		return sql;
		}
		else if(doc_id.equals("65")){//**TCT - Individual (under buyer`s name)**//
		String sql = "select trim(status_code) as \"ID\", trim(status_desc) as \"Name\", trim(Status_alias) as \"Alias\"\n" + 
				"from mf_tct_taxdec_status \n" + 
				"where status_id = 'A'\n" + 
				"and status_type = 'S'\n" + 
				"and status_code in ('100', '191','192', '193', '194', '195', '196', '197', '198', '199', '200', '201', '202', '203', '209', '217','218')\n" +
				"order by trim(status_desc);";
		return sql;
		}
		//**92	Tax Dec (House)-Mother Subdivided (under CDC name)**//
		//**98	Tax Dec (House)-Individual (under buyer`s name)**//
		//**67	Tax Dec (Lot)-Individual (under buyer`s name)**//
		//**181	Tax Dec (Lot)-Mother Subdivided (under CDC name)**//
		else if(doc_id.equals("92") || doc_id.equals("98") ||  doc_id.equals("181") ||  doc_id.equals("67") ){
		String sql = "select trim(status_code) as \"ID\", trim(status_desc) as \"Name\", trim(Status_alias) as \"Alias\"\n" + 
				"from mf_tct_taxdec_status \n" + 
				"where status_id = 'A'\n" + 
				"and status_type = 'S'\n" + 
				"and status_code in ('100', '204', '205', '209', '215')\n" +
				"order by trim(status_desc);";
		return sql;
		}
		else{
			String sql = "select trim(status_code) as \"ID\", trim(status_desc) as \"Name\", trim(Status_alias) as \"Alias\"\n" + 
					"from mf_tct_taxdec_status \n" + 
					"where status_id = 'A'\n" + 
					"and status_type = 'S'\n" + 
					"order by trim(status_desc);";
			return sql;
		}
	}
//	public static String Entity() {
//		String sql = "select trim(entity_id) as \"ID\", trim(entity_name) as \"Name\", trim(entity_kind) as \"Kind\"\n" + 
//				"from rf_entity \n" + 
//				"where status_id = 'A'\n" + 
//				//"group by trim(a.entity_id), trim(b.entity_name), trim(b.entity_kind)\n" + 
//				"order by trim(entity_name);";
//		return sql;
//	}
	public static String Entity() {
		String sql = "select trim(rec_id) as rec_id, trim(recipient_desc) as description, trim(recipient_alias) as alias, status_id as status \n" + 
				"from mf_recipient \n" + 
				"where status_id  = 'A'";
		return sql;
	}
	
	public static String Recipient() {
		String sql = "select trim(entity_id) as \"ID\", trim(entity_name) as \"Name\", trim(entity_kind) as \"Kind\"\n" + 
				"from rf_entity \n" + 
				"where status_id = 'A' and server_id is null\n" + 
				//"group by trim(a.entity_id), trim(b.entity_name), trim(b.entity_kind)\n" + 
				"order by trim(entity_name);";
		return sql;
	}
	public void displayProcStatus(String pbl_id,String doc_type, String doc_no) {
		refresh(true);

		displayProcStatus(modelProcStatus, rowHeaderProcStatus, pbl_id, doc_type, doc_no);
		scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblProcStatus.getRowCount())));
		tblProcStatus.packAll();

		//setComponentsEnabled(true);
		btnState(true, false, false, false, false);
	}
	private static void displayProcStatus(modelProcStatus model, JList rowHeader, String pbl_id, String doc_type, String doc_no) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "SELECT b.status_desc, a.status_date, get_client_name_emp_id(a.done_by), d.entity_name, c.recipient_desc, a.remarks, trim(a.doc_status), a.done_by, a.recipient, trim(a.doc_id), a.rec_id \n"

				+ "FROM rf_tct_taxdec_monitoring_dl a\n"

				+ "LEFT JOIN mf_tct_taxdec_status b on trim(a.doc_status) = trim(b.status_code)\n"
				
				+ "LEFT JOIN mf_recipient c on trim(a.doc_id)= c.rec_id\n"
				
				+ "LEFT JOIN rf_entity d on a.recipient = d.entity_id\n"

				+ "where trim(a.pbl_id) = '"+ pbl_id +"'\n" 
				+ "and a.status_id = 'A' \n"
				+ "and b.status_id in ('A', 'I') \n"
				+ "and trim(a.doc_type) = '"+doc_type+"' \n"
				+ "and trim(a.doc_no) = '"+doc_no+"' \n"
				+ "and b.status_type = 'S' \n"
				+ "order by a.date_created desc;";

		System.out.printf("SQL: %s%n%n", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	public void cancelStatus() {
		refresh(true); //---changed by jed 2018-09-14: from false to true----//
		//setComponentsEnabled(false) ;
		btnState(false, false, false, false, false);
	}
	private boolean validateSaving() {
		if (lookupProcStatus.getValue() == null || lookupProcStatus.equals("")) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Description", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if (lookupDocLocation.getValue() == null) { //Commented by Lester 2017-10-11
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Receipient", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/

		return true;
	}
	
	private String getStatusCode(String status_code){//**get the preceded status code**//
		
		String stat_code = "";
		String SQL =
				"select status_code from mf_tct_taxdec_status where status_code::int = '"+status_code+"'::int - 1 and status_id = 'A' and status_type in ('S')";
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				stat_code = (String) db.getResult()[0][0].toString();
			}
		} else {
			stat_code = "";
		}

		return stat_code;
		
	}
	
	private String checkStatusCode(String stat_code, String doc_no, String doc_type, String pbl_id){
		
		String code = "";
		String SQL =
				"select doc_status from rf_tct_taxdec_monitoring_dl a\n" + 
				"left join mf_tct_taxdec_status b on a.doc_status = b.status_code\n" + 
				"where trim(doc_no) = '"+doc_no+"' \n" + 
				"and doc_type = '"+doc_type+"' \n" + 
				"and pbl_id = '"+pbl_id+"' \n" + 
				"and a.status_id = 'A'\n" + 
				"--and b.status_id = 'A' \n" + 
				"and status_type = 'S'\n" + 
				"and doc_status = '"+stat_code+"'";
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				code = (String) db.getResult()[0][0].toString();
			}
		} else {
			code = "";
		}

		return code;
	}
	
	private String checkOtherCode(String stat_code, String doc_no, String doc_type, String pbl_id){
		
		String other_code = "";
		String SQL =
				"select doc_status from rf_tct_taxdec_monitoring_dl a\n" + 
				"left join mf_tct_taxdec_status b on a.doc_status = b.status_code\n" + 
				"where trim(doc_no) = '"+doc_no+"'\n" + 
				"and doc_type = '"+doc_type+"'\n" + 
				"and pbl_id = '"+pbl_id+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"--and b.status_id = 'A'\n" + 
				"and status_type = 'S'\n"  ;
				/*"and (case when '"+stat_code+"' = '178' then doc_status = '102' else \n" + 
				"	(case when '"+stat_code+"' = '180' then doc_status = '103' else\n" + 
				"		(case when '"+stat_code+"' = '181' then doc_status = '106' else \n" + 
				"			(case when '"+stat_code+"' = '179' then doc_status = '159' else \n" +
				"				(case when '"+stat_code+"' = '177' then doc_status = '101' end)end)end)end)end)";*/
		
		FncSystem.out("CheckOtherCode", SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				other_code = (String) db.getResult()[0][0].toString();
			}
		} else {
			other_code = "";
		}

		return other_code;
	}
	
	private static Object[] getLatestLocation(String doc_no, String doc_type, String pbl_id){
		
		String SQL =
				"select\n" + 
				"b.status_code,\n" + 
				"b.status_desc \n" + 
				"from rf_tct_taxdec_monitoring_dl a\n" + 
				"LEFT JOIN mf_tct_taxdec_status b on a.doc_status = b.status_code \n" + 
				"where trim(doc_no) = '"+doc_no+"' \n" + 
				"and doc_type = '"+doc_type+"' \n" + 
				"and pbl_id = '"+pbl_id+"' \n" + 
				"and status_type = 'L' \n" + 
				"and a.status_id = 'A'\n" + 
				"order by a.date_created DESC\n" + 
				"limit 1";
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
}