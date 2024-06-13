package Projects.SalesandMarketing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Utilities.Tripping_Rate;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel.model_SalesGroups;
import tablemodel.model_SellingUnit;

public class Tripping_Cost extends _JInternalFrame implements _GUI, LookupListener,ChangeListener,KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Actual Tripping Result / Tripping Cost";
	public static Dimension frameSize = new Dimension(1080, 600);
	private ButtonGroup grpButton = new ButtonGroup();
	private JXPanel pnlNorth;
	private _JTabbedPane tabCenter;
	private pnlTripping pnlTrip;
	private JLabel lblTripTicket;
	private JLabel lblMeeting;
	private JLabel lblPurpose;
	private JLabel lblDriverName;
	private _JLookup lookupTicket;
	private JLabel lblDate;
	private _JDateChooser dteDateTripping;
	private _JLookup lookupDriver;
	private JXTextField txtDriver;
	private JXTextField txtMobileNoDriver;
	private _JLookup lookupMeetingPlace;
	private JXTextField txtMeetingPlace;
	private _JLookup lookupTrpPurpose;
	private JXTextField txtTrpPurpose;
	private JLabel lblFrom;
	private JSpinner spnrFrom;
	private JLabel lblTo;
	private JSpinner spnrTo;
	private JLabel lblTime;
	private JLabel lblKm;
	private JLabel lblKMFrom;
	private _JXFormattedTextField txtKMFrom;
	private _JXFormattedTextField txtKMTo;
	private JLabel lblKMTo;
	private JLabel lblReqby;
	private JLabel lblProjCharge;
	private _JLookup lookupRequested;
	private JXTextField txtRequested;
	private _JLookup lookupPrjCharged;
	private JXTextField txtPrjCharged;
	private JLabel lblDiv;
	private JLabel lblSU;
	private JXTextField txtDiv;
	private JXTextField txtGrp;
	private JXTextField txtSU;
	private _JTabbedPane tabSouth;
	private JPanel pnlSalesGroup;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	private JXPanel pnlListSaleGrp;
	private model_SalesGroups modelListSalesGrp;
	private _JTableMain tblListSalesGrp;
	private JList rowListSalesGrp;
	private _JScrollPaneMain scrollListSellingUnit;
	private model_SellingUnit modelListSellingUnit;
	private _JTableMain tblListSellingUnit;
	private JList rowListSellingUnit;
	private JPanel pnlTripping;
	private String c_total_hrs;
	private String c_total_km;
	private String c_min_hrs;
	private String c_standard_rate;
	private String c_min_km;
	private Boolean is_cancelled = false;
	private _JScrollPaneTotal scrollGrpAmountTotal;
	private model_SalesGroups modelGrpAmountTotal;
	private _JTableTotal tblGrpAmountTotal;
	private JButton btnAddRow;
	private JButton btnRemoveRow;
	private _JScrollPaneMain scrollListSalesGrp;
	private String div_code;
	private _JScrollPaneTotal scrollSellingsAmountTotal;
	private model_SellingUnit modelSellingAmountTotal;
	private _JTableTotal tblSellingAmountTotal;
	private JButton btnAddRowSelling;
	private JButton btnRemoveRowSelling;
	private _Trip_Ticket_Entry methods = new _Trip_Ticket_Entry();
	private BigDecimal totalgrp  = new BigDecimal("0.00");;
	private BigDecimal totalselling  = new BigDecimal("0.00");
	private BigDecimal c_hr_excess;
	private BigDecimal c_total_excess_hrs;
	private BigDecimal c_km_excess;
	private BigDecimal c_total_excess_km;
	private BigDecimal c_total_cost;
	private String c_plate_no;
	private String c_vehicle;
	private String c_trip_rate_code;
	private String c_excess_rate_hr;
	private String c_excess_rate_km;
	private String timeFrom;
	private String timeTo;
	private String meet_time;
	private String departure;
	private String dept_code;
	private String co_id;
	private String busunit_id;
	private String charge_acct_id;
	private String selling_unit;
	private String charge_acct_name;
	private String rplf_no;
	private JPanel Center;
	private String c_toll_fee;
	private Boolean cancelled_rate = false;
	private Boolean fixed_rate = false;
	private String c_totalcost;

	public Tripping_Cost() {
		super(title, true, true, false, true);
		initGUI();
		Formload();
	}

	public Tripping_Cost(String title) {
		super(title);
		initGUI();
		Formload();
	}

	public Tripping_Cost(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		Formload();
	}

	@Override 
	public void initGUI() {
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
				pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 120));

				{ 
					JPanel pnlDetails = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlDetails, BorderLayout.NORTH);
					pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
					pnlDetails.setPreferredSize(new Dimension(this.getWidth(), 120));
					{
						JPanel West = new JPanel(new BorderLayout(5, 5));
						pnlDetails.add(West, BorderLayout.WEST);
						West.setPreferredSize(new Dimension(500, 130));
						{

							JPanel pnlLabel = new JPanel(new GridLayout(4, 1, 3, 3));
							West.add(pnlLabel,BorderLayout.WEST);
							{
								lblTripTicket = new JLabel("Trip Ticket No.");
								pnlLabel.add(lblTripTicket);

								lblDriverName = new JLabel("Driver Name");
								pnlLabel.add(lblDriverName);

								lblMeeting = new JLabel("Meeting Place");
								pnlLabel.add(lblMeeting);

								lblPurpose = new JLabel("Purpose");
								pnlLabel.add(lblPurpose);

							}

							JPanel pnlAction = new JPanel(new GridLayout(4, 1, 3, 3));
							West.add(pnlAction,BorderLayout.CENTER);
							{
								JPanel pnl = new JPanel(new BorderLayout(3, 3));
								pnlAction.add(pnl);
								pnl.setPreferredSize(new Dimension(500, 25));
								{
									lookupTicket = new _JLookup("", "Ticket No.", 0) ; /// XXX lookupTicket 
									pnl.add(lookupTicket,BorderLayout.WEST);
									lookupTicket.setPreferredSize(new Dimension(100, 25));
									lookupTicket.setLookupSQL(methods.getTicketEntryEdit());
									lookupTicket.setReturnColumn(0);
									lookupTicket.addLookupListener(this);

								}
								{
									JPanel pnlDate = new JPanel(new BorderLayout(3, 3));
									pnl.add(pnlDate,BorderLayout.EAST);
									{
										lblDate = new JLabel("Date");
										pnlDate.add(lblDate,BorderLayout.WEST);
									}
									{
										dteDateTripping = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										pnlDate.add(dteDateTripping,BorderLayout.EAST);
										dteDateTripping.setDate(Tripping_Rate.dateFormat(Tripping_Rate.getDateSQL()));
										dteDateTripping.setPreferredSize(new Dimension(150,25 ));
									}
								}
							}
							{
								JXPanel pnlDriver = new JXPanel(new BorderLayout(3, 3));
								pnlAction.add(pnlDriver);
								{

									lookupDriver = new _JLookup("", "Driver", 0) ; /// XXX lookupDriver 
									pnlDriver.add(lookupDriver,BorderLayout.WEST);
									lookupDriver.setPreferredSize(new Dimension(100, 25));
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
										txtDriver.setPreferredSize(new Dimension(180, 25));
									}
									{
										txtMobileNoDriver = new JXTextField("Mobile No");
										pnlValueD.add(txtMobileNoDriver,BorderLayout.CENTER);
									}
								}
							}
							{

								JXPanel pnlMeetingP = new JXPanel(new BorderLayout(3, 3));
								pnlAction.add(pnlMeetingP);
								{
									lookupMeetingPlace = new _JLookup("", "Meeting Place", 0) ; /// XXX lookupMeetingPlace 
									pnlMeetingP.add(lookupMeetingPlace,BorderLayout.WEST);
									lookupMeetingPlace.setPreferredSize(new Dimension(100, 25));
									lookupMeetingPlace.setReturnColumn(0);
									lookupMeetingPlace.setLookupSQL(methods.getMeetingPlace());
									lookupMeetingPlace.addLookupListener(this);
								}
								{
									JXPanel pnlValueMP = new JXPanel(new BorderLayout(3, 3));
									pnlMeetingP.add(pnlValueMP,BorderLayout.CENTER);
									{
										txtMeetingPlace = new JXTextField("Meeting Place");
										pnlValueMP.add(txtMeetingPlace,BorderLayout.CENTER);
										txtMeetingPlace.setPreferredSize(new Dimension(280, 25));
									}
								}
							}
							{

								JXPanel pnlTripPurpose = new JXPanel(new BorderLayout(3, 3));
								pnlAction.add(pnlTripPurpose);
								{
									lookupTrpPurpose = new _JLookup("", "Trip Purpose", 0) ; /// XXX lookupTrpPurpose 
									pnlTripPurpose.add(lookupTrpPurpose,BorderLayout.WEST);
									lookupTrpPurpose.setPreferredSize(new Dimension(100, 25));
									lookupTrpPurpose.setReturnColumn(0);
									lookupTrpPurpose.addLookupListener(this);

								}
								{
									JXPanel pnlValueTP = new JXPanel(new BorderLayout(3, 3));
									pnlTripPurpose.add(pnlValueTP,BorderLayout.CENTER);
									{
										txtTrpPurpose = new JXTextField("Trip Purpose");
										pnlValueTP.add(txtTrpPurpose,BorderLayout.CENTER);
									}
								}
							}
						}

						{

							Center = new JPanel(new BorderLayout(5, 5));
							pnlDetails.add(Center, BorderLayout.CENTER);
							Center.setPreferredSize(new Dimension(500, 0));
							{
								JPanel North = new JPanel(new BorderLayout(5, 5));
								Center.add(North, BorderLayout.NORTH);
								North.setPreferredSize(new Dimension(0, 25));
								{
									lblTime = new JLabel("Time");
									North.add(lblTime,BorderLayout.WEST);
								}
								{

									JXPanel pnlFromTo = new JXPanel(new BorderLayout(3, 3));
									North.add(pnlFromTo,BorderLayout.CENTER);
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

												SpinnerDateModel model = new SpinnerDateModel(dateFormat("8:00:00 AM"),  null, null, Calendar.AM);

												setSpnrFrom(new JSpinner(model));

												JSpinner.DateEditor editor = new JSpinner.DateEditor(getSpnrFrom(),"hh:mm:ss a");
												DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
												formatter.setAllowsInvalid(false); 
												formatter.setOverwriteMode(true);

												getSpnrFrom().setEditor(editor);

												pnlTime.add(getSpnrFrom(),BorderLayout.WEST); 
												getSpnrFrom().setPreferredSize(new Dimension(100,30 ));
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

												SpinnerDateModel model = new SpinnerDateModel(dateFormat("5:00:00 PM"),  null, null, Calendar.PM);

												setSpnrTo(new JSpinner(model));

												JSpinner.DateEditor editor = new JSpinner.DateEditor(getSpnrTo(),"hh:mm:ss a");
												DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
												formatter.setAllowsInvalid(false);
												formatter.setOverwriteMode(true);

												getSpnrTo().setEditor(editor);

												pnlTime.add(getSpnrTo(),BorderLayout.WEST); 
												getSpnrTo().setPreferredSize(new Dimension(100,25 ));
												getSpnrTo().addChangeListener(this);
												getSpnrTo().addKeyListener(this);
											}
										}
									}
								}
								{
									JXPanel pnlKm = new JXPanel(new BorderLayout(3, 3));
									North.add(pnlKm,BorderLayout.EAST);
									pnlKm.setPreferredSize(new Dimension(200, 25));
									{
										JXPanel pnl = new JXPanel(new BorderLayout(3, 3));
										pnlKm.add(pnl,BorderLayout.CENTER);
										{
											lblKm = new JLabel("KM");
											pnl.add(lblKm,BorderLayout.WEST);
										}
										{
											JXPanel pnlFromTo = new JXPanel(new BorderLayout(3, 3));
											pnl.add(pnlFromTo,BorderLayout.CENTER);
											{
												JXPanel pnlFrom = new JXPanel(new BorderLayout(3, 3));
												pnlFromTo.add(pnlFrom,BorderLayout.WEST);
												pnlFrom.setPreferredSize(new Dimension(100,25 ));
												{
													lblKMFrom = new JLabel("From");
													pnlFrom.add(lblKMFrom,BorderLayout.WEST);
													lblKMFrom.setPreferredSize(new Dimension(35,25 ));
												}
												{
													txtKMFrom = new _JXFormattedTextField("From KM");
													pnlFrom.add(txtKMFrom,BorderLayout.CENTER);
													txtKMFrom.setPreferredSize(new Dimension(30,25 ));
													txtKMFrom.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
													//txtKMFrom.setFormatterFactory(_JXFormattedTextField.INTEGER);
													txtKMFrom.addKeyListener(this);
												}

												JXPanel pnlTo = new JXPanel(new BorderLayout(3, 3));
												pnlFromTo.add(pnlTo,BorderLayout.CENTER);
												{
													{
														lblKMTo = new JLabel("To");
														pnlTo.add(lblKMTo,BorderLayout.WEST);
														lblKMTo.setPreferredSize(new Dimension(20,25 ));
													}
													{
														txtKMTo = new _JXFormattedTextField("To KM");
														pnlTo.add(txtKMTo,BorderLayout.CENTER);
														txtKMTo.setPreferredSize(new Dimension(40,25 ));
														txtKMTo.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
														//txtKMTo.setFormatterFactory(_JXFormattedTextField.INTEGER);
														txtKMTo.addKeyListener(this);
													}
												}
											}
										}
									}
								}

							}
							{

								JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
								Center.add(pnlCenter, BorderLayout.CENTER);
								{

									JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
									pnlCenter.add(pnlWest, BorderLayout.WEST);
									pnlWest.setPreferredSize(new Dimension(350,25 ));

									{
										JPanel pnlLabel = new JPanel(new GridLayout(2, 1, 3, 3));
										pnlWest.add(pnlLabel,BorderLayout.WEST);
										{
											lblReqby = new JLabel("Requested by ");
											pnlLabel.add(lblReqby);

											lblProjCharge = new JLabel("<html><p align=\"left\">Project to <br>be charged</p></html>");
											pnlLabel.add(lblProjCharge);

										}
									}

									JXPanel pnlCenter_Value = new JXPanel(new GridLayout(2, 1, 3, 3));
									pnlWest.add(pnlCenter_Value,BorderLayout.CENTER);
									{
										JXPanel pnlRequested = new JXPanel(new BorderLayout(3, 3));
										pnlCenter_Value.add(pnlRequested);
										{
											{
												lookupRequested = new _JLookup("", "Resquested by", 0) ; /// XXX lookupRequested 
												pnlRequested.add(lookupRequested,BorderLayout.WEST);
												lookupRequested.setLookupSQL(methods.getRequested());
												lookupRequested.setPreferredSize(new Dimension(80, 25));
												lookupRequested.setReturnColumn(0);
												lookupRequested.setFilterName(true);
												lookupRequested.addLookupListener(this);
											}
											{
												txtRequested = new JXTextField("Requested by");
												pnlRequested.add(txtRequested,BorderLayout.CENTER);
												txtRequested.setPreferredSize(new Dimension(180, 25));
												txtRequested.setCaretPosition(0);
											}



											JXPanel pnlPrjCharged = new JXPanel(new BorderLayout(3, 3));
											pnlCenter_Value.add(pnlPrjCharged);
											{
												lookupPrjCharged = new _JLookup("", "Project to be Charged", 0) ; /// XXX lookupPrjCharged 
												pnlPrjCharged.add(lookupPrjCharged,BorderLayout.WEST);
												lookupPrjCharged.setPreferredSize(new Dimension(80, 25));
												lookupPrjCharged.setReturnColumn(0);
												lookupPrjCharged.setLookupSQL("SELECT DISTINCT ON  (c_proj_id) c_proj_id AS \"Proj. ID\", c_proj_name AS \"Proj. Name\", c_co_id AS \"Co. ID\", c_busunit_id AS \"BusUnit ID\" FROM sp_generate_company_project()");
												lookupPrjCharged.addLookupListener(this);
											}
											{
												txtPrjCharged = new JXTextField("Project");
												pnlPrjCharged.add(txtPrjCharged,BorderLayout.CENTER);
												txtPrjCharged.setPreferredSize(new Dimension(175, 25));
											}
										}
									}

									JPanel pnlC = new JPanel(new BorderLayout(3, 3));
									pnlCenter.add(pnlC, BorderLayout.CENTER);
									{

										JPanel pnlLabel = new JPanel(new GridLayout(2, 1, 3, 3));
										pnlC.add(pnlLabel,BorderLayout.WEST);
										{
											lblDiv = new JLabel("Div");
											pnlLabel.add(lblDiv);

											lblSU = new JLabel("SU");
											pnlLabel.add(lblSU);

										}
										JPanel pnlAction = new JPanel(new GridLayout(2, 1, 3, 3));
										pnlC.add(pnlAction,BorderLayout.CENTER);
										{

											JPanel pnlDiv = new JPanel(new BorderLayout(3, 3));
											pnlAction.add(pnlDiv, BorderLayout.CENTER);
											{
												txtDiv = new JXTextField("Div");
												pnlDiv.add(txtDiv,BorderLayout.WEST);
												txtDiv.setPreferredSize(new Dimension(65, 25));

											}
											{
												JPanel pnlGrp = new JPanel(new BorderLayout(5, 5));
												pnlDiv.add(pnlGrp, BorderLayout.CENTER);

												{
													lblDiv = new JLabel("Grp");
													pnlGrp.add(lblDiv,BorderLayout.WEST);
												}
												{
													txtGrp = new JXTextField("Group");
													pnlGrp.add(txtGrp,BorderLayout.CENTER);
													txtGrp.setPreferredSize(new Dimension(50, 25));
												}
											}

											txtSU = new JXTextField("");
											pnlAction.add(txtSU,BorderLayout.WEST);
											txtSU.setPreferredSize(new Dimension(50, 25));
										}
									}
								}
							}
						}
					}
				}
				{
					pnlTripping = new JPanel(new BorderLayout(5,5));
					pnlMain.add(pnlTripping, BorderLayout.CENTER);
					{
						tabCenter = new _JTabbedPane();
						pnlTripping.add(tabCenter, BorderLayout.CENTER);
						{
							pnlTrip = new pnlTripping(this); 
							tabCenter.addTab("Tripping Computation", pnlTrip);
						}
					}
				}
				{
					JPanel pnlSouth= new JPanel(new BorderLayout(5,5));
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(0, 180));
					{
						JPanel pnlTab= new JPanel(new BorderLayout(5,5));
						pnlSouth.add(pnlTab, BorderLayout.CENTER);

						{
							tabSouth = new _JTabbedPane();
							pnlTab.add(tabSouth, BorderLayout.CENTER);

							{
								pnlSalesGroup = new JPanel(new BorderLayout());
								tabSouth.addTab(" Authority to Deduc (Sales Groups/Selling Units) ", pnlSalesGroup);

								JXPanel pnlCenter = new JXPanel(new GridLayout(0, 2, 10, 10));
								pnlSalesGroup.add(pnlCenter,BorderLayout.CENTER);

								{
									pnlListSaleGrp = new JXPanel(new BorderLayout(5, 5));
									pnlCenter.add(pnlListSaleGrp);
									{

										scrollListSalesGrp = new _JScrollPaneMain();
										pnlListSaleGrp.add(scrollListSalesGrp, BorderLayout.CENTER);
										scrollListSalesGrp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										scrollListSalesGrp.addMouseListener(new MouseAdapter() {
											public void mouseClicked(MouseEvent e) {
												tblListSalesGrp.clearSelection();
											}
										});
										{

											setModelListSalesGrp(new model_SalesGroups());
											getModelListSalesGrp().addTableModelListener(new TableModelListener() {
												public void tableChanged(TableModelEvent e) {
													//Addition of rows
													if(e.getType() == 1){
														((DefaultListModel)rowListSalesGrp.getModel()).addElement(getModelListSalesGrp().getRowCount());

														if(getModelListSalesGrp().getRowCount() == 0){
															rowListSalesGrp.setModel(new DefaultListModel());
														}
													}
												}
											});

											tblListSalesGrp = new _JTableMain(getModelListSalesGrp());
											scrollListSalesGrp.setViewportView(tblListSalesGrp);
											tblListSalesGrp.setHorizontalScrollEnabled(true); 
											getModelListSalesGrp().setEditable(true); 
											tblListSalesGrp.setRowHeight(22); 
											tblListSalesGrp.packAll();
											tblListSalesGrp.hideColumns("Div. ID");
											tblListSalesGrp.addPropertyChangeListener(new PropertyChangeListener() {//XXX 

												public void propertyChange(PropertyChangeEvent arg0) {
													_JTableMain table = (_JTableMain) arg0.getSource();
													String propertyName = arg0.getPropertyName();

													if (propertyName.equals("tableCellEditor")) {
														int column = table.convertColumnIndexToModel(table.getEditingColumn());
														int row = table.getEditingRow();

														int total_amount = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("Amount"));

														if (column != -1 && column != 3 && getModelListSalesGrp().getColumnClass(column).equals(BigDecimal.class)) {
															Object oldValue = null;
															try {
																oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
															} catch (NullPointerException e) {
															}

															if (oldValue != null) {
																if (oldValue instanceof Double) {
																	table.setValueAt(new BigDecimal((Double) oldValue), row, column);
																	getModelListSalesGrp().setValueAt(new BigDecimal((Double) oldValue), row, total_amount);
																}
																if (oldValue instanceof Long) {
																	table.setValueAt(new BigDecimal((Long) oldValue), row, column);
																	getModelListSalesGrp().setValueAt(new BigDecimal((Long) oldValue), row, total_amount);
																}
																computeTotal(getModelListSalesGrp(),modelGrpAmountTotal);
															}
														}
													}
												}
											});
											/** Repaint for Highlight **/
											tblListSalesGrp.getTableHeader().addMouseListener(new MouseAdapter() {
												public void mouseClicked(MouseEvent evt) {
													tblListSalesGrp.repaint();
												}
											});
										}
										{
											rowListSalesGrp = tblListSalesGrp.getRowHeader();
											rowListSalesGrp.setModel(new DefaultListModel());
											scrollListSalesGrp.setRowHeaderView(rowListSalesGrp);
											scrollListSalesGrp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										}


										{
											scrollGrpAmountTotal = new _JScrollPaneTotal(scrollListSalesGrp);
											pnlListSaleGrp.add(scrollGrpAmountTotal, BorderLayout.SOUTH);
											{
												modelGrpAmountTotal = new model_SalesGroups();
												modelGrpAmountTotal.addRow(new Object[] {null, "Total", 0.00 });

												tblGrpAmountTotal = new _JTableTotal(modelGrpAmountTotal, tblListSalesGrp);
												scrollGrpAmountTotal.setViewportView(tblGrpAmountTotal);
												tblGrpAmountTotal.hideColumns("Div. ID");
												//pnlCenter.tblGrpAmountTotal.hideColumns("Balance");
												//nlCenter.tblGrpAmountTotal.setTotalLabel(0);
												tblGrpAmountTotal.setTotalLabel(1);
											}
											scrollGrpAmountTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigationGroup());
										}
									}
								}
								{

									JXPanel pnlListSellingunit = new JXPanel(new BorderLayout(5, 5));
									pnlCenter.add(pnlListSellingunit);
									{

										scrollListSellingUnit = new _JScrollPaneMain();
										pnlListSellingunit.add(scrollListSellingUnit, BorderLayout.CENTER);
										scrollListSellingUnit.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										scrollListSellingUnit.addMouseListener(new MouseAdapter() {
											public void mouseClicked(MouseEvent e) {
												tblListSellingUnit.clearSelection();
											}
										});
										{

											setModelListSellingUnit(new model_SellingUnit());
											getModelListSellingUnit().addTableModelListener(new TableModelListener() {
												public void tableChanged(TableModelEvent e) {
													//Addition of rows
													if(e.getType() == 1){
														((DefaultListModel)rowListSellingUnit.getModel()).addElement(getModelListSellingUnit().getRowCount());

														if(getModelListSellingUnit().getRowCount() == 0){
															rowListSellingUnit.setModel(new DefaultListModel());
														}
													}
												}
											});

											tblListSellingUnit = new _JTableMain(getModelListSellingUnit());
											scrollListSellingUnit.setViewportView(tblListSellingUnit);
											tblListSellingUnit.setHorizontalScrollEnabled(true); 
											getModelListSellingUnit().setEditable(true); 
											tblListSellingUnit.setRowHeight(22); 
											tblListSellingUnit.packAll();  
											tblListSellingUnit.hideColumns("Entity_ID","Div.ID","Dept.ID");
											tblListSellingUnit.addPropertyChangeListener(new PropertyChangeListener() {//XXX 
												public void propertyChange(PropertyChangeEvent arg0) {
													_JTableMain table = (_JTableMain) arg0.getSource();
													String propertyName = arg0.getPropertyName();

													if (propertyName.equals("tableCellEditor")) {
														int column = table.convertColumnIndexToModel(table.getEditingColumn());
														int row = table.getEditingRow();

														int total_amount = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("Amount"));

														if (column != -1 && column != 3 && getModelListSellingUnit().getColumnClass(column).equals(BigDecimal.class)) {
															Object oldValue = null;
															try {
																oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
															} catch (NullPointerException e) {
															}

															if (oldValue != null) {
																if (oldValue instanceof Double) {
																	table.setValueAt(new BigDecimal((Double) oldValue), row, column);
																	getModelListSellingUnit().setValueAt(new BigDecimal((Double) oldValue), row, total_amount);
																}
																if (oldValue instanceof Long) {
																	table.setValueAt(new BigDecimal((Long) oldValue), row, column);
																	getModelListSellingUnit().setValueAt(new BigDecimal((Long) oldValue), row, total_amount);
																}
																computeTotalSelling(getModelListSellingUnit(),modelSellingAmountTotal);
															}
														}
													}
												}
											});
											/** Repaint for Highlight **/
											tblListSellingUnit.getTableHeader().addMouseListener(new MouseAdapter() {
												public void mouseClicked(MouseEvent evt) {
													tblListSellingUnit.repaint();
												}
											});
										}
										{
											rowListSellingUnit = tblListSellingUnit.getRowHeader();
											rowListSellingUnit.setModel(new DefaultListModel());
											scrollListSellingUnit.setRowHeaderView(rowListSellingUnit);
											scrollListSellingUnit.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										}
										{

											scrollSellingsAmountTotal = new _JScrollPaneTotal(scrollListSellingUnit);
											pnlListSellingunit.add(scrollSellingsAmountTotal, BorderLayout.SOUTH);
											{
												modelSellingAmountTotal = new model_SellingUnit();
												modelSellingAmountTotal.addRow(new Object[] {null,"Total", 0.00 });

												tblSellingAmountTotal = new _JTableTotal(modelSellingAmountTotal, tblListSellingUnit);
												scrollSellingsAmountTotal.setViewportView(tblSellingAmountTotal);
												tblSellingAmountTotal.hideColumns("Entity_ID","Div.ID","Dept.ID");
												//pnlCenter.tblGrpAmountTotal.hideColumns("Balance");
												tblSellingAmountTotal.setTotalLabel(1);
											}
											scrollSellingsAmountTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigationSelling());
										}
									}
								}
							}
						}

						JPanel pnlButton = new JPanel(new BorderLayout(5,5));
						pnlSouth.add(pnlButton, BorderLayout.SOUTH);
						pnlButton.setPreferredSize(new Dimension(0, 30));
						{

							JPanel pnlButtonAction = new JPanel(new GridLayout(1, 4, 3, 3));
							pnlButton.add(pnlButtonAction, BorderLayout.EAST);
							pnlButtonAction.setPreferredSize(new Dimension(500, 25));
							{

								btnNew = new JButton("New");
								pnlButtonAction.add(btnNew);
								btnNew.addActionListener(this);
								btnNew.setActionCommand("New");
								grpButton.add(btnNew);

								btnEdit = new JButton("Edit");
								pnlButtonAction.add(btnEdit);
								btnEdit.addActionListener(this);
								btnEdit.setActionCommand("Edit");
								grpButton.add(btnEdit);

								btnSave = new JButton("Save");
								pnlButtonAction.add(btnSave);
								btnSave.addActionListener(this);

								btnCancel = new JButton("Cancel");
								pnlButtonAction.add(btnCancel);
								btnCancel.addActionListener(this);
							}
						}
					}	
				}
			}
		}

	}

	@Override
	public void lookupPerformed(LookupEvent e) {

		if (e.getSource().equals(lookupTicket)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				ClearProcess();
				lookupTicket.setValue(data[0].toString());

				pgSelect db = new pgSelect();

				db.select(getDetails(data[0].toString()));

				if (db.isNotNull()) {
					Date trip_date		= (Date) db.Result[0][0];
					String driver_code 		= (String) db.Result[0][1];
					String driver_name 		= (String) db.Result[0][2];
					String driver_mobile 	= (String) db.Result[0][3];
					String mp_code 			= (String) db.Result[0][4];
					String mp_name 			= (String) db.Result[0][5];
					String purpose_code 	= (String) db.Result[0][6];
					String purpose_name 	= (String) db.Result[0][7];

					Time time_from 	= (Time) db.Result[0][8];
					Time time_to 	= (Time) db.Result[0][9];

					BigDecimal km_from = (BigDecimal) db.Result[0][10];
					BigDecimal km_to = (BigDecimal) db.Result[0][11];

					String requestedby = (String) db.Result[0][12];
					String requested_name = (String) db.Result[0][13];

					String dept_alias = (String) db.Result[0][14];
					String div_alias= (String) db.Result[0][15];
					String proj_charge_code = (String) db.Result[0][16];
					String proj_charge_name = (String) db.Result[0][17];

					String tripcode = (String) db.Result[0][18];
					BigDecimal standard_rate = (BigDecimal) db.Result[0][19];
					BigDecimal min_hrs = (BigDecimal) db.Result[0][20];
					BigDecimal min_km = (BigDecimal) db.Result[0][21];
					BigDecimal excess_hr_rate = (BigDecimal) db.Result[0][22];
					BigDecimal excess_km_rate = (BigDecimal) db.Result[0][23];

					BigDecimal total_hrs = (BigDecimal) db.Result[0][24];
					BigDecimal total_km = (BigDecimal) db.Result[0][25];

					BigDecimal excess_hrs_no = (BigDecimal) db.Result[0][26];
					BigDecimal excess_hrs_pesos = (BigDecimal) db.Result[0][27];
					BigDecimal excess_km_no = (BigDecimal) db.Result[0][28];
					BigDecimal excess_km_pesos = (BigDecimal) db.Result[0][29];

					BigDecimal total_cost = (BigDecimal) db.Result[0][30];
					cancelled_rate = (Boolean) db.Result[0][31];
					fixed_rate = (Boolean) db.Result[0][32];

					selling_unit = (String) db.Result[0][35];
					charge_acct_id = (String) db.Result[0][36];
					charge_acct_name = (String) db.Result[0][37];

					rplf_no = (String) db.Result[0][34];

					is_cancelled = (Boolean) db.Result[0][38];

					if (is_cancelled) {
						this.setTitle("Actual Tripping Result / Tripping Cost [ Cancelled Vehicle Request ]");
					}else{
						this.setTitle("Actual Tripping Result / Tripping Cost");
					}

					String status_rplf = (String) db.Result[0][39];
					meet_time = (String) db.Result[0][42];
					departure = (String) db.Result[0][44];
					div_code = (String) db.Result[0][45];
					dept_code = (String) db.Result[0][46];
					co_id = (String) db.Result[0][47];
					busunit_id = (String) db.Result[0][48];
					BigDecimal toll_fee = (BigDecimal) db.Result[0][49];

					dteDateTripping.setDate(trip_date);
					lookupDriver.setValue(driver_code);
					txtDriver.setText(driver_name);
					txtMobileNoDriver.setText(driver_mobile);
					lookupMeetingPlace.setValue(mp_code);
					txtMeetingPlace.setText(mp_name);
					lookupTrpPurpose.setValue(purpose_code);
					txtTrpPurpose.setText(purpose_name);

					getSpnrFrom().setValue(time_from);

					if (!(time_to == null)) {
						getSpnrTo().setValue(time_to);	
					}

					lookupRequested.setValue(requestedby);
					txtRequested.setText(requested_name);
					txtRequested.setCaretPosition(0);

					lookupPrjCharged.setValue(proj_charge_code);
					txtPrjCharged.setText(proj_charge_name);

					txtDiv.setText(div_alias);
					txtGrp.setText(dept_alias);

					txtKMFrom.setValue(km_from);
					txtKMTo.setValue(km_to);

					System.out.println("************TOTAL HRS" + total_hrs);

					pnlTrip.setTrippingDetails(setDetails(tripcode, standard_rate, min_hrs, min_km, excess_hr_rate, excess_km_rate, total_hrs, total_km, excess_hrs_no, excess_hrs_pesos, excess_km_no, excess_km_pesos,cancelled_rate,driver_code,rplf_no,status_rplf,toll_fee,fixed_rate,total_cost));
					rowListSalesGrp.setModel(new DefaultListModel());
					loadGroupSales(getModelListSalesGrp(), rowListSalesGrp, lookupTicket.getValue());
					rowListSellingUnit.setModel(new DefaultListModel());
					loadSellingUnit(getModelListSellingUnit(), rowListSellingUnit,  lookupTicket.getValue());


					totalgrp  = new BigDecimal("0.00");
					totalselling  = new BigDecimal("0.00");
				}
			}
		}
	}

	public static Date dateFormat(String time){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("hh:mm:ss a");
		try {
			date = (Date)formatter.parse(time);
		} catch (ParseException e) {
		} 

		return date;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnNew)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			NewProcess();
		}

		if (e.getSource().equals(btnEdit)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);


			if (lookupTicket.getValue() == null || lookupTicket.getText().equals("") || lookupTicket.getText()== null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select first ticket no.", "Edit", JOptionPane.WARNING_MESSAGE);
				return;
			}

			editProcess();
		}

		if (e.getSource().equals(btnAddRow)) {

			GroupAddRow();
		}

		if (e.getSource().equals(btnRemoveRow)) {

			int[] selectedRows = tblListSalesGrp.getSelectedRows();


			if (selectedRows.length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row to remove.", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (int x = selectedRows.length - 1; x > -1; x--) {
				int row = selectedRows[x];

				modelListSalesGrp.removeRow(row);
				((DefaultListModel) rowListSalesGrp.getModel()).removeElementAt(row);
			}

			updateCommit(scrollGrpAmountTotal, tblListSalesGrp, rowListSalesGrp);
			computeTotal(getModelListSalesGrp(), modelGrpAmountTotal);

		}

		if (e.getSource().equals(btnAddRowSelling)) {
			SellingUnit();
		}

		if (e.getSource().equals(btnRemoveRowSelling)) {

			int[] selectedRows = tblListSellingUnit.getSelectedRows();


			if (selectedRows.length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row to remove.", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (int x = selectedRows.length - 1; x > -1; x--) {
				int row = selectedRows[x];

				modelListSellingUnit.removeRow(row);
				((DefaultListModel) rowListSellingUnit.getModel()).removeElementAt(row);
			}

			updateCommit(scrollSellingsAmountTotal, tblListSellingUnit, rowListSellingUnit);
			computeTotalSelling(getModelListSellingUnit(), modelSellingAmountTotal);


			rowListSalesGrp.setModel(new DefaultListModel());
			scrollListSalesGrp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblListSalesGrp.getRowCount())));

			rowListSellingUnit.setModel(new DefaultListModel());
			scrollListSellingUnit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblListSellingUnit.getRowCount())));
		}

		if (e.getSource().equals(btnSave)) {
			toSave();

		}

		if (e.getSource().equals(btnCancel)) {

			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields? ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				ClearProcess();
				Formload();
			}
		}
	}

	private void GroupAddRow(){

		String SQL = "select a.division_code as \"Div. Code\",\n" + 
				"a.dept_code AS \"Dept. Code\",\n" + 
				"a.dept_name AS \"Dept. Name\"\n" + 
				"\n" + 
				"from mf_department  a\n" + 
				"left join mf_division b on a.division_code = b.division_code\n";
				//"where a.division_code = '"+div_code+"'";

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, SQL, false);

		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true); 

		Object[] data = dlg.getReturnDataSet();

		if(data != null){

			String div_code = (String) data[0];
			String dept_code = (String) data[1];
			String dept_name = (String) data[2];

			getModelListSalesGrp().addRow(new Object[]{dept_code,dept_name,null,div_code });
			tblListSalesGrp.packAll();

		}

		updateCommit(scrollGrpAmountTotal,tblListSalesGrp,rowListSalesGrp);
	}

	private void SellingUnit(){
		pgSelect db = new pgSelect();
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, methods.getAgent(), false);

		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true); 

		Object[] data = dlg.getReturnDataSet();

		if(data != null){

			String agent_id = (String) data[0];
			String agent_name = (String) data[1];
			String dept_name = (String) data[6];
			String agent_div_id = (String) data[2];
			String agent_dept_id = (String) data[5];

			getModelListSellingUnit().addRow(new Object[]{dept_name,agent_name,null,agent_id,agent_div_id,agent_dept_id });

			db.select("select get_cash_advances_tripping('"+agent_id+"','"+agent_div_id+"','"+agent_dept_id+"');");


			if (db.isNotNull()) {

				BigDecimal ca_broker = new BigDecimal(0.00);


				ca_broker = (BigDecimal) db.Result[0][0];

				if (ca_broker != null) {
					JOptionPane.showMessageDialog(FncGlobal.homeMDI,"This account have the cash advance amount of  "+ca_broker+" pesos","Selling Unit",JOptionPane.WARNING_MESSAGE);	
				}
			}

			tblListSellingUnit.packAll();

		}
		updateCommit(scrollSellingsAmountTotal,tblListSellingUnit,rowListSellingUnit);




	}
	private void NewProcess(){


		this.setComponentsEnabled(pnlNorth, true);
		this.setComponentsEnabled(pnlTrip.pnlMain, true);

		ClearProcess();
		btnState(false, false, true, true);
		lookupTicket.setLookupSQL(methods.getTicketEntry());
		dteDateTripping.setDate(Tripping_Rate.dateFormat(Tripping_Rate.getDateSQL()));


	}

	public void btnState(Boolean sNew,Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);

	}

	private void Formload(){

		this.setComponentsEnabled(pnlNorth, false);
		this.setComponentsEnabled(Center, false);
		this.setComponentsEnabled(pnlTrip, false);
		btnState(true, true, false, false);
		lblTripTicket.setEnabled(true);
		lookupTicket.setEnabled(true);
		lookupTicket.setEditable(true);

	}


	private String getDetails(String ticketNo){
		return "SELECT * FROM sp_generate_ticket_cost('"+ticketNo+"')";
	}

	public Object[] setDetails(String tripcode,BigDecimal standard_rate ,
			BigDecimal min_hrs,
			BigDecimal min_km ,
			BigDecimal excess_hr_rate ,
			BigDecimal excess_km_rate,

			BigDecimal total_hrs ,
			BigDecimal total_km ,

			BigDecimal excess_hrs_no,
			BigDecimal excess_hrs_pesos ,
			BigDecimal excess_km_no,
			BigDecimal excess_km_pesos,
			Boolean is_cancelled,
			String driver_code,
			String rplf_no,
			String rplf_no_status,
			BigDecimal tollfee,
			Boolean fixed_rate,
			BigDecimal total_cost
			) {
		//	System.out.printf("1 : %s\n", policy);
		//	System.out.printf("2 : %s\n", proguide);
		//	System.out.printf("3 : %s\n", internal);
		//	System.out.printf("4 : %s\n", defterms);
		//	System.out.printf("5 : %s\n", effectdata);
		//	System.out.printf("6 : %s\n", approval);

		return new Object[]{
				tripcode, // 00
				standard_rate ,// 01
				min_hrs,// 02
				min_km ,// 03
				excess_hr_rate ,// 04
				excess_km_rate,// 05
				total_hrs ,// 06
				total_km ,// 07
				excess_hrs_no,// 08
				excess_hrs_pesos ,// 09
				excess_km_no,// 10
				excess_km_pesos,// 11
				is_cancelled,// 12
				driver_code,// 13
				rplf_no,// 14
				rplf_no_status,// 15
				tollfee, // 16
				fixed_rate, // 17
				total_cost // 18

		}; // 
	}

	@Override
	public void stateChanged(ChangeEvent e) {

		pgSelect db = new pgSelect();

		if (e.getSource().equals(getSpnrTo())) {
			String timeFrom = new SimpleDateFormat("hh:mm:ss aa").format(getSpnrFrom().getValue());
			String timeTo = new SimpleDateFormat("hh:mm:ss aa").format(getSpnrTo().getValue());

			db.select("SELECT (DATE_PART('hour', '"+timeTo+"'::time - '"+timeFrom+"'::time) * 60 +  DATE_PART('minute', '"+timeTo+"'::time - '"+timeFrom+"'::time)) / 60;");

			System.out.println("***********" + db.Result[0][0]);

			pnlTrip.setTotalHrs(String.valueOf(db.Result[0][0]));

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource().equals(txtKMTo)) {

			toChangeKM(is_cancelled);
		}

		if (e.getSource().equals(txtKMFrom)) {

			toChangeKM(is_cancelled);
		}

	}

	public void toChangeKM(Boolean is_cancelled){

		BigDecimal totalKM = new BigDecimal(0.00);

		Integer km_to = Integer.valueOf((String) (txtKMTo.getText().equals("") ? "0": txtKMTo.getText().replace(",", ""))); 

		if (km_to > 0) {

			//try {
			//BigDecimal kmFrom = new BigDecimal(FncBigDecimal.format((BigDecimal) txtKMFrom.getValued()));
			BigDecimal kmFrom = (BigDecimal) txtKMFrom.getValued();
			//BigDecimal kmFrom = new BigDecimal(txtKMFrom.getText());
			//BigDecimal kmto = new BigDecimal(FncBigDecimal.format((BigDecimal) txtKMTo.getValued()));
			BigDecimal kmto =(BigDecimal) txtKMTo.getValued();

			totalKM = kmto.subtract(kmFrom);
			//	} catch (java.lang.NumberFormatException e ) {
			//		e.printStackTrace();
			//	}
		}

		pnlTrip.setTotalKM(String.valueOf(totalKM),is_cancelled);
	}

	public void getTotalCost(Object [] data){
		c_total_cost = (BigDecimal)data[0];
	}
	public void getData(Object [] data){

		c_total_hrs = data[0].toString();
		c_total_km =  data[1].toString();
		//c_min_hrs =  (String)data[2];
		//c_min_km = (String) data[3];
		//c_standard_rate = (String)data[4];

		c_hr_excess = (BigDecimal)data[5];
		c_total_excess_hrs = (BigDecimal)data[6];
		c_km_excess = (BigDecimal)data[7];
		c_total_excess_km = (BigDecimal)data[8];
		c_total_cost = (BigDecimal)data[9];
		c_plate_no = data[10].toString();
		c_vehicle = data[11].toString();
		c_trip_rate_code = data[12].toString();
		c_excess_rate_hr = data[15].toString();
		c_excess_rate_km = data[16].toString();
		c_toll_fee = (String) data[17];


	}

	private JPanel displayTableNavigationGroup() {
		btnAddRow = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Plus2-Math-icon.png")));
		btnAddRow.setActionCommand("Add Row");
		btnAddRow.setToolTipText("Add Row");
		btnAddRow.setEnabled(true);
		btnAddRow.addActionListener(this);

		btnRemoveRow = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRow.setActionCommand("Remove Row");
		btnRemoveRow.setToolTipText("Remove Row");
		btnRemoveRow.setEnabled(true);
		btnRemoveRow.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnAddRow);
		pnl.add(btnRemoveRow);

		return pnl;
	}

	private JPanel displayTableNavigationSelling() {
		btnAddRowSelling = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Plus2-Math-icon.png")));
		btnAddRowSelling.setActionCommand("Add Row");
		btnAddRowSelling.setToolTipText("Add Row");
		btnAddRowSelling.setEnabled(true);
		btnAddRowSelling.addActionListener(this);

		btnRemoveRowSelling = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRowSelling.setActionCommand("Remove Row");
		btnRemoveRowSelling.setToolTipText("Remove Row");
		btnRemoveRowSelling.setEnabled(true);
		btnRemoveRowSelling.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnAddRowSelling);
		pnl.add(btnRemoveRowSelling);

		return pnl;
	}

	private void updateCommit(_JScrollPaneTotal scroll,_JTableMain table,JList rowheader) {
		scroll.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(table.getRowCount())));
		table.packAll();

		for (int row = 0; row < table.getRowCount(); row++) {
			((DefaultListModel) rowheader.getModel()).setElementAt(row + 1, row);
		}
	}

	private void computeTotal(DefaultTableModel model,DefaultTableModel modeltotal) {
		totalgrp = new BigDecimal("0.00");


		for (int x = 0; x < model.getRowCount(); x++) {
			BigDecimal totalamount = (BigDecimal) model.getValueAt(x, 2);

			try {
				totalgrp = totalgrp.add(totalamount);
			} catch (Exception e1) { }
		}
		String	total  = pnlTrip.txtTotal.getText().replace(",", "");
		System.out.println("************xxxxtotalcost" +total);
		BigDecimal totalcost = new BigDecimal(total); 
		System.out.println("************xxxxtotalcost 1" +totalcost);


		modeltotal.setValueAt(totalgrp, 0, 2);
		/*if (totalcost != null) {
			if(!ifCost(totalcost, totalgrp, totalselling)){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Authority to deduct/group charging is not equal to computed tripping cost.", "Authority to deduct/group charging", JOptionPane.INFORMATION_MESSAGE);
				//return;
			}
		}*/
	}

	private void computeTotalSelling(DefaultTableModel model,DefaultTableModel modeltotal) {
		totalselling = new BigDecimal("0.00");

		for (int x = 0; x < model.getRowCount(); x++) {
			BigDecimal totalamount = (BigDecimal) model.getValueAt(x, 2);

			try {
				totalselling = totalselling.add(totalamount);
			} catch (Exception e1) { }
		}


		String	total  = pnlTrip.txtTotal.getText().replace(",", "");
		BigDecimal totalcost = new BigDecimal(total); 
		System.out.println("************xxxxtotalcost" +totalcost);

		modeltotal.setValueAt(totalselling, 0, 2);

		/*if (!(totalcost ==null)) {
			if(!ifCost(totalcost, totalgrp, totalselling)){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Authority to deduct/group charging is not equal to computed tripping cost.", "Authority to deduct/group charging", JOptionPane.INFORMATION_MESSAGE);
				//return;
			}
		}*/
	}


	private static Boolean ifCost(BigDecimal totalcost, BigDecimal amountgrp, BigDecimal amountselling){
		pgSelect db = new pgSelect();

		String SQL = "SELECT (ROUND("+amountgrp+",2) + ROUND("+amountselling+",2)) = "+totalcost+"" ;
		db.select(SQL);

		System.out.println("***** pasuk" +SQL);
		return (Boolean) db.Result[0][0];

	}

	private  Boolean ifDeduc( BigDecimal amountgrp, BigDecimal amountselling){
		pgSelect db = new pgSelect();

		String SQL = "SELECT ("+amountgrp+" + "+amountselling+") = 0" ;
		db.select(SQL);

		System.out.println("***** pasuk" +SQL);
		return (Boolean) db.Result[0][0];

	}

	private Boolean checkTrippingToday(String driver_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_tripticket_header WHERE driver_code = '"+driver_id+"' AND actual_tripping_date::DATE = CURRENT_DATE)";

		db.select(SQL);

		return (Boolean) db.getResult()[0][0];
	}

	private void toSave(){

		ArrayList<String> listAgentID = new ArrayList<String>();
		ArrayList<BigDecimal> listAgentAmount = new ArrayList<BigDecimal>();
		ArrayList<String> listAgent_Div_ID = new ArrayList<String>();
		ArrayList<String> listAgent_Dept_ID = new ArrayList<String>();

		ArrayList<BigDecimal> listGrpAmount = new ArrayList<BigDecimal>();
		ArrayList<String> listGrp_Div_ID = new ArrayList<String>();
		ArrayList<String> listGrp_Dept_ID = new ArrayList<String>();


		pgSelect db = new pgSelect();
		getData(pnlTrip.getData());

		if (lookupTrpPurpose.getValue() == null || txtTrpPurpose.getText().equals("") || txtTrpPurpose.getText()== null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please enter project to be charged.", "Save", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (lookupPrjCharged.getValue() == null || txtPrjCharged.getText().equals("") || txtPrjCharged.getText()== null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please enter vehicle request purpose.", "Save", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (ifDeduc(totalgrp, totalselling)) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please setup authority to deduct/group charging.", "Save", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if(!ifCost(c_total_cost, totalgrp, totalselling)){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Authority to deduct/group charging is not equal to computed tripping cost.", "Authority to deduct/group charging", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		/*if(checkTrippingToday(lookupDriver.getValue())){
			if(JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "This driver has already a tripping for today\n Do you want to proceed?", "Proceed", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){*/
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					timeFrom = new SimpleDateFormat("hh:mm:ss aa").format(getSpnrFrom().getValue());
					timeTo = new SimpleDateFormat("hh:mm:ss aa").format(getSpnrTo().getValue());

					String save = "SELECT sp_update_trip_ticket(\n" + 
							"'"+lookupDriver.getValue()+"'    ,---p_driver_code character varying,\n" + 
							"'"+txtMobileNoDriver.getText()+"'    ,---p_driver_cellno character varying,\n" + 
							"'"+dteDateTripping.getTimestamp()+"'    ,---p_actual_tripping_date timestamp without time zone,\n" + 
							"'"+lookupMeetingPlace.getValue()+"'    ,---p_meet_place character varying,\n" + 
							"'"+lookupTrpPurpose.getValue()+"'    ,---p_purpose_code character varying,\n" + 
							"'"+txtTrpPurpose.getText()+"'    ,---p_purpose character varying,\n" + 
							"'"+lookupRequested.getValue()+"'    ,---p_requested_by character varying,\n" + 
							"null    ,---p_requested_by_cellno character varying,\n" + 
							"'"+timeFrom+"'    ,---p_time_from time without time zone,\n" + 
							"'"+timeTo+"'    ,---p_time_to time without time zone,\n" + 
							"'"+txtKMFrom.getValue()+"'    ,---p_km_from integer,\n" + 
							"'"+txtKMTo.getValue()+"'    ,---p_km_to integer,\n" + 
							"'"+lookupPrjCharged.getValue()+"'    ,---p_charged_to_projcode character varying,\n" + 
							"NULLIF(NULLIF('"+c_total_hrs+"',''),'0.00')::numeric    ,---p_total_hrs numeric,\n" + 
							"NULLIF('"+c_total_km+"','0.00')::numeric    ,---p_total_km numeric,\n" + 
							"NULLIF('"+c_hr_excess+"','null')::numeric    ,---p_excess_hrs_no numeric,\n" + 
							"NULLIF('"+c_total_excess_hrs+"','null')::numeric    ,---p_excess_hrs_pesos numeric,\n" + 
							"NULLIF('"+c_km_excess+"','null')::numeric    ,---p_excess_km_no numeric,\n" + 
							"NULLIF('"+c_total_excess_km+"','null')::numeric    ,---p_excess_km_pesos numeric,\n" + 
							"'"+c_total_cost+"'    ,---p_total_cost numeric,\n" + 
							"'"+c_trip_rate_code+"'    ,---p_triprate_code character varying,\n" + 
							"'"+txtMeetingPlace.getText()+"'    ,---p_actual_meet_place character varying,\n" + 
							"'"+meet_time+"'    ,---p_meet_time time without time zone,\n" + 
							"'"+departure+"'    ,---p_departure time without time zone,\n" + 
							"null    ,---p_vehic_assigned_by character varying,\n" + 
							"'"+c_vehicle+"'    ,---p_vehicle_make character varying,\n" + 
							"'"+c_plate_no+"'    ,---p_plate_no character varying,\n" + 
							"null    ,---p_network_officer character varying,\n" + 
							"'"+charge_acct_id+"'    ,---p_charged_to_acct_id character varying,\n" + 
							"'"+co_id+"'    ,---p_co_id character varying,\n" + 
							"'"+busunit_id+"'    ,---p_busunit_id character varying,\n" + 
							"'"+UserInfo.EmployeeCode+"'    ,---p_computed_by character varying,\n" + 
							"'"+UserInfo.EmployeeCode+"'    ,---p_update_by character varying,\n" + 
							"now()::timestamp    ,---p_update_date timestamp without time zone,\n" +
							"'"+c_toll_fee+"'::numeric    ,---p_toll_fee numeric,\n" +
							"'"+lookupTicket.getValue()+"')---p_ticket_no character varying);";


					FncSystem.out("QUERY", save);
					db.select(save);

					for (int i = 0; i < getModelListSellingUnit().getRowCount(); i++) {

						String agent_id = (String) getModelListSellingUnit().getValueAt(i, 3);
						BigDecimal amount = (BigDecimal) getModelListSellingUnit().getValueAt(i, 2);
						String agent_div_id = (String) getModelListSellingUnit().getValueAt(i, 4);
						String agent_dept_id = (String) getModelListSellingUnit().getValueAt(i, 5);

						listAgentID.add(String.format("'%s'", agent_id));
						listAgentAmount.add(amount);
						listAgent_Div_ID.add(String.format("'%s'", agent_div_id));
						listAgent_Dept_ID.add(String.format("'%s'", agent_dept_id));

					}

					String p_agent_id = listAgentID.toString().replaceAll("\\[|\\]", "");
					String p_amount = listAgentAmount.toString().replaceAll("\\[|\\]", "");
					String p_agent_div_id = listAgent_Div_ID.toString().replaceAll("\\[|\\]", "");
					String p_agent_dept_id = listAgent_Dept_ID.toString().replaceAll("\\[|\\]", "");

					String SQL = "SELECT sp_update_trip_ticket_selling_unit(\n" + 
							" ARRAY["+ (p_agent_id.equals("'null'") ? "null":p_agent_id) +"]::VARCHAR[],---    p_selling_unit character varying[],\n" + 
							" ARRAY["+ (p_amount.equals("'null'") ? "null":p_amount) +"]::NUMERIC[],---    p_selling_amount numeric[],\n" + 
							" ARRAY["+ (p_agent_div_id.equals("'null'") ? "null":p_agent_div_id) +"]::VARCHAR[],---    p_selling_div character varying[],\n" + 
							" ARRAY["+ (p_agent_dept_id.equals("'null'") ? "null":p_agent_dept_id) +"]::VARCHAR[],---    p_selling_dept character varying[],\n" + 
							"'"+lookupTicket.getValue()+"' ---   p_ticket_no character varying\n" + 
							//"'"+lookupTicket.getValue()+"'  ---  p_ticket_no character varying\n" + 
							"    );";

					FncSystem.out("QUERY", SQL);
					db.select(SQL);

					for (int i = 0; i < getModelListSalesGrp().getRowCount(); i++) {

						BigDecimal grp_amount = (BigDecimal) getModelListSalesGrp().getValueAt(i, 2);
						String grp_div_id = (String) getModelListSalesGrp().getValueAt(i, 3);
						String grp_dept_id = (String) getModelListSalesGrp().getValueAt(i, 0);

						listGrpAmount.add(grp_amount);
						listGrp_Div_ID.add(String.format("'%s'", grp_div_id));
						listGrp_Dept_ID.add(String.format("'%s'", grp_dept_id));


					}

					String p_amount_grp = listGrpAmount.toString().replaceAll("\\[|\\]", "");
					String p_agent_div_id_grp = listGrp_Div_ID.toString().replaceAll("\\[|\\]", "");
					String p_agent_dept_id_grp = listGrp_Dept_ID.toString().replaceAll("\\[|\\]", "");

					String strSQL = "SELECT sp_update_trip_ticket_group(\n" + 
							" ARRAY["+ (p_amount_grp.equals("'null'") ? "null":p_amount_grp) +"]::NUMERIC[],---        p_group_amount numeric[],\n" + 
							" ARRAY["+ (p_agent_div_id_grp.equals("'null'") ? "null":p_agent_div_id_grp) +"]::VARCHAR[],---    p_group_div character varying[],\n" + 
							" ARRAY["+ (p_agent_dept_id_grp.equals("'null'") ? "null":p_agent_dept_id_grp) +"]::VARCHAR[],---    p_group_dept character varying[],\n" + 
							"'"+lookupTicket.getValue()+"' ---   p_ticket_no character varying\n" +
							"    );";
					FncSystem.out("QUERY", strSQL);
					db.select(strSQL);


					JOptionPane.showMessageDialog(FncGlobal.homeMDI,"Finished Saving!","Save",JOptionPane.INFORMATION_MESSAGE);

					ClearProcess();
					Formload();
				}
			/*}
		}else{
			if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				timeFrom = new SimpleDateFormat("hh:mm:ss aa").format(getSpnrFrom().getValue());
				timeTo = new SimpleDateFormat("hh:mm:ss aa").format(getSpnrTo().getValue());

				String save = "SELECT sp_update_trip_ticket(\n" + 
						"'"+lookupDriver.getValue()+"'    ,---p_driver_code character varying,\n" + 
						"'"+txtMobileNoDriver.getText()+"'    ,---p_driver_cellno character varying,\n" + 
						"'"+dteDateTripping.getTimestamp()+"'    ,---p_actual_tripping_date timestamp without time zone,\n" + 
						"'"+lookupMeetingPlace.getValue()+"'    ,---p_meet_place character varying,\n" + 
						"'"+lookupTrpPurpose.getValue()+"'    ,---p_purpose_code character varying,\n" + 
						"'"+txtTrpPurpose.getText()+"'    ,---p_purpose character varying,\n" + 
						"'"+lookupRequested.getValue()+"'    ,---p_requested_by character varying,\n" + 
						"null    ,---p_requested_by_cellno character varying,\n" + 
						"'"+timeFrom+"'    ,---p_time_from time without time zone,\n" + 
						"'"+timeTo+"'    ,---p_time_to time without time zone,\n" + 
						"'"+txtKMFrom.getValue()+"'    ,---p_km_from integer,\n" + 
						"'"+txtKMTo.getValue()+"'    ,---p_km_to integer,\n" + 
						"'"+lookupPrjCharged.getValue()+"'    ,---p_charged_to_projcode character varying,\n" + 
						"NULLIF(NULLIF('"+c_total_hrs+"',''),'0.00')::numeric    ,---p_total_hrs numeric,\n" + 
						"NULLIF('"+c_total_km+"','0.00')::numeric    ,---p_total_km numeric,\n" + 
						"NULLIF('"+c_hr_excess+"','null')::numeric    ,---p_excess_hrs_no numeric,\n" + 
						"NULLIF('"+c_total_excess_hrs+"','null')::numeric    ,---p_excess_hrs_pesos numeric,\n" + 
						"NULLIF('"+c_km_excess+"','null')::numeric    ,---p_excess_km_no numeric,\n" + 
						"NULLIF('"+c_total_excess_km+"','null')::numeric    ,---p_excess_km_pesos numeric,\n" + 
						"'"+c_total_cost+"'    ,---p_total_cost numeric,\n" + 
						"'"+c_trip_rate_code+"'    ,---p_triprate_code character varying,\n" + 
						"'"+txtMeetingPlace.getText()+"'    ,---p_actual_meet_place character varying,\n" + 
						"'"+meet_time+"'    ,---p_meet_time time without time zone,\n" + 
						"'"+departure+"'    ,---p_departure time without time zone,\n" + 
						"null    ,---p_vehic_assigned_by character varying,\n" + 
						"'"+c_vehicle+"'    ,---p_vehicle_make character varying,\n" + 
						"'"+c_plate_no+"'    ,---p_plate_no character varying,\n" + 
						"null    ,---p_network_officer character varying,\n" + 
						"'"+charge_acct_id+"'    ,---p_charged_to_acct_id character varying,\n" + 
						"'"+co_id+"'    ,---p_co_id character varying,\n" + 
						"'"+busunit_id+"'    ,---p_busunit_id character varying,\n" + 
						"'"+UserInfo.EmployeeCode+"'    ,---p_computed_by character varying,\n" + 
						"'"+UserInfo.EmployeeCode+"'    ,---p_update_by character varying,\n" + 
						"now()::timestamp    ,---p_update_date timestamp without time zone,\n" +
						"'"+c_toll_fee+"'::numeric    ,---p_toll_fee numeric,\n" +
						"'"+lookupTicket.getValue()+"')---p_ticket_no character varying);";


				FncSystem.out("QUERY", save);
				db.select(save);

				for (int i = 0; i < getModelListSellingUnit().getRowCount(); i++) {

					String agent_id = (String) getModelListSellingUnit().getValueAt(i, 3);
					BigDecimal amount = (BigDecimal) getModelListSellingUnit().getValueAt(i, 2);
					String agent_div_id = (String) getModelListSellingUnit().getValueAt(i, 4);
					String agent_dept_id = (String) getModelListSellingUnit().getValueAt(i, 5);

					listAgentID.add(String.format("'%s'", agent_id));
					listAgentAmount.add(amount);
					listAgent_Div_ID.add(String.format("'%s'", agent_div_id));
					listAgent_Dept_ID.add(String.format("'%s'", agent_dept_id));

				}

				String p_agent_id = listAgentID.toString().replaceAll("\\[|\\]", "");
				String p_amount = listAgentAmount.toString().replaceAll("\\[|\\]", "");
				String p_agent_div_id = listAgent_Div_ID.toString().replaceAll("\\[|\\]", "");
				String p_agent_dept_id = listAgent_Dept_ID.toString().replaceAll("\\[|\\]", "");

				String SQL = "SELECT sp_update_trip_ticket_selling_unit(\n" + 
						" ARRAY["+ (p_agent_id.equals("'null'") ? "null":p_agent_id) +"]::VARCHAR[],---    p_selling_unit character varying[],\n" + 
						" ARRAY["+ (p_amount.equals("'null'") ? "null":p_amount) +"]::NUMERIC[],---    p_selling_amount numeric[],\n" + 
						" ARRAY["+ (p_agent_div_id.equals("'null'") ? "null":p_agent_div_id) +"]::VARCHAR[],---    p_selling_div character varying[],\n" + 
						" ARRAY["+ (p_agent_dept_id.equals("'null'") ? "null":p_agent_dept_id) +"]::VARCHAR[],---    p_selling_dept character varying[],\n" + 
						"'"+lookupTicket.getValue()+"' ---   p_ticket_no character varying\n" + 
						//"'"+lookupTicket.getValue()+"'  ---  p_ticket_no character varying\n" + 
						"    );";

				FncSystem.out("QUERY", SQL);
				db.select(SQL);

				for (int i = 0; i < getModelListSalesGrp().getRowCount(); i++) {

					BigDecimal grp_amount = (BigDecimal) getModelListSalesGrp().getValueAt(i, 2);
					String grp_div_id = (String) getModelListSalesGrp().getValueAt(i, 3);
					String grp_dept_id = (String) getModelListSalesGrp().getValueAt(i, 0);

					listGrpAmount.add(grp_amount);
					listGrp_Div_ID.add(String.format("'%s'", grp_div_id));
					listGrp_Dept_ID.add(String.format("'%s'", grp_dept_id));


				}

				String p_amount_grp = listGrpAmount.toString().replaceAll("\\[|\\]", "");
				String p_agent_div_id_grp = listGrp_Div_ID.toString().replaceAll("\\[|\\]", "");
				String p_agent_dept_id_grp = listGrp_Dept_ID.toString().replaceAll("\\[|\\]", "");

				String strSQL = "SELECT sp_update_trip_ticket_group(\n" + 
						" ARRAY["+ (p_amount_grp.equals("'null'") ? "null":p_amount_grp) +"]::NUMERIC[],---        p_group_amount numeric[],\n" + 
						" ARRAY["+ (p_agent_div_id_grp.equals("'null'") ? "null":p_agent_div_id_grp) +"]::VARCHAR[],---    p_group_div character varying[],\n" + 
						" ARRAY["+ (p_agent_dept_id_grp.equals("'null'") ? "null":p_agent_dept_id_grp) +"]::VARCHAR[],---    p_group_dept character varying[],\n" + 
						"'"+lookupTicket.getValue()+"' ---   p_ticket_no character varying\n" +
						"    );";
				FncSystem.out("QUERY", strSQL);
				db.select(strSQL);


				JOptionPane.showMessageDialog(FncGlobal.homeMDI,"Finished Saving!","Save",JOptionPane.INFORMATION_MESSAGE);

				ClearProcess();
				Formload();
			}
		}*/


	}


	private void ClearProcess(){

		this.setComponentsClear(pnlNorth, "");

		this.setComponentsClear(Center, "");
		this.setComponentsClear(pnlTrip, "");

		pnlTrip.getVechileClear();
		pnlTrip.lblFixed.setVisible(false);



		getModelListSalesGrp().clear();
		updateCommit(scrollGrpAmountTotal,tblListSalesGrp,rowListSalesGrp);
		//computeTotal(getModelListSalesGrp(),modelGrpAmountTotal);
		rowListSellingUnit.setModel(new DefaultListModel());
		modelGrpAmountTotal.setValueAt(0.00, 0, 2);
		tblListSalesGrp.packAll();	

		getModelListSellingUnit().clear();
		updateCommit(scrollSellingsAmountTotal,tblListSellingUnit,rowListSellingUnit);
		//computeTotalSelling(getModelListSellingUnit(),modelSellingAmountTotal);
		rowListSalesGrp.setModel(new DefaultListModel());
		modelSellingAmountTotal.setValueAt(0.00, 0, 2);

		lookupTicket.setLookupSQL(methods.getTicketEntryEdit());

		//scrollListSalesGrp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblListSalesGrp.getRowCount())));


		//scrollListSellingUnit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblListSellingUnit.getRowCount())));

	}

	private void loadGroupSales(model_SalesGroups model, JList rowHeader, String ticket_no){
		pgSelect db = new pgSelect();
		model.clear();

		db.select("select dept_code,get_department_name_new(dept_code), amount,div_code from rf_tripticket_chargedgrp where ticket_no = '"+ticket_no+"'");

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();

			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			updateCommit(scrollGrpAmountTotal,tblListSalesGrp,rowListSalesGrp);
			computeTotal(getModelListSalesGrp(),modelGrpAmountTotal);
			tblListSalesGrp.packAll();
		}
	}

	private void loadSellingUnit(model_SellingUnit model, JList rowHeader, String ticket_no){
		pgSelect db = new pgSelect();
		model.clear();

		db.select("select get_department_name_new(dept_code),get_client_name(entity_id), amount,entity_id,div_code,dept_code from rf_tripticket_atd  where ticket_no = '"+ticket_no+"'");

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();

			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			updateCommit(scrollSellingsAmountTotal,tblListSellingUnit,rowListSellingUnit);
			computeTotalSelling(getModelListSellingUnit(),modelSellingAmountTotal);
			tblListSellingUnit.packAll();
		}
	}


	private void editProcess(){

		lookupTicket.setEditable(false);
		this.setComponentsEnabled(pnlNorth, true);
		this.setComponentsEnabled(pnlTrip.pnlMain, true);
		btnState(false, false, true, true);
	}


	public void setDivideTotal(BigDecimal total){

		pgSelect db = new pgSelect();

		if (!(getModelListSellingUnit().getRowCount() == 0)) {
			db.select("SELECT "+total+" / "+getModelListSellingUnit().getRowCount()+"");

			BigDecimal divided = (BigDecimal) db.Result[0][0];

			for (int i = 0; i < getModelListSellingUnit().getRowCount(); i++) {

				getModelListSellingUnit().setValueAt(divided, i, 2);

			}

			updateCommit(scrollSellingsAmountTotal,tblListSellingUnit,rowListSellingUnit);
			computeTotalSelling(getModelListSellingUnit(),modelSellingAmountTotal);
			tblListSellingUnit.packAll();	
		}


		if (!(getModelListSalesGrp().getRowCount() == 0)) {
			db.select("SELECT "+total+" / "+getModelListSalesGrp().getRowCount()+"");

			BigDecimal divided = (BigDecimal) db.Result[0][0];

			for (int i = 0; i < getModelListSalesGrp().getRowCount(); i++) {

				getModelListSalesGrp().setValueAt(divided, i, 2);

			}

			updateCommit(scrollGrpAmountTotal,tblListSalesGrp,rowListSalesGrp);
			computeTotal(getModelListSalesGrp(),modelGrpAmountTotal);
			tblListSalesGrp.packAll();	
		}

	}

	public model_SellingUnit getModelListSellingUnit() {
		return modelListSellingUnit;
	}

	public void setModelListSellingUnit(model_SellingUnit modelListSellingUnit) {
		this.modelListSellingUnit = modelListSellingUnit;
	}

	public model_SalesGroups getModelListSalesGrp() {
		return modelListSalesGrp;
	}

	public void setModelListSalesGrp(model_SalesGroups modelListSalesGrp) {
		this.modelListSalesGrp = modelListSalesGrp;
	}

	public JSpinner getSpnrTo() {
		return spnrTo;
	}

	public void setSpnrTo(JSpinner spnrTo) {
		this.spnrTo = spnrTo;
	}

	public JSpinner getSpnrFrom() {
		return spnrFrom;
	}

	public void setSpnrFrom(JSpinner spnrFrom) {
		this.spnrFrom = spnrFrom;
	}

	public String getAgent(String div_id){

		String SQL = "";

		SQL = "SELECT TRIM(a.entity_id)::VARCHAR as \"Entity ID\" , "
				+"TRIM(a.entity_name) AS \"Agent Name\" , "
				+"TRIM(c.division_code)::VARCHAR AS \"Div. Code\", "
				+"TRIM(c.division_name) AS \"Div Name\" ,\n" + 
				"TRIM(c.division_alias) AS \"Div. Alias\", "
				+"TRIM(b.dept_id)::VARCHAR as \"Dept ID\", "
				+"TRIM(d.dept_name)  As \"Dept. Name\",\n" + 
				"_get_client_mobileno(a.entity_id) As \"Mobile\"\n" + 
				"\n" + 
				"FROM rf_entity a\n" + 
				"\n" + 
				"JOIN (select * from mf_sellingagent_info where status_id = 'A' and salespos_id not in ('004','009')) b on b.agent_id = a.entity_id\n" + 
				"LEFT  JOIN mf_division c on c.division_code = b.sales_div_id\n" + 
				"LEFT  JOIN mf_department d on b.dept_id = d.dept_code\n" + 
				"where bdo_id is not null\n" + 
				"and c.division_code = '"+div_id+"'\n" + 
				"ORDER BY TRIM(a.entity_name)\n" + 
				"" ;

		return SQL;

	}

}
