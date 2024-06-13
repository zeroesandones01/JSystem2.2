package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Projects.ConstructionManagement.noticetoconstruct;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import tablemodel.model_tag_accounts_qualified;


public class TagAccountsQualifiedForNoticeToConstruct extends _JInternalFrame implements _GUI, MouseListener, KeyListener {

	private static final long serialVersionUID = 6500003584501090022L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;

	JLabel lblNorth;
	JLabel lblBatch;
	JLabel lblBatch2;
	JLabel lblOrientationDate;
	JLabel lblClientName;
	JLabel lblFilterToLotOnly;
	JLabel lblPhase;

	JXTextField txtOrientationDate;
	JXTextField txtClientName;
	JXTextField txtCompany;
	JXTextField txtProject;
	JXTextField txtSearch;
	JXTextField txtPhase;

	_JLookup lookupCompany;
	_JLookup lookupProject;
	_JLookup lookupBatch;
	_JLookup lookupClient;
	_JLookup lookupPhase;

	JButton btnSave;
	JButton btnPreview;
	JButton btnCancel;

	JCheckBox chkboxFilter;

	model_tag_accounts_qualified modelTagAccounts;
	JScrollPane scrollTagAccounts;
	_JTableMain tblTagAccounts;
	JList rowheaderTagAccounts;

	JComboBox cmbSorting;

	String projcode = "";

	static String title = "Tag Accounts Qualified For Notice To Construct";
	Dimension frameSize = new Dimension(1000, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);


	public TagAccountsQualifiedForNoticeToConstruct() {
		super(title, true, true, false, true);
		initGUI();
	}

	public TagAccountsQualifiedForNoticeToConstruct(String title) {
		super(title, true, true, false, true);
		initGUI();
	}

