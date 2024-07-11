package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.poi.ss.formula.functions.Today;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

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
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JFormattedTextField;
import components._JInternalFrame;
import components._JScrollPane;
import components._JScrollPaneMain;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelByrPmtPosting_CRB;
import tablemodel.modelLoanReleased;
import tablemodel.model_hdmf_postInspection_card;
import tablemodel.model_retention_details;

public class RetentionFeeBOI extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 2321370387432898517L;
	private static String title = "Retention Fee (BOI)"; 
	Border line = BorderFactory.createLineBorder(Color.GRAY);
	Dimension frameSize = new Dimension(650, 625);
	
	private _JLookup txtClientID;
	
	private JTextField txtClient;
	private JTextField txtProjectID;
	private JTextField txtProject;
	private JTextField txtUnitID;
	private JTextField txtUnit;
	private JTextField txtSeqNo;
	
	private _JDateChooser dteDate;
	private _JXFormattedTextField txtLoanableAmount;
	private JTextField txtPaySeq;
	
	private JTextField txtAcctNo;
	private JTextField txtCheckNo;
	private _JDateChooser dteCheckDate;
	private _JLookup txtBankID;
	private _JLookup txtBankBranchID;
	private JTextField txtBank;
	private JTextField txtBranch;
	
	private JXPanel panGen;
	private JLabel lblGen;
	
	private JComboBox cboRet;
	private _JLookup txtReceiptTypeID;
	private JTextField txtReceiptType;
	private JTextField txtReceiptTypeNo;
	
	private static _JXFormattedTextField txtPctg;
	private static _JXFormattedTextField txtAmount;
	
	private JButton btnClear;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	
	private static _JXFormattedTextField txtTotalRetention;
	
	private JTabbedPane tabRet; 
	private JXPanel panDetails;
	private JXPanel panJV; 
	
	private Font font10b = FncLookAndFeel.systemFont_Bold.deriveFont(10f);
	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private Font font11b = FncLookAndFeel.systemFont_Bold.deriveFont(11f);
	private Font font14 = FncLookAndFeel.systemFont_Plain.deriveFont(14f);
	private Font font14b = FncLookAndFeel.systemFont_Bold.deriveFont(14f);
	
	private JXPanel panOther; 
	private JXPanel panCheck; 
	private JXPanel panRetentionType;
	private JXPanel panAmount; 
	
	private static _JXFormattedTextField txtVarPayRet;
	private static _JXFormattedTextField txtPayRet21;
	private static _JXFormattedTextField txtPayRet22;
	
	private static _JXFormattedTextField txtVarBalRet;
	private static _JXFormattedTextField txtBalRet21;
	private static _JXFormattedTextField txtBalRet22;
	
	private JScrollPane scrRetention;
	private _JTableMain tblRetention;
	private model_retention_details modelRetention;
	private JList rowRetention;
	
	private JScrollPane scrEntries;
	private _JTableMain tblEntries;
	private modelByrPmtPosting_CRB modelEntries;
	private JList rowEntries;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	private JLabel lblVarRet; 
	
	public RetentionFeeBOI() {
		super(title, true, true, false, true);
		initGUI();
	}

	public RetentionFeeBOI(String title) {
		super(title, true, true, false, true);
		initGUI();
	}

	public RetentionFeeBOI(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(3, 3));
		getContentPane().add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(3, 3, 3, 3));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(3, 3)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 266));
				{
					{
						JXPanel panPage1 = new JXPanel(new BorderLayout(3, 3)); 
						panPage.add(panPage1, BorderLayout.PAGE_START); 
						panPage1.setPreferredSize(new Dimension(0, 100));
						{
							JXPanel panName = new JXPanel(new GridLayout(3, 1, 3, 3)); 
							panPage1.add(panName, BorderLayout.CENTER); 
							panName.setBorder(JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									JXPanel panNameDet = new JXPanel(new BorderLayout(3, 3)); 
									panName.add(panNameDet); 
									{
										{
											JXPanel panIDLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
											panNameDet.add(panIDLabel, BorderLayout.LINE_START); 
											panIDLabel.setPreferredSize(new Dimension(150, 0));
											{
												{
													lblGen = new JLabel("Name"); 
													panIDLabel.add(lblGen); 
													lblGen.setHorizontalAlignment(JTextField.LEADING);
													lblGen.setFont(font11);
												}
												{
													String strSQL = "select a.entity_id, b.entity_name as name, a.proj_id, c.proj_name, a.pbl_id, d.description, a.seq_no \n" +
															"from rf_buyer_status a \n" +
															"inner join rf_entity b on a.entity_id = b.entity_id \n" +
															"inner join mf_project c on a.proj_id = c.proj_id \n" + 
															"inner join mf_unit_info d on a.proj_id = d.proj_id and a.pbl_id = d.pbl_id \n" +
															"where TRIM(a.byrstatus_id) = '32' and a.status_id = 'A' \n" +
															"order by b.entity_name"; 
													
													txtClientID = new _JLookup(); 
													panIDLabel.add(txtClientID, BorderLayout.LINE_START); 
													txtClientID.setLookupSQL(strSQL);
													txtClientID.setReturnColumn(1);
													txtClientID.setHorizontalAlignment(JTextField.CENTER);
													txtClientID.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															
															if (data!=null) {
																txtClientID.setValue((String) data[0].toString());
																txtClient.setText((String) data[1].toString());
																txtProjectID.setText((String) data[2].toString());
																txtProject.setText((String) data[3].toString());
																txtUnitID.setText((String) data[4].toString());
																txtUnit.setText((String) data[5].toString());
																txtSeqNo.setText((String) data[6].toString());
																
																txtPaySeq.setText("");
																txtAmount.setValue(new BigDecimal("0.00"));
																txtPctg.setValue(new BigDecimal("0.00"));
																
																ctrlState(true, false, false, false, true); 
																GenFees(); 
																DisplayRetDetails(); 
																DisplayRetEntries(); 
																ComboboxAction(); 
															}
														}
													});
												}
											}
											
										}
										{
											txtClient = new JTextField(""); 
											panNameDet.add(txtClient, BorderLayout.CENTER); 
											txtClient.setHorizontalAlignment(JTextField.CENTER);
											txtClient.setEditable(false);
										}
									}
								}
								{
									JXPanel panProject = new JXPanel(new BorderLayout(3, 3)); 
									panName.add(panProject); 
									{
										{
											JXPanel panIDLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
											panProject.add(panIDLabel, BorderLayout.LINE_START); 
											panIDLabel.setPreferredSize(new Dimension(150, 0));
											{
												{
													lblGen = new JLabel("Project"); 
													panIDLabel.add(lblGen); 
													lblGen.setHorizontalAlignment(JTextField.LEADING);
													lblGen.setFont(font11);
												}
												{
													txtProjectID = new JTextField(""); 
													panIDLabel.add(txtProjectID); 
													txtProjectID.setHorizontalAlignment(JTextField.CENTER);
													txtProjectID.setEditable(false); 
												}
											}
											
										}
										{
											txtProject = new JTextField(""); 
											panProject.add(txtProject, BorderLayout.CENTER); 
											txtProject.setHorizontalAlignment(JTextField.CENTER);
											txtProject.setEditable(false);
										}
									}
								}
								{
									JXPanel panUnit = new JXPanel(new BorderLayout(3, 3)); 
									panName.add(panUnit); 
									{
										{
											JXPanel panIDLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
											panUnit.add(panIDLabel, BorderLayout.LINE_START); 
											panIDLabel.setPreferredSize(new Dimension(150, 0));
											{
												{
													lblGen = new JLabel("Unit/Seq"); 
													panIDLabel.add(lblGen); 
													lblGen.setHorizontalAlignment(JTextField.LEADING);
													lblGen.setFont(font11);
												}
												{
													txtUnitID = new JTextField(""); 
													panIDLabel.add(txtUnitID); 
													txtUnitID.setHorizontalAlignment(JTextField.CENTER);
													txtUnitID.setEditable(false);
												}
											}
											
										}
										{
											JXPanel panUnitSeq = new JXPanel(new BorderLayout(3, 3)); 
											panUnit.add(panUnitSeq, BorderLayout.CENTER); 
											{
												{
													txtUnit = new JTextField(""); 
													panUnit.add(txtUnit, BorderLayout.CENTER); 
													txtUnit.setHorizontalAlignment(JTextField.CENTER);
													txtUnit.setEditable(false);
												}
												{
													txtSeqNo = new JTextField(""); 
													panUnit.add(txtSeqNo, BorderLayout.LINE_END); 
													txtSeqNo.setHorizontalAlignment(JTextField.CENTER);
													txtSeqNo.setPreferredSize(new Dimension(50, 0));
													txtSeqNo.setEditable(false);
												}
											}
										}
									}
								}
							}
						}
						{
							panOther = new JXPanel(new GridLayout(3, 1, 3, 3)); 
							panPage1.add(panOther, BorderLayout.LINE_END); 
							panOther.setPreferredSize(new Dimension(250, 0));
							panOther.setBorder(JTBorderFactory.createTitleBorder("Other Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									panGen = new JXPanel(new BorderLayout(3, 3)); 
									panOther.add(panGen, BorderLayout.CENTER); 
									{
										{
											lblGen = new JLabel("Actual Date"); 
											panGen.add(lblGen, BorderLayout.LINE_START); 
											lblGen.setHorizontalAlignment(JTextField.LEADING);
											lblGen.setPreferredSize(new Dimension(75, 0));
											lblGen.setFont(font11);
										}
										{
											dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
											panGen.add(dteDate);
											dteDate.getCalendarButton().setVisible(true);
											dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										}
									}
								}
								{
									panGen = new JXPanel(new BorderLayout(3, 3)); 
									panOther.add(panGen, BorderLayout.CENTER); 
									{
										{
											lblGen = new JLabel("Loan Amount"); 
											panGen.add(lblGen, BorderLayout.LINE_START); 
											lblGen.setHorizontalAlignment(JTextField.LEADING);
											lblGen.setPreferredSize(new Dimension(75, 0));
											lblGen.setFont(font11);
										}
										{
											txtLoanableAmount = new _JXFormattedTextField("0.00");
											panGen.add(txtLoanableAmount, BorderLayout.CENTER);
											txtLoanableAmount.setHorizontalAlignment(JTextField.TRAILING);
											txtLoanableAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtLoanableAmount.setEditable(false);
										}
									}
								}
								{
									panGen = new JXPanel(new BorderLayout(3, 3)); 
									panOther.add(panGen, BorderLayout.CENTER); 
									{
										{
											lblGen = new JLabel("Sequence"); 
											panGen.add(lblGen, BorderLayout.LINE_START); 
											lblGen.setHorizontalAlignment(JTextField.LEADING);
											lblGen.setPreferredSize(new Dimension(75, 0));
											lblGen.setFont(font11);
										}
										{
											txtPaySeq = new JTextField(""); 
											panGen.add(txtPaySeq, BorderLayout.CENTER); 
											txtPaySeq.setHorizontalAlignment(JTextField.CENTER);
											txtPaySeq.setEditable(false);
										}
									}
								}
							}
						}
					}
					{
						JXPanel panPage2 = new JXPanel(new GridLayout(1, 2, 3, 3)); 
						panPage.add(panPage2, BorderLayout.CENTER); 
						{
							{
								panCheck = new JXPanel(new GridLayout(5, 1, 3, 3));
								panPage2.add(panCheck, BorderLayout.CENTER); 
								panCheck.setBorder(JTBorderFactory.createTitleBorder("Check Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										panGen = new JXPanel(new BorderLayout(3, 3)); 
										panCheck.add(panGen, BorderLayout.CENTER); 
										{
											{
												lblGen = new JLabel("Account No."); 
												panGen.add(lblGen, BorderLayout.LINE_START); 
												lblGen.setHorizontalAlignment(JTextField.LEADING);
												lblGen.setPreferredSize(new Dimension(100, 0));
												lblGen.setFont(font11);
											}
											{
												txtAcctNo = new JTextField(""); 
												panGen.add(txtAcctNo, BorderLayout.CENTER); 
												txtAcctNo.setHorizontalAlignment(JTextField.CENTER);
											}
										}
									}
									{
										panGen = new JXPanel(new BorderLayout(3, 3)); 
										panCheck.add(panGen, BorderLayout.CENTER); 
										{
											{
												lblGen = new JLabel("Check No."); 
												panGen.add(lblGen, BorderLayout.LINE_START); 
												lblGen.setHorizontalAlignment(JTextField.LEADING);
												lblGen.setPreferredSize(new Dimension(100, 0));
												lblGen.setFont(font11);
											}
											{
												txtCheckNo = new JTextField(""); 
												panGen.add(txtCheckNo, BorderLayout.CENTER); 
												txtCheckNo.setHorizontalAlignment(JTextField.CENTER);
											}
										}
									}
									{
										panGen = new JXPanel(new BorderLayout(3, 3)); 
										panCheck.add(panGen, BorderLayout.CENTER); 
										{
											{
												lblGen = new JLabel("Check Date"); 
												panGen.add(lblGen, BorderLayout.LINE_START); 
												lblGen.setHorizontalAlignment(JTextField.LEADING);
												lblGen.setPreferredSize(new Dimension(100, 0));
												lblGen.setFont(font11);
											}
											{
												dteCheckDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
												panGen.add(dteCheckDate);
												dteCheckDate.getCalendarButton().setVisible(true);
												dteCheckDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
											}
										}
									}
									{
										panGen = new JXPanel(new BorderLayout(3, 3)); 
										panCheck.add(panGen, BorderLayout.CENTER); 
										{
											{
												lblGen = new JLabel("Bank"); 
												panGen.add(lblGen, BorderLayout.LINE_START); 
												lblGen.setHorizontalAlignment(JTextField.LEADING);
												lblGen.setPreferredSize(new Dimension(100, 0));
												lblGen.setFont(font11);
											}
											{
												txtBankID = new _JLookup(); 
												panGen.add(txtBankID, BorderLayout.CENTER); 
												txtBankID.setLookupSQL("SELECT trim(bank_id) as ID, trim(bank_name) as Name, trim(bank_alias) as Alias \n" +
														"FROM mf_bank \n" +
														"WHERE status_id = 'A' ORDER BY bank_name");
												txtBankID.setReturnColumn(0);
												txtBankID.setFilterName(true);
												txtBankID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															txtBankBranchID.setLookupSQL("SELECT trim(bank_branch_id) as ID, trim(bank_branch_location) as Name \n" + 
																	"FROM mf_bank_branch \n" + 
																	"WHERE bank_id = '"+txtBankID.getValue().toString()+"' or '"+txtBankID.getValue().toString()+"' = '' AND status_id = 'A' \n" + 
																	"ORDER BY bank_branch_location");
															
															txtBank.setText((String) data[1].toString());
														}
														
														//ADDED by MONIQUE 2023-09-18; AUTOMATE ACCNT# FOR DBP BANK; REFER TO DCRF#2758
														if(txtBankID.getValue().equals("22")) {
															txtAcctNo.setText("405-018296-030");
														}
													}
												});
											}
											{
												txtBank = new JTextField(""); 
												panGen.add(txtBank, BorderLayout.LINE_END); 
												txtBank.setHorizontalAlignment(JTextField.LEFT);
												txtBank.setPreferredSize(new Dimension(150, 0));
												txtBank.setEditable(false);
											}
										}
									}
									{
										panGen = new JXPanel(new BorderLayout(3, 3)); 
										panCheck.add(panGen, BorderLayout.CENTER); 
										{
											{
												lblGen = new JLabel("Branch"); 
												panGen.add(lblGen, BorderLayout.LINE_START); 
												lblGen.setHorizontalAlignment(JTextField.LEADING);
												lblGen.setPreferredSize(new Dimension(100, 0));
												lblGen.setFont(font11);
											}
											{
												txtBankBranchID = new _JLookup(); 
												panGen.add(txtBankBranchID, BorderLayout.CENTER); 
												txtBankBranchID.setLookupSQL("SELECT trim(bank_branch_id) as ID, trim(bank_branch_location) as Name \n" + 
														"FROM mf_bank_branch \n" + 
														"WHERE status_id = 'A' \n" + 
														"ORDER BY bank_branch_location");
												txtBankBranchID.setReturnColumn(0);
												txtBankBranchID.setFilterName(true);
												txtBankBranchID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															txtBranch.setText((String) data[1].toString());
														}
													}
												});
											}
											{
												txtBranch = new JTextField(""); 
												panGen.add(txtBranch, BorderLayout.LINE_END); 
												txtBranch.setHorizontalAlignment(JTextField.LEFT);
												txtBranch.setPreferredSize(new Dimension(150, 0));
												txtBranch.setEditable(false);
											}
										}
									}
								}
							}
							{
								JXPanel panMisc = new JXPanel(new BorderLayout(3, 3));
								panPage2.add(panMisc, BorderLayout.CENTER); 
								{
									{
										panRetentionType = new JXPanel(new GridLayout(2, 1, 3, 3)); 
										panMisc.add(panRetentionType, BorderLayout.PAGE_START);
										panRetentionType.setPreferredSize(new Dimension(0, 83));
										panRetentionType.setBorder(JTBorderFactory.createTitleBorder("Retention Type", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												JXPanel panRet = new JXPanel(new BorderLayout(3, 3));
												panRetentionType.add(panRet);
												{
													{
														lblGen = new JLabel("Retention");
														panRet.add(lblGen, BorderLayout.LINE_START); 
														lblGen.setPreferredSize(new Dimension(75, 0));
														lblGen.setFont(font11);
													}
													{
														cboRet = new JComboBox(); 
														panRet.add(cboRet); 
														cboRet.addItemListener(new ItemListener() {
															public void itemStateChanged(ItemEvent arg0) {
																ComboboxAction(); 
															}
														});
														
														cboRet.removeAll();
														pgSelect db = new pgSelect();
														db.select("select (pay_part_id || ' - (' || particulars || ') ' || partdesc)::varchar as rettype \n" + 
																"from mf_pay_particular \n" + 
																"where particulars ~* 'LNREL' or pay_part_id in ('218', '246', '247') \n" + 
																"order by particulars");
														if (db.isNotNull()) {
															for (int x = 0; x < db.getRowCount(); x++) {
																cboRet.addItem((String) db.getResult()[x][0].toString());
															}
														}
													}
												}
											}
											{
												JXPanel panRecepit = new JXPanel(new GridLayout(1, 2, 3, 3));
												panRetentionType.add(panRecepit);
												{
													{
														JXPanel panReceiptLabel = new JXPanel(new BorderLayout(3, 3)); 
														panRecepit.add(panReceiptLabel);
														{
															{
																lblGen = new JLabel("Receipt");
																panReceiptLabel.add(lblGen, BorderLayout.LINE_START); 
																lblGen.setPreferredSize(new Dimension(75, 0));
															}
															{
																JXPanel panReceiptTypeIDandLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
																panReceiptLabel.add(panReceiptTypeIDandLabel);
																{
																	{
																		//txtReceiptTypeID = new _JLookup("01"); COMMENTED BY MONIQUE TO CHANGE RECEIPT TYPE INTO SALES INVOICE (VAT) DTD 2022-07-07
																		//txtReceiptTypeID = new _JLookup("307"); 
																		//txtReceiptTypeID = new _JLookup("03"); //DCRF 2193 //MODIFIED BY MONIQUE; SO THAT USER CAN SELECT RECEIPT TYPE; 07-11-24
																		txtReceiptTypeID = new _JLookup("", "Receipt Type", 0);
																		panReceiptTypeIDandLabel.add(txtReceiptTypeID, BorderLayout.CENTER);
																		txtReceiptTypeID.setLookupSQL("Select doc_id, doc_alias, doc_desc as description \n"
																				+ "FROM mf_documents \n"
																				+ "WHERE doc_id IN ('01', '03')\n"
																				+ "AND status_id = 'A'; ");
																		txtReceiptTypeID.setHorizontalAlignment(JTextField.CENTER);
																		txtReceiptTypeID.addLookupListener(new LookupListener() {
																			public void lookupPerformed(LookupEvent event) {
																				Object data [] = ((_JLookup)event.getSource()).getDataSet();
																				if (data != null) {
																					txtReceiptType.setText(data[1].toString());
																				} else {
																					txtReceiptType.setText("");
																				}

																			}
																		});
																		txtReceiptTypeID.setEditable(false);
																	}
																	{
																		//txtReceiptType = new JTextField("ORV"); COMMENTED BY MONIQUE TO CHANGE RECEIPT TYPE INTO SALES INVOICE (VAT) DTD 2022-07-07
																		//txtReceiptType = new JTextField("SIV");
																		//txtReceiptType = new JTextField("AR"); //DCRF 2193 //COMMENTED BY MONIQUE; SO THAT USER CAN SELECT RECEIPT TYPE; 07-11-24
																		txtReceiptType = new JTextField("");
																		panReceiptTypeIDandLabel.add(txtReceiptType); 
																		txtReceiptType.setHorizontalAlignment(JTextField.CENTER);
																		txtReceiptType.setEditable(false);
																	}
																}
															}
															
														}
													}
													{
														txtReceiptTypeNo = new JTextField("");
														panRecepit.add(txtReceiptTypeNo); 
														txtReceiptTypeNo.setHorizontalAlignment(JTextField.CENTER);
														txtReceiptTypeNo.addKeyListener(new KeyListener() {
															public void keyTyped(KeyEvent e) {
																
															}
															
															public void keyReleased(KeyEvent e) {
																/*
																try {
																	int intReceipt = Integer.parseInt(txtReceiptTypeNo.getText().toString());  
																	
																	if (txtReceiptTypeNo.getText().toString().length()>6) {
																		txtReceiptTypeNo.setText(txtReceiptTypeNo.getText().substring(0, 6));
																	}
																	
																	txtReceiptTypeNo.setText(String.format("%06d", intReceipt));
																} catch (NumberFormatException ex) {

																} finally {
																	txtReceiptTypeNo.setText(txtReceiptTypeNo.getText().substring(0, 6));
																}
																*/
															}
															
															public void keyPressed(KeyEvent e) {
																
															}
														});
													}
												}
											}
										}
									}
									{
										panAmount = new JXPanel(new BorderLayout(3, 3)); 
										panMisc.add(panAmount, BorderLayout.CENTER);
										panAmount.setBorder(JTBorderFactory.createTitleBorder("Amount", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												txtPctg = new _JXFormattedTextField("0.00");
												panAmount.add(txtPctg, BorderLayout.LINE_START);
												txtPctg.setHorizontalAlignment(JTextField.TRAILING);
												txtPctg.setFormatterFactory(_JXFormattedTextField.DECIMAL);
												txtPctg.setPreferredSize(new Dimension(75, 0));
												txtPctg.setFont(font14);
												txtPctg.getDocument().addDocumentListener(new DocumentListener() {
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
														BigDecimal dbPercentage = new BigDecimal("0.00");

														try {
															dbPercentage = new BigDecimal(txtPctg.getText()); 
														} catch (Exception ex) {
															dbPercentage = new BigDecimal("0.00");
														}
														
														BigDecimal dbLoanAmt;
														try {
															dbPercentage = dbPercentage.divide(new BigDecimal("100"));
															dbLoanAmt = new BigDecimal(txtLoanableAmount.getValue().toString());															
														} catch (NullPointerException ex) {
															dbLoanAmt = new BigDecimal("0.00");
														}
														
														try {
															//txtAmount.setValue(dbLoanAmt.multiply(dbPercentage)); --MODIFIED BY MONIQUE DTD 12-12-2023; ROUNDING OFF TO 2 DECIMAL PLACES 
															txtAmount.setValue((dbLoanAmt.multiply(dbPercentage)).setScale(2, RoundingMode.HALF_UP));  
														} catch (IllegalStateException ex) {
															
														}
													}
												});
												txtPctg.addFocusListener(new FocusListener() {
													public void focusLost(FocusEvent e) {
														
													}
													
													public void focusGained(FocusEvent e) {
														Runnable doAssist = new Runnable() {
															public void run() {
																try {
																	if (txtPctg.getValue().toString().equals("") || txtPctg.getValue().toString().equals("0.00") || txtPctg.getValue().toString().equals(null) || txtPctg.getText().equals("0.00")) {
																		txtPctg.setValue(null);
																	} else {
																		
																	}
																} catch (NullPointerException ex) {
																	
																}
															}
														};
														SwingUtilities.invokeLater(doAssist);
													}
												});
											}
											{
												txtAmount = new _JXFormattedTextField("0.00");
												panAmount.add(txtAmount, BorderLayout.CENTER);
												txtAmount.setHorizontalAlignment(JTextField.TRAILING);
												txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
												txtAmount.setFont(font14);
												txtAmount.getDocument().addDocumentListener(new DocumentListener() {
													public void removeUpdate(DocumentEvent e) {
														ComputeAmount(); 
													}
													
													public void insertUpdate(DocumentEvent e) {
														ComputeAmount(); 
													}

													public void changedUpdate(DocumentEvent e) {
														ComputeAmount(); 
													}
													
													private void ComputeAmount() {
														/*
														try {
															float dbPercentage = new Float("0.00");
															float dbLoanAmt = new Float(txtLoanableAmount.getValue().toString());
															
															dbPercentage = ((dbLoanAmt-(float) txtAmount.getValue()) / dbLoanAmt); 

															System.out.println("dbPercentage: "+dbPercentage);
															
															txtPctg.setValue(dbPercentage);								
														} catch (NullPointerException ex) {
															ex.printStackTrace();
															txtPctg.setValue(new BigDecimal("0.00"));
														}
  														*/
													}
												});
												txtAmount.addFocusListener(new FocusListener() {
													public void focusLost(FocusEvent e) {
														
													}
													
													public void focusGained(FocusEvent e) {
														Runnable doAssist = new Runnable() {
															public void run() {

															}
														};
														SwingUtilities.invokeLater(doAssist);
													}
												});
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
				JXPanel panCenter = new JXPanel(new BorderLayout(3, 3)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					{
						JXPanel panCenterPage = new JXPanel(new BorderLayout(3, 3)); 
						panCenter.add(panCenterPage, BorderLayout.PAGE_START); 
						panCenterPage.setPreferredSize(new Dimension(0, 150));
						{
							JXPanel panDiv = new JXPanel(new GridLayout(1, 3, 10, 3)); 
							panCenterPage.add(panDiv, BorderLayout.CENTER);
							panDiv.setBorder(JTBorderFactory.createTitleBorder("Undertaking", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									JXPanel panDiv0 = new JXPanel(new GridLayout(5, 1, 3, 3)); 
									panDiv.add(panDiv0);
									{
										{
											panDiv0.add(new JLabel(""));
										}
										{
											lblVarRet = new JLabel("--"); 
											panDiv0.add(lblVarRet);
											lblVarRet.setHorizontalAlignment(JTextField.LEFT);
											lblVarRet.setFont(font10b);
										}
										{
											panDiv0.add(new JLabel(""));
										}
										{
											JLabel lblRet21 = new JLabel("RETFEE2-1"); 
											panDiv0.add(lblRet21);
											lblRet21.setHorizontalAlignment(JTextField.LEFT);
											lblRet21.setFont(font10b);
										}
										{
											JLabel lblRet22 = new JLabel("RETFEE2-2"); 
											panDiv0.add(lblRet22);
											lblRet22.setHorizontalAlignment(JTextField.LEFT);
											lblRet22.setFont(font10b);
										}
									}
								}
								{
									JXPanel panDiv1 = new JXPanel(new GridLayout(5, 1, 3, 3)); 
									panDiv.add(panDiv1);
									{
										{
											JLabel lblPayable = new JXLabel("Payable");
											panDiv1.add(lblPayable);
											lblPayable.setHorizontalAlignment(JLabel.CENTER);
											lblPayable.setFont(font10b);
										}
										{
											txtVarPayRet = new _JXFormattedTextField("0.00");
											panDiv1.add(txtVarPayRet, BorderLayout.CENTER);
											txtVarPayRet.setHorizontalAlignment(JTextField.TRAILING);
											txtVarPayRet.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtVarPayRet.setEditable(false);
										}
										{
											panDiv1.add(new JLabel("")); 
										}
										{
											txtPayRet21 = new _JXFormattedTextField("0.00");
											panDiv1.add(txtPayRet21, BorderLayout.CENTER);
											txtPayRet21.setHorizontalAlignment(JTextField.TRAILING);
											txtPayRet21.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPayRet21.setEditable(false);
										}
										{
											txtPayRet22 = new _JXFormattedTextField("0.00");
											panDiv1.add(txtPayRet22, BorderLayout.CENTER);
											txtPayRet22.setHorizontalAlignment(JTextField.TRAILING);
											txtPayRet22.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPayRet22.setEditable(false);
										}
									}
								}
								{
									JXPanel panDiv2 = new JXPanel(new GridLayout(5, 1, 3, 3)); 
									panDiv.add(panDiv2);
									{
										{
											JLabel lblBal = new JXLabel("Balance");
											panDiv2.add(lblBal); 
											lblBal.setHorizontalAlignment(JLabel.CENTER);
											lblBal.setFont(font10b);
										}
										{
											txtVarBalRet = new _JXFormattedTextField("0.00");
											panDiv2.add(txtVarBalRet, BorderLayout.CENTER);
											txtVarBalRet.setHorizontalAlignment(JTextField.TRAILING);
											txtVarBalRet.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtVarBalRet.setEditable(false);
										}
										{
											panDiv2.add(new JLabel("")); 
										}
										{
											txtBalRet21 = new _JXFormattedTextField("0.00");
											panDiv2.add(txtBalRet21, BorderLayout.CENTER);
											txtBalRet21.setHorizontalAlignment(JTextField.TRAILING);
											txtBalRet21.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtBalRet21.setEditable(false);
										}
										{
											txtBalRet22 = new _JXFormattedTextField("0.00");
											panDiv2.add(txtBalRet22, BorderLayout.CENTER);
											txtBalRet22.setHorizontalAlignment(JTextField.TRAILING);
											txtBalRet22.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtBalRet22.setEditable(false);
										}
									}
								}
								{
									JXPanel panDiv3 = new JXPanel(new GridLayout(5, 1, 3, 3)); 
									panDiv.add(panDiv3);
									{
										{
											panDiv3.add(new JLabel("")); 
										}
										{
											panDiv3.add(new JLabel("")); 
										}
										{
											panDiv3.add(new JLabel("")); 
										}
										{
											JLabel lblPayable = new JXLabel("Total Retention");
											panDiv3.add(lblPayable);
											lblPayable.setHorizontalAlignment(JLabel.CENTER);
											lblPayable.setFont(font10b);
										}
										{
											txtTotalRetention = new _JXFormattedTextField("0.00");
											panDiv3.add(txtTotalRetention, BorderLayout.CENTER);
											txtTotalRetention.setHorizontalAlignment(JTextField.TRAILING);
											txtTotalRetention.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtTotalRetention.setEditable(false);
										}
									}
								}
							}
						}	
					}
					{
						JXPanel panCenterCenter = new JXPanel(new BorderLayout(3, 3)); 
						panCenter.add(panCenterCenter, BorderLayout.CENTER); 
						{
							tabRet = new JTabbedPane(); 
							panCenterCenter.add(tabRet, BorderLayout.CENTER); 
							tabRet.setBorder(new EmptyBorder(3, 3, 3, 3));
							{
								panDetails = new JXPanel(new BorderLayout(3, 3));
								panJV = new JXPanel(new BorderLayout(3, 3)); 
								
								tabRet.addTab("                    Details                    ", panDetails);
								tabRet.addTab("                    Entries                    ", panJV);
							}
						}	
					}
					{
						JXPanel panCenterEnd = new JXPanel(new GridLayout(1, 5, 3, 3)); 
						panCenter.add(panCenterEnd, BorderLayout.PAGE_END); 
						panCenterEnd.setPreferredSize(new Dimension(0, 30));
						{
							{
								btnAdd = new JButton("Add"); 
								panCenterEnd.add(btnAdd);
								btnAdd.setEnabled(false);
								btnAdd.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										LockPanel(panCheck, true);
										LockPanel(panOther, true);
										LockPanel(panRetentionType, true);
										LockPanel(panAmount, true);
										ctrlState(false, false, true, true, true); 
									}
								});
							}
							{
								btnEdit = new JButton("Edit"); 
								panCenterEnd.add(btnEdit);
								btnEdit.setEnabled(false);
								btnEdit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										JOptionPane.showMessageDialog(getContentPane(), "The `EDIT` feature is still in development.");
									}
								});
							}
							{
								btnSave = new JButton("Save"); 
								panCenterEnd.add(btnSave);
								btnSave.setEnabled(false);
								btnSave.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if (JOptionPane.showConfirmDialog(getContentPane(), "Are the entries correct?", "Save", JOptionPane.YES_NO_OPTION, 
												JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
											String strPart = cboRet.getSelectedItem().toString().substring(0, 3); 
											
											saveRetention();
											DisplayRetDetails(); 
											DisplayRetEntries(); 
											LockPanel(panCheck, false);
											LockPanel(panOther, false);
											LockPanel(panRetentionType, false);
											LockPanel(panAmount, false);
											ctrlState(false, true, false, false, true);	
										}
									}
								});
							}
							{
								btnCancel = new JButton("Cancel"); 
								panCenterEnd.add(btnCancel);
								btnCancel.setEnabled(false);
								btnCancel.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										LockPanel(panCheck, false);
										LockPanel(panOther, false);
										LockPanel(panRetentionType, false);
										LockPanel(panAmount, false);
										ctrlState(true, false, false, false, true); 
									}
								});
							}
							{
								btnClear = new JButton("Clear"); 
								panCenterEnd.add(btnClear);
								btnClear.setEnabled(false);
								btnClear.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										ClearComp(panCheck);
										ClearComp(panOther);
										ClearComp(panRetentionType);
										ClearComp(panAmount);
										
										GenFees(); 
									}
								});
							}
						}	
					}
				}
			}
		}
		{
			lblGen.setFont(font11);
			EntryMode(false);
			AdjustFont(panMain); 
			LockPanel(panCheck, false);
			LockPanel(panOther, false);
			LockPanel(panRetentionType, false);
			LockPanel(panAmount, false);
			CreateDetailsTab(); 
			CreateEntriesTab(); 
			ctrlState(false, false, false, false, false); 
		}
		
		initializeUndertaking(); 
	}

	private void EntryMode(Boolean blnDo) {
		panOther.setEnabled(blnDo);
		panCheck.setEnabled(blnDo);
	}
	
	private void AdjustFont(JXPanel pan) {
		for (Component comp : pan.getComponents()) {
			if (comp instanceof JTextField) {
				((JTextField) comp).setFont(font11);
			} else if (comp instanceof JComboBox) {
				((JComboBox) comp).setFont(font11);
			} else if (comp instanceof _JDateChooser) {
				((_JDateChooser) comp).setFont(font11);
			} else if (comp instanceof JXPanel) {
				AdjustFont((JXPanel) comp); 
			}
		}
		
		txtPctg.setFont(font14b);
		txtAmount.setFont(font14b);
	}
	
	private void LockPanel(JXPanel pan, Boolean blnDo) {
		for (Component comp : pan.getComponents()) {
			if (comp instanceof JXPanel) {
				LockPanel((JXPanel) comp, blnDo); 
			} else if (comp instanceof JLabel) {

			} else if (comp instanceof JTextField) {
				((JTextField) comp).setEnabled(blnDo);
			} else if (comp instanceof _JLookup) {
				((_JLookup) comp).setEnabled(blnDo); 
			} else if (comp instanceof JComboBox) {
				((JComboBox<?>) comp).setEnabled(blnDo);
			} else if (comp instanceof _JDateChooser) {
				((_JDateChooser) comp).setEnabled(blnDo);
			} else {
				
			}
		}		
	}
	
	private void ctrlState(Boolean blnAdd, Boolean blnEdit, Boolean blnSave, Boolean blnCancel, Boolean blnClear) {
		btnAdd.setEnabled(blnAdd);
		btnEdit.setEnabled(blnEdit);
		btnSave.setEnabled(blnSave);
		btnCancel.setEnabled(blnCancel);
		btnClear.setEnabled(blnClear);
	}
	
	private String getNewClientSeqNo() {
		pgSelect db = new pgSelect();
		db.select("SELECT get_new_client_seqno('"+ UserInfo.Branch +"', TRUE);");
		return (String) db.getResult()[0][0];
	}
	
	private void saveRetention() {
		String strPart = cboRet.getSelectedItem().toString().substring(0, 3); 
		String strClientNo = getNewClientSeqNo(); 
		txtPaySeq.setText(strClientNo);
		
		String strExec = "select sp_tag_retfee_v2( \n" + 
				"'"+txtClientID.getValue().toString()+"', \n" + 
				"'"+txtProjectID.getText()+"', \n" + 
				"'"+txtUnitID.getText()+"', \n" + 
				"'"+txtSeqNo.getText()+"', \n" + 
				"'"+strPart+"', \n" + 
				"'"+txtReceiptTypeID.getText()+"', \n" + 
				"'"+txtReceiptTypeNo.getText()+"', \n" + 
				"'"+txtCheckNo.getText()+"', \n" + 
				"'"+dteCheckDate.getDate().toString()+"', \n" + 
				"'"+txtAcctNo.getText()+"', \n" + 
				"'"+txtBankID.getValue().toString()+"', \n" + 
				"'"+txtBankBranchID.getValue().toString()+"', \n" + 
				"'"+txtAmount.getValue().toString()+"', \n" + 
				"'"+txtPaySeq.getText()+"', \n" + 
				"'"+dteDate.getDate().toString()+"', \n" + 
				"'"+UserInfo.EmployeeCode+"'); "; 
		
		pgSelect db = new pgSelect(); 
		db.select(strExec);
		
		JOptionPane.showMessageDialog(null, "Saved!");
	}
	
	private void ClearComp(JXPanel pan) {
		for (Component comp : pan.getComponents()) {
			if (comp instanceof JXPanel) {
				ClearComp((JXPanel) comp); 
			} else if (comp instanceof JLabel) {

			} else if (comp instanceof JTextField) {
				((JTextField) comp).setText("");
			} else if (comp instanceof _JLookup) {
				((_JLookup) comp).setValue(""); 
			} else if (comp instanceof JComboBox) {
				((JComboBox) comp).setSelectedIndex(0);
			} else if (comp instanceof _JDateChooser) {
				((_JDateChooser) comp).setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			} else if (comp instanceof _JFormattedTextField) {
				((_JFormattedTextField) comp).setValue(new BigDecimal("0.00")); 
			}
		}
		
		//txtReceiptType.setText("ORV"); COMMENTED BY MONIQUE TO CHANGE RECEIPT TYPE INTO SALES INVOICE (VAT) DTD 2022-07-07
		//txtReceiptType.setText("SIV");
//		txtReceiptType.setText("AR"); // REFER TO DCRF #2193
	}
	
	private void CreateDetailsTab() {
		scrRetention = new JScrollPane();
		panDetails.add(scrRetention, BorderLayout.CENTER);
		scrRetention.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrRetention.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrRetention.setBorder(line);
		{
			{
				modelRetention = new model_retention_details(); 
				tblRetention = new _JTableMain(modelRetention);
				
				rowRetention = tblRetention.getRowHeader();
				scrRetention.setViewportView(tblRetention);
				
				tblRetention.getColumnModel().getColumn(0).setPreferredWidth(50);
				tblRetention.getColumnModel().getColumn(1).setPreferredWidth(100);
				tblRetention.getColumnModel().getColumn(2).setPreferredWidth(150);
				tblRetention.getColumnModel().getColumn(3).setPreferredWidth(100);
				tblRetention.getColumnModel().getColumn(4).setPreferredWidth(125);
				tblRetention.getColumnModel().getColumn(5).setPreferredWidth(125);
				tblRetention.getColumnModel().getColumn(6).setPreferredWidth(125);
				tblRetention.getColumnModel().getColumn(7).setPreferredWidth(125);
				tblRetention.getColumnModel().getColumn(8).setPreferredWidth(125);
				tblRetention.getColumnModel().getColumn(9).setPreferredWidth(125);
				
				tblRetention.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
			}
			{
				rowRetention = tblRetention.getRowHeader();
				rowRetention.setModel(new DefaultListModel());
				scrRetention.setRowHeaderView(rowRetention);
				scrRetention.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
			}
		}
	}
	
	private void CreateEntriesTab() {
		scrEntries = new JScrollPane();
		panJV.add(scrEntries, BorderLayout.CENTER);
		scrEntries.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrEntries.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrEntries.setBorder(line);
		{
			{
				modelEntries = new modelByrPmtPosting_CRB(); 
				tblEntries = new _JTableMain(modelEntries);
				
				rowEntries = tblEntries.getRowHeader();
				scrEntries.setViewportView(tblEntries);
				
				tblEntries.getColumnModel().getColumn(0).setPreferredWidth(121);
				tblEntries.getColumnModel().getColumn(1).setPreferredWidth(200);
				tblEntries.getColumnModel().getColumn(2).setPreferredWidth(126);
				tblEntries.getColumnModel().getColumn(3).setPreferredWidth(126);
				tblEntries.getColumnModel().getColumn(4).setPreferredWidth(200);
				
				tblEntries.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
			}
			{
				rowEntries = tblEntries.getRowHeader();
				rowEntries.setModel(new DefaultListModel());
				scrEntries.setRowHeaderView(rowEntries);
				scrEntries.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
			}
		}
	}
	
	private void DisplayRetDetails() {
		FncGlobal.startProgress("Generating preview");
		
		FncTables.clearTable(modelRetention);		
		DefaultListModel listModel = new DefaultListModel();
		rowRetention.setModel(listModel);
		
		String strSQL = "select * from view_hdmf_retention_details_v2(\n" +
			    "'"+txtClientID.getValue().toString()+"', \n" +
			    "'"+txtProjectID.getText()+"', \n" +
			    "'"+txtUnitID.getText()+"', \n" +
			    "'"+txtSeqNo.getText()+"')"; 

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for (int x=0; x < db.getRowCount(); x++) {
				modelRetention.addRow(db.getResult()[x]);
				listModel.addElement(modelRetention.getRowCount());		
			}
		}

		FncGlobal.stopProgress();
	}
	
	private void DisplayRetEntries() {
		FncGlobal.startProgress("Generating preview");
		
		FncTables.clearTable(modelEntries);		
		DefaultListModel listModel = new DefaultListModel();
		rowEntries.setModel(listModel);
		
		String strSQL = "select c_acct_id, c_acct_name, c_debit, c_credit, c_remarks \n" + 
				"from view_hdmf_retention_entries(\n" +
			    "'"+txtClientID.getValue().toString()+"', \n" +
			    "'"+txtProjectID.getText()+"', \n" +
			    "'"+txtUnitID.getText()+"', \n" +
			    "'"+txtSeqNo.getText()+"')"; 

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for (int x=0; x < db.getRowCount(); x++) {
				modelEntries.addRow(db.getResult()[x]);
				listModel.addElement(modelEntries.getRowCount());		
			}
		}

		FncGlobal.stopProgress();
	}
	
	private void ComboboxAction() {
		try {
			BigDecimal dbPercentage = new BigDecimal("0.00");
			BigDecimal dbRetAmt = new BigDecimal("0.00");
			
			pgSelect db = new pgSelect(); 
			db.select("select * from view_hdmf_retfees_dues_boi('"+txtClientID.getValue().toString()+"', '"+txtProjectID.getText()+"', '"+txtUnitID.getText()+"', '"+txtSeqNo.getText()+"')"); 
			
			System.out.println("");
			System.out.println("select * from view_hdmf_retfees_dues_boi('"+txtClientID.getValue().toString()+"', '"+txtProjectID.getText()+"', '"+txtUnitID.getText()+"', '"+txtSeqNo.getText()+"')");
			
			if (cboRet.getSelectedIndex()==9) {
				dbRetAmt = new BigDecimal(db.getResult()[0][3].toString());
				dbPercentage = FncGlobal.GetDecimal("select ((1 - ('"+txtLoanableAmount.getValue().toString()+"'::numeric(19, 2) - '"+dbRetAmt+"'::numeric(19, 2)) / '"+txtLoanableAmount.getValue().toString()+"'::numeric(19, 2)) * 100)");
				txtPctg.setValue(dbPercentage);
			} else if (cboRet.getSelectedIndex()==10) {
				dbRetAmt = new BigDecimal(db.getResult()[0][4].toString());
				dbPercentage = FncGlobal.GetDecimal("select ((1 - ('"+txtLoanableAmount.getValue().toString()+"'::numeric(19, 2) - '"+dbRetAmt+"'::numeric(19, 2)) / '"+txtLoanableAmount.getValue().toString()+"'::numeric(19, 2)) * 100)");
				txtPctg.setValue(dbPercentage);
			} else if (cboRet.getSelectedIndex()==11) {
				dbRetAmt = new BigDecimal(db.getResult()[0][5].toString());
				dbPercentage = FncGlobal.GetDecimal("select ((1 - ('"+txtLoanableAmount.getValue().toString()+"'::numeric(19, 2) - '"+dbRetAmt+"'::numeric(19, 2)) / '"+txtLoanableAmount.getValue().toString()+"'::numeric(19, 2)) * 100)");
				txtPctg.setValue(dbPercentage);
			} else {
				txtPctg.setValue(new BigDecimal("0.00"));
			}
		} catch (NullPointerException ex) {
			
		}
	}
	
	private void initializeUndertaking() {
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("delete from tmp_lnrel; ", false);
		dbExec.executeUpdate("insert into tmp_lnrel\n" + 
				"select *\n" + 
				"from \n" + 
				"(\n" + 
				"	select entity_id, epeb as amount, 'epeb' as undertaking, 'LNREL3 - EPEB' as particulars  from rf_pagibig_lnrel union \n" + 
				"	select entity_id, etd as amount, 'etd' as undertaking, 'LNREL4 - ETD' as particulars  from rf_pagibig_lnrel union \n" + 
				"	select entity_id, coc as amount, 'coc' as undertaking, 'LNREL5 - COC' as particulars  from rf_pagibig_lnrel union \n" + 
				"	select entity_id, merl as amount, 'merl' as undertaking, 'LNREL6 - MERALCO' as particulars  from rf_pagibig_lnrel union \n" + 
				"	select entity_id, com5 as amount, 'com5' as undertaking, 'LNREL7 - COMFAC 5%' as particulars  from rf_pagibig_lnrel union \n" + 
				"	select entity_id, com3 as amount, 'com3' as undertaking, 'LNREL8 - COMFAC 3%' as particulars  from rf_pagibig_lnrel union \n" + 
				"	select entity_id, rightofway as amount, 'rightofway' as undertaking, 'LNREL9 - RIGHT OF WAY' as particulars  from rf_pagibig_lnrel union \n" + 
				"	select entity_id, water_supply as amount, 'water_supply' as undertaking, 'LNREL10 - WATER SUPPLY' as particulars  from rf_pagibig_lnrel union \n" + 
				"	select entity_id, tax_dec as amount, 'tax_dec' as undertaking, 'LNREL11 - TAX DECLARATION' as particulars  from rf_pagibig_lnrel\n" + 
				") a\n" + 
				"where coalesce(a.amount, 0) > 0; ", false);
		dbExec.commit();
	}
	
	private String getParticular() {
		System.out.println("");
		System.out.println("");
		System.out.println("Particular: "+FncGlobal.GetString("select particulars from tmp_lnrel where entity_id = '"+txtClientID.getValue()+"'"));
		
		return FncGlobal.GetString("select particulars from tmp_lnrel where entity_id = '"+txtClientID.getValue()+"'"); 
	}
	
	private void GenFees() {
		lblVarRet.setText(getParticular());
		
		pgSelect db = new pgSelect(); 
		db.select("select * from view_hdmf_retfees_dues_boi('"+txtClientID.getValue().toString()+"', '"+txtProjectID.getText()+"', '"+txtUnitID.getText()+"', '"+txtSeqNo.getText()+"')"); 
		
		if (db.isNotNull()) {
			txtVarPayRet.setValue(db.getResult()[0][0]);
			txtPayRet21.setValue(db.getResult()[0][1]);
			txtPayRet22.setValue(db.getResult()[0][2]);
			
			txtVarBalRet.setValue(db.getResult()[0][3]);
			txtBalRet21.setValue(db.getResult()[0][4]);
			txtBalRet22.setValue(db.getResult()[0][5]);
			
			txtTotalRetention.setValue(db.getResult()[0][6]);
			txtLoanableAmount.setValue(db.getResult()[0][7]);
		}
	}
}
