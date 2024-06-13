package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class EditAvailer extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = -6517018477299344044L;

	static String title = "Edit Availer";
	Dimension frameSize = new Dimension(500, 240);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlProcLabel;

	private JPanel pnlProcCenterComponents;

	private JPanel pnlProcLookupRPLF;

	private _JLookup lookupProcRPLF;

	private JLabel lblProcRPLF;

	private JLabel lblProcAvailer;

	private JPanel pnlProcWestlabel;

	private JPanel pnlProcLookupAvailer;

	private _JLookup lookupProcAvailer;

	private JTextField txtProcAvailer;

	private JPanel pnlTransWestlabel;

	private JPanel pnlTransLabel;

	private JLabel lblTransRPLF;

	private JLabel lblTransAvailer;

	private JPanel pnlTransCenterComponents;

	private JPanel pnlTransLookupAvailer;

	private JPanel pnlTransLookupRPLF;

	private _JLookup lookupTransRPLF;

	private _JLookup lookupTransAvailer;

	private JTextField txtTransAvailer;

	private JButton btnSave;

	private JPanel pnlMain;

	private JTabbedPane tabNorth;

	private JPanel pnlSouth;

	private JButton btnCancel;

	public EditAvailer() {
		super(title, false, true, false, true);
		initGUI();
	}

	public EditAvailer(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public EditAvailer(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(10, 10));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				tabNorth = new JTabbedPane();
				pnlMain.add(tabNorth, BorderLayout.NORTH);
				tabNorth.setPreferredSize(new Dimension(500, 140));// XXX
				{
					pnlProcWestlabel = new JPanel(new BorderLayout(5, 5));
					tabNorth.addTab("Processing Cost", null, pnlProcWestlabel, null);
					pnlProcWestlabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlProcLabel = new JPanel(new GridLayout(2, 0, 5, 5));
						pnlProcWestlabel.add(pnlProcLabel, BorderLayout.WEST);
						{
							lblProcRPLF= new JLabel("RPLF No.:");
							pnlProcLabel.add(lblProcRPLF);
							lblProcRPLF.setHorizontalAlignment(JLabel.RIGHT);
						}
						{
							lblProcAvailer = new JLabel("Availer:");
							pnlProcLabel.add(lblProcAvailer);
							lblProcAvailer.setHorizontalAlignment(JLabel.RIGHT);
						}
					}
				}
				{
					pnlProcCenterComponents = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlProcWestlabel.add(pnlProcCenterComponents, BorderLayout.CENTER);
					{
						pnlProcLookupRPLF = new JPanel(new BorderLayout(5, 5));
						pnlProcCenterComponents.add(pnlProcLookupRPLF);
						{
							lookupProcRPLF = new _JLookup(null, "PCOST RPLF No.");
							pnlProcLookupRPLF.add(lookupProcRPLF, BorderLayout.WEST);
							lookupProcRPLF.setLookupSQL( sql_PCOSTRPLF());
							lookupProcRPLF.setReturnColumn(0);
							lookupProcRPLF.setPreferredSize(new Dimension(100, 20));
							lookupProcRPLF.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										FncSystem.out("Display sql description:", lookupProcRPLF.getLookupSQL());

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
					}
					{
						pnlProcLookupAvailer = new JPanel(new BorderLayout(5, 5));
						pnlProcCenterComponents.add(pnlProcLookupAvailer);
						{
							lookupProcAvailer = new _JLookup(null, "Availer Type");
							pnlProcLookupAvailer.add(lookupProcAvailer, BorderLayout.WEST);
							lookupProcAvailer.setLookupSQL(SQL_AVAILER());
							lookupProcAvailer.setReturnColumn(0);
							lookupProcAvailer.setPreferredSize(new Dimension(100, 20));
							lookupProcAvailer.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										String name = (String) data [1];

										FncSystem.out("Display sql description:", lookupProcAvailer.getLookupSQL());

										txtProcAvailer.setText(name);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							{
								txtProcAvailer = new JTextField("");
								pnlProcLookupAvailer.add(txtProcAvailer,BorderLayout.CENTER);
								txtProcAvailer.setHorizontalAlignment(JLabel.LEFT);
								txtProcAvailer.setEditable(false);
							}
						}
					}
				}
				{
					pnlTransWestlabel = new JPanel(new BorderLayout(5, 5));
					tabNorth.addTab("Transfer Cost", null, pnlTransWestlabel, null);
					pnlTransWestlabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlTransLabel = new JPanel(new GridLayout(2, 0, 5, 5));
						pnlTransWestlabel.add(pnlTransLabel, BorderLayout.WEST);
						{
							lblTransRPLF = new JLabel("RPLF No.:");
							pnlTransLabel.add(lblTransRPLF);
							lblTransRPLF.setHorizontalAlignment(JLabel.RIGHT);
						}

						lblTransAvailer = new JLabel("Availer:");
						pnlTransLabel.add(lblTransAvailer);
						lblTransAvailer.setHorizontalAlignment(JLabel.RIGHT);
					}
				}
				{
					pnlTransCenterComponents = new JPanel(new GridLayout(2, 0, 5, 5));
					pnlTransWestlabel.add(pnlTransCenterComponents, BorderLayout.CENTER);
					{
						pnlTransLookupRPLF= new JPanel(new BorderLayout(5, 5));
						pnlTransCenterComponents.add(pnlTransLookupRPLF);
						{
							lookupTransRPLF = new _JLookup(null, "TCOST RPLF No.");
							pnlTransLookupRPLF.add(lookupTransRPLF, BorderLayout.WEST);
							lookupTransRPLF.setLookupSQL(sql_TCOSTRPLF());
							lookupTransRPLF.setReturnColumn(0);
							lookupTransRPLF.setPreferredSize(new Dimension(100, 20));
							lookupTransRPLF.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										
										FncSystem.out("Display sql description:", lookupTransRPLF.getLookupSQL());

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
					}
					{
						pnlTransLookupAvailer = new JPanel(new BorderLayout(5, 5));
						pnlTransCenterComponents.add(pnlTransLookupAvailer);
						{
							lookupTransAvailer= new _JLookup(null, "Availer");
							pnlTransLookupAvailer.add(lookupTransAvailer, BorderLayout.WEST);
							lookupTransAvailer.setLookupSQL(SQL_AVAILER());
							lookupTransAvailer.setReturnColumn(0);
							lookupTransAvailer.setPreferredSize(new Dimension(100, 20));
							lookupTransAvailer.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										String name = (String) data [1];

										FncSystem.out("Display sql description:", lookupProcAvailer.getLookupSQL());

										txtTransAvailer.setText(name);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							{
								txtTransAvailer = new JTextField();
								pnlTransLookupAvailer.add(txtTransAvailer,BorderLayout.CENTER);
								txtTransAvailer.setHorizontalAlignment(JLabel.LEFT);
								txtTransAvailer.setEditable(false);
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				pnlSouth.setPreferredSize(new Dimension(500, 50));// XXX
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(true);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}
	}

	//SQL

	private static String sql_PCOSTRPLF() {
		String sql = "select DISTINCT rplf_no as \"RPLF No.\", MAX(date_required) as \"Date Created\" \n" + 
				"from rf_processing_cost a \n" + 
				"where not rplf_no = '' and \n" + 
				"rplf_no is not null and \n" + 
//				"not EXISTs (select * from rf_pv_header b where a.rplf_no = b.rplf_no)\n" + -- MODIFIED BY MONIQUE DTD 06-20-2023; UNABLE TO RETREIVE OTHER RPLFS
				"not EXISTs (select * from rf_pv_header b where a.rplf_no = b.rplf_no and b.co_id = a.co_id)\n" +
				"and a.status_id = 'A'\n" + 
//				"and a.server_id is null\n" + -- COMMENTED BY MONIQUE DTD 06-20-2023; UNABLE TO RETREIVE RPLFS (ITSREAL ACCTS)
				"group by rplf_no\n" + 
				"order by MAX(date_required) DESC ";
		return sql;
	}

	private static String sql_TCOSTRPLF() {
		String sql = "select DISTINCT rplf_no as \"RPLF No.\", MAX(date_required) as \"Date Created\" \n" + 
				"from rf_transfer_cost a \n" + 
				"where not rplf_no = '' and \n" + 
				"rplf_no is not null and \n" + 
//				"not EXISTs (select * from rf_pv_header b where a.rplf_no = b.rplf_no)\n" + -- MODIFIED BY MONIQUE DTD 06-20-2023; UNABLE TO RETREIVE OTHER RPLFS 
				"not EXISTs (select * from rf_pv_header b where a.rplf_no = b.rplf_no and b.co_id = (Select co_id from mf_project where proj_id = a.projcode))\n" +
				"and a.status_id = 'A'\n" + 
//				"and a.server_id is null\n" + -- COMMENTED BY MONIQUE DTD 06-20-2023; UNABLE TO RETREIVE RPLFS (ITSREAL ACCTS)
				"group by rplf_no\n" + 
				"order by MAX(date_required) DESC ";
		return sql;
	}

	private static String SQL_AVAILER() {
		return "SELECT trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\", entity_kind as \"Kind\", vat_registered as \"VAT\"  \n" +
				"FROM (select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in \r\n" + 
				"(select entity_id from rf_entity_assigned_type " +
				" ) and server_id is null) a \n" +
				"ORDER BY a.entity_name  ";		
	}

	public void updatePCOSTAvailer(pgUpdate db){

		String rplf_no = "";
		String availer	= "";		

		rplf_no = lookupProcRPLF .getText();
		availer = lookupProcAvailer.getText();

		String sqlDetail =
				"update rf_request_header set " + 
						"entity_id2 = '"+availer+"',  \n" +	
						"edited_by = '"+UserInfo.EmployeeCode+"',   \n" +
						"date_edited = now() \n" +
						"where rplf_no = '"+rplf_no+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}
	
	public void updateTCOSTAvailer(pgUpdate db){

		String rplf_no = "";
		String availer	= "";		

		rplf_no = lookupTransRPLF .getText();
		availer = lookupTransAvailer.getText();

		String sqlDetail =
				"update rf_request_header set " + 
						"entity_id2 = '"+availer+"',  \n" +	
						"edited_by = '"+UserInfo.EmployeeCode+"',   \n" +
						"date_edited = now() \n" +
						"where rplf_no = '"+rplf_no+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Save")) { save(); }

		if (e.getActionCommand().equals("Cancel")) { cancel(); }
	}

	private void save(){

		if (tabNorth.getSelectedIndex() == 0) {
			if(checkPCOSTDetails()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Please complete filling up the form.", "Information", 
					JOptionPane.WARNING_MESSAGE);}
			else{
				executeSave();}

		}
		
		if (tabNorth.getSelectedIndex() == 1) {
			if(checkTCOSTDetails()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Please complete filling up the form.", "Information", 
					JOptionPane.WARNING_MESSAGE);}
			else{
				executeSave();}
		}
	}

	public void executeSave() {

		if (tabNorth.getSelectedIndex() == 0) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();
				updatePCOSTAvailer(db);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"All data updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				refresh_fields();
			}
		}
		
		if (tabNorth.getSelectedIndex() == 1) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();
				updateTCOSTAvailer(db);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"All data updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				refresh_fields();
			}
		}
	}

	public void cancel(){

		if (tabNorth.getSelectedIndex() == 0 || tabNorth.getSelectedIndex() == 1) {
			refresh_fields();
		}
	}

	public Boolean checkPCOSTDetails(){

		boolean x = false;		

		String rplf_no;
		String availer;

		rplf_no = lookupProcRPLF .getText();
		availer = lookupProcAvailer. getText();

		if (rplf_no.equals("") || availer.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}
	
	public Boolean checkTCOSTDetails(){

		boolean x = false;		

		String rplf_no;
		String availer;

		rplf_no = lookupTransRPLF .getText();
		availer = lookupTransAvailer. getText();

		if (rplf_no.equals("") || availer.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}

	private void refresh_fields(){

		lookupProcRPLF.setText("");
		lookupProcAvailer.setText("");
		txtProcAvailer.setText("");
		lookupTransRPLF.setText("");
		lookupTransAvailer.setText("");
		txtTransAvailer.setText("");
	}
}