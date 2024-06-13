package Projects.SalesandMarketing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Utilities.Tripping_Rate;
import components._JButton;
import components._JInternalFrame;
import interfaces._GUI;

public class Trip_Ticket_Entry extends _JInternalFrame implements _GUI, ActionListener, LookupListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Trip Ticket Entry";
	public static Dimension frameSize = new Dimension(700, 430);

	private ButtonGroup grpButton = new ButtonGroup();
	private JXPanel pnlNorth;
	private JLabel lblTicket;
	private _JLookup lookupTicket;
	private JCheckBox chkVehiclePre;
	private JXPanel pnlCenter;
	private JLabel lblDateNeeded;
	private JLabel lblReqstedby;
	private JLabel lblTripPurpse;
	private JLabel lblMeetingPlc;
	private JLabel lblDriver;
	private JLabel lblPrjCharge;
	private JLabel lblPrjNoPassgrs;
	private JXPanel pnlSouth;
	private _JButton btnNew;
	private _JButton btnEdit;
	private _JButton btnSave;
	private _JButton btnCancel;
	private _JDateChooser dteDateNeeded;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JSpinner spnrFrom;
	private JSpinner spnrTo;
	private JCheckBox chkTo;
	private _JLookup lookupRequested;
	private JXTextField txtRequested;
	private JXTextField txtMobileNo;
	private _JLookup lookupTrpPurpose;
	private JXTextField txtTrpPurpose;
	private _JLookup lookupMeetingPlace;
	private JXTextField txtMeetingPlace;
	private _JLookup lookupDriver;
	private JXTextField txtDriver;
	private JXTextField txtMobileNoDriver;
	private _JLookup lookupPrjCharged;
	private JXTextField txtPrjCharged;
	private JXTextField txtPrjNoPassger;
	private JLabel lblRemarks;
	private JScrollPane scpRemarks;
	private JTextArea txtareaRemaks;
	private String FinalApprvlOfficer = "000539";
	private String user_entity_id;
	private String user_div_alias;
	private String user_div_name;
	private String user_div_code;
	private String user_dept_code;
	private String user_div_head_code;
	private String user_dept_head_code;
	private String user_mobileNo;

	private _Trip_Ticket_Entry methods = new _Trip_Ticket_Entry();
	private String final_approval;
	private Boolean if_add_edit = false;
	private JLabel lblStatusTicket;
	private JLabel lblSellingUnit;
	private _JLookup lookupSalesAgent;
	private JXTextField txtSaleAgent;
	private JXTextField txtMobileSalesAgent;


	public Trip_Ticket_Entry() {
		super(title, true, true, false, true);
		initGUI();
		FormLoad();
	}

	public Trip_Ticket_Entry(String title) {
		super(title);
		initGUI();
		FormLoad();
	}

	public Trip_Ticket_Entry(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		FormLoad();
	}

	@Override
	public void initGUI() {
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new Dimension(frameSize));
			this.getContentPane().setLayout(new BorderLayout());
			this.setResizable(false);

			{
				JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{

					pnlNorth = new JXPanel();
					pnlMain.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setLayout(new BorderLayout(3, 3));
					pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 25));

					{
						JXPanel pnlWest = new JXPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlWest,BorderLayout.WEST);
						pnlWest.setPreferredSize(new Dimension(100,30 ));
						//pnlWest.setBorder(FncGlobal.lineBorder);

						{
							lblTicket = new JLabel("Trip Ticket No.");
							pnlNorth.add(lblTicket,BorderLayout.WEST);
							lblTicket.setPreferredSize(new Dimension(106,30 ));

						}
						{
							JXPanel pnlTicket = new JXPanel(new BorderLayout(3, 3));
							pnlNorth.add(pnlTicket,BorderLayout.CENTER);
							pnlTicket.setPreferredSize(new Dimension(200,30 ));
							{
								lookupTicket = new _JLookup("", "Ticket No.", 0) ; /// XXX lookupTicket 
								pnlTicket.add(lookupTicket,BorderLayout.WEST);
								lookupTicket.setPreferredSize(new Dimension(120, 25));
								lookupTicket.setLookupSQL("select ticket_no AS \"Ticket No.\",\n" + 
										"get_client_name(requested_by) AS \"Requested By\",\n" + 
										"add_date as \"Requested Date\",\n" + 
										"tripping_date AS \"Tripping Date\",\n" + 
										"get_client_name(driver_code) AS \"Driver Name\"\n" + 
										"from  rf_tripticket_header where status_id = 'A'");
								lookupTicket.setReturnColumn(0);
								lookupTicket.addLookupListener(this);
							}
							{
								chkVehiclePre = new  JCheckBox("Vehicle Request done / Pre-arranged");
								pnlTicket.add(chkVehiclePre,BorderLayout.CENTER);
							}
						}

						JXPanel pnlEast = new JXPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlEast,BorderLayout.EAST);
						pnlEast.setPreferredSize(new Dimension(100,30 ));
						{
							lblStatusTicket = new JLabel("");
							pnlNorth.add(lblStatusTicket,BorderLayout.EAST);
							lblStatusTicket.setPreferredSize(new Dimension(106,30 ));
							lblStatusTicket.setForeground(Color.RED);
						}
					}
				}
				{
					pnlCenter = new JXPanel();
					pnlMain.add(pnlCenter,BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(3, 3));
					pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
					{
						JXPanel pnlWest_label = new JXPanel(new GridLayout(8, 1, 3, 3));
						pnlCenter.add(pnlWest_label,BorderLayout.WEST);
						pnlWest_label.setPreferredSize(new Dimension(100,50 ));
						{
							lblDateNeeded = new JLabel("Date Needed ");
							pnlWest_label.add(lblDateNeeded);

							lblReqstedby = new JLabel("Requested by ");
							pnlWest_label.add(lblReqstedby);

							lblSellingUnit = new JLabel("Selling Unit ");
							pnlWest_label.add(lblSellingUnit);

							lblTripPurpse = new JLabel("Trip Purpose ");
							pnlWest_label.add(lblTripPurpse);

							lblMeetingPlc = new JLabel("Meeting \nPlace ");
							pnlWest_label.add(lblMeetingPlc);

							lblDriver = new JLabel("Driver ");
							pnlWest_label.add(lblDriver);

							lblPrjCharge = new JLabel("<html><p align=\"left\">Project to <br>be charged</p></html>");
							pnlWest_label.add(lblPrjCharge);

							lblPrjNoPassgrs = new JLabel("<html><p align=\"left\">Projected No <br>of Passengers</b></html>");
							pnlWest_label.add(lblPrjNoPassgrs);
						}
						{
							JXPanel pnlCenter_Value = new JXPanel(new GridLayout(8, 1, 3, 3));
							pnlCenter.add(pnlCenter_Value,BorderLayout.CENTER);
							//pnlCenter_Value.setPreferredSize(new Dimension(200,60 ));

							{
								JXPanel pnlDateNeeded = new JXPanel(new BorderLayout(3, 3));
								pnlCenter_Value.add(pnlDateNeeded);
								{
									dteDateNeeded = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlDateNeeded.add(dteDateNeeded,BorderLayout.WEST);
									dteDateNeeded.setDate(Tripping_Rate.dateFormat(Tripping_Rate.getDateSQL()));
									dteDateNeeded.setPreferredSize(new Dimension(150,25 ));
								}
								{
									JXPanel pnlFromTo = new JXPanel(new BorderLayout(3, 3));
									pnlDateNeeded.add(pnlFromTo,BorderLayout.CENTER);
									{
										JXPanel pnlFrom = new JXPanel(new BorderLayout(3, 3));
										pnlFromTo.add(pnlFrom,BorderLayout.WEST);
										pnlFrom.setPreferredSize(new Dimension(150,30 ));
										{
											lblFrom = new JLabel("From");
											pnlFrom.add(lblFrom,BorderLayout.WEST);
											lblFrom.setPreferredSize(new Dimension(40,30 ));
										}
										{
											JXPanel pnlTime = new JXPanel(new BorderLayout(3, 3));
											pnlFrom.add(pnlTime,BorderLayout.CENTER);
											pnlTime.setPreferredSize(new Dimension(200,30 ));
											{

												SpinnerDateModel model = new SpinnerDateModel(dateFormat("8:30:00 AM"),  null, null, Calendar.AM);

												spnrFrom = new JSpinner(model);

												JSpinner.DateEditor editor = new JSpinner.DateEditor(spnrFrom,"hh:mm:ss a");
												DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
												formatter.setAllowsInvalid(false); // this makes what you want
												formatter.setOverwriteMode(true);

												spnrFrom.setEditor(editor);

												pnlTime.add(spnrFrom,BorderLayout.WEST); 
												spnrFrom.setPreferredSize(new Dimension(100,30 ));
											}
										}
									}
									{
										JXPanel pnlTo = new JXPanel(new BorderLayout(3, 3));
										pnlFromTo.add(pnlTo,BorderLayout.CENTER);
										{
											lblTo = new JLabel("To");
											pnlTo.add(lblTo,BorderLayout.WEST);
											lblTo.setPreferredSize(new Dimension(25,30 ));
										}
										{

											JXPanel pnlTime = new JXPanel(new BorderLayout(3, 3));
											pnlTo.add(pnlTime,BorderLayout.CENTER);
											pnlTime.setPreferredSize(new Dimension(200,30 ));
											{

												SpinnerDateModel model = new SpinnerDateModel(dateFormat("5:30:00 PM"),  null, null, Calendar.PM);

												spnrTo = new JSpinner(model);

												JSpinner.DateEditor editor = new JSpinner.DateEditor(spnrTo,"hh:mm:ss a");
												DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
												formatter.setAllowsInvalid(false); // this makes what you want
												formatter.setOverwriteMode(true);

												spnrTo.setEditor(editor);

												pnlTime.add(spnrTo,BorderLayout.WEST); 
												spnrTo.setPreferredSize(new Dimension(100,25 ));
											}
											{
												chkTo = new  JCheckBox("");
												pnlTime.add(chkTo,BorderLayout.CENTER);
											}
										}
									}
								}
							}//pnlDateNeeded
							{
								JXPanel pnlRequested = new JXPanel(new BorderLayout(3, 3));
								pnlCenter_Value.add(pnlRequested);
								{

									lookupRequested = new _JLookup("", "Resquested by", 0) ; /// XXX lookupRequested 
									pnlRequested.add(lookupRequested,BorderLayout.WEST);
									lookupRequested.setLookupSQL(methods.getRequested());
									lookupRequested.setPreferredSize(new Dimension(120, 25));
									lookupRequested.setReturnColumn(0);
									lookupRequested.setFilterName(true);
									lookupRequested.addLookupListener(this);

								}
								{
									JXPanel pnlValueR = new JXPanel(new BorderLayout(3, 3));
									pnlRequested.add(pnlValueR,BorderLayout.CENTER);
									{
										txtRequested = new JXTextField("Requested by");
										pnlValueR.add(txtRequested,BorderLayout.WEST);
										txtRequested.setPreferredSize(new Dimension(280, 25));
									}
									{
										txtMobileNo = new JXTextField("Mobile No");
										pnlValueR.add(txtMobileNo,BorderLayout.CENTER);
									}
								}
								{

									JXPanel pnlSalesAgent = new JXPanel(new BorderLayout(3, 3));
									pnlCenter_Value.add(pnlSalesAgent);
									{

										lookupSalesAgent = new _JLookup("", "Selling Agent", 0) ; /// XXX lookupDriver 
										pnlSalesAgent.add(lookupSalesAgent,BorderLayout.WEST);
										lookupSalesAgent.setPreferredSize(new Dimension(120, 25));
										lookupSalesAgent.setLookupSQL(methods.getAgent());
										lookupSalesAgent.setReturnColumn(0);
										lookupSalesAgent.setFilterName(true);
										lookupSalesAgent.addLookupListener(this);
									}
									{
										JXPanel pnlValueSa = new JXPanel(new BorderLayout(3, 3));
										pnlSalesAgent.add(pnlValueSa,BorderLayout.CENTER);
										{
											txtSaleAgent = new JXTextField("Sales Agent");
											pnlValueSa.add(txtSaleAgent,BorderLayout.WEST);
											txtSaleAgent.setPreferredSize(new Dimension(280, 25));
										}
										{
											txtMobileSalesAgent = new JXTextField("Mobile No");
											pnlValueSa.add(txtMobileSalesAgent,BorderLayout.CENTER);
										}
									}
								}
							}
							{
								JXPanel pnlTripPurpose = new JXPanel(new BorderLayout(3, 3));
								pnlCenter_Value.add(pnlTripPurpose);
								{
									lookupTrpPurpose = new _JLookup("", "Trip Purpose", 0) ; /// XXX lookupTrpPurpoalale 
									pnlTripPurpose.add(lookupTrpPurpose,BorderLayout.WEST);
									lookupTrpPurpose.setPreferredSize(new Dimension(120, 25));
									lookupTrpPurpose.setReturnColumn(0);
									lookupTrpPurpose.addLookupListener(this);

								}
								{
									JXPanel pnlValueTP = new JXPanel(new BorderLayout(3, 3));
									pnlTripPurpose.add(pnlValueTP,BorderLayout.CENTER);
									{
										txtTrpPurpose = new JXTextField("Trip Purpose");
										pnlValueTP.add(txtTrpPurpose,BorderLayout.WEST);
										txtTrpPurpose.setPreferredSize(new Dimension(280, 25));
									}
								}
							}
							{
								JXPanel pnlMeetingP = new JXPanel(new BorderLayout(3, 3));
								pnlCenter_Value.add(pnlMeetingP);
								{
									lookupMeetingPlace = new _JLookup("", "Meeting Place", 0) ; /// XXX lookupMeetingPlace 
									pnlMeetingP.add(lookupMeetingPlace,BorderLayout.WEST);
									lookupMeetingPlace.setPreferredSize(new Dimension(120, 25));
									lookupMeetingPlace.setReturnColumn(0);
									lookupMeetingPlace.setLookupSQL(methods.getMeetingPlace());
									lookupMeetingPlace.addLookupListener(this);
								}
								{
									JXPanel pnlValueMP = new JXPanel(new BorderLayout(3, 3));
									pnlMeetingP.add(pnlValueMP,BorderLayout.CENTER);
									{
										txtMeetingPlace = new JXTextField("Meeting Place");
										pnlValueMP.add(txtMeetingPlace,BorderLayout.WEST);
										txtMeetingPlace.setPreferredSize(new Dimension(280, 25));
									}
								}
							}
							{

								JXPanel pnlDriver = new JXPanel(new BorderLayout(3, 3));
								pnlCenter_Value.add(pnlDriver);
								{

									lookupDriver = new _JLookup("", "Driver", 0) ; /// XXX lookupDriver 
									pnlDriver.add(lookupDriver,BorderLayout.WEST);
									lookupDriver.setPreferredSize(new Dimension(120, 25));
									lookupDriver.setLookupSQL("select distinct on (entity_id) entity_id as \"Driver Code\",get_client_name(entity_id) AS \"Driver Name\",_get_client_mobileno(entity_id) AS \"Mobile No.\" from rf_tripticket_driver_vehicles a");
									lookupDriver.setReturnColumn(0);
									lookupDriver.setFilterName(true);
									lookupDriver.addLookupListener(this);
								}
								{
									JXPanel pnlValueD = new JXPanel(new BorderLayout(3, 3));
									pnlDriver.add(pnlValueD,BorderLayout.CENTER);
									{
										txtDriver = new JXTextField("Driver");
										pnlValueD.add(txtDriver,BorderLayout.WEST);
										txtDriver.setPreferredSize(new Dimension(280, 25));
									}
									{
										txtMobileNoDriver = new JXTextField("Mobile No");
										pnlValueD.add(txtMobileNoDriver,BorderLayout.CENTER);
									}
								}
							}
							{

								JXPanel pnlPrjCharged = new JXPanel(new BorderLayout(3, 3));
								pnlCenter_Value.add(pnlPrjCharged);
								{
									lookupPrjCharged = new _JLookup("", "Project to be Charged", 0) ; /// XXX lookupPrjCharged 
									pnlPrjCharged.add(lookupPrjCharged,BorderLayout.WEST);
									lookupPrjCharged.setPreferredSize(new Dimension(120, 25));
									lookupPrjCharged.setReturnColumn(0);
									lookupPrjCharged.setLookupSQL("SELECT DISTINCT ON  (c_proj_id) c_proj_id AS \"Proj. ID\", c_proj_name AS \"Proj. Name\", c_co_id AS \"Co. ID\", c_busunit_id AS \"BusUnit ID\" FROM sp_generate_company_project()");
									lookupPrjCharged.addLookupListener(this);
								}
								{
									JXPanel pnlValuePC = new JXPanel(new BorderLayout(3, 3));
									pnlPrjCharged.add(pnlValuePC,BorderLayout.CENTER);
									{
										txtPrjCharged = new JXTextField("Project");
										pnlValuePC.add(txtPrjCharged,BorderLayout.WEST);
										txtPrjCharged.setPreferredSize(new Dimension(280, 25));
									}
								} 
							}
							{
								JXPanel pnlPrjNoP = new JXPanel(new BorderLayout(3, 3));
								pnlCenter_Value.add(pnlPrjNoP);
								{
									txtPrjNoPassger = new JXTextField("");
									pnlPrjNoP.add(txtPrjNoPassger,BorderLayout.WEST);
									txtPrjNoPassger.setPreferredSize(new Dimension(120, 25));
								}
							}
						}
						{
							JXPanel pnlSouth = new JXPanel(new BorderLayout(3, 3));
							pnlCenter.add(pnlSouth,BorderLayout.SOUTH);
							pnlSouth.setPreferredSize(new Dimension(200,60 ));
							{
								JXPanel pnlWest = new JXPanel(new GridLayout(1, 1, 3, 3));
								pnlSouth.add(pnlWest,BorderLayout.WEST);
								pnlWest.setPreferredSize(new Dimension(100,50 ));
								{
									lblRemarks = new JLabel("Remarks");
									pnlWest.add(lblRemarks,BorderLayout.WEST);
								}
								{
									scpRemarks = new JScrollPane();
									pnlSouth.add(scpRemarks,BorderLayout.CENTER);
									scpRemarks.setOpaque(true);
									scpRemarks.setPreferredSize(new java.awt.Dimension(375, 159));
									scpRemarks.setBorder(FncGlobal.lineBorder);

									{
										txtareaRemaks = new JTextArea();
										scpRemarks.add(txtareaRemaks);
										scpRemarks.setViewportView(txtareaRemaks);
										txtareaRemaks.setLineWrap(true);
										txtareaRemaks.setPreferredSize(new Dimension(366, 133));
										txtareaRemaks.setEnabled(false);	
										txtareaRemaks.setText("");
									}
								}
							}
						}
					}
				}
				{

					pnlSouth = new JXPanel();
					pnlMain.add(pnlSouth,BorderLayout.SOUTH);
					pnlSouth.setLayout(new BorderLayout(3, 3));
					pnlSouth.setPreferredSize(new Dimension(this.getWidth(), 40));
					{
						JXPanel pnlButton= new JXPanel();
						pnlSouth.add(pnlButton,BorderLayout.CENTER);
						pnlButton.setLayout(new GridLayout(1, 4, 3, 3));
						{

							{
								btnNew = new _JButton("New");
								pnlButton.add(btnNew);
								btnNew.addActionListener(this);
								btnNew.setActionCommand("New");
								grpButton.add(btnNew);
							}
							{
								btnEdit = new _JButton("Edit");
								pnlButton.add(btnEdit);
								btnEdit.addActionListener(this);
								btnEdit.setActionCommand("Edit");
								grpButton.add(btnEdit);
							}
							{
								btnSave = new _JButton("Save");
								pnlButton.add(btnSave);
								btnSave.setActionCommand("Save");
								btnSave.addActionListener(this);
							}
							{
								btnCancel = new _JButton("Cancel");
								pnlButton.add(btnCancel);
								btnCancel.addActionListener(this);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {


		if (e.getActionCommand().equals("New")) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			NewProcess();
			if_add_edit  = true;
		}

		if (e.getActionCommand().equals("Edit")) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			if_add_edit  = false;

			EditProcess();
		}


		if (e.getActionCommand().equals("Save")) {

			if (checkRequiredFields()) {

				if(checkTrippingToday(lookupDriver.getValue())){
					if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "This driver has already a tripping for today\n Do you want to proceed?", "Proceed", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							toSave();

						}
						Clear();
						FormLoad();
					}
				}else{
						if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							toSave();

						}
						Clear();
						FormLoad();
				}
			}
		}


		if (e.getSource().equals(btnCancel)) {
			Clear();
		}

	}//actionPerformed


	public static Date dateFormat(String time){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("hh:mm:ss a");
		try {
			date = (Date)formatter.parse(time);
		} catch (ParseException e) {
		} 

		System.out.println("********************" + date);
		return date;
	}

	private void FormLoad(){
		this.setComponentsEnabled(pnlCenter, false);


		final_approval = methods.getFinalApproval();

		if(checkHeadDiv_Dept(UserInfo.EmployeeCode)){
			btnState(true, true, false, false);	
		}else{
			btnState(false, false, false, false);	
		}
	}


	private Boolean getDriverAssignor(){

		pgSelect db = new pgSelect();

		Boolean driverassignor = false;

		db.select("SELECT * FROM mf_tripticket_driver_assignor WHERE user_id = '"+UserInfo.EmployeeCode+"'");

		if (db.isNotNull() ) {
			driverassignor = true;
		}else{
			driverassignor = false;
		}

		return driverassignor;
	}


	private Boolean bln_divhead = false;
	private Boolean bln_depthead = false;
	private String entity_id;
	private String requestedby;
	private String mobileNo;
	private String div_code;
	private String dept_code;
	private String mp_code;
	private String mp_desc;
	private String driver_name;
	private String driver_code;
	private String driver_mobile_no;
	private String purpose_code;
	private String purpose_desc;
	private String approved_code;
	private String acct_id;
	private String proj_id;
	private String proj_name;
	private String emp_code;
	private String co_id;
	private String busunit_id;
	private String trip_code;
	private String timeTo;
	private String timeFrom;
	private String ticketNo;
	private Date tripping_date;
	private String trip_purpose;
	private String requested_mobile;
	private Integer projected_pass;
	private String remarks;
	private String driver_mobile;
	private String status;
	private Boolean is_old_tripping;

	public boolean checkHeadDiv_Dept(String emp_code){

		pgSelect db = new pgSelect();

		db.select("select entity_id,c.division_alias, division_name,c.division_code,a.dept_code,\n" + 
				"c.div_head_code,\n" + 
				"b.dept_head_code,\n" + 
				"_get_client_mobileno(a.entity_id)\n" + 
				"from em_employee a\n" + 
				"LEFT JOIN mf_department b ON a.dept_code = b.dept_code\n" + 
				"LEFT JOIN mf_division c ON a.div_code = c.division_code \n" + 
				"where emp_code = '"+emp_code+"'\n" + 
				"\n" + 
				"");

		if (db.isNotNull()) {

			user_entity_id = db.Result[0][0].toString();
			user_div_alias = (String) (db.Result[0][1] == null ? "" : db.Result[0][1]);  
			user_div_name = (String) (db.Result[0][2] == null ? "" : db.Result[0][2]);
			user_div_code = (String) (db.Result[0][3] == null ? "" : db.Result[0][3]);
			user_dept_code = (String) (db.Result[0][4] == null ? "" : db.Result[0][4]);
			user_div_head_code = (String) (db.Result[0][5] == null ? "" : db.Result[0][5]);
			user_dept_head_code = (String) (db.Result[0][6] == null ? "" : db.Result[0][6]);
			user_mobileNo = (String) (db.Result[0][7] == null ? "" : db.Result[0][7]);

		}

		FncSystem.out("DIV", user_div_code);
		if (user_div_head_code.equals("") || user_div_head_code.isEmpty() ) {
			JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Your division has no division head assigned \nPlease call/inform ITD.","Division Head",JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			if (emp_code.equals(user_div_head_code) || UserInfo.ADMIN || emp_code.equals("900383")) {
				bln_divhead = true;
			}
		}

		if (user_dept_head_code.equals("") || user_dept_head_code.isEmpty() ) {
			JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Your deparment has no deparment head assigned \nPlease call/inform ITD.","Department Head",JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			if (emp_code.equals(user_dept_head_code) || UserInfo.ADMIN || emp_code.equals("900383") ) {
				bln_depthead = true;
			}
		}
		System.out.println("div :" +bln_divhead +" dept : "+ bln_depthead +" driver :" + getDriverAssignor() );
		if (bln_divhead == false && bln_depthead == false && getDriverAssignor() == false) {
			JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Only division and department heads are allowed to enter request.","",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCance){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCance);

	}

	private void NewProcess(){
		this.setComponentsEditable(pnlCenter, true);
		this.setComponentsEnabled(pnlCenter, true);
		dteDateNeeded.setDate(Tripping_Rate.dateFormat(Tripping_Rate.getDateSQL()));
		entity_id =  user_entity_id;
		requestedby = _Trip_Ticket_Entry.getName(entity_id);
		mobileNo = user_mobileNo;
		div_code = user_div_code;
		dept_code = user_dept_code;


		lookupTrpPurpose.setLookupSQL(_Trip_Ticket_Entry.getTripPurpose(getDriverAssignor(), bln_divhead, div_code));
		lookupRequested.setValue(entity_id);
		txtRequested.setText(requestedby);
		txtMobileNo.setText(mobileNo);

		btnState(false, false, true, true);
	}

	private void EditProcess(){
		this.setComponentsEditable(pnlCenter, true);
		this.setComponentsEnabled(pnlCenter, true);
		lookupTicket.setEditable(false);

		/*entity_id =  user_entity_id;
		requestedby = _Trip_Ticket_Entry.getName(entity_id);
		mobileNo = user_mobileNo;
		div_code = user_div_code;
		dept_code = user_dept_code;*/


		lookupTrpPurpose.setLookupSQL(_Trip_Ticket_Entry.getTripPurpose(getDriverAssignor(), bln_divhead, div_code));
		//lookupRequested.setValue(entity_id);
		//txtRequested.setText(requestedby);
		//txtMobileNo.setText(mobileNo);

		btnState(false, false, true, true);
	}

	@Override
	public void lookupPerformed(LookupEvent e) {


		if (e.getSource().equals(lookupTicket)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {

				ticketNo  = (String)data[0];

				pgSelect db = new pgSelect();

				db.select("select ticket_no,\n" +  //0 
						"tripping_date,\n" +  // 1
						"exp_time_from,\n" +  // 2
						"exp_time_to,\n" +  //  3
						"\n" + 
						"requested_by,\n" +  // 4
						"get_client_name(requested_by),\n" + //5 
						"requested_by_cellno,\n" +  // 6
						"triprate_code,\n" +  // 7
						"b.purpose_desc,\n" +  // 8
						"meet_place,\n" +  //9 
						"mp_desc,\n" +  // 10
						"driver_code,\n" +  // 11
						"get_client_name(driver_code),\n" + // 12 
						"_get_client_mobileno(driver_code),\n" +  //13
						"charged_to_projcode,\n" +  //14
						"get_project_name(charged_to_projcode),\n" + // 15 
						"COALESCE(projected_pass,0),\n" + // 16
						"remarks,\n" +  //17
						"a.status_id,\n" + 
						"is_old_tripping,\n" + 
						"co_id,\n" + 
						"busunit_id,\n" + 
						"b.purpose_code,\n" + 
						//"b.purpose,\n" + 
						"level1_approval_by,\n" + 
						"division,\n" + 
						"department\n," +
						"charged_to_acct_id \n" +
						"\n"+
						"from  rf_tripticket_header a\n" + 
						"left join mf_tripping_purpose b on b.purpose_code = a.triprate_code\n" + 
						"left join mf_tripping_meeting_place c on c.mp_code = a.meet_place\n" +
						"where ticket_no = '"+ticketNo+"' \n"+
						"");

				if (db.isNotNull()) {



					co_id = (String)db.Result[0][20];
					busunit_id = (String)db.Result[0][21];
					purpose_code = (String)db.Result[0][22];
					purpose_desc = (String)db.Result[0][8];

					approved_code = (String)db.Result[0][23];
					div_code = (String)db.Result[0][24];
					dept_code = (String)db.Result[0][25];
					acct_id = (String)db.Result[0][26];
					tripping_date = (Date)db.Result[0][1];
					Time timeFrom = (Time)db.Result[0][2];
					Time timeTo= (Time)db.Result[0][3];
					entity_id = (String)db.Result[0][4];
					requestedby = (String)db.Result[0][5];
					requested_mobile = (String)db.Result[0][6];
					trip_code = (String)db.Result[0][7];
					trip_purpose = (String)db.Result[0][8];
					mp_code = (String)db.Result[0][9];
					mp_desc = (String)db.Result[0][10];
					driver_code =  (String)db.Result[0][11];
					driver_name = (String)db.Result[0][12];
					driver_mobile = (String)db.Result[0][13];
					proj_id = (String)db.Result[0][14];
					proj_name = (String)db.Result[0][15];
					projected_pass = (Integer)db.Result[0][16];
					remarks = (String)db.Result[0][17];
					status = (String)db.Result[0][18];
					is_old_tripping = (Boolean) db.Result[0][19];

					chkVehiclePre.setSelected(is_old_tripping);
					lblStatusTicket.setText(status.equals("A") ? "Active" : "Cancelled");

					dteDateNeeded.setDate(tripping_date);
					spnrFrom.setValue(timeFrom);
					spnrTo.setValue(timeTo);
					lookupRequested.setValue(entity_id);
					txtRequested.setText(requestedby);
					txtMobileNo.setText(requested_mobile);
					lookupTrpPurpose.setValue(trip_code);
					txtTrpPurpose.setText(trip_purpose);
					lookupMeetingPlace.setValue(mp_code);
					txtMeetingPlace.setText(mp_desc);
					lookupDriver.setValue(driver_code);
					txtDriver.setText(driver_name);
					txtMobileNoDriver.setText(driver_mobile);
					lookupPrjCharged.setValue(proj_id);
					txtPrjCharged.setText(proj_name);
					txtPrjNoPassger.setText(String.valueOf(projected_pass));
					txtareaRemaks.setText(remarks);


				}

			}

		}

		if (e.getSource().equals(lookupRequested)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {
				emp_code = data[0].toString();
				entity_id =  data[1].toString();
				requestedby = data[2].toString();
				mobileNo = (String) (data[9] == null ? "" : data[9]);
				div_code = data[6].toString();
				dept_code = data[7].toString();

				lookupRequested.setValue(entity_id);
				txtRequested.setText(requestedby);
				txtMobileNo.setText(mobileNo);

			}

		}

		if (e.getSource().equals(lookupTrpPurpose)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {
				purpose_code = data[0].toString();
				purpose_desc = data[1].toString();
				approved_code = (String) (data[2] == null ? "" :data[2]);
				acct_id = data[3].toString();

				lookupTrpPurpose.setValue(purpose_code);
				txtTrpPurpose.setText(purpose_desc);

			}
		}

		if (e.getSource().equals(lookupMeetingPlace)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				mp_code = data[0].toString();
				mp_desc = data[1].toString();
				trip_code = data[2].toString();
				lookupMeetingPlace.setValue(mp_code);
				txtMeetingPlace.setText(mp_desc);
			}
			/*
			lookupDriver.setLookupSQL("SELECT seq_no as \"Priority\", \n" + 
					"        driver_code as \"Driver Code\", \n" + 
					"        driver_name as \"Driver Name\", \n" + 
					"	mobile_no As \"Mobile No.\" FROM view_tripping_driver_sequence where mp_code = '"+mp_code+"'");*/
		}

		if (e.getSource().equals(lookupDriver)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				driver_code = data[0].toString();
				driver_name = data[1].toString();
				driver_mobile_no = data[2].toString();

				lookupDriver.setValue(driver_code);
				txtDriver.setText(driver_name);
				txtMobileNoDriver.setText(driver_mobile_no);

			}
		}

		if (e.getSource().equals(lookupPrjCharged)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {

				proj_id = data[0].toString();
				proj_name = data[1].toString();
				co_id = data[2].toString();
				busunit_id = data[3].toString();

				lookupPrjCharged.setValue(proj_id);
				txtPrjCharged.setText(proj_name);

			}

		}

		if (e.getSource().equals(lookupSalesAgent)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {
				txtSaleAgent.setText((String) data[1]);
				txtMobileSalesAgent.setText( (String) (data[7].toString().contains("-") ? null : data[7]));				
			}
		}
	}

	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;

		if(lookupRequested.getValue().isEmpty() || lookupRequested.getValue().equals("")){
			required_fields = required_fields + "Requested by \n";
			toSave = false;
		}

		if(lookupTrpPurpose.getValue().isEmpty() || lookupTrpPurpose.getValue().equals("")){
			required_fields = required_fields + "Trip Purpose \n";
			toSave = false;
		}

		if(lookupMeetingPlace.getValue().isEmpty() || lookupMeetingPlace.getValue().equals("")){
			required_fields = required_fields + "Meeting Place \n";
			toSave = false;
		}

		if(lookupDriver.getValue().isEmpty() || lookupDriver.getValue().equals("")){
			required_fields = required_fields + "Driver \n";
			toSave = false;
		}

		if(lookupPrjCharged.getText().isEmpty() || lookupPrjCharged.getText().equals("")){
			required_fields = required_fields + "Project to be charged \n";
			toSave = false;
		}

		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private Boolean checkTrippingToday(String driver_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_tripticket_header WHERE driver_code = '"+driver_id+"' AND tripping_date::DATE = CURRENT_DATE)";

		db.select(SQL);
		FncSystem.out("Display SQL for Driver checker", SQL);

		return (Boolean) db.getResult()[0][0];
	}

	private void toSave(){
		pgSelect db = new pgSelect();
		timeFrom = new SimpleDateFormat("hh:mm:ss aa").format(spnrFrom.getValue());
		timeTo = new SimpleDateFormat("hh:mm:ss aa").format(spnrTo.getValue());
		String ticketno = "";
		if (if_add_edit) {
			ticketno = methods.getTicketNo();
		}else{
			ticketno = lookupTicket.getValue();
		}



		String SQL = "SELECT sp_save_trip_ticket_entry(\n" + 
				"'"+ticketno+"',---	 p_ticket_no character varying,\n" + 
				"'"+driver_code+"',---    p_driver_code character varying,\n" + 
				"NULLIF('"+driver_mobile_no+"',''),---    p_driver_mobile character varying,\n" + 
				"'"+dteDateNeeded.getDate()+"',---    p_tripping_date timestamp without time zone,\n" + 
				"'"+entity_id+"',---    p_requested_by character varying,\n" + 
				"NULLIF('"+mobileNo+"',''),---    p_requested_mobile character varying,\n" + 
				"'"+timeFrom+"',---    p_exp_time_from time without time zone,\n" + 
				"'"+timeTo+"',---    p_exp_time_to time without time zone,\n" + 
				"'"+trip_code+"',---    p_triprate_code character varying,\n" + 
				"'"+mp_code+"',---    p_meet_place character varying,\n" + 
				"NULLIF('"+txtPrjNoPassger.getText()+"',''),---    p_projected_pass character varying,\n" + 
				"'"+timeFrom+"',---    p_meet_time timestamp without time zone,\n" + 
				"'"+timeFrom+"',---    p_departure character varying,\n" + 
				"'"+purpose_code+"',---    p_purpose_code character varying,\n" + 
				"'"+purpose_desc+"',---    p_purpose character varying,\n" + 
				"'"+proj_id+"',---    p_charged_to_projcode character varying,\n" + 
				"'"+div_code+"',---    p_division character varying,\n" + 
				"'"+dept_code+"',---    p_department character varying,\n" + 
				"'"+acct_id+"',---    p_charged_to_acct_id character varying,\n" + 
				"'"+txtareaRemaks.getText()+"',---    p_remarks character varying,\n" + 
				"'"+UserInfo.Division+"',---    p_user_divcode character varying,\n" + 
				"'"+co_id+"',---    p_co_id character varying,\n" + 
				"'"+busunit_id+"',---    p_busunit_id character varying,\n" + 
				"NULLIF('"+approved_code+"',''),---    p_level1_approval_by character varying,\n" + 
				"'"+final_approval+"',---    p_level2_approval_by character varying,\n" + 
				"'A',---    p_fwd_for_pmt_apprv_status character varying,\n" + 
				""+chkVehiclePre.isSelected()+",---    p_is_old_tripping Boolean,\n" + 
				"'"+UserInfo.EmployeeCode+"',---    p_add_by character varying,    \n" +
				"null,---    p_vehic_assigned_by character varying,    \n" + 
				"NULLIF('"+lookupSalesAgent.getValue()+"',''),---    p_vehic_assigned_by character varying,    \n" + 
				"NULLIF('"+txtMobileSalesAgent.getText()+"',''),---    p_vehic_assigned_by character varying,    \n" + 
				""+if_add_edit+"---    if_add_edit BOolean	\n" + 
				"    )";


		FncSystem.out("SQL", SQL);
		db.select(SQL);

		if (if_add_edit) {
			lblStatusTicket.setText("ACTIVE");	
		} 

	}


	private void Clear(){

		this.setComponentsClear(pnlNorth, "");
		this.setComponentsClear(pnlCenter, "");
		txtareaRemaks.setText("");

		this.setComponentsEditable(pnlCenter, false);
		this.setComponentsEnabled(pnlCenter, false);
		lookupTicket.setEditable(true);
		btnState(true, true, false, false);
		lblStatusTicket.setText("");
	}





}