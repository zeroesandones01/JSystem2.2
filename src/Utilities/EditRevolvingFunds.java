package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import Database.pgSelect;
import Database.pgUpdate;
import Dialogs.dlg_Edit_Revolving_Funds;
import Functions.FncGlobal;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._JCheckBox;
import components._JInternalFrame;
import interfaces._GUI;

public class EditRevolvingFunds extends _JInternalFrame  implements _GUI {
	private static String title = "Edit Revolving Funds";
	private static Dimension size = new Dimension(500,300);
	private static JPanel pnlMain;
	private static _JLookup lookCompany,lookEmp,lookControlNo,lookRequestType,lookType;
	private static JDateChooser dateCreated;
	private static JLabel lblCompanyValue,lblEmpValue,lblRequestType; 
	private static JButton btnSave,btnCancel,btnSearch;
	public static JTextField txtRplfNo;
//	private static _JLookupTable dlg;
	private static dlg_Edit_Revolving_Funds dlg;

	public EditRevolvingFunds() {
		super(title, true, true, true,true);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(size);
		this.setPreferredSize(size);
		this.setLayout(new BorderLayout(5,5));

		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			pnlMain.setBorder(new EmptyBorder(5,5,5,5));
			this.add(pnlMain,BorderLayout.CENTER);

			{
				JPanel pnlMainEast = new JPanel(new GridLayout(7, 1, 3, 4));
				pnlMain.add(pnlMainEast,BorderLayout.WEST);
				{
					JLabel lblCompany = new JLabel("Company:");
					pnlMainEast.add(lblCompany);
				}
				{
					JLabel lblEmp = new JLabel("Employee:");
					pnlMainEast.add(lblEmp);
				}
				{
					JLabel lblDate = new JLabel("Date Created:");
					pnlMainEast.add(lblDate);
				}
				{
					JLabel lblType = new JLabel("Type:");
					pnlMainEast.add(lblType);
				}
				{
					JLabel lblContNo = new JLabel("Control No.:");
					pnlMainEast.add(lblContNo);
				}
				{
					JLabel lblRplf = new JLabel("Rplf No.:");
					pnlMainEast.add(lblRplf);
				}
				{
					JLabel lblRequestType = new JLabel("Request Type:");
					pnlMainEast.add(lblRequestType);
				}
			}
			{
				JPanel pnlMainCenter = new JPanel(new GridLayout(7, 1, 3, 3));
				pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
				{
					JPanel pnlCompany = new JPanel(new BorderLayout(5,5));
					pnlMainCenter.add(pnlCompany);

					{
						JPanel pnlCompanyWest = new JPanel(new BorderLayout(5,5));
						pnlCompany.add(pnlCompanyWest,BorderLayout.WEST);
						pnlCompanyWest.setPreferredSize(new Dimension(100,0));

						{
							lookCompany = new _JLookup();
							lookCompany.setEnabled(false);
							pnlCompanyWest.add(lookCompany);
						}
					}
					{
						JPanel pnlCompanyCenter = new JPanel(new BorderLayout(5,5));
						pnlCompany.add(pnlCompanyCenter,BorderLayout.CENTER);
						{
							lblCompanyValue = new JLabel("[ ]");
							pnlCompanyCenter.add(lblCompanyValue);
						}
					}
				}
				{
					JPanel pnlEmp = new JPanel(new BorderLayout(5,5));
					pnlMainCenter.add(pnlEmp);

					{
						JPanel pnlEmpWest = new JPanel(new BorderLayout(5,5));
						pnlEmp.add(pnlEmpWest,BorderLayout.WEST);
						pnlEmpWest.setPreferredSize(new Dimension(100,0));

						{
							lookEmp = new _JLookup();
							lookEmp.setEnabled(false);
							pnlEmpWest.add(lookEmp);
						}
					}
					{
						JPanel pnlEmpCenter = new JPanel(new BorderLayout(5,5));
						pnlEmp.add(pnlEmpCenter,BorderLayout.CENTER);
						{
							lblEmpValue = new JLabel("[ ]");
							pnlEmpCenter.add(lblEmpValue);
						}
					}
				}
				{
					JPanel pnlDate = new JPanel(new BorderLayout(5,5));
					pnlMainCenter.add(pnlDate);
					{
						JPanel pnlDateWest = new JPanel(new BorderLayout(5,5));
						pnlDate.add(pnlDateWest,BorderLayout.WEST);
						pnlDateWest.setPreferredSize(new Dimension(150,0));
						{
							dateCreated = new JDateChooser();
							pnlDateWest.add(dateCreated);
							dateCreated.setDateFormatString("MM/dd/yyyy");
							dateCreated.setEnabled(false);
						}
					}
				}
				{
					JPanel pnlType = new JPanel(new BorderLayout(5,5));
					pnlMainCenter.add(pnlType);
					{
						JPanel pnlTypeWest = new JPanel(new BorderLayout(5,5));
						pnlType.add(pnlTypeWest,BorderLayout.WEST);
						pnlTypeWest.setPreferredSize(new Dimension(150,0));
						{
							{
								lookType = new _JLookup(null,"Type");
								pnlTypeWest.add(lookType);
								lookType.setEnabled(true);
								lookType.setLookupSQL(displayType());
								lookType.setReturnColumn(0);
								lookType.addLookupListener(new LookupListener() {


									public void lookupPerformed(LookupEvent event) {
										if(lookType.getValue().equals("PCost")) {
											lookControlNo.setEnabled(true);
											lookControlNo.setLookupSQL(displayControlNoPcost());
											txtRplfNo.setEnabled(true);
											btnSearch.setEnabled(true);




										}else {
											lookControlNo.setEnabled(true);
											lookControlNo.setLookupSQL(displayControlNoTcost());
											txtRplfNo.setEnabled(true);
											btnSearch.setEnabled(true);


										}

									}
								});


							}
						}
					}
				}
				{
					JPanel pnlControlNo = new JPanel(new BorderLayout(5,5));
					pnlMainCenter.add(pnlControlNo);
					{
						JPanel pnlControlWest = new JPanel(new BorderLayout(5,5));
						pnlControlNo.add(pnlControlWest,BorderLayout.WEST);
						pnlControlWest.setPreferredSize(new Dimension(150,0));
						{
							{
								lookControlNo = new _JLookup(null,"Control Numbers",0);
								pnlControlWest.add(lookControlNo);
								lookControlNo.setEnabled(false);
								lookControlNo.addLookupListener(new LookupListener() {


									public void lookupPerformed(LookupEvent event) {
										ctrlValue();
										btnSave.setEnabled(true);
									}
								});

							}
						}
					}
				}
				{
					JPanel pnlRplfNo = new JPanel(new BorderLayout(5,5));
					pnlMainCenter.add(pnlRplfNo);
					{
						JPanel pnlRplfWest = new JPanel(new BorderLayout(5,5));
						pnlRplfNo.add(pnlRplfWest,BorderLayout.WEST);
						pnlRplfWest.setPreferredSize(new Dimension(150,0));
						{
							
						
								{
								txtRplfNo = new JTextField();
								pnlRplfWest.add(txtRplfNo);
								txtRplfNo.setEnabled(false);
								txtRplfNo.addKeyListener(new KeyAdapter() {
									public void keyTyped(KeyEvent c) {
										char key = c.getKeyChar();
										if(!(Character.isDigit(c.getKeyChar()) || (key==KeyEvent.VK_BACK_SPACE)
												|| (key==KeyEvent.VK_COMMA))) {
								JOptionPane.showMessageDialog(getContentPane(), "Numbers only", "Error",JOptionPane.ERROR_MESSAGE);
											c.consume();
											
										}
										
									}
								});
								}
							

						}

					}
					{
					JPanel pnlButtonSearch = new JPanel(new BorderLayout(5,5));
					pnlRplfNo.add(pnlButtonSearch,BorderLayout.CENTER);
					pnlButtonSearch.setPreferredSize(new Dimension(50,0));
					btnSearch = new JButton("Search");
					pnlButtonSearch.add(btnSearch);
					btnSearch.setEnabled(false);
					btnSearch.addActionListener(new ActionListener() {
						

						public void actionPerformed(ActionEvent e) {
//							dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Rplf No.", displayRplf(), false);
							dlg = new dlg_Edit_Revolving_Funds(FncGlobal.homeMDI);
							dlg.setLocationRelativeTo(btnSearch);
							dlg.setVisible(true);
//							Object[] data = dlg.getReturnDataSet();
//							if(data!=null) {
//								txtRplfNo.setText((String)data[0]);
//							
//							}
							
						}
					});


				}
				}
				{
					JPanel pnlRequestType = new JPanel(new BorderLayout(5,5));
					pnlMainCenter.add(pnlRequestType);
					{
						JPanel pnlRequestWest = new JPanel(new BorderLayout(5,5));
						pnlRequestType.add(pnlRequestWest,BorderLayout.WEST);
						pnlRequestWest.setPreferredSize(new Dimension(150,0));
						{
							{
								lookRequestType = new _JLookup(null,"Request Type",0);
								pnlRequestWest.add(lookRequestType);
								lookRequestType.setEnabled(false);


							}
						}
					}
					{
						JPanel pnlRequestCenter = new JPanel(new BorderLayout(5,5));
						pnlRequestType.add(pnlRequestCenter,BorderLayout.CENTER);
						{
							lblRequestType = new JLabel("[ ]");
							pnlRequestCenter.add(lblRequestType);
						}
					}
				}

			}
			{
				JPanel pnlMainPageEnd = new JPanel(new GridLayout(1,2,3,3));
				pnlMain.add(pnlMainPageEnd,BorderLayout.PAGE_END);
				pnlMainPageEnd.setPreferredSize(new Dimension(0, 30));
				
				{
					btnSave = new JButton("Save");
					pnlMainPageEnd.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {
			
						public void actionPerformed(ActionEvent e) {
							if(JOptionPane.showConfirmDialog(getContentPane(), "Save Update?", "Save Update", 
									JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
								saveValues();
								lookCompany.setValue(null);
								lblCompanyValue.setText("[ ]");
								lookEmp.setValue(null);
								lblEmpValue.setText("[ ]");
								dateCreated.setDate(null);
								lookType.setValue(null);
								lookControlNo.setValue(null);
								lookControlNo.setEnabled(false);
								txtRplfNo.setText("");
								txtRplfNo.setEnabled(false);
								lookRequestType.setValue(null);
								lblRequestType.setText("[ ]");
								btnSave.setEnabled(false);
								JOptionPane.showMessageDialog(getContentPane(), "Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
								
							}
						}
					});
				}
				{
					btnCancel = new JButton("Cancel");
					pnlMainPageEnd.add(btnCancel);
					btnCancel.addActionListener(new ActionListener() {


						public void actionPerformed(ActionEvent arg0) {
							lookCompany.setValue(null);
							lblCompanyValue.setText("[ ]");
							lookEmp.setValue(null);
							lblEmpValue.setText("[ ]");
							dateCreated.setDate(null);
							lookType.setValue(null);
							lookControlNo.setValue(null);
							lookControlNo.setEnabled(false);
							txtRplfNo.setText("");
							txtRplfNo.setEnabled(false);
							lookRequestType.setValue(null);
							lblRequestType.setText("[ ]");
							btnSave.setEnabled(false);
							btnSearch.setEnabled(false);


						}
					});
				}
			}
		}
	}
	public static String displayControlNoPcost(){

		String query = "select distinct on (trim(control_no)) trim(control_no):: VARCHAR AS \"Control Number\" from rf_revolving_funds WHERE status_id = 'A' and replenish_date is null  ORDER BY trim(control_no)";
		return query;
	}
	public static String displayControlNoTcost(){
		String query = "SELECT DISTINCT ON (TRIM(control_no)) (TRIM(control_no)):: VARCHAR AS \"Control Number\" FROM rf_tcost_revolving_funds WHERE status_id = 'A' and replenish_date is null ORDER BY TRIM(control_no)";
		return query;
	}
	public static String displayType(){

		String query = "select edit_type from tmp_edit_revolve";
		return query;
	}
	public static String displayRplf() {
		String query = "select rplf_no from rf_request_header  where rplf_type_id  = '06' and status_id = 'A' order by rplf_no";
		return query;
	}

