package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.awt.KeyboardFocusManager;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.modelMC_pv;
import tablemodel.modelRMC_dv;

public class TagRMCnumberPcost extends _JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6143015621825644470L;
	Dimension size =  new Dimension(550,450);
	static String title = "Tag Request MC number PCOST - Disbursement Voucher";

	private JPanel mainNorth;
	private JLabel lblCompany,lblDvno, lblDateFrom, lblDateTo;
	private JTextField txtCompany, txtDvno;
	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;
	private static _JLookup lookupCompany;
	private static _JLookup lookupPcostID;
	private JButton btnSave;
	private JButton btnPreview;
	private JButton btnCancel;
	private JScrollPane scrollMC;
	private static _JTableMain tblMC;
	private static JList rowHeadMC;
	private static modelRMC_dv modelRMC_cv;
	public static String co_id;
	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();


	public TagRMCnumberPcost(){
		super(title,true,true,false,false);
		initGui();
	}
	public TagRMCnumberPcost(String title){
		super(title);
		initGui();
	}
	public TagRMCnumberPcost(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconifiable){
		super(title, false, true, true, true);

	}

	public void initGui(){
		this.setTitle(title);
		this.setSize(size);
		setPreferredSize(size);

		{
			JPanel pnlmain = new JPanel(new BorderLayout(5,5));
			getContentPane().add(pnlmain);
			pnlmain.setBorder(new EmptyBorder(5,5,5,5));

			{
				mainNorth = new JPanel(new BorderLayout(5,5));
				pnlmain.add(mainNorth,BorderLayout.NORTH);

				{

					JPanel lblMainNorth = new JPanel(new GridLayout(5,1,3,3));
					lblMainNorth.setBorder(new EmptyBorder(5,5,5,5));
					mainNorth.add(lblMainNorth,BorderLayout.WEST);
					{
						lblCompany = new JLabel("Company:");
						lblMainNorth.add(lblCompany);

						lblDvno = new JLabel("PCOST ID:");
						lblMainNorth.add(lblDvno);
						
						lblDateFrom = new JLabel("Date From:");
						lblMainNorth.add(lblDateFrom);
	
						lblDateTo = new JLabel("Date To:");
						lblMainNorth.add(lblDateTo);	
						

					}
				}
				{
					JPanel mainNorthComponents = new JPanel(new GridLayout(5,1,3,3));
					mainNorthComponents.setBorder(new EmptyBorder(5,5,5,5));
					mainNorth.add(mainNorthComponents,BorderLayout.CENTER);
					{
						{
							lookupCompany = new _JLookup(null, "Company");
							mainNorthComponents.add(lookupCompany);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){


										txtCompany.setText(data[1].toString());
										txtCompany.setToolTipText(data[2].toString());
										lblDvno.setEnabled(true);
										lookupPcostID.setEnabled(true);
										lookupPcostID.setLookupSQL(getPcostid(lookupCompany.getValue()));
										manager.focusNextComponent();
									}else{
										txtCompany.setText("");
									}
								}

							});

						}
						{
							lookupPcostID = new _JLookup(null, "PCOST ID");
							mainNorthComponents.add(lookupPcostID);
							lookupPcostID.setReturnColumn(0);
							lookupPcostID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtDvno.setText(data[0].toString());
										manager.focusNextComponent();
										displayDV_pv(modelRMC_cv,co_id);
									}else{
										txtDvno.setText("");
									}
								}

							});
						}
					
					}
				}

				JPanel mainNorthComponents2 = new JPanel(new GridLayout(5,1,3,3));
				mainNorthComponents2.setBorder(new EmptyBorder(5,5,5,5));
				mainNorth.add(mainNorthComponents2,BorderLayout.EAST);
				{
					txtCompany = new JTextField();
					mainNorthComponents2.add(txtCompany);
					txtCompany.setEditable(false);
					txtCompany.setPreferredSize(new Dimension(350,0));
				}

				{
					txtDvno = new JTextField();
					mainNorthComponents2.add(txtDvno);
					txtDvno.setEditable(false);
					txtDvno.setPreferredSize(new Dimension(350,0));
				}
					
				{
					dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					mainNorthComponents2.add(dteDateFrom, BorderLayout.CENTER);		
					dteDateFrom.setDate(null);
					dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}	
				
				{
					dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					mainNorthComponents2.add(dateDateTo, BorderLayout.EAST);
					dateDateTo.setDate(null);
					dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}

			}
			{
				JPanel mainCenter = new JPanel(new BorderLayout(5,5));
				pnlmain.add(mainCenter,BorderLayout.CENTER);
				{
					scrollMC = new JScrollPane();
					mainCenter.add(scrollMC,BorderLayout.CENTER);
					scrollMC.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollMC.setBorder(new EmptyBorder(5,5,5,5));

				}
				{


					modelRMC_cv = new modelRMC_dv();
					tblMC = new _JTableMain(modelRMC_cv);
					scrollMC.setViewportView(tblMC);
					tblMC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblMC.setEnabled(true);
					tblMC.setSortable(false);
					tblMC.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if(tblMC.getSelectedRows().length==1){

							}

						}
					});
				}
				{
					rowHeadMC = tblMC.getRowHeader();
					rowHeadMC.setModel(new DefaultListModel());
					scrollMC.setRowHeaderView(rowHeadMC);
					scrollMC.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}


			}
			{
				JPanel mainSouth = new JPanel(new GridLayout(1,7,3,3));
				pnlmain.add(mainSouth,BorderLayout.SOUTH);
				mainSouth.setBorder(new EmptyBorder(5,5,5,5));
				mainSouth.setPreferredSize(new Dimension (0,30));
				{
					mainSouth.add(Box.createHorizontalBox());
					mainSouth.add(Box.createHorizontalBox());
					mainSouth.add(Box.createHorizontalBox());
				}
				{

					btnSave = new JButton("Save");
					mainSouth.add(btnSave);
					btnSave.setMnemonic(KeyEvent.VK_P);
					btnSave.addActionListener(this);


					btnPreview = new JButton("Preview");
					mainSouth.add(btnPreview);
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);


					btnCancel = new JButton("Cancel");
					mainSouth.add(btnCancel);
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.addActionListener(this);



				}				

			}


		}

	}

	public static String getPcostid(String co_id) {
		return "	SELECT distinct on (pcostdtl_id::int) pcostdtl_id, trim(pcostdtl_desc) as pcostdtl_desc, pcostdtl_amt, remarks\n" + 
				"					FROM mf_processing_cost_dl\n" + 
				"					WHERE status_id = 'A'\n" + 
				"                    and co_id = '" + lookupCompany.getValue() + "'\n" + 
				"					and pcostdtl_id in (\n" + 
				"									SELECT \n" + 
				"									pcostid_dl \n" + 
				"									FROM rf_processing_cost \n" + 
				"									WHERE status_id = 'A'\n" + 
				"                                   -- and pcostid_dl in ('008','011','012','021','169')\n" + 
				"                                    and server_id is null)\n" + 
				"					order by pcostdtl_id::int ASC";


	}



	public static void displayDV_pv(final DefaultTableModel modelMain, final String co_id) {
		FncTables.clearTable(modelMain);
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");
				modelMain.setRowCount(0);
				DefaultListModel listmod = new DefaultListModel();
				rowHeadMC.setModel(listmod);

				String sql = 

						"select false, \n" + 
						"    b.rplf_no as \"RPLF No.\",\n" + 
						"    c.entity_id as \"Client ID\",\n" + 
						"    get_client_name(c.entity_id) as \"Client Name\",\n" + 
						"    d.pbl_id as \"PBL\",\n" + 
						"    b.pcost_amt as \"Amount\",\n" + 
						"    a.pv_no,\n" + 
						"    a.cv_no\n" + 
						"from rf_pv_header a\n" + 
						"    left join rf_processing_cost b on a.rplf_no = b.rplf_no and a.co_id = b.co_id \n" + 
						"    left join rf_entity c on b.entity_id = c.entity_id\n" + 
						"    left join mf_unit_info d on b.projcode = d.proj_id and b.pbl_id = d.pbl_id\n" + 
						"	 \n" + 
						"where b.pcostid_dl ='"+lookupPcostID.getValue()+"'\n" + 
						"and not exists (select * from rf_rmcpcost_detail e where a.co_id = e.co_id and a.cv_no = e.cv_no and a.rplf_no = e.rplf_no and c.entity_id = e.entity_id)\n" + 
						"and a.status_id !='I'\n" + 
						"and a.co_id ='" + lookupCompany.getValue() + "'\n" + 
						"order by b.rplf_no";

				FncSystem.out("displayDV", sql);
				pgSelect db = new pgSelect();
				db.select(sql);

				if(db.isNotNull()) {
					for(int x=0; x < db.getRowCount(); x++){
						modelMain.addRow(db.getResult()[x]);
						listmod.addElement(modelRMC_cv.getRowCount());
					}
				}

				tblMC.packAll();	

				FncGlobal.stopProgress();
				return db.getResult();
			}

		};

		sw.execute();

	}


	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Save")) {		save();}
		
		if(e.getActionCommand().equals("Preview")) {	preview();}

		if(e.getActionCommand().equals("Cancel")) {		cancel();}
	}


	private void cancel() {

		co_id = "";
		lookupCompany.setValue(null);
		lookupPcostID.setValue(null);
		txtCompany.setText("");
		txtDvno.setText("");
		modelRMC_cv.clear();
		btnSave.setEnabled(false);

	}
	
	private void preview() {
		
		{
			
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("co_name", txtCompany.getText());
			mapParameters.put("pcost_id", lookupPcostID.getValue());
			mapParameters.put("emp_alias", UserInfo.Alias);
			mapParameters.put("emp_fname", UserInfo.FirstName);
			mapParameters.put("emp_lname", UserInfo.LastName);
			mapParameters.put("emp_code", UserInfo.EmployeeCode);
			mapParameters.put("p_dateFrom", dteDateFrom.getDate());
			mapParameters.put("p_dateTo", dateDateTo.getDate());
		

			FncReport.generateReport("/Reports/rptTagRMCPcost.jasper", title, txtCompany.getText(),mapParameters);
			
		
	}
	
	}

	private void save() {

		ArrayList<String> listPvRMc = new ArrayList<String>();

		if (isSelected() == true ) {


			if (JOptionPane.showConfirmDialog(getContentPane(), "Do you want to save entries?", "Save", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {
				for (int x = 0; x < modelRMC_cv.getRowCount(); x++) {
					Boolean isSelected = (Boolean) modelRMC_cv.getValueAt(x, 0);
					if(isSelected) {
						String rplf= (String) modelRMC_cv.getValueAt(x, 1);
						String client = (String) modelRMC_cv.getValueAt(x, 2);
						String pbl = (String) modelRMC_cv.getValueAt(x, 4);
						BigDecimal amt = (BigDecimal) modelRMC_cv.getValueAt(x, 5);
						String pv = (String) modelRMC_cv.getValueAt(x, 6);
						String dv = (String) modelRMC_cv.getValueAt(x, 7);
						Boolean bol = (Boolean)modelRMC_cv.getValueAt(x, 0);
					

						listPvRMc.add(String.format("%s", rplf));
						listPvRMc.add(String.format("%s", client));
						listPvRMc.add(String.format("%s", pbl));
						listPvRMc.add(String.format("%s", pv));
						listPvRMc.add(String.format("%s", dv));
				


						String sql = "INSERT INTO public.rf_rmcpcost_detail(\n" + 
								"	co_id,rplf_no, entity_id, pbl_id, amount, pv_no, cv_no,status_id, created_by, date_created, edited_by,tag)\n" + 
								"	VALUES ('" + lookupCompany.getValue() + "',"
								+ "'"+rplf+"',"
								+ "'" +client+ "',"
								+ "'" +pbl+ "',"
								+ "'" +amt+ "',"
								+ "'" +pv+ "',"
								+ "'" +dv+ "',"
								+ "'A',"
								+ "'"+UserInfo.EmployeeCode+"',"
								+ "now()::timestamp without time zone,"
								+ "'"+UserInfo.EmployeeCode+"',"
								+ "'"+bol+"')";
								
						

						FncSystem.out("SQL saving of RMC no", sql);
						pgUpdate db = new pgUpdate();
						db.executeUpdate(sql, true);
						db.commit();

					}	

				}
				JOptionPane.showMessageDialog(getContentPane(), "Successfully saved!", "Save", JOptionPane.INFORMATION_MESSAGE);
			}	

			displayDV_pv(modelRMC_cv,co_id);

		}else {
			JOptionPane.showMessageDialog(getContentPane(), "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}



