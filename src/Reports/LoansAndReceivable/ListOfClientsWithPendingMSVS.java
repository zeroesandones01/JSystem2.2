package Reports.LoansAndReceivable;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class ListOfClientsWithPendingMSVS extends _JInternalFrame implements
ActionListener, _GUI {

	private static final long serialVersionUID = -6628229145821011471L;
	static String title = "List Of Clients With Pending MSVS Application";
	Dimension frameSize = new Dimension(650, 350);

	static Border lineRed = BorderFactory.createLineBorder(Color.RED);

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;

	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;

	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;

	private _JDateChooser dteDateFrom;

	private JButton btnPreview;
	private JButton btnExport;

	private JComboBox cboStatus;

	private JCheckBox chkFindings;
	private JCheckBox chkForVerRevHL;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public ListOfClientsWithPendingMSVS() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ListOfClientsWithPendingMSVS(String title) {
		super(title);
		initGUI();		
	}

	public ListOfClientsWithPendingMSVS(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel pnlCenter = new JXPanel(new GridBagLayout());
			panMain.add(pnlCenter,BorderLayout.CENTER);
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				gbc.ipady = 40;
				{
					gbc.weightx = 1;
					gbc.weighty = 2;

					gbc.gridx = 0;
					gbc.gridy = 0;
					

					JXPanel panNorth1 = new JXPanel(new GridBagLayout());
					pnlCenter.add(panNorth1, gbc);
					panNorth1.setBorder(JTBorderFactory.createTitleBorder("Project and Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagOne = new GridBagConstraints();
						bagOne.fill = GridBagConstraints.BOTH;
						bagOne.insets = new Insets(5,3,5,3);
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 0;

							lblCompany = new JLabel("Company:");
							panNorth1.add(lblCompany,bagOne);
							lblCompany.setHorizontalAlignment(JTextField.LEFT);
							lblCompany.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 0;

							txtCoID = new _JLookup("");
							panNorth1.add(txtCoID,bagOne);
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.setLookupSQL(CompanySQL());
							txtCoID.setReturnColumn(0);
							txtCoID.setFont(FncGlobal.sizeTextValue);
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText(data[1].toString());
										txtProID.setLookupSQL(ProjectSQL(txtCoID.getValue()));

										btnPreview.setEnabled(true);
										btnExport.setEnabled(true);
									} else {
										btnPreview.setEnabled(false);
										btnExport.setEnabled(false);									
									}
								}
							});
							txtCoID.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtCoID.setValue("");
										txtCompany.setText("All Companies");
									}
								}

								public void keyReleased(KeyEvent e) {

								}

								public void keyPressed(KeyEvent e) {

								}
							});
						}
						{
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 0;

							txtCompany = new JTextField("");
							panNorth1.add(txtCompany,bagOne);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
							txtCompany.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 1;

							lblProject = new JLabel("Project:");
							panNorth1.add(lblProject,bagOne);
							lblProject.setHorizontalAlignment(JTextField.LEFT);
							lblProject.setFont(FncGlobal.sizeLabel);
							
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 1;

							txtProID = new _JLookup("");
							panNorth1.add(txtProID,bagOne);
							txtProID.setHorizontalAlignment(JTextField.CENTER);
							txtProID.setLookupSQL(ProjectSQL(""));
							txtProID.setReturnColumn(0);
							txtProID.setFont(FncGlobal.sizeTextValue);
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtProject.setText(data[2].toString());
										txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
									}
								}
							});
							txtProID.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtProID.setValue("");
										txtProject.setText("All Projects");
									}
								}

								public void keyReleased(KeyEvent e) {

								}

								public void keyPressed(KeyEvent e) {

								}
							});
						}
						{
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 1;

							txtProject = new JTextField("");
							panNorth1.add(txtProject, bagOne);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
							txtProject.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 2;

							lblPhase = new JLabel("Phase:");
							panNorth1.add(lblPhase,bagOne);
							lblPhase.setHorizontalAlignment(JTextField.LEFT);
							lblPhase.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 2;

							txtPhaseID = new _JLookup("");
							panNorth1.add(txtPhaseID,bagOne);
							txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
							txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
							txtPhaseID.setReturnColumn(0);
							txtPhaseID.setFont(FncGlobal.sizeTextValue);
							txtPhaseID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtPhase.setText(data[1].toString());
									}
								}
							});
							txtPhaseID.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {
									if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtPhaseID.setValue("");
										txtPhase.setText("All Phase");
									}
								}

								public void keyReleased(KeyEvent e) {

								}

								public void keyPressed(KeyEvent e) {

								}
							});
						}
						{
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 2;

							txtPhase = new JTextField("");
							panNorth1.add(txtPhase, bagOne);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
							txtPhase.setFont(FncGlobal.sizeTextValue);
						}
					}
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 0;

					gbc.gridx = 0;
					gbc.gridy = 1;

					JXPanel pnlCon = new JXPanel(new GridBagLayout());
					pnlCenter.add(pnlCon,gbc);
					{
						GridBagConstraints bagCon = new GridBagConstraints();
						bagCon.fill = GridBagConstraints.BOTH;
						bagCon.ipady = 20;
						{
							bagCon.weightx = 1;
							bagCon.weighty = 0;

							bagCon.gridx = 0;
							bagCon.gridy = 0;

							JXPanel panDate = new JXPanel(new GridBagLayout());
							pnlCon.add(panDate, bagCon);
							panDate.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								GridBagConstraints conDate = new GridBagConstraints();
								conDate.fill = GridBagConstraints.BOTH;
								conDate.insets = new Insets(5,3,5,3);
								{
									conDate.weightx = 1;
									conDate.weighty = 1;

									conDate.gridx= 0;
									conDate.gridy = 0; 

									dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panDate.add(dteDateFrom, conDate);
									dteDateFrom.setDate(null);
									dteDateFrom.setEnabled(true);
									dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dteDateFrom.setFont(FncGlobal.sizeTextValue);
								}
							}
						}

						{
							bagCon.weightx = 1;
							bagCon.weighty = 0;

							bagCon.gridx = 1;
							bagCon.gridy = 0;

							JXPanel panStatus = new JXPanel(new GridBagLayout());
							pnlCon.add(panStatus, bagCon);
							panStatus.setBorder(JTBorderFactory.createTitleBorder("MSVS Options", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								GridBagConstraints conSatus = new GridBagConstraints();
								conSatus.fill = GridBagConstraints.BOTH;
								conSatus.insets = new Insets(5,3,5,3);
								{
									conSatus.weightx = 1;
									conSatus.weighty = 1;

									conSatus.gridx= 0;
									conSatus.gridy = 0; 

									chkFindings = new JCheckBox("with Findings");
									panStatus.add(chkFindings,conSatus);
									chkFindings.setHorizontalAlignment(JTextField.CENTER);
									chkFindings.setSelected(true);
									chkFindings.setFont(FncGlobal.sizeLabel);
									chkFindings.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent e) {
											if (chkFindings.isSelected()) {
												chkForVerRevHL.setSelected(false);
											} else {
												chkForVerRevHL.setSelected(true);
											}
										}
									});
								}
								{
									conSatus.weightx = 1;
									conSatus.weighty = 1;

									conSatus.gridx= 1;
									conSatus.gridy = 0; 

									chkForVerRevHL = new JCheckBox("for Ver/Rev/HL");
									panStatus.add(chkForVerRevHL, conSatus);
									chkForVerRevHL.setHorizontalAlignment(JTextField.CENTER);
									chkForVerRevHL.setSelected(false);
									chkForVerRevHL.setFont(FncGlobal.sizeLabel);
									chkForVerRevHL.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent e) {
											if (chkForVerRevHL.isSelected()) {
												chkFindings.setSelected(false);
											} else {
												chkFindings.setSelected(true);
											}
										}
									});
								}
							}
						}

					}

				}
			}
		}
		{
		
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.SOUTH);
			{
				JXPanel panButton = new JXPanel(new GridLayout(1, 4, 5, 5));
				panCenter.add(panButton, BorderLayout.CENTER);
				{
					panButton.add(Box.createHorizontalBox());
					panButton.add(Box.createHorizontalBox());
					panButton.add(Box.createHorizontalBox());
				}
				{
					btnPreview = new JButton("Preview");
					panButton.add(btnPreview);
					btnPreview.setActionCommand("Preview");;
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(true);
					btnPreview.setFont(FncGlobal.sizeControls);
				}
				{
					btnExport = new JButton("Export");
					//panButton.add(btnExport);
					btnExport.setActionCommand("Export");;
					btnExport.addActionListener(this);
					btnExport.setEnabled(true);
				}
			}
		}

		Setdefaults();
	}

	private static String CompanySQL() {
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}

	public static String ProjectSQL(String strCo) {
		return "SELECT proj_id, proj_alias, proj_name\n" +
				"FROM mf_project\n" +
				"WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
				"ORDER BY proj_name";
	}

	private String sqlPhase(String strProject) {
		return "select distinct x.phase, 'PHASE ' || phase \n" +
				"from mf_unit_info x \n" +
				"inner join mf_project y on x.proj_id = y.proj_id \n" +
				"where (x.proj_id = '"+strProject+"' or '"+strProject+"' = '') \n" +
				"order by x.phase";
	}

	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;

		/*	Company Default	*/
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			txtCoID.setText("");
			txtCompany.setText("All Companies");
		} else {
			txtCoID.setValue(_RealTimeDebit.GetValue("SELECT co_id FROM mf_company LIMIT 1"));
			txtCompany.setText(_RealTimeDebit.GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		}

		/*	Project Default	*/
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			txtProID.setText("");
			txtProject.setText("All Projects");
		} else {
			txtProID.setValue(_RealTimeDebit.GetValue("SELECT proj_id FROM mf_project LIMIT 1"));
			txtProject.setText(_RealTimeDebit.GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProID.getText()+"'"));
		}

		txtPhaseID.setValue("");
		txtPhase.setText("All Phase");
	}

	public void actionPerformed(final ActionEvent e) {
		FncGlobal.startProgress("Generating report...");

		if (e.getActionCommand().equals("Preview")) {
			GenerateReport();
		} else if (e.getActionCommand().equals("Export")) {

		}

		btnPreview.setEnabled(true);
		btnExport.setEnabled(true);
		FncGlobal.stopProgress();
	}

	private void GenerateReport() {
		new Thread(new Runnable() {
			public void run() {
				String company_logo = RequestForPayment.sql_getCompanyLogo();
				String strDate = "";
				strDate = df.format(dteDateFrom.getDate());
				//strDate = "As Of: " + strDate;

				String strOption = "";
				if (chkFindings.isSelected()) {
					strOption = "MSVS with Findings";
				} else {
					strOption = "MSVS for Verification/Reverification/HL Verification";
				}

				System.out.println("");
				System.out.println("SELECT * \n" +
						"FROM view_hdmf_listofclients_with_msvs_application('"+txtCoID.getText()+"', '"+txtProID.getText()+"', '"+txtPhaseID.getText()+"', '"+dteDateFrom.getText()+"', '"+strOption+"') A\n" +
						"ORDER BY project, a.sales_div_grp, a.client_name");

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", txtCompany.getText());
				mapParameters.put("02_AsOfDate", strDate);
				mapParameters.put("03_CoID", txtCoID.getText());
				mapParameters.put("04_ProID", txtProID.getText());
				mapParameters.put("06_Project", txtProject.getText());
				mapParameters.put("07_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
				mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("09_Phase", txtPhaseID.getValue());
				mapParameters.put("10_option", strOption);
				FncReport.generateReport("/Reports/rpt_hdmf_ListOfClientswithPendingMSVS.jasper", "List of Clients with Pending MSVS Application", "", mapParameters);

			}
		}).start();
	}
}
