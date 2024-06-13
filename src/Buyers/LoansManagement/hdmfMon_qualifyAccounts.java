package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import interfaces._GUI;
import org.jdesktop.swingx.JXPanel;
import tablemodel.modelPagibigStatusMonitoring_DeficientAccounts;
import tablemodel.modelPagibigStatusMonitoring_QualifiedAccounts;
import tablemodel.model_hdmf_FirstFiling;
import tablemodel.model_hdmf_email;
import Accounting.Disbursements.RequestForPayment;
import Buyers.ClientServicing.CARD;
import Buyers.ClientServicing.ClientInformation;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Dialogs.EmailBuilder;
import Dialogs.FirstFilingReports;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTabbedPane;
import components._JTableMain;
import Buyers.LoansManagement.PagibigStatusMonitoring_v2;

public class hdmfMon_qualifyAccounts extends JXPanel implements _GUI {

	private static final long serialVersionUID = -2898555528616063616L;
	private static PagibigStatusMonitoring_v2 hdmfMainMod;
	static Dimension size = new Dimension(738, 500);

	/*** Table Declarations ***/
	private JXPanel panQuaAccounts;
	private _JTableMain tblQualAccounts;
	private JScrollPane scrQualAccounts;
	private JList rowQualAccounts;
	private modelPagibigStatusMonitoring_QualifiedAccounts modelQualAccounts;

	private JXPanel panDefAccounts;
	private _JTableMain tblDefAccounts;
	private JScrollPane scrDefAccounts;
	private JList rowDefAccounts;
	private modelPagibigStatusMonitoring_DeficientAccounts modelDefAccounts;

	private JXPanel panFirstFiling;
	public static _JTableMain tblFirstFiling;
	private JScrollPane scrFirstFiling;
	public static JList rowFirstFiling;
	private static model_hdmf_FirstFiling modelFirstFiling;

	private JXPanel panEmail;
	public static _JTableMain tblEmail;
	private JScrollPane scrEmail;
	public static JList rowEmail;
	private static model_hdmf_email modelEmail;

	private JXPanel panEmailReply;
	public static _JTableMain tblEmailReply;
	private JScrollPane scrEmailReply;
	public static JList rowEmailReply;
	private static model_hdmf_email modelEmailReply;
	/*** Table Declarations ***/

	private static JComboBox cmbStage;
	private static _JDateChooser dteTo;
	private _JTabbedPane tabQuaAcct;
	private Boolean blnTag = false;

	private String strBatch = "";
	String strCircular = "";
	private JTextField txtSearch;

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private static _JLookup txtBatch;

	private static JDialog dialogMail;

	@SuppressWarnings("static-access")
	public hdmfMon_qualifyAccounts(PagibigStatusMonitoring_v2 psm) {
		this.hdmfMainMod = psm;
		initGUI();
	}

