/**
 * 
 */
package Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._ButtonGroup;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelAddReceiptNumber;

/**
 * @author John Lester Fatallo
 */
public class AddReceiptNumber extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 3453310763855706359L;

	Dimension frameSize = new Dimension(800, 550);
	static String title = "Receipt Maintenance";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private _JXTextField txtReceipt;	
	private _JXTextField txtCompany;
	private _JXTextField txtBranch;
	private _JXTextField txtDocument;
	private _JXTextField txtFirstNo;
	private _JXTextField txtLastNo;
	private _JXTextField txtPagibigID;

	private _JDateChooser dteCurrent;

	private _JLookup lookupCompany;
	private _JLookup lookupBranch;
	private _JLookup lookupDocuments;

	private JComboBox cmbStatus;

	private JScrollPane scrollReceipt;
	private modelAddReceiptNumber modelAddReceipt;
	private _JTableMain tblReceipt;
	private JList rowHeaderReceipt;

	private JCheckBox chk;
	private JCheckBox chk2;

	private _ButtonGroup grpNewEdit = new _ButtonGroup();
	private JButton btnTransfer;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	public AddReceiptNumber() {
		super(title, true, true, true, true);
		initGUI();
	}

	public AddReceiptNumber(String title) {
		super(title);
		initGUI();
	}

	public AddReceiptNumber(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable){
		super(title, false, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(EMPTY_BORDER);
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Receipt Details"));
				pnlNorth.setPreferredSize(new Dimension(0, 180));
				{
					JPanel pnlNorthWest = new JPanel(new GridLayout(6, 1, 3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					{
						JLabel lblReceiptID = new JLabel("*Receipt ID");
						pnlNorthWest.add(lblReceiptID);
					}
					{
						JLabel lblCompany = new JLabel("*Company");
						pnlNorthWest.add(lblCompany);
					}
					{
						JLabel lblBranch = new JLabel("*Branch");
						pnlNorthWest.add(lblBranch);
					}
					{
						JLabel lblDocument = new JLabel("*Type");
						pnlNorthWest.add(lblDocument);
					}
					{
						JLabel lblFirstNo = new JLabel("*First No");
						pnlNorthWest.add(lblFirstNo);
					}
					{
						JLabel lblLastNo = new JLabel("*Last No.");
						pnlNorthWest.add(lblLastNo);
					}				
				}
				{
					JPanel pnlNorthEast = new JPanel(new GridLayout(6, 1, 3, 3));
					pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
					{
						JPanel pnlReceipt = new JPanel(new BorderLayout(3, 3));
						pnlNorthEast.add(pnlReceipt);
						{
							txtReceipt = new _JXTextField("Receipt ID");
							pnlReceipt.add(txtReceipt, BorderLayout.WEST);
							txtReceipt.setEditable(false);
							txtReceipt.setPreferredSize(new Dimension(100, 0));
							txtReceipt.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							JLabel lblDate = new JLabel("Logged Date", JLabel.TRAILING);
							pnlReceipt.add(lblDate, BorderLayout.CENTER);
						}
						{
							dteCurrent = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlReceipt.add(dteCurrent, BorderLayout.EAST);
							dteCurrent.setPreferredSize(new Dimension(120, 0));
							dteCurrent.setEnabled(false);
						}
					}
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
						pnlNorthEast.add(pnlCompany);
						{
							lookupCompany = new _JLookup(null, "Select Company", 0);
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setEditable(false);
							lookupCompany.setEnabled(false);
							lookupCompany.setPreferredSize(new Dimension(100, 0));
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String company_name = (String) data[1];
										txtCompany.setText(company_name);
										lookupBranch.setEnabled(true);
										lookupBranch.setEditable(true);
										lookupDocuments.setEnabled(false);
									}
								}
							});
						}
						{
							txtCompany = new _JXTextField("Company Name");
							pnlCompany.add(txtCompany);
						}
					}
					{
						JPanel pnlBranch = new JPanel(new BorderLayout(3, 3));
						pnlNorthEast.add(pnlBranch);
						{
							lookupBranch = new _JLookup(null, "Select Branch", 0);
							pnlBranch.add(lookupBranch, BorderLayout.WEST);
							lookupBranch.setEditable(false);
							lookupBranch.setEnabled(false);
							lookupBranch.setPreferredSize(new Dimension(100, 0));
							lookupBranch.setLookupSQL(sqlBranch());
							lookupBranch.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String branch_id = (String) data[0];
										String branch_name = (String) data[1];
										txtBranch.setText(branch_name);

										lookupDocuments.setValue("");
										txtDocument.setText("");

										lookupDocuments.setEnabled(true);
										lookupDocuments.setEditable(true);
										lookupDocuments.setLookupSQL(sqlDocument(branch_id));
									}
								}
							});
						}
						{
							txtBranch = new _JXTextField("Branch Name");
							pnlBranch.add(txtBranch);
						}
					}
					{
						JPanel pnlDocuments = new JPanel(new BorderLayout(3, 3));
						pnlNorthEast.add(pnlDocuments);
						{
							lookupDocuments = new _JLookup(null, "Select Document", 0);
							pnlDocuments.add(lookupDocuments, BorderLayout.WEST);
							lookupDocuments.setEditable(false);
							lookupDocuments.setEnabled(false);
							lookupDocuments.setPreferredSize(new Dimension(100, 0));							
							lookupDocuments.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String doc_name = (String) data[1];
										txtDocument.setText(doc_name);										
									}
								}
							});
						}
						{
							txtDocument = new _JXTextField("Receipt Type");
							pnlDocuments.add(txtDocument);
						}
					}
					{
						JPanel pnlFirst = new JPanel(new BorderLayout(3, 3));
						pnlNorthEast.add(pnlFirst);
						{
							txtFirstNo = new _JXTextField("First No");
							pnlFirst.add(txtFirstNo, BorderLayout.WEST);
							txtFirstNo.setHorizontalAlignment(JTextField.CENTER);
							txtFirstNo.setPreferredSize(new Dimension(100, 0));
						}
						{
							JLabel lblPagibigID = new JLabel("PagIBIG ID", JLabel.TRAILING);
							pnlFirst.add(lblPagibigID);
						}
						{
							txtPagibigID = new _JXTextField("PagIBIG ID");
							pnlFirst.add(txtPagibigID, BorderLayout.EAST);
							txtPagibigID.setPreferredSize(new Dimension(120, 0));
						}
					}
					{
						JPanel pnlPagibig = new JPanel(new BorderLayout(3, 3));
						pnlNorthEast.add(pnlPagibig);
						{
							txtLastNo = new _JXTextField("Last No");  //txtLastNo
							pnlPagibig.add(txtLastNo, BorderLayout.WEST);
							txtLastNo.setHorizontalAlignment(JTextField.CENTER);
							txtLastNo.setPreferredSize(new Dimension(100, 0));
						}
						{
							JLabel lblStatus = new JLabel("Status", JLabel.TRAILING);
							pnlPagibig.add(lblStatus, BorderLayout.CENTER);
						}
						{
							cmbStatus = new JComboBox(new String [] {"Active", "Inactive"});
							pnlPagibig.add(cmbStatus, BorderLayout.EAST);
							cmbStatus.setPreferredSize(new Dimension(120, 0));
							cmbStatus.setSelectedIndex(0);
							cmbStatus.setEnabled(false);
						}
					}
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Receipt List"));
				{
					JPanel pnlCenter_a = new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlCenter_a, BorderLayout.CENTER);	
					pnlCenter_a.setPreferredSize(new java.awt.Dimension(835, 244));	
					pnlCenter_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));		
					{
						scrollReceipt = new JScrollPane();
						pnlCenter_a.add(scrollReceipt, BorderLayout.CENTER);
						scrollReceipt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						{
							modelAddReceipt = new modelAddReceiptNumber();
							tblReceipt = new _JTableMain(modelAddReceipt);
							scrollReceipt.setViewportView(tblReceipt);
							tblReceipt.hideColumns("Co. ID", "Company Name", "Branch ID", "Branch Name", "Doc. ID", "Doc. Name");
							tblReceipt.setSortable(false);

							modelAddReceipt.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowHeaderReceipt.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderReceipt.getModel()).addElement(modelAddReceipt.getRowCount());
									}
								}
							});

							tblReceipt.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent arg0) {
									if(!arg0.getValueIsAdjusting()){
										try {
											if(tblReceipt.getSelectedRows().length ==1){
												int row = tblReceipt.getSelectedRow();
												Integer receipt_id = (Integer) modelAddReceipt.getValueAt(row, 0);
												String co_id = (String) modelAddReceipt.getValueAt(row, 1);
												String co_name = (String) modelAddReceipt.getValueAt(row, 2);
												String branch_id = (String) modelAddReceipt.getValueAt(row, 4);
												String branch_name = (String) modelAddReceipt.getValueAt(row, 5);
												String doc_id = (String) modelAddReceipt.getValueAt(row, 8);
												String doc_name = (String) modelAddReceipt.getValueAt(row, 9);
												Integer first_no = (Integer) modelAddReceipt.getValueAt(row,11);
												Integer last_no = (Integer) modelAddReceipt.getValueAt(row, 12);
												String pagibig_id = (String) modelAddReceipt.getValueAt(row, 14);
												String status = (String) modelAddReceipt.getValueAt(row, 15);
												Date logged_date = (Date) modelAddReceipt.getValueAt(row, 16);

												txtReceipt.setText(Integer.toString((Integer)receipt_id));
												lookupCompany.setValue(co_id);
												txtCompany.setText(co_name);
												lookupBranch.setValue(branch_id);
												txtBranch.setText(branch_name);
												lookupDocuments.setValue(doc_id);
												txtDocument.setText(doc_name);
												txtFirstNo.setText(Integer.toString((Integer)first_no));
												txtLastNo.setText(Integer.toString((Integer)last_no));
												txtPagibigID.setText(pagibig_id);

												if(status.equals("A")) {
													cmbStatus.setSelectedItem("Active");
												} else {
													cmbStatus.setSelectedItem("Inactive");
												}

												dteCurrent.setDate(logged_date);

												/*if (last_no.equals(last_no_used)||status.equals("A")) {

												} else {
													btnState(false, false, false, true);
												}*/
												btnState(true, true, true, false, true);
											}
										} catch (ArrayIndexOutOfBoundsException e) { }
									}
								}
							});
						}
						{
							rowHeaderReceipt = tblReceipt.getRowHeader();
							rowHeaderReceipt.setModel(new DefaultListModel());
							scrollReceipt.setRowHeaderView(rowHeaderReceipt);
							scrollReceipt.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}

				}
				{
					JPanel pnlCenterSouth = new JPanel(new GridLayout(1, 2));
					pnlCenter.add(pnlCenterSouth, BorderLayout.SOUTH);	
					pnlCenterSouth.setPreferredSize(new Dimension(835, 20));	
					{
						chk = new JCheckBox("Remove Inactive Receipts");
						pnlCenterSouth.add(chk);
						chk.setHorizontalAlignment(JCheckBox.CENTER);
						chk.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) {
								displayReceiptDetails();			
							}
						});
					}
					{
						chk2 = new JCheckBox("Show Only Logged User's Receipts");
						pnlCenterSouth.add(chk2);
						chk2.setHorizontalAlignment(JCheckBox.CENTER);
						chk2.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) {									
								displayReceiptDetails();
							}						
						});	
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel(new BorderLayout());
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnTransfer = new JButton("Transfer");
					pnlSouth.add(btnTransfer, BorderLayout.WEST);
					btnTransfer.setPreferredSize(new Dimension(100, 0));
					btnTransfer.addActionListener(this);
				}
				{
					JPanel pnlSouthEast = new JPanel(new GridLayout(1, 4, 5, 5));
					pnlSouth.add(pnlSouthEast, BorderLayout.EAST);
					pnlSouthEast.setPreferredSize(new Dimension(430, 30));
					{
						btnNew = new JButton("New");
						pnlSouthEast.add(btnNew);
						btnNew.setActionCommand(btnNew.getText());
						btnNew.setMnemonic(KeyEvent.VK_N);
						btnNew.addActionListener(this);
						grpNewEdit.add(btnNew);
					}
					{
						btnEdit = new JButton("Edit");
						pnlSouthEast.add(btnEdit);
						btnEdit.setActionCommand(btnEdit.getText());
						btnEdit.setMnemonic(KeyEvent.VK_E);
						btnEdit.setToolTipText("Editing is only allowed for active and unused receipts");
						btnEdit.addActionListener(this);
						grpNewEdit.add(btnEdit);
					}
					{
						btnSave = new JButton("Save");
						pnlSouthEast.add(btnSave);
						btnSave.setActionCommand(btnSave.getText());
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Refresh");
						pnlSouthEast.add(btnCancel);
						btnCancel.setActionCommand(btnCancel.getText());
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(this);
					}
				}
			}
		}

		btnState(true, true, false, false, true);
		displayReceiptDetails();
	}//END OF INIT GUI

	private void btnState(Boolean sTransfer, Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnTransfer.setEnabled(sTransfer);
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void displayReceiptDetails(){
		modelAddReceipt.clear();

		pgSelect db = new pgSelect();
		String sql = "select \n" + 
				"a.rec_id as \"Receipt ID\", \n" + 
				"trim(a.co_id) as \"Co. ID\", " +
				"trim(b.company_name) as \"Company Name\",\n" + 
				"trim(b.company_alias) as \"Company Alias\",\n" + 
				"trim(a.branch_id) as \"Branch ID\", " +
				"trim(c.branch_name) as \"Branch Name\",\n" + 
				"trim(c.branch_alias) as \"Branch Alias\",\n" + 
				"upper(trim(f.entity_name)) as \"Cashier\",  \n" +
				"trim(a.doc_id) as \"Doc. ID\", " +
				"trim(d.doc_desc) as \"Doc. Desc.\" ,\n" + 
				"trim(d.doc_alias) as \"Doc. Alias\" ,\n" + 
				"a.first_no as \"First No.\", " +
				"a.last_no as \"Last No.\",\n" + 
				"a.last_no_used as \"Last No. Used\", \n" +
				"trim(a.pagibig_id) as \"PagIbig ID\", " +
				"trim(a.status_id) as \"Status\",\n" + 
				"a.logged_date as \"Logged Date\"\n" + 
				"from cs_receipt_book a\n" + 
				"left join mf_company b on b.co_id = a.co_id\n" + 
				"left join mf_office_branch c on c.branch_id = a.branch_id\n" + 
				"left join mf_doc_details d on d.doc_id = a.doc_id \n"+
				"left join em_employee e on a.emp_code = e.emp_code \n"+
				"left join rf_entity f on e.entity_id = f.entity_id \n" +
				"where a.rec_id is not null\n" +
				(chk.isSelected() ? "and a.status_id = 'A'\n":"") +
				(chk2.isSelected() ? "and a.emp_code = '"+UserInfo.EmployeeCode+"'\n":"") +
				"order by a.rec_id desc;\n";
		System.out.println(sql);

		/*if (chk.isSelected()==true){sql = sql +  "and a.status_id = 'A' \n";}		
		if (chk2.isSelected()==true){sql = sql +  "and a.emp_code = '"+UserInfo.EmployeeCode+"' ";}		
		sql = sql + "order by a.rec_id";*/

		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelAddReceipt.addRow(db.getResult()[x]);
			}
			scrollReceipt.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReceipt.getRowCount())));
			tblReceipt.packAll();
		}
	}

	//action performed
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
	
		if (actionCommand.equals("Transfer")) {
			if(tblReceipt.getSelectedRows().length < 1 || tblReceipt.getSelectedRows().length > 1){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select receipt to transfer.", "Transfer", JOptionPane.WARNING_MESSAGE);
				return;
			}
	
			int row = tblReceipt.convertRowIndexToModel(tblReceipt.getSelectedRow());
	
			String cashier = (String) modelAddReceipt.getValueAt(row, 7);
			String company = (String) modelAddReceipt.getValueAt(row, 2);
			String branch = (String) modelAddReceipt.getValueAt(row, 5);
			String first_no = Integer.toString((Integer) modelAddReceipt.getValueAt(row, 11));
			String last_no = Integer.toString((Integer) modelAddReceipt.getValueAt(row, 12));
			String last_no_used = Integer.toString((Integer) modelAddReceipt.getValueAt(row, 13));
			String status_id = (String) modelAddReceipt.getValueAt(row, 15);
			Timestamp logged_date = (Timestamp) modelAddReceipt.getValueAt(row, 16);
	
			if(status_id.equals("I")){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Inactive receipt cannot be transferred.", "Transfer", JOptionPane.WARNING_MESSAGE);
				return;
			}
	
			dlgTransferTo dlg = new dlgTransferTo(FncGlobal.homeMDI, "Transfer To", cashier, company, branch, first_no, last_no, last_no_used, logged_date, this);
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);
		}
	
		if (actionCommand.equals("New") && Functions.FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7") == true) {
			grpNewEdit.setSelectedButton(arg0);
			newReceipt();
		} else if (actionCommand.equals("New") && Functions.FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to create new receipt series.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		/*if (actionCommand.equals("New")) {
			grpNewEdit.setSelectedButton(arg0);
			newReceipt();
		}*/
	
		//EDITING OF THE RECEIPT
		if (actionCommand.equals("Edit") && Functions.FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7") == true) {
			grpNewEdit.setSelectedButton(arg0);
			editReceipt();
		} else if (actionCommand.equals("Edit") && Functions.FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to create new receipt series.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (actionCommand.equals("Save")) {//SAVING OF THE RECEIPT NO
			if (toSave()) {
				String strMsg = "";
				Boolean blnExists = ValidateSeries(txtFirstNo.getText(), txtLastNo.getText(), lookupDocuments.getValue().toString()); 
				
				if (blnExists) {
					strMsg = "This series already exists in the database.\nProceed anyway?";
				} else {
					strMsg = "Are you sure to save Receipt Details?";
				}
				
				/*if (blnExists && cmbStatus.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "This series already exists in the database.", "Duplicate Warning", JOptionPane.WARNING_MESSAGE);
				} else {*/
					strMsg = "Are you sure to save Receipt Details?";
					
					if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), strMsg, actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						
						addReceipt(txtReceipt.getText(), lookupCompany.getValue(), lookupDocuments.getValue(), lookupBranch.getValue(), txtFirstNo.getText(), txtLastNo.getText(), dteCurrent.getDate(), txtPagibigID.getText(), (String) cmbStatus.getSelectedItem(), false);
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Receipt Details has been saved...");						
					}

					cancelReceipt();
				//}
				
				/*	Modified by Mann2x; Date Modified: February 1, 2018; DCRF#386; 
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), strMsg, actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					if(isReceiptExisting(txtReceipt.getText())){	
						updateReceipt(lookupCompany.getValue(), lookupDocuments.getValue(), lookupBranch.getValue(), txtFirstNo.getText(), txtLastNo.getText(), (String) cmbStatus.getSelectedItem(), txtReceipt.getText(), txtPagibigID.getText(), blnExists);
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Receipt Details has been Updated...");
					}else{
						addReceipt(txtReceipt.getText(), lookupCompany.getValue(), lookupDocuments.getValue(), lookupBranch.getValue(), txtFirstNo.getText(), txtLastNo.getText(), dteCurrent.getDate(), txtPagibigID.getText(), (String) cmbStatus.getSelectedItem(), blnExists);
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Receipt Details has been saved...");
					}

					cancelReceipt();
				}
				*/
			}	
		}
		
		if (actionCommand.equals("Refresh")) {
			cancelReceipt();
		}
	}


	//SQL
	private String sqlCompany(){
		return "select TRIM(co_id)::VARCHAR as \"Company ID\", \n" + 
				"TRIM(company_name) as \"Company Name\", \n" + 
				"TRIM(company_alias)::VARCHAR as \"Company Alias\" \n" + 
				"from mf_company \n" + 
				"order by company_name";
	}

	private String sqlBranch(){
		return "SELECT TRIM(branch_id)::VARCHAR AS \"ID\", \n"+
				"TRIM(branch_name) AS \"Branch Name\", \n"+
				"TRIM(branch_alias)::VARCHAR AS \"Alias\" \n"+
				"FROM mf_office_branch \n"+
				"WHERE status_id = 'A' \n"+
				"ORDER BY branch_id";
	}

	private String sqlDocument(String branch_id){

		String doc_id = "'0'"; 
		int row = tblReceipt.getRowCount();
		int x = 0;

		while (x<row){
			String doc = modelAddReceipt.getValueAt(x,8).toString().trim();	
			//String cashier = modelAddReceipt.getValueAt(x, 5).toString().trim();
			String cashier = modelAddReceipt.getValueAt(x,7).toString().trim();	
			//String branch_id2 = sql_getBranchID(modelAddReceipt.getValueAt(x,4).toString().trim());
			String branch_id2 = modelAddReceipt.getValueAt(x,3).toString().trim();
			String status_id2 = modelAddReceipt.getValueAt(x,15).toString().trim();

			System.out.printf("UserInfo.FullName " + UserInfo.FullName + "\n");
			System.out.printf("branch_id " + branch_id + "\n");
			System.out.printf("branch_id2 " + branch_id2 + "\n");
			System.out.printf("cashier: %s%n", cashier);
			System.out.printf("status_id2 " + status_id2 + "\n");

			if (cashier.equals(UserInfo.FullName)&&branch_id.equals(branch_id2)&&status_id2.equals("A")){	doc_id = doc_id + ",'"+doc+"'" ;}
			else {}	

			x++;
		}

		System.out.printf("Display doc_id: %s%n", doc_id);

		String SQL = "SELECT \n" + 
				"TRIM(doc_id) AS \"ID\",\n" + 
				"TRIM(doc_desc) AS \"Description\", \n" + 
				"TRIM(doc_alias) AS \"Alias\" \n" + 
				"FROM mf_doc_details \n" +
				"WHERE doc_id IN ('01', '03', '08') \n" +
				"and doc_id not in ("+doc_id+")" +  //to prevent entering 2 similar type of active receipt series for branch
				"ORDER BY doc_id";

		System.out.printf("SQL (List of Doc ID - allowed): " + SQL);
		return SQL;

	}

	private boolean isReceiptExisting(String receipt_id){
		pgSelect db = new pgSelect();
		String sql = "select * from cs_receipt_book where rec_id = "+receipt_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean toSave(){
		if(lookupCompany.getValue() == null||lookupCompany.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Company"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupBranch.getValue() == null||lookupBranch.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Branch"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupDocuments.getValue() == null||lookupDocuments.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Document"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtFirstNo.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input First No."), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtLastNo.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Last No."), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtPagibigID.getText().length() > 4){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Pagibig ID has maximum character of 4"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void newReceipt(){
		pgSelect db = new pgSelect();
		String sql = "select COALESCE(max(rec_id), 0) +1, now() from cs_receipt_book;";
		db.select(sql);
		txtReceipt.setText(Integer.toString((Integer)db.getResult()[0][0]));
		dteCurrent.setDate((Date) db.getResult()[0][1]);
		dteCurrent.setEnabled(true);
		lookupCompany.setEnabled(true);
		lookupCompany.setEditable(true);
		lookupBranch.setEnabled(false);
		lookupDocuments.setEnabled(false);
		txtFirstNo.setEditable(true);
		txtLastNo.setEditable(true);
		txtPagibigID.setEditable(true);
		cmbStatus.setSelectedIndex(0);
		cmbStatus.setEnabled(true);

		tblReceipt.setEnabled(false);
		tblReceipt.clearSelection();
		rowHeaderReceipt.setEnabled(false);

		btnState(false, false, false, true, true);
	}

	private void editReceipt(){
		if(tblReceipt.getSelectedRows().length ==1){
			String branch_id = (String) tblReceipt.getValueAt(tblReceipt.getSelectedRow(), 3);

			lookupDocuments.setLookupSQL(sqlDocument(branch_id));
			lookupBranch.setEnabled(true);
			lookupDocuments.setEnabled(true);

			btnState(false, false, false, true, true);
			cmbStatus.setEnabled(true);
			txtReceipt.setEditable(false);
			txtCompany.setEditable(false);
			txtBranch.setEditable(false);
			txtDocument.setEditable(false);

			tblReceipt.setEnabled(false);
			rowHeaderReceipt.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select one row to edit from the table");
			tblReceipt.clearSelection();
		}
	}

	private void cancelReceipt(){
		txtReceipt.setText("");
		dteCurrent.setDate(null);
		dteCurrent.setEnabled(false);
		txtCompany.setText("");
		lookupCompany.setValue(null);
		lookupCompany.setEnabled(false);
		txtBranch.setText("");
		lookupBranch.setValue(null);
		lookupBranch.setEnabled(false);
		txtDocument.setText("");
		lookupDocuments.setValue(null);
		lookupDocuments.setEnabled(false);

		txtFirstNo.setText("");
		txtFirstNo.setEditable(false);
		txtLastNo.setText("");
		txtLastNo.setEditable(false);
		txtPagibigID.setText("");
		txtPagibigID.setEditable(false);

		cmbStatus.setSelectedIndex(0);
		cmbStatus.setEnabled(false);

		tblReceipt.setEnabled(true);
		tblReceipt.clearSelection();
		rowHeaderReceipt.setEnabled(true);

		grpNewEdit.clearSelection();
		btnState(true, true, false, false, true);
		
		displayReceiptDetails();
	}

	private void addReceipt(String receipt_id, String co_id, String doc_id, String branch_id, String first_no, String last_no, Date logged_date, String pagibig_id, String status){
		pgUpdate db = new pgUpdate();

		String sqlInactive = 
				"UPDATE cs_receipt_book " +
				"set status_id = 'I' " +
				"where branch_id = '"+branch_id+"' \n" + 
				"and co_id = '"+co_id+"' \n" + 
				"and doc_id = '"+doc_id+"'\n" + 
				"and emp_code = '"+UserInfo.EmployeeCode+"'\n" + 
				"and status_id = 'A';";
		db.executeUpdate(sqlInactive, true);

		String sql = 
				"INSERT INTO cs_receipt_book(rec_id, co_id, doc_id, branch_id, first_no, last_no, last_no_used, \n" + 
				"pagibig_id, emp_code, status_id, logged_date, inactive_date)\n" + 
				"VALUES ("+receipt_id+", '"+co_id+"', '"+doc_id+"', '"+branch_id+"', '"+first_no+"', '"+last_no+"', 0, \n" + 
				"'"+pagibig_id+"', '"+UserInfo.EmployeeCode+"', (case when '"+status+"' = 'Active' then 'A' else 'I' end), '"+logged_date+"', null);";
		db.executeUpdate(sql, true);
		db.commit();
		displayReceiptDetails();
	}

	private void updateReceipt(String co_id, String doc_id, String branch_id, String first_no, String last_no, String status, String receipt_id, String pagibig_id){
		pgUpdate db = new pgUpdate();
		String sql =  
				"UPDATE cs_receipt_book\n" + 
				"SET co_id = '"+co_id+"', " +
				"doc_id = '"+doc_id+"', " +
				"branch_id = '"+branch_id+"', " +
				"first_no = "+first_no+", " +
				"last_no = "+last_no+", \n" + 
				"pagibig_id = '"+pagibig_id+"'," +
				"status_id = (case when '"+status+"' = 'Active' then 'A' else 'I' end), \n" + 
				"inactive_date = (case when '"+status+"' = 'Active' then null else now() end)\n" + 
				"WHERE rec_id = "+receipt_id+"\n";
		db.executeUpdate(sql, true);
		db.commit();
		displayReceiptDetails();
	}



	/**
	 * @author Alvin Gonzales
	 * 
	 * This is the dialog for transferring receipt from one user to another. 
	 */
	class dlgTransferTo extends JDialog implements ActionListener {

		private static final long serialVersionUID = -3517512415695276289L;

		Dimension size = new Dimension(400, 500);

		JScrollPane scrollSchedule;
		_JTableMain tblSchedule;
		modelCashiers modelSchedule;
		JList rowheaderSchedule;
		
		AddReceiptNumber arn = null;

		public dlgTransferTo(Frame owner, String title, String cashier, String company, String branch, String first_no, String last_no, String last_no_used, Date logged_date, AddReceiptNumber arn) {
			super(owner, title);
			this.arn = arn;
			initGUI(cashier, company, branch, first_no, last_no, last_no_used, logged_date);
		}

		public void initGUI(String cashier, String company, String branch, String first_no, String last_no, String last_no_used, Date logged_date) {
			this.setPreferredSize(size);
			this.setSize(size);
			this.setModal(true);
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			this.getContentPane().setLayout(new BorderLayout());
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			{
				JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					Dimension dimLabels = new Dimension(60, 0);

					JPanel pnlNorth = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("From", FncLookAndFeel.systemFont_Bold.deriveFont(11f)));
					//pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlNorth.setPreferredSize(new Dimension(0, 140));
					{
						JPanel pnlFromCashier = new JPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlFromCashier);
						{
							JLabel lblCashier = new JLabel("Cashier");
							pnlFromCashier.add(lblCashier, BorderLayout.WEST);
							lblCashier.setFont(lblCashier.getFont().deriveFont(11f));
							lblCashier.setPreferredSize(dimLabels);
						}
						{
							_JXTextField txtCashier = new _JXTextField("Cashier Name");
							pnlFromCashier.add(txtCashier, BorderLayout.CENTER);
							txtCashier.setFont(txtCashier.getFont().deriveFont(11f));
							txtCashier.setText(cashier);
						}
					}
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlCompany);
						{
							JLabel lblCompany = new JLabel("Company");
							pnlCompany.add(lblCompany, BorderLayout.WEST);
							lblCompany.setFont(lblCompany.getFont().deriveFont(11f));
							lblCompany.setPreferredSize(dimLabels);
						}
						{
							_JXTextField txtCompany = new _JXTextField("Company Name");
							pnlCompany.add(txtCompany, BorderLayout.CENTER);
							txtCompany.setFont(txtCompany.getFont().deriveFont(11f));
							txtCompany.setText(company);
						}
					}
					{
						JPanel pnlBranch = new JPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlBranch);
						{
							JLabel lblBranch = new JLabel("Branch");
							pnlBranch.add(lblBranch, BorderLayout.WEST);
							lblBranch.setFont(lblBranch.getFont().deriveFont(11f));
							lblBranch.setPreferredSize(dimLabels);
						}
						{
							_JXTextField txtBranch = new _JXTextField("Branch Name");
							pnlBranch.add(txtBranch, BorderLayout.CENTER);
							txtBranch.setFont(txtBranch.getFont().deriveFont(11f));
							txtBranch.setText(branch);
						}
					}
					{
						JPanel pnl4thRow = new JPanel(new GridLayout(1, 2));
						pnlNorth.add(pnl4thRow);
						{
							JPanel pnlFirstNo = new JPanel(new BorderLayout(3, 3));
							pnl4thRow.add(pnlFirstNo);
							{
								JLabel lblFirstNo = new JLabel("First No.");
								pnlFirstNo.add(lblFirstNo, BorderLayout.WEST);
								lblFirstNo.setFont(lblFirstNo.getFont().deriveFont(11f));
								lblFirstNo.setPreferredSize(dimLabels);
							}
							{
								_JXTextField txtFirstNo = new _JXTextField("");
								pnlFirstNo.add(txtFirstNo, BorderLayout.CENTER);
								txtFirstNo.setFont(txtFirstNo.getFont().deriveFont(11f));
								txtFirstNo.setHorizontalAlignment(JXTextField.CENTER);
								txtFirstNo.setText(first_no);
							}
						}
						{
							JPanel pnlLoggedDate = new JPanel(new BorderLayout(3, 3));
							pnl4thRow.add(pnlLoggedDate);
							{
								JLabel lblLoggedDate = new JLabel("Date ", JLabel.TRAILING);
								pnlLoggedDate.add(lblLoggedDate, BorderLayout.WEST);
								lblLoggedDate.setFont(lblLoggedDate.getFont().deriveFont(11f));
								lblLoggedDate.setPreferredSize(dimLabels);
							}
							{
								_JDateChooser txtLoggedDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlLoggedDate.add(txtLoggedDate, BorderLayout.CENTER);
								txtLoggedDate.setFont(txtLoggedDate.getFont().deriveFont(11f));
								txtLoggedDate.getCalendarButton().setVisible(false);
								txtLoggedDate.setEditable(false);
								txtLoggedDate.setDate(logged_date);
							}
						}
					}
					{
						JPanel pnl5thRow = new JPanel(new GridLayout(1, 2));
						pnlNorth.add(pnl5thRow);
						{
							JPanel pnlLastNo = new JPanel(new BorderLayout(3, 3));
							pnl5thRow.add(pnlLastNo);
							{
								JLabel lblLastNo = new JLabel("Last No.");
								pnlLastNo.add(lblLastNo, BorderLayout.WEST);
								lblLastNo.setFont(lblLastNo.getFont().deriveFont(11f));
								lblLastNo.setPreferredSize(dimLabels);
							}
							{
								_JXTextField txtLastNo = new _JXTextField("");
								pnlLastNo.add(txtLastNo, BorderLayout.CENTER);
								txtLastNo.setFont(txtLastNo.getFont().deriveFont(11f));
								txtLastNo.setHorizontalAlignment(JXTextField.CENTER);
								txtLastNo.setText(last_no);
							}
						}
						{
							JPanel pnlLastNoUsed = new JPanel(new BorderLayout(3, 3));
							pnl5thRow.add(pnlLastNoUsed);
							{
								JLabel lblLastNoUsed = new JLabel("Used ", JLabel.TRAILING);
								pnlLastNoUsed.add(lblLastNoUsed, BorderLayout.WEST);
								lblLastNoUsed.setFont(lblLastNoUsed.getFont().deriveFont(11f));
								lblLastNoUsed.setPreferredSize(dimLabels);
							}
							{
								_JXTextField txtLastNoUsed = new _JXTextField("");
								pnlLastNoUsed.add(txtLastNoUsed, BorderLayout.CENTER);
								txtLastNoUsed.setFont(txtLastNoUsed.getFont().deriveFont(11f));
								txtLastNoUsed.setHorizontalAlignment(JXTextField.CENTER);
								txtLastNoUsed.setText(last_no_used);
							}
						}
					}
				}
				{
					scrollSchedule = new JScrollPane();
					pnlMain.add(scrollSchedule, BorderLayout.CENTER);
					scrollSchedule.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollSchedule.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblSchedule.clearSelection();
						}
					});
					{
						modelSchedule = new modelCashiers();
						modelSchedule.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								if(e.getType() == TableModelEvent.DELETE){
									rowheaderSchedule.setModel(new DefaultListModel());
								}
								if(e.getType() == TableModelEvent.INSERT){
									((DefaultListModel)rowheaderSchedule.getModel()).addElement(modelSchedule.getRowCount());
								}
							}
						});

						tblSchedule = new _JTableMain(modelSchedule);
						scrollSchedule.setViewportView(tblSchedule);
						tblSchedule.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblSchedule.hideColumns("Entity ID");
					}
					{
						rowheaderSchedule = tblSchedule.getRowHeader();
						rowheaderSchedule.setModel(new DefaultListModel());
						scrollSchedule.setRowHeaderView(rowheaderSchedule);
						scrollSchedule.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					JPanel pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new OverlayLayout(pnlSouth));
					pnlSouth.setPreferredSize(new Dimension(0, 30));
					{
						JPanel pnlNavigation = new JPanel();
						pnlSouth.add(pnlNavigation);
						pnlNavigation.setLayout(new GridLayout(1, 2, 5, 5));
						pnlNavigation.setAlignmentX(0.5f);
						pnlNavigation.setAlignmentY(0.5f);
						pnlNavigation.setMaximumSize(new Dimension(155, 30));
						{
							JButton btnSave = new JButton("Save");
							pnlNavigation.add(btnSave);
							btnSave.setActionCommand(btnSave.getText());
							btnSave.addActionListener(this);
						}
						{
							JButton btnCancel = new JButton("Cancel");
							pnlNavigation.add(btnCancel);
							btnCancel.addActionListener(this);
						}
					}
				}
			}

			displayCashiers();
		}

		private void displayCashiers() {
			String SQL = "SELECT emp_code, get_client_name(entity_id) as client_name, entity_id\n" + 
					"FROM em_employee\n" + 
					"WHERE dept_code = '04' AND emp_status NOT IN ('I', 'E')\n" + 
					"ORDER BY client_name;";

			pgSelect db = new pgSelect();
			db.select(SQL);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					modelSchedule.addRow(db.getResult()[x]);
				}

				scrollSchedule.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSchedule.getRowCount())));
				tblSchedule.packAll(10);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();

			if(actionCommand.equals("Save")){
				if(tblSchedule.getSelectedRow() < 1){
					return;
				}
				
				int row = tblSchedule.convertRowIndexToModel(tblSchedule.getSelectedRow());
				
				String emp_code = (String) tblSchedule.getValueAt(row, 0);
				String emp_name = (String) tblSchedule.getValueAt(row, 1);
				
				int save = JOptionPane.showOptionDialog(this, String.format("You're about to transfer a receipt to %s. Do you want to continue?", emp_name), actionCommand, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, 0);
				
				if(save == 0){
					pgUpdate db = new pgUpdate();
					db.executeUpdate("UPDATE cs_receipt_book SET emp_code = '"+ emp_code +"' WHERE rec_id = "+txtReceipt.getText().trim()+";", false);
					db.commit();
					
					//displayReceiptDetails();
					//arn.cancelReceipt();
					this.dispose();
				}
			}
			if(actionCommand.equals("Cancel")){
				this.dispose();
			}
			if(actionCommand.equals("Escape")){
				this.dispose();
			}
		}

		class modelCashiers extends DefaultTableModel {

			private static final long serialVersionUID = -1968661586091606191L;

			public modelCashiers() {
				initThis();
			}

			public String[] COLUMNS = new String[] { "ID", "Name", "Entity ID" };

			public Class[] CLASS_TYPES = new Class[] { String.class, Object.class, String.class };

			private void initThis() {
				setColumnIdentifiers(COLUMNS);
			}

			public Class getColumnClass(int columnIndex) {
				return CLASS_TYPES[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}

			public void clear() {
				FncTables.clearTable(this);
			}
		}
	}
	
	private boolean ValidateSeries(String strFirst, String strLast) {
		System.out.print("select (case when count(*) > 0 then true else false end) \n" + 
				"from \n" + 
				"( \n" + 
				"select x.or_no as receipt_no from rf_payments x where status_id = 'A' \n" + 
				"union \n" + 
				"select x.ar_no as receipt_no from rf_payments x where x.status_id = 'A' \n" + 
				"union \n" + 
				"select x.receipt_no as receipt_no from rf_pay_detail x where x.status_id = 'A' \n" + 
				"union \n" + 
				"select x.receipt_no as receipt_no from rf_tra_Detail x where x.status_id = 'A' \n" + 
				") a \n" + 
				"where a.receipt_no is not null and a.receipt_no != '' and a.receipt_no::int between '"+strFirst+"'::int and '"+strLast+"'::int;");
		
		return FncGlobal.GetBoolean("select (case when count(*) > 0 then true else false end) \n" + 
				"from \n" + 
				"( \n" + 
				"select x.or_no as receipt_no from rf_payments x where status_id = 'A' \n" + 
				"union \n" + 
				"select x.ar_no as receipt_no from rf_payments x where x.status_id = 'A' \n" + 
				"union \n" + 
				"select x.receipt_no as receipt_no from rf_pay_detail x where x.status_id = 'A' \n" + 
				"union \n" + 
				"select x.receipt_no as receipt_no from rf_tra_Detail x where x.status_id = 'A' \n" + 
				") a \n" + 
				"where a.receipt_no is not null and a.receipt_no != '' and a.receipt_no::int between '"+strFirst+"'::int and '"+strLast+"'::int;");
	}
	
	private void addReceipt(String receipt_id, String co_id, String doc_id, String branch_id, String first_no, String last_no, Date logged_date, String pagibig_id, String status, Boolean blnExists){
		Integer intSeries = FncGlobal.GetInteger("select max(coalesce(series_no, 1)::int) + 1 as series \n" + 
				"from cs_receipt_book \n" + 
				"where (('"+first_no+"'::int between first_no::int and last_no::int) and ('"+last_no+"'::int >= last_no::int)) \n" + 
				"or (('"+first_no+"'::int <= first_no::int) and ('"+last_no+"'::int between first_no::int and last_no::int)) \n" + 
				"or ((first_no::int between '"+first_no+"'::int and '"+last_no+"'::int) and (last_no::int between '"+first_no+"'::int and '"+last_no+"'::int)) \n" + 
				"or (('"+first_no+"'::int between first_no::int and last_no::int) and ('"+last_no+"'::int between first_no::int and last_no::int)) \n" +
				"group by coalesce(series_no, 1)::int \n" +
				"order by coalesce(series_no, 1)::int desc limit 1");
		
		if (!blnExists) {
			intSeries = 1;
		}
		
		pgUpdate db = new pgUpdate();
		String sqlInactive = 
				"UPDATE cs_receipt_book " +
				"set status_id = 'I' " +
				"where branch_id = '"+branch_id+"' \n" + 
				"and co_id = '"+co_id+"' \n" + 
				"and doc_id = '"+doc_id+"'\n" + 
				"and emp_code = '"+UserInfo.EmployeeCode+"'\n" + 
				"and status_id = 'A';";
		db.executeUpdate(sqlInactive, true);

		String sql = 
				"INSERT INTO cs_receipt_book(rec_id, co_id, doc_id, branch_id, first_no, last_no, last_no_used, \n" + 
				"pagibig_id, emp_code, status_id, logged_date, inactive_date, series_no)\n" + 
				"VALUES ("+receipt_id+", '"+co_id+"', '"+doc_id+"', '"+branch_id+"', '"+first_no+"', '"+last_no+"', 0, \n" + 
				"'"+pagibig_id+"', '"+UserInfo.EmployeeCode+"', (case when '"+status+"' = 'Active' then 'A' else 'I' end), '"+logged_date+"', null, "+intSeries.toString()+");";
		db.executeUpdate(sql, true);
		db.commit();
		displayReceiptDetails();
	}
	
	private void updateReceipt(String co_id, String doc_id, String branch_id, String first_no, String last_no, String status, String receipt_id, String pagibig_id, Boolean blnExists){
		pgUpdate db = new pgUpdate();
		String sql =  
				"UPDATE cs_receipt_book\n" + 
				"SET co_id = '"+co_id+"', " +
				"doc_id = '"+doc_id+"', " +
				"branch_id = '"+branch_id+"', " +
				"first_no = "+first_no+", " +
				"last_no = "+last_no+", \n" + 
				"pagibig_id = '"+pagibig_id+"'," +
				"status_id = (case when '"+status+"' = 'Active' then 'A' else 'I' end), \n" + 
				"inactive_date = (case when '"+status+"' = 'Active' then null else now() end) \n" +
				"WHERE rec_id = "+receipt_id+"\n";
		db.executeUpdate(sql, true);
		db.commit();
		displayReceiptDetails();
	}
	
	private boolean ValidateSeries(String strFirst, String strLast, String strReceiptType) {
		if (strReceiptType.equals("03")) {
			return FncGlobal.GetBoolean("select (case when count(*) > 0 then true else false end) \n" + 
					"from \n" + 
					"( \n" + 
					"select or_no as receipt_no from rf_payments where (or_doc_id is null or or_doc_id = '03') and pr_doc_id = '03' \n" + 
					"union\n" + 
					"select ar_no as receipt_no from rf_payments where (or_doc_id is not null or or_doc_id = '01') and pr_doc_id = '03' \n" + 
					"union\n" + 
					"select ar_no as receipt_no from rf_payments where or_doc_id = '03' and coalesce(pr_doc_id, '') = '' \n" + 
					"union \n" + 
					"select x.receipt_no as receipt_no from rf_pay_detail x where x.receipt_type = '"+strReceiptType+"' and x.status_id = 'A' \n" + 
					"union \n" + 
					"select x.receipt_no as receipt_no from rf_tra_Detail x where x.receipt_type = '"+strReceiptType+"' and x.status_id = 'A' \n" + 
					") a \n" + 
					"where a.receipt_no is not null and a.receipt_no != '' and a.receipt_no::int between '"+strFirst+"'::int and '"+strLast+"'::int;");	
		} else {
			return FncGlobal.GetBoolean("select (case when count(*) > 0 then true else false end) \n" + 
					"from \n" + 
					"( \n" + 
					"select or_no as receipt_no from rf_payments where or_doc_id = '01' or or_date is not null \n" + 
					"union \n" + 
					"select x.receipt_no as receipt_no from rf_pay_detail x where x.receipt_type = '"+strReceiptType+"' and x.status_id = 'A' \n" + 
					"union \n" + 
					"select x.receipt_no as receipt_no from rf_tra_Detail x where x.receipt_type = '"+strReceiptType+"' and x.status_id = 'A' \n" + 
					") a \n" + 
					"where a.receipt_no is not null and a.receipt_no != '' and a.receipt_no::int between '"+strFirst+"'::int and '"+strLast+"'::int;");
		}
	}
}
