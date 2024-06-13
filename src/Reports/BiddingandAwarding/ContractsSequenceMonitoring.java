package Reports.BiddingandAwarding;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;
import components._JInternalFrame;

public class ContractsSequenceMonitoring extends _JInternalFrame implements ActionListener, _GUI, AncestorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810802178431437146L;

	static String title = "Contracts Sequence Monitoring";
	static Dimension frameSize = new Dimension(500, 180);//XXX 510, 250
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;

	String co_id;
	
	private JPanel pnlCompany;
	private JPanel pnlCompanyWest;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	
	private JPanel pnlProject;
	private JPanel pnlProjectWest;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;

	private JPanel pnlNTPType;
	private JPanel pnlNTPTypeWest;
	private JLabel lblNTPType;
	private _JLookup lookupNTPType;
	private JTextField txtNTPType;

	private JPanel pnlPhase;
	private JPanel pnlPhaseWest;
	private JLabel lblPhase;
	private _JLookup lookupPhase;
	private JTextField txtPhase;


	private JPanel pnlSouth;
	private JButton btnPreview;

	String company;
	String company_logo;

	String server = null;
	String dbase = null;

	public ContractsSequenceMonitoring() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ContractsSequenceMonitoring(String title) {
		super(title);
		initGUI();
	}

	public ContractsSequenceMonitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlMain.add(pnlNorth, BorderLayout.CENTER);
				pnlNorth.setPreferredSize(new Dimension(488, 53));
				{
					pnlCompany = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlCompany);
					{
						pnlCompanyWest = new JPanel(new GridLayout(1, 2));
						pnlCompany.add(pnlCompanyWest, BorderLayout.WEST);
						pnlCompanyWest.setPreferredSize(new Dimension(125, 25));
						{
							lblCompany = new JLabel("Company");
							pnlCompanyWest.add(lblCompany);
						}
						{
							lookupCompany = new _JLookup(null, "Company");
							pnlCompanyWest.add(lookupCompany);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(SQL_COMPANY());
							lookupCompany.setValue("02");
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										co_id = (String) data[0];	
										company		= (String) data[1];	
										company_logo = (String) data[3];
										
										String name = (String) data[1];						
										txtCompany.setText(name);
										lookupProject.setLookupSQL(SQL_PROJECT(co_id));
									}
								}
							});
							lookupCompany.addKeyListener(new KeyListener() {

								public void keyTyped(KeyEvent e) {
									
								}

								public void keyReleased(KeyEvent e) {
									
								}

								public void keyPressed(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										lookupCompany.setValue("");
										txtCompany.setText("");
									}
								}
							});
						}
					}
					{
						txtCompany = new JTextField();
						pnlCompany.add(txtCompany, BorderLayout.CENTER);
						txtCompany.setEditable(false);
						txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
					}
				}
				{
					pnlProject = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlProject);
					{
						pnlProjectWest = new JPanel(new GridLayout(1, 2));
						pnlProject.add(pnlProjectWest, BorderLayout.WEST);
						pnlProjectWest.setPreferredSize(new Dimension(125, 25));
						{
							lblProject = new JLabel("Project");
							pnlProjectWest.add(lblProject);
						}
						{
							lookupProject = new _JLookup(null, "Project");
							pnlProjectWest.add(lookupProject);
							lookupProject.setReturnColumn(0);
							co_id = lookupCompany.getText();
							lookupProject.setLookupSQL(SQL_PROJECT(co_id ));
							lookupProject.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtProject.setText((String) data[1]);
										//company = (String) data[4];
										//company_logo = (String) data[5];
										//server = (String) data[7];
										//dbase = (String) data[8];
									}
								}
							});
							lookupProject.addKeyListener(new KeyListener() {

								public void keyTyped(KeyEvent e) {
									
								}

								public void keyReleased(KeyEvent e) {
									
								}

								public void keyPressed(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										lookupProject.setValue("");
										txtProject.setText("");
									}
								}
							});
						}
					}
					{
						txtProject = new JTextField();
						pnlProject.add(txtProject, BorderLayout.CENTER);
						txtProject.setEditable(false);
					}
				}
				{
					pnlNTPType = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlNTPType);
					{
						pnlNTPTypeWest = new JPanel(new GridLayout(1, 2));
						pnlNTPType.add(pnlNTPTypeWest, BorderLayout.WEST);
						pnlNTPTypeWest.setPreferredSize(new Dimension(125, 25));
						{
							lblNTPType = new JLabel("NTP Type");
							pnlNTPTypeWest.add(lblNTPType);
						}
						{
							lookupNTPType = new _JLookup(null, "NTP Type");
							pnlNTPTypeWest.add(lookupNTPType);
							lookupNTPType.setReturnColumn(0);
							lookupNTPType.setLookupSQL(_NoticeToProceed.NTPType());
							lookupNTPType.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtNTPType.setText((String) data[1]);
									}
								}
							});
							lookupNTPType.addKeyListener(new KeyListener() {
								
								@Override
								public void keyTyped(KeyEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void keyReleased(KeyEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void keyPressed(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										lookupNTPType.setValue("");
										txtNTPType.setText("");
									}
								}
							});
						}
					}
					{
						txtNTPType = new JTextField();
						pnlNTPType.add(txtNTPType, BorderLayout.CENTER);
						txtNTPType.setEditable(false);
					}
				}
				{
					pnlPhase = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlPhase);
					{
						pnlPhaseWest = new JPanel(new GridLayout(1, 2));
						pnlPhase.add(pnlPhaseWest, BorderLayout.WEST);
						pnlPhaseWest.setPreferredSize(new Dimension(125, 25));
						{
							lblPhase = new JLabel("Phase");
							pnlPhaseWest.add(lblPhase);
						}
						{
							lookupPhase = new _JLookup(null, "Phase");
							pnlPhaseWest.add(lookupPhase);
							lookupPhase.setReturnColumn(0);
							lookupPhase.setLookupSQL(SQL_PHASE());
							lookupPhase.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtPhase.setText((String) data[0]);
									}
								}
							});
							lookupPhase.addKeyListener(new KeyListener() {
								
								@Override
								public void keyTyped(KeyEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void keyReleased(KeyEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void keyPressed(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										lookupPhase.setValue("");
										txtPhase.setText("");
									}
								}
							});
						}
					}
					{
						txtPhase = new JTextField();
						pnlPhase.add(txtPhase, BorderLayout.CENTER);
						txtPhase.setEditable(false);
					}
				}

			}

			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(588, 30));
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
	}

	@Override
	public void ancestorAdded(AncestorEvent arg0) {
		lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent arg0) { }

	@Override
	public void ancestorRemoved(AncestorEvent arg0) { }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String ntp_type_name = txtNTPType.getText();
		String proj = (String) lookupProject.getValue();
		String ntp_type = (String) lookupNTPType.getValue();
		//String ntp_type = (String) lookupNTPType.getValue()
		//String ntp_type = (String) lookupNTPType.getValue()
		System.out.println("phase:"+ lookupPhase.getValue());
		System.out.println("proj_id:"+ lookupProject.getValue());
		System.out.println("ntp:"+ ntp_type_name);
		String reportTitle = String.format("%s (%s)", title, ntp_type_name.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("proj_name", txtProject.getText());
		mapParameters.put("ntp_type_id", lookupNTPType.getValue());
		mapParameters.put("ntp_type_name", ntp_type_name);
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("phase", lookupPhase.getValue());
//		try {
//			if(ntp_type_name.equals("COMMON SERVC ENTR")) {
//				mapParameters.put("phase", lookupPhase.getValue());
//			}else {
//				mapParameters.put("phase", (lookupPhase.getValue().equals("4-A")?"4A":lookupPhase.getValue()));
//			}	
//		} catch (NullPointerException e) {
//
//			mapParameters.put("phase", "null");
//
//		}

		mapParameters.put("prepared_by", UserInfo.Alias);
		System.out.println("phase:"+ lookupPhase.getValue());
		System.out.println("proj_id:"+ lookupProject.getValue());
		System.out.println("ntp:"+ ntp_type_name);
		System.out.println("co_id:"+ lookupCompany.getValue());
		
		
	FncReport.generateReport("/Reports/rptContractsSequenceMonitoring.jasper", reportTitle, txtProject.getText(), mapParameters);
	}
	public static String SQL_PHASE(){
		pgSelect db = new pgSelect();
		//		String query = "SELECT to_char(COALESCE(MAX(control_no::INT), 0) + 1, 'FM0000000000')"
		//				+ "FROM rf_revolving_funds";
		String query = "SELECT distinct(phase) as phase\n" + 
				"		FROM mf_sub_project a\n" + 
				"		LEFT JOIN mf_project b on a.proj_id = b.proj_id\n" + 
				"		WHERE  a.status_id = 'A'\n" + 
				"		and coalesce(sub_proj_name, '') != '' and coalesce(sub_proj_alias, '') != '' and coalesce(phase, '') != '' ";
		db.select(query);

		return query;
	}

}
