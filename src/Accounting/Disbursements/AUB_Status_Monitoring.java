package Accounting.Disbursements;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.model_AUB_Status_tagging;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class AUB_Status_Monitoring extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "AUB STATUS TAGGING AND MONITORING";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlTable;
	private JPanel pnlDProc;
	private JPanel pnlDProc_a1;
	private JPanel pnlDProc_a2;
	private JPanel pnlDProc_a2_1;
	private JPanel pnlDProc_a2_2;
	private JPanel pnlReq_Main;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;

	private JLabel lblCompany;
	private JLabel lblDateEdited;
	private JLabel lblJV_no;
	private JLabel lblFiscalyear;
	private JLabel lblPeriod;
	private JLabel lblDateTrans;
	private JLabel lblTransType;
	private JLabel lblDatePosted;
	public static JLabel lblStatus;

	public static _JLookup lookupCompany;
	private _JLookup lookupFiscalYr;
	private _JLookup lookupPeriod;
	private _JLookup lookupTranType;
	public static  _JLookup lookupStatus;

	private model_AUB_Status_tagging model_AUB_Status_tagging;
	private model_AUB_Status_tagging model_AUB_Status_tagging_total;

	public static _JTagLabel tagCompany;
	private _JTagLabel tagPeriod;
	public static  _JTagLabel tagStatus;	

	private static JButton btnSave;
	private static JButton btnRefresh;	
	private static JButton btnPreview;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JDateChooser dteTransaction;
	private _JDateChooser dteEdited;
	private _JDateChooser dtePosted;

	private JPopupMenu menu;	;
	private _JScrollPaneMain scrollDProc_account;
	private _JScrollPaneTotal scrollDProc_accounttotal;	

	private JTextArea txtJV_Remark;
	private JXTextField txtStatus;

	private JMenuItem mnidelete;
	private JMenuItem mniaddrow;		

	private _JTableMain tblDoc_proc;
	private JList rowHeaderDProc;
	private _JTableTotal tblDoc_proctotal;	

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	

	public static String co_id = "02";		
	public static String company = "";
	public static String company_logo;		
	public static String doc_no = "";
	String proc_no = "";	
	Integer return_row = 0;	
	String display_sql = "";
	private JPanel pnlDate;
	private JLabel lblDateFr;
	private _JDateChooser dteRefDateFr;
	private JLabel lblDateTo;
	private _JDateChooser dteRefDateTo;
	private JButton btnOK;	

	DecimalFormat df = new DecimalFormat("#,###,##0.00");

	Boolean inc_cash = true;
	String status_no = "1";
	String status_name = "WITH MC";
	private JPanel pnlDProc_a2_2b;
	private JPanel pnlSearch_a;
	private JPanel pnlDProc_a2_2b_1;
	private JLabel lblClientName;
	private JXTextField txtClientName;

	public AUB_Status_Monitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public AUB_Status_Monitoring(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public AUB_Status_Monitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(984, 545));
		this.setBounds(0, 0, 984, 545);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		pnlReq_Main = new JPanel();		
		menu = new JPopupMenu("Popup");
		mnidelete = new JMenuItem("Remove Row      ");
		mniaddrow = new JMenuItem("Add Row");
		pnlReq_Main.add(menu);
		menu.add(mnidelete);
		menu.add(mniaddrow);
		mnidelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){

			}
		});
		mniaddrow.addActionListener(new ActionListener(){
			public void	actionPerformed(ActionEvent evt){

			}
		});	

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(821, 81));		

			pnlDProc = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlDProc, BorderLayout.CENTER);				
			pnlDProc.setPreferredSize(new java.awt.Dimension(921, 85));			
			pnlDProc.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));			

			pnlDProc_a1 = new JPanel(new GridLayout(2, 1, 5, 10));
			pnlDProc.add(pnlDProc_a1, BorderLayout.WEST);	
			pnlDProc_a1.setPreferredSize(new java.awt.Dimension(106, 112));	
			pnlDProc_a1.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			{
				lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
				pnlDProc_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(105, 25));
				lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}
			{
				lblStatus = new JLabel("Status", JLabel.TRAILING);
				pnlDProc_a1.add(lblStatus);
				lblStatus.setEnabled(true);	
				lblStatus.setPreferredSize(new java.awt.Dimension(106, 40));
				lblStatus.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
				lblStatus.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}	

			pnlDProc_a2 = new JPanel(new BorderLayout(5, 5));
			pnlDProc.add(pnlDProc_a2, BorderLayout.CENTER);	
			pnlDProc_a2.setPreferredSize(new java.awt.Dimension(814, 40));	
			pnlDProc_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			pnlDProc_a2_1 = new JPanel(new GridLayout(2, 1, 5, 10));
			pnlDProc_a2.add(pnlDProc_a2_1, BorderLayout.WEST);	
			pnlDProc_a2_1.setPreferredSize(new java.awt.Dimension(101, 24));	

			{
				lookupCompany = new _JLookup(null, "Company",0,2);
				pnlDProc_a2_1.add(lookupCompany);
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

							enableButtons(true, true, true);
						}
					}
				});
			}	
			{
				lookupStatus = new _JLookup(null, "Status", 2, 2);
				pnlDProc_a2_1.add(lookupStatus);
				lookupStatus.setBounds(20, 27, 20, 25);
				lookupStatus.setReturnColumn(0);
				lookupStatus.setEnabled(true);	
				lookupStatus.setLookupSQL(getStatus());
				lookupStatus.setPreferredSize(new java.awt.Dimension(100, 24));
				lookupStatus.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							status_no 		= (String) data[0];	
							status_name		= (String) data[1];	
							tagStatus.setTag((String) data[1]);
						}
					}
				});


				pnlDProc_a2_2 = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlDProc_a2.add(pnlDProc_a2_2, BorderLayout.CENTER);	
				pnlDProc_a2_2.setPreferredSize(new java.awt.Dimension(357, 25));	

				{
					tagCompany = new _JTagLabel("[ ]");
					pnlDProc_a2_2.add(tagCompany);
					tagCompany.setBounds(209, 27, 700, 22);
					tagCompany.setEnabled(true);	
					tagCompany.setPreferredSize(new java.awt.Dimension(380, 24));
				}
				{
					pnlDProc_a2_2b = new JPanel(new BorderLayout(5, 5));
					pnlDProc_a2_2.add(pnlDProc_a2_2b, BorderLayout.SOUTH);	
					pnlDProc_a2_2b.setPreferredSize(new java.awt.Dimension(357, 25));						

					{
						pnlDProc_a2_2b_1 = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlDProc_a2_2b.add(pnlDProc_a2_2b_1, BorderLayout.CENTER);	
						pnlDProc_a2_2b_1.setPreferredSize(new java.awt.Dimension(357, 25));	

						tagStatus = new _JTagLabel("[ ]");
						pnlDProc_a2_2b_1.add(tagStatus);
						tagStatus.setBounds(209, 27, 700, 22);
						tagStatus.setEnabled(true);	
						tagStatus.setPreferredSize(new java.awt.Dimension(380, 24));
					}

					{						
						pnlSearch_a = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlDProc_a2_2b.add(pnlSearch_a, BorderLayout.EAST);	
						pnlSearch_a.setPreferredSize(new java.awt.Dimension(508, 26));

						{	
							Color maroon = new Color (128, 0, 0);
							lblClientName = new JLabel("Search Doc. No. : ", JLabel.TRAILING);
							pnlSearch_a.add(lblClientName);
							lblClientName.setEnabled(true);	
							lblClientName.setPreferredSize(new java.awt.Dimension(98, 26));
							lblClientName.setFont(new java.awt.Font("Segoe UI",Font.ITALIC + Font.BOLD,12));
							lblClientName.setForeground(maroon);
						}	

							txtClientName= new JXTextField("");
							pnlSearch_a.add(txtClientName);
							txtClientName.setEnabled(true);	
							txtClientName.setEditable(true);
							txtClientName.setBounds(120, 25, 300, 22);	
							txtClientName.setHorizontalAlignment(JTextField.LEFT);	
							txtClientName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
							txtClientName.setPreferredSize(new java.awt.Dimension(573, 42));
							txtClientName.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {	
									checkAllCVList();
								}				 
							});	
						}
				}	
			}	
			{
				pnlTable = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlTable, BorderLayout.CENTER);	
				pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

				pnlDProc = new JPanel();
				pnlTable.add(pnlDProc, BorderLayout.CENTER);
				pnlDProc.setLayout(new BorderLayout(5, 5));
				pnlDProc.setPreferredSize(new java.awt.Dimension(923, 199));
				pnlDProc.setBorder(lineBorder);		
				pnlDProc.addMouseListener(new PopupTriggerListener_panel());			

				{	
					{
						scrollDProc_account = new _JScrollPaneMain();
						pnlDProc.add(scrollDProc_account, BorderLayout.CENTER);
						{
							model_AUB_Status_tagging = new model_AUB_Status_tagging();

							tblDoc_proc = new _JTableMain(model_AUB_Status_tagging);
							scrollDProc_account.setViewportView(tblDoc_proc);
							tblDoc_proc.getColumnModel().getColumn(3).setPreferredWidth(40);
							tblDoc_proc.addMouseListener(this);	
							tblDoc_proc.setSortable(false);
							tblDoc_proc.addMouseListener(new PopupTriggerListener_panel());
							tblDoc_proc.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {

								}							
								public void keyPressed(KeyEvent e) {

								}

							}); 
							tblDoc_proc.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblDoc_proc.rowAtPoint(e.getPoint()) == -1){
										tblDoc_proctotal.clearSelection();

									}else{
										tblDoc_proc.setCellSelectionEnabled(true);

									}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblDoc_proc.rowAtPoint(e.getPoint()) == -1){
										tblDoc_proctotal.clearSelection();

									}else{
										tblDoc_proc.setCellSelectionEnabled(true);

									}
								}
							});						
						}
						{
							rowHeaderDProc = tblDoc_proc.getRowHeader22();
							scrollDProc_account.setRowHeaderView(rowHeaderDProc);
							scrollDProc_account.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollDProc_accounttotal = new _JScrollPaneTotal(scrollDProc_account);
						pnlDProc.add(scrollDProc_accounttotal, BorderLayout.SOUTH);
						{
							model_AUB_Status_tagging_total = new model_AUB_Status_tagging();
							model_AUB_Status_tagging_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00), null });

							tblDoc_proctotal = new _JTableTotal(model_AUB_Status_tagging_total, tblDoc_proc);
							tblDoc_proctotal.setFont(dialog11Bold);
							scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
							((_JTableTotal) tblDoc_proctotal).setTotalLabel(0);
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
					btnSave.setEnabled(true);
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

								} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {

								}				
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
			}

			//added 01/26/2016 - purpose - set CENQHOMES as default company
			initialize_comp();

		}
		{
			pnlDate= new JPanel();
			pnlDate.setLayout(null);
			pnlDate.setPreferredSize(new java.awt.Dimension(80, 110));
			
			{
				lblDateFr = new JLabel();
				pnlDate.add(lblDateFr);
				lblDateFr.setBounds(10, 5, 160, 20);
				lblDateFr.setText("Date from :");
			}
			{
				dteRefDateFr = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlDate.add(dteRefDateFr);
				dteRefDateFr.setBounds(80, 5, 125, 21);
				dteRefDateFr.setDate(null);
				dteRefDateFr.setEnabled(true);
				dteRefDateFr.setDateFormatString("yyyy-MM-dd");
				((JTextFieldDateEditor)dteRefDateFr.getDateEditor()).setEditable(false);
				dteRefDateFr.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				dteRefDateFr.addPropertyChangeListener( new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
					}
				});	
			}
			{
				lblDateTo = new JLabel();
				pnlDate.add(lblDateTo);
				lblDateTo.setBounds(10, 38, 160, 20);
				lblDateTo.setText("Date to :");
			}
			{
				dteRefDateTo = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlDate.add(dteRefDateTo);
				dteRefDateTo.setBounds(80, 38, 125, 21);
				dteRefDateTo.setDate(null);
				dteRefDateTo.setEnabled(true);
				dteRefDateTo.setDateFormatString("yyyy-MM-dd");
				((JTextFieldDateEditor)dteRefDateTo.getDateEditor()).setEditable(false);
				dteRefDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				dteRefDateTo.addPropertyChangeListener( new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
					}
				});	
			}
			{
				btnOK = new JButton();
				pnlDate.add(btnOK);
				btnOK.setBounds(95, 80, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
						optionPaneWindow.dispose();				

						preview();

						/*else if(FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11")==false ) 
					{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print list of identified deposit. \n"
							+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE); }	
						 */

					}
				});
			}
		}
	}


	//display tables
	public void displaCV_forMCtagging(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 		

			"select " +
			"c_pv_no, \r\n" + 
			"(case when c_with_mc = 'Yes' then true else false end), \r\n" + 
			"c_entity_name, \r\n" + 
			"c_check_date, \r\n" + 
			"c_check_no, \r\n" + 
			"c_chk_amt, \r\n" + 
			"c_date_rlsd \r\n" + 
			"from view_aub_status_tagging_with_mc('"+lookupCompany.getValue()+"', null, null )" ;

		//System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			model_AUB_Status_tagging_total = new model_AUB_Status_tagging();
			model_AUB_Status_tagging_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), null });

			tblDoc_proctotal = new _JTableTotal(model_AUB_Status_tagging_total, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	
		tblDoc_proc.getColumnModel().getColumn(2).setPreferredWidth(400);
		tblDoc_proc.getColumnModel().getColumn(5).setPreferredWidth(100);

		adjustRowHeight_account();
		computeTotal();

	}	


	//Enable/Disable all components inside JPanel	
	public void enable_fields(Boolean x){//

		lblJV_no.setEnabled(x);	
		lblStatus.setEnabled(x);	
		txtStatus.setEnabled(x);	

		lblDateTrans.setEnabled(x);
		dteTransaction.setEnabled(x);
		lblDateEdited.setEnabled(x);	
		dteEdited.setEnabled(x);
		lblDatePosted.setEnabled(x);	
		dtePosted.setEnabled(x);

		lblTransType.setEnabled(x);	
		lblFiscalyear.setEnabled(x);	
		lblPeriod.setEnabled(x);	

		lookupTranType.setEnabled(x);	
		lookupFiscalYr.setEnabled(x);	
		lookupPeriod.setEnabled(x);	
		lookupPeriod.setEditable(x);

		tagPeriod.setEnabled(x);	
		txtJV_Remark.setEditable(x);		
	}

	public void refresh_tablesMain(){//used

		//reset table 1		
		displaCV_forMCtagging(model_AUB_Status_tagging, rowHeaderDProc, model_AUB_Status_tagging_total );
		JOptionPane.showMessageDialog(null,"Table refreshed","Information",JOptionPane.INFORMATION_MESSAGE);	
	}

	public static void enableButtons( Boolean a, Boolean b, Boolean c){		
		btnSave.setEnabled(a);		
		btnPreview.setEnabled(b);
		btnRefresh.setEnabled(c);	
	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = sql_getCompanyLogo();	

		enableButtons(true, true, true);
		lookupCompany.setValue(co_id);
		inc_cash = true;
		lookupStatus.setValue(status_no);
		tagStatus.setTag(status_name);

		displaCV_forMCtagging(model_AUB_Status_tagging, rowHeaderDProc, model_AUB_Status_tagging_total );
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//used		

		if (e.getActionCommand().equals("Save")) { save(); }	//ok
		
		if(e.getActionCommand().equals("Refresh")){	refresh();	} //ok
	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 1)) {
			//clickTableColumn_account();
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

	private void save(){

		if(checkTaggedRow()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Select Row", "Information", 
				JOptionPane.WARNING_MESSAGE);}

		else {	

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {					

				pgUpdate db = new pgUpdate();				
				insertAUB_CV_details();				

				db.commit();					
				JOptionPane.showMessageDialog(getContentPane(),"Document's status updated.","Information",JOptionPane.INFORMATION_MESSAGE);	
				displaCV_forMCtagging(model_AUB_Status_tagging, rowHeaderDProc, model_AUB_Status_tagging_total );
			}
		}		
	}

	private void refresh(){//used			
		
		refresh_tablesMain();

	}

	private void preview(){//used
		
		String criteria = "AUB Status Tagging - With MC";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());			

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("date_from", dteRefDateFr.getDate());	
		mapParameters.put("date_to", dteRefDateTo.getDate());	

		FncReport.generateReport("/Reports/rptAUB_CheckStatusTagging.jasper", reportTitle, company, mapParameters); 
	}


	//select statements
	private static String getStatus(){//used

		return 

		"select '1' as \"Status No.\" ,   'WITH MC' as \"Status Name\"  \n" ;

	}

	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = 
			"select company_logo from mf_company \n" + 
			"where co_id = '"+co_id+"' " ;

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


	//check and validate		
	private Boolean checkTaggedRow(){//

		boolean comp = false;
		int rw = tblDoc_proc.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			Boolean isTrue = false;
			if(model_AUB_Status_tagging.getValueAt(x,1) instanceof String){
				isTrue = new Boolean((String)model_AUB_Status_tagging.getValueAt(x,1));
			}
			if(model_AUB_Status_tagging.getValueAt(x,1) instanceof Boolean){
				isTrue = (Boolean)model_AUB_Status_tagging.getValueAt(x,1);
			}

			if(isTrue){					
				comp = true; break;			
			}

			x++;
		}

		return comp;
	}
	

	//table operations		
	private void adjustRowHeight_account(){//used
		int rw = tblDoc_proc.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblDoc_proc.setRowHeight(x, 22);			
			x++;
		}

	}


	//save and insert		
	public void insertAUB_CV_details(){

		for(int y=0; y < model_AUB_Status_tagging.getRowCount(); y++){
			Boolean isTrue = false;
			if(model_AUB_Status_tagging.getValueAt(y,1) instanceof String){
				isTrue = new Boolean((String)model_AUB_Status_tagging.getValueAt(y,1));
			}
			if(model_AUB_Status_tagging.getValueAt(y,1) instanceof Boolean){
				isTrue = (Boolean)model_AUB_Status_tagging.getValueAt(y,1);
			}

			if(isTrue){	
				String doc_no = model_AUB_Status_tagging.getValueAt(y,0).toString().trim();
				String SQL = "insert into rf_aub_epayment_status " +
						" values ('"+doc_no+"',1,true,'A','"+UserInfo.EmployeeCode+"',now(),null,null,'"+co_id+"') " ;

				pgUpdate db = new pgUpdate();
				db.executeUpdate(SQL, false);
				db.commit();	
			}
		}
	}
	
	private void computeTotal() {
		BigDecimal totalAmountCommitted = new BigDecimal("0.00");

		for (int x = 0; x < model_AUB_Status_tagging.getRowCount(); x++) {
			BigDecimal totalamount = (BigDecimal) model_AUB_Status_tagging.getValueAt(x, 5);

			try {
				totalAmountCommitted = totalAmountCommitted.add(totalamount);
			} catch (Exception e1) { }
		}

		model_AUB_Status_tagging_total.setValueAt(df.format(totalAmountCommitted), 0, 5);
	}

	private void checkAllCVList(){//

		int rw = tblDoc_proc.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";

			try { name	= tblDoc_proc.getValueAt(x,0).toString().toUpperCase();}//make sure it's not modelAgentAccount, otherwise, row number will be fixed 
			catch (NullPointerException e) { name	= ""; }

			String acct_name	= txtClientName.getText().trim().toUpperCase();	
			Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			Boolean	end			= name.endsWith(acct_name);

			if (match==true||start==true||end==true) {
				tblDoc_proc.setRowSelectionInterval(x, x); 
				tblDoc_proc.changeSelection(x, 0, false, false);				
				break;			
			}
			else {
				tblDoc_proc.setRowSelectionInterval(0, 0); 
				tblDoc_proc.changeSelection(0, 0, false, false);					
			}

			x++;

		}		
	}


}