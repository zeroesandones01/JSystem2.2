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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXFormattedTextField;

import FormattedTextField._JXFormattedTextField;
import interfaces._GUI;

public class dlg_ClientRequestDP1 extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(300, 190);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlDPPrincipal;
	private JLabel lblDPAmt;
	private _JXFormattedTextField txtDPAmt;
	
	private JPanel pnlProcFee;
	private JLabel lblProcFee;
	private _JXFormattedTextField txtProcFee;
	
	private JPanel pnlRPT;
	private JLabel lblRPT;
	private _JXFormattedTextField txtRPT;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;
	
	private BigDecimal dp1;
	private BigDecimal proc_fee1;
	private BigDecimal rpt_amt1;

	public dlg_ClientRequestDP1() {
		initGUI();
	}

	public dlg_ClientRequestDP1(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_ClientRequestDP1(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_ClientRequestDP1(Window owner) {
		super(owner);
		initGUI();
	}

	public dlg_ClientRequestDP1(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_ClientRequestDP1(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_ClientRequestDP1(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_ClientRequestDP1(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_ClientRequestDP1(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_ClientRequestDP1(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_ClientRequestDP1(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlg_ClientRequestDP1(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_ClientRequestDP1(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_ClientRequestDP1(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlg_ClientRequestDP1(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_ClientRequestDP1(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
				pnlCenter = new JPanel(new GridLayout(3, 1, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlDPPrincipal = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlDPPrincipal);
					{
						lblDPAmt = new JLabel("1st DP Principal");
						pnlDPPrincipal.add(lblDPAmt, BorderLayout.WEST);
						lblDPAmt.setPreferredSize(new Dimension(110, 37));
					}
					{
						txtDPAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlDPPrincipal.add(txtDPAmt, BorderLayout.CENTER);
						txtDPAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtDPAmt.setEditable(true);
						txtDPAmt.setValue(new BigDecimal("0.00"));
					}
				}
				{
					pnlProcFee = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlProcFee);
					{
						lblProcFee = new JLabel("1st Misc Fee");
						pnlProcFee.add(lblProcFee, BorderLayout.WEST);
						lblProcFee.setPreferredSize(new Dimension(110, 37));
					}
					{
						txtProcFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlProcFee.add(txtProcFee, BorderLayout.CENTER);
						txtProcFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtProcFee.setEditable(true);
						txtProcFee.setValue(new BigDecimal("0.00"));
					}
				}
				{
					pnlRPT = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlRPT);
					{
						lblRPT = new JLabel("1st RPT Amt");
						pnlRPT.add(lblRPT, BorderLayout.WEST);
						lblRPT.setPreferredSize(new Dimension(110, 37));
					}
					{
						txtRPT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlRPT.add(txtRPT, BorderLayout.CENTER);
						txtRPT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtRPT.setEditable(true);
						txtRPT.setValue(new BigDecimal("0.00"));
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
			setDP1((BigDecimal) txtDPAmt.getValued());
			setProc_fee1((BigDecimal) txtProcFee.getValued());
			setRpt_amt1((BigDecimal) txtRPT.getValued());
			dispose();
		}
		if(action.equals("Cancel")){
			setDP1(new BigDecimal("0.00"));
			setProc_fee1(new BigDecimal("0.00"));
			setRpt_amt1(new BigDecimal("0.00"));
			dispose();
		}
		if(action.equals("Escape")){
			setDP1(new BigDecimal("0.00"));
			setProc_fee1(new BigDecimal("0.00"));
			setRpt_amt1(new BigDecimal("0.00"));
			dispose();
		}
	}
	
	public BigDecimal getDP1(){
		return dp1;
	}
	
	public void setDP1(BigDecimal dp1){
		this.dp1 = dp1;
	}

	public BigDecimal getProc_fee1() {
		return proc_fee1;
	}

	public void setProc_fee1(BigDecimal proc_fee1) {
		this.proc_fee1 = proc_fee1;
	}

	public BigDecimal getRpt_amt1() {
		return rpt_amt1;
	}

	public void setRpt_amt1(BigDecimal rpt_amt1) {
		this.rpt_amt1 = rpt_amt1;
	}
	
	
	
}
