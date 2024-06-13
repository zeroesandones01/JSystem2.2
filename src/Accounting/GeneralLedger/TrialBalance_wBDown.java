package Accounting.GeneralLedger;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import tablemodel.modelCV_acct_entries;
import tablemodel.modelCV_pv;
import tablemodel.modelTB_generate_v2;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class TrialBalance_wBDown extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Trial Balance (with Debit-Credit Breakdown)";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlPeriodTo_a;
	private JPanel pnlPeriodTo_b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlTBtable;
	private JPanel pnlTBtable_acct_entries;
	private JPanel pnlSouthCenterb;
	private JPanel pnlNorth_main;
	private JPanel pnlNorth_center;
	private JPanel pnlPeriod;
	private JPanel pnlPeriodFr;
	private JPanel pnlPeriodFr_a;
	private JPanel pnlPeriodFr_b;
	private JPanel pnlPeriodTo;
	private JPanel pnlPeriodFr_c;
	private JPanel pnlPeriodFr_c1;
	private JPanel pnlPeriodFr_c2;
	private static JPanel pnlCloseTB;
	private JPanel pnlPeriod_2;
	private JPanel pnlPeriod_Fr2;
	private JPanel pnlPeriodFr_a2;
	private JPanel pnlPeriodFr_b2;
	private JPanel pnlPeriodTo2;
	private JPanel pnlPeriodTo_a2;
	private JPanel pnlPeriodTo_b2;

	private JLabel lblCompany;
	public static JLabel lbl_no;
	private JLabel lblProject;
	private JLabel lbl_date_fr;
	private JLabel lblPhase;
	private JLabel lblDate_to;
	private JLabel lblInclude;	
	private JLabel lbl_date_fr_close;
	private JLabel lbl_date_to_close;
	private JLabel lbl_period_fr2;
	private JLabel lblPeriod_to2;
	private JLabel lblYear;

	private _JTagLabel tagProject;
	private _JTagLabel tagPhase;
	public static _JTagLabel tagCompany;

	private _JLookup lookupCompany;
	public static _JLookup lookupDV_no;	
	private static _JLookup lookupProject;	
	private static _JLookup lookupPhase;

	public static modelCV_acct_entries modelDV_acct_enties;
	public static modelCV_acct_entries modelDV_accttotal;
	public static modelCV_pv modelTB_summarytotal;
	public static modelCV_pv modelTB_summary;

	private static _JTableTotal tblTB_details_total;	
	private static _JTableMain tblTB_details;

	public static JList rowHeaderDV_acct_entries;
	public static JList rowHeader_summary;
	private JList rowHeaderTB_details;

	private _JTabbedPane tabCenter;	

	private static _JDateChooser dte_from;	
	private static _JDateChooser dte_to;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	static SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy");

	private JButton btnPreview;
	private JButton btnGenerate;
	private JButton btnCancel;

	private _JScrollPaneMain scrollTB_detail;
	private static _JScrollPaneTotal scrollTB_details_total;

	private JCheckBox chkIncludeZeroBal;	

	private static modelTB_generate_v2 modelTB_details;
	private static modelTB_generate_v2 modelTB_details_total;

	static JMenuItem mnidelete;
	static JMenuItem mniaddrow;	
	
	private JPopupMenu menu;
	private JMenuItem mniOpenGL;	
	
	private JMenuItem mniCloseTB;	
	
	private JButton btnOK;	
	
	private _JDateChooser dte_from_close;	
	private _JDateChooser dte_to_close;
	
	private static JRadioButton rbtnDate;	
	private JRadioButton rbtnPeriod;
	
	private JComboBox cmbPeriodFr;	
	private JComboBox cmbPeriodTo;	
	private static JComboBox cmbYear;
	private static JComboBox cmbInclude;
	private static JCheckBox chkIncludeActive;	

	public static String co_id;
	public static String proj_id = "";
	public static String proj_name = "";
	public static String ph_no = "";
	public static String ph_code = "";	
	public static String acct_id = "";
	public static String company = "";
	public static String company_logo = "";
	static String pay_type = "";
	public static String status_id = "P";

	static Boolean show_zero = false;
	static java.util.Date date_from = null;
	static java.util.Date date_to = null;
	static String include_month 	= "";
	
	private static Integer period_from = null;
	private static Integer period_to = null;	

	public TrialBalance_wBDown() {
		super(title, true, true, true, true);
		initGUI();
	}

	public TrialBalance_wBDown(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public TrialBalance_wBDown(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(1012, 586));
		this.setBounds(0, 0, 1012, 586);

		{
			menu = new JPopupMenu("Popup");
			mniOpenGL = new JMenuItem("Open General Ledger");
			mniCloseTB = new JMenuItem("Close Trial Balance");
			menu.add(mniOpenGL);
			menu.add(mniCloseTB);
			mniOpenGL.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openGL();
				}
			});
			mniCloseTB.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					closeTB();
				}
			});
		}


		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		//pnlReq_Main = new JPanel();

		pnlNorth_main = new JPanel();
		pnlMain.add(pnlNorth_main, BorderLayout.NORTH);
		pnlNorth_main.setLayout(new BorderLayout(5, 5));
		pnlNorth_main.setBorder(lineBorder);		
		pnlNorth_main.setPreferredSize(new java.awt.Dimension(1000, 161));


		//start of north_main
		{
			pnlNorth = new JPanel();
			pnlNorth_main.add(pnlNorth, BorderLayout.CENTER);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(516, 152));

			pnlComp = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);	

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(514, 95));	

				{
					pnlComp_a1 = new JPanel(new GridLayout(3, 2, 5, 5));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(184, 94));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

					{
						lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
						pnlComp_a1.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(81, 25));
						lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}
					{
						lookupCompany = new _JLookup(null, "Company",0,2);
						pnlComp_a1.add(lookupCompany);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setReturnColumn(0);
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id 		= (String) data[0];	
									tagCompany.setTag((String) data[1]);
									company		= (String) data[1];	
									company_logo = (String) data[3];

									//enable_fields(true);
									lookupProject.setLookupSQL(getProject());	

									lblPhase.setEnabled(false);	
									lookupPhase.setEnabled(false);	
									tagPhase.setEnabled(false);	

									btnPreview.setEnabled(false);
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblProject = new JLabel("        Project", JLabel.TRAILING);
						pnlComp_a1.add(lblProject);
						lblProject.setBounds(8, 11, 62, 12);
						lblProject.setEnabled(false);	
						lblProject.setPreferredSize(new java.awt.Dimension(81, 25));
						lblProject.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}					
					{
						lookupProject = new _JLookup(null, "Project",0,2);
						pnlComp_a1.add(lookupProject);
						lookupProject.setLookupSQL(SQL_COMPANY());
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(false);	
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									proj_id 	= (String) data[0];	
									proj_name   = (String) data[1];
									tagProject.setTag(proj_name);	

									lblPhase.setEnabled(true);	
									lookupPhase.setEnabled(true);
									tagPhase.setEnabled(true);	

									lookupPhase.setLookupSQL(getSubproject());
									refresh_tables();
								}
							}
						});
					}
					{
						lblPhase = new JLabel("       Phase", JLabel.TRAILING);
						pnlComp_a1.add(lblPhase);
						lblPhase.setBounds(8, 11, 62, 12);
						lblPhase.setEnabled(false);	
						lblPhase.setPreferredSize(new java.awt.Dimension(81, 25));
						lblPhase.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lookupPhase = new _JLookup(null, "Phase",0,2);
						pnlComp_a1.add(lookupPhase);
						lookupPhase.setLookupSQL(SQL_COMPANY());
						lookupPhase.setReturnColumn(0);
						lookupPhase.setEnabled(false);	
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									ph_code		= (String) data[0];	
									ph_no		= (String) data[1];	
									tagPhase.setTag(ph_no);
									refresh_tables();
								}
							}
						});
					}
					pnlComp_a2 = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
					pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
					pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
					pnlComp_a2.addMouseListener(new PopupTriggerListener_panel());
					{
						{
							tagCompany = new _JTagLabel("[ ]");
							pnlComp_a2.add(tagCompany);
							tagCompany.setBounds(209, 27, 700, 22);
							tagCompany.setEnabled(true);	
							tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
						{
							tagProject = new _JTagLabel("[ ]");
							pnlComp_a2.add(tagProject);
							tagProject.setBounds(209, 27, 700, 22);
							tagProject.setEnabled(false);							
							tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
						{
							tagPhase = new _JTagLabel("[ ]");
							pnlComp_a2.add(tagPhase);
							tagPhase.setBounds(209, 27, 700, 22);
							tagPhase.setEnabled(false);	
							tagPhase.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
					}
				}
			}	

			{
				pnlPeriod = new JPanel(new BorderLayout(5,5));
				pnlComp.add(pnlPeriod, BorderLayout.CENTER);	
				pnlPeriod.setPreferredSize(new java.awt.Dimension(514, 23));

				{
					pnlPeriodFr = new JPanel(new BorderLayout(5,5));
					pnlPeriod.add(pnlPeriodFr, BorderLayout.WEST);	
					pnlPeriodFr.setPreferredSize(new java.awt.Dimension(275, 23));
					pnlPeriodFr.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					{
						pnlPeriodFr_a = new JPanel(new GridLayout(1, 1, 0, 0));
						pnlPeriodFr.add(pnlPeriodFr_a, BorderLayout.WEST);	
						pnlPeriodFr_a.setPreferredSize(new java.awt.Dimension(147, 23));
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
							rbtnDate.setPreferredSize(new java.awt.Dimension(30, 15));
							rbtnDate.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae){	
									
								if (rbtnDate.isSelected()==true){
									lbl_date_fr.setEnabled(true);	
									dte_from.setEnabled(true);
									lblDate_to.setEnabled(true);
									dte_to.setEnabled(true);
									lblInclude.setEnabled(true);
									cmbInclude.setEnabled(true);
									
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
									lblInclude.setEnabled(false);
									cmbInclude.setEnabled(false);
									
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
							lbl_date_fr.setPreferredSize(new java.awt.Dimension(107, 23));
						}
					}
					{
						pnlPeriodFr_b = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlPeriodFr.add(pnlPeriodFr_b, BorderLayout.CENTER);	
						pnlPeriodFr_b.setPreferredSize(new java.awt.Dimension(135, 119));
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
							dte_from.addPropertyChangeListener( new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {
									date_from = dte_from.getDate();						
								}
							});	
						}	
					}
				}
				{
					pnlPeriodTo = new JPanel(new BorderLayout(0, 0));
					pnlPeriod.add(pnlPeriodTo, BorderLayout.CENTER);	
					pnlPeriodTo.setPreferredSize(new java.awt.Dimension(335, 23));
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
								date_to = dte_to.getDate();	

								DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");					
								String end_date	 = df.format(dte_to.getDate());	
								String end_date_sub = end_date.substring(5);	

								if(end_date_sub.equals("31-12"))
								{
									cmbInclude.setEnabled(true);
								}
								else
								{
									cmbInclude.setEnabled(false);
									cmbInclude.setSelectedIndex(0);
								}
							}
						});	
					}
				}
				{
					pnlPeriodFr_c = new JPanel(new BorderLayout(5, 5));
					pnlPeriod.add(pnlPeriodFr_c, BorderLayout.EAST);	
					pnlPeriodFr_c.setPreferredSize(new java.awt.Dimension(187, 23));
					pnlPeriodFr_c.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));


					pnlPeriodFr_c1 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlPeriodFr_c.add(pnlPeriodFr_c1, BorderLayout.CENTER);	
					pnlPeriodFr_c1.setPreferredSize(new java.awt.Dimension(82, 23));
					pnlPeriodFr_c1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					{
						lblInclude = new JLabel("Include (Until)", JLabel.TRAILING);
						pnlPeriodFr_c1.add(lblInclude);
						lblInclude.setEnabled(true);
						lblInclude.setPreferredSize(new java.awt.Dimension(67, 55));
					}

					pnlPeriodFr_c2 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlPeriodFr_c.add(pnlPeriodFr_c2, BorderLayout.EAST);	
					pnlPeriodFr_c2.setPreferredSize(new java.awt.Dimension(94, 23));
					pnlPeriodFr_c2.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					{
						String status2[] = { "(None)", "13th Month","14th Month","15th Month","99th Month" };
						cmbInclude = new JComboBox(status2);
						pnlPeriodFr_c2.add(cmbInclude);
						cmbInclude.setSelectedItem(null);
						cmbInclude.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
						cmbInclude.setBounds(537, 15, 180, 21);
						cmbInclude.setEnabled(true);
						cmbInclude.setSelectedIndex(0);
						cmbInclude.setPreferredSize(new java.awt.Dimension(161, 23));
					}

				}
			}
			{
				pnlPeriod_2 = new JPanel(new BorderLayout(5,5));
				pnlComp.add(pnlPeriod_2, BorderLayout.SOUTH);	
				pnlPeriod_2.setPreferredSize(new java.awt.Dimension(514, 23));

				{
					pnlPeriod_Fr2 = new JPanel(new BorderLayout(5,5));
					pnlPeriod_2.add(pnlPeriod_Fr2, BorderLayout.WEST);	
					pnlPeriod_Fr2.setPreferredSize(new java.awt.Dimension(275, 23));
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
										lblInclude.setEnabled(false);
										cmbInclude.setEnabled(false);
										
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
										lblInclude.setEnabled(true);
										cmbInclude.setEnabled(true);
										
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
						String status2[] = { "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","99" };
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
					pnlPeriodFr_c.setPreferredSize(new java.awt.Dimension(187, 23));
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
						String status2[] = { "2018", "2017","2016" };
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
			pnlNorth_center = new JPanel();
			pnlNorth_main.add(pnlNorth_center, BorderLayout.EAST);
			pnlNorth_center.setLayout(new BorderLayout(5, 5));
			pnlNorth_center.setBorder(lineBorder);		
			pnlNorth_center.setPreferredSize(new java.awt.Dimension(290, 238));
			{
				btnGenerate = new JButton("Generate Trial Balance");
				pnlNorth_center.add(btnGenerate);
				btnGenerate.setActionCommand("Generate");
				btnGenerate.addActionListener(this);
				btnGenerate.setEnabled(false);
			}
		}

		//end of north_main




		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlTBtable = new JPanel();
			pnlTable.add(pnlTBtable, BorderLayout.CENTER);
			pnlTBtable.setLayout(new BorderLayout(5, 5));
			pnlTBtable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlTBtable.setBorder(lineBorder);	

			tabCenter = new _JTabbedPane();
			pnlTBtable.add(tabCenter, BorderLayout.CENTER);


			{			
				pnlTBtable_acct_entries = new JPanel(new BorderLayout());
				tabCenter.addTab("TB Details (with Debit / Credit Breakdown)", null, pnlTBtable_acct_entries, null);
				pnlTBtable_acct_entries.setPreferredSize(new java.awt.Dimension(1183, 365));					

				{
					scrollTB_detail = new _JScrollPaneMain();
					pnlTBtable_acct_entries.add(scrollTB_detail, BorderLayout.CENTER);
					{
						modelTB_details = new tablemodel.modelTB_generate_v2();

						tblTB_details = new _JTableMain(modelTB_details);
						scrollTB_detail.setViewportView(tblTB_details);
						tblTB_details.addMouseListener(new PopupTriggerListener_panel());
						tblTB_details.setSortable(false);
						tblTB_details.getColumnModel().getColumn(0).setPreferredWidth(90);
						tblTB_details.getColumnModel().getColumn(1).setPreferredWidth(200);
						tblTB_details.getColumnModel().getColumn(2).setPreferredWidth(90);
						tblTB_details.getColumnModel().getColumn(3).setPreferredWidth(90);
						tblTB_details.getColumnModel().getColumn(4).setPreferredWidth(90);
						tblTB_details.getColumnModel().getColumn(5).setPreferredWidth(90);
						tblTB_details.getColumnModel().getColumn(6).setPreferredWidth(90);
						tblTB_details.getColumnModel().getColumn(7).setPreferredWidth(90);
						tblTB_details.addMouseListener(this);							
						tblTB_details.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
							}							
							public void keyPressed(KeyEvent e) {
							}

						}); 
						tblTB_details.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblTB_details.rowAtPoint(e.getPoint()) == -1){
									tblTB_details_total.clearSelection();
								}else{
									tblTB_details.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblTB_details.rowAtPoint(e.getPoint()) == -1){
									tblTB_details_total.clearSelection();
								}else{
									tblTB_details.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeaderTB_details = tblTB_details.getRowHeader22();
						scrollTB_detail.setRowHeaderView(rowHeaderTB_details);
						scrollTB_detail.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollTB_details_total = new _JScrollPaneTotal(scrollTB_detail);
					pnlTBtable_acct_entries.add(scrollTB_details_total, BorderLayout.SOUTH);
					{
						modelTB_details_total = new modelTB_generate_v2();
						modelTB_details_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00),  
								new BigDecimal(0.00),  new BigDecimal(0.00),  new BigDecimal(0.00),  new BigDecimal(0.00),  new BigDecimal(0.00) });

						tblTB_details_total = new _JTableTotal(modelTB_details_total, tblTB_details);
						tblTB_details_total.setFont(dialog11Bold);
						scrollTB_details_total.setViewportView(tblTB_details_total);
						((_JTableTotal) tblTB_details_total).setTotalLabel(0);
					}
				}
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(813, 31));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				chkIncludeActive = new JCheckBox("Include Active Entries");
				pnlSouthCenterb.add(chkIncludeActive);
				chkIncludeActive.setEnabled(false);	
				chkIncludeActive.setSelected(false);
				chkIncludeActive.setPreferredSize(new java.awt.Dimension(383, 26));
				chkIncludeActive.setHorizontalAlignment(JTextField.CENTER);	
				chkIncludeActive.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;					
						if (selected) {
							status_id = "A";	
							generateTB(modelTB_details, rowHeaderTB_details, modelTB_details_total, status_id); 
						} else {
							status_id = "P";	
							generateTB(modelTB_details, rowHeaderTB_details, modelTB_details_total, status_id); 
						}
					}
				});
			}
			{
				chkIncludeZeroBal = new JCheckBox("Include accounts with zero balance");
				pnlSouthCenterb.add(chkIncludeZeroBal);
				chkIncludeZeroBal.setEnabled(false);	
				chkIncludeZeroBal.setHorizontalAlignment(JTextField.CENTER);	
				chkIncludeZeroBal.setPreferredSize(new java.awt.Dimension(383, 26));
				chkIncludeZeroBal.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						if(chkIncludeZeroBal.isSelected()==true) {show_zero=true;} else {show_zero=false;}
						generateTB(modelTB_details, rowHeaderTB_details, modelTB_details_total, status_id); 
					}
				});
			}
			{

				btnPreview = new JButton("Preview");
				pnlSouthCenterb.add(btnPreview);
				btnPreview.setActionCommand("Preview");
				btnPreview.addActionListener(this);
				btnPreview.setEnabled(false);
				//btnPreview.setVisible(false);
			}
			{

				btnCancel = new JButton("Cancel");
				pnlSouthCenterb.add(btnCancel);
				btnCancel.setActionCommand("Cancel");
				btnCancel.addActionListener(this);
				btnCancel.setEnabled(false);
			}
			/*{			
				pnlTB_summary = new JPanel(new BorderLayout());
				tabCenter.addTab("  Summary  ", null, pnlTB_summary, null);
				pnlTB_summary.setPreferredSize(new java.awt.Dimension(1183, 365));
				pnlTB_summary.setVisible(false);

				{
					scrollTB_summary = new _JScrollPaneMain();
					pnlTB_summary.add(scrollTB_summary, BorderLayout.CENTER);
					{
						modelTB_summary = new modelCV_pv();

						tblGL_summary = new _JTableMain(modelTB_summary);
						scrollTB_summary.setViewportView(tblGL_summary);
						tblGL_summary.addMouseListener(this);	
						tblGL_summary.setSortable(false);
						tblGL_summary.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblGL_summary.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblGL_summary.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblGL_summary.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
							}							
							public void keyPressed(KeyEvent e) {
							}

						}); 
						tblGL_summary.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblGL_summary.rowAtPoint(e.getPoint()) == -1){
									tblGL_summarytotal.clearSelection();
								}else{
									tblGL_summary.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblGL_summary.rowAtPoint(e.getPoint()) == -1){
									tblGL_summarytotal.clearSelection();
								}else{
									tblGL_summary.setCellSelectionEnabled(true);
								}
							}
						});

					}
					{
						rowHeader_summary = tblGL_summary.getRowHeader22();
						scrollTB_summary.setRowHeaderView(rowHeader_summary);
						scrollTB_summary.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollTB_summarytotal = new _JScrollPaneTotal(scrollTB_summary);
					pnlTB_summary.add(scrollTB_summarytotal, BorderLayout.SOUTH);
					{
						modelTB_summarytotal = new modelCV_pv();
						modelTB_summarytotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

						tblGL_summarytotal = new _JTableTotal(modelTB_summarytotal, tblGL_summary);
						tblGL_summarytotal.setFont(dialog11Bold);
						scrollTB_summarytotal.setViewportView(tblGL_summarytotal);
						((_JTableTotal) tblGL_summarytotal).setTotalLabel(0);
					}
				}	
			}*/
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	{
		pnlCloseTB= new JPanel();
		pnlCloseTB.setLayout(null);
		pnlCloseTB.setPreferredSize(new java.awt.Dimension(400, 90));

		{
			lbl_date_fr_close = new JLabel("Date From", JLabel.TRAILING);
			pnlCloseTB.add(lbl_date_fr_close);
			lbl_date_fr_close.setEnabled(true);	
			lbl_date_fr_close.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
			lbl_date_fr_close.setPreferredSize(new java.awt.Dimension(84, 55));
			lbl_date_fr_close.setBounds(0, 15, 60, 20);
		}
		{
			dte_from_close = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
			pnlCloseTB.add(dte_from_close);
			dte_from_close.setDate(null);
			dte_from_close.setEnabled(true);
			dte_from_close.setDateFormatString("yyyy-MM-dd");
			((JTextFieldDateEditor)dte_from_close.getDateEditor()).setEditable(false);
			dte_from_close.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dte_from_close.addPropertyChangeListener( new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					date_from = dte_from_close.getDate();						
				}
			});	
			dte_from_close.setBounds(70, 15, 130, 20);
		}	
		{
			lbl_date_to_close = new JLabel("Date To", JLabel.TRAILING);
			pnlCloseTB.add(lbl_date_to_close);
			lbl_date_to_close.setEnabled(true);	
			lbl_date_to_close.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
			lbl_date_to_close.setPreferredSize(new java.awt.Dimension(84, 55));
			lbl_date_to_close.setBounds(200, 15, 60, 20);
		}
		{
			dte_to_close = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
			pnlCloseTB.add(dte_to_close);
			dte_to_close.setDate(null);
			dte_to_close.setEnabled(true);
			dte_to_close.setDateFormatString("yyyy-MM-dd");
			((JTextFieldDateEditor)dte_to_close.getDateEditor()).setEditable(false);
			dte_to_close.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dte_to_close.addPropertyChangeListener( new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					date_to = dte_to_close.getDate();						
				}
			});	
			dte_to_close.setBounds(270, 15, 130, 20);
		}
		{
			btnOK = new JButton();
			pnlCloseTB.add(btnOK);
			btnOK.setBounds(150, 58, 100, 30);
			btnOK.setText("OK");
			btnOK.setFocusable(false);
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCloseTB);
					optionPaneWindow.dispose();
					openJV_closeTB(dte_from_close.getDate().toString(), dte_to_close.getDate().toString());
				}
			});
		}
	}


	//display tables
	private void generateTB(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String statusID) {

		getDate();
		
		if(cmbInclude.getSelectedIndex()==0){include_month = "";} 
		else if(cmbInclude.getSelectedIndex()==1){include_month = "13";}
		else if(cmbInclude.getSelectedIndex()==2){include_month = "14";}
		else if(cmbInclude.getSelectedIndex()==3){include_month = "15";}
		else if(cmbInclude.getSelectedIndex()==4){include_month = "99";}

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 

			"select * from view_trial_balance_with_debcre_bdown('"+co_id+"','"+ph_no+"','"+ph_code+"','"+date_from+"'," +
			"'"+date_to+"', '"+proj_id+"', "+show_zero+",'"+statusID+"','"+include_month+"',"+period_from+","+period_to+" ) ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalTB(modelMain, modelTotal);			
		}		

		else {
			modelTB_details_total = new tablemodel.modelTB_generate_v2();
			modelTB_details_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00),  new BigDecimal(0.00), new BigDecimal(0.00),  new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

			tblTB_details_total = new _JTableTotal(modelTB_details_total, tblTB_details);
			tblTB_details_total.setFont(dialog11Bold);
			scrollTB_details_total.setViewportView(tblTB_details_total);
			((_JTableTotal) tblTB_details_total).setTotalLabel(0);}

		tblTB_details.packAll();
		tblTB_details.getColumnModel().getColumn(0).setPreferredWidth(90);
		tblTB_details.getColumnModel().getColumn(2).setPreferredWidth(90);
		tblTB_details.getColumnModel().getColumn(3).setPreferredWidth(90);
		tblTB_details.getColumnModel().getColumn(4).setPreferredWidth(90);
		tblTB_details.getColumnModel().getColumn(5).setPreferredWidth(90);
		tblTB_details.getColumnModel().getColumn(6).setPreferredWidth(90);
		tblTB_details.getColumnModel().getColumn(7).setPreferredWidth(90);
		tblTB_details.getColumnModel().getColumn(8).setPreferredWidth(90);
		adjustRowHeight();
		
		totalTB(modelMain, modelTotal);	
		
		if (isClosed(null)==true) {mniCloseTB.setEnabled(false);} else {mniCloseTB.setEnabled(true);}
	}	

	public static void generateTB_total(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		//DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select 'Total', '',\n" +
			"a.beg_bal, \n" +
			"a.crb_amt_tot, \n" +
			"a.cv_amt, \n" +
			"a.pv_amt, \n" +
			"a.jv_amt, \n" +
			"(coalesce(a.crb_amt_tot,0)+coalesce(a.cv_amt,0)+coalesce(a.pv_amt,0)+coalesce(a.jv_amt,0)) as period_trans, \n" +
			"(coalesce(a.beg_bal,0)+coalesce(a.crb_amt_tot,0)+coalesce(a.cv_amt,0)+coalesce(a.pv_amt,0)+coalesce(a.jv_amt,0)) as end_bal \n\n" +

			"from ( select  \n" +			

			//Beginning Balance
			"--beginning balance\r\n" + 
			"(select sum(total) as beg_bal from (\r\n" + 
			"\r\n" + 
			"  select coalesce(sum(a.crb_amt),0) as total  \r\n" + 
			"	from (select distinct on (rb_id, doc_id, acct_id) rb_id, doc_id, acct_id, sum(trans_amt) as crb_amt from rf_crb_detail \r\n" + 
			"	where status_id = 'A' and co_id = '"+co_id+"' group by rb_id, doc_id, acct_id) a\r\n" + 
			"	join rf_crb_header b on a.rb_id = b.rb_id and a.doc_id = b.doc_id\r\n" + 
			"	where b.posted_date < '"+date_from+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else b.proj_id = '"+proj_id+"' end) \n" +
			"	and (case when '"+ph_no+"' = '' then a.acct_id is not null else b.phase = '"+ph_no+"' end)  "	+
			"	UNION ALL\r\n" + 
			"\r\n" + 
			" select coalesce(sum(a.cv_amt),0) as total  \r\n" + 
			"	from (select distinct on (cv_no, acct_id) cv_no, acct_id, sum(tran_amt) as cv_amt from rf_cv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'D' and co_id = '"+co_id+"' group by cv_no, acct_id) a\r\n" + 
			"	join rf_cv_header b on a.cv_no = b.cv_no \r\n" + 
			"	where b.date_posted < '"+date_from+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.acct_id = '00' end) \n" +
			"	and (case when '"+ph_no+"' = '' then a.acct_id is not null else a.acct_id = '00' end) \n" +    //CV does not have phase column
			"	UNION ALL\r\n" + 
			"\r\n" + 
			" select -1*coalesce(sum(a.cv_amt),0) as total  \r\n" + 
			"	from (select distinct on (cv_no, acct_id) cv_no, acct_id, sum(tran_amt) as cv_amt from rf_cv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'C' and co_id = '"+co_id+"' group by cv_no, acct_id) a\r\n" + 
			"	join rf_cv_header b on a.cv_no = b.cv_no \r\n" + 
			"	where b.date_posted < '"+date_from+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.acct_id = '00' end) \n" +
			"	and (case when '"+ph_no+"' = '' then a.acct_id is not null else a.acct_id = '00' end) \n" +    //CV does not have phase column
			"	UNION ALL 	\r\n" + 
			"\r\n" + 
			"  select coalesce(sum(a.pv_amt),0) as total  \r\n" + 
			"	from (select distinct on (pv_no, acct_id, project_id, sub_projectid) pv_no, acct_id, project_id, sub_projectid, sum(tran_amt) as pv_amt from rf_pv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'D' and co_id = '"+co_id+"' group by pv_no, acct_id, project_id, sub_projectid) a\r\n" + 
			"	join rf_pv_header b on a.pv_no = b.pv_no \r\n" + 
			"	where b.date_posted < '"+date_from+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.project_id = '"+proj_id+"' end) \n" +  
			"	and (case when '"+ph_code+"' = '' then a.acct_id is not null else a.sub_projectid = '"+ph_code+"' end) \n" + 
			"	UNION ALL\r\n" + 
			"	\r\n" + 
			"  select -1*coalesce(sum(a.pv_amt),0) as total  \r\n" + 
			"	from (select distinct on (pv_no, acct_id, project_id, sub_projectid) pv_no, acct_id, project_id, sub_projectid, sum(tran_amt) as pv_amt from rf_pv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'C' and co_id = '"+co_id+"' group by pv_no, acct_id, project_id, sub_projectid) a\r\n" + 
			"	join rf_pv_header b on a.pv_no = b.pv_no \r\n" + 
			"	where b.date_posted < '"+date_from+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.project_id = '"+proj_id+"' end) \n" +  
			"	and (case when '"+ph_code+"' = '' then a.acct_id is not null else a.sub_projectid = '"+ph_code+"' end) \n" + 
			"	UNION ALL\r\n" + 
			"	\r\n" + 
			"  select coalesce(sum(a.jv_amt),0) as total  \r\n" + 
			"	from (select distinct on (jv_no, acct_id, project_id, sub_projectid) jv_no, acct_id, project_id, sub_projectid, sum(tran_amt) as jv_amt from rf_jv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'D' and co_id = '"+co_id+"' group by jv_no, acct_id, project_id, sub_projectid) a\r\n" + 
			"	join rf_jv_header b on a.jv_no = b.jv_no \r\n" + 
			"	where b.date_posted < '"+date_from+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.project_id = '"+proj_id+"' end) \n" +  
			"	and (case when '"+ph_code+"' = '' then a.acct_id is not null else a.sub_projectid = '"+ph_code+"' end) \n" + 
			"	UNION ALL\r\n" + 
			"	\r\n" + 
			"  select -1*coalesce(sum(a.jv_amt),0) as total  \r\n" + 
			"	from (select distinct on (jv_no, acct_id, project_id, sub_projectid) jv_no, acct_id, project_id, sub_projectid, sum(tran_amt) as jv_amt from rf_jv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'C' and co_id = '"+co_id+"' group by jv_no, acct_id, project_id, sub_projectid) a\r\n" + 
			"	join rf_jv_header b on a.jv_no = b.jv_no \r\n" + 
			"	where b.date_posted < '"+date_from+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.project_id = '"+proj_id+"' end) \n" +  
			"	and (case when '"+ph_code+"' = '' then a.acct_id is not null else a.sub_projectid = '"+ph_code+"' end) \n" + 
			"	) a ), \n\n" +

			//CRB
			"(select sum(a.crb_amt) as crb_amt_tot\r\n" + 
			"	from (select distinct on (rb_id, doc_id, acct_id) rb_id, doc_id, acct_id, sum(trans_amt) as crb_amt from rf_crb_detail \r\n" + 
			"	where status_id = 'A' and co_id = '"+co_id+"' " +
			"	group by rb_id, doc_id, acct_id) a\r\n" + 
			"	join (select * from rf_crb_header where status_id = 'P') b on a.rb_id = b.rb_id and a.doc_id = b.doc_id\r\n" + 
			"	where b.posted_date >= '"+date_from+"' and b.posted_date - interval '1 day' <= '"+date_to+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else b.proj_id = '"+proj_id+"' end) \n" +
			"	and (case when '"+ph_no+"' = '' then a.acct_id is not null else b.phase = '"+ph_no+"' end)  "	+
			"	), \n\n" +

			//CV-Debit
			"(coalesce((select sum(a.cv_amt) as cv_amt_deb_tot\r\n" + 
			"	from (select distinct on (cv_no, acct_id) cv_no, acct_id, sum(tran_amt) as cv_amt from rf_cv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'D' and co_id = '"+co_id+"' group by cv_no, acct_id) a\r\n" + 
			"	join rf_cv_header b on a.cv_no = b.cv_no \r\n" + 
			"	where b.date_posted >= '"+date_from+"' and b.date_posted - interval '1 day' <= '"+date_to+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.acct_id = '00' end) \n" +  //CV does not have project column
			"	and (case when '"+ph_no+"' = '' then a.acct_id is not null else a.acct_id = '00' end) \n" +    //CV does not have phase column
			"	),0) -  \n\n" + 

			//CV-Credit
			"coalesce((\r\n" + 
			"  select sum(a.cv_amt) as cv_amt_cre_tot\r\n" + 
			"	from (select distinct on (cv_no, acct_id) cv_no, acct_id, sum(tran_amt) as cv_amt from rf_cv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'C' and co_id = '"+co_id+"' group by cv_no, acct_id) a\r\n" + 
			"	join rf_cv_header b on a.cv_no = b.cv_no \r\n" + 
			"	where b.date_posted >= '"+date_from+"' and b.date_posted - interval '1 day' <= '"+date_to+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.acct_id = '00' end) \n" +  //CV does not have project column
			"	and (case when '"+ph_no+"' = '' then a.acct_id is not null else a.acct_id = '00' end) \n" +	   //CV does not have phase column
			"	),0)) as cv_amt , \r\n" +

			//PV-Debit
			"(coalesce((select sum(a.pv_amt) as pv_amt_deb_tot  \r\n" + 
			"	from (select distinct on (pv_no, acct_id, project_id, sub_projectid) pv_no, acct_id, project_id, sub_projectid, sum(tran_amt) as pv_amt from rf_pv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'D' and co_id = '"+co_id+"' group by pv_no, acct_id, project_id, sub_projectid) a\r\n" + 
			"	join rf_pv_header b on a.pv_no = b.pv_no \r\n" + 
			"	where b.date_posted >= '"+date_from+"' and b.date_posted - interval '1 day' <= '"+date_to+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.project_id = '"+proj_id+"' end) \n" +  
			"	and (case when '"+ph_code+"' = '' then a.acct_id is not null else a.sub_projectid = '"+ph_code+"' end) \n" + 
			"	),0) - \r\n" + 

			//PV-Credit
			"coalesce((select sum(a.pv_amt) as pv_amt_cre_tot  \r\n" + 
			"	from (select distinct on (pv_no, acct_id, project_id, sub_projectid) pv_no, acct_id, project_id, sub_projectid, sum(tran_amt) as pv_amt from rf_pv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'C' and co_id = '"+co_id+"' group by pv_no, acct_id, project_id, sub_projectid) a\r\n" + 
			"	join rf_pv_header b on a.pv_no = b.pv_no \r\n" + 
			"	where b.date_posted >= '"+date_from+"' and b.date_posted - interval '1 day' <= '"+date_to+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.project_id = '"+proj_id+"' end) \n" +  
			"	and (case when '"+ph_code+"' = '' then a.acct_id is not null else a.sub_projectid = '"+ph_code+"' end) \n" + 
			"	),0)) as pv_amt ,\n" +

			//JV-Debit
			"(coalesce((select sum(a.jv_amt) as jv_amt_deb_tot  \r\n" + 
			"	from (select distinct on (jv_no, acct_id, project_id, sub_projectid) jv_no, acct_id, project_id, sub_projectid, sum(tran_amt) as jv_amt from rf_jv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'D' and co_id = '"+co_id+"' group by jv_no, acct_id, project_id, sub_projectid) a\r\n" + 
			"	join rf_jv_header b on a.jv_no = b.jv_no \r\n" + 
			"	where b.date_posted >= '"+date_from+"' and b.date_posted - interval '1 day' <= '"+date_to+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.project_id = '"+proj_id+"' end) \n" +  
			"	and (case when '"+ph_code+"' = '' then a.acct_id is not null else a.sub_projectid = '"+ph_code+"' end) \n" + 
			"	),0) - \r\n" + 

			//JV-Credit
			"coalesce((select sum(a.jv_amt) as jv_amt_cre_tot  \r\n" + 
			"	from (select distinct on (jv_no, acct_id, project_id, sub_projectid) jv_no, acct_id, project_id, sub_projectid, sum(tran_amt) as jv_amt from rf_jv_detail \r\n" + 
			"	where status_id = 'A' and bal_side = 'C' and co_id = '"+co_id+"' group by jv_no, acct_id, project_id, sub_projectid) a\r\n" + 
			"	join rf_jv_header b on a.jv_no = b.jv_no \r\n" + 
			"	where b.date_posted >= '"+date_from+"' and b.date_posted - interval '1 day' <= '"+date_to+"'  \r\n" + 
			"	and (case when '"+proj_id+"' = '' then a.acct_id is not null else a.project_id = '"+proj_id+"' end) \n" +  
			"	and (case when '"+ph_code+"' = '' then a.acct_id is not null else a.sub_projectid = '"+ph_code+"' end) \n" + 
			"	),0)) as jv_amt " +
			") a \n\n" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				//listModel.addElement(modelMain.getRowCount());				
			}					
		}	

	}



	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Generate"))
		{ 
			generateTB(modelTB_details, rowHeaderTB_details, modelTB_details_total,status_id); 
			//generateTB_total(modelTB_details_total, null, null); 
			btnPreview.setEnabled(true);
			btnCancel.setEnabled(true);	 
			chkIncludeZeroBal.setEnabled(true);	
			chkIncludeActive.setEnabled(true);	
		}
		if(e.getActionCommand().equals("Cancel"))
		{ 			
			lblPhase.setEnabled(false);	
			lookupPhase.setEnabled(false);		
			tagPhase.setEnabled(false);	
			btnPreview.setEnabled(false);
			chkIncludeZeroBal.setEnabled(false);	
			chkIncludeActive.setEnabled(false);
			refresh_fields();

			show_zero = false;
			date_from = dte_from.getDate();
			date_to   = dte_to.getDate();
			proj_id   = "";
			ph_no     = "";
			ph_code   = "";
			proj_name = "";
			status_id = "P";

		}
		if(e.getActionCommand().equals("Preview")){
			preview();
			previewTB_withCostCenter();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}	

	public void preview(){
		String criteria = "Trial Balance";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);		
		mapParameters.put("prepared_by", UserInfo.FullName);	
		mapParameters.put("date_from", date_from);	
		mapParameters.put("date_to", date_to);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("phase", ph_code);	
		mapParameters.put("ph_code", ph_code);	
		mapParameters.put("ph_no", ph_no);	
		mapParameters.put("proj_id", proj_id);			
		mapParameters.put("show_zero", show_zero);	
		mapParameters.put("status_id", status_id);	
		mapParameters.put("include_month", include_month);	
		mapParameters.put("period_from", period_from);	
		mapParameters.put("period_to", period_to);

		FncReport.generateReport("/Reports/rptTB_preview.jasper", reportTitle, company, mapParameters);
		
	}

	public void previewTB_withCostCenter(){

		String criteria = "Trial Balance with Cost Centers";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);		
		mapParameters.put("prepared_by", UserInfo.FullName);	
		mapParameters.put("date_from", date_from);	
		mapParameters.put("date_to", date_to);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("phase", ph_code);	
		mapParameters.put("ph_code", ph_code);	
		mapParameters.put("ph_no", ph_no);	
		mapParameters.put("proj_id", proj_id);			
		mapParameters.put("show_zero", show_zero);	
		mapParameters.put("status_id", status_id);	
		mapParameters.put("include_month", include_month);	
		mapParameters.put("period_from", period_from);	
		mapParameters.put("period_to", period_to);

		FncReport.generateReport("/Reports/rptTB_preview_withCostCntr.jasper", reportTitle, company, mapParameters);
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}


	//components
	public void enable_fields(Boolean x){

		lblProject.setEnabled(x);	
		lookupProject.setEnabled(x);	
		tagProject.setEnabled(x);	

		lblPhase.setEnabled(x);	
		lookupPhase.setEnabled(x);	
		tagPhase.setEnabled(x);	

		lbl_date_fr.setEnabled(x);	
		dte_from.setEnabled(x);
		lblDate_to.setEnabled(x);
		dte_to.setEnabled(x);

		btnGenerate.setEnabled(x);
		btnPreview.setEnabled(x);

	}

	public void refresh_fields(){

		lookupProject.setValue("");
		tagProject.setTag("");
		lookupPhase.setValue("");
		tagPhase.setTag("");
		dte_from.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dte_to.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

		FncTables.clearTable(modelTB_details);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderTB_details.setModel(listModel);//Setting of DefaultListModel into rowHeader

		modelTB_details_total = new modelTB_generate_v2();
		modelTB_details_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00),  new BigDecimal(0.00), new BigDecimal(0.00),  new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

		tblTB_details_total = new _JTableTotal(modelTB_details_total, tblTB_details);
		tblTB_details_total.setFont(dialog11Bold);
		scrollTB_details_total.setViewportView(tblTB_details_total);
		((_JTableTotal) tblTB_details_total).setTotalLabel(0);

	}

	public void refresh_tables(){//used

		//reset table 1
		FncTables.clearTable(modelTB_details);	
		FncTables.clearTable(modelTB_details_total);			
		rowHeaderTB_details = tblTB_details.getRowHeader22();
		scrollTB_detail.setRowHeaderView(rowHeaderTB_details);	
		modelTB_details_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00),  
				new BigDecimal(0.00),  new BigDecimal(0.00),  new BigDecimal(0.00),  new BigDecimal(0.00), new BigDecimal(0.00) });
	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = sql_getCompanyLogo();	

		enable_fields(true);
		lookupProject.setLookupSQL(getProject());	

		lblPhase.setEnabled(false);	
		lookupPhase.setEnabled(false);	
		tagPhase.setEnabled(false);	

		btnPreview.setEnabled(false);
		btnCancel.setEnabled(true);
		lookupCompany.setValue(co_id);
	}


	//lookup
	public String getProject(){

		String sql = 
			"select a.proj_id as \"Project ID\", " +
			"a.proj_name as \"Project Name\", " +
			"a.proj_alias as \"Project Alias\", " +
			"b.sub_proj_id as \"SubProject ID\", " +
			"a.vatable as \"Vatable\" " +
			"from mf_project a " +
			"left join ( select distinct on (proj_id) proj_id, sub_proj_id from mf_unit_info ) b  on a.proj_id=b.proj_id " +
			"order by a.proj_id" ;
		return sql;

	}	

	public String getSubproject(){
		String sql = 
			"select \r\n" + 
			"distinct on (a.proj_id, a.sub_proj_id)\r\n" + 
			"\r\n" + 
			"a.sub_proj_id  as \"Subproj Code\",\r\n" + 
			"a.phase as \"Phase\",  \r\n" + 
			"a.proj_id as \"Proj Code\",\r\n" + 
			"b.proj_name as \"Proj Name\"  \r\n" + 

			"from mf_unit_info a\r\n" + 
			"left join mf_project b on a.proj_id = b.proj_id \r\n" + 
			"where co_id = '"+co_id+"' " +
			"and a.proj_id = '"+proj_id+"' " +
			"and a.sub_proj_id != ''   " +
			"order by a.sub_proj_id" ;		
		return sql;

	}	

	public String getChartofAccount(){

		String sql = "select " +
		"acct_id as \"Acct ID\", " +
		"acct_name as \"Acct Name\",    " +
		"bs_is as \"Balance\"  " +
		"from mf_boi_chart_of_accounts " +
		"where status_id = 'A' " +
		"and w_subacct is null " +
		"order by acct_id ";		
		return sql;

	}

	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = 
			"select company_logo from mf_company \n" + 
			"where co_id = '02' " ;

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {a = "";}
			else {a = (String) db.getResult()[0][0]; }

		}else{
			a = "";
		}

		return a;
	}


	//table
	public static void totalTB(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal beg_bal_deb 	= new BigDecimal(0.00);	
		BigDecimal crb_bd_deb 	= new BigDecimal(0.00);	
		BigDecimal cv_bd_deb 	= new BigDecimal(0.00);	
		BigDecimal pv_bd_deb 	= new BigDecimal(0.00);	
		BigDecimal jv_bd_deb 	= new BigDecimal(0.00);	
		BigDecimal per_tran_deb = new BigDecimal(0.00);
		BigDecimal end_bal_deb 	= new BigDecimal(0.00);
		
		BigDecimal beg_bal_cre 	= new BigDecimal(0.00);	
		BigDecimal crb_bd_cre 	= new BigDecimal(0.00);	
		BigDecimal cv_bd_cre 	= new BigDecimal(0.00);	
		BigDecimal pv_bd_cre 	= new BigDecimal(0.00);	
		BigDecimal jv_bd_cre 	= new BigDecimal(0.00);	
		BigDecimal per_tran_cre = new BigDecimal(0.00);
		BigDecimal end_bal_cre 	= new BigDecimal(0.00);

		for(int x=0; x < modelMain.getRowCount(); x++){		

			//DEBIT
			try { beg_bal_deb 	= beg_bal_deb.add(((BigDecimal) modelMain.getValueAt(x,2)));} 
			catch (NullPointerException e) { beg_bal_deb = beg_bal_deb.add(new BigDecimal(0.00)); }

			try { crb_bd_deb 	= crb_bd_deb.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { crb_bd_deb = crb_bd_deb.add(new BigDecimal(0.00)); }

			try { cv_bd_deb = cv_bd_deb.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { cv_bd_deb 	= cv_bd_deb.add(new BigDecimal(0.00)); }

			try { pv_bd_deb = pv_bd_deb.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { pv_bd_deb 	= pv_bd_deb.add(new BigDecimal(0.00)); }

			try { jv_bd_deb = jv_bd_deb.add(((BigDecimal) modelMain.getValueAt(x,10)));} 
			catch (NullPointerException e) { jv_bd_deb 	= jv_bd_deb.add(new BigDecimal(0.00)); }

			try { per_tran_deb = per_tran_deb.add(((BigDecimal) modelMain.getValueAt(x,12)));} 
			catch (NullPointerException e) { per_tran_deb = per_tran_deb.add(new BigDecimal(0.00)); }

			try { end_bal_deb = end_bal_deb.add(((BigDecimal) modelMain.getValueAt(x,14)));} 
			catch (NullPointerException e) { end_bal_deb = end_bal_deb.add(new BigDecimal(0.00)); }
			
			//CREDIT
			try { beg_bal_cre 	= beg_bal_cre.add(((BigDecimal) modelMain.getValueAt(x,3)));} 
			catch (NullPointerException e) { beg_bal_cre = beg_bal_cre.add(new BigDecimal(0.00)); }

			try { crb_bd_cre 	= crb_bd_cre.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { crb_bd_cre = crb_bd_cre.add(new BigDecimal(0.00)); }

			try { cv_bd_cre = cv_bd_cre.add(((BigDecimal) modelMain.getValueAt(x,7)));} 
			catch (NullPointerException e) { cv_bd_cre 	= cv_bd_cre.add(new BigDecimal(0.00)); }

			try { pv_bd_cre = pv_bd_cre.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { pv_bd_cre 	= pv_bd_cre.add(new BigDecimal(0.00)); }

			try { jv_bd_cre = jv_bd_cre.add(((BigDecimal) modelMain.getValueAt(x,11)));} 
			catch (NullPointerException e) { jv_bd_cre 	= jv_bd_cre.add(new BigDecimal(0.00)); }

			try { per_tran_cre = per_tran_cre.add(((BigDecimal) modelMain.getValueAt(x,13)));} 
			catch (NullPointerException e) { per_tran_cre = per_tran_cre.add(new BigDecimal(0.00)); }

			try { end_bal_cre = end_bal_cre.add(((BigDecimal) modelMain.getValueAt(x,15)));} 
			catch (NullPointerException e) { end_bal_cre = end_bal_cre.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, beg_bal_deb, beg_bal_cre, crb_bd_deb, crb_bd_cre, cv_bd_deb, cv_bd_cre, 
					pv_bd_deb, pv_bd_cre, jv_bd_deb, jv_bd_cre, per_tran_deb, per_tran_cre, end_bal_deb, end_bal_cre });
	}

	private static void adjustRowHeight(){		

		int rw = tblTB_details.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblTB_details.setRowHeight(x, 22);			
			x++;
		}
	}

	public static void computeRunningBal() {			

		Double debit 	= 0.00;	
		Double credit 	= 0.00;	
		Double runBal 	= 0.00;	

		for(int x=0; x < modelTB_details.getRowCount(); x++){		

			debit  = Double.parseDouble(tblTB_details.getValueAt(x,2).toString().replace(",",""));
			credit = Double.parseDouble(tblTB_details.getValueAt(x,3).toString().replace(",",""));
			runBal = runBal + debit - credit ;			

			modelTB_details.setValueAt(new BigDecimal(runBal), x, 4);
		}		


	}


	//open modules
	public static void openGL(){

		GeneralLedger gl = new GeneralLedger();
		Home_JSystem.addWindow(gl);

		if(co_id.equals(""))
		{

		} 
		else 
		{		

			int x = tblTB_details.getSelectedRow();
			String acct_id = tblTB_details.getValueAt(x,0).toString();
			String acct_name = tblTB_details.getValueAt(x,1).toString();
			Date date_from = (Date) dte_from.getDate();
			Date date_to = (Date) dte_to.getDate();

			GeneralLedger.co_id 		= co_id;	
			GeneralLedger.proj_id 		= proj_id;	
			GeneralLedger.ph_no 		= ph_code;	
			GeneralLedger.phase 		= ph_no;	
			GeneralLedger.company 		= company;	
			GeneralLedger.proj_name 	= proj_name;	
			GeneralLedger.co_id 		= co_id;
			GeneralLedger.company_logo 	= company_logo;
			GeneralLedger.acct_id 		= acct_id;
			GeneralLedger.acct_name		= acct_name;
			GeneralLedger.status_id 	= status_id;
			GeneralLedger.include_month = include_month;

			GeneralLedger.lookupCompany.setValue(co_id);
			GeneralLedger.tagCompany.setTag(company);	
			GeneralLedger.lookupProject.setLookupSQL(GeneralLedger.getProject());									
			GeneralLedger.lookupAccount.setLookupSQL(GeneralLedger.getChartofAccount());

			if (proj_id.equals("")){}
			else 
			{
				GeneralLedger.lookupProject.setText(proj_id);
				GeneralLedger.tagProject.setTag(proj_name);
				GeneralLedger.lookupProject.setEnabled(true);	
				GeneralLedger.tagProject.setEnabled(true);	
				GeneralLedger.lblProject.setEnabled(true);
				GeneralLedger.lookupPhase.setLookupSQL(GeneralLedger.getSubproject());
			}

			if (ph_code.equals("")){}
			else 
			{
				GeneralLedger.lookupPhase.setText(ph_code);			
				GeneralLedger.tagPhase.setTag(ph_no);
				GeneralLedger.lblPhase.setEnabled(true);	
				GeneralLedger.lookupPhase.setEnabled(true);	
				GeneralLedger.tagPhase.setEnabled(true);
			}


			GeneralLedger.enable_fields(true);
			GeneralLedger.lookupProject.setLookupSQL(GeneralLedger.getProject());									
			GeneralLedger.lookupAccount.setLookupSQL(GeneralLedger.getChartofAccount());
			GeneralLedger.btnPreview.setEnabled(false);
			GeneralLedger.btnCancel.setEnabled(true);			
			GeneralLedger.lookupAccount.setText(acct_id);
			GeneralLedger.tagAccount.setTag(acct_name);
			GeneralLedger.dte_from.setDate(date_from);
			GeneralLedger.dte_to.setDate(date_to);
			GeneralLedger.cmbInclude.setSelectedIndex(cmbInclude.getSelectedIndex());

			DefaultListModel listModel = new DefaultListModel();
			GeneralLedger.generateBeginningBalance(GeneralLedger.modelGL_details, GeneralLedger.rowHeaderGL_details, 
					GeneralLedger.modelGL_details_total, listModel); 
			//GeneralLedger.generateGL(GeneralLedger.modelGL_details, GeneralLedger.rowHeaderGL_details, GeneralLedger.modelGL_details_total, listModel); 
			GeneralLedger.btnPreview.setEnabled(true);
			GeneralLedger.btnCancel.setEnabled(true);	 
			GeneralLedger.computeRunningBal();

			if (status_id.equals("A")){GeneralLedger.chkIncludeActive.setSelected(true);} else {GeneralLedger.chkIncludeActive.setSelected(false);}


		}
	}

	public static void openJV_closeTB(String dte_fr, String dte_to){

		if(checkClosable(dte_fr, dte_to)==false)
		{JOptionPane.showMessageDialog(null, "Closing the selected period required that \n" +
				"active CRB and JV be posted first. \n" +
				"", "Not Yet Qualified", 
				JOptionPane.WARNING_MESSAGE);}
		else {		

			JournalVoucher jv = new JournalVoucher();
			if(Home_JSystem.isNotExisting("JournalVoucher")){
				//DRFprooflist drf_proof = new DRFprooflist();
				Home_JSystem.addWindow(jv);
			}		

			if(co_id.equals(""))
			{

			} 
			else 	
			{
				JournalVoucher.co_id = co_id;	
				JournalVoucher.company	= company;	
				JournalVoucher.lookupCompany.setValue(co_id);
				JournalVoucher.tagCompany.setTag(company);
				JournalVoucher.company_logo = company_logo;
				JournalVoucher.lblJV_no.setEnabled(true);	
				JournalVoucher.lookupJV.setEnabled(true);	
				JournalVoucher.lookupJV.setLookupSQL(JournalVoucher.getJV_no());						
				JournalVoucher.add();	
				JournalVoucher.displayJV_closeTB(JournalVoucher.modelJV_account, JournalVoucher.rowHeaderJV_account, 
						JournalVoucher.modelJV_accounttotal, dte_fr, dte_to);	
			}
		}
	}

	public static void closeTB(){

		int scanOption = JOptionPane.showOptionDialog(null, pnlCloseTB, "Select Period",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {	
				//selectDate();
			} catch ( java.lang.ArrayIndexOutOfBoundsException ex ) {}		

		} // CLOSED_OPTION		

	}

	public static Boolean checkClosable(String dte_fr, String dte_to){		

		Boolean isClosable = false;

		String SQL = 
			//"select count(rb_id) as count from (\n" + 
			"	select rb_id from rf_crb_header \n" +
			"		where status_id in ('A') \n" +
			"		and issued_date::date >= '"+dte_fr+"'::date \n" +
			"		and issued_date::date <= '"+dte_to+"'::date \n" +
			"		and co_id = '"+co_id+"' \n" + 
			"	union \n" + 
			"	select jv_no from rf_jv_header " +
			"		where status_id in ('A', 'F') \n" +
			"		and jv_date::date >= '"+dte_fr+"'::date \n" +
			"		and jv_date::date <= '"+dte_to+"'::date \n" + 
			"		and co_id = '"+co_id+"' \n" ;
			//") a" ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			isClosable = true;
		}else{
			isClosable = false;
		}

		return isClosable;		
	}

	
	//get Date
	private void getDate(){
		
		if (rbtnDate.isSelected()==true){
			date_from = dte_from.getDate();		
			date_to = dte_to.getDate();	
			period_from = null;
			period_to = null;
		}
		else {
			
			period_from = cmbPeriodFr.getSelectedIndex() + 1;
			if (cmbPeriodTo.getSelectedIndex()<15) {period_to = cmbPeriodTo.getSelectedIndex() + 1;} else {period_to = 99;}
			include_month 	= "";
			
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
			if(cmbPeriodFr.getSelectedIndex()==15){try {date_from = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}						
			if(cmbPeriodFr.getSelectedIndex()==16){try {date_from = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}					
			
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
			if(cmbPeriodTo.getSelectedIndex()==15){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}					
			if(cmbPeriodTo.getSelectedIndex()==16){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}						
		}
		
		
	}

	private static Boolean isClosed(String dateFr){		

		Boolean isClosed = false;

		String SQL = 
			"select jv_no from rf_jv_header where tran_id = '00030' \n" ;
		
			if (rbtnDate.isSelected()==true) {SQL = SQL + "	and fiscal_yr::text = (to_char('"+dte_from.getDate()+"'::date,'yyyy')::int)::text\n" ;}
			
			else{SQL = SQL + "	and fiscal_yr::text = '"+cmbYear.getSelectedItem()+"'::text \n";}
			
			SQL = SQL +
			"	and co_id = '"+co_id+"' and status_id = 'P';" ;			

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			isClosed = true;
		}else{
			isClosed = false;
		}

		return isClosed;		
	}

}