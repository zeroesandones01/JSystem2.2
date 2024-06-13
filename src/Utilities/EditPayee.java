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

public class EditPayee extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = -6517018477299344044L;

	static String title = "Edit Payee";
	Dimension frameSize = new Dimension(500, 240);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlProcLabel;

	private JPanel pnlProcCenterComponents;

	private JPanel pnlProcLookupRPLF;

	private _JLookup lookupProcRPLF;

	private JLabel lblProcRPLF;

	private JLabel lblProcPayee;

	private JPanel pnlProcWestlabel;

	private JPanel pnlProcLookupPayee;

	private _JLookup lookupProcPayee;

	private JTextField txtProcPayee;

	private JPanel pnlTransWestlabel;

	private JPanel pnlTransLabel;

	private JLabel lblTransRPLF;

	private JLabel lblTransAvailer;

	private JPanel pnlTransCenterComponents;

	private JPanel pnlTransLookupPayee;

	private JPanel pnlTransLookupRPLF;

	private _JLookup lookupTransRPLF;

	private _JLookup lookupTransPayee;

	private JTextField txtTransPayee;

	private JButton btnSave;

	private JPanel pnlMain;

	private JTabbedPane tabNorth;

	private JPanel pnlSouth;

	private JButton btnCancel;

	public EditPayee() {
		super(title, false, true, false, true);
		initGUI();
	}

	public EditPayee(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public EditPayee(String title, boolean resizable,
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
				
			}
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
							lblProcPayee = new JLabel("Payee:");
							pnlProcLabel.add(lblProcPayee);
							lblProcPayee.setHorizontalAlignment(JLabel.RIGHT);
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
						pnlProcLookupPayee = new JPanel(new BorderLayout(5, 5));
						pnlProcCenterComponents.add(pnlProcLookupPayee);
						{
							lookupProcPayee = new _JLookup(null, "Payee");
							pnlProcLookupPayee.add(lookupProcPayee, BorderLayout.WEST);
							lookupProcPayee.setLookupSQL(SQL_PAYEE());
							lookupProcPayee.setReturnColumn(0);
							lookupProcPayee.setPreferredSize(new Dimension(100, 20));
							lookupProcPayee.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										String name = (String) data [1];

										FncSystem.out("Display sql description:", lookupProcPayee.getLookupSQL());

										txtProcPayee.setText(name);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							{
								txtProcPayee = new JTextField("");
								pnlProcLookupPayee.add(txtProcPayee,BorderLayout.CENTER);
								txtProcPayee.setHorizontalAlignment(JLabel.LEFT);
								txtProcPayee.setEditable(false);
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

						lblTransAvailer = new JLabel("Payee:");
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
						pnlTransLookupPayee = new JPanel(new BorderLayout(5, 5));
						pnlTransCenterComponents.add(pnlTransLookupPayee);
						{
							lookupTransPayee= new _JLookup(null, "Payee");
							pnlTransLookupPayee.add(lookupTransPayee, BorderLayout.WEST);
							lookupTransPayee.setLookupSQL(SQL_PAYEE());
							lookupTransPayee.setReturnColumn(0);
							lookupTransPayee.setPreferredSize(new Dimension(100, 20));
							lookupTransPayee.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										String name = (String) data [1];

										FncSystem.out("Display sql description:", lookupProcPayee.getLookupSQL());

										txtTransPayee.setText(name);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							{
								txtTransPayee = new JTextField();
								pnlTransLookupPayee.add(txtTransPayee,BorderLayout.CENTER);
								txtTransPayee.setHorizontalAlignment(JLabel.LEFT);
								txtTransPayee.setEditable(false);
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
				"not EXISTs (select * from rf_pv_header b where a.rplf_no = b.rplf_no and status_id = 'A')\n" + 
				"and a.status_id = 'A'\n" + 
				"group by rplf_no\n" + 
				"order by MAX(date_required) DESC ";
		return sql;
	}

	private static String sql_TCOSTRPLF() {
		String sql = "select DISTINCT rplf_no as \"RPLF No.\", MAX(date_required) as \"Date Created\" \n" + 
				"from rf_transfer_cost a \n" + 
				"where not rplf_no = '' and \n" + 
				"rplf_no is not null and \n" + 
				"not EXISTs (select * from rf_pv_header b where a.rplf_no = b.rplf_no and status_id = 'A')\n" + 
				"and a.status_id = 'A'\n" + 
				"group by rplf_no\n" + 
				"order by MAX(date_required) DESC ";
		return sql;
	}

	private static String SQL_PAYEE() {
		return "SELECT trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\", entity_kind as \"Kind\", vat_registered as \"VAT\"  \n" +
				"FROM (select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in \r\n" + 
				"(select entity_id from rf_entity_assigned_type " +
				" )) a \n" +
				"ORDER BY a.entity_name  ";		
	}

	public void updatePCOSTPayee(pgUpdate db){

		String rplf_no  = "";
		String payee	= "";		

		rplf_no = lookupProcRPLF .getText();
		payee = lookupProcPayee.getText();

		String sqlDetail =
				"update rf_request_header set " + 
						"entity_id1 = '"+payee+"',  \n" +	
						"edited_by = '"+UserInfo.EmployeeCode+"',   \n" +
						"date_edited = now() \n" +
						"where rplf_no = '"+rplf_no+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}
	
	public void updateTCOSTPayee(pgUpdate db){

		String rplf_no  = "";
		String payee	= "";		

		rplf_no = lookupTransRPLF .getText();
		payee = lookupTransPayee.getText();

		String sqlDetail =
				"update rf_request_header set " + 
						"entity_id1 = '"+payee+"',  \n" +	
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
				updatePCOSTPayee(db);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"All data updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				refresh_fields();
			}
		}
		
		if (tabNorth.getSelectedIndex() == 1) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();
				updateTCOSTPayee(db);
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
		String payee;

		rplf_no = lookupProcRPLF .getText();
		payee   = lookupProcPayee. getText();

		if (rplf_no.equals("") || payee.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}
	
	public Boolean checkTCOSTDetails(){

		boolean x = false;		

		String rplf_no;
		String payee;

		rplf_no = lookupTransRPLF .getText();
		payee   = lookupTransPayee. getText();

		if (rplf_no.equals("") || payee.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}

	private void refresh_fields(){

		lookupProcRPLF.setText("");
		lookupProcPayee.setText("");
		txtProcPayee.setText("");
		lookupTransRPLF.setText("");
		lookupTransPayee.setText("");
		txtTransPayee.setText("");
	}
}