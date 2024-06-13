package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncComponent;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelRB_Monitoring;
import tablemodel.modelRB_MonitoringDetails;

/**
 * @author Alvin Gonzales - 2015-03-12
 *
 */
public class pnlRB_Monitoring_v2 extends JPanel implements ActionListener, _GUI {

	private static final long serialVersionUID = -9021093053384956185L;

	//private JScrollPane scrollClients;
	//private _JTableMain tblClients;
	//private modelRB_Monitoring modelClients;
	//private JList rowHeaderClients;

	private JPanel pnlDetails;
	private JScrollPane scrollDetails;
	private _JTableMain tblDetails;
	private modelRB_MonitoringDetails modelDetails;
	private JList rowHeaderDetails;

	private JPanel pnlDetailsCenter;

	private JPanel pnlBatchNo;
	private JLabel lblBatchNo;
	private _JXTextField txtBatchNo;

	private JPanel pnlPreparedBy;
	private JLabel lblPreparedBy;
	private _JLookup lookupPreparedByID;
	private _JXTextField txtPreparedByName;

	private JPanel pnlDatePreparedSent;
	private JPanel pnlDatePrepared;
	private JLabel lblDatePrepared;
	private _JDateChooser datePrepared;
	private JPanel pnlDateSent;
	private JLabel lblDateSent;
	private _JDateChooser dateSent;

	private JPanel pnlMailedThru;
	private JLabel lblMailedThru;
	private _JLookup lookupMailedThruID;
	private _JXTextField txtMailedThruName;

	private JPanel pnlBillingDueDefaultDate;
	private JPanel pnlBillingDueDate;
	private JLabel lblBillingDueDate;
	private _JDateChooser dateBillingDueDate;
	private JPanel pnlDefaultDate;
	private JLabel lblDefaultDate;
	private _JDateChooser dateDefaultDate;

	private JPanel pnlReceivedBy;
	private JLabel lblReceivedBy;
	private _JXTextField txtReceivedBy;

	private JPanel pnlRelationtoClient;
	private JLabel lblRelationtoClient;
	private _JXTextField txtRelationtoClient;

	private JPanel pnlRTSReason;
	private JLabel lblRTSReason;
	private _JLookup lookupRTSReasonID;
	private _JXTextField txtRTSReasonName;

	private JPanel pnlRemarks;
	private JLabel lblRemarks;
	private _JXTextField txtRemarks;

	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	private JButton btnView;

	private  JTabbedPane tabMonitoring;

	private JPanel pnlPerBatch;

	private JScrollPane scrollActiveClients;
	private  modelRB_Monitoring modelActiveClients;
	private _JTableMain tblActiveClients;
	private JList rowHeaderActiveClients;

	private JScrollPane scrollCancelledClients;
	private modelRB_Monitoring modelCancelledClients;
	private _JTableMain tblCancelledClients;
	private JList rowHeaderCancelledClients;

	private JPanel pnlPerAccount;
	private _JLookup lookupBatchNo;
	private JPanel pnLabel;
	private JLabel lblClientID;
	private _JLookup lookupClientID;
	private JScrollPane scrollPerClients;
	private modelRB_Monitoring modelPerClients;
	private _JTableMain tblPerClients;
	private JList rowHeaderPerClients;
	private String notice_id;
	private JButton btnDelete;
	private String entityID;
	private String projID;
	private String pblID;
	private Integer seqNo;

	private ArrayList<String> entityIDlist;

	private JLabel lblRTSDate;

	private _JDateChooser dateRTSDate;


	public pnlRB_Monitoring_v2() {
		initGUI();
	}

	public pnlRB_Monitoring_v2(String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean cancel) {
		initGUI();
		//displayDetails(entity_id, proj_id, pbl_id, seq_no);
		selectFromCARD(entity_id, proj_id, pbl_id, seq_no);
		displayClient(entity_id, proj_id, pbl_id, seq_no);
		tabMonitoring.setSelectedIndex(1);
		setFocusName(entity_id, proj_id, pbl_id, seq_no,cancel);
		if (ifPosition() || UserInfo.ADMIN) {
			btnNavigation(true, true, true, false, false);
		}else{
			btnNavigation(false, true, false, false, false);
		}

	}

	public pnlRB_Monitoring_v2(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		initGUI();
		//displayDetails(entity_id, proj_id, pbl_id, seq_no);


		tabMonitoring.setSelectedIndex(1);
		selectFromCARD(entity_id, proj_id, pbl_id, seq_no);
		displayClient(entity_id, proj_id, pbl_id, seq_no);
		displayDetails(entity_id, proj_id, pbl_id, seq_no);

		if (ifPosition() || UserInfo.ADMIN) {
			btnNavigation(true, true, true, false, false);
		}else{
			btnNavigation(false, true, false, false, false);
		}


	}

	public pnlRB_Monitoring_v2(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlRB_Monitoring_v2(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlRB_Monitoring_v2(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {

		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(800, 600));
		{

			tabMonitoring = new  JTabbedPane();
			this.add(tabMonitoring, BorderLayout.CENTER);
			tabMonitoring.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					if (tabMonitoring.getSelectedIndex() == 0) {
					}
				}
			});
			{
				pnlPerBatch = new JPanel(new BorderLayout(5, 5));
				tabMonitoring.addTab("  Per Batch  ", null, pnlPerBatch, null);
				{
					{
						JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
						pnlPerBatch.add(pnlNorth, BorderLayout.NORTH);
						{
							{
								JPanel pnlNorth1 = new JPanel(new BorderLayout(3, 3));
								pnlNorth.add(pnlNorth1, BorderLayout.NORTH);
							}
							JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
							pnlNorth.add(pnlEast,BorderLayout.EAST);
							pnlEast.setPreferredSize(new Dimension(380, 20));

							{
								pnLabel = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlEast.add(pnLabel, BorderLayout.WEST);
								{
									lblBatchNo = new JLabel("Batch No.");
									pnLabel.add(lblBatchNo);
								}

								JPanel pnlAction = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlEast.add(pnlAction, BorderLayout.CENTER);
								{
									lookupBatchNo = new _JLookup("Search", "Search", 0) ;  /// XXX lookupBatchNo 
									pnlAction.add(lookupBatchNo,BorderLayout.CENTER);
									lookupBatchNo.setReturnColumn(0);
									lookupBatchNo.setPreferredSize(new Dimension(60, 20));
									lookupBatchNo.setLookupSQL(_RegularBillingAndNotices.getPreviewBatchNoMonitoring());
									lookupBatchNo.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent e) {
											Object[] data = ((_JLookup)e.getSource()).getDataSet();

											if (data != null) {
												String batch_no = data[0].toString();
												displayBatch(batch_no);

												displayDetailsBatch(batch_no);
												displayNoticeDetailsBatch(batch_no);

												tblActiveClients.changeSelection(0, 0, false, false);
												if (ifPosition() || UserInfo.ADMIN) {
													btnNavigation(true, true, true, false, false);
												}else{
													btnNavigation(false, true, false, false, false);
												}

												if (dateSent.getDate() != null) {
													btnDelete.setEnabled(false);
												}

											}
										}
									});
								}
							}
						}
					}

