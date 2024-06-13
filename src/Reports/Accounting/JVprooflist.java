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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

import com.toedter.calendar.JTextFieldDateEditor;
import components._JInternalFrame;
import components._JTagLabel;

public class JVprooflist extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "JV Prooflist";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlNorth_comp;
	private JPanel pnlJVDtl_2a;
	private JPanel pnlJVDtl_2b;
	private JPanel pnlJV;
	private JPanel pnlCenterJV;
	private JPanel pnlCenter_a;
	private JPanel pnlCenter_2;
	private JPanel pnlSouth;
	private JPanel pnlCenterCreated_main;
	private JPanel pnlPeriod;
	private JPanel pnlPeriodFr;
	private JPanel pnlPeriodFr_a;
	private JPanel pnlPeriodFr_b;
	private JPanel pnlPeriodTo;
	private JPanel pnlPeriodTo_a;
	private JPanel pnlPeriodTo_b;
	private JPanel pnlPeriodFr_c2;
	private JPanel pnlPeriod_2;
	private JPanel pnlPeriod_Fr2;
	private JPanel pnlPeriodFr_a2;
	private JPanel pnlPeriodFr_b2;
	private JPanel pnlPeriodTo2;
	private JPanel pnlPeriodTo_a2;
	private JPanel pnlPeriodTo_b2;
	private JPanel pnlPeriodFr_c1;
	private JPanel pnlPeriodFr_c;
	private JPanel pnlPeriodFr_c_main;	
	
	private JLabel lblCompany;
	private JLabel lblTranType;
	private JLabel lblJVFrom;
	private JLabel lblJVTo;
	private JLabel lblFiscalYr;
	private JLabel lblJVstatus;
	private JLabel lblStatus;	
	private JLabel lbl_date_fr;
	private JLabel lblDate_to;
	private JLabel lbl_period_fr2;
	private JLabel lblPeriod_to2;
	private JLabel lblYear;
	
	private _JTagLabel tagTranType;
	private _JTagLabel tagYear;
	private _JTagLabel tagStatus;
	private _JTagLabel tagPreparedBy;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupTranType;
	private _JLookup lookupJVyear;
	private _JLookup lookupStatus;
	private _JLookup lookupPreparedby;
	private _JLookup lookupJV_from;
	private _JLookup lookupJV_to;
	
	private JTextField txtCompany;
	
	private JButton btnPreview;
	private JButton btnCancel;
	
	private JRadioButton rbtnDate;
	private JRadioButton rbtnPeriod;	
	
	private _JDateChooser dte_from;
	private _JDateChooser dte_to;	
	
	private JComboBox cmbPeriodFr;	
	private JComboBox cmbPeriodTo;	
	private JComboBox cmbYear;

	String company;
	private String company_logo;		

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	static SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy");
	
	private String tran_type_id 		= "";
	private String tran_type_name		= "";
	private String jv_year 			= "";
	private String prepared_by_id	= "";
	private String prepared_by_name	= "";
	private String status_id			= "";
	private String status_name		= "";
	private String co_id = "";

	private static JButton btnexport;
	
	private static Integer period_from = null;
	private static Integer period_to = null;	
	static java.util.Date date_from = null;
	static java.util.Date date_to = null;
	
	public JVprooflist() {
		super(title, false, true, false, true);
		initGUI();
	}

	public JVprooflist(String title) {
		super(title);
		initGUI();
	}

	public JVprooflist(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(682, 441));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(680, 426));
			
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(670, 219));

				{
					pnlNorth_comp = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorth_comp, BorderLayout.NORTH);
					pnlNorth_comp.setPreferredSize(new java.awt.Dimension(611, 57));
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
										lookupTranType.setLookupSQL(getTranType());	
										lookupJVyear.setLookupSQL(getJVyear());	
										lookupPreparedby.setLookupSQL(getPreparedBy());	
										lookupStatus.setLookupSQL(getStatus());	
										
										lookupJV_from.setLookupSQL(getJV_no());			
										lookupJV_to.setLookupSQL(getJV_no());			
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
					pnlCenterJV = new JPanel(new GridLayout(1,1,5, 5));
					pnlNorth.add(pnlCenterJV, BorderLayout.CENTER);
					pnlCenterJV.setPreferredSize(new java.awt.Dimension(611, 58));
					pnlCenterJV.setBorder(components.JTBorderFactory.createTitleBorder("JV No."));
					
					{
						pnlJV = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlCenterJV.add(pnlJV, BorderLayout.WEST);					

						{
							lblJVFrom = new JLabel("JV From   ", JLabel.TRAILING);
							pnlJV.add(lblJVFrom, BorderLayout.CENTER);
							lblJVFrom.setEnabled(false);							
						}
						{
							lookupJV_from = new _JLookup(null, "JV No.", 2, 2);
							pnlJV.add(lookupJV_from);
							lookupJV_from.setBounds(20, 27, 20, 25);
							lookupJV_from.setReturnColumn(0);
							lookupJV_from.setLookupSQL(getJV_no());	
							lookupJV_from.setEnabled(false);
							lookupJV_from.setPreferredSize(new java.awt.Dimension(157, 22));
							lookupJV_from.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){	

										/*refresh_fields();							

										jv_no = (String) data[0];	
										lookupJV.setValue(jv_no);

										setJV_header(jv_no);
										displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, jv_no );								

										mnidelete.setEnabled(false);
										mniaddrow.setEnabled(false);*/

									}
								}
							});
						}	
						
						{	

							lblJVTo = new JLabel("JV To   ", JLabel.TRAILING);
							pnlJV.add(lblJVTo);
							lblJVTo.setEnabled(false);	
						}					
						{
							lookupJV_to = new _JLookup(null, "JV No.", 2, 2);
							pnlJV.add(lookupJV_to);
							lookupJV_to.setBounds(20, 27, 20, 25);
							lookupJV_to.setReturnColumn(0);
							lookupJV_to.setEnabled(false);
							lookupJV_to.setLookupSQL(getJV_no());							
							lookupJV_to.setPreferredSize(new java.awt.Dimension(157, 22));
							lookupJV_to.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){	

										/*refresh_fields();							

										jv_no = (String) data[0];	
										lookupJV.setValue(jv_no);

										setJV_header(jv_no);
										displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, jv_no );								

										mnidelete.setEnabled(false);
										mniaddrow.setEnabled(false);*/

									}
								}
							});
						}	
					}
				}
				
				
				pnlCenterCreated_main = new JPanel(new GridLayout(2,1,5, 5));
				pnlNorth.add(pnlCenterCreated_main, BorderLayout.SOUTH);
				pnlCenterCreated_main.setPreferredSize(new java.awt.Dimension(670, 89));
				pnlCenterCreated_main.setBorder(components.JTBorderFactory.createTitleBorder("Date / Period"));
								
				{
					pnlPeriod = new JPanel(new BorderLayout(5,5));
					pnlCenterCreated_main.add(pnlPeriod, BorderLayout.CENTER);	
					pnlPeriod.setPreferredSize(new java.awt.Dimension(658, 24));

					{
						pnlPeriodFr = new JPanel(new BorderLayout(5,5));
						pnlPeriod.add(pnlPeriodFr, BorderLayout.WEST);	
						pnlPeriodFr.setPreferredSize(new java.awt.Dimension(263, 23));
						pnlPeriodFr.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						{
							pnlPeriodFr_a = new JPanel(new GridLayout(1, 1, 0, 0));
							pnlPeriodFr.add(pnlPeriodFr_a, BorderLayout.WEST);	
							pnlPeriodFr_a.setPreferredSize(new java.awt.Dimension(146, 23));
							pnlPeriodFr_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));		

							{
								rbtnDate = new JRadioButton();
								pnlPeriodFr_a.add(rbtnDate);
								rbtnDate.setText("    Date");
								rbtnDate.setHorizontalTextPosition(2);
								//rbtnDate.setBounds(277, 12, 77, 18);
								rbtnDate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								rbtnDate.setSelected(true);
								rbtnDate.setEnabled(true);
								rbtnDate.setPreferredSize(new java.awt.Dimension(79, 23));
								rbtnDate.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae){	
										
									if (rbtnDate.isSelected()==true){
										lbl_date_fr.setEnabled(true);	
										dte_from.setEnabled(true);
										lblDate_to.setEnabled(true);
										dte_to.setEnabled(true);
										//lblInclude.setEnabled(true);
										//cmbInclude.setEnabled(true);
										
										rbtnPeriod.setSelected(false);
										lbl_period_fr2.setEnabled(false);	
										cmbPeriodFr.setEnabled(false);
										lblPeriod_to2.setEnabled(false);
										cmbPeriodTo.setEnabled(false);
										lblYear.setEnabled(false);
										cmbYear.setEnabled(false);
									}
									else 
									{									
										lbl_date_fr.setEnabled(false);	
										dte_from.setEnabled(false);
										lblDate_to.setEnabled(false);
										dte_to.setEnabled(false);
										//lblInclude.setEnabled(false);
										//cmbInclude.setEnabled(false);
										
										rbtnPeriod.setSelected(true);
										lbl_period_fr2.setEnabled(true);	
										cmbPeriodFr.setEnabled(true);
										lblPeriod_to2.setEnabled(true);
										cmbPeriodTo.setEnabled(true);
										lblYear.setEnabled(true);
										cmbYear.setEnabled(true);
									}									

									}});					
							}			
							{
								lbl_date_fr = new JLabel("Date From", JLabel.TRAILING);
								pnlPeriodFr_a.add(lbl_date_fr);
								lbl_date_fr.setEnabled(false);	
								lbl_date_fr.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								lbl_date_fr.setPreferredSize(new java.awt.Dimension(66, 23));
							}
						}
						{
							pnlPeriodFr_b = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlPeriodFr.add(pnlPeriodFr_b, BorderLayout.CENTER);	
							pnlPeriodFr_b.setPreferredSize(new java.awt.Dimension(129, 23));
							pnlPeriodFr_b.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));

							{
								dte_from = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlPeriodFr_b.add(dte_from);
								dte_from.setBounds(485, 7, 125, 21);
								dte_from.setDate(null);
								dte_from.setEnabled(false);
								dte_from.setDateFormatString("yyyy-MM-dd");
								((JTextFieldDateEditor)dte_from.getDateEditor()).setEditable(false);
								dte_from.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}	
						}
					}
					{
						pnlPeriodTo = new JPanel(new BorderLayout(0, 0));
						pnlPeriod.add(pnlPeriodTo, BorderLayout.CENTER);	
						pnlPeriodTo.setPreferredSize(new java.awt.Dimension(181, 23));
						pnlPeriodTo.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						pnlPeriodTo_a = new JPanel(new GridLayout(1, 1, 0, 5));
						pnlPeriodTo.add(pnlPeriodTo_a, BorderLayout.WEST);	
						pnlPeriodTo_a.setPreferredSize(new java.awt.Dimension(94, 55));
						pnlPeriodTo_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));		

						{
							lblDate_to = new JLabel("Date To", JLabel.TRAILING);
							pnlPeriodTo_a.add(lblDate_to);
							lblDate_to.setEnabled(false);
							lblDate_to.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							lblDate_to.setPreferredSize(new java.awt.Dimension(67, 55));
						}

						pnlPeriodTo_b = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlPeriodTo.add(pnlPeriodTo_b, BorderLayout.CENTER);	
						pnlPeriodTo_b.setPreferredSize(new java.awt.Dimension(135, 119));
						pnlPeriodTo_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

						{
							dte_to = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlPeriodTo_b.add(dte_to);
							dte_to.setBounds(485, 7, 125, 21);
							dte_to.setDate(null);
							dte_to.setEnabled(false);
							dte_to.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dte_to.getDateEditor()).setEditable(false);
							dte_to.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dte_to.addPropertyChangeListener( new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {
									
									//DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");					
									//String end_date	 = df.format(dte_to.getDate());	
									//String end_date_sub = end_date.substring(5);	
									
								}
							});	
						}
					}
					{
						pnlPeriodFr_c_main = new JPanel(new BorderLayout(5, 5));
						pnlPeriod.add(pnlPeriodFr_c_main, BorderLayout.EAST);	
						pnlPeriodFr_c_main.setPreferredSize(new java.awt.Dimension(158, 23));
						pnlPeriodFr_c_main.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));		
						
						/*
						pnlPeriodFr_c_date = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlPeriodFr_c_main.add(pnlPeriodFr_c_date, BorderLayout.CENTER);	
						pnlPeriodFr_c_date.setPreferredSize(new java.awt.Dimension(82, 23));
						pnlPeriodFr_c_date.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));
						
						{
							lblInclude = new JLabel("Until", JLabel.TRAILING);
							pnlPeriodFr_c_date.add(lblInclude);
							lblInclude.setEnabled(true);
							lblInclude.setPreferredSize(new java.awt.Dimension(67, 55));
						}
						
						pnlPeriodFr_c2_date = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlPeriodFr_c_main.add(pnlPeriodFr_c2_date, BorderLayout.EAST);	
						pnlPeriodFr_c2_date.setPreferredSize(new java.awt.Dimension(96, 23));
						pnlPeriodFr_c2_date.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));
						
						{
							String status2[] = { "(None)", "13th Month","14th Month","15th Month" };
							cmbInclude = new JComboBox(status2);
							pnlPeriodFr_c2_date.add(cmbInclude);
							cmbInclude.setSelectedItem(null);
							cmbInclude.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
							cmbInclude.setBounds(537, 15, 180, 21);
							cmbInclude.setEnabled(true);
							cmbInclude.setSelectedIndex(0);
							cmbInclude.setPreferredSize(new java.awt.Dimension(119, 26));
						}
						*/
					}

				}				
				{
					pnlPeriod_2 = new JPanel(new BorderLayout(5,5));
					pnlCenterCreated_main.add(pnlPeriod_2, BorderLayout.SOUTH);	
					pnlPeriod_2.setPreferredSize(new java.awt.Dimension(514, 23));

					{
						pnlPeriod_Fr2 = new JPanel(new BorderLayout(5,5));
						pnlPeriod_2.add(pnlPeriod_Fr2, BorderLayout.WEST);	
						pnlPeriod_Fr2.setPreferredSize(new java.awt.Dimension(263, 23));
						pnlPeriod_Fr2.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						{
							pnlPeriodFr_a2 = new JPanel(new GridLayout(1, 1, 0, 0));
							pnlPeriod_Fr2.add(pnlPeriodFr_a2, BorderLayout.WEST);	
							pnlPeriodFr_a2.setPreferredSize(new java.awt.Dimension(146, 23));
							pnlPeriodFr_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));		

							{
								rbtnPeriod = new JRadioButton();
								pnlPeriodFr_a2.add(rbtnPeriod);
								rbtnPeriod.setText(" Period");
								rbtnPeriod.setHorizontalTextPosition(2);
								rbtnPeriod.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								rbtnPeriod.setSelected(false);
								rbtnPeriod.setEnabled(true);
								rbtnPeriod.setPreferredSize(new java.awt.Dimension(58, 23));
								rbtnPeriod.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae){	
										
										if (rbtnDate.isSelected()==true){
											rbtnDate.setSelected(false);
											lbl_date_fr.setEnabled(false);	
											dte_from.setEnabled(false);
											lblDate_to.setEnabled(false);
											dte_to.setEnabled(false);
											//lblInclude.setEnabled(false);
											//cmbInclude.setEnabled(false);
											
											lbl_period_fr2.setEnabled(true);	
											cmbPeriodFr.setEnabled(true);
											lblPeriod_to2.setEnabled(true);
											cmbPeriodTo.setEnabled(true);
											lblYear.setEnabled(true);
											cmbYear.setEnabled(true);
										}
										else 
										{
											rbtnDate.setSelected(true);
											lbl_date_fr.setEnabled(true);	
											dte_from.setEnabled(true);
											lblDate_to.setEnabled(true);
											dte_to.setEnabled(true);
											//lblInclude.setEnabled(true);
											//cmbInclude.setEnabled(true);
											
											lbl_period_fr2.setEnabled(false);	
											cmbPeriodFr.setEnabled(false);
											lblPeriod_to2.setEnabled(false);
											cmbPeriodTo.setEnabled(false);
											lblYear.setEnabled(false);
											cmbYear.setEnabled(false);
										}
									}});					
							}	
							{
								lbl_period_fr2 = new JLabel("Period From", JLabel.TRAILING);
								pnlPeriodFr_a2.add(lbl_period_fr2);
								lbl_period_fr2.setEnabled(false);	
								lbl_period_fr2.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								lbl_period_fr2.setPreferredSize(new java.awt.Dimension(84, 55));
							}
						}
						{
							pnlPeriodFr_b2 = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlPeriod_Fr2.add(pnlPeriodFr_b2, BorderLayout.CENTER);	
							pnlPeriodFr_b2.setPreferredSize(new java.awt.Dimension(135, 119));
							pnlPeriodFr_b2.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));

							{
								String status2[] = { "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15" };
								cmbPeriodFr = new JComboBox(status2);
								pnlPeriodFr_b2.add(cmbPeriodFr);
								cmbPeriodFr.setSelectedItem(null);
								cmbPeriodFr.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
								cmbPeriodFr.setBounds(537, 15, 180, 21);
								cmbPeriodFr.setEnabled(false);
								cmbPeriodFr.setSelectedIndex(0);
								cmbPeriodFr.setPreferredSize(new java.awt.Dimension(95, 23));
								cmbPeriodFr.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent evt) 
									{
										
									}
								});		
							}	
						}
					}
					{
						pnlPeriodTo2 = new JPanel(new BorderLayout(0, 0));
						pnlPeriod_2.add(pnlPeriodTo2, BorderLayout.CENTER);	
						pnlPeriodTo2.setPreferredSize(new java.awt.Dimension(420, 23));
						pnlPeriodTo2.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						pnlPeriodTo_a2 = new JPanel(new GridLayout(1, 1, 0, 5));
						pnlPeriodTo2.add(pnlPeriodTo_a2, BorderLayout.WEST);	
						pnlPeriodTo_a2.setPreferredSize(new java.awt.Dimension(95, 23));
						pnlPeriodTo_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));		

						{
							lblPeriod_to2 = new JLabel("Period To", JLabel.TRAILING);
							pnlPeriodTo_a2.add(lblPeriod_to2);
							lblPeriod_to2.setEnabled(false);
							lblPeriod_to2.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							lblPeriod_to2.setPreferredSize(new java.awt.Dimension(89, 23));
						}

						pnlPeriodTo_b2 = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlPeriodTo2.add(pnlPeriodTo_b2, BorderLayout.CENTER);	
						pnlPeriodTo_b2.setPreferredSize(new java.awt.Dimension(135, 119));
						pnlPeriodTo_b2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

						{
							String status2[] = { "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15" };
							cmbPeriodTo = new JComboBox(status2);
							pnlPeriodTo_b2.add(cmbPeriodTo);
							cmbPeriodTo.setSelectedItem(null);
							cmbPeriodTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
							cmbPeriodTo.setBounds(537, 15, 180, 21);
							cmbPeriodTo.setEnabled(false);
							cmbPeriodTo.setSelectedIndex(0);
							cmbPeriodTo.setPreferredSize(new java.awt.Dimension(95, 23));
							cmbPeriodTo.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent evt) 
								{
									}
							});		
						}	
					}
					{
						pnlPeriodFr_c = new JPanel(new BorderLayout(5, 5));
						pnlPeriod_2.add(pnlPeriodFr_c, BorderLayout.EAST);	
						pnlPeriodFr_c.setPreferredSize(new java.awt.Dimension(158, 23));
						pnlPeriodFr_c.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						pnlPeriodFr_c1 = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlPeriodFr_c.add(pnlPeriodFr_c1, BorderLayout.CENTER);	
						pnlPeriodFr_c1.setPreferredSize(new java.awt.Dimension(82, 23));
						pnlPeriodFr_c1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						{
							lblYear = new JLabel("Year", JLabel.TRAILING);
							pnlPeriodFr_c1.add(lblYear);
							lblYear.setEnabled(true);
							lblYear.setPreferredSize(new java.awt.Dimension(67, 55));
						}

						pnlPeriodFr_c2 = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlPeriodFr_c.add(pnlPeriodFr_c2, BorderLayout.EAST);	
						pnlPeriodFr_c2.setPreferredSize(new java.awt.Dimension(95, 23));
						pnlPeriodFr_c2.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						{
							String status2[] = {"2023","2022", "2021","2020","2019","2018", "2017","2016"};
							cmbYear = new JComboBox(status2);
							pnlPeriodFr_c2.add(cmbYear);
							cmbYear.setSelectedItem(null);
							cmbYear.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
							cmbYear.setBounds(537, 15, 180, 21);
							cmbYear.setEnabled(false);
							cmbYear.setSelectedIndex(0);
							cmbYear.setPreferredSize(new java.awt.Dimension(95, 23));
						}

					}
				}

			}	
			{
				pnlCenter =  new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new java.awt.Dimension(611, 157));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));
				
				{		
					pnlCenter_a = new JPanel(new GridLayout(4, 1, 0, 5));
					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
					pnlCenter_a.setPreferredSize(new java.awt.Dimension(95, 185));
					
					{
						lblTranType = new JLabel("Tran. Type", JLabel.TRAILING);
						pnlCenter_a.add(lblTranType);
						lblTranType.setEnabled(false);	
					}	
					{
						lblFiscalYr = new JLabel("Fiscal Yr.", JLabel.TRAILING);
						pnlCenter_a.add(lblFiscalYr);
						lblFiscalYr.setEnabled(false);	
					}	
					{
						lblStatus = new JLabel("Prepared by", JLabel.TRAILING);
						pnlCenter_a.add(lblStatus);
						lblStatus.setEnabled(false);	
					}
					{
						lblJVstatus = new JLabel("JV Status", JLabel.TRAILING);
						pnlCenter_a.add(lblJVstatus);
						lblJVstatus.setEnabled(false);	
					}				

					pnlCenter_2 = new JPanel(new BorderLayout(5,0));
					pnlCenter.add(pnlCenter_2, BorderLayout.CENTER);
					pnlCenter_2.setPreferredSize(new java.awt.Dimension(203, 118));
					pnlCenter_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

					pnlJVDtl_2a = new JPanel(new GridLayout(4, 1, 0, 5));
					pnlCenter_2.add(pnlJVDtl_2a, BorderLayout.WEST);
					pnlJVDtl_2a.setPreferredSize(new java.awt.Dimension(100, 185));
					pnlJVDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						lookupTranType = new _JLookup(null, "Transaction Type", 2, 2);
						pnlJVDtl_2a.add(lookupTranType);
						lookupTranType.setBounds(20, 27, 20, 25);
						lookupTranType.setReturnColumn(0);
						lookupTranType.setEnabled(false);
						lookupTranType.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupTranType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		
									tran_type_id 	= (String) data[0];		
									tran_type_name 	= (String) data[1];			
									tagTranType.setTag(tran_type_name);
								}
							}
						});	
					}		
					{
						lookupJVyear = new _JLookup(null, "JV Year", 2, 2);
						pnlJVDtl_2a.add(lookupJVyear);
						lookupJVyear.setBounds(20, 27, 20, 25);
						lookupJVyear.setReturnColumn(0);
						lookupJVyear.setEnabled(false);	
						//lookupJVyear.setEditable(false);
						lookupJVyear.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupJVyear.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									jv_year 	= data[0].toString();	
								}
							}
						});	
					}					
					{
						lookupPreparedby = new _JLookup(null, "Prepared by", 2, 2);
						pnlJVDtl_2a.add(lookupPreparedby);
						lookupPreparedby.setBounds(20, 27, 20, 25);
						lookupPreparedby.setReturnColumn(0);
						lookupPreparedby.setEnabled(false);	
						lookupPreparedby.setFilterName(true);
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
						lookupStatus = new _JLookup(null, "JV Status", 2, 2);
						pnlJVDtl_2a.add(lookupStatus);
						lookupStatus.setBounds(20, 27, 20, 25);
						lookupStatus.setReturnColumn(0);
						lookupStatus.setEnabled(false);	
						lookupStatus.setFilterName(true);
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
					
					pnlJVDtl_2b = new JPanel(new GridLayout(4, 1, 0, 5));
					pnlCenter_2.add(pnlJVDtl_2b, BorderLayout.CENTER);
					pnlJVDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
					pnlJVDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						tagTranType = new _JTagLabel("[ ]");
						pnlJVDtl_2b.add(tagTranType);
						tagTranType.setBounds(209, 27, 700, 22);
						tagTranType.setEnabled(false);	
						tagTranType.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagYear = new _JTagLabel("[ ]");
						pnlJVDtl_2b.add(tagYear);
						tagYear.setBounds(209, 27, 700, 22);
						tagYear.setEnabled(false);	
						tagYear.setVisible(false);	
						tagYear.setPreferredSize(new java.awt.Dimension(27, 33));
					}					
					{
						tagPreparedBy = new _JTagLabel("[ ]");
						pnlJVDtl_2b.add(tagPreparedBy);
						tagPreparedBy.setBounds(209, 27, 700, 22);
						tagPreparedBy.setEnabled(false);	
						tagPreparedBy.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagStatus = new _JTagLabel("[ ]");
						pnlJVDtl_2b.add(tagStatus);
						tagStatus.setBounds(209, 27, 700, 22);
						tagStatus.setEnabled(false);	
						tagStatus.setPreferredSize(new java.awt.Dimension(27, 33));
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
					btnCancel = new JButton("Refresh");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
				{
					btnexport = new JButton("Export");
					pnlSouth.add(btnexport);
					btnexport.setAlignmentX(0.5f);
					btnexport.setAlignmentY(0.5f);
					btnexport.setMaximumSize(new Dimension(100, 30));
					btnexport.setMnemonic(KeyEvent.VK_P);
					btnexport.setEnabled(true);
					btnexport.addActionListener(this);
				}
			}
		}
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				dte_from, dte_to, btnPreview));
		this.setBounds(0, 0, 682, 441);  //174
		
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

		getDate();
		
		tran_type_id 	= lookupTranType.getText().trim();
		prepared_by_id 	= lookupPreparedby.getText().trim();
		jv_year         = lookupJV_to.getText();
		status_id 		= lookupStatus.getText().trim();
		
		Integer jv_fr = 0;
		Integer jv_to = 0;		
		
		if (lookupJV_from.getText().equals("")) { } else { 
			jv_fr = Integer.parseInt(lookupJV_from.getText()); 
			try {date_from = dateFormat.parse("2000-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
			}
		if (lookupJV_to.getText().equals("")) { } else { 
			jv_to = Integer.parseInt(lookupJV_to.getText()); 
			try {date_to = dateFormat.parse("2100-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
			}
		if (lookupJVyear.getText().equals("")) { jv_year = "All"; } else { jv_year = lookupJVyear.getText(); }
				
		System.out.printf("DRF Fr.: " + lookupJV_from.getText() + "\n");
		System.out.printf("DRF To.: " + lookupJV_to.getText() + "\n");
			
		if(tran_type_id.equals("")) { tran_type_name = "All"; } else { }
		if(prepared_by_id.equals("")) { prepared_by_name = "All"; } else { }
		if(status_id.equals("")) { status_name = "All"; } else { }
		
		if(e.getActionCommand().equals("Preview")){		
			
			//period_from = cmbPeriodFr.getSelectedIndex() + 1;
			//period_to = cmbPeriodTo.getSelectedIndex() + 1;
			String criteria = "PV Prooflist";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("date_from", date_from);	
			mapParameters.put("date_to", date_to);		
			mapParameters.put("tran_type_id", tran_type_id);	
			mapParameters.put("tran_type_name", tran_type_name);	
			mapParameters.put("jv_year", jv_year);	
			mapParameters.put("prepared_id", prepared_by_id);	
			mapParameters.put("prepared_name", prepared_by_name);	
			mapParameters.put("status_id", status_id);	
			mapParameters.put("status_name", status_name);
			mapParameters.put("jv_from", jv_fr);	
			mapParameters.put("jv_to", jv_to);
			//Added by Erick 2023-03-20
			mapParameters.put("period_from",period_from);
			mapParameters.put("period_to", period_to);
			
			System.out.println("Period_from: "+period_from);
			System.out.println("Period_to: "+period_to);
			
			
			/*	Added by Mann2x; Date Added: August 8, 2018; The company should be filtered as requested by Ma'am Fel;	*/
			mapParameters.put("co_id", lookupCompany.getValue());
			FncReport.generateReport("/Reports/rptJV_prooflist.jasper", reportTitle, "", mapParameters);
		}
		
		if(e.getActionCommand().equals("Refresh")){	
			refreshFields();
		}
		if(e.getActionCommand().equals("Export")){/*ADDED BY JED VICEDO 2021-08-17 : FOR EXPORTING GENERAL LEDGER C/O ROMUALDO*/
			String col_names [] = {"JV No.", "JV Date", "Tran Type", "Remarks", "Created By", "Status" };

			String SQL = 			
					//Replace by Del Gonzales on Aug. 06, 2016 by a function 
					"select\n" + 
					"\n" + 
					"a.jv_no,\n" + 
					"a.jv_date,\n" + 
					"trim(b.tran_desc) as tran_type,\n" + 
					"trim(a.remarks) as remarks,\n" + 
					"trim(d.entity_name) as created_by,\n" + 
					"e.status_desc\n" + 
					"\n" + 
					"\n" + 
					"from (select * from rf_jv_header where co_id = '"+lookupCompany.getValue()+"' or '"+lookupCompany.getValue()+"' = '') a\n" + 
					"left join mf_acctg_trans b on a.tran_id = b.tran_id\n" + 
					"left join em_employee c on (case when a.edited_by = '' or a.edited_by is null then a.created_by else a.edited_by end) = c.emp_code\n" + 
					"left join rf_entity d on c.entity_id = d.entity_id\n" + 
					"left join mf_record_status e on a.status_id = e.status_id\n" + 
					"\n" + 
					"where ( case when '"+jv_fr+"' = 0 or '"+jv_fr+"' is null then a.jv_no is not null else a.jv_no::int >= '"+jv_fr+"' end )\n" + 
					"and ( case when '"+jv_to+"' = 0 or '"+jv_to+"' is null then a.jv_no is not null else a.jv_no::int <= '"+jv_to+"' end )\n" + 
					"and a.jv_date::date >= '"+date_from+"'\n" + 
					"and a.jv_date::date <= '"+date_to+"'\n" + 
					"and ( case when '"+tran_type_id+"' = '' or '"+tran_type_id+"' is null then a.jv_no is not null else a.tran_id = '"+tran_type_id+"'  end )\n" + 
					"and ( case when '"+jv_year+"' = 'All' or '"+jv_year+"' is null then a.jv_no is not null else a.fiscal_yr::varchar = '"+lookupJVyear.getText()+"'   end )\n" + 
					"and ( case when '"+prepared_by_id+"' = '' or '"+UserInfo.Alias+"' is null then a.jv_no is not null else (case when a.edited_by = '' or a.edited_by is null then a.created_by else a.edited_by end) = '"+prepared_by_id+"'  end )\n" + 
					"and ( case when '"+status_id+"' = '' or '"+status_id+"' is null then a.jv_no is not null else a.status_id = '"+status_id+"'  end )\n" + 
					"\n" + 
					"order by a.jv_no";

			System.out.println("Export Query: "+ SQL);
			FncGlobal.startProgress("Creating Spreadsheet");
			FncGlobal.CreateXLS(col_names, SQL, "JV ProofList");
			FncGlobal.stopProgress();
		}

	}

	private void enabledFields(boolean x){
		
		lblJVFrom.setEnabled(x);		
		lookupJV_from.setEnabled(x);	
		lblJVTo.setEnabled(x);	
		lookupJV_to.setEnabled(x);	
		lbl_date_fr.setEnabled(x);			
		dte_from.setEnabled(x);
		lblDate_to.setEnabled(x);	
		dte_to.setEnabled(x);
		
		lblTranType.setEnabled(x);	
		lblFiscalYr.setEnabled(x);	
		lblStatus.setEnabled(x);	
		lblJVstatus.setEnabled(x);	
		
		lookupTranType.setEnabled(x);	
		lookupJVyear.setEnabled(x);	
		lookupStatus.setEnabled(x);	
		lookupPreparedby.setEnabled(x);	
		
		tagTranType.setEnabled(x);	
		tagYear.setEnabled(x);
		tagStatus.setEnabled(x);	
		tagPreparedBy.setEnabled(x);
		
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);
		
		lookupJV_from.setEnabled(x);
		lookupJV_to.setEnabled(x);
		
		
	}
	
	public void refreshFields(){
		
		lookupTranType.setValue("");	
		lookupJVyear.setValue("");	
		lookupStatus.setValue("");	
		lookupPreparedby.setValue("");
		
		tagTranType.setTag("");
		tagYear.setTag("");
		tagStatus.setTag("");	
		tagPreparedBy.setTag("");
		
		//dte_from.setDate(null);
		//dte_to.setDate(null);
		
		tran_type_id 	= "";
		tran_type_name	= "";
		jv_year 		= "";
		prepared_by_id	= "";
		prepared_by_name= "";
		status_id		= "";
		status_name		= "";
		
		lookupJV_from.setText("");
		lookupJV_to.setText("");
		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		txtCompany.setText(company);

		enabledFields(true);
		lookupTranType.setLookupSQL(getTranType());	
		lookupJVyear.setLookupSQL(getJVyear());	
		lookupPreparedby.setLookupSQL(getPreparedBy());	
		lookupStatus.setLookupSQL(getStatus());	
		
		lookupCompany.setValue(co_id);
		
		lookupJV_from.setLookupSQL(getJV_no());			
		lookupJV_to.setLookupSQL(getJV_no());	
	}
	
	
	//lookup
	
	private String getTranType(){

		return 
		"select \r\n" + 
		"\r\n" + 
		"a.tran_id as \"Tran ID\",\r\n" + 
		"trim(a.tran_desc)  as \"Tran. Desc.\" ,\r\n" + 
		"a.system_id  as \"System ID\", " +		
		"b.status_desc  as \"Status\" \r\n" + 
		"\r\n" + 
		"from mf_acctg_trans a\r\n" + 
		"left join mf_record_status b on a.status_id=b.status_id\r\n" + 
		"where co_id = '"+co_id+"' "  ;	

	}	
	
	private String getJVyear(){

		String sql = 
		"select distinct on (fiscal_yr) fiscal_yr from rf_jv_header\r\n" + 
		"where co_id = '"+co_id+"' order by fiscal_yr "  ;		
		return sql;

	}	
		
	private String getPreparedBy(){

		String sql = 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.created_by,\r\n" + 
		"upper(trim(c.last_name))||', '||upper(trim(c.first_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_jv_header a \r\n" + 
		"left join em_employee b on a.created_by = b.emp_code\r\n" + 
		"left join rf_entity c on b.entity_id = c.entity_id    \n" +
		"\r\n" + 
		") a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
		
	private String getStatus(){

		String sql = 
		"select \n" +

		"a.status_id, \n" +
		"b.status_desc \n" +

        "from (select distinct on(status_id) status_id from rf_jv_header) a  \n" +
        "left join mf_record_status b on a.status_id = b.status_id \n" ;	
        		
		return sql;

	}	
		
	
	private String getJV_no(){//used

		return 

		"select \r\n" + 
		"trim(a.jv_no) as \"JV No\",  \n" +
		"a.jv_date as \"JV Date\",  \n" +
		"a.fiscal_yr  as \"Year\",  \n" +
		"a.period_id  as \"Period\" ,  \n" +
		"c.status_desc as \"Status\" \n " +

		"from rf_jv_header a  \r\n" + 		
		"left join mf_record_status c on a.status_id = c.status_id \n"  +
		"where a.co_id = '"+co_id+"' " +
		"order by a.jv_no desc" ;			

	}
	
	private void getDate(){
		
		if (rbtnDate.isSelected()==true){
			date_from = dte_from.getDate();		
			date_to = dte_to.getDate();	
			period_from = null;
			period_to = null;
		}
		else {
			
			period_from = cmbPeriodFr.getSelectedIndex() + 1;
			period_to = cmbPeriodTo.getSelectedIndex() + 1;
			
			if(cmbPeriodFr.getSelectedIndex()==0){try {date_from = dateFormat2.parse((("01-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==1){try {date_from = dateFormat2.parse((("02-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==2){try {date_from = dateFormat2.parse((("03-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==3){try {date_from = dateFormat2.parse((("04-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==4){try {date_from = dateFormat2.parse((("05-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==5){try {date_from = dateFormat2.parse((("06-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==6){try {date_from = dateFormat2.parse((("07-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==7){try {date_from = dateFormat2.parse((("08-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==8){try {date_from = dateFormat2.parse((("09-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==9){try {date_from = dateFormat2.parse((("10-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==10){try {date_from = dateFormat2.parse((("11-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==11){try {date_from = dateFormat2.parse((("12-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==12){try {date_from = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==13){try {date_from = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==14){try {date_from = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}						
		
			if(cmbPeriodTo.getSelectedIndex()==0){try {date_to = dateFormat2.parse((("01-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==1&&cmbYear.getSelectedIndex()==2){try {date_to = dateFormat2.parse((("02-29-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==1&&cmbYear.getSelectedIndex()!=2){try {date_to = dateFormat2.parse((("02-28-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==2){try {date_to = dateFormat2.parse((("03-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==3){try {date_to = dateFormat2.parse((("04-30-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==4){try {date_to = dateFormat2.parse((("05-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==5){try {date_to = dateFormat2.parse((("06-30-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==6){try {date_to = dateFormat2.parse((("07-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==7){try {date_to = dateFormat2.parse((("08-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==8){try {date_to = dateFormat2.parse((("09-30-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==9){try {date_to = dateFormat2.parse((("10-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==10){try {date_to = dateFormat2.parse((("11-30-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==11){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==12){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==13){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==14){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}							
		}
		
		
	}


}