	public hdmfMon_qualifyAccounts(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public hdmfMon_qualifyAccounts(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public hdmfMon_qualifyAccounts(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			this.add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				{
					JXPanel panPage = new JXPanel(new GridLayout(1, 3, 5, 5));
					panMain.add(panPage, BorderLayout.PAGE_START);
					panPage.setPreferredSize(new Dimension(0, 60));
					{
						{
							JXPanel panBatch = new JXPanel(new BorderLayout(5, 5));
							panPage.add(panBatch);
							panBatch.setBorder(JTBorderFactory.createTitleBorder("Batch",
									FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								txtBatch = new _JLookup("");
								panBatch.add(txtBatch, BorderLayout.CENTER);
								txtBatch.setReturnColumn(0);
								txtBatch.setEnabled(false);
								txtBatch.setHorizontalAlignment(JTextField.CENTER);
								txtBatch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();

										if (data != null) {
											if (cmbStage.getSelectedIndex() == 3) {
												_PagibigStatusMonitoring.DisplayFirstFilingWithBatch(modelFirstFiling,
														rowFirstFiling, txtBatch.getValue());
												hdmfMainMod.CtrlLock_0(2);
												hdmfMainMod.intQA = 2;
											} else if (cmbStage.getSelectedIndex() == 4) {
												_PagibigStatusMonitoring.DisplayForFirstFilingWithBatch(
														modelFirstFiling, rowFirstFiling, txtBatch.getValue());
												hdmfMainMod.CtrlLock_0(2);
												hdmfMainMod.intQA = 2;
											} else if (cmbStage.getSelectedIndex() == 8) {
												_PagibigStatusMonitoring.DisplayEmailSent(modelEmail, rowEmail,
														hdmfMainMod.txtCoID.getText(), hdmfMainMod.txtProID.getText(),
														hdmfMainMod.txtPhaseID.getText(), dteTo.getDate().toString(),
														((txtBatch.getValue() == null) ? "" : txtBatch.getValue()));
												hdmfMainMod.CtrlLock_0(6);
												hdmfMainMod.intQA = 6;
											} else if (cmbStage.getSelectedIndex() == 9) {
												_PagibigStatusMonitoring.DisplayEmailReply(modelEmailReply,
														rowEmailReply, hdmfMainMod.txtCoID.getText(),
														hdmfMainMod.txtProID.getText(),
														hdmfMainMod.txtPhaseID.getText(), dteTo.getDate().toString(),
														((txtBatch.getValue() == null) ? "" : txtBatch.getValue()));
												hdmfMainMod.CtrlLock_0(6);
												hdmfMainMod.intQA = 6;
											}

											dteTo.setDate((Date) data[1]);
										}
									}
								});
								txtBatch.addKeyListener(new KeyListener() {

									public void keyTyped(KeyEvent e) {

									}

									public void keyReleased(KeyEvent e) {

									}

									public void keyPressed(KeyEvent e) {
										if (e.getKeyCode() == 8) {
											txtBatch.setValue("");
										}
									}
								});
							}
						}
						{
							JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5));
							panPage.add(panDate);
							panDate.setBorder(JTBorderFactory.createTitleBorder("Date Details",
									FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panDate.add(dteTo, BorderLayout.CENTER);
								dteTo.getCalendarButton().setVisible(true);
								dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteTo.setEnabled(false);
							}
						}
						{
							JXPanel panStage = new JXPanel(new BorderLayout(5, 5));
							panPage.add(panStage);
							panStage.setBorder(JTBorderFactory.createTitleBorder("Select Stage",
									FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								cmbStage = new JComboBox(new String[] { "", "FOR SCD RELEASE", "FOR DOCS COMPLETION",
										"QUALIFIED FOR FIRST FILING", "FOR FIRST FILING", "FOR HDMF INSPECTION",
										"FOR TCT ANNOTATION", "FOR POST FILING", "EMAIL SENT", "EMAIL REPLY" });

								panStage.add(cmbStage, BorderLayout.CENTER);
								cmbStage.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent arg0) {
										txtBatch.setValue("");
										txtBatch.setEnabled(false);
										int selected_item = cmbStage.getSelectedIndex();

										modelQualAccounts.clear();
										rowQualAccounts.setModel(new DefaultListModel());
										scrQualAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER,
												FncTables.getRowHeader_Footer(""));

										modelDefAccounts.clear();
										rowDefAccounts.setModel(new DefaultListModel());
										scrDefAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER,
												FncTables.getRowHeader_Footer(""));

										if (selected_item == 2 || selected_item == 3 || selected_item == 6) {
											tabQuaAcct.add("Deficient Accounts", panDefAccounts);
										} else {
											tabQuaAcct.remove(panDefAccounts);
										}

										try {
											tabQuaAcct.remove(panQuaAccounts);
										} catch (NullPointerException ex) {
											System.out.println("");
											System.out.println("panQuaAccounts is not yet added.");
										}

										if (selected_item == 2 || selected_item == 6) {
											tabQuaAcct.add("Qualified Accounts", panQuaAccounts);
											tabQuaAcct.add("Deficient Accounts", panDefAccounts);
											tabQuaAcct.remove(panFirstFiling);
										} else if (selected_item == 3 || selected_item == 4) {
											tabQuaAcct.remove(panQuaAccounts);
											tabQuaAcct.remove(panDefAccounts);
											tabQuaAcct.add("Qualified Accounts", panFirstFiling);
										} else {
											tabQuaAcct.add("Qualified Accounts", panQuaAccounts);
											tabQuaAcct.remove(panDefAccounts);
											tabQuaAcct.remove(panFirstFiling);
										}

										if (selected_item == 8) {
											tabQuaAcct.removeAll();
											tabQuaAcct.add("Qualified Accounts", panEmail);
										}

										if (selected_item == 9) {
											tabQuaAcct.removeAll();
											tabQuaAcct.add("Qualified Accounts", panEmailReply);
										}

										if (selected_item == 3 || selected_item == 4 || selected_item == 8
												|| selected_item == 9) {
											txtBatch.setEnabled(true);
											txtSearch.setEnabled(true);
										} else {
											txtSearch.setEnabled(false);
										}

										if (selected_item == 3) {
											txtBatch.setLookupSQL("select distinct batch_no, actual_date::date \n"
													+ "from rf_hdmf_status_other_values \n"
													+ "where byrstatus_id = 'QF' \n" + "order by batch_no desc");
										} else if (selected_item == 4) {
											txtBatch.setLookupSQL("select distinct trans_no, actual_date::date\n"
													+ "from rf_buyer_status \n"
													+ "where byrstatus_id = '31' and status_id = 'A'\n"
													+ "order by actual_date::date desc");
										} else if (selected_item == 8) {
											txtBatch.setLookupSQL("select distinct batch_no, actual_date::date\n"
													+ "from rf_hdmf_status_other_values\n"
													+ "where remarks ~* 'EMAIL SENT'");
										} else if (selected_item == 9) {
											txtBatch.setLookupSQL("select distinct batch_no, actual_date::date\n"
													+ "from rf_hdmf_status_other_values\n"
													+ "where remarks ~* 'EMAIL REPLY'");
										}
									}
								});
							}
						}
					}
				}
				{
					JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
					panMain.add(panCenter, BorderLayout.CENTER);
					{
						{
							CreateQualAcct();
							CreateDefAcct();
							CreateFirstFilingGrid();
							CreateEmailGridSent();
							CreateEmailGridReply();
						}
						{
							tabQuaAcct = new _JTabbedPane();
							panCenter.add(tabQuaAcct, BorderLayout.CENTER);
							{
								tabQuaAcct.add("Qualified Accounts", panQuaAccounts);
							}
						}
						{
							JXPanel panSearch = new JXPanel(new BorderLayout(5, 5));
							panCenter.add(panSearch, BorderLayout.PAGE_END);
							panSearch.setPreferredSize(new Dimension(0, 30));
							{
								JLabel lblSearch = new JLabel("Search Name");
								panSearch.add(lblSearch, BorderLayout.LINE_START);
								lblSearch.setHorizontalAlignment(JLabel.LEADING);
								lblSearch.setPreferredSize(new Dimension(125, 0));
							}
							{
								txtSearch = new JTextField("");
								panSearch.add(txtSearch, BorderLayout.CENTER);
								txtSearch.setHorizontalAlignment(JTextField.CENTER);
								txtSearch.setEditable(true);
								txtSearch.getDocument().addDocumentListener(new DocumentListener() {
									public void removeUpdate(DocumentEvent e) {
										focusonthis();
									}

									public void insertUpdate(DocumentEvent e) {
										focusonthis();
									}

									public void changedUpdate(DocumentEvent e) {
										focusonthis();
									}

									void focusonthis() {
										if (txtSearch.getText().equals("")) {
											tblFirstFiling.changeSelection(0, 0, false, false);
										} else {
											for (int x = 0; x < tblFirstFiling.getRowCount(); x++) {
												if (tblFirstFiling.getValueAt(x, 0).toString().toLowerCase()
														.contains(txtSearch.getText())
														|| tblFirstFiling.getValueAt(x, 0).toString()
																.substring(0, txtSearch.getText().length())
																.equals(txtSearch.getText())) {
													tblFirstFiling.changeSelection(x, 0, false, false);
													x = tblFirstFiling.getRowCount();
												}
											}
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

	private void CreateQualAcct() {
		panQuaAccounts = new JXPanel(new GridLayout(1, 1, 0, 0));
		panQuaAccounts.setOpaque(isOpaque());
		{
			scrQualAccounts = new JScrollPane();
			panQuaAccounts.add(scrQualAccounts, BorderLayout.CENTER);
			{
				modelQualAccounts = new modelPagibigStatusMonitoring_QualifiedAccounts();
				tblQualAccounts = new _JTableMain(modelQualAccounts);
				scrQualAccounts.setViewportView(tblQualAccounts);
				scrQualAccounts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				tblQualAccounts.hideColumns("Proj. Name", "Entity ID", "Proj. ID", "PBL ID", "Seq");
//				tblQualAccounts.getTableHeader().setEnabled(false);
				tblQualAccounts.addMouseListener(new MouseAdapter() {

					public void mouseReleased(MouseEvent e) {
						if (e.isPopupTrigger()) {
							try {
								initializeMenu(e).show((_JTableMain) e.getSource(), e.getX(), e.getY());
							} catch (NullPointerException e1) {
							}
						}
					}

					public void mousePressed(MouseEvent e) {
						if (e.isPopupTrigger()) {
							try {
								initializeMenu(e).show((_JTableMain) e.getSource(), e.getX(), e.getY());
							} catch (NullPointerException e1) {
							}
						}
					}

					public JPopupMenu initializeMenu(MouseEvent e) {
						int selected_row = tblQualAccounts.convertRowIndexToModel(tblQualAccounts.getSelectedRow());

						final String entity_id = (String) modelQualAccounts.getValueAt(selected_row, 3);
						final String entity_name = (String) modelQualAccounts.getValueAt(selected_row, 4);
						final String proj_id = (String) modelQualAccounts.getValueAt(selected_row, 5);
						final String pbl_id = (String) modelQualAccounts.getValueAt(selected_row, 6);
						final Integer seq_no = (Integer) modelQualAccounts.getValueAt(selected_row, 7);

						JPopupMenu menu = new JPopupMenu();
						JMenuItem menuViewCARD = new JMenuItem("View CARD");
						menu.add(menuViewCARD);
						menuViewCARD.setFont(menuViewCARD.getFont().deriveFont(10f));
						menuViewCARD.addActionListener(new ActionListener() {
							@SuppressWarnings("static-access")
							public void actionPerformed(ActionEvent arg0) {
								if (FncGlobal.homeMDI.isNotExisting("CARD")) {
									CARD c = new CARD(entity_id, proj_id, pbl_id, seq_no);
									Home_JSystem.addWindow(c, null);
								} else {
									// JOptionPane.showMessageDialog(PagibigStatusMonitoring_v2.this.getTopLevelAncestor(),
									// "CARD is already open", "View CARD", JOptionPane.WARNING_MESSAGE);
								}
							}
						});

						JMenuItem menuViewClientInfo = new JMenuItem("View Client Info");
						menu.add(menuViewClientInfo);
						menuViewClientInfo.setFont(menuViewClientInfo.getFont().deriveFont(10f));
						menuViewClientInfo.addActionListener(new ActionListener() {

							@SuppressWarnings("static-access")
							@Override
							public void actionPerformed(ActionEvent e) {

								if (FncGlobal.homeMDI.isNotExisting("ClientInformation")) {
									ClientInformation ci = new ClientInformation("Client Information", entity_id,
											entity_name);
									Home_JSystem.addWindow(ci, null);
								} else {

								}
							}
						});
						return menu;
					}
				});
			}
			{
				rowQualAccounts = tblQualAccounts.getRowHeader();
				rowQualAccounts.setModel(new DefaultListModel());
				scrQualAccounts.setRowHeaderView(rowQualAccounts);
				scrQualAccounts.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}

	private void CreateDefAcct() {
		panDefAccounts = new JXPanel(new GridLayout(1, 1, 0, 0));
		panDefAccounts.setOpaque(isOpaque());
		{
			scrDefAccounts = new JScrollPane();
			panDefAccounts.add(scrDefAccounts, BorderLayout.CENTER);
			{
				modelDefAccounts = new modelPagibigStatusMonitoring_DeficientAccounts();
				tblDefAccounts = new _JTableMain(modelDefAccounts);
				scrDefAccounts.setViewportView(tblDefAccounts);
				scrDefAccounts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				tblDefAccounts.hideColumns("Proj. Name", "Entity ID", "Proj. ID", "PBL ID", "Seq No", "Contact No",
						"SCD In", "SCD Out");
//				tblDefAccounts.getTableHeader().setEnabled(false);
			}
			{
				rowDefAccounts = tblDefAccounts.getRowHeader();
				rowDefAccounts.setModel(new DefaultListModel());
				scrDefAccounts.setRowHeaderView(rowDefAccounts);
				scrDefAccounts.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}

	private void CreateFirstFilingGrid() {
		panFirstFiling = new JXPanel(new GridLayout(1, 1, 0, 0));
		panFirstFiling.setOpaque(isOpaque());
		{
			scrFirstFiling = new JScrollPane();
			panFirstFiling.add(scrFirstFiling, BorderLayout.CENTER);
			scrFirstFiling.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelFirstFiling = new model_hdmf_FirstFiling();
				modelFirstFiling.setEditable(false);

				tblFirstFiling = new _JTableMain(modelFirstFiling);
				tblFirstFiling.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblFirstFiling.getSelectedRow());
						}
					}
				});

				rowFirstFiling = tblFirstFiling.getRowHeader();
				scrFirstFiling.setViewportView(tblFirstFiling);

				tblFirstFiling.getColumnModel().getColumn(0).setPreferredWidth(255);
				tblFirstFiling.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblFirstFiling.getColumnModel().getColumn(2).setPreferredWidth(200);
				tblFirstFiling.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblFirstFiling.getColumnModel().getColumn(4).setPreferredWidth(50);
				tblFirstFiling.getColumnModel().getColumn(5).setPreferredWidth(50);
				tblFirstFiling.getColumnModel().getColumn(6).setPreferredWidth(100);
				tblFirstFiling.getColumnModel().getColumn(9).setPreferredWidth(100);
				tblFirstFiling.getColumnModel().getColumn(11).setPreferredWidth(900);
				tblFirstFiling.getColumnModel().getColumn(12).setPreferredWidth(100);
				tblFirstFiling.getColumnModel().getColumn(13).setPreferredWidth(100);
				tblFirstFiling.getColumnModel().getColumn(14).setPreferredWidth(100);

				tblFirstFiling.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf));
				tblFirstFiling.getColumnModel().getColumn(12).setCellRenderer(new DateRenderer(sdf));
				tblFirstFiling.getColumnModel().getColumn(14).setCellRenderer(new DateRenderer(sdf));

				rowFirstFiling = tblFirstFiling.getRowHeader();
				rowFirstFiling.setModel(new DefaultListModel());
				scrFirstFiling.setRowHeaderView(rowFirstFiling);
				scrFirstFiling.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

				tblFirstFiling.hideColumns("Last Complied");
//				tblFirstFiling.getTableHeader().setEnabled(false);
			}
		}
	}

	public Boolean Generate() {
		txtBatch.setValue("");
		Boolean blnRet = false;

		System.out.println("");
		System.out.println("Index: " + cmbStage.getSelectedIndex());

		if (cmbStage.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Please select stage first.");
		} else {
			new Thread(new Runnable() {
				public void run() {
					FncGlobal.startProgress("Generating Accounts");
					hdmfMainMod.CtrlLock_1(4);
					if (cmbStage.getSelectedIndex() == 2) {
						blnTag = false;
						if (tabQuaAcct.getSelectedIndex() == 0) {
							_PagibigStatusMonitoring.displayPQA_Docs_Completion(hdmfMainMod.txtCoID.getValue(),
									hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), dteTo.getDate(),
									modelQualAccounts, rowQualAccounts);
							tblQualAccounts.packAll();
						}
						if (tabQuaAcct.getSelectedIndex() == 1) {
							_PagibigStatusMonitoring.displayPQA_Deficient_Accounts(hdmfMainMod.txtProID.getValue(),
									hdmfMainMod.txtPhaseID.getValue(), dteTo.getDate(), modelDefAccounts,
									rowDefAccounts);
							tblDefAccounts.packAll();
						}

						hdmfMainMod.CtrlLock_0(2);
						hdmfMainMod.intQA = 2;
					} else if (cmbStage.getSelectedIndex() == 3) {
						blnTag = false;
						_PagibigStatusMonitoring.DisplayFirstFiling(modelFirstFiling, rowFirstFiling,
								hdmfMainMod.txtCoID.getText(), hdmfMainMod.txtProID.getText(),
								hdmfMainMod.txtPhaseID.getText(), dteTo.getDate().toString(), blnTag);
						hdmfMainMod.CtrlLock_0(2);
						hdmfMainMod.intQA = 2;
					} else if (cmbStage.getSelectedIndex() == 4) {
						blnTag = false;
						_PagibigStatusMonitoring.DisplayForFirstFiling(modelFirstFiling, rowFirstFiling,
								hdmfMainMod.txtCoID.getText(), hdmfMainMod.txtProID.getText(),
								hdmfMainMod.txtPhaseID.getText(), dteTo.getDate().toString(), blnTag);
						hdmfMainMod.CtrlLock_0(2);
						hdmfMainMod.intQA = 2;
					} else if (cmbStage.getSelectedIndex() == 8) {
						_PagibigStatusMonitoring.DisplayEmailSent(modelEmail, rowEmail, hdmfMainMod.txtCoID.getText(),
								hdmfMainMod.txtProID.getText(), hdmfMainMod.txtPhaseID.getText(),
								dteTo.getDate().toString(), ((txtBatch.getValue() == null) ? "" : txtBatch.getValue()));
						hdmfMainMod.CtrlLock_0(6);
						hdmfMainMod.intQA = 6;
					} else if (cmbStage.getSelectedIndex() == 9) {
						_PagibigStatusMonitoring.DisplayEmailReply(modelEmailReply, rowEmailReply,
								hdmfMainMod.txtCoID.getText(), hdmfMainMod.txtProID.getText(),
								hdmfMainMod.txtPhaseID.getText(), dteTo.getDate().toString(),
								((txtBatch.getValue() == null) ? "" : txtBatch.getValue()));
						hdmfMainMod.CtrlLock_0(6);
						hdmfMainMod.intQA = 6;
					}

					FncGlobal.stopProgress();
				}

			}).start();
		}

		return blnRet;
	}

	public void Post() {
		FncGlobal.startProgress("Posting");

		if (cmbStage.getSelectedIndex() == 2) {

			_PagibigStatusMonitoring.saveHDMFStatus(cmbStage.getSelectedIndex(), dteTo.getDate(), modelQualAccounts);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfuly posted", "Post",
					JOptionPane.INFORMATION_MESSAGE);
			modelQualAccounts.clear();
			scrQualAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowQualAccounts.setModel(new DefaultListModel());

		} else if (cmbStage.getSelectedIndex() == 3) {
			/* Qualified for First Filing */
			blnTag = true;
			txtBatch.setValue(_PagibigStatusMonitoring.saveFFStatus(dteTo.getDate(), modelFirstFiling));
			strBatch = _PagibigStatusMonitoring.CreateNotice(modelFirstFiling, txtBatch.getValue());
			System.out.println("Btch:" + strBatch);
			JOptionPane.showMessageDialog(null, "Accounts succesfuly posted", "Post", JOptionPane.INFORMATION_MESSAGE);
			hdmfMainMod.CtrlLock_0(3);
			hdmfMainMod.intQA = 1;
			_PagibigStatusMonitoring.DeleteRows(modelFirstFiling, 1);
//			Preview();
			GenerateFirstFilingNotice(strBatch);
			GenerateFirstFilingNoticeTransmittal(strBatch);
			GenerateFirstFilingNoticePhilPostTransmittal(strBatch);
			GenerateFirstFilingTransmittal();
		} else if (cmbStage.getSelectedIndex() == 4) {
//			String[] circular = {"310/349", "312", "379", "403/349"};
			String[] circular = { "396/349", "312", "379", "403/349" };

			final JComboBox<String> combo = new JComboBox<>(circular);

			String[] options = { "OK", "CANCEL" };

			String title = "Select Circular";
			int selection = JOptionPane.showOptionDialog(null, combo, title, JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			if (selection > 0) {
				System.out.println("selection is: " + options[selection]);
			}

			strCircular = combo.getSelectedItem().toString();
			if (strCircular == "") {
				System.out.println("Circular: " + strCircular);
			}

			/* For First Filing */
			blnTag = true;
			_PagibigStatusMonitoring.saveFFFStatus(dteTo.getDate(), modelFirstFiling,txtBatch);
			// _PagibigStatusMonitoring.DisplayForFirstFiling(modelFirstFiling,
			// rowFirstFiling, hdmfMainMod.txtCoID.getText(),
			// hdmfMainMod.txtProID.getText(), hdmfMainMod.txtPhaseID.getText(),
			// dteTo.getDate().toString(), blnTag);
			JOptionPane.showMessageDialog(null, "Accounts succesfuly posted", "Post", JOptionPane.INFORMATION_MESSAGE);
			hdmfMainMod.CtrlLock_0(3);
			hdmfMainMod.intQA = 1;
			saveNotice_Emp_Verification(modelFirstFiling);
			_PagibigStatusMonitoring.DeleteRows(modelFirstFiling, 1);
			Preview();
		} else if (cmbStage.getSelectedIndex() == 8) {
			Boolean blnProceed = true;

			try {
				for (int x = 0; x < modelEmail.getRowCount(); x++) {
					if ((Boolean) modelEmail.getValueAt(x, 0)) {
						if (modelEmail.getValueAt(x, 5).toString().equals("")
								|| modelEmail.getValueAt(x, 12).toString().equals("")
								|| modelEmail.getValueAt(x, 13).toString().equals("")) {
							blnProceed = false;
						}
					}
				}
			} catch (NullPointerException ex) {
				ex.printStackTrace();
				blnProceed = false;
			}

			if (blnProceed) {
				txtBatch.setValue(_PagibigStatusMonitoring.email_sent(modelEmail));
				mailing();

				_PagibigStatusMonitoring.DisplayEmailSent(modelEmail, rowEmail, hdmfMainMod.txtCoID.getText(),
						hdmfMainMod.txtProID.getText(), hdmfMainMod.txtPhaseID.getText(), dteTo.getDate().toString(),
						((txtBatch.getValue() == null) ? "" : txtBatch.getValue()));
				hdmfMainMod.CtrlLock_0(1);
				hdmfMainMod.intQA = 1;
			} else {
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Incomplete values. Please check.", "Caution",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (cmbStage.getSelectedIndex() == 9) {
			txtBatch.setValue(_PagibigStatusMonitoring.email_reply(modelEmailReply));
			_PagibigStatusMonitoring.DisplayEmailReply(modelEmailReply, rowEmailReply, hdmfMainMod.txtCoID.getText(),
					hdmfMainMod.txtProID.getText(), hdmfMainMod.txtPhaseID.getText(), dteTo.getDate().toString(),
					((txtBatch.getValue() == null) ? "" : txtBatch.getValue()));
			hdmfMainMod.CtrlLock_0(1);
			hdmfMainMod.intQA = 1;
		}

		FncGlobal.stopProgress();
	}

	public void Preview() {
		if (cmbStage.getSelectedIndex() == 2) {

			ArrayList<TD_PST_PQA_DocsCompletion> list_PQA_Docs_Completion = new ArrayList<TD_PST_PQA_DocsCompletion>();

			for (int x = 0; x < modelQualAccounts.getRowCount(); x++) {
				Boolean isSelected = (Boolean) modelQualAccounts.getValueAt(x, 0);

				if (isSelected) {

					String proj_name = (String) modelQualAccounts.getValueAt(x, 1);
					String unit_desc = (String) modelQualAccounts.getValueAt(x, 2);
					String entity_name = (String) modelQualAccounts.getValueAt(x, 4);
					Date or_date = (Date) modelQualAccounts.getValueAt(x, 8);
					String house_perc = (String) modelQualAccounts.getValueAt(x, 9);
					Date scd_in = (Date) modelQualAccounts.getValueAt(x, 10);
					String business_class = (String) modelQualAccounts.getValueAt(x, 11);
					Integer dp_term = (Integer) modelQualAccounts.getValueAt(x, 12);
					@SuppressWarnings("unused")
					String pmt_stage = (String) modelQualAccounts.getValueAt(x, 13);
					String dp_pct = (String) modelQualAccounts.getValueAt(x, 14);
					String pmt_status = (String) modelQualAccounts.getValueAt(x, 15);
					Date cec_date = (Date) modelQualAccounts.getValueAt(x, 16);
					Date cte_date = (Date) modelQualAccounts.getValueAt(x, 17);
					Date itr_date = (Date) modelQualAccounts.getValueAt(x, 18);
					Date jc_date = (Date) modelQualAccounts.getValueAt(x, 19);
					Date afs_date = (Date) modelQualAccounts.getValueAt(x, 20);
					Date payslip = (Date) modelQualAccounts.getValueAt(x, 21);
					Date msvs_date = (Date) modelQualAccounts.getValueAt(x, 22);
					Date esav_date = (Date) modelQualAccounts.getValueAt(x, 23);
					Date pagibig_or_date = (Date) modelQualAccounts.getValueAt(x, 24);
					Date bsl_date = (Date) modelQualAccounts.getValueAt(x, 25);
					Date hdmf_or_p24 = (Date) modelQualAccounts.getValueAt(x, 26);
					Date ci_form = (Date) modelQualAccounts.getValueAt(x, 27);
					Date verified_docs = (Date) modelQualAccounts.getValueAt(x, 28);
				

					// list_PQA_Docs_Completion.add(new TD_PST_PQA_DocsCompletion(proj_name,
					// unit_desc, entity_name, or_date, cec_date, cte_date, itr_date, msvs_date,
					// esav_date, pagibig_or_date, bsl_date, hdmf_or_p24, scd_in, business_class,
					// dp_term, pmt_stage, pmt_status));
					// list_PQA_Docs_Completion.add(new TD_PST_PQA_DocsCompletion(proj_name,
					// unit_desc, entity_name, or_date, scd_in, business_class, dp_term, pmt_stage,
					// pmt_status, cec_date, cte_date, itr_date, jc_date, afs_date, msvs_date,
					// esav_date, pagibig_or_date, bsl_date, hdmf_or_p24));
					list_PQA_Docs_Completion.add(new TD_PST_PQA_DocsCompletion(proj_name, unit_desc, entity_name,
							or_date, house_perc, scd_in, business_class, dp_term, dp_pct, pmt_status, cec_date,
							cte_date, itr_date, jc_date, afs_date, payslip, esav_date, pagibig_or_date,
							bsl_date, hdmf_or_p24, ci_form, verified_docs));
				}
			}

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("company_name", hdmfMainMod.txtCo.getText());
			mapParameters.put("proj_id", hdmfMainMod.txtProID.getValue());
			mapParameters.put("phase", hdmfMainMod.txtPhaseID.getValue());
			mapParameters.put("date_cut_off", dteTo.getDate());
			mapParameters.put("proj_name", hdmfMainMod.txtPro.getText());

			mapParameters.put("list_PQA_Docs_Completion", list_PQA_Docs_Completion);

			FncReport.generateReport("/Reports/rptPagibigStatusMonitoring_PQA_DocsCompletion.jasper",
					"For Doc Completion Accounts", mapParameters);
		}

		if (cmbStage.getSelectedIndex() == 3) {
			String batch_no = FncGlobal.GetString(
					"select batch_no from rf_client_notices where module_batch_no = '" + txtBatch.getValue() + "' ");
//			System.out.println("btch:" + batch_no);
			if (txtBatch.getValue().equals("QF21082301")) {
				GenerateFirstFilingNotice("0000002234");
				GenerateFirstFilingNoticeTransmittal("0000002234");
				GenerateFirstFilingNoticePhilPostTransmittal("0000002234");
				GenerateFirstFilingTransmittal();
			} else {
//				GenerateFirstFilingNotice(strBatch);
//				GenerateFirstFilingNoticeTransmittal(strBatch);
//				GenerateFirstFilingNoticePhilPostTransmittal(strBatch);
				GenerateFirstFilingNotice(batch_no);
				GenerateFirstFilingNoticeTransmittal(batch_no);
				GenerateFirstFilingNoticePhilPostTransmittal(batch_no);
				GenerateFirstFilingTransmittal();
			}

//

		} else if (cmbStage.getSelectedIndex() == 4) {
			JDialog dialog = new JDialog(FncGlobal.homeMDI, "Printing", false);
			dialog.setLayout(new BorderLayout(0, 0));

			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Dimension screenSize = toolkit.getScreenSize();

			dialog.setSize(260, 150);
			dialog.setResizable(false);

			FirstFilingReports reports = new FirstFilingReports();
			reports.setCompanyID(hdmfMainMod.txtCoID.getValue());
			reports.setCompany(hdmfMainMod.txtCo.getText());
			reports.setProjectID(hdmfMainMod.txtProID.getValue());
			reports.setProject(hdmfMainMod.txtPro.getText());
			reports.setPhase(hdmfMainMod.txtPhaseID.getValue());
			reports.setTag(blnTag);
			reports.setDate(dteTo.getDate());
			reports.setModel(modelFirstFiling);
			reports.setBatch(txtBatch.getValue());

			dialog.add(reports, BorderLayout.CENTER);

			final int x = ((screenSize.width - dialog.getWidth()) / 2);
			final int y = ((screenSize.height - dialog.getHeight()) / 2);

			dialog.setBounds(x, y, dialog.getWidth(), dialog.getHeight());
			dialog.setVisible(true);
		}
	}

	private void GenerateFirstFilingTransmittal() {
		String company_logo = RequestForPayment.sql_getCompanyLogo();

		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdfTo.format(dteTo.getDate());

		String strTmp = "";

		pgUpdate del = new pgUpdate();
		strTmp = "delete from tmp_hdmf where emp_code = '" + UserInfo.EmployeeCode + "'";
		del.executeUpdate(strTmp, false);
		del.commit();

		System.out.println("");
		System.out.println(strTmp);

		for (int x = 0; x < modelFirstFiling.getRowCount(); x++) {
			pgUpdate ins = new pgUpdate();
			strTmp = "insert into tmp_hdmf (client_name, emp_code) values ('" + modelFirstFiling.getValueAt(x, 0)
					+ "', '" + UserInfo.EmployeeCode + "')";
			ins.executeUpdate(strTmp, false);
			ins.commit();

			System.out.println("");
			System.out.println(strTmp);
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
		mapParameters.put("02_AsOfDate", strDate);
		mapParameters.put("03_CoID", hdmfMainMod.txtCoID.getText());
		mapParameters.put("04_ProID", hdmfMainMod.txtProID.getText());
		mapParameters.put("05_Phase", hdmfMainMod.txtPhaseID.getText());
		mapParameters.put("06_Project", hdmfMainMod.txtPro.getText());
		mapParameters.put("07_User", GetName(UserInfo.EmployeeCode));
		mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("09_Tagged", blnTag);
		mapParameters.put("10_empcode", UserInfo.EmployeeCode);
		FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingTransmittal.jasper",
				"Qualified for First Filing Transmittal", "", mapParameters);
	}

	public void Export() {
		String strTmp = "";
		String strTitle = "";
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strTo = (String) sdfTo.format(dteTo.getDate());

		pgUpdate del = new pgUpdate();
		strTmp = "delete from tmp_hdmf where emp_code = '" + UserInfo.EmployeeCode + "'";
		del.executeUpdate(strTmp, false);
		del.commit();

		if (blnTag) {
			strTitle = "Accounts for Filing at HDMF";

			pgUpdate ins = new pgUpdate();
			strTmp = "INSERT INTO tmp_hdmf (client_name, emp_code) \n" + "SELECT c_name, '" + UserInfo.EmployeeCode
					+ "' \n" + "FROM view_hdmf_first_filing('" + hdmfMainMod.txtCoID.getText() + "', '"
					+ hdmfMainMod.txtProID.getText() + "', \n" + "'" + hdmfMainMod.txtPhaseID.getText() + "', '" + strTo
					+ "', " + blnTag + ") \n" + "ORDER BY c_name";
			ins.executeUpdate(strTmp, true);
			ins.commit();

//			String[] strHead = {
//					"Pag-IBIG FUND",
//					"CIRCULAR 310/349", 
//					"WINDOW II", 
//					"", 
//					"DEVELOPER'S NAME: " + hdmfMainMod.txtCo.getText().toString().trim(), 
//					"PROJECT NAME: " + hdmfMainMod.txtPro.getText().toString().trim(), 
//					"DATE: " +  strTo
//			};
			String[] strHead = { "Pag-IBIG FUND", "CIRCULAR 396/349", "WINDOW II", "",
					"DEVELOPER'S NAME: " + hdmfMainMod.txtCo.getText().toString().trim(),
					"PROJECT NAME: " + hdmfMainMod.txtPro.getText().toString().trim(), "DATE: " + strTo };

			String[] strCol = { "Last Name", "First Name", "Middle Name", "EXT", "Maiden Name", "Project", "Phase",
					"Block", "Lot", "Loanable Amount", "TCT No.", "w/o CI", "w/o BVS", "w/o CAR" };

			String strSQL = "select last_name, first_name, middle_name, ext, maiden_name, proj_alias, phase, block, lot, loanable_amount, '', 'X', 'X', 'X' \n"
					+ "from view_hdmf_transmittal('" + hdmfMainMod.txtCoID.getText() + "', '"
					+ hdmfMainMod.txtProID.getText() + "', '" + hdmfMainMod.txtPhaseID.getText() + "') a \n"
					+ "where trim(a.last_name) || ', ' || trim(a.first_name) || ' ' || trim(a.middle_name) in \n"
					+ "(select x.client_name from tmp_hdmf x where x.emp_code = '" + UserInfo.EmployeeCode + "') \n"
					+ "order by a.last_name, a.first_name, a.middle_name";

			FncGlobal.CreateXLSwithHeaders(strCol, strSQL, "MSVS Status", 7, strHead);
		} else {
			strTitle = "Accounts Qualified for Filing at HDMF";

			String col_names[] = { "Name", "Project", "Phase", "Block", "Lot", "Class", "DP Stage", "DP Term",
					"Pay Status", "House Percentage", "Findings", "NTP", "Docs Complete" };
			String strSQL = "SELECT c_name, c_proj_name, c_phase, c_block, c_lot, c_class, c_dpstage, \n"
					+ "c_dpterm, c_paystatus, c_housepctg, c_findings, c_ntpdate, c_docscomplete \n"
					+ "FROM view_hdmf_first_filing('" + hdmfMainMod.txtCoID.getText() + "', '"
					+ hdmfMainMod.txtProID.getText() + "', '" + hdmfMainMod.txtPhaseID.getText() + "', '"
					+ dteTo.getDate().toString() + "', " + blnTag + ");";

			FncGlobal.startProgress("Creating spreadsheet.");
			FncGlobal.CreateXLS(col_names, strSQL, strTitle);
			FncGlobal.stopProgress();
		}
	}

	public static void GenerateFirstFilingNotice(String strBat) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBat);
		mapParameters.put("company", hdmfMainMod.txtCo.getText());
		mapParameters.put("company_address", "");
		mapParameters.put("notice_type_id", "139");
		mapParameters.put("prepared_by",
				FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
						+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
						+ UserInfo.EmployeeCode + "'"));
		mapParameters.put("prepared_by_code", UserInfo.EmployeeCode);
		System.out.println("batch_no:" + strBat);
		System.out.println("prepared_by:" + FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
				+ UserInfo.EmployeeCode + "'"));
		
		
//		FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingFirstNotice.jasper",
		FncReport.generateReport("/Reports/rpt_NoticeonFillingatHDMF.jasper","Notice on Filing", "", mapParameters);
	}

	public static void GenerateFirstFilingNoticeTransmittal(String strBat) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
		mapParameters.put("02_BatchNo", strBat);
		mapParameters.put("03_UserName",

				FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
						+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
						+ UserInfo.EmployeeCode + "'")

		);
		System.out.println("usernm:" + FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
				+ UserInfo.EmployeeCode + "'"));
		mapParameters.put("04_UserCode", FncGlobal
				.GetString("select login_id from rf_system_user where emp_code = '" + UserInfo.EmployeeCode + "'"));
		mapParameters.put("05_NoticeType", "First Notice - Filing at HDMF");
