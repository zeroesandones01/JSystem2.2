package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

//import com.opencsv.exceptions.CsvException;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelAUBForIssuance;

public class UploadAUBPayments extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -5558719936835107654L;
	static String title = "Upload AUB Payments";
	Dimension frameSize = new Dimension(700, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupBatchNo;
	private _JXTextField txtProjName;
	private _JXTextField txtFileName;

	private JButton btnIssue;
	private JButton btnSelectFile;
	private JButton btnUpload;
	private JFileChooser fileChooser;

	private JTabbedPane tabCenter;

	private JPanel pnlForIssuance;

	private _JTableMain tblAUBForIssuance;
	private JScrollPane scrollAUBForIssuance;
	private JList rowheaderAUBForIssuance;
	private modelAUBForIssuance modelAUBForIssue;

	private _JTableMain tblAUBPayments;



	public UploadAUBPayments() {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadAUBPayments(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadAUBPayments(String title, boolean resizable, boolean closable, boolean maximizable,
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
			JPanel pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 100));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("File Upload Details"));
				{
					JPanel pnlNorthCenter = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						JPanel pnlNorthCenterLabel = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthCenter.add(pnlNorthCenterLabel, BorderLayout.WEST);
						pnlNorthCenterLabel.setPreferredSize(new Dimension(100, 0));
						{
							JLabel lblCompany = new JLabel("Company");
							pnlNorthCenterLabel.add(lblCompany);
						}
						{
							JLabel lblSelectFile = new JLabel("Select File");
							pnlNorthCenterLabel.add(lblSelectFile);
						}
						{
							JLabel lblBatchNo = new JLabel("Batch No");
							pnlNorthCenterLabel.add(lblBatchNo);
						}
					}
					{
						JPanel pnlNorthComponents = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthCenter.add(pnlNorthComponents, BorderLayout.CENTER);
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
							pnlNorthComponents.add(pnlCompany);
							{
								lookupCompany = new _JLookup(null, "Company", 0);
								pnlCompany.add(lookupCompany, BorderLayout.WEST);
								lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
								lookupCompany.setPreferredSize(new Dimension(50, 0));
								lookupCompany.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										
										if(data != null) {
											String company_name = (String) data[1];
											txtCompany.setText(company_name);
											
										}
									}
								});
							}
							{
								txtCompany = new _JXTextField();
								pnlCompany.add(txtCompany, BorderLayout.CENTER);
								
							}
						}

						{
							JPanel pnlSearchFile = new JPanel(new BorderLayout(3, 3));
							pnlNorthComponents.add(pnlSearchFile);
							{
								txtFileName = new _JXTextField();
								pnlSearchFile.add(txtFileName, BorderLayout.CENTER);
							}
							{
								btnSelectFile = new JButton("Browse");
								pnlSearchFile.add(btnSelectFile, BorderLayout.EAST);
								btnSelectFile.setPreferredSize(new Dimension(100, 0));
								btnSelectFile.setActionCommand("Browse");
								btnSelectFile.addActionListener(this);
							}
						}
						{
							JPanel pnlBatch = new JPanel(new BorderLayout(3, 3));
							pnlNorthComponents.add(pnlBatch);

							{
								lookupBatchNo = new _JLookup();
								pnlBatch.add(lookupBatchNo, BorderLayout.WEST);
								lookupBatchNo.setPreferredSize(new Dimension(50, 250));
								lookupBatchNo.setReturnColumn(0);
								lookupBatchNo.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String batch_no = (String) data[0];

											displayForIssuanceBatch(batch_no);
											previewForIssuance();
										}
									}
								});
								lookupBatchNo.addFocusListener(new FocusListener() {
									
									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void focusGained(FocusEvent e) {
										lookupBatchNo.setLookupSQL(sqlBatchNo(lookupCompany.getValue()));
									}
								});
							}
						}
						
					}
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					tabCenter = new JTabbedPane();
					pnlCenter.add(tabCenter, BorderLayout.CENTER);
					//displayGarbageFeeForIssuance();

					//					tabCenter.addTab("Garbage Fee", null, new JScrollPane(tblAUBPayments), null);
					//					System.out.println("Current tab index: "+tabCenter.getSelectedIndex());

					//					tabCenter.addChangeListener(new ChangeListener() {
					//						
					//						@Override
					//						public void stateChanged(ChangeEvent e) {
					//							System.out.println(tabCenter.getSelectedIndex());
					//							{
					//								if(tabCenter.getSelectedIndex() == 0) {
					//									btnImport.setText("Issue");
					//									pnlSouth.add(btnImport);
					//									btnImport.setActionCommand("Issue");
					//									btnImport.setMnemonic(KeyEvent.VK_I);
					//								}if(tabCenter.getSelectedIndex() == 1) {
					//									btnImport.setText("Import");
					//									pnlSouth.add(btnImport);
					//									btnImport.setActionCommand("Import");
					//									btnImport.setMnemonic(KeyEvent.VK_I);
					//								}
					//							}
					//							pnlSouth.repaint();
					//						}
					//					});
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1, 7, 3, 3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 50));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnUpload = new JButton("Upload");
					pnlSouth.add(btnUpload);
					btnUpload.setActionCommand("Upload");
					btnUpload.setMnemonic(KeyEvent.VK_I);
					btnUpload.addActionListener(this);
					btnUpload.setEnabled(false);
				}
				{
					btnIssue = new JButton("Issue");
					pnlSouth.add(btnIssue);
					btnIssue.setActionCommand("Issue");
					btnIssue.setMnemonic(KeyEvent.VK_I);
					btnIssue.addActionListener(this);
					btnIssue.setEnabled(false);
				}
			}
		}
	}//XXX END OF INIT GUI

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();


		if (actionCommand.equals("Browse")) {
			if (fileChooser == null) {
				fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				//				fileChooser.addChoosableFileFilter(new FncODSFileFilter(new String[] { "ods", "xlsx" },
				//						"Spreadsheets (*.ods, *.odc, *.ots, *.xlsx)"));

				Action details = fileChooser.getActionMap().get("viewTypeDetails");
				details.actionPerformed(null);
			}

			fileChooser.setSelectedFile(new File(""));
			int status = fileChooser.showOpenDialog(this.getTopLevelAncestor());
			if (status == JFileChooser.APPROVE_OPTION) {
				if (fileChooser.getSelectedFile().equals(null)) {
					JOptionPane.showMessageDialog(getParent(), "No Selected Document");
					return;
				}

				System.out.printf("Selected File: %s%n", fileChooser.getSelectedFile().toString());
				String selectedFile = fileChooser.getSelectedFile().toString();
				txtFileName.setText(selectedFile);
			}

			try {
				processForUpload();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} /*
			 * catch (CsvException e1) { // TODO Auto-generated catch block
			 * e1.printStackTrace(); }
			 */
		}

		if(actionCommand.equals("Upload")) {
			uploadPayments();
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Upload sucessful retrive batch no to issue", "Upload", JOptionPane.INFORMATION_MESSAGE);
		}

		if(actionCommand.equals("Issue")) {
			pgSelect db = new pgSelect();
			
			String SQL = "SELECT is_receipt_enough_aub('"+lookupBatchNo.getValue()+"', '"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
			FncSystem.out("Display SQL", SQL);
			db.select(SQL, "Issue Receipt", true);
			
			System.out.printf("Display Value of data: %s%n", db.getResult()[0][0]);
			if ((Boolean) db.getResult()[0][0]) {
				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to issue receipts for this batch?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					IssueAUBPmts(lookupBatchNo.getValue());
					clearFields();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Receipts has been issued to this batch", "Issue", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}

	}

	private void clearFields() {
		txtFileName.setText("");
		if (fileChooser != null) {
			fileChooser.setSelectedFile(new File(""));
		}
		lookupBatchNo.setValue(null);
		btnIssue.setEnabled(false);
		btnUpload.setEnabled(false);
		tabCenter.removeAll();
	}

	private String getNewBatchNo() {
		String batch_no = "";

		pgSelect db = new pgSelect();
		String SQL = "(select to_char((SELECT COALESCE(max(batch_no)::int,0) + 1 from rf_aub_uploaded_pmt_for_issuance),'FM000000'))";
		db.select(SQL);

		batch_no = (String) db.getResult()[0][0];

		return batch_no;
	}

	private static List<String> parseCSVLine(String line) {
		List<String> fields = new ArrayList<>();
		StringBuilder field = new StringBuilder();
		boolean inQuotes = false;

		for (char c : line.toCharArray()) {
			if (c == '"') {
				inQuotes = !inQuotes;
			} else if (c == ',' && !inQuotes) {
				fields.add(field.toString().trim());
				field.setLength(0);
			} else {
				field.append(c);
			}
		}

		fields.add(field.toString().trim()); // Add the last field

		return fields;
	}

	private String sqlBatchNo(String co_id) {
		String SQL = "select batch_no as \"Batch No\" from rf_aub_uploaded_pmt_for_issuance where status_id = 'A' and case when NULLIF('"+co_id+"', 'null') IS NULL THEN TRUE ELSE co_id = '"+co_id+"' END group by batch_no;\n";

		return SQL;
	}
	
	private void previewForIssuance() {
		String criteria = "List of AUB Bills Payment for issuance";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", lookupBatchNo.getValue());
	

		FncReport.generateReport("/Reports/rptAUB_BillsPmt_ForIssuance.jasper", reportTitle, mapParameters);
	}

	private void processForUpload() throws IOException{


		String filePath = fileChooser.getSelectedFile().getPath();
		DefaultTableModel model = new DefaultTableModel();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			int row = 0;
			while ((line = br.readLine()) != null) {
				//String[] data = line.split(",");
				List<String> data = parseCSVLine(line);
				if (row == 0) { // First line contains column headers
					for (String col : data) {
						model.addColumn(col.trim());
					}
				} else { // Data rows
					System.out.printf("Data Value: %s%n", data);
					model.addRow(data.toArray());
				}
				row++;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		tblAUBPayments = new _JTableMain(model);
		tabCenter.addTab("Garbage Fee", null, new JScrollPane(tblAUBPayments), null);
		tblAUBPayments.packAll();
		btnIssue.setEnabled(false);
		btnUpload.setEnabled(true);
		lookupBatchNo.setValue(null);

	}

	private void displayForIssuanceBatch(String batch_no) {

		//clearFields();
		
		txtFileName.setText("");
		if (fileChooser != null) {
			fileChooser.setSelectedFile(new File(""));
		}

		{
			pnlForIssuance = new JPanel(new BorderLayout(3, 3));

			{
				scrollAUBForIssuance = new JScrollPane();
				pnlForIssuance.add(scrollAUBForIssuance, BorderLayout.CENTER);
				scrollAUBForIssuance.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollAUBForIssuance.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollAUBForIssuance.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				{
					modelAUBForIssue = new modelAUBForIssuance();
					tblAUBForIssuance = new _JTableMain(modelAUBForIssue);
					rowheaderAUBForIssuance = tblAUBForIssuance.getRowHeader();
					scrollAUBForIssuance.setViewportView(tblAUBForIssuance);


				}
				{
					rowheaderAUBForIssuance = tblAUBForIssuance.getRowHeader();
					rowheaderAUBForIssuance.setModel(new DefaultListModel());
					scrollAUBForIssuance.setRowHeaderView(rowheaderAUBForIssuance);
					scrollAUBForIssuance.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}

		tabCenter.addTab("For Issuance", pnlForIssuance);
		btnUpload.setEnabled(false);
		//fileChooser.setSelectedFile(new File(""));

		btnIssue.setEnabled(true);

		FncTables.clearTable(modelAUBForIssue);
		DefaultListModel listModel = new DefaultListModel();

		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM view_aub_pmts_for_issuance('"+batch_no+"')";
		db.select(SQL);
		FncSystem.out("Display SQL for issuance", SQL);

		if(db.isNotNull()) {
			for(int x=0; x<db.getRowCount(); x++) {
				modelAUBForIssue.addRow(db.getResult()[x]);
				listModel.addElement(modelAUBForIssue.getRowCount());
			}
		}
		rowheaderAUBForIssuance.setModel(listModel);
		tblAUBForIssuance.packAll();
	}

	private void uploadPayments() {

		pgSelect db = new pgSelect();

		String batch_no = getNewBatchNo();

		for(int x= 0; x<tblAUBPayments.getRowCount(); x++) {
			String pmt_date = (String) tblAUBPayments.getValueAt(x, 0);
			String trans_no = (String) tblAUBPayments.getValueAt(x, 1);
			String reference_no = (String) tblAUBPayments.getValueAt(x, 2);
			String pmt_particular = (String) tblAUBPayments.getValueAt(x, 4);
			String amount = (String) tblAUBPayments.getValueAt(x, 5);


			System.out.printf("Value of reference no: %s%n", reference_no);
			System.out.printf("Value of pmt particular: %s%n", pmt_particular);
			System.out.printf("value of amount: %s%n", amount);

			String SQL = "select sp_create_pmt_sequence('"+batch_no+"','"+pmt_date+"'::DATE,'"+trans_no+"','"+reference_no+"','"+pmt_particular+"',"+amount+",'"+UserInfo.Branch+"','"+UserInfo.EmployeeCode+"') ";
			db.select(SQL);
		}

		clearFields();
	}

	private void IssueAUBPmts(String batch_no) {

		for(int x= 0; x<modelAUBForIssue.getRowCount(); x++) {
			pgSelect db = new pgSelect();
			String client_seqno = (String) modelAUBForIssue.getValueAt(x, 1);
			String SQL = "SELECT sp_ir_post_ordered('"+ client_seqno +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
			db.select(SQL);
		}	
	}

}
