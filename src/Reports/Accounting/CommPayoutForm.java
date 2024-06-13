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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXTextField;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
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

public class CommPayoutForm extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Commission Payout Form";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;
	private JPanel pnlCenter;
	private JPanel pnlCPF;
	private JPanel pnlCenter_b;
	private JPanel pnlSearchBy;
	private JPanel pnlSouth;

	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblCPFno_fr;
	private JLabel lblCPFTo;
	private JLabel lblSearchBy;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JXTextField txtCPFno_fr;
	private JXTextField txtCPFno_to;

	private JRadioButton rbtCDFnumber;
	private JRadioButton rbtnCDFdate;	

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	String company;
	String company_logo;	
	String co_id = "";
	String proj_id = "All";	

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JLabel lblStatus;

	private JCheckBox chkStatus;

	public CommPayoutForm() {
		super(title, false, true, false, true);
		initGUI();
	}

	public CommPayoutForm(String title) {
		super(title);
		initGUI();
	}

	public CommPayoutForm(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(560, 415));
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
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 94));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Comp. / Project Option" ));// XXX

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

									co_id = (String) data[0];	
									company		= (String) data[1];	
									company_logo = (String) data[3];

									String name = (String) data[1];						
									txtCompany.setText(name);
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));

									enable_fields(true);
									lblDateFrom.setEnabled(false);		
									dteDateFrom.setEnabled(false);
									lblDateTo.setEnabled(false);	
									dateDateTo.setEnabled(false);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
									btnPreview.setEnabled(true);
								}
							}
						});
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
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

									proj_id = (String) data[0];		
									String name = (String) data[1];						
									txtProject.setText(name);
									btnPreview.setEnabled(true);

									KEYBOARD_MANAGER.focusNextComponent();
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
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
					txtProject.setEnabled(false);
				}
			}

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.CENTER);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));
			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("CPF Details Option"));

			{
				pnlSearchBy = new JPanel(new GridLayout(2, 4, 5, 10));
				pnlCenter2.add(pnlSearchBy, BorderLayout.NORTH);	
				pnlSearchBy.setPreferredSize(new java.awt.Dimension(547, 74));	
				pnlSearchBy.setBorder(lineBorder);


				{
					lblSearchBy = new JLabel("               Search by :");
					pnlSearchBy.add(lblSearchBy);
					lblSearchBy.setEnabled(false);	
					lblSearchBy.setPreferredSize(new java.awt.Dimension(86, 40));
					lblSearchBy.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
					lblSearchBy.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}		
				{
					rbtCDFnumber = new JRadioButton();
					pnlSearchBy.add(rbtCDFnumber);
					rbtCDFnumber.setText("by CDF No.");
					rbtCDFnumber.setBounds(277, 12, 77, 18);
					rbtCDFnumber.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					rbtCDFnumber.setSelected(true);
					rbtCDFnumber.setEnabled(false);
					rbtCDFnumber.setPreferredSize(new java.awt.Dimension(220, 18));
					rbtCDFnumber.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){									
							rbtCDFnumber.setSelected(true);	rbtnCDFdate.setSelected(false);	

							lblCPFno_fr.setEnabled(true);	
							txtCPFno_fr.setEnabled(true);
							lblCPFTo.setEnabled(true);	
							txtCPFno_to.setEnabled(true);									

							lblDateFrom.setEnabled(false);	
							dteDateFrom.setEnabled(false);
							lblDateTo.setEnabled(false);	
							dateDateTo.setEnabled(false);
							dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							//btnPreviewCPF.setEnabled(true);
						}});					
				}
				{
					rbtnCDFdate = new JRadioButton();
					pnlSearchBy.add(rbtnCDFdate);
					rbtnCDFdate.setText("by CDF Date");
					rbtnCDFdate.setBounds(277, 12, 77, 18);
					rbtnCDFdate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					rbtnCDFdate.setSelected(false);
					rbtnCDFdate.setEnabled(false);
					rbtnCDFdate.setPreferredSize(new java.awt.Dimension(232, 24));
					rbtnCDFdate.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){									
							rbtCDFnumber.setSelected(false);	rbtnCDFdate.setSelected(true);

							lblCPFno_fr.setEnabled(false);	
							txtCPFno_fr.setEnabled(false);	
							lblCPFTo.setEnabled(false);	
							txtCPFno_to.setEnabled(false);									
							txtCPFno_fr.setText("");	
							txtCPFno_to.setText("");	

							lblDateFrom.setEnabled(true);	
							dteDateFrom.setEnabled(true);
							lblDateTo.setEnabled(true);	
							dateDateTo.setEnabled(true);
							//btnPreviewCPF.setEnabled(true);
						}});
				}
				{
					lblStatus = new JLabel("               CDF Status :");
					pnlSearchBy.add(lblStatus);
					lblStatus.setEnabled(false);	
					lblStatus.setPreferredSize(new java.awt.Dimension(86, 40));
					lblStatus.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
					lblStatus.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}		
				{
					chkStatus = new JCheckBox("Canceled CDF? ");
					pnlSearchBy.add(chkStatus);
					chkStatus.setEnabled(false);	
					chkStatus.setHorizontalAlignment(JTextField.LEFT);	
					chkStatus.setPreferredSize(new java.awt.Dimension(383, 26));
				}
			}
			{
				pnlCPF = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPF, BorderLayout.CENTER);
				pnlCPF.setPreferredSize(new java.awt.Dimension(547, 169));				

				{
					pnlCenter = new JPanel(new GridLayout(1,1,0,0));
					pnlCPF.add(pnlCenter, BorderLayout.NORTH);
					pnlCenter.setPreferredSize(new java.awt.Dimension(480, 65));
					pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("by CPF Number"));					

					{
						lblCPFno_fr = new JLabel("From :  ", JLabel.TRAILING);
						pnlCenter.add(lblCPFno_fr, BorderLayout.CENTER);
						lblCPFno_fr.setEnabled(false);							
					}
					{
						txtCPFno_fr = new JXTextField("");
						pnlCenter.add(txtCPFno_fr);
						txtCPFno_fr.setEnabled(true);	
						txtCPFno_fr.setEditable(true);
						txtCPFno_fr.setBounds(120, 25, 300, 22);	
						txtCPFno_fr.setHorizontalAlignment(JTextField.CENTER);
						txtCPFno_fr.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						txtCPFno_fr.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {	
								//btnPreviewCPF.setEnabled(true);
							}				 
						});	
					}	
					{
						lblCPFTo = new JLabel("To :  ", JLabel.TRAILING);
						pnlCenter.add(lblCPFTo);
						lblCPFTo.setEnabled(false);	
					}
					{
						txtCPFno_to = new JXTextField("");
						pnlCenter.add(txtCPFno_to);
						txtCPFno_to.setEnabled(true);	
						txtCPFno_to.setEditable(true);
						txtCPFno_to.setBounds(120, 25, 300, 22);	
						txtCPFno_to.setHorizontalAlignment(JTextField.CENTER);
						txtCPFno_to.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						txtCPFno_to.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {	
								//btnPreviewCPF.setEnabled(true);
							}				 
						});	
					}	
				}
				{
					pnlCenter_b = new JPanel(new GridLayout(1,1,0,0));
					pnlCPF.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));
					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder("by CPF Date"));

					{
						pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlCenter_b.add(pnlCriteria2, BorderLayout.WEST);					
						pnlCriteria2.setPreferredSize(new java.awt.Dimension(480, 63));

						{
							lblDateFrom = new JLabel("From  :", JLabel.TRAILING);
							pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);
							lblDateFrom.setEnabled(false);							
						}
						{
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCriteria2.add(dteDateFrom, BorderLayout.CENTER);						
							dteDateFrom.setDate(null);
							dteDateFrom.setEnabled(false);
							dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						}		
						{
							lblDateTo = new JLabel("To  :", JLabel.TRAILING);
							pnlCriteria2.add(lblDateTo);
							lblDateTo.setEnabled(false);	
						}
						{
							dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCriteria2.add(dateDateTo, BorderLayout.EAST);
							dateDateTo.setBounds(485, 7, 125, 21);
							dateDateTo.setDate(null);
							dateDateTo.setEnabled(false);
							dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						}		
					}
				}	
			}

			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));

				{
					btnPreview = new JButton("PreviewCPF");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setEnabled(false);
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
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							enable_fields(false);		
							btnPreview.setEnabled(false);
						}
					});
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 560, 415);  //174

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
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
		
		String x = "";
		if (rbtCDFnumber.isSelected()==true) {x="CDF Nos.";}
		else {x="CDF Period";}

		if(cpf_range_complete()==false && rbtCDFnumber.isSelected()==true)
		{JOptionPane.showMessageDialog(getContentPane(), "Enter complete " + x, "Incomplete Details", 
				JOptionPane.ERROR_MESSAGE);}
		else 
		{
			if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true)
			{
				if(e.getActionCommand().equals("PreviewCPF")&&rbtnCDFdate.isSelected()==true){previewCPF(); previewListing(); previewCA_JV();}  //  

				if(e.getActionCommand().equals("PreviewCPF")&&rbtnCDFdate.isSelected()==false){previewCPF(); }  //
			}
			else if(FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false) 
			{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview CPF.","Warning",JOptionPane.WARNING_MESSAGE); }
		}
	}

	private void previewCPF(){

		String status = "";
		if(chkStatus.isSelected()==true)
		{
			status = "I";
		}
		else 
		{
			status = "";
		}


		String criteria = "CPF";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);				
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));	

		if (rbtCDFnumber.isSelected()){	
			mapParameters.put("sort_by", "cdf_no");
			mapParameters.put("date_from", null);
			mapParameters.put("date_to", null);
			mapParameters.put("cdf_from", Integer.parseInt(txtCPFno_fr.getText()));
			mapParameters.put("cdf_to", Integer.parseInt(txtCPFno_to.getText()));
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("co_id", co_id);
			mapParameters.put("status", status);
			
			System.out.printf("Display Sort By: %s%n", "cdf_no");
			System.out.printf("Display Date From: %s%n", null);
			
		}

		else if (rbtnCDFdate.isSelected()){ 
			mapParameters.put("sort_by", "cdf_date");
			mapParameters.put("date_from", dteDateFrom.getDate());
			mapParameters.put("date_to", dateDateTo.getDate());
			mapParameters.put("cdf_from", "");
			mapParameters.put("cdf_to", "");
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("co_id", co_id);
			mapParameters.put("status", status);
		}

		FncReport.generateReport("/Reports/rptCPF_preview.jasper", reportTitle, company, mapParameters);		
	}

	private void previewListing(){

		String status = "";
		if(chkStatus.isSelected()==true)
		{
			status = "I";
		}
		else 
		{
			status = "";
		}

		String criteria = "CPF Listing";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", co_id);
		mapParameters.put("company", company);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("proj_id", lookupProject.getText());		
		mapParameters.put("proj_name", txtProject.getText());
		mapParameters.put("phase_no", "");
		mapParameters.put("phase_name", "All");
		mapParameters.put("status_no", 0);
		mapParameters.put("status_name", "All");

		FncReport.generateReport("/Reports/rptCDF_listing.jasper", reportTitle, company, mapParameters);		
	}

	private void enable_fields(Boolean x){

		lblProject.setEnabled(x);	
		lookupProject.setText("");
		lookupProject.setEnabled(x);
		txtProject.setEnabled(x);
		txtProject.setText("");
		lblSearchBy.setEnabled(x);	
		lblStatus.setEnabled(x);	
		rbtCDFnumber.setEnabled(x);
		rbtCDFnumber.setSelected(true);
		rbtnCDFdate.setEnabled(x);
		rbtnCDFdate.setSelected(false);
		lblStatus.setEnabled(x);	
		lblStatus.setEnabled(x);	
		lblCPFno_fr.setEnabled(x);					
		lblCPFTo.setEnabled(x);	
		txtCPFno_fr.setEnabled(x);	
		txtCPFno_fr.setText("");
		txtCPFno_to.setEnabled(x);	
		txtCPFno_to.setText("");
		lblDateFrom.setEnabled(x);		
		dteDateFrom.setEnabled(x);
		lblDateTo.setEnabled(x);	
		dateDateTo.setEnabled(x);
		chkStatus.setEnabled(x);	
		chkStatus.setSelected(false);

	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();			
		txtCompany.setText(company);

		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

		enable_fields(true);
		lblDateFrom.setEnabled(false);		
		dteDateFrom.setEnabled(false);
		lblDateTo.setEnabled(false);	
		dateDateTo.setEnabled(false);

		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);

		lookupCompany.setValue(co_id);
	}

	public void previewCA_JV(){
		
		String criteria = "JV Batch Preview";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("remark_unliq_ca", "");
		mapParameters.put("pv_no_from", 0);
		mapParameters.put("pv_no_to", 0);
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("date_from", dteDateFrom.getDate());
		mapParameters.put("date_to",  dateDateTo.getDate());
		mapParameters.put("search_by",  "date");
		mapParameters.put("from_cpf",  "yes");		
		mapParameters.put("status_id",  "");
		FncReport.generateReport("/Reports/rptJV_preview_batch.jasper", reportTitle, company, mapParameters);	
		
	}
	

	//checking
	public Boolean cpf_range_complete(){

		boolean x = false;

		if (txtCPFno_fr.getText().equals("")||txtCPFno_to.getText().equals("")) { x = false;}
		else { x = true; }		

		return x;		
	}

}
