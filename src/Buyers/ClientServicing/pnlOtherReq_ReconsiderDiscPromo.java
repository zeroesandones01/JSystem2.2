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
import java.text.DecimalFormat;

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
public class pnlOtherReq_ReconsiderDiscPromo extends JPanel implements _GUI {

	private static final long serialVersionUID = -305494527734473590L;

	protected static DecimalFormat df = new DecimalFormat("#,##0.00");

	private JPanel pnlMain;

	private JPanel pnlWest;
	private JLabel lblSellingPrice;
	private JLabel lblDiscAmt;
	private JLabel lblNetSellPrice;
	private JLabel lblDPAmt;
	private JLabel lblVatAmt;
	private JLabel lblAvailedAmt;

	private JPanel pnlEast;

	private _JXFormattedTextField txtSellingPrice;

	private JPanel pnlDiscount;
	private _JXFormattedTextField txtDiscAmt;
	private JLabel lblDiscRate;
	private _JXFormattedTextField txtDiscRate;

	private _JXFormattedTextField txtNetSellingPrice;

	private JPanel pnlDownpayment;
	private _JXFormattedTextField txtDPAmt;
	private JLabel lblDPrate;
	private _JXFormattedTextField txtDPRate;

	private JPanel pnlVat;
	private _JXFormattedTextField txtVATAmt;
	private JLabel lblVatRate;
	private _JXFormattedTextField txtVATRate;

	private JPanel pnlLoanAvailed;
	private _JXFormattedTextField txtAvailedAmt;
	private JLabel lblAvailedRate;
	private _JXFormattedTextField txtAvailedRate;

	private OtherRequest2 or;
	private pnlOtherRequest_OldData od;

	public pnlOtherReq_ReconsiderDiscPromo(pnlOtherRequest_OldData od) {
		this.od= od;
		initGUI();
	}

