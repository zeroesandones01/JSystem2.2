package Buyers.LoansManagement;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.table.DefaultTableModel;

import tablemodel.modelCommSchedule;
import tablemodel.modelPagibigStatusMonitoring_MSVSMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components.JTBorderFactory;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;

public class pnlMSVS_Monitoring extends JPanel implements _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7179374196951209049L;

	private PagibigStatusMonitoring har;
	private JPanel pnlPMMNCLabel;
	private JLabel lblPMMCompany;
	private JLabel lblPMMProject;
	private JLabel lblPMMPhase;
	private JLabel lblPMMStage;
	private JLabel lblPMMTransDate;
	private JPanel pnlPMMNCComponent;
	private JPanel pnlPMMCompany;
	private _JLookup lookupPMMCompany;
	private _JXTextField txtPMMCompany;
	private JPanel pnlPMMProject;
	private _JLookup lookupPMMProject;
	private _JXTextField txtPMMProject;
	private JPanel pnlPMMPhase;
	private _JLookup lookupPMMPhase;
	private _JXTextField txtPMMPhase;
	private JPanel pnlPMMStage;
	private JPanel pnlPMMTransDate;
	private _JDateChooser dtePMMTransDate;
	private JPanel pnlPMMNorthEast;
	private JButton btnPMMGenerate;
	private JPanel pnlPMMCenter;
	private JScrollPane scrollPMMMSVSMonitoring;
	private modelPagibigStatusMonitoring_MSVSMonitoring modelPMMMSVSMonitoring;
	private _JTableMain tblPMMMSVSMonitoring;
	private JList rowHeaderPMMMSVSMonitoring;
	private JPanel pnlPMMSouth;
	private JPanel pnlPMMNorth;
	private JPanel pnlPMMNorthCenter;
	private JButton btnPMMPost;
	private JButton btnPMMClear;
	private JPanel pnlPMMSouthLower;

	private String co_id = "02";
	private String co_name = "CENQHOMES DEVELOPMENT CORPORATION";
	private String proj_id = "";
	private String proj_name = "";
	private String phase_no = "";
	private String phase = "";
	private String pmt_stage_id_old = "";
	private String pmt_stage_id_new = "";
	private String pmt_stage_name_old = "";
	private String pmt_stage_name_new = "";

	private JComboBox cmbStage;
	private JButton btnPMMPreview;
	private JButton btnPMMExport;

	private JPanel pnlPMMNorthEast_a;

	private JPanel pnlPMMNorthEast_b;

	private JPanel pnlPMMNorthEast_b_1;

	private JPanel pnlPMMNorthEast_b_2;

	private JLabel lblPMM_batchNo;

	private _JLookup lookupBatch;

	private JPanel pnlPMMSouthWest;	


	public pnlMSVS_Monitoring(PagibigStatusMonitoring har) {
		this.har = har;

		initGUI();		
	}

	public pnlMSVS_Monitoring(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlMSVS_Monitoring(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlMSVS_Monitoring(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(600, 400));
		{
			JPanel pnlHoldingDetails = new JPanel(new BorderLayout(3, 3));
			this.add(pnlHoldingDetails, BorderLayout.CENTER);
			pnlHoldingDetails.setPreferredSize(new Dimension(0, 310));


			{
				pnlPMMNorth = new JPanel(new BorderLayout(3, 3));
				pnlHoldingDetails.add(pnlPMMNorth, BorderLayout.NORTH);
				pnlPMMNorth.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					pnlPMMNorthCenter = new JPanel(new BorderLayout(3, 3));
					pnlPMMNorth.add(pnlPMMNorthCenter, BorderLayout.CENTER);
					{
						pnlPMMNCLabel = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlPMMNorthCenter.add(pnlPMMNCLabel, BorderLayout.WEST);
						{
							lblPMMCompany = new JLabel("Company");
							pnlPMMNCLabel.add(lblPMMCompany);
							lblPMMCompany.setEnabled(true);
						}
						{
							lblPMMProject = new JLabel("Project");
							pnlPMMNCLabel.add(lblPMMProject);
							lblPMMProject.setEnabled(true);
						}
						{
							lblPMMPhase = new JLabel("Phase");
							pnlPMMNCLabel.add(lblPMMPhase);
							lblPMMPhase.setEnabled(false);
						}
						{
							lblPMMStage = new JLabel("Stage");
							pnlPMMNCLabel.add(lblPMMStage);
						}
						{
							lblPMMTransDate = new JLabel("Trans. Date");
							pnlPMMNCLabel.add(lblPMMTransDate);
						}
					}
					{
						pnlPMMNCComponent = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlPMMNorthCenter.add(pnlPMMNCComponent, BorderLayout.CENTER);
						{
							pnlPMMCompany = new JPanel(new BorderLayout(3, 3));
							pnlPMMNCComponent.add(pnlPMMCompany);
							{
								lookupPMMCompany = new _JLookup(null, "Select Company", 0);
								pnlPMMCompany.add(lookupPMMCompany, BorderLayout.WEST);
								lookupPMMCompany.setPreferredSize(new Dimension(50, 0));
								lookupPMMCompany.setText("02");								
								lookupPMMCompany.setLookupSQL(_PagibigStatusMonitoring.sqlCompany());
								lookupPMMCompany.addLookupListener(new LookupListener() {	
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();										
										if(data != null){
											co_id = (String) data[0];
											co_name = (String) data[1];

											lookupPMMProject.setLookupSQL(_PagibigStatusMonitoring.sqlProject(co_id));
											txtPMMCompany.setText(co_name);											
											lookupPMMProject.setEnabled(true);
											txtPMMProject.setEnabled(true);
											lblPMMProject.setEnabled(true);
										}
									}
								});
							}
							{
								txtPMMCompany = new _JXTextField();
								pnlPMMCompany.add(txtPMMCompany, BorderLayout.CENTER);
								txtPMMCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
							}
						}
						{
							pnlPMMProject = new JPanel(new BorderLayout(3, 3));
							pnlPMMNCComponent.add(pnlPMMProject);

							{
								lookupPMMProject = new _JLookup(null, "Select Project", 0, "Please select Company First");
								pnlPMMProject.add(lookupPMMProject, BorderLayout.WEST);
								lookupPMMProject.setPreferredSize(new Dimension(50, 0));
								lookupPMMProject.setLookupSQL(_PagibigStatusMonitoring.sqlProject(co_id));
								lookupPMMProject.setEnabled(true);
								lookupPMMProject.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null){
											proj_id = (String) data[0];
											proj_name = (String) data[1];

											lookupPMMPhase.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id));
											txtPMMProject.setText(proj_name);
											lookupPMMPhase.setEnabled(true);
											txtPMMPhase.setEnabled(true);
											lblPMMPhase.setEnabled(true);
										}
									}
								});
							}
							{
								txtPMMProject = new _JXTextField();
								pnlPMMProject.add(txtPMMProject, BorderLayout.CENTER);
								txtPMMProject.setEnabled(true);
							}
						}
						{
							pnlPMMPhase = new JPanel(new BorderLayout(3, 3));
							pnlPMMNCComponent.add(pnlPMMPhase);
							{
								lookupPMMPhase = new _JLookup(null, "Select Phase", 0, "Please select Project First");
								pnlPMMPhase.add(lookupPMMPhase, BorderLayout.WEST);
								lookupPMMPhase.setEnabled(false);
								lookupPMMPhase.setPreferredSize(new Dimension(50, 0));
								lookupPMMPhase.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null){
											phase_no = (String) data[1];
											phase = (String) data[2];											
											txtPMMPhase.setText(phase);
										}
									}
								});
							}
							{
								txtPMMPhase = new _JXTextField();
								pnlPMMPhase.add(txtPMMPhase, BorderLayout.CENTER);
								txtPMMPhase.setEnabled(false);
							}
						}
						{
							pnlPMMStage = new JPanel(new BorderLayout(3, 3));
							pnlPMMNCComponent.add(pnlPMMStage);

							String stage[] = { "MSVS for Verification", "MSVS for Reverification"};
							cmbStage = new JComboBox(stage);
							pnlPMMStage.add(cmbStage);
							cmbStage.setSelectedItem(null);
							cmbStage.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
							cmbStage.setBounds(537, 15, 190, 21);
							cmbStage.setEnabled(true);
							cmbStage.setSelectedIndex(0);
						}
						{
							pnlPMMTransDate = new JPanel(new BorderLayout(3, 3));
							pnlPMMNCComponent.add(pnlPMMTransDate);
							{
								dtePMMTransDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlPMMTransDate.add(dtePMMTransDate, BorderLayout.WEST);
								dtePMMTransDate.setPreferredSize(new Dimension(150, 0));
								dtePMMTransDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}
						}
					}
				}
				{
					pnlPMMNorthEast = new JPanel(new BorderLayout(3, 3));
					pnlPMMNorth.add(pnlPMMNorthEast, BorderLayout.EAST);
					pnlPMMNorthEast.setPreferredSize(new java.awt.Dimension(200, 82));

					{
						btnPMMGenerate = new JButton("Generate");
						pnlPMMNorthEast.add(btnPMMGenerate);
						btnPMMGenerate.setPreferredSize(new java.awt.Dimension(200, 85));
						btnPMMGenerate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								System.out.printf("FIRST:");
								displayMSVS_verification(modelPMMMSVSMonitoring,rowHeaderPMMMSVSMonitoring);
							}
						});
					}

				}
			}
			{
				pnlPMMCenter = new JPanel(new BorderLayout(3, 3));
				pnlHoldingDetails.add(pnlPMMCenter, BorderLayout.CENTER);
				pnlPMMCenter.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
				{
					scrollPMMMSVSMonitoring = new JScrollPane();
					pnlPMMCenter.add(scrollPMMMSVSMonitoring, BorderLayout.CENTER);

					{
						modelPMMMSVSMonitoring = new modelPagibigStatusMonitoring_MSVSMonitoring();
						tblPMMMSVSMonitoring = new _JTableMain(modelPMMMSVSMonitoring);
						scrollPMMMSVSMonitoring.setViewportView(tblPMMMSVSMonitoring);
						scrollPMMMSVSMonitoring.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					}
					{
						rowHeaderPMMMSVSMonitoring = tblPMMMSVSMonitoring.getRowHeader();
						rowHeaderPMMMSVSMonitoring.setModel(new DefaultListModel());
						scrollPMMMSVSMonitoring.setRowHeaderView(rowHeaderPMMMSVSMonitoring);
						scrollPMMMSVSMonitoring.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlPMMSouth = new JPanel(new BorderLayout(3, 3));
				pnlHoldingDetails.add(pnlPMMSouth, BorderLayout.SOUTH);
				pnlPMMSouth.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
				pnlPMMSouth.setPreferredSize(new java.awt.Dimension(590, 30));

				{
					pnlPMMSouthWest = new JPanel(new BorderLayout(3, 3));
					pnlPMMSouth.add(pnlPMMSouthWest, BorderLayout.WEST);
					pnlPMMSouthWest.setPreferredSize(new java.awt.Dimension(227, 75));
					//pnlPMMSouthWest.setBorder(JTBorderFactory.createTitleBorder("Batch No.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));

					{
						pnlPMMNorthEast_b_1 = new JPanel(new BorderLayout(3, 3));
						pnlPMMSouthWest.add(pnlPMMNorthEast_b_1, BorderLayout.WEST);

						{
							lblPMM_batchNo = new JLabel("Batch No.");
							pnlPMMNorthEast_b_1.add(lblPMM_batchNo);
							lblPMM_batchNo.setEnabled(true);
							lblPMM_batchNo.setPreferredSize(new java.awt.Dimension(60, 32));
						}
					}
					{
						pnlPMMNorthEast_b_2 = new JPanel(new BorderLayout(3, 3));
						pnlPMMSouthWest.add(pnlPMMNorthEast_b_2, BorderLayout.CENTER);						

						{
							lookupBatch = new _JLookup(null, "Select Batch", 0);
							pnlPMMNorthEast_b_2.add(lookupBatch, BorderLayout.WEST);
							lookupBatch.setLookupSQL(_PagibigStatusMonitoring.sqlProject(co_id));
							lookupBatch.setEnabled(true);
							lookupBatch.setPreferredSize(new java.awt.Dimension(160, 75));
							lookupBatch.addLookupListener(new LookupListener() {										
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										/*proj_id = (String) data[0];
											proj_name = (String) data[1];

											lookupPMMPhase.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id));
											txtPMMProject.setText(proj_name);
											lookupPMMPhase.setEnabled(true);
											txtPMMPhase.setEnabled(true);
											lblPMMPhase.setEnabled(true);*/
									}
								}
							});
						}						
					}

				}
				{
					pnlPMMSouthLower = new JPanel(new GridLayout(1, 1, 0, 0));
					pnlPMMSouth.add(pnlPMMSouthLower, BorderLayout.CENTER);
					pnlPMMSouthLower.setPreferredSize(new java.awt.Dimension(580, 27));

					{
						btnPMMPost = new JButton("Post");
						pnlPMMSouthLower.add(btnPMMPost);
						btnPMMPost.setActionCommand("Post");
					}
					{
						btnPMMPreview = new JButton("Preview");
						pnlPMMSouthLower.add(btnPMMPreview);
						btnPMMPreview.setActionCommand("Preview");
					}					
					{
						btnPMMClear = new JButton("Clear");
						pnlPMMSouthLower.add(btnPMMClear);
						btnPMMClear.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								lookupPMMCompany.setText("02");			
								txtPMMCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");

							}
						});
					}
					{
						btnPMMExport = new JButton("Export");
						pnlPMMSouthLower.add(btnPMMExport);
						btnPMMExport.setActionCommand("Export");
					}
				}
			}
		}			

		//this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbTransaction, lookupCompany, txtCompany, lookupProject, txtProject, lookupPBL, txtPBL,
		//txtModel, txtModelName, lookupSalesAgent, txtSalesAgent, lookupSalesDivision, txtSalesDivisionName, dateValidFrom, dateValidTo, dateExtendedUntil, txtSellingPrice));

		//splitNorth.setDividerLocation(1.0);
	}


	//action performed
	public void actionPerformed(ActionEvent e) {		

		if (e.getActionCommand().equals("Preview")) {

			String company_logo = RequestForPayment.sql_getCompanyLogo();			
			SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
			Date dateObj = new Date();
			String strDate = (String) sdfTo.format(dateObj);

			/*strDate = "As Of: " + strDate;			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", hins_txtCo.getText());
			mapParameters.put("02_AsOfDate", strDate);
			mapParameters.put("03_CoID", hins_txtCoID.getValue());
			mapParameters.put("04_ProID", hins_txtProID.getValue());
			mapParameters.put("06_Project", hins_txtPro.getText());
			mapParameters.put("07_User",  _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters.put("09_Batch", hins_txtBatch.getText());
			FncReport.generateReport("/Reports/rpt_hdmf_pre_inspection.jasper", "HDMF Inspection Report", "", mapParameters);*/

		} 		
	}


	//displays
	public void displayMSVS_verification(DefaultTableModel modelMain, JList rowHeader) {//

		System.out.printf("SECOND:");

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"false,\r\n" + 
			"b.proj_alias,\r\n" + 
			"c.description,\r\n" + 
			"d.entity_name,\r\n" + 
			"'',\r\n" + 
			"(case when trim(d.middle_name) != trim(e.mother_maiden_lname) then e.mother_maiden_lname else '' end) as maiden_name,\r\n" + 
			"f.loanable_amount,\r\n" + 
			"to_char(g.tran_date,'MM-dd-yyyy') as or_date,\r\n" + 
			"(case when h.work_percent = 100 then to_char(h.date_created, 'MM-dd-yyyy') else null end) as completion,\r\n" + 
			"h.work_percent,\r\n" + 
			"to_char(c.ntc, 'MM-dd-yyyy') as ntc_date,\r\n" + 
			"to_char(i.ntp_date, 'MM-dd-yyyy') as ntp_date,\r\n" + 
			"to_char(j.date_ok, 'MM-dd-yyyy') as compliance_date,\r\n" + 
			"j.gen_findings\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"from rf_sold_unit a\r\n" + 
			"left join mf_project b on a.projcode = b.proj_id\r\n" + 
			"left join mf_unit_info c on a.pbl_id = c.pbl_id\r\n" + 
			"left join rf_entity d on a.entity_id = d.entity_id\r\n" + 
			"left join rf_entity_mother_maiden_name e on a.entity_id = e.entity_id\r\n" + 
			"left join rf_pagibig_computation f on a.pbl_id = f.pbl_id and a.seq_no = f.seq_no \r\n" + 
			"left join (select * from rf_buyer_status where status_id = 'A' and byrstatus_id = '01') g on a.pbl_id = g.pbl_id and a.seq_no = g.seq_no \r\n" + 
			"left join (select distinct on (pbl_id) pbl_id, work_percent, date_created, ntp_no from co_ntp_detail where status_id = 'A' order by pbl_id, rec_id desc) h on a.pbl_id = h.pbl_id\r\n" + 
			"left join co_ntp_header i on h.ntp_no = i.ntp_no\r\n" + 
			"left join (select distinct on (pbl_id, seq_no) pbl_id, seq_no, gen_findings, date_ok from dm_gen_findings order by pbl_id, seq_no, gen_findings) j on a.pbl_id = j.pbl_id and a.seq_no = j.seq_no \r\n" + 
			"\r\n" + 
			"where a.pmt_scheme_id in ('001','002','008')\r\n" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}		
		}

		else {}

		tblPMMMSVSMonitoring.packAll();				
		tblPMMMSVSMonitoring.getColumnModel().getColumn(0).setPreferredWidth(80);

		System.out.printf("THIRD:");

	}





}