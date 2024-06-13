/**
 * 
 */
package Buyers.ClientServicing;

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

import interfaces._GUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 *
 */
public class pnlOtherReq_TransferReapp extends JPanel implements _GUI {

	private static final long serialVersionUID = -5668705140610066600L;
	
	private JPanel pnlMain;
	
	private JPanel pnlWest;
	private JLabel lblCreditedPayments;
	private JLabel lblDateCancelled;
	
	private JPanel pnlCenter;
	private _JXFormattedTextField txtCreditAmount;
	private _JDateChooser dteCancelled;
	private pnlOtherRequest_OldData od;
	
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblLotArea;
	private JLabel lblHouseModel;
	private JLabel lblSellingAgent;
	private JLabel lblClientClass;
	private JLabel lblPmtScheme;
	private JLabel lblSellingPrice;
	private JLabel lblVatAmt;
	private JLabel lblDiscAmt;
	private JLabel lblNetSellPrice;
	private JLabel lblDPTerms;
	private JLabel lblMATerms;
	private JLabel lblDPAmt;
	private JLabel lblAvailedAmt;

	private JPanel pnlEast;
	
	private JPanel pnlProject;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	
	private JPanel pnlUnit;
	private _JLookup lookupUnit;
	private _JXTextField txtUnit;
	
	private JPanel pnlLotArea;
	private _JXTextField txtLotArea;
	
	private JPanel pnlHouseModel;
	private _JXTextField txtModelID;
	private _JXTextField txtModelName;
	
	private JPanel pnlSellingAgent;
	private _JLookup lookupAgent;
	private _JXTextField txtAgent;

	private JPanel pnlClientClass;
	private _JLookup lookupClientClass;
	private _JXTextField txtClientClass;

	private JPanel pnlPmtScheme;
	private _JLookup lookupPmtScheme;
	private _JXTextField txtPmtScheme;
	
	private _JXFormattedTextField txtSellingPrice;

	private JPanel pnlDiscount;
	private _JXFormattedTextField txtDiscRate;
	private JLabel lblDiscRate;
	private _JXFormattedTextField txtDiscAmt;
	
	private JPanel pnlVAT;
	private _JXFormattedTextField txtVATAmt;
	private JLabel lblVatRate;
	private _JXFormattedTextField txtVATRate;

	private _JXFormattedTextField txtNetSellingPrice;

	private JPanel pnlDownPayment;
	private _JXFormattedTextField txtDPAmt;
	private JLabel lblDPRate;
	private _JXFormattedTextField txtDPRate;

	private JPanel pnlDPTerms;
	private _JXTextField txtDPTerm;
	//private _JXFormattedTextField txtDPTerms;
	private JLabel lblIntRate;
	private _JXFormattedTextField txtIntRate;

	private JPanel pnlLoanAvailed;
	private _JXFormattedTextField txtAvailedAmt;
	private JLabel lblAvailedRate;
	private _JXFormattedTextField txtAvailedRate;

	private JPanel pnlMATerms;
	private _JXTextField txtMATerm;
	private JLabel lblFireAmt;
	private _JXFormattedTextField txtFireAmt;

	public pnlOtherReq_TransferReapp(pnlOtherRequest_OldData od) {
		this.od = od;
		initGUI();
	}

	public pnlOtherReq_TransferReapp(LayoutManager layout) {
		super(layout);
	}