//		FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticeTransmittal.jasper",
//				"Qualified for First Filing Notice Transmittal", "", mapParameters);
		FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticeTransmittal_v2.jasper",
				"Qualified for First Filing Notice Transmittal", "", mapParameters);
	}

	public static void GenerateFirstFilingNoticePhilPostTransmittal(String strBat) {
		pgSelect dbExec = new pgSelect();
		String strSQL = "select distinct region::varchar(155) from view_notice_transmittal('" + strBat + "')";
		dbExec.select(strSQL);

		System.out.println("");
		System.out.println("I went here!");

		if (dbExec.getRowCount() > 1) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				System.out.println("");
				System.out.println("dbExec.getResult()[x]: " + dbExec.getResult()[x][0]);

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
				mapParameters.put("02_BatchNo", strBat);
				mapParameters.put("03_UserName", GetName(UserInfo.EmployeeCode));
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", "Notice on Filing at HDMF");
				mapParameters.put("06_region", dbExec.getResult()[x][0]);

				System.out.println("co_i:" + hdmfMainMod.txtCo.getText());
				System.out.println("co_i:" + hdmfMainMod.txtCo.getText());
				System.out.println("02_BatchNo:" + strBat);
				System.out.println("03_UserName:" + GetName(UserInfo.EmployeeCode));
				System.out.println("05_NoticeType:" + "Notice on Filing at HDMF");
				System.out.println("02_Bat06_regionchNo:" + dbExec.getResult()[x][0]);

//				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal.jasper",
//						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[x][0], "",
//						mapParameters);
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal_v2.jasper",
						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[x][0], "",
						mapParameters);
			}
		} else {

			try {
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
				mapParameters.put("02_BatchNo", strBat);
				mapParameters.put("03_UserName",
						FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
								+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
								+ UserInfo.EmployeeCode + "'")

				);
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", "First Notice - Filing at HDMF");
				mapParameters.put("06_region", dbExec.getResult()[0][0]);
//				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal.jasper",
//						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[0][0], "",
//						mapParameters);
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal_v2.jasper",
						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[0][0], "",
						mapParameters);

			} catch (NullPointerException e) {

			}

		}
	}

	private void CreateEmailGridSent() {
		panEmail = new JXPanel(new GridLayout(1, 1, 0, 0));
		panEmail.setOpaque(isOpaque());
		{
			scrEmail = new JScrollPane();
			panEmail.add(scrEmail, BorderLayout.CENTER);
			scrEmail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelEmail = new model_hdmf_email();
				modelEmail.setEditable(false);

				tblEmail = new _JTableMain(modelEmail);
				tblEmail.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblEmail.getSelectedRow());
						}
					}
				});

				rowEmail = tblEmail.getRowHeader();
				scrEmail.setViewportView(tblEmail);

				tblEmail.getColumnModel().getColumn(0).setPreferredWidth(0);
				tblEmail.getColumnModel().getColumn(1).setPreferredWidth(120);
				tblEmail.getColumnModel().getColumn(2).setPreferredWidth(237);
				tblEmail.getColumnModel().getColumn(3).setPreferredWidth(105);
				tblEmail.getColumnModel().getColumn(4).setPreferredWidth(105);
				tblEmail.getColumnModel().getColumn(5).setPreferredWidth(105);
				tblEmail.getColumnModel().getColumn(10).setPreferredWidth(250);
				tblEmail.getColumnModel().getColumn(11).setPreferredWidth(250);
				tblEmail.getColumnModel().getColumn(12).setPreferredWidth(105);
				tblEmail.getColumnModel().getColumn(13).setPreferredWidth(250);

				tblEmail.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
				tblEmail.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
				tblEmail.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
				tblEmail.getColumnModel().getColumn(8).setCellRenderer(new DateRenderer(sdf));

				rowEmail = tblEmail.getRowHeader();
				rowEmail.setModel(new DefaultListModel());
				scrEmail.setRowHeaderView(rowEmail);
				scrEmail.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

				tblEmail.hideColumns("Last Complied");
