package Accounting.Commissions;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import tablemodel.modelATM_process;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class ATM_processing extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "ATM Processing";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlATMdetails;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlATM_no_b3;
	private JPanel pnlDate;
	private JPanel pnlPreviewOption;

	private JLabel lblCompany;
	private JLabel lblATMdate;	
	private JLabel lblDate;

	private modelATM_process modelATM_onprocess;
	private modelATM_process modelATM_onprocess_total;

	private _JLookup lookupCompany;

	private JButton btnSave;
	private JButton btnAdd;
	private JButton btnTag;
	private JButton btnCancel;	
	private JButton btnOK;
	private JButton btnPreview;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTagLabel tagCompany;

	private _JScrollPaneMain scrollATM_onprocess;
	private _JScrollPaneTotal scrollATM_onprocesstotal;
	private _JTableMain tblATM_onprocess;
	private JList rowHeaderATM_onprocess;
	private _JTableTotal tblATM_onprocess_total;
	private _JDateChooser dateATM;	
	private _JDateChooser dteRefDate;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	String co_id 	= "";		
	String company 	= "";
	String to_do 	= "";
	String company_logo;

	private JCheckBox chkOnProcess;
	private JCheckBox chkForTagging;
	private JButton btnRelease;
	private JButton btnX;
	private JCheckBox chkForRelease;

	public ATM_processing() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ATM_processing(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public ATM_processing(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(944, 516));
		this.setBounds(0, 0, 944, 516);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{ //start of Company

			pnlComp = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(907, 35));	
			pnlComp.setBorder(lineBorder);

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(1045, 36));	
				pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));			

				{
					pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(172, 31));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

					{
						lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
						pnlComp_a1.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(115, 25));
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
									String name = (String) data[1];	
									company		= name;
									tagCompany.setTag(name);
									company_logo = (String) data[3];

									lblATMdate.setEnabled(true);

									enableButtons(true,true,true, false,true,false);
									refresh_Table();
								}
							}
						});
					}	
				}
				{
					pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
					pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
					pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));

					{
						tagCompany = new _JTagLabel("[ ]");
						pnlComp_a2.add(tagCompany);
						tagCompany.setBounds(209, 27, 700, 22);
						tagCompany.setEnabled(true);	
						tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
				}
				{
					pnlATM_no_b3 = new JPanel(new GridLayout(1, 2,5,0));
					pnlComp_a.add(pnlATM_no_b3, BorderLayout.EAST);	
					pnlATM_no_b3.setPreferredSize(new java.awt.Dimension(310, 20));	
					pnlATM_no_b3.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));		

					{
						lblATMdate = new JLabel("Date  ", JLabel.TRAILING);
						pnlATM_no_b3.add(lblATMdate);
						lblATMdate.setBounds(8, 11, 62, 12);
						lblATMdate.setEnabled(false);
						lblATMdate.setPreferredSize(new java.awt.Dimension(96, 26));
						lblATMdate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						lblATMdate.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}
					{
						dateATM = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlATM_no_b3.add(dateATM);
						dateATM.setBounds(485, 7, 125, 21);
						dateATM.setDate(Calendar.getInstance().getTime());
						dateATM.setEnabled(false);
						dateATM.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dateATM.getDateEditor()).setEditable(false);
						dateATM.addPropertyChangeListener( new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent e) {

							}
						});	
					}	
				}
			}										
		} //end of Company

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlSubTable.setBorder(lineBorder);		

			{						

				pnlATMdetails = new JPanel();
				pnlSubTable.add(pnlATMdetails, BorderLayout.CENTER);
				pnlATMdetails.setLayout(new BorderLayout(0,0));
				pnlATMdetails.setBorder(lineBorder);		
				pnlATMdetails.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlATMdetails.setBorder(JTBorderFactory.createTitleBorder("Agent Details"));

				//start of Commission Schedule (by client)
				{			

					{
						scrollATM_onprocess = new _JScrollPaneMain();
						pnlATMdetails.add(scrollATM_onprocess, BorderLayout.CENTER);
						{
							modelATM_onprocess = new modelATM_process();

							tblATM_onprocess = new _JTableMain(modelATM_onprocess);
							scrollATM_onprocess.setViewportView(tblATM_onprocess);
							tblATM_onprocess.addMouseListener(this);								
							tblATM_onprocess.setSortable(false);
							modelATM_onprocess.setEditable(true);
							tblATM_onprocess.setEditable(true);
							tblATM_onprocess.getColumnModel().getColumn(0).setPreferredWidth(50);
							tblATM_onprocess.getColumnModel().getColumn(1).setPreferredWidth(100);
							tblATM_onprocess.getColumnModel().getColumn(2).setPreferredWidth(300);
							tblATM_onprocess.getColumnModel().getColumn(3).setPreferredWidth(100);
							tblATM_onprocess.getColumnModel().getColumn(4).setPreferredWidth(100);
							tblATM_onprocess.getColumnModel().getColumn(5).setPreferredWidth(100);
							tblATM_onprocess.getColumnModel().getColumn(5).setPreferredWidth(120);
							tblATM_onprocess.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {}							
								public void keyPressed(KeyEvent e) {}	

							}); 
							tblATM_onprocess.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblATM_onprocess.rowAtPoint(e.getPoint()) == -1){}
									else{tblATM_onprocess.setCellSelectionEnabled(true);}

								}
								public void mouseReleased(MouseEvent e) {
									if(tblATM_onprocess.rowAtPoint(e.getPoint()) == -1){}
									else{tblATM_onprocess.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderATM_onprocess = tblATM_onprocess.getRowHeader22();
							scrollATM_onprocess.setRowHeaderView(rowHeaderATM_onprocess);
							scrollATM_onprocess.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollATM_onprocesstotal = new _JScrollPaneTotal(scrollATM_onprocess);
						pnlATMdetails.add(scrollATM_onprocesstotal, BorderLayout.SOUTH);
						{
							modelATM_onprocess_total = new modelATM_process();
							modelATM_onprocess_total.addRow(new Object[] { "Total" , new BigDecimal(0.00) });

							tblATM_onprocess_total = new _JTableTotal(modelATM_onprocess_total, tblATM_onprocess);
							tblATM_onprocess_total.setFont(dialog11Bold);
							scrollATM_onprocesstotal.setViewportView(tblATM_onprocess_total);
							((_JTableTotal) tblATM_onprocess_total).setTotalLabel(0);
						}
					}
				} 
				//end of Commission Schedule (by client)
			}

		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(914, 42));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(912, 37));
			{
				{
					btnAdd = new JButton("<html><center>Display Agents<center><html>" +
					"<html><center> For ATM Processing<center><html>");
					pnlSouthCenterb.add(btnAdd);
					btnAdd.setActionCommand("Add");
					btnAdd.addActionListener(this);
					btnAdd.setEnabled(false);
				}
				{
					btnTag = new JButton("<html><center>Display Agents<center><html>" +
					"<html><center> For ATM Tagging<center><html>");
					pnlSouthCenterb.add(btnTag);
					btnTag.setActionCommand("Tag");
					btnTag.addActionListener(this);
					btnTag.setEnabled(false);
				}
				{
					btnRelease = new JButton("<html><center>Display Agents<center><html>" +
					"<html><center> For ATM Release<center><html>");
					pnlSouthCenterb.add(btnRelease);
					btnRelease.setActionCommand("Release");
					btnRelease.addActionListener(this);
					btnRelease.setEnabled(false);
				}
				{
					btnX = new JButton("");
					pnlSouthCenterb.add(btnX);
					btnX.setActionCommand("Tag");
					btnX.addActionListener(this);
					btnX.setVisible(false);
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenterb.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
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
				lblDate.setText("Tagging Date :");
			}
			{
				dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlDate.add(dteRefDate);
				dteRefDate.setBounds(130, 15, 125, 21);
				dteRefDate.setDate(null);
				dteRefDate.setEnabled(true);
				dteRefDate.setDateFormatString("yyyy-MM-dd");
				((JTextFieldDateEditor)dteRefDate.getDateEditor()).setEditable(false);
				dteRefDate.setDate(Calendar.getInstance().getTime());
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
			pnlPreviewOption= new JPanel();
			pnlPreviewOption.setLayout(null);
			pnlPreviewOption.setPreferredSize(new java.awt.Dimension(370, 80));

			{
				chkOnProcess = new JCheckBox("ATM On Process");
				pnlPreviewOption.add(chkOnProcess);
				chkOnProcess.setEnabled(true);	
				chkOnProcess.setSelected(true);	
				chkOnProcess.setPreferredSize(new java.awt.Dimension(383, 26));
				chkOnProcess.setBounds(5, 15, 130, 20);
			}
			{
				chkForTagging = new JCheckBox("Processed ATM");
				pnlPreviewOption.add(chkForTagging);
				chkForTagging.setEnabled(true);	
				chkForTagging.setSelected(true);	
				chkForTagging.setPreferredSize(new java.awt.Dimension(383, 26));
				chkForTagging.setBounds(130, 15, 125, 21);
			}
			{
				chkForRelease = new JCheckBox("Released ATM");
				pnlPreviewOption.add(chkForRelease);
				chkForRelease.setEnabled(true);	
				chkForRelease.setSelected(true);	
				chkForRelease.setPreferredSize(new java.awt.Dimension(383, 26));
				chkForRelease.setBounds(255, 15, 125, 21);
			}
			{
				btnOK = new JButton();
				pnlPreviewOption.add(btnOK);
				btnOK.setBounds(140, 58, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlPreviewOption);
						optionPaneWindow.dispose();
					}
				});
			}
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	//display
	public void displayATM_forProcessing(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"true,\r\n" + 
			"a.agent_id,\r\n" + 
			"upper(trim(b.entity_name)) as agent_name,\r\n" + 
			"a.atm_no,  \n" +
			"trim(c.division_alias) as div,\r\n" + 
			"trim(d.dept_alias) as dept,\r\n" + 
			"e.position_abbv\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"from (select * from mf_sellingagent_info where atm_onproc = false and atm_endorse_for_processing = true " +
			"	or atm_onproc is null and atm_endorse_for_processing = true and status_id = 'A' \r\n" + 
			" 	) a\r\n" + 
			"left join rf_entity b on a.agent_id = b.entity_id \r\n" + 
			"left join mf_division c on a.sales_div_id = c.division_code\r\n" + 
			"left join mf_department d on a.dept_id = d.dept_code\r\n" + 
			"left join mf_sales_position e on a.salespos_id = e.position_code \n" +
			//"where accredit_to >=  '"+dateATM.getDate()+"' \n" +
			"order by b.entity_name";

		System.out.printf("displayATM_forProcessing #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalCV(modelMain, modelTotal);			
		}		

		else {

			modelATM_onprocess_total = new modelATM_process();
			modelATM_onprocess_total.addRow(new Object[] { "Total" , null, null, null, new BigDecimal(0.00) });

			tblATM_onprocess_total = new _JTableTotal(modelATM_onprocess_total, tblATM_onprocess);
			tblATM_onprocess_total.setFont(dialog11Bold);
			scrollATM_onprocesstotal.setViewportView(tblATM_onprocess_total);
			((_JTableTotal) tblATM_onprocess_total).setTotalLabel(0);
		}

		tblATM_onprocess.getColumnModel().getColumn(0).setPreferredWidth(50);
		tblATM_onprocess.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblATM_onprocess.getColumnModel().getColumn(2).setPreferredWidth(300);
		tblATM_onprocess.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblATM_onprocess.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblATM_onprocess.getColumnModel().getColumn(5).setPreferredWidth(70);
		//tblATM_onprocess.packAll();		
		adjustRowHeight();

		int row_tot = tblATM_onprocess.getRowCount();			
		modelATM_onprocess_total.setValueAt(new BigDecimal(row_tot), 0, 1);

	}	

	public void displayATM_forTagging(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"false," +
			"a.agent_id,\r\n" + 
			"upper(trim(b.entity_name)) as agent_name,\r\n" + 
			"a.atm_no,  \n" +
			"trim(c.division_alias) as div,\r\n" + 
			"trim(d.dept_alias) as dept,\r\n" + 
			"e.position_abbv \n" +
			//"'' as atm_no \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"from (select * from mf_sellingagent_info where atm_onproc = true and atm_no is null\r\n" + 
			"	or trim(atm_no) = '' and atm_onproc = true and status_id = 'A' ) a\r\n" + 
			"left join rf_entity b on a.agent_id = b.entity_id \r\n" + 
			"left join mf_division c on a.sales_div_id = c.division_code\r\n" + 
			"left join mf_department d on a.dept_id = d.dept_code\r\n" + 
			"left join mf_sales_position e on a.salespos_id = e.position_code \n" +
			//"where accredit_to >=  '"+dateATM.getDate()+"' \n" +
			"order by b.entity_name";

		System.out.printf("displayATM_forTagging #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalCV(modelMain, modelTotal);			
		}		

		else {

			modelATM_onprocess_total = new modelATM_process();
			modelATM_onprocess_total.addRow(new Object[] { "Total" , null, null, null, new BigDecimal(0.00) });

			tblATM_onprocess_total = new _JTableTotal(modelATM_onprocess_total, tblATM_onprocess);
			tblATM_onprocess_total.setFont(dialog11Bold);
			scrollATM_onprocesstotal.setViewportView(tblATM_onprocess_total);
			((_JTableTotal) tblATM_onprocess_total).setTotalLabel(0);
		}		

		adjustRowHeight();
		int row_tot = tblATM_onprocess.getRowCount();			
		modelATM_onprocess_total.setValueAt(new BigDecimal(row_tot), 0, 1);

	}

	public void displayATM_forRelease(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"false," +
			"a.agent_id,\r\n" + 
			"upper(trim(b.entity_name)) as agent_name,\r\n" + 
			"a.atm_no,  \n" +
			"trim(c.division_alias) as div,\r\n" + 
			"trim(d.dept_alias) as dept, \r\n" + 
			"e.position_abbv \n" +
			"\r\n" + 
			"\r\n" + 
			"from (select * from mf_sellingagent_info where trim(atm_no) != '') a\r\n" + //atm_no is not null or 
			"left join rf_entity b on a.agent_id = b.entity_id \r\n" + 
			"left join mf_division c on a.sales_div_id = c.division_code\r\n" + 
			"left join mf_department d on a.dept_id = d.dept_code\r\n" + 
			"left join mf_sales_position e on a.salespos_id = e.position_code \n" +
			"where a.atm_onproc is true \n" +
			"and a.status_id = 'A' \n" +
			"and a.atm_on_hand is false \n" +
			"order by b.entity_name";

		System.out.printf("displayATM_forTagging #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalCV(modelMain, modelTotal);			
		}		

		else {

			modelATM_onprocess_total = new modelATM_process();
			modelATM_onprocess_total.addRow(new Object[] { "Total" , null, null, null, new BigDecimal(0.00) });

			tblATM_onprocess_total = new _JTableTotal(modelATM_onprocess_total, tblATM_onprocess);
			tblATM_onprocess_total.setFont(dialog11Bold);
			scrollATM_onprocesstotal.setViewportView(tblATM_onprocess_total);
			((_JTableTotal) tblATM_onprocess_total).setTotalLabel(0);
		}		

		adjustRowHeight();
		int row_tot = tblATM_onprocess.getRowCount();			
		modelATM_onprocess_total.setValueAt(new BigDecimal(row_tot), 0, 1);

	}

	//display tables


	//reset, enable, disable
	public void refresh_Table(){

		//reset table 1
		FncTables.clearTable(modelATM_onprocess);
		FncTables.clearTable(modelATM_onprocess_total);			
		rowHeaderATM_onprocess = tblATM_onprocess.getRowHeader22();
		scrollATM_onprocess.setRowHeaderView(rowHeaderATM_onprocess);	
		modelATM_onprocess_total.addRow(new Object[] { "Total" , new BigDecimal(0.00) });

	}

	public void enableButtons(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f){

		btnAdd.setEnabled(a);
		btnTag.setEnabled(b);
		btnRelease.setEnabled(c);

		btnSave.setEnabled(d);
		btnPreview.setEnabled(e);
		btnCancel.setEnabled(f);

	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		lblATMdate.setEnabled(true);

		enableButtons(true,true,true, false,true,false);
		refresh_Table();

		lookupCompany.setValue(co_id);
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//

		if(e.getActionCommand().equals("Add")){
			//tblATM_onprocess.showColumns("Tag","Position");
			displayATM_forProcessing(modelATM_onprocess, rowHeaderATM_onprocess, modelATM_onprocess_total); 	
			modelATM_onprocess.setEditable(true);
			tblATM_onprocess.setEditable(true);
			enableButtons(false,false,false, true,false,true);
			to_do = "add_new";
			pnlATMdetails.setBorder(JTBorderFactory.createTitleBorder("Agent Details - List of Agents for Processing"));
		}	

		if(e.getActionCommand().equals("Tag")){ setTableModel_forATMtagging(); to_do = "edit"; 
		pnlATMdetails.setBorder(JTBorderFactory.createTitleBorder("Agent Details - List of Agents for Tagging"));}

		if(e.getActionCommand().equals("Release")){ setTableModel_forATMrelease(); to_do = "release"; 
		pnlATMdetails.setBorder(JTBorderFactory.createTitleBorder("Agent Details - List of Agents for ATM Release"));}

		if(e.getActionCommand().equals("Preview")){ previewOption();  }

		if(e.getActionCommand().equals("Cancel")){ 

			if(isThereAnItemtoTag()==true)
			{
				if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					cancel();
				}
			}
			else 
			{	
				cancel(); }
			}

		if(e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true)
		{
			if(isThereAnItemtoTag()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Please select an item to save.", "Information", 
					JOptionPane.INFORMATION_MESSAGE);}
			else {	

				if(e.getActionCommand().equals("Save")){ 
					if (to_do.equals("add_new")) {saveATMonprocess();}
					else if (to_do.equals("edit")) {saveATMfortagging();}
					else if (to_do.equals("release")) {saveATMforrelease();}				
				}
			}

		}
		else if(e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to process agent's ATM.","Warning",JOptionPane.WARNING_MESSAGE); }




	}

	public void mouseClicked(MouseEvent evt) {	
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

	public void preview_ATM_onproc(){

		String criteria = "ATM On Process";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("prepared_by", UserInfo.EmployeeCode);	
		mapParameters.put("co_id", co_id);	
		mapParameters.put("tagging_date", dateFormat.format(dteRefDate.getDate()));	

		FncReport.generateReport("/Reports/rptATM_on_process.jasper", reportTitle, "", mapParameters);

	}

	public void preview_ATM_processed(){

		String criteria = "ATM Processed";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);	
		mapParameters.put("co_id", co_id);	
		mapParameters.put("atm_tag_date", dateFormat.format(dteRefDate.getDate()));	

		FncReport.generateReport("/Reports/rptATM_processed.jasper", reportTitle, "", mapParameters);

	}
	
	public void preview_ATM_released(){

		String criteria = "Released ATM";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);	
		mapParameters.put("co_id", co_id);	
		mapParameters.put("rlsd_date", dateFormat.format(dteRefDate.getDate()));	

		FncReport.generateReport("/Reports/rptATM_released.jasper", reportTitle, "", mapParameters);

	}

	public void previewOption(){		

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlPreviewOption, "Report Option",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {	
				selectDate();
			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
		} // CLOSED_OPTION		
	}

	public void selectDate(){		

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Transaction Date",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {				
				if (chkOnProcess.isSelected()) {preview_ATM_onproc();} 
				if (chkForTagging.isSelected()) {preview_ATM_processed();} 
				if (chkForRelease.isSelected()) {preview_ATM_released();} 
			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
		} // CLOSED_OPTION		
	}

	public void cancel(){

		enableButtons(true,true,true, false,true,false);
		refresh_Table();
		pnlATMdetails.setBorder(JTBorderFactory.createTitleBorder("Agent Details"));
	}


	//table operations	
	private void adjustRowHeight(){		

		int rw = tblATM_onprocess.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblATM_onprocess.setRowHeight(x, 22);			
			x++;
		}
	}

	public void totalCV(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal amt_bd 		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { amt_bd 	= amt_bd.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { amt_bd 	= amt_bd.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null, null, amt_bd});
	}

	public void setTableModel_forATMtagging(){			

		tblATM_onprocess.showColumns("ATM No.");
		tblATM_onprocess.getColumnModel().getColumn(4).setPreferredWidth(120);
		displayATM_forTagging(modelATM_onprocess, rowHeaderATM_onprocess, modelATM_onprocess_total); 			
		//tblATM_onprocess.hideColumns("Tag","Position");

		enableButtons(false,false,false, true,false,true);
	}

	public void setTableModel_forATMrelease(){			

		tblATM_onprocess.showColumns("ATM No.");
		tblATM_onprocess.getColumnModel().getColumn(4).setPreferredWidth(120);
		displayATM_forRelease(modelATM_onprocess, rowHeaderATM_onprocess, modelATM_onprocess_total); 			
		//tblATM_onprocess.hideColumns("Tag","Position");

		enableButtons(false,false,false, true,false,true);
	}


	//processes
	public void saveATMonprocess() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all selected items correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			int x=0;
			pgUpdate db = new pgUpdate();

			//create CDF 
			while ( x < modelATM_onprocess.getRowCount()) {
				Boolean isTrue = false;
				if(tblATM_onprocess.getValueAt(x,0) instanceof String){isTrue = new Boolean((String)tblATM_onprocess.getValueAt(x,0));}
				if(tblATM_onprocess.getValueAt(x,0) instanceof Boolean){isTrue = (Boolean)tblATM_onprocess.getValueAt(x,0);}				

				if(isTrue)
				{						
					String agent_id 	= tblATM_onprocess.getValueAt(x,1).toString().trim();					
					updateAgentDetaisl(db, agent_id);
				}

				x++;
			}

			db.commit();	
			JOptionPane.showMessageDialog(null,"ATM for processing update saved.","Information",JOptionPane.INFORMATION_MESSAGE);	
			displayATM_forProcessing(modelATM_onprocess, rowHeaderATM_onprocess, modelATM_onprocess_total); 	
			enableButtons(true,true,true, false,true,false);
		}

	}

	public void updateAgentDetaisl(pgUpdate db, String agent_id) {//ok

		String sqlDetail = 
			"update mf_sellingagent_info " +
			"set atm_onproc = true, " +
			"atm_onproc_tagdate = '"+Calendar.getInstance().getTime()+"' " +
			"where trim(agent_id) = '"+agent_id+"' " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);			

	}

	public void saveATMfortagging() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all tagged items correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			int x=0;
			pgUpdate db = new pgUpdate();

			//create CDF 
			while ( x < modelATM_onprocess.getRowCount()) {
				Boolean isTrue = false;
				if(tblATM_onprocess.getValueAt(x,0) instanceof String){isTrue = new Boolean((String)tblATM_onprocess.getValueAt(x,0));}
				if(tblATM_onprocess.getValueAt(x,0) instanceof Boolean){isTrue = (Boolean)tblATM_onprocess.getValueAt(x,0);}				

				if(isTrue)
				{						
					String agent_id 	= tblATM_onprocess.getValueAt(x,1).toString().trim();		
					String atm_no 		= tblATM_onprocess.getValueAt(x,3).toString().trim();		
					updateAgentATM(db, agent_id, atm_no);
					insertATMfee(db, agent_id);
				}

				x++;
			}

			db.commit();	
			JOptionPane.showMessageDialog(null,"Agents' ATM numbers saved.","Information",JOptionPane.INFORMATION_MESSAGE);
			displayATM_forTagging(modelATM_onprocess, rowHeaderATM_onprocess, modelATM_onprocess_total); 		
			enableButtons(true,true,true, false,true,false);
		}

	}

	public void saveATMforrelease() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all tagged items correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			int x=0;
			pgUpdate db = new pgUpdate();

			//create CDF 
			while ( x < modelATM_onprocess.getRowCount()) {
				Boolean isTrue = false;
				if(tblATM_onprocess.getValueAt(x,0) instanceof String){isTrue = new Boolean((String)tblATM_onprocess.getValueAt(x,0));}
				if(tblATM_onprocess.getValueAt(x,0) instanceof Boolean){isTrue = (Boolean)tblATM_onprocess.getValueAt(x,0);}				

				if(isTrue)
				{						
					String agent_id 	= tblATM_onprocess.getValueAt(x,1).toString().trim();	
					updateAgentATMrelease(db, agent_id);
				}

				x++;
			}

			db.commit();	
			JOptionPane.showMessageDialog(null,"Agents' ATM released.","Information",JOptionPane.INFORMATION_MESSAGE);
			displayATM_forRelease(modelATM_onprocess, rowHeaderATM_onprocess, modelATM_onprocess_total); 		
			enableButtons(true,true,true, false,true,false);
		}

	}

	public void updateAgentATM(pgUpdate db, String agent_id, String atm_no) {//ok

		String sqlDetail = 
			"update mf_sellingagent_info " +
			"set atm_no = '"+atm_no+"', " +
			"atm_regdate = '"+Calendar.getInstance().getTime()+"', " +
			"atm_bank_acct_id = '00007', " +  //default - change upon deployment
			"paythruatm = true " +
			"where trim(agent_id) = '"+agent_id+"' " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);			

	}

	public void updateAgentATMrelease(pgUpdate db, String agent_id) {//ok

		String sqlDetail = 
			"update mf_sellingagent_info \n" +
			"set atm_on_hand = true, \n" +
			"atm_rlsd_date = '"+Calendar.getInstance().getTime()+"', " +
			"atm_rlsd_by = '"+UserInfo.EmployeeCode+"' " +
			"where trim(agent_id) = '"+agent_id+"' " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);			

	}
	
	public void insertATMfee(pgUpdate db, String agent_id) {//ok

		String sqlDetail = 
			"insert into cm_atm_fee values( \n" +
			"'"+agent_id+"', \n" +
			"100.00, \n" +
			"0, \n" +
			"null, \n" +
			"null, \n" +
			"'A', \n" +
			"'"+UserInfo.EmployeeCode+"', \n" +
			"'"+Calendar.getInstance().getTime()+"' )" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);			

	}

	
	//validate saving
	public Boolean isThereAnItemtoTag(){

		boolean x = false;		
		int w = 0;

		//create CDF 
		while ( w < tblATM_onprocess.getRowCount()) {
			Boolean isTrue = false;
			if(tblATM_onprocess.getValueAt(w,0) instanceof String){isTrue = new Boolean((String)tblATM_onprocess.getValueAt(w,0));}
			if(tblATM_onprocess.getValueAt(w,0) instanceof Boolean){isTrue = (Boolean)tblATM_onprocess.getValueAt(w,0);}
			if(isTrue){	x = true; break; }

			w++;
		}
		return x;		
	}


}
