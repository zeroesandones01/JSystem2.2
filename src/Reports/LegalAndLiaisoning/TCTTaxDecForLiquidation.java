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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class TCTTaxDecForLiquidation extends _JInternalFrame implements _GUI, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Dimension frameSize = new Dimension(800, 300);
	private JPanel pnlMain;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _JLookup lookupProject;
	private JTextField txtProject;
	private _JLookup lookupDocStatus;
	private JTextField txtDocStatus;
	private JDateChooser dteDateTo;
	private JDateChooser dteDateFrom;
	private String co_id;
	private String co_name;
	private String company_logo;
	private String proj_id;
	private String proj_name;
	private JButton btnPreview;
	private JButton btnCancel;

	public TCTTaxDecForLiquidation() {
		super("TCT/Tax Dec For Liquidation", true, true, true, true);
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
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("TCT/Tax Dec Details" ));// XXX
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					c.insets = new Insets(5, 5, 5, 5);
					
					//LINE 1
					{
						c.weightx = 0.25;
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
						c.weightx = 0.25;
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
						c.weightx = 0.25;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 2; 
						
						JLabel lblDocStatus = new JLabel("Doc Status:");
						pnlCenter.add(lblDocStatus, c);
						lblDocStatus.setFont(FncGlobal.sizeLabel);
						
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 2; 
						
						lookupDocStatus = new _JLookup(null, "Doc Status");
						pnlCenter.add(lookupDocStatus, c);
						lookupDocStatus.setReturnColumn(0);
						lookupDocStatus.setFont(FncGlobal.sizeTextValue);
						lookupDocStatus.setLookupSQL(SQL_DocStatus());
						lookupDocStatus.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data!=null) {
									String doc_status = (String) data[1];
									
									txtDocStatus.setText(doc_status);
								}
							}
						});
					}
					{
						c.weightx = 1.5;
						c.weighty = 1;

						c.gridx = 2; 
						c.gridy = 2; 
						
						txtDocStatus = new JTextField("");
						pnlCenter.add(txtDocStatus, c);
						txtDocStatus.setEditable(false);
						txtDocStatus.setFont(FncGlobal.sizeTextValue);
						
					}
					
					//LINE 4
					{
						c.weightx = 0.25;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 3; 
						
						JLabel lblDate = new JLabel("Date From:");
						pnlCenter.add(lblDate, c);
						lblDate.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 3; 
						
						dteDateFrom = new JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						JTextFieldDateEditor dateEditor = (JTextFieldDateEditor)dteDateFrom.getComponent(1);
						pnlCenter.add(dteDateFrom, c);
						dateEditor.setHorizontalAlignment(JTextField.CENTER);
						dteDateFrom.setDate(FncGlobal.getDateToday());
						dteDateFrom.setFont(FncGlobal.sizeTextValue);
						dteDateFrom.setEnabled(true);
		
					}
					
					{
						c.weightx = 0.25;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 4; 
						
						JLabel lblDate = new JLabel("Date To:");
						pnlCenter.add(lblDate, c);
						lblDate.setFont(FncGlobal.sizeLabel);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 4; 
						
						dteDateTo = new JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						JTextFieldDateEditor dateEditor = (JTextFieldDateEditor)dteDateTo.getComponent(1);
						pnlCenter.add(dteDateTo, c);
						dateEditor.setHorizontalAlignment(JTextField.CENTER);
						dteDateTo.setDate(FncGlobal.getDateToday());
						dteDateTo.setFont(FncGlobal.sizeTextValue);
						dteDateTo.setEnabled(true);
		
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
	
	private String SQL_DocStatus() {/*edited by jed 2021-12-27 : doc status 199 is removed change to epeb mortgage*/
		
		String sql = "select trim(status_code) as \"Code\", status_desc as \"Description\", status_id as \"Status\" from mf_tct_taxdec_status where status_code in ('180', '195', '217', '216') and status_id = 'A'";
		
		return sql;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview")) {	preview();}
		
		if(e.getActionCommand().equals("Cancel")) {		cancel();}
		
	}

	private void cancel() {
		// TODO Auto-generated method stub
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupDocStatus.setValue(null);
		txtDocStatus.setText("");
		dteDateFrom.setDate(FncGlobal.getDateToday());
		dteDateTo.setDate(FncGlobal.getDateToday());
		
	}

	private void preview() {
		
		String report1 = "TCT/Tax Dec Liquidation Report";	
		String report2 = "TCT/Tax Dec Liquidation Disbursement Report";		

		String reportTitle1 = String.format("%s (%s)", title.replace(" Report", ""), report1.toUpperCase());
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), report2.toUpperCase());
		
		String doc_status = lookupDocStatus.getText();
		String status_name = txtDocStatus.getText();
		java.util.Date status_date = dteDateFrom.getDate();
		java.util.Date status_dates = dteDateTo.getDate();
		String payee = "";
		
		//Remove by Tim 2022-06-20 moved to function
		
		/*edited by jed 2021-12-27 : doa epeb is added
		  if(doc_status.equals("180") || doc_status.equals("195") ||
		  doc_status.equals("199") || doc_status.equals("217") ) { payee =
		  "REGISTRY OF DEEDS OF PROVINCE OF CAVITE"; }else { payee =
		  "PROVINCIAL TREASURER OF CAVITE"; }
		 */
		
		FncSystem.out("SQL FOR REPORT", "select * from view_tct_forliqui_report('"+co_id+"', '"+proj_id+"', '"+doc_status+"', '"+status_date+"') order by c_client ASC, c_description DESC");
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_co_id", co_id);	
		mapParameters.put("p_proj_id", proj_id);	
		mapParameters.put("p_co_name", co_name);	
		mapParameters.put("p_proj_name", proj_name);	
		mapParameters.put("p_logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("p_doc_status", doc_status);
		mapParameters.put("p_status_name", status_name);
		mapParameters.put("p_status_date", status_date);
		mapParameters.put("p_status_dateTo", status_dates);
		mapParameters.put("p_payee", payee);
		mapParameters.put("p_prep_by", UserInfo.FullName);
		
		FncReport.generateReport("/Reports/rptTCTTaxDecForLiquidation.jasper", reportTitle1, co_name, mapParameters);
		FncReport.generateReport("/Reports/rptTCTTaxDecForLiquiDisbursement.jasper", reportTitle2, co_name, mapParameters);
	}
}
