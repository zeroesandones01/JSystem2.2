package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.table.NumberEditorExt;

import Database.pgSelect;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JScrollPane;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelRetentionFeeIssuance;

public class RetentionFeeIssuance extends _JInternalFrame implements _GUI {

	static Dimension size = new Dimension(900, 550);
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _JScrollPane scrollData;
	private modelRetentionFeeIssuance model_retfeelist;
	private _JTableMain tbl_retentionFee;
	private JList rowHeader;

	protected String company_logo;
	protected String co_id;
	protected String company_name;
	protected String batch_no;

	private JButton btnEdit;
	private JButton btnPost;
	private JButton btnCancel;
	private JButton btnPreview;
	private _JLookup lookupBatchNo;
	private JTextField txtBatchNo;
	private BigDecimal total_amt;


	public RetentionFeeIssuance() {
		super("Retention Fee Issuance", false, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(size);
		this.setLayout(new BorderLayout(5, 5));

		JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
		this.add(pnlMain, BorderLayout.CENTER); 
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		{
			JPanel pnlHead = new JPanel(new BorderLayout(5, 5)); 
			pnlMain.add(pnlHead, BorderLayout.PAGE_START); 
			pnlHead.setBorder(components.JTBorderFactory.createTitleBorder("Checking / Posting of Retention Fee"));
			pnlHead.setPreferredSize(new Dimension(0, 60));

			{
				JPanel pnlComp = new JPanel(new BorderLayout(5, 5)); 
				pnlHead.add(pnlComp, BorderLayout.CENTER); 

				{
					lookupCompany = new _JLookup(null, "Company"); 
					pnlComp.add(lookupCompany, BorderLayout.LINE_START); 
					lookupCompany.setPreferredSize(new Dimension(80, 0));
					lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
					lookupCompany.setReturnColumn(0);
					lookupCompany.addLookupListener(new LookupListener() {

						@Override
						public void lookupPerformed(LookupEvent event) {
							Object data []	= ((_JLookup)event.getSource()).getDataSet(); 
							if (data != null) {

								txtCompany.setText(data[1].toString());
								company_logo = (String) data[3];
								co_id = (String) data[0]; 
								company_name = (String) data[1];
								lookupBatchNo.setEnabled(true);
								lookupBatchNo.setLookupSQL(getBatchNo(lookupCompany.getValue()));
								tbl_retentionFee.setEditable(false);
								btnState(false, false, true, false);

							}

						}
					});
				}
				{
					txtCompany = new JTextField(); 
					pnlComp.add(txtCompany, BorderLayout.CENTER); 
					txtCompany.setHorizontalAlignment(JTextField.CENTER);

				}	
				{
					JPanel pnlBatchNo = new JPanel(new BorderLayout(5, 5)); 
					pnlComp.add(pnlBatchNo, BorderLayout.LINE_END); 
					pnlBatchNo.setPreferredSize(new Dimension(400, 0));
					{
						JLabel lblBatchNo = new JLabel("       Batch No: ");
						pnlBatchNo.add(lblBatchNo, BorderLayout.LINE_START); 
						lblBatchNo.setPreferredSize(new Dimension(80, 0));
					}
					{
						lookupBatchNo = new _JLookup(null, "Batch No"); 
						pnlBatchNo.add(lookupBatchNo, BorderLayout.CENTER); 
						lookupBatchNo.setReturnColumn(0); 
						lookupBatchNo.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object data []	= ((_JLookup)event.getSource()).getDataSet(); 
								if (data != null) {
									txtBatchNo.setText(data[1].toString());
									batch_no = (String) data[1];
									displayRetentionFee(co_id, batch_no, model_retfeelist, rowHeader);
									tbl_retentionFee.setEditable(true);
									btnState(true, true, true, true); 
									lookupBatchNo.setEnabled(false);
								}	
							}
							
						
						}); 
					}
					{
						txtBatchNo = new JTextField(); 
						pnlBatchNo.add(txtBatchNo, BorderLayout.LINE_END); 
						txtBatchNo.setPreferredSize(new Dimension(250, 0));
						txtBatchNo.setHorizontalAlignment(JTextField.CENTER);
					}
				}

			}

		}
		{
			JPanel pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER); 
			pnlTable.setBorder(new EmptyBorder(5, 5, 5, 5));

			{
				scrollData = new _JScrollPane(); 
				pnlTable.add(scrollData, BorderLayout.CENTER); 		
				{

					model_retfeelist = new modelRetentionFeeIssuance(); 
					tbl_retentionFee = new _JTableMain(model_retfeelist); 
					tbl_retentionFee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tbl_retentionFee.setSortable(false);
					scrollData.setViewportView(tbl_retentionFee);
					scrollData.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 

					{
						rowHeader = tbl_retentionFee.getRowHeader(); 
						rowHeader.setModel(new DefaultListModel());
						scrollData.setRowHeaderView(rowHeader);
						scrollData.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

					}

					tbl_retentionFee.getColumnModel().getColumn(0).setPreferredWidth(50);
					tbl_retentionFee.getColumnModel().getColumn(1).setPreferredWidth(220);
					tbl_retentionFee.getColumnModel().getColumn(2).setPreferredWidth(60);
					tbl_retentionFee.getColumnModel().getColumn(3).setPreferredWidth(120);
					tbl_retentionFee.getColumnModel().getColumn(4).setPreferredWidth(50);
					tbl_retentionFee.getColumnModel().getColumn(5).setPreferredWidth(80);
					tbl_retentionFee.getColumnModel().getColumn(6).setPreferredWidth(100);
					tbl_retentionFee.getColumnModel().getColumn(7).setPreferredWidth(100);
					tbl_retentionFee.getColumnModel().getColumn(8).setPreferredWidth(100);
					tbl_retentionFee.getColumnModel().getColumn(9).setPreferredWidth(200);
					tbl_retentionFee.getColumnModel().getColumn(10).setPreferredWidth(100);
					tbl_retentionFee.getColumnModel().getColumn(11).setPreferredWidth(100);
					tbl_retentionFee.getColumnModel().getColumn(12).setPreferredWidth(100);
					tbl_retentionFee.getColumnModel().getColumn(13).setPreferredWidth(100);

					tbl_retentionFee.addPropertyChangeListener(new PropertyChangeListener() {

						@Override
						public void propertyChange(PropertyChangeEvent evt) {

							_JTableMain table = (_JTableMain) evt.getSource();
							String propertyName = evt.getPropertyName();

							if (propertyName.equals("tableCellEditor")) {
								int column = table.convertColumnIndexToModel(table.getEditingColumn());

								if (column != -1 && model_retfeelist.getColumnClass(column).equals(BigDecimal.class)) {
									Object oldValue = null;
									try {
										oldValue = ((NumberEditorExt) evt.getOldValue()).getCellEditorValue();
									} catch (NullPointerException e) {
									}

									if (oldValue != null) {
										if (oldValue instanceof Double) {
											table.setValueAt(new BigDecimal((Double) oldValue), table.getEditingRow(), table.getEditingColumn());
										}
										if (oldValue instanceof Long) {
											table.setValueAt(new BigDecimal((Long) oldValue), table.getEditingRow(), table.getEditingColumn());
										}
									}
								}
							}
						}
					});
				}
			}

		}
		{
			JPanel pnlButtons = new JPanel(new GridLayout(1, 4, 5, 5)); 
			pnlMain.add(pnlButtons, BorderLayout.PAGE_END); 
			pnlButtons.setPreferredSize(new Dimension(0, 50));
			{
				btnEdit = new JButton("EDIT"); 
				pnlButtons.add(btnEdit); 
				btnEdit.addActionListener(this);
				btnEdit.setActionCommand("Edit");

				btnPost = new JButton("POST"); 
				pnlButtons.add(btnPost); 
				btnPost.addActionListener(this);
				btnPost.setActionCommand("Post");

				btnCancel = new JButton("CANCEL"); 
				pnlButtons.add(btnCancel); 
				btnCancel.addActionListener(this);
				btnCancel.setActionCommand("Cancel");

				btnPreview = new JButton("PREVIEW"); 
				pnlButtons.add(btnPreview); 
				btnPreview.addActionListener(this);
				btnPreview.setActionCommand("Preview");

			}
		}

		btnState(false, false, false, false);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Edit")) {
			if(selected_count() == 1) {
				tbl_retentionFee.setEditable(true);
				btnState(true, false, false, true);
				btnEdit.setText("SAVE");
				btnEdit.setActionCommand("Save");
			} else if (selected_count() > 1) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select only one client.", "Warning Message", JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a client.", "Warning Message", JOptionPane.WARNING_MESSAGE);
			}
		}

		if(e.getActionCommand().equals("Post")) {
			if(selected_count() > 0) {
				String formattedAmount = formatBigDecimal(total_amt);
				if(JOptionPane.showConfirmDialog(getContentPane(), String.format("<html>Are you sure you want to post?<br>Total Amount: %s</html>", formattedAmount), "Save Retention Fee Payments", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					postRetentionFee(lookupCompany.getValue().toString(), batch_no, model_retfeelist);
					btnState(false, false, true, false);
				} 
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Please select client(s) to post.", "Warning Message", JOptionPane.WARNING_MESSAGE);
			}

		}

		if(e.getActionCommand().equals("Cancel")) {
			btnState(false, false, false, false);
			FncTables.clearTable(model_retfeelist);
			tbl_retentionFee.clearSelection();
			lookupCompany.setValue("");
			txtCompany.setText("");
			lookupBatchNo.setValue("");
			txtBatchNo.setText("");
		}

		if(e.getActionCommand().equals("Preview")) {
			previewRetentionFee_ForChecking();
			btnState(true, true, true, true);


		}

		if(e.getActionCommand().equals("Save")) {
			UpdateRetentionFeeDetails();
			btnEdit.setActionCommand("Edit");
			btnState(false, true, true, true);
			displayRetentionFee(co_id, batch_no, model_retfeelist, rowHeader);
		}

	}

	private void displayRetentionFee(String co_id, String batch_no, modelRetentionFeeIssuance model, JList rowHeader) {
		FncTables.clearTable(model);
		DefaultListModel list = new DefaultListModel(); 
		rowHeader.setModel(list);

		String created_by = UserInfo.EmployeeCode.toString(); 	//ADDED by MONIQUE DTD 2023-09-06

		pgSelect db = new pgSelect();

		String SQL = "Select false as tag\n"
				+ ", get_client_name(a.entity_id) as client\n"
				+ ", (Select proj_alias from mf_project where proj_id = a.proj_id and status_id = 'A') as proj\n"
				+ ", get_unit_description(a.proj_id, a.pbl_id) as unit\n"
				+ ", b.part_type \n"
				+ ", (Select particulars from mf_pay_particular where pay_part_id = b.part_type) as particular\n"
				+ ", a.booking_date::DATE as actual_date\n"
				+ ", b.amount::NUMERIC\n"
				+ ", b.receipt_no\n"
				+ ", a.client_seqno\n"
				+ ", a.entity_id\n"
				+ ", a.proj_id\n"
				+ ", a.pbl_id\n"
				+ ", a.seq_no::INT \n"
				+ "FROM rf_pay_header a\n"
				+ "LEFT JOIN rf_pay_detail b ON b.client_seqno = a.client_seqno \n"
				+ "WHERE a.batch_no = '"+batch_no+"'\n"
				+ "AND a.co_id = '"+co_id+"'"; 

		System.out.println("SQL: " + SQL);

		db.select(SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				model.addRow(db.getResult()[x]);
				list.addElement(model.getRowCount());
			}
		}
		
		computeTotal();
		
	}

	private void btnState(Boolean sEdit, Boolean sPost, Boolean sPreview, Boolean sCancel) {
		btnEdit.setEnabled(sEdit);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
		btnCancel.setEnabled(sCancel);

	}

	private void UpdateRetentionFeeDetails() {
		pgSelect db = new pgSelect(); 

		for (int x=0; x< model_retfeelist.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model_retfeelist.getValueAt(x, 0);
			String client_seqno = (String) model_retfeelist.getValueAt(x, 9);


			if(isSelected) {
				String part_id = (String) model_retfeelist.getValueAt(x, 4);
				BigDecimal ret_fee_amt = (BigDecimal) model_retfeelist.getValueAt(x, 7); 
				Date actual_date = (Date) model_retfeelist.getValueAt(x, 6); 	

				String SQL = "Select sp_update_retention_fee('"+client_seqno+"', '"+part_id+"', "+ret_fee_amt+", '"+actual_date+"')"; 

				db.select(SQL);

				System.out.println("Update SQL: " + SQL);

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected account was updated.", JOptionPane.INFORMATION_MESSAGE));
			}
		}
	} 

	private void postRetentionFee(String co_id, String batch_no, modelRetentionFeeIssuance model) {

		ArrayList<String> listClientSeqNo = new ArrayList<String>(); 
		ArrayList<String> listEntityID = new ArrayList<String>(); 
		ArrayList<String> listProjID = new ArrayList<String>(); 
		ArrayList<String> listPblID = new ArrayList<String>(); 
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>(); 
		ArrayList<String> listActualDate = new ArrayList<String>(); 
		ArrayList<BigDecimal> listRetFeeAmt = new ArrayList<BigDecimal>(); 
		ArrayList<String> listParticular = new ArrayList<String>(); 
		ArrayList<String> listReceiptNo = new ArrayList<String>();


		for(int x = 0; x < model.getRowCount(); x++) {

			String client_seqno = (String) model.getValueAt(x, 9); 
			String entity_id = (String) model.getValueAt(x, 10);
			String proj_id = (String) model.getValueAt(x, 11);
			String pbl_id = (String) model.getValueAt(x, 12);
			Integer seq_no = (Integer) model.getValueAt(x, 13);
			String actual_date = (String) model.getValueAt(x, 6).toString(); 
			BigDecimal ret_fee_amt = (BigDecimal) model.getValueAt(x, 7); 
			String particular = (String) model.getValueAt(x, 4);
			String receipt_no = (String) model.getValueAt(x, 8);

			listClientSeqNo.add(String.format("'%s'", client_seqno)); 
			listEntityID.add(String.format("'%s'", entity_id));
			listProjID.add(String.format("'%s'", proj_id)); 
			listPblID.add(String.format("'%s'", pbl_id));
			listSeqNo.add(seq_no);
			listActualDate.add(String.format("'%s'", actual_date));
			listRetFeeAmt.add(ret_fee_amt);
			listParticular.add(String.format("'%s'", particular));
			listReceiptNo.add(String.format("'%s'", receipt_no));


		}

		String client_seqno = listClientSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", ""); 
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", ""); 
		String pbl_id = listPblID.toString().replaceAll("\\[|\\]", ""); 
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", ""); 
		String actual_date = listActualDate.toString().replaceAll("\\[|\\]", ""); 
		String ret_fee_amt = listRetFeeAmt.toString().replaceAll("\\[|\\]", ""); 
		String particular = listParticular.toString().replaceAll("\\[|\\]", ""); 
		String receipt_no = listReceiptNo.toString().replaceAll("\\[|\\]", ""); 

		pgSelect db =  new pgSelect(); 

		String SQL = "SELECT sp_issue_retfee('"+co_id+"', '"+batch_no+"', ARRAY["+client_seqno+"]::VARCHAR[], ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[],"
				+ " ARRAY["+actual_date+"]::VARCHAR[], ARRAY["+ret_fee_amt+"]::NUMERIC[], ARRAY["+particular+"]::VARCHAR[], ARRAY["+receipt_no+"]::VARCHAR[], '"+UserInfo.EmployeeCode+"')"; 

		db.select(SQL);

		if(db.isNotNull()) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts are posted.", "Post", JOptionPane.INFORMATION_MESSAGE);		
			previewRetentionFee_ForChecking();
			btnPreview.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Something went wrong. Please notify the support!", "Save", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void previewRetentionFee_ForChecking() {
		ArrayList<TD_RetentionFee_Issuance> listRetFee_Payments_ForChecking = new ArrayList<TD_RetentionFee_Issuance>(); 

		for (int x=0; x < model_retfeelist.getRowCount(); x++) {

			String client_name = (String) model_retfeelist.getValueAt(x, 1); 
			String project = (String) model_retfeelist.getValueAt(x, 2); 
			String unit = (String) model_retfeelist.getValueAt(x, 3); 
			String particular = (String) model_retfeelist.getValueAt(x, 5); 
			Date actual_date = (Date) model_retfeelist.getValueAt(x, 6);
			BigDecimal ret_fee_amt = (BigDecimal) model_retfeelist.getValueAt(x, 7); 
			String receipt_no = (String) model_retfeelist.getValueAt(x, 8); 

			System.out.println("Value of Retention Fee: "+ ret_fee_amt);


			listRetFee_Payments_ForChecking.add(new TD_RetentionFee_Issuance(client_name, project, unit, particular, actual_date, ret_fee_amt, receipt_no)); 

		}

		Map<String, Object> mapParameters = new HashMap<String, Object>(); 
		mapParameters.put("company_name", company_name); 
		mapParameters.put("checked_by", UserInfo.FullName);
		mapParameters.put("batch_no", txtBatchNo.getText()); 
		mapParameters.put("listRetFee_Payments_ForChecking", listRetFee_Payments_ForChecking); 

		System.out.println("Company:" + company_name);
		System.out.println("Checked by:" + UserInfo.FullName);
		System.out.println("List for Checking: " + listRetFee_Payments_ForChecking);

		FncReport.generateReport("/Reports/rptRetFeePaymentsforChecking.jasper", "Retention Fee Payments For Checking", mapParameters);

		System.out.println("Report name: rptRetFeePaymentsforChecking");

	}

	private static String getBatchNo(String co_id) {

		String SQL = "Select distinct ON(batch_no)\n"
				+ "row_number() over(ORDER by trans_date::DATE) as \"NO\",\n"
				+ "batch_no as \"BATCH NO\", \n"
				+ "trans_date::DATE as \"DATE\" \n"
				+ "from rf_pay_header \n"
				+ "where op_status = 'O' \n"
				+ "and co_id = '"+co_id+"'\n"
				+ "and status_id = 'A'\n"
				+ "and batch_no IS NOT NULL";

		return SQL; 
	}

	private int selected_count() {
		int count = 0;
		for (int i = 0; i < model_retfeelist.getRowCount(); i++) {
			boolean tag = (boolean) model_retfeelist.getValueAt(i, 0);
			if(tag) {
				count += 1;
			}
		}
		return count;
	}
	
	private void computeTotal() {
	    total_amt = BigDecimal.ZERO;

	    for (int i = 0; i < model_retfeelist.getRowCount(); i++) {
	        Object amt = model_retfeelist.getValueAt(i, 7);

	        if (amt != null) {
	            try {
	                BigDecimal amount = new BigDecimal(amt.toString());
	                total_amt = total_amt.add(amount);
	            } catch (NumberFormatException e) {
	                System.err.println("Invalid number format in row " + i + ": " + amt);
	            }
	        }
	    }
	}
    public static String formatBigDecimal(BigDecimal amount) {

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(amount);
    }


}
