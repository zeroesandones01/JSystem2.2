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
public class pnlOtherReq_TransferOfUnit extends JPanel implements _GUI{

	private static final long serialVersionUID = -3291908645172228786L;

	private JPanel pnlMain;
	private JPanel pnlWest;
	private JLabel lblUnit;
	private JLabel lblLotArea;
	private JLabel lblHouseModel;
	private JLabel lblClientClass;
	private JLabel lblPmtScheme;
	private JLabel lblSellPrice;
	private JLabel lblDiscAmt;
	private JLabel lblVatAmt;
	private JLabel lblNetSellPrice;
	private JLabel lblDPTerms;
	private JLabel lblMATerms;
	private JLabel lblDPAmt;
	private JLabel lblAvailedAmt;

	private JPanel pnlEast;

	private JPanel pnlUnitPBL;
	private _JLookup lookupUnit;
	private _JXTextField txtUnit;

	private JPanel pnlLotArea;
	private _JXTextField txtlotArea;

	private JPanel pnlHouseModel;
	private _JXTextField txtModelID;
	private _JXTextField txtModelName;

	private JPanel pnlClientClass;
	private _JLookup lookupClientClass;
	private _JXTextField txtClientClass;

	private JPanel pnlPmtScheme;
	private _JLookup lookupPmtScheme;
	private _JXTextField txtPmtScheme;

	private _JXFormattedTextField txtSellPrice;

	private JPanel pnlDiscount;
	private _JXFormattedTextField txtDiscRate;
	private JLabel lblDiscRate;
	private _JXFormattedTextField txtDiscAmt;

	private JPanel pnlVAT;
	private _JXFormattedTextField txtVatAmt;
	private JLabel lblVatRate;
	private _JXFormattedTextField txtVatRate;

	private _JXFormattedTextField txtNetSellingPrice;

	private JPanel pnlDownPayment;
	private _JXFormattedTextField txtDPAmt;
	private JLabel lblDPRate;
	private _JXFormattedTextField txtDPRate;

	private JPanel pnlDPTerms;
	//private _JXFormattedTextField txtDPTerms;
	private _JXTextField txtDPTerm;
	private JLabel lblFireAmt;
	private _JXFormattedTextField txtFireAmt;

	private JPanel pnlLoanAvailed;
	private _JXFormattedTextField txtAvailedAmt;
	private JLabel lblAvailedRate;
	private _JXFormattedTextField txtAvailedRate;

	private JPanel pnlMATerms;
	//private _JXFormattedTextField txtMATerms;
	private _JXTextField txtMATerm;
	private JLabel lblIntRate;
	private _JXFormattedTextField txtIntRate;

	private OtherRequest2 or;
	private pnlOtherRequest_OldData od;

	public pnlOtherReq_TransferOfUnit(pnlOtherRequest_OldData od) {
		this.od=od;
		initGUI();
	}

