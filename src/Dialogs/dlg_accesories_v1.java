package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Accounting.FixedAssets.panelAssetInformation;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_accesories;



public class dlg_accesories_v1 extends JDialog implements ActionListener, _GUI {
	
	private Dimension size = new Dimension(600, 330);
	private String title = "Asset Accesories";
	private JPanel pnlmain;
	private JPanel pnlcenter;
	private JPanel pnlsouth;
	private static JButton btnadd;
	private static JButton btnedit;
	private static JButton btnretire;
	private static JButton btndispose;
	private static JButton btncancel;
	private JScrollPane scrollaccesories;
	public static model_accesories modelacc;
	public static _JTableMain tblacc;
	public static JList rowheader_acc;
	public Integer a;
	public Integer b;
	private static JButton btnsave;
	
	public dlg_accesories_v1() {
		initGUI();
		//displayaccesories();
	}
	
	/*public dlg_accesories_v1(Dialog owner) {
		super(owner);
		initGUI();
	}*/
	
	public dlg_accesories_v1(Frame owner, String title, panelAssetInformation panelAssetInformation ) {
		super(owner, title);
		initGUI();
		displayaccesories();
	}
	
	public dlg_accesories_v1(Frame owner) {
		super(owner);
		initGUI();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8267313166657874118L;

	@Override
	public void initGUI() {
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setTitle(title);
		{
			pnlmain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlmain);
			//pnlmain.setBackground(Color.blue);
			{
				pnlcenter=new JPanel(new BorderLayout(3, 3));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				//pnlcenter.setBackground(Color.cyan);
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder("List of Accesories"));
				{
					scrollaccesories= new JScrollPane();
					pnlcenter.add(scrollaccesories, BorderLayout.CENTER);
					scrollaccesories.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelacc= new model_accesories();
						tblacc= new _JTableMain(modelacc);
						modelacc.setEditable(false);
						scrollaccesories.setViewportView(tblacc);
						tblacc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								btnadd.setEnabled(true);
								if (!e.getValueIsAdjusting()) {
									//int row = tblacc.getSelectedRow();
									//String asset_no = (String) modelacc.getValueAt(row, 1);
									//String sub_asset_no = (String) modelacc.getValueAt(row, 2);
									btnretire.setEnabled(true);
									btndispose.setEnabled(true);
									
								}
							}
						});
					}
					{
						rowheader_acc= tblacc.getRowHeader();
						scrollaccesories.setRowHeaderView(rowheader_acc);
						scrollaccesories.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						
					}
				}
			}
			{
				pnlsouth = new JPanel(new GridLayout(1, 5, 3, 3));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0,30));
				//pnlsouth.setBackground(Color.darkGray);
				{
					btnadd= new JButton("ADD");
					pnlsouth.add(btnadd);
					btnadd.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							modelacc.clear();
							displaydetails();
							disablebuttons(false, false, true, false, false, true);
							
						}
					});
				}
				{
					btnedit=new JButton("Edit");
					pnlsouth.add(btnedit);
					btnedit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							disablebuttons(false, false, true, false, false, true);
						}
					});
					
				}
				{
					btnsave= new JButton("Save");
					pnlsouth.add(btnsave);
					btnsave.setActionCommand("Save");
					btnsave.addActionListener(this);
					
				}
				{
					btnretire=new JButton("Retire");
					pnlsouth.add(btnretire);
					btnretire.setActionCommand("Retire");
					btnretire.setEnabled(false);
					btnretire.addActionListener(this);
				}
				{
					btndispose=new JButton("Dispose");
					pnlsouth.add(btndispose);
					btndispose.setEnabled(false);
					btndispose.setActionCommand("Dispose");
					btndispose.addActionListener(this);
				}
				{
					btncancel=new JButton("Cancel");
					pnlsouth.add(btncancel);
					btncancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							modelacc.clear();
							displayaccesories();
							disablebuttons(true, true, false, false, false, false);
						}
					});
				}
			}
		}
