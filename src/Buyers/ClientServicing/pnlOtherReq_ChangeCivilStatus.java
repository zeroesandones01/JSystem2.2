/**
 * 
 */
package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.math.BigDecimal;

import interfaces._GUI;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Database.pgSelect;
import Functions.FncSystem;
import components._JInternalFrame;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_ChangeCivilStatus extends JPanel implements _GUI {

	private static final long serialVersionUID = -2786637176171276092L;

	private JPanel pnlMain;

	private JPanel pnlWest;
	private JLabel lblCivilStatus;
	private JLabel lblClient;
	private JLabel lblOldCivilStatus;

	private JPanel pnlCenter;
	private JPanel pnlCivilStatus;
	private JComboBox cmbCivilStatus;
	private _JXTextField txtFName;
	private _JXTextField txtMName;
	private _JXTextField txtLName;
	private JTextField txtFirstName;
	private JTextField txtMiddleName;
	private JTextField txtLastName;
	private JLabel lblFirstName;
	private JLabel lblMiddleName;
	private JLabel lblLastName;
	
	private JPanel pnlOldName;
	private _JXTextField txtOldFName;
	private _JXTextField txtOldMName;
	private _JXTextField txtOldLName;
	
	private JPanel pnlName;
	private JPanel pnlSeparator;
	private JLabel lblSeparator;
	
	private JPanel pnlSeparator2;
	private JLabel lblOldFirstName;
	private JLabel lblOldMiddleName;
	private JLabel lblOldLastName;

	private pnlOtherRequest_OldData od;
	private OtherRequest2 or;

	public pnlOtherReq_ChangeCivilStatus(pnlOtherRequest_OldData od) {
		this.od = od;
		initGUI();
	}

	public pnlOtherReq_ChangeCivilStatus(LayoutManager layout) {
		super(layout);
	}

	public pnlOtherReq_ChangeCivilStatus(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ChangeCivilStatus(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}


	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(200, 200));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.NORTH);
			//pnlMain.setPreferredSize(new Dimension(150, 150));
			{
				pnlWest = new JPanel(new GridLayout(5, 1, 3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblCivilStatus = new JLabel("Civil Status", JLabel.TRAILING);
					pnlWest.add(lblCivilStatus);
				}
				{
					lblClient = new JLabel("Client", JLabel.TRAILING);
					pnlWest.add(lblClient);
				}
				{
					lblSeparator = new JLabel();
					pnlWest.add(lblSeparator);
				}
				{
					lblSeparator = new JLabel();
					pnlWest.add(lblSeparator);
				}
				{
					lblSeparator = new JLabel();
					pnlWest.add(lblSeparator);
				}
			}
			{
				pnlCenter = new JPanel(new GridLayout(5, 1, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlCivilStatus = new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlCivilStatus);
					{
						cmbCivilStatus = new JComboBox(_JInternalFrame.CIVIL_STATUS().values().toArray());
						pnlCivilStatus.add(cmbCivilStatus, BorderLayout.WEST);
						cmbCivilStatus.setPreferredSize(new Dimension(150, 20));
					}
				}
				{
					pnlOldName = new JPanel(new GridLayout(1, 3, 3, 3));
					pnlCenter.add(pnlOldName);
					{
						txtOldFName = new _JXTextField();
						pnlOldName.add(txtOldFName);
					}
					{
						txtOldMName = new _JXTextField();
						pnlOldName.add(txtOldMName);
					}
					{
						txtOldLName = new _JXTextField();
						pnlOldName.add(txtOldLName);
					}
				}
				{
					pnlSeparator2 = new JPanel(new GridLayout(1, 3, 3, 3));
					pnlCenter.add(pnlSeparator2);
					{
						lblOldFirstName = new JLabel("Old First Name");
						pnlSeparator2.add(lblOldFirstName);
					}
					{
						lblOldMiddleName = new JLabel("Old Middle name");
						pnlSeparator2.add(lblOldMiddleName);
					}
					{
						lblOldLastName = new JLabel("Old Last Name");
						pnlSeparator2.add(lblOldLastName);
					}
				}
				{
					pnlName = new JPanel(new GridLayout(1, 3, 3, 3));
					pnlCenter.add(pnlName);
					{
						txtFirstName = new JTextField();
						pnlName.add(txtFirstName);
					}
					{
						txtMiddleName = new JTextField();
						pnlName.add(txtMiddleName);
					}
					{
						txtLastName = new JTextField();
						pnlName.add(txtLastName);
					}
				}
				{
					pnlSeparator = new JPanel(new GridLayout(1, 3, 3, 3));
					pnlCenter.add(pnlSeparator);
					{
						lblFirstName = new JLabel("New First Name");
						pnlSeparator.add(lblFirstName);
					}
					{
						lblMiddleName = new JLabel("New Middle Name");
						pnlSeparator.add(lblMiddleName);
					}
					{
						lblLastName = new JLabel("New Last Name");
						pnlSeparator.add(lblLastName);
					}
				}
			}
		}
	}//XXX END OF INIT GUI
	
	public String getGender(String entity_id){//CHECKS THE GENDER OF THE CLIENT
		pgSelect db = new pgSelect();
		
		String sql = "select gender from rf_entity where entity_id = '"+entity_id+"'";
		db.select(sql);
		String gender = (String) db.getResult()[0][0];
		
		return gender;
	}

	public void setValuesName(){
		Object [] data1 = od.getDataOld();
		String old_entity_id = (String) data1[0];
		String sql = "select trim(a.first_name), trim(a.last_name), trim(a.middle_name), trim(b.civil_status_desc)\n" + 
					 "from rf_entity a\n" + 
					 "left join mf_civil_status b on b.civil_status_code = a.civil_status_code\n" + 
					 "where a.entity_id = '"+old_entity_id+"';\n";
		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display Client Full Name", sql);
		if(db.isNotNull()){
			String first_name = (String) db.getResult()[0][0];
			String last_name = (String) db.getResult()[0][1];
			String middle_name = (String) db.getResult()[0][2];
			String civil_status = (String) db.getResult()[0][3];
			//String gender = (String) db.getResult()[0][3];

			System.out.printf("Display middle name: %s%n", middle_name);
			System.out.printf("Display last name: %s%n", last_name);
			System.out.printf("Display first name: %s%n", first_name);
			
			cmbCivilStatus.setSelectedItem(civil_status);
			
			if(getGender(old_entity_id).equals("F")){//DISPLAYS THE FIRST NAME, MIDDLE NAME AND LAST NAME IF CLIENT IS FEMALE
				txtOldFName.setText(first_name);
				txtOldMName.setText(middle_name);
				txtOldLName.setText(last_name);
			}else{
				txtOldFName.setText(first_name);
				txtOldMName.setText(middle_name);
				txtOldLName.setText(last_name);
				
				txtFirstName.setText(first_name);
				txtMiddleName.setText(middle_name);
				txtLastName.setText(last_name);
				txtFirstName.setEditable(false);
				txtMiddleName.setEditable(false);
				txtLastName.setEditable(false);
			}
		}
		
	}

	public void editChangeCivilStatus(){//EDITING OF THE CHANGE OF CIVIL STATUS
		Object [] data1 = od.getDataOld();
		String old_entity_id = (String) data1[0];
		cmbCivilStatus.setEnabled(true);
		
		if(getGender(old_entity_id).equals("F")){
			txtFirstName.setEditable(true);
			txtLastName.setEditable(true);
			txtMiddleName.setEditable(true);
		}
	}

	public void displayChangeCivilStatus(String new_civil_status, String new_fname, String new_mname, String new_lname){
		System.out.printf("Display civil status code %s", new_civil_status);
		
		cmbCivilStatus.setSelectedItem(new_civil_status);
		cmbCivilStatus.setEnabled(false);
		txtFirstName.setText(new_fname);
		txtMiddleName.setText(new_mname);
		txtLastName.setText(new_lname);
		txtFirstName.setEditable(false);
		txtMiddleName.setEditable(false);
		txtLastName.setEditable(false);
		
	}

	public Object[] getDataChangeCivilStatus(){//XXX ADD DATA HERE FOR THE NEW CIVIL STATUS AND FOR THE SETTING OF THE NEW VARIABLES
		return new Object[]{"25", //req_id 0
				null, //new_entity_id 1
				txtLastName.getText().toUpperCase(), //new_entity_lname 2
				txtFirstName.getText().toUpperCase(), //new entity_fname 3
				txtMiddleName.getText().toUpperCase(), //new_entity_mname 4
				null, //new unit_id 5
				null, //new_house_model 6
				null, //new_agent_id 7
				null,  //new_buyer_type 8
				null, //new_pmt_scheme 9
				new BigDecimal("0.00"), //new_sprice 10
				new BigDecimal("0.00"), //new_disc_amt 11
				new BigDecimal("0.00"), //new_disc_rate 12
				new BigDecimal("0.00"), //13 new_net_sell_price
				new BigDecimal("0.00"),//new dp_amt 14
				new BigDecimal("0.00"), //new_dp_rate 15
				"", //null, //new_dp_terms //16
				new BigDecimal("0.00"), //17 new_availed_amt
				new BigDecimal("0.00"), //18 new_availed_rate
				"", //null, //new ma_terms //19 
				null, //new_dp_due_date 20
				null, //new_ma_due_date 21
				"", //22 new_lot_area
				new BigDecimal("0.00"), //23 new_fire_amt
				new BigDecimal("0.00"), //24 new_int_rate
				new BigDecimal("0.00"), //25 new_vat_amt
				new BigDecimal("0.00"), //new vat rate
				"", //27 new schedate
				_JInternalFrame.GET_CIVIL_STATUS_CODE((String) cmbCivilStatus.getSelectedItem()),//28 new civil status
				""};
	}
}
