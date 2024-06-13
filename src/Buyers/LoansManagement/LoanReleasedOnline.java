package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Buyers.ClientServicing._OrderOfPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelLoanReleased;

public class LoanReleasedOnline extends _JInternalFrame implements ActionListener, _GUI, KeyListener {

	private static final long serialVersionUID = 1144374520133370805L;
	static String title = "Loan Released (Online)";
	Dimension frameSize = new Dimension(900, 630);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;

	private _JLookup lookupClient;
	private _JXTextField txtClientName;
	private _JXTextField txtProjectID;
	private _JXTextField txtProjectName;
	private _JXTextField txtPblID;
	private _JXTextField txtUnitDescription;
	private _JXTextField txtTermRate;
	private _JXTextField txtSequenceNo;

	private _JDateChooser dateTransDate;
	private _JDateChooser dateActualDate;
	private JComboBox cmbReceiptType;
	private _JXTextField txtReceiptNo;
	private _JXTextField txtAccountNo;
	private _JXTextField txtCheckNo;
	private _JDateChooser dateCheckDate;
	private _JLookup lookupBank;
	private _JXTextField txtBank;
	private _JLookup lookupBranch;
	private _JXTextField txtBranch;
	private _JXFormattedTextField txtNetProceeds;

	private _JXFormattedTextField txtLoanableAmount;
	private _JXFormattedTextField txtVAT;
	private _JXFormattedTextField txtLoanableAmountNetVAT;
	private _JXFormattedTextField txtSRIDocStamps;
	private _JXFormattedTextField txtFire;
	private _JXFormattedTextField txtProcessingFee;
	private _JXFormattedTextField txtAppraisalFee;
	private _JXFormattedTextField txtInterimMRI;

	private JTextField txtPctgRetentionFee;
	private _JXFormattedTextField txtRetentionFee;

	private _JXFormattedTextField txt1stMA;
	private _JXFormattedTextField txtRefilingFee;
	private _JXFormattedTextField txtServiceFee;
	private _JXFormattedTextField txt10Retention;

	private JCheckBox chkEPEB;
	private _JXFormattedTextField txtEPEB;
	private JCheckBox chkETD;
	private _JXFormattedTextField txtETD;
	private JCheckBox chkCOC;
	private _JXFormattedTextField txtCOC;
	private JCheckBox chkMERALCO;
	private _JXFormattedTextField txtMERALCO;
	private JCheckBox chkCOMFAC5;
	private JTextField txtCOMFACpctg;
	private _JXFormattedTextField txtCOMFAC5;
	private _JXFormattedTextField txtTotal;
	private JCheckBox chkRightOfWay;
	private _JXFormattedTextField txtRightOfWay;

	private JCheckBox chkWaterSupply;
	private JTextField txtWaterSupplypctg;
	private _JXFormattedTextField txtWaterSupply;
	private JCheckBox chkTaxDec;
	private JTextField txtTaxDecpctg;
	private _JXFormattedTextField txtTaxDec;

	private _JScrollPaneMain scrollPaymentList;
	private _JTableMain tblPaymentList;
	private modelLoanReleased modelPaymentList;
	private JList rowHeaderPaymentList;

