package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXPanel;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class PDCduefortheday extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "PDC Due for the Day";
	static Dimension frameSize = new Dimension(525, 320);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlCenter;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	
	private JTextField txtProject;
	private JTextField txtCompany;
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnCancel;
	
	String company;
	String company_logo;		

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;	

	private JComboBox cmbSortby;
	private JLabel lblFilterby;
	private JComboBox cmbFilterby;
	
	String co_id = "02";
	String branch_id = "";

	private JLabel lblBranch;
	private _JLookup lookupBranch;
	private JTextField txtBranch;

	public PDCduefortheday() {
		super(title, false, true, false, true);
		initGUI();
	}

	public PDCduefortheday(String title) {
		super(title);
		initGUI();
	}

	public PDCduefortheday(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		
		this.setTitle(title);
		this.setSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				{
					JXPanel panPage = new JXPanel(new GridLayout(3, 1, 5, 5)); 
					pnlMain.add(panPage, BorderLayout.PAGE_START);
					panPage.setPreferredSize(new Dimension(0, 115));
					panPage.setBorder(JTBorderFactory.createTitleBorder("Check Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							JXPanel panCom = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panCom);
							{
								{
									JXPanel panComLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panCom.add(panComLabel, BorderLayout.LINE_START); 
									panComLabel.setPreferredSize(new Dimension(200, 0));
									{
										{
											lblCompany = new JLabel("Company");
											panComLabel.add(lblCompany);
											lblCompany.setHorizontalAlignment(JLabel.LEADING);
										}
										{
											lookupCompany = new _JLookup(null, "Company");
											panComLabel.add(lookupCompany);
											lookupCompany.setReturnColumn(0);
											lookupCompany.setLookupSQL(SQL_COMPANY());
											lookupCompany.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){
														
														company		= (String) data[1];										
														company_logo = (String) data[3];
														
														String name = (String) data[1];						
														txtCompany.setText(name);
														lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
														lblProject.setEnabled(true);	
														lookupProject.setText("");
														lookupProject.setEnabled(true);
														txtProject.setEnabled(true);
														txtProject.setText("");
														
														KEYBOARD_MANAGER.focusNextComponent();
														btnCancel.setEnabled(true);
													}
												}
											});
										}
									}
								}
								{
									txtCompany = new JTextField();
									panCom.add(txtCompany, BorderLayout.CENTER);
									txtCompany.setHorizontalAlignment(JTextField.CENTER);
									txtCompany.setEditable(false);
								}
							}
						}
						{
							JXPanel panProject = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panProject);
							{
								{
									JXPanel panProLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panProject.add(panProLabel, BorderLayout.LINE_START); 
									panProLabel.setPreferredSize(new Dimension(200, 0));
									{
										{
											lblProject = new JLabel("Project", JLabel.LEADING);
											panProLabel.add(lblProject);
											lblProject.setHorizontalAlignment(JLabel.LEADING);
										}
										{
											lookupProject = new _JLookup(null, "Project");
											panProLabel.add(lookupProject);
											lookupProject.setReturnColumn(0);
											lookupProject.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){
														String name = (String) data[1];						
														txtProject.setText(name);
																	
														KEYBOARD_MANAGER.focusNextComponent();
														btnCancel.setEnabled(true);
													}
												}
											});
										}
									}
								}
								{
									txtProject = new JTextField();
									panProject.add(txtProject, BorderLayout.CENTER);
									txtProject.setHorizontalAlignment(JLabel.CENTER);
									txtProject.setEditable(false);
								}
							}
						}
						{
							JXPanel panBranch = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panBranch);
							{
								{
									JXPanel panBranchLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panBranch.add(panBranchLabel, BorderLayout.LINE_START); 
									panBranchLabel.setPreferredSize(new Dimension(200, 0));
									{
										{
											lblBranch = new JLabel("Branch", JLabel.TRAILING);
											panBranchLabel.add(lblBranch);
											lblBranch.setHorizontalAlignment(JLabel.LEADING);
										}
										{
											lookupBranch = new _JLookup(null, "Branch");
											panBranchLabel.add(lookupBranch);
											lookupBranch.setReturnColumn(0);
											lookupBranch.setEnabled(true);
											lookupBranch.setLookupSQL("select branch_id, branch_name, branch_alias\n" + 
													"from mf_office_branch\n" + 
													"order by branch_id::int");
											lookupBranch.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet();
													if (data != null) {

														String name = (String) data[1];
														txtBranch.setText(name);

														KEYBOARD_MANAGER.focusNextComponent();
														btnCancel.setEnabled(true);
													}
												}
											});
										}
									}
									{
										txtBranch = new JTextField();
										panBranch.add(txtBranch, BorderLayout.CENTER);
										txtBranch.setHorizontalAlignment(JLabel.CENTER);
										txtBranch.setEditable(false);
									}
								}
							}
						}
					}
				}
				{
					pnlCenter = new JPanel(new GridLayout(3, 1, 5, 5)); 
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Option"));
					{
						{
							JXPanel panDate = new JXPanel(new BorderLayout(5, 5)); 
							pnlCenter.add(panDate);
							{
								{
									JLabel lblDate = new JLabel("Due Date: ");
									panDate.add(lblDate, BorderLayout.LINE_START);
									lblDate.setHorizontalAlignment(JLabel.LEADING);
									lblDate.setPreferredSize(new Dimension(200, 0));
								}
								{
									dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									panDate.add(dateDateTo, BorderLayout.CENTER);
									dateDateTo.getCalendarButton().setVisible(true);
									dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
						}
						{
							JXPanel panSort = new JXPanel(new BorderLayout(5, 5)); 
							pnlCenter.add(panSort);
							{
								{
									JLabel lblSort = new JLabel("Sort by: ");
									panSort.add(lblSort, BorderLayout.LINE_START);
									lblSort.setHorizontalAlignment(JLabel.LEADING);
									lblSort.setPreferredSize(new Dimension(200, 0));
								}
								{
									String status[] = {"Client's Name","Check Date","Amount"};
									
									cmbSortby = new JComboBox(status);
									panSort.add(cmbSortby, BorderLayout.CENTER);
									cmbSortby.setSelectedIndex(0);
									cmbSortby.setEnabled(true);
								}
							}
						}
						{
							JXPanel panFilter = new JXPanel(new BorderLayout(5, 5)); 
							pnlCenter.add(panFilter);
							{
								{
									lblFilterby = new JLabel("Filter by");
									panFilter.add(lblFilterby, BorderLayout.LINE_START);
									lblFilterby.setHorizontalAlignment(JLabel.LEADING);
									lblFilterby.setPreferredSize(new Dimension(200, 0));
								}
								{
									String status2[] = {"Not Yet Sold to Bank","Sold to Bank"};
									
									cmbFilterby = new JComboBox(status2);
									panFilter.add(cmbFilterby, BorderLayout.CENTER);
									cmbFilterby.setSelectedIndex(0);
									cmbFilterby.setEnabled(true);
								}
							}
						}
					}
				}
			}
			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.PAGE_END);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setAlignmentX(0.5f);
						btnPreview.setAlignmentY(0.5f);
						btnPreview.setMaximumSize(new Dimension(100, 30));
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlSouth.add(btnCancel);
						btnCancel.setAlignmentX(0.5f);
						btnCancel.setAlignmentY(0.5f);
						btnCancel.setMaximumSize(new Dimension(100, 30));
						btnCancel.setMnemonic(KeyEvent.VK_P);
						btnCancel.setEnabled(false);
						btnCancel.addActionListener(this);
					}	
				}
			}
		}
		/*
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				 dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 560, 352);  //174
		*/
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		//initialize_comp();
	}
	
	private List<JRSortField> sortBy(String sort_by) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("Client's Name", new String[]{"entity_name"});
		map.put("Check Date", new String[]{"check_date"});
		map.put("Amount", new String[]{"amount"});
		
		List<JRSortField> sortList = new ArrayList<JRSortField>();
		for(String fields : map.get(sort_by)){
			JRDesignSortField sortField = new JRDesignSortField();
			sortField.setName(fields);
			sortField.setOrder(SortOrderEnum.ASCENDING);
			sortField.setType(SortFieldTypeEnum.FIELD);
			
			sortList.add(sortField);
		}
		
		return sortList;
	}

	public void ancestorAdded(AncestorEvent event) {
		lookupProject.requestFocus();
	}

	public void ancestorMoved(AncestorEvent event) { }

	public void ancestorRemoved(AncestorEvent event) { }

	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview")){		
			String criteria = "PDC Due for the Day";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			String sort_by = (String) cmbSortby.getSelectedItem();
			
			String strProj = "";
			if (lookupProject.getValue()==null) {
				strProj = ""; 
			}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("proj_id", strProj);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("date_to", dateDateTo.getDate());		
			mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
			mapParameters.put("filter_by", (String) cmbFilterby.getSelectedItem());	
			mapParameters.put("branch_id", lookupBranch.getText()); 
			FncReport.generateReport("/Reports/rptPDCdueFortheDay_v2.jasper", reportTitle, txtProject.getText(), mapParameters);
		}
		
		if(e.getActionCommand().equals("Cancel")){	
			//lookupCompany.setText("");
			//txtCompany.setText("");
			co_id = "02";
			lblProject.setEnabled(false);	
			lookupProject.setEnabled(false);	lookupProject.setText("");
			txtProject.setEnabled(false);		txtProject.setText("");
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			cmbSortby.setSelectedIndex(0);	
			lookupBranch.setText("");
			txtBranch.setText("");
			branch_id = "";
		}
	}
	
	public void initialize_comp(){		
		co_id = "02";	
		company = "CENQHOMES DEVELOPMENT CORPORATION";		
		company_logo = RequestForPayment.sql_getCompanyLogo();
		
		txtCompany.setText(company);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupBranch.setLookupSQL(getBranch());
		lblProject.setEnabled(true);	
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);
		txtProject.setText("");
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		lookupCompany.setValue(co_id);
	}

	static String getBranch() {
		return "SELECT branch_id, branch_name, branch_alias FROM mf_office_branch;";
	}

	
}
