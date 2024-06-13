package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.LookupOp;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelTransactionSummary;

public class pnlTransactionSummaryReport extends JDialog implements _GUI, ActionListener{
	/*
	 * CHANGES AS OF 2021-09-20
	 * 
	 * 1. ADDITIONAL FIELDS - PROJECT WAS ADDED IN THE GUI 2021-09-20
	 * 2. ADDITIONAL MENU IN JCOMBO BOX - DST STANDS FOR DST MORTGAGE TO PREVIEW TAG ACCOUNTS 2021-09-20
	 * 3. TRANSFER TAX AND DOA REPORT IS MODIFIED - ADDITIONAL COLUMNS IN THE REPORT
	 * 4. PANEL FOR GENERATING BATCH NO. FOR DST MORTGAGE 2021-09-20
	 * 
	 * */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXPanel pnlMain;
	private _JLookup lookupSCompany;
	private JTextField txtSCompany;
	private JComboBox cmbParticular;
	private _JXTextField txtSearch;
	private JScrollPane scrollSummary;
	private modelTransactionSummary modelSummary;
	private _JTableMain tblSummary;
	private JButton btnSPreview;
	private JButton btnSRefresh;
	private JDateChooser dteDate;
	private _JLookup lookupSProject;
	private JTextField txtSProject;
	private JButton btnCheck;