	public void ctrlValue(){
		Object[] value = ctrlQuery();
		if(value != null){
			lookCompany.setText((String)value[0]);
			lblCompanyValue.setText(String.format("[%s]",value[1]));
			lookEmp.setText((String) value[2]);
			lblEmpValue.setText(String.format("[%s]", value[3]));
			dateCreated.setDate((Date) value[4]);
			lookRequestType.setValue("06");
			lblRequestType.setText("[CASH FUNDS REPLENISHMENT]");



		}
	}
	public static Object[] ctrlQuery(){
		String crtlNo = lookControlNo.getValue();
		System.out.println("ctrl no:"+crtlNo);
		if(lookType.getValue().equals("PCost")){
			pgSelect db= new pgSelect();
			String query = "select a.co_id,b.company_name,a.emp_id,d.entity_name,a.date_created/*,a.control_no*/ from rf_revolving_funds  a "
					+ "left join mf_company b on a.co_id = b.co_id "
					+ "left Join em_employee c on a.emp_id = c.emp_code  "
					+ "left join rf_entity d on c.entity_id = d.entity_id where a.control_no = '"+crtlNo+"'";
			db.select(query);
			if(db != null){
				return db.getResult()[0];

			}

		}
		else if(lookType.getValue().equals("TCost")){
			pgSelect db= new pgSelect();
			String query = "select a.co_id,b.company_name,a.emp_id,d.entity_name,a.date_created from rf_tcost_revolving_funds  a "
					+ "left join mf_company b on a.co_id = b.co_id "
					+ "left Join em_employee c on a.emp_id = c.emp_code  "
					+ "left join rf_entity d on c.entity_id = d.entity_id where a.control_no = '"+crtlNo+"'";
			db.select(query);
			if(db != null){
				return db.getResult()[0];

			}
		}

		return null;


	}
	public static void saveValues() {
		String controlNo = lookControlNo.getValue();
		String RplfNo = txtRplfNo.getText();
		if(RplfNo.equals("") || RplfNo.equals("null") || RplfNo==null) {
			RplfNo = "";
		} else {
			RplfNo = txtRplfNo.getText();
		}
//		int intRplfNo = Integer.parseInt(RplfNo);
		System.out.println("rplf_nooo:"+RplfNo);
		if(lookType.getValue().equals("PCost")) {
			pgUpdate db = new pgUpdate();
			db.executeUpdate("UPDATE rf_revolving_funds  set rplf_no = '"+RplfNo+"',replenish_date = now() where  control_no = '"+controlNo+"'" 
					,true);
			db.commit();
		}
		else if(lookType.getValue().equals("TCost")) {
			pgUpdate db = new pgUpdate();
			db.executeUpdate("UPDATE rf_tcost_revolving_funds  set rplf_no = '"+RplfNo+"',replenish_date = now() where  control_no = '"+controlNo+"'" 
					,true);
			db.commit();
		}
		
			
		
	}
	
	public static void disposable() {
		dlg.dispose();
	}

}
