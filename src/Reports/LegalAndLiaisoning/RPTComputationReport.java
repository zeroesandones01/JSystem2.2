package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class RPTComputationReport extends _JInternalFrame implements _GUI, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Dimension frameSize = new Dimension(700, 300);
	private JPanel pnlMain;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _JLookup lookupProject;
	private JTextField txtProject;
	private _JLookup lookupDocType;
	private JTextField txtDocType;
	private _JLookup lookupPhase;
	private JButton btnPreview;
	private JButton btnCancel;
	private String co_id;
	private String proj_id;
	private String co_name;
	private String proj_name;
	private JComboBox cmbYear;
	private String company_logo;


	public RPTComputationReport() {
		super("RPT Computation Report", true, true, true, true);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				JPanel pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("RPT Details" ));// XXX
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					c.insets = new Insets(5, 5, 5, 5);
					
					//LINE 1
					{
						c.weightx = 0;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 0; 
						
						JLabel lblCompany = new JLabel("Company:");
						pnlCenter.add(lblCompany, c);
						lblCompany.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 0; 
						
						lookupCompany = new _JLookup(null, "Company");
						pnlCenter.add(lookupCompany, c);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setFont(FncGlobal.sizeTextValue);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!=null) {
									co_id = (String) data[0];
									co_name = (String) data[1];
									company_logo = (String) data[3];
									
									txtCompany.setText(co_name);
									lookupProject.setLookupSQL(SQL_PROJECT(co_id));

								}							
							}
						});
					}
					{
						c.weightx = 1.5;
						c.weighty = 1;

						c.gridx = 2; 
						c.gridy = 0; 
						
						txtCompany = new JTextField("");
						pnlCenter.add(txtCompany, c);
						txtCompany.setEditable(false);
						txtCompany.setFont(FncGlobal.sizeTextValue);
					}
					
					//LINE 2
					{
						c.weightx = 0;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 1; 
						
						JLabel lblProject = new JLabel("Project:");
						pnlCenter.add(lblProject, c);
						lblProject.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.5;
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
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!=null) {
									proj_id = (String) data[0];
									proj_name = (String) data[1];
									
									txtProject.setText(proj_name);
									//lookupPhase.setLookupSQL(getPhase(proj_id));
									lookupPhase.setLookupSQL(SQL_PHASE(proj_id));
						
								}							
							}
						});
					}
					{
						c.weightx = 1.5;
						c.weighty = 1;

						c.gridx = 2; 
						c.gridy = 1; 
						
						txtProject = new JTextField("");
						pnlCenter.add(txtProject, c);
						txtProject.setEditable(false);
						txtProject.setFont(FncGlobal.sizeTextValue);
					}
					
					//LINE 3
					{
						c.weightx = 0;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 2; 
						
						JLabel lblDocType = new JLabel("Doc. Type:");
						pnlCenter.add(lblDocType, c);
						lblDocType.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 2; 
						
						lookupDocType = new _JLookup(null, "Doc Type");
						pnlCenter.add(lookupDocType, c);
						lookupDocType.setReturnColumn(0);
						lookupDocType.setLookupSQL(SQL_DOCTYPE());
						lookupDocType.setFont(FncGlobal.sizeTextValue);
						lookupDocType.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!=null) {
									String doc_type = (String) data[1];
									
									txtDocType.setText(doc_type);
						
								}							
							}
						});
					}
					{
						c.weightx = 1.5;
						c.weighty = 1;

						c.gridx = 2; 
						c.gridy = 2; 
						
						txtDocType = new JTextField("");
						pnlCenter.add(txtDocType, c);
						txtDocType.setEditable(false);
						txtDocType.setFont(FncGlobal.sizeTextValue);
					}
					
					//LINE 4
					{
						c.weightx = 0;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 3; 
						
						JLabel lblPhase = new JLabel("Phase:");
						pnlCenter.add(lblPhase, c);
						lblPhase.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 3; 
						
						lookupPhase = new _JLookup(null, "Select phase");
						pnlCenter.add(lookupPhase, c);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setFont(FncGlobal.sizeTextValue);
					}
					
					//LINE 5
					{
						c.weightx = 0;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 4; 
						
						JLabel lblYear = new JLabel("Year:");
						pnlCenter.add(lblYear, c);
						lblYear.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 4; 
						
						String[] year = { "",
										"2019", 
										"2020", 
										"2021", 
										"2022", 
										"2023",
										"2024",
										"2025"};
						
						cmbYear = new JComboBox(year);
						pnlCenter.add(cmbYear, c);
						cmbYear.setFont(FncGlobal.sizeTextValue);
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1,2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					//btnPreview.setEnabled(false);
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setActionCommand("Preview");
					btnPreview.setFont(FncGlobal.sizeControls);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setMnemonic(KeyEvent.VK_C);
					//btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setFont(FncGlobal.sizeControls);
				}
			}
		}
	}
	
	private String SQL_DOCTYPE() {
		
		String sql =
				"select\n" + 
				"doc_id as \"Doc ID\",\n" + 
				"doc_desc as \"Doc Tyoe\"\n" + 
				"from mf_system_doc\n" + 
				"where doc_id in ('92', '181')\n" + 
				"and status_id = 'A'";
		
		return sql;
		
	}
	
	private String getPhase(String proj_id) {
		
		String sql =
				"select\n" + 
				"phase as \"Phase\"\n" + 
				"from mf_sub_project\n" + 
				"where proj_id = '"+proj_id+"' \n" + 
				"and status_id = 'A'\n" + 
				"order by sub_proj_id ASC";
		
		return sql;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview")) {		preview();}
		
		if(e.getActionCommand().equals("Cancel")) {			cancel();}
	}

	private void cancel() {
		
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupDocType.setValue(null);
		txtDocType.setText("");
		lookupPhase.setValue("");
		cmbYear.setSelectedIndex(0);		
	}

	private void preview() {

		String criteria = "RPT Computation Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		
		
		String doc_type = lookupDocType.getText();
		String phase = lookupPhase.getText();
		Integer year = 0;
		int index = cmbYear.getSelectedIndex();
		
		if(index == 0) {
			year = 0;
		}else {
			year = Integer.parseInt((String)cmbYear.getSelectedItem());
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_co_id", co_id);	
		mapParameters.put("p_projid", proj_id);	
		mapParameters.put("p_co_name", co_name);	
		mapParameters.put("p_proj_name", proj_name);	
		mapParameters.put("p_logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("p_doc_type", doc_type);
		mapParameters.put("p_phase", phase);
		mapParameters.put("p_year", year);

		FncReport.generateReport("/Reports/rptRPTComputation.jasper", reportTitle, co_name, mapParameters);		
		
	}

}
