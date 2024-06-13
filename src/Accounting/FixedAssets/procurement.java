package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toedter.calendar.JDateChooser;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTagLabel;
import interfaces._GUI;
import net.sf.jasperreports.engine.export.Grid;
import tablemodel.modelpurchase_order;
import tablemodel.modelpurchase_request;

public class procurement extends _JInternalFrame implements _GUI, ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title="Procurement Module";
	public static Dimension framesize= new Dimension(700,500);
	private JPanel pnlmain;
	private JPanel pnlnorth;
	private JPanel pnlsouth;
	private JPanel pnlcenter;
	private JPanel pnlcompany;
	private JPanel pnlrequester;
	private JLabel lblcompany;
	private JLabel lblrequester;
	
	public static JButton btnadd;
	public static JButton btnedit;
	public static JButton btndelete;
	public static JButton btnsave;
	public static JButton btncancel;
	public static JButton btnpreview;
	
	private JPanel pnldate;
	private JLabel lbldate;
	private static JTabbedPane tabcenter;
	private JPanel pnlrequesition;
	private JPanel pnlpurchaseorder;
	private JPanel pnlreceiving;
	private JPanel pnlnorthwest;
	private JPanel pnlnortheast;
	private JPanel pnldivdept;
	private JLabel lbldivdept;

	public static _JLookup lookupcompany;
	public static _JLookup lookuprequester;
	
	public static JTextField txtdivdept;
	public static JTextField txtrequester_name;
	public static JTextField txtcompany;

	//private _JDateChooser req_date;
	protected static String co_id;
	protected String company;
	private _JTagLabel tagcompany;
	protected String emp_code;
	protected String emp_name;
	protected String div_dept;
	protected static String div_id;
	protected static String dept_id;
	
	private procurement_payment_processing pnlprocessing;
	public static modelpurchase_request modelPR;
	

	public procurement(){
		super(title, false, true, true, true);
		initGUI();
		
	}
	public procurement (String title) {
		super(title, true, true, true, true);
		initGUI();
	}
	public procurement (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	@Override
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
				pnlnorth = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 90));
				pnlnorth.setBorder(JTBorderFactory.createTitleBorder("Request Information"));
				{
					pnlnorthwest= new JPanel(new BorderLayout(3, 3));
					pnlnorth.add(pnlnorthwest, BorderLayout.WEST);
					pnlnorthwest.setPreferredSize(new Dimension(450, 0));
					{
						pnlcompany = new JPanel(new BorderLayout(3, 3));
						pnlnorthwest.add(pnlcompany, BorderLayout.NORTH);
						pnlcompany.setPreferredSize(new Dimension(0, 27));
						{
							lblcompany= new JLabel("Company");
							pnlcompany.add(lblcompany,BorderLayout.WEST);
							lblcompany.setPreferredSize(new Dimension(70, 0));
						}
						{
							lookupcompany = new _JLookup();
							pnlcompany.add(lookupcompany, BorderLayout.CENTER);
							lookupcompany.setLookupSQL(SQL_COMPANY());
							lookupcompany.setReturnColumn(0);
							lookupcompany.setEditable(false);
							lookupcompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null) {
										co_id 		= (String) data[0];	
										company		= (String) data[1];
										
										txtcompany.setText(company);
										lookuprequester.setEnabled(true);
										lookuprequester.setLookupSQL(getrequester(co_id));
									}
								}
							});
						} 
						{
							txtcompany = new JTextField();
							pnlcompany.add(txtcompany, BorderLayout.EAST);
							txtcompany.setPreferredSize(new Dimension(300, 0));
							txtcompany.setEditable(false);
						}
					}
					{
						pnlrequester= new JPanel(new BorderLayout(3,3));
						pnlnorthwest.add(pnlrequester, BorderLayout.SOUTH);
						pnlrequester.setPreferredSize(new Dimension(0, 27));
						{
							lblrequester = new JLabel("Requester");
							pnlrequester.add(lblrequester, BorderLayout.WEST);
							lblrequester.setPreferredSize(new Dimension(70, 0));
						}
						{
							lookuprequester = new _JLookup();
							pnlrequester.add(lookuprequester, BorderLayout.CENTER);
							lookuprequester.setReturnColumn(1);
							lookuprequester.setEnabled(false);
							lookuprequester.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									
									if(data != null) {
										emp_name = (String)data [0];
										emp_code = (String)data [1];
										div_dept = (String)data [10];
										div_id   = (String)data [4];
										dept_id  = (String)data [7];
												
										
										txtrequester_name.setText(emp_name);
										txtdivdept.setText(div_dept);
									}
								}
							});
						}
						{
							txtrequester_name = new JTextField();
							pnlrequester.add(txtrequester_name, BorderLayout.EAST);
							txtrequester_name.setEditable(false);
							txtrequester_name.setPreferredSize(new Dimension(300, 0));
						}
					}
				}
				{
					pnlnortheast = new JPanel(new BorderLayout(3, 3));
					pnlnorth.add(pnlnortheast, BorderLayout.EAST);
					pnlnortheast.setPreferredSize(new Dimension(170, 0));
					{
						pnldivdept= new JPanel(new BorderLayout(3, 3));
						pnlnortheast.add(pnldivdept, BorderLayout.NORTH);
						pnldivdept.setPreferredSize(new Dimension(0, 27));
						{
							lbldivdept= new JLabel("Div/Dept");
							pnldivdept.add(lbldivdept, BorderLayout.WEST);
							lbldivdept.setPreferredSize(new Dimension(60, 0));
						}
						{
							txtdivdept= new JTextField();
							pnldivdept.add(txtdivdept, BorderLayout.CENTER);
							txtdivdept.setEditable(false);
						}
					}
					{
						pnldate= new JPanel(new BorderLayout(3, 3));
						pnlnortheast.add(pnldate, BorderLayout.SOUTH);
						pnldate.setPreferredSize(new Dimension(0, 27));
						
					}
				}
			}
			{
				tabcenter = new JTabbedPane();
				pnlmain.add(tabcenter,BorderLayout.CENTER);
				{
					pnlrequesition= new purchaserequest();
					tabcenter.addTab("Requesition", null, pnlrequesition, null);
					
				}
				{
					pnlpurchaseorder = new purchaseorder();
					tabcenter.addTab("Purchase Order", null, pnlpurchaseorder, null);
				}
				{
					pnlreceiving = new receivingpurchase();
					tabcenter.addTab("Receiving", null, pnlreceiving, null);
				}
				{
					pnlprocessing = new procurement_payment_processing();
					tabcenter.addTab("Payment Processing", null, pnlprocessing, null);
				}
				
				
				tabcenter.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();
						
						if (selectedTab == 0) {
							
							//clear_procurement_request_information();
							//purchaserequest.clear_tab_requesition();
							//purchaserequest.cleartable_rowheader();
							enable_buttons(true, false, false, true, true, true);
							
						}
						
						if(selectedTab == 1) {
							
							//purchaseorder.clear_tab_PO();
							//purchaseorder.cleartable_rowheader();
							enable_buttons(false, false, false, false, true, false);
						}
						
						if(selectedTab == 2) {
							//receivingpurchase.cleartab_receiving();
							//receivingpurchase.cleartable_rowheader();
							enable_buttons(true, false, false, false, true, false);
						}
						
						if(selectedTab == 3) {
							
							enable_buttons(false, false, false, false, true, false);
						}
						
						//FncTables.clearTable(purchaserequest.modelPR);
					}
				});
				
				
			}
			{
				pnlsouth=new JPanel(new GridLayout(1, 6, 3, 3));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 30));
				{
					btnadd = new JButton("Add New");
					btnadd.setActionCommand("Add");
					pnlsouth.add(btnadd);
					btnadd.setEnabled(true);
					btnadd.addActionListener(this);
				}
				{
					btnedit= new JButton("Edit");
					pnlsouth.add(btnedit);
					btnedit.setActionCommand("EDIT");
					btnedit.setEnabled(false);
					btnedit.addActionListener(this);
				}
				{
					btndelete = new JButton("Delete");
					pnlsouth.add(btndelete);
					btndelete.setActionCommand("DELETE");
					btndelete.setEnabled(false);
					btndelete.addActionListener(this);
				}
				{
					btnsave = new JButton("Submit");
					pnlsouth.add(btnsave);
					btnsave.setActionCommand("Save");
					btnsave.setEnabled(false);
					btnsave.addActionListener(this);
				}
				{
					btncancel = new JButton("Cancel");
					pnlsouth.add(btncancel);
					btncancel.setActionCommand("Cancel");
					btncancel.setEnabled(false);
					btncancel.addActionListener(this);
				}
				{
					btnpreview = new JButton("Preview");
					pnlsouth.add(btnpreview);
					btnpreview.setActionCommand("PREVIEW");
					btnpreview.setEnabled(false);
					btnpreview.addActionListener(this);
				}
			}
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		//pgUpdate db = new pgUpdate();
		Integer selectedtab  = tabcenter.getSelectedIndex();
		
		
		if ( e.getActionCommand().equals("Add")){
			tabcenter.setEnabled(false);
			
			if(selectedtab == 0 ) {//tab requesition
				
				purchaserequest.lookupPR_No.setValue(purchaserequest .setpr_no());
				FncTables.clearTable(purchaserequest.modelPR);
				purchaserequest.cleartable_rowheader();
				purchaserequest.add_row();
				purchaserequest.adjustRowHeight_PR();
				//purchaserequest.modelPR.addRow(new Object[] {null,null,null,null,null});
				//((DefaultListModel) purchaserequest.rowheaderPR.getModel()).addElement(modelPR.getRowCount());
				newstate();
				purchaserequest.req_date.setDate(FncGlobal.getDateToday());
				enable_buttons(false, false, false, true, true, false);
				
				purchaserequest.btnAddAcct.setEnabled(true);
				purchaserequest.btnRemoveAcct.setEnabled(true);
			}
			
			if( selectedtab == 1){//TAB PURCHASE ORDER
				
				if(purchaseorder.lookcanvassid.getValue() != null) {
					//purchaseorder.lookupPO_No.setValue(purchaseorder.setpo_no());
					purchaseorder.date_PO.setDate(FncGlobal.getDateToday());
					purchaseorder.modelPO.setEditable(true);
					purchaseorder.lookupterms.setEditable(true);
					purchaseorder.lookcanvassid.setEnabled(false);
					//purchaseorder.lookupPO_No.setEnabled(false);
					purchaseorder.lookuptype.setEditable(true);
					enable_buttons(false, false, false, true, true, false);
				}else {
					
					JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select canvass ID");
				}
			}
			
			if(selectedtab ==2) {//TAB RECEIVING
				
				//if(receivingpurchase.lookupDR_id.getValue() == null) {
					
					//receivingpurchase.lookupDR_id.setValue(receivingpurchase.setreceiving_dr_id());
					receivingpurchase.received_date.setDate(FncGlobal.getDateToday());
					FncTables.clearTable(receivingpurchase.modelreceiving);
					receivingpurchase.cleartable_rowheader();
					receivingpurchase.add_row();
					receivingpurchase.btnAddAcct.setEnabled(true);
					receivingpurchase.lookuprecvdby_id.setEditable(true);
					receivingpurchase.chkpettycash.setEnabled(true);
					receivingpurchase.modelreceiving.setEditable(true);
					//receivingpurchase.txtremarks.setEditable(true);
					//receivingpurchase.Tblreceiving.setEnabled(true);
					receivingpurchase.modelreceiving.setEditable(true);
					receivingpurchase.modelreceiving.setEditable(true);
					
					enable_buttons(false, false, false, true, true, false);
				//}
			}
		}
		
		
		
		
		if(e.getActionCommand().equals("Cancel")) {
			tabcenter.setEnabled(true);
			
			if(selectedtab == 0) {//tab requesition
				
				//Clear fields request information
				clear_procurement_request_information();
				//clear fields at tab requesition
				purchaserequest.clear_tab_requesition();
				purchaserequest.cleartable_rowheader();
				purchaserequest.btnAddAcct.setEnabled(false);
				purchaserequest.btnRemoveAcct.setEnabled(false);
				//disable fields request information
				lookupcompany.setEditable(false);
				txtcompany.setEditable(false);
				
				enable_buttons(true, false, false, false, true, false);
			}
			 
			if(selectedtab == 1) { //TAB PURCHASE ORDER
				
				purchaseorder.clear_tab_PO();
				//FncTables.clearTable(purchaseorder.modelPO);
				purchaseorder.cleartable_rowheader();
				enable_buttons(true, false, false, false, false, false);
				
			}
			if(selectedtab ==2) {
				receivingpurchase.cleartab_receiving();
				receivingpurchase.cleartable_rowheader();
				receivingpurchase.lookuprecvdby_id.setEditable(false);
				receivingpurchase.received_date.setEditable(false);
				receivingpurchase.chkpettycash.setEnabled(false);
				//receivingpurchase.btnAddAcct.setEnabled(false);
				//receivingpurchase.Tblreceiving.setEnabled(false);
				receivingpurchase.modelreceiving.setEditable(false);
				enable_buttons(false, false, false, false, true, false);
			}
			if(selectedtab == 3) {
				procurement_payment_processing.cleartab_payment_processing();
				procurement_payment_processing.cleartable_rowheader();
				enable_buttons(false, false, false, false, true, false);
			}
			
		}
		
		
		if(e.getActionCommand().equals("Save")) {
			tabcenter.setEnabled(true);
			if(selectedtab == 0) {//tab requesition
				
				if(purchaserequest.checkPRdetails()) {
					if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Save Request?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						
						purchaserequest.insertpurchase_request();
						enable_buttons(true, false, false, true, true, true);
						JOptionPane.showMessageDialog(null, "Your request is already save.", "Information", JOptionPane.INFORMATION_MESSAGE);
						preview_purchase_request
						(
								purchaserequest.lookupPR_No.getValue(), //request_id
								txtcompany.getText(), //co_name
								txtrequester_name.getText(), //requester
								txtdivdept.getText(), //div_dept
								purchaserequest.req_date.getDate(), //date_requested
								UserInfo.FullName
						);
						purchaserequest.cleartable_rowheader();
						clear_procurement_request_information();
						purchaserequest.clear_tab_requesition();
						
					}
				}else {
					JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please check the request information.", "", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
			if(selectedtab == 1) { //TAB PURCHASE ORDER
				
				if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Save purchase order?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					purchaseorder.insert_PO();
					purchaseorder.create_po();
					JOptionPane.showMessageDialog(getTopLevelAncestor(), "Purchase order has been saved!", "", JOptionPane.INFORMATION_MESSAGE);
					purchaseorder.display_PO(purchaseorder.modelPO, purchaseorder.rowheaderPO);
					purchaseorder.displayPO_details();
					purchaseorder.lookcanvassid.setEnabled(true);
					purchaseorder.lookcanvassid.setEditable(true);
					enable_buttons(false, false, false, false, true, true);
				}
			}
			if(selectedtab == 2) {
				if(receivingpurchase.check_alldetails()) {
					
					if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Save purchase order?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						
						receivingpurchase.insert_receiving();
						//receivingpurchase.insert_rplf_details_header();
						receivingpurchase.display_PO(receivingpurchase.modelreceiving, receivingpurchase.rowheaderreceiving);
						
						JOptionPane.showMessageDialog(getTopLevelAncestor(), "Selected items received!", "Save", JOptionPane.INFORMATION_MESSAGE);
						
						//receivingpurchase.displayreceiving_details();
						enable_buttons(false, false, false, false, true, false);
						
					}
				}else {
					if(receivingpurchase.lookuprecvdby_id.getValue() == null) {
						JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select received by!", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
		
		if(e.getActionCommand().equals("PREVIEW")) {
			
			if(selectedtab == 0) {
				preview_purchase_request
				(
						purchaserequest.lookupPR_No.getValue(), //request_id
						txtcompany.getText(), //co_name
						txtrequester_name.getText(), //requester
						txtdivdept.getText(), //div_dept
						purchaserequest.req_date.getDate(), //date_requested
						UserInfo.FullName
				);
			}else if(selectedtab == 1) {
				preview_purchase_order
				(
						//purchaseorder.print_po, 
						purchaseorder.po_no, 
						purchaseorder.pr_no, 
						txtcompany.getText(), 
						purchaseorder.date_PO.getDate(), 
						UserInfo.FullName);
			}else if(selectedtab == 3) {
				preview_receiving_purchase(procurement_payment_processing.lookuppo_no.getValue(), txtcompany.getText(), UserInfo.FullName);
			}
			
		}
	}
	
	public static void newstate() {
		Integer selectedtab  = tabcenter.getSelectedIndex();
		
		if(selectedtab == 0) {
			lookupcompany.setEditable(true);
			//txtcompany.setEditable(true);
			purchaserequest.txtremarks.setEditable(true);
			purchaserequest.lookupPR_No.setEnabled(false); 
			purchaserequest.req_date.setEnabled(true);
			//purchaserequest.chkbudgeted.setEnabled(true);
			purchaserequest.modelPR.setEditable(true);
			purchaserequest.tblPR.setEditable(true);
			purchaserequest.tblPR.setEnabled(true);
		}
		
	}
	
	public static void clear_procurement_request_information() {
		
		//clear fields @ procurement class
		lookupcompany.setValue("");
		lookupcompany.setEditable(true);
		txtcompany.setText("");
		txtcompany.setEditable(false);
		lookuprequester.setValue("");
		lookuprequester.setEnabled(false);
		txtrequester_name.setText("");
		txtdivdept.setText("");
		
		//clear fields @ purchaserequest class
		/*purchaserequest.txtremarks.setText("");
		purchaserequest.txtremarks.setEditable(false);
		purchaserequest.lookupPR_No.setText("");
		purchaserequest.lookupPR_No.setEnabled(true);
		purchaserequest.req_date.setDate(null);
		purchaserequest.req_date.setEnabled(false);
		purchaserequest.chkbudgeted.setSelected(false);
		purchaserequest.chkbudgeted.setEnabled(false);
		purchaserequest.modelPR.setEditable(false);
		purchaserequest.modelPR.clear();
		
		enable_buttons(true, false, false, true, true, true);
		*/
	}
	
	public static void enable_buttons(Boolean badd, Boolean bedit, Boolean bdelete, Boolean bsubmit, Boolean bcancel, Boolean bpreview  ) {
		btnadd.setEnabled(badd);
		btnedit.setEnabled(bedit);
		btndelete.setEnabled(bdelete);
		btnsave.setEnabled(bsubmit);
		btncancel.setEnabled(bcancel);
		btnpreview.setEnabled(bpreview);
	}
	
	public static String SQL_COMPANY() {//XXX Company
		
		String strSQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		return strSQL;
	}
	
	public static String getrequester(String co_id){
		
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
				//"where e.co_id::int="+co_id+"";
				//"AND not emp_status='I' \n";//lookup active emp only
		System.out.println("getrequester= "+ strsql);
		return strsql;
		
	}
	
	public  void preview_purchase_request(String request_id, String co_name,  String requester, String div_dept, Date date_requested, String prepared_by ) {
		
		System.out.println("request_id=: "+request_id );
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("request_id", request_id);
		mapParameters.put("co_name", co_name);
		mapParameters.put("requester",requester );
		mapParameters.put("div_dept",div_dept );
		mapParameters.put("date_requested", date_requested);
		mapParameters.put("prepared_by", prepared_by);
		try{
			mapParameters.put("co_logo_vdc", this.getClass().getClassLoader().getResourceAsStream("Images/verdant_logo.bmp"));
			mapParameters.put("co_logo_cdc", this.getClass().getClassLoader().getResourceAsStream("Images/cenqlogo.png"));
			mapParameters.put("co_logo_adc", this.getClass().getClassLoader().getResourceAsStream("Images/acer_logo.bmp"));
		}catch(NullPointerException e){}
		FncReport.generateReport("/Reports/rpt_request_purchase.jasper", "Purchase Request", mapParameters);
	}
	
	public   void preview_purchase_order(String po_no, String req_id, String co_name, Date po_date, String prepared_by ) {
		
		System.out.println("po_no: "+po_no);
		System.out.println("req_id: "+req_id);
		System.out.println("co_name: "+co_name);
		System.out.println("po_date: "+po_date);
		System.out.println("prepared_by: "+prepared_by);
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("po_no", po_no);
		mapParameters.put("co_name", co_name);
		mapParameters.put("req_id",req_id );
		//mapParameters.put("div_dept",div_dept );
		mapParameters.put("po_date", po_date);
		mapParameters.put("prepared_by", prepared_by);
		try{
			mapParameters.put("co_logo_vdc", this.getClass().getClassLoader().getResourceAsStream("Images/verdant_logo.bmp"));
			mapParameters.put("co_logo_cdc", this.getClass().getClassLoader().getResourceAsStream("Images/cenqlogo.png"));
			mapParameters.put("co_logo_adc", this.getClass().getClassLoader().getResourceAsStream("Images/acer_logo.bmp"));
		}catch(NullPointerException e){}
		FncReport.generateReport("/Reports/rpt_purchase_order.jasper", "Purchase Order", mapParameters);
	}
	
	public  void preview_receiving_purchase(String dr_id, String co_name, String prepared_by ) {
		
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("dr_id", dr_id);
		mapParameters.put("co_name", co_name);
		//mapParameters.put("requester",requester );
		//mapParameters.put("div_dept",div_dept );
		//mapParameters.put("date_requested", date_requested);
		mapParameters.put("prepared_by", prepared_by);
		try{
			mapParameters.put("co_logo_vdc", this.getClass().getClassLoader().getResourceAsStream("Images/verdant_logo.bmp"));
			mapParameters.put("co_logo_cdc", this.getClass().getClassLoader().getResourceAsStream("Images/cenqlogo.png"));
			mapParameters.put("co_logo_adc", this.getClass().getClassLoader().getResourceAsStream("Images/acer_logo.bmp"));
		}catch(NullPointerException e){}
		FncReport.generateReport("/Reports/rpt_receiving_purchase.jasper", "Receiving", mapParameters);
	}

}
