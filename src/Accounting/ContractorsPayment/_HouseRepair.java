package Accounting.ContractorsPayment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXTextField;

import Buyers.ClientServicing.CARD;
import Buyers.LoansManagement.PagibigStatusMonitoring_v2;
import Database.pgSelect;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel.modelContractorHouseRepair;

	public class _HouseRepair extends JPanel implements _GUI, ActionListener, MouseListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3061284418918863916L;
		private CARD main_card;
		static String title = "House Repair";
		static Dimension SIZE = new Dimension(738, 351);

		private JPanel pnlMain;
		private JPanel pnlNorth;
		
		//contract details
		private JPanel pnlContract;
		private JPanel pnlContractDetails;
		
		private JPanel pnlContractDetailsA;
		private JPanel pnlContractDetails1;		
		private JLabel lblProject;
		private JLabel lblUnit;
		
		private JPanel pnlContractDetails2;
		private static JXTextField txtProject;
		private static JXTextField txtUnit;
		
		private JPanel pnlContractDetailsB;
		private JPanel pnlContractDetails3;
		private JLabel lblContractor;
		private JLabel lblContractNo;
		
		private JPanel pnlContractDetails4;
		private static JXTextField txtContractNo;
		private static JXTextField txtContractor;
		
		
		
		//repair details
		private JPanel pnlRepair;
		private JPanel pnlRepairDetails;
		
		private JPanel pnlRepairDetailsA;
		private JPanel pnlRepairDetails1;
		private JLabel lblRepairNo;
		private JLabel lblRepairDate;
		private JLabel lblRepairAmt;
		
		private JPanel pnlRepairDetails2;
		private static JXTextField txtRepairNo;
		private static JTextField txtRepairDate;
		private static JTextField txtRepairAmt;
		
		private JPanel pnlRepairDetailsB;
		private JPanel pnlRepairDetails3;
		private JLabel lblPayee;
		private JLabel lblPayeeType;
		private JLabel lblRepairRPLF;
		
		private JPanel pnlRepairDetails4;
		private static JXTextField txtPayee;
		private static JTextField txtPayeeType;
		private static JTextField txtRepairRPLF;
		
		private JPanel pnlRepairDetailsC;
		private JPanel pnlRepairDetails5;
		private JLabel lblChargeTo;
		private JLabel lblChargeAmt;
		private JLabel lblStatus;
		
		private JPanel pnlRepairDetails6;
		private static JXTextField txtChargeTo;
		private static JTextField txtChargeAmt;
		private static JTextField txtStatus;

		private JPanel pnlRepairDetailsD;
		private JPanel pnlRepairDetailLbl;
		private JPanel pnlRepairDesc;
		private JLabel lblRepairDesc;
		private static JTextArea txtRepairDesc;
		
		private modelContractorHouseRepair modelRepair;
		private modelContractorHouseRepair modelRepair_total;
		private _JTableTotal tblRepair_total;	
		private _JTableMain tblRepair;
		private JList rowHeaderRepair;	

		
		Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

		public static String company_logo;
		public static String proj_id = "";
		String co_id = "";
		String company 		= "";
		String proj_name = "";
		String pbl_no = "";
		String pbl_desc = "";
		String ntp_no 		= "";
		String contract_no 	= "";
		String contractor_name 	= "";	
		String payee_id 	= "";
		String payee_name 	= "";
		String payee_type_id 	= "";
		String payee_type_name 	= "";
		String charge_payee_id 	= "";
		String charge_payee_name 	= "";
		String charge_payee_type_id 	= "";
		String charge_payee_type_name 	= "";
		String rplf_no = "";
		String repair_no = "";

		public _HouseRepair() {
			initGUI();
		}

		public _HouseRepair(boolean isDoubleBuffered) {
			super(isDoubleBuffered);

		}

		public _HouseRepair(LayoutManager layout) {
			super(layout);

		}

		public _HouseRepair(LayoutManager layout, boolean isDoubleBuffered) {
			super(layout, isDoubleBuffered);

		}

		
