package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.model_FAD_process;
import tablemodel.model_listofEmployees;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class FAD_process_admin extends _JInternalFrame implements _GUI, ActionListener, MouseListener {
	
	/*
	 * AS OF 2021-12-02
	 * 
	 * 1. ADDITIONAL CONTROL IN FAD PROCESS - UNTAGGING JV WILL BE NOW CONTROLLED DCRF NO. 1875
	 * 
	 * */
	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Accounting Process Admin";
	static Dimension SIZE = new Dimension(880, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSearchList;
	private JPanel pnlSouth;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlSearch;
	private JPanel pnlSearch_a1;
	private JPanel pnlSearch_a2;
	private JPanel pnlSearch_a2_1;
	private JPanel pnlSearch_a;
	private JPanel pnlComp_a3;
	private JPanel pnlAddNewAcct;
	private JPanel pnlEmployees;
	private JPanel pnlAuthority;
	private JPanel pnlProcessList;
	private JPanel pnlProcessList_sub;	

	static _JLookup lookupCompany;	
	private _JLookup lookupDepartment;

	static _JTagLabel tagCompany;
	private _JTagLabel tagDepartment;

	private JButton btnSave;
	private static JButton btnCancel;
	private static JButton btnEdit;

	private _JScrollPaneMain scrollEmployees;
	private _JScrollPaneMain scrollProcess;
	private _JScrollPaneTotal scrollProcess_total;
	private _JScrollPaneTotal scrollEmployees_total;

	private model_listofEmployees modelEmployees;
	private static model_listofEmployees modelEmployees_total;
	private model_FAD_process modelProcess;
	private static model_FAD_process modelProcess_total;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private static JLabel lblSearch;
	private JLabel lblCompany;
	private JLabel lblDepartment;

	private JPopupMenu menu2;
	private JMenuItem mniopenRPLF;
	private JMenuItem mniopenJV;
	private JMenuItem mniopenPV;

	private JComboBox cmbLevelNew;

	private static JXTextField txtSearch;

	private JMenuItem mniopenGL;	
	private JMenuItem mniaddrow;

	private static _JTableMain tblEmployees;
	private static _JTableMain tblProcess;
	private _JTableTotal tblEmployees_total;	
	private _JTableTotal tblProcess_total;		
	private JList rowHeaderEmployees;	
	private JList rowHeaderProcess;		

	private static JCheckBox chkInactiveEmp;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private JPopupMenu menu;

	public static String company = "";
	public static String company_logo;
	static String co_id = "";
	static String emp_code = "";	
	static String dept_code = "04";
	static String name = "";


	public FAD_process_admin() {
		super(title, true, true, true, true);
		initGUI();
	}

	public FAD_process_admin(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public FAD_process_admin(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		//this.setPreferredSize(new java.awt.Dimension(762, 604));
		//this.setBounds(0, 0, 762, 604);

		{
			menu2 = new JPopupMenu("Popup");
			mniopenGL = new JMenuItem("Open General Ledger");		
			menu2.add(mniopenGL);

			mniopenGL.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){

				}
			});

		}
		{
			menu = new JPopupMenu("Popup");
			mniaddrow = new JMenuItem("Add Row");
			mniaddrow.setEnabled(false);
			menu.add(mniaddrow);
			mniaddrow.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					AddRow();
				}
			});
		}


		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 82));				

			pnlComp = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(921, 32));		

			pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlComp.add(pnlComp_a1, BorderLayout.WEST);	
			pnlComp_a1.setPreferredSize(new java.awt.Dimension(200, 29));
			pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

			{
				lblCompany = new JLabel("        COMPANY");
				pnlComp_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(88, 31));
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
							company		= (String) data[1];					
							tagCompany.setTag(company);
							company_logo = (String) data[3];

							KEYBOARD_MANAGER.focusNextComponent();							

							lblDepartment.setEnabled(true);
							lookupDepartment.setEnabled(true);
							tagDepartment.setEnabled(true);
							lookupDepartment.setValue("04");
							tagDepartment.setTag("FAD");
							chkInactiveEmp.setEnabled(true);	
							lblSearch.setEnabled(true);
							txtSearch.setEnabled(true);	

							displayEmployees(modelEmployees, rowHeaderEmployees);	
							enableButtons(false, false, false);
							lookupDepartment.setLookupSQL(getDepartment());

						}
					}
				});
			}

			pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlComp.add(pnlComp_a2, BorderLayout.CENTER);	
			pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
			pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));

			{
				tagCompany = new _JTagLabel("[ ]");
				pnlComp_a2.add(tagCompany);
				tagCompany.setBounds(209, 27, 700, 22);
				tagCompany.setEnabled(true);	
				tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
			}	

			pnlComp_a3 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlComp.add(pnlComp_a3, BorderLayout.EAST);	
			pnlComp_a3.setPreferredSize(new java.awt.Dimension(243, 29));	
			pnlComp_a3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
			{
				chkInactiveEmp = new JCheckBox("Include Inactive Employees");
				pnlComp_a3.add(chkInactiveEmp);
				chkInactiveEmp.setEnabled(false);	
				chkInactiveEmp.setPreferredSize(new java.awt.Dimension(383, 26));
				chkInactiveEmp.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						displayEmployees(modelEmployees, rowHeaderEmployees);
						txtSearch.setText("");
						enableButtons(false, false, false);
						refresh_tables();		
					}
				});
			}

			pnlSearch = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlSearch, BorderLayout.CENTER);				
			pnlSearch.setPreferredSize(new java.awt.Dimension(921, 41));
			pnlSearch.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlSearch.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlSearch_a1 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlSearch.add(pnlSearch_a1, BorderLayout.WEST);	
			pnlSearch_a1.setPreferredSize(new java.awt.Dimension(87, 36));	
			pnlSearch_a1.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			{
				lblDepartment = new JLabel("Dept :", JLabel.TRAILING);
				pnlSearch_a1.add(lblDepartment);
				lblDepartment.setEnabled(false);	
				lblDepartment.setPreferredSize(new java.awt.Dimension(92, 25));
				lblDepartment.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
			}		

			pnlSearch_a2 = new JPanel(new BorderLayout(5, 5));
			pnlSearch.add(pnlSearch_a2, BorderLayout.CENTER);	
			pnlSearch_a2.setPreferredSize(new java.awt.Dimension(639, 41));	
			pnlSearch_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			pnlSearch_a2_1 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlSearch_a2.add(pnlSearch_a2_1, BorderLayout.WEST);	
			pnlSearch_a2_1.setPreferredSize(new java.awt.Dimension(97, 26));					

			{
				lookupDepartment = new _JLookup(null, "Company",0,2);
				pnlSearch_a2_1.add(lookupDepartment);
				lookupDepartment.setEnabled(false);
				lookupDepartment.setReturnColumn(0);
				lookupDepartment.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){
							tagDepartment.setTag((String) data[2]);
							dept_code = (String) data[0] ;
							displayEmployees(modelEmployees, rowHeaderEmployees);	
							chkInactiveEmp.setSelected(false);	
							txtSearch.setText("");
							enableButtons(false, false, false);
							refresh_tables();
						}
					}
				});
			}

			pnlSearch_a2_1 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlSearch_a2.add(pnlSearch_a2_1, BorderLayout.CENTER);	
			pnlSearch_a2_1.setPreferredSize(new java.awt.Dimension(197, 22));					

			{
				tagDepartment = new _JTagLabel("[ ]");
				pnlSearch_a2_1.add(tagDepartment);
				tagDepartment.setBounds(209, 27, 700, 22);
				tagDepartment.setEnabled(false);	
				tagDepartment.setPreferredSize(new java.awt.Dimension(27, 33));
			}	

			pnlSearch_a = new JPanel(new BorderLayout(0,0));
			pnlSearch_a2.add(pnlSearch_a, BorderLayout.EAST);	
			pnlSearch_a.setPreferredSize(new java.awt.Dimension(261, 28));
			pnlSearch_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));			

			pnlSearch_a1 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlSearch_a.add(pnlSearch_a1, BorderLayout.WEST);	
			pnlSearch_a1.setPreferredSize(new java.awt.Dimension(91, 28));	
			pnlSearch_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

			{
				lblSearch = new JLabel("Search Name : ", JLabel.TRAILING);
				pnlSearch_a1.add(lblSearch);
				lblSearch.setEnabled(false);	
				lblSearch.setPreferredSize(new java.awt.Dimension(134, 26));
				lblSearch.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
			}						

			pnlSearch_a2 = new JPanel(new BorderLayout(0,0));
			pnlSearch_a.add(pnlSearch_a2, BorderLayout.CENTER);	
			pnlSearch_a2.setPreferredSize(new java.awt.Dimension(778, 36));	
			pnlSearch_a2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

			{
				txtSearch = new JXTextField("");
				pnlSearch_a2.add(txtSearch);
				txtSearch.setEnabled(false);	
				txtSearch.setEditable(true);	
				txtSearch.setBounds(120, 25, 300, 22);	
				txtSearch.setHorizontalAlignment(JTextField.CENTER);	
				txtSearch.addKeyListener(new KeyAdapter() {
					public void keyReleased(KeyEvent e) {	

						name = txtSearch.getText().trim().toUpperCase().replace("'", "''");

						displaySearchedEmployees(modelEmployees, rowHeaderEmployees);
						lookupDepartment.setText("");
						tagDepartment.setTag("");
						enableButtons(false, false, false);
						refresh_tables();
					}				 
				});	
			}	
		}	
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlSearchList = new JPanel();
			pnlTable.add(pnlSearchList, BorderLayout.CENTER);
			pnlSearchList.setLayout(new BorderLayout(5, 5));
			pnlSearchList.setPreferredSize(new java.awt.Dimension(923, 321));
			pnlSearchList.setBorder(lineBorder);			

			//Place any containers or components that you want to be resizable. e.g.(_JTabbedPane, JScrollPane, JSplitPane)			

			{
				pnlEmployees = new JPanel(new BorderLayout());
				pnlSearchList.add(pnlEmployees, BorderLayout.CENTER);
				pnlEmployees.setPreferredSize(new java.awt.Dimension(921, 178));
				{
					scrollEmployees = new _JScrollPaneMain();
					pnlEmployees.add(scrollEmployees, BorderLayout.CENTER);
					{
						modelEmployees = new model_listofEmployees();

						tblEmployees = new _JTableMain(modelEmployees);
						scrollEmployees.setViewportView(tblEmployees);
						tblEmployees.addMouseListener(new PopupTriggerListener_panel2());
						tblEmployees.addMouseListener(this);
						tblEmployees.setSortable(false);
						tblEmployees.getColumnModel().getColumn(0).setPreferredWidth(80);
						tblEmployees.getColumnModel().getColumn(1).setPreferredWidth(200);
						tblEmployees.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {clickTable();}
							public void keyPressed(KeyEvent e) {clickTable();}
						}); 

						tblEmployees.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {clickTable();}
							public void mouseReleased(MouseEvent e) {clickTable();}
						});

					}
					{
						rowHeaderEmployees = tblEmployees.getRowHeader22();
						scrollEmployees.setRowHeaderView(rowHeaderEmployees);
						scrollEmployees.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollEmployees_total = new _JScrollPaneTotal(scrollEmployees);
						pnlEmployees.add(scrollEmployees_total, BorderLayout.SOUTH);
						{
							modelEmployees_total = new model_listofEmployees();
							modelEmployees_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

							tblEmployees_total = new _JTableTotal(modelEmployees_total, tblEmployees);
							tblEmployees_total.setFont(dialog11Bold);
							scrollEmployees_total.setViewportView(tblEmployees_total);
							((_JTableTotal) tblEmployees_total).setTotalLabel(0);
						}
					}
				}				
			}


			pnlAuthority= new JPanel(new BorderLayout(5, 5));
			pnlTable.add(pnlAuthority, BorderLayout.SOUTH);
			pnlAuthority.setPreferredSize(new java.awt.Dimension(923, 301));
			pnlAuthority.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));						

			pnlProcessList = new JPanel(new GridLayout(1, 1, 0, 0));
			pnlAuthority.add(pnlProcessList, BorderLayout.CENTER);
			pnlProcessList.setPreferredSize(new java.awt.Dimension(923, 287));	
			pnlProcessList.setBorder(lineBorder);
			pnlProcessList.setBorder(JTBorderFactory.createTitleBorder("FAD Processes"));


			{			
				pnlProcessList_sub = new JPanel(new BorderLayout());
				pnlProcessList.add(pnlProcessList_sub, BorderLayout.CENTER);
				pnlProcessList_sub.setPreferredSize(new java.awt.Dimension(401, 253));				

				{
					scrollProcess = new _JScrollPaneMain();
					pnlProcessList_sub.add(scrollProcess, BorderLayout.CENTER);
					{
						/*EDITED BY JED 2021-12-02 DCRF NO. 1875 : EDIT TABLE MODEL, ADD CAN UNTAG*/
						modelProcess = new model_FAD_process();

						tblProcess = new _JTableMain(modelProcess);
						tblProcess.addMouseListener(new PopupTriggerListener_panel());
						scrollProcess.setViewportView(tblProcess);	
						tblProcess.getColumnModel().getColumn(0).setPreferredWidth(80);
						tblProcess.getColumnModel().getColumn(1).setPreferredWidth(200);
						tblProcess.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblProcess.getColumnModel().getColumn(3).setPreferredWidth(100);
						tblProcess.getColumnModel().getColumn(4).setPreferredWidth(100);
						tblProcess.getColumnModel().getColumn(5).setPreferredWidth(100);
						tblProcess.getColumnModel().getColumn(6).setPreferredWidth(100);

						tblProcess.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {clickTableColumn_account();}								
							}
							public void mouseReleased(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {clickTableColumn_account();}
							}
						});

						tblProcess.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {}						
							public void keyPressed(KeyEvent e) {}
						});


					}
					{
						rowHeaderProcess = tblProcess.getRowHeader22();
						scrollProcess.setRowHeaderView(rowHeaderProcess);
						scrollProcess.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollProcess_total = new _JScrollPaneTotal(scrollProcess);
						pnlProcessList_sub.add(scrollProcess_total, BorderLayout.SOUTH);
						{
							modelProcess_total = new model_FAD_process();
							modelProcess_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

							tblProcess_total = new _JTableTotal(modelProcess_total, tblProcess);
							tblProcess_total.setFont(dialog11Bold);
							scrollProcess_total.setViewportView(tblProcess_total);
							((_JTableTotal) tblProcess_total).setTotalLabel(0);
						}
					}
				}
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
					btnCancel = new JButton("Cancel");
					pnlSouthCenter.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display
	public static void displayEmployees(DefaultTableModel modelMain, JList rowHeader) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select \r\n" + 
			"\r\n" + 
			"a.emp_code, \r\n" + 
			"upper(trim(d.first_name)||' '||trim(d.last_name)),\r\n" + 
			"b.dept_alias,\r\n" + 
			"c.division_alias,\r\n" + 
			"e.empstatus_desc\r\n" + 
			"\r\n" + 
			"from em_employee a\r\n" + 
			"left join mf_department b on a.dept_code = b.dept_code\r\n" + 
			"left join mf_division c on a.div_code = c.division_code\r\n" + 
			"left join rf_entity d on a.entity_id = d.entity_id \n" +
			"left join mf_employee_status e on a.emp_status = e.empstatus_code \r\n" + 

			"where a.emp_code is not null \n" ;

		if (chkInactiveEmp.isSelected()==true){} else {sql = sql +"and e.status_id != 'I'\r\n"; } 

		sql = sql +
		"and a.dept_code = '"+dept_code+"' \r\n" + 
		"order by d.first_name";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	

		tblEmployees.packAll();	
		adjustRowHeight(tblEmployees);

		int row = tblEmployees.getRowCount();	
		modelEmployees_total.setValueAt(new BigDecimal(row), 0, 1);

	}		
	
	/*EDITED BY JED 2021-12-02 DCRF NO. 1875 : ADDITIONAL CONTROL IN FAD PROCESS (UNTAGGING OF JV))*/
	public static void displayProcessList(DefaultTableModel modelMain, JList rowHeader) {		

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

//		String sql = 				
//			"select \r\n" + 
//			"\r\n" + 
//			"distinct on (a.process_no)\r\n" + 
//			"\r\n" + 
//			"a.process_no,\r\n" + 
//			"trim(f.process_name) as process_name,  \n" +
//			"(case when b.status_id = 'A' then true else false end) as can_add, \r\n" + 
//			"(case when c.status_id = 'A' then true else false end) as can_approve,\r\n" + 
//			"(case when d.status_id = 'A' then true else false end) as can_delete, \r\n" + 
//			"(case when e.status_id = 'A' then true else false end) as can_preview \r\n" + 
//			"\r\n" + 
//			"from ( select * from mf_fad_process_access where emp_code = '"+emp_code+"' ) a  \r\n" + 
//			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 1 and emp_code = '"+emp_code+"') b on a.process_no = b.process_no  \r\n" + 
//			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 2 and emp_code = '"+emp_code+"') c on a.process_no = c.process_no  \r\n" + 
//			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 3 and emp_code = '"+emp_code+"') d on a.process_no = d.process_no  \r\n" + 
//			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 4 and emp_code = '"+emp_code+"') e on a.process_no = e.process_no  \n" +
//			"left join mf_fad_process f on a.process_no = f.process_no \r\n"  ;
		
		String sql =
			"select \n" + 
			"\n" + 
			"distinct on (a.process_no)\n" + 
			"\n" + 
			"a.process_no,\n" + 
			"trim(g.process_name) as process_name,  \n" + 
			"(case when b.status_id = 'A' then true else false end) as can_add, \n" + 
			"(case when c.status_id = 'A' then true else false end) as can_approve,\n" + 
			"(case when d.status_id = 'A' then true else false end) as can_delete, \n" + 
			"(case when e.status_id = 'A' then true else false end) as can_preview, \n" + 
			"(case when f.status_id = 'A' then true else false end) as can_untag \n" + 
			"\n" + 
			"from ( select * from mf_fad_process_access where emp_code = '"+emp_code+"' ) a  \n" + 
			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 1 and emp_code = '"+emp_code+"') b on a.process_no = b.process_no  \n" + 
			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 2 and emp_code = '"+emp_code+"') c on a.process_no = c.process_no  \n" + 
			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 3 and emp_code = '"+emp_code+"') d on a.process_no = d.process_no  \n" + 
			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 4 and emp_code = '"+emp_code+"') e on a.process_no = e.process_no  \n" + 
			"left join ( select process_no, status_id from mf_fad_process_access where authority_no = 5 and emp_code = '"+emp_code+"') f on a.process_no = f.process_no  \n" + 
			"left join mf_fad_process g on a.process_no = g.process_no \r\n";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	

		tblProcess.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblProcess.getColumnModel().getColumn(1).setPreferredWidth(200);
		tblProcess.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblProcess.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblProcess.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblProcess.getColumnModel().getColumn(5).setPreferredWidth(100);
		adjustRowHeight(tblProcess);

		int rw_count = tblProcess.getRowCount();	
		modelProcess_total.setValueAt(new BigDecimal(rw_count), 0, 1);

	}	

	public static void displayProcessListB(DefaultTableModel modelMain, JList rowHeader) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select process_no, trim(process_name), false, false, false, false from mf_fad_process order by process_no  \r\n"  ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	

		tblProcess.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblProcess.getColumnModel().getColumn(1).setPreferredWidth(200);
		tblProcess.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblProcess.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblProcess.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblProcess.getColumnModel().getColumn(5).setPreferredWidth(100);
		adjustRowHeight(tblProcess);

		int rw_count = tblProcess.getRowCount();	
		modelProcess_total.setValueAt(new BigDecimal(rw_count), 0, 1);

	}

	public static void displaySearchedEmployees(DefaultTableModel modelMain, JList rowHeader) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select \r\n" + 
			"\r\n" + 
			"a.emp_code, \r\n" + 
			"upper(trim(d.first_name)||' '||trim(d.last_name)),\r\n" + 
			"b.dept_alias,\r\n" + 
			"c.division_alias,\r\n" + 
			"e.empstatus_desc\r\n" + 
			"\r\n" + 
			"from em_employee a\r\n" + 
			"left join mf_department b on a.dept_code = b.dept_code\r\n" + 
			"left join mf_division c on a.div_code = c.division_code\r\n" + 
			"left join rf_entity d on a.entity_id = d.entity_id \n" +
			"left join mf_employee_status e on a.emp_status = e.empstatus_code \r\n" + 
			"\r\n " +
			"where upper(trim(d.first_name)) like '%"+name+"%' " +
			"or upper(trim(d.last_name)) like '%"+name+"%'   \n" +
			"\r\n" + 
			"order by d.first_name";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	

		tblEmployees.packAll();	
		adjustRowHeight(tblEmployees);

		int row = tblEmployees.getRowCount();	
		modelEmployees_total.setValueAt(new BigDecimal(row), 0, 1);

	}	


	//Enable/Disable all components inside JPanel
	public void setComponentEnabled(JPanel panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			comp.setEnabled(enable);
		}
	}

	public void refresh_tables(){		

		//reset table 
		FncTables.clearTable(modelProcess);
		FncTables.clearTable(modelProcess_total);			
		rowHeaderProcess = tblProcess.getRowHeader22();
		scrollProcess.setRowHeaderView(rowHeaderProcess);	
		modelProcess_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
	}

	public void enableButtons(Boolean a, Boolean b, Boolean c ) {

		btnEdit.setEnabled(a);
		btnSave.setEnabled(b);
		btnCancel.setEnabled(c);

	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {		

		if(e.getActionCommand().equals("Cancel")){ cancel(); }

		if (e.getActionCommand().equals("Save")) {
			if(UserInfo.EmployeeCode.equals("900054")||UserInfo.EmployeeCode.equals("900449")||UserInfo.EmployeeCode.equals("900748")||UserInfo.EmployeeCode.equals("900876")||UserInfo.EmployeeCode.equals("900553")||UserInfo.EmployeeCode.equals("901128")) 
			{saveAccess(); } else 
			{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to give access.","Information",JOptionPane.WARNING_MESSAGE); }	
		}

		if (e.getActionCommand().equals("Edit")) { editAcct(); }	

	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {

		}
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

	public class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int column 	= 0;
				if (column==12) {menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false);}

			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int column 	= 0;
				if (column==12) {menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false);}

			}
		}
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

	public void addNewAcct(){		

		cmbLevelNew.setEnabled(true);

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewAcct, "Add New Account",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {			
		} // CLOSED_OPTION

	}

	public void saveAccess(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Update process authority access?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			pgUpdate db = new pgUpdate();	

			{		
				insertNewAccess(db);	
				//insertEmplNewProcess(db);
				db.commit();	
				JOptionPane.showMessageDialog(getContentPane(),"Employee process access saved.","Information",JOptionPane.INFORMATION_MESSAGE);
				cancel();

			}	
		}

	}

	public void editAcct(){

		btnEdit.setEnabled(false);
		btnSave.setEnabled(true);
		modelProcess.setEditable(true);
		mniaddrow.setEnabled(true);

	}

	public void cancel(){
		chkInactiveEmp.setSelected(false);	
		txtSearch.setText("");
		enableButtons(false, false, false);
		displayEmployees(modelEmployees, rowHeaderEmployees);	
		refresh_tables();
		mniaddrow.setEnabled(false);
	}

	public void clickTable(){	

		Integer row = tblEmployees.getSelectedRow();
		emp_code = tblEmployees.getValueAt(row,0).toString().trim();		
		if (isEmplExisting()==true) {displayProcessList(modelProcess, rowHeaderProcess);}
		else {displayProcessListB(modelProcess, rowHeaderProcess);}
		btnEdit.setEnabled(true);
		btnSave.setEnabled(false);
		btnCancel.setEnabled(true);
		modelProcess.setEditable(false);
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		KEYBOARD_MANAGER.focusNextComponent();							

		lblDepartment.setEnabled(true);
		lookupDepartment.setEnabled(true);
		tagDepartment.setEnabled(true);
		lookupDepartment.setValue("04");
		tagDepartment.setTag("FAD");
		chkInactiveEmp.setEnabled(true);	
		lblSearch.setEnabled(true);
		txtSearch.setEnabled(true);	

		displayEmployees(modelEmployees, rowHeaderEmployees);	
		enableButtons(false, false, false);
		lookupDepartment.setLookupSQL(getDepartment());
		
		lookupCompany.setValue(co_id);
	}
	

	//select, lookup and get statements	
	public static Boolean isEmplExisting(){

		Boolean EmpExist = false;

		String SQL = 
			"select emp_code from mf_fad_process_access where emp_code = '"+emp_code+"' " ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			EmpExist = true;
		}else{
			EmpExist = false;
		}

		return EmpExist;

	}

	public static Boolean isEmpl_Proc_Existing(String process_no){

		Boolean EmpExist = true;

		String SQL = 
			"select emp_code from mf_fad_process_access where emp_code = '"+emp_code+"' and process_no = "+process_no+" " ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			EmpExist = true;
		}else{
			EmpExist = false;
		}

		return EmpExist;

	}

	public String getDepartment(){

		String sql = "select dept_code as \"Dept Code\", " +
		"dept_name as \"Dept Name\", " +
		"dept_alias as \"Dept Alias\" " +
		"from mf_department order by dept_code " ;

		return sql;

	}	

	public String getFADprocess(){

		String sql =
			"select " +
			"process_no as \"Process No\", " +
			"process_name as \"Process Name\" " +
			"from mf_fad_process " +
			"order by process_no " ;

		return sql;

	}	


	//checking and validation
	public Boolean checkIfAlreadyExisting(String process_no){

		boolean x = false;	

		int rw = tblProcess.getModel().getRowCount()-1;	
		int w = 0;

		while (w < rw) {	

			String process = modelProcess.getValueAt(w,0).toString().trim();
			if (process.equals(process_no)) {x=true; break;} 
			else {}		
			w++;
		}

		return x;


	}


	//table operations	
	private static void adjustRowHeight(_JTableMain tbl){		

		int rw = tbl.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tbl.setRowHeight(x, 22);			
			x++;
		}
	}

	private void clickTableColumn_account() {//ok

		int column = tblProcess.getSelectedColumn();
		int row = tblProcess.getSelectedRow();				

		Integer x[] = {0,1,2,3};
		String sql[] = {getFADprocess()};	
		String lookup_name[] = {"FAD Process"};		

		if (column == x[column] &&  sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 0)  {			

				if (checkIfAlreadyExisting(data[0].toString())) 
				{
					JOptionPane.showMessageDialog(getContentPane(),"Proces already exists.","Duplicate",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					modelProcess.setValueAt(data[0], row, column);	
					modelProcess.setValueAt(data[1], row, column+1);	
				}
			}
		}		

		else {}

		//tblProcess.packAll();	
	}

	
	/*EDITED BY JED 2021-12-02 DCRF NO. 1875 : ADDITIONAL CONTROL IN FAD PROCESS (UNTAGGING OF JV))*/
	//save and insert
	public void insertNewAccess(pgUpdate db) {	

		Integer row = tblProcess.getRowCount();
		Integer x = 0;		

		while (x<row) {

			Integer y=1;
			Integer z = x + 1;
			String process_no = tblProcess.getValueAt(x,0).toString().trim();
			
			/*from y<=4 to y<=5*/
			while (y<=5) {				

				Boolean can_do	= (Boolean) tblProcess.getValueAt(x,y+1);	

				if (isEmpl_Proc_Existing(process_no)==false)
				{
					String sqlDetail = 
						"\n" +
						"insert into mf_fad_process_access values ( \n " +
						"'"+emp_code+"',  \n" +
						""+z+", \n" +
						""+y+", \n" +
						"'"+UserInfo.EmployeeCode+"', \n" +
						"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	
						"'', \n" +
						"null, \n" ;

					if (can_do==true){ sqlDetail = sqlDetail +	"'A' ";} else { sqlDetail = sqlDetail +	"'I' "; }

					sqlDetail = sqlDetail +					
					")" ;

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);	

					y++;		
				}

				else 
				{
					String sqlDetail = 
						"\n" +
						"update mf_fad_process_access set \n " +
						"edited_by = '"+UserInfo.EmployeeCode+"', \n" +
						"date_edited = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" ;

					if (can_do==true){ sqlDetail = sqlDetail +	"status_id = 'A' ";} else { sqlDetail = sqlDetail +	"status_id = 'I' "; }

					sqlDetail = sqlDetail +					
					"where emp_code = '"+emp_code+"' and process_no = "+z+" and authority_no = "+y+"   " ;

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);	

					y++;		
				}
			}

			x++;
		}
	}
	
	/*EDITED BY JED 2021-12-02 DCRF NO. 1875 : ADDITIONAL CONTROL IN FAD PROCESS (UNTAGGING OF JV))*/
	public void insertEmplNewProcess(pgUpdate db) {	//i added this because insertNewAccess does not allow new process to be included 

		Integer row = tblProcess.getRowCount();
		Integer x = 0;		

		while (x<row) {

			String process_no = tblProcess.getValueAt(x,0).toString().trim();

			if (isEmpl_Proc_Existing(process_no)==true) {}
			else {

				Integer y=1;
				
				/*from y<=4 to y<=5*/
				while (y<=5) {				

					Boolean can_do	= (Boolean) tblProcess.getValueAt(x,y+1);	

					String sqlDetail = 
						"\n" +
						"insert into mf_fad_process_access values ( \n " +
						"'"+emp_code+"',  \n" +
						""+process_no+", \n" +
						""+y+", \n" +
						"'"+UserInfo.EmployeeCode+"', \n" +
						"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	
						"'', \n" +
						"null, \n" ;

					if (can_do==true){ sqlDetail = sqlDetail +	"'A' ";} else { sqlDetail = sqlDetail +	"'I' "; }

					sqlDetail = sqlDetail +					
					")" ;

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);	

					y++;		

				}
			}

			x++;
		}
	}


	//right-click
	private void AddRow(){

		modelProcess.addRow(new Object[] { "", "", false, false, false, false });		
		((DefaultListModel) rowHeaderProcess.getModel()).addElement(modelProcess.getRowCount());
		adjustRowHeight(tblProcess);
	}


}
