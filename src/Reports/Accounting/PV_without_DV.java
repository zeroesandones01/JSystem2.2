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
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.jdesktop.swingx.JXTextField;

import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;
import components._JTagLabel;

public class PV_without_DV extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "PV Without DV";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlNorth_comp;
	private JPanel pnlDRFDtl_2a;
	private JPanel pnlDRFDtl_2b;
	private JPanel pnlDRF;
	private JPanel pnlCreatedBy;
	private JPanel pnlCenterDRF;
	private JPanel pnlCenterCreated;
	private JPanel pnlCenter_a;
	private JPanel pnlCenter_2;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private static JLabel lblDateFrom;
	private static JLabel lblDateTo;
	private static JLabel lblDateRequest;
	private static JLabel lblPVFrom;
	private static JLabel lblPVTo;
	private static JLabel lblPayee;
	private static JLabel lblAvailer;
	private static JLabel lblPVStatus;
	private static JLabel lblStatus;
	private static JLabel lblPaymentType;	
	
	private static _JTagLabel tagReqType;
	private static _JTagLabel tagPayee1;
	private static _JTagLabel tagPayee2;
	private static _JTagLabel tagStatus;
	private static _JTagLabel tagPreparedBy;
	private static _JTagLabel tagPmtType;

	private _JLookup lookupProject;
	public static _JLookup lookupCompany;
	private static _JLookup lookupRequestType;
	private static _JLookup lookupPayee1;
	private static _JLookup lookupPayee2;
	private static _JLookup lookupStatus;
	private static _JLookup lookupPreparedby;
	private static _JLookup lookupPmtType;
	
	public static JTextField txtCompany;
	private static JXTextField txtPVfrom;		
	private static JXTextField txtPVto;	
	
	private static JButton btnPreview;
	private static JButton btnCancel;

	String company;
	public static String company_logo;		

	private static _JDateChooser dteDateFrom;
	private static _JDateChooser dateDateTo;	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	
	public String req_type_id 		= "";
	public String req_type_name		= "";
	public String payee_id 			= "";
	public String payee_name		= "";
	public String availer_id 		= "";
	public String availer_name 		= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String status_id			= "";
	public String status_name		= "";
	public String pmt_type_id		= "";
	public String pmt_type_name		= "";
	String co_id = "";
	
	public PV_without_DV() {
		super(title, false, true, false, true);
		initGUI();
	}

	public PV_without_DV(String title) {
		super(title);
		initGUI();
	}

	public PV_without_DV(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(624, 473));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(621, 462));
			
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 193));

				{
					pnlNorth_comp = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorth_comp, BorderLayout.NORTH);
					pnlNorth_comp.setPreferredSize(new java.awt.Dimension(547, 63));
					pnlNorth_comp.setBorder(components.JTBorderFactory.createTitleBorder("Company"));// XXX

					{
						pnlNorthWest = new JPanel(new GridLayout(1,1, 5, 5));
						pnlNorth_comp.add(pnlNorthWest, BorderLayout.WEST);
						pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));
						{
							lblCompany = new JLabel("Company", JLabel.TRAILING);
							pnlNorthWest.add(lblCompany);
						}
						{
							lookupCompany = new _JLookup(null, "Company");
							pnlNorthWest.add(lookupCompany);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(SummaryofDeposits.company());
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){

										company = (String) data[1];
										company_logo = (String) data[3];

										String name = (String) data[1];						
										txtCompany.setText(name);

										enabledFields(true);
										lookupRequestType.setLookupSQL(getRequestType());	
										lookupPayee1.setLookupSQL(getPayee());	
										lookupPayee2.setLookupSQL(getAvailer());	
										lookupPreparedby.setLookupSQL(getPreparedBy());	
										lookupPmtType.setLookupSQL(getPayment_type());	
										lookupStatus.setLookupSQL(getStatus());	
									}
								}
							});
						}
					}
					pnlNorthEast = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlNorth_comp.add(pnlNorthEast, BorderLayout.CENTER);
					pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
					{
						txtCompany = new JTextField();
						pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
						txtCompany.setEditable(false);
					}
				}
				{
					pnlCenterDRF = new JPanel(new GridLayout(1,1,5, 5));
					pnlNorth.add(pnlCenterDRF, BorderLayout.CENTER);
					pnlCenterDRF.setPreferredSize(new java.awt.Dimension(499, 60));
					pnlCenterDRF.setBorder(components.JTBorderFactory.createTitleBorder("DRF No."));
					
					{
						pnlDRF = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlCenterDRF.add(pnlDRF, BorderLayout.WEST);					

						{
							lblPVFrom = new JLabel("PV From   ", JLabel.TRAILING);
							pnlDRF.add(lblPVFrom, BorderLayout.CENTER);
							lblPVFrom.setEnabled(false);							
						}
						{
							txtPVfrom = new JXTextField("");
							pnlDRF.add(txtPVfrom);
							txtPVfrom.setEnabled(false);	
							txtPVfrom.setEditable(true);
							txtPVfrom.setBounds(120, 25, 300, 22);	
							txtPVfrom.setHorizontalAlignment(JTextField.CENTER);
							txtPVfrom.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						}	
						{	

							lblPVTo = new JLabel("PV To   ", JLabel.TRAILING);
							pnlDRF.add(lblPVTo);
							lblPVTo.setEnabled(false);	
						}					
						{
							txtPVto = new JXTextField("");
							pnlDRF.add(txtPVto);
							txtPVto.setEnabled(false);	
							txtPVto.setEditable(true);
							txtPVto.setBounds(120, 25, 300, 22);	
							txtPVto.setHorizontalAlignment(JTextField.CENTER);
							txtPVto.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						}
					}
				}
				{
					pnlCenterCreated = new JPanel(new GridLayout(1,1,5, 5));
					pnlNorth.add(pnlCenterCreated, BorderLayout.SOUTH);
					pnlCenterCreated.setPreferredSize(new java.awt.Dimension(547, 59));
					pnlCenterCreated.setBorder(components.JTBorderFactory.createTitleBorder("Created Date"));
					
					{
						pnlCreatedBy = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlCenterCreated.add(pnlCreatedBy, BorderLayout.WEST);					

						{
							lblDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
							pnlCreatedBy.add(lblDateFrom, BorderLayout.CENTER);
							lblDateFrom.setEnabled(false);							
						}
						{
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCreatedBy.add(dteDateFrom, BorderLayout.CENTER);						
							dteDateFrom.setDate(null);
							dteDateFrom.setEnabled(false);
							dteDateFrom.setDate(null);
						}	
						{	

							lblDateTo = new JLabel("Date To   ", JLabel.TRAILING);
							pnlCreatedBy.add(lblDateTo);
							lblDateTo.setEnabled(false);	
						}					
						{
							dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCreatedBy.add(dateDateTo, BorderLayout.EAST);
							dateDateTo.setBounds(485, 7, 125, 21);
							dateDateTo.setDate(null);
							dateDateTo.setEnabled(false);
							dateDateTo.setDate(null);
						}
					}
				}

			}	
			{
				pnlCenter =  new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new java.awt.Dimension(499, 50));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));
				
				{		
					pnlCenter_a = new JPanel(new GridLayout(6, 1, 0, 5));
					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
					pnlCenter_a.setPreferredSize(new java.awt.Dimension(95, 185));
					
					{
						lblDateRequest = new JLabel("Request Type", JLabel.TRAILING);
						pnlCenter_a.add(lblDateRequest);
						lblDateRequest.setEnabled(false);	
					}	
					{
						lblPayee = new JLabel("Payee", JLabel.TRAILING);
						pnlCenter_a.add(lblPayee);
						lblPayee.setEnabled(false);	
					}	
					{
						lblAvailer= new JLabel("Availer", JLabel.TRAILING);
						pnlCenter_a.add(lblAvailer);
						lblAvailer.setEnabled(false);	
					}	
					{
						lblStatus = new JLabel("Prepared by", JLabel.TRAILING);
						pnlCenter_a.add(lblStatus);
						lblStatus.setEnabled(false);	
					}
					{
						lblPVStatus = new JLabel("PV Status", JLabel.TRAILING);
						pnlCenter_a.add(lblPVStatus);
						lblPVStatus.setEnabled(false);	
					}
					{
						lblPaymentType = new JLabel("Pmt. Type", JLabel.TRAILING);
						pnlCenter_a.add(lblPaymentType);
						lblPaymentType.setEnabled(false);	
					}					

					pnlCenter_2 = new JPanel(new BorderLayout(5,0));
					pnlCenter.add(pnlCenter_2, BorderLayout.CENTER);
					pnlCenter_2.setPreferredSize(new java.awt.Dimension(203, 118));
					pnlCenter_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

					pnlDRFDtl_2a = new JPanel(new GridLayout(6, 1, 0, 5));
					pnlCenter_2.add(pnlDRFDtl_2a, BorderLayout.WEST);
					pnlDRFDtl_2a.setPreferredSize(new java.awt.Dimension(100, 185));
					pnlDRFDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						lookupRequestType = new _JLookup(null, "Request Type", 2, 2);
						pnlDRFDtl_2a.add(lookupRequestType);
						lookupRequestType.setBounds(20, 27, 20, 25);
						lookupRequestType.setReturnColumn(0);
						lookupRequestType.setFilterName(true);
						lookupRequestType.setEnabled(false);
						//lookupRequestType.setEditable(false);
						lookupRequestType.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupRequestType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		
									req_type_id 	= (String) data[0];		
									req_type_name 	= (String) data[1];			
									tagReqType.setTag(req_type_name);
								}
							}
						});	
					}		
					{
						lookupPayee1 = new _JLookup(null, "Payee", 2, 2);
						pnlDRFDtl_2a.add(lookupPayee1);
						lookupPayee1.setBounds(20, 27, 20, 25);
						lookupPayee1.setReturnColumn(0);
						lookupPayee1.setFilterName(true);
						lookupPayee1.setEnabled(false);	
						//lookupPayee1.setEditable(false);
						lookupPayee1.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupPayee1.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									payee_id 	= (String) data[0];		
									payee_name 	= (String) data[1];			
									tagPayee1.setTag(payee_name);
								}
							}
						});	
					}		
					{
						lookupPayee2 = new _JLookup(null, "Availer", 2, 2);
						pnlDRFDtl_2a.add(lookupPayee2);
						lookupPayee2.setBounds(20, 27, 20, 25);
						lookupPayee2.setReturnColumn(0);
						lookupPayee2.setFilterName(true);
						lookupPayee2.setEnabled(false);	
						//lookupPayee2.setEditable(false);
						lookupPayee2.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupPayee2.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){						
									availer_id 		= (String) data[0];		
									availer_name 	= (String) data[1];			
									tagPayee2.setTag(availer_name);
								}
							}
						});	
					}		
					
					{
						lookupPreparedby = new _JLookup(null, "DRF Status", 2, 2);
						pnlDRFDtl_2a.add(lookupPreparedby);
						lookupPreparedby.setBounds(20, 27, 20, 25);
						lookupPreparedby.setReturnColumn(0);
						lookupPreparedby.setFilterName(true);
						lookupPreparedby.setEnabled(false);	
						//lookupPreparedby.setEditable(false);
						lookupPreparedby.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupPreparedby.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		
									prepared_by_id 		= (String) data[0];		
									prepared_by_name 	= (String) data[1];			
									tagPreparedBy.setTag(prepared_by_name);	
								}
							}
						});	
					}	

					{
						lookupStatus = new _JLookup(null, "Prepared by", 2, 2);
						pnlDRFDtl_2a.add(lookupStatus);
						lookupStatus.setBounds(20, 27, 20, 25);
						lookupStatus.setReturnColumn(0);
						lookupStatus.setFilterName(true);
						lookupStatus.setEnabled(false);	
						//lookupStatus.setEditable(false);
						lookupStatus.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupStatus.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		
									status_id 		= (String) data[0];		
									status_name 	= (String) data[1];			
									tagStatus.setTag(status_name);	
								}
							}
						});	
					}	
					{
						lookupPmtType = new _JLookup(null, "Office Branch", 2, 2);
						pnlDRFDtl_2a.add(lookupPmtType);
						lookupPmtType.setBounds(20, 27, 20, 25);
						lookupPmtType.setReturnColumn(0);
						lookupPmtType.setFilterName(true);
						lookupPmtType.setEnabled(false);	
						//lookupPmtType.setEditable(false);
						lookupPmtType.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupPmtType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		
									pmt_type_id 	= (String) data[0];		
									pmt_type_name 	= (String) data[1];			
									tagPmtType.setTag(pmt_type_name);	
								}
							}
						});	
					}		

					pnlDRFDtl_2b = new JPanel(new GridLayout(6, 1, 0, 5));
					pnlCenter_2.add(pnlDRFDtl_2b, BorderLayout.CENTER);
					pnlDRFDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
					pnlDRFDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						tagReqType = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagReqType);
						tagReqType.setBounds(209, 27, 700, 22);
						tagReqType.setEnabled(false);	
						tagReqType.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagPayee1 = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagPayee1);
						tagPayee1.setBounds(209, 27, 700, 22);
						tagPayee1.setEnabled(false);	
						tagPayee1.setPreferredSize(new java.awt.Dimension(27, 33));
					}		
					{
						tagPayee2 = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagPayee2);
						tagPayee2.setBounds(209, 27, 700, 22);
						tagPayee2.setEnabled(false);	
						tagPayee2.setPreferredSize(new java.awt.Dimension(27, 33));
					}						
					{
						tagPreparedBy = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagPreparedBy);
						tagPreparedBy.setBounds(209, 27, 700, 22);
						tagPreparedBy.setEnabled(false);	
						tagPreparedBy.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagStatus = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagStatus);
						tagStatus.setBounds(209, 27, 700, 22);
						tagStatus.setEnabled(false);	
						tagStatus.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagPmtType = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagPmtType);
						tagPmtType.setBounds(209, 27, 700, 22);
						tagPmtType.setEnabled(false);	
						tagPmtType.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
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
					btnPreview.setEnabled(false);
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
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 624, 473);  //174
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}
		
	@Override
	public void ancestorAdded(AncestorEvent event) {
		
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {

		req_type_id 	= lookupRequestType.getText().trim();
		payee_id 		= lookupPayee1.getText().trim();
		availer_id 		= lookupPayee2.getText().trim();
		prepared_by_id 	= lookupPreparedby.getText().trim();
		status_id 		= lookupStatus.getText().trim();
		
		Integer pv_fr = 0;
		Integer pv_to = 0;
		
		Date date_fr = null;
		Date date_to = null;
		
		try {date_fr = dateFormat.parse("2000-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
		try {date_to = dateFormat.parse("2100-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
		
		if (txtPVfrom.getText().equals("")) { } else { pv_fr = Integer.parseInt(txtPVfrom.getText()); }
		if (txtPVto.getText().equals("")) { } else { pv_to = Integer.parseInt(txtPVto.getText()); }
		if (dteDateFrom.getDate()==null) { } else { date_fr = dteDateFrom.getDate(); }
		if (dateDateTo.getDate()==null) { } else { date_to = dateDateTo.getDate(); }
		
		System.out.printf("Date From.: " + date_fr + "\n");
		System.out.printf("Date To: " + date_to + "\n");
		System.out.printf("PV From.: " + pv_fr + "\n");
		System.out.printf("PV To: " + pv_to + "\n");
		System.out.printf("DRF Fr.: " + txtPVfrom.getText() + "\n");
		System.out.printf("DRF To.: " + txtPVto.getText() + "\n");
			
		if(req_type_id.equals("")) { req_type_name = "All"; } else { }
		if(payee_id.equals("")) { payee_name = "All"; } else { }
		if(availer_id.equals("")) { availer_name = "All"; } else { }
		if(prepared_by_id.equals("")) { prepared_by_name = "All"; } else { }
		if(status_id.equals("")) { status_name = "All"; } else { }
		
		if(e.getActionCommand().equals("Preview")){		
			String criteria = "PV Prooflist";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("date_from", date_fr);	
			mapParameters.put("date_to", date_to);		
			mapParameters.put("req_type_id", req_type_id);	
			mapParameters.put("req_type_name", req_type_name);	
			mapParameters.put("payee_id", payee_id);	
			mapParameters.put("payee_name", payee_name);	
			mapParameters.put("availer_id", availer_id);	
			mapParameters.put("availer_name", availer_name);
			mapParameters.put("prepared_id", prepared_by_id);	
			mapParameters.put("prepared_name", prepared_by_name);	
			mapParameters.put("status_id", status_id);	
			mapParameters.put("status_name", status_name);
			mapParameters.put("pmt_type_name", pmt_type_name);	
			mapParameters.put("pmt_type_id", pmt_type_id);	
			mapParameters.put("pv_from", pv_fr);	
			mapParameters.put("pv_to", pv_to);

			FncReport.generateReport("/Reports/rptPV_withoutDV.jasper", reportTitle, "", mapParameters);
		}
		if(e.getActionCommand().equals("Cancel")){	
			refreshFields();
		}

	}

	public static void enabledFields(boolean x){
		
		lblPVFrom.setEnabled(x);		
		txtPVfrom.setEnabled(x);	
		lblPVTo.setEnabled(x);	
		txtPVto.setEnabled(x);	
		lblDateFrom.setEnabled(x);			
		dteDateFrom.setEnabled(x);
		lblDateTo.setEnabled(x);	
		dateDateTo.setEnabled(x);
		
		lblDateRequest.setEnabled(x);	
		lblPayee.setEnabled(x);	
		lblAvailer.setEnabled(x);	
		lblStatus.setEnabled(x);	
		lblPVStatus.setEnabled(x);	
		lblPaymentType.setEnabled(x);
		
		lookupRequestType.setEnabled(x);	
		lookupPayee1.setEnabled(x);	
		lookupPayee2.setEnabled(x);	
		lookupStatus.setEnabled(x);	
		lookupPreparedby.setEnabled(x);	
		lookupPmtType.setEnabled(x);	
		
		tagReqType.setEnabled(x);	
		tagPayee1.setEnabled(x);	
		tagPayee2.setEnabled(x);
		tagStatus.setEnabled(x);	
		tagPreparedBy.setEnabled(x);
		tagPmtType.setEnabled(x);		
		
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);
		
		txtPVfrom.setEnabled(x);
		txtPVto.setEnabled(x);
		
		
	}
	
	public void refreshFields(){
		
		lookupRequestType.setValue("");	
		lookupPayee1.setValue("");	
		lookupPayee2.setValue("");		
		lookupStatus.setValue("");	
		lookupPreparedby.setValue("");	
		lookupPmtType.setValue("");
		
		tagReqType.setTag("");
		tagPayee1.setTag("");
		tagPayee2.setTag("");	
		tagStatus.setTag("");	
		tagPreparedBy.setTag("");
		tagPmtType.setTag("");		
		
		dteDateFrom.setDate(null);
		dateDateTo.setDate(null);
		
		req_type_id 	= "";
		req_type_name	= "";
		payee_id 		= "";
		payee_name		= "";
		availer_id 		= "";
		availer_name 	= "";
		prepared_by_id	= "";
		prepared_by_name= "";
		status_id		= "";
		status_name		= "";
		pmt_type_id		= "";
		pmt_type_name	= "";
		
		txtPVfrom.setText("");
		txtPVto.setText("");
		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		txtCompany.setText(company);

		enabledFields(true);
		lookupRequestType.setLookupSQL(getRequestType());	
		lookupPayee1.setLookupSQL(getPayee());	
		lookupPayee2.setLookupSQL(getAvailer());
		lookupPreparedby.setLookupSQL(getPreparedBy());	
		lookupPmtType.setLookupSQL(getPayment_type());	
		lookupStatus.setLookupSQL(getStatus());	
		
		lookupCompany.setValue(co_id);
	}
	
	
	//lookup
	public String getRequestType(){

		String sql = 
		"select " +
		"rplf_type_id as \"Type ID\",  \n" +
		"trim(rplf_type_desc) as \"Description\"  \n" +
		"from mf_rplf_type where status_id = 'A' " +
		"order by rplf_type_id    ";		
		return sql;

	}	
	
	public String getPayee(){

		String sql = 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.entity_id1,\r\n" + 
		"upper(trim(b.entity_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_request_header a \r\n" + 
		"left join rf_entity b on a.entity_id1 = b.entity_id ) a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
	
	public String getAvailer(){

		String sql = 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.entity_id,\r\n" + 
		"upper(trim(b.entity_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_request_detail a \r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id ) a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
		
	public String getPreparedBy(){

		String sql = 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.created_by,\r\n" + 
		"upper(trim(c.last_name))||', '||upper(trim(c.first_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_pv_header a \r\n" + 
		"left join em_employee b on a.created_by = b.emp_code\r\n" + 
		"left join rf_entity c on b.entity_id = c.entity_id    \n" +
		"\r\n" + 
		") a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
		
	public String getStatus(){

		String sql = 
		"select \n" +

		"a.status_id, \n" +
		"b.status_desc \n" +

        "from (select distinct on(status_id) status_id from rf_pv_header) a  \n" +
        "left join mf_record_status b on a.status_id = b.status_id \n" ;	
        		
		return sql;

	}	
	
	public static String getPayment_type(){//used
		return 
		"select 'B' as \"Code\", 'CHECK' as \"Payment Type\" union all "  +
		"select 'A' as \"Code\", 'CASH'  as \"Payment Type\"   "  ;

	}
	

	

}
