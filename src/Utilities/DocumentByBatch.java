package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupDispatcher;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelDocument_by_Batch;

public class DocumentByBatch extends _JInternalFrame implements ActionListener, _GUI, MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthLabels;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblDocType;
	private JLabel lblPhase;
	private JPanel pnlNorthComponents;
	private JPanel pnlCompany;
	private _JLookup lookupCompany;
	private JPanel pnlCompanyWest;
	private JTextField txtCompany;
	private JPanel pnlCompanyCenter;
	private JPanel pnlProject;
	private JPanel pnlProjectWest;
	private _JLookup lookupProject;
	private JPanel pnlProjectCenter;
	private JTextField txtProject;
	private JPanel pnlDocType;
	private JPanel pnlDocTypeWest;
	private _JLookup lookupDocType;
	private JPanel pnlDocTypeCenter;
	private JTextField txtDocType;
	private JPanel pnlPhase;
	private JPanel pnlPhaseWest;
	private JPanel pnlCenter;
	private Border loweredetched;
	private static JComboBox cmbPhase;
	private JPanel pnlCenterLabels;
	private JLabel lblLocation;
	private JLabel lblDate;
	private JLabel lblDoneBy;
	private JLabel lblRecipient;
	private JLabel lblRemarks;
	private JPanel pnlCenterComponents;
	private JPanel pnlLocation;
	private JPanel pnlCenterNorth;
	private JPanel pnlLocationWest;
	private static _JLookup lookupLocation;
	private JPanel pnlLocationCenter;
	private JTextField txtLocation;
	private JPanel pnlCenterSouth;
	private JPanel pnlDate;
	private JPanel pnlDateWest;
	private static _JDateChooser dteSetup;
	private JPanel pnlDoneBy;
	private JPanel pnlDoneByWest;
	private static _JLookup lookupDoneBy;
	private JPanel pnlDoneByCenter;
	private JTextField txtDoneBy;
	private JPanel pnlRecipient;
	private JPanel pnlRecipientWest;
	private static _JLookup lookupRecipient;
	private JPanel pnlRecipientCenter;
	private JTextField txtRecipient;
	private JTextField txtRemarks;
	private static JScrollPane scrollBatch;
	private modelDocument_by_Batch modelBatch;
	private static _JTableMain tblBatch;
	private JList rowHeaderBatch;
	private JPanel pnlSouth;
	private JButton btnGenerate;
	private JButton btnSave;
	private JButton btnCancel;
	private pgUpdate db;

	public DocumentByBatch(){
		super("Document Status by Batch", false, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout(10,10));
		this.setSize(new Dimension(600,600));
		this.setPreferredSize(new Dimension(600,500));
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			{
				pnlNorth = new JPanel (new BorderLayout(5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0,105));
				{
					pnlNorthLabels = new JPanel(new GridLayout(4,1,3,3)); 
					pnlNorth.add(pnlNorthLabels, BorderLayout.WEST);
					pnlNorthLabels.setPreferredSize(new Dimension(80,0));
					{
						lblCompany = new JLabel("Company");
						pnlNorthLabels.add(lblCompany);
					}
					{
						lblProject = new JLabel("Project");
						pnlNorthLabels.add(lblProject);
					}
					{
						lblDocType = new JLabel("Doc Type");
						pnlNorthLabels.add(lblDocType);
					}
					{
						lblPhase = new JLabel("Phase");
						pnlNorthLabels.add(lblPhase);
					}
				}
				{
					pnlNorthComponents = new JPanel(new GridLayout(4,1,3,3));
					pnlNorth.add(pnlNorthComponents, BorderLayout.CENTER);
					{
						pnlCompany = new JPanel(new BorderLayout(3,3));
						pnlNorthComponents.add(pnlCompany);
						{
							pnlCompanyWest = new JPanel(new BorderLayout(3,3));
							pnlCompany.add(pnlCompanyWest, BorderLayout.WEST);
							pnlCompanyWest.setPreferredSize(new Dimension(50,0));
							{
								lookupCompany = new _JLookup(null, "Select company");
								pnlCompanyWest.add(lookupCompany);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setReturnColumn(0);
								lookupCompany.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											
											txtCompany.setText((String) data[1]);
											lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));
											
										}
										
									}
								});
							}
							{
								pnlCompanyCenter = new JPanel(new BorderLayout(3,3));
								pnlCompany.add(pnlCompanyCenter, BorderLayout.CENTER);
								{
									txtCompany = new JTextField();
									pnlCompanyCenter.add(txtCompany);
									txtCompany.setEditable(false);
								}
							}
						}
					}
					{
						pnlProject = new JPanel(new BorderLayout(3,3));
						pnlNorthComponents.add(pnlProject);
						{
							pnlProjectWest = new JPanel(new BorderLayout(3,3));
							pnlProject.add(pnlProjectWest, BorderLayout.WEST);
							pnlProjectWest.setPreferredSize(new Dimension(50,0));
							{
								lookupProject = new _JLookup(null, "Select Project");
								pnlProjectWest.add(lookupProject);
								lookupProject.setReturnColumn(0);
								lookupProject.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											
											txtProject.setText((String) data[1]);
										}
									}
								});
							}
							{
								pnlProjectCenter = new JPanel(new BorderLayout(3,3));
								pnlProject.add(pnlProjectCenter, BorderLayout.CENTER);
								{
									txtProject = new JTextField();
									pnlProjectCenter.add(txtProject);
									txtProject.setEditable(false);
								}
							}
						}
					}
					{
						pnlDocType = new JPanel(new BorderLayout(3,3));
						pnlNorthComponents.add(pnlDocType);
						{
							pnlDocTypeWest = new JPanel(new BorderLayout(3,3));
							pnlDocType.add(pnlDocTypeWest, BorderLayout.WEST);
							pnlDocTypeWest.setPreferredSize(new Dimension(50,0));
							{
								lookupDocType = new _JLookup(null, "Select Doc Type");
								pnlDocTypeWest.add(lookupDocType);
								lookupDocType.setLookupSQL(SQL_Doctype());
								lookupDocType.setReturnColumn(0);
								lookupDocType.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											txtDocType.setText((String) data[1]);
											btnGenerate.setEnabled(true);
										}
										
									}
								});
							}
							{
								pnlDocTypeCenter = new JPanel(new BorderLayout(3,3));
								pnlDocType.add(pnlDocTypeCenter, BorderLayout.CENTER);
								{
									txtDocType = new JTextField();
									pnlDocTypeCenter.add(txtDocType);
									txtDocType.setEditable(false);
								}
							}
						}
					}
					{
						pnlPhase = new JPanel(new BorderLayout(3,3));
						pnlNorthComponents.add(pnlPhase);
						{
							pnlPhaseWest = new JPanel(new BorderLayout(3,3));
							pnlPhase.add(pnlPhaseWest, BorderLayout.WEST);
							pnlPhaseWest.setPreferredSize(new Dimension(70,0));
							{
								String phase[] = {"All", "1", "2", "3", "4-A", "4", "5", "1-B"};
								cmbPhase = new JComboBox(phase);
								pnlPhaseWest.add(cmbPhase);
							}
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
				pnlCenter.setBorder(BorderFactory.createTitledBorder(loweredetched, ""));
				{
					pnlCenterNorth = new JPanel(new BorderLayout(3,3));
					pnlCenter.add(pnlCenterNorth, BorderLayout.CENTER);
					pnlCenterNorth.setPreferredSize(new Dimension(0,50));
					pnlCenterNorth.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
					{
						pnlCenterLabels = new JPanel(new GridLayout(5,1,3,3));
						pnlCenterNorth.add(pnlCenterLabels, BorderLayout.WEST);
						pnlCenterLabels.setPreferredSize(new Dimension(100,0));
						{
							lblLocation = new JLabel("Doc Location");
							pnlCenterLabels.add(lblLocation);
						}
						{
							lblDate =  new JLabel("Setup Date");
							pnlCenterLabels.add(lblDate);
						}
						{
							lblDoneBy = new JLabel("Done By");
							pnlCenterLabels.add(lblDoneBy);
						}
						{
							lblRecipient = new JLabel("Recipient");
							pnlCenterLabels.add(lblRecipient);
						}
						{
							lblRemarks = new JLabel("Remarks");
							pnlCenterLabels.add(lblRemarks);
						}
					}
					{
						pnlCenterComponents = new JPanel(new GridLayout(5,1,3,3));
						pnlCenterNorth.add(pnlCenterComponents, BorderLayout.CENTER);
						{
							pnlLocation = new JPanel(new BorderLayout(3,3));
							pnlCenterComponents.add(pnlLocation);
							{
								pnlLocationWest = new JPanel(new BorderLayout(3,3));
								pnlLocation.add(pnlLocationWest, BorderLayout.WEST);
								pnlLocationWest.setPreferredSize(new Dimension(120,0));
								{
									lookupLocation = new _JLookup(null, "Location");
									pnlLocationWest.add(lookupLocation);
									lookupLocation.setLookupSQL(DocLocation());
									lookupLocation.setReturnColumn(0);
									lookupLocation.setFilterIndex(1);
									lookupLocation.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											// TODO Auto-generated method stub
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null){
												txtLocation.setText((String) data[1]);
											}
										}
									});
								}
								{
									pnlLocationCenter = new JPanel(new BorderLayout(3,3));
									pnlLocation.add(pnlLocationCenter, BorderLayout.CENTER);
									{
										txtLocation = new JTextField();
										pnlLocationCenter.add(txtLocation);
										txtLocation.setEditable(false);
									}
								}
							}
						}
						{
							pnlDate = new JPanel(new BorderLayout(3,3));
							pnlCenterComponents.add(pnlDate);
							{
								pnlDateWest = new JPanel(new BorderLayout(3,3));
								pnlDate.add(pnlDateWest, BorderLayout.WEST);
								pnlDateWest.setPreferredSize(new Dimension(120,0));
								{
									dteSetup = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlDateWest.add(dteSetup, BorderLayout.CENTER);
									dteSetup.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
							
						}
						{
							pnlDoneBy = new JPanel(new BorderLayout(3,3));
							pnlCenterComponents.add(pnlDoneBy);
							{
								pnlDoneByWest = new JPanel(new BorderLayout(3,3));
								pnlDoneBy.add(pnlDoneByWest, BorderLayout.WEST);
								pnlDoneByWest.setPreferredSize(new Dimension(120,0));
								{
									lookupDoneBy = new _JLookup(null, "Name");
									pnlDoneByWest.add(lookupDoneBy);
									lookupDoneBy.setLookupSQL(Entity());
									lookupDoneBy.setReturnColumn(0);
									lookupDoneBy.setFilterIndex(1);
									lookupDoneBy.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											// TODO Auto-generated method stub
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null){
												txtDoneBy.setText((String) data[1]);
											}
										}
									});
								}
								{
									pnlDoneByCenter = new JPanel(new BorderLayout(3,3));
									pnlDoneBy.add(pnlDoneByCenter, BorderLayout.CENTER);
									{
										txtDoneBy = new JTextField();
										pnlDoneByCenter.add(txtDoneBy);
										txtDoneBy.setEditable(false);
									}
								}
							}
						}
						{
							pnlRecipient = new JPanel(new BorderLayout(3,3));
							pnlCenterComponents.add(pnlRecipient);
							{
								pnlRecipientWest = new JPanel(new BorderLayout(3,3));
								pnlRecipient.add(pnlRecipientWest, BorderLayout.WEST);
								pnlRecipientWest.setPreferredSize(new Dimension(120,0));
								{
									lookupRecipient = new _JLookup(null, "Recipient");
									pnlRecipientWest.add(lookupRecipient);
									lookupRecipient.setLookupSQL(Entity());
									lookupRecipient.setReturnColumn(0);
									lookupRecipient.setFilterIndex(1);
									lookupRecipient.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											// TODO Auto-generated method stub
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null){
												txtRecipient.setText((String) data[1]);
											}
										}
									});
								}
								{
									pnlRecipientCenter = new JPanel(new BorderLayout(5,5));
									pnlRecipient.add(pnlRecipientCenter, BorderLayout.CENTER);
									{
										txtRecipient = new JTextField();
										pnlRecipientCenter.add(txtRecipient);
										txtRecipient.setEditable(false);
									}
								}
							}
						}
						{
							txtRemarks = new JTextField();
							pnlCenterComponents.add(txtRemarks);
						}
					}
				}
				{
					pnlCenterSouth = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlCenterSouth, BorderLayout.SOUTH);
					pnlCenterSouth.setPreferredSize(new Dimension(0,250));
					{
						scrollBatch = new JScrollPane();
						pnlCenterSouth.add(scrollBatch);
						scrollBatch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					}
					{
						modelBatch = new modelDocument_by_Batch();
						tblBatch = new _JTableMain(modelBatch);
						
						scrollBatch.setViewportView(tblBatch);
						tblBatch.addMouseListener(this);
						tblBatch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						//tblBatch.getColumn(0).setPreferredWidth(80);
						tblBatch.getColumn(1).setPreferredWidth(150);
						//tblBatch.getColumn(2).setPreferredWidth(130);
						//tblBatch.getColumn(3).setPreferredWidth(70);
						//tblBatch.getColumn(4).setPreferredWidth(150);
						//tblBatch.getColumn(5).setPreferredWidth(100);
						tblBatch.getTableHeader().setEnabled(false);
					}
					{
						rowHeaderBatch = tblBatch.getRowHeader();
						rowHeaderBatch .setModel(new DefaultListModel());
						scrollBatch.setRowHeaderView(rowHeaderBatch);
						scrollBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1,3,3,3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));
				{
					btnGenerate = new JButton("Generate");
					pnlSouth.add(btnGenerate);
					btnGenerate.addActionListener(this);
					btnGenerate.setActionCommand("Generate");
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.addActionListener(this);
					btnSave.setActionCommand("Save");
					btnSave.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.addActionListener(this);
					btnCancel.setActionCommand("Cancel");
				}
			}
		}
		enableCenterFields(false);
		initialize_comp();
	}
	
	private static String SQL_Doctype(){
		String SQL =
				"select doc_id as \"Doc ID\", trim(doc_desc) as \"Doc Desc\" from mf_system_doc where tct_taxdec_doc = true and status_id = 'A' order by doc_desc";
		
		FncSystem.out("SQL description for Document Type!", SQL);
		
		return SQL;
		
	}
	
	private static String DocLocation() {
		String sql = "select trim(status_code) as \"ID\", trim(status_desc) as \"Name\", trim(Status_alias) as \"Alias\"\n" + 
				"from mf_tct_taxdec_status \n" + 
				"where status_id = 'A'\n" + 
				"and status_type = 'L'\n" + 
				"order by trim(status_desc);";
		return sql;
	}
	
	private static String Entity() {
		String sql = "select trim(entity_id) as \"ID\", trim(entity_name) as \"Name\", trim(entity_kind) as \"Kind\"\n" + 
				"from rf_entity \n" + 
				"where status_id = 'A'\n" + 
				//"group by trim(a.entity_id), trim(b.entity_name), trim(b.entity_kind)\n" + 
				"order by trim(entity_name);";
		return sql;
	}
	
	public void actionPerformed (ActionEvent e){
		
		if(e.getActionCommand().equals("Generate")){
			generate();
		}
		
		if(e.getActionCommand().equals("Cancel")){
			cancel();
		}
		
		if(e.getActionCommand().equals("Save")){
			save();
		}
	}

	private void save() {
		if(checkLocation()==false){
			JOptionPane.showMessageDialog(getContentPane(), "Please select a location.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			if(checkDoneBy()==false){
				JOptionPane.showMessageDialog(getContentPane(), "Please select an employee.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				executeSave();
			}
		}
	}

	private void executeSave() {
		db = new pgUpdate();
		if(JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			
			if(tblBatch.getSelectedRows().length > 0){
				String doc_type = lookupDocType.getValue();
				String doc_status = lookupLocation.getValue();
				java.util.Date status = dteSetup.getDate();
				String done_by = lookupDoneBy.getValue();
				String recipient = lookupRecipient.getValue();
				String remarks = txtRemarks.getText();
				
				for(int x = 0; x < modelBatch.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelBatch.getValueAt(x, 0);
					String doc_no = (String) modelBatch.getValueAt(x, 1);
					String pbl_id = (String) modelBatch.getValueAt(x, 6);
					
					if(isSelected){
						
						String SQL =
								"insert into rf_tct_taxdec_monitoring_dl (\n" + 
								"pbl_id, doc_type, doc_no, doc_status, status_date, done_by, recipient, remarks, bank_id, status_id, created_by, date_created)\n" + 
								"values(\n" +
								"'"+pbl_id+"', \n" +
								"'"+doc_type+"', \n" +
								"'"+doc_no+"', \n" +
								"'"+doc_status+"', \n" +
								"'"+status+"', \n" +
								"'"+done_by+"', \n" +
								"'"+recipient+"', \n" +
								"'"+remarks+"', \n" +
								"null, \n" +
								"'A', \n" +
								"'"+UserInfo.EmployeeCode+"', \n" +
								"now())";
						
						FncSystem.out("SQL description on Saving TCT Details", SQL);
						db.executeUpdate(SQL, false);
					}
				}
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Successfully saved!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void cancel() {
		if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to clear all fields?", "Cancel",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			
			JOptionPane.showMessageDialog(getContentPane(), "All fields cleared.", "Cancel", JOptionPane.INFORMATION_MESSAGE);

			enableCenterFields(false);
			enableNorthFields(true);
			refreshFields();
			initialize_comp();

			scrollBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBatch.getRowCount())));
		}
	}

	private void generate() {
		String emp_code = UserInfo.EmployeeCode;
		String emp_name = (String) UserInfo.FullName;
		String doc_type = lookupDocType.getValue().toString();
		String phase = String.valueOf(cmbPhase.getSelectedItem().toString());
		String co_id = lookupCompany.getValue();
		String proj_id = lookupProject.getValue();
		
		if(doc_type.equals("") || doc_type.equals(null)){
			JOptionPane.showMessageDialog(getContentPane(), "Please select a document type!", "Error", JOptionPane.ERROR_MESSAGE);
		}else{
			
			displayTCTDetails(modelBatch, rowHeaderBatch, doc_type, phase, co_id, proj_id);
			
			enableCenterFields(true);
			enableNorthFields(false);
			lookupDoneBy.setValue(emp_code);
			txtDoneBy.setText(emp_name);
			//btnSave.setEnabled(true);
			btnGenerate.setEnabled(false);
		}
	}
	
	private void enableNorthFields(Boolean x){
		
		lookupCompany.setEnabled(x);
		lookupProject.setEnabled(x);
		lookupDocType.setEnabled(x);
		cmbPhase.setEnabled(x);
		
	}
	
	private void enableCenterFields(Boolean x){
		
		lookupLocation.setEnabled(x);
		dteSetup.setEnabled(x);
		lookupDoneBy.setEnabled(x);
		lookupRecipient.setEnabled(x);
		txtRemarks.setEnabled(x);;
		
	}
	
	private void refreshFields(){
		
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupDocType.setValue(null);
		txtDocType.setText("");
		cmbPhase.setSelectedIndex(0);
		lookupLocation.setValue(null);
		txtLocation.setText("");
		dteSetup.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		lookupDoneBy.setValue(null);
		txtDoneBy.setText("");
		lookupRecipient.setValue(null);
		txtRecipient.setText("");
		txtRemarks.setText("");
		modelBatch.clear();
	}
	
	private static void displayTCTDetails(modelDocument_by_Batch model, JList rowHeader, String doc_type, String phase, String co_id, String proj_id){
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		int index = cmbPhase.getSelectedIndex();
		
		String SQL = 
				"select\n" + 
				"false,\n" + 
				"doc_no,\n" + 
				"b.proj_alias,\n" + 
				"(case when c.phase is null then d.phase else c.phase end) as \"ph\",\n" + 
				"(case when c.block is null then d.block else c.block end) as \"blk\",\n" + 
				"(case when c.lot is null then d.lot else c.lot end) as \"lt\",\n" + 
				"a.pbl_id\n" + 
				"from rf_tct_taxdec_monitoring_hd a\n" + 
				"left join mf_project b on a.proj_id = b.proj_id \n" + 
				"left join mf_unit_info c on a.pbl_id = c.pbl_id and a.proj_id = c.proj_id and coalesce(a.server_id, '') = coalesce(c.server_id, '')\n" + 
				"left join mf_unit_info_pending d on a.pbl_id = d.pbl_id and a.proj_id = d.proj_id\n" + 
				"where a.status_id = 'A'\n" + 
				"and a.co_id = '"+co_id+"'\n" + 
				"and a.proj_id = '"+proj_id+"'\n" +  
				"and doc_type = '"+doc_type+"'\n"; 
				
				if(index == 1 || index == 2 || index == 3 || index == 4 || index == 5){
					{SQL = SQL + "and (c.phase = '"+phase+"' or d.phase = '"+phase+"')\n";} 
				}else{}
				
				SQL = SQL +
		
				"order by doc_no ASC";
				
		FncSystem.out("SQL Description for TCT Details!", SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
		tblBatch.packAll();
		scrollBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBatch.getRowCount())));
	}
	
	private static Boolean checkLocation(){
		
		Boolean x = false;
		
		String doc_location = lookupLocation.getText();
		
		if(doc_location.equals("")){
			x=false;
		}else{
			x=true;
		}
		
		return x;
		
	}
	
	private static Boolean checkDoneBy(){
		
		Boolean x = false;
		
		String done_by = lookupDoneBy.getText();
		
		if(done_by.equals("")){
			x=false;
		}else{
			x=true;
		}
		
		return x;
		
	}
	
//	private static Boolean checkRecipient(){
//		
//		Boolean x = false;
//		
//		String recipient = lookupRecipient.getText();
//		
//		if(recipient.equals("")){
//			x=false;
//		}else{
//			x=true;
//		}
//		
//		return x;
//		
//	}
	
	private void initialize_comp(){
		
		lookupCompany.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		lookupProject.setValue("015");
		txtProject.setText("TERRAVERDE RESIDENCES");
		btnSave.setEnabled(false);
		btnGenerate.setEnabled(false);
		
		String co_id = lookupCompany.getValue();
		lookupProject.setLookupSQL(SQL_PROJECT(co_id));
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(tblBatch)){
			for(int x = 0; x < modelBatch.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelBatch.getValueAt(x, 0);
				
				if(isSelected){
					btnSave.setEnabled(true);
				}
			}
		}
		
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
