package Admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelReceiptMaintenance;

public class receiptMaintenance extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = -1161220782014700889L;
	
	private static _JLookup lookupCompany;
	private static _JLookup lookupBranch;
	private static _JLookup lookupDocuments;
	
	private static JTextField txtCompany;
	private static JTextField txtBranch;
	private static JTextField txtReceitpType;

	private static JTextField txtFirst;
	private static JTextField txtLast;
	private static JTextField txtChar;
	
	private static JComboBox cboState; 
	
	private static JXPanel panMain;
	private static JLabel lblInactive;
	private static JLabel lblMine;
	
	private static JScrollPane scrReceipt;
	private static _JTableMain tblReceipt;
	private static modelReceiptMaintenance modelReceipt;
	private static JList rowReceipt;
	
	private static JButton btnArray[]; 
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public static Font fontDefault = new Font(Font.SANS_SERIF, Font.PLAIN, new Integer("11"));
	
	public receiptMaintenance() {
		super("Receipt Maintenance", false, true, false, false);
		initGUI();
	}

	public receiptMaintenance(String title) {
		super(title);
		initGUI();
	}

	public receiptMaintenance(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setSize(new Dimension(800, 550));
		this.setTitle(title);
		
		panMain = new JXPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 180));
				{
					{
						JXPanel panPageLine = new JXPanel(new BorderLayout(5, 5));
						panPage.add(panPageLine, BorderLayout.LINE_START); 
						panPageLine.setPreferredSize(new Dimension(600, 0));
						{
							{
								JXPanel panDetails = new JXPanel(new BorderLayout(5, 5)); 
								panPageLine.add(panDetails, BorderLayout.CENTER); 
								panDetails.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										JXPanel panLabelLookup = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panDetails.add(panLabelLookup, BorderLayout.LINE_START); 
										panLabelLookup.setPreferredSize(new Dimension(200, 0));
										{
											{
												JXPanel panLabel = new JXPanel(new GridLayout(3, 1, 5, 5)); 
												panLabelLookup.add(panLabel); 
												{
													panLabel.add(new JLabel("Company")); 
													panLabel.add(new JLabel("Branch"));
													panLabel.add(new JLabel("Receipt Type"));
												}
											}
											{
												JXPanel panLookup = new JXPanel(new GridLayout(3, 1, 5, 5)); 
												panLabelLookup.add(panLookup); 
												{
													{
														lookupCompany = new _JLookup("", "Select Company", 0);
														panLookup.add(lookupCompany, BorderLayout.WEST);
														lookupCompany.setLookupSQL(_receiptMaintenance.sqlCompany());
														lookupCompany.setEditable(false);
														lookupCompany.setFocusable(false);
														lookupCompany.addLookupListener(new LookupListener() {
															public void lookupPerformed(LookupEvent event) {
																Object [] data = ((_JLookup)event.getSource()).getDataSet();
																if(data != null){
																	txtCompany.setText(data[1].toString());
																}
															}
														});
													}
													{
														lookupBranch = new _JLookup("", "Select Branch", 0);
														panLookup.add(lookupBranch, BorderLayout.WEST);
														lookupBranch.setLookupSQL(_receiptMaintenance.sqlBranch());
														lookupBranch.setEditable(false);
														lookupBranch.setFocusable(false);
														lookupBranch.addLookupListener(new LookupListener() {
															public void lookupPerformed(LookupEvent event) {
																Object [] data = ((_JLookup)event.getSource()).getDataSet();
																if(data != null){
																	txtBranch.setText(data[1].toString());
																}
															}
														});
													}
													{
														lookupDocuments = new _JLookup("", "Select Document", 0);
														panLookup.add(lookupDocuments, BorderLayout.WEST);
														lookupDocuments.setLookupSQL(_receiptMaintenance.sqlDocument());
														lookupDocuments.setEditable(false);
														lookupDocuments.setFocusable(false);
														lookupDocuments.addLookupListener(new LookupListener() {
															public void lookupPerformed(LookupEvent event) {
																Object [] data = ((_JLookup)event.getSource()).getDataSet();
																if(data != null){
																	txtReceitpType.setText(data[1].toString());
																}
															}
														});
													}
												}
											}
										}
									}
									{
										JXPanel panText = new JXPanel(new GridLayout(3, 1, 5, 5)); 
										panDetails.add(panText, BorderLayout.CENTER); 
										{
											{
												txtCompany = new JTextField(""); 
												panText.add(txtCompany); 
												txtCompany.setEditable(false);
												txtCompany.setHorizontalAlignment(JTextField.CENTER);
											}
											{
												txtBranch = new JTextField(""); 
												panText.add(txtBranch); 
												txtBranch.setEditable(false);
												txtBranch.setHorizontalAlignment(JTextField.CENTER);
											}
											{
												txtReceitpType = new JTextField(""); 
												panText.add(txtReceitpType); 
												txtReceitpType.setEditable(false);
												txtReceitpType.setHorizontalAlignment(JTextField.CENTER);
											}
										}
									}
								}
							}
							{
								JXPanel panOther = new JXPanel(new BorderLayout(5, 5)); 
								panPageLine.add(panOther, BorderLayout.PAGE_END); 
								panOther.setPreferredSize(new Dimension(0, 60));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
								{
									{
										JXPanel panSeries = new JXPanel(new BorderLayout(5, 5)); 
										panOther.add(panSeries, BorderLayout.CENTER);
										panSeries.setBorder(JTBorderFactory.createTitleBorder("Series", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											JXPanel panFirstLast = new JXPanel(new BorderLayout(5, 5)); 
											panSeries.add(panFirstLast, BorderLayout.CENTER); 
											{
												{
													JLabel lblFirstLast = new JLabel("First - Last"); 
													panSeries.add(lblFirstLast, BorderLayout.LINE_START); 
													lblFirstLast.setPreferredSize(new Dimension(100, 0));
												}
												{
													JXPanel panFLBox = new JXPanel(new GridLayout(1, 2, 5, 5)); 
													panSeries.add(panFLBox, BorderLayout.CENTER); 
													{
														{
															txtFirst = new JTextField(""); 
															panFLBox.add(txtFirst); 
															txtFirst.setHorizontalAlignment(JTextField.CENTER);
															txtFirst.setEditable(false);
															txtFirst.addKeyListener(letterListener);
														}
														{
															txtLast = new JTextField(""); 
															panFLBox.add(txtLast); 
															txtLast.setHorizontalAlignment(JTextField.CENTER);
															txtLast.setEditable(false);
															txtLast.addKeyListener(letterListener);
														}
													}
												}
												{
													JXPanel panChar = new JXPanel(new BorderLayout(5, 5)); 
													panSeries.add(panChar, BorderLayout.LINE_END); 
													panChar.setPreferredSize(new Dimension(100, 0));
													{
														{
															JLabel lblChar = new JLabel("Char");
															panChar.add(lblChar, BorderLayout.LINE_START); 
															lblChar.setHorizontalAlignment(JLabel.CENTER);
															lblChar.setPreferredSize(new Dimension(50, 0));
														}
														{
															txtChar = new JTextField(""); 
															panChar.add(txtChar, BorderLayout.CENTER); 
															txtChar.setHorizontalAlignment(JTextField.CENTER);
															txtChar.setEditable(false);
															txtChar.addKeyListener(digitListener);
														}
													}
												}
											}
										}
									}
									{
										JXPanel panState = new JXPanel(new BorderLayout(5, 5));
										//panOther.add(panState, BorderLayout.LINE_END);
										panState.setPreferredSize(new Dimension(150, 0));
										panState.setBorder(JTBorderFactory.createTitleBorder("Status", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											cboState = new JComboBox(new String[] {
													"ACTIVE", 
													"INACTIVE"
											});
											panState.add(cboState, BorderLayout.CENTER); 
											cboState.setEnabled(false);
										}
									}
								}
							}
						}
					}
					
					{
						JXPanel panButton = new JXPanel(new GridLayout(5, 1, 5, 5)); 
						panPage.add(panButton, BorderLayout.CENTER);
						{
							String[] arr = new String[] {
									"Add", 
									"Deactivate", 
									"Save", 
									"Cancel", 
									"Refresh"
							}; 
							btnArray = new JButton[5];
							
							for (int x=0; x<arr.length; x++) {
								btnArray[x] = new JButton(arr[x].toString()); 
								panButton.add(btnArray[x]); 
								btnArray[x].setActionCommand(arr[x].toString());
								btnArray[x].setName(arr[x].toString());
								btnArray[x].addActionListener(actionListener);
								btnArray[x].setFocusable(false);
								btnArray[x].setEnabled(false);
								btnArray[x].setVisible(!arr[x].toString().equals(""));
								btnArray[x].setFont(fontDefault);
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				panCenter.setBorder(JTBorderFactory.createTitleBorder("List", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						scrReceipt = new JScrollPane();
						panCenter.add(scrReceipt, BorderLayout.CENTER);
						scrReceipt.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
						scrReceipt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrReceipt.setBorder(LINE_BORDER);
						{
							{
								modelReceipt = new modelReceiptMaintenance(); 
								tblReceipt = new _JTableMain(modelReceipt);
								tblReceipt.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent e) {
										if(!e.getValueIsAdjusting()){
											try {
												Integer row = tblReceipt.convertRowIndexToModel(tblReceipt.getSelectedRow());
												
												lookupCompany.setValue(modelReceipt.getValueAt(row, 9).toString());
												lookupBranch.setValue(modelReceipt.getValueAt(row, 10).toString());
												lookupDocuments.setValue(modelReceipt.getValueAt(row, 12).toString());
												
												txtCompany.setText(FncGlobal.GetString("select company_name from mf_company where co_id = '"+modelReceipt.getValueAt(row, 9).toString()+"'"));
												txtBranch.setText(FncGlobal.GetString("select branch_name from mf_office_branch where branch_id = '"+modelReceipt.getValueAt(row, 10).toString()+"'"));
												txtReceitpType.setText(FncGlobal.GetString("select doc_desc from mf_doc_details where doc_id = '"+modelReceipt.getValueAt(row, 12).toString()+"'"));

												txtFirst.setText(FncGlobal.GetString("select first_no::varchar from cs_receipt_book where rec_id::int = '"+modelReceipt.getValueAt(row, 11).toString()+"'::int"));
												txtLast.setText(FncGlobal.GetString("select last_no::varchar from cs_receipt_book where rec_id::int = '"+modelReceipt.getValueAt(row, 11).toString()+"'::int")); 
												txtChar.setText(FncGlobal.GetString("select suffix from cs_receipt_book where rec_id::int = '"+modelReceipt.getValueAt(row, 11).toString()+"'::int")); 
												
												cboState.setSelectedItem(modelReceipt.getValueAt(row, 7).toString());

												btnArray[1].setEnabled(modelReceipt.getValueAt(row, 7).toString().equals("ACTIVE"));
											} catch (ArrayIndexOutOfBoundsException e1) {
												
											} catch (IndexOutOfBoundsException e2) {
												
											}
										}
									}
								});
								tblReceipt.setSortable(false);
								
								rowReceipt = tblReceipt.getRowHeader();
								scrReceipt.setViewportView(tblReceipt);
								
								tblReceipt.getColumnModel().getColumn(0).setPreferredWidth(50);
								tblReceipt.getColumnModel().getColumn(1).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(2).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(3).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(4).setPreferredWidth(255);
								tblReceipt.getColumnModel().getColumn(5).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(6).setPreferredWidth(355);
								tblReceipt.getColumnModel().getColumn(7).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(8).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(9).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(10).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(11).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(12).setPreferredWidth(100);
								tblReceipt.getColumnModel().getColumn(13).setPreferredWidth(100);
								
								tblReceipt.getColumnModel().getColumn(8).setCellRenderer(new DateRenderer(sdf));
								
								tblReceipt.hideColumns("co_id", "branch_id", "receipt_id", "doc_id", "suffix");
							}
							{
								rowReceipt = tblReceipt.getRowHeader();
								rowReceipt.setModel(new DefaultListModel());
								scrReceipt.setRowHeaderView(rowReceipt);
								scrReceipt.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
							}
						}
					}
					{
						JXPanel panFilters = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panCenter.add(panFilters, BorderLayout.PAGE_END); 
						panFilters.setPreferredSize(new Dimension(0, 30));
						{
							{
								lblInactive = new JLabel("Show Inactive Receipts"); 
								panFilters.add(lblInactive); 
								lblInactive.setBorder(BorderFactory.createLoweredBevelBorder());
								lblInactive.setFont(fontDefault);
								lblInactive.setHorizontalAlignment(JLabel.CENTER);
								lblInactive.addMouseListener(new MouseListener() {
									public void mouseReleased(MouseEvent e) {
										
									}
									
									public void mousePressed(MouseEvent e) {
										
									}
									
									public void mouseExited(MouseEvent e) {
										
									}
									
									public void mouseEntered(MouseEvent e) {
										
									}
									
									public void mouseClicked(MouseEvent e) {
										if (lblInactive.getBorder()==BorderFactory.createLoweredBevelBorder()) {
											lblInactive.setText("Hide Inactive Receipts");
											lblInactive.setBorder(BorderFactory.createRaisedBevelBorder());
										} else {
											lblInactive.setText("Show Inactive Receipts");
											lblInactive.setBorder(BorderFactory.createLoweredBevelBorder());
										}
										
										lblInactive.repaint();
										displayReceiptDetails(); 
									}
								});
							}
							{
								lblMine = new JLabel("Show All Series"); 
								panFilters.add(lblMine); 
								lblMine.setBorder(BorderFactory.createLoweredBevelBorder());
								lblMine.setFont(fontDefault);
								lblMine.setHorizontalAlignment(JLabel.CENTER);
								lblMine.addMouseListener(new MouseListener() {
									public void mouseReleased(MouseEvent e) {
										
									}
									
									public void mousePressed(MouseEvent e) {
										
									}
									
									public void mouseExited(MouseEvent e) {
										
									}
									
									public void mouseEntered(MouseEvent e) {
										
									}
									
									public void mouseClicked(MouseEvent e) {
										if (lblMine.getBorder()==BorderFactory.createLoweredBevelBorder()) {
											lblMine.setText("Show Only My Series");
											lblMine.setBorder(BorderFactory.createRaisedBevelBorder());
										} else {
											lblMine.setText("Show All Series");
											lblMine.setBorder(BorderFactory.createLoweredBevelBorder());
										}
										
										lblMine.repaint();
										displayReceiptDetails(); 
									}
								});
							}
						}
					}
				}
			}
		}
		
		buttonState(true, false, false, false, true);
		displayReceiptDetails(); 
	}
	
	private static void displayReceiptDetails() {
		FncTables.clearTable(modelReceipt);		
		DefaultListModel listModel = new DefaultListModel();
		rowReceipt.setModel(listModel);

		pgSelect db = new pgSelect();
		String sql = "select trim(d.doc_alias) as doc_alias,\n" + 
				"a.first_no::varchar || coalesce(a.suffix, '') as first_no, \n" + 
				"a.last_no::varchar || coalesce(a.suffix, '') as last_no,\n" + 
				"a.last_no_used::varchar || a.suffix as last_used, \n" + 
				"upper(trim(f.entity_name)) as cashier, \n" + 
				"trim(b.company_alias) as company, \n" + 
				"trim(c.branch_name) as branch, \n" + 
				"(case when a.status_id = 'A' then 'ACTIVE' ELSE 'INACTIVE' end) as status, \n" + 
				"a.logged_date as logged_date, \n" + 
				"trim(a.co_id) as co_id, \n" + 
				"trim(a.branch_id) as branch_id, \n" + 
				"a.rec_id as receipt_id, \n" +
				"d.doc_id, \n" +
				"a.suffix \n" +
				"from cs_receipt_book a \n" + 
				"left join mf_company b on b.co_id = a.co_id \n" + 
				"left join mf_office_branch c on c.branch_id = a.branch_id \n" + 
				"left join mf_doc_details d on d.doc_id = a.doc_id \n" + 
				"left join em_employee e on a.emp_code = e.emp_code \n" + 
				"left join rf_entity f on e.entity_id = f.entity_id \n" + 
				"where a.rec_id is not null and concat(a.last_no, coalesce(suffix, '')) != concat(a.last_no_used, coalesce(suffix, '')) \n" + 
				(!lblInactive.getText().equals("Hide Inactive Receipts") ? "and a.status_id = 'A'\n":"") +
				(!lblMine.getText().equals("Show Only My Series") ? "and a.emp_code = '"+UserInfo.EmployeeCode+"'\n":"") +
				"order by a.rec_id desc;";
		
		System.out.println(sql);
		
		db.select(sql);
		if (db.isNotNull()) {
			for (int x=0; x < db.getRowCount(); x++) {
				modelReceipt.addRow(db.getResult()[x]);
				listModel.addElement(modelReceipt.getRowCount());		
			}
		}
	}
	
	private static void buttonState(Boolean blnAdd, Boolean blnDeactivate, Boolean blnSave, Boolean blnCancel, Boolean blnRefresh) {
		btnArray[0].setEnabled(blnAdd);
		btnArray[1].setEnabled(blnDeactivate);
		btnArray[2].setEnabled(blnSave);
		btnArray[3].setEnabled(blnCancel);
		btnArray[4].setEnabled(blnRefresh);
	}
	
	private static Boolean SaveValidate() {
		if (lookupCompany.getValue().equals("")) {
			JOptionPane.showMessageDialog(panMain, "Please select company", "Fill-up", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if (lookupBranch.getValue().equals("")) {
			JOptionPane.showMessageDialog(panMain, "Please select branch", "Fill-up", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if (lookupDocuments.getValue().equals("")) {
			JOptionPane.showMessageDialog(panMain, "Please select receipt type", "Fill-up", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if (txtFirst.getText().equals("")) {
			JOptionPane.showMessageDialog(panMain, "Please specify the first number of the series", "Fill-up", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (txtLast.getText().equals("")) {
			JOptionPane.showMessageDialog(panMain, "Please specify the last number of the series", "Fill-up", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		// COMMENTED BY MONIQUE DTD 2022-07-07; ADDTL CHAR ISN'T APPLICABLE TO RECEIPT# OF ITSREAL ACCOUNTS
		
		/*if (txtChar.getText().equals("")) {
			if (JOptionPane.showConfirmDialog(panMain, "You did not specify the character. Proceed?", 
					"Verification", JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(panMain, "Please specify the character suffix", "Fill-up", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}*/
		
		if (ValidateSeries(txtFirst.getText(), txtLast.getText(), lookupDocuments.getValue(), txtChar.getText(), lookupCompany.getValue())) {
			JOptionPane.showMessageDialog(panMain, "This series already exists in the database.", "Warning", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("update cs_receipt_book \n" +
				"set inactive_date = now(), status_id = 'I' \n" + 
				"where emp_code = '"+UserInfo.EmployeeCode+"' \n" + 
				"and doc_id = '"+lookupDocuments.getValue()+"' and branch_id = '"+lookupBranch.getValue()+"' \n" + 
				"and co_id = '"+lookupCompany.getValue()+"' \n" +
				"and status_id = 'A'", true);
		dbExec.commit();
		
		/*
		if (FncGlobal.GetBoolean("select exists(select * \n" +
				"from cs_receipt_book \n" +
				"where emp_code = '"+UserInfo.EmployeeCode+"' \n" +
				"and doc_id = '"+lookupDocuments.getValue()+"' and branch_id = '"+lookupBranch.getValue()+"' \n" +
				"and status_id = 'A')")) {

			if (JOptionPane.showConfirmDialog(panMain, "A series of the same type is still active. \n" +
					"Do you want to proceed with this new series? \n" +
					"If so, the old series will be deactivated.", "Warning", JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE)==JOptionPane.YES_OPTION) {

			} else {
				return false; 
			}

		}
		*/
		
		return true; 
	}
	
	private static void enableFields(Boolean blnDo) {
		lookupCompany.setEditable(blnDo);
		lookupBranch.setEditable(blnDo);
		lookupDocuments.setEditable(blnDo);
		
		txtFirst.setEditable(blnDo);
		txtLast.setEditable(blnDo);
		txtChar.setEditable(blnDo);
		
		cboState.setEnabled(blnDo);
		
		tblReceipt.setEnabled(!blnDo);
	}

	
	private static void clearFields() {
		lookupCompany.setValue("");
		lookupBranch.setValue("");
		lookupDocuments.setValue("");
		
		txtFirst.setText("");
		txtLast.setText("");
		txtChar.setText("");
		
		txtCompany.setText("");
		txtBranch.setText("");
		txtReceitpType.setText("");
		
		cboState.setSelectedIndex(0);
	}
	
	private static void addReceipt(String co_id, String doc_id, String branch_id, String first_no, String last_no, String strChar) {
		pgUpdate db = new pgUpdate();
		
		String strSQL = "";
		strSQL = "INSERT INTO cs_receipt_book(rec_id, co_id, doc_id, branch_id, first_no, last_no, last_no_used, \n" + 
			"pagibig_id, emp_code, status_id, logged_date, inactive_date, series_no, suffix)\n" + 
			"VALUES ((select coalesce(max(rec_id)+1, 0) from cs_receipt_book), '"+co_id+"', '"+doc_id+"', '"+branch_id+"', \n" +
			"'"+first_no+"', '"+last_no+"', 0, null, '"+UserInfo.EmployeeCode+"', 'A', now(), null, null, '"+strChar+"');";			

		db.executeUpdate(strSQL, true);
		db.commit();		
		displayReceiptDetails();
	}
	
	private static KeyListener letterListener = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			if (Character.isLetter(e.getKeyChar())) {
				e.consume();
				JOptionPane.showMessageDialog(panMain, "You cannot enter letters in the first and last number field.\n"+
				"Enter the suffix in the `Char` field.", "Letter Found", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		public void keyReleased(KeyEvent e) {
					
		}
		
		public void keyPressed(KeyEvent e) {
					
		}
	};
	
	private static KeyListener digitListener = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			if (Character.isDigit(e.getKeyChar())) {
				e.consume();
				JOptionPane.showMessageDialog(panMain, "You cannot enter number(s) in the `Char` field.", "Letter Found", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		public void keyReleased(KeyEvent e) {
					
		}
		
		public void keyPressed(KeyEvent e) {
					
		}
	};

	private static boolean ValidateSeries(String strFirst, String strLast, String strReceiptType, String strChar, String co_id) {
		if (strReceiptType.equals("03")) {
			return FncGlobal.GetBoolean("select (case when count(*) > 0 then true else false end) \n" + 
					"from  \n" + 
					"( \n" + 
					"select x.or_no as receipt_no_char, replace(x.or_no, '"+strChar+"', '')::int as receipt_no_int from rf_payments x where (x.or_doc_id is null or x.or_doc_id = '03') and x.pr_doc_id = '03' and coalesce(x.or_no, '') != '' and status_id = 'A' and x.co_id = '"+co_id+"'\n" + 
					"union \n" + 
					"select x.ar_no as receipt_no_char, replace(x.ar_no, '"+strChar+"', '')::int as receipt_no_int from rf_payments x where (x.or_doc_id is not null or x.or_doc_id = '01') and x.pr_doc_id = '03' and coalesce(x.ar_no, '') != '' and status_id = 'A' and x.co_id = '"+co_id+"'\n" + 
					"union \n" + 
					"select x.ar_no as receipt_no_char, replace(x.ar_no, '"+strChar+"', '')::int as receipt_no_int from rf_payments x where x.or_doc_id = '03' and coalesce(x.pr_doc_id, '') = '' and coalesce(x.ar_no, '') != '' and status_id = 'A' and x.co_id = '"+co_id+"'\n" + 
					"union  \n" + 
					"select x.receipt_no as receipt_no_char, replace(x.receipt_no, '"+strChar+"', '')::int as receipt_no_int from rf_pay_detail x where x.receipt_type = '03' and x.status_id = 'A' and coalesce(x.receipt_no, '') != '' and status_id = 'A' \n" +
					"and exists (SELECT * FROM rf_pay_header where client_seqno = x.client_seqno and co_id = '"+co_id+"')\n"+
					"union  \n" + 
					"select x.receipt_no as receipt_no_char, replace(x.receipt_no, '"+strChar+"', '')::int as receipt_no_int from rf_tra_Detail x where x.receipt_type = '03' and x.status_id = 'A' and coalesce(x.receipt_no, '') != '' and status_id = 'A' \n" +
					"and exists (SELECT * FROM rf_tra_header where client_seqno = x.client_seqno and co_id = '"+co_id+"')\n"+
					") a \n" + 
					"where coalesce(a.receipt_no_char, '') != '' \n" + 
					"and right(a.receipt_no_char, 1) = '"+strChar+"' \n" + 
					"and a.receipt_no_int::int between '"+txtFirst.getText()+"'::int and '"+txtLast.getText()+"'::int; ");	
		} else {
			return FncGlobal.GetBoolean("select (case when count(*) > 0 then true else false end) \n" + 
					"from \n" + 
					"( \n" + 
					"select x.or_no as receipt_no_char, replace(x.or_no, '"+strChar+"', '')::int as receipt_no_int, co_id from rf_payments x where (x.or_doc_id = '"+strReceiptType+"' or x.or_date is not null) and (x.status_id = 'A' or (x.status_id = 'I' and x.request_no is not null)) and status_id = 'A' AND x.pay_rec_id NOT IN (62463) and x.co_id = '"+co_id+"'\n" + 
					"union \n" + 
					"select x.receipt_no as receipt_no_char, replace(x.receipt_no, '"+strChar+"', '')::int as receipt_no_int, co_id from rf_pay_detail x where x.receipt_type = '"+strReceiptType+"' and x.status_id = 'A' and coalesce(x.receipt_no, '') != '' and status_id = 'A' \n" +
					"and exists (SELECT * FROM rf_pay_header where client_seqno = x.client_seqno and co_id = '"+co_id+"') \n"+
					"union \n" + 
					"select x.receipt_no as receipt_no_char, replace(x.receipt_no, '"+strChar+"', '')::int as receipt_no_int, '' from rf_tra_Detail x where x.receipt_type = '"+strReceiptType+"' and x.status_id = 'A' and status_id = 'A' \n" +
					"AND EXISTS (SELECT * FROM rf_tra_header where client_seqno = x.client_seqno and co_id = '"+co_id+"') \n"+
					") a \n" + 
					"where coalesce(a.receipt_no_char, '') != '' \n" + 
					"and right(a.receipt_no_char, 1) = '"+strChar+"' \n" +
					"and a.co_id = '"+co_id+"' \n"+
					"and a.receipt_no_int::int between '"+txtFirst.getText()+"'::int and '"+txtLast.getText()+"'::int; ");
		}
	}
	
	private static ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Add")) {
				enableFields(true); 
				clearFields(); 
				buttonState(false, false, true, true, false);
			} else if (e.getActionCommand().equals("Edit")) {

			} else if (e.getActionCommand().equals("Save")) {
				if (SaveValidate()) {
					addReceipt(lookupCompany.getValue(), lookupDocuments.getValue(), lookupBranch.getValue(), txtFirst.getText(), txtLast.getText(), txtChar.getText()); 
					enableFields(false); 
					buttonState(true, false, false, false, true);
					JOptionPane.showMessageDialog(panMain, "Record saved!", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (e.getActionCommand().equals("Cancel")) {
				enableFields(false);
				clearFields(); 
				buttonState(true, false, false, false, true);
			} else if (e.getActionCommand().equals("Deactivate")) {
				pgUpdate dbExec = new pgUpdate();
				
				try {
					String strSQL = "UPDATE cs_receipt_book \n" + 
							"SET status_id = 'I', inactive_date = now() \n" +
							"WHERE rec_id = '"+modelReceipt.getValueAt(tblReceipt.convertRowIndexToModel(tblReceipt.getSelectedRow()), 11)+"'";
					if (JOptionPane.showConfirmDialog(panMain, "Are you sure you want to deactivate the selected series?", 
							"Receipt Deactivation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
						dbExec.executeUpdate(strSQL, false);
						dbExec.commit();
						
						displayReceiptDetails();
						clearFields(); 
						JOptionPane.showMessageDialog(panMain, "Series deactivated!", "Deactivation", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(panMain, "No row was selected.", "Deactivation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	};
}