	public pnlTransactionSummaryReport(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(new Dimension(590,450));
		this.setModal(true);
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		{
			pnlMain = new JXPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				JPanel pnlSNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlSNorth, BorderLayout.NORTH);
				pnlSNorth.setBorder(JTBorderFactory.createTitleBorder("Details"));
				pnlSNorth.setPreferredSize(new Dimension(0,190));
				{
					JPanel pnlWestLabel = new JPanel(new GridLayout(5,1,5,5));
					pnlSNorth.add(pnlWestLabel, BorderLayout.WEST);
					{
						JLabel lblCompany = new JLabel("Company:");
						pnlWestLabel.add(lblCompany);
					}
					{/*ADDED BY JED 2021-09-14 DCRF NO. 1726 : FOR DST FORM PRINTING*/
						JLabel lblProject = new JLabel("Project:");
						pnlWestLabel.add(lblProject);
					}
					{
						JLabel lblParticular = new JLabel("Particular:");
						pnlWestLabel.add(lblParticular);
					}
					{
						JLabel lblDate = new JLabel("Date:");
						pnlWestLabel.add(lblDate);
					}
					{
						JLabel lblSearch = new JLabel("*Search:");
						pnlWestLabel.add(lblSearch);
					}
				}
				{
					JPanel pnlCenterComponents = new JPanel(new GridLayout(5,1,5,5));
					pnlSNorth.add(pnlCenterComponents, BorderLayout.CENTER);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(5,5));
						pnlCenterComponents.add(pnlCompany);
						{
							lookupSCompany = new _JLookup(null, "Company");
							pnlCompany.add(lookupSCompany, BorderLayout.WEST);
							lookupSCompany.setPreferredSize(new Dimension(110,0));
							lookupSCompany.setLookupSQL(SQL_COMPANY());
							lookupSCompany.setReturnColumn(0);
							lookupSCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										String co_id = (String) data[0];
										String co_name = (String) data[1];

										txtSCompany.setText(co_name);

										modelSummary.clear();
										cmbParticular.setSelectedIndex(0);
										txtSearch.setEditable(false);
										
										lookupSProject.setLookupSQL(SQL_PROJECT(co_id));

									}
								}
							});

						}
						{
							txtSCompany = new JTextField("");
							pnlCompany.add(txtSCompany, BorderLayout.CENTER);
							txtSCompany.setEditable(false);
						}
					}
					{/*ADDED BY JED 2021-09-14 DCRF NO. 1726 : FOR DST FORM PRINTING*/
						JPanel pnlProject = new JPanel(new BorderLayout(5,5));
						pnlCenterComponents.add(pnlProject);
						{
							lookupSProject = new _JLookup(null, "Project");
							pnlProject.add(lookupSProject, BorderLayout.WEST);
							lookupSProject.setPreferredSize(new Dimension(110,0));
							lookupSProject.setReturnColumn(0);
							lookupSProject.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										String proj_name = (String) data[1];
										
										txtSProject.setText(proj_name);

									}
								}
							});

						}
						{
							txtSProject = new JTextField("");
							pnlProject.add(txtSProject, BorderLayout.CENTER);
							txtSProject.setEditable(false);
						}
					}
					{
						JPanel pnlParticular = new JPanel(new BorderLayout(5,5));
						pnlCenterComponents.add(pnlParticular, BorderLayout.CENTER);
						{
							JPanel pnlDrop = new JPanel(new BorderLayout(5,5));
							pnlParticular.add(pnlDrop, BorderLayout.WEST);
							{
								/*EDITED BY JED DCRF NO. 1726 2021-09-20 : DST WAS ADDED TO PREVIEW TAG ACCOUNTS*/

								String[] type = { "", "DOA", "Sale", "Mortgage", "Transfer Tax", "DST" };

								cmbParticular = new JComboBox(type);
								pnlDrop.add(cmbParticular, BorderLayout.WEST);
								cmbParticular.setPreferredSize(new Dimension(110,0));
								cmbParticular.setSelectedIndex(0);
								cmbParticular.addItemListener(new ItemListener() {

									@Override
									public void itemStateChanged(ItemEvent e) {
										// TODO Auto-generated method stub
										int index = ((JComboBox) e.getSource()).getSelectedIndex();

										if(index == 0) {}
										if(index == 1) {
											txtSearch.setEditable(true);
											String co_id = lookupSCompany.getValue();
											java.util.Date tran_date = dteDate.getDate();
											displayPCOSTSummary(modelSummary, co_id, tran_date);
											btnCheck.setVisible(false);
										}
										if(index == 2) {
											txtSearch.setEditable(true);
											String co_id = lookupSCompany.getValue();
											java.util.Date tran_date = dteDate.getDate();
											displayTCOSTSummary_Sale(modelSummary, co_id, tran_date);
											btnCheck.setVisible(false);
										}
										if(index == 3) {
											txtSearch.setEditable(true);
											String co_id = lookupSCompany.getValue();
											java.util.Date tran_date = dteDate.getDate();
											displayTCOSTSummary_Mort(modelSummary, co_id, tran_date);
											btnCheck.setVisible(false);
										}
										if(index == 4) {
											txtSearch.setEditable(true);
											String co_id = lookupSCompany.getValue();
											java.util.Date tran_date = dteDate.getDate();
											displayTCOSTSummary_TransferTax(modelSummary, co_id, tran_date);
											btnCheck.setVisible(false);
										}
										if(index == 5) {
											txtSearch.setEditable(true);
											String co_id = lookupSCompany.getValue();
											java.util.Date tran_date = dteDate.getDate();
											displayTCOSTSummary_DocStampMort(modelSummary, co_id, tran_date);
											btnCheck.setVisible(true);
										}
									}
								});
							}
						}
						{
							JPanel pnlDropButton = new JPanel(new BorderLayout(5,5));
							pnlParticular.add(pnlDropButton, BorderLayout.CENTER);
							{
								btnCheck = new JButton("Check");
								pnlDropButton.add(btnCheck);
								btnCheck.setVisible(false);
								btnCheck.setActionCommand("Check");
								btnCheck.addActionListener(this);
							}
						}
						{
							JPanel pnlSpace = new JPanel(new BorderLayout(5,5));
							pnlParticular.add(pnlSpace, BorderLayout.EAST);
							pnlSpace.setPreferredSize(new Dimension(250,0));
						}
					}
					{
						JPanel pnlDate = new JPanel(new BorderLayout(5,5));
						pnlCenterComponents.add(pnlDate);
						{
							dteDate = new JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							JTextFieldDateEditor dateEditor = (JTextFieldDateEditor)dteDate.getComponent(1);
							pnlDate.add(dteDate, BorderLayout.WEST);
							dteDate.setPreferredSize(new Dimension(110,0));
							dateEditor.setHorizontalAlignment(JTextField.CENTER);
							dteDate.setDate(FncGlobal.getDateToday());
							dteDate.setEnabled(true);
							dteDate.getDateEditor().addPropertyChangeListener(
									new PropertyChangeListener() {
										@Override
										public void propertyChange(PropertyChangeEvent e) {
											if ("date".equals(e.getPropertyName())) {
												//System.out.println(e.getPropertyName() + ": " + (Date) e.getNewValue());
												int index = cmbParticular.getSelectedIndex();
												
												if(index == 0) {}
												if(index == 1) {
													txtSearch.setEditable(true);
													String co_id = lookupSCompany.getValue();
													java.util.Date tran_date = dteDate.getDate();
													displayPCOSTSummary(modelSummary, co_id, tran_date);
												}
												if(index == 2) {
													txtSearch.setEditable(true);
													String co_id = lookupSCompany.getValue();
													java.util.Date tran_date = dteDate.getDate();
													displayTCOSTSummary_Sale(modelSummary, co_id, tran_date);
												}
												if(index == 3) {
													txtSearch.setEditable(true);
													String co_id = lookupSCompany.getValue();
													java.util.Date tran_date = dteDate.getDate();
													displayTCOSTSummary_Mort(modelSummary, co_id, tran_date);
												}
												if(index == 4) {
													txtSearch.setEditable(true);
													String co_id = lookupSCompany.getValue();
													java.util.Date tran_date = dteDate.getDate();
													displayTCOSTSummary_TransferTax(modelSummary, co_id, tran_date);
												}
												if(index == 5) {
													txtSearch.setEditable(true);
													String co_id = lookupSCompany.getValue();
													java.util.Date tran_date = dteDate.getDate();
													displayTCOSTSummary_DocStampMort(modelSummary, co_id, tran_date);
												}
											}
										}
									});
						}
					}
					{
						JPanel pnlSearch = new JPanel(new BorderLayout(5,5));
						pnlCenterComponents.add(pnlSearch);
						{
							txtSearch = new _JXTextField("Search Batch No.");
							pnlSearch.add(txtSearch);
							txtSearch.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {
									searchBatchNo();
								}
							});
						}
					}
				}
			}
			{
				JPanel pnlSCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlSCenter, BorderLayout.CENTER);
				{
					scrollSummary = new JScrollPane();
					pnlSCenter.add(scrollSummary);
					scrollSummary.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				}
				{
					modelSummary = new modelTransactionSummary();
					tblSummary = new _JTableMain(modelSummary);

					scrollSummary.setViewportView(tblSummary);
					tblSummary.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				}
