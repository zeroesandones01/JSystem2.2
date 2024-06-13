package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Database.pgSelect;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncSystem;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_ChangeDueDate extends JPanel implements _GUI{

	private static final long serialVersionUID = -967483198056392946L;

	private JPanel pnlMain;
	private JPanel pnlWest;
	private JLabel lblDPDueDate;
	private JPanel pnlCenter;
	private _JDateChooser dteDPDueDate;
	private JLabel lblMADueDate;
	private _JDateChooser dteMADueDate;

	private JPanel pnlDPMA;
	private JPanel pnlNewDueDate;

	private JComboBox cmbNewDueDate;
	private JLabel lblNewSchedDate;

	private pnlOtherRequest_OldData od;

	public pnlOtherReq_ChangeDueDate(pnlOtherRequest_OldData od) {
		this.od = od;
		initGUI();
	}

	public pnlOtherReq_ChangeDueDate(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOtherReq_ChangeDueDate(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ChangeDueDate(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(200, 200));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.NORTH);
			{
				pnlWest = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblDPDueDate = new JLabel("First DP Due Date");
					pnlWest.add(lblDPDueDate);
				}
				{
					lblNewSchedDate = new JLabel("New Schedule Date");
					pnlWest.add(lblNewSchedDate);
				}
			}
			{
				pnlCenter = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlMain.add(pnlCenter);
				{
					pnlDPMA = new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlDPMA);
					{
						dteDPDueDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlDPMA.add(dteDPDueDate, BorderLayout.WEST);
						dteDPDueDate.setPreferredSize(new Dimension(100, 0));;
						/*dteDPDueDate.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent arg0) {
								Date new_dp_due_date = ((_JDateChooser) arg0.getSource()).getDate();
								
								setNewScheDate(new_dp_due_date);
							}
						});*/
						dteDPDueDate.addDateListener(new DateListener() {
							
							@Override
							public void datePerformed(DateEvent event) {
								Date new_dp_due_date = ((_JDateChooser) event.getSource()).getDate();
								
								setNewSchedDate(new_dp_due_date);
								
							}
						});
						//dteDPDueDate.setSelectableDateRange(see, max);
					}
					/*{
						lblMADueDate = new JLabel("New MA Due Date", JLabel.TRAILING);
						pnlDPMA.add(lblMADueDate, BorderLayout.CENTER);
					}
					{
						dteMADueDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlDPMA.add(dteMADueDate, BorderLayout.EAST);
						dteDPDueDate.setPreferredSize(new Dimension(100, 0));
					}*/
				}
				{
					pnlNewDueDate = new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlNewDueDate);
					{
						cmbNewDueDate = new JComboBox(new String[] {"07", "14", "21", "28"});
						pnlNewDueDate.add(cmbNewDueDate, BorderLayout.WEST);
						cmbNewDueDate.setPreferredSize(new Dimension(100, 0));
						cmbNewDueDate.setEnabled(false);
					}
				}
			}
		}
	}//END OF INIT GUI

	public void setAvailableDPDueDate(){
		Object []data1 = od.getDataOld();
		String old_entity_id = (String) data1[0];
		String old_proj_id = (String) data1[4];
		String old_unit_id = (String) data1[5];
		String old_seq_no = (String) data1[26];
		
		pgSelect db = new pgSelect();
		String sql = "select is_full_dp_paid('"+old_entity_id+"', getinteger('"+old_unit_id+"')::VARCHAR, "+old_seq_no+", '"+old_proj_id+"')";

		db.select(sql);
		FncSystem.out("Display is full dp paid", sql);
		if((Boolean) db.getResult()[0][0] == true){
			dteDPDueDate.setEnabled(false);
		}else{
			String sqldpdate = "select get_next_dp_due_date('"+old_entity_id+"', getinteger('"+old_unit_id+"')::VARCHAR, "+old_seq_no+", '"+old_proj_id+"')";
			db.select(sqldpdate);
			FncSystem.out("Display new dp due date", sqldpdate);
			Date min_dp_due_date = (Date) db.getResult()[0][0];
			System.out.printf("Display min dp due date %s%n", min_dp_due_date);
			dteDPDueDate.setMinSelectableDate(min_dp_due_date);
			dteDPDueDate.setDate(min_dp_due_date);
			//dteDPDueDate.setEnabled(false);
		}
	}

	public void setAvailableMADueDate(){
		Object [] data1 = od.getDataOld();
		String old_entity_id = (String) data1[0];
		String old_proj_id = (String) data1[4];
		String old_unit_id = (String) data1[5];
		String old_seq_no = (String) data1[26];

		pgSelect db = new pgSelect();
		String sql = "select is_full_settled('"+old_entity_id+"', getinteger('"+old_unit_id+"')::VARCHAR, "+old_seq_no+", '"+old_proj_id+"')";
		db.select(sql);
		FncSystem.out("Display is full settle", sql);
		if(db.getResult()[0][0].equals("t")){
			dteMADueDate.setEditable(false);
		}else{
			String sqlmadate = "select get_next_ma_due_date('"+old_entity_id+"', getinteger('"+old_unit_id+"')::VARCHAR, "+old_seq_no+", '"+old_proj_id+"')";
			db.select(sqlmadate);
			FncSystem.out("Display new ma Sched date", sqlmadate);
			Date min_ma_due_date = (Date) db.getResult()[0][0];
			dteMADueDate.setDate(min_ma_due_date);
			dteMADueDate.setEnabled(false);//ADDED
		}
	}
	
	public void editChangeDueDate(){
		//cmbNewDueDate.setEnabled(true);
		dteDPDueDate.setEnabled(true);
	}

	/*public void setDefaultDPMAdate(Object [] data3){
		Integer final_dpterm = (Integer) data3[20];
		//if(dteDPDueDate.isEditable()){
			Calendar calen = Calendar.getInstance();
			calen.setTime(dteDPDueDate.getDate());
			calen.add(Calendar.MONTH, final_dpterm);
			dteMADueDate.setDate(calen.getTime());
			System.out.printf("Display Default Date %s%n", calen.getTime());
			//}
	}*/
	
	public void setNewSchedDate(Date new_dp_due_date){
		Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeDueDate());
		
		String final_buyer_type = (String) data3[8];
		String final_pmt_scheme_id = (String) data3[9];
		String old_proj_id = (String) data3[34];
		
		pgSelect db = new pgSelect();
		
		//String sql = "select EXTRACT ('Day' FROM get_schedule_date('"+old_proj_id+"', '"+new_dp_due_date+"' ,'M', 1, '"+final_buyer_type+"')::DATE)::VARCHAR;\n";
		
		String sql = "select EXTRACT('Day' FROM get_schedule_date('"+old_proj_id+"', '"+new_dp_due_date+"', a.int_unit, a.interval, '"+final_buyer_type+"'))::VARCHAR\n" + 
					 "from mf_pay_scheme_detail a\n" + 
					 "where a.pmt_scheme_id = '"+final_pmt_scheme_id+"'\n" + 
					 "and a.part_id in ('013', '014');";
		db.select(sql);
		FncSystem.out("Display New Sched Date", sql);
		
		if(db.isNotNull()){
			String dp_sched_date = (String) db.getResult()[0][0];
			String ma_sched_date = (String) db.getResult()[1][0];
			System.out.printf("Display DP Sched Date: %s%n", dp_sched_date);
			System.out.printf("Display MA Sched Date: %s%n", ma_sched_date);
			
			if(dp_sched_date.equals(ma_sched_date)){
				cmbNewDueDate.setSelectedItem(dp_sched_date);
			}
		}
	}

	public Date setNewDPDueDate(){//SETS THE NEW DP DUE DATE FOR THE 

		Date min_dp_due_date = dteDPDueDate.getDate();
		String new_dp_date = ((String) cmbNewDueDate.getSelectedItem()).trim();

		pgSelect db = new pgSelect();
		
		String sqlMADate = "SELECT FORMAT('%s-%s-%s', EXTRACT('Year' FROM '"+min_dp_due_date+"'::TIMESTAMP), \n"+
						   "EXTRACT('Month' FROM '"+min_dp_due_date+"'::TIMESTAMP), "+new_dp_date+")::TIMESTAMP; ";
		db.select(sqlMADate);
		FncSystem.out("Display new dp due date", sqlMADate);
		
		return (Date) db.getResult()[0][0];	
	}
	
	public Date setNewMADueDate(){//
		Date min_ma_due_date = dteMADueDate.getDate();
		String new_ma_due_date = ((String) cmbNewDueDate.getSelectedItem()).trim();
		
		pgSelect db = new pgSelect();
		String sql = "SELECT FORMAT('%s-%s-%s', EXTRACT('Year' FROM '"+min_ma_due_date+"'::TIMESTAMP), \n"+
					 "EXTRACT('Month' FROM '"+min_ma_due_date+"'::TIMESTAMP), "+new_ma_due_date+")::TIMESTAMP; ";
		db.select(sql);
		
		FncSystem.out("Display new MA DUe Date", sql);
		return (Date) db.getResult()[0][0];
	}

	public Object[] getDataChangeDueDate(){
		return new Object[]{"01", //req_id 0
				null, //new_entity_id 1
				null, //new_entity_lname 2
				null, //new entity_fname 3
				null, //new_entity_mname 4
				null, //new unit_id 5
				null, //new_house_model 6
				null, //new_agent_id 7
				null,  //new_buyer_type 8
				null, //new_pmt_scheme 9
				new BigDecimal("0.00"), //new_sprice 10
				new BigDecimal("0.00"), //new_disc_amt 11
				new BigDecimal("0.00"), //new_disc_rate 12
				new BigDecimal("0.00"), //13
				new BigDecimal("0.00"),//new dp_amt 14
				new BigDecimal("0.00"), //new_dp_rate 15
				"", //null, //new_dp_terms //16
				new BigDecimal("0.00"), //17 new_availed_amt
				new BigDecimal("0.00"), //18 new_availed_rate
				"", //null, //new ma_terms //19 
				(Date) dteDPDueDate.getDate(), //new_dp_due_date 20 //
				null, //new_ma_due_date 21
				"", //22 new_lot_area
				new BigDecimal("0.00"), //23 new_fire_amt
				new BigDecimal("0.00"), //24 new_int_rate
				new BigDecimal("0.00"), //25 new_vat_amt
				new BigDecimal("0.00"), //26 new_vat_rate
				((String) cmbNewDueDate.getSelectedItem()).trim(),
				"",
				""}; 

	}

	public void displayChangeDueDate(Date new_dp_duedate, Date new_ma_duedate){
		dteDPDueDate.setDate(new_dp_duedate);
		dteDPDueDate.setEnabled(false);
		/*dteMADueDate.setDate(new_ma_duedate);
		dteMADueDate.setEnabled(false);*/
		setNewSchedDate(new_dp_duedate);
		cmbNewDueDate.setEnabled(false);
	}

}
