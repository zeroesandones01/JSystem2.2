package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;

import Buyers.ClientServicing._OrderOfPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
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
import tablemodel.modelOrderOfPayments_PaymentDetails;
import tablemodel.model_IssuanceOfPayments_Montalban;
import tablemodel.model_Issued_IssuanceOfPayments_Montalban;

public class IssuanceOfReceipt_Montalban extends _JInternalFrame {
	private static String title = "Issuance Of Receipt(Montalban)";
	private Border line_border = BorderFactory.createLineBorder(Color.GRAY);
	private Border empty_border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	private Dimension frame_size = new Dimension(900, 600);

	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeqNo;

	private JPanel pnlNorthCenter;
	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JLookup lookupOfficeBranch;
	private _JXTextField txtOfficeBranch;
	private _JXTextField txtClient;
	private _JXTextField txtFirstName;
	private _JXTextField txtMiddleName;
	private _JXTextField txtLastName;
	private JCheckBox chkGuest;
	private JPanel pnlProject;
	private _JXTextField txtProjID;
	private _JXTextField txtProject;

	private JPanel pnlUnit;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;

	private JPanel pnlSeqNo;
	private _JXTextField txtSeqNo;

	private _JLookup lookupParticular;
	private _JXTextField txtParticular;

	private _JXFormattedTextField txtAmount;
	private JLabel lblLotRemarks;
	private JComboBox cmbCheckType;
	private JTextField txtCheckNo;
	private JTextField txtAcctNo;
	private _JLookup lookupBank;
	private _JXTextField txtBank;
	private _JLookup lookupBankBranch;
	private _JXTextField txtBankBranch;
	private _JDateChooser dateCheckDate;
	private _JXFormattedTextField txtNoOfChecks;
	private JTextField txtBRSTN;

	private JPanel pnlCenter;

	private JTabbedPane tabCenter;
	private _JScrollPaneMain scrollPMD_Payments;
	private _JTableMain tblPMD_Payments;
	private JList rowHeaderPMD_Payments;
	private model_IssuanceOfPayments_Montalban modelPayments;

	private _JTableTotal tblPaymentListTotal;
	private _JScrollPaneTotal scrollPaymentListTotal;
	private model_IssuanceOfPayments_Montalban modelPmtsTotal;

	private JScrollPane scrollPMD_Issued_Pmts;
	private _JTableMain tblPMD_Issued_Pmts;
	private JList rowHeaderPMD_IssuedPmts;
	private model_Issued_IssuanceOfPayments_Montalban modelIssuedPmts;

	private JButton btnPost;
	private JComboBox cmbPmtType;

	private JButton btnNew;
	private JButton btnIssueReceipt;
	private JButton btnRemove;
	private JButton btnCancel;
	private JButton btnPreviewReceipt;

	private Boolean with_other_lot = false;
	private String other_lot = "";
	protected String proj_id;
	protected String receipt_no_old;
	
	private String receipt_id = "03";
	//private String receipt_type = "ACKNOWLEDGEMENT RECEIPT";
	private String receipt_alias = "AR";
	private String receipt_type = "";

	public IssuanceOfReceipt_Montalban() {
		super(title, true, true, true, true);
		initGUI();
	}

