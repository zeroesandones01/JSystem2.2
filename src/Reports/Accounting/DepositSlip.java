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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class DepositSlip extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Deposit Slip";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;
	private JPanel pnlSouth;
	
	private JLabel lblBranch;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	
	private _JLookup lookupBranch;
	private _JLookup lookupCompany;
	
	private JTextField txtBranch;
	private JTextField txtCompany;	
	
	private JButton btnPreview;
	private JButton btnCancel;
	
	String company;
	String company_logo;	
	
	private _JDateChooser dateTo;	
	private _JDateChooser dateFrom;
	
	String co_id = "";
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	

	public DepositSlip() {
		super(title, false, true, false, true);
		initGUI();
	}

	public DepositSlip(String title) {
		super(title);
		initGUI();
	}

	public DepositSlip(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(560, 232));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(557, 246));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 160));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Check Details"));// XXX
				{
					pnlNorthWest = new JPanel(new GridLayout(2,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));
					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlNorthWest.add(lblCompany);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlNorthWest.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
																		
									company		= (String) data[1];										
									company_logo = (String) data[3];
									
									String name = (String) data[1];						
									txtCompany.setText(name);
									lookupBranch.setLookupSQL(getBranch());
									lblBranch.setEnabled(true);	
									lookupBranch.setText("");
									lookupBranch.setEnabled(true);
									txtBranch.setEnabled(true);
									txtBranch.setText("");
									
									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblBranch = new JLabel("Branch", JLabel.TRAILING);
						pnlNorthWest.add(lblBranch);
						lblBranch.setEnabled(false);	
					}
					{
						lookupBranch = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupBranch);
						lookupBranch.setReturnColumn(0);
						lookupBranch.setEnabled(false);
						lookupBranch.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									String name = (String) data[1];						
									txtBranch.setText(name);												
									btnCancel.setEnabled(true);
								}
							}
						});
					}
				}
				pnlNorthEast = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtBranch = new JTextField();
					pnlNorthEast.add(txtBranch, BorderLayout.CENTER);
					txtBranch.setEditable(false);
					txtBranch.setEnabled(false);
				}
				
				
				pnlCenter2 = new JPanel(new GridLayout(1,1,5, 5));
				pnlNorth.add(pnlCenter2, BorderLayout.SOUTH);
				pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));
				pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Deposit Details"));
				{
					pnlCriteria2 = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlCenter2.add(pnlCriteria2, BorderLayout.WEST);					
					
					{
						lblDateFrom = new JLabel("Deposit Date ", JLabel.TRAILING);
						pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2.add(dateFrom, BorderLayout.EAST);
						dateFrom.setBounds(485, 7, 125, 21);
						dateFrom.setDate(null);
						dateFrom.setEnabled(true);
						dateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}			
					{
								
						lblDateTo = new JLabel("Date To   ", JLabel.TRAILING);
						pnlCriteria2.add(lblDateTo);
						lblDateTo.setEnabled(true);	
						lblDateTo.setVisible(false);	
					}
					{
						dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2.add(dateTo, BorderLayout.EAST);
						dateTo.setBounds(485, 7, 125, 21);
						dateTo.setDate(null);
						dateTo.setEnabled(true);
						dateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dateTo.setVisible(false);	
					}
				}
						
			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupBranch,  
				 dateFrom, dateTo, btnPreview));
		this.setBounds(0, 0, 560, 232);  //174
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	private String getBranch() {
		return "SELECT branch_id, branch_name, branch_alias FROM mf_office_branch;";
	}
			
	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupBranch.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview")){		
			String criteria = "Deposit Slip";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		
		
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("branch_id", lookupBranch.getValue());
			mapParameters.put("co_id", lookupCompany.getText());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("prepared_name", UserInfo.FullName);
			mapParameters.put("date_from", dateFrom.getDate());	
			mapParameters.put("dep_date", dateFormat.format(dateFrom.getDate()));	

			FncReport.generateReport("/Reports/rptDepositSlip.jasper", reportTitle, txtBranch.getText(), mapParameters);
		}
		if(e.getActionCommand().equals("Cancel")){	
			lookupCompany.setText("");
			txtCompany.setText("");
			lblBranch.setEnabled(false);	
			lookupBranch.setEnabled(false);	lookupBranch.setText("");
			txtBranch.setEnabled(false);		txtBranch.setText("");
			dateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		}
		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";				
		company_logo = RequestForPayment.sql_getCompanyLogo();			
		txtCompany.setText(company);
		
		lookupBranch.setLookupSQL(getBranch());
		lblBranch.setEnabled(true);	
		lookupBranch.setText("");
		lookupBranch.setEnabled(true);
		txtBranch.setEnabled(true);
		txtBranch.setText("");
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		
		lookupCompany.setValue(co_id);
}

	
}
