package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;

import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;

public class DOA_Monitoring extends _JInternalFrame implements _GUI, ActionListener {
	
	private static final long serialVersionUID = -393468008940008144L;
	static String title = "DOA-Monitoring";
	Dimension frameSize = new Dimension(550, 200);
	
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlCenterLabel;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	
	private JPanel pnlCenterComponents;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnPreviewAll;
	
	public DOA_Monitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public DOA_Monitoring(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public DOA_Monitoring(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, true, true, true, true);
		initGUI();
	}
	
	@Override
//	public void initGUI() {//XXX START OF INIT GUI
//		this.setSize(frameSize);
//		this.setPreferredSize(frameSize);
//		{
//			pnlMain = new JPanel(new BorderLayout(3, 3));
//			this.add(pnlMain, BorderLayout.CENTER);
//			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			{
//				pnlCenter = new JPanel(new BorderLayout(5, 5));
//				pnlMain.add(pnlCenter, BorderLayout.CENTER);
//				pnlCenter.setPreferredSize(new Dimension(0, 20));
//				{
//					pnlCenterLabel = new JPanel(new GridLayout(3, 1, 3, 3));
//					pnlCenter.add(pnlCenterLabel, BorderLayout.WEST);
//					{
//						lblCompany = new JLabel("Company", JLabel.TRAILING);
//						pnlCenterLabel.add(lblCompany);
//					}
//					{
//						lblProject = new JLabel("Project", JLabel.TRAILING);
//						pnlCenterLabel.add(lblProject);
//					}
//					{
//						lblPhase = new JLabel("Phase", JLabel.TRAILING);
//						pnlCenterLabel.add(lblPhase);
//					}
//				}
//				{
//					pnlCenterComponents = new JPanel(new GridLayout(3, 1, 3, 3));
//					pnlCenter.add(pnlCenterComponents, BorderLayout.CENTER);
//					{
//						JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
//						pnlCenterComponents.add(pnlCompany);
//						{
//							lookupCompany = new _JLookup(null, "Select Company", 0);
//							pnlCompany.add(lookupCompany, BorderLayout.WEST);
//							lookupCompany.setLookupSQL(sqlCompany());
//							lookupCompany.setPreferredSize(new Dimension(50, 0));
//							lookupCompany.addLookupListener(new LookupListener() {
//								
//								public void lookupPerformed(LookupEvent event) {
//									Object [] data = ((_JLookup) event.getSource()).getDataSet();
//									
//									FncSystem.out("Display SQL for lookup of Company", lookupCompany.getValue());
//									if(data != null){
//										String co_id = (String) data[0];
//										String company_name = (String) data[1];
//										
//										txtCompany.setText(company_name);
//										lookupProject.setLookupSQL(sqlProject(co_id));
//									}
//								}
//							});
//						}
//						{
//							txtCompany = new _JXTextField();
//							pnlCompany.add(txtCompany, BorderLayout.CENTER);
//						}
//					}
//					{
//						JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
//						pnlCenterComponents.add(pnlProject);
//						{
//							lookupProject = new _JLookup(null, "Select Project", 0, "Please select company first");
//							pnlProject.add(lookupProject, BorderLayout.WEST);
//							lookupProject.setPreferredSize(new Dimension(50, 0));
//							lookupProject.setValue("015");
//							lookupProject.addLookupListener(new LookupListener() {
//								
//								public void lookupPerformed(LookupEvent event) {
//									Object [] data = ((_JLookup) event.getSource()).getDataSet();
//									
//									FncSystem.out("Display SQL for Project", lookupProject.getValue());
//									if(data != null){
//										String proj_id = (String) data[0];
//										String proj_name = (String) data[1];
//									
//										txtProject.setText(proj_name);
//										lookupPhase.setLookupSQL(SQL_PHASE(proj_id));
//									}
//								}
//							});
//						}
//						{
//							txtProject = new _JXTextField();
//							pnlProject.add(txtProject, BorderLayout.CENTER);
//						}
//					}
//					{
//						JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
//						pnlCenterComponents.add(pnlPhase);
//						{
//							lookupPhase = new _JLookup(null, "Select Phase", 0);
//							pnlPhase.add(lookupPhase, BorderLayout.WEST);
//							lookupPhase.setPreferredSize(new Dimension(50, 0));
//							lookupPhase.setLookupSQL(SQL_PHASE(lookupProject.getValue()));
//							lookupPhase.addLookupListener(new LookupListener() {
//								
//								public void lookupPerformed(LookupEvent event) {
//									Object [] data = ((_JLookup) event.getSource()).getDataSet();
//									
//									FncSystem.out("Display SQL for Project", lookupPhase.getValue());
//									if(data != null){
//										String sub_proj_id = (String) data[0];
//										String phase = (String) data[1];
//										
//										txtPhase.setText(phase);
//									}
//								}
//							});
//						}
//						{
//							txtPhase = new _JXTextField();
//							pnlPhase.add(txtPhase);
//						}
//					}
//				}
//			}
////			{
////				pnlSouth = new JPanel(new GridLayout(1,2,3,3));
////				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
////				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
////				pnlSouth.setPreferredSize(new Dimension(388, 50));
////				pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
////				{
////					btnPreview = new JButton("Preview");
////					pnlSouth.add(btnPreview);
////					btnPreview.setAlignmentX(0.5f);
////					btnPreview.setAlignmentY(0.5f);
////					btnPreview.setMaximumSize(new Dimension(100, 50));
////					btnPreview.setMnemonic(KeyEvent.VK_P);
////					btnPreview.addActionListener(this);
////					btnPreview.setActionCommand(btnPreview.getText());
////				}
////			}
//			{//**ADDED BY JED 2019-03-01 : ADDITIONAL BUTTON FOR PREVIEW ALL C/O MISS RHEA**//
//				pnlSouth = new JPanel(new GridLayout (1,2,3,3));
//				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
//				pnlSouth.setPreferredSize(new Dimension(0,30));
//				{
//					btnPreview = new JButton("Preview");
//					pnlSouth.add(btnPreview);
//					btnPreview.addActionListener(this);
//					btnPreview.setActionCommand("Preview");
//				}
//				{
//					btnPreviewAll = new JButton("Preview All");
//					pnlSouth.add(btnPreviewAll);
//					btnPreviewAll.addActionListener(this);
//					btnPreviewAll.setActionCommand("Preview All");
//				}
//			}
//		}
//		setDefaults();
//	}//XXX END OF INIT GUI
	
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					{
						c.weightx = 1;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 0; 
						
						JPanel pnlCompany = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCompany, c);				
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.BOTH;
							cons_com.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1 company*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 0; 
								
