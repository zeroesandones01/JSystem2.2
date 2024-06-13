package System;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Database.pgSelect;
import Functions.FncTables;
import Functions.UserInfo;
import Renderer.DateRenderer;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.model_HolidaySetterTable;
import tablemodel.model_UploadEmp;

public class UploadEmployee extends _JInternalFrame {
	Dimension size =  new Dimension(600,300);
	static String title = "Upload Employee";

	private JPanel pnlMain;
	private JButton btnSave,btnAdd,btnCancel;


	private JScrollPane scrollEmp;
	private _JTableMain tblEmp;
	private JList rowHeadEmp;
	private model_UploadEmp model_Emp;
	SimpleDateFormat h = new SimpleDateFormat("MM-dd-yyyy");
	public UploadEmployee(){
		super(title,true,true,false,false);
		initGui();
	}
	public UploadEmployee(String title){
		super(title);
		initGui();
	}
	public UploadEmployee(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconifiable){
		super(title, false, true, true, true);

	}

	public void initGui(){
		this.setTitle(title);
		this.setSize(size);
		setPreferredSize(size);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5,5,5,5));
			{
				JPanel mainCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(mainCenter,BorderLayout.CENTER);
				{
					scrollEmp= new JScrollPane();
					mainCenter.add(scrollEmp,BorderLayout.CENTER);
					scrollEmp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollEmp.setBorder(new EmptyBorder(5,5,5,5));

				}
				{
					model_Emp = new model_UploadEmp();
					tblEmp = new _JTableMain(model_Emp);
					scrollEmp.setViewportView(tblEmp);
					tblEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblEmp.hideColumns("Entity ID","Rec ID");
					tblEmp.setEnabled(true);
					tblEmp.setSortable(false);
					tblEmp.getColumnModel().getColumn(17).setPreferredWidth(225);	
					tblEmp.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(h));
					tblEmp.getColumnModel().getColumn(10).setCellRenderer(new DateRenderer(h));
					tblEmp.getColumnModel().getColumn(11).setCellRenderer(new DateRenderer(h));
					tblEmp.getTableHeader().setEnabled(false);
					tblEmp.setEnabled(false);
				}
				{
					rowHeadEmp = tblEmp.getRowHeader();
					rowHeadEmp.setModel(new DefaultListModel());
					scrollEmp.setRowHeaderView(rowHeadEmp);
					scrollEmp.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				JPanel mainSouth = new JPanel(new GridLayout(1,6,3,3));
				pnlMain.add(mainSouth,BorderLayout.SOUTH);
				mainSouth.setBorder(new EmptyBorder(5,5,5,5));
				//				mainSouth.setPreferredSize(new Dimension (0,30));
				{
					mainSouth.add(Box.createHorizontalBox());
					mainSouth.add(Box.createHorizontalBox());
					mainSouth.add(Box.createHorizontalBox());
				}
				{
					btnAdd = new JButton("Add");
					mainSouth.add(btnAdd);
					btnAdd.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							tblEmp.setEnabled(true);
							btnAdd.setEnabled(false);
							btnCancel.setEnabled(true);
							btnSave.setEnabled(true);
						}
					});;
				}
				{
					btnSave = new JButton("Save");
					mainSouth.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							for(int x =0; x < model_Emp.getRowCount(); x++){
								Boolean isSelected = (Boolean) model_Emp.getValueAt(x, 0);
								String emp_code = (String) model_Emp.getValueAt(x, 1);

								if(isSelected){

									String strSQL = "SELECT jobs_employee_v2('"+emp_code+"', '"+UserInfo.EmployeeCode+"');" ; 

									pgSelect db = new pgSelect();
									db.selectForeign(strSQL);
								}
							}
							JOptionPane.showMessageDialog(getContentPane(),"Succesfully Save","Save",JOptionPane.INFORMATION_MESSAGE);
							tblEmp.setEnabled(false);
							btnSave.setEnabled(false);
							btnCancel.setEnabled(false);
							btnAdd.setEnabled(true);
							displayValue(model_Emp,rowHeadEmp);
						}
					});
				}
				{
					btnCancel = new JButton("Cancel");
					mainSouth.add(btnCancel);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							tblEmp.setEnabled(false);
							btnSave.setEnabled(false);
							btnCancel.setEnabled(false);
							btnAdd.setEnabled(true);
							displayValue(model_Emp,rowHeadEmp);
						}
					});
				}
			}
		}
		displayValue(model_Emp,rowHeadEmp);

	}
	private void displayValue(DefaultTableModel model,JList rowHeader){
		FncTables.clearTable(model);
		DefaultListModel listmod = new DefaultListModel();
		rowHeader.setModel(listmod);
		String query = "select * from view_upload_emp();";

		pgSelect db = new pgSelect();
		db.selectForeign(query);

		if(db.isNotNull()){
			for(int x = 0;x<db.getRowCount();x++){
				model.addRow(db.getResult()[x]);
				listmod.addElement(model.getRowCount());
			}

		}

	}
}
