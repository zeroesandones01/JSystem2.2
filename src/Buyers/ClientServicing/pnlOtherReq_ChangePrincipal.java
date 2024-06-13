/**
 * 
 */
package Buyers.ClientServicing;
import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.math.BigDecimal;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import components._JXTextField;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

/**
 * @author John Lester Fatallo
 */

public class pnlOtherReq_ChangePrincipal extends JPanel implements _GUI{

	private static final long serialVersionUID = 3261275931023400502L;

	private JPanel pnlMain;

	private JPanel pnlWest;
	private JLabel lblClient;
	private JLabel lblPurpose;

	private JPanel pnlEast;
	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;
	private JPanel pnlPurpose;
	private JComboBox cmbPurpose;

	private OtherRequest2 or;
	private pnlOtherRequest_OldData od;

	public pnlOtherReq_ChangePrincipal(pnlOtherRequest_OldData od) {
		this.od=od;
		initGUI();
	}

	public pnlOtherReq_ChangePrincipal(LayoutManager layout) {
		super(layout);
	}

	public pnlOtherReq_ChangePrincipal(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ChangePrincipal(LayoutManager layout,
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
					lblClient = new JLabel("*Client");
					pnlWest.add(lblClient);
				}
				{
					lblPurpose = new JLabel("Purpose");
					pnlWest.add(lblPurpose);
				}
			}
			{
				pnlEast = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlMain.add(pnlEast, BorderLayout.CENTER);
				{
					pnlClient = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlClient);
					{
						lookupClient = new _JLookup(null, "Select Client", 0);
						pnlClient.add(lookupClient, BorderLayout.WEST);
						lookupClient.setLookupSQL(_OtherRequest2.sqlChangePrincipalApp(od.getDataOld()));
						lookupClient.setFilterName(true);
						lookupClient.setPreferredSize(new Dimension(100, 0));
						
						lookupClient.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!= null){
									txtClient.setText((String) data[1]);
								}
							}
						});
					}
					{
						txtClient = new _JXTextField("Client Name");
						pnlClient.add(txtClient);
					}
				}
				{
					pnlPurpose = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlPurpose);
					{
						cmbPurpose = new JComboBox(new String[] {"Change to Spouse", "Transfer of Rights"});
						pnlPurpose.add(cmbPurpose, BorderLayout.WEST);
						cmbPurpose.setPreferredSize(new Dimension(150, 0));//XXX CHECK IF NULL VALUE HERE AT FIRST
					}
				}
			}
		}
	}//END OF INIT GUI
	
	public Object[] getDataChangePrincipal(){//RETURNS THE VALUE FROM THE COMPONENTS IN THIS PANEL
		return new Object[]{"05", //req_id 0
							lookupClient.getValue(), //new_entity_id 1
							null, //new_entity_lname 2
							null, //new entity_fname 3
							null, //new_entity_mname 4
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
							"",
							cmbPurpose.getSelectedItem()}; //new civil status

	}

	public boolean toSave(){
		if(lookupClient.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Client"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean checkRequiredFields(){
		String required_fields = "";
		Boolean toSave = true;
		
		if(lookupClient.getValue() == null || lookupClient.getValue().isEmpty()){
			required_fields = required_fields + "Client";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void editChangePrincipal(){
		lookupClient.setEditable(true);
		cmbPurpose.setEnabled(true);
	}
	
	public void displayChangePrincipal(String new_entity_id, String new_entity_name, String purpose){
		
		lookupClient.setValue(new_entity_id);
		lookupClient.setEditable(false);
		txtClient.setText(new_entity_name);
		txtClient.setEditable(false);
		cmbPurpose.setSelectedItem(purpose);
		cmbPurpose.setEnabled(false);
		
	}
}
