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
import tablemodel.modelLoanCredits;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncTables;
import components._JTableMain;

/**
 * @author JOHN LESTER FATALLO
 */

public class pnlReferences_LoanCredits extends JPanel implements _GUI,
ActionListener {


	private static final long serialVersionUID = -5572490190881794210L;

	private JPanel pnlNorth;

	private JPanel pnlCreditor;
	private JPanel pnlCreditorWest;
	private JLabel lblCreditor;
	private JPanel pnlCreditorCenter;
	private JTextField txtCreditor;

	private JPanel pnlNorthLower;
	private JPanel pnlSecurity;
	private JPanel pnlSecurityWest;
	private JLabel lblSecurity;
	private JLabel lblLoanAmt;
	private JLabel lblMaturityDate;
	private JLabel lblStatus;

	private JPanel pnlSecurityCenter;
	private JTextField txtSecurity;
	private _JXFormattedTextField txtLoanAmt;
	private _JDateChooser maturityDate;
	private JComboBox cmbStatus;

	private JPanel pnlLoanType;
	private JPanel pnlLoanTypeWest;
	private JLabel lblLoanType;
	private JLabel lblMonthlyAmort;
	private JLabel lblBalance;
	private JPanel pnlLoanTypeCenter;
	private JTextField txtLoanType;
	private _JXFormattedTextField txtMontlyAmort;
	private _JXFormattedTextField txtBalance;

	private JScrollPane scrollLoanCreditsList;
	private modelLoanCredits modelLoanCrdts;
	private _JTableMain tblLoanCreditsList;
	private JList rowHeaderRef_LoanCredits;

	private ClientInformation ci;

	public pnlReferences_LoanCredits(ClientInformation ci) {
		this.ci=ci;
		initGUI();
	}
	public pnlReferences_LoanCredits(LayoutManager layout) {
		super(layout);
		initGUI();
	}
	public pnlReferences_LoanCredits(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlReferences_LoanCredits(LayoutManager layout,
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
			pnlNorth = new JPanel(new BorderLayout(3, 3));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setPreferredSize(new Dimension(648, 161));
			{
				pnlCreditor = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlCreditor, BorderLayout.NORTH);
				pnlCreditor.setPreferredSize(new Dimension(648, 30));
				{
					pnlCreditorWest = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlCreditor.add(pnlCreditorWest, BorderLayout.WEST);
					pnlCreditorWest.setPreferredSize(new Dimension(125, 30));
					{
						lblCreditor = new JLabel("*Creditor & Address");
						pnlCreditorWest.add(lblCreditor);
					}
				}
				{
					pnlCreditorCenter = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlCreditor.add(pnlCreditorCenter, BorderLayout.CENTER);
					{
						txtCreditor = new JTextField();
						pnlCreditorCenter.add(txtCreditor);
					}
				}
			}
			{
				pnlNorthLower = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorthLower, BorderLayout.SOUTH);
				pnlNorthLower.setPreferredSize(new Dimension(648, 125));
				{
					pnlSecurity = new JPanel(new BorderLayout(5, 5));
					pnlNorthLower.add(pnlSecurity, BorderLayout.WEST);
					pnlSecurity.setPreferredSize(new Dimension(320, 145));
					{
						pnlSecurityWest = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlSecurity.add(pnlSecurityWest, BorderLayout.WEST);
						pnlSecurityWest.setPreferredSize(new Dimension(125, 145));
						{
							lblSecurity = new JLabel("Security");
							pnlSecurityWest.add(lblSecurity);
						}
						{
							lblLoanAmt = new JLabel("Loan Amount");
							pnlSecurityWest.add(lblLoanAmt);
						}
						{
							lblMaturityDate = new JLabel("*Maturity Date");
							pnlSecurityWest.add(lblMaturityDate);
						}
						{
							lblStatus = new JLabel("Status");
							pnlSecurityWest.add(lblStatus);
						}
					}
					{
						pnlSecurityCenter = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlSecurity.add(pnlSecurityCenter, BorderLayout.CENTER);
						{
							txtSecurity = new JTextField();
							pnlSecurityCenter.add(txtSecurity);
						}
						{
							txtLoanAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlSecurityCenter.add(txtLoanAmt);
							txtLoanAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtLoanAmt.setText("0.00");
						}
						{
							JPanel pnlMaturity = new JPanel(new BorderLayout());
							pnlSecurityCenter.add(pnlMaturity);
							{
								maturityDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlMaturity.add(maturityDate, BorderLayout.WEST);
								maturityDate.setPreferredSize(new Dimension(120, 0));
								maturityDate.setEnabled(false);
							}
						}
						{	
							JPanel pnlloanCreditsStatus = new JPanel(new BorderLayout());
							pnlSecurityCenter.add(pnlloanCreditsStatus);
							{
								cmbStatus = new JComboBox(new String[] { "Active", "Inactive" });
								pnlloanCreditsStatus.add(cmbStatus, BorderLayout.WEST);
								cmbStatus.setPreferredSize(new Dimension(120, 0));
								cmbStatus.setEnabled(false);
							}
						}
					}
				}
				{
					pnlLoanType = new JPanel(new BorderLayout(5, 5));
					pnlNorthLower.add(pnlLoanType, BorderLayout.CENTER);
					pnlLoanType.setPreferredSize(new Dimension(336, 125));
					{
						pnlLoanTypeWest = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlLoanType.add(pnlLoanTypeWest, BorderLayout.WEST);
						pnlLoanTypeWest.setPreferredSize(new Dimension(129, 125));
						{
							lblLoanType = new JLabel("Loan Type");
							pnlLoanTypeWest.add(lblLoanType);
						}
						{
							lblMonthlyAmort = new JLabel("Monthly Amortization");
							pnlLoanTypeWest.add(lblMonthlyAmort);
						}
						{
							lblBalance = new JLabel("Balance");
							pnlLoanTypeWest.add(lblBalance);
						}
					}
					{
						pnlLoanTypeCenter = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlLoanType.add(pnlLoanTypeCenter, BorderLayout.CENTER);
						{
							JPanel pnltxtLoanType = new JPanel(new BorderLayout(5, 5));
							pnlLoanTypeCenter.add(pnltxtLoanType, BorderLayout.WEST);
							{
								txtLoanType = new JTextField();
								pnltxtLoanType.add(txtLoanType, BorderLayout.WEST);
								txtLoanType.setPreferredSize(new Dimension(150, 0));

							}
						}
						{
							JPanel pnltxtMonthlyAmort = new JPanel(new BorderLayout(5, 5));
							pnlLoanTypeCenter.add(pnltxtMonthlyAmort, BorderLayout.WEST);
							{
								txtMontlyAmort = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnltxtMonthlyAmort.add(txtMontlyAmort, BorderLayout.WEST);
								txtMontlyAmort.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtMontlyAmort.setText("0.00");
								txtMontlyAmort.setPreferredSize(new Dimension(150, 0));
							}
						}
						{
							JPanel pnltxtBalance = new JPanel(new BorderLayout(5, 5));
							pnlLoanTypeCenter.add(pnltxtBalance, BorderLayout.WEST);
							{
								txtBalance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnltxtBalance.add(txtBalance, BorderLayout.WEST);
								txtBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBalance.setText("0.00");
								txtBalance.setPreferredSize(new Dimension(150, 0));
							}
						}
					}
				}
			}
		}
		{
			scrollLoanCreditsList = new JScrollPane();
			this.add(scrollLoanCreditsList, BorderLayout.CENTER);
			scrollLoanCreditsList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelLoanCrdts = new modelLoanCredits();
				tblLoanCreditsList = new _JTableMain(modelLoanCrdts);
				scrollLoanCreditsList.setViewportView(tblLoanCreditsList);
				tblLoanCreditsList.hideColumns("Rec. ID");
				tblLoanCreditsList.setSortable(false);
				tblLoanCreditsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				modelLoanCrdts.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						//_OrderOfPayment.totalPayments(modelConnList, modelPaymentListTotal);

						((DefaultListModel)rowHeaderRef_LoanCredits.getModel()).addElement(modelLoanCrdts.getRowCount());
						scrollLoanCreditsList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLoanCreditsList.getRowCount())));


						if(modelLoanCrdts.getRowCount() == 0){
							rowHeaderRef_LoanCredits.setModel(new DefaultListModel());
						}
					}
				});
				tblLoanCreditsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if(!arg0.getValueIsAdjusting()){
							if(tblLoanCreditsList.getSelectedRows().length ==1){
							int row = tblLoanCreditsList.getSelectedRow();

							String creditor_addr = (String) modelLoanCrdts.getValueAt(row, 1);
							String security = (String) modelLoanCrdts.getValueAt(row, 2);
							String loan_type = (String) modelLoanCrdts.getValueAt(row, 3);
							BigDecimal loan_amt = (BigDecimal) modelLoanCrdts.getValueAt(row, 4);
							BigDecimal monthly_amort = (BigDecimal) modelLoanCrdts.getValueAt(row, 5);
							Date maturity_date = (Date) modelLoanCrdts.getValueAt(row, 6);
							BigDecimal balance = (BigDecimal) modelLoanCrdts.getValueAt(row, 7);
							String loan_credits_status = (String) modelLoanCrdts.getValueAt(row, 8);

							txtCreditor.setText(creditor_addr);
							txtSecurity.setText(security);
							txtLoanType.setText(loan_type);
							txtLoanAmt.setValue(loan_amt);
							txtMontlyAmort.setValue(monthly_amort);
							maturityDate.setDate(maturity_date);
							txtBalance.setValue(balance);

							if (loan_credits_status.equals("A")){
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
				rowHeaderRef_LoanCredits = tblLoanCreditsList.getRowHeader();
				rowHeaderRef_LoanCredits.setModel(new DefaultListModel());
				scrollLoanCreditsList.setRowHeaderView(rowHeaderRef_LoanCredits);
				scrollLoanCreditsList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtCreditor, txtSecurity, txtLoanAmt, maturityDate, cmbStatus, txtLoanType, txtMontlyAmort, txtBalance));
		ci.setComponentsEditable(pnlNorth, false);
	}//END OF INIT GUI
	
	//DISPLAYS THE LIST OF THE LOAN CREDITS FOR THE PARTICULAR CLIENT
	public void displayLoanCredits(String entity_id){
		try{
		modelLoanCrdts.clear();
		} catch (ArrayIndexOutOfBoundsException e) {}
		String sql = "SELECT rec_id, creditor_addr, security, loan_type, \n" + 
				     "loan_amount, monthly_amort, maturity_date, balance, status_id \n" + 
				     "FROM rf_creditloans_availed "+
				     "where entity_id = '"+ entity_id +"' and status_id = 'A'";
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact number of columns and column types.
				modelLoanCrdts.addRow(db.getResult()[x]);
			}
			scrollLoanCreditsList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLoanCreditsList.getRowCount())));
			tblLoanCreditsList.packAll();
		}
	}
	//CHECKS TO SEE IF THE REQUIRES FIELD HAS VALUE
	public boolean toSave() {
		if (txtCreditor.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblCreditor.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (maturityDate.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblMaturityDate.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean isLoanCreditExisting(String entity_id, Integer rec_id){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_creditloans_availed where entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}
	
	public void newLoanCredits(){//NEW LOAN CREADITS
		try{
			tblLoanCreditsList.clearSelection();
		} catch (ArrayIndexOutOfBoundsException e) {}
		ci.setComponentsEditable(pnlNorth, true);
		maturityDate.setEnabled(true);
		cmbStatus.setEnabled(true);
		tblLoanCreditsList.setEnabled(false);
		rowHeaderRef_LoanCredits.setEnabled(false);
	}
	
	public void editLoanCredits(){//EDITING FOR THE LOAN CREDITS
		if(tblLoanCreditsList.getSelectedRows().length ==1){
			ci.setComponentsEditable(pnlNorth, true);
			cmbStatus.setEnabled(true);
			maturityDate.setEnabled(true);
			tblLoanCreditsList.setEnabled(false);
			rowHeaderRef_LoanCredits.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollLoanCreditsList, "Please select only one row to edit");
			tblLoanCreditsList.clearSelection();
		}
	}

	public boolean saveLoanCredits(String entity_id){//SAVING AND UPDATING FOR THE LOAN CREDITS
		pgUpdate db = new pgUpdate();

		String creditor_addr = txtCreditor.getText().trim().replace("'", "''");
		String security = txtSecurity.getText().trim().replace("'", "''");
		String loan_type = txtLoanType.getText().trim().replace("'", "''");
		Date maturity_date = maturityDate.getDate();
		BigDecimal loan_amt = (BigDecimal) txtLoanAmt.getValued();
		BigDecimal monthly_amort = (BigDecimal) txtMontlyAmort.getValued();
		BigDecimal balance = (BigDecimal) txtBalance.getValued();
		String loan_credits_status = (String) cmbStatus.getSelectedItem();
		
		//UPDATING
		if(tblLoanCreditsList.getSelectedRows().length ==1){
			int row = tblLoanCreditsList.getSelectedRow();
			Integer rec_id = (Integer) modelLoanCrdts.getValueAt(row, 0);
			if(isLoanCreditExisting(entity_id, rec_id)){
				String sql = "UPDATE rf_creditloans_availed SET creditor_addr='"+creditor_addr+"', security='"+security+"', loan_type='"+loan_type+"', \n" + 
							 "maturity_date= '"+maturity_date+"', loan_amount= "+loan_amt+", monthly_amort= "+monthly_amort+", balance= "+balance+", \n"+
							 "status_id= (case when '"+loan_credits_status+"' = 'Active' then 'A' else 'I' end) \n" +  
							 "WHERE entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
				db.executeUpdate(sql, true);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Loan Credits has been Updated");
			}
		}else{//SAVING
			String sql = "INSERT INTO rf_creditloans_availed(entity_id, creditor_addr, security, loan_type, maturity_date, \n" + 
					 	 "loan_amount, monthly_amort, balance, status_id, rowguid)\n" + 
					 	 "VALUES ('"+ entity_id +"', '"+ creditor_addr +"', '"+ security +"', '"+ loan_type +"', '"+ maturity_date +"', \n" + 
					 	 "coalesce("+ loan_amt +", 0.00), coalesce("+ monthly_amort +", 0.00), coalesce("+ balance +", 0.00), (case when '"+ loan_credits_status +"' = 'Active' then 'A' else 'I' end), '');";
			db.executeUpdate(sql, true);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Loan Credits has been Saved");
		}
		db.commit();
		
		ci.setComponentsEditable(pnlNorth, false);
		cmbStatus.setEnabled(false);
		maturityDate.setEnabled(false);
		tblLoanCreditsList.clearSelection();
		tblLoanCreditsList.setEnabled(true);
		rowHeaderRef_LoanCredits.setEnabled(true);
		return true;
	}
	
	public void cancelLoanCredits(){//CANCELATION FOR THE LOAN CREDITS
		ci.setComponentsEditable(pnlNorth, false);
		cmbStatus.setEnabled(false);
		maturityDate.setEnabled(false);
		clearFields();
		tblLoanCreditsList.clearSelection();
		tblLoanCreditsList.setEnabled(true);
		rowHeaderRef_LoanCredits.setEnabled(true);
	}
	
	public void clearFields(){//CLEARS THE FIELDS FOR THIS PANEL
		txtCreditor.setText("");
		txtSecurity.setText("");
		txtLoanType.setText("");
		txtLoanAmt.setText("0.00");
		txtMontlyAmort.setText("0.00");
		maturityDate.setDate(null);
		txtBalance.setText("0.00");
		tblLoanCreditsList.clearSelection();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


}
