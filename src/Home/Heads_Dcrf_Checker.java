package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Dialogs.dlg_dcrf_attachment_viewer;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import System.DataChangeRequest;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_HeadDcrf_Checker;


public class Heads_Dcrf_Checker extends JXPanel implements _GUI,ActionListener {

	private static final long serialVersionUID = -7213547798430965414L;
	private JPanel pnlMain,pnlMainCenter,pnlMainSouth; 
	private JButton btnPreview,btnApproved,btnDisApproved,btnAttach,btnConformed;
	static JPanel pnlTrueMain ;
	private JScrollPane scrollHead;
	private _JTableMain tblHeadDcrfChecker;
	private JList rowHeadDep;
	private model_HeadDcrf_Checker model_HeadDcrf_Checker;

	Integer dcrf_no;
	String department;
	String emp_name;
	String dcrfStatus;

	private TimerTask tmr;
	private Timer timerExecute;


	public Heads_Dcrf_Checker (){
		initGUI();
	}
	public Heads_Dcrf_Checker(boolean isDoubleBuffered){
		super(isDoubleBuffered);
		initGUI();

	}
	public Heads_Dcrf_Checker(LayoutManager layout){
		super (layout);

	}
	public Heads_Dcrf_Checker(LayoutManager layout,boolean isDoubleBuffered){
		super(layout,isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(1,1));
		//		this.setOpaque(false);
		//		this.setBorder(empty);
		this.setBackground(new Color(0,0,0,0));


		{
			pnlTrueMain = new JPanel(new BorderLayout(1,1));
			this.add(pnlTrueMain);
			pnlTrueMain.setPreferredSize(new Dimension(400, 200));
			pnlTrueMain.setBorder(components._JTBorderFactory.createTitleBorder("- \t DCRF Checker"));
			pnlTrueMain.setOpaque(false);

			{

				pnlMain = new JPanel(new BorderLayout(1,1));
				pnlTrueMain.add(pnlMain,BorderLayout.CENTER);
				pnlMain.setBorder(new EmptyBorder(5,5,5,5));
				pnlMain.setOpaque(false);
				pnlMain.removeAll();
				{
					pnlMainCenter = new JPanel(new BorderLayout(5,5));
					pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
					{
						scrollHead = new JScrollPane();
						pnlMainCenter.add(scrollHead,BorderLayout.CENTER);
						scrollHead.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollHead.setBorder(new EmptyBorder(5,5,5,5));

					}
					{
						model_HeadDcrf_Checker = new model_HeadDcrf_Checker();
						tblHeadDcrfChecker = new _JTableMain(model_HeadDcrf_Checker);
						scrollHead.setViewportView(tblHeadDcrfChecker);
						tblHeadDcrfChecker.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblHeadDcrfChecker.getColumnModel().getColumn(2).setPreferredWidth(225);
						tblHeadDcrfChecker.addMouseListener(new MouseListener() {
						
							public void mouseReleased(MouseEvent e) {}
							
							public void mousePressed(MouseEvent e) {}
							
							
							public void mouseExited(MouseEvent e) {}
							
						
							public void mouseEntered(MouseEvent e) {}
							
							
							public void mouseClicked(MouseEvent e) {
								int row = tblHeadDcrfChecker.getSelectedRow();
								dcrfStatus = (String)model_HeadDcrf_Checker.getValueAt(row, 3);
								if(dcrfStatus.equals("FIXED")) {
									btnApproved.setEnabled(false);
									btnDisApproved.setEnabled(false);
									btnAttach.setEnabled(false);
									btnConformed.setEnabled(true);
								}
								else
								{
									btnApproved.setEnabled(true);
									btnDisApproved.setEnabled(true);
									btnAttach.setEnabled(true);
									btnConformed.setEnabled(false);

								}
							}
						});

					}
					rowHeadDep = tblHeadDcrfChecker.getRowHeader();
					rowHeadDep.setModel(new DefaultListModel());
					scrollHead.setRowHeaderView(rowHeadDep);
					scrollHead.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

				}
				{

					pnlMainSouth = new JPanel(new GridLayout(1,5,3,3));
					pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
					pnlMainSouth.setPreferredSize(new Dimension(0,30));
					{
						btnPreview = new JButton("Preview");
						pnlMainSouth.add(btnPreview);
						btnPreview.setActionCommand("Preview");
						btnPreview.addActionListener(this);

						btnApproved = new JButton("Approved");
						pnlMainSouth.add(btnApproved);
						btnApproved.setActionCommand("Approved");
						btnApproved.addActionListener(this);

						btnDisApproved = new JButton("Disapproved");
						pnlMainSouth.add(btnDisApproved);
						btnDisApproved.setActionCommand("Disapproved");
						btnDisApproved.addActionListener(this);
						
						btnAttach = new JButton("Attachment");
						pnlMainSouth.add(btnAttach);
						btnAttach.setActionCommand("Attachment");
						btnAttach.addActionListener(this);
						
						btnConformed = new JButton("Conformed");
						pnlMainSouth.add(btnConformed);
						btnConformed.setActionCommand("Conformed");
						btnConformed.addActionListener(this);
						btnConformed.setEnabled(false);
					}

				}


				pnlMain.repaint();
				pnlMain.revalidate();

			}

		}


		displayValue(model_HeadDcrf_Checker, rowHeadDep);
		ExecuteUSB();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Preview")){
			System.out.println("dcrf_no"+dcrf_no);

			System.out.println("dumaan dito");

			System.out.printf("dcrf_no"+dcrf_no);

			preview();


		}

