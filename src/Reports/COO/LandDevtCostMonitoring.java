package Reports.COO;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import tablemodel.model_LandDev_CostMonitoring;
import Accounting.Collections.CheckStatusMonitoring;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Projects.BiddingandAwarding._NoticeToProceed;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JCheckListItem;
import components._JCheckListRenderer;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class LandDevtCostMonitoring extends _JInternalFrame implements _GUI, ActionListener, MouseListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	protected static final Home_JSystem Home_JSystem = null;
	static String title = "Land Development Cost Monitoring";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlSouth;
	private JPanel pnlMainDtl_1;
	private JPanel pnlMainDtl_2;
	private JPanel pnlMainDtl_1a;
	private JPanel pnlMainDtl_1b;
	private JPanel pnlTable;
	private JPanel pnlMain_particular;
	private JPanel pnlMainInfo;
	private JPanel pnlMainInfo_1;
	private JPanel pnlMainDtl_2a;
	private JPanel pnlMainDtl_2b;
	private JPanel pnlMain_noBudget;
	private JPanel pnlDate;

	private static JLabel lblDateTagged;
	private static JLabel lblTotalArea;
	private static JLabel lblCompany;
	private static JLabel lblProject;
	private static JLabel lblPhase;
	private JLabel lblDate;

	public static _JLookup lookupCompany;
	private static _JLookup lookupProject;
	private static _JLookup lookupPhase1;
	private static _JLookup lookupPhase2;
	private static _JLookup lookupPhase3;

	private _JScrollPaneMain scrollDRF_part;
	private static _JScrollPaneTotal scrollDRF_part_total;
	private JScrollPane scpDRFJustif;	

	public static model_LandDev_CostMonitoring modelDRF_part;
	public static model_LandDev_CostMonitoring modelDRF_part_total;
	public static _JTableTotal tblDRF_part_total;	
	public static _JTableMain tblDRF_part;
	public static JList rowHeaderDRF;	
	private _JTabbedPane tabCenter;	

	public static _JTagLabel tagCompany;
	private static _JTagLabel tagProject;
	private static _JTagLabel tagPhase1;
	private static _JTagLabel tagPhase2;
	private static _JTagLabel tagPhase3;
	private _JTagLabel tagDetail;

	private JButton btnSave;
	public static JButton btnCancel;
	public static JButton btnAddNew;
	public static JButton btnRefresh;	
	public static JButton btnEdit;		
	private JButton btnOK;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private static _JDateChooser dteRequest;
	private static _JDateChooser dteNeeded;
	private _JDateChooser dteRefDate;
	private static _JDateChooser dteEdited;

	private static JXTextField txtStatus;	
	private static JTextArea txtDRFJustif;	
	private static JTextArea txtDRFRemark;

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JPopupMenu menu;	
	private JPopupMenu menu2;	
	public static JMenuItem mnidelete;
	public static JMenuItem mniaddrow;	
	public static JMenuItem mnisetupPV;
	private static JMenuItem mnicopy;
	private static JMenuItem mnipaste;

	static String rplf_no   = "";   //this is used to denote the RPLF number used for saving
	private String co_id 	= "";
	private String proj_id 	= "";
	private String proj_name= "";
	String availer_id 	= "";
	Double tax_rate		= 0.00;
	String tax_id 		= "";
	String landdev_no	= "";
	String pay_type_id	= "";
	String lineno 		= "";
	String type_id 		= "";
	String type_name 	= "";
	static String pay_type_name		= "";
	public static String company 	= "";
	public static String company_logo;
	Boolean is_payee_vatable 		= false;
	private JMenuItem mniwriteoff;
	private JMenuItem mniInactivate;
	private JMenuItem mniActivate;
	private JMenuItem mniEditAmount;
	private JPanel pnlEditAmount;
	private JLabel lblEditAmount;
	private _JXFormattedTextField txteditamount;
	private JPanel pnlMainDtl_south;
	private JPanel pnlMainDtl_south_1;
	private JPanel pnlMainDtl_south_2;
	private JScrollPane scrollPhase;
	private JList listPhase;
	private JScrollPane scpAddress;
	private JTextArea txtAddress;
	private JPanel pnlMainDtl_center;
	private JPanel pnlMainDtl_north;
	private JLabel lblSubject;
	private JPanel pnlMainDtl_center_1;
	private JPanel pnlMainDtl_center_2;
	private JPanel pnlMainDtl_center_3;
	private JPanel pnlMainDtl_south_1_a;
	private JPanel pnlNorthMain;
	private JPanel pnlMainDtl_south_1_2;
	private JPanel pnlMainDtl_south_1_2a;
	private _JLookup lookupSubject;
	private _JTagLabel tagSubject;
	private JPanel pnlMainDtl_south_1_2b;
	private JScrollPane scpAddress2;
	private JTextArea txtAddress2;
	private JLabel lblLandDev_no;
	private _JLookup lookupLandDev_no;
	private _JTagLabel tagStatus;
	private JButton btnNew;
	public static JButton btnPreview;	

	public LandDevtCostMonitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public LandDevtCostMonitoring(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public LandDevtCostMonitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(935, 538));
		this.setBounds(0, 0, 935, 538);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			menu = new JPopupMenu("Popup");
			mnidelete = new JMenuItem("Remove Row      ");
			mniaddrow = new JMenuItem("Add Row");
			mnicopy = new JMenuItem("Copy");
			mnipaste = new JMenuItem("Paste");			
			mniEditAmount = new JMenuItem("Edit Amount");			
			menu.add(mnidelete);
			menu.add(mniaddrow);	
			JSeparator sp = new JSeparator();				
			menu.add(sp);
			menu.add(mnicopy);

			mnipaste.setEnabled(false);
			mniEditAmount.setEnabled(true);

			mnidelete.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					removeRow();				
				}
			});
		}
		{
			/*pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 145));
			pnlNorth.addMouseListener(new PopupTriggerListener_panel2());*/

			pnlNorthMain = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlNorthMain, BorderLayout.NORTH);				
			pnlNorthMain.setPreferredSize(new java.awt.Dimension(923, 172));
			pnlNorthMain.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlNorthMain.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

			{
				pnlMainDtl_north = new JPanel(new BorderLayout(5, 5));
				pnlNorthMain.add(pnlMainDtl_north, BorderLayout.NORTH);	
				pnlMainDtl_north.setPreferredSize(new java.awt.Dimension(913, 60));

				{
					//Start of Left Panel 
					pnlMainInfo = new JPanel(new BorderLayout(0,0));
					pnlMainDtl_north.add(pnlMainInfo, BorderLayout.CENTER);
					pnlMainInfo.setPreferredSize(new java.awt.Dimension(620, 138));
					pnlMainInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						pnlMainInfo_1 = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlMainInfo.add(pnlMainInfo_1, BorderLayout.WEST);
						pnlMainInfo_1.setPreferredSize(new java.awt.Dimension(97, 60));
						pnlMainInfo_1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

						{
							lblCompany = new JLabel("Company", JLabel.TRAILING);
							pnlMainInfo_1.add(lblCompany);
							lblCompany.setEnabled(true);	
						}	
						{
							lblProject = new JLabel("Project", JLabel.TRAILING);
							pnlMainInfo_1.add(lblProject);
							lblProject.setEnabled(true);	
						}
					}


					pnlMainDtl_2 = new JPanel(new BorderLayout(5,0));
					pnlMainInfo.add(pnlMainDtl_2, BorderLayout.CENTER);
					pnlMainDtl_2.setPreferredSize(new java.awt.Dimension(203, 118));
					pnlMainDtl_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

					{
						pnlMainDtl_2a = new JPanel(new GridLayout(2, 1, 0, 5));
						pnlMainDtl_2.add(pnlMainDtl_2a, BorderLayout.WEST);
						pnlMainDtl_2a.setPreferredSize(new java.awt.Dimension(119, 119));
						pnlMainDtl_2a.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

						{
							lookupCompany = new _JLookup(null, "Company", 2, 2);
							pnlMainDtl_2a.add(lookupCompany);
							lookupCompany.setBounds(20, 27, 20, 25);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setEnabled(true);	
							lookupCompany.setLookupSQL(SQL_COMPANY());
							lookupCompany.setPreferredSize(new java.awt.Dimension(157, 22));
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){		
										co_id = (String) data[0];	
										String co_name = (String) data[1];	
										tagCompany.setTag(co_name);
									}
								}
							});	
						}
					}
				}		
				{
					lookupProject = new _JLookup(null, "Payee", 2, 2);
					pnlMainDtl_2a.add(lookupProject);
					lookupProject.setFilterName(true);
					lookupProject.setBounds(20, 27, 20, 25);
					lookupProject.setReturnColumn(0);					
					lookupProject.setEnabled(true);	
					lookupProject.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){	

								proj_id = (String) data[0];
								proj_name = (String) data[1];
								String project_alias = (String) data[2];

								tagProject.setText("["+ project_alias + " - " + proj_name + "]");

								listPhase.setModel(new DefaultComboBoxModel(_NoticeToProceed.getPhase("", co_id, proj_id).toArray()));
								txtAddress2.setText(sql_getProjectAddress());
								
								scrollPhase.setEnabled(true);
								txtAddress.setEnabled(true);
								txtAddress2.setEnabled(true);
								lookupSubject.setEnabled(true);	
							}
						}
					});	
				}

				pnlMainDtl_2b = new JPanel(new GridLayout(2, 1, 0, 5));
				pnlMainDtl_2.add(pnlMainDtl_2b, BorderLayout.CENTER);
				pnlMainDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
				pnlMainDtl_2b.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));

				{
					tagCompany = new _JTagLabel("[ ]");
					pnlMainDtl_2b.add(tagCompany);
					tagCompany.setBounds(209, 27, 700, 22);
					tagCompany.setEnabled(true);	
					tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
					//tagCompany.addMouseListener(new PopupTriggerListener_panel2());
					tagCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,11));
				}	
				{
					tagProject = new _JTagLabel("[ ]");
					pnlMainDtl_2b.add(tagProject);
					tagProject.setBounds(209, 27, 700, 22);
					tagProject.setEnabled(true);	
					tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
					tagProject.addMouseListener(new PopupTriggerListener_panel2());
					tagProject.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,11));
				}	

				//RIGHT PANEL
				{
					pnlMainDtl_1 = new JPanel(new BorderLayout(0, 5));
					pnlMainDtl_north.add(pnlMainDtl_1, BorderLayout.EAST);	
					pnlMainDtl_1.setPreferredSize(new java.awt.Dimension(238, 116));
					pnlMainDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					pnlMainDtl_1a = new JPanel(new GridLayout(2, 1, 0, 5));
					pnlMainDtl_1.add(pnlMainDtl_1a, BorderLayout.WEST);	
					pnlMainDtl_1a.setPreferredSize(new java.awt.Dimension(108, 116));
					pnlMainDtl_1a.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));	
					pnlMainDtl_1a.addMouseListener(new PopupTriggerListener_panel());

					{
						lblDateTagged = new JLabel("Date Tagged", JLabel.TRAILING);
						pnlMainDtl_1a.add(lblDateTagged);
						lblDateTagged.setEnabled(true);	
					}

					pnlMainDtl_1b = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlMainDtl_1.add(pnlMainDtl_1b, BorderLayout.CENTER);	
					pnlMainDtl_1b.setPreferredSize(new java.awt.Dimension(135, 119));
					pnlMainDtl_1b.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

					{
						dteRequest = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlMainDtl_1b.add(dteRequest);
						dteRequest.setBounds(485, 7, 125, 21);
						dteRequest.setDate(null);
						dteRequest.setEnabled(true);
						dteRequest.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dteRequest.getDateEditor()).setEditable(false);
						dteRequest.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
				}
			}

		}
		{
			pnlMainDtl_center = new JPanel(new BorderLayout(5, 5));
			pnlNorthMain.add(pnlMainDtl_center, BorderLayout.CENTER);	
			pnlMainDtl_center.setPreferredSize(new java.awt.Dimension(911, 71));

			{
				pnlMainDtl_center_1 = new JPanel(new BorderLayout(5, 5));
				pnlMainDtl_center.add(pnlMainDtl_center_1, BorderLayout.WEST);	
				pnlMainDtl_center_1.setPreferredSize(new java.awt.Dimension(102, 43));	

				{
					lblPhase = new JLabel("Phase", JLabel.TRAILING);
					pnlMainDtl_center_1.add(lblPhase);
					lblPhase.setPreferredSize(new java.awt.Dimension(99, 49));
					lblPhase.setEnabled(true);	
				}
			}
			{
				pnlMainDtl_center_2 = new JPanel(new BorderLayout(5, 5));
				pnlMainDtl_center.add(pnlMainDtl_center_2, BorderLayout.CENTER);	
				pnlMainDtl_center_2.setPreferredSize(new java.awt.Dimension(911, 63));

				{
					scrollPhase = new JScrollPane();
					pnlMainDtl_center_2.add(scrollPhase);
					scrollPhase.setName("scrollPhase");
					scrollPhase.setEnabled(false);
					scrollPhase.setBounds(90, 50, 130, 72);
					{
						listPhase = new JList();
						scrollPhase.setViewportView(listPhase);
						listPhase.setCellRenderer(new _JCheckListRenderer());
						listPhase.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						listPhase.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent event) {
								JList list = (JList) event.getSource();

								if(list.isEnabled()){
									// Get index of item clicked
									int index = list.locationToIndex(event.getPoint());
									_JCheckListItem item = (_JCheckListItem) list.getModel().getElementAt(index);									
									item.setSelected(!item.isSelected());
									list.repaint(list.getCellBounds(index, index));

									//Object[] listCode = new Object[4];

									ArrayList<String> listSelectedPhase = getSelectedPhase();
									if (listSelectedPhase.size() > 0) {										
										//listCode[1] = listSelectedPhase.toString().replaceAll("\\[|\\]", "").replace(", ", "-");
										txtAddress.setText(listSelectedPhase.toString());
									} 									
								}
							}
						});
					}
				}
			}
			{
				pnlMainDtl_center_3 = new JPanel(new GridLayout(1, 2, 0, 5));
				pnlMainDtl_center.add(pnlMainDtl_center_3, BorderLayout.EAST);	
				pnlMainDtl_center_3.setPreferredSize(new java.awt.Dimension(571, 63));	

				scpAddress = new JScrollPane();
				pnlMainDtl_center_3.add(scpAddress);
				scpAddress.setBounds(82, 7, 309, 61);
				scpAddress.setOpaque(true);
				scpAddress.setPreferredSize(new java.awt.Dimension(375, 159));

				{
					txtAddress = new JTextArea();
					scpAddress.add(txtAddress);
					scpAddress.setViewportView(txtAddress);
					txtAddress.setBounds(77, 3, 250, 81);
					txtAddress.setLineWrap(true);
					txtAddress.setPreferredSize(new java.awt.Dimension(366, 133));
					txtAddress.setEditable(false);
					txtAddress.setEnabled(false);
					txtAddress.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	

						}});	
				}	

				scpAddress2 = new JScrollPane();
				pnlMainDtl_center_3.add(scpAddress2);
				scpAddress2.setBounds(82, 7, 309, 61);
				scpAddress2.setOpaque(true);
				scpAddress2.setPreferredSize(new java.awt.Dimension(375, 159));

				{
					txtAddress2 = new JTextArea();
					scpAddress2.add(txtAddress2);
					scpAddress2.setViewportView(txtAddress2);
					txtAddress2.setBounds(77, 3, 250, 81);
					txtAddress2.setLineWrap(true);
					txtAddress2.setPreferredSize(new java.awt.Dimension(366, 133));
					txtAddress2.setEditable(false);
					txtAddress2.setEnabled(false);
					txtAddress2.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	

						}});	
				}	
			}
		}
		{
			pnlMainDtl_south = new JPanel(new BorderLayout(5, 5));
			pnlNorthMain.add(pnlMainDtl_south, BorderLayout.SOUTH);	
			pnlMainDtl_south.setPreferredSize(new java.awt.Dimension(918, 59));

			{
				//Start of Left Panel 
				pnlMainDtl_south_1 = new JPanel(new BorderLayout(0,0));
				pnlMainDtl_south.add(pnlMainDtl_south_1, BorderLayout.CENTER);
				pnlMainDtl_south_1.setPreferredSize(new java.awt.Dimension(703, 61));
				pnlMainDtl_south_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					pnlMainDtl_south_1_a = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlMainDtl_south_1.add(pnlMainDtl_south_1_a, BorderLayout.WEST);
					pnlMainDtl_south_1_a.setPreferredSize(new java.awt.Dimension(97, 59));
					pnlMainDtl_south_1_a.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

					{
						lblSubject = new JLabel("Subject", JLabel.TRAILING);
						pnlMainDtl_south_1_a.add(lblSubject);
						lblSubject.setEnabled(true);	
					}	

					{
						lblLandDev_no = new JLabel("Land Dev. No.", JLabel.TRAILING);
						pnlMainDtl_south_1_a.add(lblLandDev_no);
						lblLandDev_no.setEnabled(true);	
					}	

					pnlMainDtl_south_1_2 = new JPanel(new BorderLayout(5,0));
					pnlMainDtl_south_1.add(pnlMainDtl_south_1_2, BorderLayout.CENTER);
					pnlMainDtl_south_1_2.setPreferredSize(new java.awt.Dimension(203, 118));
					pnlMainDtl_south_1_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

					pnlMainDtl_south_1_2a = new JPanel(new GridLayout(2, 1, 0, 5));
					pnlMainDtl_south_1_2.add(pnlMainDtl_south_1_2a, BorderLayout.WEST);
					pnlMainDtl_south_1_2a.setPreferredSize(new java.awt.Dimension(119, 119));
					pnlMainDtl_south_1_2a.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

					{
						lookupSubject = new _JLookup(null, "Company", 2, 2);
						pnlMainDtl_south_1_2a.add(lookupSubject);
						lookupSubject.setBounds(20, 27, 20, 25);
						lookupSubject.setReturnColumn(0);
						lookupSubject.setEnabled(false);	
						lookupSubject.setLookupSQL(getSubject());
						lookupSubject.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupSubject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		
									type_id = (String) data[0];
									type_name = (String) data[1];
									tagSubject.setTag(type_name);
									lookupLandDev_no.setValue(sql_getNextLandDevno());
								}
							}
						});	

					}

					{
						lookupLandDev_no = new _JLookup(null, "Land Dev. No", 2, 2);
						pnlMainDtl_south_1_2a.add(lookupLandDev_no);
						lookupLandDev_no.setBounds(20, 27, 20, 25);
						lookupLandDev_no.setReturnColumn(0);
						lookupLandDev_no.setEnabled(true);	
						lookupLandDev_no.setLookupSQL(getSubject());
						lookupLandDev_no.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupLandDev_no.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		
									
									landdev_no = data[0].toString();
									proj_id =(String) data[1];
									String proj_alias =(String) data[2];
									proj_name =(String) data[3];
									type_id =(String) data[4];
									type_name =(String) data[5];
																		
									lookupProject.setText(proj_id);
									tagProject.setText("["+ proj_alias + " - " + proj_name + "]");
									txtAddress2.setText(sql_getProjectAddress());
									lookupSubject.setText(type_id);
									tagSubject.setText(type_name);
									
									dteRequest.setDate((Date) data[6]);
									displayLandev_details(modelDRF_part, rowHeaderDRF, modelDRF_part_total );	
									setButtonStatus(false, true, false, true, true, true);
								}
							}
						});	

					}

					pnlMainDtl_south_1_2b = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlMainDtl_south_1_2.add(pnlMainDtl_south_1_2b, BorderLayout.CENTER);
					pnlMainDtl_south_1_2b.setPreferredSize(new java.awt.Dimension(682, 28));
					pnlMainDtl_south_1_2b.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));

					{
						tagSubject = new _JTagLabel("[ ]");
						pnlMainDtl_south_1_2b.add(tagSubject);
						tagSubject.setBounds(209, 27, 700, 22);
						tagSubject.setEnabled(true);	
						tagSubject.setPreferredSize(new java.awt.Dimension(672, 18));
						tagSubject.addMouseListener(new PopupTriggerListener_panel2());
						tagSubject.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,11));
					}	
					{
						tagStatus = new _JTagLabel("[ ]");
						pnlMainDtl_south_1_2b.add(tagStatus);
						tagStatus.setBounds(209, 27, 700, 22);
						tagStatus.setEnabled(true);	
						tagStatus.setPreferredSize(new java.awt.Dimension(672, 18));
						tagStatus.addMouseListener(new PopupTriggerListener_panel2());
						tagStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,11));
					}	
				}

				pnlMainDtl_south_2 = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlMainDtl_south.add(pnlMainDtl_south_2, BorderLayout.EAST);
				pnlMainDtl_south_2.setPreferredSize(new java.awt.Dimension(210, 28));
				pnlMainDtl_south_2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

			}
		}
		{
			pnlDate= new JPanel();
			pnlDate.setLayout(null);
			pnlDate.setPreferredSize(new java.awt.Dimension(265, 80));
			{
				lblDate = new JLabel();
				pnlDate.add(lblDate);
				lblDate.setBounds(5, 15, 160, 20);
				lblDate.setText("Reference Doc. Date :");
			}
			{
				dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlDate.add(dteRefDate);
				dteRefDate.setBounds(130, 15, 125, 21);
				dteRefDate.setDate(null);
				dteRefDate.setEnabled(true);
				dteRefDate.setDateFormatString("yyyy-MM-dd");
				((JTextFieldDateEditor)dteRefDate.getDateEditor()).setEditable(false);
				dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				dteRefDate.addPropertyChangeListener( new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
					}
				});	
			}
			{
				btnOK = new JButton();
				pnlDate.add(btnOK);
				btnOK.setBounds(95, 58, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
						optionPaneWindow.dispose();
					}
				});
			}
		}
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlMain = new JPanel();
			pnlTable.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlMain.setBorder(lineBorder);		
			pnlMain.addMouseListener(new PopupTriggerListener_panel());


			tabCenter = new _JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);


			{			
				pnlMain_particular = new JPanel(new BorderLayout());
				tabCenter.addTab("Estimated Cost", null, pnlMain_particular, null);
				pnlMain_particular.setPreferredSize(new java.awt.Dimension(1183, 365));	

				{
					scrollDRF_part = new _JScrollPaneMain();//XXX
					pnlMain_particular.add(scrollDRF_part, BorderLayout.CENTER);
					{
						modelDRF_part = new model_LandDev_CostMonitoring();

						tblDRF_part = new _JTableMain(modelDRF_part);
						scrollDRF_part.setViewportView(tblDRF_part);
						tblDRF_part.addMouseListener(this);	
						tblDRF_part.setSortable(false);
						tblDRF_part.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								//tblDRF_part.packAll();
								computeEstimatedCost();
							}							
							public void keyPressed(KeyEvent e) {
								//tblDRF_part.packAll();
								computeEstimatedCost();
							}

						}); 
						tblDRF_part.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblDRF_part.rowAtPoint(e.getPoint()) == -1){
									tblDRF_part_total.clearSelection();
								}else{
									tblDRF_part.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblDRF_part.rowAtPoint(e.getPoint()) == -1){
									tblDRF_part_total.clearSelection();
								}else{
									tblDRF_part.setCellSelectionEnabled(true);
								}
							}
						});

						tblDRF_part.addKeyListener(this);
						tblDRF_part.getColumnModel().getColumn(0).setPreferredWidth(20);
						tblDRF_part.getColumnModel().getColumn(1).setPreferredWidth(20);
						tblDRF_part.getColumnModel().getColumn(2).setPreferredWidth(20);
						tblDRF_part.getColumnModel().getColumn(3).setPreferredWidth(20);
						tblDRF_part.getColumnModel().getColumn(4).setPreferredWidth(20);
						tblDRF_part.getColumnModel().getColumn(5).setPreferredWidth(350);
						tblDRF_part.getColumnModel().getColumn(6).setPreferredWidth(50);
						tblDRF_part.getColumnModel().getColumn(7).setPreferredWidth(50);
						tblDRF_part.getColumnModel().getColumn(8).setPreferredWidth(100);
						tblDRF_part.getColumnModel().getColumn(9).setPreferredWidth(100);
						tblDRF_part.getColumnModel().getColumn(10).setPreferredWidth(100);
						tblDRF_part.getColumnModel().getColumn(11).setPreferredWidth(100);
						tblDRF_part.getColumnModel().getColumn(12).setPreferredWidth(100);
						tblDRF_part.getColumnModel().getColumn(13).setPreferredWidth(100);

						tblDRF_part.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								try {
									if(!arg0.getValueIsAdjusting()){

										//lineno = (String) modelDRF_part.getValueAt(tblDRF_part.getSelectedRow(), 1);
										//generateDetail(lineno);
									}
								} catch (ArrayIndexOutOfBoundsException e) { }
							}
						});
						tblDRF_part.putClientProperty("terminateEditOnFocusLost", true);
					}
					{
						rowHeaderDRF = tblDRF_part.getRowHeader22();
						scrollDRF_part.setRowHeaderView(rowHeaderDRF);
						scrollDRF_part.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollDRF_part_total = new _JScrollPaneTotal(scrollDRF_part);
					pnlMain_particular.add(scrollDRF_part_total, BorderLayout.SOUTH);
					{
						modelDRF_part_total = new model_LandDev_CostMonitoring();
						modelDRF_part_total.addRow(new Object[] {  
								null, null, null, null,  
								null, "Total", null, null,
								new BigDecimal(0.00), new BigDecimal(0.00),
								new BigDecimal(0.00), new BigDecimal(0.00),
								new BigDecimal(0.00), new BigDecimal(0.00)});

						tblDRF_part_total = new _JTableTotal(modelDRF_part_total, tblDRF_part);
						tblDRF_part_total.setFont(dialog11Bold);
						scrollDRF_part_total.setViewportView(tblDRF_part_total);
						((_JTableTotal) tblDRF_part_total).setTotalLabel(5);
					}
				}

				createDRFtable(modelDRF_part, rowHeaderDRF, modelDRF_part_total);	
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new Dimension(55, 30));
			{
				JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));

				{
					btnNew = new JButton("New");
					pnlSouthCenter.add(btnNew);
					btnNew.setActionCommand("Add");
					btnNew.addActionListener(this);
					btnNew.setEnabled(true);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenter.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenter.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}				
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenter.add(btnPreview);
					btnPreview.setActionCommand("Preview");	
					btnPreview.setEnabled(false);
					btnPreview.addActionListener(this);
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlSouthCenter.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");	
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenter.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
			{			
				pnlMain_noBudget = new JPanel(new BorderLayout());
				tabCenter.addTab("Awarded Cost", null, pnlMain_noBudget, null);
				pnlMain_noBudget.setPreferredSize(new java.awt.Dimension(1183, 365));	

				scpDRFJustif = new JScrollPane();
				pnlMain_noBudget.add(scpDRFJustif);
				scpDRFJustif.setBounds(82, 7, 150, 61);
				scpDRFJustif.setOpaque(true);
				scpDRFJustif.setPreferredSize(new java.awt.Dimension(100, 285));

				{
					txtDRFJustif = new JTextArea();
					scpDRFJustif.add(txtDRFJustif);
					scpDRFJustif.setViewportView(txtDRFJustif);
					txtDRFJustif.setBounds(77, 3, 250, 81);
					txtDRFJustif.setLineWrap(true);
					txtDRFJustif.setPreferredSize(new java.awt.Dimension(100, 133));
					txtDRFJustif.setEditable(false);
					txtDRFJustif.setEnabled(true);	
					txtDRFJustif.setText("");	
				}			
			}
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();

		{
			pnlEditAmount= new JPanel();
			pnlEditAmount.setLayout(null);
			pnlEditAmount.setPreferredSize(new java.awt.Dimension(265, 80));
			{
				lblEditAmount = new JLabel();
				pnlEditAmount.add(lblEditAmount);
				lblEditAmount.setBounds(5, 15, 160, 20);
				lblEditAmount.setText("Amount / Percent :");
			}
			{
				txteditamount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
				pnlEditAmount.add(txteditamount);
				txteditamount.setFormatterFactory(_JXFormattedTextField.DECIMAL2);
				txteditamount.setText("0.0000");
				txteditamount.setEnabled(true);
				txteditamount.setEditable(true);
				txteditamount.setBounds(130, 15, 125, 21);
				//txteditamount.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer2());
			}
			{
				btnOK = new JButton();
				pnlEditAmount.add(btnOK);
				btnOK.setBounds(95, 58, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlEditAmount);						
						optionPaneWindow.dispose();	
						//computeBillAmounts2();

					}
				});
			}
		}
	}


	//display tables
	private void displayLandev_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"---------display Land Development details\r\n" + 
			"select \r\n" + 
			"(case when a.h2 != '' then '' else a.h1 end) as h1,\r\n" + 
			"(case when a.h3 != '' then '' else a.h2 end) as h2,\r\n" + 
			"(case when a.h4 != '' then '' else a.h3 end) as h3,\r\n" + 
			"(case when a.h5 != '' then '' else a.h4 end) as h4,\r\n" + 
			"a.h5, " + 
			"(case when a.h5 != '' 	 then '                    '||a.description else\r\n" + 
			"	case when a.h4 != '' then '               '||a.description else\r\n" + 
			"	case when a.h3 != '' then '          '||a.description else\r\n" + 
			"	case when a.h2 != '' then '     '||a.description else\r\n" + 
			"	case when a.h1 != '' then a.description end end end end end) as description,\r\n" + 
			"a.unit,\r\n" + 
			"a.quantity,\r\n" + 
			"a.matl_unit_cost,\r\n" + 
			"a.matl_total_cost,\r\n" + 
			"a.labor_unit_cost,\r\n" + 
			"a.labor_total_cost,\r\n" + 
			"a.total_unit_cost,\r\n" + 
			"a.total_total_cost\r\n" + 
			"\r\n" + 
			"from rf_landdev_monitoring_cost_detail a\r\n" + 
			"\r\n" + 
			"where a.land_dev_no = "+landdev_no+"\r\n" + 
			"and a.status_id = 'A' " ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalDRF(modelMain, modelTotal);			
		}		

		else {
			modelDRF_part_total = new model_LandDev_CostMonitoring();
			modelDRF_part_total.addRow(new Object[] {  
					null, null, null, null,  
					null, "Total", null, null,
					new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00)});

			tblDRF_part_total = new _JTableTotal(modelDRF_part_total, tblDRF_part);
			tblDRF_part_total.setFont(dialog11Bold);
			scrollDRF_part_total.setViewportView(tblDRF_part_total);
			((_JTableTotal) tblDRF_part_total).setTotalLabel(0);}

		//tblDRF_part.packAll();
		adjustRowHeight();
		

	}	


	private void createDRFtable(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00   union all    \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, 0.00, 0.00, 0.00, 0.00     \r\n" + 
			" " ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalDRF(modelMain, modelTotal);			
		}		

		else {
			modelDRF_part_total = new model_LandDev_CostMonitoring();
			modelDRF_part_total.addRow(new Object[] {  "Total", null, null, null, null, null, null, null, new BigDecimal(0.00),
					null, null, null, null, null, null, null, null, null, null, null, null,  null,
					new BigDecimal(0.00), null, null,  null,  new BigDecimal(0.00),
					new BigDecimal(0.00),  new BigDecimal(0.00), new BigDecimal(0.00),  new BigDecimal(0.00), 
					new BigDecimal(0.00), null });

			tblDRF_part_total = new _JTableTotal(modelDRF_part_total, tblDRF_part);
			tblDRF_part_total.setFont(dialog11Bold);
			scrollDRF_part_total.setViewportView(tblDRF_part_total);
			((_JTableTotal) tblDRF_part_total).setTotalLabel(0);}

		//tblDRF_part.packAll();
		modelDRF_part.setEditable(true);
		adjustRowHeight();
	}	

	public static Object [] getLanddevdetails() {

		String strSQL = 
				"select \r\n" + 
						"a.rplf_no,\r\n" + 
						"a.rplf_date,\r\n" + 
						"a.date_needed,\r\n" + 
						"coalesce(b.pv_no,''),\r\n" + 
						"a.rplf_type_id,\r\n" + 
						"trim(c.rplf_type_desc),\r\n" + 
						"a.entity_id1,				--6\r\n" + 
						"a.entity_id2,				--7\r\n" + 
						"trim(d.entity_name),		--8\r\n" + 
						"trim(coalesce(e.entity_name,'')),		--9\r\n" + 
						"a.entity_type_id,			--10\r\n" + 
						"trim(f.entity_type_desc),	--11\r\n" + 
						"( case when a.status_id = 'I' then 'INACTIVE' else \r\n" + 
						"	( case when a.status_id = 'D' then 'DELETED' else \r\n" + 
						"	( case when a.status_id = 'A' and b.pv_no is null then 'ACTIVE' else \r\n" + 
						"	( case when a.status_id = 'A' and b.pv_no is not null then 'PROCESSED' else '' end) end) end) end) as status, " +
						"trim(a.remarks),		    --13  \n" +
						"trim(a.justification), 	--14  \n" + 
						"a.date_edited, 			--15  \n" + 
						"a.pay_type_id, \n" +
						"(case when a.pay_type_id = 'B' then 'Check' else 'Cash' end) as pay_type  	\n" +	

			"from rf_request_header a\r\n" + 
			"left join (select * from rf_pv_header where status_id != 'I') b on a.rplf_no = b.rplf_no and a.co_id = b.co_id \r\n" + 
			"left join mf_rplf_type c on a.rplf_type_id = c.rplf_type_id\r\n" + 
			"left join rf_entity d on a.entity_id1 = d.entity_id\r\n" + 
			"left join rf_entity e on a.entity_id2 = e.entity_id\r\n" + 
			"left join mf_entity_type f on a.entity_type_id = f.entity_type_id\r\n" + 
			"left join mf_record_status g on a.status_id = g.status_id\r\n" + 
			"where a.co_id = '02' and a.rplf_no = ''  " ;

		System.out.printf("SQL #1 getDRFdetails: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	
	
	

	//Enable/Disable all components inside JPanel
	public static void setComponentEnabled(JPanel panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			comp.setEnabled(enable);
		}
	}

	private void enable_fields(Boolean x){

		txtStatus.setEnabled(x);	
		lblDateTagged.setEnabled(x);
		dteRequest.setEnabled(x);
		dteNeeded.setEnabled(x);
		lblTotalArea.setEnabled(x);	

		lblCompany.setEnabled(x);	
		lookupCompany.setEnabled(x);	
		tagCompany.setEnabled(x);	

		lblProject.setEnabled(x);	
		lookupProject.setEnabled(x);	
		tagProject.setEnabled(x);	

		lblPhase.setEnabled(x);	
		lookupPhase1.setEnabled(x);	
		tagPhase1.setEnabled(x);	
		lookupPhase3.setEnabled(x);	
		tagPhase3.setEnabled(x);	
		lookupPhase2.setEnabled(x);	
		tagPhase2.setEnabled(x);	
		txtDRFRemark.setEditable(x);
		txtDRFJustif.setEditable(x);
		mnicopy.setEnabled(x);

	}

	private void refresh_fields(){

		lookupCompany.setValue("");
		lookupProject.setValue("");
		lookupPhase1.setValue("");
		lookupPhase2.setValue("");
		lookupPhase3.setValue("");

		txtStatus.setText("");		
		tagCompany.setText("[ ]");
		tagProject.setText("[ ]");
		tagPhase1.setText("[ ]");
		tagPhase2.setText("[ ]");
		tagPhase3.setText("[ ]");
		dteRequest.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dteNeeded.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dteEdited.setDate(null);

		txtDRFRemark.setText("");	
		txtDRFJustif.setText("");	

	}

	private void refresh_tablesMain(){
		//reset table 1
		FncTables.clearTable(modelDRF_part);
		FncTables.clearTable(modelDRF_part_total);			
		rowHeaderDRF = tblDRF_part.getRowHeader22();
		scrollDRF_part.setRowHeaderView(rowHeaderDRF);	
		modelDRF_part_total.addRow(new Object[] { "Total", null , null, null, null, null, null, null, new BigDecimal(0.00),
				null, null, null, null, null, null, null, null, null, null, null, null,  null,
				new BigDecimal(0.00), null, null,  null,  new BigDecimal(0.00),
				new BigDecimal(0.00),  new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
				new BigDecimal(0.00), new BigDecimal(0.00), null });

	}

	private void initialize_comp(){

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = sql_getCompanyLogo();		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupLandDev_no.setLookupSQL(getLandDev_no());
		
		lookupCompany.setValue(co_id);
		setButtonStatus(true, false, false, false, false, false);
	}

	private void setButtonStatus(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f){
		btnNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);
		btnPreview.setEnabled(d);
		btnRefresh.setEnabled(e);
		btnCancel.setEnabled(f);
	}

	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Refresh")){ refresh(); }

		if(e.getActionCommand().equals("Cancel")){ cancel(); }

		if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "1")==true ) { add(); }
		else if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "1")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create new request manually.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "1")==true) { edit(); }
		else if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "1")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit request.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Save")) { save(); }

		if(e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "1")==true ){	preview();}
		else if(e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "1")==false )
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print RPLF.","Information",JOptionPane.INFORMATION_MESSAGE); }

	}

	public void mouseClicked(MouseEvent evt) {

		/*int row = tblDRF_part.convertRowIndexToModel(tblDRF_part.getSelectedRow());

		if ((evt.getClickCount() >= 2)) {
			clickTableColumn();
		}
		else if ((evt.getClickCount() == 1) && row!= 20) {
			clickTableColumn2();
		}
		else if ((evt.getClickCount() == 1) && row== 20) {
			//if (evt.getSource().equals(tblDRF_part)) {
			for(int x = 0; x < modelDRF_part.getRowCount(); x++){
				Boolean isSelected1 = (Boolean) modelDRF_part.getValueAt(x, 21);

				if (isSelected1) {
					if (tax_rate>=0.00) 
					{
						modelDRF_part.setValueAt(new BigDecimal(tax_rate), x, 25);	
					}
				}else{
					modelDRF_part.setValueAt(new BigDecimal(0.00), x, 25);
				}
			}
		}
		if (evt.getSource().equals(tblDRF_part)) {
			for(int x = 0; x < modelDRF_part.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelDRF_part.getValueAt(x, 20);
				if (isSelected) {
					modelDRF_part.setValueAt(new BigDecimal(12.00), x, 23);
				}else{
					modelDRF_part.setValueAt(new BigDecimal(0.00), x, 23);
				}
				computeEstimatedCost();
			}
		}

		if (evt.getSource().equals(tblDRF_part)) {
			//int row = tblDRF_part.convertRowIndexToModel(tblDRF_part.getSelectedRow());



		}*/
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

	public class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				if(lookupCompany.getText().equals("02")||lookupCompany.getText().equals("05")||lookupCompany.getText().equals("12")){
					mniwriteoff.setEnabled(true);
				} else {mniwriteoff.setEnabled(false);}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				if(lookupCompany.getText().equals("02")||lookupCompany.getText().equals("05")||lookupCompany.getText().equals("12")){
					mniwriteoff.setEnabled(true);
				} else {mniwriteoff.setEnabled(false);}
			}
		}
	}

	public void refresh(){

		btnRefresh.setEnabled(true);		
		btnCancel.setEnabled(true);		
		btnSave.setEnabled(false);		
		mnidelete.setEnabled(false);
		mniaddrow.setEnabled(false);
		tabCenter.setSelectedIndex(0);

		if(isPVcreated()==true&&txtStatus.getText().equals("PROCESSED")) 
		{
			btnEdit.setEnabled(false); 
			mnisetupPV.setEnabled(false);
			mniInactivate.setEnabled(false);
			mniActivate.setEnabled(false);
		} 
		else if(txtStatus.getText().equals("DELETED")||txtStatus.getText().equals("INACTIVE")) 
		{
			btnEdit.setEnabled(false); 
			mnisetupPV.setEnabled(false);
			mniInactivate.setEnabled(false);
			mniActivate.setEnabled(true);
		} 
		else 
		{
			btnEdit.setEnabled(true); 
			mnisetupPV.setEnabled(true);	
			mniInactivate.setEnabled(true);
			mniActivate.setEnabled(false);
		}

		mniwriteoff.setEnabled(true);
		JOptionPane.showMessageDialog(getContentPane(),"Data refreshed.","Information",JOptionPane.INFORMATION_MESSAGE);

		tagDetail.setText(null);

	}

	public void cancel(){

		if(!btnSave.isEnabled())  {
			enable_fields(false);
			refresh_tablesMain();
			refresh_fields();
			btnAddNew.setEnabled(true);
			mnidelete.setEnabled(false);
			mnisetupPV.setEnabled(false);
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnRefresh.setEnabled(false);
			btnCancel.setEnabled(false);
			mnidelete.setEnabled(false);
			mniaddrow.setEnabled(false);
			tabCenter.setSelectedIndex(0);
			mnipaste.setEnabled(false);
			mniwriteoff.setEnabled(false);
		} 
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				enable_fields(false);
				refresh_tablesMain();
				refresh_fields();
				btnAddNew.setEnabled(true);
				mnidelete.setEnabled(false);
				mnisetupPV.setEnabled(false);
				btnEdit.setEnabled(false);
				btnSave.setEnabled(false);
				btnRefresh.setEnabled(false);
				btnCancel.setEnabled(false);
				mnipaste.setEnabled(false);
				mniwriteoff.setEnabled(false);
			}}	

		//lookupDRF_no.setEditable(true);	

		tagDetail.setText(null);
	}

	public void add(){

		setButtonStatus(false, false, true, false, false, true);		
		
	}

	public void edit(){

		enable_fields(true);
		btnEdit.setEnabled(false);
		btnCancel.setEnabled(true);
		btnSave.setEnabled(true);
		btnPreview.setEnabled(false);
		btnEdit.setEnabled(false);
		btnRefresh.setEnabled(false);
		txtStatus.setText("ACTIVE");

		lookupCompany.setLookupSQL(getSubject());
		mnidelete.setEnabled(true);mniaddrow.setEnabled(true);		
		modelDRF_part.setEditable(true);

		lblDateTagged.setEnabled(false);	
		dteRequest.setEnabled(false);
		mnisetupPV.setEnabled(true);
		mniwriteoff.setEnabled(false);
		tagDetail.setText(null);

	}

	public void save(){

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete details.", "Incomplete Detail", 
				JOptionPane.WARNING_MESSAGE);}
		else {executeSave();}
	}

	public void preview(){	

		String criteria = "Request for Payment";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());			

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("land_dev_no", landdev_no);
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("location", txtAddress2.getText());
		mapParameters.put("subject_name", type_name);

		FncReport.generateReport("/Reports/rptCOO_LandDev.jasper", reportTitle, company, mapParameters);

		//if (checkIfHasWtax()==true){print2307();}

	}

	private void executeSave(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			landdev_no = sql_getNextLandDevno();
			pgUpdate db = new pgUpdate();	
			insertLandDev_header(db);				
			insertLandDev_detail(db);
			db.commit();	
			JOptionPane.showMessageDialog(getContentPane(),"New Land Development Estimates saved.","Information",JOptionPane.INFORMATION_MESSAGE);

			setButtonStatus(false, true, false, true, true, true);
		}

	}


	//select, lookup and get statements	
	private String getLandDev_no(){

		String sql = 
			"select \r\n" + 
			"a.land_dev_no, \r\n" + 
			"a.proj_id, \r\n" + 
			"b.proj_alias, \r\n" + 
			"b.proj_name,\r\n" + 
			"a.type_id, \r\n" + 
			"c.type_desc,\r\n" + 
			"a.date_created\r\n" + 
			"\r\n" + 
			"from rf_landdev_monitoring_cost_main a\r\n" + 
			"left join mf_project b on a.proj_id = b.proj_id\r\n" + 
			"left join mf_ntp_type c on a.type_id = c.type_id\r\n" + 
			"\r\n" ;		
		return sql;

	}
		
	private String getSubject(){

		String sql = 
			"select " +
			"type_id as \"Type ID\", " +
			"type_desc as \"Type Desc\", " +
			"type_alias \"Type Alias\" " +
			"from mf_ntp_type order by type_desc";		
		return sql;

	}

	private String sql_getCompanyLogo() {

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

	private String sql_getProjectAddress() {

		String a = "";

		String SQL = 
			"select address from mf_project where proj_id = '"+proj_id+"' " ;

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

	private String sql_getNextLandDevno() {

		landdev_no = "";

		String SQL = 
			"select (max(coalesce(land_dev_no,0))+1)::char from rf_landdev_monitoring_cost_main where status_id != 'I' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {landdev_no = "1";}
			else {landdev_no = (String) db.getResult()[0][0]; }

		}else{
			landdev_no = "1";
		}

		return landdev_no;
	}


	//check and validate		
	private Boolean checkCompleteDetails(){

		boolean x = false;		

		String phase;

		proj_id		 = lookupProject.getText();
		type_id		 = lookupSubject.getText();
		phase 		 = txtAddress.getText();	
		landdev_no	 = txtAddress.getText();	

		if (proj_id.equals("") || type_id.equals("") ||phase.equals("")||landdev_no.equals("")) {x=false;} 
		else {x=true;}			

		return x;
	}

	private Boolean isPVcreated(){

		Boolean isPVcreated = false;

		String SQL = 
			"select trim(status_id) from rf_pv_header " +
			"where  ( 'A', 'F', 'P' ) and co_id = '"+co_id+"' " ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			isPVcreated = true;
		}else{
			isPVcreated = false;
		}

		return isPVcreated;

	}



	//table operations
	private void totalDRF(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal amt_bd 		= new BigDecimal(0.00);	
		BigDecimal gr_amt_bd 	= new BigDecimal(0.00);	
		BigDecimal wtax_amt_bd	= new BigDecimal(0.00);	
		BigDecimal vat_amt_bd 	= new BigDecimal(0.00);	
		BigDecimal exp_amt_bd 	= new BigDecimal(0.00);
		BigDecimal pv_amt_bd 	= new BigDecimal(0.00);

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { amt_bd 	= amt_bd.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { amt_bd 	= amt_bd.add(new BigDecimal(0.00)); }

			try { gr_amt_bd 	= gr_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { gr_amt_bd 	= gr_amt_bd.add(new BigDecimal(0.00)); }

			try { wtax_amt_bd 	= wtax_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,10)));} 
			catch (NullPointerException e) { wtax_amt_bd 	= wtax_amt_bd.add(new BigDecimal(0.00)); }

			try { vat_amt_bd 	= vat_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,11)));} 
			catch (NullPointerException e) { vat_amt_bd 	= vat_amt_bd.add(new BigDecimal(0.00)); }

			try { exp_amt_bd 	= exp_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,12)));} 
			catch (NullPointerException e) { exp_amt_bd 	= exp_amt_bd.add(new BigDecimal(0.00)); }			

			try { pv_amt_bd 	= pv_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,13)));} 
			catch (NullPointerException e) { pv_amt_bd = pv_amt_bd.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] {null, null, null, null, null,  "Total", null, null, amt_bd,
				gr_amt_bd, wtax_amt_bd,	vat_amt_bd, exp_amt_bd, pv_amt_bd});
	}

	private void clickTableColumn() {
		int column = tblDRF_part.getSelectedColumn();
		int row = tblDRF_part.getSelectedRow();		

		System.out.printf("column : " + column);
		System.out.printf("row : " + row);

		Integer x[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};
		String sql[] = {""};
		String lookup_name[] = {"","Chart of Account","Division","Department","","Project","SubProject","","","","Entity",
				"Entity Type", "Reference Doc.","","","","","","","",""
				,"","","","Withholding Tax","","","","","",""};			


		System.out.printf("column : " + column);
		System.out.printf("row : " + row);

		if (column == 14) {

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Reference Doc. Date",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

			if ( scanOption == JOptionPane.CLOSED_OPTION ) {
				try {	
					System.out.printf("Nag-run ang date!!!  \n");
					modelDRF_part.setValueAt(dteRefDate.getDate(), tblDRF_part.getSelectedRow(), 14);
				} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
			} // CLOSED_OPTION

		}

		if (column == x[column] && modelDRF_part.isEditable() && sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setFilterClientName(true);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 0) {	
				tblDRF_part.packAll();
			}
			if (data != null && column == 1) {			
				modelDRF_part.setValueAt(data[0], row, column);
				modelDRF_part.setValueAt(data[1], row, column+6);
			} else if (data != null && column == 5)  {				
				modelDRF_part.setValueAt(data[0], row, column);
				modelDRF_part.setValueAt("", row, column+1);
			}
			else if (data != null && column == 6)  {				
				modelDRF_part.setValueAt(data[0], row, column);
				modelDRF_part.setValueAt(data[2], row, column-1);
			}
			else if (data != null && column == 2)  {				
				modelDRF_part.setValueAt(data[0], row, column);
				modelDRF_part.setValueAt("", row, column+1);
			}
			else if (data != null && column == 3)  {				
				modelDRF_part.setValueAt(data[0].toString().trim(), row, column);				
			}
			else if (data != null && column == 10)  {	

				is_payee_vatable = (Boolean) data[3];
				//clickTableColumn2();				
				{
					if (JOptionPane.showConfirmDialog(getContentPane(), "The selected availer has unliquidated CA, proceed anyway?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						modelDRF_part.setValueAt(data[0], row, column);
						modelDRF_part.setValueAt(data[1], row, column+5);		
					} else {modelDRF_part.setValueAt("", row, column);}
				}				
			}
			else if (data != null && column == 24)  {				
				modelDRF_part.setValueAt(data[0], row, column);
				modelDRF_part.setValueAt(data[2], row, column+1);
				computeEstimatedCost();
			}				
			else if (data != null)  {				
				modelDRF_part.setValueAt(data[0], row, column);
			}
		}

		else {}		
		tblDRF_part.packAll();
	}	

	private void computeEstimatedCost(){

		int rw = tblDRF_part.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {		

			//String
			String h1 = "";
			String h2 = "";
			String h3 = "";
			String h4 = "";
			String h5 = "";

			Integer h1_int = 0;
			Integer h2_int = 0;
			Integer h3_int = 0;
			Integer h4_int = 0;
			Integer h5_int = 0;
			Integer h_total = 0;

			try { h1	= modelDRF_part.getValueAt(x,0).toString().trim(); } catch (NullPointerException e) { h1 = ""; }
			try { h2	= modelDRF_part.getValueAt(x,1).toString().trim(); } catch (NullPointerException e) { h2 = ""; }
			try { h3	= modelDRF_part.getValueAt(x,2).toString().trim(); } catch (NullPointerException e) { h3 = ""; }
			try { h4	= modelDRF_part.getValueAt(x,3).toString().trim(); } catch (NullPointerException e) { h4 = ""; }
			try { h5	= modelDRF_part.getValueAt(x,4).toString().trim(); } catch (NullPointerException e) { h5 = ""; }

			if(!h1.equals("")){h1_int = 1;}
			if(!h2.equals("")){h2_int = 1;}
			if(!h3.equals("")){h3_int = 1;}
			if(!h4.equals("")){h4_int = 1;}
			if(!h5.equals("")){h5_int = 1;}
			h_total =  h1_int+h2_int+h3_int+h4_int+h5_int;

			if (h_total>1)
				//if (h1!="" && h2!="" && h3!="" && h4!="" && h5!="")
			{JOptionPane.showMessageDialog(getContentPane(),"Invalid Header","Error",JOptionPane.ERROR_MESSAGE);break;}
			String work_desc = "";
			try { work_desc	= modelDRF_part.getValueAt(x,5).toString().trim(); } catch (NullPointerException e) { work_desc = ""; }
			if(!h1.equals("")) 		{}
			else if(!h2.equals("")) {work_desc = "      " + work_desc;}
			else if(!h3.equals("")) {work_desc = "            " + work_desc;}
			else if(!h4.equals("")) {work_desc = "                    " + work_desc;}
			else if(!h5.equals("")) {work_desc = "                           " + work_desc;}			

			//Computation of Amount
			Double qty			= 0.00;	
			Double mtl_unit		= 0.00;	
			Double lbr_unit		= 0.00;	

			if (modelDRF_part.getValueAt(x,7).toString().equals("")) {qty	= 0.00;}
			else { qty	= Double.parseDouble(modelDRF_part.getValueAt(x,7).toString()); }
			try { mtl_unit	= Double.parseDouble(modelDRF_part.getValueAt(x,8).toString()); } catch (NullPointerException e) { mtl_unit	= 0.00; }
			try { lbr_unit	= Double.parseDouble(modelDRF_part.getValueAt(x,10).toString()); } catch (NullPointerException e) { lbr_unit	= 0.00; }

			double mtl_total_amt 	= qty * mtl_unit;			
			double lbr_total_amt 	= qty * lbr_unit;		
			double total_unit 		= mtl_unit + lbr_unit;			
			double total_total 		= lbr_total_amt + mtl_total_amt;			

			BigDecimal mtl_total_bd = new BigDecimal(mtl_total_amt);
			BigDecimal lbr_unit_bd 	= new BigDecimal(lbr_total_amt);


			modelDRF_part.setValueAt(work_desc, x, 5);
			modelDRF_part.setValueAt(mtl_total_bd, x, 9);
			modelDRF_part.setValueAt(lbr_unit_bd, x, 11);
			modelDRF_part.setValueAt(total_unit, x, 12);
			modelDRF_part.setValueAt(total_total, x, 13);

			x++;

			work_desc = "";
		}

		//totalDRF(modelDRF_part, modelDRF_part_total);
	}

	private void removeRow(){		

		int r  = tblDRF_part.getSelectedRow();
		Boolean isprojVatable = (Boolean) modelDRF_part.getValueAt(r,19);		
		Boolean entityVatable = (Boolean) modelDRF_part.getValueAt(r,20);		
		Double vat_rate		  = Double.parseDouble(modelDRF_part.getValueAt(r,23).toString());	

		if (JOptionPane.showConfirmDialog(getContentPane(), "Remove row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) tblDRF_part.getModel()).removeRow(r);  
			modelDRF_part.addRow(new Object[] { null, null, null, null, null, null, null, null, new BigDecimal(0.00),
					null, null, null, null, null, null, null, null, null, null, isprojVatable, entityVatable, null,
					new BigDecimal(0.00), new BigDecimal(vat_rate), null,  new BigDecimal(0.00),  new BigDecimal(0.00),
					new BigDecimal(0.00),  new BigDecimal(0.00),  new BigDecimal(0.00), new BigDecimal(0.00), 
					new BigDecimal(0.00),  new BigDecimal(0.00),  new BigDecimal(0.00), null });
			computeEstimatedCost();
			adjustRowHeight();
		}


	}

	private void adjustRowHeight(){		

		int rw = tblDRF_part.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblDRF_part.setRowHeight(x, 22);			
			x++;
		}
	}



	//save and insert
	private void insertLandDev_header(pgUpdate db){

		String sqlDetail = 
			"INSERT into rf_landdev_monitoring_cost_main values (" +
			"'"+landdev_no+"',  \n" +  	//1
			"'"+proj_id+"',  \n" +		//2
			"'"+txtAddress.getText()+"',  \n" +		//3
			"'"+type_id+"',  \n" +		//4
			"'A',  \n" +				//5
			"'"+UserInfo.EmployeeCode+"', \n" + //6
			"now()  \n" +  				//7
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	private void insertLandDev_detail(pgUpdate db){

		int rw = tblDRF_part.getModel().getRowCount();	
		int x = 0;
		String h1="", h2="", h3="", h4="", h5="";	
		String h1_new, h2_new, h3_new, h4_new, h5_new;	

		while (x < rw) {	
			
			try { h1_new = modelDRF_part.getValueAt(x,0).toString().trim().replace("'", "''"); } catch (NullPointerException e) { h1_new = ""; }
			try { h2_new = modelDRF_part.getValueAt(x,1).toString().trim().replace("'", "''"); } catch (NullPointerException e) { h2_new = ""; }
			try { h3_new = modelDRF_part.getValueAt(x,2).toString().trim().replace("'", "''"); } catch (NullPointerException e) { h3_new = ""; }
			try { h4_new = modelDRF_part.getValueAt(x,3).toString().trim().replace("'", "''"); } catch (NullPointerException e) { h4_new = ""; }
			try { h5_new = modelDRF_part.getValueAt(x,4).toString().trim().replace("'", "''"); } catch (NullPointerException e) { h5_new = ""; }

			if (h1_new.equals("")){} else {h1 = h1_new;}
			if (h2_new.equals("")){} else {h2 = h2_new;}
			if (h3_new.equals("")){} else {h3 = h3_new;}
			if (h4_new.equals("")){} else {h4 = h4_new;}
			if (h5_new.equals("")){} else {h5 = h5_new;}
			
			if (h1_new.equals("")){} else {h2 = "";h3 = "";h4 = "";h5 = "";}
			if (h2_new.equals("")){} else {h3 = "";h4 = "";h5 = "";}
			if (h3_new.equals("")){} else {h4 = "";h5 = "";}
			if (h4_new.equals("")){} else {h5 = "";}
			
			if (h1_new.equals("")&&h2_new.equals("")&&h3_new.equals("")&&h4_new.equals("")&&h5_new.equals("")) {}
			else
			{			

				String description 	= "";
				String unit 		= "";
				String qty			= "";
				Double matl_unit_cost 	= 0.00;		
				Double matl_total_cost 	= 0.00;	
				Double labor_unit_cost 	= 0.00;		
				Double labor_total_cost = 0.00;	
				Double total_unit_cost 	= 0.00;		
				Double total_total_cost = 0.00;	

				try { description 		= modelDRF_part.getValueAt(x,5).toString().trim().replace("'","''"); } catch (NullPointerException e) { description = ""; }
				try { unit 				= modelDRF_part.getValueAt(x,6).toString().trim();	 } catch (NullPointerException e) { unit 	= ""; }
				try { qty 				= modelDRF_part.getValueAt(x,7).toString().trim();	 } catch (NullPointerException e) { qty 	= "0"; }
				try { matl_unit_cost 	= Double.parseDouble(modelDRF_part.getValueAt(x,8).toString()); } catch (NullPointerException e) { matl_unit_cost	= 0.00; }	
				try { matl_total_cost 	= Double.parseDouble(modelDRF_part.getValueAt(x,9).toString()); } catch (NullPointerException e) { matl_total_cost	= 0.00; }	
				try { labor_unit_cost 	= Double.parseDouble(modelDRF_part.getValueAt(x,10).toString()); } catch (NullPointerException e) { labor_unit_cost	= 0.00; }	
				try { labor_total_cost 	= Double.parseDouble(modelDRF_part.getValueAt(x,11).toString()); } catch (NullPointerException e) { labor_total_cost	= 0.00; }	
				try { total_unit_cost 	= Double.parseDouble(modelDRF_part.getValueAt(x,12).toString()); } catch (NullPointerException e) { total_unit_cost	= 0.00; }	
				try { total_total_cost 	= Double.parseDouble(modelDRF_part.getValueAt(x,13).toString()); } catch (NullPointerException e) { total_total_cost	= 0.00; }	

				String sqlDetail = 
					"INSERT into rf_landdev_monitoring_cost_detail values (" +
					"'"+landdev_no+"',  \n" +  	//1 
					""+x+",  \n" +				//2 
					"'"+h1+"',  \n" +		//3
					"'"+h2+"',  \n" +		//4 
					"'"+h3+"',  \n" +		//5 
					"'"+h4+"',  \n" +		//6 
					"'"+h5+"',  \n" +		//7 
					"'"+description+"',  \n" +	//8 
					"'"+unit+"',  \n" +			//9 
					"'"+qty+"',  \n" +			//10 					
					""+matl_unit_cost+",  \n" +	//11
					""+matl_total_cost+",  \n" +	//12
					""+labor_unit_cost+",  \n" +	//13
					""+labor_total_cost+",  \n" +	//14
					""+total_unit_cost+",  \n" +	//15
					""+total_total_cost+" , \n" +	//16
					"'A', \n"+ 					//17
					"'"+UserInfo.EmployeeCode+"', \n" + //18
					"now()  \n" +  				//19
					")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);

				db.executeUpdate(sqlDetail, false);				

			}

			/*{h1_old = h1_new;}
			{h2_old = h2_new;}
			{h3_old = h3_new;}
			{h4_old = h4_new;}
			{h5_old = h5_new;}*/
			
			x++;
		}

	}


	//set values of variables 	
	@Override
	public void keyPressed(KeyEvent arg0) { }

	@Override
	public void keyReleased(KeyEvent arg0) {
		/*_JTableMain table = (_JTableMain) arg0.getSource();
		Integer row = table.getSelectedRow();
		Integer column = table.getSelectedColumn();*/

		if(arg0.getKeyCode() == KeyEvent.VK_F2){
			clickTableColumn();
		}
	}

	@Override
	
	public void keyTyped(KeyEvent arg0) { }		

	//
	private ArrayList<String> getSelectedPhase() {
		ArrayList<String> listSelectedPhase = new ArrayList<String>();
		for (int x = 0; x < listPhase.getModel().getSize(); x++) {
			_JCheckListItem selectedItem = (_JCheckListItem) listPhase.getModel().getElementAt(x);

			String phase = selectedItem.toString().trim().split("[\\(\\)]")[1];
			boolean isSelected = selectedItem.isSelected();

			if (isSelected) {
				listSelectedPhase.add(phase);
			}
		}
		return listSelectedPhase;
	}

}