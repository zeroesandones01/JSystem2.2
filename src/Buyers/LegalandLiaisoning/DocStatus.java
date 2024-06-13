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

import com.toedter.calendar.JTextFieldDateEditor;

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

public class DocStatus extends JPanel implements ActionListener {

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

	private JLabel lblDesc;

	private JPanel pnlNorthWest;
	private JLabel lblDoneBy;
	private _JLookup lookupDoneBy;
	private JLabel lblRecipient;
	private _JLookup lookupRecipient;
	private JLabel lblRemarks;
	private _JLookup lookupDescription;

	private JPanel pnlNorthCenter;
	private JTextField txtDoneBy;
	private JTextField txtRecipient;
	private JTextField txtRemarks;
	private JTextField txtdescription;
	private Integer rec_id;

	private JPanel pnlCenter;

	private JScrollPane scrollCenter;
	public static _JTableMain tblDocStatus;
	static modelDocStatus modelDocStatus;
	static JList rowHeaderDocStatus;

	private _JDateChooser dateTo;

	private ButtonGroup grpButton = new ButtonGroup();
	private KeyboardFocusManager KEYBOARD_MANAGER = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public DocStatus() {
		initThis();
	}

	public DocStatus(LayoutManager layout) {
		super(layout);
		initThis();
	}

	public DocStatus(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initThis();
	}

