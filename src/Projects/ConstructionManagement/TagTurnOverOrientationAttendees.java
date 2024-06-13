package Projects.ConstructionManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JCheckListItem;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_orientation_attendees;
import tablemodel.model_unit_status_monitoring;

public class TagTurnOverOrientationAttendees extends _JInternalFrame implements _GUI, ActionListener,MouseListener {
	
	private static final long serialVersionUID = -6517018477299344044L;
	
	static String title = "Tag TurnOver Orientation Attendees";
	Dimension frameSize = new Dimension(700, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;
	JPanel pnlDate;
	JPanel pnlCreateRPLF;
	
	JLabel lblNorth;
	JLabel lblProject;
	JLabel lblCompany;
	JLabel lblPhase;
	
	JXTextField txtPhase;
	JXTextField txtCompany;
	JXTextField txtProject;
	JXTextField txtSearch;
	
	_JLookup lookupCompany;
	_JLookup lookupProject;
	_JLookup lookupPhase;
	
	JButton btnSave;
	JButton btnCancel;
	
	JScrollPane scrollPhase;
	JScrollPane scrollBlock;
	JList listPhase;
	JList listBlock;
	_JDateChooser dateAsOf;
	JList rowheaderOrientAttendees;
	_JCheckListItem item;
	_JCheckListItem item1;
	JScrollPane scrollOrientAttendees;
	_JTableMain tblOrientAttendees;
	_JLookup lookupVersion;
	model_orientation_attendees modelOrientation;

	JDateChooser dateStatus;
	_JDateChooser dteRefDate;
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
	
	Map<String, Object> mapBlocks = new HashMap<String, Object>();
	
	public TagTurnOverOrientationAttendees() {
		super(title, false, true, false, true);
		initGUI();
	}

	public TagTurnOverOrientationAttendees(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public TagTurnOverOrientationAttendees(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

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

				pnlMain = new JPanel();
				this.getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setLayout(new BorderLayout(5, 5));
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlNorth = new JPanel();
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setLayout(null);
					pnlNorth.setBorder(lineBorder);
					pnlNorth.setPreferredSize(new Dimension(750, 145));// XXX
					{
						lblNorth = new JLabel("Company");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 10, 120, 22);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlNorth.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setBounds(90, 10, 120, 22);
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
						txtCompany = new JXTextField("");
						pnlNorth.add(txtCompany);
						txtCompany.setHorizontalAlignment(JLabel.LEFT);
						txtCompany.setBounds(215, 10, 300, 22);
						txtCompany.setEnabled(false);
					}
						{
							lblNorth = new JLabel("Project");
							pnlNorth.add(lblNorth);
							lblNorth.setHorizontalAlignment(JLabel.LEFT);
							lblNorth.setBounds(10, 35, 120, 22);
						}
						{
							lookupProject = new _JLookup(null, "Project", "Please select company.");
							pnlNorth.add(lookupProject);
							lookupProject.setReturnColumn(0);
							lookupProject.setBounds(90, 35, 120, 22);
							lookupProject.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										txtProject.setText(String.format("%s", (String) data[1]));
										lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
										displayorientation((String) data[0], null);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							txtProject = new JXTextField("");
							pnlNorth.add(txtProject);
							txtProject.setHorizontalAlignment(JLabel.LEFT);
							txtProject.setBounds(215, 35, 300, 22);
							txtProject.setEnabled(false);
						}
						{
							lblNorth = new JLabel("Phase");
							pnlNorth.add(lblNorth);
							lblNorth.setHorizontalAlignment(JLabel.LEFT);
							lblNorth.setBounds(10, 60, 120, 22);
						}
						{
							lookupPhase = new _JLookup(null, "Phase", "Please select project.");
							pnlNorth.add(lookupPhase);
							lookupPhase.setReturnColumn(0);
							lookupPhase.setBounds(90, 60, 120, 22);
							lookupPhase.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										txtPhase.setText(String.format("Phase %s", (String) data[0]));
										//displayToCMD(lookupProject.getValue(), (String) data[0] );
										displayorientation(lookupProject.getValue(), (String) data[0]);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							txtPhase = new JXTextField("");
							pnlNorth.add(txtPhase);
							txtPhase.setHorizontalAlignment(JLabel.LEFT);
							txtPhase.setBounds(215, 60, 300, 22);
							txtPhase.setEnabled(false);
						}
						{
							JPanel pnlSearch = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnlSearch);
							pnlSearch.setBorder(JTBorderFactory.createTitleBorder("Search Buyer's Name"));
							pnlSearch.setBounds(10, 85, 530, 60);
							{
								JPanel pnlUploading = new JPanel(new GridLayout(1, 1, 10, 0));
								pnlSearch.add(pnlUploading, BorderLayout.WEST);
								{
									txtSearch = new JXTextField();
									pnlUploading.add(txtSearch);
									txtSearch.setPreferredSize(new java.awt.Dimension(480, 0));
									txtSearch.setHorizontalAlignment(JTextField.CENTER);	
									txtSearch.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent e) {	
											checkAllClientList();
										}				 
									});	
								}
								
							}
						}
					/*{
						cmbCriteria = new JComboBox(new DefaultComboBoxModel(getCriteria()));
						pnlNorth.add(cmbCriteria);
						cmbCriteria.setBounds(90, 110, 300, 22);
						cmbCriteria.addActionListener(this);
					}*/
					{
						pnlCenter = new JPanel();
						pnlMain.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setLayout(new BorderLayout(5, 5));
						pnlCenter.setBorder(lineBorder);
						{
							dteRefDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCenter.add(dteRefDate);
							dteRefDate.setDate(null);
							((JTextFieldDateEditor)dteRefDate.getDateEditor()).setEditable(false);
							dteRefDate.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							((JTextFieldDateEditor)dteRefDate.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
								public void insertUpdate(DocumentEvent evt) {
									SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
									
									if (modelOrientation.getValueAt(tblOrientAttendees.getSelectedRow(),0).equals(true)) {
										modelOrientation.setValueAt(""+sdf.format(dteRefDate.getDate()),tblOrientAttendees.getSelectedRow(),1);
									}
								}
								public void changedUpdate(DocumentEvent e) {}
								public void removeUpdate(DocumentEvent e) {}
							});
						}
						
						{
							scrollOrientAttendees = new JScrollPane();
							pnlCenter.add(scrollOrientAttendees, BorderLayout.CENTER);
							scrollOrientAttendees.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollOrientAttendees.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							
							modelOrientation = new model_orientation_attendees();
							modelOrientation.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderOrientAttendees.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderOrientAttendees.getModel()).addElement(modelOrientation.getRowCount());
									}
								}
							});
							
							tblOrientAttendees = new _JTableMain(modelOrientation);
							scrollOrientAttendees.setViewportView(tblOrientAttendees);
							tblOrientAttendees.addMouseListener(this);
							tblOrientAttendees.hideColumns("Batch");
							tblOrientAttendees.hideColumns("PBL");
							
							rowheaderOrientAttendees = tblOrientAttendees.getRowHeader();
							rowheaderOrientAttendees.setModel(new DefaultListModel());
							scrollOrientAttendees.setRowHeaderView(rowheaderOrientAttendees);
							scrollOrientAttendees.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							
						}
						
						}
				}
				
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					//pnlSouth.setBorder(lineBorder);
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX	
					{
						btnSave = new JButton("Save");
						pnlSouth.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlSouth.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int response = JOptionPane.showConfirmDialog(TagTurnOverOrientationAttendees.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
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
		//displayUnit(null);
		btnSave.setEnabled(false);
	}
	
	private void refreshTO() {
		
		if (lookupProject.getText().equals("...")) {
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupPhase.setValue("");
			lblCompany.setText("[ ]");
			lblProject.setText("[ ]");
			lblPhase.setText("[ ]");
			btnSave.setEnabled(false);
			modelOrientation.setRowCount(0);
			
		}
	} // refreshTO()

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblOrientAttendees)) {
			int row = tblOrientAttendees.convertRowIndexToModel(tblOrientAttendees.getSelectedRow());
			Boolean isSelected2 = (Boolean) modelOrientation.getValueAt(row, 0);

			for(int x = 0; x < modelOrientation.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelOrientation.getValueAt(x, 0);
				if (isSelected) {
					if (modelOrientation.getValueAt(x, 1) == null) {
						modelOrientation.setValueAt(sdf.format(Calendar.getInstance().getTime()), x, 1);
					}
					btnSave.setEnabled(true);
				}else {
					modelOrientation.setValueAt(null, x, 1);
					btnSave.setEnabled(false);
				}
			}
			
			if (isSelected2) {
				if (e.getClickCount()==2) {
					if (tblOrientAttendees.getSelectedColumn()== 1) {
						if (e.getClickCount() ==2) {
							dteRefDate.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
							dteRefDate.getCalendarButton().doClick();
						}

					}
				}
				btnSave.setEnabled(true);
			}
		}
		
		if (tblOrientAttendees.columnAtPoint(e.getPoint())==1)   // Date
		{
			if (modelOrientation.getValueAt(tblOrientAttendees.rowAtPoint(e.getPoint()),0).equals(true)) {
				tblOrientAttendees.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				tblOrientAttendees.setToolTipText("Double Click to change Trans Date");
			}
			else {
				tblOrientAttendees.setCursor(null);
				tblOrientAttendees.setToolTipText(null);
			}
		}
		
	else {
			tblOrientAttendees.setCursor(null);
			tblOrientAttendees.setToolTipText(null);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		//String criteria = (String) cmbCriteria.getSelectedItem();
		
		if(actionCommand.equals("Save")){
			
			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				modelOrientation.setRowCount(0);
			}

			for(int x =0; x < modelOrientation.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelOrientation.getValueAt(x, 0);
				String status_date = (String) modelOrientation.getValueAt(x, 1);
				String batch = (String) modelOrientation.getValueAt(x, 5);
				String pbl = (String) modelOrientation.getValueAt(x, 6);
				if(isSelected){
					
					String strSQL = "SELECT sp_save_tag_orientation_attendees('"+status_date+"'::timestamp, '"+pbl+"', '"+batch+"', '"+UserInfo.EmployeeCode +"');" ; 
					
					pgSelect db = new pgSelect();
					db.select(strSQL);
				}
			}
			
				btnSave.setEnabled(false);
				modelOrientation.setRowCount(0);
				
		}
		
	}
	
	private void displayorientation(String proj_id, String phase) {

		FncTables.clearTable(modelOrientation);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = /*"select distinct on (client_name, a.pbl_id) false, null, get_client_name(b.entity_id) as client_name, a.description, c.model_desc, d.batch_no, a.pbl_id\n" + 

		"from mf_unit_info a \n" +
		
		"left join rf_sold_unit b on a.pbl_id = b.pbl_id and a.proj_id = b.projcode \n" +
		"left join mf_product_model c on a.model_id = c.model_id  \n" + 
		"left join rf_qualified4orientation d on a.proj_id = d.projcode and a.pbl_id = d.pbl_id and b.entity_id = d.entity_id and b.seq_no = d.seq_no  \n" + 
		"left join rf_buyer_status m on b.entity_id = m.entity_id and b.projcode = m.proj_id and b.pbl_id = m.pbl_id and b.seq_no = m.seq_no  \n" +
		
		"where (CASE WHEN ('"+lookupPhase.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupPhase.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.phase ~* '"+lookupPhase.getValue()+"' END) \n" +
		"and (CASE WHEN ('"+lookupProject.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupProject.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.proj_id ~* '"+lookupProject.getValue()+"' END) \n" +
		"and m.byrstatus_id in ('32', '135', '27')\n" + 
		"and b.status_id='A'\n" +
		"and a.ntc is not null\n" +
		"and d.tover_date is null   \n" + 
		"order by client_name, a.pbl_id \n";*/
		"SELECT * FROM ( \n"+
		"SELECT false, null, get_client_name(a.entity_id), b.description, c.model_desc, d.batch_no, a.pbl_id\n" + 
		"FROM rf_sold_unit a\n" + 
		"LEFT JOIN mf_unit_info b on b.proj_id = a.projcode and b.pbl_id = a.pbl_id and coalesce(b.server_id,'')=coalesce(a.server_id,'')\n" + 
		"LEFT JOIN mf_product_model c on c.model_id = a.model_id and coalesce(c.server_id,'')=coalesce(a.server_id,'')\n" + 
		"LEFT JOIN rf_qualified4orientation d on d.entity_id = a.entity_id and d.projcode = a.projcode and d.pbl_id = a.pbl_id and d.seq_no = a.seq_no and d.status_id = 'A'\n" + 
		"LEFT JOIN rf_buyer_status e on e.entity_id = a.entity_id and e.proj_id = a.projcode and e.pbl_id = a.pbl_id and e.seq_no = a.seq_no and e.status_id = 'A' and coalesce(e.server_id,'')=coalesce(a.server_id,'')\n" + 
		"where (CASE WHEN ('"+lookupPhase.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupPhase.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE b.phase ~* '"+lookupPhase.getValue()+"' END) \n" + 
		"and (CASE WHEN ('"+lookupProject.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupProject.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.projcode ~* '"+lookupProject.getValue()+"' END) \n" + 
		"and e.byrstatus_id in ('32', '135', '27')\n" + 
		"AND a.currentstatus != '02'\n" + 
		"and a.status_id = 'A'\n" + 
		//"and b.ntc is not null\n" + 
		"and d.tover_date is null\n" + 
		"UNION\n" + 
		"SELECT false, null, get_client_name(a.entity_id), b.description, c.model_desc, d.batch_no, a.oth_pbl_id\n" + 
		"FROM hs_sold_other_lots a\n" + 
		"LEFT JOIN mf_unit_info b on b.proj_id = a.proj_id and b.pbl_id = a.oth_pbl_id and coalesce(b.server_id,'')=coalesce(a.server_id,'')\n" + 
		"LEFT JOIN mf_product_model c on c.model_id = b.model_id and coalesce(c.server_id,'')=coalesce(a.server_id,'')\n" + 
		"LEFT JOIN rf_qualified4orientation d on d.entity_id = a.entity_id and d.projcode = a.proj_id and d.pbl_id = a.oth_pbl_id and d.seq_no = a.seq_no and d.status_id = 'A'\n" + 
		"where (CASE WHEN ('"+lookupPhase.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupPhase.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE b.phase ~* '"+lookupPhase.getValue()+"' END) \n" + 
		"and (CASE WHEN ('"+lookupProject.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupProject.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.proj_id ~* '"+lookupProject.getValue()+"' END) \n" +  
		"and a.status_id = 'A'\n" + 
		"and d.tover_date is null) a \n"+
		"ORDER BY a.get_client_name, a.pbl_id";

		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display SQL for Qualified Orientation", sql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelOrientation.addRow(db.getResult()[x]);
				listModel.addElement(modelOrientation.getRowCount());				
			}	
		}		
			scrollOrientAttendees.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblOrientAttendees.getRowCount())));
			tblOrientAttendees.packAll();
			tblOrientAttendees.setEditable(true);
			btnSave.setEnabled(false);
	}
		
	private void checkAllClientList(){//

		int rw = tblOrientAttendees.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";
			
			try { name	= tblOrientAttendees.getValueAt(x,2).toString().toUpperCase();}
			 catch (NullPointerException e) { name	= ""; }
			
			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			//Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			//Boolean	end			= name.endsWith(acct_name);

			if (start==true) {
				tblOrientAttendees.setRowSelectionInterval(x, x); 
				tblOrientAttendees.changeSelection(x, 2, false, false);				
				break;			
			}
			else {
				tblOrientAttendees.setRowSelectionInterval(0, 0); 
				tblOrientAttendees.changeSelection(0, 2, false, false);					
			}

			x++;

		}		
	}
}
