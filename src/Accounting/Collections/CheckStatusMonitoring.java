package Accounting.Collections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import tablemodel.modelCheckStatus;
import tablemodel.modelCheckStatus_total;
import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;

import com.toedter.calendar.JTextFieldDateEditor;

import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class CheckStatusMonitoring extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Check Status Monitoring";
	static Dimension SIZE = new Dimension(1200, 600);
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlCompany;
	private JPanel pnlCheckDetails;
	private JPanel pblCheckTable;
	private JPanel pnlSouth;
	private JPanel pnlUnit;
	private JPanel pnlUnit_a;
	private JPanel pnlUnit_b;
	private JPanel pnlCheckDetails_a;
	private JPanel pnlCheckDetails_b;
	private JPanel pnlCompany_a;
	private JPanel pnlCompany_b;

	private JLabel lblCheckStatus;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblUnit;
	private JLabel lblBank;
	private JLabel lblCheckNo;

	private JList rowHeaderSample2Main;
	private _JScrollPaneMain scrollSample2Main;
	private _JScrollPaneTotal scrollSample2Total;

	private modelCheckStatus modelSample2Main;
	private modelCheckStatus_total modelSample2Total;

	private _JTableTotal tblSample2Total;
	private _JTableMain tblSample2Main;

	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private _JLookup lookupPhase;
	private _JLookup lookupClientName;
	private _JLookup lookupBank;
	private _JLookup lookupCheckNo;

	private _JTagLabel tagProject;
	private _JTagLabel tagCompany;
	private _JTagLabel tagPhase;
	private _JTagLabel tagUnit;
	private _JTagLabel tagBank;
	private _JTagLabel tagCheckNo;

	private _JDateChooser dateDateTo;
	private _JDateChooser dteDateFrom;

	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnRefresh;

	private JComboBox cmbStatus;
	private JComboBox cmbStatusPop;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	String co_id = "";
	String company = "";
	String proj_id = "";
	public static String company_logo;
	String phase = "";
	String entity_id = "";
	String check_no = "";
	String bank_id = "";
	String jv_no = "";
	private JButton btnPreview;
	private JLabel lblDateFr;
	private _JDateChooser dteRefDateFr;
	private _JDateChooser dteRefDateTo;
	private JButton btnOK;
	private JXPanel panDate;

	private final Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);

	_CheckStatusMonitoring csm = new _CheckStatusMonitoring(); 
	
	public CheckStatusMonitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CheckStatusMonitoring(String title) {
		super(title);

	}

	public CheckStatusMonitoring(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(1148, 573));

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(798, 85));
		pnlMain.setLayout(new BorderLayout(5, 5));

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			// pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(1136, 114));

			{
				pnlCompany = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlCompany, BorderLayout.WEST);
				pnlCompany.setBorder(JTBorderFactory.createTitleBorder("Company & Project Options"));
				pnlCompany.setPreferredSize(new java.awt.Dimension(484, 148));

				pnlCompany_a = new JPanel(new GridLayout(3, 2, 5, 5));
				pnlCompany.add(pnlCompany_a, BorderLayout.WEST);
				pnlCompany_a.setPreferredSize(new java.awt.Dimension(141, 148));
				{
					lblCompany = new JLabel("Company", JLabel.TRAILING);
					pnlCompany_a.add(lblCompany);
					lblCompany.setBounds(8, 11, 62, 12);
					lblCompany.setPreferredSize(new java.awt.Dimension(101, 16));
					lblCompany.setFont(font11);
				}
				{
					lookupCompany = new _JLookup(null, "Company", 0, 2);
					pnlCompany_a.add(lookupCompany);
					lookupCompany.setBounds(20, 27, 20, 25);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.setReturnColumn(0);
					lookupCompany.setFont(font11);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								if (confirmLeaveScreen() == false) {

									co_id = (String) data[0];
									proj_id = "";
									phase = "";
									company = (String) data[1];
									company_logo = (String) data[3];

									tagCompany.setTag(company);
									lblProject.setEnabled(true);
									lookupProject.setValue("");
									lookupProject.setEnabled(true);
									tagProject.setEnabled(true);
									tagProject.setTag("");
									lookupPhase.setEnabled(false);
									lookupPhase.setValue("");
									lblPhase.setEnabled(false);

									displayCheckList();

									KEYBOARD_MANAGER.focusNextComponent();

									lookupProject.setLookupSQL(sql_project(co_id));
									lookupCheckNo.setLookupSQL(sql_checkno());
									lookupBank.setLookupSQL(sql_bank());
									lookupClientName.setLookupSQL(sql_client(proj_id, phase));
									refreshCheckInfo();
								}

								else {
									btnSave.doClick();
								}

								enableFields(true);
								btnRefresh.setEnabled(true);
								btnCancel.setEnabled(true);
							}
						}
					});
					lookupCompany.setValue("");
				}
				{
					lblProject = new JLabel("Project", JLabel.TRAILING);
					pnlCompany_a.add(lblProject);
					lblProject.setEnabled(false);
					lblProject.setFont(font11);
				}
				{
					lookupProject = new _JLookup(null, "Project", 0, 2);
					pnlCompany_a.add(lookupProject);
					lookupProject.setBounds(20, 27, 20, 25);
					lookupProject.setEnabled(false);
					lookupProject.setFont(font11);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								if (confirmLeaveScreen() == false) {

									proj_id = (String) data[0];
									phase = "";
									String name = (String) data[1];
									tagProject.setTag(name);

									String sql = "select distinct on (a.phase) a.phase, b.proj_alias, b.proj_name  \n"
											+ "from ( select * from mf_unit_info ) a \n"
											+ "left join (select * from mf_project where proj_id = '" + proj_id
											+ "' ) b on a.proj_id = b.proj_id \n";

									lookupPhase.setLookupSQL(sql);

									lblPhase.setEnabled(true);
									lookupPhase.setValue("");
									lookupPhase.setEnabled(true);
									tagPhase.setEnabled(true);
									tagPhase.setTag("");

									displayCheckList();

									KEYBOARD_MANAGER.focusNextComponent();
									lookupCheckNo.setLookupSQL(sql_checkno());
									lookupBank.setLookupSQL(sql_bank());
									lookupClientName.setLookupSQL(sql_client(proj_id, phase));
									refreshCheckInfo();
								} else {
									btnSave.doClick();
								}
							}
						}
					});
				}
				{
					lblPhase = new JLabel("Phase", JLabel.TRAILING);
					pnlCompany_a.add(lblPhase);
					lblPhase.setEnabled(false);
					lblPhase.setFont(font11);
				}
				{
					lookupPhase = new _JLookup(null, "Phase", 0, 2);
					pnlCompany_a.add(lookupPhase);
					lookupPhase.setBounds(20, 27, 20, 25);
					lookupPhase.setEnabled(false);
					lookupPhase.setFont(font11);
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								phase = (String) data[0];

								displayCheckList();

								KEYBOARD_MANAGER.focusNextComponent();
								lookupCheckNo.setLookupSQL(sql_checkno());
								lookupBank.setLookupSQL(sql_bank());
								lookupClientName.setLookupSQL(sql_client(proj_id, phase));
								refreshCheckInfo();
							}
						}
					});
				}

				pnlCompany_b = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlCompany.add(pnlCompany_b, BorderLayout.CENTER);
				pnlCompany_b.setPreferredSize(new java.awt.Dimension(643, 150));

				{
					tagCompany = new _JTagLabel("[ ]");
					pnlCompany_b.add(tagCompany);
					tagCompany.setBounds(209, 27, 700, 22);
					tagCompany.setEnabled(true);
					tagCompany.setPreferredSize(new java.awt.Dimension(700, 22));
					tagCompany.setFont(font11);
				}
				{
					tagProject = new _JTagLabel("[ ]");
					pnlCompany_b.add(tagProject);
					tagProject.setBounds(209, 27, 700, 22);
					tagProject.setEnabled(false);
					tagProject.setFont(font11);
				}
				{
					tagPhase = new _JTagLabel("[ ]");
					pnlCompany_b.add(tagPhase);
					tagPhase.setBounds(209, 27, 700, 22);
					tagPhase.setEnabled(false);
					tagPhase.setVisible(false);
					tagPhase.setFont(font11);
				}
			}
			{
				pnlUnit = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlUnit, BorderLayout.CENTER);
				pnlUnit.setBorder(JTBorderFactory.createTitleBorder("Check Info. Options"));
				pnlUnit.setPreferredSize(new java.awt.Dimension(721, 168));

				pnlUnit_a = new JPanel(new GridLayout(3, 2, 5, 5));
				pnlUnit.add(pnlUnit_a, BorderLayout.WEST);
				pnlUnit_a.setPreferredSize(new java.awt.Dimension(197, 148));

				{
					lblUnit = new JLabel("Client Name ", JLabel.TRAILING);
					pnlUnit_a.add(lblUnit);
					lblUnit.setEnabled(false);
					lblUnit.setFont(font11);
				}
				{
					lookupClientName = new _JLookup(null, "Unit", 0, 2);
					pnlUnit_a.add(lookupClientName);
					lookupClientName.setBounds(20, 27, 20, 25);
					lookupClientName.setLookupSQL(sql_client(lookupProject.getText(), lookupPhase.getText()));
					lookupClientName.setEnabled(false);
					lookupClientName.setFont(font11);
					lookupClientName.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								if (confirmLeaveScreen() == false) {

									entity_id = (String) data[0];
									String name = (String) data[1];
									tagUnit.setTag(name);

									lookupCheckNo.setText("");
									lookupBank.setText("");
									tagCheckNo.setText("[ ]");
									tagBank.setText("[ ]");
									lookupCheckNo.setValue(null);
									lookupBank.setValue(null);

									displayCheckList();

									KEYBOARD_MANAGER.focusNextComponent();
									lookupCheckNo.setLookupSQL(sql_checkno());
								} else {
									btnSave.doClick();
								}
							}
						}
					});
					lookupClientName.setValue("");
				}
				{
					lblCheckNo = new JLabel("Check No. ", JLabel.TRAILING);
					pnlUnit_a.add(lblCheckNo);
					lblCheckNo.setEnabled(false);
					lblCheckNo.setFont(font11);
				}
				{
					lookupCheckNo = new _JLookup(null, "CheckNo", 0, 2);
					pnlUnit_a.add(lookupCheckNo);
					lookupCheckNo.setBounds(20, 27, 20, 25);
					lookupCheckNo.setLookupSQL(sql_checkno());
					lookupCheckNo.setEnabled(false);
					lookupCheckNo.setFont(font11);
					lookupCheckNo.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								check_no = (String) data[0];
								if (confirmLeaveScreen() == false) {
									String name = (String) data[1];
									tagCheckNo.setTag(name);

									lookupBank.setText("");
									tagBank.setText("[ ]");
									lookupBank.setValue(null);

									displayCheckList();
									KEYBOARD_MANAGER.focusNextComponent();
								} else {
									btnSave.doClick();
								}

							}
						}
					});
					lookupCheckNo.setValue("");
				}
				{
					lblBank = new JLabel("Bank ", JLabel.TRAILING);
					pnlUnit_a.add(lblBank);
					lblBank.setEnabled(false);
					lblBank.setFont(font11);
				}

				{
					lookupBank = new _JLookup(null, "Bank", 0, 2);
					pnlUnit_a.add(lookupBank);
					lookupBank.setBounds(20, 27, 20, 25);
					lookupBank.setEnabled(false);
					lookupBank.setLookupSQL(sql_bank());
					lookupBank.setFont(font11);
					lookupBank.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								bank_id = (String) data[0];
								if (confirmLeaveScreen() == false) {
									String name = (String) data[1];
									displayCheckList();
									tagBank.setTag(name);
									KEYBOARD_MANAGER.focusNextComponent();
								} else {
									btnSave.doClick();
								}

							}
						}
					});
					lookupBank.setValue("");
				}

				pnlUnit_b = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlUnit.add(pnlUnit_b, BorderLayout.CENTER);
				pnlUnit_b.setPreferredSize(new java.awt.Dimension(141, 148));

				{
					tagUnit = new _JTagLabel("[ ]");
					pnlUnit_b.add(tagUnit);
					tagUnit.setBounds(209, 27, 567, 22);
					tagUnit.setEnabled(false);
					tagUnit.setFont(font11);
				}
				{
					tagCheckNo = new _JTagLabel("[ ]");
					pnlUnit_b.add(tagCheckNo);
					tagCheckNo.setBounds(209, 27, 567, 22);
					tagCheckNo.setEnabled(false);
					tagCheckNo.setFont(font11);
				}
				{
					tagBank = new _JTagLabel("[ ]");
					pnlUnit_b.add(tagBank);
					tagBank.setBounds(209, 27, 567, 22);
					tagBank.setEnabled(false);
					tagBank.setFont(font11);
				}
			}
			{
				pnlCheckDetails = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlCheckDetails, BorderLayout.EAST);
				pnlCheckDetails.setPreferredSize(new java.awt.Dimension(240, 148));
				pnlCheckDetails.setBorder(JTBorderFactory.createTitleBorder("Check Status / Date Options"));

				pnlCheckDetails_a = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlCheckDetails.add(pnlCheckDetails_a, BorderLayout.WEST);
				pnlCheckDetails_a.setPreferredSize(new java.awt.Dimension(75, 148));

				// 1
				{
					lblCheckStatus = new JLabel("Check Status", JLabel.TRAILING);
					pnlCheckDetails_a.add(lblCheckStatus);
					lblCheckStatus.setEnabled(false);
					lblCheckStatus.setFont(font11);
				}

				{
					lblDateFrom = new JLabel("Date From", JLabel.TRAILING);
					pnlCheckDetails_a.add(lblDateFrom);
					lblDateFrom.setEnabled(false);
					lblDateFrom.setFont(font11);
				}
				// 3
				{
					lblDateTo = new JLabel("Date To", JLabel.TRAILING);
					pnlCheckDetails_a.add(lblDateTo);
					lblDateTo.setEnabled(false);
					lblDateTo.setFont(font11);
				}

				pnlCheckDetails_b = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlCheckDetails.add(pnlCheckDetails_b, BorderLayout.CENTER);
				pnlCheckDetails_b.setPreferredSize(new java.awt.Dimension(141, 148));

				String status[] = { "All Check Status", "Bounced", "Deposit", "Deposited", "For Deposit", "Good",
						"Hold", "Post Dated", "Replaced", "Retrnd to Buyer", "Turnover to Bank", "Withdrawn",
						"For Withdrawal", "Transmitted to Carmona Branch" };
				cmbStatus = new JComboBox(status);
				pnlCheckDetails_b.add(cmbStatus);
				cmbStatus.setSelectedItem(null);
				cmbStatus.setBounds(537, 15, 160, 21);
				cmbStatus.setEnabled(false);
				cmbStatus.setSelectedIndex(0);
				cmbStatus.setFont(font11);
				cmbStatus.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent evt) {
						if (confirmLeaveScreen() == false) {
							displayCheckList();
						} else {
							btnSave.doClick();
						}
					}
				});

				{
					dteDateFrom = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlCheckDetails_b.add(dteDateFrom);
					dteDateFrom.setBounds(485, 7, 125, 21);
					dteDateFrom.setDate(null);
					dteDateFrom.setEnabled(false);
					dteDateFrom.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor) dteDateFrom.getDateEditor()).setEditable(false);
					dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteDateFrom.setFont(font11);
					dteDateFrom.addPropertyChangeListener(new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {

							if (confirmLeaveScreen() == false) {
								System.out.println("Dumaan dito sa Display Check ListFrom");
								displayCheckList();
							} else {
								btnSave.doClick();
							}
						}

					});
				}

				{
					dateDateTo = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlCheckDetails_b.add(dateDateTo);
					dateDateTo.setBounds(485, 7, 125, 21);
					dateDateTo.setDate(null);
					dateDateTo.setEnabled(false);
					dateDateTo.setDateFormatString("yyyy-MM-dd");
					dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					((JTextFieldDateEditor) dateDateTo.getDateEditor()).setEditable(false);
					dateDateTo.setFont(font11);
					dateDateTo.addPropertyChangeListener(new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
							if (confirmLeaveScreen() == false) {
								displayCheckList();
								System.out.println("Dumaan dito sa Display Check ListTo");
							} else {
								btnSave.doClick();
							}
						}
					});
				}
				// 2
			}
		}
		{
			pblCheckTable = new JPanel();
			pnlMain.add(pblCheckTable, BorderLayout.CENTER);
			pblCheckTable.setLayout(new BorderLayout(5, 5));
			pblCheckTable.setBorder(JTBorderFactory.createTitleBorder("Checks Details"));

			scrollSample2Main = new _JScrollPaneMain();
			pblCheckTable.add(scrollSample2Main, BorderLayout.CENTER);
			{
				modelSample2Main = new modelCheckStatus();

				tblSample2Main = new _JTableMain(modelSample2Main);
				scrollSample2Main.setViewportView(tblSample2Main);
				
				tblSample2Main.setEditable(false);
				tblSample2Main.setSortable(false);
				tblSample2Main.addMouseListener(this);
				modelSample2Main.setEditable(true);
				tblSample2Main.setEditable(true);

				tblSample2Main.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if (tblSample2Main.rowAtPoint(e.getPoint()) == -1) {
							tblSample2Total.clearSelection();
						} else {
							tblSample2Main.setCellSelectionEnabled(true);
						}
					}

					public void mouseReleased(MouseEvent e) {
						if (tblSample2Main.rowAtPoint(e.getPoint()) == -1) {
							tblSample2Total.clearSelection();
						} else {
							tblSample2Main.setCellSelectionEnabled(true);
						}
					}
				});

				tblSample2Main.getColumnModel().getColumn(0).setPreferredWidth(200);
				tblSample2Main.getColumnModel().getColumn(1).setPreferredWidth(75);
				tblSample2Main.getColumnModel().getColumn(2).setPreferredWidth(75);
				tblSample2Main.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblSample2Main.getColumnModel().getColumn(4).setPreferredWidth(150);
				tblSample2Main.getColumnModel().getColumn(5).setPreferredWidth(100);
				tblSample2Main.getColumnModel().getColumn(6).setPreferredWidth(250);
				tblSample2Main.getColumnModel().getColumn(7).setPreferredWidth(100);
				tblSample2Main.getColumnModel().getColumn(8).setPreferredWidth(100);
				tblSample2Main.getColumnModel().getColumn(9).setPreferredWidth(50);
				tblSample2Main.getColumnModel().getColumn(10).setPreferredWidth(100);
				tblSample2Main.getColumnModel().getColumn(11).setPreferredWidth(100);
				tblSample2Main.getColumnModel().getColumn(12).setPreferredWidth(100);
				tblSample2Main.getColumnModel().getColumn(13).setPreferredWidth(100);
				tblSample2Main.getColumnModel().getColumn(14).setPreferredWidth(100);
			}
			{
				rowHeaderSample2Main = tblSample2Main.getRowHeader22();
				scrollSample2Main.setRowHeaderView(rowHeaderSample2Main);
				scrollSample2Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		{
			scrollSample2Total = new _JScrollPaneTotal(scrollSample2Main);
			pblCheckTable.add(scrollSample2Total, BorderLayout.SOUTH);
			{
				modelSample2Total = new modelCheckStatus_total();
				modelSample2Total.addRow(new Object[] { "Total", "", "", "", "", new BigDecimal(0.00), "", "", null });

				tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
				scrollSample2Total.setViewportView(tblSample2Total);
				tblSample2Total.setFont(dialog11Bold);
				((_JTableTotal) tblSample2Total).setTotalLabel(0);
			}
		}
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			// pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new Dimension(55, 30));
			{
				JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));

				{
					btnSave = new JButton("Save");
					pnlSouthCenter.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview Transactions");
					pnlSouthCenter.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
					btnPreview.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							/*
							 * int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate,
							 * "Report Period", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
							 * new Object[] {}, null);
							 */
							int scanOption = JOptionPane.showOptionDialog(getContentPane(), panDate, "Report Period",
									JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
							if (scanOption == JOptionPane.CLOSED_OPTION) {
								try {

								} catch (java.lang.ArrayIndexOutOfBoundsException ev) {
								}
							}
						}
					});
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlSouthCenter.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.addActionListener(this);
					btnRefresh.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenter.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}
		{
			panDate = new JXPanel(new BorderLayout(1, 1));
			panDate.setPreferredSize(new java.awt.Dimension(300, 150));
			{
				JXPanel panDateMain = new JXPanel(new GridLayout(4, 1, 5, 5));
				panDate.add(panDateMain, BorderLayout.CENTER);
				{
					JXPanel panDateFrom = new JXPanel(new BorderLayout(5, 5));
					panDateMain.add(panDateFrom, BorderLayout.CENTER);
					{
						{
							lblDateFr = new JLabel("Date From: ");
							panDateFrom.add(lblDateFr, BorderLayout.LINE_START);
							lblDateFr.setPreferredSize(new Dimension(75, 0));
							lblDateFr.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							dteRefDateFr = new _JDateChooser("MM/dd/yy", "##/##/####", '_');
							panDateFrom.add(dteRefDateFr, BorderLayout.CENTER);
							dteRefDateFr.setDate(null);
							dteRefDateFr.setEnabled(true);
							dteRefDateFr.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor) dteRefDateFr.getDateEditor()).setEditable(false);
							dteRefDateFr.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteRefDateFr.addPropertyChangeListener(new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {

								}
							});
						}
					}
					JXPanel panDateTo = new JXPanel(new BorderLayout(5, 5));
					panDateMain.add(panDateTo, BorderLayout.CENTER);
					{
						{
							lblDateTo = new JLabel("Date To: ");
							panDateTo.add(lblDateTo, BorderLayout.LINE_START);
							lblDateTo.setPreferredSize(new Dimension(75, 0));
							lblDateTo.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							dteRefDateTo = new _JDateChooser("MM/dd/yy", "##/##/####", '_');
							panDateTo.add(dteRefDateTo, BorderLayout.CENTER);
							dteRefDateTo.setDate(null);
							dteRefDateTo.setEnabled(true);
							dteRefDateTo.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor) dteRefDateTo.getDateEditor()).setEditable(false);
							dteRefDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteRefDateTo.addPropertyChangeListener(new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {

								}
							});
						}
					}
					JXPanel panStatus = new JXPanel(new BorderLayout(5, 5));
					panDateMain.add(panStatus, BorderLayout.CENTER);
					{
						{
							JLabel lblStatus = new JLabel("Status: ");
							panStatus.add(lblStatus, BorderLayout.LINE_START);
							lblStatus.setPreferredSize(new Dimension(75, 0));
							lblStatus.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							String status[] = { "All Check Status", "Bounced", "Deposit", "Deposited", "For Deposit",
									"Good", "Hold", "Post Dated", "Replaced", "Retrnd to Buyer", "Turnover to Bank",
									"Withdrawn", "For Withdrawal" };
							cmbStatusPop = new JComboBox(status);
							panStatus.add(cmbStatusPop, BorderLayout.CENTER);
							cmbStatusPop.setSelectedItem(1);
							cmbStatusPop.setEnabled(true);
						}
					}
					{
						btnOK = new JButton("OK");
						panDateMain.add(btnOK, BorderLayout.CENTER);
						btnOK.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Window optionPaneWindow = SwingUtilities.getWindowAncestor(panDate);
								optionPaneWindow.dispose();
								preview_transaction(cmbStatusPop.getSelectedItem().toString());
							}
						});
					}
				}
			}
		}

		FncTables.bindColumnTables(tblSample2Main, tblSample2Total);
		this.setFocusTraversalPolicy(
				new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, lookupClientName, lookupCheckNo,
						lookupBank, cmbStatus, dteDateFrom, dateDateTo, (JTextField) dateDateTo.getDateEditor()));
		this.setBounds(0, 0, 1148, 573);

		setComponentEnabled(pnlCheckDetails, true);
		initialize_comp();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Refresh")) {
			refresh();
		}

		if (e.getActionCommand().equals("Cancel")) {
			cancel();
		}

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11") == true) {
			edit();
		} else if (e.getActionCommand().equals("Edit")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to change check status.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11") == true) {
			save();
		} else if (e.getActionCommand().equals("Save")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to change check status.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {
			displayAddUnits();
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		edit();
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void edit() {
		btnRefresh.setEnabled(false);
		btnSave.setEnabled(true);
		btnPreview.setEnabled(false);
	}

	public void cancel() {
		if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			refreshFields();
			refresh_tablesMain();
			enableFields(false);
			btnSave.setEnabled(false);
			btnRefresh.setEnabled(false);
			btnCancel.setEnabled(false);
			btnPreview.setEnabled(false);
		}
	}

	public void refresh() {
		displayCheckList();
		JOptionPane.showMessageDialog(getContentPane(), "Data refreshed.", "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void preview_transaction(String strStatus) {// used
		String criteria = "Check Status Trasanctions";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		if (strStatus == "All Check Status") {
			strStatus = "";
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("date_from", dteRefDateFr.getDate());
		mapParameters.put("date_to", dteRefDateTo.getDate());
		mapParameters.put("check_status", strStatus);
		FncReport.generateReport("/Reports/rptCheckStatus_transactions_v2.jasper", reportTitle, company, mapParameters);
	}

	// refresh, enable, disable fields
	public void refreshFields() {

		lblPhase.setEnabled(false);
		lookupPhase.setEnabled(false);

		lookupPhase.setValue("");
		lookupProject.setValue("");

		tagPhase.setTag("");
		tagProject.setTag("");

		cmbStatus.setSelectedIndex(0);
		modelSample2Total
		.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00), new BigDecimal(0.00), null, null });

		btnRefresh.setEnabled(true);
		btnCancel.setEnabled(true);
		btnSave.setEnabled(true);
		btnPreview.setEnabled(true);

		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

		refreshCheckInfo();

		setSQL();
	}

	public void refreshCheckInfo() {

		lookupClientName.setValue("");
		lookupCheckNo.setValue("");
		lookupBank.setValue("");
		tagUnit.setTag("");
		tagCheckNo.setTag("");
		tagBank.setTag("");
		lookupClientName.setValue(null);
		lookupCheckNo.setValue(null);
		lookupBank.setValue(null);

		entity_id = "";
		check_no = "";
		bank_id = "";
		proj_id = "";
		phase = "";

	}

	public void setComponentEnabled(JPanel panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			comp.setEnabled(enable);
		}
	}

	public void refresh_tablesMain() {

		// reset table 1
		FncTables.clearTable(modelSample2Main);
		FncTables.clearTable(modelSample2Total);
		rowHeaderSample2Main = tblSample2Main.getRowHeader22();
		scrollSample2Main.setRowHeaderView(rowHeaderSample2Main);
		modelSample2Total.addRow(new Object[] { "Total", 0, "", "", "", new BigDecimal(0.00), "", "", null });

	}

	public void enableFields(Boolean x) {

		lblUnit.setEnabled(x);
		lblCheckNo.setEnabled(x);
		lblBank.setEnabled(x);
		lookupClientName.setEnabled(x);
		lookupCheckNo.setEnabled(x);
		lookupBank.setEnabled(x);
		tagUnit.setEnabled(x);
		tagCheckNo.setEnabled(x);
		tagBank.setEnabled(x);
		lblCheckStatus.setEnabled(x);
		cmbStatus.setEnabled(x);
		lblDateFrom.setEnabled(x);
		dteDateFrom.setEnabled(x);
		lblDateTo.setEnabled(x);
		dateDateTo.setEnabled(x);
	}

	public void initialize_comp() {

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION";
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		if (confirmLeaveScreen() == false) {

			tagCompany.setTag(company);
			lblProject.setEnabled(true);
			lookupProject.setValue("");
			lookupProject.setEnabled(true);
			tagProject.setEnabled(true);
			tagProject.setTag("");
			lookupPhase.setEnabled(false);
			lookupPhase.setValue("");
			lblPhase.setEnabled(false);

			displayCheckList();

			KEYBOARD_MANAGER.focusNextComponent();

			lookupProject.setLookupSQL(sql_project(co_id));
			lookupCheckNo.setLookupSQL(sql_checkno());
			lookupBank.setLookupSQL(sql_bank());
			lookupClientName.setLookupSQL(sql_client(proj_id, phase));
			refreshCheckInfo();
		}

		else {
			btnSave.doClick();
		}

		enableFields(true);
		btnRefresh.setEnabled(true);
		btnCancel.setEnabled(true);

		lookupCompany.setValue(co_id);
	}

	// saving and updating
	private boolean checkTag() {

		boolean go = false;

		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
			Boolean isTrue = false;
			if (modelSample2Main.getValueAt(x, 9) instanceof String) {
				isTrue = (Boolean) modelSample2Main.getValueAt(x, 9);
			}
			if (modelSample2Main.getValueAt(x, 9) instanceof Boolean) {
				isTrue = (Boolean) modelSample2Main.getValueAt(x, 9);
			}

			if (isTrue) {
				go = true;
				break;
			}
		}
		return go;

	}

	private boolean checkBlankNewStatus() {

		boolean go = true;

		String new_status = "";

		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {

			Boolean isTrue = false;
			if (modelSample2Main.getValueAt(x, 9) instanceof String) {
				isTrue = (Boolean) modelSample2Main.getValueAt(x, 9);
			}
			if (modelSample2Main.getValueAt(x, 9) instanceof Boolean) {
				isTrue = (Boolean) modelSample2Main.getValueAt(x, 9);
			}

			if (isTrue) {
				new_status = modelSample2Main.getValueAt(x, 11).toString().trim();
				if (new_status.equals("") || new_status == "" || new_status == null || new_status.equals(null)) {
					JOptionPane.showMessageDialog(getContentPane(), "New Check Status is not indicated", "Information",
							JOptionPane.WARNING_MESSAGE);
					go = false;
					break;
				}
			} else {
			}
		}
		return go;

	}

	private boolean checkBounceReason() {
		boolean go = true;

		String new_status = "";
		String bnc_rsn = "";

		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {

			Boolean isTrue = false;
			if (modelSample2Main.getValueAt(x, 9) instanceof String) {
				isTrue = (Boolean) modelSample2Main.getValueAt(x, 9);
			}

			if (modelSample2Main.getValueAt(x, 9) instanceof Boolean) {
				isTrue = (Boolean) modelSample2Main.getValueAt(x, 9);
			}

			if (isTrue) {
				new_status = modelSample2Main.getValueAt(x, 11).toString().trim();
				bnc_rsn = modelSample2Main.getValueAt(x, 12).toString().trim();
				if (new_status.equals("Bounced") && bnc_rsn.equals("") || new_status == "Bounced" && bnc_rsn == "") {
					JOptionPane.showMessageDialog(getContentPane(), "Please indicate Bounce Reason", "Information",
							JOptionPane.WARNING_MESSAGE);
					go = false;
					break;
				}
			} else {

			}
		}

		return go;
	}

	public void setSQL() {
		lookupProject.setLookupSQL(SQL_PROJECT(lookupCompany.getText()));
		lookupCheckNo.setLookupSQL(sql_checkno());
		lookupClientName.setLookupSQL(sql_client(lookupProject.getText(), lookupPhase.getText()));
		lookupBank.setLookupSQL(sql_bank());
	}

	public String sql_client(String projcode, String phase) {
		String SQL = "select\n"
				+ "distinct on (a.pbl_id, a.seq_no) a.entity_id, trim(b.entity_name)as client_name, a.pbl_id, a.seq_no, a.entity_id \n"
				+ "from rf_sold_unit a \n" + "left join rf_entity b on a.entity_id = b.entity_id \n"
				+ "left join mf_unit_info e on a.pbl_id = e.pbl_id  \n " + "where a.entity_id is not null \n";

		if (projcode.equals("") || projcode.equals(null)) {
		} else {
			SQL = SQL + "and a.projcode = '" + projcode + "' " + "\n";
		}

		if (phase.trim().equals("") || phase.trim().equals(null)) {
		} else {
			SQL = SQL + "and trim(e.phase) = '" + phase + "' " + "\n";
		}

		return SQL;
	}

	public String sql_checkno() {

		String co_id = lookupCompany.getText();
		String projcode = lookupProject.getText();
		String phase = lookupPhase.getText();
		String entity = lookupClientName.getText();

		String SQL =

				"select\n" + "trim(a.check_no)as check_no, a.acct_no ,b.entity_name, a.pbl_id, a.seq_no, a.co_id \n"
						+ "from "
						+ "(select check_no, acct_no, pbl_id, seq_no, proj_id, entity_id, co_id from rf_payments where check_no is not null) a \n"
						+ "left join (select distinct on (a.pbl_id, a.seq_no) a.entity_id, b.entity_name, a.pbl_id, a.seq_no, a.entity_id  \n"
						+ "	from rf_sold_unit a \n"
						+ "	left join rf_entity b on a.entity_id = b.entity_id) b on a.pbl_id=b.pbl_id and a.seq_no=b.seq_no \n"
						+ "left join mf_unit_info e on a.pbl_id = e.pbl_id  \n " + "where a.check_no is not null \n";

		if (co_id.equals("") || co_id.equals(null)) {
		} else {
			SQL = SQL + "and a.co_id = '" + co_id + "' " + "\n";
		}

		if (projcode.equals("") || projcode.equals(null)) {
		} else {
			SQL = SQL + "and a.proj_id = '" + projcode + "' " + "\n";
		}

		if (phase.trim().equals("") || phase.trim().equals(null)) {
		} else {
			SQL = SQL + "and trim(e.phase) = '" + phase + "' " + "\n";
		}

		if (entity.trim().equals("") || entity.trim().equals(null) || entity.trim() == null) {
		} else {
			SQL = SQL + "and trim(a.entity_id) = '" + entity + "' " + "\n";
		}

		return SQL;
	}

	public String sql_bank() {

		String co_id = lookupCompany.getText();
		String projcode = lookupProject.getText();
		String phase = lookupPhase.getText();
		String entity = lookupClientName.getText();

		String SQL = "select\n" + "distinct on (a.bank_id)  b.bank_alias, b.bank_name, a.bank_id \n"
				+ "from rf_payments a \n" + "left join mf_bank b on a.bank_id = b.bank_id \n"
				+ "left join mf_unit_info e on a.pbl_id = e.pbl_id  \n " +

				"where a.check_no is not null \n";

		if (co_id.equals("") || co_id.equals(null)) {
		} else {
			SQL = SQL + "and a.co_id = '" + co_id + "' " + "\n";
		}

		if (projcode.equals("") || projcode.equals(null)) {
		} else {
			SQL = SQL + "and a.proj_id = '" + projcode + "' " + "\n";
		}

		if (phase.trim().equals("") || phase.trim().equals(null)) {
		} else {
			SQL = SQL + "and trim(e.phase) = '" + phase + "' " + "\n";
		}

		if (entity.trim().equals("") || entity.trim().equals(null)) {
		} else {
			SQL = SQL + "and trim(a.entity_id) = '" + entity + "' " + "\n";
		}

		return SQL;
	}

	public static String sql_project(String co_id) {
		String SQL = "select proj_id as \"ID\", trim(proj_name) as \"Project Name\", trim(proj_alias) as \"Alias\"\n"
				+ "from mf_project where trim(co_id) = '" + co_id + "' and status_id='A' order by proj_id\n";
		return SQL;
	}

	public String getNewStatus(int row, String old_status) {

		String sql = "";

		if (old_status.equals("Deposited") || old_status == "Deposited") {
			sql = "select checkstat_desc, checkstat_id from mf_check_status "
					+ "where checkstat_id in ('01', '03', '07') and checkstat_desc != '" + old_status + "' ";
		} else if (old_status.equals("Bounced") || old_status == "Bounced") {
			sql = "select checkstat_desc, checkstat_id from mf_check_status "
					+ "where checkstat_id in ('04', '05', '07', '09', '14')  and checkstat_desc != '" + old_status
					+ "' ";
		} else if (old_status.equals("Post Dated") || old_status == "Post Dated") {
			sql = "select checkstat_desc, checkstat_id from mf_check_status "
					+ "where checkstat_id in ('05', '07', '09', '10', '11', '08', '06', '13', '14')  and checkstat_desc != '"
					+ old_status + "' ";
		} else if (old_status.equals("Hold") || old_status == "Hold") {
			sql = "select checkstat_desc, checkstat_id from mf_check_status "
					+ "where checkstat_id in ('05', '12', '09', '14') and checkstat_desc != '" + old_status + "'";
		} else if (old_status.equals("Withdrawn") || old_status == "Withdrawn") {
			sql = "select checkstat_desc, checkstat_id from mf_check_status "
					+ "where checkstat_id in ('05', '11')  and checkstat_desc != '" + old_status + "'";
		} else if (old_status.equals("For Withdrawal") || old_status == "For Withdrawal") {
			sql = "select checkstat_desc, checkstat_id from mf_check_status "
					+ "where checkstat_id in ('10', '06', '04', '11', '14')  and checkstat_desc != '" + old_status
					+ "'";
		} else {
			sql = "select checkstat_desc, checkstat_id from mf_check_status where checkstat_desc != '" + old_status
					+ "'";
		}

		return sql;

	}

	public String getReason() {

		String sql = "select reason_desc, reason_id from mf_check_bounce_reason";
		return sql;

	}

	private boolean confirmLeaveScreen() {

		boolean go = false;

		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {

			Boolean isTrue = false;
			if (modelSample2Main.getValueAt(x, 9) instanceof String) {
				isTrue = (Boolean) modelSample2Main.getValueAt(x, 9);
			}
			if (modelSample2Main.getValueAt(x, 9) instanceof Boolean) {
				isTrue = (Boolean) modelSample2Main.getValueAt(x, 9);
			}

			if (isTrue) {
				{
					if (JOptionPane.showConfirmDialog(getContentPane(), "Post tagged new status(es)?", "Save",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
						go = false;
						break;
					}

					else {
						go = true;
						break;
					}

				}
			} else {
			}
		}
		return go;

	}

	private void adjustRowHeight() {

		int rw = tblSample2Main.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			tblSample2Main.setRowHeight(x, 22);
			x++;
		}
	}

	public static void totalEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal total_check_amt = new BigDecimal(0.00);
		Integer row_count = modelMain.getRowCount();

		FncTables.clearTable(modelTotal); 
		for (int x = 0; x < modelMain.getRowCount(); x++) {
			try {
				total_check_amt = total_check_amt.add(((BigDecimal) modelMain.getValueAt(x, 5))); 
			} catch (NullPointerException e) {
				total_check_amt = total_check_amt.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(new Object[] { "Total", row_count, null, null, null, total_check_amt, null });
	}

	private void displayAddUnits() {
		int column = tblSample2Main.getSelectedColumn();
		int row = tblSample2Main.getSelectedRow();

		String old_status = modelSample2Main.getValueAt(row, 10).toString().trim();

		if (column == 11 && modelSample2Main.isEditable()) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "New Status", getNewStatus(row, old_status),
					false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelSample2Main.setValueAt(data[0], row, column);
			}
		}
		
		if (column == 12 && modelSample2Main.isEditable() && modelSample2Main.getValueAt(row, 11).toString().equals("Bounced")) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Reason of Bounce", getReason(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelSample2Main.setValueAt(data[0], row, column);
			}
		}
	}

	private static Object[] getMonthYear() {// used

		DateFormat df = new SimpleDateFormat("yyyy-dd-MM");
		String month = df.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		String month_sub = month.substring(8);

		String month_word[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
		String year = df2.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		Integer year_sub = Integer.parseInt(year.substring(6)) - 2000;
		Integer year_full = Integer.parseInt(year.substring(6));

		Object x[] = { month_sub, year_sub, year_full, month_word[Integer.parseInt(month_sub) - 1], year.substring(6) };

		return x;

	}

	public void insertBuyerStatus_OR(pgUpdate db, String entity_id, String pbl_id, String seq_no, String proj_id) {

		String sqlDetail = "insert into rf_buyer_status values (" + "'" + entity_id + "',  \n" + // 1 entity_id
						"'" + proj_id + "',  \n" + // 2 proj_id
						"'" + pbl_id + "',  \n" + // 3 pbl_id
						"" + seq_no + ",  \n" + // 4 seq_no
						"'01',  \n" + // 5 byrstatus_id
						"now(),  \n" + // 6 tran_date
						"now(),  \n" + // 7 actual_date
						"null,  \n" + // 8 request_no
						"null,  \n" + // 9 inactive_date
						"'A',  \n" + // 10 status_id
						"null,  \n" + // 11 trans_no
						"null,  \n" + // 12 jv_no
						"null,  \n" + // 13 to_mi_mo_projcode
						"null,  \n" + // 14 to_mi_mo_pbl_id
						"null,  \n" + // 15 branch_id
						"'" + UserInfo.EmployeeCode + "'  \n" + // 16 created_by

						")   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

		String sqlDetail2 = "update rf_sold_unit set currentstatus = '01' " + "where pbl_id = '" + pbl_id + "' "
				+ "and seq_no = " + seq_no + " " + "and status_id = 'A' ";

		System.out.printf("SQL #2 update rf_sold_unit: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}
	
	private void save() {
		if (checkBlankNewStatus() && checkBounceReason() && checkTag()) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				
				modelCheckStatus model = modelSample2Main; 
				
				String bounce_rsn = "";
				String remarks = "";
				String bounce_rsn_id = "";
				String new_status_id = ""; 
				String prev_checkstat_id = "";
				String check_no = "";
				String pbl_id = "";
				String seq_no = "";
				Integer jv_srl_no_counter = 0;

				for (int x=0; x<modelSample2Main.getRowCount(); x++) {
					
					if ((Boolean) modelSample2Main.getValueAt(x, 9)) {
						//Commented by lester because of wrong index for new check status
						//new_status_id = FncGlobal.GetString("select checkstat_id from mf_check_status where trim(checkstat_desc) = '"+model.getValueAt(x, 10).toString()+"'; ");
						new_status_id = FncGlobal.GetString("select checkstat_id from mf_check_status where trim(checkstat_desc) = '"+model.getValueAt(x, 11).toString()+"'; ");
						try {
							bounce_rsn_id = FncGlobal.GetString("select reason_id from mf_check_bounce_reason where trim(reason_desc) = '"+model.getValueAt(x, 12).toString().trim()+"'; ");
						} catch (NullPointerException e) {
							bounce_rsn_id = ""; 
						}
						
						try {
							prev_checkstat_id = FncGlobal.GetString("select checkstat_id from mf_check_status where trim(checkstat_desc) = '"+model.getValueAt(x, 10).toString()+"'; ");
						} catch (NullPointerException e) {
							prev_checkstat_id = "";
						}

						try {
							check_no = model.getValueAt(x, 7).toString().trim();
						} catch (NullPointerException e) {
							check_no = "";
						}

						try {
							pbl_id = model.getValueAt(x, 2).toString().trim();
						} catch (NullPointerException e) {
							pbl_id = "";
						}

						try {
							seq_no = model.getValueAt(x, 3).toString();
						} catch (NullPointerException e) {
							seq_no = "";
						}

						try {
							remarks = model.getValueAt(x, 14).toString().replace("'", "''").trim();
						} catch (NullPointerException e) {
							remarks = "";
						}
						
						Integer strPayID = FncGlobal.GetInteger("select pay_rec_id::int \n" +
								"from rf_payments \n" + 
								"where check_no = '"+check_no+"' and pbl_id = '"+pbl_id+"' and seq_no::int = "+seq_no+"::int; "); 
						
						csm.saveCheckStatus(new_status_id, bounce_rsn_id, pbl_id, seq_no, check_no, remarks);
						csm.insertCheckStatus(new_status_id, bounce_rsn_id, pbl_id, seq_no, check_no, strPayID, prev_checkstat_id, "");
						csm.removeCheckDeposit(check_no, strPayID);
						
						if (FncGlobal.GetBoolean("select exists(select * from rf_client_ledger where pay_rec_id::int = '"+strPayID+"'::int and status_id = 'A'); ")) {
							csm.removeLedger(pbl_id, seq_no, check_no);
						}
						
						if (new_status_id.equals("03")) {
							csm.create_jv_bounced(strPayID.toString());
						} else if (new_status_id.equals("10")) {
							csm.create_jv_withdrawn(strPayID.toString());
						} else if (new_status_id.equals("06")) {
							
						} else if (new_status_id.equals("11")) {
							csm.create_jv_returned(strPayID.toString());
						} else {
							jv_no = "";
						}
					}
				}
				
				displayCheckList();
			}
		} else {
			JOptionPane.showMessageDialog(getContentPane(), "Check all input", "Information", JOptionPane.WARNING_MESSAGE);			
		}
	}
	

	public void displayCheckList() {
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {

				FncTables.clearTable(modelSample2Main);
				DefaultListModel listModel = new DefaultListModel();
				rowHeaderSample2Main.setModel(listModel);

				String strSQL = "select * \n" + 
						"from view_deposit_check_list_2 a \n" + 
						"where (a.co_id = '"+lookupCompany.getValue()+"' or '"+lookupCompany.getValue()+"' = '') \n" + 
						"and (a.proj_id = '"+lookupProject.getValue()+"' or '"+lookupProject.getValue()+"' = '') \n" + 
						"and (a.phase = '"+lookupPhase.getValue()+"' or '"+lookupPhase.getValue()+"' = '') \n" + 
						"and (a.entity_id = '"+((lookupClientName.getValue()==null)?"":lookupClientName.getValue())+"' or '"+((lookupClientName.getValue()==null)?"":lookupClientName.getValue())+"' = '') \n" + 
						"and (a.check_no = '"+((lookupCheckNo.getValue()==null)?"":lookupCheckNo.getValue())+"' or '"+((lookupCheckNo.getValue()==null)?"":lookupCheckNo.getValue())+"' = '') \n" + 
						"and (a.bank_alias = '"+((lookupBank.getValue()==null)?"":lookupBank.getValue())+"' or '"+((lookupBank.getValue()==null)?"":lookupBank.getValue())+"' = ''); ";
						//"and a.check_date::DATE BETWEEN '"+dte+"'"
				System.out.println();
				System.out.println("strSQL: "+strSQL);
				
				pgSelect db = new pgSelect();
				db.select(strSQL);

				if (db.isNotNull()) {
					for (int x = 0; x < db.getRowCount(); x++) {
						modelSample2Main.addRow(db.getResult()[x]);
						listModel.addElement(modelSample2Main.getRowCount());
					}

					totalEntries(modelSample2Main, modelSample2Total);
					btnSave.setEnabled(false);
					btnPreview.setEnabled(true);
				} else {
					modelSample2Total = new modelCheckStatus_total();
					modelSample2Total.addRow(new Object[] { "Total", null, null, null, null, 0.00, null });
					tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
					scrollSample2Total.setViewportView(tblSample2Total);
					((_JTableTotal) tblSample2Total).setTotalLabel(0);
					tblSample2Total.setFont(dialog11Bold);
					btnSave.setEnabled(false);
				}

				return null;
			}
		};
		sw.execute();
	}

}