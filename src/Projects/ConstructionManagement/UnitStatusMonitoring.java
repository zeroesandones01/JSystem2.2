package Projects.ConstructionManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import components._JCheckListItem;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_unit_status_monitoring;

public class UnitStatusMonitoring extends _JInternalFrame implements _GUI, ActionListener,MouseListener {
	
	private static final long serialVersionUID = -6517018477299344044L;
	
	static String title = "Unit Status Monitoring";
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
	JLabel lblDate;
	JLabel lblStatus;
	JLabel lblBatch;
	JLabel lblBlock;
	JLabel lblPhase;
	JLabel lblDate1;
	JLabel lblRequestType;
	JLabel lblAvailer;
	JLabel lblAvailerType;
	JLabel lblStatusDesc;
	
	JTextField txtStatus;
	JTextField txtBatch;
	JTextField txtBlock;
	JTextField txtProject;
	
	_JLookup lookupCompany;
	_JLookup lookupProject;
	_JLookup lookupPhase;
	_JLookup lookupBlock;
	_JLookup lookupRequestType;
	_JLookup lookupAvailer;
	_JLookup lookupAvailerType;
	_JLookup lookupStatus;
	
	JButton btnSave;
	JButton btnCancel;
	JButton btnOK;
	JButton btnDRF;
	JButton btnSaveRPLF;
	JButton btnCancelRPLF;
	
	JComboBox cmbCriteria;
	
	JScrollPane scrollPhase;
	JScrollPane scrollBlock;
	JList listPhase;
	JList listBlock;
	_JDateChooser dateAsOf;
	JList rowheaderUnitStatus;
	_JCheckListItem item;
	_JCheckListItem item1;
	JScrollPane scrollUnitStatus;
	_JTableMain tblUnitStatus;
	_JLookup lookupVersion;
	model_unit_status_monitoring modelUnitStatus;

	JDateChooser dateStatus;
	_JDateChooser dteRefDate;
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
	
	Map<String, Object> mapBlocks = new HashMap<String, Object>();
	
	public UnitStatusMonitoring() {
		super(title, false, true, false, true);
		initGUI();
	}

