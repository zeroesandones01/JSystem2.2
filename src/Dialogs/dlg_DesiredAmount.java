package Dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
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
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.action.ActionContainerFactory;

import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import interfaces._GUI;

public class dlg_DesiredAmount extends JDialog implements _GUI, ActionListener {

	private static final long serialVersionUID = 6406591681553500827L;
	Dimension size = new Dimension(300,100);
	String title = "Amount";

	private JPanel pnlMain;
	private _JXFormattedTextField txtAmount;
	private JLabel lblAmount;
	private JButton btnOk,btnCancel;
	private String doc_id;
	private BigDecimal desired_loan_amt;

	public dlg_DesiredAmount() {
		initGUI();
	}

	public dlg_DesiredAmount(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_DesiredAmount(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_DesiredAmount(Window owner) {
		super(owner);
		initGUI();
	}

	public dlg_DesiredAmount(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_DesiredAmount(Frame owner, String title, String doc_id) {
		super(owner, title);
		this.doc_id = doc_id;
		initGUI();
	}

	public dlg_DesiredAmount(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_DesiredAmount(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_DesiredAmount(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_DesiredAmount(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_DesiredAmount(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlg_DesiredAmount(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_DesiredAmount(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_DesiredAmount(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlg_DesiredAmount(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_DesiredAmount(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	public void initGUI() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			getContentPane().add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			//			JPanel pnlMainCenter = new JPanel(new GridLayout(1,2,3,3));
			//			pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
			//			{
			//			lblAmount = new JLabel("Desired Amount");
			//			pnlMainCenter.add(lblAmount);
			//			}
			//			{
			//				txtAmount = new _JXFormattedTextField(JXTextField.RIGHT);
			//				pnlMainCenter.add(txtAmount);
			//				
			//			}




			{
				JPanel pnlMainWest = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainWest,BorderLayout.CENTER);
				{
					lblAmount = new JLabel("Desired Amount:");
					pnlMainWest.add(lblAmount, BorderLayout.WEST);
					lblAmount.setPreferredSize(new Dimension(150, 37));
				}
				{
					txtAmount = new _JXFormattedTextField(JXTextField.RIGHT);
					pnlMainWest.add(txtAmount,BorderLayout.CENTER);
//					txtAmount.setPreferredSize(new Dimension(150,37));
					txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtAmount.setValue(new BigDecimal("0.00"));
				}
			}
			//			{
			//				JPanel pnlMainCenter = new JPanel(new BorderLayout(5,5));
			//				pnlMainCenter.setPreferredSize(new Dimension(100,50));
			//				pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
			//				
			//
			//				txtAmount = new _JXFormattedTextField(JXTextField.RIGHT);
			////				txtAmount.setPreferredSize(new Dimension(50,50));
			//				pnlMainCenter.add(txtAmount);
			//				txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
			//				txtAmount.setValue(new BigDecimal("0"));
			//			}
			{
				JPanel pnlMainSouth = new JPanel(new GridLayout(1,2,3,3));
				pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
				pnlMainSouth.setPreferredSize(new Dimension(0,30));
				{
					btnOk = new JButton("Ok");
					pnlMainSouth.add(btnOk);
					btnOk.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlMainSouth.add(btnCancel);
					btnCancel.addActionListener(this);
				}
			}
		}
		this.pack();
	}

	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("Ok")) {
			setDesired_loan_amt((BigDecimal) txtAmount.getValued());
			dispose();
		}
		if(action.equals("Cancel")) {
			setDesired_loan_amt(FncBigDecimal.zeroValue());
			dispose();
		}
		if(action.equals("Escape")) {
			setDesired_loan_amt(FncBigDecimal.zeroValue());
			dispose();
		}

	}

	public BigDecimal getDesired_loan_amt() {
		return desired_loan_amt;
	}

	public void setDesired_loan_amt(BigDecimal desired_loan_amt) {
		this.desired_loan_amt = desired_loan_amt;
	}

}
