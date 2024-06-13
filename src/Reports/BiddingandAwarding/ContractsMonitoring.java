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

import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;
import components._JInternalFrame;

public class ContractsMonitoring extends _JInternalFrame implements ActionListener, _GUI, AncestorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810802178431437146L;
	
	static String title = "Contracts Monitoring";
	static Dimension frameSize = new Dimension(500, 180);//XXX 510, 250
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;
	
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

	private JPanel pnlNTPDateFrom;
	private JPanel pnlNTPDateFromWest;
	private JLabel lblNTPDateFrom;
	private _JDateChooser dateNTPDateFrom;

	private JPanel pnlNTPDateTo;
	private JPanel pnlNTPDateToWest;
	private JLabel lblNTPDateTo;
	private _JDateChooser dateNTPDateTo;
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	
	String company;
	String company_logo;
	
	String server = null;
	String dbase = null;

	public ContractsMonitoring() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ContractsMonitoring(String title) {
		super(title);
		initGUI();
	}

	public ContractsMonitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
							lookupProject.setLookupSQL(SQL_PROJECT());
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
						}
					}
					{
						txtNTPType = new JTextField();
						pnlNTPType.add(txtNTPType, BorderLayout.CENTER);
						txtNTPType.setEditable(false);
					}
				}
				{
					pnlNTPDateFrom = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlNTPDateFrom);
					{
						pnlNTPDateFromWest = new JPanel(new BorderLayout(3, 3));
						pnlNTPDateFrom.add(pnlNTPDateFromWest, BorderLayout.WEST);
						pnlNTPDateFromWest.setPreferredSize(new Dimension(250, 20));
						{
							lblNTPDateFrom = new JLabel("NTP Date From");
							pnlNTPDateFromWest.add(lblNTPDateFrom, BorderLayout.WEST);
							lblNTPDateFrom.setPreferredSize(new Dimension(100, 0));
						}
						{
							dateNTPDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlNTPDateFromWest.add(dateNTPDateFrom, BorderLayout.CENTER);
							dateNTPDateFrom.setDate(Calendar.getInstance().getTime());
						}
					}
				}
				{
					pnlNTPDateTo = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlNTPDateTo);
					{
						pnlNTPDateToWest = new JPanel(new BorderLayout(3, 3));
						pnlNTPDateTo.add(pnlNTPDateToWest, BorderLayout.WEST);
						pnlNTPDateToWest.setPreferredSize(new Dimension(250, 20));
						{
							lblNTPDateTo = new JLabel("NTP Date To");
							pnlNTPDateToWest.add(lblNTPDateTo, BorderLayout.WEST);
							lblNTPDateTo.setPreferredSize(new Dimension(100, 0));
						}
						{
							dateNTPDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlNTPDateToWest.add(dateNTPDateTo, BorderLayout.CENTER);
							dateNTPDateTo.setDate(Calendar.getInstance().getTime());
						}
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
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupProject, lookupNTPType, (JTextField)dateNTPDateFrom.getDateEditor(), (JTextField)dateNTPDateFrom.getDateEditor(), btnPreview));
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
		String reportTitle = String.format("%s (%s)", title, ntp_type_name.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("proj_name", txtProject.getText());
		mapParameters.put("ntp_type_id", lookupNTPType.getValue());
		mapParameters.put("ntp_type_name", ntp_type_name);
		mapParameters.put("ntp_date_from", dateNTPDateFrom.getDate());
		mapParameters.put("ntp_date_to", dateNTPDateTo.getDate());
		mapParameters.put("prepared_by", UserInfo.Alias);

		FncReport.generateReport("/Reports/rptContractsMonitoring.jasper", reportTitle, txtProject.getText(), mapParameters);
	}

}
