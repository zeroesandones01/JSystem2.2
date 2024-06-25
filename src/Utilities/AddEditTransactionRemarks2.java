package Utilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;

import Accounting.Collections.CheckStatusMonitoring;
import Database.pgSelect;
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
import tablemodel.modelTransactionSummary2;

public class AddEditTransactionRemarks2 extends _JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Dimension frameSize = new Dimension(700,600);
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;
	private JXTextField txtRemarks;
	private JPanel pnlSouth;
	private JButton btnSave;
	private JButton btnCancel;
	private JTabbedPane tabPane;
	private JScrollPane scrollPCOST;
	private modelTransactionSummary2 modelPCOSTList;
	private _JTableMain tblPCOST;
	private JScrollPane scrollTCOST;
	private modelTransactionSummary2 modelTCOSTList;
	private _JTableMain tblTCOST;
	private String co_id;
	private String company;
	private String proj_id;
	private String proj_name;
	private JButton btnPreview;
	private JPanel pnlPreview;
	private JButton btnOK;
	private JButton btnClose;
	private _JDateChooser dteDateFrom;
	private _JDateChooser dteDateTo;
	private _JLookup lookupbatch;
	private _JLookup lookuprplf;
	private JRadioButton rbutton_rplf;
	private JRadioButton rbutton_date;

	public AddEditTransactionRemarks2() {
		super("Add/Edit Remarks2", true, true, true, true);
		initGui();

	}

	private void initGui() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				pnlPreview = new JPanel(new BorderLayout(5,5));
				pnlPreview.setPreferredSize(new Dimension(600,110));
				//pnlPreview.setPreferredSize(new Dimension(600,80));
				{
					JPanel button_option = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlPreview.add(button_option, BorderLayout.WEST);
					button_option.setPreferredSize(new Dimension(50, 0));
					{
						rbutton_rplf = new JRadioButton();
						button_option.add(rbutton_rplf);
						rbutton_rplf.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								if(e.getStateChange() == ItemEvent.SELECTED) {
									lookuprplf.setEnabled(true);
									rbutton_date.setEnabled(false);
									dteDateFrom.setDate(null);
									dteDateTo.setDate(null);
								}
								if(e.getStateChange()==ItemEvent.DESELECTED){
									lookuprplf.setEnabled(false);
									rbutton_date.setEnabled(true);
									lookuprplf.setValue(null);
								}
							}
						});
					}
					{
						rbutton_date = new JRadioButton();
						button_option.add(rbutton_date);
						rbutton_date.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								if(e.getStateChange() == ItemEvent.SELECTED) {
									rbutton_rplf.setEnabled(false);
									dteDateFrom.setDate(FncGlobal.getDateToday());
									dteDateTo.setDate(FncGlobal.getDateToday());
									dteDateFrom.setEnabled(true);
									dteDateTo.setEnabled(true);
									lookuprplf.setValue(null);
								}
								if(e.getStateChange() == ItemEvent.DESELECTED) {
									rbutton_rplf.setEnabled(true);		
									dteDateFrom.setDate(null);
									dteDateTo.setDate(null);
									dteDateFrom.setEnabled(false);
									dteDateTo.setEnabled(false);
								}
							}
						});
					}
				}
				{
					JPanel pnloption = new JPanel(new GridLayout(2,1,5,5));
					pnlPreview.add(pnloption, BorderLayout.CENTER);
					{
						JPanel pnlrplf = new JPanel(new GridLayout(1, 4, 5, 5));
						pnloption.add(pnlrplf);
						{
							JLabel lblrplf = new JLabel("RPLF NO.", JLabel.CENTER);
							pnlrplf.add(lblrplf);
							lblrplf.setFont(FncGlobal.sizeLabel);
						}
						{
							
							lookuprplf = new _JLookup();
							pnlrplf.add(lookuprplf);
							lookuprplf.setEnabled(false);
							lookuprplf.setReturnColumn(0);
							//lookuprplf.setLookupSQL(getrplf(lookupCompany.getValue(), lookupProject.getValue(), type));
						}
						{
							JLabel lbl = new JLabel("");
							pnlrplf.add(lbl);
						}
						{
							JLabel lbl = new JLabel("");
							pnlrplf.add(lbl);
						}
					}
					{
						JPanel pnlDate = new JPanel(new GridLayout(1,4,5,5));
						pnloption.add(pnlDate);
						{
							JLabel lblDateFrom = new JLabel("Date From:", JLabel.CENTER);
							pnlDate.add(lblDateFrom);
							lblDateFrom.setFont(FncGlobal.sizeLabel);
						}
						{
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlDate.add(dteDateFrom);						
							dteDateFrom.setFont(FncGlobal.sizeTextValue);
							dteDateFrom.setEnabled(false);
							//dteDateFrom.setDate(FncGlobal.getDateToday());//commment by erick 2024-05-03
						}
						{
							JLabel lblDateTo = new JLabel("Date To:", JLabel.CENTER);
							pnlDate.add(lblDateTo);
							lblDateTo.setFont(FncGlobal.sizeLabel);
						}
						{
							dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlDate.add(dteDateTo);
							dteDateTo.setFont(FncGlobal.sizeTextValue);
							dteDateTo.setEnabled(false);
							//dteDateTo.setDate(FncGlobal.getDateToday()); //commment by erick 2024-05-03
						}
					}
					
				}
