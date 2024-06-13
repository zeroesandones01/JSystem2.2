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
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
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
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Buyers.ClientServicing._OrderOfPayment;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
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
public class IssuanceOfReceipt_Alvin extends _JInternalFrame implements ActionListener, _GUI, MouseListener {

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

	private JLabel lblTransactionType;
	private _JXTextField txtClientSequenceNo;
	private _JDateChooser dateTransaction;
	private _JDateChooser dateActual;
	private _JLookup lookupClient;
	private _JXTextField txtClientName;
	private _JLookup lookupCompany;
	private _JXTextField txtCompanyName;
	private _JLookup lookupProject;
	private _JXTextField txtProjectName;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDescription;
	private _JXTextField txtModelID;
	private _JXTextField txtModelName;
	private _JXFormattedTextField txtSellingPrice;
	private _JXTextField txtSequence;

	private JPanel pnlReceipts;

	private JRadioButton rbOfficialReceipt;
	private _JXTextField txtOfficialReceipt;
	private JRadioButton rbProvisionalReceipt;
	private _JXTextField txtProvisionalReceipt;
	private JRadioButton rbAcknowledgementReceipt;
	private _JXTextField txtAcknowledgementReceipt;
	private JRadioButton rbPagIBIGReceipt;
	private _JXTextField txtPagIBIGReceipt;

	private _JScrollPaneMain scrollPaymentList;
	private _JTableMain tblPaymentList;
	private modelIssuanceOfReceipt_PaymentList modelPaymentList;
	private JList rowHeaderPaymentList;

	private _JScrollPaneTotal scrollPaymentListTotal;
	private _JTableTotal tblPaymentListTotal;
	private modelIssuanceOfReceipt_PaymentList modelPaymentListTotal;

	private _JXFormattedTextField txtTotalAmount;

	private JButton btnNewPayment;
	private JButton btnOrderedPayment;
	private JButton btnNewCashReturn;
	private JButton btnReturnToCSD;
	private JButton btnSave;
	private JButton btnPrintReceipt;
	private JButton btnCancel;

	private JButton btnAddRow;
	private JButton btnRemoveRow;

	public IssuanceOfReceipt_Alvin() {
		super(title, true, true, true, true);
		initGUI();
	}

	public IssuanceOfReceipt_Alvin(String title) {
		super(title);
		initGUI();
	}

