package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class GTG_TaxClearance extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	private static final long serialVersionUID = 1L;
	static Dimension size = new Dimension(450, 350);
	static String title = "Tax Clearance for G-to-G Accounts";
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlDetails;
	private JPanel pnlDetailsWest;
	private JPanel pnlDetailsEast;
	private JPanel pnlSouth;

	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;

	private _JLookup lookupProject;
	private _JLookup lookupPhase;
	private _JLookup lookupCompany;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtPhase;

	private JButton btnPreview;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private static	String company;	
	private static String co_id;
	private static String proj_id;
	private static String phase;	

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");


	public GTG_TaxClearance() {
		super(title, false, true, false, true);
		initGUI();
	}

	public GTG_TaxClearance(String title) {
		super(title);
		initGUI();
	}

	public GTG_TaxClearance(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(size);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			{
				pnlDetails = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlDetails, BorderLayout.NORTH);
				pnlDetails.setPreferredSize(new Dimension(0, 180));
				pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Details" ));

				{
					pnlDetailsWest = new JPanel(new GridLayout(3,1, 5, 5));
					pnlDetails.add(pnlDetailsWest, BorderLayout.WEST);
					pnlDetailsWest.setPreferredSize(new Dimension(142, 100));

					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlDetailsWest.add(lblCompany);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlDetailsWest.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id = (String) data[0];	
									company		= (String) data[1];
									String name = (String) data[1];						
									txtCompany.setText(name);
									btnPreview.setEnabled(true);
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlDetailsWest.add(lblProject);
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlDetailsWest.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									System.out.println("PROJECT: " + lookupProject.getValue());

									proj_id = (String) data[0];		
									String name = (String) data[1];						
									txtProject.setText(name);
									btnPreview.setEnabled(true);
									lookupPhase.setLookupSQL(SQL_PHASE(proj_id));
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}

					{
						lblPhase = new JLabel("Phase", JLabel.TRAILING);
						pnlDetailsWest.add(lblPhase);
					}
					{
						lookupPhase = new _JLookup(null, "Phase");
						pnlDetailsWest.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									phase = (String) data[0];		
									String name = (String) data[1];						
									txtPhase.setText(name);
									btnPreview.setEnabled(true);

								}
							}
						});
					}
				}

				pnlDetailsEast = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlDetails.add(pnlDetailsEast, BorderLayout.CENTER);
				pnlDetailsEast.setPreferredSize(new Dimension(300, 159));
				{
					txtCompany = new JTextField();
					pnlDetailsEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtProject = new JTextField();
					pnlDetailsEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
				}
				{
					txtPhase = new JTextField();
					pnlDetailsEast.add(txtPhase, BorderLayout.CENTER);
					txtPhase.setEditable(false);
				}
			}
			{
				JPanel pnlCoveredDate = new JPanel(new GridLayout(1, 2, 3, 3)); 
				pnlMain.add(pnlCoveredDate, BorderLayout.CENTER);
				pnlCoveredDate.setBorder(components.JTBorderFactory.createTitleBorder("Period Covered")); 
				{
					lblDateFrom = new JLabel("From  :", JLabel.TRAILING);
					pnlCoveredDate.add(lblDateFrom, BorderLayout.CENTER);

				}
				{
					dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCoveredDate.add(dteDateFrom, BorderLayout.CENTER);						
					dteDateFrom.setDate(null);
					dteDateFrom.setDate(FncGlobal.dateFormat("2000-01-01"));
				}		
				{
					lblDateTo = new JLabel("To  :", JLabel.TRAILING);
					pnlCoveredDate.add(lblDateTo);	
				}
				{
					dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCoveredDate.add(dateDateTo, BorderLayout.EAST);
					dateDateTo.setBounds(435, 7, 115, 21);
					dateDateTo.setDate(null);
					dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}		
			}
			{				
				pnlSouth = new JPanel(new GridLayout(1, 3,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 55));

				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview,BorderLayout.CENTER);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Preview();
						}
					});
				}

			}
		}
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupCompany.requestFocus();
	}

	@Override 
	public void ancestorMoved(AncestorEvent event) { 

	}

	@Override
	public void ancestorRemoved(AncestorEvent event) { 

	}

	public static String SQL_PHASE(String proj_id) {//XXX Phase

		String SQL = "select\n" +
				"TRIM(a.phase) as \"Phase\",\n" +
				"'Phase ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" +
				"b.proj_alias || a.phase as \"Alias\"\n" +
				"from mf_sub_project a\n" +
				"left join mf_project b on a.proj_id = b.proj_id and coalesce(a.server_id, '') = coalesce(b.server_id, '') \n" +
				"where a.proj_id = '"+ proj_id +"'\n" +
				"and a.status_id = 'A'\n" +
				"group by a.phase, b.proj_alias\n" +
				"order by a.phase;";

		FncSystem.out("PHASE HERE!!!", SQL);

		return SQL;
	}

	private void Preview(){

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("p_co_id", co_id);
		mapParameters.put("p_proj_id", proj_id);
		mapParameters.put("p_phase", phase);
		mapParameters.put("p_from", dteDateFrom.getDate());
		mapParameters.put("p_to", dateDateTo.getDate());

		String sql = "SELECT * FROM view_endorsement_to_lld_pmt_gtg('"+co_id+"','"+proj_id+"','"+phase+"','"+dteDateFrom.getDate()+"','"+dateDateTo.getDate()+"');";
		FncSystem.out("G-to-G-Tax-Clearance-Request SQL:", sql);

		FncReport.generateReport("/Reports/rptGToGTaxClearance.jasper", title, mapParameters);
	}
}
