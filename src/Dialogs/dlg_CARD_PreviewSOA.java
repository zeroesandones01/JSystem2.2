/**
 * 
 */
package Dialogs;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXFormattedTextField;

import Buyers.ClientServicing._CARD;
import Database.pgSelect;
import FormattedTextField._JXFormattedTextField;
import Functions.FncReport;
import Functions.UserInfo;

/**
 * @author JLF
 */

public class dlg_CARD_PreviewSOA extends JDialog implements ActionListener, _GUI {
	
	private static final long serialVersionUID = 2505641913855370665L;
	
	private Dimension size = new Dimension(370, 360);
	
	private JPanel pnlMain;
	
	//private JPanel pnlNorth;
	private JCheckBox chkSOA_BOI;
	
	private JPanel pnlCenter;
	private ButtonGroup btnGrpPmtInclude = new ButtonGroup();
	private JRadioButton rbtnLastNthPmt;
	private JSpinner spinnerLastNthPmt;
	private JRadioButton rbtnSOA_AllPayments;
	
	//private ButtonGroup btnGrpShowAmt = new ButtonGroup();
	private JCheckBox chkAmtToUpdate;
	private JCheckBox chkAmtToFullSettle;
	private JComboBox cmbPmtType;
	private JCheckBox chkBIR_Format;
	
	private JPanel pnlSouth;
	private JCheckBox chkSOA_BankREM;
	private ButtonGroup btnGrpTransferCost = new ButtonGroup();
	private JRadioButton rbtnTransferCostPercent;
	private _JXFormattedTextField txtTransferCostPctg;
	private JRadioButton rbtnTransferCostAmt;
	private _JXFormattedTextField txtTransferCostAmt;
	
	private JButton btnPreview;
	
	private String entity_id;
	private String proj_id;
	private String pbl_id;
	private Integer seq_no;
	private Boolean special_order;
	private Timestamp date_cut_off;
	
	public dlg_CARD_PreviewSOA() {
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Window owner) {
		super(owner);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Window owner, String title, String entity_id, String proj_id, String pbl_id, Integer seq_no, Timestamp date_cut_off, Boolean special_order) {
		super(owner, title);
		
		this.entity_id= entity_id;
		this.proj_id = proj_id;
		this.pbl_id = pbl_id;
		this.seq_no = seq_no;
		this.date_cut_off = date_cut_off;
		this.special_order = special_order;
		
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_CARD_PreviewSOA(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		/*this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		this.setResizable(false);*/
		
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.MODELESS);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		/*this.setFocusableWindowState(true);
		this.setAutoRequestFocus(true);*/
		
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridLayout(9, 1, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					chkSOA_BOI = new JCheckBox("Statement of Accounts (BIR Format)");
					pnlCenter.add(chkSOA_BOI);
					//chkSOA_BOI.setBorder(_LINE_BORDER);
				}
				{
					JPanel pnlLastNthPmt = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlLastNthPmt);
					{
						rbtnLastNthPmt = new JRadioButton("Include Payments made for the last");
						pnlLastNthPmt.add(rbtnLastNthPmt, BorderLayout.CENTER);
						btnGrpPmtInclude.add(rbtnLastNthPmt);
						rbtnLastNthPmt.addChangeListener(new ChangeListener() {
							
							@Override
							public void stateChanged(ChangeEvent e) {
								Boolean isSelected = ((JRadioButton) e.getSource()).isSelected();
								
								if(isSelected){
									spinnerLastNthPmt.setEnabled(true);
								}else{
									spinnerLastNthPmt.setEnabled(false);
									spinnerLastNthPmt.setValue(1);
								}
							}
						});
					}
					{
						JPanel pnlMonths = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlLastNthPmt.add(pnlMonths, BorderLayout.EAST);
						{
							spinnerLastNthPmt = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
							pnlMonths.add(spinnerLastNthPmt);
							spinnerLastNthPmt.setEnabled(false);
						}
						{
							JLabel lblMonths = new JLabel("months");
							pnlMonths.add(lblMonths);
						}
					}
				}
				{
					rbtnSOA_AllPayments = new JRadioButton("Incude all Payments (Full SOA)");
					pnlCenter.add(rbtnSOA_AllPayments);
					btnGrpPmtInclude.add(rbtnSOA_AllPayments);
					rbtnSOA_AllPayments.addChangeListener(new ChangeListener() {
						
						@Override
						public void stateChanged(ChangeEvent e) {
							Boolean isSelected = ((JRadioButton) e.getSource()).isSelected();
							
							if(isSelected){
								chkAmtToUpdate.setEnabled(true);
								chkAmtToFullSettle.setEnabled(true);
							}else{
								chkAmtToUpdate.setEnabled(false);
								chkAmtToFullSettle.setEnabled(false);
								chkAmtToUpdate.setSelected(false);
								chkAmtToFullSettle.setSelected(false);
							}
						}
					});
				}
				{
					chkAmtToUpdate = new JCheckBox("Show Amount to Update, if any");
					pnlCenter.add(chkAmtToUpdate);
					//btnGrpShowAmt.add(chkAmtToUpdate);
					chkAmtToUpdate.setEnabled(false);
				}
				{
					chkAmtToFullSettle = new JCheckBox("Show Amount to Full Settle, if any");
					pnlCenter.add(chkAmtToFullSettle);
					//btnGrpShowAmt.add(chkAmtToFullSettle);
					chkAmtToFullSettle.setEnabled(false);
					chkAmtToFullSettle.addChangeListener(new ChangeListener() {
						
						@Override
						public void stateChanged(ChangeEvent e) {
							Boolean isSelected = ((JCheckBox) e.getSource()).isSelected();
							
							if(isSelected){
								cmbPmtType.setEnabled(true);
							}else{
								cmbPmtType.setEnabled(true);
							}
						}
					});
				}
				{
					JPanel pnlPmtType = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlPmtType);
					{
						JLabel lblPmtType = new JLabel("Payment Type");
						pnlPmtType.add(lblPmtType, BorderLayout.WEST);
					}
					{
						cmbPmtType = new JComboBox(new String[]{"Cash", "Manager's or Cashier's Check", "Regular Check"});
						pnlPmtType.add(cmbPmtType, BorderLayout.CENTER);
						cmbPmtType.setEnabled(false);
					}
				}