//				tblEmail.getTableHeader().setEnabled(false);
			}
		}
	}

	private void CreateEmailGridReply() {
		panEmailReply = new JXPanel(new GridLayout(1, 1, 0, 0));
		panEmailReply.setOpaque(isOpaque());
		{
			scrEmailReply = new JScrollPane();
			panEmailReply.add(scrEmailReply, BorderLayout.CENTER);
			scrEmailReply.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelEmailReply = new model_hdmf_email();
				modelEmailReply.setEditable(false);

				tblEmailReply = new _JTableMain(modelEmailReply);
				tblEmailReply.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblEmailReply.getSelectedRow());
						}
					}
				});

				rowEmailReply = tblEmailReply.getRowHeader();
				scrEmailReply.setViewportView(tblEmailReply);

				tblEmailReply.getColumnModel().getColumn(0).setPreferredWidth(0);
				tblEmailReply.getColumnModel().getColumn(1).setPreferredWidth(120);
				tblEmailReply.getColumnModel().getColumn(2).setPreferredWidth(237);
				tblEmailReply.getColumnModel().getColumn(3).setPreferredWidth(105);
				tblEmailReply.getColumnModel().getColumn(4).setPreferredWidth(105);
				tblEmailReply.getColumnModel().getColumn(5).setPreferredWidth(105);
				tblEmailReply.getColumnModel().getColumn(10).setPreferredWidth(250);
				tblEmailReply.getColumnModel().getColumn(11).setPreferredWidth(250);
				tblEmailReply.getColumnModel().getColumn(12).setPreferredWidth(105);
				tblEmailReply.getColumnModel().getColumn(13).setPreferredWidth(250);
				tblEmailReply.getColumnModel().getColumn(14).setPreferredWidth(105);

				tblEmailReply.hideColumns("COE Date", "entity_id", "proj_id", "pbl_id", "seq_no", "email", "employer",
						"date hired", "position");
				tblEmailReply.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
				tblEmailReply.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
