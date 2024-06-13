package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

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

public class LRA_Forms extends _JInternalFrame implements _GUI, ActionListener {
	
	/*
	 * CHANGES AS OF 2021-09-23
	 * 
	 * 1. CHANGES IN PREVIEWING LRA FORM - IF CENQ, PRESENTER IS BRETT 2021-09-23
	 * 
	 * */

	private static final long serialVersionUID = 1569364752923322801L;
	static String title = "LRA Form";

	Dimension frameSize = new Dimension(600, 300);
	private JPanel pnlCenter;
	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JPanel pnlNWLabel;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnitDesc;
	private JLabel lblSeqNo;

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

	private JPanel pnlSouth;
	private JButton btnPreview;
	private JPanel pnlMain;

	public LRA_Forms() {
		super(title, true, true, true, true);
		initGUI();
	}

	public LRA_Forms(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public LRA_Forms(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
//					{
//						JLabel lblPresenter = new JLabel("Presenter Name");
//						pnlNWLabel.add(lblPresenter);
//					}
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
//				}
//				{
//					pnlNWComponent = new JPanel(new GridLayout(5, 1));
//					pnlCenter.add(pnlNWComponent, BorderLayout.CENTER);
//					{
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
//					}
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
//
//										txtClient.setText(entity_name);
//										txtUnitID.setText(unit_id);
//										txtUnitDesc.setText(unit_desc);
//										txtProjID.setText(proj_id);
//										txtProject.setText(proj_name);
//										if(seq_no == null || seq_no.equals("")){
//											txtSeqNo.setText(null);
//										}else{
//											txtSeqNo.setText(seq_no.toString());
//										}
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
							
							/*LINE 1 presenters name*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 0;
								
								JLabel lblPresenter = new JLabel("Presenter Name");
								pnlClient.add(lblPresenter, cons_client);
								lblPresenter.setFont(FncGlobal.sizeLabel);													
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 0; 
								
								lookupPresenter = new _JLookup(null, "Presenter", 0);
								pnlClient.add(lookupPresenter, cons_client);
								lookupPresenter.setLookupSQL(sqlEmployee());
								lookupPresenter.setPreferredSize(new Dimension(100, 0));
								lookupPresenter.setFilterName(true);
								lookupPresenter.setFont(FncGlobal.sizeTextValue);
								lookupPresenter.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										FncSystem.out("Display SQL for presenter", sqlEmployee());
										
										if(data != null){
											String presenter_name = (String) data[1];
											txtPresenter.setText(presenter_name);
										}
									}
								});																												
							}
							{
								cons_client.weightx = 1.5;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 0; 
								
								txtPresenter = new _JXTextField();
								pnlClient.add(txtPresenter, cons_client);
								txtPresenter.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 2 clients name*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 1; 
								
								lblClient = new JLabel("Client's Name");
								pnlClient.add(lblClient, cons_client);
								lblClient.setFont(FncGlobal.sizeLabel);													
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;
	
								cons_client.gridx = 1; 
								cons_client.gridy = 1; 
								
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
											String proj_id = (String) data[4];
											String proj_name = (String) data[5];
											Integer seq_no = (Integer) data[6];
						

											txtClient.setText(entity_name);
											txtUnitID.setText(unit_id);
											txtUnitDesc.setText(unit_desc);
											txtProjID.setText(proj_id);
											txtProject.setText(proj_name);
											if(seq_no == null || seq_no.equals("")){
												txtSeqNo.setText(null);
											}else{
												txtSeqNo.setText(Integer.toString(seq_no));
											}

										}
									}
								});							
							}
							{
								cons_client.weightx = 1.5;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 1; 
								
								txtClient = new _JXTextField();
								pnlClient.add(txtClient, cons_client);							
								txtClient.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 3 project*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 2;
								
								lblProject = new JLabel("Project");
								pnlClient.add(lblProject, cons_client);
								lblProject.setFont(FncGlobal.sizeLabel);									
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 2; 
								
								txtProjID = new _JXTextField();
								pnlClient.add(txtProjID, cons_client);
								txtProjID.setPreferredSize(new Dimension(100, 0));	
								txtProjID.setFont(FncGlobal.sizeTextValue);
								txtProjID.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 2;
								
								txtProject = new _JXTextField();
								pnlClient.add(txtProject, cons_client);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 4 ph-blk-lot*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 3; 
								
								lblUnitDesc = new JLabel("Phase/Block/Lot");
								pnlClient.add(lblUnitDesc, cons_client);		
								lblUnitDesc.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 3;
								
								txtUnitID = new _JXTextField();
								pnlClient.add(txtUnitID, cons_client);
								txtUnitID.setPreferredSize(new Dimension(100, 0));	
								txtUnitID.setFont(FncGlobal.sizeTextValue);
								txtUnitID.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 3;
								
								txtUnitDesc = new _JXTextField();
								pnlClient.add(txtUnitDesc, cons_client);
								txtUnitDesc.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 5 seq*/
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 4; 
								
								lblSeqNo = new JLabel("Seq.");
								pnlClient.add(lblSeqNo, cons_client);
								lblSeqNo.setFont(FncGlobal.sizeLabel);							
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 4;
								
								txtSeqNo = new _JXTextField();
								pnlClient.add(txtSeqNo, cons_client);
								txtSeqNo.setFont(FncGlobal.sizeTextValue);
								txtSeqNo.setPreferredSize(new Dimension(100,0));
								txtSeqNo.setHorizontalAlignment(JTextField.CENTER);
							}
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


	private String sqlClients(){//**EDITED BY JED 2019-04-17 : TO DISPLAY PENDING UNITS**//
		/*return "SELECT a.entity_id as \"ID\", b.entity_name \"Name\", \n" + 
				"(CASE WHEN e.oth_pbl_id IS null then c.description else FORMAT('%s/%s', c.description, f.lot) end) as \"Unit\",\n" + 
				"c.unit_id as \"Unit ID\",a.projcode as \"Proj. ID\", d.proj_name as \"Proj. Name\", a.seq_no as \"Seq\",\n" + 
				"g.pmt_status as \"Pmt Status\"\n" + 
				"from rf_sold_unit a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id \n" + 
				"LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_project d on d.proj_id = a.projcode\n" + 
				"LEFT JOIN hs_sold_other_lots e on e.entity_id = a.entity_id and e.proj_id = a.projcode and e.pbl_id = a.pbl_id and e.seq_no = a.seq_no\n" + 
				"LEFT JOIN mf_unit_info f on f.proj_id = a.projcode and f.pbl_id = e.oth_pbl_id\n" + 
				"LEFT JOIN rf_card_pmt_status g on g.entity_id = a.entity_id and g.proj_id = a.projcode and g.pbl_id = a.pbl_id and g.seq_no = a.seq_no\n" + 
				"where a.currentstatus !=  '02'\n" + 
				"AND a.status_id != 'I'\n" + 
				"ORDER BY b.entity_name;";*/
		
		return "select * from(\n" + 
				"SELECT a.entity_id as \"ID\", b.entity_name \"Name\",\n" + 
				"				(CASE WHEN e.oth_pbl_id IS null then c.description else FORMAT('%s/%s', c.description, f.lot) end) as \"Unit\",\n" + 
				"				c.unit_id as \"Unit ID\",a.projcode as \"Proj. ID\", d.proj_name as \"Proj. Name\", a.seq_no as \"Seq\",\n" + 
				"				g.pmt_status as \"Pmt Status\"\n" + 
				"				from rf_sold_unit a\n" + 
				"				left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"				LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id\n" + 
				"				LEFT JOIN mf_project d on d.proj_id = a.projcode\n" + 
				"				LEFT JOIN hs_sold_other_lots e on e.entity_id = a.entity_id and e.proj_id = a.projcode and e.pbl_id = a.pbl_id and e.seq_no = a.seq_no\n" + 
				"				LEFT JOIN mf_unit_info f on f.proj_id = a.projcode and f.pbl_id = e.oth_pbl_id\n" + 
				"				LEFT JOIN rf_card_pmt_status g on g.entity_id = a.entity_id and g.proj_id = a.projcode and g.pbl_id = a.pbl_id and g.seq_no = a.seq_no\n" + 
				"				where a.currentstatus !=  '02'\n" + 
				"				AND a.status_id != 'I'\n" + 
				"				ORDER BY b.entity_name) a\n" + 
				"\n" + 
				"union all\n" + 
				"\n" + 
				"Select * from \n" + 
				"(select \n" + 
				"				''::Varchar as \"Entity ID\",\n" + 
				"				''::varchar as \"Client\",\n" + 
				"				format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Unit\",\n" + 
				"				a.unit_id AS \"Unit ID\",\n" + 
				"				btrim(a.proj_id::text)::character varying AS \"Projcode\",\n" + 
				"				d.proj_name::text as \"Proj Name\",\n" + 
				"				null::int AS \"Sequence\",\n" + 
				"				''::text AS \"Pmt Status\"\n" + 
				"				FROM mf_unit_info_pending a\n" + 
				"				LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text\n" + 
				"				ORDER BY a.phase, a.block::INT, a.lot::INt) b";
	}

	private String sqlEmployee(){
		return "SELECT a.entity_id as \"ID\", a.entity_name as \"Name\"\n" + 
				"FROM rf_entity a \n" + 
				"LEFT JOIN em_employee b on b.entity_id = a.entity_id \n" + 
				"where b.dept_code = '18'\n" + 
				"OR a.entity_id = '1212121212'";
	}
	
	private boolean toPreview(){
		if(lookupPresenter.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select presenter", "Preview", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupClient.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client", "Preview", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	private void previewLRA(){
		
		String presenter_id = lookupPresenter.getValue();

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", lookupClient.getValue());
		mapParameters.put("proj_id", txtProjID.getText());
		mapParameters.put("unit_id", txtUnitID.getText());
		mapParameters.put("seq_no", Integer.valueOf(txtSeqNo.getText()));
		//mapParameters.put("seq_no", txtSeqNo.getText());
		mapParameters.put("presenter", txtPresenter.getText().trim());
		mapParameters.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "lra_form.jpg"));
		
		/*ADDED BY JED 2021-09-23 : FOR CENQ THE PRESENTER IS BRETT*/
		
		if(presenter_id.equals("1212121212")) {
			mapParameters.put("strStaff", " - MAR JOVIN CRUZ");
		}else {
			mapParameters.put("strStaff", "");
		}
		if(txtProjID.getText().equals("015")) {
			if(presenter_id.equals("1212121212")) {
				mapParameters.put("strStaff", " - EVEBRETT DOMANAIS ");
			}else {
				mapParameters.put("strStaff", "");
			}
			mapParameters.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "lra_form.png"));
			FncReport.generateReport("/Reports/rptLRA_Form_Cavite.jasper", "LRA Form Cavite", mapParameters);
			
			Map<String, Object> mapParameters2 = new HashMap<String, Object>();
			mapParameters2.put("entity_id", lookupClient.getValue());
			mapParameters2.put("proj_id", txtProjID.getText());
			mapParameters2.put("unit_id", txtUnitID.getText());
			mapParameters2.put("seq_no", Integer.valueOf(txtSeqNo.getText()));
			mapParameters2.put("presenter", txtPresenter.getText().trim());
			mapParameters2.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "lra_form2.png"));
			FncReport.generateReport("/Reports/rptLRA_Form_Cavite_page2.jasper", "LRA Form Cavite Page", mapParameters2);
		
		}else {
			
			FncReport.generateReport("/Reports/rptLRA_Form.jasper", "LRA Form", mapParameters);
		}
		
		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("background", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "lra_form2.jpg"));
		FncReport.generateReport("/Reports/rptLRA_Form2.jasper", "LRA Form2", mapParameters2);
	}

	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();


		if(actionCommand.equals("Preview")){
			if(toPreview()){
				previewLRA();
			}
		}

	}
}
