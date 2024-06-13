package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
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

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPane;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelWP_PaymentsMade;
import tablemodel.modelWP_PaymentsWaived;

public class WaivePenalty extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -7330353573332060594L;

	private static String title = "Waive Penalty";
	static Dimension SIZE = new Dimension(1200, 500);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	//private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlWest;

	private JPanel pnlClientDetails;
	private JPanel pnlCDLabel;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeq;
	private JLabel lblSellingPrice;
	private JLabel lblReservationDate;
	private JLabel lblORDate;
	private JLabel lblStatus;

	private JPanel pnlCDComponents;
	private _JLookup lookupClient;
	private _JXTextField txtClient;

	private _JXTextField txtProjID;
	private _JXTextField txtProjName;

	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;

	private _JXTextField txtSeqNo;

	private _JXFormattedTextField txtSellingPrice;
	private _JDateChooser dteReservation;
	private _JDateChooser dteORDate;
	private _JXTextField txtStatus;

	private JPanel pnlRequestDetails;

	private JPanel pnlRDLabel;
	private JLabel lblRequestNo;
	private JLabel lblRequestDate;
	private JLabel lblRequestBy;
	private JLabel lblRequestStatus;
	private JLabel lblRequestRemarks;

	private JPanel pnlRDComponents;
	private _JXTextField txtRequestNo;
	private _JDateChooser dteRequest;
	private JTextField txtRequestBy;
	private _JXTextField txtRequestStatus;
	private JTextField txtRequestRemarks;

	private JPanel pnlEast;

	private _JTabbedPane tabCenter;
	private JPanel pnlWaive;
	private JPanel pnlPaymentsMade;
	private _JTableMain tblPaymentsMade;
	private _JScrollPane scrollPaymentsMade;
	private JList rowHeaderPaymentsMade;
	private modelWP_PaymentsMade modelPmtsMade;

	private JPanel pnlPaymentsWaived;
	private _JTableMain tblPaymentsWaived;
	private _JScrollPaneMain scrollPaymentsWaived;
	private JList rowHeaderPaymentsWaived;
	private modelWP_PaymentsWaived modelPmtsWaived;
	
	private _JScrollPaneTotal scrollPWTotal;
	private _JTableTotal tblPWTotal;
	private modelWP_PaymentsWaived modelPmtsWaivedTotal;

	private JPanel pnlJournalEntries;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnSearch;
	private JButton btnPost;
	private JButton btnClear;
	private JButton btnPreview;

	public WaivePenalty() {
		super(title, true, true, true, true);
		initGUI();
	}

	public WaivePenalty(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);

		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter, BorderLayout.CENTER);
			{
				pnlWest = new JPanel(new BorderLayout(5, 5));
				pnlCenter.add(pnlWest, BorderLayout.WEST);
				pnlWest.setPreferredSize(new Dimension(500, 0));
				{
					pnlRequestDetails = new JPanel(new BorderLayout(5, 5));
					pnlWest.add(pnlRequestDetails, BorderLayout.NORTH);
					pnlRequestDetails.setBorder(JTBorderFactory.createTitleBorder("Request Details"));
					pnlRequestDetails.setPreferredSize(new Dimension(150, 150));
					{
						pnlRDLabel = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlRequestDetails.add(pnlRDLabel, BorderLayout.WEST);
						{
							lblRequestNo = new JLabel("Request No:");
							pnlRDLabel.add(lblRequestNo);
						}
						{
							lblRequestStatus = new JLabel("Status");
							pnlRDLabel.add(lblRequestStatus);
						}
						{
							lblRequestBy = new JLabel("Request By");
							pnlRDLabel.add(lblRequestBy);
						}
						{
							lblRequestRemarks = new JLabel("Remarks");
							pnlRDLabel.add(lblRequestRemarks);
						}
					}
					{
						pnlRDComponents = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlRequestDetails.add(pnlRDComponents, BorderLayout.CENTER);
						{
							JPanel pnlRequestNo = new JPanel(new BorderLayout(3, 3));
							pnlRDComponents.add(pnlRequestNo);
							{
								txtRequestNo = new _JXTextField();
								pnlRequestNo.add(txtRequestNo, BorderLayout.WEST);
								txtRequestNo.setPreferredSize(new Dimension(150, 0));
							}
							{
								lblRequestDate = new JLabel("Date", JLabel.TRAILING);
								pnlRequestNo.add(lblRequestDate);
							}
							{
								dteRequest = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlRequestNo.add(dteRequest, BorderLayout.EAST);
								dteRequest.setPreferredSize(new Dimension(150, 0));
								dteRequest.setEditable(false);
								dteRequest.getCalendarButton().setEnabled(false);
								//dteRequest.setDate(FncGlobal.getDateToday());
							}
						}
						{
							txtRequestStatus = new _JXTextField();
							pnlRDComponents.add(txtRequestStatus);
						}
						{
							txtRequestBy = new JTextField();
							pnlRDComponents.add(txtRequestBy);
						}
						{
							txtRequestRemarks = new JTextField();
							pnlRDComponents.add(txtRequestRemarks);
						}
					}
				}
				{
					pnlClientDetails = new JPanel(new BorderLayout(5, 5));
					pnlWest.add(pnlClientDetails, BorderLayout.CENTER);
					pnlClientDetails.setBorder(JTBorderFactory.createTitleBorder("Client Details"));
					{
						pnlCDLabel = new JPanel(new GridLayout(7, 1, 5, 5));
						pnlClientDetails.add(pnlCDLabel, BorderLayout.WEST);
						{
							lblClient = new JLabel("Client");
							pnlCDLabel.add(lblClient);
						}
						{
							lblProject = new JLabel("Project");
							pnlCDLabel.add(lblProject);
						}
						{
							lblUnit = new JLabel("Unit");
							pnlCDLabel.add(lblUnit);
						}
						{
							lblSeq = new JLabel("Seq. No");
							pnlCDLabel.add(lblSeq);
						}

						{
							lblReservationDate = new JLabel("Reservation Date");
							pnlCDLabel.add(lblReservationDate);
						}
						{
							lblORDate = new JLabel("OR Date");
							pnlCDLabel.add(lblORDate);
						}
						{
							lblStatus = new JLabel("Status");
							pnlCDLabel.add(lblStatus);
						}
					}
					{
						pnlCDComponents = new JPanel(new GridLayout(7, 1, 5, 5));
						pnlClientDetails.add(pnlCDComponents, BorderLayout.CENTER);
						{
							JPanel pnlClient = new JPanel(new BorderLayout(5, 5));
							pnlCDComponents.add(pnlClient);
							{
								lookupClient = new _JLookup(null, "Client", 0);
								pnlClient.add(lookupClient, BorderLayout.WEST);
								lookupClient.setLookupSQL(_WaivePenalty.sqlClient());
								lookupClient.setFilterName(true);
								lookupClient.setPreferredSize(new Dimension(100, 0));
								lookupClient.setEditable(false);
								lookupClient.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										FncSystem.out("Display SQL For Lookup of Client", lookupClient.getLookupSQL());

										if(data != null){

											String entity_id = (String) data[0];
											String entity_name = (String) data[1];
											String proj_id = (String) data[2];
											String proj_name = (String) data[3];
											String unit_id = (String) data[4];
											String unit_desc = (String) data[5];
											Integer seq_no = (Integer) data[6];
											BigDecimal sellingprice = (BigDecimal) data[7];
											Date reservation_date = (Date) data[8];
											Date or_date = (Date) data[9];
											String status = (String) data[10];

											txtClient.setText(entity_name);
											txtProjID.setText(proj_id);
											txtProjName.setText(proj_name);
											txtUnitID.setText(unit_id);
											txtUnitDesc.setText(unit_desc);
											txtSeqNo.setText(seq_no.toString());
											txtSellingPrice.setValue(sellingprice);
											dteReservation.setDate(reservation_date);
											dteORDate.setDate(or_date);
											txtStatus.setText(status);

											_WaivePenalty.displayPaymentsMade(modelPmtsMade, entity_id, proj_id, unit_id, seq_no, txtRequestNo.getText().trim());
											scrollPaymentsMade.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPaymentsMade.getRowCount())));
											tblPaymentsMade.packAll();

										}
									}
								});
							}
							{
								txtClient = new _JXTextField();
								pnlClient.add(txtClient, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
							pnlCDComponents.add(pnlProject);
							{
								txtProjID = new _JXTextField();
								pnlProject.add(txtProjID, BorderLayout.WEST);
								txtProjID.setPreferredSize(new Dimension(50, 0));
								txtProjID.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								txtProjName = new _JXTextField();
								pnlProject.add(txtProjName, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlUnit = new JPanel(new BorderLayout(5, 5));
							pnlCDComponents.add(pnlUnit);
							{
								txtUnitID = new _JXTextField();
								pnlUnit.add(txtUnitID, BorderLayout.WEST);
								txtUnitID.setPreferredSize(new Dimension(100, 0));
								txtUnitID.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								txtUnitDesc = new _JXTextField();
								pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlSeq = new JPanel(new BorderLayout(5, 5));
							pnlCDComponents.add(pnlSeq);
							{
								txtSeqNo = new _JXTextField();
								pnlSeq.add(txtSeqNo, BorderLayout.WEST);
								txtSeqNo.setPreferredSize(new Dimension(50, 0));
								txtSeqNo.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								lblSellingPrice = new JLabel("Selling Price", JLabel.TRAILING);
								pnlSeq.add(lblSellingPrice);
							}
							{
								txtSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlSeq.add(txtSellingPrice, BorderLayout.EAST);
								txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSellingPrice.setValue(new BigDecimal("0.00"));
								txtSellingPrice.setPreferredSize(new Dimension(150, 0));
								txtSellingPrice.setEditable(false);
							}
						}

						{
							dteReservation = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlCDComponents.add(dteReservation);
							dteReservation.setEditable(false);
							dteReservation.getCalendarButton().setEnabled(false);
						}
						{
							dteORDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlCDComponents.add(dteORDate);
							dteORDate.setEditable(false);
							dteORDate.getCalendarButton().setEnabled(false);
						}
						{
							txtStatus = new _JXTextField();
							pnlCDComponents.add(txtStatus);
						}
					}
				}
			}
			{
				tabCenter = new _JTabbedPane();
				pnlCenter.add(tabCenter, BorderLayout.CENTER);
				{
					pnlWaive = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Waive", pnlWaive);
					{
						JPanel pnlWaiveCenter = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlWaive.add(pnlWaiveCenter, BorderLayout.CENTER);
						{
							pnlPaymentsMade = new JPanel(new BorderLayout(3, 3));
							pnlWaiveCenter.add(pnlPaymentsMade);
							pnlPaymentsMade.setBorder(JTBorderFactory.createTitleBorder("Payments Made"));
							{
								scrollPaymentsMade = new _JScrollPane();
								pnlPaymentsMade.add(scrollPaymentsMade, BorderLayout.CENTER);
								scrollPaymentsMade.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								{
									modelPmtsMade = new modelWP_PaymentsMade();
									tblPaymentsMade = new _JTableMain(modelPmtsMade);
									tblPaymentsMade.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if(tblPaymentsMade.rowAtPoint(e.getPoint()) == -1){
												tblPWTotal.clearSelection();
											}else{
												tblPaymentsMade.setCellSelectionEnabled(true);
											}
										}
										public void mouseReleased(MouseEvent e) {
											if(tblPaymentsMade.rowAtPoint(e.getPoint()) == -1){
												tblPWTotal.clearSelection();
											}else{
												tblPaymentsMade.setCellSelectionEnabled(true);
											}
										}
									});
									
									scrollPaymentsMade.setViewportView(tblPaymentsMade);
									tblPaymentsMade.hideColumns("Rec. ID");
									modelPmtsMade.addTableModelListener(new TableModelListener() {

										@Override
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderPaymentsMade.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowHeaderPaymentsMade.getModel()).addElement(modelPmtsMade.getRowCount());
											}
										}
									});
								}
								{
									rowHeaderPaymentsMade = tblPaymentsMade.getRowHeader();
									rowHeaderPaymentsMade.setModel(new DefaultListModel());
									scrollPaymentsMade.setRowHeaderView(rowHeaderPaymentsMade);
									scrollPaymentsMade.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						{
							pnlPaymentsWaived = new JPanel(new BorderLayout(3, 3));
							pnlWaiveCenter.add(pnlPaymentsWaived);
							pnlPaymentsWaived.setBorder(JTBorderFactory.createTitleBorder("Payments Waived"));
							{
								scrollPaymentsWaived = new _JScrollPaneMain();
								pnlPaymentsWaived.add(scrollPaymentsWaived, BorderLayout.CENTER);
								//scrollPaymentsWaived.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								{
									modelPmtsWaived = new modelWP_PaymentsWaived();
									tblPaymentsWaived = new _JTableMain(modelPmtsWaived);
									scrollPaymentsWaived.setViewportView(tblPaymentsWaived);
									tblPaymentsWaived.hideColumns("%");
									tblPaymentsWaived.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if(tblPaymentsWaived.rowAtPoint(e.getPoint()) == -1){
												tblPWTotal.clearSelection();
											}else{
												tblPaymentsWaived.setCellSelectionEnabled(true);
											}
										}
										public void mouseReleased(MouseEvent e) {
											if(tblPaymentsWaived.rowAtPoint(e.getPoint()) == -1){
												tblPWTotal.clearSelection();
											}else{
												tblPaymentsWaived.setCellSelectionEnabled(true);
											}
										}
									});
									
									modelPmtsWaived.addTableModelListener(new TableModelListener() {

										@Override
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderPaymentsWaived.setModel(new DefaultListModel());
												((DefaultListModel)rowHeaderPaymentsWaived.getModel()).addElement(modelPmtsWaived.getRowCount());
												scrollPWTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPmtsWaived.getRowCount())));
												_WaivePenalty.totalWPEntries(modelPmtsWaived, modelPmtsWaivedTotal);
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowHeaderPaymentsWaived.getModel()).addElement(modelPmtsWaived.getRowCount());
												if(modelPmtsWaived.getRowCount() == 0){
													rowHeaderPaymentsWaived.setModel(new DefaultListModel());
													scrollPWTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPmtsWaived.getRowCount())));
												}
											}
										}
									});
								}
								{
									rowHeaderPaymentsWaived = tblPaymentsWaived.getRowHeader();
									rowHeaderPaymentsWaived.setModel(new DefaultListModel());
									scrollPaymentsWaived.setRowHeaderView(rowHeaderPaymentsWaived);
									scrollPaymentsWaived.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
							{
								scrollPWTotal = new _JScrollPaneTotal(scrollPaymentsWaived);
								pnlPaymentsWaived.add(scrollPWTotal, BorderLayout.SOUTH);
								{
									modelPmtsWaivedTotal = new modelWP_PaymentsWaived();
									modelPmtsWaivedTotal.addRow(new Object[] {0.00, "Total", null});

									tblPWTotal = new _JTableTotal(modelPmtsWaivedTotal, tblPaymentsWaived);
									scrollPWTotal.setViewportView(tblPWTotal);

									tblPWTotal.setTotalLabel(1);
								}
							}
						}
					}
					/*{
						JPanel pnlWaiveSouth = new JPanel(new BorderLayout(3, 3));
						pnlWaive.add(pnlWaiveSouth, BorderLayout.SOUTH);

					}*/
				}
				{
					pnlJournalEntries = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Journal Entries", pnlJournalEntries);
					{
						JPanel pnlJECenter = new JPanel(new BorderLayout(3, 3));
						pnlJournalEntries.add(pnlJECenter, BorderLayout.CENTER);

					}
					{
						JPanel pnlJESouth = new JPanel(new BorderLayout(3, 3));
						pnlJournalEntries.add(pnlJESouth, BorderLayout.SOUTH);

					}
				}
			}
		}
		{
			pnlSouth = new JPanel(new GridLayout(1, 6, 5, 5));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			{
				btnNew = new JButton("New");
				pnlSouth.add(btnNew);
				btnNew.setActionCommand(btnNew.getText());
				btnNew.setMnemonic(KeyEvent.VK_N);
				btnNew.addActionListener(this);
			}
			{
				btnSave = new JButton("Save");
				pnlSouth.add(btnSave);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.setMnemonic(KeyEvent.VK_S);
				btnSave.addActionListener(this);
			}
			{
				btnCancel = new JButton("Cancel Request");
				pnlSouth.add(btnCancel);
				btnCancel.setActionCommand(btnCancel.getText());
				btnCancel.setMnemonic(KeyEvent.VK_C);
				btnCancel.addActionListener(this);
			}
			{
				btnSearch = new JButton("Search");
				pnlSouth.add(btnSearch);
				btnSearch.setActionCommand(btnSearch.getText());
				btnSearch.setMnemonic(KeyEvent.VK_E);
				btnSearch.addActionListener(this);
			}
			{
				btnPreview = new JButton("Preview");
				pnlSouth.add(btnPreview);
				btnPreview.setActionCommand(btnPreview.getText());
				btnPreview.setMnemonic(KeyEvent.VK_R);
				btnPreview.addActionListener(this);
			}
			{
				btnPost = new JButton("Post");
				pnlSouth.add(btnPost);
				btnPost.setActionCommand(btnPost.getText());
				btnPost.setMnemonic(KeyEvent.VK_P);
				btnPost.addActionListener(this);
			}
			{
				btnClear = new JButton("Clear");
				pnlSouth.add(btnClear);
				btnClear.setActionCommand(btnClear.getText());
				btnClear.setMnemonic(KeyEvent.VK_C);
				btnClear.addActionListener(this);
			}
		}
		//System.out.println("Dumaan dito sa Init GUI ng Waive Penalty");
		btnState(true, false, false, true, false, false, false);
	}//XXX END OF INIT GUI

	private void btnState(Boolean sNew, Boolean sSave, Boolean sCancel, Boolean sSearch, Boolean sPreview, Boolean sPost,Boolean sClear){
		btnNew.setEnabled(sNew);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
		btnSearch.setEnabled(sSearch);
		btnClear.setEnabled(sClear);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
	}

	private void newWP(){
		lookupClient.setEditable(true);

		btnState(false, true, false, false, false, false, true);
	}

	private void cancelWP(String request_no){
		pgUpdate db = new pgUpdate();

		String SQL = "UPDATE req_rt_request_header SET request_status = 'X' WHERE request_no = '"+request_no+"'";
		db.executeUpdate(SQL, true);
		db.commit();

	}

	private void searchWP(){

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null , "Request", _WaivePenalty.sqlSearch(), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object [] data = dlg.getReturnDataSet();

		if(data != null){

			String request_no = (String) data[0];
			String client_name = (String) data[1];
			String request_status = (String) data[2];
			Date request_date = (Date) data[3];
			String entity_id = (String) data[4];
			String proj_id = (String) data[5];
			String unit_id = (String) data[6];
			Integer seq_no = (Integer) data[7];
			String request_by = (String) data[8];
			String request_remarks = (String) data[9];

			txtRequestNo.setText(request_no);
			txtRequestStatus.setText(request_status);
			dteRequest.setDate(request_date);
			txtRequestBy.setText(request_by);
			txtRequestRemarks.setText(request_remarks);

		}
	}

	private void clearWP(){

		txtRequestNo.setText("");
		dteRequest.setDate(null);
		txtRequestBy.setText("");
		txtRequestStatus.setText("");
		txtRequestRemarks.setText("");

		lookupClient.setEditable(false);
		lookupClient.setValue(null);
		txtClient.setText("");
		txtProjID.setText("");
		txtProjName.setText("");
		txtUnitID.setText("");
		txtUnitDesc.setText("");
		txtSeqNo.setText("");
		txtSellingPrice.setValue(new BigDecimal("0.00"));
		dteReservation.setDate(null);
		dteORDate.setDate(null);
		txtStatus.setText("");

		modelPmtsMade.clear();
		scrollPaymentsMade.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		rowHeaderPaymentsMade.setModel(new DefaultListModel());

		modelPmtsWaived.clear();
		scrollPaymentsWaived.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		rowHeaderPaymentsWaived.setModel(new DefaultListModel());

		//btnState(true, false, false, true, false, false);
		//btnState(sNew, sSave, sCancel, sSearch, sPreview, sPost, sClear);
		btnState(true, false, false, true, false, false, false);

	}

	private void displayClientDetails(String entity_id, String proj_id, String unit_id, Integer seq_no){

		pgSelect db = new pgSelect();

		String SQL = "SELECT get_project_name('"+proj_id+"'),  get_unit_description('"+proj_id+"', \n"+
				"getinteger('"+unit_id+"')::VARCHAR), get_nsp('"+entity_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+proj_id+"'), \n"+
				"_get_tr_date('"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+"), \n"+
				"_get_or_date('"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+"), \n"+
				"get_current_status('"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+")";

		db.select(SQL);

		if(db.isNotNull()){
			String proj_name = (String) db.getResult()[0][0];
			String unit_desc = (String) db.getResult()[0][1];
			BigDecimal nsp = (BigDecimal) db.getResult()[0][2];
			Date tr_date = (Date) db.getResult()[0][3];
			Date or_date = (Date) db.getResult()[0][4];
			String currentstatus = (String) db.getResult()[0][5];

			txtProjName.setText(proj_name);
			txtUnitDesc.setText(unit_desc);
			txtSellingPrice.setValue(nsp);
			dteReservation.setDate(tr_date);
			dteORDate.setDate(or_date);
			txtStatus.setText(currentstatus);

		}
	}

	private void previewWaivePenalty(String request_no){

		/*ArrayList<TD_WaivePenalty_PmtsMade> listPmtsMade = new ArrayList<TD_WaivePenalty_PmtsMade>();

		for(int x = 0; x<modelPmtsMade.getRowCount(); x++){
			Date date_paid = (Date) modelPmtsMade.getValueAt(x, 1);
			String particular = (String) modelPmtsMade.getValueAt(x, 2);
			BigDecimal amount = (BigDecimal) modelPmtsMade.getValueAt(x, 3);

			listPmtsMade.add(new TD_WaivePenalty_PmtsMade(date_paid, particular, amount));
		}

		ArrayList<TD_WaivePenalty_PmtsWaived> listPmtsWaived = new ArrayList<TD_WaivePenalty_PmtsWaived>();

		for(int x = 0; x<modelPmtsWaived.getRowCount(); x++){
			BigDecimal amount = (BigDecimal) modelPmtsWaived.getValueAt(x, 0);
			String particular = (String) modelPmtsWaived.getValueAt(x, 1);
			String remarks = (String) modelPmtsWaived.getValueAt(x, 2);

			listPmtsWaived.add(new TD_WaivePenalty_PmtsWaived(amount, particular, remarks));
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", lookupClient.getValue().trim());
		mapParameters.put("entity_name", txtClient.getText().trim());
		mapParameters.put("proj_name", txtProjName.getText().trim());
		mapParameters.put("unit_desc", txtUnitDesc.getText().trim());
		mapParameters.put("request_no", txtRequestNo.getText().trim());
		mapParameters.put("request_date", dteRequest.getDate());
		mapParameters.put("request_by", txtRequestBy.getText().trim());
		mapParameters.put("remarks", txtRequestRemarks.getText().trim());
		mapParameters.put("listPmtsMade", listPmtsMade);
		mapParameters.put("listPmtsWaived", listPmtsWaived);

		mapParameters.put("request_no", txtRequestNo.getText().trim());*/
		
		Map<String, Object> mapRequest = new HashMap<String, Object>();
		mapRequest.put("request_no", txtRequestNo.getText().trim());
		mapRequest.put("request_type", "Waive Penalty");

		//FncReport.generateReport("/Reports/rptWaivePenalty.jasper", "Waive Penalty", mapParameters);
		
		//Map<String, Object> mapParameters = new HashMap<String, Object>();
		FncReport.generateReport("/Reports/rptWaivePenalty2.jasper", String.format("Waive Penalty"), mapRequest);
		
	}

	public boolean toSave(){
		if(txtRequestBy.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input request by", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtRequestRemarks.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input remarks", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("New")){
			newWP();
		}

		if(actionCommand.equals("Save")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Save Request?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION){
					_WaivePenalty.saveWP(modelPmtsMade, _WaivePenalty.getRequestNo() ,lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), Integer.valueOf(txtSeqNo.getText()), txtRequestBy.getText(), txtRequestRemarks.getText());

					clearWP();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Request saved", "Save", JOptionPane.INFORMATION_MESSAGE, null);
				}
			}
		}

		if(actionCommand.equals("Cancel Request")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Cancel Request?", "Cancel Request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION){
				cancelWP(txtRequestNo.getText().trim());
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Request cancelled", "Save", JOptionPane.INFORMATION_MESSAGE, null);
			}
		}

		if(actionCommand.equals("Search")){
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null , "Request", _WaivePenalty.sqlSearch(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			FncSystem.out("Display sql for Search", _WaivePenalty.sqlSearch());

			Object [] data = dlg.getReturnDataSet();

			if(data != null){
				String request_no = (String) data[0];
				String entity_name = (String) data[1];
				String request_status = (String) data[2];
				Date request_date = (Date) data[3];
				String entity_id = (String) data[4];
				String proj_id = (String) data[5];
				String unit_id = (String) data[6];
				Integer seq_no = (Integer) data[7];
				String request_by = (String) data[8];
				String request_remarks = (String) data[9];

				txtRequestNo.setText(request_no);
				txtRequestStatus.setText(request_status);
				dteRequest.setDate(request_date);
				txtRequestBy.setText(request_by);
				txtRequestRemarks.setText(request_remarks);
				lookupClient.setValue(entity_id);
				txtClient.setText(entity_name);
				txtProjID.setText(proj_id);
				txtUnitID.setText(unit_id);
				txtSeqNo.setText(String.valueOf(seq_no));

				displayClientDetails(entity_id, proj_id, unit_id, seq_no);

				_WaivePenalty.displayPaymentsMade(modelPmtsMade, entity_id, proj_id, unit_id, seq_no, request_no);
				scrollPaymentsMade.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPaymentsMade.getRowCount())));
				tblPaymentsMade.packAll();

				_WaivePenalty.displayPaymentsWaived(modelPmtsWaived, request_no, modelPmtsWaivedTotal);
				scrollPaymentsWaived.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPaymentsWaived.getRowCount())));
				scrollPWTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblPaymentsWaived.getRowCount())));
				tblPaymentsWaived.packAll();

				txtRequestBy.setEditable(false);
				txtRequestRemarks.setEditable(false);

				if(request_status.equals("ACTIVE")){
					//btnState(false, false, true, false, true, true);
					btnState(false, false, true, false, true, true, true);
				}else{
					//btnState(false, false, false, false, true, false);
					btnState(false, false, false, false, true, false, true);
				}
			}
		}

		if(actionCommand.equals("Clear")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to clear fields?", "Clear", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION){
				clearWP();
			}
		}

		if(actionCommand.equals("Preview")){
			previewWaivePenalty(txtRequestNo.getText().trim());

		}

		if(actionCommand.equals("Post")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to post request?", "Post", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION){
				_WaivePenalty.postWP(txtRequestNo.getText().trim());

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Request successfully posted", "Post", JOptionPane.INFORMATION_MESSAGE, null);
				clearWP();
			}
		}

	}
}
