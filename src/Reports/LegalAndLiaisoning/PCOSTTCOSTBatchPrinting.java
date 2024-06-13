package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Accounting.Collections.CheckStatusMonitoring;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Reports.Accounting.SummaryofDeposits;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import tablemodel.modelTransactionSummary;

public class PCOSTTCOSTBatchPrinting extends _JInternalFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Dimension framesize = new Dimension(700, 600);
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;
	private _JXTextField txtSearch;
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnCancel;
	protected String co_id;
	protected String company;
	protected String proj_id;
	protected String proj_name;
	private modelTransactionSummary modelPCOSTList;
	private JScrollPane scrollPCOST;
	private _JTableMain tblPCOST;
	private JScrollPane scrollTCOST;
	private modelTransactionSummary modelTCOSTList;
	private _JTableMain tblTCOST;
	private JLabel lblDateFrom;
	private _JDateChooser dteDateFrom;
	private JLabel lblDateTo;
	private _JDateChooser dteDateTo;
	private JTabbedPane tabPane;
	private JButton btnRefresh;
	private JButton btnDelete;

	public PCOSTTCOSTBatchPrinting() {
		super("PCOST/TCOST Batch Printing", true, true, true, true);
		initGUI();

	}

	private void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(framesize);
		this.setMinimumSize(framesize);
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

						JPanel pnlCompany = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCompany, c);
						pnlCompany.setBorder(JTBorderFactory.createTitleBorder("Company"));
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.BOTH;
							cons_com.insets = new Insets(5, 5, 5, 5);
							//LINE1
							{
								cons_com.weightx = 0;
								cons_com.weighty = 0;

								cons_com.gridx = 0;
								cons_com.gridy = 0;

								lblCompany = new JLabel("Company", JLabel.CENTER);
								pnlCompany.add(lblCompany, cons_com);
								lblCompany.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1;
								cons_com.gridy = 0;

								lookupCompany = new _JLookup(null, "Company");
								pnlCompany.add(lookupCompany, cons_com);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SummaryofDeposits.company());
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								//lookupCompany.setPreferredSize(new Dimension(60,0));
								lookupCompany.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object[] data = ((_JLookup) event.getSource()).getDataSet();

										if(data!=null) {
											co_id = (String) data[0];
											company = (String) data[1];

											lookupProject.setValue(null);
											txtProject.setText("");

											txtCompany.setText(company);
											lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
										}	
									}
								});
							}
							{
								cons_com.weightx = 1;
								cons_com.weighty = 1;

								cons_com.gridx = 2;
								cons_com.gridy = 0;

								txtCompany = new JTextField();
								pnlCompany.add(txtCompany, cons_com);
								txtCompany.setEditable(false);
								txtCompany.setFont(FncGlobal.sizeTextValue);
							}

							//LINE2
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0;
								cons_com.gridy = 1;

								lblProject = new JLabel("Project", JLabel.CENTER);
								pnlCompany.add(lblProject, cons_com);
								lblProject.setEnabled(true);
								lblProject.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1;
								cons_com.gridy = 1;

								lookupProject = new _JLookup(null, "Project");
								pnlCompany.add(lookupProject, cons_com);
								lookupProject.setReturnColumn(0);
								lookupProject.setFont(FncGlobal.sizeTextValue);
								//lookupProject.setPreferredSize(new Dimension(60,0));
								lookupProject.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {

										Object[] data = ((_JLookup) event.getSource()).getDataSet();

										if(data!=null) {
											proj_id = (String) data[0];		
											proj_name = (String) data[1];

											txtProject.setText(proj_name);
											txtSearch.setEditable(true);

											lblDateFrom.setEnabled(true);
											lblDateTo.setEnabled(true);
											dteDateFrom.setEnabled(true);
											dteDateTo.setEnabled(true);

											btnPreview.setEnabled(true);
											btnRefresh.setEnabled(true);

											//displayPCOST(modelPCOSTList, co_id, proj_id);	
											//displayTCOST(modelTCOSTList, co_id, proj_id);
										}	
									}
								});
							}
							{
								cons_com.weightx = 1;
								cons_com.weighty = 1;

								cons_com.gridx = 2;
								cons_com.gridy = 1;

								txtProject = new JTextField();
								pnlCompany.add(txtProject, cons_com);
								txtProject.setEditable(false);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0;
						c.gridy = 1;

						JPanel pnlCreate = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCreate, c);
						pnlCreate.setBorder(components.JTBorderFactory.createTitleBorder("Created Date"));
						{
							GridBagConstraints cons_dtecreated = new GridBagConstraints();
							cons_dtecreated.fill = GridBagConstraints.BOTH;
							cons_dtecreated.insets = new Insets(5, 5, 5, 5);

							{
								cons_dtecreated.weightx = 0.5;
								cons_dtecreated.weighty = 1;

								cons_dtecreated.gridx = 0; 
								cons_dtecreated.gridy = 0; 

								lblDateFrom = new JLabel("Date From", JLabel.CENTER);
								pnlCreate.add(lblDateFrom, cons_dtecreated);
								lblDateFrom.setEnabled(false);
								lblDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_dtecreated.weightx = 1;
								cons_dtecreated.weighty = 1;

								cons_dtecreated.gridx = 1; 
								cons_dtecreated.gridy = 0; 

								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCreate.add(dteDateFrom, cons_dtecreated);						
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(false);
								dteDateFrom.setFont(FncGlobal.sizeTextValue);
								dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
							}
							{
								cons_dtecreated.weightx = 0.5;
								cons_dtecreated.weighty = 1;

								cons_dtecreated.gridx = 2; 
								cons_dtecreated.gridy = 0; 

								lblDateTo = new JLabel("Date To", JLabel.CENTER);
								pnlCreate.add(lblDateTo, cons_dtecreated);
								lblDateTo.setEnabled(false);
								lblDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_dtecreated.weightx = 1;
								cons_dtecreated.weighty = 1;

								cons_dtecreated.gridx = 3; 
								cons_dtecreated.gridy = 0;

								dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCreate.add(dteDateTo, cons_dtecreated);
								dteDateTo.setDate(null);
								dteDateTo.setEnabled(false);
								dteDateTo.setFont(FncGlobal.sizeTextValue);
								dteDateTo.setDate(FncGlobal.getDateToday());
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0;
						c.gridy = 2;

						JPanel pnlSearch = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlSearch, c);
						pnlSearch.setBorder(JTBorderFactory.createTitleBorder("Search"));
						{
							GridBagConstraints cons_sch = new GridBagConstraints();
							cons_sch.fill = GridBagConstraints.BOTH;
							cons_sch.insets = new Insets(5, 5, 5, 5);

							{
								cons_sch.weightx = 0;
								cons_sch.weighty = 1;

								cons_sch.gridx = 0;
								cons_sch.gridy = 0;

								JLabel lblSearch = new JLabel("**Search:");
								pnlSearch.add(lblSearch, cons_sch);
								lblSearch.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_sch.weightx = 1;
								cons_sch.weighty = 1;

								cons_sch.gridx = 1;
								cons_sch.gridy = 0;

								txtSearch = new _JXTextField("Search batch number...");
								pnlSearch.add(txtSearch, cons_sch);
								txtSearch.setFont(FncGlobal.sizeTextValue);
								txtSearch.addKeyListener(new KeyListener() {

									@Override
									public void keyTyped(KeyEvent e) {
										// TODO Auto-generated method stub

									}

									@Override
									public void keyReleased(KeyEvent e) {
										// TODO Auto-generated method stub
										searchBatchNo();

									}

									@Override
									public void keyPressed(KeyEvent e) {
										// TODO Auto-generated method stub

									}
								});
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 2;

						c.gridx = 0;
						c.gridy = 3;

						tabPane= new JTabbedPane(); 
						pnlCenter.add(tabPane, c);
						tabPane.setPreferredSize(new Dimension(0,500));
						{
							JPanel pnlPcost = new JPanel(new BorderLayout(5,5));
							tabPane.add("PCOST", pnlPcost);
							{
								scrollPCOST = new JScrollPane();
								pnlPcost.add(scrollPCOST);
								scrollPCOST.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							}
							{
								modelPCOSTList = new modelTransactionSummary();
								tblPCOST = new _JTableMain(modelPCOSTList);

								scrollPCOST.setViewportView(tblPCOST);
								tblPCOST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								tblPCOST.setFont(FncGlobal.sizeTextValue);
							}
						}
						{
							JPanel pnlTcost = new JPanel(new BorderLayout(5,5));
							tabPane.add("TCOST", pnlTcost);
							{
								scrollTCOST = new JScrollPane();
								pnlTcost.add(scrollTCOST);
								scrollTCOST.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							}
							{
								modelTCOSTList = new modelTransactionSummary();
								tblTCOST = new _JTableMain(modelTCOSTList);

								scrollTCOST.setViewportView(tblTCOST);
								tblTCOST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								tblTCOST.setFont(FncGlobal.sizeTextValue);
							}
						}

					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1,4,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnPreview = new JButton("Generate");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Generate");
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
					btnPreview.setEnabled(true);
				}
				{/*ADDED BY JED 2021-09-13 : FOR DELETING BATCHES IN THE SAME TIME*/
					btnDelete = new JButton("Delete");
					pnlSouth.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.addActionListener(this);
					btnDelete.setFont(FncGlobal.sizeControls);
					btnDelete.setEnabled(false);
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlSouth.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.addActionListener(this);
					btnRefresh.setFont(FncGlobal.sizeControls);
					btnRefresh.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setFont(FncGlobal.sizeControls);
				}
			}
		}

		initialize_comp();
	}

	private void initialize_comp() {

		co_id = "";
		lookupCompany.setValue("");
		txtCompany.setText("");
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

	}

	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Generate")) {		generateDetail();}

		if(e.getActionCommand().equals("Preview")) {		preview();}

		if(e.getActionCommand().equals("Refresh")) {		refreshTables();}

		if(e.getActionCommand().equals("Cancel")) {			cancel();}

		/*ADDED BY JED 2021-09-13 : FOR DELETING BATCHES IN THE SAME TIME*/
		if(e.getActionCommand().equals("Delete")) {			delete();}


	}

	private void delete() {/*ADDED BY JED 2021-09-13 : FOR DELETING BATCHES IN THE SAME TIME*/

		int index = tabPane.getSelectedIndex();
		java.util.Date dateFrom = dteDateFrom.getDate();
		java.util.Date dateTo = dteDateTo.getDate();


		if(index == 0) {
			if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete this/these batch?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				for(int x = 0; x < modelPCOSTList.getRowCount(); x++ ) {
					Boolean isSelected = (Boolean) modelPCOSTList.getValueAt(x, 0);
					if(isSelected){
						pgUpdate db = new pgUpdate();
						String batch_no = (String) modelPCOSTList.getValueAt(x, 1);
						String emp_code = UserInfo.EmployeeCode;
						String rplf_no = (String) modelPCOSTList.getValueAt(x, 3);

						System.out.printf("batch_no:%s\n", batch_no);
						System.out.printf("rplf_no:%s\n", rplf_no);

						String sql = "update rf_processing_cost set status_id = 'I', edited_by = '"+emp_code+"', date_edited = now() where batch_no = '"+batch_no+"' and status_id = 'A' and co_id = '"+co_id+"'";
						db.executeUpdate(sql, true);
						db.commit();

						if(rplf_no.equals("") || rplf_no.equals("null")) {

						}else {
							System.out.println("@@PCOST WITH RPLF@@");
							pgUpdate db2 = new pgUpdate();
							String sql2 = "update rf_request_header set status_id = 'I', edited_by = '"+emp_code+"', date_edited = now() where rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"'";
							db2.executeUpdate(sql2, true);
							db2.commit();
						}



					}
				}
				JOptionPane.showMessageDialog(getContentPane(), "Succesfully deleted!", "Delete", JOptionPane.INFORMATION_MESSAGE);
				displayPCOST(modelPCOSTList, co_id, proj_id, dateFrom, dateTo);
			}
		}

		if(index == 1) {
			if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete this/these batch?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				for(int x = 0; x < modelTCOSTList.getRowCount(); x++ ) {
					Boolean isSelected = (Boolean) modelTCOSTList.getValueAt(x, 0);
					if(isSelected){
						pgUpdate db = new pgUpdate();
						String batch_no = (String) modelTCOSTList.getValueAt(x, 1);
						String emp_code = UserInfo.EmployeeCode;
						String rplf_no = (String) modelTCOSTList.getValueAt(x, 3);

						System.out.printf("batch_no:%s\n", batch_no);
						System.out.printf("rplf_no:%s\n", rplf_no);

						String sql = "update rf_transfer_cost set status_id = 'I', edited_by = '"+emp_code+"', date_edited = now() where batch_no = '"+batch_no+"' and status_id = 'A' and co_id = '"+co_id+"'";
						db.executeUpdate(sql, true);
						db.commit();

						if(rplf_no.equals("") || rplf_no.equals("null")) {

						}else {
							System.out.println("@@TCOST WITH RPLF@@");
							pgUpdate db2 = new pgUpdate();
							String sql2 = "update rf_request_header set status_id = 'I', edited_by = '"+emp_code+"', date_edited = now() where rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"'";
							db2.executeUpdate(sql2, true);
							db2.commit();
						}
					}
				}
				JOptionPane.showMessageDialog(getContentPane(), "Succesfully deleted!", "Delete", JOptionPane.INFORMATION_MESSAGE);
				displayTCOST(modelTCOSTList, co_id, proj_id, dateFrom, dateTo);
			}
		}
	}

	private void cancel() {

		if(JOptionPane.showConfirmDialog(getContentPane(), "Do you want to clear the fields?", "Cancel", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {


			JOptionPane.showMessageDialog(getContentPane(), "Successfully cleared!", "Cancel", JOptionPane.INFORMATION_MESSAGE);

			modelPCOSTList.clear();
			modelTCOSTList.clear();		
			lookupCompany.setValue(null);
			txtCompany.setText("");
			lookupProject.setValue(null);
			txtProject.setText("");
			lblDateFrom.setEnabled(false);
			lblDateTo.setEnabled(false);
			dteDateFrom.setEnabled(false);
			dteDateTo.setEnabled(false);
			dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
			dteDateTo.setDate(FncGlobal.getDateToday());
			txtSearch.setText("");
			txtSearch.setEditable(false);
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
			btnPreview.setEnabled(false);
			btnDelete.setEnabled(false);

		}

	}

	private void refreshTables() {

		JOptionPane.showMessageDialog(getContentPane(), "Data is refreshed!", "Refresh",  JOptionPane.INFORMATION_MESSAGE);

		java.util.Date dateFrom = dteDateFrom.getDate();
		java.util.Date dateTo = dteDateTo.getDate();

		displayPCOST(modelPCOSTList, co_id, proj_id, dateFrom, dateTo);
		displayTCOST(modelTCOSTList, co_id, proj_id, dateFrom, dateTo);

	}

	private void preview() {
		// TODO Auto-generated method stub
		java.util.Date dateFrom = dteDateFrom.getDate();
		java.util.Date dateTo = dteDateTo.getDate();
		String co_name = txtCompany.getText();
		String proj_name = txtProject.getText();
		String emp_alias = UserInfo.Alias;
		String emp_name = UserInfo.FullName2;

		ArrayList<String> listBatch = new ArrayList<String>();

		if (tabPane.getSelectedIndex() == 0) {
			String report_name = "PROCESSING COST REPORT";

			for (int x = 0; x < modelPCOSTList.getRowCount(); x++) {
				Boolean isSelected = (Boolean) modelPCOSTList.getValueAt(x, 0);
				if(isSelected) {
					String batch_no = (String) modelPCOSTList.getValueAt(x, 1);
					listBatch.add(String.format("%s", batch_no));
				}
			}

			if(listBatch.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select first for preview!", "Error", JOptionPane.ERROR_MESSAGE);
			}else {
				String batch_no = listBatch.toString().replaceAll("\\[", "").replaceAll("\\]","");
				System.out.printf("The value of batch_no: (%s)\n", batch_no);

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("p_co_id", co_id);
				mapParameters.put("p_proj_id", proj_id);
				mapParameters.put("p_batch_no", batch_no);
				mapParameters.put("p_date_from", dateFrom);
				mapParameters.put("p_date_to", dateTo);
				mapParameters.put("p_report_name", report_name);
				mapParameters.put("p_proj_name", proj_name);
				mapParameters.put("p_co_name", co_name);
				mapParameters.put("p_emp_alias", emp_alias);
				mapParameters.put("p_emp_name", emp_name);
				mapParameters.put("p_emp_code", UserInfo.EmployeeCode);


				/*FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Processing Cost Report", mapParameters);

				String SQL = "select * from view_pcost_tcost_bp('"+co_id+"', '"+proj_id+"', '"+batch_no+"', '"+report_name+"')";
				System.out.println("view_pcost_tcost_bp: "+SQL);

				FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Processing Cost Report", mapParameters);

				FncReport.generateReport("/Reports/rptPCOSTDisbursement_Req_BP.jasper", "PCOST Disbursement Request", mapParameters); */

				if(lookupCompany.getValue().equals("02")){
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2CDC.jasper", "Processing Cost Report", mapParameters);

					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "PCOST Disbursement Request" , mapParameters);

					String SQL1 = "select * from view_pcost_tcost_bp_v2('"+co_id+"', NULLIF('"+proj_id+"','null'), '"+batch_no+"', '"+report_name+"')";
					System.out.println("view_pcost_tcost_bp: "+SQL1);
				} 

				else if(lookupCompany.getValue().equals("05")){
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2EDC.jasper", "Processing Cost Report", mapParameters);

					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "PCOST Disbursement Request", mapParameters);

					String SQL1 = "select * from view_pcost_tcost_bp_v2('"+co_id+"', NULLIF('"+proj_id+"','null'), '"+batch_no+"', '"+report_name+"')";
					System.out.println("view_pcost_tcost_bp: "+SQL1);
				}

				else {
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Processing Cost Report", mapParameters);

					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "PCOST Disbursement Request", mapParameters);

					String SQL3 = "select * from view_pcost_tcost_bp_v2('"+co_id+"', NULLIF('"+proj_id+"','null'), '"+batch_no+"', '"+report_name+"')";
					System.out.println("view_pcost_tcost_bp: "+SQL3);
				}

			}
		}

		if (tabPane.getSelectedIndex() == 1) {
			String report_name = "TRANSFER COST REPORT";

			for (int x = 0; x < modelTCOSTList.getRowCount(); x++) {
				Boolean isSelected = (Boolean) modelTCOSTList.getValueAt(x, 0);
				if(isSelected) {
					String batch_no = (String) modelTCOSTList.getValueAt(x, 1);
					listBatch.add(String.format("%s", batch_no));
				}
			}

			if(listBatch.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select first for preview!", "Error", JOptionPane.ERROR_MESSAGE);
			}else {
				String batch_no = listBatch.toString().replaceAll("\\[", "").replaceAll("\\]","");
				System.out.printf("The value of batch_no: (%s)\n", batch_no);

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("p_co_id", co_id);
				mapParameters.put("p_proj_id", proj_id);
				mapParameters.put("p_batch_no", batch_no);
				mapParameters.put("p_date_from", dateFrom);
				mapParameters.put("p_date_to", dateTo);
				mapParameters.put("p_report_name", report_name);
				mapParameters.put("p_proj_name", proj_name);
				mapParameters.put("p_co_name", co_name);
				mapParameters.put("p_emp_alias", emp_alias);
				mapParameters.put("p_emp_name", emp_name);
				mapParameters.put("p_emp_code", UserInfo.EmployeeCode);
				
				if(lookupCompany.getValue().equals("02")){
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2CDC.jasper", "Transfer Cost Report", mapParameters);

					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "TCOST Disbursement Request", mapParameters);

					String SQL1 = "select * from view_pcost_tcost_bp_v2('"+co_id+"', NULLIF('"+proj_id+"','null'), '"+batch_no+"', '"+report_name+"')";
					System.out.println("view_pcost_tcost_bp: "+SQL1);
				} 

				else if(lookupCompany.getValue().equals("05")){
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2EDC.jasper", "Transfer Cost Report", mapParameters);

					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "TCOST Disbursement Request", mapParameters);

					String SQL1 = "select * from view_pcost_tcost_bp_v2('"+co_id+"', NULLIF('"+proj_id+"','null'), '"+batch_no+"', '"+report_name+"')";
					System.out.println("view_pcost_tcost_bp: "+SQL1);
				}
				else if(lookupCompany.getValue().equals("04")) {
					//FncReport.generateReport("/Reports/rptTransferCost2ADC.jasper", "Transfer Cost Report", null, mapParameters);
					FncReport.generateReport("/Reports/rptTransferCost2ADC.jasper", "TCOST Disbursement Request", mapParameters);
					System.out.println("Co ID: 04");		
				}

				else {
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Transfer Cost Report", mapParameters);

					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "TCOST Disbursement Request", mapParameters);

					String SQL3 = "select * from view_pcost_tcost_bp_v2('"+co_id+"', NULLIF('"+proj_id+"','null'), '"+batch_no+"', '"+report_name+"')";
					System.out.println("view_pcost_tcost_bp: "+SQL3);
				}
			}
		}
	}

	private void generateDetail() {
		// TODO Auto-generated method stub

		java.util.Date dateFrom = dteDateFrom.getDate();
		java.util.Date dateTo = dteDateTo.getDate();

		displayPCOST(modelPCOSTList, co_id, proj_id, dateFrom, dateTo);	
		displayTCOST(modelTCOSTList, co_id, proj_id, dateFrom, dateTo);

		btnPreview.setText("Preview");
		btnPreview.setActionCommand("Preview");
		btnDelete.setEnabled(true);

	}

	/*edited by jed : remove all batch no that has already pv no generated by accounting*/
	private void displayPCOST(DefaultTableModel modelMain, String co_id, String proj_id, java.util.Date dateFrom, java.util.Date dateTo) {

		modelMain.setRowCount(0);
		DefaultListModel listModel = new DefaultListModel();

		String sql = 
				"select distinct on (a.batch_no)\n" + 
						"false,\n" + 
						"a.batch_no,\n" + 
						"c.entity_name,\n" + 
						"a.rplf_no,\n" + 
						"a.status_id,\n" + 
						"a.tran_date::date\n" + 
						"from rf_processing_cost a\n" + 
						"left join em_employee b on a.created_by = b.emp_code\n" + 
						"left join rf_entity c on b.entity_id = c.entity_id\n" + 
						"where a.co_id = '"+co_id+"'\n" + 
						"and a.projcode = '"+proj_id+"'\n" + 
						"and a.status_id = 'A'\n" + 
						"and a.tran_date between '"+dateFrom+"' and '"+dateTo+"'\n" +
						"and not exists (select pv_no from rf_pv_header where pv_no = a.rplf_no and co_id = a.co_id and status_id in ('A', 'F', 'P'))\n" +
						"group by a.batch_no, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created, a.pcostid_dl\n" + 
						"order by a.batch_no DESC";


		FncSystem.out("displayPCOST", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}

		tblPCOST.packAll();

	}

	/*edited by jed : remove all batch no that has already pv no generated by accounting*/
	private void displayTCOST(DefaultTableModel modelMain, String co_id, String proj_id, java.util.Date dateFrom, java.util.Date dateTo) {

		modelMain.setRowCount(0);
		DefaultListModel listModel = new DefaultListModel();

		String sql = 
				"select distinct on (a.batch_no)\n" + 
						"false,\n" + 
						"a.batch_no,\n" + 
						"c.entity_name,\n" + 
						"a.rplf_no,\n" + 
						"a.status_id,\n" + 
						"a.tran_date::date\n" + 
						"from rf_transfer_cost a\n" + 
						"left join em_employee b on a.created_by = b.emp_code\n" + 
						"left join rf_entity c on b.entity_id = c.entity_id\n" + 
						"where a.co_id = '"+co_id+"'\n" + 
						"and a.projcode = NULLIF(a.projcode,'"+proj_id+"') is null\n" + 
						"and a.status_id = 'A'\n" + 
						"and a.tran_date between '"+dateFrom+"' and '"+dateTo+"'\n" +
						"and not exists (select pv_no from rf_pv_header where pv_no = a.rplf_no and co_id = a.co_id and status_id in ('A', 'F', 'P'))\n"+
						"group by a.batch_no, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created, a.tcostid_dl\n" + 
						"order by a.batch_no DESC";


		FncSystem.out("displayTCOST", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}

		tblTCOST.packAll();

	}

	private void searchBatchNo() {

		if (tabPane.getSelectedIndex() == 0) {

			int rw = tblPCOST.getModel().getRowCount();
			int x = 0;

			while (x < rw) {

				String batch = "";

				try {
					batch = tblPCOST.getValueAt(x, 1).toString().toUpperCase();
				} catch (NullPointerException e) {
					batch = "";
				}

				String strBatch = txtSearch.getText().trim().toUpperCase();
				// Boolean match = name.indexOf(acct_name)>0;
				Boolean start = batch.contains(strBatch);
				// Boolean end = name.endsWith(module_name);

				if (start == true) {
					tblPCOST.setRowSelectionInterval(x, x);
					tblPCOST.changeSelection(x, 1, false, false);
					break;
				} else {
					tblPCOST.setRowSelectionInterval(0, 0);
					tblPCOST.changeSelection(0, 1, false, false);
				}

				x++;
			}
		}

		if (tabPane.getSelectedIndex() == 1) {

			int rw = tblTCOST.getModel().getRowCount();
			int x = 0;

			while (x < rw) {

				String batch = "";

				try {
					batch = tblTCOST.getValueAt(x, 1).toString().toUpperCase();
				} catch (NullPointerException e) {
					batch = "";
				}

				String strBatch = txtSearch.getText().trim().toUpperCase();
				// Boolean match = name.indexOf(acct_name)>0;
				Boolean start = batch.contains(strBatch);
				// Boolean end = name.endsWith(module_name);

				if (start == true) {
					tblTCOST.setRowSelectionInterval(x, x);
					tblTCOST.changeSelection(x, 1, false, false);
					break;
				} else {
					tblTCOST.setRowSelectionInterval(0, 0);
					tblTCOST.changeSelection(0, 1, false, false);
				}

				x++;
			}
		}
	}

}
