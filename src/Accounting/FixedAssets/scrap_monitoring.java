package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.poi.ss.formula.ptg.TblPtg;
import org.odftoolkit.odfdom.dom.attribute.grddl.GrddlTransformationAttribute;

import com.toedter.calendar.JDateChooser;

import Accounting.FixedAssets.procurement_payment_processing.PopupTriggerListener_panel2;
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
import tablemodel.modelscrap_monitoring;

public class scrap_monitoring extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2474925958473350486L;
	
	public static String title="Scrap Monitoring";
	public static Dimension framesize= new Dimension(750,500);
	private JPanel pnlmain;

	private static JTextField txtitem_source;
	private static JTextField txtdescription;
	private static JTextField txtscrap_name;
	private static JTextField assetname;
	
	private static JDateChooser date_added;
	private static JButton btnadd;
	private static JButton btnedit;
	private static JButton btncancel;
	private static JButton btnsave;
	
	private JScrollPane jscrollscrap_monitoring;
	private static modelscrap_monitoring modelscrap;
	private static _JTableMain tblscrap;
	private static JList rowheaderscrap;

	private JPopupMenu menu1;
	private JMenuItem mniuse_scrap;
	private JMenuItem mni_1;
	private JMenuItem mni_2;
	
	protected String custodian;
	protected String co_id;
	protected String custodian_name;
	protected String item_source_name;
	protected  String asset_name;
	protected Integer asset_no;
	
	private static _JLookup lookupitem_source;
	private static _JLookup lookupasset_no;
	private static _JLookup lookupscrap_itemid;

	
	
	
	public scrap_monitoring(){
		super(title, false, true, true, true);
		initGUI();
		enable_buttons(true, false, true, false);
		displayscrap();
	}
	public scrap_monitoring (String title) {
		super(title, true, true, true, true);
		initGUI();
	}
	
	public scrap_monitoring (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
				menu1 = new JPopupMenu("Popup");
				//JSeparator sp = new JSeparator();
				mniuse_scrap = new JMenuItem("Use Scrap");
				mni_1 = new JMenuItem("");
				mni_2 = new JMenuItem("");
				
				menu1.add(mni_1);
				menu1.add(mni_2);
				menu1.add(mniuse_scrap);
				
			
				mniuse_scrap.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select asset.");
						lookupasset_no.setEditable(true);
						
					}
				});
				
			}
			{
				JPanel pnlnorth = new JPanel(new BorderLayout(5,5));
				pnlmain.add(pnlnorth,BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 150));
				{
					JPanel pnlnorth_west = new JPanel(new BorderLayout(5,5));
					pnlnorth.add(pnlnorth_west, BorderLayout.WEST);
					pnlnorth_west.setPreferredSize(new Dimension(500, 0));
					{
						JPanel pnllabels = new JPanel(new GridLayout(5, 1, 5, 5));
						pnlnorth_west.add(pnllabels, BorderLayout.WEST);
						pnllabels.setPreferredSize(new Dimension(80, 0));
						{
							JLabel lblitem_source = new JLabel("Scrap Item ID");
							pnllabels.add(lblitem_source);
						}
						{
							JLabel lblscrap_item_id= new JLabel("Scrap Name");
							pnllabels.add(lblscrap_item_id);
						}
						{
							JLabel lblscrap_name = new JLabel("Description");
							pnllabels.add(lblscrap_name);
						}
						{
							JLabel lblitem_source = new JLabel("Item Source");
							pnllabels.add(lblitem_source);
						}
						{
							JLabel custodian = new JLabel("Use To");
							pnllabels.add(custodian);
						}
					}
					{
						JPanel pnlfields = new JPanel(new GridLayout(5, 1, 5, 5));
						pnlnorth_west.add(pnlfields, BorderLayout.CENTER);
						{
							JPanel pnlscrapitemid = new JPanel(new BorderLayout(5,5));
							pnlfields.add(pnlscrapitemid);
							{
								lookupscrap_itemid = new _JLookup();
								lookupscrap_itemid.setEditable(true);
								lookupscrap_itemid.setReturnColumn(0);
								lookupscrap_itemid.setLookupSQL(getscrap_id());
								pnlscrapitemid.add(lookupscrap_itemid, BorderLayout.WEST);
								lookupscrap_itemid.setPreferredSize(new Dimension(100, 0));
								lookupscrap_itemid.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null) {
											
											display_details(lookupscrap_itemid.getValue());
											enable_buttons(false, true, true, false);
											//modelscrap.setEditable(false);
											tblscrap.setEnabled(true);
										}
									}
								});
							}
						}
						{
							txtscrap_name = new JTextField();
							txtscrap_name.setEditable(false);
							pnlfields.add(txtscrap_name);
						}
						{
							JPanel pnlassetno = new JPanel(new BorderLayout(5,5));
							pnlfields.add(pnlassetno);
							{
								txtdescription = new JTextField();
								txtdescription.setEditable(false);
								pnlassetno.add(txtdescription, BorderLayout.CENTER);
								//pnlassetno.add(txtdescription, BorderLayout.WEST);
								//txtasset_no.setPreferredSize(new Dimension(150, 0));
							}
						}
						{
							JPanel pnlitemsource = new JPanel(new BorderLayout(5,5));
							pnlfields.add(pnlitemsource);
							{
								lookupitem_source = new _JLookup();
								lookupitem_source.setReturnColumn(0);
								lookupitem_source.setEditable(false);
								lookupitem_source.setLookupSQL(getitem_source());
								pnlitemsource.add(lookupitem_source, BorderLayout.WEST);
								lookupitem_source.setPreferredSize(new Dimension(100, 0));
								lookupitem_source.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data =((_JLookup) event.getSource()).getDataSet();
										if(data != null) {
											item_source_name = (String) data[1];
											
											txtitem_source.setText(item_source_name);
										}
									}
								});
							}
							{
								txtitem_source = new JTextField(20);
								txtitem_source.setEditable(false);
								pnlitemsource.add(txtitem_source, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlcustodian = new JPanel(new BorderLayout(5,5));
							pnlfields.add(pnlcustodian);
							{
								lookupasset_no = new _JLookup();
								lookupasset_no.setReturnColumn(0);
								lookupasset_no.setEditable(false);
								lookupasset_no.setLookupSQL(getasset_no());
								lookupasset_no.setPreferredSize(new Dimension(100, 0));
								pnlcustodian.add(lookupasset_no, BorderLayout.WEST);
								lookupasset_no.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data =((_JLookup) event.getSource()).getDataSet();
										if(data != null) {
											//custodian_name = (String)data[0];
											//custodian = (String)data[1];
											//co_id = (String)data[2];
											asset_no = (Integer)data[0];
											asset_name =  (String)data[1];
											
											assetname.setText(asset_name);
											
											if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Would you like to save?", "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
												
												updatescrap();
												JOptionPane.showMessageDialog(getTopLevelAncestor(), "Done!");
												lookupasset_no.setEditable(false);
											}
										}
									}
								});
							}
							{
								assetname = new JTextField();
								assetname.setEditable(false);
								pnlcustodian.add(assetname, BorderLayout.CENTER);
							}
						}
					}
				}
				{
					JPanel pnlnorth_east = new JPanel(new BorderLayout(5,5));
					pnlnorth.add(pnlnorth_east, BorderLayout.EAST);
					pnlnorth_east.setPreferredSize(new Dimension(190, 0));
					{
						JPanel pnldate = new JPanel(new BorderLayout(5,5));
						pnlnorth_east.add(pnldate, BorderLayout.NORTH);
						pnldate.setPreferredSize(new Dimension(0, 30));
						{
							JLabel lbldate = new JLabel("Date", JLabel.TRAILING);
							pnldate.add(lbldate, BorderLayout.WEST);
							lbldate.setPreferredSize(new Dimension(70, 0));
						}
						{
							date_added = new _JDateChooser("MM/dd/yyyy","##/##/####",'_');
							date_added.setEnabled(false);
							pnldate.add(date_added, BorderLayout.CENTER);
									
						}
					}
				}
			}
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(5,5));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder("Details"));
				{
					jscrollscrap_monitoring = new JScrollPane();
					pnlcenter.add(jscrollscrap_monitoring);
					jscrollscrap_monitoring.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelscrap = new modelscrap_monitoring();
						tblscrap = new _JTableMain(modelscrap);
						jscrollscrap_monitoring.setViewportView(tblscrap);
						
						tblscrap.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblscrap.getColumnModel().getColumn(1).setPreferredWidth(300);
						tblscrap.getColumnModel().getColumn(2).setPreferredWidth(300);
						
						tblscrap.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if(e.getValueIsAdjusting()) {
									
									int row = tblscrap.getSelectedRow();
									String scrap_id = (String) modelscrap.getValueAt(row, 0);
									display_details(scrap_id);
								}
							}
						});
						tblscrap.addMouseListener(new PopupTriggerListener_tblscrap());
					}
					{
						rowheaderscrap = tblscrap.getRowHeader();
						jscrollscrap_monitoring.setRowHeaderView(rowheaderscrap);
						jscrollscrap_monitoring.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 6, 5, 5));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0,35));
				{
					JLabel lblextra1 = new JLabel("");
					pnlsouth.add(lblextra1);
				}
				{
					btnadd = new JButton("ADD");
					pnlsouth.add(btnadd);
					btnadd.setEnabled(false);
					btnadd.setActionCommand("add");
					btnadd.addActionListener(this);
				}
				{
					btnedit = new JButton("EDIT");
					btnedit.setActionCommand("edit");
					pnlsouth.add(btnedit);
					btnedit.setEnabled(false);
					btnedit.addActionListener(this);
				}
				{
					btnsave = new JButton("SAVE");
					pnlsouth.add(btnsave);
					btnsave.setActionCommand("save");
					btnsave.setEnabled(false);
					btnsave.addActionListener(this);
				}
				{
					btncancel = new JButton("CANCEL");
					
					btncancel.setActionCommand("cancel");
					btncancel.setEnabled(false);
					pnlsouth.add(btncancel);
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
		
		if(e.getActionCommand().equals("add")) {
			enable_buttons(false, false, true, true);
			
			lookupscrap_itemid.setValue(generate_scrap_id());
			date_added.setDate(FncGlobal.getDateToday());
			
			lookupitem_source.setEditable(true);
			lookupitem_source.setValue("");
			txtitem_source.setEditable(true);
			txtitem_source.setText("");
			txtscrap_name.setEditable(true);
			txtscrap_name.setText("");
			txtdescription.setEditable(true);
			txtdescription.setText("");
			
			//modelscrap.setEditable(false);
			tblscrap.setEnabled(false);
		}
		
		if(e.getActionCommand().equals("edit")){
			
			enable_buttons(false, false, true, true);
		}
		
		if(e.getActionCommand().equals("save")) {
			if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Save Item!", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
				insert_scrap();
				lookupitem_source.setEditable(false);
				txtitem_source.setEditable(false);
				lookupscrap_itemid.setEditable(true);
				txtscrap_name.setEditable(false);
				txtdescription.setEditable(false);
				//modelscrap.setEditable(true);
				tblscrap.setEnabled(true);
				enable_buttons(true, true, true, false);
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "Saved!");
				displayscrap();
				
			}
		}
		
		if(e.getActionCommand().equals("cancel")){
			
			lookupitem_source.setValue("");
			lookupitem_source.setEditable(false);
			txtitem_source.setText("");
			txtitem_source.setEditable(false);
			lookupscrap_itemid.setValue("");
			lookupscrap_itemid.setEditable(true);
			txtscrap_name.setText("");
			txtscrap_name.setEditable(false);
			txtdescription.setText("");
			txtdescription.setEditable(false);
			date_added.setDate(null);
			enable_buttons(true, false, true, false);
			//modelscrap.setEditable(true);
			tblscrap.setEnabled(true);
			lookupasset_no.setEditable(false);
			lookupasset_no.setValue("");
			assetname.setText("");
		}
	}
	
	class PopupTriggerListener_tblscrap extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				//int column = tblscrap.getSelectedColumn();
				menu1.show(ev.getComponent(), ev.getX(), ev.getY());
				/*if(column == 0  ) {
					
					menu1.show(ev.getComponent(), ev.getX(), ev.getY());
					
				}else {
					
					menu1.setEnabled(false);
				}*/
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu1.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}
	public static String getCustodian(){
		String strsql="select b.entity_name, a.emp_code, e.co_id,e.company_name\n" + 
				"from  em_employee a \n" + 
				"left join rf_entity b ON a.entity_id=b.entity_id \n" + 
				"left join mf_company e on a.co_id=e.co_id\n" + 
				"where e.co_id  not in('03')";
		
		return strsql;
	}
	
	public static String getasset_no(){
		String strsql="select asset_no, asset_name from tbl_asset where status='A'";
		
		return strsql;
	}
	
	public static void enable_buttons(Boolean add, Boolean edit, Boolean cancel, Boolean save) {
		
		btnadd.setEnabled(add);
		btnedit.setEnabled(edit);
		btncancel.setEnabled(cancel);
		btnsave.setEnabled(save);
	}
	
	public static String getitem_source() {
		
		String strsql="select asset_no,asset_name from tbl_asset where remarks='DISPOSED'";
		return strsql;
	}
	
	public static String getscrap_id() {
		
		String strsql=" select scrap_id, scrap_name from tbl_scrap";
		return strsql;
	}
	
	public static String generate_scrap_id() {
		
		pgSelect db = new pgSelect();
		String strsql="select  trim(to_char(coalesce(max(scrap_id)::int,0) +1,'000000'))  from  tbl_scrap";
		db.select(strsql);
		return db.Result[0][0].toString();
	}
	public static void insert_custodian() {
		 pgUpdate db = new pgUpdate();
		 
		 String strsql="";
		 
		 db.executeUpdate(strsql, false);
		 db.commit();
	}
	
	public static void updatescrap() {
		pgUpdate db = new pgUpdate();
		
		String strsql="update tbl_scrap set asset_no='"+lookupasset_no.getValue()+"' where scrap_id='"+lookupscrap_itemid.getValue()+"' and status_id='A'";
		System.out.println("updatescrap: "+strsql);
		db.executeUpdate(strsql, false);
		db.commit();
	}
	
	public static void insert_scrap() {
		 int x = 0;
		 int rc = tblscrap.getModel().getRowCount();
		 String item_source = lookupitem_source.getValue();
		 String scrap_id = lookupscrap_itemid.getValue();
		 String scrap_name = txtscrap_name.getText();
		 String description = txtdescription.getText();
		 
		 pgUpdate db = new pgUpdate();
			 
			 String strsql="insert into tbl_scrap\n" + 
			 		"(\n" + 
			 		"	scrap_id,\n" + 
			 		"	item_source,\n" + 
			 		"	scrap_name,\n" + 
			 		"	scrap_description,\n" + 
			 		"	status_id, \n"+
			 		"	date_added,\n" + 
			 		"	added_by\n" + 
			 		")\n" + 
			 		"values\n" + 
			 		"(\n" + 
			 		"	'"+scrap_id+"',\n" + 
			 		"	'"+item_source+"',\n" + 
			 		"	'"+scrap_name+"',\n" + 
			 		"	'"+description+"',\n" + 
			 		"	'A',\n"+
			 		"	now(),\n" + 
			 		"	'"+UserInfo.EmployeeCode+"'\n" + 
			 		")";
			 
			 System.out.println("insert_scrap: "+strsql);
			 db.executeUpdate(strsql, false);
		 
		db.commit();
	}
	
	public static void displayscrap() {
		FncTables.clearTable(modelscrap);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderscrap.setModel(listModel);//Setting of listModel into rowHeader.
		
		String strsql="select scrap_id, scrap_name, scrap_description from tbl_scrap";
		
		pgSelect db = new pgSelect();
		db.select(strsql);
		
		if(db.isNotNull()) {
			
			for(int x=0; x < db.getRowCount(); x++)
			{
				modelscrap.addRow(db.getResult()[x]);
				listModel.addElement(modelscrap.getRowCount());
			}
		}
	}
	
	public static void display_details(String scrap_id) {
		
		String strsql="select item_source,  \n" + 
				"b.asset_name,\n" + 
				"a.scrap_id,\n" + 
				"a.scrap_name,\n" + 
				"a.scrap_description,\n" + 
				"a.date_added,\n" + 
				"a.asset_no,\n"+
				"c.asset_name \n"+
				"from tbl_scrap a\n" + 
				"left join tbl_asset b on a.item_source=b.asset_no::varchar\n" + 
				"left join tbl_asset c on a.asset_no=c.asset_no::varchar\n"+
				"where a.scrap_id='"+scrap_id+"'";
		
		System.out.println("display_details: "+strsql);
		pgSelect db = new pgSelect();
		db.select(strsql);
		
		if(db.isNotNull()) {
			
			lookupitem_source.setValue((String) db.getResult()[0][0]);
			txtitem_source.setText((String) db.getResult()[0][1]);
			lookupscrap_itemid.setValue((String) db.getResult()[0][2]);
			txtscrap_name.setText((String) db.getResult()[0][3]);
			txtdescription.setText((String) db.getResult()[0][4]);
			date_added.setDate((Date) db.getResult()[0][5]);
			lookupasset_no.setValue(db.getResult()[0][6].toString());
			assetname.setText(db.getResult()[0][7].toString());
		}
	}
	
}
