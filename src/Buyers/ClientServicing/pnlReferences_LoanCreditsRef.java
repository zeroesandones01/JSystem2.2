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
import tablemodel.modelLoanCreditRef;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncSystem;
import Functions.FncTables;
import components._JTableMain;

/**
 * @author JOHN LESTER FATALLO
 */

public class pnlReferences_LoanCreditsRef extends JPanel implements _GUI,
ActionListener {

	private static final long serialVersionUID = -5572490190881794210L;

	private JPanel pnlNorth;

	private JPanel pnlBank;
	private JPanel pnlBankWest;
	private JLabel lblBank;
	private JLabel lblAddress;
	private JPanel pnlBankCenter;
	private JTextField txtBank;
	private JTextField txtAddress;

	private JPanel pnlNorthLower;
	private JPanel pnlPurpose;

	private JPanel pnlPurposeWest;
	private JLabel lblPurpose;
	private JLabel lblHighestAmt;
	private JLabel lbldateObtained;
	private JLabel lblStatus;

	private JPanel pnlPurposeCenter;
	private JTextField txtPurpose;
	private _JXFormattedTextField txtHighestAmt;
	private _JDateChooser dateObtained;
	private JComboBox cmbStatus;

	private JPanel pnlSecurity;
	private JPanel pnlSecurityWest;
	private JLabel lblSecurity;
	private JLabel lblBalance;
	private JLabel lblDateFullyPaid;
	private JPanel pnlSecurityCenter;
	private JTextField txtSecurity;
	private _JXFormattedTextField txtBalance;
	private _JDateChooser dateFullPaid;

	private JScrollPane scrollLoanCreditRef;
	private modelLoanCreditRef modelLoanCrdtsRef;
	private _JTableMain tblLoanCreditRef;
	private JList rowHeaderRef_LoanCreditsRef;

	private ClientInformation ci;

	public pnlReferences_LoanCreditsRef(ClientInformation ci) {
		this.ci=ci;
		initGUI();
	}

	public pnlReferences_LoanCreditsRef(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlReferences_LoanCreditsRef(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlReferences_LoanCreditsRef(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}
	@Override
	public void initGUI() {

		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(658, 391));
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setPreferredSize(new Dimension(648, 199));
			{
				pnlBank = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlBank, BorderLayout.NORTH);
				pnlBank.setPreferredSize(new Dimension(648, 60));
				{
					pnlBankWest = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlBank.add(pnlBankWest, BorderLayout.WEST);
					pnlBankWest.setPreferredSize(new Dimension(146, 60));
					{
						lblBank = new JLabel("*Bank/Financial Inst.");
						pnlBankWest.add(lblBank);
					}
					{
						lblAddress = new JLabel("*Address");
						pnlBankWest.add(lblAddress);
					}
				}
				{
					pnlBankCenter = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlBank.add(pnlBankCenter, BorderLayout.CENTER);
					{
						txtBank = new JTextField();
						pnlBankCenter.add(txtBank);
					}
					{
						txtAddress = new JTextField();
						pnlBankCenter.add(txtAddress);
					}
				}
			}
			{
				pnlNorthLower = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorthLower, BorderLayout.SOUTH);
				pnlNorthLower.setPreferredSize(new Dimension(648, 132));
				{
					pnlPurpose = new JPanel(new BorderLayout(5, 5));
					pnlNorthLower.add(pnlPurpose, BorderLayout.WEST);
					pnlPurpose.setPreferredSize(new Dimension(321, 132));
					{
						pnlPurposeWest = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlPurpose.add(pnlPurposeWest, BorderLayout.WEST);
						pnlPurposeWest.setPreferredSize(new Dimension(146, 132));
						{
							lblPurpose = new JLabel("Purpose");
							pnlPurposeWest.add(lblPurpose);
						}
						{
							lblHighestAmt = new JLabel();
							pnlPurposeWest.add(lblHighestAmt);
							lblHighestAmt.setText("Highest Amount Owed");
						}
						{
							lbldateObtained = new JLabel("*Date Obtained");
							pnlPurposeWest.add(lbldateObtained);
						}
						{
							lblStatus = new JLabel("Status");
							pnlPurposeWest.add(lblStatus);
						}
					}
					{
						pnlPurposeCenter = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlPurpose.add(pnlPurposeCenter, BorderLayout.CENTER);
						{
							txtPurpose = new JTextField();
							pnlPurposeCenter.add(txtPurpose);
						}
						{
							txtHighestAmt =  new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlPurposeCenter.add(txtHighestAmt);
							txtHighestAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtHighestAmt.setText("0.00");
						}
						{
							JPanel pnldteObtained = new JPanel(new BorderLayout());
							pnlPurposeCenter.add(pnldteObtained);
							{
								dateObtained = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnldteObtained.add(dateObtained, BorderLayout.WEST);
								dateObtained.setPreferredSize(new Dimension(120, 0));
							}
						}
						{	
							JPanel pnlLoanCreditsRefStat = new JPanel(new BorderLayout());
							pnlPurposeCenter.add(pnlLoanCreditsRefStat);
							{
								cmbStatus = new JComboBox(new String[] { "Active", "Inactive" });
								pnlLoanCreditsRefStat.add(cmbStatus, BorderLayout.WEST);
								cmbStatus.setPreferredSize(new Dimension(120, 0));
							}
						}
					}
				}
				{
					pnlSecurity = new JPanel(new BorderLayout(5, 5));
					pnlNorthLower.add(pnlSecurity, BorderLayout.CENTER);
					{
						pnlSecurityWest = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlSecurity.add(pnlSecurityWest, BorderLayout.WEST);
						pnlSecurityWest.setPreferredSize(new Dimension(97, 132));
						{
							lblSecurity = new JLabel("Security");
							pnlSecurityWest.add(lblSecurity, BorderLayout.CENTER);
						}
						{
							lblBalance = new JLabel("Balance");
							pnlSecurityWest.add(lblBalance);
						}
						{
							lblDateFullyPaid = new JLabel("Date Fully Paid");
							pnlSecurityWest.add(lblDateFullyPaid);
						}
					}
					{
						pnlSecurityCenter = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlSecurity.add(pnlSecurityCenter, BorderLayout.CENTER);
						{
							JPanel pnltxtSecurity = new JPanel(new BorderLayout());
							pnlSecurityCenter.add(pnltxtSecurity);
							{
								txtSecurity = new JTextField();
								pnltxtSecurity.add(txtSecurity, BorderLayout.WEST);
								txtSecurity.setPreferredSize(new Dimension(150, 0));
							}
						}
						{
							JPanel pnltxtBalance = new JPanel(new BorderLayout());
							pnlSecurityCenter.add(pnltxtBalance);
							{
								txtBalance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnltxtBalance.add(txtBalance, BorderLayout.WEST);
								txtBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBalance.setText("0.00");
								txtBalance.setPreferredSize(new Dimension(150, 0));
							}
						}
						{
							JPanel pnldteFullPaid = new JPanel(new BorderLayout());
							pnlSecurityCenter.add(pnldteFullPaid);
							{
								dateFullPaid = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnldteFullPaid.add(dateFullPaid, BorderLayout.WEST);
								dateFullPaid.setPreferredSize(new Dimension(120, 0));
							}
						}
					}
				}
			}
		}
		{
			scrollLoanCreditRef = new JScrollPane();
			this.add(scrollLoanCreditRef, BorderLayout.CENTER);
			scrollLoanCreditRef.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelLoanCrdtsRef = new modelLoanCreditRef();
				tblLoanCreditRef = new _JTableMain(modelLoanCrdtsRef);
				scrollLoanCreditRef.setViewportView(tblLoanCreditRef);
				tblLoanCreditRef.hideColumns("Rec. ID");
				tblLoanCreditRef.setSortable(false);
				tblLoanCreditRef.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				modelLoanCrdtsRef.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						//_OrderOfPayment.totalPayments(modelConnList, modelPaymentListTotal);

						((DefaultListModel)rowHeaderRef_LoanCreditsRef.getModel()).addElement(modelLoanCrdtsRef.getRowCount());
						scrollLoanCreditRef.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLoanCreditRef.getRowCount())));


						if(modelLoanCrdtsRef.getRowCount() == 0){
							rowHeaderRef_LoanCreditsRef.setModel(new DefaultListModel());
						}
					}
				});
				tblLoanCreditRef.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if(!arg0.getValueIsAdjusting()){
							if(tblLoanCreditRef.getSelectedRows().length ==1){

								int row = tblLoanCreditRef.getSelectedRow();

								String bank = (String) modelLoanCrdtsRef.getValueAt(row, 1);
								String address = (String) modelLoanCrdtsRef.getValueAt(row, 2);
								String purpose = (String) modelLoanCrdtsRef.getValueAt(row, 3);
								String security = (String) modelLoanCrdtsRef.getValueAt(row, 4);
								BigDecimal highest_amt_owed = (BigDecimal) modelLoanCrdtsRef.getValueAt(row, 5);
								BigDecimal present_balance = (BigDecimal) modelLoanCrdtsRef.getValueAt(row, 6);
								Date date_obtained = (Date) modelLoanCrdtsRef.getValueAt(row, 7);
								Date date_fully_paid = (Date) modelLoanCrdtsRef.getValueAt(row, 8);
								String loan_credits_ref_stat = (String) modelLoanCrdtsRef.getValueAt(row, 9);

								txtBank.setText(bank);
								txtAddress.setText(address);
								txtPurpose.setText(purpose);
								txtSecurity.setText(security);
								txtHighestAmt.setValue(highest_amt_owed);
								txtBalance.setValue(present_balance);
								dateObtained.setDate(date_obtained);
								dateFullPaid.setDate(date_fully_paid);
								if (loan_credits_ref_stat.equals("A")){
									cmbStatus.setSelectedItem("Active");
								}else{
									cmbStatus.setSelectedItem("Inactive");
								}
								ci.btnState(true, true, false, false, false);
								
								if(ci.canEdit() == false){
									ci.btnState(false, false, false, false, false);
								}
								
							}
						}
					}
				});
			}
			{
				rowHeaderRef_LoanCreditsRef = tblLoanCreditRef.getRowHeader();
				rowHeaderRef_LoanCreditsRef.setModel(new DefaultListModel());
				scrollLoanCreditRef.setRowHeaderView(rowHeaderRef_LoanCreditsRef);
				scrollLoanCreditRef.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtBank, txtAddress, txtPurpose, txtHighestAmt, dateObtained, cmbStatus,
				txtSecurity, txtBalance, dateFullPaid));
		ci.setComponentsEditable(pnlNorth, false);
		setComponentsEnabled(false, false, false);
	}//END OF INIT GUI

	public void setComponentsEnabled(Boolean date_obtained, Boolean date_fully_paid, Boolean status){
		dateFullPaid.setEnabled(date_fully_paid);
		dateObtained.setEnabled(date_obtained);
		cmbStatus.setEnabled(status);
	}

	public void displayLoanCreditsRef(String entity_id){ //DISPLAYING OF LOAN CREDITS REFERENCE BASED ON THE SELECTED CLIENT
		modelLoanCrdtsRef.clear();

		String sql ="SELECT rec_id, bank, address, purpose, security, highest_amt_owed, \n" + 
					"present_balance, date_obtained, date_fullypaid, status_id \n" + 
					"FROM rf_loancredit_references where entity_id = '"+ entity_id +"'";

		FncSystem.out("Display Loan Credits Reference", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact number of columns and column types.
				modelLoanCrdtsRef.addRow(db.getResult()[x]);
			}
			scrollLoanCreditRef.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLoanCreditRef.getRowCount())));
			tblLoanCreditRef.packAll();
		}
	}

	public boolean toSave() {//CHECKS THE REQUIRED FIELDS BEFORE SAVING
		if (txtBank.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblBank.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtAddress.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblAddress.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (dateObtained.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lbldateObtained.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if (dateFullPaid.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblDateFullyPaid.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		return true;
	}

	//CHECKS WHETHER THE LOAN CREDITS REFERENCE IS ALREADY EXISTING
	public boolean isLoanCreditRefExisting(String entity_id, Integer rec_id){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_loancredit_references where entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	public void newLoanCreditsRef(){//NEW LOAN CREDITS REFERENCE
		ci.setComponentsEditable(pnlNorth, true);
		setComponentsEnabled(true, true, true);
		try{
			tblLoanCreditRef.clearSelection();
		} catch (ArrayIndexOutOfBoundsException e) {}
		tblLoanCreditRef.setEnabled(false);
		rowHeaderRef_LoanCreditsRef.setEnabled(false);
	}

	public void editLoanCreditsRef(){//EDITING FOR THE LOAN CREDITS REFERENCE
		if(tblLoanCreditRef.getSelectedRows().length ==1){
			ci.setComponentsEditable(pnlNorth, true);
			setComponentsEnabled(true, true, true);
			tblLoanCreditRef.setEnabled(false);
			rowHeaderRef_LoanCreditsRef.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollLoanCreditRef, "Please select only one row to edit");
			tblLoanCreditRef.clearSelection();
		}
	}

	public boolean saveLoanCreditsRef(String entity_id){ //SAVING AND UPDATING FOR THE LOAN CREDITS REFERENCE

		pgUpdate db = new pgUpdate();
		String bank = txtBank.getText();
		String address = txtAddress.getText();
		String purpose = txtPurpose.getText();
		String security = txtSecurity.getText();
		BigDecimal highest_amt_owed = (BigDecimal) txtHighestAmt.getValued();
		BigDecimal present_bal = (BigDecimal) txtBalance.getValued();
		Date date_obtained = dateObtained.getDate();
		Date date_fully_paid = dateFullPaid.getDate();
		String loan_credits_ref_status = (String) cmbStatus.getSelectedItem();
		
		//UPDATING
		if(tblLoanCreditRef.getSelectedRows().length ==1){
			int row = tblLoanCreditRef.getSelectedRow();
			Integer rec_id = (Integer) modelLoanCrdtsRef.getValueAt(row, 0);
			if(isLoanCreditRefExisting(entity_id, rec_id)){
				String sql = "UPDATE rf_loancredit_references SET bank='"+bank+"', address='"+address+"', purpose='"+purpose+"', security= '"+security+"', \n" + 
							 "highest_amt_owed="+highest_amt_owed+", present_balance= "+present_bal+", date_obtained='"+date_obtained+"', date_fullypaid= NULLIF('"+date_fully_paid+"', 'null')::TIMESTAMP, \n" + 
							 "status_id= (case when '"+loan_credits_ref_status+"' = 'Active' then 'A' else 'I' end)\n" + 
							 " WHERE entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
				db.executeUpdate(sql, true);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Loan Credits Ref has been Updated");
			}
		}else{//SAVING
			String sql = "INSERT INTO rf_loancredit_references(entity_id, bank, address, purpose, security, highest_amt_owed, \n" + 
						 "present_balance, date_obtained, date_fullypaid, status_id, rowguid)\n" + 
						 "VALUES ('"+ entity_id +"', '"+ bank +"', '"+ address +"', '"+ purpose +"', '"+ security +"', coalesce("+ highest_amt_owed +", 0.00), \n" + 
						 "coalesce("+ present_bal +",0.00), '"+ date_obtained +"', NULLIF('"+date_fully_paid+"', 'null')::TIMESTAMP,  \n" +
						 "(case when '"+ loan_credits_ref_status +"' = 'Active' then 'A' else 'I' end), '')";
			db.executeUpdate(sql, true);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Loan Credits Ref has been Saved");
		}
		db.commit();
		
		ci.setComponentsEditable(pnlNorth, false);
		setComponentsEnabled(false, false, false);
		tblLoanCreditRef.clearSelection();
		tblLoanCreditRef.setEnabled(true);
		rowHeaderRef_LoanCreditsRef.setEnabled(true);
		return true;
	}

	public void cancelLoanCreditsRef(){//CANCELATION FOR THE LOAN CREDITS REFERENCE
		
		ci.setComponentsEditable(pnlNorth, false);
		setComponentsEnabled(false, false, false);
		clearFields();
		tblLoanCreditRef.setEnabled(true);
		rowHeaderRef_LoanCreditsRef.setEnabled(true);
	}

	public void clearFields(){//CLEARS THE FIELDS FOR THIS PANEL
		txtBank.setText("");
		txtAddress.setText("");
		txtPurpose.setText("");
		txtSecurity.setText("");
		txtHighestAmt.setText("0.00");
		txtBalance.setText("0.00");
		dateObtained.setDate(null);
		dateFullPaid.setDate(null);
		tblLoanCreditRef.clearSelection();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
