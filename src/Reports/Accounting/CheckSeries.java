package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Buyers.ClientServicing._HoldingAndReservation;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class CheckSeries extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Check Series";
	static Dimension frameSize = new Dimension(500, 600);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlSouth;

	private JLabel lblDocsType;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblBank;
	private JLabel lblBankBranch;
	private JLabel lblBankAccount;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtBranch;	
	
	private JButton btnPreview;
	private JButton btnCancel;
	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;
	
	JComboBox cmbBank;
	JComboBox cmbBankBranch;
	JComboBox cmbBankAccount;
	JComboBox cmbPayee;
	JComboBox cmbStatus;
	JComboBox cmbDocsType;

	String company;
	String company_logo;	
	String co_id 		= "";
	String branch_name	= "";
	String branch_id 	= "";
	String proj_id 		= "";	
	String proj_name 	= "";
	String bank_id 	= "";
	
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	private JPanel pnlCenter_b;

	private JLabel lblDateTo;

	public CheckSeries() {
		super(title, false, true, false, true);
		initGUI();
	}

	public CheckSeries(String title) {
		super(title);
		initGUI();
	}

	public CheckSeries(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(545, 400));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(541, 400));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(531, 265));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Check Details"));// XXX
				
				{
					pnlNorthWest = new JPanel(new GridLayout(7,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					//pnlNorthWest.setPreferredSize(new java.awt.Dimension(135, 100));
					
					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlNorthWest.add(lblCompany);
					}
					{
						lblDocsType = new JLabel("Docs Type", JLabel.TRAILING);
						pnlNorthWest.add(lblDocsType);
						lblDocsType.setEnabled(false);	
					}
					{
						JLabel lblPayee = new JLabel("Payee", JLabel.TRAILING);
						pnlNorthWest.add(lblPayee);
						//lblProject.setEnabled(false);	
					}
					{
						JLabel lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlNorthWest.add(lblStatus);
						//lblProject.setEnabled(false);	
					}
					{
						lblBank = new JLabel("Bank", JLabel.TRAILING);
						pnlNorthWest.add(lblBank, BorderLayout.WEST);
						lblBank.setEnabled(false);	
					}
					{
						lblBankBranch = new JLabel("Bank Branch", JLabel.TRAILING);
						pnlNorthWest.add(lblBankBranch, BorderLayout.WEST);
						lblBankBranch.setEnabled(false);	
					}
					{
						lblBankAccount = new JLabel("Bank Account", JLabel.TRAILING);
						pnlNorthWest.add(lblBankAccount, BorderLayout.WEST);
						lblBankAccount.setEnabled(false);	
					}
					
				}
				pnlNorthCenter = new JPanel(new GridLayout(7,1, 5, 5));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				pnlNorthCenter.setPreferredSize(new java.awt.Dimension(70, 100));
				{
					lookupCompany = new _JLookup(null, "Company");
					pnlNorthCenter.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								co_id 		= (String) data[0];	
								company		= (String) data[1];	
								company_logo = (String) data[3];

								String name = (String) data[1];						
								txtCompany.setText(name);
								//lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
							
								lblDocsType.setEnabled(true);	
								//lookupProject.setText("");
								//lookupProject.setEnabled(true);
								//txtProject.setEnabled(true);
								//txtProject.setText("");									

								lblBank.setEnabled(true);	
								//txtBranch.setEnabled(true);
								//txtBranch.setText("");
								
								lblDateFrom.setEnabled(true);				
								lblDateTo.setEnabled(true);				
								dteDateFrom.setEnabled(true);
								dateDateTo.setEnabled(true);

								KEYBOARD_MANAGER.focusNextComponent();
								btnCancel.setEnabled(true);
								btnPreview.setEnabled(true);
							}
						}
					});
				}
				/*{
					lookupProject = new _JLookup(null, "Project");
					pnlNorthCenter.add(lookupProject, BorderLayout.WEST);
					lookupProject.setReturnColumn(0);
					lookupProject.setEnabled(false);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								
								proj_id = (String) data[0];	
								proj_name = (String) data[1];						
								txtProject.setText(proj_name);

								//KEYBOARD_MANAGER.focusNextComponent();
								btnCancel.setEnabled(true);
							}
						}
					});
				}*/
				
				pnlNorthEast = new JPanel(new GridLayout(7, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(375, 159));
				
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.EAST);
					txtCompany.setEditable(false);
				}
				/*{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.EAST);
					txtProject.setEditable(false);
					txtProject.setEnabled(false);
				}*/
				{
					cmbDocsType = new JComboBox(getDocsType());
					pnlNorthEast.add(cmbDocsType, BorderLayout.EAST);
					cmbDocsType.setActionCommand("Payee");
					cmbDocsType.addActionListener(this);
				}
				{
					cmbPayee = new JComboBox(getPayee());
					pnlNorthEast.add(cmbPayee, BorderLayout.EAST);
					cmbPayee.setActionCommand("Payee");
					cmbPayee.addActionListener(this);
				}
				{
					cmbStatus = new JComboBox(getStatus());
					pnlNorthEast.add(cmbStatus, BorderLayout.EAST);
					cmbStatus.setActionCommand("Status");
					cmbStatus.addActionListener(this);
				}
				{
					cmbBank = new JComboBox(getBank());
					pnlNorthEast.add(cmbBank, BorderLayout.EAST);
					cmbBank.setActionCommand("Bank");
					cmbBank.addActionListener(this);
				}
				{
					cmbBankBranch = new JComboBox(getBankBranch1());
					pnlNorthEast.add(cmbBankBranch, BorderLayout.EAST);
					cmbBankBranch.setActionCommand("Bank Branch");
					cmbBankBranch.addActionListener(this);
				}
				{
					cmbBankAccount = new JComboBox(getBankAccount1());
					pnlNorthEast.add(cmbBankAccount, BorderLayout.EAST);
					cmbBankAccount.setActionCommand("Bank Account");
					cmbBankAccount.addActionListener(this);
				}
				{
					pnlCenter_b = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlMain.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));	
					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder("Period - Check Date"));// XXX

					{
						lblDateFrom = new JLabel("Date From  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(false);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(false);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}		
					{
						lblDateTo = new JLabel("Date To  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(false);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(false);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
				}				
			}				
			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new java.awt.Dimension(413, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setActionCommand("Preview");
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany,  
				dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 545, 400);  //174
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		//lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "12")==true ) { previewCancRcpt(); }
		else if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "12")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview CRB entries.","Information",JOptionPane.INFORMATION_MESSAGE); }
		
		if (e.getActionCommand().equals("Cancel")){ cancel(); }
		
		String action = e.getActionCommand();

		if(action.equals("Bank")){
			JComboBox combo = (JComboBox) e.getSource();
			String item = (String) combo.getSelectedItem();
			String bank_id = item.split("~")[0].trim();

			System.out.printf("BANK ID: %s%n", bank_id);

			if(getBankBranch(bank_id) != null){
				cmbBankBranch.setModel(new DefaultComboBoxModel(getBankBranch(bank_id)));
			}else{
				cmbBankBranch.setModel(new DefaultComboBoxModel(new String[]{""}));
			}
		}
		if(action.equals("Bank Branch")){
			JComboBox combo = (JComboBox) e.getSource();
			String item = (String) combo.getSelectedItem();
			String bank_branch_id = item.split("~")[0].trim();

			System.out.printf("BANK BRANCH ID: %s%n", bank_branch_id);

			if(getBankAccount(bank_branch_id) != null){
				cmbBankAccount.setModel(new DefaultComboBoxModel(getBankAccount(bank_branch_id)));
			}else{
				cmbBankAccount.setModel(new DefaultComboBoxModel(new String[]{""}));
			}
		}
	}
	
	private void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";		
		//proj_id = "015";	
		//proj_name = "TERRAVERDE RESIDENCES";						
		//txtProject.setText(proj_name);
		company_logo = RequestForPayment.sql_getCompanyLogo();	
					
		txtCompany.setText(company);
		//lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
	
		lblDocsType.setEnabled(true);	
		//lookupProject.setText("");
		//lookupProject.setEnabled(true);
		//txtProject.setEnabled(true);
		//txtProject.setText("");									

		lblBank.setEnabled(true);	
		lblBankBranch.setEnabled(true);
		lblBankAccount.setEnabled(true);
		/*lookupBranch.setText("");
		lookupBranch.setEnabled(true);
		txtBranch.setEnabled(true);
		txtBranch.setText("");*/
		
		lblDateFrom.setEnabled(true);				
		lblDateTo.setEnabled(true);				
		dteDateFrom.setEnabled(true);
		dateDateTo.setEnabled(true);

		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);
		
		lookupCompany.setValue(co_id);
}
	
	
	//preview
	private void previewCancRcpt(){
		
		String criteria = "Check Series";		
		String DocsType = (String) cmbDocsType.getSelectedItem();
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		String item = (String) cmbBank.getSelectedItem();
		String item2 = (String) cmbBankBranch.getSelectedItem();
		String item3 = (String) cmbStatus.getSelectedItem();
		String bankbranch = "";
		String bank = "";
		String status = "";
		if (item.equals(null) || item.equals("")) {
			bank = null;
		}else {
			bank = item.split("~")[1].trim();
		}
		if (item2.equals(null) || item2.equals("")) {
			bankbranch = null;
		}else {
			bankbranch = item2.split("~")[1].trim();
		}
		if (item3.equals(null) || item3.equals("")) {
			status = null;
		}else {
			status = item3;
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("printby", UserInfo.Alias);
		mapParameters.put("bank", bank);
		mapParameters.put("bank_account", cmbBankAccount.getSelectedItem());
		mapParameters.put("bank_branch", bankbranch);
		mapParameters.put("date_from", dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("payee", cmbPayee.getSelectedItem());
		mapParameters.put("status", status);
		mapParameters.put("subrptUnUsedCheck", this.getClass().getResourceAsStream("/Reports/subrptUnUsedCheck.jasper"));
		
		if (DocsType.equals(null) || DocsType.equals("")) {
			FncReport.generateReport("/Reports/rptCheckSeries.jasper", reportTitle, mapParameters);
		}
		if (DocsType.equals("DISBURSEMENT")) {
			FncReport.generateReport("/Reports/rptCheckSeries_dis.jasper", reportTitle, mapParameters);
		}
		if (DocsType.equals("COMMISSION")) {
			FncReport.generateReport("/Reports/rptCheckSeries_com.jasper", reportTitle, mapParameters);
		}
	}
	
	private void cancel(){		
		lookupProject.setText("");
		txtProject.setText("");		
		cmbPayee.setSelectedItem(null);
		cmbStatus.setSelectedItem(null);
		cmbBank.setSelectedItem(null);
		cmbBankBranch.setSelectedItem(null);
		cmbBankAccount.setSelectedItem(null);
		dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		
		btnCancel.setEnabled(false);
		btnPreview.setEnabled(true);	
		
		branch_name	= "";
		branch_id 	= "";
		proj_id 		= "";	
		proj_name 	= "";
	}
	private static String[] getBank() {
		String SQL = "SELECT FORMAT('%s ~ %s', TRIM(bank_id), TRIM(bank_name))\n" + 
				"FROM mf_bank\n" + 
				"ORDER BY TRIM(bank_name);";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	private static String[] getBankBranch(String bank_id) {
		String SQL = "SELECT  FORMAT('%s ~ %s', TRIM(bank_branch_id), TRIM(bank_branch_location))\n" +
				"FROM mf_bank_branch\n" + 
				"WHERE bank_id = '"+bank_id+"'\n" + 
				"ORDER BY TRIM(bank_branch_location);";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	private static String[] getBankBranch1() {
		String SQL = "SELECT  FORMAT('%s ~ %s', TRIM(bank_branch_id), TRIM(bank_branch_location))\n" + 
				"FROM mf_bank_branch\n" + 
				"ORDER BY TRIM(bank_branch_location);";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	private static String[] getBankAccount(String bank_branch_id) {
		String SQL = "SELECT TRIM(bank_acct_no)\n" + 
				"FROM mf_bank_account\n" + 
				"WHERE bank_branch_id = '"+bank_branch_id+"'\n" + 
				"ORDER BY TRIM(bank_acct_no);";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	private static String[] getBankAccount1() {
		String SQL = "SELECT TRIM(bank_acct_no)\n" + 
				"FROM mf_bank_account\n" + 
				"ORDER BY TRIM(bank_acct_no);";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	private static String[] getPayee() {
		String SQL = "SELECT DISTINCT ON (get_client_name(entity_id)) get_client_name(entity_id)\n" + 
				"FROM rf_check\n" + 
				"ORDER BY get_client_name(entity_id) ASC;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	private static String[] getStatus() {
		String SQL = "select 'FOR CHECKING' union all    \n" +
					 "select 'FOR APPROVAL' union all    \n" +
					 "select 'FOR CHECK SIGNATURE' union all    \n" +
					 "select 'FOR RELEASE TO PAYEE' union all      \n" +
					 "select 'PAID OUT' \n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	private static String[] getDocsType() {
		String SQL = "select 'DISBURSEMENT' union all    \n" +
					 "select 'COMMISSION' \n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}

}