package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tablemodel.modelDRF_particulars_total;
import tablemodel.model_BankPayments;

public class BankPayments extends _JInternalFrame implements _GUI {
	
	/**
	 * hippity ?
	 */
	private static final long serialVersionUID = -8656247576008850013L;
	private static String title = "Bank Payments";
	private Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Dimension size = new Dimension(800, 550);
	private _JLookup lookupProject;
	protected String project_id;
	protected String project_name;
	private _JLookup lookupBank;
	protected String bank_id;
	protected String bank_name;
	private _JLookup lookupJV;
	private _JLookup lookupRPLF;
	private JComboBox cbType;
	private _JDateChooser ma_date;
	private model_BankPayments modelBankPayments;
	private model_BankPayments modelBankPaymentsTotal;
	private JList rowheaderBankPayments;
	private _JTableMain tableBankPayments;
	private String rplf_no;
	private String jv_no;
	private String batch_no;
	private _JLookup lookupCompany;
	private String company_id;
	private String company_name;
	private Double total_amount;
	private double total_bank_balance;
	protected Object local_rplf_no;
	
	public BankPayments() {
		super(title, false, true, false, true);
		initGUI();
	}

	public BankPayments(String title) {
		super(title);
		initGUI();
	}

	public BankPayments(String title, boolean resizable, boolean closable, boolean maximizable,
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
				/*{
					JPanel pnlNorthAbove = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthAbove, BorderLayout.NORTH);
					pnlNorthAbove.setPreferredSize(pnlSize);
					pnlNorthAbove.setBorder(lineBorder);
				}*/
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
							JPanel pnlInputsWest = new JPanel(new GridLayout(1, 2, 5, 5));
							pnlInputs.add(pnlInputsWest);
							{
								JPanel pnlInputsWest1 = new JPanel(new BorderLayout(5, 5));
								pnlInputsWest.add(pnlInputsWest1);
								{
									JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlInputsWest1.add(pnlLabels, BorderLayout.WEST);
									{
										pnlLabels.add(new JLabel("MA Date"));
										pnlLabels.add(new JLabel("Company"));
										pnlLabels.add(new JLabel("Project"));
										pnlLabels.add(new JLabel("Bank"));
									}
								}
								{
									JPanel pnlInputFields = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlInputsWest1.add(pnlInputFields, BorderLayout.CENTER);
									{
										ma_date = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlInputFields.add(ma_date);
										ma_date.getCalendarButton().setVisible(true);
										ma_date.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
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
													
													lookupProject.setLookupSQL(sql_project(company_id));
													
													lookupRPLF.setLookupSQL(issued_rplf_no(company_id));
													lookupJV.setLookupSQL(issued_jv_no(company_id));
													project_id		= "000";
													lookupProject.setValue("ALL");
												}
												
											}
										});
									}
									{
										lookupProject = new _JLookup(null, "Project", 1);
										pnlInputFields.add(lookupProject);
										lookupProject.addLookupListener(new LookupListener() {
											
											@Override
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null) {
													project_id		= (String) data[0];
													project_name	= (String) data[1];
													lookupProject.setValue((String) data[2]);
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
								}
							}
							{
								JPanel pnlInputsWest2 = new JPanel(new BorderLayout(5, 5));
								pnlInputsWest.add(pnlInputsWest2);
								{
									JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlInputsWest2.add(pnlLabels, BorderLayout.WEST);
									{
										pnlLabels.add(new JLabel("RPLF No"));
										pnlLabels.add(new JLabel("JV No"));
									}
								}
								{
									JPanel pnlInputFields = new JPanel(new GridLayout(4, 1, 5, 5));
									pnlInputsWest2.add(pnlInputFields, BorderLayout.CENTER);
									{
										lookupRPLF = new _JLookup(null, "RPLF No", 2);
										pnlInputFields.add(lookupRPLF);
										lookupRPLF.setReturnColumn(0);
										lookupRPLF.addLookupListener(new LookupListener() {
											
											@Override
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null) {
													local_rplf_no = data[0];
													System.out.println("local_rplf_no "+local_rplf_no);
												}
												
											}
										});
									}
									{
										lookupJV = new _JLookup(null, "JV No", 2);
										pnlInputFields.add(lookupJV);
										lookupJV.setReturnColumn(0);
										lookupJV.addLookupListener(new LookupListener() {
											
											@Override
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null) {
													
												}
												
											}
										});
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
								pnlLabel.add(new JLabel("Payment Type"));
							}
							{
								JPanel pnlDropdown = new JPanel(new GridLayout(1, 1, 5, 5));
								pnlButtonsNorth.add(pnlDropdown, BorderLayout.CENTER);
								{
									String[] typeStrings = { "Check", "Cash", "Fund Transfer" };
									cbType = new JComboBox(typeStrings);
									pnlDropdown.add(cbType, BorderLayout.NORTH);
									cbType.setSelectedIndex(0);
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
						modelBankPayments = new model_BankPayments();
						tableBankPayments = new _JTableMain(modelBankPayments);
						scroll.setViewportView(tableBankPayments);
						tableBankPayments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tableBankPayments.setSortable(false);
						{
							JList rowHeader = tableBankPayments.getRowHeader();
							rowHeader.setModel(new DefaultListModel());
							scroll.setRowHeaderView(rowHeader);
							scroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							{
								int defaultWidth = 85;
								//tableBankPayments.getColumnModel().getColumn(0).setPreferredWidth(10);
								tableBankPayments.getColumnModel().getColumn(1).setPreferredWidth(defaultWidth);
								tableBankPayments.getColumnModel().getColumn(2).setPreferredWidth(defaultWidth);
								tableBankPayments.getColumnModel().getColumn(3).setPreferredWidth(defaultWidth*2);
								tableBankPayments.getColumnModel().getColumn(4).setPreferredWidth(250);
								tableBankPayments.getColumnModel().getColumn(5).setPreferredWidth(defaultWidth+25);
								tableBankPayments.getColumnModel().getColumn(10).setPreferredWidth(defaultWidth+25);
								tableBankPayments.hideColumns("Entity ID","Proj ID","Pbl ID","Seq No");
							}
							rowheaderBankPayments = tableBankPayments.getRowHeader();
							scroll.setRowHeaderView(rowheaderBankPayments);
							scroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						tableBankPayments.getModel().addTableModelListener(new TableModelListener() {
							
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
						modelBankPaymentsTotal = new model_BankPayments("total");
						modelBankPaymentsTotal.addRow(new Object[] { "-", "Total", null, null, null, new BigDecimal(0.00), null, null, null, null, new BigDecimal(0.00) });
						
						_JTableTotal tableBankPayments_total = new _JTableTotal(modelBankPaymentsTotal, tableBankPayments);
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
	
	private void postFunction() {
		int count = selected_count();
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Tag " + count + " selected clients?");
		
		if(confirmation == 0) {
			tagBankPayments();
			processFunction();
			lookupRPLF.setValue(rplf_no);
			lookupJV.setValue(jv_no);
		} else {
			System.out.println("Cancelled");
		}
	}
	
	private void tagBankPayments() {
		rplf_no = null;
		jv_no = null;
		batch_no = null;
		int count = 0;
		String date = ma_date.getDateString();
		String emp_code = UserInfo.EmployeeCode;
		for (int i = 0; i < modelBankPayments.getRowCount(); i++) {
			boolean tag = (boolean) modelBankPayments.getValueAt(i, 0);
			if(tag) {
				count += 1;
				
				String entity_id = (String) modelBankPayments.getValueAt(i, 6);
				String proj_id = (String) modelBankPayments.getValueAt(i, 7);
				String pbl_id = (String) modelBankPayments.getValueAt(i, 8);
				Object seq_no = modelBankPayments.getValueAt(i, 9);
				Object amount = modelBankPayments.getValueAt(i, 5);

				/*System.out.println("Entity ID: "+entity_id);
				System.out.println("Proj ID: "+proj_id);
				System.out.println("Pbl ID: "+pbl_id);
				System.out.println("Seq No: "+seq_no);
				System.out.println("Amount : "+amount);
				System.out.println("Rplf No: "+rplf_no);
				System.out.println("Jv No: "+jv_no);
				System.out.println("*******************");*/
				
				String SQL = "select * from sp_bank_payments_posting('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+amount+", '"+rplf_no+"', '"+jv_no+"', '"+batch_no+"', '"+emp_code+"', '"+date+"')";
				FncSystem.out("sp_bank_payments_posting", SQL);
				pgSelect db = new pgSelect();
				db.select(SQL);
				
				rplf_no = (String) db.getResult()[0][0];
				jv_no = (String) db.getResult()[0][1];
				batch_no = (String) db.getResult()[0][2];
			}
		}
		//insert extra jv detail for credit using total amount
		String SQL = "select sp_bank_payments_posting_credit('"+company_id+"','"+bank_id+"','"+total_amount+"','"+rplf_no+"','"+jv_no+"','"+batch_no+"','"+emp_code+"','"+date+"')";
		FncSystem.out("sp_bank_payments_posting_credit", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		String SQLJV_Remarks = "SELECT sp_update_bank_jv_remarks('"+company_id+"', '"+jv_no+"')";
		db.select(SQLJV_Remarks);
		
		FncGlobal.stopProgress();
		JOptionPane.showMessageDialog(null, "Successfully tagged " + count + " clients"
				+"\nRPLF NO: "+rplf_no
				+"\nJV NO: "+jv_no, "Tagging Successful", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void computeTotal() {
		total_amount = 0.0;
		total_bank_balance = 0.0;
		int count = selected_count();
		for (int i = 0; i < modelBankPayments.getRowCount(); i++) {
			boolean tag = (boolean) modelBankPayments.getValueAt(i, 0);
			if(tag) {
				Object amount = modelBankPayments.getValueAt(i, 5);
				total_amount += Double.parseDouble(amount.toString());
				Object bank_balance = modelBankPayments.getValueAt(i, 10);
				total_bank_balance += Double.parseDouble(bank_balance.toString());
			}
		}
		//System.out.println("Total amount: "+total_amount);
		FncTables.clearTable(modelBankPaymentsTotal);
		modelBankPaymentsTotal.addRow(new Object[] { count, "Total", null, null, null, total_amount, null, null, null, null, total_bank_balance });
	}
	
	private void processFunction() {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Gathering.. Please wait...");
				String date = ma_date.getDateString();
				System.out.println("MA Date: "+date);
				System.out.println("Company ID: "+company_id);
				System.out.println("Project ID: "+project_id);
				System.out.println("Bank ID: "+bank_id);

				modelBankPayments.clear();
				DefaultListModel listModel = new DefaultListModel();
				rowheaderBankPayments.setModel(listModel);
				String SQL="select * from view_bank_payments('"+date+"','"+company_id+"','"+project_id+"','"+bank_id+"')";
				FncSystem.out("Display Bank Payments For Tagging", SQL);
				pgSelect db = new pgSelect();
				db.select(SQL);
				if(db.isNotNull()) {
					for(int x=0; x < db.getRowCount(); x++){
						try {
							modelBankPayments.addRow(db.getResult()[x]);

							listModel.addElement(modelBankPayments.getRowCount());	
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
				int count = modelBankPayments.getRowCount();
				System.out.println("modelBankPayments row/s: "+count);
				if(modelBankPayments.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, count+" Results found", "For tagging", JOptionPane.PLAIN_MESSAGE);
				}
				FncGlobal.stopProgress();
			}
		};
		FncGlobal.loadThread("Gathering clients ...", runPreview);
	}
	
	private void screenFunction() {
		System.out.println("Screen");
		
		String date = ma_date.getDateString();
		System.out.println("MA Date: "+date);
		System.out.println("Company ID: "+company_id);
		System.out.println("Project ID: "+project_id);
		System.out.println("Bank ID: "+bank_id);
		String selected_entity_id = "";
		String selected_proj_id = "";
		String selected_pbl_id = "";
		String selected_seq_no = "";

		for (int i = 0; i < modelBankPayments.getRowCount(); i++) {
			boolean tag = (boolean) modelBankPayments.getValueAt(i, 0);
			if(tag) {
				String entity_id = (String) modelBankPayments.getValueAt(i, 6);
				String proj_id = (String) modelBankPayments.getValueAt(i, 7);
				String pbl_id = (String) modelBankPayments.getValueAt(i, 8);
				Object seq_no = modelBankPayments.getValueAt(i, 9);
				
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
		String company_logo = sql_getCompanyLogo(company_id);
		System.out.println("selected_pbl_id: "+selected_pbl_id);
		mapParameters.put("p_logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("p_ma_date", date);
		mapParameters.put("p_co_id", company_id);
		mapParameters.put("p_proj_id", project_id);
		mapParameters.put("p_bank_id", bank_id);
		mapParameters.put("p_selected_client_entity_id", selected_entity_id);
		mapParameters.put("p_selected_client_proj_id", selected_proj_id);
		mapParameters.put("p_selected_client_pbl_id", selected_pbl_id);
		mapParameters.put("p_selected_client_seq_no", selected_seq_no);
		mapParameters.put("p_rplf_no", local_rplf_no);
		mapParameters.put("p_prepared_by", UserInfo.Alias);
		
		
		if (modelBankPayments.getRowCount() == 0) { // pag walang laman table
			
			String SQL="select * from view_bank_payments_report("
					+ "'"+date+"',"
					+ "'"+company_id+"',"
					+ "'"+project_id+"',"
					+ "'"+bank_id+"',"
					+ "'"+selected_entity_id+"',"
					+ "'"+selected_proj_id+"',"
					+ "'"+selected_pbl_id+"',"
					+ "'"+selected_pbl_id+"',"
				    + "'"+local_rplf_no+"')";
			
			FncSystem.out("Bank Payments Report", SQL);
			FncReport.generateReport("/Reports/rpt_bank_payments.jasper", "Bank Payments Due For "+date, "", mapParameters);
		} else {
			System.out.println("selected_count(): "+selected_count());
			if(selected_count() == 0) { // may laman table pero walang naka tag
				System.out.println("wala");
				System.out.println("local_rplf_no "+local_rplf_no);
				if (local_rplf_no == null) { // walang client at walang rplf
					int no_client = JOptionPane.showConfirmDialog(null,"You have 0 selected client \n"
							+ "view all processed clients ?");
					if (no_client == 0) {
						
						String SQL="select * from view_bank_payments_report("
								+ "'"+date+"',"
								+ "'"+company_id+"',"
								+ "'"+project_id+"',"
								+ "'"+bank_id+"',"
								+ "'"+selected_entity_id+"',"
								+ "'"+selected_proj_id+"',"
								+ "'"+selected_pbl_id+"',"
								+ "'"+selected_pbl_id+"',"
							    + "'"+local_rplf_no+"')";
						
						FncSystem.out("Bank Payments Report", SQL);
						FncReport.generateReport("/Reports/rpt_bank_payments.jasper", "Bank Payments Due For "+date, "", mapParameters);
					}
				} else { // walang client at merong rplf
					int with_rplf = JOptionPane.showConfirmDialog(null,"You have 0 selected client \n"
							+ "but selected a RPLF NO \n"
							+ "view RPLF instead ?");
					if (with_rplf == 0) {
						
						String SQL="select * from view_bank_payments_report("
								+ "'"+date+"',"
								+ "'"+company_id+"',"
								+ "'"+project_id+"',"
								+ "'"+bank_id+"',"
								+ "'"+selected_entity_id+"',"
								+ "'"+selected_proj_id+"',"
								+ "'"+selected_pbl_id+"',"
								+ "'"+selected_pbl_id+"',"
							    + "'"+local_rplf_no+"')";
						
						FncSystem.out("Bank Payments Report", SQL);
						FncReport.generateReport("/Reports/rpt_bank_payments.jasper", "Bank Payments Due For "+date, "", mapParameters);
					}
				}
			} else {
				mapParameters.put("p_rplf_no", "null");
				
				String SQL="select * from view_bank_payments_report("
						+ "'"+date+"',"
						+ "'"+company_id+"',"
						+ "'"+project_id+"',"
						+ "'"+bank_id+"',"
						+ "'"+selected_entity_id+"',"
						+ "'"+selected_proj_id+"',"
						+ "'"+selected_pbl_id+"',"
						+ "'"+selected_pbl_id+"',"
					    + "'"+local_rplf_no+"')";
				
				FncSystem.out("Bank Payments Report", SQL);
				FncReport.generateReport("/Reports/rpt_bank_payments.jasper", "Bank Payments Due For "+date, "", mapParameters);
			}
		}
	}
	
	private int selected_count() {
		int count = 0;
		for (int i = 0; i < modelBankPayments.getRowCount(); i++) {
			boolean tag = (boolean) modelBankPayments.getValueAt(i, 0);
			if(tag) {
				count += 1;
			}
		}
		return count;
	}

	private String sql_company() {
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\" FROM mf_company order by co_id ";
		return SQL;
	}

	private String sql_project(String co_id) {
		String SQL = "SELECT '000' as \"ID\", 'ALL' as \"Project Name\", 'ALL' as \"Alias\"\n"
				+ "UNION\n"
				+ "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\"\n"
				+ "FROM mf_project\n"
				+ "WHERE co_id = '"+co_id+"'\n"
				+ "AND status_id = 'A'\n"
				+ "order by \"ID\"";
		return SQL;
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

	private String issued_rplf_no(String co_id) {
		System.out.println("co_id: "+company_id);
		String SQL = "SELECT TRIM(a.rplf_no)::VARCHAR as \"Request No.\",\n"
				+ "TRIM(get_client_name(entity_id1)) as \"Payee\",\n"
				+ "get_client_name(b.entity_id) as \"Created By\",\n"
				+ "a.date_created as \"Date Created\"\n"
				+ "from rf_request_header a\n"
				+ "left join em_employee b on a.created_by = b.emp_code\n"
				+ "where exists(select * from issued_bank_payments where rplf_no = a.rplf_no and co_id = a.co_id and status_id = 'A')\n"
				+ "and a.co_id = '"+co_id+"'"
				+ "order by a.date_created desc";
		FncSystem.out("ISSUED RPLF SQL", SQL);
		return SQL;
	}

	private String issued_jv_no(String co_id) {// used
		System.out.println("co_id: "+co_id);
		String SQL ="select \n"
				+ "trim(a.jv_no) as \"JV No\",\n"
				+ "a.jv_date as \"JV Date\",\n"
				+ "(select sum(tran_amt) from rf_jv_detail where jv_no = a.jv_no and co_id = a.co_id and status_id = 'A') as \"Amount\",\n"
				+ "a.fiscal_yr as \"Fiscal Year\", \n"
				+ "a.period_id as \"Period\", \n"
				+ "get_client_name(b.entity_id) as \"Created By\", \n"
				+ "a.date_created as \"Date Created\"\n"
				+ "from rf_jv_header a\n"
				+ "left join em_employee b on a.created_by = b.emp_code\n"
				+ "where exists(select * from issued_bank_payments where jv_no = a.jv_no and co_id = a.co_id and status_id = 'A')\n"
				+ "and a.co_id = '"+co_id+"'"
				+ "order by a.date_created desc";
		FncSystem.out("ISSUED JV SQL", SQL);
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
}
