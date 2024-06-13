/**
 * 
 */
package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import Database.pgSelect;
import Database.pgUpdate;
import components._JXTextField;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

/**
 * @author John Lester Fatallo
 */
public class pnlResolvedby extends JPanel implements _GUI, ActionListener {
	
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	
	private JPanel pnlNorthWest;
	private JLabel lblResolvedDate;
	private JLabel lblResolvedby;
	
	private JPanel pnlNorthCenter;
	private JPanel pnlResolvedDate;
	private _JDateChooser dateResolved;
	private JPanel pnlResolvedby;
	private _JLookup lookupResolvedByNo;
	private _JXTextField txtResolvedBy;
	
	private JLabel lblActionTaken;
	private JScrollPane scrollActionTaken;
	private JTextArea txtAreaActionTaken;
	
	private ClientFeedback cf;
	
	public pnlResolvedby(ClientFeedback cf) {
		this.cf = cf;
		initGUI();
	}

	public pnlResolvedby(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlResolvedby(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlResolvedby(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(_EMPTY_BORDER);
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Accomplishment Details"));
			{
				pnlNorthWest = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				{
					lblResolvedDate = new JLabel("*Resolved Date");
					pnlNorthWest.add(lblResolvedDate);
				}
				{
					lblResolvedby = new JLabel("*Resolved by");
					pnlNorthWest.add(lblResolvedby);
				}
			}
			{
				pnlNorthCenter = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				{
					pnlResolvedDate = new JPanel(new BorderLayout(5, 5));
					pnlNorthCenter.add(pnlResolvedDate);
					{
						dateResolved = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlResolvedDate.add(dateResolved, BorderLayout.WEST);
						dateResolved.setPreferredSize(new Dimension(100, 20));
						dateResolved.setEnabled(false);
					}
				}
				{
					pnlResolvedby = new JPanel(new BorderLayout(5, 5));
					pnlNorthCenter.add(pnlResolvedby);
					{
						lookupResolvedByNo = new _JLookup(null, "Resolved By", 0);
						pnlResolvedby.add(lookupResolvedByNo, BorderLayout.WEST);
						lookupResolvedByNo.setPreferredSize(new Dimension(80, 0));
						//lookupResolvedByNo.setLookupSQL(sqlReceivedby());
						lookupResolvedByNo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if (data != null){
									String resolved_by = (String) data[1];
									txtResolvedBy.setText(resolved_by);
								}
							}
						});
					}
					{
						txtResolvedBy = new _JXTextField();
						pnlResolvedby.add(txtResolvedBy);
					}
				}
			}
		}
		{
			scrollActionTaken = new JScrollPane();
			this.add(scrollActionTaken, BorderLayout.CENTER);
			scrollActionTaken.setBorder(components.JTBorderFactory.createTitleBorder("Action Taken"));
			{
				txtAreaActionTaken = new JTextArea("");
				txtAreaActionTaken.setBorder(lineBorder);
				scrollActionTaken.setViewportView(txtAreaActionTaken);
				txtAreaActionTaken.setLineWrap(true);
				txtAreaActionTaken.setEditable(false);
			}
		}
		//SpringUtilities.makeCompactGrid(this, 2, 1, 3, 3, 3, 3, false);
		//cf.setComponentsEditable(pnlNorth, false);
	}//XXX END OF INIT GUI
	
	private String sqlResolvedBy(String compl_no){//

		return "select a.emp_code as \"ID\", get_employee_name(a.emp_code) as \"Employee Name\" \n" + 
			   "from em_employee a \n" + 
			   "left join rf_client_complaints b on b.comp_addressed_to = a.dept_code\n" + 
			   "where b.compl_no = '"+compl_no+"';\n";
	}
	
	public boolean toSave(){
		if(dateResolved.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input resolved date"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupResolvedByNo.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select resolved by"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtAreaActionTaken.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Action Taken"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean isResolvedExisting(String compl_no){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_complain_accomplishment where compl_no = '"+compl_no+"'";
		db.select(sql);
		FncSystem.out("Is resolved existing", sql);
		return db.isNotNull();
	}
	
	public void displayResolvedby(String complaint_no){//DISPLAYS THE ACTION TAKEN TO RESOLVE THE COMPLAINT
		pgSelect db = new pgSelect();
		String sql = "select resolve_date::DATE, trim(action_taken_by), trim(get_employee_name(action_taken_by)), trim(action_taken) from rf_complain_accomplishment where compl_no = '"+complaint_no+"';";
		db.select(sql);
		
		FncSystem.out("Display Resolved by", sql);
		if(db.isNotNull()){
			
			Date resolved_date = (Date) db.getResult()[0][0];
			String resolved_by_code = (String) db.getResult()[0][1];
			String resolved_by = (String) db.getResult()[0][2];
			String action_taken = (String) db.getResult()[0][3];
			
			dateResolved.setDate(resolved_date);
			lookupResolvedByNo.setValue(resolved_by_code);
			txtResolvedBy.setText(resolved_by);
			txtAreaActionTaken.setText(action_taken);
		}else{
			dateResolved.setDate(null);
			lookupResolvedByNo.setValue(null);
			txtResolvedBy.setText("");
			txtAreaActionTaken.setText("");
		}
		txtAreaActionTaken.setEditable(false);
	}
		
	public void newResolvedby(String compl_no){
		/*pgSelect db = new pgSelect();
		String sql = "select trim(person_in_charged), trim(get_employee_name(person_in_charged)) from rf_complain_action_plan where compl_no = '"+compl_no+"'";
		db.select(sql);
		String emp_code = (String) db.getResult()[0][0];
		String emp_name = (String) db.getResult()[0][1];
		
		lookupResolvedByNo.setValue(emp_code);
		txtResolvedBy.setText(emp_name);*/
		lookupResolvedByNo.setEditable(true);
		lookupResolvedByNo.setLookupSQL(sqlResolvedBy(compl_no));
		dateResolved.setEnabled(true);
		txtAreaActionTaken.setEditable(true);
	}
	
	public boolean saveResolvedDate(String compl_no){//SAVIGN FOR THE RESOLVED BY
		pgUpdate db = new pgUpdate();
		String action_taken_by = lookupResolvedByNo.getValue();
		String action_taken = txtAreaActionTaken.getText().replace("'", "''");
		Date date_resolved = (Date) dateResolved.getDate();
		//String ctgry_code = cc.getCtgry_Code();
		String ctgry_code = "";

		if(isResolvedExisting(compl_no)){
				String sql = "UPDATE rf_complain_accomplishment\n" + 
							 "SET action_taken='"+action_taken+"', resolve_date= nullif('"+date_resolved+"', 'null')::TIMESTAMP , \n" + 
							 "action_taken_by= '"+action_taken_by+"', edited_by='"+UserInfo.EmployeeCode+"', edited_date= now()\n" + 
							 "WHERE compl_no = '"+compl_no+"'";
				db.executeUpdate(sql, true);
		}else{
			String sql = "INSERT INTO rf_complain_accomplishment(\n" + 
						 "compl_no, ctgry_code, action_taken, resolve_date, \n" + 
						 "action_taken_by, created_by, created_date, status_id)\n" + 
						 "VALUES ('"+compl_no+"', '"+ctgry_code+"', '"+action_taken+"', nullif('"+date_resolved+"', 'null')::TIMESTAMP , \n" + 
						 "'"+action_taken_by+"', '"+UserInfo.EmployeeCode+"' , now() , 'A');";

			db.executeUpdate(sql, true);
		}
		
		db.commit();
		return true;
	}
	
	public void cancelResolved(){
		
		/*if(isResolvedExisting(compl_no) == false){
			dateResolved.setDate(null);
			lookupResolvedByNo.setValue(null);
			txtResolvedBy.setText("");
			txtAreaActionTaken.setText("");
		}*/
		
		dateResolved.setEnabled(false);
		//cf.setComponentsEditable(pnlNorth, false);
		lookupResolvedByNo.setEditable(false);
		txtAreaActionTaken.setEditable(false);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		

	}
}
