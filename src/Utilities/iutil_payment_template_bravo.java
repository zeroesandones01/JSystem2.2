package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import components.JTBorderFactory;

public class iutil_payment_template_bravo extends JPanel {

	private static final long serialVersionUID = 4839176536034326724L;

	private static JButton btnReturn; 
	private static String strTemplateID;

	private static JScrollPane scroll;
	private static JScrollPane scrollDate;

	private static JLabel[] lblPointer; 
	private static JPanel[] panContent; 

	private static JLabel[] lblDate;
	private static JLabel[] lblPartType;
	private static JLabel[] lblAmount;

	private static JComboBox cboDate; 
	
	private static _JXFormattedTextField txtAmount;
	private static _JXFormattedTextField txtApplied; 
	private static _JXFormattedTextField txtExcess; 

	public static void setTemplateID(String template) {
		strTemplateID = template; 
	}

	public iutil_payment_template_bravo() {
		initGUI(); 
	}

	public iutil_payment_template_bravo(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public iutil_payment_template_bravo(boolean arg0) {
		super(arg0);
		initGUI(); 
	}

	public iutil_payment_template_bravo(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initGUI(); 
	}

	private void initGUI() {
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JPanel panLine = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panLine, BorderLayout.LINE_START); 
				panLine.setPreferredSize(new Dimension(250, 0));
				{
					{
						JPanel panSched = new JPanel(new BorderLayout(5, 5)); 
						panLine.add(panSched, BorderLayout.PAGE_START); 
						panSched.setPreferredSize(new Dimension(0, 60));
						panSched.setBorder(JTBorderFactory.createTitleBorder("Due Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							cboDate = new JComboBox(); 
							panSched.add(cboDate); 
							cboDate.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									try {
										iutil_payment_template_manual.setDate(cboDate.getSelectedItem().toString());
										iutil_payment_template_manual.reload();	
									} catch (NullPointerException ex) {
										
									}
								}
							});
						}
					}
					{
						JPanel panPayments = new JPanel(new BorderLayout(5, 5)); 
						panLine.add(panPayments, BorderLayout.CENTER);
						panPayments.setBorder(JTBorderFactory.createTitleBorder("Payment List", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							scroll = new JScrollPane();
							panPayments.add(scroll, BorderLayout.CENTER); 
						}
					}
				}
			}
			{
				JPanel panCenter = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					{
						JPanel panManual = new JPanel(new BorderLayout(5, 5)); 
						panCenter.add(panManual, BorderLayout.PAGE_START);
						panManual.setPreferredSize(new Dimension(0, 200));
						{
							iutil_payment_template_manual manual = new iutil_payment_template_manual(new BorderLayout(5, 5)); 
							panManual.add(manual, BorderLayout.CENTER); 
						}
					}
					{
						JPanel panLedgerPreview = new JPanel(new BorderLayout(5, 5)); 
						panCenter.add(panLedgerPreview, BorderLayout.CENTER); 
						{
							iutil_payment_template_ledger_bravo preview = new iutil_payment_template_ledger_bravo(new BorderLayout(5, 5));
							panLedgerPreview.add(preview, BorderLayout.CENTER); 
						}
					}
					{
						JPanel panEnd = new JPanel(new BorderLayout(5, 5)); 
						panCenter.add(panEnd, BorderLayout.PAGE_END);
						panEnd.setPreferredSize(new Dimension(0, 60));
						{
							{
								JPanel panExcess = new JPanel(new GridLayout(1, 6, 5, 5)); 
								panEnd.add(panExcess, BorderLayout.CENTER); 
								panExcess.setBorder(JTBorderFactory.createTitleBorder("Excess", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										JLabel label = new JLabel("Pay Amount"); 
										panExcess.add(label); 
										label.setHorizontalAlignment(JLabel.CENTER);
									}
									{
										txtAmount = new _JXFormattedTextField("0.00");
										panExcess.add(txtAmount);
										txtAmount.setHorizontalAlignment(JTextField.TRAILING);
										txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtAmount.setEnabled(true);
										txtAmount.setEditable(false);
									}
									{
										JLabel label = new JLabel("Applied Amount"); 
										panExcess.add(label); 
										label.setHorizontalAlignment(JLabel.CENTER);
									}
									{
										txtApplied = new _JXFormattedTextField("0.00");
										panExcess.add(txtApplied);
										txtApplied.setHorizontalAlignment(JTextField.TRAILING);
										txtApplied.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtApplied.setEnabled(true);
										txtApplied.setEditable(false);
									}
									{
										JLabel label = new JLabel("Unapplied/Excess (+/-)"); 
										panExcess.add(label); 
										label.setHorizontalAlignment(JLabel.CENTER);
										label.setFont(FncGlobal.font11); 
									}
									{
										txtExcess = new _JXFormattedTextField("0.00");
										panExcess.add(txtExcess);
										txtExcess.setHorizontalAlignment(JTextField.TRAILING);
										txtExcess.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtExcess.setEnabled(true);
										txtExcess.setEditable(false);
									}
								}
							}
							{
								JPanel panButton = new JPanel(new BorderLayout(5, 5)); 
								panEnd.add(panButton, BorderLayout.LINE_END); 
								panButton.setPreferredSize(new Dimension(200, 0));
								{
									{
										btnReturn = new JButton("Return"); 
										panButton.add(btnReturn, BorderLayout.CENTER); 
										btnReturn.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												iutil_payment_template.switcher("alpha");
												iutil_payment_template_parameters.focus(); 
											}
										});
										btnReturn.setMnemonic(KeyEvent.VK_E);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static void focus() {
		btnReturn.requestFocus();
	}

	public static void reload() {
		reloadPayments(scroll);
	}

	private static void reloadPayments(JScrollPane scr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

		pgSelect dbExec = new pgSelect(); 
		dbExec.select("select actual_date, amount, rec_id, part_id, \n" +
				"(case when part_id = '040' then 'BMA' else 'BMALS' end) as particular \n" +
				"from rf_template_payments \n" + 
				"where template_id = "+strTemplateID+" \n" + 
				"order by trans_date, actual_date, rec_id");

		lblPointer = new JLabel[(dbExec.getRowCount()>20?dbExec.getRowCount():20)];
		panContent = new JPanel[(dbExec.getRowCount()>20?dbExec.getRowCount():20)];

		lblDate = new JLabel[(dbExec.getRowCount()>20?dbExec.getRowCount():20)];
		lblPartType = new JLabel[(dbExec.getRowCount()>20?dbExec.getRowCount():20)];
		lblAmount = new JLabel[(dbExec.getRowCount()>20?dbExec.getRowCount():20)];

		JPanel panList = new JPanel(new GridBagLayout());
		panList.setName("Jim");
		{
			GridBagConstraints c = new GridBagConstraints(); 
			c.fill = GridBagConstraints.BOTH;
			c.ipady = 10; 

			for (int x=0; x< (dbExec.getRowCount()>20?dbExec.getRowCount():20); x++) {
				c.weightx = 1;
				c.weighty = 1;
				c.gridx = 0; 
				c.gridy = x;

				try {
					panContent[x] = new JPanel(new BorderLayout(0, 0)); 
					panList.add(panContent[x], c);
					panContent[x].setName(dbExec.getResult()[x][2].toString());
					{
						if (x<dbExec.getRowCount()) {
							{
								JPanel panGrid = new JPanel(new GridLayout(1, 3, 5, 5)); 
								panContent[x].add(panGrid, BorderLayout.CENTER);
								panGrid.setBorder(new EmptyBorder(5, 5, 5, 5));
								panGrid.setOpaque(false);
								{
									{
										lblPartType[x] = new JLabel((String) dbExec.getResult()[x][4]); 
										panGrid.add(lblPartType[x]);
										lblPartType[x].setHorizontalAlignment(JTextField.CENTER);
										lblPartType[x].addMouseListener(mouse);
										lblPartType[x].setForeground(Color.BLACK);
										lblPartType[x].setOpaque(false);
									}
									{
										_JDateChooser date = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panGrid.add(date);
										date.getCalendarButton().setVisible(false);
										date.setDate((Date) dbExec.getResult()[x][0]);
										date.addMouseListener(mouse); 
									}
									{
										_JXFormattedTextField txtPaymentAmountList = new _JXFormattedTextField("0.00");
										panGrid.add(txtPaymentAmountList);
										txtPaymentAmountList.setHorizontalAlignment(JTextField.TRAILING);
										txtPaymentAmountList.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtPaymentAmountList.setEnabled(true);
										txtPaymentAmountList.setEditable(false);

										try {
											txtPaymentAmountList.setValue(new BigDecimal(dbExec.getResult()[x][1].toString()));       
										} catch (Exception exdate) {
											txtPaymentAmountList.setValue(new BigDecimal("0.00"));
										}

										txtPaymentAmountList.addMouseListener(mouse);
									}         
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException ex) {

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
	}

	static MouseListener mouse = new MouseListener() {

		public void mouseReleased(MouseEvent arg0) {

		}

		public void mousePressed(MouseEvent arg0) {

		}

		public void mouseExited(MouseEvent arg0) {

		}

		public void mouseEntered(MouseEvent arg0) {

		}

		public void mouseClicked(MouseEvent event) {
			Object source = event.getSource(); 
			String strPayID = "";  
			
			if (source instanceof JLabel) {
				strPayID = ((JPanel) ((JLabel) source).getParent().getParent()).getName();
				fillCombo(((JPanel) ((JLabel) source).getParent().getParent()).getName().toString()); 
				resetcolor((JPanel) ((JLabel) source).getParent().getParent().getParent());
				((JLabel) source).getParent().getParent().setBackground(FncLookAndFeel.systemColor);
			} else if (source instanceof _JDateChooser) {
				strPayID = ((JPanel) ((_JDateChooser) source).getParent().getParent()).getName();
				fillCombo(((JPanel) ((_JDateChooser) source).getParent().getParent()).getName().toString()); 
				resetcolor((JPanel) ((_JDateChooser) source).getParent().getParent().getParent());
				((_JDateChooser) source).getParent().getParent().setBackground(FncLookAndFeel.systemColor);
			} else if (source instanceof _JXFormattedTextField) {
				strPayID = ((JPanel) ((_JXFormattedTextField) source).getParent().getParent()).getName();
				fillCombo(((JPanel) ((_JXFormattedTextField) source).getParent().getParent()).getName().toString()); 
				resetcolor((JPanel) ((_JXFormattedTextField) source).getParent().getParent().getParent());
				((_JXFormattedTextField) source).getParent().getParent().setBackground(FncLookAndFeel.systemColor);
			}
			
			iutil_payment_template_manual.setPayID(strPayID);
			iutil_payment_template_manual.setTemplateID(strTemplateID);
			iutil_payment_template_manual.reload();
			
			pgSelect dbExcess = new pgSelect(); 
			dbExcess.select("select y.amount as pay, sum(x.amount) as applied, y.amount-sum(x.amount) as excess \n" + 
					"from rf_template_client_ledger x \n" + 
					"inner join rf_template_payments y on x.pay_rec_id::int = y.rec_id and x.template_id = y.template_id \n" + 
					"where x.template_id::int = '"+strTemplateID+"'::int and x.pay_rec_id::int = '"+strPayID+"'::int and x.status_id = 'A' \n" + 
					"group by y.amount");
			iutil_payment_template_bravo.setExcess((BigDecimal) dbExcess.Result[0][0], (BigDecimal) dbExcess.Result[0][1], (BigDecimal) dbExcess.Result[0][2]);
		}
	};

	private static void resetcolor(JPanel panel) {
		for (java.awt.Component component : panel.getComponents()) {
			JPanel containers = ((JPanel) component); 

			containers.setBackground(null);
			containers.setOpaque(true);
			containers.revalidate();
		}
	}

	private static void fillCombo(String strRec) {
		pgSelect dbExec = new pgSelect(); 
		dbExec.select("select distinct scheddate::date " + 
				"from rf_template_client_ledger " + 
				"where pay_rec_id::int = '"+strRec+"'::int " + 
				"group by scheddate::date " + 
				"order by scheddate::date");

		cboDate.removeAllItems();
		
		try {
			for (int x=0; x<dbExec.getRowCount(); x++) {
				cboDate.addItem(dbExec.Result[x][0].toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void setExcess(BigDecimal pay, BigDecimal applied, BigDecimal excess) {
		txtAmount.setValue(pay);
		txtApplied.setValue(applied);
		txtExcess.setValue(excess);
	}
}