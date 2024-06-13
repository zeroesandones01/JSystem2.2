package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import com.lowagie.text.Font;
import com.toedter.calendar.JDateChooser;

import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class RevolvingFundsReports extends _JInternalFrame implements _GUI {
	private static final long serialVersionUID = -2861825570192603758L;

	private static String title = "Revolving Funds Reports";
	Dimension frameSize = new Dimension(700, 250);

	JPanel pnlMain;
	_JLookup lookupCompany,lookupProject,lookupRequestType;
	_JDateChooser dateTo,dateFrom;
	JLabel lblCompany,lblProject,lblRequestType,lblDateTo,lblDateFrom,lblProcess;
	JTextField txtCompany,txtProject,txtRequestType;
	JButton btnPrev;
	JComboBox comboChoose;


	public RevolvingFundsReports(){
		super(title, true, true, true, true);
		initGUI();
	}



	@Override
	public void initGUI() {
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setMinimumSize(frameSize);

		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			pnlMain.setBorder(new EmptyBorder(5,5,5,5));
			this.add(pnlMain,BorderLayout.CENTER);
//			pnlMain.addComponentListener(new ComponentListener() {
//				
//				@Override
//				public void componentShown(ComponentEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void componentResized(ComponentEvent e) {
//					int width = pnlMain.getWidth();
//					
//					
//				}
//				
//				@Override
//				public void componentMoved(ComponentEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void componentHidden(ComponentEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
			{
				{
					JXPanel panCenter = new JXPanel(new GridBagLayout());
					pnlMain.add(panCenter, BorderLayout.CENTER);
					{
						GridBagConstraints c = new GridBagConstraints();
						c.fill = GridBagConstraints.BOTH;
						c.ipady = 40;

						{
							c.weightx = 0.5;
							c.weighty = 1;

							c.gridx = 0; 
							c.gridy = 0; 


							JPanel pnlCenter = new JPanel(new GridBagLayout());
							panCenter.add(pnlCenter,c);
							{
								GridBagConstraints gbc = new GridBagConstraints();
								gbc.fill = GridBagConstraints.BOTH;
//								gbc.ipady = 20;
								gbc.insets = new Insets(10, 5, 10, 5);

								{
									gbc.weightx = 0;
									gbc.weighty = 1;

									gbc.gridx = 0;
									gbc.gridy = 0;

									lblCompany = new JLabel("Company:");
									pnlCenter.add(lblCompany,gbc);
									lblCompany.setHorizontalAlignment(JLabel.LEADING);
									lblCompany.setFont(FncGlobal.sizeLabel);
								}
								{
									gbc.weightx = 0.5;
									gbc.weighty = 1;

									gbc.gridx = 1;
									gbc.gridy = 0;

									lookupCompany = new _JLookup(null,"Company");
									pnlCenter.add(lookupCompany,gbc);
									lookupCompany.setReturnColumn(0);
									lookupCompany.setValue("02");
									lookupCompany.setLookupSQL(lookCompany());
									lookupCompany.setFont(FncGlobal.sizeTextValue);
									lookupCompany.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object [] data =((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String company = (String) data [1];
												txtCompany.setText(company);
											}
										}
									});
								}
								{
									gbc.weightx = 1.5;
									gbc.weighty = 1;

									gbc.gridx = 2;
									gbc.gridy = 0;
									gbc.gridwidth = 2;

									txtCompany = new JTextField();
									pnlCenter.add(txtCompany,gbc);
									txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
									txtCompany.setEditable(false);
									txtCompany.setHorizontalAlignment(JTextField.CENTER);
									txtCompany.setFont(FncGlobal.sizeTextValue);
								}

								{
									gbc.weightx = 0;
									gbc.weighty = 1;

									gbc.gridx = 0;
									gbc.gridy = 1;
									gbc.gridwidth = 1;

									lblProject = new JLabel("Project:");
									pnlCenter.add(lblProject,gbc);
									lblProject.setFont(FncGlobal.sizeLabel);
								}
								{
									gbc.weightx = 0.5;
									gbc.weighty = 1;

									gbc.gridx = 1;
									gbc.gridy = 1;

									lookupProject = new _JLookup(null,"Project");
									pnlCenter.add(lookupProject,gbc);
									lookupProject.setReturnColumn(0);
									lookupProject.setValue("15");
									lookupProject.setLookupSQL(lookProject());
									lookupProject.setFont(FncGlobal.sizeTextValue);
									lookupProject.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String project =(String) data [1];
												txtProject.setText(project);

											}
										}
									});
								}
								{
									gbc.weightx = 1.5;
									gbc.weighty = 1;

									gbc.gridx = 2;
									gbc.gridy = 1;
									gbc.gridwidth = 2;

									txtProject = new JTextField();
									pnlCenter.add(txtProject,gbc);
									txtProject.setText("TERRAVERDE RESIDENCES TV");
									txtProject.setEditable(false);
									txtProject.setHorizontalAlignment(JTextField.CENTER);
									txtProject.setFont(FncGlobal.sizeTextValue);
								}
								{
									gbc.weightx = 0;
									gbc.weighty = 1;

									gbc.gridx = 0;
									gbc.gridy = 2;
									gbc.gridwidth = 1;

									lblRequestType = new JLabel("Request Type:");
									pnlCenter.add(lblRequestType,gbc);
									lblRequestType.setFont(FncGlobal.sizeLabel);

								}

								{
									gbc.weightx = 0.5;
									gbc.weighty = 1;

									gbc.gridx = 1;
									gbc.gridy = 2;

									lookupRequestType = new _JLookup(null,"Request Type");
									pnlCenter.add(lookupRequestType,gbc);
									lookupRequestType.setReturnColumn(0);
									lookupRequestType.setLookupSQL(lookRequestType());
									lookupRequestType.setFont(FncGlobal.sizeTextValue);
									lookupRequestType.addLookupListener(new LookupListener() {


										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String request =(String) data[1];
												String type = (String) data[0];

												lookupRequestType.setText(type);
												txtRequestType.setText(request);

											}
										}
									});

								}
								{
									gbc.weightx = 0.5;
									gbc.weighty = 1;

									gbc.gridx = 2;
									gbc.gridy = 2;
									gbc.gridwidth = 2;

									txtRequestType = new JTextField();
									pnlCenter.add(txtRequestType, gbc);
									txtRequestType.setEditable(false);
									txtRequestType.setHorizontalAlignment(JTextField.CENTER);
									txtRequestType.setFont(FncGlobal.sizeTextValue);
								}
								{
									gbc.weightx = 0.5;
									gbc.weighty = 1;

									gbc.gridx = 0;
									gbc.gridy = 3;
									gbc.gridwidth = 1;
									
									lblDateFrom = new JLabel("Date From:");
									pnlCenter.add(lblDateFrom,gbc);
									lblDateFrom.setFont(FncGlobal.sizeLabel);
								}
								{
									gbc.weightx = 1;
									gbc.weighty = 1;

									gbc.gridx = 1;
									gbc.gridy = 3;

									dateFrom = new _JDateChooser("MM/dd/yyyy","##/##/##",'_');
									pnlCenter.add(dateFrom,gbc);
									dateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dateFrom.setFont(FncGlobal.sizeTextValue);
								}
								{
									gbc.weightx = 0;
									gbc.weighty = 1;

									gbc.gridx = 2;
									gbc.gridy = 3;

									lblDateTo = new JLabel("to:");
									pnlCenter.add(lblDateTo,gbc);
									lblDateTo.setFont(FncGlobal.sizeLabel);
								}
								{
									gbc.weightx = 1;
									gbc.weighty = 1;

									gbc.gridx = 3;
									gbc.gridy = 3;

									dateTo = new _JDateChooser("MM/dd/yyyy","##/##/##",'_');
									pnlCenter.add(dateTo,gbc);
									dateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dateTo.setFont(FncGlobal.sizeTextValue);
								}



							}

						}
					}
				}
				{
					JPanel pnlMainSouth = new JPanel(new GridLayout(0, 4, 3, 3));
					pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
					pnlMainSouth.setPreferredSize(new Dimension(0,30));
					{
						pnlMainSouth.add(Box.createHorizontalBox());
						pnlMainSouth.add(Box.createHorizontalBox());
						pnlMainSouth.add(Box.createHorizontalBox());
						
					}
					btnPrev = new JButton("Preview");
					pnlMainSouth.add(btnPrev);
					btnPrev.setFont(FncGlobal.sizeControls);
					btnPrev.addActionListener(new ActionListener() {


						public void actionPerformed(ActionEvent arg0) {
							if(checkRequestType()){
								JOptionPane.showMessageDialog(getContentPane(), "Please Indicate a Request Type", "Error", JOptionPane.ERROR_MESSAGE);
							}

							else if(lookupRequestType.getText().equals("07")){
								System.out.println("date to1 %s:"+dateTo.getDate());
								System.out.println("date from1 %s:"+dateFrom.getDate());
								prevCa();
							}
							else {
								prevReplenish();
							}


						}
					});
				}

			}
		}



	}
	private String lookCompany(){
		return "SELECT TRIM(co_id) as \"ID\", \n" + 
				"TRIM(company_name) as \"Company\", \n" + 
				"TRIM(company_alias) as \"Alias\"\n" + 
				"FROM mf_company ORDER BY co_id ASC;";
	}
	private String lookProject(){
		return "SELECT TRIM(proj_id) as \"ID\",\n" + 
				"TRIM(proj_name) as \"Project\", \n" + 
				"TRIM(proj_alias) as \"Alias\"\n" + 
				"FROM mf_project \n";
	}

	private String lookRequestType(){
		return "SELECT rplf_type_id as TypeID, trim(rplf_type_desc) as Description \n"+
				"FROM mf_rplf_type where rplf_type_id in ('06','07')";
	}
	private void prevCa(){
		Map<String, Object> mapparameter = new HashMap<String,Object>();
		mapparameter.put("datefrom", dateFrom.getDate());
		mapparameter.put("dateto", dateTo.getDate());


		FncReport.generateReport("/Reports/rptRevolvingFundsCa.jasper", "Revolving Funds",mapparameter);


	}
	private void prevReplenish(){
		Map<String, Object> mapparameter = new HashMap<String,Object>();
		mapparameter.put("datefrom", dateFrom.getDate());
		mapparameter.put("dateto", dateTo.getDate());



		FncReport.generateReport("/Reports/rptRevolvingFundsReplenish.jasper", "Revolving Funds",mapparameter);


	}

	private Boolean checkRequestType(){
		Boolean x = false;
		String RequestType = lookupRequestType.getText();

		if((RequestType==null) || (RequestType.equals("")) || RequestType.equals("null")) {
			return x = true;

		}
		else 
			return x = false;
	}


}
