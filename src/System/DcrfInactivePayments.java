package System;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Buyers.ClientServicing._CARD;
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
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelCARD_Ledger;
import tablemodel.modelCARD_Payments;
import tablemodel.modelCARD_Payments_v2;
import tablemodel.modelCARD_Schedule;
import tablemodel.model_RevolvingFunds;
import tablemodel.model_TcostRevolvingFunds;

public class DcrfInactivePayments extends _JInternalFrame implements _GUI,ActionListener  {


	static String title = "Dcrf Inactive Payments";
	Dimension frameSize = new Dimension(750, 500);

	private JPanel pnlMain; 
	private _JLookup lookupClient,lookupDcrf;
	private JButton btnCancel,btnSave;
	static _JTableMain tblPayment,tblLedger,tblSchedule; 
	private JLabel lblClient,lblProj,lblUnit,lblDcrf,lblClient2,lblProj2,lblUnit2,lblDcrf2;
	private JList rowPayment,rowLedger,rowSched;
	private static  JTabbedPane tabDesc;
	private modelCARD_Schedule modelSchedule;
	private modelCARD_Ledger modelLedger;
	private modelCARD_Payments_v2 modelPayments;
	private JScrollPane scrollPayments,scrollLedger,scrollSchedule;
	private JTextField txtProj,txtUnit;
	private String pbl_id;
	private Integer seq_no;