	public IssuanceOfReceipt_Alvin(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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

		//disabled due to unnecessary icon on toolbar
		/*Image iconInternalFrame = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/buyers/issueanceofreceipt.png")).getImage();
		iconInternalFrame = iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
		this.setFrameIcon(new ImageIcon(iconInternalFrame));*/
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
						JPanel pnlPaymentDetailsWest = new JPanel(new GridLayout(7, 1, 3, 3));
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
					}
					{
						JPanel pnlPaymentDetailsCenter = new JPanel(new BorderLayout(3, 3));
						pnlPaymentDetails.add(pnlPaymentDetailsCenter, BorderLayout.CENTER);
						{
							JPanel jPanel1 = new JPanel(new GridLayout(7, 1, 3, 3));
							pnlPaymentDetailsCenter.add(jPanel1, BorderLayout.WEST);
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
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											txtClientName.setText((String) data[1]);

											String selectedTransaction = lblTransactionType.getText().replaceAll("\\[|\\]", "").trim();

											if(selectedTransaction.equals("NEW PAYMENT")){
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
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
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
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
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
							pnlPaymentDetailsCenter.add(jPanel2, BorderLayout.CENTER);
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
							JPanel pnlRadioButtons = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlReceipts.add(pnlRadioButtons, BorderLayout.WEST);
							pnlRadioButtons.setPreferredSize(new Dimension(175, 0));
							{
								rbOfficialReceipt = new JRadioButton("Official (OR)");
								pnlRadioButtons.add(rbOfficialReceipt);
							}
							{
								rbProvisionalReceipt = new JRadioButton("Provisional (PR)");
								pnlRadioButtons.add(rbProvisionalReceipt);
							}
							{
								rbAcknowledgementReceipt = new JRadioButton("Acknowledgement (AR)");
								pnlRadioButtons.add(rbAcknowledgementReceipt);
							}
							{
								rbPagIBIGReceipt = new JRadioButton("PagIBIG (PIR)");
								pnlRadioButtons.add(rbPagIBIGReceipt);
							}
						}
						{
							JPanel pnlReceiptText = new JPanel(new GridLayout(4, 1, 3, 3));
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
								} catch (NullPointerException e1) { }
							}
						});
						{
							modelPaymentList = new modelIssuanceOfReceipt_PaymentList();
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

									try {
										BigDecimal totalAmount = _IssuanceOfReceipt.totalPayments(modelPaymentList, modelPaymentListTotal);
										txtTotalAmount.setValue(totalAmount);
									} catch (ArrayIndexOutOfBoundsException e1) { }
								}
							});

							tblPaymentList = new _JTableMain(modelPaymentList);//XXX Payment List
							scrollPaymentList.setViewportView(tblPaymentList);
							tblPaymentList.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblPaymentList.rowAtPoint(e.getPoint()) == -1){
										tblPaymentListTotal.clearSelection();
									}else{
										tblPaymentList.setCellSelectionEnabled(true);
									}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblPaymentList.rowAtPoint(e.getPoint()) == -1){
										tblPaymentListTotal.clearSelection();
									}else{
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

							tblPaymentList.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent arg0) {
									if(arg0.getOldValue() != null){
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
							scrollPaymentList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
						scrollPaymentListTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
					}
				}
				{
					JPanel pnlNavigations = new JPanel(new GridLayout(7, 1, 5, 5));
					pnlCenter.add(pnlNavigations, BorderLayout.EAST);
					pnlNavigations.setPreferredSize(new Dimension(150, 0));
					{
						btnNewPayment = new JButton("New Payment");
						pnlNavigations.add(btnNewPayment);
						//btnNewPayment.setActionCommand("New Payment");
						btnNewPayment.addActionListener(this);
					}
					{
						btnOrderedPayment = new JButton("Ordered Payment");
						pnlNavigations.add(btnOrderedPayment);
						//btnNewPayment.setActionCommand("Ordered Payment");
						btnOrderedPayment.addActionListener(this);
					}
					{
						btnNewCashReturn = new JButton("New Cash Return");
						pnlNavigations.add(btnNewCashReturn);
						btnNewCashReturn.addActionListener(this);
					}
					{
						btnReturnToCSD = new JButton("Return to CSD");
						pnlNavigations.add(btnReturnToCSD);
						btnReturnToCSD.addActionListener(this);
					}
					{
						btnSave = new JButton("Save");
						pnlNavigations.add(btnSave);
						btnSave.addActionListener(this);
					}
					{
						btnPrintReceipt = new JButton("Print Receipt");
						pnlNavigations.add(btnPrintReceipt);
						btnPrintReceipt.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigations.add(btnCancel);
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
					JLabel lblTotal= new JLabel("TOTAL: ");
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

		//setComponentsEditable(pnlPaymentDetails, false);
		setComponentsEnabled(pnlDates, false);
		setComponentsEnabled(pnlReceipts, false);

		clearPaymentDetails();
		setComponentsEnabled(pnlPaymentDetails, false);

		clearReceipt();
		setComponentsEnabled(pnlReceipts, false);

		FncTables.bindColumnTables(tblPaymentList, tblPaymentListTotal);
		tblPaymentList.hideColumns("Part ID", "Check Type ID", "Bank ID", "Branch ID", "Receipt ID");

		btnState(true, true, true, false, false, true, true);
	}//XXX End of initGUI

	private void btnState(Boolean sNewPayment, Boolean sOrderedPayment, Boolean sNewCashReturn, Boolean sReturnToCSD, Boolean sSave, Boolean sPrintReceipt, Boolean sCancel) {
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
	}

	/*private void displayHolding(Object[] data) {
		lookupClient.setValue((String) data[1]);
		txtClientName.setText((String) data[2]);
		lookupCompany.setValue((String) data[3]);
		txtCompanyName.setText((String) data[4]);
		txtUnitID.setText((String) data[5]);
		txtUnitDescription.setText((String) data[6]);
		//txtLotArea.setValue((BigDecimal) data[7]);

		dateTransaction.setDate((Date) data[9]);
		dateActual.setDate((Date) data[10]);

		//Display payments in table
		_IssueanceOfReceipt.displayHoldingPayments(modelPaymentList, txtClientSequenceNo.getText());
		tblPaymentList.packAll();

		btnState(true, false, true, false, true);
	}*/

	private void displayReservation(Object[] data) {
		clearPaymentDetails();
		setComponentsEnabled(pnlPaymentDetails, data != null);
		setComponentsEnabled(pnlDates, data != null);
		clearReceipt();
		setComponentsEnabled(pnlReceipts, false);
		modelPaymentList.clear();
		rowHeaderPaymentList.setModel(new DefaultListModel());
		txtTotalAmount.setEditable(false);

		if(data != null){
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
			if(data[15] != null){
				txtSequence.setText(Integer.toString((Integer) data[15]));
			}

			if(((String) data[16]).equals("HOLDING")){
				//Display payments in table
				_IssuanceOfReceipt.displayHoldingPayments(modelPaymentList, (String) data[0]);
				tblPaymentList.packAll();
			} else if(((String) data[16]).equals("RESERVATION")){
				//Display payments in table
				_IssuanceOfReceipt.displayReservationPayments(modelPaymentList, (String) data[0]);
				tblPaymentList.packAll();
			} else {
				//Display payments in table
				_IssuanceOfReceipt.displayReservationPayments(modelPaymentList, (String) data[0]);
				tblPaymentList.packAll();
			}
			btnState(false, false, false, (!((String) data[16]).equals("HOLDING")), true, false, true);

			/*BigDecimal percent = null;
			for(int x=0; x < modelPaymentList.getRowCount(); x++){
				String part_id = (String) modelPaymentList.getValueAt(x, 0);
				percent = _IssuanceOfReceipt.totalPercentage(lookupClient.getValue(), txtProjectID.getText(), txtUnitID.getText(), Integer.parseInt(txtSequence.getText()), part_id, (BigDecimal) txtTotalAmount.getValued());
			}
			//System.out.printf("Percent: %s%n", percent);

			String doc_id = "03";
			if(percent.compareTo(new BigDecimal(20.00)) >= 0) {
				doc_id = "01";
			}

			if(_HoldingAndReservation.getLatestAR(doc_id) > 0){
				btnState(false, false, false, true, false, true);
			}else{
				btnState(false, false, false, false, false, true);

				if(doc_id.equals("01")){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "No official receipt to issue.\n\nPlease go to Admin>Add Receipt Number and tag new official receipt to issue.", "New Payment", JOptionPane.WARNING_MESSAGE);
				}
				if(doc_id.equals("03")){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "No acknowledgement receipt to issue.\n\nPlease go to Admin>Add Receipt Number and tag new acknowledgement receipt to issue.", "New Payment", JOptionPane.WARNING_MESSAGE);
				}
			}*/
		}else{
			btnState(true, true, true, false, false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "No available payments.", "New Payment", JOptionPane.WARNING_MESSAGE);
		}
	}

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
		btnState(false, false, false, false, true, false, true);
		
		modelPaymentList.addRow(new Object[] { null, null, null, "Cash", null, null, null, null, null, null, null, null, null, null, null, null });
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

		txtClientSequenceNo.setText(_IssuanceOfReceipt.getClientSeqNo(true));
		lblTransactionType.setText(String.format("[ %s ]", "CASH RETURN"));
		//txtTotalAmount.setEditable(true);

		setComponentsEnabled(pnlDates, true);
		setDates(Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
		tableNavigation(true);
		btnState(false, false, false, false, true, false, true);
	}

	private void displayReceiptDetails() {
		Object[] dataReceipt = _IssuanceOfReceipt.getReceiptDetails(txtClientSequenceNo.getText(), lblTransactionType.getText().replaceAll("\\[|\\]", "").trim(),
				lookupClient.getValue(), lookupProject.getValue(), txtUnitID.getText(), txtSequence.getText().equals("") ? null:Integer.parseInt(txtSequence.getText()));
		String receipt_type = (String) dataReceipt[0];
		String receipt_no = (String) dataReceipt[1];

		if(receipt_type.equals("ORV")){
			rbOfficialReceipt.setEnabled(true);
			rbOfficialReceipt.setSelected(true);
			txtOfficialReceipt.setEnabled(true);
			txtOfficialReceipt.setText(receipt_no);
		}
		if(receipt_type.equals("AR")){
			rbAcknowledgementReceipt.setEnabled(true);
			rbAcknowledgementReceipt.setSelected(true);
			txtAcknowledgementReceipt.setEnabled(true);
			txtAcknowledgementReceipt.setText(receipt_no);
		}
		if(receipt_type.equals("PFR")){
			rbPagIBIGReceipt.setEnabled(true);
			rbPagIBIGReceipt.setSelected(true);
			txtPagIBIGReceipt.setEnabled(true);
			txtPagIBIGReceipt.setText(receipt_no);
		}
	}

	private Boolean toSave() {
		if(lookupClient.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client.", "Save", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		if(lookupCompany.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select company.", "Save", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		if(lookupProject.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project.", "Save", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		if(txtTotalAmount.getValue() == null || ((BigDecimal)txtTotalAmount.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input total amount.", "Save", JOptionPane.PLAIN_MESSAGE);
			return false;
		}

		return true;
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

	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();
		String selectedTransaction = lblTransactionType.getText().replaceAll("\\[|\\]", "").trim();

		if(action.equals("New Payment")){
			newPayment();
		}

		if(action.equals("Ordered Payment")){
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Ordered Payment", _IssuanceOfReceipt.sqlNewPayment(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if(data != null){
				String client_seqno = (String) data[1];
				String transaction = (String) data[3];

				if(transaction.equals("HOLDING")){
					displayReservation(_IssuanceOfReceipt.getHoldingDetails(client_seqno));
				}else{
					displayReservation(_IssuanceOfReceipt.getNewPaymentDetails(client_seqno));
				}
			}else{
				//JOptionPane.showMessageDialog(parentComponent, message, selectedTransaction, messageType);
			}
		}

		if(action.equals("New Cash Return")){
			newCashReturn();
		}

		if(action.equals("Return to CSD")){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to return transaction to CSD?", "Return to CSD", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				_IssuanceOfReceipt.returnToCSD(txtClientSequenceNo.getText());

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Transaction has been return to CSD successfully.", action, JOptionPane.INFORMATION_MESSAGE);
				btnCancel.doClick();
			}
		}

		if(action.equals("Save")){
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					if(selectedTransaction.equals("HOLDING")) {
						if(_IssuanceOfReceipt.saveHolding(txtClientSequenceNo.getText(), lookupCompany.getValue(), txtUnitID.getText())){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Holding payment has been saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(true, true, true, false, false, true, true);
						}
					} else if(selectedTransaction.equals("RESERVATION")) {
						if(_IssuanceOfReceipt.saveReservation(lookupCompany.getValue(), txtClientSequenceNo.getText(), "106", (BigDecimal)txtTotalAmount.getValued())){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Reservation payment has been saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(true, true, true, false, false, true, true);
						}
					} else if(selectedTransaction.equals("CASH RETURN")) {
						/*if(_IssuanceOfReceipt.saveCashReturn(modelPaymentList, lookupClient.getValue(), (BigDecimal)txtTotalAmount.getValued(), lookupCompany.getValue())){
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
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cash Return payment has been saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(true, true, true, false, false, true, true);
						}*/
					} else if(selectedTransaction.equals("NEW PAYMENT")) {
						if(_IssuanceOfReceipt.saveNewPayment(modelPaymentList, lookupClient.getValue(), lookupProject.getText(), txtUnitID.getText(), Integer.parseInt(txtSequence.getText()), (BigDecimal)txtTotalAmount.getValued(), lookupCompany.getValue())){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New payment has been saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(true, true, true, false, false, true, true);
						}
					} else {
						if(_IssuanceOfReceipt.saveOrderedPayment(modelPaymentList, txtClientSequenceNo.getText(), (BigDecimal)txtTotalAmount.getValued(), lookupCompany.getValue())){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New payment has been saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
							displayReceiptDetails();
							btnState(true, true, true, false, false, true, true);
						}
					}
				}
			}
		}

		if(action.equals("Print Receipt")){
			if(rbAcknowledgementReceipt.isSelected()) {
				//_IssuanceOfReceipt.printAR(txtClientSequenceNo.getText(), txtAcknowledgementReceipt.getText(), "", "", null);
			}
			if(rbOfficialReceipt.isSelected()) {
				_IssuanceOfReceipt.printOR(txtClientSequenceNo.getText(), txtOfficialReceipt.getText(), "", "", null);
			}
			if(rbPagIBIGReceipt.isSelected()) {
				_IssuanceOfReceipt.printPR(txtClientSequenceNo.getText(), txtOfficialReceipt.getText());
			}
		}

		if(action.equals("Cancel")){
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
			btnState(true, true, true, false, false, false, false);
		}






		if (action.equals("Add Row")) {
			//int seq_no = modelPaymentList.getRowCount() + 1;

			modelPaymentList.addRow(new Object[] { null, null, null, "Cash", null, null, null, null, null, null, null, null, null, null, null, null });

			//((DefaultListModel) rowHeaderPaymentList.getModel()).addElement(modelPaymentList.getRowCount());
			//updateWorkItem();
		}

		if (action.equals("Remove Row")) {
			int[] selectedRows = tblPaymentList.getSelectedRows();

			if (selectedRows.length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row in Payment List.", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (int x = selectedRows.length - 1; x > -1; x--) {
				int row = selectedRows[x];

				modelPaymentList.removeRow(row);
				//((DefaultListModel) rowHeaderPaymentList.getModel()).removeElementAt(row);
			}

			//updateWorkItem();
			//computeTotal();
		}
	}

	/**
	 * Added by Alvin Gonzales (2015-07-15)
	 * 
	 */
	private void tableNavigation(boolean enable) {
		btnAddRow.setEnabled(enable);
		btnRemoveRow.setEnabled(enable);
	}

	private JPanel displayTableNavigation() {
		btnAddRow = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Plus2-Math-icon.png")));
		btnAddRow.setActionCommand("Add Row");
		btnAddRow.setToolTipText("Add Row");
		btnAddRow.setEnabled(false);
		btnAddRow.addActionListener(this);

		btnRemoveRow = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
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

	private void displayParticulars() {//XXX
		String selectedTransaction = lblTransactionType.getText().replaceAll("\\[|\\]", "").trim();
		String entity_id = lookupClient.getValue();
		String proj_id = lookupProject.getValue();
		String pbl_id = txtUnitID.getText();
		String seq_no = txtSequence.getText();

		int column = tblPaymentList.convertColumnIndexToModel(tblPaymentList.getSelectedColumn());
		int row = tblPaymentList.getSelectedRow();

		if (column == 1 && modelPaymentList.isEditable()) {
			String SQL = "";
			if(selectedTransaction.equals("CASH RETURN")){
				SQL = "SELECT TRIM(pay_part_id)::VARCHAR as \"ID\", TRIM(partdesc) as \"Name\", TRIM(particulars) as \"Alias\" FROM mf_pay_particular WHERE group_id = '06' AND status_id = 'A' ORDER BY partdesc;";
			}else{
				if(entity_id == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client first.", "Particular", JOptionPane.WARNING_MESSAGE);
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
			Object[] option = new Object[]{"Cash", "Check"};
			int payment_type = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select payment type.", "Payment Type", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option,  0);
			modelPaymentList.setValueAt(option[payment_type], row, column);
			tblPaymentList.packAll();
		}

		if (column == 6 && modelPaymentList.isEditable()) { //lookup for Check Type
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Check", _OrderOfPayment.getCheckType(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				//modelPaymentList.setValueAt("Check", row, 3);
				modelPaymentList.setValueAt(data[0], row, 5);
				modelPaymentList.setValueAt(data[1], row, column);
				tblPaymentList.packAll();
			}
		}

		if (column == 10 && modelPaymentList.isEditable()) { //lookup for Bank
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Bank", _OrderOfPayment.getBank(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				//modelPaymentList.setValueAt("Check", row, 3);
				modelPaymentList.setValueAt(data[0], row, 9);
				modelPaymentList.setValueAt(data[1], row, column);
				tblPaymentList.packAll();
			}
		}

		if (column == 12 && modelPaymentList.isEditable()) { //lookup for Bank Branch
			String bank_id = (String) modelPaymentList.getValueAt(row, 9);
			if(bank_id == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select bank first.", "Branch", JOptionPane.WARNING_MESSAGE);
				return;
			}

			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Branch", _OrderOfPayment.getBankBranch(bank_id), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				//modelPaymentList.setValueAt("Check", row, 3);
				modelPaymentList.setValueAt(data[0], row, 11);
				modelPaymentList.setValueAt(data[1], row, column);
				tblPaymentList.packAll();
			}
		}
	}

	private JPopupMenu initializeMenu() {//XXX Menu
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
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		int column = tblPaymentList.convertColumnIndexToModel(tblPaymentList.getSelectedColumn());

		if(arg0.isPopupTrigger() && (column == 1 || column== 6 || column== 10 || column== 12)){
			initializeMenu().show((_JTableMain)arg0.getSource(), arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		int column = tblPaymentList.convertColumnIndexToModel(tblPaymentList.getSelectedColumn());

		if(arg0.isPopupTrigger() && (column == 1 || column== 6 || column== 10 || column== 12)){
			initializeMenu().show((JPanel)arg0.getSource(), arg0.getX(), arg0.getY());
		}
	}
	
	private void checkTable() {
		/*int[] list = 
		
		
		for(int x=0; ){
			
		}*/
		
		
		
		
		
		
		
		
		
		
		
	}

}
