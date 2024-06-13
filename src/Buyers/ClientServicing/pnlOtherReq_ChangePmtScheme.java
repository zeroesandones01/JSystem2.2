/**
 * 
 */
package Buyers.ClientServicing;
import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import FormattedTextField._JXFormattedTextField;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_ChangePmtScheme extends JPanel implements _GUI{

	private static final long serialVersionUID = 2324344450147101835L;

	private JPanel pnlMain;
	private JPanel pnlWest;
	private JLabel lblPmtScheme;
	private JLabel lblDiscAmt;
	private JLabel lblNetSellPrice;
	private JLabel lblDPTerms;
	private JLabel lblMATerms;
	private JLabel lblDPAmt;
	private JLabel lblAvailedAmt;

	private JPanel pnlEast;

	private JPanel pnlPmtScheme;
	private _JLookup lookupPmtScheme;
	private _JXTextField txtPmtScheme;

	private JPanel pnlDiscount;
	private _JXFormattedTextField txtDiscRate;
	private JLabel lblDiscRate;
	private _JXFormattedTextField txtDiscAmt;

	private _JXFormattedTextField txtNetSellingPrice;

	private JPanel pnlDownPayment;
	private _JXFormattedTextField txtDPAmt;
	private JLabel lblDPRate;
	private _JXFormattedTextField txtDPRate;

	private JPanel pnlDPTerms;
	//private _JXFormattedTextField txtDPTerms;
	private _JXTextField txtDPTerm;
	private JLabel lblIntRate;
	private _JXFormattedTextField txtIntRate;

	private JPanel pnlLoanAvailed;
	private _JXFormattedTextField txtAvailedAmt;
	private JLabel lblAvailedRate;
	private _JXFormattedTextField txtAvailedRate;

	private JPanel pnlMATerms;
	//private _JXFormattedTextField txtMATerms;
	private _JXTextField txtMATerm;
	
	private OtherRequest2 or;
	private pnlOtherRequest_OldData od;

	public pnlOtherReq_ChangePmtScheme(pnlOtherRequest_OldData od) {
		this.od= od;
		initGUI();
	}

	public pnlOtherReq_ChangePmtScheme(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOtherReq_ChangePmtScheme(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ChangePmtScheme(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(200, 500));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.NORTH);
			{
				pnlWest = new JPanel(new GridLayout(7, 1, 3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblPmtScheme = new JLabel("*Payment Scheme");
					pnlWest.add(lblPmtScheme);
				}
				{
					lblDiscAmt = new JLabel("Discount");
					pnlWest.add(lblDiscAmt);
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
					lblDPTerms = new JLabel("DP Terms");
					pnlWest.add(lblDPTerms);
				}
				{
					lblAvailedAmt = new JLabel("Loan Availed");
					pnlWest.add(lblAvailedAmt);
				}
				{
					lblMATerms = new JLabel("MA Terms");
					pnlWest.add(lblMATerms);
				}
			}
			{
				pnlEast = new JPanel(new GridLayout(7, 1, 3, 3));
				pnlMain.add(pnlEast, BorderLayout.CENTER);
				{
					pnlPmtScheme = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlPmtScheme);
					{
						lookupPmtScheme = new _JLookup(null, "Select Payment Scheme", 0);
						pnlPmtScheme.add(lookupPmtScheme, BorderLayout.WEST);
						lookupPmtScheme.setLookupSQL(_OtherRequest2.sqlPmtScheme("15", null, null, od.getDataOld()));
						lookupPmtScheme.setPreferredSize(new Dimension(100, 0));
						lookupPmtScheme.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!= null){
									try{

										Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangePmtScheme());
										
										String final_pmt_scheme = (String) data3[9];
										Date old_dp_duedate = (Date) data3[38];

										txtPmtScheme.setText((String) data[1]);
										setDPMATerms(data3);
										//setDPMAAmt(data3);
										//computeOnDiscRate(data3);
										computeAmounts(data3);
										
										txtIntRate.setValue(_OtherRequest2.setNewIntRate(final_pmt_scheme));
										FncSystem.out("SQL for Payment Scheme", lookupPmtScheme.getLookupSQL());
										//computeOnDiscRate(od.getDataOld(), getDataChangePmtScheme());
										//XXX SET DEFAULT DP/MA DATE
										//checkFullDP(old_dp_duedate);
									}catch (NumberFormatException e) {}
								}
							}
						});
					}
					{
						txtPmtScheme = new _JXTextField("Payment Scheme Name");
						pnlPmtScheme.add(txtPmtScheme, BorderLayout.CENTER);
					}
				}
				{
					pnlDiscount = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlDiscount);
					{
						txtDiscAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDiscount.add(txtDiscAmt, BorderLayout.WEST);
						txtDiscAmt.setPreferredSize(new Dimension(100, 0));
						txtDiscAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDiscAmt.setValue(new BigDecimal("0.00"));
						txtDiscAmt.setEditable(false);
						/*txtDiscAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangePmtScheme());
								computeOnDiscAmt(data3);
							}
						});*/
					}
					{
						lblDiscRate = new JLabel("Disc Rate", JLabel.TRAILING);
						pnlDiscount.add(lblDiscRate, BorderLayout.CENTER);
					}
					{
						txtDiscRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDiscount.add(txtDiscRate, BorderLayout.EAST);
						txtDiscRate.setPreferredSize(new Dimension(100, 0));
						txtDiscRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDiscRate.setValue(new BigDecimal("0.00"));
						txtDiscRate.setEditable(false);
						/*txtDiscRate.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								computeOnDiscRate(od.getDataOld(), getDataChangePmtScheme());
							}
						});*/
					}
				}
				{
					txtNetSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlEast.add(txtNetSellingPrice);
					txtNetSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtNetSellingPrice.setValue(new BigDecimal("0.00"));
					txtNetSellingPrice.setEditable(false);
				}
				{
					pnlDownPayment = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlDownPayment);
					{
						txtDPAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDownPayment.add(txtDPAmt, BorderLayout.WEST);
						txtDPAmt.setPreferredSize(new Dimension(100, 0));
						txtDPAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDPAmt.setValue(new BigDecimal("0.00"));
						txtDPAmt.setEditable(false);
						txtDPAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								/*Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangePmtScheme());
								computeAvailedOnDP(data3);*/
								if(txtDPAmt.getValued() != null){
									try{
									computeONDPAmt();
									} catch (NumberFormatException a) {}
								}else{
									txtDPAmt.setValue(new BigDecimal("0.00"));
								}
							}
						});
					}
					{
						lblDPRate = new JLabel("DP Rate", JLabel.TRAILING);
						pnlDownPayment.add(lblDPRate, BorderLayout.CENTER);
					}
					{
						txtDPRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDownPayment.add(txtDPRate, BorderLayout.EAST);
						txtDPRate.setPreferredSize(new Dimension(100, 0));
						txtDPRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDPRate.setValue(new BigDecimal("0.00"));
						txtDPRate.setEditable(true);
						txtDPRate.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								computeOnDPRate();
							}
						});
					}
				}
				{
					pnlDPTerms = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlDPTerms);
					/*{
						txtDPTerms = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDPTerms.add(txtDPTerms, BorderLayout.WEST);
						txtDPTerms.setPreferredSize(new Dimension(100, 0));
					}*/
					{
						txtDPTerm = new _JXTextField();
						pnlDPTerms.add(txtDPTerm, BorderLayout.WEST);
						txtDPTerm.setPreferredSize(new Dimension(100, 0));
						txtDPTerm.setHorizontalAlignment(JXTextField.CENTER);
						txtDPTerm.setEditable(true);
					}
					{
						lblIntRate = new JLabel("Interest Rate", JLabel.TRAILING);
						pnlDPTerms.add(lblIntRate, BorderLayout.CENTER);
					}
					{
						txtIntRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDPTerms.add(txtIntRate, BorderLayout.EAST);
						txtIntRate.setPreferredSize(new Dimension(100, 0));
						txtIntRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtIntRate.setValue(new BigDecimal("0.00"));
						txtIntRate.setEditable(false);
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
						txtAvailedAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								/*Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangePmtScheme());
								computeDPOnAvailed(data3);*/
								if(txtAvailedAmt.getValued() != null){
									computeONAvailed();
								}else{
									txtAvailedAmt.setValue(new BigDecimal("0.00"));
								}
							}
						});
					}
					{
						lblAvailedRate = new JLabel("Loan Availed Rate", JLabel.TRAILING);
						pnlLoanAvailed.add(lblAvailedRate, BorderLayout.CENTER);
					}
					{
						txtAvailedRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlLoanAvailed.add(txtAvailedRate, BorderLayout.EAST);
						txtAvailedRate.setPreferredSize(new Dimension(100, 0));
						txtAvailedRate.setValue(new BigDecimal("0.00"));
						txtAvailedRate.setEditable(false);
						/*txtAvailedRate.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangePmtScheme());
								computeOnAvailedRate(data3);
							}
						});*/
					}
				}
				{
					pnlMATerms = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlMATerms);
					/*{
						txtMATerms = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlMATerms.add(txtMATerms, BorderLayout.WEST);
						txtMATerms.setPreferredSize(new Dimension(100, 0));
					}*/
					{
						txtMATerm = new _JXTextField();
						pnlMATerms.add(txtMATerm, BorderLayout.WEST);
						txtMATerm.setPreferredSize(new Dimension(100, 0));
						txtMATerm.setHorizontalAlignment(JXTextField.CENTER);
						txtMATerm.setEditable(true);
						txtMATerm.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								try{
									Integer ma_term = Integer.parseInt(txtMATerm.getText());
									if(ma_term % 12 != 0){
										JOptionPane.showMessageDialog(pnlOtherReq_ChangePmtScheme.this.getTopLevelAncestor(), "Please input a number that is divisible by twelve");
										txtMATerm.setText("");
									}
								}catch (NumberFormatException a) {}
								
							}
						});
						/*txtMATerm.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								try{
									Integer ma_term = Integer.parseInt(txtMATerm.getText());
									if(ma_term % 12 != 0){
										JOptionPane.showMessageDialog(pnlOtherReq_ChangePmtScheme.this.getTopLevelAncestor(), "Please input a number that is divisible by twelve");
										txtMATerm.setText("");
									}
								}catch (NumberFormatException a) {}
							}
						});*/
					}
				}
			}
		}
	}//XXX END OF INIT GUI

	private void setDPMATerms(Object [] data3){//XXX SETTING OF VALUES AFTER LOOKUP OF THE PAYMENT SCHEME
		String final_pmt_scheme = (String) data3[9];
		pgSelect db = new pgSelect();
		
		String sqlDPTerm = "select term from mf_pay_scheme_detail where pmt_scheme_id = '"+final_pmt_scheme+"' and part_id = '013'";
		db.select(sqlDPTerm);
		if(db.isNotNull()){
			txtDPTerm.setText(db.getResult()[0][0].toString());
		}else{
			txtDPTerm.setText("0");
		}
		
		String sqlMATerm = "select term from mf_pay_scheme_detail where pmt_scheme_id = '"+final_pmt_scheme+"' and part_id = '014'";
		db.select(sqlMATerm);
		
		if(db.isNotNull()){
			txtMATerm.setText(db.getResult()[0][0].toString());
		}else{
			txtMATerm.setText("0");
		}
		
		String sqlDiscRate = "select (case when pmt_scheme_desc ~*'SPOT DP' THEN 8 when pmt_scheme_desc ~*'SPOT CASH' then 12 when pmt_scheme_desc ~*'DEFERRED CASH' then 5 ELSE 0 END)::NUMERIC from mf_payment_scheme where pmt_scheme_id = '"+final_pmt_scheme+"'";
		db.select(sqlDiscRate);
		
		txtDiscRate.setValue(db.getResult()[0][0]);
	}

	private void setDPMAAmt(String pmt_scheme_id){
		String sqlDPRate = "select round(rate,2),round(amount,2) from mf_pay_scheme_detail where pmt_scheme_id  = '"+pmt_scheme_id+"' and part_id  = '013'";
		FncSystem.out("Display SQL for getting dp rate and amount", sqlDPRate);
		pgSelect dbDPrate = new pgSelect();
		dbDPrate.select(sqlDPRate);
		if(dbDPrate.isNotNull()){
			BigDecimal dp_rate = (BigDecimal) dbDPrate.getResult()[0][0];
			BigDecimal dp_amount = (BigDecimal) dbDPrate.getResult()[0][1];
			if(dp_rate.compareTo(new BigDecimal("0")) == 1){
				txtDPRate.setValue(dp_rate);
				computeOnDPRate(); //XXX UNCOMMENT ME IF COMPUTATION HAS NO ERROR
			}else{
				txtDPAmt.setValue(dp_amount);
				System.out.printf("Display dp amt %s%n", dp_amount);
				Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangePmtScheme());
				computeAvailedOnDP(data3);; //XXX UNCOMMENT ME IF COMPUTATION HAS NO ERROR
			}
		}else{
			txtDPAmt.setValue(new BigDecimal("0.00"));
			txtDPRate.setValue(new BigDecimal("0.00"));
		}
	}
	
	private void setDPMAAmt(Object [] data3){//XXX COPY THIS TO OTHER MODULES
		String final_pmt_scheme = (String) data3[9];
		String final_entity_class = (String) data3[8];
		
		String sqlDPRate = "select round(rate,2),round(amount,2) from mf_pay_scheme_detail where pmt_scheme_id  = '"+final_pmt_scheme+"' and part_id  = '013'";
		FncSystem.out("Display SQL for getting dp rate and amount", sqlDPRate);
		pgSelect db = new pgSelect();
		db.select(sqlDPRate);
		if(db.isNotNull()){
			BigDecimal dp_rate = (BigDecimal) db.getResult()[0][0];
			BigDecimal dp_amount = (BigDecimal) db.getResult()[0][1];
			if(dp_rate.compareTo(new BigDecimal("0")) == 1){
				txtDPRate.setValue(dp_rate);
				computeOnDPRate(); //XXX UNCOMMENT ME IF COMPUTATION HAS NO ERROR
			}else{
				txtDPAmt.setValue(dp_amount);
				System.out.printf("Display dp amt %s%n", dp_amount);
				computeAvailedOnDP(data3);; //XXX UNCOMMENT ME IF COMPUTATION HAS NO ERROR
			}
		}else{
			txtDPAmt.setValue(new BigDecimal("0.00"));
			txtDPRate.setValue(new BigDecimal("0.00"));
		}
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("03")){
			txtAvailedAmt.setEditable(false);
			txtAvailedRate.setEditable(false);
		}else{
			txtAvailedRate.setEditable(true);
			txtAvailedAmt.setEditable(true);
		}
	}
	
	private void computeOnDPRate(){//COMPUTES AS THE DP RATE CHANGES
		BigDecimal dp_rate = (BigDecimal) txtDPRate.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNetSellingPrice.getValued();
		Double dp_amt = 0.00;
		Double availed_amt = 0.00;
		Double availed_rate = 0.00;

		if(dp_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_ChangePmtScheme.this.getTopLevelAncestor(), "Invalid rate");
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

	private void computeOnDiscRate(Object [] data1, Object [] data2){
		Object [] data3 = _OtherRequest2.setFinalVariables(data1, data2);
		BigDecimal old_sprice = (BigDecimal) data3[53];
		BigDecimal disc_rate = (BigDecimal) txtDiscRate.getValued();

		if(disc_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_ChangePmtScheme.this.getTopLevelAncestor(), "Invalid Rate");
			txtDiscRate.setValue(new BigDecimal("0.00"));
		}else{
			BigDecimal disc_amt = (disc_rate.divide(new BigDecimal("100"))).multiply(old_sprice);
			txtDiscAmt.setValue(disc_amt);
			setDefaultAmounts(data3);
		}
	}
	
	private void computeOnDiscAmt(Object [] data3){
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal new_disc_amt = (BigDecimal) txtDiscAmt.getValued();
		BigDecimal final_sprice = (BigDecimal) data3[10];
		
		if(new_disc_amt.doubleValue() >= final_net_sell_price.doubleValue()){
			JOptionPane.showMessageDialog(pnlOtherReq_ChangePmtScheme.this.getTopLevelAncestor(), "Invalid Amount");
			txtDiscAmt.setValue(new BigDecimal("0.00"));
		}else{
			BigDecimal new_disc_rate = BigDecimal.valueOf((new_disc_amt.doubleValue()/final_sprice.doubleValue()) * 100);
			txtDiscRate.setValue(new_disc_rate);
			setDefaultAmounts(data3);
		}
	}
	
	private void computeAvailedOnDP(Object [] data3){
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
	
	public void computeDPOnAvailed(Object [] data3){
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		System.out.printf("Display fianl availed amt %s%n", final_availed_amt);
		BigDecimal dp_amt = final_net_sell_price.subtract(final_availed_amt);
		txtDPAmt.setValue(dp_amt);
		computeDPAvailedRate(data3);
	}
	
	public void computeOnAvailedRate(Object [] data3){
		BigDecimal new_availed_rate = (BigDecimal) txtAvailedRate.getValued();
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		
		if(new_availed_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_ChangePmtScheme.this.getTopLevelAncestor(), "Invalid Rate");
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

	public void setDefaultAmounts(Object [] data3){
		Object [] data4 = _OtherRequest2.computeDefaultAmts(data3);
		BigDecimal net_sell_price = (BigDecimal) data4[0];
		BigDecimal vat_amt = (BigDecimal) data4[1];
		BigDecimal vat_rate = (BigDecimal) data4[2];
		BigDecimal availed_amt = (BigDecimal) data4[3];
		BigDecimal dp_amt = (BigDecimal) data4[4];
		BigDecimal availed_rate = (BigDecimal) data4[6];
		BigDecimal dp_rate = (BigDecimal) data4[5];

		txtNetSellingPrice.setValue(net_sell_price);
		txtAvailedAmt.setValue(availed_amt);
		txtAvailedRate.setValue(availed_rate);
		txtDPAmt.setValue(dp_amt);
		txtDPRate.setValue(dp_rate);
		//txtVatRate.setValue(vat_rate);
		//txtVatAmt.setValue(vat_amt);

		/*System.out.printf("Display net selling price %s%n", net_sell_price);
		System.out.printf("Display dp rate %s%n", dp_rate);
		System.out.printf("Display availed rate %s%n", availed_rate);*/
	}

	public void checkFullDP(String old_dp_duedate){
		if(old_dp_duedate.equals("FULL DP")){
			txtDPTerm.setEditable(false);
		}else{
			txtDPTerm.setEditable(true);
		}
	}
	
	public void computeOnDiscRate(Object [] data3){ //Copy to Other Modules
		BigDecimal final_nsp = (BigDecimal) data3[15];
		BigDecimal final_disc_rate = (BigDecimal) data3[12];
		BigDecimal disc_amt = new BigDecimal("0.00");
		
		disc_amt = BigDecimal.valueOf(final_nsp.doubleValue()*(final_disc_rate.doubleValue()*.01));
		BigDecimal new_nsp = final_nsp.subtract(disc_amt);
		
		txtDiscAmt.setValue(disc_amt);
		txtNetSellingPrice.setValue(new_nsp);
	}
	
	public void computeAmounts(Object [] data3){ //Correct Computations for the Amounts with Discount
		String final_unit_id = (String) data3[5];
		String final_entity_class = (String) data3[8];
		BigDecimal final_nsp = (BigDecimal) data3[15];
		String final_model_id = (String) data3[6];
		
		BigDecimal old_sprice = (BigDecimal) data3[36];
		
		pgSelect db = new pgSelect();
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("04")){//FOR PAGIBIG ACCOUNTS
			BigDecimal disc_rate = ((BigDecimal) txtDiscRate.getValued()).divide(new BigDecimal("100"));
			BigDecimal disc_amt = old_sprice.multiply(disc_rate);
			BigDecimal nsp = old_sprice.subtract(disc_amt);
			
			String sql = "select get_hdmf_loanable_amt('"+final_unit_id+"', '"+final_model_id+"', "+final_nsp+")";
			
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
			
			txtNetSellingPrice.setValue(nsp);
			txtAvailedAmt.setValue(loanable_amt);
			txtDPAmt.setValue(dp_amt);
			txtAvailedRate.setValue(loan_rate);
			txtDPRate.setValue(dp_rate);
			
		}
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("02")){
			BigDecimal disc_rate = ((BigDecimal) txtDiscRate.getValued()).divide(new BigDecimal("100"));
			BigDecimal disc_amt = old_sprice.multiply(disc_rate);
			BigDecimal nsp = old_sprice.subtract(disc_amt);
			
			BigDecimal dp_amt = BigDecimal.valueOf(nsp.doubleValue()*.20);
			BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/nsp.doubleValue())*100);
			BigDecimal availed_amt = nsp.subtract(dp_amt);
			BigDecimal availed_rate = BigDecimal.valueOf(100-dp_rate.doubleValue());
			
			txtDiscAmt.setValue(disc_amt);
			txtNetSellingPrice.setValue(nsp);
			txtDPAmt.setValue(dp_amt);
			txtDPRate.setValue(dp_rate);
			txtAvailedAmt.setValue(availed_amt);
			txtAvailedRate.setValue(availed_rate);
		}
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("03")){ //CHECK IF SPOT CASH OR DEFERRED CASH BEFORE COMPUTING HERE
			BigDecimal disc_rate = ((BigDecimal) txtDiscRate.getValued()).divide(new BigDecimal("100"));
			BigDecimal disc_amt = old_sprice.multiply(disc_rate);
			BigDecimal nsp = old_sprice.subtract(disc_amt);
			
			txtDiscAmt.setValue(disc_amt);
			txtNetSellingPrice.setValue(nsp);
			txtDPAmt.setValue(nsp);
			txtDPRate.setValue(new BigDecimal("100.00"));
			txtAvailedAmt.setValue(new BigDecimal("0.00"));
			txtAvailedRate.setValue(new BigDecimal("0.00"));
		}
	}
	
	public void editChangePmtScheme(){
		lookupPmtScheme.setEditable(true);
		txtDPTerm.setEditable(true);
		txtMATerm.setEditable(true);
		txtDPAmt.setEditable(true);
		txtAvailedAmt.setEditable(true);
	}
	
	public void computeONDPAmt(){//COMPUTE Amounts;
		BigDecimal nsp = (BigDecimal) txtNetSellingPrice.getValued();
		BigDecimal dp_amt = (BigDecimal) txtDPAmt.getValued();
		
		BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/nsp.doubleValue())*100);
		BigDecimal availed_amt = nsp.subtract(dp_amt);
		BigDecimal availed_rate = new BigDecimal("100").subtract(dp_rate);
		txtDPRate.setValue(dp_rate);
		txtAvailedAmt.setValue(availed_amt);
		txtAvailedRate.setValue(availed_rate);
	}
	
	private void computeONDPRate(){
		BigDecimal nsp = (BigDecimal) txtNetSellingPrice.getValued();
		BigDecimal dp_rate = (BigDecimal) txtDPRate.getValued();

		BigDecimal dp_amt = BigDecimal.valueOf((dp_rate.doubleValue()/100)*nsp.doubleValue());
		BigDecimal availed_rate = new BigDecimal("100").subtract(dp_rate);
		BigDecimal availed_amt = nsp.subtract(dp_amt);
		
		txtDPAmt.setValue(dp_amt);
		txtAvailedRate.setValue(availed_rate);
		txtAvailedAmt.setValue(availed_amt);
		
	}
	
	public void computeONAvailed(){
		
		BigDecimal nsp = (BigDecimal) txtNetSellingPrice.getValued();
		BigDecimal availed_amt = (BigDecimal) txtAvailedAmt.getValued();
		
		BigDecimal availed_rate = BigDecimal.valueOf((availed_amt.doubleValue()/nsp.doubleValue())*100);
		BigDecimal dp_amt = nsp.subtract(availed_amt);
		BigDecimal dp_rate = BigDecimal.valueOf(100-availed_rate.doubleValue());
		txtAvailedRate.setValue(availed_rate);
		txtDPAmt.setValue(dp_amt);
		txtDPRate.setValue(dp_rate);
	}
	
	public Object[] getDataChangePmtScheme(){
		return new Object[]{"15", //req_id 0
							null, //new_entity_id 1
							null, //new_entity_lname 2
							null, //new entity_fname 3
							null, //new_entity_mname 4
							null, //new unit_id 5
							null, //new_house_model 6
							null, //new_agent_id 7
							null,  //new_buyer_type 8
							lookupPmtScheme.getValue(), //new_pmt_scheme 9
							new BigDecimal("0.00"), //new_sprice 10
							(BigDecimal) txtDiscAmt.getValued(), //new_disc_amt 11
							(BigDecimal) txtDiscRate.getValued(), //new_disc_rate 12
							(BigDecimal) txtNetSellingPrice.getValued(), //13 new_net_sell_price
							(BigDecimal) txtDPAmt.getValued(),//new dp_amt 14
							(BigDecimal) txtDPRate.getValued(), //new_dp_rate 15
							txtDPTerm.getText(), //(Integer) txtDPTerms.getValued(), //new_dp_terms //16
							(BigDecimal) txtAvailedAmt.getValued(), //17 new_availed_amt
							(BigDecimal) txtAvailedRate.getValued(), //18 new_availed_rate
							txtMATerm.getText(), //(Integer) txtMATerms.getValued(), //new ma_terms //19 
							null, //new_dp_due_date 20
							null, //new_ma_due_date 21
							"", //22 new_lot_area
							new BigDecimal("0.00"), //23 new_fire_amt
							(BigDecimal) txtIntRate.getValued(), //24 new_int_rate
							new BigDecimal("0.00"), //25 new_vat_amt
							new BigDecimal("0.00"), //26 new_vat_rate
							"", 
							"",
							""}; //new scheddate

	}

	public boolean toSave(){
		if(lookupPmtScheme.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Payment Scheme"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean checkRequiredFields(){
		String required_fields = "";
		Boolean toSave = true;
		if(lookupPmtScheme.getValue() == null || lookupPmtScheme.getValue().isEmpty()){
			required_fields = required_fields + "Payment Scheme\n";
			toSave = false;
		}
		if(txtDPTerm.getText().isEmpty() || txtDPTerm.getText().equals("")){
			required_fields = required_fields + "DP Term\n";
			toSave = false;
		}
		if(txtMATerm.getText().isEmpty() || txtMATerm.getText().equals("")){
			required_fields = required_fields + "MA Term";
			toSave = false;
		}
		
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	public void displayChangePmtScheme(String new_entity_class, String new_entity_class_desc, String new_pmt_scheme, String new_pmt_scheme_desc,
			BigDecimal new_disc_amt, BigDecimal new_disc_rate, BigDecimal new_dp_amt, BigDecimal new_dp_rate, Integer new_dpterm,
			BigDecimal new_availed_amt, BigDecimal new_availed_rate, Integer new_materm, BigDecimal new_sprice){
		System.out.println("Dumaan dito sa display ng Change Payment Scheme");
		
		lookupPmtScheme.setValue(new_pmt_scheme);
		txtPmtScheme.setText(new_pmt_scheme_desc);
		txtDiscAmt.setValue(new_disc_amt);
		txtDiscRate.setValue(new_disc_rate);
		//XXXNET SELL PRICE
		txtNetSellingPrice.setValue(new_sprice.subtract(new_disc_amt));
		txtDPAmt.setValue(new_dp_amt);
		txtDPRate.setValue(new_dp_rate);
		//txtDPTerms.setValue(new_dpterm);
		txtDPTerm.setText(new_dpterm.toString());
		txtIntRate.setValue(_OtherRequest2.setNewIntRate(new_pmt_scheme));
		System.out.print("Display Int rate");
		txtAvailedAmt.setValue(new_availed_amt);
		txtAvailedRate.setValue(new_availed_rate);
		//txtMATerms.setValue(new_materm);
		txtMATerm.setText(new_materm.toString());
		//computeOnDiscRate(od.getDataOld(), getDataChangePmtScheme());
		//computeOnDPAmt();
		System.out.printf("Display new entity class %s%n", new_entity_class);
		System.out.printf("Display new pmt scheme %s%n", new_pmt_scheme);
		System.out.printf("Display new sprice %s%n", new_sprice);
		System.out.printf("Display int_rate %s%n", _OtherRequest2.setNewIntRate(new_pmt_scheme));
		//setDefaultAmounts(data3);
	}

}
