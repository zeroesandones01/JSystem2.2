package System;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelUpdateEmployee;
import tablemodel.model_UploadEmp;

public class UpdateEmployeeStatus extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String title = "Inactive Employee Status";
	Dimension frameSize = new Dimension(600,500);
	private JPanel pnlMain;
	private JXTextField txtSearch;
	private JScrollPane scrollEmp;
	private _JTableMain tblEmp;
	private JList rowHeadEmp;
	SimpleDateFormat h = new SimpleDateFormat("MM-dd-yyyy");
	private JButton btnUpdate;
	private JButton btnCancel;
	private modelUpdateEmployee model_Emp;

	public UpdateEmployeeStatus() {
		// TODO Auto-generated constructor stub
		super(title, true, true, false, true);
		initGUI();
	}

	public UpdateEmployeeStatus(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public UpdateEmployeeStatus(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0,30));
				{
					JLabel lblSearch = new JLabel("Search:");
					pnlNorth.add(lblSearch, BorderLayout.WEST);
				}
				{
					txtSearch = new JXTextField("Search employee here...");
					pnlNorth.add(txtSearch, BorderLayout.CENTER);
					txtSearch.addKeyListener(new KeyListener() {
						
						@Override
						public void keyTyped(KeyEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void keyReleased(KeyEvent e) {
							// TODO Auto-generated method stub
							searchName();
							
						}
						
						@Override
						public void keyPressed(KeyEvent e) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(JTBorderFactory.createTitleBorder("List of Employees"));
				{
					scrollEmp= new JScrollPane();
					pnlCenter.add(scrollEmp,BorderLayout.CENTER);
					scrollEmp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollEmp.setBorder(new EmptyBorder(5,5,5,5));
					
				}
				{
					model_Emp = new modelUpdateEmployee();
					tblEmp = new _JTableMain(model_Emp);
					scrollEmp.setViewportView(tblEmp);
					tblEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblEmp.hideColumns("Entity ID","Rec ID");
					tblEmp.setEnabled(true);
					tblEmp.setSortable(false);
					tblEmp.getColumnModel().getColumn(2).setPreferredWidth(200);
					tblEmp.getColumnModel().getColumn(18).setPreferredWidth(225);	
					tblEmp.getColumnModel().getColumn(6).setCellRenderer(new DateRenderer(h));
					tblEmp.getColumnModel().getColumn(11).setCellRenderer(new DateRenderer(h));
					tblEmp.getColumnModel().getColumn(12).setCellRenderer(new DateRenderer(h));
					tblEmp.getColumnModel().getColumn(13).setCellRenderer(new DateRenderer(h));
					tblEmp.getTableHeader().setEnabled(false);
				}
				{
					rowHeadEmp = tblEmp.getRowHeader();
					rowHeadEmp.setModel(new DefaultListModel());
					scrollEmp.setRowHeaderView(rowHeadEmp);
					scrollEmp.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1,2,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));
				{
					btnUpdate = new JButton("Update");
					pnlSouth.add(btnUpdate);
					btnUpdate.setActionCommand("Update");
					btnUpdate.addActionListener(this);
				}
				{
					btnCancel = new JButton("Refresh");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Refresh");
					btnCancel.addActionListener(this);
				}
			}
		}
		
		displayListofEmployees(model_Emp, rowHeadEmp);
	}
	
	private void displayListofEmployees(DefaultTableModel modelMain, JList rowHeader) {
		FncTables.clearTable(modelMain);
		DefaultListModel list = new DefaultListModel();
		rowHeader.setModel(list);
		
		String sql = "select \n" + 
				"false as bol,\n" + 
				"a.emp_code::varchar,\n" + 
				"trim(format('%s %s %s', a.first_name, a.mid_name, a.last_name)) as full_name,\n" + 
				"a.first_name::varchar,\n" + 
				"a.mid_name::varchar,\n" + 
				"a.last_name::varchar,\n" + 
				"a.birth_date::date,\n" + 
				"a.sex::varchar,\n" + 
				"a.civil_status::varchar,\n" + 
				"a.dependent_no::int,\n" + 
				"a.emp_status::varchar,\n" + 
				"a.hired_date::Date,\n" + 
				"a.regular_date::date,\n" + 
				"a.terminate_date::timestamp,\n" + 
				"a.emp_rank::varchar,\n" + 
				"a.emp_pos::varchar,\n" + 
				"a.group_code::varchar, \n" + 
				"a.div_code::varchar,\n" + 
				"a.dept_code::varchar,\n" + 
				"a.address::varchar,\n" + 
				"a.tel_nos::varchar,\n" + 
				"a.cellphone_no::varchar,\n" + 
				"a.email::varchar, \n" + 
				"a.entity_id::varchar,\n" + 
				"a.rec_id::int \n" + 
				"from acerhomes.employee a \n" + 
				"where exists(select * from em_employee x where a.emp_code::varchar = x.emp_code )  \n" + 
				"and a.emp_status::varchar in ('I','E') \n" + 
				"and a.emp_status::varchar != (select x.emp_status from em_employee x where a.emp_code::varchar = x.emp_code )  \n" + 
				"order by hired_date::dATE desc";
		
		FncSystem.out("SQl for list of employees", sql);
		pgSelect db = new pgSelect();
		db.selectForeign(sql);
		
		if(db.isNotNull()){
			for(int x = 0;x<db.getRowCount();x++){
				modelMain.addRow(db.getResult()[x]);
				list.addElement(modelMain.getRowCount());
			}
		}		
	}
	
	private void searchName() {

		int rw = tblEmp.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String name = "";

			try {
				name = tblEmp.getValueAt(x, 2).toString().toUpperCase();
			} catch (NullPointerException e) {
				name = "";
			}

			String strBatch = txtSearch.getText().trim().toUpperCase();
			// Boolean match = name.indexOf(acct_name)>0;
			Boolean start = name.contains(strBatch);
			// Boolean end = name.endsWith(module_name);

			if (start == true) {
				tblEmp.setRowSelectionInterval(x, x);
				tblEmp.changeSelection(x, 2, false, false);
				break;
			} else {
				tblEmp.setRowSelectionInterval(0, 0);
				tblEmp.changeSelection(0, 2, false, false);
			}

			x++;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand().equals("Update")) {		update();}
		
		if(e.getActionCommand().equals("Refresh")) {	refresh();}
		
	}
	
	private void refresh() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(getContentPane(), "Data is refreshed!");
		displayListofEmployees(model_Emp, rowHeadEmp);
		txtSearch.setText("");
		
	}

	private void update() {

		if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to inactive this employee?", "Update", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			for(int x=0 ; x<model_Emp.getRowCount(); x++) {
				Boolean ifSelected = (Boolean) model_Emp.getValueAt(x, 0);

				if(ifSelected) {
					String emp_code = (String) model_Emp.getValueAt(x, 1);
					String status = (String) model_Emp.getValueAt(x, 10);
					String user = UserInfo.EmployeeCode;
					String login = getLoginRole(emp_code);
					java.util.Date termi_date = (java.util.Date) model_Emp.getValueAt(x, 13);

					updateEmployeeStatus(status, user, emp_code);
					updateLoginRoles(login, termi_date);
					updateSystemUser(user, emp_code);
					
					JOptionPane.showMessageDialog(getContentPane(), "Successfully updated!");
					displayListofEmployees(model_Emp, rowHeadEmp);
				}
			}

		}	
	}
	
	private void updateEmployeeStatus(String status, String user, String emp_code) {
		
		String sql = "UPDATE em_employee\n" + 
				"SET emp_status = '"+status+"', edit_by = '"+user+"', date_edited = now()\n" +
				"WHERE emp_code = '"+emp_code+"'";
		
		FncSystem.out("SQL FOR UPDATING STATUS", sql);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();
		
	}
	
	private void updateLoginRoles(String login, java.util.Date termi_date) {

		String sql = "ALTER ROLE "+login+" VALID UNTIL '"+termi_date+"'";

		FncSystem.out("SQL FOR UPDATING STATUS", sql);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();

	}
	
	private void updateSystemUser(String user, String emp_code) {
		
		String sql = "UPDATE rf_system_user\n" + 
				"SET status_id = 'I', edited_by = '"+user+"', date_edited = now()\n" +
				"WHERE emp_code = '"+emp_code+"'";
		
		FncSystem.out("SQL FOR UPDATING SYSTEM USER", sql);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();
		
	}
	
	private String getLoginRole(String emp_code) {
		 
		String login = "";
		
		String sql = "SELECT login_id from rf_system_user where emp_code = '"+emp_code+"' and status_id = 'A'";
		
		FncSystem.out("SQL FOR GETTING LOGIN", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			login = db.getResult()[0][0].toString();
		}else {
			login = "";
		}
		
		return login;
	}

}