	public DocStatus(LayoutManager layout, boolean isDoubleBuffered) {
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
				pnlNorth = new JPanel();
				pnlCenter.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(3, 3));
				pnlNorth.setPreferredSize(new Dimension(416, 125));
				{
					pnlNorthNorth = new JPanel();
					pnlNorth.add(pnlNorthNorth, BorderLayout.NORTH);
					pnlNorthNorth.setLayout(null);
					pnlNorthNorth.setPreferredSize(new Dimension(416, 47));
					{
						lblDesc = new JLabel("Doc. Location");
						pnlNorthNorth.add(lblDesc);
						lblDesc.setBounds(0, 0, 100, 22);
					}
					{
						lookupDescription = new _JLookup(null, "Document Location");
						pnlNorthNorth.add(lookupDescription);
						lookupDescription.setReturnColumn(0);
						lookupDescription.setLookupSQL(Description());
						lookupDescription.setBounds(110, 0, 100, 22);
						lookupDescription.setEditable(false);
						lookupDescription.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtdescription.setText((String) data[1]);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						txtdescription = new JTextField();
						pnlNorthNorth.add(txtdescription);
						txtdescription.setBounds(213, 0, 475, 22);
						txtdescription.setEditable(false);
					}
					{
						lblDesc = new JLabel("Setup Date");
						pnlNorthNorth.add(lblDesc);
						lblDesc.setBounds(0, 25, 100, 22);
					}
					{
						dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlNorthNorth.add(dateTo);
						dateTo.setDate(null);
						dateTo.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dateTo.getDateEditor()).setEditable(false);
						dateTo.setDate(Calendar.getInstance().getTime());
						dateTo.setBounds(110, 25, 150, 22);
						dateTo.getCalendarButton().setEnabled(false);
						dateTo.setEditable(false);
					}
				}
				{
					pnlNorthWest = new JPanel();
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setLayout(null);
					pnlNorthWest.setPreferredSize(new Dimension(210, 100));
					{
						lblDoneBy = new JLabel("Done By");
						pnlNorthWest.add(lblDoneBy);
						lblDoneBy.setBounds(0, 0, 110, 22);
					}
					{
						lookupDoneBy = new _JLookup(null, "Done By");
						pnlNorthWest.add(lookupDoneBy);
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
						lblRecipient = new JLabel("Recipient");
						pnlNorthWest.add(lblRecipient);
						lblRecipient.setBounds(0, 25, 110, 22);
					}
					{
						lookupRecipient = new _JLookup(null, "Recipient");
						pnlNorthWest.add(lookupRecipient);
						lookupRecipient.setReturnColumn(0);
						lookupRecipient.setLookupSQL(Entity());
						lookupRecipient.setBounds(110, 25, 100, 22);
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
						lblRemarks = new JLabel("Remarks");
						pnlNorthWest.add(lblRemarks);
						lblRemarks.setBounds(0, 50, 110, 22);
					}
				}
				{
					pnlNorthCenter = new JPanel();
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					pnlNorthCenter.setLayout(new GridLayout(3, 1, 3, 3));
					{
						txtDoneBy = new JTextField();
						pnlNorthCenter.add(txtDoneBy);
						txtDoneBy.setEditable(false);
					}
					{
						txtRecipient = new JTextField();
						pnlNorthCenter.add(txtRecipient);
						txtRecipient.setEditable(false);
					}
				}
				{
					JPanel pnlNorthSouth = new JPanel();
					pnlNorth.add(pnlNorthSouth, BorderLayout.SOUTH);
					//pnlNorthSouth.setPreferredSize(new Dimension(210, 25));
					pnlNorthSouth.setLayout(new GridLayout(1, 1, 3, 3));
					{
						txtRemarks = new JTextField();
						pnlNorthCenter.add(txtRemarks);
						txtRemarks.setEditable(false);
					}
				}
			}
			{
				scrollCenter = new JScrollPane();
				pnlCenter.add(scrollCenter, BorderLayout.CENTER);
				{
					modelDocStatus = new modelDocStatus();

					tblDocStatus = new _JTableMain(modelDocStatus);
					scrollCenter.setViewportView(tblDocStatus);
					tblDocStatus.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblDocStatus.hideColumns("Status ID", "Done/Accomplish By ID", "Recipient ID", "Rec ID");

					tblDocStatus.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

						public void valueChanged(ListSelectionEvent arg0) {
							try {
								if(!arg0.getValueIsAdjusting()){
									if (tblDocStatus.getSelectedRows().length>0){
										int row = tblDocStatus.convertRowIndexToModel(tblDocStatus.getSelectedRow());

										String status = (String) modelDocStatus.getValueAt(row, 0);
										Date status_date = (Date) modelDocStatus.getValueAt(row, 1);
										String doneby = (String) modelDocStatus.getValueAt(row, 2);
										String recipient = (String) modelDocStatus.getValueAt(row, 3);
										String remarks = (String) modelDocStatus.getValueAt(row, 4);
										String statusid = (String) modelDocStatus.getValueAt(row, 5);
										String donebyid = (String) modelDocStatus.getValueAt(row, 6);
										String recipientid = (String) modelDocStatus.getValueAt(row, 7);
										rec_id = (Integer) modelDocStatus.getValueAt(row, 8);

										btnState(true, true, true, false, false);

										lookupDescription.setValue(statusid);
										txtdescription.setText(status);
										dateTo.setDate(status_date);
										lookupDoneBy.setValue(donebyid);
										txtDoneBy.setText(doneby);
										lookupRecipient.setValue(recipientid);
										txtRecipient.setText(recipient);
										txtRemarks.setText(remarks);
									}
								}
							} catch (ArrayIndexOutOfBoundsException e) { }
						}
					});
					tblDocStatus.setSortable(false);
				}
				{
					rowHeaderDocStatus = tblDocStatus.getRowHeader();
					rowHeaderDocStatus.setModel(new DefaultListModel());
					scrollCenter.setRowHeaderView(rowHeaderDocStatus);
					scrollCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
		lookupDescription.setText("");
		txtdescription.setText("");
		lookupDoneBy.setValue(null);
		txtDoneBy.setText("");
		lookupRecipient.setValue(null);
		txtRecipient.setText("");
		txtRemarks.setText("");
		//lookupDoneBy.setEnabled(false);

		if(isNewSuretyBond){
			((DefaultListModel)rowHeaderDocStatus.getModel()).removeAllElements();
			modelDocStatus.clear();
			scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocStatus.getRowCount())));
			tblDocStatus.packAll();
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
		if(lookupRecipient.getValue() == ""){
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
		String actionCommand = arg0.getActionCommand();
		String emp_code = (String) UserInfo.EmployeeCode;
		String emp_name = (String) UserInfo.FullName;
		String doc_no = (String) TCTTaxDecProcessing.txtDocNo.getText();
		String pbl_id = (String) TCTTaxDecProcessing.lookupPBL.getValue();
		String doc_type = TCTTaxDecProcessing.lookupDocType.getValue();
		String doc_status = (String) lookupDescription.getText();
		Date status_date = (Date) dateTo.getDate();
		String done_by = (String) lookupDoneBy.getValue();
		String recipient = (String) lookupRecipient.getValue();
		String remarks = (String) txtRemarks.getText();
		

		if(actionCommand.equals("Add")){
			refresh(false);
			//setComponentsEnabled(true);
			lookupDescription.setEditable(true);
			dateTo.getCalendarButton().setEnabled(true);
			dateTo.setEditable(true);
			dateTo.setDate(FncGlobal.getDateToday());
			//lookupDoneBy.setEditable(true);
			lookupRecipient.setEditable(true);
			txtRemarks.setEditable(true);
			
			tblDocStatus.clearSelection();
			tblDocStatus.setEnabled(false);
			lookupDoneBy.setValue(emp_code);
			txtDoneBy.setText(String.format("%s", emp_name));
			btnState(false, false, false, true, true);

			//---added by jed 2018-09-14 : to disable buttons when adding particular---//
			TCTTaxDecProcessing.btnNew.setEnabled(false);
			TCTTaxDecProcessing.btnEdit.setEnabled(false);
			TCTTaxDecProcessing.btnCancel.setEnabled(false);
			TCTTaxDecProcessing.btnSearch.setEnabled(false);
			TCTTaxDecProcessing.pnlState(true, false);

			grpButton.setSelected(((JButton)arg0.getSource()).getModel(), true);
		}

		if(actionCommand.equals("Edit")){
			//setComponentsEnabled(true);
			dateTo.getCalendarButton().setEnabled(true);
			lookupRecipient.setEditable(true);
			txtRemarks.setEditable(true);
			tblDocStatus.setEnabled(false);
			btnState(false, false, false, true, true);

			grpButton.setSelected(((JButton)arg0.getSource()).getModel(), true);

			//---added by jed 2018-09-14 : to disable buttons when editing particular---//
			//lookupDescription.setEnabled(false);

			TCTTaxDecProcessing.btnNew.setEnabled(false);
			TCTTaxDecProcessing.btnEdit.setEnabled(false);
			TCTTaxDecProcessing.btnCancel.setEnabled(false);
			TCTTaxDecProcessing.btnSearch.setEnabled(false);
			TCTTaxDecProcessing.pnlState(true, false);

		}

		if(actionCommand.equals("Delete")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate dbPgUpdate = new pgUpdate();
				String pgSQL = "";

				pgSQL = "UPDATE rf_tct_taxdec_monitoring_dl\n" + 
						"   SET status_id='I', edited_by='"+UserInfo.EmployeeCode+"', date_edited=now()\n" + 
						" WHERE pbl_id = '"+pbl_id+"' and status_id = 'A' and trim(doc_no) = '"+doc_no+"' and doc_status = '"+doc_status+"' and rec_id = '"+rec_id+"'";

				dbPgUpdate.executeUpdate(pgSQL, false);
				dbPgUpdate.commit();

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Document Status. has been deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);
				btnCancel.doClick();

				displayDocStatus(modelDocStatus, rowHeaderDocStatus, pbl_id, doc_type, doc_no); //---added by jed 2018-09-14: to display tables after deleting---//
				scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocStatus.getRowCount())));
				tblDocStatus.packAll();
			}
		}

		if(actionCommand.equals("Save")){

			if (validateSaving()) {
				boolean isExisting = grpButton.getSelection().getActionCommand().equals("Edit");
				//int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (JOptionPane.showConfirmDialog(this, "Are all entries correct?", "Confirmation",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					if(tblDocStatus.getSelectedRows().length == 0) {

						String strSQL = "SELECT sp_save_docstatus('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+doc_status+"',\n"
								+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');"; 
						/*String strSQL = "SELECT sp_save_docstatus('"+doc_status+"',\n"
								+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');";*/

						FncSystem.out("Display SQL for Saving doc status", strSQL);

						pgSelect db = new pgSelect();
						db.select(strSQL);

						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Document Location has been created.", "Save", JOptionPane.INFORMATION_MESSAGE);
						btnCancel.doClick();

						//---added by jed 2018-09-14: to display tables after edit---//
						//displayDocStatus(modelDocStatus, rowHeaderDocStatus, pbl);
						TCTTaxDecProcessing.btnNew.setEnabled(true);
						TCTTaxDecProcessing.btnEdit.setEnabled(true);
						TCTTaxDecProcessing.btnCancel.setEnabled(true);
						TCTTaxDecProcessing.btnSearch.setEnabled(true);
						TCTTaxDecProcessing.pnlState(true, true);

					} else {

						String strSQL = "SELECT sp_update_docstatus('"+pbl_id+"', '"+doc_type+"', '"+doc_no+"', '"+doc_status+"',\n"
								+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');"; 
						/*String strSQL = "SELECT sp_save_docstatus('"+doc_status+"',\n"
								+ "'"+status_date+"', '"+done_by+"', '"+recipient+"', '"+remarks+"');";*/


						pgSelect db = new pgSelect();
						db.select(strSQL);

						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Document Status. has been updated.", "Save", JOptionPane.INFORMATION_MESSAGE);
						btnCancel.doClick();

						//---added by jed 2018-09-14: to display tables after edit---//
						//displayDocStatus(modelDocStatus, rowHeaderDocStatus, pbl);
						TCTTaxDecProcessing.btnNew.setEnabled(true);
						TCTTaxDecProcessing.btnEdit.setEnabled(true);
						TCTTaxDecProcessing.btnCancel.setEnabled(true);
						TCTTaxDecProcessing.btnSearch.setEnabled(true);
						TCTTaxDecProcessing.pnlState(true, true);
					}
					displayDocStatus(modelDocStatus, rowHeaderDocStatus, pbl_id, doc_type, doc_no);
				}
				
			}
		}

		if(actionCommand.equals("Cancel")){
			refresh(false);
			//setComponentsEnabled(false);
			lookupDescription.setEditable(false);
			dateTo.getCalendarButton().setEnabled(false);
			dateTo.setEditable(false);
			lookupDoneBy.setEditable(false);
			lookupRecipient.setEditable(false);
			txtRemarks.setEditable(false);
			
			tblDocStatus.setEnabled(true);
			btnState(true, false, false, false, false);
			displayDocStatus(pbl_id, doc_type, doc_no);

			//---added by jed 2018-09-14: to enable buttons---//
			TCTTaxDecProcessing.btnNew.setEnabled(true);
			TCTTaxDecProcessing.btnEdit.setEnabled(true);
			TCTTaxDecProcessing.btnCancel.setEnabled(true);
			TCTTaxDecProcessing.btnSearch.setEnabled(true);
			TCTTaxDecProcessing.pnlState(true, true);

		}
	}
	public static String Description() {
		String sql = "select trim(status_code)::INT as \"ID\", trim(status_desc) as \"Name\", trim(Status_alias) as \"Alias\"\n" + 
				"from mf_tct_taxdec_status \n" + 
				"where status_id = 'A'\n" + 
				"and status_type = 'L'\n" + 
				"order by trim(status_desc);";
		return sql;
	}
	public static String Entity() {
		String sql = "select trim(entity_id) as \"ID\", trim(entity_name) as \"Name\", trim(entity_kind) as \"Kind\"\n" + 
				"from rf_entity \n" + 
				"where status_id = 'A'\n" + 
				//"group by trim(a.entity_id), trim(b.entity_name), trim(b.entity_kind)\n" + 
				"order by trim(entity_name);";
		return sql;
	}
	public void displayDocStatus(String pbl_id,String doc_type, String doc_no) {
		refresh(true);

		displayDocStatus(modelDocStatus, rowHeaderDocStatus, pbl_id, doc_type, doc_no);
		scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocStatus.getRowCount())));
		tblDocStatus.packAll();

		//setComponentsEnabled(true);
		btnState(true, false, false, false, false);
	}
	
	public static void displayDocStatus(modelDocStatus model, JList rowHeader, String pbl_id, String doc_type, String doc_no) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "SELECT b.status_desc, a.status_date, get_client_name_emp_id(a.done_by), get_client_name(a.recipient), remarks, trim(a.doc_status), a.done_by, a.recipient, rec_id \n"

				+ "FROM rf_tct_taxdec_monitoring_dl a\n"

				+ "LEFT JOIN mf_tct_taxdec_status b on trim(a.doc_status) = trim(b.status_code)\n"

				+ "where trim(a.pbl_id) = '"+ pbl_id +"'\n" 
				+ "and a.status_id = 'A' \n"
				+ "and b.status_id in ('A', 'I') \n"
				+ "and trim(a.doc_type) = '"+doc_type+"' \n"
				+ "and trim(a.doc_no) = '"+doc_no+"' \n"
				+ "and b.status_type = 'L' \n"
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
		if (lookupDescription.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Description", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if (lookupRecipient.getValue() == null) { //Commented by Lester 2017-10-11
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Receipient", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/

		return true;
	}
}