@Override
public void initGUI() {
	this.setLayout(new BorderLayout(5,5));
	this.setSize(SIZE);
	this.setPreferredSize(new java.awt.Dimension(935, 500));
	this.setBounds(0, 0, 935, 538);

	pnlMain = new JPanel();
	this.add(pnlMain, BorderLayout.CENTER);
	pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	pnlMain.setLayout(new BorderLayout(5, 5));
	pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));
	{
		pnlNorth = new JPanel();
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlNorth.setLayout(new BorderLayout(0,0));
		pnlNorth.setBorder(lineBorder);
		pnlNorth.setPreferredSize(new java.awt.Dimension(923, 200));
		{
			//contract details
			pnlContract = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlNorth.add(pnlContract, BorderLayout.NORTH);	
			pnlContract.setPreferredSize(new java.awt.Dimension(600, 85));	
			pnlContract.setBorder(JTBorderFactory.createTitleBorder("Contract Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				pnlContractDetailsA = new JPanel(new BorderLayout(0, 0));
				pnlContract.add(pnlContractDetailsA, BorderLayout.WEST);
				pnlContractDetailsA.setPreferredSize(new java.awt.Dimension(200, 80));
				{
					pnlContractDetails1 = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlContractDetailsA.add(pnlContractDetails1, BorderLayout.WEST);
					pnlContractDetails1.setPreferredSize(new java.awt.Dimension(65, 80));
					{
						lblProject = new JLabel("Project", JLabel.CENTER);
						pnlContractDetails1.add(lblProject);
						lblProject.setEnabled(true);

						lblUnit = new JLabel("Unit", JLabel.CENTER);
						pnlContractDetails1.add(lblUnit);
						lblUnit.setEnabled(true);
					}
					
					pnlContractDetails2  = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlContractDetailsA.add(pnlContractDetails2, BorderLayout.CENTER);
					pnlContractDetails2.setPreferredSize(new java.awt.Dimension(135, 80));
					{
						txtProject = new JXTextField("");
						pnlContractDetails2.add(txtProject);
						txtProject.setEnabled(true);	
						txtProject.setEditable(false);	
						txtProject.setHorizontalAlignment(JTextField.CENTER);	

						txtUnit = new JXTextField("");
						pnlContractDetails2.add(txtUnit);
						txtUnit.setEnabled(true);	
						txtUnit.setEditable(false);	
						txtUnit.setHorizontalAlignment(JTextField.CENTER);
					}
					
				}
				pnlContractDetailsB = new JPanel(new BorderLayout(0, 0));
				pnlContract.add(pnlContractDetailsB, BorderLayout.WEST);
				pnlContractDetailsB.setPreferredSize(new java.awt.Dimension(400, 80));
				{
					pnlContractDetails3 = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlContractDetailsB.add(pnlContractDetails3, BorderLayout.WEST);
					pnlContractDetails3.setPreferredSize(new java.awt.Dimension(100, 80));
					{

						lblContractor = new JLabel("Contractor", JLabel.CENTER);
						pnlContractDetails3.add(lblContractor);
						lblContractor.setEnabled(true);

						lblContractNo = new JLabel("Contract No.", JLabel.CENTER);
						pnlContractDetails3.add(lblContractNo);
						lblContractNo.setEnabled(true);
					}
					pnlContractDetails4  = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlContractDetailsB.add(pnlContractDetails4, BorderLayout.CENTER);
					pnlContractDetails4.setPreferredSize(new java.awt.Dimension(300, 80));
					{
						txtContractor = new JXTextField("");
						pnlContractDetails4.add(txtContractor);
						txtContractor.setEnabled(true);	
						txtContractor.setEditable(false);	
						txtContractor.setHorizontalAlignment(JTextField.CENTER);	

						txtContractNo = new JXTextField("");
						pnlContractDetails4.add(txtContractNo);
						txtContractNo.setEnabled(true);	
						txtContractNo.setEditable(false);	
						txtContractNo.setHorizontalAlignment(JTextField.CENTER);
					}
				}
			}
								
			
			//repair details
			pnlRepair = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlNorth.add(pnlRepair, BorderLayout.CENTER);				
			pnlRepair.setPreferredSize(new java.awt.Dimension(900, 85));
			pnlRepair.setBorder(JTBorderFactory.createTitleBorder("Repair Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				pnlRepairDetailsA = new JPanel(new BorderLayout(0, 0));
				pnlRepair.add(pnlRepairDetailsA, BorderLayout.WEST);
				pnlRepairDetailsA.setPreferredSize(new java.awt.Dimension(300, 80));
				{
					pnlRepairDetails1 = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlRepairDetailsA.add(pnlRepairDetails1, BorderLayout.WEST);
					pnlRepairDetails1.setPreferredSize(new java.awt.Dimension(100, 80));
					{

						lblRepairNo = new JLabel("Repair No.", JLabel.CENTER);
						pnlRepairDetails1.add(lblRepairNo);
						lblRepairNo.setEnabled(true);

						lblRepairDate = new JLabel("Repair Date", JLabel.CENTER);
						pnlRepairDetails1.add(lblRepairDate);
						lblRepairDate.setEnabled(true);
						
						lblRepairAmt = new JLabel("Repair Amount", JLabel.CENTER);
						pnlRepairDetails1.add(lblRepairAmt);
						lblRepairAmt.setEnabled(true);
					}
					pnlRepairDetails2 = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlRepairDetailsA.add(pnlRepairDetails2, BorderLayout.CENTER);
					pnlRepairDetails2.setPreferredSize(new java.awt.Dimension(200, 80));
					{

						txtRepairNo = new JXTextField("");
						pnlRepairDetails2.add(txtRepairNo);
						txtRepairNo.setEnabled(true);
						txtRepairNo.setEditable(false);

						txtRepairDate = new JXTextField("");
						pnlRepairDetails2.add(txtRepairDate);
						txtRepairDate.setEnabled(true);
						txtRepairDate.setEditable(false);
						
						txtRepairAmt = new JXTextField("");
						pnlRepairDetails2.add(txtRepairAmt);
						txtRepairAmt.setEnabled(true);
						txtRepairAmt.setEditable(false);
					}
					
				}
				
			
				pnlRepairDetailsB = new JPanel(new BorderLayout(0, 0));
				pnlRepair.add(pnlRepairDetailsB, BorderLayout.CENTER);
				pnlRepairDetailsB.setPreferredSize(new java.awt.Dimension(300, 80));
				{
					pnlRepairDetails3 = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlRepairDetailsB.add(pnlRepairDetails3, BorderLayout.WEST);
					pnlRepairDetails3.setPreferredSize(new java.awt.Dimension(80, 80));
					{

						lblPayee = new JLabel("Payee", JLabel.CENTER);
						pnlRepairDetails3.add(lblPayee);
						lblPayee.setEnabled(true);

						lblPayeeType = new JLabel("Payee Type", JLabel.CENTER);
						pnlRepairDetails3.add(lblPayeeType);
						lblPayeeType.setEnabled(true);
						
						lblRepairRPLF = new JLabel("RPLF No.", JLabel.CENTER);
						pnlRepairDetails3.add(lblRepairRPLF);
						lblRepairAmt.setEnabled(true);
					}
					pnlRepairDetails4 = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlRepairDetailsB.add(pnlRepairDetails4, BorderLayout.CENTER);
					pnlRepairDetails4.setPreferredSize(new java.awt.Dimension(215, 80));
					{

						txtPayee = new JXTextField("");
						pnlRepairDetails4.add(txtPayee);
						txtPayee.setEnabled(true);
						txtPayee.setEditable(false);

						txtPayeeType = new JXTextField("");
						pnlRepairDetails4.add(txtPayeeType);
						txtPayeeType.setEnabled(true);
						txtPayeeType.setEditable(false);
						
						txtRepairRPLF = new JXTextField("");
						pnlRepairDetails4.add(txtRepairRPLF);
						txtRepairRPLF.setEnabled(true);
						txtRepairRPLF.setEditable(false);
					}
				}
				
				pnlRepairDetailsC = new JPanel(new BorderLayout(5, 5));
				pnlRepair.add(pnlRepairDetailsC, BorderLayout.CENTER);
				pnlRepairDetailsC.setPreferredSize(new java.awt.Dimension(300, 80));
				{
					pnlRepairDetails5 = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlRepairDetailsC.add(pnlRepairDetails5, BorderLayout.WEST);
					pnlRepairDetails5.setPreferredSize(new java.awt.Dimension(100, 80));
					{

						lblChargeTo = new JLabel("Charge To", JLabel.CENTER);
						pnlRepairDetails5.add(lblChargeTo);
						lblChargeTo.setEnabled(true);

						lblChargeAmt = new JLabel("Charge Amount", JLabel.CENTER);
						pnlRepairDetails5.add(lblChargeAmt);
						lblChargeAmt.setEnabled(true);
						
						lblStatus = new JLabel("Repair Status", JLabel.CENTER);
						pnlRepairDetails5.add(lblStatus);
						lblStatus.setEnabled(true);
					}
					pnlRepairDetails4 = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlRepairDetailsC.add(pnlRepairDetails4, BorderLayout.CENTER);
					pnlRepairDetails4.setPreferredSize(new java.awt.Dimension(200, 80));
					{

						txtChargeTo = new JXTextField("");
						pnlRepairDetails4.add(txtChargeTo);
						txtChargeTo.setEnabled(true);
						txtChargeTo.setEditable(false);

						txtChargeAmt = new JXTextField("");
						pnlRepairDetails4.add(txtChargeAmt);
						txtChargeAmt.setEnabled(true);
						txtChargeAmt.setEditable(false);
						
						txtStatus = new JXTextField("");
						pnlRepairDetails4.add(txtStatus);
						txtStatus.setEnabled(true);
						txtStatus.setEditable(false);
						
					}
				}
				pnlRepairDetailsD = new JPanel(new BorderLayout(10,10));
				pnlMain.add(pnlRepairDetailsD, BorderLayout.SOUTH);
				pnlRepairDetailsD.setPreferredSize(new java.awt.Dimension(400, 60));
				
				{
					pnlRepairDetailLbl = new JPanel(new GridLayout(1, 1, 10, 10));
					pnlRepairDetailsD.add(pnlRepairDetailLbl, BorderLayout.WEST);
					pnlRepairDetailLbl.setPreferredSize(new java.awt.Dimension(150, 60));
					{
						lblRepairDesc = new JLabel("Repair Description", JLabel.CENTER);
						pnlRepairDetailLbl.add(lblRepairDesc);
						lblRepairDesc.setEnabled(true);
						
					}
					pnlRepairDesc = new JPanel(new GridLayout(1, 1, 10, 10));
					pnlRepairDetailsD.add(pnlRepairDesc, BorderLayout.CENTER);
					pnlRepairDesc.setPreferredSize(new java.awt.Dimension(250, 60));
					{
						txtRepairDesc = new JTextArea("");
						pnlRepairDesc.add(txtRepairDesc);
						txtRepairDesc.setEnabled(true);
						txtRepairDesc.setEditable(false);
						txtRepairDesc.setBorder(_LINE_BORDER);
					}
															
				}
					
				

			}
		}
	}
	

}

	private Integer sql_getBackchargesNo() {

		Integer nxt_bc_no = 0;

		String SQL = 
			"select coalesce(rec_id,0) + 1  from co_house_repair_backcharges " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {nxt_bc_no = 1;}
			else {nxt_bc_no = (Integer) db.getResult()[0][0]; }

		}else{
			nxt_bc_no = 1;
		}

		return nxt_bc_no;
	}

	private String getEntityID(String entity_name){

		String entity_id = "";

		String SQL = 
			"select trim(entity_id) from rf_entity where upper(trim(entity_name)) = '"+entity_name+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			entity_id = (String) db.getResult()[0][0];
		}else{
			entity_id = null;
		}

		return entity_id;
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public static void display_hrdetails(String entity_id, String proj_id, String pbl_id, String seq_no) {
		
		pgSelect db = new pgSelect();
		//String SQL = "SELECT * FROM view_card_house_repair('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"')";
		//String SQL = "select * from co_house_repair a left join co_house_repair_backcharges b on a.payee_id = b.entity_id where a.pbl_id = '"+pbl_id+"'";
		String SQL = "select d.proj_name, \r\n"+ //0
					 "c.description, \r\n"+ //1
					 "a.pbl_id, \r\n"+ //2
					 "a.repair_no, \r\n"+ //3
					 "a.payee_id, \r\n"+ //4
					 "a.payee_type_id, \r\n"+ //5
					 "a.repair_date, \r\n"+ //6
					 "a.net_amt as repair_amt, \r\n"+ //7
					 "a.remarks, \r\n"+ //8
					 "a.rplf_no, \r\n"+ //9
					 "h.ntp_no, \r\n"+ //10
					 "h.contract_no, \r\n"+ //11
					 "h.contractor as contractor_name, \r\n" + //12
					 "i.entity_id  as contractor_id, \r\n" +  //13
					 "(case when g.date_paid is not null then 'RELEASED' else " + 
						"(case when a.status_id = 'I' then 'INACTIVE' else  " + 
							" (case when trim(e.status_desc) is not null then trim(e.status_desc) else " + 
								 "'ACTIVE' end) end) end) as status, \r\n"+ //14
					"m.entity_alias as payee, \r\n"+  //15
					"n.entity_name as charged_to, \r\n"+ //16
					"o.amount as charged_amount \r\n"+ //17
					"from co_house_repair a \r\n" +
					"left join co_house_repair_backcharges b on a.payee_id = b.entity_id \r\n" +
					"left join rf_entity bb on b.entity_id = bb.entity_id \r\n" +
					"left JOIn mf_unit_info c on a.pbl_id = c.pbl_id \r\n" +
					"left join mf_project d on d.proj_id = c.proj_id \r\n" +
					"left join co_house_repair_backcharges dd on a.repair_no = dd.repair_no \r\n" +
					"left join mf_entity_type cc on dd.entity_type_id = cc.entity_type_id \r\n" +			
					"left join (select * from rf_pv_header where status_id != 'I') f on a.rplf_no = f.pv_no \r\n" +
					"left join rf_request_header ff on a.rplf_no = ff.rplf_no \r\n" + 
					"left join mf_record_status e on f.status_id = e.status_id \r\n" + 
					"left join rf_cv_header g on f.cv_no = g.cv_no \r\n" +
					"left join co_ntp_accomplishment h on h.pbl_id = a.pbl_id \r\n" + 
					"left join co_ntp_header i on  i.ntp_no = h.ntp_no \r\n" +
					"left join mf_unit_info k on k.pbl_id = h.pbl_id \r\n" +
					"left join mf_project l on l.proj_id = k.proj_id \r\n" + 
					"left join rf_entity m on m.entity_id = a.payee_id \r\n" + 
					"left join rf_entity n on n.entity_id = i.entity_id \r\n" + 
					"left join co_house_repair_backcharges o on o.repair_no = a.repair_no \r\n" + 
					"where h.contract_no !~*('MIS') and a.pbl_id = '"+pbl_id+"'";
		
		db.select(SQL);
		
		FncSystem.out("Display SQL for House Repair", SQL);
		
		if(db.isNotNull()){
			txtContractNo.setText(db.getResult()[0][11].toString());
			txtContractor.setText(db.getResult()[0][12].toString());
			
			txtProject.setText(db.getResult()[0][0].toString());
			txtUnit.setText(db.getResult()[0][1].toString());
			
			txtRepairNo.setText(db.getResult()[0][3].toString());
			txtRepairDate.setText(db.getResult()[0][6].toString());
			txtRepairAmt.setText(db.getResult()[0][7].toString()); 
			
			txtRepairRPLF.setText(db.getResult()[0][9].toString());
			txtPayee.setText(db.getResult()[0][15].toString()); 
			txtPayeeType.setText(db.getResult()[0][4].toString());
			
			txtChargeTo.setText(db.getResult()[0][16].toString());
			txtChargeAmt.setText(db.getResult()[0][17].toString());
			txtStatus.setText(db.getResult()[0][14].toString());
			
			txtRepairDesc.setText(db.getResult()[0][8].toString()); 
						
		}
		
		/*if(db.isNotNull()){
			txtContractNo.setText(db.getResult()[0][15].toString());
			txtContractor.setText(db.getResult()[0][13].toString());
			
			txtProject.setText(db.getResult()[0][17].toString());
			txtUnit.setText(db.getResult()[0][16].toString());
			
			txtRepairNo.setText(db.getResult()[0][0].toString());
			txtRepairDate.setText(db.getResult()[0][1].toString());
			txtRepairAmt.setText(db.getResult()[0][2].toString()); 
			
			txtRepairRPLF.setText(db.getResult()[0][3].toString());
			txtPayee.setText(db.getResult()[0][4].toString()); 
			txtPayeeType.setText(db.getResult()[0][5].toString());
			
			txtChargeTo.setText(db.getResult()[0][6].toString());
			txtChargeAmt.setText(db.getResult()[0][8].toString());
			txtStatus.setText(db.getResult()[0][9].toString());
			
			txtRepairDesc.setText(db.getResult()[0][11].toString()); 
						
		}*/
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
}