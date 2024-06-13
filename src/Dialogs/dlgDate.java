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
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DateChooser._JDateChooser;

public class dlgDate extends JDialog implements ActionListener, _GUI,ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(300, 150);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlDateFrom;
	private JLabel lblDateFrom;
	private _JDateChooser dteFrom;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;
	
	private Date dateFrom;
	private Date dateTo;
	
	
	private JPanel pnlDateTo;
	private JLabel lblDateTo;
	
	private _JDateChooser dteTo;
	private JCheckBox chckPrint;
	private JPanel pnlPrint;
	private JLabel lblPrint;
	private boolean OK;

	public dlgDate() {
		initGUI();
	}

	public dlgDate(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDate(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDate(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDate(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDate(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDate(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDate(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDate(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDate(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDate(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDate(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDate(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDate(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDate(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDate(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
				pnlCenter = new JPanel(new GridLayout(3, 3, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					
					{
						pnlPrint = new JPanel(new BorderLayout(3, 3));
						pnlCenter.add(pnlPrint);
						
						{
							lblPrint = new JLabel("Print All");
							pnlPrint.add(lblPrint,BorderLayout.WEST);
							lblPrint.setPreferredSize(new Dimension(100, 37));
						}
						{
							chckPrint = new JCheckBox();
							pnlPrint.add(chckPrint,BorderLayout.CENTER );
							chckPrint.setHorizontalAlignment(SwingConstants.LEFT);
							chckPrint.addChangeListener(this);
						}
					}
					
					pnlDateFrom = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlDateFrom);
					{
						lblDateFrom = new JLabel("Date From :");
						pnlDateFrom.add(lblDateFrom, BorderLayout.WEST);
						lblDateFrom.setPreferredSize(new Dimension(100, 37));
					}
					{
						dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlDateFrom.add(dteFrom, BorderLayout.CENTER);
					}
					{
						pnlDateTo = new JPanel(new BorderLayout(3, 3));
						pnlCenter.add(pnlDateTo);
						{
							lblDateTo = new JLabel("Date To :");
							pnlDateTo.add(lblDateTo, BorderLayout.WEST);
							lblDateTo.setPreferredSize(new Dimension(100, 37));
						}
						{
							dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlDateTo.add(dteTo, BorderLayout.CENTER);
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
		
	}//XXX END OF INIT GUI

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("OK")){
			setDateFrom(dteFrom.getDate());
			setDateTo(dteTo.getDate());
			setOK(true);
			dispose();
		}
		if(action.equals("Cancel")){
			setDateFrom(null);
			setDateTo(null);
			dispose();
		}
		if(action.equals("Escape")){
			setDateFrom(null);
			setDateTo(null);
			dispose();
		}
	}

	/**
	 * @return 
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param 
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	/**
	 * @return 
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param 
	 */
	public void setDateTo(Date DateTo) {
		this.dateTo = DateTo;
	}
	
	public Boolean getPrintAll() {
		return chckPrint.isSelected();
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (chckPrint.isSelected()) {
			lblDateFrom.setEnabled(false);
			lblDateTo.setEnabled(false);
			dteFrom.setEnabled(false);
			dteTo.setEnabled(false);
		}else{
			lblDateFrom.setEnabled(true);
			lblDateTo.setEnabled(true);
			dteFrom.setEnabled(true);
			dteTo.setEnabled(true);
		}
		
	}
	/**
	 * @return the oK
	 */
	public boolean isOK() {
		return OK;
	}

	/**
	 * @param oK the oK to set
	 */
	public void setOK(boolean oK) {
		OK = oK;
	}

}
