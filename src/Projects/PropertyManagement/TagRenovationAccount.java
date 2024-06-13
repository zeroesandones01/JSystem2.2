package Projects.PropertyManagement;

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
import java.util.ArrayList;
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
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelTagForRenovation;
import tablemodel.model_TTOAccounts;

public class TagRenovationAccount extends _JInternalFrame implements _GUI, ActionListener,MouseListener {

	private static final long serialVersionUID = -2801000532514662030L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
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

	JButton btnSave;
	JButton btnCancel;

	modelTagForRenovation modelForReno;
	JScrollPane scrollTTOAccounts;
	_JTableMain tblTagForRenovation;
	JList rowheaderTTOAccounts;

	JDateChooser dateStatus;
	_JDateChooser dteRefDate;

	String co_id = "";
	String company = "";
	String company_logo = "";

	String batch = "";
	String batch1 = "";
	static String SQL = "";

	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

	static String title = "Tag for Renovation Accounts";
	Dimension frameSize = new Dimension(700, 500);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public TagRenovationAccount() {
		super(title, false, true, false, true);
		initGUI();
	}

	public TagRenovationAccount(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public TagRenovationAccount(String title, boolean resizable,
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
			pnlMain.setLayout(new BorderLayout(10, 10));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				{
					JPanel pnlLabel = new JPanel(new GridLayout(3, 0, 5, 5));
					pnlNorth.add(pnlLabel, BorderLayout.WEST);
					{
						JLabel lblCompany = new JLabel("Company:");
						pnlLabel.add(lblCompany);
						//lblCompany.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						JLabel lblProject = new JLabel("Project:");
						pnlLabel.add(lblProject);
						//lblProject.setHorizontalAlignment(JLabel.RIGHT);
					}
					{
						JLabel lblPhase = new JLabel("Phase:");
						pnlLabel.add(lblPhase);
						//lblPhase.setHorizontalAlignment(JLabel.RIGHT);
					}
				}
				{
					JPanel pnlComponents = new JPanel(new GridLayout(3, 0, 5, 5));
					pnlNorth.add(pnlComponents);
					{
						JPanel pnlLookup1 = new JPanel(new BorderLayout(3, 3));
						pnlComponents.add(pnlLookup1);
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
						}
						{
							txtCompany = new JTextField("Select Company");
							pnlLookup1.add(txtCompany,BorderLayout.CENTER);
							txtCompany.setHorizontalAlignment(JLabel.LEFT);
							txtCompany.setEditable(false);
							txtCompany.setPreferredSize(new Dimension(100, 20));
						}
					}
					{
						JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
						pnlComponents.add(pnlLookup);
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
										//String proj_id = (String) data[0];
										txtProject.setText(String.format("%s", (String) data[1]));
										lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));

										//displayQualifiedUnderRenovation(lookupCompany.getValue(),proj_id, null);
										generateQualified();
										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							{
								txtProject = new JTextField("All Projects");
								pnlLookup.add(txtProject,BorderLayout.CENTER);
								txtProject.setHorizontalAlignment(JLabel.LEFT);
								//txtProject.setPreferredSize(new Dimension(100, 20));
								txtProject.setEditable(false);
							}
						}
					}
					{
						JPanel pnlLookup4 = new JPanel(new BorderLayout(3, 3));
						pnlComponents.add(pnlLookup4);
						{
							lookupPhase = new _JLookup(null, "Phase", "Please select project.");
							pnlLookup4.add(lookupPhase, BorderLayout.WEST);
							lookupPhase.setReturnColumn(0);
							lookupPhase.setPreferredSize(new Dimension(70, 20));
							lookupPhase.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										//String phase_code = (String) data[0];
										txtPhase.setText(String.format("%s", (String) data[1]));

										//displayQualifiedUnderRenovation(lookupCompany.getValue(),lookupProject.getValue(), phase_code);
										generateQualified();
										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							{
								txtPhase = new JTextField();
								pnlLookup4.add(txtPhase,BorderLayout.CENTER);
								txtPhase.setHorizontalAlignment(JLabel.LEFT);
								txtPhase.setPreferredSize(new Dimension(100, 20));
								txtPhase.setEditable(false);
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

							if (modelForReno.getValueAt(tblTagForRenovation.getSelectedRow(),0).equals(true)) {
								modelForReno.setValueAt(""+sdf.format(dteRefDate.getDate()),tblTagForRenovation.getSelectedRow(),1);
							}
						}
						public void changedUpdate(DocumentEvent e) {}
						public void removeUpdate(DocumentEvent e) {}
					});
				}
			}
			{
				scrollTTOAccounts = new JScrollPane();
				pnlCenter.add(scrollTTOAccounts, BorderLayout.CENTER);
				scrollTTOAccounts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollTTOAccounts.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				{
					modelForReno = new modelTagForRenovation();
					modelForReno.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == TableModelEvent.DELETE){
								rowheaderTTOAccounts.setModel(new DefaultListModel());
							}
							if(e.getType() == TableModelEvent.INSERT){
								((DefaultListModel)rowheaderTTOAccounts.getModel()).addElement(modelForReno.getRowCount());
							}
						}
					});

					tblTagForRenovation = new _JTableMain(modelForReno);
					scrollTTOAccounts.setViewportView(tblTagForRenovation);
					tblTagForRenovation.setSortable(false);
					tblTagForRenovation.addMouseListener(this);
					tblTagForRenovation.hideColumns("PBL ID");
					tblTagForRenovation.hideColumns("Entity ID");
					tblTagForRenovation.hideColumns("Proj. ID");
					tblTagForRenovation.hideColumns("Seq No");
					
				}
				{
					rowheaderTTOAccounts = tblTagForRenovation.getRowHeader();
					rowheaderTTOAccounts.setModel(new DefaultListModel());
					scrollTTOAccounts.setRowHeaderView(rowheaderTTOAccounts);
					scrollTTOAccounts.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
							int response = JOptionPane.showConfirmDialog(TagRenovationAccount.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
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
		if (e.getSource().equals(tblTagForRenovation)) {
			int row = tblTagForRenovation.convertRowIndexToModel(tblTagForRenovation.getSelectedRow());
			Boolean isSelected2 = (Boolean) modelForReno.getValueAt(row, 0);

			for(int x = 0; x < modelForReno.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelForReno.getValueAt(x, 0);
				if (isSelected) {
					//lblBatch.setText(String.format("%s", generateBatchNo()));
					if (modelForReno.getValueAt(x, 1) == null) {
						modelForReno.setValueAt(sdf.format(Calendar.getInstance().getTime()), x, 1);
					}
					//btnSave.setEnabled(true);
				}else {
					modelForReno.setValueAt(null, x, 1);
					//btnSave.setEnabled(false);
				}
			}

			if (isSelected2) {
				//lblBatch.setText(String.format("%s", generateBatchNo()));
				if (e.getClickCount()==2) {
					if (tblTagForRenovation.getSelectedColumn()== 1) {
						if (e.getClickCount() ==2) {
							dteRefDate.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
							dteRefDate.getCalendarButton().doClick();
						}

					}
				}
				//btnSave.setEnabled(true);
			}
		}

		if (tblTagForRenovation.columnAtPoint(e.getPoint())==1)   // Date
		{
			if (modelForReno.getValueAt(tblTagForRenovation.rowAtPoint(e.getPoint()),0).equals(true)) {
				tblTagForRenovation.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				tblTagForRenovation.setToolTipText("Double Click to change Trans Date");
			}
			else {
				tblTagForRenovation.setCursor(null);
				tblTagForRenovation.setToolTipText(null);
			}
		}

		else {
			tblTagForRenovation.setCursor(null);
			tblTagForRenovation.setToolTipText(null);
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

	private void generateQualified() {
		FncGlobal.startProgress("Generating Qualified Accounts");

		new Thread(new Runnable() {
			public void run() {
				displayQualifiedUnderRenovation(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue());
				FncGlobal.stopProgress();
			}
		}).start();
	}

	private void displayQualifiedUnderRenovation(String co_id, String proj_id, String phase) {
		FncTables.clearTable(modelForReno);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_qualified_for_renovation(NULLIF('"+co_id+"', 'null'),NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'))";
		db.select(SQL);
		FncSystem.out("Display SQL For Reno", SQL);

		if(db.isNotNull()) {
			for(int x = 0; x<db.getRowCount(); x++) {
				modelForReno.addRow(db.getResult()[x]);
				listModel.addElement(modelForReno.getRowCount());
			}
		}
		scrollTTOAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(String.valueOf(tblTagForRenovation.getRowCount())));
		tblTagForRenovation.packAll();
	}

	private void saveAccountsUnderRenovation(DefaultTableModel model) {

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<String> listRenovationDate = new ArrayList<String>();

		for(int x=0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);

			if(isSelected){
				String renovation_date = (String) model.getValueAt(x, 1);
				String entity_id = (String) model.getValueAt(x, 5);
				String proj_id = (String) model.getValueAt(x, 6);
				String pbl_id = (String) model.getValueAt(x, 7);
				Integer seq_no = (Integer) model.getValueAt(x, 8);

				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listRenovationDate.add(String.format("'%s'", renovation_date));

			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		String renovation_date = listRenovationDate.toString().replaceAll("\\[|\\]", "");

		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_for_renovation(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], ARRAY["+renovation_date+"]::TIMESTAMP WITHOUT TIME ZONE[],'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Save")){

			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				saveAccountsUnderRenovation(modelForReno);
				modelForReno.clear();
				scrollTTOAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
				displayQualifiedUnderRenovation(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue());
				lookupCompany.setValue(null);
				txtCompany.setText("");
				lookupProject.setValue(null);
				txtProject.setText("");
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	private void refreshTO() {

		for (int i = 0; i < modelForReno.getRowCount(); i++) {
			Boolean SelectedItem = (Boolean) modelForReno.getValueAt(i, 0);
			//int row = tblPCost.convertRowIndexToModel(tblPCost.getSelectedRow());

			if(SelectedItem){
				modelForReno.setValueAt(false, i, 0);
			}
		}

		for (int i = 0; i < modelForReno.getRowCount(); i++) {
			Boolean SelectedItem = (Boolean) modelForReno.getValueAt(i, 0);
			//int row = tblPCost.convertRowIndexToModel(tblPCost.getSelectedRow());

			if(SelectedItem){
				modelForReno.setValueAt(false, i, 0);
			}
		}

		if (lookupProject.getText().equals(" ")) {
			lookupCompany.setText(null);
			txtCompany.setText("Select Company");
			txtProject.setText("All Projects");
			lookupPhase.setText(null);
			txtPhase.setText("All Phases");

		}
		modelForReno.clear();
		rowheaderTTOAccounts.setModel(new DefaultListModel());
		scrollTTOAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
	} // refreshTO()

	private void checkAllClientList(){//

		int rw = tblTagForRenovation.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";

			try { name	= tblTagForRenovation.getValueAt(x,2).toString().toUpperCase();}
			catch (NullPointerException e) { name	= ""; }

			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			//Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			//Boolean	end			= name.endsWith(acct_name);

			if (start==true) {
				tblTagForRenovation.setRowSelectionInterval(x, x); 
				tblTagForRenovation.changeSelection(x, 2, false, false);				
				break;			
			}
			else {
				tblTagForRenovation.setRowSelectionInterval(0, 0); 
				tblTagForRenovation.changeSelection(0, 2, false, false);					
			}

			x++;

		}		
	}
}