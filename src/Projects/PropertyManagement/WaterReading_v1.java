package Projects.PropertyManagement;

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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JTextFieldDateEditor;

import tablemodel.model_water_billing;
import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
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

public class WaterReading_v1 extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Water Reading";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlBillingTable;
	private JPanel pnlSouth;
	private JPanel pnlTable;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlRepair;
	private JPanel pnTableRepair;
	private JPanel pnlMeterReaderDtl;
	private JPanel pnlMeterReaderDtl_1;
	private JPanel pnlMeterReaderDtl_1a;
	private JPanel pnlMeterReaderDtl_1b;
	private JPanel pnlMeterReaderDtl_2;
	private JPanel pnlMeterReaderDtl_2_a;
	private JPanel pnlMeterReaderDtl_2_a1;
	private JPanel pnlMeterReaderDtl_2_a2;

	private JLabel lblCompany;
	private JLabel lblDate;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblReader;
	private JLabel lblClientName;

	private _JTagLabel tagCompany;
	private _JTagLabel tagProject;
	private _JTagLabel tagPhase;
	private _JTagLabel tagReader;
	private _JScrollPaneMain scrollMeterReader;
	private _JScrollPaneTotal scrollMeterReader_total;

	private model_water_billing modelWBilling;
	private model_water_billing modelWBilling_total;
	private _JTableTotal tblWBilling_total;	
	private _JTableMain tblWBilling;
	private JList rowHeaderRepair;	

	private _JLookup lookupCompany;
	private _JLookup lookupPhase;
	private _JLookup lookupProject;

	private JButton btnGenerate;
	private JButton btnRefresh;	

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JPopupMenu menu;
	private JMenuItem mniViewBill;
	//private JMenuItem mniViewDisconnection;
	private _JLookup lookupReader;	
	private JXTextField txtClientName;

	private _JDateChooser dateReading;
	private JButton btnPreview;
	private JButton btnSave;

	// variables
	String co_id = "";
	String company 		= "";
	public static String company_logo;
	String proj_id = "";
	String proj_name = "";	
	String ph_no = "";
	String ph_code = "";
	String reader_id = "";
	String reader_name = "";	
	String pbl_id = "";
	String entity_id = "";	
	Boolean other_lot = null;
	private Double Prev_Rdng = null;
	private Double Curr_Rdng = null;
	private Double garbage_fee = null;
	private String Curr_Rdng_str = null;
	
	//sizes of panels
	private Dimension szPanelWest = new Dimension(150, 90);
	private Dimension szPanelEast = new Dimension(630, 90);

	public WaterReading_v1() {
		super(title, true, true, true, true);
		initGUI();
	}

	public WaterReading_v1(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public WaterReading_v1(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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

		//{} JPopMenu
		menuPopup();
		
		//{} pnlNorth
		pnlMain.add(pnlNorth(), BorderLayout.NORTH);
		
		//{} pnlTable 
		pnlMain.add(pnlTable(), BorderLayout.CENTER);	
		
		//{} pnlSouth		
		pnlMain.add(pnlSouth(), BorderLayout.SOUTH);

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display tables
	public void displayWBilling(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {   //used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 

				"select c_select, c_unit, c_client_name, c_prev_reading, c_curr_reading, c_water_cost, c_garbage_fee, c_amt_due, c_remarks, c_pbl_id, c_entity_id, c_proj_id, c_seq_no from display_water_billing_units('"+co_id+"', '"+proj_id+"','"+ph_no+"','"+dateReading.getDate().toString()+"')";

		/*
			"select \r\n" + 
			"trim(a.description) as unit, \r\n" + 
			"upper(d.entity_name) as client_name,  \r\n" + 
			"(case when e.prev_reading is null then 0.00 else e.prev_reading end)  as prev_reading, \r\n" + 
			"(case when ee.curr_reading is null then null else ee.curr_reading end) as curr_reading, \r\n" + 
			"(case when ee.reading_amt is null then null else ee.reading_amt end) as water_cost, \r\n" + 
			"null amt_due, \r\n" + 
			"ee.remarks, \r\n" + 
			"a.pbl_id, \r\n" + 
			"b.entity_id, \r\n" + 
			"a.proj_id," +
			"b.seq_no \r\n" + 
			"\r\n" + 
			"from mf_unit_info a  \r\n" + 
			"\r\n" + 
			"left join rf_sold_unit b on a.pbl_id = b.pbl_id " +
			/*
			"left join (" +
			"		   select pbl_id, seq_no, entity_id, status_id from rf_sold_unit where status_id = 'A'\r\n" + 
			"		      and pbl_id IN (select distinct on (pbl_id) pbl_id from rf_unit_status where unitstatus_id = '01' and status_id = 'A') " +
			"		   union all\r\n" + 
			"		   select oth_pbl_id, seq_no, entity_id, status_id from hs_sold_other_lots) b on a.pbl_id = b.pbl_id --and a.proj_id = b.projcode  \r\n" + 

			//"left join (select * from rf_water_reading order by reading_date desc) c on a.pbl_id = c.pbl_id and a.proj_id = c.proj_id and b.entity_id = c.entity_id  \r\n" + 
			"left join rf_entity d on b.entity_id = d.entity_id \r\n" +
			"left join (select distinct on (pbl_id) pbl_id, entity_id, max(meter_reading) as prev_reading\r\n" + 
			"	from rf_water_reading_v2 " +
			"	where status_id = 'A' " +
			"	and reading_date::date < '"+(Date) dateReading.getDate()+"' " +
			"	group by pbl_id, entity_id ) e on a.pbl_id = e.pbl_id and b.entity_id = e.entity_id  " + 
			"left join (select distinct on (pbl_id) pbl_id, entity_id, max(meter_reading) as curr_reading, " +
			"	reading_amt, remarks \r\n" + 
			"	from rf_water_reading_v2 " +
			"	where status_id = 'A' " +
			"	and reading_date::date = '"+(Date) dateReading.getDate()+"' " +
			"	group by pbl_id, entity_id, reading_amt, remarks ) ee on a.pbl_id = ee.pbl_id and b.entity_id = ee.entity_id  " + 
			"\r\n" + 
			"where (CASE WHEN '"+proj_id+"' = '' THEN TRUE ELSE a.proj_id = '"+proj_id+"' END)  \r\n" + 
			"and (CASE WHEN '"+ph_no+"' = '' THEN TRUE ELSE a.phase = '"+ph_no+"' END)  \r\n" + 
			"and b.status_id='A' \r\n" + 
			"\r\n" + 
			"order by getinteger(a.phase), a.block::int, a.lot::int;";
		 */

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
			FncSystem.out("Display water biling accounts", sql);

			//totalRepair(modelMain, modelTotal);
			totalRepair2(modelMain, modelTotal);

		}else {
			modelWBilling_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), 
					new BigDecimal(0.00), new BigDecimal(0.00), null });
		}

		tblWBilling.packAll();
		tblWBilling.getColumnModel().getColumn(7).setPreferredWidth(250);
		adjustRowHeight();	

	}	


	//Enable/Disable all components inside JPanel
	private void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d){

		btnGenerate.setEnabled(a);
		btnSave.setEnabled(b);		
		btnPreview.setEnabled(c);
		btnRefresh.setEnabled(d);

	}

	private void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		lblProject.setEnabled(true);
		lookupProject.setEnabled(true);
		tagProject.setEnabled(true);	

		lookupProject.setValue("");	
		tagProject.setTag("");	

		KEYBOARD_MANAGER.focusNextComponent();		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

		lookupCompany.setValue(co_id);
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Generate")){ 
			generate(); 
		}

		if(e.getActionCommand().equals("Refresh")){ 
			refresh(); 
		}

		if(e.getActionCommand().equals("Save")){
			new Thread(new Runnable() {
				public void run() {
					FncGlobal.startProgress("Saving Accounts");
					save();
					displayWBilling(modelWBilling, rowHeaderRepair, modelWBilling_total);
					enableButtons(false, true, true, true);
					FncGlobal.stopProgress();
				}
			}).start();
			/*new Thread(new Runnable() {
				public void run() {
					FncGlobal.startProgress("Generating Accounts");
					displayWBilling(modelWBilling, rowHeaderRepair, modelWBilling_total);	
					enableButtons(false, true, true, true);
					FncGlobal.stopProgress();
				}
			}).start();*/
//			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Water Meter Reading saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
		}

		if(e.getActionCommand().equals("Preview")){ 
			pbl_id 	 = "";  
			entity_id = ""; 
			//preview();
			previewAllBillings();
		}
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

	public class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {

				menu.show(ev.getComponent(), ev.getX(), ev.getY()); 
				mniViewBill.setEnabled(true);  
				//mniViewDisconnection.setEnabled(true) ;


			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {

				menu.show(ev.getComponent(), ev.getX(), ev.getY()); 
				mniViewBill.setEnabled(true);  
				//mniViewDisconnection.setEnabled(true) ;

			}

		}
	}

	private void refresh(){

		FncTables.clearTable(modelWBilling);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderRepair.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		initialize_comp();
		enableButtons(true, false, true, true);
		JOptionPane.showMessageDialog(getContentPane(),"Screen refreshed.","Information",JOptionPane.INFORMATION_MESSAGE);		

		lookupProject.setValue("");	
		tagProject.setTag("");

		lookupPhase.setValue("");	
		tagPhase.setTag("");

		lookupPhase.setEnabled(false);	
		tagPhase.setEnabled(false);
		lblPhase.setEnabled(false);
		lookupReader.setValue("");
		tagReader.setText("");
		txtClientName.setText("");

		dateReading.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		ph_no = "";
		ph_code = "";
		reader_id = "";
		reader_name = "";
		dateReading.setEnabled(true);
	}

	private void generate(){
		new Thread(new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating Accounts");
				displayWBilling(modelWBilling, rowHeaderRepair, modelWBilling_total);
				enableButtons(false, true, true, true);
				JOptionPane.showMessageDialog(getContentPane(),"Units Generated.","Information",JOptionPane.INFORMATION_MESSAGE);	
				((JTextFieldDateEditor)dateReading.getDateEditor()).setEnabled(false);
				dateReading.setEnabled(false);
				FncGlobal.stopProgress();
			}
		}).start();
	}


	//select, lookup and get statements	
	private String getReader(){    
		return 
				"select \n" +

		"a.entity_id as \"ID\", \n" +
		"upper(b.entity_name) as \"Name\" \n" +

		"from em_employee a \n" +
		"left join rf_entity b on a.entity_id = b.entity_id \n" +
		"where a.dept_code = '08' \n" +
		"and a.emp_status not in ('E') \n" +
		"order by b.entity_name " ;

	}

	private String getPhase(){//used
		return 
				"select\r\n" + 
				"\r\n" + 
				"trim(sub_proj_id) as \"Phase ID\",\r\n" + 
				"trim(phase) as \"Phase\"\r\n" + 
				"\r\n" + 
				"from mf_sub_project " +
				"where (case when '"+proj_id+"' = '' then true else proj_id = '"+proj_id+"' end) AND status_id = 'A'\r\n" + 
				"\r\n" + 
				"order by sub_proj_id "  ;

	}


	//table operations	
	private void totalRepair(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);//Code to clear modelMain.	

		Double amt_due = 0.00;
		Double wtr_cost = 0.00;
		Double gar_cost = 0.00;

		Double amt_due_Ttl = 0.00;
		Double wtr_cost_Ttl = 0.00;
		Double gar_cost_Ttl = 0.00;

		Double reading = null;

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { reading = Double.parseDouble(modelWBilling.getValueAt(x, 3).toString().replace(",",""));
			} 	catch (NullPointerException e) { 
				reading =0.00 ; 
			}			

			if(reading>0.00){	

				try { wtr_cost = Double.parseDouble(modelWBilling.getValueAt(x, 4).toString().replace(",","")); } 	
				catch (NullPointerException e) { wtr_cost =0.00 ; }				

				try { gar_cost = Double.parseDouble(modelWBilling.getValueAt(x, 5).toString().replace(",","")); } 	
				catch (NullPointerException e) { gar_cost =0.00 ; }

				try { amt_due = Double.parseDouble(modelWBilling.getValueAt(x, 6).toString().replace(",","")); } 	
				catch (NullPointerException e) { amt_due =0.00 ; }



				amt_due_Ttl = amt_due_Ttl + amt_due;
				wtr_cost_Ttl = wtr_cost_Ttl + wtr_cost;
				gar_cost_Ttl = gar_cost_Ttl + gar_cost;



			}		
			else {}				
		}	

		modelTotal.addRow(new Object[] { "Total", null, null, null,  wtr_cost_Ttl, gar_cost_Ttl, amt_due_Ttl, null });
	}

	private void totalRepair2(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);

		BigDecimal amt_due = new BigDecimal("0.00");
		BigDecimal wtr_cost = new BigDecimal("0.00");
		BigDecimal gar_cost = new BigDecimal("0.00");

		BigDecimal amt_due_Ttl = new BigDecimal("0.00");
		BigDecimal wtr_cost_Ttl = new BigDecimal("0.00");
		BigDecimal gar_cost_Ttl = new BigDecimal("0.00");
		BigDecimal reading = new BigDecimal("0.00");

		for(int x=0; x < modelMain.getRowCount(); x++){

			reading = (BigDecimal) modelMain.getValueAt(x, 3);

			wtr_cost_Ttl = wtr_cost_Ttl.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 4) == null ? new BigDecimal("0.00")
					: modelMain.getValueAt(x, 4)));

			gar_cost_Ttl = gar_cost_Ttl.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 5) == null ? new BigDecimal("0.00")
					: modelMain.getValueAt(x, 5)));

			amt_due_Ttl = amt_due_Ttl.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 6) == null ? new BigDecimal("0.00")
					: modelMain.getValueAt(x, 6)));

		}

		modelTotal.addRow(new Object[] { "Total", null, null, null,  wtr_cost_Ttl, gar_cost_Ttl, amt_due_Ttl, null });
	}

	private void adjustRowHeight(){		

		int rw = tblWBilling.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblWBilling.setRowHeight(x, 22);			
			x++;
		}
	}

	private void checkAllClientList(){//

		int rw = tblWBilling.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";

			try { name	= tblWBilling.getValueAt(x,1).toString().toUpperCase();}//make sure it's not modelAgentAccount, otherwise, row number will be fixed 
			catch (NullPointerException e) { name	= ""; }

			String acct_name	= txtClientName.getText().trim().toUpperCase();	
			Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			Boolean	end			= name.endsWith(acct_name);

			if (match==true||start==true||end==true) {
				tblWBilling.setRowSelectionInterval(x, x); 
				tblWBilling.changeSelection(x, 1, false, false);				
				break;			
			}
			else {
				tblWBilling.setRowSelectionInterval(0, 0); 
				tblWBilling.changeSelection(0, 1, false, false);					
			}

			x++;

		}		
	}

	private void displayAddReading() {

		int row = tblWBilling.getSelectedRow();

		Double Prev_Rdng = null;
		Double Curr_Rdng = null;
		Double garbage_fee = null;
		String Curr_Rdng_str = null;

		Integer x = row-1;

		if (x < 0){
			x=1;
		}
		System.out.printf("Value of row: %s%n", row);

		while (x<row+1){

			try { Curr_Rdng_str = modelWBilling.getValueAt(x, 3).toString().replace(",",""); } 	
			catch (NullPointerException e) { Curr_Rdng_str = "" ; }	

			try { Prev_Rdng = Double.parseDouble(modelWBilling.getValueAt(x, 2).toString().replace(",","")); } 	
			catch (NullPointerException e) { Prev_Rdng =0.00 ; }	

			try { garbage_fee = Double.parseDouble(modelWBilling.getValueAt(x, 5).toString().replace(",","")); } 	
			catch (NullPointerException e) { Prev_Rdng =0.00 ; }

			if (Curr_Rdng_str==""){Curr_Rdng =0.00 ;}
			else{ Curr_Rdng = Double.parseDouble(Curr_Rdng_str);} 	

			Double Totl_Rdng = Curr_Rdng - Prev_Rdng;

			System.out.println("");
			System.out.println("Name: "+modelWBilling.getValueAt(x, 1));
			System.out.printf("Prev_Rdng #1: " + Prev_Rdng + "\n");
			System.out.printf("Curr_Rdng #1: " + Curr_Rdng + "\n");
			System.out.printf("Totl_Rdng #1: " + Totl_Rdng + "\n");

			if (Totl_Rdng <= 10.00) {
				if (Curr_Rdng_str.equals("")){}
				else {modelWBilling.setValueAt(240.00, x, 4);}

				System.out.printf("Water Cost 1: " + 240.00 + "\n");
			}
			else if (Totl_Rdng > 10.00 && Totl_Rdng <= 20.00) {
				//modelWBilling.setValueAt(true, row, 0);
				modelWBilling.setValueAt(Totl_Rdng*27.00, x, 4);

				System.out.printf("Water Cost 2: " + Totl_Rdng*27.00 + "\n");
			}
			else if (Totl_Rdng > 20.00 && Totl_Rdng <= 30.00) {
				//modelWBilling.setValueAt(true, row, 0);
				modelWBilling.setValueAt(Totl_Rdng*30.00, x, 4);

				System.out.printf("Water Cost 3: " + Totl_Rdng*30.00 + "\n");
			}
			else if (Totl_Rdng > 30.00 && Totl_Rdng <= 40) {
				//modelWBilling.setValueAt(true, row, 0);
				modelWBilling.setValueAt(Totl_Rdng*33.00, x, 4);
				System.out.printf("Water Cost 4: " + Totl_Rdng*33.00 + "\n");
			}
			else if (Totl_Rdng >= 41.00) {
				//modelWBilling.setValueAt(true, row, 0);
				modelWBilling.setValueAt(Totl_Rdng*38.00, x, 4);
				System.out.printf("Water Cost 5: " + Totl_Rdng*38.00 + "\n");
			}
			//}

			x++;
		}

		totalRepair(modelWBilling, modelWBilling_total);	
	}


	private void computeWaterCost() {

		if(tblWBilling.getSelectedRows().length > 0) {
			int selected_row = tblWBilling.getSelectedRow();
			Boolean isSelected = (Boolean) modelWBilling.getValueAt(selected_row, 0);
			if(isSelected) {
				System.out.println("Compute Water Cost");

				try { Curr_Rdng_str = modelWBilling.getValueAt(selected_row, 4).toString().replace(",",""); } 	
				catch (NullPointerException e) { Curr_Rdng_str = "" ; }	

				try { Prev_Rdng = Double.parseDouble(modelWBilling.getValueAt(selected_row, 3).toString().replace(",","")); } 	
				catch (NullPointerException e) { Prev_Rdng =0.00 ; }	

				try { garbage_fee = Double.parseDouble(modelWBilling.getValueAt(selected_row, 6).toString().replace(",","")); } 	
				catch (NullPointerException e) { Prev_Rdng =0.00 ; }

				if (Curr_Rdng_str==""){
					Curr_Rdng =0.00 ;
				}else{ 
					Curr_Rdng = Double.parseDouble(Curr_Rdng_str);
				} 	
				if(Integer.parseInt(modelWBilling.getValueAt(selected_row, 4).toString()) > 0) {

					Double Totl_Rdng = Curr_Rdng - Prev_Rdng;

					System.out.println("");
					System.out.println("Name: "+modelWBilling.getValueAt(selected_row, 2));
					System.out.printf("Prev_Rdng #1: " + Prev_Rdng + "\n");
					System.out.printf("Curr_Rdng #1: " + Curr_Rdng + "\n");
					System.out.printf("Totl_Rdng #1: " + Totl_Rdng + "\n");
					if (Totl_Rdng >=0 && Totl_Rdng <= 10.00) {
						if (Curr_Rdng_str.equals("")){}
						else {modelWBilling.setValueAt(240.00, selected_row, 5);}

						System.out.printf("Water Cost 1: " + 240.00 + "\n");
					}else if (Totl_Rdng > 10.00 && Totl_Rdng <= 20.00) {
						//modelWBilling.setValueAt(true, row, 0);
						modelWBilling.setValueAt(Totl_Rdng*27.00, selected_row, 5);

						System.out.printf("Water Cost 2: " + Totl_Rdng*27.00 + "\n");
					}else if (Totl_Rdng > 20.00 && Totl_Rdng <= 30.00) {
						//modelWBilling.setValueAt(true, row, 0);
						modelWBilling.setValueAt(Totl_Rdng*30.00, selected_row, 5);

						System.out.printf("Water Cost 3: " + Totl_Rdng*30.00 + "\n");
					}else if (Totl_Rdng > 30.00 && Totl_Rdng <= 40) {
						//modelWBilling.setValueAt(true, row, 0);
						modelWBilling.setValueAt(Totl_Rdng*33.00, selected_row, 5);
						System.out.printf("Water Cost 4: " + Totl_Rdng*33.00 + "\n");
					}else if (Totl_Rdng >= 41.00) {
						//modelWBilling.setValueAt(true, row, 0);
						modelWBilling.setValueAt(Totl_Rdng*38.00, selected_row, 5);
						System.out.printf("Water Cost 5: " + Totl_Rdng*38.00 + "\n");
					}		
				}

				totalRepair(modelWBilling, modelWBilling_total);
			}
			
		}

	}
	
	private void computeWaterCostV2() {

		//if(tblWBilling.getSelectedRowCount() == 1) {
		String proj_id = lookupProject.getValue();
		//int selected_row = tblWBilling.getSelectedRow();
		//System.out.printf("Value of selected row: %s%n", selected_row);

		/*if(selected_row == 0) {
					selected_row -= 1;
				}*/
		Double Prev_Rdng = null;
		Double Curr_Rdng = null;
		Double garbage_fee = null;
		String Curr_Rdng_str = null;
		
		for(int x= 0; x< modelWBilling.getRowCount(); x++) {
			Boolean isSelected = (Boolean) modelWBilling.getValueAt(x, 0);
			
			if(isSelected) {
				//System.out.println("Compute Water Cost");
				/*Double Prev_Rdng = null;
				Double Curr_Rdng = null;
				Double garbage_fee = null;
				String Curr_Rdng_str = null;*/
				

				try {
					System.out.printf("Value of index: %s%n", x);
					Curr_Rdng_str = tblWBilling.getValueAt(x, 4).toString().replace(",","");
					//Curr_Rdng_str = String.valueOf(modelWBilling.getValueAt(x, 4).toString());
				} catch (NullPointerException e) {
					System.out.printf("Value of getmodel: %s%n", modelWBilling.getValueAt(x, 4));
					Curr_Rdng_str = "0"; 
				}
				//Curr_Rdng_str = tblWBilling.getValueAt(selected_row, 4).toString();

				/*System.out.printf("Data type of getvalue: %s%n", modelWBilling.getValueAt(selected_row, 4).getClass().getSimpleName());
					Curr_Rdng_str = String.valueOf(modelWBilling.getValueAt(selected_row, 4));*/
				//current_reading = (BigDecimal) modelWBilling.getValueAt(selected_row, 4);
				//Curr_Rdng_str = Double.valueOf(model)
				//BigDecimal current_reading = (BigDecimal) modelWBilling.getValueAt(selected_row, 4);

				System.out.printf("Current Reading Str " + Curr_Rdng_str + "\n");
				/*System.out.printf("Current Reading BD " + current_reading + "\n");*/
				try { 
					Prev_Rdng = Double.parseDouble(modelWBilling.getValueAt(x, 3).toString().replace(",","")); 
				}catch (NullPointerException e) { 
					Prev_Rdng =0.00 ; 
				}	

				try { 
					garbage_fee = Double.parseDouble(modelWBilling.getValueAt(x, 6).toString().replace(",","")); 
				}catch (NullPointerException e) { 
					Prev_Rdng =0.00 ; 
				}

				/*if (Curr_Rdng_str==""){
							Curr_Rdng =0.00 ;
						}else{*/ 
				Curr_Rdng = Double.parseDouble(Curr_Rdng_str);

				//Curr_Rdng = Curr_Rdng_str.doubleValue();
				//} 	

				Double Totl_Rdng = 0.00;

				/*System.out.println("");
				System.out.println("Name: "+modelWBilling.getValueAt(x, 2));
				System.out.printf("Prev_Rdng #1: " + Prev_Rdng + "\n");
				System.out.printf("Curr_Rdng #1: " + Curr_Rdng + "\n");
				System.out.printf("Totl_Rdng #1: " + Totl_Rdng + "\n");*/

				if(proj_id.equals("015")) {
					Totl_Rdng = Curr_Rdng - Prev_Rdng;

					if (Totl_Rdng >=0 && Totl_Rdng <= 10.00) {
						if (Curr_Rdng_str.equals("")){}
						else {modelWBilling.setValueAt(240.00, x, 5);}

						System.out.printf("Water Cost 1: " + 240.00 + "\n");
					}else if (Totl_Rdng > 10.00 && Totl_Rdng <= 20.00) {
						//modelWBilling.setValueAt(true, row, 0);
						modelWBilling.setValueAt(Totl_Rdng*27.00, x, 5);

						System.out.printf("Water Cost 2: " + Totl_Rdng*27.00 + "\n");
					}else if (Totl_Rdng > 20.00 && Totl_Rdng <= 30.00) {
						//modelWBilling.setValueAt(true, row, 0);
						modelWBilling.setValueAt(Totl_Rdng*30.00, x, 5);

						System.out.printf("Water Cost 3: " + Totl_Rdng*30.00 + "\n");
					}else if (Totl_Rdng > 30.00 && Totl_Rdng <= 40) {
						//modelWBilling.setValueAt(true, row, 0);
						modelWBilling.setValueAt(Totl_Rdng*33.00, x, 5);
						System.out.printf("Water Cost 4: " + Totl_Rdng*33.00 + "\n");
					}else if (Totl_Rdng >= 41.00) {
						//modelWBilling.setValueAt(true, row, 0);
						modelWBilling.setValueAt(Totl_Rdng*38.00, x, 5);
						System.out.printf("Water Cost 5: " + Totl_Rdng*38.00 + "\n");
					}
				}

				if(proj_id.equals("017")) {
					modelWBilling.setValueAt(BigDecimal.valueOf(Curr_Rdng*23.00), x, 5);
				}


				totalRepair(modelWBilling, modelWBilling_total);
				
			}
		}
		//totalRepair2(modelWBilling, modelWBilling_total);
		tblWBilling.packAll();
		tblWBilling_total.packAll();
		//}
	}



	//save & preview
	private void save(){


		if (lookupReader.getText().equals("")){
			JOptionPane.showMessageDialog(getContentPane(),"Please indicate Meter Reader.","ERROR",JOptionPane.ERROR_MESSAGE);	
		}else {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String reading = null;

				for(int x =0; x < modelWBilling.getRowCount(); x++){

					Boolean selected = (Boolean) modelWBilling.getValueAt(x, 0);
					System.out.println("Dumaan dito sa Water Bill");
					if(selected) {
						
						try { 
							reading = modelWBilling.getValueAt(x, 4).toString().replace(",","");
						} 	catch (NullPointerException e1) { 
							reading = "" ; 
						}

						//if (!reading.equals("")){
							String pbl = (String) modelWBilling.getValueAt(x, 9);
							Double prev_reading;
							Double curr_reading;
							Double water_cost;
							try { 
								prev_reading = Double.parseDouble(modelWBilling.getValueAt(x, 3).toString().replace(",",""));
							} 	catch (NullPointerException e1) { 
								prev_reading = 0.0; 
							}
							
							try { 
								curr_reading = Double.parseDouble(modelWBilling.getValueAt(x, 4).toString().replace(",",""));
							} 	catch (NullPointerException e1) { 
								curr_reading = 0.0;
							}
							
							try {
								water_cost 	= Double.parseDouble(modelWBilling.getValueAt(x, 5).toString().replace(",",""));
							}catch (NullPointerException e1) { 
								water_cost = 0.0;
							}
							

							String entity = (String) modelWBilling.getValueAt(x, 10);
							String reader = (String) lookupReader.getText();
							Date reading_date = (Date) dateReading.getDate();
							String remarks = (String) modelWBilling.getValueAt(x, 8);
							String seq_no = modelWBilling.getValueAt(x, 12).toString();
							//String batch_no =  generateBatchNo().toString();

							String strSQL = "SELECT sp_save_water_reading('"+entity+"', '"+proj_id+"', '"+pbl+"', '"+reader+"', " +
									"	'"+reading_date+"'::timestamp, "+prev_reading+", "+curr_reading+", "+water_cost+", null," +
									"  '"+UserInfo.EmployeeCode +"', '"+remarks+"', '"+seq_no+"');" ; 

							pgSelect db = new pgSelect();
							db.select(strSQL);	

							//System.out.println("batch no.  " +batch_no);
							System.out.println("strSQL: "+strSQL); 

						//}

						//}

					}
				}

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Water Meter Reading saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void preview(){	

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("phase_code", ph_code);
		mapParameters.put("phase_no", ph_no);
		mapParameters.put("block_no", "");
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", "");
		mapParameters.put("date_fr", dateReading.getDate());	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));

		FncReport.generateReport("/Reports/rptMonthlyStatementofAccount.jasper", "Transmittal Form", mapParameters);

		System.out.println("pbl_id: "+pbl_id); 
		System.out.println("pbl_id: "+pbl_id); 

	}

	private void previewAllBillings() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("proj_id", "");
		mapParameters.put("phase_no", ph_no);
		mapParameters.put("entity_id", "");
		mapParameters.put("phase_no", ph_no);
		mapParameters.put("pbl_id", "");
		mapParameters.put("seq_no", "");
		mapParameters.put("reading_date", dateReading.getDate());	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		
		//System.out.print

		FncReport.generateReport("/Reports/rptMonthlyStatementofAccount_v2.jasper", "Transmittal Form", mapParameters);
	}
	
	private JPanel pnlNorth() {
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BorderLayout(0,0));
		pnlMain.setPreferredSize(new Dimension(900, 160));
		{
			//company
			JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCompany, BorderLayout.NORTH);	
			pnlCompany.setPreferredSize(new Dimension(900, 90));
			pnlCompany.setBorder(JTBorderFactory.createTitleBorder("Company Details"));
			{
				JPanel pnlWest = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlCompany.add(pnlWest, BorderLayout.WEST);	
				pnlWest.setPreferredSize(szPanelWest);
				pnlWest.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
				{
					lblCompany = new JLabel("COMPANY", JLabel.TRAILING);
					pnlWest.add(lblCompany);
					lblCompany.setBounds(8, 11, 62, 12);
					lblCompany.setPreferredSize(new Dimension(115, 25));
					lblCompany.setFont(new Font("Segoe UI",Font.BOLD,12));
				}
				{
					lblProject = new JLabel("Project", JLabel.TRAILING);
					pnlWest.add(lblProject);
					lblProject.setBounds(8, 11, 62, 12);
					lblProject.setEnabled(false);
					lblProject.setPreferredSize(new Dimension(96, 26));
				}	
				{
					lblPhase = new JLabel("Phase", JLabel.TRAILING);
					pnlWest.add(lblPhase);
					lblPhase.setBounds(8, 11, 62, 12);
					lblPhase.setEnabled(false);
					lblPhase.setPreferredSize(new java.awt.Dimension(96, 26));
				}
			}
			{
				JPanel pnlCenter = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlCompany.add(pnlCenter, BorderLayout.CENTER);
				{
					lookupCompany = new _JLookup(null, "Company",0,2);
					pnlCenter.add(lookupCompany);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.setReturnColumn(0);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								co_id 		= (String) data[0];	
								company     = (String) data[1];						
								tagCompany.setTag(company);

								lblProject.setEnabled(true);
								lookupProject.setEnabled(true);
								tagProject.setEnabled(true);	

								lookupProject.setValue("");	
								tagProject.setTag("");	

								KEYBOARD_MANAGER.focusNextComponent();		
								lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
							}
						}
					});
				}
				{
					lookupProject = new _JLookup(null, "Project",0,2);
					pnlCenter.add(lookupProject);
					lookupProject.setReturnColumn(0);
					lookupProject.setEnabled(false);
					lookupProject.setPreferredSize(new java.awt.Dimension(103, 26));
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								proj_id =(String) data[0];	
								proj_name 	= (String) data[1];				
								tagProject.setTag(proj_name);

								lblPhase.setEnabled(true);	
								lookupPhase.setEnabled(true);	
								tagPhase.setEnabled(true);	

								lookupPhase.setValue("");	
								tagPhase.setTag("");	

								KEYBOARD_MANAGER.focusNextComponent();		
								lookupPhase.setLookupSQL(getPhase());
							}
						}
					});
				}
				{
					lookupPhase = new _JLookup(null, "Unit",0,2);
					pnlCenter.add(lookupPhase);
					lookupPhase.setReturnColumn(0);
					lookupPhase.setEnabled(false);
					lookupPhase.setPreferredSize(new java.awt.Dimension(103, 26));
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								ph_code =(String) data[0];	
								ph_no 	= (String) data[1];				
								tagPhase.setTag(ph_no);							
							}
						}
					});
				}
			}
			{
				JPanel pnlEast = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlCompany.add(pnlEast, BorderLayout.EAST);
				pnlEast.setPreferredSize(szPanelEast);
				{
					tagCompany = new _JTagLabel("[ ]");
					pnlEast.add(tagCompany);
					tagCompany.setBounds(209, 27, 700, 22);
					tagCompany.setEnabled(true);	
					tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagProject = new _JTagLabel("[ ]");
					pnlEast.add(tagProject);
					tagProject.setBounds(209, 27, 700, 22);
					tagProject.setEnabled(false);	
					tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagPhase = new _JTagLabel("[ ]");
					pnlEast.add(tagPhase);
					tagPhase.setBounds(209, 27, 700, 22);
					tagPhase.setEnabled(false);	
					tagPhase.setPreferredSize(new java.awt.Dimension(27, 33));
				}
			}
		}
		{
			JPanel pnlReadingDetails = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlReadingDetails, BorderLayout.CENTER);
			pnlReadingDetails.setBorder(JTBorderFactory.createTitleBorder("Water Reading Details"));
			{
				JPanel pnlWest = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlReadingDetails.add(pnlWest, BorderLayout.WEST);
				pnlWest.setPreferredSize(szPanelWest);
				{
					lblReader = new JLabel("Meter Reader", JLabel.TRAILING);
					pnlWest.add(lblReader);
					lblReader.setEnabled(true);	
				}
				{
					lblDate = new JLabel("Date", JLabel.TRAILING);
					pnlWest.add(lblDate);
					lblDate.setEnabled(true);	
				}
			}
			{
				JPanel pnlCenter = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlReadingDetails.add(pnlCenter, BorderLayout.CENTER);
				{
					lookupReader = new _JLookup(null, "Reader",0,2);
					pnlCenter.add(lookupReader);
					lookupReader.setReturnColumn(0);
					lookupReader.setLookupSQL(getReader());
					lookupReader.setEnabled(true);
					lookupReader.setPreferredSize(new java.awt.Dimension(103, 26));
					lookupReader.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
	
								reader_id 	=(String) data[0];	
								reader_name = (String) data[1];				
								tagReader.setTag(reader_name);
								btnGenerate.setEnabled(true);
							}
						}
					});
				}
				{
					dateReading = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenter.add(dateReading, BorderLayout.EAST);
					dateReading.setDate(null);
					dateReading.setEnabled(true);
					dateReading.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}
			}
			{
				JPanel pnlEast = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlReadingDetails.add(pnlEast, BorderLayout.EAST);
				pnlEast.setPreferredSize(szPanelEast);
				{
					tagReader = new _JTagLabel("[ ]");
					pnlEast.add(tagReader);
					tagReader.setEnabled(true);	
				}
				{
					JPanel pnlSearchClient = new JPanel(new BorderLayout(5, 5));
					pnlEast.add(pnlSearchClient);
					{
						Color maroon = new Color (128, 0, 0);
						lblClientName = new JLabel("Search Client : ", JLabel.TRAILING);
						pnlSearchClient.add(lblClientName, BorderLayout.WEST);
						lblClientName.setEnabled(true);
						lblClientName.setFont(new Font("Segoe UI",Font.ITALIC + Font.BOLD,12));
						lblClientName.setForeground(maroon);
					}
					{
						txtClientName= new JXTextField("");
						pnlSearchClient.add(txtClientName, BorderLayout.CENTER);
						txtClientName.setEnabled(true);
						txtClientName.setEditable(true);
//						txtClientName.setHorizontalAlignment(JTextField.LEFT);	
						txtClientName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						txtClientName.setPreferredSize(new java.awt.Dimension(573, 42));
						txtClientName.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {		

								checkAllClientList();

							}				 
						});	
					}
				}
			}
		}
		return pnlMain;
	}
	
	public void menuPopup() {
		menu = new JPopupMenu("Popup");
		mniViewBill = new JMenuItem("View Client's Billing");
		//mniViewDisconnection = new JMenuItem("View Client Disconnection");	
		menu.add(mniViewBill);
		//menu.add(mniViewDisconnection);

		//if(UserInfo.EmployeeCode.equals("900876") == false) {
		mniViewBill.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent evt){ 
				if(tblWBilling.getSelectedRows().length == 1) { 
					int row = tblWBilling.getSelectedRow();

					String entity_id = (String) modelWBilling.getValueAt(row, 10); 
					String proj_id = (String) modelWBilling.getValueAt(row, 11); 
					String pbl_id = (String) modelWBilling.getValueAt(row, 9);
					String seq_no = (String) modelWBilling.getValueAt(row, 12);
					System.out.printf("Display value of entity_id: %s%n", entity_id);
					System.out.printf("Display value of proj_id: %s%n", proj_id);
					System.out.printf("Display value of pbl_id: %s%n", pbl_id);
					System.out.printf("Display value of seq_no: %s%n", seq_no);
					System.out.printf("Display value of co_id: %s%n", lookupCompany.getValue());
					System.out.printf("Display value of phase: %s%n", ph_no);
					System.out.printf("Display value of reading date: %s%n",dateReading.getDate());

					Map<String, Object> mapParameters = new HashMap<String, Object>();

					mapParameters.put("co_id", lookupCompany.getValue());
					mapParameters.put("proj_id", proj_id); 
					mapParameters.put("phase_no", ph_no);
					mapParameters.put("entity_id", entity_id); 
					mapParameters.put("phase_no", ph_no); 
					mapParameters.put("pbl_id", pbl_id); 
					mapParameters.put("seq_no", seq_no); 
					mapParameters.put("reading_date", dateReading.getDate());
					mapParameters.put("logo",this.getClass().getClassLoader().getResourceAsStream("Images/"+company_logo));

					FncReport.generateReport("/Reports/rptMonthlyStatementofAccount_v2.jasper","Transmittal Form", mapParameters);

				} 
			} 
		});
		/*}else {
			mniViewBill.addActionListener(new ActionListener(){ 
				public void actionPerformed(ActionEvent evt){ 
					int row = tblWBilling.getSelectedRow();
					pbl_id = ""; entity_id = ""; 
					try { 
						pbl_id = modelWBilling.getValueAt(row,8).toString().replace(",",""); 
					} catch (NullPointerException e) { 
						pbl_id = ""; 
					} try { 
						entity_id = modelWBilling.getValueAt(row,9).toString().replace(",",""); 
					} catch (NullPointerException e) { 
						entity_id = "" ; 
					}

					System.out.printf("pbl_id #1: " + pbl_id + "\n"); preview(); 
				} 
			});
		}*/


		/*mniViewDisconnection.addActionListener(new ActionListener(){
			public void	actionPerformed(ActionEvent evt){
				int row = tblWBilling.getSelectedRow();
				pbl_id 	 = "";
				entity_id = "";		
				try { pbl_id = modelWBilling.getValueAt(row, 7).toString().replace(",",""); } catch (NullPointerException e) { pbl_id = "" ; }
				try { entity_id = modelWBilling.getValueAt(row, 8).toString().replace(",",""); } catch (NullPointerException e) { entity_id = "" ; }		

				wtr_disconnection();				
			}
		});*/
	}
	
	private JPanel pnlTable() {
		pnlTable = new JPanel(new BorderLayout(5, 5));
		pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

		pnlBillingTable = new JPanel();
		pnlTable.add(pnlBillingTable, BorderLayout.CENTER);
		pnlBillingTable.setLayout(new BorderLayout(5, 5));
		pnlBillingTable.setPreferredSize(new java.awt.Dimension(923, 284));
		pnlBillingTable.setBorder(lineBorder);		

		{			
			pnTableRepair = new JPanel(new BorderLayout());
			pnlBillingTable.add(pnTableRepair, "Center");
			pnTableRepair.setPreferredSize(new java.awt.Dimension(1183, 365));				

			{
				scrollMeterReader = new _JScrollPaneMain();
				pnTableRepair.add(scrollMeterReader, BorderLayout.CENTER);
				{
					modelWBilling = new model_water_billing();

					tblWBilling = new _JTableMain(modelWBilling);
					scrollMeterReader.setViewportView(tblWBilling);
					tblWBilling.addMouseListener(this);	
					tblWBilling.addMouseListener(new PopupTriggerListener_panel());
					tblWBilling.hideColumns("PBL");
					tblWBilling.hideColumns("Entity");
					tblWBilling.hideColumns("Proj ID");
					tblWBilling.hideColumns("Seq No.");
					tblWBilling.setSortable(false);
					tblWBilling.getColumnModel().getColumn(7).setPreferredWidth(250);
					tblWBilling.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblWBilling.getTableHeader().setEnabled(false);
					tblWBilling.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							//displayAddReading();


						}	
						public void keyReleased(KeyEvent evt) {
							//displayAddReading();
							//computeWaterCost();
							if(tblWBilling.getSelectedRows().length == 1) {
//								computeWaterCost();
//								computeWaterCostV2();
								
							}
						}							
						public void keyPressed(KeyEvent e) {
							//displayAddReading();
							//computeWaterCost();
						}

					}); 
					tblWBilling.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						
						@Override
						public void valueChanged(ListSelectionEvent e) {
							computeWaterCostV2();
						}
					});
					modelWBilling.addTableModelListener(new TableModelListener() {
						
						@Override
						public void tableChanged(TableModelEvent e) {
							
						}
					});
					/*
					 * tblWBilling.addMouseListener(new MouseAdapter() { public void
					 * mousePressed(MouseEvent e) { if(tblWBilling.rowAtPoint(e.getPoint()) == -1){
					 * tblWBilling_total.clearSelection(); }else{
					 * tblWBilling.setCellSelectionEnabled(true); } //displayAddReading();
					 * //computeWaterCost(); } public void mouseReleased(MouseEvent e) {
					 * if(tblWBilling.rowAtPoint(e.getPoint()) == -1){
					 * tblWBilling_total.clearSelection(); }else{
					 * 
					 * } //displayAddReading(); //computeWaterCost(); } });
					 */
				}
				{
					rowHeaderRepair = tblWBilling.getRowHeader22();
					scrollMeterReader.setRowHeaderView(rowHeaderRepair);
					scrollMeterReader.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				scrollMeterReader_total = new _JScrollPaneTotal(scrollMeterReader);
				pnTableRepair.add(scrollMeterReader_total, BorderLayout.SOUTH);
				{
					modelWBilling_total = new model_water_billing();
					modelWBilling_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), 
							new BigDecimal(0.00), new BigDecimal(0.00),  null });

					tblWBilling_total = new _JTableTotal(modelWBilling_total, tblWBilling);
					tblWBilling_total.setFont(dialog11Bold);
					scrollMeterReader_total.setViewportView(tblWBilling_total);
					((_JTableTotal) tblWBilling_total).setTotalLabel(0);
				}
			}
		}
		return pnlTable;
	}
	
	private JPanel pnlSouth() {
		pnlSouth = new JPanel();
		pnlSouth.setLayout(new BorderLayout());
		pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		pnlSouth.setPreferredSize(new Dimension(55, 30));
		{
			JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
			pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));

			{
				btnGenerate = new JButton("Generate Units");
				pnlSouthCenter.add(btnGenerate);
				btnGenerate.setActionCommand("Generate");
				btnGenerate.addActionListener(this);
				btnGenerate.setEnabled(true);
			}
			{
				btnSave = new JButton("Save");
				pnlSouthCenter.add(btnSave);
				btnSave.setActionCommand("Save");
				btnSave.addActionListener(this);
				btnSave.setEnabled(false);
			}
			{
				btnPreview = new JButton("View All Billings");
				pnlSouthCenter.add(btnPreview);
				btnPreview.setActionCommand("Preview");
				btnPreview.addActionListener(this);
				btnPreview.setEnabled(true);
			}
			{
				btnRefresh = new JButton("Refresh");
				pnlSouthCenter.add(btnRefresh);
				btnRefresh.setActionCommand("Refresh");
				btnRefresh.addActionListener(this);
				btnRefresh.setEnabled(true);
			}

		}
		return pnlSouth;
	}

	/*	  private static String generateBatchNo() {
			String strSQL = "SELECT to_char(COALESCE(MAX(batch_no::INT), 0), 'FM0000000000') FROM rf_water_reading_v2 WHERE status_id = 'A' and batch_no is not null and batch_no != '';";

			FncSystem.out("Batch No", strSQL);
			pgSelect db = new pgSelect();
			db.select(strSQL);

			if(db.isNotNull()){
				return (String) db.getResult()[0][0];
			}else{
				return null;
			}
		}*/


}
