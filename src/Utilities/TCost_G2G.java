package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JTextFieldDateEditor;

import Buyers.ClientServicing._OtherRequest;
import Database.pgSelect;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelG2GTcostTagging;
import tablemodel.model_garbage_fee_for_issuance;

public class TCost_G2G extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 959523740860207910L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;
	JPanel pnlSelect;
	JPanel pnlDate;
	JPanel pnllblDateTo;
	JPanel pnlDateTo;
	JPanel pnlUploading;
	JPanel pnlGenerateDetails;

	private JButton btnGenerate;
	private JButton btnPreview;
	private JButton btnApprove;
	private JButton btnCancel;
	private JButton btnSearch;
	private JButton btnSave;

	JCheckBox chkPBL;

	JLabel lblDateTo;
	JLabel lblDateFr;
	JLabel lblWithPBL;

	JXTextField tagSelectFile;

	_JDateChooser dateTo;
	_JDateChooser dateFr;

	_JTableMain tblGarbageFee;
	JTabbedPane tabCenter;

	JScrollPane scrollG2GTcostTagging;

	JList rowheaderGarbageFee;

	JFileChooser fileChooser;

	DefaultTableModel modelGarbageFee;

	private modelG2GTcostTagging modelG2GTcost;
	JList rowHeaderG2GTcost;

	_JLookup lookupCompany;
	JTextField txtCompany;

	private _JLookup lookupProject; 
	private _JXTextField txtProject;

	private _JLookup lookupPhase;
	private _JXTextField txtPhase;

	private JComboBox cmbEPEBType;
	private _JXTextField txtJVNo;

	JTextField txtBatch;

	protected Boolean pressedShift = false;

	static String title = "G to G Tcost Tagging";
	Dimension frameSize = new Dimension(700, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTableMain tblG2GTCostTagging;

	public TCost_G2G() {
		super(title, false, true, false, true);
		initGUI();
	}

	public TCost_G2G(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public TCost_G2G(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setVisible(true);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			// pnlMain.setPreferredSize(new java.awt.Dimension(798, 85));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setBorder(null);
				pnlNorth.setPreferredSize(new java.awt.Dimension(1000, 150));
				{
					pnlGenerateDetails = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlGenerateDetails, BorderLayout.CENTER);
					pnlGenerateDetails.setBorder(JTBorderFactory.createTitleBorder("Details"));

					// File Upload Labels
					JPanel pnlLabels = new JPanel(new BorderLayout(3, 3));
					{
						pnlLabels = new JPanel(new GridLayout(5, 1, 10, 3));
						pnlGenerateDetails.add(pnlLabels, BorderLayout.WEST);
						pnlLabels.add(new JLabel("Company"));
						pnlLabels.add(new JLabel("Project"));
						pnlLabels.add(new JLabel("Phase"));
						pnlLabels.add(new JLabel("EPEB Type"));
						pnlLabels.add(new JLabel("Batch No"));
					}

					// File Upload Center
					{
						pnlUploading = new JPanel(new GridLayout(5, 1, 10, 3));
						pnlGenerateDetails.add(pnlUploading, BorderLayout.CENTER);

						// Project
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
							pnlUploading.add(pnlCompany);
							{
								lookupCompany = new _JLookup();
								pnlCompany.add(lookupCompany, BorderLayout.WEST);
								lookupCompany.setPreferredSize(new Dimension(50, 100));
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
								lookupCompany.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String company_name = (String) data[1];
											txtCompany.setText(company_name);
											lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));
										}
									}
								});
							}

							{
								txtCompany = new JTextField();
								pnlCompany.add(txtCompany, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlProj = new JPanel(new BorderLayout(3, 3));
							pnlUploading.add(pnlProj);
							{
								lookupProject = new _JLookup();
								pnlProj.add(lookupProject, BorderLayout.WEST);
								lookupProject.setPreferredSize(new Dimension(50, 100));
								lookupProject.setReturnColumn(0);
								lookupProject.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String proj_name = (String) data[1];
											txtProject.setText(proj_name);
											lookupPhase.setLookupSQL(SQL_PHASE_ALL(data[0].toString()));
										}
									}
								});
							}

							{
								txtProject = new _JXTextField();
								pnlProj.add(txtProject, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
							pnlUploading.add(pnlPhase);
							{
								lookupPhase = new _JLookup();
								pnlPhase.add(lookupPhase, BorderLayout.WEST);
								lookupPhase.setPreferredSize(new Dimension(50, 100));
								lookupPhase.setReturnColumn(0);
								lookupPhase.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String phase = (String) data[1];
											txtPhase.setText(phase);
										}
									}
								});
							}

							{
								txtPhase = new _JXTextField();
								pnlPhase.add(txtPhase, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlEPEBType = new JPanel(new BorderLayout(3, 3));
							pnlUploading.add(pnlEPEBType);
							{
								cmbEPEBType = new JComboBox(new String[] {"Mortgage", "Sale"});
								pnlEPEBType.add(cmbEPEBType, BorderLayout.WEST);
								cmbEPEBType.setPreferredSize(new Dimension(150, 0));

							}
						}
						{
							JPanel pnlBatch = new JPanel(new BorderLayout(3, 3));
							pnlUploading.add(pnlBatch);
							{
								txtBatch = new _JXTextField();
								pnlBatch.add(txtBatch, BorderLayout.WEST);
								txtBatch.setPreferredSize(new Dimension(150, 0));

							}
							{
								JPanel pnlJVNo = new JPanel(new BorderLayout(3, 3));
								pnlBatch.add(pnlJVNo, BorderLayout.EAST);
								pnlJVNo.setPreferredSize(new Dimension(200, 0));
								{
									JLabel lblJVNo = new JLabel("JV No");
									pnlJVNo.add(lblJVNo, BorderLayout.WEST);
									lblJVNo.setPreferredSize(new Dimension(100, 0));
								}
								{
									txtJVNo = new _JXTextField();
									pnlJVNo.add(txtJVNo, BorderLayout.CENTER);
								}
							}
						}
					}
					// File Upload Buttons
				}
				{
					btnGenerate = new JButton("Generate");
					pnlNorth.add(btnGenerate, BorderLayout.EAST);
					btnGenerate.setActionCommand("Generate");
					btnGenerate.setMnemonic(KeyEvent.VK_R);
					btnGenerate.addActionListener(this);
					btnGenerate.setPreferredSize(new java.awt.Dimension(200, 40));
				}
				{
					pnlCenter = new JPanel();
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(5, 5));
					pnlCenter.setBorder(lineBorder);
					{
						JPanel pnlForIssuance = new JPanel(new BorderLayout(3, 3));
						pnlCenter.add(pnlForIssuance);
						{
							scrollG2GTcostTagging = new JScrollPane();
							pnlForIssuance.add(scrollG2GTcostTagging, BorderLayout.CENTER);
							scrollG2GTcostTagging.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							scrollG2GTcostTagging.setBorder(BorderFactory.createLineBorder(Color.GRAY));
							{
								modelG2GTcost = new modelG2GTcostTagging();
								tblG2GTCostTagging = new _JTableMain(modelG2GTcost);
								rowHeaderG2GTcost = tblG2GTCostTagging.getRowHeader();
								scrollG2GTcostTagging.setViewportView(tblG2GTCostTagging);
								tblG2GTCostTagging.hideColumns("JV No", "Entity ID", "Proj ID", "PBL ID", "Seq No");
								modelG2GTcost.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {

										((DefaultListModel)rowHeaderG2GTcost.getModel()).addElement(modelG2GTcost.getRowCount());

										if(modelG2GTcost.getRowCount() == 0){
											rowHeaderG2GTcost.setModel(new DefaultListModel());
										}
									}
								});

							}
							{
								rowHeaderG2GTcost = tblG2GTCostTagging.getRowHeader();
								rowHeaderG2GTcost.setModel(new DefaultListModel());
								scrollG2GTcostTagging.setRowHeaderView(rowHeaderG2GTcost);
								scrollG2GTcostTagging.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
				}
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 9, 3, 3));
					// pnlSouth.setBorder(lineBorder);
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX
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
						btnSave.setMnemonic(KeyEvent.VK_V);
						btnSave.addActionListener(this);
					}
					{
						btnSearch = new JButton("Search");
						pnlSouth.add(btnSearch);
						btnSearch.setActionCommand("Search");
						btnSearch.setMnemonic(KeyEvent.VK_V);
						btnSearch.addActionListener(this);
					}

					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setActionCommand("Preview");
						btnPreview.setMnemonic(KeyEvent.VK_V);
						btnPreview.addActionListener(this);
					}
					{
						btnApprove = new JButton("Approve");
						pnlSouth.add(btnApprove);
						btnApprove.setActionCommand("Approve");
						btnApprove.setMnemonic(KeyEvent.VK_I);
						btnApprove.addActionListener(this);
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
		initializeComponents();
	}//XXX END OF INIT GUI

	private void btnState(Boolean save, Boolean search, Boolean preview, Boolean approve, Boolean cancel) {
		btnSave.setEnabled(save);
		btnSearch.setEnabled(search);
		btnPreview.setEnabled(preview);
		btnApprove.setEnabled(approve);
		btnCancel.setEnabled(cancel);
	}

	private void initializeComponents() {
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupPhase.setValue(null);
		txtPhase.setText("");
		cmbEPEBType.setSelectedIndex(0);
		txtBatch.setText("");
		txtJVNo.setText("");
		modelG2GTcost.clear();
		scrollG2GTcostTagging.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
		btnState(false, true, false, false, false);
	}

	private void generateQualifiedAccounts(String co_id, String proj_id, String phase, String epeb_type, String batch_no) {
		modelG2GTcost.clear();

		pgSelect db = new pgSelect();
		String SQL = "select * from view_qualified_tcost_g2g('"+co_id+"', '"+proj_id+"', '"+phase+"', '"+epeb_type+"' ,'"+batch_no+"');";
		db.select(SQL);

		FncSystem.out("Display SQL for generate", SQL);

		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelG2GTcost.addRow(db.getResult()[x]);
			}
			scrollG2GTcostTagging.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblG2GTCostTagging.getRowCount())));
			tblG2GTCostTagging.packAll();
		}
	}

	private String saveTaggedTcostAccts() {

		String batch_no = "";

		String epeb_type = (String) cmbEPEBType.getSelectedItem();

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<BigDecimal> listTcostAmt = new ArrayList<BigDecimal>();

		for(int x=0; x<modelG2GTcost.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelG2GTcost.getValueAt(x, 0);

			if(isSelected){
				BigDecimal tcost_amt = new BigDecimal("0.00");

				try {
					tcost_amt = (BigDecimal) modelG2GTcost.getValueAt(x, 3);
				} catch (ClassCastException e) {
					

					if (modelG2GTcost.getValueAt(x, 3) instanceof Double) {
						tcost_amt = BigDecimal.valueOf((Double) modelG2GTcost.getValueAt(x, 3));
					}

					if (modelG2GTcost.getValueAt(x, 3) instanceof Long) {
						tcost_amt = BigDecimal.valueOf((Long) modelG2GTcost.getValueAt(x, 3));
					}
				}

				String entity_id = (String) modelG2GTcost.getValueAt(x, 5);
				String proj_id = (String) modelG2GTcost.getValueAt(x, 6);
				String pbl_id = (String) modelG2GTcost.getValueAt(x, 7);
				Integer seq_no = (Integer) modelG2GTcost.getValueAt(x, 8);

				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listTcostAmt.add(tcost_amt);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		String tcost_amt = listTcostAmt.toString().replaceAll("\\[|\\]", "");

		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_tcost_g2g('"+epeb_type+"',ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], ARRAY["+tcost_amt+"]::NUMERIC[] ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		FncSystem.out("Display SQL for saving", SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}
	
	private void approveTaggedBatch(String batch_no) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_create_jv_epeb_g2g('"+batch_no+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);	
	}

	public int getSelectedClients() {
		int count = 0;
		try {
			for (int x  = 0;x<tblG2GTCostTagging.getRowCount();x++){
				if (tblG2GTCostTagging.getValueAt(x, 0).toString().equals("true")){
					count++;
				}
			}		
		} catch (ArrayIndexOutOfBoundsException e) { }
		return count;
	}

	private Boolean toSave() {
		if(getSelectedClients() <= 0) {
			JOptionPane.showMessageDialog(null, "Please select clients to tag","Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}
	
	private String sqlSearch() {
		String SQL = "SELECT distinct on (batch_no) batch_no as \"Batch No\", coalesce(jv_no, '') as \"JV No\", \n"
				+ "date_created::DATE as \"Date Created\"\n"
				+ "from rf_g2g_tcost\n"
				+ "where status_id = 'A'\n"
				+ "ORDER BY batch_no desc";
		
		return SQL;
	}

	private void previewTaggedBatch(String batch_no) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("batch_no", batch_no);
		mapParameters.put("prepared_by", UserInfo.FullName2);

		//FncReport.generateReport("/Reports/rptTcostG2GBatch.jasper", title, mapParameters);
		FncReport.generateReportV2("/Reports/rptTcostG2GBatch.jrxml",title ,mapParameters);
		
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Generate")) {
			generateQualifiedAccounts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), cmbEPEBType.getSelectedItem().toString() ,txtBatch.getText());
			btnState(true, false, false, false, true);
		}

		if(actionCommand.equals("Save")) {
			if(toSave()) {
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					String batch_no = saveTaggedTcostAccts();
					previewTaggedBatch(batch_no);
					initializeComponents();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully saved", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
		if(actionCommand.equals("Search")) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Tagged Accounts", sqlSearch(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			FncSystem.out("Display sql for Search", _OtherRequest.sqlSearch());

			Object[] data = dlg.getReturnDataSet();
			
			try {
				String batch_no = (String) data[0];
				String jv_no = (String) data[1];

				generateQualifiedAccounts("null", "null", "null","", batch_no);
				
				txtBatch.setText(batch_no);
				txtJVNo.setText(jv_no);
				
				if(jv_no.equals("")) {
					btnState(false, true, true, true, true);
				}else {
					btnState(false, false, true, false, true);
				}
			} catch (NullPointerException e2) {
				// TODO: handle exception
			}
			
			
		}
		
		if(actionCommand.equals("Preview")) {
			previewTaggedBatch(txtBatch.getText());
		}
		
		if(actionCommand.equals("Approve")) {
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to approve?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				approveTaggedBatch(txtBatch.getText());
				previewTaggedBatch(txtBatch.getText());
				initializeComponents();
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully approved", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(actionCommand.equals("Cancel")) {
			initializeComponents();
		}

	}

}
