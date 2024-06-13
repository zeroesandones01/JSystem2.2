package Accounting.Disbursements;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import tablemodel.modelMC_pv;

public class TagMCCVnumber extends _JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6143015621825644470L;
	Dimension size =  new Dimension(550,450);
	static String title = "Tag MC number - Check Voucher";

	private JPanel mainNorth;
	private JLabel lblCompany,lblDvno;
	private JTextField txtCompany, txtDvno;
	private static _JLookup lookupCompany;
	private static _JLookup lookupCvno;
	private JButton btnSave;
	private JButton btnCancel;
	private JScrollPane scrollMC;
	private static _JTableMain tblMC;
	private static JList rowHeadMC;
	private static modelMC_pv modelMC_cv;

	public static String co_id;
	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();


	public TagMCCVnumber(){
		super(title,true,true,false,false);
		initGui();
	}
	public TagMCCVnumber(String title){
		super(title);
		initGui();
	}
	public TagMCCVnumber(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconifiable){
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

						lblDvno = new JLabel("DV No.:");
						lblMainNorth.add(lblDvno);

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


										txtCompany.setText(data[1].toString());
										txtCompany.setToolTipText(data[2].toString());
										lblDvno.setEnabled(true);
										lookupCvno.setEnabled(true);
										lookupCvno.setLookupSQL(getPV_no(lookupCompany.getValue()));
										manager.focusNextComponent();
									}else{
										txtCompany.setText("");
									}
								}

							});

						}
						{
							lookupCvno = new _JLookup(null, "DV. No");
							mainNorthComponents.add(lookupCvno);
							lookupCvno.setReturnColumn(0);
							lookupCvno.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtDvno.setText(data[0].toString());


										manager.focusNextComponent();
										displayDV_pv(modelMC_cv,co_id);
									}else{
										txtDvno.setText("");
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
					txtDvno = new JTextField();
					mainNorthComponents2.add(txtDvno);
					txtDvno.setEditable(false);
					txtDvno.setPreferredSize(new Dimension(350,0));
				}

			}
			{
				JPanel mainCenter = new JPanel(new BorderLayout(5,5));
				pnlmain.add(mainCenter,BorderLayout.CENTER);
				{
					scrollMC = new JScrollPane();
					mainCenter.add(scrollMC,BorderLayout.CENTER);
					scrollMC.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollMC.setBorder(new EmptyBorder(5,5,5,5));

				}
				{


					modelMC_cv = new modelMC_pv();
					tblMC = new _JTableMain(modelMC_cv);
					scrollMC.setViewportView(tblMC);
					tblMC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblMC.setEnabled(true);
					tblMC.setSortable(false);
					tblMC.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if(tblMC.getSelectedRows().length==1){

							}

						}
					});
				}
				{
					rowHeadMC = tblMC.getRowHeader();
					rowHeadMC.setModel(new DefaultListModel());
					scrollMC.setRowHeaderView(rowHeadMC);
					scrollMC.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}


			}
			{
				JPanel mainSouth = new JPanel(new GridLayout(2,2,3,3));
				pnlmain.add(mainSouth,BorderLayout.SOUTH);
				mainSouth.setBorder(new EmptyBorder(5,5,5,5));
				mainSouth.setPreferredSize(new Dimension (0,50));
				{

					btnSave = new JButton("Save");
					mainSouth.add(btnSave);
					btnSave.setAlignmentX(0.5f);
					btnSave.setAlignmentY(0.5f);
					btnSave.setMaximumSize(new Dimension(100, 50));
					btnSave.setMnemonic(KeyEvent.VK_P);
					btnSave.addActionListener(this);



					btnCancel = new JButton("Cancel");
					mainSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setMaximumSize(new Dimension(100, 50));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.addActionListener(this);



				}				

			}


		}

	}

	public static String getPV_no(String co_id) {
		String SQL = "select \n" + 
				"distinct on (a.cv_no,a.co_id) a.cv_no  as \"CV No.\" ,\n" + 
				"d.status_desc as \"Status\"  from\n" + 
				"(select * from rf_cv_header where coalesce(remarks, '') !~* 'Released through AUB E-payment Facility' and co_id = '"+lookupCompany.getValue()+"') a\n" + 
				"left join rf_entity b on a.entity_id1=b.entity_id\n" + 
				"left join rf_cv_detail c on a.cv_no = c.cv_no and a.co_id = c.co_id left join mf_record_status d on a.status_id = d.status_id  order by a.cv_no desc ";

		FncSystem.out("GET PV_NO", SQL);

		return SQL;

	}



	public static void displayDV_pv(final DefaultTableModel modelMain, final String co_id) {
		FncTables.clearTable(modelMain);
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");
				modelMain.setRowCount(0);
				DefaultListModel listmod = new DefaultListModel();
				rowHeadMC.setModel(listmod);

				String sql = 

						"select false, trim(a.pv_no) as \"PV No\",  trim(nullif(b.mc_no, 'null')) as \"MC No\" \n" 
								+"from rf_pv_header a\n"	
								+"left join rf_mc_detail b on a.co_id = b.co_id and a.pv_no = b.pv_no  \n"
								+"where a.co_id = '" + lookupCompany.getValue() + "'\n"
								+"and a.cv_no = '" + lookupCvno.getValue() + "'\n"
								+"and a.status_id not in ('I','F')\n"
								//+"and CASE WHEN b.status_id IN('I') THEN TRUE ELSE FALSE END \n"
								+"and nullif(b.mc_no, '') is null \n" //REPLACED BY EMPTY STRING BY LESTER 2023-09-28
								+"order by a.cv_no DESC;\n";

				FncSystem.out("displayMC", sql);
				pgSelect db = new pgSelect();
				db.select(sql);

				if(db.isNotNull()) {
					for(int x=0; x < db.getRowCount(); x++){
						modelMain.addRow(db.getResult()[x]);
						listmod.addElement(modelMC_cv.getRowCount());
					}
				}

				tblMC.packAll();	

				FncGlobal.stopProgress();
				return db.getResult();
			}

		};

		sw.execute();

	}


	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Save")) {		save();}

		if(e.getActionCommand().equals("Cancel")) {		cancel();}
	}


	private void cancel() {

		co_id = "";
		lookupCompany.setValue(null);
		lookupCvno.setValue(null);
		txtCompany.setText("");
		txtDvno.setText("");
		modelMC_cv.clear();
		btnSave.setEnabled(false);

	}


	private void save() {

		String DisplayMC = "";
		for (int x = 0; x < modelMC_cv.getRowCount(); x++)
		{
			Boolean isSelected = (Boolean) modelMC_cv.getValueAt(x, 0);
			if(isSelected) {
				String pv_no= (String) modelMC_cv.getValueAt(x, 1);
				String mc_no = (String) modelMC_cv.getValueAt(x, 2);

				DisplayMC += "PV no: "+pv_no+      " MC no: "    + mc_no + "\n";  
			}

		}

		int response = JOptionPane.showConfirmDialog(getContentPane(), "Do you want to save entries?\n\n"+DisplayMC+" ", "Confirm",  JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

		if (response == JOptionPane.YES_OPTION == true ) {

			{
				for (int x = 0; x < modelMC_cv.getRowCount(); x++)
				{
					Boolean isSelected = (Boolean) modelMC_cv.getValueAt(x, 0);
					if(isSelected) {
						String pv_no= (String) modelMC_cv.getValueAt(x, 1);
						String mc_no = (String) modelMC_cv.getValueAt(x, 2);
						String cv_no= (String) lookupCvno.getValue();

						DisplayMC += pv_no +" "+ mc_no + "\n";  


						if (response == JOptionPane.YES_OPTION) {
							String sql = "INSERT INTO public.rf_mc_detail(\n" + 
									"	co_id, cv_no, pv_no, mc_no, status_id, created_by, date_created, edited_by, date_edited)\n" + 
									"	VALUES ('" + lookupCompany.getValue() + "',"
									+ "'"+cv_no+"',"
									+ "'" +pv_no+ "',"
									+ "'"+mc_no+"',"
									+ "'A',"
									+ "'"+UserInfo.EmployeeCode+"',"
									+ "now()::timestamp without time zone,"
									+ "'"+UserInfo.EmployeeCode+"',"
									+ "now());";


							FncSystem.out("SQL saving of MC no", sql);
							pgUpdate db = new pgUpdate();
							db.executeUpdate(sql, true);
							db.commit();
						}
					}	
				}
				

				JOptionPane.showMessageDialog(getContentPane(), "Successfully saved!", "Save", JOptionPane.INFORMATION_MESSAGE);
			}	

			displayDV_pv(modelMC_cv,co_id);

		}else {
			JOptionPane.showMessageDialog(getContentPane(), "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}



