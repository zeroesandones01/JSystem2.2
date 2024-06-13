package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncTables;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_dummy;
import tablemodel.model_notfound;

public class ReconOfAsset extends _JInternalFrame implements _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String title="Recon of Assets";
	public static Dimension framesize = new Dimension(800, 500);

	private JPanel pnlmain;

	private JPanel pnlnorth;

	private JPanel pnlsouth;

	private JPanel pnlcenter;

	private JScrollPane scrolldummy;

	private JScrollPane scrollnotfound;

	private JComboBox cmbcategory;

	private JButton btnmatch;

	private model_dummy modeldummy;

	private _JTableMain tbldummy;

	private model_notfound modelnotfound;

	private _JTableMain tblnotfound;

	private JList rowheaderdummy;

	private JList rowheadernotfound;

	private JLabel lblcategory;

	private JTextField txtextra;

	private DefaultComboBoxModel modelcmbcategory;
	
	public String category="1";

	private JPanel pnlmatch;

	public String assetnonotfound;

	public String assetnodummy;

	public ReconOfAsset() {
		super(title,false,true,true,true);
		initGUI();
		displaynotfound();
		displaydummy();
	}

	public ReconOfAsset(String title) {
		super(title,false,true,true,true);
		initGUI();
	}

	public ReconOfAsset(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setTitle(title);
		this.setSize(framesize);
		this.setPreferredSize(framesize);
		//this.setBorder(BorderFactory.createEmptyBorder(5, 5, 3, 3));
		
		{
			pnlmain = new JPanel(new BorderLayout(10,10));
			getContentPane().add(pnlmain);
			{
				pnlnorth = new JPanel(new BorderLayout(3,3));
				pnlmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 35));
				pnlnorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 3, 5));
				{
					lblcategory = new JLabel("Select Category:");
					pnlnorth.add(lblcategory, BorderLayout.WEST);
					lblcategory.setPreferredSize(new Dimension(125, 0));
				}
				{
					modelcmbcategory = new DefaultComboBoxModel(new String []{"OFFICE EQUIPMENT","FURNITURES","SOFTWARE","VEHICLES","FIXTURES","OTHERS","ALL"});
					cmbcategory = new JComboBox();
					cmbcategory.setModel(modelcmbcategory);
					cmbcategory.setSelectedItem(null);
					pnlnorth.add(cmbcategory,BorderLayout.CENTER);
					cmbcategory.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							new Thread(new Runnable() {
								public void run() {
									FncGlobal.startProgress("Please wait while loading assets.");
									int selecteditem = cmbcategory.getSelectedIndex();
									System.out.println("selecteditem "+selecteditem);
									if(selecteditem == 0){
										
										category="1";
										displaydummy();
										displaynotfound();
									}else if(selecteditem == 1){
										
										category="2";
										displaydummy();
										displaynotfound();
									}else if(selecteditem == 2){
										
										category="3";
										displaydummy();
										displaynotfound();
									}else if(selecteditem == 3){
										
										category="4";
										displaydummy();
										displaynotfound();
									}else if(selecteditem == 4){
										
										category="5";
										displaydummy();
										displaynotfound();
									}else if(selecteditem == 5){
										
										category="6";
										displaydummy();
										displaynotfound();
									}else {
										
										category="0";
										displaydummy();
										displaynotfound();
									}
									
									
									FncGlobal.stopProgress();
								}
							}).start();;
						}
					});
				}
				{
					pnlmatch = new JPanel(new BorderLayout(3, 3));
					pnlnorth.add(pnlmatch, BorderLayout.EAST);
					pnlmatch.setPreferredSize(new Dimension(400, 0));
					{
						btnmatch = new JButton("MATCH");
						pnlmatch.add(btnmatch, BorderLayout.EAST);
						btnmatch.setPreferredSize(new Dimension(150, 0));
						btnmatch.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(assetnodummy=="" && assetnonotfound ==""){
									
								}else{
									int recon= JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Are you sure to recon this assets?", "Recon Asset", JOptionPane.YES_NO_OPTION);
									if(recon == JOptionPane.YES_OPTION){
										
										executerecon(assetnonotfound, assetnodummy);
										JOptionPane.showMessageDialog(getTopLevelAncestor(), "Finish");
										cmbcategory.setSelectedIndex(6);
										
									}else{}
								}
							}
						});
					}
				}
				
				/*{
					txtextra = new JTextField();
					pnlnorth.add(txtextra, BorderLayout.EAST);
					txtextra.setPreferredSize(new Dimension(300, 0));
					txtextra.setBorder(_EMPTY_BORDER);
				}*/
			}
			{
				pnlcenter = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				{
					scrolldummy= new JScrollPane();
					pnlcenter.add(scrolldummy);
					scrolldummy.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrolldummy.setBorder(BorderFactory.createTitledBorder(null, "DUMMY ASSETS", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif",0,12)));
					{
						modeldummy = new model_dummy();
						tbldummy = new _JTableMain(modeldummy);
						scrolldummy.setViewportView(tbldummy);
						modeldummy.setEditable(false);
						tbldummy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tbldummy.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							
							public void valueChanged(ListSelectionEvent e) {
								
								if(!e.getValueIsAdjusting()){
									
									try{
										
										int r = tbldummy.getSelectedRow();
										  assetnodummy = (String) modeldummy.getValueAt(r, 0);
										System.out.println("assetnodummy "+assetnodummy);
										
									}catch(ArrayIndexOutOfBoundsException ex){}
									
								}
							}
						});
						tbldummy.getTableHeader().setEnabled(false);
						tbldummy.getColumnModel().getColumn(0).setPreferredWidth(70);//asset no
						tbldummy.getColumnModel().getColumn(1).setPreferredWidth(180);//asset name
						//tbldummy.getColumnModel().getColumn(2).setPreferredWidth(100);//category id
						tbldummy.getColumnModel().getColumn(3).setPreferredWidth(180);//category name 
						//tbldummy.getColumnModel().getColumn(4).setPreferredWidth(150);//date acquired
						//tbldummy.getColumnModel().getColumn(5).setPreferredWidth(100);//custodian id
						tbldummy.getColumnModel().getColumn(6).setPreferredWidth(180);//custodian name
						tbldummy.getColumnModel().getColumn(7).setPreferredWidth(70);//status
						tbldummy .hideColumns("category_id","Date Acquired","Custodian ID");
						
					}
					/*{
						rowheaderdummy = tbldummy.getRowHeader();
						scrolldummy.setRowHeaderView(rowheaderdummy);
					}*/
				}
				{
					scrollnotfound = new JScrollPane();
					pnlcenter.add(scrollnotfound);
					scrollnotfound.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollnotfound.setBorder(BorderFactory.createTitledBorder(null, "NOT FOUND ASSETS", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif",0,12)));
					{
						modelnotfound= new model_notfound();
						tblnotfound = new _JTableMain(modelnotfound);
						scrollnotfound.setViewportView(tblnotfound);
						modelnotfound.setEditable(false);
						tblnotfound.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblnotfound.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){
									
									try{
										
										int r = tblnotfound.getSelectedRow();
										  assetnonotfound = (String) modelnotfound.getValueAt(r, 0);
										System.out.println("assetnonotfound "+assetnonotfound);
										
									}catch(ArrayIndexOutOfBoundsException ex){}
								}
							}
						});
						tblnotfound.getTableHeader().setEnabled(false);
						tblnotfound.getColumnModel().getColumn(0).setPreferredWidth(70);//asset no
						tblnotfound.getColumnModel().getColumn(1).setPreferredWidth(180);//asset name
						//tblnotfound.getColumnModel().getColumn(2).setPreferredWidth(100);//category id
						tblnotfound.getColumnModel().getColumn(3).setPreferredWidth(180);//category name 
						//tblnotfound.getColumnModel().getColumn(4).setPreferredWidth(150);//date acquired
						//tblnotfound.getColumnModel().getColumn(5).setPreferredWidth(100);//custodian id
						tblnotfound.getColumnModel().getColumn(6).setPreferredWidth(180);//custodian name
						tblnotfound.getColumnModel().getColumn(7).setPreferredWidth(70);//status
						tblnotfound .hideColumns("category_id","Date Acquired","Custodian ID");
					}
					/*{
						rowheadernotfound=tblnotfound.getRowHeader();
						scrollnotfound.setRowHeaderView(rowheadernotfound);
					}*/
				}
			}
			/*{
				pnlsouth = new JPanel();
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 30));
				{
					btnmatch = new JButton("MATCH");
					pnlsouth.add(btnmatch);
				}
			}*/
		}
	}
	
	/*public static Object  getcategory(){
		return"select category_id || '-' ||category_name as category_name from tbl_category   order by category_name";
	}*/
	
	public void displaynotfound(){
		
		modelnotfound.clear();
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		//rowheadernotfound.setModel(listModel);//Setting of listModel into rowHeader.
		String sql="select \n" + 
				"a.asset_no::varchar, \n" + 
				"a.asset_name,\n" + 
				"c.category_id,\n" + 
				"c.category_name, \n" + 
				"a.date_acquired,\n" + 
				"a.current_cust,\n" + 
				"get_employee_name(lpad(a.current_cust::text, 6, '0'::text)), \n" + 
				"a.status \n" + 
				"from tbl_asset a\n" + 
				"left join tbl_item b on a.item_id=b.item_id\n" + 
				"left join tbl_category c on b.category_id=c.category_id\n" + 
				"where a.item_found  ='f' \n" + 
				"and a.status='A' \n" + 
				//"and c.category_id='"+category+"'\n" + 
				"and (case when '"+category+"' = '0' then true else c.category_id='"+category+"' end)\n"+
				"order by a.asset_no";
		
		System.out.println("displaynotfound: "+sql);
		System.out.println("notfoundcategory: "+category);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
	
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelnotfound.addRow(db.getResult()[x]);
	
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelnotfound.getRowCount());
			}
		}
		tbldummy.packAll();
	}
