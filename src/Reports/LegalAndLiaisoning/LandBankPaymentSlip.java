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

public class LandBankPaymentSlip extends _JInternalFrame implements _GUI, ActionListener {
	
	/*
	 * CHANGES AS OF 2021-09-23
	 * 
	 * 1. CHANGES IN PREVIEWING LRA FORM - IF CENQ, PRESENTER IS BRETT 2021-09-23
	 * 
	 * */

	private static final long serialVersionUID = 1569364752923322801L;
	static String title = "Land Bank Payment Slip";

	Dimension frameSize = new Dimension(600, 300);
	private JPanel pnlCenter;
	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JPanel pnlNWLabel;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblCheck;
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
	private _JXTextField txtPayee;
	private _JXTextField txtSeqNo;
	
	private _JLookup lookupCheck;
	private _JXTextField txtCheck;

	private JPanel pnlSouth;
	private JButton btnPreview;
	private JPanel pnlMain;
	private String entity_id;

	public LandBankPaymentSlip() {
		super(title, true, true, true, true);
		initGUI();
	}

	public LandBankPaymentSlip(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public LandBankPaymentSlip(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, true, true, true, true);
		initGUI();
	}

	
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
											entity_id = (String) data[0];
											String entity_name = (String) data[1];										
											String proj_id = (String) data[4];
											String proj_name = (String) data[5];
										
											txtClient.setText(entity_name);
											txtProjID.setText(proj_id);
											txtProject.setText(proj_name);
										
											lookupCheck.setLookupSQL(sqlChecks());

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
							
							/*LINE 2 clients name*/
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
								txtProjID.setPreferredSize(new Dimension(100, 0));	
								txtProjID.setFont(FncGlobal.sizeTextValue);
								txtProjID.setHorizontalAlignment(JTextField.CENTER);
														
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
							
							
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 0; 
								cons_client.gridy = 2;
								
								lblCheck = new JLabel("Check Number");
								pnlClient.add(lblCheck, cons_client);		
								lblCheck.setFont(FncGlobal.sizeLabel);			
								
								
							}
							{
								cons_client.weightx = 0.25;
								cons_client.weighty = 1;

								cons_client.gridx = 1; 
								cons_client.gridy = 2; 
								
								lookupCheck = new _JLookup(null, "Check Number", 1);
								pnlClient.add(lookupCheck, cons_client);
								lookupCheck.setLookupSQL(sqlChecks());
								lookupCheck.setPreferredSize(new Dimension(100, 0));
								lookupCheck.setFilterName(true);
								lookupCheck.setFont(FncGlobal.sizeTextValue);
								lookupCheck.addLookupListener(new LookupListener() {

							

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null){
											entity_id = (String) lookupClient.getValue();
											String payeee = (String) data[5];
											
											
											txtPayee.setText(payeee);
											
							
										}
									}
								});	
								
							}
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 2;
								
								txtPayee = new _JXTextField();
								pnlClient.add(txtPayee, cons_client);
								txtPayee.setFont(FncGlobal.sizeTextValue);
							}
							
							
					/*		{
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
								txtSeqNo.setPreferredSize(new Dimension(100,0));
								txtSeqNo.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								cons_client.weightx = 0;
								cons_client.weighty = 1;

								cons_client.gridx = 2; 
								cons_client.gridy = 3;
								
								txtPayee = new _JXTextField();
								pnlClient.add(txtPayee, cons_client);
								txtPayee.setFont(FncGlobal.sizeTextValue);
							}*/
						
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
		
		String sql = "select * from(\n" + 
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
	
		FncSystem.out("CLIENT DETAILS", sql);
		
		return sql;
	}
	
	private String sqlChecks(){
		
		String sql = "SELECT  \n" + 
				"\n" + 
				"get_client_name(c.entity_id) as \"Client Name\",\n" + 
				"                b.check_no as \"Check Nor\",\n" + 
				"                a.cv_no as \"DV No\", \n" + 
				"                a.co_id as \"Company ID\",\n" + 
				"                a.rplf_no as \"Rplf No\",\n" + 
				"get_client_name(b.entity_id) as \"Payee\"\n" + 
				"\n" + 
				"FROM  rf_pv_header a\n" + 
				"LEFT JOIN rf_check b on b.co_id = a.co_id and b.cv_no = a.cv_no and b.status_id ='A'\n" + 
				"LEFT JOIN rf_transfer_cost c on c.co_id = a.co_id and c.rplf_no = a.pv_no\n" + 
				"\n" + 
				"\n" + 
				"WHERE   c.entity_id = '"+ lookupClient.getValue() +"'\n" + 
				"AND     a.status_id = 'P'\n" + 
				"";
	
		FncSystem.out("sqlChecks", sql);
		
		return sql;
	}

	
	private boolean toPreview(){
		if(lookupClient.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client", "Preview", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	private void previewLBP(){
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_entity_id", lookupClient.getValue());
		mapParameters.put("proj_id", txtProjID.getText());
		mapParameters.put("p_check_no", lookupCheck.getValue());
		
		

		FncReport.generateReport("/Reports/rptLBP_PaymentSlip.jasper", "Land Bank Payment Slip", mapParameters);
		
	}

	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();


		if(actionCommand.equals("Preview")){
			if(toPreview()){
				previewLBP();
			}
		}

	}
}
