package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Accounting.Collections.CheckStatusMonitoring;
import Functions.FncGlobal;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Reports.Accounting.SummaryofDeposits;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class Loan_Released_EPEB extends _JInternalFrame implements _GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Dimension frameSize = new Dimension(600,250);
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _JLookup lookupProject;
	private JTextField txtProject;
	private _JLookup lookupPhase;
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnCancel;
	private String co_id = "";
	private String co_name;
	private String proj_id = "";
	private String proj_name;

	public Loan_Released_EPEB() {
		super("Loan Released EPEB", true, true, true, true);
		initGUI();	
	}

	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Details"));
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					c.insets = new Insets(5, 5, 5, 5);
					//company
					{
						c.weightx = 0;
						c.weighty = 1;
						
						c.gridx = 0;
						c.gridy = 0;
						
						JLabel lblCompany = new JLabel("Company", JLabel.CENTER);
						pnlCenter.add(lblCompany, c);
						lblCompany.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.25;
						c.weighty = 1;
						
						c.gridx = 1;
						c.gridy = 0;
						
						lookupCompany = new _JLookup(null, "Company");
						pnlCenter.add(lookupCompany, c);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SummaryofDeposits.company());
						lookupCompany.setFont(FncGlobal.sizeTextValue);
						lookupCompany.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								// TODO Auto-generated method stub
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								
								if(data!=null) {
									co_id = (String) data[0];
									co_name = (String) data[1];
									
									txtCompany.setText(co_name);
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
									btnPreview.setEnabled(true);
								}
							}
						});
					}
					{
						c.weightx = 1;
						c.weighty = 1;
						
						c.gridx = 2;
						c.gridy = 0;
						
						txtCompany = new JTextField();
						pnlCenter.add(txtCompany, c);
						txtCompany.setEditable(false);
						txtCompany.setFont(FncGlobal.sizeTextValue);
					}
					
					//project
					{
						c.weightx = 0;
						c.weighty = 1;
						
						c.gridx = 0;
						c.gridy = 1;
						
						JLabel lblProject = new JLabel("Project", JLabel.CENTER);
						pnlCenter.add(lblProject, c);
						lblProject.setEnabled(true);
						lblProject.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.25;
						c.weighty = 1;
						
						c.gridx = 1;
						c.gridy = 1;
						
						lookupProject = new _JLookup(null, "Project");
						pnlCenter.add(lookupProject, c);
						lookupProject.setReturnColumn(0);
						lookupProject.setFont(FncGlobal.sizeTextValue);
						lookupProject.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								// TODO Auto-generated method stub
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								
								if(data!=null) {
									proj_id = (String) data[0];		
									proj_name = (String) data[1];
									
									txtProject.setText(proj_name);
									lookupPhase.setLookupSQL(SQL_PHASE(proj_id));
									btnPreview.setEnabled(true);
									
								}
							}
						});
					}
					{
						c.weightx = 1;
						c.weighty = 1;
						
						c.gridx = 2;
						c.gridy = 1;
						
						txtProject = new JTextField();
						pnlCenter.add(txtProject, c);
						txtProject.setEditable(false);
						txtProject.setFont(FncGlobal.sizeTextValue);
					}
					
					//phase
					{
						c.weightx = 0;
						c.weighty = 1;
						
						c.gridx = 0;
						c.gridy = 3;
						
						JLabel lblPhase = new JLabel("Phase", JLabel.CENTER);
						pnlCenter.add(lblPhase, c);
						lblPhase.setEnabled(true);
						lblPhase.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.25;
						c.weighty = 1;
						
						c.gridx = 1;
						c.gridy = 3;
						
						lookupPhase = new _JLookup(null, "Select phase");
						pnlCenter.add(lookupPhase, c);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setFont(FncGlobal.sizeTextValue);
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1,2,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
					btnPreview.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setFont(FncGlobal.sizeControls);
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getActionCommand().equals("Preview")) {		preview();}
		
		if(e.getActionCommand().equals("Cancel")) {			cancel();}
		
		
	}

	private void cancel() {
		// TODO Auto-generated method stub
		co_id = "";
		proj_id = "";
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupPhase.setValue(null);
		btnPreview.setEnabled(false);
		
	}

	private void preview() {
		// TODO Auto-generated method stub
		String phase = lookupPhase.getText();
		String co_name = txtCompany.getText();
		String proj_name = txtProject.getText();
		String strPhase = "";
		
		if(proj_name.equals("") || proj_name.equals("null")) {
			proj_name = "ALL PROJECTS";
		}else {
			proj_name = txtProject.getText();
		}
		
		if(phase.equals("") || phase.equals("null") || phase == null) {
			strPhase = "ALL";
		}else {
			strPhase = lookupPhase.getText();
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_co_id", co_id);
		mapParameters.put("p_co_name", co_name);
		mapParameters.put("p_proj_id", proj_id);
		mapParameters.put("p_phase", phase);
		mapParameters.put("p_proj_name", proj_name);
		mapParameters.put("p_strphase", strPhase);
		
		FncReport.generateReport("/Reports/rptLoanReleasedEPEB.jasper", "Loan Released EPEB", mapParameters);
	}

}
