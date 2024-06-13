package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JFormattedTextField;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_OffRemAccounts;

public class ZeroOutHOADues  extends _JInternalFrame implements _GUI, ActionListener {
	
	static Dimension SIZE = new Dimension(600, 470);
	private JTextField txtProj;
	private JTextField txtPhase;
	private JDateChooser dateSent;
	private _JLookup lookupProj;
	private _JLookup lookupPhase;
	
	private JButton btnUpdate;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _JLookup lookupClient;
	private JTextField txtClient;
	private JTextField txtUnitID;
	private JTextField txtUnit;
	private _JXFormattedTextField txtAmount;
	private JTextField txtUserID;
	private _JDateChooser dteHOADues;
	private JTextField txtRemarks;
	private Object objSeq;

	public ZeroOutHOADues() {
		super("Zero-Out Homeowner's Account Dues", false, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);

		JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
		this.add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			JPanel pnlDetails = new JPanel(new BorderLayout(5, 5)); 
			pnlMain.add(pnlDetails, BorderLayout.NORTH);
			pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Client Details"));
			pnlDetails.setPreferredSize(new Dimension(0, 200));
			{

				JPanel pnlWest = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlWest.setPreferredSize(new Dimension(100, 0));
				{
					pnlDetails.add(pnlWest, BorderLayout.WEST);
					{
						JLabel lblCompany = new JLabel("Company");
						pnlWest.add(lblCompany); 
					}
					{
						JLabel lblProject = new JLabel("Project");
						pnlWest.add(lblProject); 
					}
					{
						JLabel lblClient = new JLabel("Client");
						pnlWest.add(lblClient); 
					}
					{
						JLabel lblUnit = new JLabel("Unit");
						pnlWest.add(lblUnit); 
					}
					JPanel pnlEast = new JPanel(new GridLayout(4, 1, 5, 5));

					pnlEast.setPreferredSize(new Dimension(465, 0));
					pnlDetails.add(pnlEast, BorderLayout.EAST);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlEast.add(pnlCompany);
						lookupCompany = new _JLookup(null, "Company");
						lookupCompany.setPreferredSize(new Dimension(100, 20));
						pnlCompany.add(lookupCompany, BorderLayout.WEST);
						lookupCompany.setLookupSQL(FncGlobal.getCompany());
						lookupCompany.setReturnColumn(0);
						lookupCompany.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {

								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if (data != null) {
									txtCompany.setText(data[1].toString());
									lookupProj.setLookupSQL(FncGlobal.getProject(lookupCompany.getValue()));
									fncClearInput("COMPANY");
								}
								else {
									txtCompany.setText("");
								}

							}
						});
						
						txtCompany = new JTextField();
						pnlCompany.add(txtCompany, BorderLayout.EAST); 
						txtCompany.setPreferredSize(new Dimension(360, 50));
						txtCompany.setHorizontalAlignment(JTextField.CENTER);
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
						pnlEast.add(pnlProject);
						lookupProj = new _JLookup(null, "Project");
						lookupProj.setPreferredSize(new Dimension(100, 20));
						pnlProject.add(lookupProj, BorderLayout.WEST);
						lookupProj.setReturnColumn(0);
						lookupProj.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {

								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if (data != null) {
									txtProj.setText(data[1].toString());
									lookupClient.setLookupSQL(getClient(lookupProj.getValue().toString()));
									fncClearInput("PROJECT");
								}
								else {
									txtProj.setText("");
								}

							}
						});
						
						txtProj = new JTextField();
						pnlProject.add(txtProj); 
						txtProj.setPreferredSize(new Dimension(360, 50));
						txtProj.setHorizontalAlignment(JTextField.CENTER);
					}
					{
						JPanel pnlClient = new JPanel(new BorderLayout(5, 5));
						pnlEast.add(pnlClient);
						lookupClient = new _JLookup(null, "Client");
						lookupClient.setPreferredSize(new Dimension(100, 20));
						lookupClient.setFilterCardName(true);
						pnlClient.add(lookupClient, BorderLayout.WEST);
						lookupClient.setReturnColumn(1);
						lookupClient.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet(); 
								if (data != null) {
									txtClient.setText(data[2].toString());
									txtUnitID.setText(data[6].toString());
									txtUnit.setText(data[4].toString());
									objSeq = data[7];
									txtAmount.setEnabled(true);
									fncClearInput("CLIENT");
								} else {
									txtClient.setText("");
								}
								
							}
						});

						txtClient = new JTextField(); 
						pnlClient.add(txtClient);
						txtClient.setPreferredSize(new Dimension(360, 50));
						txtClient.setHorizontalAlignment(JTextField.CENTER);
					}
					{
						JPanel pnlUnit = new JPanel(new BorderLayout(5, 5));
						pnlEast.add(pnlUnit);
						txtUnitID = new JTextField(); 
						txtUnitID.setPreferredSize(new Dimension(100, 20));
						txtUnitID.setHorizontalAlignment(JTextField.CENTER);
						pnlUnit.add(txtUnitID, BorderLayout.WEST);
						
						txtUnit = new JTextField(); 
						pnlUnit.add(txtUnit);
						txtUnit.setPreferredSize(new Dimension(360, 50));
						txtUnit.setHorizontalAlignment(JTextField.CENTER);
					}
				}
			}

			JPanel pnlDataInput = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlDataInput, BorderLayout.CENTER); 
			pnlDataInput.setBorder(BorderFactory.createEtchedBorder());
			
			{
				JPanel pnlLblInput = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlDataInput.add(pnlLblInput, BorderLayout.WEST); 
				pnlLblInput.setPreferredSize(new Dimension(102, 0));
				{
					JLabel lblAmount = new JLabel(" Amount");
					pnlLblInput.add(lblAmount); 
					
					JLabel lblUser = new JLabel(" By");
					pnlLblInput.add(lblUser); 
					
					JLabel lblDate = new JLabel(" Date");
					pnlLblInput.add(lblDate); 
				
					JLabel lblRemarks = new JLabel(" Remarks");
					pnlLblInput.add(lblRemarks); 
					
				}
				
				JPanel pnlInputComp = new JPanel(new GridLayout(4, 1, 5, 5)); 
				pnlDataInput.add(pnlInputComp, BorderLayout.CENTER); 
				
				{
					txtAmount = new _JXFormattedTextField("0.00"); 
					pnlInputComp.add(txtAmount); 
					txtAmount.setHorizontalAlignment(JTextField.CENTER);
					txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL); 
					
					
					txtUserID = new JTextField(); 
					pnlInputComp.add(txtUserID);
					txtUserID.setEditable(false);
					txtUserID.setHorizontalAlignment(JTextField.CENTER);
					
					dteHOADues = new _JDateChooser(); 
					pnlInputComp.add(dteHOADues);
					dteHOADues.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					
					txtRemarks = new JTextField(); 
					pnlInputComp.add(txtRemarks);
	
				}

				JPanel pnlEast = new JPanel(new BorderLayout(5, 5));
				pnlDataInput.add(pnlEast, BorderLayout.EAST); 
				pnlEast.setPreferredSize(new Dimension(2, 0));
				
			}

			
			JPanel pnlSouth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH); 
			pnlSouth.setPreferredSize(new Dimension(0, 30));
			
			{
				btnUpdate = new JButton("UPDATE"); 
				pnlSouth.add(btnUpdate);
				btnUpdate.setPreferredSize(new Dimension(30, 25));
				btnUpdate.addActionListener(this);
							
			}

		}
		
	 txtUserID.setText(UserInfo.FullName2);
	}
	
	private String getClient(String proj_id) {
		String SQL = "SELECT "
				+ "a.status_id as \"Status\", "
				+ "a.entity_id as \"Client Code\", "
				+ "get_client_name(a.entity_id) AS \"Client Name\", "
				+ "get_project_name(a.projcode) AS \"Project\", "
				+ "b.description as \"Unit Description\", "
				+ "a.projcode as \"Project Code\", "
				+ "a.pbl_id as \"Unit Code\", "
				+ "a.seq_no as \"Seq No\" "
				+ "FROM rf_sold_unit a "
				+ "LEFT JOIN mf_unit_info b ON b.proj_id = a.projcode AND b.pbl_id = a.pbl_id\n"
				+ "WHERE a.status_id = 'A'\n"
				+ "AND EXISTS (SELECT * FROM rf_buyer_status\n"
				+ "			WHERE entity_id = a.entity_id\n"
				+ "			AND proj_id = a.projcode\n"
				+ "			AND pbl_id = a.pbl_id\n"
				+ "			AND seq_no = a.seq_no \n"
				+ "			AND status_id = 'A'\n"
				+ "			AND TRIM(byrstatus_id) in ('78', '76'))\n"
//				+ "AND EXISTS (SELECT * FROM rf_buyer_status\n"
//				+ "			WHERE entity_id = a.entity_id\n"
//				+ "			AND proj_id = a.projcode\n"
//				+ "			AND pbl_id = a.pbl_id\n"
//				+ "			AND seq_no = a.seq_no \n"
//				+ "			AND status_id = 'A'\n"
//				+ "			AND TRIM(byrstatus_id) = '76')\n"
				+ "AND a.projcode = '"+proj_id+"'\n"
				+ "and exists(select * from rf_water_reading_v2 where entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no and status_id = 'A') \n"
				+ "ORDER BY get_project_name(a.projcode), get_client_name(a.entity_id);";
		FncSystem.out("getClient", SQL);
		return SQL;
	}
	
	private void fncClearInput(String from) {
		if (from.equals("UPDATE")) {
			lookupCompany.setValue("");
			txtCompany.setText("");
			lookupProj.setValue("");
			txtProj.setText("");
			lookupClient.setValue("");
			txtClient.setText("");
			txtUnitID.setText("");
			txtUnit.setText("");
			txtAmount.setValue(null);
			dteHOADues.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			txtRemarks.setText("");
		} else if (from.equals("COMPANY")) {
			lookupProj.setValue("");
			txtProj.setText("");
			lookupClient.setValue("");
			txtClient.setText("");
			txtUnitID.setText("");
			txtUnit.setText("");
			txtAmount.setValue(null);
			dteHOADues.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			txtRemarks.setText("");
		} else if (from.equals("PROJECT")) {
			lookupClient.setValue("");
			txtClient.setText("");
			txtUnitID.setText("");
			txtUnit.setText("");
			txtAmount.setValue(null);
			dteHOADues.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			txtRemarks.setText("");
		} else if (from.equals("CLIENT")) {
			txtAmount.setValue(null);
			dteHOADues.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			txtRemarks.setText("");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();
		if(action.equals("UPDATE")){
			fncUpdate();
		}
	}
	
	private void fncUpdate() {
		String entity_id = lookupClient.getValue();
		String proj_id = lookupProj.getValue();
		String pbl_id = txtUnitID.getText();
		Object seq_no = objSeq;
		BigDecimal amount = (BigDecimal) txtAmount.getValued();
		String by = UserInfo.EmployeeCode;
		String date = dteHOADues.getDateString();
		String remarks = txtRemarks.getText();
		
		System.out.println("Entity: ID "+entity_id);
		System.out.println("Project: ID "+proj_id);
		System.out.println("Pbl ID: "+pbl_id);
		System.out.println("Seq No: "+seq_no);
		System.out.println("Amount: "+amount);
		System.out.println("By: "+by);
		System.out.println("Date: "+date);
		System.out.println("Remarks: "+remarks);
		
		String SQL = "select sp_insert_zero_out_adjustment("
				+ "'"+entity_id+"',"
				+ "'"+proj_id+"',"
				+ "'"+pbl_id+"',"
				+ ""+seq_no+","
				+ ""+amount+","
				+ "'"+by+"',"
				+ "'"+date+"',"
				+ "'"+remarks+"')";
		
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Save Adjustment for \n" + txtClient.getText() + " ?\n"
				+ "Amount: "+amount+"\n"
				+ "Date: "+date+"\n"
				+ "Remarks: "+remarks+"\n");
		
		if(confirmation == 0) {			
//			fncAction(SQL);
			pgSelect db = new pgSelect();
			db.select(SQL);
			if (db.getResult()[0][0].equals("DATE")){
				JOptionPane.showMessageDialog(null, "Please check if date is correct.");
			}
			if (db.getResult()[0][0].equals("SUCCESS")){
				fncClearInput("UPDATE");
				FncSystem.out("sp_insert_zero_out_adjustment", SQL);
				JOptionPane.showMessageDialog(null, "Added Successfully.");
			}
		} else {
			System.out.println("Cancelled");
		}
	}

	private void fncAction(String SQL) {
		pgSelect db = new pgSelect();
		db.select(SQL);
	}
}
