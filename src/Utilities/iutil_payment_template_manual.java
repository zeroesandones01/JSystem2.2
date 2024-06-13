package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.odftoolkit.odfdom.dom.attribute.db.DbExtensionAttribute;

import com.lowagie.text.Jpeg;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import components.JTBorderFactory;
import components._JFormattedTextField;

public class iutil_payment_template_manual extends JPanel {

	private static final long serialVersionUID = -5385241129341594291L;
	private static String strTemplateID;
	private static String strPayID;
	private static String strSched = null;
	private static JScrollPane scroll; 
	
	private static JPanel panLedger; 

	private static _JXFormattedTextField txtProcFee;
	private static _JXFormattedTextField txtMRI; 
	private static _JXFormattedTextField txtFire; 
	private static _JXFormattedTextField txtVAT; 
	
	private static _JXFormattedTextField txtSOI;
	private static _JXFormattedTextField txtSOP; 
	private static _JXFormattedTextField txtInterest; 
	private static _JXFormattedTextField txtPrincipal; 
	
	public static void setTemplateID(String template) {
		strTemplateID = template; 
	}

	public static void setPayID(String payid) {
		strPayID = payid; 
	}

	public static void setDate(String date) {
		strSched = date; 
	}

	public iutil_payment_template_manual() {
		initGUI(); 
	}

	public iutil_payment_template_manual(LayoutManager arg0) {
		super(arg0);
		initGUI(); 
	}

	public iutil_payment_template_manual(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI(); 
	}

	public iutil_payment_template_manual(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI(); 
	}

