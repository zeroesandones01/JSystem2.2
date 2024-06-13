package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import org.jdesktop.swingx.JXTable;

import Database.pgSelect;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.UserInfo;
import Home.Console;
import Lookup._JLookup;
import search.SearchDispatcher;
import search.SearchEvent;
import search.SearchListener;

public class _JInternalFrame extends JInternalFrame implements ComponentListener, InternalFrameListener, SearchDispatcher, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2498146657462217237L;

	private Dimension SIZE_OLD;

	private SearchListener searchListener;

	public Object[] RETURN_DATA;

	protected static KeyboardFocusManager KEYBOARD_MANAGER = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	protected static Dimension SIZE = new Dimension(800, 550);

	protected static BorderLayout BORDER_LAYOUT = new BorderLayout();

	protected static Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);

	private String TITLE_MENU = null;

	private String PRIMARY_TITLE = null;
	private String SECONDARY_TITLE = null;
	boolean blinkState = false;
	private Timer timerTitle = null;

	public _JInternalFrame() {
		initThis();
	}

	public _JInternalFrame(String title) {
		super(title);
		this.setName(title);
		initThis();
	}

	public _JInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		this.setName(title);
		initThis();
	}

	private void initThis() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setLayout(BORDER_LAYOUT);
		//this.setSize(new Dimension(800, 600));
		//this.setPreferredSize(new Dimension(800, 600));
		this.addComponentListener(this);
		this.addInternalFrameListener(this);
		//setNorthPane(((BasicInternalFrameUI) this.getUI()).getNorthPane());
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String action = ae.getActionCommand();

				if(action.equals("Close")){
					dispose();
				}
				if(action.equals("Console")){
					if(FncGlobal.lpsOut != null){
						Console console  = new Console(FncGlobal.homeMDI, "Console");
						console.setLocationRelativeTo(null);
						console.setVisible(true);
					}
				}
				/*if(action.equals("Search")){
					FncGlobal.search = new _JSearch(FncGlobal.homeMDI, "Search");
					FncGlobal.search.setVisible(true);
					RETURN_DATA = FncGlobal.search.getRETURN_DATA();

					if(RETURN_DATA != null){
						for(Object obj : RETURN_DATA){
							System.out.println(obj);
						}
						System.out.println();System.out.println();
						doSearch();
					}
				}*/

				/**
				 * added by Alvin Gonzales (2015-11-09)
				 */
				if(action.equals("Bookmark")){
					JRootPane root = (JRootPane) ae.getSource();
					root.getParent().getName();

					String className = root.getParent().getClass().getCanonicalName();
					String titleName = getTitleMenu();
					System.out.printf("Display getTitleMenu:%s%n", getTitleMenu());
					
					if(getTitleMenu() == null){
						JOptionPane.showMessageDialog(_JInternalFrame.this.getTopLevelAncestor(), String.format("%s cannot be bookmarked. \n\n %s", root.getParent().getName(), "This module might be opened from another module."), "Bookmark", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					pgSelect db = new pgSelect();
					db.select("SELECT sp_bookmark('"+ UserInfo.EmployeeCode +"', '"+ className +"', '"+ titleName +"');");

					if(db.isNotNull() && (Boolean) db.getResult()[0][0]){
						JOptionPane.showMessageDialog(_JInternalFrame.this.getTopLevelAncestor(), String.format("%s has been bookmarked.", titleName), "Bookmark", JOptionPane.INFORMATION_MESSAGE);
						FncGlobal.homeMDI.reloadBookmark();
					}else{
						JOptionPane.showMessageDialog(_JInternalFrame.this.getTopLevelAncestor(), String.format("%s has already bookmarked.", titleName), "Bookmark", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
				if(action.equals("Manual")) {
					if(JOptionPane.showConfirmDialog(_JInternalFrame.this.getTopLevelAncestor(), "View module user manual?", "View", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						String titleName = getTitleMenu();
						preview(titleName);
					}
				}
			}
		};

		/*JMenuBar bar = new JMenuBar();

		JMenu menu = new JMenu("");
		bar.add(menu);

		JMenuItem item = new JMenuItem("Bookmark");
		menu.add(item);

		this.setJMenuBar(bar);*/

		this.getRootPane().registerKeyboardAction(action, "Close", KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		this.getRootPane().registerKeyboardAction(action, "Console", KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		this.getRootPane().registerKeyboardAction(action, "Bookmark", KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		this.getRootPane().registerKeyboardAction(action, "Manual", KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void preview(String module_name) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM mf_jsystem_modules where module_name = '"+module_name+"' and user_manual is not null";
		db.select(SQL);
		
		if(db.isNotNull()) {
			Connection connection = null;

			if (Desktop.isDesktopSupported()) {
				try {
					connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery("select user_manual from mf_jsystem_modules where module_name = '"+module_name+"' and status_id = 'A'");

					File pdfTemp = File.createTempFile(module_name.replace("'", "").toString(), ".pdf");
					pdfTemp.deleteOnExit();

					FileOutputStream fos = new FileOutputStream(pdfTemp);
					rs.next();
					byte[] fileBytes = rs.getBytes(1);
					fos.write(fileBytes);
					rs.close();
					fos.close();

					Desktop.getDesktop().open(pdfTemp);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (IOException e) {

				}
			}
		}else {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "This module has no uploaded manual yet", "Preview", JOptionPane.WARNING_MESSAGE);
		}
		
	}

	/**
	 * XXX MAXIMIZE
	 */
	private void maximize() {
		InternalFrameUI ui = getUI();
		if (ui instanceof BasicInternalFrameUI){
			((BasicInternalFrameUI) ui).setNorthPane(null);
			setBorder(BorderFactory.createEmptyBorder());
		}
		FncGlobal.homeMDI.setTitle(FncGlobal.ORIGINAL_TITLE + " - " + getTitle());
		FncGlobal.homeMDI.maximizeAllFrames(this);

		//System.out.printf("Primary: %s%n", getPrimaryTitle());
		if(getSecondaryTitle() != null){
			System.out.printf("Secondary: %s%n", getSecondaryTitle());

			ActionListener action = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					blinkState = !blinkState;
					if(blinkState){
						FncGlobal.homeMDI.setTitle(String.format("%s - %s *** %s ***", FncGlobal.ORIGINAL_TITLE, getPrimaryTitle(), getSecondaryTitle()));
					}else{
						FncGlobal.homeMDI.setTitle(String.format("%s - %s", FncGlobal.ORIGINAL_TITLE, getPrimaryTitle()));
					}
				}
			};

			if(getTimerTitle() == null){
				setTimerTitle(new Timer(1000, action));
				getTimerTitle().start();
			}else{
				getTimerTitle().stop();
				setTimerTitle(new Timer(1000, action));
				getTimerTitle().start();
			}
		}
	}

	private void close() {
		InternalFrameUI ui = this.getUI();
		if (ui instanceof BasicInternalFrameUI){
			FncGlobal.homeMDI.setTitle(FncGlobal.ORIGINAL_TITLE);
			FncGlobal.homeMDI.setMenubarButtonVisible(false);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		try {
			if(FncGlobal.homeMDI.getDesktopPane().getSelectedFrame() == this){
				if(((JInternalFrame)e.getComponent()).isMaximum()){
					maximize();
				}else{
					setSIZE_OLD(getSize());
				}
			}
		} catch (NullPointerException e1) { }
	}

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) { }

	/**
	 * @return the sIZE_OLD
	 */
	public Dimension getSIZE_OLD() {
		return SIZE_OLD;
	}

	/**
	 * @param sIZE_OLD the sIZE_OLD to set
	 */
	public void setSIZE_OLD(Dimension sIZE_OLD) {
		SIZE_OLD = sIZE_OLD;
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) { }

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		//System.out.printf("**********Closing: %s%n", getTitle());
		close();
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) { }

	@Override
	public void internalFrameIconified(InternalFrameEvent e) { }

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		//System.out.println("*****Deiconified!");
		if(((JInternalFrame)e.getSource()).isMaximum()){
			maximize();
		}else{
			setSIZE_OLD(getSize());
		}
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) { }

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) { }



	@Override
	public void setRETURN_DATA(Object[] RETURN_DATA) {
		this.RETURN_DATA = RETURN_DATA;
	}

	@Override
	public Object[] getRETURN_DATA() {
		return RETURN_DATA;
	}

	@Override
	public void addSearchListener(SearchListener listener) {
		this.searchListener = listener;
	}

	@Override
	public void doSearch() {
		dispatchEvent();
	}

	@Override
	public void dispatchEvent() {
		dispatchRunnableOnEventQueue(searchListener, new SearchEvent(this));
	}

	@Override
	public void dispatchRunnableOnEventQueue(SearchListener listener, final SearchEvent event) {
		if(searchListener != null){
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					searchListener.searchPerformed(event);
				}
			});
		}
	}

	public Object[] getMonths() {
		ArrayList<String> listMonths = new ArrayList<String>();
		String[] months = new DateFormatSymbols().getMonths();
		for (int i = 0; i < months.length; i++) {
			String month = months[i];
			if(!month.equals("")){
				listMonths.add(month);
			}
			//System.out.println("month = " + month);
		}
		return listMonths.toArray();
	}

	public Object[] getYears() {
		ArrayList<Integer> listYears = new ArrayList<Integer>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//listYears.add(year);
		for(int x=0; x < 5; x++){
			listYears.add(year -x);
		}
		return listYears.toArray();
	}

	public Object[] getWeeks(int year, int month) {
		ArrayList<String> listWeeks = new ArrayList<String>();

		Calendar c = Calendar.getInstance();
		int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		ArrayList<Date> listDates = new ArrayList<Date>();
		for(int x=1; x <= monthMaxDays; x++){
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, x);

			int no_of_week = cal.get(Calendar.DAY_OF_WEEK);
			if(no_of_week >= 2 && no_of_week <= 5){
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

		/*Calendar endCal = Calendar.getInstance();
		endCal.setTime(Collections.max(listDates));
		int endDay = endCal.get(Calendar.DAY_OF_WEEK);
		for(int x = endDay; x < 5; x++){
			endCal.add(Calendar.DATE, 1);
			listDates.add(endCal.getTime());
		}*/

		Collections.sort(listDates);
		for(Date d : listDates){
			SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");

			Calendar cal = Calendar.getInstance();
			cal.setTime(d);

			if(cal.get(Calendar.DAY_OF_WEEK) == 2){
				cal.add(Calendar.DATE, 3);
				listWeeks.add(String.format("%s  -  %s%n", df.format(d), df.format(cal.getTime())));
				//System.out.printf("Date: %s - %s%n", df.format(d), df.format(cal.getTime()));
			}
		}
		return listWeeks.toArray();
	}

	public static String SQL_BATCHID() {//XXX Batch ID
		String SQL = "SELECT a.batch_no as \"Batch No.\", get_employee_name(a.preparedby) as \"Prepared By\", a.dateprep::DATE::TIMESTAMP as \"Date Prepared\", TRIM(c.notice_desc) as \"Notice\"\n" + 
				"FROM rf_client_notices a\n" + 
				"LEFT JOIN mf_notice_type c ON c.notice_id = a.notice_id\n" + 
				"GROUP BY a.batch_no, get_employee_name(a.preparedby), a.dateprep::DATE::TIMESTAMP, TRIM(c.notice_desc)\n" + 
				"ORDER BY a.batch_no::INT DESC;";
		return SQL;
	}

	public static String SQL_COMPANY() {//XXX Company
		/* edited on No. 26, 2014 by Del Gonzales as needed in PV preview */
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		return SQL;
	}

	public static String SQL_PROJECT() {//XXX Project
		String SQL = "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\" FROM mf_project where status_id = 'A' ORDER BY proj_id;";
		return SQL;
	}

	public static String SQL_PROJECT(String co_id) {//XXX Project(Company ID)
		String SQL = "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\"\n" +
				"FROM mf_project\n" +
				"WHERE co_id = '"+ co_id +"'\n" +
				"AND proj_id NOT IN ('008', '021') \n"+
				"AND status_id = 'A'" +
				"order by proj_id;";
		return SQL;
	}

	public static String SQL_PROJECT_ALL(String co_id) {//XXX Project(Company ID)
		String SQL = "SELECT * FROM view_lookup_project('"+ co_id +"');";
		return SQL;
	}

	public static String SQL_PHASE(String proj_id) {//XXX Phase
	
		String SQL = "select\n" +
				"TRIM(a.phase) as \"Phase\",\n" +
				"'Phase ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" +
				"b.proj_alias || a.phase as \"Alias\"\n" +
				"from mf_sub_project a\n" +
				"left join mf_project b on a.proj_id = b.proj_id and coalesce(a.server_id, '') = coalesce(b.server_id, '') \n" +
				"where a.proj_id = '"+ proj_id +"'\n" +
				"and a.status_id = 'A'\n" +
				"group by a.phase, b.proj_alias\n" +
				"order by a.phase;";
		
		return SQL;
	

		
	}
	public static String SQL_PHASE_NTC(String proj_id) {//XXX Phase
		if(proj_id.equals("018")) {
			
			String SQL = "select\n" +
					"a.phase as \"Phase\",\n" +
					"'Phase ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" +
					"b.proj_alias || a.phase as \"Alias\"\n" +
					"from mf_sub_project a\n" +
					"left join mf_project b on a.proj_id = b.proj_id\n" +
					"where a.proj_id = '"+ proj_id +"'\n" +
					"and a.status_id = 'A' and phase = ''\n" +
					"group by a.phase, b.proj_alias\n" +
					"order by a.phase;";
			return SQL;
		}else {
		String SQL = "select\n" +
				"a.phase as \"Phase\",\n" +
				"'Phase ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" +
				"b.proj_alias || a.phase as \"Alias\"\n" +
				"from mf_sub_project a\n" +
				"left join mf_project b on a.proj_id = b.proj_id\n" +
				"where a.proj_id = '"+ proj_id +"'\n" +
				"and a.status_id = 'A'\n" +
				"group by a.phase, b.proj_alias\n" +
				"order by a.phase;";
		
		return SQL;
		}
		
	}
	public static String SQL_PHASE_ALL(String proj_id) {
		String phase = "SELECT * FROM view_lookup_phase('"+ proj_id +"');";
		return phase;
	}


	public static String SQL_BLOCK(String phase) {//XXX Block
		String SQL = "select block as \"Block\" from mf_unit_info where phase = '"+ phase +"' and status_id = 'A' and server_id is null group by block order by block;";
		return SQL;
	}

	public static String SQL_CLIENT() {
		String SQL = "SELECT * FROM jsearch;" ;	
		return SQL;
	}

	public static String SQL_CHECKNO() {
		String SQL = "select\n" +
				"a.checkno, a.acctno ,b.entity_name, a.pbl_id, a.seq_no \n" +
				"from (select checkno, acctno, pbl_id, seq_no from rt_payments where checkno is not null) a \n" +
				"left join (select distinct on (a.pbl_id, a.seq_no) a.entity_id, b.entity_name, a.pbl_id, a.seq_no, a.entity_id  \n" +
				"from mf_sold_unit a left join mf_entity b on a.entity_id = b.entity_id) b on a.pbl_id=b.pbl_id and a.seq_no=b.seq_no \n" ;
		return SQL;
	}

	public static String SQL_BANK() {
		String SQL = "select\n" +
				"distinct on (a.bank_id)  b.bank_alias, b.bank_name, a.bank_id \n" +
				"from rt_payments a \n" +
				"left join mf_bank b on a.bank_id = b.bank_id \n" ;
		return SQL;
	}

	public static Map<String, String> CIVIL_STATUS() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		pgSelect db = new pgSelect();
		db.select("SELECT civil_status_code, civil_status_desc FROM mf_civil_status WHERE status_id = 'A' ORDER BY sort_order;");

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				String civilstatuscode = (String) db.getResult()[x][0];
				String civilstatusdesc = (String) db.getResult()[x][1];

				map.put(civilstatuscode, civilstatusdesc);
			}
		}
		return map;
	}

	public static String GET_CIVIL_STATUS_CODE(String civil_status) {
		String civilstatuscode = null;
		for(Entry<String, String> entry : CIVIL_STATUS().entrySet()){
			if(entry.getValue().equals(civil_status)){
				civilstatuscode = entry.getKey();
			}
		}
		return civilstatuscode;
	}

	public static Map<String, String> CITIZENSHIP() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		pgSelect db = new pgSelect();
		db.select("SELECT TRIM(citizenship_code), TRIM(citizenship_desc) FROM mf_citizen WHERE status_id = 'A' ORDER BY citizenship_desc;");

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				String citizenshipcode = (String) db.getResult()[x][0];
				String citizenshipdesc = (String) db.getResult()[x][1];

				map.put(citizenshipcode, citizenshipdesc);
			}
		}
		return map;
	}

	public static String GET_CITIZENSHIP_CODE(String citizenship) {
		String citizenshipcode = null;
		for(Entry<String, String> entry : CITIZENSHIP().entrySet()){
			if(entry.getValue().equals(citizenship)){
				citizenshipcode = entry.getKey();
			}
		}
		return citizenshipcode;
	}

	protected static String SQL_OFFICE_BRANCH() {
		String SQL = "SELECT trim(branch_id) as \"Branch ID\", trim(branch_name) as \"Branch Name\" FROM mf_office_branch";
		return SQL;
	}





	/**
	 * Added 2014-11-06 by Alvin Gonzales
	 *
	 */
	public void setComponentsEnabled(JPanel panel, boolean enable) {
		panelLooper(panel, enable);
	}

	public void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JXTable) {
				return;
			}

			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				panelLooper((JScrollPane) comp, enable);
			} else if (comp instanceof JTabbedPane) {
				//panelLooper((JTabbedPane) comp, enable);
			} else {
				if (comp.getName() != null) {
					comp.setEnabled(enable);
				} else {
					if (panel instanceof JScrollPane) {
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					} else {
						comp.setEnabled(enable);
					}
				}
			}
		}
	}


	/**
	 * Added 2014-11-07 by Alvin Gonzales
	 *
	 */
	public void setComponentsEditable(JPanel panel, boolean editable) {
		panelLooperEditable(panel, editable);
	}

	public void panelLooperEditable(Container panel, boolean editable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooperEditable((JPanel) comp, editable);
			} else if (comp instanceof JScrollPane) {
				panelLooperEditable((JScrollPane) comp, editable);
			} else {
				if(comp instanceof JTextField){
					((JTextField)comp).setEditable(editable);
				}
			}
		}
	}
	
	
	
	
	/**
	 * Added 2016-07-01 by Christian Paquibot
	 *
	 */
	public void setComponentsClear(JPanel panel, String clear) {
		panelLooperClear(panel, clear);
	}

	public void panelLooperClear(Container panel, String clear) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooperClear((JPanel) comp, clear);
			} else {
				if(comp instanceof JTextField){
					((JTextField)comp).setText(clear);
				}
				if(comp instanceof _JLookup){
					((_JLookup)comp).setValue(clear);
				}
				if(comp instanceof JTextArea){
					((JTextArea)comp).setText(clear);
				}
				if(comp instanceof _JXFormattedTextField){
					((_JXFormattedTextField)comp).setValue(null);
				}
				
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public String getTitleMenu() {
		return TITLE_MENU;
	}

	public void setTitleMenu(String title_menu) {
		TITLE_MENU = title_menu;
	}

	public String getPrimaryTitle() {
		return PRIMARY_TITLE;
	}

	public void setPrimaryTitle(String primary_title) {
		PRIMARY_TITLE = primary_title;
	}

	public String getSecondaryTitle() {
		return SECONDARY_TITLE;
	}

	public void setSecondaryTitle(String secondary_title) {
		SECONDARY_TITLE = secondary_title;
	}

	public Timer getTimerTitle() {
		return timerTitle;
	}

	public void setTimerTitle(Timer timerTitle) {
		this.timerTitle = timerTitle;
	}

}
