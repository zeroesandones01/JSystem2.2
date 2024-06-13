package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;

public class Form_1904 extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 5805381924922506483L;
	
	/*
	 * CHANGES AS OF 2021-09-27
	 * 
	 * 1. CHANGE THE TITLE OF MODULE DCRF NO. 1726 - ADDITIONAL BIR DOCS FROM LLD 2021-09-27
	 * 
	 * */

	//static String title = "Form 1904";
	/*CHANGED BY JED 2021-09-27 DCRF NO. 1726 : ADDITIONAL BIR DOCUMENTS*/
	static String title = "Form 1904/2000OT/2000/RT Slip";

	Dimension frameSize = new Dimension(650, 290);
	private JPanel pnlCenter;
	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JPanel pnlNWLabel;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnitDesc;
	private JLabel lblSeqNo;
	private JLabel lblTinNo;

	private JPanel pnlNWComponent;

	private JPanel pnlPresenter;
	private _JLookup lookupPresenter;
	private _JXTextField txtPresenter;

	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;

	private JPanel pnlProject;
	private _JXTextField txtProjID;
	private _JXTextField txtProject;

	private JPanel pnlUnit;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;
	private _JXTextField txtSeqNo;
	
	private _JXTextField txtTIN_No;
	private JComboBox cmbReportName;

	private JPanel pnlSouth;
	private JButton btnPreview;

	private JPanel pnlMain;

	private JPanel pnlPreview;
	private JPanel pnlForm2000OTAmt;
	private _JXFormattedTextField txtTaxDueAmt;

	public Form_1904() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Form_1904(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public Form_1904(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
//	public void initGUI() {
//		this.setTitle(title);
//		this.setSize(frameSize);
//		{
//			JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
//			getContentPane().add(pnlMain, BorderLayout.CENTER);
//			pnlMain.setPreferredSize(frameSize);
//			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
//			{
//				pnlCenter = new JPanel(new BorderLayout(3, 3));
//				pnlMain.add(pnlCenter, BorderLayout.CENTER);
//				pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Client Details"));
//				{
//					pnlNWLabel = new JPanel(new GridLayout(5, 1));
//					pnlCenter.add(pnlNWLabel, BorderLayout.WEST);
//					
//					{
//						lblClient = new JLabel("Client's Name");
//						pnlNWLabel.add(lblClient);
//					}
//					{
//						lblProject = new JLabel("Project");
//						pnlNWLabel.add(lblProject);
//					}
//					{
//						lblUnitDesc = new JLabel("Phase/Block/Lot");
//						pnlNWLabel.add(lblUnitDesc);
//					}
//					{
//						lblSeqNo = new JLabel("Seq.");
//						pnlNWLabel.add(lblSeqNo);
//					}
//					{
//						lblTinNo = new JLabel("TIN No.");
//						pnlNWLabel.add(lblTinNo);
//					}
//				}
//				{
//					pnlNWComponent = new JPanel(new GridLayout(5, 1));
//					pnlCenter.add(pnlNWComponent, BorderLayout.CENTER);
//					/*{
//						pnlPresenter = new JPanel(new BorderLayout(5, 5));
//						pnlNWComponent.add(pnlPresenter);
//						{
//							lookupPresenter = new _JLookup(null, "Presenter", 0);
//							pnlPresenter.add(lookupPresenter, BorderLayout.WEST);
//							lookupPresenter.setLookupSQL(sqlEmployee());
//							lookupPresenter.setPreferredSize(new Dimension(100, 0));
//							lookupPresenter.setFilterName(true);
//							lookupPresenter.addLookupListener(new LookupListener() {
//
//								public void lookupPerformed(LookupEvent event) {
//									Object [] data = ((_JLookup) event.getSource()).getDataSet();
//									FncSystem.out("Display SQL for presenter", sqlEmployee());
//									
//									if(data != null){
//										String presenter_name = (String) data[1];
//										txtPresenter.setText(presenter_name);
//									}
//								}
//							});
//						}
//						{
//							txtPresenter = new _JXTextField();
//							pnlPresenter.add(txtPresenter, BorderLayout.CENTER);
//						}
//					}*/
//					{
//						pnlClient = new JPanel(new BorderLayout(5, 5));
//						pnlNWComponent.add(pnlClient);
//						{
//							lookupClient = new _JLookup(null, "Client", 0);
//							pnlClient.add(lookupClient, BorderLayout.WEST);
//							lookupClient.setLookupSQL(sqlClients());
//							lookupClient.setPreferredSize(new Dimension(100, 0));
//							lookupClient.setFilterName(true);
//							lookupClient.addLookupListener(new LookupListener() {
//
//								@Override
//								public void lookupPerformed(LookupEvent event) {
//									Object [] data = ((_JLookup) event.getSource()).getDataSet();
//
//									if(data != null){
//										String entity_id = (String) data[0];
//										String entity_name = (String) data[1];
//										String unit_desc = (String) data[2];
//										String unit_id = (String) data[3];
//										String proj_id = (String) data[4];
//										String proj_name = (String) data[5];
//										Integer seq_no = (Integer) data[6];
//										String tin_no = (String) data[8];
//
//										txtClient.setText(entity_name);
//										txtUnitID.setText(unit_id);
//										txtUnitDesc.setText(unit_desc);
//										txtProjID.setText(proj_id);
//										txtProject.setText(proj_name);
//										txtSeqNo.setText(seq_no.toString());
//										txtTIN_No.setText(tin_no);
//
//									}
//								}
//							});
//						}
//						{
//							txtClient = new _JXTextField();
//							pnlClient.add(txtClient, BorderLayout.CENTER);
//						}
//					}
//					{
//						pnlProject = new JPanel(new BorderLayout(5, 5));
//						pnlNWComponent.add(pnlProject);
//						{
//							txtProjID = new _JXTextField();
//							pnlProject.add(txtProjID, BorderLayout.WEST);
//							txtProjID.setPreferredSize(new Dimension(50, 0));
//						}
//						{
//							txtProject = new _JXTextField();
//							pnlProject.add(txtProject, BorderLayout.CENTER);
//						}
//					}
//					{
//						pnlUnit = new JPanel(new BorderLayout(5, 5));
//						pnlNWComponent.add(pnlUnit);
//						{
//							txtUnitID = new _JXTextField();
//							pnlUnit.add(txtUnitID, BorderLayout.WEST);
//							txtUnitID.setPreferredSize(new Dimension(100, 0));
//						}
//						{
//							txtUnitDesc = new _JXTextField();
//							pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
//						}
//					}
//					{
//						txtSeqNo = new _JXTextField();
//						pnlNWComponent.add(txtSeqNo, BorderLayout.WEST);
//						//txtSeqNo.setPreferredSize(new Dimension(50, 0));
//					}
//					{
//						txtTIN_No = new _JXTextField();
//						pnlNWComponent.add(txtTIN_No);
//					}
//				}
//			}
//			{
//				pnlSouth = new JPanel(new GridLayout(1, 3));
//				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
//				pnlSouth.setPreferredSize(new Dimension(0, 30));
//				{
//					pnlSouth.add(Box.createHorizontalBox());
//					pnlSouth.add(Box.createHorizontalBox());
//				}
//				{
//					btnPreview = new JButton("Preview");
//					pnlSouth.add(btnPreview);
//					btnPreview.setActionCommand("Preview");
//					btnPreview.addActionListener(this);
//				}
//			}
//		}
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
				pnlPreview = new JPanel(new BorderLayout(5,5));
				pnlPreview.setPreferredSize(new Dimension(450,150));
				{
					JPanel pnlPreviewCenter = new JPanel(new BorderLayout(5,5));
					pnlPreview.add(pnlPreviewCenter, BorderLayout.CENTER);
					{
						JLabel lblPreview = new JLabel("Please select one.");
						pnlPreviewCenter.add(lblPreview);
						lblPreview.setHorizontalAlignment(JLabel.CENTER);				
					}
				}
				{
					JPanel pnlPreviewSouth = new JPanel(new GridLayout(2,3,5,5));
					pnlPreview.add(pnlPreviewSouth, BorderLayout.SOUTH);
					pnlPreviewSouth.setPreferredSize(new Dimension(0,100));
					{
						JButton btnForm1904 = new JButton("Form 1904");
						pnlPreviewSouth.add(btnForm1904);
						btnForm1904.setActionCommand("1904");
						btnForm1904.addActionListener(this);
					}
					{
						JButton btn2000ot = new JButton("2000-OT");
						pnlPreviewSouth.add(btn2000ot);
						btn2000ot.setActionCommand("2000ot");
						btn2000ot.addActionListener(this);
						if(UserInfo.EmployeeCode.equals("900027")) {
							btn2000ot.setEnabled(false);
						}
					}
					{
						JButton btn2000 = new JButton("2000");
						pnlPreviewSouth.add(btn2000);
						btn2000.setActionCommand("2000");
						btn2000.addActionListener(this);
						if(UserInfo.EmployeeCode.equals("900027")) {
							btn2000.setEnabled(false);
						}
					}
					{
						JButton btnRTSlip = new JButton("RT Slip");
						pnlPreviewSouth.add(btnRTSlip);
						btnRTSlip.setActionCommand("RT SLIP");
						btnRTSlip.addActionListener(this);
						if(UserInfo.EmployeeCode.equals("900027")) {
							btnRTSlip.setEnabled(false);
						}
					}
					{
						JButton btnRemChecklist = new JButton("Rem Check List");
						pnlPreviewSouth.add(btnRemChecklist);
						btnRemChecklist.setActionCommand("Rem Check List");
						btnRemChecklist.addActionListener(this);
						if(UserInfo.EmployeeCode.equals("900027")) {
							btnRemChecklist.setEnabled(false);
						}
					}
				}
			}
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
						
						JPanel pnlClient = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlClient, c);		
						pnlClient.setBorder(components.JTBorderFactory.createTitleBorder("Client Details" ));// XXX
						{
							GridBagConstraints cons_client = new GridBagConstraints();
							cons_client.fill = GridBagConstraints.BOTH;
							cons_client.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1 client*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 0;
								
								lblClient = new JLabel("Client's Name");
								pnlClient.add(lblClient, cons_client);
								lblClient.setFont(FncGlobal.sizeLabel);													
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 0; 
								
								lookupClient = new _JLookup(null, "Client", 0);
								pnlClient.add(lookupClient, cons_client);
								lookupClient.setLookupSQL(sqlClients());
								lookupClient.setPreferredSize(new Dimension(100, 0));
								lookupClient.setFilterName(true);
								lookupClient.setFont(FncGlobal.sizeTextValue);
								lookupClient.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null){
											String entity_id = (String) data[0];
											String entity_name = (String) data[1];
											String unit_desc = (String) data[2];
											String unit_id = (String) data[3];
											Integer seq_no = (Integer) data[4];
											String proj_id = (String) data[5];
											String proj_name = (String) data[6];											
											String tin_no = (String) data[8];

											txtClient.setText(entity_name);
											txtUnitID.setText(unit_id);
											txtUnitDesc.setText(unit_desc);
											txtProjID.setText(proj_id);
											txtProject.setText(proj_name);
											txtSeqNo.setText(seq_no.toString());
											txtTIN_No.setText(tin_no);

										}
									}
								});																												
							}
							{
								cons_client.weightx = 1.5;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 0; 
								
								txtClient = new _JXTextField();
								pnlClient.add(txtClient, cons_client);
								txtClient.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 2 project*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 1; 
								
								lblProject = new JLabel("Project");
								pnlClient.add(lblProject, cons_client);
								lblProject.setFont(FncGlobal.sizeLabel);													
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;
	
								cons_client.gridx = 1; 
								cons_client.gridy = 1; 
								
								txtProjID = new _JXTextField();
								pnlClient.add(txtProjID, cons_client);
								txtProjID.setFont(FncGlobal.sizeTextValue);
								txtProjID.setHorizontalAlignment(JTextField.CENTER);
								txtProjID.setPreferredSize(new Dimension(100, 0));
							}
							{
								cons_client.weightx = 1.5;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 1; 
								
								txtProject = new _JXTextField();
								pnlClient.add(txtProject, cons_client);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 3 ph-blk-lt*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 2;
								
								lblUnitDesc = new JLabel("Phase/Block/Lot");
								pnlClient.add(lblUnitDesc, cons_client);
								lblUnitDesc.setFont(FncGlobal.sizeLabel);									
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 2; 
								
								txtUnitID = new _JXTextField();
								pnlClient.add(txtUnitID, cons_client);
								txtUnitID.setFont(FncGlobal.sizeTextValue);
								txtUnitID.setHorizontalAlignment(JTextField.CENTER);
								txtUnitID.setPreferredSize(new Dimension(100, 0));
							}
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 2;
								
								txtUnitDesc = new _JXTextField();
								pnlClient.add(txtUnitDesc, cons_client);
								txtUnitDesc.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 4 sequence*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 3; 
								
								lblSeqNo = new JLabel("Seq.");
								pnlClient.add(lblSeqNo, cons_client);		
								lblSeqNo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 3;
								
								txtSeqNo = new _JXTextField();
								pnlClient.add(txtSeqNo, cons_client);		
								txtSeqNo.setFont(FncGlobal.sizeTextValue);
								txtSeqNo.setHorizontalAlignment(JTextField.CENTER);
								txtSeqNo.setPreferredSize(new Dimension(100, 0));
							}
							
							/*LINE 5 tin*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 4; 
								
								lblTinNo = new JLabel("TIN No.");
								pnlClient.add(lblTinNo, cons_client);
								lblTinNo.setFont(FncGlobal.sizeLabel);							
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 4;
								
								cons_client.gridwidth = 2;
								
								txtTIN_No = new _JXTextField();
								pnlClient.add(txtTIN_No, cons_client);	
								txtTIN_No.setFont(FncGlobal.sizeTextValue);
								txtTIN_No.setHorizontalAlignment(JTextField.CENTER);
							}
							
//							{
//								cons_client.weightx = 0;
//								cons_client.weighty = 1;
//
//								cons_client.gridx = 0; 
//								cons_client.gridy = 5; 
//								
//								JLabel lblReportName = new JLabel("Report Name");
//								pnlClient.add(lblReportName, cons_client);
//								lblReportName.setFont(FncGlobal.sizeLabel);							
//							}
//							{
//								cons_client.weightx = 0.25;
//								cons_client.weighty = 1;
//
//								cons_client.gridx = 1; 
//								cons_client.gridy = 5;
//								
//								cons_client.gridwidth = 2;
//								
//								cmbReportName = new JComboBox(new DefaultComboBoxModel(new String[] {""}));
//								pnlClient.add(cmbReportName, cons_client);	
//							}
						}
					}	
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1, 3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
				}
			}
		}
	}


	private String sqlClients(){
		/*return "SELECT a.entity_id as \"ID\", b.entity_name \"Name\", \n" + 
				"(CASE WHEN e.oth_pbl_id IS null then c.description else FORMAT('%s/%s', c.description, f.lot) end) as \"Unit\",\n" + 
				"c.unit_id as \"Unit ID\",a.projcode as \"Proj. ID\", d.proj_name as \"Proj. Name\", a.seq_no as \"Seq\",\n" + 
				"g.pmt_status as \"Pmt Status\"\n, h.tin_no as \"Tin No.\"" + 
				"from rf_sold_unit a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id \n" + 
				"LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_project d on d.proj_id = a.projcode\n" + 
				"LEFT JOIN hs_sold_other_lots e on e.entity_id = a.entity_id and e.proj_id = a.projcode and e.pbl_id = a.pbl_id and e.seq_no = a.seq_no \n" + 
				"LEFT JOIN mf_unit_info f on f.proj_id = a.projcode and f.pbl_id = e.oth_pbl_id \n" + 
				"LEFT JOIN rf_card_pmt_status g on g.entity_id = a.entity_id and g.proj_id = a.projcode and g.pbl_id = a.pbl_id and g.seq_no = a.seq_no \n"+
				"LEFT JOIN rf_entity_id_no h on h.entity_id = a.entity_id and h.status_id = 'A' \n" + 
				"where a.currentstatus !=  '02'\n" + 
				"AND a.status_id != 'I'\n" + 
				"ORDER BY b.entity_name;";*/
		
		String SQL = "select * from view_sql_clients()";

		FncSystem.out("Clients", SQL);
		return SQL;
	}

	private String sqlEmployee(){
		return "SELECT a.entity_id as \"ID\", a.entity_name as \"Name\"\n" + 
				"FROM rf_entity a \n" + 
				"LEFT JOIN em_employee b on b.entity_id = a.entity_id \n" + 
				"where b.dept_code = '18'\n" + 
				"OR a.entity_id = '1212121212'";
	}
	
	private boolean toPreview(){
		/*if(lookupPresenter.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select presenter", "Preview", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		if(lookupClient.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client", "Preview", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	private void showDlg(){
		
		JOptionPane.showOptionDialog(getContentPane(), pnlPreview, "Preview",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

//		Map<String, Object> mapParameters = new HashMap<String, Object>();
//		mapParameters.put("entity_id", lookupClient.getValue());
//		mapParameters.put("proj_id", txtProjID.getText());
//		mapParameters.put("unit_id", txtUnitID.getText());
//		mapParameters.put("seq_no", Integer.valueOf(txtSeqNo.getText()));
//		//mapParameters.put("presenter", txtPresenter.getText().trim());
//		mapParameters.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "1904.jpg"));
//
//		FncReport.generateReport("/Reports/rptForm1904.jasper", "Form 1904", mapParameters);
		
		/*Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "lra_form2.jpg"));
		FncReport.generateReport("/Reports/rptLRA_Form2.jasper", "LRA Form2", mapParameters2);*/
	}
	
	
	private void editAmt() {
		{
			pnlForm2000OTAmt = new JPanel(new BorderLayout(5,5));
			pnlForm2000OTAmt.setPreferredSize(new Dimension(300,50));
			{
				JPanel pnlPreviewCenter = new JPanel(new BorderLayout(5,5));
				pnlForm2000OTAmt.add(pnlPreviewCenter, BorderLayout.CENTER);
				{
					JLabel lblTaxDueAmt = new JLabel("Please input amt");
					pnlPreviewCenter.add(lblTaxDueAmt, BorderLayout.WEST);
					lblTaxDueAmt.setHorizontalAlignment(JLabel.CENTER);				
				}
				{
					txtTaxDueAmt = new _JXFormattedTextField(JXTextField.RIGHT);
					pnlPreviewCenter.add(txtTaxDueAmt, BorderLayout.CENTER);
					txtTaxDueAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
				}
			}
			{
				JPanel pnlPreviewSouth = new JPanel(new BorderLayout(5, 5));
				pnlForm2000OTAmt.add(pnlPreviewSouth, BorderLayout.SOUTH);
				{
					JButton btnOk = new JButton("Ok");
					pnlPreviewSouth.add(btnOk);
					btnOk.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							preview2000ot((BigDecimal)txtTaxDueAmt.getValued());
							
						}
					});
				}
			}
		}
	}
	
	private void previewForm1904() {
		// TODO Auto-generated method stub
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", lookupClient.getValue());
		mapParameters.put("proj_id", txtProjID.getText());
		mapParameters.put("unit_id", txtUnitID.getText());
		mapParameters.put("seq_no", Integer.valueOf(txtSeqNo.getText()));
		//mapParameters.put("presenter", txtPresenter.getText().trim());
		mapParameters.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "1904.jpg"));

		FncReport.generateReport("/Reports/rptForm1904.jasper", "Form 1904", mapParameters);
		
		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlPreview);
		optionPaneWindow.dispose();
	}
	
	private void preview2000ot(BigDecimal tax_due_amt) {
		
		
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_form_2000ot_taxdue_amt('"+lookupClient.getValue()+"', '"+txtProjID.getText()+"', '"+txtUnitID.getText()+"', "+txtSeqNo.getText()+", "+tax_due_amt+", '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		FncSystem.out("SQL of Tax Due Amt", SQL);
		
		// TODO Auto-generated method stub
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", lookupClient.getValue());
		mapParameters.put("proj_id", txtProjID.getText());
		mapParameters.put("unit_id", txtUnitID.getText());
		mapParameters.put("seq_no", Integer.valueOf(txtSeqNo.getText()));
		//mapParameters.put("presenter", txtPresenter.getText().trim());
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "2000OT.jpg"));

		FncReport.generateReport("/Reports/rptFormBIR2000OT.jasper", "BIR 2000-OT", mapParameters);
		
		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlPreview);
		optionPaneWindow.dispose();
		
		Window optionPaneWindow2 = SwingUtilities.getWindowAncestor(pnlForm2000OTAmt);
		optionPaneWindow2.dispose();
	}
	
	private void preview2000() {
		// TODO Auto-generated method stub
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", lookupClient.getValue());
		mapParameters.put("proj_id", txtProjID.getText());
		mapParameters.put("unit_id", txtUnitID.getText());
		mapParameters.put("seq_no", Integer.valueOf(txtSeqNo.getText()));
		//mapParameters.put("presenter", txtPresenter.getText().trim());
		mapParameters.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "2000.jpg"));

		FncReport.generateReport("/Reports/rptFormBIR2000.jasper", "BIR 2000", mapParameters);
		
		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlPreview);
		optionPaneWindow.dispose();
		
	}
	
	private void previewRTSlip() {
		// TODO Auto-generated method stub
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", lookupClient.getValue());
		mapParameters.put("proj_id", txtProjID.getText());
		mapParameters.put("unit_id", txtUnitID.getText());
		mapParameters.put("seq_no", Integer.valueOf(txtSeqNo.getText()));
		//mapParameters.put("presenter", txtPresenter.getText().trim());
		mapParameters.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "rtslip.png"));

		FncReport.generateReport("/Reports/rptFormRT_Slip.jasper", "Routing Slip", mapParameters);
		
		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlPreview);
		optionPaneWindow.dispose();
		
	}
	
	private void previewRemCheckList() {
		// TODO Auto-generated method stub
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", lookupClient.getValue());
		mapParameters.put("proj_id", txtProjID.getText());
		mapParameters.put("unit_id", txtUnitID.getText());
		mapParameters.put("seq_no", Integer.valueOf(txtSeqNo.getText()));
		//mapParameters.put("presenter", txtPresenter.getText().trim());
		mapParameters.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "rtslip.png"));

		FncReport.generateReport("/Reports/rptRemChecklist.jasper", "Rem CheckList", mapParameters);
		
		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlPreview);
		optionPaneWindow.dispose();
		
	}

	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();


		if(actionCommand.equals("Preview")){
			if(toPreview()){
				showDlg();
			}
		}
		
		if(actionCommand.equals("1904")){
			if(toPreview()){
				previewForm1904();
			}
		}
		
		if(actionCommand.equals("2000ot")){
			if(toPreview()){
				//preview2000ot();
				editAmt();
				JOptionPane.showOptionDialog(getContentPane(), pnlForm2000OTAmt, "Edit Amount",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
				
			}
		}
		
		if(actionCommand.equals("2000")){
			if(toPreview()){
				preview2000();
			}
		}
		
		if(actionCommand.equals("RT SLIP")){
			if(toPreview()){
				previewRTSlip();
			}
		}
		
		if(actionCommand.equals("Rem Check List")) {
			if(toPreview()) {
				previewRemCheckList();
			}
		}

	}

}
