package Reports.CreditAndCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class LoanRealeasedAccountsWithPN extends _JInternalFrame implements _GUI , ActionListener, AncestorListener {
	
	static String title = "Loan Released Accounts with signed PN (w/ Remaining DP's)";
	static Dimension frameSize = new Dimension(500, 250);
	
	private JXPanel pnlMain;
	private JTextField txtCompany;
	private _JLookup lookupProject;

	private JXLabel lblCompany;
	private _JLookup lookupCompany;
	
	private JTextField txtProject;

	private _JLookup lookupPhase;
	private JXTextField txtPhase;

	private _JDateChooser dateFrom;
	private _JDateChooser dateTo;

	private JXPanel pnlSouth;
	private JButton btnPreview;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public LoanRealeasedAccountsWithPN() {
		super(title, false, true, false, true);
		initGUI();
	}

	public LoanRealeasedAccountsWithPN(String title) {
		super(title);
		initGUI();
	}

	public LoanRealeasedAccountsWithPN(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		
		{
			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JXPanel pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(null);
				pnlCenter.setBorder(FncGlobal.lineBorder);
				{
					JXLabel lblCompany = new JXLabel("Company");
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
				{
					JXLabel lblDateFrom = new JXLabel("Date From");
					pnlCenter.add(lblDateFrom);
					lblDateFrom.setBounds(10, 100, 75, 25);
				}
				{
					dateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
					pnlCenter.add(dateFrom);
					dateFrom.setDate(Calendar.getInstance().getTime());
					dateFrom.setBounds(90, 100, 120, 25);
				}
				{
					JXLabel lblDateTo = new JXLabel("Date To");
					pnlCenter.add(lblDateTo);
					lblDateTo.setBounds(10, 130, 75, 25);
				}
				{
					dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
					pnlCenter.add(dateTo);
					dateTo.setDate(Calendar.getInstance().getTime());
					dateTo.setBounds(90, 130, 120, 25);
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
					btnPreview.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							System.out.println("co_id:" + lookupCompany.getValue());
							System.out.println("company:" + txtCompany.getText());
							System.out.println("company_alias:" +  txtCompany.getToolTipText());
							System.out.println("proj_id:" + lookupProject.getValue());
							System.out.println("phase:" + lookupPhase.getValue());
							System.out.println("date_from:" +sdf.format(dateFrom.getDate()) );
							System.out.println("date_to:" + sdf.format(dateTo.getDate()));
							System.out.println("prepared_by:" + UserInfo.FullName);
							System.out.println("Select * from view_loan_released_accounts_with_PN('"+lookupCompany.getValue()+"','"+lookupProject.getValue()+"','"+lookupPhase.getValue()+"','"+sdf.format(dateFrom.getDate())+"','"+sdf.format(dateTo.getDate())+"');");
							
							Map<String, Object> mapParameters = new HashMap<String, Object>();
					
							mapParameters.put("co_id", lookupCompany.getValue());
							mapParameters.put("company", txtCompany.getText());
							mapParameters.put("company_alias", txtCompany.getToolTipText());
							mapParameters.put("proj_id", lookupProject.getValue());
							mapParameters.put("phase", lookupPhase.getValue());
							mapParameters.put("date_from", dateFrom.getDate());
							mapParameters.put("date_to", dateTo.getDate());
							mapParameters.put("prepared_by", UserInfo.FullName);
							mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/cenqlogo.png"));
								

							FncReport.generateReport("/Reports/rptLoanReleasedAccntsWithPN.jasper", "Loan Released Accounts with PN", txtProject.getText(), txtPhase.getText(), mapParameters);

						}
					});
				}
			}
		}
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, btnPreview));
	}



	@Override
	public void ancestorAdded(AncestorEvent arg0) {
	}

	@Override
	public void ancestorMoved(AncestorEvent arg0) {
	}

	@Override
	public void ancestorRemoved(AncestorEvent arg0) {
	}

}
