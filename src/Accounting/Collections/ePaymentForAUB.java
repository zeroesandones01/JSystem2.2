package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import tablemodel.model_epaymentForAUB;

public class ePaymentForAUB extends _JInternalFrame implements
		ActionListener {

	private static final long serialVersionUID = 5913829032219786434L;
	static String title = "ePayment - For AUB (Uploading)";
	Dimension frameSize = new Dimension(570, 450);
	
	JFileChooser Chooser;
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlack = BorderFactory.createLineBorder(Color.BLACK);
	
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	private model_epaymentForAUB modelAUB;
	public _JTableMain tblAUB;
	public JList rowAUB;
	
	private JXPanel pnlTab1;
	private JScrollPane scrTab1;
	private model_epaymentForAUB modelAUB1;
	public _JTableMain tblAUB1;
	public JList rowAUB1;
	
	private JXPanel pnlTab2;
	private JScrollPane scrTab2;
	private model_epaymentForAUB modelAUB2;
	public _JTableMain tblAUB2;
	public JList rowAUB2;
	
	private JButton btnUnTag;
	private JButton btnSendtoAUB;
	private JButton btnPreview;
	
	private JLabel lblCo;
	
	private _JLookup txtCoID;
	
	private JTextField txtCo;	
	
	private JTabbedPane tabCenter;
	
	private JXTextField txtSearch;
	
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	
	public ePaymentForAUB() {
		super(title, false, true, false, true);
		initGui();
	}

	public ePaymentForAUB(String title) {
		super(title);
		initGui();
	}

	public ePaymentForAUB(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGui();
	}

	private void initGui(){
		this.setSize(frameSize);
		this.setTitle(title);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panPgStart = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panPgStart, BorderLayout.PAGE_START);
			panPgStart.setPreferredSize(new Dimension(555, 130));
			{
				JXPanel panSubMain1 = new JXPanel(new BorderLayout(5, 5));
				panPgStart.add(panSubMain1, BorderLayout.LINE_START);
				panSubMain1.setPreferredSize(new Dimension(555, 0));
				{
					JXPanel pnlMainParam1 = new JXPanel(new BorderLayout(5, 5));
					panSubMain1.add(pnlMainParam1, BorderLayout.CENTER);
					{
						JXPanel pnlLabelBox = new JXPanel(new GridLayout(1, 1, 5, 10));
						pnlMainParam1.add(pnlLabelBox, BorderLayout.PAGE_START);
						pnlLabelBox.setPreferredSize(new Dimension(100, 60));
						pnlLabelBox.setBorder(JTBorderFactory.createTitleBorder("Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel pnlCoLabel = new JXPanel(new BorderLayout(5, 5));
							pnlLabelBox.add(pnlCoLabel, BorderLayout.CENTER);
							{
								lblCo = new JLabel("Company:");
								pnlCoLabel.add(lblCo, BorderLayout.LINE_START);
								lblCo.setPreferredSize(new Dimension(80, 0));
								lblCo.setHorizontalAlignment(JTextField.RIGHT);
							}
							{
								JXPanel pnlIDName = new JXPanel(new BorderLayout(5, 5));
								pnlCoLabel.add(pnlIDName, BorderLayout.CENTER);
								{
									txtCoID = new _JLookup("");
									pnlIDName.add(txtCoID, BorderLayout.LINE_START);
									txtCoID.setHorizontalAlignment(JTextField.CENTER);
									txtCoID.setPreferredSize(new Dimension(60, 0));
									txtCoID.setLookupSQL(SQL_COMPANY());
									txtCoID.setReturnColumn(0);
									txtCoID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent e) {
											Object[] data = ((_JLookup) e.getSource()).getDataSet();
											if (data != null) {
												txtCo.setText(data[1].toString());
												
												forChecking();
												forSending();
												SenttoAUB();
											}
										}
									});
								}
								{
									txtCo = new JTextField("---");
									pnlIDName.add(txtCo, BorderLayout.CENTER);
									txtCo.setHorizontalAlignment(JTextField.CENTER);
									txtCo.setEditable(false);
								}
							}
						}
						JXPanel pnlLabelOth = new JXPanel(new GridLayout(1, 1, 5, 10));
						pnlMainParam1.add(pnlLabelOth, BorderLayout.CENTER);
						pnlLabelOth.setBorder(JTBorderFactory.createTitleBorder("Search PV No.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtSearch = new JXTextField();
							pnlLabelOth.add(txtSearch);
							txtSearch.setPreferredSize(new java.awt.Dimension(450, 0));
							txtSearch.setHorizontalAlignment(JTextField.CENTER);	
							txtSearch.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {	
									checkAllPostedPVList();
								}				 
							});
						}
					}
				}
				JXPanel panSubMain2 = new JXPanel(new BorderLayout(5, 5));
				panPgStart.add(panSubMain2, BorderLayout.CENTER);
				panSubMain2.setPreferredSize(new Dimension(500, 0));
			}
			//Setdefaults();
			JXPanel panPgEnd = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panPgEnd, BorderLayout.CENTER);
			{
				JXPanel panTable = new JXPanel(new BorderLayout(5, 5));
				panPgEnd.add(panTable, BorderLayout.CENTER);
				{
					tabCenter = new JTabbedPane();
					panTable.add(tabCenter, BorderLayout.CENTER);
					{
						JPanel pnlCenter = new JPanel();
						tabCenter.addTab("For Checking", null, pnlCenter, null);
						pnlCenter.setLayout(new BorderLayout());
						{
							CreateTableforChecking();
							pnlCenter.add(pnlTab);
						}
					}
					{
						JPanel pnlCenter1 = new JPanel();
						tabCenter.addTab("for Sending", null, pnlCenter1, null);
						pnlCenter1.setLayout(new BorderLayout());
						{
							CreateTableforSending();
							pnlCenter1.add(pnlTab1);
						}
					}
					{
						JPanel pnlCenter2 = new JPanel();
						tabCenter.addTab("Sent to AUB", null, pnlCenter2, null);
						pnlCenter2.setLayout(new BorderLayout());
						{
							CreateTableSent();
							pnlCenter2.add(pnlTab2);
						}
					}
					tabCenter.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();

							if(selectedTab == 0){ // Enabling of buttons for the Client Info Panel
								btnPreview.setEnabled(true);
								btnUnTag.setEnabled(false);
								btnSendtoAUB.setEnabled(false);
							}
							if(selectedTab == 1){//Enabling of buttons for the Submitted ID Panel
								btnPreview.setEnabled(true);
								btnUnTag.setEnabled(true);
								btnSendtoAUB.setEnabled(true);
							}
							if(selectedTab == 2){ // Enabling of buttons for the Address Panel
								btnPreview.setEnabled(true);
								btnUnTag.setEnabled(false);
								btnSendtoAUB.setEnabled(false);
							}
						}
					});
				}
				JXPanel panFinal = new JXPanel(new GridLayout(1, 4, 5, 5));
				panPgEnd.add(panFinal, BorderLayout.PAGE_END);
				panFinal.setPreferredSize(new Dimension(0, 30));
				{
					btnPreview = new JButton("Preview");
					panFinal.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
				}
				{
					btnUnTag = new JButton("Untag");
					panFinal.add(btnUnTag);
					btnUnTag.setActionCommand("Untag");
					btnUnTag.addActionListener(this);
				}
				{
					btnSendtoAUB = new JButton("Send");
					panFinal.add(btnSendtoAUB);
					btnSendtoAUB.setActionCommand("Send");
					btnSendtoAUB.addActionListener(this);
				}
			}
		}
		
		btnPreview.setEnabled(true);
		btnUnTag.setEnabled(false);
		btnSendtoAUB.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Preview")) {
			if (tabCenter.getSelectedIndex() == 0) {
				print();
				//saveforsending();
			}
			if (tabCenter.getSelectedIndex() == 1) {
				print();
			}
			if (tabCenter.getSelectedIndex() == 2) {
				print();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "This part is not finished yet.");
		}
	}
	
	public void CreateTableforChecking(){
		pnlTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			pnlTab.add(scrTab, BorderLayout.CENTER);
			scrTab.setBorder(lineGray);
			scrTab.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			modelAUB = new model_epaymentForAUB();
			modelAUB.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if(e.getType() == TableModelEvent.DELETE){
						rowAUB.setModel(new DefaultListModel());
					}
					if(e.getType() == TableModelEvent.INSERT){
						((DefaultListModel)rowAUB.getModel()).addElement(modelAUB.getRowCount());
					}
				}
			});
			
			tblAUB = new _JTableMain(modelAUB);
			tblAUB.packAll();
			scrTab.setViewportView(tblAUB);
			
			rowAUB = tblAUB.getRowHeader();
			rowAUB.setModel(new DefaultListModel());
			scrTab.setRowHeaderView(rowAUB);
			scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
		}
	}
	public void CreateTableforSending(){
		pnlTab1 = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab1.setOpaque(isOpaque());
		{
			scrTab1 = new JScrollPane();
			pnlTab1.add(scrTab1, BorderLayout.CENTER);
			scrTab1.setBorder(lineGray);
			scrTab1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrTab1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			modelAUB1 = new model_epaymentForAUB();
			modelAUB1.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if(e.getType() == TableModelEvent.DELETE){
						rowAUB1.setModel(new DefaultListModel());
					}
					if(e.getType() == TableModelEvent.INSERT){
						((DefaultListModel)rowAUB1.getModel()).addElement(modelAUB1.getRowCount());
					}
				}
			});
			
			tblAUB1 = new _JTableMain(modelAUB1);
			tblAUB1.packAll();
			scrTab1.setViewportView(tblAUB1);
			
			rowAUB1 = tblAUB1.getRowHeader();
			rowAUB1.setModel(new DefaultListModel());
			scrTab1.setRowHeaderView(rowAUB1);
			scrTab1.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
		}
	}
	public void CreateTableSent(){
		pnlTab2 = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab2.setOpaque(isOpaque());
		{
			scrTab2 = new JScrollPane();
			pnlTab2.add(scrTab2, BorderLayout.CENTER);
			scrTab2.setBorder(lineGray);
			scrTab2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrTab2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			modelAUB2 = new model_epaymentForAUB();
			modelAUB2.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if(e.getType() == TableModelEvent.DELETE){
						rowAUB2.setModel(new DefaultListModel());
					}
					if(e.getType() == TableModelEvent.INSERT){
						((DefaultListModel)rowAUB2.getModel()).addElement(modelAUB2.getRowCount());
					}
				}
			});
			
			tblAUB2 = new _JTableMain(modelAUB2);
			tblAUB2.packAll();
			scrTab2.setViewportView(tblAUB2);
			
			rowAUB2 = tblAUB2.getRowHeader();
			rowAUB2.setModel(new DefaultListModel());
			scrTab2.setRowHeaderView(rowAUB2);
			scrTab2.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
		}
	}
	
    private void forChecking(){
    	FncTables.clearTable(modelAUB);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		
		String sql = "select distinct on (a.pv_no) false, a.pv_no, now(), null, c.entity_id1, get_client_name(c.entity_id1), sum(d.pv_amt)::int,\n"
				+ "sum(d.wtax_amt), a.pv_no, c.entity_id1, a.pv_no, '2017', '"+txtCoID.getText()+"', 'PHP', c.entity_id1, get_client_tin(c.entity_id1), get_client_address_for2307(c.entity_id1), Cast(((select income_payment_desc from rf_withholding_tax where wtax_id = (select wtax_id from rf_request_detail where status_id = 'A' and rplf_no = c.rplf_no and wtax_id != '' and wtax_rate != 0 LIMIT 1))) As VarChar(40)), (select wtax_rate from rf_withholding_tax where wtax_id = (select wtax_id from rf_request_detail where status_id = 'A' and rplf_no = c.rplf_no and wtax_id != '' and wtax_rate != 0 LIMIT 1)), (select wtax_bir_code from rf_withholding_tax where wtax_id = (select wtax_id from rf_request_detail where status_id = 'A' and rplf_no = c.rplf_no and wtax_id != '' and wtax_rate != 0 LIMIT 1)),"
				+ "null, sum(d.vat_amt), null, null, null, null, null, null, null, a.pv_date, unnest(ARRAY[e.email]) \n"
				+ "from rf_pv_header a\n"
				+ "left join rf_request_header c on a.pv_no = c.rplf_no and c.status_id = 'A'\n"
				+ "left join rf_request_detail d on a.pv_no = d.rplf_no and d.status_id = 'A'\n"
				+ "left join rf_contacts e on c.entity_id1 = e.entity_id and e.status_id = 'A'\n"
				//+ "left join rf_cv_header e on a.cv_no = e.cv_no and e.status_id = 'A'\n"
				+ "Where a.status_id = 'P' and a.cv_no = '' and a.pv_no not in (select inv_no from rf_aub_epayment where status_id = 'A')\n"
				+ "and a.co_id = '"+txtCoID.getText()+"' \n"
				+ "group by a.pv_no, c.entity_id1, a.pv_date, income_payment_desc, wtax_id, wtax_bir_code, wtax_rate, c.rplf_no, e.email order by a.pv_no desc, a.pv_no::int desc, get_client_name(c.entity_id1);";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display SQL For PCost", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelAUB.addRow(db.getResult()[x]);
				listModel.addElement(modelAUB.getRowCount());
			}	
		}		
		tblAUB.packAll();
		btnPreview.setEnabled(true);
    }
    private void forSending(){
    	FncTables.clearTable(modelAUB1);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		
		String sql = "select distinct on (a.pv_no) false, a.pv_no, null, null, c.entity_id1, get_client_name(c.entity_id1), sum(d.pv_amt)::int,\n"
				+ "sum(d.wtax_amt), null, c.entity_id1, a.pv_no, '2017', '"+txtCoID.getText()+"', 'PHP', null, null, null, null, null, null,"
				+ "null, sum(d.vat_amt), null, null, null, null, null, null, null, a.pv_date, null \n"
				+ "from rf_pv_header a\n"
				+ "left join rf_request_header c on a.pv_no = c.rplf_no and c.status_id = 'A'\n"
				+ "left join rf_request_detail d on a.pv_no = d.rplf_no and d.status_id = 'A'\n"
				//+ "left join rf_cv_header e on a.cv_no = e.cv_no and e.status_id = 'A'\n"
				+ "Where a.status_id = 'P' and a.cv_no = '' and a.pv_no in (select inv_no from rf_aub_epayment where status_id = 'A')\n"
				+ "and a.co_id = '"+txtCoID.getText()+"' \n"
				+ "group by a.pv_no, c.entity_id1, a.pv_date order by a.pv_no desc, a.pv_no::int desc, get_client_name(c.entity_id1);";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display SQL For PCost", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelAUB1.addRow(db.getResult()[x]);
				listModel.addElement(modelAUB1.getRowCount());
			}	
		}		
		tblAUB1.packAll();
		btnPreview.setEnabled(true);
    }
    private void SenttoAUB(){
    	FncTables.clearTable(modelAUB2);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		
		String sql = "select distinct on (a.pv_no) false, a.pv_no, null, null, c.entity_id1, get_client_name(c.entity_id1), sum(d.pv_amt)::int,\n"
				+ "sum(d.wtax_amt), null, c.entity_id1, a.pv_no, '2017', '"+txtCoID.getText()+"', 'PHP', null, null, null, null, null, null,"
				+ "null, sum(d.vat_amt), null, null, null, null, null, null, null, a.pv_date, null \n"
				+ "from rf_pv_header a\n"
				+ "left join rf_request_header c on a.pv_no = c.rplf_no and c.status_id = 'A'\n"
				+ "left join rf_request_detail d on a.pv_no = d.rplf_no and d.status_id = 'A'\n"
				//+ "left join rf_cv_header e on a.cv_no = e.cv_no and e.status_id = 'A'\n"
				+ "Where a.status_id = 'P' and a.cv_no = '' and a.pv_no in (select inv_no from rf_aub_epayment where status_id = 'F')\n"
				+ "and a.co_id = '"+txtCoID.getText()+"' \n"
				+ "group by a.pv_no, c.entity_id1, a.pv_date order by a.pv_no desc, a.pv_no::int desc, get_client_name(c.entity_id1);";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display SQL For PCost", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelAUB2.addRow(db.getResult()[x]);
				listModel.addElement(modelAUB2.getRowCount());
			}	
		}		
		tblAUB2.packAll();
		btnPreview.setEnabled(true);
    }
    private void print(){	
    	ArrayList<String> iftrue = new ArrayList<String>();
		String SQL ="";
		ArrayList<String> listpv = new ArrayList<String>();
		for (int i = 0; i < modelAUB.getRowCount(); i++) {
			Boolean SelectedItem = (Boolean) modelAUB.getValueAt(i, 0);
			String pv = (String) modelAUB.getValueAt(i, 1);
			
			if (SelectedItem) {
				listpv.add(pv);
				iftrue.add(modelAUB.getValueAt(i, 1).toString());
				SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +
						
						"SELECT \n" +
						"'"+modelAUB.getValueAt(i, 1)+"' AS inv_no,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 2)+"','null') AS check_date,\n" + 
						"NULLIF('"+modelAUB.getValueAt(i, 3)+"','null') AS acct_no,\n" +
						"'"+modelAUB.getValueAt(i, 4)+"' AS vendor_no,\n" +
						"'"+modelAUB.getValueAt(i, 5)+"' AS vendor_name,\n" + 
						"'"+modelAUB.getValueAt(i, 6)+"' AS inv_amt,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 7)+"','null') AS tax_amt,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 8)+"','null') AS reference,\n" + 
						"NULLIF('"+modelAUB.getValueAt(i, 9)+"','null') AS doc_no,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 10)+"','null') AS inv_doc,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 11)+"','null') AS yr,\n" + 
						"NULLIF('"+modelAUB.getValueAt(i, 12)+"','null') AS co_id,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 13)+"','null') AS currency,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 14)+"','null') AS check_no,\n" + 
						"NULLIF('"+modelAUB.getValueAt(i, 15)+"','null') AS tin_no,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 16)+"','null') AS address,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 17)+"','null') AS tax_desc,\n" + 
						"NULLIF('"+modelAUB.getValueAt(i, 18)+"','null') AS tax_base,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 19)+"','null') AS tax_code,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 20)+"','null') AS ins_key,\n" + 
						"NULLIF('"+modelAUB.getValueAt(i, 21)+"','null') AS vat,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 22)+"','null') AS remarks1,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 23)+"','null') AS remarks2,\n" + 
						"NULLIF('"+modelAUB.getValueAt(i, 24)+"','null') AS remarks3,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 25)+"','null') AS remarks4,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 26)+"','null') AS remarks5,\n" + 
						"NULLIF('"+modelAUB.getValueAt(i, 27)+"','null') AS or_no,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 28)+"','null') AS mc_no,\n" + 
						"'"+modelAUB.getValueAt(i, 29)+"'::timestamp AS inv_date,\n" +
						"NULLIF('"+modelAUB.getValueAt(i, 30)+"','null') AS email \n";
			}
		}

		if (iftrue.isEmpty()) {
			JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
			return;
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("co_id", txtCoID.getText());
		mapParameters.put("pv", listpv);
		
		FncReport.generateReport("/Reports/rptForAUB.jasper", "For AUB", mapParameters,SQL);
    }
    private void checkAllPostedPVList(){//

		int rw = tblAUB.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";
			
			try { name	= tblAUB.getValueAt(x,1).toString().toUpperCase();}
			 catch (NullPointerException e) { name	= ""; }
			
			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			Boolean	end			= name.endsWith(acct_name);

			if (match==true||start==true||end==true) {
				tblAUB.setRowSelectionInterval(x, x); 
				tblAUB.changeSelection(x, 1, false, false);
				break;			
			}
			else {
				
				tblAUB.setRowSelectionInterval(0, 0); 
				tblAUB.changeSelection(0, 1, false, false);					
			}

			x++;

		}		
	}
    private void saveforsending(){
		for(int x = 0; x < tblAUB.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelAUB.getValueAt(x, 0);
			String pv_no = (String) modelAUB.getValueAt(x, 1);
			String edited_by = (String) UserInfo.EmployeeCode;
			
			if(isSelected){
				
				String SQL = "SELECT sp_save_epayment_forsending(p_co_id character varying,\n" + 
						"    p_inv_no character varying,\n" + 
						"    p_inv_date timestamp without time zone,\n" + 
						"    p_acct_no character varying,\n" + 
						"    p_vendor_no character varying,\n" + 
						"    p_vendor_name character varying,\n" + 
						"    p_inv_amnt character varying,\n" + 
						"    p_tax_amt character varying,\n" + 
						"    p_vat_amt character varying,\n" + 
						"    p_reference_no character varying,\n" + 
						"    p_doc_no character varying,\n" + 
						"    p_inv_doc_no character varying,\n" + 
						"    p_fiscal_yr character varying,\n" + 
						"    p_currency character varying,\n" + 
						"    p_remarks character varying,\n" + 
						"    p_or_no character varying,\n" + 
						"    p_mc_no character varying,\n" + 
						"    p_email character varying,\n" + 
						"    p_created_by character varying,\n" + 
						"    p_date_created timestamp without time zone);";
	
				pgSelect db = new pgSelect();
				FncSystem.out("SQL", SQL);
				db.select(SQL);
			}
		}
		//JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Change Payment Type.", JOptionPane.INFORMATION_MESSAGE);
		
		modelAUB.setRowCount(0);
		tblAUB.packAll();
		forChecking();
	}
}