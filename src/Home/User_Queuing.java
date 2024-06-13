package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components._JTableMain;
import tablemodel.modelQueuingSystem;
import tablemodel.modelUser_Queue;

public class User_Queuing extends JXPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color green = new Color(0,128,0);
	Border linecolor = BorderFactory.createLineBorder(Color.BLACK);
	TitledBorder title1 = BorderFactory.createTitledBorder(linecolor, "For Process");
	TitledBorder title2 = BorderFactory.createTitledBorder(linecolor, "Ongoing Process");
	TitledBorder title3 = BorderFactory.createTitledBorder(linecolor, "Finished Process");
	
	private JPanel pnlMain;
	private JPanel pnlQueueTable;
	private JScrollPane scrollQueue;
	private modelUser_Queue modelQueue;
	private _JTableMain tblQueue;
	private JPanel pnlCenter;
	private JPanel pnlOngoingProc;
	private JScrollPane scrollOngoingProc;
	private modelUser_Queue modelOngoingProc;
	private _JTableMain tblOngoingProc;
	private JPanel pnlFinishedProc;
	private JScrollPane scrollFinishedProc;
	private modelUser_Queue modelFinishedProc;
	private _JTableMain tblFinishedProc;
	private Timer timerReload;

	public User_Queuing() {
		initGUI();
	}

	private void initGUI() {
		 this.setLayout(new BorderLayout(1, 1));
         this.setOpaque(true);
         this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
         this.setBackground(new Color(0, 0, 0, 0));
         {
        	 pnlMain = new JPanel(new BorderLayout(2,2));
        	 this.add(pnlMain);
        	 pnlMain.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        	 pnlMain.setOpaque(false);
        	 pnlMain.setBorder(components._JTBorderFactory.createTitleBorder("QUEUING SYSTEM"));
        	 {
        		 pnlQueueTable = new JPanel(new BorderLayout(2,2));
        		 pnlMain.add(pnlQueueTable, BorderLayout.WEST);
        		 pnlQueueTable.setPreferredSize(new Dimension(300,0));
        		 pnlQueueTable.setBorder(title1);
        		 title1.setTitleColor(green);
        		 {
        			 scrollQueue = new JScrollPane();
        			 pnlQueueTable.add(scrollQueue);
        			 scrollQueue.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        		 }
        		 {
        			 modelQueue = new modelUser_Queue();
        			 tblQueue = new _JTableMain(modelQueue);

        			 scrollQueue.setViewportView(tblQueue);
        			 tblQueue.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        			 tblQueue.getColumn(0).setPreferredWidth(102);
        			 //tblQueue.setRowHeight(35);
        			 tblQueue.setEnabled(false);
        			 tblQueue.hideColumns("Time Finished");
        			 tblQueue.setFont(new Font("SanSerif", Font.PLAIN, 10));
        		 }
        	 }
        	 {
        		 pnlCenter = new JPanel(new GridLayout(2,1,2,2));
        		 pnlMain.add(pnlCenter, BorderLayout.CENTER);
        		 {
        			 pnlOngoingProc = new JPanel(new BorderLayout(2,2));
        			 pnlCenter.add(pnlOngoingProc, BorderLayout.CENTER);
        			 pnlOngoingProc.setBorder(title2);
        			 title2.setTitleColor(green);
        			 {
            			 scrollOngoingProc = new JScrollPane();
            			 pnlOngoingProc.add(scrollOngoingProc);
            			 scrollOngoingProc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            		 }
            		 {
            			 modelOngoingProc = new modelUser_Queue();
            			 tblOngoingProc = new _JTableMain(modelOngoingProc);

            			 scrollOngoingProc.setViewportView(tblOngoingProc);
            			 tblOngoingProc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            			 tblOngoingProc.getColumn(0).setPreferredWidth(250);
            			 //tblQueue.setRowHeight(35);
            			 tblOngoingProc.setEnabled(false);
            			 tblOngoingProc.hideColumns("Time of Arrival");
            			 tblOngoingProc.hideColumns("Time Finished");
            			 tblOngoingProc.hideColumns("Process");
            			 tblOngoingProc.setFont(new Font("SanSerif", Font.PLAIN, 10));
            		 }
        		 }
        		 {
        			 pnlFinishedProc = new JPanel(new BorderLayout(2,2));
        			 pnlCenter.add(pnlFinishedProc, BorderLayout.SOUTH);
        			 pnlFinishedProc.setBorder(title3);
        			 title3.setTitleColor(green);
        			 {
            			 scrollFinishedProc = new JScrollPane();
            			 pnlFinishedProc.add(scrollFinishedProc);
            			 scrollFinishedProc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            		 }
            		 {
            			 modelFinishedProc= new modelUser_Queue();
            			 tblFinishedProc = new _JTableMain(modelFinishedProc);

            			 scrollFinishedProc.setViewportView(tblFinishedProc);
            			 tblFinishedProc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            			 tblFinishedProc.getColumn(0).setPreferredWidth(80);
            			 //tblQueue.setRowHeight(35);
            			 tblFinishedProc.setEnabled(false);
            			 //tblFinishedProc.hideColumns("Time of Arrival");
            			 tblFinishedProc.hideColumns("Process");
            			 tblFinishedProc.setFont(new Font("SanSerif", Font.PLAIN, 10));
            		 }
        		 }
        	 }
         }
         
         initialize_comp();
         Reload();
         Reload2();
	}
	
	private void initialize_comp(){
		
		displayQueueNumbers(modelQueue, UserInfo.Department);
		displayOngoingProcess(modelOngoingProc, UserInfo.Department);
		displayFinishedProcess(modelFinishedProc, UserInfo.Department);
	
	}
	
	private void displayQueueNumbers(DefaultTableModel model, String dept_code) {
		FncTables.clearTable(model);
		//DefaultListModel listModel = new DefaultListModel();
		//rowHeader.setModel(listModel);
		
		//String sql = "select CONCAT(dept_alias, proc_no) as \"proc_no\" from rf_queuing_system where dept_code = '"+dept_code+"' and status_id = 'A' order by date_created ASC";
		String sql =
				"select \n" + 
				"CONCAT(dept_alias, proc_no) as \"proc_no\",\n" + 
				"to_char((select cast (date_created::timestamp as time)), 'HH12:MI AM') as toa,\n" + 
				"to_char((select cast (date_finished::timestamp as time)), 'HH12:MI AM') as tof,\n" +
				"(	\n" + 
				"	case	\n" + 
				"			when order_no = 1 and fwd_to_dept = '52' then 'FF'\n" + 
				"			when order_no = 2 and fwd_to_dept = '52' then 'PF'\n" + 
				"			when order_no = 3 and fwd_to_dept = '52' then 'REM'\n" + 
				"			when order_no = 4 and fwd_to_dept = '52' then 'IHF'\n" + 
				"			when order_no = 1 and fwd_to_dept = '02' then 'PAYMENT'\n" + 
				"			when order_no = 1 and fwd_to_dept = '54' then 'COLLECTION'\n" + 
				"			when order_no = 1 and fwd_to_dept = '08' then 'PROPERTY'\n" + 
				"			else null end\n" + 
				") as process\n" +
				"from rf_queuing_system\n" + 
				"where fwd_to_dept = '"+dept_code+"'\n" + 
				"and status_id = 'A'\n" + 
				"and date_created::date = now()::date\n" + 
				"\n" + 
				"union all\n" + 
				"\n" + 
				"(select\n" + 
				"CONCAT(dept_alias, proc_no) as \"proc_no\",\n" + 
				"to_char((select cast (date_created::timestamp as time)), 'HH12:MI AM') as toa,\n" + 
				"to_char((select cast (date_finished::timestamp as time)), 'HH12:MI AM') as tof,\n" +
				"(\n" + 
				"	case	\n" + 
				"			when order_no = 1 and dept_code = '52' then 'FF'\n" + 
				"			when order_no = 2 and dept_code = '52' then 'PF'\n" + 
				"			when order_no = 3 and dept_code = '52' then 'REM'\n" + 
				"			when order_no = 4 and dept_code = '52' then 'IHF'\n" + 
				"			when order_no = 1 and dept_code = '02' then 'PAYMENT'\n" + 
				"			when order_no = 1 and dept_code = '54' then 'COLLECTION'\n" + 
				"			when order_no = 1 and dept_code = '08' then 'PROPERTY'\n" + 
				"			else null end\n" + 
				") as process\n" +
				"from rf_queuing_system\n" + 
				"where dept_code = '"+dept_code+"'\n" + 
				"and status_id = 'A'\n" + 
				"and fwd_to_dept is null\n" + 
				"and date_created::date = now()::date\n" + 
				"order by date_created ASC)";
		
		//FncSystem.out("List of queue", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				model.addRow(db.getResult()[x]);
				//listModel.addElement(model.getRowCount());
			}
		}
	}
	
	private void displayOngoingProcess(DefaultTableModel model, String dept_code) {
		FncTables.clearTable(model);
		
		String sql =
				"select\n" + 
				"concat(dept_alias, proc_no) as \"proc_no\"\n" + 
				"from rf_queuing_system \n" + 
				"where status_id = 'P' \n" + 
				"and date_finished is null \n" + 
				"and\n" + 
				"	case\n" + 
				"		when fwd_to_dept is null then dept_code = '"+dept_code+"'\n" + 
				"		else fwd_to_dept = '"+dept_code+"'\n" + 
				"	end\n" + 
				"and date_created::date = now()::date\n" + 
				"order by date_created ASC";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				model.addRow(db.getResult()[x]);
				//listModel.addElement(model.getRowCount());
			}
		}
	}
	
	private void displayFinishedProcess(DefaultTableModel model, String dept_code) {
		FncTables.clearTable(model);
		
		String sql =
				"select\n" + 
				"concat(dept_alias, proc_no) as \"proc_no\",\n" + 
				"to_char((select cast (date_created::timestamp as time)), 'HH12:MI AM') as toa,\n" +
				"to_char((select cast (date_finished::timestamp as time)), 'HH12:MI AM') as tof\n" +
				"from rf_queuing_system \n" + 
				"where status_id = 'I' \n" + 
				"and date_finished::date = current_date \n" + 
				"and\n" + 
				"	case\n" + 
				"		when fwd_to_dept is null then dept_code = '"+dept_code+"'\n" + 
				"		else fwd_to_dept = '"+dept_code+"'\n" + 
				"	end\n" + 
				"order by date_finished DESC";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				model.addRow(db.getResult()[x]);
				//listModel.addElement(model.getRowCount());
			}
		}
	}
	
	private void Reload() {
		TimerTask tmr = new TimerTask() {

			public void run() {
				
				displayQueueNumbers(modelQueue, UserInfo.Department);
				displayOngoingProcess(modelOngoingProc, UserInfo.Department);
				//displayFinishedProcess(modelFinishedProc, UserInfo.Department);
			}
		};

		timerReload = new Timer();
		long delay = 0;
		long intevalPeriod = 2000; 
	
		timerReload.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}
	
	private void Reload2() {
		TimerTask tmr = new TimerTask() {

			public void run() {
				
				displayFinishedProcess(modelFinishedProc, UserInfo.Department);
			}
		};

		timerReload = new Timer();
		long delay = 0;
		long intevalPeriod = 10000; 
	
		timerReload.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}
	
//	private String getDeptCode(String emp_code) {
//		
//		String dept_code = "";
//		
//		String sql = "select dept_code from em_employee where emp_code = '"+emp_code+"'";
//		
//		FncSystem.out("SQL", sql);
//		pgSelect db = new pgSelect();
//		db.select(sql);
//		
//		if(db.isNotNull()) {
//			dept_code = db.getResult()[0].toString();
//		}else {
//			dept_code = "";
//		}
//		return dept_code;
//	}

}