//				{
//					JPanel pnlBIR = new JPanel(new BorderLayout(3, 3));
//					pnlCenter.add(pnlBIR);
//					{
//						JLabel lblBIRFormat = new JLabel("BIR Format");
//						pnlBIR.add(lblBIRFormat, BorderLayout.WEST);
//					}
//					{
//						chkBIR_Format = new JCheckBox();
//						pnlBIR.add(chkBIR_Format);
//					}
//				}
				{
					chkSOA_BankREM = new JCheckBox("SOA - Bank REM Format");
					pnlCenter.add(chkSOA_BankREM);
				}
				{
					JPanel pnlTransferCostPctg = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlTransferCostPctg);
					{
						rbtnTransferCostPercent = new JRadioButton();
						pnlTransferCostPctg.add(rbtnTransferCostPercent, BorderLayout.WEST);
						btnGrpTransferCost.add(rbtnTransferCostPercent);
					}
					{
						JPanel pnlTransferCostComponents = new JPanel(new BorderLayout(3, 3));
						pnlTransferCostPctg.add(pnlTransferCostComponents, BorderLayout.CENTER);
						{
							txtTransferCostPctg = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlTransferCostComponents.add(txtTransferCostPctg, BorderLayout.WEST);
							txtTransferCostPctg.setPreferredSize(new Dimension(150, 0));
							txtTransferCostPctg.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtTransferCostPctg.setEditable(false);
						}
						{
							JLabel lblTransferCostPctg = new JLabel("Transfer Cost %");
							pnlTransferCostComponents.add(lblTransferCostPctg, BorderLayout.CENTER);
						}
					}
				}
				{
					JPanel pnlTransferCostAmt = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlTransferCostAmt);
					{
						rbtnTransferCostAmt = new JRadioButton();
						pnlTransferCostAmt.add(rbtnTransferCostAmt, BorderLayout.WEST);
						btnGrpTransferCost.add(rbtnTransferCostAmt);
					}
					{
						JPanel pnlTransferCostAmtComponents = new JPanel(new BorderLayout(3, 3));
						pnlTransferCostAmt.add(pnlTransferCostAmtComponents, BorderLayout.CENTER);
						{
							txtTransferCostAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlTransferCostAmtComponents.add(txtTransferCostAmt, BorderLayout.WEST);
							txtTransferCostAmt.setPreferredSize(new Dimension(150, 0));
							txtTransferCostAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtTransferCostAmt.setEditable(false);
						}
						{
							JLabel lblTransferCostAmt = new JLabel("Transfer Cost Amt");
							pnlTransferCostAmtComponents.add(lblTransferCostAmt, BorderLayout.CENTER);
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview, BorderLayout.EAST);
					btnPreview.setActionCommand(btnPreview.getText());
					btnPreview.setPreferredSize(new Dimension(150, 40));
					btnPreview.addActionListener(this);
				}
			}
		}
		this.pack();
	}//XXX END OF INIT GUI

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Preview")){
			Boolean SOA_BOI_Accnts = chkSOA_BOI.isSelected();
			Boolean SOA_LastNthPmt = rbtnLastNthPmt.isSelected();
			String months = (String) String.valueOf(spinnerLastNthPmt.getValue());
			Boolean SOA_full = rbtnSOA_AllPayments.isSelected();
			Boolean to_update = chkAmtToUpdate.isSelected();
			Boolean to_full_settle = chkAmtToFullSettle.isSelected();
			/*String pmt_type = (String) cmbPmtType.getSelectedItem();
			Boolean BIR_Format = chkBIR_Format.isSelected();
			Boolean SOA_Bank_REM_Format = chkSOA_BankREM.isSelected();
			Boolean is_transfer_cost_pctg = rbtnTransferCostPercent.isSelected();
			BigDecimal transfer_cost_pctg = (BigDecimal) txtTransferCostPctg.getValued();
			Boolean is_transfer_cost_amt = rbtnTransferCostAmt.isSelected();
			BigDecimal transfer_cost_amt = (BigDecimal) txtTransferCostAmt.getValued();
			*/
			Object [] data = _CARD.displayClientInformation(entity_id, proj_id, pbl_id, seq_no, true);
			
			String entity_name = (String) data[1];
			String proj_name = (String) data[3];
			String unit_desc = (String) data[5];
			String house_model = (String) data[8];
			String buyer_type_id = (String) data[9];
			String pmt_scheme = (String) data[12];
			BigDecimal gsp = (BigDecimal) data[15];
			BigDecimal discount = (BigDecimal) (data[16] == null ? new BigDecimal("0.00"):data[16]);
			BigDecimal nsp = gsp.subtract(discount);
			BigDecimal dp = (BigDecimal) data[17];
			BigDecimal availed_amt = (BigDecimal) data[18];
			BigDecimal os_balance = (BigDecimal) data[19];
			Integer ma_terms = (Integer) data[24];
			BigDecimal int_rate = (BigDecimal) data[36];
			BigDecimal vat_amt = (BigDecimal) data[70];
			BigDecimal net_of_vat = (BigDecimal) data[73];
			
			Map<String, Object> mapSOA = new HashMap<String, Object>();
			mapSOA.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
			
			
			mapSOA.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapSOA.put("co_id", "02");
			mapSOA.put("entity_id", entity_id);
			mapSOA.put("proj_id", proj_id);
			mapSOA.put("pbl_id", pbl_id);
			mapSOA.put("seq_no", seq_no);

			mapSOA.put("entity_name", entity_name);
			mapSOA.put("proj_name", proj_name);
			mapSOA.put("unit_desc", unit_desc);
			mapSOA.put("payment_scheme", pmt_scheme);
			mapSOA.put("house_model", house_model);

			mapSOA.put("gsp", gsp);
			mapSOA.put("vat", vat_amt);
			mapSOA.put("discount", discount);
			
			if(vat_amt == new BigDecimal("0.00")) {
				mapSOA.put("nsp", nsp);
			}else {
				mapSOA.put("nsp", net_of_vat);
			}
			
			mapSOA.put("dp", dp);
			mapSOA.put("availed_amount", availed_amt);
			mapSOA.put("os_balance", os_balance);
			mapSOA.put("terms", ma_terms);
			mapSOA.put("int_rate", int_rate);
			
			mapSOA.put("type_group_id", buyer_type_id);
			mapSOA.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
			mapSOA.put("pmt_nth_month",SOA_LastNthPmt);
			mapSOA.put("months", months);
			mapSOA.put("pmt_all", SOA_full);
			mapSOA.put("date_cut_off", date_cut_off);
			mapSOA.put("to_update", to_update);
			mapSOA.put("to_full_settle", to_full_settle);
			mapSOA.put("pmt_type", cmbPmtType.getSelectedItem());
			mapSOA.put("img_hand_pointer", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "handpointer.png"));
			mapSOA.put("SOA_BOI_Accnts", SOA_BOI_Accnts);
			mapSOA.put("special_order", special_order);
			
			System.out.println("SOA_LastNthPmt:" + SOA_LastNthPmt);
			System.out.println("months:" + months);
			
			this.setFocusableWindowState(false);
			
			if (SOA_BOI_Accnts) {
				if(isITSREAL_SOA(entity_id, proj_id, pbl_id, seq_no)) {
					FncReport.generateReport("/Reports/rptStatementOfAccount_itsreal_boi.jasper", "Statement of Account", mapSOA);
				}else {
					FncReport.generateReport("/Reports/rptStatementOfAccount_boi.jasper", "Statement of Account", mapSOA);
				}
				
			} else {
				FncReport.generateReport("/Reports/rptStatementOfAccount.jasper", "Statement of Account", mapSOA);
			}
			
			/*if(SOA_full){
					
				if(to_update || to_full_settle){	
					Map<String, Object> mapFull_SOA = new HashMap<String, Object>();
					mapFull_SOA.put("entity_id", entity_id);
					mapFull_SOA.put("proj_id", proj_id);
					mapFull_SOA.put("pbl_id", pbl_id);
					mapFull_SOA.put("seq_no", seq_no);
					mapFull_SOA.put("date_cut_off", date_cut_off);
					mapFull_SOA.put("entity_name", entity_name);
					mapFull_SOA.put("proj_name", proj_name);
					mapFull_SOA.put("unit_desc", unit_desc);
					mapFull_SOA.put("company", "CENQHOME DEVELOPMENT CORPORATION");
					mapFull_SOA.put("to_update", to_update);
					mapFull_SOA.put("to_full_settle", to_full_settle);
					mapFull_SOA.put("img_hand_pointer", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "handpointer.png"));
					
					FncReport.generateReport("/Reports/rptCARD_FullSOA.jasper", "Full Statement of Account", mapFull_SOA);
				}
			}*/
		}
	}
	
	private boolean isITSREAL_SOA(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_itsreal_bir_soa where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and status_id = 'A')";
		db.select(SQL);
		
		return (boolean) db.getResult()[0][0];
	
	}

}
