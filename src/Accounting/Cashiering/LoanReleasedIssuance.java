package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
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

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.jdesktop.swingx.table.NumberEditorExt;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JScrollPane;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelLoanReleasedIssuance;

/**
 * @author Monique Boriga
 */

public class LoanReleasedIssuance extends _JInternalFrame implements _GUI, ActionListener{

	static Dimension size = new Dimension(900, 550);
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _JScrollPane scrollData;
	private modelLoanReleasedIssuance model_tablelist;
	private _JTableMain tbl_loanReleased;
	private JList rowHeader;

	protected String company_logo;
	protected String co_id;
	protected String company_name; 

	private JButton btnEdit;
	private JButton btnPost;
	private JButton btnCancel;
	private JButton btnPreview;

	public LoanReleasedIssuance() {
		super("Loan Released Issuance", false, true, true, true);
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
			pnlHead.setBorder(components.JTBorderFactory.createTitleBorder("Checking / Posting of Loan Released Accounts"));
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

								tbl_loanReleased.setEditable(false);
								displayLoanReleasedforTheDay(co_id, model_tablelist, rowHeader);
								btnState(true, true, true, true);

							}

						}
					});

					txtCompany = new JTextField(); 
					pnlComp.add(txtCompany, BorderLayout.CENTER); 
					txtCompany.setHorizontalAlignment(JTextField.CENTER);


					JLabel lblExtra = new JLabel(""); 
					pnlComp.add(lblExtra, BorderLayout.LINE_END); 
					lblExtra.setPreferredSize(new Dimension(250, 0));
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

					model_tablelist = new modelLoanReleasedIssuance(); 
					tbl_loanReleased = new _JTableMain(model_tablelist); 
					tbl_loanReleased.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tbl_loanReleased.setSortable(false);
					scrollData.setViewportView(tbl_loanReleased);
					scrollData.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
					
					tbl_loanReleased.addPropertyChangeListener(new PropertyChangeListener() {
						
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							_JTableMain table = (_JTableMain) evt.getSource();
							String propertyName = evt.getPropertyName();
							
							if (propertyName.equals("tableCellEditor")) {
								int column = table.convertColumnIndexToModel(table.getEditingColumn());

								if (column != -1 && model_tablelist.getColumnClass(column).equals(BigDecimal.class)) {
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
							
							computeNetProceeds();
						}
					});

					{
						rowHeader = tbl_loanReleased.getRowHeader(); 
						rowHeader.setModel(new DefaultListModel());
						scrollData.setRowHeaderView(rowHeader);
						scrollData.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

					}

					tbl_loanReleased.hideColumns("Project", "Rec_ID");
					tbl_loanReleased.getColumnModel().getColumn(0).setPreferredWidth(50);
					tbl_loanReleased.getColumnModel().getColumn(1).setPreferredWidth(220);
					tbl_loanReleased.getColumnModel().getColumn(2).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(3).setPreferredWidth(150);
					tbl_loanReleased.getColumnModel().getColumn(4).setPreferredWidth(150);
					tbl_loanReleased.getColumnModel().getColumn(5).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(6).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(7).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(8).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(9).setPreferredWidth(100);

					tbl_loanReleased.getColumnModel().getColumn(10).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(11).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(12).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(13).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(14).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(15).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(16).setPreferredWidth(100);
					tbl_loanReleased.getColumnModel().getColumn(17).setPreferredWidth(100);	

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
			btnState(true, false, false, true);
			btnEdit.setText("SAVE");
			btnEdit.setActionCommand("Save");
			tbl_loanReleased.setEditable(true);

		}

		if(e.getActionCommand().equals("Post")) {
			postLoanReleased(lookupCompany.getValue().toString(), model_tablelist);
			btnState(false, false, true, false);
		}

		if(e.getActionCommand().equals("Cancel")) {
			btnState(false, false, false, false);
			FncTables.clearTable(model_tablelist);
			tbl_loanReleased.clearSelection();
			lookupCompany.setValue("");
			txtCompany.setText("");
		}

		if(e.getActionCommand().equals("Preview")) {
			previewLoanReleased_ForChecking();
			btnState(false, false, false, false);


		}

		if(e.getActionCommand().equals("Save")) {
			UpdateLoanReleasedDetails();
			displayLoanReleasedforTheDay(lookupCompany.getValue().toString(), model_tablelist, rowHeader);
			btnEdit.setActionCommand("Edit");
			btnState(false, true, true, true);
		}

	}

	private void displayLoanReleasedforTheDay(String co_id, modelLoanReleasedIssuance model, JList rowHeader) {
		FncTables.clearTable(model);
		DefaultListModel list = new DefaultListModel(); 
		rowHeader.setModel(list);
		
		String created_by = UserInfo.EmployeeCode.toString(); 	//ADDED by MONIQUE DTD 2023-09-06

		pgSelect db = new pgSelect();

		String SQL = "Select false, "
				+ "b.proj_name, "
				+ "get_client_name(a.entity_id) as client_name, "
				+ "c.description,\n" 
				+ "a.net_proceeds, "
				+ "a.loanable_amt, "
				+ "coalesce(a.sri_doc_stamps, 0) as sri_doc_stamps,"
				+ " coalesce(a.fire, 0.00) as fire, "
				+ "coalescE(a.proc_fee, 0.00) as proc_fee,\n" 
				+ "coalesce(a.appraisal_fee, 0.00) as appraisal_fee,"
				+ " coalesce(a.interim_mri, 0.00) as interim_mri,"
				+ " coalesce(a.retention_fee, 0.00) as retention_fee,"
				+ " coalescE(a.first_ma, 0.00) as first_ma,\n" 
				+ "coalesce(a.refiling_fee, 0.00) as refiling_fee,"
				+ " coalesce(a.ret_10_percent, 0.00) as ret_10_percent,"
				+ " coalesce(a.rightofway, 0.00) as rightofway, "
				+ "null, "
				+ "null, "
				+ "coalesce(a.service_fee, 0) as service_fee, "
				+ "a.rec_id, "
				+ "a.entity_id, "
				+ "a.proj_id, "
				+ "a.pbl_id, "
				+ "a.seq_no, "
				+ "a.actual_date "
				+ "FROM rf_pagibig_lnrel a \n" 
				+ "LEFT JOIN mf_project b ON b.proj_id = a.proj_id \n" 
				+ "LEFT JOIN mf_unit_info c ON c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id AND c.status_id != 'I' \n"
				+ "WHERE \n"
				+ "/*a.date_created::DATE = CURRENT_DATE \n" //COMMENTED DTD 2023-09-07 
				+ "AND*/ (SELECT co_id from mf_project where proj_id = a.proj_id) = '"+co_id+"'\n"
				+ "AND NULLIF(a.client_seqno, '') IS NULL \n"
				+ "AND a.created_by = '"+created_by+"' \n" //ADDED by MONIQUE DTD 2023-09-06
				+ "ORDER by client_name; "; 

		System.out.println("SQL: " + SQL);

		db.select(SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				model.addRow(db.getResult()[x]);
				list.addElement(model.getRowCount());
			}
		}
	}

	private void btnState(Boolean sEdit, Boolean sPost, Boolean sPreview, Boolean sCancel) {
		btnEdit.setEnabled(sEdit);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
		btnCancel.setEnabled(sCancel);

	}
	
	private void computeNetProceeds() {
		
		if (tbl_loanReleased.getSelectedRows().length == 1 ) {
			int x = tbl_loanReleased.convertRowIndexToModel(tbl_loanReleased.getSelectedRow()); 
		
			BigDecimal loanable_amount = (BigDecimal) model_tablelist.getValueAt(x, 5); 
			BigDecimal mri_sri_doc_stamp = (BigDecimal) model_tablelist.getValueAt(x, 6); 
			BigDecimal fire = (BigDecimal) model_tablelist.getValueAt(x, 7);
			BigDecimal processing_fee = (BigDecimal) model_tablelist.getValueAt(x, 8);	
			BigDecimal appraisal_fee = (BigDecimal) model_tablelist.getValueAt(x, 9);
			BigDecimal interim_mri = (BigDecimal) model_tablelist.getValueAt(x, 10); 
			BigDecimal retention_fee = (BigDecimal) model_tablelist.getValueAt(x, 11); 
			BigDecimal service_fee = (BigDecimal) model_tablelist.getValueAt(x, 18);
			
			BigDecimal total_recap = mri_sri_doc_stamp.add(fire).add(processing_fee).add(appraisal_fee).add(interim_mri).add(retention_fee).add(service_fee);
			BigDecimal net_proceeds = loanable_amount.subtract(total_recap); 
			
			model_tablelist.setValueAt(net_proceeds, x, 4);
		}
	}
	

	private void UpdateLoanReleasedDetails() {
		pgUpdate db = new pgUpdate(); 

		for (int x=0; x< model_tablelist.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model_tablelist.getValueAt(x, 0);

			if(isSelected) {
				
				BigDecimal net_proceeds = (BigDecimal) model_tablelist.getValueAt(x, 4); 
				BigDecimal mri_sri_doc_stamp = (BigDecimal) model_tablelist.getValueAt(x, 6); 
				BigDecimal fire = (BigDecimal) model_tablelist.getValueAt(x, 7);
				BigDecimal processing_fee = (BigDecimal) model_tablelist.getValueAt(x, 8);	
				BigDecimal appraisal_fee = (BigDecimal) model_tablelist.getValueAt(x, 9);
				BigDecimal interim_mri = (BigDecimal) model_tablelist.getValueAt(x, 10); 
				BigDecimal retention_fee = (BigDecimal) model_tablelist.getValueAt(x, 11); 
				BigDecimal service_fee = (BigDecimal) model_tablelist.getValueAt(x, 18);
				Integer rec_id = (Integer) model_tablelist.getValueAt(x, 19); 
				Date actual_date = (Date) model_tablelist.getValueAt(x, 24); 
				String edited_by = UserInfo.EmployeeCode.toString(); 	
				
				String SQL = "UPDATE rf_pagibig_lnrel \n" 
							+ "SET net_proceeds = '"+net_proceeds+"', \n" 
							+ "sri_doc_stamps = '"+mri_sri_doc_stamp+"', \n" 
							+ "fire = '"+fire+"', \n" 
							+ "proc_fee = '"+processing_fee+"', \n" 
							+ "appraisal_fee = '"+appraisal_fee+"', \n" 
							+ "interim_mri = '"+interim_mri+"', \n" 
							+ "retention_fee = '"+retention_fee+"', \n"  
							+ "service_fee = '"+service_fee+"', \n" 
							+ "actual_date = '"+actual_date+"', \n"
							+ "edited_by = '"+edited_by+"', \n" 
							+ "date_edited = now() \n" 
							+ "where rec_id = '"+rec_id+"'"; 
				
				db.executeUpdate(SQL, true);
				
				System.out.println("Update SQL: " + SQL);
				
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected account was updated!", JOptionPane.INFORMATION_MESSAGE));
			}
		}
		
		db.commit();
	} 
	
	private void postLoanReleased(String co_id, modelLoanReleasedIssuance model) {
		
		ArrayList<Integer> listRecID = new ArrayList<Integer>(); 
		ArrayList<String> listEntityID = new ArrayList<String>(); 
		ArrayList<String> listProjID = new ArrayList<String>(); 
		ArrayList<String> listPblID = new ArrayList<String>(); 
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>(); 
		ArrayList<String> listActualDate = new ArrayList<String>(); 
		ArrayList<BigDecimal> listLoanableAmt = new ArrayList<BigDecimal>(); 
		ArrayList<BigDecimal> listNetProceeds = new ArrayList<BigDecimal>();
		
	
		for(int x = 0; x < model.getRowCount(); x++) {
			
			Integer rec_id = (Integer) model.getValueAt(x, 19); 
			String entity_id = (String) model.getValueAt(x, 20);
			String proj_id = (String) model.getValueAt(x, 21);
			String pbl_id = (String) model.getValueAt(x, 22);
			Integer seq_no = (Integer) model.getValueAt(x, 23);
			String actual_date = (String) model.getValueAt(x, 24).toString(); 
			BigDecimal loanable_amt = (BigDecimal) model.getValueAt(x, 5); 
			BigDecimal net_proceeds = (BigDecimal) model.getValueAt(x, 4);
			
			listRecID.add(rec_id); 
			listEntityID.add(String.format("'%s'", entity_id));
			listProjID.add(String.format("'%s'", proj_id)); 
			listPblID.add(String.format("'%s'", pbl_id));
			listSeqNo.add(seq_no);
			listActualDate.add(String.format("'%s'", actual_date));
			listLoanableAmt.add(loanable_amt);
			listNetProceeds.add(net_proceeds);	
	
		}
		
		String rec_id = listRecID.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", ""); 
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", ""); 
		String pbl_id = listPblID.toString().replaceAll("\\[|\\]", ""); 
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", ""); 
		String actual_date = listActualDate.toString().replaceAll("\\[|\\]", ""); 
		String loanable_amt = listLoanableAmt.toString().replaceAll("\\[|\\]", ""); 
		String net_proceeds = listNetProceeds.toString().replaceAll("\\[|\\]", "");
		
		
		pgSelect db =  new pgSelect(); 
		
		String SQL = "SELECT sp_seq_loan_release('"+co_id+"', ARRAY["+rec_id+"]::INT[], ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[],"
				+ " ARRAY["+actual_date+"]::VARCHAR[], ARRAY["+loanable_amt+"]::NUMERIC[], ARRAY["+net_proceeds+"]::NUMERIC[], '"+UserInfo.EmployeeCode+"')"; 
		
		db.select(SQL);
		
		if(db.isNotNull()) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts are posted!", "Post", JOptionPane.INFORMATION_MESSAGE);		
		} else {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Something went wrong.", "Save", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	private void previewLoanReleased_ForChecking() {
		ArrayList<TD_LoanReleased_Issuance> listLoanReleased_ForChecking = new ArrayList<TD_LoanReleased_Issuance>(); 

		for (int x=0; x < model_tablelist.getRowCount(); x++) {

			String client_name = (String) model_tablelist.getValueAt(x, 2); 
			String unit = (String) model_tablelist.getValueAt(x, 3); 
			BigDecimal loanable_amount = (BigDecimal) model_tablelist.getValueAt(x, 5); 
			BigDecimal mri_sri_doc_stamp = (BigDecimal) model_tablelist.getValueAt(x, 6); 
			BigDecimal fire = (BigDecimal) model_tablelist.getValueAt(x, 7);
			BigDecimal processing_fee = (BigDecimal) model_tablelist.getValueAt(x, 8);	
			BigDecimal appraisal_fee = (BigDecimal) model_tablelist.getValueAt(x, 9);
			BigDecimal interim_mri = (BigDecimal) model_tablelist.getValueAt(x, 10); 
			BigDecimal retention_fee = (BigDecimal) model_tablelist.getValueAt(x, 11); 
			BigDecimal service_fee = (BigDecimal) model_tablelist.getValueAt(x, 18);
			BigDecimal net_proceeds = (BigDecimal) model_tablelist.getValueAt(x, 4);
			
			BigDecimal total_deductions = mri_sri_doc_stamp.add(fire).add(processing_fee).add(appraisal_fee).add(interim_mri).add(retention_fee).add(service_fee);
			
			Date loan_released = (Date) model_tablelist.getValueAt(x, 24);

			listLoanReleased_ForChecking.add(new TD_LoanReleased_Issuance(client_name, unit, loanable_amount, mri_sri_doc_stamp, fire, processing_fee, appraisal_fee,
					interim_mri, retention_fee, service_fee, net_proceeds, loan_released, total_deductions)); 

		}

		Map<String, Object> mapParameters = new HashMap<String, Object>(); 
		mapParameters.put("company_name", company_name); 
		mapParameters.put("checked_by", UserInfo.FullName);
		mapParameters.put("listLoanReleased_ForChecking", listLoanReleased_ForChecking); 

		System.out.println("Company:" + company_name);
		System.out.println("Checked by:" + UserInfo.FullName);
		System.out.println("List for Checking: " + listLoanReleased_ForChecking);

		FncReport.generateReport("/Reports/rptLoanReleasedforChecking.jasper", "Loan Released For Checking", mapParameters);
		
		System.out.println("Report name: rptLoanReleasedforChecking");

	}

}
