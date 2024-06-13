package Dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import com.linuxense.javadbf.DBFException;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncExport;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import interfaces._GUI;
import tablemodel.model_hdmf_FirstFiling;

public class FirstFilingReports extends JPanel implements _GUI {

	private static final long serialVersionUID = 2268194130719050072L;

	private String strCoID;
	private String strCO;
	private String strProjID;
	private String strProj;
	private String strPhase;
	private String strUserName;
	private String strCircular;
	private String strBatch;

	private Boolean blnTag;
	private Date dteDate; 
	private model_hdmf_FirstFiling modelFF; 

	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);

	public void setCompanyID(String co_id) {
		this.strCoID = co_id; 
	}

	public void setCompany(String company) {
		this.strCO = company;  
	}

	public void setProjectID(String project_id) {
		this.strProjID = project_id;  
	}

	public void setProject(String project) {
		this.strProj = project; 
	}

	public void setPhase(String phase) {
		this.strPhase = phase;
	}

	public void setTag(Boolean tag) {
		this.blnTag = tag;
	}

	public void setDate(Date date) {
		this.dteDate = date;
	}

	public void setModel(model_hdmf_FirstFiling model) {
		this.modelFF = model; 
	}
	public void setBatch(String batch) {
		this.strBatch = batch;  
	}

	public FirstFilingReports() {
		initGUI(); 
	}

	public FirstFilingReports(LayoutManager layout) {
		super(layout);

	}

	public FirstFilingReports(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public FirstFilingReports(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public void initGUI() {
		String[] strRep = new String[] {
				"Dev Entry", 
				"Dev Entry Transmittal", 
				"Housing Loan Fees",
				"Notice of Emp. Verification"
		}; 

		String[] strAction = new String[] {
				"Create", 
				"Preview", 
				"Preview",
				"Preview"
		}; 

		JLabel[] lblRep = new JLabel[4]; 

		this.setLayout(new BorderLayout(5, 5));
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panList = new JXPanel(new GridLayout(4, 1, 5, 5)); 
			panMain.add(panList, BorderLayout.CENTER); 
			{
				for (int x=0; x<strRep.length; x++) {
					JXPanel panDiv = new JXPanel(new BorderLayout(10, 5)); 
					panList.add(panDiv); 
					{
						lblRep[x] = new JLabel(strRep[x]); 
						panDiv.add(lblRep[x], BorderLayout.CENTER);
						lblRep[x].setHorizontalAlignment(JLabel.TRAILING);
						lblRep[x].setFont(font11);

						JButton btnAction = new JButton(strAction[x]); 
						panDiv.add(btnAction, BorderLayout.LINE_END);
						btnAction.setActionCommand(strAction[x]);
						btnAction.setPreferredSize(new Dimension(100, 0));
						btnAction.setName(strRep[x]);
						btnAction.setFont(font11);
						btnAction.addActionListener(actionListener);
					}
				}	
			}
		}
	}

	ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			System.out.println("((JButton) e.getSource()).getName(): "+((JButton) e.getSource()).getName());
			System.out.println("((JButton) e.getSource()).getName(): "+((JButton) e.getSource()).getActionCommand());

			String strName = ((JButton) e.getSource()).getName(); 

			switch (strName) {
			case "Dev Entry":
				if (JOptionPane.showConfirmDialog(null, "Create Dev Entry?", "Confirmation", 
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {

					String title = "Select Circular";
					//						String[] circular = {"310/349", "312", "379", "403/349"};
					String[] circular = {"396/349", "312", "379", "403/349"};
					String[] options = {"OK", "CANCEL"};

					final JComboBox<String> combo = new JComboBox<>(circular);

					int selection = JOptionPane.showOptionDialog(null, combo, title,
							JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

					if (selection > 0) {
						System.out.println("selection is: " + options[selection]);
					}

					strCircular = combo.getSelectedItem().toString();

					CreateDevEntry();	
				}

				break;
			case "Dev Entry Transmittal":
				GenerateForFirstFilingTransmittal(); 
				break;
			case "Housing Loan Fees":
				LoanFeesPreview(); 
				break;
			case "Notice of Emp. Verification":
				EmpVerificationPreview(); 
				TransmittalPreview();
				break;
			default:

			}
		}
	};

	private void CreateDevEntry() {
		String strDir = FncGlobal.OpenDir("Folder");

		try {
			createADDRESSB(strDir);
			createADDRESSA(strDir);
			createADDRESSE(strDir);
			createAPMASTER(strDir);		
			createCHAR_REF(strDir);			
			createDOA_DATA(strDir);
			createINSURE(strDir);
		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createADDRESSA(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "UNIT_NO", "BLDG_NAME", "STREET", "VILLAGE", "TOWN_CITY", "STATE", "COUNTRY", "ZIPCODE"};

		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer numeric_columns [] = {1};

		FncExport.createADDRESSA_DBF_FromModelWithQuery_FirstFiling(col_names, char_columns, numeric_columns, modelFF, "ADDRESSA.DBF", strDir);
	}

	private void createADDRESSB(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "TELNO", "HOUSE_NO", "STREET", "SUBD", "BRGY_VILL", 
				"TOWN_CITY", "PROVINCE", "REGION", "ZIPCODE"};

		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
		Integer numeric_columns [] = {1};

		FncExport.createADDRESSB_DBF_FromModelWithQuery_FirstFiling(col_names, char_columns, numeric_columns, modelFF, "ADDRESSB.DBF", strDir);

	}

	private void createADDRESSE(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "EYERID", "BUS_NAME", "TELNO", "RM_FLR", "BLDG_NAME", "STREET", "BRGY_VILL", "TOWN_CITY", "PROVINCE", "REGION", "ZIPCODE"};

		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		Integer numeric_columns [] = {1};

		FncExport.createADDRESSE_DBF_FromModelWithQuery_FirstFiling(col_names, char_columns, numeric_columns, modelFF, "ADDRESSE.DBF", strDir);
	}

	private void createAPMASTER(String strDir) throws DBFException, IOException{
		String col_names [] = {"DEVCODE", "PROJCODE", "APPLICNO", "APPLICDATE", "PE", "GUIDELINE", "SHLF" , "PROG_CD", "LNAME", "FNAME", 
				"MID", "EXTNAME", "PURP_CD", "PAYMODE", "AMTAPPLIED", "TERM", "REGHOLDER", "ISMORT", "LANDAREA", 
				"EXIST_STRY", "PROP_STRY", "EXIST_FLR", "PROP_FLR", "TCTNO", "TAXDECLOT", "TAXDECBLDG", "LOT_UNIT", 
				"BLK_BLDG", 
				"SURVEY_NO", 
				"HOUSEAGE", "HDMFID", "BDAY", "GENDER", "STATUS", "OTHER", "EMAILADDR", "OWNERSHIP", "RENT", 
				"YRSTAY", "SSS_GSISID", "TIN", "CELLPHONE", "BEEPERNO","NATURE_BUS", "POSITION", "YREMP_BUS", "FILIPINO", 
		"DEPENDENTS"};

		Integer char_columns [] = {2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 23, 24, 25, 26, 27, 28, 30, 32, 33, 34, 35, 36, 39, 40, 41, 42, 43, 44};
		Integer numeric_columns [] = {0, 1, 14, 15, 18, 19, 20, 21, 22, 29, 37, 38, 45, 47};
		Integer bool_columns [] = {4, 17, 46};
		Integer date_columns [] = {3, 31};

		FncExport.createDBF_FromModelWithQuery_FirstFiling(col_names, char_columns, bool_columns, date_columns, numeric_columns, modelFF, "APMASTER.DBF", strDir, strCircular);

	}

	private void createCHAR_REF(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "SEQ", "NAME", "ADDRESS", "TELNO"};

		Integer char_columns [] = {0, 3, 4, 5};
		Integer numeric_columns [] = {1, 2};

		FncExport.createCHAR_REF_DBF_FromModelWithQuery_FirstFiling(col_names, char_columns, numeric_columns, modelFF, "CHAR_REF.DBF", strDir);
	}

	private void createDOA_DATA(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "SEL_DATE", "SEL_DOCNO", "SEL_PGNO", "SEL_BOOKNO", "SEL_SERIES", "NOTARY_PUB", "ADA_DATE"};

		Integer char_columns [] = {0, 2, 3, 4, 5, 6};
		Integer date_columns [] = {1, 7};

		FncExport.createDOADATA_DBF_FromModelWithQuery_FirstFiling(col_names, char_columns, date_columns, modelFF, "DOADATA.DBF", strDir);
	}

	private void createINSURE(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "GBCODE", "GBTYPE", "REFERNO", "REFDATE"};

		Integer char_columns [] = {0, 2, 3};
		Integer numeric_columns [] = {1};
		Integer date_columns [] = {4};

		FncExport.createINSURE_DBF_FromModelWithQuery_FirstFiling(col_names, char_columns, date_columns, numeric_columns, modelFF, "INSURE.DBF", strDir);
	}

	private void GenerateForFirstFilingTransmittal() {	
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdfTo.format(dteDate);

		String strTmp = "";

		pgUpdate del = new pgUpdate();
		strTmp = "delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'";
		del.executeUpdate(strTmp, false);
		del.commit();

		System.out.println("");
		System.out.println(strTmp);

		for (int x = 0; x < modelFF.getRowCount(); x++) {
			pgUpdate ins = new pgUpdate();
			strTmp = "insert into tmp_hdmf (client_name, emp_code) values ('"+modelFF.getValueAt(x, 0)+"', '"+UserInfo.EmployeeCode+"')";
			ins.executeUpdate(strTmp, false);
			ins.commit();

			System.out.println("");
			System.out.println(strTmp);
		}
		String username = FncGlobal.GetString("Select a.entity_name from rf_entity a "
				+ "inner join em_employee b on a.entity_id = b.entity_id "
				+ "where b.emp_code = '"+UserInfo.EmployeeCode+"'");

		String[] circular = {"396/349", "312", "379", "403/349"};

		final JComboBox<String> combo = new JComboBox<>(circular);

		String[] options = {"OK", "CANCEL"};

		String title = "Select Circular";
		int selection = JOptionPane.showOptionDialog(null, combo, title,
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (selection > 0) {
			System.out.println("selection is: " + options[selection]);
		}

		strCircular = combo.getSelectedItem().toString();
		if (strCircular=="") {
			System.out.println("Circular: " + strCircular);
		}

		strCircular = combo.getSelectedItem().toString();
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", strCO);
		mapParameters.put("02_AsOfDate", strDate);
		mapParameters.put("03_CoID", strCoID);
		mapParameters.put("04_ProID", strProjID);
		mapParameters.put("05_Phase", strPhase);
		mapParameters.put("06_Project", strProj);
		mapParameters.put("07_User",  username);
		mapParameters.put("08_Tag", blnTag);
		mapParameters.put("09_UserID", UserInfo.EmployeeCode);
		mapParameters.put("10_Circular", strCircular);
		mapParameters.put("11_BatchNo", strBatch);
		FncReport.generateReport("/Reports/rpt_hdmf_transmittal_firstfiling.jasper", "DEV Entry Transmittal", "", mapParameters);
	}

	private void EmpVerificationPreview() {

		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
		dbExec.commit(); 

		dbExec = new pgUpdate();
		for (int x=0; x<modelFF.getRowCount(); x++) {
			if ((Boolean) modelFF.getValueAt(x, 1)) {
				dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelFF.getValueAt(x, 0).toString()+"', '"+UserInfo.EmployeeCode+"')", false);				
			}
		}
		dbExec.commit();

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		System.out.println("batch_no:::" + strBatch);
		System.out.println("prepared_by:::" + FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
				+ UserInfo.EmployeeCode + "'"));
		mapParameters.put("prepared_by",
				FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
						+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
						+ UserInfo.EmployeeCode + "'"));
		mapParameters.put("batch_no", strBatch);	
		FncReport.generateReport("/Reports/rpt_NoticeofEmploymentVerification.jasper", "Notice of Employment Verification ", "", mapParameters);

	}
	private void TransmittalPreview(){
		//		pgUpdate dbExec = new pgUpdate();
		//		dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
		//		dbExec.commit(); 
		//		
		//		dbExec = new pgUpdate();
		//		for (int x=0; x<modelFF.getRowCount(); x++) {
		//			if ((Boolean) modelFF.getValueAt(x, 1)) {
		//				dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelFF.getValueAt(x, 0).toString()+"', '"+UserInfo.EmployeeCode+"')", false);				
		//			}
		//		}
		//		dbExec.commit();
		pgSelect dbExec = new pgSelect();
		System.out.println("02_BatchNo:" + strBatch);
		String strSQL = "select distinct region::varchar(155) \n" +
				"from view_notice_transmittal_verification('"+strBatch+"') \n" +
				"where entity_id in (select y.entity_id \n" +
				"from tmp_hdmf x \n" +
				"inner join rf_entity y on x.client_name = y.entity_name \n" +
				"where x.emp_code = '"+UserInfo.EmployeeCode+"')";

		System.out.println("");
		System.out.println("strSQL: " + strSQL);
		dbExec.select(strSQL);

		System.out.println("");
		System.out.println("I went here!");
		if (dbExec.getRowCount() > 0) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", strCO);
				mapParameters.put("02_BatchNo", strBatch);
				mapParameters.put("03_UserName", GetName(UserInfo.EmployeeCode));
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", "Notice on Filing at HDMF");
				mapParameters.put("06_region", dbExec.getResult()[x][0]);

				System.out.println("co_i:" + strCO);
				System.out.println("co_i:" + strCO);
				System.out.println("02_BatchNo:" + strBatch);
				System.out.println("03_UserName:" + GetName(UserInfo.EmployeeCode));
				System.out.println("05_NoticeType:" + "Notice on Filing at HDMF");


				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal_v4.jasper",
						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[x][0], "", mapParameters);		
			}
		}


	}	
	private void LoanFeesPreview() {
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
		dbExec.commit(); 

		dbExec = new pgUpdate();
		for (int x=0; x<modelFF.getRowCount(); x++) {
			if ((Boolean) modelFF.getValueAt(x, 1)) {
				dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelFF.getValueAt(x, 0).toString()+"', '"+UserInfo.EmployeeCode+"')", false);				
			}
		}
		dbExec.commit();

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("emp_code", UserInfo.EmployeeCode);
		mapParameters.put("proj_id", strProjID);
		System.out.println("proj_idddd" + strProjID);
		FncReport.generateReport("/Reports/rpt_hdmf_housing_loan_fees.jasper", "Housing Loan Fees Remittance Form", "", mapParameters);
	}
	public static String GetName(String emp_code) {
		String entityid = "";

		String SQL = "SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '" + emp_code + "'";

		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			entityid = (String) sqlExec.getResult()[0][0];
		} else {
			entityid = "";
		}

		return entityid;
	}
}
