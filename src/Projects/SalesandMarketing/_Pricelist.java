package Projects.SalesandMarketing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelPricelist_complete;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components._JTableMain;

public class _Pricelist {

	public static void displayMonitoringUnits(modelPricelist_complete modelMonitoringUnits, JList rowHeader, String proj_id, String phase) {
		FncTables.clearTable(modelMonitoringUnits);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String SQL = 
				"SELECT " +
						"phase, \n" +
						"block, \n" +
						"lot, \n" + 
						"lotarea, \n" + 
						"model_alias, \n" + 
						"total_factor, \n" + 
						"house_apv, \n" + 
						"appraise_value_apv, \n" + 
						"lot_apv, \n" + 
						"total_apv, \n" + 
						"house_sp, \n" + 
						"lot_sp, \n" + 
						"other_factor, \n" + 
						"tcp, \n" + 
						"disc, \n" + 
						"tcp_discounted, \n" + 
						"loanable_amt_90, \n" + 
						"tcp_discounted_roundoff, \n" + 
						"loanable_amt_final, \n" + 
						"perc_la_to_apv, \n" + 
						"perc_la_to_tcp, \n" + 
						"equity, \n" + 
						"mri, \n" + 
						"fi, \n" + 
						"filing_fee_release, \n" + 
						"docs_stamps, \n" + 
						"inspection_fee, \n" + 
						"commit_fee, \n" + 
						"addl_hdmf_retention, \n" + 
						"filing_fee_filing, \n" + 
						"int_on_cts, \n" + 
						"upico, \n" + 
						"misc_fees, \n" + 
						"equity, \n" + 
						"tot_cash_outlay, \n" + 
						"months_cash_outlay_11mos, \n" + 
						"monthly_int_6pt5, \n" + 
						"monthly_mri_6pt5, \n" + 
						"monthly_fi_6pt5, \n" + 
						"monthly_addl_hdmf_premium_6pt5, \n" + 
						"monthly_total_6pt5, \n" + 
						"required_monthly_net_income_6pt5, \n" + 
						"monthly_int_7pt9, \n" + 
						"monthly_mri_7pt9, \n" + 
						"monthly_fi_7pt9, \n" + 
						"monthly_addl_hdmf_premium_7pt9, \n" + 
						"monthly_total_7pt9, \n" + 
						"required_monthly_net_income_7pt9 \n" + 
						"FROM rf_marketing_scheme_pricelist \n" + 
						"WHERE proj_id = '"+ proj_id +"' \n" +
						"and phase = '"+phase+"' \n" +
						"and status_id = 'A' \n" + 
						"ORDER BY TRIM(phase), block::int, lot::int ;";

		System.out.printf("SQL #1 displayMonitoringUnits: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelMonitoringUnits.addRow(db.getResult()[x]);
				listModel.addElement(modelMonitoringUnits.getRowCount());
			}
		}
	}

	public static void displayMonitoringUnits2(modelPricelist_complete modelMonitoringUnits, JList rowHeader, String proj_id, String phase) {
		FncTables.clearTable(modelMonitoringUnits);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String SQL = "SELECT a.unit_id as \"Unit ID\", TRIM(a.pbl_id) as \"PBL ID\", a.description as \"Description\", b.model_desc as \"Model\", a.vat as \"VAT\", a.lotarea as \"Lot Area\", a.floorarea as \"Floor Area\",\n" + 
				"	a.sellingprice as \"Selling Price\", a.date_opened as \"Date Opened\", a.house_constructed as \"House Constructed\"\n" + 
				"FROM mf_unit_info a\n" + 
				"LEFT JOIN mf_product_model b ON b.model_id = a.model_id\n" + 
				"LEFT JOIN mf_unit_pricing C ON c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
				"WHERE a.proj_id = '"+ proj_id +"' " +
				"and a.phase = '"+phase+"'" +
				"and a.status_id != 'D' \n" + 
				"AND a.date_opened IS NULL\n" + 
				"AND EXTRACT(YEAR FROM c.effectivity) = EXTRACT(YEAR FROM CURRENT_DATE)\n" + 
				"ORDER BY TRIM(a.pbl_id)::INT;";

		System.out.printf("SQL #1 displayMonitoringUnits: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelMonitoringUnits.addRow(db.getResult()[x]);
				listModel.addElement(modelMonitoringUnits.getRowCount());
			}
		}
	}

	public static Boolean saveUnits(_JTableMain tblAddUnit, String co_id, String proj_id, Boolean toUpdatePreviousVersion) {
		ArrayList<Object> listModelAlias = new ArrayList<Object>();
		ArrayList<Object> listPhase = new ArrayList<Object>();
		ArrayList<Object> listBlock = new ArrayList<Object>();
		ArrayList<Object> listLot = new ArrayList<Object>();
		ArrayList<BigDecimal> listLotArea = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listTotalContractPrice = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listHouseAPV = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listHouseSP = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listLotAPV = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listTotalLoanableAmount = new ArrayList<BigDecimal>();
		ArrayList<Object> listOthers = new ArrayList<Object>();

		for(int x=0; x < tblAddUnit.getRowCount(); x++){
			Boolean isSelected = (Boolean) tblAddUnit.getValueAt(x, 0);

			if(isSelected){
				Object model_alias = (String) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House Model"));
				Object phase = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Phase"));
				Object block = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Block"));
				Object lot = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot"));
				BigDecimal lot_area = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot Area in Sqm."));
				BigDecimal total_contract_price = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));

				listModelAlias.add(String.format("'%s'", model_alias));
				listPhase.add(String.format("'%s'", phase));
				listBlock.add(String.format("'%s'", block));
				listLot.add(String.format("'%s'", lot));
				listLotArea.add(lot_area);
				listTotalContractPrice.add(total_contract_price);


				BigDecimal house_apv = null;
				try {
					house_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House APV"));
				} catch (IllegalArgumentException e) { }
				listHouseAPV.add(house_apv);

				BigDecimal house_sp = null;
				try {
					house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House SP"));
					if(house_sp.compareTo(new BigDecimal(0)) == 0){
						house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot SP"));
					}
				} catch (IllegalArgumentException e) {
					house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot SP"));
				}
				listHouseSP.add(house_sp);

				BigDecimal lot_apv = null;
				try {
					lot_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot APV"));
				} catch (Exception e1) { }
				listLotAPV.add(lot_apv);

				BigDecimal total_loanable_amount = null;
				try {
					total_loanable_amount = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Loanable Amount"));
				} catch (IllegalArgumentException e) {
					total_loanable_amount = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Loanable"));
				}
				listTotalLoanableAmount.add(total_loanable_amount);

				String other_details = getOtherDetails(tblAddUnit, x);
				listOthers.add(String.format("[%s]", other_details));
			}
		}

		String model_alias = listModelAlias.toString().replaceAll("\\[|\\]", "");
		String phase = listPhase.toString().replaceAll("\\[|\\]", "");
		String block = listBlock.toString().replaceAll("\\[|\\]", "");
		String lot = listLot.toString().replaceAll("\\[|\\]", "");
		String lot_area = listLotArea.toString().replaceAll("\\[|\\]", "");
		String total_contract_price = listTotalContractPrice.toString().replaceAll("\\[|\\]", "");
		String house_apv = listHouseAPV.toString().replaceAll("\\[|\\]", "");
		String house_sp = listHouseSP.toString().replaceAll("\\[|\\]", "");
		String lot_apv = listLotAPV.toString().replaceAll("\\[|\\]", "");
		String total_loanable_amount = listTotalLoanableAmount.toString().replaceAll("\\[|\\]", "");
		String other_details = listOthers.toString()/*.replaceAll("\\[|\\]", "")*/;

		String SQL = "SELECT sp_save_pricelist('"+ co_id +"', '"+ proj_id +"',\n" + 
				"   ARRAY["+ model_alias +"]::VARCHAR[],\n" + 
				"   ARRAY["+ phase +"]::VARCHAR[],\n" + 
				"   ARRAY["+ block +"]::VARCHAR[],\n" + 
				"   ARRAY["+ lot +"]::VARCHAR[],\n" + 
				"   ARRAY["+ lot_area +"]::NUMERIC[],\n" + 
				"   ARRAY["+ total_contract_price +"]::NUMERIC[],\n" + 
				"   ARRAY["+ house_apv +"]::NUMERIC[],\n" + 
				"   ARRAY["+ house_sp +"]::NUMERIC[],\n" + 
				"   ARRAY["+ lot_apv +"]::NUMERIC[],\n" + 
				"   ARRAY["+ total_loanable_amount +"]::NUMERIC[],\n" + 
				"   ARRAY"+ other_details +"::NUMERIC[], \n" + 
				"   "+ toUpdatePreviousVersion +", '"+ UserInfo.EmployeeCode +"');";

		FncSystem.out("Pricelist: %s%n", SQL);
		pgSelect sdb = new pgSelect();
		sdb.select(SQL, "Save", true);
		if(sdb.isNotNull()){
			return (Boolean) sdb.getResult()[0][0];
		}else{
			return false;
		}
	}
	
	public static String getOtherDetails(_JTableMain tblAddUnit, Integer x) {
		ArrayList<BigDecimal> listOtherDetails = new ArrayList<BigDecimal>();

		BigDecimal total_factor = null; //1
		BigDecimal house_apv = null; //2
		BigDecimal appraise_value_apv = null; //3
		BigDecimal lot_apv = null; //4
		BigDecimal total_apv = null; //5
		BigDecimal house_sp = null; //6
		BigDecimal lot_sp = null; //7
		BigDecimal other_factor = null; //8
		BigDecimal TCP = null; //9
		BigDecimal disc = null; //10
		BigDecimal tcp_discounted = null; //11
		BigDecimal tcp_discounted_roundoff = null; //12
		BigDecimal loanable_amt_90 = null; //13
		BigDecimal loanable_amt_final = null; //14
		BigDecimal perc_LA_to_APV = null; //15
		BigDecimal perc_LA_to_TCP = null; //16
		BigDecimal equity = null; //17
		BigDecimal mri = null; //18
		BigDecimal fi = null; //19
		BigDecimal filing_fee_release = null; //20
		BigDecimal docs_stamps = null; //21
		BigDecimal inspection_fee = null; //22
		BigDecimal commit_fee = null; //23
		BigDecimal addl_hdmf_retention = null; //24
		BigDecimal filing_fee_filing = null; //25
		BigDecimal int_on_cts = null; //26
		BigDecimal upico = null; //27
		BigDecimal misc_fees = null; //28
		BigDecimal tot_cash_outlay = null; //29
		BigDecimal months_cash_outlay_11mos = null; //30
		BigDecimal monthly_int_6pt5 = null; //31
		BigDecimal monthly_mri_6pt5 = null; //32
		BigDecimal monthly_fi_6pt5 = null; //33
		BigDecimal monthly_addl_hdmf_premium_6pt5 = null; //34
		BigDecimal monthly_total_6pt5 = null; //35
		BigDecimal required_monthly_net_income_6pt5 = null; //36
		BigDecimal monthly_int_7pt9 = null; //37
		BigDecimal monthly_mri_7pt9 = null; //38
		BigDecimal monthly_fi_7pt9 = null; //39
		BigDecimal monthly_addl_hdmf_premium_7pt9 = null; //40
		BigDecimal monthly_total_7pt9 = null; //41
		BigDecimal required_monthly_net_income_7pt9 = null; //42

		try { total_factor = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total"));} catch (NullPointerException e) { }
		try { house_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House APV"));} catch (NullPointerException e) { }
		try { appraise_value_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Appraise Value Per Sqm"));} catch (NullPointerException e) { }
		try { lot_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot APV"));} catch (NullPointerException e) { }
		try { total_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Unit APV"));} catch (NullPointerException e) { }
		try { house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House SP"));} catch (NullPointerException e) { }
		try { lot_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot SP"));} catch (NullPointerException e) { }
		try { other_factor = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("OTHER FACTOR"));} catch (NullPointerException e) { }
		try { TCP = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));} catch (NullPointerException e) { }
		try { disc = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discount"));} catch (NullPointerException e) { }
		try { tcp_discounted = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));} catch (NullPointerException e) { }
		try { tcp_discounted_roundoff = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));} catch (NullPointerException e) { }
		try { loanable_amt_90 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("90%Loanable"));} catch (NullPointerException e) { }
		try { loanable_amt_final = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Loanable Amount"));} catch (NullPointerException e) { }
		try { perc_LA_to_APV = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("% of LA to APV"));} catch (NullPointerException e) { }
		try { perc_LA_to_TCP = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("%LA to TCP"));} catch (NullPointerException e) { }
		try { equity = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("EQUITY"));} catch (NullPointerException e) { }
		try { mri = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI"));} catch (NullPointerException e) { }
		try { fi = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI"));} catch (NullPointerException e) { }
		try { filing_fee_release = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Filing Fee (upon release)"));} catch (NullPointerException e) { }
		try { docs_stamps = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Doc. Stamps"));} catch (NullPointerException e) { }
		try { inspection_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Inspection Fee"));} catch (NullPointerException e) { }
		try { commit_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Commitment Fee"));} catch (NullPointerException e) { }
		try { addl_hdmf_retention = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Add'l HDMF Retention"));} catch (NullPointerException e) { }
		try { filing_fee_filing = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Filing Fee (upon filing)"));} catch (NullPointerException e) { }
		try { int_on_cts = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Interest on CTS 1st"));} catch (NullPointerException e) { }
		try { upico = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("upgrading of Pagibig Contribution (5mo)"));} catch (NullPointerException e) { }
		try { misc_fees = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Miscellaneous Fees"));} catch (NullPointerException e) { }
		try { tot_cash_outlay = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Cash Outlay (Equity + Misc. Fees)"));} catch (NullPointerException e) { }
		try { months_cash_outlay_11mos = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("11 Months Cash Outlay"));} catch (NullPointerException e) { }
		try { monthly_int_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date"));} catch (NullPointerException e) { }
		try { monthly_mri_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI 6.5%"));} catch (NullPointerException e) { } catch (IllegalArgumentException a) { columnNotExists("MRI 6.5%"); }
		try { monthly_fi_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI 6.5%"));} catch (NullPointerException e) { }
		try { monthly_addl_hdmf_premium_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Additional Pag-Ibig Premium 6.5%"));} catch (NullPointerException e) { }
		try { monthly_total_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date PLUS INSURANCES"));} catch (NullPointerException e) { }
		try { required_monthly_net_income_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Required Net Monthly Income 6.5%"));} catch (NullPointerException e) { }
		try { monthly_int_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 7.985% interest per annum Paid Within Due Date"));} catch (NullPointerException e) { }
		try { monthly_mri_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI 7.985%"));} catch (NullPointerException e) { }
		try { monthly_fi_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI 7.985%"));} catch (NullPointerException e) { }
		try { monthly_addl_hdmf_premium_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Additional Pag-Ibig Premium 7.985%"));} catch (NullPointerException e) { }
		try { monthly_total_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 7.985% interest per annum Paid Within Due Date PLUS INSURANCES"));} catch (NullPointerException e) { }
		try { required_monthly_net_income_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Required Net Monthly Income 7.985%"));} catch (NullPointerException e) { }

		listOtherDetails.add(total_factor);
		listOtherDetails.add(house_apv);
		listOtherDetails.add(appraise_value_apv);
		listOtherDetails.add(lot_apv);
		listOtherDetails.add(total_apv);
		listOtherDetails.add(house_sp);
		listOtherDetails.add(lot_sp);
		listOtherDetails.add(other_factor);
		listOtherDetails.add(TCP);
		listOtherDetails.add(disc);
		listOtherDetails.add(tcp_discounted);
		listOtherDetails.add(tcp_discounted_roundoff);
		listOtherDetails.add(loanable_amt_90);
		listOtherDetails.add(loanable_amt_final);
		listOtherDetails.add(perc_LA_to_APV);
		listOtherDetails.add(perc_LA_to_TCP);
		listOtherDetails.add(equity);
		listOtherDetails.add(mri);
		listOtherDetails.add(fi);
		listOtherDetails.add(filing_fee_release);
		listOtherDetails.add(docs_stamps);
		listOtherDetails.add(inspection_fee);
		listOtherDetails.add(commit_fee);
		listOtherDetails.add(addl_hdmf_retention);
		listOtherDetails.add(filing_fee_filing);
		listOtherDetails.add(int_on_cts);
		listOtherDetails.add(upico);
		listOtherDetails.add(misc_fees);
		listOtherDetails.add(tot_cash_outlay);
		listOtherDetails.add(months_cash_outlay_11mos);
		listOtherDetails.add(monthly_int_6pt5);
		listOtherDetails.add(monthly_mri_6pt5);
		listOtherDetails.add(monthly_fi_6pt5);
		listOtherDetails.add(monthly_addl_hdmf_premium_6pt5);
		listOtherDetails.add(monthly_total_6pt5);
		listOtherDetails.add(required_monthly_net_income_6pt5);
		listOtherDetails.add(monthly_int_7pt9);
		listOtherDetails.add(monthly_mri_7pt9);
		listOtherDetails.add(monthly_fi_7pt9);
		listOtherDetails.add(monthly_addl_hdmf_premium_7pt9);
		listOtherDetails.add(monthly_total_7pt9);
		listOtherDetails.add(required_monthly_net_income_7pt9);

		return listOtherDetails.toString().replaceAll("\\[|\\]", "");
	}

	public static void savePricelist(_JTableMain tblAddUnit, String proj_id, pgUpdate db) {

		for(int x=0; x < tblAddUnit.getRowCount(); x++){

			Boolean isSelected = (Boolean) tblAddUnit.getValueAt(x, 0);

			if(isSelected){

				Object phase = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Phase"));
				Object block = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Block"));
				Object lot = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot"));
				BigDecimal lot_area = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot Area in Sqm."));	
				Object model_alias = (String) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House Model"));		

				BigDecimal total_factor = null;
				BigDecimal house_apv = null;
				BigDecimal appraise_value_apv = null;
				BigDecimal lot_apv = null;
				BigDecimal total_apv = null;
				BigDecimal house_sp = null;
				BigDecimal lot_sp = null;
				BigDecimal other_factor = null;
				BigDecimal TCP = null;
				BigDecimal disc = null;
				BigDecimal tcp_discounted = null;
				BigDecimal tcp_discounted_roundoff = null;
				BigDecimal loanable_amt_90 = null;
				BigDecimal loanable_amt_final = null;
				BigDecimal perc_LA_to_APV = null;
				BigDecimal perc_LA_to_TCP = null;
				BigDecimal equity = null;
				BigDecimal mri = null;
				BigDecimal fi = null;
				BigDecimal filing_fee_release = null;
				BigDecimal docs_stamps = null;
				BigDecimal inspection_fee = null;
				BigDecimal commit_fee = null;
				BigDecimal addl_hdmf_retention = null;
				BigDecimal filing_fee_filing = null;
				BigDecimal int_on_cts = null;
				BigDecimal upico = null;
				BigDecimal misc_fees = null;
				BigDecimal tot_cash_outlay = null;
				BigDecimal months_cash_outlay_11mos = null;
				BigDecimal monthly_int_6pt5 = null;
				BigDecimal monthly_mri_6pt5 = null;
				BigDecimal monthly_fi_6pt5 = null;
				BigDecimal monthly_addl_hdmf_premium_6pt5 = null;
				BigDecimal monthly_total_6pt5 = null;
				BigDecimal required_monthly_net_income_6pt5 = null;
				BigDecimal monthly_int_7pt9 = null;
				BigDecimal monthly_mri_7pt9 = null;
				BigDecimal monthly_fi_7pt9 = null;
				BigDecimal monthly_addl_hdmf_premium_7pt9 = null;
				BigDecimal monthly_total_7pt9 = null;
				BigDecimal required_monthly_net_income_7pt9 = null;

				try { total_factor = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total"));} catch (NullPointerException e) { }
				try { house_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House APV"));} catch (NullPointerException e) { }
				try { appraise_value_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Appraise Value Per Sqm"));} catch (NullPointerException e) { }
				try { lot_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot APV"));} catch (NullPointerException e) { }
				try { total_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Unit APV"));} catch (NullPointerException e) { }
				try { house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House SP"));} catch (NullPointerException e) { }
				try { lot_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot SP"));} catch (NullPointerException e) { }
				try { other_factor = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("OTHER FACTOR"));} catch (NullPointerException e) { }
				try { TCP = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));} catch (NullPointerException e) { }
				try { disc = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discount"));} catch (NullPointerException e) { }
				try { tcp_discounted = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));} catch (NullPointerException e) { }
				try { tcp_discounted_roundoff = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));} catch (NullPointerException e) { }
				try { loanable_amt_90 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("90%Loanable"));} catch (NullPointerException e) { }
				try { loanable_amt_final = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Loanable Amount"));} catch (NullPointerException e) { }
				try { perc_LA_to_APV = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("% of LA to APV"));} catch (NullPointerException e) { }
				try { perc_LA_to_TCP = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("%LA to TCP"));} catch (NullPointerException e) { }
				try { equity = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("EQUITY"));} catch (NullPointerException e) { }
				try { mri = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI per year"));} catch (NullPointerException e) { }
				try { fi = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI per year"));} catch (NullPointerException e) { }
				try { filing_fee_release = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Filing Fee (upon release)"));} catch (NullPointerException e) { }
				try { docs_stamps = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Doc. Stamps"));} catch (NullPointerException e) { }
				try { inspection_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Inspection Fee"));} catch (NullPointerException e) { }
				try { commit_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Commitment Fee"));} catch (NullPointerException e) { }
				try { addl_hdmf_retention = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Add'l HDMF Retention"));} catch (NullPointerException e) { }
				try { filing_fee_filing = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Filing Fee (upon filing)"));} catch (NullPointerException e) { }
				try { int_on_cts = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Interest on CTS 1st"));} catch (NullPointerException e) { }
				try { upico = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("upgrading of Pagibig Contribution (5mo)"));} catch (NullPointerException e) { }
				try { misc_fees = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Miscellaneous Fees"));} catch (NullPointerException e) { }
				try { tot_cash_outlay = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Cash Outlay (Equity + Misc. Fees)"));} catch (NullPointerException e) { }
				try { months_cash_outlay_11mos = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("11 Months Cash Outlay"));} catch (NullPointerException e) { }
				try { monthly_int_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date"));} catch (NullPointerException e) { }
				try { monthly_mri_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI 6.5%"));} catch (NullPointerException e) { }
				try { monthly_fi_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI 6.5%"));} catch (NullPointerException e) { }
				try { monthly_addl_hdmf_premium_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Additional Pag-Ibig Premium 6.5%"));} catch (NullPointerException e) { }
				try { monthly_total_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date PLUS INSURANCES"));} catch (NullPointerException e) { }
				try { required_monthly_net_income_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Required Net Monthly Income 6.5%"));} catch (NullPointerException e) { }
				try { monthly_int_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 7.985% interest per annum Paid Within Due Date"));} catch (NullPointerException e) { }
				try { monthly_mri_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI 7.985%"));} catch (NullPointerException e) { }
				try { monthly_fi_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI 7.985%"));} catch (NullPointerException e) { }
				try { monthly_addl_hdmf_premium_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Additional Pag-Ibig Premium 7.985%"));} catch (NullPointerException e) { }
				try { monthly_total_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 7.985% interest per annum Paid Within Due Date PLUS INSURANCES"));} catch (NullPointerException e) { }
				try { required_monthly_net_income_7pt9 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Required Net Monthly Income 7.985%"));} catch (NullPointerException e) { }

				//save 
				String SQL = 
						"insert into rf_marketing_scheme_pricelist values ( \n" +
								"'"+proj_id+"', \n" +  		//proj_id
								"'"+phase+"', \n" +			//phase
								"'"+block+"', \n" +			//block
								"'"+lot+"', \n" +			//lot
								"'"+lot_area+"', \n" +		//lotarea
								"'"+model_alias+"', \n" +	//model_alias					
								""+total_factor+", \n" +	//total_factor
								""+house_apv+", \n" +		//house_apv
								""+appraise_value_apv+", \n" +	//appraise_value_apv
								""+lot_apv+", \n" +			//lot_apv
								""+total_apv+", \n" +		//total_apv	
								""+house_sp+", \n" +		//house_sp
								""+lot_sp+", \n" +			//lot_sp
								""+other_factor+", \n" +	//other_factor
								""+TCP+", \n" +				//tcp
								""+disc+", \n" +			//disc 
								""+tcp_discounted+", \n" +	//tcp_discounted
								""+tcp_discounted_roundoff+", \n" +		//tcp_discounted_roundoff
								""+loanable_amt_90+", \n" +				//loanable_amt_90
								""+loanable_amt_final+", \n" +			//loanable_amt_final
								""+perc_LA_to_APV+", \n" +				//perc_la_to_apv
								""+perc_LA_to_TCP+", \n" +				//perc_la_to_tcp
								""+equity+", \n" +			//equity					
								""+mri+", \n" +				//mri
								""+fi+", \n" +				//fi
								""+filing_fee_release+", \n" +			//filing_fee_release
								""+docs_stamps+", \n" +		//docs_stamps
								""+inspection_fee+", \n" +	//inspection_fee
								""+commit_fee+", \n" +		//commit_fee
								""+addl_hdmf_retention+", \n" +			//addl_hdmf_retention					
								""+filing_fee_filing+", \n" +			//filing_fee_filing
								""+int_on_cts+", \n" +		//int_on_cts
								""+upico+", \n" +			//upico					
								""+misc_fees+", \n" +		//misc_fees
								""+tot_cash_outlay+", \n" +				//tot_cash_outlay
								""+months_cash_outlay_11mos+", \n" +	//months_cash_outlay_11mos
								""+monthly_int_6pt5+", \n" +			//monthly_int_6pt5
								""+monthly_mri_6pt5+", \n" +			//monthly_mri_6pt5
								""+monthly_fi_6pt5+", \n" +				//monthly_fi_6pt5
								""+monthly_addl_hdmf_premium_6pt5+", \n" +			//monthly_addl_hdmf_premium_6pt5
								""+monthly_total_6pt5+", \n" +			//monthly_total_6pt5
								""+required_monthly_net_income_6pt5+", \n" +		//required_monthly_net_income_6pt5
								""+monthly_int_7pt9+", \n" +			//monthly_int_7pt9					
								""+monthly_mri_7pt9+", \n" +			//monthly_mri_7pt9
								""+monthly_fi_7pt9+", \n" +				//monthly_fi_7pt9
								""+monthly_addl_hdmf_premium_7pt9+", \n" +		 //monthly_addl_hdmf_premium_7pt9
								""+monthly_total_7pt9+", \n" +			//monthly_total_7pt9
								""+required_monthly_net_income_7pt9+", \n" +	//required_monthly_net_income_7pt9
								"'A', \n" +					//status_id
								"'"+UserInfo.EmployeeCode+"', \n" +				//created_by
								"'"+Calendar.getInstance().getTime()+"', \n" +	//date_created
								"'', \n" +					//edited_by
								"null," +
								""+sql_getNextVersionNo(proj_id, phase)+" \n" +					//date_edited					

					") \n";

				db.executeUpdate(SQL, false);				
			}
		}
	}

	public static Integer sql_getNextVersionNo(String proj_id, Object phase) {

		Integer nxt_version_no = 0;

		String SQL = 
				"select max(coalesce(version_no,0)) +1  from rf_marketing_scheme_pricelist " +
						"where proj_id = '"+proj_id+"' and phase = '"+phase+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {nxt_version_no = 1;}
			else {nxt_version_no = (Integer) db.getResult()[0][0]; }

		}else{
			nxt_version_no = 1;
		}

		return nxt_version_no;
	}

	public static ArrayList<String> listColumns() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("Phase");
		list.add("Block");
		list.add("Lot");
		list.add("Lot Area in Sqm.");
		list.add("House Model");
		list.add("Discounted Total Contract Price");
		list.add("House APV");
		list.add("House SP");
		list.add("Lot SP");
		list.add("Lot APV");
		list.add("Total Loanable Amount");

		list.add("");
		list.add("House APV");
		list.add("Appraise Value Per Sqm");
		list.add("Total Unit APV");
		list.add("House SP");
		list.add("Lot SP");
		list.add("OTHER FACTOR");
		list.add("Discounted Total Contract Price");
		list.add("Discount");
		list.add("Discounted Total Contract Price");
		list.add("Discounted Total Contract Price");
		list.add("90%Loanable");
		list.add("Total Loanable Amount");
		list.add("% of LA to APV");
		list.add("%LA to TCP");
		list.add("EQUITY");
		list.add("MRI");
		list.add("FI");
		list.add("Filing Fee (upon release)");
		list.add("Doc. Stamps");
		list.add("Inspection Fee");
		list.add("Commitment Fee");
		list.add("Add'l HDMF Retention");
		list.add("Filing Fee (upon filing)");
		list.add("Interest on CTS 1st");
		list.add("upgrading of Pagibig Contribution (5mo)");
		list.add("Miscellaneous Fees");
		list.add("Total Cash Outlay (Equity + Misc. Fees)");
		list.add("11 Months Cash Outlay");
		list.add("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date");
		list.add("MRI 6.5%");
		list.add("FI 6.5%");
		list.add("Additional Pag-Ibig Premium 6.5%");
		list.add("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date PLUS INSURANCES");
		list.add("Required Net Monthly Income 6.5%");
		list.add("30 yrs to pay @ 7.985% interest per annum Paid Within Due Date");
		list.add("MRI 7.985%");
		list.add("FI 7.985%");
		list.add("Additional Pag-Ibig Premium 7.985%");
		list.add("30 yrs to pay @ 7.985% interest per annum Paid Within Due Date PLUS INSURANCES");
		list.add("Required Net Monthly Income 7.985%");

		return list;
	}
	
	public static ArrayList<String> listColumns_TV() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("Phase");
		list.add("Block");
		list.add("Lot");
		list.add("Lot Area in Sqm.");
		list.add("House Model");
		list.add("House Color");  //TV - unique
		list.add("Discounted Total Contract Price");
		list.add("House APV");
		list.add("House SP");
		list.add("Lot SP");
		list.add("Lot APV");
		list.add("Total Loanable Amount");

		list.add("");
		list.add("House APV");
		list.add("Appraise Value Per Sqm");
		list.add("Total Unit APV");
		list.add("House SP");
		list.add("Lot SP");
		list.add("OTHER FACTOR");
		list.add("Discounted Total Contract Price");
		list.add("Discount");
		list.add("Discounted Total Contract Price");
		list.add("Discounted Total Contract Price");
		list.add("85% Loanable");
		list.add("Total Loanable Amount");
		list.add("% of LA to APV");
		list.add("%LA to TCP");
		list.add("EQUITY");
		list.add("MRI");
		list.add("FI");
		list.add("Filing Fee (upon release)");
		list.add("Doc. Stamps");
		//list.add("Inspection Fee");
		list.add("Commitment Fee (.5%)");
		list.add("Appraisal Fee");
		list.add("Add'l HDMF Retention");
		list.add("Filing Fee (upon filing)");
		//list.add("Interest on CTS 1st");
		list.add("Interim MRI (5 mos only)");
		//list.add("upgrading of Pagibig Contribution (5mo)");
		list.add("Commitment Fee (.5%)");
		list.add("Miscellaneous Fees");
		list.add("Total Cash Outlay (Equity + Misc. Fees)");
		//list.add("11 Months Cash Outlay");
		list.add("9 Months Cash Outlay");
		list.add("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date");
		//list.add("MRI 6.5%");
		//list.add("FI 6.5%");
		//list.add("Additional Pag-Ibig Premium 6.5%");
		list.add("MRI");
		list.add("FI");
		list.add("Additional Pag-Ibig Premium");
		list.add("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date PLUS INSURANCES");
		//list.add("Required Net Monthly Income 6.5%");
		/*list.add("Required Net Monthly Income");
		list.add("30 yrs to pay @ 7.985% interest per annum Paid Within Due Date");
		list.add("MRI 7.985%");
		list.add("FI 7.985%");
		list.add("Additional Pag-Ibig Premium 7.985%");
		list.add("30 yrs to pay @ 7.985% interest per annum Paid Within Due Date PLUS INSURANCES");
		list.add("Required Net Monthly Income 7.985%");*/

		return list;
	}

	public static Boolean hasFileHasCorrectColumns(_JTableMain table) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> missing = new ArrayList<String>();

		for(int x=0; x < table.getColumnCount(); x++){
			list.add(table.getColumnName(x));
		}

		for(String title : listColumns_TV()){
			if(!list.contains(title)){
				//System.out.printf("Header: %s%n", title);
				missing.add(String.format("• %s", title));
			}
		}

		System.out.println();
		if(missing.size() > 0){
			String message = missing.toString().replaceAll("\\[|\\]", "").replace(", ", "\n     ");
			//System.out.println(missing.toString());
			JOptionPane.showMessageDialog(FncGlobal.homeMDI, String.format("The following columns are missing. Please double-check the file.\n\n     %s", message), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			return true;
		}
	}

	public static void columnNotExists(String column) {
		JOptionPane.showMessageDialog(FncGlobal.homeMDI, String.format("%s column is missing. Please double-check the file.", column), "Save", JOptionPane.WARNING_MESSAGE);
	}


	//For Terraverde
	public static Boolean saveUnits_TV(_JTableMain tblAddUnit, String co_id, String proj_id, String pricelist_type, Boolean toUpdatePreviousVersion) {
		ArrayList<Object> listModelAlias = new ArrayList<Object>();
		ArrayList<Object> listPhase = new ArrayList<Object>();
		ArrayList<Object> listBlock = new ArrayList<Object>();
		ArrayList<Object> listLot = new ArrayList<Object>();
		ArrayList<Object> listHseColor = new ArrayList<Object>();
		ArrayList<BigDecimal> listLotArea = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listTotalContractPrice = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listHouseAPV = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listHouseSP = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listLotAPV = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listTotalLoanableAmount = new ArrayList<BigDecimal>();
		ArrayList<Object> listOthers = new ArrayList<Object>();
		
		pgSelect db = new pgSelect();

		for(int x=0; x < tblAddUnit.getRowCount(); x++){
			Boolean isSelected = (Boolean) tblAddUnit.getValueAt(x, 0);

			if(isSelected){
				Object model_alias = (String) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House Model"));
				Object phase = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Phase"));
				Object block = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Block"));
				Object lot = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot"));
				Object hse_color = tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House Color"));
				BigDecimal lot_area = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot Area in Sqm."));
				//BigDecimal total_contract_price = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));
				BigDecimal total_contract_price = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Net TCP"));
				
				listModelAlias.add(String.format("'%s'", model_alias));
				listPhase.add(String.format("'%s'", phase));
				listBlock.add(String.format("'%s'", block));
				listLot.add(String.format("'%s'", lot));
				listHseColor.add(String.format("'%s'", hse_color));
				listLotArea.add(lot_area);
				listTotalContractPrice.add(total_contract_price);


				BigDecimal house_apv = null;
				try {
					house_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House APV"));
				} catch (IllegalArgumentException e) { }
				listHouseAPV.add(house_apv);

				BigDecimal house_sp = null;
				try {
					house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House SP"));
					
					if(house_sp.compareTo(new BigDecimal(0)) == 0){
						house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot SP"));
					}
				} catch (IllegalArgumentException e) {
					house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot SP"));
				}
				listHouseSP.add(house_sp);

				BigDecimal lot_apv = null;
				try {
					lot_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot APV"));
				} catch (Exception e1) { }
				listLotAPV.add(lot_apv);

				BigDecimal total_loanable_amount = null;
				try {
					total_loanable_amount = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Loanable Amount"));
				} catch (IllegalArgumentException e) {
					total_loanable_amount = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Loanable"));
				}
				listTotalLoanableAmount.add(total_loanable_amount);

				String other_details = getOtherDetails_TV(tblAddUnit, x);
				listOthers.add(String.format("[%s]", other_details));
			}
		}

		String model_alias = listModelAlias.toString().replaceAll("\\[|\\]", "");
		String phase = listPhase.toString().replaceAll("\\[|\\]", "");
		String block = listBlock.toString().replaceAll("\\[|\\]", "");
		String lot = listLot.toString().replaceAll("\\[|\\]", "");
		String lot_area = listLotArea.toString().replaceAll("\\[|\\]", "");
		String total_contract_price = listTotalContractPrice.toString().replaceAll("\\[|\\]", "");
		String house_apv = listHouseAPV.toString().replaceAll("\\[|\\]", "");
		String house_sp = listHouseSP.toString().replaceAll("\\[|\\]", "");
		String lot_apv = listLotAPV.toString().replaceAll("\\[|\\]", "");
		String total_loanable_amount = listTotalLoanableAmount.toString().replaceAll("\\[|\\]", "");
		String other_details = listOthers.toString()/*.replaceAll("\\[|\\]", "")*/;
		String hse_color = listHseColor.toString().replaceAll("\\[|\\]", "");
		String SQL = "";
		
		if(pricelist_type.equals("JSystem")) {
			SQL = "SELECT sp_save_pricelist_TV('"+ co_id +"', '"+ proj_id +"',\n" + 
					"   ARRAY["+ model_alias +"]::VARCHAR[],\n" + 
					"   ARRAY["+ phase +"]::VARCHAR[],\n" + 
					"   ARRAY["+ block +"]::VARCHAR[],\n" + 
					"   ARRAY["+ lot +"]::VARCHAR[],\n" + 
					"   ARRAY["+ hse_color +"]::VARCHAR[],\n" + 
					"   ARRAY["+ lot_area +"]::NUMERIC[],\n" + 
					"   ARRAY["+ total_contract_price +"]::NUMERIC[],\n" + 
					"   ARRAY["+ house_apv +"]::NUMERIC[],\n" + 
					"   ARRAY["+ house_sp +"]::NUMERIC[],\n" + 
					"   ARRAY["+ lot_apv +"]::NUMERIC[],\n" + 
					"   ARRAY["+ total_loanable_amount +"]::NUMERIC[],\n" + 
					"   ARRAY"+ other_details +"::NUMERIC[], \n" + 
					"   "+ toUpdatePreviousVersion +", '"+ UserInfo.EmployeeCode +"');";
		}else {
			SQL = "SELECT sp_save_pricelist_itsreal('"+ co_id +"', '"+ proj_id +"',\n" + 
					"   ARRAY["+ model_alias +"]::VARCHAR[],\n" + 
					"   ARRAY["+ phase +"]::VARCHAR[],\n" + 
					"   ARRAY["+ block +"]::VARCHAR[],\n" + 
					"   ARRAY["+ lot +"]::VARCHAR[],\n" + 
					"   ARRAY["+ hse_color +"]::VARCHAR[],\n" + 
					"   ARRAY["+ lot_area +"]::NUMERIC[],\n" + 
					"   ARRAY["+ total_contract_price +"]::NUMERIC[],\n" + 
					"   ARRAY["+ house_apv +"]::NUMERIC[],\n" + 
					"   ARRAY["+ house_sp +"]::NUMERIC[],\n" + 
					"   ARRAY["+ lot_apv +"]::NUMERIC[],\n" + 
					"   ARRAY["+ total_loanable_amount +"]::NUMERIC[],\n" + 
					"   ARRAY"+ other_details +"::NUMERIC[], \n" + 
					"   "+ toUpdatePreviousVersion +", '"+ UserInfo.EmployeeCode +"');";
		}

		

		FncSystem.out("Pricelist: %s%n", SQL);
		pgSelect sdb = new pgSelect();
		sdb.select(SQL, "Save", true);
		if(sdb.isNotNull()){
			return (Boolean) sdb.getResult()[0][0];
		}else{
			return false;
		}
	}

	public static String getOtherDetails_TV(_JTableMain tblAddUnit, Integer x) {
		ArrayList<BigDecimal> listOtherDetails = new ArrayList<BigDecimal>();

		BigDecimal total_factor = null; 		//1
		BigDecimal house_apv = null; 			//2
		BigDecimal appraise_value_apv = null; 	//3
		BigDecimal lot_apv = null; 				//4
		BigDecimal total_apv = null; 			//5
		BigDecimal house_sp = null; 			//6
		BigDecimal lot_sp = null; 				//7
		BigDecimal other_factor = null; 		//8
		BigDecimal TCP = null; 					//9
		BigDecimal disc = null; 				//10
		BigDecimal tcp_discounted = null; 		//11
		BigDecimal tcp_discounted_roundoff = null; //12
		BigDecimal loanable_amt_85 = null; 		//13
		BigDecimal loanable_amt_final = null; 	//14
		BigDecimal perc_LA_to_APV = null; 		//15
		BigDecimal perc_LA_to_TCP = null; 		//16
		BigDecimal equity = null; 				//17
		BigDecimal mri = null; 					//18
		BigDecimal fi = null; 					//19
		BigDecimal filing_fee_release = null; 	//20
		BigDecimal docs_stamps = null; 			//21
		//BigDecimal inspection_fee = null; 
		BigDecimal commit_fee = null; 			//22
		BigDecimal appraisal_fee = null; 		//23
		BigDecimal addl_hdmf_retention = null; 	//24
		BigDecimal filing_fee_filing = null; 	//25
		BigDecimal int_on_cts = null; 			//26
		BigDecimal upico = null; 				//27
		BigDecimal misc_fees = null; 			//28
		BigDecimal tot_cash_outlay = null; 		//29
		BigDecimal months_cash_outlay_11mos = null; //30
		BigDecimal monthly_int_6pt5 = null; 	//31
		BigDecimal monthly_mri_6pt5 = null; 	//32
		BigDecimal monthly_fi_6pt5 = null; 		//33
		BigDecimal monthly_addl_hdmf_premium_6pt5 = null; //34
		BigDecimal monthly_total_6pt5 = null; 	//35
		BigDecimal required_monthly_net_income_6pt5 = null; //36
		BigDecimal interim_mri = null; 			//37
		
		//Additional Columns for Bank-Financing 
		BigDecimal bf_85_disc_tcp = null; 		//38
		BigDecimal bf_85_misc_fees = null; 		//39
		BigDecimal bf_tot_loan_amt = null; 		//40
		BigDecimal bf_equity_15 = null; 		//41
		BigDecimal bf_misc_fee_15 = null; 		//42
		BigDecimal bf_tot_cash_outlay = null; 	//43
		BigDecimal bf_9_mos_cash_outlay = null; //44
		BigDecimal bf_bank_ma = null; 			//45
		BigDecimal bf_mri_bank = null; 			//46
		BigDecimal bf_fi_bank = null; 			//47
		BigDecimal bf_addl_premium = null; 		//48
		BigDecimal bf_ma_bank_20_yrs = null; 	//49
		BigDecimal bf_gr_mo_income = null; 		//50
		BigDecimal bf_rem_charges = null; 		//51
		BigDecimal bf_title_transfer = null; 	//52
		BigDecimal bf_total = null; 			//53
		BigDecimal bf_diff_hdmf_misc_fee = null;//54
		
		//Additional Columns for Spot Cash 
		BigDecimal sp_vat = null; 				//55
		BigDecimal sp_disc_tcp = null; 			//56
		
		//Additional columns for TV 4-A
		BigDecimal vat = null; 					//57
		BigDecimal annotation_fee = null; 		//58
		BigDecimal addl_equity_tcp = null; 		//59
		BigDecimal sp_discount = null; 			//60		

		//Additional columns for TV 4
		BigDecimal addl_discount = null; 	//61
		BigDecimal net_tcp = null; 				//62
		BigDecimal rpt_amt = null;				//63
		BigDecimal floor_area = null;
		BigDecimal prime_area = null;
		
		try { prime_area = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Prime Area"));} catch (NullPointerException e) { }
		try { floor_area = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Floor Area"));} catch (NullPointerException e) { }
		try { total_factor = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total"));} catch (NullPointerException e) { }
		try { house_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House APV"));} catch (NullPointerException e) { }
		try { appraise_value_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Appraise Value Per Sqm"));} catch (NullPointerException e) { }
		try { lot_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot APV"));} catch (NullPointerException e) { }
		try { total_apv = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Unit APV"));} catch (NullPointerException e) { }
		try { house_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("House SP"));} catch (NullPointerException e) { }
		try { lot_sp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Lot SP"));} catch (NullPointerException e) { }
		try { other_factor = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("OTHER FACTOR"));} catch (NullPointerException e) { }
		try { TCP = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Contract Price"));} catch (NullPointerException e) { }
		try { disc = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discount"));} catch (NullPointerException e) { }
		try { tcp_discounted = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));} catch (NullPointerException e) { }
		try { tcp_discounted_roundoff = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price"));} catch (NullPointerException e) { }
		try { loanable_amt_85 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("85% Loanable"));} catch (NullPointerException e) { }
		try { loanable_amt_final = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Loanable Amount"));} catch (NullPointerException e) { }
		try { perc_LA_to_APV = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("% of LA to APV"));} catch (NullPointerException e) { }
		try { perc_LA_to_TCP = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("%LA to TCP"));} catch (NullPointerException e) { }
		try { equity = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("EQUITY"));} catch (NullPointerException e) { }
		try { mri = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI"));} catch (NullPointerException e) { }
		try { fi = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI"));} catch (NullPointerException e) { }
		try { filing_fee_release = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Filing Fee (upon release)"));} catch (NullPointerException e) { }
		try { docs_stamps = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Doc. Stamps"));} catch (NullPointerException e) { }
		//try { inspection_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Inspection Fee"));} catch (NullPointerException e) { }
		try { commit_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Commitment Fee (.5%)"));} catch (NullPointerException e) { }
		try { appraisal_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Appraisal Fee"));} catch (NullPointerException e) { }
		try { addl_hdmf_retention = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Add'l HDMF Retention"));} catch (NullPointerException e) { }
		try { filing_fee_filing = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Filing Fee (upon filing)"));} catch (NullPointerException e) { }
		//try { int_on_cts = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Interest on CTS 1st"));} catch (NullPointerException e) { }
		//try { upico = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("upgrading of Pagibig Contribution (5mo)"));} catch (NullPointerException e) { }
		try { misc_fees = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Miscellaneous Fees"));} catch (NullPointerException e) { }
		try { tot_cash_outlay = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Cash Outlay (Equity + Misc. Fees)"));} catch (NullPointerException e) { }
		try { months_cash_outlay_11mos = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("9 Months Cash Outlay"));} catch (NullPointerException e) { }
		try { monthly_int_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date"));} catch (NullPointerException e) { }
		try { monthly_mri_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI (mo.)"));} catch (NullPointerException e) { } catch (IllegalArgumentException a) { columnNotExists("MRI 6.5%"); }
		try { monthly_fi_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI (mo.)"));} catch (NullPointerException e) { }
		try { monthly_addl_hdmf_premium_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Additional Pag-Ibig Premium"));} catch (NullPointerException e) { }
		try { monthly_total_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("30 yrs to pay @ 6.5% interest per annum Paid Within Due Date PLUS INSURANCES"));} catch (NullPointerException e) { }
		try { required_monthly_net_income_6pt5 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Required Gross Monthly Income"));} catch (NullPointerException e) { }
		try { interim_mri = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Interim MRI (5 mos only)"));} catch (NullPointerException e) { }
		
		
		//Additional Columns for Bank-Financing 
		try { bf_85_disc_tcp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("85% of Discounted Total Contract Price"));} catch (NullPointerException e) { }
		try { bf_85_misc_fees = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("85% of Miscellaneous Fees"));} catch (NullPointerException e) { }
		try { bf_tot_loan_amt = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Loan Amount"));} catch (NullPointerException e) { }
		try { bf_equity_15 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Equity (15%)"));} catch (NullPointerException e) { }
		try { bf_misc_fee_15 = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Mis Fee (15%)"));} catch (NullPointerException e) { }
		try { bf_tot_cash_outlay = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total Cash Outlay (Equity + Misc. Fees) (Bank)"));} catch (NullPointerException e) { }
		try { bf_9_mos_cash_outlay = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("9 Months Cash Outlay (Bank)"));} catch (NullPointerException e) { }
		try { bf_bank_ma = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Bank Monthly Amortization (20 yrs at 7%)"));} catch (NullPointerException e) { }
		try { bf_mri_bank = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("MRI (bank)"));} catch (NullPointerException e) { }
		try { bf_fi_bank = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("FI (bank)"));} catch (NullPointerException e) { }
		try { bf_addl_premium = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Additional Premium"));} catch (NullPointerException e) { }
		try { bf_ma_bank_20_yrs = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("20 yrs to pay @ 7% interest per annum Paid Within Due Date PLUS INSURANCES"));} catch (NullPointerException e) { }
		try { bf_gr_mo_income = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Required Gross Monthly Income (Bank)"));} catch (NullPointerException e) { }
		try { bf_rem_charges = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("REM Charges (3.5% of loan amount)"));} catch (NullPointerException e) { }
		try { bf_title_transfer = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Title Transfer Cost (3.25% of Selling Price)"));} catch (NullPointerException e) { }
		try { bf_total = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Total BF"));} catch (NullPointerException e) { }
		try { bf_diff_hdmf_misc_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Difference with HDMF Mis Fee (Excess)"));} catch (NullPointerException e) { }
		
		//Additional Columns for Spot Cash 
		try { sp_vat = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Vat"));} catch (NullPointerException e) { }
		try { sp_disc_tcp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Discounted Total Contract Price w/ VAT"));} catch (NullPointerException e) { }
		
		//Additional columns for TV 4-A
		try { vat = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Vat"));} catch (NullPointerException e) { }
		try { annotation_fee = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Annotation Fee"));} catch (NullPointerException e) { }
		try { addl_equity_tcp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Additional Equity part of TCP"));} catch (NullPointerException e) { }
		try { sp_discount = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("SP Discount"));} catch (NullPointerException e) { }
		
		//Additional Columns for TV4
		try { addl_discount = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Additional Discount"));} catch (NullPointerException e) { }
		try { net_tcp = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("Net TCP"));} catch (NullPointerException e) { }
		try { rpt_amt = (BigDecimal) tblAddUnit.getValueAt(x, tblAddUnit.getColumnModel().getColumnIndex("RPT Amt"));} catch (NullPointerException e) { }
		
		listOtherDetails.add(total_factor);
		listOtherDetails.add(house_apv);
		listOtherDetails.add(appraise_value_apv);
		listOtherDetails.add(lot_apv);
		listOtherDetails.add(total_apv);
		listOtherDetails.add(house_sp);
		listOtherDetails.add(lot_sp);
		listOtherDetails.add(other_factor);
		listOtherDetails.add(TCP);
		listOtherDetails.add(disc);
		listOtherDetails.add(tcp_discounted);
		listOtherDetails.add(tcp_discounted_roundoff);
		listOtherDetails.add(loanable_amt_85);
		listOtherDetails.add(loanable_amt_final);
		listOtherDetails.add(perc_LA_to_APV);
		listOtherDetails.add(perc_LA_to_TCP);
		listOtherDetails.add(equity);
		listOtherDetails.add(mri);
		listOtherDetails.add(fi);
		listOtherDetails.add(filing_fee_release);
		listOtherDetails.add(docs_stamps);
		//listOtherDetails.add(inspection_fee);
		listOtherDetails.add(commit_fee);
		listOtherDetails.add(appraisal_fee);
		listOtherDetails.add(addl_hdmf_retention);
		listOtherDetails.add(filing_fee_filing);
		listOtherDetails.add(int_on_cts);
		listOtherDetails.add(upico);
		listOtherDetails.add(misc_fees);
		listOtherDetails.add(tot_cash_outlay);
		listOtherDetails.add(months_cash_outlay_11mos);
		listOtherDetails.add(monthly_int_6pt5);
		listOtherDetails.add(monthly_mri_6pt5);
		listOtherDetails.add(monthly_fi_6pt5);
		listOtherDetails.add(monthly_addl_hdmf_premium_6pt5);
		listOtherDetails.add(monthly_total_6pt5);
		listOtherDetails.add(required_monthly_net_income_6pt5);
		listOtherDetails.add(interim_mri);
		
		//Additional Columns for Bank-Financing 
		listOtherDetails.add(bf_85_disc_tcp);
		listOtherDetails.add(bf_85_misc_fees);
		listOtherDetails.add(bf_tot_loan_amt);
		listOtherDetails.add(bf_equity_15);
		listOtherDetails.add(bf_misc_fee_15);
		listOtherDetails.add(bf_tot_cash_outlay);
		listOtherDetails.add(bf_9_mos_cash_outlay);
		listOtherDetails.add(bf_bank_ma);
		listOtherDetails.add(bf_mri_bank);
		listOtherDetails.add(bf_fi_bank);
		listOtherDetails.add(bf_addl_premium);
		listOtherDetails.add(bf_ma_bank_20_yrs);
		listOtherDetails.add(bf_gr_mo_income);
		listOtherDetails.add(bf_rem_charges);
		listOtherDetails.add(bf_title_transfer);
		listOtherDetails.add(bf_total);
		listOtherDetails.add(bf_diff_hdmf_misc_fee);
		
		//Additional Columns for Spot Cash 
		listOtherDetails.add(sp_vat);
		listOtherDetails.add(sp_disc_tcp);
		
		//Additional columns for TV 4-A
		listOtherDetails.add(vat);
		listOtherDetails.add(annotation_fee);
		listOtherDetails.add(addl_equity_tcp);
		listOtherDetails.add(sp_discount);		
		
		//Additional Columns for TV4
		listOtherDetails.add(addl_discount);
		listOtherDetails.add(net_tcp);
		listOtherDetails.add(rpt_amt);
		listOtherDetails.add(floor_area);
		listOtherDetails.add(prime_area);
		
		return listOtherDetails.toString().replaceAll("\\[|\\]", "");
	}


}
