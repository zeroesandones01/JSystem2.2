package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
import Lookup.lookupMethods;
import components._JInternalFrame;
import interfaces._GUI;

public class SummaryOfReturnedChecks extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = 5238270340074571524L;
	static String title = "Summary of Returned Checks To Buyer";
	static Dimension frameSize = new Dimension(571, 334);

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupReason;
	private _JLookup lookupBranch;
	
	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtReason;
	private JTextField txtBranch;
	
	private JComboBox cboStatus;
	
	private _JDateChooser dateFrom;
	private _JDateChooser dateTo;
	
	private JButton btnPreview; 
	
	public SummaryOfReturnedChecks() {
		super(title, false, true, false, true);
		initGUI();
	}

	public SummaryOfReturnedChecks(String title) {
		super(title);
		initGUI();
	}

	public SummaryOfReturnedChecks(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(571, 334));
		this.setLayout(new BorderLayout());
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(panMain, BorderLayout.WEST);
			panMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			panMain.setPreferredSize(new Dimension(568, 800));
			{
				{
					JXPanel panPage = new JXPanel(new GridLayout(5, 1, 5, 5));
					panMain.add(panPage, BorderLayout.PAGE_START);
					panPage.setPreferredSize(new java.awt.Dimension(558, 193));
					panPage.setBorder(components.JTBorderFactory.createTitleBorder("Check Details"));
					{
						{
							JXPanel panCompany = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panCompany);
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panCompany.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblCompany = new JLabel("Company"); 
											panLabel.add(lblCompany);
										}
										{
											lookupCompany = new _JLookup();
											panLabel.add(lookupCompany);
											lookupCompany.setReturnColumn(0);
											lookupCompany.setLookupSQL(SQL_COMPANY());
											lookupCompany.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){
														txtCompany.setText((String) data[1].toString()); 
														try {
															lookupProject.setLookupSQL(lookupMethods.getProject(lookupCompany.getValue()));
														} catch (NullPointerException ex) {
															
														}
													}
												}
											});
											lookupCompany.setValue("02");
										}
									}
								}
								{
									txtCompany = new JTextField("CENQHOMES DEVELOPMENT CORPORATION");
									panCompany.add(txtCompany, BorderLayout.CENTER);
									txtCompany.setHorizontalAlignment(JTextField.CENTER);
									txtCompany.setEditable(false);
								}
							}
						}
						{
							JXPanel panProject = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panProject);
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panProject.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblProject = new JLabel("Project"); 
											panLabel.add(lblProject);
										}
										{
											lookupProject = new _JLookup(null, "Project");
											panLabel.add(lookupProject);
											lookupProject.setReturnColumn(0);
											lookupProject.setLookupSQL(lookupMethods.getProject(lookupCompany.getValue()));
											lookupProject.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													
													if(data != null){
														txtProject.setText(data[1].toString());
													}
												}
											});
										}
									}
								}
								{
									txtProject = new JTextField();
									panProject.add(txtProject, BorderLayout.CENTER);
									txtProject.setHorizontalAlignment(JTextField.CENTER);
									txtProject.setEditable(false);
								}
							}
						}
						{
							JXPanel panReason = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panReason);
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panReason.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblProject = new JLabel("Reason"); 
											panLabel.add(lblProject);
										}
										{
											lookupReason = new _JLookup(null, "Status");
											panLabel.add(lookupReason);
											lookupReason.setReturnColumn(0);
											lookupReason.setLookupSQL(lookupMethods.getBounceReason());
											lookupReason.setPreferredSize(new java.awt.Dimension(62, 27));
											lookupReason.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){
														txtReason.setText(data[1].toString());
													}
												}
											});
										}
									}
								}
								{
									txtReason = new JTextField();
									panReason.add(txtReason, BorderLayout.CENTER);
									txtReason.setHorizontalAlignment(JTextField.CENTER);
									txtReason.setEditable(false);
								}
							}
						}
						{
							JXPanel panBatch = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panBatch);
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panBatch.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblProject = new JLabel("Branch"); 
											panLabel.add(lblProject);
										}
										{
											lookupBranch = new _JLookup(null, "Branch");
											panLabel.add(lookupBranch);
											lookupBranch.setReturnColumn(0);
											lookupBranch.setLookupSQL(lookupMethods.getOfficeBranch());
											lookupBranch.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet();
													if (data != null) {
														txtBranch.setText(data[1].toString());
													}
												}
											});
										}
									}
								}
								{
									txtBranch = new JTextField();
									panBatch.add(txtBranch, BorderLayout.CENTER);
									txtBranch.setHorizontalAlignment(JTextField.CENTER);
									txtBranch.setEditable(false);
								}
							}
						}
						{
							JXPanel panBatch = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panBatch);
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panBatch.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblProject = new JLabel("Type"); 
											panLabel.add(lblProject);
										}
										{
											JLabel lblProject = new JLabel(""); 
											panLabel.add(lblProject);
										}
									}
								}
								{
									String status[] = {
										"Both", 
										"Dated", 
										"PDC"
									};

									cboStatus = new JComboBox(status);
									panBatch.add(cboStatus);
									cboStatus.setSelectedItem(null);
									cboStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
									cboStatus.setBounds(537, 15, 160, 21);	
									cboStatus.setEnabled(true);
									cboStatus.setSelectedIndex(0);	
									cboStatus.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent evt) 
										{			
											
										}
									});
								}
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new GridLayout(1, 2,5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				panCenter.setPreferredSize(new java.awt.Dimension(499, 100));
				panCenter.setBorder(components.JTBorderFactory.createTitleBorder("Check Date"));
				{
					JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5)); 
					panCenter.add(panDate, BorderLayout.CENTER); 
					{
						{
							JXPanel panFrom = new JXPanel(new BorderLayout(5, 5)); 
							panDate.add(panFrom); 
							{
								{
									JLabel lblFrom = new JLabel("From", JLabel.TRAILING);
									panFrom.add(lblFrom, BorderLayout.LINE_START);
									lblFrom.setHorizontalAlignment(JLabel.LEADING);
									lblFrom.setPreferredSize(new Dimension(75, 0));
								}
								{
									dateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panFrom.add(dateFrom, BorderLayout.CENTER);						
									dateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}	
							}
						}
						{
							JXPanel panTo = new JXPanel(new BorderLayout(5, 5)); 
							panDate.add(panTo); 
							{
								{
									JLabel lblTo = new JLabel("To", JLabel.TRAILING);
									panTo.add(lblTo, BorderLayout.LINE_START);
									lblTo.setHorizontalAlignment(JLabel.CENTER);
									lblTo.setPreferredSize(new Dimension(75, 0));							
								}
								{
									dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panTo.add(dateTo, BorderLayout.CENTER);						
									dateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}	
							}
						}
					}
				}
				{
					JXPanel panEnd = new JXPanel(new BorderLayout(5, 5)); 
					panMain.add(panEnd, BorderLayout.PAGE_END); 
					panEnd.setPreferredSize(new Dimension(0, 30));
					{
						btnPreview = new JButton("Preview");
						panEnd.add(btnPreview); 
						btnPreview.setEnabled(true);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String strDate = ""; 
								
								SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy"); 
								
								if (dateFrom.getDate().toString().equals(dateTo.getDate().toString())) {
									strDate = sdf.format(dateTo.getDate());
								} else {
									strDate = sdf.format(dateFrom.getDate())+" to "+sdf.format(dateTo.getDate());
								}

								String company_logo = RequestForPayment.sql_getCompanyLogo();
								Map<String, Object> mapParameters = new HashMap<String, Object>();
								mapParameters.put("01_logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
								mapParameters.put("02_co_id", ((lookupCompany.getValue()==null)?"":lookupCompany.getValue()));
								mapParameters.put("03_proj_id", ((lookupProject.getValue()==null)?"":lookupProject.getValue()));
								mapParameters.put("04_branch_id", ((lookupBranch.getValue()==null)?"":lookupBranch.getValue()));
								mapParameters.put("05_reason", ((lookupReason.getValue()==null)?"":lookupReason.getValue()));
								mapParameters.put("06_dateFrom", dateFrom.getDate());
								mapParameters.put("07_dateTo", dateTo.getDate());
								mapParameters.put("08_checkType", cboStatus.getSelectedItem());
								mapParameters.put("09_Company", txtCompany.getText());
								mapParameters.put("10_AsOf", strDate);
								mapParameters.put("11_preparedBy", UserInfo.EmployeeCode);
								FncReport.generateReport("/Reports/rptSummaryofReturnedChecks.jasper", "Returned Checks", "", mapParameters);
							}
						});
					}
				}
			}
		}	
	}

	private static String getBranch() {
		return "SELECT branch_id, branch_name, branch_alias FROM mf_office_branch;";
	}
}
