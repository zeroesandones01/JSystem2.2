package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import tablemodel.modelMBTCPcost;
import tablemodel.modelTransactionSummary;

public class ReturnCheckMBTCPcost_v2 extends _JInternalFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6143015621825644470L;
	Dimension size =  new Dimension(550,600);
	static String title = "Returned Checks-MBTC For PCOST";

	private JPanel mainNorth;
	private JLabel lblCompany,lblProject,lblPhase;
	private JTextField txtCompany, txtProject, txtPhase;
	private _JLookup lookupCompany, lookupProject, lookupPhase;
	private JButton btnPreview;
	private JScrollPane scrollPCOST;
	private _JTableMain tblPCOST;
	private JList rowHeadPcost;
	private modelMBTCPcost modelPCOSTList;
	private String co_id;
	private String proj_id;
	private String phase;
	private String rplf_no;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();


	public ReturnCheckMBTCPcost_v2(){
		super(title,true,true,false,false);
		initGui();
	}
	public ReturnCheckMBTCPcost_v2(String title){
		super(title);
		initGui();
	}
	public ReturnCheckMBTCPcost_v2(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconifiable){
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

					JPanel lblMainNorth = new JPanel(new GridLayout(3,1,3,3));
					lblMainNorth.setBorder(new EmptyBorder(5,5,5,5));
					mainNorth.add(lblMainNorth,BorderLayout.WEST);
					{
						lblCompany = new JLabel("Company:");
						lblMainNorth.add(lblCompany);

						lblProject = new JLabel("Project:");
						lblMainNorth.add(lblProject);

						lblPhase = new JLabel("Phase:");
						lblMainNorth.add(lblPhase);

					}
				}
				{
					JPanel mainNorthComponents = new JPanel(new GridLayout(3,1,3,3));
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

										String co_id = (String) data[0];
										txtCompany.setText(data[1].toString());
										txtCompany.setToolTipText(data[2].toString());
										lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));
										manager.focusNextComponent();
										displayPCOST(modelPCOSTList, lookupCompany.getValue(),lookupProject.getValue(), lookupPhase.getValue());
									}else{
										txtCompany.setText("");
									}
								}

							});

						}
						{
							lookupProject = new _JLookup(null, "Project");
							mainNorthComponents.add(lookupProject);
							lookupProject.setReturnColumn(0);
							lookupProject.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {//XXX Project
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtProject.setText(data[1].toString());
										lookupPhase.setLookupSQL(sqlPhase(data[0].toString()));
										manager.focusNextComponent();
										displayPCOST(modelPCOSTList, lookupCompany.getValue(),lookupProject.getValue(), lookupPhase.getValue());
									}else{
										txtProject.setText("");
									}
								}

							});
						}
						{
							lookupPhase = new _JLookup(null, "Phase");
							mainNorthComponents.add(lookupPhase);
							lookupPhase.setReturnColumn(0);
							lookupPhase.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {//XXX Phase
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtPhase.setText(data[1].toString());
										manager.focusNextComponent();
										displayPCOST(modelPCOSTList, lookupCompany.getValue(),lookupProject.getValue(), lookupPhase.getValue());

									}else{
										JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select project first.", "Phase", JOptionPane.WARNING_MESSAGE);
									}
								}
							});						
						}
					}
				}

				JPanel mainNorthComponents2 = new JPanel(new GridLayout(3,1,3,3));
				mainNorthComponents2.setBorder(new EmptyBorder(5,5,5,5));
				mainNorth.add(mainNorthComponents2,BorderLayout.EAST);
				{
					txtCompany = new JTextField();
					mainNorthComponents2.add(txtCompany);
					txtCompany.setEditable(false);
					txtCompany.setPreferredSize(new Dimension(350,0));
				}

				{
					txtProject = new JTextField();
					mainNorthComponents2.add(txtProject);
					txtProject.setEditable(false);
					txtProject.setPreferredSize(new Dimension(350,0));
				}

				{
					txtPhase = new JTextField();
					mainNorthComponents2.add(txtPhase);
					txtPhase.setEditable(false);
					txtPhase.setPreferredSize(new Dimension(350,0));
				}

			}
			{
				JPanel mainCenter = new JPanel(new BorderLayout(5,5));
				pnlmain.add(mainCenter,BorderLayout.CENTER);
				{
					scrollPCOST = new JScrollPane();
					mainCenter.add(scrollPCOST,BorderLayout.CENTER);
					scrollPCOST.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollPCOST.setBorder(new EmptyBorder(5,5,5,5));

				}
				{


					modelPCOSTList = new modelMBTCPcost();
					tblPCOST = new _JTableMain(modelPCOSTList);
					scrollPCOST.setViewportView(tblPCOST);
					tblPCOST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblPCOST.setEnabled(true);
					tblPCOST.setSortable(false);
					tblPCOST.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if(tblPCOST.getSelectedRows().length==1){

							}

						}
					});
				}
				{
					rowHeadPcost = tblPCOST.getRowHeader();
					rowHeadPcost.setModel(new DefaultListModel());
					scrollPCOST.setRowHeaderView(rowHeadPcost);
					scrollPCOST.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}


			}
			{
				JPanel mainSouth = new JPanel(new GridLayout(1,7,3,3));
				pnlmain.add(mainSouth,BorderLayout.SOUTH);
				mainSouth.setBorder(new EmptyBorder(5,5,5,5));
				mainSouth.setPreferredSize(new Dimension (0,30));
				{

					btnPreview = new JButton("Preview");
					mainSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);


				}				

			}

		}
		displayPCOST(modelPCOSTList, lookupCompany.getValue(),lookupProject.getValue(), lookupPhase.getValue());

	}


	//	private String getDRF_no(String co_id)
	//	{
	//		return 
	//				"select \r\n" + 
	//				"a.rplf_no as \"Rplf No.\", \n" + 
	//				"get_employee_name(a.created_by) as \"Created By\",\r\n"+
	//				"a.retrn_check_no as \"Returned Check No.\",\r\n"+
	//				"a.date_created as \"Rplf Date Created\"\r\n" + 
	//
	//		"from rf_processing_cost a\r\n" + 
	//		"left join rf_entity b on a.entity_id = b.entity_id  " +
	//		"left join rf_pv_header c on a.rplf_no=c.pv_no and a.co_id=c.co_id\r\n" + 
	//		" \r\n" + 
	//		"where CASE WHEN '"+co_id+"' = 'null'  THEN TRUE ELSE a.co_id = '"+co_id+"' END \n" +
	//		"order by a.rplf_no desc "  ;
	//
	//	}

	private String sqlPhase(String proj_id) {
		return "SELECT a.phase as \"Phase\", a.sub_proj_name as \"Description\", \n" + 
				"a.proj_id as \"Proj. ID\", b.proj_name as \"Project Name\", b.proj_alias as \"Alias\"\n" + 
				"from mf_sub_project a\n" + 
				"LEFT JOIN mf_project b on b.proj_id = a.proj_id\n" + 
				"where (CASE WHEN NULLIF('"+proj_id+"', 'null') IS NOT NULL THEN  a.proj_id = '"+proj_id+"' ELSE TRUE END) AND a.status_id = 'A'";//ADDED STATUS ID BY LESTER DCRF 2718
	}

	private void displayPCOST(final DefaultTableModel modelMain, final String co_id, final String proj_id, final String phase) {
		FncTables.clearTable(modelMain);
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");
				modelMain.setRowCount(0);
				DefaultListModel listmod = new DefaultListModel();
				rowHeadPcost.setModel(listmod);

				String sql = 
						"select distinct on (a.rplf_no)\n" + 
								"false,\n" +
								"a.rplf_no as \"Rplf No.\", \n" + 
								"get_employee_name(a.created_by) as \"Created By\",\n"+
								"a.date_created as \"Rplf Date Created\"\n," + 
								"a.retrn_check_no as \"Returned Check No.\"\n"+


						"from rf_processing_cost a\n" + 
						"left join rf_entity b on a.entity_id = b.entity_id  \n" +
						"left join rf_pv_header c on a.rplf_no = c.pv_no and a.co_id=c.co_id\n" + 
						"left join mf_unit_info d on d.proj_id = a.projcode and d.pbl_id = a.pbl_id" +					
						" \n" + 
						"where CASE WHEN '"+co_id+"' = 'null'  THEN TRUE ELSE a.co_id = '"+co_id+"' END \n" +
						"and CASE WHEN '"+proj_id+"' = 'null'  THEN TRUE ELSE a.projcode = '"+proj_id+"' END \n" +
						"and CASE WHEN '"+phase+"' = 'null'  THEN TRUE ELSE d.phase = '"+phase+"' END \n" +
						//"and CASE WHEN '"+rplf_no+"' = 'null'  THEN TRUE ELSE a.rplf_no = '"+rplf_no+"' END \n" +
						"group by a.rplf_no, a.created_by, a.retrn_check_no, a.date_created\n" + 
						"order by a.rplf_no desc "  ;

				FncSystem.out("displayPCOST", sql);
				pgSelect db = new pgSelect();
				db.select(sql);

				if(db.isNotNull()) {
					for(int x=0; x < db.getRowCount(); x++){
						modelMain.addRow(db.getResult()[x]);
						listmod.addElement(modelPCOSTList.getRowCount());
					}
				}

				tblPCOST.packAll();

				FncGlobal.stopProgress();
				return null;
			}
		};
		sw.execute();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if(action.equals("Preview")){
			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("p_co_id", lookupCompany.getValue());
			mapParameters.put("p_co_name", txtCompany.getText());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			mapParameters.put("p_phase", lookupPhase.getValue());
			mapParameters.put("p_rplf_no", rplf_no);


			FncReport.generateReport("/Reports/rptReturnCheckForPcost.jasper", title, txtProject.getText(), txtPhase.getText(), mapParameters);
			System.out.print("Dumaan dito....");
		}

	}
}