	private void initGUI() {
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			panLedger = new JPanel(new GridLayout(4, 4, 5, 5)); 
			panMain.add(panLedger, BorderLayout.CENTER);
			panLedger.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				{
					JLabel label = new JLabel("Proc. Fee"); 
					panLedger.add(label); 
					label.setHorizontalAlignment(JLabel.LEADING);
				}
				{
					txtProcFee = new _JXFormattedTextField("0.00");
					panLedger.add(txtProcFee);
					txtProcFee.setHorizontalAlignment(JTextField.TRAILING);
					txtProcFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtProcFee.setEditable(true);
					txtProcFee.addKeyListener(keykey);
				}
				{
					JLabel label = new JLabel("SOI"); 
					panLedger.add(label); 
					label.setHorizontalAlignment(JLabel.LEADING);
				}
				{
					txtSOI = new _JXFormattedTextField("0.00");
					panLedger.add(txtSOI);
					txtSOI.setHorizontalAlignment(JTextField.TRAILING);
					txtSOI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtSOI.setEditable(true);
					txtSOI.addKeyListener(keykey);
				}
				{
					JLabel label = new JLabel("MRI"); 
					panLedger.add(label); 
					label.setHorizontalAlignment(JLabel.LEADING);
				}
				{
					txtMRI = new _JXFormattedTextField("0.00");
					panLedger.add(txtMRI);
					txtMRI.setHorizontalAlignment(JTextField.TRAILING);
					txtMRI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtMRI.setEditable(true);
					txtMRI.addKeyListener(keykey);
				}
				{
					JLabel label = new JLabel("SOP"); 
					panLedger.add(label); 
					label.setHorizontalAlignment(JLabel.LEADING);
				}
				{
					txtSOP = new _JXFormattedTextField("0.00");
					panLedger.add(txtSOP);
					txtSOP.setHorizontalAlignment(JTextField.TRAILING);
					txtSOP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtSOP.setEditable(true);
					txtSOP.addKeyListener(keykey);
				}
				{
					JLabel label = new JLabel("Fire"); 
					panLedger.add(label); 
					label.setHorizontalAlignment(JLabel.LEADING);
				}
				{
					txtFire = new _JXFormattedTextField("0.00");
					panLedger.add(txtFire);
					txtFire.setHorizontalAlignment(JTextField.TRAILING);
					txtFire.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtFire.setEditable(true);
					txtFire.addKeyListener(keykey);
				}
				{
					JLabel label = new JLabel("Interest"); 
					panLedger.add(label); 
					label.setHorizontalAlignment(JLabel.LEADING);
				}
				{
					txtInterest = new _JXFormattedTextField("0.00");
					panLedger.add(txtInterest);
					txtInterest.setHorizontalAlignment(JTextField.TRAILING);
					txtInterest.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtInterest.setEditable(true);
					txtInterest.addKeyListener(keykey);
				}
				{
					JLabel label = new JLabel("VAT"); 
					panLedger.add(label); 
					label.setHorizontalAlignment(JLabel.LEADING);
				}
				{
					txtVAT = new _JXFormattedTextField("0.00");
					panLedger.add(txtVAT);
					txtVAT.setHorizontalAlignment(JTextField.TRAILING);
					txtVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtVAT.setEditable(true);
					txtVAT.addKeyListener(keykey);
				}
				{
					JLabel label = new JLabel("Principal"); 
					panLedger.add(label); 
					label.setHorizontalAlignment(JLabel.LEADING);
				}
				{
					txtPrincipal = new _JXFormattedTextField("0.00");
					panLedger.add(txtPrincipal);
					txtPrincipal.setHorizontalAlignment(JTextField.TRAILING);
					txtPrincipal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtPrincipal.setEditable(true);
					txtPrincipal.addKeyListener(keykey);
				}
			}
		}
	}

	public static void reload() {
		pgSelect dbExec = new pgSelect(); 
		dbExec.select("select * \n" + 
				"from fn_template_display_client_ledger_manual("+strTemplateID+", "+strPayID+") \n" +
				"where c_scheddate::date = '"+strSched+"'::date or '"+strSched+"' = ''; ");

		try {
			txtProcFee.setValue(new BigDecimal(dbExec.getResult()[0][2].toString()));
			txtProcFee.setName(dbExec.getResult()[0][1].toString());			
		} catch (NullPointerException e) {
			txtProcFee.setValue(new BigDecimal("0.00")); 
			txtProcFee.setName(null);
		}
		
		
		try {
			txtMRI.setValue(new BigDecimal(dbExec.getResult()[0][4].toString()));
			txtMRI.setName(dbExec.getResult()[0][3].toString());			
		} catch (NullPointerException e) {
			txtMRI.setValue(new BigDecimal("0.00"));
			txtMRI.setName(null);
		}		
	
		try {
			txtFire.setValue(new BigDecimal(dbExec.getResult()[0][6].toString()));
			txtFire.setName(dbExec.getResult()[0][5].toString());			
		} catch (NullPointerException e) {
			txtFire.setValue(new BigDecimal("0.00"));
			txtFire.setName(null);
		}
		
		try {
			txtVAT.setValue(new BigDecimal(dbExec.getResult()[0][8].toString()));
			txtVAT.setName(dbExec.getResult()[0][7].toString());			
		} catch (NullPointerException e) {
			txtVAT.setValue(new BigDecimal("0.00"));
			txtVAT.setName(null);
		}
		
		try {
			txtSOI.setValue(new BigDecimal(dbExec.getResult()[0][10].toString()));
			txtSOI.setName(dbExec.getResult()[0][9].toString());			
		} catch (NullPointerException e) {
			txtSOI.setValue(new BigDecimal("0.00"));
			txtSOI.setName(null);
		}
		
		try {
			txtSOP.setValue(new BigDecimal(dbExec.getResult()[0][12].toString()));
			txtSOP.setName(dbExec.getResult()[0][11].toString());			
		} catch (NullPointerException e) {
			txtSOP.setValue(new BigDecimal("0.00"));
			txtSOP.setName(null);
		}
		
		try {
			txtInterest.setValue(new BigDecimal(dbExec.getResult()[0][14].toString()));
			txtInterest.setName(dbExec.getResult()[0][13].toString());			
		} catch (NullPointerException e) {
			txtInterest.setValue(new BigDecimal("0.00"));
			txtInterest.setName(null);
		}
		
		try {
			txtPrincipal.setValue(new BigDecimal(dbExec.getResult()[0][16].toString()));
			txtPrincipal.setName(dbExec.getResult()[0][15].toString());			
		} catch (NullPointerException e) {
			txtPrincipal.setValue(new BigDecimal("0.00"));
			txtPrincipal.setName(null);
		}
	}
	
	private DocumentListener listener = new DocumentListener() {

		public void removeUpdate(DocumentEvent e) {
			change(e);
		}

		public void insertUpdate(DocumentEvent e) {
			change(e);
		}
		
		public void changedUpdate(DocumentEvent e) {
			change(e);
		}
		
		void change(DocumentEvent e) {
			
		}
	};
	
	private KeyListener keykey = new KeyListener() {

		public void keyTyped(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {
			
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==10) {
				Object object = e.getSource(); 
				String strID = ((_JXFormattedTextField) object).getName();
				String strAmount = new BigDecimal(((_JXFormattedTextField) object).getValue().toString()).toString();  
				
				updateLedger(strID, strAmount);
			}
		}
	};
	
	private void updateLedger(String strLedgerID, String strAmount) {
		String strPart = FncGlobal.GetString("select part_id from rf_template_client_ledger where rec_id::int = "+strLedgerID+"::int; "); 
		
		if (strAmount.equals("0")) {
			pgUpdate dbExec = new pgUpdate(); 
			dbExec.executeUpdate("update rf_template_client_ledger \n" +
					"set status_id = 'I' \n" +
					"where template_id::int = '"+strTemplateID+"'::int and pay_rec_id::int = '"+strPayID+"'::int and scheddate::date = '"+strSched+"'::date and rec_id::int = '"+strLedgerID+"'::int; ", true);
			dbExec.commit();
			
			iutil_payment_template_ledger_bravo.reloadLedger();
		} else {
			if (strPart.equals("008")) {
				pgUpdate dbExec = new pgUpdate(); 
				dbExec.executeUpdate("update rf_template_client_ledger \n" +
						"set status_id = 'I' \n" +
						"where template_id::int = '"+strTemplateID+"'::int and part_id = '008' and pay_rec_id::int = '"+strPayID+"'::int and scheddate::date = '"+strSched+"'::date; ", true);
				dbExec.commit();
				
				System.out.println();
				
				dbExec = new pgUpdate();
				dbExec.executeUpdate("insert into rf_template_client_ledger (template_id, trans_date, actual_date, scheddate, part_id, amount, status_id, pay_rec_id, due_type) \n" + 
						"select template_id, trans_date, actual_date, scheddate, part_id, '"+strAmount+"'::numeric(19, 2), 'A', '"+strPayID+"'::int, due_type \n" + 
						"from rf_template_client_ledger \n" + 
						"where rec_id::int = "+strLedgerID+"::int", true);
				dbExec.commit();
				
				iutil_payment_template_ledger_bravo.reloadLedger();
			} else {
				pgUpdate dbExec = new pgUpdate(); 
				dbExec.executeUpdate("update rf_template_client_ledger \n" +
						"set amount = '"+strAmount+"'::numeric(19, 2) \n" +
						"where template_id::int = '"+strTemplateID+"'::int and pay_rec_id::int = '"+strPayID+"'::int and rec_id = '"+strLedgerID+"'::int; ", true);
				dbExec.commit();
				
				iutil_payment_template_ledger_bravo.reloadLedger();
			}
		}
		
		pgSelect dbExcess = new pgSelect(); 
		dbExcess.select("select y.amount as pay, sum(x.amount) as applied, y.amount-sum(x.amount) as excess \n" + 
				"from rf_template_client_ledger x \n" + 
				"inner join rf_template_payments y on x.pay_rec_id::int = y.rec_id and x.template_id = y.template_id \n" + 
				"where x.template_id::int = '"+strTemplateID+"'::int and x.pay_rec_id::int = '"+strPayID+"'::int and x.status_id = 'A' \n" + 
				"group by y.amount");
		iutil_payment_template_bravo.setExcess((BigDecimal) dbExcess.Result[0][0], (BigDecimal) dbExcess.Result[0][1], (BigDecimal) dbExcess.Result[0][2]);
	}
}
