package Accounting.FixedAssets;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Accounting.Disbursements.RequestForPayment;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class AssetCard extends _JInternalFrame implements _GUI, ActionListener {
	
private static final long serialVersionUID = 652923134846245300L;
	
	public static String title = "Asset Card";
	public static Dimension framesize= new Dimension(450, 150);
	//public static Dimension framesize= new Dimension(450, 180);

	private JPanel pnlMain;

	private JPanel pnlCustodian;

	private JPanel pnlpreview;

	private JLabel lblCustodianid;

	private JPanel pnlCustodianid;

	private _JLookup lookupCustodianid;

	private JPanel pnlXtra;

	private JLabel lblCustodianname;

	private JTextField txtcustodianname;

	private JButton btnpreview;

	private JPanel pnlCompany;

	private JLabel lblCompany;

	private _JLookup lookupCompany;

	private JPanel pnlCompanyxtra;

	private JTextField txtCompanyname;

	private JPanel pnlCompanyname;

	private JLabel lblCompanyname;

	private JButton btndetailed;

	private JPanel pnlbuttons;

	private JPanel pnlchckbox;

	private JCheckBox chckbox;
	
	public static String co_logo;
	
	public static String co_name;
	
	public static String co_alias;
	
	public static String co_id;
	
	private JComboBox cbType;
	
	
	
	public AssetCard(){
		super(title, false, true, true, true);
		initGUI();
		
	}
	public AssetCard(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public AssetCard(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(framesize);
		this.setPreferredSize(framesize);
		this.setMinimumSize(framesize);
		this.setLayout(new BorderLayout(5,5));
		{
			pnlMain = new JPanel(new GridBagLayout());
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			{
				GridBagConstraints c = new GridBagConstraints();
				c.fill = c.BOTH;
				{
					c.gridx = 0; 
					c.gridy = 0; 
					c.weightx = 1; 
					c.weighty = 0.5;	
					c.ipady = 15; 
					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("Billing Date", FncGlobal.sizeLabel));
					panel.add(getcustodianID(), BorderLayout.CENTER);
					pnlMain.add(panel, c);
				}
				{
					c.gridx = 0; 
					c.gridy = 1; 
					c.weightx = 1; 
					c.weighty = 0.5;	
					c.ipady = 15; 
					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("1", FncGlobal.sizeLabel));
					panel.add(getcustodianname(),BorderLayout.CENTER);
					pnlMain.add(panel, c);
				}
				{
					c.gridx = 0; 
					c.gridy = 2; 
					c.weightx = 1; 
					c.weighty = 0.5;	
					c.ipady = 5; 
					
					chckbox= new JCheckBox("Exclude not found assets.");
					chckbox.setSelected(true);
					chckbox.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(arg0.getStateChange()==ItemEvent.SELECTED){
								
							}
							if(arg0.getStateChange()==ItemEvent.DESELECTED){
								
							}
						}
					}); 
					pnlMain.add(chckbox, c);

				}
				{
					c.gridx = 0; 
					c.gridy = 3; 
					c.weightx = 1; 
					c.weighty = 0.5;	
					c.ipady = 5; 
					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("4", FncGlobal.sizeLabel));
					panel.add(getbutton(),BorderLayout.CENTER);
					pnlMain.add(panel, c);
				}
			}
		}
		
	}
	private void previewAssetCard(String emp_code,  String co_logo, String co_name, String co_alias,Boolean item_found, String asset_filter ){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		String strsql="";
		
		mapParameters.put("item_found", item_found);
		mapParameters.put("emp_code", Integer.valueOf(emp_code));
		mapParameters.put("company_name", co_name);
		mapParameters.put("co_alias", co_alias);
		mapParameters.put("co_logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ co_logo));
		mapParameters.put("asset_filter", asset_filter); //added by jari cruz 7.8.2022
		
		//FncSystem.out("co_name", co_name);
		System.out.println(item_found);
		System.out.println(co_logo);
		System.out.println(emp_code);
		System.out.println(co_name);
		System.out.println(co_alias);
		System.out.println(asset_filter);
		
		
		//FncReport.generateReport("/Reports/AssetCard.jasper", "Asset Card", mapParameters);
		FncReport.generateReport("/Reports/AssetCard_v3.jasper", "Asset Card", mapParameters);
		
	}
	private void previewAssetCard_notfound(String emp_code,  String co_logo, String co_name, String co_alias ){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		//Boolean item_found = false;
		//String strsql="";
		
		//mapParameters.put("item_found", item_found);
		mapParameters.put("emp_code", Integer.valueOf(emp_code));
		mapParameters.put("company_name", co_name);
		mapParameters.put("co_alias", co_alias);
		mapParameters.put("co_logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ co_logo));
		
		//FncSystem.out("co_name", co_name);
		//System.out.println(item_found);
		System.out.println(co_logo);
		System.out.println(emp_code);
		System.out.println(co_name);
		System.out.println(co_alias);
		
		
		FncReport.generateReport("/Reports/AssetCard_v2.jasper", "Not found Asset Card", mapParameters);
		
	}
	
	private void printAssetDetailedInduvidual(String custodian_id, String custodian_name, String co_logo, String co_name ){
		
		Map<String, Object> mapParameters = new HashMap<String,Object>();
		
		mapParameters.put("emp_id", Integer.valueOf(custodian_id));
		mapParameters.put("cust_name", custodian_name);
		mapParameters.put("co_logo", co_logo);
		mapParameters.put("co_name", co_name);
		mapParameters.put("co_logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ co_logo));
		FncReport.generateReport("/Reports/Detailed Assets.jasper", "Detailed Assets", mapParameters);
		
		System.out.println(custodian_id);
		System.out.println(custodian_name);
		
	}
	private JPanel getcustodianID() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel pnl = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 5;
		c.insets = new Insets(3, 3, 3, 3);
		{
			c.gridx = 0; 
			c.gridy = 0; 
			c.weightx = 0;
			c.weighty = 1; 
			c.ipadx = 5;
			
			lblCustodianid= new JLabel("Custodian ID      ");
			lblCustodianid.setFont(FncGlobal.sizeLabel);
			pnl.add(lblCustodianid, c);
		}
		{
			c.gridx = 1; 
			c.gridy = 0; 
			c.weightx = .5;
			c.weighty = 1;
			c.ipadx = 5;
			
			lookupCustodianid= new _JLookup();
			lookupCustodianid.setReturnColumn(1);
			lookupCustodianid.setFont(FncGlobal.sizeTextValue);
			pnl.add(lookupCustodianid, c);
			lookupCustodianid.setLookupSQL(_AssetCard.getCustodian());
			lookupCustodianid.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					
					Object[] data= ((_JLookup)event.getSource()).getDataSet();
					
					FncSystem.out("LookUp Custodian", lookupCustodianid.getLookupSQL());
					if(data!=null){
						String emp_name= (String) data [0];
						co_id= (String) data [2];
						co_name= (String) data[3];
						co_logo= (String) data [4];
						co_alias= (String) data [5];
						txtcustodianname.setText(emp_name);
						btndetailed.setEnabled(true);
						btnpreview.setEnabled(true);
						
					}
				}
			});
		}
		{
			c.gridx = 2; 
			c.gridy = 0; 
			c.weightx = 1;
			c.weighty = 1;
			c.ipadx = 5;
			JLabel lblextra = new JLabel("");
			pnl.add(lblextra, c);
		}
		{
			c.gridx = 2; 
			c.gridy = 0; 
			c.weightx = 1;
			c.weighty = 1;
			c.ipadx = 5;
			String[] typeStrings = { "Fixed Asset", "Non-fixed Asset" };
			cbType = new JComboBox(typeStrings);
			cbType.setSelectedIndex(0);
			JLabel lblextra = new JLabel("");
			pnl.add(cbType, c);
		}
		
		return pnl;
	}
	
	private JPanel getcustodianname() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel pnl = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 5;
		c.insets = new Insets(3, 3, 3, 3);
		{
			c.gridx = 0; 
			c.gridy = 0; 
			c.weightx = 0;
			c.weighty = 1; 
			c.ipadx = 5;
			
			lblCustodianname= new JLabel("Custodian Name");
			lblCustodianname.setFont(FncGlobal.sizeLabel);
			pnl.add(lblCustodianname, c);
		}
		{
			c.gridx = 1; 
			c.gridy = 0; 
			c.weightx = .5;
			c.weighty = 1;
			c.ipadx = 5;
			
			txtcustodianname= new JTextField();
			txtcustodianname.setFont(FncGlobal.sizeTextValue);
			pnl.add(txtcustodianname, c);
		}
		
		return pnl;
	}
	
	private JPanel getbutton() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel panbutton = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 5;
		c.insets = new Insets(0, 5, 0, 5);
		{
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.ipadx = 10;
			
			btnpreview= new JButton("Asset Card");
			btnpreview.setEnabled(false);
			btnpreview.setFont(FncGlobal.sizeControls);
			panbutton.add(btnpreview, c);
			btnpreview.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				previewAssetCard(lookupCustodianid.getValue(),  co_logo, co_name, co_alias, chckbox.isSelected(), cbType.getSelectedItem().toString());
				previewAssetCard_notfound(lookupCustodianid.getValue(), co_logo, co_name, co_alias);
				lookupCustodianid.setValue("");
				txtcustodianname.setText("");
				//chckbox.setSelected(false);
				
				}
			});
		}
		{
			c.gridx = 1;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.ipadx = 10;
			
			btndetailed=new JButton("Detailed Asset");
			btndetailed.setEnabled(false);
			btndetailed.setFont(FncGlobal.sizeControls);
			panbutton.add(btndetailed, c);
			btndetailed.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ( lookupCustodianid.getValue().equals("")|| txtcustodianname.getText().equals("")) {
						//JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please fill up required fields.", "Preview", JOptionPane.INFORMATION_MESSAGE);
						JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Plese fill up required fields", "Preview", JOptionPane.WARNING_MESSAGE);
					} else {
						printAssetDetailedInduvidual(lookupCustodianid.getValue(), txtcustodianname.getText(), co_logo, co_name);
						//lookupCompany.setValue("");
						//txtCompanyname.setText("");
						lookupCustodianid.setValue("");
						txtcustodianname.setText("");	
					}
				}
			});
		}
		
		return panbutton;
	}
}
