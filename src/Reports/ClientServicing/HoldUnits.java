package Reports.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class HoldUnits extends _JInternalFrame implements _GUI, ActionListener, AncestorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5159650933602942626L;

	static String title = "Hold Units";
	Dimension frameSize = new Dimension(510, 200);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;

	private JXPanel pnlCenter;

	private JXLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;

	private _JLookup lookupProject;
	private JTextField txtProject;

	private _JLookup lookupPhase;
	private JXTextField txtPhase;

	private JCheckBox chkIncludeManagementHoldAccounts;

	private JXPanel pnlSouth;
	private JButton btnPreview;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	private _JDateChooser dteDate;
	private JCheckBox chkIncludeFloating;
	private JCheckBox chkIncludeHolding;
	private JCheckBox chkIncludeCommitment;
	
	private Boolean bln_floating = true;
	private Boolean bln_holding = true;
	private Boolean bln_commit = true;
	private Boolean bln_mgmt_hold = true;

	public HoldUnits() {
		super(title, false, true, false, true);
		initGUI();
	}

	public HoldUnits(String title) {
		super(title);
		initGUI();
	}

	public HoldUnits(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(625, 237));
		this.addAncestorListener(this);
		{
			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(null);
				pnlCenter.setBorder(FncGlobal.lineBorder);
				pnlCenter.setPreferredSize(new java.awt.Dimension(498, 165));
				{
					lblCompany = new JXLabel("Company");
					pnlCenter.add(lblCompany);
					//lblProject.setBorder(lineBorder);
					lblCompany.setBounds(10, 10, 75, 25);
				}
				{
					lookupCompany = new _JLookup(null, "Company");
					pnlCenter.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
					lookupCompany.setBounds(90, 10, 50, 25);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Project
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								txtCompany.setText(data[1].toString());

								lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));

								manager.focusNextComponent();
							}else{
								txtCompany.setText("");
							}
						}
					});
				}
				{
					txtCompany = new JTextField();
					pnlCenter.add(txtCompany);
					txtCompany.setEditable(false);
					txtCompany.setBounds(144, 10, 456, 25);
				}
				{
					JXLabel lblProject = new JXLabel("Project");
					pnlCenter.add(lblProject);
					lblProject.setBounds(10, 40, 75, 25);
				}
				{
					lookupProject = new _JLookup(null, "Project", "Please select company.");
					pnlCenter.add(lookupProject);
					lookupProject.setReturnColumn(0);
					lookupProject.setBounds(90, 40, 50, 25);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Project
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								txtProject.setText(data[1].toString());
								lookupPhase.setLookupSQL(SQL_PHASE_ALL(data[0].toString()));
								manager.focusNextComponent();
							}else{
								txtProject.setText("");
							}
						}
					});
				}
				{
					txtProject = new JTextField();
					pnlCenter.add(txtProject);
					txtProject.setEditable(false);
					txtProject.setBounds(144, 40, 456, 25);
				}
				{
					JXLabel lblPhase = new JXLabel("Phase");
					pnlCenter.add(lblPhase);
					lblPhase.setBounds(10, 70, 75, 25);
				}
				{
					lookupPhase = new _JLookup(null, "Phase", "Please select project.");
					pnlCenter.add(lookupPhase);
					lookupPhase.setReturnColumn(0);
					lookupPhase.setBounds(90, 70, 50, 25);
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Phase
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								txtPhase.setText(data[1].toString());
								manager.focusNextComponent();
							}else{
								JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select project first.", "Phase", JOptionPane.WARNING_MESSAGE);
							}
						}
					});
				}
				{
					txtPhase = new JXTextField("");
					pnlCenter.add(txtPhase);
					txtPhase.setEditable(false);
					txtPhase.setBounds(144, 70, 120, 25);
				}
				{
					JXLabel lblDate = new JXLabel("Date");
					pnlCenter.add(lblDate);
					lblDate.setBounds(11, 96, 69, 28);
				}
				{
					dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenter.add(dteDate, BorderLayout.CENTER);						
					dteDate.setDate(null);
					dteDate.setEnabled(true);
					dteDate.setDate(null);
					dteDate.setBounds(90, 100, 174, 25);
				}	
				{
					chkIncludeFloating = new JCheckBox("Floating");
					pnlCenter.add(chkIncludeFloating);
					chkIncludeFloating.setBounds(33, 134, 94, 23);
					chkIncludeFloating.setSelected(true);
					chkIncludeFloating.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(chkIncludeFloating.isSelected()==true){bln_floating = true;} 
							else{bln_floating = false;}											
						}
					});
					set_restriction();
				}
				{
					chkIncludeHolding = new JCheckBox("Holding");
					pnlCenter.add(chkIncludeHolding);
					chkIncludeHolding.setBounds(174, 133, 120, 25);
					chkIncludeHolding.setSelected(true);
					chkIncludeHolding.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(chkIncludeHolding.isSelected()==true){bln_holding = true;} 
							else{bln_holding = false;}											
						}
					});
					set_restriction();
				}
				{
					chkIncludeCommitment = new JCheckBox("Commitment");
					pnlCenter.add(chkIncludeCommitment);
					chkIncludeCommitment.setBounds(324, 134, 137, 25);
					chkIncludeCommitment.setSelected(true);
					chkIncludeCommitment.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(chkIncludeCommitment.isSelected()==true){bln_commit = true;} 
							else{bln_commit = false;}											
						}
					});
					set_restriction();
				}
				{
					chkIncludeManagementHoldAccounts = new JCheckBox("Mgmt. Hold");
					pnlCenter.add(chkIncludeManagementHoldAccounts);
					chkIncludeManagementHoldAccounts.setBounds(472, 134, 128, 25);
					chkIncludeManagementHoldAccounts.setSelected(true);
					chkIncludeManagementHoldAccounts.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(chkIncludeManagementHoldAccounts.isSelected()==true){bln_mgmt_hold = true;} 
							else{bln_mgmt_hold = false;}											
						}
					});
					set_restriction();
				}
			}
			{
				pnlSouth = new JXPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				//pnlSouth.setBorder(lineBorder);
				//pnlSouth.setCursor(handCursor);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(388, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, chkIncludeManagementHoldAccounts, btnPreview));
		this.setBounds(0, 0, 625, 237);
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupCompany.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		Map<String, Object> mapHoldUnits = new HashMap<String, Object>();

		mapHoldUnits.put("co_id", lookupCompany.getValue());
		mapHoldUnits.put("company", txtCompany.getText());
		mapHoldUnits.put("proj_id", lookupProject.getValue());
		mapHoldUnits.put("phase", lookupPhase.getValue());
		mapHoldUnits.put("prepared_by_alias", UserInfo.Alias);
		mapHoldUnits.put("prepared_by", UserInfo.FullName);
		mapHoldUnits.put("date_hold", dteDate.getDate());
		mapHoldUnits.put("include_management_hold", chkIncludeManagementHoldAccounts.isSelected());
		mapHoldUnits.put("bln_floating", bln_floating);
		mapHoldUnits.put("bln_holding", bln_holding);
		mapHoldUnits.put("bln_commit", bln_commit);
		mapHoldUnits.put("bln_mgmt_hold", bln_mgmt_hold);
		
		System.out.println("view_hold_units('"+lookupCompany.getValue()+"', '"+lookupProject.getValue()+"', '"+lookupPhase.getValue()+"', "+chkIncludeManagementHoldAccounts.isSelected()+", '"+dteDate.getDate()+"')");
		FncReport.generateReport("/Reports/rptHoldUnits.jasper", title, txtProject.getText(), txtPhase.getText(), mapHoldUnits);
	}
	
	private void set_restriction(){
		
		Object[] div_dtl = sql_getDivId();
		String dept_id = "";
		try { dept_id = (String) div_dtl[2];} catch (NullPointerException e) { dept_id = ""; }
		
		if (dept_id.equals("02")||dept_id.equals("52")||dept_id.equals("54")||dept_id.equals("09")||
				UserInfo.EmployeeCode.equals("000539")||UserInfo.EmployeeCode.equals("000645")||
				UserInfo.EmployeeCode.equals("000605")||UserInfo.EmployeeCode.equals("900395")||
				UserInfo.EmployeeCode.equals("900028")||UserInfo.EmployeeCode.equals("900449")||
				UserInfo.EmployeeCode.equals("900876"))
		{
			//chkIncludeManagementHoldAccounts.setSelected(true);		
		}
		else 
		{
			//chkIncludeManagementHoldAccounts.setSelected(false);		
		}		
		
	}

	private Object [] sql_getDivId() {

	String SQL = 
		"select " +
		"a.div_code, " +
		"trim(b.division_name) as div_name,  " +
		"a.dept_code, " +
		"trim(c.dept_name) as dept_name  " +
		"from em_employee a \n" +
		"left join mf_division b on a.div_code = b.division_code " +
		"left join mf_department c on a.dept_code = c.dept_code " +
		"where a.emp_code = '"+UserInfo.EmployeeCode+"'  " ;

	System.out.printf("SQL #1 sql_getDivId: %s", SQL);
	pgSelect db = new pgSelect();
	db.select(SQL);		

	if(db.isNotNull()){
		return db.getResult()[0];
	}else{
		return null;
	}
}

}