package Reports.BiddingandAwarding;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;

public class WeeklyConstructionAccomplishment extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6857984331331553016L;

	static String title = "Weekly Construction Accomplishment";
	static Dimension frameSize = new Dimension(500, 285);//XXX 510, 250
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthWest1;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblPhase;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupPhase;

	
	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtPhase;

	

	private JPanel pnlCenter;

	private JPanel pnlMonth;
	private JLabel lblMonth;
	private JComboBox cmbMonth;

	private JPanel pnlYear;
	private JLabel lblYear;
	private JComboBox cmbYear;

	private JPanel pnlWeek;
	private JLabel lblWeek;
	private JComboBox cmbWeek;

	private JPanel pnlSorting;
	private JLabel lblSorting;
	private JComboBox cmbSorting;

	private JPanel pnlSouth;
	private JButton btnPreview;
	
	public JRadioButton rbtnCompleted;

	String company;
	SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
	
	String server = null;
	String dbase = null;

	public WeeklyConstructionAccomplishment() {
		super(title, false, true, false, true);
		initGUI();
	}

	public WeeklyConstructionAccomplishment(String title) {
		super(title);
		initGUI();
	}

	public WeeklyConstructionAccomplishment(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				pnlNorth.setPreferredSize(new Dimension(400, 100));
					{
						lblCompany = new JLabel("Company");
						pnlNorth.add(lblCompany);
						lblCompany.setHorizontalAlignment(JLabel.LEFT);
						lblCompany.setBounds(0, 10, 120, 25);
	
						lblProject = new JLabel("Project");
						pnlNorth.add(lblProject);
						lblProject.setHorizontalAlignment(JLabel.LEFT);
						lblProject.setBounds(0, 40, 120, 25);
			
						lblPhase = new JLabel("Phase");
						pnlNorth.add(lblPhase);
						lblPhase.setHorizontalAlignment(JLabel.LEFT);
						lblPhase.setBounds(0, 70, 120, 25);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlNorth.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setBounds(60, 10, 80, 25);
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									txtCompany.setText(String.format("%s", (String) data[1]));
									lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lookupProject = new _JLookup(null, "Project", "Please select company.");
						pnlNorth.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setBounds(60, 40, 80, 25);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								final Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									txtProject.setText(String.format("%s", (String) data[1]));
									lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lookupPhase = new _JLookup(null, "Project", "Please select Project.");
						pnlNorth.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setBounds(60, 70, 80, 25);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								final Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									txtPhase.setText(String.format("%s", (String) data[1]));

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						txtCompany = new JXTextField(" ");
						pnlNorth.add(txtCompany);
						txtCompany.setHorizontalAlignment(JLabel.LEFT);
						txtCompany.setBounds(145, 10, 339, 25);
						txtCompany.setEditable(false);

						txtProject = new JXTextField(" ");
						pnlNorth.add(txtProject);
						txtProject.setHorizontalAlignment(JLabel.LEFT);
						txtProject.setBounds(145, 40, 339, 25);
						txtProject.setEditable(false);

						txtPhase = new JXTextField(" ");
						pnlNorth.add(txtPhase);
						txtPhase.setHorizontalAlignment(JLabel.LEFT);
						txtPhase.setBounds(145, 70, 339, 25);
						txtPhase.setEditable(false);
					}
					
			{
				pnlCenter = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlYear = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlYear);
					{
						lblYear = new JLabel("Year");
						pnlYear.add(lblYear, BorderLayout.WEST);
						lblYear.setPreferredSize(new Dimension(55, 0));
					}
					{
						cmbYear = new JComboBox(new DefaultComboBoxModel(getYears()));
						pnlYear.add(cmbYear, BorderLayout.CENTER);
						cmbYear.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								JComboBox combo = (JComboBox) arg0.getSource();
								int year = (Integer) combo.getSelectedItem();
								int month = cmbMonth.getSelectedIndex();
								Date current_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());
								
								if(java.time.LocalDate.now() != null ){
									cmbMonth.setEnabled(true);
								}else{
									cmbMonth.setEnabled(false);
								}
							    System.out.println(java.time.LocalTime.now());  
							    

								cmbWeek.setModel(new DefaultComboBoxModel(getWeeks2(year, month)));
								
							}
						});
					}
				}
				{
					pnlMonth = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlMonth);
					{
						lblMonth = new JLabel("Month");
						pnlMonth.add(lblMonth, BorderLayout.WEST);
						lblMonth.setPreferredSize(new Dimension(55, 0));
					}
					{
						cmbMonth = new JComboBox(new DefaultComboBoxModel(getMonths()));
						pnlMonth.add(cmbMonth, BorderLayout.CENTER);
						cmbMonth.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
						cmbMonth.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								JComboBox combo = (JComboBox) arg0.getSource();
								int year = (Integer) cmbYear.getSelectedItem();
								
								cmbWeek.setModel(new DefaultComboBoxModel(getWeeks2(year, combo.getSelectedIndex())));
							
							}
						});
					}
				}
				{
					pnlWeek = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlWeek);
					{
						lblWeek = new JLabel("Week");
						pnlWeek.add(lblWeek, BorderLayout.WEST);
						lblWeek.setPreferredSize(new Dimension(55, 0));
					}
					{
						cmbWeek = new JComboBox(new DefaultComboBoxModel(getWeeks2((Integer) cmbYear.getSelectedItem(), Calendar.getInstance().get(Calendar.MONTH))));
						pnlWeek.add(cmbWeek, BorderLayout.CENTER);
					}
				}
				{
					pnlSorting = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlSorting);
					//pnlSorting.setBorder(components.JTBorderFactory.createTitleBorder("Sorting"));
					{
						lblSorting = new JLabel("Sort By");
						pnlSorting.add(lblSorting, BorderLayout.WEST);
						lblSorting.setPreferredSize(new Dimension(55, 0));
					}
					{
						cmbSorting = new JComboBox(new DefaultComboBoxModel(
								new String[] { "Unit", "Client", "Contractor", "Contract No.", "Work Detail" }));
						pnlSorting.add(cmbSorting, BorderLayout.CENTER);
						cmbSorting.setSelectedIndex(3);
						cmbSorting.setEnabled(true);
					}
				}
				/*{
					JPanel pnlCompleted = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlCompleted);
					{
						rbtnCompleted = new JRadioButton();
						pnlCompleted.add(rbtnCompleted);
						rbtnCompleted.setText("Include 100% completed units");
						rbtnCompleted.setBounds(277, 12, 77, 18);
						rbtnCompleted.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						rbtnCompleted.setPreferredSize(new java.awt.Dimension(164, 23));
						rbtnCompleted.setSelected(true);
						//rbtnCompleted.addActionListener(this);
						rbtnCompleted.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae){									
								if (rbtnCompleted.isSelected()==false)
								{
									
								} 
								else 
								{
									
								}				
							}});
					}
				}*/
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(400, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(1f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					//btnPreview.setEnabled(false);
				}
			}
		}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupPhase, cmbYear, cmbMonth, cmbWeek, btnPreview));
	}
	
	//private String getProject() {
		//return "SELECT * FROM lookup_project;";
	//}

	private List<JRSortField> sortBy(String sort_by) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("Unit", new String[]{"c_phase_no", "c_block_no", "c_lot_no"});
		map.put("Client", new String[]{"c_client"});
		map.put("Contractor", new String[]{"c_contractor"});
		map.put("Contract No.", new String[]{"c_contract_no"});
		map.put("Work Detail", new String[]{"c_work_detail"});
		
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

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupCompany.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		pgSelect db = new pgSelect();
		db.select("select company_logo from mf_company where co_id = '"+lookupCompany.getValue()+"'");
		
		String company_logo = null;
		if(db.isNotNull()){
			company_logo = (String) db.getResult()[0][0];
		}
		
		String sort_by = (String) cmbSorting.getSelectedItem();
		
		Object[] date = ((String) cmbWeek.getSelectedItem()).split("  -  ");
		Date date_from = null;
		try {
			date_from = df.parse((String) date[0]);
		} catch (ParseException e) { }

		Date date_to = null;
		try {
			date_to = df.parse((String) date[1]);
		} catch (ParseException e) { }
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("proj_name", txtProject.getText());
		mapParameters.put("phase", lookupPhase.getText());
		mapParameters.put("date_from", date_from);
		mapParameters.put("date_to", date_to);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));

		mapParameters.put("subrptSummaryOfOnGoingConstructionPerContract", this.getClass().getResourceAsStream("/Reports/subrptSummaryOfOnGoingConstructionPerContract.jasper"));
		//mapParameters.put("subrptSummaryOfDelayedUnitsPerContract", this.getClass().getResourceAsStream("/Reports/subrptSummaryOfDelayedUnitsPerContract.jasper"));
		/*if (rbtnCompleted.isSelected()==true) {
			FncReport.generateReport("/Reports/rptWeeklyConstructionAccomplishmentReport.jasper", title, txtProject.getText(), mapParameters);
		} else {
			FncReport.generateReport("/Reports/rptWeeklyConstructionAccomplishmentReport_withoutCompleted.jasper", title, txtProject.getText(), mapParameters);
		}*/
		FncReport.generateReport("/Reports/rptWeeklyConstructionAccomplishmentReport_withoutCompleted.jasper", title, txtProject.getText(), mapParameters);
	}
	/**
	 * Added 2018-05-08
	 * for construction monitoring weekly report
	 * @return 
	 */
	private Object[] getWeeks2(int year, int month) {
		ArrayList<String> listWeeks = new ArrayList<String>();

		//Date current = FncGlobal.dateFormat(FncGlobal.getDateSQL());

		Calendar c = Calendar.getInstance();
		int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		ArrayList<Date> listDates = new ArrayList<Date>();
		for(int x=1; x <= monthMaxDays; x++){
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, x);

			int no_of_week = cal.get(Calendar.DAY_OF_WEEK);
			if(no_of_week >= 2 && no_of_week <= 6){
				listDates.add(cal.getTime());
			}
		}

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(Collections.min(listDates));
		int startDay = startCal.get(Calendar.DAY_OF_WEEK);
		for(int x = startDay; x > 2; x--){
			startCal.add(Calendar.DATE, -1);
			listDates.add(startCal.getTime());
		}


		Collections.sort(listDates);
		for(Date d : listDates){
			SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");

			Calendar cal = Calendar.getInstance();
			cal.setTime(d);

			if(cal.get(Calendar.DAY_OF_WEEK) == 3){
				cal.add(Calendar.DATE, 3);
				listWeeks.add(String.format("%s  -  %s%n", df.format(d), df.format(cal.getTime())));
				//System.out.printf("Date: %s - %s%n", df.format(d), df.format(cal.getTime()));
			}
		}
		return listWeeks.toArray();

	}
	

}
