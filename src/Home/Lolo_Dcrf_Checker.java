package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import System.DataChangeRequest;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_LoloDcrf_Checker;

public class Lolo_Dcrf_Checker extends JXPanel implements _GUI,ActionListener {

	private Border empty = BorderFactory.createEmptyBorder(1,1,1,1);
	private static final long serialVersionUID = 6584153642981872351L;

	private JLabel[] lblDcrf;
	private JLabel[] lblDept;
	private JButton[] btnPrev;
	private JButton[] btnApp;
	private JButton[] btnDisapp;
	private JScrollPane scrollPane;

	private JPanel pnlMain;
	private JPanel pnlScrollLbl;
	private JPanel pnlDept;
	static JPanel pnlTrueMain;
	private JPanel mainCenter;
	private JPanel pnlPrev;
	private JPanel pnlApp;
	private JPanel pnlDis;

	private TimerTask tmr;
	private Timer timerExecute;


	public Lolo_Dcrf_Checker (){
		initGUI();
	}

	public Lolo_Dcrf_Checker(boolean isDoubleBuffered){
		super(isDoubleBuffered);
		initGUI();

	}

	public Lolo_Dcrf_Checker(LayoutManager layout){
		super (layout);

	}

	public Lolo_Dcrf_Checker(LayoutManager layout,boolean isDoubleBuffered){
		super(layout,isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(1, 1));
		this.setOpaque(true);
		this.setBorder(empty);
		this.setBackground(new Color(0, 0, 0, 0));

		{
			pnlTrueMain = new JPanel(new BorderLayout());
			this.add(pnlTrueMain);
			pnlTrueMain.setPreferredSize(new Dimension(400, 200));
			pnlTrueMain.setBorder(components._JTBorderFactory.createTitleBorder("- \t DCRF Checker"));
			pnlTrueMain.setOpaque(false);

			{
				pnlMain = new JPanel(new BorderLayout(5,5));
				pnlMain.setBorder(new EmptyBorder(10,10,10,10));
				pnlMain.setOpaque(false);
				repeat();
				scrollPane = new JScrollPane(pnlMain);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				pnlTrueMain.add(scrollPane,BorderLayout.CENTER);
				scrollPane.setOpaque(false);

			}
			ExecuteUSB();
		}



	}

