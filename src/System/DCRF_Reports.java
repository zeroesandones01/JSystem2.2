package System;

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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;


import com.toedter.calendar.JDateChooser;

import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class DCRF_Reports extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "DCRF Reports";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCenter_b;
	private JPanel pnlSouth;
	private JPanel pnlCPFdate;
	private JPanel pnlCPFreport_type;
	private JPanel pnlCPFreport_type_a;
	private JPanel pnlCPFreport_type_b;

	private JLabel lblDivision;
	private JLabel lblInCharge;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblReport;
	private JLabel lblRequester;
	private JLabel lblErrorType;
	private JLabel lblDept;
	private JLabel lblModule;

	private _JLookup lookupDivision;
	private _JLookup lookupInCharge;
	private _JLookup lookupRequester;
	private _JLookup lookupDept;	
	private _JLookup lookupModule;	

	private JTextField txtDivision;
	private JTextField txtInCharge;
	private JTextField txtRequester;	
	private JTextField txtDept;

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JComboBox cmbErrorType;
	//private JComboBox cmbStage;


	String in_charge_id = "";
	String in_charge_name = "All";

	String div_id 		= "";
	String div_name 	= "All";

	String dept_id 		= "";
	String dept_name 	= "All";

	String req_id 		= "";
	String req_name		= "All";

	String module		= "All";

	Integer error_type_no = 0;
	Integer status_no = 0;
	Integer report_no = 0;

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JLabel lblX;
	private JLabel lblStatus;
	private JComboBox cmbStatus;
	private JComboBox cmbReport;
	private JCheckBox chkProvideDetails;
	private boolean get_details = false;

	public DCRF_Reports() {
		super(title, false, true, false, true);
		initGUI();
	}

	public DCRF_Reports(String title) {
		super(title);
		initGUI();
	}

	public DCRF_Reports(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(524, 422));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(522, 383));

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.NORTH);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(578, 88));
			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Report Option"));


			{
				pnlCPFreport_type = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFreport_type, BorderLayout.NORTH);
				pnlCPFreport_type.setPreferredSize(new java.awt.Dimension(423, 25));		

				{
					pnlCPFreport_type_a = new JPanel(new BorderLayout(0,0));
					pnlCPFreport_type.add(pnlCPFreport_type_a, BorderLayout.CENTER);
					pnlCPFreport_type_a.setPreferredSize(new java.awt.Dimension(91, 25));

					{
						lblReport = new JLabel("Report Name ", JLabel.TRAILING);
						pnlCPFreport_type_a.add(lblReport, BorderLayout.CENTER);
						lblReport.setPreferredSize(new java.awt.Dimension(84, 65));
						lblReport.setEnabled(true);							
					}
				}
				{
					pnlCPFreport_type_b = new JPanel(new BorderLayout(0,0));
					pnlCPFreport_type.add(pnlCPFreport_type_b, BorderLayout.EAST);
					pnlCPFreport_type_b.setPreferredSize(new java.awt.Dimension(428, 25));
					{
						String status[] = {
								"Pending Requests",
								"Completed Request",
								"All Requests",
								"DCRF Status Report"
						};					
						cmbReport = new JComboBox(status);
						pnlCPFreport_type_b.add(cmbReport);
						cmbReport.setSelectedItem(null);
						cmbReport.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbReport.setBounds(537, 15, 160, 21);	
						cmbReport.setEnabled(true);
						cmbReport.setSelectedIndex(0);	
						cmbReport.setPreferredSize(new java.awt.Dimension(418, 65));
						cmbReport.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{																
								report_no = cmbReport.getSelectedIndex();	

								if (cmbReport.getSelectedIndex()==0&&cmbStatus.getSelectedIndex()==5)
								{		
									//cmbStatus.setEnabled(false);
									cmbStatus.setSelectedIndex(0);	
									{JOptionPane.showMessageDialog(getContentPane(), "Selected status is not applicable", "Error", 
											JOptionPane.ERROR_MESSAGE);}
								}	
								else if (cmbReport.getSelectedIndex()==1&&cmbStatus.getSelectedIndex()!=5)
								{											
									//cmbStatus.setEnabled(true);
									cmbStatus.setSelectedIndex(5);	
									{JOptionPane.showMessageDialog(getContentPane(), "Selected status is not applicable", "Error", 
											JOptionPane.ERROR_MESSAGE);}
								}	
							}
						});
					}

				}
			}




			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.CENTER);
				pnlNorth.setPreferredSize(new java.awt.Dimension(578, 271));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details Options" ));// XXX

				{
					pnlNorthWest = new JPanel(new GridLayout(8,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(162, 173));

					{
						lblInCharge = new JLabel("In-Charge", JLabel.TRAILING);
						pnlNorthWest.add(lblInCharge);
						lblInCharge.setEnabled(true);
					}
					{
						lookupInCharge = new _JLookup(null, "In-Charge");
						pnlNorthWest.add(lookupInCharge);
						lookupInCharge.setReturnColumn(0);
						lookupInCharge.setEnabled(true);
						lookupInCharge.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									in_charge_id = (String) data[0];	
									in_charge_name	= (String) data[1];	
									txtInCharge.setText(in_charge_name);

									KEYBOARD_MANAGER.focusNextComponent();

								}
							}
						});
					}
					{
						lblDivision = new JLabel("Division", JLabel.TRAILING);
						pnlNorthWest.add(lblDivision);
						lblDivision.setEnabled(true);	
					}
					{
						lookupDivision = new _JLookup(null, "Division");
						pnlNorthWest.add(lookupDivision);
						lookupDivision.setReturnColumn(0);
						lookupDivision.setEnabled(true);
						lookupDivision.setFilterName(true);
						lookupDivision.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									div_id = (String) data[0];		
									div_name = (String) data[2];	
									txtDivision.setText(div_name);
									btnPreview.setEnabled(true);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);

									lookupDept.setLookupSQL(getDepartment());	
									lookupDept.setText("");
									txtDept.setText("");
									lookupDept.setEnabled(true);
									txtDept.setEnabled(true);
								}

							}
						});
					}
					{
						lblDept = new JLabel("Department", JLabel.TRAILING);
						pnlNorthWest.add(lblDept);
						lblDept.setEnabled(true);	
					}
					{
						lookupDept = new _JLookup(null, "Department");
						pnlNorthWest.add(lookupDept);
						lookupDept.setReturnColumn(0);
						lookupDept.setEnabled(false);
						lookupDept.setFilterName(true);
						lookupDept.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									dept_id = (String) data[0];		
									dept_name = (String) data[2];	
									txtDept.setText(dept_name);

									lookupRequester.setValue("");
									txtRequester.setText("");

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblRequester = new JLabel("Requester", JLabel.TRAILING);
						pnlNorthWest.add(lblRequester);
						lblRequester.setEnabled(true);	
					}
					{
						lookupRequester = new _JLookup(null, "Requester");
						pnlNorthWest.add(lookupRequester);
						lookupRequester.setReturnColumn(0);
						lookupRequester.setEnabled(true);
						lookupRequester.setFilterName(true);
						lookupRequester.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){								

									req_id = (String) data[0];		
									req_name = (String) data[1];	
									txtRequester.setText(req_name);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblX = new JLabel("", JLabel.TRAILING);
						pnlNorthWest.add(lblX);
						lblX.setEnabled(true);	
					}
					{
						lblModule = new JLabel("Module", JLabel.TRAILING);
						pnlNorthWest.add(lblModule);
						lblModule.setEnabled(true);	
					}

					{
						lblX = new JLabel("", JLabel.TRAILING);
						pnlNorthWest.add(lblX);
						lblX.setEnabled(false);	
					}
					{
						lblErrorType = new JLabel("Error Type", JLabel.TRAILING);
						pnlNorthWest.add(lblErrorType);
						lblErrorType.setEnabled(true);	
					}
					{
						lblX = new JLabel("", JLabel.TRAILING);
						pnlNorthWest.add(lblX);
						lblX.setEnabled(false);	
					}
					{
						lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlNorthWest.add(lblStatus);
						lblStatus.setEnabled(true);	
					}
				}

				pnlNorthEast = new JPanel(new GridLayout(8, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));

				{
					txtInCharge = new JTextField();
					pnlNorthEast.add(txtInCharge, "Center");
					txtInCharge.setEditable(false);
				}
				{
					txtDivision = new JTextField();
					pnlNorthEast.add(txtDivision, BorderLayout.CENTER);
					txtDivision.setEditable(false);
					txtDivision.setEnabled(true);
				}
				{
					txtDept = new JTextField();
					pnlNorthEast.add(txtDept, "Center");
					txtDept.setEditable(false);
					txtDept.setEnabled(false);
				}
				{
					txtRequester = new JTextField();
					pnlNorthEast.add(txtRequester, "Center");
					txtRequester.setEditable(false);
					txtRequester.setEnabled(true);
				}
				{
					lookupModule = new _JLookup(null, "Module");
					pnlNorthEast.add(lookupModule);
					lookupModule.setReturnColumn(0);
					lookupModule.setEnabled(true);
					//lookupModule.setFilterName(true);
					lookupModule.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								module = (String) data[0];		
								//posn_name = (String) data[1];	
								//txtModule.setText(posn_name);								

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					String error_type[] = {
							"All",
							"Systems Error",
							"User Error",
							"Systems Change"
					};					
					cmbErrorType = new JComboBox(error_type);
					pnlNorthEast.add(cmbErrorType);
					cmbErrorType.setSelectedItem(null);
					cmbErrorType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
					cmbErrorType.setBounds(537, 15, 160, 21);	
					cmbErrorType.setEnabled(true);
					cmbErrorType.setSelectedIndex(0);	
					cmbErrorType.setPreferredSize(new java.awt.Dimension(418, 65));
					cmbErrorType.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{		
							error_type_no = cmbErrorType.getSelectedIndex();
						}
					});
				}
				{
					String status[] = {
							"All", 
							"Active",
							"Checked",
							"Approved",
							"Fixed",
							"Completed",
							"Received"
					};					
					cmbStatus = new JComboBox(status);
					pnlNorthEast.add(cmbStatus);
					cmbStatus.setSelectedItem(null);
					cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
					cmbStatus.setBounds(537, 15, 160, 21);	
					cmbStatus.setEnabled(true);
					cmbStatus.setSelectedIndex(0);	
					cmbStatus.setPreferredSize(new java.awt.Dimension(418, 65));
					cmbStatus.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{		
							status_no = cmbStatus.getSelectedIndex();
							if (cmbReport.getSelectedIndex()==0&&cmbStatus.getSelectedIndex()==5)
							{		
								//cmbStatus.setEnabled(false);
								cmbStatus.setSelectedIndex(0);	
								{JOptionPane.showMessageDialog(getContentPane(), "Selected status is not applicable", "Error", 
										JOptionPane.ERROR_MESSAGE);}
							}	
							else if (cmbReport.getSelectedIndex()==1&&cmbStatus.getSelectedIndex()!=5)
							{											
								//cmbStatus.setEnabled(true);
								cmbStatus.setSelectedIndex(5);	
								{JOptionPane.showMessageDialog(getContentPane(), "Selected status is not applicable", "Error", 
										JOptionPane.ERROR_MESSAGE);}
							}	
						}
					});
				}
				{
					chkProvideDetails = new JCheckBox("Generate DCRF Details");
					pnlNorthEast.add(chkProvideDetails);
					chkProvideDetails.setEnabled(true);	
					chkProvideDetails.setSelected(false);
					chkProvideDetails.setPreferredSize(new java.awt.Dimension(383, 26));
					chkProvideDetails.setHorizontalAlignment(JTextField.LEFT);	
					chkProvideDetails.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

							if (selected) { get_details = true;
							} else { get_details = false; }
						}
					});
				}
			}
			{
				pnlCPFdate = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFdate, BorderLayout.CENTER);
				pnlCPFdate.setPreferredSize(new java.awt.Dimension(499, 29));		

				{
					pnlCenter_b = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCPFdate.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));							

					{
						lblDateFrom = new JLabel("DCRF Date From  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat("2016-01-01"));
					}		
					{
						lblDateTo = new JLabel("DCRF Date To  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(true);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
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
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setEnabled(true);
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
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.setEnabled(true);
					btnCancel.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbErrorType, lookupInCharge, lookupDivision,  lookupDept, lookupRequester, lookupModule,
				dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 524, 422);  //174

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupDivision.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Preview")&&cmbReport.getSelectedIndex()==0 || 
				e.getActionCommand().equals("Preview")&&cmbReport.getSelectedIndex()==1||
				e.getActionCommand().equals("Preview")&&cmbReport.getSelectedIndex()==2){ previewRequests();}
//		else{previewJervin();}
		else if(e.getActionCommand().equals("Preview")&&cmbReport.getSelectedIndex()==3){previewJervin();}

		if(e.getActionCommand().equals("Cancel"))
		{
			refreshFields();

			in_charge_id = "";
			in_charge_name = "All";			
			div_id 		= "";
			div_name 	= "All";			
			dept_id 		= "";
			dept_name 	= "All";			
			req_id 		= "";
			req_name	= "";			
			module		= "All";			
			error_type_no = 0;
			status_no = 0;

			lookupInCharge.setLookupSQL(getInCharge());
			lookupDivision.setLookupSQL(getDivision());		
			lookupDept.setLookupSQL(getDepartment());		
			lookupRequester.setLookupSQL(getRequester());		
			lookupModule.setLookupSQL(getModule());	
		}
	}


	//enable, disable
	private void refreshFields(){

		lookupInCharge.setValue("");
		txtInCharge.setText("");

		lookupDivision.setValue("");
		txtDivision.setText("");

		lookupDept.setValue("");
		txtDept.setText("");

		lookupRequester.setValue("");
		txtRequester.setText("");

		lookupModule.setValue("");		
		cmbStatus.setSelectedIndex(0);	
		cmbErrorType.setSelectedIndex(0);
		cmbReport.setSelectedIndex(0);	
		dteDateFrom.setDate(FncGlobal.dateFormat("2016-01-01"));
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		chkProvideDetails.setSelected(false);

		cmbStatus.setEnabled(true);
		get_details = false;
	}

	private void initialize_comp(){		

		refreshFields();		

		KEYBOARD_MANAGER.focusNextComponent();

		lookupInCharge.setLookupSQL(getInCharge());
		lookupDivision.setLookupSQL(getDivision());		
		lookupDept.setLookupSQL(getDepartment());		
		lookupRequester.setLookupSQL(getRequester());		
		lookupModule.setLookupSQL(getModule());		

	}


	//preview
	private void previewRequests(){

		String criteria = "All Requests";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		Map<String, Object> mapParameters = new HashMap<String, Object>();		
		mapParameters.put("emp_id", req_id);
		mapParameters.put("emp_name", req_name);
		mapParameters.put("div_id", div_id);
		mapParameters.put("div_name", div_name);		
		mapParameters.put("dept_id", dept_id);
		mapParameters.put("dept_name", dept_name);
		mapParameters.put("incharge_id", in_charge_name);
		mapParameters.put("incharge_name", in_charge_name);
		mapParameters.put("module", module);
		mapParameters.put("error_no", error_type_no);
		mapParameters.put("error_name", cmbErrorType.getSelectedItem());
		mapParameters.put("status_no", status_no);
		mapParameters.put("status_name", cmbStatus.getSelectedItem());
		mapParameters.put("report_no", report_no);
		mapParameters.put("report_name", cmbReport.getSelectedItem());	
		mapParameters.put("prepared_by", UserInfo.FullName );
		mapParameters.put("date_from", dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		System.out.printf("Display SQL for Report: %s", "select * from view_dcrf_requests('','"+div_id+"','"+dept_id+"','"+in_charge_id+"','"+req_id+"','"+module+"','"+error_type_no+"','"+status_no+"','"+report_no+"','"+dteDateFrom.getDate()+"','"+dateDateTo.getDate()+"'");
		FncReport.generateReport("/Reports/rptDCRF_pending_all_requests.jasper", reportTitle, null, mapParameters);
		
		if (get_details == true)
		{
			String criteria2 = "All Pending Requests Details";		
			String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());
			Map<String, Object> mapParameters2 = new HashMap<String, Object>();
			mapParameters2.put("emp_id", req_id);
			mapParameters2.put("emp_name", req_name);
			mapParameters2.put("div_id", div_id);
			mapParameters2.put("div_name", div_name);		
			mapParameters2.put("dept_id", dept_id);
			mapParameters2.put("dept_name", dept_name);
			mapParameters2.put("incharge_id", in_charge_name);
			mapParameters2.put("incharge_name", in_charge_name);
			mapParameters2.put("module", module);
			mapParameters2.put("error_no", error_type_no);
			mapParameters2.put("error_name", cmbErrorType.getSelectedItem());
			mapParameters2.put("status_no", status_no);
			mapParameters2.put("status_name", cmbStatus.getSelectedItem());
			mapParameters2.put("report_no", report_no);
			mapParameters2.put("report_name", cmbReport.getSelectedItem());	
			mapParameters2.put("prepared_by", UserInfo.FullName );
			mapParameters2.put("date_from", dteDateFrom.getDate());
			mapParameters2.put("date_to", dateDateTo.getDate());
			FncReport.generateReport("/Reports/rptDCRF_pending_all_requests_details.jasper", reportTitle2, null, mapParameters2);
		}
	}


	//lookup SQL	
	private String getInCharge(){

		String sql = "select distinct on (in_charge) in_charge, get_employee_name(in_charge) " +
		"from mf_privileges where in_charge is not null " ;

		return sql;

	}	

	private String getDivision(){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(division_code) as \"Div. ID\",\r\n" + 
		"trim(division_alias) as \"Div. Alias\",\r\n" + 
		"trim(division_name) as \"Div. Name\" \r\n" + 
		"\r\n" + 
		"from mf_division \r\n" +
		"where division_code not in ('04','06','07','08','09','29')" + 
		"\r\n" + 
		"order by division_code"  ;

	}

	private String getDepartment(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.dept_code as \"Dept. ID\",\r\n" + 
		"a.dept_alias  as \"Dept. Alias\",\r\n" + 
		"a.dept_name as \"Dept. Name\" \r\n" + 
		"\r\n" + 
		"\r\n" + 
		"from mf_department a\r\n" + 
		"left join mf_division b on a.division_code = b.division_code\r\n" + 
		"\r\n" + 
		"where " +
		"(case when '"+div_id+"' = '' then true else " +
		"a.division_code = '"+div_id+"' end)"  ;

	}

	private String getRequester(){//used

		String sql = 
			"select created_by as \"Requester ID\", " +
			"emp_name as \"Requester Name\" " +
			"from (select distinct on (created_by) " +
			"created_by, " +
			"get_employee_name(created_by) as emp_name  " +
			"from rf_dcrf_header where created_by is not null ) a order by emp_name " ;

		return sql;

	}

	private String getModule(){//used

		String sql = 
			"select distinct on (privileges) upper(trim(privileges)) as privileges, in_charge " +
			"from mf_privileges " +
			"where privileges != '' \n" +
			"and (case when '"+in_charge_id+"' = '' then true else in_charge = '"+in_charge_id+"' end)  " +
			"and upper(privileges) in (select distinct on (module) module from rf_dcrf_detail) " +
			"" ;

		return sql;

	}
	
	private void previewJervin(){
		
		System.out.println("Dumaan Dito@@@@@@@@@@@@@@@@@@@@@@@@@@");
		String criteria4 = "DCRF Status Report";	
		Map <String,Object>mapParameter = new HashMap<String,Object>();
		mapParameter.put("dateFrom",dteDateFrom.getDate());
		mapParameter.put("dateTo", dateDateTo.getDate());
		System.out.println("From:"+dteDateFrom);
		System.out.println("To:"+dateDateTo);
		FncReport.generateReport("/Reports/rptDCRFStatusReport.jasper",criteria4,mapParameter);
	}





}