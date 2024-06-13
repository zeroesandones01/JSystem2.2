package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXPanel;

import Accounting.Collections.CheckStatusMonitoring;
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

public class CheckStatusListing extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Check Status Listing";
	static Dimension frameSize = new Dimension(500, 396);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlSouth;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblPhase;
	private JLabel lblStatus;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JPanel pnlCenter;
	private JPanel pnlCriteria;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupPhase;
	private _JLookup lookupStatus;
	
	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtPhase;
	private JTextField txtStatus;
	
	private JButton btnPreview;
	private JButton btnCancel;
	
	String company;
	String company_logo;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;
	
	String co_id = "02";

	private JLabel lblX;
	private JLabel lblSortby;

	private JComboBox cmbSortby;
	
	private JCheckBox chkDate;
	private JCheckBox chkTran;

	public CheckStatusListing() {
		super(title, true, true, false, true);
		initGUI();
	}

	public CheckStatusListing(String title) {
		super(title);
		initGUI();
	}

	public CheckStatusListing(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		//this.setPreferredSize(new java.awt.Dimension(500, 390));
		//this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.PAGE_START);
				pnlNorth.setPreferredSize(new Dimension(0, 190));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Check Details"));// XXX
				{
					pnlNorthWest = new JPanel(new GridLayout(5, 2, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new Dimension(150, 130));
					{
						lblCompany = new JLabel("Company");
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
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
									lblProject.setEnabled(true);	
									lookupProject.setText("");
									lookupProject.setEnabled(true);
									txtProject.setEnabled(true);
									txtProject.setText("");
									
									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
						lookupCompany.setValue("");
					}
					{
						lblProject = new JLabel("Project");
						pnlNorthWest.add(lblProject);
						lblProject.setEnabled(false);	
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(false);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									String name = (String) data[1];						
									txtProject.setText(name);
									
									String sql = 	
										"select distinct on (a.phase) a.phase, b.proj_alias, b.proj_name  \n" +
										"from ( select * from mf_unit_info ) a \n" +
										"left join (select * from mf_project where proj_id = '"+(String) data[0]+"' ) b on a.proj_id = b.proj_id \n" ;
																		
									lookupPhase.setLookupSQL(sql);
									
									lblPhase.setEnabled(true);	
									lookupPhase.setText("");
									lookupPhase.setEnabled(true);
									txtPhase.setEnabled(true);
									txtPhase.setText("");									
									
									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblPhase = new JLabel("Phase");
						pnlNorthWest.add(lblPhase);
						lblPhase.setEnabled(false);
					}
					{
						lookupPhase = new _JLookup(null, "Phase");
						pnlNorthWest.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setEnabled(false);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtPhase.setText((String) data[1]);
								}
							}
						});
					}
					{
						lblStatus = new JLabel("Status");
						pnlNorthWest.add(lblStatus);
					}
					{
						lookupStatus = new _JLookup(null, "Status");
						pnlNorthWest.add(lookupStatus);
						lookupStatus.setReturnColumn(0);
						lookupStatus.setLookupSQL("select checkstat_id, checkstat_desc from mf_check_status");
						lookupStatus.setPreferredSize(new java.awt.Dimension(62, 27));
						lookupStatus.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtStatus.setText((String) data[1]);
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblX = new JLabel("");
						pnlNorthWest.add(lblX);
					}
					{
						lblSortby = new JLabel("Sort by");
						pnlNorthWest.add(lblSortby);
					}
				}
				pnlNorthEast = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new Dimension(300, 100));
				
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
					txtProject.setEnabled(false);
				}
				{
					txtPhase = new JTextField();
					pnlNorthEast.add(txtPhase, BorderLayout.CENTER);
					txtPhase.setEditable(false);
					txtPhase.setEnabled(false);
					txtPhase.setVisible(false);
				}
				{
					txtStatus = new JTextField();
					pnlNorthEast.add(txtStatus, BorderLayout.CENTER);
					txtStatus.setEditable(false);
				}
				{
					String status[] = { "Client Name", "Check No.", "Check Date" };
					cmbSortby = new JComboBox(status);
					pnlNorthEast.add(cmbSortby);
					cmbSortby.setSelectedItem(null);
					cmbSortby.setFont(new java.awt.Font("Segoe UI",
							Font.PLAIN, 13));
					cmbSortby.setBounds(537, 15, 190, 21);
					cmbSortby.setEnabled(true);
					cmbSortby.setPreferredSize(new java.awt.Dimension(131,							80));
					cmbSortby.setSelectedIndex(0);
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					JXPanel panOtherFilter = new JXPanel(new GridLayout(2, 1, 5, 5));
					pnlCenter.add(panOtherFilter, BorderLayout.CENTER);
					{
						{
							JXPanel panRadio = new JXPanel(new GridLayout(1, 2, 5, 5));
							panOtherFilter.add(panRadio, BorderLayout.CENTER);
							panRadio.setBorder(components.JTBorderFactory.createTitleBorder("Filter by"));
							{
								{
									chkDate = new JCheckBox("Filter by Check Date");
									panRadio.add(chkDate, BorderLayout.CENTER);
									chkDate.setHorizontalAlignment(JTextField.CENTER);
									chkDate.setSelected(false);
									chkDate.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent arg0) {
											if (chkDate.isSelected()) {
												chkTran.setSelected(false);
												pnlCriteria.setBorder(components.JTBorderFactory.createTitleBorder("Check Date"));
											}
										}
									});
								}
								{
									chkTran = new JCheckBox("Filter by Transaction Date");
									panRadio.add(chkTran, BorderLayout.CENTER);
									chkTran.setHorizontalAlignment(JTextField.CENTER);
									chkTran.setSelected(true);
									chkTran.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if (chkTran.isSelected()) {
												chkDate.setSelected(false);
												pnlCriteria.setBorder(components.JTBorderFactory.createTitleBorder("Transaction Date"));
											}
										}
									});
								}
							}
						}
						{
							pnlCriteria = new JPanel(new GridLayout(1, 4, 3, 3));
							panOtherFilter.add(pnlCriteria, BorderLayout.CENTER);
							pnlCriteria.setBorder(components.JTBorderFactory.createTitleBorder("Transaction Date"));
							{
								lblDateFrom = new JLabel("Date From", JLabel.TRAILING);
								pnlCriteria.add(lblDateFrom, BorderLayout.CENTER);
								lblDateFrom.setHorizontalAlignment(JTextField.LEADING);						
							}
							{												
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCriteria.add(dteDateFrom, BorderLayout.CENTER);						
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(true);			
								dteDateFrom.setPreferredSize(new java.awt.Dimension(248, 38));
								dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}		
							{	
								lblDateTo = new JLabel("Date To", JLabel.TRAILING);
								pnlCriteria.add(lblDateTo);
								lblDateTo.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCriteria.add(dateDateTo, BorderLayout.EAST);
								dateDateTo.setBounds(485, 7, 125, 21);
								dateDateTo.setDate(null);
								dateDateTo.setEnabled(true);
								dateDateTo.setPreferredSize(new java.awt.Dimension(291, 27));
								dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}	
						}
					}
				}
			}

			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.PAGE_END);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
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
		//this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, lookupStatus, dteDateFrom, dateDateTo, btnPreview);
		//this.setBounds(0, 0, 513, 335);
		//initialize_comp();
	}
	
	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview")){		
			String criteria = "Checks Status Listing";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

			Boolean blnTrans = true;
			if (chkDate.isSelected()) {
				blnTrans = false;
			}
			
			String strDate = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			if (dteDateFrom.getDate().equals(dateDateTo.getDate())) {
				strDate = dateFormat.format(dteDateFrom.getDate());
			} else {
				strDate = dateFormat.format(dteDateFrom.getDate()) + " to " + dateFormat.format(dateDateTo.getDate());
			}
			
			String strCoID = ((lookupCompany.getValue().equals(""))?"":lookupCompany.getValue()); 
			
			System.out.println("company: "+company);
			System.out.println("proj_id: "+lookupProject.getValue());
			System.out.println("prepared_by: "+UserInfo.Alias);
			System.out.println("phase: "+lookupPhase.getValue());	
			System.out.println("status: "+lookupStatus.getText());	
			System.out.println("date_from: "+dteDateFrom.getDate());	
			System.out.println("date_to: "+dateDateTo.getDate());	
			System.out.println("sort: "+cmbSortby.getSelectedItem());
			System.out.println("filter_by_trans: "+ blnTrans);
			System.out.println("as_of_date: "+strDate);
			System.out.println("co_id: "+strCoID);
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("phase", lookupPhase.getValue());	
			mapParameters.put("status", lookupStatus.getText());	
			mapParameters.put("date_from", dteDateFrom.getDate());	
			mapParameters.put("date_to", dateDateTo.getDate());	
			mapParameters.put("sort", cmbSortby.getSelectedItem());
			mapParameters.put("filter_by_trans", blnTrans);
			mapParameters.put("as_of_date", strDate);
			mapParameters.put("co_id", strCoID);
			FncReport.generateReport("/Reports/rptChecksStatusListing.jasper", reportTitle, txtProject.getText(), mapParameters);
		}
		if(e.getActionCommand().equals("Cancel")){	
			//lookupCompany.setText("");
			//txtCompany.setText("");
			//lblProject.setEnabled(false);	
			//lookupProject.setEnabled(false);	
			//txtProject.setEnabled(false);		
			lookupProject.setText("");			
			txtProject.setText("");
			lblPhase.setEnabled(false);	
			lookupPhase.setEnabled(false);		lookupPhase.setText("");
			txtPhase.setEnabled(false);			txtPhase.setText("");
			lookupStatus.setText("");			txtStatus.setText("");
			dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			cmbSortby.setSelectedIndex(0);
		}
		
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";	
		company_logo = RequestForPayment.sql_getCompanyLogo();			
		txtCompany.setText(company);
		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lblProject.setEnabled(true);	
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);
		txtProject.setText("");
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		
		lookupCompany.setValue(co_id);
		cmbSortby.setSelectedIndex(0);
}
	
}
