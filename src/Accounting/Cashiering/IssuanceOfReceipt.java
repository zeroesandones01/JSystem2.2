package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Buyers.ClientServicing._OrderOfPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Renderer.TextRenderer;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelIssuanceOfReceipt_PaymentList;

/**
 * @author Alvin Gonzales
 *
 */
public class IssuanceOfReceipt extends _JInternalFrame implements ActionListener, _GUI, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -711623292464110324L;

	static String title = "Issuance of Receipt";
	Dimension frameSize = new Dimension(800, 550);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlDates;
	private JPanel pnlPaymentDetails;
	private JPanel pnlReceipts;

	private JLabel lblTransactionType;

	private _JXTextField txtClientSequenceNo;
	private _JXTextField txtClientName;
	private _JXTextField txtCompanyName;
	private _JXTextField txtProjectName;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDescription;
	private _JXTextField txtModelID;
	private _JXTextField txtModelName;
	private _JXTextField txtSequence;
	private _JXTextField txtRemarks;
	private _JXTextField txtOfficialReceipt;
	private _JXTextField txtProvisionalReceipt;
	private _JXTextField txtAcknowledgementReceipt;
	private _JXTextField txtPagIBIGReceipt;
	private _JXTextField txtSalesInvoice;

	private _JXFormattedTextField txtSellingPrice;
	private _JXFormattedTextField txtTotalAmount;

	private _JDateChooser dateTransaction;
	private _JDateChooser dateActual;

	private _JLookup lookupClient;
	private _JLookup lookupCompany;
	private _JLookup lookupProject;

	private JRadioButton rbOfficialReceipt;
	private JRadioButton rbProvisionalReceipt;
	private JRadioButton rbAcknowledgementReceipt;
	private JRadioButton rbPagIBIGReceipt;
	private JRadioButton rbSalesInvoice;

	private JButton btnNewPayment;
	private JButton btnOrderedPayment;
	private JButton btnNewCashReturn;
	private JButton btnReturnToCSD;
	private JButton btnSave;
	private JButton btnPrintReceipt;
	private JButton btnCancel;
	private JButton btnAddRow;
	private JButton btnRemoveRow;

	private _JScrollPaneMain scrollPaymentList;
	private _JTableMain tblPaymentList;
	private JList rowHeaderPaymentList;
	private _JScrollPaneTotal scrollPaymentListTotal;
	private _JTableTotal tblPaymentListTotal;
	private modelIssuanceOfReceipt_PaymentList modelPaymentListTotal;
	private modelIssuanceOfReceipt_PaymentList modelPaymentList;

	private String clientSeqNo = "";
	private String tran_type = "";
	private String clientName = "";
	private String projID = "";
	private String entityID = "";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public IssuanceOfReceipt() {
		super(title, true, true, true, true);
		initGUI();
	}

	public IssuanceOfReceipt(String title) {
		super(title);
		initGUI();
	}

	public IssuanceOfReceipt(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
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

		// disabled due to unnecessary icon on toolbar
		/*
		 * Image iconInternalFrame = new
		 * ImageIcon(FncLookAndFeel.class.getClassLoader().getResource(
		 * "Images/buyers/issueanceofreceipt.png")).getImage(); iconInternalFrame =
		 * iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
		 * this.setFrameIcon(new ImageIcon(iconInternalFrame));
		 */
		{
			JPanel pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(3, 3));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 3));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 234));
				{
					pnlPaymentDetails = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlPaymentDetails, BorderLayout.CENTER);
					pnlPaymentDetails.setBorder(components.JTBorderFactory.createTitleBorder("Payment Details"));
					{
						JPanel pnlPaymentDetailsWest = new JPanel(new GridLayout(8, 1, 3, 3));
						pnlPaymentDetails.add(pnlPaymentDetailsWest, BorderLayout.WEST);
						pnlPaymentDetailsWest.setPreferredSize(new Dimension(80, 0));
						{
							JLabel lblSequenceNo = new JLabel("Client Seq.");
							pnlPaymentDetailsWest.add(lblSequenceNo);
						}
						{
							JLabel lblClient = new JLabel("Client");
							pnlPaymentDetailsWest.add(lblClient);
						}
						{
							JLabel lblCompany = new JLabel("Company");
							pnlPaymentDetailsWest.add(lblCompany);
						}
						{
							JLabel lblProject = new JLabel("Project");
							pnlPaymentDetailsWest.add(lblProject);
						}
						{
							JLabel lblUnit = new JLabel("Unit");
							pnlPaymentDetailsWest.add(lblUnit);
						}
						{
							JLabel lblModel = new JLabel("Model");
							pnlPaymentDetailsWest.add(lblModel);
						}
						{
							JLabel lblLotArea = new JLabel("Unit Sequence");
							pnlPaymentDetailsWest.add(lblLotArea);
						}
						{
							JLabel lblRemarks = new JLabel("Remarks");
							pnlPaymentDetailsWest.add(lblRemarks);
						}
						
					}
					{
						JPanel pnlPaymentDetailsCenter = new JPanel(new BorderLayout(3, 3));
						pnlPaymentDetails.add(pnlPaymentDetailsCenter, BorderLayout.CENTER);
						{
							JPanel pnlPDCenter = new JPanel(new BorderLayout(3, 3));
							pnlPaymentDetailsCenter.add(pnlPDCenter, BorderLayout.CENTER);
							
							{
								JPanel jPanel1 = new JPanel(new GridLayout(7, 1, 3, 3));
								pnlPDCenter.add(jPanel1, BorderLayout.WEST);
								jPanel1.setPreferredSize(new Dimension(100, 0));
								{
									txtClientSequenceNo = new _JXTextField("");
									jPanel1.add(txtClientSequenceNo);
									txtClientSequenceNo.setFont(new Font("Tahoma", Font.PLAIN, 12));
									txtClientSequenceNo.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									lookupClient = new _JLookup(null, "Client", 0);
									jPanel1.add(lookupClient);
									lookupClient.setReturnColumn(0);
									lookupClient.setFilterName(true);
									lookupClient.setPrompt("Client ID");
									lookupClient.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												entityID = (String) data[0];
												clientName = (String) data[1];
												txtClientName.setText(clientName);

												String selectedTransaction = lblTransactionType.getText()
														.replaceAll("\\[|\\]", "").trim();

												if (selectedTransaction.equals("NEW PAYMENT")) {
													String entity_id = (String) data[0];
													String proj_id = (String) data[6];
													String pbl_id = (String) data[3];
													Integer seq_no = (Integer) data[4];

													displayNewPaymentDetails(entity_id, proj_id, pbl_id, seq_no);
												}
											}
										}
									});
								}
								{
									lookupCompany = new _JLookup(null, "Company", 0);
									jPanel1.add(lookupCompany);
									lookupCompany.setReturnColumn(0);
									lookupCompany.setLookupSQL(SQL_COMPANY());
									lookupCompany.setPrompt("Company ID");
									lookupCompany.setEditable(false);
									lookupCompany.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												txtCompanyName.setText((String) data[1]);

												lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));
											}
										}
									});
								}
								{
									lookupProject = new _JLookup(null, "Company", "Please select company first.");
									jPanel1.add(lookupProject);
									lookupProject.setReturnColumn(0);
									lookupProject.setPrompt("Project ID");
									lookupProject.setEditable(false);
									lookupProject.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												projID = (String) data[0];
												txtProjectName.setText((String) data[1]);
											}
										}
									});
								}
								{
									txtUnitID = new _JXTextField("Unit ID");
									jPanel1.add(txtUnitID);
									txtUnitID.setHorizontalAlignment(_JXTextField.CENTER);
								}
								{
									txtModelID = new _JXTextField("Model ID");
									jPanel1.add(txtModelID);
									txtModelID.setHorizontalAlignment(_JXTextField.CENTER);
								}
								{
									txtSequence = new _JXTextField("Sequence");
									jPanel1.add(txtSequence);
									txtSequence.setHorizontalAlignment(_JXTextField.CENTER);
								}
								
							}
							{
								JPanel jPanel2 = new JPanel(new GridLayout(7, 1, 3, 3));
								pnlPDCenter.add(jPanel2, BorderLayout.CENTER);
								{
									lblTransactionType = new JLabel("[ ]");
									jPanel2.add(lblTransactionType);
								}
								{
									txtClientName = new _JXTextField("Client Name");
									jPanel2.add(txtClientName);
								}
								{
									txtCompanyName = new _JXTextField("Company Name");
									jPanel2.add(txtCompanyName);
								}
								{
									txtProjectName = new _JXTextField("Project Name");
									jPanel2.add(txtProjectName);
								}
								{
									txtUnitDescription = new _JXTextField("Unit Description");
									jPanel2.add(txtUnitDescription);
								}
								{
									txtModelName = new _JXTextField("Model Name");
									jPanel2.add(txtModelName);
								}
								{
									JPanel pnlSellingPrice = new JPanel(new BorderLayout(3, 3));
									jPanel2.add(pnlSellingPrice);
									{
										JLabel lblSellingPrice = new JLabel("Selling Price ", JLabel.TRAILING);
										pnlSellingPrice.add(lblSellingPrice, BorderLayout.WEST);
										lblSellingPrice.setPreferredSize(new Dimension(110, 0));
									}
									{
										txtSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlSellingPrice.add(txtSellingPrice, BorderLayout.CENTER);
										txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtSellingPrice.setEditable(false);
									}
								}
								
							}
						}
						{
							txtRemarks = new _JXTextField("Remarks");
							pnlPaymentDetailsCenter.add(txtRemarks, BorderLayout.SOUTH);	
						}
					}
				}
				{
					JPanel pnlNorthEast = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
					{
						pnlDates = new JPanel(new BorderLayout(3, 3));
						pnlNorthEast.add(pnlDates, BorderLayout.NORTH);
						pnlDates.setBorder(components.JTBorderFactory.createTitleBorder("Dates"));
						pnlDates.setPreferredSize(new Dimension(300, 84));
						{
							JPanel pnlDatesLabels = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlDates.add(pnlDatesLabels, BorderLayout.WEST);
							pnlDatesLabels.setPreferredSize(new Dimension(150, 0));
							{
								JLabel lblTransactionDate = new JLabel("Transaction Date");
								pnlDatesLabels.add(lblTransactionDate);
							}
							{
								JLabel lblActualDate = new JLabel("Actual Date");
								pnlDatesLabels.add(lblActualDate);
							}
						}
						{
							JPanel pnlDatesDates = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlDates.add(pnlDatesDates, BorderLayout.CENTER);
							{
								dateTransaction = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlDatesDates.add(dateTransaction);
							}
							{
								dateActual = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlDatesDates.add(dateActual);
							}
						}
					}
					{
						pnlReceipts = new JPanel(new BorderLayout(3, 3));
						pnlNorthEast.add(pnlReceipts, BorderLayout.CENTER);
						pnlReceipts.setBorder(components.JTBorderFactory.createTitleBorder("Receipt"));
						pnlReceipts.setPreferredSize(new Dimension(300, 0));
						{
							JPanel pnlRadioButtons = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlReceipts.add(pnlRadioButtons, BorderLayout.WEST);
							pnlRadioButtons.setPreferredSize(new Dimension(175, 0));
							{
								rbOfficialReceipt = new JRadioButton("Official (OR)");
								pnlRadioButtons.add(rbOfficialReceipt);
								rbOfficialReceipt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										if (rbOfficialReceipt.isSelected() == true) {
											rbOfficialReceipt.setSelected(true);
											rbProvisionalReceipt.setSelected(false);
											rbAcknowledgementReceipt.setSelected(false);
											rbPagIBIGReceipt.setSelected(false);

											txtOfficialReceipt.setEnabled(true);
											txtProvisionalReceipt.setEnabled(false);
											txtAcknowledgementReceipt.setEnabled(false);
											txtPagIBIGReceipt.setEnabled(false);

											txtOfficialReceipt.setEditable(true);
											txtProvisionalReceipt.setEditable(false);
											txtAcknowledgementReceipt.setEditable(false);
											txtPagIBIGReceipt.setEditable(false);

											txtOfficialReceipt.setText("");
											txtOfficialReceipt.requestFocus();
											txtProvisionalReceipt.setText("PR");
											txtAcknowledgementReceipt.setText("AR");
											txtPagIBIGReceipt.setText("PIR");
										}
									}
								});
							}
							{
								rbProvisionalReceipt = new JRadioButton("Provisional (PR)");
								pnlRadioButtons.add(rbProvisionalReceipt);
								rbProvisionalReceipt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										if (rbProvisionalReceipt.isSelected() == true) {
											rbOfficialReceipt.setSelected(false);
											rbProvisionalReceipt.setSelected(true);
											rbAcknowledgementReceipt.setSelected(false);
											rbPagIBIGReceipt.setSelected(false);
											rbSalesInvoice.setSelected(false);

											txtOfficialReceipt.setEnabled(false);
											txtProvisionalReceipt.setEnabled(true);
											txtAcknowledgementReceipt.setEnabled(false);
											txtPagIBIGReceipt.setEnabled(false);
											txtSalesInvoice.setEnabled(false);

											txtOfficialReceipt.setEditable(false);
											txtProvisionalReceipt.setEditable(true);
											txtAcknowledgementReceipt.setEditable(false);
											txtPagIBIGReceipt.setEditable(false);
											txtSalesInvoice.setEditable(false);

											txtOfficialReceipt.setText("OR");
											txtProvisionalReceipt.setText("");
											txtProvisionalReceipt.requestFocus();
											txtAcknowledgementReceipt.setText("AR");
											txtPagIBIGReceipt.setText("PIR");
											txtSalesInvoice.setText("SI");
										}
									}
								});
							}
							{
								rbAcknowledgementReceipt = new JRadioButton("Acknowledgement (AR)");
								pnlRadioButtons.add(rbAcknowledgementReceipt);
								rbAcknowledgementReceipt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										if (rbAcknowledgementReceipt.isSelected() == true) {
											rbOfficialReceipt.setSelected(false);
											rbProvisionalReceipt.setSelected(false);
											rbAcknowledgementReceipt.setSelected(true);
											rbPagIBIGReceipt.setSelected(false);
											rbSalesInvoice.setSelected(false);

											txtOfficialReceipt.setEnabled(false);
											txtProvisionalReceipt.setEnabled(false);
											txtAcknowledgementReceipt.setEnabled(true);
											txtPagIBIGReceipt.setEnabled(false);
											txtSalesInvoice.setEnabled(false);

											txtOfficialReceipt.setEditable(false);
											txtProvisionalReceipt.setEditable(false);
											txtAcknowledgementReceipt.setEditable(true);
											txtPagIBIGReceipt.setEditable(false);
											txtSalesInvoice.setEditable(false);

											txtOfficialReceipt.setText("OR");
											txtProvisionalReceipt.setText("PR");
											txtAcknowledgementReceipt.setText("");
											txtAcknowledgementReceipt.requestFocus();
											txtPagIBIGReceipt.setText("PIR");
											txtSalesInvoice.setText("SI");
										}
									}
								});
							}
							{
								rbPagIBIGReceipt = new JRadioButton("PagIBIG (PIR)");
								pnlRadioButtons.add(rbPagIBIGReceipt);
								rbPagIBIGReceipt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										if (rbPagIBIGReceipt.isSelected() == true) {
											rbOfficialReceipt.setSelected(false);
											rbProvisionalReceipt.setSelected(false);
											rbAcknowledgementReceipt.setSelected(false);
											rbPagIBIGReceipt.setSelected(true);
											rbSalesInvoice.setSelected(false);

											txtOfficialReceipt.setEnabled(false);
											txtProvisionalReceipt.setEnabled(false);
											txtAcknowledgementReceipt.setEnabled(false);
											txtPagIBIGReceipt.setEnabled(true);
											txtSalesInvoice.setEnabled(false);

											txtOfficialReceipt.setEditable(false);
											txtProvisionalReceipt.setEditable(false);
											txtAcknowledgementReceipt.setEditable(false);
											txtPagIBIGReceipt.setEditable(true);
											txtSalesInvoice.setEditable(false);

											txtOfficialReceipt.setText("OR");
											txtProvisionalReceipt.setText("PR");
											txtAcknowledgementReceipt.setText("AR");
											txtPagIBIGReceipt.setText("");
											txtSalesInvoice.requestFocus();
											txtSalesInvoice.setText("SI");
										}
									}
								});
							}
							// ADDED BY MONIQUE FOR ISSUANCE OF SALES INVOICE DTD 2022-07-11; WITH DCRF#2121
							{
								rbSalesInvoice = new JRadioButton("Sales Invoice (SI)");
								pnlRadioButtons.add(rbSalesInvoice);
								rbSalesInvoice.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										if (rbSalesInvoice.isSelected() == true) {
											rbOfficialReceipt.setSelected(false);
											rbProvisionalReceipt.setSelected(false);
											rbAcknowledgementReceipt.setSelected(false);
											rbPagIBIGReceipt.setSelected(false);
											rbSalesInvoice.setSelected(true);

											txtOfficialReceipt.setEnabled(false);
											txtProvisionalReceipt.setEnabled(false);
											txtAcknowledgementReceipt.setEnabled(false);
											txtPagIBIGReceipt.setEnabled(false);
											txtSalesInvoice.setEnabled(true);

											txtOfficialReceipt.setEditable(false);
											txtProvisionalReceipt.setEditable(false);
											txtAcknowledgementReceipt.setEditable(false);
											txtPagIBIGReceipt.setEditable(false);
											txtSalesInvoice.setEditable(true);

											txtOfficialReceipt.setText("OR");
											txtProvisionalReceipt.setText("PR");
											txtAcknowledgementReceipt.setText("AR");
											txtPagIBIGReceipt.setText("PIR");
											txtSalesInvoice.setText("");
											txtSalesInvoice.requestFocus();
										}
									}
								});
							}
						}
						{
							JPanel pnlReceiptText = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlReceipts.add(pnlReceiptText, BorderLayout.CENTER);
							{
								txtOfficialReceipt = new _JXTextField("OR");
								pnlReceiptText.add(txtOfficialReceipt);
								txtOfficialReceipt.setHorizontalAlignment(_JXTextField.CENTER);
							}
							{
								txtProvisionalReceipt = new _JXTextField("PR");
								pnlReceiptText.add(txtProvisionalReceipt);
								txtProvisionalReceipt.setHorizontalAlignment(_JXTextField.CENTER);
							}
							{
								txtAcknowledgementReceipt = new _JXTextField("AR");
								pnlReceiptText.add(txtAcknowledgementReceipt);
								txtAcknowledgementReceipt.setHorizontalAlignment(_JXTextField.CENTER);
							}
							{
								txtPagIBIGReceipt = new _JXTextField("PIR");
								pnlReceiptText.add(txtPagIBIGReceipt);
								txtPagIBIGReceipt.setHorizontalAlignment(_JXTextField.CENTER);
							}
							{ // ADDED BY MONIQUE FOR ISSUANCE OF SALES INVOICE DTD 2022-07-11; WITH DCRF#2121
								txtSalesInvoice = new _JXTextField("SI");
								pnlReceiptText.add(txtSalesInvoice);
								txtSalesInvoice.setHorizontalAlignment(_JXTextField.CENTER);
							}
						}
					}
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					JPanel pnlCenterTable = new JPanel(new BorderLayout());
					pnlCenter.add(pnlCenterTable, BorderLayout.CENTER);
					pnlCenterTable.setBorder(components.JTBorderFactory.createTitleBorder("Payment List"));
					{
						scrollPaymentList = new _JScrollPaneMain();
						pnlCenterTable.add(scrollPaymentList, BorderLayout.CENTER);
						scrollPaymentList.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblPaymentList.clearSelection();
								try {
									tblPaymentList.getCellEditor().stopCellEditing();
								} catch (NullPointerException e1) {
								}
							}
						});
						{
							modelPaymentList = new modelIssuanceOfReceipt_PaymentList();
							modelPaymentList.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if (e.getType() == TableModelEvent.INSERT) {
										((DefaultListModel) rowHeaderPaymentList.getModel())
												.addElement(modelPaymentList.getRowCount());
										scrollPaymentListTotal.setRowHeaderView(FncTables
												.getRowHeader_Footer(Integer.toString(modelPaymentList.getRowCount())));
									}
									if (e.getType() == TableModelEvent.DELETE) {
										if (modelPaymentList.getRowCount() == 0) {
											rowHeaderPaymentList.setModel(new DefaultListModel());
											scrollPaymentListTotal.setRowHeaderView(FncTables.getRowHeader_Footer(
													Integer.toString(modelPaymentList.getRowCount())));
											// adjustRowModel();
										}
									}

									try {
										BigDecimal totalAmount = _IssuanceOfReceipt.totalPayments(modelPaymentList,
												modelPaymentListTotal);
										txtTotalAmount.setValue(totalAmount);
									} catch (ArrayIndexOutOfBoundsException e1) {
									}
								}
							});

							tblPaymentList = new _JTableMain(modelPaymentList);// XXX Payment List
							scrollPaymentList.setViewportView(tblPaymentList);
							tblPaymentList.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if (tblPaymentList.rowAtPoint(e.getPoint()) == -1) {
										tblPaymentListTotal.clearSelection();
									} else {
										tblPaymentList.setCellSelectionEnabled(true);
									}
								}

								public void mouseReleased(MouseEvent e) {
									if (tblPaymentList.rowAtPoint(e.getPoint()) == -1) {
										tblPaymentListTotal.clearSelection();
									} else {
										tblPaymentList.setCellSelectionEnabled(true);
									}
								}

								public void mouseClicked(MouseEvent evt) {
									if ((evt.getClickCount() >= 2)) {
										displayParticulars();
									}
								}
							});

							tblPaymentList.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent arg0) {
									if (arg0.getKeyCode() == KeyEvent.VK_F2) {
										displayParticulars();
									}
								}
							});

							tblPaymentList.getColumnModel().getColumn(3).setCellRenderer(TextRenderer
									.getTextRenderer(SwingConstants.CENTER, "Double-click to select payment type."));
							;

							tblPaymentList.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent arg0) {
									if (arg0.getOldValue() != null) {
										tblPaymentList.packAll();
									}
								}
							});

							tblPaymentList.addMouseListener(this);
						}
						{
							rowHeaderPaymentList = tblPaymentList.getRowHeader();
							rowHeaderPaymentList.setModel(new DefaultListModel());
							scrollPaymentList.setRowHeaderView(rowHeaderPaymentList);
							scrollPaymentList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
					{
						scrollPaymentListTotal = new _JScrollPaneTotal(scrollPaymentList);
						pnlCenterTable.add(scrollPaymentListTotal, BorderLayout.SOUTH);
						{
							modelPaymentListTotal = new modelIssuanceOfReceipt_PaymentList();
							modelPaymentListTotal.addRow(new Object[] { null, "Total", 0.00, null, null, null });

							tblPaymentListTotal = new _JTableTotal(modelPaymentListTotal, tblPaymentList);
							scrollPaymentListTotal.setViewportView(tblPaymentListTotal);

							tblPaymentListTotal.setTotalLabel(1);
						}
						scrollPaymentListTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
								displayTableNavigation());
					}
				}
				{
					JPanel pnlNavigations = new JPanel(new GridLayout(7, 1, 5, 5));
					pnlCenter.add(pnlNavigations, BorderLayout.EAST);
					pnlNavigations.setPreferredSize(new Dimension(150, 0));

					{
						btnOrderedPayment = new JButton("Ordered Payment");
						pnlNavigations.add(btnOrderedPayment);
						btnOrderedPayment.setActionCommand("Ordered Payment");
						btnOrderedPayment.addActionListener(this);
					}
					{
						btnNewCashReturn = new JButton("New Cash Return");
						pnlNavigations.add(btnNewCashReturn);
						btnNewCashReturn.setActionCommand("New Cash Return");
						btnNewCashReturn.addActionListener(this);
					}
					{
						btnReturnToCSD = new JButton("Return to CSD");
						pnlNavigations.add(btnReturnToCSD);
						btnReturnToCSD.setActionCommand("Return to CSD");
						btnReturnToCSD.addActionListener(this);
					}
					{
						btnSave = new JButton("Issue Receipt");
						pnlNavigations.add(btnSave);
						btnSave.setActionCommand("Issue Receipt");
						btnSave.addActionListener(this);
					}
					{
						btnPrintReceipt = new JButton("Preview Receipt");
						pnlNavigations.add(btnPrintReceipt);
						btnPrintReceipt.setActionCommand("Preview Receipt");
						btnPrintReceipt.addActionListener(this);
					}
					{
						btnNewPayment = new JButton("Manual Issue");
						pnlNavigations.add(btnNewPayment);
						btnNewPayment.setActionCommand("Manual Issue");
						btnNewPayment.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigations.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.addActionListener(this);
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 50));

				Font fontTotal = new Font(FncLookAndFeel.font_name, Font.BOLD, 30);

				{
					JLabel lblTotal = new JLabel("TOTAL: ");
					pnlSouth.add(lblTotal, BorderLayout.WEST);
					lblTotal.setFont(fontTotal);
				}
				{
					txtTotalAmount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlSouth.add(txtTotalAmount, BorderLayout.CENTER);
					txtTotalAmount.setFont(fontTotal);
					txtTotalAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtTotalAmount.setEditable(false);
				}
			}
		}

		// setComponentsEditable(pnlPaymentDetails, false);
		setComponentsEnabled(pnlDates, false);
		setComponentsEnabled(pnlReceipts, false);

		clearPaymentDetails();
		setComponentsEnabled(pnlPaymentDetails, false);

		clearReceipt();
		setComponentsEnabled(pnlReceipts, false);

		FncTables.bindColumnTables(tblPaymentList, tblPaymentListTotal);
		tblPaymentList.hideColumns("Part ID", "Check Type ID", "Bank ID", "Branch ID", "Receipt ID");

		btnState(false, true, true, false, false, false, false);
	}// XXX End of initGUI

	// enable, disable, refresh, clear
	private void btnState(Boolean sNewPayment, Boolean sOrderedPayment, Boolean sNewCashReturn, Boolean sReturnToCSD,
			Boolean sSave, Boolean sPrintReceipt, Boolean sCancel) {
		btnNewPayment.setEnabled(sNewPayment);
		btnOrderedPayment.setEnabled(sOrderedPayment);
		btnNewCashReturn.setEnabled(sNewCashReturn);
		btnReturnToCSD.setEnabled(sReturnToCSD);
		btnSave.setEnabled(sSave);
		btnPrintReceipt.setEnabled(sPrintReceipt);
		btnCancel.setEnabled(sCancel);
	}

	private void clearPaymentDetails() {
		txtClientSequenceNo.setText("");
		lblTransactionType.setText("[ ]");
		lookupClient.setValue(null);
		txtClientName.setText("");
		lookupCompany.setValue(null);
		txtCompanyName.setText("");
		lookupProject.setValue(null);
		txtProjectName.setText("");
		txtUnitID.setText("");
		txtUnitDescription.setText("");
		txtModelID.setText("");
		txtModelName.setText("");
		txtSequence.setText("");
		txtSellingPrice.setValue(null);
		txtRemarks.setText("");
	}

	private void clearReceipt() {
		rbOfficialReceipt.setSelected(false);
		txtOfficialReceipt.setText("OR");
		rbProvisionalReceipt.setSelected(false);
		txtProvisionalReceipt.setText("PR");
		rbAcknowledgementReceipt.setSelected(false);
		txtAcknowledgementReceipt.setText("AR");
		rbPagIBIGReceipt.setSelected(false);
		txtPagIBIGReceipt.setText("PIR");
		rbSalesInvoice.setSelected(false);
		txtSalesInvoice.setText("SI");
	}

	/*
	 * private void displayHolding(Object[] data) { lookupClient.setValue((String)
	 * data[1]); txtClientName.setText((String) data[2]);
	 * lookupCompany.setValue((String) data[3]); txtCompanyName.setText((String)
	 * data[4]); txtUnitID.setText((String) data[5]);
	 * txtUnitDescription.setText((String) data[6]);
	 * //txtLotArea.setValue((BigDecimal) data[7]);
	 * 
	 * dateTransaction.setDate((Date) data[9]); dateActual.setDate((Date) data[10]);
	 * 
	 * //Display payments in table
	 * _IssueanceOfReceipt.displayHoldingPayments(modelPaymentList,
	 * txtClientSequenceNo.getText()); tblPaymentList.packAll();
	 * 
	 * btnState(true, false, true, false, true); }
	 */

	private void newPayment() {
		clearPaymentDetails();
		setComponentsEnabled(pnlPaymentDetails, true);
		clearReceipt();
		setComponentsEnabled(pnlReceipts, false);
		modelPaymentList.clear();
		modelPaymentList.setEditable(true);
		rowHeaderPaymentList.setModel(new DefaultListModel());

		lookupClient.setLookupSQL(_IssuanceOfReceipt.sqlClients());
		lookupClient.setEditable(true);
		lookupCompany.setEditable(true);
		lookupCompany.setEditable(false);
		lookupProject.setEditable(true);
		lookupProject.setEditable(false);
		txtSellingPrice.setEditable(true);
		txtSellingPrice.setEditable(false);

		txtClientSequenceNo.setText(_IssuanceOfReceipt.getClientSeqNo(false));
		lblTransactionType.setText(String.format("[ %s ]", "NEW PAYMENT"));
		lookupClient.requestFocus();

		setComponentsEnabled(pnlDates, true);
		setDates(Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
		tableNavigation(true);
		btnState(false, false, false, false, true, true, true);

		modelPaymentList.addRow(new Object[] { null, null, null, "Cash", null, null, null, null, null, null, null, null,
				null, null, null, null });
		btnPrintReceipt.setText("Preview Receipt");
		btnPrintReceipt.setActionCommand("Preview Receipt");
	}

	private void newCashReturn() {
		clearPaymentDetails();
		setComponentsEnabled(pnlPaymentDetails, true);
		clearReceipt();
		setComponentsEnabled(pnlReceipts, false);
		modelPaymentList.clear();
		modelPaymentList.setEditable(true);
		rowHeaderPaymentList.setModel(new DefaultListModel());

		lookupClient.setLookupSQL(FncGlobal.getEmployees());
		lookupClient.setEditable(true);
		lookupCompany.setEditable(true);
		lookupProject.setEditable(true);
		txtRemarks.setEditable(true);

		txtClientSequenceNo.setText(_IssuanceOfReceipt.getClientSeqNo(true));
		lblTransactionType.setText(String.format("[ %s ]", "CASH RETURN"));
		// txtTotalAmount.setEditable(true);

		setComponentsEnabled(pnlDates, true);
		setDates(Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
		tableNavigation(true);
		btnState(false, false, false, false, true, true, true);

		btnPrintReceipt.setText("Preview Receipt");
		btnPrintReceipt.setActionCommand("Preview Receipt");
	}

	private void setDates(Date transaction, Date actual) {
		dateTransaction.setDate(_IssuanceOfReceipt.getRealTransactionDate(transaction));
		dateTransaction.setEditable(true);
		dateTransaction.setEditable(false);
		dateTransaction.getCalendarButton().setEnabled(false);
		dateActual.setDate(actual);
		dateActual.setEditable(true);
		dateActual.setEditable(false);
		dateActual.getCalendarButton().setEnabled(false);
	}

	/**
	 * Added by Alvin Gonzales (2015-07-15)
	 * 
	 */
	private void tableNavigation(boolean enable) {
		btnAddRow.setEnabled(enable);
		btnRemoveRow.setEnabled(enable);
	}

	// display
	private JPanel displayTableNavigation() {
		btnAddRow = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Plus2-Math-icon.png")));
		btnAddRow.setActionCommand("Add Row");
		btnAddRow.setToolTipText("Add Row");
		btnAddRow.setEnabled(false);
		btnAddRow.addActionListener(this);

		btnRemoveRow = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRow.setActionCommand("Remove Row");
		btnRemoveRow.setToolTipText("Remove Row");
		btnRemoveRow.setEnabled(false);
		btnRemoveRow.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnAddRow);
		pnl.add(btnRemoveRow);

		return pnl;
	}

	private void displayNewPaymentDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		Object[] data = _IssuanceOfReceipt.getNewPaymentDetails(entity_id, proj_id, pbl_id, seq_no);

		lookupCompany.setValue((String) data[0]);
		txtCompanyName.setText((String) data[1]);
		lookupProject.setValue((String) data[2]);
		txtProjectName.setText((String) data[3]);
		txtUnitID.setText((String) data[4]);
		txtUnitDescription.setText((String) data[5]);
		txtModelID.setText((String) data[6]);
		txtModelName.setText((String) data[7]);
		txtSequence.setText(Integer.toString((Integer) data[8]));
		txtSellingPrice.setValue((BigDecimal) data[9]);
	}

	private void displayParticulars() {// XXX
		String selectedTransaction = lblTransactionType.getText().replaceAll("\\[|\\]", "").trim();
		String entity_id = lookupClient.getValue();
		String proj_id = lookupProject.getValue();
		String pbl_id = txtUnitID.getText();
		String seq_no = txtSequence.getText();

		int column = tblPaymentList.convertColumnIndexToModel(tblPaymentList.getSelectedColumn());
		int row = tblPaymentList.getSelectedRow();

		if (column == 1 && modelPaymentList.isEditable()) {
			String SQL = "";
			if (selectedTransaction.equals("CASH RETURN")) {
				SQL = "SELECT TRIM(pay_part_id)::VARCHAR as \"ID\", TRIM(partdesc) as \"Name\", TRIM(particulars) as \"Alias\" "
						+ "FROM mf_pay_particular WHERE (group_id = '06' OR pay_part_id IN ('179', '204', '214', '212', '197', '277', '278', '279', '280', '174', '283')) AND status_id = 'A' ORDER BY partdesc;"; //ADDED PART ID 174; MONIQUE 01-08-24
			
			} else {
				if (entity_id == null) {
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client first.",
							"Particular", JOptionPane.WARNING_MESSAGE);
					return;
				}

				SQL = _OrderOfPayment.getParticulars(entity_id, proj_id, pbl_id, seq_no, false, "");
			}

			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Particulars", SQL, false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelPaymentList.setValueAt(data[0], row, 0);
				modelPaymentList.setValueAt(data[1], row, column);
				tblPaymentList.packAll();
			}
		}

		if (column == 3 && modelPaymentList.isEditable()) {
			Object[] option = new Object[] { "Cash", "Check" };
			int payment_type = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select payment type.",
					"Payment Type", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, 0);
			modelPaymentList.setValueAt(option[payment_type], row, column);
			tblPaymentList.packAll();
		}

		if (column == 6 && modelPaymentList.isEditable()) { // lookup for Check Type
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Check", _OrderOfPayment.getCheckType(),
					false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				// modelPaymentList.setValueAt("Check", row, 3);
				modelPaymentList.setValueAt(data[0], row, 5);
				modelPaymentList.setValueAt(data[1], row, column);
				tblPaymentList.packAll();
			}
		}

		if (column == 10 && modelPaymentList.isEditable()) { // lookup for Bank
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Bank", _OrderOfPayment.getBank(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				// modelPaymentList.setValueAt("Check", row, 3);
				modelPaymentList.setValueAt(data[0], row, 9);
				modelPaymentList.setValueAt(data[1], row, column);
				tblPaymentList.packAll();
			}
		}

		if (column == 12 && modelPaymentList.isEditable()) { // lookup for Bank Branch
			String bank_id = (String) modelPaymentList.getValueAt(row, 9);
			if (bank_id == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select bank first.", "Branch",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Branch",
					_OrderOfPayment.getBankBranch(bank_id), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				// modelPaymentList.setValueAt("Check", row, 3);
				modelPaymentList.setValueAt(data[0], row, 11);
				modelPaymentList.setValueAt(data[1], row, column);
				tblPaymentList.packAll();
			}
		}
	}

	private void displayReservation(Object[] data) {
		clearPaymentDetails();
		setComponentsEnabled(pnlPaymentDetails, data != null);
		setComponentsEnabled(pnlDates, data != null);
		clearReceipt();
		setComponentsEnabled(pnlReceipts, false);
		modelPaymentList.clear();
		rowHeaderPaymentList.setModel(new DefaultListModel());
		txtTotalAmount.setEditable(false);

		if (data != null) {
			txtClientSequenceNo.setText((String) data[0]);
			lookupClient.setValue((String) data[1]);
			txtClientName.setText((String) data[2]);
			lookupCompany.setValue((String) data[3]);
			txtCompanyName.setText((String) data[4]);
			txtUnitID.setText((String) data[5]);
			txtUnitDescription.setText((String) data[6]);
			txtSellingPrice.setValue((BigDecimal) data[8]);

			setDates((Date) data[9], (Date) data[10]);

			lookupProject.setValue((String) data[13]);
			txtProjectName.setText((String) data[14]);
			txtModelID.setText((String) data[11]);
			txtModelName.setText((String) data[12]);
			lblTransactionType.setText(String.format("[ %s ]", (String) data[16]));
			if (data[15] != null) {
				txtSequence.setText(Integer.toString((Integer) data[15]));
			}

			if (((String) data[16]).equals("HOLDING")) {
				// Display payments in table
				_IssuanceOfReceipt.displayHoldingPayments(modelPaymentList, (String) data[0]);
				tblPaymentList.packAll();
			} else if (((String) data[16]).equals("RESERVATION")) {
				// Display payments in table
				_IssuanceOfReceipt.displayReservationPayments(modelPaymentList, (String) data[0]);
				tblPaymentList.packAll();
			} else {
				// Display payments in table
				_IssuanceOfReceipt.displayReservationPayments(modelPaymentList, (String) data[0]);
				tblPaymentList.packAll();
			}
			btnState(true, false, false, (!((String) data[16]).equals("HOLDING")), true, true, true);

			/*
			 * BigDecimal percent = null; for(int x=0; x < modelPaymentList.getRowCount();
			 * x++){ String part_id = (String) modelPaymentList.getValueAt(x, 0); percent =
			 * _IssuanceOfReceipt.totalPercentage(lookupClient.getValue(),
			 * txtProjectID.getText(), txtUnitID.getText(),
			 * Integer.parseInt(txtSequence.getText()), part_id, (BigDecimal)
			 * txtTotalAmount.getValued()); } //System.out.printf("Percent: %s%n", percent);
			 * 
			 * String doc_id = "03"; if(percent.compareTo(new BigDecimal(20.00)) >= 0) {
			 * doc_id = "01"; }
			 * 
			 * if(_HoldingAndReservation.getLatestAR(doc_id) > 0){ btnState(false, false,
			 * false, true, false, true); }else{ btnState(false, false, false, false, false,
			 * true);
			 * 
			 * if(doc_id.equals("01")){
			 * JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
			 * "No official receipt to issue.\n\nPlease go to Admin>Add Receipt Number and tag new official receipt to issue."
			 * , "New Payment", JOptionPane.WARNING_MESSAGE); } if(doc_id.equals("03")){
			 * JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
			 * "No acknowledgement receipt to issue.\n\nPlease go to Admin>Add Receipt Number and tag new acknowledgement receipt to issue."
			 * , "New Payment", JOptionPane.WARNING_MESSAGE); } }
			 */
		} else {
			btnState(true, true, true, false, false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "No available payments.", "New Payment",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void displayReceiptDetails() {
		Object[] dataReceipt = _IssuanceOfReceipt.getReceiptDetails(txtClientSequenceNo.getText(),
				lblTransactionType.getText().replaceAll("\\[|\\]", "").trim(), lookupClient.getValue(),
				lookupProject.getValue(), txtUnitID.getText(),
				txtSequence.getText().equals("") ? null : Integer.parseInt(txtSequence.getText()));
		String receipt_type = (String) dataReceipt[0];
		String receipt_no = (String) dataReceipt[1];

		if (receipt_type.equals("ORV")) {
			rbOfficialReceipt.setEnabled(true);
			rbOfficialReceipt.setSelected(true);
			txtOfficialReceipt.setEnabled(true);
			txtOfficialReceipt.setText(receipt_no);
		}
		if (receipt_type.equals("AR")) {
			rbAcknowledgementReceipt.setEnabled(true);
			rbAcknowledgementReceipt.setSelected(true);
			txtAcknowledgementReceipt.setEnabled(true);
			txtAcknowledgementReceipt.setText(receipt_no);
		}
		if (receipt_type.equals("PFR")) {
			rbPagIBIGReceipt.setEnabled(true);
			rbPagIBIGReceipt.setSelected(true);
			txtPagIBIGReceipt.setEnabled(true);
			txtPagIBIGReceipt.setText(receipt_no);
		}
		if (receipt_type.equals("SI")) { // ADDED BY MONIQUE FOR ISSUANCE OF SALES INVOICE DTD 2022-07-11; WITH
											// DCRF#2121
			rbSalesInvoice.setEnabled(true);
			rbSalesInvoice.setSelected(true);
			txtSalesInvoice.setEnabled(true);
			txtSalesInvoice.setText(receipt_no);
		}
	}

	private Boolean reverseHoldingReceipt_CRB() {

		Boolean reversed = false;

		int rw = tblPaymentList.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String credited_ar_no = "";
			try {
				credited_ar_no = modelPaymentList.getValueAt(x, 13).toString();
			} catch (NullPointerException e) {
				credited_ar_no = "";
			}

			Double amount = Double.parseDouble(modelPaymentList.getValueAt(x, 2).toString());

			if (credited_ar_no.equals("") || credited_ar_no == null) {

			} else {
				String new_receipt_no = txtAcknowledgementReceipt.getText();
				String pbl_id = sql_getPBL_ID(txtUnitID.getText(), lookupProject.getText());

				/*
				 * Modified by Mann2x; Date Modified: February 20, 2017; A problems occurs
				 * within the reversal procedure beecause of payments with similar receipts;
				 * String sqlDetail = "SELECT sp_journalize_ar_holding_reversal('"+
				 * new_receipt_no +"', '"+ credited_ar_no +"', " + "'"+ lookupProject.getText()
				 * +"', '"+ pbl_id +"', "+ Integer.parseInt(txtSequence.getText()) +", "+ amount
				 * +");";
				 */
				String strEntityID = lookupClient.getValue();
				String sqlDetail = "SELECT sp_journalize_ar_holding_reversal_v2 (" + "'" + new_receipt_no + "', \n"
						+ "'" + credited_ar_no + "', \n" + "'" + lookupProject.getText() + "', \n" + "'" + pbl_id
						+ "', \n" + Integer.parseInt(txtSequence.getText()) + ", \n" + amount + ", \n" + "'"
						+ strEntityID + "')";

				FncSystem.out("Issuance of Receipt", sqlDetail);
				pgSelect db = new pgSelect();
				db.select(sqlDetail, "Save", true);
				if (db.isNotNull()) {
					reversed = (Boolean) db.getResult()[0][0];
				} else {
					reversed = true;
				}
			}

			x++;
		}

		return reversed;
	}

	// Action Performed
	@Override
	public void actionPerformed(ActionEvent e) {// XXX actionCommand

		String action = e.getActionCommand();
		String selectedTransaction = lblTransactionType.getText().replaceAll("\\[|\\]", "").trim();

		if (action.equals("New Payment")) {
			newPayment();
		}

		if (action.equals("Ordered Payment")) {
			orderedPayment();
		}

		if (action.equals("New Cash Return")) {
			newCashReturn();
		}

		if (action.equals("Return to CSD")) {
			returnToCSD(action);
		}

		if (action.equals("Issue Receipt")) {
			save(selectedTransaction);
		}

		if (action.equals("Print Receipt")) {
			if (isLedgerApplied(clientSeqNo) && isLedgerAppliedCorrect(clientSeqNo)) {
				System.out.println("Dumaan dito sa Print Receipt");
				print();
			} else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
						"Payment application not in order. Contact JST.", "Preview", JOptionPane.WARNING_MESSAGE);
			}
		}

		if (action.equals("Preview Receipt")) {
			if (isLedgerApplied(clientSeqNo) && isLedgerAppliedCorrect(clientSeqNo)) {
				System.out.println("Dumaan dito sa Preview");
				preview(selectedTransaction);
			} else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
						"Payment application not in order. Contact JST.", "Preview", JOptionPane.WARNING_MESSAGE);
			}

		}

		if (action.equals("Cancel")) {
			cancel();
		}

		if (action.equals("Add Row")) {
			addRow();
		}

		if (action.equals("Remove Row")) {
			removeRow();
		}

		if (action.equals("Manual Issue")) {
			executeManualIssue();
		}

		if (action.equals("Save")) {
			executeSave();
		}

	}

	private JPopupMenu initializeMenu() {// XXX Menu
		JPopupMenu menu = new JPopupMenu();

		JMenuItem menuClear = new JMenuItem("Clear");
		menu.add(menuClear);
		menuClear.setFont(menuClear.getFont().deriveFont(10f));
		menuClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tblPaymentList.convertRowIndexToModel(tblPaymentList.getSelectedRow());
				int column = tblPaymentList.convertColumnIndexToModel(tblPaymentList.getSelectedColumn());
				modelPaymentList.setValueAt(null, row, column);
			}
		});

		return menu;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int column = tblPaymentList.convertColumnIndexToModel(tblPaymentList.getSelectedColumn());

		if (arg0.isPopupTrigger() && (column == 1 || column == 6 || column == 10 || column == 12)) {
			initializeMenu().show((_JTableMain) arg0.getSource(), arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		int column = tblPaymentList.convertColumnIndexToModel(tblPaymentList.getSelectedColumn());

		if (arg0.isPopupTrigger() && (column == 1 || column == 6 || column == 10 || column == 12)) {
			initializeMenu().show((JPanel) arg0.getSource(), arg0.getX(), arg0.getY());
		}
	}

	// Action Performed
	private void preview(String transaction) {

		String payPartid = "";
		try {
			payPartid = modelPaymentList.getValueAt(0, 0).toString().trim().replace("'", "''");
		} catch (NullPointerException e) {
			payPartid = "";
		}
		String projId = lookupProject.getText();
		String unit_id = txtUnitID.getText();
		String sub_proj_id = sql_getSubProj(projId, unit_id);
		Boolean hasBOI = sql_getBOI(sub_proj_id);
		Boolean hasLTS = sql_getLTS(sub_proj_id);
		Date checkDate = null;
		try {
			checkDate = (Date) modelPaymentList.getValueAt(0, 7);
		} catch (NullPointerException e) {
			checkDate = null;
		}

		if (transaction.equals("HOLDING") || transaction.equals("RESERVATION")) {
			Object[] recpt_dtl = null;
			if (transaction.equals("HOLDING")) {
				recpt_dtl = _IssuanceOfReceipt.getHoldingDetails(clientSeqNo);
			} else if (transaction.equals("RESERVATION")) {
				recpt_dtl = _IssuanceOfReceipt.getNewPaymentDetails(clientSeqNo);
			}

			String entity_id = (String) recpt_dtl[1];
			String client_name = (String) recpt_dtl[2];
			String unit_desc = (String) recpt_dtl[6];
			String proj_id = (String) recpt_dtl[13];

			Object[] client_dtl = sql_ClientAddress_TIN(entity_id);
			String entity_address = (String) client_dtl[0];
			String entity_tin = (String) client_dtl[1];
			BigDecimal amount_bd = new BigDecimal(txtTotalAmount.getText().replace(",", ""));

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("client_name", client_name);
			mapParameters.put("proj_alias", sql_ProjAlias(proj_id));
			mapParameters.put("unit_desc", unit_desc);
			mapParameters.put("client_TIN", entity_tin);
			mapParameters.put("client_address", entity_address);
			mapParameters.put("trans_date", dateTransaction.getDate());
			mapParameters.put("amount", amount_bd);
			mapParameters.put("check_no", null);
			mapParameters.put("check_date", null);
			mapParameters.put("acct_no", null);
			mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());

			List<receiptDtl_class> list = new ArrayList<receiptDtl_class>();
			for (int i = 0; i < modelPaymentList.getRowCount(); i++) {
				String pymnt_part = (String) modelPaymentList.getValueAt(i, 1);
				Double amount = Double.parseDouble(modelPaymentList.getValueAt(i, 2).toString());
				list.add(new receiptDtl_class(pymnt_part, amount));
			}
			mapParameters.put("list", list);

			FncReport.generateReport("/Reports/rptARReceipt_preview.jasper", "Acknowledgement Receipt",
					String.format("AR No.: %s", ""), mapParameters);
		}

		else if (transaction.equals("CASH RETURN")) {
			Object[] client_dtl = sql_ClientAddress_TIN(entityID);
			String entity_address = (String) client_dtl[0];
			String entity_tin = (String) client_dtl[1];
			BigDecimal amount_bd = new BigDecimal(txtTotalAmount.getText().replace(",", ""));

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("client_name", clientName);
			mapParameters.put("proj_alias", sql_ProjAlias(projID));
			mapParameters.put("unit_desc", "");
			mapParameters.put("client_TIN", entity_tin);
			mapParameters.put("client_address", entity_address);
			mapParameters.put("trans_date", dateTransaction.getDate());
			mapParameters.put("amount", amount_bd); //
			mapParameters.put("check_no", null);
			mapParameters.put("check_date", null);
			mapParameters.put("acct_no", null);
			mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());

			List<receiptDtl_class> list = new ArrayList<receiptDtl_class>();
			for (int i = 0; i < modelPaymentList.getRowCount(); i++) {
				String pymnt_part = (String) modelPaymentList.getValueAt(i, 1);
				Double amount = Double.parseDouble(modelPaymentList.getValueAt(i, 2).toString());
				list.add(new receiptDtl_class(pymnt_part, amount));
			}
			mapParameters.put("list", list);

			FncReport.generateReport("/Reports/rptARReceipt_preview.jasper", "Acknowledgement Receipt",
					String.format("AR No.: %s", ""), mapParameters);
		} else if (sql_getApplicableReceipt(payPartid).equals("03") || hasBOI == false || hasLTS == false
				|| hasPDC() == true) {
			Object[] client_dtl = sql_ClientAddress_TIN(lookupClient.getText());
			String entity_address = (String) client_dtl[0];
			String entity_tin = (String) client_dtl[1];
			BigDecimal amount_bd = new BigDecimal(txtTotalAmount.getText().replace(",", ""));

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("client_name", txtClientName.getText());
			mapParameters.put("proj_alias", sql_ProjAlias(projID));
			mapParameters.put("unit_desc", txtUnitDescription.getText());
			mapParameters.put("client_TIN", entity_tin);
			mapParameters.put("client_address", entity_address);
			mapParameters.put("trans_date", dateTransaction.getDate());
			mapParameters.put("amount", amount_bd); //
			mapParameters.put("check_no", null);
			mapParameters.put("check_date", null);
			mapParameters.put("acct_no", null);
			mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());

			List<receiptDtl_class> list = new ArrayList<receiptDtl_class>();
			for (int i = 0; i < modelPaymentList.getRowCount(); i++) {
				String pymnt_part = (String) modelPaymentList.getValueAt(i, 1);
				Double amount = Double.parseDouble(modelPaymentList.getValueAt(i, 2).toString());
				list.add(new receiptDtl_class(pymnt_part, amount));
			}
			mapParameters.put("list", list);
			if (lookupCompany.getValue().equals("01")) {
				FncReport.generateReport("/Reports/rptARReceipt_preview.jasper", "Acknowledgement Receipt",
						String.format("AR No.: %s", ""), mapParameters);
			} else if (lookupCompany.getValue().equals("02")) {
				FncReport.generateReport("/Reports/rptARReceipt_preview.jasper", "Acknowledgement Receipt",
						String.format("AR No.: %s", ""), mapParameters);
			} else if (lookupCompany.getValue().equals("04")) {
				FncReport.generateReport("/Reports/rptARReceipt_preview.jasper", "Acknowledgement Receipt",
						String.format("AR No.: %s", ""), mapParameters);
			} else if (lookupCompany.getValue().equals("05")) {
				FncReport.generateReport("/Reports/rptARReceipt_preview.jasper", "Acknowledgement Receipt",
						String.format("AR No.: %s", ""), mapParameters);
			}
			// FncReport.generateReport("/Reports/rptARReceipt_preview.jasper",
			// "Acknowledgement Receipt", String.format("AR No.: %s", ""), mapParameters);

		}

		else {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("client_seqno", clientSeqNo);
			mapParameters.put("or_no", "");
			mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());

			if (UserInfo.Branch.equals("10")) {
				// FncReport.generateReport("/Reports/rptORReceipt_v2.jasper", "Official
				// Receipt", String.format("OR No.: %s", ""), mapParameters);
				FncReport.generateReport("/Reports/rptORReceipt.jasper", "Official Receipt",
						String.format("OR No.: %s", ""), mapParameters);
			} else if(lookupCompany.getValue().equals("05")) { // ADDED BY MONIQUE DTD 2023-07-14; FOR OR OF EXO
					FncReport.generateReport("/Reports/rptORReceipt_EDC.jasper", "Official Receipt",
							String.format("OR No.: %s", ""), mapParameters);	
			} else {
				FncReport.generateReport("/Reports/rptORReceipt.jasper", "Official Receipt",
						String.format("OR No.: %s", ""), mapParameters);
			}

			// FncReport.generateReport("/Reports/rptORReceipt.jasper", "Official Receipt",
			// String.format("OR No.: %s", ""), mapParameters);
		}
	}

	private void orderedPayment() {
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Ordered Payment",
				_IssuanceOfReceipt.sqlNewPayment(), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[] data = dlg.getReturnDataSet();
		if (data != null) {
			clientSeqNo = (String) data[1];
			tran_type = (String) data[3];

			if (tran_type.equals("HOLDING")) {
				displayReservation(_IssuanceOfReceipt.getHoldingDetails(clientSeqNo));
			} else {
				displayReservation(_IssuanceOfReceipt.getNewPaymentDetails(clientSeqNo));
			}
		} else {
		}

		btnPrintReceipt.setText("Preview Receipt");
		btnPrintReceipt.setActionCommand("Preview Receipt");
	}

	private void returnToCSD(String action) {
		if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),
				"Are you sure you want to return transaction to CSD?", "Return to CSD", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			_IssuanceOfReceipt.returnToCSD(txtClientSequenceNo.getText());

			JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
					"Transaction has been return to CSD successfully.", action, JOptionPane.INFORMATION_MESSAGE);
			btnCancel.doClick();
		}
	}

	private void print() {

		String creditedARNO = "";
		Double creditedamount = 0.00;
		try {
			creditedARNO = tblPaymentList.getValueAt(0, 9).toString();
		} catch (NullPointerException e) {
			creditedARNO = "";
		}
		try {
			creditedamount = Double.parseDouble(tblPaymentList.getValueAt(0, 1).toString());
		} catch (NullPointerException e) {
			creditedamount = 0.00;
		}

		System.out.printf("creditedARNO: " + creditedARNO);
		System.out.printf("rbAcknowledgementReceipt.isSelected(): " + rbAcknowledgementReceipt.isSelected());
		System.out.printf("rbOfficialReceipt.isSelected(): " + rbOfficialReceipt.isSelected());
		System.out.printf("rbPagIBIGReceipt.isSelected(): " + rbPagIBIGReceipt.isSelected());
		System.out.printf("rbSalesInvoice.isSelected(): " + rbSalesInvoice.isSelected());

		if (rbAcknowledgementReceipt.isSelected()) {
			_IssuanceOfReceipt.printAR(txtClientSequenceNo.getText(), txtAcknowledgementReceipt.getText(), creditedARNO,
					"AR No. ", creditedamount, lookupCompany.getValue());
		} else if (rbOfficialReceipt.isSelected()) {
			_IssuanceOfReceipt.printOR(txtClientSequenceNo.getText(), txtOfficialReceipt.getText(), creditedARNO,
					"AR No. ", creditedamount);
		} else if (rbPagIBIGReceipt.isSelected()) {
			_IssuanceOfReceipt.printPR(txtClientSequenceNo.getText(), txtPagIBIGReceipt.getText());
		} else if (rbSalesInvoice.isSelected()) { // ADDED BY MONIQUE FOR ISSUANCE OF SALES INVOICE DTD 2022-07-11; WITH
													// DCRF#2121
			_IssuanceOfReceipt.printSIV(txtClientSequenceNo.getText(), txtSalesInvoice.getText(), creditedARNO,
					"SI No. ", creditedamount, lookupCompany.getValue());
		}
	}

	private void cancel() {
		clearPaymentDetails();
		setComponentsEnabled(pnlPaymentDetails, false);
		setComponentsEnabled(pnlDates, false);
		clearReceipt();
		setComponentsEnabled(pnlReceipts, false);
		txtTotalAmount.setEditable(false);
		txtTotalAmount.setValue(null);
		modelPaymentList.clear();
		modelPaymentList.setEditable(false);
		tableNavigation(false);
		btnState(false, true, true, false, false, false, false);

		btnPrintReceipt.setText("Preview Receipt");
		btnPrintReceipt.setActionCommand("Preview Receipt");
		btnSave.setActionCommand("Issue Receipt");
		btnSave.setText("Issue Receipt");
		btnSave.setEnabled(false);

		clientSeqNo = "";
		tran_type = "";
		clientName = "";
		projID = "";
		entityID = "";

		enableReceiptFields(false);
	}

	private void addRow() {
		modelPaymentList.addRow(new Object[] { null, null, null, "Cash", null, null, null, null, null, null, null, null,
				null, null, null, null });
	}

	private void removeRow() {
		int[] selectedRows = tblPaymentList.getSelectedRows();

		if (selectedRows.length == 0) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row in Payment List.",
					"Remove Row", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		for (int x = selectedRows.length - 1; x > -1; x--) {
			int row = selectedRows[x];

			modelPaymentList.removeRow(row);
		}

		adjustRowModel();
	}

	private void save(String selectedTransaction) {
		System.out.printf("Display Selected Transaction: %s%n", selectedTransaction);

		if (checkTable() == true) {
		}

		else {
			if (toSave()) {
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					if (selectedTransaction.equals("HOLDING")) {
						if (_IssuanceOfReceipt.saveHolding(txtClientSequenceNo.getText(), lookupCompany.getValue(),
								txtUnitID.getText())) {
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
									"Holding payment has been saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(false, false, false, false, false, true, true);
						}
					} else if (selectedTransaction.equals("RESERVATION")) {
						if (_IssuanceOfReceipt.saveReservation(lookupCompany.getValue(), txtClientSequenceNo.getText(),
								"106", (BigDecimal) txtTotalAmount.getValued())) {
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
									"Reservation payment has been saved and issued.", "Save",
									JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();

							/*
							 * Modified by Mann2x; Date Modified: August 7, 2017; DCRF# 209; if
							 * (reverseHoldingReceipt_CRB()) {
							 * JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							 * "Holding payment entries successfully reversed.", "Entry Reversal",
							 * JOptionPane.PLAIN_MESSAGE); }
							 */
							btnState(false, false, false, false, false, true, true);
						}
					} else if (selectedTransaction.equals("CASH RETURN")
							&& txtClientSequenceNo.getText().substring(0, 2).equals("GA")) {
						if (_IssuanceOfReceipt.saveCashReturn(modelPaymentList, lookupClient.getValue(),
								(BigDecimal) txtTotalAmount.getValued(), lookupCompany.getValue(), txtRemarks.getText().trim().replace("'", "''"))) {
							lookupClient.setEnabled(true);
							lookupClient.setEditable(true);
							lookupClient.setEditable(false);
							lookupCompany.setEnabled(true);
							lookupCompany.setEditable(true);
							lookupCompany.setEditable(false);
							lookupProject.setEnabled(true);
							lookupProject.setEditable(true);
							lookupProject.setEditable(false);
							txtTotalAmount.setEditable(false);
							txtRemarks.setEditable(false);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
									"Cash Return payment has been saved and issued.", "Save",
									JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(false, false, false, false, false, true, true);
						}
					} else if (selectedTransaction.equals("NEW PAYMENT")) {
						if (_IssuanceOfReceipt.saveNewPayment(modelPaymentList, lookupClient.getValue(),
								lookupProject.getText(), txtUnitID.getText(), Integer.parseInt(txtSequence.getText()),
								(BigDecimal) txtTotalAmount.getValued(), lookupCompany.getValue())) {
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
									"New payment has been saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(false, false, false, false, false, true, true);
						}
					} else {
						System.out.println("Display Last Else Statement Issue Receipt");
						if (_IssuanceOfReceipt.saveOrderedPayment(modelPaymentList, txtClientSequenceNo.getText(),
								(BigDecimal) txtTotalAmount.getValued(), lookupCompany.getValue())) {
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
									"New payment has been saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(false, false, false, false, false, true, true);
						}
					}

					btnPrintReceipt.setText("Print Receipt");
					btnPrintReceipt.setActionCommand("Print Receipt");
				}
			}
		}
	}

	private void executeManualIssue() {
		enableReceiptFields(true);
		btnSave.setEnabled(false);
		btnPrintReceipt.setEnabled(false);
		btnReturnToCSD.setEnabled(false);
		btnSave.setText("Save");
		btnSave.setActionCommand("Save");
		btnSave.setEnabled(true);
		btnNewPayment.setEnabled(false);
	}

	private void enableReceiptFields(Boolean x) {
		rbOfficialReceipt.setEnabled(x);
		rbProvisionalReceipt.setEnabled(false);
		rbAcknowledgementReceipt.setEnabled(x);
		rbPagIBIGReceipt.setEnabled(x);
		rbSalesInvoice.setEnabled(x);
	}

	private void executeSave() {

		if (checkReceiptNo() == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Enter Receipt No.", "Error", JOptionPane.ERROR_MESSAGE);
			if (rbOfficialReceipt.isSelected() == true) {
				txtOfficialReceipt.requestFocus();
			} else if (rbProvisionalReceipt.isSelected() == true) {
				txtProvisionalReceipt.requestFocus();
			} else if (rbAcknowledgementReceipt.isSelected() == true) {
				txtAcknowledgementReceipt.requestFocus();
			} else if (rbPagIBIGReceipt.isSelected() == true) {
				txtPagIBIGReceipt.requestFocus();
			} else if (rbSalesInvoice.isSelected() == true) {
				txtSalesInvoice.requestFocus();
			}
		} else {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to issue receipt manually?",
					"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				if (_IssuanceOfReceipt.saveManualPayment(modelPaymentList, txtClientSequenceNo.getText(),
						(BigDecimal) txtTotalAmount.getValued(), lookupCompany.getValue(),
						getReceiptTypeId_No()[0].toString(), getReceiptTypeId_No()[1].toString())) {
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"New payment has been saved and manually issued.", "Save", JOptionPane.PLAIN_MESSAGE);
					displayReceiptDetails();
					btnState(false, false, false, false, false, true, true);
					btnPrintReceipt.setText("Print Receipt");
					btnPrintReceipt.setActionCommand("Print Receipt");
				}
			}
		}

	}

	private Object[] getReceiptTypeId_No() {

		String receipt_TypeID = "";
		String receiptNo = "";
		// Object receipt_No[] = null;

		if (rbOfficialReceipt.isSelected() == true) {
			receipt_TypeID = "01";
			receiptNo = txtOfficialReceipt.getText();
		} else if (rbProvisionalReceipt.isSelected() == true) {
			receipt_TypeID = "03";
			receiptNo = txtProvisionalReceipt.getText();
		} else if (rbAcknowledgementReceipt.isSelected() == true) {
			receipt_TypeID = "03";
			receiptNo = txtAcknowledgementReceipt.getText();
		} else if (rbPagIBIGReceipt.isSelected() == true) {
			receipt_TypeID = "08";
			receiptNo = txtPagIBIGReceipt.getText();
		} else if (rbSalesInvoice.isSelected() == true) // ADDED BY MONIQUE FOR ISSUANCE OF SALES INVOICE DTD
														// 2022-07-11; WITH DCRF#2121
		{
			receipt_TypeID = "307";
			receiptNo = txtSalesInvoice.getText();
		}

		/*
		 * //Object[] receipt_No2 = receipt_No; receipt_No[0] = receipt_TypeID;
		 * receipt_No[1] = receiptNo;
		 */

		return new Object[] { receipt_TypeID, receiptNo };
	}

	// SQL
	private String sql_ProjAlias(String proj_id) {

		String proj_alias = "";

		String SQL = "select proj_alias from mf_project where proj_id = '" + proj_id + "' ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				proj_alias = "";
			} else {
				proj_alias = (String) db.getResult()[0][0];
			}
		} else {
			proj_alias = "";
		}

		return proj_alias;
	}

	private Object[] sql_ClientAddress_TIN(String entity_id) {

		String SQL = "select get_client_address( '" + entity_id + "' ), get_client_tin('" + entity_id + "')  ";

		System.out.printf("sql_ClientAddress_TIN: %s%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private String sql_getApplicableReceipt(String payPartId) {

		String a = "";

		String SQL = "select receipt_to_issue from mf_pay_particular where pay_part_id = '" + payPartId + "'\r\n";

		System.out.printf("SQL #1: sql_getApplicableReceipt", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}

	private String sql_getSubProj(String proj, String unitID) {

		String a = "";

		String SQL = "SELECT sub_proj_id from mf_unit_info where proj_id = '" + proj + "' and unit_id = '" + unitID
				+ "' ";

		System.out.printf("SQL #1: sql_getSubProj: " + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}

	private Boolean sql_getBOI(String subProj) {

		boolean a = false;

		String SQL = "SELECT case when boi is null then false else true end FROM mf_sub_project WHERE sub_proj_id = '"
				+ subProj + "' and status_id = 'A'"; //ADDED STATUS ID BY LESTER DCRF 2718

		System.out.printf("SQL #1: sql_getBOI; " + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((Boolean) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = false;
			} else {
				a = (Boolean) db.getResult()[0][0];
			}

		} else {
			a = false;
		}

		return a;
	}

	private Boolean sql_getLTS(String subProj) {

		boolean a = false;

		String SQL = "SELECT case when lts_date is null then false else true end FROM mf_sub_project WHERE sub_proj_id = '"
				+ subProj + "' AND status_id = 'A'"; //ADDED STATUS ID BY LESTER DCRF 2718

		System.out.printf("SQL #1: sql_getLTS; " + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((Boolean) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = false;
			} else {
				a = (Boolean) db.getResult()[0][0];
			}

		} else {
			a = false;
		}

		return a;
	}

	private String sql_getPBL_ID(String unit_id, String proj_id) {

		String pbl_id = "";

		String SQL = "select distinct on (pbl_id) pbl_id " + "from mf_unit_info " + "where trim(unit_id) = '" + unit_id
				+ "' \r\n" + "and proj_id = '" + proj_id + "' ";

		System.out.printf("SQL - sql_getPBL_ID : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0].toString() == null || db.getResult()[0][0].equals("null")) {
				pbl_id = "";
			} else {
				pbl_id = db.getResult()[0][0].toString();
			}
		} else {
			pbl_id = "";
		}

		return pbl_id;
	}

	// checking
	private Boolean checkTable() {

		Boolean withNull = false;
		int count = tblPaymentList.getRowCount();
		String particular = "";
		Double amount = 0.00;

		for (int x = 0; x < count; x++) {

			try {
				particular = modelPaymentList.getValueAt(x, 1).toString();
			} catch (NullPointerException e) {
				particular = "";
			}
			try {
				amount = Double.parseDouble(modelPaymentList.getValueAt(x, 2).toString());
			} catch (NullPointerException e) {
				amount = 0.00;
			}

			if (particular.equals("") && amount != 0.00) {
				withNull = true;
				JOptionPane.showMessageDialog(getContentPane(), "Please enter the missing payment particular.",
						"Missing Payment Particular", JOptionPane.WARNING_MESSAGE);
				break;
			} else if (!particular.equals("") && amount == 0.00) {
				withNull = true;
				JOptionPane.showMessageDialog(getContentPane(), "Please enter the missing payment amount.",
						"Missing Payment Amount", JOptionPane.WARNING_MESSAGE);
				break;
			}
		}

		return withNull;

	}

	private Boolean toSave() {
		if (lookupClient.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client.", "Save",
					JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		if (lookupCompany.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select company.", "Save",
					JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		if (lookupProject.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project.", "Save",
					JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		if (txtTotalAmount.getValue() == null
				|| ((BigDecimal) txtTotalAmount.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input total amount.", "Save",
					JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		if (checkTable()) {
			return false;
		}

		return true;
	}

	private Boolean checkReceiptNo() {

		Boolean hasReceiptNo = false;

		if (rbOfficialReceipt.isSelected() == true) {
			if (txtOfficialReceipt.getText().trim().equals("OR") || txtOfficialReceipt.getText().trim().equals("")) {
				hasReceiptNo = false;
			} else {
				hasReceiptNo = true;
			}
		} else if (rbProvisionalReceipt.isSelected() == true) {
			if (txtProvisionalReceipt.getText().trim().equals("PR")
					|| txtProvisionalReceipt.getText().trim().equals("")) {
				hasReceiptNo = false;
			} else {
				hasReceiptNo = true;
			}
		} else if (rbAcknowledgementReceipt.isSelected() == true) {
			if (txtAcknowledgementReceipt.getText().trim().equals("AR")
					|| txtAcknowledgementReceipt.getText().trim().equals("")) {
				hasReceiptNo = false;
			} else {
				hasReceiptNo = true;
			}
		} else if (rbPagIBIGReceipt.isSelected() == true) {
			if (txtPagIBIGReceipt.getText().trim().equals("PIR") || txtPagIBIGReceipt.getText().trim().equals("")) {
				hasReceiptNo = false;
			} else {
				hasReceiptNo = true;
			}
		} else if (rbSalesInvoice.isSelected() == true) // ADDED BY MONIQUE FOR ISSUANCE OF SALES INVOICE DTD
														// 2022-07-11; WITH DCRF#2121
		{
			if (txtSalesInvoice.getText().trim().equals("SI") || txtSalesInvoice.getText().trim().equals("")) {
				hasReceiptNo = false;
			} else {
				hasReceiptNo = true;
			}
		}

		return hasReceiptNo;
	}

	private Boolean hasPDC() {

		Boolean hasPDC = false;
		Integer x = 0;
		Integer y = tblPaymentList.getModel().getRowCount();
		Date checkDate = null;

		while (x < y) {
			try {
				checkDate = (Date) modelPaymentList.getValueAt(x, 7);
			} catch (NullPointerException e) {
				checkDate = null;
			}
			if (checkDate == null) {
				hasPDC = false;
			} else {
				if (checkDate.compareTo(FncGlobal.dateFormat(FncGlobal.getDateSQL())) > 0) {
					hasPDC = true;
				} else {
					hasPDC = false;
				}
			}

			x++;
		}

		return hasPDC;
	}

	// table operation
	private void adjustRowModel() {
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeaderPaymentList.setModel(listModel);
		Integer r = tblPaymentList.getRowCount();
		Integer s = 1;
		while (s <= r) {
			listModel.addElement(s);
			s++;
		}
		adjustRowHeight();
	}

	private void adjustRowHeight() {// used

		int rw = tblPaymentList.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			tblPaymentList.setRowHeight(x, 20);
			x++;
		}
	}

	// save, insert
	public static Object[] getPmtdetails(String pay_rec_id) {

		String strSQL = "select \r\n" + "\r\n" + "trim(a.entity_id) as entity_id,\r\n" + // 0
				"trim(a.proj_id) as proj_id,\r\n" + // 1
				"trim(a.pbl_id) as pbl_id,\r\n" + // 2
				"a.seq_no,\r\n" + // 3
				"a.amount,\r\n" + // 4
				"a.pay_part_id,\r\n" + // 5
				"a.amount,\r\n" + // 6
				"a.client_seqno\r\n" + // 7
				"\r\n" + "from (select * from rf_payments where pay_rec_id = 1379 and status_id = 'A') a";

		System.out.printf("SQL #1 getDRFdetails: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private Boolean isLedgerApplied(String client_seqno) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT is_ledger_applied('" + client_seqno + "')";
		db.select(SQL);

		if (db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		} else {
			return false;
		}
	}

	private Boolean isLedgerAppliedCorrect(String client_seqno) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT is_ledger_applied_correct('" + client_seqno + "')";
		db.select(SQL);

		if (db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		} else {
			return false;
		}
	}

}
