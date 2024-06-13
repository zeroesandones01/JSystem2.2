/**
 * 
 */
package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXFormattedTextField;

import Database.pgSelect;
import components._JXTextField;
import FormattedTextField._JXFormattedTextField;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_ChangeHouseModel extends JPanel implements _GUI{
	
	private static final long serialVersionUID = -2150927359319438667L;
	
	private JPanel pnlMain;
	private JPanel pnlWest;
	private JLabel lblHouseModel;
	private JLabel lblSellingPrice;
	private JLabel lblNetSellPrice;
	private JLabel lblDPAmt;
	private JLabel lblAvailedAmt;
	private JLabel lblFireAmt;
	
	private JPanel pnlEast;
	private JPanel pnlHouseModel;
	private _JLookup lookupHouseModel;
	private _JXTextField txtHouseModel;
	
	private _JXFormattedTextField txtSellPrice;
	private _JXFormattedTextField txtNetSellingPrice;
	
	private JPanel pnlDownpayment;
	private _JXFormattedTextField txtDPAmt;
	private JLabel lblDPRate;
	private _JXFormattedTextField txtDPRate;
	
	private JPanel pnlLoanAvailed;
	private _JXFormattedTextField txtAvailedAmt;
	private JLabel lblAvailedRate;
	private _JXFormattedTextField txtAvailedRate;
	
	private JPanel pnlFireAmt;
	private _JXFormattedTextField txtFireAmt;
	
	private OtherRequest2 or;
	private pnlOtherRequest_OldData od;
	
	public pnlOtherReq_ChangeHouseModel(pnlOtherRequest_OldData od) {
		this.od=od;
		initGUI();
	}

	public pnlOtherReq_ChangeHouseModel(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOtherReq_ChangeHouseModel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ChangeHouseModel(LayoutManager layout,boolean isDoubleBuffered) {
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
				pnlWest = new JPanel(new GridLayout(6, 1, 3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblHouseModel = new JLabel("*House Model");
					pnlWest.add(lblHouseModel);
				}
				{
					lblSellingPrice = new JLabel("Selling Price");
					pnlWest.add(lblSellingPrice);
				}
				{
					lblNetSellPrice = new JLabel("Net Selling Price");
					pnlWest.add(lblNetSellPrice);
				}
				{
					lblDPAmt = new JLabel("Downpayment");
					pnlWest.add(lblDPAmt);
				}
				{
					lblAvailedAmt = new JLabel("Loan Availed");
					pnlWest.add(lblAvailedAmt);
				}
				{
					lblFireAmt = new JLabel("Fire Amount");
					pnlWest.add(lblFireAmt);
				}
			}
			{
				pnlEast = new JPanel(new GridLayout(6, 1, 3, 3));
				pnlMain.add(pnlEast);
				{
					pnlHouseModel = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlHouseModel);
					{
						lookupHouseModel = new _JLookup(null, "Select House Model", 0);
						pnlHouseModel.add(lookupHouseModel, BorderLayout.WEST);
						lookupHouseModel.setLookupSQL(_OtherRequest2.sqlHouseModel(od.getDataOld()));
						lookupHouseModel.setPreferredSize(new Dimension(100, 0));
						//lookupHouseModel.set
						lookupHouseModel.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {//XXX FINISH ME AND MAKE THE SELLING PRICE EDITABLE TO INPUT THE NEW SELLING PRICE IN THE TABLE
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!= null){
									FncSystem.out("Display SQL House Model", _OtherRequest2.sqlHouseModel(od.getDataOld()));
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeHouseModel());
									String final_unit_id =  (String) data3[5];
									String final_entity_class = (String) data3[8];
									//BigDecimal final_sprice = (BigDecimal) data3[10];
									
									txtHouseModel.setText((String) data[1]);
									txtFireAmt.setValue(_OtherRequest2.setNewFire("", final_entity_class, final_unit_id, (String) data[0]));
									
									computeNewSellingPriceOnHouseModel(data3);//TRY THIS TO COMPUTE THE NEW SELLING PRICE
									//computeAmounts();
									//to compute the new selling price subtract the old model cost from the old selling price then add the new model cost
								}
							}
						});
					}
					{
						txtHouseModel = new _JXTextField("Model Name");
						pnlHouseModel.add(txtHouseModel, BorderLayout.CENTER);
					}
				}
				{
					txtSellPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlEast.add(txtSellPrice);
					txtSellPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtSellPrice.setValue(new BigDecimal("0.00"));
					txtSellPrice.setEditable(true);
					txtSellPrice.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e){
							try{
								
								txtNetSellingPrice.setValue(txtSellPrice.getValued());
								computeAmounts();
								//computeOnDiscRate(data3);
							}catch (NumberFormatException a) {}
						}
					});
				}
				{
					txtNetSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlEast.add(txtNetSellingPrice);
					txtNetSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtNetSellingPrice.setValue(new BigDecimal("0.00"));
					txtNetSellingPrice.setEditable(false);
				}
				{
					pnlDownpayment = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlDownpayment);
					{
						txtDPAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDownpayment.add(txtDPAmt, BorderLayout.WEST);
						txtDPAmt.setPreferredSize(new Dimension(100, 0));
						txtDPAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDPAmt.setValue(new BigDecimal("0.00"));
						txtDPAmt.setEditable(false);
					}
					{
						lblDPRate = new JLabel("DP Rate", JLabel.TRAILING);
						pnlDownpayment.add(lblDPRate, BorderLayout.CENTER);
					}
					{
						txtDPRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDownpayment.add(txtDPRate, BorderLayout.EAST);
						txtDPRate.setPreferredSize(new Dimension(100, 0));
						txtDPRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDPRate.setValue(new BigDecimal("0.00"));
						txtDPRate.setEditable(false);
					}
				}
				{
					pnlLoanAvailed = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlLoanAvailed);
					{
						txtAvailedAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlLoanAvailed.add(txtAvailedAmt, BorderLayout.WEST);
						txtAvailedAmt.setPreferredSize(new Dimension(100, 0));
						txtAvailedAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtAvailedAmt.setValue(new BigDecimal("0.00"));
						txtAvailedAmt.setEditable(false);
					}
					{
						lblAvailedRate = new JLabel("Loan Availed Rate", JLabel.TRAILING);
						pnlLoanAvailed.add(lblAvailedRate, BorderLayout.CENTER);
					}
					{
						txtAvailedRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlLoanAvailed.add(txtAvailedRate, BorderLayout.EAST);
						txtAvailedRate.setPreferredSize(new Dimension(100, 0));
						txtAvailedRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtAvailedRate.setValue(new BigDecimal("0.00"));
						txtAvailedRate.setEditable(false);
					}
				}
				{
					pnlFireAmt = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlFireAmt);
					{
						txtFireAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlFireAmt.add(txtFireAmt, BorderLayout.WEST);
						txtFireAmt.setPreferredSize(new Dimension(100, 0));
						txtFireAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtFireAmt.setValue(new BigDecimal("0.00"));
						txtFireAmt.setEditable(false);
					}
				}
			}
		}
	}//XXX END OF INIT GUI
	
	public void computeNewSellingPriceOnHouseModel(Object [] data3){
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		BigDecimal old_sprice = (BigDecimal) data3[36];
		String old_model_id = (String) data3[40];
		BigDecimal old_model_cost = new BigDecimal("0.00");
		BigDecimal new_model_cost = new BigDecimal("0.00");
		BigDecimal selling_price = new BigDecimal("0.00");
		pgSelect db = new pgSelect();
		
		String sql = "select compute_selling_price('"+final_unit_id+"', '"+final_model_id+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for GSP", sql);
		
		BigDecimal gsp = (BigDecimal) db.getResult()[0][0];
		txtSellPrice.setValue(gsp);
		txtNetSellingPrice.setValue(gsp);
		
		computeAmounts();
	}
	
	public void computeDPOnAvailed(Object [] data3){
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		System.out.printf("Display fianl availed amt %s%n", final_availed_amt);
		BigDecimal dp_amt = final_net_sell_price.subtract(final_availed_amt);
		System.out.printf("Display final availed amt %s%n", dp_amt);
		txtDPAmt.setValue(dp_amt);
		computeDPAvailedRate(data3);
	}

	public void computeDPAvailedRate(Object [] data3){
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal dp_amt = (BigDecimal) txtDPAmt.getValued();

		BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/final_net_sell_price.doubleValue())*100) ;
		System.out.printf("Display dp rate %s%n", dp_rate);
		txtDPRate.setValue((dp_rate));
		BigDecimal availed_rate = BigDecimal.valueOf((100 - dp_rate.doubleValue())) ;
		System.out.printf("Display availed rate %s%n", availed_rate);
		txtAvailedRate.setValue(availed_rate);
	}
	
	public void setDefaultAmounts(Object [] data3){
		Object [] data4 = _OtherRequest2.computeDefaultAmts(data3);
		BigDecimal net_sell_price = (BigDecimal) data4[0];
		BigDecimal vat_amt = (BigDecimal) data4[1];
		BigDecimal vat_rate = (BigDecimal) data4[2];
		BigDecimal availed_amt = (BigDecimal) data4[3];
		BigDecimal dp_amt = (BigDecimal) data4[4];
		BigDecimal availed_rate = (BigDecimal) data4[6];
		BigDecimal dp_rate = (BigDecimal) data4[5];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];

		txtNetSellingPrice.setValue(net_sell_price);
		//txtAvailedAmt.setValue(availed_amt); //XXX  
		txtAvailedAmt.setValue(final_availed_amt);
		txtAvailedRate.setValue(availed_rate);
		txtDPAmt.setValue(dp_amt);
		txtDPRate.setValue(dp_rate);
		
		System.out.printf("Display net selling price %s%n", net_sell_price);
		System.out.printf("Display dp rate %s%n", dp_rate);
		System.out.printf("Display availed rate %s%n", availed_rate);
		System.out.printf("Display dp amt %s%n", dp_amt);
		System.out.printf("Display availed amt %s%n", availed_amt);
	}
	
	public boolean toSave(){//CHECKING OF THE REQUIRED FIELDS FOR THIS REQUEST
		if(lookupHouseModel.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select House Model"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			return true;
		}
	}
	
	public boolean checkRequiredFields(){
		String required_fields = "";
		Boolean toSave = true;
		
		if(lookupHouseModel.getValue() == null || lookupHouseModel.getValue().isEmpty()){
			required_fields = required_fields + "House Model";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void computeAmounts(){ //Copy To Other Modules
		Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeHouseModel());
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		String final_entity_class = (String) data3[8];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal final_nsp = (BigDecimal) data3[15];
		BigDecimal old_sprice = (BigDecimal) data3[36];
		
		System.out.printf("Display final selling price %s%n", final_sprice);
		
		pgSelect db = new pgSelect();
		if(_OtherRequest2.getGroupID(final_entity_class).equals("04")){ //XXX CHECK THE COMPUTATION FOR THE LOAN AMOUNT HERE 
			String sql = "select get_hdmf_loanable_amt('"+final_unit_id+"', '"+final_model_id+"', "+final_nsp+")";
			
			db.select(sql);
			FncSystem.out("Display Loanable amt", sql);
			BigDecimal loanable_amt = (BigDecimal) db.getResult()[0][0];
			BigDecimal dp_amt = final_sprice.subtract(loanable_amt);
			//BigDecimal dp_rate = (dp_amt.divide((BigDecimal) txtSellPrice.getValued())).multiply(new BigDecimal("100"));
			BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/final_sprice.doubleValue())*100);
			BigDecimal loan_rate = new BigDecimal("100").subtract(dp_rate);
			//dp_rate.r
			//System.out.printf("Display availea_rate %s%n", loanable_amt_rate);
			//System.out.printf("Display dp_amt %s%n", dp_amt);
			//System.out.printf("Display dp_rate %s%n", dp_rate);

			txtAvailedAmt.setValue(loanable_amt);
			txtDPAmt.setValue(dp_amt);
			txtAvailedRate.setValue(loan_rate);
			txtDPRate.setValue(dp_rate);
			
		}
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("02")){

			BigDecimal dp_amt = BigDecimal.valueOf(final_sprice.doubleValue()*.20);
			BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/final_sprice.doubleValue())*100);
			BigDecimal availed_amt = final_sprice.subtract(dp_amt);
			BigDecimal availed_rate = BigDecimal.valueOf(100-dp_rate.doubleValue());
			
			//System.out.printf("Display Downpayment %s%n", dp_amt);
			//System.out.printf("Display Availed Amt %s%n", availed_amt);

			txtDPAmt.setValue(dp_amt);
			txtDPRate.setValue(dp_rate);
			txtAvailedAmt.setValue(availed_amt);
			txtAvailedRate.setValue(availed_rate);
		}
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("03")){
			txtDPAmt.setValue(old_sprice);
			txtDPRate.setValue(new BigDecimal("100.00"));
		}
	}
	
	public void editChangeHouseModel(){
		lookupHouseModel.setEditable(true);
		txtSellPrice.setEditable(true);
	}
	
	public Object[] getDataChangeHouseModel(){
		return new Object[]{"16", //req_id 0
							null, //new_entity_id 1
							null, //new_entity_lname 2
							null, //new entity_fname 3
							null, //new_entity_mname 4
							null, //new unit_id 5
							lookupHouseModel.getValue(), //new_house_model 6
							null, //new_agent_id 7
							null,  //new_buyer_type 8
							null, //new_pmt_scheme 9
							(BigDecimal) txtSellPrice.getValued(), //new_sprice 10
							new BigDecimal("0.00"), //new_disc_amt 11
							new BigDecimal("0.00"), //new_disc_rate 12
							(BigDecimal) txtNetSellingPrice.getValued(), //13 new_net_sell_price
							(BigDecimal) txtDPAmt.getValued(),//new dp_amt 14
							(BigDecimal) txtDPRate.getValued(), //new_dp_rate 15
							"",//null, //new_dp_terms //16
							(BigDecimal) txtAvailedAmt.getValued(), //17 new_availed_amt
							(BigDecimal) txtAvailedRate.getValued(), //18 new_availed_rate
							"", //null, //new ma_terms //19 
							null, //new_dp_due_date 20
							null, //new_ma_due_date 21
							"", //22 new_lot_area
							(BigDecimal) txtFireAmt.getValued(), //23 new_fire_amt
							new BigDecimal("0.00"), //24 new_int_rate
							new BigDecimal("0.00"), //25 new_vat_amt
							new BigDecimal("0.00"), //26 new_vat_rate
							"", //
							"", //new scheddate
							""}; 
	}
	
	public void displayChangeHouseModel(String new_model_id, String new_model_desc, BigDecimal new_sprice, BigDecimal new_dp_amt,
			BigDecimal new_dp_rate, BigDecimal new_availed_amt, BigDecimal new_availed_rate, BigDecimal new_fire_amt){
		lookupHouseModel.setValue(new_model_id);
		txtHouseModel.setText(new_model_desc);
		txtSellPrice.setValue(new_sprice);
		txtNetSellingPrice.setValue(new_sprice);
		txtDPAmt.setValue(new_dp_amt);
		txtDPRate.setValue(new_dp_rate);
		txtAvailedAmt.setValue(new_availed_amt);
		txtAvailedRate.setValue(new_availed_rate);
		txtFireAmt.setValue(new_fire_amt);
		//computeNewSellingPriceOnHouseModel(data3);
	}
}