	public pnlOtherReq_ReconsiderDiscPromo(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOtherReq_ReconsiderDiscPromo(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ReconsiderDiscPromo(LayoutManager layout,
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
				pnlWest = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				/*{
					lblSellingPrice = new JLabel("Selling Price");
					pnlWest.add(lblSellingPrice);
				}*/
				{
					lblDiscRate = new JLabel("*Disc. Rate");
					pnlWest.add(lblDiscRate);
				}
				/*{
					lblVatAmt = new JLabel("VAT Amount");
					pnlWest.add(lblVatAmt);
				}*/
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
				/*{
					txtSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlEast.add(txtSellingPrice);
					txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtSellingPrice.setValue(new BigDecimal("0.00"));
					txtSellingPrice.setEditable(false);
				}*/
				{
					pnlDiscount = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlDiscount);
					{
						txtDiscRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDiscount.add(txtDiscRate, BorderLayout.WEST);
						txtDiscRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDiscRate.setPreferredSize(new Dimension(100, 0));
						txtDiscRate.setValue(new BigDecimal("0.00"));
						txtDiscRate.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								try{
									//computeOnDiscRate(od.getDataOld(), getDataReconDiscPromo());
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataReconDiscPromo());
									computeOnDiscRate(data3);
									//computeAmounts(data3);
								} catch(NullPointerException a) {}
							}
						});
					}
					{
						lblDiscAmt = new JLabel("Disc. Amt", JLabel.TRAILING);
						pnlDiscount.add(lblDiscAmt, BorderLayout.CENTER);
					}
					{
						txtDiscAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDiscount.add(txtDiscAmt, BorderLayout.EAST);
						txtDiscAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDiscAmt.setPreferredSize(new Dimension(100, 0));
						txtDiscAmt.setValue(new BigDecimal("0.00"));
						txtDiscAmt.setEditable(false);
						txtDiscAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								try{
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataReconDiscPromo());
									computeOnDiscAmt(data3);
									//computeOnDiscAmt(data3);
									//computeAmounts(data3);
								}catch (NullPointerException a) {}
							}
						});
					}
				}
				/*{
					pnlVat = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlVat);
					{
						txtVATAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlVat.add(txtVATAmt, BorderLayout.WEST);
						txtVATAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtVATAmt.setPreferredSize(new Dimension(100, 0));
						txtVATAmt.setValue(new BigDecimal("0.00"));
						txtVATAmt.setEditable(false);
					}
					{
						lblVatRate = new JLabel("VAT Rate", JLabel.TRAILING);
						pnlVat.add(lblVatRate, BorderLayout.CENTER);
					}
					{
						txtVATRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlVat.add(txtVATRate, BorderLayout.EAST);
						txtVATRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtVATRate.setPreferredSize(new Dimension(100, 0));
						txtVATRate.setValue(new BigDecimal("0.00"));
						txtVATRate.setEditable(false);
					}
				}*/
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
						/*txtDPAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								try{
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataReconDiscPromo());
									computeAvailedOnDP(data3);
								} catch(NullPointerException a) {}
							}
						});*/
					}
					{
						lblDPrate = new JLabel("DP Rate", JLabel.TRAILING);
						pnlDownpayment.add(lblDPrate, BorderLayout.CENTER);
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
								computeOnDPRate();
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

								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataReconDiscPromo());
								computeDPOnAvailed(data3);
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
								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataReconDiscPromo());
								computeOnAvailedRate(data3);
							}
						});*/
					}
				}
			}
		}
	}//END OF INIT GUI

	/*private void computeOnDiscAmt(Object [] data3){
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal new_disc_amt = (BigDecimal) txtDiscAmt.getValued();
		BigDecimal final_sprice = (BigDecimal) data3[10];

		if(new_disc_amt.doubleValue() >= final_net_sell_price.doubleValue()){
			JOptionPane.showMessageDialog(pnlOtherReq_ReconsiderDiscPromo.this.getTopLevelAncestor(), "Invalid Amount");
			txtDiscAmt.setValue(new BigDecimal("0.00"));
		}else{
			BigDecimal new_disc_rate = BigDecimal.valueOf((new_disc_amt.doubleValue()/final_sprice.doubleValue()) * 100);
			txtDiscRate.setValue(new_disc_rate);
			setDefaultAmounts(data3);
		}
	}*/

	private void computeOnDiscRate(Object [] data1, Object [] data2){ //REMOVE THIS IF COMPUTATIONS ARE CORRECT
		Object [] data3 = _OtherRequest2.setFinalVariables(data1, data2);
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal disc_rate = (BigDecimal) txtDiscRate.getValued();

		if(disc_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_ReconsiderDiscPromo.this.getTopLevelAncestor(), "Invalid Rate");
			txtDiscRate.setValue(new BigDecimal("0.00"));
		}else{
			BigDecimal disc_amt = BigDecimal.valueOf((disc_rate.doubleValue()/100) * final_sprice.doubleValue());
			txtDiscAmt.setValue(disc_amt);
			//txtDiscRate.setText(df.format(disc_amt.doubleValue()));
			System.out.printf("Display discount amt %s%n", disc_amt);
			setDefaultAmounts(data3);
		}
	}

	private void computeAvailedOnDP(Object [] data3){ //REMOVE THIS IF COMPUTATIONS ARE CORRECT
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal final_dp_amt = (BigDecimal) data3[16];
		BigDecimal new_availed_amt = final_net_sell_price.subtract(final_dp_amt);
		txtAvailedAmt.setValue(new_availed_amt);
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

	private void computeOnDPRate(){//COMPUTES AS THE DP RATE CHANGES //REMOVE THIS WHEN COMPUTATIONS ARE CORRECT
		BigDecimal dp_rate = (BigDecimal) txtDPRate.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNetSellingPrice.getValued();
		Double dp_amt = 0.00;
		Double availed_amt = 0.00;
		Double availed_rate = 0.00;

		if(dp_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_ReconsiderDiscPromo.this.getTopLevelAncestor(), "Invalid rate");
			txtDPRate.setValue(new BigDecimal("0.00"));
		}else{
			dp_amt = (dp_rate.doubleValue()/100) * net_sell_price.doubleValue();
			availed_amt = net_sell_price.doubleValue() -dp_amt;
			availed_rate = 100 - dp_rate.doubleValue();
			System.out.printf("Display dp amt %s%n", dp_amt);

			txtDPAmt.setValue(BigDecimal.valueOf(dp_amt));
			txtAvailedAmt.setValue(BigDecimal.valueOf(availed_amt));
			txtAvailedRate.setValue(BigDecimal.valueOf(availed_rate));
		}
	}

	public void computeDPOnAvailed(Object [] data3){//REMOVE THIS WHEN COMPUTATIONS ARE CORRECT
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		System.out.printf("Display fianl availed amt %s%n", final_availed_amt);
		BigDecimal dp_amt = final_net_sell_price.subtract(final_availed_amt);
		txtDPAmt.setValue(dp_amt);
		computeDPAvailedRate(data3);
	}

	public void computeOnAvailedRate(Object [] data3){ //REMOVE THIS WHEN COMPUTATIONS ARE CORRECT
		BigDecimal new_availed_rate = (BigDecimal) txtAvailedRate.getValued();
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];

		if(new_availed_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_ReconsiderDiscPromo.this.getTopLevelAncestor(), "Invalid Rate");
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

	public void setDefaultAmounts(Object [] data3){//REMOVE THIS WHEN COMPUTATIONS ARE CORRECT
		Object [] data4 = _OtherRequest2.computeDefaultAmts(data3);
		BigDecimal net_sell_price = (BigDecimal) data4[0];
		BigDecimal vat_amt = (BigDecimal) data4[1];
		BigDecimal vat_rate = (BigDecimal) data4[2];
		BigDecimal availed_amt = (BigDecimal) data4[3];
		BigDecimal dp_amt = (BigDecimal) data4[4];
		BigDecimal availed_rate = (BigDecimal) data4[6];
		BigDecimal dp_rate = (BigDecimal) data4[5];

		/*Double net_sell_price = (Double) data4[0];
		Double vat_amt = (Double) data4[1];
		Double vat_rate = (Double) data4[2];
		Double availed_amt = (Double) data4[3];
		Double dp_amt = (Double) data4[4];
		Double availed_rate = (Double) data4[5];
		Double dp_rate = (Double) data4[6];*/

		txtNetSellingPrice.setValue(net_sell_price);
		txtAvailedAmt.setValue(availed_amt);
		txtAvailedRate.setValue(availed_rate);
		txtDPAmt.setValue(dp_amt);
		txtDPRate.setValue(dp_rate);

		//txtVatRate.setValue(vat_rate);
		//txtVatAmt.setValue(vat_amt);

		/*System.out.printf("Display net selling price %s%n", net_sell_price);
		System.out.printf("Display dp rate %s%n", dp_rate);
		System.out.printf("Display availed rate %s%n", availed_rate);
		System.out.printf("Display dp amt %s%n", dp_amt);
		System.out.printf("Display availed amt %s%n", availed_amt);*/
	}

	public void computeAmounts(Object [] data3){//REMOVE THIS WHEN COMPUTATIONS ARE CORRECT
		String final_unit_id = (String) data3[5];
		String final_entity_class = (String) data3[8];
		BigDecimal final_nsp = (BigDecimal) data3[15];

		pgSelect db = new pgSelect();

		if(_OtherRequest2.getGroupID(final_entity_class).equals("04")){
			String sql = "select round(loanable_amt, 2), round(((loanable_amt/"+final_nsp+")*100), 2), \n"+
					""+final_nsp+"-loanable_amt, 100-round(((loanable_amt/"+final_nsp+")*100), 2) \n"+
					"from mf_unit_pricing where pbl_id = getinteger('"+final_unit_id+"')::VARCHAR";
			db.select(sql);
			FncSystem.out("Display Loanable amt", sql);
			BigDecimal loanable_amt = (BigDecimal) db.getResult()[0][0];
			BigDecimal loanable_amt_rate = (BigDecimal) db.getResult()[0][1];
			BigDecimal dp_amt = (BigDecimal) db.getResult()[0][2];
			BigDecimal dp_rate = (BigDecimal) db.getResult()[0][3];
			//dp_rate.r
			System.out.printf("Display availea_rate %s%n", loanable_amt_rate);
			System.out.printf("Display dp_amt %s%n", dp_amt);
			System.out.printf("Display dp_rate %s%n", dp_rate);

			txtAvailedAmt.setValue(loanable_amt);
			txtAvailedRate.setValue(loanable_amt_rate);
			txtDPAmt.setValue(dp_amt);
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
	}

	public void computeOnDiscRate(Object[] data3){ //PARA DITO LANG TO
		String final_unit_id = (String) data3[5];
		String final_entity_class = (String) data3[8];
		BigDecimal final_nsp = (BigDecimal) data3[15];
		String final_model_id = (String) data3[6];
		//BigDecimal old_nsp = (BigDecimal) data3[47];
		BigDecimal final_sprice = (BigDecimal) data3[10];;
		BigDecimal disc_rate = (BigDecimal) txtDiscRate.getValued();
		BigDecimal disc_amt = new BigDecimal("0.00");

		pgSelect db = new pgSelect();

		if(disc_rate.doubleValue() >= 100 || disc_rate.doubleValue() < 0){
			JOptionPane.showMessageDialog(pnlOtherReq_ReconsiderDiscPromo.this.getTopLevelAncestor(), "Invalid Rate");
			txtDiscRate.setValue(new BigDecimal("0.00"));
		}else{
			if(_OtherRequest2.getGroupID(final_entity_class).equals("02")){

				disc_amt = BigDecimal.valueOf(final_sprice.doubleValue()*(disc_rate.doubleValue()*.01));
				BigDecimal new_nsp = final_sprice.subtract(disc_amt);
				BigDecimal dp_amt = new_nsp.multiply(new BigDecimal(".20"));
				BigDecimal availed_amt = new_nsp.subtract(dp_amt);

				txtDiscAmt.setValue(disc_amt);
				txtNetSellingPrice.setValue(new_nsp);
				txtDPAmt.setValue(dp_amt);
				txtAvailedAmt.setValue(availed_amt);
				txtAvailedRate.setValue(new BigDecimal("80"));
				txtDPRate.setValue(new BigDecimal("20"));
			}
			if(_OtherRequest2.getGroupID(final_entity_class).equals("04")){//CHECK THE DISCOUNT AMOUNT HERE

				disc_amt = BigDecimal.valueOf(final_sprice.doubleValue()*(disc_rate.doubleValue()*.01));
				BigDecimal nsp = final_sprice.subtract(disc_amt);

				String sql = "select get_hdmf_loanable_amt('"+final_unit_id+"', '"+final_model_id+"', '"+nsp+"')";
				db.select(sql);

				txtNetSellingPrice.setValue(nsp);
				BigDecimal loanable_amt = (BigDecimal) db.getResult()[0][0];
				BigDecimal dp_amt = nsp.subtract(loanable_amt);
				BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/final_nsp.doubleValue())*100);
				BigDecimal loan_rate = new BigDecimal("100").subtract(dp_rate);

				txtAvailedAmt.setValue(loanable_amt);
				txtDPAmt.setValue(dp_amt);
				txtAvailedRate.setValue(loan_rate);
				txtDPRate.setValue(dp_rate);

			}
			if(_OtherRequest2.getGroupID(final_entity_class).equals("03")){
				disc_amt = BigDecimal.valueOf(final_sprice.doubleValue()*(disc_rate.doubleValue()*.01));
				BigDecimal nsp = final_sprice.subtract(disc_amt);
				txtDPAmt.setValue(nsp);
				txtDPRate.setValue(new BigDecimal("100.00"));
				txtAvailedAmt.setValue(new BigDecimal("0.00"));
				txtAvailedRate.setValue(new BigDecimal("0.00"));
						
			}
		}
	}

	public void editReconDiscPromo(){
		txtDiscRate.setEditable(true);
	}

	public void computeOnDiscAmt(Object [] data3){// THE DISCOUNT AMOUNT TO BE ENTERED HERE MUST NOT EXCEED THE FINAL_NSP - LOANABLE AMOUNT IN THE mf_unit_pricing if the buyer type is pagibig
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal disc_amt = (BigDecimal) txtDiscAmt.getValued();
		BigDecimal disc_rate = new BigDecimal("0.00");

		BigDecimal new_nsp = final_sprice.subtract(disc_amt);
		disc_rate = BigDecimal.valueOf((disc_amt.doubleValue()/final_sprice.doubleValue())*100);
		txtNetSellingPrice.setValue(new_nsp);
		txtDiscRate.setValue(disc_rate);
		computeAmounts(data3);
	}

	public boolean toSave(){
		BigDecimal disc_rate = (BigDecimal) txtDiscRate.getValued();
		BigDecimal disc_amt = (BigDecimal) txtDiscAmt.getValued();

		if(disc_rate.doubleValue() == 0){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Discount rate"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(disc_amt.doubleValue() == 0){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Discount amount"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean checkRequiredFields(){
		BigDecimal disc_rate = (BigDecimal) txtDiscRate.getValued();
		BigDecimal disc_amt = (BigDecimal) txtDiscAmt.getValued();
		String required_fields = "";
		Boolean toSave = true; 

		if(disc_rate.doubleValue() == 0 || disc_rate == null){
			required_fields = required_fields + "Discount Rate \n";
			toSave = false;
		}
		if(disc_amt.doubleValue() == 0 || disc_amt == null){
			required_fields = required_fields + "Discount Amount\n";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	public Object[] getDataReconDiscPromo(){
		return new Object[]{"08", //req_id 0
				null, //new_entity_id 1
				null, //new_entity_lname 2
				null, //new entity_fname 3
				null, //new_entity_mname 4
				null, //new unit_id 5
				null, //new_house_model 6
				null, //new_agent_id 7
				null,  //new_buyer_type 8
				null, //new_pmt_scheme 9
				new BigDecimal("0.00"), //new_sprice 10
				(BigDecimal) txtDiscAmt.getValued(), //new_disc_amt 11
				(BigDecimal) txtDiscRate.getValued(), //new_disc_rate 12
				(BigDecimal) txtNetSellingPrice.getValued(), //13
				(BigDecimal) txtDPAmt.getValued(),//new dp_amt 14
				(BigDecimal) txtDPRate.getValued(), //new_dp_rate 15
				"", //null, //new_dp_terms //16
				(BigDecimal) txtAvailedAmt.getValued(), //17
				(BigDecimal) txtAvailedRate.getValued(), //18
				"", //null, //new ma_terms //19
				null, //new_dp_due_date 20
				null, //new_ma_due_date 21
				"", //22
				new BigDecimal("0.00"), //23
				new BigDecimal("0.00"), //24
				new BigDecimal("0.00"), //25
				new BigDecimal("0.00"),
				"", //26 new schedate
				"", //new civil status
		""}; 

	}

	public void displayReconDiscPromo(BigDecimal new_disc_amt, BigDecimal new_disc_rate, BigDecimal new_dp_amt, BigDecimal new_dp_rate,
			BigDecimal new_availed_amt, BigDecimal new_availed_rate, BigDecimal new_sprice){

		txtDiscAmt.setValue(new_disc_amt);
		txtDiscRate.setValue(new_disc_rate);
		txtDPAmt.setValue(new_dp_amt);
		txtDPRate.setValue(new_dp_rate);
		txtAvailedAmt.setValue(new_availed_amt);
		txtAvailedRate.setValue(new_availed_rate);
		txtNetSellingPrice.setValue(new_sprice.subtract(new_disc_amt));
		//computeOnDiscRate(od.getDataOld(),getDataReconDiscPromo());

	}

}
