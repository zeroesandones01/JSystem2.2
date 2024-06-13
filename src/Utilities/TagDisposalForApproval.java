package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.modelTagdisposalforapproval;

public class TagDisposalForApproval extends _JInternalFrame {

	public static String title = "Tag Disposal for Approval";
	public static Dimension framesize = new Dimension(700, 600);


	private JScrollPane scrollAssets;
	private static _JTableMain tblAssets;
	private static JList rowheaderAssets;
	private static modelTagdisposalforapproval modelAssets;


	private static _JLookup txtBranchID;
	private static _JLookup txtbatch;

	private JTextField txtBranch;
	private JCheckBox chkall;

	private static JButton btnPreview;
	public static JButton btnSave;
	public static JButton btncancel;
	public static JButton btndispose;
	public String allbatch="";
	public Date isdisposed;
	private JTextField txtStatus;
	private JTextField txtcustodian;
	private _JLookup lookupcustodian;
	public String reporttitle="";
	public static JButton btnedit;
	

	public TagDisposalForApproval(){
		super(title, false, true, true, true);
		initGUI();
	}

	public TagDisposalForApproval (String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public TagDisposalForApproval (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(framesize );
		this.setPreferredSize(framesize );
		this.setLayout(new BorderLayout());
		{
			JPanel pnlmain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlmain);
			pnlmain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				JPanel panFilters = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlmain.add(panFilters, BorderLayout.PAGE_START);
				panFilters.setPreferredSize(new Dimension(0, 120));
				panFilters.setBorder(JTBorderFactory.createTitleBorder("Filters", FncLookAndFeel.systemFont_Bold.deriveFont(10f))); 
				{
					
					{
						JPanel panBatchSerach = new JPanel(new BorderLayout(5, 5));
						panFilters.add(panBatchSerach);
						{
							{
								JPanel panBatch = new JPanel(new BorderLayout(5, 5));
								panBatchSerach.add(panBatch, BorderLayout.LINE_START);
								panBatch.setPreferredSize(new Dimension(200, 0));
								{
									{
										JLabel lblbatch = new JLabel("Batch");
										panBatch.add(lblbatch, BorderLayout.WEST);
										lblbatch.setPreferredSize(new Dimension(96, 0));
									}
									{
										txtbatch = new _JLookup("");
										panBatch.add(txtbatch, BorderLayout.CENTER); 
										txtbatch.setReturnColumn(0);
										//txtbatch.setLookupSQL("select distinct batch, date_created from tbl_asset_disposal");
										txtbatch.setLookupSQL("select distinct on (batch)batch, date_created, date_disposed from tbl_asset_disposal");
										txtbatch.setHorizontalAlignment(_JLookup.CENTER);
										txtbatch.addLookupListener(new LookupListener() {

											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet(); 

												if (data!=null) {
													 isdisposed = (Date) data[2];
													
													txtbatch.setValue(data[0].toString());
													displayfortagging(txtbatch.getValue());
													
													if(UserInfo.EmployeeCode.equals("901135")|| UserInfo.EmployeeCode.equals("900767") ) {
														
														if(isdisposed != null) {
															//System.out.println("isdisposed != null");
															
															control_buttons(false, true, true, false);
															//txtStatus.setText("DISPOSED");
														}else {
															control_buttons(false, true, true, true); 
															//txtStatus.setText("FOR APPROVAL");
															}
														
													}else { control_buttons(false, true, false, false); }
													
													
												}
											}
										});
										txtbatch.setValue("");
									} 
								}
							}
													
						}
					}
					{
						JPanel panBranch = new JPanel(new BorderLayout(5, 5));
						panFilters.add(panBranch);
						{
							{
								JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5)); 
								panBranch.add(panLabel, BorderLayout.LINE_START);
								panLabel.setPreferredSize(new Dimension(200, 0));
								{
									{
										JLabel lblbcustodian = new JLabel("Custodian");
										panLabel.add(lblbcustodian); 
									}
									{
										lookupcustodian = new _JLookup("");
										panLabel.add(lookupcustodian);  
										lookupcustodian.setReturnColumn(0);
										lookupcustodian.setLookupSQL(getCustodian());
										lookupcustodian.setHorizontalAlignment(_JLookup.CENTER);
										lookupcustodian.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet(); 

												if (data!=null) {
													lookupcustodian.setValue(data[1].toString());
													System.out.println("current_cust= "+lookupcustodian.getValue());
													txtcustodian.setText(data[0].toString());
													chkall.setSelected(false);
													displayfortagging(txtbatch.getText());
													
												} else {
																									
												}
											}
										});
										lookupcustodian.addKeyListener(new KeyListener() {

											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													lookupcustodian.setValue("");
													txtcustodian.setText("");
													
												}
											}

											public void keyReleased(KeyEvent e) {

											}

											public void keyPressed(KeyEvent e) {

											}
										});
										lookupcustodian.setValue("");
									}
								}
							}
							{
								txtcustodian = new JTextField(""); 
								panBranch.add(txtcustodian, BorderLayout.CENTER); 
								txtcustodian.setHorizontalAlignment(JTextField.CENTER);
								txtcustodian.setEditable(false);
							}
						}
					}
					{
						JPanel pnlall = new JPanel(new BorderLayout(5, 5));
						panFilters.add(pnlall);
						{
							JLabel lblall = new JLabel("View All Disposed Bacth");
							pnlall.add(lblall, BorderLayout.WEST);
							lblall.setPreferredSize(new Dimension(120, 0));
						}
						{
							chkall = new JCheckBox("");
							pnlall.add(chkall, BorderLayout.CENTER);
							chkall.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									
									if(e.getStateChange() == ItemEvent.SELECTED) {
										System.out.println("chkall1= "+e.getStateChange());
										allbatch="all";
										txtbatch.setText("");
										txtbatch.setEditable(false);
										lookupcustodian.setValue("");
										txtcustodian.setText("");
										displayfortagging(txtbatch.getText());
										//txtStatus.setText("DISPOSED");
										control_buttons(false, true, true, false);
									}
									
									if(e.getStateChange() == ItemEvent.DESELECTED) {
										System.out.println("chkall2= "+e.getStateChange());
										displayfortagging(txtbatch.getText());
										//txtStatus.setText("");
										txtbatch.setEditable(true);
									}
								}
							});
						}
					}
					
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout());
				pnlmain.add(pnlCenter, BorderLayout.CENTER);
				{
					scrollAssets = new JScrollPane();
					pnlCenter.add(scrollAssets);
					scrollAssets.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelAssets = new modelTagdisposalforapproval();
						tblAssets = new _JTableMain(modelAssets);
						scrollAssets.setViewportView(tblAssets);
						modelAssets .setEditable(true);
						tblAssets.getTableHeader().setEnabled(false); 
						tblAssets.setFillsViewportHeight(false);

						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

						tblAssets.getColumnModel().getColumn(0).setPreferredWidth(50); 
						tblAssets.getColumnModel().getColumn(1).setPreferredWidth(70); 
						tblAssets.getColumnModel().getColumn(2).setPreferredWidth(160); 
						tblAssets.getColumnModel().getColumn(3).setPreferredWidth(270);
						tblAssets.getColumnModel().getColumn(4).setPreferredWidth(90); 
						tblAssets.getColumnModel().getColumn(6).setPreferredWidth(300);

						tblAssets.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
					}
					{
						rowheaderAssets = tblAssets.getRowHeader();
						scrollAssets.setRowHeaderView(rowheaderAssets);
						scrollAssets.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1, 4, 5, 5));
				pnlmain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnSave = new JButton("Save for approval");
						pnlSouth.add(btnSave);
						btnSave.setEnabled(false);
						btnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (JOptionPane.showConfirmDialog(null, "Tag for disposal?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
									try {
										save_tagged();
										JOptionPane.showMessageDialog(null, "For disposal saved!");
									} catch (Exception ex) {
										ex.printStackTrace();
										JOptionPane.showMessageDialog(null, "Something went wrong");
									}
								} else {
									JOptionPane.showMessageDialog(null, "Tagging did not proceed!");
								}
							}

						});
					}
					{
						btncancel= new JButton("Cancel");
						pnlSouth.add(btncancel);
						btncancel.setEnabled(false);
						btncancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								txtbatch.setValue("");
								chkall.setSelected(false);
								//txtStatus.setText("");
								lookupcustodian.setValue("");
								txtcustodian.setText("");
								displayfortagging(txtbatch.getValue());
							}
						});
					}
					{
						btnPreview = new JButton("Preview"); 
						pnlSouth.add(btnPreview);
						btnPreview.setEnabled(false);
						btnPreview.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								if (txtbatch.getValue().equals("")) {
									if(chkall.isSelected()) {
										 reporttitle = "List of Disposed Asset";
										
										Map<String, Object> mapParameters = new HashMap<String, Object>();
										mapParameters.put("current_cust", lookupcustodian.getValue());
										mapParameters.put("report_title", reporttitle);
										mapParameters.put("01_batch", txtbatch.getValue());
										mapParameters.put("allbatch", allbatch);
										mapParameters.put("prepared_by", UserInfo.FullName);
										FncReport.generateReport("/Reports/rpt_asset_for_disposal.jasper", "Asset for disposal", "", mapParameters);
										
									}else {
										if(txtbatch.getValue().equals("") && !chkall.isSelected() &&  !lookupcustodian.getValue().equals("") ) {
											reporttitle = "List of Disposed Asset";
											
											Map<String, Object> mapParameters = new HashMap<String, Object>();
											mapParameters.put("current_cust", lookupcustodian.getValue());
											mapParameters.put("report_title", reporttitle);
											mapParameters.put("01_batch", txtbatch.getValue());
											mapParameters.put("allbatch", allbatch);
											mapParameters.put("prepared_by", UserInfo.FullName);
											FncReport.generateReport("/Reports/rpt_asset_for_disposal.jasper", "Asset for disposal", "", mapParameters);
											
										}else { JOptionPane.showMessageDialog(null, "Select a batch first!"); } 
									}
									
								} else {
									if(isdisposed != null) {
										reporttitle = "List of Disposed Asset";
									}else { reporttitle = "List of Assets For Disposal"; }
									
									Map<String, Object> mapParameters = new HashMap<String, Object>();
									mapParameters.put("current_cust", lookupcustodian.getValue());
									mapParameters.put("report_title", reporttitle);
									mapParameters.put("01_batch", txtbatch.getValue());
									mapParameters.put("allbatch", allbatch);
									mapParameters.put("prepared_by", UserInfo.FullName);
									
									System.out.println("current_cust: "+lookupcustodian.getValue() );
									System.out.println("01_batch: "+txtbatch.getValue() );
									System.out.println("allbatch: "+allbatch );
									
									FncReport.generateReport("/Reports/rpt_asset_for_disposal.jasper", "Asset for disposal", "", mapParameters);
								}
							}
						});
					}
					{
						btndispose = new JButton("Dispose");
						pnlSouth.add(btndispose);
						btndispose.setEnabled(false);
						btndispose.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(txtbatch.getText().equals("")) {
									JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select Batch!");
								}else {
									if (JOptionPane.showConfirmDialog(null, "Are you sure you want to Dispose batch '"+txtbatch.getText()+"'?" , "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
										
										try {
											if(UserInfo.EmployeeCode.equals("901135") || UserInfo.EmployeeCode.equals("900767")) {
												DisposeAsset();
												JOptionPane.showMessageDialog(null, "Disposal of asset done!");
												
											}else {JOptionPane.showMessageDialog(null, "You are not Authorized to Dispose assets!");}
											
										} catch (Exception ex) {
											ex.printStackTrace();
											JOptionPane.showMessageDialog(null, "Something went wrong");
										}
									}else {
										JOptionPane.showMessageDialog(null, "Dispose did not proceed!");
									}
									
								}
							}
						});
					}
					
				}
			}

		}
		//displayfortagging(txtbatch.getValue(), txtBranchID.getValue()); 
		displayfortagging(txtbatch.getValue());
		if(UserInfo.EmployeeCode.equals("901135") //Marinette
				|| UserInfo.EmployeeCode.equals("900767")
				|| UserInfo.EmployeeCode.equals("901117")		
			) {
				modelAssets.setEditable(true);
		}else {modelAssets.setEditable(false);}
	}
	
	public static String getCustodian(){
		
		String strsql="select b.entity_name,a.emp_code,e.co_id,e.company_name, company_logo, company_alias\n" + 
				"from  em_employee a\n" + 
				"left join rf_entity b ON a.entity_id=b.entity_id\n" + 
				"left join mf_department as c on a.dept_code=c.dept_code\n" + 
				"left join mf_division as d on c.division_code=d.division_code \n" + 
				"left join mf_company e on a.co_id=e.co_id\n" + 
				"where  emp_status not in('c') ";// view inactive emp
				//"where  emp_status not in('I') ";//view active emp only
				//+ "and e.co_id::int="+co_id+"";
		
		System.out.println("getCustodian= "+strsql);
		
		return strsql;



		}

	public static void control_buttons(Boolean save, Boolean cancel, Boolean preview, Boolean dispose) {
		btnSave.setEnabled(save);
		btncancel.setEnabled(cancel);
		btnPreview.setEnabled(preview);
		btndispose.setEnabled(dispose);
	}
	
	private void save_tagged() {
		pgUpdate dbExec = new pgUpdate();

		String strBatch = FncGlobal.GetString("select concat('D', \n" + 
				"right(date_part('year', now())::varchar, 2) , \n" + 
				"lpad(date_part('month', now())::varchar, 2, '0'), \n" + 
				"lpad(date_part('day', now())::varchar, 2, '0'),\n" + 
				"lpad((coalesce(max(right(replace(batch, 'D', ''), 3)::int), 0)::int+1)::varchar, 3, '0'))\n" + 
				"from tbl_asset_disposal\n" + 
				"where date_created::date = now()::date"); 

		txtbatch.setValue(strBatch);
		System.out.println("strBatch= "+strBatch);

		for(int x=0; x<modelAssets.getRowCount(); x++){
			if ((Boolean) modelAssets.getValueAt(x, 0)) {
				dbExec.executeUpdate("insert into tbl_asset_disposal (batch, asset_no, status_id, date_created, created_by)\n" + 
						"values \n" + 
						"(\n" + 
						"	'"+strBatch+"', \n" + 
						"	"+modelAssets.getValueAt(x, 1)+", \n" + 
						"	'A', \n" + 
						"	now(), \n" + 
						"	'"+UserInfo.EmployeeCode+"'\n" + 
						"); ", true);

			}
		} 

		dbExec.commit();
		//displayfortagging(strBatch,  txtBranchID.getValue());
		displayfortagging(strBatch);
		control_buttons(false, true, true, false);
	}
	
	private void DisposeAsset() {
		pgUpdate dbExec2 = new pgUpdate();

		for(int x=0; x<modelAssets.getRowCount(); x++){
			
			if ((Boolean) modelAssets.getValueAt(x, 0)) {
				
				//dbExec2.executeUpdate("update tbl_asset set status='I', remarks='DISPOSED', date_disposed=current_date where asset_no="+modelAssets.getValueAt(x, 1)+" ",false);
				dbExec2.executeUpdate("update tbl_asset_disposal set status_id='I', date_disposed=current_date, disposed_by='"+UserInfo.EmployeeCode+"' where asset_no="+modelAssets.getValueAt(x, 1)+" ",false);
				/*dbExec2.executeUpdate(
						"INSERT INTO tbl_asset_history( \n" + 
						"asset_code, \n" + 
						"prev_cust, \n" + 
						"current_cust, \n" + 
						"trans_date, \n" + 
						"reason, \n" + 
						"remarks, \n" + 
						"status, \n" + 
						"move_no, \n" + 
						"asset_no, \n" + 
						"trans_by) \n" + 
						"VALUES (\n" + 
						"'"+modelAssets.getValueAt(x, 2)+"', \n" + //asset_code
						"(select current_cust from tbl_asset where asset_no ='"+modelAssets.getValueAt(x, 1)+"'), \n" +  //prev_cust
						"(select current_cust from tbl_asset where asset_no ='"+modelAssets.getValueAt(x, 1)+"'), \n" +  //current_cust
						"current_date, \n" +  //trans_date
						"'Disposed', \n" +  //reason
						"'Disposed by "+UserInfo.FullName+"', \n" +  //remarks 
						"'I', \n" +  //status
						"(select coalesce(max(move_no),0) + 1 from tbl_asset_history), \n" +   //move_no
						"'"+modelAssets.getValueAt(x, 1)+"', \n" +  //asset_no
						"'"+UserInfo.EmployeeCode+"') "
						,false);*/

			}
		} 

		dbExec2.commit();
		displayfortagging("");
		control_buttons(true, true, false, false);
		
	}

	private void displayfortagging(String batch) {
		modelAssets.clear();
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderAssets.setModel(listModel);//Setting of listModel into rowHeader.
		String current_cust=lookupcustodian.getValue();
		String strSQL = ""; 
		
		
		if (!txtbatch.getValue().equals("")) {
			
			if(chkall.isSelected()) {
				System.out.println("1");
				strSQL = "select distinct on (asset_no) true as tag, a.asset_no, a.asset_code, a.asset_name, a.date_acquired, b.date_disposed, entity_name  \n" + 
						"from tbl_asset a\n" + 
						"left join tbl_asset_disposal b on a.asset_no = b.asset_no \n" +
						"left join em_employee c on a.current_cust = c.emp_code::int \n" +
						"left join rf_entity d on c.entity_id = d.entity_id  \n" +
						"where b.batch is not null \n" +
						"order by a.asset_no";
						control_buttons(false, true, true, false);
			}else {	
				System.out.println("2");
				strSQL = "select distinct on (asset_no) true as tag, a.asset_no, a.asset_code, a.asset_name, a.date_acquired,  b.date_disposed, entity_name \n" + 
						"from tbl_asset a\n" + 
						"left join tbl_asset_disposal b on a.asset_no = b.asset_no \n" +
						"left join em_employee c on a.current_cust = c.emp_code::int \n" +
						"left join rf_entity d on c.entity_id = d.entity_id  \n" +
						"where  \n";
						if(!current_cust.equals("")) 
							
							{strSQL = strSQL + " a.current_cust='"+current_cust+"' and b.date_disposed is not null and (b.batch = '"+batch+"' or '"+batch+"' = '') \n"; } 
						else {strSQL = strSQL + "  (b.batch = '"+batch+"' or '"+batch+"' = '') \n";}
						//if( !txtbatch.getValue().equals("") ) { strSQL = strSQL + " and (b.batch = '"+batch+"' or '"+batch+"' = '') \n"; }
						strSQL = strSQL +
						"order by a.asset_no";
						control_buttons(false, true, true, false);
			}
		} else {
			
			if(chkall.isSelected()) {
				System.out.println("3");
				strSQL = "select distinct on (asset_no) true as tag, a.asset_no, a.asset_code, a.asset_name, a.date_acquired, b.date_disposed, entity_name  \n" + 
						"from tbl_asset a\n" + 
						"left join tbl_asset_disposal b on a.asset_no = b.asset_no \n" +
						"left join em_employee c on a.current_cust = c.emp_code::int \n" +
						"left join rf_entity d on c.entity_id = d.entity_id  \n" +
						"where b.batch is not null \n" +
						"order by a.asset_no";
				control_buttons(false, true, true, false);
			}else {
				System.out.println("4");
				strSQL = "select distinct on (asset_no) (case when '"+lookupcustodian.getValue()+"' != '' then true else false end) as tag, a.asset_no, a.asset_code, a.asset_name, a.date_acquired, b.date_disposed, entity_name  \n" + 
						"from tbl_asset a\n" + 
						"left join tbl_asset_disposal b on a.asset_no = b.asset_no \n" +
						"left join em_employee c on a.current_cust = c.emp_code::int \n" +
						"left join rf_entity d on c.entity_id = d.entity_id  \n" +
						"where  \n" ;
						if(!current_cust.equals("")) {strSQL = strSQL + " a.current_cust='"+current_cust+"' and b.date_disposed is not null \n"; }
						else { strSQL = strSQL + "b.batch is null \n"; }
						strSQL = strSQL +	
						"order by a.asset_no";
						
						if(!lookupcustodian.getValue().equals("")) {
							control_buttons(false, true, true, false);
						}else { control_buttons(true, true, false, false); }
						
			}
		}
		
		System.out.println("displayfortagging= "+strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelAssets.addRow(db.getResult()[x]);
				listModel.addElement(modelAssets.getRowCount());
			}
		}
		
	}
}

