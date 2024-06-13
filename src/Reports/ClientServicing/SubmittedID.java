package Reports.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class SubmittedID extends _JInternalFrame implements _GUI, ActionListener, AncestorListener{

	private static final long serialVersionUID = -3896252484512614826L;

	static String title = "List of Client Submitted ID";
	Dimension frameSize = new Dimension(750, 175);// 510, 250

	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlWest;
	private JLabel lblCompany;
	private JCheckBox chkCompany;
	//private JCheckBox chkProject;
	private JLabel lblExpiration;
	private JPanel pnlCompany;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;

	/*private JPanel pnlProject;
	private _JLookup lookupProject;
	private _JXTextField txtProject;*/
	private JPanel pnlExpiration;
	private _JDateChooser dteExpiration;

	private JPanel pnlSouth;
	private JButton btnPreview;

	public SubmittedID() {
		super(title, true, true, true, true);
		initGUI();
	}

	public SubmittedID(String title) {
		super(title);
		initGUI();
	}

	public SubmittedID(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter,BorderLayout.CENTER);
				GridBagConstraints bagMain = new GridBagConstraints();
				bagMain.fill = GridBagConstraints.BOTH;
				bagMain.ipady = 40;
				{

					bagMain.weightx = 1;
					bagMain.weighty = 0.5;
					bagMain.gridx = 0;
					bagMain.gridy = 0;

					JPanel pnlPrimo = new JPanel(new GridBagLayout());
					pnlCenter.add(pnlPrimo,bagMain);
					{
						GridBagConstraints bagPrimo = new GridBagConstraints();
						bagPrimo.fill = GridBagConstraints.BOTH;
//						bagPrimo.ipady = 5;
						bagPrimo.insets = new Insets(10, 5, 10, 5);
						{
							bagPrimo.weightx = 0;
							bagPrimo.weighty = 1;

							bagPrimo.gridx = 0;
							bagPrimo.gridy = 0;

							{
								chkCompany = new JCheckBox("Company");
								pnlPrimo.add(chkCompany, bagPrimo);
								chkCompany.setFont(FncGlobal.sizeLabel);
								chkCompany.addItemListener(new ItemListener() {

									public void itemStateChanged(ItemEvent e) {
										int state = e.getStateChange();
										if(state == ItemEvent.SELECTED){
											lookupCompany.setEditable(true);
										}else{
											/*lookupCompany.setEditable(false);
											lookupCompany.setValue(null);
											txtCompany.setText("");
											lookupProject.setValue(null);
											txtProject.setText("");*/
										}
									}
								});
							}

						}
						{
							bagPrimo.weightx = 0;
							bagPrimo.weighty = 1;

							bagPrimo.gridx = 1;
							bagPrimo.gridy = 0;

							{
								lookupCompany = new _JLookup(null, "Select company", 0);
								pnlPrimo.add(lookupCompany, bagPrimo);
								lookupCompany.setLookupSQL(sqlCompany());
								lookupCompany.setEditable(false);
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								lookupCompany.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String co_id = (String) data[0];
											String co_name = (String) data[1];

											txtCompany.setText(co_name);

											/*lookupProject.setLookupSQL(sqlProject(co_id));
											lookupProject.setValue(null);
											txtProject.setText("");*/
										}
									}
								});
							}
						}

						{
							bagPrimo.weightx = 1.5;
							bagPrimo.weighty = 1;

							bagPrimo.gridx = 2;
							bagPrimo.gridy = 0;
							bagPrimo.gridwidth = 2;

							txtCompany = new _JXTextField("Name");
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
							pnlPrimo.add(txtCompany, bagPrimo);
							txtCompany.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagPrimo.weightx = 0;
							bagPrimo.weighty = 1;

							bagPrimo.gridx = 0;
							bagPrimo.gridy = 1;
							bagPrimo.gridwidth = 1;
							
							lblExpiration = new JLabel("Expiration:", JLabel.LEADING);
							pnlPrimo.add(lblExpiration,bagPrimo);
							lblExpiration.setFont(FncGlobal.sizeLabel);
						}
						{
							bagPrimo.weightx = 0.25;
							bagPrimo.weighty = 2;

							bagPrimo.gridx = 1;
							bagPrimo.gridy = 1;
							
							dteExpiration = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlPrimo.add(dteExpiration,bagPrimo);
							dteExpiration.setFont(FncGlobal.sizeTextValue);
							//dteExpiration.getCalendarButton().setVisible(false);
						}

					}




				}
			

			}


			{
				pnlSouth = new JPanel(new GridLayout(0,4,3,3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
//				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(388, 30));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setActionCommand(btnPreview.getText());
				}
			}
		}
		//this.setComponentsEditable(pnlMain, false);
	}

	private String sqlCompany(){
		return "select trim(co_id) as \"ID\", trim(company_name) as \"Name\", trim(company_alias) as \"Alias\" from mf_company";
	}

	private String sqlProject(String co_id){
		return "select trim(proj_id) as \"ID\" , trim(proj_name) as \"Project Name\", trim(proj_alias) as \"Alias\" from mf_project where co_id = '"+co_id+"';\n";
	}



	@Override
	public void ancestorAdded(AncestorEvent event) {

	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("Preview")){//PREVIEW OF THE LIST OF THE ACTIVE COMPLAIN
			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("company", txtCompany.getText());
			//mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("prepared_by", UserInfo.FullName);
			mapParameters.put("expiration", dteExpiration.getDate());
			//mapParameters.put("filterproject", chkProject.isSelected()); //filters the complaints based on the project no
			mapParameters.put("filtercompany", chkCompany.isSelected()); 

			System.out.printf("Display Company %s%n", lookupCompany.getValue());
			//System.out.printf("Display Projec %s%n", lookupProject.getValue());
			System.out.printf("Display filter proejct %s%n", chkCompany.isSelected());
			System.out.printf("Display filter company %s%n", chkCompany.isSelected());
			System.out.printf("Display expiration %s%n", dteExpiration.getDate());

			FncReport.generateReport("/Reports/rptClientSubmittedID.jasper", "List of Client Id", mapParameters);
		}
	}
}
