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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import components._JXTextField;

public class dlgDM_YearMonth extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(300, 200);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlYear;
	private JLabel lblYear;
	private JComboBox cmbYear;

	private JPanel pnlMonth;
	private JLabel lblMonth;
	private JComboBox cmbMonth;
	
	private JPanel pnlContractDuration;
	private JLabel lblContractDuration;
	private JSpinner spinnerMonths;
	private JTextField txtContractDuration;
	
	private JPanel pnlValidityDate;
	private JLabel lblValidityDate;
	private _JDateChooser dteValidity;
	
	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;
	
	private Integer Year;
	private Integer Month;
	private Date validUntil;
	private String doc_id;

	public dlgDM_YearMonth() {
		initGUI();
	}

	public dlgDM_YearMonth(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_YearMonth(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_YearMonth(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_YearMonth(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_YearMonth(Frame owner, String title, String doc_id) {
		super(owner, title);
		this.doc_id = doc_id;
		initGUI();
	}

	public dlgDM_YearMonth(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_YearMonth(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_YearMonth(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_YearMonth(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_YearMonth(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_YearMonth(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_YearMonth(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_YearMonth(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_YearMonth(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_YearMonth(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
				pnlCenter = new JPanel(new GridLayout(4, 1, 3, 3));
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
						cmbYear.setSelectedItem(null);
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
						cmbMonth.setSelectedItem(null);
					}
				}
				{
					pnlContractDuration = new JPanel( new BorderLayout(3, 3));
					pnlCenter.add(pnlContractDuration);
					{
						lblContractDuration = new JLabel("Contract duration in months");
						pnlContractDuration.add(lblContractDuration, BorderLayout.WEST);
						lblContractDuration.setPreferredSize(new Dimension(200, 26));
					}
					{
						txtContractDuration = new JTextField();
						pnlContractDuration.add(txtContractDuration);
						txtContractDuration.setHorizontalAlignment(JXTextField.CENTER);
						txtContractDuration.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								//try {
									if(cmbYear.getSelectedItem() != null && cmbMonth.getSelectedItem() != null && txtContractDuration.getText().equals("") == false){
										dteValidity.setDate(getValidityDate(cmbYear.getSelectedItem().toString(), cmbMonth.getSelectedItem().toString(), txtContractDuration.getText()));
									}else{
										dteValidity.setDate(null);
									}
								//} catch(NumberFormatException a) {}
							}
						});
					}
				}
				{
					pnlValidityDate = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlValidityDate);
					{
						lblValidityDate = new JLabel("Valid Until");
						pnlValidityDate.add(lblValidityDate, BorderLayout.WEST);
						lblValidityDate.setPreferredSize(new Dimension(150, 26));
					}
					{
						dteValidity = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlValidityDate.add(dteValidity, BorderLayout.CENTER);
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
	
	private Date getValidityDate(String year, String month, String validity_months){
		String date_issued = String.format("%s-%s-%s", year, month, "01");
		String validity_from_months = String.format("%s months", validity_months);
		
		Date validity_date = null;
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT (DATE_TRUNC('MONTH', (select (DATE_TRUNC('MONTH', TIMESTAMP '"+date_issued+"') + INTERVAL '"+validity_from_months+"')) ) + INTERVAL '1 month - 1 day')::date;";
		db.select(SQL);
		FncSystem.out("Display value of doc validity:", SQL);
		
		if(db.isNotNull()){
			validity_date = (Date) db.getResult()[0][0];
		}
		
		return validity_date;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("OK")){
			setYear((Integer) cmbYear.getSelectedItem());
			setMonth(cmbMonth.getSelectedIndex() + 1);
			setValidUntil(dteValidity.getDate());
			dispose();
		}
		if(action.equals("Cancel")){
			setYear(null);
			setMonth(null);
			setValidUntil(null);
			dispose();
		}
		if(action.equals("Escape")){
			setYear(null);
			setMonth(null);
			setValidUntil(null);
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

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}
	
	
}
