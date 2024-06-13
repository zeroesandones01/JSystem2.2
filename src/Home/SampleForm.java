package Home;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import tablemodel.modelSample1;
import Functions.FncFocusTraversalPolicy;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class SampleForm extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1498467846637975919L;
	static String title = "Documents Monitoring";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	
	private JPanel pnlMain;
	
	private JPanel pnlNorth;
	private JLabel lblEmployee;
	private _JLookup lookupEmployee;
	private _JTagLabel tagEmployeeName;
	
	private _JTabbedPane tabCenter;

	private JScrollPane scrollSample1;
	private _JTableMain tblSample1;
	private modelSample1 modelSample1;
	private JList rowHeaderSample1;

	private JPanel pnlSample2;
	private _JScrollPaneMain scrollSample2Main;
	private _JTableMain tblSample2Main;
	private modelSample1 modelSample2Main;
	private JList rowHeaderSample2Main;

	private _JScrollPaneTotal scrollSample2Total;
	private _JTableTotal tblSample2Total;
	private modelSample1 modelSample2Total;

	private JPanel pnlSouth;

	private JPanel pnlSouthWest;
	private JButton btnPreview;

	private JPanel pnlSouthEast;
	private JButton btnCancel;
	private JButton btnSave;

	public SampleForm() {
		super(title, true, true, true, true);
		initGUI();
		displaySamples();
	}

	public SampleForm(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public SampleForm(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		
		pnlMain = new JPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(EMPTY_BORDER);
		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(null);
			pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Details"));
			pnlNorth.setPreferredSize(new Dimension(798, 121));
			{
				lblEmployee = new JLabel("Employee");
				pnlNorth.add(lblEmployee);
				lblEmployee.setBounds(12, 27, 91, 22);
			}
			{
				lookupEmployee = new _JLookup(null, "Employee", 0);
				pnlNorth.add(lookupEmployee);
				lookupEmployee.setLookupSQL(sqlEmployee());
				lookupEmployee.setBounds(103, 27, 100, 25);
				lookupEmployee.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){
							String fname = (String) data[1];
							String mname = (String) data[2];
							String lname = (String) data[3];
							
							tagEmployeeName.setTag(lname  + ", " + fname + " " + mname);
						}
					}
				});
			}
			{
				tagEmployeeName = new _JTagLabel("[ ]");
				pnlNorth.add(tagEmployeeName);
				tagEmployeeName.setBounds(209, 27, 567, 22);
			}
		}
		{
			//Place any containers or components that you want to be resizable. e.g.(_JTabbedPane, JScrollPane, JSplitPane)
			tabCenter = new _JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);
			{//XXX Table without Total
				scrollSample1 = new JScrollPane();
				tabCenter.addTab("Table w/o Total", null, scrollSample1, null);
				scrollSample1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollSample1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						tblSample1.clearSelection();
					}
				});
				{
					modelSample1 = new modelSample1();
					tblSample1 = new _JTableMain(modelSample1);
					scrollSample1.setViewportView(tblSample1);
					tblSample1.setFillsViewportHeight(false);
					tblSample1.hideColumns("Remarks");
				}
				{
					rowHeaderSample1 = tblSample1.getRowHeader();
					scrollSample1.setRowHeaderView(rowHeaderSample1);
					scrollSample1.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{//XXX Table with Total
				pnlSample2 = new JPanel(new BorderLayout());
				tabCenter.addTab("Table w/ Total", null, pnlSample2, null);
				{
					scrollSample2Main = new _JScrollPaneMain();
					pnlSample2.add(scrollSample2Main, BorderLayout.CENTER);
					{
						modelSample2Main = new modelSample1();

						tblSample2Main = new _JTableMain(modelSample2Main);
						scrollSample2Main.setViewportView(tblSample2Main);
						tblSample2Main.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblSample2Main.rowAtPoint(e.getPoint()) == -1){
									tblSample2Total.clearSelection();
								}else{
									tblSample2Main.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblSample2Main.rowAtPoint(e.getPoint()) == -1){
									tblSample2Total.clearSelection();
								}else{
									tblSample2Main.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeaderSample2Main = tblSample2Main.getRowHeader();
						scrollSample2Main.setRowHeaderView(rowHeaderSample2Main);
						scrollSample2Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollSample2Total = new _JScrollPaneTotal(scrollSample2Main);
					pnlSample2.add(scrollSample2Total, BorderLayout.SOUTH);
					{
						modelSample2Total = new modelSample1();
						modelSample2Total.addRow(new Object[] { null, "Total", 0.00, 0.00, null, null });

						tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
						scrollSample2Total.setViewportView(tblSample2Total);
						
						tblSample2Total.setTotalLabel(1);
					}
				}
			}
		}
		{
			pnlSouth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			//pnlSouth.setBorder(LINE_BORDER);
			pnlSouth.setPreferredSize(new Dimension(788, 30));
			{
				pnlSouthWest = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlSouth.add(pnlSouthWest, BorderLayout.WEST);
				pnlSouthWest.setPreferredSize(new Dimension(100, 28));
				{
					btnPreview = new JButton("Preview");
					pnlSouthWest.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
				}
			}
			{
				pnlSouthEast = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlSouthEast, BorderLayout.EAST);
				pnlSouthEast.setPreferredSize(new Dimension(205, 28));
				{
					btnSave = new JButton("Save");
					pnlSouthEast.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthEast.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}
		
		//This code handles the 2 table on hiding/showing columns between Main table and Total table e.g. (tblSample2Main, tblSample2Total) Note: this code must declare on last part of initGUI()
		FncTables.bindColumnTables(tblSample2Main, tblSample2Total);
		tblSample2Main.hideColumns("Remarks");

		//This code handles the Tab function of keyboard based on the arrangement of components you give as parameter e.g. (btnPreview, btnSave, btnCancel) Note: this code must declare on last part of initGUI()
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupEmployee, btnPreview, btnSave, btnCancel));
	}
	
	private void displaySamples() {
		
		//Display Sample #1 Query without Total
		_SampleForm.displaySampleQuery1(modelSample1, rowHeaderSample1);
		scrollSample1.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSample1.getRowCount())));
		tblSample1.packAll();
		
		//Display Sample #2 Query with Total
		_SampleForm.displaySampleQuery2(modelSample2Main, rowHeaderSample2Main, modelSample2Total);
		scrollSample2Total.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblSample2Main.getRowCount())));
		tblSample2Main.packAll();
	}
	
	private String sqlEmployee() {
		String sql = "SELECT\n" +
				"emp.emp_code as \"Code\",\n" +
				"emp.first_name as \"First Name\",\n" +
				"emp.mid_name as \"Middle Name\",\n" +
				"emp.last_name as \"Last Name\",\n" +
				"emp.emp_status as \"Status ID\",\n" +
				"stat.empstatus_desc as \"Status\",\n" +
				"emp.emp_rank as \"Rank ID\",\n" +
				"rank.rankdesc as \"Rank\",\n" +
				"emp.emp_pos as \"Position\",\n" +
				"emp.div_code as \"Division ID\",\n" +
				"div.division_alias as \"Division\",\n" +
				"emp.dept_code as \"Department ID\",\n" +
				"dept.dept_alias as \"Department\",\n" +
				"emp.entity_id as \"Entity ID\"\n" +
				"FROM public.employee emp\n" +
				"LEFT JOIN public.mf_system_user usr ON usr.user_id = emp.emp_code\n" +
				"LEFT JOIN public.employee_status stat ON stat.empstatus_code = emp.emp_status\n" +
				"LEFT JOIN public.employee_rank rank ON rank.rankcode = emp.emp_rank\n" +
				"LEFT JOIN public.rf_department dept ON dept.dept_code = emp.dept_code\n" +
				"LEFT JOIN public.rf_division div ON div.division_code = emp.div_code\n" +
				"WHERE emp.emp_status NOT IN ('E', 'I')\n" +
				"ORDER BY emp.last_name;";
		return sql;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("Preview")){
			System.out.printf("**********ActionCommand: %s%n", actionCommand);
		}
		if(actionCommand.equals("Save")){
			System.out.printf("**********ActionCommand: %s%n", actionCommand);
		}
		if(actionCommand.equals("Cancel")){
			System.out.printf("**********ActionCommand: %s%n", actionCommand);
		}
	}

}
