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
import FormattedTextField._JXFormattedTextField;
import Functions.FncSystem;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_ChangeSellPrice extends JPanel implements _GUI{

	private static final long serialVersionUID = -8855070077169583804L;
	
	private JPanel pnlMain;
	
	private JPanel pnlWest;
	private JLabel lblSellPrice;
	private JLabel lblDPAmt;
	private JLabel lblAvailedAmt;
	private JLabel lblNetSellPrice;
	private JLabel lblDiscAmt;
	
	private JPanel pnlEast;
	private _JXFormattedTextField txtSellPrice;
	
	private JPanel pnlDiscount;
	private _JXFormattedTextField txtDiscAmt;
	private JLabel lblDiscRate;
	private _JXFormattedTextField txtDiscRate;
	
	private JPanel pnlDownpayment;
	private _JXFormattedTextField txtDPAmt;
	private JLabel lblDPRate;
	private _JXFormattedTextField txtDPRate;
	
	
	private JPanel pnlLoanAvailed;
	private _JXFormattedTextField txtAvailedAmt;
	private JLabel lblAvailedRate;
	private _JXFormattedTextField txtAvailedRate;
	private _JXFormattedTextField txtNetSellPrice;
	
	private OtherRequest2 or;
	private pnlOtherRequest_OldData od;
	
	public pnlOtherReq_ChangeSellPrice(pnlOtherRequest_OldData od) {
		//this.or=or;
		this.od=od;
		initGUI();
	}

	public pnlOtherReq_ChangeSellPrice(LayoutManager layout) {
		super(layout);
		initGUI();
	}
	public pnlOtherReq_ChangeSellPrice(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ChangeSellPrice(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.NORTH);
			{
				pnlWest = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblSellPrice = new JLabel("Selling Price");
					pnlWest.add(lblSellPrice);
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
			}
			{
				pnlEast = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlMain.add(pnlEast, BorderLayout.CENTER);
				{
					txtSellPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlEast.add(txtSellPrice);
					txtSellPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtSellPrice.setValue(new BigDecimal("0.00"));
					txtSellPrice.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e){
							try{
								
								txtNetSellPrice.setValue(txtSellPrice.getValued());
								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeSellPrice());
								computeAmounts(data3);
								//computeOnDiscRate(data3);
								
							}catch (NumberFormatException a) {}
						}
					});
				}
				{
					txtNetSellPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlEast.add(txtNetSellPrice);
					txtNetSellPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtNetSellPrice.setValue(new BigDecimal("0.00"));
					txtNetSellPrice.setEditable(false);
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
						/*txtDPAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								try{
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeSellPrice());
									computeAvailedOnDP(data3);
								}catch (NumberFormatException a) {}
							}
						});*/
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
						/*txtDPRate.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								try{
										computeOnDPRate();	
								}catch (NumberFormatException a) {}
							}
						});*/
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
						/*txtAvailedAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								try{
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeSellPrice());
									computeDPOnAvailed(data3);
								}catch (NumberFormatException a){}
							}
						});*/
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
						/*txtAvailedRate.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								try{
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeSellPrice());
									computeOnAvailedRate(data3);
								}catch (NullPointerException b) {}
							}
						});*/
					}
				}
			}
		}
	}//END OF INIT GUI
	
	private void computeOnDPAmt(){//XXX COMPUTES AS THE DP AMOUNT CHANGES
		BigDecimal full_perc = new BigDecimal("100.00");
		BigDecimal dp_amount = (BigDecimal) txtDPAmt.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNetSellPrice.getValued();

		BigDecimal availed_amt = net_sell_price.subtract(dp_amount);
		BigDecimal dp_rate = (dp_amount.divide(net_sell_price)).multiply(full_perc);
		BigDecimal availed_rate = full_perc.subtract(dp_rate);

		txtAvailedAmt.setValue(availed_amt);
		txtDPRate.setValue(dp_rate);
		txtAvailedRate.setValue(availed_rate);
	}
	
	private void computeAvailedOnDP(Object [] data3){
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal final_dp_amt = (BigDecimal) data3[16];
		BigDecimal new_availed_amt = final_net_sell_price.subtract(final_dp_amt);
		txtAvailedAmt.setValue(new_availed_amt);
		computeDPAvailedRate(data3);
	}
	
	private void computeOnDPRate(){//COMPUTES AS THE DP RATE CHANGES
		BigDecimal dp_rate = (BigDecimal) txtDPRate.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNetSellPrice.getValued();
		Double dp_amt = 0.00;
		Double availed_amt = 0.00;
		Double availed_rate = 0.00;

		if(dp_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_ChangeSellPrice.this.getTopLevelAncestor(), "Invalid rate");
			txtDPRate.setValue(new BigDecimal("0.00"));
		}else{
			dp_amt = (dp_rate.doubleValue()/100) * net_sell_price.doubleValue();
			availed_amt = net_sell_price.doubleValue() -dp_amt;
			availed_rate = 100 - dp_rate.doubleValue();
			System.out.printf("Display dp amt %s%n", dp_amt);
			//System.out.printf("Display availed", args)

			txtDPAmt.setValue(BigDecimal.valueOf(dp_amt));
			txtAvailedAmt.setValue(BigDecimal.valueOf(availed_amt));
			txtAvailedRate.setValue(BigDecimal.valueOf(availed_rate));
		}
	}
	
	public void computeOnAvailedRate(Object [] data3){
		BigDecimal new_availed_rate = (BigDecimal) txtAvailedRate.getValued();
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		
		if(new_availed_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_ChangeSellPrice.this.getTopLevelAncestor(), "Invalid Rate");
			txtAvailedRate.setValue(new BigDecimal("0.00"));
		}else{
			BigDecimal new_availed_amt = BigDecimal.valueOf((new_availed_rate.doubleValue()/100) * final_net_sell_price.doubleValue());
			txtAvailedAmt.setValue(new_availed_amt);
			BigDecimal new_dp_amt = final_net_sell_price.subtract(new_availed_amt);
			txtDPAmt.setValue(new_dp_amt);
			BigDecimal new_dp_rate = BigDecimal.valueOf(100 - new_availed_rate.doubleValue());
			txtDPRate.setValue(new_dp_rate);
		}
	}
	
	public void computeDPOnAvailed(Object [] data3){
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		System.out.printf("Display fianl availed amt %s%n", final_availed_amt);
		BigDecimal dp_amt = final_net_sell_price.subtract(final_availed_amt);
		txtDPAmt.setValue(dp_amt);
		computeDPAvailedRate(data3);
	}
	
	public void computeDPAvailedRate(Object [] data3){
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal dp_amt = (BigDecimal) txtDPAmt.getValued();

		BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/final_net_sell_price.doubleValue())*100) ;

		txtDPRate.setValue((dp_rate));
		BigDecimal availed_rate = BigDecimal.valueOf((100 - dp_rate.doubleValue())) ;
		txtAvailedRate.setValue(availed_rate);
	}
	
	public void computeAmounts(Object [] data3){ //Copy To Other Modules
		String final_unit_id = (String) data3[5];
		String final_entity_class = (String) data3[8];
		BigDecimal final_nsp = (BigDecimal) data3[15];
		BigDecimal final_disc_amt = (BigDecimal) data3[11];
		String final_model_id = (String) data3[6];
		BigDecimal gsp = (BigDecimal) txtSellPrice.getValued();
		
		pgSelect db = new pgSelect();
		if(_OtherRequest2.getGroupID(final_entity_class).equals("04")){
			String sql = "select get_hdmf_loanable_amt('"+final_unit_id+"', '"+final_model_id+"', "+gsp+")";
			
			db.select(sql);
			FncSystem.out("Display Loanable amt", sql);
			BigDecimal loanable_amt = (BigDecimal) db.getResult()[0][0];
			BigDecimal dp_amt = final_nsp.subtract(loanable_amt);
			//BigDecimal dp_rate = (dp_amt.divide((BigDecimal) txtSellPrice.getValued())).multiply(new BigDecimal("100"));
			BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/final_nsp.doubleValue())*100);
			BigDecimal loan_rate = new BigDecimal("100").subtract(dp_rate);
			//dp_rate.r
			//System.out.printf("Display availea_rate %s%n", loanable_amt_rate);
			//System.out.printf("Display dp_amt %s%n", dp_amt);
			//System.out.printf("Display dp_rate %s%n", dp_rate);

			txtNetSellPrice.setValue(gsp);
			txtAvailedAmt.setValue(loanable_amt);
			txtDPAmt.setValue(dp_amt);
			txtAvailedRate.setValue(loan_rate);
			txtDPRate.setValue(dp_rate);
		}
		if(_OtherRequest2.getGroupID(final_entity_class).equals("02")){
			
			BigDecimal dp_amt = BigDecimal.valueOf(final_nsp.doubleValue()*.20);
			BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/final_nsp.doubleValue())*100);
			BigDecimal availed_amt = final_nsp.subtract(dp_amt);
			BigDecimal availed_rate = BigDecimal.valueOf(100-dp_rate.doubleValue());
			
			txtDPAmt.setValue(dp_amt);
			txtDPRate.setValue(dp_rate);
			txtAvailedAmt.setValue(availed_amt);
			txtAvailedRate.setValue(availed_rate);
			
		}
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("03")){
			txtDPAmt.setValue(final_nsp.subtract(final_disc_amt));
			txtDPRate.setValue(new BigDecimal("100"));
			txtAvailedAmt.setValue(new BigDecimal("0.00"));
			txtAvailedRate.setValue(new BigDecimal("0.00"));
		}
	}
	
	public void computeOnDiscRate(Object [] data3){ //Copy to Other Modules
		BigDecimal final_nsp = (BigDecimal) txtNetSellPrice.getValued();
		BigDecimal final_disc_rate = (BigDecimal) data3[12];
		BigDecimal disc_amt = new BigDecimal("0.00");
		
		disc_amt = BigDecimal.valueOf(final_nsp.doubleValue()*(final_disc_rate.doubleValue()*.01));
		BigDecimal new_nsp = final_nsp.subtract(disc_amt);
		System.out.printf("Display nsp %s%n", new_nsp);
		
		txtNetSellPrice.setValue(new_nsp);
	}
	
	public void editChangeSellingPrice(){
		txtSellPrice.setEditable(true);
	}
	
	public boolean isLargerLoanableAMt(){
		pgSelect db = new pgSelect();
		String sql = "";
		
		db.select(sql);
		return (Boolean) db.getResult()[0][0];
	}
	
	
	public Object[] getDataChangeSellPrice(){
		return new Object[]{"04", //req_id 0
							null, //new_entity_id 1
							null, //new_entity_lname 2
							null, //new entity_fname 3
							null, //new_entity_mname 4
							null, //new unit_id 5
							null, //new_house_model 6
							null, //new_agent_id 7
							null,  //new_buyer_type 8
							null, //new_pmt_scheme 9
							(BigDecimal) txtSellPrice.getValued(), //new_sprice 10
							new BigDecimal("0.00"), //new_disc_amt 11
							new BigDecimal("0.00"), //new_disc_rate 12
							(BigDecimal) txtNetSellPrice.getValued(), //13 new_net_sell_price
							(BigDecimal) txtDPAmt.getValued(),//new dp_amt 14
							(BigDecimal) txtDPRate.getValued(), //new_dp_rate 15
							"", //null, //new_dp_terms //16
							(BigDecimal) txtAvailedAmt.getValued(), //17 new_availed_amt
							(BigDecimal) txtAvailedRate.getValued(), //18 new_availed_rate
							"", //null, //new ma_terms //19 
							null, //new_dp_due_date 20
							null, //new_ma_due_date 21
							"", //22 new_lot_area
							new BigDecimal("0.00"), //23 new_fire_amt
							new BigDecimal("0.00"), //24 new_int_rate
							new BigDecimal("0.00"), //25 new_vat_amt
							new BigDecimal("0.00"), //26 new_vat_rate
							"",
							"",
							""}; //new scheddate

	}
	
	//XXX OK NA DITO
	public void setDefaultAmounts(Object [] data3){
		Object [] data4 = _OtherRequest2.computeDefaultAmts(data3);
		BigDecimal net_sell_price = (BigDecimal) data4[0];
		BigDecimal vat_amt = (BigDecimal) data4[1];
		BigDecimal vat_rate = (BigDecimal) data4[2];
		BigDecimal availed_amt = (BigDecimal) data4[3];
		BigDecimal dp_amt = (BigDecimal) data4[4];
		BigDecimal availed_rate = (BigDecimal) data4[6];
		BigDecimal dp_rate = (BigDecimal) data4[5];

		txtNetSellPrice.setValue(net_sell_price);
		txtAvailedAmt.setValue(availed_amt);
		txtAvailedRate.setValue(availed_rate);
		txtDPAmt.setValue(dp_amt);
		txtDPRate.setValue(dp_rate);
		//txtVatRate.setValue(vat_rate);
		//txtVatAmt.setValue(vat_amt);

		System.out.printf("Display net selling price %s%n", net_sell_price);
		System.out.printf("Display dp rate %s%n", dp_rate);
		System.out.printf("Display availed rate %s%n", availed_rate);

	}
	
	public void displayChangeSellPrice(BigDecimal new_sprice, BigDecimal new_dp_amt, BigDecimal new_dp_rate, BigDecimal new_availed_amt, BigDecimal new_availed_rate){
		txtSellPrice.setValue(new_sprice);
		txtNetSellPrice.setValue(new_sprice);
		txtDPAmt.setValue(new_dp_amt);
		txtDPRate.setValue(new_dp_rate);
		txtAvailedAmt.setValue(new_availed_amt);
		txtAvailedRate.setValue(new_availed_rate);
	}
}