	private void repeat() {
		pnlMain.removeAll();
		System.out.println("Dumaan dito");

		pgSelect db = new pgSelect();
		db.select("select a.dcrf_no,b.dept_alias from rf_dcrf_header  a \n "+
				"left join mf_department b on a.dept_code = b.dept_code \n"+
				"left join  em_employee c on b.dept_code = c.dept_code and a.created_by = c.emp_code \n"+
				"left join rf_entity d on  c.entity_id = d.entity_id where status = 'APPROVED' and date_approved is not null \n"+
				"and date_deleted is null and authorized_date is null");

		lblDcrf = new JLabel[db.getRowCount()];
		lblDept = new JLabel[db.getRowCount()];
		btnPrev = new JButton[db.getRowCount()];
		btnApp = new JButton[db.getRowCount()];
		btnDisapp = new JButton[db.getRowCount()];

		int row_count = db.getRowCount();

		if(row_count<= 6) {
			row_count = 6;
		} else {
			row_count = db.getRowCount();
		}

		{
			JPanel pnlWest = new JPanel(new BorderLayout(5,5));
			pnlMain.add(pnlWest,BorderLayout.WEST);
			{
				pnlScrollLbl = new JPanel(new GridLayout(row_count,1,5,5));
				pnlWest.add(pnlScrollLbl,BorderLayout.CENTER);
				pnlScrollLbl.setBorder(new EmptyBorder(10,2,10,2));


				for (int x = 0; x<db.getRowCount(); x++){
					lblDcrf[x] = new JLabel("DCRF#"+db.getResult()[x][0].toString());
					pnlScrollLbl.add(lblDcrf[x]);

				}
			}
			{
				pnlDept = new JPanel(new GridLayout(row_count,1,5,5));
				pnlWest.add(pnlDept,BorderLayout.EAST);
				pnlDept.setBorder(new EmptyBorder(10,5,10,30));


				for (int x = 0; x<db.getRowCount(); x++){
					lblDept[x] = new JLabel("Dept: \t"+db.getResult()[x][1].toString());
					pnlDept.add(lblDept[x]);

				}
			}
		}

		JPanel mainEast = new JPanel(new GridLayout(1,3,5,5));
		mainEast.setPreferredSize(new Dimension(220,150));
		pnlMain.add(mainEast,BorderLayout.EAST);

		{
			pnlPrev = new JPanel(new GridLayout(row_count,1,5,5));
			pnlPrev.setBorder(new EmptyBorder(10,0,10,0));
			mainEast.add(pnlPrev,BorderLayout.CENTER);
			int row = db.getRowCount();
			for(int x = 0;x<row;x++){
				btnPrev[x] = new JButton("Preview");
				pnlPrev.add(btnPrev[x]);
				btnPrev[x].setActionCommand("Preview");
				btnPrev[x].addActionListener(this);
				btnPrev[x].setName(db.getResult()[x][0].toString());
				btnPrev[x].setFont(new Font("",Font.PLAIN,10));
			}
		}
		{
			pnlApp = new JPanel(new GridLayout(row_count,1,5,5));
			pnlApp.setBorder(new EmptyBorder(10,0,10,0));
			mainEast.add(pnlApp,BorderLayout.CENTER);
			for(int x = 0;x<db.getRowCount();x++){
				btnApp[x] = new JButton("Approve");
				pnlApp.add(btnApp[x]);
				btnApp[x].setActionCommand("Approve");
				btnApp[x].addActionListener(this);
				btnApp[x].setName(db.getResult()[x][0].toString());
				btnApp[x].setFont(new Font("",Font.PLAIN,10));
			}
		}
		{
			pnlDis = new JPanel(new GridLayout(row_count,1,5,5));
			pnlDis.setBorder(new EmptyBorder(10,0,10,0));
			mainEast.add(pnlDis,BorderLayout.EAST);
			for(int x = 0;x<db.getRowCount();x++){
				btnDisapp[x] = new JButton("Disapprove");
				pnlDis.add(btnDisapp[x]);
				btnDisapp[x].setActionCommand("Disapprove");
				btnDisapp[x].addActionListener(this);
				btnDisapp[x].setName(db.getResult()[x][0].toString());
				btnDisapp[x].setFont(new Font("",Font.PLAIN,10));

			}
		}

		pnlMain.repaint();
		pnlMain.revalidate();
	}

	private void preview(String dcrf){	

		Integer dcrf_no = Integer.parseInt(dcrf);
		String criteria = "DCRF";		
		String reportTitle = String.format("%s (%s)", DataChangeRequest.title.replace(" Report", ""), criteria.toUpperCase());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("dcrf_no",dcrf_no);
		FncReport.generateReport("/Reports/rptDCRF_preview.jasper", reportTitle,DataChangeRequest.company, mapParameters);
	}
	private void approved(String dcrf){

		Integer dcrf_no = Integer.parseInt(dcrf);
		pgUpdate db = new pgUpdate();

		{
			String query = "UPDATE rf_dcrf_header SET authorized_date = '"+FncGlobal.getDateToday()+"' WHERE dcrf_no = '"+dcrf_no+"'";
			db.executeUpdate(query, true);
		}
		db.commit();		

	}
	private void disApproved(String dcrf){

		Integer dcrf_no = Integer.parseInt(dcrf);
		pgUpdate db = new pgUpdate();

		{
			String query = "UPDATE rf_dcrf_header SET date_deleted = '"+FncGlobal.getDateToday()+"',status = 'INACTIVE' WHERE dcrf_no = '"+dcrf_no+"'";
			db.executeUpdate(query, true);
		}
		db.commit();

	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Preview")){

			preview((((JButton) e.getSource()).getName()));


		}



		if(e.getActionCommand().equals("Approve")){

			approved((((JButton) e.getSource()).getName()));

			JOptionPane.showMessageDialog(this, "dcrf#: \t"+ (((JButton) e.getSource()).getName())+"\t has been approved");
			this.removeAll();
			initGUI();

		}
		if(e.getActionCommand().equals("Disapprove")){
			disApproved((((JButton) e.getSource()).getName()));

			JOptionPane.showMessageDialog(this, "dcrf#: \t"+ (((JButton) e.getSource()).getName())+"\t has been disapproved");
			this.removeAll();
			initGUI();

		}
	}
	private void ExecuteUSB() {
		tmr = new TimerTask() {

			public void run() {
				repeat(); 
			}
		};

		timerExecute = new Timer();
		long delay = 0;
		long intevalPeriod = 20 * 1000; 

		timerExecute.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}


}
