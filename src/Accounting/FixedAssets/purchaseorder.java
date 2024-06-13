package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import components._JTagLabel;
import interfaces._GUI;
import tablemodel.model_Post_Office_Utility;
import tablemodel.modelpurchase_order;

public class purchaseorder extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelpurchaseorder;
	public static _JLookup lookcanvassid;
	public static _JLookup lookupPO_No;
	public static _JLookup lookuptype;
	public static _JLookup lookupterms;
	
	public static _JDateChooser date_PO;
	private static JTextField txtsupp_id;
	private _JTagLabel txtsupp_name;
	private _JTagLabel tagaddress;
	private static JTextField txtPR_No;
	private static JTextField txtterm_id;
	private static JTextField txtstatus;
	private static JTextField txttype;
	
	private _JTagLabel tagterm_name;
	private _JTagLabel tagremarks;
	
	private JPanel pnlPO_center;
	private JScrollPane scrollPO;
	public static modelpurchase_order modelPO;
	private static _JTableMain tblPO;
	public static JList rowheaderPO;

	private DefaultComboBoxModel jComboBox1Model;
	private JComboBox cmbterms;
	
	private static String canvass_id;
	public static String pr_no;
	public static String po_no;
	protected String po_date;
	protected String terms;
	public static _JTagLabel tagtype;
	protected String class_name;
	public static String print_po;
	private static String class_type;


	
	
	public purchaseorder() {
		initGUI();
	}

	public purchaseorder(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public purchaseorder(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public purchaseorder(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 3, 3));
		{
			panelpurchaseorder= new JPanel(new BorderLayout(5, 5));
			this.add(panelpurchaseorder, BorderLayout.CENTER);
			
			{
				 JPanel pnlPO_north = new JPanel(new GridLayout(3, 1, 5, 5));
				 panelpurchaseorder.add(pnlPO_north, BorderLayout.NORTH);
				 pnlPO_north.setPreferredSize(new Dimension(0,90));
				 {
					 JPanel pnlcanvassid= new JPanel(new BorderLayout(5, 5));
					 pnlPO_north.add(pnlcanvassid);
					 {
						 JLabel lblcanvassid = new JLabel("Canvass ID");
						 pnlcanvassid.add(lblcanvassid, BorderLayout.WEST);
						 lblcanvassid.setPreferredSize(new Dimension(70, 0));
					 }
					 {
						 lookcanvassid = new _JLookup();
						 lookcanvassid.setLookupSQL(getcanvass_id());
						 lookcanvassid.setReturnColumn(0);
						 pnlcanvassid.add(lookcanvassid, BorderLayout.CENTER);
						 lookcanvassid.addLookupListener(new LookupListener() {
							

							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								
								if(data !=null) {
									 canvass_id = (String) data[0];
									 pr_no = (String)data[1];
									 //po_no = (String)data[2];
									 //po_date = (String)data[3];
									 display_PO(modelPO, rowheaderPO);
									 displayPO_details();
									 
									 procurement.enable_buttons(true, false, false, false, true, true);
									// txtPR_No.setText(pr_no);
									// lookupPO_No.setValue(po_no);
									//System.out.println("po_date"+po_date);
								}
								
							}
						});
					 }
					
					 { JPanel pnlcanvassid_east = new JPanel(new BorderLayout(5, 5));
					  pnlcanvassid.add(pnlcanvassid_east, BorderLayout.EAST);
					  pnlcanvassid_east.setPreferredSize(new Dimension(500, 0)); 
						  {
							  	
							  JPanel pnlcanvassid_east_west = new JPanel(new BorderLayout(5, 5));
							  pnlcanvassid_east.add(pnlcanvassid_east_west, BorderLayout.WEST);
							  pnlcanvassid_east_west.setPreferredSize(new Dimension(330, 0)); 
						  } 
						  { 
							  JLabel lblPO_No = new JLabel("PR No."); 
							  pnlcanvassid_east.add(lblPO_No,BorderLayout.CENTER); 
						  } 
						  {
								 txtPR_No = new JTextField();
								 txtPR_No.setEnabled(false);
								 pnlcanvassid_east.add(txtPR_No, BorderLayout.EAST);
								 txtPR_No.setPreferredSize(new Dimension(110, 0)); 
						  }
					  }
				 }
				  {
					 JPanel pnlsupplier = new JPanel(new BorderLayout(5, 5));
					 pnlPO_north.add(pnlsupplier);
					 //Original Code
					/* {
						 JLabel lblsupplier = new JLabel("Supplier");
						 pnlsupplier.add(lblsupplier, BorderLayout.WEST);
						 lblsupplier.setPreferredSize(new Dimension(70, 0));
					 }
					 {
						 txtsupp_id = new JTextField();
						 txtsupp_id.setEnabled(false);
						 pnlsupplier.add(txtsupp_id, BorderLayout.CENTER);
					 }*/
					 {
						 JLabel lblPR_No = new JLabel("Terms   ", JLabel.LEADING);
						 pnlsupplier.add(lblPR_No, BorderLayout.WEST);
						 lblPR_No.setPreferredSize(new Dimension(70, 0));
					 }
					 {
						 lookupterms = new _JLookup();
						 lookupterms.setReturnColumn(0);
						 lookupterms.setLookupSQL(get_terms());
						 lookupterms.setEditable(false);
						 pnlsupplier.add(lookupterms, BorderLayout.CENTER);
						 lookupterms.addLookupListener(new LookupListener() {

							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								
								if(data != null) {
									terms = (String)data[0];
								}
							}
						});
					 }
					 {
						 JPanel pnlsupplier_east = new JPanel(new BorderLayout(5, 5));
						 pnlsupplier.add(pnlsupplier_east, BorderLayout.EAST);
						 pnlsupplier_east.setPreferredSize(new Dimension(500, 0));
						 /*{
							 txtsupp_name = new _JTagLabel("[ ]");
							 pnlsupplier_east.add(txtsupp_name, BorderLayout.WEST);
							 txtsupp_name.setPreferredSize(new Dimension(250, 0));
						 }*/
						 {
							 JLabel lbldate = new JLabel("Date   ", JLabel.TRAILING);
							 pnlsupplier_east.add(lbldate, BorderLayout.CENTER);
						 }
						 {
							 date_PO = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							 pnlsupplier_east.add(date_PO, BorderLayout.EAST);
							 date_PO.setPreferredSize(new Dimension(110, 0));
						 }
					 }
				 }
				/* {
					 JPanel pnladdress = new JPanel(new BorderLayout(5, 5));
					 pnlPO_north.add(pnladdress);
					 {
						 JLabel lbladdress = new JLabel("Address");
						 pnladdress.add(lbladdress, BorderLayout.WEST);
						 lbladdress.setPreferredSize(new Dimension(70, 0));
						 {
							 JPanel pnladdress_center = new JPanel(new BorderLayout(5,5));
							 pnladdress.add(pnladdress_center, BorderLayout.CENTER);
							 {
								 tagaddress = new _JTagLabel("[ ]"); 
								 pnladdress_center.add(tagaddress, BorderLayout.WEST);
								 tagaddress.setPreferredSize(new Dimension(300, 0));
								 tagaddress.setBackground(Color.cyan);
							 }
							 {
								 JLabel lblPR_No = new JLabel("PR No   ", JLabel.TRAILING);
								 pnladdress_center.add(lblPR_No, BorderLayout.CENTER);
							 }
							 {
								 txtPR_No = new JTextField();
								 txtPR_No.setEnabled(false);
								 pnladdress_center.add(txtPR_No, BorderLayout.EAST);
								 txtPR_No.setPreferredSize(new Dimension(110, 0));
							 }
						 }
						 
					 }
				 }*/
				 {
					 JPanel pnlterms = new JPanel(new BorderLayout(5,5));
					 pnlPO_north.add(pnlterms);
					 {
						 JLabel lbltype = new JLabel("Type   ");
						 pnlterms.add(lbltype, BorderLayout.WEST);
						 lbltype.setPreferredSize(new Dimension(70, 0));
					 }
					 {
						 //txttype = new JTextField();
						 lookuptype = new _JLookup();
						 lookuptype.setEditable(false);
						 lookuptype.setLookupSQL(get_class());
						 pnlterms.add(lookuptype, BorderLayout.CENTER);
						 lookuptype.addLookupListener(new LookupListener() {

							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								
								if(data != null) {
									class_type = (String)data[1];
									class_name = (String)data[0];
									
									tagtype.setTag(class_name);
									
								}
							}
						});
					 }
					
					 {
						 JPanel pnlterms_east = new JPanel(new BorderLayout(5,5));
						 pnlterms.add(pnlterms_east, BorderLayout.EAST);
						 pnlterms_east.setPreferredSize(new Dimension(500, 0));
						 {
							 tagtype = new _JTagLabel("[]");
							 pnlterms_east.add(tagtype, BorderLayout.WEST);
							 tagtype.setPreferredSize(new Dimension(200,0));
							 
						 }
						 {
							 JLabel lblstatus= new JLabel("Status   ", JLabel.TRAILING);
							 pnlterms_east.add(lblstatus, BorderLayout.CENTER);
						 }
						 {
							 txtstatus= new JTextField();
							 txtstatus.setEditable(false);
							 pnlterms_east.add(txtstatus, BorderLayout.EAST);
							 txtstatus.setPreferredSize(new Dimension(110,0));
						 }
						 
					 }
					 
					 /*{
						 JLabel lblterms = new JLabel("Terms");
						 pnlterms.add(lblterms, BorderLayout.WEST);
						 lblterms.setPreferredSize(new Dimension(70, 0));
					 }
					{
						 txtterm_id = new JTextField();
						 txtterm_id.setEnabled(false);
						 pnlterms.add(txtterm_id, BorderLayout.CENTER);
					 }
					 {
						 JPanel pnlterms_east = new JPanel(new BorderLayout(5,5));
						 pnlterms.add(pnlterms_east, BorderLayout.EAST);
						 pnlterms_east.setPreferredSize(new Dimension(500, 0));
						 {
							 tagterm_name = new _JTagLabel("[ ]");
							 pnlterms_east.add(tagterm_name, BorderLayout.WEST);
							 tagterm_name.setPreferredSize(new Dimension(250, 0));
						 }
						 {
							 JLabel lbltype = new JLabel("Type   ", JLabel.TRAILING);
							 pnlterms_east.add(lbltype, BorderLayout.CENTER);
						 }
						 {
							 txttype = new JTextField();
							 txttype.setEnabled(false);
							 pnlterms_east.add(txttype, BorderLayout.EAST);
							 txttype.setPreferredSize(new Dimension(110,0));
						 }
					 }*/
				 }
				/* {
					 JPanel pnlremarks = new JPanel(new BorderLayout(5,5));
					 pnlPO_north.add(pnlremarks);
					 {
						 JLabel lblremarks = new JLabel("Remarks");
						 pnlremarks.add(lblremarks, BorderLayout.WEST); 
						 lblremarks.setPreferredSize(new Dimension(70, 0));
					 }
					 {
						 tagremarks = new _JTagLabel("[ ]");
						 pnlremarks.add(tagremarks, BorderLayout.CENTER);
					 }
				 }*/
			}
			{
				pnlPO_center = new JPanel(new BorderLayout(5,5));
				panelpurchaseorder.add(pnlPO_center, BorderLayout.CENTER);
				{
					scrollPO = new JScrollPane();
					pnlPO_center.add(scrollPO, BorderLayout.CENTER);
					scrollPO.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelPO = new modelpurchase_order();
						tblPO = new _JTableMain(modelPO);
						scrollPO.setViewportView(tblPO);
						tblPO.getTableHeader().setEnabled(false);
						tblPO.packAll();
						tblPO.setHorizontalScrollEnabled(true);
						tblPO.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								
								if(!e.getValueIsAdjusting()) {
									
									if(tblPO.getSelectedRows().length > 0) {
										
									try {
											int row= tblPO.getSelectedRow();
											
											print_po = (String) modelPO.getValueAt(row, 10);
											
										}catch (ArrayIndexOutOfBoundsException ex) { }
										
										if(po_no != null ) {
											 procurement.enable_buttons(false, false, false, false, true, true);
											 
										 }else {
											 procurement.enable_buttons(false, false, false, true, true, false);
										 }
									}
								}
							}
						});
						tblPO.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblPO.getColumnModel().getColumn(2).setPreferredWidth(75);
						tblPO.getColumnModel().getColumn(3).setPreferredWidth(250);
						tblPO.getColumnModel().getColumn(4).setPreferredWidth(150);
						tblPO.getColumnModel().getColumn(5).setPreferredWidth(150);
						tblPO.getColumnModel().getColumn(6).setPreferredWidth(100);
						tblPO.getColumnModel().getColumn(7).setPreferredWidth(50);
						tblPO.getColumnModel().getColumn(8).setPreferredWidth(100);
						tblPO.getColumnModel().getColumn(9).setPreferredWidth(100);
						tblPO.getColumnModel().getColumn(10).setPreferredWidth(100);
					}
					{
						rowheaderPO = tblPO.getRowHeader();
						scrollPO.setRowHeaderView(rowheaderPO);
						scrollPO.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
		}

	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public static String setpo_no() {
		pgSelect db = new pgSelect();
		String strsql="select  trim(to_char(coalesce(max(po_no)::int,0) +1,'000000'))  from  rf_purchase_order";
		db.select(strsql);
		return db.Result[0][0].toString();
	}
	
	public static String getcanvass_id() {
		String strsql="select distinct on(canvass_id)a.canvass_id, pr_id ,  b.po_date \n" + 
				"from rf_canvassing_details a\n" + 
				"left join rf_purchase_order b on a.canvass_id=b.canvass_id\n" + 
				"where a.status_id ='A'";
		return strsql;
	}
	
	private String get_class() {
		
		String strsql=	"select 'Fixed Asset' as class_type, '01' as class_id union all \n" + 
						"select 'Staionary and Supplies', '02'";
		return strsql;
	}
	
	private String get_terms() {
		
		String strsql=	"select 	'COD',			'01'   			union all	\n" + 
						"select 	'INSTALLMENT', 	'02'  			union all	\n" + 
						"select 	'CHEQUE', 		'03' 			union all 	\n" + 
						"select 	'7 DAYS', 		'04' 			union all 	\n" + 
						"select 	'15 DAYS', 		'05' 			";
		return strsql;
		
	}
	
	public static void clear_tab_PO() {
		modelPO.clear();
		lookcanvassid.setValue("");
		//lookupPO_No.setValue("");
		lookupterms.setValue("");
		lookupterms.setEditable(false);
		date_PO.setDate(null);
		lookuptype.setValue("");
		lookuptype.setEditable(false);
		tagtype.setTag("");
		txtPR_No.setText("");
		txtstatus.setText("");
		//enable fields
		lookcanvassid.setEnabled(true);
		//lookupPO_No.setEnabled(true);
		procurement.lookupcompany.setValue("");
		procurement.txtcompany.setText("");
		procurement.lookuprequester.setValue("");
		procurement.txtrequester_name.setText("");
		
	}
	
	public static void displayPO_details() {
		
		String strsql="select distinct on (a.pr_no)a.co_id,d.company_name, a.requester_id,f.entity_name, a.pr_no, b.canvass_id, c.po_no, c.po_date, c.classification_type as type, \n" + 
				"g.division_alias || '-' || h.dept_alias as div_dept,\n" + 
				"(case when c.status_id ='A' then 'ACTIVE' \n" + 
				"	when c.status_id ='I' then 'INACTIVE' \n" + 
				"	else c.status_id end) as status_id, \n" + 
				"c.terms \n"+
				"from rf_purchase_request_details a\n" + 
				"left join rf_canvassing_details b on a.pr_no=b.pr_id and a.item_id=b.item_id\n" + 
				"left join rf_purchase_order c on b.pr_id=c.pr_no and c.item_id=b.item_id\n" + 
				"left join mf_company d on a.co_id=d.co_id\n" + 
				"left join em_employee e on a.requester_id=e.emp_code\n" + 
				"left join rf_entity f on f.entity_id=e.entity_id\n" + 
				"left join mf_division g on a.div_id=g.division_code\n" + 
				"left join mf_department h on a.dept_id=h.dept_code\n" + 
				"where b.canvass_id='"+canvass_id+"'";
				
		
		FncSystem.out("displayPO_details", strsql);
		
		pgSelect db = new pgSelect();
		db.select(strsql);
		
		if(db.isNotNull()) {
			procurement.lookupcompany.setValue((String) db.getResult()[0][0]);
			procurement.txtcompany.setText((String) db.getResult()[0][1]);
			procurement.lookuprequester.setValue((String) db.getResult()[0][2]);
			procurement.txtrequester_name.setText((String) db.getResult()[0][3]);
			txtPR_No.setText((String) db.getResult()[0] [4]);
			//lookupPO_No.setValue((String) db.getResult()[0][6]);
			date_PO.setDate((Date) db.getResult()[0][7]);
			lookuptype .setValue((String) db.getResult()[0][8]);
			procurement.txtdivdept.setText((String) db.getResult()[0][9]);
			txtstatus.setText((String) db.getResult()[0][10]);
			lookupterms.setValue((String) db.getResult()[0][11]);
		}
	}
	
	
	public static void display_PO(DefaultTableModel model, JList rowHeader){
		
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		
		//Individual display of assets
		String strSQL = "select true, a.supplier_id, a.item_id, a.product_name , c.model, c.brand, c.item_unit, b.quantity, b.purpose, (case when a.nego_price is null then a.original_price else a.nego_price end) as price, \n" + 
				" d.po_no \n"+
				"from rf_canvassing_details  a\n" + 
				"left join rf_purchase_request_details b on a.item_id=b.item_id and a.pr_id=b.pr_no\n" + 
				"left join rf_supplier_products c on a.item_id=c.item_id and a.supplier_id=c.supplier_id and a.product_id = c.product_id\n" + 
				"left join rf_purchase_order d on a.canvass_id= d.canvass_id and d.supplier=a.supplier_id and a.item_id=d.item_id\n"+
				"where a.canvass_id='"+canvass_id+"' ";
		
		FncSystem.out("display_PO", strSQL);	
		 
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			po_no =(String) db.getResult()[0][10];
			
			for(int x=0; x < db.getRowCount(); x++)
			{
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				model.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(model.getRowCount());
			}
		}
	}
	public static void cleartable_rowheader() {
		 
			FncTables.clearTable(modelPO);//Code to clear model.
			DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
			rowheaderPO.setModel(listModel);//Setting of listModel into rowHeader.
	 }
	
	public static void create_po() {
		pgSelect db = new pgSelect();
		
		String strsql="select sp_create_po_no('"+canvass_id+"')";
		
		System.out.printf("create_po= ", strsql);
		db.select(strsql);	
	}

	public static void insert_PO() {
		
	int x  = 0;	
	int rw = tblPO.getModel().getRowCount();
	String pr_no = txtPR_No.getText();
	String canvass_id = lookcanvassid.getValue();
	String terms = lookupterms.getValue();
	//String po_no = lookupPO_No.getValue();
	String class_type = lookuptype.getValue();
	pgUpdate db = new pgUpdate();
	
	while(x<rw) {
		
		Boolean selected = (Boolean) modelPO.getValueAt(x, 0);
		String supp_id = (String) modelPO.getValueAt(x, 1);
		String item_id = (String)modelPO.getValueAt(x, 2);
		String description = (String)modelPO.getValueAt(x, 3);
		String model = (String)modelPO.getValueAt(x, 4);
		String brand = (String)modelPO.getValueAt(x, 5);
		String unit = (String)modelPO.getValueAt(x, 6);
		String qty = (String)modelPO.getValueAt(x, 7);
		String purpose = (String)modelPO.getValueAt(x, 8);
		Double unit_price = Double.parseDouble(modelPO.getValueAt(x, 9).toString());
		

		
	if(selected) {
			String strsql="insert into rf_purchase_order  \n" + 
					"(\n" + 
					"co_id,\n" + 
					"requester,\n" + 
					"classification_type,\n" + 
					//"po_no,\n" + 
					"po_date,\n" + 
					"canvass_id,\n" + 
					"pr_no,\n" + 
					"item_id,\n" + 
					"description,\n" + 
					"model,\n" + 
					"brand, \n" + 
					"unit,\n" + 
					"quantity,\n" + 
					"purpose,\n" + 
					"unit_price,\n" + 
					"supplier,\n" + 
					"terms,\n" + 
					"status_id,\n"+
					"added_by,\n" + 
					"date_added\n" + 
					")\n" + 
					"values\n" + 
					"(\n" + 
					"'"+procurement.lookupcompany.getValue()+"', \n" + //co_id
					"'"+procurement.lookuprequester.getValue()+"',  \n" + //requester
					"'"+class_type+"',  \n" +   //classification_type
					//"'"+po_no+"',  \n" + //po_no 
					"'"+date_PO.getDate()+"',  \n" +  //po_date
					"'"+canvass_id+"',\n" +  //canvass_id
					"'"+pr_no+"', \n" +  //canvass_id
					"'"+item_id+"', \n" +  //item_id
					"'"+description+"', \n" +  //description
					"'"+model+"', \n" +    //model
					"'"+brand+"', \n" +   //brand
					"'"+unit+"',  \n" +   //unit
					"'"+qty+"', \n" +     //qty
					"'"+purpose+"', \n" + //purpose
					"'"+unit_price+"', \n" +  //unit_price
					"'"+supp_id+"', \n" +     //supp_id
					"'"+terms+"', \n" + 	  //terms
					"'A',\n"+
					"'"+UserInfo.EmployeeCode+"', \n" +  //added ny
					"now()\n" +   //date added
					");";
			
			db.executeUpdate(strsql, false);
			System.out.printf("insertpurchase_order: %s",strsql);
		}	
		x++;
	}
	db.commit();
	
}

}