	public UnitStatusMonitoring(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public UnitStatusMonitoring(String title, boolean resizable,
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
				pnlCreateRPLF= new JPanel();
				pnlCreateRPLF.setLayout(null);
				pnlCreateRPLF.setPreferredSize(new java.awt.Dimension(500, 125));
				{
					lblNorth = new JLabel("Request Type");
					pnlCreateRPLF.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 10, 120, 22);
				}
				{
					lookupRequestType = new _JLookup(null, "Request Type");
					pnlCreateRPLF.add(lookupRequestType);
					lookupRequestType.setReturnColumn(0);
					lookupRequestType.setLookupSQL(SQL_REQUESTTYPE());
					lookupRequestType.setBounds(110, 10, 90, 22);
					lookupRequestType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								lblRequestType.setText(String.format("[ %s ]", (String) data[1]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					lblRequestType = new JLabel("[ ]");
					pnlCreateRPLF.add(lblRequestType);
					lblRequestType.setHorizontalAlignment(JLabel.LEFT);
					lblRequestType.setBounds(205, 10, 200, 22);
				}
				
				{
					lblNorth = new JLabel("Availer");
					pnlCreateRPLF.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 35, 120, 22);
				}
				{
					lookupAvailer = new _JLookup(null, "Availer");
					pnlCreateRPLF.add(lookupAvailer);
					lookupAvailer.setReturnColumn(0);
					lookupAvailer.setLookupSQL(SQL_AVAILER());
					lookupAvailer.setBounds(110, 35, 90, 22);
					lookupAvailer.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							FncSystem.out("Display SQL for Availer", lookupAvailer.getLookupSQL());
							if (data != null) {

								lblAvailer.setText(String.format("[ %s ]", (String) data[1]));
								lookupAvailerType.setLookupSQL(SQL_AVAILERTYPE((String) data[0]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					lblAvailer = new JLabel("[ ]");
					pnlCreateRPLF.add(lblAvailer);
					lblAvailer.setHorizontalAlignment(JLabel.LEFT);
					lblAvailer.setBounds(205, 35, 255, 22);
				}
				
				{
					lblNorth = new JLabel("Availer Type");
					pnlCreateRPLF.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 60, 120, 22);
				}
				{
					lookupAvailerType = new _JLookup(null, "Availer Type");
					pnlCreateRPLF.add(lookupAvailerType);
					lookupAvailerType.setReturnColumn(0);
					lookupAvailerType.setBounds(110, 60, 90, 22);
					lookupAvailerType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								lblAvailerType.setText(String.format("[ %s ]", (String) data[1]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					lblAvailerType = new JLabel("[ ]");
					pnlCreateRPLF.add(lblAvailerType);
					lblAvailerType.setHorizontalAlignment(JLabel.LEFT);
					lblAvailerType.setBounds(205, 60, 200, 22);
				}
				
				{
					btnSaveRPLF = new JButton("Create RPLF");
					pnlCreateRPLF.add(btnSaveRPLF);
					btnSaveRPLF.setActionCommand("Create RPLF");
					btnSaveRPLF.setBounds(130, 90, 100, 30);
					btnSaveRPLF.addActionListener(this);
				}
				{
					btnCancelRPLF = new JButton("Cancel");
					pnlCreateRPLF.add(btnCancelRPLF);
					btnCancelRPLF.setActionCommand("Cancel RPLF");
					btnCancelRPLF.setBounds(235, 90, 100, 30);
					btnCancelRPLF.addActionListener(this);
				}
			}
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
									lblCompany.setText(String.format("[ %s ]", (String) data[1]));
									lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblCompany = new JLabel("[ ]");
						pnlNorth.add(lblCompany);
						lblCompany.setHorizontalAlignment(JLabel.LEFT);
						lblCompany.setBounds(215, 10, 300, 22);
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

										lblProject.setText(String.format("[ %s ]", (String) data[1]));
										lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
										//displayToCMD(lookupCompany.getValue(), (String) data[0] );

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							lblProject = new JLabel("[ ]");
							pnlNorth.add(lblProject);
							lblProject.setHorizontalAlignment(JLabel.LEFT);
							lblProject.setBounds(215, 35, 250, 22);
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

										lblPhase.setText(String.format("[ Phase %s ]", (String) data[0]));
										lookupBlock.setLookupSQL(SQL_BLOCK((String) data[0]));
										//displayToCMD(lookupProject.getValue(), (String) data[0] );

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							lblPhase = new JLabel("[ ]");
							pnlNorth.add(lblPhase);
							lblPhase.setHorizontalAlignment(JLabel.LEFT);
							lblPhase.setBounds(215, 60, 250, 22);
						}
						{
							lblNorth = new JLabel("Block");
							pnlNorth.add(lblNorth);
							lblNorth.setHorizontalAlignment(JLabel.LEFT);
							lblNorth.setBounds(10, 85, 120, 22);
						}
						{
							lookupBlock = new _JLookup(null, "Block", "Please select phase.");
							pnlNorth.add(lookupBlock);
							lookupBlock.setReturnColumn(0);
							lookupBlock.setBounds(90, 85, 120, 22);
							lookupBlock.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										lblBlock.setText(String.format("[ Block %s ]", (String) data[0]));
										displayToCMD(lookupPhase.getValue(), (String) data[0] );
										
										KEYBOARD_MANAGER.focusNextComponent();
										//btnSave.setEnabled(true);
									}
								}
							});
						}
						{
							lblBlock = new JLabel("[ ]");
							pnlNorth.add(lblBlock);
							lblBlock.setHorizontalAlignment(JLabel.LEFT);
							lblBlock.setBounds(215, 85, 250, 22);
						}
					{
						lblStatus = new JLabel("Status");
						pnlNorth.add(lblStatus);
						lblStatus.setHorizontalAlignment(JLabel.LEFT);
						lblStatus.setBounds(10, 110, 120, 22);
					}
					{
						lookupStatus = new _JLookup();
						pnlNorth.add(lookupStatus);
						lookupStatus.setReturnColumn(0);
						lookupStatus.setLookupSQL(SQL_CRITERIA());
						lookupStatus.setBounds(90, 110, 120, 22);
						lookupStatus.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									lblStatusDesc.setText(String.format("[ %s ]", (String) data[1]));
									if (lookupStatus.getValue().equals("01")) {
										displayToCMD(lookupProject.getValue(), lookupPhase.getValue());
										if (UserInfo.Department.equals("07")) {
											btnSave.setEnabled(true);
										} else {
											btnSave.setEnabled(false);
										}
									} else if (lookupStatus.getValue().equals("02")) {
										displayToPMD(lookupProject.getValue(), lookupPhase.getValue());
										if (UserInfo.Department.equals("08")) {
											btnSave.setEnabled(true);
										} else {
											btnSave.setEnabled(false);
										}
									} else if (lookupStatus.getValue().equals("03")) {
										displayCEI(lookupProject.getValue(), lookupPhase.getValue());
									} else if (lookupStatus.getValue().equals("04")) {
										displayCEI(lookupProject.getValue(), lookupPhase.getValue());
									} else if (lookupStatus.getValue().equals("05")) {
										displayCEI(lookupProject.getValue(), lookupPhase.getValue());
									}
									
									KEYBOARD_MANAGER.focusNextComponent();
									//btnSave.setEnabled(true);
								}
							}
						});
					}
					{
						lblStatusDesc = new JLabel("[ ]");
						pnlNorth.add(lblStatusDesc);
						lblStatusDesc.setHorizontalAlignment(JLabel.LEFT);
						lblStatusDesc.setBounds(215, 110, 250, 22);
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
									
									if (modelUnitStatus.getValueAt(tblUnitStatus.getSelectedRow(),0).equals(true)) {
										modelUnitStatus.setValueAt(""+sdf.format(dteRefDate.getDate()),tblUnitStatus.getSelectedRow(),2);
									}
								}
								public void changedUpdate(DocumentEvent e) {}
								public void removeUpdate(DocumentEvent e) {}
							});
						}
						
						{
							scrollUnitStatus = new JScrollPane();
							pnlCenter.add(scrollUnitStatus, BorderLayout.CENTER);
							scrollUnitStatus.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollUnitStatus.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							
							modelUnitStatus = new model_unit_status_monitoring();
							modelUnitStatus.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderUnitStatus.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderUnitStatus.getModel()).addElement(modelUnitStatus.getRowCount());
									}
								}
							});
							
							tblUnitStatus = new _JTableMain(modelUnitStatus);
							scrollUnitStatus.setViewportView(tblUnitStatus);
							tblUnitStatus.addMouseListener(this);
							tblUnitStatus.hideColumns("PBL ID");
							tblUnitStatus.hideColumns("Entity ID");
							tblUnitStatus.hideColumns("Seq No");
							
							rowheaderUnitStatus = tblUnitStatus.getRowHeader();
							rowheaderUnitStatus.setModel(new DefaultListModel());
							scrollUnitStatus.setRowHeaderView(rowheaderUnitStatus);
							scrollUnitStatus.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							
						}
						
						}
				}
				
				{
					pnlDate= new JPanel();
					pnlDate.setLayout(null);
					pnlDate.setPreferredSize(new java.awt.Dimension(265, 80));
					{
						lblDate1 = new JLabel();
						pnlDate.add(lblDate1);
						lblDate1.setBounds(5, 15, 160, 20);
						lblDate1.setText("Trans. Date :");
					}
				}
				
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					//pnlSouth.setBorder(lineBorder);
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX	
					{
						lblBatch = new JLabel("");
						pnlSouth.add(lblBatch);
					}
					{
						btnDRF = new JButton("<html><center><font size = 2>Create Disbursement\n Request</html>");
						pnlSouth.add(btnDRF);
						btnDRF.setActionCommand("Create Disbursement Request");
						btnDRF.addActionListener(this);
						btnDRF.setEnabled(false);
					}
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
								int response = JOptionPane.showConfirmDialog(UnitStatusMonitoring.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
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
			lookupBlock.setValue("");
			lblCompany.setText("[ ]");
			lblProject.setText("[ ]");
			lblPhase.setText("[ ]");
			lblBlock.setText("[ ]");
			btnSave.setEnabled(false);
			modelUnitStatus.setRowCount(0);
			lblBatch.setText("");
			
		}
	} // refreshTO()

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblUnitStatus)) {
			int row = tblUnitStatus.convertRowIndexToModel(tblUnitStatus.getSelectedRow());
			Boolean isSelected2 = (Boolean) modelUnitStatus.getValueAt(row, 0);

			for(int x = 0; x < modelUnitStatus.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelUnitStatus.getValueAt(x, 0);
				if (isSelected) {
					lblBatch.setText(String.format("%s", generateBatchNo()));
					if (modelUnitStatus.getValueAt(x, 2) == null) {
						modelUnitStatus.setValueAt(sdf.format(Calendar.getInstance().getTime()), x, 2);
					}
					btnSave.setEnabled(true);
				}else {
					modelUnitStatus.setValueAt(null, x, 2);
					btnSave.setEnabled(false);
				}
			}
			
			if (isSelected2) {
				lblBatch.setText(String.format("%s", generateBatchNo()));
				if (e.getClickCount()==2) {
					if (tblUnitStatus.getSelectedColumn()== 2) {
						if (e.getClickCount() ==2) {
							dteRefDate.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
							dteRefDate.getCalendarButton().doClick();
						}

					}
				}
				btnSave.setEnabled(true);
			}
		}
		
		if (tblUnitStatus.columnAtPoint(e.getPoint())==2)   // Date
		{
			if (modelUnitStatus.getValueAt(tblUnitStatus.rowAtPoint(e.getPoint()),0).equals(true)) {
				tblUnitStatus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				tblUnitStatus.setToolTipText("Double Click to change Trans Date");
			}
			else {
				tblUnitStatus.setCursor(null);
				tblUnitStatus.setToolTipText(null);
			}
		}
		
	else {
			tblUnitStatus.setCursor(null);
			tblUnitStatus.setToolTipText(null);
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
			
			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are all your fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				modelUnitStatus.setRowCount(0);
			}
			
			if(lookupStatus.getText().equals("01")){
				for(int x =0; x < modelUnitStatus.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelUnitStatus.getValueAt(x, 0);
					String entity = (String) modelUnitStatus.getValueAt(x, 8);
					Integer seq = (Integer) modelUnitStatus.getValueAt(x, 9);
					String status_date = (String) modelUnitStatus.getValueAt(x, 2);
					String pbl = (String) modelUnitStatus.getValueAt(x, 7);
					String batch = (String) lblBatch.getText();
					String status_code = (String) lookupStatus.getText();
					if(isSelected){
						
						String strSQL = "SELECT sp_save_tag_unitstatus('"+entity+"', "+seq+", '"+status_code+"', '"+status_date+"'::timestamp, '"+lookupProject.getValue()+"', '"+pbl+"', '"+lookupCompany.getValue()+"', NULLIF('"+batch+"','null'),  '"+UserInfo.EmployeeCode +"');" ; 
						
						pgSelect db = new pgSelect();
						db.select(strSQL);
					}
				}
			}
			if(lookupStatus.getText().equals("02")){
				System.out.println("lookupStatus "+lookupStatus.getText());
				for(int x =0; x < modelUnitStatus.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelUnitStatus.getValueAt(x, 0);
					String unit = (String) modelUnitStatus.getValueAt(x, 1);
					String entity = (String) modelUnitStatus.getValueAt(x, 8);
					Integer seq = (Integer) modelUnitStatus.getValueAt(x, 9);
					String status_date = (String) modelUnitStatus.getValueAt(x, 2);
					String pbl = (String) modelUnitStatus.getValueAt(x, 7);
					String batch = (String) lblBatch.getText();
					String status_code = (String) lookupStatus.getText();
					if(isSelected){
						if(dateCompare(pbl, status_date)) {
							System.out.println("02 IF");
							JOptionPane.showMessageDialog(getContentPane(), "Date Inputed is Less than the Latest Date Accomplishment on "+unit, "Error", JOptionPane.ERROR_MESSAGE);
						}
						else {
							System.out.println("02 ELSE");
							String strSQL = "SELECT sp_save_tag_unitstatus_pmd('"+entity+"', "+seq+", '"+status_code+"', '"+status_date+"'::timestamp, '"+lookupProject.getValue()+"', '"+pbl+"', '"+lookupCompany.getValue()+"', NULLIF('"+batch+"','null'),  '"+UserInfo.EmployeeCode +"');" ; 
							
							pgSelect db = new pgSelect();
						db.select(strSQL);
						}
					}
				}
			}

				btnSave.setEnabled(false);
				modelUnitStatus.setRowCount(0);
				lblBatch.setText("");
				
		}
		
