package Reports.LoansAndReceivable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DateChooser._JDateChooser;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import interfaces._GUI;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JXTextField;

public class SORReports extends _JInternalFrame implements _GUI, ActionListener{

	/**
	 * @author John Lester Fatallo
	 */
	private static final long serialVersionUID = -7142035172764696855L;

	static String title = "SOR Reports";
	Dimension frameSize = new Dimension(550, 200);// 510, 250

	private _JTabbedPane tabSORCenter;
	private JPanel pnlSORQualifiedAccounts;
	private JPanel pnlSORQALabel;
	private JLabel lblSORQAProject;
	private JLabel lblSORQAPhase;
	private JLabel lblSORQABank;
	private JLabel lblSORQAMADate;

	private JPanel pnlSORQAComponents;
	private JPanel pnlSORQAProject;
	private _JLookup lookupSORQAProject;
	private _JXTextField txtSORQAProject;

	private JPanel pnlSORQAPhase;
	private _JLookup lookupSORQAPhase;
	private _JXTextField txtSORQAPhase;

	private JPanel pnlSORQABank;
	private _JLookup lookupSORQABank;
	private _JXTextField txtSORQABank;

	private JPanel pnlSORQAMADate;
	private _JDateChooser dateSORQAMADate;
	private JLabel lblSORQACutOff;
	private _JDateChooser dateSORQACutOff;

	private JPanel pnlSORApprovedAccounts;
	private JPanel pnlSORAAWest;
	private JCheckBox chkSORAAProject;
	private JCheckBox chkSORAAPhase;

	private JPanel pnlSORAACenter;
	private JPanel pnlSORAAProject;
	private _JLookup lookupSORAAProject;
	private _JXTextField txtSORAAProject;

	private JPanel pnlSORAAPhase;
	private _JLookup lookupSORAAPhase;
	private _JXTextField txtSORAAPhase;

	private JPanel pnlSORReturnedAccounts;
	private JPanel pnlSORRAWest;
	private JCheckBox chkSORRAProject;
	private JCheckBox chkSORRAPhase;

	private JPanel pnlSORRACenter;
	private JPanel pnlSORRAProject;
	private _JLookup lookupSORRAProject;
	private _JXTextField txtSORRAProject;

	private JPanel pnlSORRAPhase;
	private _JLookup lookupSORRAPhase;
	private _JXTextField txtSORRAPhase;

	private JPanel pnlSouth;
	private JButton btnPreview;

	public SORReports() {
		super(title, false, true, false, true);
		initGUI();
	}

