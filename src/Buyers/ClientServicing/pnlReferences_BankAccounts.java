package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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

import org.jdesktop.swingx.JXFormattedTextField;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelBankAccounts;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;

/**
 * @author JOHN LESTER FATALLO
 */

public class pnlReferences_BankAccounts extends JPanel implements _GUI,
ActionListener {

	private static final long serialVersionUID = 4863925816366609661L;
	private JPanel pnlCenter;
	private JPanel pnlCenterNorth;

	private JPanel pnlBank;
	private JPanel pnlBankWest;
	private JLabel lblBankName;
	private JLabel lblBankBranch;
	private JLabel lblAccntType;
	private JLabel lblDateOpened;
	private JLabel lblStatus;
	private JPanel pnlBankCenter;
	private JTextField txtBankName;
	private JTextField txtBankBranch;
	private JTextField txtAccntType;
	private _JDateChooser dateOpened;
	private JComboBox cmbStatus;

	private JPanel pnlAccntNum;
	private JPanel pnlAccntNumWest;
	private JLabel lblEndorsed;
	private JLabel lblAccntnum;
	private JLabel lblBalance;
	private JPanel pnlAccntNumCenter;
	private JCheckBox chkEndorsed;
	private JTextField txtAccntNum;
	private _JXFormattedTextField txtBalance;

	private JScrollPane scrollBankAccntList;
	private _JTableMain tblBankAccntList;
	private modelBankAccounts modelBankAccnt;
	private JList rowHeaderRef_BankAccnt;

	private _JLookup lookupBankName;
	private _JLookup lookupBankBranch;
	private _JLookup lookupAccntType;

	private ClientInformation ci;

	public pnlReferences_BankAccounts(ClientInformation ci) {
		this.ci=ci;
		initGUI();
	}

	public pnlReferences_BankAccounts(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlReferences_BankAccounts(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlReferences_BankAccounts(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {

		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(708, 394));
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			this.add(pnlCenter, BorderLayout.CENTER);
			{
				pnlCenterNorth = new JPanel(new BorderLayout(5, 5));
				pnlCenter.add(pnlCenterNorth, BorderLayout.NORTH);
				pnlCenterNorth.setPreferredSize(new Dimension(1015, 147));
				{
					pnlBank = new JPanel(new BorderLayout(5, 5));
					pnlCenterNorth.add(pnlBank, BorderLayout.CENTER);
					pnlBank.setPreferredSize(new Dimension(386, 147));
					{
						pnlBankWest = new JPanel(new GridLayout(5, 1, 5, 5));
						pnlBank.add(pnlBankWest, BorderLayout.WEST);
						pnlBankWest.setPreferredSize(new Dimension(92, 147));
						{
							lblBankName = new JLabel("*Bank Name");
							pnlBankWest.add(lblBankName);
						}
						{
							lblBankBranch = new JLabel("*Bank Branch");
							pnlBankWest.add(lblBankBranch);
						}
						{
							lblAccntType = new JLabel("*Account Type");
							pnlBankWest.add(lblAccntType);
						}
						{
							lblDateOpened = new JLabel("Date Opened");
							pnlBankWest.add(lblDateOpened);
						}
						{
							lblStatus = new JLabel("Status");
							pnlBankWest.add(lblStatus);
						}
					}
					{
						pnlBankCenter = new JPanel(new GridLayout(5, 1, 5, 5));
						pnlBank.add(pnlBankCenter, BorderLayout.CENTER);
						{	
							JPanel pnlBankName = new JPanel(new BorderLayout(3, 3));
							pnlBankCenter.add(pnlBankName);
							{
								lookupBankName = new _JLookup(null, "Bank Name", 0);
								pnlBankName.add(lookupBankName, BorderLayout.WEST);
								lookupBankName.setLookupSQL(sqlBankName());
								lookupBankName.setFilterName(true);
								lookupBankName.setPreferredSize(new Dimension(100, 0));
								lookupBankName.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										
										FncSystem.out("Display SQL for Bank Name", lookupBankName.getLookupSQL());
										
										if (data != null){
											String bank_id = (String) data[0];
											String bank_name = (String) data[1];
											txtBankName.setText(bank_name);
											lookupBankBranch.setLookupSQL(sqlBankBranch(bank_id));
											lookupBankBranch.setValue(null);
											txtBankBranch.setText("");
										}
									}
								});
							}
							{
								txtBankName = new JTextField();
								pnlBankName.add(txtBankName);
							}
						}
						{
							JPanel pnlBankBranch = new JPanel(new BorderLayout(3, 3));
							pnlBankCenter.add(pnlBankBranch);
							{
								lookupBankBranch = new _JLookup(null, "Bank Branch", 0, "Please select bank first");
								pnlBankBranch.add(lookupBankBranch, BorderLayout.WEST);
								lookupBankBranch.setPreferredSize(new Dimension(100, 0));
								lookupBankBranch.setFilterName(true);
								lookupBankBranch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										
										FncSystem.out("Display SQL for Bank Branch", lookupBankBranch.getLookupSQL());
										if (data != null){
											String branch_name = (String) data[1];
											txtBankBranch.setText(branch_name);
										}
									}
								});
							}
							{
								txtBankBranch = new JTextField();
								pnlBankBranch.add(txtBankBranch);
							}
						}
						{
							JPanel pnlAccntType = new JPanel(new BorderLayout(3, 3));
							pnlBankCenter.add(pnlAccntType);
							{
								lookupAccntType = new _JLookup(null, "Account Type", 0);
								pnlAccntType.add(lookupAccntType, BorderLayout.WEST);
								lookupAccntType.setLookupSQL(sqlAccntType());
								lookupAccntType.setPreferredSize(new Dimension(100, 0));
								lookupAccntType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										if (data != null){
											String acct_name = (String) data[1];
											txtAccntType.setText(acct_name);
										}
									}
								});
							}
							{
								JPanel pnltxtAccntType = new JPanel(new BorderLayout(5, 5));
								pnlAccntType.add(pnltxtAccntType);
								{
									txtAccntType = new JTextField();
									pnltxtAccntType.add(txtAccntType);
									txtAccntType.setPreferredSize(new Dimension(100, 0));
								}
							}
						}
						{
							JPanel pnlDateOpened = new JPanel(new BorderLayout());
							pnlBankCenter.add(pnlDateOpened);
							{
								dateOpened = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlDateOpened.add(dateOpened, BorderLayout.WEST);
								dateOpened.setPreferredSize(new Dimension(120, 0));
							}
						}
						{	
							JPanel pnlBankAccntStatus = new JPanel(new BorderLayout());
							pnlBankCenter.add(pnlBankAccntStatus);
							{
								cmbStatus = new JComboBox(new String[] { "Active", "Inactive" });
								pnlBankAccntStatus.add(cmbStatus, BorderLayout.WEST);
								cmbStatus.setPreferredSize(new Dimension(120, 0));
							}
						}
					}
				}
				{
					pnlAccntNum = new JPanel(new BorderLayout(5, 5));
					pnlCenterNorth.add(pnlAccntNum, BorderLayout.EAST);
					{
						pnlAccntNumWest = new JPanel(new GridLayout(5, 1, 5, 5));
						pnlAccntNum.add(pnlAccntNumWest, BorderLayout.WEST);
						pnlAccntNumWest.setPreferredSize(new Dimension(90, 147));
						{
							lblEndorsed = new JLabel("Endorsed?");
							pnlAccntNumWest.add(lblEndorsed);
						}
						{
							lblBalance = new JLabel("Balance");
							pnlAccntNumWest.add(lblBalance);
						}
						{
							lblAccntnum = new JLabel("Account No.");
							pnlAccntNumWest.add(lblAccntnum);
						}
					}
					{
						pnlAccntNumCenter = new JPanel(new GridLayout(5, 1, 5, 5));
						pnlAccntNum.add(pnlAccntNumCenter, BorderLayout.CENTER);
						pnlAccntNumCenter.setPreferredSize(new Dimension(154, 147));
						{
							chkEndorsed = new JCheckBox();
							pnlAccntNumCenter.add(chkEndorsed);
						}
						{
							JPanel pnltxtBalance = new JPanel(new BorderLayout(5, 5));
							pnlAccntNumCenter.add(pnltxtBalance, BorderLayout.WEST);
							{
								txtBalance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnltxtBalance.add(txtBalance, BorderLayout.WEST);
								txtBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBalance.setValue(new BigDecimal("0.00"));
								txtBalance.setPreferredSize(new Dimension(150, 0));
							}
						}
						{
							JPanel pnltxtAccntNum = new JPanel(new BorderLayout(5, 5));
							pnlAccntNumCenter.add(pnltxtAccntNum, BorderLayout.WEST);
							{
								txtAccntNum = new JTextField();
								pnltxtAccntNum.add(txtAccntNum, BorderLayout.WEST);
								txtAccntNum.setPreferredSize(new Dimension(150, 0));
							}
						}
						
					}
				}
			}
			{
				scrollBankAccntList = new JScrollPane();
				pnlCenter.add(scrollBankAccntList, BorderLayout.CENTER);
				scrollBankAccntList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollBankAccntList.setPreferredSize(new Dimension(836, 220));
				{
					modelBankAccnt = new modelBankAccounts();
					tblBankAccntList = new _JTableMain(modelBankAccnt);
					scrollBankAccntList.setViewportView(tblBankAccntList);
					tblBankAccntList.setSortable(false);
					tblBankAccntList.hideColumns("Rec. ID","Bank ID", "Branch ID", "Account Type ID", "RTD Rec. ID");
					tblBankAccntList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					
					modelBankAccnt.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							//_OrderOfPayment.totalPayments(modelConnList, modelPaymentListTotal);

							((DefaultListModel)rowHeaderRef_BankAccnt.getModel()).addElement(modelBankAccnt.getRowCount());
							scrollBankAccntList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBankAccntList.getRowCount())));


							if(modelBankAccnt.getRowCount() == 0){
								rowHeaderRef_BankAccnt.setModel(new DefaultListModel());
							}
						}
					});
					tblBankAccntList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent arg0) {
							if(!arg0.getValueIsAdjusting()){
								try {
									if(tblBankAccntList.getSelectedRows().length ==1){
										int row = tblBankAccntList.getSelectedRow();
										
										String bank_id = (String) modelBankAccnt.getValueAt(row, 1);
										String bank_name = (String) modelBankAccnt.getValueAt(row, 2);
										String branch_id = (String) modelBankAccnt.getValueAt(row, 3);
										String branch_name = (String) modelBankAccnt.getValueAt(row, 4);
										Boolean endorsed = (Boolean) modelBankAccnt.getValueAt(row, 5);
										String accttype_id = (String) modelBankAccnt.getValueAt(row, 6);
										String accttype_name = (String) modelBankAccnt.getValueAt(row, 7);
										String acct_no = (String) modelBankAccnt.getValueAt(row, 8);
										Date date_opened = (Date) modelBankAccnt.getValueAt(row, 9);
										BigDecimal ave_balance = (BigDecimal) modelBankAccnt.getValueAt(row, 10);
										String bank_acct_stat = (String) modelBankAccnt.getValueAt(row, 11);
										
										lookupBankName.setValue(bank_id);
										txtBankName.setText(bank_name);
										lookupBankBranch.setValue(branch_id);
										txtBankBranch.setText(branch_name);
										chkEndorsed.setSelected(endorsed);
										lookupAccntType.setValue(accttype_id);
										txtAccntType.setText(accttype_name);
										txtAccntNum.setText(acct_no);
										dateOpened.setDate(date_opened);
										txtBalance.setValue(ave_balance);
										
										if (bank_acct_stat.equals("A")){
											cmbStatus.setSelectedItem("Active");
										}else{
											cmbStatus.setSelectedItem("Inactive");
										}
										ci.btnState(true, true, false, false, false);
										
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
					rowHeaderRef_BankAccnt = tblBankAccntList.getRowHeader();
					rowHeaderRef_BankAccnt.setModel(new DefaultListModel());
					scrollBankAccntList.setRowHeaderView(rowHeaderRef_BankAccnt);
					scrollBankAccntList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupBankName, lookupBankBranch, lookupAccntType, dateOpened, 
				cmbStatus, chkEndorsed, txtAccntNum, txtBalance));
		ci.setComponentsEditable(pnlCenterNorth, false);
		setComponentsEnabled(false, false, false, false, false, false);
	}//END OF INIT GUI
	
	private void setComponentsEnabled(Boolean bank_name, Boolean bank_branch, Boolean acct_type, Boolean endorsed, Boolean date_opened, Boolean status){
		txtBankName.setEditable(bank_name);
		txtBankBranch.setEditable(bank_branch);
		txtAccntType.setEditable(acct_type);
		chkEndorsed.setEnabled(endorsed);
		dateOpened.setEnabled(date_opened);
		cmbStatus.setEnabled(status);
	}
	
	public void displayBankAccnts(String entity_id){
		modelBankAccnt.clear();
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM view_ci_bank_accounts('"+entity_id+"')";
		db.select(SQL);
		
		FncSystem.out("Display Bank Accounts", SQL);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				modelBankAccnt.addRow(db.getResult()[x]);
			}
			scrollBankAccntList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBankAccntList.getRowCount())));
			tblBankAccntList.packAll();
		}
	}

	private String sqlBankBranch(String bank_id){//SQL FOR THE BANK BRANCH
		return "Select trim(bank_branch_id) as \"Branch ID\", trim(bank_branch_location) as \"Bank Branch\" from mf_bank_branch where status_id = 'A' and bank_id = '"+bank_id+"' order by bank_branch_location";
	}

	private String sqlAccntType(){//SQL FOR THE ACCOUNT TYPE
		return "select trim(bank_accttype_id) as \"ID\", trim(bank_accttype_desc) as \"Account Type\" from mf_bank_account_type where status_id ='A'";
	}

	private String sqlBankName(){//SQL FOR THE BANK NAME
		return "select trim(bank_id) as \"ID\", trim(bank_name) as \"Bank Name\", trim(bank_alias) as \"Bank Alias\" \n" + 
				"from mf_bank\n" + 
				"where status_id ='A' \n"+
				"order by bank_name";
	}

	public boolean toSave(){//CHECKS FOR THE REQUIRED FIELDS BEFORE SAVING
		if (lookupBankName.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblBankName.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupBankBranch.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblBankBranch.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupAccntType.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblAccntType.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if (dateOpened.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblDateOpened.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtAccntNum.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblAccntnum.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		return true;
	}
	
	//CHECKS WHETHER BANK ACCOUNT IS ALREADY EXISTING
	private boolean isBankAcctExisting(String entity_id, Integer rec_id){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_bank_accounts where entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	public void newBankAccnt(){//NEW BANK ACCOUNT
		try{
			tblBankAccntList.clearSelection();
		} catch (ArrayIndexOutOfBoundsException e) {}
		tblBankAccntList.setEnabled(false);
		rowHeaderRef_BankAccnt.setEnabled(false);
		ci.setComponentsEditable(pnlCenterNorth, true);
		setComponentsEnabled(false, false, false, true, true, true);
	}
	
	public void editBankList(){//EDITING FOR THE BANK ACCOUNTS
		if(tblBankAccntList.getSelectedRows().length ==1){
			ci.setComponentsEditable(pnlCenterNorth, true);
			setComponentsEnabled(false, false, false, true, true, true);
			tblBankAccntList.setEnabled(false);
			rowHeaderRef_BankAccnt.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollBankAccntList, "Please select only one row to edit");
			tblBankAccntList.clearSelection();
		}
	}

	public boolean saveBankAccnts(String entity_id){//SAVING AND UPDATING OF THE BANK ACCOUNTS 
		//pgUpdate db = new pgUpdate();
		
		pgSelect db = new pgSelect();
		
		String bank_id = lookupBankName.getValue();
		String branch_id = lookupBankBranch.getValue();
		String accttype_id = lookupAccntType.getValue();
		String acct_no = txtAccntNum.getText();
		Date date_opened = dateOpened.getDate();
		BigDecimal ave_balance = (BigDecimal) txtBalance.getValued();
		String bankacct_status = (String) cmbStatus.getSelectedItem();
		Boolean endorsed = chkEndorsed.isSelected();
		Integer rec_id = null;
		Integer rtd_rec_id = null;
		
		//UPDATING
		if(tblBankAccntList.getSelectedRows().length == 1){
			int row = tblBankAccntList.getSelectedRow();
			rec_id = (Integer) modelBankAccnt.getValueAt(row, 0);
			rtd_rec_id = (Integer) modelBankAccnt.getValueAt(row, 12);
		}
		
		String SQL = "SELECT sp_save_ci_bank_accts("+rec_id+", "+rtd_rec_id+", '"+entity_id+"', '"+bank_id+"', '"+branch_id+"', '"+accttype_id+"', \n"+
					 "'"+acct_no+"', NULLIF('"+date_opened+"', 'null')::TIMESTAMP, "+ave_balance+", '"+bankacct_status+"', "+endorsed+");";
		db.select(SQL);
		FncSystem.out("Display SQL for saving of bank accts: ", SQL);
		JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Bank Account has Been saved", "Save", JOptionPane.INFORMATION_MESSAGE);
		/*if(tblBankAccntList.getSelectedRows().length == 1){
			int row = tblBankAccntList.getSelectedRow();
			Integer rec_id = (Integer) modelBankAccnt.getValueAt(row, 0);
			Integer rtd_rec_id = (Integer) modelBankAccnt.getValueAt(row, 12);
			
			if(isBankAcctExisting(entity_id, rec_id)){
				String sql = "UPDATE rf_bank_accounts SET bank_id= '"+bank_id+"', \n" + 
							 "bank_branch_id= '"+branch_id+"', accttype_id= '"+accttype_id+"', account_no= '"+acct_no+"', date_opened= NULLIF('"+date_opened+"', 'null')::TIMESTAMP, \n" + 
							 "ave_balance="+ave_balance+", endorsed="+endorsed+", \n"+
							 "status_id= (case when '"+bankacct_status+"' = 'Active' then 'A' else 'I' end) \n" + 
							 "WHERE entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
				db.executeUpdate(sql, true);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Bank Account has been Updated");
			}
		}else{//SAVING
			String sql = "INSERT INTO rf_bank_accounts(\n" + 
						 "entity_id, projcode, pbl_id, seq_no, bank_id, bank_branch_id, \n" + 
						 "accttype_id, account_no, date_opened, ave_balance, endorsed, status_id, rowguid)\n" + 
						 "VALUES ('"+ entity_id +"', null, null , null, \n"+
						 "'"+bank_id+"', '"+branch_id +"','"+accttype_id+"', '"+acct_no+"', NULLIF('"+date_opened +"', 'null')::TIMESTAMP, "+ ave_balance +" , "+ endorsed +", \n" + 
						 "(case when '"+ bankacct_status +"' = 'Active' then 'A' else 'I' end), '')";
			db.executeUpdate(sql, true);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Bank Account has been Saved");
		}
		db.commit();*/
		
		ci.setComponentsEditable(pnlCenterNorth, false);
		setComponentsEnabled(false, false, false, false, false, false);
		tblBankAccntList.clearSelection();
		tblBankAccntList.setEnabled(true);
		rowHeaderRef_BankAccnt.setEnabled(true);
		return true;
	}
	
	
	public void cancelBankAccnt(){//CANCELATION OF BANK ACCOUNTS
		ci.setComponentsEditable(pnlCenterNorth, false);
		setComponentsEnabled(false, false, false, false, false, false);
		clearFields();
		tblBankAccntList.clearSelection();
		tblBankAccntList.setEnabled(true);
		rowHeaderRef_BankAccnt.setEnabled(true);
	}

	public void clearFields(){//CLEARS THE FIELDS IN THIS PANEL
		lookupBankName.setValue(null);
		txtBankName.setText("");
		lookupBankBranch.setValue(null);
		txtBankBranch.setText("");
		lookupAccntType.setValue(null);
		txtAccntType.setText("");
		dateOpened.setDate(null);
		cmbStatus.setSelectedItem("Active");
		chkEndorsed.setSelected(false);
		txtAccntNum.setText("");
		txtBalance.setText("0.00");
		tblBankAccntList.clearSelection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX action

	}
}
