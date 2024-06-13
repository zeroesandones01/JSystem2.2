package Reports.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Buyers.ClientServicing._CARD;
import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JCheckBox;
import components._JInternalFrame;
import interfaces._GUI;

public class GeneralAssemblyHOA extends _JInternalFrame implements _GUI, ActionListener {
	
	private static final long serialVersionUID = 5058393165442977651L;
	
	static String title = "General Assembly for Creation of HOA";
	Dimension frameSize = new Dimension(510, 150);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;

	private JXPanel pnlCenter;

	private JXLabel lblCompany;
	private JCheckBox chkOpenHouse;
	private _JLookup lookupCompany;
	private JTextField txtCompany;

	private JXLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;

	private JXPanel pnlSouth;
	private JButton btnPreview;

	public GeneralAssemblyHOA() {
		super(title, false, true, false, true);
		initGUI();
	}

	public GeneralAssemblyHOA(String title) {
		super(title);
		initGUI();
	}

	public GeneralAssemblyHOA(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(FncGlobal.lineBorder);
				{
					JPanel pnlCenterLabel = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlCenter.add(pnlCenterLabel, BorderLayout.WEST);
					pnlCenterLabel.setPreferredSize(new Dimension(100, 0));
					pnlCenterLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						lblCompany = new JXLabel("Company");
						pnlCenterLabel.add(lblCompany);
					}
					{
						lblProject = new JXLabel("Project");
						pnlCenterLabel.add(lblProject);
					}
				}
				{
					JPanel pnlCenterComponents = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlCenter.add(pnlCenterComponents, BorderLayout.CENTER);
					pnlCenterComponents.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlCenterComponents.add(pnlCompany);
						pnlCompany.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						{
							lookupCompany = new _JLookup(null, "Company", 0);
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setPreferredSize(new Dimension(50, 0));
							lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
							lookupCompany.addLookupListener(new LookupListener() {
								
								@Override
								public void lookupPerformed(LookupEvent event) {
									Object []data = ((_JLookup) event.getSource()).getDataSet();
									
									if(data != null) {
										String co_id = (String) data[0];
										String company_name = (String) data[1];
									
										txtCompany.setText(company_name);
										lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(co_id));
									}
								}
							});
						}
						{
							txtCompany = new JTextField();
							pnlCompany.add(txtCompany);
						}
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
						pnlCenterComponents.add(pnlProject);
						pnlProject.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						{
							lookupProject = new _JLookup(null, "Project", 0);
							pnlProject.add(lookupProject, BorderLayout.WEST);
							lookupProject.setPreferredSize(new Dimension(50, 0));
							lookupProject.addLookupListener(new LookupListener() {
								
								@Override
								public void lookupPerformed(LookupEvent event) {
									Object []data = ((_JLookup) event.getSource()).getDataSet();
									
									if(data != null) {
										String proj_name = (String) data[1];
										
										txtProject.setText(proj_name);
									}
								}
							});
						}
						{
							txtProject = new JTextField();
							pnlProject.add(txtProject);
						}
					}
				}
			}
			{
				pnlSouth = new JXPanel(new GridLayout(1, 4, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 25));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
				}
			}
		}
	}
	
	private String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if(action.equals("Preview")) {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("company_name", txtCompany.getText());
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(lookupCompany.getValue())));
			
			FncReport.generateReport("/Reports/rptGeneralAssemblyHOA.jasper", title, mapParameters);
		}
	}
}
