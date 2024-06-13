/**
 * 
 */
package Buyers.CreditandCollections;
import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import tablemodel.modelRB_Monitoring_old;
import tablemodel.modelRB_MonitoringDetails;
import DateChooser._JDateChooser;
import Functions.FncComponent;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup._JLookup;
import components._JTableMain;
import components._JXTextField;

/**
 * @author Alvin Gonzales - 2015-03-12
 *
 */
public class pnlRB_Monitoring_OLD extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9021093053384956185L;

	private JScrollPane scrollClients;
	private _JTableMain tblClients;
	private modelRB_Monitoring_old modelClients;
	private JList rowHeaderClients;

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
	

	public pnlRB_Monitoring_OLD() {
		initGUI();
	}

	public pnlRB_Monitoring_OLD(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlRB_Monitoring_OLD(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlRB_Monitoring_OLD(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(800, 600));
		{
			scrollClients = new JScrollPane();
			this.add(scrollClients, BorderLayout.CENTER);
			scrollClients.setBorder(components.JTBorderFactory.createTitleBorder("Clients"));
			scrollClients.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollClients.setPreferredSize(new Dimension(590, 200));
			scrollClients.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(tblClients.isEnabled()){
						tblClients.clearSelection();
						//clearFields();
					}
				}
			});
			{
				modelClients = new modelRB_Monitoring_old();
				modelClients.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						if(e.getType() == 1){
							((DefaultListModel)rowHeaderClients.getModel()).addElement(modelClients.getRowCount());
							if(modelClients.getRowCount() == 0){
								rowHeaderClients.setModel(new DefaultListModel());
							}
						}
					}
				});

				tblClients = new _JTableMain(modelClients);
				scrollClients.setViewportView(tblClients);
				tblClients.hideColumns("Client ID", "Project ID", "PBL ID", "Seq.");
				tblClients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if(!arg0.getValueIsAdjusting()){//XXX Clients
							clearFields();
							try {
								int row = tblClients.getSelectedRow();

								String entity_id = (String) modelClients.getValueAt(row, 0);
								String proj_id = (String) modelClients.getValueAt(row, 2);
								String pbl_id = (String) modelClients.getValueAt(row, 4);
								Integer seq_no = (Integer) modelClients.getValueAt(row, 5);
								
								displayDetails(entity_id, proj_id, pbl_id, seq_no);
							} catch (ArrayIndexOutOfBoundsException e) { }
						}
					}
				});
			}
			{
				rowHeaderClients = tblClients.getRowHeader();
				rowHeaderClients.setModel(new DefaultListModel());
				scrollClients.setRowHeaderView(rowHeaderClients);
				scrollClients.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				scrollClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblClients.getRowCount())));
			}
		}
		{
			pnlDetails = new JPanel(new BorderLayout(5, 5));
			this.add(pnlDetails, BorderLayout.SOUTH);
			pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
			pnlDetails.setPreferredSize(new java.awt.Dimension(790, 300));
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
					tblDetails.hideColumns("Stage ID", "Stage", "Notice ID", "Rec ID");
					tblDetails.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if(!e.getValueIsAdjusting()){//XXX Details
								try {
									int row = tblDetails.getSelectedRow();
									
									Integer rec_id = (Integer) modelDetails.getValueAt(row, 4);
									displayNoticeDetails(rec_id);
								} catch (ArrayIndexOutOfBoundsException e1) { }
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
							}
							{
								txtPreparedByName = new _JXTextField();
								jPanel1.add(txtPreparedByName, BorderLayout.CENTER);
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
					{
						pnlRTSReason = new JPanel(new BorderLayout(3, 3));
						pnlDetailsCenterCenter.add(pnlRTSReason);
						{
							lblRTSReason = new JLabel("RTS Reason");
							pnlRTSReason.add(lblRTSReason, BorderLayout.WEST);
							lblRTSReason.setPreferredSize(new Dimension(100, 35));
						}
						{
							JPanel jPanel1 = new JPanel(new BorderLayout(3, 3));
							pnlRTSReason.add(jPanel1, BorderLayout.CENTER);
							{
								lookupRTSReasonID = new _JLookup(null, "RTS Reason");
								jPanel1.add(lookupRTSReasonID, BorderLayout.WEST);
								lookupRTSReasonID.setPreferredSize(new Dimension(83, 35));
							}
							{
								txtRTSReasonName = new _JXTextField();
								jPanel1.add(txtRTSReasonName, BorderLayout.CENTER);
							}
						}
					}
					{
						pnlRemarks = new JPanel(new BorderLayout(3, 3));
						pnlDetailsCenterCenter.add(pnlRemarks);
						{
							lblRemarks = new JLabel("Remarks By");
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
						JPanel pnlNavigation = new JPanel(new GridLayout(1, 3, 5, 5));
						pnlDetailsCenterSouth.add(pnlNavigation, BorderLayout.EAST);
						pnlNavigation.setPreferredSize(new Dimension(310, 0));
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
		displayClients();
		refreshFields(false);
		btnNavigation(true, false, false);
	}

	private void displayClients() {
		rowHeaderClients.setModel(new DefaultListModel());
		//_RegularBillingAndNotices.displayMonitoring(modelClients);
		scrollClients.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblClients.getRowCount())));
		tblClients.packAll();
	}

	private void displayDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		rowHeaderDetails.setModel(new DefaultListModel());
		_RegularBillingAndNotices.displayMonitoringDetails(modelDetails, entity_id, proj_id, pbl_id, seq_no);
		scrollDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDetails.getRowCount())));
		tblDetails.packAll();
	}
	
	private void displayNoticeDetails(Integer rec_id) {/*
		Object[] data = _RegularBillingAndNotices.getNoticeDetails(rec_id);

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
	*/}
	
	private void btnNavigation(Boolean sEdit, Boolean sSave, Boolean sCancel) {
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}
	
	private void refreshFields(Boolean enable) {
		FncComponent.setComponentsEnabled(pnlDetailsCenter, enable);
		dateBillingDueDate.setEnabled(false);
		dateDefaultDate.setEnabled(false);
	}
	
	private void clearFields() {
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
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {//XXX action
		String action = arg0.getActionCommand();

		if(action.equals("Edit")){
			if(tblDetails.getSelectedRows().length == 1){
				tblClients.setEnabled(false);
				tblDetails.setEnabled(false);

				refreshFields(true);
				btnNavigation(false, true, true);
			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select only 1 notice to edit.", action, JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if(action.equals("Save")){
			
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
				Date rts_date = null;
				String remarks = txtRemarks.getText();
				
				//Added by: Monique Boriga 10/21/2021 for tagging of creator of rts
				String rts_created_by = UserInfo.EmployeeCode;
				Date rts_date_created = FncGlobal.getDateToday();

//				_RegularBillingAndNotices.saveNoticeDetails(rec_id, notice_id, prepared_id, date_prepared, date_sent, mailed_thru, received_by, relation_to_buyer, received_date, rts_reason_id, rts_reason, rts_date, remarks, rts_created_by, rts_date_created);

				_RegularBillingAndNotices.saveNoticeDetails(rec_id, entity_id, proj_id, pbl_id, seq_no, notice_id, prepared_id, date_prepared, date_sent, mailed_thru, received_by, relation_to_buyer, received_date, rts_reason_id, rts_reason, rts_date, remarks, rts_created_by, rts_date_created);
				
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Notice has been updated.", action, JOptionPane.INFORMATION_MESSAGE);
				tblClients.setEnabled(true);
				tblDetails.setEnabled(true);

				refreshFields(false);
				btnNavigation(true, false, false);
			}
		}

		if(action.equals("Cancel")){
			tblClients.setEnabled(true);
			tblDetails.setEnabled(true);

			refreshFields(false);
			clearFields();
			btnNavigation(true, false, false);
		}
	}

}
