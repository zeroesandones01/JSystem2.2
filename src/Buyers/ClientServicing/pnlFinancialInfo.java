package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdesktop.swingx.JXFormattedTextField;

import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncSystem;
import Functions.SpringUtilities;
import components.JTBorderFactory;
import components._JFormattedTextField;
import components._JXTextField;

/**
 * @author JOHN LESTER FATALLO
 */

public class pnlFinancialInfo extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = 7842954854133782601L;

	private JPanel pnlMainInfo;
	private JLabel lblEntityType;
	private JComboBox cmbEntityType;
	private JLabel lblLoanEntitlement;
	private _JXFormattedTextField txtLoanEntitlement;
	private JLabel lblEntityName;
	private _JXTextField txtEntityName;

	private JPanel pnlAssets;
	private JLabel lblCashOnHand;
	private _JXFormattedTextField txtCashOnHand;
	private JLabel lblStocksBonds;
	private _JXFormattedTextField txtStocksBonds;
	private JLabel lblRealEstate;
	private _JXFormattedTextField txtRealEstate;
	private JLabel lblVehiclesOwned;
	private _JXFormattedTextField txtVehiclesOwned;
	private JLabel lblAssetsOthers;
	private _JXFormattedTextField txtAssetsOthers;
	private JLabel lblTotalAssets;
	//private _JXFormattedTextField txtTotalAssets;
	private _JFormattedTextField txtTotalAssets;

	private JPanel pnlLiabilities;
	private JLabel lblUnsecuredLoans;
	private _JXFormattedTextField txtUnsecuredLoans;
	private JLabel lblLiabilitiesOthers;
	private _JXFormattedTextField txtLiabilitiesOthers;
	private JLabel lblTotalLiabilities;
	//private _JXFormattedTextField txtTotalLiabilities;
	private _JFormattedTextField txtTotalLiabilities;

	private JPanel pnlNetWorth;
	private JLabel lblAssetsLiabilities;
	//private _JXFormattedTextField txtAssetsLiabilities;
	private _JFormattedTextField txtAssetsLiabilities;

	private JPanel pnlIncome;
	private JLabel lblIncomeSeparator1;
	private JLabel lblIncomeSeparator2;
	private JLabel lblBorrower;
	private JLabel lblSpouseOther;
	private JLabel lblSalaries;
	private JLabel lblIncomeSeparator3;
	private _JXFormattedTextField txtBorrowerSal;
	private _JXFormattedTextField txtSpouseSal;
	private JLabel lblAllowances;
	private JLabel lblIncomeSeparator4;
	private _JXFormattedTextField txtBorrowerAllowance;
	private _JXFormattedTextField txtSpouseAllowance;
	private JLabel lblCommisssions;
	private JLabel lblIncomeSeparator5;
	private _JXFormattedTextField txtBorrowerComm;
	private _JXFormattedTextField txtSpouseComm;
	private JLabel lblRentalIncome;
	private JLabel lblIncomeSeparator6;
	private _JXFormattedTextField txtBorrowerRental;
	private _JXFormattedTextField txtSpouseRental;
	private JLabel lblIncomeOthers;
	private JTextField txtOthersSpecify;
	private _JXFormattedTextField txtBorrowerOthers;
	private _JXFormattedTextField txtSpouseOthers;
	private JLabel lblGMI;
	private JLabel lblIncomeSeparator7;
	//private _JXFormattedTextField txtBorrowerGMI;
	private _JFormattedTextField txtBorrowerGMI;
	//private _JXFormattedTextField txtSpouseGMI;
	private _JFormattedTextField txtSpouseGMI;
	private JLabel lblWithholdTax;
	private JLabel lblIncomeSeparator8;
	//private _JXFormattedTextField txtBorrowerWT;
	private _JFormattedTextField txtBorrowerWT;
	//private _JXFormattedTextField txtSpouseWT;
	private _JFormattedTextField txtSpouseWT;
	private JLabel lblIncomeSeparator9;

	private JPanel pnlCNMI;
	private JLabel lblCNMI;
	//private _JXFormattedTextField txtCNMI;
	private _JFormattedTextField txtCNMI;

	private JPanel pnlExpenses;
	private JLabel lblLivingUtil;
	private JLabel lblSeparator1;
	private _JXFormattedTextField txtLivingUtil;
	private JLabel lblEducation;
	private JLabel lblSeparator2;
	private _JXFormattedTextField txtEducation;
	private JLabel lblTransportation;
	private JLabel lblSeparator3;
	private _JXFormattedTextField txtTransportation;
	private JLabel lblLoanAmort;
	private JLabel lblSeparator4;
	private _JXFormattedTextField txtLoanAmort;
	private JLabel lblExpensesOthers;
	private JTextField txtExpensesOthers1;
	private _JXFormattedTextField txtExpensesOthers2;
	private JLabel lblCME;
	private JLabel lblSeparator5;
	//private _JXFormattedTextField txtCME;
	private _JFormattedTextField txtCME;
	private JLabel lblCNDI;
	private JLabel lblSeparator6;
	//private _JXFormattedTextField txtCNDI;
	private _JFormattedTextField txtCNDI;
	

	private ClientInformation ci;

	private JLabel lblMTPPClass;

	private JComboBox cmbMTPPClass;

	protected static DecimalFormat df = new DecimalFormat("#,##0.00");

	public pnlFinancialInfo(ClientInformation ci) {
		this.ci = ci;
		initGUI();
	}

	public pnlFinancialInfo(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlFinancialInfo(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlFinancialInfo(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}
	@Override
	public void initGUI() {
		this.setLayout(new GridLayout(1, 2));
		{
			JPanel pnlLeft= new JPanel(new SpringLayout());
			this.add(pnlLeft/*, BorderLayout.WEST*/);
			{
				pnlMainInfo = new JPanel();
				pnlLeft.add(pnlMainInfo);
				pnlMainInfo.setLayout(new SpringLayout());
				pnlMainInfo.setBorder(JTBorderFactory.createTitleBorder("Main Info"));
				{
					lblEntityType = new JLabel("Entity Type", JLabel.TRAILING);
					pnlMainInfo.add(lblEntityType);
				}
				{
					cmbEntityType = new JComboBox();
					pnlMainInfo.add(cmbEntityType);
					cmbEntityType.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {

							/*if(cmbEntityType.getSelectedItem().toString().equals("HLA CO-BORROWER 1") 
									|| cmbEntityType.getSelectedItem().toString().equals("HLA CO-BORROWER 2"))
							{
								//clearFields();
								lblSpouseOther.setVisible(false);
								txtSpouseSal.setText("0.00");
								txtSpouseSal.setVisible(false);
								txtSpouseAllowance.setText("0.00");
								txtSpouseAllowance.setVisible(false);
								txtSpouseComm.setText("0.00");
								txtSpouseComm.setVisible(false);
								txtSpouseRental.setText("0.00");
								txtSpouseRental.setVisible(false);
								txtSpouseOthers.setText("0.00");
								txtSpouseOthers.setVisible(false);
								txtSpouseGMI.setText("0.00");
								txtSpouseGMI.setVisible(false);
								txtSpouseWT.setText("0.00");
								txtSpouseWT.setVisible(false);
								//txtAssetsLiabilities.setText("0.00");
								lblCNMI.setText("Net Monthly Income");
								lblCME.setText("Monthly Expenses");
								lblCNDI.setText("Net Disposable Income");
								//txtLoanEntitlement.setEnabled(true);
								displayFinancialInfo(ci.getEntityID());

							}else{
								//clearFields();
								lblSpouseOther.setVisible(true);
								txtSpouseSal.setVisible(true);
								txtSpouseSal.setText("0.00");;
								txtSpouseAllowance.setVisible(true);
								txtSpouseAllowance.setText("0.00");
								txtSpouseComm.setVisible(true);
								txtSpouseComm.setText("0.00");
								txtSpouseRental.setVisible(true);
								txtSpouseRental.setText("0.00");
								txtSpouseOthers.setVisible(true);
								txtSpouseOthers.setText("0.00");
								txtSpouseGMI.setVisible(true);
								txtSpouseGMI.setText("0.00");
								txtSpouseWT.setVisible(true);
								txtSpouseWT.setText("0.00");
								lblCNMI.setText("Combined Net Monthly Income");		
								lblCME.setText("Combined Monthly Expenses");
								lblCNDI.setText("Combined Net Disposable Income");
								//txtLoanEntitlement.setEnabled(false);
								displayFinancialInfo(ci.getEntityID());
							}*/
							
							displayFinancialInfo(ci.getEntityID());
						}
					});
				}
				{//ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
					lblMTPPClass = new JLabel("MTPP Classification", JLabel.TRAILING);
					pnlMainInfo.add(lblMTPPClass);
				}
				{//ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
					String status [] = {"Low Risk", "Normal Risk", "High Risk"};
					cmbMTPPClass = new JComboBox(status);
					pnlMainInfo.add(cmbMTPPClass);
					cmbMTPPClass.setEnabled(false);
				}
				{
					lblEntityName = new JLabel("Entity Name");
					pnlMainInfo.add(lblEntityName);
				}
				{
					lblLoanEntitlement = new JLabel("Loan Entitlement", JLabel.TRAILING);
					pnlMainInfo.add(lblLoanEntitlement);
				}
				{
					txtEntityName = new _JXTextField();
					pnlMainInfo.add(txtEntityName);
				}
				{
					txtLoanEntitlement = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlMainInfo.add(txtLoanEntitlement);
					txtLoanEntitlement.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					//txtLoanEntitlement.setText("0.00");
				}
				
				SpringUtilities.makeCompactGrid(pnlMainInfo, 4, 2, 5, 5, 5, 5, false);
			}
			{
				pnlAssets = new JPanel();
				pnlLeft.add(pnlAssets);
				pnlAssets.setLayout(new SpringLayout());
				pnlAssets.setBorder(JTBorderFactory.createTitleBorder("Assets"));
				{
					lblCashOnHand = new JLabel("Cash on Hand/Bank", JLabel.TRAILING);
					pnlAssets.add(lblCashOnHand);
				}
				{
					txtCashOnHand = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlAssets.add(txtCashOnHand);
					txtCashOnHand.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtCashOnHand.setText("0.00");
					txtCashOnHand.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {
							try {
								TotalAssets();
								netWorth();
							} catch (NumberFormatException a) { }
						}
					});
				}
				{
					lblStocksBonds = new JLabel("Stocks/Bonds", JLabel.TRAILING);
					pnlAssets.add(lblStocksBonds);
				}
				{
					txtStocksBonds = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlAssets.add(txtStocksBonds);
					txtStocksBonds.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtStocksBonds.setText("0.00");
					txtStocksBonds.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	
							try {
								TotalAssets();
								netWorth();
							} catch (NumberFormatException a) { }
						}
					});
				}
				{
					lblRealEstate = new JLabel("Real Estate", JLabel.TRAILING);
					pnlAssets.add(lblRealEstate);
				}
				{
					txtRealEstate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlAssets.add(txtRealEstate);
					txtRealEstate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtRealEstate.setText("0.00");
					txtRealEstate.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {
							try {
								TotalAssets();
								netWorth();
							} catch (NumberFormatException a) { }
						}
					});
				}
				{
					lblVehiclesOwned = new JLabel("Vehicles Owned", JLabel.TRAILING);
					pnlAssets.add(lblVehiclesOwned);
				}
				{
					txtVehiclesOwned = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlAssets.add(txtVehiclesOwned);
					txtVehiclesOwned.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtVehiclesOwned.setText("0.00");
					txtVehiclesOwned.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {
							try {
								TotalAssets();
								netWorth();
							} catch (NumberFormatException a) { }
						}
					});
				}
				{
					lblAssetsOthers = new JLabel("Others", JLabel.TRAILING);
					pnlAssets.add(lblAssetsOthers);
				}
				{
					txtAssetsOthers = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlAssets.add(txtAssetsOthers);
					txtAssetsOthers.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtAssetsOthers.setText("0.00");
					txtAssetsOthers.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {
							try {
								TotalAssets();
								netWorth();
							} catch (NumberFormatException a) { }
						}
					});
				}
				{
					lblTotalAssets = new JLabel("Total Assets", JLabel.TRAILING);
					pnlAssets.add(lblTotalAssets);
				}
				/*{
					txtTotalAssets = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlAssets.add(txtTotalAssets);
					txtTotalAssets.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtTotalAssets.setText("0.00");
					txtTotalAssets.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {		
							netWorth();
						}
					});
				}*/
				{
					txtTotalAssets = new _JFormattedTextField();
					pnlAssets.add(txtTotalAssets);
					txtTotalAssets.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {		
							netWorth();
						}
					});
				}
				SpringUtilities.makeCompactGrid(pnlAssets, 6, 2, 5, 5, 5, 5, false);
			}
			{
				pnlLiabilities = new JPanel();
				pnlLeft.add(pnlLiabilities);
				pnlLiabilities.setLayout(new SpringLayout());
				pnlLiabilities.setBorder(JTBorderFactory.createTitleBorder("Liabilities"));
				{
					lblUnsecuredLoans = new JLabel("Unsecured Loans", JLabel.TRAILING);
					pnlLiabilities.add(lblUnsecuredLoans);
				}
				{
					txtUnsecuredLoans = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					txtUnsecuredLoans.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					pnlLiabilities.add(txtUnsecuredLoans);
					txtUnsecuredLoans.setText("0.00");
					txtUnsecuredLoans.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	
							try {
								totalLiabilities();
								netWorth();
							} catch (NumberFormatException a) { }
						}
					});
				}
				{
					lblLiabilitiesOthers = new JLabel("Others", JLabel.TRAILING);
					pnlLiabilities.add(lblLiabilitiesOthers);
				}
				{
					txtLiabilitiesOthers = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					txtLiabilitiesOthers.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					pnlLiabilities.add(txtLiabilitiesOthers);
					txtLiabilitiesOthers.setText("0.00");
					txtLiabilitiesOthers.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {
							try {
								totalLiabilities();
								netWorth();
							} catch (NumberFormatException a) { }
						}
					});
				}
				{
					lblTotalLiabilities = new JLabel("Total Liabilities", JLabel.TRAILING);
					pnlLiabilities.add(lblTotalLiabilities);
				}
				/*{
					txtTotalLiabilities = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					txtTotalLiabilities.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					pnlLiabilities.add(txtTotalLiabilities);
					txtTotalLiabilities.setText("0.00");
					txtTotalLiabilities.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {		
							netWorth();
						}
					});
				}*/
				{
					txtTotalLiabilities = new _JFormattedTextField();
					pnlLiabilities.add(txtTotalLiabilities);
					txtTotalLiabilities.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {		
							netWorth();
						}
					});
				}
				SpringUtilities.makeCompactGrid(pnlLiabilities, 3, 2, 5, 5, 5, 5, false);
			}
			{
				pnlNetWorth = new JPanel();
				pnlLeft.add(pnlNetWorth);
				pnlNetWorth.setLayout(new SpringLayout());
				pnlNetWorth.setBorder(JTBorderFactory.createTitleBorder("Net Worth"));
				{
					lblAssetsLiabilities = new JLabel("(Assets-Liabilities)", JLabel.TRAILING);
					pnlNetWorth.add(lblAssetsLiabilities);
				}
				/*{
					txtAssetsLiabilities = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlNetWorth.add(txtAssetsLiabilities);
					txtAssetsLiabilities.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtAssetsLiabilities.setText("0.00");
				}*/
				{
					txtAssetsLiabilities = new _JFormattedTextField();
					pnlNetWorth.add(txtAssetsLiabilities);
				}
				SpringUtilities.makeCompactGrid(pnlNetWorth, 1, 2, 5, 5, 5, 5, false);
			}
			SpringUtilities.makeCompactGrid(pnlLeft, 4, 1, 5, 5, 5, 5, false);
		}
		{
			JPanel pnlRight = new JPanel(new SpringLayout());
			this.add(pnlRight/*, BorderLayout.EAST*/);
			{
				{
					pnlIncome = new JPanel(new BorderLayout());
					pnlRight.add(pnlIncome);
					pnlIncome.setLayout(new SpringLayout());
					pnlIncome.setBorder(JTBorderFactory.createTitleBorder("Income"));
					{
						JPanel pnlIncomeWest = new JPanel(new GridLayout(9, 1));
						pnlIncome.add(pnlIncomeWest, BorderLayout.WEST);
						{
							lblIncomeSeparator1 = new JLabel();
							pnlIncomeWest.add(lblIncomeSeparator1);
						}
						{
							lblSalaries = new JLabel("Salaries", JLabel.TRAILING);
							pnlIncomeWest.add(lblSalaries);
						}
						{
							lblAllowances = new JLabel("Allowances", JLabel.TRAILING);
							pnlIncomeWest.add(lblAllowances);
						}
						{
							lblCommisssions = new JLabel("Commission", JLabel.TRAILING);
							pnlIncomeWest.add(lblCommisssions);
						}
						{
							lblRentalIncome = new JLabel("Rental Income", JLabel.TRAILING);
							pnlIncomeWest.add(lblRentalIncome);
						}
						{
							lblIncomeOthers = new JLabel("Others", JLabel.TRAILING);
							pnlIncomeWest.add(lblIncomeOthers);
						}
						{
							lblGMI = new JLabel("Gross Mon. Income", JLabel.TRAILING);
							pnlIncomeWest.add(lblGMI);
						}
						{
							lblWithholdTax = new JLabel("Withholding Tax", JLabel.TRAILING);
							pnlIncomeWest.add(lblWithholdTax);
						}
						{
							lblCNMI = new JLabel("Combined Net Mon. Income", JLabel.TRAILING);
							pnlIncomeWest.add(lblCNMI);
						}
						//SpringUtilities.makeCompactGrid(pnlIncomeWest, 9, 1, 3, 3, 3, 3, false);
					}
					{
						JPanel pnlIncomeEast = new JPanel(new GridLayout(9, 1, 2, 2));
						pnlIncome.add(pnlIncomeEast, BorderLayout.CENTER);
						pnlIncomeEast.setPreferredSize(new Dimension(500, 0));
						{
							JPanel pnlIERow1 = new JPanel(new GridLayout(1, 2));
							pnlIncomeEast.add(pnlIERow1);
							{
								lblBorrower = new JLabel("Borrower");
								pnlIERow1.add(lblBorrower);
							}
							{
								lblSpouseOther = new JLabel("Spouse/Other");
								pnlIERow1.add(lblSpouseOther);
							}
							//SpringUtilities.makeCompactGrid(pnlIERow1, 1, 2, 10, 10, 10, 10, false);
						}
						{
							JPanel pnlIERow2 = new JPanel(new BorderLayout());
							pnlIncomeEast.add(pnlIERow2);
							pnlIERow2.setLayout(new GridLayout(1, 2, 2, 2));
							{
								txtBorrowerSal = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow2.add(txtBorrowerSal);
								txtBorrowerSal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBorrowerSal.setText("0.00");
								txtBorrowerSal.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											borrowerGMI();
											//borrowerWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtSpouseSal = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow2.add(txtSpouseSal);
								txtSpouseSal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSpouseSal.setText("0.00");
								txtSpouseSal.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											spouseGMI();
											//spouseWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							//SpringUtilities.makeCompactGrid(pnlIERow2, 1, 2, 1, 1, 1, 1, false);
						}
						{
							JPanel pnlIERow3 = new JPanel(new BorderLayout());
							pnlIncomeEast.add(pnlIERow3);
							pnlIERow3.setLayout(new GridLayout(1, 2, 2, 2));
							{
								txtBorrowerAllowance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow3.add(txtBorrowerAllowance);
								txtBorrowerAllowance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBorrowerAllowance.setValue(new BigDecimal("0.00"));
								txtBorrowerAllowance.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											borrowerGMI();
											//borrowerWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtSpouseAllowance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow3.add(txtSpouseAllowance);
								txtSpouseAllowance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSpouseAllowance.setText("0.00");
								txtSpouseAllowance.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											spouseGMI();
											//spouseWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							//SpringUtilities.makeCompactGrid(pnlIERow3, 1, 2, 1, 1, 1, 1, false);
						}
						{
							JPanel pnlIERow4 = new JPanel(new BorderLayout());
							pnlIncomeEast.add(pnlIERow4);
							pnlIERow4.setLayout(new GridLayout(1, 2, 2, 2));
							{
								txtBorrowerComm = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow4.add(txtBorrowerComm);
								txtBorrowerComm.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBorrowerComm.setText("0.00");
								txtBorrowerComm.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try {
											borrowerGMI();
											//borrowerWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtSpouseComm = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow4.add(txtSpouseComm);
								txtSpouseComm.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSpouseComm.setText("0.00");
								txtSpouseComm.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try {
											spouseGMI();
											//spouseWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							//SpringUtilities.makeCompactGrid(pnlIERow4, 1, 2, 1, 1, 1, 1, false);
						}
						{
							JPanel pnlIERow5 = new JPanel(new BorderLayout());
							pnlIncomeEast.add(pnlIERow5);
							pnlIERow5.setLayout(new GridLayout(1, 2, 2, 2));
							{
								txtBorrowerRental = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow5.add(txtBorrowerRental);
								txtBorrowerRental.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBorrowerRental.setText("0.00");
								txtBorrowerRental.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											borrowerGMI();
											//borrowerWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtSpouseRental = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow5.add(txtSpouseRental);
								txtSpouseRental.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSpouseRental.setText("0.00");
								txtSpouseRental.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											spouseGMI();
											//spouseWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							//SpringUtilities.makeCompactGrid(pnlIERow5, 1, 2, 1, 1, 1, 1, false);
						}
						{
							JPanel pnlIERow6 = new JPanel(new BorderLayout());
							pnlIncomeEast.add(pnlIERow6);
							pnlIERow6.setLayout(new GridLayout(1, 2, 2, 2));
							{
								txtBorrowerOthers = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow6.add(txtBorrowerOthers);
								txtBorrowerOthers.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBorrowerOthers.setText("0.00");
								txtBorrowerOthers.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											borrowerGMI();
											//borrowerWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtSpouseOthers = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow6.add(txtSpouseOthers);
								txtSpouseOthers.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSpouseOthers.setText("0.00");
								txtSpouseOthers.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											spouseGMI();
											//spouseWT();
											netMonthInc();
											netDisposableIncome();
										} catch(NumberFormatException a) {}
									}
								});
							}
							//SpringUtilities.makeCompactGrid(pnlIERow6, 1, 2, 1, 1, 1, 1, false);
						}
						{
							JPanel pnlIERow7 = new JPanel(new BorderLayout());
							pnlIncomeEast.add(pnlIERow7);
							pnlIERow7.setLayout(new GridLayout(1, 2, 2, 2));
							/*{
								txtBorrowerGMI = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow7.add(txtBorrowerGMI);
								txtBorrowerGMI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBorrowerGMI.setText("0.00");
							}*/
							{
								txtBorrowerGMI = new _JFormattedTextField();
								pnlIERow7.add(txtBorrowerGMI);
							}
							/*{
								txtSpouseGMI = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow7.add(txtSpouseGMI);
								txtSpouseGMI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSpouseGMI.setText("0.00");
							}*/
							{
								txtSpouseGMI = new _JFormattedTextField();
								pnlIERow7.add(txtSpouseGMI);
							}
							//SpringUtilities.makeCompactGrid(pnlIERow7, 1, 2, 1, 1, 1, 1, false);
						}
						{
							JPanel pnlIERow8 = new JPanel(new BorderLayout());
							pnlIncomeEast.add(pnlIERow8);
							pnlIERow8.setLayout(new GridLayout(1, 2, 2, 4));
							/*{
								txtBorrowerWT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow8.add(txtBorrowerWT);
								txtBorrowerWT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBorrowerWT.setText("0.00");
							}*/
							{
								txtBorrowerWT = new _JFormattedTextField();
								pnlIERow8.add(txtBorrowerWT);
							}
							/*{
								txtSpouseWT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlIERow8.add(txtSpouseWT);
								txtSpouseWT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSpouseWT.setText("0.00");
							}*/
							{
								txtSpouseWT = new _JFormattedTextField();
								pnlIERow8.add(txtSpouseWT);
							}
						}
						/*{
							txtCNMI = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlIncomeEast.add(txtCNMI);
							txtCNMI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtCNMI.setText("0.00");
						}*/
						{
							txtCNMI = new _JFormattedTextField();
							pnlIncomeEast.add(txtCNMI);
						}
					}
					SpringUtilities.makeCompactGrid(pnlIncome, 1, 2, 5, 5, 5, 5, false);
				}
				{
					pnlExpenses = new JPanel();
					pnlRight.add(pnlExpenses);
					pnlExpenses.setLayout(new SpringLayout());
					pnlExpenses.setBorder(JTBorderFactory.createTitleBorder("Expenses(Combined for Borrower/Spouse/Other)"));
					{
						lblLivingUtil = new JLabel("Living & Utilities", JLabel.TRAILING);
						pnlExpenses.add(lblLivingUtil);
					}
					{
						txtLivingUtil = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlExpenses.add(txtLivingUtil);
						txtLivingUtil.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtLivingUtil.setText("0.00");
						txtLivingUtil.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								try{
									totalExpenses();
									netDisposableIncome();
								} catch(NumberFormatException a) {}
							}
						});
					}
					{
						lblEducation = new JLabel("Education", JLabel.TRAILING);
						pnlExpenses.add(lblEducation);
					}
					/*{
						lblSeparator2 = new JLabel("", JLabel.TRAILING);
						pnlExpenses.add(lblSeparator2);
					}*/
					{
						txtEducation = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlExpenses.add(txtEducation);
						txtEducation.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtEducation.setText("0.00");
						txtEducation.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								try{
									totalExpenses();
									netDisposableIncome();
								} catch(NumberFormatException a) {}
							}
						});
					}
					{
						lblTransportation = new JLabel("Trasportation", JLabel.TRAILING);
						pnlExpenses.add(lblTransportation);
					}
					/*{
						lblSeparator3 = new JLabel("", JLabel.TRAILING);
						pnlExpenses.add(lblSeparator3);
					}*/
					{
						txtTransportation = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlExpenses.add(txtTransportation);
						txtTransportation.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtTransportation.setText("0.00");
						txtTransportation.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								try{
									totalExpenses();
									netDisposableIncome();
								} catch(NumberFormatException a) {}
							}
						});
					}
					{
						lblLoanAmort = new JLabel("Loan Amortization", JLabel.TRAILING);
						pnlExpenses.add(lblLoanAmort);
					}
					/*{
						lblSeparator4 = new JLabel("", JLabel.TRAILING);
						pnlExpenses.add(lblSeparator4);
					}*/
					{
						txtLoanAmort = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlExpenses.add(txtLoanAmort);
						txtLoanAmort.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtLoanAmort.setText("0.00");
						txtLoanAmort.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								try{
									totalExpenses();
									netDisposableIncome();
								} catch(NumberFormatException a) {}
							}
						});
					}
					{
						lblExpensesOthers = new JLabel("Others", JLabel.TRAILING);
						pnlExpenses.add(lblExpensesOthers);
					}
					/*{
						txtExpensesOthers1 = new JTextField();
						pnlExpenses.add(txtExpensesOthers1);
					}*/
					{
						txtExpensesOthers2 = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlExpenses.add(txtExpensesOthers2);
						txtExpensesOthers2.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtExpensesOthers2.setText("0.00");
						txtExpensesOthers2.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								try{
									totalExpenses();
									netDisposableIncome();
								} catch (NumberFormatException a) { }
							}
						});
					}
					{
						lblCME = new JLabel("Combined Monthly Expenses", JLabel.TRAILING);
						pnlExpenses.add(lblCME);
					}
					/*{
						txtCME = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlExpenses.add(txtCME);
						txtCME.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtCME.setText("0.00");
					}*/
					{
					txtCME = new _JFormattedTextField();
					pnlExpenses.add(txtCME);
					}
					{
						lblCNDI = new JLabel("Combined Net Disposable Inc.", JLabel.TRAILING);
						pnlExpenses.add(lblCNDI);
					}
					/*{
						txtCNDI = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlExpenses.add(txtCNDI);
						txtCNDI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtCNDI.setText("0.00");
					}*/
					{
						txtCNDI = new _JFormattedTextField();
						pnlExpenses.add(txtCNDI);
					}
					SpringUtilities.makeCompactGrid(pnlExpenses, 7, 2, 5, 5, 5, 5, false);
				}
				SpringUtilities.makeCompactGrid(pnlRight, 2, 1, 5, 5, 5, 5, false);
			}
		}

		mapEntityType();
		cmbEntityType.setModel(new DefaultComboBoxModel(listEntitytype()));
		ci.setComponentsEditable(this, false);
		cmbEntityType.setEnabled(true);
			
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtLoanEntitlement, txtCashOnHand, txtStocksBonds, txtRealEstate, txtVehiclesOwned, txtAssetsOthers, 
				txtUnsecuredLoans, txtLiabilitiesOthers, txtBorrowerSal, txtSpouseSal, txtBorrowerAllowance, txtSpouseAllowance, txtBorrowerComm, txtSpouseComm, txtBorrowerRental, 
				txtSpouseRental, txtBorrowerOthers, txtSpouseOthers, txtLivingUtil, txtEducation, txtTransportation, txtLoanAmort, txtExpensesOthers2));
	}//XXX END OF INIT GUI
	private LinkedHashMap<String, String>mapEntityType(){ //Hashmap for connection type
		LinkedHashMap<String, String> mapEntityType = new LinkedHashMap<String, String>();

		String sql = "select trim(connect_type), trim(connect_type_desc) from mf_connect_type where with_financial_info = 't' order by sequence";
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x = 0; x < db.getRowCount(); x++){
				String connect_type = (String) db.getResult()[x][0];
				String connect_desc = (String) db.getResult()[x][1];
				mapEntityType.put(connect_type, connect_desc);
			}
			return mapEntityType;
		}else{
			return null;
		}
	}
	
	private Object[] listEntitytype(){
		ArrayList<String> arrayEntityType = new ArrayList<String>();

		for(Entry<String, String> entry : mapEntityType().entrySet()) {
			String connect_desc = entry.getValue();

			arrayEntityType.add(connect_desc);
			//System.out.printf("%s - %s%n", province_id, province_name);
		}
		return arrayEntityType.toArray();
	}
	
	private void computeLoanAmt(){//UNCOMMENT ME
		String entity_id = ci.getEntityID();
		pgSelect db = new pgSelect();
		
		String sql = "select sum(principal) from rf_client_schedule where entity_id = '"+entity_id+"' and part_id = '014'";
		db.select(sql);
		FncSystem.out("Display sql for loan entitlement", sql);
		
		BigDecimal loan_availed = (BigDecimal) db.getResult()[0][0];
		txtLoanEntitlement.setValue(loan_availed);
	}
	
	private void TotalAssets(){ //Computation for the Total Assets

		Double cash_on_hand = Double.valueOf(txtCashOnHand.getText().trim().replace(",","")).doubleValue();
		Double stocks_bonds = Double.valueOf(txtStocksBonds.getText().trim().replace(",","")).doubleValue();
		Double real_estate = Double.valueOf(txtRealEstate.getText().trim().replace(",","")).doubleValue();
		Double vehicles = Double.valueOf(txtVehiclesOwned.getText().trim().replace(",","")).doubleValue();
		Double assets_others = Double.valueOf(txtAssetsOthers.getText().trim().replace(",","")).doubleValue();

		Double totalAssets = cash_on_hand + stocks_bonds + real_estate + vehicles + assets_others;

		txtTotalAssets.setText(df.format(totalAssets));
	}
	private void totalLiabilities(){ //Computation for the total liabilities

		Double unsecured_loans = Double.valueOf(txtUnsecuredLoans.getText().trim().replace(",", "")).doubleValue();
		Double liabilities_others = Double.valueOf(txtLiabilitiesOthers.getText().trim().replace(",", "")).doubleValue();

		Double total_liabilitites = unsecured_loans + liabilities_others;
		txtTotalLiabilities.setText(df.format(total_liabilitites));
		//txtTotalLiabilities.setValue(value);
	}
	private void netWorth(){ //Computation for the total net worth (assets-liabilities)
		Double cash_on_hand = Double.valueOf(txtCashOnHand.getText().trim().replace(",","")).doubleValue();
		Double stocks_bonds = Double.valueOf(txtStocksBonds.getText().trim().replace(",","")).doubleValue();
		Double real_estate = Double.valueOf(txtRealEstate.getText().trim().replace(",","")).doubleValue();
		Double vehicles = Double.valueOf(txtVehiclesOwned.getText().trim().replace(",","")).doubleValue();
		Double assets_others = Double.valueOf(txtAssetsOthers.getText().trim().replace(",","")).doubleValue();
		Double unsecured_loans = Double.valueOf(txtUnsecuredLoans.getText().trim().replace(",", "")).doubleValue();
		Double liabilities_others = Double.valueOf(txtLiabilitiesOthers.getText().trim().replace(",", "")).doubleValue();

		Double networth = (cash_on_hand + stocks_bonds + real_estate + vehicles + assets_others) - (unsecured_loans + liabilities_others);
		txtAssetsLiabilities.setText(df.format(networth));
	}
	private void borrowerGMI(){ //COMPUTATION FOR BORROWER GROSS MONTHLY INCOME

		Double borrower_sal = Double.valueOf(txtBorrowerSal.getText().trim().replace(",","")).doubleValue();
		Double borrower_allowance = Double.valueOf(txtBorrowerAllowance.getText().trim().replace(",","")).doubleValue();
		Double borrower_comm = Double.valueOf(txtBorrowerComm.getText().trim().replace(",","")).doubleValue();
		Double borrower_rental_income = Double.valueOf(txtBorrowerRental.getText().trim().replace(",","")).doubleValue();
		Double borrower_others = Double.valueOf(txtBorrowerOthers.getText().trim().replace(",","")).doubleValue();

		Double borrowerGMI = borrower_sal + borrower_allowance + borrower_comm + borrower_rental_income + borrower_others;

		txtBorrowerGMI.setText(df.format(borrowerGMI));

	}
	
	private void spouseGMI(){ //COMPUTATION FOR SPOUSE GROSS MONTHLY INCOME
		//try{
		Double spouse_sal = Double.valueOf(txtSpouseSal.getText().trim().replace(",","")).doubleValue();
		//} catch (NumberFormatException evt) {}
		Double spouse_allowance = Double.valueOf(txtSpouseAllowance.getText().trim().replace(",","")).doubleValue();
		Double spouse_comm = Double.valueOf(txtSpouseComm.getText().trim().replace(",","")).doubleValue();
		Double spouse_rental_income = Double.valueOf(txtSpouseRental.getText().trim().replace(",","")).doubleValue();
		Double spouse_others = Double.valueOf(txtSpouseOthers.getText().trim().replace(",","")).doubleValue();

		Double spouseGMI = spouse_sal + spouse_allowance + spouse_comm + spouse_rental_income + spouse_others;

		txtSpouseGMI.setText(df.format(spouseGMI));
	}
	
	private void borrowerWT(){ //COMPUTATION FOR BORROWER WITHHOLDING TAX
		Double borrower_sal = Double.valueOf(txtBorrowerSal.getText().trim().replace(",","")).doubleValue();
		Double borrower_allowance = Double.valueOf(txtBorrowerAllowance.getText().trim().replace(",","")).doubleValue();
		Double borrower_comm = Double.valueOf(txtBorrowerComm.getText().trim().replace(",","")).doubleValue();
		Double borrower_rental_income = Double.valueOf(txtBorrowerRental.getText().trim().replace(",","")).doubleValue();
		Double borrower_others = Double.valueOf(txtBorrowerOthers.getText().trim().replace(",","")).doubleValue();

		Double borrowerWT = (borrower_sal + borrower_allowance + borrower_comm + borrower_rental_income + borrower_others) * .10;

		txtBorrowerWT.setText(df.format(borrowerWT));
	}
	
	private void spouseWT(){ //COMPUTATION FOR SPOUSE WITHHOLDING TAX
		Double spouse_sal = Double.valueOf(txtSpouseSal.getText().trim().replace(",","")).doubleValue();
		Double spouse_allowance = Double.valueOf(txtSpouseAllowance.getText().trim().replace(",","")).doubleValue();
		Double spouse_comm = Double.valueOf(txtSpouseComm.getText().trim().replace(",","")).doubleValue();
		Double spouse_rental_income = Double.valueOf(txtSpouseRental.getText().trim().replace(",","")).doubleValue();
		Double spouse_others = Double.valueOf(txtSpouseOthers.getText().trim().replace(",","")).doubleValue();

		Double spouseWT = (spouse_sal + spouse_allowance + spouse_comm + spouse_rental_income + spouse_others) * .10;

		txtSpouseWT.setText(df.format(spouseWT));
	}
	
	private void netMonthInc(){ //COMPUTATION FOR NET MONTHLY INCOME

		Double borrower_sal = Double.valueOf(txtBorrowerSal.getText().trim().replace(",","")).doubleValue();
		Double borrower_allowance = Double.valueOf(txtBorrowerAllowance.getText().trim().replace(",","")).doubleValue();
		Double borrower_comm = Double.valueOf(txtBorrowerComm.getText().trim().replace(",","")).doubleValue();
		Double borrower_rental_income = Double.valueOf(txtBorrowerRental.getText().trim().replace(",","")).doubleValue();
		Double borrower_others = Double.valueOf(txtBorrowerOthers.getText().trim().replace(",","")).doubleValue();
		Double spouse_sal = Double.valueOf(txtSpouseSal.getText().trim().replace(",","")).doubleValue();
		Double spouse_allowance = Double.valueOf(txtSpouseAllowance.getText().trim().replace(",","")).doubleValue();
		Double spouse_comm = Double.valueOf(txtSpouseComm.getText().trim().replace(",","")).doubleValue();
		Double spouse_rental_income = Double.valueOf(txtSpouseRental.getText().trim().replace(",","")).doubleValue();
		Double spouse_others = Double.valueOf(txtSpouseOthers.getText().trim().replace(",","")).doubleValue();

		Double borrowerGMI = borrower_sal + borrower_allowance + borrower_comm + borrower_rental_income + borrower_others;
		Double borrowerWT = (borrower_sal + borrower_allowance + borrower_comm + borrower_rental_income + borrower_others) * .10;
		Double spouseGMI = spouse_sal + spouse_allowance + spouse_comm + spouse_rental_income + spouse_others;
		Double spouseWT = (spouse_sal + spouse_allowance + spouse_comm + spouse_rental_income + spouse_others) * .10;

		//Double net_month_inc = (borrowerGMI + spouseGMI) - (borrowerWT + spouseWT);
		Double net_month_inc = (borrowerGMI + spouseGMI);

		txtCNMI.setText(df.format(net_month_inc));
	}

	private void totalExpenses(){ //COMPUTATION FOR TOTAL EXPENSES
		Double living_util = Double.valueOf(txtLivingUtil.getText().trim().replace(",","")).doubleValue();
		Double education = Double.valueOf(txtEducation.getText().trim().replace(",","")).doubleValue();
		Double transpo = Double.valueOf(txtTransportation.getText().trim().replace(",","")).doubleValue();
		Double loan_amort = Double.valueOf(txtLoanAmort.getText().trim().replace(",","")).doubleValue();
		Double exp_others = Double.valueOf(txtExpensesOthers2.getText().trim().replace(",","")).doubleValue();

		Double total_exp = living_util + education + transpo + loan_amort + exp_others;

		txtCME.setText(df.format(total_exp));
	}
	
	private void netDisposableIncome(){ //COMPUTATION FOR NET DISPOSABLE INCOME
		Double borrower_sal = Double.valueOf(txtBorrowerSal.getText().trim().replace(",","")).doubleValue();
		Double borrower_allowance = Double.valueOf(txtBorrowerAllowance.getText().trim().replace(",","")).doubleValue();
		Double borrower_comm = Double.valueOf(txtBorrowerComm.getText().trim().replace(",","")).doubleValue();
		Double borrower_rental_income = Double.valueOf(txtBorrowerRental.getText().trim().replace(",","")).doubleValue();
		Double borrower_others = Double.valueOf(txtBorrowerOthers.getText().trim().replace(",","")).doubleValue();
		Double spouse_sal = Double.valueOf(txtSpouseSal.getText().trim().replace(",","")).doubleValue();
		Double spouse_allowance = Double.valueOf(txtSpouseAllowance.getText().trim().replace(",","")).doubleValue();
		Double spouse_comm = Double.valueOf(txtSpouseComm.getText().trim().replace(",","")).doubleValue();
		Double spouse_rental_income = Double.valueOf(txtSpouseRental.getText().trim().replace(",","")).doubleValue();
		Double spouse_others = Double.valueOf(txtSpouseOthers.getText().trim().replace(",","")).doubleValue();
		Double living_util = Double.valueOf(txtLivingUtil.getText().trim().replace(",","")).doubleValue();
		Double education = Double.valueOf(txtEducation.getText().trim().replace(",","")).doubleValue();
		Double transpo = Double.valueOf(txtTransportation.getText().trim().replace(",","")).doubleValue();
		Double loan_amort = Double.valueOf(txtLoanAmort.getText().trim().replace(",","")).doubleValue();
		Double exp_others = Double.valueOf(txtExpensesOthers2.getText().trim().replace(",","")).doubleValue();

		Double borrowerGMI = borrower_sal + borrower_allowance + borrower_comm + borrower_rental_income + borrower_others;
		Double borrowerWT = (borrower_sal + borrower_allowance + borrower_comm + borrower_rental_income + borrower_others) * .10;
		Double spouseGMI = spouse_sal + spouse_allowance + spouse_comm + spouse_rental_income + spouse_others;
		Double spouseWT = (spouse_sal + spouse_allowance + spouse_comm + spouse_rental_income + spouse_others) * .10;
		Double total_exp = living_util + education + transpo + loan_amort + exp_others;

		//Double net_disposable_inc = ((borrowerGMI + spouseGMI) - (borrowerWT + spouseWT)) - total_exp;
		Double net_disposable_inc = ((borrowerGMI + spouseGMI) - total_exp);

		txtCNDI.setText(df.format(net_disposable_inc));
	}
	
	private boolean isFinancialExisting(String entity_id, String entity_type){//CHECKS WHETHER THE FINANCIAL INFORMATION IS EXISTING
		pgSelect db = new pgSelect();
		db.select("select * from rf_financial_information "+
				"where connect_type = (select connect_type from mf_connect_type where connect_type_desc = '"+ entity_type +"')"+
				"and entity_id = '"+entity_id+"'");
		return db.isNotNull();
	}
	
	public void editFinancialInfo(){//EDITING FOR THE FINANCIAL INFORMATION
			ci.setComponentsEditable(this, true);
			txtTotalAssets.setEditable(false);
			txtTotalLiabilities.setEditable(false);
			txtAssetsLiabilities.setEditable(false);
			txtBorrowerGMI.setEditable(false);
			txtBorrowerWT.setEditable(false);
			txtSpouseGMI.setEditable(false);
			txtSpouseWT.setEditable(false);
			txtCNMI.setEditable(false);
			txtCME.setEditable(false);
			txtCNDI.setEditable(false);
			
			cmbEntityType.setEnabled(false);
			cmbMTPPClass.setEnabled(true);
	}
	
	public void cancelFinancialInfo(){//CANCELATION FOR THE FINANCIAL INFO
		clearFields();
		ci.setComponentsEditable(this, false);
		cmbEntityType.setEnabled(true);
	}

	public boolean saveFinancial(String entity_id){//SAVING FOR THE FINANCIAL INFORMATION

		pgUpdate db = new pgUpdate();

		String entity_type = (String) cmbEntityType.getSelectedItem();
		String mttp_class = (String) cmbMTPPClass.getSelectedItem(); //ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
		BigDecimal loan_entitlement = (BigDecimal) txtLoanEntitlement.getValued();
		BigDecimal assets_cash_on_hand = (BigDecimal) txtCashOnHand.getValued();
		BigDecimal stocks_bonds = (BigDecimal) txtStocksBonds.getValued();
		BigDecimal real_estate = (BigDecimal) txtRealEstate.getValued();
		BigDecimal vehicles_owned = (BigDecimal) txtVehiclesOwned.getValued();
		BigDecimal assets_others = (BigDecimal) txtAssetsOthers.getValued();
		BigDecimal unsecured_loans = (BigDecimal) txtUnsecuredLoans.getValued();
		BigDecimal liabilities_others = (BigDecimal) txtLiabilitiesOthers.getValued();
		BigDecimal borrower_sal = (BigDecimal) txtBorrowerSal.getValued();
		BigDecimal spouse_sal = (BigDecimal) txtSpouseSal.getValued();
		BigDecimal borrower_allowances = (BigDecimal) txtBorrowerAllowance.getValued();
		BigDecimal spouse_allowances = (BigDecimal) txtSpouseAllowance.getValued();
		BigDecimal borrower_comm = (BigDecimal) txtBorrowerComm.getValued();
		BigDecimal spouse_comm = (BigDecimal) txtSpouseComm.getValued();
		BigDecimal borrower_rental_income = (BigDecimal) txtBorrowerRental.getValued();
		BigDecimal spouse_rental_income = (BigDecimal) txtSpouseRental.getValued();
		BigDecimal borrower_others = (BigDecimal) txtBorrowerOthers.getValued();
		BigDecimal spouse_others = (BigDecimal) txtSpouseOthers.getValued();
		BigDecimal living_util = (BigDecimal) txtLivingUtil.getValued();
		BigDecimal education = (BigDecimal) txtEducation.getValued();
		BigDecimal transportation = (BigDecimal) txtTransportation.getValued();
		BigDecimal loan_amort = (BigDecimal) txtLoanAmort.getValued();
		BigDecimal expenses_others = (BigDecimal) txtExpensesOthers2.getValued();

		if (isFinancialExisting(entity_id, entity_type)){
				
				String sql = "UPDATE rf_financial_information\n" + 
						"   SET as_cashonhand= coalesce("+ assets_cash_on_hand +", 0.00) , as_stocksandbonds= coalesce("+ stocks_bonds +", 0.00), \n" + 
						"       as_realestate= coalesce("+ real_estate +", 0.00) , as_vehicles= coalesce("+ vehicles_owned +", 0.00) , as_others1= coalesce("+ assets_others +", 0.00),  \n" + 
						"       li_unsecloans= coalesce("+ unsecured_loans +", 0.00) , li_others1= coalesce("+ liabilities_others +", 0.00) , in_bosalaries= coalesce("+ borrower_sal +", 0.00), \n" + 
						"       in_boallowances= coalesce("+ borrower_allowances +", 0.00), in_bocommissions= coalesce("+ borrower_comm +", 0.00), in_borentalincome = coalesce("+ borrower_rental_income +", 0.00), \n" + 
						"       in_boothers1=coalesce("+ borrower_others +", 0.00), in_spsalaries= coalesce("+ spouse_sal +", 0.00), in_spallowances= coalesce("+ spouse_allowances +", 0.00), in_spcommissions= coalesce("+ spouse_comm +", 0.00), \n" + 
						"       in_sprentalincome= coalesce("+ spouse_rental_income +", 0.00), in_spothers1= coalesce("+ spouse_others +", 0.00), \n" + 
						"       ex_living= coalesce("+ living_util +",0.00), ex_education=coalesce("+ education +", 0.00), ex_transpo=coalesce("+ transportation +", 0.00), ex_loanamort=coalesce("+ loan_amort +", 0.00),\n" + 
						"       ex_others1=coalesce("+ expenses_others +", 0.00), loan_entitlement= coalesce ("+ loan_entitlement +", 0.00), mtpp_class = '"+mttp_class+"' \n" + 
						" WHERE entity_id = '"+ entity_id +"' and connect_type = (select connect_type from mf_connect_type where connect_type_desc = '"+ entity_type +"')";
				db.executeUpdate(sql, true);
				
		}else{
			
				String sql = "INSERT INTO rf_financial_information( \n" + 
						"entity_id, connect_type, as_cashonhand, as_stocksandbonds, \n" + 
						"as_realestate, as_vehicles, as_others1, li_unsecloans, li_others1, \n" + 
						"in_bosalaries, in_boallowances, in_bocommissions, in_borentalincome, \n" + 
						"in_boothers1, in_spsalaries, in_spallowances, \n" + 
						"in_spcommissions, in_sprentalincome, in_spothers1,  \n" + 
						"ex_living, ex_education, ex_transpo, ex_loanamort, ex_others1, \n" + 
						"loan_entitlement, status_id, rowguid, mtpp_class)\n" + 
						"VALUES ('"+ entity_id +"', (select connect_type from mf_connect_type where connect_type_desc = '"+ entity_type +"'), coalesce("+ assets_cash_on_hand +", 0.00), coalesce("+ stocks_bonds +", 0.00), " + 
						"coalesce("+ real_estate +", 0.00), coalesce("+ vehicles_owned +", 0.00), coalesce("+ assets_others +", 0.00), coalesce("+ unsecured_loans +", 0.00), " + 
						"coalesce("+ liabilities_others +", 0.00), coalesce("+ borrower_sal +", 0.00), coalesce("+ borrower_allowances +", 0.00), coalesce("+ borrower_comm +", 0.00), " + 
						"coalesce("+ borrower_rental_income +", 0.00), coalesce("+ borrower_others +", 0.00), coalesce("+ spouse_sal +" , 0.00), coalesce("+ spouse_allowances +", 0.00), " + 
						"coalesce("+ spouse_comm +", 0.00), coalesce("+ spouse_rental_income +", 0.00), coalesce("+ spouse_others +", 0.00), coalesce("+ living_util +", 0.00), " + 
						"coalesce("+ education +", 0.00), coalesce("+ transportation +", 0.00), coalesce("+ loan_amort +", 0.00), coalesce("+ expenses_others +", 0.00), " + 
						""+ loan_entitlement +", 'A', '"+mttp_class+"')";
				db.executeUpdate(sql, true);
		}
		db.commit();
		ci.setComponentsEditable(this, false);
		cmbEntityType.setEnabled(true);
		cmbMTPPClass.setEnabled(false);
		return true;
	}

	public void displayFinancialInfo(String entity_id){//TODO check display of financial info
		String entity_type = (String) cmbEntityType.getSelectedItem();

		//if(cmbEntityType.getSelectedItem().toString().equals("MAIN APPLICANT")){

		String sql =" SELECT b.connect_type_desc , a.as_cashonhand , a.as_stocksandbonds, \n" + 
				"       a.as_realestate, a.as_vehicles, a.as_others1,(a.as_cashonhand + a.as_stocksandbonds + a.as_realestate + a.as_vehicles + a.as_others1), a.li_unsecloans, \n" + 
				"       a.li_others1,(a.li_unsecloans + a.li_others1), (a.as_cashonhand + a.as_stocksandbonds + a.as_realestate + a.as_vehicles + a.as_others1 - a.li_unsecloans - a.li_others1), a.in_bosalaries, a.in_boallowances, a.in_bocommissions, a.in_borentalincome, \n" + 
				"       a.in_boothers1,(a.in_bosalaries + a.in_boallowances + a.in_bocommissions + a.in_borentalincome + a.in_boothers1) as Borrower_GMI, /*((a.in_bosalaries + a.in_boallowances + a.in_bocommissions + a.in_borentalincome + a.in_boothers1)*.10)*/ 0 as Borrower_WT, \n" + 
				"       a.in_spsalaries, a.in_spallowances, a.in_spcommissions, a.in_sprentalincome, a.in_spothers1,\n" + 
				"       (a.in_spsalaries + a.in_spallowances + a.in_spcommissions +  a.in_sprentalincome+a.in_spothers1) as Spouse_GMI, \n" + 
				"       /*((a.in_spsalaries + a.in_spallowances + a.in_spcommissions +  a.in_sprentalincome+a.in_spothers1)*.10)*/ 0 as Spouse_WT, \n" + 
				"       ((a.in_bosalaries + a.in_boallowances + a.in_bocommissions + a.in_borentalincome + a.in_boothers1)+(coalesce(a.in_spsalaries, 0.00) + coalesce(a.in_spallowances, 0.00) + coalesce(a.in_spcommissions, 0.00) +  coalesce(a.in_sprentalincome, 0.00) + coalesce(a.in_spothers1, 0.00))) \n"+ 
				"       /*-((a.in_bosalaries +a.in_boallowances + a.in_bocommissions + a.in_borentalincome + a.in_boothers1)*.10)\n" + 
				"       -((coalesce(a.in_spsalaries, 0.00) + coalesce(a.in_spallowances, 0.00) + coalesce(a.in_spcommissions, 0.00) +  coalesce(a.in_sprentalincome, 0.00)+coalesce(a.in_spothers1, 0.00))*.10))*/ as combined_net_monthly_income, \n" + 
				
				"       a.ex_living, a.ex_education, a.ex_transpo, a.ex_loanamort,  \n" + 
				"       a.ex_others1,(a.ex_living + a.ex_education + a.ex_transpo + a.ex_loanamort + a.ex_others1)as total_expenses, \n" + 
				"       ((a.in_bosalaries + a.in_boallowances + a.in_bocommissions + a.in_borentalincome + a.in_boothers1)+(coalesce(a.in_spsalaries, 0.00) + coalesce(a.in_spallowances, 0.00) + coalesce(a.in_spcommissions, 0.00) +  coalesce(a.in_sprentalincome, 0.00) "+
				"		+coalesce(a.in_spothers1, 0.00))) /*-((a.in_bosalaries + a.in_boallowances + a.in_bocommissions + a.in_borentalincome + a.in_boothers1)*.10)\n" + 
				"       -((coalesce(a.in_spsalaries, 0.00) + coalesce(a.in_spallowances, 0.00) + coalesce(a.in_spcommissions, 0.00) +  coalesce(a.in_sprentalincome, 0.00)+coalesce(a.in_spothers1, 0.00))*.10)-(a.ex_living + a.ex_education + a.ex_transpo + a.ex_loanamort + a.ex_others1))*/ as combined_net_disposable_income,\n" + 
				"       coalesce(a.loan_entitlement, 0.00), a.mtpp_class \n" + 
				//"        (case when b.connect_type_desc = 'MAIN APPLICANT' then c.loan_amount else a.loan_entitlement end)" + 
				"  FROM rf_financial_information a\n" + 
				"  left join mf_connect_type b on b.connect_type = a.connect_type\n"+
				"  left join rf_creditloans_availed c on c.entity_id = a.entity_id" + 
				"  where a.entity_id ='"+ entity_id +"' and a.connect_type = (select connect_type from mf_connect_type where connect_type_desc = '"+ entity_type +"')";

		FncSystem.out("Display Financial Info", sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			//cmbEntityType.setSelectedItem(db.getResult()[0][0]);
			
			txtCashOnHand.setValue(db.getResult()[0][1]);
			txtStocksBonds.setValue(db.getResult()[0][2]);
			txtRealEstate.setValue(db.getResult()[0][3]);
			txtVehiclesOwned.setValue(db.getResult()[0][4]);
			txtAssetsOthers.setValue(db.getResult()[0][5]);
			txtTotalAssets.setValue(db.getResult()[0][6]);
			txtUnsecuredLoans.setValue(db.getResult()[0][7]);
			txtLiabilitiesOthers.setValue(db.getResult()[0][8]);
			txtTotalLiabilities.setValue(db.getResult()[0][9]);
			txtAssetsLiabilities.setValue(db.getResult()[0][10]);
			txtBorrowerSal.setValue(db.getResult()[0][11]);
			txtBorrowerAllowance.setValue(db.getResult()[0][12]);
			txtBorrowerComm.setValue(db.getResult()[0][13]);
			txtBorrowerRental.setValue(db.getResult()[0][14]);
			txtBorrowerOthers.setValue(db.getResult()[0][15]);
			txtBorrowerGMI.setValue(db.getResult()[0][16]);
			txtBorrowerWT.setValue(db.getResult()[0][17]);
			txtSpouseSal.setValue(db.getResult()[0][18]);
			txtSpouseAllowance.setValue(db.getResult()[0][19]);
			txtSpouseComm.setValue(db.getResult()[0][20]);
			txtSpouseRental.setValue(db.getResult()[0][21]);
			txtSpouseOthers.setValue(db.getResult()[0][22]);
			txtSpouseGMI.setValue(db.getResult()[0][23]);
			txtSpouseWT.setValue(db.getResult()[0][24]);
			txtCNMI.setValue(db.getResult()[0][25]);
			txtLivingUtil.setValue(db.getResult()[0][26]);
			txtEducation.setValue(db.getResult()[0][27]);
			txtTransportation.setValue(db.getResult()[0][28]);
			txtLoanAmort.setValue(db.getResult()[0][29]);
			txtExpensesOthers2.setValue(db.getResult()[0][30]);
			txtCME.setValue(db.getResult()[0][31]);
			txtCNDI.setValue(db.getResult()[0][32]);
			txtLoanEntitlement.setValue(db.getResult()[0][33]);
			cmbMTPPClass.setSelectedItem(db.getResult()[0][34]); //ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
			
		}else {
			
			txtCashOnHand.setText("0.00");
			txtStocksBonds.setText("0.00");
			txtRealEstate.setText("0.00");
			txtVehiclesOwned.setText("0.00");
			txtAssetsOthers.setText("0.00");
			txtTotalAssets.setText("0.00");
			txtUnsecuredLoans.setText("0.00");
			txtLiabilitiesOthers.setText("0.00");
			txtTotalLiabilities.setText("0.00");
			txtAssetsLiabilities.setText("0.00");
			txtBorrowerSal.setText("0.00");
			txtBorrowerAllowance.setText("0.00");
			txtBorrowerComm.setText("0.00");
			txtBorrowerRental.setText("0.00");
			txtBorrowerOthers.setText("0.00");
			txtBorrowerGMI.setText("0.00");
			txtBorrowerWT.setText("0.00");
			txtSpouseSal.setText("0.00");
			txtSpouseAllowance.setText("0.00");
			txtSpouseComm.setText("0.00");
			txtSpouseRental.setText("0.00");
			txtSpouseOthers.setText("0.00");
			txtSpouseGMI.setText("0.00");
			txtSpouseWT.setText("0.00");
			txtCNMI.setText("0.00");
			txtLivingUtil.setText("0.00");
			txtEducation.setText("0.00");
			txtTransportation.setText("0.00");
			txtLoanAmort.setText("0.00");
			txtExpensesOthers2.setText("0.00");
			txtCME.setText("0.00");
			txtCNDI.setText("0.00");
			cmbMTPPClass.setEnabled(false); //ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
			/*if(cmbEntityType.getSelectedItem().equals("MAIN APPLICANT")){//XXX UNCOMMENT ME IF WRONG
				computeLoanAmt();
			}*/
		}
	}
	
	public void clearFields(){//CLEARS THE FIELDS FOR THE FINANCIAL INFORMATION 
		txtLoanEntitlement.setValue(new BigDecimal("0.00"));
		txtCashOnHand.setValue(new BigDecimal("0.00"));
		txtStocksBonds.setValue(new BigDecimal("0.00"));
		txtRealEstate.setValue(new BigDecimal("0.00"));
		txtVehiclesOwned.setValue(new BigDecimal("0.00"));
		txtAssetsOthers.setValue(new BigDecimal("0.00"));
		txtTotalAssets.setValue(new BigDecimal("0.00"));
		txtUnsecuredLoans.setValue(new BigDecimal("0.00"));
		txtLiabilitiesOthers.setValue(new BigDecimal("0.00"));
		txtAssetsLiabilities.setValue(new BigDecimal("0.00"));
		txtBorrowerSal.setValue(new BigDecimal("0.00"));
		txtSpouseSal.setValue(new BigDecimal("0.00"));
		txtBorrowerAllowance.setValue(new BigDecimal("0.00"));
		txtSpouseAllowance.setValue(new BigDecimal("0.00"));
		txtBorrowerComm.setValue(new BigDecimal("0.00"));
		txtSpouseComm.setValue(new BigDecimal("0.00"));
		txtBorrowerRental.setValue(new BigDecimal("0.00"));
		txtSpouseRental.setValue(new BigDecimal("0.00"));
		txtBorrowerOthers.setValue(new BigDecimal("0.00"));
		txtSpouseOthers.setValue(new BigDecimal("0.00"));
		txtBorrowerGMI.setValue(new BigDecimal("0.00"));
		txtSpouseGMI.setValue(new BigDecimal("0.00"));
		txtBorrowerWT.setValue(new BigDecimal("0.00"));
		txtSpouseWT.setValue(new BigDecimal("0.00"));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
}
