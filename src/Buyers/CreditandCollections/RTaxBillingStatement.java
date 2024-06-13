package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_RTaxBilling;

public class RTaxBillingStatement extends _JInternalFrame implements _GUI, ActionListener{

	static Dimension size = new Dimension(750, 550);
	private _JLookup lookupCompany;
	private _JLookup lookupProj;
	private JButton btnGenerate;
	private JDateChooser dateBillingDate;
	private JTextField txtCompany;
	private JTextField txtProj;
	private JButton btnViewAll;
	private JButton btnRefresh;
	private JScrollPane scrollData;
	private model_RTaxBilling tbl_model_RTaxBilling;
	private _JTableMain tbl_RTaxBilling;
	private JList rowReadHeader;
	
	//variables 
	private String co_id = "";
	private String proj_id = "";
	private JComboBox comboVAl;
	private _JLookup lookupPhase;
	private JTextField txtPhase;
	private JButton btnPreview; 
	public static String company_logo;



	public RTaxBillingStatement() {
		super("Real Property Tax Billing Statement", false, true, true, true);
		initGUI();
	}


	@Override
	public void initGUI() {
		this.setSize(size);
		this.setLayout(new BorderLayout(5, 5));


		JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
		this.add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		{
			JPanel pnlGenAccnts = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlGenAccnts, BorderLayout.NORTH); 
			pnlGenAccnts.setBorder(components.JTBorderFactory.createTitleBorder("Generate Accounts"));
			pnlGenAccnts.setPreferredSize(new Dimension(0, 150));

			{
				JPanel pnlComp = new JPanel(new GridLayout(4, 2, 5, 5)); 
				pnlGenAccnts.add(pnlComp, BorderLayout.LINE_START); 
				pnlComp.setPreferredSize(new Dimension(250, 0));

				{
					JLabel lblCompany = new JLabel("     Company:");
					pnlComp.add(lblCompany); 

					lookupCompany = new _JLookup(null, "Company");
					pnlComp.add(lookupCompany);
					lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
					lookupCompany.setReturnColumn(0);
					lookupCompany.addLookupListener(new LookupListener() {

						@Override
						public void lookupPerformed(LookupEvent event) {

							Object data [] = ((_JLookup)event.getSource()).getDataSet();
							if (data != null) {

								txtCompany.setText(data[1].toString());
								company_logo = (String) data[3];
								lookupProj.setLookupSQL(SQL_PROJECT(data[0].toString()));
								lookupProj.setReturnColumn(0);
								lookupProj.setEnabled(true);
//								btnGenerate.setEnabled(true);
							} else {
								txtCompany.setText("");
							}
						}
					});


					JLabel lblProj = new JLabel("     Project:");
					pnlComp.add(lblProj); 


					lookupProj = new _JLookup(null, "Project");
//					lookupProj.setEnabled(false);
					pnlComp.add(lookupProj);
					lookupProj.addLookupListener(new LookupListener() {

						@Override
						public void lookupPerformed(LookupEvent event) {

							Object data [] = ((_JLookup)event.getSource()).getDataSet(); 
							if (data != null) {
								txtProj.setText(data[1].toString());
								lookupPhase.setLookupSQL(SQL_PHASE(data[0].toString()));
								lookupPhase.setReturnColumn(0);
								lookupPhase.setEnabled(true);
							} else {
								txtProj.setText("");
							}
						}
					});
					
					JLabel lblPhase = new JLabel("     Phase:");
					pnlComp.add(lblPhase); 


					lookupPhase = new _JLookup(null, "Phase");
					pnlComp.add(lookupPhase);
					lookupPhase.addLookupListener(new LookupListener() {

						@Override
						public void lookupPerformed(LookupEvent event) {

							Object data [] = ((_JLookup)event.getSource()).getDataSet(); 
							if (data != null) {
								txtPhase.setText(data[1].toString());
								dateBillingDate.setEnabled(true);
								comboVAl.setEnabled(true);
								btnGenerate.setEnabled(true);
							} else {
								txtPhase.setText("");
							}
						}
					});


					JLabel lblDate = new JLabel("     Date:"); 
					pnlComp.add(lblDate);

					dateBillingDate = new JDateChooser(); 
					pnlComp.add(dateBillingDate); 
					dateBillingDate.setDate(FncGlobal.getDateToday());
					dateBillingDate.setDateFormatString("MM/dd/yy");

				}				
			}

			{
				JPanel pnlComp2 = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlGenAccnts.add(pnlComp2, BorderLayout.CENTER); 

				{
					txtCompany = new JTextField(); 
					pnlComp2.add(txtCompany); 


					txtProj = new JTextField(); 
					pnlComp2.add(txtProj); 
					
					txtPhase = new JTextField(); 
					pnlComp2.add(txtPhase); 

					
					String [] status = {"All","Moved-In", "Not Yet Moved-In"};
					comboVAl = new JComboBox(status); 
					pnlComp2.add(comboVAl); 
					
				}	
			}

			{ 
				JPanel pnlGen = new JPanel(new BorderLayout(5, 5));
				pnlGenAccnts.add(pnlGen, BorderLayout.LINE_END); 
				pnlGen.setPreferredSize(new Dimension(180, 0));

				{
					btnGenerate = new JButton("Generate"); 
					pnlGen.add(btnGenerate, BorderLayout.CENTER); 
					btnGenerate.setEnabled(false);
					btnGenerate.setActionCommand("Generate");
					btnGenerate.addActionListener(this);

				}

			}

		}
		{
			JPanel pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);
			pnlTable.setBorder(new EmptyBorder(5, 5, 5, 5));

