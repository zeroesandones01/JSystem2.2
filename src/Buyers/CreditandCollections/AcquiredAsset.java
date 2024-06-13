package Buyers.CreditandCollections;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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
//import Projects.PropertyManagement.TagCancelledByPAGIBIG;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_AcquiredAsset;

public class AcquiredAsset extends _JInternalFrame implements _GUI, ActionListener,MouseListener {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6226331060225981427L;
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
//	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlCenter1;
	JPanel pnlSouth;
	
	JTextField txtProject;
	JTextField txtCompany;
	JTextField txtPhase;
	JTextField txtBlock;
	JTextField txtReader;
	JTextField txtBatch;
	JTextField txtrplf;
	JXTextField txtSearch;
	
	JLabel lblBatch;
	
	JTabbedPane tabCenter;
	
	_JLookup lookupProject;
	_JLookup lookupCompany;
	_JLookup lookupPhase;
	_JLookup lookupBlock;
	_JLookup lookupBatch;
	_JLookup lookupReader;
	_JLookup lookupBatch1;
	_JLookup lookupRplf;
	
	_JDateChooser dateFrom;
	_JDateChooser dateTo;
	
	JComboBox cmbClass;
	JCheckBox chkboxListSummary;
	JComboBox cmbTurn;
	
	JButton btnSave;
	JButton btnCancel;
	
	model_AcquiredAsset AcquiedAsset;
	JScrollPane scrollAcquiedAsset;
	_JTableMain tblAcquiedAsset;
	JList rowheaderAcquiedAsset;
	
	JDateChooser dateStatus;
	_JDateChooser dteRefDate;
	
	String co_id = "";
	String company = "";
	String company_logo = "";
	
	String batch = "";
	String batch1 = "";
	static String SQL = "";
	
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
	