		if(e.getActionCommand().equals("Approved")){
			approved();
			this.removeAll();
			initGUI();

		}
		if(e.getActionCommand().equals("Disapproved")){
			disapproved();
			this.removeAll();
			initGUI();

		}
		if(e.getActionCommand().equals("Attachment")){
			attachment();
			
				
			}
		if(e.getActionCommand().equals("Conformed")){
			
			Conformed();
			this.removeAll();
			initGUI();
				
			}

	}
	private void displayValue(DefaultTableModel model_HeadDcrf_Checker,JList rowHeadDep) {
		FncTables.clearTable(model_HeadDcrf_Checker);
		DefaultListModel listmod = new DefaultListModel();
		rowHeadDep.setModel(listmod);
		if (UserInfo.EmployeeCode.equals("900054")) {
			String query = "	select a.dcrf_no, b.dept_alias, d.entity_name,a.status \n" + 
					"				from rf_dcrf_header a \n" + 
					"				left join mf_department b on a.dept_code = b.dept_code \n" + 
					"				left join em_employee c on b.dept_code = c.dept_code and a.created_by = c.emp_code \n" + 
					"				left join rf_entity d on c.entity_id = d.entity_id \n" + 
					"				where a.status = 'ACTIVE' and b.dept_head_code = '"+UserInfo.EmployeeCode+"' and a.app_dept_head is null and a.disapp_dept_head_date is null\n" + 
					"				union all \n" + 
					"				select a.dcrf_no, b.dept_alias, d.entity_name,a.status \n" + 
					"				from rf_dcrf_header a \n" + 
					"				left join mf_department b on a.dept_code = b.dept_code \n" + 
					"				left join em_employee c on b.dept_code = c.dept_code and a.created_by = c.emp_code \n" + 
					"				left join rf_entity d on c.entity_id = d.entity_id \n" + 
					"				where a.status = 'FIXED' and b.dept_head_code = '"+UserInfo.EmployeeCode+"' and a.app_dept_head = '"+UserInfo.EmployeeCode+"' \n" + 
					"				and a.created_by = '"+UserInfo.EmployeeCode+"'  and a.disapp_dept_head_date is null "+ 
					"				union all"+ 
					"				select a.dcrf_no,'FAD', d.entity_name,a.status\n" + 
					"				from rf_dcrf_header a\n" + 
					"				left join em_employee c on  a.created_by = c.emp_code   \n" + 
					"				left join rf_entity d on c.entity_id = d.entity_id   \n" + 
					"				where a.status = 'ACTIVE' AND a.created_by = '901119' AND a.app_dept_head is null";
			pgSelect db = new pgSelect();
			db.select(query);
			if(db.isNotNull()){
				for(int x = 0;x<db.getRowCount();x++){
					model_HeadDcrf_Checker.addRow(db.getResult()[x]);
					listmod.addElement(model_HeadDcrf_Checker.getRowCount());
				}
			}
		}
		else {
			String query = "	select a.dcrf_no, b.dept_alias, d.entity_name,a.status \n" + 
					"				from rf_dcrf_header a \n" + 
					"				left join mf_department b on a.dept_code = b.dept_code \n" + 
					"				left join em_employee c on b.dept_code = c.dept_code and a.created_by = c.emp_code \n" + 
					"				left join rf_entity d on c.entity_id = d.entity_id \n" + 
					"				where a.status = 'ACTIVE' and b.dept_head_code = '"+UserInfo.EmployeeCode+"' and a.app_dept_head is null and a.disapp_dept_head_date is null\n" +
					//"				and a.dcrf_no NOT IN (1737, 1770, 1766, 1869, 1915, 1908, 1916) \n"+
					"				union all \n" + 
					"				select a.dcrf_no, b.dept_alias, d.entity_name,a.status \n" + 
					"				from rf_dcrf_header a \n" + 
					"				left join mf_department b on a.dept_code = b.dept_code \n" + 
					"				left join em_employee c on b.dept_code = c.dept_code and a.created_by = c.emp_code \n" + 
					"				left join rf_entity d on c.entity_id = d.entity_id \n" + 
					"				where a.status = 'FIXED' and b.dept_head_code = '"+UserInfo.EmployeeCode+"' and a.app_dept_head = '"+UserInfo.EmployeeCode+"' \n" + 
					"				and a.created_by = '"+UserInfo.EmployeeCode+"'  and a.disapp_dept_head_date is null ";
			pgSelect db = new pgSelect();
			db.select(query);
			if(db.isNotNull()){
				for(int x = 0;x<db.getRowCount();x++){
					model_HeadDcrf_Checker.addRow(db.getResult()[x]);
					listmod.addElement(model_HeadDcrf_Checker.getRowCount());
				}
			}
		}


	}
	
	private void preview(){	
		System.out.println("dumaan dito");


		if(tblHeadDcrfChecker.getSelectedRows().length==1){
			int row = tblHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer)model_HeadDcrf_Checker.getValueAt(row, 0);
			department = (String)model_HeadDcrf_Checker.getValueAt(row, 1);
			emp_name = (String)model_HeadDcrf_Checker.getValueAt(row, 2);
			System.out.println("dumaan dito");



			String criteria = "DCRF";		
			String reportTitle = String.format("%s (%s)", DataChangeRequest.title.replace(" Report", ""), criteria.toUpperCase());
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("dcrf_no",dcrf_no);
			FncReport.generateReport("/Reports/rptDCRF_preview_paperless.jasper", reportTitle,DataChangeRequest.company, mapParameters);
		} 
		else 
		{
			JOptionPane.showMessageDialog(null, "Please select a row","Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void ExecuteUSB() {
		tmr = new TimerTask() {

			public void run() {
				displayValue(model_HeadDcrf_Checker, rowHeadDep);
			}
		};

		timerExecute = new Timer();
		long delay = 0;
		long intevalPeriod = 20 * 1000; 

		timerExecute.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}

	
	private void approved() {
		
		if(tblHeadDcrfChecker.getSelectedRows().length==1){
			int row = tblHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer)model_HeadDcrf_Checker.getValueAt(row, 0);
			department = (String)model_HeadDcrf_Checker.getValueAt(row, 1);
			emp_name = (String)model_HeadDcrf_Checker.getValueAt(row, 2);
			pgSelect db = new pgSelect();
			String query = "SELECT sp_save_dept_head_app('"+dcrf_no+"','"+UserInfo.EmployeeCode+"')";
			db.select(query);
	JOptionPane.showMessageDialog(null, "Dcrf has been approved", "Success",JOptionPane.INFORMATION_MESSAGE );
			
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "Please select a row","Warning", JOptionPane.ERROR_MESSAGE);
		}
			
