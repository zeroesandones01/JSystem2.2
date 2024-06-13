/**
 * 
 */
package Buyers.LoansManagement;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import tablemodel.modelBankREMLOG;
import tablemodel.modelBankREMQualifiedAccounts;
import tablemodel.modelBankREMRelease;
import tablemodel.modelBankREMStatus;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */

public class BankREM extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 7103887123752084600L;

	private static String title = "Bank REM";
	static Dimension SIZE = new Dimension(650, 550);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTabbedPane tabCenter;
	private JPanel pnlQualifiedAccnt;

	private JPanel pnlQANorth;
	private JPanel pnlQANorthLabel;
	private JLabel lblSearchby;
	private JPanel pnlQANorthComponent;
	private JComboBox cmbSearchBy;
	private JTextField txtSearchBy;

	private JPanel pnlQACenter;
	private JScrollPane scrollQualifiedAcct;
	private _JTableMain tblQualifiedAcct;
	private JList rowHeaderQualifiedAcct;
	private modelBankREMQualifiedAccounts modelQualifiedAcct;

	private JPanel pnlQASouth;
	private JButton btnQASave;
	private JButton btnQAReset;
	private JButton btnQAPreview; //XXX CHEKC IF THIS WILL BE USED 

	private JPanel pnlLoanStatus;
	private JPanel pnlLoanStatusNorth;
	private JPanel pnlLSNorthWest;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnit;

	private JPanel pnlLSNorthEast;

	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;

	private JPanel pnlProject;
	private _JXTextField txtProjID;
	private _JXTextField txtProjName;

	private JPanel pnlUnit;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;
	
	private JLabel lblSeq;
	private _JXTextField txtSeqNo;

	private _JTabbedPane tabLoanStatusCenter;

	private JPanel pnlREMStatus;
	private JPanel pnlREMStatusNorth;

	private JPanel pnlREMStatusNorthLabel;
	private JLabel lblREMLoanStatus;
	private JLabel lblREMStatusDate;
	private JLabel lblREMBank;

	private JPanel pnlREMStatusNorthComponent;
	private JPanel pnlREMLoanStatus;
	private _JLookup lookupREMLoanStatus;
	private _JXTextField txtREMLoanStatus;
	private JPanel pnlREMStatusDate;
	private _JDateChooser dateREMStatusDate;
	private JPanel pnlREMBank;
	private _JLookup lookupREMBank;
	private _JXTextField txtREMBankName;

	private JPanel pnlREMStatusCenter;
	private JScrollPane scrollREMStatusCenter;
	private _JTableMain tblREMStatusCenter;
	private JList rowHeaderREMStatusCenter;
	private modelBankREMStatus modelREMStat;

	private JPanel pnlLOGValidity;
	private JPanel pnlLOGValidityNorth;
	private JPanel pnlLOGVNLabel;
	private JLabel lblLOGBank;
	private JLabel lblLOGReleased;
	private JLabel lblLOGAmount;
	private JLabel lblLOGExpiry;

	private JPanel pnlLOGVNComponent;

	private JPanel pnlLOGBank;
	private _JLookup lookupLOGBank;
	private _JXTextField txtLOGBank;

	private JPanel pnlLOGReleased;
	private _JDateChooser dateLOGReleased;
	private JLabel lblLOGREMFees;
	private _JXFormattedTextField txtLOGREMFees;

	private JPanel pnlLOGAmount;
	private _JXFormattedTextField txtLOGAmount;
	private JLabel lblLOGDatePaid;
	private JPanel pnlLOGDatePaid;
	private JCheckBox chkLOGDatePaid;
	private _JDateChooser dateLOGDatePaid;

	private JPanel pnlLOGExpiry;
	private _JDateChooser dateLOGExpiry;
	private JLabel lblLOGMRIFireFees;
	private _JXFormattedTextField txtLOGMRIFireFees;

	private JPanel pnlLOGValidityCenter;
	private JScrollPane scrollLOGValidityCenter;
	private _JTableMain tblLOGValidityCenter;
	private JList rowHeaderLOGValidityCenter;
	private modelBankREMLOG modelREMLOG;

	private JPanel pnlReleaseUndertaking;
	private JPanel pnlRUNorth;
	private JPanel pnlRUNorthLabel;
	private JLabel lblRULoanReleaseDate;
	private JLabel lblRUUndertakingExpDate;
	private JLabel lblRUDOASTransmitted;

	private JPanel pnlRUNorthComponent;
	private JPanel pnlRUReleasedDate;
	private JPanel pnlRUReleasedDateWest;
	private JCheckBox chkRUReleaseDate;
	private _JDateChooser dateRUReleasedDate;
	private JPanel pnlRUUndertakingExpDate;
	private JPanel pnlRUUndertakingExpDateWest;
	private JCheckBox chkRUUndertaking;
	private _JDateChooser dateRUUndertakingExp;

	private JPanel pnlRUDOASTransmitted;
	private JPanel pnlRUDOASTransmittedWest;
	private JCheckBox chkRUDOASTransmitted;
	private _JDateChooser dateRUDOASTransmitted;
	private JLabel lblRUTransferCostPaid;
	private _JXFormattedTextField txtRUTransferCostPaid;

	private JPanel pnlRUCenter;
	private JScrollPane scrollRUCenter;
	private _JTableMain tblRUCenter;
	private JList rowHeaderRUCenter;
	private modelBankREMRelease modelREMRelease;

	private JPanel pnlLoanStatusSouth;
	private JButton btnLSNew;
	private JButton btnLSEdit;
	private JButton btnLSSave;
	private JButton btnLSReset;

	public BankREM() {
		super(title, true, true, true, true);
		initGUI();
	}

	public BankREM(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public BankREM(String title, boolean resizable, boolean closable,
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

		{
			tabCenter = new _JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);
			//tabCenter.setBorder(JTBorderFactory.createTitleBorder(""));
			{
				pnlQualifiedAccnt = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("Qualified Accounts", pnlQualifiedAccnt);
				pnlQualifiedAccnt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlQANorth = new JPanel(new BorderLayout(3, 3));
					pnlQualifiedAccnt.add(pnlQANorth, BorderLayout.NORTH);
					{
						pnlQANorthLabel = new JPanel(new BorderLayout(3, 3));
						pnlQANorth.add(pnlQANorthLabel, BorderLayout.WEST);
						{
							lblSearchby = new JLabel("Search by:");
							pnlQANorthLabel.add(lblSearchby);
						}
					}
					{
						pnlQANorthComponent = new JPanel(new BorderLayout(3, 3));
						pnlQANorth.add(pnlQANorthComponent, BorderLayout.CENTER);
						{
							cmbSearchBy = new JComboBox(new String [] {"Client Name", "Ph-Blk-Lt", "Project"});
							pnlQANorthComponent.add(cmbSearchBy, BorderLayout.WEST);
							cmbSearchBy.setPreferredSize(new Dimension(150, 0));
							cmbSearchBy.setSelectedItem(null);
							cmbSearchBy.addItemListener(new ItemListener() {

								@Override
								public void itemStateChanged(ItemEvent arg0) {

									_BankREM.displayQualifiedAccounts(cmbSearchBy.getSelectedIndex(), txtSearchBy.getText(), modelQualifiedAcct, rowHeaderQualifiedAcct);
									tblQualifiedAcct.packAll();
									scrollQualifiedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedAcct.getRowCount())));
								}
							});
						}
						{
							txtSearchBy = new JTextField();
							pnlQANorthComponent.add(txtSearchBy, BorderLayout.CENTER);
							txtSearchBy.addKeyListener(new KeyAdapter() {

								public void keyReleased(KeyEvent e){
									_BankREM.displayQualifiedAccounts(cmbSearchBy.getSelectedIndex(), txtSearchBy.getText(), modelQualifiedAcct, rowHeaderQualifiedAcct);
									tblQualifiedAcct.packAll();
									scrollQualifiedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedAcct.getRowCount())));
								}
							});
						}
					}
				}
				{
					pnlQACenter = new JPanel(new BorderLayout(3, 3));
					pnlQualifiedAccnt.add(pnlQACenter, BorderLayout.CENTER);
					pnlQACenter.setBorder(JTBorderFactory.createTitleBorder("Qualified Accounts"));
					{
						scrollQualifiedAcct = new JScrollPane();
						pnlQACenter.add(scrollQualifiedAcct, BorderLayout.CENTER);
						{
							modelQualifiedAcct = new modelBankREMQualifiedAccounts();
							tblQualifiedAcct = new _JTableMain(modelQualifiedAcct);
							scrollQualifiedAcct.setViewportView(tblQualifiedAcct);
							scrollQualifiedAcct.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblQualifiedAcct.hideColumns("Entity ID", "Proj. ID");
						}
						{
							rowHeaderQualifiedAcct = tblQualifiedAcct.getRowHeader();
							rowHeaderQualifiedAcct.setModel(new DefaultListModel());
							scrollQualifiedAcct.setRowHeaderView(rowHeaderQualifiedAcct);
							scrollQualifiedAcct.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
				{
					pnlQASouth = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlQualifiedAccnt.add(pnlQASouth, BorderLayout.SOUTH);
					{
						btnQASave = new JButton("Save");
						pnlQASouth.add(btnQASave);
						btnQASave.setActionCommand("Qualified Accounts Save");;
						btnQASave.addActionListener(this);
					}
					{
						btnQAReset = new JButton("Reset");
						pnlQASouth.add(btnQAReset);
						btnQAReset.setActionCommand("Qualified Accounts Reset");
						btnQAReset.addActionListener(this);
					}
				}
			}
			{
				pnlLoanStatus = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("Loan Status of Accounts", pnlLoanStatus);
				pnlLoanStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlLoanStatusNorth = new JPanel(new BorderLayout(3, 3));
					pnlLoanStatus.add(pnlLoanStatusNorth, BorderLayout.NORTH);
					pnlLoanStatusNorth.setBorder(JTBorderFactory.createTitleBorder("Client Details"));
					{
						pnlLSNorthWest = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlLoanStatusNorth.add(pnlLSNorthWest, BorderLayout.WEST);
						{
							lblClient = new JLabel("Client");
							pnlLSNorthWest.add(lblClient);
						}
						{
							lblProject = new JLabel("Project");
							pnlLSNorthWest.add(lblProject);
						}
						{
							lblUnit = new JLabel("Unit");
							pnlLSNorthWest.add(lblUnit);
						}
					}
					{
						pnlLSNorthEast = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlLoanStatusNorth.add(pnlLSNorthEast, BorderLayout.CENTER);
						{
							pnlClient = new JPanel(new BorderLayout(3, 3));
							pnlLSNorthEast.add(pnlClient);
							{
								lookupClient = new _JLookup(null, "Select Client", 0);
								pnlClient.add(lookupClient, BorderLayout.WEST);
								lookupClient.setLookupSQL(_BankREM.sqlLoanStatusClients());
								lookupClient.setPreferredSize(new Dimension(100, 0));
								lookupClient.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null){
											String entity_id = (String) data[0];
											String client_name = (String) data[1];
											String proj_id = (String) data[2];
											String proj_name = (String) data[3];
											String unit_id = (String) data[4];
											String unit_desc = (String) data[5];
											Integer seq_no = (Integer) data[6];

											txtClient.setText(client_name);
											txtProjID.setText(proj_id);
											txtProjName.setText(proj_name);
											txtUnitID.setText(unit_id);
											txtUnitDesc.setText(unit_desc);
											txtSeqNo.setText(String.valueOf(seq_no));
											
											clearLoanStatus();

											_BankREM.displayLoanStatus(entity_id, proj_id, unit_id, modelREMStat, rowHeaderREMStatusCenter);
											tblREMStatusCenter.packAll();
											scrollREMStatusCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblREMStatusCenter.getRowCount())));

											_BankREM.displayLOG(entity_id, proj_id, unit_id, modelREMLOG, rowHeaderLOGValidityCenter);
											tblLOGValidityCenter.packAll();
											scrollLOGValidityCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLOGValidityCenter.getRowCount())));

											_BankREM.displayReleasedUndertaking(entity_id, unit_id, modelREMRelease, rowHeaderRUCenter);
											tblRUCenter.packAll();
											scrollRUCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRUCenter.getRowCount())));

											setLoanStatusBtnState(true, false, false, true);
										}
									}
								});
							}
							{
								txtClient = new _JXTextField();
								pnlClient.add(txtClient, BorderLayout.CENTER);
							}
						}
						{
							pnlProject = new JPanel(new BorderLayout(3, 3));
							pnlLSNorthEast.add(pnlProject);
							{
								txtProjID = new _JXTextField();
								pnlProject.add(txtProjID, BorderLayout.WEST);
								txtProjID.setPreferredSize(new Dimension(100, 0));
								txtProjID.setHorizontalAlignment(_JXTextField.CENTER);
							}
							{
								txtProjName = new _JXTextField();
								pnlProject.add(txtProjName, BorderLayout.CENTER);
							}
						}
						{
							pnlUnit = new JPanel(new BorderLayout(3, 3));
							pnlLSNorthEast.add(pnlUnit);
							{
								txtUnitID = new _JXTextField();
								pnlUnit.add(txtUnitID, BorderLayout.WEST);
								txtUnitID.setPreferredSize(new Dimension(100, 0));
								txtUnitID.setHorizontalAlignment(_JXTextField.CENTER);
							}
							
							{
								txtUnitDesc = new _JXTextField();
								pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
							}
							{
								JPanel pnlSeq = new JPanel(new BorderLayout(3, 3));
								pnlUnit.add(pnlSeq, BorderLayout.EAST);
								pnlSeq.setPreferredSize(new Dimension(100, 0));
								{
									lblSeq = new JLabel("Seq.");
									pnlSeq.add(lblSeq, BorderLayout.WEST);
									lblSeq.setPreferredSize(new Dimension(50, 0));
								}
								{
									txtSeqNo = new _JXTextField();
									pnlSeq.add(txtSeqNo, BorderLayout.CENTER);
								}
							}
						}
					}
				}
				{
					tabLoanStatusCenter = new _JTabbedPane();
					pnlLoanStatus.add(tabLoanStatusCenter, BorderLayout.CENTER);
					{
						pnlREMStatus = new JPanel(new BorderLayout(3, 3));
						tabLoanStatusCenter.add("Client REM Status", pnlREMStatus);
						pnlREMStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						{
							pnlREMStatusNorth = new JPanel(new BorderLayout(3, 3));
							pnlREMStatus.add(pnlREMStatusNorth, BorderLayout.NORTH);
							{
								pnlREMStatusNorthLabel = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlREMStatusNorth.add(pnlREMStatusNorthLabel, BorderLayout.WEST);
								{
									lblREMLoanStatus = new JLabel("Loan Status");
									pnlREMStatusNorthLabel.add(lblREMLoanStatus);
								}
								{
									lblREMStatusDate = new JLabel("Status Date");
									pnlREMStatusNorthLabel.add(lblREMStatusDate);
								}
								{
									lblREMBank = new JLabel("Bank");
									pnlREMStatusNorthLabel.add(lblREMBank);
								}
							}
							{
								pnlREMStatusNorthComponent = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlREMStatusNorth.add(pnlREMStatusNorthComponent, BorderLayout.CENTER);
								{
									pnlREMLoanStatus = new JPanel(new BorderLayout(3, 3));
									pnlREMStatusNorthComponent.add(pnlREMLoanStatus);
									{
										lookupREMLoanStatus = new _JLookup(null, "REM Status", 0);
										pnlREMLoanStatus.add(lookupREMLoanStatus, BorderLayout.WEST);
										lookupREMLoanStatus.setLookupSQL(_BankREM.sqlREMLoanStatus());
										lookupREMLoanStatus.setPreferredSize(new Dimension(100, 0));
										lookupREMLoanStatus.addLookupListener(new LookupListener() {

											@Override
											public void lookupPerformed(LookupEvent event) {
												Object [] data = ((_JLookup) event.getSource()).getDataSet();

												if(data != null){
													String loan_status = (String) data[1];
													txtREMLoanStatus.setText(loan_status);
												}
											}
										});
									}
									{
										txtREMLoanStatus = new _JXTextField();
										pnlREMLoanStatus.add(txtREMLoanStatus, BorderLayout.CENTER);
									}
								}
								{
									pnlREMStatusDate = new JPanel(new BorderLayout(3, 3));
									pnlREMStatusNorthComponent.add(pnlREMStatusDate);
									{
										dateREMStatusDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlREMStatusDate.add(dateREMStatusDate, BorderLayout.WEST);
										dateREMStatusDate.setPreferredSize(new Dimension(150, 0));
										dateREMStatusDate.setDate(Calendar.getInstance().getTime());
									}
								}
								{
									pnlREMBank = new JPanel(new BorderLayout(3, 3));
									pnlREMStatusNorthComponent.add(pnlREMBank);
									{
										lookupREMBank = new _JLookup(null, "Select Bank", 0);
										pnlREMBank.add(lookupREMBank, BorderLayout.WEST);
										lookupREMBank.setPreferredSize(new Dimension(100, 0));
									}
									{
										txtREMBankName = new _JXTextField();
										pnlREMBank.add(txtREMBankName, BorderLayout.CENTER);
									}
								}
							}
						}
						{
							pnlREMStatusCenter = new JPanel(new BorderLayout(3, 3));
							pnlREMStatus.add(pnlREMStatusCenter, BorderLayout.CENTER);
							{
								scrollREMStatusCenter = new JScrollPane();
								pnlREMStatusCenter.add(scrollREMStatusCenter, BorderLayout.CENTER);
								{
									modelREMStat = new modelBankREMStatus();
									tblREMStatusCenter = new _JTableMain(modelREMStat);
									scrollREMStatusCenter.setViewportView(tblREMStatusCenter);
									scrollREMStatusCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

									modelREMStat.addTableModelListener(new TableModelListener() {

										public void tableChanged(TableModelEvent e) {
											if(modelREMStat.getRowCount() == 0){
												rowHeaderREMStatusCenter.setModel(new DefaultListModel());
											}
										}
									});

									tblREMStatusCenter.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

										public void valueChanged(ListSelectionEvent arg0) {
											if(!arg0.getValueIsAdjusting()){
												try{
													int row = tblREMStatusCenter.getSelectedRow();

													String status_desc = (String) modelREMStat.getValueAt(row, 0);
													Date status_date = (Date) modelREMStat.getValueAt(row, 1);
													String status_id = (String) modelREMStat.getValueAt(row, 4);

													lookupREMLoanStatus.setValue(status_id);
													txtREMLoanStatus.setText(status_desc);
													dateREMStatusDate.setDate(status_date);

													setLoanStatusBtnState(true, true, false, true);
												}catch (ArrayIndexOutOfBoundsException e){}
											}
										}
									});
								}
								{
									rowHeaderREMStatusCenter = tblREMStatusCenter.getRowHeader();
									rowHeaderREMStatusCenter.setModel(new DefaultListModel());
									scrollREMStatusCenter.setRowHeaderView(rowHeaderREMStatusCenter);
									scrollREMStatusCenter.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
					}
					{
						pnlLOGValidity = new JPanel(new BorderLayout(3, 3));
						tabLoanStatusCenter.add("LOG Validity/Extensions", pnlLOGValidity);
						pnlLOGValidity.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						{
							pnlLOGValidityNorth = new JPanel(new BorderLayout(3, 3));
							pnlLOGValidity.add(pnlLOGValidityNorth, BorderLayout.NORTH);
							{
								pnlLOGVNLabel = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlLOGValidityNorth.add(pnlLOGVNLabel, BorderLayout.WEST);
								{
									lblLOGBank = new JLabel("Bank");
									pnlLOGVNLabel.add(lblLOGBank);
								}
								{
									lblLOGReleased = new JLabel("Released");
									pnlLOGVNLabel.add(lblLOGReleased);
								}
								{
									lblLOGAmount = new JLabel("Amount");
									pnlLOGVNLabel.add(lblLOGAmount);
								}
								{
									lblLOGExpiry = new JLabel("Expiry");
									pnlLOGVNLabel.add(lblLOGExpiry);
								}
							}
							{
								pnlLOGVNComponent = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlLOGValidityNorth.add(pnlLOGVNComponent, BorderLayout.CENTER);
								{
									pnlLOGBank = new JPanel(new BorderLayout(3, 3));
									pnlLOGVNComponent.add(pnlLOGBank);
									{
										lookupLOGBank = new _JLookup(null, "Select Bank", 0);
										pnlLOGBank.add(lookupLOGBank, BorderLayout.WEST);
										lookupLOGBank.setPreferredSize(new Dimension(100, 0));
										lookupLOGBank.setLookupSQL(_BankREM.sqlBank());
										lookupLOGBank.addLookupListener(new LookupListener() {

											public void lookupPerformed(LookupEvent event) {
												Object [] data = ((_JLookup) event.getSource()).getDataSet();

												if(data != null){
													String bank_name = (String) data[1];
													txtLOGBank.setText(bank_name);
												}
											}
										});
									}
									{
										txtLOGBank = new _JXTextField();
										pnlLOGBank.add(txtLOGBank, BorderLayout.CENTER);
									}
								}
								{
									pnlLOGReleased = new JPanel(new BorderLayout(3, 3));
									pnlLOGVNComponent.add(pnlLOGReleased);
									{
										dateLOGReleased = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlLOGReleased.add(dateLOGReleased, BorderLayout.WEST);
										dateLOGReleased.setPreferredSize(new Dimension(150, 0));
									}
									{
										lblLOGREMFees = new JLabel("REM Fees", JLabel.TRAILING);
										pnlLOGReleased.add(lblLOGREMFees);
									}
									{
										txtLOGREMFees = new _JXFormattedTextField(JXTextField.RIGHT);
										pnlLOGReleased.add(txtLOGREMFees, BorderLayout.EAST);
										txtLOGREMFees.setPreferredSize(new Dimension(150, 0));
										txtLOGREMFees.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									}
								}
								{
									pnlLOGAmount = new JPanel(new BorderLayout(3, 3));
									pnlLOGVNComponent.add(pnlLOGAmount);
									{
										txtLOGAmount = new _JXFormattedTextField(JXTextField.RIGHT);
										pnlLOGAmount.add(txtLOGAmount, BorderLayout.WEST);
										txtLOGAmount.setPreferredSize(new Dimension(150, 0));
										txtLOGAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									}
									{
										lblLOGDatePaid = new JLabel("Date Paid", JLabel.TRAILING);
										pnlLOGAmount.add(lblLOGDatePaid);
									}
									{
										pnlLOGDatePaid = new JPanel(new BorderLayout(3, 3));
										pnlLOGAmount.add(pnlLOGDatePaid, BorderLayout.EAST);
										pnlLOGDatePaid.setPreferredSize(new Dimension(150, 0));
										{
											chkLOGDatePaid = new JCheckBox();
											pnlLOGDatePaid.add(chkLOGDatePaid, BorderLayout.WEST);
											chkLOGDatePaid.addItemListener(new ItemListener() {

												public void itemStateChanged(ItemEvent arg0) {
													Boolean isSelected = (arg0.getStateChange() == ItemEvent.SELECTED);

													if(isSelected){
														dateLOGDatePaid.getCalendarButton().setEnabled(true);
														dateLOGDatePaid.setEditable(true);
													}else{
														dateLOGDatePaid.getCalendarButton().setEnabled(false);
														dateLOGDatePaid.setEditable(false);
														System.out.println("Dumaan dito sa not enabled ng components");
													}
												}
											});
										}
										{
											dateLOGDatePaid = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
											pnlLOGDatePaid.add(dateLOGDatePaid, BorderLayout.CENTER);
											dateLOGDatePaid.setPreferredSize(new Dimension(150, 0));
										}
									}
								}
								{
									pnlLOGExpiry = new JPanel(new BorderLayout(3, 3));
									pnlLOGVNComponent.add(pnlLOGExpiry);
									{
										dateLOGExpiry = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlLOGExpiry.add(dateLOGExpiry, BorderLayout.WEST);
										dateLOGExpiry.setPreferredSize(new Dimension(150, 0));
									}
									{
										lblLOGMRIFireFees = new JLabel("MRI/Fire Fees", JLabel.TRAILING);
										pnlLOGExpiry.add(lblLOGMRIFireFees);
									}
									{
										txtLOGMRIFireFees = new _JXFormattedTextField(JXTextField.RIGHT);
										pnlLOGExpiry.add(txtLOGMRIFireFees,BorderLayout.EAST);
										txtLOGMRIFireFees.setPreferredSize(new Dimension(150, 0));
										txtLOGMRIFireFees.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									}
								}
							}
						}
						{
							pnlLOGValidityCenter = new JPanel(new BorderLayout(3, 3));
							pnlLOGValidity.add(pnlLOGValidityCenter, BorderLayout.CENTER);
							{
								scrollLOGValidityCenter = new JScrollPane();
								pnlLOGValidityCenter.add(scrollLOGValidityCenter, BorderLayout.CENTER);
								{
									modelREMLOG = new modelBankREMLOG();
									tblLOGValidityCenter = new _JTableMain(modelREMLOG);
									scrollLOGValidityCenter.setViewportView(tblLOGValidityCenter);
									scrollLOGValidityCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								}
								{
									rowHeaderLOGValidityCenter = tblLOGValidityCenter.getRowHeader();
									rowHeaderLOGValidityCenter.setModel(new DefaultListModel());
									scrollLOGValidityCenter.setRowHeaderView(rowHeaderLOGValidityCenter);
									scrollLOGValidityCenter.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
					}
					{
						pnlReleaseUndertaking = new JPanel(new BorderLayout(3, 3));
						tabLoanStatusCenter.add("Release with Undertaking", pnlReleaseUndertaking);
						pnlReleaseUndertaking.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						{
							pnlRUNorth = new JPanel(new BorderLayout(3, 3));
							pnlReleaseUndertaking.add(pnlRUNorth, BorderLayout.NORTH);
							{
								pnlRUNorthLabel = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlRUNorth.add(pnlRUNorthLabel, BorderLayout.WEST);
								{
									lblRULoanReleaseDate = new JLabel("Loan Release Date");
									pnlRUNorthLabel.add(lblRULoanReleaseDate);
								}
								{
									lblRUUndertakingExpDate = new JLabel("Undertaking Expiry Date");
									pnlRUNorthLabel.add(lblRUUndertakingExpDate);
								}
								{
									lblRUDOASTransmitted = new JLabel("DOAS Transmitted to LLD");
									pnlRUNorthLabel.add(lblRUDOASTransmitted);
								}
							}
							{
								pnlRUNorthComponent = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlRUNorth.add(pnlRUNorthComponent, BorderLayout.CENTER);
								{
									pnlRUReleasedDate = new JPanel(new BorderLayout(3, 3));
									pnlRUNorthComponent.add(pnlRUReleasedDate, BorderLayout.WEST);
									pnlRUReleasedDate.setPreferredSize(new Dimension(150, 20));
									{
										pnlRUReleasedDateWest = new JPanel(new BorderLayout(3, 3));
										pnlRUReleasedDate.add(pnlRUReleasedDateWest, BorderLayout.WEST);
										pnlRUReleasedDateWest.setPreferredSize(new Dimension(150, 0));
										{
											chkRUReleaseDate = new JCheckBox();
											pnlRUReleasedDateWest.add(chkRUReleaseDate, BorderLayout.WEST);
											chkRUReleaseDate.addItemListener(new ItemListener() {

												public void itemStateChanged(ItemEvent e) {
													Boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
													if(isSelected){
														dateRUReleasedDate.getCalendarButton().setEnabled(true);
														dateRUReleasedDate.setEditable(true);
													}else{
														dateRUReleasedDate.getCalendarButton().setEnabled(false);
														dateRUReleasedDate.setEditable(false);
													}
												}
											});
										}
										{
											dateRUReleasedDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
											pnlRUReleasedDateWest.add(dateRUReleasedDate);
											dateRUReleasedDate.setPreferredSize(new Dimension(150, 0));
										}
									}
								}
								{
									pnlRUUndertakingExpDate = new JPanel(new BorderLayout(3, 3));
									pnlRUNorthComponent.add(pnlRUUndertakingExpDate, BorderLayout.WEST);
									pnlRUUndertakingExpDate.setPreferredSize(new Dimension(150, 20));
									{
										pnlRUUndertakingExpDateWest = new JPanel(new BorderLayout(3, 3));
										pnlRUUndertakingExpDate.add(pnlRUUndertakingExpDateWest, BorderLayout.WEST);
										pnlRUUndertakingExpDateWest.setPreferredSize(new Dimension(150, 0));
										{
											chkRUUndertaking = new JCheckBox();
											pnlRUUndertakingExpDateWest.add(chkRUUndertaking, BorderLayout.WEST);
											chkRUUndertaking.addItemListener(new ItemListener() {

												public void itemStateChanged(ItemEvent e) {
													Boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);

													if(isSelected){
														dateRUUndertakingExp.getCalendarButton().setEnabled(true);
														dateRUReleasedDate.setEditable(true);
													}else{
														dateRUUndertakingExp.getCalendarButton().setEnabled(false);
														dateRUUndertakingExp.setEditable(false);
													}
												}
											});
										}
										{
											dateRUUndertakingExp = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
											pnlRUUndertakingExpDateWest.add(dateRUUndertakingExp);
											dateRUUndertakingExp.setPreferredSize(new Dimension(150, 0));
										}
									}

								}
								{
									pnlRUDOASTransmitted = new JPanel(new BorderLayout(3, 3));
									pnlRUNorthComponent.add(pnlRUDOASTransmitted, BorderLayout.WEST);
									{
										pnlRUDOASTransmittedWest = new JPanel(new BorderLayout(3, 3));
										pnlRUDOASTransmitted.add(pnlRUDOASTransmittedWest, BorderLayout.WEST);
										pnlRUDOASTransmittedWest.setPreferredSize(new Dimension(150, 0));
										{
											chkRUDOASTransmitted = new JCheckBox();
											pnlRUDOASTransmittedWest.add(chkRUDOASTransmitted, BorderLayout.WEST);
											chkRUDOASTransmitted.addItemListener(new ItemListener() {

												public void itemStateChanged(ItemEvent e) {
													Boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);

													if(isSelected){
														dateRUDOASTransmitted.getCalendarButton().setEnabled(true);
														dateRUDOASTransmitted.setEditable(true);
														txtRUTransferCostPaid.setEditable(true);
													}else{
														dateRUDOASTransmitted.getCalendarButton().setEnabled(false);
														dateRUDOASTransmitted.setEditable(false);
														txtRUTransferCostPaid.setEditable(false);
														txtRUTransferCostPaid.setValue(new BigDecimal("0.00"));
													}
												}
											});
										}
										{
											dateRUDOASTransmitted = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
											pnlRUDOASTransmittedWest.add(dateRUDOASTransmitted);
											dateRUDOASTransmitted.setPreferredSize(new Dimension(150, 0));
										}
									}
									{
										lblRUTransferCostPaid = new JLabel("Transfer Cost Paid", JLabel.TRAILING);
										pnlRUDOASTransmitted.add(lblRUTransferCostPaid);
									}
									{
										txtRUTransferCostPaid = new _JXFormattedTextField(JXTextField.RIGHT);
										pnlRUDOASTransmitted.add(txtRUTransferCostPaid, BorderLayout.EAST);
										txtRUTransferCostPaid.setPreferredSize(new Dimension(150, 0));
										txtRUTransferCostPaid.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									}
								}
							}
						}
						{
							pnlRUCenter = new JPanel(new BorderLayout(3, 3));
							pnlReleaseUndertaking.add(pnlRUCenter, BorderLayout.CENTER);
							{
								scrollRUCenter = new JScrollPane();
								pnlRUCenter.add(scrollRUCenter, BorderLayout.CENTER);
								{
									modelREMRelease = new modelBankREMRelease();
									tblRUCenter = new _JTableMain(modelREMRelease);
									scrollRUCenter.setViewportView(tblRUCenter);
									scrollRUCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								}
								{
									rowHeaderRUCenter = tblRUCenter.getRowHeader();
									rowHeaderRUCenter.setModel(new DefaultListModel());
									scrollRUCenter.setRowHeaderView(rowHeaderRUCenter);
									scrollRUCenter.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
					}
				}
				{
					pnlLoanStatusSouth = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlLoanStatus.add(pnlLoanStatusSouth, BorderLayout.SOUTH);
					{
						btnLSNew = new JButton("New");
						pnlLoanStatusSouth.add(btnLSNew);
						btnLSNew.setActionCommand("Loan Status New");
						btnLSNew.addActionListener(this);
					}
					{
						btnLSEdit = new JButton("Edit");
						pnlLoanStatusSouth.add(btnLSEdit);
						btnLSEdit.setActionCommand("Loan Status Edit");
						btnLSEdit.addActionListener(this);
					}
					{
						btnLSSave = new JButton("Save");
						pnlLoanStatusSouth.add(btnLSSave);
						btnLSSave.setActionCommand("Loan Status Save");
						btnLSSave.addActionListener(this);
					}
					{
						btnLSReset = new JButton("Reset");
						pnlLoanStatusSouth.add(btnLSReset);
						btnLSReset.setActionCommand("Loan Status Reset");
						btnLSReset.addActionListener(this);
					}
				}
			}
		}
		resetQualifiedAccounts();
		clearLoanStatus();
	}//XXX END OF INIT GUI

	/*private void setQualifytBtnState(Boolean sSave, Boolean sClear){
		btnQASave.setEnabled(sSave);
		btnQAReset.setEnabled(sClear);
	}*/

	private void setLoanStatusBtnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sClear){
		btnLSNew.setEnabled(sNew);
		btnLSEdit.setEnabled(sEdit);
		btnLSSave.setEnabled(sSave);
		btnLSReset.setEnabled(sClear);
	}

	private void pnlStateLoanStatus(Boolean sREMStatus, Boolean sLOGValidity, Boolean sReleaseUndertaking){
		tabLoanStatusCenter.setEnabledAt(0, sREMStatus);
		tabLoanStatusCenter.setEnabledAt(1, sLOGValidity);
		tabLoanStatusCenter.setEnabledAt(2, sReleaseUndertaking);
	}

	private Boolean toSave(){

		if(tabCenter.getSelectedIndex() == 0){
			if(_BankREM.getSelectedQualifiedAcct(modelQualifiedAcct) == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		if(tabCenter.getSelectedIndex() == 1){
			if(tabLoanStatusCenter.getSelectedIndex() == 0){
				if(lookupREMLoanStatus.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Loan Status", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(dateREMStatusDate.getDate() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input status date", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
			
			if(tabLoanStatusCenter.getSelectedIndex() == 1){
				if(lookupLOGBank.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Bank", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(dateLOGReleased.getDate() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input release date", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(txtLOGAmount.getValued() == null || txtLOGAmount.getValued().equals(new BigDecimal("0.00"))){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input LOG Amount", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(dateLOGExpiry.getDate() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input expiry date", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(txtLOGREMFees.getValued() == null || txtLOGREMFees.getValued().equals(new BigDecimal("0.00"))){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input REM Fees", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(dateLOGDatePaid.getDate() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input date paid", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(txtLOGMRIFireFees.getValued() == null || txtLOGMRIFireFees.getValued().equals(new BigDecimal("0.00"))){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input MRI/Fire Fees", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
			
			if(tabLoanStatusCenter.getSelectedIndex() == 2){
				if(dateRUReleasedDate.getDate() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input released date", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(dateRUUndertakingExp.getDate() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Undertaking Expiry Date", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(dateRUDOASTransmitted.getDate() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input DOAS Transmitted Date", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(chkRUDOASTransmitted.isSelected()){
					if(txtRUTransferCostPaid.getValued() == null || txtRUTransferCostPaid.getValued().equals(new BigDecimal("0.00"))){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Transfer Cost Paid", "Save", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}
			}
			
		}
		
		return true;
	}

	private void resetQualifiedAccounts(){
		modelQualifiedAcct.clear();
		rowHeaderQualifiedAcct.setModel(new DefaultListModel());
		scrollQualifiedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		
		System.out.println("Dumaan dito save reset ng Qaulified Accounts");
		_BankREM.displayQualifiedAccounts(cmbSearchBy.getSelectedIndex(), txtSearchBy.getText(), modelQualifiedAcct, rowHeaderQualifiedAcct);
		tblQualifiedAcct.packAll();
		scrollQualifiedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedAcct.getRowCount())));
	}

	private void resetLoanStatus(){
		if(lookupClient.getValue() != null){

			if(tabLoanStatusCenter.getSelectedIndex() == 0){
				_BankREM.displayLoanStatus(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), modelREMStat, rowHeaderREMStatusCenter);
				tblREMStatusCenter.packAll();
				scrollREMStatusCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblREMStatusCenter.getRowCount())));
				dateREMStatusDate.getCalendarButton().setEnabled(false);
				lookupREMLoanStatus.setEditable(false);
			}

			if(tabLoanStatusCenter.getSelectedIndex() == 1){
				_BankREM.displayLOG(lookupClient.getValue(), txtProjID.getText() ,txtUnitID.getText(), modelREMLOG, rowHeaderLOGValidityCenter);
				tblLOGValidityCenter.packAll();
				scrollLOGValidityCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLOGValidityCenter.getRowCount())));
			}

			if(tabLoanStatusCenter.getSelectedIndex() == 2){
				_BankREM.displayReleasedUndertaking(lookupClient.getValue(), txtUnitID.getText(), modelREMRelease, rowHeaderRUCenter);
				tblRUCenter.packAll();
				scrollRUCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRUCenter.getRowCount())));
			}
		}
		setLoanStatusBtnState(false, false, false, false);
	}

	private void newLoanStatus(){
		if(tabLoanStatusCenter.getSelectedIndex() == 0){
			lookupREMLoanStatus.setEditable(true);
			dateREMStatusDate.getCalendarButton().setEnabled(true);

			pnlStateLoanStatus(true, false, false);
			setLoanStatusBtnState(false, false, true, true);

			if(tblREMStatusCenter.getSelectedRows().length == 1){
				tblREMStatusCenter.clearSelection();
			}
		}
		if(tabLoanStatusCenter.getSelectedIndex() == 1){
			lookupLOGBank.setEditable(true);
			dateLOGReleased.getCalendarButton().setEnabled(true);
			dateLOGExpiry.getCalendarButton().setEnabled(true);
			chkLOGDatePaid.setEnabled(true);

			this.setComponentsEditable(pnlLOGValidityNorth, true);
			dateLOGDatePaid.setEditable(false);

			pnlStateLoanStatus(false, true, false);
			setLoanStatusBtnState(false, false, true, true);
		}
		if(tabLoanStatusCenter.getSelectedIndex() == 2){
			chkRUReleaseDate.setEnabled(true);
			chkRUUndertaking.setEnabled(true);
			chkRUDOASTransmitted.setEnabled(true);

			pnlStateLoanStatus(false, false, true);
			setLoanStatusBtnState(false, false, true, true);
		}
	}

	private void clearLoanStatus(){
		//if(tabLoanStatusCenter.getSelectedIndex() == 0){
		lookupREMLoanStatus.setValue(null);
		txtREMLoanStatus.setText("");
		dateREMStatusDate.setDate(Calendar.getInstance().getTime());
		dateREMStatusDate.getCalendarButton().setEnabled(false);

		this.setComponentsEditable(pnlREMStatusNorth, false);

		modelREMStat.clear();
		rowHeaderREMStatusCenter.setModel(new DefaultListModel());
		scrollREMStatusCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		//}
		//if(tabLoanStatusCenter.getSelectedIndex() == 1){

		lookupLOGBank.setValue(null);
		txtLOGBank.setText("");
		dateLOGReleased.setDate(Calendar.getInstance().getTime());
		dateLOGReleased.getCalendarButton().setEnabled(false);
		chkLOGDatePaid.setEnabled(false);
		chkLOGDatePaid.setSelected(false);
		dateLOGDatePaid.setDate(Calendar.getInstance().getTime());
		dateLOGDatePaid.getCalendarButton().setEnabled(false);
		dateLOGExpiry.setDate(Calendar.getInstance().getTime());
		dateLOGExpiry.getCalendarButton().setEnabled(false);
		txtLOGREMFees.setValue(new BigDecimal("0.00"));
		txtLOGAmount.setValue(new BigDecimal("0.00"));
		txtLOGMRIFireFees.setValue(new BigDecimal("0.00"));

		this.setComponentsEditable(pnlLOGValidityNorth, false);

		modelREMLOG.clear();
		rowHeaderLOGValidityCenter.setModel(new DefaultListModel());
		scrollLOGValidityCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		//}
		//if(tabLoanStatusCenter.getSelectedIndex() == 2){

		chkRUReleaseDate.setEnabled(false);
		chkRUReleaseDate.setSelected(false);
		dateRUReleasedDate.setDate(Calendar.getInstance().getTime());
		dateRUReleasedDate.getCalendarButton().setEnabled(false);
		chkRUUndertaking.setEnabled(false);
		chkRUUndertaking.setSelected(false);
		dateRUUndertakingExp.setDate(Calendar.getInstance().getTime());
		dateRUUndertakingExp.getCalendarButton().setEnabled(false);
		chkRUDOASTransmitted.setEnabled(false);
		chkRUDOASTransmitted.setSelected(false);
		dateRUDOASTransmitted.setDate(Calendar.getInstance().getTime());
		dateRUDOASTransmitted.getCalendarButton().setEnabled(false);
		txtRUTransferCostPaid.setValue(new BigDecimal("0.00"));
		
		tblREMStatusCenter.setEnabled(true);
		
		this.setComponentsEditable(pnlRUNorth, false);

		modelREMRelease.clear();
		rowHeaderRUCenter.setModel(new DefaultListModel());
		scrollRUCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		//}
		setLoanStatusBtnState(true, false, false, false);
		pnlStateLoanStatus(true, true, true);
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Qualified Accounts Save")){
			if(toSave()){
				_BankREM.saveQualifiedAccounts(modelQualifiedAcct);
				resetQualifiedAccounts();
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Account(s) tagged has been save", actionCommand, JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if(actionCommand.equals("Qualified Accounts Reset")){
			resetQualifiedAccounts();
		}
		if(actionCommand.equals("Loan Status New")){
			newLoanStatus();
		}
		if(actionCommand.equals("Loan Status Edit")){
			if(tabLoanStatusCenter.getSelectedIndex() == 0){
				if(tblREMStatusCenter.getSelectedRows().length == 1){
					tblREMStatusCenter.setEnabled(false);
					rowHeaderREMStatusCenter.setEnabled(false);
					setLoanStatusBtnState(false, false, true, true);
					pnlStateLoanStatus(true, false, false);
					lookupREMLoanStatus.setEditable(true);
					dateREMStatusDate.getCalendarButton().setEnabled(true);
				}
			}
		}
		if(actionCommand.equals("Loan Status Save")){
			if(toSave()){
				
				if(tabLoanStatusCenter.getSelectedIndex() == 0){
					if(tblREMStatusCenter.getSelectedRows().length == 1){
						int x = tblREMStatusCenter.getSelectedRow();
						Integer rec_id = (Integer) modelREMStat.getValueAt(x, 5);

						_BankREM.updateREMStatus(lookupREMLoanStatus.getValue(), dateREMStatusDate.getDate(), rec_id);
						_BankREM.displayLoanStatus(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), modelREMStat, rowHeaderREMStatusCenter);
						tblREMStatusCenter.packAll();
						scrollREMStatusCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblREMStatusCenter.getRowCount())));
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Status has been updated", actionCommand, JOptionPane.INFORMATION_MESSAGE);
					}else{
						_BankREM.saveREMStatus(lookupClient.getValue(), txtProjID.getText() ,txtUnitID.getText(), txtSeqNo.getText() ,lookupREMLoanStatus.getValue(), dateREMStatusDate.getDate());
						_BankREM.displayLoanStatus(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), modelREMStat, rowHeaderREMStatusCenter);
						tblREMStatusCenter.packAll();
						scrollREMStatusCenter.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblREMStatusCenter.getRowCount())));
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New current status has been saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
				if(tabLoanStatusCenter.getSelectedIndex() == 1){
					_BankREM.saveREMLOGValidity(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), lookupLOGBank.getValue(),(BigDecimal) txtLOGAmount.getValued(), 
							dateLOGReleased.getDate(), dateLOGExpiry.getDate(), (BigDecimal) txtLOGREMFees.getValued(), (BigDecimal) txtLOGMRIFireFees.getValued(), dateLOGDatePaid.getDate());
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "LOG has been saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
				}
				
				if(tabLoanStatusCenter.getSelectedIndex() == 2){
					_BankREM.saveREMRelease(lookupClient.getValue(), txtUnitID.getText(), dateRUReleasedDate.getDate(), 
							dateRUUndertakingExp.getDate(), dateRUDOASTransmitted.getDate(), (BigDecimal) txtRUTransferCostPaid.getValued());
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Release details has been saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
				}
				
				clearLoanStatus();
			}
		}

		if(actionCommand.equals("Loan Status Reset")){
			clearLoanStatus();
		}
	}
}
