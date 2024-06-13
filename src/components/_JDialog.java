package components;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXRootPane;

public class _JDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Dimension size = new Dimension(800, 400);
	public BorderLayout BORDER_LAYOUT = new BorderLayout(5, 5);

	public _JDialog() {
		initThis();
	}

	public _JDialog(Frame owner) {
		super(owner);
		initThis();
	}

	public _JDialog(Dialog owner) {
		super(owner);
		initThis();
	}

	public _JDialog(Window owner, String entity_name, String entity_id, Integer pbl_id, Integer seq_no, List<Integer> listRecIDs) {
		super(owner, entity_name);
		initThis();
	}

	public _JDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initThis();
	}

	public _JDialog(Frame owner, String title) {
		super(owner, title);
		initThis();
	}

	public _JDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		initThis();
	}

	public _JDialog(Dialog owner, String title) {
		super(owner, title);
		initThis();
	}

	public _JDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initThis();
	}

	public _JDialog(Window owner, String title) {
		super(owner, title);
		initThis();
	}

	public _JDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initThis();
	}

	public _JDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initThis();
	}

	public _JDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initThis();
	}

	public _JDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initThis();
	}

	public _JDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initThis();
	}

	public _JDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initThis();
	}
	
	private void initThis() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(this.getOwner());
		this.getRootPane().registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.equals("Dispose")){
			dispose();
		}
	}
	
	@Override
	protected JXRootPane createRootPane() {
		return new JXRootPane();
	}

	@Override
	public JXRootPane getRootPane() {
		return (JXRootPane) super.getRootPane();
	}

}
