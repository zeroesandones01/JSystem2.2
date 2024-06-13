package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class cancelledTCTAnnotation extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -6823045432063995510L;
	private static String title = "Cancelled TCT Annotation"; 
	Border line = BorderFactory.createLineBorder(Color.GRAY);
	Dimension frameSize = new Dimension(500, 355);
	
	private _JLookup txtClientID;
	private JTextField txtClient;
	private JTextField txtUnitID;
	private JTextField txtUnit;  
	private JTextField txtProjectID;
	private JTextField txtProject;
	private JTextField txtSeqNo;

	private _JDateChooser dteForwarded;
	private _JDateChooser dteAnnotated;
	private _JDateChooser dteCancelled;

	private JComboBox cboItems;
	private _JDateChooser dteItems;
	
	private JButton btnModify; 
	private JButton btnSave; 
	private JButton btnCancel;
	private JButton btnPreview;
	
	private JXPanel panPrev; 
	private _JLookup txtCoID;
	private _JLookup txtProjID;
	private _JLookup txtPhaseID;
	
	private JTextField txtCo;
	private JTextField txtProj;
	private JTextField txtPhase;
	
	public cancelledTCTAnnotation() {
		super(title, true, true, false, true);
		initGUI();
	}

	public cancelledTCTAnnotation(String title) {
		super(title);
		initGUI();
	}

	public cancelledTCTAnnotation(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(3, 3));
		getContentPane().add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(3, 3, 3, 3));
		{
			{
				JXPanel panPage = new JXPanel(new GridLayout(2, 1, 3, 3)); 
				panMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 220));
				{
					{
						JXPanel panClientDetails = new JXPanel(new GridLayout(3, 1, 3, 3)); 
						panPage.add(panClientDetails); 
						panClientDetails.setBorder(JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JXPanel panName = new JXPanel(new BorderLayout(3, 3)); 
								panClientDetails.add(panName); 
								{
									{
										JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
										panName.add(panLabel, BorderLayout.LINE_START);
										panLabel.setPreferredSize(new Dimension(200, 0));
										{
											{
												JLabel lblName = new JLabel("Name");
												panLabel.add(lblName); 
												lblName.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												String strSQL = "select a.entity_id, b.entity_name as name, a.proj_id, c.proj_name, a.pbl_id, d.description, a.seq_no \n" +
														"from rf_buyer_status a \n" +
														"inner join rf_entity b on a.entity_id = b.entity_id \n" +
														"inner join mf_project c on a.proj_id = c.proj_id \n" + 
														"inner join mf_unit_info d on a.proj_id = d.proj_id and a.pbl_id = d.pbl_id \n" +
														"where a.byrstatus_id = '02' and a.status_id = 'A' \n" + 
														"and exists(select * from rf_buyer_status x where x.entity_id = a.entity_id and x.proj_id = a.proj_id \n" + 
														"and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int and x.byrstatus_id = '46' and x.status_id = 'A') \n" + 
														"order by b.entity_name"; 
												
												txtClientID = new _JLookup("");
												panLabel.add(txtClientID); 
												txtClientID.setReturnColumn(0);
												txtClientID.setHorizontalAlignment(JTextField.CENTER);
												txtClientID.setLookupSQL(strSQL);
												txtClientID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup)event.getSource()).getDataSet();
														
														if (data!=null) {
															txtClient.setText((String) data[1].toString());
															txtProjectID.setText((String) data[2].toString());
															txtProject.setText((String) data[3].toString());
															txtUnitID.setText((String) data[4].toString());
															txtUnit.setText((String) data[5].toString());
															txtSeqNo.setText((String) data[6].toString());
															
															dteForwarded.setDate(FncGlobal.GetDate("select x.tran_date from rf_buyer_status x where x.entity_id = '"+txtClientID.getText()+"' and x.proj_id = '"+txtProjectID.getText()+"' and x.pbl_id = '"+txtUnitID.getText()+"' and x.seq_no::int = '"+txtSeqNo.getText()+"'::int and x.byrstatus_id = '56' and x.status_id = 'A'"));
															dteAnnotated.setDate(FncGlobal.GetDate("select x.tran_date from rf_buyer_status x where x.entity_id = '"+txtClientID.getText()+"' and x.proj_id = '"+txtProjectID.getText()+"' and x.pbl_id = '"+txtUnitID.getText()+"' and x.seq_no::int = '"+txtSeqNo.getText()+"'::int and x.byrstatus_id = '46' and x.status_id = 'A'"));
															dteCancelled.setDate(FncGlobal.GetDate("select x.tran_date from rf_buyer_status x where x.entity_id = '"+txtClientID.getText()+"' and x.proj_id = '"+txtProjectID.getText()+"' and x.pbl_id = '"+txtUnitID.getText()+"' and x.seq_no::int = '"+txtSeqNo.getText()+"'::int and x.byrstatus_id = '02' and x.status_id = 'A'"));
															
															ActivateControls(true); 
															ActivateButtons(true); 
															CheckStatus(); 
														}
													}
												});
											}
										}
									}
									{
										JXPanel panText = new JXPanel(new BorderLayout(3, 3)); 
										panName.add(panText, BorderLayout.CENTER);
										{
											txtClient = new JTextField(); 
											panText.add(txtClient, BorderLayout.CENTER); 
											txtClient.setHorizontalAlignment(JTextField.CENTER);
											txtClient.setEditable(false);
										}
									}
								}
							}
							{

								JXPanel panProject = new JXPanel(new BorderLayout(3, 3)); 
								panClientDetails.add(panProject); 
								{
									{
										JXPanel panIDLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
										panProject.add(panIDLabel, BorderLayout.LINE_START); 
										panIDLabel.setPreferredSize(new Dimension(200, 0));
										{
											{
												JLabel lblProject = new JLabel("Project"); 
												panIDLabel.add(lblProject); 
												lblProject.setHorizontalAlignment(JTextField.LEADING);
											}
											{
												txtProjectID = new JTextField(""); 
												panIDLabel.add(txtProjectID); 
												txtProjectID.setHorizontalAlignment(JTextField.CENTER);
												txtProjectID.setEditable(false); 
											}
										}
										
									}
									{
										txtProject = new JTextField(""); 
										panProject.add(txtProject, BorderLayout.CENTER); 
										txtProject.setHorizontalAlignment(JTextField.CENTER);
										txtProject.setEditable(false);
									}
								}
							}
							{
								JXPanel panUnit = new JXPanel(new BorderLayout(3, 3)); 
								panClientDetails.add(panUnit); 
								{
									{
										JXPanel panIDLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
										panUnit.add(panIDLabel, BorderLayout.LINE_START); 
										panIDLabel.setPreferredSize(new Dimension(200, 0));
										{
											{
												JLabel lblUnit = new JLabel("Unit/Seq"); 
												panIDLabel.add(lblUnit); 
												lblUnit.setHorizontalAlignment(JTextField.LEADING);
											}
											{
												txtUnitID = new JTextField(""); 
												panIDLabel.add(txtUnitID); 
												txtUnitID.setHorizontalAlignment(JTextField.CENTER);
												txtUnitID.setEditable(false);
											}
										}
										
									}
									{
										JXPanel panUnitSeq = new JXPanel(new BorderLayout(3, 3)); 
										panUnit.add(panUnitSeq, BorderLayout.CENTER); 
										{
											{
												txtUnit = new JTextField(""); 
												panUnit.add(txtUnit, BorderLayout.CENTER); 
												txtUnit.setHorizontalAlignment(JTextField.CENTER);
												txtUnit.setEditable(false);
											}
											{
												txtSeqNo = new JTextField(""); 
												panUnit.add(txtSeqNo, BorderLayout.LINE_END); 
												txtSeqNo.setHorizontalAlignment(JTextField.CENTER);
												txtSeqNo.setPreferredSize(new Dimension(75, 0));
												txtSeqNo.setEditable(false);
											}
										}
									}
								}
							}
						}
					}
					{
						JXPanel panStatus = new JXPanel(new GridLayout(3, 2, 3, 3)); 
						panPage.add(panStatus); 
						panStatus.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JLabel lblDiv1 = new JLabel("Forwarded for Annotation"); 
								panStatus.add(lblDiv1); 
								lblDiv1.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								dteForwarded = new _JDateChooser("MM/dd/yyyy", "##/##/####", '-');
								panStatus.add(dteForwarded);
								dteForwarded.getCalendarButton().setVisible(false);
								dteForwarded.setEditable(false);
							}
							{
								JLabel lblDiv2 = new JLabel("TCT Annotated"); 
								panStatus.add(lblDiv2); 
								lblDiv2.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								dteAnnotated = new _JDateChooser("MM/dd/yyyy", "##/##/####", '-');
								panStatus.add(dteAnnotated);
								dteAnnotated.getCalendarButton().setVisible(false);
								dteAnnotated.setEditable(false);
							}
							{
								JLabel lblDiv3 = new JLabel("Cancelled Date"); 
								panStatus.add(lblDiv3); 
								lblDiv3.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								dteCancelled = new _JDateChooser("MM/dd/yyyy", "##/##/####", '-');
								panStatus.add(dteCancelled);
								dteCancelled.getCalendarButton().setVisible(false);
								dteCancelled.setEditable(false);
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(3, 3)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					{
						JXPanel panCombo = new  JXPanel(new BorderLayout(3, 3)); 
						panCenter.add(panCombo, BorderLayout.PAGE_START);
						panCombo.setPreferredSize(new Dimension(0, 60));
						panCombo.setBorder(JTBorderFactory.createTitleBorder("Status", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								String[] items = {
										"1 - Requested cancellation of assignment to HDMF",
										"2 - Cancellation of Assignment Released from HDMF",
										"3 - Forwarded to LLD",
										"4 - TCT Annotation Cancelled"
								}; 
								
								cboItems = new JComboBox<>(items);
								panCombo.add(cboItems, BorderLayout.CENTER); 
								cboItems.setEnabled(false);
								cboItems.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										CheckStatus(); 
									}
								});
							}
							{
								dteItems = new _JDateChooser("MM/dd/yyyy", "##/##/####", '-');
								panCombo.add(dteItems, BorderLayout.LINE_END);
								dteItems.getCalendarButton().setVisible(true);
								dteItems.setPreferredSize(new Dimension(150, 0));
								dteItems.setEnabled(false);
								dteItems.getCalendarButton().setVisible(false);
								dteItems.setEditable(false);
							}
						}
					}
					{
						JXPanel panButton = new JXPanel(new GridLayout(1, 5, 3, 3)); 
						panCenter.add(panButton, BorderLayout.CENTER); 
						{
							{
								btnModify = new JButton("Modify"); 
								panButton.add(btnModify); 
								btnModify.setEnabled(false);
								btnModify.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										ActivateButtons(false);
									}
								});
							}
							{
								btnSave = new JButton("Save"); 
								panButton.add(btnSave); 
								btnSave.setEnabled(false);
								btnSave.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if (JOptionPane.showConfirmDialog(getContentPane(), "Proceed?", "Save", 
												JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
											if (dteItems.getDate()!=null) {
												Save();
												ActivateButtons(true);
												dteItems.setDate(null);
												CheckStatus();	
											}  else {
												JOptionPane.showMessageDialog(getContentPane(), "Please set the date.");
											}
										}
									}
								});
							}
							{
								btnCancel = new JButton("Cancel"); 
								panButton.add(btnCancel); 
								btnCancel.setEnabled(false);
								btnCancel.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										ActivateButtons(true);
										dteItems.setDate(null);
										CheckStatus(); 
									}
								});
							}
							{
								panButton.add(new JXPanel(new BorderLayout(3, 3))); 
							}
							{
								btnPreview = new JButton("Preview"); 
								panButton.add(btnPreview); 
								btnPreview.setEnabled(true);
								btnPreview.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										Object[] options1 = {
												"Preview", 
												"Cancel"
											};
										int scanOption = JOptionPane.showOptionDialog(getContentPane(), panPrev, "Accounts with Cancelled TCT Annotation",
												JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, null);		

										if (scanOption==JOptionPane.CLOSED_OPTION) {
											try {

											} catch (ArrayIndexOutOfBoundsException arrEx) {
												
											}
										} else {
											DateFormat df = new SimpleDateFormat("MM-dd-yyyy"); 
											String strDate = df.format(FncGlobal.getDateToday()).toString();  
											
											strDate = "As Of: " + strDate;
											
											String strProject = "";
											String strPhase = "";
											
											
											if (txtProject.getText() == "All Projects") {
												strProject = "";
											} else {
												strProject = txtProjID.getValue();
											}
											
											if (strProject == null) {
												strProject = "";
											}
											
											if (txtPhase.getText() == "All Phase") {
												strPhase = "";
											} else {
												strPhase = txtPhaseID.getValue();
											}
											
											Map<String, Object> mapParameters = new HashMap<String, Object>();
											mapParameters.put("co_id", txtCoID.getText());
											mapParameters.put("co_name", txtCo.getText());
											mapParameters.put("dateParam", strDate);
											mapParameters.put("project_id", strProject);
											mapParameters.put("user_name", UserInfo.FullName2);
											mapParameters.put("project", txtProj.getText());
											mapParameters.put("phase", strPhase);
											FncReport.generateReport("/Reports/rpt_hdmf_cancelled_tct_annotation.jasper", "Accounts with Cancelled TCT Annotation", "", mapParameters);
										}
										
										System.out.println("");
										System.out.println("scanOption: " + scanOption);
										System.out.println("YES_OPTION: " + JOptionPane.YES_OPTION);
										System.out.println("NO_OPTION: " + JOptionPane.NO_OPTION);
									}
								});
							}
						}
					}
				}
			}
		}
		CreatePreview(); 
	}
	
	private void ActivateControls(Boolean blnDo) {
		cboItems.setEnabled(blnDo);
		dteItems.setEnabled(blnDo);
	}
	
	private void ActivateButtons(Boolean blnDo) {
		btnModify.setEnabled(blnDo);
		btnSave.setEnabled(!blnDo);
		btnCancel.setEnabled(!blnDo);
		btnPreview.setEnabled(true);
		
		cboItems.setEnabled(blnDo);
		dteItems.getCalendarButton().setVisible(!blnDo);
	}
	
	private void CheckStatus() {
		try {
			if (cboItems.getSelectedIndex()==0) {
				dteItems.setDate(FncGlobal.GetDate("select assigment_cancellation_requested from rf_tct_annotation_status where entity_id = '"+txtClientID.getValue()+"' and proj_id = '"+txtProjectID.getText()+"' and pbl_id = '"+txtUnitID.getText()+"' and seq_no::int = '"+txtSeqNo.getText()+"'::int and status_id = 'A'")); 
			} else if (cboItems.getSelectedIndex()==1) {
				dteItems.setDate(FncGlobal.GetDate("select assigment_cancellation from rf_tct_annotation_status where entity_id = '"+txtClientID.getValue()+"' and proj_id = '"+txtProjectID.getText()+"' and pbl_id = '"+txtUnitID.getText()+"' and seq_no::int = '"+txtSeqNo.getText()+"'::int and status_id = 'A'"));
			} else if (cboItems.getSelectedIndex()==2) {
				dteItems.setDate(FncGlobal.GetDate("select forwarded_lld from rf_tct_annotation_status where entity_id = '"+txtClientID.getValue()+"' and proj_id = '"+txtProjectID.getText()+"' and pbl_id = '"+txtUnitID.getText()+"' and seq_no::int = '"+txtSeqNo.getText()+"'::int and status_id = 'A'"));
			} else if (cboItems.getSelectedIndex()==3) {
				try {
					dteItems.setDate(FncGlobal.GetDate("select x.tran_date from rf_buyer_status x where x.entity_id = '"+txtClientID.getText()+"' and x.proj_id = '"+txtProjectID.getText()+"' and x.pbl_id = '"+txtUnitID.getText()+"' and x.seq_no::int = '"+txtSeqNo.getText()+"'::int and x.byrstatus_id = '108' and x.status_id = 'A'"));
				} catch (NullPointerException ex) {
					dteItems.setDate(null); 
				}		
			}
		} catch (NullPointerException ex) {
			dteItems.setDate(null);
		}
	}
	
	private void Save() {
		String SQL = "";
		pgUpdate dbExec = new pgUpdate(); 
		
		if (FncGlobal.GetInteger("select count(*)::int \n" +
				"from rf_tct_annotation_status \n" +
				"where entity_id = '"+txtClientID.getValue().toString()+"' and proj_id = '"+txtProjectID.getText()+"' \n" +
				"and pbl_id = '"+txtUnitID.getText()+"' and seq_no::int = '"+txtSeqNo.getText()+"'::int and status_id = 'A'")==0) {
			SQL = "insert into rf_tct_annotation_status (entity_id, proj_id, pbl_id, seq_no, status_id, date_created, created_by)\n" + 
				"values ('"+txtClientID.getValue().toString()+"', '"+txtProjectID.getText()+"', '"+txtUnitID.getText()+"', '"+txtSeqNo.getText()+"', 'A', now(), '"+UserInfo.EmployeeCode+"')";
			
			System.out.println("");
			System.out.println(SQL);
			
			dbExec = new pgUpdate();
			dbExec.executeUpdate(SQL, false);
			dbExec.commit();
		};  
		
		if (cboItems.getSelectedIndex()==3) {
			if (FncGlobal.GetInteger("select count(*)::int from rf_buyer_status x where x.entity_id = '"+txtClientID.getText()+"' \n" +
					"and x.proj_id = '"+txtProjectID.getText()+"' and x.pbl_id = '"+txtUnitID.getText()+"' \n" +
					"and x.seq_no::int = '"+txtSeqNo.getText()+"'::int and x.byrstatus_id = '108' and x.status_id = 'A'") > 0) {
				SQL = "update rf_buyer_status \n" +
					"set actual_date = '"+dteItems.getDate().toString()+"' \n" +
					"where x.entity_id = '"+txtClientID.getText()+"' \n" + 
					"and x.proj_id = '"+txtProjectID.getText()+"' and x.pbl_id = '"+txtUnitID.getText()+"'  +\n" + 
					"and x.seq_no::int = '"+txtSeqNo.getText()+"'::int and x.byrstatus_id = '108' and x.status_id = 'A'"; 
			} else {
				SQL = "insert into rf_buyer_status( \n" + 
					"entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date,\n" + 
					"actual_date, status_id, branch_id, created_by)\n" + 
					"values ('"+txtClientID.getValue().toString()+"', '"+txtProjectID.getText()+"', '"+txtUnitID.getText()+"', '"+txtSeqNo.getText()+"', '108', now(),\n" + 
					"'"+dteItems.getDate().toString()+"', 'A', '01', '"+UserInfo.EmployeeCode+"') ";	
			}
			
			dbExec = new pgUpdate(); 
			dbExec.executeUpdate(SQL, true);
			dbExec.commit();
			
			dbExec = new pgUpdate(); 
			dbExec.executeUpdate("update rf_tct_annotation_status \n" +
					"set tct_annotation_cancellation = '"+dteItems.getDate().toString()+"'::date, date_edited = now(), edited_by = '"+UserInfo.EmployeeCode+"' \n" +
					"where entity_id = '"+txtClientID.getValue().toString()+"' and proj_id = '"+txtProjectID.getText()+"' and pbl_id = '"+txtUnitID.getText()+"' \n" +
					"and seq_no::int = '"+txtSeqNo.getText()+"'::int and status_id = 'A'", true);
			dbExec.commit();
		} else {
			SQL = "update rf_tct_annotation_status \n"; 
			if (cboItems.getSelectedIndex()==0) {
				SQL = SQL + "set assigment_cancellation_requested = '"+dteItems.getDate().toString()+"'::date, date_edited = now(), edited_by = '"+UserInfo.EmployeeCode+"' \n"; 
			} else if (cboItems.getSelectedIndex()==1) {
				SQL = SQL + "set assigment_cancellation = '"+dteItems.getDate().toString()+"'::date, date_edited = now(), edited_by = '"+UserInfo.EmployeeCode+"' \n";
			} else if (cboItems.getSelectedIndex()==2) {
				SQL = SQL + "set forwarded_lld = '"+dteItems.getDate().toString()+"'::date, date_edited = now(), edited_by = '"+UserInfo.EmployeeCode+"' \n";
			}
			SQL = SQL + "where entity_id = '"+txtClientID.getValue().toString()+"' and proj_id = '"+txtProjectID.getText()+"' and pbl_id = '"+txtUnitID.getText()+"' and seq_no::int = '"+txtSeqNo.getText()+"'::int and status_id = 'A'";
			
			dbExec = new pgUpdate(); 
			dbExec.executeUpdate(SQL, true);
			dbExec.commit();
		}
		
		JOptionPane.showMessageDialog(getContentPane(), "Saved!");
	}
	
	private void CreatePreview() {
		panPrev = new JXPanel(new GridLayout(3, 1, 3, 3)); 
		panPrev.setPreferredSize(new Dimension(450, 110));
		panPrev.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
		{
			{
				JXPanel panCo = new JXPanel(new BorderLayout(3, 3)); 
				panPrev.add(panCo);
				{
					{
						JXPanel panCoLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
						panCo.add(panCoLabel, BorderLayout.LINE_START); 
						panCoLabel.setPreferredSize(new Dimension(150, 0));
						{
							{
								JLabel lblCo = new JLabel("Company"); 
								panCoLabel.add(lblCo);
								lblCo.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								txtCoID = new _JLookup(); 
								panCoLabel.add(txtCoID); 
								txtCoID.setReturnColumn(0);
								txtCoID.setHorizontalAlignment(JTextField.CENTER);
								txtCoID.setLookupSQL(CompanySQL());
								txtCoID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
										
										if (data!=null) {
											txtCo.setText((String) data[1].toString()); 
											txtProjID.setLookupSQL(ProjectSQL(txtCoID.getValue()));
										}
									}
								});
								txtCoID.addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											txtCoID.setValue("");
											txtCo.setText("All Companies");
										}
									}
									
									public void keyReleased(KeyEvent e) {
										
									}
									
									public void keyPressed(KeyEvent e) {
										
									}
								});
							}
						}
					}
					{
						txtCo = new JTextField(""); 
						panCo.add(txtCo, BorderLayout.CENTER); 
						txtCo.setHorizontalAlignment(JTextField.CENTER);
						txtCo.setEditable(false);
					}
				}
			}
			{
				JXPanel panPro = new JXPanel(new BorderLayout(3, 3)); 
				panPrev.add(panPro);
				{
					{
						JXPanel panProLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
						panPro.add(panProLabel, BorderLayout.LINE_START); 
						panProLabel.setPreferredSize(new Dimension(150, 0));
						{
							{
								JLabel lblPro = new JLabel("Project"); 
								panProLabel.add(lblPro);
								lblPro.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								txtProjID = new _JLookup(); 
								panProLabel.add(txtProjID); 
								txtProjID.setReturnColumn(0);
								txtProjID.setHorizontalAlignment(JTextField.CENTER);
								txtProjID.setLookupSQL(ProjectSQL(""));
								txtProjID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
										
										if (data!=null) {
											txtProj.setText((String) data[2].toString()); 
											
											System.out.print("");
											System.out.print("txtProjID.getValue(): " + txtProjID.getValue());
											
											txtPhaseID.setLookupSQL(sqlPhase(txtProjID.getValue()));
										}
									}
								});
								txtProjectID.addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											txtProjID.setValue("");
											txtProj.setText("All Project");
										}										
									}
									
									public void keyReleased(KeyEvent e) {
										
									}
									
									public void keyPressed(KeyEvent e) {
										
									}
								});
							}	
						}
					}
					{
						txtProj = new JTextField(""); 
						panPro.add(txtProj, BorderLayout.CENTER); 
						txtProj.setHorizontalAlignment(JTextField.CENTER);
						txtProj.setEditable(false);
					}
				}
			}
			{
				JXPanel panPhase = new JXPanel(new BorderLayout(3, 3)); 
				panPrev.add(panPhase);
				{
					{
						JXPanel panPhaseLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
						panPhase.add(panPhaseLabel, BorderLayout.LINE_START); 
						panPhaseLabel.setPreferredSize(new Dimension(150, 0));
						{
							{
								JLabel lblPhase = new JLabel("Phase"); 
								panPhaseLabel.add(lblPhase);
								lblPhase.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								txtPhaseID = new _JLookup(); 
								panPhaseLabel.add(txtPhaseID); 
								txtPhaseID.setReturnColumn(0);
								txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
								txtPhaseID.setLookupSQL(sqlPhase(""));
								txtPhaseID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
										
										if (data!=null) {
											txtPhase.setText((String) data[1].toString()); 
										}
									}
								});
							}
							{
								txtPhaseID.addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											txtPhaseID.setValue("");
											txtPhase.setText("All Phase");
										}	
									}
									
									public void keyReleased(KeyEvent e) {
										
									}
									
									public void keyPressed(KeyEvent e) {
										
									}
								});
							}
						}
					}
					{
						txtPhase = new JTextField(""); 
						panPhase.add(txtPhase, BorderLayout.CENTER); 
						txtPhase.setHorizontalAlignment(JTextField.CENTER);
						txtPhase.setEditable(false);
					}
				}
			}
		}
		{
			txtCoID.setValue("02");
			txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");
			
			txtProjID.setValue("");
			txtProj.setText("All Projects");
			
			txtPhaseID.setValue("");
			txtPhase.setText("All Phase");
		}
	}
	
	private static String CompanySQL() {
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			   "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}
	
	public static String ProjectSQL(String strCo){
		return "SELECT proj_id, proj_alias, proj_name\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
			   "ORDER BY proj_name";
	}
	
	private String sqlPhase(String strProject) {
		return "select x.phase, x.sub_proj_name \n" +
			"from mf_sub_project x \n" +
			"where x.phase in (select distinct a.phase from mf_unit_info a) \n" +
			"and (proj_id = '"+strProject+"' or '"+strProject+"' = 'null') \n" +
			"and status_id = 'A' \n" +
			"order by x.phase::int";
	}
}
