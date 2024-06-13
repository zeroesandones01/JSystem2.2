package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class ProcessingCostLiquidationMonitoring extends _JInternalFrame implements _GUI, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5331321268144020882L;
	private JPanel pnlMain;
	private _JLookup lookupParticulars;
	private _JLookup lookupRPLF;
	private JLabel lblParticularDisplay;
	private JButton btnPreview;
	private JButton btnCancel;
	private _JDateChooser dteCurrent;
	private JButton btnPreviewAll;
	
	public ProcessingCostLiquidationMonitoring(){
		//super("Processing Cost Liquidation Monitoring");
		super("Processing Cost Liquidation Monitoring", false, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle("Processing Cost Liquidation Monitoring");
		this.setSize(new Dimension(500, 200));
		this.setPreferredSize(new Dimension(500, 200));
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					JPanel pnlWestLabel = new JPanel(new GridLayout(3,1,5,5));
					pnlCenter.add(pnlWestLabel, BorderLayout.WEST);
					{
						JLabel lblParticulars = new JLabel("Particulars:");
						pnlWestLabel.add(lblParticulars);
					}
					{
						JLabel lblRPLF = new JLabel("RPLF No.:");
						pnlWestLabel.add(lblRPLF);
					}
					{
						JLabel lblDate = new JLabel("As of Date:");
						pnlWestLabel.add(lblDate);
					}
				}
				{
					JPanel pnlCenterComponents = new JPanel (new GridLayout(3,1,5,5));
					pnlCenter.add(pnlCenterComponents, BorderLayout.CENTER);
					{
						JPanel pnlParticulars = new JPanel(new BorderLayout(3,3));
						pnlCenterComponents.add(pnlParticulars, BorderLayout.CENTER);
						{
							JPanel pnlWestParticular = new JPanel(new BorderLayout(3,3));
							pnlParticulars.add(pnlWestParticular, BorderLayout.WEST);
							pnlWestParticular.setPreferredSize(new Dimension(100,0));
							{
								lookupParticulars = new _JLookup(null, "Particulars");
								pnlWestParticular.add(lookupParticulars);
								lookupParticulars.setLookupSQL(getPcostParticulars());
								lookupParticulars.setReturnColumn(0);
								lookupParticulars.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String pcost_id = (String) data[0];
											
											FncSystem.out("SQL for PCOST Particulars", getPcostParticulars());
											lblParticularDisplay.setText(String.format("[%s]", data[1]));
											lookupRPLF.setEnabled(true);
											lookupRPLF.setValue(null);
											lookupRPLF.setLookupSQL(getRPLF(pcost_id));
											btnPreview.setEnabled(true);
										}
									}
								});
							}
						}
						{
							JPanel pnlCenterParticular = new JPanel(new BorderLayout(5,5));
							pnlParticulars.add(pnlCenterParticular, BorderLayout.CENTER);
							{
								lblParticularDisplay = new JLabel("[]");
								pnlCenterParticular.add(lblParticularDisplay);
							}
						}
					}
					{
						JPanel pnlRPLF = new JPanel(new BorderLayout(3,3));
						pnlCenterComponents.add(pnlRPLF, BorderLayout.CENTER);
						{
							JPanel pnlWestRPLF = new JPanel(new BorderLayout(3,3));
							pnlRPLF.add(pnlWestRPLF, BorderLayout.WEST);
							pnlWestRPLF.setPreferredSize(new Dimension(100,0));
							{
								lookupRPLF = new _JLookup(null, "RPLF No");
								pnlWestRPLF.add(lookupRPLF);
								lookupRPLF.setReturnColumn(0);
								lookupRPLF.setEnabled(false);
							}
						}
					}
					{
						JPanel pnlDate = new JPanel(new BorderLayout(3,3));
						pnlCenterComponents.add(pnlDate, BorderLayout.CENTER);
						{
							dteCurrent = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlDate.add(dteCurrent, BorderLayout.WEST);
							pnlDate.setPreferredSize(new Dimension(100,0));
							dteCurrent.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						}
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1,3,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnPreviewAll = new JButton("Preview All");
					pnlSouth.add(btnPreviewAll);
					btnPreviewAll.setActionCommand("Preview All");
					btnPreviewAll.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Cancel")) { Cancel();}
		
		if(actionCommand.equals("Preview")) { Preview();}
		
		if(actionCommand.equals("Preview All")) { PreviewAll();}
	}
	
	private void PreviewAll() {
		// TODO Auto-generated method stub
		
		String pcost_id = "";
		String rplf_no = "";
		String emp_alias = UserInfo.Alias;
		java.util.Date as_of_date = dteCurrent.getDate();
		String criteria = "PCOST Liquidation Monitoring";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_pcost_id", pcost_id);
		mapParameters.put("p_rplf_no", rplf_no);
		mapParameters.put("p_as_of_date", as_of_date);
		mapParameters.put("p_emp_alias", emp_alias);
		
		FncReport.generateReport("/Reports/rptPCOST_Liquidation_Monitoring.jasper", reportTitle, null, mapParameters);
	}

	private void Preview() {
		// TODO Auto-generated method stub
		
		String pcost_id = lookupParticulars.getValue();
		String rplf_no = lookupRPLF.getValue();
		String emp_alias = UserInfo.Alias;
		java.util.Date as_of_date = dteCurrent.getDate();
		String criteria = "PCOST Liquidation Monitoring";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		if(rplf_no == null) {
			rplf_no = "";
		}else {
			rplf_no = lookupRPLF.getValue();
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_pcost_id", pcost_id);
		mapParameters.put("p_rplf_no", rplf_no);
		mapParameters.put("p_as_of_date", as_of_date);
		mapParameters.put("p_emp_alias", emp_alias);
		
		FncReport.generateReport("/Reports/rptPCOST_Liquidation_Monitoring.jasper", reportTitle, null, mapParameters);
	}

	private void Cancel() {
		// TODO Auto-generated method stub
		if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to clear all fields?", "Confirmation", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			
			JOptionPane.showMessageDialog(getContentPane(), "Successfully cleared!", "Information", JOptionPane.INFORMATION_MESSAGE);
			
			lookupParticulars.setValue(null);
			lookupRPLF.setValue(null);
			lookupRPLF.setEnabled(false);
			btnPreview.setEnabled(false);
			lblParticularDisplay.setText("[]");
		}
	}

	private static String getPcostParticulars() {
		String sql =
				"select\n" + 
				"pcostdtl_id as \"Pcost ID\",\n" + 
				"pcostdtl_desc as \"Pcost Desc\",\n" + 
				"status_id as \"Status\"\n" + 
				"from mf_processing_cost_dl \n" + 
				"where pcostdtl_type = 'L'\n" + 
				"and status_id = 'A' \n" + 
				"order by pcostdtl_id::INT ASC";
		
		return sql;
	}
	
	private static String getRPLF(String pcost_id) {
		String sql =
				"select distinct on (rplf_no)\n" + 
				"rplf_no as \"RPLF No\" \n" + 
				"from rf_processing_cost\n" + 
				"where pcostid_dl = '"+pcost_id+"'\n" + 
				"and status_id = 'A'\n" + 
				"order by rplf_no, date_created DESC";
		
		return sql;
	}

}