//		if (UserInfo.EmployeeCode.equals("901280")) {
//			btnadd.setEnabled(true);
//		}else {
//			btnadd.setEnabled(false);
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Save")){
			int insertacc= JOptionPane.showConfirmDialog(null, "Save accesories?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(insertacc ==JOptionPane.YES_OPTION){
				insertaccesories();
				JOptionPane.showMessageDialog(null, "Accesories already saved.");
				displayaccesories();
				tblacc.setEditable(false);
				disablebuttons(true, true, false, false, false, false);
				
			}else{
				
			}
		}
		if(e.getActionCommand().equals("Dispose")) {
			int to_dispose = JOptionPane.showConfirmDialog(getContentPane(),
					"Are you sure to dispose asset?", "Dispose Accessories",
					JOptionPane.YES_NO_OPTION);
			if (to_dispose == JOptionPane.YES_NO_OPTION) {
				disposeasset();
				displayaccesories();
				btnretire.setEnabled(false);
				btndispose.setEnabled(false);
				JOptionPane.showMessageDialog(getContentPane(),
						"Disposed!", "Disposal",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		if(e.getActionCommand().equals("Retire")) {
			int to_dispose = JOptionPane.showConfirmDialog(getContentPane(),
					"Are you sure to retire asset?", "Retire Accessories",
					JOptionPane.YES_NO_OPTION);
			if (to_dispose == JOptionPane.YES_NO_OPTION) {
				retireasset();
				displayaccesories();
				btnretire.setEnabled(false);
				btndispose.setEnabled(false);
				JOptionPane.showMessageDialog(getContentPane(),
						"Retired!", "Retire",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public static void disablebuttons(Boolean a,Boolean b, Boolean c,Boolean d,Boolean e,Boolean f){
		btnadd.setEnabled(a);
		btnedit.setEnabled(b);
		btnsave.setEnabled(c);
		btnretire.setEnabled(d);
		btndispose.setEnabled(e);
		btncancel.setEnabled(f);
		
	}
	
	public static void displayaccesories(){
		modelacc.clear();
		modelacc.setEditable(false);
		
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheader_acc.setModel(listModel);//Setting of listModel into rowHeader.
		
		//String sql="select false,* from tbl_accesories where asset_no='"+panelAssetInformation.txtAssetNo.getText()+"'";
		String sql="select false, \n"
				+ "asset_no,\n"
				+ "sub_asset_no,\n"
				+ "item_name,\n"
				+ "item_brand,\n"
				+ "serial_model_no,\n"
				+ "status_id,\n"
				+ "date_created,\n"
				+ "created_by,\n"
				+ "retired_date,\n"
				+ "disposed_date,\n"
				+ "rec_id\n"
				+ "from tbl_accesories where asset_no= '"+panelAssetInformation.txtAssetNo.getText()+"'";
		
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){

				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelacc.addRow(db.getResult()[x]);

				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelacc.getRowCount());
			}
		}
		
		tblacc.packAll();
	}
	
	public static void disposeasset() {
		pgUpdate db = new pgUpdate();
		for (int x = 0; x < modelacc.getRowCount(); x++) {

			Boolean selected = (Boolean) modelacc.getValueAt(x, 0);

			if (selected) {
				String asset_no = ((String) modelacc.getValueAt(x, 1)).trim();
				String sub_asset_no = ((String) modelacc.getValueAt(x, 2)).trim();
				Integer rec_id = (Integer) (modelacc.getValueAt(x, 11));
				String strsql = "update tbl_accesories set status_id = 'I', disposed_date = now(), disposed_by = '"+UserInfo.EmployeeCode+"' where asset_no = '"+asset_no+"' and sub_asset_no = '"+sub_asset_no+"' and rec_id = "+rec_id+" and status_id = 'A'";
				
				System.out.println("asset_no" + asset_no);
				System.out.println("sub_asset_no" + sub_asset_no);
				//FncSystem.out("disposeasset", strsql);
				db.executeUpdate(strsql, true);
				

			}
		}
		db.commit();
	}
	
	public static void retireasset() {
		pgUpdate db = new pgUpdate();
		for (int x = 0; x < modelacc.getRowCount(); x++) {

			Boolean selected = (Boolean) modelacc.getValueAt(x, 0);

			if (selected) {
				String asset_no = ((String) modelacc.getValueAt(x, 1)).trim();
				String sub_asset_no = ((String) modelacc.getValueAt(x, 2)).trim();
				Integer rec_id = (Integer) (modelacc.getValueAt(x, 11));
				String strsql = "update tbl_accesories set status_id = 'I', retired_date = now(), retired_by = '"+UserInfo.EmployeeCode+"' where asset_no = '"+asset_no+"' and sub_asset_no = '"+sub_asset_no+"' and rec_id = "+rec_id+" and status_id = 'A'";
				
				System.out.println("asset_no" + asset_no);
				System.out.println("sub_asset_no" + sub_asset_no);
				//FncSystem.out("retireasset", strsql);
				db.executeUpdate(strsql, true);
				

			}
		}
		db.commit();
	}
	
	public static void insertaccesories(){
		
		pgUpdate db= new pgUpdate();
		
		String asset_no= (String) modelacc.getValueAt(0, 1);
		String sub_asset=(String) modelacc.getValueAt(0, 2);
		String item_name=(String) modelacc.getValueAt(0, 3);
		String item_brand=(String) modelacc.getValueAt(0, 4);
		String Serial_Model_No=(String) modelacc.getValueAt(0, 5);
		
		
		String strsql="insert into tbl_accesories  \n" + 
				"(\n" + 
				"asset_no,\n" + 
				"sub_asset_no,\n" + 
				"item_name,\n" + 
				"item_brand,\n" + 
				"serial_model_no,\n" + 
				"status_id,\n" + 
				"date_created,\n" + 
				"created_by\n" + 
				")\n" + 
				"values\n" + 
				"(\n" + 
				"'"+asset_no+"',\n" + 
				"'"+sub_asset+"',\n" + 
				"'"+item_name+"',\n" + 
				"'"+item_brand+"',\n" + 
				"'"+Serial_Model_No+"',\n" + 
				"'A',\n" + 
				"now()::date, \n" + 
				"'"+UserInfo.EmployeeCode+"' \n" + 
				")";
		System.out.println(strsql);
		db.executeUpdate(strsql, false);
		db.commit();
	}
	
	public static void displaydetails(){
		modelacc.setEditable(true);
		if(check_existing()){
			System.out.println("check_existing"+ check_existing());
			System.out.println("Dumaan dito sa true");
			modelacc.addRow(new Object[]{false,panelAssetInformation.txtAssetNo.getText().toString(),get_assetno_sub_assetno()[0],
					"","","",get_assetno_sub_assetno()[1],get_assetno_sub_assetno()[2],UserInfo.EmployeeCode,"",""});
			
		}else{
			System.out.println("check_existing"+ check_existing());
			System.out.println("Dumaan dito sa false");
			modelacc.addRow(new Object[]{false,panelAssetInformation.txtAssetNo.getText().toString(),get_sub_asset()[0],
					"","","","A",get_sub_asset()[1],UserInfo.EmployeeCode,"",""});
			
		}
	}
	
	  public boolean isCellEditable(int row, int column) {
	      return modelacc.isCellEditable(row, (column));
	  }
	  
	public static Object[] get_sub_asset(){
		
		String strsql="select concat('"+panelAssetInformation.txtAssetNo.getText()+"','-',lpad((coalesce(0)+1)::varchar,2,'0'::varchar)) as sub_asset,now()::date";
		System.out.println("get_sub_asset= "+ strsql);
		
		pgSelect db = new pgSelect();
		db.select(strsql);
		
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	private static Object [] get_assetno_sub_assetno() {

		String strSQL = "select concat(asset_no, '-', LPAD((coalesce(max(split_part(sub_asset_no, '-', 2)::int), 0)+1)::varchar, 2, '0'::varchar)) as sub_asset,\n" + 
				"'A' as status,now()::date as date_created\n" +
				"from tbl_accesories \n" + 
				"where asset_no='"+panelAssetInformation.txtAssetNo.getText()+"'\n" + 
				"group by asset_no";
				

		FncSystem.out("Display Sql for get_assetno_sub_assetno:", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		


		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	public static Boolean  check_existing(){
		Boolean x=false;
		String strsql= "select exists(select * from tbl_accesories  where asset_no='"+panelAssetInformation.txtAssetNo.getText()+"')";
		
		pgSelect db = new pgSelect();
		db.select(strsql);
		
		if(db.isNotNull()){
			if((Boolean)db.getResult()[0][0]== true){x=true;}else{x=false;}
		}else{
			x=false;
		}
		return x;
	}

}
