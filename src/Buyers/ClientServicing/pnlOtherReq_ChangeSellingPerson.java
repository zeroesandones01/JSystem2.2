
package Buyers.ClientServicing;
import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import components._JXTextField;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

/**
 * @author John Lester Fatallo
 */

public class pnlOtherReq_ChangeSellingPerson extends JPanel implements _GUI{

	private static final long serialVersionUID = 3261275931023400502L;

	private JPanel pnlMain;

	private JPanel pnlWest;
	private JLabel lblAgent;

	private JPanel pnlEast;
	private JPanel pnlAgent;
	private _JLookup lookupAgent;
	private _JXTextField txtAgent;

	private OtherRequest2 or;
	private pnlOtherRequest_OldData od;

	public pnlOtherReq_ChangeSellingPerson(pnlOtherRequest_OldData od) {
		this.od=od;
		initGUI();
	}

	public pnlOtherReq_ChangeSellingPerson(LayoutManager layout) {
		super(layout);
	}

	public pnlOtherReq_ChangeSellingPerson(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ChangeSellingPerson(LayoutManager layout,
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
				pnlWest = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblAgent = new JLabel("*Selling Agent");
					pnlWest.add(lblAgent);
				}
			}
			{
				pnlEast = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlEast, BorderLayout.CENTER);
				{
					pnlAgent = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlAgent);
					{
						lookupAgent = new _JLookup(null, "Select Agent", 0);
						pnlAgent.add(lookupAgent, BorderLayout.WEST);
						lookupAgent.setPrompt("Agent ID");
						lookupAgent.setFilterName(true);
						lookupAgent.setLookupSQL(_OtherRequest2.sqlChangeAgent("13",od.getDataOld()));//XXX CHECK SQL HERE
						lookupAgent.setPreferredSize(new Dimension(100, 0));
						lookupAgent.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!= null){
									FncSystem.out("Display sql agent", lookupAgent.getLookupSQL());
									txtAgent.setText((String) data[1]);
								}
							}
						});
					}
					{
						txtAgent = new _JXTextField("Agent Name");
						pnlAgent.add(txtAgent);
					}
				}
			}
		}

	}//END OF INIT GUI
	
	public Object[] getDataChangeSellingPerson(){
		return new Object[]{"13", //req_id 0
							null, //new_entity_id 1
							null, //new_entity_lname 2
							null, //new entity_fname 3
							null, //new_entity_mname 4
							null, //new unit_id 5
							null, //new_house_model 6
							lookupAgent.getValue(), //new_agent_id 7
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
							"", //new sched date
							"", //new civilstatus
							""}; 

	}

	public boolean toSave(){
		if(lookupAgent.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Client"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELDS BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;
		
		if(lookupAgent.getValue() == null || lookupAgent.getValue().isEmpty()){
			required_fields = required_fields + "Selling Agent";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void editChangeSellingPerson(){//FOR EDITING OF THIS PANEL
		lookupAgent.setEditable(true);
	}
	
	public void displayChangeSellingPerson(String new_entity_id, String new_entity_name){//DISPLAYS THE DATA IN THIS PANEL WHEN THE REQUEST IS SEARCHED
		lookupAgent.setValue(new_entity_id);
		lookupAgent.setEditable(false);
		txtAgent.setText(new_entity_name);
		txtAgent.setEditable(false);
	}
}
