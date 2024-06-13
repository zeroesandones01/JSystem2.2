package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Buyers.CreditandCollections._RegularBillingAndNotices;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelAssetMonitoring;
import tablemodel.modelPrintAssetSticker;

public class PrintAssetSticker extends _JInternalFrame implements ActionListener, _GUI {
	
	public static String title = "Print Asset Sticker";
	public static Dimension framesize= new Dimension(600, 400);
	private JPanel pnlmain;
	private JPanel pnlSouth;
	private JPanel pnlCenter;
	private JPanel pnlNorth;
	private JPanel pnlNorth1;
	private JPanel pnlNorth2;
	private JPanel pnlNorth3;
	private JButton btnPreview;
	private JRadioButton rbAllAsset;
	private JRadioButton rbByDepartment;
	private _JLookup lookupByDepartment;
	private JTextField txtByDepartment;
	private JScrollPane scrollAssets;
	private static _JTableMain tblAssets;
	private static JList rowheaderAssets;
	private JRadioButton rbByCustodian;
	private _JLookup lookupByCustodian;
	private JTextField txtByCustodian;
	private JPanel pnlFilter;
	private JPanel pnlButton;
	private JPanel pnlSouthextra1;
	private JPanel pnlSouthextra2;
	private JTextField txtSouthextra1;
	private JTextField txtSouthextra2;
	private static JComboBox cbType;
	private JLabel lblAssetType;
	private static modelPrintAssetSticker modelAssets;
	public static Boolean byDepartment=false; 
	public static Boolean byCustodian=false;
	public static String cust;
	public static String co_name;
	public static String co_logo;
	public static ButtonGroup grpAllAsset = new ButtonGroup();
	public static ButtonGroup grpCustodian = new ButtonGroup();
	
