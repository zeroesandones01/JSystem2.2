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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;

public class dlgDM_YearMonthValidity extends JDialog implements ActionListener, _GUI {
	

	private static final long serialVersionUID = -880349176619301257L;

	private Dimension size = new Dimension(300, 150);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlYear;
	private JLabel lblYear;
	private JComboBox cmbYear;

	private JPanel pnlMonth;
	private JLabel lblMonth;
	private JComboBox cmbMonth;
	
	private JPanel pnlValidUntil;
	private JLabel lblValidUntil;
	private _JDateChooser dteValidUntil;
	
	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;
	
	private Integer Year;
	private Integer Month;
	private Date validUntil;
	private String doc_id;

	public dlgDM_YearMonthValidity() {
		initGUI();
	}

	public dlgDM_YearMonthValidity(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Frame owner, String title, String doc_id) {
		super(owner, title);
		this.doc_id = doc_id;
		initGUI();
	}

	public dlgDM_YearMonthValidity(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_YearMonthValidity(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
					pnlYear = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlYear);
					{
						lblYear = new JLabel("Year");
						pnlYear.add(lblYear, BorderLayout.WEST);
						lblYear.setPreferredSize(new Dimension(60, 26));
					}
					{
						//cmbYear = new JComboBox(new String[] {"2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011"});
						cmbYear = new JComboBox(new DefaultComboBoxModel(FncGlobal.listYear.toArray()));
						pnlYear.add(cmbYear, BorderLayout.CENTER);
						cmbYear.setSelectedItem(null);
						cmbYear.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								if(cmbYear.getSelectedItem() != null && cmbMonth.getSelectedItem() != null){
									Integer year = (Integer) cmbYear.getSelectedItem();
									String month = (String) cmbMonth.getSelectedItem();
									
									dteValidUntil.setDate(getValidityDate(year.toString(), month, doc_id));
								}
							}
						});
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
						cmbMonth.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								if(cmbYear.getSelectedItem() != null && cmbMonth.getSelectedItem() != null){
									Integer year = (Integer) cmbYear.getSelectedItem();
									String month = (String) cmbMonth.getSelectedItem();
									
									dteValidUntil.setDate(getValidityDate(year.toString(), month, doc_id));
								}
							}
						});
					}
				}
				{
					pnlValidUntil = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlValidUntil);
					{
						lblValidUntil = new JLabel("Valid Until");
						pnlValidUntil.add(lblValidUntil, BorderLayout.WEST);
						//lblValidUntil.setPreferredSize(new Dimension(60, 0));
						lblValidUntil.setPreferredSize(new Dimension(100, 37));
					}
					{
						dteValidUntil = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlValidUntil.add(dteValidUntil, BorderLayout.CENTER);
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
	
	private Date getValidityDate(String year, String month, String doc_id){
		String date_issued = String.format("%s-%s-%s", year, month, "01");
		
		Date validity_date = null;
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT get_doc_validity_date('"+date_issued+"', '"+doc_id+"')";
		db.select(SQL);
		//FncSystem.out("Display value of doc validity:", SQL);
		
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
			setValidUntil(dteValidUntil.getDate());
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

	/**
	 * @param validUntil the validUntil to set
	 */
	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}
	
	

}
