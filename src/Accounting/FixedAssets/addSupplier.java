package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelsupplier;

public class addSupplier extends _JInternalFrame implements ActionListener, _GUI {
	
	public static String title = "Add Supplier";
	private JPanel pnladdsupplier;
	private JPanel pnladdsuppliercenter;
	private JPanel pnladdsuppliersouth;
	private JPanel pnladdsuppliernorth;
	private JPanel pnladdsuppliercenter1;
	private JPanel pnladdsuppliercenter1_1;
	private JLabel lblsuppid;
	public static JTextField txtsuppid;
	private JPanel pnladdsuppliercenter1_2;
	private JLabel lblcontactno;
	public static JTextField txtcontactno;
	private JPanel pnladdsuppliercenter2;
	private JPanel pnladdsuppliercenter2_1;
	private JPanel pnladdsuppliercenter2_2;
	private JLabel lblsuppliername;
	public static JTextField txtsuppliername;
	private JLabel lbladdress;
	public static JTextField txtaddress;
	private JPanel pnladdsuppliercenter3;
	private JButton btnnew;
	private JButton btnedit;
	private JButton btnsave;
	private JButton btnreset;
	private JPanel pnlbuttons;
	private JScrollPane scrollsupplier;
	private static modelsupplier modelsupplier;
	private _JTableMain tblsupplier;
	protected ButtonGroup grpNE = new ButtonGroup();
	
	
	