	public TagAccountsQualifiedForNoticeToConstruct(String title,
			boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
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
				pnlNorth.setLayout(null);
				pnlNorth.setBorder(lineBorder);
				pnlNorth.setPreferredSize(new Dimension(1000, 100));
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
					txtCompany = new JXTextField(" ");
					pnlNorth.add(txtCompany);
					txtCompany.setHorizontalAlignment(JLabel.LEFT);
					txtCompany.setBounds(215, 10, 280, 22);
					txtCompany.setEditable(false);
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
							final Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								new Thread(new Runnable() {

									@Override
									public void run() {
										//int pqa_selected_item = lookupProject.getValue();

										FncGlobal.startProgress("Generating Accounts");

										//displayAccounts((String) data[0], lookupBatch.getValue(), null, lookupPhase.getValue() );
										lookupPhase.setEnabled(true);
										cmbSorting.setEnabled(true);

										FncGlobal.stopProgress();
									}
								}).start();

								txtProject.setText(String.format("%s", (String) data[1]));
								lookupPhase.setLookupSQL(SQL_PHASE_NTC((String) data[0]));
								//lblBatch2.setText(String.format("%s", generateBatchNo()));
								//displayAccounts((String) data[0], lookupBatch.getValue(), lookupClient.getValue(), lookupPhase.getValue() );

								btnSave.setEnabled(true);
								btnPreview.setEnabled(true);

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					txtProject = new JXTextField(" ");
					pnlNorth.add(txtProject);
					txtProject.setHorizontalAlignment(JLabel.LEFT);
					txtProject.setBounds(215, 35, 280, 22);
					txtProject.setEditable(false);
				}
				{
					lblPhase = new JLabel("Phase");
					pnlNorth.add(lblPhase);
					lblPhase.setHorizontalAlignment(JLabel.LEFT);
					lblPhase.setBounds(10, 60, 200, 22);
				}
				{
					lookupPhase = new _JLookup(null, "Phase", "Please select project.");
					pnlNorth.add(lookupPhase);
					lookupPhase.setReturnColumn(0);
					lookupPhase.setBounds(90, 60, 120, 22);
					lookupPhase.setEnabled(false);
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							final Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								new Thread(new Runnable() {

									@Override
									public void run() {
										//int pqa_selected_item = lookupProject.getValue();

										FncGlobal.startProgress("Generating Accounts");

										displayAccounts(lookupProject.getValue(), lookupBatch.getValue(), null, (String) data[0] );

										FncGlobal.stopProgress();
									}
								}).start();
								txtPhase.setText(String.format("%s", (String) data[1]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					txtPhase = new JXTextField(" ");
					pnlNorth.add(txtPhase);
					txtPhase.setHorizontalAlignment(JLabel.LEFT);
					txtPhase.setBounds(215, 60, 280, 22);
					txtPhase.setEditable(false);
				}
				{
					lblBatch = new JLabel("By Batch #");
					pnlNorth.add(lblBatch);
					lblBatch.setHorizontalAlignment(JLabel.LEFT);
					lblBatch.setBounds(540, 10, 200, 22);
				}
				{
					lookupBatch = new _JLookup(null, "Batch");
					pnlNorth.add(lookupBatch);
					lookupBatch.setReturnColumn(0);
					lookupBatch.setLookupSQL(SQL_NTCBATCHID());
					lookupBatch.setBounds(655, 10, 130, 22);
					lookupBatch.setEditable(false);
					lookupBatch.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							final Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								new Thread(new Runnable() {

									@Override
									public void run() {
										//int pqa_selected_item = lookupProject.getValue()

										FncGlobal.startProgress("Generating Accounts");

										displayAccountswithNTC((String) data[0]);

										FncGlobal.stopProgress();
									}
								}).start();

								cmbSorting.setEnabled(true);
								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					lblClientName = new JLabel("By Client Name");
					pnlNorth.add(lblClientName);
					lblClientName.setHorizontalAlignment(JLabel.LEFT);
					lblClientName.setBounds(540, 35, 120, 22);
				}
				{
					txtSearch = new JXTextField();
					pnlNorth.add(txtSearch);
					txtSearch.setBounds(655, 35, 280, 22);
					txtSearch.setHorizontalAlignment(JTextField.CENTER);	
					txtSearch.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	
							checkAllClientList();
						}				 
					});	
				}
				{
					JLabel lblSorting = new JLabel("Sort By");
					pnlNorth.add(lblSorting);
					lblSorting.setHorizontalAlignment(JLabel.LEFT);
					lblSorting.setBounds(540, 60, 120, 22);
				}
				{
					cmbSorting = new JComboBox(new DefaultComboBoxModel(
							new String[] { "Client", "Block", "House Model" }));
					pnlNorth.add(cmbSorting);
					cmbSorting.setBounds(655, 60, 280, 22);
					cmbSorting.setEnabled(false);
				}
				{
					pnlWest = new JPanel();
					//pnlMain.add(pnlWest, BorderLayout.WEST);
					pnlWest.setLayout(new BorderLayout(5, 5));
					pnlWest.setBorder(lineBorder);
					pnlWest.setPreferredSize(new Dimension(200, 400));// XXX	
					{
						JLabel lblWest = new JLabel("WEST");
						pnlWest.add(lblWest, BorderLayout.CENTER);
						lblWest.setHorizontalAlignment(JLabel.CENTER);
					}
				}
				{
					pnlEast = new JPanel();
					//pnlMain.add(pnlEast, BorderLayout.EAST);
					pnlEast.setLayout(new BorderLayout(5, 5));
					pnlEast.setBorder(lineBorder);
					pnlEast.setPreferredSize(new Dimension(200, 400));// XXX
					{
						JLabel lblEast = new JLabel("EAST");
						pnlEast.add(lblEast, BorderLayout.CENTER);
						lblEast.setHorizontalAlignment(JLabel.CENTER);
					}
				}
				{
					pnlCenter = new JPanel();
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(5, 5));
					pnlCenter.setBorder(lineBorder);
					{
						scrollTagAccounts = new JScrollPane();
						pnlCenter.add(scrollTagAccounts, BorderLayout.CENTER);
						scrollTagAccounts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollTagAccounts.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

						modelTagAccounts = new model_tag_accounts_qualified();
						modelTagAccounts.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								if(e.getType() == TableModelEvent.DELETE){
									rowheaderTagAccounts.setModel(new DefaultListModel());
								}
								if(e.getType() == TableModelEvent.INSERT){
									((DefaultListModel)rowheaderTagAccounts.getModel()).addElement(modelTagAccounts.getRowCount());
								}
							}
						});


						tblTagAccounts = new _JTableMain(modelTagAccounts);
						tblTagAccounts.addMouseListener(this);
						tblTagAccounts.addKeyListener(this);
						tblTagAccounts.getTableHeader().setEnabled(false);
						scrollTagAccounts.setViewportView(tblTagAccounts);
						//tblTagAccounts.setRowSorter(null);
						tblTagAccounts.hideColumns("Lot Area");
						tblTagAccounts.hideColumns("Floor Area");
						tblTagAccounts.hideColumns("Unit ID");
						tblTagAccounts.hideColumns("Entity ID");
						tblTagAccounts.hideColumns("Pmt %");
						tblTagAccounts.hideColumns("Selling Price");
						tblTagAccounts.hideColumns("DP Term");
						tblTagAccounts.hideColumns("PBL ID");
						tblTagAccounts.hideColumns("Batch #");
						tblTagAccounts.hideColumns("LOG Date");

						rowheaderTagAccounts = tblTagAccounts.getRowHeader();
						rowheaderTagAccounts.setModel(new DefaultListModel());
						scrollTagAccounts.setRowHeaderView(rowheaderTagAccounts);
						scrollTagAccounts.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

					}
				}
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					//pnlSouth.setBorder(lineBorder);
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX
					{
						lblBatch2 = new JLabel("");
						pnlSouth.add(lblBatch2);
					}
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
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(this);
					}
					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setActionCommand("Preview");
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlSouth.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int response = JOptionPane.showConfirmDialog(TagAccountsQualifiedForNoticeToConstruct.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
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

		btnSave.setEnabled(false);
		btnPreview.setEnabled(false);
	}

	private void displayAccounts(String proj_id, String batchno, String client, String phase) {

		FncTables.clearTable(modelTagAccounts);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		/*
		String sql = "SELECT * FROM sp_generate_notice_construct('"+proj_id+"', '"+batchno+"', '"+client+"', '"+phase+"') order by c_client_name";
		 */

		//String sql = "SELECT * FROM sp_generate_notice_construct_v2('"+proj_id+"', '"+batchno+"', '"+client+"', '"+phase+"') order by c_client_name";

		String SQL = "SELECT * FROM sp_generate_notice_construct_v3(nullif('"+lookupCompany.getValue()+"','null'), NULLIF('"+lookupProject.getValue()+"','null'), NULLIF('"+lookupPhase.getValue()+"', 'null'))"
				+ "group by c_description\n" + 
				",c_select , c_batch_no , c_client_name , c_or_date , c_proj_alias , c_phase , c_block , c_lot , c_model_desc , c_color_scheme , c_stage , c_status , c_buyer_type , c_lotarea , c_floorarea , c_unit_id , c_entity_id , c_pmt_perc , c_model_id , c_dp_term , c_pbl_id , c_log_date , c_cluster_value ,c_description \n" + 
				"ORDER BY c_cluster_value,getinteger(c_phase), c_block::int, c_lot::int;;";

		pgSelect db = new pgSelect();
		db.select(SQL);

		FncSystem.out("Display sql for Tag Accounts", SQL);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelTagAccounts.addRow(db.getResult()[x]);
				listModel.addElement(modelTagAccounts.getRowCount());
			}	
		}		
		tblTagAccounts.packAll();
		btnSave.setEnabled(true);
	}

	private void displayAccountswithNTC(String batchno) {

		FncTables.clearTable(modelTagAccounts);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT * FROM sp_generate_notice_construct_withNTC('"+lookupBatch.getValue()+"') order by c_client_name"; 

		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display sql for NTC Accounts", sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelTagAccounts.addRow(db.getResult()[x]);
				listModel.addElement(modelTagAccounts.getRowCount());
			}	
		}		
		tblTagAccounts.packAll();
		//tblTagAccounts.setEditable(false);
		btnSave.setEnabled(false);
	}

	private void refreshTO() {

		if (lookupProject.getText().equals("...")) {
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupBatch.setValue("");
			lookupPhase.setValue("");
			lookupPhase.setEnabled(false);
			txtClientName.setText("");
			txtCompany.setText(" ");
			txtProject.setText(" ");
			modelTagAccounts.setRowCount(0);
			btnSave.setEnabled(false);
			btnPreview.setEnabled(false);
			lblBatch2.setText("");
			cmbSorting.setEnabled(false);
		}
	} // refreshTO()
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		String sort_by = (String) cmbSorting.getSelectedItem();
		ArrayList<String> iftrue = new ArrayList<String>();
		String SQL ="";
		if(actionCommand.equals("Preview")){
			ArrayList<noticetoconstruct> listnoticetoconstruct = new ArrayList<noticetoconstruct>();
			for(int x =0; x < modelTagAccounts.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelTagAccounts.getValueAt(x, 0);
				String client_name = (String) modelTagAccounts.getValueAt(x, 2);
				String phase = (String) modelTagAccounts.getValueAt(x, 5);
				String block = (String) modelTagAccounts.getValueAt(x, 6);
				String lot = (String) modelTagAccounts.getValueAt(x, 7);
				if(isSelected){
					listnoticetoconstruct.add(new noticetoconstruct(client_name, phase, block, lot));
					System.out.printf("%s: Phase %s Block %s Lot %s\n", client_name, phase, block, lot);
				}
			}

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("proj_name", txtProject.getText());
			mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));



			for (int i = 0; i < modelTagAccounts.getRowCount(); i++) {
				Boolean SelectedItem = (Boolean) modelTagAccounts.getValueAt(i, 0);

				if (SelectedItem) {
					iftrue.add(modelTagAccounts.getValueAt(i, 0).toString());
					//iftrue.add(modelTagAccounts.getValueAt(i, 18).toString());
					SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +

							"SELECT \n" +
							"'"+modelTagAccounts.getValueAt(i, 1)+"' AS batch_no,\n" +
							"'"+modelTagAccounts.getValueAt(i, 2)+"' AS client_name,\n" + 
							"NULLIF('"+modelTagAccounts.getValueAt(i, 3)+"','null') AS or_date,\n" + 
							"'"+modelTagAccounts.getValueAt(i, 18)+"' AS selling_price,\n" +
							"'"+modelTagAccounts.getValueAt(i, 5)+"' AS phase,\n" + 
							"NULLIF('"+modelTagAccounts.getValueAt(i, 6) +"','null') AS block,\n" + 
							"'"+modelTagAccounts.getValueAt(i, 7)+"' AS lot,\n" + 
							"NULLIF('"+modelTagAccounts.getValueAt(i, 13)+"','null') AS lotarea,\n" + 
							"NULLIF('"+modelTagAccounts.getValueAt(i, 14)+"','null') AS floorarea,\n" + 
							"'"+modelTagAccounts.getValueAt(i, 8)+"' AS house_model,\n" + 
							"NULLIF('"+modelTagAccounts.getValueAt(i, 9)+"','null') AS color_scheme,\n" + 
							"NULLIF('"+modelTagAccounts.getValueAt(i, 10)+"','null') AS stage,\n" + 
							"'"+modelTagAccounts.getValueAt(i, 11)+"' AS status,\n" + 
							"NULLIF('"+modelTagAccounts.getValueAt(i, 17)+"','null') AS pmt_rate, \n"+
							"'"+modelTagAccounts.getValueAt(i, 12)+"' AS buyer_type, \n"+
							"NULLIF('"+modelTagAccounts.getValueAt(i, 19)+"','null') AS dp_term,\n" + 
							"NULLIF('"+modelTagAccounts.getValueAt(i, 21)+"','null') AS log_date,\n" + 
							"'' AS remarks,\n"+
							"'"+modelTagAccounts.getValueAt(i, 4)+"' AS proj_alias\n";
				}
			}

			if (iftrue.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
				return;
			}

			FncReport.generateReport("/Reports/rptNoticeToConstructList.jasper", "Notice to Construct", mapParameters,SQL);
		}

		if(actionCommand.equals("Save")){
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listunit_id = new ArrayList<String>();
			ArrayList<String> listproj= new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<String> listcs = new ArrayList<String>();
			ArrayList<String> listphase = new ArrayList<String>();
			ArrayList<String> listblock = new ArrayList<String>();
			ArrayList<String> listlot = new ArrayList<String>();

			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				for(int x =0; x < modelTagAccounts.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelTagAccounts.getValueAt(x, 0);
					//					String entity_id = (String) modelTagAccounts.getValueAt(x, 16);
					//					String unit_id = (String) modelTagAccounts.getValueAt(x, 15);
					//					String pbl_id = (String) modelTagAccounts.getValueAt(x, 20);
					//					String cs = (String) modelTagAccounts.getValueAt(x, 9);
					//					//String proj = (String) modelTagAccounts.getValueAt(x, 4);
					//					String phase = (String) modelTagAccounts.getValueAt(x, 5);
					//					String block = (String) modelTagAccounts.getValueAt(x, 6);
					//					String lot = (String) modelTagAccounts.getValueAt(x, 7);
					//					String batch = (String) lblBatch2.getText();
					if(isSelected){
						String entity_id = (String) modelTagAccounts.getValueAt(x, 16);
						String unit_id = (String) modelTagAccounts.getValueAt(x, 15);
						String pbl_id = (String) modelTagAccounts.getValueAt(x, 20);
						String cs = (String) modelTagAccounts.getValueAt(x, 9);
						String proj = lookupProject.getValue();
						String phase = (String) modelTagAccounts.getValueAt(x, 5);
						String block = (String) modelTagAccounts.getValueAt(x, 6);
						String lot = (String) modelTagAccounts.getValueAt(x, 7);
						//						String strSQL = "SELECT sp_save_tagaccounts_ntc('"+entity_id+"', '"+ lookupProject.getValue() +"', '"+unit_id+"', '"+ UserInfo.EmployeeCode +"', '"+pbl_id+"', NULLIF('"+batch+"','null'), '"+cs+"', '"+phase+"', '"+block+"', '"+lot+"');"; 
						//
						//						pgSelect db = new pgSelect();
						//						db.select(strSQL);
						listPBL.add(String.format("'%s'", pbl_id));
						listEntityID.add(String.format("'%s'", entity_id));
						listproj.add(String.format("'%s'", proj));
						listunit_id.add(String.format("'%s'", pbl_id));
						listcs.add(String.format("'%s'", cs));
						listphase.add(String.format("'%s'", phase));
						listblock.add(String.format("'%s'", block));
						listlot.add(String.format("'%s'", lot));
					}
					//JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
				{
					String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
					String proj = listproj.toString().replaceAll("\\[|\\]", "");
					String unit_id = listunit_id.toString().replaceAll("\\[|\\]", "");
					String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
					String cs = listcs.toString().replaceAll("\\[|\\]", "");
					String phase = listphase.toString().replaceAll("\\[|\\]", "");
					String block = listblock.toString().replaceAll("\\[|\\]", "");
					String lot = listlot.toString().replaceAll("\\[|\\]", "");

//					String strSQL = "SELECT sp_save_tagaccounts_ntc_v2('"+entity_id+"', '"+ lookupProject.getValue() +"', '"+unit_id+"', '"+ UserInfo.EmployeeCode +"', '"+pbl_id+"', NULLIF('"+batch+"','null'), '"+cs+"', '"+phase+"', '"+block+"', '"+lot+"');"; 
					String strSQL = "SELECT sp_save_tagaccounts_ntc_v2(ARRAY["+entity_id+"]::VARCHAR[],ARRAY["+proj+"]::VARCHAR[],ARRAY["+unit_id+"]::VARCHAR[], '"+ UserInfo.EmployeeCode +"',ARRAY["+pbl_id+"]::VARCHAR[],ARRAY["+cs+"]::VARCHAR[],ARRAY["+phase+"]::VARCHAR[], ARRAY["+block+"]::VARCHAR[],ARRAY["+lot+"]::VARCHAR[]);"; 

					pgSelect db = new pgSelect();
					db.select(strSQL);
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);

				}
				btnSave.setEnabled(true);
				btnPreview.setEnabled(true);
				modelTagAccounts.setRowCount(0);
				
				displayAccounts(lookupProject.getValue(), lookupBatch.getValue(), lookupClient.getValue(), lookupPhase.getValue() );	
				lblBatch2.setText("");
				modelTagAccounts.setRowCount(0);
			}


		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblTagAccounts)) {
			int row = tblTagAccounts.convertRowIndexToModel(tblTagAccounts.getSelectedRow());
			Boolean isSelected2 = (Boolean) modelTagAccounts.getValueAt(row, 0);

			for(int x = 0; x < modelTagAccounts.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelTagAccounts.getValueAt(x, 0);
				if (isSelected) {
					lblBatch2.setText(String.format("%s", generateBatchNo()));
				}
			}
			if (isSelected2) {
				lblBatch2.setText(String.format("%s", generateBatchNo()));
			}
			if ((e.getClickCount() >= 2)) {
				clickTableColumn();
			}
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
	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F2) {
			clickTableColumn();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	private void clickTableColumn() {//used
		int column = tblTagAccounts.getSelectedColumn();
		int row = tblTagAccounts.getSelectedRow();

		if (column == 9) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Color Scheme", getColorScheme(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelTagAccounts.setValueAt(data[0], row, column);
			}
		}
	}
	private String getColorScheme(){//used

		String sql = "select hse_color as \"Color Scheme\" from rf_marketing_scheme_pricelist group by hse_color order by hse_color";		

		FncSystem.out("Color", sql);

		return sql;

	}
	private void checkAllClientList(){

		int rw = tblTagAccounts.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";

			try { name	= tblTagAccounts.getValueAt(x,1).toString().toUpperCase();}
			catch (NullPointerException e) { name	= ""; }

			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			Boolean	end			= name.endsWith(acct_name);

			if (match==true||start==true||end==true) {
				tblTagAccounts.setRowSelectionInterval(x, x); 
				tblTagAccounts.changeSelection(x, 1, false, false);				
				break;			
			}
			else {
				tblTagAccounts.setRowSelectionInterval(0, 0); 
				tblTagAccounts.changeSelection(0, 1, false, false);					
			}

			x++;

		}		
	}
	private static String SQL_NTCBATCHID() {
		String SQL = "SELECT ntc_batchno as \"Batch No.\", get_client_name(entity_id) as \"Client Name\"\n" + 
				"FROM rf_buyer_status\n" + 
				"where ntc_batchno is not null and status_id = 'A' \n" +
				"GROUP BY ntc_batchno, get_client_name(entity_id) \n"+
				"ORDER BY ntc_batchno DESC;";
		return SQL;
	}
	private static String generateBatchNo() {
		String strSQL = "SELECT to_char(COALESCE(MAX(ntc_batchno::INT), 0) + 1, 'FM0000000000') FROM rf_buyer_status WHERE status_id = 'A' and ntc_batchno is not null and ntc_batchno != '';";

		//FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	private List<JRSortField> sortBy(String sort_by) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("Client", new String[]{"client_name"});
		map.put("Block", new String[]{"block"});
		map.put("House Model", new String[]{"house_model"});

		List<JRSortField> sortList = new ArrayList<JRSortField>();
		for(String fields : map.get(sort_by)){
			JRDesignSortField sortField = new JRDesignSortField();
			sortField.setName(fields);
			sortField.setOrder(SortOrderEnum.ASCENDING);
			sortField.setType(SortFieldTypeEnum.FIELD);

			sortList.add(sortField);
		}

		return sortList;
	}
}