package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.Event;

import com.toedter.calendar.JDateChooser;

import Buyers.CreditandCollections.pnlTagAccountsStatus;
import Database.pgSelect;
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
import components._JInternalFrame;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelRetFeeMultiPayments;

public class RetFeeOnlineMultiplePayments extends _JInternalFrame implements _GUI, ActionListener {


	static String title = "Retention Fee Online (Multiple Payments)";
	Dimension frameSize = new Dimension(800, 610);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	private JPanel pnlMain;
	private JPanel pnlTransDetails;
	private JPanel pnlNorthWest;
	private JPanel pnlLabels;
	private JPanel pnlIds;
	private _JLookup lookupClient;
	private _JXTextField txtUnitId;
	private JDateChooser dteActualDate;
	private _JXTextField txtClient;
	private _JXTextField txtUnitDesc;
	private _JXTextField txtProjId;
	private _JXTextField txtProject;
	private JPanel pnlNorthCenter;
	private JPanel pnlPaymentList;
	private modelRetFeeMultiPayments modelRetFeeList;
	private _JTableMain tablePaymentList;
	private JScrollPane scrollPaymentList;
	private JButton btnNew;
	private JButton btnRemoveRow;
	private JButton btnCancel;
	private JButton btnSave;
	private JPanel pnlPaymentDetails;
	private _JLookup lookupPart;

	private _JXFormattedTextField txtAmt;
	private JPanel pnlPDEast;
	private JButton btnPost;
	private JButton btnEdit1;
	private JButton btnCancel1;
	private JPanel pnlPDCenter;
	private _JXFormattedTextField txtRetFee1;
	private _JXFormattedTextField txtRetFee21;
	private _JXFormattedTextField txtRetFee22;
	private _JXFormattedTextField txtRetFee1bal;
	private _JXFormattedTextField txtRetFee21Bal;
	private _JXFormattedTextField txtRetFee22Bal;
	private JTextField txtParticular;
	private _JXTextField txtSeqNo;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private String co_id;
	private _JXFormattedTextField txtTotRet;

	private String entity_id;
	private String proj_id;
	private String pbl_id;
	private String seq_no;
	private double total_amt;
	private JList rowReadHeader;

	public RetFeeOnlineMultiplePayments() {
		super(title, true, true, true, true);
		initGUI();
	}

	public RetFeeOnlineMultiplePayments(String title) {
		super(title);
		initGUI();
	}

	public RetFeeOnlineMultiplePayments(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}


