package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelClientComplaints;
import DateChooser._JDateChooser;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JTableMain;
import components._JXTextField;
/**
 * @author John Lester Fatallo
 */
public class pnlClientComplaint extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -7788899907504792982L;
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlNorth;

	private JPanel pnlComplaintWest;

	private JPanel pnlCWLabel;
	private JLabel lblComplaintType;
	private JLabel lblSource;
	private JLabel lblConcernedDept;
	private JLabel lblComplainantContactNo;

	private JPanel pnlCWCenter;

	private JPanel pnlComplaintType;
	private _JLookup lookupComplaintType;
	private _JXTextField txtComplaintType;

	private JPanel pnlSource;
	private _JLookup lookupSource;
	private _JXTextField txtSource;

	private JPanel pnlAddressed;
	private _JLookup lookupConcernedDept;
	private _JXTextField txtAddressedTo;

	private JPanel pnlComplainantsContactNo;
	private JTextField txtContactNo;

	private JPanel pnlComplaintEast;
	private JScrollPane scrollComplaintDetails;
	private JTextArea txtAreaComplaintDetails;

	private JPanel pnlPunchlist;

	private JPanel pnlPunchListWest;

	private JPanel pnlPWLabel;
	private JLabel lblInspectionDate;
	private JLabel lblInspector;
	private JLabel lblCallDate;
	private JLabel lblSeparator;

	private JPanel pnlPWCenter;

	private JPanel pnlInspectionDate;
	private _JDateChooser inspectionDate;

	private JPanel pnlInspectedBy;
	private _JLookup lookupInspector;
	private _JXTextField txtInspector;

	private JPanel pnlRectification;
	private _JDateChooser rectificationDate;

	private JPanel pnlCenter;
	private JScrollPane scrollComplaintList;
	private _JTableMain tblComplaintList;
	private JList rowHeaderComplaintList;
	private modelClientComplaints modelCompList;

	private JPanel pnlComplaintLeft;
	private JLabel lblComplaintNo;
	private _JLookup lookupComplaint;
	private JLabel lblDateReceived;
	private _JDateChooser dateReceived;

	private JPanel pnlComplaintRight;

	private JPanel pnlPunchlistLeft;

	private JPanel pnlPunchlistRight;
	private JLabel lblPunchListFind;
	private JScrollPane scrollAreaFindings;
	private JTextArea txtAreaFindings;

	private ClientFeedback cf;
	
	public pnlClientComplaint(ClientFeedback cf) {
		this.cf=cf;
		initGUI();
	}

	public pnlClientComplaint(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlClientComplaint(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlClientComplaint(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel(new GridLayout(1, 2, 5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Complaint Details"));	
			{
				pnlComplaintWest = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlComplaintWest, BorderLayout.WEST);
				pnlComplaintWest.setBorder(JTBorderFactory.createTitleBorder("Complaint Details"));
				{
					pnlCWLabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlComplaintWest.add(pnlCWLabel, BorderLayout.WEST);
					{
						lblComplaintType = new JLabel("*Complaint Type    ");
						pnlCWLabel.add(lblComplaintType);
					}
					{
						lblSource = new JLabel("*Source");
						pnlCWLabel.add(lblSource);
					}
					{
						lblConcernedDept = new JLabel("*Concerned Dept");
						pnlCWLabel.add(lblConcernedDept);
					}
					{
						lblComplainantContactNo = new JLabel("*Complainant's Contact No.");
						pnlCWLabel.add(lblComplainantContactNo);
					}
				}
				{
					pnlCWCenter = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlComplaintWest.add(pnlCWCenter, BorderLayout.CENTER);
					{
						pnlComplaintType = new JPanel(new BorderLayout(5, 5));
						pnlCWCenter.add(pnlComplaintType);
						{
							lookupComplaintType = new _JLookup(null, "Complaint Type", 0);
							pnlComplaintType.add(lookupComplaintType, BorderLayout.WEST);
							lookupComplaintType.setPreferredSize(new Dimension(50, 0));
							lookupComplaintType.setLookupSQL(sqlComplaintType());
							lookupComplaintType.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									FncSystem.out("DIsplay sql for Complaint Type", sqlComplaintType());
									if(data != null){
										String complaint_type = (String) data[1];
										txtComplaintType.setText(complaint_type);
									}

								}
							});
						}
						{
							txtComplaintType = new _JXTextField("");
							pnlComplaintType.add(txtComplaintType, BorderLayout.CENTER);
						}
					}
					{
						pnlSource = new JPanel(new BorderLayout(5, 5));
						pnlCWCenter.add(pnlSource);
						{
							lookupSource = new _JLookup(null, "Source", 0);
							pnlSource.add(lookupSource, BorderLayout.WEST);
							lookupSource.setPreferredSize(new Dimension(50, 0));
							lookupSource.setLookupSQL(sqlSource());
							lookupSource.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									FncSystem.out("Display sql for Source", sqlSource());
									if(data != null){
										String source = (String) data[1];
										txtSource.setText(source);
									}

								}
							});
						}
						{
							txtSource = new _JXTextField("");
							pnlSource.add(txtSource, BorderLayout.CENTER);
						}

					}
					{
						pnlAddressed = new JPanel(new BorderLayout(5, 5));
						pnlCWCenter.add(pnlAddressed);
						{
							lookupConcernedDept = new _JLookup(null, "Addressed To", 0);
							pnlAddressed.add(lookupConcernedDept, BorderLayout.WEST);
							lookupConcernedDept.setPreferredSize(new Dimension(50, 0));
							lookupConcernedDept.setLookupSQL(sqlAddressedTo());
							lookupConcernedDept.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									FncSystem.out("Display sql for Addressed to", sqlAddressedTo());
									if(data != null){
										String addressed_to = (String) data[1];
										txtAddressedTo.setText(addressed_to);
									}
								}
							});
						}
						{
							txtAddressedTo = new _JXTextField("");
							pnlAddressed.add(txtAddressedTo, BorderLayout.CENTER);
						}
					}
					{
						pnlComplainantsContactNo = new JPanel(new BorderLayout(5, 5));
						pnlCWCenter.add(pnlComplainantsContactNo);
						{
							txtContactNo = new JTextField();
							pnlComplainantsContactNo.add(txtContactNo, BorderLayout.WEST);
							txtContactNo.setPreferredSize(new Dimension(100, 0));
						}
					}
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			this.add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Complaint Details"));
			{
				scrollComplaintDetails = new JScrollPane();
				pnlCenter.add(scrollComplaintDetails);
				{
					txtAreaComplaintDetails = new JTextArea();
					scrollComplaintDetails.setViewportView(txtAreaComplaintDetails);
					txtAreaComplaintDetails.setBorder(lineBorder);
					txtAreaComplaintDetails.setLineWrap(true);
					txtAreaComplaintDetails.setEditable(false);
				}
			}
		}
		
		
		/*{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			this.add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Complaint List"));
			{
				scrollComplaintList = new JScrollPane();
				pnlCenter.add(scrollComplaintList, BorderLayout.CENTER);
				scrollComplaintList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				{
					modelCompList = new modelClientComplaints();
					tblComplaintList = new _JTableMain(modelCompList);
					scrollComplaintList.setViewportView(tblComplaintList);
					tblComplaintList.hideColumns("Category Code", "Source Code", "Addressed to Code", "Complaint Details");
					tblComplaintList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

					modelCompList.addTableModelListener(new TableModelListener() {
						
						@Override
						public void tableChanged(TableModelEvent e) {

							((DefaultListModel) rowHeaderComplaintList.getModel()).addElement(modelCompList.getRowCount());

							if(modelCompList.getRowCount() == 0){
								rowHeaderComplaintList.setModel(new DefaultListModel());
							}
						}
					});
					
					tblComplaintList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e) {
							if(!e.getValueIsAdjusting()){
								if(tblComplaintList.getSelectedRows().length == 1){

									int selected_row = tblComplaintList.convertRowIndexToModel(tblComplaintList.getSelectedRow());
									
									String comp_no = (String) modelCompList.getValueAt(selected_row, 0);
									String ctgry_code = (String) modelCompList.getValueAt(selected_row, 1);
									String ctgry_desc = (String) modelCompList.getValueAt(selected_row, 2);
									String src_id = (String) modelCompList.getValueAt(selected_row, 3);
									String src_desc = (String) modelCompList.getValueAt(selected_row, 4);
									String concerned_dept = (String) modelCompList.getValueAt(selected_row, 5);
									String dept_name = (String) modelCompList.getValueAt(selected_row, 6);
									String contact_no = (String) modelCompList.getValueAt(selected_row, 7);
									String comp_details = (String) modelCompList.getValueAt(selected_row, 8);
									
									lookupComplaintType.setValue(ctgry_code);
									txtComplaintType.setText(ctgry_desc);
									lookupSource.setValue(src_id);
									txtSource.setText(src_desc);
									lookupConcernedDept.setValue(concerned_dept);
									txtAddressedTo.setText(dept_name);
									txtContactNo.setText(contact_no);
									txtAreaComplaintDetails.setText(comp_details);
									
									cf.btnState(true, false, true, false, false);
									ap = new pnlActionPlan();
									
									if(ap != null){
										System.out.println("Dumaan dito sa Display ng action plan");
										ap.displayActionPlan(comp_no);
									}
								}
							}
						}
					});
				}
				{
					rowHeaderComplaintList = tblComplaintList.getRowHeader();
					rowHeaderComplaintList.setModel(new DefaultListModel());
					scrollComplaintList.setRowHeaderView(rowHeaderComplaintList);
					scrollComplaintList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}*/
		cf.setComponentsEditable(this, false);
	}//XXX END OF INIT GUI

	private String sqlComplaintType(){ //SQL FOR THE LOOKUP OF COMPLAINT TYPE
		return "select trim(ctgry_code) as \"ID\", trim(ctgry_desc) as \"Description\" from mf_complaint_category where ctgry_code not in ('01', '04', '05')";
	}
	private String sqlSource(){
		return "select trim(src_id) as \"ID\", trim(complain_source) as \"Description\" from mf_complaint_sources";
	}

	private String sqlReceivedby(){// SQL FOR THE LOOKUP OF THE RECEIVED BY	
		return "select trim(a.emp_code) as \"ID\", trim(b.entity_name) as \"Employee Name\" \n" + 
		"from em_employee a\n" + 
		"left join rf_entity b on b.entity_id = a.entity_id\n" + 
		"where a.dept_code in ('02','61')";
	}

	private String sqlAddressedTo(){// SQL FOR THE LOOKUP OF THE ADDRESSED TO
		return "select trim(dept_code) as \"Dept. Code\", trim(dept_name) as \"Dept. Name\" \n" + 
		"from mf_department\n" + 
		"where dept_code != '';";
	}

	public void displayComplaints(String compl_no){//sql for displaying complaint details after lookup 

		String sql = "select a.date_complained, \n" + 
				"trim(a.category_code), trim(b.ctgry_desc),\n" + 
				"trim(a.src_id), trim(c.complain_source),\n" + 
				"trim(a.comp_rcvd_by), trim(e.entity_name),\n" + 
				"trim(a.comp_addressed_to), \n" + 
				"trim(f.dept_name),\n" + 
				"trim(a.contact_no), trim(a.complaint_details),\n" + 
				"g.date_inspection,trim(g.inspected_by),\n" + 
				"(select trim(e.entity_name)\n" + 
				"from em_employee d\n" + 
				"left join rf_entity e on e.entity_id = d.entity_id\n" + 
				"where d.emp_code = g.inspected_by),\n" + 
				"g.date_rectification, g.punchlist_findings  \n" + 
				"from rf_client_complaints a\n" + 
				"left join mf_complaint_category b on b.ctgry_code = a.category_code\n" + 
				"left join mf_complaint_sources c on c.src_id = a.src_id\n" + 
				"left join em_employee d on d.emp_code = a.comp_rcvd_by\n" + 
				"left join rf_entity e on e.entity_id = d.entity_id \n" + 
				"left join mf_department f on f.dept_code = a.comp_addressed_to\n" + 
				"left join rf_complaint_punchlist g on g.compl_no = a.compl_no\n" + 
				"where a.compl_no = '"+compl_no+"' ";

		FncSystem.out("Display Complaint Details", sql);		
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()){
			//dateReceived.setDate((Date) db.getResult()[0][0]);
			lookupComplaintType.setValue((String) db.getResult()[0][1]);
			txtComplaintType.setText((String) db.getResult()[0][2]);
			lookupSource.setValue((String) db.getResult()[0][3]);
			txtSource.setText((String) db.getResult()[0][4]);
			/*lookupReceivedBy.setValue((String) db.getResult()[0][5]);
			txtReceivedBy.setText((String) db.getResult()[0][6]);*/
			lookupConcernedDept.setValue((String) db.getResult()[0][7]);
			txtAddressedTo.setText((String) db.getResult()[0][8]);
			txtContactNo.setText((String) db.getResult()[0][9]);
			txtAreaComplaintDetails.setText((String) db.getResult()[0][10]);
		}
	}

	public void newClientComplaint(){
		clearClientComplaint();
		lookupComplaintType.setEditable(true);
		lookupSource.setEditable(true);
		lookupConcernedDept.setEditable(true);
		txtContactNo.setEditable(true);
		txtAreaComplaintDetails.setEditable(true);
	}

	public void editClientComplaint(){
		
		lookupComplaintType.setEditable(true);
		lookupSource.setEditable(true);
		lookupConcernedDept.setEditable(true);
		txtContactNo.setEditable(true);
		txtAreaComplaintDetails.setEditable(true);
		
		/*lookupComplaintType.setEditable(true);
		lookupSource.setEditable(true);
		lookupReceivedBy.setEditable(true);
		lookupAddressedTo.setEditable(true);
		txtContactNo.setEditable(true);
		txtAreaComplaint.setEditable(true);*/

	}

	public boolean toEdit(){
		
		if(lookupComplaint.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please search complaint before editing"), "Edit", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	private boolean isComplaintExisting(String compl_no){

		pgSelect db = new pgSelect();
		db.select("select * from rf_client_complaints where compl_no = '"+ compl_no +"'");
		return db.isNotNull();
	}

	public String Punchlist(){
		String punchlist = txtAreaFindings.getText();
		return punchlist;
	}

	public boolean toSave(){
		//String compl_no = cf.getCompl_No();

		if(lookupComplaintType.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select Complaint Type"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(lookupSource.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select Source"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(lookupConcernedDept.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select Address To"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtContactNo.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Contact No"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtAreaComplaintDetails.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Complaint Details"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtContactNo.getText().length() > 11){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Contact No. with max length of 11"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}
	// SAVING OF DATA IN THE rf_client_complaints
	public boolean saveComplaints(String entity_id, String proj_code, String unit_id, String seq_no ,String compl_no){
		pgUpdate db = new pgUpdate();

		String src_id = lookupSource.getValue();
		String addressed_to = lookupConcernedDept.getValue();
		String category_code = lookupComplaintType.getValue();
		String comp_details = txtAreaComplaintDetails.getText().replace("'", "''");
		String contact_no = txtContactNo.getText();

		if(isComplaintExisting(compl_no)){
			String sql = "UPDATE rf_client_complaints\n" + 
					"SET entity_id='"+entity_id+"', projcode='"+proj_code+"', pbl_id= getinteger('"+unit_id+"')::VARCHAR, \n"+
					"seq_no= "+seq_no+", src_id='"+src_id+"', date_complained= now(), comp_rcvd_by='"+UserInfo.EmployeeCode+"', \n" + 
					"comp_addressed_to='"+addressed_to+"', addressee='', complaint_details='"+comp_details+"', update_by='"+UserInfo.EmployeeCode+"', update_date= now(), \n" + 
					"category_code= '"+category_code+"', status_id='A', contact_no= '"+contact_no+"'\n" + 
					"WHERE compl_no = '"+compl_no+"' \n";
			db.executeUpdate(sql, true);
		}else{
			String sql = "INSERT INTO rf_client_complaints(\n" + 
					"entity_id, compl_no, projcode, pbl_id, seq_no, src_id, \n" + 
					"date_complained, comp_rcvd_by, comp_addressed_to, \n" + 
					"addressee, complaint_details, \n" + 
					"add_by, add_date, category_code, status_id, contact_no)\n" + 
					"VALUES ('"+entity_id+"', '"+compl_no+"', '"+proj_code+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", \n"+
					"'"+src_id+"', now(), '"+UserInfo.EmployeeCode+"', '"+addressed_to+"', '', '"+comp_details+"', \n"+
					"'"+UserInfo.EmployeeCode+"' , now(), '"+category_code+"', 'A', '"+contact_no+"') \n";
			db.executeUpdate(sql, true);
		}
		db.commit();
		return true;
	}

	private boolean isPunchlistExisting(String compl_no){

		pgSelect db = new pgSelect();
		db.select("select * from rf_complaint_punchlist where compl_no = '"+compl_no+"'");
		return db.isNotNull();

	}

	//Saving of Data into rf_complaint_punchlist
	public boolean savePunchlist(String entity_id, String compl_no){
		pgUpdate db = new pgUpdate();

		String category_code = lookupComplaintType.getValue();
		Date inspect_date = inspectionDate.getDate();
		String inspected_by = "";
		Date rectification_Date = rectificationDate.getDate();
		String punchlist_findings = txtAreaFindings.getText().replace("'", "''");

		if(lookupInspector.getValue() != null){
			inspected_by = lookupInspector.getValue();
		}

		if(isPunchlistExisting(compl_no)){
			String sql = "UPDATE rf_complaint_punchlist\n" + 
					"SET entity_id='"+entity_id+"', compl_no='"+compl_no+"', ctgry_code='"+category_code+"', date_inspection= nullif('"+inspect_date+"', 'null')::TIMESTAMP, \n" + 
					"inspected_by='"+inspected_by+"', date_rectification= nullif('"+rectification_Date+"', 'null')::TIMESTAMP, status_id='A', punchlist_findings= '"+punchlist_findings+"'\n" + 
					"WHERE compl_no = '"+compl_no+"' \n";
			db.executeUpdate(sql, true);
		}else{
			String sql = "INSERT INTO rf_complaint_punchlist(\n" + 
					"entity_id, compl_no, ctgry_code, date_inspection, inspected_by, \n" + 
					"date_rectification, status_id, created_by, date_created, punchlist_findings)\n" + 
					"VALUES ('"+entity_id+"', '"+compl_no+"', '"+category_code+"', nullif('"+inspect_date+"', 'null')::TIMESTAMP , \n"+
					"'"+inspected_by+"' , nullif('"+rectification_Date+"', 'null')::TIMESTAMP, 'A','"+UserInfo.EmployeeCode+"' , now(), '"+punchlist_findings+"') \n";

			db.executeUpdate(sql, true);
		}
		db.commit();
		return true;
	}

	public boolean saveClientComplaints(String entity_id, String proj_code, String unit_id, String seq_no, String complain_no){

		//if(FncGlobal.connection != null){
		String complaint_details = txtAreaComplaintDetails.getText().replace("'", "''");

		/*try { //XXX UNCOMMENT ME IF DEPARTMENT HEADS HAVE EMAIL
		FncEmail.sendEmail(new String[]{"jfatallo@yahoo.com"}, new String[]{""}, "Client Complaint", complaint_details);
		} catch (MessagingException e) {}*/

		saveComplaints(entity_id, proj_code, unit_id, seq_no, complain_no);

		//savePunchlist(entity_id, complain_no);

		/*}else{
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Connection Unavailable", "Warning", JOptionPane.WARNING_MESSAGE);
		}*/

		cf.setComponentsEditable(this, false);
		/*inspectionDate.setEnabled(false);
		rectificationDate.setEnabled(false);*/
		clearClientComplaint();
		return true;
	}

	public void cancelClientComplaint(){ //OK NA DTIO
		clearClientComplaint();
		cf.setComponentsEditable(this, false);
	}

	public void clearClientComplaint(){ //OK NA DITO

		lookupComplaintType.setValue(null);
		txtComplaintType.setText("");
		lookupSource.setValue(null);
		txtSource.setText("");
		lookupConcernedDept.setValue(null);
		txtAddressedTo.setText("");
		txtContactNo.setText("");
		txtAreaComplaintDetails.setText("");

	}

	public String getCtgry_Code(){
		String ctgry_code = lookupComplaintType.getValue();
		return ctgry_code;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
