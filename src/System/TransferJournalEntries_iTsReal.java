/**
 * 
 */
package System;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledSeparator;

import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncDate;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup.ResetEvent;
import Lookup.ResetListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import Renderer.NumberRenderer;
import Renderer.TextRenderer;

import components._JInternalFrame;
import components._JTableMain;
import components._JTableTotal;

/**
 * @author PC-111l
 * 
 */
public class TransferJournalEntries_iTsReal extends _JInternalFrame implements
		ActionListener {

	private static final long serialVersionUID = 1L;

	static String TITLE = "Transfer Journal Entries (Postbooks)";
	static Dimension FRAME_SIZE = new Dimension(800, 600);
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Cursor HAND_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	/********** Preferences **********/
	public static String DESTINATION_NAME = "Local Server";
	public static String DESTINATION_HOST = "177.177.179.112";
	public static String DESTINATION_PORT = "5432";
	public static String DESTINATION_DATABASE = "_verdant";
	public static String DESTINATION_USERNAME = "admin";
	public static String DESTINATION_PASSWORD = "admin";
	public static String DESTINATION_URL = "jdbc:postgresql://"
			+ DESTINATION_HOST + ":" + DESTINATION_PORT + "/"
			+ DESTINATION_DATABASE + "?user=" + DESTINATION_USERNAME
			+ "&password=" + DESTINATION_USERNAME;
	/********** Preferences **********/

	private JXPanel pnlMain;

	private JXPanel pnlNorth;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField tagCompany;

	private JLabel lblProject;
	private _JLookup lookupProject;
	private JTextField tagProject;

	private JLabel lblPhase;
	private _JLookup lookupPhase;
	private JTextField tagPhase;
	private JTextField tagPhaseAlias;

	private JXTitledSeparator separatorGenerateBy;
	private JLabel lblBooks;
	private JComboBox cmbBooks;
	private JLabel lblDateFrom;
	private _JDateChooser dateFrom;
	private JLabel lblDateTo;
	private _JDateChooser dateTo;

	private JXPanel pnlCenter;

	private JScrollPane scrollMainCenter;
	private _JTableMain _tblMain;
	private tablemodelTransferJournalEntries _modelMain;
	private JList rowHeader;

	private JScrollPane scrollMainSouth;
	private _JTableTotal _tblTotal;
	private tablemodelTransferJournalEntries _modelTotal;

	private JXPanel pnlSouth;
	private JButton btnDisplay;
	private JButton btnTransfer;

	private ArrayList<String> projectName = new ArrayList<String>();
	private KeyboardFocusManager manager = KeyboardFocusManager
			.getCurrentKeyboardFocusManager();

	/**
	 * 
	 */
	public TransferJournalEntries_iTsReal() {
		super(TITLE, true, true, true, true);
		initGUI();
	}

	private void initGUI() {
		this.setTitle(TITLE);
		this.setSize(FRAME_SIZE);
		this.setPreferredSize(new Dimension(FRAME_SIZE));
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JXPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				pnlNorth.setBorder(LINE_BORDER);
				pnlNorth.setPreferredSize(new Dimension(788, 223));
				{
					lblCompany = new JLabel("Company");
					pnlNorth.add(lblCompany);
					lblCompany.setBounds(8, 8, 90, 25);
				}
				{
					lookupCompany = new _JLookup("", "Company");
					pnlNorth.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(FncGlobal.getCompany());
					lookupCompany.setBounds(98, 8, 50, 25);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								tagCompany.setText((String) data[1]);
								manager.focusNextComponent();
							}
						}
					});
				}
				{
					tagCompany = new JTextField();
					pnlNorth.add(tagCompany);
					tagCompany.setEditable(false);
					tagCompany.setBounds(151, 8, 426, 25);
				}
				{
					lblProject = new JLabel("Project");
					pnlNorth.add(lblProject);
					lblProject.setBounds(8, 39, 90, 25);
				}
				{
					lookupProject = new _JLookup("", "Project");
					pnlNorth.add(lookupProject);
					lookupProject.setBounds(98, 39, 50, 25);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								tagProject.setText((String) data[1]);
								manager.focusNextComponent();
							}
						}
					});

					lookupProject.addFocusListener(new FocusListener() {
						public void focusLost(FocusEvent e) {
						}

						public void focusGained(FocusEvent e) {
							_JLookup lookup = (_JLookup) e.getSource();
							lookup.setReturnColumn(0);
							lookup.setLookupSQL(FncGlobal.getProject(lookupCompany.getValue()));
						}
					});
				}
				{
					tagProject = new JTextField();
					pnlNorth.add(tagProject);
					tagProject.setEditable(false);
					tagProject.setBounds(151, 39, 426, 25);
				}
				{
					lblPhase = new JLabel("Phase");
					pnlNorth.add(lblPhase);
					lblPhase.setBounds(8, 70, 90, 25);
				}
				{
					lookupPhase = new _JLookup("", "Phase");
					pnlNorth.add(lookupPhase);
					lookupPhase.setBounds(98, 70, 50, 25);
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								tagPhase.setText((String) data[1]);
								tagPhaseAlias.setText((String) data[2]);
								manager.focusNextComponent();

								DESTINATION_DATABASE = "_verdant_" + tagPhaseAlias.getText().toLowerCase();
								DESTINATION_URL = "jdbc:postgresql://" + DESTINATION_HOST + ":" + DESTINATION_PORT + "/" + DESTINATION_DATABASE + "?user=" + DESTINATION_USERNAME + "&password=" + DESTINATION_USERNAME;
								System.out.println("DESTINATION_URL: " + DESTINATION_URL);
							}
						}
					});

					lookupPhase.addFocusListener(new FocusListener() {
						public void focusLost(FocusEvent e) {
						}

						public void focusGained(FocusEvent e) {
							_JLookup lookup = (_JLookup) e.getSource();
							lookup.setReturnColumn(0);
							lookup.setLookupSQL(FncGlobal.getPhase(lookupProject.getValue()));
						}
					});

					lookupPhase.addResetListener(new ResetListener() {
						public void resetPerformed(ResetEvent event) {
							_JLookup lookup = (_JLookup) event.getSource();
							lookup.setValue(null);
							lookup.setText(null);
							tagPhase.setText(null);
							tagPhaseAlias.setText(null);

							DESTINATION_DATABASE = "_verdant";
							DESTINATION_URL = "jdbc:postgresql://" + DESTINATION_HOST + ":" + DESTINATION_PORT + "/" + DESTINATION_DATABASE + "?user=" + DESTINATION_USERNAME + "&password=" + DESTINATION_USERNAME;
							System.out.println("DESTINATION_URL: " + DESTINATION_URL);
						}
					});
				}
				{
					tagPhase = new JTextField();
					pnlNorth.add(tagPhase);
					tagPhase.setEditable(false);
					tagPhase.setBounds(151, 70, 150, 25);
				}
				{
					tagPhaseAlias = new JTextField();
					pnlNorth.add(tagPhaseAlias);
					tagPhaseAlias.setEditable(false);
					tagPhaseAlias.setHorizontalAlignment(SwingConstants.CENTER);
					tagPhaseAlias.setBounds(305, 70, 60, 25);
				}
				{
					separatorGenerateBy = new JXTitledSeparator("Generate By");
					pnlNorth.add(separatorGenerateBy);
					separatorGenerateBy.setBounds(8, 100, 569, 15);
				}
				{
					lblBooks = new JLabel("Books");
					pnlNorth.add(lblBooks);
					lblBooks.setHorizontalAlignment(SwingConstants.RIGHT);
					lblBooks.setBounds(13, 121, 80, 25);
				}
				{
					cmbBooks = new JComboBox(new DefaultComboBoxModel(new String[] { "All Books", "Receipt Book", "Cash Voucher", "Journal Voucher" }));
					pnlNorth.add(cmbBooks);
					// cmbBooks.setSelectedIndex(1);
					cmbBooks.setBounds(98, 121, 172, 25);
				}
				{
					lblDateFrom = new JLabel("Date Form");
					pnlNorth.add(lblDateFrom);
					lblDateFrom.setHorizontalAlignment(SwingConstants.RIGHT);
					lblDateFrom.setBounds(13, 152, 80, 25);
				}
				{
					dateFrom = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlNorth.add(dateFrom);
					dateFrom.setDate(FncDate.getFirstDateOfMonth(Calendar.getInstance().getTime()));
					dateFrom.setBounds(98, 152, 104, 25);
					dateFrom.addDateListener(new DateListener() {
						public void datePerformed(DateEvent e) {
							_JDateChooser dateChooser = (_JDateChooser) e.getSource();
							FncDate.setLastDayOfMonth(dateChooser.getDate(), dateTo);
						}
					});
				}
				{
					lblDateTo = new JLabel("Date To");
					pnlNorth.add(lblDateTo);
					lblDateTo.setHorizontalAlignment(SwingConstants.RIGHT);
					lblDateTo.setBounds(13, 183, 80, 25);
				}
				{
					dateTo = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlNorth.add(dateTo);
					dateTo.setDate(FncDate.getLastDayOfMonth(dateFrom.getDate()));
					dateTo.setBounds(98, 183, 104, 25);
				}
			}
			{
				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(new BorderLayout());
				// pnlCenter.setBorder(LINE_BORDER);
				{
					scrollMainCenter = new JScrollPane();
					pnlCenter.add(scrollMainCenter, BorderLayout.CENTER);
					scrollMainCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					scrollMainCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					scrollMainCenter.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							_tblMain.clearSelection();
							_tblTotal.clearSelection();
						}
					});
					{
						_modelMain = new tablemodelTransferJournalEntries();
						_tblMain = new _JTableMain(_modelMain);
						scrollMainCenter.setViewportView(_tblMain);

						_tblMain.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
						_tblMain.setDefaultRenderer(String.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
						_tblMain.setDefaultRenderer(Date.class, DateRenderer.getDateRenderer());
						_tblMain.setDefaultRenderer(Integer.class, NumberRenderer.getIntegerRenderer(SwingConstants.CENTER));

						// _tblMain.getColumnModel().getColumn(7).setPreferredWidth(200);
						// _tblMain.getColumnModel().getColumn(8).setPreferredWidth(200);

						_tblMain.setFillsViewportHeight(false);
						_tblMain.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (_tblMain.rowAtPoint(e.getPoint()) == -1) {
									_tblTotal.clearSelection();
								} else {
									_tblMain.setCellSelectionEnabled(true);
								}
							}

							public void mouseReleased(MouseEvent e) {
								if (_tblMain.rowAtPoint(e.getPoint()) == -1) {
									_tblTotal.clearSelection();
								} else {
									_tblMain.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeader = _tblMain.getRowHeader();
						rowHeader.setBorder(BorderFactory.createEmptyBorder());
						scrollMainCenter.setRowHeaderView(rowHeader);
						scrollMainCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollMainSouth = new JScrollPane();
					pnlCenter.add(scrollMainSouth, BorderLayout.SOUTH);
					scrollMainSouth.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
					scrollMainSouth.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollMainSouth.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 17));
					scrollMainSouth.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
								public void adjustmentValueChanged(AdjustmentEvent e) {
									scrollMainCenter.getHorizontalScrollBar().setValue(e.getValue());
								}
							});
					scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer("0"));
					scrollMainSouth.setPreferredSize(new Dimension(586, 37));
					{
						_modelTotal = new tablemodelTransferJournalEntries();
						_modelTotal.addRow(new Object[] { "Total", null, 0.00, 0.00 });

						_tblTotal = new _JTableTotal(_modelTotal);
						scrollMainSouth.setViewportView(_tblTotal);

						_tblTotal.setTableHeader(null);

						_tblTotal.setRowHeight(20);
						_tblTotal.setCellSelectionEnabled(true);

						_tblTotal.setDefaultRenderer(Object.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
						_tblTotal.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
						_tblTotal.setFillsViewportHeight(false);

						_tblTotal.addHighlighter(FncTables.getHighlighterTOTAL(0));

						_tblTotal.addFocusListener(new FocusAdapter() {
							public void focusLost(FocusEvent e) {
								((_JTableTotal) e.getSource()).clearSelection();
							}
						});

						/*********************** Main Table ***********************/
						_tblMain.getColumnModel().addColumnModelListener(
								new TableColumnModelListener() {
									public void columnAdded(
											TableColumnModelEvent arg0) {
									}

									public void columnMarginChanged(ChangeEvent arg0) {
										TableColumnModel tableColumnModel = _tblMain.getColumnModel();
										TableColumnModel footerColumnModel = _tblTotal.getColumnModel();
										for (int i = 0; i < tableColumnModel.getColumnCount(); i++) {
											int w = tableColumnModel.getColumn(i).getWidth();
											footerColumnModel.getColumn(i).setMinWidth(w);
											footerColumnModel.getColumn(i).setMaxWidth(w);
											footerColumnModel.getColumn(i).setPreferredWidth(w);
										}
										_tblTotal.doLayout();
										_tblTotal.repaint();
										repaint();
									}

									public void columnMoved(
											TableColumnModelEvent arg0) {
									}

									public void columnRemoved(
											TableColumnModelEvent arg0) {
									}

									public void columnSelectionChanged(
											ListSelectionEvent arg0) {
									}
								});
					}
				}
			}
			{
				pnlSouth = new JXPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new BorderLayout());
				// pnlSouth.setBorder(LINE_BORDER);
				pnlSouth.setPreferredSize(new Dimension(588, 30));
				{
					btnDisplay = new JButton("Display");
					pnlSouth.add(btnDisplay, BorderLayout.WEST);
					btnDisplay.setActionCommand("Display");
					btnDisplay.setCursor(HAND_CURSOR);
					btnDisplay.setMnemonic(KeyEvent.VK_D);
					btnDisplay.setPreferredSize(new Dimension(120, 28));
					btnDisplay.addActionListener(this);
				}
				{
					btnTransfer = new JButton("Transfer");
					pnlSouth.add(btnTransfer, BorderLayout.EAST);
					btnTransfer.setActionCommand("Transfer");
					btnTransfer.setCursor(HAND_CURSOR);
					btnTransfer.setMnemonic(KeyEvent.VK_T);
					btnTransfer.setPreferredSize(new Dimension(120, 28));
					btnTransfer.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, cmbBooks, (JTextField) dateFrom.getDateEditor(), (JTextField) dateTo.getDateEditor(), btnDisplay, btnTransfer));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Display")) {
			projectName.clear();

			FncTables.clearTable(_modelMain);
			rowHeader.setModel(new DefaultListModel());
			scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(0)));

			String project = (tagPhaseAlias.getText().equals("") ? "All" : tagPhaseAlias.getText());

			switch (cmbBooks.getSelectedIndex()) {
			case 0: _TransferJournalEntries.displayAll(_modelMain, _modelTotal, rowHeader, projectName, lookupCompany.getValue(), dateFrom.getDate(), dateTo.getDate(), project); break;
			case 1: _TransferJournalEntries.displayRB(_modelMain, _modelTotal, rowHeader, projectName, lookupCompany.getValue(), dateFrom.getDate(), dateTo.getDate(), project); break;
			case 2: _TransferJournalEntries.displayCV(_modelMain, _modelTotal, rowHeader, projectName, lookupCompany.getValue(), dateFrom.getDate(), dateTo.getDate(), project); break;
			case 3: _TransferJournalEntries.displayJV(_modelMain, _modelTotal, rowHeader, projectName, lookupCompany.getValue(), dateFrom.getDate(), dateTo.getDate(), project); break;
			default: break;
			}

			scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(_tblMain.getRowCount())));
			_tblMain.packColumns(5, "Account ID", "Account Name", "Debit", "Credit", "Date Created", "Created by", "Doc #"/*, "References", "Remarks"*/);
			_tblMain.packColumns(20, "Debit", "Credit");
		}
		if (e.getActionCommand().equals("Transfer")) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", btnTransfer.getText(), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				_TransferJournalEntries.transferJournalEntries(getContentPane(), _modelMain, DESTINATION_URL, cmbBooks.getSelectedItem(), projectName);
				
				transferJournalEntries(_modelMain);
				/*
				 * Thread transferJournal = new Thread(new Runnable(){ public
				 * void run() {
				 * _TransferJournalEntries.transferJournalEntries(getContentPane
				 * (), _modelMain, DESTINATION_URL, cmbBooks.getSelectedItem(),
				 * projectName); } }); transferJournal.start();
				 */
			}
		}
	}
	
	private void transferJournalEntries(DefaultTableModel model) {
		/*UpdateDB db = new UpdateDB();
		
		String sqlHeader = "INSERT INTO VerdantEX.dbo.boi_crb_or_header\n" +
				"(rb_id, particulars, pymnttype, amount, posted_by, posted_date, issued_by, issued_date, for_reversal, reversal_date, remarks, reference_no, pay_rec_id, projcode, phase, co_id, doc_id, status_id)\n" +
				"VALUES\n" +
				"('', '', '', $, '', , '', , , , '', '', '', '', '', '', '', '');";
		
		
		String sqlDetail = "INSERT INTO VerdantEX.dbo.boi_crb_or_detail\n" +
				"(rb_id, pbl_id, seq_no, entity_id, line_no, acct_id, trans_amt, rb_fiscal_year, rb_month, ar_no, remarks, reference_no, projcode, phase, co_id, doc_id, status_id)\n" +
				"VALUES\n" +
				"('', '', , '', $, '', $, , '', '', '', '', '', '', '', '', '');";
		
		
		db.executeUpdate(sqlHeader, true);
		db.executeUpdate(sqlDetail, true);*/
		
		/*for(){
			
		}*/
	}
	
	

}
