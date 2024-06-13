package Reports.CreditAndCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXTextField;

import Accounting.Collections.CheckStatusMonitoring;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class rptReport_v3 extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Real Property Tax Report";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;
	private JPanel pnlCenter;
	private JPanel pnlPaymentStatus;
	private JPanel pnlCenter_b;
	private JPanel pnlBuyerType;
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


	private JRadioButton rbtnInHouse;
	private JRadioButton rbtnPagIbig;

	private JRadioButton rbtnFullyPaid;
	private JRadioButton rbtnWithBalance;

	private JButton btnPreview;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private static	String company;	
	private static String co_id;
	private static String proj_id;
	private static String phase;	

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");


	public rptReport_v3() {
		super(title, false, true, false, true);
		initGUI();
	}

	public rptReport_v3(String title) {
		super(title);
		initGUI();
	}

	public rptReport_v3(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(560, 415));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new Dimension(557, 246));

			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(547, 94));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details" ));

				{
					pnlNorthWest = new JPanel(new GridLayout(3,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new Dimension(142, 100));

					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlNorthWest.add(lblCompany);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlNorthWest.add(lookupCompany);
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
						pnlNorthWest.add(lblProject);
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupProject);
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
						pnlNorthWest.add(lblPhase);
					}
					{
						lookupPhase = new _JLookup(null, "Phase");
						pnlNorthWest.add(lookupPhase);
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

				pnlNorthEast = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new Dimension(300, 159));
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
				}
				{
					txtPhase = new JTextField();
					pnlNorthEast.add(txtPhase, BorderLayout.CENTER);
					txtPhase.setEditable(false);
				}
			}

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.CENTER);
			pnlCenter2.setPreferredSize(new Dimension(480, 65));
			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Buyer Type"));

			{
				pnlBuyerType = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlCenter2.add(pnlBuyerType, BorderLayout.NORTH);	
				pnlBuyerType.setPreferredSize(new Dimension(480, 50));	
				pnlBuyerType.setBorder(lineBorder);

				{
					rbtnInHouse = new JRadioButton();
					pnlBuyerType.add(rbtnInHouse);
					rbtnInHouse.setText("In-House");
					rbtnInHouse.setBounds(260, 12, 77, 18);
					rbtnInHouse.setPreferredSize(new Dimension(220, 18));
					rbtnInHouse.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){	
							rbtnInHouse.setSelected(true);
							rbtnPagIbig.setSelected(false);

						}});					
				}

				{
					rbtnPagIbig = new JRadioButton();
					pnlBuyerType.add(rbtnPagIbig);
					rbtnPagIbig.setText("Pag-Ibig");
					rbtnPagIbig.setBounds(277, 12, 77, 18);
					rbtnPagIbig.setPreferredSize(new Dimension(232, 24));
					rbtnPagIbig.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){									
							rbtnInHouse.setSelected(false);
							rbtnPagIbig.setSelected(true);


						}});
				}

			}
			{
				pnlPaymentStatus = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlPaymentStatus, BorderLayout.CENTER);
				pnlPaymentStatus.setPreferredSize(new Dimension(480, 65));				

				{
					pnlCenter = new JPanel(new GridLayout(1,1,5,5));
					pnlPaymentStatus.add(pnlCenter, BorderLayout.NORTH);
					pnlCenter.setPreferredSize(new Dimension(480, 65));
					pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Payment Status"));					
					{
						rbtnFullyPaid = new JRadioButton();
						pnlCenter.add(rbtnFullyPaid);
						rbtnFullyPaid.setText("Fully Paid");
						rbtnFullyPaid.setBounds(277, 12, 77, 18);
						rbtnFullyPaid.setPreferredSize(new Dimension(220, 18));
						rbtnFullyPaid.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae){	
								rbtnFullyPaid.setSelected(true);
								rbtnWithBalance.setSelected(false);

							}});					
					}


					{
						rbtnWithBalance = new JRadioButton();
						pnlCenter.add(rbtnWithBalance);
						rbtnWithBalance.setText("With Balance");
						rbtnWithBalance.setBounds(277, 12, 77, 18);
						rbtnWithBalance.setPreferredSize(new Dimension(232, 24));
						rbtnWithBalance.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae){									
								rbtnFullyPaid.setSelected(false);
								rbtnWithBalance.setSelected(true);


							}});
					}

				}
				{
					pnlCenter_b = new JPanel(new GridLayout(1,1,5,5));
					pnlPaymentStatus.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new Dimension(480, 75));
					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder("Period Covered"));

					{
						pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlCenter_b.add(pnlCriteria2, BorderLayout.WEST);					
						pnlCriteria2.setPreferredSize(new Dimension(380, 65));

						{
							lblDateFrom = new JLabel("From  :", JLabel.TRAILING);
							pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);

						}
						{
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCriteria2.add(dteDateFrom, BorderLayout.CENTER);						
							dteDateFrom.setDate(null);
							dteDateFrom.setDate(FncGlobal.dateFormat("2000-01-01"));
						}		
						{
							lblDateTo = new JLabel("To  :", JLabel.TRAILING);
							pnlCriteria2.add(lblDateTo);	
						}
						{
							dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCriteria2.add(dateDateTo, BorderLayout.EAST);
							dateDateTo.setBounds(435, 7, 115, 21);
							dateDateTo.setDate(null);
							dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						}		
					}
				}	
			}

			{				
				pnlSouth = new JPanel(new GridLayout(1, 3,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(75, 55));

				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview,BorderLayout.CENTER);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}

			}
		}
		
		this.setBounds(0, 0, 560, 415);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if((action.equals("Preview"))&&rbtnInHouse.isSelected()==true)
		{
			Preview();
		}    

		if((action.equals("Preview"))&&rbtnPagIbig.isSelected()==true)
		{
			Preview();
		}    


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
		mapParameters.put("p_dateFrom", dteDateFrom.getDate());
		mapParameters.put("p_dateTo", dateDateTo.getDate());
		String rbtpagibig = "";
		if (rbtnPagIbig.isSelected()== true) {
			rbtpagibig = "PAGIBIG";
			mapParameters.put("p_pmt_buyer", "PAGIBIG");
		}

		String rbtbalance = "";
		if (rbtnWithBalance.isSelected()== true) {
			rbtbalance = "With Balance";
			mapParameters.put("p_pmt_status",  "With Balance");
		}
		System.out.println("PAGIBIG: " +rbtpagibig);
		System.out.println("PAGIBIG: " +rbtbalance);

		String sql = "SELECT * FROM view_rtax_house_lot('"+co_id+"','"+proj_id+"','"+phase+"','"+dteDateFrom.getDate()+"','"+dateDateTo.getDate()+"','"+rbtpagibig+"','"+rbtbalance+"');";
	
		FncSystem.out("GG1", sql);
		
		
		FncReport.generateReport("/Reports/rptReport.jasper", title, txtProject.getText(),mapParameters);


	}


}
