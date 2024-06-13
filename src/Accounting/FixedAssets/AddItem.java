package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
//import fixedAsset.classes.FrmAddItem_DB;
//import fixedAsset.classes.FrmAddItem_DB;
import interfaces._GUI;

public class AddItem extends _JInternalFrame implements ActionListener, _GUI {
	
	private static final long serialVersionUID = 652923134846245300L;
	
	public static String title = "Add Item";
	public static Dimension framesize= new Dimension(600, 500);
	//public static Dimension framesize= new Dimension(500, 450);
	//public static Dimension framesize = new Dimension(922, 630);//510, 250
	public static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	public static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	
	
	public AddItem(){
		super(title, true, true, true, true);
		initGUI();
		displayItem();
	}
	public AddItem(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public AddItem(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlCenter;
	private JLabel lblCategory;
	private JLabel lblItemid;
	private JLabel lblItemname;
	private JPanel pnlButtons;
	
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnReset;
	
	private JTextField txtItemname;
	private JTextField txtItemid1;
	private JTextField txtCategory; _JTableMain tblItem;

	private JScrollPane scrollItem;
	private static DefaultTableModel modelItem;
	protected ButtonGroup grpNE = new ButtonGroup();
	
	private _JLookup lookupCategory;
	private JPanel pnlXtra2;
	private JPanel pnl2;
	private JPanel pnl3;
	private JPanel pnl4;
	private JPanel pnlxtra;
	private JPanel pnlSouth1;
	private JPanel pnlSouth2;

	protected String category_name;
	private static JList rowheaderitem;


	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(framesize);
		this.setPreferredSize(framesize);
		this.setLayout(new BorderLayout());
		{
			pnlMain= new JPanel(new BorderLayout(10, 10));
			getContentPane().add(pnlMain);
			//pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
			//pnlMain.setBorder(new LineBorder(new java.awt.Color(0,0,0),1,false));
			

			//pnlMain.setPreferredSize(new Dimension(250, 100));
			{
				pnlNorth= new JPanel(new BorderLayout(10, 10));
				pnlMain.add(pnlNorth, BorderLayout.CENTER);
				//pnlNorth.setBackground(Color.RED);
				//pnlNorth.setPreferredSize(new Dimension(400, 300));
				pnlNorth.setPreferredSize(new Dimension(0, 400));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Item Details"));
				{
					scrollItem = new JScrollPane();
					pnlNorth.add(scrollItem,BorderLayout.CENTER);
					{
						modelItem= new tablemodel.modelItem();
						tblItem= new _JTableMain(modelItem);
						scrollItem.setViewportView(tblItem);
						
						tblItem.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								
								if(!e.getValueIsAdjusting()){
									try {
										//System.out.println("Dumaan dito");
										int row = tblItem.getSelectedRow();
										String item_id= (String) modelItem.getValueAt(row, 0);
										//System.out.println(row);
										//System.out.println(item_id);
										
										displayitemdetails(item_id);
										btnState(true, true, false, false);
									}catch (ArrayIndexOutOfBoundsException ex) { }
							}
							}
						});
						{
							//tblItem.setFillsViewportHeight(false);
							//tblItem.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
							tblItem.getColumnModel().getColumn(0).setPreferredWidth(82);//item_id
							tblItem.getColumnModel().getColumn(1).setPreferredWidth(175);//item_name
							tblItem.getColumnModel().getColumn(2).setPreferredWidth(175);//category
							tblItem.getColumnModel().getColumn(3).setPreferredWidth(82);//category_id
						}
					}
					{
						rowheaderitem= tblItem.getRowHeader();
						scrollItem.setRowHeaderView(rowheaderitem);
						scrollItem.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				/*{
					pnlXtra2= new JPanel();
					pnlNorth.add(pnlXtra2, BorderLayout.SOUTH);
					pnlXtra2.setPreferredSize(new Dimension(0, 10));
				}*/
			}
			
			{
				pnlCenter= new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlCenter, BorderLayout.SOUTH);
				pnlCenter.setPreferredSize(new Dimension(0,160));
				//pnlCenter.setBorder(lineBorder);
				//pnlCenter.setBackground(Color.BLACK);
				pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Adding Details"));
				{
					pnlSouth1= new JPanel(new GridLayout(3, 1,3,3));
					pnlCenter.add(pnlSouth1, BorderLayout.CENTER);
					{
						pnl2= new JPanel(new BorderLayout(5,5));
						pnlSouth1.add(pnl2);
						//pnl2.setBackground(Color.blue);
						pnl2.setPreferredSize(new Dimension(0, 20));
						{
							lblCategory= new JLabel("Category");
							pnl2.add(lblCategory,BorderLayout.WEST);
							lblCategory.setPreferredSize(new Dimension(75, 0));
							//lblCategory.setBorder(lineBorder);
						}
						{
							lookupCategory=new _JLookup(null, "", 0);
							pnl2.add(lookupCategory, BorderLayout.CENTER);
							lookupCategory.setPreferredSize(new Dimension(75, 0));
							lookupCategory.setLookupSQL(getCategory());
							lookupCategory.setEditable(false);
							lookupCategory.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if(data != null) {
										category_name = (String)data [1];
										txtCategory.setText(category_name);
									}
								}
							});
						}
						{
							txtCategory= new JTextField();
							pnl2.add(txtCategory, BorderLayout.EAST);
							txtCategory.setPreferredSize(new Dimension(350,0));
						}
					}
					{
						pnl3= new JPanel(new BorderLayout(5,5));
						pnlSouth1.add(pnl3);
						//pnl3.setBackground(Color.CYAN);
						pnl3.setPreferredSize(new Dimension(0, 20));
						
						{
							lblItemid= new JLabel("Item ID");
							pnl3.add(lblItemid, BorderLayout.WEST);
							lblItemid.setPreferredSize(new Dimension(75, 0));
							//lblItemid.setBorder(lineBorder);
						}
						{
							txtItemid1= new JTextField();
							txtItemid1.setEditable(false);
							txtItemid1.setHorizontalAlignment(JTextField.CENTER);
							pnl3.add(txtItemid1, BorderLayout.CENTER);
						}
						{
							pnlxtra= new JPanel();
							pnl3.add(pnlxtra, BorderLayout.EAST);
							pnlxtra.setPreferredSize(new Dimension(350, 0));
						}
					}
					{
						pnl4=new JPanel(new BorderLayout(5,5));
						pnlSouth1.add(pnl4);
						//pnl4.setBackground(Color.green);
						{
							lblItemname= new JLabel("Item Name");
							pnl4.add(lblItemname, BorderLayout.WEST);
							lblItemname.setPreferredSize(new Dimension(75, 0));
							//lblItemname.setBorder(lineBorder);
						}
						{
							txtItemname= new JTextField();
							txtItemname.setEditable(false);
							pnl4.add(txtItemname, BorderLayout.CENTER);
						}
					}
				}
				}
				{
					pnlSouth2= new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlSouth2, BorderLayout.SOUTH);
					{
						pnlButtons=new JPanel(new GridLayout(1, 4, 5, 5));
						pnlSouth2.add(pnlButtons);
						{
							btnNew= new JButton("New");
							pnlButtons.add(btnNew);
							grpNE.add(btnNew);
							btnNew.setPreferredSize(new Dimension(100, 30));
							btnNew.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent arg0) {
									newState();
								}
							});
						}
						{
							btnEdit= new JButton("Edit");
							pnlButtons.add(btnEdit);
							grpNE.add(btnEdit);
							btnEdit.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									editState();
								}
							});
						}
						{
							btnSave= new JButton("Save");
							pnlButtons.add(btnSave);
							btnSave.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if(toadd()){
										//int tosave=JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to add this item?","save",JOptionPane.YES_NO_OPTION);
										int tosave;
										
										if (grpNE.isSelected(btnNew.getModel())){
											if (!_AddItem.checkItemIfExist(txtItemname.getText().trim().replace("'", "''").toUpperCase())) {
												tosave=JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to add this item?","save",JOptionPane.YES_NO_OPTION);
												if(tosave==JOptionPane.YES_OPTION){
													_AddItem.addItem(txtItemid1.getText(), txtItemname.getText().replace("'", "''").toUpperCase(), lookupCategory.getText());
													//JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Done!");
													JOptionPane.showMessageDialog(getTopLevelAncestor(), "Done!");
													resetState();
												}
											} else {
												JOptionPane.showMessageDialog(getTopLevelAncestor(), "Item is existing.","Save",JOptionPane.WARNING_MESSAGE);
											}
										}

										if (grpNE.isSelected(btnEdit.getModel())) {
											tosave=JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to update this item?","save",JOptionPane.YES_NO_OPTION);
											if(tosave==JOptionPane.YES_OPTION){
												_AddItem.updateItem(txtItemid1.getText(), txtItemname.getText().replace("'", "''").toUpperCase(), lookupCategory.getText());
												resetState();
											}
											
										}
									} else {
										JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please fill-up required fields.","Save",JOptionPane.WARNING_MESSAGE);
									}
								}
							});
						}
						{
							btnReset= new JButton("Reset");
							pnlButtons.add(btnReset);
							btnReset.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									resetState();
									
								}
							});
						}
					}
				}
		}

	}
	protected Boolean toadd(){
		if(lookupCategory.getText().equals("") || txtItemid1.getText().equals("") || txtItemname.getText().equals("")){
			return false;
		}else{
			return true;
		}
	}
	protected void editState(){
		grpNE.setSelected(btnEdit.getModel(), true);
		lookupCategory.setEditable(true);
		txtItemname.setEditable(true);
		txtItemid1.setEditable(false);
		btnState(false, false, true, true);
	}
	protected void resetState(){
		displayItem();
		tblItem.setEnabled(true);
		lookupCategory.setEditable(false);
		lookupCategory.setText("");
		txtCategory.setText("");
		txtCategory.setEditable(false);
		txtItemid1.setText("");
		txtItemid1.setEditable(false);
		txtItemname.setText("");
		txtItemname.setEditable(false);
		btnState(true, false, false, false);
	}
	public   void displayitemdetails(String item_id){
		
		pgSelect db= new pgSelect();
		
		String strSQL="select a.item_id::varchar,\n" + 
				"a.item_name,\n" + 
				"b.category_name,\n" + 
				"b.category_id::varchar\n" + 
				"from tbl_item a\n" + 
				"left join tbl_category  b on a.category_id=b.category_id\n" + 
				"where item_id="+item_id+"";
		db.select(strSQL);
		if(db.isNotNull()){
			lookupCategory.setValue((String) db.getResult()[0][3]);
			txtCategory.setText((String) db.getResult()[0][2]);
			txtItemid1.setText((String) db.getResult()[0][0]);
			txtItemname.setText((String) db.getResult()[0][1]);
		}
		
	}
	protected void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnReset.setEnabled(sCancel);
	}
	protected void newState(){
		grpNE.setSelected(btnNew.getModel(), true);
		//grpNE.setSelected(btnNew.getModel(), true);
		//rowSM.removeListSelectionListener(rowLSL);
		tblItem.setEnabled(false);
		lookupCategory.setEditable(true);
		txtCategory.setText("");
		lookupCategory.setText("");
		//lookupCategory.setToTextField(false);
		txtItemid1.setText(_AddItem.getNewItemID());
		txtItemid1.setEditable(false);
		txtItemname.setText("");
		txtItemname.setEditable(true);
		txtItemname.setEditable(true);

		//lblCategory.setFont(italicFont);
		//lblCategory.setForeground(redFont);
		//lblItemname.setFont(italicFont);
		//lblItemName.setForeground(redFont);
		btnState(false, false, true, true);
	}
	
	public static String getCategory(){
		return "select category_id as \"Category ID\", category_name as \"Category Name\" from tbl_category order by category_name";
	}
	
	public static void displayItem(){
		
		pgSelect db = new pgSelect();
		
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderitem.setModel(listModel);//Setting of listModel into rowHeader.
		
		String strSQL =
			"select to_char(a.item_id,'FM000000'), \n" +
			"a.item_name, \n" +
			"b.category_name, \n" +
			"b.category_id \n" +
			"from tbl_item a, \n" +
			"tbl_category b \n" +
			"where a.category_id = b.category_id \n" +
			"order by a.item_id \n";
		
		db.select(strSQL);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelItem.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelItem.getRowCount());
			}
		}

		/*try{
			functions.FncSelectRecords.select(strSQL);
			functions.FncSelectRecords.rs.last();
			int rec = functions.FncSelectRecords.rs.getRow();
			functions.FncSelectRecords.rs.beforeFirst();
			int x = 0;

			while (functions.FncSelectRecords.rs.next()) {
				if (table.getModel() instanceof DefaultTableModel) {
					((DefaultTableModel) table.getModel()).setRowCount(rec);

					table.setValueAt(functions.FncSelectRecords.rs.getString(1), x, 0);//item_id
					table.setValueAt(functions.FncSelectRecords.rs.getString(2), x, 1);//item_name
					table.setValueAt(functions.FncSelectRecords.rs.getString(3), x, 2);//category
					table.setValueAt(functions.FncSelectRecords.rs.getString(4), x, 3);//category_id
					x++;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}*/
		//functions.FncSelectRecords.disconnect();
	}
}