			{
				scrollData = new JScrollPane(); 
				pnlTable.add(scrollData, BorderLayout.CENTER); 

				tbl_model_RTaxBilling = new model_RTaxBilling(); 
				tbl_RTaxBilling = new _JTableMain(tbl_model_RTaxBilling); 
				
				
				rowReadHeader = tbl_RTaxBilling.getRowHeader();	
				rowReadHeader.setModel(new DefaultListModel());
				scrollData.setRowHeaderView(rowReadHeader);
				scrollData.setViewportView(tbl_RTaxBilling);
				scrollData.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				tbl_RTaxBilling.setSortable(true);
				
				tbl_RTaxBilling.getColumnModel().getColumn(0).setPreferredWidth(20);
				tbl_RTaxBilling.getColumnModel().getColumn(1).setPreferredWidth(150);
				tbl_RTaxBilling.getColumnModel().getColumn(2).setPreferredWidth(120);
				tbl_RTaxBilling.getColumnModel().getColumn(3).setPreferredWidth(100);
				tbl_RTaxBilling.getColumnModel().getColumn(4).setPreferredWidth(80);
				tbl_RTaxBilling.getColumnModel().getColumn(5).setPreferredWidth(80);
				tbl_RTaxBilling.getColumnModel().getColumn(6).setPreferredWidth(80);
				tbl_RTaxBilling.getColumnModel().getColumn(7).setPreferredWidth(100);
				tbl_RTaxBilling.hideColumns("Entity_ID");
				tbl_RTaxBilling.hideColumns("Proj_ID");
				tbl_RTaxBilling.hideColumns("PBL_ID");
				tbl_RTaxBilling.hideColumns("Seq_No");
				tbl_RTaxBilling.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				
				
				/*tbl_model_RTaxBilling.addTableModelListener(new TableModelListener() {
					
					@Override
					public void tableChanged(TableModelEvent arg0) {
						
						((DefaultListModel)rowReadHeader.getModel()).addElement(tbl_model_RTaxBilling.getRowCount());
						
						if(tbl_model_RTaxBilling.getRowCount() == 0){
							rowReadHeader.setModel(new DefaultListModel());
						}
						
					}
				});*/
 
			}


		}




		{
			JPanel pnlSouth = new JPanel(new GridLayout(1, 3, 5, 5)); 
			pnlMain.add(pnlSouth, BorderLayout.PAGE_END); 
			pnlSouth.setPreferredSize(new Dimension(0, 50));

			{
				btnPreview = new JButton("Preview"); 
				pnlSouth.add(btnPreview); 
				btnPreview.setEnabled(true);
				btnPreview.setActionCommand("Preview");
				btnPreview.addActionListener(this);
				

				btnViewAll = new JButton("View All Billings"); 
				pnlSouth.add(btnViewAll); 
				btnViewAll.setEnabled(false);
				btnViewAll.setActionCommand("View All");
				btnViewAll.addActionListener(this);


				btnRefresh = new JButton("Refresh");
				pnlSouth.add(btnRefresh);
				btnRefresh.setEnabled(false);
				btnRefresh.setActionCommand("Refresh");
				btnRefresh.addActionListener(this);


			}
		}
		
		initialize_comp();// Set default values to components
	}
	
	//UNCOMMENTED BY MONIQUE DTD 2023-07-28
	
	 private void initialize_comp(){
		
		 lookupCompany.setEnabled(true);
		 lookupProj.setEnabled(false);
		 lookupPhase.setEnabled(false);
		 dateBillingDate.setEnabled(false);
		 comboVAl.setEnabled(false);
		 btnPreview.setEnabled(false);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		/*if(e.getActionCommand().equals("Generate")){ 
		
			new Thread(new Runnable() {
				public void run() {
					FncGlobal.startProgress("Generating Accounts");
					
					displayData(tbl_model_RTaxBilling, rowReadHeader);
					btnViewAll.setEnabled(true);
					btnRefresh.setEnabled(true);
					JOptionPane.showMessageDialog(getContentPane(),"Units Generated.","Information",JOptionPane.INFORMATION_MESSAGE);	
					FncGlobal.stopProgress();
				}
			}).start();

		}*/
		
		if(e.getActionCommand().equals("Generate")){ 
			if (comboVAl.getSelectedIndex() == 0) {
				
				new Thread(new Runnable() {
					public void run() {
						FncGlobal.startProgress("Generating Accounts");
						
						displayDataMovedIn(tbl_model_RTaxBilling, rowReadHeader);
						btnViewAll.setEnabled(true);
						btnRefresh.setEnabled(true);
						JOptionPane.showMessageDialog(getContentPane(),"Units Generated.","Information",JOptionPane.INFORMATION_MESSAGE);	
						FncGlobal.stopProgress();
						btnPreview.setEnabled(true);
					}
				}).start();
				
			} else {
			
			new Thread(new Runnable() {
				public void run() {
					FncGlobal.startProgress("Generating Accounts");
					
					displayData2(tbl_model_RTaxBilling, rowReadHeader);
					btnViewAll.setEnabled(true);
					btnRefresh.setEnabled(true);
					JOptionPane.showMessageDialog(getContentPane(),"Units Generated.","Information",JOptionPane.INFORMATION_MESSAGE);	
					FncGlobal.stopProgress();
					btnPreview.setEnabled(true);
				}
			}).start();
			}

		}

		if(e.getActionCommand().equals("Refresh")){ 
			Refresh(); 
			
		} 

		if(e.getActionCommand().equals("View All")){ 
			previewAllBillings();
		}
		
		if(e.getActionCommand().equals("Preview")){ 
			previewSelectedBillings();
		}
		
	}

	private void displayDataMovedIn(DefaultTableModel model_RTaxBilling, JList rowReadHeader) {
		FncTables.clearTable(tbl_model_RTaxBilling);
		DefaultListModel list = new DefaultListModel(); 
		rowReadHeader.setModel(list);
	
	String SQL = "Select c_select, c_client_name, c_project, c_unit, c_rtax_year_covered, c_lot_amt_topay, c_hse_amt_topay, c_total_rpt_amt, c_entity_id, c_proj_id, c_pbl_id, c_seq_no "
					+  "from view_rtax_billing_statement('"+lookupCompany.getValue()+"', '',  '"+lookupProj.getValue()+"', '"+lookupPhase.getValue()+"', '', '', '"+dateBillingDate.getDate()+"', '"+comboVAl.getSelectedItem()+"')";
	
				
		pgSelect db = new pgSelect();
		System.out.println("SQL: " + SQL);
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				tbl_model_RTaxBilling.addRow(db.getResult()[x]);	
				list.addElement(tbl_model_RTaxBilling.getRowCount());
				scrollData.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tbl_RTaxBilling.getRowCount())));
				
			}


		}
	}
	
	private void displayData2(DefaultTableModel model_RTaxBilling, JList rowReadHeader) {
		FncTables.clearTable(tbl_model_RTaxBilling);
		DefaultListModel list = new DefaultListModel(); 
		rowReadHeader.setModel(list);
		String SQL = "Select c_select, c_client_name, c_project, c_unit, c_rtax_year_covered, c_lot_amt_topay, c_hse_amt_topay, c_total_rpt_amt, c_entity_id, c_proj_id, c_pbl_id, c_seq_no  "
					//+  "from view_rtax_billing_statement(' "+lookupCompany.getValue()+"', '"+lookupProj.getValue()+"', '"+lookupPhase.getValue()+"') where c_total_rpt_amt != 0.00 and c_movein_date is null order by c_unit;";
				+  "from view_rtax_billing_statement('"+lookupCompany.getValue()+"', '',  '"+lookupProj.getValue()+"', '"+lookupPhase.getValue()+"', '', '', '"+dateBillingDate.getDate()+"', '"+comboVAl.getSelectedItem()+"');";
				
		pgSelect db = new pgSelect();
		System.out.println("SQL: " + SQL);
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				tbl_model_RTaxBilling.addRow(db.getResult()[x]);	
				list.addElement(tbl_model_RTaxBilling.getRowCount());
				scrollData.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tbl_RTaxBilling.getRowCount())));
				
			}
		}
	}
	
	private void Refresh() {
		FncTables.clearTable(tbl_model_RTaxBilling);
		lookupCompany.setValue("");
		txtCompany.setText("");
		lookupProj.setValue("");
		lookupProj.setEnabled(false);
		txtProj.setText("");	
		lookupPhase.setValue("");
		lookupPhase.setEnabled(false);
		txtPhase.setText("");
		dateBillingDate.setEnabled(false);
		comboVAl.setEnabled(false);
//		dateBillingDate.setDate(FncGlobal.getDateToday());
//		dateBillingDate.setDateFormatString("MM/dd/yy");
		btnPreview.setEnabled(false);
		btnGenerate.setEnabled(false);
		btnViewAll.setEnabled(false);
		btnRefresh.setEnabled(false); 
		
	}
	
	private void previewSelectedBillings() {
		String selected_entity_id = "";
		String selected_proj_id = "";
		String selected_pbl_id = "";
		String selected_seq_no = "";

		for (int i = 0; i < tbl_model_RTaxBilling.getRowCount(); i++) {
			boolean tag = (boolean) tbl_model_RTaxBilling.getValueAt(i, 0);
			if(tag) {
				String entity_id = (String) tbl_model_RTaxBilling.getValueAt(i, 8);
				String proj_id = (String) tbl_model_RTaxBilling.getValueAt(i, 9);
				String pbl_id = (String) tbl_model_RTaxBilling.getValueAt(i, 10);
				Object seq_no = tbl_model_RTaxBilling.getValueAt(i, 11);
				
				if(i == selected_count()) {
					selected_entity_id += entity_id;
					selected_proj_id += proj_id;
					selected_pbl_id += pbl_id;
					selected_seq_no += seq_no;
				} else {
					selected_entity_id += entity_id + ",";
					selected_proj_id += proj_id + ",";
					selected_pbl_id += pbl_id + ",";
					selected_seq_no += seq_no + ",";
				}
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", lookupCompany.getValue());
		//mapParameters.put("proj_id", lookupProj.getValue());
		mapParameters.put("proj_id", selected_proj_id);
		mapParameters.put("phase", lookupPhase.getValue());
		mapParameters.put("status", comboVAl.getSelectedItem()); 
		mapParameters.put("entity_id", selected_entity_id);	
		mapParameters.put("pbl_id", selected_pbl_id);
		mapParameters.put("seq_no", selected_seq_no);
		mapParameters.put("reading_date", dateBillingDate.getDate());	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		System.out.println("Dumaan dito sa Preview Billings");
		FncReport.generateReport("/Reports/rptRTaxBillingStatement.jasper", "Billing Statement", mapParameters);
		System.out.println("report: rptRTaxBillingStatement.jasper");
		
		String sql = "Select * from view_rtax_billing_statement('"+lookupCompany.getValue()+"', '"+selected_entity_id+"', '"+selected_proj_id+"', '"+lookupPhase.getValue()+"', '"+selected_pbl_id+"', '"+selected_seq_no+"', '"+dateBillingDate.getDate()+"', '"+comboVAl.getSelectedItem()+"');";
		FncSystem.out("RTax Billing Statement Report", sql);

	}
	
	
	
	private void previewAllBillings() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("proj_id", lookupProj.getValue());
		mapParameters.put("phase", lookupPhase.getValue());
		mapParameters.put("status", comboVAl.getSelectedItem()); 
		mapParameters.put("entity_id", "");	
		mapParameters.put("pbl_id", "");
		mapParameters.put("seq_no", "");
		mapParameters.put("reading_date", dateBillingDate.getDate());	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		System.out.println("Dumaan dito sa Preview Billings");
		FncReport.generateReport("/Reports/rptRTaxBillingStatement.jasper", "Billing Statement", mapParameters);
		System.out.println("report: rptRTaxBillingStatement.jasper");
	}
	
	private int selected_count() {
		int count = 0;
		for (int i = 0; i < tbl_model_RTaxBilling.getRowCount(); i++) {
			boolean tag = (boolean) tbl_model_RTaxBilling.getValueAt(i, 0);
			if(tag) {
				count += 1;
			}
		}
		return count;
	}
}
