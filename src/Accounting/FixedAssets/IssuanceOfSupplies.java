package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelIssuanceOfSupplies;

public class IssuanceOfSupplies extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title="Issuance of Supplies";
	public static Dimension framesize= new Dimension(700,500);
	private static JPanel pnlmain;
	private static JButton btnsave;
	private static JButton btncancel;
	private static JButton btnaddnew;
	private  JScrollPane scrollissuance;
	private static modelIssuanceOfSupplies modelissuance_supplies;
	private static _JTableMain tblissuance_supplies;
	private static JList rowheaderissuance_supplies;
	private static JTextField txtremarks;
	private static _JDateChooser date_issued;
	private static _JLookup lookupreq_id;
	private static _JLookup lookupissue_id;
	protected static String co_id;
	protected static String request_id;
	protected static String requester_id;
	protected static Date date_requested;
	protected static String div_code;
	protected static String dept_code;
	private static String issue_id;
	private static String remarks;
	
	
	public IssuanceOfSupplies(){
		super(title, false, true, true, true);
		initGUI();
		enable_buttons(true, false, true);
	}
	public IssuanceOfSupplies (String title) {
		super(title, true, true, true, true);
		initGUI();
	}
	
	public IssuanceOfSupplies (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(framesize );
		this.setPreferredSize(framesize );
		this.setLayout(new BorderLayout());
		{
			pnlmain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlmain);
			pnlmain.setBorder(_EMPTY_BORDER);
			{
				JPanel pnlnorth = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 90));
				pnlnorth.setBorder(JTBorderFactory.createTitleBorder(""));
				{
					JPanel pnlreq_id_supplier= new JPanel(new BorderLayout(5,5));
					pnlnorth.add(pnlreq_id_supplier, BorderLayout.WEST);
					pnlreq_id_supplier.setPreferredSize(new Dimension(350, 0));
					{
						JPanel pnllabels_west = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlreq_id_supplier.add(pnllabels_west, BorderLayout.WEST);
						pnllabels_west.setPreferredSize(new Dimension(70, 0));
						{
							JLabel lblreq_id = new JLabel("Req. ID");
							pnllabels_west.add(lblreq_id);
						}
						{
							JLabel lblsupplier = new JLabel("Remarks");
							pnllabels_west.add(lblsupplier);
						}
					}
					{
						JPanel pnlfields_west = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlreq_id_supplier.add(pnlfields_west, BorderLayout.CENTER);
						{
							JPanel pnlreq_id = new JPanel(new BorderLayout(5,5));
							pnlfields_west.add(pnlreq_id, BorderLayout.WEST);
							{
								lookupreq_id= new _JLookup();
								pnlreq_id.add(lookupreq_id, BorderLayout.WEST);
								lookupreq_id.setReturnColumn(0);
								lookupreq_id.setPreferredSize(new Dimension(80,0));
								lookupreq_id.setEditable(false);
								lookupreq_id.setLookupSQL(getreq_id());
								lookupreq_id.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null) {
											
											request_id = (String) data[0];
											requester_id = (String)data[2];
											date_requested = (Date)data[3];
											div_code = (String)data[4];
											dept_code = (String)data[5];
											co_id = (String)data[6];
											
											
											display_request();
											modelissuance_supplies.setEditable(true);
										}
									}
								});
							}
							{
								txtremarks = new JTextField();
								pnlfields_west.add(txtremarks);
							}
							/*{
								JLabel lblsupplier = new JLabel("");
								pnlfields_west.add(lblsupplier);
							}*/
						}
					}
				}
				{
					JPanel pnldate_issuance_id= new JPanel(new BorderLayout(5,5));
					pnlnorth.add(pnldate_issuance_id, BorderLayout.EAST);
					pnldate_issuance_id.setPreferredSize(new Dimension(200,0));
					{
						JPanel pnllabels_east = new JPanel(new GridLayout(2, 1, 5, 5));
						pnldate_issuance_id.add(pnllabels_east, BorderLayout.WEST);
						pnllabels_east.setPreferredSize(new Dimension(80, 0));
						{
							JLabel lbldate = new JLabel("Date", JLabel.TRAILING);
							pnllabels_east.add(lbldate);
						}
						{
							JLabel lblissuance_id = new JLabel("Issuance ID",JLabel.TRAILING);
							pnllabels_east.add(lblissuance_id);
						}
					}
					{
						JPanel pnlfields_east = new JPanel(new  GridLayout(2, 1, 5, 5));
						pnldate_issuance_id.add(pnlfields_east, BorderLayout.CENTER);
						{
							date_issued = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlfields_east.add(date_issued);
							date_issued.setEditable(false);
							date_issued.setEnabled(false);
						}
						{
							//txtissuance_id = new JTextField();
							//pnlfields_east.add(txtissuance_id);
							lookupissue_id = new _JLookup();
							pnlfields_east.add(lookupissue_id);
							lookupissue_id.setReturnColumn(0);
							lookupissue_id.setLookupSQL(getissue_id());
							lookupissue_id.setEditable(true);
							lookupissue_id.setEnabled(true);
							lookupissue_id.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null) {
										String issueid = (String) data [0];
										String reqid = (String) data[2];
										Date dateissue = (Date)data[3];
										String remarks= (String) data[4];
										
										lookupreq_id.setValue(reqid);
										txtremarks.setText(remarks);
										date_issued.setDate((Date) dateissue);
										display_issued();
									}
								}
							});
						}
					}
				}
			}
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(5,5));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder("List Of Supplies"));
				{
					scrollissuance = new JScrollPane();
					pnlcenter.add(scrollissuance);
					scrollissuance.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelissuance_supplies = new modelIssuanceOfSupplies();
						tblissuance_supplies = new _JTableMain(modelissuance_supplies);
						scrollissuance.setViewportView(tblissuance_supplies);
						modelissuance_supplies.setEditable(false);
						
						tblissuance_supplies.getColumnModel().getColumn(0).setPreferredWidth(150);
						tblissuance_supplies.getColumnModel().getColumn(1).setPreferredWidth(250);
						tblissuance_supplies.getColumnModel().getColumn(2).setPreferredWidth(250);
						tblissuance_supplies.getColumnModel().getColumn(3).setPreferredWidth(100);
						tblissuance_supplies.getColumnModel().getColumn(4).setPreferredWidth(100);
						tblissuance_supplies.hideColumns("SUPPLIER");
						
					}
					{
						rowheaderissuance_supplies = tblissuance_supplies.getRowHeader();
						scrollissuance.setRowHeaderView(rowheaderissuance_supplies);
						scrollissuance.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 5, 5, 5));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 35));
				{
					JLabel lblextra1 = new JLabel("");
					pnlsouth.add(lblextra1);
				}
				{
					btnaddnew = new JButton("ADD NEW");
					pnlsouth.add(btnaddnew);
					btnaddnew.setActionCommand("ADDNEW");
					btnaddnew.setEnabled(false);
					btnaddnew.addActionListener(this);
				}
				{
					btnsave= new JButton("SAVE");
					pnlsouth.add(btnsave);
					btnsave.setActionCommand("SAVE");
					//btnsave.setFocusPainted(false);
					btnsave.setEnabled(false);
					btnsave.addActionListener(this);
				}
				{
					btncancel= new JButton("CANCEL");
					pnlsouth.add(btncancel);
					btncancel.setActionCommand("CANCEL");
					btncancel.setEnabled(false);
					btncancel.addActionListener(this);
				}
				
				{
					JLabel lblextra2 = new JLabel("");
					pnlsouth.add(lblextra2);
				}
			}
		}

	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("ADDNEW")) {
			enable_buttons(false, true, true);
			lookupreq_id.setEditable(true);
			txtremarks.setEditable(true);
			date_issued.setDate(FncGlobal.getDateToday());
			lookupissue_id.setValue(generate_issue_id());
			lookupissue_id.setEditable(false);
		}
		
		if(e.getActionCommand().equals("SAVE")) {
			if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to issue selected supplies?", "", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();
				insert_issuance(db);
				db.commit();
				enable_buttons(true, false, true);
				lookupreq_id.setEditable(false);
				lookupreq_id.setValue("");
				lookupissue_id.setValue("");
				lookupissue_id.setEnabled(true);
				lookupissue_id.setEditable(true);
				txtremarks.setText("");
				txtremarks.setEditable(false);
				FncTables.clearTable(modelissuance_supplies);
				cleartable_rowheader();
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "Selected supplies already issued/save!");
			}
		}
		
		if(e.getActionCommand().equals("CANCEL")) {
			
			enable_buttons(true, false, true);
			lookupreq_id.setEditable(false);
			lookupreq_id.setValue("");
			date_issued.setDate(null);
			lookupissue_id.setValue("");
			lookupissue_id.setEditable(true);
			txtremarks.setText("");
			txtremarks.setEditable(false);
			FncTables.clearTable(modelissuance_supplies);
			cleartable_rowheader();
			
		}
	}
	
	private static void enable_buttons(Boolean addnew, Boolean save, Boolean cancel) {
		btnaddnew.setEnabled(addnew);
		btnsave.setEnabled(save);
		btncancel.setEnabled(cancel);
	}
	
	public static void cleartable_rowheader() {
		 
		FncTables.clearTable(modelissuance_supplies);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderissuance_supplies.setModel(listModel);//Setting of listModel into rowHeader.
	}
	
	public static String getreq_id() {
		
		String strsql="select distinct on (a.request_id)\n" + 
				"a.request_id,\n" + 
				"get_employee_name(a.requester_id) as requester, \n" + 
				"requester_id,\n" + 
				"a.date_created as date_requested,\n" + 
				"c.division_code,\n" + 
				"d.dept_code,\n" + 
				"b.co_id\n"+
				"from rf_stationary_supplies_request a\n" + 
				"left join em_employee b on a.requester_id=b.emp_code\n" + 
				"left join mf_division c on b.div_code=c.division_code\n" + 
				"left join mf_department d on b.dept_code=d.dept_code\n" + 
				"where a.status_id='A'";
		
		return strsql;
	}
	
	public static String getissue_id() {
		String strsql="select distinct on (issue_id)issue_id,get_employee_name(requester_id),request_id, date_issued, remarks from rf_issuance_supplies ";
		
		return strsql;
	}
	public static String generate_issue_id() {
		
		pgSelect db = new pgSelect();
		String strsql="select  trim(to_char(coalesce(max(issue_id)::int,0) +1,'000000'))  from  rf_issuance_supplies";
		db.select(strsql);
		return db.Result[0][0].toString();
	}
	
	public static void display_request() {
		
		FncTables.clearTable(modelissuance_supplies);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderissuance_supplies.setModel(listModel);//Setting of listModel into rowHeader.
		
		String strsql="select \n" + 
				"false,\n" + 
				"a.item_id,\n" + 
				"a.description,\n" + 
				"a.quantity,\n" + 
				"a.unit,\n" + 
				"(case when b.actual_release= null then null else coalesce(a.quantity::int,0) - coalesce(b.actual_release::int,0) end ) as actual_qty,\n" + 
				//"'' as actual_qty, \n" + 
				"a.supplier\n" + 
				"from rf_stationary_supplies_request a\n" + 
				"left join  rf_issuance_supplies b on a.request_id=b.request_id and a.item_id=b.item_id\n" + 
				"where  a.request_id='"+lookupreq_id.getValue()+"' ";
		
		System.out.println("display_request: "+strsql);
		pgSelect db = new pgSelect();
		db.select(strsql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++)
			{
				modelissuance_supplies.addRow(db.getResult()[x]);
				listModel.addElement(modelissuance_supplies.getRowCount());
			}
		}
	}
	
	public static void display_issued() {
		FncTables.clearTable(modelissuance_supplies);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderissuance_supplies.setModel(listModel);//Setting of listModel into rowHeader.
		String strsql="select true,\n" + 
				"item_id,\n" + 
				"description,\n" + 
				"quantity,\n" + 
				"unit,\n" + 
				"actual_release\n" + 
				"from rf_issuance_supplies\n" + 
				"where issue_id='"+lookupissue_id.getValue()+"'";
		System.out.println("display_issued: "+strsql);
		pgSelect db = new pgSelect();
		db.select(strsql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++)
			{
				modelissuance_supplies.addRow(db.getResult()[x]);
				listModel.addElement(modelissuance_supplies.getRowCount());
			}
		}
		
	}
	
	public static void insert_issuance(pgUpdate db) {
		int x  = 0;	
		int y  = 1;
		int rw = tblissuance_supplies.getModel().getRowCount();
		issue_id= lookupissue_id.getValue();
		remarks = txtremarks.getText();
		
		
		while(x < rw) {
			Boolean selected = (Boolean) modelissuance_supplies.getValueAt(x, 0);
			String item_id = (String) modelissuance_supplies.getValueAt(x, 1);
			String  description = (String) modelissuance_supplies.getValueAt(x, 2);
			String  qty= (String) modelissuance_supplies.getValueAt(x, 3);
			String  unit = (String) modelissuance_supplies.getValueAt(x, 4);
			String  actual_release= (String) modelissuance_supplies.getValueAt(x, 5);
			String  supplier= (String) modelissuance_supplies.getValueAt(x, 6);
			
			if(selected) {
				String strsql="insert into rf_issuance_supplies\n" + 
						"(\n" + 
						"	co_id,\n" + 
						"	issue_id,\n" + 
						"	div_id,\n" + 
						"	dept_id,\n" + 
						"	requester_id,\n" + 
						"	request_id,\n"+
						"	description,\n" + 
						"	supplier,\n" + 
						"	item_id,\n" + 
						"	remarks,\n" + 
						"	status_id,\n" + 
						"	unit,\n" + 
						"	quantity,\n" + 
						"	actual_release,\n" + 
						"	issued_torequester,\n"+
						"	date_issued,\n" + 
						"	date_added,\n" + 
						"	added_by\n" + 
						")\n" + 
						"values\n" + 
						"(\n" + 
						"	'"+co_id+"',\n" + 
						"	'"+issue_id+"',\n"+
						"	'"+div_code+"',\n" + 
						"	'"+div_code+"',\n" + 
						"	'"+requester_id+"',\n" + 
						"	'"+request_id+"',\n"+
						"	'"+description+"',\n" + 
						"	'"+supplier+"',\n" + 
						"	'"+item_id+"',\n" + 
						"	'"+remarks+"',\n" + 
						"	'A',\n" + 
						"	'"+unit+"',\n" + 
						"	'"+qty+"',\n" + 
						"	'"+actual_release+"',\n" + 
						"	'true',\n"+
						"	'"+date_issued.getDate()+"',\n" + 
						"	now(),\n" + 
						"	'"+UserInfo.EmployeeCode+"'\n" + 
						")";
				
				db.executeUpdate(strsql, false);
			}
			x++;
		}
		//db.commit();
	}

}