					scrollActiveClients = new JScrollPane();
					pnlPerBatch.add(scrollActiveClients, BorderLayout.CENTER);
					scrollActiveClients.setBorder(components.JTBorderFactory.createTitleBorder("Clients"));
					scrollActiveClients.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollActiveClients.setPreferredSize(new Dimension(590, 200));
					scrollActiveClients.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							if(tblActiveClients.isEnabled()){
								tblActiveClients.clearSelection();
								//clearFields();
							}
						}
					});
					{
						modelActiveClients = new modelRB_Monitoring();
						modelActiveClients.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								if(e.getType() == 1){
									((DefaultListModel)rowHeaderActiveClients.getModel()).addElement(modelActiveClients.getRowCount());
									if(modelActiveClients.getRowCount() == 0){
										rowHeaderActiveClients.setModel(new DefaultListModel());
									}
								}
							}
						});
						modelActiveClients.setEditable(true);
						tblActiveClients = new _JTableMain(modelActiveClients);
						scrollActiveClients.setViewportView(tblActiveClients);
						tblActiveClients.hideColumns("Client ID", "Project ID", "PBL ID", "Seq.");
						tblActiveClients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								if(!arg0.getValueIsAdjusting()){//XXX Clients
									// clearFields();
									try {
										try {
											int row = tblActiveClients.convertRowIndexToModel(tblActiveClients.getSelectedRow());
											String batchno = (String) modelActiveClients.getValueAt(row, 1);
											String entity_id = (String) modelActiveClients.getValueAt(row, 2);
											String proj_id = (String) modelActiveClients.getValueAt(row, 4);
											String pbl_id = (String) modelActiveClients.getValueAt(row, 6);
											Integer seq_no = (Integer) modelActiveClients.getValueAt(row, 7);
											displayDetailsBatch(entity_id, proj_id, pbl_id, seq_no,batchno);

										} catch (ArrayIndexOutOfBoundsException e) { }
									} catch (IndexOutOfBoundsException e) {
									}
								}
							}
						});
					}
					{
						rowHeaderActiveClients = tblActiveClients.getRowHeader();
						rowHeaderActiveClients.setModel(new DefaultListModel());
						scrollActiveClients.setRowHeaderView(rowHeaderActiveClients);
						scrollActiveClients.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						scrollActiveClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblActiveClients.getRowCount())));

					}
					tblActiveClients.hideColumns("Batch No.");

				}
				{
					pnlPerAccount = new JPanel(new BorderLayout(5, 5));
					tabMonitoring.addTab("  Per Accounts  ", null, pnlPerAccount, null);
					{
						{
							JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
							pnlPerAccount.add(pnlNorth, BorderLayout.NORTH);
							{
								{
									JPanel pnlNorth1 = new JPanel(new BorderLayout(3, 3));
									pnlNorth.add(pnlNorth1, BorderLayout.NORTH);
								}
								JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
								pnlNorth.add(pnlEast,BorderLayout.EAST);
								pnlEast.setPreferredSize(new Dimension(380, 20));

								{
									pnLabel = new JPanel(new GridLayout(1, 1, 3, 3));
									pnlEast.add(pnLabel, BorderLayout.WEST);
									{
										lblClientID = new JLabel("Client ID");
										pnLabel.add(lblClientID);
									}

									JPanel pnlAction = new JPanel(new GridLayout(1, 1, 3, 3));
									pnlEast.add(pnlAction, BorderLayout.CENTER);
									{
										lookupClientID = new _JLookup("Search", "Search", 0) ;  /// XXX lookupBatchNo 
										pnlAction.add(lookupClientID,BorderLayout.CENTER);
										lookupClientID.setReturnColumn(0);
										lookupClientID.setPreferredSize(new Dimension(60, 20));
										lookupClientID.setLookupSQL(_RegularBillingAndNotices.getPreviewClientMonitoring());
										lookupClientID.addLookupListener(new LookupListener() {

											@Override
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup)e.getSource()).getDataSet();

												if (data != null) {
													String entityID = data[0].toString();
													String projID = data[5].toString();
													String pblID = data[6].toString();
													seqNo = (Integer) data[7];

													displayClient(entityID, projID, pblID, seqNo);

													displayDetails(entityID, projID, pblID, seqNo);
													if (ifPosition() || UserInfo.ADMIN) {
														btnNavigation(true, true, true, false, false);


													}else{
														btnNavigation(false, true, false, false, false);
													}
												}
											}
										});
									}
								}
							}
						}


						scrollPerClients = new JScrollPane();
						pnlPerAccount.add(scrollPerClients, BorderLayout.CENTER);
						scrollPerClients.setBorder(components.JTBorderFactory.createTitleBorder("Clients"));
						scrollPerClients.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollPerClients.setPreferredSize(new Dimension(590, 200));
						scrollPerClients.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								if(tblActiveClients.isEnabled()){
									tblActiveClients.clearSelection();
									//clearFields();
								}
							}
						});
						{
							modelPerClients = new modelRB_Monitoring();
							modelPerClients.setEditable(false);
							modelPerClients.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == 1){
										((DefaultListModel)rowHeaderPerClients.getModel()).addElement(modelPerClients.getRowCount());
										if(modelPerClients.getRowCount() == 0){
											rowHeaderPerClients.setModel(new DefaultListModel());
										}
									}
								}
							});


							tblPerClients = new _JTableMain(modelPerClients);
							scrollPerClients.setViewportView(tblPerClients);
							tblPerClients.hideColumns("Client ID", "Project ID", "PBL ID", "Seq.");
							tblPerClients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent arg0) {
									if(!arg0.getValueIsAdjusting()){//XXX Clients
										clearFields();
										try {
											try {
												//int row = tblPerClients.getSelectedRow();
												int row = tblPerClients.convertRowIndexToModel(tblPerClients.getSelectedRow());


												String entity_id = (String) modelPerClients.getValueAt(row, 2);
												String proj_id = (String) modelPerClients.getValueAt(row, 4);
												String pbl_id = (String) modelPerClients.getValueAt(row, 6);
												Integer seq_no = (Integer) modelPerClients.getValueAt(row, 7);


												displayDetails(entity_id, proj_id, pbl_id, seq_no);



											} catch (ArrayIndexOutOfBoundsException e) { }
										} catch (IndexOutOfBoundsException e) {
										}
									}
								}
							});
						}
						{
							rowHeaderPerClients = tblPerClients.getRowHeader();
							rowHeaderPerClients.setModel(new DefaultListModel());
							scrollPerClients.setRowHeaderView(rowHeaderPerClients);
							scrollPerClients.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							scrollPerClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerClients.getRowCount())));
						}

						tblPerClients.hideColumns("Batch No.");
					}
				}
			}


			pnlDetails = new JPanel(new BorderLayout(5, 5));
			this.add(pnlDetails, BorderLayout.SOUTH);
			pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
			pnlDetails.setPreferredSize(new java.awt.Dimension(810, 280));
			{
				scrollDetails = new JScrollPane();
				pnlDetails.add(scrollDetails, BorderLayout.CENTER);
				scrollDetails.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollDetails.setPreferredSize(new Dimension(238, 352));
				scrollDetails.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(tblDetails.isEnabled()){
							tblDetails.clearSelection();
							clearFields();
						}
					}
				});
				{
					modelDetails = new modelRB_MonitoringDetails();
					modelDetails.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == 1){
								((DefaultListModel)rowHeaderDetails.getModel()).addElement(modelDetails.getRowCount());
								if(modelDetails.getRowCount() == 0){
									rowHeaderDetails.setModel(new DefaultListModel());
								}
							}
						}
					});

					tblDetails = new _JTableMain(modelDetails);
					scrollDetails.setViewportView(tblDetails);
					//tblDetails.hideColumns("Stage ID", "Stage", "Notice ID","Rec ID");
					tblDetails.hideColumns("Stage ID", "Stage", "Notice ID","Rec ID");
					tblDetails.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if(!e.getValueIsAdjusting()){//XXX Details
								try {
									int row = tblDetails.getSelectedRow();

									Integer rec_id = (Integer) modelDetails.getValueAt(row, 4);
									String notice_id = (String) modelDetails.getValueAt(row, 2);
									/*if (tabMonitoring.getSelectedIndex() == 0 ) {
										String batchNo = lookupBatchNo.getValue();
										displayNoticeDetailsBatch(batchNo);

									}else{
										Integer rec_id = (Integer) modelDetails.getValueAt(row, 4);	
										displayNoticeDetails(rec_id);	
									}
									 */
									displayNoticeDetails(rec_id,notice_id);	


									pgSelect db = new pgSelect();

									db.select("select datesent from rf_client_notices where notice_id = '"+notice_id+"' and rec_id = "+rec_id+ " and status_id = 'A'");

									if (db.isNotNull()) {
										if (db.Result[0][0] == null) {
											btnDelete.setEnabled(true);
										}else{
											btnDelete.setEnabled(false);
										}

									}

								} catch (ArrayIndexOutOfBoundsException e1) {}
							}
						}
					});
				}
				{
					rowHeaderDetails = tblDetails.getRowHeader();
					rowHeaderDetails.setModel(new DefaultListModel());
					scrollDetails.setRowHeaderView(rowHeaderDetails);
					scrollDetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					scrollDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDetails.getRowCount())));
				}
			}
			{
				pnlDetailsCenter = new JPanel(new BorderLayout(3, 3));
				pnlDetails.add(pnlDetailsCenter, BorderLayout.EAST);
				pnlDetailsCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlDetailsCenter.setPreferredSize(new Dimension(524, 237));
				{
					JPanel pnlDetailsCenterCenter = new JPanel(new GridLayout(9, 1, 3, 3));
					pnlDetailsCenter.add(pnlDetailsCenterCenter, BorderLayout.CENTER);
					{
						pnlBatchNo = new JPanel(new BorderLayout(3, 3));
						pnlDetailsCenterCenter.add(pnlBatchNo);
						{
							lblBatchNo = new JLabel("Batch No.");
							pnlBatchNo.add(lblBatchNo, BorderLayout.WEST);
							lblBatchNo.setPreferredSize(new Dimension(100, 32));
						}
						{
							JPanel pnlSubBatchNo = new JPanel(new BorderLayout());
							pnlBatchNo.add(pnlSubBatchNo, BorderLayout.CENTER);
							{
								txtBatchNo = new _JXTextField();
								pnlSubBatchNo.add(txtBatchNo, BorderLayout.WEST);
								txtBatchNo.setPreferredSize(new Dimension(150, 32));
								txtBatchNo.setHorizontalAlignment(_JXTextField.CENTER);
							}
						}
					}
					{
						pnlPreparedBy = new JPanel(new BorderLayout(3, 3));
						pnlDetailsCenterCenter.add(pnlPreparedBy);
						{
							lblPreparedBy = new JLabel("Prepared By");
							pnlPreparedBy.add(lblPreparedBy, BorderLayout.WEST);
							lblPreparedBy.setPreferredSize(new Dimension(100, 35));
						}
						{
							JPanel jPanel1 = new JPanel(new BorderLayout(3, 3));
							pnlPreparedBy.add(jPanel1, BorderLayout.CENTER);
							{
								lookupPreparedByID = new _JLookup(null, "Prepared By");
								jPanel1.add(lookupPreparedByID, BorderLayout.WEST);
								lookupPreparedByID.setPreferredSize(new Dimension(83, 35));
								lookupPreparedByID.setEditable(false);
							}
							{
								txtPreparedByName = new _JXTextField();
								jPanel1.add(txtPreparedByName, BorderLayout.CENTER);
								txtPreparedByName.setEditable(false);
							}
						}
					}
					{
						pnlDatePreparedSent = new JPanel(new GridLayout(1, 2));
						pnlDetailsCenterCenter.add(pnlDatePreparedSent);
						{
							pnlDatePrepared = new JPanel(new BorderLayout(3, 3));
							pnlDatePreparedSent.add(pnlDatePrepared);
							{
								lblDatePrepared = new JLabel("Date Prepared");
								pnlDatePrepared.add(lblDatePrepared, BorderLayout.WEST);
								lblDatePrepared.setPreferredSize(new Dimension(100, 22));
							}
							{
								datePrepared = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlDatePrepared.add(datePrepared, BorderLayout.CENTER);
							}
						}
						{
							pnlDateSent = new JPanel(new BorderLayout(3, 3));
							pnlDatePreparedSent.add(pnlDateSent);
							{
								lblDateSent = new JLabel("Date Sent ", JLabel.TRAILING);
								pnlDateSent.add(lblDateSent, BorderLayout.WEST);
								lblDateSent.setPreferredSize(new Dimension(100, 22));
							}
							{
								dateSent = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlDateSent.add(dateSent, BorderLayout.CENTER);
							}
						}
					}
					{
						pnlMailedThru = new JPanel(new BorderLayout(3, 3));
						pnlDetailsCenterCenter.add(pnlMailedThru);
						{
							lblMailedThru = new JLabel("Mailed Thru");
							pnlMailedThru.add(lblMailedThru, BorderLayout.WEST);
							lblMailedThru.setPreferredSize(new Dimension(100, 35));
						}
						{
							JPanel jPanel1 = new JPanel(new BorderLayout(3, 3));
							pnlMailedThru.add(jPanel1, BorderLayout.CENTER);
							{
								lookupMailedThruID = new _JLookup(null, "Mailed Thru");
								jPanel1.add(lookupMailedThruID, BorderLayout.WEST);
								lookupMailedThruID.setPreferredSize(new Dimension(83, 35));
							}
							{
								txtMailedThruName = new _JXTextField();
								jPanel1.add(txtMailedThruName, BorderLayout.CENTER);
							}
						}
					}
					{
						pnlBillingDueDefaultDate = new JPanel(new GridLayout(1, 2));
						pnlDetailsCenterCenter.add(pnlBillingDueDefaultDate);
						{
							pnlBillingDueDate = new JPanel(new BorderLayout(3, 3));
							pnlBillingDueDefaultDate.add(pnlBillingDueDate);
							{
								lblBillingDueDate = new JLabel("Billing DueDate");
								pnlBillingDueDate.add(lblBillingDueDate, BorderLayout.WEST);
								lblBillingDueDate.setPreferredSize(new Dimension(100, 22));
							}
							{
								dateBillingDueDate = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlBillingDueDate.add(dateBillingDueDate, BorderLayout.CENTER);
							}
						}
						{
							pnlDefaultDate = new JPanel(new BorderLayout(3, 3));
							pnlBillingDueDefaultDate.add(pnlDefaultDate);
							{
								lblDefaultDate = new JLabel("Default Date ", JLabel.TRAILING);
								pnlDefaultDate.add(lblDefaultDate, BorderLayout.WEST);
								lblDefaultDate.setPreferredSize(new Dimension(100, 22));
							}
							{
								dateDefaultDate = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlDefaultDate.add(dateDefaultDate, BorderLayout.CENTER);
							}
						}
					}
					{
						pnlReceivedBy = new JPanel(new BorderLayout(3, 3));
						pnlDetailsCenterCenter.add(pnlReceivedBy);
						{
							lblReceivedBy = new JLabel("Received By");
							pnlReceivedBy.add(lblReceivedBy, BorderLayout.WEST);
							lblReceivedBy.setPreferredSize(new Dimension(100, 22));
						}
						{
							txtReceivedBy = new _JXTextField();
							pnlReceivedBy.add(txtReceivedBy, BorderLayout.CENTER);
							txtReceivedBy.setEditable(true);
						}
					}
					{
						pnlRelationtoClient = new JPanel(new BorderLayout(3, 3));
						pnlDetailsCenterCenter.add(pnlRelationtoClient);
						{
							lblRelationtoClient = new JLabel("Relation to Client");
							pnlRelationtoClient.add(lblRelationtoClient, BorderLayout.WEST);
							lblRelationtoClient.setPreferredSize(new Dimension(120, 22));
						}
						{
							txtRelationtoClient = new _JXTextField();
							pnlRelationtoClient.add(txtRelationtoClient, BorderLayout.CENTER);
							txtRelationtoClient.setEditable(true);
						}
					}
//					{
//						pnlRTSReason = new JPanel(new BorderLayout(3, 3));
//						pnlDetailsCenterCenter.add(pnlRTSReason);
//						{
//							lblRTSReason = new JLabel("RTS Reason");
//							pnlRTSReason.add(lblRTSReason, BorderLayout.WEST);
//							lblRTSReason.setPreferredSize(new Dimension(100, 35));
//						}
//						{
//							JPanel jPanel1 = new JPanel(new BorderLayout(3, 3));
//							pnlRTSReason.add(jPanel1, BorderLayout.CENTER);
//							{
//								lookupRTSReasonID = new _JLookup(null, "RTS Reason");
//								jPanel1.add(lookupRTSReasonID, BorderLayout.WEST);
//								lookupRTSReasonID.setPreferredSize(new Dimension(40, 35));
//								
//										
//									}
//								});
//								
//								
//								
//							}
//							{
//								txtRTSReasonName = new _JXTextField();
//								jPanel1.add(txtRTSReasonName, BorderLayout.CENTER);
//							}
//						
//						}
//						
//					}	
					
					// Mondified by: Monique Boriga; 10/20/2021 For tagging of RTS reason and date 
					{
						pnlRTSReason = new JPanel(new GridLayout(1, 2));
						pnlDetailsCenterCenter.add(pnlRTSReason);
					
						{
							JPanel jPanel1 = new JPanel(new BorderLayout(3, 3));
							pnlRTSReason.add(jPanel1);
							{
								lblRTSReason = new JLabel("RTS Reason");
								jPanel1.add(lblRTSReason, BorderLayout.WEST);
								lblRTSReason.setPreferredSize(new Dimension(100, 35));
							}
							{
								lookupRTSReasonID = new _JLookup(null, "RTS ID");
								jPanel1.add(lookupRTSReasonID, BorderLayout.CENTER);
								//lookupRTSReasonID.setPreferredSize(new Dimension(40, 35));
								
								//Added by Monique Boriga; For tagging of RTS notices 
								
								lookupRTSReasonID.setLookupSQL(_RegularBillingAndNotices.getRTSReason());
								lookupRTSReasonID.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup)e.getSource()).getDataSet();
										
										if (data != null) {
											
											String rts_id = data[0].toString();
											String rts_name = data[1].toString();
										
											lookupRTSReasonID.setValue(rts_id);
											txtRTSReasonName.setText(rts_name);
											
										}
										 
									}
								});
							}
							{
								txtRTSReasonName = new _JXTextField();
								jPanel1.add(txtRTSReasonName, BorderLayout.EAST);
								txtRTSReasonName.setPreferredSize(new Dimension(115, 35));
								
							}
						
						}
						{
							JPanel jPanel2 = new JPanel(new BorderLayout(3, 3));
							pnlRTSReason.add(jPanel2);
							
							{
								lblRTSDate = new JLabel("       RTS Date");
								jPanel2.add(lblRTSDate, BorderLayout.WEST); 
								lblRTSDate.setPreferredSize(new Dimension(100, 35));
							}
							{
								dateRTSDate = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								jPanel2.add(dateRTSDate, BorderLayout.CENTER); 
							}
							
						}
					}
				
					{
						pnlRemarks = new JPanel(new BorderLayout(3, 3));
						pnlDetailsCenterCenter.add(pnlRemarks);
						{
							lblRemarks = new JLabel("Remarks");
							pnlRemarks.add(lblRemarks, BorderLayout.WEST);
							lblRemarks.setPreferredSize(new Dimension(100, 22));
						}
						{
							txtRemarks = new _JXTextField();
							pnlRemarks.add(txtRemarks, BorderLayout.CENTER);
							txtRemarks.setEditable(true);
						}
					}
				}
				{
					JPanel pnlDetailsCenterSouth = new JPanel(new BorderLayout());
					pnlDetailsCenter.add(pnlDetailsCenterSouth, BorderLayout.SOUTH);
					pnlDetailsCenterSouth.setPreferredSize(new Dimension(524, 30));
					{
						JPanel pnlNavigation = new JPanel(new GridLayout(1, 5, 5, 5));
						pnlDetailsCenterSouth.add(pnlNavigation, BorderLayout.EAST);
						pnlNavigation.setPreferredSize(new Dimension(350, 0));
						{
							btnDelete = new JButton("Delete");
							pnlNavigation.add(btnDelete);
							btnDelete.addActionListener(this);
						}
						{
							btnView = new JButton("View");
							pnlNavigation.add(btnView);
							btnView.addActionListener(this);
						}
						{
							btnEdit = new JButton("Edit");
							pnlNavigation.add(btnEdit);
							btnEdit.addActionListener(this);
						}
						{
							btnSave = new JButton("Save");
							pnlNavigation.add(btnSave);
							btnSave.addActionListener(this);
						}
						{
							btnCancel = new JButton("Cancel");
							pnlNavigation.add(btnCancel);
							btnCancel.addActionListener(this);
						}
					}
				}
			}
		}

		refreshFields(false);
		btnNavigation(false,false,false, false, false);
	}


	private void displayBatch(String batchno) {
		rowHeaderActiveClients.setModel(new DefaultListModel());
		_RegularBillingAndNotices.displayMonitoringBatch(modelActiveClients, batchno);
		tblActiveClients.packAll();
		scrollActiveClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblActiveClients.getRowCount())));
	}

	private void displayClient(String entityID,String projID, String pblID, Integer seqNo) {
		rowHeaderPerClients.setModel(new DefaultListModel());
		_RegularBillingAndNotices.displayPerClient(modelPerClients, entityID,projID,pblID,seqNo);
		tblPerClients.packAll();
		scrollPerClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerClients.getRowCount())));
	}

	private void displayDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		rowHeaderDetails.setModel(new DefaultListModel());
		_RegularBillingAndNotices.displayMonitoringDetails(modelDetails, entity_id, proj_id, pbl_id, seq_no);
		scrollDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDetails.getRowCount())));
		tblDetails.packAll();
	}
	private void displayDetailsBatch(String batchno) {
		rowHeaderDetails.setModel(new DefaultListModel());
		_RegularBillingAndNotices.displayMonitoringDetailsBatch(modelDetails, batchno);
		scrollDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDetails.getRowCount())));
		tblDetails.packAll();
	}

	private void displayDetailsBatch(String entity_id, String proj_id, String pbl_id, Integer seq_no,String batchno) {
		rowHeaderDetails.setModel(new DefaultListModel());
		_RegularBillingAndNotices.displayMonitoringDetailsBatch(modelDetails, entity_id, proj_id, pbl_id, seq_no,batchno);
		scrollDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDetails.getRowCount())));
		tblDetails.packAll();
	}

	private void displayActiveClients() {
		rowHeaderActiveClients.setModel(new DefaultListModel());
		_RegularBillingAndNotices.displayMonitoring(modelActiveClients);
		scrollActiveClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblActiveClients.getRowCount())));
		tblActiveClients.packAll();
	}

	private void displayCancelledClients() {
		rowHeaderCancelledClients.setModel(new DefaultListModel());
		_RegularBillingAndNotices.displayMonitoringCancelled(modelCancelledClients);
		scrollCancelledClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelledClients.getRowCount())));
		tblCancelledClients.packAll();
	}

	private void displayNoticeDetails(Integer rec_id, String notice_id) {
		Object[] data = _RegularBillingAndNotices.getNoticeDetails(rec_id, notice_id);

		String batch_no = (String) data[0];
		String prepared_id = (String) data[1];
		String prepared_by = (String) data[2];
		Date date_prepared = (Date) data[3];
		Date date_sent = (Date) data[4];
		String mailed_thru = (String) data[5];
		Date due_date = (Date) data[6];
		Date default_date = (Date) data[7];
		String received_by = (String) data[8];
		String relation_to_buyer = (String) data[9];
		String rts_reason_id = (String) data[10];
		String rts_reason = (String) data[11];
		Date rts_date = (Date) data[12]; //Added by: Monique Boriga 10-21-2021
		String remarks = (String) data[13];

		txtBatchNo.setText(batch_no);
		lookupPreparedByID.setValue(prepared_id);
		txtPreparedByName.setText(prepared_by);
		datePrepared.setDate(date_prepared);
		dateSent.setDate(date_sent);
		txtMailedThruName.setText(mailed_thru);
		dateBillingDueDate.setDate(due_date);
		dateDefaultDate.setDate(default_date);
		txtReceivedBy.setText(received_by);
		txtRelationtoClient.setText(relation_to_buyer);
		lookupRTSReasonID.setValue(rts_reason_id);
		txtRTSReasonName.setText(rts_reason);
		dateRTSDate.setDate(rts_date); //Added by: Monique Boriga 10-21-2021
		txtRemarks.setText(remarks);
	}

	private void displayNoticeDetailsBatch(String batchNo) {
		Object[] data = _RegularBillingAndNotices.getNoticeDetailsBatch(batchNo);

		String batch_no = (String) data[0];
		String prepared_id = (String) data[1];
		String prepared_by = (String) data[2];
		Date date_prepared = (Date) data[3];
		Date date_sent = (Date) data[4];
		String mailed_thru = (String) data[5];
		Date due_date = (Date) data[6];
		Date default_date = (Date) data[7];
		String received_by = (String) data[8];
		String relation_to_buyer = (String) data[9];
		String rts_reason_id = (String) data[10];
		String rts_reason = (String) data[11];
		String remarks = (String) data[12];

		txtBatchNo.setText(batch_no);
		lookupPreparedByID.setValue(prepared_id);
		txtPreparedByName.setText(prepared_by);
		datePrepared.setDate(date_prepared);
		dateSent.setDate(date_sent);
		txtMailedThruName.setText(mailed_thru);
		dateBillingDueDate.setDate(due_date);
		dateDefaultDate.setDate(default_date);
		txtReceivedBy.setText(received_by);
		txtRelationtoClient.setText(relation_to_buyer);
		lookupRTSReasonID.setValue(rts_reason_id);
		txtRTSReasonName.setText(rts_reason);
		txtRemarks.setText(remarks);
	}

	private void selectFromCARD(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		System.out.printf("*8888  %s, %s, %s, %s%n%n", entity_id, proj_id, pbl_id, seq_no);

		for(int x=0; x < modelPerClients.getRowCount(); x++){
			String table_entity_id = (String) modelPerClients.getValueAt(x, 2);
			//String table_entity_name = (String) modelActiveClients.getValueAt(x, 1);
			String table_proj_id = (String) modelPerClients.getValueAt(x, 4);
			String table_pbl_id = (String) modelPerClients.getValueAt(x, 6);
			Integer table_seq_no = (Integer) modelPerClients.getValueAt(x, 7);

			if(entity_id.equals(table_entity_id) && proj_id.equals(table_proj_id) && pbl_id.equals(table_pbl_id) && seq_no.equals(table_seq_no)){
				//System.out.printf("Client: %s (%s)%n", table_entity_name, x);
				tblPerClients.setColumnSelectionAllowed(false);
				tblPerClients.setCellSelectionEnabled(false);
				tblPerClients.setRowSelectionAllowed(true);
				tblPerClients.addRowSelectionInterval(x, x);
			}
		}
	}

	private void btnNavigation(Boolean sDelete,Boolean sView,Boolean sEdit, Boolean sSave, Boolean sCancel) {
		btnDelete.setEnabled(sDelete);
		btnView.setEnabled(sView);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void refreshFields(Boolean enable) {
		FncComponent.setComponentsEnabled(pnlDetailsCenter, enable);
		dateBillingDueDate.setEnabled(false);
		dateDefaultDate.setEnabled(false);
	}

	public void clearFields() {
		lookupBatchNo.setValue("");

		txtBatchNo.setText("");
		lookupPreparedByID.setValue(null);
		txtPreparedByName.setText("");
		datePrepared.setDate(null);
		dateSent.setDate(null);
		lookupMailedThruID.setValue(null);
		txtMailedThruName.setText("");
		dateBillingDueDate.setDate(null);
		dateDefaultDate.setDate(null);
		txtReceivedBy.setText("");
		txtRelationtoClient.setText("");
		lookupRTSReasonID.setValue(null);
		txtRTSReasonName.setText("");
		txtRemarks.setText("");
		dateRTSDate.setDate(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX action
		String action = e.getActionCommand();

		if (e.getSource().equals(btnDelete)) {
			if (tabMonitoring.getSelectedIndex() == 0) {
				toDelete();
			}

			if (tabMonitoring.getSelectedIndex() == 1){
				toDeletePerNotice();
			}
		}


		if(action.equals("View")){
			Map<String, Object> mapParameters = new HashMap<String, Object>();

			String entity_id = "";
			String proj_id = "";
			String pbl_id = "";
			Integer seq_no = 0;
			String notice_id = "";
			String batch_no = "";

			if (tabMonitoring.getSelectedIndex() == 0) {
				batch_no = lookupBatchNo.getValue();
			}else{

				for (int i = 0; i < modelPerClients.getRowCount(); i++) {

					entity_id = modelPerClients.getValueAt(i, 2).toString();
					proj_id = modelPerClients.getValueAt(i, 4).toString();
					pbl_id = modelPerClients.getValueAt(i, 6).toString();
					seq_no = (Integer) modelPerClients.getValueAt(i, 7);

					notice_id = modelDetails.getValueAt(tblDetails.getSelectedRow(), 2).toString();

					batch_no = txtBatchNo.getText().toString();

				}
			}

			/*	if (notice_id.equals("04")) {
				String SQL = "SELECT _get_notices_under_noticeofcancellation('"+entity_id+"','"+proj_id+"','"+pbl_id+"',"+seq_no+",'"+notice_id+"') ";

				System.out.println("****"+ SQL);

				pgSelect db = new pgSelect();
				db.select(SQL);

				if(db.isNotNull()){ 

					mapParameters.put("batch_no", txtBatchNo.getText().toString());
					mapParameters.put("entity_id", entity_id);
					mapParameters.put("proj_id", proj_id);
					mapParameters.put("pbl_id", pbl_id);
					mapParameters.put("seq_no", seq_no);	

					System.out.println(db.Result[0][0]);
					System.out.println("REPORT"+" /Reports/"+db.Result[0][0]+"");
					FncReport.generateReport("/Reports/"+db.Result[0][0]+".jasper", "Notice of Cancellation",  "", mapParameters);	
				}
			}*/

			notice_id = modelDetails.getValueAt(tblDetails.getSelectedRow(), 2).toString();
			NoticePreview(notice_id, entity_id, proj_id, pbl_id, seqNo, batch_no, false);
		}

		if(action.equals("Edit")){
			if (tabMonitoring.getSelectedIndex() == 0 ) {
				tblActiveClients.setEnabled(false);
				tblDetails.setEnabled(false);

				refreshFields(true);
				btnNavigation(false,false,false, true, true);
				tabMonitoring.setEnabledAt(1, false);
				modelActiveClients.setEditable(true);
				tblActiveClients.setEnabled(true);
			}else{
				tblPerClients.setEnabled(false);
				tblDetails.setEnabled(false);

				refreshFields(true);
				btnNavigation(false,false,false, true, true);
				tabMonitoring.setEnabledAt(0, false);
			}
		}

		if(action.equals("Save")){

			if (tabMonitoring.getSelectedIndex() == 0) {
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					toSave();
				}
			}

			if (tabMonitoring.getSelectedIndex() == 1) {

				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					
					//Added by Monique Boriga 10-27-2021 for additional filter on saving RTS 
					String entity_id = (String) modelDetails.getValueAt(tblDetails.getSelectedRow(),7);
					String proj_id = (String) modelDetails.getValueAt(tblDetails.getSelectedRow(),8);
					String pbl_id = (String) modelDetails.getValueAt(tblDetails.getSelectedRow(),9);
					Integer seq_no = (Integer) modelDetails.getValueAt(tblDetails.getSelectedRow(),10);
					
					Integer rec_id = (Integer) modelDetails.getValueAt(tblDetails.getSelectedRow(), 4);
					String notice_id = (String) modelDetails.getValueAt(tblDetails.getSelectedRow(), 2); //Added by MDB 10-27-21
					String prepared_id = lookupPreparedByID.getValue();
					Date date_prepared = datePrepared.getDate();
					Date date_sent = dateSent.getDate();
					String mailed_thru = txtMailedThruName.getText();
					String received_by = txtReceivedBy.getText();
					String relation_to_buyer = txtRelationtoClient.getText();
					Date received_date = null;
					String rts_reason_id = lookupRTSReasonID.getValue();
					String rts_reason = txtRTSReasonName.getText();
					Date rts_date = dateRTSDate.getDate();  //Modified by: Monique Boriga 10/20/2021 
					String remarks = txtRemarks.getText();
					
					//Added by: Monique Boriga 10/21/2021 for tagging of creator of rts
					String rts_created_by = UserInfo.EmployeeCode;
					Date rts_date_created = FncGlobal.getDateToday();

//					_RegularBillingAndNotices.saveNoticeDetails(rec_id, notice_id, prepared_id, date_prepared, date_sent, mailed_thru, received_by, relation_to_buyer, received_date, rts_reason_id, rts_reason, rts_date, remarks, rts_created_by, rts_date_created);

					_RegularBillingAndNotices.saveNoticeDetails(rec_id, entity_id, proj_id, pbl_id, seq_no, notice_id, prepared_id, date_prepared, date_sent, mailed_thru, received_by, relation_to_buyer, received_date, rts_reason_id, rts_reason, rts_date, remarks, rts_created_by, rts_date_created);
					
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Notice has been updated.", action, JOptionPane.INFORMATION_MESSAGE);

					tblPerClients.setEnabled(true);
					tblDetails.setEnabled(true);

					refreshFields(false);
					btnNavigation(false,true, true, false, true);
				}
			}
			tabMonitoring.setEnabled(true);
		}

		if(action.equals("Cancel")){

			tblPerClients.setEnabled(true);
			tblActiveClients.setEnabled(true);
			tblDetails.setEnabled(true);

			refreshFields(false);
			clearFields();

			lookupBatchNo.setText("");

			rowHeaderActiveClients.setModel(new DefaultListModel());
			modelActiveClients.clear();
			scrollActiveClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblActiveClients.getRowCount())));
			rowHeaderPerClients.setModel(new DefaultListModel());
			modelPerClients.clear();
			scrollPerClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerClients.getRowCount())));

			rowHeaderDetails.setModel(new DefaultListModel());
			modelDetails.clear();
			scrollDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDetails.getRowCount())));
			btnNavigation(true,true, true, false, false);
		}
	}

	public void setFocusName(String entity_id, String proj_id, String pbl_id, Integer seq_no ,Boolean cancel){/*

		if (cancel) {
			for (int i = 0; i < modelActiveClients.getRowCount(); i++) {

				String batchno = (String) modelActiveClients.getValueAt(i, 1);
				String entityID = (String) modelActiveClients.getValueAt(i, 2);
				String projID = (String) modelActiveClients.getValueAt(i, 4);
				String pblID = (String) modelActiveClients.getValueAt(i, 6);
				Integer seqNO = (Integer) modelActiveClients.getValueAt(i, 7);

				System.out.println(" ****"+ entityID +"   "+(entityID.equals(entity_id) && projID.equals(proj_id) &&  pblID.equals(pbl_id) && seqNO.equals(seq_no)));	


				if ((entityID.equals(entity_id) && projID.equals(proj_id) &&  pblID.equals(pbl_id) && seqNO.equals(seq_no))) {

					System.out.println(" ****"+ modelCancelledClients.getValueAt(i, 0));
					tblCancelledClients.requestFocus();
					tblCancelledClients.changeSelection(i, 0, false, false);
					displayDetails(entity_id, proj_id, pbl_id, seq_no,batchno);
				}

			}
		}else{
			for (int i = 0; i < modelActiveClients.getRowCount(); i++) {
				String entityID = (String) modelActiveClients.getValueAt(i, 0);
				String projID = (String) modelActiveClients.getValueAt(i, 2);
				String pblID = (String) modelActiveClients.getValueAt(i, 4);
				Integer seqNO = (Integer) modelActiveClients.getValueAt(i, 5);
				System.out.println(" ****"+ entityID +"   "+(entityID.equals(entity_id) && projID.equals(proj_id) &&  pblID.equals(pbl_id) && seqNO.equals(seq_no)));	

				if ((entityID.equals(entity_id) && projID.equals(proj_id) &&  pblID.equals(pbl_id) && seqNO.equals(seq_no))) {

					System.out.println(" ****"+ modelActiveClients.getValueAt(i, 0));
					tblCancelledClients.requestFocus();
					tblCancelledClients.changeSelection(i, 0, false, false);
					displayDetails(entity_id, proj_id, pbl_id, seq_no);

				}
			}
		}
	 */

	}


	private void toSave(){
		if (tabMonitoring.getSelectedIndex() == 0) {
			String SQL = "";
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listprojID = new ArrayList<String>();
			ArrayList<String> listpblID = new ArrayList<String>();
			ArrayList<String> listSeqNo = new ArrayList<String>();

			for (int i = 0; i < modelActiveClients.getRowCount(); i++) {


				Boolean isSelected = (Boolean) modelActiveClients.getValueAt(i, 0);

				if (isSelected) {

					String entityID = (String) modelActiveClients.getValueAt(i, 2);
					projID = (String) modelActiveClients.getValueAt(i, 4);
					String pblID = (String) modelActiveClients.getValueAt(i, 6);
					String seqNo =  String.valueOf(modelActiveClients.getValueAt(i, 7));

					listEntityID.add(String.format("'%s'", entityID));
					listprojID.add(String.format("'%s'", projID));
					listpblID.add(String.format("'%s'", pblID));
					listSeqNo.add(String.format("'%s'", seqNo));

				}
			}

			System.out.println("list" + listEntityID);
			for (int i = 0; i < modelDetails.getRowCount(); i++) {
				notice_id = (String) modelDetails.getValueAt(i, 2);
			}

			String entityID = listEntityID.toString().replaceAll("\\[|\\]", "");
			//String projID = listprojID.toString().replaceAll("\\[|\\]", "");
			String pblID =listpblID.toString().replaceAll("\\[|\\]", "");
			String seqNo = listSeqNo.toString().replaceAll("\\[|\\]", "");

			String prepared_id = lookupPreparedByID.getValue();
			Date date_prepared = datePrepared.getDate();
			Date date_sent = dateSent.getDate();
			String mailed_thru = txtMailedThruName.getText();
			String received_by = txtReceivedBy.getText();
			String relation_to_buyer = txtRelationtoClient.getText();
			Date received_date = null;
			String rts_reason_id = lookupRTSReasonID.getValue();
			String rts_reason = txtRTSReasonName.getText();
			Date rts_date = dateRTSDate.getDate(); //Modified by: Monique Boriga 10/20/2021 
			String remarks = txtRemarks.getText();

			String batchno = lookupBatchNo.getValue();

			SQL = "SELECT sp_save_notices_monitoring(\n" + 
					"    ARRAY["+ entityID +"]::VARCHAR[]   ,---p_entityID character varying[],\n" + 
					"   '"+ projID +"'  ,---p_projID character varying[],\n" + 
					"    ARRAY["+ pblID +"]::VARCHAR[]   ,---p_pblID character varying[],\n" + 
					"    ARRAY["+ seqNo +"]::VARCHAR[]   ,---p_seqno character varying[],\n" + 
					"    '"+notice_id+"',---p_notice_id character varying, \n" + 
					"    '"+prepared_id+"',---p_prepared_id character varying,  \n" + 
					"    nullif('"+ date_prepared +"', 'null')::timestamp,---p_date_prepared  timestamp without time zone, \n" + 
					"    nullif('"+ date_sent +"', 'null')::timestamp,---p_date_sent timestamp without time zone, \n" + 
					"    '"+mailed_thru+"',---p_mailed_thru character varying, \n" + 
					"    '"+received_by+"',---p_received_by character varying, \n" + 
					"    '"+relation_to_buyer+"',---p_relation_to_buyer character varying, \n" + 
					"   nullif('"+ received_date +"', 'null')::timestamp,---p_received_date timestamp without time zone,  \n" + 
					"    nullif('"+ rts_reason_id +"', 'null'),---p_rts_reason_id character varying, \n" + 
					"    '"+rts_reason+"',---p_rts_reason character varying,  \n" + 
					"    nullif('"+ rts_date +"', 'null')::timestamp,---p_rts_date timestamp without time zone,  \n" + 
					"    '"+remarks+"',---p_remarks character varying,\n" + 
					"    '"+batchno+"')---p_batch_no character varying";
			FncSystem.out("save query", SQL);

			pgSelect db = new pgSelect();
			db.select(SQL);
			FncSystem.out("save query", SQL);

			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Notice has been updated.", "Save", JOptionPane.INFORMATION_MESSAGE);

			tblActiveClients.setEnabled(true);
			tblDetails.setEnabled(true);

			refreshFields(false);
			btnNavigation(false,true, true, false, true);
		}
	}
	private Boolean hasSelected(modelRB_Monitoring model) {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < model.getRowCount(); x++){
			listSelected.add((Boolean) model.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	private Boolean hasSelectedNotices() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelDetails.getRowCount(); x++){
			listSelected.add((Boolean) modelDetails.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}


	private void toDelete(){

		if (hasSelected(modelActiveClients)) {

			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to delete selected client?", "Delete", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				for (int i = 0; i < modelActiveClients.getRowCount(); i++) {

					Boolean isSelected = (Boolean) modelActiveClients.getValueAt(i, 0);

					if (isSelected) {
						String entityID = (String) modelActiveClients.getValueAt(i, 2);
						String projID = (String) modelActiveClients.getValueAt(i, 4);
						String pblID = (String) modelActiveClients.getValueAt(i, 6);
						String seqNo =  String.valueOf(modelActiveClients.getValueAt(i, 7));

						String batchno = lookupBatchNo.getValue();

						pgUpdate pu = new pgUpdate();

						for (int j = 0; j < modelDetails.getRowCount(); j++) {
							notice_id = (String) modelDetails.getValueAt(j, 2);
						}

						pu.executeUpdate("UPDATE rf_client_notices SET status_id = 'I' \n" + 
								"--select * from rf_client_notices \n" + 
								"where entity_id = '"+entityID+"'\n" + 
								"and projcode = '"+projID+"'\n" + 
								"and pbl_id = '"+pblID+"'\n" + 
								"and seq_no = '"+seqNo+"'\n" + 
								"and batch_no = '"+batchno+"'\n" +
								"and notice_id = '"+notice_id+"' \n" +
								"and status_id = 'A'", true);
						pu.commit();
					}

				}
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client has been updated.", "Delete", JOptionPane.INFORMATION_MESSAGE);

				displayBatch(lookupBatchNo.getValue());

				displayDetailsBatch(lookupBatchNo.getValue());
				displayNoticeDetailsBatch(lookupBatchNo.getValue());

			}

		}else{
			JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select client(s) to Delete.", "Delete", JOptionPane.WARNING_MESSAGE);

		}
	}

	private void toDeletePerNotice(){
		if (hasSelected(modelPerClients) || (hasSelectedNotices())) {
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to delete selected notice?", "Delete", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				for (int i = 0; i < modelPerClients.getRowCount(); i++) {

					Boolean isSelected = (Boolean) modelPerClients.getValueAt(i, 0);

					if (isSelected) {
						entityID = (String) modelPerClients.getValueAt(i, 2);
						projID = (String) modelPerClients.getValueAt(i, 4);
						pblID = (String) modelPerClients.getValueAt(i, 6);
						seqNo =  (Integer) modelPerClients.getValueAt(i, 7);

						String batchno = txtBatchNo.getText().toString();

						int selected_row = tblDetails.convertRowIndexToModel(tblDetails.getSelectedRow());

						Integer rec_id = (Integer) modelDetails.getValueAt(selected_row, 4);
						String notice_id = (String) modelDetails.getValueAt(selected_row, 2);

						pgUpdate pu = new pgUpdate();

						pu.executeUpdate("UPDATE rf_client_notices SET status_id = 'I' \n" + 
								"--select * from rf_client_notices \n" + 
								"where entity_id = '"+entityID+"'\n" + 
								"and projcode = '"+projID+"'\n" + 
								"and pbl_id = '"+pblID+"'\n" + 
								"and seq_no = '"+seqNo+"'\n" + 
								"and batch_no = '"+batchno+"'\n" +
								"and rec_id = '"+rec_id+"' \n" +
								"and notice_id = '"+notice_id+"' \n" +
								"and status_id = 'A'", true);
						pu.commit();
					}
				}

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client has been updated.", "Delete", JOptionPane.INFORMATION_MESSAGE);

				for (int i = 0; i < modelPerClients.getRowCount(); i++) {
					String entityID = (String) modelPerClients.getValueAt(i, 2);
					String projID = (String) modelPerClients.getValueAt(i, 4);
					String pblID = (String) modelPerClients.getValueAt(i, 6);
					Integer seqNo =  (Integer) modelPerClients.getValueAt(i, 7);

					displayDetails(entityID, projID, pblID, seqNo);
				}
			}

		}else{
			JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select notice to delete", "Delete", JOptionPane.WARNING_MESSAGE);
		}
	}


	private Boolean ifPosition(){
		pgSelect db = new pgSelect();
		
		//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
//		db.select("select 13 < (select level_no from mf_rank_level where  level_code = emp_rank order by level_no) from em_employee where emp_code  = '"+UserInfo.EmployeeCode+"' ");
		db.select("select 5 > (select level_no from mf_rank_level where  level_code = emp_rank order by level_no) from em_employee where emp_code  = '"+UserInfo.EmployeeCode+"' ");
		return (Boolean) db.Result[0][0];

	}


	public void NoticePreview(String notice,String entity_id, String proj_id,String pbl_id, Integer seq_no, String batch_no, Boolean from_card){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		pgSelect db = new pgSelect();
		ArrayList<String> list_entity_id = new ArrayList<String>();
		ArrayList<String> list_pbl_id = new ArrayList<String>();
		ArrayList<Integer> list_seq_no = new ArrayList<Integer>();
		String comp = "";
		
		String SQL = "";
		System.out.printf("Display from card: %s%n", from_card);
		
		if (from_card) {
			
			list_entity_id.add(entity_id);
			list_pbl_id.add(pbl_id);
			list_seq_no.add(seq_no);
			SQL = "select report_name,notice_desc from mf_notice_type_per_account where notice_id = '"+notice+"'";
			FncSystem.out("Display SQL", SQL);

		} else {
			if (tabMonitoring.getSelectedIndex() == 0) {
				for (int i = 0; i < modelActiveClients.getRowCount(); i++) {
					//int selected_row = tblActiveClients.convertRowIndexToModel(tblActiveClients.getSelectedRow());
					
					Boolean Selected = (Boolean) modelActiveClients.getValueAt(i, 0);
					String entityID = (String) modelActiveClients.getValueAt(i, 2);
					String pblID = (String) modelActiveClients.getValueAt(i, 6);
					Integer seqNo = (Integer) modelActiveClients.getValueAt(i, 7);
					
					if (Selected) {
						list_entity_id.add(entityID);
						list_pbl_id.add(pblID);
						list_seq_no.add(seqNo);
						
					}
					
				}
				
				SQL = "select report_name,notice_desc from mf_notice_type_per_account where notice_id = '"+notice+"'";

			} else {
				SQL = "select report_name,notice_desc from mf_notice_type_per_account where notice_id = '"+notice+"'";

			}
		}
		
		/*if(proj_id.equals("015")) {// Added by Erick 2021-06-24
			comp = "CENQHOMES DEVELOPMENT CORPORATION";
			System.out.println("Dumaan dito sa cenq");
		}else {
			comp = "VERDANTPOINT DEVELOPMENT CORPORATION";
		}*/
		
		if (notice.equals("34") || notice.equals("127") || notice.equals("128") || notice.equals("130") || notice.equals("131") || notice.equals("139") || notice.equals("140") || notice.equals("141")) {
			//Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			if (notice.equals("34")) {
				mapParameters.put("batch_no", batch_no);
				mapParameters.put("entity_id", entity_id);
				mapParameters.put("proj_id", proj_id);
				mapParameters.put("pbl_id", pbl_id);
				mapParameters.put("seq_no", seq_no.toString());
				mapParameters.put("notice_type_id", notice);
				//mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters.put("company", comp);
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("from_card", from_card);
				
				System.out.printf("Display value of batch_no: %s%n", batch_no);
				System.out.printf("Display value of entity_id: %s%n", entity_id);
				System.out.printf("Display value of proj_id: %s%n", proj_id);
				System.out.printf("Display value of pbl_id: %s%n", pbl_id);
				System.out.printf("Display value of seq_no: %s%n", seq_no);

				/*System.out.println("REPORT"+" /Reports/"+db.Result[0][0]+"");
				FncReport.generateReport("/Reports/"+db.Result[0][0]+".jasper", "Notice of Cancellation",  "", mapParameters);	*/
				//System.out.printf("Display SQL for report: SELECT * FROM view_notice_post_filing_first_notice(string_to_array(%s, ',')::VARCHAR[], string_to_array(%s, ',')::VARCHAR[], string_to_array(%s, ',')::VARCHAR[], string_to_array(%s, ',')::VARCHAR[], %s, %s);%n", list_entity_id, proj_id, list_pbl_id, list_seq_no, batch_no, true);

				FncReport.generateReport("/Reports/rptNotcTO_card.jasper", "Notice for TO Orientation", "", mapParameters);	
			} else if (notice.equals("127") || notice.equals("128")) {
				pgUpdate dbExec = new pgUpdate();
				dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
				
				dbExec = new pgUpdate();
				String strEnt = FncGlobal.GetString("select entity_name from rf_entity where entity_id = '"+entity_id+"'");
				dbExec.executeUpdate("insert into tmp_hdmf (client_name, emp_code) values ('"+strEnt+"',  '"+UserInfo.EmployeeCode+"')", false);
				dbExec.commit();
				
				Map<String, Object> mapParameters_ffnot = new HashMap<String, Object>();
				mapParameters_ffnot.put("batch_no", batch_no);
				//mapParameters_ffnot.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters_ffnot.put("company", comp);
				mapParameters_ffnot.put("company_address", "");
				mapParameters_ffnot.put("notice_type_id", notice);
				mapParameters_ffnot.put("prepared_by", GetName(UserInfo.EmployeeCode));
		
				if (notice.equals("127")) {
					FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingFirstNotice.jasper", "Qualified for First Filing First Notice", "", mapParameters_ffnot);
				} else if (notice.equals("128")) {
					FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingFinalNotice.jasper", "Qualified for First Filing Final Notice", "", mapParameters_ffnot);					
				}
			} else if (notice.equals("130") || notice.equals("131")) {
				pgUpdate dbExec = new pgUpdate();
				dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
				
				dbExec = new pgUpdate();
				String strEnt = FncGlobal.GetString("select entity_name from rf_entity where entity_id = '"+entity_id+"'");
				dbExec.executeUpdate("insert into tmp_hdmf (client_name, emp_code) values ('"+strEnt+"',  '"+UserInfo.EmployeeCode+"')", false);
				dbExec.commit();
				
				Map<String, Object> mapParameters_ffnot = new HashMap<String, Object>();
				mapParameters_ffnot.put("batch_no", batch_no);
				//mapParameters_ffnot.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters_ffnot.put("company", comp);
				mapParameters_ffnot.put("company_address", "");
				mapParameters_ffnot.put("notice_type_id", notice);
				mapParameters_ffnot.put("prepared_by", GetName(UserInfo.EmployeeCode));
				if (notice.equals("130")) {
					FncReport.generateReport("/Reports/rpt_hdmf_ReturnedHDMF_FirstNotice.jasper", "Returned from HDMF First Notice", "", mapParameters_ffnot);
				} else if (notice.equals("131")) {
					FncReport.generateReport("/Reports/rpt_hdmf_ReturnedHDMF_FinalNotice.jasper", "Returned from HDMF Final Notice", "", mapParameters_ffnot);					
				}
			}  else if (notice.equals("139")) {
				pgUpdate dbExec = new pgUpdate();
				dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
				
				dbExec = new pgUpdate();
				String strEnt = FncGlobal.GetString("select entity_name from rf_entity where entity_id = '"+entity_id+"'");
				dbExec.executeUpdate("insert into tmp_hdmf (client_name, emp_code) values ('"+strEnt+"',  '"+UserInfo.EmployeeCode+"')", false);
				dbExec.commit();
				
				Map<String, Object> mapParameters_ffnot = new HashMap<String, Object>();
				mapParameters_ffnot.put("batch_no", batch_no);
				//mapParameters_ffnot.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters_ffnot.put("company", comp);
				mapParameters_ffnot.put("company_address", "");
				mapParameters_ffnot.put("notice_type_id", notice);
				mapParameters_ffnot.put("prepared_by", GetName(UserInfo.EmployeeCode));
					FncReport.generateReport("/Reports/rpt_NoticeonFillingatHDMF.jasper", "Qualified for First Filing First Notice", "", mapParameters_ffnot);
				
			} else if (notice.equals("140")){
				pgUpdate dbExec = new pgUpdate();
				dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
				
				
				dbExec = new pgUpdate();
				String strEnt = FncGlobal.GetString("select entity_name from rf_entity where entity_id = '"+entity_id+"'");
				dbExec.executeUpdate("insert into tmp_hdmf (client_name, emp_code) values ('"+strEnt+"',  '"+UserInfo.EmployeeCode+"')", false);
				dbExec.commit();

				Map<String, Object> mapParameters_Nev = new HashMap<String, Object>();
				
				System.out.println("batch_no:::" + batch_no);
				System.out.println("prepared_by:::" + FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
						+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
						+ UserInfo.EmployeeCode + "'"));
				mapParameters_Nev.put("prepared_by",
						FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
								+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
								+ UserInfo.EmployeeCode + "'"));
				mapParameters_Nev.put("batch_no", batch_no);	
				FncReport.generateReport("/Reports/rpt_NoticeofEmploymentVerification_v2.jasper", "Notice of Employment Verification ", "", mapParameters_Nev);
				
			}  else if (notice.equals("141")) {
				pgUpdate dbExec = new pgUpdate();
				dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
				
				dbExec = new pgUpdate();
				String strEnt = FncGlobal.GetString("select entity_name from rf_entity where entity_id = '"+entity_id+"'");
				dbExec.executeUpdate("insert into tmp_hdmf (client_name, emp_code) values ('"+strEnt+"',  '"+UserInfo.EmployeeCode+"')", false);
				dbExec.commit();
				
				Map<String, Object> mapParameters_ffnot = new HashMap<String, Object>();
				mapParameters_ffnot.put("batch_no", batch_no);
				//mapParameters_ffnot.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters_ffnot.put("company", comp);
				mapParameters_ffnot.put("company_address", "");
				mapParameters_ffnot.put("notice_type_id", notice);
				mapParameters_ffnot.put("prepared_by", GetName(UserInfo.EmployeeCode));
					FncReport.generateReport("/Reports/rpt_NoticeonPostFillingatHDMF.jasper", "Qualified for Post Filing Notice", "", mapParameters_ffnot);
				
			}
			
		} else {
			db.select(SQL);
			System.out.println("***** " + SQL +", " + list_entity_id + " "+ list_pbl_id + " "+ proj_id + " " + db.Result[0][0].toString() );

			mapParameters.put("batch_no", batch_no);
			mapParameters.put("entity_id", entity_id);
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("pbl_id", pbl_id);
			mapParameters.put("seq_no", seq_no.toString());
			mapParameters.put("list_entity_id", list_entity_id);
			mapParameters.put("list_pbl_id", list_pbl_id);	
			mapParameters.put("list_seq_no", list_seq_no);
			mapParameters.put("notice_type_id", notice);
			mapParameters.put("batch_no", batch_no);
			//mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapParameters.put("company", comp);
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("from_card", from_card);
			String logo = FncGlobal.GetString("SELECT company_logo from mf_company where co_id = (SELECT co_id from mf_project where proj_id = '"+proj_id+"')");
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo));
			
			System.out.printf("Display value of From CARD: %s%n", from_card);
			System.out.printf("Display value of batch_no: %s%n", batch_no);
			System.out.printf("Display value of entity_id: %s%n", entity_id);
			System.out.printf("Display value of proj_id: %s%n", proj_id);
			System.out.printf("Display value of pbl_id: %s%n", pbl_id);
			System.out.printf("Display value of seq_no: %s%n", seq_no);
			

			/*System.out.println("REPORT"+" /Reports/"+db.Result[0][0]+"");
			FncReport.generateReport("/Reports/"+db.Result[0][0]+".jasper", "Notice of Cancellation",  "", mapParameters);	*/
			//System.out.printf("Display SQL for report: SELECT * FROM view_notice_post_filing_first_notice(string_to_array(%s, ',')::VARCHAR[], string_to_array(%s, ',')::VARCHAR[], string_to_array(%s, ',')::VARCHAR[], string_to_array(%s, ',')::VARCHAR[], %s, %s);%n", list_entity_id, proj_id, list_pbl_id, list_seq_no, batch_no, true);

			FncReport.generateReport(String.format("/Reports/%s.jasper", db.Result[0][0].toString()), db.Result[0][1].toString(), mapParameters);			
			//FncReport.generateReport("/Reports/rptNoticeForPostFiling_FirstNotice.jasper", "First Notice - Post Filing at HDMF", mapParameters);
		}
	}
	public static String GetName(String emp_code){
		String entityid = "";

		String SQL = "SELECT B.entity_name\n" + 
		"FROM em_employee A\n" + 
		"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + 
		"WHERE a.emp_code = '"+emp_code+"'";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			entityid = (String) sqlExec.getResult()[0][0];
		}else{
			entityid = "";
		}

		return entityid;
	}
}