//					{
//						rowHeaderSummary = tblSummary.getRowHeader();
//						rowHeaderSummary.setModel(new DefaultListModel());
//						scrollSummary.setRowHeaderView(rowHeaderSummary);
//						scrollSummary.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
//					}
			}
			{
				JPanel pnlSSouth = new JPanel(new GridLayout(1,4,5,5));
				pnlMain.add(pnlSSouth, BorderLayout.SOUTH);
				pnlSSouth.setPreferredSize(new Dimension(0,30));
				{
					pnlSSouth.add(Box.createHorizontalBox());
					pnlSSouth.add(Box.createHorizontalBox());
					pnlSSouth.add(Box.createHorizontalBox());
				}
				{
					btnSPreview = new JButton("Preview");
					pnlSSouth.add(btnSPreview);
					btnSPreview.addActionListener(this);
					btnSPreview.setActionCommand("Preview Summary");
				}
				{
					btnSRefresh = new JButton("Refresh");
					pnlSSouth.add(btnSRefresh);
					btnSRefresh.addActionListener(this);
					btnSRefresh.setActionCommand("Refresh");
				}
			}
		}
	}

	private static String SQL_COMPANY() {
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		return SQL;
	}
	
	private static String SQL_PROJECT(String co_id) {
		String SQL = "select proj_id as \"ID\", trim(proj_name) as \"Project Name\", trim(proj_alias) as \"Alias\"\n"
				+ "from mf_project where trim(co_id) = '" + co_id + "' and status_id = 'A' order by proj_id\n";
		return SQL;
	}

	private void displayPCOSTSummary(DefaultTableModel modelMain, String co_id, java.util.Date tran_date) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		/*String sql = 
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
				"and a.status_id = 'A'\n" + 
				"group by a.batch_no, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created\n" + 
				"order by a.batch_no DESC";*/

		String sql = 
				"select distinct on (a.batch_no)\n" + 
				"true,\n" + 
				"a.batch_no,\n" + 
				"c.entity_name,\n" + 
				"a.rplf_no,\n" + 
				"a.status_id,\n" + 
				"a.tran_date::date\n" + 
				"from rf_processing_cost a\n" + 
				"left join em_employee b on a.created_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where a.co_id = '"+co_id+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"and a.pcostid_dl in ('109', '210', '105', '108', '211')\n" + 
				"and tran_date = '"+tran_date+"'::date\n" + 
				"group by a.batch_no, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created, a.pcostid_dl\n" + 
				"order by a.batch_no DESC";

		FncSystem.out("SQL for pcost summary", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}	
		}		
		tblSummary.packAll();
	}
	
	private void displayTCOSTSummary_Sale(DefaultTableModel modelMain, String co_id, java.util.Date tran_date) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 
				"select distinct on (a.batch_no)\n" + 
				"true,\n" + 
				"a.batch_no,\n" + 
				"c.entity_name,\n" + 
				"a.rplf_no,\n" + 
				"a.status_id,\n" + 
				"a.tran_date::date,\n" + 
				"a.tcostid_dl\n" + 
				"from rf_transfer_cost a\n" + 
				"left join em_employee b on a.created_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where a.co_id = '"+co_id+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"--and a.tcostid_dl in ('008', '011', '169', '021', '196')\n" + 
				"and a.tcostid_dl in ('169', '196','212')\n" + 
				"and a.tran_date = '"+tran_date+"'\n" + 
				"group by a.batch_no, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created, a.tcostid_dl\n" + 
				"order by a.batch_no DESC, a.tcostid_dl DESC";

		FncSystem.out("SQL for tcost summary", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}	
		}		
		tblSummary.packAll();
	}

	private void displayTCOSTSummary_Mort(DefaultTableModel modelMain, String co_id, java.util.Date tran_date) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 
				"select distinct on (a.batch_no)\n" + 
				"true,\n" + 
				"a.batch_no,\n" + 
				"c.entity_name,\n" + 
				"a.rplf_no,\n" + 
				"a.status_id,\n" + 
				"a.tran_date::date,\n" + 
				"a.tcostid_dl\n" + 
				"from rf_transfer_cost a\n" + 
				"left join em_employee b on a.created_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where a.co_id = '"+co_id+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"--and a.tcostid_dl in ('009', '012', '013', '193', '194')\n" + 
				"and a.tcostid_dl in ('193', '194')\n" + 
				"and tran_date = '"+tran_date+"'::date\n" + 
				"group by a.batch_no, a.tcostid_dl, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created\n" + 
				"order by a.batch_no DESC, a.tcostid_dl DESC";

		FncSystem.out("SQL for tcost summary", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}	
		}		
		tblSummary.packAll();
	}
	
	private void displayTCOSTSummary_TransferTax(DefaultTableModel modelMain, String co_id, java.util.Date tran_date) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 
				"select distinct on (a.batch_no)\n" + 
				"true,\n" + 
				"a.batch_no,\n" + 
				"c.entity_name,\n" + 
				"a.rplf_no,\n" + 
				"a.status_id,\n" + 
				"a.tran_date::date,\n" + 
				"a.tcostid_dl\n" + 
				"from rf_transfer_cost a\n" + 
				"left join em_employee b on a.created_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where a.co_id = '"+co_id+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"and a.tcostid_dl = '166'\n" + 
				"and tran_date = '"+tran_date+"'::date\n" + 
				"group by a.batch_no, a.tcostid_dl, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created\n" + 
				"order by a.batch_no DESC, a.tcostid_dl DESC";

		FncSystem.out("SQL for tcost summary", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}	
		}		
		tblSummary.packAll();
	}
	
	private void displayTCOSTSummary_DocStampMort(DefaultTableModel modelMain, String co_id, java.util.Date tran_date) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 
				"select distinct on (a.batch_no)\n" + 
				"true,\n" + 
				"a.batch_no,\n" + 
				"c.entity_name,\n" + 
				"a.rplf_no,\n" + 
				"a.status_id,\n" + 
				"a.tran_date::date,\n" + 
				"a.tcostid_dl\n" + 
				"from rf_transfer_cost a\n" + 
				"left join em_employee b on a.created_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where a.co_id = '"+co_id+"'\n" + 
				"and a.status_id = 'A'\n" + 
				"and a.tcostid_dl in ('034','230')\n" + 
				"and tran_date = '"+tran_date+"'::date\n" + 
				"group by a.batch_no, a.tcostid_dl, c.entity_name, a.rplf_no, a.tran_date, a.status_id, a.date_created\n" + 
				"order by a.batch_no DESC, a.tcostid_dl DESC";

		FncSystem.out("SQL for tcost summary", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}	
		}		
		tblSummary.packAll();
	}

	private void searchBatchNo() {

		int rw = tblSummary.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String batch = "";

			try {
				batch = tblSummary.getValueAt(x, 1).toString().toUpperCase();
			} catch (NullPointerException e) {
				batch = "";
			}

			String batch_no = txtSearch.getText().trim().toUpperCase();
			// Boolean match = name.indexOf(acct_name)>0;
			Boolean start = batch.contains(batch_no);
			// Boolean end = name.endsWith(module_name);

			if (start == true) {
				tblSummary.setRowSelectionInterval(x, x);
				tblSummary.changeSelection(x, 1, false, false);
				break;
			} else {
				tblSummary.setRowSelectionInterval(0, 0);
				tblSummary.changeSelection(0, 1, false, false);
			}

			x++;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*EDITED BY JED DCRF NO. 1726 2021-09-20 : REPORT OF TRANSFER TAX IS MODIFIED(TD LOT AND TD HOUSE IS INCLUDED IN REPORT),
		 * DOA REPORT WAS MODIFIED(TCT NO IS INCLUDED IN REPORT), PANEL FOR DST IS ADDED TO GENERATED BATCH NO. IN THE TEMP TABLE*/
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Refresh")) {
			int index = cmbParticular.getSelectedIndex();
			String co_id = lookupSCompany.getValue();
			java.util.Date tran_date = dteDate.getDate();

			if(index == 1) {
				JOptionPane.showMessageDialog(getContentPane(), "The fields have been refreshed!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
				displayPCOSTSummary(modelSummary, co_id, tran_date);
			}

			if(index == 2) {
				JOptionPane.showMessageDialog(getContentPane(), "The fields have been refreshed!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
				displayTCOSTSummary_Sale(modelSummary, co_id, tran_date);
			}
			
			if(index == 3) {
				JOptionPane.showMessageDialog(getContentPane(), "The fields have been refreshed!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
				displayTCOSTSummary_Mort(modelSummary, co_id, tran_date);
			}
			
			if(index == 4) {
				JOptionPane.showMessageDialog(getContentPane(), "The fields have been refreshed!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
				displayTCOSTSummary_TransferTax(modelSummary, co_id, tran_date);
			}
			if(index == 5) {
				JOptionPane.showMessageDialog(getContentPane(), "The fields have been refreshed!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
				displayTCOSTSummary_DocStampMort(modelSummary, co_id, tran_date);
			}
		}

		if(actionCommand.equals("Preview Summary")) {/*EDITED BY JED 2021-09-14 DCRF NO. 1726 : FOR DST FORM PRINTING*/
			java.util.Date dateFrom = dteDate.getDate();
			java.util.Date dateTo = dteDate.getDate();
			String co_name = txtSCompany.getText();
			String proj_id = lookupSProject.getValue();
			String proj_name = "";
			String emp_alias = UserInfo.Alias;
			String emp_name = UserInfo.FullName2;
			
			ArrayList<String> listBatch = new ArrayList<String>();

			for(int a = 0; a < modelSummary.getRowCount(); a++) {
				Boolean isTrue = (Boolean) modelSummary.getValueAt(a, 0);
				if(isTrue) {
					String batch = (String) modelSummary.getValueAt(a, 1);
					listBatch.add(String.format("%s", batch));
				}
			}
			if(listBatch.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a row!", "Error", JOptionPane.ERROR_MESSAGE);
			}else {
				int index = cmbParticular.getSelectedIndex();
				String emp_code = UserInfo.EmployeeCode;
				String co_id = lookupSCompany.getValue();
				deleteTempTable(emp_code);

				for(int x=0 ; x < modelSummary.getRowCount() ; x++) {
					Boolean ifSelected = (Boolean) modelSummary.getValueAt(x,0);
					if(ifSelected){
						String batch_no = (String) modelSummary.getValueAt(x,1);

						String sql = "SELECT sp_save_summary_to_tmp('"+co_id+"', '"+batch_no+"', '"+emp_code+"', "+index+")";

						System.out.println(sql);
						pgSelect db = new pgSelect();
						db.select(sql);
					}
				}
				
				String batch_no = listBatch.toString().replaceAll("\\[", "").replaceAll("\\]","");
				
				System.out.printf("The value of batch_no: (%s)\n", batch_no);
				
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("p_co_id", lookupSCompany.getValue());
				mapParameters.put("p_proj_id", lookupSProject.getValue());
				mapParameters.put("p_batch_no", batch_no);
				mapParameters.put("p_date_from", dateFrom);
				mapParameters.put("p_date_to", dateTo);
				mapParameters.put("p_proj_name", proj_name);
				mapParameters.put("p_co_name", co_name);
				mapParameters.put("p_emp_alias", emp_alias);
				mapParameters.put("p_emp_name", emp_name);
				mapParameters.put("p_emp_code", UserInfo.EmployeeCode);
				
				if(cmbParticular.getSelectedIndex() == 1) {
					String report_name = "PROCESSING COST REPORT";
					
					mapParameters.put("p_report_name", report_name);
					
					FncReport.generateReport("/Reports/rptTransactionSummaryReport_DOA.jasper", "Transaction Summary Report", mapParameters);
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Processing Cost Report", mapParameters);
					FncReport.generateReport("/Reports/rptPCOSTDisbursement_Req_BP.jasper", "PCOST Disbursement Request", mapParameters);
				}
				
				if(cmbParticular.getSelectedIndex() == 2) {
					String report_name = "TRANSFER COST REPORT";
					
					mapParameters.put("p_report_name", report_name);
					
					FncReport.generateReport("/Reports/rptTransactionSummaryReport.jasper", "Transaction Summary Report", mapParameters);
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Transfer Cost Report", mapParameters);
					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "TCOST Disbursement Request", mapParameters);
				}
				
				if(cmbParticular.getSelectedIndex() == 3) {
					String report_name = "TRANSFER COST REPORT";
					
					mapParameters.put("p_report_name", report_name);
					
					FncReport.generateReport("/Reports/rptTransactionSummaryReport.jasper", "Transaction Summary Report", mapParameters);
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Transfer Cost Report", mapParameters);
					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "TCOST Disbursement Request", mapParameters);
				}
				
				if(cmbParticular.getSelectedIndex() == 4) {
					String report_name = "TRANSFER COST REPORT";
					
					mapParameters.put("p_report_name", report_name);
					
					FncReport.generateReport("/Reports/rptTransactionSummaryReport_TTax.jasper", "Transaction Summary Report", mapParameters);
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Transaction Cost Report", mapParameters);
					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "TCOST Disbursement Request", mapParameters);
				}
				
				if(cmbParticular.getSelectedIndex() == 5) {
					String report_name = "TRANSFER COST REPORT";
					
					mapParameters.put("p_report_name", report_name);
					
					FncReport.generateReport("/Reports/rptTransactionSummaryReport.jasper", "Transaction Summary Report", mapParameters);
					FncReport.generateReport("/Reports/rptPCOSTCOSTBatchPrinting_v2.jasper", "Transfer Cost Report", mapParameters);
					FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_BP.jasper", "TCOST Disbursement Request", mapParameters);
				}
			}
		}
		
		if(actionCommand.equals("Check")) {
			java.util.Date dteTag = dteDate.getDate();
			String co_id = lookupSCompany.getValue();
			String proj_id = lookupSProject.getValue();
			
			pnlDocStampMortgage pd = new pnlDocStampMortgage(FncGlobal.homeMDI, "Documentay Stamp Mortgage", dteTag, co_id, proj_id);
			pd.setLocationRelativeTo(this);
			pd.setVisible(true);
			
		}
	}

	private void deleteTempTable(String emp_code) {

		String sql = "DELETE from tmp_summary_report where emp_code = '"+emp_code+"'";

		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();

	}
}