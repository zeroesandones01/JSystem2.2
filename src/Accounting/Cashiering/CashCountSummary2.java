package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;

public class CashCountSummary2 extends _JInternalFrame {

	private static final long serialVersionUID = -4478540326000585720L;
	static String title = "Cash Count 2";
	static Dimension size = new Dimension(1000, 600);
	
	private _JLookup lookupCompany;
	private _JLookup lookupOfficeBranch;
	private _JLookup lookupTransNo;
	
	private JTextField txtCompany;
	private JTextField txtBranch;
	private JTextField txtTransNo;
	
	private _JDateChooser dteCash;
	
	private JButton btnAddNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnPost;
	private JButton btnRefresh;
	private JButton btnPreview;

	private CashCountSummary_CashCount cc;  
	private CashCountSummary_CashDeposit cd; 
	private CashCountSummary_OtherDeposit od; 
	private CashCountSummary_UnusedReceipt ur; 
	
	private static Boolean blnEdit = false; 
	
	public CashCountSummary2() {
		super(title, false, true, false, true);
		initGUI();
	}

	public CashCountSummary2(String title) {
		super(title);

	}

	public CashCountSummary2(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}
	
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JPanel panPage = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 140));
				{
					{
						JPanel panDetails = new JPanel(new GridLayout(3, 1, 5, 5)); 
						panPage.add(panDetails, BorderLayout.CENTER);
						panDetails.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JPanel panCompany = new JPanel(new BorderLayout(5, 5));
								panDetails.add(panCompany); 
								{
									{
										JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5)); 
										panCompany.add(panLabel, BorderLayout.LINE_START);
										panLabel.setPreferredSize(new Dimension(200, 0));
										{
											{
												JLabel label = new JLabel("Company"); 
												panLabel.add(label); 
												label.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												lookupCompany = new _JLookup(null, "Company",0,2);
												panLabel.add(lookupCompany);
												lookupCompany.setLookupSQL(lookupMethods.getCompany());
												lookupCompany.setReturnColumn(0);
												lookupCompany.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup)event.getSource()).getDataSet();
														
														if (data != null) {
															lookupCompany.setValue((String) data[0].toString());
															txtCompany.setText((String) data[1].toString());
															
															lookupTransNo.setLookupSQL(getTransNo(lookupCompany.getValue(), lookupOfficeBranch.getValue()));
														}
													}
												});
												lookupCompany.addKeyListener(new KeyListener() {

													public void keyTyped(KeyEvent e) {
														if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
															lookupCompany.setValue("");
															txtCompany.setText("");
															
															lookupTransNo.setLookupSQL(getTransNo(lookupCompany.getValue(), lookupOfficeBranch.getValue()));
														}
													}

													public void keyReleased(KeyEvent e) {
														
													}

													public void keyPressed(KeyEvent e) {
														
													}
												});
												lookupCompany.setValue("");
											}
										}
									}
									{
										txtCompany = new JTextField(""); 
										panCompany.add(txtCompany, BorderLayout.CENTER); 
										txtCompany.setHorizontalAlignment(JTextField.CENTER);
										txtCompany.setEditable(false);
									}
								}
							}
							{
								JPanel panBranch = new JPanel(new BorderLayout(5, 5));
								panDetails.add(panBranch); 
								{
									{
										JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5)); 
										panBranch.add(panLabel, BorderLayout.LINE_START);
										panLabel.setPreferredSize(new Dimension(200, 0));
										{
											{
												JLabel label = new JLabel("Branch"); 
												panLabel.add(label); 
												label.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												lookupOfficeBranch = new _JLookup(null, "Phase", 0, 2);
												panLabel.add(lookupOfficeBranch);
												lookupOfficeBranch.setLookupSQL(lookupMethods.getOfficeBranch());
												lookupOfficeBranch.setReturnColumn(0);
												lookupOfficeBranch.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup)event.getSource()).getDataSet();
														
														if (data != null) {
															lookupOfficeBranch.setValue((String) data[0].toString());
															txtBranch.setText((String) data[1].toString());
															
															lookupTransNo.setLookupSQL(getTransNo(lookupCompany.getValue(), lookupOfficeBranch.getValue()));
														}
													}
												});
												lookupOfficeBranch.addKeyListener(new KeyListener() {

													public void keyTyped(KeyEvent e) {
														if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
															lookupOfficeBranch.setValue("");
															txtBranch.setText("");
															
															lookupTransNo.setLookupSQL(getTransNo(lookupCompany.getValue(), lookupOfficeBranch.getValue()));
														}
													}

													public void keyReleased(KeyEvent e) {
														
													}

													public void keyPressed(KeyEvent e) {
														
													}
												});
												lookupOfficeBranch.setValue("");
											}
										}
									}
									{
										txtBranch = new JTextField(""); 
										panBranch.add(txtBranch, BorderLayout.CENTER); 
										txtBranch.setHorizontalAlignment(JTextField.CENTER);
										txtBranch.setEditable(false);
									}
								}
							}
							{
								JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
								panDetails.add(panel); 
								{
									{
										JPanel panDate = new JPanel(new BorderLayout(5, 5)); 
										panel.add(panDate); 
										{
											{
												JLabel label = new JLabel("Date"); 
												panDate.add(label, BorderLayout.LINE_START); 
												label.setPreferredSize(new Dimension(96, 0));
											}
											{
												dteCash = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
												panDate.add(dteCash, BorderLayout.CENTER);
												dteCash.getCalendarButton().setVisible(true);
												dteCash.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
											}
										}

									}
									{
										JPanel panCash = new JPanel(new BorderLayout(5, 5)); 
										panel.add(panCash); 
										{
											{
												JLabel label = new JLabel("Cash Count"); 
												panCash.add(label, BorderLayout.LINE_START); 
												label.setHorizontalAlignment(JLabel.CENTER);
												label.setPreferredSize(new Dimension(100, 0));
											}
											{
												lookupTransNo = new _JLookup(null, "Transaction No.", 0, 2);
												panCash.add(lookupTransNo, BorderLayout.CENTER);
												lookupTransNo.setLookupSQL(getTransNo("", ""));
												lookupTransNo.setReturnColumn(0);
												lookupTransNo.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup)event.getSource()).getDataSet();
														
														if (data != null) {
															controlState(false, true, false, true, true, true);
														}
													}
												});
											}
										}

									}
								}
							}
						}
					}
					{
						JButton btnGenerate = new JButton("Generate"); 
						panPage.add(btnGenerate, BorderLayout.LINE_END); 
						btnGenerate.setPreferredSize(new Dimension(150, 0));
						{
							
						}
					}
				}
				
			}
			{
				JPanel panCenter = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					JTabbedPane tab = new JTabbedPane(); 
					panCenter.add(tab, BorderLayout.CENTER); 
					{
						JPanel pancc = new JPanel(new BorderLayout(5, 5)); 
						pancc.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							cc = new CashCountSummary_CashCount(new BorderLayout(0, 0)); 
							pancc.add(cc, BorderLayout.LINE_START); 
							cc.setPreferredSize(new Dimension(400, 0));
						}
						
						JPanel pancd = new JPanel(new BorderLayout(5, 5));
						pancd.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							cd = new CashCountSummary_CashDeposit(new BorderLayout(0, 0)); 
							pancd.add(cd, BorderLayout.LINE_START); 
							cd.setPreferredSize(new Dimension(600, 0));
						}
						
						JPanel panod = new JPanel(new BorderLayout(5, 5));
						panod.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							od = new CashCountSummary_OtherDeposit(new BorderLayout(0, 0)); 
							panod.add(od, BorderLayout.LINE_START); 
							od.setPreferredSize(new Dimension(400, 0));
						}
						
						JPanel panur = new JPanel(new BorderLayout(5, 5));
						panur.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							ur = new CashCountSummary_UnusedReceipt(new BorderLayout(0, 0)); 
							panur.add(ur, BorderLayout.CENTER); 
							ur.setPreferredSize(new Dimension(400, 0));
						}
						
						tab.add("Cash Count", pancc);
						tab.add("Cash Deposit", pancd);
						tab.add("Other Deposit", panod);
						tab.add("Unused Receit", panur);
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new GridLayout(1, 6, 5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnAddNew = new JButton("Add New"); 
						panEnd.add(btnAddNew);
						btnAddNew.setEnabled(false);
						btnAddNew.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								blnEdit = false; 
								
								if (validateEntry()) {
									controlState(false, false, true, false, false, true); 
									inputState(false, false, false, false, true);
									
									cc.reload();
									cd.reload(lookupCompany.getValue(), lookupOfficeBranch.getValue(), dteCash.getDate());
									od.reload();
									ur.reload(lookupOfficeBranch.getValue(), dteCash.getDate());
								} else {
									JOptionPane.showMessageDialog(null, "Please input all values"); 
								}
								
							}
						});
					}
					{
						btnEdit = new JButton("Edit"); 
						panEnd.add(btnEdit);
						btnEdit.setEnabled(false);
						btnEdit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								blnEdit = true; 
								controlState(false, false, true, false, false, true);
								inputState(false, false, false, false, true);
							}
						});
					}
					{
						btnSave = new JButton("Save"); 
						panEnd.add(btnSave);
						btnSave.setEnabled(false);
						btnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
								try {
									if (!blnEdit) {
										Integer cash_count = FncGlobal.GetInteger("select * from fn_get_cash_count()");
										lookupTransNo.setValue(cash_count.toString());
										
										cc.insertCashCount(cash_count, dteCash.getDate(), lookupCompany.getValue(), lookupOfficeBranch.getValue()); 
										cd.insertCashDeposit(cash_count); 
										od.insertOtherDeposit(cash_count); 
										ur.insertUnusedReceipt(cash_count, dteCash.getDate(), lookupCompany.getValue(), lookupOfficeBranch.getValue());
									} else {
										
									}
									
									blnEdit = false; 
									controlState(true, false, false, false, false, true);
									inputState(true, true, true, true, false);
									
									JOptionPane.showMessageDialog(null, "Saved!");	
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, "Something went wrong.");
								}
							}
						});
					}
					{
						btnPost = new JButton("Post"); 
						panEnd.add(btnPost);
						btnPost.setEnabled(false);
						btnPost.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								controlState(true, false, false, false, false, true);
							}
						});
					}
					{
						btnPreview = new JButton("Preview"); 
						panEnd.add(btnPreview);
						btnPreview.setEnabled(false);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
							}
						});
					}
					{
						btnRefresh = new JButton("Refresh"); 
						panEnd.add(btnRefresh);
						btnRefresh.setEnabled(false);
						btnRefresh.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								blnEdit = false; 
								clear();
								controlState(true, false, false, false, false, true);
								inputState(true, true, true, true, false);
							}
						});
					}
				}
			}
		}	
		
		controlState(true, false, false, false, false, true);
	}

	private String getTransNo(String strCoID, String strBranch) {
		return "select a.trans_no as \"Trans. No.\", a.trans_date::date as \"Date\", trim(a.branch_id) as \"Branch ID\", \n" + 
		"trim(b.branch_name) as \"Branch Name\", \n" + 
		"(case when a.status_id = 'A' then 'ACTIVE' when a.status_id = 'P' then 'POSTED' else 'INACTIVE' end) as \"Status\", \n" + 
		"a.co_id, c.company_name \n" + 
		"from rf_cash_count_summary a \n" + 
		"left join mf_office_branch b on a.branch_id = b.branch_id \n" + 
		"left join mf_company c on a.co_id = c.co_id \n" +
		"where (a.co_id = '"+strCoID+"' or '"+strCoID+"' = '') \n" +
		"and (a.branch_id = '"+strBranch+"' or '"+strBranch+"' = '') \n" +
		"order by a.trans_no desc;";
	}
	
	private void controlState(Boolean add, Boolean edit, Boolean save, Boolean post, Boolean preview, Boolean refresh) {
		btnAddNew.setEnabled(add);
		btnEdit.setEnabled(edit);
		btnSave.setEnabled(save);
		btnPost.setEnabled(post);
		btnPreview.setEnabled(preview);
		btnRefresh.setEnabled(refresh);
	}
	
	private void inputState(Boolean company, Boolean branch, Boolean cashcount, Boolean date, Boolean tables) {
		lookupCompany.setEnabled(company);
		lookupOfficeBranch.setEnabled(branch);
		lookupTransNo.setEnabled(cashcount);
		dteCash.setEnabled(date);
		
		CashCountSummary_CashCount.setState(tables);
		CashCountSummary_CashDeposit.setState(tables);
	}
	
	private void clear() {
		lookupCompany.setValue("");
		txtCompany.setText("");
		lookupOfficeBranch.setValue("");
		txtBranch.setText("");
		lookupTransNo.setValue("");
		dteCash.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		
		lookupTransNo.setLookupSQL(getTransNo(lookupCompany.getValue(), lookupOfficeBranch.getValue()));
		
		cc.clear();
		cd.clear();
		od.clear();
	}
	
	private boolean validateEntry() {
		Boolean proceed = true; 
		
		if (lookupCompany.getValue().equals("")) {
			proceed = false; 
		}
		
		if (lookupOfficeBranch.getValue().equals("")) {
			proceed = false; 
		}
		
		if (dteCash.getDate()==null) {
			proceed = false; 
		}
		
		return proceed; 
	}
}
