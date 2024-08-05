package Reports.CreditAndCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class ListOfAcctsWithECAR extends _JInternalFrame implements _GUI, ActionListener, AncestorListener {

	private static final long serialVersionUID = 4032334442202602399L;
	
	static String title = "List of Accounts with E-CAR";
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

	private _JDateChooser dateFrom;
	private _JDateChooser dateTo;

	private JComboBox<String> cmbSorting;

	private JCheckBox chkIncludeManagementHoldAccounts;

	private JXPanel pnlSouth;
	private JButton btnPreview;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public ListOfAcctsWithECAR() {
		initGUI();
	}

	public ListOfAcctsWithECAR(String title) {
		super(title);
		initGUI();
	}

	public ListOfAcctsWithECAR(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
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
								txtCompany.setToolTipText(data[2].toString());

								lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));

								manager.focusNextComponent();
							}else{
								txtCompany.setText("");
								txtCompany.setToolTipText("");
							}
						}
					});
				}
				{
					txtCompany = new JTextField();
					pnlCenter.add(txtCompany);
					txtCompany.setEditable(false);
					txtCompany.setBounds(144, 10, 341, 25);
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
					txtProject.setBounds(144, 40, 251, 25);
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
	
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("company_alias", txtCompany.getToolTipText());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("phase", lookupPhase.getValue());

		FncReport.generateReport("/Reports/rptECAR_Available.jasper", title, txtProject.getText(), txtPhase.getText(), mapParameters);
	}

}
