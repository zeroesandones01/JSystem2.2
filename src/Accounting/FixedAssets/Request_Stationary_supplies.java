package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelrequest_stationary_supplies;

public class Request_Stationary_supplies extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title="Request Stationary and Supplies";
	public static Dimension framesize= new Dimension(700,500);
	private JPanel pnlmain;
	private static _JDateChooser request_date;
	private static JTextField txtrequester;
	private static JTextField txtdiv_dept;
	private _JLookup lookuprequester;
	private JTextField txtdiv_dept_code;
	private static JButton btncancel;
	private JScrollPane jscrollrequest_stationary_supplies;
	private static modelrequest_stationary_supplies modelreq_stationary_supp;
	private static _JTableMain tblreq_stationary_supp;
	private static JList rowheader_req_stationary_supp;
	protected String requester_name;
	protected static String requester_id;
	protected static String dept_id;
	protected String dept_name;
	protected String div_dept;
	private static JButton btndelete;
	private static _JLookup lookupdept_code;
	private static JButton btnaddnew;
	private static JButton btnpreview;
	private static JButton btnsave;
	protected static String div_id;
	protected static String co_id;
	private static _JLookup lookupreq_id;
	private static String co_name="CENQHOMES DEVELOPMENT CORPORATION";
	
	public Request_Stationary_supplies(){
		super(title, false, true, true, true);
		initGUI();
		enable_buttons(true, false, true, false,false);
	}
	public Request_Stationary_supplies (String title) {
		super(title, true, true, true, true);
		initGUI();
	}
	
	public Request_Stationary_supplies (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
				JPanel pnlnorth = new JPanel(new BorderLayout(5,5));
				pnlmain.add(pnlnorth,BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 60));
				{
					JPanel pnlrequester_div_dept = new JPanel(new BorderLayout(5, 5));
					pnlnorth.add(pnlrequester_div_dept, BorderLayout.WEST);
					pnlrequester_div_dept.setPreferredSize(new Dimension(450, 0));
					{
						JPanel pnllabels = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlrequester_div_dept.add(pnllabels, BorderLayout.WEST);
						pnllabels.setPreferredSize(new Dimension(70, 0));
						{
							JLabel lblrequester = new JLabel("Requester");
							pnllabels.add(lblrequester);
						}
						{
							JLabel lbldiv_dept = new JLabel("Dept");
							pnllabels.add(lbldiv_dept);
						}
					}
					{
						JPanel pnlfields = new JPanel(new BorderLayout(5, 5));
						pnlrequester_div_dept.add(pnlfields, BorderLayout.CENTER);
						{
							JPanel pnllookup = new JPanel(new GridLayout(2, 1, 5, 5));
							pnlfields.add(pnllookup, BorderLayout.WEST);
							pnllookup.setPreferredSize(new Dimension(75, 0));
							{
								lookuprequester = new _JLookup();
								pnllookup.add(lookuprequester);
								lookuprequester.setLookupSQL(getrequester());
								lookuprequester.setReturnColumn(1);
								lookuprequester.setEditable(false);
								lookuprequester.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										if(data !=null) {
											requester_name =(String) data[0];
											requester_id   =(String) data[1];
											co_id 		   =(String)data[2];
											div_id		   =(String)data[4];
											dept_id        =(String) data[7];
											dept_name	   =(String)data[8];
											div_dept	   =(String)data[10];
											
											
											txtrequester.setText(requester_name);
											lookupdept_code.setValue(dept_id);
											txtdiv_dept.setText(dept_name);
											
										}
									}
								});
								
							}
							{
								 lookupdept_code = new _JLookup();
								//txtdiv_dept_code = new JTextField();
								pnllookup.add(lookupdept_code);
								lookupdept_code.setEditable(false);
							}
							
						}
						{
							JPanel pnltextflds = new JPanel(new GridLayout(2, 1, 5, 5));
							pnlfields.add(pnltextflds, BorderLayout.CENTER);
							{
								txtrequester = new JTextField();
								pnltextflds.add(txtrequester);
							}
							{
								txtdiv_dept = new JTextField();
								pnltextflds.add(txtdiv_dept);
							}
						}
					}
				}
				{
					JPanel pnlnorth_east = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlnorth.add(pnlnorth_east, BorderLayout.EAST);
					pnlnorth_east.setPreferredSize(new Dimension(180, 0));
					{
						JPanel pnldate = new JPanel(new BorderLayout(5, 5));
						pnlnorth_east.add(pnldate, BorderLayout.EAST);
						pnldate.setPreferredSize(new Dimension(200, 0));
						{
							JPanel pnlreq_date = new JPanel(new BorderLayout(5, 5));
							pnldate.add(pnlreq_date, BorderLayout.NORTH);
							pnlreq_date.setPreferredSize(new Dimension(0, 30));
							
							{
								JLabel lbldate = new JLabel("Date", JLabel.TRAILING);
								pnlreq_date.add(lbldate, BorderLayout.WEST);
								lbldate.setPreferredSize(new Dimension(70, 0));
								
							}
							{
								request_date = new _JDateChooser("MM/dd/yyyy","##/##/####",'_');
								pnlreq_date.add(request_date,BorderLayout.CENTER);
								request_date.setEditable(false);
								request_date.setDate(FncGlobal.getDateToday());
							}
						}
						
					}
					{
						JPanel pnlrequest_id = new JPanel(new BorderLayout(5,5));
						pnlnorth_east.add(pnlrequest_id);
						{
							JLabel lblrequest_id = new JLabel("Req. ID", JLabel.TRAILING);
							pnlrequest_id.add(lblrequest_id, BorderLayout.WEST);
							lblrequest_id.setPreferredSize(new Dimension(70,0));
							
							
						}
						{
							lookupreq_id = new _JLookup();
							pnlrequest_id.add(lookupreq_id, BorderLayout.CENTER);
							lookupreq_id.setLookupSQL(getrequest_id());
							lookupreq_id.setReturnColumn(0);
							lookupreq_id.addLookupListener(new LookupListener() {
								

								private String request;
								private String employee_name;
								private String employee_id;
								private Date date_created;
								private String dept_code;
								

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null) {
										request = (String)data[0];
										employee_id= (String)data[1];
										employee_name=(String)data[2];
										date_created =(Date) data[3];
										dept_code = (String) data[4];
										dept_name = (String)data[5];
										
										display_request(request);
										lookupreq_id.setValue(request);
										lookuprequester.setValue(employee_id); 
										txtrequester.setText(employee_name);
										lookupdept_code.setValue(dept_code);
										txtdiv_dept.setText(dept_name);
										request_date.setEditable(false);
										request_date.setDate((Date)date_created);
										enable_buttons(false, false, true, true,true);
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
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder("List of Stationary and Supplies"));
				{
					jscrollrequest_stationary_supplies = new JScrollPane();
					pnlcenter.add(jscrollrequest_stationary_supplies);
					jscrollrequest_stationary_supplies.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelreq_stationary_supp = new modelrequest_stationary_supplies();
						tblreq_stationary_supp = new _JTableMain(modelreq_stationary_supp);
						jscrollrequest_stationary_supplies.setViewportView(tblreq_stationary_supp);
						
						tblreq_stationary_supp.getTableHeader().setEnabled(false);
						tblreq_stationary_supp.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblreq_stationary_supp.getColumnModel().getColumn(1).setPreferredWidth(450);
						tblreq_stationary_supp.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblreq_stationary_supp.getColumnModel().getColumn(3).setPreferredWidth(100);
						//tblreq_stationary_supp.hideColumns("SUPPLIER");
						
						
					}
					{
						rowheader_req_stationary_supp= tblreq_stationary_supp.getRowHeader();
						jscrollrequest_stationary_supplies.setRowHeaderView(rowheader_req_stationary_supp);
						jscrollrequest_stationary_supplies.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 5, 5, 5));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0,35));
				{
					 btnaddnew = new JButton("ADD NEW");
					 pnlsouth.add(btnaddnew);
					 btnaddnew.setEnabled(false);
					 btnaddnew.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							lookupreq_id.setValue(set_request_id());
							display_items(modelreq_stationary_supp, rowheader_req_stationary_supp);
							modelreq_stationary_supp.setEditable(true);
							lookuprequester.setEditable(true);
							lookuprequester.setValue("");
							lookupreq_id.setEditable(false);
							txtrequester.setText("");
							lookupdept_code.setValue("");
							txtdiv_dept.setText("");
							request_date.setDate(FncGlobal.getDateToday());
							request_date.setEditable(true);
							enable_buttons(false, true, true, false, false);
						}
					});
				}
				{
					btnsave = new JButton("SAVE");
					pnlsouth.add(btnsave);
					btnsave.setEnabled(false);
					btnsave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to save request?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE ) == JOptionPane.YES_OPTION ) {
								
								insert_request();
								JOptionPane.showMessageDialog(getTopLevelAncestor(), "Request already saved.");
								modelreq_stationary_supp.setEditable(false);
								display_request(lookupreq_id.getValue());
								enable_buttons(true, false, true, false, false);
								request_date.setEditable(false);
								preview_request_stationary_supplies(lookupreq_id.getValue(), co_name, txtdiv_dept.getText(), request_date.getDate(), txtrequester.getText(), UserInfo.FullName);
							}
						}
					});
				}
				{
					btncancel = new JButton("CANCEL");
					pnlsouth.add(btncancel);
					btncancel.setEnabled(false);
					btncancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							txtrequester.setText("");
							lookuprequester.setValue("");
							lookupdept_code.setValue("");
							lookuprequester.setEditable(false);
							lookupreq_id.setEditable(false);
							lookupreq_id.setEditable(true);
							txtdiv_dept.setText("");
							lookupreq_id.setValue("");
							request_date.setDate(FncGlobal.getDateToday());
							request_date.setEditable(false);
							btnsave.setEnabled(false);
							//display_items(modelreq_stationary_supp, rowheader_req_stationary_supp);
							enable_buttons(true, false, true, false,false);
							FncTables.clearTable(modelreq_stationary_supp);
							cleartable_rowheader();
							modelreq_stationary_supp.setEditable(false);
						}
					});
				}
				{
					btndelete = new JButton("DELETE");
					pnlsouth.add(btndelete);
					btndelete.setEnabled(false);
					btndelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to delete this request?", "", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
								
								delete_request(lookupreq_id.getValue());
								JOptionPane.showMessageDialog(getTopLevelAncestor(), "Request already deleted!");
								txtrequester.setText("");
								lookuprequester.setValue("");
								lookupdept_code.setValue("");
								lookuprequester.setEditable(false);
								lookupreq_id.setEditable(false);
								lookupreq_id.setEditable(true);
								txtdiv_dept.setText("");
								lookupreq_id.setValue("");
								request_date.setDate(FncGlobal.getDateToday());
								request_date.setEditable(false);
								btnsave.setEnabled(false);
								//display_items(modelreq_stationary_supp, rowheader_req_stationary_supp);
								enable_buttons(true, false, true, false,false);
								FncTables.clearTable(modelreq_stationary_supp);
								cleartable_rowheader();
								modelreq_stationary_supp.setEditable(false);
							}
							
						}
					});
				}
				{
					btnpreview = new JButton("PREVIEW");
					pnlsouth.add(btnpreview);
					btnpreview.setEnabled(false);
					btnpreview.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							preview_request_stationary_supplies(lookupreq_id.getValue(), co_name, txtdiv_dept.getText(), request_date.getDate(), txtrequester.getText(), UserInfo.FullName);
						}
					});
				}
			}
		}
	}
	
	public static void cleartable_rowheader() {
		 
		FncTables.clearTable(modelreq_stationary_supp);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheader_req_stationary_supp.setModel(listModel);//Setting of listModel into rowHeader.
	}
	
	public static void enable_buttons(Boolean addnew, Boolean save, Boolean cancel, Boolean preview, Boolean delete) {
		btnaddnew.setEnabled(addnew);
		btnsave.setEnabled(save);
		btncancel.setEnabled(cancel);
		btnpreview.setEnabled(preview);
		btndelete.setEnabled(delete);
	}
	
	public static void delete_request(String req_id ) {
		
		String strsql="update rf_stationary_supplies_request\n" + 
				"set status_id='I',date_edited= now(), edited_by='"+UserInfo.EmployeeCode+"'\n" + 
				"where request_id='"+req_id+"'\n" + 
				"and status_id='A'";
		
		System.out.println("delete_request: "+strsql);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(strsql, false);
		db.commit();
	}
	
	public static void insert_request() {
		 
		int x  = 0;	
		int y  = 1;
		int rw = tblreq_stationary_supp.getModel().getRowCount();
		String req_id = lookupreq_id.getValue();
		String req_date = request_date.getDate().toString();
		
		pgUpdate db = new pgUpdate();
		
		while(x < rw) {
			Boolean selected = (Boolean) modelreq_stationary_supp.getValueAt(x, 0);
			String description = (String) modelreq_stationary_supp.getValueAt(x, 1);
			String model = (String) modelreq_stationary_supp.getValueAt(x, 2);
			String  brand= (String) modelreq_stationary_supp.getValueAt(x, 3);
			String  qty= (String)modelreq_stationary_supp.getValueAt(x, 4);
			String unit = (String)modelreq_stationary_supp.getValueAt(x, 5);
			String  supplier_id= (String)modelreq_stationary_supp.getValueAt(x, 6);
			String  item_id= (String)modelreq_stationary_supp.getValueAt(x, 7);
			String  remarks= (String)modelreq_stationary_supp.getValueAt(x, 8);
			
			if(selected == false) {}
			else {
				String strsql="insert into rf_stationary_supplies_request \n" + 
						"(\n" + 
						"	co_id,\n" + 
						"	div_id,\n" + 
						"	dept_id,\n" + 
						"	requester_id,\n" + 
						"	request_id,\n" + 
						"	description,\n" + 
						"	model,\n" + 
						"	brand,\n" + 
						"	supplier,\n" + 
						"	item_id,\n" + 
						"	remarks,\n"+
						"	status_id,\n" + 
						"	unit,\n" + 
						"	quantity,\n" + 
						"   date_created,\n"+
						"	date_added,\n" + 
						"	added_by\n" + 
						")\n" + 
						"values\n" + 
						"(\n" + 
						"	'"+co_id+"',\n" + 					//co_id
						"	'"+div_id+"',\n" +      			//div_id
						"	'"+dept_id+"',\n" + 				//dept_id
						"	'"+requester_id+"',\n" + 			//requester_id
						"	'"+req_id+"',\n" + 					//request_id
						"	'"+description+"',\n" + 			//description
						"	'"+model+"',\n" +   				//model
						"	'"+brand+"',\n" +  					//brand
						"	'"+supplier_id+"',\n" +  			//supplier
						"	'"+item_id+"',\n" +   				//item_id
						"	'"+remarks+"', \n"+					//remarks
						"	'A',\n" +   						//status_id
						"	'"+unit+"',\n" +   					//unit
						"	'"+qty+"',\n" +  					//quantity
						"	'"+req_date+"',\n" +  				//date_created
						"	now(),\n" +  						//date_added
						"	'"+UserInfo.EmployeeCode+"'\n" +    //added_by
						")";
				
				db.executeUpdate(strsql, false);
				System.out.printf("SQL #1: %s" , strsql);
				
				
			}
			x++;
		}
		db.commit();
		
	}
	
	
	public static void display_items(DefaultTableModel model, JList rowHeader) {
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		
		String strsql="select false,product_name, model,  brand, '' as quantity, item_unit as unit, supplier_id, item_id from rf_supplier_products where category_id='6' order by product_name";
		
		FncSystem.out("display_PO", strsql);	
		 
		pgSelect db = new pgSelect();
		db.select(strsql);

		if(db.isNotNull()){
			
			for(int x=0; x < db.getRowCount(); x++)
			{
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				model.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static void display_request(String req_id) {
		
		FncTables.clearTable(modelreq_stationary_supp);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheader_req_stationary_supp.setModel(listModel);//Setting of listModel into rowHeader.
		
		String strsql="select \n" + 
				"true,\n" + 
				"description,\n" + 
				"model,\n" + 
				"brand,\n" + 
				"quantity,\n" + 
				"unit,\n" + 
				"supplier,\n" + 
				"item_id, \n" + 
				"remarks \n"+
				"from rf_stationary_supplies_request\n" + 
				"where request_id='"+req_id+"'";
		
		System.out.println( "display_request: \n"+ strsql );
		pgSelect db = new pgSelect();
		db.select(strsql);
		if(db.isNotNull()){
			
			
			for(int x=0; x < db.getRowCount(); x++)
			{
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelreq_stationary_supp.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelreq_stationary_supp.getRowCount());
			}
		}
	}
	
	public static String set_request_id() {
		pgSelect db = new pgSelect();
		String strsql="select  trim(to_char(coalesce(max(request_id)::int,0) +1,'000000'))  from  rf_stationary_supplies_request";
		db.select(strsql);
		return db.Result[0][0].toString();
	}
	
	public static String getrequester() {
		
		String strsql="select \n" + 
				"b.entity_name as employee_name, \n" + 
				" a.emp_code as employee_Code, \n" + 
				"e.co_id as company_id,\n" + 
				"trim(e.company_name) as \"Company Name\",\n" + 
				"d.division_code as division_id,\n" + 
				"d.division_name as division_name,\n" + 
				"d.division_alias Division_alias, \n" + 
				"c.dept_code as department_id,\n" + 
				"c.dept_name as department_name,\n" + 
				"c.dept_alias as department_alias, \n" + 
				"d.division_alias || ' - ' || c.dept_alias as div_dept\n" + 
				"from  em_employee a\n" + 
				"left join rf_entity b ON a.entity_id=b.entity_id\n" + 
				"left join mf_department as c on a.dept_code=c.dept_code\n" + 
				"left join mf_division as d on c.division_code=d.division_code \n" + 
				"left join mf_company e on a.co_id=e.co_id\n" + 
				"where a.emp_status in ('R')";
		
		return strsql;
	}
	
	public static String getrequest_id() {
		
		String strsql="select distinct on (request_id)  \n" + 
				"a.request_id as REQUEST_ID,\n" + 
				"a.requester_id,\n" + 
				"get_employee_name(a.requester_id) as employee_name,\n" + 
				"a.date_created, \n" + 
				"c.dept_code,\n" + 
				"c.dept_name \n" + 
				"from rf_stationary_supplies_request a\n" + 
				"left join em_employee b on a.requester_id=b.emp_code\n" + 
				"left join  mf_department c on b.dept_code=c.dept_code\n" + 
				"where a.status_id='A'";
		
		return strsql;
	}
	
	public  void preview_request_stationary_supplies (String req_no, String co_name, String dept, Date req_date, String requester, String prepared_by ) {
		
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("req_no", req_no);
		mapParameters.put("co_name", co_name);
		mapParameters.put("requester",requester );
		mapParameters.put("dept",dept );
		mapParameters.put("req_date", req_date);
		mapParameters.put("prepared_by", prepared_by);
		
		try{
			mapParameters.put("co_logo_vdc", this.getClass().getClassLoader().getResourceAsStream("Images/verdant_logo.bmp"));
			mapParameters.put("co_logo_cdc", this.getClass().getClassLoader().getResourceAsStream("Images/cenqlogo.png"));
			mapParameters.put("co_logo_adc", this.getClass().getClassLoader().getResourceAsStream("Images/acer_logo.bmp"));
		}catch(NullPointerException e){}
		
		FncReport.generateReport("/Reports/rpt_request_stationary_supplies.jasper", "Request Stationary and Supplies", mapParameters);
	}

}