	public DcrfInactivePayments(){
		super(title, true, true, true,true);
		initGUI();
	}
	public DcrfInactivePayments(String title){
		super(title);
		initGUI();

	}
	public DcrfInactivePayments(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconiflable){
		super(title,resizable,closable,maximizable,iconiflable);
		initGUI();

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));		
		this.setPreferredSize(frameSize);
		this.setSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5,5,5,5));
			{
				JPanel pnlMainNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainNorth,BorderLayout.NORTH);
				pnlMainNorth.setBorder(new EmptyBorder(5,5,5,5));
				{
					JPanel pnlNorth = new JPanel(new BorderLayout(5,5));
					pnlMainNorth.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(0,100));
					{
						JPanel pnlNorthWest = new JPanel(new GridLayout(4,1,3,3));
						pnlNorth.add(pnlNorthWest,BorderLayout.WEST);
						{
							lblClient = new JLabel("Client:");
							pnlNorthWest.add(lblClient);
							lblProj = new JLabel("Project:");
							pnlNorthWest.add(lblProj);
							lblUnit = new JLabel("Unit/Seq:");
							pnlNorthWest.add(lblUnit);
							lblDcrf = new JLabel("Dcrf no:");
							pnlNorthWest.add(lblDcrf);

						}
					}
					{
						JPanel pnlNorthCenter = new JPanel(new GridLayout(4,1,3,3));
						pnlNorth.add(pnlNorthCenter,BorderLayout.CENTER);
						{
							JPanel pnlCleint = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlCleint);
							{
								JPanel pnlClientWest = new JPanel(new BorderLayout(5,5));
								pnlCleint.add(pnlClientWest,BorderLayout.WEST);
								pnlClientWest.setPreferredSize(new Dimension(100,0));
								{
									lookupClient = new _JLookup(null,"Client");
									pnlClientWest.add(lookupClient);
									lookupClient.setEnabled(true);
									lookupClient.setLookupSQL(_CARD.sqlClients());
									lookupClient.setReturnColumn(0);
									lookupClient.setFilterIndex(2);
									lookupClient.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();


											if(data != null){
												lookupClient.setValue((String) data[1]);
												lblClient2.setText(String.format("[%s]", (String) data[2]));
												txtProj.setText((String) data[7]);
												lblProj2.setText(String.format("[%s]", (String) data[8]));
												txtUnit.setText((String) data[4]);
												lblUnit2.setText(String.format("[%s] [%s]", (String) data[3], Integer.toString((Integer) data[5])));
												//												lblSeq.setText(String.format("[%s]", (String) data[5]));
												pbl_id = (String) data[4];
												seq_no = (Integer) data[5];
												btnCancel.setEnabled(true);
												displayValueSchedule(modelSchedule, lookupClient.getValue(), txtProj.getText(), pbl_id, seq_no);
												displayValueLedger(modelLedger, lookupClient.getValue(), txtProj.getText(), pbl_id, seq_no);
												displayValuePayments(modelPayments, lookupClient.getValue(), txtProj.getText(), pbl_id, seq_no);
												if(lookupDcrf.getValue() != null) {
													btnSave.setEnabled(true);
												}

											}
										}

									});

								}
							}
							{
								JPanel pnlClientCenter = new JPanel(new BorderLayout(5,5));
								pnlCleint.add(pnlClientCenter,BorderLayout.CENTER);
								{
									lblClient2 = new JLabel("[]");
									pnlClientCenter.add(lblClient2);
								}
							}
						}
						{
							JPanel pnlProj = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlProj);
							{
								JPanel pnlProjWest = new JPanel(new BorderLayout(5,5));
								pnlProj.add(pnlProjWest,BorderLayout.WEST);
								pnlProjWest.setPreferredSize(new Dimension(50,0));
								{
									txtProj = new JTextField();
									pnlProjWest.add(txtProj);
									txtProj.setHorizontalAlignment(JTextField.CENTER);
									txtProj.setEnabled(false);
								}
							}
							{
								JPanel pnlProjCenter = new JPanel(new BorderLayout(5,5));
								pnlProj.add(pnlProjCenter,BorderLayout.CENTER);
								{
									lblProj2 = new JLabel("[]");
									pnlProjCenter.add(lblProj2);
								}

							}
						}
						{

							JPanel pnlUnit = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlUnit);
							{
								JPanel pnlUnitWest = new JPanel(new BorderLayout(5,5));
								pnlUnit.add(pnlUnitWest,BorderLayout.WEST);
								pnlUnitWest.setPreferredSize(new Dimension(50,0));
								{
									txtUnit = new JTextField();
									pnlUnitWest.add(txtUnit);
									txtUnit.setHorizontalAlignment(JTextField.CENTER);
									txtUnit.setEnabled(false);
								}
							}
							{
								JPanel pnlUnitCenter = new JPanel(new BorderLayout(5,5));
								pnlUnit.add(pnlUnitCenter,BorderLayout.CENTER);
								{
									lblUnit2 = new JLabel("[]");
									pnlUnitCenter.add(lblUnit2);
								}

							}

						}

						JPanel pnlDcrf = new JPanel(new BorderLayout(5,5));
						pnlNorthCenter.add(pnlDcrf);
						{
							JPanel pnlDcrfWest = new JPanel(new BorderLayout(5,5));
							pnlDcrf.add(pnlDcrfWest,BorderLayout.WEST);
							pnlDcrfWest.setPreferredSize(new Dimension(100,0));
							{
								lookupDcrf = new _JLookup(null,"Dcrf");
								pnlDcrfWest.add(lookupDcrf);
								lookupDcrf.setEnabled(true);
								lookupDcrf.setLookupSQL(getDCRF_no());
								lookupDcrf.setReturnColumn(0);
								lookupDcrf.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();


										if(data != null){
											lookupDcrf.setValue(data[0].toString());
											lblDcrf2.setText(String.format("[%s]", (String) data[4]));
											if(lookupClient.getValue() !=null ) {
												btnSave.setEnabled(true);
											}

										}
									}
								});
							}
						}
						{
							JPanel pnlDcrfCenter = new JPanel(new BorderLayout(5,5));
							pnlDcrf.add(pnlDcrfCenter,BorderLayout.CENTER);
							{
								lblDcrf2 = new JLabel("[]");
								pnlDcrfCenter.add(lblDcrf2);
							}
						}


					}
				}

			}
			{
				JPanel pnlMainCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
				{
					tabDesc = new JTabbedPane();
					pnlMainCenter.add(tabDesc);


					{
						scrollSchedule = new JScrollPane();
						tabDesc.addTab("Schedule", scrollSchedule);
						scrollSchedule.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollSchedule.setBorder(new EmptyBorder(5,5,5,5));

						{
							modelSchedule = new modelCARD_Schedule();
							tblSchedule = new _JTableMain(modelSchedule);
							scrollSchedule.setViewportView(tblSchedule);
							tblSchedule.setSortable(false);
							tblSchedule.setEnabled(false);


						}
					}
					{
						rowSched = tblSchedule.getRowHeader();
						rowSched.setModel(new DefaultListModel());
						scrollSchedule.setRowHeaderView(rowSched);
						scrollSchedule.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollLedger = new JScrollPane();
						tabDesc.addTab("Ledger", scrollLedger);
						scrollLedger.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
						scrollLedger.setBorder(new EmptyBorder(5,5,5,5));

					}
					{
						modelLedger = new modelCARD_Ledger();
						tblLedger = new _JTableMain(modelLedger);
						scrollLedger.setViewportView(tblLedger);
						tblLedger.setSortable(false);
						tblLedger.setEnabled(false);

					}
					{
						rowLedger = tblLedger.getRowHeader();
						rowLedger.setModel(new DefaultListModel());
						scrollLedger.setRowHeaderView(rowLedger );
						scrollLedger.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollPayments = new JScrollPane();
						tabDesc.addTab("Payments", scrollPayments);
						scrollPayments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
						scrollPayments.setBorder(new EmptyBorder(5,5,5,5));

					}
					{
						modelPayments = new modelCARD_Payments_v2();
						tblPayment = new _JTableMain(modelPayments);
						scrollPayments.setViewportView(tblPayment);
						tblPayment.setSortable(false);
						tblPayment.setEnabled(false);

					}
					{
						rowPayment = tblPayment.getRowHeader();
						rowPayment.setModel(new DefaultListModel());
						scrollPayments.setRowHeaderView(rowPayment );
						scrollPayments.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					tabDesc.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent arg0) {
							if(tabDesc.getSelectedIndex()==2) {
								if(lookupDcrf.getValue() != null && lookupClient.getValue() != null )
									btnSave.setEnabled(true);
								tblPayment.setEnabled(true);
							}
							else {
								btnSave.setEnabled(false);
							}
						}
					});


				}



			}
			{
				JPanel pnlMainSouth = new JPanel(new GridLayout(1,8,3,3));
				pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
				pnlMainSouth.setPreferredSize(new Dimension(0,30));

				{
					btnSave = new JButton("Save");
					pnlMainSouth.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save it?", "Save",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
								for(int x =0; x < modelPayments.getRowCount(); x++){
									Boolean isSelected = (Boolean) modelPayments.getValueAt(x, 0);
									Integer rec_id = (Integer) modelPayments.getValueAt(x, 23);

									if(isSelected){

										String strSQL = "SELECT sp_create_dcrf_inactive_ledger('"+lookupClient.getValue()+"','"+txtProj.getText()+"','"+txtUnit.getText()+"',"+seq_no+","+rec_id+",'"+lookupDcrf.getValue()+"','"+UserInfo.EmployeeCode+"');;" ; 

										pgSelect db = new pgSelect();
										db.select(strSQL);
										FncSystem.out("SQL for saving", strSQL);
									}
								}
								//reapplyAllPayments();
								JOptionPane.showMessageDialog(getContentPane(),"Succesfully Save","Save",JOptionPane.INFORMATION_MESSAGE);
							}
							else {

							}

						}

					});
				} 

				{
					btnCancel = new JButton("Cancel");
					pnlMainSouth.add(btnCancel);
					btnCancel.setEnabled(false);

					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							lookupClient.setValue(null);
							lblClient2.setText("[]");
							txtProj.setText("");
							lblProj2.setText("[]");
							txtUnit.setText("");
							lblUnit2.setText("[]");
							lookupDcrf.setValue(null);
							lblDcrf2.setText("[]");
							rowSched.setModel(new DefaultListModel());
							rowLedger.setModel(new DefaultListModel());
							rowPayment.setModel(new DefaultListModel());
							modelSchedule.clear();
							modelLedger.clear();
							modelPayments.clear();
							tblPayment.setEnabled(false);
							btnCancel.setEnabled(false);
							btnSave.setEnabled(false);
						}

					});
				}
			}
		}


	}
	private void displayValueSchedule(DefaultTableModel model,String entity_id,String proj_id,String pbl_id,Integer seq_no) {


		FncTables.clearTable(model);	
		DefaultListModel listModel = new DefaultListModel();
		rowSched.setModel(listModel);

		String sql = "SELECT * FROM view_card_schedule_v4('"+ entity_id+"', '"+ proj_id+"', '"+ pbl_id +"', "+ seq_no +");";

		FncSystem.out("Display description", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());

			}	
		}
		tblSchedule.packAll();
	}
	private void displayValueLedger(DefaultTableModel model,String entity_id,String proj_id,String pbl_id,Integer seq_no) {

		FncTables.clearTable(model);	
		DefaultListModel listModel = new DefaultListModel();
		rowLedger.setModel(listModel);

		if(lookupClient.getValue().equals("5810522222")) {
			String sql = "SELECT * \n" +
					"FROM view_card_ledger_v2('"+ entity_id+"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", false) \n" +
					"ORDER BY c_trans_date, c_sched_date,  (case when c_penalty is not null then 1 else 2 end);";

			FncSystem.out("Display description", sql);
			pgSelect db = new pgSelect();
			db.select(sql);

			if(db.isNotNull()){ 
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());

				}
			}
			tblLedger.packAll();
		}else {

			String sql = "SELECT * \n" + 
					"FROM view_card_ledger_with_moratorium('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", false) \n" + 
					"ORDER BY COALESCE(c_percent_paid, 0), c_pay_rec_id, c_trans_date, \n" + 
					"(case when c_due_type = 'M' then 0 when c_due_type = 'W' then 1 else 2 end), \n" + 
					"c_sched_date, (case when c_penalty is not null then 1 else 2 end); ";

			FncSystem.out("Display description", sql);
			pgSelect db = new pgSelect();
			db.select(sql);

			if(db.isNotNull()){ 
				for(int x=0; x < db.getRowCount(); x++){
					modelLedger.addRow(db.getResult()[x]);
					listModel.addElement(modelLedger.getRowCount());

				}
			}
			tblLedger.packAll();
		}
	}
	private void displayValuePayments(DefaultTableModel model,String entity_id,String proj_id,String pbl_id,Integer seq_no) {
		FncTables.clearTable(model);	
		DefaultListModel listModel = new DefaultListModel();
		rowPayment.setModel(listModel);

		String sql = "SELECT * FROM view_card_payments_v3('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";

		FncSystem.out("Display description", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());

			}	
		}
		tblPayment.packAll();



	}
	private String getDCRF_no(){

		String sql = "\r\n" + 
				"select \r\n" + 
				"a.dcrf_no as\"DCRF No.\", \r\n" + 
				"trim(b.dept_alias) as \"Dept.\", \r\n" + 
				"upper(trim(cc.last_name)) as \"Prepared By\",\r\n" + 
				"to_char((case when a.date_edited is not null then a.date_edited else a.date_created end),'MM-dd-yyyy') as \"Prepared Date\" ,\r\n" + 
				"a.status  as \"Status\"," +
				"upper(trim(e.last_name)) as \"In-Charge\"," +
				"(case when a.date_conformed is not null then null else\n" + 
				"		case when a.date_conformed is null and a.dcrf_no::int < 54 and a.date_fixed is not null then null else \n" + 
				"		(EXTRACT(epoch FROM (now()-a.date_created))/86400)::int  end end) as \"Days Lapsed\",\n" + 
				"(case when a.date_conformed is not null then (EXTRACT(epoch FROM (a.date_conformed - a.date_created))/86400)::int else\n" + 
				"		case when a.date_conformed is null and a.dcrf_no::int < 54 and a.date_fixed is not null " +
				"		then (EXTRACT(epoch FROM (a.date_fixed - a.date_created))/86400)::int else null end end) as \"Days to Finish\", \r\n" + 
				"a.error_desc as \"Change Desc\", a.user_remarks as \"Remarks\", b.dept_code as \"Dept Code\", a.created_by as \"Requester\", \r\n"+ 
				"b.dept_head_code as \"Head\" \r\n" + 
				"from rf_dcrf_header a\r\n" + 
				"left join mf_department b on a.dept_code = b.dept_code\r\n" + 
				"left join em_employee c on (case when a.edited_by = '' then a.edited_by else a.created_by end) = c.emp_code\r\n" + 
				"left join rf_entity cc on c.entity_id = cc.entity_id\r\n" +
				"left join (select distinct on (dcrf_no) dcrf_no, module from rf_dcrf_detail where status_id != 'I') d on a.dcrf_no =d.dcrf_no \n" +
				"left join (select \n" + 
				"	a.module, a.privileges, a.parent, c.last_name \n" + 
				"	from ( select distinct on (privileges) * from mf_privileges where in_charge is not null ) a\n" + 
				"	left join em_employee b on a.in_charge = b.emp_code \n" + 
				"	left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"	) e on upper(d.module) = upper(e.privileges)   " + 
				"order by a.dcrf_no desc \n\n" ;

		return sql;

	}	
	private void reapplyAllPayments() {
		ArrayList<String> listRecId = new ArrayList<String>();

		for(int x =0; x < modelPayments.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPayments.getValueAt(x, 0);
		

			if(isSelected){
				Integer rec_id = (Integer) modelPayments.getValueAt(x, 23);
				listRecId.add(String.format("'%s'", rec_id.toString()));
			}
		}	
		
		String rec_id = listRecId.toString().replaceAll("\\[|\\]", "");
		String strSQL = "SELECT sp_reapply_all_payments_V2('"+lookupClient.getValue()+"','"+txtProj.getText()+"','"+txtUnit.getText()+"',"+seq_no+",ARRAY["+ rec_id +"]::int[]);" ; 

		pgSelect db = new pgSelect();
		db.select(strSQL);
		FncSystem.out("Reapply:", strSQL);
	}






}
