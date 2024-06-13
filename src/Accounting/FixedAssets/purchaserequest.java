package Accounting.FixedAssets;

import java.awt.BorderLayout;
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
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.ptg.TblPtg;
import org.mozilla.javascript.ObjArray;

import com.mysql.jdbc.PreparedStatement.ParseInfo;

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
import Lookup._JLookupTable;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelpurchase_request;

public class purchaserequest extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelpurchaserequest;
	private JScrollPane scrollpnlpurchaserequest;
	public static _JLookup lookupPR_No;
	
	public static JTextField txtremarks;
	public static JTextField txtPR_No;
	
	public static _JDateChooser req_date;
	public static JCheckBox chkbudgeted;
	
	public static modelpurchase_request modelPR;
	public static _JTableMain tblPR;
	public static JList rowheaderPR;
	
	private JButton btnadd;
	private JButton btnedit;
	private JButton btndelete;
	private JButton btnsave;
	private JButton btncancel;
	private JButton btnpreview;
	public static JButton btnAddAcct;
	public static JButton btnRemoveAcct;
	
	protected Integer pr_no;


	public static Boolean is_budgeted = false;
	
	public purchaserequest() {
		initGUI();
	}

	public purchaserequest(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public purchaserequest(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public purchaserequest(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 3, 3));
		{
			panelpurchaserequest= new JPanel(new BorderLayout(5, 5));
			this.add(panelpurchaserequest);
			{
				JPanel panelpurchaserequest_north = new JPanel(new GridLayout(2, 1, 5, 5));
				panelpurchaserequest.add(panelpurchaserequest_north, BorderLayout.NORTH);
				panelpurchaserequest_north.setPreferredSize(new Dimension(0, 55));
				{
					JPanel pnlremarks= new JPanel(new BorderLayout(5,5));
					panelpurchaserequest_north.add(pnlremarks);
					{
						JLabel lblremarks = new JLabel("Remarks");
						pnlremarks.add(lblremarks, BorderLayout.WEST);
						lblremarks.setPreferredSize(new Dimension(70, 0));
					}
					{
						txtremarks= new JTextField();
						pnlremarks.add(txtremarks, BorderLayout.CENTER);
						txtremarks.setEditable(false);
						
					}
					{
						JPanel pnlremarks_east = new JPanel(new BorderLayout(5,5));
						pnlremarks.add(pnlremarks_east, BorderLayout.EAST);
						pnlremarks_east.setPreferredSize(new Dimension(300, 0));
						{
							JLabel lbldate= new JLabel("Date", JLabel.TRAILING);
							pnlremarks_east.add(lbldate, BorderLayout.CENTER);
						}
						{
							req_date= new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlremarks_east.add(req_date,BorderLayout.EAST);
							//req_date.setDate(FncGlobal.getDateToday());
							req_date.setEnabled(false);
							req_date.setPreferredSize(new Dimension(120, 0));
						}
					}
				} 
				{
					JPanel pnlPR_No = new JPanel(new BorderLayout(5,5));
					panelpurchaserequest_north.add(pnlPR_No);
					{
						JLabel lblPR_No = new JLabel("PR NO.");
						pnlPR_No.add(lblPR_No, BorderLayout.WEST);
						lblPR_No.setPreferredSize(new Dimension(70, 0));
					}
					{
						//txtPR_No = new JTextField();
						lookupPR_No = new _JLookup();
						pnlPR_No.add(lookupPR_No, BorderLayout.CENTER);
						lookupPR_No.setLookupSQL(getpr_no());
						lookupPR_No.setReturnColumn(0);
						lookupPR_No.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object [] data =((_JLookup)event.getSource()).getDataSet();
								
								if(data !=null) {
									
									pr_no = (Integer) data [0];
									display_pr_details(String.valueOf(pr_no));
									display_request(modelPR, rowheaderPR, String.valueOf(pr_no));
									adjustRowHeight_PR();
									//disable fields
									modelPR.setEditable(false);
									tblPR.setEnabled(false);
									procurement.enable_buttons(false, true, false, false, true, true);
								}
							}
						});
					}
					{
						JPanel pnlPR_No_east = new JPanel(new BorderLayout(5, 5));
						pnlPR_No.add(pnlPR_No_east, BorderLayout.EAST);
						pnlPR_No_east.setPreferredSize(new Dimension(500, 0));
						{
							JPanel pnlPR_No_east_extra = new JPanel(new BorderLayout(5, 5));
							pnlPR_No_east.add(pnlPR_No_east_extra, BorderLayout.WEST);
							pnlPR_No_east_extra.setPreferredSize(new Dimension(350, 0)); 
						}
						{
							chkbudgeted = new JCheckBox();
							pnlPR_No_east.add(chkbudgeted, BorderLayout.CENTER);
							chkbudgeted.setEnabled(false);
							chkbudgeted.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent arg0) {
									
									if(chkbudgeted.isSelected() ) {
										is_budgeted= true;
									}else {
										is_budgeted=false;
									}
								}
							});
						}
						{
							JLabel lblbudgeted = new JLabel("Budgeted");
							pnlPR_No_east.add(lblbudgeted, BorderLayout.EAST);
							lblbudgeted.setPreferredSize(new Dimension(110, 0));
						}
					}
				}
			}
			{
				JPanel panelpurchaserequest_center = new JPanel(new BorderLayout(3, 3));
				panelpurchaserequest.add(panelpurchaserequest_center, BorderLayout.CENTER);
				{
					scrollpnlpurchaserequest = new JScrollPane();
					panelpurchaserequest_center.add(scrollpnlpurchaserequest,BorderLayout.CENTER);
					scrollpnlpurchaserequest.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelPR = new modelpurchase_request();
						tblPR   = new _JTableMain(modelPR);
						scrollpnlpurchaserequest.setViewportView(tblPR);
						modelPR.setEditable(false);
						tblPR.setEnabled(false);
						tblPR.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblPR.getColumnModel().getColumn(1).setPreferredWidth(200);
						tblPR.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblPR.getColumnModel().getColumn(3).setPreferredWidth(100);
						//tblPR.getColumnModel().getColumn(4).setPreferredWidth(200);
						tblPR.getTableHeader().setEnabled(false); 
						tblPR.setHorizontalScrollEnabled(true);
						tblPR.addMouseListener(new MouseAdapter() {
							
							public void mousePressed(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {	
									int column 	= tblPR.getSelectedColumn();
									if(column == 0) {getitem();};
									if(column == 2) {set_itemunit();};
									tblPR.packAll();
								}
							}
							
							public void mouseReleased(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {
									int column 	= tblPR.getSelectedColumn();
									if(column == 0) {getitem();};
									if(column == 2) {set_itemunit();};
									tblPR.packAll();
								}
							}
						});
					}
					{
						rowheaderPR = tblPR.getRowHeader22();
						scrollpnlpurchaserequest.setRowHeaderView(rowheaderPR);
						scrollpnlpurchaserequest.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollpnlpurchaserequest.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
					}
				}
			}
			
		}

	}

	@Override
	public  void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Add Acct")) {
			add_row();
			//tblPR.packAll();
			//modelPR.addRow(new Object [] {null,null,null,null,null,});
			//((DefaultListModel) rowheaderPR.getModel()).addElement(modelPR.getRowCount());
			adjustRowHeight_PR();
			
		}
	}
	
	public static void clear_tab_requesition() {
		
		txtremarks.setText("");
		lookupPR_No.setValue("");
		lookupPR_No.setEnabled(true);
		modelPR.clear();
		chkbudgeted.setSelected(false);
		chkbudgeted.setEnabled(false);
		req_date.setDate(null);
		req_date.setEnabled(false);
		
	}
	public static void cleartable_rowheader() {
		 
		FncTables.clearTable(modelPR);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderPR.setModel(listModel);//Setting of listModel into rowHeader.
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
	
	public static void display_pr_details(String pr_no) {
		
		String strsql = "select a.co_id, \n" + 
				"b.company_name,\n" + 
				"a.requester_id,\n" + 
				"get_employee_name(a.requester_id) as requester_name,\n" + 
				"c.division_alias || '-' || d.dept_alias as div_dept,\n" + 
				"a.remarks,\n" + 
				"is_budgeted,\n" + 
				"a.pr_date \n"+
				"from rf_purchase_request_details a\n" + 
				"left join mf_company b on a.co_id=b.co_id\n" + 
				"left join mf_division c on a.div_id=c.division_code\n" + 
				"left join mf_department d on a.dept_id=d.dept_code\n" + 
				"where pr_no='"+pr_no+"'";
		
		pgSelect db = new pgSelect();
		db.select(strsql);
		
		if(db.isNotNull()) {
			procurement.lookupcompany.setValue((String) db.getResult()[0][0]);
			procurement.txtcompany.setText((String) db.getResult()[0][1]);
			procurement.lookuprequester .setValue((String) db.getResult()[0][2]);
			procurement.txtrequester_name.setText((String) db.getResult()[0][3]);
			procurement.txtdivdept.setText((String) db.getResult()[0][4]);
			txtremarks.setText((String) db.getResult()[0][5]);
			chkbudgeted.setSelected((boolean) db.getResult()[0][6]);
			req_date.setDate((Date) db.getResult()[0][7]);
			
		}
		
	}
	
	public static void display_request(DefaultTableModel model, JList rowHeader, String pr_no){
		
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		
		//Individual display of assets
		String strSQL = "select item_id,description, quantity, purpose from rf_purchase_request_details where pr_no='"+pr_no+"' and status_id='A'";
		
		FncSystem.out("display_request", strSQL);	
		 
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
	
	private void set_itemunit() {
		
		int column = tblPR.getSelectedColumn();
		int row = tblPR.getSelectedRow();
		if (column == 2 && modelPR.isEditable() ) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Select Item Unit", get_itemunit(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			
			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelPR.setValueAt(data[0], row, column);
				
			}			
			tblPR.packAll();
		}
	}
	public void getitem () {
		int column = tblPR.getSelectedColumn();
		int row = tblPR.getSelectedRow();
		
		if (column == 0 ) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Items", selectitems(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			
			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelPR.setValueAt(data[0], row, column);
				modelPR.setValueAt(data[1], row, column+1);
			}		
			tblPR.packAll();
		}
	}
	
	private String get_itemunit() {
		String strsql="select 'PER PIECE'   union all\n" + 
				"select 'PER BOX'  union all\n" + 
				"select 'PER SET' union all\n" + 
				"select 'PER REAM' ";
		return strsql;
	}
	private String selectitems() {
		String sql = "select item_id::varchar, item_name from tbl_item";
		
		return sql;
	}
	
	public static String getpr_no() {
		
		String strsql="select  distinct on(pr_no) pr_no::int, get_employee_name(requester_id) as requester from rf_purchase_request_details order by pr_no";
		
		return strsql;
		
	}
	public static String setpr_no() {
		
		pgSelect db = new pgSelect();
		String strSQL = "select  COALESCE(MAX(pr_no::int),0) + 1 from rf_purchase_request_details";
		db.select(strSQL);
		return db.Result[0][0].toString();
		
	}
	
	public static void insertpurchase_request() {
		int x  = 0;	
		int y  = 1;
		int rw = tblPR.getModel().getRowCount();
		String pr_no = lookupPR_No.getValue();
		pgUpdate db = new pgUpdate();
		System.out.println("x: "+x);
		System.out.println("rw: "+rw);
		while(x < rw ) {
			
			String item_id     = modelPR.getValueAt(x, 0).toString();
			String description = modelPR.getValueAt(x, 1).toString();
			//String unit_id     = modelPR.getValueAt(x, 2).toString();
			String qty         = modelPR.getValueAt(x, 2).toString();
			String purpose    =  modelPR.getValueAt(x, 3).toString();
			System.out.println("item_id: "+item_id);
			System.out.println("description: "+description);
			//System.out.println("unit_id: "+unit_id);
			System.out.println("qty: "+qty);
			System.out.println("purpose: "+purpose);
			
			if(item_id == "") {}
			else {
				
				
				String strsql="insert into rf_purchase_request_details \n" + 
						"(\n" + 
						"rec_id,\n" +
						"co_id,\n" + 
						"div_id,\n" + 
						"dept_id,\n" + 
						"requester_id,\n" + 
						"pr_no,\n" + 
						"pr_date,\n" +
						"description,\n" +
						"item_id,\n" + 
						"is_budgeted,\n" + 
						"purpose,\n" + 
						"remarks,\n" + 
						"status_id,\n" + 
						"unit_id,\n" + 
						"quantity,\n" + 
						"date_added,\n" + 
						"added_by,\n" + 
						"date_edited,\n" + 
						"edited_by\n" + 
						")\n" + 
						"values\n" + 
						"(\n" + 
						"'"+pr_no+"',\n" +
						"'"+procurement.lookupcompany.getValue()+"',\n" + 
						"'"+procurement.div_id+"',\n" + 
						"'"+procurement.dept_id+"',\n" + 
						"'"+procurement.lookuprequester.getValue()+"',\n" + 
						"'"+pr_no+"',\n" + 
						"'"+req_date.getText()+"',\n" +
						"'"+description+"',\n" + 
						"'"+item_id+"',\n" + 
						""+is_budgeted+",\n" + 
						"'"+purpose+"',\n" + 
						"'"+txtremarks.getText()+"',\n" + 
						"'A',\n" + 
						"'',\n" + //unit_id
						"'"+qty+"',\n" + 
						"now(),\n" + 
						"'"+UserInfo.EmployeeCode+"',\n" + 
						"null,\n" + 
						"null\n" + 
						")";
					
				
				db.executeUpdate(strsql, false);
				System.out.printf("SQL #1: %s" , strsql);
				
				x++;
			}
		}
		db.commit();
	}
	
	public static void add_row() {
		
		modelPR.addRow(new Object [] {null,null,null,null,});
		((DefaultListModel) purchaserequest.rowheaderPR.getModel()).addElement(modelPR.getRowCount());
	}
	
	public static void adjustRowHeight_PR(){//used
		int rw = tblPR.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblPR.setRowHeight(x, 22);			
			x++;
		}

	}
	
	public static Boolean checkPRdetails() {
		
		if(procurement.lookupcompany.getText().equals("")
				||procurement.lookuprequester.getText().equals("")
				) {
			return false;
		}else {
			return true;
		}
	}
	
	

}