	static String title = "Acquired Asset";
	Dimension frameSize = new Dimension(700, 500);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public AcquiredAsset() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AcquiredAsset(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public AcquiredAsset(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
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
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setPreferredSize(new Dimension(700, 200));// XXX
				{
					pnlWest = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlWest, BorderLayout.WEST);
					pnlWest.setPreferredSize(new Dimension(680, 200));// XXX
					{
						JPanel pnlWest1 = new JPanel(new BorderLayout(5, 5));
						pnlWest.add(pnlWest1, BorderLayout.NORTH);
						pnlWest1.setPreferredSize(new Dimension(440, 120));// XXX
						{
							JPanel pnlLabel = new JPanel(new GridLayout(4, 0, 5, 5));
							pnlWest1.add(pnlLabel, BorderLayout.WEST);
							{
								JLabel lblCompany = new JLabel("Company:");
								pnlLabel.add(lblCompany);
								lblCompany.setHorizontalAlignment(JLabel.RIGHT);
								
								JLabel lblProject = new JLabel("Project:");
								pnlLabel.add(lblProject);
								lblProject.setHorizontalAlignment(JLabel.RIGHT);
								
								JLabel lblPhase = new JLabel("Phase:");
								pnlLabel.add(lblPhase);
								lblPhase.setHorizontalAlignment(JLabel.RIGHT);
								
								JLabel lblBlock = new JLabel("Block:");
								pnlLabel.add(lblBlock);
								lblBlock.setHorizontalAlignment(JLabel.RIGHT);
								

							}
						}
						{
							JPanel pnlText = new JPanel(new GridLayout(4, 0, 5, 5));
							pnlWest1.add(pnlText, BorderLayout.CENTER);
							{
								JPanel pnlLookup1 = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlLookup1);
								{
									lookupCompany = new _JLookup(null, "Company");
									pnlLookup1.add(lookupCompany, BorderLayout.WEST);
									lookupCompany.setLookupSQL(SQL_COMPANY());
									lookupCompany.setReturnColumn(0);
									lookupCompany.setPreferredSize(new Dimension(70, 20));
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
									{
									
										
										{
											txtCompany = new JTextField("Select Company");
											pnlLookup1.add(txtCompany,BorderLayout.CENTER);
											txtCompany.setHorizontalAlignment(JLabel.CENTER);
											txtCompany.setEditable(false);
											txtCompany.setPreferredSize(new Dimension(100, 20));
										}
										
										
									}
								}
							}
							{
								JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlLookup);
								{
									lookupProject = new _JLookup(null, "Project", "Please select Company.");
									pnlLookup.add(lookupProject, BorderLayout.WEST);
									lookupProject.setLookupSQL(SQL_PROJECT());
									lookupProject.setReturnColumn(0);
									lookupProject.setPreferredSize(new Dimension(70, 20));
									lookupProject.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												txtProject.setText(String.format("%s", (String) data[1]));
												lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
												
												displayTTOAccounts((String) data[0], lookupPhase.getText(), lookupBlock.getText() );
												btnSave.setEnabled(true);

												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
									{
										txtProject = new JTextField("All Projects");
										pnlLookup.add(txtProject,BorderLayout.CENTER);
										txtProject.setHorizontalAlignment(JLabel.CENTER);
										txtProject.setPreferredSize(new Dimension(100, 20));
										txtProject.setEditable(false);
									}
								}
							}
							{
								JPanel pnlLookup4 = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlLookup4);
								{
									lookupPhase = new _JLookup(null, "Phase", "Please select project.");
									pnlLookup4.add(lookupPhase, BorderLayout.WEST);
									lookupPhase.setReturnColumn(0);
									lookupPhase.setPreferredSize(new Dimension(70, 20));
									lookupPhase.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												txtPhase.setText(String.format("%s", (String) data[1]));
												lookupBlock.setLookupSQL(BLOCK((String) data[0]));
												
												displayTTOAccounts(lookupProject.getText(), (String) data[0], lookupBlock.getText() );

												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
									{
										txtPhase = new JTextField("All Phases");
										pnlLookup4.add(txtPhase,BorderLayout.CENTER);
										txtPhase.setHorizontalAlignment(JLabel.CENTER);
										txtPhase.setPreferredSize(new Dimension(100, 20));
										txtPhase.setEditable(false);
									}
								}
							}
							{
								JPanel pnlLookup5 = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlLookup5);
								{
									lookupBlock = new _JLookup(null, "Block", "Please select phase.");
									pnlLookup5.add(lookupBlock, BorderLayout.WEST);
									lookupBlock.setReturnColumn(0);
									lookupBlock.setPreferredSize(new Dimension(70, 20));
									lookupBlock.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												txtBlock.setText(String.format("%s", (String) data[1]));
												
												displayTTOAccounts(lookupProject.getText(), lookupPhase.getText(), (String) data[0] );

												KEYBOARD_MANAGER.focusNextComponent();
												
												
											}
										
										}
									});
									{
										txtBlock = new JTextField("All Block");
										pnlLookup5.add(txtBlock,BorderLayout.CENTER);
										txtBlock.setHorizontalAlignment(JLabel.CENTER);
										txtBlock.setPreferredSize(new Dimension(100, 20));
										txtBlock.setEditable(false);
									}
								}
							}
							
						}
					}
					{
						JPanel pnlSearch = new JPanel(new BorderLayout(5, 5));
						pnlWest.add(pnlSearch);
						pnlSearch.setBorder(JTBorderFactory.createTitleBorder("Search Buyer's Name"));
						{
							JPanel pnlUploading = new JPanel(new GridLayout(1, 1, 10, 0));
							pnlSearch.add(pnlUploading, BorderLayout.WEST);
							{
								txtSearch = new JXTextField();
								pnlUploading.add(txtSearch);
								txtSearch.setPreferredSize(new java.awt.Dimension(665, 0));
								txtSearch.setHorizontalAlignment(JTextField.CENTER);	
								txtSearch.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {	
										checkAllClientList();
									}				 
								});	
							}
							
						}
					}
				}
			}
			{
				pnlCenter = new JPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(new BorderLayout());
				{
					dteRefDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenter.add(dteRefDate);
					dteRefDate.setDate(null);
					((JTextFieldDateEditor)dteRefDate.getDateEditor()).setEditable(false);
					dteRefDate.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					((JTextFieldDateEditor)dteRefDate.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
						public void insertUpdate(DocumentEvent evt) {
							SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
							
							if (AcquiedAsset.getValueAt(tblAcquiedAsset.getSelectedRow(),0).equals(true)) {
								AcquiedAsset.setValueAt(""+sdf.format(dteRefDate.getDate()),tblAcquiedAsset.getSelectedRow(),1);
							}
						}
						public void changedUpdate(DocumentEvent e) {}
						public void removeUpdate(DocumentEvent e) {}
					});
				}
				{
					scrollAcquiedAsset = new JScrollPane();
					pnlCenter.add(scrollAcquiedAsset, BorderLayout.CENTER);
					scrollAcquiedAsset.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollAcquiedAsset.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							
					AcquiedAsset = new model_AcquiredAsset();
					AcquiedAsset.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == TableModelEvent.DELETE){
								rowheaderAcquiedAsset.setModel(new DefaultListModel());
							}
							if(e.getType() == TableModelEvent.INSERT){
								((DefaultListModel)rowheaderAcquiedAsset.getModel()).addElement(AcquiedAsset.getRowCount());
							}
						}
					});
					
					tblAcquiedAsset = new _JTableMain(AcquiedAsset);
					scrollAcquiedAsset.setViewportView(tblAcquiedAsset);
					tblAcquiedAsset.addMouseListener(this);
					tblAcquiedAsset.hideColumns("PBL ID");
					tblAcquiedAsset.hideColumns("Entity ID");
					tblAcquiedAsset.hideColumns("Seq No");
					
					rowheaderAcquiedAsset = tblAcquiedAsset.getRowHeader();
					rowheaderAcquiedAsset.setModel(new DefaultListModel());
					scrollAcquiedAsset.setRowHeaderView(rowheaderAcquiedAsset);
					scrollAcquiedAsset.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				pnlSouth.setPreferredSize(new Dimension(700, 25));// XXX
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnSave = new JButton("Save");		//**EDITED BY JED VICEDO 2019-07-15 : EDIT DISPLAY OF CLIENT, INCLUDE OTHER LOTS**//
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int response = JOptionPane.showConfirmDialog(AcquiredAsset.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
									"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.YES_OPTION) {
								lookupProject.setText(" ");
								refreshTO();
							}
						}
					});
				}
			}
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblAcquiedAsset)) {
			int row = tblAcquiedAsset.convertRowIndexToModel(tblAcquiedAsset.getSelectedRow());
			Boolean isSelected2 = (Boolean) AcquiedAsset.getValueAt(row, 0);

			for(int x = 0; x < AcquiedAsset.getRowCount(); x++){
					Boolean isSelected = (Boolean) AcquiedAsset.getValueAt(x, 0);
				if (isSelected) {
					//lblBatch.setText(String.format("%s", generateBatchNo()));
					if (AcquiedAsset.getValueAt(x, 1) == null) {
						AcquiedAsset.setValueAt(sdf.format(Calendar.getInstance().getTime()), x, 1);
					}
					//btnSave.setEnabled(true);
				}else {
					AcquiedAsset.setValueAt(null, x, 1);
					//btnSave.setEnabled(false);
				}
			}
			
			if (isSelected2) {
				//lblBatch.setText(String.format("%s", generateBatchNo()));
				if (e.getClickCount()==2) {
					if (tblAcquiedAsset.getSelectedColumn()== 1) {
						if (e.getClickCount() ==2) {
							dteRefDate.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
							dteRefDate.getCalendarButton().doClick();
						}

					}
				}
				//btnSave.setEnabled(true);
			}
		}
		
		if (tblAcquiedAsset.columnAtPoint(e.getPoint())==1)   // Date
		{
			if (AcquiedAsset.getValueAt(tblAcquiedAsset.rowAtPoint(e.getPoint()),0).equals(true)) {
				tblAcquiedAsset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				tblAcquiedAsset.setToolTipText("Double Click to change Trans Date");
			}
			else {
				tblAcquiedAsset.setCursor(null);
				tblAcquiedAsset.setToolTipText(null);
			}
		}
		
	else {
			tblAcquiedAsset.setCursor(null);
			tblAcquiedAsset.setToolTipText(null);
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
	private void displayTTOAccounts(String proj, String phase, String block) {


		FncTables.clearTable(AcquiedAsset);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		
		String sql = "select * from view_acquired_asset('"+proj+"', '"+phase+"', '"+block+"')";
				
				
				
		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("DIsplay SQL ", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				AcquiedAsset.addRow(db.getResult()[x]);
				listModel.addElement(AcquiedAsset.getRowCount());
			}	
		}		
		tblAcquiedAsset.packAll();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Save")){
			
			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				AcquiedAsset.setRowCount(0);
			}

			for(int x =0; x < AcquiedAsset.getRowCount(); x++){
				Boolean isSelected = (Boolean) AcquiedAsset.getValueAt(x, 0);
				String entity = (String) AcquiedAsset.getValueAt(x, 6);
				Integer seq = (Integer) AcquiedAsset.getValueAt(x, 7);
				String to_date = (String) AcquiedAsset.getValueAt(x, 1);
				String pbl = (String) AcquiedAsset.getValueAt(x, 5);
				if(isSelected){
					
					String strSQL = "";
				
					strSQL = "SELECT sp_save_tag_acquired_asset('"+entity+"', "+seq+", '"+to_date+"'::timestamp, '"+lookupProject.getValue()+"', '"+pbl+"', '"+lookupCompany.getValue()+"',  '"+UserInfo.EmployeeCode +"');" ; 

					pgSelect db = new pgSelect();
					db.select(strSQL);
					
					FncSystem.out("Display SQL: ", strSQL);
					
				}
			}
			
				//btnSave.setEnabled(false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
			
			AcquiedAsset.setRowCount(0);
			btnSave.setEnabled(false);
			txtSearch.setText("");
				
		}
	}

	private void refreshTO() {

		if (lookupProject.getText().equals(" ")) {
			lookupCompany.setText(null);
			txtCompany.setText("Select Company");
			txtProject.setText("All Projects");
			lookupPhase.setText(null);
			lookupBlock.setText(null);
			txtPhase.setText("All Phases");
			btnSave.setEnabled(false);
			AcquiedAsset.clear();
			txtSearch.setText("");
		
			
		}
	} // refreshTO()
	public static String BLOCK(String phase) {//XXX Block
		String SQL = "select block as \"Block\", 'Block ' || block as \"Description\" from mf_unit_info where phase = '"+ phase +"' group by block order by block::int;";
		return SQL;
	}
	
	private void checkAllClientList(){//

		int rw = tblAcquiedAsset.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";
			
			try { name	= tblAcquiedAsset.getValueAt(x,2).toString().toUpperCase();}
			 catch (NullPointerException e) { name	= ""; }
			
			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			//Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			//Boolean	end			= name.endsWith(acct_name);

			if (start==true) {
				tblAcquiedAsset.setRowSelectionInterval(x, x); 
				tblAcquiedAsset.changeSelection(x, 2, false, false);				
				break;			
			}
			else {
				tblAcquiedAsset.setRowSelectionInterval(0, 0); 
				tblAcquiedAsset.changeSelection(0, 2, false, false);					
			}

			x++;

		}		
	}
}
