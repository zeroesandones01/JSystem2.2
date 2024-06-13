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
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelActionPlan;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
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
public class pnlActionPlan extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = 994010708302975170L;
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlMain;

	private JPanel pnlTarget;
	private JPanel pnlTargetLeft;

	private JPanel pnlTargetLeftLower;

	private JPanel pnlTargetRight;
	private JLabel lblActionPlan;

	private JPanel pnlResolved;
	private JPanel pnlResolvedLeft;
	private JLabel lblResolvedDate;
	private _JDateChooser dateResolved;
	private JLabel lblResolvedby;
	private _JLookup lookupResolvedByNo;
	private JTextField txtResolvedBy;
	private JPanel pnlResolvedRight;
	private JLabel lblActionTaken;

	private JPanel pnlNorth;
	private JPanel pnlActionPlanDetails;
	private JPanel pnlAPDNorth;
	private JPanel pnlAPDNorthLabel;
	private JLabel lblTargetDate;
	private JLabel lblInCharge;
	private JLabel lblPersonContacted;

	private JPanel pnlAPDNorthComponents;

	private JPanel pnlTargetDate;
	private _JDateChooser dateTarget;
	private JLabel lblDoneDate;
	private _JDateChooser dteDone;

	private JPanel pnlInCharge;
	private _JLookup lookupInChargeNo;
	private JTextField txtInCharge;

	//private JPanel pnlPersonContacted;
	private _JXTextField txtPersonContacted;

	private JPanel pnlAPDSouth;
	private JLabel lblFeedBack;
	private JPanel pnlFeedback;
	private JCheckBox chkEmail;
	private JCheckBox chkPhone;
	private JCheckBox chkLetter;
	private JCheckBox chkSMS;
	private JCheckBox chkFax;
	private JCheckBox chkPersonal;

	private JPanel pnlActionPlan;
	private JScrollPane scrollActionPlan;
	private JTextArea txtAreaActionPlan;

	private JPanel pnlCenter;
	private _JTableMain tblDevelopment;
	private JScrollPane scrollDevelopment;
	private modelActionPlan modelAction;
	private JList rowHeaderDevelopment;

	private ClientFeedback cf;
	private pnlClientComplaint cc;

	private ArrayList<JCheckBox> aList = new ArrayList<JCheckBox>();

	public pnlActionPlan(ClientFeedback cf) {
		this.cf=cf;
		initGUI();
	}

	public pnlActionPlan(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlActionPlan(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlActionPlan(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new SpringLayout());
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(lineBorder);
			//pnlMain.setLayout(new SpringLayout());
			{
				pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				{
					pnlActionPlanDetails = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlActionPlanDetails, BorderLayout.WEST);
					pnlActionPlanDetails.setBorder(JTBorderFactory.createTitleBorder("Action Plan Details"));
					{
						pnlAPDNorth = new JPanel(new BorderLayout(3, 3));
						pnlActionPlanDetails.add(pnlAPDNorth, BorderLayout.NORTH);
						{
							pnlAPDNorthLabel = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlAPDNorth.add(pnlAPDNorthLabel, BorderLayout.WEST);
							{
								lblTargetDate = new JLabel("*Target Date");
								pnlAPDNorthLabel.add(lblTargetDate);
							}
							{
								lblInCharge = new JLabel("*In Charge");
								pnlAPDNorthLabel.add(lblInCharge);
							}
							{
								lblPersonContacted = new JLabel("*Person Contacted");
								pnlAPDNorthLabel.add(lblPersonContacted);
							}
						}
						{
							pnlAPDNorthComponents = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlAPDNorth.add(pnlAPDNorthComponents, BorderLayout.CENTER);
							{
								pnlTargetDate = new JPanel(new BorderLayout(3, 3));
								pnlAPDNorthComponents.add(pnlTargetDate);
								{
									dateTarget = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlTargetDate.add(dateTarget, BorderLayout.WEST);
									dateTarget.setPreferredSize(new Dimension(100, 20));
									dateTarget.setEnabled(false);
								}
								{
									lblDoneDate = new JLabel("*Done Date", JLabel.TRAILING);
									pnlTargetDate.add(lblDoneDate);
								}
								{
									dteDone = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlTargetDate.add(dteDone, BorderLayout.EAST);
									dteDone.setPreferredSize(new Dimension(100, 20));
									dteDone.setEnabled(false);
								}
							}
							{
								pnlInCharge = new JPanel(new BorderLayout(3, 3));
								pnlAPDNorthComponents.add(pnlInCharge);
								{
									lookupInChargeNo = new _JLookup(null, "In Charge", 0);
									pnlInCharge.add(lookupInChargeNo, BorderLayout.WEST);
									lookupInChargeNo.setPreferredSize(new Dimension(80, 0));
									lookupInChargeNo.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();
											FncSystem.out("Display sql for In Charge", lookupInChargeNo.getLookupSQL());
											
											if (data != null){
												String in_charge = (String) data[1];
												txtInCharge.setText(in_charge);
												
											}
										}
									});
								}
								{
									txtInCharge = new JTextField(JTextField.LEFT);
									pnlInCharge.add(txtInCharge);
								}
							}
							{
								txtPersonContacted = new _JXTextField();
								pnlAPDNorthComponents.add(txtPersonContacted);
							}
						}
					}
					{
						pnlAPDSouth = new JPanel(new BorderLayout(3, 3));
						pnlActionPlanDetails.add(pnlAPDSouth, BorderLayout.CENTER);
						{
							lblFeedBack = new JLabel("*Send feedback to complainant thru");
							pnlAPDSouth.add(lblFeedBack, BorderLayout.NORTH);
						}
						{
							pnlFeedback = new JPanel();
							pnlAPDSouth.add(pnlFeedback, BorderLayout.CENTER);
							pnlFeedback.setLayout(new SpringLayout());
							{
								chkEmail = new JCheckBox("Email");
								pnlFeedback.add(chkEmail);
								aList.add(chkEmail);
								chkEmail.setName("checkEmail");
							}
							{
								chkPhone = new JCheckBox("Phone");
								pnlFeedback.add(chkPhone);
								aList.add(chkPhone);
								chkPhone.setName("checkPhonel");
							}
							{
								chkLetter = new JCheckBox("Letter");
								pnlFeedback.add(chkLetter);
								aList.add(chkLetter);
								chkLetter.setName("checkLetter");
							}
							{
								chkSMS = new JCheckBox("SMS");
								pnlFeedback.add(chkSMS);
								aList.add(chkSMS);
								chkSMS.setName("checkSMS");
							}
							{
								chkFax = new JCheckBox("Fax");
								pnlFeedback.add(chkFax);
								aList.add(chkFax);
								chkFax.setName("checkFax");
							}
							{
								chkPersonal = new JCheckBox("Personal Visit");
								pnlFeedback.add(chkPersonal);
								aList.add(chkPersonal);
								chkPersonal.setName("checkPhone");
							}
							SpringUtilities.makeCompactGrid(pnlFeedback, 2, 3, 3, 3, 3, 3, false);
						}
					}
				}
				{
					pnlActionPlan = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlActionPlan, BorderLayout.CENTER);
					pnlActionPlan.setBorder(JTBorderFactory.createTitleBorder("Action Plan"));
					{
						scrollActionPlan = new JScrollPane();
						pnlActionPlan.add(scrollActionPlan);
						{
							txtAreaActionPlan = new JTextArea("");
							txtAreaActionPlan.setBorder(lineBorder);
							scrollActionPlan.setViewportView(txtAreaActionPlan);
							txtAreaActionPlan.setLineWrap(true);
							txtAreaActionPlan.setEditable(false);
						}
					}
				}
			}
			/*{
				pnlTarget = new JPanel(new BorderLayout(5, 5));
				pnlTarget.setLayout(new SpringLayout());
				pnlMain.add(pnlTarget, BorderLayout.NORTH);
				pnlTarget.setBorder(lineBorder);
				pnlTarget.setBorder(components.JTBorderFactory.createTitleBorder("Action Plan Details"));
				{
					pnlTargetLeft = new JPanel(new SpringLayout());
					pnlTarget.add(pnlTargetLeft, BorderLayout.CENTER);
					{
						JPanel pnlTargetLeftUpper = new JPanel();
						pnlTargetLeft.add(pnlTargetLeftUpper);
						pnlTargetLeftUpper.setLayout(new SpringLayout());
						{
							lblTargetDate = new JLabel("*Target Date        ");
							pnlTargetLeftUpper.add(lblTargetDate); 
						}
						{
							JPanel pnlTargetDate = new JPanel(new BorderLayout(5, 5));
							pnlTargetLeftUpper.add(pnlTargetDate, BorderLayout.WEST);
							{
								dateTarget = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlTargetDate.add(dateTarget, BorderLayout.WEST);
								dateTarget.setPreferredSize(new Dimension(100, 20));
								dateTarget.setEnabled(false);
							}
						}
						{
							lblInCharge = new JLabel("*In Charge");
							pnlTargetLeftUpper.add(lblInCharge);
						}
						{
							JPanel pnlInCharge = new JPanel(new BorderLayout(5, 5));
							pnlTargetLeftUpper.add(pnlInCharge, BorderLayout.WEST);
							{
								lookupInChargeNo = new _JLookup(null, "In Charge", 0);
								pnlInCharge.add(lookupInChargeNo, BorderLayout.WEST);
								lookupInChargeNo.setPreferredSize(new Dimension(80, 0));
								//lookupInChargeNo.setLookupSQL(sqlInCharged(""));
								lookupInChargeNo.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										if (data != null){
											String in_charge = (String) data[1];
											txtInCharge.setText(in_charge);
											FncSystem.out("Display sql for In Charge", lookupInChargeNo.getLookupSQL());
										}
									}
								});
							}
							{
								txtInCharge = new JTextField(JTextField.LEFT);
								pnlInCharge.add(txtInCharge);
							}
						}
						SpringUtilities.makeCompactGrid(pnlTargetLeftUpper, 2, 2, 3, 3, 3, 3, false);
					}
					{
						lblFeedBack = new JLabel("*Send feedback to complainant thru");
						pnlTargetLeft.add(lblFeedBack);
					}
					{
						pnlTargetLeftLower = new JPanel();
						pnlTargetLeft.add(pnlTargetLeftLower, BorderLayout.WEST);
						pnlTargetLeftLower.setLayout(new SpringLayout());
						{
							chkEmail = new JCheckBox("Email");
							pnlTargetLeftLower.add(chkEmail);
							aList.add(chkEmail);
							chkEmail.setName("checkEmail");
						}
						{
							chkPhone = new JCheckBox("Phone");
							pnlTargetLeftLower.add(chkPhone);
							aList.add(chkPhone);
							chkPhone.setName("checkPhonel");
						}
						{
							chkLetter = new JCheckBox("Letter");
							pnlTargetLeftLower.add(chkLetter);
							aList.add(chkLetter);
							chkLetter.setName("checkLetter");
						}
						{
							chkSMS = new JCheckBox("SMS");
							pnlTargetLeftLower.add(chkSMS);
							aList.add(chkSMS);
							chkSMS.setName("checkSMS");
						}
						{
							chkFax = new JCheckBox("Fax");
							pnlTargetLeftLower.add(chkFax);
							aList.add(chkFax);
							chkFax.setName("checkFax");
						}
						{
							chkPersonal = new JCheckBox("Personal Visit");
							pnlTargetLeftLower.add(chkPersonal);
							aList.add(chkPersonal);
							chkPersonal.setName("checkPhone");
						}
						SpringUtilities.makeCompactGrid(pnlTargetLeftLower, 2, 3, 3, 3, 3, 3, false);
					}
					SpringUtilities.makeCompactGrid(pnlTargetLeft, 3, 1, 3, 3, 3, 3, false);
				}
				{
					pnlTargetRight = new JPanel(new SpringLayout());
					pnlTarget.add(pnlTargetRight, BorderLayout.EAST);
					pnlTargetRight.setPreferredSize(new Dimension(80, 0));
					{
						lblActionPlan = new JLabel("*Action Plan");
						pnlTargetRight.add(lblActionPlan);
					}
					{
						scrollActionPlan = new JScrollPane();
						pnlTargetRight.add(scrollActionPlan);
						{
							txtAreaActionPlan = new JTextArea("");
							txtAreaActionPlan.setBorder(lineBorder);
							scrollActionPlan.setViewportView(txtAreaActionPlan);
							txtAreaActionPlan.setLineWrap(true);
							txtAreaActionPlan.setEditable(false);
						}
					}
					SpringUtilities.makeCompactGrid(pnlTargetRight, 2, 1, 3, 3, 3, 3, false);
				}
				SpringUtilities.makeCompactGrid(pnlTarget, 1, 2, 3, 3, 3, 3, false);
			}*/
			{
				scrollDevelopment = new JScrollPane();
				pnlMain.add(scrollDevelopment, BorderLayout.CENTER);
				scrollDevelopment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				{
					modelAction = new modelActionPlan();
					tblDevelopment = new _JTableMain(modelAction);
					scrollDevelopment.setViewportView(tblDevelopment);
					tblDevelopment.hideColumns("Rec. ID", "Complaint No", "In Charge Code", "Feedback Thru", "Action Plan", "Done Date", "Person Contacted");
					tblDevelopment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

					modelAction.addTableModelListener(new TableModelListener() {

						public void tableChanged(TableModelEvent arg0) {
							((DefaultListModel)rowHeaderDevelopment.getModel()).addElement(modelAction.getRowCount());

							if(modelAction.getRowCount() == 0){
								rowHeaderDevelopment.setModel(new DefaultListModel());
							}
						}
					});

					tblDevelopment.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent arg0) {
							if(!arg0.getValueIsAdjusting()){
								try {
									int row = tblDevelopment.getSelectedRow();
									
									String comp_no = (String) modelAction.getValueAt(row, 1);
									Date target_date = (Date) modelAction.getValueAt(row, 2);
									Date done_date = (Date) modelAction.getValueAt(row, 3);
									String ic_code = (String) modelAction.getValueAt(row, 4);
									String ic_name = (String) modelAction.getValueAt(row, 5);
									String person_contacted = (String) modelAction.getValueAt(row, 6);
									String action_plan = (String) modelAction.getValueAt(row, 8);

									dateTarget.setDate(target_date);
									dteDone.setDate(done_date);
									lookupInChargeNo.setValue(ic_code);
									txtInCharge.setText(ic_name);
									txtPersonContacted.setText(person_contacted);
									txtAreaActionPlan.setText(action_plan);

									for(int x=0; x < aList.size(); x++){
										aList.get(x).setSelected(false);
									}

									String[] feedback = ((String) modelAction.getValueAt(row, 7)).replace("0", "").replace("'", "").split(",");
									for(int x=0; x < feedback.length; x++){
										try {
											aList.get((Integer.parseInt(feedback[x]) -1 )).setSelected(true);
										} catch (NumberFormatException e) { }
									}
									
									if(cf.isEditable(comp_no)){
										cf.btnState(true, true, false, false);
									}else{
										cf.btnState(true, false, false, false);
									}
									
								} catch (ArrayIndexOutOfBoundsException e) { }
							}
						}
					});
				}
				{
					rowHeaderDevelopment = tblDevelopment.getRowHeader();
					rowHeaderDevelopment.setModel(new DefaultListModel());
					scrollDevelopment.setRowHeaderView(rowHeaderDevelopment);
					scrollDevelopment.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			SpringUtilities.makeCompactGrid(pnlMain, 2, 1, 3, 3, 3, 3, false);
		}
		SpringUtilities.makeCompactGrid(this, 1, 1, 3, 3, 3, 3, false);
		/*cf.setComponentsEnabled(pnlTarget, false);
		cf.setComponentsEnabled(pnlResolved, false);*/
		//cf.setComponentsEditable(this, false);
		//cf.setComponentsEnabled(pnlTargetLeftLower, false);
		cancelActionplan();
	}//XXX END OF INIT GUI

	private String feedBack() {
		String feedback_thru = "";
		for(int x=1 ;x <= aList.size(); x++){
			if(((JCheckBox)aList.get(x - 1)).isSelected()){
				feedback_thru = feedback_thru + "'0"+x + "'" + (x==aList.size() ? "":",");
			}
		}
		return feedback_thru;
	}

	private String sqlInCharged(String compl_no){//

		return "select a.emp_code as \"ID\", get_employee_name(a.emp_code) as \"Employee Name\" \n" + 
		"from em_employee a \n" + 
		"left join rf_client_complaints b on b.comp_addressed_to = a.dept_code\n" + 
		"where b.compl_no = '"+compl_no+"';\n";
	}

	public void clearFeedbackThrough(Boolean sEmail, Boolean sPhone, Boolean sLetter, Boolean sSMS, Boolean sFax, Boolean sPV){
		chkEmail.setSelected(sEmail);
		chkPhone.setSelected(sPhone);
		chkLetter.setSelected(sLetter);
		chkSMS.setSelected(sSMS);
		chkFax.setSelected(sFax);
		chkPersonal.setSelected(sPV);
	}

	public void enabledFeedback(Boolean sEmail, Boolean sPhone, Boolean sLetter, Boolean sSMS, Boolean sFax, Boolean sPV){
		chkEmail.setEnabled(sEmail);
		chkPhone.setEnabled(sPhone);
		chkLetter.setEnabled(sLetter);
		chkSMS.setEnabled(sSMS);
		chkFax.setEnabled(sFax);
		chkPersonal.setEnabled(sPV);
	}

	public void newActionPlan(String compl_no){//OK NA TO

		dateTarget.setDate(null);
		dateTarget.setEnabled(true);
		
		dteDone.setDate(null);
		dteDone.setEnabled(true);
		
		lookupInChargeNo.setValue(null);
		lookupInChargeNo.setEditable(true);
		lookupInChargeNo.setLookupSQL(sqlInCharged(compl_no));
		txtInCharge.setText("");
		
		txtPersonContacted.setText("");

		txtAreaActionPlan.setText("");
		dateTarget.setEnabled(true);
		dteDone.setEnabled(true);
		txtPersonContacted.setEditable(true);
		
		txtAreaActionPlan.setEditable(true);
		clearFeedbackThrough(false, false, false, false, false, false);
		enabledFeedback(true, true, true, true, true, true);

		tblDevelopment.clearSelection();

	}

	public void editActionPlan(){
		
		enabledFeedback(true, true, true, true, true, true);
		dateTarget.setEnabled(true);
		dteDone.setEnabled(true);
		//lookupInChargeNo.setEditable(true);
		txtPersonContacted.setEditable(true);
		txtAreaActionPlan.setEditable(true);
		
		tblDevelopment.setEnabled(false);
		rowHeaderDevelopment.setEnabled(false);
		
	}

	public boolean tosave(){//CHECKING OF REQUIRED VALUES
		if(dateTarget.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Target Date"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(dateTarget.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Done Date", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupInChargeNo.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input who is in charge"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtPersonContacted.getText().isEmpty()){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Person Contacted", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(feedBack().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select on how to send feedback"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtAreaActionPlan.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Action Plan"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean saveActionPlan(String compl_no, String ctgry_code){
		pgUpdate db = new pgUpdate();

		//String ctgry_code = cc.getCtgry_Code();
		Date target_Date = dateTarget.getDate();
		Date done_date = dteDone.getDate();
		String in_charge = lookupInChargeNo.getValue();
		String person_contacted = txtPersonContacted.getText();
		String feedback_thru = feedBack().replace("'", "''");
		String action_plan = txtAreaActionPlan.getText().replace("'", "''");

		if(tblDevelopment.getSelectedRows().length == 1){
			int row = tblDevelopment.getSelectedRow();
			Integer rec_id = (Integer) modelAction.getValueAt(row, 0);

			if(isActionPlanExisting(compl_no, rec_id)){
				String sql = "UPDATE rf_complain_action_plan\n" + 
						"SET ctgry_code= '"+ctgry_code+"', action_plan= '"+action_plan+"', target_date= nullif('"+target_Date+"', 'null')::TIMESTAMP, \n" + 
						"send_feedback_thru= '"+feedback_thru+"', person_in_charged= '"+in_charge+"',  \n" + 
						"edited_by = '"+UserInfo.EmployeeCode+"', edited_dated= now(), done_date = '"+done_date+"', person_contacted = '"+person_contacted+"'\n" + 
						"WHERE compl_no = '"+compl_no+"' AND rec_id = "+rec_id+";\n";
				db.executeUpdate(sql, true);
			}
		}else{

			String sql = "INSERT INTO rf_complain_action_plan(\n" + 
					"compl_no, ctgry_code, action_plan, target_date, send_feedback_thru, \n" + 
					"person_in_charged, date_action_taken, \n" + 
					"created_by, created_date, status_id, done_date, person_contacted)\n" + 
					"VALUES ('"+compl_no+"', '"+ctgry_code+"', '"+action_plan+"' ,'"+target_Date+"', '"+feedback_thru+"', \n" + 
					"'"+in_charge+"', now(), '"+UserInfo.EmployeeCode+"', now() , 'A', '"+done_date+"', '"+person_contacted+"');\n";

			db.executeUpdate(sql, true);
		}

		db.commit();
		return true;
	}

	private boolean isActionPlanExisting(String compl_no, Integer rec_id){//REC_ID
		pgSelect db = new pgSelect();

		String sql = "select * from rf_complain_action_plan where compl_no = '"+compl_no+"' and rec_id = "+rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean isTargetExisting(String compl_no){
		pgSelect db = new pgSelect();
		db.select("select * from rf_client_complaints where compl_no = '"+ compl_no +"';");
		return db.isNotNull();
	}

	private boolean isResolvedExisting(String compl_no){
		pgSelect db = new pgSelect();
		db.select("select * from rf_complain_accomplishment where compl_no = '"+ compl_no +"';");
		return db.isNotNull();
	}

	/*public boolean saveResolvedDate(String entity_id, String proj_code, String pbl_id){
		pgUpdate db = new pgUpdate();
		String action_taken_by = lookupResolvedByNo.getValue();
		String action_taken = txtAreaActionTaken.getText().replace("'", "''");
		Date date_resolved = dateResolved.getDate();
		String compl_no = cf.getCompl_No();
		String ctgry_code = cc.getCtgry_Code();

		if(isResolvedExisting(compl_no)){
				String sql = "UPDATE rf_complain_accomplishment\n" + 
							 "SET action_taken='"+action_taken+"', resolve_date= nullif('"+date_resolved+"', 'null')::TIMESTAMP , \n" + 
							 "action_taken_by=null, edited_by='"+UserInfo.EmployeeCode+"', edited_date= now()\n" + 
							 "WHERE compl_no = '"+compl_no+"'";
				db.executeUpdate(sql, true);
		}else{
			String sql = "INSERT INTO rf_complain_accomplishment(\n" + 
						 "compl_no, ctgry_code, action_taken, resolve_date, \n" + 
						 "action_taken_by, created_by, created_date, status_id)\n" + 
						 "VALUES ('"+compl_no+"', '"+ctgry_code+"', '', null , \n" + 
						 "null, '"+UserInfo.EmployeeCode+"' , now() , 'A');";

			db.executeUpdate(sql, true);
		}
		db.commit();
		return true;
	}*/

	public void cancelActionplan(){//CANCELATION OF THE ACTION PLAN

		//if(tblDevelopment.getRowCount() == 0){
			lookupInChargeNo.setValue(null);
			txtInCharge.setText("");
		//}

		cf.setComponentsEditable(this, false);

		dateTarget.setDate(null);
		dateTarget.setEnabled(false);
		dteDone.setDate(null);
		dteDone.setEnabled(false);
		
		txtPersonContacted.setText("");
		txtAreaActionPlan.setText("");
		clearFeedbackThrough(false, false, false, false, false, false);
		enabledFeedback(false, false, false, false, false, false);
		
		txtAreaActionPlan.setEditable(false);

		tblDevelopment.clearSelection();
		tblDevelopment.setEnabled(true);
		rowHeaderDevelopment.setEnabled(true);
		
	}

	public void displayActionPlan(String compl_no){//Displays the action plan for the particular complaint_no of the client
		modelAction.clear();

		String sql = "select rec_id, trim(compl_no), target_date, done_date, \n" + 
					 "trim(person_in_charged) , trim(get_employee_name(person_in_charged)) , trim(person_contacted) ,\n" + 
					 "trim(send_feedback_thru) , action_plan\n" + 
					 "from rf_complain_action_plan \n" + 
					 "where compl_no = '"+compl_no+"' \n"+
					 "order by created_date DESC";

		FncSystem.out("Display Action Plan", sql);		
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelAction.addRow(db.getResult()[x]);
			}
			tblDevelopment.packAll();
			if(tblDevelopment.getRowCount() != 0){//SETS THE VALUE OF THE PERSON IN CHARGE TO BE THE SAME AS THE FIRST ONE THAT MADE THE REQUEST
				//lookupInChargeNo.setValue((String) db.getResult()[0][3]);
				//lookupInChargeNo.setEditable(false);
				//txtInCharge.setText((String) db.getResult()[0][4]);
				//txtInCharge.setEditable(false);
			}
		}else{
			lookupInChargeNo.setValue(null);
			txtInCharge.setText("");
		}

		scrollDevelopment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDevelopment.getRowCount())));
	}

	public void clearTargetDate(){

		dateTarget.setDate(null);
		lookupInChargeNo.setValue(lookupResolvedByNo.getValue());
		txtInCharge.setText(txtResolvedBy.getText());//XXX FIX ME
		txtAreaActionPlan.setText("");
		clearFeedbackThrough(false, false, false, false, false, false);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
