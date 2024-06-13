package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXPanel;

//import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_sms_batch_sending;

public class sms_tab0 extends JXPanel implements _GUI {
	// hello world

	private static final long serialVersionUID = -3153278366352001143L;
	private jSMS sms_main; 

	private _JLookup txtCoID;
	private _JLookup txtProjID; 
	private _JLookup txtPhaseID;
	private _JLookup txtBatch;

	private JTextField txtCo;
	private JTextField txtProj;
	private JTextField txtPhase;
	private JTextField txtSearch; 

	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;

	private JButton btnGenerate;
	private JButton btnPreview;
	private JButton btnCreateBatch; 

	public static JList rowSMS;
	private _JTableMain tblSMS;
	private JScrollPane scrSMS;
	private model_sms_batch_sending modelSMS;

	private JComboBox cboList;
	private JComboBox cboType; 

	private Font fontPlainSanSer11 = new Font("SansSerif", Font.PLAIN, 11);
	
	pgUpdate dbExec = new pgUpdate(); 
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	/*
	Message Status Legend:
	Q - Queued
	P - Pending
	S - Sent
	*/

	public sms_tab0(jSMS sms) {
		this.sms_main = sms; 
		initGUI();
	}

	public sms_tab0(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public sms_tab0(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public sms_tab0(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(3, 3)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 110));
				{
					JXPanel panDetails = new JXPanel(new GridLayout(3, 1, 3, 3)); 
					panPage.add(panDetails, BorderLayout.CENTER); 
					panDetails.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							JXPanel panCo = new JXPanel(new BorderLayout(3, 3)); 
							panDetails.add(panCo);
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
									panCo.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblCompany = new JLabel("Company"); 
											panLabel.add(lblCompany);
											lblCompany.setHorizontalAlignment(JLabel.LEADING);
											lblCompany.setFont(fontPlainSanSer11);
										}
										{
											txtCoID = new _JLookup(); 
											panLabel.add(txtCoID); 
											txtCoID.setReturnColumn(0);
											txtCoID.setLookupSQL(FncGlobal.getCompany());
											txtCoID.setHorizontalAlignment(JTextField.CENTER);
											txtCoID.setFont(fontPlainSanSer11);
											txtCoID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet();
													
													if (data!=null) {
														txtCo.setText((String) data[1].toString());
														System.out.println("companyID: "+FncGlobal.getProject((String) data[0].toString()));
														txtProjID.setLookupSQL(FncGlobal.getProject((String) data[0].toString()));
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
									txtCo = new JTextField("All Company");
									panCo.add(txtCo, BorderLayout.CENTER);
									txtCo.setHorizontalAlignment(JTextField.CENTER);
									txtCo.setFont(fontPlainSanSer11);
									txtCo.setEditable(false);
								}
							}
						}
						{
							JXPanel panProj = new JXPanel(new BorderLayout(3, 3)); 
							panDetails.add(panProj);
							{
								{
									JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
									panProj.add(panLabel, BorderLayout.LINE_START); 
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel lblProject = new JLabel("Project"); 
											panLabel.add(lblProject);
											lblProject.setHorizontalAlignment(JLabel.LEADING);
											lblProject.setFont(fontPlainSanSer11);
										}
										{
											txtProjID = new _JLookup(); 
											panLabel.add(txtProjID); 
											txtProjID.setReturnColumn(0);
											txtProjID.setLookupSQL(FncGlobal.getProject(""));
											txtProjID.setHorizontalAlignment(JTextField.CENTER);
											txtProjID.setFont(fontPlainSanSer11);
											txtProjID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
													
													if (data!=null) {
														txtProj.setText((String) data[1].toString());
														sqlPhase(txtProjID.getValue().toString()); 
													}
												}
											});
											txtProjID.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
														txtProjID.setValue("");
														txtProj.setText("All Projects");
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
									txtProj = new JTextField("All projects");
									panProj.add(txtProj, BorderLayout.CENTER);
									txtProj.setHorizontalAlignment(JTextField.CENTER);
									txtProj.setFont(fontPlainSanSer11);
									txtProj.setEditable(false);
								}
							}
						}
						{
							JXPanel panPhaseType = new JXPanel(new GridLayout(1, 2, 3, 3)); 
							panDetails.add(panPhaseType); 
							{
								{
									JXPanel panPhase = new JXPanel(new BorderLayout(3, 3)); 
									panPhaseType.add(panPhase);
									{
										{
											JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
											panPhase.add(panLabel, BorderLayout.LINE_START); 
											panLabel.setPreferredSize(new Dimension(150, 0));
											{
												{
													JLabel lblPhase = new JLabel("Phase"); 
													panLabel.add(lblPhase);
													lblPhase.setHorizontalAlignment(JLabel.LEADING);
													lblPhase.setFont(fontPlainSanSer11);
												}
												{
													txtPhaseID = new _JLookup(); 
													panLabel.add(txtPhaseID); 
													txtPhaseID.setReturnColumn(1);
													txtPhaseID.setLookupSQL(sqlPhase(""));
													txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
													txtPhaseID.setFont(fontPlainSanSer11);
													txtPhaseID.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object data[] = ((_JLookup) event.getSource()).getDataSet(); 
															
															if (data!=null) {
																txtPhaseID.setValue((String) data[0].toString());
																txtPhase.setText((String) data[1].toString());
															}
														}
													});
													txtPhaseID.addKeyListener(new KeyListener() {
														public void keyTyped(KeyEvent e) {
															if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
																txtPhaseID.setValue("");
																txtPhase.setText("All Phases");
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
											txtPhase = new JTextField("All Phases");
											panPhase.add(txtPhase, BorderLayout.CENTER);
											txtPhase.setHorizontalAlignment(JTextField.CENTER);
											txtPhase.setFont(fontPlainSanSer11);
											txtPhase.setEditable(false);
										}
									}
								}
								{
									JXPanel panType = new JXPanel(new BorderLayout(3, 3)); 
									panPhaseType.add(panType);
									{
										{
											JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 3, 3)); 
											panType.add(panLabel, BorderLayout.LINE_START); 
											panLabel.setPreferredSize(new Dimension(75, 0));
											{
												{
													JLabel lblType = new JLabel("B.Type"); 
													panLabel.add(lblType);
													lblType.setHorizontalAlignment(JLabel.CENTER);
													lblType.setFont(fontPlainSanSer11);
												}
												/*
												{
													txtTypeID = new _JLookup(); 
													panLabel.add(txtTypeID); 
													txtTypeID.setReturnColumn(0);
													txtTypeID.setLookupSQL("");
													txtTypeID.setHorizontalAlignment(JTextField.CENTER);
													txtTypeID.setFont(fontPlainSanSer11);
													txtTypeID.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															
														}
													});
												}
												*/
											}	
										}
										{
											cboType = new JComboBox(); 
											panType.add(cboType, BorderLayout.CENTER); 
											cboType.setFont(fontPlainSanSer11);
											
											pgSelect dbSel = new pgSelect(); 
											dbSel.select("select distinct type_group_id || ' - ' || \n" + 
													"(case when type_desc ~* 'In-House Financing Account' then 'In-House Financing Account'\n" + 
													"when type_desc ~* 'Cash Buyer' then 'Cash Buyer (Deferred Cash)' \n" + 
													"when type_desc ~* 'PagIbig' then 'PagIbig Account'\n" + 
													"when type_desc ~* 'Bank Financing' then 'Bank Financing' end), type_group_id::int\n" + 
													"from mf_buyer_type\n" + 
													"order by type_group_id::int");
											
											if (dbSel.isNotNull()) {
												for (int x = 0; x < dbSel.getRowCount(); x++) {
													cboType.addItem((String) dbSel.getResult()[x][0]); 
												}
											}
											
											cboType.setSelectedIndex(2);
										}
									}
								}	
							}
						}				
					}
				}	
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					{
						JXPanel panCenterStart = new JXPanel(new BorderLayout(5, 5)); 
						panCenter.add(panCenterStart, BorderLayout.PAGE_START); 
						panCenterStart.setPreferredSize(new Dimension(0, 58));
						{
							{
								JXPanel panListType = new JXPanel(new BorderLayout(5, 5)); 
								panCenterStart.add(panListType, BorderLayout.LINE_START);
								panListType.setPreferredSize(new Dimension(225, 0));
								panListType.setBorder(JTBorderFactory.createTitleBorder("List Type", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									String[] strArr = {
										"1 - LOAN RELEASED", 
										"2 - DOWN PAYMENT", 
										"3 - MBTC DOWN PAYMENT", 
										"4 - QUALIFIED FOR FIRST FILING", 
										"5 - BORROWERS VALIDATION",  	
										"6 - NOA RELEASED", 
										"7 - MBTC ACCOUNTS ACTIVATED", 
										"8 - TURNOVER ORIENTATION", 
										"9 - OFFICIALLY REM", 
										"10 - LOAN FILED",
										"11 - OFFICIALLY REM(W/UNSETTLED DUES)" 
									}; 
									
									cboList = new JComboBox(strArr); 
									panListType.add(cboList, BorderLayout.CENTER);
									cboList.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent e) {
											
										}
									});
									cboList.setFont(fontPlainSanSer11);
								}
							}
							{
								JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panCenterStart.add(panDate, BorderLayout.CENTER); 
								panDate.setBorder(JTBorderFactory.createTitleBorder("Due Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										JXPanel panFrom = new JXPanel(new BorderLayout(3, 3)); 
										panDate.add(panFrom); 
										{
											{
												JLabel lblFrom = new JLabel("From"); 
												panFrom.add(lblFrom, BorderLayout.LINE_START); 
												lblFrom.setHorizontalAlignment(JLabel.LEADING);
												lblFrom.setFont(fontPlainSanSer11);
												lblFrom.setPreferredSize(new Dimension(40, 0));
											}
											{
												dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
												panFrom.add(dteFrom, BorderLayout.CENTER);
												dteFrom.getCalendarButton().setVisible(true);
												dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
											}
										}
									}
									{
										JXPanel panTo = new JXPanel(new BorderLayout(3, 3)); 
										panDate.add(panTo); 
										{
											{
												JLabel lblTo = new JLabel("To"); 
												panTo.add(lblTo, BorderLayout.LINE_START); 
												lblTo.setHorizontalAlignment(JLabel.CENTER);
												lblTo.setFont(fontPlainSanSer11);
												lblTo.setPreferredSize(new Dimension(40, 0));
											}
											{
												dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
												panTo.add(dteTo, BorderLayout.CENTER);
												dteTo.getCalendarButton().setVisible(true);
												dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
											}
										}
									}
								}
							}
						}	
					}
					{
						JXPanel panCenterCenter = new JXPanel(new BorderLayout(5, 5)); 
						panCenter.add(panCenterCenter, BorderLayout.CENTER); 
						{
							scrSMS = new JScrollPane();
							panCenterCenter.add(scrSMS, BorderLayout.CENTER);
							{
								{
									modelSMS = new model_sms_batch_sending();
									tblSMS = new _JTableMain(modelSMS);
									scrSMS.setViewportView(tblSMS);
									scrSMS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									
									tblSMS.getColumnModel().getColumn(1).setPreferredWidth(203);
									tblSMS.getColumnModel().getColumn(2).setPreferredWidth(50);
									tblSMS.getColumnModel().getColumn(3).setPreferredWidth(125);
									tblSMS.getColumnModel().getColumn(4).setPreferredWidth(300);
									tblSMS.getColumnModel().getColumn(5).setPreferredWidth(125);
									tblSMS.getColumnModel().getColumn(6).setPreferredWidth(100);
									tblSMS.getColumnModel().getColumn(7).setPreferredWidth(75);
									
									tblSMS.setSortable(false);
									tblSMS.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no", "Sent", "batch", "msg_id", "due_date", "msg_id");
								}
								{
									rowSMS = tblSMS.getRowHeader();
									rowSMS.setModel(new DefaultListModel());
									scrSMS.setRowHeaderView(rowSMS);
									scrSMS.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}	
							}
						}
					}
					{
						JXPanel panCenterEnd = new JXPanel(new BorderLayout(5, 5)); 
						panCenter.add(panCenterEnd, BorderLayout.PAGE_END); 
						panCenterEnd.setPreferredSize(new Dimension(0, 95));
						{
							{
								JXPanel panEndPage = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panCenterEnd.add(panEndPage, BorderLayout.PAGE_START); 
								panEndPage.setPreferredSize(new Dimension(0, 60));
								{
									{
										JXPanel panBatch = new JXPanel(new BorderLayout(5, 5)); 
										panEndPage.add(panBatch); 
										panBatch.setBorder(JTBorderFactory.createTitleBorder("Batch", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												txtBatch = new _JLookup(); 
												panBatch.add(txtBatch); 
												txtBatch.setEditable(false);
												txtBatch.setReturnColumn(0);
												txtBatch.setLookupSQL("select distinct x.batch, x.date_created::Date, login_id from rf_sms_batch x inner join rf_system_user y on x.created_by = y.emp_code order by x.batch desc");
												txtBatch.setHorizontalAlignment(JTextField.CENTER);
												txtBatch.setFont(fontPlainSanSer11);
												txtBatch.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															dteFrom.setDate(FncGlobal.GetDate("select min(due_date)::date from rf_sms_batch where batch = '"+txtBatch.getValue().toString()+"'"));
															dteTo.setDate(FncGlobal.GetDate("select max(due_date)::date from rf_sms_batch where batch = '"+txtBatch.getValue().toString()+"'"));
															
															GenerateList(); 
															buttons(true, true, false); 
														}
													}
												});
											}
										}
									}
									{
										JXPanel panSearch = new JXPanel(new BorderLayout(5, 5)); 
										panEndPage.add(panSearch); 
										panSearch.setBorder(JTBorderFactory.createTitleBorder("Search", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											txtSearch = new JTextField(""); 
											panSearch.add(txtSearch); 
											txtSearch.setHorizontalAlignment(JTextField.CENTER);
											txtSearch.setEditable(true);
											txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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
													if (txtSearch.getText().equals("")) {
														tblSMS.changeSelection(0, 0, false, false);
													} else {
														for (int x=0; x<tblSMS.getRowCount(); x++) {
															if (tblSMS.getValueAt(x, 1).toString().toLowerCase().contains(txtSearch.getText()) ||
																	tblSMS.getValueAt(x, 1).toString().substring(0, txtSearch.getText().length()).equals(txtSearch.getText())) {
																tblSMS.changeSelection(x, 1, false, false);
																x=tblSMS.getRowCount(); 
															}
														}
													}
												}
											});
										}
									}
								}
							}
							{
								JXPanel panDiv = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panCenterEnd.add(panDiv, BorderLayout.CENTER);
								{
									{
										btnGenerate = new JButton("Generate"); 
										panDiv.add(btnGenerate); 
										btnGenerate.setEnabled(true);
										btnGenerate.setFont(fontPlainSanSer11);
										btnGenerate.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												txtBatch.setValue("");
												
												FncGlobal.startProgress("Generating list...");
												
												if (GenerateList()) {
													buttons(true, false, true); 
												} else {
													JOptionPane.showMessageDialog(null, "No list was generated from the set parameters.");
													buttons(true, false, false); 
												}; 
												FncGlobal.stopProgress();
											}
										});
									}
									{
										btnPreview = new JButton("Preview"); 
										panDiv.add(btnPreview); 
										btnPreview.setEnabled(true);
										btnPreview.setFont(fontPlainSanSer11);
										btnPreview.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();  
												mapParameters.put("co_name", txtCo.getText());
												mapParameters.put("project", txtProj.getText());
												mapParameters.put("batch_no", txtBatch.getValue());
												FncReport.generateReport("/Reports/rpt_sms_list.jasper", "SMS List", "", mapParameters);
											}
										});
									}
									{
										btnCreateBatch = new JButton("Create Batch"); 
										panDiv.add(btnCreateBatch); 
										btnCreateBatch.setEnabled(true);
										btnCreateBatch.setFont(fontPlainSanSer11);
										btnCreateBatch.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												if (JOptionPane.showConfirmDialog(null, "Proceed on creating a batch?", "Proceed", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==
														JOptionPane.YES_OPTION) {
													if (CreateBatch()) {
														buttons(true, true, false);
														JOptionPane.showMessageDialog(null, "Batch created!");
													} else {
														buttons(true, true, true);
														JOptionPane.showMessageDialog(null, "Something went wrong.");
													}; 	
												}
											}
										});
									}
								}
							}
						}
					}
				}
			}
		}
		SetDefaults(); 
		buttons(true, false, false); 
	}

	private void SetDefaults() {
		txtCoID.setValue("02");
		txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");

		txtProjID.setValue("015");
		txtProj.setText("TERRAVERDE RESIDENCES");

		txtPhaseID.setValue("");
		txtPhase.setText("All Phases");
	}

	private void buttons(Boolean blnGen, Boolean blnPreview, Boolean blnCreate) {
		btnGenerate.setEnabled(blnGen);
		btnPreview.setEnabled(blnPreview);
		btnCreateBatch.setEnabled(blnCreate);
	}
	
	private boolean GenerateList() {
		boolean blnSuccess = false; 
		
		FncTables.clearTable(modelSMS);
		DefaultListModel listModel = new DefaultListModel();
		rowSMS.setModel(listModel); 
		
		String strCoID = txtCoID.getValue()==null || txtCoID.getValue().toString()=="null" || txtCoID.getValue().isEmpty() ? "" : txtCoID.getValue().toString();
		String strProID = txtProjID.getValue()==null || txtProjID.getValue().toString()=="null" || txtProjID.getValue().isEmpty() ? "" : txtProjID.getValue().toString();
		String strPhaseID = txtPhaseID.getValue()==null || txtPhaseID.getValue().toString()=="null" || txtPhaseID.getValue().isEmpty() ? "" : txtPhaseID.getValue().toString();
		String strBatch = txtBatch.getValue()==null || txtBatch.getValue().toString()=="null" || txtBatch.getValue().isEmpty() ? "" : txtBatch.getValue().toString();
		
		pgSelect db = new pgSelect();
		db.select("select distinct c_tag, c_name, c_project, c_unit, c_cellphone, c_sent, '"+strBatch+"', c_entity_id, c_proj_id, c_pbl_id, c_seq_no, c_date \n" + 
				"from view_sms_list('"+strCoID+"', '"+strProID+"', " +
				"'"+strPhaseID+"', '"+cboType.getSelectedItem().toString().substring(0, 2)+"', " +
				"'"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"', '"+cboList.getSelectedIndex()+"', " +
				"'"+strBatch+"') \n" +
				"order by c_name");
		
		System.out.println("select distinct c_tag, c_name, c_project, c_unit, c_cellphone, c_sent, '"+strBatch+"', c_entity_id, c_proj_id, c_pbl_id, c_seq_no, c_date \n" + 
				"from view_sms_list('"+strCoID+"', '"+strProID+"', " +
				"'"+strPhaseID+"', '"+cboType.getSelectedItem().toString().substring(0, 2)+"', " +
				"'"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"', '"+cboList.getSelectedIndex()+"', " +
				"'"+strBatch+"')\n " +
				"order by c_name");

		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelSMS.addRow(db.getResult()[x]);
				listModel.addElement(modelSMS.getRowCount());
			}
			blnSuccess = true; 
		}
		
		return blnSuccess; 
	}
	
	private boolean CreateBatch() {
		boolean blnSucess = false; 

		Integer intRowID = 0; 
		Integer intMsgID = 0;
		
		try {
			String strExec = ""; 
			String strBatch = FncGlobal.GetString("select LPAD((coalesce(max(batch::int), 0) + 1)::text, 10, '0') from rf_sms_batch where batch != ''");
			
			for (int x = 0; x < modelSMS.getRowCount(); x++) {
				if ((Boolean) modelSMS.getValueAt(x, 0)) {
					intRowID = FncGlobal.GetInteger("select (coalesce(max(msg_row_id::int), 0) + 1) from rf_sms_batch");
					intMsgID = FncGlobal.GetInteger("select (coalesce(max(msg_id::int), 0) + 1) from rf_sms_batch");
					
					System.out.println("");
					System.out.println("Mark I");
					
					strExec = "insert into rf_sms_batch (msg_row_id, msg_id, batch, entity_id, proj_id, pbl_id, seq_no, \n" + 
							"cellphone, sent, message, created_by, date_created, sent_by, date_sent, \n" +
							"msg_status, due_date, type_id, list_type_index) \n" + 
							"values ("+intRowID+"::bigint, "+intMsgID+"::bigint, '"+strBatch+"', \n" +
							"'"+modelSMS.getValueAt(x, 7).toString()+"', '"+modelSMS.getValueAt(x, 8).toString()+"', \n" +
							"'"+modelSMS.getValueAt(x, 9).toString()+"', "+modelSMS.getValueAt(x, 10).toString()+", \n" +
							"'"+modelSMS.getValueAt(x, 4).toString()+"', false, '', '"+UserInfo.EmployeeCode+"', now(), \n" +
							"'', null, 'O', '"+modelSMS.getValueAt(x, 11).toString()+"', \n" +
							"'"+cboType.getSelectedItem().toString().substring(0, 2)+"', '"+cboList.getSelectedIndex()+"')";
					
					System.out.println("");
					System.out.println(strExec);
					
					dbExec = new pgUpdate(); 
					dbExec.executeUpdate(strExec, false);
					dbExec.commit();
				}
			}
			
			txtBatch.setValue(strBatch);
			GenerateList(); 
			blnSucess = true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Something went wrong. Please check the individual rows for inconsistent values. \n" + ex.getMessage());
			blnSucess = false; 
		}
		
		return blnSucess; 
	}
	
	private String sqlPhase(String strProject) {
		return "select x.phase, x.sub_proj_name \n" +
			"from mf_sub_project x \n" +
			"where x.phase in (select distinct a.phase from mf_unit_info a) \n" +
			"and (proj_id = '"+strProject+"' or '"+strProject+"' = '') \n" +
			"and status_id = 'A' \n" +
			"order by x.phase";
	}
}