	@Override
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
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,0,3));

			{
				pnlTransDetails = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlTransDetails, BorderLayout.NORTH); 
				pnlTransDetails.setBorder(components._JTBorderFactory.createTitleBorder("Transaction Details"));
				pnlTransDetails.setPreferredSize(new Dimension(0, 150));
				{
					pnlNorthWest = new JPanel(new BorderLayout(3, 3));
					pnlTransDetails.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new Dimension(450, 0));
					{
						pnlLabels = new JPanel(new GridLayout(3, 2, 3, 3)); 
						pnlNorthWest.add(pnlLabels, BorderLayout.WEST);
						pnlLabels.setPreferredSize(new Dimension(200, 0));
						{
							JLabel lblcompany = new JLabel("   Company:");
							pnlLabels.add(lblcompany);
						}
						{
							lookupCompany = new _JLookup(null, "Company");
							pnlLabels.add(lookupCompany); 
							lookupCompany.setReturnColumn(0);
							lookupCompany.setPrompt("Company");
							lookupCompany.setLookupSQL(sqlCompany());





							lookupCompany.addLookupListener(new LookupListener() {

								@Override
								public void lookupPerformed(LookupEvent event) {

									Object data [] = ((_JLookup)event.getSource()).getDataSet(); 

									if (data != null) {
										co_id = (String) data[0]; 
										FncSystem.out("co_id:", co_id);
										txtCompany.setText(data[1].toString()); 
										lookupClient.setEnabled(true);

										String getClient = 	"SELECT a.entity_id, get_client_name(a.entity_id) as name, a.projcode, get_project_name(a.projcode) as proj, get_unit_description(a.projcode, a.pbl_id) as unit, a.pbl_id, a.seq_no \n"
												+ "from rf_sold_unit a\n"
												+ "left join mf_project c on TRIM(a.projcode) = c.proj_id \n"
												+ "where exists( Select * from rf_buyer_status b where a.entity_id = b.entity_id and a.projcode = b.proj_id and a.pbl_id = b.pbl_id and a.seq_no = b.seq_no and trim(b.byrstatus_id) = '32' and trim(b.status_id) = 'A') \n"
												+ "and	a.currentstatus != '02'\n"
												+ "and TRIM(a.status_id) = 'A' and c.co_id = '"+co_id+"';"; 

										lookupClient.setLookupSQL(getClient);
										btnState(true, false, true, false, true, false, false);

									}

								}
							});
						}

						{
							JLabel lblClient = new JLabel("   Client:"); 
							pnlLabels.add(lblClient); 
						}
						{
							lookupClient = new _JLookup(null, "Client"); 
							pnlLabels.add(lookupClient);
							lookupClient.setReturnColumn(0);
							lookupClient.setPrompt("Client ID");
							lookupClient.setFilterName(true);
							lookupClient.addLookupListener(new LookupListener() {

								@Override
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet(); 

									if (data != null) {

										txtClient.setText(data[1].toString());
										txtUnitId.setText(data[5].toString());
										txtUnitDesc.setText(data[4].toString());
										txtProjId.setText(data[2].toString());
										txtSeqNo.setText(data[6].toString());
										txtTotRet.setEnabled(true);
										GenFees(); 
										dteActualDate.setEnabled(true);
										lookupPart.setEnabled(true);
										lookupPart.setLookupSQL(sqlParticular()); 
										DataState(true, true, true, true, true, true);

									} else {
										txtClient.setText("");
										txtProjId.setText("");
										txtProject.setText("");
										txtUnitId.setText("");
										txtUnitDesc.setText("");
									}


								}
							});

						}
						{
							JLabel lblUnit = new JLabel("   Unit:"); 
							pnlLabels.add(lblUnit);
							
						}
						{
							txtUnitId = new _JXTextField("Unit ID"); 
							pnlLabels.add(txtUnitId); 
							txtUnitId.setHorizontalAlignment(_JXTextField.CENTER);
						}
					}
					{
						pnlIds = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthWest.add(pnlIds, BorderLayout.CENTER); 

						{
							txtCompany = new _JXTextField("Company"); 
							pnlIds.add(txtCompany); 
							txtCompany.setHorizontalAlignment(_JXTextField.CENTER);
						}

						{
							txtClient = new _JXTextField("Client Name");
							pnlIds.add(txtClient); 
							txtClient.setHorizontalAlignment(_JXTextField.CENTER);
						}
						{
							txtUnitDesc = new _JXTextField("Unit Description");
							pnlIds.add(txtUnitDesc); 
							txtUnitDesc.setHorizontalAlignment(_JXTextField.CENTER);
						}

					}
					{
						pnlNorthCenter = new JPanel(new BorderLayout(3, 10)); 
						pnlTransDetails.add(pnlNorthCenter, BorderLayout.CENTER);
						{ 
							JPanel pnlTDWest = new JPanel(new GridLayout(3, 1,3,3)); 
							pnlNorthCenter.add(pnlTDWest, BorderLayout.WEST); 

							{
								JLabel lblProj = new JLabel("     Project:");
								pnlTDWest.add(lblProj); 	
							}
							{
								JLabel lblSeqNo= new JLabel("     Seq. No:");
								pnlTDWest.add(lblSeqNo); 
							}
							{
								JLabel lblActualDate = new JLabel("     Actual Date:"); 
								pnlTDWest.add(lblActualDate); 
							}

							{
								JPanel pnlTDCenter = new JPanel(new GridLayout(3, 1, 3, 3)); 
								pnlNorthCenter.add(pnlTDCenter, BorderLayout.CENTER); 

								{
									txtProjId = new _JXTextField("Project ID"); 
									pnlTDCenter.add(txtProjId); 
									txtProjId.setHorizontalAlignment(_JXTextField.CENTER);
								}
								{
									txtSeqNo = new _JXTextField("Seq. No"); 
									pnlTDCenter.add(txtSeqNo); 
									txtSeqNo.setHorizontalAlignment(_JXTextField.CENTER);
								}


								{
									dteActualDate = new JDateChooser(); 
									pnlTDCenter.add(dteActualDate); 
									dteActualDate.setDateFormatString("MM/dd/yy");
									dteActualDate.setDate(FncGlobal.getDateToday()); 
								}
							}
						}
					}
				}


			}
			{
				pnlPaymentDetails = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlPaymentDetails, BorderLayout.CENTER); 
				pnlPaymentDetails.setBorder(components._JTBorderFactory.createTitleBorder("Payment Details"));
				{
					JPanel pnlPDNorth = new JPanel(new BorderLayout(3, 3));
					pnlPaymentDetails.add(pnlPDNorth, BorderLayout.NORTH); 
					pnlPDNorth.setPreferredSize(new Dimension(0, 40));
					{
						JPanel pnlPart = new JPanel(new GridLayout(1, 3, 3, 3));
						pnlPDNorth.add(pnlPart, BorderLayout.WEST); 
						pnlPart.setPreferredSize(new Dimension(280, 0));

						{
							JPanel pnlPartlbl = new JPanel(new GridLayout(1, 2, 3, 3)); 
							pnlPart.add(pnlPartlbl, BorderLayout.WEST); 
							pnlPartlbl.setPreferredSize(new Dimension(30, 0));
							{
								JLabel lblPart = new JLabel("Particular:");
								pnlPartlbl.add(lblPart); 
							}
							{
								lookupPart = new _JLookup("Particular"); 
								pnlPartlbl.add(lookupPart); 
								lookupPart.setReturnColumn(0);
								//lookupPart.setLookupSQL(sqlParticular());
								lookupPart.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {

										Object data [] = ((_JLookup)event.getSource()).getDataSet();

										if (data != null) {
											txtParticular.setEnabled(true);
											txtParticular.setText(data[1].toString());
											txtAmt.setEnabled(true); 
											btnPost.setEnabled(true);
											btnCancel1.setEnabled(true); 

										} else {
											txtParticular.setText("");
										}

									}
								});

							}
						}
						{
							JPanel pnlPartTxt = new JPanel(new BorderLayout(3, 3)); 
							pnlPart.add(pnlPartTxt, BorderLayout.CENTER); 
							{
								txtParticular = new JTextField(); 
								pnlPartTxt.add(txtParticular); 
							}

						}


					}
					{
						JPanel pnlAmt = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlPDNorth.add(pnlAmt, BorderLayout.CENTER); 
						{
							JLabel lblAmt = new JLabel("         Amount:"); 
							pnlAmt.add(lblAmt);
						}
						{
							txtAmt = new _JXFormattedTextField("0.00");
							pnlAmt.add(txtAmt); 
							txtAmt.setHorizontalAlignment(JTextField.CENTER);
							txtAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
						}

					}
					{
						JPanel pnlTotalRet = new JPanel(new GridLayout(1, 2, 3, 3)); 
						pnlPDNorth.add(pnlTotalRet, BorderLayout.EAST); 
						pnlTotalRet.setPreferredSize(new Dimension(250, 0));
						{
							JLabel lblTotRet = new JLabel("    Total Retention:"); 
							pnlTotalRet.add(lblTotRet); 
						}
						{
							txtTotRet = new _JXFormattedTextField("0.00"); 
							pnlTotalRet.add(txtTotRet); 
							txtTotRet.setHorizontalAlignment(JTextField.CENTER); 
							txtTotRet.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
							txtTotRet.setEditable(false); 
						}

					}
				}
				{
					pnlPDCenter = new JPanel(new BorderLayout(3, 3));
					pnlPaymentDetails.add(pnlPDCenter, BorderLayout.CENTER);
					pnlPDCenter.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

					{
						JPanel pnlPayablesWest = new JPanel(new BorderLayout(3, 3)); 
						pnlPDCenter.add(pnlPayablesWest, BorderLayout.WEST);
						pnlPayablesWest.setPreferredSize(new Dimension(320, 0));
						pnlPayablesWest.setBorder(_EMPTY_BORDER);

						{
							JPanel lblPayables1 = new JPanel(new GridLayout(4, 1, 3, 3)); 
							pnlPayablesWest.add(lblPayables1, BorderLayout.WEST);
							lblPayables1.setPreferredSize(new Dimension(80, 0));


							{
								lblPayables1.add(Box.createHorizontalBox());
							}
							{
								JLabel lblRet1 = new JLabel("  RetFee1: "); 
								lblPayables1.add(lblRet1);
							}
							{
								JLabel lblRet21 = new JLabel("  RetFee2-1: "); 
								lblPayables1.add(lblRet21);
							}
							{
								JLabel lblRet22 = new JLabel("  RetFee2-2: "); 
								lblPayables1.add(lblRet22);
							}

						}
						{
							JPanel txtPayables = new JPanel(new GridLayout(4, 1, 3, 3)); 
							pnlPayablesWest.add(txtPayables, BorderLayout.CENTER);

							{
								JLabel lblPayables = new JLabel("  PAYABLES"); 
								txtPayables.add(lblPayables);
								lblPayables.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
							}
							{
								txtRetFee1 = new _JXFormattedTextField("0.00");
								txtPayables.add(txtRetFee1); 
								txtRetFee1.setHorizontalAlignment(JTextField.CENTER); 
								txtRetFee1.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
								txtRetFee1.setEditable(false); 
							}
							{
								txtRetFee21 = new _JXFormattedTextField("0.00"); 
								txtPayables.add(txtRetFee21);
								txtRetFee21.setHorizontalAlignment(JTextField.CENTER); 
								txtRetFee21.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
								txtRetFee21.setEditable(false); 
							}
							{
								txtRetFee22 = new _JXFormattedTextField("0.00"); 
								txtPayables.add(txtRetFee22); 
								txtRetFee22.setHorizontalAlignment(JTextField.CENTER); 
								txtRetFee22.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
								txtRetFee22.setEditable(false); 
							}

						}


					}
					{
						JPanel pnlBalances = new JPanel(new BorderLayout(3, 3));
						pnlPDCenter.add(pnlBalances, BorderLayout.CENTER);
						pnlBalances.setBorder(_EMPTY_BORDER);
						{
							JPanel pnlBalWest = new JPanel(new GridLayout(4, 1, 3, 3)); 
							pnlBalances.add(pnlBalWest, BorderLayout.WEST);
							pnlBalWest.setPreferredSize(new Dimension(80, 0));

							{
								pnlBalWest.add(Box.createHorizontalBox()); 
							}
							{
								JLabel lblRet1bal = new JLabel("  RetFee1: "); 
								pnlBalWest.add(lblRet1bal);
							}
							{
								JLabel lblRet1Bal = new JLabel("  RetFee2-1: "); 
								pnlBalWest.add(lblRet1Bal);
							}
							{
								JLabel lblRet2Bal = new JLabel("  RetFee2-2: "); 
								pnlBalWest.add(lblRet2Bal);
							}
						}
						{
							JPanel pnlBalCenter = new JPanel(new GridLayout(4, 1, 3, 3)); 
							pnlBalances.add(pnlBalCenter, BorderLayout.CENTER); 

							{
								JLabel lblBalances = new JLabel("  BALANCES"); 
								pnlBalCenter.add(lblBalances);
								lblBalances.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
							}

							{
								txtRetFee1bal = new _JXFormattedTextField("0.00"); 
								pnlBalCenter.add(txtRetFee1bal); 
								txtRetFee1bal.setHorizontalAlignment(JTextField.CENTER); 
								txtRetFee1bal.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
								txtRetFee1bal.setEditable(false); 

							}
							{
								txtRetFee21Bal = new _JXFormattedTextField("0.00"); 
								pnlBalCenter.add(txtRetFee21Bal); 
								txtRetFee21Bal.setHorizontalAlignment(JTextField.CENTER); 
								txtRetFee21Bal.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
								txtRetFee21Bal.setEditable(false); 
							}
							{
								txtRetFee22Bal = new _JXFormattedTextField("0.00"); 
								pnlBalCenter.add(txtRetFee22Bal); 
								txtRetFee22Bal.setHorizontalAlignment(JTextField.CENTER); 
								txtRetFee22Bal.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
								txtRetFee22Bal.setEditable(false); 
							}
						}
					}


				}

				{
					pnlPDEast = new JPanel(new GridLayout(3, 1, 5, 5)); 
					pnlPaymentDetails.add(pnlPDEast, BorderLayout.EAST);
					pnlPDEast.setPreferredSize(new Dimension(80, 0));
					//pnlPDEast.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
					pnlPDEast.setBorder(lineBorder);

					{
						btnPost = new JButton("POST"); 
						pnlPDEast.add(btnPost);
						btnPost.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {

								if (toPost()) {
									postTransaction(); 
									lookupPart.setValue(null);
									txtParticular.setText("");
									txtAmt.setText("0.00"); 
									txtAmt.setEnabled(false); 
									btnState(true, true, true, true, true, false, false);
								}


							}
						}); 


					}
					{
						btnEdit1 = new JButton("EDIT");
						pnlPDEast.add(btnEdit1); 

					}	
					{
						btnCancel1 = new JButton("CANCEL");
						pnlPDEast.add(btnCancel1);

					}

				}
			}

			{
				pnlPaymentList = new JPanel(new BorderLayout(3, 3)); 
				pnlMain.add(pnlPaymentList, BorderLayout.SOUTH); 
				pnlPaymentList.setBorder(components._JTBorderFactory.createTitleBorder("Payment List"));
				pnlPaymentList.setPreferredSize(new Dimension(0, 200));

				{
					JPanel pnlTblPList = new JPanel(new BorderLayout(3, 3)); 
					pnlPaymentList.add(pnlTblPList, BorderLayout.CENTER); 
					{

						scrollPaymentList = new JScrollPane(); 
						pnlTblPList.add(scrollPaymentList, BorderLayout.CENTER); 
						scrollPaymentList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					}
					{
						modelRetFeeList = new modelRetFeeMultiPayments(); 
						tablePaymentList = new _JTableMain(modelRetFeeList); 
						//tablePaymentList.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq No.");
						
						tablePaymentList.getColumnModel().getColumn(0).setPreferredWidth(30);
						tablePaymentList.getColumnModel().getColumn(1).setPreferredWidth(200);
						tablePaymentList.getColumnModel().getColumn(2).setPreferredWidth(120);
						
						scrollPaymentList.setViewportView(tablePaymentList);
						tablePaymentList.setSortable(false);			
					}

				}
				{
					JPanel pnlButtons = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlPaymentList.add(pnlButtons, BorderLayout.SOUTH);
					pnlButtons.setPreferredSize(new Dimension(0, 35));
					{
						btnNew = new JButton("ADD NEW"); 
						pnlButtons.add(btnNew); 
						btnNew.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								clearFields();
								
							}
						}); 
						
					}
					{
						// ADDED BY MONIQUE DTD 10-18-2023; FOR REMOVAL OF ROWS - INCORRECT INPUT
						btnRemoveRow = new JButton("Remove Retention");
						pnlButtons.add(btnRemoveRow);
						btnRemoveRow.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								 removePayment();
								
							}
						});
					}
					{
						btnCancel = new JButton("Cancel");
						pnlButtons.add(btnCancel);
						btnCancel.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								cancelRetFeeMP();
								
							}
						});
					}
					{
						btnSave = new JButton("Save");
						pnlButtons.add(btnSave);
						btnSave.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								
								computeTotal();
								if (JOptionPane.showConfirmDialog(getContentPane(), "Total Amount: " + total_amt, "Save",
										JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
										
										String ar_no = saveRetFeeOnline();
										if (ar_no != null) {
											JOptionPane.showMessageDialog(RetFeeOnlineMultiplePayments.this,
													String.format("Retention saved with AR NO: %s", ar_no), "Save",
													JOptionPane.INFORMATION_MESSAGE);
											
											cancelRetFeeMP();
									}
									
								}																
							}
						});
					}
				}


			}
		}

		//INITIALIZE COMPONENTS
		initializeComponents();

	} 


	private void initializeComponents() {

		lookupCompany.setEnabled(true);
		lookupClient.setEnabled(false);
		dteActualDate.setEnabled(false);
		lookupPart.setEnabled(false);
		txtParticular.setEnabled(false);
		txtAmt.setEnabled(false);
		txtRetFee1.setEnabled(false);
		txtRetFee21.setEnabled(false);
		txtRetFee22.setEnabled(false);
		txtRetFee1bal.setEnabled(false);
		txtRetFee21Bal.setEnabled(false);
		txtRetFee22Bal.setEnabled(false);
		txtTotRet.setEnabled(false);
		

		btnState(false, false, false, false, false, false, false);	
	}
	
	private void clearFields () {
		
		lookupClient.setValue(null);
		txtClient.setText("");
		txtUnitId.setText("");
		txtUnitDesc.setText("");
		txtProjId.setText("");
		txtSeqNo.setText("");
		lookupPart.setValue(null);
		txtParticular.setText("");
		txtParticular.setEnabled(false);
		txtAmt.setText("0.00"); 
		txtAmt.setEnabled(false); 
		txtTotRet.setText("0.00");
		txtTotRet.setEnabled(false);
		txtRetFee1.setText("0.00"); 
		txtRetFee1.setEnabled(false); 
		txtRetFee21.setText("0.00");
		txtRetFee21.setEnabled(false);
		txtRetFee22.setText("0.00"); 
		txtRetFee22.setEnabled(false); 
		txtRetFee1bal.setText("0.00");
		txtRetFee1bal.setEnabled(false);
		txtRetFee21Bal.setText("0.00"); 
		txtRetFee21Bal.setEnabled(false); 
		txtRetFee22Bal.setText("0.00");
		txtRetFee22Bal.setEnabled(false);	
	}
	