	public IssuanceOfReceipt_Montalban(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public IssuanceOfReceipt_Montalban(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frame_size);
		this.setPreferredSize(frame_size);
		this.setLayout(new BorderLayout(5, 5));

		JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(empty_border);
		{
			JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			//pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Payment Details"));
			//pnlNorth.setBorder(LINE_BORDER);
			pnlNorth.setPreferredSize(new Dimension(0, 370));
			{
				JPanel pnlNorthUpper = new JPanel(new BorderLayout(3, 3));
				pnlNorth.add(pnlNorthUpper, BorderLayout.NORTH);
				pnlNorthUpper.setBorder(JTBorderFactory.createTitleBorder("Client Details"));
				{
					JPanel pnlNC_Label = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorthUpper.add(pnlNC_Label, BorderLayout.WEST);
					{
						JLabel lblOfficeBranch = new JLabel("Office Branch");
						pnlNC_Label.add(lblOfficeBranch);
					}
					{
						JLabel lblCLient = new JLabel("Client");
						pnlNC_Label.add(lblCLient);
					}
					{
						JLabel lblProject = new JLabel("Project");
						pnlNC_Label.add(lblProject);
					}
					{
						JLabel lblUnit = new JLabel("Unit");
						pnlNC_Label.add(lblUnit);
					}
					{
						JLabel lblSeqNo = new JLabel("Seq. No");
						pnlNC_Label.add(lblSeqNo);
					}
				}
				{
					JPanel pnlNC_Component = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorthUpper.add(pnlNC_Component, BorderLayout.CENTER);
					{
						JPanel pnlOfficeBranch = new JPanel(new BorderLayout(3, 3));
						pnlNC_Component.add(pnlOfficeBranch);
						{
							lookupOfficeBranch = new _JLookup(null, "Office Branch", 0);
							pnlOfficeBranch.add(lookupOfficeBranch, BorderLayout.WEST);
							lookupOfficeBranch.setPreferredSize(new Dimension(100, 0));
							lookupOfficeBranch.setLookupSQL(getBranch());
							lookupOfficeBranch.addLookupListener(new LookupListener() {
								
								@Override
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									
									if(data != null) {
										String branch_id = (String) data[0];
										String branch_name = (String) data[1];
										txtOfficeBranch.setText(branch_name);
										lookupOfficeBranch.setEditable(false);
									}
								}
							});
						}
						{
							txtOfficeBranch = new _JXTextField("Office Branch");
							pnlOfficeBranch.add(txtOfficeBranch, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlClient = new JPanel(new BorderLayout(3, 3));
						pnlNC_Component.add(pnlClient);
						{
							lookupClient = new _JLookup(null, "Client", 0);
							pnlClient.add(lookupClient, BorderLayout.WEST);
							lookupClient.setPreferredSize(new Dimension(100, 0));
							lookupClient.setLookupSQL(_pmd_payments.sqlClients());
							lookupClient.setFilterName(true);
							lookupClient.addLookupListener(new LookupListener() {

								@Override
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null) {
										FncSystem.out("Lookup Clients", lookupClient.getLookupSQL());

										String entity_id = (String) data[0];
										String entity_name = (String) data[1];
										proj_id = (String) data[2];
										String proj_name = (String) data[3];
										String pbl_id = (String) data[4];
										String unit_desc = (String) data[5];
										String seq_no = (String) data[6];
										Boolean with_other_lot = (Boolean) data[7];
										String first_name = (String) data[8];
										String middle_name = (String) data[9];
										String last_name = (String) data[10];
										
										if(pbl_id != null) {
											receipt_no_old = (String) receiptNo();
										}
										

										//txtClient.setText(entity_name);
										txtFirstName.setText(first_name);
										txtMiddleName.setText(middle_name);
										txtLastName.setText(last_name);
										txtProjID.setText(proj_id);
										txtProject.setText(proj_name);
										txtUnitID.setText(pbl_id);
										txtUnitDesc.setText(unit_desc);
										txtSeqNo.setText(seq_no);
										IssuanceOfReceipt_Montalban.this.with_other_lot = with_other_lot;

										//lookupParticular.setLookupSQL(_PMD_Payment.sqlParticular());

										lookupParticular.setValue(null);
										txtParticular.setText("");
										txtAmount.setValue(new BigDecimal("0.00"));
										modelPayments.clear();
										rowHeaderPMD_Payments.setModel(new DefaultListModel());
									}
								}
							});
						}
						{
							JPanel pnlClientFullName = new JPanel(new BorderLayout(3, 3));
							pnlClient.add(pnlClientFullName, BorderLayout.CENTER);
							{
								JPanel pnlClientFullName_Center = new JPanel(new GridLayout(1, 3, 3, 3));
								pnlClientFullName.add(pnlClientFullName_Center, BorderLayout.CENTER);
								{
									txtFirstName = new _JXTextField("First Name");
									pnlClientFullName_Center.add(txtFirstName);
								}
								{
									txtMiddleName = new _JXTextField("Middle Name");
									pnlClientFullName_Center.add(txtMiddleName);
								}
								{
									txtLastName = new _JXTextField("Last Name");
									pnlClientFullName_Center.add(txtLastName);
								}
							}
						}
						/*{
							txtClient = new _JXTextField();
							pnlClient.add(txtClient, BorderLayout.CENTER);
						}*/
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
						pnlNC_Component.add(pnlProject);
						{
							txtProjID = new _JXTextField();
							pnlProject.add(txtProjID, BorderLayout.WEST);
							txtProjID.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtProject = new _JXTextField();
							pnlProject.add(txtProject, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlUnit = new JPanel(new BorderLayout(3, 3));
						pnlNC_Component.add(pnlUnit);
						{
							txtUnitID = new _JXTextField();
							pnlUnit.add(txtUnitID, BorderLayout.WEST);
							txtUnitID.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtUnitDesc = new _JXTextField();
							pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);

						}
					}
					{
						JPanel pnlSeq = new JPanel(new BorderLayout(3, 3));
						pnlNC_Component.add(pnlSeq);
						{
							txtSeqNo = new _JXTextField();
							pnlSeq.add(txtSeqNo, BorderLayout.WEST);
							txtSeqNo.setPreferredSize(new Dimension(100, 0));
						}
					}
				}
			}
			{
				JPanel pnlNorthLower = new JPanel(new BorderLayout(3, 3));
				pnlNorth.add(pnlNorthLower, BorderLayout.CENTER);
				pnlNorthLower.setPreferredSize(new Dimension(0, 350));
				pnlNorthLower.setBorder(JTBorderFactory.createTitleBorder("Payment Details"));
				{
					JPanel pnlNorthLowerWest = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorthLower.add(pnlNorthLowerWest, BorderLayout.WEST);
					pnlNorthLowerWest.setPreferredSize(new Dimension(100, 0));
					//pnlNorthLowerWest.setBorder(LINE_BORDER);
					{
						btnPost = new JButton("Post");
						pnlNorthLowerWest.add(btnPost);
						btnPost.setActionCommand(btnPost.getText());
						btnPost.addActionListener(this);
					}
					//					{
					//						cmbPmtType = new JComboBox();
					//						pnlNorthLowerWest.add(cmbPmtType);
					//						
					//					}

					{
						pnlNorthLowerWest.add(Box.createHorizontalBox());
						/*pnlNorthLowerWest.add(Box.createHorizontalBox());
						pnlNorthLowerWest.add(Box.createHorizontalBox());
						pnlNorthLowerWest.add(Box.createHorizontalBox());*/
					}
				}
				{
					JPanel pnlNorthLowerCenter = new JPanel(new BorderLayout(3, 3));
					pnlNorthLower.add(pnlNorthLowerCenter, BorderLayout.CENTER);
					{
						JPanel pnlNorthLowerLabel = new JPanel(new GridLayout(7, 1, 3, 3));
						pnlNorthLowerCenter.add(pnlNorthLowerLabel, BorderLayout.WEST);
						pnlNorthLowerLabel.setPreferredSize(new Dimension(100, 40));
						{
							JLabel lblPmtParticular = new JLabel("Particular");
							pnlNorthLowerLabel.add(lblPmtParticular);
						}
						{
							JLabel lblAmount = new JLabel("Amount");
							pnlNorthLowerLabel.add(lblAmount);
						}
						{
							JLabel lblCheckType = new JLabel("Check Type");
							pnlNorthLowerLabel.add(lblCheckType);
						}
						{
							JLabel lblCheckNo = new JLabel("Check No");
							pnlNorthLowerLabel.add(lblCheckNo);
						}
						{
							JLabel lblAcctNo = new JLabel("Account No");
							pnlNorthLowerLabel.add(lblAcctNo);
						}
						{
							JLabel lblBank = new JLabel("Bank");
							pnlNorthLowerLabel.add(lblBank);
						}
						{
							JLabel lblBranch = new JLabel("Bank Branch");
							pnlNorthLowerLabel.add(lblBranch);
						}
						/*{
							pnlNorthLowerLabel.add(Box.createHorizontalBox());
							pnlNorthLowerLabel.add(Box.createHorizontalBox());
							pnlNorthLowerLabel.add(Box.createHorizontalBox());
						}*/
					}
					{
						JPanel pnlNorthLowerComponents = new JPanel(new GridLayout(7, 1, 3, 3));
						pnlNorthLowerCenter.add(pnlNorthLowerComponents, BorderLayout.CENTER);
						pnlNorthLowerComponents.setPreferredSize(new Dimension(0, 40));
						{
							JPanel pnlParticular = new JPanel(new BorderLayout(3, 3));
							pnlNorthLowerComponents.add(pnlParticular);
							pnlParticular.setPreferredSize(new Dimension(0, 20));
							{
								lookupParticular = new _JLookup(null, "Particular", 0);
								pnlParticular.add(lookupParticular, BorderLayout.WEST);
								lookupParticular.setPreferredSize(new Dimension(100, 0));
								lookupParticular.setReturnColumn(0);
								lookupParticular.setLookupSQL(sqlParticular());
								lookupParticular.setFilterName(true);
								lookupParticular.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();		

										if(data != null) {
											String particular = (String) data[1];
											txtParticular.setText(particular);

											if(with_other_lot) {
												other_lot = OtherLotSelection();

												ActivateLotLabel(other_lot);
											}else {
												other_lot = "";
											}
											
											if (hasLTS_BOI(txtUnitID.getText())==false||receipt_id.equals(null)) {
												receipt_id = "03";
												receipt_type = "AR";
											} else {
												receipt_id = (String) data[5];
												receipt_type = (String) data[6];
											}		
										}
									}
								});
							}
							{
								txtParticular = new _JXTextField();
								pnlParticular.add(txtParticular, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlAmount = new JPanel(new BorderLayout(3, 3));
							pnlNorthLowerComponents.add(pnlAmount);
							pnlAmount.setPreferredSize(new Dimension(0, 20));
							{
								txtAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlAmount.add(txtAmount, BorderLayout.WEST);
								txtAmount.setPreferredSize(new Dimension(100, 0));
								txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtAmount.setValue(new BigDecimal("0.00"));

							}
							{
								JPanel pnlPmtType = new JPanel(new BorderLayout(5, 5));
								pnlAmount.add(pnlPmtType);
								{
									cmbPmtType = new JComboBox(new String[] { "Cash", "Check" });
									pnlPmtType.add(cmbPmtType, BorderLayout.WEST);
									cmbPmtType.setPreferredSize(new Dimension(150, 0));
									cmbPmtType.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											
											String selected_item = (String) ((JComboBox) e.getSource()).getSelectedItem();
											
											
											if(selected_item.equals("Cash")) {
												cmbCheckType.setEnabled(false);
												dateCheckDate.setEnabled(false);
												dateCheckDate.getCalendarButton().setEnabled(false);
												txtNoOfChecks.setEditable(false);
												txtCheckNo.setEditable(false);
												txtAcctNo.setEditable(false);
												txtBRSTN.setEditable(false);
												lookupBank.setEnabled(false);
												lookupBankBranch.setEnabled(false);
												
												cmbCheckType.setSelectedIndex(0);
												dateCheckDate.setDate(null);
												txtCheckNo.setText("");
												txtNoOfChecks.setValue(null);
												txtAcctNo.setText("");
												txtBRSTN.setText("");
												lookupBank.setValue(null);
												txtBank.setText("");
												lookupBankBranch.setValue(null);
												txtBankBranch.setText("");
												
											}else {
												cmbCheckType.setEnabled(true);
												dateCheckDate.setEnabled(true);
												dateCheckDate.getCalendarButton().setEnabled(true);
												txtNoOfChecks.setEditable(true);
												txtCheckNo.setEditable(true);
												txtAcctNo.setEditable(true);
												txtBRSTN.setEditable(true);
												lookupBank.setEnabled(true);
												lookupBankBranch.setEnabled(true);
											}
										}
									});
								}
							}
							{
								lblLotRemarks = new JLabel(""); 
								pnlAmount.add(lblLotRemarks, BorderLayout.EAST);
								lblLotRemarks.setHorizontalAlignment(JLabel.TRAILING);
								lblLotRemarks.setEnabled(true);
							}
						}
						{
							JPanel pnlCheckType = new JPanel(new BorderLayout(3, 3));
							pnlNorthLowerComponents.add(pnlCheckType);
							{
								cmbCheckType = new JComboBox(getCheckTypes());
								pnlCheckType.add(cmbCheckType, BorderLayout.WEST);
								cmbCheckType.setPreferredSize(new Dimension(200, 0));
							}
							{
								JPanel pnlCheckDate = new JPanel(new BorderLayout(3, 3));
								pnlCheckType.add(pnlCheckDate, BorderLayout.CENTER);
								pnlCheckDate.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 150));
								{
									JPanel pnlCheckDateWest = new JPanel(new BorderLayout(3, 3));
									pnlCheckDate.add(pnlCheckDateWest, BorderLayout.WEST);
									pnlCheckDateWest.setPreferredSize(new Dimension(200, 0));
									{
										JLabel lblCheckDate = new JLabel("Check Date");
										pnlCheckDateWest.add(lblCheckDate, BorderLayout.WEST);
										lblCheckDate.setPreferredSize(new Dimension(80, 0));
									}
									{
										dateCheckDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlCheckDateWest.add(dateCheckDate, BorderLayout.CENTER);
									}
								}
							}
						}

						{
							JPanel pnlCheckNo = new JPanel(new BorderLayout(3, 3));
							pnlNorthLowerComponents.add(pnlCheckNo);
							{
								txtCheckNo = new _JXTextField("Check No.");
								pnlCheckNo.add(txtCheckNo, BorderLayout.WEST);
								txtCheckNo.setPreferredSize(new Dimension(200, 0));
							}
							{
								JPanel pnlNoOfCheck = new JPanel(new BorderLayout(3, 3));
								pnlCheckNo.add(pnlNoOfCheck, BorderLayout.CENTER);
								pnlNoOfCheck.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 150));
								{
									JPanel pnlNoOfCheckWest = new JPanel(new BorderLayout(3, 3));
									pnlNoOfCheck.add(pnlNoOfCheckWest, BorderLayout.WEST);
									pnlNoOfCheckWest.setPreferredSize(new Dimension(173, 0));
									{
										JLabel lblNoOfCheck = new JLabel("No. of Check");
										pnlNoOfCheckWest.add(lblNoOfCheck, BorderLayout.WEST);
										lblNoOfCheck.setPreferredSize(new Dimension(80, 0));
									}
									{
										txtNoOfChecks = new _JXFormattedTextField(JXFormattedTextField.CENTER);
										pnlNoOfCheckWest.add(txtNoOfChecks, BorderLayout.CENTER);
										txtNoOfChecks.setPrompt("#");
										txtNoOfChecks.setFormatterFactory(_JXFormattedTextField.INTEGER);
										txtNoOfChecks.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent e) {	

												if (Integer.parseInt(txtNoOfChecks.getText())>12)
												{
													JOptionPane.showMessageDialog(getContentPane(), "No. of checks cannot exceed 12", "Warning", 
															JOptionPane.WARNING_MESSAGE);
												}

											}				 
										});		
									}
								}
							}
						}
						{
							JPanel pnlAccountNo = new JPanel(new BorderLayout(3, 3));
							pnlNorthLowerComponents.add(pnlAccountNo);
							{
								txtAcctNo = new _JXTextField("Account No.");
								pnlAccountNo.add(txtAcctNo, BorderLayout.WEST);
								txtAcctNo.setPreferredSize(new Dimension(200, 0));
							}
							{
								JPanel pnlBRSTN = new JPanel(new BorderLayout(3, 3));
								pnlAccountNo.add(pnlBRSTN, BorderLayout.CENTER);
								pnlBRSTN.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 150));
								{
									JPanel pnlNoOfCheckWest = new JPanel(new BorderLayout(3, 3));
									pnlBRSTN.add(pnlNoOfCheckWest, BorderLayout.WEST);
									pnlNoOfCheckWest.setPreferredSize(new Dimension(173, 0));
									{
										JLabel lblBRSTN = new JLabel("BRSTN");
										pnlNoOfCheckWest.add(lblBRSTN, BorderLayout.WEST);
										lblBRSTN.setPreferredSize(new Dimension(80, 0));
									}
									{
										txtBRSTN = new _JXTextField("BRSTN");
										pnlNoOfCheckWest.add(txtBRSTN, BorderLayout.CENTER);
									}
								}
							}
						}
						{
							JPanel pnlBank = new JPanel(new BorderLayout(3, 3));
							pnlNorthLowerComponents.add(pnlBank);
							{
								lookupBank = new _JLookup(null, "Bank", 0);
								pnlBank.add(lookupBank, BorderLayout.WEST);
								lookupBank.setPrompt("Bank ID");
								lookupBank.setLookupSQL(_OrderOfPayment.getBank());
								lookupBank.setPreferredSize(new Dimension(80, 0));
								lookupBank.setFilterName(true);
								lookupBank.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String bank_id = (String) data[0];
											String bank_name = (String) data[1];

											txtBank.setText(bank_name);
											lookupBankBranch.setLookupSQL(_OrderOfPayment.getBankBranch(bank_id));
										}
									}
								});
							}
							{
								txtBank = new _JXTextField("Bank Name");
								pnlBank.add(txtBank, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlBranch = new JPanel(new BorderLayout(3, 3));
							pnlNorthLowerComponents.add(pnlBranch);
							{
								lookupBankBranch = new _JLookup(null, "Branch", 0);
								pnlBranch.add(lookupBankBranch, BorderLayout.WEST);
								lookupBankBranch.setPrompt("Branch ID");
								lookupBankBranch.setPreferredSize(new Dimension(80, 0));
								lookupBankBranch.setFilterName(true);
								lookupBankBranch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String branch_name = (String) data[1];

											txtBankBranch.setText(branch_name);
										}
									}
								});
							}
							{
								txtBankBranch = new _JXTextField("Branch Name");
								pnlBranch.add(txtBankBranch, BorderLayout.CENTER);
							}
						}

						/*{
							pnlNorthLowerComponents.add(Box.createHorizontalBox());
							pnlNorthLowerComponents.add(Box.createHorizontalBox());
							pnlNorthLowerComponents.add(Box.createHorizontalBox());
						}*/
					}
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(3, 3));
			pnlMain.add(pnlCenter, BorderLayout.CENTER);
			{
				tabCenter= new JTabbedPane();
				pnlCenter.add(tabCenter, BorderLayout.CENTER);
				{
					JPanel pnlPMD_Payments = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Payments", pnlPMD_Payments);
					{
						JPanel pnlPMD_Pmts_Center = new JPanel(new BorderLayout(3, 3));
						pnlPMD_Payments.add(pnlPMD_Pmts_Center, BorderLayout.CENTER);
						{
							scrollPMD_Payments = new _JScrollPaneMain();
							pnlPMD_Pmts_Center.add(scrollPMD_Payments, BorderLayout.CENTER);
							//scrollPMD_Payments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							{
								modelPayments = new model_IssuanceOfPayments_Montalban();
								tblPMD_Payments = new _JTableMain(modelPayments);
								scrollPMD_Payments.setViewportView(tblPMD_Payments);
								tblPMD_Payments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								tblPMD_Payments.setSortable(false);
								modelPayments.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {


										if(modelPayments.getRowCount() == 0){
											rowHeaderPMD_Payments.setModel(new DefaultListModel<Integer>());
										}

										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel<Integer>)rowHeaderPMD_Payments.getModel()).addElement(modelPayments.getRowCount());
											scrollPaymentListTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPayments.getRowCount())));
										}

										if(e.getType() == TableModelEvent.DELETE){
											try {
												//_OrderOfPayment.totalPayments(modelPaymentList, modelPaymentListTotal);
												scrollPaymentListTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPayments.getRowCount())));

												rowHeaderPMD_Payments.setModel(new DefaultListModel<Integer>());
												for(int x=0; x < modelPayments.getRowCount(); x++){
													((DefaultListModel<Integer>)rowHeaderPMD_Payments.getModel()).addElement(x + 1);
												}
											} catch (ArrayIndexOutOfBoundsException e1) { }
										}
										totalPayments(modelPayments, modelPmtsTotal);
									}
								});
							}
							{
								rowHeaderPMD_Payments = tblPMD_Payments.getRowHeader();
								rowHeaderPMD_Payments.setModel(new DefaultListModel<Integer>());
								scrollPMD_Payments.setRowHeaderView(rowHeaderPMD_Payments);
								scrollPMD_Payments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollPaymentListTotal = new _JScrollPaneTotal(scrollPMD_Payments);
							pnlPMD_Pmts_Center.add(scrollPaymentListTotal, BorderLayout.SOUTH);
							{
								modelPmtsTotal = new model_IssuanceOfPayments_Montalban();
								modelPmtsTotal.addRow(new Object[] {null, "Total", 0.00, null});

								tblPaymentListTotal = new _JTableTotal(modelPmtsTotal, tblPMD_Payments);
								scrollPaymentListTotal.setViewportView(tblPaymentListTotal);

								tblPaymentListTotal.setTotalLabel(1);
							}
						}
					}
					{
						JPanel pnlPMD_Pmts_South = new JPanel(new GridLayout(1, 5, 5, 5));
						pnlPMD_Payments.add(pnlPMD_Pmts_South, BorderLayout.SOUTH);
						pnlPMD_Pmts_South.setPreferredSize(new Dimension(0, 40));
						pnlPMD_Pmts_South.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
						{
							pnlPMD_Pmts_South.add(Box.createHorizontalBox());
						}
						{
							btnNew = new JButton("New");
							pnlPMD_Pmts_South.add(btnNew);
							btnNew.setActionCommand(btnNew.getText());
							btnNew.addActionListener(this);
						}
						{
							btnIssueReceipt = new JButton("Issue Payment");
							pnlPMD_Pmts_South.add(btnIssueReceipt);
							btnIssueReceipt.setActionCommand(btnIssueReceipt.getText());
							btnIssueReceipt.addActionListener(this);
						}
						{
							btnRemove = new JButton("Remove Payment");
							pnlPMD_Pmts_South.add(btnRemove);
							btnRemove.setActionCommand(btnRemove.getText());
							btnRemove.addActionListener(this);
						}
						{
							btnCancel = new JButton("Cancel");
							pnlPMD_Pmts_South.add(btnCancel);
							btnCancel.setActionCommand(btnCancel.getText());
							btnCancel.addActionListener(this);
						}
					}
				}
				{
					JPanel pnlPMD_Issued_Payments = new JPanel(new BorderLayout(5, 5));
					tabCenter.addTab("Issued Payments", pnlPMD_Issued_Payments);
					{
						JPanel pnlIssued_PMT_Center = new JPanel(new BorderLayout(5, 5));
						pnlPMD_Issued_Payments.add(pnlIssued_PMT_Center, BorderLayout.CENTER);
						{
							scrollPMD_Issued_Pmts = new JScrollPane();
							pnlIssued_PMT_Center.add(scrollPMD_Issued_Pmts);
							scrollPMD_Issued_Pmts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							{
								modelIssuedPmts = new model_Issued_IssuanceOfPayments_Montalban();
								tblPMD_Issued_Pmts= new _JTableMain(modelIssuedPmts);
								scrollPMD_Issued_Pmts.setViewportView(tblPMD_Issued_Pmts);
								tblPMD_Issued_Pmts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								tblPMD_Issued_Pmts.setSortable(false);
								tblPMD_Issued_Pmts.hideColumns("Entity ID", "Proj. ID", "Unit ID", "Seq. No");
								modelIssuedPmts.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {

										((DefaultListModel)rowHeaderPMD_IssuedPmts.getModel()).addElement(modelIssuedPmts.getRowCount());

										if(modelIssuedPmts.getRowCount() == 0){
											rowHeaderPMD_IssuedPmts.setModel(new DefaultListModel());
										}

									}
								});

							}
							{
								rowHeaderPMD_IssuedPmts = tblPMD_Issued_Pmts.getRowHeader();
								rowHeaderPMD_IssuedPmts.setModel(new DefaultListModel());
								scrollPMD_Issued_Pmts.setRowHeaderView(rowHeaderPMD_IssuedPmts);
								scrollPMD_Issued_Pmts.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}

					}
					{
						JPanel pnlIssued_PMT_South = new JPanel(new GridLayout(1, 5, 5, 5));
						pnlPMD_Issued_Payments.add(pnlIssued_PMT_South, BorderLayout.SOUTH);
						pnlIssued_PMT_South.setPreferredSize(new Dimension(0, 40));
						pnlIssued_PMT_South.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
						{
							pnlIssued_PMT_South.add(Box.createHorizontalBox());
							pnlIssued_PMT_South.add(Box.createHorizontalBox());
							pnlIssued_PMT_South.add(Box.createHorizontalBox());
							pnlIssued_PMT_South.add(Box.createHorizontalBox());
						}
						{
							btnPreviewReceipt = new JButton("Preview Receipt");
							pnlIssued_PMT_South.add(btnPreviewReceipt);
							btnPreviewReceipt.setActionCommand(btnPreviewReceipt.getText());
							btnPreviewReceipt.addActionListener(this);
						}
					}
				}
			}
		}
		//btnState(false, false, false, false, true);
		FncTables.bindColumnTables(tblPMD_Payments, tblPaymentListTotal);
		tblPMD_Payments.hideColumns("Part ID");
		displayIssuedPmts();
		cancelPMD_Pmt();
	}

	private void btnState(Boolean sPost, Boolean sNew ,Boolean sIssue, Boolean sRemove, Boolean sCancel, Boolean sPreview) {

		btnPost.setEnabled(sPost);
		btnNew.setEnabled(sNew);
		btnIssueReceipt.setEnabled(sIssue);
		btnRemove.setEnabled(sRemove);
		btnCancel.setEnabled(sCancel);
		btnPreviewReceipt.setEnabled(sPreview);
	}


	private void postPmt() {

		String part_id = lookupParticular.getValue();
		String part_desc = txtParticular.getText();
		BigDecimal amount = (BigDecimal) txtAmount.getValued();

		if(lookupParticular.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select payment particular", "Particular", JOptionPane.WARNING_MESSAGE);
		}else {
			if(((BigDecimal) txtAmount.getValued()).compareTo(FncBigDecimal.zeroValue()) == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input payment amount", "Payment", JOptionPane.WARNING_MESSAGE);
			}else {
				modelPayments.addRow(new Object[] {part_id, part_desc, amount, other_lot});
				totalPayments(modelPayments, modelPmtsTotal);
				tblPMD_Payments.packAll();
				lookupParticular.setValue(null);
				txtParticular.setText("");
				txtAmount.setValue(new BigDecimal("0.00"));
				lblLotRemarks.setText("");

			}
		}
	}

	private void postCashPmt(){
		String part_id = lookupParticular.getValue();
		String part_name = txtParticular.getText();
		BigDecimal amount = (BigDecimal) txtAmount.getValued();
		String payment_type = "CASH";

		Double op_amount	= 0.00;						
		try { op_amount	= Double.parseDouble(modelPmtsTotal.getValueAt(0,2).toString()); } 
		catch (NullPointerException e) { op_amount	= 0.00; }
		BigDecimal op_amount_bd 	= new BigDecimal(op_amount);


		if (part_id.equals("033")) {
			Integer intOption = null;

			/*	Modified by Mann2x; Date Modified: May 26, 2020; Included moratorium options;	*/
			String due_type = ""; 
			String strPBLID = FncGlobal.GetString("select pbl_id from mf_unit_info where proj_id = '"+txtProjID.getText()+"' and unit_id = '"+txtUnitID.getText()+"'"); 

			if (_OrderOfPayment.withRemainingMoratorium(lookupClient.getValue(), txtProjID.getText(), strPBLID, txtSeqNo.getText())) {
				if (pastDuePriorECQ(lookupClient.getValue(), txtProjID.getText(), strPBLID, txtSeqNo.getText())) {
					JOptionPane.showMessageDialog(this, "This account is not current. Due type will automatically be set as `Regular`.");
					intOption = JOptionPane.YES_OPTION;
				} else if (!_OrderOfPayment.withRemainingRegular(lookupClient.getValue(), txtProjID.getText(), strPBLID, txtSeqNo.getText())) {
					JOptionPane.showMessageDialog(this, "This account no longer have any regular dues. Due type will automatically be set as `Moratorium`.");
					intOption = JOptionPane.CANCEL_OPTION;					          
				} else {
					intOption = JOptionPane.showOptionDialog(null, "This payment is intended for which due?", "Payment Mode", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
							new Object[] {" Regular  ", "Moratorium"}, JOptionPane.YES_OPTION);
				}
			} else {
				intOption = JOptionPane.YES_OPTION;
			}

			due_type = (String) ((intOption==JOptionPane.YES_OPTION)?"R":"M"); 
			modelPayments.addRow(new Object[]{part_id, part_name, amount, other_lot ,payment_type, null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_alias, null,  null, false, null, "", due_type});

		} else if (part_id.equals("197") || part_id.equals("268")) {
			modelPayments.addRow(new Object[]{part_id, part_name, amount, other_lot ,payment_type, null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_alias, null,  null, false, null});
			other_lot = ""; 
		} else {						
			modelPayments.addRow(new Object[]{part_id, part_name, amount, other_lot ,payment_type, null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_alias,null,  null, false});
		}
		tblPMD_Payments.packAll();
	}
	
	private void postCheckTransaction() {
		
		String part_id = lookupParticular.getValue();
		String part_name = txtParticular.getText();
		BigDecimal amount = (BigDecimal) txtAmount.getValued();
		String payment_type = "CHECK";
		String check_type_id = ((String)cmbCheckType.getSelectedItem()).split("[\\(\\)]")[1];
		String check_type_name = ((String)cmbCheckType.getSelectedItem()).split("[\\(\\)]")[0];
		Date check_date = dateCheckDate.getDate();
		System.out.printf("Display Check No: (%s)%n", txtCheckNo.getText().replaceAll("[^\\d]", ""));
		String check_no = txtCheckNo.getText().replaceAll("[^\\d]", "");
		int no_of_check = (Integer) txtNoOfChecks.getIntegerValue();
		String account_no = txtAcctNo.getText();
		String brstn = txtBRSTN.getText();
		String bank_id = lookupBank.getValue();
		String bank_name = txtBank.getText();
		String branch_id = lookupBankBranch.getValue();
		String branch_name = txtBankBranch.getText();
		String receiptID = "";
		String receiptTypeID = "";

		System.out.printf("Display check no: (%s)%n", check_no);
		Long new_check_no = Long.valueOf(check_no);
		System.out.printf("Display Value of Check No: %s%n", new_check_no);

		Calendar cal = Calendar.getInstance();
		cal.setTime(check_date);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DAY_OF_MONTH);

		Double op_amount	= 0.00;						
		try { op_amount	= Double.parseDouble(modelPmtsTotal.getValueAt(0,2).toString()); } catch (NullPointerException e) { op_amount	= 0.00; }
		BigDecimal op_amount_bd 	= new BigDecimal(op_amount);
		Double tot_check_amt = Double.parseDouble(txtAmount.getText().replace(",","")) * no_of_check;
		BigDecimal tot_check_amt_bd = new BigDecimal(tot_check_amt);

		System.out.println("");
		System.out.println("SQL Date: " + FncGlobal.getDateSQL());

		System.out.println("");
		System.out.println("strSelectedLot: "+other_lot); 


		if (part_id.equals("033")) {

			for(int x=0; x < no_of_check; x++){
				cal.set(year, month++, date);

				/*	Added by Mann2x; Date Added: July 5, 2017; DCRF# 159;	*/
				String strPBL = FncGlobal.GetString("select x.pbl_id from mf_unit_info x where x.unit_id = '"+txtUnitID.getText().toString()+"'");
				Boolean blnOR = FncGlobal.GetBoolean("select (case when (\n" +
						"select count(*) \n" +
						"from rf_buyer_status x \n" +
						"where x.entity_id = '"+lookupClient.getValue().toString()+"' and x.proj_id = '"+txtProjID.getText().toString()+"' \n" +
						"and x.pbl_id = '"+strPBL+"' and x.seq_no::int = '"+txtSeqNo.getText()+"'::int \n" +
						"and byrstatus_id = '01' and x.status_id = 'A'\n" +
						") > 0 then true else false end)");
				String strSubProject = FncGlobal.GetString("SELECT sub_proj_id \n" +
						"from mf_unit_info \n" +
						"where proj_id = '"+txtProjID.getText().toString()+"' and pbl_id = '"+strPBL+"'");
				Boolean blnBOI = FncGlobal.GetBoolean("SELECT (case when boi is null then false else true end) \n" +
						"FROM mf_sub_project \n" +
						"WHERE sub_proj_id = '"+strSubProject+"' and status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
				Boolean blnLTS = FncGlobal.GetBoolean("SELECT (case when lts_date is null then false else true end) \n" +
						"FROM mf_sub_project \n" +
						"WHERE sub_proj_id = '"+strSubProject+"' AND status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718

				/*	Modified By Mann2x; Modified Date: October 27, 2016; A check can be considered as dated the day before it's check date;
					if (cal.getTime().compareTo(FncGlobal.dateFormat(FncGlobal.getDateSQL()))<=0) {
						receiptID = "03";
						receiptTypeID = "AR";
					}
				 */

				/*	Modified by Mann2x; Date Modified: July 5, 2017; DCRF# 159;
					if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL())) {
				 */
				if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL()) && blnOR && blnBOI && blnLTS) {
					receiptID = receipt_id;
					receiptTypeID = receipt_alias;							
				} else {
					receiptID = "03";
					receiptTypeID = "AR";						
				}

				System.out.println("");
				System.out.println("receiptID: " + receiptID);
				System.out.println("receiptTypeID: " + receiptTypeID);

				/* As can be seen in this code, the "receiptID" value is hardcoded.
					modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, Long.toString(new_check_no++),
							check_type_id, check_type_name, cal.getTime(), account_no, bank_id, 
							bank_name, branch_id, branch_name, null, "03", 
							receiptTypeID, null, brstn, false});
				 */

				/*	Modified by Mann2x; Date Modified: May 26, 2020; Included moratorium options;	*/
				String due_type = ""; 
				String strPBLID = FncGlobal.GetString("select pbl_id from mf_unit_info where proj_id = '"+txtProjID.getText()+"' and unit_id = '"+txtUnitID.getText()+"'"); 

				if (_OrderOfPayment.withRemainingMoratorium(lookupClient.getValue(), txtProjID.getText(), strPBLID, txtSeqNo.getText())) {
					Integer intOption; 

					if (_OrderOfPayment.withRemainingMoratorium(lookupClient.getValue(), txtProjID.getText(), strPBLID, txtSeqNo.getText())) {
						if (pastDuePriorECQ(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText())) {
							JOptionPane.showMessageDialog(this, "This account is not current. Due type will automatically be set as `Regular`.");
							intOption = JOptionPane.YES_OPTION;
						} else if (!_OrderOfPayment.withRemainingRegular(lookupClient.getValue(), txtProjID.getText(), strPBLID, txtSeqNo.getText())) {
							JOptionPane.showMessageDialog(this, "This account no longer have any regular dues. Due type will automatically be set as `Moratorium`.");
							intOption = JOptionPane.CANCEL_OPTION;                                                    
						} else {
							intOption = JOptionPane.showOptionDialog(null, "This payment is intended for which due?", "Payment Mode", 
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
									new Object[] {" Regular  ", "  Mixed   ", "Moratorium"}, JOptionPane.YES_OPTION);
						}
					} else {
						intOption = JOptionPane.YES_OPTION;
					}

					due_type = (String) ((intOption==JOptionPane.YES_OPTION)?"R":(intOption==JOptionPane.NO_OPTION)?"W":(intOption==JOptionPane.CANCEL_OPTION)?"M":true); 
				}

				/*
					modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, Long.toString(new_check_no++),
							check_type_id, check_type_name, cal.getTime(), account_no, bank_id, 
							bank_name, branch_id, branch_name, null, receiptID, 
							receiptTypeID, null, brstn, false});
				 */

				modelPayments.addRow(new Object[]{part_id, part_name, amount, other_lot ,payment_type, Long.toString(new_check_no++),
						check_type_id, check_type_name, cal.getTime(), account_no, bank_id, 
						bank_name, branch_id, branch_name, null, receiptID, 
						receiptTypeID, null, brstn, false, null, null, due_type});
			}
		} else if (part_id.equals("197") || part_id.equals("268")) {
			for(int x=0; x < no_of_check; x++){
				cal.set(year, month++, date);

				receiptID = "03";
				receiptTypeID = "AR";

				modelPayments.addRow(new Object[]{part_id, part_name, amount, other_lot ,payment_type, Long.toString(new_check_no++),
						check_type_id, check_type_name, cal.getTime(), account_no, bank_id, 
						bank_name, branch_id, branch_name, null, receiptID, 
						receiptTypeID, null, brstn, false, null});
			}
			other_lot = ""; 
		} else {
			for(int x=0; x < no_of_check; x++){
				cal.set(year, month++, date);

				/*	Added by Mann2x; Date Added: July 5, 2017; DCRF# 159;	*/
				String strPBL = "";
				try {
					strPBL = FncGlobal.GetString("select x.pbl_id from mf_unit_info x where x.unit_id = '"+txtUnitID.getText().toString()+"'");
				} catch (NullPointerException ex) {
					strPBL = "";
				}

				Boolean blnOR = false;
				try {
					blnOR = FncGlobal.GetBoolean("select (case when (\n" +
							"select count(*) \n" +
							"from rf_buyer_status x \n" +
							"where x.entity_id = '"+lookupClient.getValue().toString()+"' and x.proj_id = '"+txtProjID.getText().toString()+"' \n" +
							"and x.pbl_id = '"+txtUnitID.getText()+"' and x.seq_no::int = '"+txtSeqNo.getText()+"'::int \n" +
							"and byrstatus_id = '01' and x.status_id = 'A'\n" +
							") > 0 then true else false end)");
				} catch (NullPointerException ex) {
					blnOR = false;
				}

				String strSubProject = "";
				try {
					strSubProject = FncGlobal.GetString("SELECT sub_proj_id \n" +
							"from mf_unit_info \n" +
							"where proj_id = '"+txtProjID.getText().toString()+"' and pbl_id = '"+txtUnitID.getText()+"'");
				} catch (NullPointerException ex) {
					strSubProject = "";
				}

				Boolean blnBOI = false;
				try {
					blnBOI = FncGlobal.GetBoolean("SELECT (case when boi is null then false else true end) \n" +
							"FROM mf_sub_project \n" +
							"WHERE sub_proj_id = '"+strSubProject+"' AND status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
				} catch (NullPointerException ex) {
					blnBOI = false;
				}

				Boolean blnLTS = false;
				try {
					blnLTS = FncGlobal.GetBoolean("SELECT (case when lts_date is null then false else true end) \n" +
							"FROM mf_sub_project \n" +
							"WHERE sub_proj_id = '"+strSubProject+"' AND status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
				} catch (NullPointerException ex) {
					blnLTS = false;
				}

				/*	Modified By Mann2x; Modified Date: October 27, 2016; A check can be considered as dated the day before it's check date;
					if (cal.getTime().compareTo(FncGlobal.dateFormat(FncGlobal.getDateSQL()))<=0) {
						receiptID = receipt_id; 
						receiptTypeID = receipt_alias;
					} else {
						receiptID = "03";
						receiptTypeID = "AR";
					}
				 */

				/*	Modified by Mann2x; Date Modified: July 5, 2017; DCRF# 159;	
					if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL())) {
				 */
				if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL()) && blnOR && blnBOI && blnLTS) {
					receiptID = receipt_id;
					receiptTypeID = receipt_alias;							
				} else {
					receiptID = "03";
					receiptTypeID = "AR";						
				}

				modelPayments.addRow(new Object[]{part_id, part_name, amount, other_lot ,payment_type, Long.toString(new_check_no++), check_type_id, check_type_name, cal.getTime(), account_no,
						bank_id, bank_name, branch_id, branch_name, null, receiptID, receiptTypeID, null, brstn, false});
			}
		}
		
		tblPMD_Payments.packAll();
	}

	
	

	public static void totalPayments(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal(0.00);

		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amount = amount.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e1) {
				amount = amount.add(new BigDecimal(0.00));
			}
		}
		modelTotal.setValueAt(amount, 0, 2);
	}



	private Boolean toIssue() {

		if(lookupClient.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client", "Issue", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(tblPMD_Payments.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input payments to issue", "Issue", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(receiptNo() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Receipt in Receipt Maintenance Module", "Issue", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	private void displayIssuedPmts() {

		modelIssuedPmts.clear();
		DefaultListModel listModel = new DefaultListModel();
		//rowHeaderPMD_IssuedPmts.setModel(listModel);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_issued_pmts_montalban()";

		db.select(SQL);

		if(db.isNotNull()) {
			for(int x= 0; x<db.getRowCount(); x++) {
				modelIssuedPmts.addRow(db.getResult()[x]);
				//listModel.addElement(modelIssuedPmts.getRowCount());
			}
		}
		scrollPMD_Issued_Pmts.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(modelIssuedPmts.getRowCount())));
		tblPMD_Issued_Pmts.packAll();
	}

	private String issuePayments() {

		String client_seqno = "";


		String entity_id = lookupClient.getValue();
		String first_name = txtFirstName.getText();
		String middle_name = txtMiddleName.getText();
		String last_name = txtLastName.getText();

		String proj_id = txtProjID.getText();
		String pbl_id = txtUnitID.getText();
		String seq_no = txtSeqNo.getText();
		BigDecimal total_amt = new BigDecimal("0.00");

		ArrayList<String> listPartID = new ArrayList<String>(); 
		ArrayList<BigDecimal> listAmount = new ArrayList<BigDecimal>();
		ArrayList<String> listLot = new ArrayList<String>();

		for(int x = 0; x<modelPayments.getRowCount(); x++) {
			listPartID.add(String.format("'%s'", (String) modelPayments.getValueAt(x, 0)));
			listAmount.add((BigDecimal) modelPayments.getValueAt(x, 2));
			listLot.add(String.format("'%s'", modelPayments.getValueAt(x, 3)));

			total_amt = total_amt.add((BigDecimal) modelPayments.getValueAt(x, 2));
		}

		String part_id = listPartID.toString().replaceAll("\\[|\\]", "");
		String amount = listAmount.toString().replaceAll("\\[|\\]", "");
		String lot = listLot.toString().replaceAll("\\[|\\]", ""); 

		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_issue_pmd_pmt_v2_montalban('"+entity_id+"', '"+first_name+"', '"+middle_name+"', '"+last_name+"' ,NULLIF('"+proj_id+"', ''), NULLIF('"+pbl_id+"', ''),NULLIF('"+seq_no+"', ''), \n" + 
				"ARRAY["+ part_id +"]::VARCHAR[], ARRAY["+ amount +"]::NUMERIC[], \n" + 
				"ARRAY["+ lot +"]::VARCHAR[],"+total_amt+", '11', '"+UserInfo.EmployeeCode+"')"; // papalitan ung branch id

		db.select(SQL, "Issue", true);
		FncSystem.out("Display SQL for issuance", SQL);

		if(db.isNotNull()) {
			client_seqno = (String) db.getResult()[0][0];

		}
		return client_seqno;
	}
	
	private Object [] saveNewPayment(DefaultTableModel model, String entity_id, String proj_id, String unit_id, String seq_no, BigDecimal totalAmount, Boolean credit_itsreal) {

		String a = "";
		String b = "";
		Object op_search_result[] = null;
		String office_branch_id = lookupOfficeBranch.getValue();
		
		String receiptTypeID [] = {"01","03","08", "307"};	
		int w = 0;
		/*System.out.println("EntityID: " + entity_id);
		System.out.println("proj_id: " + proj_id);
		System.out.println("unit_id: " + unit_id);
		System.out.println("seq_no: " + seq_no);
		System.out.println("totalAmount: " + totalAmount);*/

		while (w<=3)

		{	
			
			ArrayList<String> listPartID = new ArrayList<String>(); 
			ArrayList<BigDecimal> listAmount = new ArrayList<BigDecimal>();
			ArrayList<String> listPymntTypeID = new ArrayList<String>();
			ArrayList<String> listCheckNo = new ArrayList<String>(); 
			ArrayList<String> listCheckTypeID = new ArrayList<String>();
			ArrayList<String> listCheckDate = new ArrayList<String>();
			ArrayList<String> listAccountNo = new ArrayList<String>();
			ArrayList<String> listBankID = new ArrayList<String>(); 
			ArrayList<String> listBankBranchID = new ArrayList<String>();
			ArrayList<String> listReceiptNo = new ArrayList<String>();
			ArrayList<String> listBRSTN = new ArrayList<String>();
			ArrayList<Boolean> listCredit = new ArrayList<Boolean>();
			ArrayList<String> listLot = new ArrayList<String>();
			ArrayList<String> listDueType = new ArrayList<String>();

			int y = 0;
			Double amount_tot = 0.00;

			for (int x=0; x < model.getRowCount(); x++) {

				String receiptType_ID = (String) model.getValueAt(x, 15);				
				/*System.out.println("receiptType_ID: " + receiptType_ID);
				 */
				if (receiptTypeID[w].equals(receiptType_ID)) {		
					System.out.println();
					amount_tot	= amount_tot + Double.parseDouble(model.getValueAt(x,2).toString());	
					String check_no = (String) model.getValueAt(x, 5);
					String check_type = (String) model.getValueAt(x, 6);
					Date check_date = (Date) model.getValueAt(x, 8);
					String account_no = (String) model.getValueAt(x, 9);
					String bank_id = (String) model.getValueAt(x, 10);
					String bank_branch_id = (String) model.getValueAt(x, 12);
					String receipt_no = (String) model.getValueAt(x, 14);
					String brstn = (String) model.getValueAt(x, 18);
					Boolean credit = (Boolean) model.getValueAt(x, 19);
					listPartID.add(String.format("'%s'", (String) model.getValueAt(x, 0)));
					listAmount.add((BigDecimal) model.getValueAt(x, 2));
					listPymntTypeID.add(String.format("'%s'", (String) model.getValueAt(x, 4)));
					String Lot = (String) model.getValueAt(x, 3);
					String due_type = (String) model.getValueAt(x, 21);

					if (check_no != null) {
						listCheckNo.add(String.format("'%s'", check_no));
					} else {
						listCheckNo.add("null");
					}

					if (check_type != null) {
						listCheckTypeID.add(String.format("'%s'", check_type));
					} else {
						listCheckTypeID.add("null");
					}

					if (check_date != null) {
						listCheckDate.add(String.format("'%s'", check_date));
					} else {
						listCheckDate.add("null");
					}

					if (account_no != null) {
						listAccountNo.add(String.format("'%s'", account_no));
					} else {
						listAccountNo.add("null");
					}

					if (bank_id != null) {
						listBankID.add(String.format("'%s'", bank_id));
					} else {
						listBankID.add("null");
					}

					if (bank_branch_id != null) {
						listBankBranchID.add(String.format("'%s'", bank_branch_id));
					} else {
						listBankBranchID.add("null");
					}

					if (receipt_no != null) {
						listReceiptNo.add(String.format("'%s'", receipt_no));
					} else {
						listReceiptNo.add("null");
					}

					if (brstn != null) {
						listBRSTN.add(String.format("'%s'", brstn));
					} else {
						listBRSTN.add("null");
					}

					if (Lot != null) {
						listLot.add(String.format("'%s'", Lot));
					} else {
						listLot.add("null");
					}

					if (due_type != null) {
						listDueType.add(String.format("'%s'", due_type));
					} else {
						listDueType.add("null");
					}

					y++;
				} else {

				}
			}

			String part_id = listPartID.toString().replaceAll("\\[|\\]", "");
			String amount = listAmount.toString().replaceAll("\\[|\\]", "");
			String pymnttype = listPymntTypeID.toString().replaceAll("\\[|\\]", "");
			String check_no = listCheckNo.toString().replaceAll("\\[|\\]", "");
			String check_type = listCheckTypeID.toString().replaceAll("\\[|\\]", "");
			String check_date = listCheckDate.toString().replaceAll("\\[|\\]", "");
			String account_no = listAccountNo.toString().replaceAll("\\[|\\]", "");
			String bank_id = listBankID.toString().replaceAll("\\[|\\]", "");
			String bank_branch_id = listBankBranchID.toString().replaceAll("\\[|\\]", "");
			String receipt_no = listReceiptNo.toString().replaceAll("\\[|\\]", "");
			String brstn = listBRSTN.toString().replaceAll("\\[|\\]", "");
			String credit = listCredit.toString().replaceAll("\\[|\\]", "");
			String lot = listLot.toString().replaceAll("\\[|\\]", ""); 
			String duetype = listDueType.toString().replaceAll("\\[|\\]", "");

			if (y>0)
			{
				String SQL = "SELECT * from sp_op_post_new_v2(" +
						/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
						 * be determined depending on which server the user logs in to.
				"'"+ UserInfo.Branch +"', " +
						 */
						 "'"+office_branch_id+"', " +
						 "'"+ entity_id +"', " +
						 "'"+ proj_id +"', " +
						 "'"+ unit_id +"', " +
						 ""+ seq_no +", " +
						 //""+ totalAmount +",\n" + 
						 ""+ amount_tot +",\n" + //-changed by Del Gonzales 06/02/2016
						 "  ARRAY["+ part_id +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ amount +"]::NUMERIC[],\n" + 
						 "  ARRAY["+ pymnttype +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
						 "  ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[],\n" + 
						 "  NULL, \n" + 
						 "  ARRAY["+ (brstn.equals("'null'") ? "null":brstn) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ credit +"]::BOOLEAN[], \n" +
						 "  ARRAY["+ lot +"]::VARCHAR[], \n" +
						 "  ARRAY["+ duetype +"]::VARCHAR[], \n" +
						 "  '"+ UserInfo.EmployeeCode +"', "+credit_itsreal+");";

				FncSystem.out("SQL", SQL);
				pgSelect db = new pgSelect();
				db.select(SQL);

				if(db.isNotNull()){
					op_search_result = db.getResult()[0];
				}else{
					op_search_result = null;
				}	

				a = db.getResult()[0][0].toString();
				b = b + ": " + db.getResult()[0][1].toString();
			}
			else 
			{}

			w++;
		}

		op_search_result[0] = a;
		op_search_result[1] = b;

		return op_search_result;
	}

	private void removePayment() {
		int[] rows = tblPMD_Payments.getSelectedRows();

		for(int x = rows.length - 1; x >= 0; x--){
			modelPayments.removeRow(rows[x]);
		}
	}

	private void previewReceipt_orig(String client_seqno) {
		System.out.println("receipt No: "+receiptNo());
		System.out.println("receipt No Old: "+receipt_no_old);
		System.out.println("client seq_no: " + client_seqno);
		String credited_ar_no = "";
		String recpt_type = "";
		Double credited_amount = 0.00; 

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("client_seqno", client_seqno);
		mapParameters.put("ar_no", receipt_no_old);
		mapParameters.put("credit_ar_no", credited_ar_no);
		mapParameters.put("recpt_type", recpt_type);
		mapParameters.put("credited_amount", credited_amount);
		mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
		System.out.printf("recpt_type : " + recpt_type);
		System.out.printf("credited_amount : " + credited_amount);
		String co_id = FncGlobal.GetString("select b.co_id from rf_payments a \n"
				+ "left join mf_project b on a.proj_id = b.proj_id\n" + "where a.client_seqno = '"
				+ client_seqno + "'");

		FncReport.generateReport("/Reports/rptARReceipt_VDC.jasper", "Receipt Report", mapParameters);
	}
	
	private void previewReceipt(String client_seqno) {
		

		//String client_seqno = (String) modelIssuedPmts.getValueAt(selected_row, 0);
		String receipt_no = "";
		String receipt_type = "";
		String pymt_type = "";
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT (CASE WHEN a.or_doc_id IS NOT NULL THEN 'OR'\n"+
					 "  					 WHEN a.pr_doc_id IS NOT NULL THEN 'AR' \n"+
					 "  					 WHEN a.si_doc_id IS NOT NULL THEN 'SI' ELSE NULL END) as receipt_type, "+
					 "a.pymnt_type, b.receipt_no, a.client_seqno \n"+
					 "FROM rf_payments a \n"+
					 "LEFT JOIN rf_pay_detail b on b.client_seqno = a.client_seqno \n"+
					 "where a.client_seqno = TRIM(REPLACE('"+client_seqno+"', ':', '')) LIMIT 1";
		db.select(SQL);
		FncSystem.out("Display SQL after issuance", SQL);
		
		if(db.isNotNull()) {
			receipt_type = (String) db.getResult()[0][0];
			pymt_type = (String) db.getResult()[0][1];
			receipt_no = (String) db.getResult()[0][2];
			client_seqno = (String) db.getResult()[0][3];
		}
		
		if(tblPMD_Issued_Pmts.getSelectedRowCount() > 1) {
			int selected_row = tblPMD_Issued_Pmts.convertRowIndexToModel(tblPMD_Issued_Pmts.getSelectedRow());
			receipt_no = (String) modelIssuedPmts.getValueAt(selected_row, 8);
		}
		
		if(isLedgerApplied(client_seqno) && isLedgerAppliedCorrect(client_seqno)) {
			System.out.println("Dumaan dito sa Print Receipt");
			
			String office_branch_id = lookupOfficeBranch.getValue();
			
			if(receipt_type.equals("OR")){
				
				System.out.println("Dumaan dito sa Print Receipt OR");

				Object[] rcpt_dtl = getReceiptDtls(receipt_no, client_seqno);
				String credited_ar_no = "", recpt_type = "";
				Double credited_amount = null; 
				try { credited_ar_no = rcpt_dtl[0].toString();} catch (NullPointerException x) { credited_ar_no = ""; }
				try { recpt_type = rcpt_dtl[2].toString();} catch (NullPointerException x) { recpt_type = ""; }
				try { credited_amount = Double.parseDouble(rcpt_dtl[1].toString());} catch (NullPointerException x) { credited_amount = 0.00; }

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("client_seqno", client_seqno);
				mapParameters.put("or_no", receipt_no);
				mapParameters.put("credit_ar_no", credited_ar_no);
				mapParameters.put("recpt_type", recpt_type);
				mapParameters.put("credited_amount", credited_amount);
				mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
				String co_id = FncGlobal.GetString("select co_id from rf_payments a \n" + 
						"where client_seqno = TRIM(REPLACE('"+client_seqno+"', ':', '')) and or_no = '"+receipt_no+"' ");

				//FncReport.generateReport("/Reports/rptORReceipt_EDC.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
				//FncReport.generateReport("/Reports/rptSalesInvoice_VDC.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
				
				if(office_branch_id.equals("10")) {
					FncReport.generateReport("/Reports/rptORReceipt_v2.jasper", "Official Receipt", String.format("OR No.: %s", receipt_no), mapParameters);		
				}else {
					if(isRetFeeOnline(client_seqno)) {
					//FncReport.generateReport("/Reports/rptOR_RetFeeOL.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
					//FncReport.generateReport("/Reports/rpt_RetFeeOL_CDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
					}else if(co_id.equals("05")) {
						FncReport.generateReport("/Reports/rptORReceipt_EDC.jasper", "Official Receipt", String.format("OR No.: %s", receipt_no), mapParameters);
					}else {
						FncReport.generateReport("/Reports/rptORReceipt.jasper", "Official Receipt", String.format("OR No.: %s", receipt_no), mapParameters);
						
					}
				}
			}

			if(receipt_type.equals("AR")){	
				
				System.out.println("Dumaan dito sa Print Receipt AR");

				Object[] rcpt_dtl = getReceiptDtls(receipt_no, client_seqno);
				String credited_ar_no = "", recpt_type = "";
				Double credited_amount = null; 
				try { credited_ar_no = rcpt_dtl[0].toString();} catch (NullPointerException x) { credited_ar_no = ""; }
				try { recpt_type = rcpt_dtl[2].toString();} catch (NullPointerException x) { recpt_type = ""; }
				try { credited_amount = Double.parseDouble(rcpt_dtl[1].toString());} catch (NullPointerException x) { credited_amount = 0.00; }
				
				System.out.printf("Value of client_seqno: %s%n", client_seqno);
				System.out.printf("Value of receipt no: %s%n", receipt_no);
				System.out.printf("Value of credited_ar_no: %s%n", credited_ar_no);
				System.out.printf("Value of receipt type: %s%n", receipt_type);
				System.out.printf("value of credited amt: %s%n", credited_amount);
				System.out.printf("value of prepared by: %s%n", UserInfo.Alias.toUpperCase());
				System.out.printf("value of pmt_type: %s%n", pymt_type);

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("client_seqno", client_seqno);
				mapParameters.put("ar_no", receipt_no);
				mapParameters.put("credit_ar_no", credited_ar_no);
				mapParameters.put("recpt_type", recpt_type);
				mapParameters.put("credited_amount", credited_amount);
				mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
				mapParameters.put("pymnt_type", pymt_type);
				
				System.out.printf("recpt_type : " + recpt_type);
				System.out.printf("credited_amount : " + credited_amount);
				/*String co_id = FncGlobal.GetString("select b.co_id from rf_payments a \n" + 
						"left join mf_project b on a.proj_id = b.proj_id\n" + 
						"where a.client_seqno = '"+client_seqno+"'");*/
				String co_id = FncGlobal.GetString("select co_id from rf_pay_header a \n" + 
						"where client_seqno = TRIM(REPLACE('"+client_seqno+"', ':', ''))");
				
				
				//FncReport.generateReport("/Reports/rptARReceipt_EDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
				
				//FncReport.generateReport("/Reports/rptARReceipt_2.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
				//FncReport.generateReport("/Reports/rptARReceipt_VDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
				//Added by Erick for migration
				
				 if (isRetFeeOnlineMP(client_seqno)) {
					FncReport.generateReport("/Reports/rptARRetFeeOL_CDC.jasper", "Acknowledgment Receipt", String.format("AR No.: %s", receipt_no), mapParameters);
				} else if(co_id.equals("01")) {
					FncReport.generateReport("/Reports/rptARReceipt_VDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", receipt_no), mapParameters);
				}else if(co_id.equals("02")) {
					FncReport.generateReport("/Reports/rptARReceipt_CDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", receipt_no), mapParameters);
				}else if(co_id.equals("04")) {
					FncReport.generateReport("/Reports/rptARReceipt_ADC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", receipt_no), mapParameters);
				}else if(co_id.equals("05")) {
					FncReport.generateReport("/Reports/rptARReceipt_EDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", receipt_no), mapParameters);
				}
			}
			//ADDED BY MONIQUE FOR SALES INVOICE DTD 2022-07-11 WITH DCRF#2121
			if(receipt_type.equals("SI")){	

				Object[] rcpt_dtl = getSalesInvoiceDtls(receipt_no, client_seqno);
				String credited_ar_no = "", recpt_type = "";
				Double credited_amount = null; 
				try { credited_ar_no = rcpt_dtl[0].toString();} catch (NullPointerException x) { credited_ar_no = ""; }
				try { recpt_type = rcpt_dtl[2].toString();} catch (NullPointerException x) { recpt_type = ""; }
				try { credited_amount = Double.parseDouble(rcpt_dtl[1].toString());} catch (NullPointerException x) { credited_amount = 0.00; }


				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("client_seqno", client_seqno);
				mapParameters.put("si_no", receipt_no);
				mapParameters.put("credit_ar_no", credited_ar_no);
				mapParameters.put("recpt_type", recpt_type);
				mapParameters.put("credited_amount", credited_amount);
				mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
				System.out.printf("recpt_type : " + recpt_type);
				System.out.printf("credited_amount : " + credited_amount);
				String co_id = FncGlobal.GetString("select co_id from rf_pay_header a \n" + 
						"where client_seqno = TRIM(REPLACE('"+client_seqno+"', ':', ''))");
				
				if(isRetFeeOnline(client_seqno)) {
					FncReport.generateReport("/Reports/rpt_RetFeeOL_CDC.jasper", "Sales Invoice", String.format("SI No.: %s", receipt_no), mapParameters);
				} else if(co_id.equals("01") || co_id.equals("04")) {
					FncReport.generateReport("/Reports/rptSalesInvoice_VDC.jasper", "Sales Invoice", String.format("SI No.: %s", receipt_no), mapParameters);
				}else if(co_id.equals("02")) {
					FncReport.generateReport("/Reports/rptSalesInvoice_CDC.jasper", "Sales Invoice", String.format("SI No.: %s", receipt_no), mapParameters);
				}
			}

			
		}else {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Payment application not in order. Contact JST.", "Preview", JOptionPane.WARNING_MESSAGE);
		}
	}

	private String OtherLotSelection() {
		String strOtherLot = ""; 
		String strSQL = "select unnest(string_to_array(z.lot|| '-' ||y.lot, '-')) as lots \n" +
				"FROM hs_sold_other_lots x \n" +
				"LEFT JOIN mf_unit_info y on y.proj_id = x.proj_id and y.pbl_id = x.oth_pbl_id \n" +
				"LEFT JOIN mf_unit_info z on z.proj_id = x.proj_id and z.pbl_id = x.pbl_id \n" +
				"where x.entity_id = '"+lookupClient.getValue()+"' and x.proj_id = '"+txtProjID.getText()+"' \n" +
				"and z.pbl_id = '"+txtUnitID.getText()+"' and x.seq_no::int = "+txtSeqNo.getText()+"::int and x.status_id = 'A'";

		pgSelect dbExec = new pgSelect(); 
		final JComboBox<String> combo = new JComboBox<String>();

		dbExec.select(strSQL);

		System.out.println("");
		System.out.println("strSQL: "+strSQL);

		if (dbExec.isNotNull()) {
			for (int x=0; x<dbExec.getRowCount();x++) {
				combo.addItem(dbExec.getResult()[x][0].toString()); 
			}
		}

		String[] options = {
				"OK", 
				"CANCEL"
		};

		String title = "Select Lot";

		int selection = JOptionPane.showOptionDialog(null, combo, title,
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		System.out.println("selection: " + selection);

		if (selection==0) {
			System.out.println("selected lot: " + combo.getSelectedItem().toString());

			strOtherLot = FncGlobal.GetString("select a.pbl_id \n" +
					"from \n" +
					"( \n" +
					"select unnest(string_to_array(z.lot|| '-' ||y.lot, '-')) as lot, \n" + 
					"unnest(string_to_array(z.pbl_id|| '-' ||y.pbl_id, '-')) as pbl_id \n" +
					"from hs_sold_other_lots x \n" +
					"left join mf_unit_info y on y.proj_id = x.proj_id and y.pbl_id = x.oth_pbl_id \n" + 
					"left join mf_unit_info z on z.proj_id = x.proj_id and z.pbl_id = x.pbl_id \n" +
					"where x.entity_id = '"+lookupClient.getValue()+"' and x.proj_id = '"+txtProjID.getText()+"' \n" +
					"and z.pbl_id = '"+txtUnitID.getText()+"' and x.seq_no::int = "+txtSeqNo.getText()+"::int and x.status_id = 'A' \n" +
					") a \n" +
					"where a.lot = '"+combo.getSelectedItem().toString()+"'");
		} else {
			strOtherLot = "";
		}

		return strOtherLot; 
	}

	private String companyID() {
		pgSelect db = new pgSelect();
		String SQL = "SELECT co_id from mf_project where proj_id = '"+proj_id+"'";
		db.select(SQL);

		return (String) db.getResult()[0][0];
	}

	private String receiptNo() {
		String receipt_no = null;

		pgSelect db = new pgSelect();										  //brach id
		String SQL = "SELECT get_receipt_no_to_issue('"+companyID()+"', '03', '11', '"+UserInfo.EmployeeCode+"')"; // papalitan
		//System.out.print(client_seqno_global);
		//		String SQL = "Select or_no from rf_payments where client_seqno = '"+client_seqno_global+"' and created_by = '"+UserInfo.EmployeeCode+"'"; 
		System.out.println(SQL);
		db.select(SQL);

		if(db.isNotNull()) {
			receipt_no = (String) db.getResult()[0][0];
		}

		return receipt_no;
	}

	private void ActivateLotLabel(String strSelectedLot) {
		if (strSelectedLot.equals("")) {
			lblLotRemarks.setText("");
		} else {
			lblLotRemarks.setText("Payment for Lot: "+strSelectedLot);
		}
	}

	private void newPMD_Pmt() {

		lookupClient.setEditable(true);
		//		lookupClient.setValue(_pmd_payments.getNewEntityID());
		lookupClient.setValue("");
		txtFirstName.setEditable(true);
		txtMiddleName.setEditable(true);
		txtLastName.setEditable(true);
		lookupParticular.setEditable(true);
		txtAmount.setEditable(true);
		cmbPmtType.setEnabled(true);

		btnState(true, false, true, true, true, true);
	}

	private void cancelPMD_Pmt() {

		lookupClient.setEditable(false);
		lookupClient.setValue(null);
		txtFirstName.setText("");
		txtFirstName.setEditable(false);
		txtMiddleName.setText("");
		txtMiddleName.setEditable(false);
		txtLastName.setText("");
		txtLastName.setEditable(false);

		//txtClient.setText("");
		txtProjID.setText("");
		txtProject.setText("");
		txtUnitID.setText("");
		txtUnitDesc.setText("");
		txtSeqNo.setText("");
		//btnState(false, false, false, sCancel, sPreview);
		lookupParticular.setValue(null);
		lookupParticular.setEditable(false);
		txtParticular.setText("");
		txtAmount.setValue(new BigDecimal("0.00"));
		cmbPmtType.setSelectedIndex(0);
		txtCheckNo.setText("");
		txtAcctNo.setText("");
		dateCheckDate.setDate(null);
		txtNoOfChecks.setValue(null);
		txtBRSTN.setText("");
		lookupBank.setValue(null);
		txtBank.setText("");
		lookupBankBranch.setValue(null);
		txtBankBranch.setText("");
		
		txtAmount.setEditable(false);
		cmbPmtType.setEnabled(false);
		cmbCheckType.setEnabled(false);
		dateCheckDate.setEnabled(false);
		dateCheckDate.getCalendarButton().setEnabled(false);
		txtNoOfChecks.setEditable(false);
		lookupBank.setEnabled(false);
		lookupBankBranch.setEnabled(false);

		modelPayments.clear();
		rowHeaderPMD_Payments.setModel(new DefaultListModel());

		with_other_lot = false;
		other_lot = "";

		btnState(false, true, false, false, false, true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Post")) {
			//postPmt();
			
			String pmt_type = (String) cmbPmtType.getSelectedItem();
			
			if(pmt_type.equals("Cash")) {
				postCashPmt();
			}else {
				postCheckTransaction();
			}
			
			lookupParticular.setValue(null);
			txtParticular.setText("");
			txtAmount.setValue(new BigDecimal("0.00"));
			cmbCheckType.setSelectedIndex(0);
			dateCheckDate.setDate(null);
			txtCheckNo.setText("");
			txtAcctNo.setText("");
			txtNoOfChecks.setValue(null);
			txtBRSTN.setText("");
			lookupBank.setValue(null);
			txtBank.setText("");
			lookupBankBranch.setValue(null);
			txtBankBranch.setText("");
			
		}

		if(actionCommand.equals("New")) {
			if(lookupOfficeBranch.getValue() != null) {
				newPMD_Pmt();
			}else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select office branch before proceeding with transactions", "New", JOptionPane.WARNING_MESSAGE);
			}
			
		}

		if(actionCommand.equals("Issue Payment")) {
			//if(toIssue()) {
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to issue payment?", "Issue Payment", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					//String client_seqno = issuePayments();
					
					BigDecimal totalAmount = (BigDecimal) modelPayments.getValueAt(0, 2);
					String office_branch_id = lookupOfficeBranch.getValue();
					
					
					Object[] op_dtl = saveNewPayment(modelPayments, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), totalAmount, false);
					String client_seqno = op_dtl[1].toString();	
					
					System.out.printf("Value of clientseqno: %s%n", client_seqno);
					
					pgSelect db = new pgSelect();
					String SQL = "";
					
					if(isSalesInvoice(client_seqno)) {
						SQL = "SELECT sp_ir_post_ordered_v2(TRIM(REPLACE('"+client_seqno+"', ':', '')), '"+ office_branch_id +"', '"+ UserInfo.EmployeeCode +"');";
					}else {
						SQL = "SELECT sp_ir_post_ordered(TRIM(REPLACE('"+client_seqno+"', ':', '')), '"+ office_branch_id +"', '"+ UserInfo.EmployeeCode +"');";
					}
					
					FncSystem.out("Display SQL for issuance", SQL);
					db.select(SQL, "Issue Receipt", true);
					
					Boolean issued = (Boolean) db.getResult()[0][0];
							
					if(issued) {
						displayIssuedPmts();
						cancelPMD_Pmt();

						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Payment sucessfully issued", "Issue", JOptionPane.INFORMATION_MESSAGE);
						previewReceipt(client_seqno);

					}
				}
			//}
		}

		if(actionCommand.equals("Remove Payment")) {
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to remove payment?", "Remove", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
				removePayment();
			}
		}

		if(actionCommand.equals("Cancel")) {
			cancelPMD_Pmt();
		}

		if(actionCommand.equals("Preview Receipt")) {
			if(tblPMD_Issued_Pmts.getSelectedRows().length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select payment to preview", "Preview", JOptionPane.WARNING_MESSAGE);
			}else {
				int selected_row = tblPMD_Issued_Pmts.convertRowIndexToModel(tblPMD_Issued_Pmts.getSelectedRow());

				String client_seqno = (String) modelIssuedPmts.getValueAt(selected_row, 0);
//				String ar_no = (String) modelIssuedPmts.getValueAt(selected_row, 8);
//
//				String credited_ar_no = "", recpt_type = "";
//				Double credited_amount = 0.00; 
//
//				Map<String, Object> mapParameters = new HashMap<String, Object>();
//				System.out.println("Project ID"+proj_id);
//				mapParameters.put("client_seqno", client_seqno);
//				mapParameters.put("ar_no", ar_no);
//				mapParameters.put("credit_ar_no", credited_ar_no);
//				mapParameters.put("recpt_type", recpt_type);
//				mapParameters.put("credited_amount", credited_amount);
//				mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
//				System.out.printf("recpt_type : " + recpt_type);
//				System.out.printf("credited_amount : " + credited_amount);
//				String co_id = FncGlobal.GetString("select b.co_id from rf_payments a \n"
//						+ "left join mf_project b on a.proj_id = b.proj_id\n" + "where a.client_seqno = '"
//						+ client_seqno + "'");
//
//				FncReport.generateReport("/Reports/rptARReceipt_VDC.jasper", "Receipt Report", mapParameters);

				previewReceipt(client_seqno);
			}
		}

	}

	private static class _pmd_payments {

		public static String sqlClients() {
			/*return "select a.entity_id as \"Entity ID\",b.entity_name as \"Client\", \n" + 
					"a.projcode as \"Proj. ID\", d.proj_name \"Project\", \n" + 
					"a.pbl_id as \"PBL\", (CASE WHEN e.oth_pbl_id IS null then FORMAT('%s - %s', d.proj_alias,c.description) else FORMAT('%s/%s', FORMAT('%s - %s', d.proj_alias,c.description), f.lot) end) as \"Unit\", \n" + 
					"a.seq_no as \"Seq. No\",\n" +
					"(CASE WHEN e.oth_pbl_id is null then false else true end) as \"With Other Lot\",\n"+
					"b.first_name as \"First Name\", b.middle_name as \"Middle Name\", b.last_name \"Last Name\" \n"+
					"from rf_sold_unit a\n" + 
					"left join rf_entity b on b.entity_id = a.entity_id \n" + 
					"LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id\n" + 
					"LEFT JOIN mf_project d on d.proj_id = a.projcode\n" + 
					"LEFT JOIN hs_sold_other_lots e on e.entity_id = a.entity_id and e.proj_id = a.projcode and e.pbl_id = a.pbl_id and e.seq_no = a.seq_no\n" + 
					"LEFT JOIN mf_unit_info f on f.proj_id = a.projcode and f.pbl_id = e.oth_pbl_id\n" + 
					"--LEFT JOIN mf_product_model g on g.model_id = a.model_id\n" + 
					"where a.currentstatus != '02'\n" + 
					"and a.status_id != 'I'\n" + 
					"ORDER BY b.entity_name;";*/

			return "SELECT * FROM view_clients_issuance_montalban()";
		}

		public static String sqlParticular() {
			return "SELECT a.pay_part_id as \"ID\", a.partdesc \"Desc\" \n" + 
					"FROM mf_pay_particular a\n" + 
					"WHERE a.pay_part_id in ('223', '224', '197', '083', '200', '180');";
		}
		

		public static String getNewEntityID(){

			pgSelect db = new pgSelect();

			String SQL = "SELECT get_new_entity_id()";
			db.select(SQL);
			//FncSystem.out("Display New Entity ID", SQL);

			return (String) db.getResult()[0][0];
		}

	}
	
	private String sqlParticular() {
		String SQL = "SELECT a.pay_part_id as \"ID\", a.partdesc as \"Desc\", b.doc_id as \"Receipt ID\", b.doc_desc as \"Receipt Type\"\n"
				+ "					FROM mf_pay_particular a\n"
				+ "					LEFT JOIN mf_doc_details b on b.doc_id = a.receipt_to_issue\n"
				+ "					WHERE a.pay_part_id in ('223', '224', '197', '083', '200', '180', '033', '263', '041', '042', '262', '040', '193', \n"
				+ "											'182', '268', '271', '266', '267', '206', '207', '209', '208', '276', '270', '174', '198', '276', '195') \n"
				+ "and a.status_id = 'A';";
	
		return SQL;
	}
	
	public static Boolean isSalesInvoice(String client_seqno) {
		//String SQL = "SELECT EXISTS (SELECT * FROM rf_pay_detail where client_seqno IN ('"+client_seqno+"') AND client_seqno NOT IN ('010220824003', '010220824004') AND part_type in ('087', '259', '262'))";
		//MODIFIED BY MONIQUE DTD 12-20-2022; TO FILTER OR ACCOUNTS FOR ISSUANCE OF SI 
		String SQL = "SELECT EXISTS (SELECT * FROM rf_pay_detail a \n" +
					 "LEFT JOIN rf_pay_header b ON b.client_seqno = a.client_seqno \n" +
				     "WHERE a.client_seqno IN (TRIM(REPLACE('"+client_seqno+"', ':', ''))) \n" +
				     "AND a.client_seqno NOT IN ('010220824003', '010220824004') \n" + 
				     "AND a.part_type in ('087', '259', '262') \n" +
				     "AND EXISTS (Select * from rf_buyer_status where entity_id = b.entity_id and proj_id = b.proj_id and pbl_id = b.pbl_id and seq_no = b.seq_no \n" +
					 "		  		and TRIM(byrstatus_id) = '01' and TRIM(status_id) = 'A'))"; 
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
		
	}

	private Object[] getCheckTypes() {
		pgSelect db = new pgSelect();
		db.select("select format('%s (%s)', check_desc, check_id) from mf_check_type order by rec_id;");

		if(db.isNotNull()){
			ArrayList<Object> listCheckType = new ArrayList<Object>();
			for(int x=0; x < db.getRowCount(); x++){
				listCheckType.add(db.getResult()[x][0]);
			}
			return listCheckType.toArray();
		}else{
			return null;
		}
	}

	private Boolean pastDuePriorECQ(String strEntityID, String strProjID, String strPBLID, String strSeqNo) {
		return FncGlobal .GetBoolean("select exists(SELECT * FROM view_card_dues_regular('"+strEntityID+"', '"+strProjID+"', '"+strPBLID+"', '"+strSeqNo+"'::int, current_date::TIMESTAMP, FALSE) where c_scheddate::date < '2020-03-17'::date)"); 
	}
	
	private Boolean hasLTS_BOI(String unit_id) {

		Boolean a = false;

		String SQL = 
				"select sub_proj_id from mf_sub_project " +
						"where lts_date is not null " +
						"and boi is not null " +
						"and sub_proj_id in (select sub_proj_id from mf_unit_info where unit_id = '"+unit_id+"') AND status_id = 'A' " ;//ADDED STATUS ID BY LESTER DCRF 2718

		System.out.printf("SQL #1: hasHoldingFee" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			a = true;
		}else{
			a = false;
		}

		return a;
	}
	
	private String getBranch() {
		return "SELECT branch_id, branch_name, branch_alias FROM mf_office_branch;";
	}
	
	private Boolean isLedgerApplied(String client_seqno) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT is_ledger_applied('"+client_seqno+"')";
		db.select(SQL);

		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		}else {
			return false;
		}
	}

	private Boolean isLedgerAppliedCorrect(String client_seqno) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT is_ledger_applied_correct('"+client_seqno+"')";
		db.select(SQL);

		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		}else {
			return false;
		}
	}
	
	private Boolean isRetFeeOnline(String client_seqno) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_payments where client_seqno = '"+client_seqno+"' AND remarks ~*'Ret Fee Online' AND status_id = 'A' AND or_no IS NOT NULL AND pr_doc_id = '03')";
		db.select(SQL);

		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];	
		}else {
			return false;
		}
	}
	
	public static Object [] getReceiptDtls(String recpt_no, String strNo) {
		String strSQL = "select receipt_id, amount, " +
				"(case when or_date is not null then 'OR No.' else " +
				"	case when pr_doc_id = '03' then 'AR No. ' else 'PR No. ' end end) " +
				"from rf_payments where or_no = '"+recpt_no+"' and client_seqno = TRIM(REPLACE('"+strNo+"', ':', '')) order by receipt_id \r\n" ;

		System.out.printf("SQL #1 getReceiptDtls: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}
	
	private Boolean isRetFeeOnlineMP(String client_seqno) { //ADDED BY MONIQUE DTD 9-14-2022; FOR TAGGED ACCNTS ON RETFEEOL (MULTIPAYMENTS)
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_payments where client_seqno = '"+client_seqno+"' AND /*remarks ~*'Ret Fee Online'*/ pay_part_id in ('218', '246', '247') AND status_id = 'A' AND  pr_doc_id = '03')"; //DCRF #2193
		db.select(SQL);

		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		}else {
			return false;
		}
	}
	
	public static Object [] getSalesInvoiceDtls(String recpt_no, String strNo) {
		String strSQL = "select receipt_id, amount " +
				"from rf_payments where si_no = '"+recpt_no+"' and client_seqno = TRIM(REPLACE('"+strNo+"', ':', '')) order by receipt_id \r\n" ;

		System.out.printf("SQL #1 getReceiptDtls: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}
}
