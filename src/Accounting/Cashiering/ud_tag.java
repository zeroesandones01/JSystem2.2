package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Lookup.lookupMethods;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.model_unidentified_deposit_issuance;

public class ud_tag extends _JInternalFrame {

	private static final long serialVersionUID = -6889896390126935588L;
	private static String title = "Issue Identified Deposits"; 
	private BorderLayout border;  

	private static _JLookup txtCoID; 
	private JTextField txtCo;
	private JTextField txtSearch; 

	private JScrollPane scrud;
	public static JList rowud;
	private static _JTableMain tblud;
	private static model_unidentified_deposit_issuance model_ud; 

	private static JCheckBox chkName; 
	private static JCheckBox chkDate;
	private static JCheckBox chkAsc;
	private static JCheckBox chkDesc;

	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private static JButton btnRefresh;
	private static JButton btnPost;
	private static JButton btnDeactivate;
	private static JButton btnPreview; 

	public ud_tag() {
		super(title, false, true, false, false);
		initGUI(); 
	}

	public ud_tag(String title) {
		super(title);
		initGUI(); 
	}

	public ud_tag(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	private void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setSize(new Dimension(1000, 600));

		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JPanel panPage = new JPanel(new GridLayout(1, 2, 5, 5));
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 60));
				{
					{
						JPanel panCompany = new JPanel(new BorderLayout(5, 5)); 
						panPage.add(panCompany, BorderLayout.CENTER);
						panCompany.setBorder(JTBorderFactory.createTitleBorder("Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								txtCoID = new _JLookup(""); 
								panCompany.add(txtCoID, BorderLayout.LINE_START); 
								txtCoID.setReturnColumn(0);
								txtCoID.setLookupSQL(lookupMethods.getCompany());
								txtCoID.setPreferredSize(new Dimension(100, 0));
								txtCoID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet(); 

										if (data!=null) {
											txtCoID.setValue(data[0].toString());
											txtCo.setText(data[1].toString());
											display();
										}
									}
								});
								txtCoID.setValue("");
							}
							{
								txtCo = new JTextField(""); 
								panCompany.add(txtCo, BorderLayout.CENTER); 
								txtCo.setHorizontalAlignment(JTextField.CENTER);
								txtCo.setEditable(false);
							}
						}
					}
					{
						JPanel panSort = new JPanel(new GridLayout(1, 4, 5, 5)); 
						panPage.add(panSort, BorderLayout.LINE_END);
						panSort.setPreferredSize(new Dimension(300, 0));
						panSort.setBorder(JTBorderFactory.createTitleBorder("Sort", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								chkName = new JCheckBox("Name"); 
								panSort.add(chkName); 
								chkName.setHorizontalAlignment(JCheckBox.CENTER);
								chkName.setSelected(true);
								chkName.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										chkDate.setSelected(!chkName.isSelected());
										display(); 
									}
								});
							}
							{
								chkDate = new JCheckBox("Date"); 
								panSort.add(chkDate); 
								chkDate.setHorizontalAlignment(JCheckBox.CENTER);
								chkDate.setSelected(false);
								chkDate.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										chkName.setSelected(!chkDate.isSelected());
										display(); 
									}
								});
							}
							{
								chkAsc = new JCheckBox("Asc"); 
								panSort.add(chkAsc); 
								chkAsc.setHorizontalAlignment(JCheckBox.CENTER);
								chkAsc.setSelected(true);
								chkAsc.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										chkDesc.setSelected(!chkAsc.isSelected());
										display(); 
									}
								});
							}
							{
								chkDesc = new JCheckBox("Desc"); 
								panSort.add(chkDesc); 
								chkDesc.setHorizontalAlignment(JCheckBox.CENTER);
								chkDesc.setSelected(false);
								chkDesc.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										chkAsc.setSelected(!chkDesc.isSelected());
										display(); 
									}
								});
							}
						}

					}
				}
			}
			{
				JPanel panCenter = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					scrud = new JScrollPane();
					panCenter.add(scrud, BorderLayout.CENTER);
					scrud.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrud.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					{
						{
							model_ud = new model_unidentified_deposit_issuance(); 
							tblud = new _JTableMain(model_ud);

							rowud = tblud.getRowHeader();
							scrud.setViewportView(tblud);

							tblud.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
							tblud.getColumnModel().getColumn(0).setMaxWidth(30);
							tblud.getColumnModel().getColumn(1).setMaxWidth(275);
							tblud.getColumnModel().getColumn(2).setMaxWidth(75);
							tblud.getColumnModel().getColumn(3).setMaxWidth(150);
							tblud.getColumnModel().getColumn(4).setMaxWidth(75);
							tblud.getColumnModel().getColumn(5).setMaxWidth(150);
							tblud.getColumnModel().getColumn(6).setMaxWidth(100);
							tblud.getColumnModel().getColumn(7).setMaxWidth(75);
							tblud.getColumnModel().getColumn(8).setMaxWidth(100);

							tblud.addMouseListener(new MouseListener() {
								public void mouseReleased(MouseEvent e) {

								}

								public void mousePressed(MouseEvent e) {

								}

								public void mouseExited(MouseEvent e) {

								}

								public void mouseEntered(MouseEvent e) {

								}

								public void mouseClicked(MouseEvent e) {
									if (e.getClickCount()>1) {
										lookup();
									}
								}
							});

							tblud.addKeyListener(new KeyListener() {
								public void keyTyped(KeyEvent e) {

								}

								public void keyReleased(KeyEvent e) {
									if (e.getKeyCode()==KeyEvent.VK_F2) {
										lookup();
									}
								}

								public void keyPressed(KeyEvent e) {

								}
							}); 

							tblud.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no", "ud_id", "pay_part_id", "co_id", "unit_id" );
							tblud.getColumnModel().getColumn(6).setCellRenderer(new DateRenderer(sdf));
							tblud.setSortable(false);
						}
						{
							rowud = tblud.getRowHeader();
							rowud.setModel(new DefaultListModel());
							scrud.setRowHeaderView(rowud);
							scrud.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
						}	
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new GridLayout(1, 2, 5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 60));
				{
					{
						JPanel panSearch = new JPanel(new BorderLayout(5, 5)); 
						panEnd.add(panSearch);
						panSearch.setBorder(JTBorderFactory.createTitleBorder("Search", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								txtSearch = new JTextField(""); 
								panSearch.add(txtSearch, BorderLayout.CENTER); 
								txtSearch.setHorizontalAlignment(JTextField.CENTER);
								txtSearch.setEditable(false);
								txtSearch.getDocument().addDocumentListener(new DocumentListener() {

									public void removeUpdate(DocumentEvent e) {
										search();
									}

									public void insertUpdate(DocumentEvent e) {
										search();
									}

									public void changedUpdate(DocumentEvent e) {
										search();
									}

									void search() {
										display(); 
									}
								});
							}
						}
					}
					{
						JPanel panButton = new JPanel(new GridLayout(1, 4, 5, 5)); 
						panEnd.add(panButton); 
						{

							{
								btnRefresh = new JButton("Refresh"); 
								panButton.add(btnRefresh); 
								btnRefresh.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										display(); 
									}
								});
							}
							{
								btnPost = new JButton("Post"); 
								panButton.add(btnPost); 
								btnPost.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if (JOptionPane.showConfirmDialog(null, "Sequence numbers would be created to selected deposits. Proceed?", "Confirm", 
												JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {

											try {
												sequence();
												previewApproved();

											} catch (Exception e) {
												JOptionPane.showMessageDialog(null, "Something went wrong.");
											}


										}
									}
								});
							}
							{
								btnDeactivate = new JButton("Deactivate"); 
								panButton.add(btnDeactivate); 
								btnDeactivate.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if (JOptionPane.showConfirmDialog(null, "Selected deposits would be deactivated. Proceed?", "Confirm", 
												JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {

											try {
												deactivate(); 
											} catch (Exception e) {
												JOptionPane.showMessageDialog(null, "Something went wrong.");
											}


										}
									}
								});
							}
							{
								btnPreview = new JButton("w/o Receipt"); 
								panButton.add(btnPreview); 
								btnPreview.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										displayList(); 
									}
								});
							}
						}
					}
				}
			}
		}
	}

	public static void display() {
		
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");
				controls(false);

				FncTables.clearTable(model_ud);
				DefaultListModel listModel = new DefaultListModel();
				rowud.setModel(listModel); 

				String strSQL = "select false, a.name, a.proj_alias, a.unit, a.bank_alias, a.bank_account, a.deposit_date, a.particulars, a.amount, \n" + 
						"a.entity_id, a.proj_id, a.pbl_id, a.seq_no, a.record_id, a.pay_part_id, a.co_id, a.unit_id \n" + 
						"from view_ud_for_issuance a \n"+ 
						"where not exists(select * from rf_pay_detail x where x.ud_id = a.record_id) \n" +
						"and (a.co_id = '"+txtCoID.getValue()+"' or '"+txtCoID.getValue()+"' = '') \n" +
						"ORDER BY a.name, a.proj_alias, a.unit, a.deposit_date";
						/*"order by (case when "+chkName.isSelected()+" then a.name, a.proj_alias, a.unit, a.deposit_date else null end) "+((chkAsc.isSelected())?"asc":"desc")+", \n" +
						"(case when "+chkDate.isSelected()+" then a.deposit_date else null end) "+((chkAsc.isSelected())?"asc":"desc")+"; "; */

				pgSelect db = new pgSelect();
				db.select(strSQL);

				System.out.println();
				System.out.println("strSQL: "+strSQL);

				if (db.isNotNull()){
					for (int x = 0; x < db.getRowCount(); x++) {
						model_ud.addRow(db.getResult()[x]);
						listModel.addElement(model_ud.getRowCount());
					}
				}

				FncGlobal.stopProgress();
				controls(true);
				return null;
			}
		};
		sw.execute();
		

	};

	private void lookup() {
		int row = tblud.getSelectedRow();

		String strSQL = "select pay_part_id, particulars, partdesc \n" + 
				"from mf_pay_particular \n" + 
				"where pay_part_id in (select pay_part_id from rf_payments where coalesce(remarks, '') ~* 'direct deposit' group by pay_part_id) \n" + 
				"order by pay_part_id, particulars, partdesc; "; 

		if (tblud.getSelectedColumn()==7) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Chart of Accounts", strSQL, false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setFilterClientName(true);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();

			if (data!=null) {
				model_ud.setValueAt(data[1], row, 7);
				model_ud.setValueAt(data[0], row, 14);
			}
		}
	}

	private static void controls(boolean blnDo) {
		btnRefresh.setEnabled(blnDo);
		btnPost.setEnabled(blnDo);
		btnDeactivate.setEnabled(blnDo);
		btnPreview.setEnabled(blnDo);

		txtCoID.setEnabled(blnDo);
		tblud.setEnabled(blnDo);

		chkName.setEnabled(blnDo);
		chkDate.setEnabled(blnDo);
		chkAsc.setEnabled(blnDo);
		chkDesc.setEnabled(blnDo);
	}
	
	private void previewApproved(){//

		String criteria = "List of Approved Deposits";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCo.getText());
		mapParameters.put("co_id", txtCoID.getValue());
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cur_date", Calendar.getInstance().getTime());

		FncReport.generateReport("/Reports/rptListOfDirectDepositForIssuance.jasper", reportTitle, txtCo.getText(), mapParameters);
	}

	private void sequence() {
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Sequencing...");
				controls(false);

				String sequence = ""; 
				pgSelect dbExec = new pgSelect(); 

				for (int x=0; x<model_ud.getRowCount(); x++) {
					if ((Boolean) model_ud.getValueAt(x, 0)) {

						System.out.println("select sp_create_pay_header('"+UserInfo.branch_id+"', \n" +
								"'"+model_ud.getValueAt(x, 9).toString()+"', \n" +
								"'"+model_ud.getValueAt(x, 10).toString()+"', \n" +
								"'"+model_ud.getValueAt(x, 16).toString()+"', \n" +
								model_ud.getValueAt(x, 12).toString()+"::int, \n" +
								model_ud.getValueAt(x, 8).toString()+"::numeric(19, 2), \n" +
								"'"+UserInfo.EmployeeCode+"', " +
								model_ud.getValueAt(x, 13).toString()+")");

						sequence = FncGlobal.GetString("select sp_create_pay_header('"+UserInfo.branch_id+"', \n" +
								"'"+model_ud.getValueAt(x, 9).toString()+"', \n" +
								"'"+model_ud.getValueAt(x, 10).toString()+"', \n" +
								"'"+model_ud.getValueAt(x, 16).toString()+"', \n" +
								model_ud.getValueAt(x, 12).toString()+"::int, \n" +
								model_ud.getValueAt(x, 8).toString()+"::numeric(19, 2), \n" +
								"'"+UserInfo.EmployeeCode+"', " +
								model_ud.getValueAt(x, 13).toString()+")");

						System.out.println("select sp_create_pay_detail( \n" + 
								"'"+sequence+"', \n" + 
								"'"+model_ud.getValueAt(x, 9).toString()+"', \n" + 
								"'"+model_ud.getValueAt(x, 14).toString()+"', \n" + 
								"'"+model_ud.getValueAt(x, 5).toString()+"', \n" + 
								"'"+model_ud.getValueAt(x, 8).toString()+"'::numeric(19, 2), \n" + 
								"'', \n" +
								""+model_ud.getValueAt(x, 13).toString()+", \n " +
								"'"+UserInfo.EmployeeCode+"'); ");

						dbExec = new pgSelect(); 
						dbExec.select("select sp_create_pay_detail( \n" + 
								"'"+sequence+"', \n" + 
								"'"+model_ud.getValueAt(x, 9).toString()+"', \n" + 
								"'"+model_ud.getValueAt(x, 14).toString()+"', \n" + 
								"'"+model_ud.getValueAt(x, 5).toString()+"', \n" + 
								"'"+model_ud.getValueAt(x, 8).toString()+"'::numeric(19, 2), \n" + 
								"'', \n" +
								""+model_ud.getValueAt(x, 13).toString()+", \n " +
								"'"+UserInfo.EmployeeCode+"'); ");
					}
				}

				display();
				FncGlobal.stopProgress();
				controls(true);
				JOptionPane.showMessageDialog(null, "Sequence number(s) created.");
				return null;
			}
		};
		sw.execute();
	}

	private void deactivate() {
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Deactivating...");
				controls(false);

				pgUpdate dbExec = new pgUpdate(); 
				
				for (int x=0; x<model_ud.getRowCount(); x++) {
					if ((Boolean) model_ud.getValueAt(x, 0)) {
						dbExec.executeUpdate("update tf_unidentified_dep \n" +
								"set status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', edited_date = now() \n" +
								"where record_id::int = '"+model_ud.getValueAt(x, 13).toString()+"'::int; ", true); 
					}
				}
				
				dbExec.commit();

				display();
				FncGlobal.stopProgress();
				controls(true);
				return null;
			}
		};
		sw.execute();
	}
	
	private void displayList() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		FncReport.generateReport("/Reports/rpt_direct_deposit_without_receipt.jasper", "Approved Direct Deposits without Receipt", "", mapParameters);
	}
}
