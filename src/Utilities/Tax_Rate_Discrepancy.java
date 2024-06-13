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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import tablemodel.modelListof_ReleasedComm_perEntity;
import tablemodel.modelTaxRateDisc;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Functions.FncTables;
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

public class Tax_Rate_Discrepancy extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "List of Entities With WTax Rate Discrepancy";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlEntityList;
	private JPanel pnlTable;
	private JPanel pnlDisbursed;
	private JPanel pnlComp;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlEntityList_a;

	private _JScrollPaneMain scrollEntityList;	
	private _JScrollPaneMain scrollDisbursed;	
	private static _JScrollPaneTotal scrollEntityList_total;

	private static modelTaxRateDisc modelEntityList;
	private static modelListof_ReleasedComm_perEntity modelDisbursed;
	private static modelListof_ReleasedComm_perEntity modelDisbursedList_total;
	private static modelTaxRateDisc modelEntityList_total;

	private static _JTableMain tblEntityList;
	private static _JTableMain tblDisbursed;	

	static _JLookup lookupCompany;		
	
	static _JTagLabel tagCompany;
	private static JList rowHeaderEntityList;
	private static JList rowHeaderDisbursed;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	private JLabel lblCompany;
	private static _JTableTotal tblEntityList_total;
	
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public static String company = "";
	public static String company_logo;
	static String co_id = "";
	private JPanel pnlDisbursed_a;
	private _JScrollPaneTotal scrollDisbursedList_total;
	private _JTableTotal tblDisbursedList_total;
	private JSplitPane splitPane;	

	

	public Tax_Rate_Discrepancy() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Tax_Rate_Discrepancy(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Tax_Rate_Discrepancy(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(901, 571));
		this.setBounds(0, 0, 901, 571);		

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
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 38));				

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
							displayEntityList(modelEntityList, rowHeaderEntityList, 1, "A", "");

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
		}		
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			pnlTable.add(splitPane);
			splitPane.setOneTouchExpandable(true);
			splitPane.setResizeWeight(.4d);
			
			pnlEntityList = new JPanel();
			splitPane.add(pnlEntityList, JSplitPane.LEFT);
			pnlEntityList.setLayout(new BorderLayout(5, 5));
			pnlEntityList.setPreferredSize(new java.awt.Dimension(921, 242));
			pnlEntityList.setBorder(lineBorder);
			pnlEntityList.setBorder(JTBorderFactory.createTitleBorder("List of Entities"));			

			//Progress Billing
			{
				pnlEntityList_a = new JPanel(new BorderLayout());
				pnlEntityList.add(pnlEntityList_a, BorderLayout.CENTER);
				pnlEntityList_a.setPreferredSize(new java.awt.Dimension(919, 212));
								
				{
					scrollEntityList = new _JScrollPaneMain();
					pnlEntityList_a.add(scrollEntityList, BorderLayout.CENTER);
					{
						modelEntityList = new modelTaxRateDisc();

						tblEntityList = new _JTableMain(modelEntityList);
						scrollEntityList.setViewportView(tblEntityList);
						tblEntityList.addMouseListener(this);
						tblEntityList.setSortable(false);
						tblEntityList.getColumnModel().getColumn(1).setPreferredWidth(80);
						tblEntityList.getColumnModel().getColumn(2).setPreferredWidth(200);
						tblEntityList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){displaySubAccountList(modelDisbursed, rowHeaderDisbursed);
								}
							}
						});
					}
					{
						rowHeaderEntityList = tblEntityList.getRowHeader22();
						scrollEntityList.setRowHeaderView(rowHeaderEntityList);
						scrollEntityList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollEntityList_total = new _JScrollPaneTotal(scrollEntityList);
						pnlEntityList_a.add(scrollEntityList_total, BorderLayout.SOUTH);
						{
							modelEntityList_total = new modelTaxRateDisc();
							modelEntityList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

							tblEntityList_total = new _JTableTotal(modelEntityList_total, tblEntityList);
							tblEntityList_total.setFont(dialog11Bold);
							scrollEntityList_total.setViewportView(tblEntityList_total);
							((_JTableTotal) tblEntityList_total).setTotalLabel(0);
						}
					}
				}				
			}
			
			pnlDisbursed = new JPanel(new BorderLayout(5, 5));
			splitPane.add(pnlDisbursed, JSplitPane.RIGHT);
			pnlDisbursed.setPreferredSize(new java.awt.Dimension(921, 274));
			pnlDisbursed.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			pnlDisbursed.setBorder(JTBorderFactory.createTitleBorder("List of Disbursed Amounts"));			
			
			{			
				pnlDisbursed_a = new JPanel(new BorderLayout());
				pnlDisbursed.add(pnlDisbursed_a, BorderLayout.CENTER);
				pnlDisbursed_a.setPreferredSize(new java.awt.Dimension(921, 205));				
				
				{
					scrollDisbursed = new _JScrollPaneMain();
					pnlDisbursed_a.add(scrollDisbursed, BorderLayout.CENTER);
					{
						modelDisbursed = new modelListof_ReleasedComm_perEntity();
						
						tblDisbursed = new _JTableMain(modelDisbursed);
						scrollDisbursed.setViewportView(tblDisbursed);
						tblDisbursed.getColumnModel().getColumn(4).setPreferredWidth(100);
						tblDisbursed.getColumnModel().getColumn(5).setPreferredWidth(100);
						tblDisbursed.getColumnModel().getColumn(6).setPreferredWidth(100);
						tblDisbursed.getColumnModel().getColumn(7).setPreferredWidth(100);
						
						tblDisbursed.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {
									
								}
							}
							public void mouseReleased(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {
									
								}
							}
						});
						
						tblDisbursed.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								
							}						
							public void keyPressed(KeyEvent e) {
								
							}
							
						});
						
						
					}
					{
						rowHeaderDisbursed = tblDisbursed.getRowHeader22();
						scrollDisbursed.setRowHeaderView(rowHeaderDisbursed);
						scrollDisbursed.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollDisbursedList_total = new _JScrollPaneTotal(scrollDisbursed);
						pnlDisbursed_a.add(scrollDisbursedList_total, BorderLayout.SOUTH);
						{
							modelDisbursedList_total = new modelListof_ReleasedComm_perEntity();
							modelDisbursedList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
							
							tblDisbursedList_total = new _JTableTotal(modelDisbursedList_total, tblDisbursed);
							tblDisbursedList_total.setFont(dialog11Bold);
							scrollDisbursedList_total.setViewportView(tblDisbursedList_total);
							((_JTableTotal) tblDisbursedList_total).setTotalLabel(0);
						}
					}
				}
			}

		} 
		/*
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
					btnAddNew = new JButton("Add New");
					pnlSouthCenter.add(btnAddNew);
					btnAddNew.setActionCommand("Add");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenter.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenter.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
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
		*/
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display
	public static void displayEntityList(DefaultTableModel modelMain, JList rowHeader, Integer level, String status, String acct) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select * from get_list_of_entity_with_wtax_rate_diff('"+co_id+"') n" ;

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

		tblEntityList.packAll();	
		adjustRowHeight(tblEntityList);

		int row = tblEntityList.getRowCount();			
		modelEntityList_total.setValueAt(new BigDecimal(row), 0, 1);
		
	}	
	
	private void displaySubAccountList(DefaultTableModel modelMain, JList rowHeader) {		

		int row = tblEntityList.getSelectedRow();			

		String entity_id = tblEntityList.getValueAt(row,0).toString().trim();		
		String entity_name = tblEntityList.getValueAt(row,1).toString().trim();		
		
		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select c_pv_no, c_pv_date, c_wtax_rate, c_wtax_bir_code, c_gross_comm, c_wtax_amt, c_caliq_amt, c_applied_amt " +
			"from get_list_of_entity_released_comm('"+co_id+"','"+entity_id+"')";

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

		//tblDisbursed.packAll();	
		adjustRowHeight(tblDisbursed);		
		totalProgBill(modelDisbursed, modelDisbursedList_total);
		pnlDisbursed.setBorder(JTBorderFactory.createTitleBorder("List of Disbursed Amounts (Entity : " + entity_name + ")"   ));
	}	
	
	//Enable/Disable all components inside JPanel
	public void setComponentEnabled(JPanel panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			comp.setEnabled(enable);
		}
	}		

	public void refresh_tablesMain(){

		//reset table 1
		FncTables.clearTable(modelEntityList);
		FncTables.clearTable(modelEntityList_total);			
		rowHeaderEntityList = tblEntityList.getRowHeader22();
		scrollEntityList.setRowHeaderView(rowHeaderEntityList);	
		modelEntityList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

		//reset table 2
		FncTables.clearTable(modelDisbursed);
		FncTables.clearTable(modelDisbursedList_total);			
		rowHeaderDisbursed = tblDisbursed.getRowHeader22();
		scrollDisbursed.setRowHeaderView(rowHeaderDisbursed);	
		modelDisbursedList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
		
		
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		KEYBOARD_MANAGER.focusNextComponent();			
		displayEntityList(modelEntityList, rowHeaderEntityList, 1, "A", "");
		
		lookupCompany.setValue(co_id);
		pnlDisbursed.setBorder(JTBorderFactory.createTitleBorder("List of Disbursed Amounts"));	
}
	

	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {		
		
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

	//table operations	
	private static void adjustRowHeight(_JTableMain tbl){		

		int rw = tbl.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tbl.setRowHeight(x, 22);			
			x++;
		}
	}

	public static void totalProgBill(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal gross_amt 	= new BigDecimal(0.00);
		BigDecimal wtax_amt 	= new BigDecimal(0.00);
		BigDecimal ca_liq_amt 	= new BigDecimal(0.00);
		BigDecimal net_amt 		= new BigDecimal(0.00);
		
		//String billing_status   = "";

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){				

			try { gross_amt 	= gross_amt.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { gross_amt 	= gross_amt.add(new BigDecimal(0.00)); }
			
			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }
			
			try { ca_liq_amt 	= ca_liq_amt.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { ca_liq_amt = ca_liq_amt.add(new BigDecimal(0.00)); }
			
			try { net_amt 	= net_amt.add(((BigDecimal) modelMain.getValueAt(x,7)));} 
			catch (NullPointerException e) { net_amt 	= net_amt.add(new BigDecimal(0.00)); }
			
		}

		modelTotal.addRow(new Object[] { "Total", null, null, null, gross_amt, wtax_amt, ca_liq_amt, net_amt});

	}
	


}
