package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lowagie.text.Jpeg;

import Buyers.LegalandLiaisoning.NewTCTAndTDTagging;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.ConstructionManagement._GenerateForUnitTurnOverOrientation;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class MasterList extends _JInternalFrame implements ActionListener, _GUI {
	
	public static String title = "MasterList";
	public static Dimension frameSize = new Dimension(496, 350);//510, 250

	private static final long serialVersionUID = -6266060783110851200L;
	private JPanel pnlMain;
	private JPanel pnl1;
	private JPanel pnl2;
	private JPanel pnl3;
	private JPanel pnl4;
	private JPanel pnl5;
	private JPanel pnl6;
	private JPanel pnl7;
	private JPanel pnl8;
	private JPanel pnl1xtra;
	private JPanel pnlAllCategory;
	private JCheckBox chkAllcategory;
	private JLabel lblCategory;
	private JTextField txtCategory;
	private _JLookup lookupCategory;
	private JPanel pnl3xtra;
	private JCheckBox chkAllDivision;
	private JPanel pnl4xtra;
	private JLabel lblDivision;
	private _JLookup lookupDivision;
	private JTextField txtDivision;
	private JPanel pnl5xtra;
	private JCheckBox chkAllDepartment;
	private JLabel lblDepartment;
	private _JLookup lookupDepartment;
	private JTextField txtDepartment;
	private JPanel pnl7xtra;
	private JCheckBox chkAllSupplier;
	private JLabel lblSupplier;
	private _JLookup lookSupplier;
	private JTextField txtSupplier;
	private JPanel pnlPreview;
	private JPanel pnl9;
	private JButton btnPreview;
	private JPanel pnl9xtra;
	private JPanel pnlxtra;
	private _JLookup lookupSupplier;
	private JPanel pnl10;
	private DefaultComboBoxModel cmbSortByModel;
	private static JComboBox cmbSortBy;
	private JLabel lblcmbSortByModel;
	
	String cmbValue="";
	
	
	public MasterList() {
		super(title, true, true, true, true);
		initGUI();
	}
	public void initGUI() {
		
		this.setPreferredSize(new Dimension(600, 420));
		this.setBounds(1, 1, 600, 420);
		this.setTitle("Master List Options");
		this.setIconifiable(true);
		this.setClosable(true);
		this.setVisible(true);
		
		{
			pnlMain= new JPanel(new GridBagLayout());
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(JTBorderFactory.createTitleBorder("Set filters"));
			{
				GridBagConstraints c = new GridBagConstraints();
				c.fill = c.BOTH;
				
				c.gridx = 0; 
				c.gridy = 0; 
				c.weightx = 1; 
				c.weighty = 1;	
				c.ipady = 70; 
				JPanel panel = new JPanel(new BorderLayout());
				panel.add(getfilters(), BorderLayout.CENTER);
				pnlMain.add(panel, c);
			}
			{
				GridBagConstraints c = new GridBagConstraints();
				c.fill = c.BOTH;
				
				c.gridx = 0; 
				c.gridy = 1; 
				c.weightx = 1; 
				c.weighty = 1;	
				//c.ipady = 5; 
				JPanel panel = new JPanel(new BorderLayout());
				panel.add(getsortby(), BorderLayout.CENTER);
				pnlMain.add(panel, c);
			}
			{
				GridBagConstraints c = new GridBagConstraints();
				c.fill = c.BOTH;
				
				c.gridx = 0; 
				c.gridy = 2; 
				c.weightx = 1; 
				c.weighty = 1;	
				c.ipady = 5; 
				JPanel panel = new JPanel(new BorderLayout());
				panel.add(getbutton());
				pnlMain.add(panel, c);
			}

		}
	}
	
	private JPanel getfilters() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel pnl = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		//c.ipady = 20;
		c.insets = new Insets(0, 3, 3, 3);
		//column1
		{
			c.gridx = 0; 
			c.gridy = 1; 
			c.weightx = .005;
			c.weighty = 1; 
			c.ipadx = 5;
			
			lblCategory= new JLabel("Category ");
			lblCategory.setFont(FncGlobal.sizeLabel);
			lblCategory.setBorder(LINE_BORDER);
			pnl.add(lblCategory, c);
		}
		{
			c.gridx = 0; 
			c.gridy = 3; 
			c.weightx = .005;
			c.weighty = 1; 
			c.ipadx = 5;
			
			lblDivision= new JLabel("Division   ");
			lblDivision.setFont(FncGlobal.sizeLabel);
			lblDivision.setBorder(LINE_BORDER);
			pnl.add(lblDivision, c);
		}
		{
			c.gridx = 0; 
			c.gridy = 5; 
			c.weightx = .005;
			c.weighty = 1; 
			c.ipadx = 5;
			
			lblDepartment= new JLabel("Department");
			lblDepartment.setFont(FncGlobal.sizeLabel);
			lblDepartment.setBorder(LINE_BORDER);
			pnl.add(lblDepartment, c);
		}
		{
			c.gridx = 0; 
			c.gridy = 7; 
			c.weightx = .005;
			c.weighty = 1; 
			c.ipadx = 5;
			
			lblSupplier= new JLabel("Supplier ");
			lblSupplier.setFont(FncGlobal.sizeLabel);
			lblSupplier.setBorder(LINE_BORDER);
			pnl.add(lblSupplier, c);
		}
		/*{
			c.gridx = 0; 
			c.gridy = 8; 
			c.weightx = .005;
			c.weighty = 1; 
			c.ipadx = 5;
			
			lblcmbSortByModel=new JLabel("Sort by  ");
			lblcmbSortByModel.setBorder(LINE_BORDER);
			pnl.add(lblcmbSortByModel, c);
		}*/
		
		//column2
		{
			c.gridx = 1; 
			c.gridy = 0; 
			c.weightx = 0;
			c.weighty = 1; 
			c.ipadx = 5;
			
			chkAllcategory= new JCheckBox("All Category");
			pnl.add(chkAllcategory, c);
			chkAllcategory.setFont(FncGlobal.sizeLabel);
			chkAllcategory.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED){
						lookupCategory.setEnabled(false);
						txtCategory.setEnabled(false);
						
					}
					if(e.getStateChange()==ItemEvent.DESELECTED){
						lookupCategory.setEnabled(true);
						txtCategory.setEnabled(true);
						
					}
				}
			});
		}
		{
			c.gridx = 1; 
			c.gridy = 1; 
			c.weightx = .002;
			c.weighty = 1;
			c.ipadx = 5;
			
			lookupCategory= new _JLookup();
			lookupCategory.setReturnColumn(0);
			lookupCategory.setFont(FncGlobal.sizeTextValue);
			pnl.add(lookupCategory, c);
			lookupCategory.setLookupSQL(_MasterList.getCategory());
			lookupCategory.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					Object[] data=((_JLookup)event.getSource()).getDataSet();
					
					if(data !=null){
						String category_name= (String) data[1];
						txtCategory.setText(category_name);
					}
				}
			});
		}
		{
			c.gridx = 1; 
			c.gridy = 2; 
			c.weightx = 0;
			c.weighty = 1; 
			c.ipadx = 5;
			
			chkAllDivision= new JCheckBox("All Division");
			chkAllDivision.setFont(FncGlobal.sizeLabel);
			chkAllDivision.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED){
						lookupDivision.setEnabled(false);
						txtDivision.setEnabled(false);
						
					}
					if(e.getStateChange()==ItemEvent.DESELECTED){
						lookupDivision.setEnabled(true);
						txtDivision.setEnabled(true);
					}
				}
			});

			pnl.add(chkAllDivision, c);
		}
		{
			c.gridx = 1; 
			c.gridy = 3; 
			c.weightx = .002;
			c.weighty = 1;
			c.ipadx = 5;
			
			lookupDivision= new _JLookup();
			lookupDivision.setReturnColumn(0);
			lookupDivision.setFont(FncGlobal.sizeTextValue);
			lookupDivision.setLookupSQL(_MasterList.getDivision());
			lookupDivision.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					Object [] data= ((_JLookup)event.getSource()).getDataSet();
					
					if(data!=null){
						String division_name= (String) data[1];
						txtDivision.setText(division_name);
						lookupDepartment.setLookupSQL(getDepartment(lookupDivision.getValue()));
					}
				}
			});
			pnl.add(lookupDivision, c);
		}
		{	
			c.gridx = 1; 
			c.gridy = 4; 
			c.weightx = 0;
			c.weighty = 1; 
			c.ipadx = 5;
			
			chkAllDepartment= new JCheckBox("All Department");
			chkAllDepartment.setFont(FncGlobal.sizeLabel);
			chkAllDepartment.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED){
						lookupDepartment.setEnabled(false);
						txtDepartment.setEnabled(false);
					}
					if(e.getStateChange()==ItemEvent.DESELECTED){
						lookupDepartment.setEnabled(true);
						txtDepartment.setEnabled(true);
					}
				}
			});
			pnl.add(chkAllDepartment,c);
	
		}
		{
			c.gridx = 1; 
			c.gridy = 5; 
			c.weightx = .002;
			c.weighty = 1;
			c.ipadx = 5;
			
			lookupDepartment= new _JLookup();
			lookupDepartment.setReturnColumn(0);
			lookupDepartment.setFont(FncGlobal.sizeTextValue);
			lookupDepartment.setLookupSQL(getDepartment(lookupDivision.getValue()));
			//lookupDepartment.setLookupSQL(_MasterList.getDepartment());
			lookupDepartment.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					Object[] data = ((_JLookup)event.getSource()).getDataSet();
					
					if(data!=null){
						String department_name= (String) data[1];
						txtDepartment.setText(department_name);
					}
				}
			});
			pnl.add(lookupDepartment, c);
		}
		{
			c.gridx = 1; 
			c.gridy = 6; 
			c.weightx = 0;
			c.weighty = 1; 
			c.ipadx = 5;
			
			chkAllSupplier= new JCheckBox("All Supplier");
			chkAllSupplier.setFont(FncGlobal.sizeLabel);
			chkAllSupplier.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED){
						lookupSupplier.setEnabled(false);
						txtSupplier.setEnabled(false);
					}
					if(e.getStateChange()==ItemEvent.DESELECTED){
						lookupSupplier.setEnabled(true);
						txtSupplier.setEnabled(true);
						
						
					}
						
				}
			});
			pnl.add(chkAllSupplier,c);
	
		}
		
		{
			c.gridx = 1; 
			c.gridy = 7; 
			c.weightx = .002;
			c.weighty = 1;
			c.ipadx = 5;
			
			lookupSupplier= new _JLookup();
			lookupSupplier.setReturnColumn(0);
			lookupSupplier.setFont(FncGlobal.sizeTextValue);
			lookupSupplier.setLookupSQL(_MasterList.getSupplier());
			lookupSupplier.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					Object[] data= ((_JLookup)event.getSource()).getDataSet();
					if(data !=null){
						String supplier_name= (String) data[1];
						txtSupplier.setText(supplier_name);
					}
				}
			});
			pnl.add(lookupSupplier, c);
		}
		/*{
			c.gridx = 1; 
			c.gridy = 8; 
			c.weightx = .002;
			c.weighty = 1;
			c.ipadx = 5;
			
			cmbSortByModel= new DefaultComboBoxModel( new String[]{"current_cust","asset_no"});
			cmbSortBy = new JComboBox();
			cmbSortBy.setModel(cmbSortByModel);
			cmbSortBy.setSelectedItem(null);
			cmbSortBy.addActionListener(this);
			cmbSortBy.setSelectedIndex(0);
			pnl.add(cmbSortBy, c);
		}*/
		
		//column3
		{
			c.gridx = 2; 
			c.gridy = 1; 
			c.weightx = .05;
			c.weighty = 1;
			c.ipadx = 5;
			
			txtCategory= new JTextField();
			txtCategory.setFont(FncGlobal.sizeTextValue);
			pnl.add(txtCategory, c);
		}
		{
			c.gridx = 2; 
			c.gridy = 3; 
			c.weightx = .05;
			c.weighty = 1;
			c.ipadx = 5;
			
			txtDivision= new JTextField();
			txtDivision.setFont(FncGlobal.sizeTextValue);
			pnl.add(txtDivision, c);
		}
		{
			c.gridx = 2; 
			c.gridy = 5; 
			c.weightx = .05;
			c.weighty = 1;
			c.ipadx = 5;
			
			txtDepartment= new JTextField();
			txtDepartment.setFont(FncGlobal.sizeTextValue);
			pnl.add(txtDepartment, c);
		}
		{
			c.gridx = 2; 
			c.gridy = 7; 
			c.weightx = .05;
			c.weighty = 1;
			c.ipadx = 5;
			
			txtSupplier= new JTextField();
			txtSupplier.setFont(FncGlobal.sizeTextValue);
			pnl.add(txtSupplier, c);
		}
		
		return pnl;
	}
	
	private JPanel getsortby() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel pnl = new JPanel(new GridBagLayout()); 
		//pnl.setBackground(Color.blue);
		c.fill = c.BOTH; 
		//c.ipady = 5;
		c.insets = new Insets(3, 3, 3, 3);
		{
			c.gridx = 0; 
			c.gridy = 1; 
			c.weightx = .010;
			c.weighty = 1; 
			//c.ipadx = 5;
			
			lblcmbSortByModel=new JLabel("Sort by  ");
			lblcmbSortByModel.setBorder(LINE_BORDER);
			lblcmbSortByModel.setFont(FncGlobal.sizeLabel);
			//lblcmbSortByModel.setPreferredSize(new Dimension(50, 0));
			pnl.add(lblcmbSortByModel, c);
		}
		/*{
			c.gridx = 1; 
			c.gridy = 1; 
			c.weightx = .002;
			c.weighty =1;
			
			JLabel lblxtra = new JLabel("");
			pnl.add(lblxtra, c);
		}*/
		{
			c.gridx = 2; 
			c.gridy = 1; 
			c.weightx = .05;
			c.weighty =1;
			//c.ipadx = 5;
			
			cmbSortByModel= new DefaultComboBoxModel( new String[]{"current_cust","asset_no"});
			cmbSortBy = new JComboBox();
			cmbSortBy.setModel(cmbSortByModel);
			cmbSortBy.setFont(FncGlobal.sizeTextValue);
			cmbSortBy.setSelectedItem(null);
			cmbSortBy.addActionListener(this);
			cmbSortBy.setSelectedIndex(0);
			pnl.add(cmbSortBy, c);
		}
		
		return pnl;
	}
	
	private JPanel getbutton() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel pnl = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		//c.ipady = 5;
		c.insets = new Insets(3, 3, 3, 3);
		{
			c.gridx = 0; 
			c.gridy = 0; 
			c.weightx = 1;
			c.weighty = 1; 
			c.ipadx = 5;
			btnPreview= new JButton("Preview");
			btnPreview.setFont(FncGlobal.sizeControls);
			btnPreview.setFocusPainted(true);
			btnPreview.setPreferredSize(new Dimension(100, 30));
			btnPreview.addActionListener(new ActionListener() {
			

				public void actionPerformed(ActionEvent e) {
					if(toPreview()){
						
						cmbValue= (String)cmbSortBy.getSelectedItem();
						String cmbSortByValue =cmbValue.toString();
						
						printMasterList(
								//(chkAllcategory.isSelected()?"All":txtCategory.getText()),
								(chkAllcategory .isSelected()?"All":lookupCategory.getValue()),
								(chkAllDivision.isSelected()?"All":lookupDivision.getValue()),
								(chkAllDepartment.isSelected()?"All":lookupDepartment.getValue()),
								(chkAllSupplier.isSelected()?"All":lookupSupplier.getValue()),
								//(String) (cmbSortBy.getSelectedItem()));
								(cmbSortByValue));
						
						System.out.println(cmbSortByValue);
					}else
					{
					JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please fill-up required fields.", "Preview", JOptionPane.WARNING_MESSAGE);
					}
					
				}
			});
			pnl.add(btnPreview);
		}
	
		
		
		return pnl;
	}
	
	private Boolean toPreview(){
		if(!chkAllcategory.isSelected() && lookupCategory.getText().equals("")
				|| !chkAllDivision.isSelected() && lookupDivision.getText().equals("")
				|| !chkAllDepartment.isSelected() && lookupDepartment.getText().equals("")
				|| !chkAllSupplier.isSelected() && lookupSupplier.getText().equals("")){
			return false;
		}else{
			return true;
		}
	}
	public static String getCategory(){
		return "select category_id as \"ID\", category_name as \"Category Name\" from tbl_category order by category_name";	
	}
	public static String getSupplier(){
		return "select to_char(supp_id, 'FM000000') as \"ID\", supp_name as \"Supplier Name\" from tbl_supplier order by supp_name";
	}
	public static String getDepartment(String div_id){
		return"select dept_code as \"ID\", dept_name as department_name from mf_department where division_code='"+div_id+"' order by dept_name ";
	}
	public static String getDivision(){
		return"select division_code as \"ID\", division_name as \"div_name\"from mf_division  order by division_name";
	}
	public static void printMasterList(String category_id, String div_id, String dept_id, String supp_id, String sort_by){
		
		//String sortby= (String) cmbSortBy.getSelectedItem();
		
		
		String strSQL = 
			"select * from (select \n"+
			"a.asset_code, \n"+
			"a.item_id, \n"+
			"b.category_name, \n"+
			"a.asset_name, \n"+
			"a.original_cost, \n"+
			"a.asset_ulm, \n"+
			"a.asset_mon_dep, \n"+
			"a.asset_no, \n"+
			"a.asset_serial, \n"+
			"a.asset_model, \n"+
			"a.asset_bk_val, \n"+
			"a.asset_ret_cost, \n"+
			"a.date_acquired, \n"+
			"a.from_dep, \n"+
			"a.to_dep, \n"+
			"a.date_retired, \n"+
			"a.owned, \n"+
			"a.insured, \n"+
			"a.insured_value, \n"+
			"i.remarks, \n"+
			"get_asset_status(substr(a.status, 1, 1)) as status, \n"+
			"case e.emp_fname when 'STOCKROOM' then 'STOCKROOM' else e.emp_lname || ', ' || e.emp_fname || ' ' || e.emp_mi end as current_cust \n"+
			"as current_cust, \n"+
			"f.div_alias as division_alias, \n"+
			"g.dept_alias, \n"+
			"h.supp_name \n"+
			
			"from \n"+
			"tbl_asset a \n"+
			"left join tbl_item d \n"+
			"on a.item_id = d.item_id \n"+
			"left join tbl_category b \n"+
			"on b.category_id = d.category_id \n"+
			"left join tbl_user e \n"+
			"on a.current_cust = e.emp_id \n"+
			"left join rf_division f \n"+
			"on e.rf_div_id = f.div_id \n"+
			"left join rf_department g \n"+
			"on e.rf_dept_id = g.dept_id \n"+
			"left join tbl_supplier h \n"+
			"on a.supp_id = h.supp_id \n"+
			"left join tbl_asset_history i \n"+
			"on a.asset_no=i.asset_no and a.current_cust=i.current_cust \n" +
			
			"where substr(a.status, 1, 1) = 'A' \n"+
			"and a.asset_bk_val > 0.00 \n";

		if(!category_id.equals("All")){
			strSQL = strSQL + "and b.category_id='"+category_id+"' \n";
		}
		if(!div_id.equals("All")){
			strSQL = strSQL + "and e.rf_div_id='"+div_id+"' \n";
		}
		if(!dept_id.equals("All")){
			strSQL = strSQL + "and e.rf_dept_id='"+dept_id+"' \n";
		}
		if(!supp_id.equals("All")){
			strSQL = strSQL + "and a.supp_id='"+supp_id+"' \n";
		}
		if (cmbSortBy.getSelectedItem().equals("current_cust")){
			sort_by="current_cust";
		}
		else
		{
			sort_by="asset_no";
		}
		
		//strSQL = strSQL + "order by division_alias, dept_alias, current_cust, asset_no) a where current_cust is not null \n";
		
		Map<String, Object> mapParameters = new HashMap<String,Object>();
		
		mapParameters.put("category_id", category_id);
		mapParameters.put("div_id", div_id);
		mapParameters.put("dept_id", dept_id);
		mapParameters.put("supp_id", supp_id);
		mapParameters.put("sort_by",sort_by );
		
		System.out.println(category_id);
		System.out.println(div_id);
		System.out.println(dept_id);
		System.out.println(supp_id);
		
		FncReport.generateReport("/Reports/MasterList.jasper", "Master List", mapParameters);

		/*try {
			functions.FncSelectRecords.select(strSQL);
			
			Map<String, Object> mapMasterlist = new HashMap<String, Object>();
			mapMasterlist.put("preparedBy", GlbVariables.emp_alias);

			JasperPrint print = JasperFillManager.fillReport("reports/rptMasterList.jasper", mapMasterlist, new JRResultSetDataSource(functions.FncSelectRecords.rs));
			
			if(print.getPages().size() > 0){
				JasperViewer printAssetSticker = new JasperViewer(print, false);
				printAssetSticker.setTitle("Masterlist");
				printAssetSticker.isMaximumSizeSet();
				printAssetSticker.setVisible(true);
			}else{
				JOptionPane.showMessageDialog(GlbVariables.parentFrame, "No Reports Generated!", "Masterlist", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (JRException e) { }*/
	}
}
