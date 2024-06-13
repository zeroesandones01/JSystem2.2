package Utilities;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

public class iutil_payment_template_parameters extends JPanel {

	private static final long serialVersionUID = 8394922340520287333L; 
	
	private static _JLookup txtTemplateNo; 

	private static _JXFormattedTextField txtProcFee;
	private static _JXFormattedTextField txtMRI;
	private static _JXFormattedTextField txtFire;
	private static _JXFormattedTextField txtVAT;
	private static _JXFormattedTextField txtIntRate;
	private static _JXFormattedTextField txtBalance;
	private static _JXFormattedTextField txtMA;
	private static _JXFormattedTextField txtPenRate;
	private static _JXFormattedTextField txtGracePeriod; 
	
	private static _JDateChooser dteStart;
	private static _JDateChooser dteEnd;
	private static _JDateChooser dteSixtieth;
	
	private JPanel panButtonSet1; 
	private CardLayout card = new CardLayout();
	
	/*	Setter	*/
	private void setProcFee(Integer template) {
		txtTemplateNo.setValue(template.toString());
	}
	
	private void setProcFee(BigDecimal procfree) {
		txtProcFee.setValue(procfree);
	}

	private void setMRI(BigDecimal mri) {
		txtMRI.setValue(mri);
	}

	private void setFire(BigDecimal fire) {
		txtFire.setValue(fire);
	}

	private void setVAT(BigDecimal vat) {
		txtVAT.setValue(vat);
	}
	
	private void setIntRate(BigDecimal intrate) {
		txtIntRate.setValue(intrate);
	}
	
	private void setBalance(BigDecimal balance) {
		txtBalance.setValue(balance);
	}
	
	private void setMA(BigDecimal ma) {
		txtMA.setValue(ma);
	}

	private void setStart(Date start) {
		dteStart.setDate(start);
	}

	private void setEnd(Date end) {
		dteStart.setDate(end);
	}
	
	private void setSixtieth(Date sixtieth) {
		dteStart.setDate(sixtieth);
	}
	
	/*	Getter	*/
	public static Integer getTemplateNo() {
		return new Integer(txtTemplateNo.getValue());   
	}
	
	public static BigDecimal getProcFee() {
		return new BigDecimal(txtProcFee.getValue().toString()); 
	}

	public static BigDecimal getMRI() {
		return new BigDecimal(txtMRI.getValue().toString());
	}

	public static BigDecimal getFire() {
		return new BigDecimal(txtFire.getValue().toString());
	}

	public static BigDecimal getVAT() {
		return new BigDecimal(txtFire.getValue().toString());
	}
	
	public static BigDecimal getIntRate() {
		return new BigDecimal(txtIntRate.getValue().toString());
	}
	
	public static BigDecimal getBalance() {
		return new BigDecimal(txtBalance.getValue().toString());
	}
	
	public static BigDecimal getMA() {
		return new BigDecimal(txtMA.getValue().toString());
	}

	public static Date getStart() {
		return dteStart.getDate(); 
	}

	public static Date getEnd() {
		return dteStart.getDate(); 
	}
	
	public static Date getSixtieth() {
		return dteStart.getDate();
	}

         public static void focus() {
                   txtTemplateNo.requestFocus();
          }
	
	public iutil_payment_template_parameters() {
		initGUI(); 
	}