	public PrintAssetSticker(){
		super(title, false, true, true, true);
		initGUI();
		//displayAllAssets();
	}
	public PrintAssetSticker (String title) {
		super(title, true, true, true, true);
		initGUI();
	}
	public PrintAssetSticker (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
				pnlNorth=new JPanel(new BorderLayout(3,3));
				pnlmain.add(pnlNorth,BorderLayout.NORTH );
				pnlNorth.setBorder(_EMPTY_BORDER);
				pnlNorth.setBorder(BorderFactory.createTitledBorder(null, "Filter Asset List", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif",0,12)));
				{
					pnlFilter= new JPanel(new GridLayout(3,1,3,3));
					pnlNorth.add(pnlFilter);
					pnlFilter.setPreferredSize(new Dimension(0, 80));
					{
						{ /*ADDED BY JARI CRUZ JULY 7 2022 */
							JPanel pnlAssetFilter = new JPanel(new BorderLayout(5, 5));
							pnlFilter.add(pnlAssetFilter);
							{
								JPanel pnlAssetFilterWest = new JPanel(new GridLayout(1, 1, 5, 5));
								pnlAssetFilter.add(pnlAssetFilterWest, BorderLayout.WEST);
								lblAssetType = new JLabel("      Asset Type");
								pnlAssetFilterWest.add(lblAssetType);
								pnlAssetFilterWest.setPreferredSize(new Dimension(125, 0));
//								lblAssetType.setEnabled(false);
							}
							{
								JPanel pnlAssetFilterCenter = new JPanel(new GridLayout(1, 1, 5, 5));
								pnlAssetFilter.add(pnlAssetFilterCenter, BorderLayout.CENTER);
								{
									String[] typeStrings = { "Fixed Asset", "Non-fixed Asset" };
									cbType = new JComboBox(typeStrings);
									pnlAssetFilterCenter.add(cbType);
									cbType.setSelectedIndex(0);
//									cbType.setEnabled(false);
									cbType.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											if (rbAllAsset.isSelected()) {
												displayAllAssets();
											}
											if (rbByCustodian.isSelected()) {
												filterbyCustodian(modelAssets, rowheaderAssets, lookupByCustodian.getText());
											}
										}
									});
								}
							}
						}/*ADDED BY JARI CRUZ JULY 7 2022 */
						pnlNorth1=new JPanel(new BorderLayout(3,3));
						pnlFilter.add(pnlNorth1);
						{
							rbAllAsset= new JRadioButton("All Asset");
							pnlNorth1.add(rbAllAsset);
							grpAllAsset.add(rbAllAsset);
							rbAllAsset.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									
									if(rbAllAsset.isSelected()){
										btnPreview.setEnabled(false);
										new Thread(new Runnable() {
											public void run() {
												lblAssetType.setEnabled(false);
//												cbType.setEnabled(false);
												FncGlobal.startProgress("Loading all assets.");
												displayAllAssets();
												FncGlobal.stopProgress();
											}
										}).start();
										
										btnPreview.setEnabled(true);
										grpCustodian.clearSelection();
										lookupByCustodian.setValue("");
										lookupByCustodian.setEditable(false);
										txtByCustodian.setText("");
										txtByCustodian.setEditable(false);

									}
									else{}
								}
							});
						}
					}
					/*{
						pnlNorth2= new JPanel(new BorderLayout(5,5));
						pnlFilter.add(pnlNorth2);
						{
							rbByDepartment= new JRadioButton("By Department");
							pnlNorth2.add(rbByDepartment, BorderLayout.WEST);
							rbByDepartment.setPreferredSize(new Dimension(125, 0));
							rbByDepartment.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									lookupByDepartment.setEditable(true);
								}
							});
						}
						{
							lookupByDepartment= new _JLookup();
							lookupByDepartment.setReturnColumn(0);
							lookupByDepartment.setEditable(false);
							pnlNorth2.add(lookupByDepartment, BorderLayout.CENTER);
							lookupByDepartment.setLookupSQL(getDepartment());
							lookupByDepartment.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if(data!=null){
										String dept_name = (String) data[1];
										txtByDepartment.setText(dept_name);
									}
								}
							});
						}
						{
							txtByDepartment= new JTextField();
							pnlNorth2.add(txtByDepartment, BorderLayout.EAST);
							txtByDepartment.setPreferredSize(new Dimension(325, 0));
						}
					}*/
					{
						pnlNorth3= new JPanel(new BorderLayout(5,5));
						pnlFilter.add(pnlNorth3, BorderLayout.CENTER);
						{
							rbByCustodian= new JRadioButton("By Custodian");
							pnlNorth3.add(rbByCustodian, BorderLayout.WEST);
							grpCustodian.add(rbByCustodian);
							rbByCustodian.setPreferredSize(new Dimension(125, 0));
							rbByCustodian.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if(rbByCustodian.isSelected()){
										lblAssetType.setEnabled(true);
										cbType.setEnabled(true);
										lookupByCustodian.setEditable(true);
										modelAssets.clear();
										grpAllAsset.clearSelection();
									}
									else{
										lookupByCustodian.setValue("");
										txtByCustodian .setText("");
										lookupByCustodian.setEditable(false);
										txtByCustodian.setEditable(false);
										modelAssets.clear();
									}
									
								}
							});
						}
						{
							lookupByCustodian= new _JLookup();
							lookupByCustodian.setReturnColumn(0);
							lookupByCustodian.setLookupSQL(getCustodian());
							lookupByCustodian.setEditable(false);
							pnlNorth3.add(lookupByCustodian, BorderLayout.CENTER);
							lookupByCustodian.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if(data !=null){
										String emp_name = (String) data[1];
										co_name = (String) data [3];
										co_logo=(String) data[4];
										txtByCustodian.setText(emp_name);
										filterbyCustodian(modelAssets, rowheaderAssets, lookupByCustodian.getText());									
										
									}
								}
							});
						}
						{
							txtByCustodian= new JTextField();
							pnlNorth3.add(txtByCustodian, BorderLayout.EAST);
							txtByCustodian.setPreferredSize(new Dimension(325, 0));
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout());
				pnlmain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(BorderFactory.createTitledBorder(null, "Select Assets for Printing", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif",0,12)));
				{
					scrollAssets = new JScrollPane();
					pnlCenter.add(scrollAssets);
					scrollAssets.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelAssets = new modelPrintAssetSticker();
						tblAssets = new _JTableMain(modelAssets);
						scrollAssets.setViewportView(tblAssets);
						modelAssets .setEditable(true);
						//tblAssets.setEnabled(true);
						tblAssets.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){
									
									try{
										
										int row = tblAssets.getSelectedRow();
										String	asset_no=    (String) modelAssets.getValueAt(row, 1);
										//co_logo= (String) modelAssets.getValueAt(row, 10);
										//co_name= (String) modelAssets .getValueAt(row, 9);
										//System.out.println(row);
										//System.out.println(asset_no);
										FncSystem.out("Asset_no:",asset_no);
										
										
										
									}catch (ArrayIndexOutOfBoundsException ex) { }
									
								}
							}
						});
						tblAssets.getTableHeader().setEnabled(false); 
						//tblAssets.setSortable(false);
						tblAssets.setFillsViewportHeight(false);
						tblAssets.getColumnModel().getColumn(0).setPreferredWidth(50);//checkbox
						tblAssets.getColumnModel().getColumn(1).setPreferredWidth(120);;//asset_no
						tblAssets.getColumnModel().getColumn(2).setPreferredWidth(120);//asset code
						tblAssets.getColumnModel().getColumn(3).setPreferredWidth(180);//asset name
						tblAssets.getColumnModel().getColumn(4).setPreferredWidth(100);//date acquired
						tblAssets.getColumnModel().getColumn(6).setPreferredWidth(180);//Custodian
						tblAssets.getColumnModel().getColumn(8).setPreferredWidth(70);//status
						tblAssets.hideColumns("Custodian ID", "Reference No.");
					}
					{
						rowheaderAssets = tblAssets.getRowHeader();
						scrollAssets.setRowHeaderView(rowheaderAssets);
						scrollAssets.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(3,3));
				pnlmain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					{
						pnlSouthextra1= new JPanel();
						pnlSouth.add(pnlSouthextra1,BorderLayout.WEST);
						pnlSouthextra1.setPreferredSize(new Dimension(200, 0));
						
					}
					{
						btnPreview= new JButton("Preview");
						pnlSouth.add(btnPreview, BorderLayout.CENTER);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								previewSticker(modelAssets);
							}
						});
					}
					{
						pnlSouthextra2= new JPanel();
						pnlSouth.add(pnlSouthextra2,BorderLayout.EAST);
						pnlSouthextra2.setPreferredSize(new Dimension(200, 0));
					}
				}
				
			}
			
		}

	}
	private void previewSticker(modelPrintAssetSticker model){
		
		ArrayList<String> listAsset_no = new ArrayList<String>();
		String filterAssetType = cbType.getSelectedItem().toString();
		
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_asset where emp_code = '"+UserInfo.EmployeeCode+"'", true);
		dbExec.commit();
		
		for(int x=0; x<model.getRowCount(); x++){
			if ((Boolean) model.getValueAt(x, 0)) {
				dbExec = new pgUpdate();
				dbExec.executeUpdate("insert into tmp_asset(asset_no, emp_code, asset_type) values (getinteger('"+(String) model.getValueAt(x, 1).toString()+"'), '"+UserInfo.EmployeeCode+"', '"+filterAssetType+"')", true);
				dbExec.commit();
			}
		} 
		
		System.out.println("");
		//System.out.println("custodian: "+current_cust);
		System.out.println("emp_code: "+UserInfo.EmployeeCode);
		
		Object[] option= {"Big Sticker","Small Sticker"};
		int Option=JOptionPane.showOptionDialog(getTopLevelAncestor(), "Please select sticker size.", "Sticker size option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
		System.out.println("Option: "+Option);

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("asset_type",filterAssetType);
		mapParameters.put("emp_code",UserInfo.EmployeeCode);
		try{
			mapParameters.put("co_logo_vdc", this.getClass().getClassLoader().getResourceAsStream("Images/verdant_logo.bmp"));
			mapParameters.put("co_logo_cdc", this.getClass().getClassLoader().getResourceAsStream("Images/cenqlogo.png"));
			mapParameters.put("co_logo_adc", this.getClass().getClassLoader().getResourceAsStream("Images/acer_logo.bmp"));
		}catch(NullPointerException e){}
		
		if(filterAssetType == "Non-fixed Asset") {
			System.out.println("Non-fixed Asset");
			if(Option == JOptionPane.NO_OPTION){
				System.out.println("No Option");
				FncReport.generateReport("/Reports/rptMultiple_NF_sticker.jasper", "Assetcode Sticker", mapParameters);
			}
			
		}else {
			if(Option == JOptionPane.YES_OPTION){
				FncReport.generateReport("/Reports/rptMultipleAssetSticker.jasper", "Assetcode Sticker", mapParameters);
			}
			if(Option == JOptionPane.NO_OPTION){
				FncReport.generateReport("/Reports/rptMultipleAssetSticker2.jasper", "Assetcode Sticker", mapParameters);
			}
		}
		
		
	}	
	
	public static void filterbyCustodian(DefaultTableModel model, JList rowHeader, String emp_code){
		
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		String filterAssetType = cbType.getSelectedItem().toString();
		String strSQL = null;
		
		if(filterAssetType == "Fixed Asset") {
			strSQL="select false, \n" + 
					"a.asset_no,\n" + 
					"a.asset_code,\n" + 
					"a.asset_name,\n" + 
					"a.date_acquired,\n"+
					"lpad(a.current_cust::text, 6, '0'::text),\n" +
					"format('%s,%s %s.', c.last_name,c.first_name,left(c.middle_name,1) ) as custodian, \n" + 
					"a.reference_no, \n" +
					"format('%s',left(a.status,1)) as status \n"+
					"from tbl_asset a,\n" + 
					"em_employee b,\n" + 
					"rf_entity c\n" + 
					"where a.current_cust='"+emp_code+"'\n" + 
					"and asset_cost >= 5000\n" +
					"and a.current_cust=b.emp_code::int\n" + 
					"and c.entity_id=b.entity_id\n" +
					"and a.status='A'\n" +
					"order by a.asset_no";
		}
		
		if(filterAssetType == "Non-fixed Asset") {
			strSQL="select false, \n" + 
					"a.object_id,\n" + 
					"null,\n" + 
					"a.object_name,\n" + 
					"a.date_acquired,\n"+
					"lpad(a.current_cust::text, 6, '0'::text),\n" +
					"format('%s,%s %s.', c.last_name,c.first_name,left(c.middle_name,1) ) as custodian, \n" + 
					"a.reference_no, \n" +
					"format('%s',left(a.status_id,1)) as status \n"+
					"from tbl_nonfixedasset a,\n" + 
					"em_employee b,\n" + 
					"rf_entity c\n" + 
					"where a.current_cust='"+emp_code+"'\n" + 
					"and asset_cost between 1500 and 4999\n" +
					"and a.current_cust=b.emp_code\n" + 
					"and c.entity_id=b.entity_id\n" +
					"and a.status_id='Active'\n" +
					"order by a.asset_no";
		}
	
	FncSystem.out("Filter by Custodian", strSQL);
	
	pgSelect db = new pgSelect();
	db.select(strSQL);
	
	if(db.isNotNull()){
		for(int x=0; x < db.getRowCount(); x++){

			//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
			model.addRow(db.getResult()[x]);
			
			//For every row added in model, the table header will also add the row number.
			listModel.addElement(model.getRowCount());
		}
	}
	
	
	}
	
	public static String getCustodian(){
		
		String strsql="select a.emp_code,  b.entity_name,e.co_id,e.company_name,e.company_logo,d.division_code,d.division_name \n" + 
				"from  em_employee a\n" + 
				"left join rf_entity b ON a.entity_id=b.entity_id\n" + 
				"left join mf_department as c on a.dept_code=c.dept_code\n"+
				"left join mf_division as d on c.division_code=d.division_code \n"+
				"left join mf_company e on a.co_id=e.co_id\n"+
				"where not emp_status='I'\n";
				//"AND not emp_status='I' \n";
		
		FncSystem.out("Get Custodian", strsql);
		
		return strsql;
		
	}
	
	public static String getDepartment() {
		String strql="select \n" + 
				"a.dept_code,\n" + 
				"a.dept_name,\n" + 
				"a.dept_alias \n" + 
				"from mf_department a,\n" + 
				"mf_division b \n" + 
				"where a.division_code=b.division_code \n" + 
				"order by a.dept_name\n"; 
		
		FncSystem.out("Get Department", strql);
		
		return strql;
				
	}
	
	
	private void displayAllAssets() {
		modelAssets.clear();
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderAssets.setModel(listModel);//Setting of listModel into rowHeader.
		String filterAssetType = cbType.getSelectedItem().toString();
		
		String strSQL = null;
		if (filterAssetType == "Fixed Asset") {
			strSQL = 
					"select false,a.asset_no, \n" + 		
					"a.asset_code, \n" + 
					"a.asset_name,\n" + 
					"a.date_Acquired,\n" + 
					"lpad(a.current_cust::text, 6, '0'::text),\n" + 
					"get_employee_name(lpad(a.current_cust::text, 6, '0'::text)), \n" + 
					"a.reference_no, \n" + 
					"format('%s',left(a.status,1)) as status, \n"+
					"(case when c.company_name is null then 'ACERHOMES DEVELOPMENT CORPORATION' else c.company_name end) as company_name,\n" + 
					"(case when c.company_logo is null then 'acer_logo.bmp' else c.company_logo end  ) as company_logo \n"+
					"from tbl_asset a \n" + 
					"left join em_employee b on a.current_cust::int=b.emp_code::int\n" + 
					"left join mf_company c on b.co_id=c.co_id \n"+
					"where get_employee_name(lpad(a.current_cust::text, 6, '0'::text)) != '' \n"+
					"AND lpad(a.current_cust::text, 6, '0'::text) in (select emp_code from em_employee)\n"+
					"AND a.status in ('Active', 'A')\n"+
					"and a.asset_cost >= 5000\n" +
					"order by asset_no,get_employee_name(lpad(a.current_cust::text, 6, '0'::text))";
		}
		
		if (filterAssetType == "Non-fixed Asset") {
			strSQL = 
					"select false, a.object_id, \n" + 		
					"null, \n" + 
					"a.object_name,\n" + 
					"a.date_Acquired,\n" + 
					"lpad(a.current_cust::text, 6, '0'::text),\n" + 
					"get_employee_name(lpad(a.current_cust::text, 6, '0'::text)), \n" + 
					"a.reference_no, \n" + 
					"format('%s',left(a.status_id,1)) as status, \n"+
					"(case when c.company_name is null then 'ACERHOMES DEVELOPMENT CORPORATION' else c.company_name end) as company_name,\n" + 
					"(case when c.company_logo is null then 'acer_logo.bmp' else c.company_logo end  ) as company_logo \n"+
					"from tbl_nonfixedasset a \n" + 
					"left join em_employee b on a.current_cust::int=b.emp_code::int\n" + 
					"left join mf_company c on b.co_id=c.co_id \n"+
					"where get_employee_name(lpad(a.current_cust::text, 6, '0'::text)) != '' \n"+
					"AND lpad(a.current_cust::text, 6, '0'::text) in (select emp_code from em_employee)\n"+
					"AND a.status_id in ('Active', 'A')\n"+
					//"and a.asset_cost between 1500 and 4999\n" + //Comment by Erick 2023-07-19  
					"order by object_id,get_employee_name(lpad(a.current_cust::text, 6, '0'::text))";
		}
				
				FncSystem.out("Display All Assets", strSQL);

			pgSelect db = new pgSelect();
			db.select(strSQL);
		
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
		
					//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
					 modelAssets.addRow(db.getResult()[x]);
		
					//For every row added in model, the table header will also add the row number.
					listModel.addElement(modelAssets.getRowCount());
				}
			}
			
			
			tblAssets.packAll();
	}
}