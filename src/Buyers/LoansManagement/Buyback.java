package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Database.pgUpdate;
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
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel.model_buyback;

public class Buyback extends _JInternalFrame implements _GUI, PropertyChangeListener  {

	/*
	 * 
	 * 
	 *  Variables
	 */
	private static final long serialVersionUID = -8656247576008850013L;
	private static String title = "Buyback";
	private Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Dimension size = new Dimension(800, 550);
	private _JDateChooser payment_date;
	private _JLookup lookupBank;
	private _JLookup lookupRPLF;
	private JComboBox cbClass;
	private model_buyback modelbuyback;
	private _JTableMain tablebuyback;
	private JList rowheaderbuyback;
	private model_buyback modelbuybacktotal;
	protected String bank_id;
	protected String bank_name;
	private double total_ob;
	private double total_interest;
	private double total_amount;
	private _JLookup lookupCompany;
	protected String company_id;
	protected String company_name;
	private String rplf_no;
	
	public Buyback() {
		super(title, false, true, false, true);
		initGUI();
	}

	public Buyback(String title) {
		super(title);
		initGUI();
	}

	public Buyback(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, false, true, true, true);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setTitle(title);
		this.setSize(size);
		this.setPreferredSize(size);
		{
			JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 105));
				pnlNorth.setBorder(emptyBorder);
				Dimension pnlSize = new Dimension(0, 100);
				pnlNorth.setBorder(lineBorder);
				{
					JPanel pnlNorthBelow = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthBelow, BorderLayout.NORTH);
					pnlNorthBelow.setPreferredSize(pnlSize);
					pnlNorthBelow.setBorder(emptyBorder);
					{
						JPanel pnlPost = new JPanel(new BorderLayout(5,5));
						pnlNorthBelow.add(pnlPost, BorderLayout.WEST);
						pnlPost.setPreferredSize(new Dimension(100, 0));
						{
							JButton btnPost = new JButton("Post");
							pnlPost.add(btnPost);
							btnPost.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									postFunction();
								}
							});
						}
					}
					{
						JPanel pnlInputs = new JPanel(new BorderLayout(5, 5));
						pnlNorthBelow.add(pnlInputs);
						{
							JPanel pnlInputsWest = new JPanel(new BorderLayout(5, 5));
							pnlInputsWest.setPreferredSize(new Dimension(150, 0));
							pnlInputs.add(pnlInputsWest);
							{
								JPanel pnlInputsWest1 = new JPanel(new BorderLayout(5, 5));
								pnlInputsWest.add(pnlInputsWest1);
								{
									JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlInputsWest1.add(pnlLabels, BorderLayout.WEST);
									{
										pnlLabels.add(new JLabel("RPLF No"));
										pnlLabels.add(new JLabel("Company"));
										pnlLabels.add(new JLabel("Bank"));
										pnlLabels.add(new JLabel("Client Class"));
									}
								}
								{
									JPanel pnlInputFields = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlInputsWest1.add(pnlInputFields, BorderLayout.CENTER);
									{
										lookupRPLF = new _JLookup(null, "RPLF No", 2);
										pnlInputFields.add(lookupRPLF);
										lookupRPLF.setReturnColumn(0);
										lookupRPLF.addLookupListener(new LookupListener() {
											
											@Override
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null) {
													
												}
												
											}
										});
									}
									{
										lookupCompany = new _JLookup(null, "Company", 1);
										pnlInputFields.add(lookupCompany);
										lookupCompany.setLookupSQL(sql_company());
										lookupCompany.addLookupListener(new LookupListener() {
											
											@Override
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null) {
													company_id		= (String) data[0];
													company_name	= (String) data[1];
													lookupCompany.setValue((String) data[2]);
													
													lookupRPLF.setLookupSQL(issued_rplf_no(company_id));
												}
												
											}
										});
									}
									{
										lookupBank = new _JLookup(null, "Bank", 1);
										pnlInputFields.add(lookupBank);
										lookupBank.setLookupSQL(SQL_BANK());
										lookupBank.addLookupListener(new LookupListener() {

											@Override
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null) {
													bank_id			= (String) data[0];
													bank_name		= (String) data[1];
													lookupBank.setValue((String) data[2]);
												}
												
											}
										});
									}
									{
										String[] typeStrings = { "Bank Finance", "Cash Buyer", "In House Finance", "Pagibig" };
										cbClass = new JComboBox(typeStrings);
										pnlInputFields.add(cbClass, BorderLayout.NORTH);
										cbClass.setSelectedIndex(0);
									}
								}
								{
									JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlLabels.setPreferredSize(new Dimension(80, 0));
									pnlInputsWest1.add(pnlLabels, BorderLayout.EAST);
									{
										/*just an empty space*/
									}
								}
							}
						}
					}
					{
						JPanel pnlButtons = new JPanel(new BorderLayout(5, 5));
						pnlNorthBelow.add(pnlButtons, BorderLayout.EAST);
						pnlButtons.setPreferredSize(new Dimension(300, 0));
						{
							JPanel pnlButtonsNorth = new JPanel(new BorderLayout(5, 5));
							pnlButtons.add(pnlButtonsNorth, BorderLayout.NORTH);
							{
								JPanel pnlLabel = new JPanel(new GridLayout(1, 1, 5, 5));
								pnlButtonsNorth.add(pnlLabel, BorderLayout.WEST);
								pnlLabel.setPreferredSize(new Dimension(100, 20));
								pnlLabel.add(new JLabel("Payment Date"));
							}
							{
								JPanel pnlDate = new JPanel(new GridLayout(1, 1, 5, 5));
								pnlButtonsNorth.add(pnlDate, BorderLayout.CENTER);
								{

									{
										payment_date = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlDate.add(payment_date);
										payment_date.getCalendarButton().setVisible(true);
										payment_date.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										payment_date.addPropertyChangeListener(this);
									}
								}
							}
						}
						{
							JPanel pnlButtonsSouth = new JPanel(new GridLayout(1, 4, 5, 5));
							pnlButtons.add(pnlButtonsSouth, BorderLayout.CENTER);
							{
								JButton btnProcess = new JButton("Process");
								pnlButtonsSouth.add(btnProcess);
								btnProcess.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										System.out.println("Process");
										processFunction();
									}
								});
							}
							{
								JButton btnScreen = new JButton("Screen");
								pnlButtonsSouth.add(btnScreen);
								btnScreen.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										screenFunction();
									}
								});
							}
							{
								JButton btnPrint = new JButton("Print");
								pnlButtonsSouth.add(btnPrint);
							}
							{
								JButton btnClose = new JButton("Close");
								pnlButtonsSouth.add(btnClose);
							}
						}
					}
				}
			}
			{
				JPanel pnlTable = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlTable, BorderLayout.CENTER);
				pnlTable.setPreferredSize(new Dimension(0, 225));
				pnlTable.setBorder(lineBorder);
				{
					_JScrollPaneMain scroll = new _JScrollPaneMain();
					pnlTable.add(scroll, BorderLayout.CENTER);
					{
						modelbuyback = new model_buyback();
						tablebuyback = new _JTableMain(modelbuyback);
						scroll.setViewportView(tablebuyback);
						tablebuyback.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tablebuyback.setSortable(false);
						{
							JList rowHeader = tablebuyback.getRowHeader();
							rowHeader.setModel(new DefaultListModel());
							scroll.setRowHeaderView(rowHeader);
							scroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							{
								int defaultWidth = 85;
								//tableBankPayments.getColumnModel().getColumn(0).setPreferredWidth(10);
								tablebuyback.getColumnModel().getColumn(1).setPreferredWidth(defaultWidth);
								tablebuyback.getColumnModel().getColumn(2).setPreferredWidth(defaultWidth);
								tablebuyback.getColumnModel().getColumn(3).setPreferredWidth(defaultWidth*2);
								tablebuyback.getColumnModel().getColumn(4).setPreferredWidth(250);
								tablebuyback.getColumnModel().getColumn(5).setPreferredWidth(defaultWidth+25);
								tablebuyback.getColumnModel().getColumn(10).setPreferredWidth(defaultWidth+25);
								tablebuyback.hideColumns("Entity ID","Proj ID","Pbl ID","Seq No");
							}
							rowheaderbuyback = tablebuyback.getRowHeader();
							scroll.setRowHeaderView(rowheaderbuyback);
							scroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						
						tablebuyback.addKeyListener(new KeyListener() {
							
							@Override
							public void keyTyped(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void keyReleased(KeyEvent e) {
								// TODO Auto-generated method stub
								int column = tablebuyback.convertColumnIndexToModel(tablebuyback.getSelectedColumn());
								if (e.getKeyCode() == KeyEvent.VK_F2) {
									int row = tablebuyback.getSelectedRow();
									if(column == 13) {
										buybackLookup(row);
									}
								}
								if (e.getKeyCode() == KeyEvent.VK_ENTER) {
									if(column == 11) {
										computeInterestUsingTable();
									}
								}
								if (e.getKeyCode() == KeyEvent.VK_ENTER) {
									if(column == 10) {
										compute_amount();
									}
								}
								
							}
							
							@Override
							public void keyPressed(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}
						});
						
						tablebuyback.addMouseListener(new MouseListener() {
							
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
								if (e.getClickCount() >= 2) {
									int column = tablebuyback.convertColumnIndexToModel(tablebuyback.getSelectedColumn());
									int row = tablebuyback.getSelectedRow();
									if(column == 13) {
										buybackLookup(row);
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
						});
						
						tablebuyback.getModel().addTableModelListener(new TableModelListener() {
							
							@Override
							public void tableChanged(TableModelEvent e) {
								computeTotal();
								
							}
						});
					}
					_JScrollPaneTotal scroll_total = new _JScrollPaneTotal(scroll);
					pnlTable.add(scroll_total, BorderLayout.SOUTH);
					{
						Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
						modelbuybacktotal = new model_buyback("total");
						modelbuybacktotal.addRow(new Object[] { "-", "Total", null, null, null, new BigDecimal(0.00), null, null, null, null, new BigDecimal(0.00) });
						
						_JTableTotal tableBankPayments_total = new _JTableTotal(modelbuybacktotal, tablebuyback);
						{
							tableBankPayments_total.hideColumns("Entity ID","Proj ID","Pbl ID","Seq No");
						}
						tableBankPayments_total.setFont(dialog11Bold);
						scroll_total.setViewportView(tableBankPayments_total);
						((_JTableTotal) tableBankPayments_total).setTotalLabel(1);
					}
				}
			}
		}
	}
	
	protected void postFunction() {
		int count = selected_count();
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Tag " + count + " selected clients?");
		
		if(confirmation == 0) {

			Object emp_code = UserInfo.EmployeeCode;
			FncGlobal.startProgress("Tagging.. Please wait...");
			pgSelect db = new pgSelect();
			
			for (int i = 0; i < modelbuyback.getRowCount(); i++) {
				boolean tag = (boolean) modelbuyback.getValueAt(i, 0);
				if (tag) {
					Object status = modelbuyback.getValueAt(i, 1);
					Object proj_alias = modelbuyback.getValueAt(i, 2);
					Object description = modelbuyback.getValueAt(i, 3);
					Object entity_name = modelbuyback.getValueAt(i, 4);
					Object entity_id = modelbuyback.getValueAt(i, 5);
					Object proj_id = modelbuyback.getValueAt(i, 6);
					Object pbl_id = modelbuyback.getValueAt(i, 7);
					Object seq_no = modelbuyback.getValueAt(i, 8);
					Object out_balance = modelbuyback.getValueAt(i, 9);
					Object interest = modelbuyback.getValueAt(i, 10);
					Object int_rate = modelbuyback.getValueAt(i, 11);
					Object total = modelbuyback.getValueAt(i, 12);
					Object reason = modelbuyback.getValueAt(i, 13);
					Object date = payment_date.getDateString();
							

					String SQL = " select sp_buyback_posting("
							+ "'"+status+"', "
							+ "'"+proj_alias+"', "
							+ "'"+description+"', "
							+ "'"+entity_name+"', "
							+ "'"+entity_id+"', "
							+ "'"+proj_id+"', "
							+ "'"+pbl_id+"', "
							+ ""+seq_no+", "
							+ ""+out_balance+", "
							+ ""+interest+", "
							+ ""+total+", "
							+ "'"+reason+"', "
							+ "'"+int_rate+"', "
							+ "'"+date+"', "
							+ "'"+emp_code+"');";
							

					FncSystem.out("sp_buyback_posting", SQL);
					db.select(SQL);
					rplf_no = (String) db.getResult()[0][0];
				}
			}
			JOptionPane.showMessageDialog(null, "Successfully tagged " + count + " clients"
					+"\nRPLF NO: "+rplf_no, "Tagging Successful", JOptionPane.PLAIN_MESSAGE);
			processFunction();
		}else {
			System.out.println("Cancelled");
		}
	}

	private void screenFunction() {
		
		pgUpdate dbExec = new pgUpdate();
		Object emp_code = UserInfo.EmployeeCode;
		dbExec.executeUpdate("delete from tmp_buyback where c_emp_code = '"+emp_code+"'", true);
		dbExec.commit();
		
		for (int i = 0; i < modelbuyback.getRowCount(); i++) {
			boolean tag = (boolean) modelbuyback.getValueAt(i, 0);
			if (tag) {
				Object status = modelbuyback.getValueAt(i, 1);
				Object proj_alias = modelbuyback.getValueAt(i, 2);
				Object description = modelbuyback.getValueAt(i, 3);
				Object entity_name = modelbuyback.getValueAt(i, 4);
				Object entity_id = modelbuyback.getValueAt(i, 5);
				Object proj_id = modelbuyback.getValueAt(i, 6);
				Object pbl_id = modelbuyback.getValueAt(i, 7);
				Object seq_no = modelbuyback.getValueAt(i, 8);
				Object out_balance = modelbuyback.getValueAt(i, 9);
				Object interest = modelbuyback.getValueAt(i, 10);
				Object int_rate = modelbuyback.getValueAt(i, 11);
				Object total = modelbuyback.getValueAt(i, 12);
				Object reason = modelbuyback.getValueAt(i, 13);
				Object latest_ledger_date = modelbuyback.getValueAt(i, 14);
				
				dbExec = new pgUpdate();
				dbExec.executeUpdate("INSERT INTO tmp_buyback(\n"
						+ "	c_status,"
						+ " c_proj_alias,"
						+ " c_description,"
						+ " c_entity_name,"
						+ " c_entity_id,"
						+ " c_proj_id,"
						+ " c_pbl_id,"
						+ " c_seq_no,"
						+ " c_out_balance,"
						+ " c_interest,"
						+ " c_total,"
						+ " c_reason,"
						+ " c_int_rate,"
						+ " c_latest_ledger_date,"
						+ " c_emp_code)\n"
						+ "	VALUES ("
						+ "'"+status+"', "
						+ "'"+proj_alias+"', "
						+ "'"+description+"', "
						+ "'"+entity_name+"', "
						+ "'"+entity_id+"', "
						+ "'"+proj_id+"', "
						+ "'"+pbl_id+"', "
						+ ""+seq_no+", "
						+ ""+out_balance+", "
						+ ""+interest+", "
						+ ""+total+", "
						+ "'"+reason+"', "
						+ "'"+int_rate+"', "
						+ "'"+latest_ledger_date+"', "
						+ "'"+emp_code+"');", true);
				dbExec.commit();
			}
		}
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		String company_logo = sql_getCompanyLogo(company_id);
		String date = payment_date.getDateString();
		mapParameters.put("p_logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("p_payment_date", date);
		mapParameters.put("p_co_id", company_id);
		mapParameters.put("p_bank_id", bank_id);
		mapParameters.put("p_emp_code", emp_code);
		mapParameters.put("p_prepared_by", UserInfo.Alias);
		FncReport.generateReport("/Reports/rpt_buyback.jasper", "Buyback For "+date, "", mapParameters);
	}
	
	protected void computeInterestUsingTable() {
		// TODO Auto-generated method stub
		int row = tablebuyback.getSelectedRow();
		
		String entity_id = (String) modelbuyback.getValueAt(row, 5);
		String proj_id = (String) modelbuyback.getValueAt(row, 6);
		String pbl_id = (String) modelbuyback.getValueAt(row, 7);
		Object seq_no = modelbuyback.getValueAt(row, 8);
		String date = payment_date.getDateString();
		Object int_rate = modelbuyback.getValueAt(row, 11);
		String SQL= "select * from sp_buyback_interest_calculator('"+entity_id+"','"+proj_id+"','"+pbl_id+"',"+seq_no+",'"+date+"','"+int_rate+"')";
		FncSystem.out("sp_buyback_interest_calculator", SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		try {
			Object interest = db.getResult()[0][0];
			Object total = db.getResult()[0][1];
			modelbuyback.setValueAt(interest, row, 10);
			modelbuyback.setValueAt(total, row, 12);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void computeInterest() {
		// TODO Auto-generated method stub
		if (modelbuyback.getRowCount() != 0) {
			for (int i = 0; i < modelbuyback.getRowCount(); i++) {
				String entity_id = (String) modelbuyback.getValueAt(i, 5);
				String proj_id = (String) modelbuyback.getValueAt(i, 6);
				String pbl_id = (String) modelbuyback.getValueAt(i, 7);
				Object seq_no = modelbuyback.getValueAt(i, 8);
				String date = payment_date.getDateString();
				Object int_rate = modelbuyback.getValueAt(i, 11);
				String SQL= "select * from sp_buyback_interest_calculator('"+entity_id+"','"+proj_id+"','"+pbl_id+"',"+seq_no+",'"+date+"','"+int_rate+"')";
				FncSystem.out("computeInterest_sp_buyback_interest_calculator", SQL);
				
				pgSelect db = new pgSelect();
				db.select(SQL);
				try {
					Object interest = db.getResult()[0][0];
					Object total = db.getResult()[0][1];
					modelbuyback.setValueAt(interest, i, 10);
					modelbuyback.setValueAt(total, i, 12);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	protected void buybackLookup(int row) {
		// TODO Auto-generated method stub
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Reason", buyback_reason(), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setFilterClientName(true);
		dlg.setVisible(true);
		Object[] data = dlg.getReturnDataSet();
		if (data != null) {
			modelbuyback.setValueAt(data[0], row, 13);
			modelbuyback.setValueAt(data[1], row, 1);
			tablebuyback.packSelected();
		}
		
	}

	private void computeTotal() {
		total_ob = 0.0;
		total_interest = 0.0;
		total_amount = 0.0;
		int count = selected_count();
		for (int i = 0; i < modelbuyback.getRowCount(); i++) {
			boolean tag = (boolean) modelbuyback.getValueAt(i, 0);
			if(tag) {
				Object ob = modelbuyback.getValueAt(i, 9);
				total_ob += Double.parseDouble(ob.toString());
				Object interest = modelbuyback.getValueAt(i, 10);
				total_interest += Double.parseDouble(interest.toString());
				Object amount = modelbuyback.getValueAt(i, 11);
				total_amount += Double.parseDouble(amount.toString());
			}
		}
		//System.out.println("Total amount: "+total_amount);
		FncTables.clearTable(modelbuybacktotal);
		modelbuybacktotal.addRow(new Object[] { count, "Total", null, null, null, null, null, null, null, total_ob, total_interest, total_amount, null });
	}
	
	private int selected_count() {
		int count = 0;
		for (int i = 0; i < modelbuyback.getRowCount(); i++) {
			boolean tag = (boolean) modelbuyback.getValueAt(i, 0);
			if(tag) {
				count += 1;
			}
		}
		return count;
	}
	
	private void processFunction() {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Gathering.. Please wait...");
				System.out.println("Bank ID: "+bank_id);
				String class_type = (String) cbClass.getSelectedItem();
				System.out.println("Class Type: "+class_type);
				System.out.println("Company ID: "+company_id);
				if (class_type.equals("Bank Finance")) {
					class_type = "05";
				}
				if (class_type.equals("Pagibig")) {
					class_type = "04";
				}
				if (class_type.equals("Cash Buyer")) {
					class_type = "03";
				}
				if (class_type.equals("In House Finance")) {
					class_type = "02";
				}
				modelbuyback.clear();
				DefaultListModel listModel = new DefaultListModel();
				rowheaderbuyback.setModel(listModel);
				String SQL="select * from view_buyback('"+bank_id+"','"+class_type+"','"+company_id+"')";
				FncSystem.out("Display Buyback For Tagging", SQL);
				pgSelect db = new pgSelect();
				db.select(SQL);
				if(db.isNotNull()) {
					for(int x=0; x < db.getRowCount(); x++){
						Object c_tag = db.getResult()[x][0];
						Object c_status = db.getResult()[x][1];
						Object c_proj_alias = db.getResult()[x][2];
						Object c_description = db.getResult()[x][3];
						Object c_entity_name = db.getResult()[x][4];
						Object c_entity_id = db.getResult()[x][5];
						Object c_proj_id = db.getResult()[x][6];
						Object c_pbl_id = db.getResult()[x][7];
						Object c_seq_no = db.getResult()[x][8];
						Object c_out_balance = db.getResult()[x][9];
						Object c_interest = db.getResult()[x][10];
						Object c_total = db.getResult()[x][11];
						Object c_reason = db.getResult()[x][12];
						Object c_int_rate = db.getResult()[x][13];
						Object c_latest_ledger_date = db.getResult()[x][14];
						String date = payment_date.getDateString();
						
						String SQL2= "select * from sp_buyback_interest_calculator('"+c_entity_id+"','"+c_proj_id+"','"+c_pbl_id+"',"+c_seq_no+",'"+date+"','"+c_int_rate+"')";
						FncSystem.out("processFunction_sp_buyback_interest_calculator", SQL2);
						
						pgSelect db2 = new pgSelect();
						db2.select(SQL2);
						Object interest = db2.getResult()[0][0];
						Object total = db2.getResult()[0][1];
						try {
							modelbuyback.addRow(new Object[] 
									{
											c_tag,
											c_status,
											c_proj_alias,
											c_description,
											c_entity_name,
											c_entity_id,
											c_proj_id,
											c_pbl_id,
											c_seq_no,
											c_out_balance,
											interest,
											c_int_rate,
											total,
											c_reason,
											c_latest_ledger_date
							        });

							listModel.addElement(modelbuyback.getRowCount());	
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
				int count = modelbuyback.getRowCount();
				System.out.println("modelbuyback row/s: "+count);
				if(modelbuyback.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, count+" Results found", "For tagging", JOptionPane.PLAIN_MESSAGE);
				}
				FncGlobal.stopProgress();
			}
		};
		FncGlobal.loadThread("Gathering clients ...", runPreview);
	}

	public static String SQL_BANK() {
		String SQL = "select \n"
				+ "bank_id as \"Bank ID\",\n"
				+ "bank_name as \"Bank Name\",\n"
				+ "bank_alias as \"Bank Alias\"\n"
				+ "from mf_bank\n"
				+ "where status_id = 'A'\n"
				+ "and server_id is null\n"
				+ "and proj_server is null\n"
				+ "order by \"Bank Name\" asc";
		return SQL;
	}

	private String buyback_reason() {
		// TODO Auto-generated method stub
		return "select reason_desc as \"Reason Desc\",\n"
				+ "alias as \"Alias\"\n"
				+ "from mf_buyback_reason \n"
				+ "where status_id = 'A'";
	}
	
	private String sql_company() {
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\" FROM mf_company order by co_id ";
		return SQL;
	}
	
	private String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		String property = evt.getPropertyName();
		System.out.println("property: "+property);
		if(property.equals("date")) {
			computeInterest();
		}
	}
	/*Jari*/
	private String issued_rplf_no(String co_id) {
		System.out.println("co_id: "+company_id);
		String SQL = "SELECT TRIM(a.rplf_no)::VARCHAR as \"Request No.\",\n"
				+ "TRIM(get_client_name(entity_id1)) as \"Payee\",\n"
				+ "get_client_name(b.entity_id) as \"Created By\",\n"
				+ "a.date_created as \"Date Created\"\n"
				+ "from rf_request_header a\n"
				+ "left join em_employee b on a.created_by = b.emp_code\n"
				+ "where exists(select * from rf_buyback where rplf_no = a.rplf_no \n"
				+ "			 and proj_id in (select proj_id from mf_project where co_id = '"+co_id+"') and status_id = 'A')\n"
				+ "and a.co_id = '"+co_id+"'\n"
				+ "order by a.date_created desc;";
		FncSystem.out("ISSUED RPLF SQL", SQL);
		return SQL;
	}
	
	protected void compute_amount() {
		// TODO Auto-generated method stub
		int row = tablebuyback.getSelectedRow();
		

		Object ob = modelbuyback.getValueAt(row, 9);
		Double double_ob = Double.parseDouble(ob.toString());
		
		Object interest = modelbuyback.getValueAt(row, 10);
		Double double_interest = Double.parseDouble(interest.toString());
		
		Double total = (double_ob + double_interest);
		
		modelbuyback.setValueAt(total, row, 12);
	}

}
