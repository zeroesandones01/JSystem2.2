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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JXTextField;

public class dlgDM_InsuranceCompany extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(500, 100);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlInsuranceCompany;
	private JLabel lblInsuranceCompany;
	
	private JPanel pnlLookups;
	private _JLookup lookupInsuranceCompany;
	private _JXTextField txtInsuranceCompany;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;
	
	private Integer Year;
	private String InsuranceID;
	private String InsuranceName;

	public dlgDM_InsuranceCompany() {
		initGUI();
	}

	public dlgDM_InsuranceCompany(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_InsuranceCompany(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
				pnlCenter = new JPanel(new GridLayout(1, 1, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlInsuranceCompany = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlInsuranceCompany);
					{
						lblInsuranceCompany = new JLabel("Insurance Company");
						pnlInsuranceCompany.add(lblInsuranceCompany, BorderLayout.WEST);
						lblInsuranceCompany.setPreferredSize(new Dimension(130, 37));
					}
					{
						pnlLookups = new JPanel(new BorderLayout(3, 3));
						pnlInsuranceCompany.add(pnlLookups, BorderLayout.CENTER);
						{
							lookupInsuranceCompany = new _JLookup(null, "Insurance Company");
							pnlLookups.add(lookupInsuranceCompany, BorderLayout.WEST);
							lookupInsuranceCompany.setPrompt("ID");
							lookupInsuranceCompany.setReturnColumn(0);
							lookupInsuranceCompany.setLookupSQL(sqlInsuranceCompany());
							lookupInsuranceCompany.setPreferredSize(new Dimension(100, 0));
							lookupInsuranceCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtInsuranceCompany.setText((String) data[1]);
										
										setInsuranceID((String) data[0]);
										setInsuranceName((String) data[1]);
									}
								}
							});
						}
						{
							txtInsuranceCompany = new _JXTextField("Insurance Company Name");
							pnlLookups.add(txtInsuranceCompany, BorderLayout.CENTER);
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
	
	String sqlInsuranceCompany() {
		String SQL = "SELECT TRIM(entity_id) as \"ID\", TRIM(entity_name) as \"Name\", TRIM(entity_alias) as \"Alias\"\n" + 
				"FROM rf_entity\n" + 
				"WHERE entity_id IN (SELECT entity_id FROM rf_entity_assigned_type WHERE entity_type_id = '36' AND status_id = 'A')\n" + 
				"ORDER BY TRIM(entity_name);";
		return SQL;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("OK")){
			//setYear((int) cmbYear.getSelectedItem());
			dispose();
		}
		if(action.equals("Cancel")){
			setYear(null);
			dispose();
		}
		if(action.equals("Escape")){
			setYear(null);
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
	public void setYear(Integer year) {
		Year = year;
	}

	public String getInsuranceID() {
		return InsuranceID;
	}

	public void setInsuranceID(String insuranceID) {
		InsuranceID = insuranceID;
	}

	public String getInsuranceName() {
		return InsuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		InsuranceName = insuranceName;
	}

}
