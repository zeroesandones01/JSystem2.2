package Projects.SalesandMarketing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import tablemodel.modelPRC;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class prcDocMon extends _JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 2230338357156454667L;
	static String title = "PRC - HLURB Monitoring";
	Dimension frameSize = new Dimension(700, 600);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlack = BorderFactory.createLineBorder(Color.BLACK);
	
	private JButton btnAss;
	private JButton btnMod;
	private JButton btnSave;
	private JButton btnPrev;
	private JButton btnIn;
	private JButton btnOut;
	private JButton btnDeactivate;
	private JButton btnActivate;
	
	private static Boolean blnEdit = false;
	
	private JCheckBox chkAll;
	private JCheckBox chkNotR;
	private JCheckBox chkNotS;
	private JCheckBox chkActive;
	
	private JXPanel pnlTab;
	
	private modelPRC modelPRC;
	
	public static JList rowPRC;
	
	private static _JLookup txtOverID;
	
	private static JTextField txtOverName;
	private static JTextField txtDeptID;
	private static JTextField txtDept;
	
	private static JTextField txtLast;
	private static JTextField txtFirst;
	private static JTextField txtMiddle;
	
	private static JLabel lblLast;
	private static JLabel lblFirst;
	private static JLabel lblMiddle;
	
	public static _JTableMain tblPRC;
	
	private JScrollPane scrTab;
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	static String strRec = "";
	
	public prcDocMon() {
		super(title, true, true, false, false);
		InitGUI();
	}

	public prcDocMon(String title) {
		super(title);
		InitGUI();
	}

	public prcDocMon(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		InitGUI();
	}

	private void InitGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panStart = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panStart, BorderLayout.PAGE_START);
			panStart.setPreferredSize(new Dimension(0, 163));
			{
				JXPanel panParam0 = new JXPanel(new GridLayout(2, 4, 5, 5));
				panStart.add(panParam0, BorderLayout.PAGE_START);
				panParam0.setPreferredSize(new Dimension(275, 90));
				panParam0.setBorder(JTBorderFactory.createTitleBorder("Agent Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					lblFirst = new JLabel("First Name");
					panParam0.add(lblFirst);
					
					lblMiddle = new JLabel("Middle Name");
					panParam0.add(lblMiddle);

					lblLast = new JLabel("Last Name");
					panParam0.add(lblLast);
					
					txtFirst = new JTextField("First Name");
					panParam0.add(txtFirst);
					txtFirst.setHorizontalAlignment(JTextField.CENTER);
					txtFirst.setEnabled(false);
					
					txtMiddle = new JTextField("Middle Name");
					panParam0.add(txtMiddle);
					txtMiddle.setHorizontalAlignment(JTextField.CENTER);
					txtMiddle.setEnabled(false);
					
					txtLast = new JTextField("Last Name");
					panParam0.add(txtLast);
					txtLast.setHorizontalAlignment(JTextField.CENTER);
					txtLast.setEnabled(false);
				}
				JXPanel panEndPage = new JXPanel(new BorderLayout(5, 5));
				panStart.add(panEndPage, BorderLayout.CENTER);
				panEndPage.setPreferredSize(new Dimension(0, 65));
				{
					JXPanel panOver1 = new JXPanel(new BorderLayout(5, 5));
					panEndPage.add(panOver1, BorderLayout.CENTER);
					panOver1.setPreferredSize(new Dimension(0, 65));
					panOver1.setBorder(JTBorderFactory.createTitleBorder("Broker Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panOverSub1 = new JXPanel(new BorderLayout(5, 5));
						panOver1.add(panOverSub1, BorderLayout.LINE_START);
						panOverSub1.setPreferredSize(new Dimension(125, 0));
						panOverSub1.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							txtOverID = new _JLookup("ID");
							panOverSub1.add(txtOverID);
							txtOverID.setHorizontalAlignment(JTextField.CENTER);
							txtOverID.setEnabled(false);
							txtOverID.setLookupSQL(SQL_Over());
							txtOverID.setReturnColumn(0);
							txtOverID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										txtOverName.setText(data[1].toString());
										txtDeptID.setText(data[2].toString());
										txtDept.setText(data[3].toString());
									}
								}
							});
						}
						JXPanel panOverSub2 = new JXPanel(new BorderLayout(5, 5));
						panOver1.add(panOverSub2, BorderLayout.CENTER);
						panOverSub2.setPreferredSize(new Dimension(125, 0));
						panOverSub2.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							txtOverName = new JTextField("Name");
							panOverSub2.add(txtOverName);
							txtOverName.setHorizontalAlignment(JTextField.CENTER);
							txtOverName.setEditable(false);
						}
					}
					
					JXPanel panOver2 = new JXPanel(new BorderLayout(5, 5));
					panEndPage.add(panOver2, BorderLayout.LINE_END);
					panOver2.setPreferredSize(new Dimension(250, 65));
					panOver2.setBorder(JTBorderFactory.createTitleBorder("Department", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panEmpty = new JXPanel(new BorderLayout(5, 5));
						panOver2.add(panEmpty, BorderLayout.CENTER);
						panEmpty.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							JXPanel panOverSub3 = new JXPanel(new GridLayout(1, 2, 5, 5));
							panEmpty.add(panOverSub3, BorderLayout.CENTER);
							panOverSub3.setPreferredSize(new Dimension(125, 0));
							{
								txtDeptID = new JTextField("Dept ID");
								panOverSub3.add(txtDeptID);
								txtDeptID.setHorizontalAlignment(JTextField.CENTER);
								txtDeptID.setEditable(false);
							}
							{
								txtDept = new JTextField("Dept Name");
								panOverSub3.add(txtDept);
								txtDept.setHorizontalAlignment(JTextField.CENTER);
								txtDept.setEditable(false);
							}
						}
					}
				}
			}
		}
		{
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			panCenter.setBorder(lineGray);
			{
				CreatePages();
				panCenter.add(pnlTab);
			}
		}
		{
			JXPanel panEnd = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panEnd, BorderLayout.PAGE_END);
			panEnd.setPreferredSize(new Dimension(0, 130));
			{
				JXPanel panParam1 = new JXPanel(new BorderLayout(5, 5));
				panEnd.add(panParam1, BorderLayout.CENTER);
				//panParam1.setPreferredSize(new Dimension(300, 30));
				{
					JXPanel pnlCenterCheck = new JXPanel(new GridLayout(1, 4, 5, 0));
					panParam1.add(pnlCenterCheck, BorderLayout.CENTER);
					pnlCenterCheck.setPreferredSize(new Dimension(275, 30));
					pnlCenterCheck.setBorder(JTBorderFactory.createTitleBorder("Filter Control", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						chkAll = new JCheckBox("Show All");
						chkNotR = new JCheckBox("Show Not Released");
						chkNotS = new JCheckBox("Show Not Submitted");
						
						pnlCenterCheck.add(chkAll);
						pnlCenterCheck.add(chkNotR);
						pnlCenterCheck.add(chkNotS);
						
						chkAll.setSelected(true);
						chkNotR.setSelected(false);
						chkNotS.setSelected(false);
						
						chkAll.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								if(e.getStateChange() == ItemEvent.SELECTED){
									chkNotR.setSelected(false);
									chkNotS.setSelected(false);
									prcDisplay(modelPRC, rowPRC, 1);
									modelPRC.setEditable(false);
								}
							}
						});
						
						chkNotR.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								if(e.getStateChange() == ItemEvent.SELECTED){
									chkAll.setSelected(false);
									chkNotS.setSelected(false);
									prcDisplay(modelPRC, rowPRC, 2);
								}
							}
						});
						
						chkNotS.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								if(e.getStateChange() == ItemEvent.SELECTED){
									prcDisplay(modelPRC, rowPRC, 3);
									InOut(false);
									chkAll.setSelected(false);
									chkNotR.setSelected(false);
									chkNotS.setSelected(true);
								}
							}
						});
					}
				}
				JXPanel panEndSub = new JXPanel(new GridLayout(1, 2, 5, 5));
				panEnd.add(panEndSub, BorderLayout.PAGE_END);
				panEndSub.setPreferredSize(new Dimension(275, 60));
				{
					JXPanel panEndDiv2 = new JXPanel(new GridLayout(1, 2, 1, 1));
					panEndSub.add(panEndDiv2, BorderLayout.CENTER);
					panEndDiv2.setPreferredSize(new Dimension(0, 0));
					{
						JXPanel panBtn1 = new JXPanel(new GridLayout(2, 4, 5, 5));
						panEndDiv2.add(panBtn1);
						{
							btnAss = new JButton("Add");
							panBtn1.add(btnAss);
							btnAss.setActionCommand("Ass");
							btnAss.addActionListener(this);

							btnMod = new JButton("Modify");
							panBtn1.add(btnMod);
							btnMod.setActionCommand("Mod");
							btnMod.addActionListener(this);

							btnSave = new JButton("Save");
							panBtn1.add(btnSave);
							btnSave.setActionCommand("Save");
							btnSave.addActionListener(this);

							btnPrev = new JButton("Print");
							panBtn1.add(btnPrev);
							btnPrev.setActionCommand("Prev");
							btnPrev.addActionListener(this);

							btnOut = new JButton("Out");
							panBtn1.add(btnOut);
							btnOut.setActionCommand("Out");
							btnOut.addActionListener(this);

							btnIn = new JButton("In");
							panBtn1.add(btnIn);
							btnIn.setActionCommand("In");
							btnIn.addActionListener(this);
							
							btnDeactivate = new JButton("Deactivate");
							panBtn1.add(btnDeactivate);
							btnDeactivate.setActionCommand("Deactivate");
							btnDeactivate.addActionListener(this);
							
							btnActivate = new JButton("Activate");
							panBtn1.add(btnActivate);
							btnActivate.setActionCommand("Activate");
							btnActivate.addActionListener(this);
							btnActivate.setEnabled(false);
						}
					}
				}
				btnState(1);				
			}
		}
		prcDisplay(modelPRC, rowPRC, 1);
	}
	
	public void UpdateRecords(String strID){
		/*
		String SQL = "SELECT * \n" + 
				"FROM rf_gov_doc_delta\n" + 
				"WHERE agent_code = '"+strID+"'";
				
		pgSelect db_select = new pgSelect();
		db_select.select(SQL);
		if (db_select.isNotNull()){
			System.out.println(strID + "is already recorded.");
		}
		else{
			pgUpdate db_update = new pgUpdate();
			String strPrime = GetNextPrime();
			
			String SQL_insert = "INSERT INTO rf_gov_doc_delta (agent_doc_rec_id, agent_code, status_id)\n" + 
					"VALUES\n" + 
					"("+strPrime+", '"+strID+"', 'A')";
			System.out.println(SQL_insert);
			db_update.executeUpdate(SQL_insert, false);
			db_update.commit();
		}
		*/
	}
	
	public static String SQL_Over(){
		String SQL = "SELECT A.agent_id, B.entity_name, A.dept_id, C.dept_name\n" + 
				"FROM mf_sellingagent_info A\n" + 
				"INNER JOIN rf_entity B ON A.agent_id = B.entity_id\n" + 
				"INNER JOIN mf_department C ON A.dept_id = C.dept_code\n" + 
				"WHERE salespos_id = '009'";
		
		return SQL;
	};
	
	public void CreatePages() {
		pnlTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlTab.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			pnlTab.add(scrTab, BorderLayout.CENTER);
			scrTab.setBorder(lineGray);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelPRC = new modelPRC();
				modelPRC.setEditable(false);
				tblPRC = new _JTableMain(modelPRC);
				tblPRC.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if(!e.getValueIsAdjusting()){
							System.out.println("");
							System.out.println("Selected row " + tblPRC.getSelectedRow());
							
							Integer intRow = tblPRC.convertRowIndexToModel(tblPRC.getSelectedRow());
							
							if(intRow!=-1){
								String strCurStat = "";
								if ((String) tblPRC.getValueAt(intRow, 2)==null) {
									strCurStat = "";
								} else {
									strCurStat = (String) tblPRC.getValueAt(intRow, 2).toString();
								}
								
								System.out.println("");
								System.out.println("Overriding	-	" + (String)tblPRC.getValueAt(intRow, 5));
								System.out.println("Broker		-	" + (String)tblPRC.getValueAt(intRow, 0));
								System.out.println("Status		-	" + strCurStat);
								System.out.println("Status		-	" + (String)tblPRC.getValueAt(intRow, 2));
								
								if((String)tblPRC.getValueAt(intRow, 0)==null){
									btnIn.setEnabled(false);
									btnOut.setEnabled(false);
									btnState(1);
									
									txtOverID.setText("");
									txtOverName.setText("");
									txtDeptID.setText("");
									txtDept.setText("");
								}
								else if((String)tblPRC.getValueAt(intRow, 0)!=null && strCurStat.equals("")){
									InOut(true);
									btnState(3);
									
									if(blnAssigned((String)tblPRC.getValueAt(intRow, 0))){
										btnMod.setEnabled(false);
									}
									txtOverID.setText(strVariety("OverID", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtOverName.setText((String)tblPRC.getValueAt(intRow, 5).toString());
									txtDeptID.setText(strVariety("DeptID", txtOverID.getText()));
									txtDept.setText(strVariety("Dept", txtDeptID.getText()));
									
									txtFirst.setText(strVariety("First", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtMiddle.setText(strVariety("Middle", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtLast.setText(strVariety("Last", (String)tblPRC.getValueAt(intRow, 0).toString()));
								}
								else if(strCurStat.equals("Released to BDT")){
									InOut(false);
									btnState(4);
									
									if(blnAssigned((String)tblPRC.getValueAt(intRow, 0))){
										btnMod.setEnabled(false);
									}
									txtOverID.setText(strVariety("OverID", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtOverName.setText((String)tblPRC.getValueAt(intRow, 5).toString());
									txtDeptID.setText(strVariety("DeptID", txtOverID.getText()));
									txtDept.setText(strVariety("Dept", txtDeptID.getText()));
									
									txtFirst.setText(strVariety("First", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtMiddle.setText(strVariety("Middle", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtLast.setText(strVariety("Last", (String)tblPRC.getValueAt(intRow, 0).toString()));
								}
								else if(strCurStat.equals("Documents Submitted")||strCurStat.equals("Inactive")){
									btnState(6);
									
									if(blnAssigned((String)tblPRC.getValueAt(intRow, 0))){
										btnMod.setEnabled(false);
									}
									txtOverID.setText(strVariety("OverID", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtOverName.setText((String)tblPRC.getValueAt(intRow, 5).toString());
									txtDeptID.setText(strVariety("DeptID", txtOverID.getText()));
									txtDept.setText(strVariety("Dept", txtDeptID.getText()));
									
									txtFirst.setText(strVariety("First", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtMiddle.setText(strVariety("Middle", (String)tblPRC.getValueAt(intRow, 0).toString()));
									txtLast.setText(strVariety("Last", (String)tblPRC.getValueAt(intRow, 0).toString()));
								}
								
								if (strCurStat.equals("Inactive")) {
									btnActivate.setEnabled(true);
									btnDeactivate.setEnabled(false);
								} else {
									btnActivate.setEnabled(false);
								}
							}
						}
					}
				});
				
				rowPRC = tblPRC.getRowHeader();
				scrTab.setViewportView(tblPRC);
				
				tblPRC.getColumnModel().getColumn(0).setPreferredWidth(250);
				tblPRC.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblPRC.getColumnModel().getColumn(2).setPreferredWidth(140);
				tblPRC.getColumnModel().getColumn(3).setPreferredWidth(88);
				tblPRC.getColumnModel().getColumn(4).setPreferredWidth(88);
				tblPRC.getColumnModel().getColumn(5).setPreferredWidth(250);
				
				rowPRC = tblPRC.getRowHeader();
				rowPRC.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowPRC);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
			}
		}	
	}
	
	public void actionPerformed(ActionEvent act) {
		if(act.getActionCommand().equals("Ass")) {
			txtOverID.setText("Double-click here");
			txtOverName.setText("");
			txtDeptID.setText("");
			txtDept.setText("");
			
			txtFirst.setEnabled(true);
			txtMiddle.setEnabled(true);
			txtLast.setEnabled(true);
			
			txtOverID.setEnabled(true);
			btnState(2);
		}
		else if(act.getActionCommand().equals("Mod")) {
			txtOverID.setEnabled(true);
			txtFirst.setEnabled(true);
			txtMiddle.setEnabled(true);
			txtLast.setEnabled(true);
			
			strRec = RecID(txtFirst.getText(), txtMiddle.getText(), txtLast.getText());
			
			System.out.println("");
			System.out.println("Record ID: " + strRec);
			
			btnState(5);
			
			blnEdit = true;
		}
		else if(act.getActionCommand().equals("Prev")) {
			Boolean blnWithSelected = false;
			for (int x = 0; x < tblPRC.getRowCount(); x++) {
				if((Boolean)tblPRC.getValueAt(x, 1).equals(true)){
					blnWithSelected = true;
				}					
			}
			
			if(!blnWithSelected){
				JOptionPane.showMessageDialog(null, "No entry was selected.");
			}
			else{
				pgUpdate db_Rev = new pgUpdate();
				String SQL_Rev = "UPDATE rf_gov_doc_delta SET tobeprinted = '0'";
				db_Rev.executeUpdate(SQL_Rev, false);
				db_Rev.commit();
				
				String strFilter = "";
				
				System.out.println("");
				System.out.println("strFilter		-	" + strFilter);
				System.out.println("Row Count		-	" + tblPRC.getRowCount());
				
				for (int x = 0; x < tblPRC.getRowCount(); x++) {
					if((Boolean)tblPRC.getValueAt(x, 1).equals(true)){
						System.out.println("Row 			-	" + x);
						strFilter = strFilter + "'" + (String)tblPRC.getValueAt(x, 0).toString() + "', ";
					}					
				}
				
				strFilter = strFilter.substring(0, strFilter.length() - 2);
				System.out.println("");
				System.out.println("strFilter		-	" + strFilter);
				
				
				pgUpdate db = new pgUpdate();
				String SQL_Out = "UPDATE rf_gov_doc_delta\n" + 
					"SET tobeprinted = '1'\n" +
					"WHERE concat_ws(' ', concat_ws(', ', Last_Name, First_Name), Middle_Name) IN ("+strFilter+")";

				System.out.println(SQL_Out);
				
				db.executeUpdate(SQL_Out, false);
				db.commit();
				
				GenHLURB();
				GenEnd();
				GenEngEmp();
			}
		}
		else if(act.getActionCommand().equals("In")) {
			In();
			btnState(6);
			prcDisplay(modelPRC, rowPRC, 1);
			chkAll.setSelected(true);
			chkNotR.setSelected(false);
			chkNotS.setSelected(false);
		}
		else if(act.getActionCommand().equals("Out")) {
			if(Out().equals(true)){
				InOut(false);
				btnState(4);
				prcDisplay(modelPRC, rowPRC, 3);
				chkAll.setSelected(false);
				chkNotR.setSelected(false);
				chkNotS.setSelected(true);	
			}
		}
		else if(act.getActionCommand().equals("Save")) {
			if(txtOverID.getText().equals("Double-click here")){
				JOptionPane.showMessageDialog(null, "No name was selected.");
			}
			else if(txtOverID.getText().equals("[ID]")){
				JOptionPane.showMessageDialog(null, "No name was selected.");
			}
			else if(txtOverID.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No name was selected.");
			}
			else{
				OverSave();
				BoxLock(false);
				btnState(3);
				prcDisplay(modelPRC, rowPRC, 1);				
			}
			
			blnEdit = false;
		}
		else if(act.getActionCommand().equals("Deactivate")) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to deactivate this account?", "Deactivation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();
				String SQL_Out = "UPDATE rf_gov_doc_delta\n" + 
						"SET status_id = 'I'\n" + 
						"WHERE first_name = '"+txtFirst.getText()+"' AND middle_name = '"+txtMiddle.getText()+"' and last_name = '"+txtLast.getText()+"'";

				System.out.println(SQL_Out);
				db.executeUpdate(SQL_Out, false);
				db.commit();
				prcDisplay(modelPRC, rowPRC, 1);
				JOptionPane.showMessageDialog(getContentPane(), txtLast.getText() + ", " + txtFirst.getText() + " " + txtMiddle.getText() + " is deactivated.");
			}
		}
		else if(act.getActionCommand().equals("Activate")) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to activate this account?", "Deactivation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();
				String SQL_Out = "UPDATE rf_gov_doc_delta\n" + 
						"SET status_id = 'A'\n" + 
						"WHERE first_name = '"+txtFirst.getText()+"' AND middle_name = '"+txtMiddle.getText()+"' and last_name = '"+txtLast.getText()+"'";

				System.out.println(SQL_Out);
				db.executeUpdate(SQL_Out, false);
				db.commit();
				prcDisplay(modelPRC, rowPRC, 1);
				JOptionPane.showMessageDialog(getContentPane(), txtLast.getText() + ", " + txtFirst.getText() + " " + txtMiddle.getText() + " is activated.");
			}
		}
	}
	
	public void prcDisplay(DefaultTableModel modelMain, JList rowHeader, Integer i){
		String strSQL = "";
		String strStatus = "";
		
		System.out.println("Status: " + strStatus);
		
		if(i==1){
			System.out.println("");
			System.out.println("Query 1");
			
			strSQL = "SELECT concat_ws(' ', concat_ws(', ', A.Last_Name, A.First_Name), A.Middle_Name) as Name, false as Mark, \n" + 
					"CASE WHEN a.status_id = 'I' THEN 'Inactive' ELSE a.status END as status, \n" +
					"out_date as Out, in_date as In, B.entity_name as Overriding\n" + 
					"FROM rf_gov_doc_delta A\n" + 
					"INNER JOIN rf_entity B ON A.overriding_id = B.entity_id\n" +
					"WHERE (a.status_id = '"+strStatus+"' OR '"+strStatus+"' = '')\n" +
					"ORDER BY A.last_name, A.first_name, A.middle_name";
		}
		else if(i==2){
			System.out.println("");
			System.out.println("Query 2");
			
			strSQL = "SELECT concat_ws(' ', concat_ws(', ', A.Last_Name, A.First_Name), A.Middle_Name) as Name, false as Mark, \n" + 
					"CASE WHEN a.status_id = 'I' THEN 'Inactive' ELSE a.status END as status, \n" +
					"out_date as Out, in_date as In, B.entity_name as Overriding\n" + 
					"FROM rf_gov_doc_delta A\n" + 
					"INNER JOIN rf_entity B ON A.overriding_id = B.entity_id\n" + 
					"WHERE COALESCE(A.status, '') = '' AND (a.status_id = '"+strStatus+"' OR '"+strStatus+"' = '')\n" + 
					"ORDER BY A.last_name, A.first_name, A.middle_name";
		}
		else{
			System.out.println("");
			System.out.println("Query 3");
			
			strSQL = "SELECT concat_ws(' ', concat_ws(', ', A.Last_Name, A.First_Name), A.Middle_Name) as Name, false as Mark, \n" + 
					"CASE WHEN a.status_id = 'I' THEN 'Inactive' ELSE a.status END as status, \n" +
					"out_date as Out, in_date as In, B.entity_name as Overriding\n" + 
					"FROM rf_gov_doc_delta A\n" + 
					"INNER JOIN rf_entity B ON A.overriding_id = B.entity_id\n" + 
					"WHERE A.status = 'Released to BDT' AND (a.status_id = '"+strStatus+"' OR '"+strStatus+"' = '')\n" + 
					"ORDER BY A.last_name, A.first_name, A.middle_name";
		}
		
		System.out.println("");
		System.out.println(strSQL);
		
		try{
			FncTables.clearTable(modelMain);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Mark 1");
		}
		
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
		};
	}
	
	public void btnState(Integer i){
		if(i == 1){							/**		Without overriding		**/
			btnAss.setEnabled(true);
			btnMod.setEnabled(false);
			btnPrev.setEnabled(false);
			btnIn.setEnabled(false);
			btnOut.setEnabled(false);
			btnSave.setEnabled(false);
			btnDeactivate.setEnabled(true);
			
			BoxLock(false);
		}
		else if(i == 2){					/**		Assign is pressed		**/
			btnAss.setEnabled(false);
			btnMod.setEnabled(false);
			btnPrev.setEnabled(false);
			btnIn.setEnabled(false);
			btnOut.setEnabled(false);
			btnSave.setEnabled(true);
			btnDeactivate.setEnabled(false);
			
			txtFirst.setText("");
			txtMiddle.setText("");
			txtLast.setText("");
			
			chkActive.setEnabled(false);
			
			BoxLock(true);
		}
		else if(i == 3){					/**		Save is pressed			**/
			btnAss.setEnabled(true);
			btnMod.setEnabled(true);
			btnPrev.setEnabled(false);
			btnIn.setEnabled(false);
			btnOut.setEnabled(true);
			btnSave.setEnabled(false);
			btnDeactivate.setEnabled(true);
			
			chkActive.setEnabled(true);
			
			BoxLock(false);
		}
		else if(i == 4){					/**		Out is pressed			**/
			btnAss.setEnabled(true);
			btnMod.setEnabled(false);
			btnPrev.setEnabled(true);
			btnIn.setEnabled(true);
			btnOut.setEnabled(false);
			btnSave.setEnabled(false);
			btnDeactivate.setEnabled(true);
			
			chkActive.setEnabled(true);
			
			BoxLock(false);
		}
		else if(i == 5){					/**		Modify is pressed		**/
			btnAss.setEnabled(false);
			btnMod.setEnabled(false);
			btnPrev.setEnabled(false);
			btnIn.setEnabled(false);
			btnOut.setEnabled(false);
			btnSave.setEnabled(true);
			btnDeactivate.setEnabled(false);
			
			chkActive.setEnabled(false);
		}
		else if(i == 6){					/**		Submitted is pressed	**/
			btnAss.setEnabled(true);
			btnMod.setEnabled(false);
			btnPrev.setEnabled(false);
			btnIn.setEnabled(false);
			btnOut.setEnabled(false);
			btnSave.setEnabled(false);
			btnDeactivate.setEnabled(true);
			
			BoxLock(false);
		}
	}
	
	public static Boolean Out(){
		Boolean blnOut = false;
		
		if(tblPRC.getRowCount() < 1){
			JOptionPane.showMessageDialog(null, "No records were retrieved for tagging.");
		}
		else{
			if(JOptionPane.showConfirmDialog(null, "Proceed?", "Documents Submission", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
				
				Boolean blnWithKek = false;
				for(int w = 0; w < tblPRC.getRowCount(); w++){
					if((Boolean)tblPRC.getValueAt(w,1).equals(true)){
						blnWithKek = true;
					}
				}
						
				if(!blnWithKek){
					JOptionPane.showMessageDialog(null, "No entry was selected.");
				}
				else{
					for(int w = 0; w < tblPRC.getRowCount(); w++){
						if((Boolean)tblPRC.getValueAt(w,1).equals(true)){
							String ID = (String)tblPRC.getValueAt(w, 0);
							String strStatus = "Released to BDT";
							
							pgUpdate db = new pgUpdate();
							String SQL_Out = "UPDATE rf_gov_doc_delta\n" + 
									"SET status = '"+strStatus+"', out_date = '"+dateFormat.format(Calendar.getInstance().getTime())+"',\n" +
									"submitted_by = '"+UserInfo.EmployeeCode+"'\n" + 
									"WHERE concat_ws(' ', concat_ws(', ', Last_Name, First_Name), Middle_Name) = '"+ID+"'";
							
							System.out.println("ID:		" + ID);
							System.out.println("Status:		" + strStatus);
							System.out.println("Date:		" + dateFormat.format(Calendar.getInstance().getTime()));
							System.out.println("User:		" + UserInfo.EmployeeCode);
							
							System.out.println("");
							System.out.println("*************************************");
							System.out.println("***				  ***");
							System.out.println("*** rf_gov_doc_delta update block ***");
							System.out.println("***				  ***");
							System.out.println("*************************************");
							System.out.println(SQL_Out);
							db.executeUpdate(SQL_Out, false);
							db.commit();
						}
					}
					
					blnOut = true;
				}
			}
		}
		return blnOut;
	}
	
	public void In(){
		if(tblPRC.getRowCount() < 1){
			JOptionPane.showMessageDialog(null, "No records were retrieved for tagging.");
		}
		else{
			if(JOptionPane.showConfirmDialog(getContentPane(), "Proceed?", "Documents Submission", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
				
				Boolean blnWithKek = false;
				for(int w = 0; w < tblPRC.getRowCount(); w++){
					if((Boolean)tblPRC.getValueAt(w,1).equals(true)){
						blnWithKek = true;
					}
				}
				
				if(!blnWithKek){
					JOptionPane.showMessageDialog(null, "No entry was selected.");
				}
				else{
					for(int w = 0; w < tblPRC.getRowCount(); w++){
						if((Boolean)tblPRC.getValueAt(w,1).equals(true)){
							String ID = (String)tblPRC.getValueAt(w, 0);
							String strStatus = "Documents Submitted";
							
							pgUpdate db = new pgUpdate();
							
							String SQL_In = "UPDATE rf_gov_doc_delta\n" + 
									"SET status = '"+strStatus+"', in_date = '"+dateFormat.format(Calendar.getInstance().getTime())+"',\n" +
									"received_by = '"+UserInfo.EmployeeCode+"'\n" + 
									"WHERE concat_ws(' ', concat_ws(', ', Last_Name, First_Name), Middle_Name) = '"+ID+"'";
							
							System.out.println("");
							System.out.println("*************************************");
							System.out.println("***				  ***");
							System.out.println("*** rf_gov_doc_delta update block ***");
							System.out.println("***				  ***");
							System.out.println("*************************************");
							System.out.println(SQL_In);
							db.executeUpdate(SQL_In, false);
							db.commit();
						}
					}
				}
			}
		}
	}
	
	public static String GetEntityID(String strName){
		String entityid = "";

		String SQL = "SELECT entity_id FROM rf_entity WHERE entity_name = '"+strName+"' \r\n";

		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			entityid = (String) sqlExec.getResult()[0][0];
		}
		else{
			entityid = "";
		}

		return entityid;
	}
	
	public static String GetNextPrime() {
		String rec_id = "";
		String SQL = "select trim(to_char(max(coalesce(agent_doc_rec_id::int,0))+1,'000000000')) from rf_gov_doc_delta" ;

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		System.out.println("");
		if(db.isNotNull()){
			rec_id = (String)db.getResult()[0][0];
			
			System.out.println("With Record");
			
			if(rec_id==null){
				rec_id = "1";
			}
		}
		else{
			System.out.println("Without Record");
			rec_id = "1";
		}
		
		System.out.println(rec_id);
		return rec_id;
	}
	
	private void GenEnd(){
		System.out.println("");
		System.out.println("Endorsement printing");
		
		/**Map<String, Object> mapParameters = new HashMap<String, Object>();**/
		FncReport.generateReport("/Reports/rpt_hlurbEnd.jasper", "HLURB Endorsement", "", null);
	}
	
	private void GenHLURB(){
		System.out.println("");
		System.out.println("Application printing");
		System.out.println(this.getClass().getClassLoader().getResourceAsStream("Images/file-page1.jpg"));
		
		String currentDir = System.getProperty("user.dir");
	    System.out.println("Current dir using System:" +currentDir);
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("image", this.getClass().getClassLoader().getResourceAsStream("Images/file-page1.jpg"));
		FncReport.generateReport("/Reports/rpt_hlurbApp.jasper", "HLURB Application", "", mapParameters);
	}
	
	private void GenEngEmp(){
		System.out.println("");
		System.out.println("Engaged / Employed");
		
		/*
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		*/
		FncReport.generateReport("/Reports/rpt_hlurbEngEmp.jasper", "Engagement / Employment", "", null);
	}
	
	private void InOut(Boolean blnDo){
		btnOut.setEnabled(blnDo);
		btnIn.setEnabled(!blnDo);
	}
	
	private void OverSave(){
		String SQL_Out = "";
		pgUpdate db = new pgUpdate();
		
		System.out.println("Edit Boolean: " + blnEdit);
		
		if(!blnEdit){
			if(txtOverID.equals("")){
				JOptionPane.showMessageDialog(getContentPane(), "No broker was specified.");
			}
			else{
				String strPrime = GetNextPrime();
				SQL_Out = "INSERT INTO rf_gov_doc_delta\n" +
						"(agent_doc_rec_id, first_name, middle_name, last_name, overriding_id, status_id)\n" +
						"VALUES\n" +
						"('"+strPrime+"', '"+txtFirst.getText()+"', '"+txtMiddle.getText()+"', '"+txtLast.getText()+"', '"+txtOverID.getText()+"', 'A')";
				System.out.println("");
				System.out.println("*************************************");
				System.out.println("***				  ***");
				System.out.println("*** rf_gov_doc_delta update block ***");
				System.out.println("***				  ***");
				System.out.println("*************************************");
			}
		}
		else{
			System.out.println("");
			System.out.println("Record ID: " + strRec);
			
			SQL_Out = "UPDATE rf_gov_doc_delta\n" + 
					"SET overriding_id = '"+txtOverID.getText()+"', first_name = '"+txtFirst.getText()+"', middle_name = '"+txtMiddle.getText()+"', last_name = '"+txtLast.getText()+"'\n" +
					"WHERE agent_doc_rec_id = "+strRec;
		}
		System.out.println(SQL_Out);
		db.executeUpdate(SQL_Out, false);
		db.commit();
		
		JOptionPane.showMessageDialog(getContentPane(), "Saved!");
	}
	
	private static String strVariety(String strWhat, String ID){
		String strThis = "";
		String SQL = "";
		
		if(strWhat.equals("OverID")){
			SQL = "SELECT B.entity_id\n" + 
					"FROM rf_gov_doc_delta A \n" + 
					"INNER JOIN rf_entity B ON A.overriding_id = B.entity_id\n" + 
					"WHERE concat_ws(' ', concat_ws(', ', A.Last_Name, A.First_Name), A.Middle_Name) = '"+ID+"'";
		}
		else if(strWhat.equals("DeptID")){
			SQL = "SELECT A.dept_code FROM mf_department A INNER JOIN mf_sellingagent_info B ON A.dept_code = B.dept_id WHERE B.agent_id = '"+ID+"'";
		}
		else if(strWhat.equals("Dept")){
			SQL = "SELECT A.dept_name FROM mf_department A WHERE A.dept_code = '"+ID+"'";
		}
		else if(strWhat.equals("First")){
			SQL = "SELECT A.First_Name\n" + 
				"FROM rf_gov_doc_delta A\n" + 
				"WHERE concat_ws(' ', concat_ws(', ', A.Last_Name, A.First_Name), A.Middle_Name) = '"+ID+"'";
		}
		else if(strWhat.equals("Middle")){
			SQL = "SELECT A.Middle_Name\n" + 
				"FROM rf_gov_doc_delta A\n" + 
				"WHERE concat_ws(' ', concat_ws(', ', A.Last_Name, A.First_Name), A.Middle_Name) = '"+ID+"'";
		}
		else if(strWhat.equals("Last")){
			SQL = "SELECT A.Last_Name\n" + 
				"FROM rf_gov_doc_delta A\n" + 
				"WHERE concat_ws(' ', concat_ws(', ', A.Last_Name, A.First_Name), A.Middle_Name) = '"+ID+"'";
		}
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		System.out.println("");
		if(db.isNotNull()){
			strThis = (String)db.getResult()[0][0];
		}
		else{
			System.out.println("Without Record");
			strThis = "";
		}
		
		return strThis;
	}
	
	private boolean blnAssigned(String strID){
		Boolean blnYes = false;
		String SQL = "SELECT *\n" + 
				"FROM mf_sellingagent_info A\n" + 
				"INNER JOIN rf_entity B ON A.agent_id = B.entity_id\n" + 
				"WHERE B.entity_name = '"+strID+"' AND COALESCE(override_id, '') <> ''";
		
		System.out.println("");
		System.out.println(SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			blnYes = true;
		}
		else{
			blnYes = false;
		}
		
		return blnYes;
	}
	
	private static String RecID(String strF, String strM, String strL) {
		String strRec = "";
		
		String SQL = "SELECT TRIM(agent_doc_rec_id::CHAR(5))\n" + 
					 "FROM rf_gov_doc_delta\n" + 
					 "WHERE first_name = '"+strF+"' and middle_name = '"+strM+"' and last_name = '"+strL+"'";
		
		System.out.println("");
		System.out.println(SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			strRec = (String)db.getResult()[0][0];
		}
		else {
			strRec = "";
		}
		
		return strRec;
	}
	
	private void BoxLock(Boolean blnDo){
		if(!blnEdit)
		{

		}

		txtFirst.setEnabled(blnDo);
		txtMiddle.setEnabled(blnDo);
		txtLast.setEnabled(blnDo);
		txtOverID.setEnabled(blnDo);
	}
}