	private _JScrollPaneTotal scrollPaymentListTotal;
	private _JTableTotal tblPaymentListTotal;
	private modelLoanReleased modelPaymentListTotal;

	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	private GridLayout grid = new GridLayout(9, 1, 3, 3);
	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);

	private JPanel pnlCol1;
	private Boolean blnEdit = false; 

	public LoanReleasedOnline() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public LoanReleasedOnline(String title) {
		super(title);
		initGUI(); 
	}

	public LoanReleasedOnline(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(3, 3));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 3));
			{
				pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
				pnlNorth.setPreferredSize(new Dimension(790, 430));
				{
					JPanel pnlNorthBorder = new JPanel(new BorderLayout());
					pnlNorth.add(pnlNorthBorder, BorderLayout.NORTH);
					pnlNorthBorder.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
					pnlNorthBorder.setPreferredSize(new Dimension(790, 86));

					JPanel pnlClientDetails = new JPanel(new BorderLayout(3, 3));
					pnlNorthBorder.add(pnlClientDetails, BorderLayout.CENTER);
					pnlClientDetails.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					//pnlClientDetails.setPreferredSize(new Dimension(790, 80));
					{
						JPanel pnlLabels = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlClientDetails.add(pnlLabels, BorderLayout.WEST);
						{
							{
								JLabel lblClient = new JLabel("Client");
								pnlLabels.add(lblClient);
								lblClient.setFont(font11);
							}
							{
								JLabel lblProject = new JLabel("Project");
								pnlLabels.add(lblProject);
								lblProject.setFont(font11);
							}
							{
								JLabel lblUnitSeq = new JLabel("Unit / Seq. ");
								pnlLabels.add(lblUnitSeq);
								lblUnitSeq.setFont(font11);
							}
						}
					}
					{
						JPanel pnlFields = new JPanel(new BorderLayout(3, 3));
						pnlClientDetails.add(pnlFields, BorderLayout.CENTER);
						{
							JPanel pnlLookup = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlFields.add(pnlLookup, BorderLayout.WEST);
							{
								lookupClient = new _JLookup(null, "Clients", 0);
								pnlLookup.add(lookupClient);
								lookupClient.setReturnColumn(0);
								lookupClient.setLookupSQL(sqlClients());
								lookupClient.setFilterName(true);
								lookupClient.setPrompt("Client ID");
								lookupClient.setFont(FncLookAndFeel.systemFont_Plain.deriveFont(11f));
								lookupClient.setSizeDialog(new Dimension(800, 420));
								lookupClient.setPreferredSize(new java.awt.Dimension(100, 18));
								lookupClient.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											String entity_id = (String) data[0];
											String proj_id = (String) data[6];
											String pbl_id = (String) data[3];
											Integer seq_no = (Integer) data[4];

											displayClientDetails(entity_id, proj_id, pbl_id, seq_no);
										}
									}
								});
							}
							{
								txtProjectID = new _JXTextField("Project ID");
								pnlLookup.add(txtProjectID);
								txtProjectID.setFont(font11);
								txtProjectID.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								txtPblID = new _JXTextField("Unit ID");
								pnlLookup.add(txtPblID);
								txtPblID.setFont(font11);
								txtPblID.setHorizontalAlignment(JXTextField.CENTER);
							}
						}
						{
							JPanel pnlTags = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlFields.add(pnlTags, BorderLayout.CENTER);
							{
								txtClientName = new _JXTextField("Client Name");
								pnlTags.add(txtClientName);
								txtClientName.setFont(font11);
							}
							{
								txtProjectName = new _JXTextField("Project Name");
								pnlTags.add(txtProjectName);
								txtProjectName.setFont(font11);
							}
							{
								txtUnitDescription = new _JXTextField("Unit Description / Sequence No.");
								pnlTags.add(txtUnitDescription);
								txtUnitDescription.setFont(font11);
							}
						}
					}
					{
						JPanel pnlWest = new JPanel(new GridLayout(3, 1, 10, 3));
						pnlClientDetails.add(pnlWest, BorderLayout.EAST);
						//pnlWest.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
						pnlWest.setPreferredSize(new Dimension(240, 68));

						Dimension dimWest = new Dimension(100, 0);
						{
							JPanel pnlTermRate = new JPanel(new BorderLayout(3, 3));
							pnlWest.add(pnlTermRate);
							{
								JLabel lblTerm = new JLabel("Term / Rate", JLabel.TRAILING);
								pnlTermRate.add(lblTerm, BorderLayout.WEST);
								lblTerm.setPreferredSize(dimWest);
								lblTerm.setFont(font11);
							}
							{
								txtTermRate = new _JXTextField("Term / Rate");
								pnlTermRate.add(txtTermRate, BorderLayout.CENTER);
								txtTermRate.setFont(font11);
								txtTermRate.setHorizontalAlignment(JXTextField.CENTER);
							}
						}
						{
							JPanel pnlActualDate = new JPanel(new BorderLayout(3, 3));
							pnlWest.add(pnlActualDate);
							{
								JLabel lblActualDate = new JLabel("Actual Date", JLabel.TRAILING);
								pnlActualDate.add(lblActualDate, BorderLayout.WEST);
								lblActualDate.setPreferredSize(dimWest);
								lblActualDate.setFont(font11);
							}
							{
								dateActualDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlActualDate.add(dateActualDate, BorderLayout.CENTER);
								dateActualDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								((JTextField)dateActualDate.getDateEditor()).setFont(font11);
							}
						}
						{
							JPanel pnlTermRate = new JPanel(new BorderLayout(3, 3));
							pnlWest.add(pnlTermRate);
							{
								JLabel lblRate = new JLabel("Sequence No.", JLabel.TRAILING);
								pnlTermRate.add(lblRate, BorderLayout.WEST);
								lblRate.setPreferredSize(dimWest);
								lblRate.setFont(font11);
							}
							{
								txtSequenceNo = new _JXTextField("Sequence No.");
								pnlTermRate.add(txtSequenceNo, BorderLayout.CENTER);
								txtSequenceNo.setFont(font11.deriveFont(Font.BOLD));
								txtSequenceNo.setHorizontalAlignment(JXTextField.CENTER);
							}
						}
					}
				}
				{
					JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlNorth.add(pnlCenter, BorderLayout.CENTER);
					{
						JPanel pnlCol0 = new JPanel(new BorderLayout(3, 3));
						//pnlCenter.add(pnlCol0, BorderLayout.CENTER);
						{
							pnlCol1 = new JPanel(new BorderLayout(3, 3));
							pnlCol0.add(pnlCol1, BorderLayout.CENTER);
							pnlCol1.setBorder(JTBorderFactory.createTitleBorder("Receipt/Check Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								Dimension dimWest = new Dimension(73, 0);
								JPanel pnlCol1_1 = new JPanel(new GridLayout(7, 1, 3, 3));
								pnlCol1.add(pnlCol1_1, BorderLayout.CENTER);
								{
									JPanel pnlTransDate = new JPanel(new BorderLayout(3, 3));
									//pnlCol1_1.add(pnlTransDate);
									{
										JLabel lblTransDate = new JLabel("*Trans Date");
										pnlTransDate.add(lblTransDate, BorderLayout.WEST);
										lblTransDate.setPreferredSize(dimWest);
										lblTransDate.setFont(font11);
									}
									{
										dateTransDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlTransDate.add(dateTransDate, BorderLayout.CENTER);
										dateTransDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										dateTransDate.getCalendarButton().setEnabled(false);
										((JTextField)dateTransDate.getDateEditor()).setEditable(false);
										((JTextField)dateTransDate.getDateEditor()).setFont(font11);
									}
								}
								{
									JPanel pnlActualDate = new JPanel(new BorderLayout(3, 3));
									//pnlCol1_1.add(pnlActualDate);
									{
										JLabel lblActualDate = new JLabel("*Actual Date");
										pnlActualDate.add(lblActualDate, BorderLayout.WEST);
										lblActualDate.setPreferredSize(dimWest);
										lblActualDate.setFont(font11);
									}
								}
								{
									JPanel pnlReceiptType = new JPanel(new BorderLayout(3, 3));
									//pnlCol1_1.add(pnlReceiptType);
									{
										JLabel lblReceiptType = new JLabel("*Receipt Type");
										pnlReceiptType.add(lblReceiptType, BorderLayout.WEST);
										lblReceiptType.setPreferredSize(dimWest);
										lblReceiptType.setFont(font11);
									}
									{
										cmbReceiptType = new JComboBox(new DefaultComboBoxModel(getReceiptType()));
										pnlReceiptType.add(cmbReceiptType, BorderLayout.CENTER);
										cmbReceiptType.setFont(font11);
									}
								}
								{
									JPanel pnlReceiptNo = new JPanel(new BorderLayout(3, 3));
									pnlCol1_1.add(pnlReceiptNo);
									{
										JLabel lblReceiptNo = new JLabel("*Receipt No");
										pnlReceiptNo.add(lblReceiptNo, BorderLayout.WEST);
										lblReceiptNo.setPreferredSize(dimWest);
										lblReceiptNo.setFont(font11);
									}
									{
										txtReceiptNo = new _JXTextField();
										pnlReceiptNo.add(txtReceiptNo, BorderLayout.CENTER);
										txtReceiptNo.setEditable(true);
										txtReceiptNo.setFont(font11);
										txtReceiptNo.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {

											}

											public void keyReleased(KeyEvent e) {
												try {
													int intReceipt = Integer.parseInt(txtReceiptNo.getText().toString());  

													if (txtReceiptNo.getText().toString().length()>6) {
														txtReceiptNo.setText(txtReceiptNo.getText().substring(0, 6));
													}

													txtReceiptNo.setText(String.format("%06d", intReceipt));
												} catch (NumberFormatException ex) {

												} finally {
													txtReceiptNo.setText(txtReceiptNo.getText().substring(0, 6));
												}

											}

											public void keyPressed(KeyEvent e) {

											}
										});
									}
								}
								{
									JPanel pnlAccountNo = new JPanel(new BorderLayout(3, 3));
									pnlCol1_1.add(pnlAccountNo);
									{
										JLabel lblAccountNo = new JLabel("*Account No.");
										pnlAccountNo.add(lblAccountNo, BorderLayout.WEST);
										lblAccountNo.setPreferredSize(dimWest);
										lblAccountNo.setFont(font11);
									}
									{
										txtAccountNo = new _JXTextField();
										pnlAccountNo.add(txtAccountNo, BorderLayout.CENTER);
										txtAccountNo.setEditable(true);
										txtAccountNo.setFont(font11);
									}
								}
								{
									JPanel pnlCheckNo = new JPanel(new BorderLayout(3, 3));
									pnlCol1_1.add(pnlCheckNo);
									{
										JLabel lblCheckNo = new JLabel("*Check No.");
										pnlCheckNo.add(lblCheckNo, BorderLayout.WEST);
										lblCheckNo.setPreferredSize(dimWest);
										lblCheckNo.setFont(font11);
									}
									{
										txtCheckNo = new _JXTextField();
										pnlCheckNo.add(txtCheckNo, BorderLayout.CENTER);
										txtCheckNo.setEditable(true);
										txtCheckNo.setFont(font11);
									}
								}
								{
									JPanel pnlCheckDate = new JPanel(new BorderLayout(3, 3));
									pnlCol1_1.add(pnlCheckDate);
									{
										JLabel lblCheckNo = new JLabel("*Check Date");
										pnlCheckDate.add(lblCheckNo, BorderLayout.WEST);
										lblCheckNo.setPreferredSize(dimWest);
										lblCheckNo.setFont(font11);
									}
									{
										dateCheckDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlCheckDate.add(dateCheckDate, BorderLayout.CENTER);
										dateCheckDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										((JTextField)dateCheckDate.getDateEditor()).setFont(font11);
									}
								}
								{
									JPanel pnlBank = new JPanel(new BorderLayout(3, 3));
									pnlCol1_1.add(pnlBank);
									{
										JLabel lblBank = new JLabel("*Bank");
										pnlBank.add(lblBank, BorderLayout.WEST);
										lblBank.setPreferredSize(dimWest);
										lblBank.setFont(font11);
									}
									{
										JPanel pnlBankCenter = new JPanel(new BorderLayout(3, 3));
										pnlBank.add(pnlBankCenter, BorderLayout.CENTER);
										{
											lookupBank = new _JLookup(null, "Bank");
											pnlBankCenter.add(lookupBank, BorderLayout.WEST);
											lookupBank.setFilterName(true);
											lookupBank.setReturnColumn(0);
											lookupBank.setFont(font11);
											lookupBank.setLookupSQL(_OrderOfPayment.getBank());
											lookupBank.setPreferredSize(new Dimension(35, 0));
											lookupBank.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){
														txtBank.setText((String) data[1]);
														txtBank.setToolTipText((String) data[1]);
														txtBank.setCaretPosition(0);

														lookupBranch.setLookupSQL(_OrderOfPayment.getBankBranch((String) data[0]));
													}
												}
											});
										}
										{
											txtBank = new _JXTextField();
											pnlBankCenter.add(txtBank, BorderLayout.CENTER);
											txtBank.setFont(font11);
										}
									}
								}
								{
									JPanel pnlBranch = new JPanel(new BorderLayout(3, 3));
									pnlCol1_1.add(pnlBranch);
									{
										JLabel lblBranch = new JLabel("*Branch");
										pnlBranch.add(lblBranch, BorderLayout.WEST);
										lblBranch.setPreferredSize(dimWest);
										lblBranch.setFont(font11);
									}
									{
										JPanel pnlBranchCenter = new JPanel(new BorderLayout(3, 3));
										pnlBranch.add(pnlBranchCenter, BorderLayout.CENTER);
										{
											lookupBranch = new _JLookup(null, "Branch", 0, "Please select Bank first.");
											pnlBranchCenter.add(lookupBranch, BorderLayout.WEST);
											lookupBranch.setFilterName(true);
											lookupBranch.setFont(font11);
											lookupBranch.setPreferredSize(new Dimension(35, 0));
											lookupBranch.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){
														txtBranch.setText((String) data[1]);
														txtBranch.setToolTipText((String) data[1]);
														txtBranch.setCaretPosition(0);
													}
												}
											});
										}
										{
											txtBranch = new _JXTextField();
											pnlBranchCenter.add(txtBranch, BorderLayout.CENTER);
											txtBranch.setFont(font11);
										}
									}
								}
							}
							{
								JPanel pnlNetProceeds = new JPanel(new BorderLayout(3, 3));
								pnlCol0.add(pnlNetProceeds, BorderLayout.SOUTH);
								pnlNetProceeds.setPreferredSize(new Dimension(0, 80));
								pnlNetProceeds.setBorder(JTBorderFactory.createTitleBorder("Net Proceeds", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										JLabel lblNetProceeds = new JLabel("NEW PROCEEDS", JLabel.CENTER);
										lblNetProceeds.setFont(FncLookAndFeel.systemFont_Bold.deriveFont(15f));
									}
									{
										txtNetProceeds = new _JXFormattedTextField(JXFormattedTextField.CENTER);
										pnlNetProceeds.add(txtNetProceeds, BorderLayout.CENTER);
										txtNetProceeds.setFont(FncLookAndFeel.systemFont_Bold.deriveFont(20f));
										txtNetProceeds.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtNetProceeds.setEditable(false);
									}	
								}
							}	
						}
					}
					{
						JPanel pnlCol2 = new JPanel(new BorderLayout(3, 3));
						pnlCenter.add(pnlCol2);
						//pnlCol2.setBorder(components.JTBorderFactory.createTitleBorder("Recapitulation"));
						pnlCol2.setBorder(JTBorderFactory.createTitleBorder("Recapitulation", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							GridLayout grid = new GridLayout(14, 1, 3, 3);
							JPanel pnlLabel = new JPanel(grid);
							pnlCol2.add(pnlLabel, BorderLayout.WEST);
							{
								{
									JLabel lblLoanableAmount = new JLabel("Loanable Amount (Gross of VAT)");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblVAT = new JLabel("VAT on O/S");
									pnlLabel.add(lblVAT);
									lblVAT.setFont(font11);
								}
								{
									JLabel lblLoanableAmountNetVAT = new JLabel("Loanable Amount (Net of VAT)");
									pnlLabel.add(lblLoanableAmountNetVAT);
									lblLoanableAmountNetVAT.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("Less: SRI & Doc Stamps");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("         Fire");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("         Processing Fee");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("         Appraisal Fee");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("         Interim MRI");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("         Retention Fee");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("         1st MA");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("         Refiling Fee");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
								{
									JLabel lblServiceFee = new JLabel("         Service Fee");
									pnlLabel.add(lblServiceFee);
									lblServiceFee.setFont(font11);
								}
								{
									JLabel lblLoanableAmount = new JLabel("         10% Retention");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
									lblLoanableAmount.setVisible(false);
								}

								{
									JLabel lblLoanableAmount = new JLabel("");
									pnlLabel.add(lblLoanableAmount);
									lblLoanableAmount.setFont(font11);
								}
							}

							JPanel pnlFields = new JPanel(grid);
							pnlCol2.add(pnlFields, BorderLayout.CENTER);
							{
								{
									txtLoanableAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtLoanableAmount);
									txtLoanableAmount.setActionCommand("Recapitulation");
									txtLoanableAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtLoanableAmount.setEditable(false);
									txtLoanableAmount.setFont(font11);
								}
								{
									txtVAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtVAT);
									txtVAT.setActionCommand("Recapitulation");
									txtVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtVAT.setEditable(false);
									txtVAT.setFont(font11);
								}
								{
									txtLoanableAmountNetVAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtLoanableAmountNetVAT);
									txtLoanableAmountNetVAT.setActionCommand("Recapitulation");
									txtLoanableAmountNetVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtLoanableAmountNetVAT.setEditable(false);
									txtLoanableAmountNetVAT.setFont(font11);
								}
								{
									txtSRIDocStamps = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtSRIDocStamps);
									txtSRIDocStamps.setActionCommand("Recapitulation");
									txtSRIDocStamps.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtSRIDocStamps.setFont(font11);
									txtSRIDocStamps.addKeyListener(this);
								}
								{
									txtFire = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtFire);
									txtFire.setActionCommand("Recapitulation");
									txtFire.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtFire.setFont(font11);
									txtFire.addKeyListener(this);
								}
								{
									txtProcessingFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtProcessingFee);
									txtProcessingFee.setActionCommand("Recapitulation");
									txtProcessingFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtProcessingFee.setEditable(false);
								}
								{
									txtAppraisalFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtAppraisalFee);
									txtAppraisalFee.setActionCommand("Recapitulation");
									txtAppraisalFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtAppraisalFee.setFont(font11);
									txtAppraisalFee.addKeyListener(this);
								}
								{
									txtInterimMRI = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtInterimMRI);
									txtInterimMRI.setActionCommand("Recapitulation");
									txtInterimMRI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtInterimMRI.setFont(font11);
									txtInterimMRI.addKeyListener(this);
								}
								{
									JXPanel panPctgAmt = new JXPanel(new BorderLayout(3, 3)); 
									pnlFields.add(panPctgAmt);
									{
										{
											txtPctgRetentionFee = new JTextField("0.00");
											panPctgAmt.add(txtPctgRetentionFee, BorderLayout.LINE_START);
											txtPctgRetentionFee.setEditable(true);
											txtPctgRetentionFee.setFont(font11);
											txtPctgRetentionFee.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetentionFee.setPreferredSize(new Dimension(40, 0));
											txtPctgRetentionFee.getDocument().addDocumentListener(new DocumentListener() {
												public void removeUpdate(DocumentEvent e) {
													ComputeRetention(); 
												}

												public void insertUpdate(DocumentEvent e) {
													ComputeRetention(); 
												}

												public void changedUpdate(DocumentEvent e) {
													ComputeRetention(); 
												}

												private void ComputeRetention() {
													BigDecimal dbRetention = new BigDecimal("0.00");
													BigDecimal dbPercentage = new BigDecimal("0.00");

													try {
														dbPercentage = new BigDecimal(txtPctgRetentionFee.getText());
													} catch (Exception ex) {
														dbPercentage = new BigDecimal("0.00");
													}

													dbPercentage = dbPercentage.divide(new BigDecimal("100"));

													BigDecimal dbLoanAmt = new BigDecimal("0.00");

													//													// COMMENTED AND MODIFIED BY MONIQUE DTD 07-06-2023 REFER TO DCRF#2656
													//													if (((BigDecimal) txtLoanableAmountNetVAT.getValue()).compareTo(new BigDecimal("0.00" ))==1) {
													//														dbLoanAmt = new BigDecimal(txtLoanableAmountNetVAT.getValue().toString()); 
													//													} else {
													//														dbLoanAmt = new BigDecimal(txtLoanableAmount.getValue().toString());
													//													}

													dbLoanAmt = new BigDecimal(txtLoanableAmount.getValue().toString()); //DCRF#2656

													dbRetention = dbLoanAmt.multiply(dbPercentage); 
													txtRetentionFee.setValue(dbRetention);
												}
											});
										}
										{
											txtRetentionFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											panPctgAmt.add(txtRetentionFee, BorderLayout.CENTER);
											txtRetentionFee.setActionCommand("Recapitulation");
											txtRetentionFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetentionFee.setEditable(false);
											txtRetentionFee.setFont(font11);
										}		
									}
								}
								{
									txt1stMA = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txt1stMA);
									txt1stMA.setActionCommand("Recapitulation");
									txt1stMA.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txt1stMA.setFont(font11);
									txt1stMA.addKeyListener(this);
								}
								{
									txtRefilingFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtRefilingFee);
									txtRefilingFee.setActionCommand("Recapitulation");
									txtRefilingFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtRefilingFee.setFont(font11);
									txtRefilingFee.addKeyListener(this);
								}
								{
									txtServiceFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txtServiceFee);
									txtServiceFee.setActionCommand("Recapitulation");
									txtServiceFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtServiceFee.setFont(font11);
									txtServiceFee.addKeyListener(this);
								}
								{
									txt10Retention = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlFields.add(txt10Retention);
									txt10Retention.setActionCommand("Recapitulation");
									txt10Retention.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txt10Retention.setFont(font11);
									txt10Retention.addKeyListener(this);
									txt10Retention.setVisible(false);
								}	
							}
						}
					}
					{
						JPanel pnlCol3 = new JPanel(new BorderLayout(3, 3));
						pnlCenter.add(pnlCol3);
						{
							{
								JXPanel panCol3Fields = new JXPanel(new BorderLayout(3, 3)); 
								pnlCol3.add(panCol3Fields, BorderLayout.PAGE_START);
								panCol3Fields.setPreferredSize(new Dimension(0, 280));
								panCol3Fields.setBorder(JTBorderFactory.createTitleBorder("Retentions", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										JPanel pnlChecks = new JPanel(grid);
										panCol3Fields.add(pnlChecks, BorderLayout.WEST);
										pnlChecks.setPreferredSize(new Dimension(130, 0));
										{
											chkEPEB = new JCheckBox("EPEB");
											pnlChecks.add(chkEPEB);
											chkEPEB.setFont(font11);
										}
										{
											chkETD = new JCheckBox("ETD");
											pnlChecks.add(chkETD);
											chkETD.setFont(font11);
										}
										{
											chkCOC = new JCheckBox("COC");
											pnlChecks.add(chkCOC);
											chkCOC.setFont(font11);
										}
										{
											chkMERALCO = new JCheckBox("MERALCO");
											pnlChecks.add(chkMERALCO);
											chkMERALCO.setFont(font11);
										}
										{
											chkCOMFAC5 = new JCheckBox("COMFAC");
											pnlChecks.add(chkCOMFAC5);
											chkCOMFAC5.setFont(font11);
										}
										{
											chkRightOfWay = new JCheckBox("Right Of Way");
											pnlChecks.add(chkRightOfWay);
											chkRightOfWay.setFont(font11);
										}
										{
											chkWaterSupply = new JCheckBox("Water Supply");
											pnlChecks.add(chkWaterSupply);
											chkWaterSupply.setFont(font11);
										}
										{
											chkTaxDec = new JCheckBox("Tax Declaration");
											pnlChecks.add(chkTaxDec);
											chkTaxDec.setFont(font11);
										}
										{
											JLabel lblTotal = new JLabel("TOTAL: ", JLabel.TRAILING);
											pnlChecks.add(lblTotal);
											lblTotal.setFont(font11.deriveFont(Font.BOLD));
										}
									}
									{
										JPanel pnlFields = new JPanel(grid);
										panCol3Fields.add(pnlFields, BorderLayout.CENTER);
										{
											txtEPEB = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlFields.add(txtEPEB);
											txtEPEB.setActionCommand("Retention");
											txtEPEB.setFont(font11);
											txtEPEB.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtEPEB.setEditable(false);
											addItemListener(chkEPEB, txtEPEB);
										}
										{
											txtETD = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlFields.add(txtETD);
											txtETD.setActionCommand("Retention");
											txtETD.setFont(font11);
											txtETD.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtETD.setEditable(false);
											addItemListener(chkETD, txtETD);
										}
										{
											txtCOC = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlFields.add(txtCOC);
											txtCOC.setActionCommand("Retention");
											txtCOC.setFont(font11);
											txtCOC.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtCOC.setEditable(false);
											addItemListener(chkCOC, txtCOC);
										}
										{
											txtMERALCO = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlFields.add(txtMERALCO);
											txtMERALCO.setActionCommand("Retention");
											txtMERALCO.setFont(font11);
											txtMERALCO.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtMERALCO.setEditable(false);
											addItemListener(chkMERALCO, txtMERALCO);
										}
										{
											JXPanel panCommFac = new JXPanel(new BorderLayout(3, 3)); 
											pnlFields.add(panCommFac); 
											{
												{
													txtCOMFACpctg = new JTextField("0.00");
													panCommFac.add(txtCOMFACpctg, BorderLayout.LINE_START);
													txtCOMFACpctg.setFont(font11);
													txtCOMFACpctg.setEditable(false);
													txtCOMFACpctg.setPreferredSize(new Dimension(40, 0));
													txtCOMFACpctg.setHorizontalAlignment(JTextField.CENTER);
													txtCOMFACpctg.getDocument().addDocumentListener(new DocumentListener() {
														public void removeUpdate(DocumentEvent e) {
															ComputeCommfac(); 
														}

														public void insertUpdate(DocumentEvent e) {
															ComputeCommfac(); 
														}

														public void changedUpdate(DocumentEvent e) {
															ComputeCommfac(); 
														}

														private void ComputeCommfac() {
															BigDecimal dbCommFac = new BigDecimal("0.00");
															BigDecimal dbPercentage = new BigDecimal("0.00");

															try {
																dbPercentage = new BigDecimal(txtCOMFACpctg.getText());
															} catch (Exception ex) {
																dbPercentage = new BigDecimal("0.00");
															}

															dbPercentage = dbPercentage.divide(new BigDecimal("100"));

															BigDecimal dbLoanAmt = new BigDecimal("0.00"); 

															if (((BigDecimal) txtLoanableAmountNetVAT.getValue()).compareTo(new BigDecimal("0.00" ))==1) {
																dbLoanAmt = new BigDecimal(txtLoanableAmountNetVAT.getValue().toString()); 
															} else {
																dbLoanAmt = new BigDecimal(txtLoanableAmount.getValue().toString());
															}

															dbCommFac = dbLoanAmt.multiply(dbPercentage); 

															txtCOMFAC5.setValue(dbCommFac);
														}
													});
												}
												{
													txtCOMFAC5 = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
													panCommFac.add(txtCOMFAC5, BorderLayout.CENTER);
													txtCOMFAC5.setActionCommand("Retention");
													txtCOMFAC5.setFont(font11);
													txtCOMFAC5.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtCOMFAC5.setEditable(false);
													addItemListener(chkCOMFAC5, txtCOMFACpctg);
												}
											}
										}
										{
											txtRightOfWay = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlFields.add(txtRightOfWay);
											txtRightOfWay.setActionCommand("Retention");
											txtRightOfWay.setFont(font11);
											txtRightOfWay.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRightOfWay.setEditable(false);
											addItemListener(chkRightOfWay, txtRightOfWay);
											txtRightOfWay.addKeyListener(new KeyAdapter() {
												public void keyReleased(KeyEvent arg0) {
													if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
														computeRetentions();
													}
												}
											});
										}
										{
											JXPanel panWaterSupply = new JXPanel(new BorderLayout(3, 3)); 
											pnlFields.add(panWaterSupply); 
											{
												{
													txtWaterSupplypctg = new JTextField("0.00");
													panWaterSupply.add(txtWaterSupplypctg, BorderLayout.LINE_START);
													txtWaterSupplypctg.setFont(font11);
													txtWaterSupplypctg.setEditable(false);
													txtWaterSupplypctg.setPreferredSize(new Dimension(40, 0));
													txtWaterSupplypctg.setHorizontalAlignment(JTextField.CENTER);
													txtWaterSupplypctg.getDocument().addDocumentListener(new DocumentListener() {
														public void removeUpdate(DocumentEvent e) {
															ComputeWaterSupply(); 
														}

														public void insertUpdate(DocumentEvent e) {
															ComputeWaterSupply(); 
														}

														public void changedUpdate(DocumentEvent e) {
															ComputeWaterSupply(); 
														}

														private void ComputeWaterSupply() {
															BigDecimal dbWaterSupply = new BigDecimal("0.00");
															BigDecimal dbPercentage = new BigDecimal("0.00");

															try {
																dbPercentage = new BigDecimal(txtWaterSupplypctg.getText());
															} catch (Exception ex) {
																dbPercentage = new BigDecimal("0.00");
															}

															dbPercentage = dbPercentage.divide(new BigDecimal("100"));

															BigDecimal dbLoanAmt = new BigDecimal("0.00"); 

															if (((BigDecimal) txtLoanableAmountNetVAT.getValue()).compareTo(new BigDecimal("0.00" ))==1) {
																dbLoanAmt = new BigDecimal(txtLoanableAmountNetVAT.getValue().toString()); 
															} else {
																dbLoanAmt = new BigDecimal(txtLoanableAmount.getValue().toString());
															}

															dbWaterSupply = dbLoanAmt.multiply(dbPercentage); 

															txtWaterSupply.setValue(dbWaterSupply);
														}
													});
												}
												{
													txtWaterSupply = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
													panWaterSupply.add(txtWaterSupply, BorderLayout.CENTER);
													txtWaterSupply.setActionCommand("Retention");
													txtWaterSupply.setFont(font11);
													txtWaterSupply.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtWaterSupply.setEditable(false);
													addItemListener(chkWaterSupply, txtWaterSupplypctg);
												}
											}
										}
										{
											JXPanel panTaxDec = new JXPanel(new BorderLayout(3, 3)); 
											pnlFields.add(panTaxDec); 
											{
												{
													txtTaxDecpctg = new JTextField("0.00");
													panTaxDec.add(txtTaxDecpctg, BorderLayout.LINE_START);
													txtTaxDecpctg.setFont(font11);
													txtTaxDecpctg.setEditable(false);
													txtTaxDecpctg.setPreferredSize(new Dimension(40, 0));
													txtTaxDecpctg.setHorizontalAlignment(JTextField.CENTER);
													txtTaxDecpctg.getDocument().addDocumentListener(new DocumentListener() {
														public void removeUpdate(DocumentEvent e) {
															ComputeTaxDec(); 
														}

														public void insertUpdate(DocumentEvent e) {
															ComputeTaxDec(); 
														}

														public void changedUpdate(DocumentEvent e) {
															ComputeTaxDec(); 
														}

														private void ComputeTaxDec() {
															BigDecimal dbTaxDec = new BigDecimal("0.00");
															BigDecimal dbPercentage = new BigDecimal("0.00");

															try {
																dbPercentage = new BigDecimal(txtTaxDecpctg.getText());
															} catch (Exception ex) {
																dbPercentage = new BigDecimal("0.00");
															}

															dbPercentage = dbPercentage.divide(new BigDecimal("100"));

															BigDecimal dbLoanAmt = new BigDecimal("0.00"); 

															if (((BigDecimal) txtLoanableAmountNetVAT.getValue()).compareTo(new BigDecimal("0.00" ))==1) {
																dbLoanAmt = new BigDecimal(txtLoanableAmountNetVAT.getValue().toString()); 
															} else {
																dbLoanAmt = new BigDecimal(txtLoanableAmount.getValue().toString());
															}

															dbTaxDec = dbLoanAmt.multiply(dbPercentage); 

															txtTaxDec.setValue(dbTaxDec);
														}
													});
												}
												{
													txtTaxDec = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
													panTaxDec.add(txtTaxDec, BorderLayout.CENTER);
													txtTaxDec.setActionCommand("Retention");
													txtTaxDec.setFont(font11);
													txtTaxDec.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtTaxDec.setEditable(false);
													addItemListener(chkTaxDec, txtTaxDecpctg);
												}
											}
										}
										{
											txtTotal = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlFields.add(txtTotal);
											txtTotal.setFont(font11);
											txtTotal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtTotal.setEditable(false);
										}
									}
								}
							}
							{
								JXPanel panCenter = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								pnlCol3.add(panCenter, BorderLayout.CENTER);
								{
									{
										JXPanel panProceeds = new JXPanel(new BorderLayout(5, 5)); 
										panCenter.add(panProceeds); 
										panProceeds.setBorder(JTBorderFactory.createTitleBorder("Net Proceeds", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											txtNetProceeds = new _JXFormattedTextField(JXFormattedTextField.CENTER);
											panProceeds.add(txtNetProceeds, BorderLayout.CENTER);
											txtNetProceeds.setFont(FncLookAndFeel.systemFont_Bold.deriveFont(20f));
											txtNetProceeds.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtNetProceeds.setEditable(false);
										}
									}
									{
										JXPanel panButtons = new JXPanel(new GridLayout(2, 2, 1, 1)); 
										panCenter.add(panButtons); 
										{
											{
												btnNew = new JButton("New");
												panButtons.add(btnNew);
												btnNew.addActionListener(this);
											}
											{
												btnEdit = new JButton("Edit");
												panButtons.add(btnEdit);
												btnEdit.addActionListener(this);
											}
											{
												btnSave = new JButton("Save");
												panButtons.add(btnSave);
												btnSave.addActionListener(this);
											}
											{
												btnCancel = new JButton("Cancel");
												panButtons.add(btnCancel);
												btnCancel.addActionListener(this);
											}
										}	
									}
								}
							}
						}
					}
				}
			}
			{
				JXPanel pnlDetail = new JXPanel(new BorderLayout());
				pnlMain.add(pnlDetail, BorderLayout.CENTER);
				{
					scrollPaymentList = new _JScrollPaneMain();
					pnlDetail.add(scrollPaymentList, BorderLayout.CENTER);
					scrollPaymentList.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblPaymentList.clearSelection();
							try {
								tblPaymentList.getCellEditor().stopCellEditing();
							} catch (NullPointerException e1) { }

						}
					});
					{
						modelPaymentList = new modelLoanReleased();
						modelPaymentList.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								if(e.getType() == TableModelEvent.INSERT){
									((DefaultListModel)rowHeaderPaymentList.getModel()).addElement(modelPaymentList.getRowCount());
									scrollPaymentListTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPaymentList.getRowCount())));
								}
								if(e.getType() == TableModelEvent.DELETE){
									if(modelPaymentList.getRowCount() == 0){
										rowHeaderPaymentList.setModel(new DefaultListModel());
										scrollPaymentListTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPaymentList.getRowCount())));
									}
								}
							}
						});
						{
							tblPaymentList = new _JTableMain(modelPaymentList);
							scrollPaymentList.setViewportView(tblPaymentList);
							tblPaymentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if(!e.getValueIsAdjusting()){
										try {
											Integer row = tblPaymentList.convertRowIndexToModel(tblPaymentList.getSelectedRow());
											String receipt_no = (String) modelPaymentList.getValueAt(row, 0);
											displayPaymentDetails(receipt_no);
										} catch (ArrayIndexOutOfBoundsException e1) {

										} catch (IndexOutOfBoundsException e2) {

										}
									}
								}
							});

							tblPaymentList.getColumnModel().getColumn(0).setPreferredWidth(100);
							tblPaymentList.getColumnModel().getColumn(1).setPreferredWidth(250);
							tblPaymentList.getColumnModel().getColumn(2).setPreferredWidth(100);
							tblPaymentList.getColumnModel().getColumn(3).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(4).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(5).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(6).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(7).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(8).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(9).setPreferredWidth(150);

							tblPaymentList.getColumnModel().getColumn(10).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(11).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(12).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(13).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(14).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(15).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(16).setPreferredWidth(150);
							tblPaymentList.getColumnModel().getColumn(17).setPreferredWidth(150);
						}
					}
					{
						rowHeaderPaymentList = tblPaymentList.getRowHeader();
						rowHeaderPaymentList.setModel(new DefaultListModel());
						scrollPaymentList.setRowHeaderView(rowHeaderPaymentList);
						scrollPaymentList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollPaymentListTotal = new _JScrollPaneTotal(scrollPaymentList);
					pnlDetail.add(scrollPaymentListTotal, BorderLayout.SOUTH);
					{
						modelPaymentListTotal = new modelLoanReleased();
						modelPaymentListTotal.addRow(new Object[] { null, null, null, "Total", 0.00, null, null, null, null, null, null, null, null, null, null });

						tblPaymentListTotal = new _JTableTotal(modelPaymentListTotal, tblPaymentList);

						scrollPaymentListTotal.setViewportView(tblPaymentListTotal);
						tblPaymentListTotal.setTotalLabel(3);
					}
				}
			}
		}

		displayTaggedLoanReleased();
		FncTables.bindColumnTables(tblPaymentList, tblPaymentListTotal);
		tblPaymentList.hideColumns("Project");

		setComponentsEnabled(pnlNorth, false);
		btnState(true, false, false, false);
	}

	private String sqlClients() {
		return "SELECT * FROM jsearch WHERE EXISTS(SELECT * FROM rf_buyer_status " +
				"WHERE entity_id = \"Client ID\" AND proj_id = \"Project ID\" " +
				"AND pbl_id = \"PBL ID\" AND seq_no = \"Sequence\" AND byrstatus_id IN ('48', '144')" +
				"AND (pbl_id, seq_no) not in (select pbl_id, seq_no from rf_pagibig_lnrel where status_id = 'A'));";
	}

	private void displayTaggedLoanReleased() {
		modelPaymentList.clear();

		String SQL = "SELECT a.or_no, b.proj_name, get_client_name(a.entity_id) as client_name, c.description, \n" + 
				"a.net_proceeds, a.loanable_amt, coalesce(a.sri_doc_stamps, 0) as sri_doc_stamps, coalesce(a.fire, 0.00) as fire, coalescE(a.proc_fee, 0.00) as proc_fee, \n" + 
				"coalesce(a.appraisal_fee, 0.00) as appraisal_fee, coalesce(a.interim_mri, 0.00) as interim_mri, coalesce(a.retention_fee, 0.00) as retention_fee, coalescE(a.first_ma, 0.00) as first_ma,\n" + 
				"coalesce(a.refiling_fee, 0.00) as refiling_fee, coalesce(a.ret_10_percent, 0.00) as ret_10_percent, coalesce(a.rightofway, 0.00) as rightofway, null, null, coalesce(a.service_fee, 0)\n" + 
				"FROM rf_pagibig_lnrel a \n" + 
				"LEFT JOIN mf_project b ON b.proj_id = a.proj_id \n" + 
				"LEFT JOIN mf_unit_info c ON c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id AND c.status_id != 'I' \n" + 
				"ORDER BY a.trans_date, client_name, c.description;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelPaymentList.addRow(db.getResult()[x]);
			}
			tblPaymentList.packAll();
		}
		computeTotalNetProceeds();
	}


	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void addItemListener(JCheckBox check, final _JXFormattedTextField textfield) {
		check.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				textfield.setEditable(e.getStateChange() == ItemEvent.SELECTED);
				textfield.requestFocus();

				System.out.println(textfield.getName());

				/*
				if (textfield.getName().equals("txtCOMFAC5")) {
					txtCOMFACpctg.setEditable(e.getStateChange() == ItemEvent.SELECTED);
				}
				 */
			}
		});
	}

	private void addItemListener(JCheckBox check, final JTextField txtpctg) {
		check.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtpctg.setEditable(e.getStateChange() == ItemEvent.SELECTED);
				txtpctg.requestFocus();
			}
		});
	}


	private void displayClientDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) { 
		String SQL = "select * \n" + 
				"from view_loanreleased_client_list a \n" + 
				"WHERE a.entity_id = '"+ entity_id +"' AND a.projcode = '"+ proj_id +"' " +
				"AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +"; ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			txtClientName.setText((String) db.getResult()[0][1]);
			txtProjectID.setText((String) db.getResult()[0][2]);
			txtProjectName.setText((String) db.getResult()[0][3]);
			txtPblID.setText((String) db.getResult()[0][4]);
			txtUnitDescription.setText(String.format("%s   /   %s", db.getResult()[0][5], db.getResult()[0][6]));
			txtTermRate.setText(String.format("%s   /   %s", Integer.toString((Integer) db.getResult()[0][7]), ((BigDecimal) db.getResult()[0][8]).toString()));
			txtLoanableAmount.setValue((BigDecimal) db.getResult()[0][9]);
			txtVAT.setValue((BigDecimal) db.getResult()[0][12]);
			txtLoanableAmountNetVAT.setValue((BigDecimal) db.getResult()[0][13]);

			txtProcessingFee.setValue((BigDecimal) db.getResult()[0][10]);
			txtRetentionFee.setValue((BigDecimal) db.getResult()[0][11]);

			computeNetProceeds();
		}
	}

	private void displayPaymentDetails(String receipt_no) {
		BigDecimal bdPctg = new BigDecimal("0.00"); 

		clearFields();
		/*
		String SQL = "SELECT a.entity_id, get_client_name(a.entity_id), a.proj_id, b.proj_name, a.pbl_id, c.description, a.seq_no, a.term, a.rate, a.actual_date, a.client_seqno, a.or_no, a.acct_no,\n" + 
				"  a.check_no, a.check_date, a.bank_id, d.bank_name, a.branch_id, e.bank_branch_location, NULLIF(a.net_proceeds, 0), NULLIF(a.loanable_amt, 0), NULLIF(a.sri_doc_stamps, 0),\n" + 
				"  NULLIF(a.fire, 0), NULLIF(a.proc_fee, 0), NULLIF(a.appraisal_fee, 0), NULLIF(a.interim_mri, 0), NULLIF(a.retention_fee, 0), NULLIF(a.first_ma, 0), NULLIF(a.refiling_fee, 0),\n" + 
				"  NULLIF(a.ret_10_percent, 0), NULLIF(a.epeb, 0), NULLIF(a.etd, 0), NULLIF(a.coc, 0), NULLIF(a.merl, 0), NULLIF(a.com3, 0), NULLIF(a.com5, 0), a.* \n" + 
				"FROM rf_pagibig_lnrel a\n" + 
				"LEFT JOIN mf_project b ON b.proj_id = a.proj_id\n" + 
				"LEFT JOIN mf_unit_info c ON c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_bank d ON d.bank_id = a.bank_id\n" + 
				"LEFT JOIN mf_bank_branch e ON e.bank_branch_id = a.branch_id\n" + 
				"WHERE a.or_no = '"+ receipt_no +"';";
		 */

		String SQL = "SELECT a.entity_id, get_client_name(a.entity_id), a.proj_id, b.proj_name, a.pbl_id, c.description, a.seq_no, a.term, a.rate, a.actual_date, a.client_seqno, a.or_no, a.acct_no,\n" + 
				"a.check_no, a.check_date, a.bank_id, d.bank_name, a.branch_id, e.bank_branch_location, COALESCE(a.net_proceeds, 0.00), COALESCE(a.loanable_amt, 0.00), COALESCE(a.sri_doc_stamps, 0.00),\n" + 
				"COALESCE(a.fire, 0.00), COALESCE(a.proc_fee, 0.00), COALESCE(a.appraisal_fee, 0.00), COALESCE(a.interim_mri, 0.00), COALESCE(a.retention_fee, 0.00), COALESCE(a.first_ma, 0.00), \n" + 
				"COALESCE(a.refiling_fee, 0.00), COALESCE(a.ret_10_percent, 0.00), COALESCE(a.epeb, 0.00), COALESCE(a.etd, 0.00), COALESCE(a.coc, 0.00), COALESCE(a.merl, 0.00), COALESCE(a.com3, 0.00), \n" + 
				"COALESCE(a.com5, 0.00), COALESCE(a.rightofway, 0.00), COALESCE(a.water_supply, 0.00), COALESCE(a.tax_dec, 0.00), coalesce(a.service_fee, 0.00) \n" + 
				"FROM rf_pagibig_lnrel a\n" + 
				"LEFT JOIN mf_project b ON b.proj_id = a.proj_id\n" + 
				"LEFT JOIN mf_unit_info c ON c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_bank d ON d.bank_id = a.bank_id\n" + 
				"LEFT JOIN mf_bank_branch e ON e.bank_branch_id = a.branch_id\n" + 
				"WHERE a.or_no = '"+ receipt_no +"' and a.status_id = 'A';";

		System.out.println("");
		System.out.println("Display Payment Details: " + SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		System.out.println("db.isNotNull(): " + db.isNotNull()); 

		if(db.isNotNull()){
			lookupClient.setValue((String) db.getResult()[0][0]);
			txtClientName.setText((String) db.getResult()[0][1]);
			txtProjectID.setText((String) db.getResult()[0][2]);
			txtProjectName.setText((String) db.getResult()[0][3]);
			txtPblID.setText((String) db.getResult()[0][4]);
			txtUnitDescription.setText(String.format("%s   /   %s", db.getResult()[0][5], db.getResult()[0][6]));
			txtTermRate.setText(String.format("%s   /   %s", Integer.toString((Integer) db.getResult()[0][7]), ((BigDecimal) db.getResult()[0][8]).toString()));
			dateActualDate.setDate((Date) db.getResult()[0][9]);
			txtSequenceNo.setText((String) db.getResult()[0][10]);
			txtReceiptNo.setText((String) db.getResult()[0][11]);
			txtAccountNo.setText((String) db.getResult()[0][12]);
			txtCheckNo.setText((String) db.getResult()[0][13]);
			dateCheckDate.setDate((Date) db.getResult()[0][14]);
			lookupBank.setValue((String) db.getResult()[0][15]);
			txtBank.setText((String) db.getResult()[0][16]);
			lookupBranch.setValue((String) db.getResult()[0][17]);
			txtBranch.setText((String) db.getResult()[0][18]);

			txtNetProceeds.setValue(db.getResult()[0][19]);
			txtLoanableAmount.setValue(db.getResult()[0][20]);
			txtSRIDocStamps.setValue(db.getResult()[0][21]);
			txtFire.setValue(db.getResult()[0][22]);
			txtProcessingFee.setValue(db.getResult()[0][23]);
			txtAppraisalFee.setValue(db.getResult()[0][24]);
			txtInterimMRI.setValue(db.getResult()[0][25]);
			txtRetentionFee.setValue(db.getResult()[0][26]);
			txt1stMA.setValue(db.getResult()[0][27]);
			txtRefilingFee.setValue(db.getResult()[0][28]);
			txt10Retention.setValue(db.getResult()[0][29]);
			txtServiceFee.setValue(db.getResult()[0][30]);

			BigDecimal bdLoan = new BigDecimal(txtLoanableAmount.getValue().toString());

			bdPctg = FncGlobal.GetDecimal("select 100::numeric(19, 2) - ((('"+bdLoan+"'::numeric(19, 2) - '"+txtRetentionFee.getValue().toString()+"'::numeric(19, 2)) / '"+bdLoan+"'::numeric(19, 2)) * 100)::numeric(19, 2)");
			System.out.println("");
			System.out.println("bdPctg: " + bdPctg); 

			txtPctgRetentionFee.setText(bdPctg.toString());

			txtEPEB.setValue(db.getResult()[0][30]);
			txtETD.setValue(db.getResult()[0][31]);
			txtCOC.setValue(db.getResult()[0][32]);
			txtMERALCO.setValue(db.getResult()[0][33]);
			txtCOMFAC5.setValue(db.getResult()[0][35]);
			txtRightOfWay.setValue(db.getResult()[0][36]);
			txtWaterSupply.setValue(db.getResult()[0][37]);
			txtTaxDec.setValue(db.getResult()[0][38]);

			bdPctg = FncGlobal.GetDecimal("select 100::numeric(19, 2) - ((('"+bdLoan+"'::numeric(19, 2) - '"+txtCOMFAC5.getValue().toString()+"'::numeric(19, 2)) / '"+bdLoan+"'::numeric(19, 2)) * 100)::numeric(19, 2)");
			txtCOMFACpctg.setText(bdPctg.toString());

			/*	Water Supply	*/
			bdPctg = FncGlobal.GetDecimal("select 100::numeric(19, 2) - ((('"+bdLoan+"'::numeric(19, 2) - '"+txtWaterSupply.getValue().toString()+"'::numeric(19, 2)) / '"+bdLoan+"'::numeric(19, 2)) * 100)::numeric(19, 2)");
			txtWaterSupplypctg.setText(bdPctg.toString());

			/*	Tax Declaration	*/
			bdPctg = FncGlobal.GetDecimal("select 100::numeric(19, 2) - ((('"+bdLoan+"'::numeric(19, 2) - '"+txtTaxDec.getValue().toString()+"'::numeric(19, 2)) / '"+bdLoan+"'::numeric(19, 2)) * 100)::numeric(19, 2)");
			txtTaxDecpctg.setText(bdPctg.toString());

			BigDecimal bd = new BigDecimal((String) db.getResult()[0][30].toString()); 			
			BigDecimal sum = bd.add(new BigDecimal((String) db.getResult()[0][31].toString()));
			sum = sum.add(new BigDecimal((String) db.getResult()[0][32].toString()));
			sum = sum.add(new BigDecimal((String) db.getResult()[0][33].toString()));
			sum = sum.add(new BigDecimal((String) db.getResult()[0][35].toString()));
			sum = sum.add(new BigDecimal((String) db.getResult()[0][36].toString()));
			sum = sum.add(new BigDecimal((String) db.getResult()[0][37].toString()));
			sum = sum.add(new BigDecimal((String) db.getResult()[0][38].toString()));

			txtTotal.setValue(sum);

			System.out.println("");
			System.out.println("Sum: " + sum);

			btnState(true, true, false, false);
			setComponentsEnabled(pnlNorth, false);

		} else {
			btnState(false, false, false, true);
		}
	}

	private Object[] getReceiptType() {
		ArrayList<String> list = new ArrayList<String>();

		String SQL = "SELECT FORMAT('%s - %s', doc_id, doc_desc), * FROM mf_system_doc WHERE modules = 'CH' AND status_id = 'A';";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				list.add((String) db.getResult()[x][0]);
			}
		}

		return list.toArray();
	}

	private String getNewClientSeqNo() {
		pgSelect db = new pgSelect();
		db.select("SELECT get_new_client_seqno('"+ UserInfo.Branch +"', TRUE);");
		return (String) db.getResult()[0][0];
	}

	private void computeNetProceeds() {
		BigDecimal loanable_amount = (BigDecimal) txtLoanableAmount.getValued();
		BigDecimal sri_doc_stamps = (BigDecimal) txtSRIDocStamps.getValued();
		BigDecimal fire = (BigDecimal) txtFire.getValued();
		BigDecimal proc_fee = (BigDecimal) txtProcessingFee.getValued();
		BigDecimal appraisal_fee = (BigDecimal) txtAppraisalFee.getValued();
		BigDecimal interim_mri = (BigDecimal) txtInterimMRI.getValued();
		BigDecimal retention_fee = (BigDecimal) txtRetentionFee.getValued();
		BigDecimal first_ma = (BigDecimal) txt1stMA.getValued();
		BigDecimal refiling_fee = (BigDecimal) txtRefilingFee.getValued();
		BigDecimal ten_retention = (BigDecimal) txt10Retention.getValued();
		BigDecimal service_fee = (BigDecimal) txtServiceFee.getValued();

		BigDecimal bdEPEB = (BigDecimal) txtEPEB.getValued();
		BigDecimal bdETD = (BigDecimal) txtETD.getValued();
		BigDecimal bdCOC = (BigDecimal) txtCOC.getValued();
		BigDecimal bdMERALCO = (BigDecimal) txtMERALCO.getValued();
		BigDecimal bdCOMFAC = (BigDecimal) txtCOMFAC5.getValued();
		BigDecimal bdROW = (BigDecimal) txtRightOfWay.getValued();
		BigDecimal bdWS = (BigDecimal) txtWaterSupply.getValued();
		BigDecimal bdTD = (BigDecimal) txtTaxDec.getValued();

		if(sri_doc_stamps != null){
			loanable_amount = loanable_amount.subtract(sri_doc_stamps);
		}

		if(fire != null){
			loanable_amount = loanable_amount.subtract(fire);
		}

		if(proc_fee != null){
			loanable_amount = loanable_amount.subtract(proc_fee);
		}

		if(appraisal_fee != null){
			loanable_amount = loanable_amount.subtract(appraisal_fee);
		}

		if(interim_mri != null){
			loanable_amount = loanable_amount.subtract(interim_mri);
		}

		if(retention_fee != null){
			loanable_amount = loanable_amount.subtract(retention_fee);
		}

		if(first_ma != null){
			loanable_amount = loanable_amount.subtract(first_ma);
		}

		if(refiling_fee != null){
			loanable_amount = loanable_amount.subtract(refiling_fee);
		}

		if(ten_retention != null){
			loanable_amount = loanable_amount.subtract(ten_retention);
		}

		if(bdEPEB != null){
			loanable_amount = loanable_amount.subtract(bdEPEB);
		}

		if(bdETD != null){
			loanable_amount = loanable_amount.subtract(bdETD);
		}

		if(bdCOC != null){
			loanable_amount = loanable_amount.subtract(bdCOC);
		}

		if(bdMERALCO != null){
			loanable_amount = loanable_amount.subtract(bdMERALCO);
		}

		if(bdCOMFAC != null){
			loanable_amount = loanable_amount.subtract(bdCOMFAC);
		}

		if(bdROW != null){
			loanable_amount = loanable_amount.subtract(bdROW);
		}

		if(bdWS != null){
			loanable_amount = loanable_amount.subtract(bdWS);
		}

		if(bdTD != null){
			loanable_amount = loanable_amount.subtract(bdTD);
		}

		if(service_fee != null){
			loanable_amount = loanable_amount.subtract(service_fee);
		}

		txtNetProceeds.setValue(loanable_amount);
		System.out.println("");
		System.out.println("txtNetProceeds: " + loanable_amount);
	}

	private void computeRetentions() {
		BigDecimal total_retention = new BigDecimal("0.00");
		BigDecimal epeb = (BigDecimal) txtEPEB.getValued();
		BigDecimal etd = (BigDecimal) txtETD.getValued();
		BigDecimal coc = (BigDecimal) txtCOC.getValued();
		BigDecimal meralco = (BigDecimal) txtMERALCO.getValued();
		BigDecimal comfac = (BigDecimal) txtCOMFAC5.getValued();
		BigDecimal rightofway = (BigDecimal) txtRightOfWay.getValued();

		BigDecimal bdLoan = new BigDecimal(txtLoanableAmount.getValue().toString());
		BigDecimal bdPctg = new BigDecimal("0.00"); 

		bdPctg = FncGlobal.GetDecimal("select 100::numeric(19, 2) - ((('"+bdLoan+"'::numeric(19, 2) - '"+comfac+"'::numeric(19, 2)) / '"+bdLoan+"'::numeric(19, 2)) * 100)::numeric(19, 2)"); 
		txtCOMFACpctg.setText(bdPctg.toString());

		if (epeb != null) {
			total_retention = total_retention.add(epeb);
		}

		if (etd != null) {
			total_retention = total_retention.add(etd);
		}

		if (coc != null) {
			total_retention = total_retention.add(coc);
		}

		if (meralco != null) {
			total_retention = total_retention.add(meralco);
		}

		if (comfac != null) {
			total_retention = total_retention.add(comfac);
		}

		if (rightofway != null) {
			total_retention = total_retention.add(rightofway);
		}

		txtTotal.setValue(total_retention);
		txtRetentionFee.setValue(total_retention);
		computeNetProceeds();
	}

	private void cancel(){

		if(!btnSave.isEnabled())  {
			cancelTransactions();
		} else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				cancelTransactions();
			}}	

	}

	public void setComponentsEnabled(JPanel panel, boolean enable) {
		panelLooper(panel, enable);
	}

	public void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof _JDateChooser) {
				comp.setEnabled(enable);
			} else if (comp instanceof _JXFormattedTextField) {
				comp.setEnabled(enable);
			}else if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				panelLooper((JScrollPane) comp, enable);
			} else if (comp instanceof JButton) {

			} else {
				comp.setEnabled(enable);
			}
		}
	}

	private Boolean toSave() {
		if(lookupClient.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Client.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	private void newTransactions() {
		txtSequenceNo.setText("");
		clearFields();
		setComponentsEnabled(pnlNorth, true);
		btnState(false, false, true, true);
	}

	private void editTransactions() {
		//setComponentsEnabled(pnlNorth, true);
		setComponentsEnabled(pnlCol1, true);
		btnState(false, false, true, true);
	}

	private void saveTransaction() {

		//MODIFIED BY MONIQUE DTD 2023-08-23; DUE TO CREATION OF SEQUENCE WILL BE ON POSTING; REFER TO DCRF#2656

		/*			
		  if (txtSequenceNo.getText().equals("")) {
				txtSequenceNo.setText(getNewClientSeqNo());
					}
		 */

		txtSequenceNo.setText(""); 

		String entity_id = lookupClient.getValue();
		String proj_id = txtProjectID.getText();
		String pbl_id = txtPblID.getText();
		String seq_no = txtUnitDescription.getText().split("/")[1].trim();
		String term = txtTermRate.getText().split("/")[0].trim();
		String rate = txtTermRate.getText().split("/")[1].trim();
		Date actual_date = dateActualDate.getDate();
		String client_seqno = txtSequenceNo.getText();
		String receipt_no = txtReceiptNo.getText();
		String account_no = txtAccountNo.getText();
		String check_no = txtCheckNo.getText();
		Date check_date = dateCheckDate.getDate();
		String bank_id = lookupBank.getValue();
		String branch_id = lookupBranch.getValue();

		BigDecimal loanable_amount = null; 

		if (((BigDecimal) txtLoanableAmountNetVAT.getValue()).compareTo(new BigDecimal("0.00"))==1) {
			loanable_amount = (BigDecimal) txtLoanableAmountNetVAT.getValue();
		} else {
			loanable_amount = (BigDecimal) txtLoanableAmount.getValue();
		}

		System.out.println("");
		System.out.println("blnEdit: " + blnEdit);
		System.out.println("Count: " + FncGlobal.GetInteger("select count(*)::int from rf_payments where entity_id = '"+entity_id+"' and check_no = '"+check_no+"'"));

		if (blnEdit) {

			if (FncGlobal.GetInteger("select count(*)::int from rf_payments where entity_id = '"+entity_id+"' and check_no = '"+check_no+"'")==Integer.parseInt("0")) {
				ArrayList<BigDecimal> less = new ArrayList<BigDecimal>();
				less.add((BigDecimal) txtSRIDocStamps.getValued());
				less.add((BigDecimal) txtFire.getValued());
				less.add((BigDecimal) txtProcessingFee.getValued());
				less.add((BigDecimal) txtAppraisalFee.getValued());
				less.add((BigDecimal) txtInterimMRI.getValued());
				less.add((BigDecimal) txtRetentionFee.getValued());
				less.add((BigDecimal) txt1stMA.getValued());
				less.add((BigDecimal) txtRefilingFee.getValued());
				less.add((BigDecimal) txt10Retention.getValued());
				less.add((BigDecimal) txtServiceFee.getValued());

				ArrayList<BigDecimal> retentions = new ArrayList<BigDecimal>();
				retentions.add((BigDecimal) txtEPEB.getValued());
				retentions.add((BigDecimal) txtETD.getValued());
				retentions.add((BigDecimal) txtCOC.getValued());
				retentions.add((BigDecimal) txtMERALCO.getValued());
				retentions.add((BigDecimal) txtCOMFAC5.getValued());
				retentions.add((BigDecimal) txtRightOfWay.getValued());
				retentions.add((BigDecimal) txtWaterSupply.getValued());
				retentions.add((BigDecimal) txtTaxDec.getValued());

				BigDecimal net_proceeds = (BigDecimal) txtNetProceeds.getValue();

				try {
					//String SQL = "call sp_loanreleased_tagging_online(\n" +  //MODIFIED BY MONIQUE DTD 2023-08-23;
					String SQL = "call sp_loanreleased_tagging_online_v2(\n" + 
							"'"+entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", "+ term +", "+ rate +",\n" + 
							"'"+actual_date+"'::TIMESTAMP, '"+ client_seqno +"', \n" +
							loanable_amount +", ARRAY"+ less.toString() +"::NUMERIC[], " +
							"ARRAY"+ retentions +"::NUMERIC[], "+ net_proceeds +", '"+ UserInfo.EmployeeCode +"', '"+txtVAT.getText()+"'::numeric(19, 2));";

					pgUpdate db = new pgUpdate();
					db.executeUpdate(SQL, true);
					db.commit();

					chkEPEB.setSelected(false);;
					chkETD.setSelected(false);
					chkCOC.setSelected(false);
					chkMERALCO.setSelected(false);
					chkCOMFAC5.setSelected(false);
					chkRightOfWay.setSelected(false);
					chkWaterSupply.setSelected(false);
					chkTaxDec.setSelected(false);

					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client has been tagged.", "Save", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Something went wrong.", "Save", JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(getContentPane(), "This check has already been deposited. The modification \nof Loan Released details is no longer permitted.");
			}

		} else {
			ArrayList<BigDecimal> less = new ArrayList<BigDecimal>();
			less.add((BigDecimal) txtSRIDocStamps.getValued());
			less.add((BigDecimal) txtFire.getValued());
			less.add((BigDecimal) txtProcessingFee.getValued());
			less.add((BigDecimal) txtAppraisalFee.getValued());
			less.add((BigDecimal) txtInterimMRI.getValued());
			less.add((BigDecimal) txtRetentionFee.getValued());
			less.add((BigDecimal) txt1stMA.getValued());
			less.add((BigDecimal) txtRefilingFee.getValued());
			less.add((BigDecimal) txt10Retention.getValued());
			less.add((BigDecimal) txtServiceFee.getValued());

			ArrayList<BigDecimal> retentions = new ArrayList<BigDecimal>();
			retentions.add((BigDecimal) txtEPEB.getValued());
			retentions.add((BigDecimal) txtETD.getValued());
			retentions.add((BigDecimal) txtCOC.getValued());
			retentions.add((BigDecimal) txtMERALCO.getValued());
			retentions.add((BigDecimal) txtCOMFAC5.getValued());
			retentions.add((BigDecimal) txtRightOfWay.getValued());
			retentions.add((BigDecimal) txtWaterSupply.getValued());
			retentions.add((BigDecimal) txtTaxDec.getValued());

			BigDecimal net_proceeds = (BigDecimal) txtNetProceeds.getValue();

			try {
				//String SQL = "call sp_loanreleased_tagging_online(\n" +  //MODIFIED BY MONIQUE DTD 2023-08-23;
				String SQL = "call sp_loanreleased_tagging_online_v2(\n"+
						"'"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", "+ term +", "+ rate +",\n" + 
						"'"+ actual_date +"'::TIMESTAMP, '"+ client_seqno +"', \n" +
						loanable_amount +", ARRAY"+ less.toString() +"::NUMERIC[], " +
						"ARRAY"+ retentions +"::NUMERIC[], "+ net_proceeds +", '"+ UserInfo.EmployeeCode +"', '"+txtVAT.getValue()+"'::numeric(19, 2));";

				pgUpdate db = new pgUpdate();
				db.executeUpdate(SQL, true);
				db.commit();

				chkEPEB.setSelected(false);;
				chkETD.setSelected(false);
				chkCOC.setSelected(false);
				chkMERALCO.setSelected(false);
				chkCOMFAC5.setSelected(false);
				chkRightOfWay.setSelected(false);
				chkWaterSupply.setSelected(false);
				chkTaxDec.setSelected(false);

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client has been tagged.", "Save", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Something went wrong.", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		displayTaggedLoanReleased();
		setComponentsEnabled(pnlNorth, false);
	}

	private void clearFields() {
		lookupClient.setValue(null);
		txtClientName.setText("");
		txtProjectID.setText("");
		txtProjectName.setText("");
		txtPblID.setText("");
		txtUnitDescription.setText("");
		txtTermRate.setText("");
		txtSequenceNo.setText("");
		dateActualDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		cmbReceiptType.setSelectedIndex(0);
		txtReceiptNo.setText("");
		txtAccountNo.setText("");
		txtCheckNo.setText("");
		dateCheckDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		lookupBank.setValue(null);
		txtBank.setText("");
		txtBank.setToolTipText("");
		lookupBranch.setValue(null);
		lookupBranch.setLookupSQL(null);
		txtBranch.setText("");
		txtBranch.setToolTipText("");
		txtNetProceeds.setValue(null);

		txtLoanableAmount.setValue(null);
		txtSRIDocStamps.setValue(null);
		txtFire.setValue(null);
		txtProcessingFee.setValue(null);
		txtAppraisalFee.setValue(null);
		txtInterimMRI.setValue(null);
		txtRetentionFee.setValue(null);
		txt1stMA.setValue(null);
		txtRefilingFee.setValue(null);

		chkEPEB.setSelected(false);
		txtEPEB.setValue(null);
		chkETD.setSelected(false);
		txtETD.setValue(null);
		chkCOC.setSelected(false);
		txtCOC.setValue(null);
		chkMERALCO.setSelected(false);
		txtMERALCO.setValue(null);
		chkCOMFAC5.setSelected(false);
		txtCOMFAC5.setValue(null);
		chkRightOfWay.setSelected(false);
		txtRightOfWay.setValue(null);

		chkWaterSupply.setSelected(false);
		txtWaterSupply.setValue(null);
		chkTaxDec.setSelected(false);
		txtTaxDec.setValue(null);

		txtTotal.setValue(null);
	}

	private void cancelTransactions() {//XXX Cancel Transactions
		clearFields();
		setComponentsEnabled(pnlNorth, false);
		btnState(true, false, false, false);
	}

	private void computeTotalNetProceeds() {
		BigDecimal total_net_proceeds = new BigDecimal("0.00");
		for(int x=0; x < tblPaymentList.getRowCount(); x++){
			BigDecimal net_proceeds = (BigDecimal) tblPaymentList.getValueAt(x, 4);
			total_net_proceeds = total_net_proceeds.add(net_proceeds);
		}
		modelPaymentListTotal.setValueAt(total_net_proceeds, 0, 4);
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();

		if(action.equals("New")){
			newTransactions();
			blnEdit = false; 
		}

		if(action.equals("Edit")){
			editTransactions();
			blnEdit = true; 
		} 

		if(action.equals("Save")){
			computeNetProceeds();
			BigDecimal bdNetProceeds = new BigDecimal(txtNetProceeds.getValue().toString()); 

			System.out.println(""); 
			System.out.println("txtNetProceeds.getValue(): " + txtNetProceeds.getValue()); 
			System.out.println("bdNetProceeds: " + bdNetProceeds); 

			String strNetProceed = String.format("%,.2f", bdNetProceeds.setScale(2, RoundingMode.HALF_UP)); 

			System.out.println("");
			System.out.println("strNetProceed: " + strNetProceed); 

			String strMessage = "The computed net proceed is " + strNetProceed + ". \n\nAre all entries correct?";
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), strMessage, "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					saveTransaction();
					btnState(true, false, false, false);
				}
			}

			blnEdit = false;
		}

		if(action.equals("Cancel")){
			cancel();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			computeNetProceeds();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { 

	}
}
