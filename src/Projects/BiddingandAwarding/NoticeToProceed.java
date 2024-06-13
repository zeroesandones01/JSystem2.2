package Projects.BiddingandAwarding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.table.NumberEditorExt;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.EnglishNumberTranslator;
import Functions.FncDate;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._ButtonGroup;
import components._JCheckListItem;
import components._JCheckListRenderer;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;
import tablemodel.modelNTPProgressBilling;
import tablemodel.modelNTPWorkItems;

public class NoticeToProceed extends _JInternalFrame
implements ActionListener, MouseListener, KeyListener, AncestorListener {

	private static final long serialVersionUID = 1696231374073396297L;

	static String title = "Notice to Proceed";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;

	private _JLookup lookupNTPNo;
	private JXTextField txtContractNo;
	private _JLookup lookupNTPType;
	private _JLookup lookupCompany;
	private _JLookup lookupProject;

	private JTextField txtNTPType;
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtContractor;
	private JTextField txtContractorContact;

	private JLabel lblStatus;

	private JScrollPane scrollPhase;
	private JList listPhase;

	private _JLookup lookupContractor;

	private _JXFormattedTextField txtDownpayment;
	private JCheckBox chkWithSuretyBond;
	// private _JXFormattedTextField txtWithSuretyBond;

	private _JDateChooser dateStart;
	private _JDateChooser dateFinish;
	private _JDateChooser dateExtension;
	private JTextField txtExtNo;

	private _JDateChooser datePrepared;
	private _JDateChooser dateAwarded;
	private JCheckBox chkCOCDate;
	private _JDateChooser dateCOC;
	private JCheckBox chkCOCExpiry;
	private _JDateChooser dateCOCExpiry;

	private JTabbedPane tabCenter;

	private _JScrollPaneMain scrollWorkItemsMain;
	private _JTableMain tblWorkItemsMain;
	private modelNTPWorkItems modelWorkItemsMain;
	private JList rowHeaderWorkItemsMain;

	private _JScrollPaneTotal scrollWorkItemsTotal;
	private _JTableTotal tblWorkItemsTotal;
	private modelNTPWorkItems modelWorkItemsTotal;

	private JScrollPane scrollSubject;
	private JXTextArea txtSubject;

	private JScrollPane scrollNotes;
	private JXTextArea txtNotes;

	private _JScrollPaneMain scrollProgressBilling;
	private _JTableMain tblProgressBilling;
	private modelNTPProgressBilling modelProgressBilling;
	private JList rowHeaderProgressBilling;

	private _JScrollPaneTotal scrollProgressBillingTotal;
	private _JTableTotal tblProgressBillingTotal;
	private modelNTPProgressBilling modelProgressBillingTotal;

	private SuretyBond pnlSuretyBond;

	private JButton btnBypass;
	private JButton btnAddUnit;
	private JButton btnPreview;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;

	private _ButtonGroup grpNewEdit = new _ButtonGroup();
	private JButton btnAddRow;
	private JButton btnRemoveRow;

	private Object[] option_signatory = new Object[] { "Ms. Maria Rhea F. Lim", "Mr. Victor H. Manarang",
	"Mr.  Ferdinand Soliman" };
	// private Object[] option_signatory = new Object[]{"Mr. Ferdinand Soliman",
	// "Mr. Victor H. Manarang"};
	private Object[] option_cse_type = new Object[] { "Metering", "Post" };
	private Object[] tin_signatory = new Object[] { "181-746-452-000", "108-728-634-000", "206-816-824-000" };
	private Object[] repair_type = new Object[] {"Housing", "Land Dev."};
	// private Object[] tin_signatory = new Object[]{"218-426-958-000",
	// "108-728-634-000"};

	private JButton btnTakeover;

	private _JTagLabel tagTO;
	public static String ntp_no = "";

	// private ArrayList<Integer> listDeletedItems;

	static String to_do = "edit";

	private JButton btnAddDesc;
	private JTextArea txtDesc;
	private JXPanel panDesc;

	private Font fontPlainSanSerEleven = new Font("SansSerif", Font.PLAIN, 11);

	DecimalFormat formatter = new DecimalFormat("#,###.00");

	private static JTextField txtTIN;
	private static JCheckBox chkVAT;

	private String sub_proj_id;

	public NoticeToProceed() {
		super(title, true, true, true, true);
		initGUI();

		pgSelect db = new pgSelect();
		db.select("SELECT MAX(ntp_no) FROM co_ntp_header;");

		if (db.isNotNull()/* && !((String)db.getResult()[0][0]).trim().equals("") */) {
			String ntp_no = (String) db.getResult()[0][0];
			System.out.printf("NTP #: %s", ntp_no);
			if (ntp_no != null) {
				lookupNTPNo.setValue(ntp_no);

				displayContractInfo(ntp_no);
				// btnState(true, true, false, false, false);
			}
		}
	}

	public NoticeToProceed(String title) {
		super(title);
		initGUI();
	}

	public NoticeToProceed(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	private void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(874, 600));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setPreferredSize(new Dimension(0, 325));
				{
					{
						JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
						pnlNorth.add(panCenter, BorderLayout.CENTER);
						{
							{
								JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5));
								panCenter.add(panDiv1, BorderLayout.PAGE_START);
								panDiv1.setPreferredSize(new Dimension(0, 120));
								{
									{
										JXPanel panLabel = new JXPanel(new GridLayout(4, 1, 5, 5));
										panDiv1.add(panLabel, BorderLayout.LINE_START);
										panLabel.setPreferredSize(new Dimension(100, 0));
										{
											String[] strLabels = new String[] { "NTP No.", "Contract No.", "Company",
											"Project" };

											JLabel[] lblLabel = new JLabel[4];
											for (int x = 0; x < strLabels.length; x++) {
												lblLabel[x] = new JLabel(strLabels[x]);
												panLabel.add(lblLabel[x]);
												lblLabel[x].setHorizontalAlignment(JLabel.LEFT);
												lblLabel[x].setFont(fontPlainSanSerEleven);
											}
										}
									}
									{
										JXPanel panText = new JXPanel(new GridLayout(4, 1, 5, 5));
										panDiv1.add(panText, BorderLayout.CENTER);
										{
											{
												JXPanel panLine1 = new JXPanel(new BorderLayout(5, 5));
												panText.add(panLine1);
												{
													{
														lookupNTPNo = new _JLookup(null, "NTP No.");
														panLine1.add(lookupNTPNo, BorderLayout.LINE_START);
														lookupNTPNo.setReturnColumn(0);
														lookupNTPNo.setName("lookupNTPNo");
														lookupNTPNo.setLookupSQL(_NoticeToProceed.NTPNo());
														lookupNTPNo.setPreferredSize(new Dimension(100, 0));
														lookupNTPNo.addLookupListener(new LookupListener() {
															public void lookupPerformed(LookupEvent event) {
																Object[] data = ((_JLookup) event.getSource())
																		.getDataSet();
																if (data != null) {
																	lookupContractor.setLookupSQL(
																			_NoticeToProceed.Contractor());

																	displayContractInfo((String) data[0]);
																	ntp_no = (String) data[0];
																	displayTO(ntp_no);

																}
															}
														});
													}
													{
														lblStatus = new JLabel();
														panLine1.add(lblStatus, BorderLayout.CENTER);
														lblStatus.setHorizontalTextPosition(JLabel.CENTER);
														lblStatus.setFont(fontPlainSanSerEleven);
													}
												}
											}
											{
												JXPanel panLine2 = new JXPanel(new BorderLayout(5, 5));
												panText.add(panLine2);
												{
													{
														txtContractNo = new JXTextField("");
														panLine2.add(txtContractNo, BorderLayout.CENTER);
														txtContractNo.getDocument()
														.addDocumentListener(new DocumentListener() {
															public void changedUpdate(DocumentEvent e) {
																warn();
															}

															public void removeUpdate(DocumentEvent e) {
																warn();
															}

															public void insertUpdate(DocumentEvent e) {
																warn();
															}

															public void warn() {
																if (txtContractNo.getText().contains("null")) {
																	txtContractNo.setForeground(Color.RED);
																} else {
																	txtContractNo.setForeground(Color.BLACK);
																}
															}
														});
													}
													{
														tagTO = new _JTagLabel("[ ]");
														panLine2.add(tagTO, BorderLayout.LINE_END);
														tagTO.setPreferredSize(new Dimension(200, 0));
														tagTO.setFont(fontPlainSanSerEleven);
														tagTO.setEnabled(false);
														tagTO.setHorizontalAlignment(_JTagLabel.CENTER);
													}
												}
											}
											{
												JXPanel panLine3 = new JXPanel(new BorderLayout(5, 5));
												panText.add(panLine3);
												{
													{
														lookupCompany = new _JLookup(null, "Company");
														panLine3.add(lookupCompany, BorderLayout.LINE_START);
														lookupCompany.setReturnColumn(0);
														lookupCompany.setLookupSQL(SQL_COMPANY());
														lookupCompany.setPreferredSize(new Dimension(100, 0));
														lookupCompany.addLookupListener(new LookupListener() {
															public void lookupPerformed(LookupEvent event) {
																Object[] data = ((_JLookup) event.getSource())
																		.getDataSet();
																if (data != null) {
																	txtCompany.setText((String) data[1]);
																	lookupProject.setLookupSQL(
																			SQL_PROJECT((String) data[0]));

																	lookupProject.setValue(null);
																	txtProject.setText("");
																	listPhase.setModel(new DefaultComboBoxModel(
																			new ArrayList<_JCheckListItem>()
																			.toArray()));
																	lookupNTPType.setValue(null);
																	txtNTPType.setText("");
																	lookupContractor.setValue(null);
																	txtContractor.setText("");

																	generateContractNo();
																	KEYBOARD_MANAGER.focusNextComponent();
																}
															}
														});
													}
													{
														txtCompany = new JTextField();
														panLine3.add(txtCompany, BorderLayout.CENTER);
														txtCompany.setFont(fontPlainSanSerEleven);
														txtCompany.setEditable(false);
													}
												}
											}
											{
												JXPanel panLine4 = new JXPanel(new BorderLayout(5, 5));
												panText.add(panLine4);
												{
													{
														lookupProject = new _JLookup(null, "Project",
																"Please select project.");
														panLine4.add(lookupProject, BorderLayout.LINE_START);
														lookupProject.setReturnColumn(0);
														lookupProject.setPreferredSize(new Dimension(100, 0));
														lookupProject.addLookupListener(new LookupListener() {
															public void lookupPerformed(LookupEvent event) {
																Object[] data = ((_JLookup) event.getSource())
																		.getDataSet();
																if (data != null) {
																	String co_id = lookupCompany.getValue();

																	String project_id = (String) data[0];
																	String project_name = (String) data[1];
																	String project_alias = (String) data[2];

																	txtProject.setText(String.format("%s (%s)",
																			project_name, project_alias));
																	btnAddUnit.setEnabled(true);
																	btnBypass.setEnabled(true);
																	listPhase.setModel(
																			new DefaultComboBoxModel(_NoticeToProceed
																					.getPhase(lookupNTPNo.getValue(),
																							co_id, project_id)
																					.toArray()));

																	lookupNTPType.setValue(null);
																	txtNTPType.setText("");
																	lookupContractor.setValue(null);
																	txtContractor.setText("");

																	generateContractNo();
																	KEYBOARD_MANAGER.focusNextComponent();
																}
															}
														});
													}
													{
														txtProject = new JTextField();
														panLine4.add(txtProject, BorderLayout.CENTER);
														txtProject.setFont(fontPlainSanSerEleven);
														txtProject.setEditable(false);
													}
												}
											}
										}
									}
								}
							}
							{
								JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5));
								panCenter.add(panDiv2, BorderLayout.CENTER);
								{
									{
										JLabel lblPhase = new JLabel("Phase");
										panDiv2.add(lblPhase, BorderLayout.LINE_START);
										lblPhase.setPreferredSize(new Dimension(200, 0));
									}
									{
										scrollPhase = new JScrollPane();
										panDiv2.add(scrollPhase, BorderLayout.CENTER);
										scrollPhase.setName("scrollPhase");
										{
											listPhase = new JList();
											scrollPhase.setViewportView(listPhase);
											listPhase.setCellRenderer(new _JCheckListRenderer());
											listPhase.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
											listPhase.addMouseListener(new MouseAdapter() {
												public void mouseClicked(MouseEvent event) {
													JList list = (JList) event.getSource();

													if (list.isEnabled()) {
														int index = list.locationToIndex(event.getPoint());
														_JCheckListItem item = (_JCheckListItem) list.getModel()
																.getElementAt(index);
														item.setSelected(!item.isSelected());
														list.repaint(list.getCellBounds(index, index));

														generateContractNo();

														KEYBOARD_MANAGER.focusNextComponent(lookupProject);
														lookupNTPType.setValue(null); //added by lester 2022-11-08 to prevent skipping of series of contract no
														txtNTPType.setText("");
													}
												}
											});
										}
									}
								}
							}
							{
								JXPanel panDiv3 = new JXPanel(new BorderLayout(5, 5));
								panCenter.add(panDiv3, BorderLayout.PAGE_END);
								panDiv3.setPreferredSize(new Dimension(0, 120));
								{
									{
										JXPanel panLabel = new JXPanel(new GridLayout(4, 1, 5, 5));
										panDiv3.add(panLabel, BorderLayout.LINE_START);
										panLabel.setPreferredSize(new Dimension(100, 0));
										{
											String[] strLabels = new String[] { "NTP Type", "Contractor",
													"Downpayment %", "TIN" };

											JLabel[] lblLabel = new JLabel[4];
											for (int x = 0; x < strLabels.length; x++) {
												lblLabel[x] = new JLabel(strLabels[x]);
												panLabel.add(lblLabel[x]);
												lblLabel[x].setHorizontalAlignment(JLabel.LEFT);
												lblLabel[x].setFont(fontPlainSanSerEleven);
											}
										}
									}
									{
										JXPanel panText = new JXPanel(new GridLayout(4, 1, 5, 5));
										panDiv3.add(panText, BorderLayout.CENTER);
										{
											{
												JXPanel panLine1 = new JXPanel(new BorderLayout(5, 5));
												panText.add(panLine1);
												{
													{
														lookupNTPType = new _JLookup(null, "NTP Type");
														panLine1.add(lookupNTPType, BorderLayout.LINE_START);
														lookupNTPType.setReturnColumn(0);
														lookupNTPType.setLookupSQL(_NoticeToProceed.NTPType());
														lookupNTPType.setPreferredSize(new Dimension(100, 0));
														lookupNTPType.addLookupListener(new LookupListener() {
															public void lookupPerformed(LookupEvent event) {
																Object[] data = ((_JLookup) event.getSource())
																		.getDataSet();

																ArrayList<String> listPhase = getSelectedPhase();

																String phase = listPhase.toString()
																		.replaceAll("\\[|\\]", "").replace(", ", "-");
																System.out.printf("Display Phase: %s%n", phase);

																if (listPhase.size() > 1) {
																	phase = "";
																}
																if (data != null) {

																	String type_name = (String) data[1];
																	String type_alias = "";
																	if (lookupCompany.getValue().equals("03")
																			&& (lookupProject.getValue().equals("016") )) {
																		String strSubProjID = FncGlobal
																				.GetString("select sub_proj_id \n"
																						+ "from mf_sub_project \n"
																						+ "where SPLIT_PART(sub_proj_alias, ',', 1) = (case when '"
																						+ phase + "' = 'PH4A' then 'PH4-A' else '"+phase+"' end) \n"
																						+ " and proj_id = '"
																						+ lookupProject.getValue()
																						+ "' AND status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
																		System.out.println("phase1:" + phase);
																		type_alias = _NoticeToProceed.contractCode2(
																				lookupCompany.getValue(),
																				lookupProject.getValue(), strSubProjID,
																				(String) data[0]);
																		System.out.println("Phase :" + phase);
																		txtNTPType.setText(String.format("%s (%s)",
																				type_name, type_alias));
																		sub_proj_id = FncGlobal.GetString(
																				"select sub_proj_id from mf_sub_project where proj_id = '"
																						+ lookupProject.getValue() + "'"
																						+ " and SPLIT_PART(sub_proj_alias, ',', 1) = '"
																						+ phase + "'");

																		System.out.println("sub_proj_id1 :" + sub_proj_id);

																		generateContractNo();
																		KEYBOARD_MANAGER.focusNextComponent();
																	} else {
																		String strSubProjID = FncGlobal
																				.GetString("select sub_proj_id \n"
																						+ "from mf_sub_project \n"
																						+ "where SPLIT_PART(sub_proj_alias, ',', 1) = (case when '"
																						+ phase
																						+ "' = 'PH4A' then 'PH4-A' else '"
																						+ phase
																						+ "' end) and proj_id = '"
																						+ lookupProject.getValue()
																						+ "' AND status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
																		System.out.println("phase1:" + phase);
																		type_alias = _NoticeToProceed.contractCode2(
																				lookupCompany.getValue(),
																				lookupProject.getValue(), strSubProjID,
																				(String) data[0]);
																		System.out.println("Phase :" + phase);
																		txtNTPType.setText(String.format("%s (%s)",
																				type_name, type_alias));
																		sub_proj_id = FncGlobal.GetString(
																				"select sub_proj_id from mf_sub_project where proj_id = '"
																						+ lookupProject.getValue() + "'"
																						+ " and SPLIT_PART(sub_proj_alias, ',', 1) = '"
																						+ phase + "'");

																		System.out.println("sub_proj_id2 :" + sub_proj_id);
																		generateContractNo();
																		KEYBOARD_MANAGER.focusNextComponent();
																	}

																}
															}
														});
													}
													{
														txtNTPType = new JTextField();
														panLine1.add(txtNTPType, BorderLayout.CENTER);
														txtNTPType.setFont(fontPlainSanSerEleven);
														txtNTPType.setEditable(false);
													}
													{
														btnAddDesc = new JButton("Add");
														panLine1.add(btnAddDesc, BorderLayout.LINE_END);
														btnAddDesc.setPreferredSize(new Dimension(50, 0));
														btnAddDesc.addActionListener(new ActionListener() {
															public void actionPerformed(ActionEvent e) {
																try {
																	if (lookupNTPType.getValue().equals("05")
																			|| lookupNTPType.getValue().equals("10")
																			|| lookupNTPType.getValue().equals("07")) {
																		int scanOption = 10;

																		scanOption = JOptionPane.showOptionDialog(null,
																				panDesc, "Add Details",
																				JOptionPane.OK_OPTION,
																				JOptionPane.PLAIN_MESSAGE, null,
																				new Object[] { "CONFIRM" },
																				JOptionPane.OK_OPTION);

																		if (scanOption == JOptionPane.OK_OPTION) {
																			txtDesc.setText(txtDesc.getText()
																					.replace("'", "`"));
																		}
																	} else {
																		JOptionPane.showMessageDialog(getContentPane(),
																				"This feature only works for Special Projects NTP.",
																				"Caution",
																				JOptionPane.INFORMATION_MESSAGE);
																	}
																} catch (NullPointerException ex) {
																	JOptionPane.showMessageDialog(getContentPane(),
																			"Select NTP type first.", "Caution",
																			JOptionPane.INFORMATION_MESSAGE);
																}
															}
														});
													}
												}
											}
											{
												JXPanel panLine2 = new JXPanel(new BorderLayout(5, 5));
												panText.add(panLine2);
												// {
												{
													lookupContractor = new _JLookup(null, "Contractor");
													panLine2.add(lookupContractor, BorderLayout.LINE_START);
													lookupContractor.setLookupSQL(_NoticeToProceed.Contractor());
													lookupContractor.setReturnColumn(0);
													// lookupContractor.setBounds(120, 150, 100, 22);
													lookupContractor.setPreferredSize(new Dimension(100, 0));
													lookupContractor.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															if (data != null) {
																txtContractor.setText((String) data[1]);

																KEYBOARD_MANAGER.focusNextComponent();
																chkVAT.setSelected((Boolean) data[2]);
																txtContractorContact.setText((String) data[4]);
																txtTIN.setText((String) data[5]);

															}
														}
													});
												}
												{
													txtContractor = new JTextField();
													panLine2.add(txtContractor, BorderLayout.CENTER);
													txtContractor.setFont(fontPlainSanSerEleven);
													txtContractor.setEditable(false);
												}
												// }
											}
											{
												JXPanel panLine3 = new JXPanel(new BorderLayout(5, 5));
												panText.add(panLine3);
												{
													{
														txtDownpayment = new _JXFormattedTextField(
																JXFormattedTextField.CENTER);
														panLine3.add(txtDownpayment, BorderLayout.LINE_START);
														txtDownpayment
														.setFormatterFactory(_JXFormattedTextField.PERCENT);
														txtDownpayment.setPreferredSize(new Dimension(100, 0));
													}
													{
														chkWithSuretyBond = new JCheckBox("w/ Surety Bond", false);
														panLine3.add(chkWithSuretyBond, BorderLayout.CENTER);
														chkWithSuretyBond.setMnemonic(KeyEvent.VK_W);
														chkWithSuretyBond.setBorder(lineBorder);
														chkWithSuretyBond.addItemListener(new ItemListener() {
															public void itemStateChanged(ItemEvent arg0) {
																boolean selected = arg0
																		.getStateChange() == ItemEvent.SELECTED;
																// txtWithSuretyBond.setEnabled(chkWithSuretyBond.isEnabled()
																// && selected);

															}
														});
													}
													{
														chkVAT = new JCheckBox("Vatable");
														panLine3.add(chkVAT, BorderLayout.LINE_END);
														chkVAT.setHorizontalAlignment(JCheckBox.LEFT);
														chkVAT.setSelected(false);
														chkVAT.setFont(fontPlainSanSerEleven);
														chkVAT.setEnabled(false);
														chkVAT.setPreferredSize(new Dimension(200, 0));
													}
												}
											}
											{
												JXPanel panLine4 = new JXPanel(new BorderLayout(5, 5));
												panText.add(panLine4);
												{
													{
														txtTIN = new JTextField("");
														panLine4.add(txtTIN, BorderLayout.CENTER);
														txtTIN.setHorizontalAlignment(JTextField.CENTER);
														txtTIN.setFont(fontPlainSanSerEleven);
														txtTIN.setEnabled(false);
													}
													{
														JXPanel panContact = new JXPanel(new BorderLayout(5, 5));
														panLine4.add(panContact, BorderLayout.LINE_END);
														panContact.setPreferredSize(new Dimension(250, 0));
														{
															{
																JLabel lblContact = new JLabel("Contact");
																panContact.add(lblContact, BorderLayout.LINE_START);
																lblContact.setHorizontalAlignment(JLabel.CENTER);
																lblContact.setPreferredSize(new Dimension(100, 0));
															}
															{
																txtContractorContact = new JTextField("");
																panContact.add(txtContractorContact,
																		BorderLayout.CENTER);
																txtContractorContact
																.setHorizontalAlignment(JTextField.CENTER);
																txtContractorContact.setEditable(false);
																txtContractorContact
																.addMouseListener(new MouseListener() {
																	public void mouseReleased(MouseEvent e) {

																	}

																	public void mousePressed(MouseEvent e) {

																	}

																	public void mouseExited(MouseEvent e) {

																	}

																	public void mouseEntered(MouseEvent e) {

																	}

																	public void mouseClicked(MouseEvent e) {
																		JOptionPane.showMessageDialog(
																				getContentPane(),
																				txtContractorContact.getText());
																	}
																});
																txtContractorContact
																.setToolTipText("Click to view content");
															}
														}
													}

												}
											}
										}
									}
								}
							}
						}
					}
					/*
					 * JPanel pnlCenter = new JPanel(); pnlNorth.add(pnlCenter,
					 * BorderLayout.CENTER); pnlCenter.setLayout(new BorderLayout(3, 3));
					 * pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); { { JPanel
					 * pnlCenterNorth = new JPanel(); pnlCenter.add(pnlCenterNorth,
					 * BorderLayout.NORTH); pnlCenterNorth.setLayout(null);
					 * pnlCenterNorth.setPreferredSize(new Dimension(546, 47));
					 * pnlCenterNorth.setBorder(lineBorder); {
					 * 
					 * 
					 * } } { JPanel pnlCenterWest = new JPanel(); pnlCenter.add(pnlCenterWest,
					 * BorderLayout.WEST); pnlCenterWest.setLayout(null);
					 * pnlCenterWest.setBorder(lineBorder); pnlCenterWest.setPreferredSize(new
					 * Dimension(220, 180)); {
					 * 
					 * 
					 * { JLabel lblNTPType = new JLabel("NTP Type"); pnlCenterWest.add(lblNTPType);
					 * lblNTPType.setBounds(0, 125, 120, 22); }
					 * 
					 * { JLabel lblContractor = new JLabel("Contractor");
					 * pnlCenterWest.add(lblContractor); lblContractor.setBounds(0, 150, 120, 22); }
					 * 
					 * } } { JPanel pnlCenterCenter = new JPanel(); pnlCenter.add(pnlCenterCenter,
					 * BorderLayout.CENTER); pnlCenterCenter.setLayout(new GridLayout(7, 1, 3, 3));
					 * pnlCenterCenter.setBorder(lineBorder); {
					 * 
					 * 
					 * { pnlCenterCenter.add(Box.createHorizontalBox());
					 * pnlCenterCenter.add(Box.createHorizontalBox());
					 * pnlCenterCenter.add(Box.createHorizontalBox()); } { { JXPanel panNTPType =
					 * new JXPanel(new BorderLayout(5, 5)); pnlCenterCenter.add(panNTPType); {
					 * 
					 * 
					 * } } }
					 * 
					 * } } { JPanel pnlCenterSouth = new JPanel(new BorderLayout(5, 5));
					 * pnlCenter.add(pnlCenterSouth, BorderLayout.SOUTH);
					 * pnlCenterSouth.setPreferredSize(new Dimension(546, 22)); { { JLabel
					 * lblDownpayment = new JLabel("Downpayment %");
					 * pnlCenterSouth.add(lblDownpayment, BorderLayout.LINE_START);
					 * lblDownpayment.setPreferredSize(new Dimension(115, 0)); }
					 * 
					 * { JXPanel panFiller = new JXPanel(new BorderLayout(5, 5));
					 * pnlCenterSouth.add(panFiller, BorderLayout.LINE_END);
					 * panFiller.setPreferredSize(new Dimension(387, 0)); {
					 * 
					 * } } } } }
					 */
				}
				{
					JPanel pnlEast = new JPanel();
					pnlNorth.add(pnlEast, BorderLayout.LINE_END);
					pnlEast.setLayout(new BorderLayout(5, 5));
					pnlEast.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 2));
					pnlEast.setPreferredSize(new Dimension(225, 180));
					{
						JPanel pnlWorkSchedule = new JPanel();
						pnlEast.add(pnlWorkSchedule, BorderLayout.NORTH);
						pnlWorkSchedule.setLayout(null);
						pnlWorkSchedule.setBorder(components.JTBorderFactory.createTitleBorder("Work Schedule"));
						pnlWorkSchedule.setPreferredSize(new Dimension(223, 135));
						{
							JLabel lblDateStart = new JLabel("Start Date");
							pnlWorkSchedule.add(lblDateStart);
							lblDateStart.setBounds(12, 27, 83, 22);
						}
						{
							dateStart = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlWorkSchedule.add(dateStart);
							dateStart.setBounds(95, 27, 116, 22);
							dateStart.addDateListener(new DateListener() {
								public void datePerformed(DateEvent event) {
									_JDateChooser date = (_JDateChooser) event.getSource();

									try {
										Integer duration = Integer.parseInt(
												JOptionPane.showInputDialog(NoticeToProceed.this.getTopLevelAncestor(),
														"Please input project duration", "Project Duration",
														JOptionPane.QUESTION_MESSAGE));

										Calendar calendar = Calendar.getInstance();
										calendar.setTime(date.getDate());
										calendar.add(Calendar.DAY_OF_MONTH, duration - 1);

										dateFinish.setDate(calendar.getTime());
										// System.out.printf("Start Date: %s - %s (%s)%n", date.getDate(),
										// calendar.getTime(), duration);
									} catch (NumberFormatException e) {
										// e.printStackTrace();
									}
								}
							});
						}
						{
							JLabel lblDateFinish = new JLabel("Finish Date");
							pnlWorkSchedule.add(lblDateFinish);
							lblDateFinish.setBounds(12, 52, 83, 22);
						}
						{
							dateFinish = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlWorkSchedule.add(dateFinish);
							dateFinish.setBounds(95, 52, 116, 22);
						}
						{
							JLabel lblExtension = new JLabel("Extension");
							pnlWorkSchedule.add(lblExtension);
							lblExtension.setBounds(12, 77, 83, 22);
						}
						{
							dateExtension = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlWorkSchedule.add(dateExtension);
							dateExtension.setBounds(95, 77, 116, 22);
						}
						{
							JLabel lblExtNo = new JLabel("Ext No.");
							pnlWorkSchedule.add(lblExtNo);
							lblExtNo.setBounds(12, 102, 83, 22);
						}
						{
							txtExtNo = new JTextField();
							pnlWorkSchedule.add(txtExtNo);
							txtExtNo.setBounds(95, 102, 116, 22);
						}
					}
					{
						JPanel pnlOtherDetails = new JPanel();
						pnlEast.add(pnlOtherDetails, BorderLayout.CENTER);
						pnlOtherDetails.setLayout(null);
						// pnlOtherDetails.setBorder(lineBorder);
						{
							JLabel lblDatePrepared = new JLabel("Date Prepared");
							pnlOtherDetails.add(lblDatePrepared);
							lblDatePrepared.setBounds(6, 2, 95, 22);
						}
						{
							datePrepared = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlOtherDetails.add(datePrepared);
							datePrepared.getCalendarButton().setEnabled(false);
							datePrepared.getCalendarButton().setName("datePrepared");
							((JTextField) datePrepared.getDateEditor()).setEditable(false);
							datePrepared.setBounds(101, 2, 116, 22);
						}
						{
							JLabel lblAwarded = new JLabel("Date Awarded");
							pnlOtherDetails.add(lblAwarded);
							lblAwarded.setBounds(6, 27, 95, 22);
						}
						{
							dateAwarded = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlOtherDetails.add(dateAwarded);
							dateAwarded.setBounds(101, 27, 116, 22);
						}
						{
							chkCOCDate = new JCheckBox("COC Date", false);
							pnlOtherDetails.add(chkCOCDate);
							chkCOCDate.setBounds(6, 52, 95, 22);
							chkCOCDate.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent arg0) {
									boolean isSelected = arg0.getStateChange() == ItemEvent.SELECTED;
									dateCOC.setEnabled(chkCOCDate.isEnabled() && isSelected);
								}
							});
						}
						{
							dateCOC = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlOtherDetails.add(dateCOC);
							((JTextField) dateCOC.getDateEditor()).setName("dateCOC");
							dateCOC.getCalendarButton().setName("dateCOC");
							dateCOC.setBounds(101, 52, 116, 22);
						}
						{
							chkCOCExpiry = new JCheckBox("COC Expiry", false);
							pnlOtherDetails.add(chkCOCExpiry);
							chkCOCExpiry.setBounds(6, 77, 95, 22);
							chkCOCExpiry.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent arg0) {
									boolean isSelected = arg0.getStateChange() == ItemEvent.SELECTED;
									dateCOCExpiry.setEnabled(chkCOCExpiry.isEnabled() && isSelected);
								}
							});
						}
						{
							dateCOCExpiry = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlOtherDetails.add(dateCOCExpiry);
							((JTextField) dateCOCExpiry.getDateEditor()).setName("dateCOCExpiry");
							dateCOCExpiry.getCalendarButton().setName("dateCOCExpiry");
							dateCOCExpiry.setBounds(101, 77, 116, 22);
						}
					}
				}
			}
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				{
					JPanel pnlWorkItems = new JPanel();
					tabCenter.addTab("Work Item(s)", null, pnlWorkItems, null);
					pnlWorkItems.setLayout(new BorderLayout());
					{
						scrollWorkItemsMain = new _JScrollPaneMain();
						pnlWorkItems.add(scrollWorkItemsMain, BorderLayout.CENTER);
						{
							modelWorkItemsMain = new modelNTPWorkItems();

							tblWorkItemsMain = new _JTableMain(modelWorkItemsMain);
							scrollWorkItemsMain.setViewportView(tblWorkItemsMain);
							tblWorkItemsMain.getTableHeader().setEnabled(true);
							tblWorkItemsMain.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if (tblWorkItemsMain.rowAtPoint(e.getPoint()) == -1) {
										tblWorkItemsTotal.clearSelection();
									} else {
										tblWorkItemsMain.setCellSelectionEnabled(true);
									}
								}

								public void mouseReleased(MouseEvent e) {
									if (tblWorkItemsMain.rowAtPoint(e.getPoint()) == -1) {
										tblWorkItemsTotal.clearSelection();
									} else {
										tblWorkItemsMain.setCellSelectionEnabled(true);
									}
								}
							});

							tblWorkItemsMain.getColumnModel().getColumn(1).setMinWidth(200);
							tblWorkItemsMain.getColumnModel().getColumn(4).setMinWidth(100);
							tblWorkItemsMain.getColumnModel().getColumn(5).setMinWidth(100);
							tblWorkItemsMain.getColumnModel().getColumn(6).setMinWidth(100);

							tblWorkItemsMain.addMouseListener(this);
							tblWorkItemsMain.addKeyListener(this);

							tblWorkItemsMain.addPropertyChangeListener(new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent arg0) {
									_JTableMain table = (_JTableMain) arg0.getSource();
									String propertyName = arg0.getPropertyName();

									if (propertyName.equals("tableCellEditor")) {
										int column = table.convertColumnIndexToModel(table.getEditingColumn());
										int row = table.getEditingRow();

										int lumpsum_other = table.convertColumnIndexToModel(
												table.getColumnModel().getColumnIndex("Lump Sum/Other"));
										int total_unit_cost = table.convertColumnIndexToModel(
												table.getColumnModel().getColumnIndex("Total Unit Cost"));
										int total_cost = table.convertColumnIndexToModel(
												table.getColumnModel().getColumnIndex("Total Cost"));

										if (column != -1 && column != 3
												&& modelWorkItemsMain.getColumnClass(column).equals(BigDecimal.class)) {
											Object oldValue = null;
											try {
												oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
											} catch (NullPointerException e) {
											}

											if (oldValue != null) {
												int columnQTY = table.convertColumnIndexToModel(3);
												// System.out.printf("QTY: %s", table.getValueAt(table.getEditingRow(),
												// columnQTY));
												BigDecimal quatity = new BigDecimal("1");
												try {
													quatity = (BigDecimal) table.getValueAt(row, columnQTY);
												} catch (Exception e) {
												}

												if (oldValue instanceof Double) {
													table.setValueAt(new BigDecimal((Double) oldValue), row, column);
													modelWorkItemsMain.setValueAt(new BigDecimal((Double) oldValue),
															row, total_unit_cost);
													if (quatity != null) {
														modelWorkItemsMain.setValueAt(
																new BigDecimal((Double) oldValue).multiply(quatity),
																row, total_cost);
													} else {
														modelWorkItemsMain.setValueAt(new BigDecimal((Double) oldValue),
																row, total_cost);
													}
												}
												if (oldValue instanceof Long) {
													table.setValueAt(new BigDecimal((Long) oldValue), row, column);
													modelWorkItemsMain.setValueAt(new BigDecimal((Long) oldValue), row,
															total_unit_cost);
													if (quatity != null) {
														modelWorkItemsMain.setValueAt(
																new BigDecimal((Long) oldValue).multiply(quatity), row,
																total_cost);
													} else {
														modelWorkItemsMain.setValueAt(new BigDecimal((Long) oldValue),
																row, total_cost);
													}
												}
												computeTotal();
											}
										}

										if (column != -1 && column == 3
												&& modelWorkItemsMain.getColumnClass(column).equals(BigDecimal.class)) {
											Object oldValue = null;
											try {
												oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
											} catch (NullPointerException e) {
											}

											/*
											 * Object newValue = null; try { newValue =
											 * ((NumberEditorExt)arg0.getNewValue()).getCellEditorValue(); } catch
											 * (NullPointerException e) { } System.out.printf("Old: %s     New: %s%n",
											 * oldValue, newValue);
											 */

											if (oldValue instanceof Double) {
												if (oldValue != null && new BigDecimal((Double) oldValue)
														.compareTo(new BigDecimal("0")) == 1) {
													table.setValueAt(new BigDecimal((Double) oldValue), row, column);
													BigDecimal totalUnitcost = (BigDecimal) modelWorkItemsMain
															.getValueAt(row, lumpsum_other);
													modelWorkItemsMain.setValueAt(
															totalUnitcost.multiply(new BigDecimal((Double) oldValue)),
															row, total_cost);

													computeTotal();
												}
											}

											if (oldValue instanceof Long) {
												if (oldValue != null && new BigDecimal((Long) oldValue)
														.compareTo(new BigDecimal("0")) == 1) {
													table.setValueAt(new BigDecimal((Long) oldValue), row, column);
													BigDecimal totalUnitcost = (BigDecimal) modelWorkItemsMain
															.getValueAt(row, lumpsum_other);
													if (totalUnitcost != null) {
														modelWorkItemsMain.setValueAt(
																totalUnitcost.multiply(new BigDecimal((Long) oldValue)),
																row, total_cost);
													}
													computeTotal();
												}
											}
										}
									}
								}
							});
							tblWorkItemsMain.putClientProperty("terminateEditOnFocusLost", true);
							// FncActionMap.print(tblWorkItemsMain);
						}
						{
							rowHeaderWorkItemsMain = tblWorkItemsMain.getRowHeader();
							rowHeaderWorkItemsMain.setModel(new DefaultListModel());
							scrollWorkItemsMain.setRowHeaderView(rowHeaderWorkItemsMain);
							scrollWorkItemsMain.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
					{
						scrollWorkItemsTotal = new _JScrollPaneTotal(scrollWorkItemsMain);
						pnlWorkItems.add(scrollWorkItemsTotal, BorderLayout.SOUTH);
						{
							modelWorkItemsTotal = new modelNTPWorkItems();
							modelWorkItemsTotal.addRow(new Object[] { null, "Total", null, null, null, null, null, null,
									null, null, null });

							tblWorkItemsTotal = new _JTableTotal(modelWorkItemsTotal, tblWorkItemsMain);
							scrollWorkItemsTotal.setViewportView(tblWorkItemsTotal);

							tblWorkItemsTotal.setTotalLabel(1);
						}
						scrollWorkItemsTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
					}
				}
				{
					scrollSubject = new JScrollPane();
					tabCenter.addTab("Subject/Title", null, scrollSubject, null);
					{
						txtSubject = new JXTextArea("Please enter Subject/Title here.");
						scrollSubject.setViewportView(txtSubject);
						txtSubject.setLineWrap(true);
						txtSubject.setWrapStyleWord(true);
						txtSubject.setEditable(false);
						txtSubject.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					}
				}
				{
					scrollNotes = new JScrollPane();
					tabCenter.addTab("Notes", null, scrollNotes, null);
					{
						txtNotes = new JXTextArea("Please enter notes here.");
						scrollNotes.setViewportView(txtNotes);
						txtNotes.setLineWrap(true);
						txtNotes.setWrapStyleWord(true);
						txtNotes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					}
				}
				{
					JPanel pnlAccomplishment = new JPanel();
					// tabCenter.addTab("Accomplishment", null, pnlAccomplishment, null);
					pnlAccomplishment.setLayout(new BorderLayout());
				}
				{
					JPanel pnlExtensions = new JPanel();
					// tabCenter.addTab("Extensions", null, pnlExtensions, null);
					pnlExtensions.setLayout(new BorderLayout());
				}
				{
					JPanel pnlProgressBilling = new JPanel();
					tabCenter.addTab("Progress Billing", null, pnlProgressBilling, null);
					pnlProgressBilling.setLayout(new BorderLayout());
					{
						scrollProgressBilling = new _JScrollPaneMain();
						pnlProgressBilling.add(scrollProgressBilling, BorderLayout.CENTER);
						{
							modelProgressBilling = new modelNTPProgressBilling();

							tblProgressBilling = new _JTableMain(modelProgressBilling);
							scrollProgressBilling.setViewportView(tblProgressBilling);
							tblProgressBilling.getTableHeader().setEnabled(true);
							tblProgressBilling.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if (tblProgressBilling.rowAtPoint(e.getPoint()) == -1) {
										tblWorkItemsTotal.clearSelection();
									} else {
										tblProgressBilling.setCellSelectionEnabled(true);
									}
								}

								public void mouseReleased(MouseEvent e) {
									if (tblProgressBilling.rowAtPoint(e.getPoint()) == -1) {
										tblWorkItemsTotal.clearSelection();
									} else {
										tblProgressBilling.setCellSelectionEnabled(true);
									}
								}
							});

							tblProgressBilling.getColumnModel().getColumn(3).setMinWidth(100);
							tblProgressBilling.getColumnModel().getColumn(4).setMinWidth(100);
							tblProgressBilling.getColumnModel().getColumn(5).setMinWidth(100);
							tblProgressBilling.getColumnModel().getColumn(6).setMinWidth(100);
							tblProgressBilling.getColumnModel().getColumn(7).setMinWidth(100);
							tblProgressBilling.getColumnModel().getColumn(8).setMinWidth(100);
							tblProgressBilling.getColumnModel().getColumn(9).setMinWidth(100);
							tblProgressBilling.getColumnModel().getColumn(10).setMinWidth(100);
							tblProgressBilling.getColumnModel().getColumn(11).setMinWidth(100);
						}
						{
							rowHeaderProgressBilling = tblProgressBilling.getRowHeader();
							rowHeaderProgressBilling.setModel(new DefaultListModel());
							scrollProgressBilling.setRowHeaderView(rowHeaderProgressBilling);
							scrollProgressBilling.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
					{
						scrollProgressBillingTotal = new _JScrollPaneTotal(scrollProgressBilling);
						pnlProgressBilling.add(scrollProgressBillingTotal, BorderLayout.SOUTH);
						{
							modelProgressBillingTotal = new modelNTPProgressBilling();
							modelProgressBillingTotal.addRow(new Object[] { null, "Total", null, null, null, null, null,
									null, null, null, null });

							tblProgressBillingTotal = new _JTableTotal(modelProgressBillingTotal, tblProgressBilling);
							scrollProgressBillingTotal.setViewportView(tblProgressBillingTotal);

							tblProgressBillingTotal.setTotalLabel(1);
						}
					}
					tblProgressBilling.setSortable(false);
				}
				{
					pnlSuretyBond = new SuretyBond();
					tabCenter.addTab("Surety/Guarantee Bond", null, pnlSuretyBond, null);

				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1, 10, 3, 3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(500, 30));
				{
					btnBypass = new JButton("Bypass");
					pnlSouth.add(btnBypass);
					btnBypass.setEnabled(false);
					btnBypass.addActionListener(this);
					btnBypass.setActionCommand("Bypass");
					btnBypass.setFont(fontPlainSanSerEleven);
					btnBypass.setMnemonic(KeyEvent.VK_B);
				}
				{
					btnAddUnit = new JButton("Add Unit(s)");
					pnlSouth.add(btnAddUnit);
					btnAddUnit.setActionCommand("AddUnit");
					btnAddUnit.setMnemonic(KeyEvent.VK_A);
					btnAddUnit.setEnabled(false);
					btnAddUnit.setMinimumSize(new Dimension(200, 30));
					btnAddUnit.addActionListener(this);
					btnAddUnit.setFont(fontPlainSanSerEleven);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.addActionListener(this);
					btnPreview.setFont(fontPlainSanSerEleven);
				}
				{
					btnNew = new JButton("New");
					pnlSouth.add(btnNew);
					btnNew.setActionCommand("New");
					btnNew.setMnemonic(KeyEvent.VK_N);
					btnNew.addActionListener(this);
					btnNew.setFont(fontPlainSanSerEleven);
					grpNewEdit.add(btnNew);

				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
					btnEdit.setFont(fontPlainSanSerEleven);
					grpNewEdit.add(btnEdit);

					btnEdit.addPropertyChangeListener("enabled", new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent arg0) {
							// btnPreview.setEnabled((Boolean) arg0.getNewValue());
							// System.out.printf("Edit: %s (%s, %s)%n",
							// arg0.getPropertyName(),
							// arg0.getOldValue(), arg0.getNewValue());
						}
					});
				}
				{
					btnDelete = new JButton("Delete");
					pnlSouth.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.setMnemonic(KeyEvent.VK_D);
					btnDelete.addActionListener(this);
					btnDelete.setFont(fontPlainSanSerEleven);
				}
				{
					btnTakeover = new JButton("T.O.");
					pnlSouth.add(btnTakeover);
					btnTakeover.setActionCommand("Takeover");
					btnTakeover.setMnemonic(KeyEvent.VK_T);
					btnTakeover.addActionListener(this);
					btnTakeover.setFont(fontPlainSanSerEleven);
					grpNewEdit.add(btnTakeover);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
					btnSave.setFont(fontPlainSanSerEleven);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
					btnCancel.setFont(fontPlainSanSerEleven);
				}
			}
		}

		FncTables.bindColumnTables(tblWorkItemsMain, tblWorkItemsTotal);
		FncTables.bindColumnTables(tblProgressBilling, tblProgressBillingTotal);

		setComponentsEnabled(false);
		btnState(true, false, false, false, false);

		this.grabFocus();
		// KEYBOARD_MANAGER.focusNextComponent(txtWithSuretyBond);
		// this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupNTPNo,
		// lookupCompany, lookupProject, lookupNTPType, lookupContractor,
		// txtDownpayment, txtWithSuretyBond));
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupNTPNo, lookupCompany, lookupProject,
				lookupNTPType, lookupContractor, txtDownpayment));
		this.setBounds(0, 0, 874, 600);

		panDesc = new JXPanel(new BorderLayout(5, 5));
		panDesc.setBorder(new EmptyBorder(5, 5, 5, 5));
		panDesc.setPreferredSize(new Dimension(750, 500));
		{
			txtDesc = new JTextArea("");
			txtDesc.setEditable(true);
			txtDesc.setFont(new Font("Tahoma", Font.PLAIN, 10));
			txtDesc.setLineWrap(true);
			txtDesc.setWrapStyleWord(true);

			JScrollPane scrDesc = new JScrollPane(txtDesc);
			panDesc.add(scrDesc);
		}
	}

	private void generateContractNo() {
		Object[] listCode = new Object[4];

		listCode[0] = txtProject.getText().equals("") ? null : txtProject.getText().split("[\\(\\)]")[1];
		listCode[1] = FncDate.getYear("YYYY", Calendar.getInstance().getTime());
		listCode[3] = txtNTPType.getText().equals("") ? null : txtNTPType.getText().split("[\\(\\)]")[1];

		ArrayList<String> listSelectedPhase = getSelectedPhase();
		if (listSelectedPhase.size() > 0) {
			listCode[2] = listSelectedPhase.toString().replaceAll("\\[|\\]", "").replace(", ", "-");
			txtContractNo.setText(String.format("%s-%s-%s-%s", listCode));
		} else {
			if(lookupNTPType.getValue() != null) {
				if(lookupNTPType.getValue().equals("04")) {
					listCode[2] = null;
					Object[] newListCode = new Object[4];
					newListCode[0] = listCode[0];
					newListCode[1] = listCode[1];
					newListCode[2] = "COMFAC";
					newListCode[3] = listCode[3];

					txtContractNo.setText(String.format("%s-%s-%s-%s", newListCode));
				}
				if(lookupCompany.getValue().equals("02") && (lookupProject.getValue().equals("018") || lookupProject.getValue().equals("015")) && listSelectedPhase.size() == 0) {
					listCode[2] = null;
					Object[] newListCode = new Object[3];
					newListCode[0] = listCode[0];
					newListCode[1] = listCode[1];
					newListCode[2] = listCode[3];

					System.out.println("Dumaan dito sa no phase");

					txtContractNo.setText(String.format("%s-%s-%s", newListCode));
				}

			}else {
				listCode[2] = null;
				Object[] newListCode = new Object[3];
				newListCode[0] = listCode[0];
				newListCode[1] = listCode[1];
				newListCode[2] = listCode[3];

				System.out.println("Dumaan dito sa no phase");

				txtContractNo.setText(String.format("%s-%s-%s", newListCode));
			}
		}
	}

	private ArrayList<String> getSelectedPhase() {
		ArrayList<String> listSelectedPhase = new ArrayList<String>();
		for (int x = 0; x < listPhase.getModel().getSize(); x++) {
			_JCheckListItem selectedItem = (_JCheckListItem) listPhase.getModel().getElementAt(x);


			boolean isSelected = selectedItem.isSelected();

			if (isSelected) {
				String phase = selectedItem.toString().trim().split("[\\(\\)]")[1];
				listSelectedPhase.add(phase);
			}
		}
		return listSelectedPhase;
	}

	private JPanel displayTableNavigation() {
		btnAddRow = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Plus2-Math-icon.png")));
		btnAddRow.setActionCommand("Add Row");
		btnAddRow.setToolTipText("Add Row");
		btnAddRow.setEnabled(false);
		btnAddRow.addActionListener(this);

		btnRemoveRow = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRow.setActionCommand("Remove Row");
		btnRemoveRow.setToolTipText("Remove Row");
		btnRemoveRow.setEnabled(false);
		btnRemoveRow.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnAddRow);
		pnl.add(btnRemoveRow);

		return pnl;
	}

	private void tableNavigation(boolean enable) {
		btnAddRow.setEnabled(enable);
		btnRemoveRow.setEnabled(enable);
	}

	private void updateWorkItem() {
		scrollWorkItemsTotal
		.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblWorkItemsMain.getRowCount())));
		tblWorkItemsMain.packAll();
		if (tblWorkItemsMain.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
			tblWorkItemsMain.getColumnModel().getColumn(1).setPreferredWidth(200);
		}

		for (int row = 0; row < tblWorkItemsMain.getRowCount(); row++) {
			modelWorkItemsMain.setValueAt(row + 1, row, 0);
			((DefaultListModel) rowHeaderWorkItemsMain.getModel()).setElementAt(row + 1, row);
		}
	}

	private void refreshContract(boolean isNew) {

		ntp_no = "";
		lookupNTPNo.setValue(null);
		txtContractNo.setText("");
		lookupNTPType.setValue(null);
		txtNTPType.setText("");
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		listPhase.setModel(new DefaultComboBoxModel(new ArrayList<_JCheckListItem>().toArray()));
		lookupContractor.setValue(null);
		txtContractor.setText("");
		txtDownpayment.setValue(null);
		chkWithSuretyBond.setSelected(false);
		// txtWithSuretyBond.setValue(null);

		dateStart.setDate(null);
		dateFinish.setDate(null);
		dateExtension.setText("");
		txtExtNo.setText("");
		txtContractorContact.setText("");

		datePrepared.setDate(Calendar.getInstance().getTime());
		dateAwarded.setDate(null);
		dateCOC.setDate(null);
		dateCOCExpiry.setDate(null);

		txtNotes.setEditable(false);

		if (isNew) {
			generateContractNo();

			ntp_no = lookupNTPNo.getText();

			txtDownpayment.setValue(0);

			dateStart.setDate(Calendar.getInstance().getTime());
			dateFinish.setDate(Calendar.getInstance().getTime());

			datePrepared.setDate(Calendar.getInstance().getTime());
			dateAwarded.setDate(Calendar.getInstance().getTime());

			pnlSuretyBond.disableSuretyBond();
		}

		txtDesc.setText("");
		chkVAT.setSelected(false);
		txtTIN.setText("");
	}

	private void refreshContractTO(boolean isNew) {

		String old_cont_no = txtContractNo.getText();
		String new_cont_no = old_cont_no + "-T1";

		lookupNTPNo.setValue(null);
		txtContractNo.setText(new_cont_no);
		tagTO.setTag("Take Over : " + old_cont_no);

		dateStart.setDate(null);
		dateFinish.setDate(null);
		dateExtension.setText("");
		txtExtNo.setText("");

		lookupContractor.setValue(null);
		txtContractor.setText("");

		datePrepared.setDate(Calendar.getInstance().getTime());
		dateAwarded.setDate(null);
		dateCOC.setDate(null);
		dateCOCExpiry.setDate(null);

		txtNotes.setEditable(false);

		if (isNew) {

			dateStart.setDate(Calendar.getInstance().getTime());
			dateFinish.setDate(Calendar.getInstance().getTime());

			datePrepared.setDate(Calendar.getInstance().getTime());
			dateAwarded.setDate(Calendar.getInstance().getTime());

			pnlSuretyBond.disableSuretyBond();
		}

		btnAddUnit.setEnabled(true);
		btnBypass.setEnabled(true);
	}

	private void displayContractInfo(String ntp_no) {
		Object[] data = _NoticeToProceed.getContractInfo(ntp_no);

		txtContractNo.setText((String) data[0]);
		lookupNTPType.setValue((String) data[1]);
		txtNTPType.setText((String) data[2]);
		lookupCompany.setValue((String) data[3]);
		txtCompany.setText((String) data[4]);
		lookupProject.setValue((String) data[5]);
		txtProject.setText((String) data[6]);
		lookupContractor.setValue((String) data[7]);
		txtContractor.setText((String) data[8]);
		txtDownpayment.setValue(data[9]);
		// txtWithSuretyBond.setValue(data[11]);
		chkWithSuretyBond.setSelected((Boolean) data[10]);
		lblStatus.setText((String) data[21]);
		txtTIN.setText((String) data[23]);
		chkVAT.setSelected((Boolean) data[22]);

		listPhase.setModel(new DefaultComboBoxModel(_NoticeToProceed
				.getPhase(lookupNTPNo.getValue(), lookupCompany.getValue(), lookupProject.getValue()).toArray()));

		dateStart.setDate((Date) data[12]);
		dateFinish.setDate((Date) data[13]);
		dateExtension.setDate((Date) data[20]);
		datePrepared.setDate((Date) data[14]);
		dateAwarded.setDate((Date) data[15]);
		dateCOC.setDate((Date) data[16]);
		dateCOCExpiry.setDate((Date) data[17]);
		txtSubject.setText((String) data[19]);
		txtNotes.setText((String) data[24]);

		_NoticeToProceed.displayWorkItems(modelWorkItemsMain, rowHeaderWorkItemsMain, ntp_no);
		updateWorkItem();
		computeTotal();

		_NoticeToProceed.displayProgressBilling(modelProgressBilling, rowHeaderProgressBilling, ntp_no);
		scrollProgressBillingTotal
		.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblProgressBilling.getRowCount())));
		tblProgressBilling.packAll();
		_NoticeToProceed.computeTotalProgressBilling(modelProgressBilling, modelProgressBillingTotal);

		pnlSuretyBond.cancelSuretyBond();
		pnlSuretyBond.displaySuretyBonds(txtContractNo.getText());

		if (lblStatus.getText().equals("CANCEL")) {
			btnState(true, false, false, false, false);
			btnPreview.setEnabled(false);
		} else {
			btnState(true, true, !(Boolean) data[18], false, false);
			btnPreview.setEnabled(true);
		}

		txtContractorContact
		.setText(FncGlobal.GetString("select left(trim(contacts), length(trim(contacts))-1) as contacts\n"
				+ "from (SELECT coalesce(replace(replace(replace(replace(replace(\n"
				+ "(select a.mobile::varchar || ', ' || a.telephone::varchar from rf_contacts a where a.entity_id = x.entity_id and a.status_id = 'A')\n"
				+ ", '{', ''), '}', ''), '\"', ''), '-', ''), ';', ''), '') as contacts FROM rf_entity x WHERE x.entity_id = '"
				+ lookupContractor.getValue() + "') a"));

		txtDesc.setText(
				FncGlobal.GetString("select oth_description from co_ntp_header where ntp_no = '" + ntp_no + "'"));
	}

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sDelete, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnDelete.setEnabled(sDelete);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void setComponentsEnabled(boolean enable) {
		panelLooper(pnlNorth, enable);
	}

	public void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				if (comp.getName().equals("scrollPhase")) {
					panelLooper((JScrollPane) comp, enable);
				}
			} else {
				// System.out.printf("Panel: %-50s Component: %s%n",
				// panel.getName(), comp.getName());
				if (comp.getName() != null) {
					if (!comp.getName().equals("datePrepared")) {
						if (comp.getName().equals("dateCOC")) {
							comp.setEnabled(chkCOCDate.isEnabled() && chkCOCDate.isSelected());
						} else if (comp.getName().equals("dateCOCExpiry")) {
							comp.setEnabled(chkCOCExpiry.isEnabled() && chkCOCExpiry.isSelected());
						} else {
							comp.setEnabled(!enable);
						}
					}
				} else {
					if (panel instanceof JScrollPane) {
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					} else {
						// System.out.printf("Comp: %s%n", comp.getClass().getName());
						/*
						 * if(comp instanceof JTextField){ ((JTextField) comp).setEditable(enable);
						 * }else{ comp.setEnabled(enable); }
						 */
						comp.setEnabled(enable);
					}
				}
			}
		}
	}

	private void computeTotal() {
		BigDecimal totalQuantity = new BigDecimal("0.00");
		BigDecimal totalLumpSum = new BigDecimal("0.00");
		BigDecimal totalUnitCost = new BigDecimal("0.00");
		BigDecimal totalCost = new BigDecimal("0.00");

		for (int x = 0; x < modelWorkItemsMain.getRowCount(); x++) {
			BigDecimal quantity = (BigDecimal) modelWorkItemsMain.getValueAt(x, 3);
			BigDecimal lumpSum = (BigDecimal) modelWorkItemsMain.getValueAt(x, 4);
			BigDecimal unitCost = (BigDecimal) modelWorkItemsMain.getValueAt(x, 5);
			BigDecimal cost = (BigDecimal) modelWorkItemsMain.getValueAt(x, 6);

			try {
				totalQuantity = totalQuantity.add(quantity);
			} catch (Exception e1) {
			}
			try {
				totalLumpSum = totalLumpSum.add(lumpSum);
			} catch (Exception e) {
			}
			try {
				totalUnitCost = totalUnitCost.add(unitCost);
			} catch (Exception e) {
			}
			try {
				totalCost = totalCost.add(cost);
			} catch (Exception e) {
			}
		}

		modelWorkItemsTotal.setValueAt(totalQuantity, 0, 3);
		modelWorkItemsTotal.setValueAt(totalLumpSum, 0, 4);
		modelWorkItemsTotal.setValueAt(totalUnitCost, 0, 5);
		modelWorkItemsTotal.setValueAt(totalCost, 0, 6);
	}

	private boolean validateSaving() {
		if (lookupNTPType.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select NTP Type", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupCompany.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Company", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupProject.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*
		 * if (getSelectedPhase().size() == 0) {
		 * JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
		 * "Please select Phase", "Save", JOptionPane.WARNING_MESSAGE); return false; }
		 */
		if (lookupContractor.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Contractor", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (dateStart.getDate() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Start Date", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (dateFinish.getDate() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Finish Date", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (datePrepared.getDate() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Date Prepared", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (dateAwarded.getDate() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Date Awarded", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (chkCOCDate.isSelected() && dateCOC.getDate() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select COC Date", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (chkCOCExpiry.isSelected() && dateCOCExpiry.getDate() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select COC Expiry", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		ArrayList<Integer> listRow = new ArrayList<Integer>();
		listRow.add(1);
		listRow.add(2);
		listRow.add(3);
		listRow.add(6);
		for (int x = 0; x < modelWorkItemsMain.getRowCount(); x++) {
			for (int y = 0; y < modelWorkItemsMain.getColumnCount(); y++) {
				if (listRow.contains(y)) {
					// System.out.printf("Value: %s (%s, %s)%n",
					// modelWorkItemsMain.getValueAt(x, y), x, y);
					if (modelWorkItemsMain.getValueAt(x, y) == null) {
						String column_name = modelWorkItemsMain.getColumnName(y);

						JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
								String.format("Please input data in Column \"%s\". Row %s", column_name, x + 1), "Save",
								JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}
			}
		}
		return true;
	}

	private Boolean isContractExisting(String contract_no) {
		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM co_ntp_header where contract_no = '"+contract_no+"'";
		db.select(SQL);

		if(db.isNotNull()) {
			return true;
		}else {
			return false;
		}
	}

	private void previewReport() {

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Units",
				_NoticeToProceed.getNTPTabulation(lookupNTPType.getValue(), txtDownpayment.getValued()), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		pgSelect db = new pgSelect();
		db.select("select company_logo from mf_company where co_id = '" + lookupCompany.getValue() + "';");

		String company_logo = null;
		if (db.isNotNull()) {
			company_logo = (String) db.getResult()[0][0];
		}

		String ntp_type_id = lookupNTPType.getValue();

		Object[] data = dlg.getReturnDataSet();
		if (data != null) {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			String print_type = (String) data[0];
			// System.out.printf("Print Type: %s%n", data[0]);

			if (print_type.equals("CSE BILL OF QUANTITIES")) {
				if (ntp_type_id.equals("06")) {
					int cse_type = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select CSE Type",
							print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_cse_type,
							option_cse_type[0]);

					mapParameters.put("ntp_no", lookupNTPNo.getValue());
					mapParameters.put("company", txtCompany.getText());
					mapParameters.put("contract_no", txtContractNo.getText());
					mapParameters.put("project", txtProject.getText());
					mapParameters.put("contractor", txtContractor.getText());
					mapParameters.put("start_date", dateStart.getDate());
					mapParameters.put("finish_date", dateFinish.getDate());
					mapParameters.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
					mapParameters.put("isMetering", cse_type == 0);

					FncReport.generateReport("/Reports/rptCSEPBillOfQuantities.jasper", print_type,
							txtCompany.getText(), mapParameters);
				}
				if (ntp_type_id.equals("08")) {
					mapParameters.put("ntp_no", lookupNTPNo.getValue());
					mapParameters.put("company", txtCompany.getText());
					mapParameters.put("contract_no", txtContractNo.getText());
					mapParameters.put("project", txtProject.getText());
					mapParameters.put("contractor", txtContractor.getText());
					mapParameters.put("start_date", dateStart.getDate());
					mapParameters.put("finish_date", dateFinish.getDate());
					mapParameters.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));

					FncReport.generateReport("/Reports/rptBillOfQuantities.jasper", print_type, txtCompany.getText(),
							mapParameters);
				}
			}
			if (print_type.equals("CSE CONTRACT")) {
				if (ntp_type_id.equals("05")) {
					int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
							print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
							option_signatory[0]);

					mapParameters.put("subrptCSEContractUnits",
							this.getClass().getResourceAsStream("/Reports/subrptCSEContractUnits.jasper"));
					mapParameters.put("subrptNTPContractUnits",
							this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits.jasper"));
					mapParameters.put("ntp_no", lookupNTPNo.getValue());
					mapParameters.put("signatory", option_signatory[signatory]);
					mapParameters.put("tin", tin_signatory[signatory]);
					mapParameters.put("prepared_by", UserInfo.Alias);

					FncReport.generateReport("/Reports/rptCSEPContract.jasper", print_type, txtCompany.getText(),
							mapParameters);
				} else {
					int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
							print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
							option_signatory[0]);

					mapParameters.put("subrptCSEContractUnits",
							this.getClass().getResourceAsStream("/Reports/subrptCSEContractUnits.jasper"));
					mapParameters.put("subrptNTPContractUnits",
							this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits.jasper"));
					mapParameters.put("ntp_no", lookupNTPNo.getValue());
					mapParameters.put("isMetering", false);
					mapParameters.put("signatory", option_signatory[signatory]);
					mapParameters.put("tin", tin_signatory[signatory]);
					mapParameters.put("prepared_by", UserInfo.Alias);

					String strAmount = "";
					strAmount = FncGlobal.GetString(
							"select (orig_contract_price * (dp_percentage / 100))::numeric(19, 2)::varchar \n"
									+ "from co_ntp_header \n" + "where ntp_no = '" + lookupNTPNo.getValue() + "'");

					mapParameters.put("dp_amt", formatter.format(new BigDecimal(strAmount)));
					mapParameters.put("dp_amt_words", convertToWords(strAmount));

					FncReport.generateReport("/Reports/rptCSEContract.jasper", print_type, txtCompany.getText(),
							mapParameters);
				}

			}
			if (print_type.equals("CSE CONTRACT (METERING)")) {
				int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
						print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
						option_signatory[0]);

				mapParameters.put("subrptCSEContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptCSEContractUnits.jasper"));
				mapParameters.put("subrptNTPContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits.jasper"));
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("isMetering", true);
				mapParameters.put("signatory", option_signatory[signatory]);
				mapParameters.put("tin", tin_signatory[signatory]);
				mapParameters.put("prepared_by", UserInfo.Alias);

				System.out.println(lookupNTPNo.getValue());

				// FncReport.generateReport("/Reports/rptCSEContract.jasper", print_type,
				// txtCompany.getText(), mapParameters);String strAmount = "";
				// 11-18-2021 hindi existing yung parameters by: jervin vilog
				String strAmount = "";

				strAmount = FncGlobal.GetString(
						"select (orig_contract_price * (dp_percentage / 100))::numeric(19, 2)::varchar \n"
								+ "from co_ntp_header \n" + "where ntp_no = '" + lookupNTPNo.getValue() + "'");

				mapParameters.put("dp_amt", formatter.format(new BigDecimal(strAmount)));
				mapParameters.put("dp_amt_words", convertToWords(strAmount));
				FncReport.generateReport("/Reports/rptCSEContract_metering.jasper", print_type, txtCompany.getText(),
						mapParameters);
			}
			if (print_type.equals("CSE NOTICE OF AWARD")) {// this.getClass().getResourceAsStream("/Reports/subrptNoticeOfAward.jasper")

				if (ntp_type_id.equals("05")) {
					mapParameters.put("subrptNoticeOfAward", this.getClass().getClassLoader()
							.getResourceAsStream("Reports/subrptCSENoticeOfAward.jasper"));
					mapParameters.put("ntp_no", lookupNTPNo.getValue());
					mapParameters.put("hasDP", ((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0);
					mapParameters.put("logo",
							this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
					mapParameters.put("prepared_by", UserInfo.Alias);

					FncReport.generateReport("/Reports/rptCSEPNoticeOfAward.jasper", print_type, txtCompany.getText(),
							mapParameters);
				} else {


					if(ntp_type_id.equals("03")) {
						int rep_type = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select repair type",
								print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, repair_type,
								repair_type[0]);

						mapParameters.put("repair_type", rep_type);
					}else {
						mapParameters.put("repair_type", null);
					}


					mapParameters.put("subrptNoticeOfAward", this.getClass().getClassLoader()
							.getResourceAsStream("Reports/subrptCSENoticeOfAward.jasper"));
					mapParameters.put("subrptCSEContractUnits", this.getClass().getClassLoader()
							.getResourceAsStream("Reports/subrptCSEContractUnits.jasper"));
					mapParameters.put("ntp_no", lookupNTPNo.getValue());
					mapParameters.put("isMetering", false);
					mapParameters.put("hasDP", ((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0);
					mapParameters.put("logo",
							this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
					mapParameters.put("prepared_by", UserInfo.Alias);


					FncReport.generateReport("/Reports/rptCSENoticeOfAward.jasper", print_type, txtCompany.getText(),
							mapParameters);
				}
			}
			if (print_type.equals("CSE NOTICE OF AWARD (METERING)")) {
				mapParameters.put("subrptNoticeOfAward",
						this.getClass().getClassLoader().getResourceAsStream("Reports/subrptCSENoticeOfAward.jasper"));
				mapParameters.put("subrptCSEContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptCSEContractUnits.jasper"));
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("isMetering", true);
				mapParameters.put("hasDP", ((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0);
				mapParameters.put("logo",
						this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);

				System.out.println("");
				System.out.println("rptCSENoticeOfAward.jasper");

				FncReport.generateReport("/Reports/rptCSENoticeOfAward.jasper", print_type, txtCompany.getText(),
						mapParameters);
			}

			if (print_type.equals("HOUSING BILL OF QUANTITIES")) {
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("company", txtCompany.getText());
				mapParameters.put("contract_no", txtContractNo.getText());
				mapParameters.put("project", txtProject.getText());
				mapParameters.put("contractor", txtContractor.getText());
				mapParameters.put("start_date", dateStart.getDate());
				mapParameters.put("finish_date", dateFinish.getDate());
				mapParameters.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));

				FncReport.generateReport("/Reports/rptBillOfQuantities.jasper", print_type, txtCompany.getText(),
						mapParameters);
			}
			if (print_type.equals("HOUSING CONTRACT")) {

				int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
						print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
						option_signatory[0]);

				mapParameters.put("contract_no", txtContractNo.getText());
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("signatory", option_signatory[signatory]);
				mapParameters.put("tin", tin_signatory[signatory]);
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("subrptNTPContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits.jasper"));
				mapParameters.put("isHousing", true);

				if (tagTO.getText().equals("[ ]")) {
					if (((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0) {
						FncReport.generateReport("/Reports/rptNTPContract.jasper", print_type, txtCompany.getText(),
								mapParameters);
					} else {
						FncReport.generateReport("/Reports/rptNTPContractWithoutDP.jasper", print_type,
								txtCompany.getText(), mapParameters);
					}
				} else {
					if (((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0) {
						FncReport.generateReport("/Reports/rptNTPContractTO.jasper", print_type, txtCompany.getText(),
								mapParameters);
					} else {
						FncReport.generateReport("/Reports/rptNTPContractWithoutDPTO.jasper", print_type,
								txtCompany.getText(), mapParameters);
					}
				}

				/* Added by Mann2x; Date Added: September 6, 20178; DCRF# 743; */
				FncReport.generateReport("/Reports/rptGeneralGuidelinesOnRepairs.jasper", "Guidelines on Repairs",
						txtCompany.getText(), mapParameters);
				FncReport.generateReport("/Reports/rptStandardRepairCost.jasper", "Standard Repair Cost",
						txtCompany.getText(), mapParameters);
			}

			if (print_type.equals("HOUSING NOTICE OF AWARD")) {// this.getClass().getResourceAsStream("/Reports/subrptNoticeOfAward.jasper")
				mapParameters.put("subrptNoticeOfAward",
						this.getClass().getClassLoader().getResourceAsStream("Reports/subrptNoticeOfAward.jasper"));
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("logo",
						this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);

				if (tagTO.getText().equals("[ ]")) {
					System.out.println("");
					System.out.println("Reports/rptNoticeOfAward.jasper");
					FncReport.generateReport("/Reports/rptNoticeOfAward.jasper", print_type, txtCompany.getText(),
							mapParameters);
				} else {
					System.out.println("");
					System.out.println("Reports/rptNoticeOfAwardTO.jasper");

					FncReport.generateReport("/Reports/rptNoticeOfAwardTO.jasper", print_type, txtCompany.getText(),
							mapParameters);
				}
			}

			if (print_type.equals("FACILITIES CONTRACT")) {
				int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
						print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
						option_signatory[0]);

				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("signatory", option_signatory[signatory]);
				mapParameters.put("tin", tin_signatory[signatory]);
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("subrptNTPContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits.jasper"));
				mapParameters.put("isHousing", false);

				// System.out.printf("DP: %s%n%n", txtDownpayment.getValued());
				if (((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0) {
					FncReport.generateReport("/Reports/rptNTPContract.jasper", print_type, txtCompany.getText(),
							mapParameters);
				} else {
					FncReport.generateReport("/Reports/rptNTPContractWithoutDP.jasper", print_type,
							txtCompany.getText(), mapParameters);
				}
			}
			if (print_type.equals("FACILITIES NOTICE OF AWARD")) {
				mapParameters.put("subrptNoticeOfAward",
						this.getClass().getResourceAsStream("/Reports/subrptCSENoticeOfAward.jasper"));
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("project", txtProject.getText());
				mapParameters.put("hasDP", ((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0);
				mapParameters.put("logo",
						this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);

				FncReport.generateReport("/Reports/rptCSEPNoticeOfAward.jasper", print_type, txtCompany.getText(),
						mapParameters);
			}

			if (print_type.equals("LANDEV CONTRACT")) {
				int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
						print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
						option_signatory[0]);

				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("signatory", option_signatory[signatory]);
				mapParameters.put("tin", tin_signatory[signatory]);
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("subrptNTPContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits.jasper"));
				mapParameters.put("subrptNoticeOfAward", this.getClass().getClassLoader()
						.getResourceAsStream("Reports/subrptLandDevContractUnits.jasper"));
				mapParameters.put("isHousing", true);

				// System.out.printf("DP: %s%n%n", txtDownpayment.getValued());
				if (((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0) {
					FncReport.generateReport("/Reports/rptLandevContract.jasper", print_type, txtCompany.getText(),
							mapParameters);
				} else {
					FncReport.generateReport("/Reports/rptLandevContractWithoutDP.jasper", print_type,
							txtCompany.getText(), mapParameters);
				}
			}
			if (print_type.equals("LANDEV NOTICE OF AWARD")) {// this.getClass().getResourceAsStream("/Reports/subrptNoticeOfAward.jasper")
				mapParameters.put("subrptNoticeOfAward",
						this.getClass().getClassLoader().getResourceAsStream("Reports/subrptNoticeOfAward.jasper"));
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("company", txtCompany.getText());
				mapParameters.put("hasDP", ((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0);
				mapParameters.put("logo",
						this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);

				System.out.println("");
				System.out.println("rptLandevNoticeOfAward");

				FncReport.generateReport("/Reports/rptLandevNoticeOfAward.jasper", print_type, txtCompany.getText(),
						mapParameters);
			}

			if (print_type.equals("SURETY BOND")) {
				mapParameters.put("company", txtCompany.getText());
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("logo",
						this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("bat", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
				mapParameters.put("prepared_by", UserInfo.Alias);

				FncReport.generateReport("/Reports/rptSuretyBondRequestForm.jasper", print_type, txtCompany.getText(),
						mapParameters);
			}

			// if (print_type.equals("LANDEV BILL OF QUANTITES")) {

			// }

			if (print_type.equals("MINOR CONTRACT")) {
				int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
						print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
						option_signatory[0]);

				//				mapParameters.put("subrptCSEContractUnits",
				//						this.getClass().getResourceAsStream("/Reports/subrptCSEContractUnits.jasper"));
				mapParameters.put("subrptLandDevContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptLandDevContractUnits.jasper"));
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("project", txtProject.getText());
				mapParameters.put("signatory", option_signatory[signatory]);
				mapParameters.put("tin", tin_signatory[signatory]);
				mapParameters.put("prepared_by", UserInfo.Alias);

				if (lookupNTPType.getValue().equals("10") || lookupNTPType.getValue().equals("03")) {
					System.out.print("rptSPContractADM");
					FncReport.generateReport("/Reports/rptSPContractADM.jasper", print_type, txtCompany.getText(),
							mapParameters);
				} else {
					System.out.print("rptSPContract_v2");
					FncReport.generateReport("/Reports/rptSPContract_v2.jasper", print_type, txtCompany.getText(),
							mapParameters);
				}
			}

			if (print_type.equals("SUPPLIER CONTRACT")) {
				int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
						print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
						option_signatory[0]);

				//				mapParameters.put("subrptCSEContractUnits",
				//						this.getClass().getResourceAsStream("/Reports/subrptCSEContractUnits.jasper"));
				mapParameters.put("subrptLandDevContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptLandDevContractUnits.jasper"));
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("project", txtProject.getText());
				mapParameters.put("signatory", option_signatory[signatory]);
				mapParameters.put("tin", tin_signatory[signatory]);
				mapParameters.put("prepared_by", UserInfo.Alias);


				System.out.print("rptSupplierContract");
				FncReport.generateReport("/Reports/rptSupplierContract.jasper", print_type, txtCompany.getText(),
						mapParameters);

			}

			if (print_type.equals("SP NOTICE OF AWARD")) {// this.getClass().getResourceAsStream("/Reports/subrptNoticeOfAward.jasper")
				mapParameters.put("subrptNoticeOfAward",
						this.getClass().getResourceAsStream("/Reports/subrptCSENoticeOfAward.jasper"));
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("project", txtProject.getText());
				mapParameters.put("hasDP", ((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0);
				mapParameters.put("logo",
						this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);

				if (lookupNTPNo.getValue().equals("000608") || lookupNTPNo.getValue().equals("002289")) {
					mapParameters.put("subrptNoticeOfAward",
							this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits_special2.jasper"));
					FncReport.generateReport("/Reports/rptCSEPNoticeOfAward_special.jasper", print_type,
							txtCompany.getText(), mapParameters);
				} else {
					FncReport.generateReport("/Reports/rptCSEPNoticeOfAward.jasper", print_type, txtCompany.getText(),
							mapParameters);
				}
			}

			if (print_type.equals("MAJOR CONTRACT")) {
				int signatory = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select signatory",
						print_type, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_signatory,
						option_signatory[0]);

				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("signatory", option_signatory[signatory]);
				mapParameters.put("tin", tin_signatory[signatory]);
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("subrptNTPContractUnits",
						this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits.jasper"));
				mapParameters.put("isHousing", false);
				mapParameters.put("title", txtSubject.getText().replace("&", "&amp;"));

				// System.out.printf("DP: %s%n%n", txtDownpayment.getValued());
				if (((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0) {
					System.out.println("");
					System.out.println("NTP No. " + lookupNTPNo.getValue());

					if (lookupNTPNo.getValue().equals("000608") || lookupNTPNo.getValue().equals("002289")) {
						mapParameters.put("subrptNTPContractUnits",
								this.getClass().getResourceAsStream("/Reports/subrptNTPContractUnits_special.jasper"));
						FncReport.generateReport("/Reports/rptNTPContract_special.jasper", print_type,
								txtCompany.getText(), mapParameters);
					} else {
						if (lookupNTPNo.getValue().equals("001285")) {
							FncReport.generateReport("/Reports/rptNTPContract_aircon.jasper", print_type,
									txtCompany.getText(), mapParameters);
						} else {
							FncReport.generateReport("/Reports/rptNTPContract.jasper", print_type, txtCompany.getText(),
									mapParameters);
						}
					}
				} else {
					//					FncReport.generateReport("/Reports/rptNTPContractWithoutDP.jasper", print_type,
					//							txtCompany.getText(), mapParameters);
					FncReport.generateReport("/Reports/rptNTPContractWithoutDP_v2.jasper", print_type,
							txtCompany.getText(), mapParameters);
				}
			}

			if (print_type.equals("OSP NOTICE OF AWARD")) {
				mapParameters.put("ntp_no", lookupNTPNo.getValue());
				mapParameters.put("project", txtProject.getText());
				mapParameters.put("hasDP", ((BigDecimal) txtDownpayment.getValued()).doubleValue() > 0);
				mapParameters.put("logo",
						this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);

				String title = txtSubject.getText();

				for(int x=0; x < modelWorkItemsMain.getRowCount(); x++){
					String work_item = (String) modelWorkItemsMain.getValueAt(x, 1);

					if(title.equals(work_item)) {
						title = "";
					}
				}
				mapParameters.put("title", title);


				if (lookupNTPType.getValue().equals("10")) {
					mapParameters.put("location", JOptionPane
							.showInputDialog("Please specify location. `4th Floor, The Aster Business Center, \n"
									+ "Mandala Park, Mandaluyong City` will be set as default location if left blank."));
					mapParameters.put("subrptNoticeOfAward", this.getClass().getClassLoader()
							.getResourceAsStream("/Reports/subrptLandDevContractUnitsADM.jasper"));
					FncReport.generateReport("/Reports/rptOSPNoticeOfAwardADM.jasper", print_type, txtCompany.getText(),
							mapParameters);
				} else {
					mapParameters.put("subrptNoticeOfAward", this.getClass().getClassLoader()
							.getResourceAsStream("/Reports/subrptLandDevContractUnits.jasper"));
					FncReport.generateReport("/Reports/rptOSPNoticeOfAward.jasper", print_type, txtCompany.getText(),
							mapParameters);
				}
			}
		}
	}

	private void displayAddUnits() {
		int column = tblWorkItemsMain.getSelectedColumn();
		int row = tblWorkItemsMain.getSelectedRow();

		if (column == 2 && modelWorkItemsMain.isEditable()) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Unit of Measure", _NoticeToProceed.UOM(),
					false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelWorkItemsMain.setValueAt(data[0], row, column);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {
			displayAddUnits();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F2) {
			displayAddUnits();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupNTPNo.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if (actionCommand.equals("Preview")) {
			previewReport();
		}

		if (actionCommand.equals("New")) {
			grpNewEdit.setSelectedButton(arg0);

			modelWorkItemsMain.setEditable(true);
			refreshContract(true);
			setComponentsEnabled(true);

			modelWorkItemsMain.clear();
			((DefaultListModel) rowHeaderWorkItemsMain.getModel()).removeAllElements();
			updateWorkItem();
			computeTotal();

			modelProgressBilling.clear();
			((DefaultListModel) rowHeaderProgressBilling.getModel()).removeAllElements();
			scrollProgressBillingTotal.setRowHeaderView(
					FncTables.getRowHeader_Footer(Integer.toString(tblProgressBilling.getRowCount())));
			_NoticeToProceed.computeTotalProgressBilling(modelProgressBilling, modelProgressBillingTotal);

			lookupNTPNo.setValue(_NoticeToProceed.generateNewNTPNo());

			btnState(false, false, false, true, true);
			tableNavigation(true);

			lblStatus.setText("");
			txtSubject.setText("");
			txtNotes.setText("");
			txtSubject.setEditable(true);
			txtNotes.setEditable(true);
		}

		if (actionCommand.equals("Takeover")) {
			grpNewEdit.setSelectedButton(arg0);

			modelWorkItemsMain.setEditable(true);
			refreshContractTO(true);
			setComponentsEnabled(true);
			lookupCompany.setEnabled(false);
			lookupProject.setEnabled(false);
			listPhase.setEnabled(false);
			lookupNTPType.setEnabled(false);

			// modelWorkItemsMain.clear();
			// ((DefaultListModel) rowHeaderWorkItemsMain.getModel()).removeAllElements();
			updateWorkItem();
			computeTotal();

			modelProgressBilling.clear();
			((DefaultListModel) rowHeaderProgressBilling.getModel()).removeAllElements();
			scrollProgressBillingTotal.setRowHeaderView(
					FncTables.getRowHeader_Footer(Integer.toString(tblProgressBilling.getRowCount())));
			_NoticeToProceed.computeTotalProgressBilling(modelProgressBilling, modelProgressBillingTotal);

			lookupNTPNo.setValue(_NoticeToProceed.generateNewNTPNo());

			btnState(false, false, false, true, true);
			tableNavigation(true);

			lblStatus.setText("");
		}

		if (actionCommand.equals("Edit")) {
			Object[] data = _NoticeToProceed.getContractInfo(lookupNTPNo.getText());

			Boolean ext = (Boolean) data[18];

			if (ext.equals(true)) {
				grpNewEdit.setSelectedButton(arg0);

				dateExtension.setEnabled(true);
				lookupNTPNo.setEnabled(false);
				modelWorkItemsMain.setEditable(false);
				// setComponentsEnabled(false);
				btnState(false, false, false, true, true);

				btnAddUnit.setEnabled(false);
				btnBypass.setEnabled(true);

				tableNavigation(false);
				// pnlSuretyBond.disableSuretyBond();
				txtSubject.setEditable(true);
				btnPreview.setEnabled(false);
				txtNotes.setEditable(true);
			} else {
				grpNewEdit.setSelectedButton(arg0);
				modelWorkItemsMain.setEditable(true);
				setComponentsEnabled(true);
				btnState(false, false, true, true, true);

				btnAddUnit.setEnabled(true);
				btnBypass.setEnabled(true);

				tableNavigation(true);
				pnlSuretyBond.disableSuretyBond();
				txtSubject.setEditable(true);
				txtNotes.setEditable(true);
			}
		}

		if (actionCommand.equals("Delete")) {
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),
					"Are you sure you want to delete selected contract?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				_NoticeToProceed.deleteNTP(lookupNTPNo.getValue(), lookupCompany.getValue(), lookupProject.getValue());

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Contract has been deleted.", "Save",
						JOptionPane.INFORMATION_MESSAGE);
				btnCancel.doClick();

				pgSelect db = new pgSelect();
				db.select("select max(ntp_no) from co_ntp_header;");

				if (db.isNotNull()) {
					String ntp_no = (String) db.getResult()[0][0];
					lookupNTPNo.setValue(ntp_no);

					displayContractInfo(ntp_no);
					// btnState(true, true, false, false, false);
				}
			}
		}

		if (actionCommand.equals("Save")) {
			String selection = grpNewEdit.getActionCommand();

			if (validateSaving()) {
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					BigDecimal contract_price = null;
					BigDecimal orig_contract_price = (BigDecimal) modelWorkItemsTotal.getValueAt(0, 6);

					// String selection = grpNewEdit.getActionCommand();
					System.out.printf("Value of selection: %s%n", selection);
					if (selection.equals("New")) {
						if(isContractExisting(txtContractNo.getText()) == false) {

							System.out.println("sub_proj_id444 :" + sub_proj_id);
							_NoticeToProceed.saveNTP(lookupNTPNo.getValue(), datePrepared.getTimestamp(),
									lookupCompany.getValue(), lookupProject.getValue(), lookupContractor.getValue(),
									dateStart.getTimestamp(), dateFinish.getTimestamp(), dateAwarded.getTimestamp(),
									txtContractNo.getText(), contract_price, orig_contract_price, lookupNTPType.getValue(),
									txtDownpayment.getValued(), chkWithSuretyBond.isSelected(), txtNotes.getText().trim(),
									false, null, null, dateCOC.getTimestamp(), dateCOCExpiry.getTimestamp(), 12.00,
									txtSubject.getText().trim().replace("'", "''"), modelWorkItemsMain,
									dateExtension.getTimestamp(), txtDesc.getText(),sub_proj_id);
							System.out.println("sub_proj_id3 :" + sub_proj_id);
							String strPhase = getSelectedPhase().toString().replaceAll("\\[|\\]", "").replace(", ", "-");
							String strSubProject = FncGlobal
									.GetString("select sub_proj_id \n" + "from mf_sub_project\n" + "where proj_id = '"
											+ lookupProject.getValue() + "' and sub_proj_alias = (case when'" + strPhase + "' = 'PH4A' then 'PH4-A' else '"+strPhase+"' end) AND status_id = 'A'"); //ADDED STATUS ID BY LESTER DCRF 2718
							if (lookupNTPType.getValue().equals("05")) {
								pgUpdate dbExec = new pgUpdate();
								dbExec.executeUpdate("update rf_contract_sequence \n" + "set sequence_no = sequence_no+1 \n"
										+ "where co_id = '" + lookupCompany.getValue() + "' and proj_id = '"
										+ lookupProject.getValue() + "' \n" + "and (ntp_type_id = '"
										+ lookupNTPType.getValue() + "' or (ntp_type_id in ('06', '08') and '"
										+ lookupNTPType.getValue() + "' in ('06', '08'))) \n" + "and status_id = 'A'",
										true);
								dbExec.commit();
								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Contract has been created.",
										"Save", JOptionPane.INFORMATION_MESSAGE);
								System.out.println("sub_proj_id3 :" + sub_proj_id);

							} else {
								pgUpdate dbExec = new pgUpdate();
								dbExec.executeUpdate("update rf_contract_sequence \n" + "set sequence_no = sequence_no+1 \n"
										+ "where co_id = '" + lookupCompany.getValue() + "' and proj_id = '"
										+ lookupProject.getValue() + "' and sub_proj_id = '" + strSubProject + "' \n"
										+ "and (ntp_type_id = '" + lookupNTPType.getValue()
										+ "' or (ntp_type_id in ('06', '08') and '" + lookupNTPType.getValue()
										+ "' in ('06', '08'))) \n" + "and status_id = 'A'", true);
								dbExec.commit();
								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Contract has been created.",
										"Save", JOptionPane.INFORMATION_MESSAGE);
							}

							if (strSubProject.equals("")) {
								pgUpdate dbExec = new pgUpdate();
								dbExec.executeUpdate("update rf_contract_sequence \n" + "set sequence_no = sequence_no+1 \n"
										+ "where co_id = '" + lookupCompany.getValue() + "' and proj_id = '"
										+ lookupProject.getValue() + "' and sub_proj_id is null \n" + "and (ntp_type_id = '"
										+ lookupNTPType.getValue() + "' or (ntp_type_id in ('06', '08') and '"
										+ lookupNTPType.getValue() + "' in ('06', '08'))) \n" + "and status_id = 'A'",
										true);
								dbExec.commit();
								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Contract has been created.",
										"Save", JOptionPane.INFORMATION_MESSAGE);
							} 
						}else {
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Contract is already existing", "Save", JOptionPane.WARNING_MESSAGE);
						}
					}
					if (selection.equals("Edit")) {
						_NoticeToProceed.updateNTP(lookupNTPNo.getValue(), datePrepared.getTimestamp(),
								lookupCompany.getValue(), lookupProject.getValue(), lookupContractor.getValue(),
								dateStart.getTimestamp(), dateFinish.getTimestamp(), dateAwarded.getTimestamp(),
								txtContractNo.getText(), contract_price, orig_contract_price, lookupNTPType.getValue(),
								txtDownpayment.getValued(),
								// (String) txtWithSuretyBond.getValue(),
								chkWithSuretyBond.isSelected(), txtNotes.getText().trim(), false, null, null,
								dateCOC.getTimestamp(), dateCOCExpiry.getTimestamp(), 12.00,
								txtSubject.getText().trim().replace("'", "''"), modelWorkItemsMain,
								dateExtension.getTimestamp(), txtDesc.getText().toString());

						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Contract has been edited.", "Save",
								JOptionPane.INFORMATION_MESSAGE);
					}
					if (selection.equals("Takeover")) {
						_NoticeToProceed.saveNTP(lookupNTPNo.getValue(), datePrepared.getTimestamp(),
								lookupCompany.getValue(), lookupProject.getValue(), lookupContractor.getValue(),
								dateStart.getTimestamp(), dateFinish.getTimestamp(), dateAwarded.getTimestamp(),
								txtContractNo.getText(), contract_price, orig_contract_price, lookupNTPType.getValue(),
								txtDownpayment.getValued(),
								// (String) txtWithSuretyBond.getValue(),
								chkWithSuretyBond.isSelected(), txtNotes.getText().trim(), false, null, null,
								dateCOC.getTimestamp(), dateCOCExpiry.getTimestamp(), 12.00,
								txtSubject.getText().trim().replace("'", "''"), modelWorkItemsMain,
								dateExtension.getTimestamp(), txtDesc.getText(),sub_proj_id);

						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Contract has been taken over.",
								"Save", JOptionPane.INFORMATION_MESSAGE);
						updateTO();
					}

					saveEditNtpNotes();
					txtSubject.setEditable(false);
					// btnPreview.doClick();
					btnCancel.doClick();
				}
			}

			displayContractInfo(lookupNTPNo.getValue());
			removeBypass();
		}

		if (actionCommand.equals("Cancel")) {
			String selection = grpNewEdit.getActionCommand();
			Document doc = Jsoup.parse(selection);
			selection = doc.body().text();

			setComponentsEnabled(false);
			if (selection.equals("New")) {
				lookupNTPNo.setValue(null);
				refreshContract(false);

				modelWorkItemsMain.clear();
				((DefaultListModel) rowHeaderWorkItemsMain.getModel()).removeAllElements();
				updateWorkItem();
				computeTotal();

				modelProgressBilling.clear();
				((DefaultListModel) rowHeaderProgressBilling.getModel()).removeAllElements();
				scrollProgressBillingTotal.setRowHeaderView(
						FncTables.getRowHeader_Footer(Integer.toString(tblProgressBilling.getRowCount())));

				btnState(true, false, false, false, false);

				pnlSuretyBond.cancelSuretyBond();
			} else {
				displayContractInfo(lookupNTPNo.getValue());
				btnState(true, true, true, false, false);
				pnlSuretyBond.editSuretyBond();
			}

			modelWorkItemsMain.setEditable(false);

			btnAddUnit.setEnabled(false);
			btnBypass.setEnabled(false);

			grpNewEdit.clearSelection();
			tableNavigation(false);

			lblStatus.setText("");
			ntp_no = "";

			lblStatus.setText("");
			txtSubject.setText("");
			txtSubject.setEditable(false);
			txtNotes.setEditable(false);

			removeBypass();
		}

		if (actionCommand.equals("Add Row")) {
			int seq_no = modelWorkItemsMain.getRowCount() + 1;

			modelWorkItemsMain
			.addRow(new Object[] { seq_no, null, null, null, null, null, null, null, null, null, null });

			((DefaultListModel) rowHeaderWorkItemsMain.getModel()).addElement(modelWorkItemsMain.getRowCount());
			updateWorkItem();
		}

		if (actionCommand.equals("Remove Row")) {
			int[] selectedRows = tblWorkItemsMain.getSelectedRows();

			if (selectedRows.length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row in Work Item(s).",
						"Remove Row", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (int x = selectedRows.length - 1; x > -1; x--) {
				int row = selectedRows[x];

				modelWorkItemsMain.removeRow(row);
				((DefaultListModel) rowHeaderWorkItemsMain.getModel()).removeElementAt(row);
			}

			updateWorkItem();
			computeTotal();
		}

		if (actionCommand.equals("AddUnit")) {
			String proj_id = lookupProject.getValue();
			String phase = getSelectedPhase().toString().replaceAll("\\[|\\]", "'").replace(", ", "', '");
			String ntp_type_id = lookupNTPType.getValue();
			String SQL = "";
			Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

			if( lookupCompany.getValue().equals("03") && lookupProject.getValue().equals("020")) {//Added by Erick 2022-01-24 EVE HAS NO PHASE(No need to select phase.)

			}else {
				if (getSelectedPhase().size() == 0 && lookupProject.getValue().equals("018") == false) {

					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select phase.", "Add Unit(s)",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

			}





			if (lookupNTPType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select NTP Type.", "Add Unit(s)",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			/*if (ntp_type_id.equals("06") && tblWorkItemsMain.getRowCount() <= 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please add one row to update.",
						"Add Unit(s)", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			if (ntp_type_id.equals("06") && (tblWorkItemsMain.getSelectedRows().length < 1
					|| tblWorkItemsMain.getSelectedRows().length > 1)) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select one row to update.",
						"Add Unit(s)", JOptionPane.INFORMATION_MESSAGE);
				return;
			}*/


			if (ntp_type_id.equals("02") || ntp_type_id.equals("04")) {
				if (lookupCompany.getValue().equals("02")) {
					SQL = _NoticeToProceed.AddUnits(proj_id, phase, ntp_type_id, null);
				} else if (lookupCompany.getValue().equals("03")) {
					SQL = _NoticeToProceed.AddUnitsSRDC(proj_id, phase, ntp_type_id, null);
					System.out.println("Dumaan dito sa SRDC");
				} else {
					SQL = _NoticeToProceed.AddUnits(proj_id, phase, ntp_type_id, null);
				}
			} else {
				int cse_type = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Please select CSE Type",
						"Add Unit", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option_cse_type,
						option_cse_type[0]);
				SQL = _NoticeToProceed.AddSpecialProjectUnits(proj_id, phase, ntp_type_id,
						(String) option_cse_type[cse_type], map);
			}

			FncSystem.out("Display SQL for AddUnits", SQL);

			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Units", SQL, true);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[][] data = dlg.getResult();
			try {
				for (int x = 0; x < data.length; x++) {
					Integer unit_id = (Integer) data[x][0];
					String description = (String) data[x][1];
					String model = (String) data[x][2];
					BigDecimal amount = (BigDecimal) data[x][3];

					modelWorkItemsMain.addRow(new Object[] { x + 1, model, "UNIT", new BigDecimal("1"), amount, amount,
							amount, unit_id, description, null, null });
					((DefaultListModel) rowHeaderWorkItemsMain.getModel()).addElement(modelWorkItemsMain.getRowCount());
				}

				updateWorkItem();
				computeTotal();
			} catch (NullPointerException e) {

			}
		}

		if (actionCommand.equals("Bypass")) {
			String strContracts = "";
			pgUpdate dbExec = new pgUpdate();
			String strSQL = "select false::boolean as tag, contract_no, ntp_date \n" + "from co_ntp_header \n"
					+ "where status_id = 'A' \n" + "order by contract_no desc, ntp_date desc";

			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Contract Units", strSQL, true);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[][] data = dlg.getResult();

			for (int x = 0; x < data.length; x++) {
				strContracts = strContracts + "*	" + data[x][0].toString() + "\n";
			}

			if (JOptionPane.showConfirmDialog(null,
					"The unit(s) from the selected contracts will \nbe included. Proceed?\n\n" + strContracts,
					"Bypass Options", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				for (int x = 0; x < data.length; x++) {
					System.out.println("data: " + data[x][0].toString());

					dbExec = new pgUpdate();
					dbExec.executeUpdate(
							"insert into rf_ntp_unit_bypass (pbl_id, description, unit_id, created_by, date_created, status_id)\n"
									+ "select x.pbl_id, z.description, z.unit_id, '" + UserInfo.EmployeeCode
									+ "', now(), 'A'\n" + "from co_ntp_detail x\n"
									+ "inner join co_ntp_header y on x.ntp_no = y.ntp_no\n"
									+ "inner join mf_unit_info z on x.pbl_id = z.pbl_id\n" + "where y.contract_no = '"
									+ data[x][0].toString() + "'",
									true);
					dbExec.commit();
				}

				JOptionPane.showMessageDialog(null, "Unit bypass complete!");
			}
		}
	}

	// saving
	private void updateTO() {

		pgUpdate db = new pgUpdate();
		String sqlDetail = "update co_ntp_header " + "set is_takeover_ntp = true," + "takenover_ntpno = '" + ntp_no
				+ "' " + "where trim(ntp_no) = '" + lookupNTPNo.getText() + "'  ";
		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
		db.commit();

	}

	private void displayTO(String x) {

		String a = "";
		String SQL = "select contract_no from co_ntp_header "
				+ "where ntp_no in (select takenover_ntpno from co_ntp_header where ntp_no = '" + x + "') ";

		System.out.printf("SQL #1: sql_getTakenOverContract", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = "Taken Over : " + (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		tagTO.setTag(a);

	}

	private void removeBypass() {
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("update rf_ntp_unit_bypass \n" + "set status_id = 'I'\n" + "where status_id = 'A'", false);
		dbExec.commit();
	}

	private String convertToWords(String strNum) {
		String strConverted = "";

		System.out.println("DP: " + formatter.format(new BigDecimal(strNum)));
		strConverted = EnglishNumberTranslator.convert(formatter.format(new BigDecimal(strNum)));
		System.out.println(strConverted);

		return strConverted;
	}

	private void saveEditNtpNotes() {
		if (tabCenter.getSelectedIndex() == 2) {
			String ntp_no = lookupNTPNo.getValue();
			String contract_no = txtContractNo.getText();
			String remarks = txtNotes.getText();
			pgSelect db = new pgSelect();

			String query = "SELECT sp_save_ntp_notes('" + ntp_no + "','" + contract_no + "','" + UserInfo.Department
					+ "','" + remarks + "','" + UserInfo.EmployeeCode + "')";
			db.select(query);
		}
	}
}
