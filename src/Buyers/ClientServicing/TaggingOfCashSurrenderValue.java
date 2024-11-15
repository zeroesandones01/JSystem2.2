package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel.model_BankPayments;
import tablemodel.model_tagging_of_csv;

public class TaggingOfCashSurrenderValue extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * @author Jari Cruz
	 */
	private static final long serialVersionUID = 2614798161941904171L;
	
	private static String title = "Tagging of Cash Surrender Value"; 
	static Dimension SIZE = new Dimension(700, 500);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JLookup lookupClient;

	private JTextField txtProject;

	private JTextField txtUnit;
	private JTextField txtSeq;
	private JTextField txtBatch;

	private JTextField txtProductModel;

	private JTextField txtRemarks;

	private model_tagging_of_csv model_tagging_of_csv;

	private _JTableMain tableCSV;

	private JList rowheaderCSV;
	
	Object[] clientDetails;

	private JLabel lblProductModel;

	private JLabel lblActualDate;

	private JLabel lblClient;

	private JLabel lblClientDesc;

	private JLabel lblClientName = new JLabel();;

	private JLabel lblClientUnit = new JLabel();;

	private JLabel lblProjectDesc;

	private JLabel lblUnitSeqBatch;

	private JLabel lblProject;

	private String batch_no;

	private String pbl;

	private String entity;

	private String projid;

	private Integer seq;

	private JLabel lblProductModelDesc;

	private JLabel lblUnitDesc;

	private JLabel lblTransDate;

	private JLabel lblRequestType;
	
	private JLabel lblRequestDesc;
	
	private JLabel lblRequestDescConf = new JLabel();;

	private _JLookup lookupRequest;

	private _JDateChooser dteTransDate;

	private _JDateChooser dteActualDate;

	private JButton btnAdd;

	private JButton btnDelete;

	private JButton btnEdit;

	private JButton btnSave;

	private JButton btnCancel;
	
	public TaggingOfCashSurrenderValue() {
		super(title, true, true, true, true);
		initGUI();
	}

	public TaggingOfCashSurrenderValue(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public TaggingOfCashSurrenderValue(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		/*NORTH*/
		{
			JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			/*Client Information*/
			{
				JPanel pnlClientInformation = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlNorth.add(pnlClientInformation, BorderLayout.NORTH);
				pnlClientInformation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Client Information"));
				{
					{
						JPanel pnlLookupClient = new JPanel(new BorderLayout(5, 3));
						pnlClientInformation.add(pnlLookupClient);
						lblClient = new JLabel("Client");
						pnlLookupClient.add(lblClient, BorderLayout.WEST);
						lblClient.setPreferredSize(new Dimension(100, 15));
						lblClient.setAlignmentX(RIGHT_ALIGNMENT);
						{
							JPanel pnlDetails = new JPanel(new BorderLayout(5, 5));
							pnlLookupClient.add(pnlDetails, BorderLayout.CENTER);
							{
								lookupClient = new _JLookup(null, "Select Client", 1);
								pnlDetails.add(lookupClient, BorderLayout.WEST);
								lookupClient.setLookupSQL(lookupClients());
								lookupClient.setFilterCardName(true);
								lookupClient.setPreferredSize(new Dimension(100, 0));
								lookupClient.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											lblClientDesc.setText(generateTAG(data[2]));
											lblClientName.setText((String) data[2]);
											lblClientUnit.setText((String) data[7]);
											txtProject.setText((String) data[3]);
											lblProjectDesc.setText(generateTAG(data[4]));
											txtUnit.setText((String) data[5]);
											txtSeq.setText(Integer.toString((int) data[6]));
											lblUnitDesc.setText(generateTAG(data[7]));
											txtProductModel.setText((String) data[8]);
											lblProductModelDesc.setText(generateTAG(data[9]));
											
											displayTaggingOfCsv((String) data[1], (String) data[3], (String) data[5], (int) data[6]);
											
											fncButtonEnabler(true, false, false, false, true);
										}
									}
									
								});
							}
							{
								JPanel pnlLabels = new JPanel(new BorderLayout());
								pnlDetails.add(pnlLabels, BorderLayout.CENTER);
								pnlLabels.setPreferredSize(new Dimension(100, 0));
								lblClientDesc = new JLabel(generateTAG(""));
								pnlLabels.add(lblClientDesc);
							}
						}
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(5, 3));
						pnlClientInformation.add(pnlProject);
						lblProject = new JLabel("Project");
						pnlProject.add(lblProject, BorderLayout.WEST);
						lblProject.setPreferredSize(new Dimension(100, 15));
						{
							JPanel pnlDetails = new JPanel(new BorderLayout(5, 5));
							pnlProject.add(pnlDetails, BorderLayout.CENTER);
							{
								txtProject = new JTextField();
								pnlDetails.add(txtProject, BorderLayout.WEST);
								txtProject.setPreferredSize(new Dimension(100, 0));
							}
							{
								JPanel pnlLabels = new JPanel(new BorderLayout());
								pnlDetails.add(pnlLabels, BorderLayout.CENTER);
								pnlLabels.setPreferredSize(new Dimension(100, 0));
								lblProjectDesc = new JLabel(generateTAG(""));
								pnlLabels.add(lblProjectDesc);
							}
						}
					}
					{
						JPanel pnlUnitSeqBatch = new JPanel(new BorderLayout(5, 5));
						pnlClientInformation.add(pnlUnitSeqBatch);
						lblUnitSeqBatch = new JLabel("Unit/Seq/Batch");
						pnlUnitSeqBatch.add(lblUnitSeqBatch, BorderLayout.WEST);
						lblUnitSeqBatch.setPreferredSize(new Dimension(100, 15));
						{
							JPanel pnlDetails = new JPanel(new BorderLayout(5, 5));
							pnlUnitSeqBatch.add(pnlDetails, BorderLayout.CENTER);
							{
								JPanel pnlTextFields = new JPanel(new BorderLayout());
								pnlDetails.add(pnlTextFields, BorderLayout.WEST);
								pnlTextFields.setPreferredSize(new Dimension(100, 0));
								txtUnit = new JTextField();
								txtUnit.setPreferredSize(new Dimension(60, 0));
								pnlTextFields.add(txtUnit, BorderLayout.WEST);
								txtSeq = new JTextField();
								txtSeq.setPreferredSize(new Dimension(20, 0));
								pnlTextFields.add(txtSeq, BorderLayout.CENTER);
								txtBatch = new JTextField();
								txtBatch.setPreferredSize(new Dimension(20, 0));
								pnlTextFields.add(txtBatch, BorderLayout.EAST);
							}
							{

								
								JPanel pnlLabels = new JPanel(new BorderLayout());
								pnlDetails.add(pnlLabels, BorderLayout.CENTER);
								pnlLabels.setPreferredSize(new Dimension(100, 0));
								lblUnitDesc = new JLabel(generateTAG(""));
								pnlLabels.add(lblUnitDesc);
							}
						}
					}
					{
						JPanel pnlProductModel = new JPanel(new BorderLayout(5, 5));
						pnlClientInformation.add(pnlProductModel);
						lblProductModel = new JLabel("Product Model");
						pnlProductModel.add(lblProductModel, BorderLayout.WEST);
						lblProductModel.setPreferredSize(new Dimension(100, 15));
						{
							JPanel pnlDetails = new JPanel(new BorderLayout(5, 5));
							pnlProductModel.add(pnlDetails, BorderLayout.CENTER);
							{
								txtProductModel = new JTextField();
								pnlDetails.add(txtProductModel, BorderLayout.WEST);
								txtProductModel.setPreferredSize(new Dimension(100, 0));
							}
							{
								JPanel pnlLabels = new JPanel(new BorderLayout());
								pnlDetails.add(pnlLabels, BorderLayout.CENTER);
								pnlLabels.setPreferredSize(new Dimension(100, 0));
								lblProductModelDesc = new JLabel(generateTAG(""));
								pnlLabels.add(lblProductModelDesc);
							}
						}
					}
				}
			}
			/*Inputs*/
			{
				JPanel pnlInputs = new JPanel(new BorderLayout(3, 3));
				pnlNorth.add(pnlInputs, BorderLayout.SOUTH);
				pnlInputs.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"INPUTS"));
				{
					JPanel pnlInputsMain = new JPanel(new BorderLayout(3, 3));
					pnlInputs.add(pnlInputsMain, BorderLayout.WEST);
					pnlInputsMain.setPreferredSize(new Dimension(650, 36));
					{
						JPanel pnlInputsWest = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlInputsMain.add(pnlInputsWest, BorderLayout.WEST);
						pnlInputsWest.setPreferredSize(new Dimension(233, 36));
						{
							{
								JPanel pnlTransDate = new JPanel(new BorderLayout(5, 3));
								pnlInputsWest.add(pnlTransDate);
								lblTransDate = new JLabel("Trans Date");
								pnlTransDate.add(lblTransDate, BorderLayout.WEST);
								lblTransDate.setPreferredSize(new Dimension(100, 15));
								{
									dteTransDate = new _JDateChooser();
									pnlTransDate.add(dteTransDate, BorderLayout.CENTER);
									dteTransDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
							{
								JPanel pnlActualDate = new JPanel(new BorderLayout(5, 3));
								pnlInputsWest.add(pnlActualDate);
								lblActualDate = new JLabel("Actual Date");
								pnlActualDate.add(lblActualDate, BorderLayout.WEST);
								lblActualDate.setPreferredSize(new Dimension(100, 15));
								{
									dteActualDate = new _JDateChooser();
									pnlActualDate.add(dteActualDate, BorderLayout.CENTER);
									dteActualDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
						}
					}
					{
						JPanel pnlInputsEast = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlInputsMain.add(pnlInputsEast, BorderLayout.EAST);
						pnlInputsEast.setPreferredSize(new Dimension(400, 36));
						{
							{
								JPanel pnlRequestType = new JPanel(new BorderLayout(5, 3));
								pnlInputsEast.add(pnlRequestType);
								lblRequestType = new JLabel("Request Type");
								pnlRequestType.add(lblRequestType, BorderLayout.WEST);
								lblRequestType.setPreferredSize(new Dimension(100, 15));
								{
									JPanel pnlRequestDesc = new JPanel(new BorderLayout(3, 3));
									pnlRequestType.add(pnlRequestDesc, BorderLayout.CENTER);
									{
										
										lookupRequest = new _JLookup(null, "Select Request", 0);
										pnlRequestDesc.add(lookupRequest, BorderLayout.WEST);
										lookupRequest.setLookupSQL(lookupRequest());
										lookupRequest.setPreferredSize(new Dimension(20, 0));
										lookupRequest.addLookupListener(new LookupListener() {

											@Override
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {
													lblRequestDesc.setText(generateTAG(data[1]));
													lblRequestDescConf.setText((String) data[1]);
												}
											}
											
										});
									}
									{
										lblRequestDesc = new JLabel();
										pnlRequestDesc.add(lblRequestDesc, BorderLayout.CENTER);
										lblRequestDesc.setText(generateTAG(""));
									}
								}
							}
							{
								JPanel pnlRemarks = new JPanel(new BorderLayout(5, 3));
								pnlInputsEast.add(pnlRemarks);
								JLabel lblActualDate = new JLabel("Remarks");
								pnlRemarks.add(lblActualDate, BorderLayout.WEST);
								lblActualDate.setPreferredSize(new Dimension(100, 15));
								{
									txtRemarks = new JTextField();
									pnlRemarks.add(txtRemarks, BorderLayout.CENTER);
								}
							}
						}
					}
				}
			}
		}
		
		/*SOUTH*/
		{
			JPanel pnlTable = new JPanel(new BorderLayout(3, 3));
			pnlMain.add(pnlTable, BorderLayout.CENTER);
			{
				_JScrollPaneMain scroll = new _JScrollPaneMain();
				pnlTable.add(scroll, BorderLayout.CENTER);
				{
					model_tagging_of_csv = new model_tagging_of_csv();
					tableCSV = new _JTableMain(model_tagging_of_csv);
					scroll.setViewportView(tableCSV);
					scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					tableCSV.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tableCSV.setSortable(false);
					{
						JList rowHeader = tableCSV.getRowHeader();
						rowHeader.setModel(new DefaultListModel());
						scroll.setRowHeaderView(rowHeader);
						scroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						rowheaderCSV = tableCSV.getRowHeader();
						scroll.setRowHeaderView(rowheaderCSV);
						scroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				tableCSV.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						if(tableCSV.isEnabled()) {
							fncSelectedRow();
						}
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			{
				JPanel pnlButtons = new JPanel(new GridLayout(1, 5));
				pnlTable.add(pnlButtons, BorderLayout.SOUTH);
				{
					btnAdd = new JButton("Add");
					pnlButtons.add(btnAdd);
					btnAdd.setEnabled(false);
					btnAdd.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							fncAdd();
						}
					});
					
					btnDelete = new JButton("Delete");
					pnlButtons.add(btnDelete);
					btnDelete.setEnabled(false);
					btnDelete.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							fncDelete();
						}
					});
					
					btnEdit = new JButton("Edit");
					pnlButtons.add(btnEdit);
					btnEdit.setEnabled(false);
					btnEdit.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							fncEdit();
						}
					});
					
					btnSave = new JButton("Save");
					pnlButtons.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							fncSave();
						}
					});
					
					btnCancel = new JButton("Cancel");
					pnlButtons.add(btnCancel);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							fncCancel();
						}
					});
				}
			}
		}
	}//XXX END OF INIT GUI

	private void fncAdd() {	
		System.out.println("Add Function");
		String entity_id = lookupClient.getValue();
		String proj_id = txtProject.getText();
		String pbl_id = txtUnit.getText();
		Integer seq_no = Integer.parseInt(txtSeq.getText());
		String trans_date = dteTransDate.getDateString();
		String actual_date = dteActualDate.getDateString();
		String request_type = lookupRequest.getValue();
		String remarks = txtRemarks.getText();
		String created_by = UserInfo.EmployeeCode;
		String SQL = "select sp_csd_upsert('ADD', '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+trans_date+"', '"+actual_date+"', '"+request_type+"', '"+remarks+"', '"+created_by+"', '')";
		FncSystem.out("fncAdd", SQL);
		
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Add Request for " + lblClientName.getText() + " ?\n"
				+ "Trans Date: "+trans_date+"\n"
				+ "Actual Date: "+actual_date+"\n"
				+ "Request Type: "+lblRequestDescConf.getText()+"\n"
				+ "Remarks: "+remarks+"\n");
		
		if(confirmation == 0) {
			fncAction(SQL);
			displayTaggingOfCsv(entity_id, proj_id, pbl_id, seq_no);
		} else {
			System.out.println("Cancelled");
		}
	}
	
	private void fncDelete() {		
		System.out.println("Delete Function");

		String entity_id = lookupClient.getValue();
		String proj_id = txtProject.getText();
		String pbl_id = txtUnit.getText();
		Integer seq_no = Integer.parseInt(txtSeq.getText());
		String trans_date = dteTransDate.getDateString();
		String actual_date = dteActualDate.getDateString();
		String request_type = lookupRequest.getValue();
		String remarks = txtRemarks.getText();
		String created_by = UserInfo.EmployeeCode;
		
		int selected_row = tableCSV.getSelectedRow();
		String request_no = (String) tableCSV.getValueAt(selected_row, 5);
		
		String SQL = "select sp_csd_upsert('DELETE', '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+trans_date+"', '"+actual_date+"', '"+request_type+"', '"+remarks+"', '"+created_by+"', '"+request_no+"')";
		FncSystem.out("fncDelete", SQL);
		
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Delete Request for " + lblClientName.getText() + " ?\n"
				+ "Request No.: " + request_no + "\n"
				+ "Remarks: " + (String) tableCSV.getValueAt(selected_row, 2) + "\n");
		
		if(confirmation == 0) {
			fncAction(SQL);
			displayTaggingOfCsv(entity_id, proj_id, pbl_id, seq_no);
			fncButtonEnabler(true, false, false, false, true);
		} else {
			System.out.println("Cancelled");
		}
	}
	
	private void fncEdit() {		
		int selected_row = tableCSV.getSelectedRow();
		String request_no = (String) tableCSV.getValueAt(selected_row, 5);
		
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Edit Request for " + lblClientName.getText() + " ?\n"
				+ "Request No.: " + request_no + "\n");
		
		if(confirmation == 0) {
			fncEditSetup(selected_row);
		} else {
			System.out.println("Cancelled");
		}
	}
	
	private void fncSave() {		
		System.out.println("Save Function");
		String entity_id = lookupClient.getValue();
		String proj_id = txtProject.getText();
		String pbl_id = txtUnit.getText();
		Integer seq_no = Integer.parseInt(txtSeq.getText());
		String trans_date = dteTransDate.getDateString();
		String actual_date = dteActualDate.getDateString();
		String request_type = lookupRequest.getValue();
		String remarks = txtRemarks.getText();
		String created_by = UserInfo.EmployeeCode;
		
		int selected_row = tableCSV.getSelectedRow();
		String request_no = (String) tableCSV.getValueAt(selected_row, 5);
		
		String SQL = "select sp_csd_upsert('EDIT', '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+trans_date+"', '"+actual_date+"', '"+request_type+"', '"+remarks+"', '"+created_by+"', '"+request_no+"')";
		FncSystem.out("fncEdit", SQL);
		
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Save Edited Request for " + lblClientName.getText() + " ?\n"
				+ "Request No.: " + request_no + "\n"
				+ "New Trans Date: "+trans_date+"\n"
				+ "New Actual Date: "+actual_date+"\n"
				+ "New Request Type: "+lblRequestDescConf.getText()+"\n"
				+ "New Remarks: "+remarks+"\n");
		
		if(confirmation == 0) {
			fncAction(SQL);
			displayTaggingOfCsv(entity_id, proj_id, pbl_id, seq_no);
			fncButtonEnabler(true, false, false, false, true);
			tableCSV.setEnabled(true);
			fncClearInputs();
		} else {
			System.out.println("Cancelled");
		}
	}
	
	private void fncCancel() {		
		System.out.println("Cancel Function");
		fncClearInputs();
	}
	
	private void fncEditSetup(int selected_row) {		
		System.out.println("Edit Setup");
		fncButtonEnabler(false, false, false, true, true);
		lookupClient.setEditable(false);
		tableCSV.setEnabled(false);
		System.out.println("Clickable Table ? "+tableCSV.isEnabled());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		Date actual_date = (Date) tableCSV.getValueAt(selected_row, 0);  
        String actual_date_str = dateFormat.format(actual_date);  
        
		Date trans_date = (Date) tableCSV.getValueAt(selected_row, 3); 
        String trans_date_str = dateFormat.format(trans_date);  

		String request_type = (String) tableCSV.getValueAt(selected_row, 1);
		String remarks = (String) tableCSV.getValueAt(selected_row, 2);
		String requested_by = (String) tableCSV.getValueAt(selected_row, 4);
		String request_no = (String) tableCSV.getValueAt(selected_row, 5);
		
		dteTransDate.setDate(FncGlobal.dateFormat(trans_date_str));
		dteActualDate.setDate(FncGlobal.dateFormat(actual_date_str));
		lookupRequest.setValue(fncRequestTypeCode(request_type));
		lblRequestDesc.setText(generateTAG(request_type));
		lblRequestDescConf.setText(request_type);
		txtRemarks.setText(remarks);
	}
	
	private String fncRequestTypeCode(String request_desc) {
		pgSelect db = new pgSelect(); 
		db.select("SELECT request_code from mf_csd_request_type where request_desc = '"+request_desc+"'"); 

		if (db.getResult()!=null) {
			return db.Result[0][0].toString(); 	
		} else {
			return FncGlobal.getDateSQL(); 
		}	
	}
	
	private void fncClearInputs() {		
		dteTransDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dteActualDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		lookupRequest.setValue(null);
		lblRequestDesc.setText(generateTAG(""));
		lblRequestDescConf.setText("");
		txtRemarks.setText("");
	}

	private String generateTAG(Object text) {
		return String.format("[ %s ]", text);
	}
	
	private static String lookupClients() {

		String SQL = "select * from\n"
				+ "(\n"
				+ "	select\n"
				+ "	rfs.status_id as \"Status\",\n"
				+ "	rfs.entity_id as \"Client Code\",\n"
				+ "	TRIM(rfe.entity_name) as \"Client Name\",\n"
				+ "	rfs.projcode as \"Project ID\",\n"
				+ "	TRIM(mfp.proj_name) as \"Project Name\",\n"
				+ "	rfs.pbl_id as \"Unit\",\n"
				+ "	rfs.seq_no as \"Seq\",\n"
				+ "	TRIM(mfu.description) as \"Unit Description\",\n"
				+ "	rfs.model_id as \"Model\",\n"
				+ "	TRIM(mfm.model_desc) as \"Model Desc\"\n"
				+ "	from rf_sold_unit rfs\n"
				+ "	left join rf_entity rfe on rfs.entity_id = rfe.entity_id\n"
				+ "	left join mf_project mfp on rfs.projcode = mfp.proj_id and mfp.status_id = 'A'\n"
				+ "	left join mf_unit_info mfu on rfs.projcode = mfu.proj_id and rfs.pbl_id = mfu.pbl_id\n"
				+ "	left join mf_product_model mfm on rfs.model_id = mfm.model_id and coalesce(rfs.server_id,'') = coalesce(mfm.server_id, '') and coalesce(rfs.proj_server,'') = coalesce(mfm.proj_server, '')\n"
				//+ "	where rfs.status_id = 'A'\n"
				//+ "	and rfs.currentstatus != '02'\n"
				+ ") client\n"
				+ "order by client.\"Client Name\", client.\"Unit Description\"";

		FncSystem.out(SQL, "CLIENT DETAILS");
		return SQL;

	}
	
	private static String lookupRequest() {

		String SQL = "select \n"
				+ "request_code as \"Request Type\",\n"
				+ "request_desc as \"Request Description\"\n"
				+ "from mf_csd_request_type";

		FncSystem.out(SQL, "REQUEST DETAILS");
		return SQL;

	}
	
	private void displayTaggingOfCsv(String entity_id, String proj_id, String pbl_id, Object seq_no) {
		FncTables.clearTable(model_tagging_of_csv);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowheaderCSV.setModel(listModel);
		String sql = 
				"select * from\n"
				+ "(select\n"
				+ "request_date::date as \"Actual Date\",\n"
				+ "b.request_desc as \"Request Type\",\n"
				+ "remarks as \"Remarks\",\n"
				+ "date_appv::date as \"Trans Date\",\n"
				+ "request_by as \"Requested By\",\n"
				+ "request_no as \"Requested No.\"\n"
				+ "from rf_csd_request_header a\n"
				+ "left join mf_csd_request_type b on a.request_type = b.request_code\n"
				+ "where status_id = 'A'\n"
				+ "and entity_id = '"+entity_id+"'\n"
				+ "and proj_id = '"+proj_id+"'\n"
				+ "and pbl_id = '"+pbl_id+"'\n"
				+ "and seq_no = "+seq_no+"\n"
				//+ "order by request_date desc\n"
				+ "union all \n"
				+ "select \n"
				+ "request_date::date as \"Actual Date\",\n"
				+ "b.request_desc as \"Request Type\",\n"
				+ "remarks as \"Remarks\",\n"
				+ "date_appv::date as \"Trans Date\",\n"
				+ "request_by as \"Requested By\",\n"
				+ "request_no as \"Requested No.\"\n"
				+ "from rf_csd_request_header_test a\n"
				+ "left join mf_csd_request_type b on a.request_type = b.request_code\n"
				+ "where status_id = 'A'\n"
				+ "and entity_id = '"+entity_id+"'\n"
				+ "and proj_id = '"+proj_id+"'\n"
				+ "and pbl_id = '"+pbl_id+"'\n"
				+ "and seq_no = "+seq_no+") a\n"
				+ "order by \"Requested No.\" desc\n";		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display  ", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				model_tagging_of_csv.addRow(db.getResult()[x]);
				listModel.addElement(model_tagging_of_csv.getRowCount());
			}	
		}		
		tableCSV.packAll();
	}
	
	private void fncAction(String SQL) {
		pgSelect db = new pgSelect();
		db.select(SQL);
	}
	
	private void fncButtonEnabler(boolean add, boolean delete, boolean edit, boolean save, boolean cancel) {
		btnAdd.setEnabled(add);
		btnDelete.setEnabled(delete);
		btnEdit.setEnabled(edit);
		btnSave.setEnabled(save);
		btnCancel.setEnabled(cancel);
	}
	
	private void fncSelectedRow() {
		if(tableCSV.getSelectedRows().length > 0) {
			fncButtonEnabler(true, true, true, false, true);
		}
	}

}
	
