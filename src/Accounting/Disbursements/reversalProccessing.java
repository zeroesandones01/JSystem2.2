package Accounting.Disbursements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JTableMain;
import components._JTagLabel;
import interfaces._GUI;
import tablemodel.modelreversal_proc;

public class reversalProccessing extends _JInternalFrame implements _GUI, ActionListener {
	private static final long serialVersionUID = 1L;
	static String title = "Reversal Proccessing";
	public static Dimension frameSize = new Dimension(922, 630);
	private JButton btnSave;
	private JButton btnPreview;
	private JButton btnRefresh;
	private JButton btnCancel;
	private _JScrollPaneMain scrolldoc_reversal;
	private static  _JLookup lookupcompany;
	private _JTagLabel tagcompany;
	private _JLookup lookupdoctype;
	private _JTagLabel tagdoctype;
	protected String co_name;
	protected String doctypename;
	private static  modelreversal_proc modeldoc_reversal;
	private _JTableMain tbldoc_reversal;
	private JList rowheader_reversal;
	protected String doctype;
	private JButton btngenerate;
	
	public reversalProccessing() {
		super(title, true, true, true, true);
		initGUI();
	}

	public reversalProccessing(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public reversalProccessing(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		
		{
			JPanel pnlmain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlmain, BorderLayout.CENTER);
			pnlmain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlnorth = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setBorder(JTBorderFactory.createTitleBorder("Filters"));
				pnlnorth.setPreferredSize(new Dimension(0, 130));
				{
					JPanel pnlfilter_label = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlnorth.add(pnlfilter_label, BorderLayout.WEST);
					pnlfilter_label.setPreferredSize(new Dimension(120, 0));
					{
						JLabel lblcompany = new JLabel("Company", JLabel.LEADING);
						pnlfilter_label.add(lblcompany);
						lblcompany.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
					}
					{
						JLabel lbldoctype = new JLabel("Document Type", JLabel.LEADING);
						pnlfilter_label.add(lbldoctype);
						lbldoctype.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
					}
					//pnlfilter_label.add(Box.createHorizontalBox());
				}
				{
					JPanel pnlfilter_lookup = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlnorth.add(pnlfilter_lookup, BorderLayout.CENTER);
					{
						lookupcompany = new _JLookup();
						pnlfilter_lookup.add(lookupcompany);
						lookupcompany.setLookupSQL(SQL_COMPANY());
						lookupcompany.setReturnColumn(0);
						lookupcompany.addLookupListener(new LookupListener() {
							

							@Override
							public void lookupPerformed(LookupEvent event) {
								// TODO Auto-generated method stub
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									co_name = (String)data [1];
									
									tagcompany.setTag(co_name);
									lookupdoctype.setEnabled(true);
									buttonstate(false, false, true);
									System.out.println("lookupcompany");
								}
							}
						});
					}
					{
						lookupdoctype = new _JLookup();
						pnlfilter_lookup.add(lookupdoctype);
						lookupdoctype.setEnabled(false);
						lookupdoctype.setLookupSQL(getDocType());
						lookupdoctype.setReturnColumn(0);
						lookupdoctype.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								// TODO Auto-generated method stub
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									doctype = (String)data[0];
									doctypename = (String)data[1];
									
									tagdoctype.setTag(doctypename);
									
									if(doctype.equals("01")) {
										
										new Thread(new Runnable() {
											public void run() {
												// TODO Auto-generated method stub
												FncGlobal.startProgress("Please wait...");
												System.out.println("Doc Type = 01");
												displayJV_forreversal(modeldoc_reversal, rowheader_reversal, modeldoc_reversal);
												buttonstate(true, true, true);
												FncGlobal.stopProgress();
											}
										}).start();
									}
									modeldoc_reversal.setEditable(true);
								}
							}
						});
					}
				}
				{
					JPanel pnlfilter_name = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlnorth.add(pnlfilter_name, BorderLayout.EAST);
					pnlfilter_name.setPreferredSize(new Dimension(650, 0));
					{
						tagcompany = new _JTagLabel("[ ]");
						pnlfilter_name.add(tagcompany);
					}
					{
						tagdoctype = new _JTagLabel("[ ]");
						pnlfilter_name.add(tagdoctype);
					}
				}
			}
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				pnlcenter.setBorder(LINE_BORDER);
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder("Details"));
				{
					scrolldoc_reversal = new _JScrollPaneMain();
					pnlcenter.add(scrolldoc_reversal, BorderLayout.CENTER);
					scrolldoc_reversal.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modeldoc_reversal = new modelreversal_proc();
						tbldoc_reversal = new _JTableMain(modeldoc_reversal);
						scrolldoc_reversal.setViewportView(tbldoc_reversal);
						tbldoc_reversal.getTableHeader().setEnabled(false);
						tbldoc_reversal.setFillsViewportHeight(false);
						modeldoc_reversal.setEditable(false);
						tbldoc_reversal.getColumnModel().getColumn(1).setPreferredWidth(150);
						tbldoc_reversal.getColumnModel().getColumn(2).setPreferredWidth(120);
						tbldoc_reversal.getColumnModel().getColumn(3).setPreferredWidth(300);
						tbldoc_reversal.getColumnModel().getColumn(4).setPreferredWidth(100);
						tbldoc_reversal.getColumnModel().getColumn(5).setPreferredWidth(100);
						tbldoc_reversal.getColumnModel().getColumn(6).setPreferredWidth(300);
						
						/*tbldoc_reversal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							@Override
							public void valueChanged(ListSelectionEvent e) {
								
								if(!e.getValueIsAdjusting()) {
									
									int row = tbldoc_reversal.getSelectedRow();
									String doc_no = (String) modeldoc_reversal.getValueAt(row, 1);
									
									System.out.println("Doc No. = "+doc_no);
								}
							}
						});*/
					}
					{
						rowheader_reversal = tbldoc_reversal.getRowHeader22();
						scrolldoc_reversal.setRowHeaderView(rowheader_reversal);
						scrolldoc_reversal.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 4, 5, 5));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 35));
				{
					btngenerate = new JButton();
					pnlsouth.add(btngenerate);
					btngenerate.setActionCommand("generate");
					btngenerate.setEnabled(false);
					btngenerate.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlsouth.add(btnSave);
					btnSave.setActionCommand("save");
					btnSave.setEnabled(false);
					btnSave.addActionListener(this);
				}

				{
					btnRefresh = new JButton("Refresh");
					pnlsouth.add(btnRefresh);
					btnRefresh.setActionCommand("refresh");
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlsouth.add(btnCancel);
					btnCancel.setEnabled(false);
					btnCancel.setActionCommand("cancel");
					btnCancel.addActionListener(this);
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("save")) {
			
			if(doctype.equals("01")) {
				if(JOptionPane.showConfirmDialog(this, "Reverse selected Journal Voucher?", "Save Reversal", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					reversejv();
					JOptionPane.showMessageDialog(this, "Reversal already save.");
					lookupcompany.setValue(null);
					tagcompany.setText("[ ]");
					lookupdoctype.setValue(null);
					lookupdoctype.setEnabled(false);
					tagdoctype.setText("[ ]");
					FncTables.clearTable(modeldoc_reversal);
					rowheader_reversal = tbldoc_reversal.getRowHeader22();
					scrolldoc_reversal.setRowHeaderView(rowheader_reversal);
					buttonstate(false, false, true);
					System.out.println("cancel");
				}
			}
		}
		
		if (e.getActionCommand().equals("refresh")) {
			if (lookupdoctype.getValue().equals(null)) {
				System.out.println("lookupdoctype is null");
			}else {
				System.out.println("lookupdoctype is not null");
				new Thread(new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
						FncGlobal.startProgress("Please wait...");
						displayJV_forreversal(modeldoc_reversal, rowheader_reversal, modeldoc_reversal);
						buttonstate(true, true, true);
						FncGlobal.stopProgress();
					}
				}).start();
			}
		}
		
		if (e.getActionCommand().equals("cancel")) {
			
			lookupcompany.setValue(null);
			tagcompany.setText("[ ]");
			lookupdoctype.setValue(null);
			lookupdoctype.setEnabled(false);
			tagdoctype.setText("[ ]");
			FncTables.clearTable(modeldoc_reversal);
			rowheader_reversal = tbldoc_reversal.getRowHeader22();
			scrolldoc_reversal.setRowHeaderView(rowheader_reversal);
			buttonstate(false, false, true);
			System.out.println("cancel");
			
		}
	}
	public static String SQL_COMPANY() {//XXX Company
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		return SQL;
	}
	
	public static String getDocType() {
		return

				"select '01' as \"Doc Type No.\" ,   'GENERAL JOURNAL VOUCHER' as \"Doc. Name\"    union all    \n"
				+ "select '02' as \"Doc Type No.\" ,   'PAYABLE VOUCHER' as \"Doc. Name\"  ";

	}
	
	public void buttonstate( Boolean save, Boolean refresh, Boolean cancel) {
		btnSave.setEnabled(save);
		//btnPreview.setEnabled(save);
		btnRefresh.setEnabled(refresh);
		btnCancel.setEnabled(cancel);
		
	}
	
	
	public void displayJV_forreversal(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {
		FncTables.clearTable(modelMain);//Code to clear modelMain.	
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		
		String Strsql = "select \n"
				+ "false, a.jv_no,\n"
				+ "b.amount,\n"
				+ "get_employee_name(a.created_by) as created_by,\n"
				+ "a.jv_date,  \n"
				+ "a.date_posted, \n"
				+ "(select get_client_name(entity_id) from rf_jv_detail where jv_no = a.jv_no and co_id = a.co_id LIMIT 1) as payee\n"
				+ "from rf_jv_header a\n"
				+ "left join ( \n"
				+ "	select distinct on (jv_no, co_id) a.jv_no, a.co_id, sum(tran_amt) as amount \n"
				+ "	from (\n"
				+ "		select jv_no, co_id, tran_amt  \n"
				+ "		from rf_jv_detail \n"
				+ "		where bal_side = 'D' and status_id = 'A' and co_id = '02'\n"
				+ "	) a\n"
				+ "	group by jv_no, co_id  ) b on trim(a.jv_no) = trim(b.jv_no) and a.co_id = b.co_id \n"
				+ "left join rf_jv_header c on a.reversal_jv_no = c.jv_no and a.co_id = c.co_id \n"
				+ "where a.status_id = 'P' and a.tran_id in ('00006','00071') and a.co_id = '"+lookupcompany.getValue()+"'\n"
				+ "and  a.proc_id = 2\n"
				+ "and case when c.status_id in ('A','F','P') then false else true end	\n"
				+ "order by a.jv_no  desc";
		
		System.out.println("displayJV_forreversal: "+Strsql);
		pgSelect db = new pgSelect();
		db.select(Strsql);
		if(db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
	}
	
	public static void reversejv () {
		for (int x = 0; x < modeldoc_reversal.getRowCount(); x++) {
			Boolean selected = (Boolean) modeldoc_reversal.getValueAt(x, 0);
			if (selected) {
				
				String jv_no = (String) modeldoc_reversal.getValueAt(x, 1);
				System.out.println("Jv no. :"+jv_no);
				String SQL = "select sp_save_jv_reversal ('"+lookupcompany.getValue()+"','"+jv_no+"','"+UserInfo.EmployeeCode+"')";
				pgSelect db = new pgSelect();
				FncSystem.out("SQL", SQL);
				db.select(SQL);
				
			}
		}
	}
}