package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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

public class MA_Schedule_Reports extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 5159650933602942626L;

	static String title = "MA Schedule Reports";
	Dimension frameSize = new Dimension(500, 600);
	Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;


	private JXLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;

	private JXLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;

	private JXLabel lblPhase;
	private _JLookup lookupPhase;
	private JXTextField txtPhase;

	private JCheckBox chkIncludeMgmtHoldAcc;

	private JXPanel pnlSouth;
	private JButton btnPreview;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	private _JDateChooser dteDateFrom;
	private _JDateChooser dteDateTo;

	private JXPanel pnlNorth;

	private JXPanel pnlNorthLabel;

	private JXPanel pnlNorthComponents;


	private JXPanel pnlNorthLookUp;

	private JXPanel pnlNorthTxtField;


	public MA_Schedule_Reports() {
		super(title, false, true, true, true);
		initGUI();
	}

	public MA_Schedule_Reports(String title) {
		super(title);
		initGUI();
	}

	public MA_Schedule_Reports(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(FncGlobal.lineBorder);
				pnlNorth.setPreferredSize(new Dimension(0, 170));
				{
					pnlNorthLabel = new JXPanel (new GridLayout(5, 1, 3, 3));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					pnlNorthLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
					pnlNorthLabel.setPreferredSize(new Dimension(100, 0));
					{
						lblCompany = new JXLabel("Company");
						pnlNorthLabel.add(lblCompany);
					}
					{
						lblProject = new JXLabel("Project");
						pnlNorthLabel.add(lblProject);
					}
					{
						lblPhase = new JXLabel("Phase");
						pnlNorthLabel.add(lblPhase);
					}
					{
						JXLabel lblDateFrom = new JXLabel("Date From");
						pnlNorthLabel.add(lblDateFrom);
					}
					{
						JXLabel lblDateTo = new JXLabel("Date To");
						pnlNorthLabel.add(lblDateTo);
					}
				}
				{
					pnlNorthComponents = new JXPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthComponents, BorderLayout.CENTER);
					{
						pnlNorthLookUp = new JXPanel(new GridLayout(5, 1, 3, 3));
						pnlNorthComponents.add(pnlNorthLookUp, BorderLayout.WEST);
						pnlNorthLookUp.setBorder(new EmptyBorder(5, 5, 5, 5));
						pnlNorthLookUp.setPreferredSize(new Dimension(130, 0));
						
						{
							lookupCompany = new _JLookup(null, "Company");
							pnlNorthLookUp.add(lookupCompany);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();

									if(data != null){
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
							lookupProject = new _JLookup(null, "Project","Please select company.");
							pnlNorthLookUp.add(lookupProject);
							lookupProject.setReturnColumn(0);
							lookupProject.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();

									if(data != null){

										txtProject.setText(data[1].toString());
										lookupPhase.setLookupSQL(sqlPhase(data[0].toString()));
										manager.focusNextComponent();
									}else{
										txtProject.setText("");
									}
								}
							});
						}
						
						{
							lookupPhase = new _JLookup(null, "Phase", "Please select project.");
							pnlNorthLookUp.add(lookupPhase);
							lookupPhase.setReturnColumn(0);
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
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNorthLookUp.add(dteDateFrom);	
							dteDateFrom.setDate(null);
							dteDateFrom.setEnabled(true);
							dteDateFrom.setDate(null);
						}
						{
							dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNorthLookUp.add(dteDateTo);	
							dteDateTo.setDate(null);
							dteDateTo.setEnabled(true);
							dteDateTo.setDate(null);
						}
						
					}
					
					pnlNorthTxtField = new JXPanel(new GridLayout(5, 1, 3, 3));
					pnlNorthComponents.add(pnlNorthTxtField, BorderLayout.CENTER);
					pnlNorthTxtField.setBorder(new EmptyBorder(5, 5, 5, 5));

				
					{
						txtCompany = new JTextField();
						pnlNorthTxtField.add(txtCompany);
						txtCompany.setEditable(false);
					}
					{
						txtProject = new JTextField();
						pnlNorthTxtField.add(txtProject);
						txtProject.setEditable(false);
					}

					
					{
						txtPhase = new JXTextField("");
						pnlNorthTxtField.add(txtPhase);
						txtPhase.setEditable(false);
					}
					
				}	
			}
			{
				pnlSouth = new JXPanel(new GridLayout(1,7,3,3));
				pnlSouth = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setBorder(new EmptyBorder(5, 5, 5, 5));
				pnlSouth.setPreferredSize(new Dimension (0,40));
				
				{
					JPanel pnlSouthWest = new JPanel(new BorderLayout(5, 5)); 
					pnlSouth.add(pnlSouthWest, BorderLayout.WEST); 
					pnlSouthWest.setPreferredSize(new Dimension(250, 100)); 
				}
				{
					JPanel pnlSouthCenter = new JPanel(new BorderLayout(5, 5)); 
					pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER); 
					
					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setActionCommand("preview");
						btnPreview.setAlignmentX(0.5f);
						btnPreview.setAlignmentY(0.5f);
						btnPreview.setMaximumSize(new Dimension(150, 100));
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.addActionListener(this);
					}
					
				}
				{
					JPanel pnlSoutEast = new JPanel(new BorderLayout(5, 5)); 
					pnlSouth.add(pnlSoutEast, BorderLayout.EAST); 
					pnlSoutEast.setPreferredSize(new Dimension(250, 100)); 
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, dteDateFrom, dteDateTo, btnPreview));
		this.setBounds(0, 0, 650, 250);
	}


	private String sqlPhase(String proj_id) {
		String phase = "SELECT * FROM view_lookup_phase('"+ proj_id +"');";
		return phase;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if(action.equals("preview")){
			
			String criteria = "MA Schedule Reports";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			Map<String, Object> mapMAScheduleReports = new HashMap<String, Object>();
			
			mapMAScheduleReports.put("company", txtCompany.getText());
			mapMAScheduleReports.put("project", txtProject.getText());
			mapMAScheduleReports.put("phase", txtPhase.getText());
			mapMAScheduleReports.put("co_id", lookupCompany.getValue());
			mapMAScheduleReports.put("phase_id", lookupPhase.getValue());
			mapMAScheduleReports.put("proj_id", lookupProject.getValue());
			mapMAScheduleReports.put("date_from", dteDateFrom.getDate());
			mapMAScheduleReports.put("date_to", dteDateTo.getDate());
			
//			System.out.println("company"+ txtCompany.getText());
//			System.out.println("project"+ txtProject.getText());
//			System.out.println("phase"+ txtPhase.getText());
//			System.out.println("co_id"+ lookupCompany.getValue());
//			System.out.println("phase_id"+ lookupPhase.getValue());
//			System.out.println("proj_id"+ lookupProject.getValue());
//			System.out.println("date_from"+ dteDateFrom.getDate());
//			System.out.println("date_to"+ dteDateTo.getDate());
			
			FncReport.generateReport("/Reports/rptMAScheduleReports.jasper", reportTitle, lookupCompany.getValue(), mapMAScheduleReports);		
		
		}
	}
}