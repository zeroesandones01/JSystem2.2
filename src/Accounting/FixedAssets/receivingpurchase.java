package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._JScrollPaneTotal;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelpurchase_receiving;

public class receivingpurchase extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelreceivingpurchase;
	private JPanel pnlcenter;
	private JPanel pnlsouth;
	
	public static JCheckBox chkpettycash;
	
	private static JTextField txtstatus;
	public static JTextField txtdrf_no;
	public static JTextField txtremarks;
	private JTextField txtlocation;
	private JTextField txtsupplier;
	private JTextField txtterms;
	private static JTextField txtrecvd_by_id;
	private static JTextField txtrecvd_by_name;
	private JTextField txttype;
	
	private JSplitPane jsplitpanelreceivingpurchase;
	private JScrollPane scrollreceiving;
	public static modelpurchase_receiving modelreceiving;
	public static _JTableMain Tblreceiving;
	public static JList rowheaderreceiving;

	private _JScrollPaneTotal scroll_accounttotal;
	private Object scrollreceiving_total;
	
	public static JButton btnAddAcct;
	private JButton btnRemoveAcct;

	
	protected String emp_name;
	protected String emp_code;
	public static String po_no;
	
	private _JLookup lookupsupp_id;
	private static _JLookup lookuppo_no;
	public static _JDateChooser received_date;
	public static _JLookup lookupDR_id;
	public static _JLookup lookuprecvdby_id;
	
	//public static String co_id = procurement.lookupcompany.getValue();
	static String rplf_no 		= ""; 
	
	public receivingpurchase() {
		initGUI();
	}

	public receivingpurchase(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public receivingpurchase(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public receivingpurchase(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 3, 3));
		{
			
			  panelreceivingpurchase= new JPanel(new BorderLayout(5, 5));
			  this.add(panelreceivingpurchase, BorderLayout.CENTER);
			  {
				  jsplitpanelreceivingpurchase = new JSplitPane();
				  panelreceivingpurchase.add(jsplitpanelreceivingpurchase, BorderLayout.CENTER);
				  jsplitpanelreceivingpurchase.setOneTouchExpandable(true);
				  jsplitpanelreceivingpurchase.setOrientation(JSplitPane.VERTICAL_SPLIT);
				  jsplitpanelreceivingpurchase.setResizeWeight(.20d);
				  {
					 JPanel jsplitpanelreceivingpurchase_north = new JPanel(new BorderLayout(5, 5));
					 jsplitpanelreceivingpurchase.add(jsplitpanelreceivingpurchase_north, JSplitPane.TOP);
					 jsplitpanelreceivingpurchase_north.setPreferredSize(new Dimension(0, 100));
					 {
							JPanel pnlnorth = new JPanel(new BorderLayout(5, 5));
							jsplitpanelreceivingpurchase_north.add(pnlnorth, BorderLayout.NORTH);
							pnlnorth.setPreferredSize(new Dimension(0,120));
							//pnlnorth.setBackground(Color.blue);
							{
								JPanel pnlnorth_west = new JPanel(new BorderLayout(5,5));
								pnlnorth.add(pnlnorth_west, BorderLayout.CENTER);
								//pnlnorth_west.setBackground(Color.cyan);
								{
									//JPanel pnldetails_west = new JPanel(new GridLayout(6, 1, 5, 5));
									JPanel pnldetails_west = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlnorth_west.add(pnldetails_west,BorderLayout.WEST);
									pnldetails_west.setPreferredSize(new Dimension(80, 0));
									{
										JLabel lblsupplier = new JLabel("PO NO.");
										pnldetails_west.add(lblsupplier);
									}
									{
										JLabel lblrcvd_by = new JLabel("Received by");
										pnldetails_west .add(lblrcvd_by);
									}
									{
										JLabel lblstatus = new JLabel("Status");
										pnldetails_west.add(lblstatus);
									}
									{
										JLabel lblremarks = new JLabel("Remarks");
										pnldetails_west .add(lblremarks);
									}
								}
								{
									//JPanel pnldetails_center = new JPanel(new GridLayout(6, 1, 5, 5));
									JPanel pnldetails_center = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlnorth_west.add(pnldetails_center, BorderLayout.CENTER);
									{
										JPanel pnlsupp_id = new JPanel(new BorderLayout(5, 5));
										pnldetails_center.add(pnlsupp_id);
										{
											lookuppo_no = new _JLookup();
											lookuppo_no.setLookupSQL(getpo_no());
											lookuppo_no.setReturnColumn(0);
											pnlsupp_id.add(lookuppo_no, BorderLayout.WEST);
											lookuppo_no.setPreferredSize(new Dimension(80, 0));
											lookuppo_no.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object [] data = ((_JLookup)event.getSource()).getDataSet();
													
													if(data !=null) {
														po_no = (String) data[0];
														
														display_PO(modelreceiving, rowheaderreceiving);
														displayreceiving_details();
														btnAddAcct.setEnabled(false);
														modelreceiving.setEditable(false);
														
														//procurement.enable_buttons(true, false, false, false, true, true);
													}
												}
											});
										}
									}
									{
										JPanel pnlrecvd_by = new JPanel(new BorderLayout(5, 5));
										pnldetails_center.add(pnlrecvd_by);
										{
											lookuprecvdby_id = new _JLookup();
											lookuprecvdby_id.setEditable(false);
											lookuprecvdby_id.setLookupSQL(getreceivedby());
											pnlrecvd_by.add(lookuprecvdby_id, BorderLayout.WEST);
											lookuprecvdby_id.setPreferredSize(new Dimension(80, 0));
											lookuprecvdby_id.addLookupListener(new LookupListener() {

												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													
													if(data != null) {
														
														emp_name = (String)data[0];
														emp_code = (String) data[1];
														
														lookuprecvdby_id.setValue(emp_code);
														txtrecvd_by_name.setText(emp_name);
													}
												}
											});
										}
										{
											txtrecvd_by_name = new JTextField();
											txtrecvd_by_name.setEditable(false);
											pnlrecvd_by.add(txtrecvd_by_name, BorderLayout.CENTER);
										}
									}
									{
										JPanel pnlstatus = new JPanel(new BorderLayout(5, 5));
										pnldetails_center.add(pnlstatus);
										{
											txtstatus = new JTextField();
											txtstatus.setEditable(false);
											pnlstatus.add(txtstatus, BorderLayout.WEST);
											txtstatus.setPreferredSize(new Dimension(80, 0));
										}
									}
									{
										txtremarks = new JTextField();
										txtremarks.setEditable(false);
										pnldetails_center.add(txtremarks);
									}
								}
								{
									JPanel pnldetails_east = new JPanel(new BorderLayout(5, 5));
									pnlnorth_west.add(pnldetails_east, BorderLayout.EAST);
									pnldetails_east.setPreferredSize(new Dimension(50, 0));
								}
							}
							{
								JPanel pnlnorth_east = new JPanel(new BorderLayout(5, 5));
								pnlnorth.add(pnlnorth_east, BorderLayout.EAST);
								pnlnorth_east.setPreferredSize(new Dimension(200, 0));
								//pnlnorth_east.setBackground(Color.DARK_GRAY);
								{
									JPanel pnlLabel = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlnorth_east.add(pnlLabel, BorderLayout.WEST);
									pnlLabel.setPreferredSize(new Dimension(60, 0));
									{
										JLabel lbldr_id = new JLabel("DR ID");
										pnlLabel.add(lbldr_id);
									}

									{
										JLabel lbldate = new JLabel("Date");
										pnlLabel.add(lbldate);
									}
									{
										JLabel lbldrf_no = new JLabel("DRF No.:");
										pnlLabel.add(lbldrf_no);
									}
									{
										chkpettycash = new JCheckBox();
										chkpettycash.setEnabled(false);
										pnlLabel.add(chkpettycash);
										chkpettycash.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												
											}
										});
									}

								}
								{
									JPanel pnlfields = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlnorth_east.add(pnlfields, BorderLayout.CENTER);
									{
										lookupDR_id = new _JLookup();
										lookupDR_id.setEditable(false);
										pnlfields.add(lookupDR_id);
									}
									{
										 received_date = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
										 received_date.setEditable(false);
										 pnlfields.add(received_date, BorderLayout.EAST);
										 received_date.setPreferredSize(new Dimension(110, 0));
									 }
									{
										txtdrf_no = new JTextField();
										txtdrf_no.setEditable(false);
										pnlfields.add(txtdrf_no);
									}
									{
										JLabel lblpettycash = new JLabel("Petty Cash");
										pnlfields.add(lblpettycash);
									}
								}
							}
						}
				  }
				  {
					  JPanel jsplitpanelreceivingpurchase_south = new JPanel(new BorderLayout(5, 5));
					  jsplitpanelreceivingpurchase.add(jsplitpanelreceivingpurchase_south, JSplitPane.BOTTOM);
					  
					  {
						  pnlcenter = new JPanel(new BorderLayout(5,5));
						  jsplitpanelreceivingpurchase_south.add(pnlcenter, BorderLayout.CENTER);
						  {
							  scrollreceiving = new JScrollPane();
							  pnlcenter.add(scrollreceiving);
							  scrollreceiving.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
							  {
								  modelreceiving = new modelpurchase_receiving();
								  Tblreceiving = new _JTableMain(modelreceiving);
								  scrollreceiving.setViewportView(Tblreceiving);
								  Tblreceiving.setHorizontalScrollEnabled(true);
								  Tblreceiving.hideColumns("RPLF");
								  //modelreceiving.setEditable(false);
								 // Tblreceiving.setEnabled(false);
								  //Tblreceiving.packAll();
								  
								  Tblreceiving.getColumnModel().getColumn(1).setPreferredWidth(100);
								  Tblreceiving.getColumnModel().getColumn(2).setPreferredWidth(70);
								  Tblreceiving.getColumnModel().getColumn(3).setPreferredWidth(70);
								  Tblreceiving.getColumnModel().getColumn(4).setPreferredWidth(250);
								  Tblreceiving.getColumnModel().getColumn(5).setPreferredWidth(70);
								  Tblreceiving.getColumnModel().getColumn(6).setPreferredWidth(70);
								  Tblreceiving.getColumnModel().getColumn(7).setPreferredWidth(70);
								  Tblreceiving.getColumnModel().getColumn(8).setPreferredWidth(70);
								  
								  Tblreceiving.addMouseListener(new MouseAdapter() {
									  public void mousePressed(MouseEvent e) {
										  if(e.getClickCount() >= 2) {
											  int column 	= Tblreceiving.getSelectedColumn();
											  if(column == 1) {
												  selectitems_toreceived();
											  }
										  }
										 
									  }
									  public void mouseReleased(MouseEvent e) {
										  if(e.getClickCount() >= 2) {
											  int column 	= Tblreceiving.getSelectedColumn();
											  if(column == 1) {
												  selectitems_toreceived();
											  }
										  }
										  
									  }
								});
							  }
							  {
								  rowheaderreceiving = Tblreceiving.getRowHeader();
								  scrollreceiving.setRowHeaderView(rowheaderreceiving);
								  scrollreceiving.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							  }
							  {
								  scrollreceiving.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());

							  }
							
						  }
					  }
				  }
			  }
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Add Acct")) {
				
				receivingpurchase.add_row();
				System.out.println("Dumaan dito sa Add Acct");
			
		}

	}
	
	private JPanel displayTableNavigation() {
		
		
		  btnAddAcct = new JButton(new
		  ImageIcon(this.getClass().getClassLoader().getResource(
		  "Images/Science-Plus2-Math-icon.png")));
		  btnAddAcct.setActionCommand("Add Acct");
		  btnAddAcct.setToolTipText("For multiple entry");
		  btnAddAcct.setEnabled(false); 
		  btnAddAcct.addActionListener(this);
		  
		  btnRemoveAcct = new JButton(new
		  ImageIcon(this.getClass().getClassLoader().getResource(
		  "Images/Science-Minus2-Math-icon.png")));
		  btnRemoveAcct.setActionCommand("Minus Acct");
		  btnRemoveAcct.setToolTipText("For multiple entry");
		  btnRemoveAcct.setEnabled(false); 
		  btnRemoveAcct.addActionListener(this);
		  
		  
		  JPanel pnl = new JPanel(new GridLayout(1, 2)); pnl.add(btnAddAcct);
		  pnl.add(btnRemoveAcct);
		 

		return pnl;
}
	
	public static Boolean check_alldetails() {
		
		if(lookuprecvdby_id.getValue() == null
				|| txtrecvd_by_name.getText().equals("")
				|| received_date.getDate()== null) {
			
			return false;
		}else {
			return true;
		}
	}
	
	
	public static void insert_rplf_details_header() {
		
		pgSelect db = new pgSelect();
		String strsql="select sp_create_rplf_purchase_order2()";
		//String strsql="select sp_create_rplf_purchase_order3('"+procurement.lookupcompany.getValue()+"' , '"+po_no+"' , '"+UserInfo.EmployeeCode+"', '"+txtremarks.getText()+"', '"+lookuprecvdby_id.getValue()+"', '"+received_date.getDate()+"')";
		
		System.out.printf("insert_rplf_details_header= ", strsql);
		db.select(strsql);	
	}
	
	
	
	public static void insert_receiving() {
		
		int x  = 0;	
		int rw = Tblreceiving.getModel().getRowCount();
		String co_id = procurement.lookupcompany.getValue();
		String requester = procurement.lookuprequester.getValue();
		String remarks = txtremarks.getText();
		String rcvd_by = lookuprecvdby_id.getValue();
		String dr_id= setreceiving_dr_id();
		
		pgSelect db = new pgSelect();
		
		while(x<rw) {
			
			Boolean selected = (Boolean) modelreceiving.getValueAt(x, 0);
			String supp_id = (String) modelreceiving.getValueAt(x, 1);
			String po_no = (String) modelreceiving.getValueAt(x, 2);
			String item_id = (String) modelreceiving.getValueAt(x, 3);
			String description = (String) modelreceiving.getValueAt(x, 4);
			String unit = (String) modelreceiving.getValueAt(x, 5);
			String quantity = (String) modelreceiving.getValueAt(x, 6);
			BigDecimal price = (BigDecimal) modelreceiving.getValueAt(x, 7);
			
		if(selected) {	
		
		String strsql="select sp_insert_receiving_purchase  \n"+
				"(\n"+
						"'"+co_id+"',\n"+
						"'"+po_no+"', \n"+
						"'"+UserInfo.EmployeeCode+"',\n"+
						"'"+txtremarks.getText()+"', \n"+
						"'"+supp_id+"',\n"+
						"'"+item_id+"',\n"+
						"'"+description+"',\n"+
						"'"+unit+"',\n"+
						"'"+quantity+"',\n"+
						"'"+price+"',\n"+
						"'"+dr_id+"', \n"+
						"'"+rcvd_by+"',\n"+
						"'"+received_date.getDate()+"'\n"+
						//"'"++"',\n"+
						
				")";
			db.select(strsql);
			System.out.printf("insert_receiving: %s",strsql);
			System.out.println("supp_id= "+ supp_id);
			System.out.println("po_no= "+ po_no);
			System.out.println("price= "+ price);
			//System.out.println("rplf= "+ rplf_no);
			//System.out.println("co_id= "+ co_id);
		}
			x++;
		}
		
		
	}
	
	public static String getreceivedby(){
		
		String strsql="select b.entity_name as employee_name, \n" + 
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
				"left join mf_company e on a.co_id=e.co_id ";
				//"AND not emp_status='I' \n";//lookup active emp only
		
		return strsql;
		
		}
	
	
	
	public static String setreceiving_dr_id() {
		pgSelect db = new pgSelect();
		String strsql="select  trim(to_char(coalesce(max(dr_id)::int,0) +1,'000000'))  from  rf_receiving_purchase";
		db.select(strsql);
		return db.Result[0][0].toString();
	}
	
	public static void displayreceiving_details() {
		
		String strsql="select distinct on (a.po_no)\n" + 
				"a.co_id,\n" + 
				"c.company_name,\n" + 
				"a.requester,\n" + 
				"e.entity_name,\n" + 
				"g.division_alias || '-' || h.dept_alias as div_dept,\n" + 
				"b.received_by,\n" + 
				"get_employee_name(b.received_by) as name_received,\n"+
				"b.status_id,\n" + 
				"b.remarks,\n" + 
				//"b.dr_id,\n" + 
				"b.date_received, \n" + 
				//"b.drf_no, \n"+
				"(case when b.dr_id is null then false else b.petty_cash end) as petty_cash\n" + 
				"from rf_purchase_order a \n" + 
				"left join rf_receiving_purchase b on a.po_no=b.po_no\n" + 
				"left join mf_company c on a.co_id=c.co_id\n" + 
				"left join em_employee d on a.requester=d.emp_code\n" + 
				"left join rf_entity e on d.entity_id=e.entity_id\n" + 
				"left join rf_purchase_request_details f on a.pr_no=f.pr_no\n" + 
				"left join mf_division g on f.div_id=g.division_code\n" + 
				"left join mf_department h on f.dept_id=h.dept_code\n" + 
				"where a.po_no='"+lookuppo_no.getValue()+"' ";
		
		FncSystem.out("displayreceiving_details", strsql);
		pgSelect db = new pgSelect();
		db.select(strsql);
		if(db.isNotNull()) {
			procurement.lookupcompany.setValue((String) db.getResult()[0][0]);    //co_id
			procurement.txtcompany.setText((String) db.getResult()[0][1]);        //company_name
			procurement.lookuprequester.setValue((String) db.getResult()[0][2]);  //requester
			procurement.txtrequester_name.setText((String) db.getResult()[0][3]); //entity_name
			procurement.txtdivdept.setText((String) db.getResult()[0][4]);		  //Div/Dept
			lookuprecvdby_id.setValue((String) db.getResult()[0][5]);			  //received_by
			txtrecvd_by_name.setText((String) db.getResult()[0][6]);		      //name_received
			txtstatus.setText((String) db.getResult()[0][7]);					  //status_id
			txtremarks.setText((String) db.getResult()[0][8]); 					  //remarks
			//lookupDR_id.setValue((String) db.getResult()[0][9]);
			received_date.setDate((Date) db.getResult()[0][9]);					  //date_received
			//txtdrf_no.setText((String) db.getResult()[0][11]);
			//if((boolean) db.getResult()[0][12].equals(null)) {chkpettycash.setSelected(false);}else {chkpettycash.setSelected((boolean) db.getResult()[0][12]);}
			chkpettycash.setSelected((boolean) db.getResult()[0][10]); 			  //Petty cash
			
			//System.out.println("pettycash= "+ db.getResult()[0][12]);
			
		}
	}
	
	public static void selectitems_toreceived() {
		
		int column = Tblreceiving.getSelectedColumn();
		int row = Tblreceiving.getSelectedRow();
		
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Items", selecttoreceive(), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);
		
		Object[] data = dlg.getReturnDataSet();
		if (data != null) {
			modelreceiving.setValueAt(data [0], row, 1); //supplier
			modelreceiving.setValueAt(data [2], row, 2); //po_no
			modelreceiving.setValueAt(data [3], row, 3); //item_id
			modelreceiving.setValueAt(data [4], row, 4); //description
			modelreceiving.setValueAt(data [5], row, 5); //unit
			modelreceiving.setValueAt(data [6], row, 6); //quantity
			modelreceiving.setValueAt(data [7], row, 7); //unit_price
			
			System.out.println("data [0]"+data [0]);
			System.out.println("data [2]"+data [2]);
			System.out.println("data [3]"+data [3]);
			System.out.println("data [4]"+data [4]);
			System.out.println("data [5]"+data [5]);
			System.out.println("data [6]"+data [6]);
			System.out.println("data [8]"+data [8]);
			System.out.println("column"+column);
			System.out.println("row"+row);
		}	
		Tblreceiving.packAll();
	}
	
	public static String selecttoreceive() {
		
		String strsql="select  \n" + 
				"a.supplier, \n" + 
				"b.entity_name,\n" + 
				"a.po_no, \n" + 
				"a.item_id, \n" + 
				"a.description, \n" + 
				"a.unit, \n" + 
				//"a.quantity, \n" + 
				"(case when c.item_received = true then (a.quantity::int - c.quantity::int)::varchar else a.quantity  end) as quantity,\n"+
				"a.unit_price, \n" +
				"c.quantity as quantity_delivered, \n" + 
				"a.quantity, \n"+
				"c.quantity as quantity_delivered \n"+
				"from rf_purchase_order  a\n" + 
				"left join rf_entity b on a.supplier=b.entity_id\n" + 
				"left join rf_receiving_purchase c on a.po_no=c.po_no and a.item_id=c.item_id\n" + 
				"where a.po_no = '"+lookuppo_no.getValue()+"' \n" + 
				"and a.supplier not in (select supplier_id from rf_receiving_purchase where po_no='"+lookuppo_no.getValue()+"' and item_received ='t' and a.quantity =c.quantity )\n" + 
				"";
		
		return strsql;
	}
	
	public static void display_PO(DefaultTableModel model, JList rowHeader){
		
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		
		//Individual display of assets
		String strSQL = "select (case when item_received is null then false else item_received end ) as item_received, a.supplier, a.po_no, a.item_id, a.description, a.unit,a.quantity, a.unit_price, b.drf_no, b.dr_id \n" + 
				"from rf_purchase_order  a\n" + 
				"left join rf_receiving_purchase b on a.po_no=b.po_no and a.item_id=b.item_id\n"+
				"where a.po_no='"+lookuppo_no.getValue()+"'\n" + 
				" ";
		
		FncSystem.out("display_PO", strSQL);	
		 
		pgSelect db = new pgSelect();
		db.select(strSQL);

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
	
	public static String getpo_no() {
		String strsql="select distinct on (po_no) po_no,po_date, canvass_id, pr_no from rf_purchase_order  ";
		return strsql;
	}
	
	public static void cleartab_receiving() {
		procurement.lookupcompany.setValue("");
		procurement.txtcompany.setText("");
		procurement.lookuprequester.setValue("");
		procurement.txtrequester_name.setText("");
		procurement.txtdivdept.setText("");
		
		lookuppo_no.setValue("");
		lookuprecvdby_id.setValue("");
		txtrecvd_by_name.setText("");
		txtstatus.setText("");
		txtremarks.setText("");
		
		lookupDR_id.setValue("");
		received_date.setDate(null);
		txtdrf_no.setText("");
		chkpettycash.setSelected(false);
		FncTables.clearTable(modelreceiving);
	}
	
	public static void cleartable_rowheader() {
		 
		FncTables.clearTable(modelreceiving);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderreceiving.setModel(listModel);//Setting of listModel into rowHeader.
	}
	public static void add_row() {
		
		modelreceiving.addRow(new Object [] {true,null,null,null,null,null,null,null,null,});
		((DefaultListModel) receivingpurchase.rowheaderreceiving.getModel()).addElement(modelreceiving.getRowCount());
	}

}