//			Boolean blnDiv = JOptionPane.showConfirmDialog(null, "Require Division Head Approval?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION; 
			
//			pgUpdate db = new pgUpdate();
//			String query = "update rf_dcrf_header \n" +
//					"set status = 'APPROVED', \n" +
//					"app_dept_head = '"+UserInfo.EmployeeCode+"', \n" +
//					"app_dept_head_date = now(), \n" +
////					"require_div_head = "+blnDiv+" \n" +
//					"where dcrf_no = '"+dcrf_no+"'";
//			db.executeUpdate(query, true);
//			db.commit();
			
		
	}
	
	private void disapproved() {
		if(tblHeadDcrfChecker.getSelectedRows().length==1){
			int row = tblHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer)model_HeadDcrf_Checker.getValueAt(row, 0);
			department = (String)model_HeadDcrf_Checker.getValueAt(row, 1);
			emp_name = (String)model_HeadDcrf_Checker.getValueAt(row, 2);

			pgUpdate db = new pgUpdate();
			String query = "update rf_dcrf_header \n" +
					"set status = 'INACTIVE', \n" +
					"disapp_dept_head_date = now() \n" +
					"where dcrf_no = '"+dcrf_no+"'";
			db.executeUpdate(query, true);
			db.commit();
			JOptionPane.showMessageDialog(null, "Dcrf has been disapproved", "Success",JOptionPane.INFORMATION_MESSAGE );
			

		}
		else 
		{
			JOptionPane.showMessageDialog(null, "Please select a row","Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void attachment(){
		if(tblHeadDcrfChecker.getSelectedRows().length==1){
			int row = tblHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer)model_HeadDcrf_Checker.getValueAt(row, 0);
			department = (String)model_HeadDcrf_Checker.getValueAt(row, 1);
			emp_name = (String)model_HeadDcrf_Checker.getValueAt(row, 2);
			pgSelect db = new pgSelect();
			
			String query = "select attachment_img from rf_dcrf_attachments where dcrf_no ='"+dcrf_no+"'";
			db.select(query);
			
			if(db.isNotNull()){
			dlg_dcrf_attachment_viewer dialog = new dlg_dcrf_attachment_viewer(FncGlobal.homeMDI, "Attachments", false,dcrf_no.toString());
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Dimension screenSize = toolkit.getScreenSize();
			
			dialog.setSize(screenSize.width-100, screenSize.height-50);
			dialog.setResizable(false);
			dialog.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "There's no Attachment ", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "Please select a row","Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void Conformed() {
		if(tblHeadDcrfChecker.getSelectedRows().length==1){
			int row = tblHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer)model_HeadDcrf_Checker.getValueAt(row, 0);
			department = (String)model_HeadDcrf_Checker.getValueAt(row, 1);
			emp_name = (String)model_HeadDcrf_Checker.getValueAt(row, 2);
			dcrfStatus = (String)model_HeadDcrf_Checker.getValueAt(row, 3);
			
			pgUpdate db = new pgUpdate();
			db.executeUpdate("update rf_dcrf_header set status = 'COMPLETED',date_conformed = now(),conformed_by = '"+UserInfo.EmployeeCode+"' where dcrf_no = '"+dcrf_no+"'"
					, true);
			db.commit();
			
	JOptionPane.showMessageDialog(null, "Dcrf has been conformed", "Success",JOptionPane.INFORMATION_MESSAGE );
			
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "Please select a row","Warning", JOptionPane.ERROR_MESSAGE);
		}
			
	}
}
