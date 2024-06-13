package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Accounting.Collections.CheckStatusMonitoring;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Reports.Accounting.SummaryofDeposits;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;

public class TransferTaxReceipt extends _JInternalFrame implements _GUI, ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Dimension frameSize = new Dimension(700, 450);
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;
	private JLabel lblEntity;
	private _JLookup lookupEntity;
	private _JXTextField txtEntity;
	private JLabel lblPBL;
	private JTextField txtPBL;
	private JTextField txtUnit;
	private JLabel lblSeqNo;
	private _JXTextField txtSeqNo;
	private JPanel pnlSouth;
	private JButton btnPreview;
	protected String co_id;
	protected String company;
	protected String proj_id;
	protected String proj_name;
	private JButton btnCancel;
	private JLabel lblDate;
	private _JDateChooser dteDate;
	private JLabel lblCAVNo;
	private JTextField txtCAVNo;

	public TransferTaxReceipt() {
		// TODO Auto-generated constructor stub
		super("OR Transfer Tax", true, true, true, true);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				//pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Details" ));// XXX
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					//c.insets = new Insets(5, 5, 5, 5);
					{
						c.weightx = 1;
						c.weighty = 0.5;

						c.gridx = 0; 
						c.gridy = 0; 
						
						JPanel pnlCompany = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCompany, c);
						pnlCompany.setBorder(components.JTBorderFactory.createTitleBorder("Company" ));// XXX
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.BOTH;
							cons_com.insets = new Insets(5, 5, 5, 5);
							
							//LINE1
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
								
								lookupCompany = new _JLookup(null, "Company");
								pnlCompany.add(lookupCompany, cons_com);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SummaryofDeposits.company());
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								//lookupCompany.setPreferredSize(new Dimension(60,0));
								lookupCompany.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										
										if(data!=null) {
											co_id = (String) data[0];
											company = (String) data[1];
											
											clearFields();
											lookupProject.setValue(null);
											txtProject.setText("");
											
											txtCompany.setText(company);
											lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
										}	
									}
								});
							}
							{
								cons_com.weightx = 1;
								cons_com.weighty = 1;
								
								cons_com.gridx = 2;
								cons_com.gridy = 0;
								
								txtCompany = new JTextField();
								pnlCompany.add(txtCompany, cons_com);
								txtCompany.setEditable(false);
								txtCompany.setFont(FncGlobal.sizeTextValue);
							}
							
							//LINE2
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;
								
								cons_com.gridx = 0;
								cons_com.gridy = 1;
								
								lblProject = new JLabel("Project", JLabel.CENTER);
								pnlCompany.add(lblProject, cons_com);
								lblProject.setEnabled(true);
								lblProject.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;
								
								cons_com.gridx = 1;
								cons_com.gridy = 1;
								
								lookupProject = new _JLookup(null, "Project");
								pnlCompany.add(lookupProject, cons_com);
								lookupProject.setReturnColumn(0);
								lookupProject.setFont(FncGlobal.sizeTextValue);
								//lookupProject.setPreferredSize(new Dimension(60,0));
								lookupProject.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										
										if(data!=null) {
											proj_id = (String) data[0];		
											proj_name = (String) data[1];
											
											txtProject.setText(proj_name);
											lookupEntity.setLookupSQL(sqlClients(proj_id));
											
											clearFields();
											
										}
										
									}
								});
							}
							{
								cons_com.weightx = 1;
								cons_com.weighty = 1;
								
								cons_com.gridx = 2;
								cons_com.gridy = 1;
								
								txtProject = new JTextField();
								pnlCompany.add(txtProject, cons_com);
								txtProject.setEditable(false);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 1;
						
						JPanel pnlDetails = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlDetails, c);
						pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
						{
							GridBagConstraints cons_details = new GridBagConstraints();
							cons_details.fill = GridBagConstraints.BOTH;
							cons_details.insets = new Insets(5, 5, 5, 5);
							
							//LINE3
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;
								
								cons_details.gridx = 0;
								cons_details.gridy = 0;
								
								lblEntity = new JLabel("Client's Name", JLabel.CENTER);
								pnlDetails.add(lblEntity, cons_details);
								lblEntity.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_details.weightx = 0.25;
								cons_details.weighty = 1;
								
								cons_details.gridx = 1;
								cons_details.gridy = 0;
								
								lookupEntity = new _JLookup(null, "Entity");
								pnlDetails.add(lookupEntity, cons_details);
								lookupEntity.setReturnColumn(0);
								lookupEntity.setFont(FncGlobal.sizeTextValue);
								lookupEntity.setFilterIndex(1);
								//lookupEntity.setPreferredSize(new Dimension(60,0));
								lookupEntity.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										
										if(data!=null) {
											String entity_name = (String) data[1];
											String unit_desc = (String) data[2];
											String pbl_id = (String) data[3];
											Integer seq_no = (Integer) data[6];
											
											txtEntity.setText(entity_name);
											txtUnit.setText(unit_desc);
											txtPBL.setText(pbl_id);
											txtSeqNo.setText(Integer.toString(seq_no));
											
											dteDate.setEnabled(true);
											txtCAVNo.setEnabled(true);
											btnPreview.setEnabled(true);
										}
									}
								});;
							}
							{
								cons_details.weightx = 1;
								cons_details.weighty = 1;
								
								cons_details.gridx = 2;
								cons_details.gridy = 0;
								
								txtEntity = new _JXTextField();
								pnlDetails.add(txtEntity, cons_details);							
								txtEntity.setFont(FncGlobal.sizeTextValue);
							}
							
							//LINE4
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;
								
								cons_details.gridx = 0;
								cons_details.gridy = 1;
								
								lblPBL = new JLabel("PBL", JLabel.CENTER);
								pnlDetails.add(lblPBL, cons_details);
								lblPBL.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_details.weightx = 0.25;
								cons_details.weighty = 1;
								
								cons_details.gridx = 1;
								cons_details.gridy = 1;
								
								txtPBL = new JTextField("");
								pnlDetails.add(txtPBL, cons_details);
								txtPBL.setFont(FncGlobal.sizeTextValue);
								txtPBL.setEditable(false);
								//txtPBL.setEnabled(false);
								txtPBL.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								cons_details.weightx = 1;
								cons_details.weighty = 1;
								
								cons_details.gridx = 2;
								cons_details.gridy = 1;
								
								txtUnit = new JTextField("");
								pnlDetails.add(txtUnit, cons_details);
								txtUnit.setFont(FncGlobal.sizeTextValue);
								txtUnit.setEditable(false);
								//txtUnit.setEnabled(false);
							}
							
							//LINE5
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;
								
								cons_details.gridx = 0;
								cons_details.gridy = 2;
								
								lblSeqNo = new JLabel("Seq", JLabel.CENTER);
								pnlDetails.add(lblSeqNo, cons_details);
								lblSeqNo.setFont(FncGlobal.sizeLabel);	
							}
							{
								cons_details.weightx = 0.25;
								cons_details.weighty = 1;
								
								cons_details.gridx = 1;
								cons_details.gridy = 2;
								
								txtSeqNo = new _JXTextField();
								pnlDetails.add(txtSeqNo, cons_details);
								txtSeqNo.setFont(FncGlobal.sizeTextValue);
								txtSeqNo.setHorizontalAlignment(JTextField.CENTER);
								txtSeqNo.setEditable(false);
								//txtSeqNo.setEnabled(false);			
							}
							
							//LINE6
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;
								
								cons_details.gridx = 0;
								cons_details.gridy = 3;
								
								lblDate = new JLabel("Date", JLabel.CENTER);
								pnlDetails.add(lblDate, cons_details);
								//lblDate.setEnabled(false);
								lblDate.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_details.weightx = 0.25;
								cons_details.weighty = 1;
								
								cons_details.gridx = 1;
								cons_details.gridy = 3;
								
								dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDetails.add(dteDate, cons_details);		
								dteDate.setDate(FncGlobal.dateFormat("2017-01-01"));
								dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteDate.setEnabled(false);
								dteDate.setFont(FncGlobal.sizeTextValue);
							}
							
							//LINE7
							{
								cons_details.weightx = 0;
								cons_details.weighty = 1;
								
								cons_details.gridx = 0;
								cons_details.gridy = 4;
								
								lblCAVNo = new JLabel("CAV No.", JLabel.CENTER);
								pnlDetails.add(lblCAVNo, cons_details);
								lblCAVNo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_details.weightx = 0.25;
								cons_details.weighty = 1;
								
								cons_details.gridx = 1;
								cons_details.gridy = 4;
								
								txtCAVNo = new JTextField("");
								pnlDetails.add(txtCAVNo, cons_details);
								txtCAVNo.setHorizontalAlignment(JTextField.CENTER);
								txtCAVNo.setFont(FncGlobal.sizeTextValue);
								txtCAVNo.setEnabled(false);
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1,2,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
					btnPreview.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setFont(FncGlobal.sizeControls);
				}
			}
		}
		
		initialize_comp();
		
	}
	
	private void initialize_comp() {
		
		co_id = "02";
		proj_id = "015";
		
		lookupCompany.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		lookupProject.setValue("015");
		txtProject.setText("TERRAVERDE RESIDENCES");
		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupEntity.setLookupSQL(sqlClients(proj_id));
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview")) {		preview();}
		
		if(e.getActionCommand().equals("Cancel")) {			cancel();}
		
		
	}
	
	private void clearFields() {
		
		lookupEntity.setValue(null);
		txtEntity.setText("");
		txtPBL.setText("");
		txtUnit.setText("");
		txtSeqNo.setText("");
		txtCAVNo.setText("");
		dteDate.setEnabled(false);
		txtCAVNo.setEnabled(false);
		btnPreview.setEnabled(false);

	}
	
	private void cancel() {
		
		clearFields();
		lookupCompany.setValue(null);
		lookupProject.setValue(null);
		txtCompany.setText("");
		txtProject.setText("");
	}
	
	private void preview() {
		
		String criteria = "OR Transfer Tax";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		
		String entity_id = lookupEntity.getText();
		String pbl_id = txtPBL.getText();
		String client = txtEntity.getText();
		Integer seq_no = Integer.parseInt(txtSeqNo.getText());
		String proj_id = lookupProject.getText();
		java.util.Date actual_date = dteDate.getDate();
		String cav_no = txtCAVNo.getText();
		
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_background", this.getClass().getClassLoader().getResourceAsStream("Images/TransferTaxOR.jpg"));
		mapParameters.put("p_entity_id", entity_id);
		mapParameters.put("p_pbl_id", pbl_id);
		mapParameters.put("p_seq_no", seq_no);
		mapParameters.put("p_proj_id", proj_id);
		mapParameters.put("p_client", client);
		mapParameters.put("p_date", actual_date);
		mapParameters.put("p_cav_no", cav_no);
		
		
		FncReport.generateReport("/Reports/rptORTransferTax.jasper", reportTitle, company, mapParameters);
		
	}

	private String sqlClients(String proj_id){
		
		return "select * from(\n" + 
				"SELECT a.entity_id as \"ID\", b.entity_name \"Name\",\n" + 
				"				(CASE WHEN e.oth_pbl_id IS null then c.description else FORMAT('%s/%s', c.description, f.lot) end) as \"Unit\",\n" + 
				"				a.pbl_id as \"PBL\",a.projcode as \"Proj. ID\", d.proj_name as \"Proj. Name\", a.seq_no as \"Seq\"\n" + 
				"				from rf_sold_unit a\n" + 
				"				left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"				LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id\n" + 
				"				LEFT JOIN mf_project d on d.proj_id = a.projcode\n" + 
				"				LEFT JOIN hs_sold_other_lots e on e.entity_id = a.entity_id and e.proj_id = a.projcode and e.pbl_id = a.pbl_id and e.seq_no = a.seq_no\n" + 
				"				LEFT JOIN mf_unit_info f on f.proj_id = a.projcode and f.pbl_id = e.oth_pbl_id\n" + 
				"				LEFT JOIN rf_card_pmt_status g on g.entity_id = a.entity_id and g.proj_id = a.projcode and g.pbl_id = a.pbl_id and g.seq_no = a.seq_no\n" + 
				"				where a.currentstatus !=  '02'\n" + 
				"				AND a.status_id != 'I'\n" + 
				"				AND a.projcode = '"+proj_id+"'\n" + 
				"				ORDER BY b.entity_name) a\n" + 
				"\n" + 
				"union all\n" + 
				"\n" + 
				"Select * from \n" + 
				"(select \n" + 
				"				''::Varchar as \"Entity ID\",\n" + 
				"				''::varchar as \"Client\",\n" + 
				"				format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Unit\",\n" + 
				"				a.pbl_id AS \"PBL\",\n" + 
				"				btrim(a.proj_id::text)::character varying AS \"Projcode\",\n" + 
				"				d.proj_name::text as \"Proj Name\",\n" + 
				"				null::int AS \"Sequence\"\n" + 
				"				FROM mf_unit_info_pending a\n" + 
				"				LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text\n" + 
				"				AND a.proj_id = '"+proj_id+"'\n" + 
				"				ORDER BY a.phase, a.block::INT, a.lot::INt) b	";
	}
}
