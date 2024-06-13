package Dialogs;

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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXTextField;

import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import interfaces._GUI;

public class dlgDM_OSBalance extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(300, 150);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlDateLastPmt;
	private JLabel lblDateLastPmt;
	private _JDateChooser dteLastPmt;
	
	private JPanel pnlOutstandingBalance;
	private JLabel lblOutstandingBalance;
	private _JXFormattedTextField txtOutstandingBalance;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;
	
	private Date dateLastPmt;
	private BigDecimal outstanding_balance;

	public dlgDM_OSBalance() {
		initGUI();
	}

	public dlgDM_OSBalance(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_OSBalance(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_OSBalance(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_OSBalance(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_OSBalance(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_OSBalance(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_OSBalance(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_OSBalance(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_OSBalance(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_OSBalance(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_OSBalance(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_OSBalance(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_OSBalance(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_OSBalance(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_OSBalance(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
				pnlCenter = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlDateLastPmt = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlDateLastPmt);
					{
						lblDateLastPmt = new JLabel("Date of Last Payment");
						pnlDateLastPmt.add(lblDateLastPmt, BorderLayout.WEST);
						lblDateLastPmt.setPreferredSize(new Dimension(150, 37));
					}
					{
						dteLastPmt = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlDateLastPmt.add(dteLastPmt, BorderLayout.CENTER);
						
					}
				}
				{//ADDED BY JLF 2016-06-28
					pnlOutstandingBalance = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlOutstandingBalance);
					{
						lblOutstandingBalance = new JLabel("Outstanding Balance");
						pnlOutstandingBalance.add(lblOutstandingBalance, BorderLayout.WEST);
						lblOutstandingBalance.setPreferredSize(new Dimension(150, 37));
					}
					{
						txtOutstandingBalance = new _JXFormattedTextField(JXTextField.RIGHT);
						pnlOutstandingBalance.add(txtOutstandingBalance);
						txtOutstandingBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtOutstandingBalance.setValue(new BigDecimal("0.00"));
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
	}//XXX END OF INIT GUI
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("OK")){
			setDateLastPmt(dteLastPmt.getDate());
			setOutstandingBalance((BigDecimal) txtOutstandingBalance.getValued());
			dispose();
		}
		if(action.equals("Cancel")){
			setDateLastPmt(null);
			setOutstandingBalance(FncBigDecimal.zeroValue());
			dispose();
		}
		if(action.equals("Escape")){
			setDateLastPmt(null);
			setOutstandingBalance(FncBigDecimal.zeroValue());
			dispose();
		}
	}
	
	/**
	 * @return the dateOfAprroval
	 */
	public Date getDateLastPmt() {
		return dateLastPmt;
	}

	/**
	 * @param dateLastPmt the dateOfAprroval to set
	 */
	public void setDateLastPmt(Date dateLastPmt) {
		this.dateLastPmt = dateLastPmt;
	}

	public BigDecimal getOutstanding_Balance() {
		return outstanding_balance;
	}

	public void setOutstandingBalance(BigDecimal outstanding_balance) {
		this.outstanding_balance = outstanding_balance;
	}

}
