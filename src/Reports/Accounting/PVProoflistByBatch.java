package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelPVProoflistBatch;

public class PVProoflistByBatch extends JDialog implements _GUI, ActionListener {

	/*
	 * AS OF 2021-11-25
	 * 
	 * 1. PREVIEWING OF REPORT - PHASE WAS ADDED IN THE PARTICULARS 2021-11-25
	 * */
	/**
	 * 
	 */
	static String title = "PV Prooflist By Batch";
	private static final long serialVersionUID = 1L;
	private JXPanel pnlMain;
	private JPanel pnlBatch;
	private JScrollPane scrollPVBatch;
	private modelPVProoflistBatch modelPVBatch;
	private _JTableMain tblPVBatch;
	private JList rowHeaderPVBatch;
	private JButton btnPrevBatch;
	private JButton btnCancelBatch;
	private static String co_id;
	private Integer pv_from;
	private Integer pv_to;
	private java.util.Date date_from;
	private java.util.Date date_to;
	private String req_type;
	private String payee;
	private String availer;
	private String prep_by;
	private String pv_status;
	private String payment_type;
	private String company;

	private NumberFormat formatter = new DecimalFormat("#,##0.00"); 

	public PVProoflistByBatch(Frame owner, String title, String co_id, Integer pv_fr, Integer pv_to, java.util.Date date_fr, 
			java.util.Date date_to, String req_type_id, String payee_id, String availer_id, String prepared_by_id, String status_id, 
			String pmt_type_id, String company_name) {
		super(owner, title);
		this.co_id = co_id;
		this.pv_from = pv_fr;
		this.pv_to = pv_to;
		this.date_from = date_fr;
		this.date_to = date_to;
		this.req_type = req_type_id;
		this.payee = payee_id;
		this.availer = availer_id;
		this.prep_by = prepared_by_id;
		this.pv_status = status_id;
		this.payment_type = pmt_type_id;
		this.company = company_name;
		initGUI();
	}

	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout(5,5));
		this.setSize(new Dimension(750,400));
		this.setModal(true);
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		{
			pnlMain = new JXPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				pnlBatch = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlBatch, BorderLayout.CENTER);
				{
					JPanel pnlBatchCenter = new JPanel(new BorderLayout(5,5));
					pnlBatch.add(pnlBatchCenter, BorderLayout.CENTER);
					{
						scrollPVBatch = new JScrollPane();
						pnlBatch.add(scrollPVBatch);
						scrollPVBatch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						{
							modelPVBatch = new modelPVProoflistBatch();
							tblPVBatch = new _JTableMain(modelPVBatch);

							scrollPVBatch.setViewportView(tblPVBatch);
							tblPVBatch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblPVBatch.setFont(FncGlobal.sizeTextValue);
						}
						{ 
							rowHeaderPVBatch = tblPVBatch.getRowHeader(); 
							rowHeaderPVBatch.setModel(new DefaultListModel()); 
							scrollPVBatch.setRowHeaderView(rowHeaderPVBatch);
							scrollPVBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header()); 
						}
					}
				}
				{
					JPanel pnlBatchSouth = new JPanel(new GridLayout(1,2,5,5));
					pnlBatch.add(pnlBatchSouth, BorderLayout.SOUTH);
					pnlBatchSouth.setPreferredSize(new Dimension(0,30));
					{
						btnPrevBatch = new JButton("Preview");
						pnlBatchSouth.add(btnPrevBatch);
						btnPrevBatch.addActionListener(this);
						btnPrevBatch.setActionCommand("Show Batch");
						btnPrevBatch.setFont(FncGlobal.sizeControls);
					}
					{
						btnCancelBatch = new JButton("Cancel");
						pnlBatchSouth.add(btnCancelBatch);
						btnCancelBatch.addActionListener(this);
						btnCancelBatch.setActionCommand("Close Batch");
						btnCancelBatch.setFont(FncGlobal.sizeControls);
					}
				}
			}
		}

		displayPVProoflistByBatch(modelPVBatch, rowHeaderPVBatch, co_id, pv_from, pv_to, date_from, date_to, req_type, payee, availer, prep_by, pv_status, payment_type);
	}

	private void displayPVProoflistByBatch(DefaultTableModel modelmain, JList rowHeader, String co_id, Integer pv_from, Integer pv_to,
			java.util.Date date_from, java.util.Date date_to, String req_type, String payee, String availer, String prep_by,
			String pv_status, String payment_type) {	
		FncTables.clearTable(modelmain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql =
				"select\n" + 
						"false,\n" +
						"a.pv_no,\n" + 
						"to_char(a.pv_date,'MM-dd-yyyy') as pv_date,\n" + 
						"f.amt_payable,\n" + 
						"trim(d.entity_name) as payee,\n" + 
						"trim(e.entity_name) as availer,\n" + 
						"(case when a.pay_type_id = 'A' then 'Cash' else 'Check' end) as pmt_tpye,\n" + 
						"g.status_desc,\n" + 
						"'/' || i.proj_alias as proj_alias, c.rplf_type_desc \n" +
						"from rf_pv_header a\n" + 
						"left join rf_request_header b on a.pv_no = b.rplf_no and a.co_id = b.co_id\n" + 
						"left join mf_rplf_type c on b.rplf_type_id = c.rplf_type_id\n" + 
						"left join rf_entity d on a.entity_id1 = d.entity_id\n" + 
						"left join rf_entity e on a.entity_id2 = e.entity_id\n" + 
						"left join ( select distinct on (pv_no) pv_no, sum(tran_amt) as amt_payable, project_id, co_id from rf_pv_detail where status_id != 'I' and acct_id = '03-01-01-001' AND co_id = '"+co_id+"' group by pv_no, project_id, co_id ) f on a.pv_no = f.pv_no AND a.co_id = f.co_id\n" + 
						"left join mf_record_status g on a.status_id = g.status_id\n" + 
						"left join rf_cv_header h on a.cv_no = h.cv_no and a.co_id = h.co_id\n" + 
						"left join mf_project i on f.project_id = i.proj_id\n" +
						"where a.pv_no != ''\n" + 
						"and a.pv_date::date >= '"+date_from+"'\n" + 
						"and a.pv_date::date <= '"+date_to+"'\n" + 
						"and a.co_id = '"+co_id+"'\n" + 
						"and ( case when '"+req_type+"' = '' or '"+req_type+"' is null then a.pv_no is not null else b.rplf_type_id = '"+req_type+"'  end )\n" + 
						"and ( case when '"+payee+"' = '' or '"+payee+"' is null then a.pv_no is not null else a.entity_id1 = '"+payee+"' end )\n" + 
						"and ( case when '"+availer+"' = '' or '"+availer+"' is null then a.pv_no is not null else a.entity_id2 = '"+availer+"' end )\n" + 
						"and ( case when '"+prep_by+"' = '' or '"+prep_by+"' is null then a.pv_no is not null else a.created_by = '"+prep_by+"' end )\n" + 
						"and ( case when "+pv_from+" = 0 or "+pv_from+" is null then a.pv_no is not null else a.pv_no::int >= "+pv_from+" end )\n" + 
						"and ( case when "+pv_to+" = 0 or "+pv_to+" is null then a.pv_no is not null else a.pv_no::int <= "+pv_to+" end )\n" + 
						"and ( case when '"+payment_type+"' = '' then a.pv_no is not null else a.pay_type_id = '"+payment_type+"' end )\n" + 
						"--and ( case when '"+pv_status+"' = '' then a.pv_no is not null else a.status_id = '"+pv_status+"' end )\n" + 
						"and a.status_id = 'F'\n" +
						"order by a.pv_no";

		FncSystem.out("display PV Prooflist batch SQL", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelmain.addRow(db.getResult()[x]);
				listModel.addElement(modelmain.getRowCount());				
			}	
		}

		scrollPVBatch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPVBatch.getRowCount())));
		tblPVBatch.packAll();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("Show Batch")) {

			ArrayList<String> listPV = new ArrayList<String>();
			Double amount = 0.00;
			Double total = 0.00;
			String particular = "";
			String pv_no = "";
			String client = "";
			String pay_type = "";
			String status = "";
			String payee = "";
			String availer = "";
			String company_logo = sql_getCompanyLogo();	
			String project = "";
			String desc = "";
			String phase = ""; /*ADDED BY JED 2021-11-25 : INCLUDE PHASE ON PARTICULAR AS PER MAM ROSHEL*/
			String request_type = "";

			for(int x=0 ; x < modelPVBatch.getRowCount() ; x++) {
				Boolean isSelected = (Boolean) modelPVBatch.getValueAt(x, 0);
				if(isSelected) {				
					pv_no = (String) modelPVBatch.getValueAt(x, 1);
					listPV.add(String.format("%s", pv_no));
					amount = Double.parseDouble(modelPVBatch.getValueAt(x, 3).toString());
					payee = (String) modelPVBatch.getValueAt(x, 4);
					availer = (String) modelPVBatch.getValueAt(x, 5);
					pay_type = (String) modelPVBatch.getValueAt(x, 6);
					status = (String) modelPVBatch.getValueAt(x, 7);
					project = (String) modelPVBatch.getValueAt(x, 8);
					request_type = (String) modelPVBatch.getValueAt(x, 9);
					
					System.out.printf("value of co_id: %s%n", co_id);
					try{
						client = getClientName(pv_no, co_id);
					} catch (NullPointerException a) { 
						client = ""; 
					}
					try{
						phase = getPhase(pv_no, co_id);
					} catch (NullPointerException a) { 
						phase = ""; 
					}
					particular = particular + pv_no + "     -     " + formatter.format(amount) + "     -     " + client + " " + "(" + phase + ")" + "\n";
					total = total + amount;
				}			
			}

			if(listPV.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a row!", "Error", JOptionPane.ERROR_MESSAGE);
			}else {
				System.out.printf("Total pv:%s\n", total);
				System.out.printf("%s\n", particular);

				String pvlist = listPV.toString().replaceAll("\\[", "").replaceAll("\\]","");
				System.out.printf("The value of new pvlist: (%s)\n", pvlist);

				if(ifDOA(pvlist)) {
					desc = "ENTRY FEE DEED OF ASSIGNMENT" + "\n" +
							"JUDICIAL FORM FEE" + "\n" +
							"REGISTRATION FEE OF ASSIGNMENT" + "\n" +
							"RESEARCH FEE  ASSIGNMENT";
				}

				else if(ifSale(pvlist)) {
					desc = "ENTRY FEE FOR TRANSFER TCT  FROM CDC TO BUYER" + "\n" +
							"ISSUANCE OF TITLE" + "\n" +
							"JUDICIAL FORM FEE" + "\n" +
							"REGISTRATION FEE TRANSFER OF TCT" + "\n" +
							"RESEARCH FEE (LRF) TRANSFER OF TCT FROM CDC TO BUYER";
				}

				else if(ifMortgage(pvlist)) {
					desc = "ENTRY FEE MORTGAGE" + "\n" +
							"JUDICIAL FORM FEE" + "\n" +
							"LEGAL RESEARCH FEE OF MORTGAGE" + "\n" +
							"Registration of Mortgage";
				}

				else if(ifTransferTax(pvlist)) {
					desc = "TRANSFER TAX";
				}

				else {
					desc = getParticular(pvlist);
				}

				String criteria = "PV Prooflist By Batch";		
				String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("company", company);
				mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("p_prep_by", UserInfo.FullName);
				mapParameters.put("p_amount", total);	
				mapParameters.put("p_pay_type", pay_type);		
				mapParameters.put("p_status", status);	
				mapParameters.put("p_payee", payee);	
				mapParameters.put("p_availer", availer);	
				mapParameters.put("p_particulars", particular);	
				mapParameters.put("p_proj", project);	
				mapParameters.put("p_desc", desc);
				mapParameters.put("p_request_type", request_type);

				FncReport.generateReport("/Reports/rptPV_prooflist_batch.jasper", reportTitle, "", mapParameters);
			}
		}	

		if(arg0.getActionCommand().equals("Close Batch")) {

			PVprooflist.Dispose();
		}
	}

	private Boolean ifDOA(String pvlist) {

		Boolean x = false;

		String sql =
				"select\n" + 
						"*\n" + 
						"from rf_processing_cost\n" + 
						"where rplf_no in  ((select trim(regexp_split_to_table('"+pvlist+"', ','))))\n" + 
						"and pcostid_dl in ('109', '210', '105', '108')\n" + 
						"--and status_id = 'A'\n" +
						"and server_id is null \n"+
						"and co_id = '"+co_id+"'";

		FncSystem.out("pv list sql", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			x = true;
		}else {
			x = false;
		}

		return x;

	}

	private Boolean ifSale(String pvlist) {

		Boolean x = false;

		String sql =
				"select\n" + 
						"*\n" + 
						"from rf_transfer_cost\n" + 
						"where rplf_no in  ((select trim(regexp_split_to_table('"+pvlist+"', ','))))\n" + 
						"and tcostid_dl in ('008', '011', '169', '021')\n" + 
						"--and status_id = 'A'\n" +
						"and co_id = '"+co_id+"'";

		FncSystem.out("pv list sql", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			x = true;
		}else {
			x = false;
		}

		return x;

	}

	private Boolean ifMortgage(String pvlist) {

		Boolean x = false;

		String sql =
				"select\n" + 
						"*\n" + 
						"from rf_transfer_cost\n" + 
						"where rplf_no in  ((select trim(regexp_split_to_table('"+pvlist+"', ','))))\n" + 
						"and tcostid_dl in ('009', '013', '193')\n" + 
						"--and status_id = 'A'\n" +
						"and co_id = '"+co_id+"'";

		FncSystem.out("pv list sql", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			x = true;
		}else {
			x = false;
		}

		return x;

	}

	private Boolean ifTransferTax(String pvlist) {

		Boolean x = false;

		String sql =
				"select\n" + 
						"*\n" + 
						"from rf_transfer_cost\n" + 
						"where rplf_no in  ((select trim(regexp_split_to_table('"+pvlist+"', ','))))\n" + 
						"and tcostid_dl = '166'\n" + 
						"--and status_id = 'A'\n" +
						"and co_id = '"+co_id+"'";

		FncSystem.out("pv list sql", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			x = true;
		}else {
			x = false;
		}

		return x;

	}

	private String getClientName(String pv_no, String co_id) {

		String client = "";

		String sql =
				"select * from\n" + 
						"(\n" + 
						"	select \n" + 
						"	trim(b.entity_name)\n" + 
						"	from rf_processing_cost a\n" + 
						"	left join rf_entity b on a.entity_id = b.entity_id\n" + 
						"	where a.rplf_no = '"+pv_no+"'\n" +
						"	AND a.co_id = '"+co_id+"' \n"+
						"	and a.status_id = 'A' \n"+
						"   and exists (SELECT *"
						+ "				from rf_request_detail "
						+ "			 	where rplf_no = '"+pv_no+"'"
								+ " 	and co_id = '"+co_id+"' \n"+
						"				and tcost_pcost_rec_id = a.rec_id \n"+
						"				and status_id = 'A' \n"+		
						") \n"+
//						"	and a.server_id is null \n"+
						"	--and a.status_id = 'A'\n" + 
						"\n" + 
						"	union all\n" + 
						"\n" + 
						"	select \n" + 
						"	trim(b.entity_name)\n" + 
						"	from rf_transfer_cost a\n" + 
						"	left join rf_entity b on a.entity_id = b.entity_id\n" + 
						"	where a.rplf_no = '"+pv_no+"'\n" +
						"	and a.co_id = '"+co_id+"' \n"+
						"	and a.status_id = 'A' \n"+
						"   and exists (SELECT *"
						+ "				from rf_request_detail "
						+ "			 	where rplf_no = '"+pv_no+"'"
								+ " 	and co_id = '"+co_id+"' \n"+
						"				and tcost_pcost_rec_id = a.rec_id \n"+
						"				and status_id = 'A' \n"+
//						"	and a.server_id is null \n"+
						"	--and a.status_id = 'A'\n" + 
						"))a\n" + 
						"limit 1";

		FncSystem.out("getClientName!!", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			client = db.getResult()[0][0].toString();
		}else {
			client = "";
		}
		return client;
	}

	private String getPhase(String pv_no, String co_id) {/*ADDED BY JED 2021-11-25 : INCLUDE PHASE ON PARTICULAR AS PER MAM ROSHEL*/

		String phase = "";

		String sql =
				"select * from\n" + 
						"(\n" + 
						"	select \n" + 
						"	c.proj_alias || b.phase\n" + 
						"	from rf_processing_cost a\n" + 
						"	left join mf_unit_info b on a.pbl_id = b.pbl_id and a.projcode = b.proj_id\n" + 
						"	left join mf_project c on a.projcode = c.proj_id\n" + 
						"	where a.rplf_no = '"+pv_no+"'\n" +
						"	and a.co_id = '"+co_id+"' \n"+
						"	--and a.status_id = 'A'\n" + 
						"\n" + 
						"	union all\n" + 
						"\n" + 
						"	select \n" + 
						"	c.proj_alias || b.phase\n" + 
						"	from rf_transfer_cost a\n" + 
						"	left join mf_unit_info b on a.pbl_id = b.pbl_id and a.projcode = b.proj_id\n" + 
						"	left join mf_project c on a.projcode = c.proj_id\n" + 
						"	where a.rplf_no = '"+pv_no+"'\n" +
						"   and a.co_id = '"+co_id+"' \n"+
						"	--and a.status_id = 'A'\n" + 
						")a\n" + 
						"limit 1";

		FncSystem.out("getPhase!!", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			phase = db.getResult()[0][0].toString();
		}else {
			phase = "";
		}
		return phase;
	}

	private String getParticular(String pvlist) {

		String particular = "";

		String sql =
				"select * from (\n" + 
						"					select\n" + 
						"					b.pcostdtl_desc\n" + 
						"					from rf_processing_cost a\n" + 
						"					left join (select * from mf_processing_cost_dl where status_id = 'A') b on a.pcostid_dl = b.pcostdtl_id \n" + 
						"					where rplf_no in  ((select trim(regexp_split_to_table('"+pvlist+"', ','))))\n" + 
						"					--and a.status_id = 'A'\n" + 
						"					and a.co_id = '"+co_id+"'\n" +
//						"					and a.server_id is null \n"+
						"					and b.pcostdtl_desc is not null\n" + 
						"					union all\n" + 
						"\n" + 
						"					select\n" + 
						"					b.tcostdtl_desc\n" + 
						"					from rf_transfer_cost a\n" + 
						"					left join (select * from mf_transfer_cost_dl where status_id = 'A') b on a.tcostid_dl = b.tcostdtl_id \n" + 
						"					where rplf_no in  ((select trim(regexp_split_to_table('"+pvlist+"', ','))))\n" + 
						"					--and a.status_id = 'A'\n" + 
//						"					and a.server_id is null \n"+
						"					and a.co_id = '"+co_id+"' and b.tcostdtl_desc is not null\n" + 
						"					)A limit 1";

		FncSystem.out("getParticulars!!", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()) {
			particular = db.getResult()[0][0].toString();
		}else {
			particular = "";
		}
		return particular;
	}
	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}
}
