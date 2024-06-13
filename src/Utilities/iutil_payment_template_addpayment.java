package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.postgresql.util.PSQLException;

import com.toedter.calendar.JDateChooser;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import components.JTBorderFactory;

public class iutil_payment_template_addpayment extends JPanel {

	private static final long serialVersionUID = 4490853794737393121L;

	private static JPanel panCenter;

	private static _JDateChooser dteTransaction;
	private static _JDateChooser dteActual;
	private static _JXFormattedTextField txtPaymentAmount;

	private static JButton btnAdd2;
	private static JButton btnRefresh;
	private static JButton btnExport;
	private static JButton btnManual;

	private static String strTemplateID; 
	private static JScrollPane scroll;

	private static JComboBox cboPart; 
	private static JComboBox cboDueType; 

	public static void setTemplateID(String template) {
		strTemplateID = template; 
	}

	public iutil_payment_template_addpayment() {
		initGUI();
	}

	public iutil_payment_template_addpayment(LayoutManager arg0) {
		super(arg0);
		initGUI();
	}

	public iutil_payment_template_addpayment(boolean arg0) {
		super(arg0);
		initGUI();
	}

	public iutil_payment_template_addpayment(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initGUI();
	}

	private void initGUI() {
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		{
			{
				JPanel panAddAmount = new JPanel(new GridLayout(6, 1, 5, 5)); 
				panMain.add(panAddAmount, BorderLayout.PAGE_START);
				panAddAmount.setPreferredSize(new Dimension(0, 240));
				panAddAmount.setBorder(JTBorderFactory.createTitleBorder("Add Payments", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JPanel panParticular = new JPanel(new BorderLayout(5, 5)); 
						panAddAmount.add(panParticular); 
						{
							{
								JLabel label = new JLabel("Particular"); 
								panParticular.add(label, BorderLayout.LINE_START); 
								label.setPreferredSize(new Dimension(100, 0));
							}
							{
								pgSelect dbExec = new pgSelect(); 
								dbExec.select("select concat(pay_part_id, ' - ', particulars) \n" + 
										"from mf_pay_particular \n" + 
										"where pay_part_id in ('040', '041') \n" + 
										"order by pay_part_id");

								cboPart = new JComboBox(); 
								panParticular.add(cboPart); 
								cboPart.setEnabled(false);

								for (int x=0; x<dbExec.getRowCount(); x++) {
									cboPart.addItem(dbExec.getResult()[x][0]);									
								}

								cboPart.setSelectedIndex(0);
							}
						}
					}
					{
						JPanel panTransaction = new JPanel(new BorderLayout(5, 5)); 
						panAddAmount.add(panTransaction); 
						{
							{
								JLabel label = new JLabel("Transaction"); 
								panTransaction.add(label, BorderLayout.LINE_START); 
								label.setPreferredSize(new Dimension(100, 0));
							}
							{
								dteTransaction = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panTransaction.add(dteTransaction, BorderLayout.CENTER);
								dteTransaction.getCalendarButton().setVisible(true);
								dteTransaction.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}
						}
					}
					{
						JPanel panActual = new JPanel(new BorderLayout(5, 5)); 
						panAddAmount.add(panActual); 
						{
							{
								JLabel label = new JLabel("Actual"); 
								panActual.add(label, BorderLayout.LINE_START); 
								label.setPreferredSize(new Dimension(100, 0));
							}
							{
								dteActual = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panActual.add(dteActual, BorderLayout.CENTER);
								dteActual.getCalendarButton().setVisible(true);
								dteActual.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}
						}
					}
					{
						JPanel panAmount = new JPanel(new BorderLayout(5, 5)); 
						panAddAmount.add(panAmount); 
						{
							{
								JLabel label = new JLabel("Amount"); 
								panAmount.add(label, BorderLayout.LINE_START); 
								label.setPreferredSize(new Dimension(100, 0));
							}
							{
								txtPaymentAmount = new _JXFormattedTextField("0.00");
								panAmount.add(txtPaymentAmount, BorderLayout.CENTER);
								txtPaymentAmount.setHorizontalAlignment(JTextField.TRAILING);
								txtPaymentAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtPaymentAmount.setEnabled(true);
							}	
						}
					}
					{
						JPanel panDueType = new JPanel(new BorderLayout(5, 5)); 
						panAddAmount.add(panDueType); 
						{
							{
								JLabel label = new JLabel("Due Type"); 
								panDueType.add(label, BorderLayout.LINE_START); 
								label.setPreferredSize(new Dimension(100, 0));
							}
							{
								String[] type = new String[] {
										"REGULAR",
										"MORATORIUM",
										"MIXED"
								}; 

								cboDueType = new JComboBox(type); 
								panDueType.add(cboDueType); 
								cboDueType.setEnabled(false);
								cboDueType.setSelectedIndex(0);
							}
						}
					}
					{
						btnAdd2 = new JButton("ADD"); 
						panAddAmount.add(btnAdd2); 
						btnAdd2.setFont(FncGlobal.font12);
						btnAdd2.addActionListener(actionAdd);
					}
				}
			}
			{
				panCenter = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				panCenter.setBorder(JTBorderFactory.createTitleBorder("Payment List", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					scroll = new JScrollPane(); 
					panCenter.add(scroll, BorderLayout.CENTER); 
				}
			}
			{
				JPanel panButtons = new JPanel(new GridLayout(3, 1, 5, 5)); 
				panMain.add(panButtons, BorderLayout.PAGE_END); 
				panButtons.setPreferredSize(new Dimension(0, 110));
				{
					{
						btnRefresh = new JButton("REFRESH"); 
						panButtons.add(btnRefresh); 
						btnRefresh.setFont(FncGlobal.font12);
						btnRefresh.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								iutil_payment_template_ledger.reloadLedger();
								iutil_payment_template.reloadRegular();
								iutil_payment_template_dues.reloadDues();
								iutil_payment_template_ledger_bravo.reloadLedger();
							}
						});
						btnRefresh.setMnemonic(KeyEvent.VK_E);
					}
					{
						btnExport = new JButton("PREVIEW"); 
						panButtons.add(btnExport); 
						btnExport.setFont(FncGlobal.font12);
						btnExport.setMnemonic(KeyEvent.VK_W);
						btnExport.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Map<String, Object> mapParameters1 = new HashMap<String, Object>();
								mapParameters1.put("1_template_id", strTemplateID);
								mapParameters1.put("2_template_name", FncGlobal.GetString("select template_name from rf_template_itsreal_payment where template_id = '"+strTemplateID+"'"));
								FncReport.generateReport("/Reports/rpt_template_ledger_preview.jasper", "Processing Cost Report", mapParameters1);
							}
						});
					}
					{
						btnManual = new JButton("MANUAL INPUT"); 
						panButtons.add(btnManual); 
						btnManual.setFont(FncGlobal.font12);
						btnManual.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								iutil_payment_template.switcher("bravo");
								iutil_payment_template_bravo.focus();
							}
						});
						btnManual.setMnemonic(KeyEvent.VK_M);
					}
				}
			}
		}

		enableAdd(false);
		clearAdd();
	}

	public static void reload() {
		reloadPayments(scroll);
	}

	private static void reloadPayments(JScrollPane scr) {
		pgSelect dbExec = new pgSelect(); 
		dbExec.select("select actual_date, amount, rec_id \n" + 
				"from rf_template_payments \n" + 
				"where template_id = "+strTemplateID+" \n" + 
				"order by trans_date, actual_date, rec_id");

		JPanel panList = new JPanel(new GridBagLayout()); 
		{
			GridBagConstraints c = new GridBagConstraints(); 
			c.fill = GridBagConstraints.BOTH;
			c.ipady = 10;
			c.insets = new Insets(5, 5, 5, 5); 

			for (int x=0; x< (dbExec.getRowCount()>20?dbExec.getRowCount():20); x++) {
				c.weightx = 1;
				c.weighty = 1;

				c.gridx = 0; 
				c.gridy = x; 

				JPanel panContent = new JPanel(new BorderLayout(0, 0)); 
				panList.add(panContent, c); 
				panContent.setSize(100, 20);
				{
					if (x<dbExec.getRowCount()) {
						{
							JPanel panGrid = new JPanel(new GridLayout(1, 2, 5, 5)); 
							panContent.add(panGrid, BorderLayout.CENTER); 
							{
								{
									JDateChooser dteActual = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									panGrid.add(dteActual);
									dteActual.getCalendarButton().setVisible(true);

									try {
										dteActual.setDate(FncGlobal.dateFormat(dbExec.getResult()[x][0].toString()));	
									} catch (Exception exdate) {
										dteActual.setDate(null);
									}

									dteActual.getCalendarButton().setVisible(false);
								}
								{
									_JXFormattedTextField txtPaymentAmountList = new _JXFormattedTextField("0.00");
									panGrid.add(txtPaymentAmountList, BorderLayout.CENTER);
									txtPaymentAmountList.setHorizontalAlignment(JTextField.TRAILING);
									txtPaymentAmountList.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtPaymentAmountList.setEnabled(true);
									txtPaymentAmountList.setEditable(false);

									try {
										txtPaymentAmountList.setValue(new BigDecimal(dbExec.getResult()[x][1].toString()));	
									} catch (Exception exdate) {
										txtPaymentAmountList.setValue(new BigDecimal("0.00"));
									}
								}	
							}
						}
						{ 
							JLabel label = new JLabel(FncLookAndFeel.iconRemove); 
							panContent.add(label, BorderLayout.LINE_END); 
							label.setToolTipText("Remove payment");
							label.setName(dbExec.getResult()[x][2].toString());
							label.addMouseListener(new MouseListener() {
								public void mouseReleased(MouseEvent e) {

								}

								public void mousePressed(MouseEvent e) {

								}

								public void mouseExited(MouseEvent e) {

								}

								public void mouseEntered(MouseEvent e) {

								}

								public void mouseClicked(MouseEvent e) {
									if (JOptionPane.showConfirmDialog(null, "Remove this payment?", "Confirm", 
											JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
										pgUpdate dbExec = new pgUpdate(); 
										dbExec.executeUpdate("delete from rf_template_client_ledger where rec_id = "+((JLabel) e.getSource()).getName()+"", true);
										dbExec.executeUpdate("delete from rf_template_payments where rec_id = "+((JLabel) e.getSource()).getName()+"", true);
										dbExec.commit();

										iutil_payment_template_ledger.reloadLedger();
										reload();
									}
								}
							});
						}
					}


				}
			}
		}

		scr.setViewportView(panList);

		if (dbExec.getRowCount()<7) {
			scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);			
		} else {
			scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

		scr.repaint();
		scr.revalidate();
	}

	public static void enableAdd(Boolean blnDo) {
		cboPart.setEnabled(blnDo);
		cboDueType.setEnabled(blnDo);
		dteTransaction.setEnabled(blnDo);
		dteActual.setEnabled(blnDo);
		txtPaymentAmount.setEnabled(blnDo);
		panCenter.setEnabled(blnDo);

		btnAdd2.setEnabled(blnDo);
		btnRefresh.setEnabled(blnDo);
		btnExport.setEnabled(blnDo);
		btnManual.setEnabled(blnDo);
	}

	public static void clearAdd() {
		cboPart.setSelectedIndex(0);
		cboDueType.setSelectedIndex(0);
		dteTransaction.setDate(null);
		dteActual.setDate(null);
		txtPaymentAmount.setValue(new BigDecimal("0.00"));
	}

	private ActionListener actionAdd = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(null, "Save payment?", "Confirm", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
				try {
					String strPart = cboPart.getSelectedItem().toString().substring(0, 3).toString();
					String strDueType = (cboDueType.getSelectedIndex()==0?"R":cboDueType.getSelectedIndex()==1?"M":"W");
					Boolean blnVatable = FncGlobal.GetBoolean("select coalesce(vat, 0) > 0 from rf_template_itsreal_payment where template_id = "+strTemplateID+""); 

					pgUpdate dbExec = new pgUpdate();
					dbExec.executeUpdate("insert into rf_template_payments (template_id, trans_date, actual_date, part_id, amount, due_type) \n" + 
							"values ('"+strTemplateID+"', '"+dteTransaction.getDate()+"', '"+dteActual.getDate()+"', '"+strPart+"', '"+txtPaymentAmount.getValue()+"', '"+strDueType+"')", false);
					dbExec.commit();

					String strID = FncGlobal.GetString("select max(rec_id)::varchar from rf_template_payments where template_id::int = '"+strTemplateID+"'::int; "); 

					pgSelect dbApply = new pgSelect();

					if (blnVatable) {

						if (cboDueType.getSelectedIndex()==0) {
							if (cboPart.getSelectedIndex()==1) {
								dbApply.select("select fn_template_apply_payment_ma_lumpsum_vat("+strTemplateID+", '"+dteTransaction.getDate()+"'::timestamp, '"+dteActual.getDate()+"'::timestamp, "+txtPaymentAmount.getValue()+"::numeric(19, 2), "+strID+"); ");      
							} else {
								dbApply.select("select fn_template_apply_payment_ma_advanced_vat("+strTemplateID+", '"+dteTransaction.getDate()+"'::timestamp, '"+dteActual.getDate()+"'::timestamp, "+txtPaymentAmount.getValue()+"::numeric(19, 2), "+strID+"); ");
							}
						} else if (cboDueType.getSelectedIndex()==1) {
							dbApply.select("select fn_template_apply_payment_ma_advanced_moratorium_vat("+strTemplateID+", '"+dteTransaction.getDate()+"'::timestamp, '"+dteActual.getDate()+"'::timestamp, "+txtPaymentAmount.getValue()+"::numeric(19, 2), "+strID+"); ");
						} else if (cboDueType.getSelectedIndex()==2) {

						}

					} else {

						if (cboDueType.getSelectedIndex()==0) {
							if (cboPart.getSelectedIndex()==1) {
								dbApply.select("select fn_template_apply_payment_ma_lumpsum("+strTemplateID+", '"+dteTransaction.getDate()+"'::timestamp, '"+dteActual.getDate()+"'::timestamp, "+txtPaymentAmount.getValue()+"::numeric(19, 2), "+strID+"); ");	
							} else {
								dbApply.select("select fn_template_apply_payment_ma_advanced("+strTemplateID+", '"+dteTransaction.getDate()+"'::timestamp, '"+dteActual.getDate()+"'::timestamp, "+txtPaymentAmount.getValue()+"::numeric(19, 2), "+strID+"); ");
							}
						} else if (cboDueType.getSelectedIndex()==1) {
							dbApply.select("select fn_template_apply_payment_ma_advanced_moratorium("+strTemplateID+", '"+dteTransaction.getDate()+"'::timestamp, '"+dteActual.getDate()+"'::timestamp, "+txtPaymentAmount.getValue()+"::numeric(19, 2), "+strID+"); ");
						} else if (cboDueType.getSelectedIndex()==2) {

						}

					}

					reloadPayments(scroll);	
					iutil_payment_template.reloadRegular();
					iutil_payment_template_ledger.reloadLedger();
					iutil_payment_template_dues.reloadDues();
					clearAdd();

					JOptionPane.showMessageDialog(null, "Payment applied!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please check the values.");
				}
			}
		}
	};
}
