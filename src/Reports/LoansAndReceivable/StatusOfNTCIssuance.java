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
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class StatusOfNTCIssuance extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = 1162406788303915011L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;

	JLabel lblNorth;
	JLabel lblCenter;
	JLabel lblProject;
	JLabel lblBatch;
	JLabel lblPhase;
	JLabel lblCompany;
	JLabel lblQualificationBatch;
	JLabel lblListFormat;
	JLabel lblLotOnly;

	_JLookup lookupProject;
	_JLookup lookupPhase;
	_JLookup lookupCompany;

	String company_logo;

	static String title = "Status of NTC Issuance";
	Dimension frameSize = new Dimension(620, 180);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public StatusOfNTCIssuance() {
		super(title, false, true, true, true);
		initGUI();
	}

	public StatusOfNTCIssuance(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public StatusOfNTCIssuance(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JXPanel pnlCenter = new JXPanel(new GridBagLayout());
				pnlMain.add(pnlCenter,BorderLayout.CENTER);
				{
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.fill = GridBagConstraints.BOTH;
					gbc.insets = new Insets(5, 3, 5, 3);
					{
						gbc.weightx = 0;
						gbc.weighty = 1;

						gbc.gridx = 0;
						gbc.gridy = 0;

						JLabel lblCom = new JLabel("Company");
						pnlCenter.add(lblCom,gbc);
						lblCom.setHorizontalAlignment(JLabel.LEFT);
						lblCom.setFont(FncGlobal.sizeLabel);

					}
					{
						gbc.weightx = 0.5;
						gbc.weighty = 1;

						gbc.gridx = 1;
						gbc.gridy = 0;

						lookupCompany = new _JLookup(null, "Company");
						pnlCenter.add(lookupCompany,gbc);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setFont(FncGlobal.sizeTextValue);
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									lblCompany.setText(String.format("[ %s ]", (String) data[1]));
									lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));

									//lookupProject.setValue(null);
									//txtProject.setText("");
									//listPhase.setModel(new DefaultComboBoxModel(new ArrayList<_JCheckListItem>().toArray()));

									//generateContractNo();
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});

					}
					{
						gbc.weightx = 1;
						gbc.weighty = 1;

						gbc.gridx = 2;
						gbc.gridy = 0;

						lblCompany = new JLabel("[ ]");
						pnlCenter.add(lblCompany,gbc);
						lblCompany.setHorizontalAlignment(JLabel.LEFT);
						lblCompany.setFont(FncGlobal.sizeLabel);

					}
					{
						gbc.weightx = 0;
						gbc.weighty = 1;

						gbc.gridx = 0;
						gbc.gridy = 1;

						JLabel lblPro = new JLabel("Project");
						pnlCenter.add(lblPro,gbc);
						lblPro.setHorizontalAlignment(JLabel.LEFT);
						lblPro.setFont(FncGlobal.sizeLabel);

					}
					{
						gbc.weightx = 0.5;
						gbc.weighty = 1;

						gbc.gridx = 1;
						gbc.gridy = 1;


						lookupProject = new _JLookup(null, "Project", "Please select company.");
						pnlCenter.add(lookupProject,gbc);
						lookupProject.setReturnColumn(0);
						lookupProject.setFont(FncGlobal.sizeTextValue);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									lblProject.setText(String.format("[ %s ]", (String) data[1]));
									lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						gbc.weightx = 1;
						gbc.weighty = 1;

						gbc.gridx = 2;
						gbc.gridy = 1;

						lblProject = new JLabel("[ ]");
						pnlCenter.add(lblProject,gbc);
						lblProject.setHorizontalAlignment(JLabel.LEFT);
						lblProject.setFont(FncGlobal.sizeLabel);

					}
					{
						gbc.weightx = 0;
						gbc.weighty = 1;

						gbc.gridx = 0;
						gbc.gridy = 2;

						JLabel lblPh = new JLabel("Phase");
						pnlCenter.add(lblPh,gbc);
						lblPh.setHorizontalAlignment(JLabel.LEFT);
						lblPh.setFont(FncGlobal.sizeLabel);

					}
					{
						gbc.weightx = 0.5;
						gbc.weighty = 1;

						gbc.gridx = 1;
						gbc.gridy = 2;

						lookupPhase = new _JLookup(null, "Phase", "Please select project.");
						pnlCenter.add(lookupPhase,gbc);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setFont(FncGlobal.sizeTextValue);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									lblPhase.setText(String.format("[ %s ]", (String) data[1]));

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						gbc.weightx = 1;
						gbc.weighty = 1;

						gbc.gridx = 2;
						gbc.gridy = 2;

						lblPhase = new JLabel("[ ]");
						pnlCenter.add(lblPhase,gbc);
						lblPhase.setHorizontalAlignment(JLabel.LEFT);
						lblPhase.setBounds(215, 60, 250, 22);
						lblPhase.setFont(FncGlobal.sizeLabel);

					}
				}




			
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				//pnlSouth.setBorder(lineBorder);
				pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX	
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					JButton btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
				}
				{
					JButton btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.setFont(FncGlobal.sizeControls);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int response = JOptionPane.showConfirmDialog(StatusOfNTCIssuance.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
									"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.YES_OPTION) {
								lookupProject.setText("...");
								refreshTO();
							}
						}
					});
				}
			}
		}
	}
	}
	private void refreshTO() {

		if (lookupProject.getText().equals("...")) {
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupPhase.setValue("");
			lblCompany.setText("[ ]");
			lblProject.setText("[ ]");
			lblPhase.setText("[ ]");

		}
	} // refreshTO()
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		//company_logo = RequestForPayment.sql_getCompanyLogo();
		if(actionCommand.equals("Preview")){

			previewNTCIssuance();
			previewNTCIssuanceListing();

			/*Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters.put("phase", lookupPhase.getValue());
			mapParameters.put("proj_name", lblProject.getText().replace("[ ", "").replace(" ]",""));


			FncReport.generateReport("/Reports/rptStatusofNTCIssuanceListing.jasper", "Status of NTC Issuance Listing", mapParameters);
			FncReport.generateReport("/Reports/rptStatusofNTCIssuance.jasper", "Status of NTC Issuance", mapParameters);*/
		}
	}
	private void previewNTCIssuanceListing(){
		String criteria = "Status of NTC Issuance Listing";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		company_logo = RequestForPayment.sql_getCompanyLogo();

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("phase", lookupPhase.getValue());
		mapParameters.put("proj_name", lblProject.getText().replace("[ ", "").replace(" ]",""));

		FncReport.generateReport("/Reports/rptStatusofNTCIssuanceListing.jasper", reportTitle, mapParameters);
	}
	private void previewNTCIssuance(){
		String criteria = "Status of NTC Issuance";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		company_logo = RequestForPayment.sql_getCompanyLogo();

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("phase", lookupPhase.getValue());
		mapParameters.put("proj_name", lblProject.getText().replace("[ ", "").replace(" ]",""));

		FncReport.generateReport("/Reports/rptStatusofNTCIssuance.jasper", reportTitle, mapParameters);
	}

}