private void cancelRetFeeMP() {
		
		lookupClient.setValue(null);
		txtClient.setText("");
		txtUnitId.setText("");
		txtUnitDesc.setText("");
		txtProjId.setText("");
		txtSeqNo.setText("");
		lookupPart.setValue(null);
		txtParticular.setText("");
		txtParticular.setEnabled(false);
		txtAmt.setText("0.00"); 
		txtAmt.setEnabled(false); 
		txtTotRet.setText("0.00");
		txtTotRet.setEnabled(false);
		txtRetFee1.setText("0.00"); 
		txtRetFee1.setEnabled(false); 
		txtRetFee21.setText("0.00");
		txtRetFee21.setEnabled(false);
		txtRetFee22.setText("0.00"); 
		txtRetFee22.setEnabled(false); 
		txtRetFee1bal.setText("0.00");
		txtRetFee1bal.setEnabled(false);
		txtRetFee21Bal.setText("0.00"); 
		txtRetFee21Bal.setEnabled(false); 
		txtRetFee22Bal.setText("0.00");
		txtRetFee22Bal.setEnabled(false);	
		modelRetFeeList.clear();
		btnState(true, false, false, false, false, false, false);
		
	}


	private void btnState (Boolean New, Boolean Edit, Boolean Cancel, Boolean Save, Boolean Post, Boolean Edit1, Boolean Cancel1 ) {
		btnNew.setEnabled(New);
		btnRemoveRow.setEnabled(Edit);
		btnCancel.setEnabled(Cancel);
		btnSave.setEnabled(Save);
		btnPost.setEnabled(Post);
		btnEdit1.setEnabled(Edit1);
		btnCancel1.setEnabled(Cancel1);
	}
	private void DataState (Boolean PayEPEB, Boolean PayRet1, Boolean PayRet2, Boolean BalEPEB, Boolean BalRet1, Boolean BalRet2) {
		txtRetFee1.setEnabled(PayEPEB);
		txtRetFee21.setEnabled(PayRet1);
		txtRetFee22.setEnabled(PayRet2);
		txtRetFee1bal.setEnabled(BalEPEB);
		txtRetFee21Bal.setEnabled(BalRet1);
		txtRetFee22Bal.setEnabled(BalRet2);
	}
	

	private String sqlCompany() {
		return "SELECT co_id as \"ID\", \n" + "company_name as \"Company Name\", \n" + "company_alias as \"Alias\" \n"
				+ "FROM mf_company;";
	}	

	private String sqlParticular() {
		return "select pay_part_id, particulars, partdesc\n" + "	from mf_pay_particular \n"
				+ "	where particulars ~* 'LNREL' or pay_part_id in ('218', '246', '247')\n" + "	order by particulars";
	}
	private void GenFees() {

		pgSelect db = new pgSelect(); 
		db.select("select * from view_ret_fee_online_amounts_v2('"+lookupClient.getValue().toString()+"', '"+txtProjId.getText()+"', '"+txtUnitId.getText()+"', '"+txtSeqNo.getText()+"')"); 

		if (db.isNotNull()) {
			txtRetFee1.setValue(db.getResult()[0][0]);
			txtRetFee21.setValue(db.getResult()[0][2]);
			txtRetFee22.setValue(db.getResult()[0][4]);

			txtRetFee1bal.setValue(db.getResult()[0][1]);
			txtRetFee21Bal.setValue(db.getResult()[0][3]);
			txtRetFee22Bal.setValue(db.getResult()[0][5]);

			txtTotRet.setValue(db.getResult()[0][6]);

		}
	}

	private Boolean toPost(){
		if (txtAmt.getValued() == null
				|| ((BigDecimal) txtAmt.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0) {
			JOptionPane.showMessageDialog(getContentPane(), "Please input valid cash amount.", "Post",
					JOptionPane.WARNING_MESSAGE);
			return false;
		} 

		return true;
	}

	private void postTransaction() { 

		String client_name = txtClient.getText(); 
		String unit = txtUnitDesc.getText(); 
		String part_id = lookupPart.getValue();
		String particular = txtParticular.getText(); 
		BigDecimal amount = (BigDecimal) txtAmt.getValued(); 
		BigDecimal payable_retfee1 = (BigDecimal) txtRetFee1.getValued(); 
		BigDecimal bal_retfee1 = (BigDecimal) txtRetFee1bal.getValued(); 
		BigDecimal payable_retfee21 = (BigDecimal) txtRetFee21.getValued(); 
		BigDecimal bat_retfee21 = (BigDecimal) txtRetFee21Bal.getValued();
		BigDecimal payable_retfee22 = (BigDecimal) txtRetFee22.getValued(); 
		BigDecimal bal_retfee22 = (BigDecimal) txtRetFee22Bal.getValued();
		BigDecimal total_retention = (BigDecimal) txtTotRet.getValued();
		entity_id = lookupClient.getValue().toString();
		proj_id = txtProjId.getText(); 
		pbl_id = txtUnitId.getText(); 
		seq_no = txtSeqNo.getText(); 
		
		int rowNumber = modelRetFeeList.getRowCount() + 1; 
		
		modelRetFeeList.addRow(new Object [] { rowNumber, client_name, unit,  part_id, particular, amount, payable_retfee1, bal_retfee1, payable_retfee21, bat_retfee21, payable_retfee22, bal_retfee22,
				total_retention, entity_id, proj_id, pbl_id, seq_no});

	}
	
	private String getARNo() {
		String ar_no = "";
//		String SQL = "SELECT get_receipt_no_to_issue('" + lookupCompany.getValue() + "', '03', '" + UserInfo.branch_id
//				+ "', '" + UserInfo.EmployeeCode + "')";
		
		String SQL = "SELECT get_receipt_no_to_issue('" + lookupCompany.getValue() + "', '01', '" + UserInfo.branch_id
				+ "', '" + UserInfo.EmployeeCode + "')";
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			ar_no = (String) db.getResult()[0][0];
		} else {
			ar_no = null;
		}
		return ar_no;
		
	}
	private String saveRetFeeOnline() {
	
		String entity_id = "";
		String proj_id = "";
		String pbl_id = "";
		String seq_no = "";
		String part_type = "";
		BigDecimal amount = new BigDecimal("0.00");
		String client_seqno = "";
		String ar_no = getARNo();
		

		for (int x = 0; x < modelRetFeeList.getRowCount(); x++) {
			String name = (String) modelRetFeeList.getValueAt(x, 1);
			System.out.println("x = " + x);

			if (name != null) {
				amount = new BigDecimal("0.00");
				System.out.printf("valuer of amount: %s", modelRetFeeList.getValueAt(x, 5));

				try {
					amount = (BigDecimal) modelRetFeeList.getValueAt(x, 5);
				} catch (ClassCastException e) {

					if (modelRetFeeList.getValueAt(x, 2) instanceof Double) {
						amount = BigDecimal.valueOf((Double) modelRetFeeList.getValueAt(x, 5));
					}

					if (modelRetFeeList.getValueAt(x, 2) instanceof Long) {
						amount = BigDecimal.valueOf((Long) modelRetFeeList.getValueAt(x, 5));
					}
				}

				entity_id = (String) modelRetFeeList.getValueAt(x, 13);
				proj_id = (String) modelRetFeeList.getValueAt(x, 14);
				pbl_id = (String) modelRetFeeList.getValueAt(x, 15);
				seq_no = (String) modelRetFeeList.getValueAt(x, 16);
				part_type = (String) modelRetFeeList.getValueAt(x, 3);
		
			}
		
			pgSelect db = new pgSelect();
			
			String SQL = "SELECT * from sp_post_ret_fee_online_multi_payments('" + lookupCompany.getValue() + "','" + entity_id
					+ "','" + proj_id + "','" + pbl_id + "', " + seq_no + ", " + amount + ", '" + dteActualDate.getDate()
					+ "'::TIMESTAMP WITHOUT TIME ZONE,'" + UserInfo.Branch + "','" + UserInfo.EmployeeCode + "', '"
					+ ar_no + "', '" + part_type + "')";

			db.select(SQL, "Save", true);

			FncSystem.out("Display SQL for saveRetFeeOnline", SQL);

			if (db.isNotNull()) {
				ar_no = (String) db.getResult()[0][0];
				client_seqno = (String) db.getResult()[0][1];
			}
		}

		System.out.println("");
		System.out.println("client_seqno = " + client_seqno);
		System.out.println("ar_no = " + ar_no);
		
		if (ar_no != null && client_seqno != null) {
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("client_seqno", client_seqno);
			mapParameters.put("ar_no", ar_no);
			mapParameters.put("credit_ar_no", null);
			mapParameters.put("recpt_type", "AR No.");
			mapParameters.put("credited_amount", null);
			mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
			
			String co_id = FncGlobal.GetString("select co_id from rf_pay_header a \n" + 
					"where client_seqno = '"+client_seqno+"'");
			
			System.out.println("mapParameters: " + mapParameters);
			
			/*if(co_id.equals("01")) {
				FncReport.generateReport("/Reports/rptARReceipt_VDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
			}else if(co_id.equals("02")) {
				FncReport.generateReport("/Reports/rptARReceipt_CDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
			}else if(co_id.equals("04")) {
				FncReport.generateReport("/Reports/rptARReceipt_ADC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
			}else if(co_id.equals("05")) {
				FncReport.generateReport("/Reports/rptARReceipt_EDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
			}*/
						
			FncReport.generateReport("/Reports/rptARRetFeeOL_CDC.jasper", "Acknowledgment Receipt",
					String.format("AR No.: %s", ar_no), mapParameters);
			
			
		}

		return ar_no;
	}

	
	private void computeTotal() {
		total_amt = 0.00;
		for (int i = 0; i < modelRetFeeList.getRowCount(); i++ ) {
			Object amt = modelRetFeeList.getValueAt(i, 5);
			total_amt += Double.parseDouble(amt.toString());
			
		}
		
	}
	
	//ADDED BY MONIQUE DTD 10-17-2023
	private void removePayment() {
		int[] rows = tablePaymentList.getSelectedRows();

		for(int x = rows.length - 1; x >= 0; x--){
			modelRetFeeList.removeRow(rows[x]);
		}
	}


}
