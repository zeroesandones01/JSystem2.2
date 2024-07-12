package Reports.LegalAndLiaisoning;

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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;


import components._JInternalFrame;

/**
 * @author Allei Anne Beltran
 */

public class TransferTaxDecMonitoring extends _JInternalFrame implements _GUI, ActionListener, AncestorListener {

	private static final long serialVersionUID = 5159650933602942626L;

	static String title = "Transfer Tax Dec Monitoring";
	Dimension frameSize = new Dimension(510, 180);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;

	private JXLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;

	private JXLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;

	private JXLabel lblYear;
	private _JLookup lookupYear;
	
	private JXPanel pnlSouth;
	private JButton btnPreview;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	private JXPanel pnlNorth;
	private JXPanel pnlNorthComponents;
	private JXPanel pnlNorthLookUp;
	private JXPanel pnlNorthLabel;
	private JXPanel pnlNorthTxtField;



	public TransferTaxDecMonitoring() {
		super(title, false, true, false, true);
		initGUI();
	}

	public TransferTaxDecMonitoring(String title) {
		super(title);
		initGUI();
	}

	public TransferTaxDecMonitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.addAncestorListener(this);
		{
			pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));		
			{
				pnlNorth = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.CENTER);
				pnlNorth.setBorder(FncGlobal.lineBorder);
				pnlNorth.setPreferredSize(new Dimension(0, 120));
				{
					pnlNorthLabel = new JXPanel (new GridLayout(3, 1, 3, 3));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					pnlNorthLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
					pnlNorthLabel.setPreferredSize(new Dimension(70, 0));

					{
						lblCompany = new JXLabel("Company");
						pnlNorthLabel.add(lblCompany);
					}
					{
						lblProject = new JXLabel("Project");
						pnlNorthLabel.add(lblProject);
					}
					{
						lblYear = new JXLabel("Year");
						pnlNorthLabel.add(lblYear);
					}
				}

				{
					pnlNorthComponents = new JXPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthComponents, BorderLayout.CENTER);
					{
						pnlNorthLookUp = new JXPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthComponents.add(pnlNorthLookUp, BorderLayout.WEST);
						pnlNorthLookUp.setBorder(new EmptyBorder(5, 5, 5, 5));
						pnlNorthLookUp.setPreferredSize(new Dimension(50, 0));		
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
										lookupYear.setLookupSQL(getYear());
										manager.focusNextComponent();
									}else{
										txtProject.setText("");
									}
								}
							});
						}
						{
							lookupYear = new _JLookup(null, "Year", "Please select project.");
							pnlNorthLookUp.add(lookupYear);
							lookupYear.setReturnColumn(1);
							lookupYear.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										manager.focusNextComponent();
									}else{
										JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select project first.", "Year", JOptionPane.WARNING_MESSAGE);
									}
								}
							});
						}

						pnlNorthTxtField = new JXPanel(new GridLayout(3, 1, 3, 3));
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
					}
				}
			}		
			{
				pnlSouth = new JXPanel(new GridLayout(1,7,3,3));
				pnlSouth = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setBorder(new EmptyBorder(5, 5, 5, 5));
				pnlSouth.setPreferredSize(new Dimension (60,35));
				
				{
					JPanel pnlSouthWest = new JPanel(new BorderLayout(5, 5)); 
					pnlSouth.add(pnlSouthWest, BorderLayout.WEST); 
					pnlSouthWest.setPreferredSize(new Dimension(200, 0)); 
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
						btnPreview.setMaximumSize(new Dimension(100, 30));
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.addActionListener(this);
					}
					
				}
				{
					JPanel pnlSoutEast = new JPanel(new BorderLayout(5, 5)); 
					pnlSouth.add(pnlSoutEast, BorderLayout.EAST); 
					pnlSoutEast.setPreferredSize(new Dimension(200, 0)); 
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupYear, btnPreview));
	}
	
	private String getYear(){

		String sql = 
			"select " +
			"year_no as \"Year No.\", \n" +
			"year_txt as \"Year\" \n" +
			"from mf_ttdmonitoring_year \r\n" +
			"order by year_txt::int " ;
		return sql;

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
		String action = e.getActionCommand();

		if(action.equals("preview")){
			
			String criteria = "Transfer Tax Dec Monitoring";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			Map<String, Object> mapTransferTaxDecMonitoring = new HashMap<String, Object>();
			
			mapTransferTaxDecMonitoring.put("Company", txtCompany.getText());
			//mapTransferTaxDecMonitoring.put("Proj_id", lookupProject.getValue());
			mapTransferTaxDecMonitoring.put("Project", lookupProject.getValue());
			mapTransferTaxDecMonitoring.put("Year", lookupYear.getValue());

			
			System.out.println("company"+ txtCompany.getText());
			//System.out.println("proj_id"+ lookupProject.getValue());
			System.out.println("project"+ txtProject.getText());
			System.out.println("year"+ lookupYear.getValue());
			
			FncReport.generateReport("/Reports/rptTransferTaxDecMonitoring.jasper", reportTitle, lookupCompany.getValue(), mapTransferTaxDecMonitoring);		
		
		}
	}

}