	public iutil_payment_template_parameters(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public iutil_payment_template_parameters(boolean arg0) {
		super(arg0);
		initGUI(); 
	}

	public iutil_payment_template_parameters(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initGUI(); 
	}

	private void initGUI() {
		JPanel panMain = new JPanel(new GridLayout(15, 1, 5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		{
			{
				JPanel panProcFee = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panProcFee); 
				{
					{
						JLabel label = new JLabel("Template"); 
						panProcFee.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtTemplateNo = new _JLookup(""); 
						panProcFee.add(txtTemplateNo, BorderLayout.CENTER); 
						txtTemplateNo.setReturnColumn(0);
						txtTemplateNo.setLookupSQL("select template_id, template_name, proc_fee, mri, fire, vat, int_rate, balance, total_ma_amount, \n" +
								"pen_rate, grace_period, start_date, end_date, sixtieth_year, status_id, created_by, date_created \n" + 
								"from rf_template_itsreal_payment \n" +
								"where status_id = 'A' \n" + 
								"order by template_id");
						txtTemplateNo.setHorizontalAlignment(_JLookup.CENTER);
						txtTemplateNo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								loadValues(((_JLookup) event.getSource()).getDataSet()); 
								
								iutil_payment_template_dues.setTemplateID(new Integer(txtTemplateNo.getValue().toString()));
								iutil_payment_template_dues.setGracePeriod(new Integer(txtGracePeriod.getValue().toString()));
								iutil_payment_template_dues.setIntRate(new BigDecimal(txtIntRate.getValue().toString()));
								iutil_payment_template_dues.setPenRate(new BigDecimal(txtPenRate.getValue().toString()));
								iutil_payment_template_dues.reloadDues();
								
								iutil_payment_template_addpayment.setTemplateID(txtTemplateNo.getValue().toString());
								iutil_payment_template_addpayment.enableAdd(true);
								iutil_payment_template_addpayment.clearAdd();
								iutil_payment_template_addpayment.reload(); 
								
								iutil_payment_template_ledger.setTemplateID(txtTemplateNo.getValue().toString());
								iutil_payment_template_ledger.reloadLedger();
								
								iutil_payment_template_ledger_bravo.setTemplateID(txtTemplateNo.getValue().toString());
								iutil_payment_template_ledger_bravo.reloadLedger();
								
								iutil_payment_template_bravo.setTemplateID(txtTemplateNo.getValue().toString());
								iutil_payment_template_bravo.reload();
							}
						});	
						txtTemplateNo.setValue("");
					}					
				}
			}
			{
				panMain.add(new JLabel(""));
			}
			{
				JPanel panProcFee = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panProcFee); 
				{
					{
						JLabel label = new JLabel("Proc. Fee"); 
						panProcFee.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtProcFee = new _JXFormattedTextField("0.00");
						panProcFee.add(txtProcFee, BorderLayout.CENTER);
						txtProcFee.setHorizontalAlignment(JTextField.TRAILING);
						txtProcFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtProcFee.setEnabled(true);
						txtProcFee.setEditable(false);
					}							
				}
			}
			{
				JPanel panMRI = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panMRI); 
				{
					{
						JLabel label = new JLabel("MRI"); 
						panMRI.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtMRI = new _JXFormattedTextField("0.00");
						panMRI.add(txtMRI, BorderLayout.CENTER);
						txtMRI.setHorizontalAlignment(JTextField.TRAILING);
						txtMRI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtMRI.setEnabled(true);
						txtMRI.setEditable(false);
					}							
				}
			}
			{
				JPanel panFire = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panFire); 
				{
					{
						JLabel label = new JLabel("Fire"); 
						panFire.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtFire = new _JXFormattedTextField("0.00");
						panFire.add(txtFire, BorderLayout.CENTER);
						txtFire.setHorizontalAlignment(JTextField.TRAILING);
						txtFire.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtFire.setEnabled(true);
						txtFire.setEditable(false);
					}							
				}
			}
			{
				JPanel panVAT = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panVAT); 
				{
					{
						JLabel label = new JLabel("VAT"); 
						panVAT.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtVAT = new _JXFormattedTextField("0.00");
						panVAT.add(txtVAT, BorderLayout.CENTER);
						txtVAT.setHorizontalAlignment(JTextField.TRAILING);
						txtVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtVAT.setEnabled(true);
						txtVAT.setEditable(false);
					}							
				}
			}
			{
				JPanel panIntRate = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panIntRate); 
				{
					{
						JLabel label = new JLabel("Int. Rate"); 
						panIntRate.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtIntRate = new _JXFormattedTextField("0.00");
						panIntRate.add(txtIntRate, BorderLayout.CENTER);
						txtIntRate.setHorizontalAlignment(JTextField.TRAILING);
						txtIntRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtIntRate.setEnabled(true);
						txtIntRate.setEditable(false);
					}							
				}
			}
			{
				JPanel panIntRate = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panIntRate); 
				{
					{
						JLabel label = new JLabel("Balance"); 
						panIntRate.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtBalance = new _JXFormattedTextField("0.00");
						panIntRate.add(txtBalance, BorderLayout.CENTER);
						txtBalance.setHorizontalAlignment(JTextField.TRAILING);
						txtBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtBalance.setEnabled(true);
						txtBalance.setEditable(false);
					}
				}
			}
			{
				JPanel panIntRate = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panIntRate); 
				{
					{
						JLabel label = new JLabel("MA"); 
						panIntRate.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtMA = new _JXFormattedTextField("0.00");
						panIntRate.add(txtMA, BorderLayout.CENTER);
						txtMA.setHorizontalAlignment(JTextField.TRAILING);
						txtMA.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtMA.setEnabled(true);
						txtMA.setEditable(false);
					}							
				}
			}
			{
				JPanel panPenRate = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panPenRate); 
				{
					{
						JLabel label = new JLabel("Pen. Rate"); 
						panPenRate.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtPenRate = new _JXFormattedTextField("0.00");
						panPenRate.add(txtPenRate, BorderLayout.CENTER);
						txtPenRate.setHorizontalAlignment(JTextField.TRAILING);
						txtPenRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtPenRate.setEnabled(true);
						txtPenRate.setEditable(false);
					}							
				}
			}
			{
				JPanel panGracePeriod = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panGracePeriod); 
				{
					{
						JLabel label = new JLabel("Grace P."); 
						panGracePeriod.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtGracePeriod = new _JXFormattedTextField("0");
						panGracePeriod.add(txtGracePeriod, BorderLayout.CENTER);
						txtGracePeriod.setHorizontalAlignment(JTextField.TRAILING);
						txtGracePeriod.setFormatterFactory(_JXFormattedTextField.INTEGER);
						txtGracePeriod.setEnabled(true);
						txtGracePeriod.setEditable(false);
					}							
				}
			}
			{
				JPanel panStart = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panStart); 
				{
					{
						JLabel label = new JLabel("Start"); 
						panStart.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}					
					{
						dteStart = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						panStart.add(dteStart, BorderLayout.CENTER);
						dteStart.getCalendarButton().setVisible(true);
						dteStart.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panEnd); 
				{
					{
						JLabel label = new JLabel("End"); 
						panEnd.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}					
					{
						dteEnd = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						panEnd.add(dteEnd, BorderLayout.CENTER);
						dteEnd.getCalendarButton().setVisible(true);
						dteEnd.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panEnd); 
				{
					{
						JLabel label = new JLabel("Sixtieth"); 
						panEnd.add(label, BorderLayout.LINE_START); 
						label.setPreferredSize(new Dimension(75, 0));
					}					
					{
						dteSixtieth = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						panEnd.add(dteSixtieth, BorderLayout.CENTER);
						dteSixtieth.getCalendarButton().setVisible(true);
						dteSixtieth.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
				}
			}
			
			{
				panButtonSet1 = new JPanel(card); 
				panMain.add(panButtonSet1); 
				{
					panButtonSet1.add("set1", panSet1());
					panButtonSet1.add("set2", panSet2()); 
				}
			}
		}
		
		enableCreation(false); 
		clearCreation();
	}
	
	void enableCreation(Boolean blnDo) {
		txtTemplateNo.setFocusable(!blnDo);
		txtTemplateNo.setEditable(!blnDo);

		txtProcFee.setEditable(blnDo);
		txtMRI.setEditable(blnDo);
		txtFire.setEditable(blnDo);
		txtVAT.setEditable(blnDo);
		txtIntRate.setEditable(blnDo);
		txtBalance.setEditable(blnDo);
		txtMA.setEditable(blnDo);
		txtPenRate.setEditable(blnDo);
		txtGracePeriod.setEditable(blnDo);

		if (blnDo) {
			dteStart.setEditable(true);
			dteEnd.setEditable(true);
			dteSixtieth.setEditable(true);
			
			dteStart.getCalendarButton().setVisible(true);
			dteEnd.getCalendarButton().setVisible(true);
			dteSixtieth.getCalendarButton().setVisible(true);
		} else {
			dteStart.setEditable(false);
			dteEnd.setEditable(false);
			dteSixtieth.setEditable(false);
			
			dteStart.getCalendarButton().setVisible(false);
			dteEnd.getCalendarButton().setVisible(false);
			dteSixtieth.getCalendarButton().setVisible(false);
		}
	}

	void clearCreation() {
		txtTemplateNo.setValue("");

		txtProcFee.setValue(new BigDecimal("0.00"));
		txtMRI.setValue(new BigDecimal("0.00"));
		txtFire.setValue(new BigDecimal("0.00"));
		txtVAT.setValue(new BigDecimal("0.00"));
		txtIntRate.setValue(new BigDecimal("0.00"));
		txtBalance.setValue(new BigDecimal("0.00"));
		txtMA.setValue(new BigDecimal("0.00"));
		txtPenRate.setValue(new BigDecimal("0.00"));
		txtGracePeriod.setValue(new Integer("0"));
		
		dteStart.setDate(null);
		dteEnd.setDate(null);
		dteSixtieth.setDate(null);
	}
	
	private JPanel panSet1() {
		JPanel panButton = new JPanel(new BorderLayout(5, 5)); 
		{
			JButton btnAdd = new JButton("ADD"); 
			panButton.add(btnAdd, BorderLayout.CENTER); 
			btnAdd.setFont(FncGlobal.font12);
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					card.show(panButtonSet1, "set2");
					enableCreation(true);

					txtProcFee.setValue(new BigDecimal("0"));
					txtMRI.setValue(new BigDecimal("486.00"));
					txtFire.setValue(new BigDecimal("407.76"));
					txtVAT.setValue(new BigDecimal("0"));
					txtIntRate.setValue(new BigDecimal("18"));
					txtBalance.setValue(new BigDecimal("363860.67"));
					txtMA.setValue(new BigDecimal("20319.17"));
					txtPenRate.setValue(new BigDecimal("2"));
					txtGracePeriod.setValue(new Integer("3"));
					
					dteStart.setDate(FncGlobal.dateFormat("2021-03-07"));
					dteEnd.setDate(FncGlobal.dateFormat("2022-12-07"));
					dteSixtieth.setDate(null);

					txtTemplateNo.setValue(FncGlobal.GetInteger("select coalesce(max(template_id), 0)+1 from rf_template_itsreal_payment").toString());

					iutil_payment_template_addpayment.setTemplateID(txtTemplateNo.getValue().toString());
					iutil_payment_template_addpayment.enableAdd(true);
					iutil_payment_template_addpayment.clearAdd();
					iutil_payment_template_addpayment.reload(); 
				}
			});
		}

		return panButton; 
	}
	
	private JPanel panSet2() {
		JPanel panButton = new JPanel(new GridLayout(1, 2, 5, 5));
		{
			{
				JButton btnCreate = new JButton("CREATE"); 
				panButton.add(btnCreate); 
				btnCreate.setFont(FncGlobal.font12);
				btnCreate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String str60th = ""; 
						
						try {
							str60th = new String("'").concat((dteSixtieth.getDate().toString()==null?"null":dteSixtieth.getDate().toString())).concat("'");	
						} catch (NullPointerException ex) {
							str60th = "null";
						}

						pgUpdate dbDelete = new pgUpdate(); 
						dbDelete.executeUpdate("delete from rf_template_client_schedule where template_id = "+txtTemplateNo.getValue(), false);
						dbDelete.commit();
						
						pgSelect dbCreate = new pgSelect(); 
						dbCreate.call("call sp_template_create_schedule_regular(\n" +
								txtProcFee.getValue()+", \n" +
								txtMRI.getValue()+", \n" +
								txtFire.getValue()+", \n" +
								txtVAT.getValue()+", \n" +
								txtIntRate.getValue()+", \n" +
								txtBalance.getValue()+", \n" +
								txtMA.getValue()+", \n" +
								"'"+dteStart.getDate().toString()+"'::timestamp, \n" +
								"'"+dteEnd.getDate().toString()+"'::timestamp, \n" +
								""+str60th+"::timestamp, \n" +
								txtTemplateNo.getValue()+"\n"+
								"); ");
						
						System.out.println("");
						System.out.println("call sp_template_create_schedule_regular(\n" +
								txtProcFee.getValue()+", \n" +
								txtMRI.getValue()+", \n" +
								txtFire.getValue()+", \n" +
								txtVAT.getValue()+", \n" +
								txtIntRate.getValue()+", \n" +
								txtBalance.getValue()+", \n" +
								txtMA.getValue()+", \n" +
								"'"+dteStart.getDate().toString()+"'::timestamp, \n" +
								"'"+dteEnd.getDate().toString()+"'::timestamp, \n" +
								""+str60th+"::timestamp, \n" +
								txtTemplateNo.getValue()+"\n"+
								"); ");
						
						iutil_payment_template.reloadRegular(); 
					}
				});
			}
			{
				JButton btnSave = new JButton("SAVE");
				panButton.add(btnSave); 
				btnSave.setFont(FncGlobal.font12);
				btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (JOptionPane.showConfirmDialog(null, "Save template?", "Confirm", JOptionPane.YES_NO_OPTION, 
								JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
							
							String strName = JOptionPane.showInputDialog(null, "Please enter template name", "Set Name", JOptionPane.INFORMATION_MESSAGE); 
							
							if (strName.equals("null") || strName==null) {
								strName = ""; 
							}
							
							String str60th = ""; 
							
							try {
								str60th = new String("'").concat((dteSixtieth.getDate().toString()==null?"null":dteSixtieth.getDate().toString())).concat("'");	
							} catch (NullPointerException ex) {
								str60th = "null";
							}
							
							pgUpdate dbExec = new pgUpdate(); 
							dbExec.executeUpdate("insert into rf_template_itsreal_payment(template_id, template_name, proc_fee, mri, fire, vat, int_rate, balance, total_ma_amount, pen_rate, grace_period, start_date, end_date, sixtieth_year, status_id, created_by, date_created)\n" + 
									"values ("+txtTemplateNo.getValue()+", \n" +
									"'"+strName+"', \n" +
									"'"+txtProcFee.getValue().toString()+"'::numeric(19, 2), \n" +
									"'"+txtMRI.getValue().toString()+"'::numeric(19, 2), \n" +
									"'"+txtFire.getValue().toString()+"'::numeric(19, 2), \n" +
									"'"+txtVAT.getValue().toString()+"'::numeric(19, 2), \n" +
									"'"+txtIntRate.getValue().toString()+"'::numeric(19, 2), \n" +
									"'"+txtBalance.getValue().toString()+"'::numeric(19, 2), \n" +
									"'"+txtMA.getValue().toString()+"'::numeric(19, 2), \n" +
									"'"+txtPenRate.getValue().toString()+"'::numeric(19, 2), \n" +
									"'"+txtGracePeriod.getValue().toString()+"'::integer, \n" +
									"'"+dteStart.getDate().toString()+"', \n" +
									"'"+dteEnd.getDate().toString()+"', \n" +
									""+str60th+", \n" +
									"'A', \n" +
									"'"+UserInfo.EmployeeCode+"', \n" +
									"now()); ", false);
							dbExec.commit();
							
							card.show(panButtonSet1, "set1");
							enableCreation(false);
							
							JOptionPane.showMessageDialog(null, "Template saved!");
						}
					}
				});
			}
		}

		return panButton; 
	}
	
	private void loadValues(Object[] data) {
		if (data!=null) {
			txtTemplateNo.setValue(data[0].toString());

			txtProcFee.setValue(new BigDecimal(data[2].toString()));
			txtMRI.setValue(new BigDecimal(data[3].toString()));
			txtFire.setValue(new BigDecimal(data[4].toString()));
			txtVAT.setValue(new BigDecimal(data[5].toString()));
			txtIntRate.setValue(new BigDecimal(data[6].toString()));
			txtBalance.setValue(new BigDecimal(data[7].toString()));
			txtMA.setValue(new BigDecimal(data[8].toString()));

			txtPenRate.setValue(new BigDecimal(data[9].toString()));
			txtGracePeriod.setValue(new BigDecimal(data[10].toString()));
			
			dteStart.setDate(FncGlobal.dateFormat(data[11].toString()));
			dteEnd.setDate(FncGlobal.dateFormat(data[12].toString()));
			
			try {
				dteSixtieth.setDate(FncGlobal.dateFormat(data[13].toString()));
			} catch (NullPointerException ex) {
				dteSixtieth.setDate(null);
			}
			
			
			iutil_payment_template.reloadRegular();
		}
	}
}
