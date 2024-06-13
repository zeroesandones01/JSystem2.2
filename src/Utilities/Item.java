package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Renderer.DateRenderer;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.model_HolidaySetterTable;

public class Item extends _JInternalFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6143015621825644470L;
	Dimension size =  new Dimension(550,600);
	static String title = "DIVISORIA";

	private JPanel mainNorth;
	private JLabel lblHoDate,lblHoName,lblhoType,lblHoHalf;
	private JCheckBox cBoxHalfDay;
	private JButton btnNew,btnEdit,btnSave,btnCancel;
	private JTextField textName;
	private JComboBox comboType;
	private JDateChooser chooseDate; 

	private JScrollPane scrollHoliday;
	private _JTableMain tblHoliday;
	private JList rowHeadHoliday;
	private model_HolidaySetterTable model_HolidaySetterTable;

	private String toDo = "";
	private Object rwRec = "";
	private Object rwDate = "";
	private Object rwName = "";
	private Object rwType = "";
	private Object rwHalfday = "";
	private Object rwUser = "";


	SimpleDateFormat h = new SimpleDateFormat("yyyy/MM/dd");


	public Item(){
		super(title,true,true,false,false);
		initGui();
	}
	public Item(String title){
		super(title);
		initGui();
	}
	public Item(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconifiable){
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

					JPanel lblMainNorth = new JPanel(new GridLayout(4,1,3,3));
					lblMainNorth.setBorder(new EmptyBorder(5,5,5,5));
					mainNorth.add(lblMainNorth,BorderLayout.WEST);
					{
						lblHoDate = new JLabel("Set Date:");
						lblMainNorth.add(lblHoDate);

						lblHoName = new JLabel("Name of Holiday:");
						lblMainNorth.add(lblHoName);

						lblhoType = new JLabel("Type of holiday:");
						lblMainNorth.add(lblhoType);

						lblHoHalf = new JLabel("Half day:");
						lblMainNorth.add(lblHoHalf);
					}
				}
				{
					JPanel mainNorthComponents = new JPanel(new GridLayout(4,1,3,3));
					mainNorthComponents.setBorder(new EmptyBorder(5,5,5,5));
					mainNorth.add(mainNorthComponents,BorderLayout.CENTER);
					{
						{
							chooseDate = new JDateChooser();
							mainNorthComponents.add(chooseDate);
							chooseDate.setDate(FncGlobal.getDateToday());
							chooseDate.setDateFormatString("MM/dd/yyyy");
							chooseDate.setEnabled(false);
						}
						{
							textName = new JTextField("");
							mainNorthComponents.add(textName);
							textName.setEnabled(false);
							textName.addKeyListener(new KeyAdapter() {
								public void keyTyped(KeyEvent e){
									char c = e.getKeyChar();

									char [] invalidSymbol = "[!@#$%^&*()_+=.,?|`'~]".toCharArray();

									for(int x = 0; x < invalidSymbol.length; x++) {
										if(c == invalidSymbol[x]) {

											e.consume();
										}
									}
								}
							});
						}
						{
							String [] holiday = {"Special","Regular"};
							comboType = new JComboBox(holiday);
							mainNorthComponents.add(comboType);
							comboType.setEnabled(false);
						}
						{
							cBoxHalfDay = new JCheckBox();
							mainNorthComponents.add(cBoxHalfDay);
							cBoxHalfDay.setEnabled(false);
						}
					}
				}
			}
			{
				JPanel mainCenter = new JPanel(new BorderLayout(5,5));
				pnlmain.add(mainCenter,BorderLayout.CENTER);
				{
					scrollHoliday = new JScrollPane();
					mainCenter.add(scrollHoliday,BorderLayout.CENTER);
					scrollHoliday.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollHoliday.setBorder(new EmptyBorder(5,5,5,5));

				}
				{


					model_HolidaySetterTable = new model_HolidaySetterTable();
					tblHoliday = new _JTableMain(model_HolidaySetterTable);
					scrollHoliday.setViewportView(tblHoliday);
					tblHoliday.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblHoliday.hideColumns("HO OTCODE ","DATE CREATED","REC ID","CREATED BY ");
					tblHoliday.setEnabled(true);
					tblHoliday.setSortable(false);
					tblHoliday.getColumnModel().getColumn(1).setPreferredWidth(225);	
					tblHoliday.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(h));
					tblHoliday.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if(tblHoliday.getSelectedRows().length==1){
								tableCLick();
								btnEdit.setEnabled(true);

							}

						}
					});
				}
				{
					rowHeadHoliday = tblHoliday.getRowHeader();
					rowHeadHoliday.setModel(new DefaultListModel());
					scrollHoliday.setRowHeaderView(rowHeadHoliday);
					scrollHoliday.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
					btnNew = new JButton("New");
					mainSouth.add(btnNew);
					btnNew.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							chooseDate.setEnabled(true);	
							textName.setEnabled(true);
							comboType.setEnabled(true);
							cBoxHalfDay.setEnabled(true);
							tblHoliday.setEnabled(false);
							btnSave.setEnabled(true);
							btnCancel.setEnabled(true);
							btnEdit.setEnabled(false);
							btnNew.setEnabled(false);
							textName.setText(null);
							comboType.setSelectedIndex(0);
							cBoxHalfDay.setSelected(false);
							chooseDate.setDate(FncGlobal.getDateToday());


						}
					});
				}
				{
					btnEdit = new JButton("Edit");
					mainSouth.add(btnEdit);
					btnEdit.setEnabled(false);
					btnEdit.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							toDo = "edit";

							int rw = tblHoliday.getSelectedRow();
							{

								rwRec =  model_HolidaySetterTable.getValueAt(rw, 0);  
								rwDate =  model_HolidaySetterTable.getValueAt(rw, 1);
								rwName =  model_HolidaySetterTable.getValueAt(rw, 2);
								rwType =  model_HolidaySetterTable.getValueAt(rw , 3);
								rwHalfday =  model_HolidaySetterTable.getValueAt(rw, 4);
								rwUser =  model_HolidaySetterTable.getValueAt(rw, 5);
								//rwHo = (String) model_HolidaySetterTable.getValueAt(rw, 6);
								//rwCreated = (String) model_HolidaySetterTable.getValueAt(rw, 7);

								//System.out.printf("rec %s\n",rwRec);
								System.out.printf("date %s\n",rwDate);
								System.out.printf("name %s\n",rwName);
								System.out.printf("type%s\n",rwType);
								System.out.printf("halfday %s\n",rwHalfday);
								System.out.printf("user %s\n",rwUser);
								//System.out.printf("hootcode %s\n",rwHo);
								//System.out.printf("created %s\n",rwCreated);

							}

							chooseDate.setEnabled(true);
							textName.setEnabled(true);
							comboType.setEnabled(true);
							cBoxHalfDay.setEnabled(true);
							tblHoliday.setEnabled(true);
							btnNew.setEnabled(false);
							btnSave.setEnabled(true);
							btnCancel.setEnabled(true);
							btnEdit.setEnabled(false);
						}
					});

				}
				{
					btnSave = new JButton("Save");
					mainSouth.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if(textName.getText().equals("")){
								JOptionPane.showMessageDialog(getContentPane(), "Error,Please input Holiday Name","Error",JOptionPane.ERROR_MESSAGE);


							}
							else if (JOptionPane.showConfirmDialog(getContentPane(),"Are you sure you want to save it?", "Save", 
									JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){

								if(toDo.equals("edit")){
									updateValue();	
									JOptionPane.showMessageDialog(getContentPane(), "Successfully Updated");
									displayValue(model_HolidaySetterTable, rowHeadHoliday);
									chooseDate.setEnabled(false);
									textName.setEnabled(false);
									comboType.setEnabled(false);
									cBoxHalfDay.setEnabled(false);
									tblHoliday.setEnabled(true);
									btnNew.setEnabled(true);
									btnEdit.setEnabled(false);
									btnSave.setEnabled(false);
									btnCancel.setEnabled(false);
								}

								else {
									getValue();
									JOptionPane.showMessageDialog(getContentPane(), "Successfully Save");
									displayValue(model_HolidaySetterTable, rowHeadHoliday);
									chooseDate.setEnabled(false);
									textName.setEnabled(false);
									comboType.setEnabled(false);
									cBoxHalfDay.setEnabled(false);
									tblHoliday.setEnabled(true);
									btnNew.setEnabled(true);
									btnEdit.setEnabled(false);
									btnSave.setEnabled(false);
									btnCancel.setEnabled(false);
								}

							}


						}
					});
				}
				{
					btnCancel = new JButton("Cancel");
					mainSouth.add(btnCancel);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to cancel?", "Cancel", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
								tblHoliday.setEnabled(true);
								chooseDate.setEnabled(false);
								textName.setEnabled(false);
								comboType.setEnabled(false);
								cBoxHalfDay.setEnabled(false);
								btnNew.setEnabled(true);
								btnEdit.setEnabled(true);
								btnSave.setEnabled(false);
								btnCancel.setEnabled(false);
								textName.setText(null);
								comboType.setSelectedIndex(0);
								cBoxHalfDay.setSelected(false);
								chooseDate.setDate(FncGlobal.getDateToday());

								displayValue(model_HolidaySetterTable, rowHeadHoliday);
							}
						}
					});
				}

			}

		}
		displayValue(model_HolidaySetterTable, rowHeadHoliday);

	}
	private void displayValue(DefaultTableModel model_HolidaySetterTable,JList rowHeadHoliday){
		FncTables.clearTable(model_HolidaySetterTable);
		DefaultListModel listmod = new DefaultListModel();
		rowHeadHoliday.setModel(listmod);
		String query = "SELECT rec_id,ho_day,ho_desc,ho_type,halfday,created_by,ho_otcode,date_created FROM mf_holidays";
		pgSelect db = new pgSelect();
		db.select(query);
		if(db.isNotNull()){
			for(int x = 0;x<db.getRowCount();x++){
				model_HolidaySetterTable.addRow(db.getResult()[x]);
				listmod.addElement(model_HolidaySetterTable.getRowCount());
			}

		}

	}

	private void getValue(){
		pgUpdate db = new pgUpdate();
		Date dateVal = chooseDate.getDate();
		String textVal = textName.getText();
		Boolean  checkVal = cBoxHalfDay.isSelected();
		String comboVal = "";
		if(comboType.getSelectedIndex()==0){
			comboVal = "S";

		}
		else
		{
			comboVal = "R";
		}
		{
			String query = "INSERT INTO mf_holidays (rec_id,ho_day,ho_desc,ho_type,halfday,created_by,ho_otcode,date_created) "+
					"VALUES ((SELECT max(rec_id)+1 from mf_holidays),"+  
					"'"+dateVal+"','"+textVal+"', '"+comboVal+"','"+checkVal+"','"+UserInfo.EmployeeCode+"','00','"+FncGlobal.getDateToday()+"')";
			db.executeUpdate(query, true);

		}
		db.commit();


	}
	private void updateValue(){
		pgUpdate db = new pgUpdate();

		String userVal = "";
		Date dateVal = chooseDate.getDate();
		String textVal = textName.getText();
		Boolean  checkVal = cBoxHalfDay.isSelected();
		String comboVal = "";
		if(comboType.getSelectedIndex()==0){
			comboVal = "S";

		}
		else
		{
			comboVal = "R";
		}
		{
			String query = "UPDATE mf_holidays SET ho_day = '"+dateVal+"',ho_desc = '"+textVal+"',ho_type = '"+comboVal+"'"
					+ ",halfday = '"+checkVal+"',created_by = '"+userVal+"' WHERE rec_id = '"+rwRec+"' ";
			db.executeUpdate(query, true);
		}
		db.commit();



	}
	private void tableCLick(){

		if(tblHoliday.getSelectedRows().length==1);{
			int row = tblHoliday.getSelectedRow();
			Date Dayte = (Date) model_HolidaySetterTable.getValueAt(row, 1);
			String Name = (String) model_HolidaySetterTable.getValueAt(row, 2);
			String Type = (String) model_HolidaySetterTable.getValueAt(row , 3);
			Boolean Halfday = (Boolean) model_HolidaySetterTable.getValueAt(row, 4);



			chooseDate.setDate(Dayte);
			textName.setText(Name);
			cBoxHalfDay.setSelected(Halfday);

			if(Type.equals("R")){
				comboType.setSelectedIndex(1);
			}
			else
			{
				comboType.setSelectedIndex(0);
			}



		}
	}
}





