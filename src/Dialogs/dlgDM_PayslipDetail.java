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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.JXTitledSeparator;

import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;

public class dlgDM_PayslipDetail extends JDialog implements ActionListener, _GUI, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(300, 280);

	private JPanel pnlMain;

	private JPanel pnlCenter;
	private JXTitledSeparator sepTotal;

	private JPanel pnlYear;
	private JLabel lblYear;
	private JComboBox cmbYear;

	private JPanel pnlMonth;
	private JLabel lblMonth;
	private JComboBox cmbMonth;

	private JPanel pnlGrossPay;
	private JLabel lblGrossPay;
	private _JXFormattedTextField txtGrossPay;

	private JPanel pnlDeductions;
	private JLabel lblDeductions;
	private _JXFormattedTextField txtDeductions;

	private JPanel pnlNetDisposableIncome;
	private JLabel lblNetDisposableIncome;
	private _JXFormattedTextField txtNetDisposableIncome;

	private JPanel pnlProjectedHDMF;
	private JLabel lblProjectedHDMF;
	private _JXFormattedTextField txtProjectedHDMF;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;

	private Integer Year;
	private Integer Month;
	private BigDecimal grossPay;
	private BigDecimal deduction;
	private BigDecimal netDisposableIncome;
	private BigDecimal projectdHDMF;

	public dlgDM_PayslipDetail() {
		initGUI();
	}

	public dlgDM_PayslipDetail(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_PayslipDetail(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_PayslipDetail(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_PayslipDetail(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_PayslipDetail(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_PayslipDetail(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_PayslipDetail(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_PayslipDetail(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_PayslipDetail(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_PayslipDetail(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_PayslipDetail(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_PayslipDetail(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_PayslipDetail(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_PayslipDetail(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_PayslipDetail(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridLayout(7, 1, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlYear = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlYear);
					{
						lblYear = new JLabel("Year");
						pnlYear.add(lblYear, BorderLayout.WEST);
						lblYear.setPreferredSize(new Dimension(60, 26));
					}
					{
						cmbYear = new JComboBox(new DefaultComboBoxModel(FncGlobal.listYear.toArray()));
						pnlYear.add(cmbYear, BorderLayout.CENTER);
					}
				}
				{
					pnlMonth = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlMonth);
					{
						lblMonth = new JLabel("Month");
						pnlMonth.add(lblMonth, BorderLayout.WEST);
						lblMonth.setPreferredSize(new Dimension(60, 26));
					}
					{
						cmbMonth = new JComboBox(FncGlobal.cmbmodelMonth);
						pnlMonth.add(cmbMonth, BorderLayout.CENTER);
					}
				}
				{
					pnlGrossPay = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlGrossPay);
					{
						lblGrossPay = new JLabel("Gross Pay");
						pnlGrossPay.add(lblGrossPay, BorderLayout.WEST);
						lblGrossPay.setPreferredSize(new Dimension(150, 35));
					}
					{
						txtGrossPay = new _JXFormattedTextField(JXTextField.RIGHT);
						pnlGrossPay.add(txtGrossPay, BorderLayout.CENTER);
						txtGrossPay.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtGrossPay.addKeyListener(this);
					}
				}
				{
					pnlDeductions = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlDeductions);
					{
						lblDeductions = new JLabel("Deductions");
						pnlDeductions.add(lblDeductions, BorderLayout.WEST);
						lblDeductions.setPreferredSize(new Dimension(150, 35));
					}
					{
						txtDeductions = new _JXFormattedTextField(JXTextField.RIGHT);
						pnlDeductions.add(txtDeductions, BorderLayout.CENTER);
						txtDeductions.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDeductions.addKeyListener(this);
					}
				}
				{
					sepTotal = new JXTitledSeparator("Total");
					pnlCenter.add(sepTotal);
				}
				{
					pnlNetDisposableIncome = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlNetDisposableIncome);
					{
						lblNetDisposableIncome = new JLabel("Net Disposable Income");
						pnlNetDisposableIncome.add(lblNetDisposableIncome, BorderLayout.WEST);
						lblNetDisposableIncome.setPreferredSize(new Dimension(150, 29));
					}
					{
						txtNetDisposableIncome = new _JXFormattedTextField(JXTextField.RIGHT);
						pnlNetDisposableIncome.add(txtNetDisposableIncome, BorderLayout.CENTER);
						txtNetDisposableIncome.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					}
				}
				{
					pnlProjectedHDMF = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlProjectedHDMF);
					{
						lblProjectedHDMF = new JLabel("Projected HDMF");
						pnlProjectedHDMF.add(lblProjectedHDMF, BorderLayout.WEST);
						lblProjectedHDMF.setPreferredSize(new Dimension(150, 35));
					}
					{
						txtProjectedHDMF = new _JXFormattedTextField(JXTextField.RIGHT);
						pnlProjectedHDMF.add(txtProjectedHDMF, BorderLayout.CENTER);
						txtProjectedHDMF.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtProjectedHDMF.setValue(new BigDecimal("0.00"));
					}
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setAlignmentX(0.5f);
					pnlNavigation.setAlignmentY(0.5f);
					pnlNavigation.setMaximumSize(new Dimension(205, 30));
					{
						btnOK = new JButton("OK");
						pnlNavigation.add(btnOK);
						btnOK.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}
		this.pack();
	}//END OF INIT GUI

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("OK")){
			setYear((Integer) cmbYear.getSelectedItem());
			setMonth(cmbMonth.getSelectedIndex() + 1);
			setGrossPay((BigDecimal) txtGrossPay.getValued());
			setDeduction((BigDecimal) txtDeductions.getValued());
			setNetDisposableIncome((BigDecimal) txtNetDisposableIncome.getValued());
			setProjectdHDMF((BigDecimal) txtProjectedHDMF.getValued());
			dispose();
		}
		if(action.equals("Cancel")){
			setYear(null);
			setMonth(null);
			setGrossPay(null);
			setDeduction(null);
			setNetDisposableIncome(null);
			setProjectdHDMF(null);
			dispose();
		}
		if(action.equals("Escape")){
			setYear(null);
			setMonth(null);
			setGrossPay(null);
			setDeduction(null);
			setNetDisposableIncome(null);
			setProjectdHDMF(null);
			dispose();
		}
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return Year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year){
		Year = year;
	}

	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return Month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		Month = month;
	}

	/**
	 * @return the grossPay
	 */
	public BigDecimal getGrossPay() {
		return grossPay;
	}

	/**
	 * @param grossPay the grossPay to set
	 */
	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	/**
	 * @return the deduction
	 */
	public BigDecimal getDeduction() {
		return deduction;
	}

	/**
	 * @param deduction the deduction to set
	 */
	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	/**
	 * @return the netDisposableIncome
	 */
	public BigDecimal getNetDisposableIncome() {
		return netDisposableIncome;
	}

	/**
	 * @param posablnetDisposableIncomeeIncome the netDisposableIncome to set
	 */
	public void setNetDisposableIncome(BigDecimal netDisposableIncome) {
		this.netDisposableIncome = netDisposableIncome;
	}

	/**
	 * @return the projectdHDMF
	 */
	public BigDecimal getProjectdHDMF() {
		return projectdHDMF;
	}

	/**
	 * @param projectdHDMF the projectdHDMF to set
	 */
	public void setProjectdHDMF(BigDecimal projectdHDMF) {
		this.projectdHDMF = projectdHDMF;
	}

	private void computeNetDisposableIncome() {
		BigDecimal gross_pay = (BigDecimal) txtGrossPay.getValued();
		if(gross_pay == null){
			gross_pay = FncBigDecimal.zeroValue();
		}

		BigDecimal deductions = (BigDecimal) txtDeductions.getValued();
		if(deductions == null){
			deductions = FncBigDecimal.zeroValue();
		}

		BigDecimal net_disposable_income = gross_pay.subtract(deductions);
		txtNetDisposableIncome.setValue(net_disposable_income);
	}

	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {
		computeNetDisposableIncome();
	}

	@Override
	public void keyTyped(KeyEvent e) { }

}