	public addSupplier() {
		super(title, true, true, true, true);
		initGUI();
		displaysupplier();
		btnState(true, true, false, false);
	
	}	
	public addSupplier(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public addSupplier(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}


	public void actionPerformed(ActionEvent arg0) {
		
	}
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setBounds(0, 0, 496, 455);
		
		{
			pnladdsupplier=new JPanel(new BorderLayout(3,3));
			this.add(pnladdsupplier,BorderLayout.CENTER);
			{
				pnladdsuppliernorth= new JPanel(new BorderLayout());
				pnladdsupplier.add(pnladdsuppliernorth,BorderLayout.NORTH);
				pnladdsuppliernorth.setPreferredSize(new Dimension(0, 280));
				pnladdsuppliernorth.setBorder(JTBorderFactory.createTitleBorder(""));
				{
					scrollsupplier= new JScrollPane();
					pnladdsuppliernorth.add(scrollsupplier,BorderLayout.CENTER);
					scrollsupplier.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelsupplier= new modelsupplier();
						tblsupplier= new _JTableMain(modelsupplier);	
						scrollsupplier.setViewportView(tblsupplier);
						tblsupplier.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								if(!arg0.getValueIsAdjusting()){
									int row = tblsupplier.getSelectedRow();
									//int supp_id= (int) tblsupplier.getValueAt(row, 0);
									String supp_id =  tblsupplier.getValueAt(row, 0).toString();
									
									
									displaysupplierdetails( supp_id);
								}
							}
						});;
						
						tblsupplier.getTableHeader().setEnabled(false);
						tblsupplier.setFillsViewportHeight(false);
						tblsupplier.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblsupplier.getColumnModel().getColumn(1).setPreferredWidth(250);
						tblsupplier.getColumnModel().getColumn(2).setPreferredWidth(300);
						tblsupplier.getColumnModel().getColumn(3).setPreferredWidth(100);
					}
				}
			}
			{
				pnladdsuppliercenter= new JPanel(new GridLayout(3, 1,3,3));
				pnladdsupplier.add(pnladdsuppliercenter,BorderLayout.CENTER);
				pnladdsuppliercenter.setBorder(JTBorderFactory.createTitleBorder("Set supplier information"));
				{
					pnladdsuppliercenter1= new JPanel(new GridLayout(1, 2));
					pnladdsuppliercenter.add(pnladdsuppliercenter1,BorderLayout.NORTH);
					//pnladdsuppliercenter1.setPreferredSize(new Dimension(20, 0));
					{
						pnladdsuppliercenter1_1= new JPanel(new BorderLayout(3,3));
						pnladdsuppliercenter1.add(pnladdsuppliercenter1_1,BorderLayout.WEST);
						{
							lblsuppid= new JLabel("Supplier ID");
							pnladdsuppliercenter1_1.add(lblsuppid,BorderLayout.WEST);
							lblsuppid.setPreferredSize(new Dimension(75, 0));
						}
						{
							txtsuppid= new JTextField();
							pnladdsuppliercenter1_1.add(txtsuppid,BorderLayout.CENTER);
							txtsuppid.setEditable(false);
						}
					}
					{
						pnladdsuppliercenter1_2= new JPanel(new BorderLayout(3,3));
						pnladdsuppliercenter1.add(pnladdsuppliercenter1_2);
						{
							lblcontactno= new JLabel("Contact No.");
							pnladdsuppliercenter1_2.add(lblcontactno,BorderLayout.WEST);
							lblcontactno.setPreferredSize(new Dimension(75, 0));
						}
						{
							txtcontactno= new JTextField();
							pnladdsuppliercenter1_2.add(txtcontactno,BorderLayout.CENTER);
							txtcontactno.setEditable(false);
						}
					}
				}
				{
					pnladdsuppliercenter2= new JPanel(new BorderLayout(3,3));
					pnladdsuppliercenter.add(pnladdsuppliercenter2);
					{
						lblsuppliername= new JLabel("Supplier Name");
						pnladdsuppliercenter2.add(lblsuppliername,BorderLayout.WEST);
						lblsuppliername.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtsuppliername=new JTextField();
						pnladdsuppliercenter2.add(txtsuppliername,BorderLayout.CENTER);
						txtsuppliername.setEditable(false);
					}
				}
				{
					pnladdsuppliercenter3= new JPanel(new BorderLayout(3,3));
					pnladdsuppliercenter.add(pnladdsuppliercenter3);
					{
						lbladdress= new JLabel("Address");
						pnladdsuppliercenter3.add(lbladdress,BorderLayout.WEST);
						lbladdress.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtaddress= new JTextField();
						pnladdsuppliercenter3.add(txtaddress,BorderLayout.CENTER);
						txtaddress.setEditable(false);
					}
				}
			}
			{
				pnladdsuppliersouth=new JPanel(new BorderLayout(3,3));
				pnladdsupplier.add(pnladdsuppliersouth,BorderLayout.SOUTH);
				pnladdsuppliersouth.setPreferredSize(new Dimension(0, 30));
				{
					pnlbuttons= new JPanel(new GridLayout(1, 4,3,3));
					pnladdsuppliersouth.add(pnlbuttons);
					{
						btnnew= new JButton("New");
						pnlbuttons.add(btnnew);
						btnnew.setEnabled(false);
						grpNE.add(btnnew);
						btnnew.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								newstate();	
							}
						});
					}
					{
						btnedit= new JButton("Edit");
						pnlbuttons.add(btnedit);
						grpNE.add(btnedit);
						btnedit.setEnabled(false);
						btnedit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								editstate();
								btnState(false, false, true, true);
								tblsupplier.setEnabled(false);
								
							}
						});
					}
					{
						btnsave= new JButton("Save");
						pnlbuttons.add(btnsave);
						btnnew.setEnabled(false);
						btnsave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(toadd()){
									int tosave;
									if(grpNE.isSelected(btnnew.getModel())){
										if(!checkItemIfExist(txtsuppliername.getText().trim().replace("'", "''").toUpperCase())){
											tosave=JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to add this supplier?","save",JOptionPane.YES_NO_OPTION);
											System.out.println("");
											if(tosave==JOptionPane.YES_OPTION){
												executesave(txtsuppid.getText(), txtsuppliername.getText(), txtaddress.getText(), txtcontactno.getText());
												clearfields();
												displaysupplier();
												btnState(true, true, false, false);
											}
											
										}else{
											JOptionPane.showMessageDialog(getTopLevelAncestor(), "Supplier is existing.","Save",JOptionPane.WARNING_MESSAGE);
										}
									}
									if(grpNE.isSelected(btnedit.getModel())){
										tosave=JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to update this supplier?","save",JOptionPane.YES_NO_OPTION);
										if(tosave==JOptionPane.YES_OPTION){
											updatesupplier(txtsuppid.getText());
											clearfields();
											displaysupplier();
											tblsupplier.setEnabled(true);
											btnState(true, true, false, false);
										}
									}else{}
									
								}else{
									JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please fill-up required fields.","Save",JOptionPane.WARNING_MESSAGE);
								}
							}
							
						});
					}
					{
						btnreset= new JButton("Reset");
						pnlbuttons.add(btnreset);
						btnreset.setEnabled(false);
						btnreset.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								clearfields();
								btnState(true, true, false, false);
							}
						});
						
					}
				}
				
			}
		}
		
	}
	
	public static String nextsuppid(){
		pgSelect db = new pgSelect();
		String strsql="select to_char(max(supp_id)+1,'FM000000') from tbl_supplier";
		db.select(strsql);
		return db.Result[0][0].toString();
	}
	protected void newstate(){
		txtsuppid.setText(nextsuppid());
		grpNE.setSelected(btnnew.getModel(), true);
		btnState(false, false, true, true);
		txtsuppid.setEditable(true);
		txtsuppid.setEnabled(true);
		txtsuppliername.setText("");
		txtsuppliername.setEditable(true);
		txtsuppliername.setEnabled(true);
		txtaddress.setText("");
		txtaddress.setEditable(true);
		txtaddress.setEnabled(true);
		txtcontactno.setText("");
		txtcontactno.setEditable(true);
		txtcontactno.setEnabled(true);
		
	}
	protected void editstate(){
		grpNE.setSelected(btnedit.getModel(), true);
		txtsuppid.setEditable(true);
		txtsuppliername.setEditable(true);
		txtaddress.setEditable(true);
		txtcontactno.setEditable(true);
		
	}
	public static void clearfields(){
		txtsuppid.setText("");
		txtsuppid.setEditable(false);
		txtsuppliername.setText("");
		txtsuppliername.setEditable(false);
		txtaddress.setText("");
		txtaddress.setEditable(false);
		txtcontactno.setText("");
		txtcontactno.setEditable(false);
	}
	
	public static void updatesupplier( String supp_id){
		pgUpdate db = new pgUpdate();
		
		String strsql="update tbl_supplier\n" + 
				"set\n" + 
				"supp_name='"+txtsuppliername.getText()+"',\n" + 
				"supp_address='"+txtaddress.getText()+"',\n" + 
				"supp_contact='"+txtcontactno.getText()+"'\n" + 
				"where supp_id='"+supp_id+"'";
		
		db.executeUpdate(strsql, false);
		db.commit();
	}
	public static Boolean checkItemIfExist(String supplier_name){
		
		pgSelect db = new pgSelect();
		
		String strql ="select item_id from tbl_item where item_name  ~*'"+supplier_name+"' ";
		
		System.out.println("checkItemIfExist "+strql);
		FncSystem.out("supplier_name", strql);
		db.select(strql);
		
		System.out.println( db.getRowCount()>0? true:false);
		
		return db.getRowCount()>0? true:false;
	}
	
	protected void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnnew.setEnabled(sNew);
		btnedit.setEnabled(sEdit);
		btnsave.setEnabled(sSave);
		btnreset.setEnabled(sCancel);
	}
	public static void executesave(String supp_id, String supp_name, String supp_address, String supp_contact){
		 pgUpdate db= new pgUpdate();
		 
		 String strsql="insert into tbl_supplier (\n" + 
		 		"supp_id,\n" + 
		 		"supp_name,\n" + 
		 		"supp_address,\n" + 
		 		"supp_contact)\n" + 
		 		"values(\n" + 
		 		"'"+supp_id+"',\n" + 
		 		"'"+supp_name+"',\n" + 
		 		"'"+supp_address+"',\n" + 
		 		"'"+supp_contact+"')";
		 
		 System.out.println("executesave "+strsql);
		 db.executeUpdate(strsql, false);
		 db.commit();
		 
	}
	public Boolean toadd(){
		if(txtsuppid.getText().equals("") 
				|| txtsuppliername.getText().equals("")
				|| txtaddress.getText().equals("")
				|| txtcontactno.getText().equals("")
				)
		{
			return false;
		}else 
			return true;
	}
	
	public static void displaysupplier(){
		pgSelect db= new pgSelect();
		
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		
		String strsql="select * from tbl_supplier where status_id='A'order by supp_id  ";
		
		
		db.select(strsql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelsupplier.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelsupplier.getRowCount());
			}
		}
		
	}
	public void displaysupplierdetails(String supp_id){
		pgSelect db= new pgSelect();
		
		String strsql="select  \n" + 
				"supp_id::varchar,\n" + 
				"supp_name,\n" + 
				"supp_address,\n" + 
				"supp_contact\n" + 
				"from tbl_supplier  where supp_id  ='"+supp_id+"'";
		
		db.select(strsql);
		if(db.isNotNull()){
			txtsuppid.setText((String) db.getResult()[0][0]);
			//txtsuppid.setText((Integer) db.getResult()[0][0]);
			txtsuppliername.setText((String) db.getResult()[0][1]);
			txtaddress.setText((String) db.getResult()[0][2]);
			txtcontactno.setText((String) db.getResult()[0][3]);
		}
	}
}