//				{
//					JPanel pnlDate = new JPanel(new GridLayout(2,4,5,5));
//					pnlPreview.add(pnlDate, BorderLayout.CENTER);
//					JPanel pnlgrid_1 = new JPanel();
//					JPanel pnlgrid_2 = new JPanel();
//					{
//						JLabel lblDateFrom = new JLabel("Date From:", JLabel.CENTER);
//						pnlDate.add(lblDateFrom);
//						lblDateFrom.setFont(FncGlobal.sizeLabel);
//					}
//					{
//						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//						pnlDate.add(dteDateFrom);						
//						dteDateFrom.setFont(FncGlobal.sizeTextValue);
//						dteDateFrom.setDate(FncGlobal.getDateToday());
//					}
//					{
//						JLabel lblDateTo = new JLabel("Date To:", JLabel.CENTER);
//						pnlDate.add(lblDateTo);
//						lblDateTo.setFont(FncGlobal.sizeLabel);
//					}
//					{
//						dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//						pnlDate.add(dteDateTo);
//						dteDateTo.setFont(FncGlobal.sizeTextValue);
//						dteDateTo.setDate(FncGlobal.getDateToday());
//					}
//				}
				{
					JPanel pnlButton = new JPanel(new GridLayout(1,6,5,5));
					pnlPreview.add(pnlButton, BorderLayout.SOUTH);
					pnlButton.setPreferredSize(new Dimension(0,30));
					{
						pnlButton.add(Box.createHorizontalBox());
						pnlButton.add(Box.createHorizontalBox());
					}
					{
						btnOK = new JButton("OK");
						pnlButton.add(btnOK);
						btnOK.setActionCommand("Preview");
						btnOK.addActionListener(this);
						btnOK.setFont(FncGlobal.sizeControls);
					}
					{
						btnClose = new JButton("Cancel");
						pnlButton.add(btnClose);
						btnClose.setActionCommand("Close");
						btnClose.addActionListener(this);
						btnClose.setFont(FncGlobal.sizeControls);
					}
					{
						pnlButton.add(Box.createHorizontalBox());
						pnlButton.add(Box.createHorizontalBox());
					}
				}
			}
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
						pnlCompany.setBorder(JTBorderFactory.createTitleBorder("Details"));
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.BOTH;
							cons_com.insets = new Insets(5, 5, 5, 5);
							//LINE1
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

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
											
											
											if(tabPane.getSelectedIndex() == 0) {
												lookupbatch.setLookupSQL(getbatch_pcost(lookupCompany.getValue(), lookupProject.getValue()));
											}else {
												lookupbatch.setLookupSQL(getbatch_tcost(lookupCompany.getValue(), lookupProject.getValue()));
											}
											
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

											//btnSave.setEnabled(true);
											btnPreview.setEnabled(true);

											//displayPCOST(modelPCOSTList, co_id);	
											//displayTCOST(modelTCOSTList, co_id);
											System.out.println("getSelectedIndex: "+tabPane.getSelectedIndex());
											if(tabPane.getSelectedIndex() == 0) {
												lookupbatch.setLookupSQL(getbatch_pcost(lookupCompany.getValue(), lookupProject.getValue()));
											}else {
												lookupbatch.setLookupSQL(getbatch_tcost(lookupCompany.getValue(), lookupProject.getValue()));
											}
											
											lookuprplf.setLookupSQL(getrplf(lookupCompany.getValue(), lookupProject.getValue()));
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

						JPanel pnlRemarks = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlRemarks, c);
						pnlRemarks.setBorder(JTBorderFactory.createTitleBorder("Remarks"));
						{
							GridBagConstraints cons_rem = new GridBagConstraints();
							cons_rem.fill = GridBagConstraints.BOTH;
							cons_rem.insets = new Insets(5, 5, 5, 5);
							{
								cons_rem.weightx = 0;
								cons_rem.weighty = 1;

								cons_rem.gridx = 0;
								cons_rem.gridy = 0;

								JLabel lblRemarks = new JLabel("Remarks");
								pnlRemarks.add(lblRemarks, cons_rem);
								lblRemarks.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_rem.weightx = 1;
								cons_rem.weighty = 1;

								cons_rem.gridx = 1;
								cons_rem.gridy = 0;

								txtRemarks = new JXTextField("Enter remarks...");
								pnlRemarks.add(txtRemarks, cons_rem);
								txtRemarks.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 2;

						c.gridx = 0;
						c.gridy = 2;

						JPanel pnlSearch = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlSearch, c);
						pnlSearch.setBorder(JTBorderFactory.createTitleBorder("PCOST/TCOST"));
						{
							GridBagConstraints cons_sch = new GridBagConstraints();
							cons_sch.fill = GridBagConstraints.BOTH;
							cons_sch.insets = new Insets(5, 5, 5, 5);

							{
								cons_sch.weightx = 0;
								cons_sch.weighty = 0.25;

								cons_sch.gridx = 0;
								cons_sch.gridy = 0;

								JLabel lblSearch = new JLabel("**Search:");
								pnlSearch.add(lblSearch, cons_sch);
								lblSearch.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_sch.weightx = 1;
								cons_sch.weighty = 0.25;

								cons_sch.gridx = 1;
								cons_sch.gridy = 0;

								lookupbatch = new _JLookup();
								pnlSearch.add(lookupbatch, cons_sch);
								lookupbatch.setFont(FncGlobal.sizeTextValue);
								
								lookupbatch.setReturnColumn(1);
								lookupbatch.addLookupListener(new LookupListener() {
									@Override
									public void lookupPerformed(LookupEvent event) {
										System.out.println("lookupbatch:"+lookupbatch.getLookupSQL());
										if(tabPane.getSelectedIndex() == 0) {
											displayPCOST(modelPCOSTList, co_id);
										}else {
											displayTCOST(modelTCOSTList, co_id);
										}
										btnSave.setEnabled(true);
										
									}
								});
								/*txtSearch = new _JXTextField("Search DRF number...");
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
								});*/
							}
							{
								cons_sch.weightx = 1;
								cons_sch.weighty = 2;

								cons_sch.gridx = 0;
								cons_sch.gridy = 1;

								cons_sch.gridwidth = 2;

								tabPane= new JTabbedPane(); 
								pnlSearch.add(tabPane, cons_sch);
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
										modelPCOSTList = new modelTransactionSummary2();
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
										modelTCOSTList = new modelTransactionSummary2();
										tblTCOST = new _JTableMain(modelTCOSTList);

										scrollTCOST.setViewportView(tblTCOST);
										tblTCOST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
										tblTCOST.setFont(FncGlobal.sizeTextValue);
									}
								}
								tabPane.addChangeListener(new ChangeListener() {
									public void stateChanged(ChangeEvent e) {
										int selectedTab = ((JTabbedPane) e.getSource()).getSelectedIndex();
										if(selectedTab == 0) {
											lookupbatch.setLookupSQL(getbatch_pcost(lookupCompany.getValue(), lookupProject.getValue()));
										}else {
											lookupbatch.setLookupSQL(getbatch_tcost(lookupCompany.getValue(), lookupProject.getValue()));
										}
									}
								});
							}						
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1,3,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setFont(FncGlobal.sizeControls);
					btnSave.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Show");
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
					btnPreview.setEnabled(false);
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
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Save")) {		save();}

		if(e.getActionCommand().equals("Cancel")) {		cancel();}

		if(e.getActionCommand().equals("Show"))	{		showPreview();}

		if(e.getActionCommand().equals("Preview"))	{	previewReport();}

	}

	private void previewReport() {
		// TODO Auto-generated method stub
		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlPreview);
		optionPaneWindow.dispose();

		String co_name = txtCompany.getText();
		String proj_name = txtProject.getText();
		String table = "";

		int index = tabPane.getSelectedIndex();
		if(index == 0) {
			table = "PCOST";
		}else {
			table = "TCOST";
		}
		
		
		

		java.util.Date date_from = dteDateFrom.getDate();
		java.util.Date date_to = dteDateTo.getDate();

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_co_id", co_id);
		mapParameters.put("p_proj_id", proj_id);
		mapParameters.put("p_co_name", co_name);
		mapParameters.put("p_table", table);
		mapParameters.put("p_date_from", date_from);
		mapParameters.put("p_date_to", date_to);
		mapParameters.put("p_user", UserInfo.EmployeeCode);
		mapParameters.put("p_proj_name", proj_name);
		mapParameters.put("p_requester", UserInfo.FullName2);
		mapParameters.put("p_rplf", lookuprplf.getValue());

		FncReport.generateReport("/Reports/rptTransactionRemarks_2.jasper", "Report", mapParameters);

	}

	private void showPreview() {
		JOptionPane.showOptionDialog(getContentPane(), pnlPreview, "Preview Report",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

	}

	private void cancel() {

		co_id = "";
		proj_id = "";
		lookupCompany.setValue(null);
		lookupProject.setValue(null);
		txtCompany.setText("");
		txtProject.setText("");
		txtRemarks.setText("");
		modelPCOSTList.clear();
		modelTCOSTList.clear();
		btnSave.setEnabled(false);
		btnPreview.setEnabled(false);
		lookupbatch.setValue(null);

	}

	private Boolean hasSelected(DefaultTableModel model) {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < model.getRowCount(); x++){
			listSelected.add((Boolean) model.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	private void save() {

		ArrayList<String> listBatch = new ArrayList<String>();

		if (tabPane.getSelectedIndex() == 0) {
			if (hasSelected(modelPCOSTList) == true ) {


				if (JOptionPane.showConfirmDialog(getContentPane(), "Do you want to save entries?", "Save", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {
					
					for (int x = 0; x < modelPCOSTList.getRowCount(); x++) {
						Boolean isSelected = (Boolean) modelPCOSTList.getValueAt(x, 0);
						if(isSelected) {
							String batch_no = (String) modelPCOSTList.getValueAt(x, 1);
							String or_no = (String) modelPCOSTList.getValueAt(x, 7);
							String retrn_check_no = (String) modelPCOSTList.getValueAt(x, 8);
							Integer rec_id =  (Integer) modelPCOSTList.getValueAt(x, 9);
							

							listBatch.add(String.format("%s", batch_no));

							//System.out.printf("The value of batch_no: (%s)\n", batch_no);

							String co_id = lookupCompany.getText();
							String remarks = txtRemarks.getText();
							String table = "PCOST";
							String user = UserInfo.EmployeeCode;

							//String sql = "select sp_save_transaction_remarks_v2('"+co_id+"', '"+table+"', '"+batch_no+"', '"+remarks+"', '"+user+"', '"+retrn_check_no+"')";
							String sql = "select sp_save_transaction_remarks_v2_debug('"+co_id+"', '"+table+"', '"+batch_no+"', '"+remarks+"', '"+user+"', '"+retrn_check_no+"', '"+or_no+"', "+rec_id+")";	
							
							FncSystem.out("SQL FOR SAVING REMARKS", sql);
							pgSelect db = new pgSelect();
							db.select(sql);


						}	
					}
					JOptionPane.showMessageDialog(getContentPane(), "Successfully saved!", "Save", JOptionPane.INFORMATION_MESSAGE);
					//displayPCOST(modelTCOSTList, co_id);
					btnSave.setEnabled(false);
					txtRemarks.setText("");
				}
			}else {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (tabPane.getSelectedIndex() == 1) {
			if (hasSelected(modelTCOSTList) == true ) {


				if (JOptionPane.showConfirmDialog(getContentPane(), "Do you want to save entries?", "Save", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {
					for (int x = 0; x < modelTCOSTList.getRowCount(); x++) {
						Boolean isSelected = (Boolean) modelTCOSTList.getValueAt(x, 0);
						if(isSelected) {
							String batch_no = (String) modelTCOSTList.getValueAt(x, 1);
							String or_no = (String) modelTCOSTList.getValueAt(x, 7);
							String retrn_check_no = (String) modelTCOSTList.getValueAt(x, 8);
							Integer rec_id = (Integer) modelTCOSTList.getValueAt(x, 9);

							listBatch.add(String.format("%s", batch_no));

							//System.out.printf("The value of batch_no: (%s)\n", batch_no);

							String co_id = lookupCompany.getText();
							String remarks = txtRemarks.getText();
							String table = "TCOST";
							String user = UserInfo.EmployeeCode;

							//String sql = "select sp_save_transaction_remarks_v2('"+co_id+"', '"+table+"', '"+batch_no+"', '"+remarks+"', '"+user+"', '"+retrn_check_no+"')";
							String sql = "select sp_save_transaction_remarks_v2_debug('"+co_id+"', '"+table+"', '"+batch_no+"', '"+remarks+"', '"+user+"', '"+retrn_check_no+"', '"+or_no+"', "+rec_id+")";

							FncSystem.out("SQL FOR SAVING REMARKS", sql);
							pgSelect db = new pgSelect();
							db.select(sql);

						

						}	
					}
					JOptionPane.showMessageDialog(getContentPane(), "Successfully saved!", "Save", JOptionPane.INFORMATION_MESSAGE);
					//displayTCOST(modelTCOSTList, co_id);
					btnSave.setEnabled(false);
					txtRemarks.setText("");
				}
			}else {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
			}

		
		}
	}

	private void displayPCOST(final DefaultTableModel modelMain, final String co_id) {

		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");

				modelMain.setRowCount(0);
				DefaultListModel listModel = new DefaultListModel();

				String sql = 
						"select \n" + 
								"false,\n" + 
								"a.batch_no,\n" + 
								"c.entity_name,\n" + 
								"a.rplf_no,\n" + 
								"a.status_id,\n" + 
								"a.pcostid_dl, \n"+
								"a.tran_date::date,\n" + 
								"'',\n"+
								"'',\n"+
								"a.rec_id \n" + 
								"from rf_processing_cost a\n" + 
								//"left join em_employee b on a.created_by = b.emp_code\n" + 
								"left join rf_entity c on a.entity_id = c.entity_id\n" + 
								"where a.co_id = '"+co_id+"'\n" + 
								"and a.status_id = 'A'\n" + 
								"and a.batch_no = '"+lookupbatch.getValue()+"'\n"+
								//"and not exists (select rplf_no from tmp_transaction_remarks where rplf_no = a.rplf_no and co_id = a.co_id and proj_id = a.projcode and status_id = 'A' and trans_type = 'P')\n" +
								"group by a.batch_no, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created, a.pcostid_dl, a.rec_id, or_no_reference\n" + 
								"order by a.batch_no ASC";


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

				FncGlobal.stopProgress();
				return null;
			}
		};
		sw.execute();

	}

	private void displayTCOST(final DefaultTableModel modelMain, final String co_id) {

		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");

				modelMain.setRowCount(0);
				DefaultListModel listModel = new DefaultListModel();

				String sql = 
						"select \n" + 
								"false,\n" + 
								"a.batch_no,\n" + 
								"c.entity_name,\n" + 
								"a.rplf_no,\n" + 
								"a.status_id,\n" + 
								"a.tcostid_dl,\n"+
								"a.tran_date::date,\n" + 
								"case when or_no_reference is not null then or_no_reference else '' end,\n"+
								"'',\n"+
								"a.rec_id\n" + 
								"from rf_transfer_cost a\n" + 
								//"left join em_employee b on a.created_by = b.emp_code\n" + 
								"left join rf_entity c on a.entity_id = c.entity_id\n" + 
								"where a.co_id = '"+co_id+"'\n" + 
								"and a.status_id = 'A'\n" + 
								"and a.batch_no = '"+lookupbatch.getValue()+"'\n"+
								//"and not exists (select rplf_no from tmp_transaction_remarks where rplf_no = a.rplf_no and co_id = a.co_id and proj_id = a.projcode and status_id = 'A' and trans_type = 'T')\n" +
								"group by a.batch_no, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created, a.tcostid_dl, a.rec_id, or_no_reference \n" + 
								"order by a.batch_no ASC";


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


				FncGlobal.stopProgress();
				return null;
			}
		};
		sw.execute();
	}
	
	public String getrplf (String co_id, String proj_id ) {
		String type;
		int index = tabPane.getSelectedIndex();
		if(index == 0) {
			type = "P";
		}else {
			type = "T";
		}
		return"select rplf_no, batch_no from tmp_transaction_remarks a\n"
				+ "where status_id = 'A' \n"
				+ "and co_id = '"+co_id+"'\n"
				+ "and proj_id = '"+proj_id+"'\n"
				+ "and trans_type = '"+type+"' ";
	}
	
	public String getbatch_pcost(String co_id,String proj_id) {
		
		return  "SELECT distinct on (a.batch_no)\n" + 
				"rplf_no as \"Rplf No\", \n" + 
				//"to_char(a.batch_no::int, 'FM0000000000') as \"Batch No\",\n" + 
				"a.batch_no as \"Batch No\",\n" + 
				"a.co_id as \"Company ID\", \n" + 
				"c.entity_name as \"Entity Name\", \n" + 
				"a.date_created as \"Date Created\" \n" + 
				"\n" + 
				"FROM rf_processing_cost a\n" + 
				"left join em_employee b on a.created_by = emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id \n" + 
				"WHERE a.co_id = '"+co_id+"'\n" + 
				"AND nullif(a.batch_no, '') is not null\n" + 
				"AND case when '"+proj_id+"' = 'null' then true else a.projcode = '"+proj_id+"' end\n" + 
				"AND a.status_id = 'A'\n" + 
				"order by a.batch_no desc";
	}
	
	public String getbatch_tcost(String co_id,String proj_id) {
		return "select distinct on (a.batch_no) \n" + 
				"a.rplf_no as \"RPLF No.\", \n" + 
				"a.batch_no  as \"Batch No\", \n" + 
				"a.co_id as \"Company ID\", \n" + 
				"c.entity_name as \"Entity Name\", \n" + 
				"a.date_created as \"Date Created\"\n" + 
				"\n" + 
				"from rf_transfer_cost a\n" + 
				"left join em_employee b on a.created_by = emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id \n" + 
				"where a.status_id = 'A'\n" + 
				"and a.co_id = '"+co_id+"'\n" + 
				"and case when '"+proj_id+"' = 'null' then true else a.projcode = '"+proj_id+"' end\n" + 
				"and a.batch_no is not null\n" + 
				"order by a.batch_no desc";
	}

	
}