	public pnlOtherReq_TransferReapp(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_TransferReapp(LayoutManager layout,
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
				pnlWest = new JPanel(new GridLayout(14, 1, 3, 3));
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
					lblSellingAgent = new JLabel("Selling Agent");
					pnlWest.add(lblSellingAgent);
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
					lblSellingPrice = new JLabel("Selling Price");
					pnlWest.add(lblSellingPrice);
				}
				{
					lblDiscAmt = new JLabel("Discount");
					pnlWest.add(lblDiscAmt);
				}
				{
					lblVatAmt = new JLabel("VAT");
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
				pnlEast = new JPanel(new GridLayout(14, 1, 3, 3));
				pnlMain.add(pnlEast, BorderLayout.CENTER);
				/*{
					pnlProject = new JPanel(new BorderLayout(5, 5));
					pnlEast.add(pnlProject);
					{
						lookupProject = new _JLookup(null, "select Project", 0);
						pnlProject.add(lookupProject, BorderLayout.WEST);
						lookupProject.setPreferredSize(new Dimension(100, 0));
						lookupProject.addLookupListener(new LookupListener() {
							
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								
								if(data != null){
									txtProject.setText((String) data[1]);
									FncSystem.out("Display sql for project", lookupProject.getLookupSQL());
								}
							}
						});
					}
					{
						txtProject = new _JXTextField();
						pnlProject.add(txtProject, BorderLayout.CENTER);
					}
				}*/
				{
					pnlUnit = new JPanel(new BorderLayout(5, 5));
					pnlEast.add(pnlUnit);
					{
						lookupUnit = new _JLookup(null, "Select Unit", 0);
						pnlUnit.add(lookupUnit, BorderLayout.WEST);
						lookupUnit.setPreferredSize(new Dimension(100, 0));
						lookupUnit.setLookupSQL(_OtherRequest2.sqlOpenUnits(od.getDataOld()));
						lookupUnit.addLookupListener(new LookupListener() {
							
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								
								if(data != null){
									txtUnit.setText((String) data[1]);
									txtModelID.setText((String) data[3]);
									txtModelName.setText((String) data[4]);
									txtLotArea.setText((String) data[5]);
									
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferReapp());
									
									String final_unit_id = (String) data3[5];
									String final_model_id = (String) data3[6];
									BigDecimal final_sprice = (BigDecimal) data3[10];
									String final_entity_class = (String) data3[8];
									String old_unit_id = (String) data3[35];
									String new_unit_id = (String) data3[65];
									
									computeNewSellingPriceOnHouseModel(data3);
									txtFireAmt.setValue(_OtherRequest2.setNewFire(new_unit_id, final_entity_class, final_unit_id, final_model_id));
									setNewVatrate(new_unit_id, final_sprice);
									FncSystem.out("Display sql for Unit", lookupUnit.getLookupSQL());
									
								}
							}
						});
					}
					{
						txtUnit = new _JXTextField();
						pnlUnit.add(txtUnit, BorderLayout.CENTER);
					}
				}
				{
					pnlLotArea = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlLotArea);
					{
						txtLotArea = new _JXTextField("Lot Area");
						pnlLotArea.add(txtLotArea, BorderLayout.WEST);
						txtLotArea.setHorizontalAlignment(JXTextField.CENTER);
						txtLotArea.setPreferredSize(new Dimension(100, 0));
					}
				}
				{
					pnlHouseModel = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlHouseModel);
					{
						txtModelID = new _JXTextField("ID");
						pnlHouseModel.add(txtModelID, BorderLayout.WEST);
						txtModelID.setHorizontalAlignment(JXTextField.CENTER);
						txtModelID.setPreferredSize(new Dimension(50, 0));
					}
					{
						txtModelName = new _JXTextField("Model Name");
						pnlHouseModel.add(txtModelName, BorderLayout.CENTER);
						txtModelName.setHorizontalAlignment(JXTextField.LEFT);
					}
				}
				{
					pnlSellingAgent = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlSellingAgent);
					{
						lookupAgent = new _JLookup(null, "Select Agent", 0);
						pnlSellingAgent.add(lookupAgent, BorderLayout.WEST);
						lookupAgent.setPreferredSize(new Dimension(100, 0));
						lookupAgent.setLookupSQL(_OtherRequest2.sqlChangeAgent("12", od.getDataOld()));
						lookupAgent.addLookupListener(new LookupListener() {
							
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								
								if(data != null){
									txtAgent.setText((String) data[1]);
									FncSystem.out("Display Sql for Agent", lookupAgent.getLookupSQL());
								}
							}
						});
					}
					{
						txtAgent = new _JXTextField("Agent Name");
						pnlSellingAgent.add(txtAgent, BorderLayout.CENTER);
					}
				}
				{
					pnlClientClass = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlClientClass);
					{
						lookupClientClass = new _JLookup(null, "Select Client Class", 0);
						pnlClientClass.add(lookupClientClass, BorderLayout.WEST);
						lookupClientClass.setPreferredSize(new Dimension(100, 0));
						lookupClientClass.setLookupSQL(_OtherRequest2.sqlClientClass("02", od.getDataOld(), null));
						lookupClientClass.addLookupListener(new LookupListener() {
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtClientClass.setText((String) data[1]);
									lookupPmtScheme.setLookupSQL(_OtherRequest2.sqlPmtScheme("02", null,(String) data[0],od.getDataOld()));
									lookupPmtScheme.setValue(null);
									txtPmtScheme.setText("");
									FncSystem.out("Display client class sql", _OtherRequest2.sqlClientClass("02", od.getDataOld(), null));
									FncSystem.out("Display pmt scheme sql", _OtherRequest2.sqlPmtScheme("02", null, (String) data[0], od.getDataOld()));
								}
							}
						});
						/*lookupClientClass.addResetListener(new ResetListener() {
							
							public void resetPerformed(ResetEvent event) {
								_JLookup lookup = (_JLookup)event.getSource();
								lookup.setValue(null);
								txtClientClass.setText("");
							}
						});*/
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
								if(data!= null){
									Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferReapp());
									String final_pmt_scheme = (String) data3[9];
									Date old_dp_duedate = (Date) data3[38];

									txtPmtScheme.setText((String) data[1]);
									setDPMATerms(data3);
									
									//computeOnDiscAmt(data3);
									computeAmounts();
									txtIntRate.setValue(_OtherRequest2.setNewIntRate(final_pmt_scheme));
									//computeOnDiscRate(od.getDataOld(), getDataChangeClientClass());
									//XXX SETDEFAULT DP MA DATE
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
					txtSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlEast.add(txtSellingPrice);
					txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtSellingPrice.setValue(new BigDecimal("0.00"));
					txtSellingPrice.setEditable(false);
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
								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeClientClass());
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
								try{
									computeOnDiscRate(od.getDataOld(), getDataChangeClientClass());
								} catch(NullPointerException a) {}
							}
						});*/
					}
				}
				{
					pnlVAT = new JPanel(new BorderLayout(3, 3));
					pnlEast.add(pnlVAT);
					{
						txtVATAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlVAT.add(txtVATAmt, BorderLayout.WEST);
						txtVATAmt.setPreferredSize(new Dimension(100, 0));
						txtVATAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtVATAmt.setValue(new BigDecimal("0.00"));
						txtVATAmt.setEditable(false);
					}
					{
						lblVatRate = new JLabel("VAT Rate", JLabel.TRAILING);
						pnlVAT.add(lblVatRate, BorderLayout.CENTER);
					}
					{
						txtVATRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlVAT.add(txtVATRate, BorderLayout.EAST);
						txtVATRate.setPreferredSize(new Dimension(100, 0));
						txtVATRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtVATRate.setValue(new BigDecimal("0.00"));
						txtVATRate.setEditable(false);
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
								//Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeClientClass());
								//computeAvailedOnDP(data3);
								//try{
								if(txtDPAmt.getValued() != null){
									computeONDPAmt();
								}else{
									txtDPAmt.setValue(new BigDecimal("0.00"));
								}
								//} catch ()
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
						txtDPRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
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
								/*Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeClientClass());
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
						txtAvailedRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtAvailedRate.setValue(new BigDecimal("0.00"));
						txtAvailedRate.setEditable(false);
						/*txtAvailedRate.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e){
								Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataChangeClientClass());
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
										JOptionPane.showMessageDialog(pnlOtherReq_TransferReapp.this.getTopLevelAncestor(), "Please input a number divisible by twelve");
										txtMATerm.setText("");
									}
								} catch (NumberFormatException a) {}
							}
						});
					}
					{
						lblFireAmt = new JLabel("Fire Amount", JLabel.TRAILING);
						pnlMATerms.add(lblFireAmt, BorderLayout.CENTER);
					}
					{
						txtFireAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlMATerms.add(txtFireAmt, BorderLayout.EAST);
						txtFireAmt.setPreferredSize(new Dimension(100, 0));
						txtFireAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtFireAmt.setValue(new BigDecimal("0.00"));
						txtFireAmt.setEditable(false);
					}
				}
			}
		}
		
	}//XXX END OF INIT GUI
	
	public void newTransferReapp(Object [] data1){
		String old_agent = (String) data1[3];
		String old_unit_id = (String) data1[5];
		
		pgSelect db = new pgSelect();
		String sql = "select get_client_name('"+old_agent+"')";
		db.select(sql);
		
		String agent_name = (String) db.getResult()[0][0];
		
		if(_OtherRequest2.isFullComm(old_unit_id)){
			lookupAgent.setValue(old_agent);
			txtAgent.setText(agent_name);
			lookupAgent.setEditable(false);
		}
	}
	
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
	
	public void computeNewSellingPriceOnHouseModel(Object [] data3){
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		BigDecimal old_sprice = (BigDecimal) data3[36];
		String old_model_id = (String) data3[40];
		BigDecimal old_model_cost = new BigDecimal("0.00");
		BigDecimal new_model_cost = new BigDecimal("0.00");
		BigDecimal selling_price = new BigDecimal("0.00");
		pgSelect db = new pgSelect();
		
		System.out.printf("Display final house model: %s%n", final_model_id);
		
		String sql = "select compute_selling_price('"+final_unit_id+"', '"+final_model_id+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for GSP", sql);
		
		BigDecimal gsp = (BigDecimal) db.getResult()[0][0];
		txtSellingPrice.setValue(gsp);
		txtNetSellingPrice.setValue(gsp);
		
		computeAmounts();
	}
	
	public void setNewVatrate(String new_unit_id, BigDecimal final_sprice){
		pgSelect db = new pgSelect();
		String sql = "select case when vat = true then \n" + 
					 "12.00 else 0.00 end\n" + 
					 "from mf_unit_info where unit_id = '"+new_unit_id+"'";
		db.select(sql);
		BigDecimal new_vat_rate = (BigDecimal) db.getResult()[0][0];
		BigDecimal new_vat_amt = BigDecimal.valueOf(final_sprice.doubleValue() - (final_sprice.doubleValue()/(1+new_vat_rate.doubleValue())));
		txtVATRate.setValue(new_vat_rate);
		txtVATAmt.setValue(new_vat_amt);
	}
	
	public void computeAmounts(){ //Copy To Other Modules
		Object [] data3 = _OtherRequest2.setFinalVariables(od.getDataOld(), getDataTransferReapp());
		
		String final_unit_id = (String) data3[5];
		String final_entity_class = (String) data3[8];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal final_nsp = (BigDecimal) data3[15];
		String final_model_id = (String) data3[6];
		BigDecimal new_sprice = (BigDecimal) data3[70];
		//System.out.printf("Display final sprice: %s%n", final_sprice);
		
		BigDecimal old_sprice = (BigDecimal) data3[36];
		
		pgSelect db = new pgSelect();
		
		if(_OtherRequest2.getGroupID(final_entity_class).equals("04")){//FOR PAGIBIG ACCOUNTS
			BigDecimal disc_rate = ((BigDecimal) txtDiscRate.getValued()).divide(new BigDecimal("100"));
			BigDecimal disc_amt = new_sprice.multiply(disc_rate);
			BigDecimal nsp = new_sprice.subtract(disc_amt);
			
			String sql = "select get_hdmf_loanable_amt('"+final_unit_id+"', '"+final_model_id+"', "+nsp+")";
			
			db.select(sql);
			FncSystem.out("Display Loanable amt", sql);
			BigDecimal loanable_amt = (BigDecimal) db.getResult()[0][0];
			BigDecimal dp_amt = nsp.subtract(loanable_amt);
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
			BigDecimal disc_amt = new_sprice.multiply(disc_rate);
			BigDecimal nsp = new_sprice.subtract(disc_amt);
			
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
			BigDecimal disc_amt = new_sprice.multiply(disc_rate);
			BigDecimal nsp = new_sprice.subtract(disc_amt);
			
			txtDiscAmt.setValue(disc_amt);
			txtNetSellingPrice.setValue(nsp);
			txtDPAmt.setValue(nsp);
			txtDPRate.setValue(new BigDecimal("100.00"));
			txtAvailedAmt.setValue(new BigDecimal("0.00"));
			txtAvailedRate.setValue(new BigDecimal("0.00"));
		}
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
	
	private void computeOnDPRate(){//COMPUTES AS THE DP RATE CHANGES
		BigDecimal dp_rate = (BigDecimal) txtDPRate.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNetSellingPrice.getValued();
		Double dp_amt = 0.00;
		Double availed_amt = 0.00;
		Double availed_rate = 0.00;

		if(dp_rate.doubleValue() >= 100){
			JOptionPane.showMessageDialog(pnlOtherReq_TransferReapp.this.getTopLevelAncestor(), "Invalid rate");
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
	
	public void editTransferReapp(){
		lookupUnit.setEditable(true);
		lookupClientClass.setEditable(true);
		lookupPmtScheme.setEditable(true);
		txtAvailedAmt.setEditable(true);
		txtDPAmt.setEditable(true);
		txtMATerm.setEditable(true);
		txtDPTerm.setEditable(true);
	}
	
	public void displayTransferReapp(String new_unit_id, String new_unit_desc,String new_lotarea, String new_model_id ,
			String new_model_name,String new_agent_id, String new_agent_name, String new_entity_class, String new_entity_class_desc, 
			String new_pmt_scheme, String new_pmt_scheme_desc, BigDecimal new_disc_amt, BigDecimal new_disc_rate, 
			BigDecimal new_dp_amt, BigDecimal new_dp_rate, Integer new_dpterm, BigDecimal new_availed_amt, BigDecimal new_availed_rate, 
			Integer new_materm, BigDecimal new_sprice, BigDecimal new_fire_amt){
		
		lookupUnit.setValue(new_unit_id);
		txtUnit.setText(new_unit_desc);
		lookupAgent.setValue(new_agent_id);
		txtAgent.setText(new_agent_name);
		lookupClientClass.setValue(new_entity_class);
		txtClientClass.setText(new_entity_class_desc);
		lookupPmtScheme.setValue(new_pmt_scheme);
		txtPmtScheme.setText(new_pmt_scheme_desc);
		txtDiscAmt.setValue(new_disc_amt);
		txtDiscRate.setValue(new_disc_rate);
		BigDecimal new_nsp = new_sprice.subtract(new_disc_amt);
		txtSellingPrice.setValue(new_sprice);
		//XXXNET SELL PRICE
		txtDPAmt.setValue(new_dp_amt);
		txtDPRate.setValue(new_dp_rate);
		//txtDPTerms.setValue(new_dpterm);
		txtDPTerm.setText(new_dpterm.toString());
		txtIntRate.setValue(_OtherRequest2.setNewIntRate(new_pmt_scheme));
		txtAvailedAmt.setValue(new_availed_amt);
		txtAvailedRate.setValue(new_availed_rate);
		//txtMATerms.setValue(new_materm);
		txtMATerm.setText(new_materm.toString());
		setNewVatrate(new_unit_id, new_sprice);
		txtModelID.setText(new_model_id);
		txtModelName.setText(new_model_name);
		txtLotArea.setText(new_lotarea);
		txtNetSellingPrice.setValue(new_sprice.subtract((new_disc_amt.add((BigDecimal) txtVATAmt.getValued()))));
		txtFireAmt.setValue(new_fire_amt);
		
		//computeOnDiscRate(od.getDataOld(), getDataChangeClientClass());
		//setDefaultAmounts(data3);
	}
	
	public boolean checkRequiredFields(){//TRY TO ADD DP AMOUNT HERE AND DP RATE
		String required_fields = "";
		Boolean toSave = true;
		
		if(lookupUnit.getValue() == null){
			required_fields = required_fields + "Unit \n";
			toSave = false;
		}
		
		/*if(lookupAgent.getValue() == null){
			required_fields = required_fields + "Agent \n";
			toSave = false;
		}*/
		
		if(lookupClientClass.getValue() == null){
			required_fields = required_fields + "Client Class \n";
			toSave = false;
		}
		if(lookupPmtScheme.getValue() == null){
			required_fields = required_fields + "Payment Scheme \n";
			toSave = false;
		}
		if(txtDPTerm.getText().isEmpty()){
			required_fields = required_fields + "DP Term \n";
			toSave = false;
		}
		if(txtMATerm.getText().isEmpty()){
			required_fields = required_fields + "MA Term \n";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	public Object[] getDataTransferReapp(){
		return new Object[]{"12", //req_id 0
				null, //new_entity_id 1
				null, //new_entity_lname 2
				null, //new entity_fname 3
				null, //new_entity_mname 4
				lookupUnit.getValue(), //new unit_id 5
				null, //new_house_model 6
				null, //new_agent_id 7
				lookupClientClass.getValue(),  //new_buyer_type 8
				lookupPmtScheme.getValue(), //new_pmt_scheme 9
				(BigDecimal) txtSellingPrice.getValued(), //new_sprice 10
				(BigDecimal) txtDiscAmt.getValued(), //new_disc_amt 11
				(BigDecimal) txtDiscRate.getValued(), //new_disc_rate 12
				(BigDecimal) txtNetSellingPrice.getValued(), //13 new_net_sell_price
				(BigDecimal) txtDPAmt.getValued(),//new dp_amt 14
				(BigDecimal) txtDPRate.getValued(), //new_dp_rate 15
				txtDPTerm.getText(),//(Integer) txtDPTerms.getValued(), //new_dp_terms //16
				(BigDecimal) txtAvailedAmt.getValued(), //17 new_availed_amt
				(BigDecimal) txtAvailedRate.getValued(), //18 new_availed_rate
				txtMATerm.getText(), //(Integer) txtMATerms.getValued(), //new ma_terms //19 
				null, //new_dp_due_date 20
				null, //new_ma_due_date 21
				"", //22 new_lot_area
				new BigDecimal("0.00"), //23 new_fire_amt
				(BigDecimal) txtIntRate.getValued(), //24 new_int_rate
				(BigDecimal) txtVATAmt.getValued(), //25 new_vat_amt
				(BigDecimal) txtVATRate.getValued(), //26 new_vat_rate
				"", //new_civil_status
				"", //new_sched_date
				"",
				""}; //new civil status
		
	}
}
