package Renderer;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import Database.pgSelect;
import tablemodel.modelDM_AllDocuments;
import tablemodel.modelDM_RRDocuments;
import Dialogs.dlgDM_DateofApproval;
import Dialogs.dlgDM_InsuranceCompany;
import Dialogs.dlgDM_OSBalance;
import Dialogs.dlgDM_PayslipDetail;
import Dialogs.dlgDM_SPA;
import Dialogs.dlgDM_TCT_Details;
import Dialogs.dlgDM_TINNumber;
import Dialogs.dlgDM_VenueLocation;
import Dialogs.dlgDM_Year;
import Dialogs.dlgDM_ValidUntil;
import Dialogs.dlgDM_YearMonth;
import Dialogs.dlgDM_YearMonthValidity;
import Dialogs.dlg_DesiredAmount;
import Functions.FncGlobal;
import Functions.FncSystem;

/**
 * 
 * @author Alvin Gonzales (2015-02-18)
 *
 */
public class ButtonCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3655539289749722716L;

	JButton btnDetails;

	Window owner;

	String doc_desc;
	String doc_id;
	Integer column;

	DefaultTableModel model;
	//modelDM_AllDocuments model;
	modelDM_RRDocuments modelDocsIn;
	int row;

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	DecimalFormat df = new DecimalFormat("#,##0.00");

	/*public ButtonCell() {
		btnDetails = new JButton("Details");

	}*/

	public ButtonCell(Window owner, Integer column) {
		this.owner = owner;
		this.column = column;
		System.out.println("Dumaan dito sa GUI ng Details");
		
		

		btnDetails = new JButton("Details");
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Dumaan dito sa Action ng Details");

				//Year
				if(getYear().contains(doc_id)){
					dlgDM_Year dm_year = new dlgDM_Year(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					dm_year.setLocationRelativeTo(null);
					dm_year.setVisible(true);

					Integer year = dm_year.getYear();
					if(year != null){

						model.setValueAt(String.format("Year: %s;", year), row, ButtonCell.this.column);
					}
				}

				//Year and Month
				/*if(getYearMonth().contains(doc_id)){
					System.out.println("Dumaan dito sa Year and Month");
					dlgDM_YearMonth dm_year_month = new dlgDM_YearMonth(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					dm_year_month.setLocationRelativeTo(null);
					dm_year_month.setVisible(true);

					Integer year = dm_year_month.getYear();
					Integer month = dm_year_month.getMonth();
					if(year != null && month != null){
						System.out.printf("Display Value of owner: %s%n", ButtonCell.this.owner);

							model.setValueAt(String.format("Year: %s; Month: %s;", year, month), row, ButtonCell.this.column);

					}
				}*/

				//Valid Until
				/*if(getValidUntil().contains(doc_id)){
					dlgDM_ValidUntil valid_until = new dlgDM_ValidUntil(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					valid_until.setLocationRelativeTo(null);
					valid_until.setVisible(true);

					Date validUntil = valid_until.getValidUntil();
					if(validUntil != null){
						model.setValueAt(String.format("Valid Until: %s;", sdf.format(validUntil)), row, ButtonCell.this.column);
					}
				}*/

				//Date of Approval
				if(getDateOfApproval().contains(doc_id)){
					dlgDM_DateofApproval date_of_approval = new dlgDM_DateofApproval(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")), doc_id);
					date_of_approval.setLocationRelativeTo(null);
					date_of_approval.setVisible(true);

					Date dateOfAprroval = date_of_approval.getDateOfAprroval();
					BigDecimal loan_entitlement = date_of_approval.getLoan_entitlement();
					//Date validUntil = date_of_approval.getValidUntil();
					Integer year = date_of_approval.getYear();
					Integer month = date_of_approval.getMonth();

					if(dateOfAprroval != null && loan_entitlement != null){
						//model.setValueAt(String.format("Date of Approval: %s; Loan Entitlement: %s; Valid Until: %s", sdf.format(dateOfAprroval), loan_entitlement, sdf.format(validUntil)), row, ButtonCell.this.column);
						model.setValueAt(String.format("Date of Approval: %s; Loan Entitlement: %s; Year: %s; Month: %s", sdf.format(dateOfAprroval), loan_entitlement, year, month), row, ButtonCell.this.column);
					}
				}

				//Outstanding Balance
				if(getOutstandingBalance().contains(doc_id)){
					dlgDM_OSBalance os_balance = new dlgDM_OSBalance(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					os_balance.setLocationRelativeTo(null);
					os_balance.setVisible(true);

					Date dateLastPmt = os_balance.getDateLastPmt();
					BigDecimal outstanding_balance = os_balance.getOutstanding_Balance();

					if(dateLastPmt != null && outstanding_balance != null){
						model.setValueAt(String.format("Date of Last Pmt: %s; Outstanding Balance: %s", sdf.format(dateLastPmt), df.format(outstanding_balance.doubleValue())), row, ButtonCell.this.column);
					}
				}

				//TIN Number
				if(getTINNumber().contains(doc_id)){
					dlgDM_TINNumber tin_number = new dlgDM_TINNumber(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					tin_number.setLocationRelativeTo(null);
					tin_number.setVisible(true);

					String tinNumber = tin_number.getTINnumber();

					if(tinNumber != null){
						model.setValueAt(String.format("TIN Number: %s;", tinNumber), row, ButtonCell.this.column);
					}
				}

				//Insurance Company
				if(getInsuranceCompany().contains(doc_id)){
					dlgDM_InsuranceCompany dm_insurance_company = new dlgDM_InsuranceCompany(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					dm_insurance_company.setLocationRelativeTo(null);
					dm_insurance_company.setVisible(true);

					String insuranceID = dm_insurance_company.getInsuranceID();
					String insuranceName = dm_insurance_company.getInsuranceName();


					if(insuranceID != null){
						//System.out.printf("TIN Number: %s%n", tinNumber);
						model.setValueAt(String.format("Insurance ID: %s; Insurance Name: %s;", insuranceID, insuranceName), row, ButtonCell.this.column);
					}
				}

				//Venue Location
				if(getVenueLocation().contains(doc_id)){
					dlgDM_VenueLocation venue_location = new dlgDM_VenueLocation(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					venue_location.setLocationRelativeTo(null);
					venue_location.setVisible(true);

					String venue_id = venue_location.getVenueID();
					String venue_name = venue_location.getVenueName();
					Date venue_date = venue_location.getVenueDate();

					if(venue_id != null && venue_name != null && venue_date != null){
						model.setValueAt(String.format("Venue ID: %s; Venue Name: %s; Venue Date: %s;", venue_id, venue_name, sdf.format(venue_date)), row, ButtonCell.this.column);
					}
				}

				//SPECIAL POWER OF ATTORNEY
				if(getAttorneyInFact().contains(doc_id)){
					dlgDM_SPA dm_spa = new dlgDM_SPA(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					dm_spa.setLocationRelativeTo(null);
					dm_spa.setVisible(true);

					String attorney_in_fact = dm_spa.getAttorneyInFact();
					Date spa_notary_date = dm_spa.getSPANotaryDate();

					if(attorney_in_fact != null && spa_notary_date != null){
						model.setValueAt(String.format("Attorney In-Fact: %s; SPA Notary Date: %s;", attorney_in_fact, sdf.format(spa_notary_date)), row, ButtonCell.this.column);
					}
				}

				//PAYSLIP
				if(getPayslip().contains(doc_id)){
					dlgDM_PayslipDetail dm_payslip = new dlgDM_PayslipDetail(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					dm_payslip.setLocationRelativeTo(null);
					dm_payslip.setVisible(true);

					Integer year = dm_payslip.getYear();
					Integer month = dm_payslip.getMonth();
					BigDecimal gross_pay = dm_payslip.getGrossPay();
					BigDecimal deductions = dm_payslip.getDeduction();
					BigDecimal net_dispsable_income = dm_payslip.getNetDisposableIncome();
					BigDecimal projected_hdmf = dm_payslip.getProjectdHDMF();

					if(year != null && month != null && gross_pay != null && deductions != null && net_dispsable_income != null && projected_hdmf != null){
						model.setValueAt(String.format("Year: %s; Month: %s; Gross Pay: %s; Deductions: %s; Net Disposable Income: %s; Projected HDMF: %s;", year, month, df.format(gross_pay.doubleValue()), df.format(deductions.doubleValue()), df.format(net_dispsable_income.doubleValue()), df.format(projected_hdmf.doubleValue())), row, ButtonCell.this.column);
					}
				}

				//TCT Details
				if(getTCT().contains(doc_id)){
					dlgDM_TCT_Details dm_tct = new dlgDM_TCT_Details(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					dm_tct.setLocationRelativeTo(null);
					dm_tct.setVisible(true);

					System.out.println("Dumaan dito sa TCT Details");
				}

				if(getYearMonthValidity().contains(doc_id)){
					dlgDM_YearMonthValidity dm_year_month_validity = new dlgDM_YearMonthValidity(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")), doc_id);
					dm_year_month_validity.setLocationRelativeTo(null);
					dm_year_month_validity.setVisible(true);

					Integer year = dm_year_month_validity.getYear();
					Integer month = dm_year_month_validity.getMonth();
					Date validUntil = dm_year_month_validity.getValidUntil();

					if(year != null && month != null){
						model.setValueAt(String.format("Year: %s; Month: %s; Valid Until:%s", year, month, sdf.format(validUntil)), row, ButtonCell.this.column);
					}

					/*if(year != null && month != null){
						model.setValueAt(String.format("Year: %s; Month: %s; Valid Until:%s", year, month, sdf.format(getValidityDate(year.toString(), month.toString(), doc_id))), row, ButtonCell.this.column);
					}*/

					/*if(validUntil != null){
						model.setValueAt(String.format("Valid Until: %s;", sdf.format(validUntil)), row, ButtonCell.this.column + 1);
						System.out.println("Dumaan dito sa valid until set value");
					}*/
				}

				if(getValidUntilFromMonths().contains(doc_id)){
					dlgDM_YearMonth dm_year_month = new dlgDM_YearMonth(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					dm_year_month.setLocationRelativeTo(null);
					dm_year_month.setVisible(true);

					Integer year = dm_year_month.getYear();
					Integer month = dm_year_month.getMonth();
					Date validUntil = dm_year_month.getValidUntil();

					if(year != null && month != null){
						model.setValueAt(String.format("Year: %s; Month: %s; Valid Until:%s", year, month, sdf.format(validUntil)), row, ButtonCell.this.column);
					}
				}
				
				if(doc_id.equals("225")) {
					System.out.println("Dumaan dito");
				}

				if(getDesiredAmount().contains(doc_id)) {
					
					System.out.println("Desired Loan Amt");
					dlg_DesiredAmount desiredAmount = new dlg_DesiredAmount(FncGlobal.homeMDI, String.format("%s", doc_desc.replace("*", "")));
					desiredAmount.setLocationRelativeTo(null);
					desiredAmount.setVisible(true);
					
					BigDecimal desired_loan_amt = desiredAmount.getDesired_loan_amt();
					
					if(desired_loan_amt != null) {
						model.setValueAt(String.format("Desired Loan Amt: %s;", desired_loan_amt), row, ButtonCell.this.column);
					}
				}
			}
		});
	}

	private void updateData(JTable table, int row) {

		model = (DefaultTableModel) table.getModel();

		this.row = row;
		doc_id = (String) model.getValueAt(row, 2);
		doc_desc = (String) model.getValueAt(row, 3);

		/*if(modelDocsIn != null){
			modelDocsIn = (modelDM_RRDocuments) table.getModel();

			this.row = row;

			doc_id = (String) modelDocsIn.getValueAt(row, 2);
			doc_desc = (String) modelDocsIn.getValueAt(row, 3);
		}*/

		//COMMENTED ON 2017-03-28 BY LESTER
		/*boolean hasRequired = doc_desc.contains("*") && getAllRequired().contains(doc_id);

		if(hasRequired){
			btnDetails.setText("Details");
		}else{
			btnDetails.setText("");
		}*/

		boolean withDetails = getAllRequired().contains(doc_id);

		if(withDetails){
			btnDetails.setText("Details");
		}else{
			btnDetails.setText("");
		}

		//System.out.println("Dumaan dito sa Required ng GUI");

		btnDetails.setOpaque(withDetails);
		btnDetails.setContentAreaFilled(withDetails);
		btnDetails.setBorderPainted(withDetails);

		btnDetails.setFocusPainted(false);
		btnDetails.setEnabled(withDetails);
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		updateData(table, row);

		return btnDetails;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		updateData(table, row);

		return btnDetails;
	}

	public ArrayList<String> getValidUntilFromMonths() {
		ArrayList<String> listValidUntil = new ArrayList<String>();
		listValidUntil.add("42"); //EMPLOYMENT CONTRACT
		listValidUntil.add("213"); //WORK PERMIT
		return listValidUntil;
	}

	public ArrayList<String> getDateOfApproval() {
		ArrayList<String> listDateOfApproval = new ArrayList<String>();
		listDateOfApproval.add("47"); //MEMBERSHIP STATUS VERIFICATION SLIP
		return listDateOfApproval;
	}

	public ArrayList<String> getOutstandingBalance(){
		ArrayList<String> listOutstandingBalance = new ArrayList<String>();
		listOutstandingBalance.add("234");
		return listOutstandingBalance;
	}

	public ArrayList<String> getTINNumber() {
		ArrayList<String> listTINNumber = new ArrayList<String>();
		listTINNumber.add("28"); //TAX IDENTIFICATION NO.
		return listTINNumber;
	}

	public ArrayList<String> getYear() {
		ArrayList<String> listYear = new ArrayList<String>();
		listYear.add("120"); //BALANCE SHEET/INCOME STATEMENT
		//listYear.add("81"); //BUSINESS PERMIT
		listYear.add("40"); //INCOME TAX RETURN
		return listYear;
	}

	public ArrayList<String> getYearMonth() {
		ArrayList<String> listYearMonth = new ArrayList<String>();
		listYearMonth.add("43"); //AUDITED FINANCIAL STATEMENTS
		listYearMonth.add("122"); //PROOF OF INCOME/REMITTANCE
		listYearMonth.add("81"); //BUSINESS PERMIT
		listYearMonth.add("82"); //BUSINESS REGISTRATION
		listYearMonth.add("41"); //CERT. OF EMPLOYMENT AND COMPENSATION
		listYearMonth.add("99"); //ESAV
		listYearMonth.add("52"); //PAGIBIG CONTRIBUTION OR
		listYearMonth.add("51"); //PAYSLIP
		listYearMonth.add("15"); //PROOF OF BILLING
		//listYearMonth.add("99"); //ESAV
		listYearMonth.add("252"); //SCANNED CEC WITH EMAIL THREAD 
		listYearMonth.add("253"); //SCANNED CEC 
		listYearMonth.add("254"); //SCANNED POB 
		listYearMonth.add("255"); //SCANNED PAYSLIP 
		listYearMonth.add("256"); //SCANNED CEC (OCW) 
		return listYearMonth;
	}

	public ArrayList<String> getYearMonthValidity(){
		ArrayList<String> listYearMonthValidity = new ArrayList<String>();
		listYearMonthValidity.add("43"); //AUDITED FINANCIAL STATEMENTS
		listYearMonthValidity.add("122"); //PROOF OF INCOME/REMITTANCE
		listYearMonthValidity.add("81"); //BUSINESS PERMIT
		listYearMonthValidity.add("82"); //BUSINESS REGISTRATION
		listYearMonthValidity.add("41"); //CERT. OF EMPLOYMENT AND COMPENSATION
		listYearMonthValidity.add("99"); //ESAV
		listYearMonthValidity.add("52"); //HDMF OR (OCW/SE)
		listYearMonthValidity.add("51"); //PAYSLIP
		listYearMonthValidity.add("15"); //PROOF OF BILLING
		listYearMonthValidity.add("206"); //SUBSIDIARY LEDGER
		listYearMonthValidity.add("237"); //HDMF OR w/ M1
		listYearMonthValidity.add("238"); //HDMF OR w/ P2-4
		/*listYearMonthValidity.add("42"); //EMPLOYMENT CONTRACT
		listYearMonthValidity.add("213");*/ //WORK PERMIT
		listYearMonthValidity.add("252"); //SCANNED CEC WITH EMAIL THREAD 
		listYearMonthValidity.add("253"); //SCANNED CEC 
		listYearMonthValidity.add("254"); //SCANNED POB 
		listYearMonthValidity.add("255"); //SCANNED PAYSLIP 
		listYearMonthValidity.add("256"); //SCANNED CEC (OCW) 
		return listYearMonthValidity;
	}

	public ArrayList<String> getInsuranceCompany() {
		ArrayList<String> listYearMonth = new ArrayList<String>();
		listYearMonth.add("18"); //MORTGAGE REDEMPTION INSURANCE
		return listYearMonth;
	}

	public ArrayList<String> getVenueLocation() {
		ArrayList<String> listYearMonth = new ArrayList<String>();
		listYearMonth.add("79"); //MSVS ACKNOWLEDGMENT RECEIPT
		return listYearMonth;
	}

	public ArrayList<String> getAttorneyInFact() {
		ArrayList<String> listYearMonth = new ArrayList<String>();
		listYearMonth.add("19"); //SPECIAL POWER OF ATTORNEY
		return listYearMonth;
	}

	public ArrayList<String> getPayslip() {
		ArrayList<String> listYearMonth = new ArrayList<String>();
		//listYearMonth.add("51"); //PAYSLIP
		return listYearMonth;
	}

	public ArrayList<String> getTCT(){
		ArrayList<String> listTCT_Details = new ArrayList<String>();
		listTCT_Details.add("65");
		return listTCT_Details;
	}

	public ArrayList<String> getMSVS(){
		ArrayList<String> listMSVS = new ArrayList<String>();
		listMSVS.add("47");
		return listMSVS;
	}

	public ArrayList<String> getAllRequired() {
		ArrayList<String> listAllRequired = new ArrayList<String>();
		listAllRequired.add("42"); //EMPLOYMENT CONTRACT *
		listAllRequired.add("47"); //MEMBERSHIP STATUS VERIFICATION SLIP *
		listAllRequired.add("28"); //TAX IDENTIFICATION NO. *
		listAllRequired.add("120"); //BALANCE SHEET/INCOME STATEMENT *
		listAllRequired.add("81"); //BUSINESS PERMIT *
		listAllRequired.add("40"); //INCOME TAX RETURN *
		listAllRequired.add("43"); //AUDITED FINANCIAL STATEMENTS *
		listAllRequired.add("122"); //PROOF OF INCOME/REMITTANCE *
		listAllRequired.add("18"); //MORTGAGE REDEMPTION INSURANCE *
		listAllRequired.add("19"); //SPECIAL POWER OF ATTORNEY *
		listAllRequired.add("79"); //MSVS ACKNOWLEDGMENT RECEIPT *
		listAllRequired.add("51"); //PAYSLIP *
		listAllRequired.add("82"); //BUSINESS REGISTRATION *
		listAllRequired.add("41"); //CERT. OF EMPLOYMENT AND COMPENSATION 8
		listAllRequired.add("52"); //PAGIBIG CONTRIBUTION OR *
		listAllRequired.add("99"); //ESAV *
		listAllRequired.add("65"); //TCT - Individual (under buyer's name)
		listAllRequired.add("15"); //PROOF OF BILLING
		listAllRequired.add("213"); //WORK PERMIT
		listAllRequired.add("234"); //STATEMENT OF ACCOUNT (PAGIBIG HOUSING LOAN)
		listAllRequired.add("206"); //SUBSIDIARY LEDGER
		listAllRequired.add("237"); //HDMF OR w/ M1
		listAllRequired.add("238"); //HDMF OR w/ P2-4
		listAllRequired.add("225"); //HOUSING LOAN APPLICATION CO-BORROWER (HQP-HLF-069)
		listAllRequired.add("252"); //SCANNED CEC WITH EMAIL THREAD 
		listAllRequired.add("253"); //SCANNED CEC 
		listAllRequired.add("254"); //SCANNED POB 
		listAllRequired.add("255"); //SCANNED PAYSLIP 
		listAllRequired.add("256"); //SCANNED CEC (OCW) 
		return listAllRequired;
	}

	public ArrayList<String> getDesiredAmount(){
		ArrayList<String> listAmount = new ArrayList<String>();
		listAmount.add("225");
		return listAmount;
	}

	private String getDocValidity(String doc_id){
		String doc_validity = "";

		pgSelect db = new pgSelect();
		String SQL = "SELECT validity FROM mf_documents WHERE doc_id = '"+doc_id+"'";
		db.select(SQL);
		FncSystem.out("Display SQL for DOC ID Validity", SQL);

		if(db.isNotNull()){
			doc_validity = (String) db.getResult()[0][0];
		}

		return doc_validity;
	}

	private Date getValidityDate(String year, String month, String doc_id){
		String date_issued = String.format("%s-%s-%s", year, month, "01");

		Date validity_date = null;

		pgSelect db = new pgSelect();
		String SQL = "SELECT get_doc_validity_date('"+date_issued+"', '"+doc_id+"')";
		db.select(SQL);
		FncSystem.out("Display value of doc validity:", SQL);

		if(db.isNotNull()){
			validity_date = (Date) db.getResult()[0][0];
		}

		return validity_date;
	}

}
