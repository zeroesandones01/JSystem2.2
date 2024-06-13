/**
 * 
 */
package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Database.pgSelect;
import Functions.FncSystem;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_CorrectionName extends JPanel implements _GUI, ItemListener {

	private static final long serialVersionUID = 6591825152012617853L;

	private JPanel pnlMain;

	private JPanel pnlWest;
	private JLabel lblClient;
	private JLabel lblSeparator;

	private JPanel pnlEast;
	private JPanel pnlClient;
	private JTextField txtFirstName;
	private JTextField txtMiddleName;
	private JTextField txtLastName;
	private JPanel pnlSeparator;
	private JLabel lblFName;
	private JLabel lblMName;
	private JLabel lblLName;

	private OtherRequest2 or;
	private pnlOtherRequest_OldData od;

	public pnlOtherReq_CorrectionName(pnlOtherRequest_OldData od) {
		this.od=od;
		//this.or=or;
		initGUI();
	}

	public pnlOtherReq_CorrectionName(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOtherReq_CorrectionName(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_CorrectionName(LayoutManager layout,
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
			{
				pnlWest = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblClient = new JLabel("Client");
					pnlWest.add(lblClient);
				}
				{
					lblSeparator = new JLabel();
					pnlWest.add(lblSeparator);
				}
			}
			{
				pnlEast = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlMain.add(pnlEast, BorderLayout.CENTER);
				{
					pnlClient = new JPanel(new GridLayout(1, 3, 3, 3));
					pnlEast.add(pnlClient);
					{
						txtFirstName = new JTextField();
						pnlClient.add(txtFirstName);
						//txtFirstName.setPreferredSize(new Dimension(100, 0));
					}
					{
						txtMiddleName = new JTextField();
						pnlClient.add(txtMiddleName);
						//txtMiddleName.setPreferredSize(new Dimension(100, 0));
					}
					{
						txtLastName = new JTextField();
						pnlClient.add(txtLastName);
						//txtLastName.setPreferredSize(new Dimension(100, 0));
					}
				}
				{
					pnlSeparator = new JPanel(new GridLayout(1, 3, 3, 3));
					pnlEast.add(pnlSeparator);
					{
						lblFName = new JLabel("New First Name");
						pnlSeparator.add(lblFName);
					}
					{
						lblMName = new JLabel("New Middle Name");
						pnlSeparator.add(lblMName);
					}
					{
						lblLName = new JLabel("New Last Name");
						pnlSeparator.add(lblLName);
					}
				}
			}
		}
	}
	
	/*public Object[] getDataCorrectionName() {
		return new Object[]{null, //new_entity_id 0
							txtLastName.getText(), //new_entity_lname 1
							txtFirstName.getText(), //new entity_fname 2
							txtMiddleName.getText(), //new_entity_mname 3
							null, //new unit_id 4
							null, //new_house_model 5
							null, //new_agent_id 6
							null,  //new_buyer_type 7
							null, //new_pmt_scheme 8
							new BigDecimal("0.00"), //new_sprice 9
							new BigDecimal("0.00"), //new_disc_amt 10
							new BigDecimal("0.00"), //new_disc_rate 11
							new BigDecimal("0.00"), //12
							new BigDecimal("0.00"),//new dp_amt 13
							new BigDecimal("0.00"), //new_dp_rate 14
							null, //new_dp_terms //15
							new BigDecimal("0.00"), //16
							new BigDecimal("0.00"), //17
							null, //new ma_terms //18
							null, //new_dp_due_date 19
							null, //new_ma_due_date 20
							"03"}; //21
		
	}*/
	
	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD FOR THIS PANEL
		String required_fields = "";
		
		Boolean toSave = true;
		
		if(txtFirstName.getText().isEmpty()){
			required_fields = required_fields + "First Name \n";
			toSave = false;
		}
		if(txtMiddleName.getText().isEmpty()){
			required_fields = required_fields + "Middle name \n";
			toSave = false;
		}
		if(txtLastName.getText().isEmpty()){
			required_fields = required_fields + "Last Name \n";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);

		}
		return true;
	}
	
	public Object[] getDataCorrectionName(){
		return new Object[]{"03", //req_id 0
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
							new BigDecimal("0.00"), //26 new_vat_rate
							"", //new schedate
							"", //new civil status
							""}; 

	}
	
	public void setValuesCorrectionName(){
		Object [] data1 = od.getDataOld();
		String old_entity_id = (String) data1[0];
		String sql = "select trim(first_name), trim(last_name), trim(middle_name) from rf_entity where entity_id = '"+old_entity_id+"'";
		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display the sql for the client class", sql);
		if(db.isNotNull()){
			txtFirstName.setText((String) db.getResult()[0][0]);
			txtLastName.setText((String) db.getResult()[0][1]);
			txtMiddleName.setText((String) db.getResult()[0][2]);
		}
	}
	
	public void displayCorrectionName(String new_fname, String new_lname, String new_mname){
		txtFirstName.setText(new_fname);
		txtFirstName.setEditable(false);
		txtLastName.setText(new_lname);
		txtLastName.setEditable(false);
		txtMiddleName.setText(new_mname);
		txtMiddleName.setEditable(false);
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}
}
