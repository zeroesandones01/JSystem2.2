package Reports.LegalAndLiaisoning;

import interfaces._GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorListener;

import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class REM_Status_List_Report extends _JInternalFrame implements ActionListener, _GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String title = "REM Status List Report";
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private JPanel mainNorth;
	private JPanel pnlNorth;
	private JPanel pnlNorthLabels;
	private JLabel lblStatus;
	private JLabel lblDate;
	private JPanel pnlNorthComponents;
	private JComboBox cmbStatus;
	private _JDateChooser dteDateFrom;
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JPanel pnlDate;
	private JPanel pnlDateWest;
	
	private JLabel lblCompany,lblProject;
	private JTextField txtCompany, txtProject;
	private _JLookup lookupCompany, lookupProject;
	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();


	public REM_Status_List_Report() {
		super(title, false, true, false, true);
		initGUI();
	}
	/*EDITED BY JED 2021-03-12 : ADDITIONAL REPORT TCT WITH PROB C/0 MAM RHEA*/

	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		this.setTitle(title);
		this.setSize(new Dimension(480,250));
		this.setPreferredSize(new Dimension(480,250));
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				{
					mainNorth = new JPanel(new BorderLayout(5,5));
					pnlMain.add(mainNorth,BorderLayout.NORTH);
					mainNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details" ));

					{

						JPanel lblMainNorth = new JPanel(new GridLayout(2,1,3,3));
						lblMainNorth.setBorder(new EmptyBorder(5,5,5,5));
						mainNorth.add(lblMainNorth,BorderLayout.WEST);
						{
							lblCompany = new JLabel("Company:");
							lblMainNorth.add(lblCompany);

							lblProject = new JLabel("Project:");
							lblMainNorth.add(lblProject);

						}
					}
					{
						JPanel mainNorthComponents = new JPanel(new GridLayout(2,1,3,3));
						mainNorthComponents.setBorder(new EmptyBorder(5,5,5,5));
						mainNorth.add(mainNorthComponents,BorderLayout.CENTER);
						{
							{
								lookupCompany = new _JLookup(null, "Company");
								mainNorthComponents.add(lookupCompany);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setPreferredSize(new Dimension(0,20));
								lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											String co_id = (String) data[0];
											txtCompany.setText(data[1].toString());
											txtCompany.setToolTipText(data[2].toString());
											lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));
											manager.focusNextComponent();
		
										}else{
											txtCompany.setText("");
										}
									}

								});

							}
							{
								lookupProject = new _JLookup(null, "Project");
								mainNorthComponents.add(lookupProject);
								lookupProject.setReturnColumn(0);
								lookupProject.setPreferredSize(new Dimension(0,20));
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {//XXX Project
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											txtProject.setText(data[1].toString());
											manager.focusNextComponent();
											//preview();
										}else{
											txtProject.setText("");
										}
									}

								});
							}
						}
					}

					JPanel mainNorthComponents2 = new JPanel(new GridLayout(2,1,3,3));
					mainNorthComponents2.setBorder(new EmptyBorder(5,5,5,5));
					mainNorth.add(mainNorthComponents2,BorderLayout.EAST);
					{
						txtCompany = new JTextField();
						mainNorthComponents2.add(txtCompany);
						txtCompany.setEditable(false);
						txtCompany.setPreferredSize(new Dimension(310,20));
					}

					{
						txtProject = new JTextField();
						mainNorthComponents2.add(txtProject);
						txtProject.setEditable(false);
						txtProject.setPreferredSize(new Dimension(310,20));
					}

				}
				
				
				pnlNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlNorth, BorderLayout.CENTER);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Rem Status List" ));
				
				{
					pnlNorthLabels = new JPanel(new GridLayout(2,1,5,5));
					pnlNorth.add(pnlNorthLabels, BorderLayout.WEST);
			
					{
						lblStatus = new JLabel("Status:");
						pnlNorthLabels.add(lblStatus);
					}
					{
						lblDate = new JLabel("As of:");
						pnlNorthLabels.add(lblDate);
					}
				}
				{
					pnlNorthComponents = new JPanel(new GridLayout(2,1,5,5));
					pnlNorth.add(pnlNorthComponents, BorderLayout.CENTER);
					{
						String status[] = {"", 
								"1 - BIR Submission (For CAR Application)",
								"2 - Submitted to BIR - BIR Withouth CAR",
								"3 - Real Property Tax Updating",
								"4 - For RD Submission - Sale",
								"5 - Submitted to RD - For Processing of Sale",
								"6 - Sale - With EPEB",
								"7 - For RD Submission - Mortgage",
								"8 - Individual TCT with Problem after Transmittal to LMD",
								"9 - Submitted to RD - For Processing of Mortgage",
								"10 - TD Application"};
						cmbStatus = new JComboBox(status);
						pnlNorthComponents.add(cmbStatus);
					}
					{
						pnlDate = new JPanel(new BorderLayout(5,5));
						pnlNorthComponents.add(pnlDate);
						{
							pnlDateWest = new JPanel(new BorderLayout(5,5));
							pnlDate.add(pnlDateWest, BorderLayout.WEST);
							pnlDateWest.setPreferredSize(new Dimension(130,20));
							{
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDateWest.add(dteDateFrom, BorderLayout.CENTER);
								dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1,3,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));
				{
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.addActionListener(this);
					btnPreview.setActionCommand("Preview");
				}
				{
					pnlSouth.add(Box.createHorizontalBox());
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand().equals("Preview")){
			
			preview();
			
		}
	}

	private void preview() {
		// TODO Auto-generated method stub	
		int index = cmbStatus.getSelectedIndex();
		
		if(index == 0){
			JOptionPane.showMessageDialog(getContentPane(), "Please select a status.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		if(index == 1){
			
			String criteria = "BIR Submission - FOR CAR APPLICATION";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptBIR_Submission.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 2){
			
			String criteria = "Submitted to BIR - BIR Withouth CAR";
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptSubmitted_to_BIR.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 3){
			
			String criteria = "Real Property Tax Updating";			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptRealPropTax_Update.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 4){
			
			String criteria = "For RD Submission - Sale";			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptRD_Submission.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 5){
			
			String criteria = "Submitted to RD - For Processing of Sale";			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptSubmitted_to_RD.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 6){
			
			String criteria = "Sale - With EPEB";			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptSale_WithEPEB.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 7){
			
			String criteria = "For RD Submission - Mortgage";			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptFor_RD_Submission_Mortgage.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 8){
			
			String criteria = "Ind. TCT with Problem after Transmittal to LMD";			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptTCT_With_Problem.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 9){
			
			String criteria = "Submitted to RD - For Processing of Mortgage";			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptSubmitted_to_RD_Mortgage_Proc.jasper", reportTitle, null, mapParameters);
			
		}
		
		if(index == 10){
			
			String criteria = "TD Application";			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
			
			java.util.Date as_of_date = dteDateFrom.getDate();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("p_as_of_date", as_of_date);
			mapParameters.put("p_co_id",lookupCompany.getValue());
			mapParameters.put("p_proj_id", lookupProject.getValue());
			
			FncReport.generateReport("/Reports/rptTD_Application.jasper", reportTitle, null, mapParameters);
			
		}
	}
}
