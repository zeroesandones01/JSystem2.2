package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.PreparedStatement.ParseInfo;
import com.toedter.calendar.JTextFieldDateEditor;

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
import tablemodel.modelwithoutassetnumber;

public class Withoutassetnumber extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String title = "Without Asset Number";
	public static Dimension framesize = new Dimension(700, 450);

	public static JButton btnnew;
	public static JButton btnsave;

	public static JButton btncancel;

	public static _JLookup lookupitem;

	public static JTextField txttotal;

	public static JTextField txtitem;

	private JScrollPane scrollcenter;

	public String item_name;

	public static modelwithoutassetnumber modelNoassetnumber;

	private _JTableMain tblNoassetnumber;

	public static JList rowheaderNoassetnumber;

	public static _JDateChooser dispose_date;

	public static String item_id;

	public static String batch;

	public static _JLookup lookupbatch;

	
	public Withoutassetnumber(){
		super(title, false, true, true, true);
		initGUI();
		displaydisposed();
	}

	public Withoutassetnumber (String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public Withoutassetnumber (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
			JPanel pnlmain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlmain);
			pnlmain.setBorder(new EmptyBorder(5, 5, 5, 5));
			//North
			{
				JPanel pnlnorth = new JPanel(new BorderLayout(5,5));
				pnlmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 130));
				pnlnorth.setBorder(JTBorderFactory.createTitleBorder(null, "Filters", 5));
				{
					JPanel pnllabel = new JPanel(new GridLayout(3, 1, 3, 3)); 
					pnlnorth.add(pnllabel, BorderLayout.WEST);
					pnllabel.setPreferredSize(new Dimension(50, 0));
						{
							JLabel lblitem = new JLabel("Item", JLabel.TRAILING);
							pnllabel.add(lblitem);
						}
						{
							JLabel lbltotal = new JLabel("Total", JLabel.TRAILING);
							pnllabel.add(lbltotal);
						}
				}
				{
					JPanel pnlfields = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlnorth.add(pnlfields, BorderLayout.CENTER);
						{
							lookupitem = new _JLookup();
							pnlfields.add(lookupitem);
							lookupitem.setReturnColumn(0);
							lookupitem.setEditable(false);
							lookupitem.setLookupSQL("select item_id, item_name from tbl_item");
							lookupitem.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null) {
										item_id = data[0].toString();
										item_name = (String)data[1];
										
										txtitem.setText(item_name);
									}
								}
							});
						}
						{
							txttotal = new JTextField();
							pnlfields.add(txttotal);
							txttotal.setEditable(false);
						}
				}
				{
					JPanel pnlnorth_east = new JPanel(new BorderLayout(5,5));
					pnlnorth.add(pnlnorth_east, BorderLayout.EAST);
					pnlnorth_east.setPreferredSize(new Dimension(500, 0));
						{
							JPanel pnlnorth_south1 = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlnorth_east.add(pnlnorth_south1, BorderLayout.CENTER);
							//pnlnorth_south1.setPreferredSize(new Dimension(450, 0));
							{
								txtitem = new JTextField();
								pnlnorth_south1.add(txtitem);
								txtitem.setEditable(false);
							}
							{
								JPanel pnlbatch = new JPanel(new BorderLayout(5, 5));
								pnlnorth_south1.add(pnlbatch);
								{
									JLabel lblbatch = new JLabel("Batch", JLabel.TRAILING);
									pnlbatch.add(lblbatch, BorderLayout.CENTER);
								}
								{
									lookupbatch = new _JLookup();
									pnlbatch.add(lookupbatch, BorderLayout.EAST);
									lookupbatch.setReturnColumn(0);
									lookupbatch.setEditable(false);
									lookupbatch.setPreferredSize(new Dimension(120, 0));
									lookupbatch.setLookupSQL("select distinct on(batch )batch, date_disposed from tbl_asset_disposal where date_disposed is not null");
									lookupbatch.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();
											if(data !=null) {
												batch = (String)data[0];
											}
										}
									});
								}
							}
							{
								JPanel pnldate = new JPanel(new BorderLayout(5, 5));
								pnlnorth_south1.add(pnldate);
								{
									JLabel lbldisposedate = new JLabel("Date Disposed", JLabel.TRAILING);
									pnldate.add(lbldisposedate, BorderLayout.CENTER);
								}
								{
									dispose_date = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnldate.add(dispose_date, BorderLayout.EAST);
									dispose_date.setDateFormatString("yyyy-MM-dd");
									dispose_date.setEditable(false);
									dispose_date.setEnabled(false);
									((JTextFieldDateEditor)dispose_date.getDateEditor()).setEditable(false);
									dispose_date.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dispose_date.setPreferredSize(new Dimension(120, 0));
								}
							}
						}
						{
							JPanel pnlx = new JPanel(new BorderLayout(5,5));
							pnlnorth_east.add(pnlx, BorderLayout.EAST);
							pnlx.setPreferredSize(new Dimension(50, 0));
						}
				}
			}
			
			//Center
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(5,5));
				pnlmain.add(pnlcenter);
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder("List"));
				{
					scrollcenter = new JScrollPane();
					pnlcenter.add(scrollcenter);
					scrollcenter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelNoassetnumber = new modelwithoutassetnumber();
						tblNoassetnumber = new _JTableMain(modelNoassetnumber);
						scrollcenter.setViewportView(tblNoassetnumber);
						modelNoassetnumber .setEditable(false);
						tblNoassetnumber.getTableHeader().setEnabled(false); 
						tblNoassetnumber.setFillsViewportHeight(false);
					}
					{
						rowheaderNoassetnumber = tblNoassetnumber.getRowHeader();
						scrollcenter.setRowHeaderView(rowheaderNoassetnumber);
						scrollcenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			
			//South
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 5, 5, 5));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 30));
				{
					JLabel lblx = new JLabel("");
					pnlsouth.add(lblx);
				}
				{
					btnnew = new JButton("Add New");
					pnlsouth.add(btnnew);
					btnnew.setEnabled(false);
					btnnew.setActionCommand("add");
					btnnew.addActionListener(this);
				}
				{
					btnsave = new JButton("Save");
					pnlsouth.add(btnsave);
					btnsave.setEnabled(false);
					btnsave.setActionCommand("save");
					btnsave.addActionListener(this);
				}
				{
					btncancel = new JButton("Cancel");
					pnlsouth.add(btncancel);
					btncancel.setEnabled(false);
					btncancel.setActionCommand("cancel");
					btncancel.addActionListener(this);
				}
				{
					JLabel lblx = new JLabel("");
					pnlsouth.add(lblx);
				}
			}
			enable_buttons(true, false, false);
			
		}
	}
	//refrest tables and fields
	public static void enable_buttons(Boolean a, Boolean b, Boolean c) {
		btnnew.setEnabled(a);
		btnsave.setEnabled(b);
		btncancel.setEnabled(c);
		
	}
	
	public static void enable_fields(Boolean x) {
		lookupitem.setEditable(x);
		lookupbatch.setEditable(x);
		txttotal.setEditable(x);
		dispose_date.setEditable(x);
		dispose_date.setEnabled(x);
	}
	
	public static void refresh_fields() {
		
		lookupitem.setValue("");
		txtitem.setText("");
		txttotal.setText("");
		lookupbatch.setValue("");
		dispose_date.setDate(null);
	}
	
	//Action performed
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("add")) { 
			refresh_fields();
			enable_fields(true);
			enable_buttons(false, true, true);
		}
		if(e.getActionCommand().equals("save")) { 
			executesave(); 
			enable_fields(false);
			enable_buttons(true, false, true);
			displaydisposed();
		}
		if(e.getActionCommand().equals("cancel")) { 
			enable_fields(false);
			refresh_fields();
			enable_buttons(true, false, true);
		}
	}
	
	//display tables
		public static void displaydisposed() {//used

			FncTables.clearTable(modelNoassetnumber);//Code to clear modelMain.		
			DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
			rowheaderNoassetnumber.setModel(listModel);//Setting of DefaultListModel into rowHeader.
			
			
			
			String strsql="select \n" + 
					"b.item_name,\n" + 
					"a.batch_no,\n" + 
					"a.serial_no,\n" + 
					"a.model_no,\n" + 
					"description,\n" + 
					"dept_id,\n" + 
					"date_disposed,\n" + 
					"disposed_by,\n" + 
					"a.line_no\n" + 
					"from \n" + 
					"tbl_dispose_withoutassetno a\n" + 
					"left join tbl_item b on a.item_id::int = b.item_id";
			
			System.out.println("Display disposed: "+strsql);
			pgSelect db = new pgSelect();
			db.select(strsql);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					modelNoassetnumber.addRow(db.getResult()[x]);
					listModel.addElement(modelNoassetnumber.getRowCount());
				}
			}
		}
	//save/insert
	private void executesave() {
		
		pgUpdate dbExec = new pgUpdate();
		
		String tot = txttotal.getText();	
		int y  = Integer.parseInt(tot);
		String item_id = lookupitem.getValue();
		String disposedate = dispose_date.getDateString();
		int line_no = 1;
		
		for(int x  = 0; x<= y -1; x++) {
			
			String strsql="insert into tbl_dispose_withoutassetno\n" + 
					"(\n" + 
					"item_id,\n" +
					"batch_no,\n" + 
					"status_id,\n" + 
					"serial_no,\n" + 
					"model_no,\n" + 
					"description,\n" + 
					"dept_id,\n" + 
					"date_created,\n" + 
					"created_by,\n" + 
					"date_disposed,\n" + 
					"disposed_by,\n" +
					"line_no, \n"+
					"recon_assetno \n"+
					") \n"+
					"values\n" + 
					"(\n" + 
					"'"+item_id+"',\n" + //item_id
					"'"+batch+"',\n" +  //batch
					"'I',\n" +  //status_id
					"'',\n" + //serial_no
					"'',\n" +  //model_no
					"'',\n" +  //description
					"'',\n" +  //dept_id
					"now(),\n" + //date_created
					"'"+UserInfo.EmployeeCode+"',\n" + 
					"'"+disposedate+"',\n" +  //date_disposed
					"'"+UserInfo.EmployeeCode+"',\n" +  //disposed_by
					"'"+line_no+"',\n"+ //line_no
					"''\n"+ //recon_assetno
					")";
			
			System.out.println("executesave: "+strsql);
			dbExec.executeUpdate(strsql, false);
			line_no++;
		}
		
		dbExec.commit();
		
		
	}
	
	
	
	
	
	
	
}
