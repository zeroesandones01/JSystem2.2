package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class AssetInventory extends _JInternalFrame implements _GUI {
	public static Dimension framesize= new Dimension(500, 300);
	public static String title = "Inventory";

	private static final long serialVersionUID = 1158039814080494444L;
	public static JPanel pnlmain;
	public static  JPanel pnlcenter;
	public static JTextArea jtxtcenter;
	
	private JPanel pnleast;
	private JPanel pnlbutton;
	private JPanel pnlcenternorth;
	private JPanel pnlcenter_center;
	private JLabel lblassetlocation;
	private JScrollPane scrcenter;
	public static _JLookup lookupassetloc;
	public static String location_id;
	public static String location_name;
	
	public static JToggleButton togFound;
	public static JToggleButton togDisposed;
	public static JToggleButton togRetire;
	
	private static JTextField txtAssetNo; 

	public AssetInventory() {
		super(title, false, true, true, true);
		initGUI();
		
	}

	public AssetInventory(String title) {
		super(title, false, true, true, true);
		initGUI();
	}

	public AssetInventory(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(framesize);;
		this.setPreferredSize(framesize);
		this.setLayout(new BorderLayout());
		{
			pnlmain = new JPanel(new BorderLayout(10,10));
			getContentPane().add(pnlmain);
			pnlmain.setBorder(_EMPTY_BORDER);
			//pnlmain.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Scan!");
			//setDefaultCloseOperation(EXIT_ON_CLOSE);
			//pack();
			//setVisible(true);
			/*pnlmain.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						JOptionPane.showInputDialog(getTopLevelAncestor(), "Enter Asset No.");
					}
				}
			});*/
			{
				pnlcenter= new JPanel(new BorderLayout(3,3));
				pnlmain.add(pnlcenter,BorderLayout.CENTER);
				pnlcenter.setBorder(BorderFactory.createTitledBorder(LINE_BORDER, "Asset Information"));
				{
					pnlcenternorth = new JPanel(new BorderLayout(3, 3));
					pnlcenter.add(pnlcenternorth, BorderLayout.NORTH);
					pnlcenternorth.setPreferredSize(new Dimension(0, 27));
					{
						lblassetlocation= new JLabel("Set Asset Location");
						lblassetlocation.setFont(new Font("Tahoma",Font.PLAIN, 11));
						pnlcenternorth.add(lblassetlocation, BorderLayout.WEST);
						lblassetlocation.setPreferredSize(new Dimension(100,0));
					}
					{
						lookupassetloc = new _JLookup();
						pnlcenternorth.add(lookupassetloc, BorderLayout.CENTER);
						lookupassetloc.setFont(new Font("Tahoma", Font.PLAIN, 11));
						lookupassetloc.setEnabled(false);
						lookupassetloc.setLookupSQL(getassetlocation());
						lookupassetloc.addLookupListener(new  LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object [] db = ((_JLookup)event.getSource()).getDataSet();
								if(db != null){
									  location_id = (String)db[0];
									  location_name = (String)db[1];
									
									lookupassetloc.setText(location_name);
								}
							}
						});
					}
				}
				{
					pnlcenter_center = new JPanel(new BorderLayout(3,3));
					pnlcenter.add(pnlcenter_center, BorderLayout.CENTER);
					{
						jtxtcenter = new JTextArea();
						//pnlcenter_center.add(jtxtcenter);
						//JScrollPane sp = new JScrollPane(jtxtcenter);
						jtxtcenter.setBackground(Color.BLACK);
						jtxtcenter.setForeground(Color.white);
						jtxtcenter.setLineWrap(true);
						jtxtcenter.setFont(new Font("Tahoma",Font.PLAIN,11));
						jtxtcenter.setEditable(false);
						jtxtcenter.addFocusListener(new FocusListener() {
							public void focusLost(FocusEvent e) {
								
							}
							
							public void focusGained(FocusEvent e) {
								txtAssetNo.requestFocus();							
							}
						});
					}
					{
						scrcenter = new JScrollPane(jtxtcenter);
						pnlcenter_center.add(scrcenter, BorderLayout.CENTER);
						scrcenter.setViewportView(jtxtcenter);
						scrcenter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrcenter.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					}
				}
				
			}
			{
				pnleast = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnleast, BorderLayout.EAST);
				pnleast.setPreferredSize(new Dimension(100, 0));
				pnleast.setBorder(BorderFactory.createTitledBorder(LINE_BORDER, "Select Mode"));
				{
					{
						pnlbutton = new JPanel(new GridLayout(3, 1, 5, 5));
						pnleast.add(pnlbutton, BorderLayout.CENTER);
						{
							{
								togFound = new JToggleButton("Found");
								pnlbutton.add(togFound);
								togFound.addActionListener(new ActionListener() {
									
									public void actionPerformed(ActionEvent e) {
										if(togFound.isSelected()){
											togDisposed.setEnabled(false);
											togRetire.setEnabled(false);
											jtxtcenter.setText("");
											lookupassetloc.setEnabled(true);
											addLog("Found Mode Selected");
											System.out.println("Found mode activated");
											
										}else{
											togDisposed.setEnabled(true);
											togRetire.setEnabled(true);
											jtxtcenter.setText("");
											lookupassetloc.setEnabled(false);
											lookupassetloc.setText("");
											System.out.println("Found mode deactivated");
										}
										
										txtAssetNo.requestFocus();
									}
								});
							}
							{
								togDisposed = new JToggleButton("Disposed");
								pnlbutton.add(togDisposed);
								togDisposed.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										
										if(togDisposed.isSelected()){
											togFound.setEnabled(false);
											togRetire.setEnabled(false);
											jtxtcenter.setText("");
											addLog("Dispose Mode Selected");
											System.out.println("Dispose mode activated");
										}else{
											togFound.setEnabled(true);
											togRetire.setEnabled(true);
											jtxtcenter.setText("");
											System.out.println("Dispose mode deactivated");
										}
										
										txtAssetNo.requestFocus();
									}
								});
							}
							{
								togRetire = new JToggleButton("Retire");
								pnlbutton.add(togRetire);
								togRetire.addActionListener(new ActionListener() {
									
									public void actionPerformed(ActionEvent e) {
											
										if(togRetire.isSelected()){
											togFound.setEnabled(false);
											togDisposed.setEnabled(false);
											jtxtcenter.setText("");
											addLog("Retire Mode Selected");
											System.out.println("Retire mode activated");
										}else{
											togFound.setEnabled(true);
											togDisposed.setEnabled(true);
											jtxtcenter.setText("");
											System.out.println("Retire mode deactivated");
										}
										
										txtAssetNo.requestFocus();
									}
								});
							}
						}	
					}
					{
						txtAssetNo = new JTextField(""); 
						pnleast.add(txtAssetNo, BorderLayout.PAGE_END);
						txtAssetNo.setPreferredSize(new Dimension(0, 27));
						txtAssetNo.addKeyListener(keyAdapter);
						txtAssetNo.addFocusListener(new FocusListener() {
							public void focusLost(FocusEvent e) {
								
							}
							
							public void focusGained(FocusEvent e) {
								
							}
						});
					}
				}
			}
		}
		
		txtAssetNo.requestFocus();
	}
	
	public static String getassetlocation(){
		
		return"select * from tbl_asset_location ";
		
		/*return "select '01' as loc_id, '7th Floor Summit' as loc_name union all\n" + 
				"select '02' as loc_id, 'Mezzanine Floor Summit' as loc_name union all\n" + 
				"select '03' as loc_id, 'Anita Office' as loc_name union all\n" + 
				"select '04' as loc_id, 'Site Montalban' as loc_name union all\n" + 
				"select '05' as loc_id, 'Site Carmona' as loc_name ";*/
	}
	
	public static void assetinfo(String asset_no) {
		
		String SQL="select concat('Company Name: ', g.company_name), \n" +
				"concat('Asset No.: ', a.asset_no), \n" + 			//0 asset_no
				"concat('Asset Code: ', a.asset_code), \n" +		//1 asset_code
				"concat('Asset Name: ', a.asset_name), \n" +		//2 asset_name
				"concat('Date Acquired: ', a.date_acquired), \n" + 	//3 date_acquired
				"concat('Current Custodian: ', a.current_cust ||'-' ||e.entity_name), \n" +  //4 current_cust
				"concat('Category Name: ', c.category_name), \n"+ 	//5 category_name
				"concat('Item Name: ', f.item_name ), \n"+ 			//6 item_name
				"concat('Item Brand: ', a.brand ), \n"+ 				//7 brand
				"concat('Item Description: ', a.description ), \n"+ 		//8 description
				"concat('Asset Serial No.: ', a.asset_serial ), \n"+//9 asset_serial
				"concat('Asset Model No.: ', a.asset_model ), \n"+ 	//10 asset_model
				"concat('Net Cost: ', a.asset_cost ), \n"+ 			//11 asset_cost
				"concat('Useful Life Months: ', a.asset_ulm ), \n"+ //12 asset_ulm
				"concat('Depreciation Start: ', a.from_dep ), \n"+ 	//13 from_dep
				"concat('Depreciation End: ', a.to_dep ) \n"+ 		//14 to_dep
				
				"from tbl_asset a \n" + 
				"left join tbl_item b on a.item_id=b.item_id \n" + 
				"left join tbl_category c on b.category_id=c.category_id \n" + 
				"left join em_employee d on a.current_cust=d.emp_code::int \n" + 
				"left join rf_entity e on d.entity_id=e.entity_id \n" + 
				"left join tbl_item f on a.item_id=f.item_id\n"+
				" left join mf_company g on d.co_id=g.co_id\n"+
				"where asset_no=replace('"+asset_no+"','Asset no:','')::int ";
		
		System.out.println("SQL: "+SQL);
		System.out.println("Scanned: "+asset_no);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			
			addLog(db.getResult()[0][0].toString()); //0 asset_no
			addLog(db.getResult()[0][1].toString()); //1 asset_code
			addLog(db.getResult()[0][2].toString()); //2 asset_name
			addLog(db.getResult()[0][3].toString()); //3 date_acquired
			addLog(db.getResult()[0][4].toString()); //4 current_cust
			addLog(db.getResult()[0][5].toString()); //5 category_name
			addLog(db.getResult()[0][6].toString()); //6 item_name
			addLog(db.getResult()[0][7].toString()); //7 brand
			addLog(db.getResult()[0][8].toString()); //8 description
			addLog(db.getResult()[0][9].toString()); //9 asset_serial
			addLog(db.getResult()[0][10].toString()); //10 asset_model
			addLog(db.getResult()[0][11].toString()); //11 asset_cost
			addLog(db.getResult()[0][12].toString()); //12 asset_ulm
			addLog(db.getResult()[0][13].toString()); //13 from_dep
			addLog(db.getResult()[0][14].toString()); //14 to_dep
			addLog(db.getResult()[0][15].toString());
			
		}
		txtAssetNo.setText("");
		
		if(togFound.isSelected()){
			
			if(lookupassetloc.getText().equals("")){
				
				JOptionPane.showInternalMessageDialog(pnlmain, "Please select asset location then scan the barcode again.");
				//clearLog();
				addLog("Found Mode Selected");
				
			}else{
				
				pgUpdate db1 = new pgUpdate();
				
				String SQL1="update tbl_asset  \n" + 
						"set item_found  ='t',\n" + 
						"date_edited='now()',\n" + 
						"Edited_by='"+UserInfo.EmployeeCode+"',\n" + 
						"date_scanned='now()',\n" + 
						"scanned_by='"+UserInfo.EmployeeCode+"',\n" + 
						"loc_id='"+location_id+"'\n"+
						//"loc_name='"+location_name+"'\n"+
						"where asset_no=replace('"+asset_no+"','Asset no:','')::int";
				
				System.out.println("tagfound "+ SQL1);
				db1.executeUpdate(SQL1, false);
				db1.commit();
			}
		
		}else if(togDisposed.isSelected()){
			pgUpdate db2 = new pgUpdate();
			
			String SQL2 = "update tbl_asset set status='I', remarks='DISPOSED', date_retired=current_date where asset_no=replace('"+asset_no+"','Asset no:','')::int " ;
			db2.executeUpdate(SQL2, true);
			System.out.println("tagdispose"+ SQL2);
			
			String strSQL2 = "INSERT INTO tbl_asset_history( \n" +
					"asset_code, \n" +
					"prev_cust, \n" +
					"current_cust, \n" +
					"trans_date, \n" +
					"reason, \n" +
					"remarks, \n" +
					"status, \n" +
					"move_no, \n" +
					"asset_no, \n" +
					"trans_by) \n" +
					"VALUES (\n" +
					"(select asset_code from tbl_asset where asset_no =replace('"+asset_no+"','Asset no:','')::int), \n" +//asset_code
					"(select current_cust from tbl_asset where asset_no =replace('"+asset_no+"','Asset no:','')::int), \n" +//prev_cust
					"(select current_cust from tbl_asset where asset_no =replace('"+asset_no+"','Asset no:','')::int), \n" +//current_cust
					"current_date, \n" +//trans_date
					"'Disposed', \n" +//reason
					"'Disposed by "+UserInfo.FullName+"', \n" +//remarks
					"'I', \n" +//status
					"(select coalesce(max(move_no),0) + 1 from tbl_asset_history), \n" +//move_no
					"(replace('"+asset_no+"','Asset no:','')::int), \n" +//asset_no
					"'" +UserInfo.EmployeeCode + "') \n";//trans_by
			System.out.println("insert dipose  "+ strSQL2);
			db2.executeUpdate(strSQL2, false);
			db2.commit();
			
			//JOptionPane.showMessageDialog(pnlmain, "Dispose mode");
			System.out.println("Dispose mode");
			
		}else if(togRetire.isSelected()){
			pgUpdate db3 = new pgUpdate();
			
			String SQL3 = "update tbl_asset set status='I', remarks='RETIRED', date_retired=current_date where asset_no=replace('"+asset_no+"','Asset no:','')::int " ;
			
			db3.executeUpdate(SQL3, false);
			System.out.println("tagretire"+ SQL3);
			
			String strSQL3 = "INSERT INTO tbl_asset_history( \n" +
					"asset_code, \n" +
					"prev_cust, \n" +
					"current_cust, \n" +
					"trans_date, \n" +
					"reason, \n" +
					"remarks, \n" +
					"status, \n" +
					"move_no, \n" +
					"asset_no, \n" +
					"trans_by) \n" +
					"VALUES (\n" +
					"(select asset_code from tbl_asset where asset_no =replace('"+asset_no+"','Asset no:','')::int), \n" +//asset_code
					"(select current_cust from tbl_asset where asset_no =replace('"+asset_no+"','Asset no:','')::int), \n" +//prev_cust
					"(select current_cust from tbl_asset where asset_no =replace('"+asset_no+"','Asset no:','')::int), \n" +//current_cust
					"current_date, \n" +//trans_date
					"'Retired', \n" +//reason
					"'Retired by "+UserInfo.FullName+"', \n" +//remarks
					"'A', \n" +//status
					"(select coalesce(max(move_no),0) + 1 from tbl_asset_history), \n" +//move_no
					//"(replace('"+asset_no+"','Asset no:','')::int), \n" +//asset_no
					"(replace('"+asset_no+"','Asset no:','')::int), \n" +//asset_no
					"'" + UserInfo.EmployeeCode+ "') \n";//trans_by
			System.out.println("insert retire "+ strSQL3);
			db3.executeUpdate(strSQL3, true);
			db3.commit();
			JOptionPane.showMessageDialog(pnlmain, "Retire mode");
		}else{
			
			JOptionPane.showMessageDialog(pnlmain, "Please select mode.");
			clearLog();
			
		}
	}
	
	private static void addLog(String strLog) {
		jtxtcenter.append(strLog+"\n");
	}
	
	private static void clearLog() {
		jtxtcenter.setText("");
	}
	
	static KeyAdapter keyAdapter = new KeyAdapter() {

		public void keyReleased(KeyEvent e) {
			try {
				if(e.getKeyCode()==10){
					System.out.println("I went here!");
					clearLog(); 
					//assetinfo(Integer.valueOf(txtAssetNo.getText().trim()));
					assetinfo(txtAssetNo.getText());
					//tagfound(txtAssetNo.getText());
					
					System.out.println(txtAssetNo.getText());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}	 
		}
		
	};
	public static void tagfound(String assetno){
		pgUpdate db = new pgUpdate();
		
		String SQL="update tbl_asset  set item_found  ='t' where asset_no=replace('"+assetno+"','Asset no:','')::int";
		
		db.executeUpdate(SQL,false);
		db.commit();
		System.out.println("tagfound "+SQL);
		System.out.println("Asset no. "+assetno);
		
		
		JOptionPane.showMessageDialog(jtxtcenter,"Asset tagged as found.");
	}
}