								lblCompany = new JLabel("Company", JLabel.CENTER);
								pnlCompany.add(lblCompany, cons_com);
								lblCompany.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 0; 
								
								lookupCompany = new _JLookup(null, "Select Company", 0);
								pnlCompany.add(lookupCompany, cons_com);
								lookupCompany.setLookupSQL(sqlCompany());
								lookupCompany.setPreferredSize(new Dimension(30, 0));
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								lookupCompany.addLookupListener(new LookupListener() {
									
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										
										FncSystem.out("Display SQL for lookup of Company", lookupCompany.getValue());
										if(data != null){
											String co_id = (String) data[0];
											String company_name = (String) data[1];
											
											txtCompany.setText(company_name);
											lookupProject.setLookupSQL(sqlProject(co_id));
										}
									}
								});																
							}
							{
								cons_com.weightx = 1.5;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 0; 
														
								txtCompany = new _JXTextField();
								pnlCompany.add(txtCompany, cons_com);
								txtCompany.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 2 project*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 1; 
								
								lblProject = new JLabel("Project", JLabel.CENTER);
								pnlCompany.add(lblProject, cons_com);
								lblProject.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;
	
								cons_com.gridx = 1; 
								cons_com.gridy = 1; 
								
								lookupProject = new _JLookup(null, "Select Project", 0, "Please select company first");
								pnlCompany.add(lookupProject, cons_com);
								lookupProject.setPreferredSize(new Dimension(30, 0));
								lookupProject.setValue("015");
								lookupProject.setFont(FncGlobal.sizeTextValue);
								lookupProject.addLookupListener(new LookupListener() {
									
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										
										FncSystem.out("Display SQL for Project", lookupProject.getValue());
										if(data != null){
											String proj_id = (String) data[0];
											String proj_name = (String) data[1];
										
											txtProject.setText(proj_name);
											lookupPhase.setLookupSQL(SQL_PHASE(proj_id));
										}
									}
								});															
							}
							{
								cons_com.weightx = 1.5;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 1; 
								
								txtProject = new _JXTextField();
								pnlCompany.add(txtProject, cons_com);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 3 payee*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 2;
								
								lblPhase = new JLabel("Phase", JLabel.CENTER);
								pnlCompany.add(lblPhase, cons_com);
								lblPhase.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 2; 
									
								lookupPhase = new _JLookup(null, "Select Phase", 0);
								pnlCompany.add(lookupPhase, cons_com);
								lookupPhase.setPreferredSize(new Dimension(30, 0));
								lookupPhase.setLookupSQL(SQL_PHASE(lookupProject.getValue()));
								lookupPhase.addLookupListener(new LookupListener() {
									
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										
										FncSystem.out("Display SQL for Project", lookupPhase.getValue());
										if(data != null){
											String sub_proj_id = (String) data[0];
											String phase = (String) data[1];
											
											txtPhase.setText(phase);
										}
									}
								});							
							}
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 2;
								
								txtPhase = new _JXTextField();
								pnlCompany.add(txtPhase, cons_com);
								txtPhase.setFont(FncGlobal.sizeTextValue);
							}
						}
					}		
				}
			}
			{//**ADDED BY JED 2019-03-01 : ADDITIONAL BUTTON FOR PREVIEW ALL C/O MISS RHEA**//
				pnlSouth = new JPanel(new GridLayout (1,2,3,3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.addActionListener(this);
					btnPreview.setActionCommand("Preview");
					btnPreview.setFont(FncGlobal.sizeControls);
				}
				{
					btnPreviewAll = new JButton("Preview All");
					pnlSouth.add(btnPreviewAll);
					btnPreviewAll.addActionListener(this);
					btnPreviewAll.setActionCommand("Preview All");
					btnPreviewAll.setFont(FncGlobal.sizeControls);
				}
			}
		}
		setDefaults();
	}
	
	private void setDefaults(){
		lookupCompany.setValue("");
		txtCompany.setText("");
		lookupProject.setValue("");
		txtProject.setText("");
	}
	
	//SQL FOR COMPANY
	private String sqlCompany(){
		return "SELECT TRIM(co_id) as \"ID\", \n" + 
			   "TRIM(company_name) as \"Company\", \n" + 
			   "TRIM(company_alias) as \"Alias\"\n" + 
			   "FROM mf_company;";
	}

		//SQL FOR PROJECT
	private String sqlProject(String co_id){
		return "SELECT TRIM(proj_id) as \"ID\",\n" + 
			   "TRIM(proj_name) as \"Project\", \n" + 
			   "TRIM(proj_alias) as \"Alias\"\n" + 
			   "FROM mf_project \n" + 
			   "WHERE co_id = '"+co_id+"' and status_id ='A';";
	}
	
	public void actionPerformed(ActionEvent e) { 
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Preview")){
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("phase", lookupPhase.getValue());
			mapParameters.put("prepared_by", UserInfo.FullName2);
			
			FncReport.generateReport("/Reports/rptDOA_Monitoring.jasper","DOA-Monitoring" , mapParameters);
		}
		if(actionCommand.equals("Preview All")){//**ADDED BY JED 2019-03-01 : ADDITIONAL BUTTON FOR PREVIEW ALL C/O MISS RHEA**//
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("phase", null);
			mapParameters.put("prepared_by", UserInfo.FullName2);
			
			FncReport.generateReport("/Reports/rptDOA_Monitoring.jasper","DOA-Monitoring" , mapParameters);
		}
	}
}