	public pnlOtherReq_TransferOfUnit(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOtherReq_TransferOfUnit(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_TransferOfUnit(LayoutManager layout,boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(500, 500));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.NORTH);
			{
				pnlWest = new JPanel(new GridLayout(13, 1, 1, 1));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblUnit = new JLabel("*Unit");
					pnlWest.add(lblUnit);
				}
				{
					lblLotArea = new JLabel("Lot Area");
					pnlWest.add(lblLotArea);
				}
				{
					lblHouseModel = new JLabel("House Model");
					pnlWest.add(lblHouseModel);
				}
				{
					lblClientClass = new JLabel("*Client Class");
					pnlWest.add(lblClientClass);
				}
				{
					lblPmtScheme = new JLabel("*Payment Scheme");
					pnlWest.add(lblPmtScheme);
				}
				{
					lblSellPrice = new JLabel("Selling Price");
					pnlWest.add(lblSellPrice);
				}
				{
					lblDiscAmt = new JLabel("Discount");
					pnlWest.add(lblDiscAmt);
				}
				{
					lblVatAmt= new JLabel("VAT");
					pnlWest.add(lblVatAmt);
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
				pnlEast = new JPanel(new GridLayout(13, 1, 3, 3));
				pnlMain.add(pnlEast, BorderLayout.CENTER);
				{
					pnlUnitPBL = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlUnitPBL);
					{
						lookupUnit = new _JLookup(null, "Select Unit", 0);
						pnlUnitPBL.add(lookupUnit, BorderLayout.WEST);
						lookupUnit.setPreferredSize(new Dimension(100, 0));
						//lookupUnit.setLookupSQL(_OtherRequest.sqlNewUnit(od.getDataOld()));
						lookupUnit.setLookupSQL(_OtherRequest2.sqlOpenUnits(od.getDataOld()));
						lookupUnit.addLookupListener(new LookupListener() {
							@Override
							public void lookupPerformed(LookupEvent event) { //ok na dito
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!= null){
									lookupClientClass.setLookupSQL(_OtherRequest2.sqlClientClass("06", od.getDataOld(), (String) data[0]));
									txtUnit.setText((String) data[1]);
									txtModelID.setText((String) data[3]);
									txtModelName.setText((String) data[4]);
									txtlotArea.setText((String) data[5]);
									
									FncSystem.out("Display sql Open Units", lookupUnit.getLookupSQL());
									
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
									String final_unit_id = (String) data3[5];
									String final_model_id = (String) data3[6];
									BigDecimal final_sprice = (BigDecimal) data3[10];
									String final_entity_class = (String) data3[8];
									String old_unit_id = (String) data3[35];
									String new_unit_id = (String) data3[65];
									//System.out.printf("Display old unit id %s%n", old_unit_id);
									txtFireAmt.setValue(_OtherRequest2.setNewFire(new_unit_id, final_entity_class, final_unit_id, final_model_id));
									setNewVatrate(new_unit_id, final_sprice);
									//computeSellingPrice(data3);
									computeNewSellingPriceOnHouseModel(data3);
									/*if(_OtherRequest2.hasMergeUnit(old_unit_id)){
										try{
											computeOnSellingPriceOnMerge(data3);
										}catch (NumberFormatException e) {}
									}else{
										try{
											computeNewSellingPriceOnHouseModel(data3);
											setDefaultAmounts(data3);
										}catch(NumberFormatException e) {}
									}*/
									
								}
							}
						});
					}
					{
						txtUnit = new _JXTextField("Unit Name");
						pnlUnitPBL.add(txtUnit, BorderLayout.CENTER);
					}
				}
				{
					pnlLotArea = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlLotArea);
					{
						txtlotArea = new _JXTextField("Lot Area");
						pnlLotArea.add(txtlotArea, BorderLayout.WEST);
						txtlotArea.setHorizontalAlignment(JXTextField.CENTER);
						txtlotArea.setPreferredSize(new Dimension(100, 0));
					}
				}
				{
					pnlHouseModel = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlHouseModel);
					{
						txtModelID = new _JXTextField("ID");
						pnlHouseModel.add(txtModelID, BorderLayout.WEST);
						txtModelID.setHorizontalAlignment(JXTextField.CENTER);
						txtModelID.setPreferredSize(new Dimension(100, 0));
					}
					{
						txtModelName = new _JXTextField("Model Name");
						pnlHouseModel.add(txtModelName, BorderLayout.CENTER);
						txtModelName.setHorizontalAlignment(JXTextField.LEFT);
					}
				}
				{
					pnlClientClass = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlClientClass);
					{
						lookupClientClass = new _JLookup(null, "Select Client Class", 0);
						pnlClientClass.add(lookupClientClass, BorderLayout.WEST);
						lookupClientClass.setPreferredSize(new Dimension(100, 0));
						lookupClientClass.addLookupListener(new LookupListener() {
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!= null){
									txtClientClass.setText((String) data[1]);
									lookupPmtScheme.setLookupSQL(_OtherRequest2.sqlPmtScheme("06", lookupUnit.getValue(), (String) data[0], od.getDataOld()));
									lookupPmtScheme.setValue(null);
									txtPmtScheme.setText("");
									FncSystem.out("Display SQL for the Buyer Type", lookupClientClass.getLookupSQL());
								}
							}
						});
					}
					{
						txtClientClass = new _JXTextField("Client Class Name");
						pnlClientClass.add(txtClientClass, BorderLayout.CENTER);
					}
				}
				{
					pnlPmtScheme = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlPmtScheme);
					{
						lookupPmtScheme = new _JLookup(null, "Select Payment Scheme", 0);
						pnlPmtScheme.add(lookupPmtScheme, BorderLayout.WEST);
						lookupPmtScheme.setPreferredSize(new Dimension(100, 0));
						lookupPmtScheme.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!=null){
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
									String final_pmt_scheme = (String) data3[9];
									Date old_dp_duedate = (Date) data3[38];
									
									txtPmtScheme.setText((String) data[1]);
									setDPMATerms(data3);
									//computeOnDiscRate();
									computeAmounts();
									//setDPMAAmt(data3);
									txtIntRate.setValue(_OtherRequest2.setNewIntRate(final_pmt_scheme));
									//computeOnDiscRate(od.getDataOld(), getDataTransferUnit());
									//XXX SET DEFAULT DP/MA DATE
									//checkFullDP(old_dp_duedate);
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
					txtSellPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT); 
					pnlEast.add(txtSellPrice);
					txtSellPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtSellPrice.setValue(new BigDecimal("0.00"));
					txtSellPrice.setEditable(false);
					/*txtSellPrice.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e){
							Object [] data3 =_OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
							setDefaultAmounts(data3);
						}
					});*/
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
								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
								computeOnDiscAmt(data3);
							}
						});*/
					}
					{
						lblDiscRate = new JLabel("Disc Rate", JLabel.TRAILING);
						pnlDiscount.add(lblDiscRate, BorderLayout.CENTER);
					}
					{
						txtDiscRate = new _JXFormattedTextField(JXTextField.RIGHT);
						pnlDiscount.add(txtDiscRate, BorderLayout.EAST);
						txtDiscRate.setPreferredSize(new Dimension(100, 0));
						txtDiscRate.setValue(new BigDecimal("0.00"));
						txtDiscRate.setEditable(false);
						/*txtDiscRate.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								computeOnDiscRate(od.getDataOld(), getDataTransferUnit());
							}
						});*/
					}
				}
				{
					pnlVAT = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlVAT);
					{
						txtVatAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlVAT.add(txtVatAmt, BorderLayout.WEST);
						txtVatAmt.setPreferredSize(new Dimension(100, 0));
						txtVatAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtVatAmt.setValue(new BigDecimal("0.00"));
						txtVatAmt.setEditable(false);
					}
					{
						lblVatRate = new JLabel("VAT Rate", JLabel.TRAILING);
						pnlVAT.add(lblVatRate, BorderLayout.CENTER);
					}
					{
						txtVatRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlVAT.add(txtVatRate, BorderLayout.EAST);
						txtVatRate.setPreferredSize(new Dimension(100, 0));
						txtVatRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtVatRate.setValue(new BigDecimal("0.00"));
						txtVatRate.setEditable(false);
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
								/*Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
								computeAvailedOnDP(data3);*/
								if(txtDPAmt.getValue() != null){
									computeONDPAmt();
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
						lblFireAmt = new JLabel("Fire Amount", JLabel.TRAILING);
						pnlDPTerms.add(lblFireAmt, BorderLayout.CENTER);
					}
					{
						txtFireAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDPTerms.add(txtFireAmt, BorderLayout.EAST);
						txtFireAmt.setPreferredSize(new Dimension(100, 0));
						txtFireAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtFireAmt.setValue(new BigDecimal("0.00"));
						txtFireAmt.setEditable(false);
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
								/*Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
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
								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
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
							public void actionPerformed(ActionEvent e) {
								try{
									Integer ma_term = Integer.parseInt(txtMATerm.getText());
									if(ma_term % 12 != 0){
										JOptionPane.showMessageDialog(pnlOtherReq_TransferOfUnit.this.getTopLevelAncestor(), "Please input a number divisible by twelve");
										txtMATerm.setText("");
									}
								} catch (NumberFormatException a) {}
								
							}
						});
					}
					{
						lblIntRate = new JLabel("Interest Rate", JLabel.TRAILING);
						pnlMATerms.add(lblIntRate, BorderLayout.CENTER);
					}
					{
						txtIntRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlMATerms.add(txtIntRate, BorderLayout.EAST);
						txtIntRate.setPreferredSize(new Dimension(100, 0));
						txtIntRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtIntRate.setValue(new BigDecimal("0.00"));
						txtIntRate.setEditable(false);
					}
				}
			}
		}
		
	}//END OF INIT GUI

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

	private void setDPMAAmt(Object [] data3){//REMOVE THIS IF COMPUTATIONS ARE CORRECT
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
			txtDPRate.setValue(new BigDecimal("0"));
		}
		
		String sqlgroup = "select get_group_id('"+final_entity_class+"')";
		db.select(sqlgroup);
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("03")){
			txtAvailedAmt.setEditable(false);
			txtAvailedRate.setEditable(false);
		}else{
			txtAvailedRate.setEditable(true);
			txtAvailedAmt.setEditable(true);
		}
	}

	private void computeOnDPAmt(){//XXX COMPUTES AS THE DP AMOUNT CHANGES
		BigDecimal full_perc = new BigDecimal("100.00");
		BigDecimal dp_amount = (BigDecimal) txtDPAmt.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNetSellingPrice.getValued();

		BigDecimal availed_amt = net_sell_price.subtract(dp_amount);
		BigDecimal dp_rate = (dp_amount.divide(net_sell_price)).multiply(full_perc);
		BigDecimal availed_rate = full_perc.subtract(dp_rate);

		txtAvailedAmt.setValue(availed_amt);
		txtDPRate.setValue(dp_rate);
		txtAvailedRate.setValue(availed_rate);
	}

	private void computeOnDPRate(){//COMPUTES AS THE DP RATE CHANGES //REMOVE THIS WHEN COMPUTATIONS ARE CORRECT
		BigDecimal dp_rate = (BigDecimal) txtDPRate.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNetSellingPrice.getValued();
		Double dp_amt = 0.00;
		Double availed_amt = 0.00;
		Double availed_rate = 0.00;

		if(dp_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_TransferOfUnit.this.getTopLevelAncestor(), "Invalid rate");
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

	private void computeOnDiscRate(Object [] data1, Object [] data2){ //REMOVE THIS IF COMPUTATIONS ARE CORRECT
		Object [] data3 = _OtherRequest2.setFinalVariables(data1, data2);
		BigDecimal old_sprice = (BigDecimal) data3[53];
		BigDecimal disc_rate = (BigDecimal) txtDiscRate.getValued();

		if(disc_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_TransferOfUnit.this.getTopLevelAncestor(), "Invalid Rate");
			txtDiscRate.setValue(new BigDecimal("0.00"));
		}else{
			BigDecimal disc_amt = (disc_rate.divide(new BigDecimal("100"))).multiply(old_sprice);
			txtDiscAmt.setValue(disc_amt);
			setDefaultAmounts(data3);
		}
	}
	
	private void computeOnDiscAmt(Object [] data3){//REMOVE THIS IF COMPUTATIONS ARE CORRECT
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal new_disc_amt = (BigDecimal) txtDiscAmt.getValued();
		BigDecimal final_sprice = (BigDecimal) data3[10];
		
		if(new_disc_amt.doubleValue() >= final_net_sell_price.doubleValue()){
			JOptionPane.showMessageDialog(pnlOtherReq_TransferOfUnit.this.getTopLevelAncestor(), "Invalid Amount");
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

	public void computeOnSellingPriceOnMerge(Object [] data3){///XXX DROP this if computations are correct
		//Object [] data3 = _OtherRequest2.setFinalVariables(data1, data2);
		String final_unit_id = (String) data3[5];
		pgSelect db = new pgSelect();
		String new_merge_unit_id = _OtherRequest2.getMergeUnitID(final_unit_id);
		String sql = "select coalesce(compute_selling_price('"+final_unit_id+"', (select model_id from mf_unit_info where unit_id = '"+final_unit_id+"')), 0.00) + "+
				"coalesce(compute_selling_price('"+new_merge_unit_id+"', (select model_id from mf_unit_infow where unit_id = '"+new_merge_unit_id+"')), 0.00)";
		db.select(sql);
		if(db.isNotNull()){
			txtSellPrice.setValue(db.getResult()[0][0]);
		}
		setDefaultAmounts(data3);
		computeDPOnAvailed(data3);
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
	
	public void computeOnAvailedRate(Object [] data3){//XXX DROP THIS IF COMPUTATIONS ARE CORRECT
		BigDecimal new_availed_rate = (BigDecimal) txtAvailedRate.getValued();
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		
		if(new_availed_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_TransferOfUnit.this.getTopLevelAncestor(), "Invalid Rate");
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

	public void setDefaultAmounts(Object [] data3){//XXX DROP THIS IF COMPUTATIONS ARE CORRECT
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
		txtVatRate.setValue(vat_rate);
		txtVatAmt.setValue(vat_amt);
		
	}
	
	public void setNewVatrate(String new_unit_id, BigDecimal final_sprice){
		pgSelect db = new pgSelect();
		String sql = "select case when vat = true then \n" + 
					 "12.00 else 0.00 end\n" + 
					 "from mf_unit_info where unit_id = '"+new_unit_id+"'";
		db.select(sql);
		BigDecimal new_vat_rate = (BigDecimal) db.getResult()[0][0];
		BigDecimal new_vat_amt = BigDecimal.valueOf(final_sprice.doubleValue() - (final_sprice.doubleValue()/(1+new_vat_rate.doubleValue())));
		txtVatRate.setValue(new_vat_rate);
		txtVatAmt.setValue(new_vat_amt);
	}

	public void checkFullDP(String old_dp_duedate){//XXX TRY TO REMOVE THIS
		if(old_dp_duedate.equals("FULL DP")){
			txtDPTerm.setEditable(false);
		}else{
			txtDPTerm.setEditable(true);
		}
	}
	
	public void computeAmounts(){ //Copy To Other Modules
		Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
		
		String final_unit_id = (String) data3[5];
		String final_entity_class = (String) data3[8];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal old_sprice = (BigDecimal) data3[36];
		BigDecimal final_nsp = (BigDecimal) data3[15];
		String final_model_id = (String) data3[6];
		BigDecimal new_sprice = (BigDecimal) data3[70];
		
		pgSelect db = new pgSelect();
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("04")){//FOR PAGIBIG ACCOUNTS
			BigDecimal disc_rate = ((BigDecimal) txtDiscRate.getValued()).divide(new BigDecimal("100"));
			BigDecimal disc_amt = new_sprice.multiply(disc_rate);
			BigDecimal nsp = new_sprice.subtract(disc_amt);
			
			String sql = "select get_hdmf_loanable_amt('"+final_unit_id+"', '"+final_model_id+"', "+nsp+")";
			
			db.select(sql);
			FncSystem.out("Display Loanable amt", sql);
			BigDecimal loanable_amt = (BigDecimal) db.getResult()[0][0];
			BigDecimal dp_amt = final_nsp.subtract(loanable_amt);
			//BigDecimal dp_rate = (dp_amt.divide((BigDecimal) txtSellPrice.getValued())).multiply(new BigDecimal("100"));
			BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue()/nsp.doubleValue())*100);
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
			BigDecimal disc_amt = ((BigDecimal) txtSellPrice.getValued()).multiply(disc_rate);
			BigDecimal nsp = ((BigDecimal) txtSellPrice.getValued()).subtract(disc_amt);
			
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
			BigDecimal disc_amt = final_sprice.multiply(disc_rate);
			BigDecimal nsp = final_sprice.subtract(disc_amt);
			
			txtDiscAmt.setValue(disc_amt);
			txtNetSellingPrice.setValue(nsp);
			txtDPAmt.setValue(nsp);
			txtDPRate.setValue(new BigDecimal("100.00"));
			txtAvailedAmt.setValue(new BigDecimal("0.00"));
			txtAvailedRate.setValue(new BigDecimal("0.00"));
		}
	}
	
	public void computeOnDiscRate(){ //Copy to Other Modules
		//BigDecimal final_nsp = (BigDecimal) data3[15];
		BigDecimal final_sprice = (BigDecimal) txtSellPrice.getValued();
		BigDecimal disc_rate = (BigDecimal) txtDiscRate.getValued();
		BigDecimal disc_amt = new BigDecimal("0.00");
		
		disc_amt = BigDecimal.valueOf(final_sprice.doubleValue()*(disc_rate.doubleValue()*.01));
		BigDecimal new_nsp = final_sprice.subtract(disc_amt);
		
		txtDiscAmt.setValue(disc_amt);
		txtNetSellingPrice.setValue(new_nsp);
		
	}
	
	public void computeSellingPrice(Object [] data3){
		String final_unit_id = (String) data3[5];
		//BigDecimal final_disc_amt = (BigDecimal) data3[11];
		//BigDecimal final_nsp =
		
		pgSelect db = new pgSelect();
		String sql = "select selling_price from mf_unit_pricing where pbl_id = getinteger('"+final_unit_id+"')::VARCHAR";
		db.select(sql);
		txtSellPrice.setValue(db.getResult()[0][0]);
		txtNetSellingPrice.setValue(db.getResult()[0][0]);
	}
	
	
	public void editTransferOfUnit(){
		lookupUnit.setEditable(true);
		lookupClientClass.setEditable(true);
		lookupPmtScheme.setEditable(true);
		txtDPAmt.setEditable(true);
		txtAvailedAmt.setEditable(true);
		txtDPTerm.setEditable(true);
		txtMATerm.setEditable(true);
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

	public Object[] getDataTransferUnit(){
		return new Object[]{"06", //req_id 0
				null, //new_entity_id 1
				null, //new_entity_lname 2
				null, //new entity_fname 3
				null, //new_entity_mname 4
				lookupUnit.getValue(), //new unit_id 5
				txtModelID.getText(), //new_house_model 6
				null, //new_agent_id 7
				lookupClientClass.getValue(),  //new_buyer_type 8
				lookupPmtScheme.getValue(), //new_pmt_scheme 9
				(BigDecimal) txtSellPrice.getValued(), //new_sprice 10
				(BigDecimal) txtDiscAmt.getValued(), //new_disc_amt 11
				(BigDecimal) txtDiscRate.getValued(), //new_disc_rate 12
				(BigDecimal) txtNetSellingPrice.getValued(), //13
				(BigDecimal) txtDPAmt.getValued(),//new dp_amt 14
				(BigDecimal) txtDPRate.getValued(), //new_dp_rate 15
				txtDPTerm.getText(),//(Integer) txtDPTerms.getValued(), //new_dp_terms //16
				(BigDecimal) txtAvailedAmt.getValued(), //17 new availed_amt
				(BigDecimal) txtAvailedRate.getValued(), //18 new_availed_rate
				txtMATerm.getText(), //(Integer) txtMATerms.getValued(), //new ma_terms //19
				null, //new_dp_due_date 20
				null, //new_ma_due_date 21
				txtlotArea.getText(), //22 new_lot_area
				(BigDecimal) txtFireAmt.getValued(), //23 new_fire_amt
				(BigDecimal) txtIntRate.getValued(), //24 new_int_rate
				(BigDecimal) txtVatAmt.getValued(), //25 new_vat_amt
				(BigDecimal) txtVatRate.getValued(),  //26 new_vat_rate
				"",
				"",
				""};
	}
	
	public boolean checkRequiredFields(){
		String required_fields = "";
		Boolean toSave = true;
		
		if(lookupUnit.getValue() == null || lookupUnit.getValue().isEmpty()){
			required_fields = required_fields + "Unit\n";
			toSave = false;
		}
		if(lookupClientClass.getValue() == null || lookupClientClass.getValue().isEmpty()){
			required_fields = required_fields + "Client Class\n";
			toSave = false;
		}
		if(lookupPmtScheme.getValue() == null || lookupPmtScheme.getValue().isEmpty()){
			required_fields= required_fields + "Payment Scheme\n";
			toSave = false;
		}
		if(txtDPTerm.getText().isEmpty() || txtDPTerm.getText().equals("")){
			required_fields = required_fields + "DP Term\n";
			toSave = false;
		}
		if(txtMATerm.getText().isEmpty() || txtMATerm.getText().equals("")){
			required_fields = required_fields + "MA Term\n";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public void displayTransferofUnit(String new_unit_id, String new_unit_desc, String new_lot_area, String new_model_id, String new_model_desc,
									 String new_entity_class, String new_entity_class_desc, String new_pmt_scheme, String new_pmt_scheme_desc,
									 BigDecimal new_sprice, BigDecimal new_disc_amt, BigDecimal new_disc_rate, BigDecimal new_dp_amt, BigDecimal new_dp_rate,
									 Integer new_dpterm, BigDecimal new_fire_amt, BigDecimal new_availed_rate, Integer new_materm, BigDecimal new_availed_amt){
		//Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferUnit());
		lookupUnit.setValue(new_unit_id);
		txtUnit.setText(new_unit_desc);
		txtlotArea.setText(new_lot_area);
		txtModelID.setText(new_model_id);
		txtModelName.setText(new_model_desc);
		lookupClientClass.setValue(new_entity_class);
		txtClientClass.setText(new_entity_class_desc);
		lookupPmtScheme.setValue(new_pmt_scheme);
		txtPmtScheme.setText(new_pmt_scheme_desc);
		txtSellPrice.setValue(new_sprice);
		txtNetSellingPrice.setValue(new_sprice);
		txtDiscAmt.setValue(new_disc_amt);
		txtDiscRate.setValue(new_disc_rate);
		txtDPAmt.setValue(new_dp_amt);
		txtDPRate.setValue(new_dp_rate);
		//txtDPTerms.setValue(new_dpterm);
		txtDPTerm.setText(new_dpterm.toString());
		txtFireAmt.setValue(new_fire_amt);
		txtAvailedRate.setValue(new_availed_rate);
		txtAvailedAmt.setValue(new_availed_amt);
		//txtMATerms.setValue(new_materm);
		txtMATerm.setText(new_materm.toString());
		setNewVatrate(new_unit_id, new_sprice);
		txtNetSellingPrice.setValue(new_sprice.subtract((new_disc_amt.add((BigDecimal) txtVatAmt.getValued()))));
		txtIntRate.setValue(_OtherRequest2.setNewIntRate(new_pmt_scheme));
		//computeOnDiscRate(od.getDataOld(), getDataTransferUnit());
		
	}
}
