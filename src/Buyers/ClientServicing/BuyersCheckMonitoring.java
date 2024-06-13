package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelCheckStatus;
import tablemodel.modelCheckStatus_total;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class BuyersCheckMonitoring extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Buyers Check Holding and Withdrawal";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlTable;
	private JPanel pnlHeader;
	private JPanel pnlHeader_a1;
	private JPanel pnlHeader_a2;
	private JPanel pnlHeader_a2_1;
	private JPanel pnlHeader_a2_2;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlDate;
	private JPanel pnlHeader2;
	private JPanel pnlProcess;
	private JPanel pnlProcess_2;
	private JPanel pnlProcess_2a;
	private JPanel pnlTableList;
	private JPanel pnlCheckList;
	private JPanel pnlCheckList_sub;

	private JLabel lblCompany;
	public static JLabel lblClientName;
	public static JLabel lblProcess;
	private JLabel lblUnit;
	private JLabel lblUnitStatus;

	public static _JLookup lookupCompany;
	public static _JLookup lookupClientName;	
	public static  _JLookup lookupProcess;
	
	private modelCheckStatus modelBC;
	private modelCheckStatus_total modelBC_total;

	public static _JTagLabel tagCompany;
	public static _JTagLabel tagClientName;
	public static  _JTagLabel tagProcess;
	private _JTagLabel tagUnit;
	private _JTagLabel tagStatus;	

	private static JButton btnSave;
	private static JButton btnCancel;
	private static JButton btnRefresh;	
	private static JButton btnPreview;
	private JComboBox cmbProcess;	

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	private JXTextField txtAcctStatus;	
	private JXTextField txtUnit;
	
	private _JTableMain tblBC;
	private _JScrollPaneMain scrollBC;
	private _JScrollPaneTotal scrollBC_total;
	private JList rowHeaderBC;
	private _JTableTotal tblBC_total;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	

	public static String co_id = "";		
	public static String company = "";
	public static String company_logo;	
	public static String entity_id = "";
	public static String entity_name = "";
	public static String unit_id = "";
	public static String unit_desc = "";
	public static String status_id = "";
	public static String status_name = "";
	public static String pbl_id = "";
	public static String seq_no = "";		
	

	public BuyersCheckMonitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public BuyersCheckMonitoring(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public BuyersCheckMonitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(926, 545));
		this.setBounds(0, 0, 926, 545);

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
			pnlNorth.setPreferredSize(new java.awt.Dimension(821, 133));		

			pnlHeader = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlHeader, BorderLayout.CENTER);				
			pnlHeader.setPreferredSize(new java.awt.Dimension(921, 85));			
			pnlHeader.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));			

			pnlHeader_a1 = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlHeader.add(pnlHeader_a1, BorderLayout.WEST);	
			pnlHeader_a1.setPreferredSize(new java.awt.Dimension(106, 112));	
			pnlHeader_a1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

			{
				lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
				pnlHeader_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(105, 25));
				lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}
			{
				lblClientName = new JLabel("Client Name", JLabel.TRAILING);
				pnlHeader_a1.add(lblClientName);
				lblClientName.setEnabled(false);	
				lblClientName.setPreferredSize(new java.awt.Dimension(106, 40));
				lblClientName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}	
			{
				lblUnit = new JLabel("Unit", JLabel.TRAILING);
				pnlHeader_a1.add(lblUnit);
				lblUnit.setEnabled(false);	
				lblUnit.setPreferredSize(new java.awt.Dimension(106, 40));
				lblUnit.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}	
			{
				lblUnitStatus = new JLabel("Account Status", JLabel.TRAILING);
				pnlHeader_a1.add(lblUnitStatus);
				lblUnitStatus.setEnabled(false);	
				lblUnitStatus.setPreferredSize(new java.awt.Dimension(106, 40));
				lblUnitStatus.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}	

			pnlHeader_a2 = new JPanel(new BorderLayout(5, 5));
			pnlHeader.add(pnlHeader_a2, BorderLayout.CENTER);	
			pnlHeader_a2.setPreferredSize(new java.awt.Dimension(814, 40));	
			pnlHeader_a2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

			pnlHeader_a2_1 = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlHeader_a2.add(pnlHeader_a2_1, BorderLayout.WEST);	
			pnlHeader_a2_1.setPreferredSize(new java.awt.Dimension(101, 24));	

			{
				lookupCompany = new _JLookup(null, "Company",0,2);
				pnlHeader_a2_1.add(lookupCompany);
				lookupCompany.setLookupSQL(SQL_COMPANY());
				lookupCompany.setReturnColumn(0);
				lookupCompany.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							co_id 		= (String) data[0];	
							company		= (String) data[1];	
							tagCompany.setTag((String) data[1]);
							company_logo = (String) data[3];

							lblClientName.setEnabled(true);	
							lookupClientName.setEnabled(true);	
							lookupClientName.setEditable(true);	
							tagClientName.setEnabled(true);	

							lblProcess.setEnabled(true);	
							lookupClientName.setLookupSQL(getClientList());							

							enableButtons(false, false, false, true);
						}
					}
				});
			}		

			{
				lookupClientName = new _JLookup(null, "PV No.", 2, 2);
				pnlHeader_a2_1.add(lookupClientName);
				lookupClientName.setBounds(20, 27, 20, 25);
				lookupClientName.setReturnColumn(0);
				lookupClientName.setEnabled(false);	
				lookupClientName.setPreferredSize(new java.awt.Dimension(100, 24));
				lookupClientName.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){	

							entity_id 	= (String) data[0];	
							entity_name = (String) data[1];	
							unit_id 	= (String) data[2];	
							unit_desc 	= (String) data[3];	
							status_id 	= (String) data[4];	
							status_name = (String) data[5];	
							pbl_id 		= (String) data[6];	
							seq_no 		= data[7].toString();	

							lookupClientName.setValue(entity_id);
							tagClientName.setTag(entity_name);
							txtUnit.setText(unit_id);
							tagUnit.setTag(unit_desc);
							txtAcctStatus.setText(status_id);	
							tagStatus.setTag(status_name);	


							tagUnit.setEnabled(true);	
							lblUnit.setEnabled(true);	
							txtUnit.setEnabled(true);	
							tagUnit.setEnabled(true);	
							lblUnitStatus.setEnabled(true);	
							txtAcctStatus.setEnabled(true);	
							tagStatus.setEnabled(true);	

							lblProcess.setEnabled(true);
							cmbProcess.setEnabled(true);

							displayCheckList(modelBC, rowHeaderBC, modelBC_total);
							modelBC.setEditable(true);
							tblBC.setEditable(true);
							btnRefresh.setEnabled(false);
							btnSave.setEnabled(true);
							btnPreview.setEnabled(false);

						}
					}
				});
			}

			{
				txtUnit = new JXTextField("");
				pnlHeader_a2_1.add(txtUnit);
				txtUnit.setEnabled(false);	
				txtUnit.setEditable(false);	
				txtUnit.setBounds(120, 25, 300, 22);	
				txtUnit.setHorizontalAlignment(JTextField.CENTER);
			}	
			{
				txtAcctStatus = new JXTextField("");
				pnlHeader_a2_1.add(txtAcctStatus);
				txtAcctStatus.setEnabled(false);	
				txtAcctStatus.setEditable(false);	
				txtAcctStatus.setBounds(120, 25, 300, 22);	
				txtAcctStatus.setHorizontalAlignment(JTextField.CENTER);
			}	


			pnlHeader_a2_2 = new JPanel(new GridLayout(4, 2, 5, 5));
			pnlHeader_a2.add(pnlHeader_a2_2, BorderLayout.CENTER);	
			pnlHeader_a2_2.setPreferredSize(new java.awt.Dimension(357, 25));	

			{
				tagCompany = new _JTagLabel("[ ]");
				pnlHeader_a2_2.add(tagCompany);
				tagCompany.setBounds(209, 27, 700, 22);
				tagCompany.setEnabled(true);	
				tagCompany.setPreferredSize(new java.awt.Dimension(380, 24));
			}	
			{
				tagClientName = new _JTagLabel("[ ]");
				pnlHeader_a2_2.add(tagClientName);
				tagClientName.setBounds(209, 27, 700, 22);
				tagClientName.setEnabled(false);	
				tagClientName.setPreferredSize(new java.awt.Dimension(380, 24));
			}	
			{
				tagUnit = new _JTagLabel("[ ]");
				pnlHeader_a2_2.add(tagUnit);
				tagUnit.setBounds(209, 27, 700, 22);
				tagUnit.setEnabled(false);	
				tagUnit.setPreferredSize(new java.awt.Dimension(380, 24));
			}	
			{
				tagStatus = new _JTagLabel("[ ]");
				pnlHeader_a2_2.add(tagStatus);
				tagStatus.setBounds(209, 27, 700, 22);
				tagStatus.setEnabled(false);	
				tagStatus.setPreferredSize(new java.awt.Dimension(380, 24));
			}	
		}	
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));		

			{
				pnlHeader2 = new JPanel();
				pnlTable.add(pnlHeader2, BorderLayout.NORTH);
				pnlHeader2.setLayout(new BorderLayout(5, 5));
				pnlHeader2.setPreferredSize(new java.awt.Dimension(821, 41));
				pnlHeader2.setBorder(lineBorder);		

				{
					pnlProcess = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlHeader2.add(pnlProcess, BorderLayout.WEST);	
					pnlProcess.setPreferredSize(new java.awt.Dimension(112, 45));	
					pnlProcess.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblProcess = new JLabel("Process", JLabel.TRAILING);
						pnlProcess.add(lblProcess);
						lblProcess.setEnabled(false);	
						lblProcess.setPreferredSize(new java.awt.Dimension(113, 45));
						lblProcess.setFont(new java.awt.Font("Segoe UI",Font.BOLD|Font.ITALIC,12));
					}						

					pnlProcess_2 = new JPanel(new BorderLayout(5, 5));
					pnlHeader2.add(pnlProcess_2, BorderLayout.CENTER);	
					pnlProcess_2.setPreferredSize(new java.awt.Dimension(697, 45));	
					pnlProcess_2.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlProcess_2a = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlProcess_2.add(pnlProcess_2a, BorderLayout.WEST);	
						pnlProcess_2a.setPreferredSize(new java.awt.Dimension(131, 31));					

						{
							String status[] = {"Hold","Withdraw"};					
							cmbProcess = new JComboBox(status);
							pnlProcess_2a.add(cmbProcess);
							cmbProcess.setSelectedItem(null);
							cmbProcess.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
							cmbProcess.setBounds(537, 15, 160, 21);	
							cmbProcess.setEnabled(false);
							cmbProcess.setSelectedIndex(0);	
							cmbProcess.setPreferredSize(new java.awt.Dimension(155, 31));
							cmbProcess.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent evt) 
								{

								}
							});		
						}	
					}			
				}
			}
			{

				pnlCheckList = new JPanel(new BorderLayout());
				pnlTable.add(pnlCheckList, BorderLayout.CENTER);
				pnlCheckList.setPreferredSize(new java.awt.Dimension(923, 166));
				pnlCheckList.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlCheckList_sub = new JPanel(new GridLayout(1, 1, 0, 0));
				pnlCheckList.add(pnlCheckList_sub, BorderLayout.CENTER);
				pnlCheckList_sub.setPreferredSize(new java.awt.Dimension(518, 288));
				pnlCheckList_sub.setBorder(lineBorder);
				pnlCheckList_sub.setBorder(JTBorderFactory.createTitleBorder("List of Checks"));

				{			
					pnlTableList = new JPanel(new BorderLayout());
					pnlCheckList_sub.add(pnlTableList, "Center");
					pnlTableList.setPreferredSize(new java.awt.Dimension(529, 286));				

					{
						scrollBC = new _JScrollPaneMain();
						pnlTableList.add(scrollBC, BorderLayout.CENTER);
						{
							modelBC = new modelCheckStatus();

							tblBC = new _JTableMain(modelBC);
							scrollBC.setViewportView(tblBC);
							tblBC.addMouseListener(this);	
							tblBC.hideColumns("Client Name","Unit PBL","Seq.","Reason of Bounce","Change Status to");
							tblBC.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {}							
								public void keyPressed(KeyEvent e) {}

							}); 
							tblBC.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblBC.rowAtPoint(e.getPoint()) == -1){tblBC_total.clearSelection();}
									else{tblBC.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblBC.rowAtPoint(e.getPoint()) == -1){tblBC_total.clearSelection();}
									else{tblBC.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderBC = tblBC.getRowHeader();
							scrollBC.setRowHeaderView(rowHeaderBC);
							scrollBC.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollBC_total = new _JScrollPaneTotal(scrollBC);
						pnlTableList.add(scrollBC_total, BorderLayout.SOUTH);
						{
							modelBC_total = new modelCheckStatus_total();
							modelBC_total.addRow(new Object[] { null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null });

							tblBC_total = new _JTableTotal(modelBC_total, tblBC);
							tblBC_total.hideColumns("Client Name","Unit PBL","Seq.","Reason of Bounce","Change Status to");

							tblBC_total.setFont(dialog11Bold);
							scrollBC_total.setViewportView(tblBC_total);
							((_JTableTotal) tblBC_total).setTotalLabel(1);
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
			pnlSouth.setPreferredSize(new java.awt.Dimension(789, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));

			{
				btnSave = new JButton("Save");
				pnlSouthCenterb.add(btnSave);
				btnSave.setActionCommand("Save");	
				btnSave.setEnabled(false);
				btnSave.addActionListener(this);
			}
			{
				btnPreview = new JButton("Preview");
				pnlSouthCenterb.add(btnPreview);
				btnPreview.setActionCommand("Preview");	
				btnPreview.setEnabled(true);
				btnPreview.addActionListener(this);
				btnPreview.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Report Period",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

						if ( scanOption == JOptionPane.CLOSED_OPTION ) {
							try {	

							} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {}				
						} // CLOSED_OPTION
					}
				});
			}
			{
				btnRefresh = new JButton("Refresh");
				pnlSouthCenterb.add(btnRefresh);
				btnRefresh.setActionCommand("Refresh");	
				btnRefresh.setEnabled(false);
				btnRefresh.addActionListener(this);
			}
			{
				btnCancel = new JButton("Cancel");
				pnlSouthCenterb.add(btnCancel);
				btnCancel.setActionCommand("Cancel");
				btnCancel.addActionListener(this);
				btnCancel.setEnabled(false);
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {							
						
						
					}
				});
			}
		}
	}
	


	//display tables
	public void displayCheckList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal ) {
		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 

			"select \r\n" + 
			"\r\n" + 
			"(case when a.row_num = 1 then a.entity_name else '' end) as entity_name,\r\n" + 
			"a.proj_alias,\r\n" + 
			"a.pbl_id,\r\n" + 
			"a.seq_no,\r\n" + 
			"a.particulars,\r\n" + 
			"a.amount,\r\n" + 
			"a.bank_name,\r\n" + 
			"a.check_no,\r\n" + 
			"a.check_date,\r\n" + 
			"'',\r\n" + 
			"a.checkstat_desc,\r\n" + 
			"'',\r\n" + 
			"a.reason_desc,\r\n" + 
			"a.actual_date,\r\n" + 
			"a.remarks,\r\n" + 
			"a.pay_rec_id\r\n" + 
			"\r\n" + 
			"from (" +
			"select \n " +
			"row_number() over(PARTITION BY f.entity_name ORDER BY f.entity_name, a.check_date) as row_num," +
			"trim(f.entity_name)as entity_name, \n " +   			//client name
			"g.proj_alias, \n " + 									//project
			"trim(a.pbl_id)as pbl_id, \n " +						//pbl_id
			"a.seq_no, \n " +										//seq_no
			"trim(i.partdesc)as particulars, \r\n" + 				//particulars
			"a.amount, \n " +										//amount
			"coalesce(b.bank_name,'') as bank_name, \n " +			//bank name
			"a.check_no, \n " +										//check no
			"to_char(a.check_date,'MM/dd/yy')as check_date , \n " +	//check date
			"'',  \n " +											//tag
			"d.checkstat_desc, \n " +								//current status
			"'',  \n " +											//new status
			"(case when h.reason_desc is null then '' else h.reason_desc end) as reason_desc ,  \n " +	//reason of bounce
			"to_char(a.trans_date,'MM/dd/yy')as actual_date, \n " +	//actual date			
			"trim(a.remarks) as remarks," +							//remarks	
			"a.pay_rec_id  \n " +								

			"from rf_payments a \n " +

			"left join mf_bank b on a.bank_id = b.bank_id \n " +
			"left join mf_bank_branch c on a.bank_branch_id = c.bank_branch_id \n " +
			"left join mf_check_status d on a.check_stat_id = d.checkstat_id  \n " +
			"left join mf_unit_info e on a.pbl_id = e.pbl_id  \n " +
			"left join rf_entity f on a.entity_id = f.entity_id  \n " +
			"left join mf_project g on a.proj_id = g.proj_id \n " +
			"left join mf_check_bounce_reason h on a.bounce_reason_id = h.reason_id \n" +
			"left join mf_pay_particular i on a.pay_part_id = i.pay_part_id \n" + 

			"where a.pymnt_type = 'B' \n " +
			"and a.co_id = '"+lookupCompany.getText()+"'  " +
			"and trim(a.entity_id) = '" +entity_id+ "' " + 
			"and a.pbl_id = '"+pbl_id+"' and a.seq_no = "+seq_no+" " +
			"and a.check_stat_id = '04' \n" +

			"order by f.entity_name, a.check_date 	\n " +
			") a" ;  //

		System.out.printf("SQL: %s%n%n", sql);

		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalEntries(modelMain, modelTotal);
		}

		else {
			modelBC_total = new modelCheckStatus_total();
			modelBC_total.addRow(new Object[] { null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null });

			tblBC_total = new _JTableTotal(modelBC_total, tblBC);
			tblBC_total.hideColumns("Client Name","Unit PBL","Seq.","Reason of Bounce");

			tblBC_total.setFont(dialog11Bold);
			scrollBC_total.setViewportView(tblBC_total);
			((_JTableTotal) tblBC_total).setTotalLabel(1);
		}


		tblBC.packAll();
	}

	
	//Enable/Disable all components inside JPanel	
	public void refresh_tablesMain(){//used

		//reset table 1
		FncTables.clearTable(modelBC);
		FncTables.clearTable(modelBC_total);	
		rowHeaderBC = tblBC.getRowHeader();
		scrollBC.setRowHeaderView(rowHeaderBC);	
		modelBC_total.addRow(new Object[] { null, "Total", null, null, null, null, null, null, null, null, null});

	}

	public static void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d){		
		btnSave.setEnabled(a);		
		btnPreview.setEnabled(b);
		btnRefresh.setEnabled(c);
		btnCancel.setEnabled(d);		
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//used		

		if (e.getActionCommand().equals("Save")) { save(); }	//ok

		if(e.getActionCommand().equals("Refresh")){	refresh();	} //ok

		if(e.getActionCommand().equals("Cancel")) { cancel(); }  //ok
	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 1)) {}	
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

	public void save(){		

		if (checkTag()==true) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				
				String remarks   			= "";
				String new_checkstat_id 	= "";
				String prev_checkstat_id 	= "";
				String check_no 			= "";
				
				for(int x=0; x < modelBC.getRowCount(); x++){
					Boolean isTrue = false;
					if(modelBC.getValueAt(x,9) instanceof String){
						isTrue = new Boolean((String)modelBC.getValueAt(x,9));
					}
					if(modelBC.getValueAt(x,9) instanceof Boolean){
						isTrue = (Boolean)modelBC.getValueAt(x,9);
					}

					if(isTrue){	
						
						new_checkstat_id 	= "";
						prev_checkstat_id 	= "";
						check_no 			= "";
						remarks 			= "";

						if (cmbProcess.getSelectedIndex()==0){new_checkstat_id = "07";} else if (cmbProcess.getSelectedIndex()==0){new_checkstat_id = "09";} 
						try { prev_checkstat_id = gatCheckStatusID(modelBC.getValueAt(x,10).toString()); } catch (NullPointerException e) { prev_checkstat_id = ""; }
						try { check_no 			= modelBC.getValueAt(x,7).toString().trim(); } catch (NullPointerException e) { check_no = ""; }
						try { remarks 			= modelBC.getValueAt(x,14).toString().replace("'","''").trim(); } catch (NullPointerException e) { remarks = ""; }
						
						pgUpdate db = new pgUpdate();	

						saveCheckStatus(
								new_checkstat_id,									//new check status
								Calendar.getInstance().getTime(),					//transaction date
								pbl_id,												//pbl_id
								seq_no,												//seq_no
								check_no,											//check no
								remarks,											//remarks	
								db
						);

						insertCheckStatus(
								new_checkstat_id,									//new check status
								Calendar.getInstance().getTime(),					//transaction date
								pbl_id,												//pbl_id
								seq_no,												//seq_no
								check_no,											//check_no
								getPayRecId(check_no,pbl_id,seq_no),				//rec_id
								prev_checkstat_id,									//prev check status id
								"",													//deposit no
								db
						);
						
						db.commit(); 
					}														
				}

				JOptionPane.showMessageDialog(getContentPane(),"New check status(es) saved.","Information",JOptionPane.INFORMATION_MESSAGE);
				displayCheckList(modelBC, rowHeaderBC, modelBC_total);				
			}
		}

		else if (checkTag()==false) {JOptionPane.showMessageDialog(getContentPane(),"Select check for posting","Information",JOptionPane.WARNING_MESSAGE);}
	}

	private void refresh(){//used
		
		refresh_tablesMain();

		modelBC.setEditable(true);
		btnSave.setEnabled(true);
		btnRefresh.setEnabled(true);
		btnCancel.setEnabled(true);

		//JOptionPane.showMessageDialog(null,"Document's list refreshed.","Information",JOptionPane.INFORMATION_MESSAGE);	

	}

	private void cancel(){//used
		if (checkTag()==false)  {

			lookupClientName.setValue("");
			tagClientName.setTag("");
			txtUnit.setText("");
			tagUnit.setTag("");
			txtAcctStatus.setText("");
			tagStatus.setTag("");
			cmbProcess.setSelectedIndex(0);							
			
			lblUnit.setEnabled(false);	
			txtUnit.setEnabled(false);	
			tagUnit.setEnabled(false);	
			lblUnitStatus.setEnabled(false);	
			txtAcctStatus.setEnabled(false);
			tagStatus.setEnabled(false);	
			lblProcess.setEnabled(false);	
			cmbProcess.setEnabled(false);
			
			refresh_tablesMain();
			enableButtons(false, false, false, true);
		} 
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				lookupClientName.setValue("");
				tagClientName.setTag("");
				txtUnit.setText("");
				tagUnit.setTag("");
				txtAcctStatus.setText("");
				tagStatus.setTag("");
				cmbProcess.setSelectedIndex(0);							
				
				lblUnit.setEnabled(false);	
				txtUnit.setEnabled(false);	
				tagUnit.setEnabled(false);	
				lblUnitStatus.setEnabled(false);	
				txtAcctStatus.setEnabled(false);
				tagStatus.setEnabled(false);	
				lblProcess.setEnabled(false);	
				cmbProcess.setEnabled(false);
				
				refresh_tablesMain();
				enableButtons(false, false, false, true);			
			} 	else {}			
		}
	}

	
	//select statements
	public static String getClientList(){//used

		return 

		"select \r\n" + 
		"\r\n" + 
		"a.entity_id as \"Client ID\",\r\n" +    //0
		"upper(trim(b.entity_name)) as \"Client Name\",\r\n" +   //1
		"trim(c.unit_id) as \"Unit ID\",\r\n" +   //2
		"trim(c.description) as \"Unit Desc.\",\r\n" + //3
		"trim(a.currentstatus) as \"Buyer Status ID\",\r\n" + //4
		"trim(d.status_desc) as \"Buyer Status Name\",\r\n" + //5
		"a.pbl_id as \"PBL ID\",\r\n" + //5
		"a.seq_no as \"Seq. No.\"\r\n" +  //6
		"\r\n" + 
		"from rf_sold_unit a\r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id \r\n" + 
		"left join mf_unit_info c on a.projcode = c.proj_id and a.pbl_id = c.pbl_id \r\n" + 
		"left join mf_buyer_status d on a.currentstatus = d.byrstatus_id\r\n" + 
		"\r\n" + 
		"order by b.entity_name \r\n" + 
		"" ;			

	}

	public static String gatCheckStatusID(String stat_desc) {	

		String strSQL = "select checkstat_id from mf_check_status where trim(checkstat_desc) = '"+stat_desc+"' ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	
	public static Integer getPayRecId(String check_no, String pbl_id, String seq_no) {	

		String strSQL = 
			"select pay_rec_id from rf_payments " +
			"where trim(check_no) = '"+check_no+"' and " +
			"trim(pbl_id) = '"+pbl_id+"' and " +
			"seq_no = "+seq_no+" ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {return 0;}
			else { return (Integer) db.getResult()[0][0]; }		
		}else{
			return null;
		}
	}	
	

	//check and validate		
	private boolean checkTag() {		

		boolean go = false;

		for(int x=0; x < modelBC.getRowCount(); x++){
			Boolean isTrue = false;
			if(modelBC.getValueAt(x,9) instanceof String){
				isTrue = new Boolean((String)modelBC.getValueAt(x,9));
			}
			if(modelBC.getValueAt(x,9) instanceof Boolean){
				isTrue = (Boolean)modelBC.getValueAt(x,9);
			}

			if(isTrue){
				go = true;	
				break;
			}														
		}
		return go;

	}

	
	//table operations		
	public static void totalEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal total_check_amt = new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try {
				total_check_amt = total_check_amt.add(((BigDecimal) modelMain.getValueAt(x, 5)));//Dont forget to adjust column number
				System.out.printf("Credit: %s%n", total_check_amt);
			} catch (NullPointerException e) {
				total_check_amt = total_check_amt.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(new Object[] { null, "Total", null, null, null, total_check_amt, null, null });
	}	
	

	//save and insert	
	public void saveCheckStatus(String checkstat_id, Date status_dte, String pbl_id, String seq_no, String check_no, String remarks, pgUpdate db) {

		String sqlDetail = 
			"UPDATE rf_payments " +
			"set check_stat_id = '"+checkstat_id+"' , " +
			"remarks = '"+remarks+"'    \n"  +
			"where pbl_id = '"+pbl_id+"' " +
			"and seq_no = '"+seq_no+"' " +
			"and trim(check_no) = '"+check_no+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}
	
	public void insertCheckStatus(String checkstat_id, Date status_dte, String pbl_id, String seq_no, String check_no,
			Integer rec_id, String prev_checkstat_id, String dep_no, pgUpdate db) {

		String sqlDetail2 = 

			"INSERT into rf_check_history values (" +			
			" 	'"+rec_id+"',				--pay_rec_id		\n" +
			"	'"+prev_checkstat_id+"',	--prev_checkstat_id \n" +
			"	'"+checkstat_id+"',			--new_checkstat_id 	\n" +
			"	'',							--bouncereasonid 	\n" +
			"	'"+status_dte+"',			--trans_date    	\n" +
			"	'"+dep_no+"',				--dep_no	    	\n" +
			"	null,	    				--inactive_date		\n" +
			"	'A',						--status_id  		\n" +
			"	'" + UserInfo.EmployeeCode + "',	--created_by_id	    \n" +
			"	'"+status_dte+"',			--date_created    	\n" +
			"	null				   		--jv_no  		    \n" +
			")  \n\n"  ;

		System.out.printf("SQL #2: %s", sqlDetail2
		);
		db.executeUpdate(sqlDetail2, false);	
	}
	

}
