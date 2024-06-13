package Reports.CreditAndCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._FCancellationProcessing;
import Buyers.CreditandCollections._FPromissoryNoteCommintment;
import DateChooser._JDateChooser;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JButton;
import components._JInternalFrame;
@SuppressWarnings("rawtypes")
public class StatusCancellationAccnts extends _JInternalFrame implements _GUI ,LookupListener , ActionListener, ChangeListener,AncestorListener{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String title = "Canceled Accounts Status";
	public static Dimension frameSize = new Dimension(450, 250);
	private JXPanel pnlMain;
	private JXPanel pnlNorth;
	private JXPanel pnlSouth;
	private _JButton btnView;
	private Component lblCompany;
	private JLabel lblProcess;
	private JLabel lblProject;
	private _JLookup lookupCompany;
	private JTextField txtCompany;

	private _FPromissoryNoteCommintment methods = new _FPromissoryNoteCommintment();
	private _FCancellationProcessing functions = new _FCancellationProcessing();
	
	private DefaultComboBoxModel cmbProcessForModel;
	private JComboBox cmbProcessFor;
	private _JLookup lookupProjID;
	private JTextField txtProjectName;
	private JXPanel pnlCenter;
	private JXPanel pnlCenter_North;
	private JCheckBox chckPrint;
	private JXPanel pnlCenter_Center;
	private JLabel lblDateFrom;
	private _JDateChooser dteFrom;
	private JLabel lblDateTo;
	private _JDateChooser dteTo;
	private String co_id;
	private String company;
	private String company_logo;
	private String _proj_id;
	private String _proj_name;
	private boolean printAll;
	private Date dateFrom;
	private Date dateTo;

	public StatusCancellationAccnts() {
		super(title, false, true, false, true);
		initGUI();
		formLoad();
	}

	public StatusCancellationAccnts(String title) {
		super(title);
	}

