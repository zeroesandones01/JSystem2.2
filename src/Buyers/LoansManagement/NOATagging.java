package Buyers.LoansManagement;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Buyers.ClientServicing.TD_WaivePenalty_PmtsWaived;
import Database.pgSelect;
import DateChooser._JDateChooser;
import tablemodel.modelNOATagging;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components._JInternalFrame;
import components._JTableMain;

public class NOATagging extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6517018477299344044L;

	static String title = "NOA Tagging (PagIBIG Accounts)";
	Dimension frameSize = new Dimension(800, 550);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;

	private JScrollPane scrollCenter;
	private _JTableMain tblAccounts;
	private modelNOATagging modelAccounts;
	private JList rowheaderAccounts;

	private _JDateChooser dteNOAActualDate;
	private JButton btnSave;
	private JButton btnCancel;

	public NOATagging() {
		super(title, true, true, true, true);
		initGUI();
	}

	public NOATagging(String title) {
		super(title);
		initGUI();
	}

	public NOATagging(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(3, 3));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 3));
			{
				pnlNorth = new JPanel();
				//pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
				pnlNorth.setPreferredSize(new Dimension(790, 119));
			}
			{
				scrollCenter = new JScrollPane();
				pnlMain.add(scrollCenter, BorderLayout.CENTER);
				scrollCenter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollCenter.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						tblAccounts.clearSelection();
					}
				});
				{
					modelAccounts = new modelNOATagging();
					modelAccounts.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == TableModelEvent.DELETE){
								rowheaderAccounts.setModel(new DefaultListModel());
								scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccounts.getRowCount())));
							}
							if(e.getType() == TableModelEvent.INSERT){
								((DefaultListModel)rowheaderAccounts.getModel()).addElement(modelAccounts.getRowCount());
							}
						}
					});

					tblAccounts = new _JTableMain(modelAccounts);
					scrollCenter.setViewportView(tblAccounts);
					tblAccounts.hideColumns("Client ID", "Proj. ID", "PBL ID", "Seq." ,"BuyerType", "Project");

					tblAccounts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if(!e.getValueIsAdjusting()){//XXX tblJVDetail

							}
						}
					});
				}
				{
					rowheaderAccounts = tblAccounts.getRowHeader();
					rowheaderAccounts.setModel(new DefaultListModel());
					scrollCenter.setRowHeaderView(rowheaderAccounts);
					scrollCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout());
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(790, 30));
				{
					JPanel pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.WEST);
					pnlNavigation.setPreferredSize(new Dimension(400, 30));
					{
						JButton btnRefresh = new JButton("Refresh");
						pnlNavigation.add(btnRefresh);
						btnRefresh.addActionListener(this);
					}
					{
						JPanel pnlActualDate = new JPanel(new BorderLayout(5, 5));
						pnlNavigation.add(pnlActualDate);
						{
							JLabel lblActualDate = new JLabel("Actual Date", JLabel.TRAILING);
							pnlActualDate.add(lblActualDate, BorderLayout.WEST);
						}
						{
							dteNOAActualDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlActualDate.add(dteNOAActualDate, BorderLayout.CENTER);
							dteNOAActualDate.setPreferredSize(new Dimension(150, 0));
						}
					}
				}
				{
					JPanel pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setPreferredSize(new Dimension(205, 30));
					{
						btnSave = new JButton("Save");
						pnlNavigation.add(btnSave);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}

		displayAccounts(null);
	}

	private void displayAccounts(String co_id) {
		modelAccounts.clear();
		/*String SQL = "SELECT false, *\n" + 
				"FROM view_pagibig_accounts('"+ co_id +"') a\n" + 
				"WHERE NOT EXISTS(SELECT *\n" + 
				"     FROM rf_buyer_status\n" + 
				"     WHERE entity_id = a.client_id\n" + 
				"     AND proj_id = a.proj_id\n" + 
				"     AND pbl_id = a.pbl_id\n" + 
				"     AND seq_no = a.seq_no\n" + 
				"     AND byrstatus_id = '35'" +
				"	  AND status_id = 'A') " +
				"AND unit_isofficiallyreserved(a.pbl_id, a.seq_no) is true " +
				"AND (a.pbl_id::int, a.seq_no::int) not in (select pbl_id, seq_no::int from canceled_accounts) ;";*/
		
		String SQL = "SELECT * FROM view_pagibig_noa_qualified()";
		FncSystem.out("PagIBIG Accounts", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelAccounts.addRow(db.getResult()[x]);
			}
		}

		scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccounts.getRowCount())));
		tblAccounts.packAll();
	}
	
	private void previewTaggedNOA(){
		
		ArrayList<TD_NOA_Tagging> listNOATaggedAccts = new ArrayList<TD_NOA_Tagging>();
		
		for(int x = 0; x < modelAccounts.getRowCount(); x++){
			
			Boolean isSelected = (Boolean) modelAccounts.getValueAt(x, 0);
			
			if(isSelected){
				
				String entity_name = (String) modelAccounts.getValueAt(x, 2);
				String unit_desc = (String) modelAccounts.getValueAt(x, 3);
				BigDecimal loan_amt = (BigDecimal) modelAccounts.getValueAt(x, 9);
				Integer loan_term = (Integer) modelAccounts.getValueAt(x, 10);
				Date actual_date = dteNOAActualDate.getDate();
				
				listNOATaggedAccts.add(new TD_NOA_Tagging(entity_name, unit_desc, loan_amt, loan_term, actual_date));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listNOATaggedAccts", listNOATaggedAccts);
		
		FncReport.generateReport("/Reports/rptNOATaggedAccounts.jasper", "NOA Tagged Accounts", mapParameters);
	}
	
	private void exportTaggedNOA(){
		
		for(int x= 0; x<modelAccounts.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelAccounts.getValueAt(x, 0);
			
			if(isSelected){
				
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();

		if(action.equals("Refresh")){
			displayAccounts(null);
			dteNOAActualDate.setDate(null);
		}
		if(action.equals("Save")){
			ArrayList<Boolean> hasSelected = new ArrayList<Boolean>();
			for(int x=0; x < modelAccounts.getRowCount(); x++){
				Boolean selected = (Boolean) modelAccounts.getValueAt(x, 0);
				hasSelected.add(selected);
			}

			if(hasSelected.contains(true)){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					ArrayList<String> listEntityID = new ArrayList<String>();
					ArrayList<String> listProjID = new ArrayList<String>();
					ArrayList<String> listPblID = new ArrayList<String>();
					ArrayList<Integer> listSeqNo = new ArrayList<Integer>();


					for(int x=0; x < modelAccounts.getRowCount(); x++){
						Boolean selected = (Boolean) modelAccounts.getValueAt(x, 0);
						String entity_id = (String) modelAccounts.getValueAt(x, 1);
						//String entity_name = (String) modelAccounts.getValueAt(x, 2);
						String pbl_id = (String) modelAccounts.getValueAt(x, 4);
						Integer seq_no = (Integer) modelAccounts.getValueAt(x, 5);
						String proj_id = (String) modelAccounts.getValueAt(x, 7);

						hasSelected.add(selected);
						if(selected){
							listEntityID.add(String.format("'%s'", entity_id));
							listProjID.add(String.format("'%s'", proj_id));
							listPblID.add(String.format("'%s'", pbl_id));
							listSeqNo.add(seq_no);
						}
					}
					
					Date actual_date = dteNOAActualDate.getDate();
					
					//PUT DATE TAGGED HERE
					String SQL = "SELECT sp_tag_noa(\n" +
							"  ARRAY"+ listEntityID +"::VARCHAR[],\n" +
							"  ARRAY"+ listProjID +"::VARCHAR[],\n" +
							"  ARRAY"+ listPblID +"::VARCHAR[],\n" +
							"  ARRAY"+ listSeqNo +"::INTEGER[],\n" +
							" NULLIF('"+actual_date+"', 'null')::TIMESTAMP, \n"+
							"  '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
					System.out.printf("%s%n", listEntityID.toString());
					System.out.printf("%s%n", listProjID.toString());
					System.out.printf("%s%n", listPblID.toString());
					System.out.printf("%s%n%n", listSeqNo.toString());
					System.out.printf("%s%n", SQL);

					pgSelect db = new pgSelect();
					db.select(SQL, "Save", true);

					if(db.isNotNull() && (Boolean)db.getResult()[0][0]){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client(s) has been tagged.", action, JOptionPane.INFORMATION_MESSAGE);
						previewTaggedNOA();
						displayAccounts(null);
						dteNOAActualDate.setDate(null);
					}
				}
			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client to tagged.", action, JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