//				tblEmailReply.getTableHeader().setEnabled(false);

				rowEmailReply = tblEmailReply.getRowHeader();
				rowEmailReply.setModel(new DefaultListModel());
				scrEmailReply.setRowHeaderView(rowEmailReply);
				scrEmailReply.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}

	private void mailing() {
		String[] strName;

		Integer intSelected = 0;

		for (int x = 0; x < modelEmail.getRowCount(); x++) {
			if ((Boolean) modelEmail.getValueAt(x, 0)) {
				intSelected++;
			}
		}

		if (intSelected > 0) {
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Dimension screenSize = toolkit.getScreenSize();

			dialogMail = new JDialog(FncGlobal.homeMDI, "Mailing List", true);
			dialogMail.setSize(screenSize.width - 100, screenSize.height - 100);
			dialogMail.setResizable(false);

			EmailBuilder email = new EmailBuilder();
			dialogMail.add(email, BorderLayout.CENTER);

			strName = new String[intSelected];
			email.hashClear();

			int intSelectedCounter = 0;

			for (int x = 0; x < modelEmail.getRowCount(); x++) {
				if ((Boolean) modelEmail.getValueAt(x, 0)) {
					strName[intSelectedCounter] = modelEmail.getValueAt(x, 2).toString();
					email.mapRecipientID(modelEmail.getValueAt(x, 2).toString(),
							modelEmail.getValueAt(x, 6).toString());
					email.mapRecipient(modelEmail.getValueAt(x, 2).toString(), modelEmail.getValueAt(x, 10).toString());
					email.mapEmployer(modelEmail.getValueAt(x, 2).toString(), modelEmail.getValueAt(x, 11).toString());
					email.mapEmploymentDate(modelEmail.getValueAt(x, 2).toString(),
							(Date) modelEmail.getValueAt(x, 12));
					email.mapPosition(modelEmail.getValueAt(x, 2).toString(), modelEmail.getValueAt(x, 13).toString());
					email.mapCOE(modelEmail.getValueAt(x, 2).toString(), (Date) modelEmail.getValueAt(x, 5));

					intSelectedCounter++;
				}
			}

			email.addList(strName);

			final int x = (screenSize.width - dialogMail.getWidth()) / 2;
			final int y = (screenSize.height - dialogMail.getHeight()) / 2;

			dialogMail.setLocation(x, y);
			dialogMail.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No rows were selected.");
		}
	}

	public static void dispose() {
		dialogMail.dispose();
	}

	public static String GetName(String emp_code) {
		String entityid = "";

		String SQL = "SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '" + emp_code + "'";

		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			entityid = (String) sqlExec.getResult()[0][0];
		} else {
			entityid = "";
		}

		return entityid;
	}
	public static String saveNotice_Emp_Verification(DefaultTableModel model){
		String batch_no = "";

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();


		for(int x=0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 1);

			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 19);
				String proj_id = (String) model.getValueAt(x, 21);
				String pbl_id = (String) model.getValueAt(x, 20);
				Integer seq_no = (Integer) model.getValueAt(x, 22);

				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		System.out.println("SELECT sp_tag_notice_filing_hdmf(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')");
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_notice_emp_verification(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		System.out.println("batch_no00:"+ batch_no);
		return batch_no;
	}
}