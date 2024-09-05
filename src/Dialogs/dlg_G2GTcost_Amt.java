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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.UserInfo;
import components._JDialog;
import interfaces._GUI;

public class dlg_G2GTcost_Amt extends _JDialog implements _GUI, ActionListener {
	
	private static final long serialVersionUID = -4601200124997546991L;
	
	private Dimension SIZE = new Dimension(300, 100);
	private JPanel pnlMain;
	private JPanel pnlMainLabel;
	private JPanel pnlMainComponent;
	private _JXFormattedTextField txtTcostAmt;
	private JButton btnSave;
	private Integer rec_id;
	private String entity_id;
	private String proj_id;
	private String pbl_id;
	private String seq_no;
	private String batch_no;

	public dlg_G2GTcost_Amt() {
		initGUI();
	}

	public dlg_G2GTcost_Amt(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Window owner, String entity_name, String entity_id, Integer pbl_id, Integer seq_no,
			List<Integer> listRecIDs) {
		super(owner, entity_name, entity_id, pbl_id, seq_no, listRecIDs);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Window owner, String title, Integer rec_id, String entity_id, String proj_id, String pbl_id, String seq_no, String batch_no) {
		super(owner, title);
		initGUI();
		this.rec_id = rec_id;
		this.entity_id = entity_id;
		this.proj_id = proj_id;
		this.seq_no = seq_no;
		this.batch_no = batch_no;
	}

	public dlg_G2GTcost_Amt(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_G2GTcost_Amt(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setMinimumSize(SIZE);
		
		this.getRootPane().registerKeyboardAction(this, "Add", KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			pnlMain = new JPanel(new BorderLayout(3, 3));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlMainLabel = new JPanel(new GridLayout(1, 1, 3, 3));
				pnlMain.add(pnlMainLabel, BorderLayout.WEST);
				{
					JLabel lblTcostAmt = new JLabel("Tcost Amt");
					pnlMainLabel.add(lblTcostAmt);
				}
			}
			{
				pnlMainComponent = new JPanel(new GridLayout(1, 1, 3, 3));
				pnlMain.add(pnlMainComponent, BorderLayout.CENTER);
				{
					txtTcostAmt = new _JXFormattedTextField("0.00");
					pnlMainComponent.add(txtTcostAmt);
				}
			}
		}
		{
			JPanel pnlSouth = new JPanel(new BorderLayout(3, 3));
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			{
				btnSave = new JButton("Save");
				this.add(btnSave, BorderLayout.SOUTH);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.addActionListener(this);
			}
		}
		this.pack();
		
	}//XXX END OF INIT GUI

	private Boolean toSave(){
		if(txtTcostAmt.getValued() == new BigDecimal("0.00")){
			JOptionPane.showMessageDialog(this, "Please input tcost amt", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private void updateTCostAmt() {
		BigDecimal tcost_amt = (BigDecimal) txtTcostAmt.getValued();
		
		pgSelect db = new pgSelect();
		String SQL = "select sp_update_tcost_amt_g2g('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+tcost_amt+", '"+batch_no+"', "+rec_id+", '"+UserInfo.EmployeeCode+"');";
		db.select(SQL);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Save")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this, "Save amt?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					updateTCostAmt();
					JOptionPane.showMessageDialog(this, "Tcost Amt updated saved", "Save", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				}
			}
		}
	}
	
}
