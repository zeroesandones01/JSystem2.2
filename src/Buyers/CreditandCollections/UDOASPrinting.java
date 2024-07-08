package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelUDOASPrinting;

public class UDOASPrinting extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 1L;
	static String title = "Unilateral DOAS Printing";
	Dimension frameSize = new Dimension(700, 500);

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

	private JTabbedPane tabCenter;
	private JButton btnGenerate;
	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private _JLookup lookupPhase;
	private _JDateChooser dateDateFrom;
	private _JDateChooser dateDateTo;
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	private JScrollPane scrollTable;
	private modelUDOASPrinting modelUDOASPrinting;
	private _JTableMain tableUDOASPrinting;
	private JList rowHeader;
	private JButton btnPreview;

	public UDOASPrinting() {
		super(title, true, true, false, true);
		initGUI();
	}

	public UDOASPrinting(String title) {
		super(title);
		initGUI();
	}

	public UDOASPrinting(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		{
			JPanel pnlMain = new JPanel(new BorderLayout(5, 5)); 
			getContentPane().add(pnlMain, BorderLayout.CENTER); 
			pnlMain.setPreferredSize(frameSize);
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER); 
				{
					JPanel pnlPrintUDOAS = new JPanel(new BorderLayout(5, 5));
					tabCenter.addTab("UDOAS Printing", pnlPrintUDOAS);
					{
						JPanel pnlNorth = new JPanel(new BorderLayout(5, 5)); 
						pnlPrintUDOAS.add(pnlNorth, BorderLayout.NORTH); 
						pnlNorth.setPreferredSize(new Dimension(0, 180));
						pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Generate Accounts"));
						{
							JPanel pnlCenter = new JPanel(new BorderLayout(5, 5)); 
							pnlNorth.add(pnlCenter, BorderLayout.CENTER); 

							{
								JPanel pnlWestComp = new JPanel(new GridLayout(3, 2, 3, 3)); 
								pnlCenter.add(pnlWestComp, BorderLayout.WEST);
								pnlWestComp.setPreferredSize(new Dimension(200, 0));
								{
									JLabel lblCompany = new JLabel("   Company"); 
									pnlWestComp.add(lblCompany); 
								}
								{
									lookupCompany = new _JLookup(null,"Company", 0); 
									pnlWestComp.add(lookupCompany); 
									lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
									lookupCompany.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object data [] = ((_JLookup)event.getSource()).getDataSet();
											if (data != null) {

												txtCompany.setText(data[1].toString());
												btnGenerate.setEnabled(true);
												lookupProject.setLookupSQL(SQL_PROJECT(data[0].toString()));
											} else {
												txtCompany.setText("");
											}

										}
									});
								}
								{
									JLabel lblProject = new JLabel("   Project"); 
									pnlWestComp.add(lblProject); 
								}
								{
									lookupProject = new _JLookup(null, "Project", 0);
									pnlWestComp.add(lookupProject); 
									lookupProject.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object data [] = ((_JLookup)event.getSource()).getDataSet();
											if (data != null) {

												txtProject.setText(data[1].toString());
												lookupPhase.setLookupSQL(SQL_PHASE(data[0].toString()));
											} else {
												txtProject.setText("");
											}

										}
									});
								}
								{
									JLabel lblPhase = new JLabel("   Phase"); 
									pnlWestComp.add(lblPhase); 
								}
								{
									lookupPhase = new _JLookup(null, "Phase", 0);
									pnlWestComp.add(lookupPhase); 
									lookupPhase.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object data [] = ((_JLookup)event.getSource()).getDataSet();
											if (data != null) {

												txtPhase.setText(data[1].toString());
											} else {
												txtPhase.setText("");
											}

										}
									});
								}
							}

							{
								JPanel pnlEastComp = new JPanel(new GridLayout(3, 1, 3, 3)); 
								pnlCenter.add(pnlEastComp, BorderLayout.CENTER); 
								{
									txtCompany = new JTextField(); 
									pnlEastComp.add(txtCompany); 
								}
								{
									txtProject = new JTextField(); 
									pnlEastComp.add(txtProject); 
								}
								{
									txtPhase = new JTextField(); 
									pnlEastComp.add(txtPhase); 
								}
							}
							{
								JPanel pnlDateCovered = new JPanel(new GridLayout(1, 5, 5, 5));
								pnlCenter.add(pnlDateCovered, BorderLayout.SOUTH); 
								pnlDateCovered.setPreferredSize(new Dimension(0, 40));
								{
									JLabel lblDateFrom = new JLabel("   Date From:"); 
									pnlDateCovered.add(lblDateFrom); 
								}
								{
									dateDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlDateCovered.add(dateDateFrom); 
								}
								{
									JLabel lblDateTo = new JLabel("              Date To:"); 
									pnlDateCovered.add(lblDateTo); 
								}
								{
									dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlDateCovered.add(dateDateTo); 
								}
								{
									pnlDateCovered.add(Box.createHorizontalBox());
								}
							}	
						}
						{
							JPanel pnlGenerate = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnlGenerate, BorderLayout.EAST); 
							pnlGenerate.setPreferredSize(new Dimension(150, 0));
							{
								btnGenerate = new JButton("Generate"); 
								pnlGenerate.add(btnGenerate); 
								btnGenerate.addActionListener(this);
								btnGenerate.setActionCommand(btnGenerate.getText());

							}
						}
					}
					{
						JPanel pnlTable = new JPanel(new BorderLayout(3, 3)); 
						pnlPrintUDOAS.add(pnlTable, BorderLayout.CENTER); 
						{
							scrollTable = new JScrollPane();
							pnlTable.add(scrollTable, BorderLayout.CENTER); 
							{
								modelUDOASPrinting = new modelUDOASPrinting(); 
								tableUDOASPrinting = new _JTableMain(modelUDOASPrinting); 
								scrollTable.setViewportView(tableUDOASPrinting); 
								scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
								tableUDOASPrinting.setSortable(false); 

								tableUDOASPrinting.hideColumns("Entity ID");
								tableUDOASPrinting.hideColumns("Proj. ID");
								tableUDOASPrinting.hideColumns("PBL ID");
								tableUDOASPrinting.hideColumns("Seq. No");
								tableUDOASPrinting.getColumnModel().getColumn(4).setPreferredWidth(250);
								tableUDOASPrinting.getColumnModel().getColumn(6).setPreferredWidth(100);

							}
							{
								rowHeader = tableUDOASPrinting.getRowHeader();
								rowHeader.setModel(new DefaultListModel());
								scrollTable.setRowHeaderView(rowHeader);
								scrollTable.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
					{
						JPanel pnlSouth = new JPanel(new BorderLayout(5, 5)); 
						pnlPrintUDOAS.add(pnlSouth, BorderLayout.SOUTH); 
						pnlSouth.setPreferredSize(new Dimension(0, 50));
						{
							JPanel pnlPreview = new JPanel(); 
							pnlSouth.add(pnlPreview, BorderLayout.EAST); 
							pnlPreview.setPreferredSize(new Dimension(100, 0)); 
							{
								btnPreview = new JButton("Preview"); 
								pnlPreview.add(btnPreview);
								btnPreview.setEnabled(false);
								btnPreview.setPreferredSize(new Dimension(80, 40));
								btnPreview.setActionCommand(btnPreview.getText());
								btnPreview.addActionListener(this); 
							}	
						}
					}
				}
			}
		}
	} //XXX END OF INIT GUI

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand(); 

		if(actionCommand.equals("Generate")) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					FncGlobal.startProgress("Generating Accounts");
					displayDataTable(modelUDOASPrinting, rowHeader);
					btnPreview.setEnabled(true);
					JOptionPane.showMessageDialog(getContentPane(),"Units Generated.","Information",JOptionPane.INFORMATION_MESSAGE);	
					FncGlobal.stopProgress();
					btnGenerate.setEnabled(false);

				}
			}).start();

		}

		if(actionCommand.equals("Preview")) {
			previewUDOAS();
		}
	}

	private void displayDataTable(DefaultTableModel modelUDOASPrinting, JList rowHeader) {
		FncTables.clearTable(modelUDOASPrinting);
		DefaultListModel list = new DefaultListModel(); 
		rowHeader.setModel(list);

		String SQL = "Select * from fn_generate_accts_for_udoas('"+lookupCompany.getValue()+"', '"+lookupProject.getValue()+"', '"+lookupPhase.getValue()+"', '"+dateDateFrom.getDate()+"', '"+dateDateTo.getDate()+"');"; 
		pgSelect db = new pgSelect(); 
		System.out.println("SQL: " + SQL);
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelUDOASPrinting.addRow(db.getResult()[x]);	
				list.addElement(modelUDOASPrinting.getRowCount());
				scrollTable.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tableUDOASPrinting.getRowCount())));
			}
		}
	}
	
	private void previewUDOAS() {
		String selected_entity_id = "";
		String selected_proj_id = "";
		String selected_pbl_id = "";
		String selected_seq_no = "";

		for (int i = 0; i < modelUDOASPrinting.getRowCount(); i++) {
			boolean tag = (boolean) modelUDOASPrinting.getValueAt(i, 0);
			if(tag) {
				String entity_id = (String) modelUDOASPrinting.getValueAt(i, 11);
				String proj_id = (String) modelUDOASPrinting.getValueAt(i, 12);
				String pbl_id = (String) modelUDOASPrinting.getValueAt(i, 13);
				Object seq_no = modelUDOASPrinting.getValueAt(i, 14);
				
				if(i == selected_count()) {
					selected_entity_id += entity_id;
					selected_proj_id += proj_id;
					selected_pbl_id += pbl_id;
					selected_seq_no += seq_no;
				} else {
					selected_entity_id += entity_id + ",";
					selected_proj_id += proj_id + ",";
					selected_pbl_id += pbl_id + ",";
					selected_seq_no += seq_no + ",";
				}
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("entity_id", selected_entity_id);
		mapParameters.put("proj_id", selected_proj_id);
		mapParameters.put("phase", lookupPhase.getValue());
		mapParameters.put("pbl_id", selected_pbl_id); 
		mapParameters.put("seq_no", selected_seq_no); 

		// ADDED DUE TO LENGTH OF TECHNICAL DESCRIPTION
		if(lookupProject.getValue().equals("015")){ //TERRAVERDE RESIDENCES 
			FncReport.generateReport("/Reports/rptUDOAS_TV.jasper", "Unilateral DOAS TV", mapParameters);
		} else {
			FncReport.generateReport("/Reports/rptUDOAS.jasper", "Unilateral DOAS TV", mapParameters);
		}
		
		System.out.println("Preview UDOAS Report");
		String sql = "Select * from view_unilateral_doas('"+lookupCompany.getValue()+"', '"+selected_entity_id+"', '"+selected_proj_id+"', '"+lookupPhase.getValue()+"', '"+selected_pbl_id+"', '"+selected_seq_no+"');";
		FncSystem.out("Unilateral DOAS Report: ", sql);
	}
	
	private int selected_count() {
		int count = 0;
		for (int i = 0; i < modelUDOASPrinting.getRowCount(); i++) {
			boolean tag = (boolean) modelUDOASPrinting.getValueAt(i, 0);
			if(tag) {
				count += 1;
			}
		}
		return count;
	}

}