public void displaydummy(){
		
		modeldummy.clear();
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		//rowheaderdummy.setModel(listModel);//Setting of listModel into rowHeader.
		String sql="select \n" + 
				"a.asset_no::varchar, \n" + 
				"a.asset_name,\n" + 
				"c.category_id,\n" + 
				"c.category_name, \n" + 
				"a.date_acquired,\n" + 
				"a.current_cust,\n" + 
				"get_employee_name(lpad(a.current_cust::text, 6, '0'::text)), \n" + 
				"a.status \n" + 
				"from tbl_asset a\n" + 
				"left join tbl_item b on a.item_id=b.item_id\n" + 
				"left join tbl_category c on b.category_id=c.category_id\n" + 
				"where a.remarks2='Dummy' \n" + 
				"and a.status='A' \n" + 
				//"and c.category_id='"+category+"'\n" + 
				"and (case when '"+category+"' = '0' then true else c.category_id='"+category+"' end)\n"+
				"order by a.asset_no";
		System.out.println("displaydummy: "+sql);
		System.out.println("dummycategory: "+category);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
	
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modeldummy.addRow(db.getResult()[x]);
	
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modeldummy.getRowCount());
			}
		}
		tbldummy.packAll();
	}
	
	public static void executerecon(String assetnonotfound, String assetnodummy ){
		
		pgUpdate db = new pgUpdate();
		
		String oldasset ="Old asset no is "+ assetnonotfound;
		String newasset ="New asset no is "+assetnodummy;
		
		String sql1 ="update tbl_asset set status='I',remarks='"+newasset+"' where asset_no='"+assetnonotfound+"'";
		
		System.out.println("Update notfound "+sql1);
		db.executeUpdate(sql1, false);
		
		String sql2 ="update tbl_asset set remarks2='Asset Reconed',remarks='"+oldasset+"' where asset_no='"+assetnodummy+"'";
		
		System.out.println("Update notdummy "+sql2);
		db.executeUpdate(sql2, false);
		
		db.commit();
	}
	
}
