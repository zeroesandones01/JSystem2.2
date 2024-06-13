package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_agentbroker_branch;
import tablemodel.model_sms_pool;

public class salesAgentBranch extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = 8248173361256488165L;
	static final String title = "Sales Agent/Broker Branch";
	static final Dimension frameSize = new Dimension(500, 500);
	
	private static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	
	private static JList rowAgent;
	private static _JTableMain tblAgent;
	private static JScrollPane scrAgent;
	private static model_agentbroker_branch modelAgent;
	
	private static _JLookup txtTeamID;
	private static _JLookup txtGroupID;
	private static _JLookup txtSalesTypeID;
	private static _JLookup txtPositionID;
	private static _JLookup txtStatusID;
	
	private static JTextField txtAgent;
	private static JTextField txtTeam;
	private static JTextField txtGroup;
	private static JTextField txtSalesType;
	private static JTextField txtPosition;
	private static JTextField txtStatus;
	
	public salesAgentBranch() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public salesAgentBranch(String title) {
		super(title);
		initGUI(); 
	}

	public salesAgentBranch(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(3, 3)); 
		getContentPane().add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(3, 3)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 180));
				panPage.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel panDiv = new JXPanel(new GridLayout(6, 1, 3, 3));
					panPage.add(panDiv, BorderLayout.CENTER);
					{
						/**		Agent Team		**/
						{
							JXPanel panDiv2 = new JXPanel(new BorderLayout(3, 3)); 
							panDiv.add(panDiv2); 
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panDiv2.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblLabel = new JLabel("Division"); 
											panLabel.add(lblLabel, BorderLayout.LINE_START);
											lblLabel.setHorizontalAlignment(JLabel.LEADING);
											lblLabel.setPreferredSize(new Dimension(100, 0));
											lblLabel.setFont(font11);
										}
										{
											txtTeamID = new _JLookup(""); 
											panLabel.add(txtTeamID); 
											txtTeamID.setReturnColumn(0);
											txtTeamID.setLookupSQL(lookupMethods.getDivision());
											txtTeamID.setFont(font11);
											txtTeamID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
													
													if (data!=null) {
														txtGroupID.setLookupSQL(lookupMethods.getSalesGroup(txtTeamID.getValue()));
														txtTeam.setText((String) data[1]);
														
														smithReload(); 
													}
												}
											});
											txtTeamID.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyCode()==0) {
														txtTeamID.setValue("");
														txtTeam.setText("All Division");
													}
												}
												
												public void keyReleased(KeyEvent e) {

												}
												
												public void keyPressed(KeyEvent e) {

												}
											});
											txtTeamID.setToolTipText("Press backspace to reset.");
										}
									}
								}
								{
									txtTeam = new JTextField(""); 
									panDiv2.add(txtTeam, BorderLayout.CENTER); 
									txtTeam.setHorizontalAlignment(JTextField.CENTER);
									txtTeam.setFont(font11);
									txtTeam.setFocusable(false);
								}
							}
						}
						/**		Agent Group		**/
						{
							JXPanel panDiv3 = new JXPanel(new BorderLayout(3, 3)); 
							panDiv.add(panDiv3); 
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panDiv3.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblLabel = new JLabel("Sales Group"); 
											panLabel.add(lblLabel, BorderLayout.LINE_START);
											lblLabel.setHorizontalAlignment(JLabel.LEADING);
											lblLabel.setPreferredSize(new Dimension(100, 0));
											lblLabel.setFont(font11);
										}
										{
											txtGroupID = new _JLookup(""); 
											panLabel.add(txtGroupID); 
											txtGroupID.setReturnColumn(0);
											txtGroupID.setLookupSQL(lookupMethods.getSalesGroup(""));
											txtGroupID.setFont(font11);
											txtGroupID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
													
													if (data!=null) {
														txtGroup.setText((String) data[1]);
														smithReload(); 
													}
												}
											});
											txtGroupID.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyCode()==0) {
														txtGroupID.setValue("");
														txtGroup.setText("All Sales Group");
													}
												}
												
												public void keyReleased(KeyEvent e) {

												}
												
												public void keyPressed(KeyEvent e) {

												}
											});
											txtGroupID.setToolTipText("Press backspace to reset.");
										}
									}
								}
								{
									txtGroup = new JTextField(""); 
									panDiv3.add(txtGroup, BorderLayout.CENTER); 
									txtGroup.setHorizontalAlignment(JTextField.CENTER);
									txtGroup.setFont(font11);
									txtGroup.setFocusable(false);
								}
							}
						}
						/**		Sales Type		**/
						{
							JXPanel panDiv4 = new JXPanel(new BorderLayout(3, 3)); 
							panDiv.add(panDiv4); 
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panDiv4.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblLabel = new JLabel("Sales Type"); 
											panLabel.add(lblLabel, BorderLayout.LINE_START);
											lblLabel.setHorizontalAlignment(JLabel.LEADING);
											lblLabel.setPreferredSize(new Dimension(100, 0));
											lblLabel.setFont(font11);
										}
										{
											txtSalesTypeID = new _JLookup(""); 
											panLabel.add(txtSalesTypeID); 
											txtSalesTypeID.setReturnColumn(0);
											txtSalesTypeID.setLookupSQL(lookupMethods.getSalesType());
											txtSalesTypeID.setFont(font11);
											txtSalesTypeID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
													
													if (data!=null) {
														txtPositionID.setLookupSQL(lookupMethods.getSalesPosition(txtSalesTypeID.getValue()));
														txtSalesType.setText((String) data[1]);
														smithReload(); 
													}
												}
											});
											txtSalesTypeID.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyCode()==0) {
														txtSalesTypeID.setValue("");
														txtSalesType.setText("All Sales Type");
													}
												}
												
												public void keyReleased(KeyEvent e) {

												}
												
												public void keyPressed(KeyEvent e) {

												}
											});
											txtSalesTypeID.setToolTipText("Press backspace to reset.");
										}
									}
								}
								{
									txtSalesType = new JTextField(""); 
									panDiv4.add(txtSalesType, BorderLayout.CENTER); 
									txtSalesType.setHorizontalAlignment(JTextField.CENTER);
									txtSalesType.setFont(font11);
									txtSalesType.setFocusable(false);
								}
							}
						}
						/**		Postion			**/
						{
							JXPanel panDiv5 = new JXPanel(new BorderLayout(3, 3)); 
							panDiv.add(panDiv5); 
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panDiv5.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblLabel = new JLabel("Position"); 
											panLabel.add(lblLabel, BorderLayout.LINE_START);
											lblLabel.setHorizontalAlignment(JLabel.LEADING);
											lblLabel.setPreferredSize(new Dimension(100, 0));
											lblLabel.setFont(font11);
										}
										{
											txtPositionID = new _JLookup(""); 
											panLabel.add(txtPositionID); 
											txtPositionID.setReturnColumn(0);
											txtPositionID.setLookupSQL(lookupMethods.getSalesPosition(""));
											txtPositionID.setFont(font11);
											txtPositionID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
													
													if (data!=null) {
														txtPosition.setText((String) data[1]);
														smithReload(); 
													}
												}
											});
											txtPositionID.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyCode()==0) {
														txtPositionID.setValue("");
														txtPosition.setText("All Sales Position");
													}
												}
												
												public void keyReleased(KeyEvent e) {

												}
												
												public void keyPressed(KeyEvent e) {

												}
											});
											txtPositionID.setToolTipText("Press backspace to reset.");
										}
									}
								}
								{
									txtPosition = new JTextField(""); 
									panDiv5.add(txtPosition, BorderLayout.CENTER); 
									txtPosition.setHorizontalAlignment(JTextField.CENTER);
									txtPosition.setFont(font11);
									txtPosition.setFocusable(false);
								}
							}
						}
						/**		Status			**/
						{
							JXPanel panDiv6 = new JXPanel(new BorderLayout(3, 3)); 
							panDiv.add(panDiv6); 
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panDiv6.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblLabel = new JLabel("Status"); 
											panLabel.add(lblLabel, BorderLayout.LINE_START);
											lblLabel.setHorizontalAlignment(JLabel.LEADING);
											lblLabel.setPreferredSize(new Dimension(100, 0));
											lblLabel.setFont(font11);
										}
										{
											txtStatusID = new _JLookup(""); 
											panLabel.add(txtStatusID); 
											txtStatusID.setReturnColumn(0);
											txtStatusID.setLookupSQL(lookupMethods.getStatus());
											txtStatusID.setFont(font11);
											txtStatusID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
													
													if (data!=null) {
														txtStatus.setText((String) data[1]);
														smithReload(); 
													}
												}
											});
											txtStatusID.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyCode()==0) {
														txtStatusID.setValue("");
														txtStatus.setText("All Status");
													}
												}
												
												public void keyReleased(KeyEvent e) {

												}
												
												public void keyPressed(KeyEvent e) {

												}
											});
											txtStatusID.setToolTipText("Press backspace to reset.");
										}
									}
								}
								{
									txtStatus = new JTextField(""); 
									panDiv6.add(txtStatus, BorderLayout.CENTER); 
									txtStatus.setHorizontalAlignment(JTextField.CENTER);
									txtStatus.setFont(font11);
									txtStatus.setFocusable(false);
								}
							}
						}
						/**		Agent Name		**/
						{
							JXPanel panDiv1 = new JXPanel(new BorderLayout(3, 3)); 
							panDiv.add(panDiv1); 
							{
								{
									JLabel lblLabel = new JLabel("Agent"); 
									panDiv1.add(lblLabel, BorderLayout.LINE_START);
									lblLabel.setHorizontalAlignment(JLabel.LEADING);
									lblLabel.setPreferredSize(new Dimension(150, 0));
									lblLabel.setFont(font11);
								}
								{
									txtAgent = new JTextField(""); 
									panDiv1.add(txtAgent, BorderLayout.CENTER); 
									txtAgent.setHorizontalAlignment(JTextField.CENTER);
									txtAgent.setFont(font11);
									txtAgent.getDocument().addDocumentListener(new DocumentListener() {
										public void removeUpdate(DocumentEvent e) {
											focusonthis(); 
										}
										
										public void insertUpdate(DocumentEvent e) {
											focusonthis();  
										}
										
										public void changedUpdate(DocumentEvent e) {
											focusonthis(); 
										}
										
										void focusonthis() {
											if (txtAgent.getText().equals("")) {
												tblAgent.changeSelection(0, 0, false, false);
											} else {
												for (int x=0; x<tblAgent.getRowCount(); x++) {
													if (tblAgent.getValueAt(x, 1).toString().toLowerCase().contains(txtAgent.getText()) ||
															tblAgent.getValueAt(x, 1).toString().substring(1, txtAgent.getText().length()).equals(txtAgent.getText())) {
														tblAgent.changeSelection(x, 1, false, false);
														x=tblAgent.getRowCount(); 
													}
												}
											}
										}
									});
								}
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(3, 3)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				panCenter.setBorder(lineRed);
				{
					scrAgent = new JScrollPane();
					panMain.add(scrAgent, BorderLayout.CENTER);
					{
						{
							modelAgent = new model_agentbroker_branch();
							tblAgent = new _JTableMain(modelAgent);
							scrAgent.setViewportView(tblAgent);
							scrAgent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							
							tblAgent.getColumnModel().getColumn(0).setPreferredWidth(100);
							tblAgent.getColumnModel().getColumn(1).setPreferredWidth(225);
							tblAgent.getColumnModel().getColumn(2).setPreferredWidth(100);
							
							tblAgent.setToolTipText("Click on the branch to change.");
							
							tblAgent.setSortable(false);
							tblAgent.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if (!e.getValueIsAdjusting()) {
										//if (tblAgent.getSelectedColumn()==2) {
										if (tblAgent.getSelectedRows().length>0){
											Integer row = 0;
											String current_branch = ""; 
											String new_branch = ""; 
											
											row = tblAgent.convertRowIndexToModel(tblAgent.getSelectedRow());
											current_branch = (String) modelAgent.getValueAt(row, 2); 
											
											if (!current_branch.equals("")) {
												new_branch = (current_branch.equals("SUMMIT")?"CARMONA":"SUMMIT");
												
												if (JOptionPane.showConfirmDialog(getContentPane(), 
														"Change "+((String) modelAgent.getValueAt(row, 1))+"'s branch to "+new_branch+"?", 
														"Change branch", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
													modelAgent.setValueAt(new_branch, row, 2);
													saveDetails(current_branch, new_branch, (String) modelAgent.getValueAt(row, 0)); 
													JOptionPane.showMessageDialog(getContentPane(), "Saved successfully!", "Save", JOptionPane.INFORMATION_MESSAGE);
													smithReload();
												}	
											} else {
												current_branch = "";
												new_branch = "SUMMIT";
												
												modelAgent.setValueAt("SUMMIT", row, 2);
												saveDetails(current_branch, new_branch, (String) modelAgent.getValueAt(row, 0));
												JOptionPane.showMessageDialog(getContentPane(), "Saved successfully!", "Save", JOptionPane.INFORMATION_MESSAGE);
												smithReload();
											}

											//saveDetails(current_branch, new_branch, (String) modelAgent.getValueAt(row, 0)); 
											//smithReload(); 
										}	
									}
								}
							});
						}
						{
							rowAgent = tblAgent.getRowHeader();
							rowAgent.setModel(new DefaultListModel());
							scrAgent.setRowHeaderView(rowAgent);
							scrAgent.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}	
					}
				}
			}
		}
		
		txtTeam.setText("All Division");
		txtGroup.setText("All Sales Group");
		txtSalesType.setText("All Sales Type");
		txtPosition.setText("All Sales Position");
		txtStatus.setText("All Status");
		
		smithReload(); 
	}
	
	private void smithReload() {
		pgSelect db = new pgSelect();

		FncTables.clearTable(modelAgent);
		DefaultListModel listModel = new DefaultListModel();
		rowAgent.setModel(listModel); 
		
		String strSQL = "select a.agent_id, b.entity_name, coalesce(c.branch_alias, '') as branch_alias \n" + 
				"from mf_sellingagent_info a \n" + 
				"inner join rf_entity b on a.agent_id = b.entity_id \n" + 
				"left join mf_office_branch c on a.branch_id = c.branch_id \n" + 
				"where (a.sales_div_id = '"+((txtTeamID.getValue()==null)?"":txtTeamID.getValue())+"' or '"+((txtTeamID.getValue()==null)?"":txtTeamID.getValue())+"' = '') \n" +
				"and (a.dept_id = '"+((txtGroupID.getValue()==null)?"":txtGroupID.getValue())+"' or '"+((txtGroupID.getValue()==null)?"":txtGroupID.getValue())+"' = '') \n" +
				"and (a.salespos_id = '"+((txtPositionID.getValue()==null)?"":txtPositionID.getValue())+"' or '"+((txtPositionID.getValue()==null)?"":txtPositionID.getValue())+"' = '') \n" +
				"and (a.status_id = '"+((txtStatusID.getValue()==null)?"":txtStatusID.getValue())+"' or '"+((txtStatusID.getValue()==null)?"":txtStatusID.getValue())+"' = '') \n" +
				"and (a.salestype_id = '"+((txtSalesTypeID.getValue()==null)?"":txtSalesTypeID.getValue())+"' or '"+((txtSalesTypeID.getValue()==null)?"":txtSalesTypeID.getValue())+"' = '') \n" +
				"order by b.entity_name"; 
		
		System.out.println("");
		System.out.println("strSQL: "+strSQL);
		
		db.select(strSQL);
		
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelAgent.addRow(db.getResult()[x]);
				listModel.addElement(modelAgent.getRowCount());
			}
		}
	}
	
	private void saveDetails(String strPrevBranch, String strCurrBranch, String strAgent) {
		pgUpdate dbExec = new pgUpdate(); 

		String strCurID = FncGlobal.GetString("select branch_id from mf_office_branch where branch_alias = '"+strCurrBranch+"'"); 
		
		dbExec.executeUpdate("update mf_sellingagent_info set branch_id = '"+strCurID+"' where agent_id = '"+strAgent+"'", false);		
		dbExec.executeUpdate("insert into mf_audit_trail (system_id, activity, user_code, date_upd, remarks)\n" + 
				"values ('SAB', 'BRANCH CHANGE', '"+UserInfo.EmployeeCode+"', now(), 'Previous branch: "+strPrevBranch+"; New Branch: "+strCurrBranch+"; ')", false);
		dbExec.commit();
	}
}