	public SORReports(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public SORReports(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, false, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			tabSORCenter = new _JTabbedPane();
			this.add(tabSORCenter, BorderLayout.CENTER);
			tabSORCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlSORQualifiedAccounts = new JPanel(new BorderLayout(3, 3));
				tabSORCenter.add("Qualified Accounts", pnlSORQualifiedAccounts);
				{
					pnlSORQALabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlSORQualifiedAccounts.add(pnlSORQALabel, BorderLayout.WEST);
					{
						lblSORQAProject = new JLabel("Project");
						pnlSORQALabel.add(lblSORQAProject);
					}
					{
						lblSORQAPhase = new JLabel("Phase");
						pnlSORQALabel.add(lblSORQAPhase);
					}
					{
						lblSORQABank = new JLabel("Bank");
						pnlSORQALabel.add(lblSORQABank);
					}
					{
						lblSORQAMADate = new JLabel("MA Date");
						pnlSORQALabel.add(lblSORQAMADate);
					}
				}
				{
					pnlSORQAComponents = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlSORQualifiedAccounts.add(pnlSORQAComponents, BorderLayout.CENTER);
					{
						pnlSORQAProject = new JPanel(new BorderLayout(3, 3));
						pnlSORQAComponents.add(pnlSORQAProject);
						{
							lookupSORQAProject = new _JLookup(null, "Select Project", 0);
							pnlSORQAProject.add(lookupSORQAProject, BorderLayout.WEST);
							lookupSORQAProject.setPreferredSize(new Dimension(50, 0));
							lookupSORQAProject.setLookupSQL(sqlProject());
							lookupSORQAProject.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										String proj_id = (String) data[0];
										String proj_name = (String) data[1];
										txtSORQAProject.setText(proj_name);
										lookupSORQAPhase.setLookupSQL(sqlPhase(proj_id));
									}
								}
							});
						}
						{
							txtSORQAProject = new _JXTextField();
							pnlSORQAProject.add(txtSORQAProject, BorderLayout.CENTER);
						}
					}
					{
						pnlSORQAPhase = new JPanel(new BorderLayout(3, 3));
						pnlSORQAComponents.add(pnlSORQAPhase);
						{
							lookupSORQAPhase = new _JLookup(null, "Select Phase", 0);
							pnlSORQAPhase.add(lookupSORQAPhase, BorderLayout.WEST);
							lookupSORQAPhase.setPreferredSize(new Dimension(50, 0));
							lookupSORQAPhase.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										String sub_proj_name = (String) data[1];
										txtSORQAPhase.setText(sub_proj_name);
									}
								}
							});
						}
						{
							txtSORQAPhase = new _JXTextField();
							pnlSORQAPhase.add(txtSORQAPhase, BorderLayout.CENTER);
						}
					}
					{
						pnlSORQABank = new JPanel(new BorderLayout(3, 3));
						pnlSORQAComponents.add(pnlSORQABank);
						{
							lookupSORQABank = new _JLookup(null, "Select bank", 0);
							pnlSORQABank.add(lookupSORQABank, BorderLayout.WEST);
							lookupSORQABank.setPreferredSize(new Dimension(50, 0));
							lookupSORQABank.setLookupSQL(sqlBank());
							lookupSORQABank.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										String bank_name = (String) data[1];
										txtSORQABank.setText(bank_name);
									}
								}
							});
						}
						{
							txtSORQABank = new _JXTextField();
							pnlSORQABank.add(txtSORQABank, BorderLayout.CENTER);
						}
					}
					{
						pnlSORQAMADate = new JPanel(new BorderLayout(3, 3));
						pnlSORQAComponents.add(pnlSORQAMADate);
						{
							dateSORQAMADate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlSORQAMADate.add(dateSORQAMADate, BorderLayout.WEST);
							dateSORQAMADate.setPreferredSize(new Dimension(150, 0));
						}
						{
							lblSORQACutOff = new JLabel("Cut Off", JLabel.TRAILING);
							pnlSORQAMADate.add(lblSORQACutOff);
						}
						{
							dateSORQACutOff = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlSORQAMADate.add(dateSORQACutOff, BorderLayout.EAST);
							dateSORQACutOff.setPreferredSize(new Dimension(150, 0));
						}
					}
				}
			}
			{
				pnlSORApprovedAccounts = new JPanel(new BorderLayout(3, 3));
				tabSORCenter.add("Sold to Bank", pnlSORApprovedAccounts);
				{
					pnlSORAAWest = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlSORApprovedAccounts.add(pnlSORAAWest, BorderLayout.WEST);
					{
						chkSORAAProject = new JCheckBox("Project");
						pnlSORAAWest.add(chkSORAAProject);
						chkSORAAProject.addItemListener(new ItemListener() {

							public void itemStateChanged(ItemEvent arg0) {
								int state = arg0.getStateChange();

								lookupSORAAProject.setValue(null);
								txtSORAAProject.setText("");
								if(state == ItemEvent.SELECTED){
									lookupSORAAProject.setEditable(true);
								}else{
									lookupSORAAProject.setEditable(false);
								}
							}
						});
					}
				}
				{
					pnlSORAACenter = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlSORApprovedAccounts.add(pnlSORAACenter);
					{
						pnlSORAAProject = new JPanel(new BorderLayout(3, 3));
						pnlSORAACenter.add(pnlSORAAProject);
						{
							lookupSORAAProject = new _JLookup(null, "Select Project", 0);
							pnlSORAAProject.add(lookupSORAAProject, BorderLayout.WEST);
							lookupSORAAProject.setPreferredSize(new Dimension(50, 0));
							lookupSORAAProject.setLookupSQL(sqlProject());
							lookupSORAAProject.setEditable(false);
							lookupSORAAProject.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										String proj_name = (String) data[1];
										txtSORAAProject.setText(proj_name);
									}
								}
							});
						}
						{
							txtSORAAProject = new _JXTextField();
							pnlSORAAProject.add(txtSORAAProject, BorderLayout.CENTER);
						}
					}
				}
			}
			{
				pnlSORReturnedAccounts = new JPanel(new BorderLayout(3, 3));
				tabSORCenter.add("Returned by Bank", pnlSORReturnedAccounts);
				{
					pnlSORRAWest = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlSORReturnedAccounts.add(pnlSORRAWest, BorderLayout.WEST);
					{
						chkSORRAProject = new JCheckBox("Project");
						pnlSORRAWest.add(chkSORRAProject);
						chkSORRAProject.addItemListener(new ItemListener() {

							public void itemStateChanged(ItemEvent e) {
								int state = e.getStateChange();

								lookupSORRAProject.setValue(null);
								txtSORRAProject.setText("");
								if(state == ItemEvent.SELECTED){
									lookupSORRAProject.setEditable(true);
								}else{
									lookupSORRAProject.setEditable(false);
								}
							}
						});
					}
				}
				{
					pnlSORRACenter = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlSORReturnedAccounts.add(pnlSORRACenter);
					{
						pnlSORRAProject = new JPanel(new BorderLayout(3, 3));
						pnlSORRACenter.add(pnlSORRAProject);
						{
							lookupSORRAProject = new _JLookup(null, "Select Project", 0);
							pnlSORRAProject.add(lookupSORRAProject, BorderLayout.WEST);
							lookupSORRAProject.setPreferredSize(new Dimension(50, 0));
							lookupSORRAProject.setLookupSQL(sqlProject());
							lookupSORRAProject.setEditable(false);
							lookupSORRAProject.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										String proj_id = (String) data[0];
										String proj_name = (String) data[1];
										txtSORRAProject.setText(proj_name);

										System.out.printf("Display proj_id: %s%n", proj_id);
									}
								}
							});
						}
						{
							txtSORRAProject = new _JXTextField();
							pnlSORRAProject.add(txtSORRAProject, BorderLayout.CENTER);
						}
					}
				}
			}
		}
		{
			pnlSouth = new JPanel(new BorderLayout(3, 3));
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				btnPreview = new JButton("Preview");
				pnlSouth.add(btnPreview, BorderLayout.CENTER);
				btnPreview.setActionCommand("Preview");
				btnPreview.addActionListener(this);
			}
		}
	}//XXX END OF INITGUI

	private String sqlProject(){
		return "SELECT TRIM(proj_id) as \"Proj. ID\", \n" + 
				"proj_name as \"Proj Name\", \n" + 
				"proj_alias as \"Proj Alias\"\n" + 
				"FROM mf_project;";
	}

	private String sqlPhase(String proj_id){
		return "SELECT sub_proj_id AS \"ID\", \n" + 
				"sub_proj_name AS \"Phase\", \n" + 
				"sub_proj_alias AS \"Alias\" \n" + 
				"FROM mf_sub_project\n" + 
				"WHERE proj_id = '"+proj_id+"'\n" + 
				"ORDER BY sub_proj_name";
	}

	private String sqlBank(){
		return "SELECT TRIM(bank_id) as \"Bank ID\", \n" + 
				"bank_name as \"Bank Name\"\n" + 
				"FROM mf_bank\n" + 
				"ORDER BY bank_name;";
	}

	private Boolean toSave(){
		if(tabSORCenter.getSelectedIndex() == 0){
			if(lookupSORQAProject.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project", "Preview", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(lookupSORQAPhase.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select phase", "Preview", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(lookupSORQABank.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select bank", "Preview", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(dateSORQACutOff.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input cut off date", "Preview", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if(tabSORCenter.getSelectedIndex() == 1){
			if(lookupSORAAProject.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select projecy", "Preview", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if(tabSORCenter.getSelectedIndex() == 2){
			if(lookupSORRAProject.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project", "Preview", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		return true;
	}

	public void actionPerformed(ActionEvent e) { 
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("Preview")){
			if(toSave()){

				if(tabSORCenter.getSelectedIndex() == 0){//PREVIEW OF REPORTS FOR QUALIFIED ACCOUNTS
					Map<String, Object> mapParameters = new HashMap<String, Object>();

					mapParameters.put("proj_id", lookupSORQAProject.getValue());
					mapParameters.put("sub_proj_id", lookupSORQAPhase.getValue());
					mapParameters.put("bank_id", lookupSORQABank.getValue());
					mapParameters.put("cut_off_date", dateSORQACutOff.getDate());

					FncReport.generateReport("/Reports/rptSORQualifiedAccounts.jasper", "SOR Approved Accounts", mapParameters);
					//System.out.println("Preview of SOR Qualified Accounts");
				}
				if(tabSORCenter.getSelectedIndex() == 1){//PREVIEW OF REPORTS FOR APPROVED ACCOUNTS
					Map<String, Object> mapParameters = new HashMap<String, Object>();

					mapParameters.put("proj_id", lookupSORAAProject.getValue());
					mapParameters.put("proj_name", txtSORAAProject.getText());

					FncReport.generateReport("/Reports/rptSORApprovedAccounts.jasper", "SOR Approved Accounts", mapParameters);
					//System.out.println("Preview of SOR Approved Accounts");
				}
				if(tabSORCenter.getSelectedIndex() == 2){//PREVIEW OF REPORTS FOR RETURNED ACCOUNTS
					Map<String, Object> mapParameters = new HashMap<String, Object>();

					mapParameters.put("proj_id", lookupSORRAProject.getValue());
					mapParameters.put("proj_name", txtSORRAProject.getText());

					FncReport.generateReport("/Reports/rptSORReturnedAccounts.jasper", "SOR Returned Accounts", mapParameters);
					//System.out.printf("Display returned accounts proj_id: %s%n", lookupSORRAProject.getValue());
				}
			}
		}
	}
}
