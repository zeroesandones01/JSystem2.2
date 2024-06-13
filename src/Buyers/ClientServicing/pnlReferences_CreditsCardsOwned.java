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
import tablemodel.modelCreditsCardsOwned;
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

public class pnlReferences_CreditsCardsOwned extends JPanel implements _GUI,
ActionListener {

	private static final long serialVersionUID = 7029483007101100456L;

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthUpper;
	private JPanel pnlNorthLower;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthCenter;

	private JPanel pnlSouth;
	private JPanel pnlExpiry;
	private JPanel pnlExpiryWest;
	private JPanel pnlExpiryCenter;

	private JPanel pnlCardCompany;

	private JPanel pnlCardCompanyWest;
	private JLabel lblCardComp;
	private JLabel lblBankIssuer;
	private JLabel lblExpiryDate;
	private JLabel lblBalance;
	private JLabel lblStatus;

	private JPanel pnlCardCompanyCenter;
	private JTextField txtCardCompany;
	private JTextField txtBankIssuer;
	private _JDateChooser expiryDate;
	private _JXFormattedTextField txtBalance;
	private JComboBox cmbStatus;

	private JPanel pnlCardNum;

	private JPanel pnlCardNumWest;
	private JLabel lblCardNum;
	private JLabel lblCreditLimit;

	private JPanel pnlCardNumCenter;
	private JTextField txtCardNum;
	private _JXFormattedTextField txtCreditLimit;

	private JScrollPane scrollCardsOwnedList;
	private modelCreditsCardsOwned modelCardsOwned;
	private _JTableMain tblCardsOwnedList;
	private JList rowHeaderRef_CardsOwned;

	private _JLookup lookupBank;

	private ClientInformation ci;

	public pnlReferences_CreditsCardsOwned(ClientInformation ci) {
		this.ci=ci;
		initGUI();
	}

	public pnlReferences_CreditsCardsOwned(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlReferences_CreditsCardsOwned(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlReferences_CreditsCardsOwned(LayoutManager layout,
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
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.NORTH);
			pnlMain.setPreferredSize(new Dimension(648, 141));
			//pnlNorth.setPreferredSize(new Dimension(744, 162));
			//Start of Edit
			{
				pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(648, 55));
				{
					pnlCardCompany = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlCardCompany);
					{
						pnlCardCompanyWest = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlCardCompany.add(pnlCardCompanyWest, BorderLayout.WEST);
						{
							lblCardComp = new JLabel("*Card Company");
							pnlCardCompanyWest.add(lblCardComp);
						}
						{
							lblBankIssuer = new JLabel("*Bank Issuer");
							pnlCardCompanyWest.add(lblBankIssuer);
						}
					}
					{
						pnlCardCompanyCenter = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlCardCompany.add(pnlCardCompanyCenter, BorderLayout.CENTER);
						{
							txtCardCompany = new JTextField();
							pnlCardCompanyCenter.add(txtCardCompany);
						}
						{
							JPanel pnlBankIssuer = new JPanel(new BorderLayout(5, 5));
							pnlCardCompanyCenter.add(pnlBankIssuer, BorderLayout.WEST);
							{
								lookupBank = new _JLookup(null, "Bank Issuer", 0);
								pnlBankIssuer.add(lookupBank, BorderLayout.WEST);
								lookupBank.setLookupSQL(sqlBankIssuer());
								lookupBank.setFilterName(true);
								lookupBank.setPreferredSize(new Dimension(100, 0));
								lookupBank.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String bank_name = (String) data[1];

											txtBankIssuer.setText(bank_name);
										}
									}
								});
							}
							{
								txtBankIssuer = new JTextField();
								pnlBankIssuer.add(txtBankIssuer);
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1, 2));//TODO CHECK LAYOUT
				pnlMain.add(pnlSouth);
				pnlSouth.setPreferredSize(new Dimension(648, 90));
				{
					pnlExpiry = new JPanel(new BorderLayout(5, 5));
					pnlSouth.add(pnlExpiry, BorderLayout.WEST);
					pnlExpiry.setPreferredSize(new Dimension(200, 80));
					{
						pnlExpiryWest = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlExpiry.add(pnlExpiryWest, BorderLayout.WEST);
						pnlExpiryWest.setPreferredSize(new Dimension(95, 81));
						{
							lblExpiryDate = new JLabel("*Expiry Date");
							pnlExpiryWest.add(lblExpiryDate);
						}
						{
							lblBalance = new JLabel("Balance");
							pnlExpiryWest.add(lblBalance);
						}
						{
							lblStatus = new JLabel("Status");
							pnlExpiryWest.add(lblStatus);
						}
					}
					{
						pnlExpiryCenter = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlExpiry.add(pnlExpiryCenter);
						{
							JPanel pnlExpiryDate = new JPanel(new BorderLayout(3, 3));
							pnlExpiryCenter.add(pnlExpiryDate);
							{
								expiryDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlExpiryDate.add(expiryDate, BorderLayout.WEST);
								expiryDate.setPreferredSize(new Dimension(120, 0));
							}
						}
						{
							JPanel pnlBalance = new JPanel(new BorderLayout(3, 3));
							pnlExpiryCenter.add(pnlBalance);
							{
								txtBalance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlBalance.add(txtBalance, BorderLayout.WEST);
								txtBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBalance.setText("0.00");
								txtBalance.setPreferredSize(new Dimension(120, 0));
							}
						}
						{
							JPanel pnlCardsOwnedStatus = new JPanel(new BorderLayout(3, 3));
							pnlExpiryCenter.add(pnlCardsOwnedStatus);
							{
								cmbStatus = new JComboBox(new String[] { "Active", "Inactive" });
								pnlCardsOwnedStatus.add(cmbStatus, BorderLayout.WEST);
								cmbStatus.setPreferredSize(new Dimension(120, 0));
							}
						}
					}
				}
				{
					pnlCardNum = new JPanel(new BorderLayout(5, 5));
					pnlSouth.add(pnlCardNum, BorderLayout.CENTER);
					pnlCardNum.setPreferredSize(new Dimension(336, 162));
					{
						pnlCardNumWest = new JPanel(new GridLayout(3, 1, 5, 5));
						pnlCardNum.add(pnlCardNumWest, BorderLayout.WEST);
						//pnlCardNumWest.setPreferredSize(new Dimension(83, 162));
						{
							lblCardNum = new JLabel("*Card No.");
							pnlCardNumWest.add(lblCardNum);
						}
						{
							lblCreditLimit = new JLabel();
							pnlCardNumWest.add(lblCreditLimit);
							lblCreditLimit.setText("Credit Limit");
						}
					}
					{
						pnlCardNumCenter = new JPanel(new GridLayout(3, 1, 5, 5));
						pnlCardNum.add(pnlCardNumCenter, BorderLayout.CENTER);
						{
							JPanel pnltxtCardNum = new JPanel(new BorderLayout(3, 3));
							pnlCardNumCenter.add(pnltxtCardNum);
							{
								txtCardNum = new JTextField();
								pnltxtCardNum.add(txtCardNum, BorderLayout.WEST);
								txtCardNum.setPreferredSize(new Dimension(120, 0));
							}
						}
						{
							JPanel pnlCredit = new JPanel(new BorderLayout(3, 3));
							pnlCardNumCenter.add(pnlCredit);
							{
								txtCreditLimit = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlCredit.add(txtCreditLimit, BorderLayout.WEST);
								txtCreditLimit.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtCreditLimit.setText("0.00");
								txtCreditLimit.setPreferredSize(new Dimension(120, 0));
							}
						}
					}
				}
				//SpringUtilities.makeCompactGrid(pnlSouth, 1, 2, 3, 3, 3, 3, false);
			}
			//End of Edit
		}
		{
			scrollCardsOwnedList = new JScrollPane();
			this.add(scrollCardsOwnedList, BorderLayout.CENTER);
			{
				modelCardsOwned = new modelCreditsCardsOwned();
				tblCardsOwnedList = new _JTableMain(modelCardsOwned);
				scrollCardsOwnedList.setViewportView(tblCardsOwnedList);
				tblCardsOwnedList.hideColumns("Rec. ID","Bank ID");
				tblCardsOwnedList.setSortable(false);
				tblCardsOwnedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				modelCardsOwned.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						//_OrderOfPayment.totalPayments(modelConnList, modelPaymentListTotal);

						((DefaultListModel)rowHeaderRef_CardsOwned.getModel()).addElement(modelCardsOwned.getRowCount());
						scrollCardsOwnedList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCardsOwnedList.getRowCount())));


						if(modelCardsOwned.getRowCount() == 0){
							rowHeaderRef_CardsOwned.setModel(new DefaultListModel());
						}
					}
				});
				tblCardsOwnedList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if(!arg0.getValueIsAdjusting()){
							if(tblCardsOwnedList.getSelectedRows().length ==1){
								int row = tblCardsOwnedList.getSelectedRow();
								
								String card_company = (String) modelCardsOwned.getValueAt(row, 1);
								String bank_id = (String) modelCardsOwned.getValueAt(row, 2);
								String bank_name = (String) modelCardsOwned.getValueAt(row, 3);
								BigDecimal credit_limit = (BigDecimal) modelCardsOwned.getValueAt(row, 4);
								String card_no = (String) modelCardsOwned.getValueAt(row, 5);
								Date expiry_date = (Date) modelCardsOwned.getValueAt(row, 6);
								BigDecimal ave_balance = (BigDecimal) modelCardsOwned.getValueAt(row, 7);
								String card_owned_stat = (String) modelCardsOwned.getValueAt(row, 8);
								
								txtCardCompany.setText(card_company);
								lookupBank.setValue(bank_id);
								txtBankIssuer.setText(bank_name);
								expiryDate.setDate(expiry_date);
								txtCardNum.setText(card_no);
								txtBalance.setValue(ave_balance);
								txtCreditLimit.setValue(credit_limit);
								
								if (card_owned_stat.equals("A")){
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
				rowHeaderRef_CardsOwned = tblCardsOwnedList.getRowHeader();
				rowHeaderRef_CardsOwned.setModel(new DefaultListModel());
				scrollCardsOwnedList.setRowHeaderView(rowHeaderRef_CardsOwned);
				scrollCardsOwnedList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtCardCompany, lookupBank, expiryDate, txtBalance, cmbStatus, txtCardNum, txtCreditLimit));
		ci.setComponentsEditable(pnlMain, false);
		setComponentsEnabled(false, false, false);
	}//END OF INIT GUI

	public void setComponentsEnabled(Boolean bank, Boolean expiry, Boolean status){
		txtBankIssuer.setEditable(bank);
		expiryDate.setEnabled(expiry);
		cmbStatus.setEnabled(status);
	}

	public void displayCreditCardsOwned(String entity_id){//DISPLAYS THE CREDIT CARDS OWNED FOR THE SELECTED ENTITY
		modelCardsOwned.clear();

		String sql = "SELECT a.rec_id, a.card_comp, a.bank_issuer, b.bank_name , a.credit_limit, a.card_no, \n" + 
					 "a.expiry_date, a.ave_balance, a.status_id\n" + 
					 "FROM rf_credit_cards_owned a\n" + 
					 "left join mf_bank b on b.bank_id = a.bank_issuer\n" + 
					 "where a.entity_id = '"+ entity_id +"' and a.status_id = 'A'";

		FncSystem.out("Display Credit Cards Owned", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact number of columns and column types.
				modelCardsOwned.addRow(db.getResult()[x]);
			}
			scrollCardsOwnedList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCardsOwnedList.getRowCount())));
			tblCardsOwnedList.packAll();
		}
	}

	private String sqlBankIssuer(){//SQL FOR THE BANK ISSUER
		return "Select trim(bank_id) as \"ID\", trim(bank_name) as \"Bank Name\" from mf_bank where status_id = 'A' order by bank_name";
	}

	public boolean toSave(){//CHECKING OF THE REQUIRED FIELDS BEFORE SAVING
		if (txtCardCompany.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblCardComp.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupBank.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblBankIssuer.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (expiryDate.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblExpiryDate.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtCardNum.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblCardNum.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	//CHECKS WHETHER THE CREDIT CARDS IS ALREADY EXISTING
	public boolean isCreditCardExisting(String entity_id, Integer rec_id){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_credit_cards_owned where entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	public void newCreditCardsOwned(){//NEW CREDIT CARD
		clearFields();
		ci.setComponentsEditable(pnlMain, true);
		setComponentsEnabled(false, true, true);
		try{
			tblCardsOwnedList.clearSelection();
		} catch (ArrayIndexOutOfBoundsException e) {}
		tblCardsOwnedList.setEnabled(false);
		rowHeaderRef_CardsOwned.setEnabled(false);
	}
	
	public void editCreditCardsOwned(){//EDITING FOR THE CREDIT CARDS OWNED
		if(tblCardsOwnedList.getSelectedRows().length ==1){
			ci.setComponentsEditable(pnlMain, true);
			setComponentsEnabled(false, true, true);
			tblCardsOwnedList.setEnabled(false);
			rowHeaderRef_CardsOwned.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollCardsOwnedList, "Please select only one row to edit");
			tblCardsOwnedList.clearSelection();
		}
	}

	public boolean saveCreditCardsOwned(String entity_id){//SAVING AND UPDATING FOR THE CREDIT CARDS OWNED
		pgUpdate db = new pgUpdate();
		String card_comp = txtCardCompany.getText().trim().replace("'", "''");
		String bank_issuer = lookupBank.getValue();
		BigDecimal credit_limit = (BigDecimal) txtCreditLimit.getValued();
		String card_no = txtCardNum.getText();
		Date expiry_date = expiryDate.getDate();
		BigDecimal ave_balance = (BigDecimal) txtBalance.getValued();
		String credit_cards_owned_status = (String) cmbStatus.getSelectedItem();
		
		//UPDATING
		if(tblCardsOwnedList.getSelectedRows().length ==1){
			int row = tblCardsOwnedList.getSelectedRow();
			Integer rec_id = (Integer) modelCardsOwned.getValueAt(row, 0);
			if(isCreditCardExisting(entity_id, rec_id)){
				String sql = "UPDATE rf_credit_cards_owned SET card_comp='"+card_comp+"', bank_issuer='"+bank_issuer+"', credit_limit="+credit_limit+", \n" + 
							 "card_no='"+card_no+"', expiry_date='"+expiry_date+"', ave_balance="+ave_balance+", \n"+
							 "status_id= (case when '"+credit_cards_owned_status+"' = 'Active' then 'A' else 'I' end) \n" + 
							 "WHERE entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
				db.executeUpdate(sql, true);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Credit Card has been Updated");
			}
		}else{//SAVING
			String sql = "INSERT INTO rf_credit_cards_owned(entity_id, card_comp, bank_issuer, credit_limit, card_no, \n" + 
						 "expiry_date, ave_balance, status_id, rowguid)\n" + 
						 "VALUES ('"+ entity_id +"','"+ card_comp +"' , '"+ bank_issuer +"', "+ credit_limit +", '"+ card_no +"', \n" + 
						 "'"+ expiry_date +"', "+ ave_balance +", (case when '"+ credit_cards_owned_status +"' = 'Active' then 'A' else 'I' end), '')";
			db.executeUpdate(sql, true);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Credit Card has been Saved");
		}
		db.commit();
		
		ci.setComponentsEditable(pnlMain, false);
		setComponentsEnabled(false, false, false);
		tblCardsOwnedList.clearSelection();
		tblCardsOwnedList.setEnabled(true);
		rowHeaderRef_CardsOwned.setEnabled(true);
		return true;
	}

	public void cancelCardsOwned(){//CANCELATION OF CARDS OWNED
		clearFields();
		ci.setComponentsEditable(pnlMain, false);
		setComponentsEnabled(false, false, false);
		tblCardsOwnedList.clearSelection();
		tblCardsOwnedList.setEnabled(true);
		rowHeaderRef_CardsOwned.setEnabled(true);
	}

	public void clearFields(){//CLEAR THE FIELD IN THIS PANEL
		txtCardCompany.setText("");
		lookupBank.setValue(null);
		txtBankIssuer.setText("");
		expiryDate.setDate(null);
		txtBalance.setText("0.00");
		txtCardNum.setText("");
		txtCreditLimit.setText("0.00");
		tblCardsOwnedList.clearSelection();
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
