package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Database.pgSelect;
import Database.pgUpdate;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class Trip_Purpose extends _JInternalFrame implements _GUI, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Trip Purpose";
	public static Dimension frameSize = new Dimension(420,370);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private JLabel lblCode;
	private JLabel lblDesc;
	private JLabel lblAlias;
	private JLabel lblAccount;
	private JLabel lblAcerText;
	private JLabel lblBlank;
	private JLabel lblStatus;
	private JTextField txtCode;
	private JTextField txtDesc;
	private JTextField txtAlias;
	private JTextField txtAcctID;
	private JTextField txtAcetxt;
	private JCheckBox chkGrpHeads;
	private JCheckBox chkDivHeads;
	private JComboBox cmbStatus;
	private DefaultComboBoxModel cmbStatusModel;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	private _JLookup lookupCode;
	private JPanel pnlNorth;
	private ButtonGroup grpButton = new ButtonGroup();
	private JLabel lblPerDivision;
	private JCheckBox chkPerDivision;
	private _JLookup lookupAcctID;
	
	
	private String purpose_code;
	private String purpose_desc;
	private String purpose_alias;
	private Boolean per_division;
	private String acct_id;
	private String acertxt;
	private Boolean grp_heads;
	private Boolean div_heads;
	private String status;
	private JPanel pnlCenter;
	public Trip_Purpose() {
		super(title, false, true, false, true);
		initGUI();
		formLoad();
	}

	public Trip_Purpose(String title) {
		super(title);
		initGUI();
		formLoad();
	}

	public Trip_Purpose(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		formLoad();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		getContentPane().setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			this.add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setLayout(new BorderLayout(3,3));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(390, 150));
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Purpose is allowed to"));
				pnlNorth.setLayout(new BorderLayout(3,3)); 
				{

					JPanel pnlWest = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlWest, BorderLayout.WEST);
					pnlWest.setPreferredSize(new Dimension(400, 0));

					{

						JPanel pnlLabel = new JPanel(new GridLayout(5,0, 2, 2));
						pnlWest.add(pnlLabel, BorderLayout.WEST);
						pnlLabel.setPreferredSize(new Dimension(100, 0));
						{


							lblCode = new JLabel("Code");
							pnlLabel.add(lblCode);
							lblCode.setPreferredSize(new Dimension(100, 25));

							lblDesc = new JLabel("Description");
							pnlLabel.add(lblDesc);
							lblDesc.setPreferredSize(new Dimension(100, 25));

							lblAlias = new JLabel("Alias");
							pnlLabel.add(lblAlias);
							lblAlias.setPreferredSize(new Dimension(100, 25));

							lblAccount = new JLabel("Account ID");
							pnlLabel.add(lblAccount);
							lblAccount.setPreferredSize(new Dimension(100, 25));

							lblAcerText = new JLabel("ACETText Code");
							pnlLabel.add(lblAcerText);
							lblAcerText.setPreferredSize(new Dimension(100, 25));

						}

						JPanel pnlAction = new JPanel(new GridLayout(5, 0, 2, 2));
						pnlWest.add(pnlAction, BorderLayout.CENTER);
						pnlAction.setPreferredSize(new Dimension(100, 0));

						{
							{
								JPanel pnlLookup = new JPanel(new BorderLayout(3,3));
								pnlAction.add(pnlLookup,BorderLayout.CENTER);
								pnlLookup.setPreferredSize(new Dimension(74, 25));
								{

									lookupCode = new _JLookup("", "Code", 0) ; /// XXX lookupCode 
									pnlLookup.add(lookupCode,BorderLayout.WEST);
									lookupCode.setLookupSQL("select purpose_code AS \"Purpose Code\", purpose_desc AS \"Desc\", purpose_alias AS \"Alias\", per_division as \"Per Div.\",acct_id AS \"Acct. ID\",acertxt_code AS \"AcerTxt Code\",allow_group_heads AS \"Grp. Heads\",allow_div_heads AS \"Div. Heads\",status_id AS \"Status\" from mf_tripping_purpose where status_id = 'A' order by purpose_code");
									lookupCode.setPreferredSize(new Dimension(100, 25));
									lookupCode.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent e) {
											

												Object[] data = ((_JLookup)e.getSource()).getDataSet();

												if(data != null){
													purpose_code = (String)data[0];
													purpose_desc = (String)data[1];
													purpose_alias = (String)data[2];
													per_division = (Boolean)data[3];
													acct_id = (String)data[4];
													acertxt = (String)data[5];
													grp_heads = (Boolean)data[6];
													div_heads = (Boolean)data[7];
													status = (String)data[8];
													
													
													lookupCode.setValue(purpose_code);
													txtDesc.setText(purpose_desc);
													txtAlias.setText(purpose_alias);
													
													txtAcetxt.setText(acertxt);
													pgSelect db = new pgSelect();
													
													db.select("SELECT acct_name FROM mf_boi_chart_of_accounts where acct_id = '"+acct_id+"'");
													
													txtAcctID.setText((String) db.Result[0][0]);
													lookupAcctID.setValue(acct_id);
													
													chkGrpHeads.setSelected(grp_heads);
													chkDivHeads.setSelected(div_heads);
													
													cmbStatus.setSelectedIndex(status.equals("A") ? 0 : 1);
													
													chkPerDivision.setSelected(per_division);
												}
										}
									});
								}
								{
									txtCode = new JTextField();
									pnlLookup.add(txtCode,BorderLayout.CENTER);
									txtCode.setEditable(false);
									txtCode.setVisible(false);
									txtCode.setPreferredSize(new Dimension(100, 25));
								} 
							}
						}
						{
							txtDesc = new JTextField();
							pnlAction.add(txtDesc,BorderLayout.CENTER);
							txtDesc.setPreferredSize(new Dimension(100, 25));

						}
						{
							txtAlias = new JTextField();
							pnlAction.add(txtAlias,BorderLayout.CENTER);
							txtAlias.setPreferredSize(new Dimension(100, 25));

						}
						{
							JPanel pnlLookup = new JPanel(new BorderLayout(3,3));
							pnlAction.add(pnlLookup,BorderLayout.CENTER);
							pnlLookup.setPreferredSize(new Dimension(74, 25));
							{

								lookupAcctID = new _JLookup("", "Code", 0) ; /// XXX lookupTripRate 
								pnlLookup.add(lookupAcctID,BorderLayout.WEST);
								lookupAcctID.setLookupSQL("select acct_id,acct_name from mf_boi_chart_of_accounts where acct_id ~* '08-03'");
								lookupAcctID.setPreferredSize(new Dimension(100, 25));
								lookupAcctID.addLookupListener(new LookupListener() {

									private String acct_name;
									private String acct_id;

									@Override
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup)e.getSource()).getDataSet();

										if(data != null){

											acct_id  = data[0].toString();
											acct_name = data[1].toString();

											lookupAcctID.setValue(acct_id);
											txtAcctID.setText(acct_name);
										}
									}
								});

							}
							{
								txtAcctID = new JTextField();
								pnlLookup.add(txtAcctID,BorderLayout.CENTER);
								txtAcctID.setEditable(false);
								txtAcctID.setPreferredSize(new Dimension(100, 25));
							} 
						}
						{
							txtAcetxt = new JTextField();
							pnlAction.add(txtAcetxt,BorderLayout.CENTER);
							txtAcetxt.setPreferredSize(new Dimension(100, 25));

						}


					}
				}

				pnlCenter = new JPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new Dimension(pnlMain.getWidth(), 0));
				pnlCenter.setLayout(new BorderLayout(3,3));

				{
					JPanel pnlN = new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlN, BorderLayout.NORTH);
					pnlN.setPreferredSize(new Dimension(400, 80));

					JPanel pnlWest = new JPanel(new BorderLayout(5, 5));
					pnlN.add(pnlWest, BorderLayout.WEST);
					pnlWest.setPreferredSize(new Dimension(400, 0));
					{
						JPanel pnlLabel = new JPanel(new GridLayout(1,1, 2, 2));
						pnlWest.add(pnlLabel, BorderLayout.WEST);
						pnlLabel.setPreferredSize(new Dimension(100, 0));
						{

							lblBlank = new JLabel("");
							pnlLabel.add(lblBlank);
							lblBlank.setPreferredSize(new Dimension(152, 25));
						}

					}
					JPanel pnlActionS = new JPanel(new GridLayout(1, 1, 2, 2));
					pnlWest.add(pnlActionS, BorderLayout.CENTER);
					pnlActionS.setPreferredSize(new Dimension(100,0 ));
					{
						JPanel pnlHeads = new JPanel(new BorderLayout(5, 5));
						pnlActionS.add(pnlHeads);
						pnlHeads.setBorder(components.JTBorderFactory.createTitleBorder("Purpose is allowed to"));
						{
							chkGrpHeads = new JCheckBox("Group Heads");
							pnlHeads.add(chkGrpHeads,BorderLayout.NORTH);

							chkDivHeads = new JCheckBox("Div Heads");
							pnlHeads.add(chkDivHeads,BorderLayout.CENTER);
						}

					}
					{
						JPanel pnlC = new JPanel(new BorderLayout(5, 5));
						pnlCenter.add(pnlC, BorderLayout.CENTER);
						pnlC.setPreferredSize(new Dimension(400, 25));
						{
							JPanel pnlWest_C = new JPanel(new BorderLayout(5, 5));
							pnlC.add(pnlWest_C, BorderLayout.WEST);
							pnlWest_C.setPreferredSize(new Dimension(400, 0));

							JPanel pnlLabel = new JPanel(new GridLayout(2,1, 2, 2));
							pnlWest_C.add(pnlLabel, BorderLayout.WEST);
							pnlLabel.setPreferredSize(new Dimension(100, 0));
							{

								lblStatus = new JLabel("Status");
								pnlLabel.add(lblStatus);
								lblStatus.setPreferredSize(new Dimension(152, 25));
							} 
							{

								lblPerDivision = new JLabel("Per Division");
								pnlLabel.add(lblPerDivision);
								lblPerDivision.setPreferredSize(new Dimension(152, 25));
							}

							JPanel pnlAction = new JPanel(new GridLayout(2, 1, 2, 2));
							pnlWest_C.add(pnlAction, BorderLayout.CENTER);
							pnlAction.setPreferredSize(new Dimension(100,0 ));
							{

								cmbStatusModel = new DefaultComboBoxModel(new String[] { "Active", "Inactive"});
								cmbStatus = new JComboBox();
								pnlAction.add(cmbStatus);
								cmbStatus.setModel(cmbStatusModel);
								cmbStatus.setSelectedItem(null);
								cmbStatus.addActionListener(this);
							}
							{
								chkPerDivision = new JCheckBox("");
								pnlAction.add(chkPerDivision);
							}


						}
					}

				}
				{
					JPanel pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(pnlMain.getWidth(), 40));
					pnlSouth.setLayout(new BorderLayout(3,3)); 
					{
						JPanel pnlButton = new JPanel(new GridLayout(1, 4, 3, 3));
						pnlSouth.add(pnlButton, BorderLayout.EAST);
						pnlButton.setPreferredSize(new Dimension(300, 25));

						{

							btnNew = new JButton("New");
							pnlButton.add(btnNew);
							btnNew.addActionListener(this);
							btnNew.setActionCommand("New");
							grpButton.add(btnNew);

							btnEdit = new JButton("Edit");
							pnlButton.add(btnEdit);
							btnEdit.addActionListener(this);
							btnEdit.setActionCommand("Edit");
							grpButton.add(btnEdit);

							btnSave = new JButton("Save");
							pnlButton.add(btnSave);
							btnSave.addActionListener(this);

							btnCancel = new JButton("Cancel");
							pnlButton.add(btnCancel);
							btnCancel.addActionListener(this);
						}
					}
				}
			}
		}
	}

	@Override 
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnNew)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			
			Clear();
			lookupCode.setEditable(false);
			btnState(false, false, true, true);

			this.setComponentsEnabled(pnlNorth, true);
			this.setComponentsEnabled(pnlCenter, true);

			lookupCode.setValue(String.valueOf(Code()));

		}	

		if (e.getSource().equals(btnEdit)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			
			
			lookupCode.setEditable(false);
			this.setComponentsEnabled(pnlNorth, true);
			this.setComponentsEnabled(pnlCenter, true);
			btnState(false, false, true, true);
		}	

		if (e.getSource().equals(btnSave)) {

			toSave();
		}	

		if (e.getSource().equals(btnCancel)) {
			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields? ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				Clear();
				formLoad();
			}
		}	

	}



	private void btnState(Boolean sNew,Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);

	}

	private void formLoad(){

		this.setComponentsEnabled(pnlNorth, false);
		this.setComponentsEnabled(pnlCenter, false);
		
		lookupCode.setEnabled(true);
		lookupCode.setEditable(true);
		lblCode.setEnabled(true);
		btnState(true, true, false, false);
	}


	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;

		if(txtDesc.getText().isEmpty() || txtDesc.getText().equals("")){
			required_fields = required_fields + "Place \n";
			toSave = false;
		}
		if(txtAlias.getText().isEmpty() || txtAlias.getText().equals("")){
			required_fields = required_fields + "Alias \n";
			toSave = false;
		}
		if(txtAcetxt.getText().isEmpty() || txtAcetxt.getText().equals("")){
			required_fields = required_fields + "ACERText Code \n";
			toSave = false;
		}

		if(txtAlias.getText().isEmpty() || txtAlias.getText().equals("")){
			required_fields = required_fields + "Alias \n";
			toSave = false;
		}

		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private Boolean getAcertxt(String acertxt_code,String mp_code){

		pgSelect db = new pgSelect();
		String SQL ="";
		
		if (grpButton.getSelection().getActionCommand().equals("New")) {

			SQL = "SELECT * FROM mf_tripping_meeting_place WHERE mp_acertxt_code = '"+acertxt_code+"' AND status_id = 'A'";
			
		}else{
			
			SQL = "SELECT * FROM mf_tripping_meeting_place WHERE mp_acertxt_code = '"+acertxt_code+"' AND mp_code = '"+mp_code+"' AND status_id = 'A'";
			
		}
		
		db.select(SQL);

		if (db.isNotNull()) {
			
			JOptionPane.showMessageDialog(null, "ACERtxt code already exists.","Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;

	}
	private void toSave(){

		String SQL ="";

		String status = cmbStatus.getSelectedIndex() == 0 ? "A" : "I";

		if (checkRequiredFields()) {
			if (getAcertxt(txtAcetxt.getText(),lookupCode.getValue())) {
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					if (grpButton.getSelection().getActionCommand().equals("New")) {

						SQL = "INSERT INTO mf_tripping_purpose(\n" + 
								"            purpose_code, \n" + 
								"            purpose_desc, \n" + 
								"            purpose_alias, \n" + 
								"            per_division, \n" + 
								"            acct_id, \n" + 
								"            acertxt_code, \n" + 
								"            allow_group_heads, \n" + 
								"            allow_div_heads, \n" + 
								"            status_id)\n" + 
								"    VALUES ('"+lookupCode.getValue()+"',---purpose_code, \n" + 
								"            '"+txtDesc.getText().toUpperCase()+"',---purpose_desc, \n" + 
								"            '"+txtAlias.getText().toUpperCase()+"',---purpose_alias, \n" + 
								"            "+chkPerDivision.isSelected()+",---per_division, \n" + 
								"            '"+lookupAcctID.getValue()+"',---acct_id, \n" + 
								"            '"+txtAcetxt.getText().toUpperCase()+"',---acertxt_code, \n" + 
								"            "+chkGrpHeads.isSelected()+",---allow_group_heads, \n" + 
								"            "+chkDivHeads.isSelected()+",---allow_div_heads, \n" + 
								"            '"+status+"'---status_id\n" + 
								"            );\n" + 
								"";
					}else{

						SQL = "UPDATE mf_tripping_purpose\n" + 
								"   SET purpose_desc = '"+txtDesc.getText()+"', \n" + 
								"       purpose_alias = '"+txtAlias.getText().toUpperCase()+"', \n" + 
								"       per_division = "+chkPerDivision.isSelected()+", \n" +
								"       acct_id = '"+lookupAcctID.getValue()+"', \n" + 
								"       acertxt_code = '"+txtAcetxt.getText().toUpperCase()+"', \n" +
								"       allow_group_heads = "+chkGrpHeads.isSelected()+", \n" + 
								"       allow_div_heads = "+chkDivHeads.isSelected()+", \n" + 
								"       status_id =  '"+status+"'\n" + 
								"       \n" + 
								"WHERE purpose_code = '"+lookupCode.getValue()+"' \n" + 
								"and status_id = 'A';\n" + 
								"";

					}

					pgUpdate dbU = new pgUpdate();
					dbU.executeUpdate(SQL, true);
					dbU.commit();

					JOptionPane.showMessageDialog( getContentPane(), "Saved successfully ", "Successfull", JOptionPane.INFORMATION_MESSAGE );
					formLoad();
					Clear();
				}	

			}

		}





	}

	private void Clear(){

		lookupCode.setValue("");
		txtDesc.setText("");
		txtAlias.setText("");
		txtAcetxt.setText("");
		lookupAcctID.setValue("");
		txtAcctID.setText("");
		chkDivHeads.setSelected(false);
		chkGrpHeads.setSelected(false);
		chkPerDivision.setSelected(false);
		cmbStatus.setSelectedIndex(0);

		btnState(true, true, false, false);

	}

	private String Code(){

		pgSelect db = new pgSelect();

		db.select("SELECT (COALESCE(MAX(purpose_code)::int,0) + 1)::TEXT FROM mf_tripping_purpose");

		return (String) db.Result[0][0];

	}
}