/*		if(lookupStatus.equals("01") && UserInfo.EmployeeCode.equals("900933")){
			displayToCMD(lookupProject.getValue(), lookupPhase.getValue());
			btnSave.setEnabled(true);
			tblUnitStatus.setEditable(true);
		}
		else if(lookupStatus.equals("01") && !UserInfo.EmployeeCode.equals("900933")){
			displayToCMD(lookupProject.getValue(), lookupPhase.getValue());
			btnSave.setEnabled(false);
			tblUnitStatus.setEditable(false);
		}
		if(lookupStatus.equals("02") && UserInfo.EmployeeCode.equals("900933")){
			displayToPMD(lookupProject.getValue(), lookupPhase.getValue());
			btnSave.setEnabled(true);
			tblUnitStatus.setEditable(true);
		}
		else if(lookupStatus.equals("01") && !UserInfo.EmployeeCode.equals("900933")){
			displayToPMD(lookupProject.getValue(), lookupPhase.getValue());
			btnSave.setEnabled(false);
			tblUnitStatus.setEditable(false);
		}*/
	}
	
	private void displayToCMD(String proj_id, String phase) {

		FncTables.clearTable(modelUnitStatus);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		/*String sql = 			
				"select false, trim(b.description), null, null, null, null, null, a.pbl_id, a.entity_id, a.seq_no\n" +
		
				"from rf_sold_unit a\n" +
					
				"left join mf_unit_info b \n" +
				"on a.pbl_id = b.pbl_id and a.projcode = b.proj_id\n" +
				"left join co_ntp_detail c \n" +
				"on a.pbl_id = c.pbl_id and c.status_id = 'A'\n" +
		
				"where (CASE WHEN ('"+lookupProject.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupProject.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.projcode ~* '"+lookupProject.getValue()+"' END)" +
				"and (CASE WHEN ('"+lookupPhase.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupPhase.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE b.phase ~* '"+lookupPhase.getValue()+"' END)" +
				"and (CASE WHEN ('"+lookupBlock.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupBlock.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE b.block ~* '"+lookupBlock.getValue()+"' END)" +
				"and trim(a.pbl_id) not in (select trim(pbl_id) as pbl_id from rf_unit_status where unitstatus_id = '01' and status_id='A')" +
				//"and a.unit_id not in ('')" +
				//"and a.currentstatus not in ('01','02')" +
				"and a.status_id='A'\n" +
				"and c.work_percent = 95\n" +
				"group by b.description, b.date_created, a.pbl_id, a.entity_id, a.seq_no, b.phase, b.block, b.lot\n" +
				"order by  b.block::int, b.lot::int   \n";*/
		
		String SQL = "SELECT * FROM view_qualified_usm_to_contractor_to_cmd_v2(NULLIF('"+lookupProject.getValue()+"', 'null'), NULLIF('"+lookupPhase.getValue()+"', 'null'), NULLIF('"+lookupBlock.getValue()+"', 'null'))";

		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("Display SQL:", SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelUnitStatus.addRow(db.getResult()[x]);
				listModel.addElement(modelUnitStatus.getRowCount());				
			}	
		}		

			tblUnitStatus.packAll();
			tblUnitStatus.setEditable(true);
			btnSave.setEnabled(false);
	}
		
	private void displayToPMD(String proj_id, String phase) {

		FncTables.clearTable(modelUnitStatus);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		/*String sql = 			
				"select false, trim(b.description), null, null, null, null, null, a.pbl_id, a.entity_id, a.seq_no\n" +
		
				"from rf_sold_unit a\n" +
					
				"left join mf_unit_info b \n" +
				"on a.pbl_id = b.pbl_id and a.projcode = b.proj_id\n" +
				"left join co_ntp_detail c \n" +
				"on a.pbl_id = c.pbl_id and c.status_id = 'A'\n" +
		
				"where (CASE WHEN ('"+lookupProject.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupProject.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.projcode ~* '"+lookupProject.getValue()+"' END)" +
				"and (CASE WHEN ('"+lookupPhase.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupPhase.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE b.phase ~* '"+lookupPhase.getValue()+"' END)" +
				"and (CASE WHEN ('"+lookupBlock.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupBlock.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE b.block ~* '"+lookupBlock.getValue()+"' END)" +
				"and not exists (select trim(x.pbl_id) as pbl_id from rf_unit_status x where x.unitstatus_id = '02' and x.status_id = 'A' and x.pbl_id = a.pbl_id) " +
				"and trim(a.pbl_id) in (select trim(pbl_id) as pbl_id from rf_unit_status where unitstatus_id = '01' and status_id='A')" +
				//"and a.unit_id not in ('')" +
				//"and a.currentstatus not in ('01','02')" +
				"and a.status_id = 'A'\n" +
				"and c.work_percent >= 95\n" +
				"group by b.description, b.date_created, a.pbl_id, a.entity_id, a.seq_no, b.phase, b.block, b.lot\n" +
				"order by b.phase, b.block::int, b.lot::int  \n";*/
		
			String SQL = "SELECT * FROM view_qualified_usm_to_cmd_to_pmd(NULLIF('"+lookupProject.getValue()+"', 'null'), NULLIF('"+lookupPhase.getValue()+"', 'null'), NULLIF('"+lookupBlock.getValue()+"', 'null'))";

			pgSelect db = new pgSelect();
			db.select(SQL);
			FncSystem.out("Display SQL", SQL);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					// Adding of row in table
					modelUnitStatus.addRow(db.getResult()[x]);
					listModel.addElement(modelUnitStatus.getRowCount());				
				}	
			}		

			tblUnitStatus.packAll();
			tblUnitStatus.setEditable(true);
			btnSave.setEnabled(false);
		}
	private void displayCEI(String proj_id, String phase) {

		FncTables.clearTable(modelUnitStatus);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"select false, trim(b.description), null, null, null, null, null, a.pbl_id, a.entity_id, a.seq_no\n" +

				"from rf_sold_unit a\n" +
					
				"left join mf_unit_info b \n" +
				"on a.pbl_id = b.pbl_id and a.projcode = b.proj_id\n" +
				"left join co_ntp_detail c \n" +
				"on a.pbl_id = c.pbl_id and c.status_id = 'A'\n" +

				"where (CASE WHEN ('"+lookupProject.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupProject.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.projcode ~* '"+lookupProject.getValue()+"' END)" +
				"and (CASE WHEN ('"+lookupPhase.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupPhase.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE b.phase ~* '"+lookupPhase.getValue()+"' END)" +
				"and (CASE WHEN ('"+lookupBlock.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupBlock.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE b.block ~* '"+lookupBlock.getValue()+"' END)" +
				"and trim(a.pbl_id) not in (select trim(pbl_id) as pbl_id from rf_unit_status where unitstatus_id = '01' and status_id='A')" +
				//"and a.unit_id not in ('')" +
				"and a.currentstatus not in ('02')" +
				"and a.status_id='A'\n" +
				//"and c.work_percent = 100\n" +
				"group by b.description, b.date_created, a.pbl_id, a.entity_id, a.seq_no, b.phase, b.block, b.lot \n" +
				"order by b.phase::int, b.block::int, b.lot::int  \n";

			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					// Adding of row in table
					modelUnitStatus.addRow(db.getResult()[x]);
					listModel.addElement(modelUnitStatus.getRowCount());				
				}	
			}		

			tblUnitStatus.packAll();
			tblUnitStatus.setEditable(true);
			btnSave.setEnabled(false);
		}
	
	private static String[] getCriteria() {
		String SQL = "SELECT status_desc\n" + 
				"FROM mf_unit_status\n" + 
				"WHERE status_id = 'A'\n" + 
				"ORDER BY status_code::int ASC;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	private static String generateBatchNo() {
		String strSQL = "SELECT to_char(COALESCE(MAX(batch_no::INT), 0) + 1, 'FM0000000000') FROM rf_unit_status WHERE status_id = 'A' and batch_no is not null and batch_no != '';;";

		//FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	private static String SQL_CRITERIA() {
		return "SELECT status_code as \"Code\", status_desc as \"Description\" FROM mf_unit_status WHERE status_id = 'A' ORDER BY status_code::int ASC";		
	}
	private static String SQL_REQUESTTYPE() {
		return "SELECT rplf_type_id as \"Type ID\", trim(rplf_type_desc) as \"Description\"  \n" +
						"FROM mf_rplf_type where status_id = 'A' " +
						"ORDER BY rplf_type_id ";		
	}
	private static String SQL_AVAILER() {
		return "SELECT trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\", entity_kind as \"Kind\", vat_registered as \"VAT\"  \n" +
						"FROM (select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in \r\n" + 
						"(select entity_id from rf_entity_assigned_type " +
						" )) a \n" +
						"ORDER BY a.entity_name  ";		
	}
	private static String SQL_AVAILERTYPE(String entity_id) {
		return "SELECT a.entity_type_id, trim(b.entity_type_desc), c.wtax_id, c.wtax_rate \n" + 
						"FROM (select * from rf_entity_assigned_type where trim(entity_id) =  '"+entity_id+"' ) a \r\n" + 
						"LEFT JOIN mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n" + 		
						"LEFT JOIN rf_withholding_tax c on b.wtax_id = c.wtax_id"  ;
	}
	private boolean dateCompare(String pbl_id,String date) {
				String strSQL = "select max(as_of_date::Date) > '"+date+"'::date from co_ntp_accomplishment where pbl_id = '"+pbl_id+"' and status_id ='A' and percent_accomplish = 95" ; 
				
				pgSelect db = new pgSelect();
				db.select(strSQL);
				return (boolean) db.getResult()[0][0]; 
		}
		
	
}
