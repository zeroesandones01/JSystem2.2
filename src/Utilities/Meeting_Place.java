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

public class Meeting_Place extends _JInternalFrame implements _GUI,ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Meeting Place";
	public static Dimension frameSize = new Dimension(430,280);
	public Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private JLabel lblCode;
	private JLabel lblPlace;
	private JLabel lblAlias;
	private JLabel lblAcerText;
	private JLabel lblTripRate;
	private JLabel lblStatus;
	private _JLookup lookupCode;
	private JTextField txtCode;
	private JTextField txtPlace;
	private JTextField txtAlias;
	private JTextField txtAcetxt;
	private _JLookup lookupTripRate;
	private JTextField txtTripRate;
	private JComboBox cmbStatus;
	private DefaultComboBoxModel cmbStatusModel;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	private JPanel pnlNorth;
	private ButtonGroup grpButton = new ButtonGroup();

	public Meeting_Place() {
		super(title, false, true, false, true);
		initGUI();
		formLoad();
	}
	public Meeting_Place(String title) {
		super(title);
		initGUI();
		formLoad();
	}

	public Meeting_Place(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
				pnlNorth.setPreferredSize(new Dimension(390, 200));
				pnlNorth.setLayout(new BorderLayout(3,3));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder(""));

				{
					JPanel pnlWest = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlWest, BorderLayout.WEST);
					pnlWest.setPreferredSize(new Dimension(400, 0));

					JPanel pnlLabel = new JPanel(new GridLayout(6,0, 2, 2));
					pnlWest.add(pnlLabel, BorderLayout.WEST);
					pnlLabel.setPreferredSize(new Dimension(100, 0));
					{


						lblCode = new JLabel("Code");
						pnlLabel.add(lblCode);
						lblCode.setPreferredSize(new Dimension(100, 25));

						lblPlace = new JLabel("Place");
						pnlLabel.add(lblPlace);
						lblPlace.setPreferredSize(new Dimension(100, 25));

						lblAlias = new JLabel("Alias");
						pnlLabel.add(lblAlias);
						lblAlias.setPreferredSize(new Dimension(100, 25));

						lblAcerText = new JLabel("ACERText Code");
						pnlLabel.add(lblAcerText);
						lblAcerText.setPreferredSize(new Dimension(100, 25));

						lblTripRate = new JLabel("Tripping Rate");
						pnlLabel.add(lblTripRate);
						lblTripRate.setPreferredSize(new Dimension(100, 25));

						lblStatus= new JLabel("Status");
						pnlLabel.add(lblStatus);
						lblStatus.setPreferredSize(new Dimension(100, 25));

					}

					JPanel pnlAction = new JPanel(new GridLayout(6, 0, 2, 2));
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
								lookupCode.setLookupSQL("SELECT  mp_code as \"CODE\",  mp_desc As \"Desc\",  mp_alias As \"Alias\",mp_acertxt_code as \"AcerTxt Code\", tripping_rate_code as \"Rate Code\" ,status_id  as \"Status\" From mf_tripping_meeting_place  order by mp_code");
								lookupCode.setPreferredSize(new Dimension(100, 25));
								lookupCode.addLookupListener(new LookupListener() {
									
									private String mp_code;
									private String mp_desc;
									private String mp_alias;
									private String mp_acer_code;
									private String mp_status;
									private String mp_rate_code;

									@Override
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup)e.getSource()).getDataSet();

										if(data != null){

											mp_code = data[0].toString();
											mp_desc = data[1].toString();
											mp_alias = data[2].toString();
											mp_acer_code = data[3] == null ? "" : data[3].toString();
											mp_rate_code = data[4].toString();
											mp_status = data[5].toString(); 
											
											lookupCode.setValue(mp_code);
											txtPlace.setText(mp_desc);
											txtAlias.setText(mp_alias);
											txtAcetxt.setText(mp_acer_code);
											
											pgSelect db = new pgSelect();
											db.select("SELECT standard_rate::text FROM mf_tripping_rate WHERE rate_code = '"+mp_rate_code+"'");
											
											lookupTripRate.setValue(mp_rate_code);
											txtTripRate.setText((String) db.Result[0][0]);
											
											cmbStatus.setSelectedIndex(mp_status.equals("A") ? 0 : 1);
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
						{
							txtPlace = new JTextField();
							pnlAction.add(txtPlace,BorderLayout.CENTER);
							txtPlace.setPreferredSize(new Dimension(100, 25));

						}
						{
							txtAlias = new JTextField();
							pnlAction.add(txtAlias,BorderLayout.CENTER);
							txtAlias.setPreferredSize(new Dimension(100, 25));

						}
						{
							txtAcetxt = new JTextField();
							pnlAction.add(txtAcetxt,BorderLayout.CENTER);
							txtAcetxt.setPreferredSize(new Dimension(100, 25));

						}
						{
							JPanel pnlLookup = new JPanel(new BorderLayout(3,3));
							pnlAction.add(pnlLookup,BorderLayout.CENTER);
							pnlLookup.setPreferredSize(new Dimension(74, 25));
							{

								lookupTripRate = new _JLookup("", "Code", 0) ; /// XXX lookupTripRate 
								pnlLookup.add(lookupTripRate,BorderLayout.WEST);
								lookupTripRate.setLookupSQL("SELECT rate_code as \"Rate Code\", standard_rate \"Standard Rate\" From mf_tripping_rate  WHERE status_id='A' order by rate_code");
								lookupTripRate.setPreferredSize(new Dimension(100, 25));
								lookupTripRate.addLookupListener(new LookupListener() {
									
									private String rate_code;
									private String standard_rate;

									@Override
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup)e.getSource()).getDataSet();

										if(data != null){

											rate_code = data[0].toString();
											standard_rate = data[1].toString();
											
											lookupTripRate.setValue(rate_code);
											txtTripRate.setText(standard_rate);
										}
									}
								});
							}
							{
								txtTripRate = new JTextField();
								pnlLookup.add(txtTripRate,BorderLayout.CENTER);
								txtTripRate.setEditable(false);
								txtTripRate.setPreferredSize(new Dimension(100, 25));
							} 
						}
						{

							cmbStatusModel = new DefaultComboBoxModel(new String[] { "Active", "Inactive"});
							cmbStatus = new JComboBox();
							pnlAction.add(cmbStatus);
							cmbStatus.setModel(cmbStatusModel);
							cmbStatus.setSelectedItem(null);
							cmbStatus.addActionListener(this);
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
						grpButton.add(btnNew);

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
	@Override 
	public void actionPerformed(ActionEvent e) {




		if (e.getSource().equals(btnNew)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			Clear();
			lookupCode.setEditable(false);
			btnState(false, false, true, true);
			this.setComponentsEnabled(pnlNorth, true);

			lookupCode.setValue(String.valueOf(Code()));
			
			
			
			
		}	

		if (e.getSource().equals(btnEdit)) {

			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			lookupCode.setEditable(false);
			btnState(false, false, true, true);
			this.setComponentsEnabled(pnlNorth, true);
			
			
			
			btnState(false, false, true, true);

		}	

		if (e.getSource().equals(btnSave)) {
			String SQL ="";
			
			 String status = cmbStatus.getSelectedIndex() == 0 ? "A" : "I";
			
			if (checkRequiredFields()) {
				if (getAcertxt(txtAcetxt.getText(),lookupCode.getValue())) {
					if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						if (grpButton.getSelection().getActionCommand().equals("New")) {
							
							SQL = "INSERT INTO mf_tripping_meeting_place(\n" + 
									"            mp_code, \n" + 
									"            mp_desc, \n" + 
									"            mp_alias, \n" + 
									"            mp_acertxt_code, \n" + 
									"            tripping_rate_code, \n" + 
									"            status_id\n" + 
									"            )\n" + 
									"    VALUES ('"+lookupCode.getValue()+"',---mp_code, \n" + 
									"	    	 '"+txtPlace.getText().toUpperCase()+"',---mp_desc, \n" + 
									"            '"+txtAlias.getText().toUpperCase()+"',---mp_alias, \n" + 
									"            NULLIF('"+txtAcetxt.getText().toUpperCase()+"',''),---mp_acertxt_code, \n" + 
									"            '"+lookupTripRate.getValue()+"' ,---tripping_rate_code, \n" + 
									"            'A'---status_id\n" + 
									"            );\n" + 
									"";
						}else{
							
							
							SQL = "UPDATE mf_tripping_meeting_place\n" + 
									"   SET mp_desc = '"+txtPlace.getText().toUpperCase()+"', \n" + 
									"       mp_alias = '"+txtAlias.getText().toUpperCase()+"', \n" + 
									"       mp_acertxt_code = NULLIF('"+txtAcetxt.getText().toUpperCase()+"',''), \n" + 
									"       tripping_rate_code = '"+lookupTripRate.getValue()+"', \n" + 
									"       status_id =  '"+status+"'\n" + 
									"       \n" + 
									"WHERE mp_code = '"+lookupCode.getText()+"'\n" + 
									"and status_id = 'A';\n" + 
									"";
							
						}
						
						pgUpdate dbU = new pgUpdate();
						dbU.executeUpdate(SQL, true);
						dbU.commit();
						
						formLoad();
						Clear();
					}	
				}
			}
			
			


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
		lookupCode.setEnabled(true);
		lookupCode.setEditable(true);
		lblCode.setEnabled(true);
		btnState(true, true, false, false);
	}
	
	private Integer Code(){
		
		pgSelect db = new pgSelect();
		
		db.select("SELECT COALESCE(MAX(mp_code)::int,0) + 1 FROM mf_tripping_meeting_place");
		
		return (Integer) db.Result[0][0];
		
	}
	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;

		if(txtPlace.getText().isEmpty() || txtPlace.getText().equals("")){
			required_fields = required_fields + "Place \n";
			toSave = false;
		}
		if(txtAlias.getText().isEmpty() || txtAlias.getText().equals("")){
			required_fields = required_fields + "Alias \n";
			toSave = false;
		}
		/*if(txtAcetxt.getText().isEmpty() || txtAcetxt.getText().equals("")){
			required_fields = required_fields + "ACERText Code \n";
			toSave = false;
		}*/

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
	
	private void Clear(){
		
		lookupCode.setValue("");
		txtPlace.setText("");
		txtAlias.setText("");
		txtAcetxt.setText("");
		lookupTripRate.setValue("");
		txtTripRate.setText("");
		
		btnState(true, true, false, false);
		
	}
}
