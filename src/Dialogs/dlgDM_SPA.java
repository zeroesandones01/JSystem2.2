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

import DateChooser._JDateChooser;
import components._JXTextField;

public class dlgDM_SPA extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(500, 130);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlAttorneyInFact;
	private JLabel lblAttorneyInFact;
	private _JXTextField txtAttorneyInFact;

	private JPanel pnlSPANotaryDate;
	private JLabel lblSPANotaryDate;
	private _JDateChooser dateSPANotary;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;
	
	private String attorneyInFact;
	private Date spaNotaryDate;

	public dlgDM_SPA() {
		initGUI();
	}

	public dlgDM_SPA(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_SPA(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_SPA(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_SPA(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_SPA(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_SPA(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_SPA(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_SPA(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_SPA(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_SPA(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_SPA(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_SPA(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_SPA(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_SPA(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_SPA(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
					pnlAttorneyInFact = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlAttorneyInFact);
					{
						lblAttorneyInFact = new JLabel("Attorney In-Fact");
						pnlAttorneyInFact.add(lblAttorneyInFact, BorderLayout.WEST);
						lblAttorneyInFact.setPreferredSize(new Dimension(110, 37));
					}
					{
						txtAttorneyInFact = new _JXTextField("Attorney In-Fact Name");
						pnlAttorneyInFact.add(txtAttorneyInFact, BorderLayout.CENTER);
						txtAttorneyInFact.setEditable(true);
					}
				}
				{
					pnlSPANotaryDate = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlSPANotaryDate);
					{
						lblSPANotaryDate = new JLabel("SPA Notary Date");
						pnlSPANotaryDate.add(lblSPANotaryDate, BorderLayout.WEST);
						lblSPANotaryDate.setPreferredSize(new Dimension(110, 37));
					}
					{
						JPanel panel = new JPanel(new BorderLayout());
						pnlSPANotaryDate.add(panel, BorderLayout.CENTER);
						{
							dateSPANotary = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							panel.add(dateSPANotary, BorderLayout.WEST);
							dateSPANotary.setPreferredSize(new Dimension(120, 37));
						}
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
			setAttorneyInFact(txtAttorneyInFact.getText());
			setSPANotaryDate(dateSPANotary.getDate());
			dispose();
		}
		if(action.equals("Cancel")){
			setAttorneyInFact(null);
			setSPANotaryDate(null);
			dispose();
		}
		if(action.equals("Escape")){
			setAttorneyInFact(null);
			setSPANotaryDate(null);
			dispose();
		}
	}

	/**
	 * @return the attorneyInFact
	 */
	public String getAttorneyInFact() {
		return attorneyInFact;
	}

	/**
	 * @param attorneyInFact the attorneyInFact to set
	 */
	public void setAttorneyInFact(String attorneyInFact) {
		this.attorneyInFact = attorneyInFact;
	}

	/**
	 * @return the spaNotaryDate
	 */
	public Date getSPANotaryDate() {
		return spaNotaryDate;
	}

	/**
	 * @param spaNotaryDate the spaNotaryDate to set
	 */
	public void setSPANotaryDate(Date spaNotaryDate) {
		this.spaNotaryDate = spaNotaryDate;
	}

}
