package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_deposit_assignment;

public class DirectDepositAssignment extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = 3679602908914352918L;
	static String title = "Direct Deposit Assignment";
	Dimension frameSize = new Dimension(563, 605);

	private static _JLookup txtClientID;

	private _JDateChooser dteDepositDate;

	private _JXFormattedTextField txtAmount; 
	private _JXFormattedTextField txtRemaining;

	private JTextField txtBank; 
	private JTextField txtAccountNo;          
	private JTextField txtProjID ; 
	private JTextField txtUnitID; 
	private JTextField txtName; 
	private JTextField txtProject; 
	private JTextField txtUnit; 
	private JTextField txtSeq;
	private JTextField txtParticulars; 
	private JTextField txtSequence; 

	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private Font font20 = FncLookAndFeel.systemFont_Plain.deriveFont(20f);

	private JScrollPane scrDeposit;
	private _JTableMain tblDeposit;
	private model_deposit_assignment modelDeposit;
	private JList rowDeposit;

	private JPanel panAdd; 
	private _JLookup txtPartID;  
	private JFormattedTextField txtPayAmount; 
	private JComboBox cboDueType; 

	private JButton btnAssign; 

	private static Integer intRecID; 

	public DirectDepositAssignment() {
		super(title, false, true, false, false);
		initGUI(); 
	}

	public DirectDepositAssignment(String title) {
		super(title);
		initGUI(); 
	}

	public DirectDepositAssignment(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel pnlPageStart = new JXPanel(new BorderLayout(5, 5));
				panMain.add(pnlPageStart, BorderLayout.PAGE_START);
				pnlPageStart.setPreferredSize(new Dimension(0, 223));
				{
					{
						JXPanel panClient = new JXPanel(new BorderLayout(5, 5));
						pnlPageStart.add(panClient, BorderLayout.PAGE_START);
						panClient.setPreferredSize(new Dimension(0, 125));
						panClient.setBorder(JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel pnlPSLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
							panClient.add(pnlPSLabel, BorderLayout.LINE_START);
							pnlPSLabel.setPreferredSize(new Dimension(200, 0));
							{
								JLabel lblName = new JLabel("Name");
								pnlPSLabel.add(lblName);
								lblName.setHorizontalAlignment(JTextField.LEADING);
								lblName.setFont(font11); 
							}
							{
								txtClientID = new _JLookup("");
								pnlPSLabel.add(txtClientID);
								txtClientID.setHorizontalAlignment(JTextField.CENTER);
								txtClientID.setReturnColumn(1);
								txtClientID.setLookupSQL("select a.entity_id, b.entity_name as name, c.projcode as proj_id, d.proj_name, a.pbl_id, e.description as unit, c.seq_no, \n" + 
										"h.bank_alias, a.bank_account, a.deposit_date, a.amount, a.record_id \n" +
										"from tf_unidentified_dep a \n" +
										"inner join rf_entity b on a.entity_id = b.entity_id \n" +
										"inner join rf_sold_unit c on a.entity_id = c.entity_id and a.pbl_id = c.pbl_id and c.status_id != 'I' \n" +
										"inner join mf_project d on c.projcode = d.proj_id \n" +
										"inner join mf_unit_info e on c.projcode = e.proj_id and c.pbl_id = e.pbl_id \n" +
										"inner join mf_bank_account f on a.bank_acct_id = f.bank_acct_id \n" +
										"inner join mf_bank_branch g on f.bank_branch_id = g.bank_branch_id \n" +
										"inner join mf_bank h on g.bank_id = h.bank_id \n" +
										"where a.status_id = 'P' \n" +
										"and (not exists \n" +
										"( \n" +
										"        select * \n" +
										"        from rf_payments x \n" + 
										"        where a.entity_id = x.entity_id and a.pbl_id = x.pbl_id and a.deposit_date::date = x.actual_date::date \n" +
										"        and x.status_id = 'A' and x.amount = a.amount \n" +
										") or a.record_id::int = '1357'::int) \n" +
										"order by b.entity_name, a.deposit_date desc; ");
								txtClientID.setFilterName(true);
								txtClientID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet(); 

										if (data!=null) {
											txtClientID.setValue(data[0].toString());
											txtName.setText(data[1].toString());
											txtProjID.setText(data[2].toString());
											txtProject.setText(data[3].toString());
											txtUnitID.setText(data[4].toString());
											txtUnit.setText(data[5].toString());
											txtSeq.setText(data[6].toString());
											txtBank.setText(data[7].toString());
											txtAccountNo.setText(data[8].toString());
											dteDepositDate.setDate((Date) data[9]);
											txtAmount.setValue((BigDecimal) data[10]);

											String strUnitID = FncGlobal.GetString("select unit_id from mf_unit_info where proj_id = '"+txtProjID.getText()+"' and pbl_id = '"+txtUnitID.getText()+"'; "); 
											String strCoID = FncGlobal.GetString("select co_id from mf_project where proj_id = '"+txtProjID.getText()+"'; ");

											intRecID = (Integer) data[11]; 

											pgSelect dbExec = new pgSelect(); 
											dbExec.select("select sp_create_pay_header('"+UserInfo.branch_id+"', '"+txtClientID.getValue()+"', '"+txtProjID.getText()+"', '"+strUnitID+"', "+txtSeq.getText()+"::int, "+txtAmount.getValue()+"::numeric(19, 2), '"+UserInfo.EmployeeCode+"', "+intRecID+")");

											txtSequence.setText(dbExec.getResult()[0][0].toString());

											System.out.println("");
											System.out.println("select sp_create_pay_header('"+UserInfo.branch_id+"', '"+txtClientID.getValue()+"', '"+txtProjID.getText()+"', '"+strUnitID+"', "+txtSeq.getText()+"::int, "+txtAmount.getValue()+"::numeric(19, 2), '"+UserInfo.EmployeeCode+"', "+intRecID+")");

											loadAssignments(intRecID, data[0].toString(), strCoID);
										}
									}
								});
								txtClientID.setFont(font11); 
							}
							{         
								JLabel lblProject = new JLabel("Project");
								pnlPSLabel.add(lblProject);
								lblProject.setHorizontalAlignment(JTextField.LEADING);
								lblProject.setFont(font11); 
							}
							{
								txtProjID = new JTextField("");
								pnlPSLabel.add(txtProjID);
								txtProjID.setHorizontalAlignment(JTextField.CENTER);
								txtProjID.setEditable(false);
								txtProjID.setFont(font11); 
							}
							{         
								JLabel lblUnit = new JLabel("Unit");
								pnlPSLabel.add(lblUnit);
								lblUnit.setHorizontalAlignment(JTextField.LEADING);
								lblUnit.setFont(font11); 
							}
							{         
								txtUnitID = new JTextField("");
								pnlPSLabel.add(txtUnitID);
								txtUnitID.setHorizontalAlignment(JTextField.CENTER);
								txtUnitID.setEditable(false);
								txtUnitID.setFont(font11); 
							}
						}
						{
							JXPanel pnlBox = new JXPanel(new GridLayout(3, 1, 5, 5));
							panClient.add(pnlBox, BorderLayout.CENTER);
							{
								txtName = new JTextField("");
								pnlBox.add(txtName);
								txtName.setHorizontalAlignment(JTextField.CENTER);
								txtName.setEditable(false);
								txtName.setFont(font11); 
							}
							{
								txtProject = new JTextField("");
								pnlBox.add(txtProject);
								txtProject.setHorizontalAlignment(JTextField.CENTER);
								txtProject.setEditable(false);
								txtProject.setFont(font11); 
							}
							{
								JXPanel pnlUnitSeq = new JXPanel(new BorderLayout(5, 5));
								pnlBox.add(pnlUnitSeq, BorderLayout.CENTER);
								{
									JXPanel pnlUnitDesc = new JXPanel(new BorderLayout(5, 5));
									pnlUnitSeq.add(pnlUnitDesc, BorderLayout.CENTER);
									{
										{
											txtUnit = new JTextField("");
											pnlUnitDesc.add(txtUnit);
											txtUnit.setHorizontalAlignment(JTextField.CENTER);
											txtUnit.setHorizontalAlignment(JTextField.CENTER);
											txtUnit.setEditable(false);
											txtUnit.setFont(font11);
										}
										{
											JXPanel pnlSeq = new JXPanel(new BorderLayout(5, 5));
											pnlUnitSeq.add(pnlSeq, BorderLayout.LINE_END);
											pnlSeq.setPreferredSize(new Dimension(125, 0));
											{
												{
													JLabel lblSeq = new JLabel("Sequence");
													pnlSeq.add(lblSeq, BorderLayout.CENTER);
													lblSeq.setHorizontalAlignment(JTextField.CENTER);
													lblSeq.setFont(font11);
												}
												{
													txtSeq = new JTextField("");
													pnlSeq.add(txtSeq, BorderLayout.LINE_END);
													txtSeq.setPreferredSize(new Dimension(50, 0));
													txtSeq.setHorizontalAlignment(JTextField.CENTER);
													txtSeq.setEditable(false);
													txtSeq.setFont(font11);
												}        
											}
										}
									}
								}
							}
						}         
					}
					{
						JXPanel panDetails = new JXPanel(new GridLayout(1, 2, 5, 5));
						pnlPageStart.add(panDetails, BorderLayout.CENTER);
						panDetails.setBorder(JTBorderFactory.createTitleBorder("Deposit Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JXPanel panDiv1 = new JXPanel(new GridLayout(2, 1, 5, 5)); 
								panDetails.add(panDiv1); 
								{
									{
										JPanel panBank = new JPanel(new BorderLayout(5, 5)); 
										panDiv1.add(panBank); 
										{
											{
												JLabel lblBank = new JLabel("Bank"); 
												panBank.add(lblBank, BorderLayout.LINE_START); 
												lblBank.setPreferredSize(new Dimension(100, 0));
												lblBank.setFont(font11); 
											}
											{
												txtBank = new JTextField(""); 
												panBank.add(txtBank, BorderLayout.CENTER); 
												txtBank.setEditable(false);
												txtBank.setHorizontalAlignment(JTextField.CENTER);
												txtBank.setFont(font11); 
											}
										}
									}
									{
										JPanel panAccount = new JPanel(new BorderLayout(5, 5)); 
										panDiv1.add(panAccount); 
										{
											{
												JLabel lblAccount = new JLabel("Account #"); 
												panAccount.add(lblAccount, BorderLayout.LINE_START); 
												lblAccount.setPreferredSize(new Dimension(100, 0));
												lblAccount.setFont(font11); 
											}
											{
												txtAccountNo = new JTextField(""); 
												panAccount.add(txtAccountNo, BorderLayout.CENTER); 
												txtAccountNo.setEditable(false);
												txtAccountNo.setHorizontalAlignment(JTextField.CENTER);
												txtAccountNo.setFont(font11); 
											}
										}
									}
								}
							}
							{
								JXPanel panDiv2 = new JXPanel(new GridLayout(2, 1, 5, 5)); 
								panDetails.add(panDiv2); 
								{
									{
										JPanel panDepositDate = new JPanel(new BorderLayout(5, 5)); 
										panDiv2.add(panDepositDate); 
										{
											{
												JLabel lblDepositDate = new JLabel("Deposit Date"); 
												panDepositDate.add(lblDepositDate, BorderLayout.LINE_START); 
												lblDepositDate.setHorizontalAlignment(JLabel.CENTER);
												lblDepositDate.setPreferredSize(new Dimension(100, 0));
												lblDepositDate.setFont(font11); 
											}
											{
												dteDepositDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
												panDepositDate.add(dteDepositDate, BorderLayout.CENTER);
												dteDepositDate.getCalendarButton().setVisible(false);
												dteDepositDate.setDate(null);
												dteDepositDate.setFont(font11); 
											}
										}
									}
									{
										JPanel panAmount = new JPanel(new BorderLayout(5, 5)); 
										panDiv2.add(panAmount); 
										{
											{
												JLabel lblAmount = new JLabel("Amount"); 
												panAmount.add(lblAmount, BorderLayout.LINE_START);
												lblAmount.setHorizontalAlignment(JLabel.CENTER);
												lblAmount.setPreferredSize(new Dimension(100, 0));
												lblAmount.setFont(font11);
											}
											{
												txtAmount = new _JXFormattedTextField("0.00");
												panAmount.add(txtAmount, BorderLayout.CENTER);
												txtAmount.setHorizontalAlignment(JTextField.TRAILING);
												txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
												txtAmount.setEditable(false);
												txtAmount.setFont(font11); 
											}
										}
									}
								}
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					scrDeposit = new JScrollPane();
					panCenter.add(scrDeposit, BorderLayout.CENTER);
					scrDeposit.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					{
						modelDeposit = new model_deposit_assignment();
						modelDeposit.setEditable(false);

						tblDeposit = new _JTableMain(modelDeposit);

						rowDeposit = tblDeposit.getRowHeader();
						scrDeposit.setViewportView(tblDeposit);

						tblDeposit.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblDeposit.getColumnModel().getColumn(1).setPreferredWidth(188);
						tblDeposit.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblDeposit.getColumnModel().getColumn(3).setPreferredWidth(100);

						rowDeposit = tblDeposit.getRowHeader();
						rowDeposit.setModel(new DefaultListModel());
						scrDeposit.setRowHeaderView(rowDeposit);
						scrDeposit.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JXPanel panEnd = new JXPanel(new GridLayout(1, 3, 5, 5)); 
				panMain.add(panEnd,BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 60));
				{
					{
						btnAssign = new JButton("Assign"); 
						panEnd.add(btnAssign, BorderLayout.LINE_START); 
						btnAssign.setPreferredSize(new Dimension(200, 0));
						btnAssign.setFocusable(false);
						btnAssign.setFont(font11);
						btnAssign.setEnabled(false);
						btnAssign.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								createPanel(); 

								int intOption = 0; 
								intOption = JOptionPane.showOptionDialog(FncGlobal.homeMDI, panAdd, "Sequence", 
										JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
										new Object[] {"  Add  ", " Cancel"}, JOptionPane.CANCEL_OPTION);

								if (intOption==JOptionPane.YES_OPTION) {
									pgSelect dbExec = new pgSelect(); 
									try {
										dbExec.select("select sp_create_pay_detail( \n" + 
												"'"+txtSequence.getText()+"', \n" + 
												"'"+txtClientID.getValue()+"', \n" + 
												"'"+txtPartID.getValue()+"', \n" + 
												"'"+txtAccountNo.getText()+"', \n" + 
												"'"+txtAmount.getValue()+"'::numeric(19, 2), \n" + 
												"'"+cboDueType.getSelectedItem().toString().substring(0, 1)+"', \n" + 
												""+intRecID+", \n " +
												"'"+UserInfo.EmployeeCode+"'); ");
									} catch (IndexOutOfBoundsException e) {
										dbExec.select("select sp_create_pay_detail( \n" + 
												"'"+txtSequence.getText()+"', \n" + 
												"'"+txtClientID.getValue()+"', \n" + 
												"'"+txtPartID.getValue()+"', \n" + 
												"'"+txtAccountNo.getText()+"', \n" + 
												"'"+txtAmount.getValue()+"'::numeric(19, 2), \n" + 
												"'', \n" +
												""+intRecID+", \n " +
												"'"+UserInfo.EmployeeCode+"'); ");
									}

									loadAssignments();	

									JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Saved!");

									/*
									if ((new BigDecimal(txtAmount.getText())).compareTo(new BigDecimal(txtRemaining.getText()))!=1) {
										pgSelect dbExec = new pgSelect(); 
										dbExec.select("select sp_create_pay_detail( \n" + 
												"'"+txtSequence.getText()+"', \n" + 
												"'"+txtClientID.getValue()+"', \n" + 
												"'"+txtPartID.getValue()+"', \n" + 
												"'"+txtAccountNo.getText()+"', \n" + 
												"'"+txtAmount.getValue()+"'::numeric(19, 2), \n" + 
												"'"+cboDueType.getSelectedItem()+"', \n" + 
												"'"+UserInfo.EmployeeCode+"'); ");
										loadAssignments();	
									} else {
										JOptionPane.showMessageDialog(FncGlobal.homeMDI, "The amount set is greater than the remaining amount.");
									}
									 */
								}
							}
						});
					}
					{
						JXPanel panRemaining = new JXPanel(new BorderLayout(5, 5)); 
						panEnd.add(panRemaining, BorderLayout.LINE_END);
						panRemaining.setPreferredSize(new Dimension(200, 0));
						panRemaining.setBorder(JTBorderFactory.createTitleBorder("Remaining Unassigned", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtRemaining = new _JXFormattedTextField("0.00");
							panRemaining.add(txtRemaining, BorderLayout.CENTER);
							txtRemaining.setHorizontalAlignment(JTextField.TRAILING);
							txtRemaining.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtRemaining.setEditable(false);
							txtRemaining.setFont(font20); 
						}
					}
					{
						JXPanel panSequence = new JXPanel(new BorderLayout(5, 5)); 
						panEnd.add(panSequence, BorderLayout.LINE_END);
						panSequence.setPreferredSize(new Dimension(200, 0));
						panSequence.setBorder(JTBorderFactory.createTitleBorder("Sequence Number", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtSequence = new JTextField("");
							panSequence.add(txtSequence, BorderLayout.CENTER);
							txtSequence.setHorizontalAlignment(JTextField.CENTER);
							txtSequence.setEditable(false);
							txtSequence.setFont(font11); 
						}
					}
				}
			}
		}
	}

	private void createPanel() {
		panAdd = new JPanel(new BorderLayout(5, 5)); 
		panAdd.setPreferredSize(new Dimension(400, 100));
		{
			{
				JPanel panLabel = new JPanel(new GridLayout(3, 2, 5, 5)); 
				panAdd.add(panLabel, BorderLayout.LINE_START); 
				panLabel.setPreferredSize(new Dimension(150, 0));
				{
					{
						panLabel.add(new JLabel("Particular")); 
					}
					{
						txtPartID = new _JLookup("");
						panLabel.add(txtPartID);
						txtPartID.setHorizontalAlignment(JTextField.CENTER);
						txtPartID.setReturnColumn(0);
						txtPartID.setLookupSQL("SELECT TRIM(c_part_id)::VARCHAR as \"ID\", TRIM(c_part_desc) as \"Name\", TRIM(c_part_alias) as \"Alias\", c_apply_ledger as \"Apply Ledger\", c_or_receipt as \"OR Receipt\", TRIM(c_receipt_id)::VARCHAR as \"Receipt ID\", \r\n" + 
								"TRIM(c_receipt_type) as \"Receipt Type\", TRIM(c_receipt_alias)::VARCHAR as \"Receipt Alias\" \r\n" + 
								"FROM view_particulars('2794266171', '015', '3731', 2); ");
						txtPartID.setFilterName(true);
						txtPartID.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = txtPartID.getDataSet(); 
								txtParticulars.setText((String) data[1]);

								if (!txtPartID.getValue().equals("033")) {
									cboDueType.setSelectedIndex(3);
									cboDueType.setEnabled(false);
								} else {
									cboDueType.setEnabled(true);
								}
							}
						});
						txtPartID.setValue("033");
					}
					{
						panLabel.add(new JLabel("Amount")); 
					}
					{
						panLabel.add(new JLabel("")); 
					}
					{
						panLabel.add(new JLabel("Due Type")); 
					}
					{
						panLabel.add(new JLabel("")); 
					}

				}
			}
			{
				JPanel panText = new JPanel(new GridLayout(3, 1, 5, 5)); 
				panAdd.add(panText, BorderLayout.CENTER); 
				panText.setPreferredSize(new Dimension(150, 0));
				{
					{
						txtParticulars = new JTextField("DOWN PAYMENT"); 
						panText.add(txtParticulars); 
						txtParticulars.setFont(font11);
						txtParticulars.setEditable(false);
						txtParticulars.setFocusable(false);
						txtParticulars.setHorizontalAlignment(JTextField.CENTER);
					}
					{
						txtPayAmount = new _JXFormattedTextField("0.00");
						panText.add(txtPayAmount, BorderLayout.CENTER);
						txtPayAmount.setHorizontalAlignment(JTextField.TRAILING);
						txtPayAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtPayAmount.setEditable(true);
						txtPayAmount.setFont(font11); 
						txtPayAmount.setValue((BigDecimal) txtRemaining.getValue());
					}
					{
						cboDueType = new JComboBox(new String[] {
								"R - REGULAR", "W - MIXED", "M - MORATORIUM", ""
						}); 
						panText.add(cboDueType); 
						cboDueType.setFont(font11);
					}
				}
			}
		}
	}

	private void loadAssignments() {
		BigDecimal dbAssigned = new BigDecimal(0); 

		FncTables.clearTable(modelDeposit);
		DefaultListModel listModel = new DefaultListModel();
		rowDeposit.setModel(listModel); 

		String SQL = "select a.part_type, b.partdesc, a.amount, (case when a.due_type = 'R' then 'REGULAR' \n" + 
				"when a.due_type = 'W' then 'MIXED' when a.due_type = 'M' then 'MORATORIUM' END) AS due_type, ud_id \n" + 
				"from rf_pay_detail a\r\n" + 
				"inner join mf_pay_particular b on a.part_type = b.pay_part_id\r\n" + 
				"where a.client_seqno = '"+txtSequence.getText()+"'";	

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelDeposit.addRow(db.getResult()[x]);
				listModel.addElement(modelDeposit.getRowCount());
			}
		};

		for (int x=0; x < modelDeposit.getRowCount(); x++) {
			dbAssigned = dbAssigned.add((BigDecimal) modelDeposit.getValueAt(x, 2)); 
		}

		txtRemaining.setValue(((BigDecimal) txtAmount.getValue()).subtract(dbAssigned));

		try {
			btnAssign.setEnabled(!FncGlobal.GetBoolean("select exists(select * from rf_pay_detail where ud_id::int = '"+intRecID+"'::int and status_id != 'I')"));
			
			/*
			if (db.getResult()[0][4].toString().equals("") || db.getResult()[0][4].toString()==null) {
				btnAssign.setEnabled(true);
			} else {
				btnAssign.setEnabled(false);
			}
			*/
		} catch (NullPointerException ex) {
			btnAssign.setEnabled(true);
		}
	}
	

	private void loadAssignments(Integer udid, String client, String coid) {
		BigDecimal dbAssigned = new BigDecimal(0); 

		FncTables.clearTable(modelDeposit);
		DefaultListModel listModel = new DefaultListModel();
		rowDeposit.setModel(listModel); 

		String SQL = "select a.part_type, b.partdesc, a.amount, (case when a.due_type = 'R' then 'REGULAR' \n" + 
				"when a.due_type = 'W' then 'MIXED' when a.due_type = 'M' then 'MORATORIUM' END) AS due_type, ud_id \n" + 
				"from rf_pay_detail a\r\n" + 
				"inner join mf_pay_particular b on a.part_type = b.pay_part_id\r\n" + 
				"where a.client_seqno = '"+txtSequence.getText()+"'";	

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelDeposit.addRow(db.getResult()[x]);
				listModel.addElement(modelDeposit.getRowCount());
			}
		};

		for (int x=0; x < modelDeposit.getRowCount(); x++) {
			dbAssigned = dbAssigned.add((BigDecimal) modelDeposit.getValueAt(x, 2)); 
		}

		txtRemaining.setValue(((BigDecimal) txtAmount.getValue()).subtract(dbAssigned));

		try {
			btnAssign.setEnabled(!FncGlobal.GetBoolean("select exists(select * from rf_pay_detail where entity_id = '"+client+"' and ud_id::int = '"+udid+"'::int and status_id != 'I')"));
			
			/*
			if (db.getResult()[0][4].toString().equals("") || db.getResult()[0][4].toString()==null) {
				btnAssign.setEnabled(true);
			} else {
				btnAssign.setEnabled(false);
			}
			*/
		} catch (NullPointerException ex) {
			btnAssign.setEnabled(true);
		}
	}
}

