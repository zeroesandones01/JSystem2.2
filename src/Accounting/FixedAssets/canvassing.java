package Accounting.FixedAssets;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.functions.Even;
import org.apache.poi.ss.formula.ptg.TblPtg;

import Buyers.ClientServicing.Add_Edit_Holidays;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelcanvass;
import tablemodel.modelcanvassing_itemrequested;

public class canvassing extends _JInternalFrame implements _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title="Canvassing Module";
	public static Dimension framesize= new Dimension(700,500);
	private JPanel panelmain;
	private JScrollPane scrollcenter;
	private JScrollPane scrollitems_requested;
	
	private static modelcanvass modelcanvassing;
	public static modelcanvassing_itemrequested modelcanvass_itemrequested;
	
	private static _JTableMain tblcanvass;
	private _JTableMain tblcanvass_itemrequested;
	private static JList rowheadercanvass;
	private static JList rowheader_itemrequested;
	
	private JButton btnsave;
	private JButton btnpreview;
	private JButton btnedit;
	private JButton btncancel;
	private JButton btnaddnew;
	private static JButton btnAddAcct;
	private static JButton btnRemoveAcct;
	
	private static _JLookup lookuppr_id;
	private static _JLookup lookupcanvassid;
	private JTextField txtcontactno;

	protected String selected_supplier;
	protected static String item_id;
	public static String pr_no;
	private static String co_logo = "cenqlogo.png";
	private static String co_name = "CENQHOMES DEVELOPMENT CORPORATION";
	
	
	public canvassing(){
		super(title, false, true, true, true);
		initGUI();
		
	}
	public canvassing (String title) {
		super(title, true, true, true, true);
		initGUI();
	}
	public canvassing (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
			panelmain = new JPanel(new BorderLayout(5, 5));
			this.add(panelmain, BorderLayout.CENTER);
			panelmain.setBorder(_EMPTY_BORDER);
			{
				JPanel pnlnorth = new JPanel(new BorderLayout(5, 5));
				panelmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 250));
				{
					JSplitPane splitcanvassing = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
					pnlnorth.add(splitcanvassing);
					splitcanvassing.setOneTouchExpandable(true);
					splitcanvassing.setResizeWeight(.5);
					{
						JPanel pnltop = new JPanel(new BorderLayout(5, 5));
						splitcanvassing.add(pnltop, JSplitPane.TOP);
						pnltop.setBorder(JTBorderFactory.createTitleBorder("Request Details"));
						{
							JPanel pnlpr_id = new JPanel(new BorderLayout(5, 5));
							pnltop.add(pnlpr_id, BorderLayout.NORTH);
							pnlpr_id.setPreferredSize(new Dimension(0, 25));
							{
								JLabel lblpr_id = new JLabel("PR ID");
								pnlpr_id.add(lblpr_id, BorderLayout.WEST);
								lblpr_id.setPreferredSize(new Dimension(50, 0));
							}
							{
								lookuppr_id = new _JLookup();
								pnlpr_id.add(lookuppr_id, BorderLayout.CENTER);
								lookuppr_id.setLookupSQL(getPR());
								lookuppr_id.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										pgSelect db = new pgSelect();
										if(data != null) {
											pr_no = (String)data [0];
											
											lookuppr_id.setValue(pr_no);
											displaypurchase_request(modelcanvass_itemrequested,rowheader_itemrequested, db, pr_no);
											//display_canvassing(modelcanvassing , rowheadercanvass , db, lookupcanvassid.getValue());
											enablebuttons(true, false, false, false, true);
										}
									}
								});
							}
							{
								JPanel pnlpr_id_extra = new JPanel(new BorderLayout(5, 5));
								pnlpr_id.add(pnlpr_id_extra, BorderLayout.EAST);
								pnlpr_id_extra.setPreferredSize(new Dimension(450, 0));
							}
						}
						
						{
							JPanel pnlitems_requested = new JPanel(new BorderLayout(5, 5));
							pnltop.add(pnlitems_requested, BorderLayout.CENTER);
							{
								  scrollitems_requested = new JScrollPane();
								 pnlitems_requested.add(scrollitems_requested);
								 scrollitems_requested.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
								 {
									 modelcanvass_itemrequested = new modelcanvassing_itemrequested();
									 tblcanvass_itemrequested = new _JTableMain(modelcanvass_itemrequested);
									 scrollitems_requested.setViewportView(tblcanvass_itemrequested);
									 tblcanvass_itemrequested.setHorizontalScrollEnabled(true);
									 tblcanvass_itemrequested.getTableHeader().setEnabled(false);
									 tblcanvass_itemrequested.packAll();
									 tblcanvass_itemrequested.setEnabled(false);
									 tblcanvass_itemrequested.setEditable(false);
									 tblcanvass_itemrequested.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										
										public void valueChanged(ListSelectionEvent e) {
											if(!e.getValueIsAdjusting()) {
												
												try {
													pgSelect db = new pgSelect();
													int row = tblcanvass_itemrequested.getSelectedRow();
													 item_id = (String) modelcanvass_itemrequested.getValueAt(row, 1);
													System.out.println("item_id = "+ item_id);
													
													display_supplier_canvassing(modelcanvassing, rowheadercanvass, item_id, db);
												}catch (ArrayIndexOutOfBoundsException ex) {
												}
											}
										}
									});
								 }
								 {
									 rowheader_itemrequested = tblcanvass_itemrequested.getRowHeader();
									 scrollitems_requested.setRowHeaderView(rowheader_itemrequested);
									 scrollitems_requested.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								 }
								 /*{
									 scrollitems_requested.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
									 
								 }*/
							}
						}
					}
					{
						JPanel pnlbottom = new JPanel(new BorderLayout(5, 5));
						splitcanvassing.add(pnlbottom, JSplitPane.BOTTOM);
						//pnlbottom.setBackground(Color.black);
						pnlbottom.setBorder(JTBorderFactory.createTitleBorder("Canvassing Details"));
						
						{
							 JPanel pnlcanvassid_contactno = new JPanel(new GridLayout(2, 1, 5, 5));
							 pnlbottom.add(pnlcanvassid_contactno,BorderLayout.NORTH);
							 pnlcanvassid_contactno.setPreferredSize(new Dimension(0, 60));
							 //pnlcanvassid_contactno.setBackground(Color.CYAN);
							  
							{
								JPanel pnlcanvass_id = new JPanel(new BorderLayout(5, 5));
								pnlcanvassid_contactno.add(pnlcanvass_id);
								
								{
									JLabel lblcanvassid = new JLabel("Canvass ID");
									pnlcanvass_id.add(lblcanvassid, BorderLayout.WEST);
									lblcanvassid.setPreferredSize(new Dimension(80, 0));
								}
								{
									lookupcanvassid= new _JLookup();
									pnlcanvass_id.add(lookupcanvassid, BorderLayout.CENTER);
									//lookupcanvassid.setReturnColumn(0);
									lookupcanvassid.setLookupSQL(getcanvass_id());
									lookupcanvassid.addLookupListener(new LookupListener() {
										
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();
											pgSelect db = new pgSelect();
											if(data !=null) {
												String canvass_id = (String) data[0];
												String pr_id = (String) data[1];
												lookuppr_id.setValue(pr_id);
												lookupcanvassid.setValue(canvass_id);
												displaypurchase_request(modelcanvass_itemrequested,rowheader_itemrequested, db, pr_id);
												display_canvassing(modelcanvassing , rowheadercanvass , db, lookupcanvassid.getValue());
												enablebuttons(false, false, true, false, true);
												
												
											}
										}
									});
								}
								{
									JPanel pnlcanvass_id_east = new JPanel(new BorderLayout(5,5));
									pnlcanvass_id.add(pnlcanvass_id_east, BorderLayout.EAST);
									pnlcanvass_id_east.setPreferredSize(new Dimension(480, 0));
								}
							}
							{
								JPanel pnlcontactno= new JPanel(new BorderLayout(5, 5));
								pnlcanvassid_contactno.add(pnlcontactno);
								{
									JLabel lblcontactno = new JLabel("Contact NO.");
									pnlcontactno.add(lblcontactno, BorderLayout.WEST);
									lblcontactno.setPreferredSize(new Dimension(80, 0));
								}
								{
									txtcontactno= new JTextField();
									pnlcontactno.add(txtcontactno, BorderLayout.CENTER);
								}
							}
						}
					}
				}
			}
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(5, 5));
				panelmain.add(pnlcenter, BorderLayout.CENTER);
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder("SUPPLIERS"));
				{
					scrollcenter = new JScrollPane();
					pnlcenter.add(scrollcenter, BorderLayout.CENTER);
					scrollcenter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelcanvassing = new modelcanvass();
						tblcanvass = new _JTableMain(modelcanvassing);
						scrollcenter.setViewportView(tblcanvass);
						tblcanvass.setEnabled(false);
						tblcanvass.setEditable(false);
						tblcanvass.getTableHeader().setEnabled(false);
						//tblcanvass.packAll();
						tblcanvass.setHorizontalScrollEnabled(true);
						tblcanvass.getColumnModel().getColumn(0).setPreferredWidth(50);
						tblcanvass.getColumnModel().getColumn(1).setPreferredWidth(120);
						tblcanvass.getColumnModel().getColumn(2).setPreferredWidth(250);
						tblcanvass.getColumnModel().getColumn(3).setPreferredWidth(250);
						tblcanvass.getColumnModel().getColumn(4).setPreferredWidth(100);
						tblcanvass.getColumnModel().getColumn(5).setPreferredWidth(100);
						tblcanvass.getColumnModel().getColumn(6).setPreferredWidth(50);
						tblcanvass.getColumnModel().getColumn(7).setPreferredWidth(50);
						tblcanvass.getColumnModel().getColumn(8).setPreferredWidth(50);
						
						
						
						tblcanvass.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()) {
									try {
										
										int row = tblcanvass.getSelectedRow();
											selected_supplier=    (String) modelcanvassing.getValueAt(row, 1);
										btnAddAcct.setEnabled(true);
										System.out.println("selected_supplier: "+selected_supplier);
										
									}catch (ArrayIndexOutOfBoundsException ex) {
									}
								}
							}
						});
					}
					{
						rowheadercanvass = tblcanvass.getRowHeader();
						scrollcenter.setRowHeaderView(rowheadercanvass);
						scrollcenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollcenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 5, 5, 5));
				panelmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 35));
				
				{
					btnaddnew = new JButton("ADDNEW");
					pnlsouth.add(btnaddnew);
					btnaddnew.setEnabled(false);
					btnaddnew.setActionCommand("ADDNEW");
					btnaddnew.addActionListener(this);
				}
				{
					btnsave = new JButton("SAVE");
					pnlsouth.add(btnsave);
					btnsave.setEnabled(false);
					btnsave.setActionCommand("SAVE");
					btnsave.addActionListener(this);
				}
				{
					btnpreview = new JButton("PREVIEW");
					pnlsouth.add(btnpreview);
					btnpreview.setEnabled(false);
					btnpreview.setActionCommand("PREVIEW");
					btnpreview.addActionListener(this);
				}
				{
					btnedit = new JButton("EDIT");
					pnlsouth.add(btnedit);
					btnedit.setEnabled(false);
					btnedit.setActionCommand("EDIT");
					btnedit.addActionListener(this);
				}
				{
					btncancel = new JButton("CANCEL");
					pnlsouth.add(btncancel);
					btncancel.setEnabled(false);
					btncancel.setActionCommand("CANCEL");
					btncancel.addActionListener(this);
				}
			}
			
		}
		

	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ADDNEW")) {
						
			tblcanvass.setEnabled(true);
			tblcanvass.setEditable(true);
			modelcanvassing.setEditable(true);
			tblcanvass_itemrequested.setEnabled(true);
			tblcanvass_itemrequested.setEditable(true);
			lookupcanvassid.setEnabled(false);
			lookupcanvassid.setValue(setcanvass_id());
			
			enablebuttons(false, true, true, false, true);
		}
		/*if(e.getActionCommand().equals("Add Acct")) {
			lookupcanvassid.setValue(setcanvass_id());
			pgSelect db = new pgSelect();
			display_selected_supplier(modelcanvassing , rowheadercanvass, item_id, selected_supplier, db);
			//getselectedsupp();
			getselectedsupp();
		}*/
		
		if(e.getActionCommand().equals("SAVE")) {
			
			if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to save canvassing?", "Save", JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION) {
				insertcanvassing();
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "Saved!");
				lookupcanvassid.setEnabled(true);
				enablebuttons(false, false, false, false, true);
			}
		}
		
		if(e.getActionCommand().equals("PREVIEW")) {
			
			if(lookupcanvassid.getValue() == "") {}
			else {preview_canvassing(lookuppr_id.getValue(), lookupcanvassid.getValue(), co_name, co_logo);}
		}
		
		if(e.getActionCommand().equals("EDIT")) {
			
		}
		
		if(e.getActionCommand().equals("CANCEL")) {
			
			lookuppr_id.setValue("");
			lookupcanvassid.setValue("");
			FncTables.clearTable(modelcanvass_itemrequested);
			FncTables.clearTable(modelcanvassing);
			btnAddAcct.setEnabled(false);
			tblcanvass.setEnabled(false);
			enablebuttons(false, false, false, false, false);
			cleartable_rowheader_canvassing();
			cleartable_rowheader_itemrequested();
			tblcanvass.setEnabled(false);
			tblcanvass.setEditable(false);
			tblcanvass_itemrequested.setEnabled(false);
			tblcanvass_itemrequested.setEditable(false);
			
		}
	}
	
	public static void cleartable_rowheader_itemrequested() {
		 
		FncTables.clearTable(modelcanvass_itemrequested);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheader_itemrequested.setModel(listModel);//Setting of listModel into rowHeader.
	}
	
	public static void cleartable_rowheader_canvassing() {
		 
		FncTables.clearTable(modelcanvassing );//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheadercanvass.setModel(listModel);//Setting of listModel into rowHeader.
	}
	public static String getcanvass_id() {
		String strsql="select distinct on(canvass_id) canvass_id, pr_id from rf_canvassing_details  where status_id='A'";
		return strsql;
	}
	
	private String setcanvass_id() {
		pgSelect db = new pgSelect();
		String strsql="select  trim(to_char(coalesce(max(canvass_id)::int,0) +1,'000000'))  from  rf_canvassing_details where status_id='A'";
		db.select(strsql);
		return db.Result[0][0].toString();
	}
	
	public static void insertcanvassing() {
		int x  = 0;	
		int y  = 1;
		int rw = tblcanvass.getModel().getRowCount();
		String pr_no = lookuppr_id.getValue();
		String canvass_id = lookupcanvassid.getValue();
		Double nego_price=0.00;
		pgUpdate db = new pgUpdate();
		
		while(x<rw) {
			
			Boolean selected = (Boolean) modelcanvassing.getValueAt(x, 0);
			String supp_id = (String) modelcanvassing.getValueAt(x, 1);
			//String terms = (String)modelcanvassing.getValueAt(x, 3);
			String product_name = (String)modelcanvassing.getValueAt(x, 3);
			Double original_price = Double.parseDouble(modelcanvassing.getValueAt(x, 4).toString()) ;
			try{ nego_price = Double.parseDouble(modelcanvassing.getValueAt(x, 5).toString());}catch(NumberFormatException ex) {nego_price=0.00;};
			String unit = (String)modelcanvassing.getValueAt(x, 6);
			String item_id = (String)modelcanvassing.getValueAt(x, 7);
			String product_id = (String)modelcanvassing.getValueAt(x, 8);
			
		if(selected) {
		
				String strsql="insert into rf_canvassing_details\n" + 
						"(\n" + 
						"rec_id,\n" + 
						"canvass_id,\n" + 
						"pr_id,\n" + 
						"supplier_id,\n" + 
						"item_id,\n" + 
						//"terms,\n" + 
						"product_name,\n" + 
						"status_id,\n" + 
						"original_price,\n" + 
						//"nego_price,\n" + 
						"unit,\n" + 
						"added_by,\n" + 
						"date_added,\n" + 
						"product_id\n" + 
						")\n" + 
						"values\n" + 
						"(\n" + 
						"'"+canvass_id+"',\n" + 
						"'"+canvass_id+"',\n" + 
						"'"+pr_no+"', \n" + 
						"'"+supp_id+"', \n" + 
						"'"+item_id+"', \n" +
						//"'"+terms+"', \n" +
						"'"+product_name+"', \n" +
						"'A', \n" + 
						"'"+original_price+"', \n" + 
						//"'"+nego_price+"', \n" + 
						"'"+unit+"', \n" +
						"'"+UserInfo.EmployeeCode+"', \n" + 
						"now(), \n" +
						"'"+product_id+"'\n"+
						")";
				
				db.executeUpdate(strsql, false);
				System.out.printf("insertpurchase_request: %s",strsql);
				System.out.println("selected= "+selected);
			
			
			}
			x++;
		}
		db.commit();
	}
	
	private ArrayList<String> getselectedsupp() {
		ArrayList<String> listsupplier = new ArrayList<String>();
		
		for(int x=0; x< tblcanvass.getRowCount();x++) {
			Boolean selected= (Boolean) modelcanvassing .getValueAt(x, 0);
			if(selected) {
				
				String supp_id = ((String)modelcanvassing .getValueAt(x, 1)).trim();
				listsupplier.add(supp_id);
				
				System.out.println("supp_id: "+supp_id);
			}
		}
		
		return listsupplier;
	}
	

	
	public void enablebuttons(Boolean addnew, Boolean save, Boolean preview, Boolean edit, Boolean cancel) {
		btnaddnew.setEnabled(addnew);
		btnsave.setEnabled(save);
		btnpreview.setEnabled(preview);
		btnedit.setEnabled(edit);
		btncancel.setEnabled(cancel);
	}
	
	private static void display_selected_supplier(DefaultTableModel modelcanvass, JList rowheader, String item_id,String supplier, pgSelect db) {
		
		/*FncTables.clearTable(modelcanvassing);
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader
		rowheader.setModel(listModel);
		
		String strsql="select \n" + 
				"true,\n" + 
				"supplier_id,\n" + 
				"b.entity_name, \n" + 
				"a.terms, \n" + 
				"product_name, \n" + 
				"unit_cost, \n" + 
				"'' as nego_price,\n" + 
				"unit \n" + 
				"from rf_supplier_products a \n" + 
				"left join  rf_entity b on a.supplier_id=b.entity_id \n" + 
				"where supplier_id in ('"+supplier+"') and \n" + 
				"item_id='"+item_id+"';\n" + 
				"";
		System.out.println(strsql);
		db.select(strsql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelcanvassing.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelcanvassing.getRowCount());
			}
		}*/
	}
	
	public static void display_canvassing(DefaultTableModel modelcanvass, JList rowheader,pgSelect db, String canvass_id) {
		FncTables.clearTable(modelcanvassing);
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader
		rowheader.setModel(listModel);
		
		String strsql="select \n" + 
				"true, \n" + 
				"a.supplier_id,\n" + 
				"b.entity_name,\n" + 
				//"a.terms,\n" + 
				"a.product_name,\n" + 
				"a.original_price,\n" + 
				"a.nego_price,\n" + 
				"a.status_id,\n" + 
				"a.item_id,\n" + 
				"a.product_id \n"+
				"from rf_canvassing_details a   \n" + 
				"left join rf_entity b on a.supplier_id=b.entity_id\n";
				if(canvass_id.equals("")) {strsql = strsql + "where a.item_id='"+item_id+"'";}
				else {strsql = strsql + "where canvass_id ='"+canvass_id +"'";}
				//"where canvass_id ='"+canvass_id +"' \n" + 
				//"and a.status_id='A'";
			
		db.select(strsql);
		System.out.printf("display_canvassing: %s", strsql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelcanvassing.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelcanvassing.getRowCount());
			}
		}
	}
	
	public static void display_supplier_canvassing(DefaultTableModel modelcanvass, JList rowheader, String item_id, pgSelect db ) {
		
		//FncTables.clearTable(modelcanvassing);
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader
		rowheader.setModel(listModel);
		
		String strsql ="select \n" +
				"false,\n"+
				"supplier_id,\n"+
				"b.entity_name,\n" + 
				//"a.terms,\n" + 
				"product_name,\n" + 
				"unit_cost,\n" + 
				"0.00,\n" +
				"unit,\n" +
				"item_id,\n" +
				"product_id\n" +
				"from rf_supplier_products a\n" + 
				"left join  rf_entity b on a.supplier_id=b.entity_id\n" +
				"where item_id='"+item_id+"'";
				//if(canvass_id.equals("")) {strsql = strsql + "where canvass_id='"+item_id+"'"; }
				//else {strsql = strsql + "where canvass_id='"+canvass_id+"'"; }
				
					
		
		db.select(strsql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelcanvassing.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelcanvassing.getRowCount());
			}
		}
	}
	
	private JPanel displayTableNavigation() {
			
			
			  btnAddAcct = new JButton(new
			  ImageIcon(this.getClass().getClassLoader().getResource(
			  "Images/Science-Plus2-Math-icon.png")));
			  btnAddAcct.setActionCommand("Add Acct");
			  btnAddAcct.setToolTipText("Add to cart");
			  btnAddAcct.setEnabled(false); btnAddAcct.addActionListener(this);
			  /*
			  btnRemoveAcct = new JButton(new
			  ImageIcon(this.getClass().getClassLoader().getResource(
			  "Images/Science-Minus2-Math-icon.png")));
			  btnRemoveAcct.setActionCommand("Minus Acct");
			  btnRemoveAcct.setToolTipText("For multiple entry");
			  btnRemoveAcct.setEnabled(false); btnRemoveAcct.addActionListener(this);
			  */
			  
			  JPanel pnl = new JPanel(new GridLayout(1, 1)); 
			  pnl.add(btnAddAcct);
			  //pnl.add(btnRemoveAcct);
			 
	
			return pnl;
	}
	
	public static String getPR() {
		//String strsql="select pr_no,description, unit_id,quantity from rf_purchase_request_details";
		String strsql="select distinct on (pr_no)\n" + 
				"a.pr_no,\n" + 
				"c.entity_name \n" + 
				"from rf_purchase_request_details a\n" + 
				"left join em_employee b on a.requester_id=b.emp_code\n" + 
				"left join rf_entity c on b.entity_id=c.entity_id\n" + 
				"where a.pr_no not in(select pr_id from rf_canvassing_details)";
		
		return strsql;
	}
	
	public static void displaypurchase_request(DefaultTableModel modelitem_requested, JList rowheader, pgSelect db, String pr_no) {
		
		FncTables.clearTable(modelitem_requested);
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader
		rowheader.setModel(listModel);
		
		
		String strsql = "select false,item_id,description, unit_id,quantity from rf_purchase_request_details where pr_no='"+pr_no+"'";
		
		db.select(strsql);
		System.out.printf("displaypurchase_request: %s", strsql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelcanvass_itemrequested.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelcanvass_itemrequested.getRowCount());
			}
		}
	}
	
	private void preview_canvassing( String pr_id, String canvass_id, String co_name, String co_logo) {
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("pr_id", pr_id);
		mapParameters.put("canvass_id", canvass_id);
		mapParameters.put("co_name", co_name);
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("co_logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ co_logo));

		
		FncReport.generateReport("/Reports/rpt_canvassing.jasper", "Canvassing", mapParameters);
		
	}

}
