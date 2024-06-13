package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JFormattedTextField;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelEditTaxPayment;

public class EditTaxPayments extends _JInternalFrame implements _GUI, ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension frameSize = new Dimension(900, 600);
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JTextField txtCompany;
	private JScrollPane scrollTax;
	private modelEditTaxPayment modelTax;
	private _JTableMain tblTax;
	private JList rowHeaderTax;
	private JTextField txtProject;
	private JButton btnSave;
	private JButton btnEdit;
	private JButton btnCancel;
	private _JLookup lookupBatch;
	private String co_id = "";
	protected String proj_id = "";
	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private JTextField txtORNo;
	private JTextField txtYear;
	private _JFormattedTextField txtAmount;
	private JButton btnDelete;
	private JLabel lblProject;
	private JLabel lblBatch;
	private JLabel lblORNo;
	private JLabel lblYear;
	private JLabel lblAmount;
	private JLabel lblCompany;
	
	public EditTaxPayments() {
		super("Edit Tax Payments", true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 0; 
						
						JPanel pnlDetails = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlDetails, c);		
						pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Details" )); 
						{
							GridBagConstraints cons_details = new GridBagConstraints();
							cons_details.fill = GridBagConstraints.BOTH;
							cons_details.insets = new Insets(5, 5, 5, 5);
							//LINE 1 company
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;

								cons_details.gridx = 0; 
								cons_details.gridy = 0; 

								lblCompany = new JLabel("Company:");
								pnlDetails.add(lblCompany, cons_details);
								lblCompany.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_details.weightx = 0.5;
								cons_details.weighty = 1;

								cons_details.gridx = 1; 
								cons_details.gridy = 0; 

								lookupCompany = new _JLookup(null, "Company");
								pnlDetails.add(lookupCompany, cons_details);
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.addLookupListener(new LookupListener() {
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null) {
											co_id = (String) data[0];
											String co_name = (String) data[1];
											
											txtCompany.setText(co_name);
											
										}
									}
								});
							}
							{
								cons_details.weightx = 1.5;
								cons_details.weighty = 1;

								cons_details.gridx = 2; 
								cons_details.gridy = 0; 

								txtCompany = new JTextField("");
								pnlDetails.add(txtCompany, cons_details);
								txtCompany.setEditable(false);
								txtCompany.setFont(FncGlobal.sizeTextValue);
							}

							//LINE 2 project
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;

								cons_details.gridx = 0; 
								cons_details.gridy = 1; 

								lblProject = new JLabel("Project:");
								pnlDetails.add(lblProject, cons_details);	
								lblProject.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_details.weightx = 0.5;
								cons_details.weighty = 1;

								cons_details.gridx = 1; 
								cons_details.gridy = 1; 								

								lookupProject = new _JLookup(null, "Project");
								pnlDetails.add(lookupProject, cons_details);
								lookupProject.setLookupSQL(SQL_PROJECT());
								lookupProject.setReturnColumn(0);
								lookupProject.setFont(FncGlobal.sizeTextValue);
								lookupProject.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null) {
											proj_id = (String) data[0];
											String proj_name = (String) data[1];
											
											txtProject.setText(proj_name);
											lookupBatch.setLookupSQL(getBatchNo(co_id, proj_id));
										}
									}
								});
							}
							{
								cons_details.weightx = 1.5;
								cons_details.weighty = 1;

								cons_details.gridx = 2; 
								cons_details.gridy = 1; 
								
								txtProject = new JTextField("");
								pnlDetails.add(txtProject, cons_details);
								txtProject.setEditable(false);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}
							
							//LINE 3 batchno
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;

								cons_details.gridx = 0; 
								cons_details.gridy = 2;
								
								lblBatch = new JLabel("Batch No.:");
								pnlDetails.add(lblBatch, cons_details);
								lblBatch.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_details.weightx = 0.5;
								cons_details.weighty = 1;

								cons_details.gridx = 1; 
								cons_details.gridy = 2; 
								
								lookupBatch = new _JLookup(null, "Project");
								pnlDetails.add(lookupBatch, cons_details);
								lookupBatch.setEnabled(false);
								lookupBatch.setReturnColumn(0);
								lookupBatch.setFont(FncGlobal.sizeTextValue);
								lookupBatch.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null) {
											String batch_no = (String) data[0];
											
											displayRPTPayments(modelTax, rowHeaderTax, batch_no, co_id, proj_id);
																			
											txtORNo.setText("");
											txtYear.setText("");
											txtAmount.setValue(0.00);
											btnSave.setEnabled(false);
											btnEdit.setEnabled(false);
											btnDelete.setEnabled(false);
										}
									}
								});
							}
							
							//LINE 4 OR No.
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;
								
								cons_details.gridx = 0;
								cons_details.gridy = 3;
								
								lblORNo = new JLabel("OR No.:");
								pnlDetails.add(lblORNo, cons_details);
								lblORNo.setFont(FncGlobal.sizeLabel);
								lblORNo.setEnabled(false);
							}
							{
								cons_details.weightx = 0.5;
								cons_details.weighty = 1;
								
								cons_details.gridx = 1;
								cons_details.gridy = 3;
								
								txtORNo = new JTextField("");
								pnlDetails.add(txtORNo, cons_details);
								txtORNo.setEnabled(false);
								txtORNo.setHorizontalAlignment(JTextField.RIGHT);
								txtORNo.setFont(FncGlobal.sizeTextValue);
								txtORNo.addKeyListener(new KeyAdapter() {
									public void keyTyped(KeyEvent e) {
										char c = e.getKeyChar();
										if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
											e.consume();  // ignore event
										}
									}
								});
							}
							
							//LINE 5 Year
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;
								
								cons_details.gridx = 0;
								cons_details.gridy = 4;
								
								lblYear = new JLabel("Year:");
								pnlDetails.add(lblYear, cons_details);
								lblYear.setFont(FncGlobal.sizeLabel);
								lblYear.setEnabled(false);
							}
							{
								cons_details.weightx = 0.5;
								cons_details.weighty = 1;
								
								cons_details.gridx = 1;
								cons_details.gridy = 4;
								
								txtYear = new JTextField("");
								pnlDetails.add(txtYear, cons_details);
								txtYear.setEnabled(false);
								txtYear.setHorizontalAlignment(JTextField.RIGHT);
								txtYear.setFont(FncGlobal.sizeTextValue);
								txtYear.addKeyListener(new KeyAdapter() {
									public void keyTyped(KeyEvent e) {
										char c = e.getKeyChar();
										if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
											e.consume();  // ignore event
										}
									}
								});
							}
							
							//LINE 6 Amount
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;
								
								cons_details.gridx = 0;
								cons_details.gridy = 5;
								
								lblAmount = new JLabel("Amount:");
								pnlDetails.add(lblAmount, cons_details);
								lblAmount.setFont(FncGlobal.sizeLabel);
								lblAmount.setEnabled(false);
							}
							{
								cons_details.weightx = 0.5;
								cons_details.weighty = 1;
								
								cons_details.gridx = 1;
								cons_details.gridy = 5;
								
								txtAmount = new _JFormattedTextField("0.00");
								pnlDetails.add(txtAmount, cons_details);
								txtAmount.setHorizontalAlignment(JLabel.RIGHT);
								txtAmount.setFont(FncGlobal.sizeTextValue);
								txtAmount.setEnabled(false);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 1.5;

						c.gridx = 0; 
						c.gridy = 1;
						
						scrollTax = new JScrollPane();
						pnlCenter.add(scrollTax, c);
						scrollTax.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
						{
							modelTax = new modelEditTaxPayment();
							tblTax = new _JTableMain(modelTax);
							
							scrollTax.setViewportView(tblTax );
							tblTax.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
							tblTax.hideColumns("Projcode");
							tblTax.hideColumns("ID");
							tblTax.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								
								@Override
								public void valueChanged(ListSelectionEvent e) {
									// TODO Auto-generated method stub
									if(!e.getValueIsAdjusting()) {
										if(tblTax.getSelectedRows().length>0) {
											int rw = tblTax.convertRowIndexToModel(tblTax.getSelectedRow());
											String or_no = (String) modelTax.getValueAt(rw, 6);
											Integer year = (Integer) modelTax.getValueAt(rw, 7);
											BigDecimal amount = (BigDecimal) modelTax.getValueAt(rw, 5);
											
											txtORNo.setText(or_no);
											txtYear.setText(Integer.toString(year));
											txtAmount.setValue(amount);
											
											btnEdit.setEnabled(true);
											btnDelete.setEnabled(true);
											
										}
									}
								}
							});
						}
						{ 
							rowHeaderTax = tblTax.getRowHeader(); 
							rowHeaderTax.setModel(new DefaultListModel()); 
							scrollTax.setRowHeaderView(rowHeaderTax);
							scrollTax.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header()); 
						}
					}
				}
			}
			{

				JPanel pnlSouth = new JPanel(new GridLayout(1,4,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.setEnabled(false);
					btnSave.addActionListener(this);
					btnSave.setFont(FncGlobal.sizeControls);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.setEnabled(false);
					btnEdit.addActionListener(this);
					btnEdit.setFont(FncGlobal.sizeControls);
				}
				{
					btnDelete = new JButton("Delete");
					pnlSouth.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.setEnabled(false);
					btnDelete.addActionListener(this);
					btnDelete.setFont(FncGlobal.sizeControls);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
					btnCancel.setFont(FncGlobal.sizeControls);
				}			
			}
		}
		initialize_comp();
	}
	
	private void initialize_comp() {
		
		co_id = "02";
		proj_id = "015";
		lookupCompany.setValue(co_id);
		lookupProject.setValue(proj_id);
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		txtProject.setText("TERRAVERDE RESIDENCES");
		lookupBatch.setLookupSQL(getBatchNo(co_id, proj_id));
		lookupBatch.setEnabled(true);

	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals("Save")) 		{	saveEntries();}
		
		if(arg0.getActionCommand().equals("Edit")) 		{	editEntries();}
		
		if(arg0.getActionCommand().equals("Delete")) 	{	deleteEntries();}
		
		if(arg0.getActionCommand().equals("Cancel")) 	{	cancel();}

	}

	private void saveEntries() {
		// TODO Auto-generated method stub
		if(JOptionPane.showConfirmDialog(getContentPane(), "Save entries?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			int rw = tblTax.convertRowIndexToModel(tblTax.getSelectedRow());
			if(tblTax.getSelectedRows().length>0) {
				String new_or_no = txtORNo.getText();
				String new_rpt_yr = txtYear.getText();
				BigDecimal new_amt = (BigDecimal) txtAmount.getValue();
				String edited_by = UserInfo.EmployeeCode;
				
				String batch_no = lookupBatch.getText();
				Integer proc_cost_id = (Integer) modelTax.getValueAt(rw, 9);
				String entity_id = (String) modelTax.getValueAt(rw, 0);
				String pbl_id = (String) modelTax.getValueAt(rw, 2);
				String proj_id = (String) modelTax.getValueAt(rw, 8);
				
				String sql = 
						"UPDATE public.rf_processing_cost\n" + 
						"SET pcost_amt="+new_amt+", setup_amt="+new_amt+", rpt_year='"+new_rpt_yr+"', rpt_or_no='"+new_or_no+"', edited_by='"+edited_by+"', date_edited=now()\n" + 
						"WHERE proc_cost_id = "+proc_cost_id+"\n" +
						"and entity_id = '"+entity_id+"'\n" +
						"and pbl_id = '"+pbl_id+"'\n" +
						"and projcode = '"+proj_id+"'\n" +
						"and batch_no = '"+batch_no+"'\n" +
						"and co_id = '"+co_id+"'\n" +
						"and status_id = 'A'";
				
				FncSystem.out("SQL for update RPT Payments", sql);
				pgUpdate db = new pgUpdate();
				db.executeUpdate(sql, false);
				db.commit();
				
				JOptionPane.showMessageDialog(getContentPane(), "Successfully saved!", "Save", JOptionPane.INFORMATION_MESSAGE);
				displayRPTPayments(modelTax, rowHeaderTax, batch_no, co_id, proj_id);
				txtORNo.setText("");
				txtYear.setText("");
				txtAmount.setValue(0.00);
				btnSave.setEnabled(false);
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				btnCancel.setEnabled(false);
				
				lblCompany.setEnabled(true);
				lblProject.setEnabled(true);
				lblBatch.setEnabled(true);
				lookupCompany.setEnabled(true);
				lookupProject.setEnabled(true);
				lookupBatch.setEnabled(true);
				tblTax.setEnabled(true);
				
				lblORNo.setEnabled(false);
				lblYear.setEnabled(false);
				lblAmount.setEnabled(false);
				txtORNo.setEnabled(false);
				txtYear.setEnabled(false);
				txtAmount.setEditable(false);
				txtAmount.setEnabled(false);
			}else {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a row!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void editEntries() {
		// TODO Auto-generated method stub
		
		btnSave.setEnabled(true);
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		btnCancel.setEnabled(true);
		
		lblCompany.setEnabled(false);
		lblProject.setEnabled(false);
		lblBatch.setEnabled(false);
		lookupCompany.setEnabled(false);
		lookupProject.setEnabled(false);
		lookupBatch.setEnabled(false);
		tblTax.setEnabled(false);
		
		lblORNo.setEnabled(true);
		lblYear.setEnabled(true);
		lblAmount.setEnabled(true);
		txtORNo.setEnabled(true);
		txtYear.setEnabled(true);
		txtAmount.setEditable(true);
		txtAmount.setEnabled(true);
	}
	

	private void deleteEntries() {
		// TODO Auto-generated method stub
		if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure want to delete this?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			int rw = tblTax.convertRowIndexToModel(tblTax.getSelectedRow());
			if(tblTax.getSelectedRows().length>0) {
				String batch_no = lookupBatch.getText();
				Integer proc_cost_id = (Integer) modelTax.getValueAt(rw, 9);
				String entity_id = (String) modelTax.getValueAt(rw, 0);
				String pbl_id = (String) modelTax.getValueAt(rw, 2);
				String proj_id = (String) modelTax.getValueAt(rw, 8);
				String edited_by = UserInfo.EmployeeCode;
				
				String sql =
						"UPDATE public.rf_processing_cost\n" + 
						"SET status_id = 'I', edited_by='"+edited_by+"', date_edited=now()\n" + 
						"WHERE proc_cost_id = "+proc_cost_id+"\n" +
						"and entity_id = '"+entity_id+"'\n" +
						"and pbl_id = '"+pbl_id+"'\n" +
						"and projcode = '"+proj_id+"'\n" +
						"and batch_no = '"+batch_no+"'\n" +
						"and co_id = '"+co_id+"'\n" +
						"and status_id = 'A'";
				
				FncSystem.out("SQL for update RPT Payments", sql);
				pgUpdate db = new pgUpdate();
				db.executeUpdate(sql, false);
				db.commit();
				
				JOptionPane.showMessageDialog(getContentPane(), "Successfully deleted!", "Delete", JOptionPane.INFORMATION_MESSAGE);
				displayRPTPayments(modelTax, rowHeaderTax, batch_no, co_id, proj_id);
				txtORNo.setText("");
				txtYear.setText("");
				txtAmount.setValue(0.00);
				btnSave.setEnabled(false);
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				btnCancel.setEnabled(false);
				
				lblCompany.setEnabled(true);
				lblProject.setEnabled(true);
				lblBatch.setEnabled(true);
				lookupCompany.setEnabled(true);
				lookupProject.setEnabled(true);
				lookupBatch.setEnabled(true);
				tblTax.setEnabled(true);
				
				lblORNo.setEnabled(false);
				lblYear.setEnabled(false);
				lblAmount.setEnabled(false);
				txtORNo.setEnabled(false);
				txtYear.setEnabled(false);
				txtAmount.setEditable(false);
				txtAmount.setEnabled(false);
			}	
		}
	}
	
	private void cancel() {
		// TODO Auto-generated method stub
		
		int rw = tblTax.convertRowIndexToModel(tblTax.getSelectedRow());
		String or_no = (String) modelTax.getValueAt(rw, 6);
		Integer year = (Integer) modelTax.getValueAt(rw, 7);
		BigDecimal amount = (BigDecimal) modelTax.getValueAt(rw, 5);
		
		txtORNo.setText(or_no);
		txtYear.setText(Integer.toString(year));
		txtAmount.setValue(amount);
		
		btnSave.setEnabled(false);
		btnEdit.setEnabled(true);
		btnDelete.setEnabled(true);
		btnCancel.setEnabled(false);
		
		lblCompany.setEnabled(true);
		lblProject.setEnabled(true);
		lblBatch.setEnabled(true);
		lookupCompany.setEnabled(true);
		lookupProject.setEnabled(true);
		lookupBatch.setEnabled(true);
		tblTax.setEnabled(true);
		
		lblORNo.setEnabled(false);
		lblYear.setEnabled(false);
		lblAmount.setEnabled(false);
		txtORNo.setEnabled(false);
		txtYear.setEnabled(false);
		txtAmount.setEditable(false);
		txtAmount.setEnabled(false);
		
	}
	
	private String getBatchNo(String co_id, String proj_id) {
		
		String sql = "select distinct on (batch_no::int) trim(batch_no)::varchar as \"Batch\"  from rf_processing_cost where pcostid_dl in ('215', '216') and status_id = 'A' and co_id = '"+co_id+"' and projcode = '"+proj_id+"' and rplf_no = '' order by batch_no::int DESC";
		
		return sql;
	}
	
	private void displayRPTPayments(DefaultTableModel modelMain, JList rowHeader, String batch_no, String co_id, String proj_id) {
		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		
		String sql = 
				"select\n" + 
				"a.entity_id,\n" + 
				"b.entity_name,\n" + 
				"a.pbl_id,\n" + 
				"format('%s-%s-%s', c.phase, c.block, c.lot) as unit,\n" + 
				"d.pcostdtl_desc,\n" + 
				"a.pcost_amt,\n" + 
				"a.rpt_or_no,\n" + 
				"a.rpt_year,\n" + 
				"a.projcode,\n" +
				"a.proc_cost_id\n" +
				"from rf_processing_cost a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"left join mf_unit_info c on a.pbl_id = c.pbl_id and a.projcode = c.proj_id\n" + 
				"left join (select * from mf_processing_cost_dl where status_id = 'A') d on a.pcostid_dl = d.pcostdtl_id\n" + 
				"where a.batch_no = '"+batch_no+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"and a.co_id = '"+co_id+"'\n" +
				"and projcode = '"+proj_id+"'";
		
		FncSystem.out("SQL for RPT Payments", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}
		scrollTax.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTax.getRowCount())));
		tblTax.packAll();
	}

}