	public StatusCancellationAccnts(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGUI() {

		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JXPanel(new BorderLayout(3, 3));
			getContentPane().add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{

				{
					pnlNorth = new JXPanel();
					pnlMain.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setLayout(new BorderLayout(3, 3));
					pnlNorth.setPreferredSize(new Dimension(0, 90));
					{
						JPanel pnlLabel = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorth.add(pnlLabel,BorderLayout.WEST);
						{
							lblCompany = new JLabel("Company");
							pnlLabel.add(lblCompany);
						}
						{

							lblProcess = new JLabel("Process for");
							pnlLabel.add(lblProcess);
						}
						{

							lblProject = new JLabel("Project");
							pnlLabel.add(lblProject);
						}
					}
				}
				{

					JPanel pnlAction = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlNorth.add(pnlAction,BorderLayout.CENTER);

					JPanel pnlCompany = new JPanel(new BorderLayout(3,3));
					pnlAction.add(pnlCompany,BorderLayout.CENTER);
					pnlCompany.setPreferredSize(new Dimension(74, 20));
					{

						lookupCompany = new _JLookup("Co. ID", "Company", 0) ; /// XXX lookupCompany 
						pnlCompany.add(lookupCompany,BorderLayout.WEST);
						lookupCompany.addLookupListener(this);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(methods.getCompany());
						lookupCompany.setPreferredSize(new Dimension(60, 20));
					}
					{
						txtCompany = new JTextField();
						pnlCompany.add(txtCompany,BorderLayout.CENTER);
						txtCompany.setEditable(false);
						txtCompany.setPreferredSize(new Dimension(100, 20));
					} 
					{
						cmbProcessForModel = new DefaultComboBoxModel(new String[] { "Cancelled Accounts Status", "CSV Status" });
						cmbProcessFor = new JComboBox();
						pnlAction.add(cmbProcessFor);
						cmbProcessFor.setModel(cmbProcessForModel);
						cmbProcessFor.addActionListener(this);
					}
					{

						JPanel pnlProject = new JPanel(new BorderLayout(3,3));
						pnlAction.add(pnlProject);
						{
							lookupProjID = new _JLookup("Proj ID", "Search Project", 0);
							pnlProject.add(lookupProjID,BorderLayout.WEST);
							lookupProjID.setPreferredSize(new Dimension(60, 0));
							lookupProjID.addLookupListener(this);

						}
						{
							txtProjectName = new JTextField();
							pnlProject.add(txtProjectName,BorderLayout.CENTER);
							txtProjectName.setEditable(false);
						}
					}
				}
			}
			{

				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter,BorderLayout.CENTER);
				pnlCenter.setLayout(new BorderLayout(3, 3));

				{

					pnlCenter_North = new JXPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlCenter_North,BorderLayout.NORTH);
					{

						chckPrint = new JCheckBox("Print All");
						pnlCenter_North.add(chckPrint,BorderLayout.CENTER );
						chckPrint.setHorizontalAlignment(SwingConstants.LEFT);
						chckPrint.addChangeListener(this);

					}
					{
						pnlCenter_Center = new JXPanel(new BorderLayout(3, 3));
						pnlCenter.add(pnlCenter_Center,BorderLayout.CENTER);


						{
							JPanel pnlLabel = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlCenter_Center.add(pnlLabel,BorderLayout.WEST);
							{
								lblDateFrom = new JLabel("Date From ");
								pnlLabel.add(lblDateFrom);
							}
							{

								lblDateTo = new JLabel("Date To ");
								pnlLabel.add(lblDateTo);
							}
						}
						{
							JPanel pnlDate = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlCenter_Center.add(pnlDate,BorderLayout.CENTER);
							{
								dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlDate.add(dteFrom, BorderLayout.CENTER);
							}
							{
								dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlDate.add(dteTo, BorderLayout.CENTER);
							}
						}
					}
				}
			}
			{

				pnlSouth = new JXPanel();
				pnlMain.add(pnlSouth,BorderLayout.SOUTH);
				pnlSouth.setLayout(new BorderLayout(3, 3));
				pnlSouth.setPreferredSize(new Dimension(0, 40));
				{
					btnView = new _JButton("View");
					pnlSouth.add(btnView,BorderLayout.CENTER);
					btnView.addActionListener(this);
				}

			}
		}

	}

	@Override
	public void lookupPerformed(LookupEvent e) {
		if (e.getSource().equals(lookupCompany)) {


			txtProjectName.setText("");
			lookupProjID.setValue("");
			cmbProcessFor.setSelectedIndex(0);
			dteFrom.setDate(null);
			dteTo.setDate(null);
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3].toString();
				txtCompany.setText(company);

				lookupProjID.setLookupSQL(functions.lookupProjects(co_id));
			}	
		}
		if (e.getSource().equals(lookupProjID)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				_proj_id = data[0].toString();
				_proj_name = data[1].toString();
				txtProjectName.setText(_proj_name);
				lookupProjID.setValue(_proj_id);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnView)) {
			
			if (txtProjectName.getText().isEmpty()){
				JOptionPane.showMessageDialog( getContentPane(), "Please choose a project name first  ", "Incomplete", JOptionPane.OK_OPTION );
				return;
			}
			
			if (txtCompany.getText().isEmpty()){
				JOptionPane.showMessageDialog( getContentPane(), "Please choose a company name first  ", "Incomplete", JOptionPane.OK_OPTION );
				return;
			}
			
			if (!chckPrint.isSelected()) {
				if (dteFrom.getDate() == null || dteTo.getDate() == null ) {
					JOptionPane.showMessageDialog( getContentPane(), "Please select a date first", "Incomplete", JOptionPane.OK_OPTION );
					return;
				}	
			}
			
			dateFrom = dteFrom.getDate();
			dateTo = dteTo.getDate();
			printAll = chckPrint.isSelected();
			new Thread(new ForPreview()).start();
		}
		if (e.getSource().equals(cmbProcessFor)) {
			lookupProjID.setValue("");
			txtProjectName.setText("");
			chckPrint.setSelected(true);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(chckPrint)) {
			disableDate();
		}

	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupCompany.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {

	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {

	}


	private void formLoad(){

		chckPrint.setSelected(true);
		disableDate();
	}

	private void disableDate(){

		if (chckPrint.isSelected()) {
			this.setComponentsEnabled(pnlCenter_Center, false);
			//dteFrom.setDate(null);
			//dteTo.setDate(null);
		}else{
			this.setComponentsEnabled(pnlCenter_Center, true);
		}
	}

	public class ForPreview implements Runnable{
		@Override
		public void run() {

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			System.out.println("Status" + printAll +    "  "+dateFrom + " " +dateTo  + "" + _proj_id);
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("dateFrom", dateFrom);
			mapParameters.put("dateTo", dateTo); 
			mapParameters.put("printall", printAll);
			mapParameters.put("proj_id", _proj_id);

			if (cmbProcessFor.getSelectedIndex() == 0) {

				FncReport.generateReport("/Reports/rptCancellationStatus.jasper", "Status of Canceled Accounts",  company, mapParameters);	
			}else{
				System.out.println("Status CSV");
				FncReport.generateReport("/Reports/rptCancellationStatus_CSV.jasper", "Status of Accounts With CSV",  company, mapParameters);
			}

		}
	}
}
