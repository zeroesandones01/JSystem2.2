package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.NewsAddress;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTable.BooleanEditor;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import Accounting.Commissions.Commission_Schedule_Generator;
import Accounting.Commissions.CreateCommissionSchedule;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Dialogs.dlgDM_RRDocuments;
import Dialogs.dlg_DesiredAmount;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.ButtonCell;
import components._ButtonGroup;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelCARD_Ledger;
import tablemodel.modelDM_AllDocuments;
import tablemodel.modelDM_Coapp_Submitted_Docs;
import tablemodel.modelDM_DocumentsForPrinting;
import tablemodel.modelDM_Findings;
import tablemodel.modelDM_Payments;
import tablemodel.modelDM_SubmittedDocuments;

public class DocumentsMonitoring extends _JInternalFrame implements _GUI, ActionListener, AncestorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9133445162295128006L;
	static String title = "Documents Monitoring";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Dimension frameSize = new Dimension(900, 550);

	// XXX
	private _JTabbedPane tabCenter;
	private _JTabbedPane tabSubmittedDocuments;
	private JButton btnSubmitDocuments;
	private JCheckBox chkDisplayOnlyRequiredDocs;
	private JButton btnSave;
	private JButton btnCancel;

	private _JLookup lookupClient;
	private _JXTextField txtBuyerType;
	private _JXTextField txtCurrentStatus;
	private _JXTextField txtReservation;
	private _JXTextField txtOfficialReservation;
	private _JXTextField txtBusinessClass;
	private _JLookup lookupCOApplicant;

	private _JXTextField txtClientName;
	private _JXTextField txtBuyerTypeName;
	private _JXTextField txtCurrentStatusName;
	private _JXTextField txtBusinessClassName;
	private _JXTextField txtCoapplicant;

	private _JXFormattedTextField txtDP;
	private _JXFormattedTextField txtDesiredAmount;

	private _JXTextField txtCompanyID;
	private _JXTextField txtProjectID;
	private _JXTextField txtUnitID;
	private _JXTextField txtModelID;
	private _JXTextField txtSequence;

	private _JXTextField txtCompanyName;
	private _JXTextField txtProjectName;
	private _JXTextField txtUnitDescription;
	private _JXTextField txtModelName;

	private JScrollPane scrollAllDocuments;
	private _JTableMain tblAllDocuments;
	private modelDM_AllDocuments modelAllDocuments;
	private JList rowHeaderAllDocuments;

	private JTextField txtReceivedFrom;
	private JTextField txtReceivedBy;
	private _JDateChooser dateReceived;

	private JScrollPane scrollSubmittedDocuments;
	private _JTableMain tblSubmittedDocuments;
	private modelDM_SubmittedDocuments modelSubmittedDocuments;
	private JList rowHeaderSubmittedDocuments;

	private JScrollPane scrollCoappSubmittedDocs;
	private _JTableMain tblCoappSubmittedDocs;
	private modelDM_Coapp_Submitted_Docs modelCoappSubmittedDocs;
	private JList rowHeaderCoappSubmittedDocs;

	private JButton btnPreview;

	private JButton btnSaveDocDetails;
	private JButton btnReleaseDocuments;
	private JButton btnReturnDocuments;

	private JScrollPane scrollDocumentsForPrinting;
	private _JTableMain tblDocumentsForPrinting;
	private modelDM_DocumentsForPrinting modelDocumentsForPrinting;
	private JList rowHeaderDocumentsForPrinting;

	private JCheckBox chkFullDPEquity;
	private _JDateChooser dateFullDPEquity;
	private JCheckBox chkOKMinorDocs;
	private _JDateChooser dateOKMinorDocs;
	private JCheckBox chkAccountsComeplete;
	private _JDateChooser dateAccountsComeplete;
	private JCheckBox chkReturnDocuments;
	private _JDateChooser dateReturnDocuments;

	private _JScrollPaneMain scrollPayment;
	private _JTableMain tblPayment;
	private modelDM_Payments modelPayment;
	private JList rowheaderPayment;

	private _JScrollPaneTotal scrollPaymentTotal;
	private _JTableTotal tblPaymentTotal;
	private modelDM_Payments modelPaymentTotal;

	private JScrollPane scrollLedger;
	private _JTableMain tblLedger;
	private modelCARD_Ledger modelLedger;
	private JList rowheaderLedger;

	private JButton btnDC_New;
	private JButton btnDC_Edit;
	private JButton btnDC_Delete;
	private JButton btnDC_Save;
	private JButton btnDC_Cancel;

	// private JTextField txtReceivedBy;

	private JPanel pnlORDocumentsEvaluationNorth;

	private _JLookup lookupFindings;
	private _JXTextField txtFindings;

	private JPanel pnlOtherFindings;
	private JScrollPane scrollOtherFindings;
	private JXTextArea txtOtherFindings;

	private JScrollPane scrollFindings;
	private _JTableMain tblFindings;
	private modelDM_Findings modelFindings;
	private JList rowHeaderFindings;

	private JPanel pnlFindingsOKBy;
	private _JXTextField txtOKBy;

	private JButton btnFindingsNew;
	private JButton btnFindingsEdit;
	private JButton btnFindingsSave;
	private JButton btnFindingsDelete;
	private JButton btnFindingsCancel;
	private _ButtonGroup groupFindings = new _ButtonGroup();

	private ArrayList<Object[]> listData;
	private ArrayList<String> listSelected = new ArrayList<String>();

	private ArrayList<Object[]> listDataCoapp;
	private ArrayList<String> listSelectedCoapp = new ArrayList<String>();

	public DocumentsMonitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public DocumentsMonitoring(Object[] data) {
		super(title, true, true, true, true);
		initGUI();

		displayClientInformationDetails2(data);
	}

	public DocumentsMonitoring(String entity_id, String proj_id, String unit_id, Integer seq_no) {
		super(title, true, true, true, true);
		initGUI();

		displayClientInformationDetails(entity_id, proj_id, unit_id, seq_no);
	}

	public DocumentsMonitoring(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public DocumentsMonitoring(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		/* this.setMinimumSize(frameSize); */
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);

		// disabled due to unnecessary icon on toolbar
		/*
		 * Image iconInternalFrame = new
		 * ImageIcon(FncLookAndFeel.class.getClassLoader().getResource(
		 * "Images/buyers/documentsmonitoring.png")).getImage(); iconInternalFrame =
		 * iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
		 * this.setFrameIcon(new ImageIcon(iconInternalFrame));
		 */

		JPanel pnlMain = new JPanel(new BorderLayout());
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(EMPTY_BORDER);
		{
			// JPanel pnlNorth = new JPanel(new GridLayout(1, 2, 3, 3)/*new BorderLayout(3,
			// 3)*/);
			JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			// pnlNorth.setBorder(LINE_BORDER);
			pnlNorth.setPreferredSize(new Dimension(0, 200));
			{
				JPanel pnlNorthCenter = new JPanel(new BorderLayout(3, 3));
				// pnlNorth.add(pnlNorthCenter/*, BorderLayout.CENTER*/);
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				pnlNorthCenter.setBorder(components.JTBorderFactory.createTitleBorder("Client Information"));
				{
					JPanel pnlCILabels = new JPanel(new GridLayout(7, 1));
					pnlNorthCenter.add(pnlCILabels, BorderLayout.WEST);
					// pnlCILabels.setBorder(LINE_BORDER);
					pnlCILabels.setPreferredSize(new Dimension(150, 0));
					{
						JLabel lblClient = new JLabel("Client");
						pnlCILabels.add(lblClient);
					}
					{
						JLabel lblBuyerType = new JLabel("Buyer Type");
						pnlCILabels.add(lblBuyerType);
					}
					{
						JLabel lblCurrentStatus = new JLabel("Current Status");
						pnlCILabels.add(lblCurrentStatus);
					}
					{
						JLabel lblReservationDate = new JLabel("RES Date");
						pnlCILabels.add(lblReservationDate);
						lblReservationDate.setToolTipText("Reservation Date");
					}
					/*
					 * { JLabel lblORDate = new JLabel("OR Date"); pnlCILabels.add(lblORDate);
					 * lblORDate.setToolTipText("Official Reservation Date"); }
					 */
					{
						JLabel lblBusinessClass = new JLabel("Business Class");
						pnlCILabels.add(lblBusinessClass);
					}
					{
						JLabel lblCoapplicant = new JLabel("Coapplicant");
						pnlCILabels.add(lblCoapplicant);
					}
					{
						JLabel lblDesiredAmount = new JLabel("Coapplicant Desired Loan Amount");
						pnlCILabels.add(lblDesiredAmount);
					}
				}
				{
					JPanel pnlCICenter = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlCICenter, BorderLayout.CENTER);
					{
						JPanel pnlCILookup = new JPanel(new GridLayout(7, 1, 3, 3));
						pnlCICenter.add(pnlCILookup, BorderLayout.WEST);
						pnlCILookup.setPreferredSize(new Dimension(120, 0));
						{
							lookupClient = new _JLookup(null, "Client", 0);
							pnlCILookup.add(lookupClient);
							lookupClient.setPrompt("ID");
							// lookupClient.setFilterName(true);
							lookupClient.setFilterCardName(true);
							lookupClient.setLookupSQL(_CARD.sqlClients());// _DocumentsMonitoring.sqlClients()
							lookupClient.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {

									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {// XXX Lookup Client
										String entity_id = (String) data[1];
										String proj_id = (String) data[7];
										String unit_id = (String) data[4];
										Integer seq_no = (Integer) data[5];
										BigDecimal desiredAmount = (BigDecimal) txtDesiredAmount.getValued();

										displayClientInformationDetails(entity_id, proj_id, unit_id, seq_no);
										lookDesiredAmountValue();

										if (chkDisplayOnlyRequiredDocs.isSelected()) {
											displayRecquiredDocs(true);
										}
										// if(desiredAmount.equals(null)||desiredAmount.equals("")||desiredAmount.equals("null"))
										// {
										// txtDesiredAmount.setValue("0.00");
										// txtDesiredAmount.setEnabled(false);
										// }

										lookupCOApplicant.setValue(null);
										txtCoapplicant.setText("");
										lookupCOApplicant.setLookupSQL(_DocumentsMonitoring.sqlCoapplicant(entity_id));
									}
								}
							});
							// System.out.printf("Font: %s%n", lookupClient.getFont());
						}
						{
							txtBuyerType = new _JXTextField("ID");
							pnlCILookup.add(txtBuyerType);
							txtBuyerType.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtCurrentStatus = new _JXTextField("ID");
							pnlCILookup.add(txtCurrentStatus);
							txtCurrentStatus.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtReservation = new _JXTextField();
							pnlCILookup.add(txtReservation, BorderLayout.WEST);
							txtReservation.setHorizontalAlignment(JXTextField.CENTER);
							txtReservation.setPreferredSize(new Dimension(80, 0));
						}
						{
							txtBusinessClass = new _JXTextField();
							pnlCILookup.add(txtBusinessClass);
							txtBusinessClass.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lookupCOApplicant = new _JLookup(null, "Coapplicant", 0, "Please select Client First");
							pnlCILookup.add(lookupCOApplicant);
							lookupCOApplicant.setFilterName(true);
							lookupCOApplicant.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									FncSystem.out("Display SQL Lookup For Coapplicant",
											lookupCOApplicant.getLookupSQL());

									if (data != null) {
										String entity_id = lookupClient.getValue();
										String coapplicant_id = (String) data[0];
										String coapplicant_name = (String) data[1];
										String proj_id = txtProjectID.getText();
										String pbl_id = txtUnitID.getText();
										Integer seq_no = Integer.parseInt(txtSequence.getText());
										String buyertype_id = txtBuyerType.getText();

										txtCoapplicant.setText(coapplicant_name);

										displayCoAppDocuments(entity_id, proj_id, pbl_id, seq_no, coapplicant_id,
												buyertype_id, false);
										displayCoAppSubmittedDocuments(entity_id, proj_id, pbl_id, seq_no,
												coapplicant_id);

										if (chkDisplayOnlyRequiredDocs.isSelected()) {
											displayRecquiredDocs(true);
										}
									}
								}
							});
						}
						{
							txtDesiredAmount = new _JXFormattedTextField();
							pnlCILookup.add(txtDesiredAmount);
							txtDesiredAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtDesiredAmount.setValue(new BigDecimal("0.00"));
							txtDesiredAmount.setEnabled(false);
						}
					}
					{
						JPanel pnlCITexts = new JPanel(new GridLayout(7, 1, 3, 3));
						pnlCICenter.add(pnlCITexts, BorderLayout.CENTER);
						{
							txtClientName = new _JXTextField();
							pnlCITexts.add(txtClientName);
						}
						{
							txtBuyerTypeName = new _JXTextField();
							pnlCITexts.add(txtBuyerTypeName);
						}
						{
							txtCurrentStatusName = new _JXTextField();
							pnlCITexts.add(txtCurrentStatusName);
						}
						{
							JPanel pnlOfficialReservation = new JPanel(new BorderLayout(3, 3));
							pnlCITexts.add(pnlOfficialReservation);
							{
								JLabel lblORDate = new JLabel("OR Date", JLabel.TRAILING);
								pnlOfficialReservation.add(lblORDate);
								lblORDate.setToolTipText("Official Reservation Date");
							}
							{
								txtOfficialReservation = new _JXTextField();
								pnlOfficialReservation.add(txtOfficialReservation, BorderLayout.EAST);
								txtOfficialReservation.setHorizontalAlignment(JXTextField.CENTER);
								txtOfficialReservation.setPreferredSize(new Dimension(80, 0));
							}
						}
						/*
						 * { pnlCITexts.add(Box.createHorizontalGlue()); }
						 */
						{
							txtBusinessClassName = new _JXTextField();
							pnlCITexts.add(txtBusinessClassName);
						}
						{
							txtCoapplicant = new _JXTextField();
							pnlCITexts.add(txtCoapplicant);
						}
					}
				}
			}
			{
				JPanel pnlNorthEast = new JPanel(new BorderLayout(3, 0));
				// pnlNorth.add(pnlNorthEast/*, BorderLayout.EAST*/);
				pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
				pnlNorthEast.setBorder(components.JTBorderFactory.createTitleBorder("Unit Information"));
				pnlNorthEast.setPreferredSize(new Dimension(380, 119));
				{
					JPanel pnlUnitWest = new JPanel(new BorderLayout(5, 3));
					pnlNorthEast.add(pnlUnitWest, BorderLayout.WEST);
					pnlUnitWest.setPreferredSize(new Dimension(100, 0));
					{
						JPanel pnlUnitLabels = new JPanel(new GridLayout(6, 1, 3, 3));
						pnlUnitWest.add(pnlUnitLabels, BorderLayout.WEST);
						{
							JLabel lblCompany = new JLabel("Company");
							pnlUnitLabels.add(lblCompany);
						}
						{
							JLabel lblProject = new JLabel("Project");
							pnlUnitLabels.add(lblProject);
						}
						{
							JLabel lblUnit = new JLabel("Unit");
							pnlUnitLabels.add(lblUnit);
						}
						{
							JLabel lblModel = new JLabel("Model");
							pnlUnitLabels.add(lblModel);
						}
						{
							JLabel lblSequence = new JLabel("Seq.");
							pnlUnitLabels.add(lblSequence);
						}
					}
					{
						JPanel pnlUnitLookups = new JPanel(new GridLayout(6, 1, 3, 3));
						pnlUnitWest.add(pnlUnitLookups, BorderLayout.CENTER);
						{
							txtCompanyID = new _JXTextField();
							pnlUnitLookups.add(txtCompanyID);
							txtCompanyID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtProjectID = new _JXTextField();
							pnlUnitLookups.add(txtProjectID);
							txtProjectID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtUnitID = new _JXTextField();
							pnlUnitLookups.add(txtUnitID);
							txtUnitID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtModelID = new _JXTextField();
							pnlUnitLookups.add(txtModelID);
							txtModelID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtSequence = new _JXTextField();
							pnlUnitLookups.add(txtSequence);
							txtSequence.setHorizontalAlignment(JXTextField.CENTER);
						}
					}
				}
				{
					JPanel pnlUnitTextfields = new JPanel(new GridLayout(6, 1, 3, 3));
					pnlNorthEast.add(pnlUnitTextfields, BorderLayout.CENTER);
					{
						txtCompanyName = new _JXTextField();
						pnlUnitTextfields.add(txtCompanyName);
					}
					{
						txtProjectName = new _JXTextField();
						pnlUnitTextfields.add(txtProjectName);
					}
					{
						txtUnitDescription = new _JXTextField();
						pnlUnitTextfields.add(txtUnitDescription);
					}
					{
						txtModelName = new _JXTextField();
						pnlUnitTextfields.add(txtModelName);
					}
				}
			}
		}
		{
			tabCenter = new _JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);
			{
				JPanel pnlAllDocuments = new JPanel(new BorderLayout(5, 5));
				tabCenter.addTab("All Documents", null, pnlAllDocuments, null);
				{
					JPanel pnlAllDocumentsCenter = new JPanel(new BorderLayout(5, 5));
					pnlAllDocuments.add(pnlAllDocumentsCenter, BorderLayout.CENTER);
					{
						scrollAllDocuments = new JScrollPane();
						pnlAllDocumentsCenter.add(scrollAllDocuments, BorderLayout.CENTER);
						scrollAllDocuments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollAllDocuments.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblAllDocuments.clearSelection();
							}
						});
						{
							modelAllDocuments = new modelDM_AllDocuments();
							modelAllDocuments.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									// Addition of rows
									if (e.getType() == 1) {
										((DefaultListModel) rowHeaderAllDocuments.getModel())
												.addElement(modelAllDocuments.getRowCount());

										if (modelAllDocuments.getRowCount() == 0) {
											rowHeaderAllDocuments.setModel(new DefaultListModel());
										}
									}
								}
							});

							tblAllDocuments = new _JTableMain(modelAllDocuments);
							scrollAllDocuments.setViewportView(tblAllDocuments);
							tblAllDocuments.hideColumns("ID", "Alias", "Required");

							tblAllDocuments.setDefaultRenderer(JButton.class,
									new ButtonCell((JFrame) this.getTopLevelAncestor(), 8));
							tblAllDocuments.setDefaultEditor(JButton.class,
									new ButtonCell((JFrame) this.getTopLevelAncestor(), 8));
							tblAllDocuments.setSortable(false);
							// tblAllDocuments.setEditable(false);

							// tblAllDocuments.setEditable(false);

							tblAllDocuments.addPropertyChangeListener(new PropertyChangeListener() {// XXX Work Items
								public void propertyChange(PropertyChangeEvent arg0) {
									_JTableMain table = (_JTableMain) arg0.getSource();
									String propertyName = arg0.getPropertyName();

									if (propertyName.equals("tableCellEditor")) {
										int column = table.convertColumnIndexToModel(table.getEditingColumn());
										int row = table.getEditingRow();

										// setting of selected documents when selecting
										if (column != -1
												&& modelAllDocuments.getColumnClass(column).equals(Boolean.class)) {
											Object oldValue = null;
											try {
												oldValue = ((BooleanEditor) arg0.getOldValue()).getCellEditorValue();
											} catch (NullPointerException e) {
											}

											if (oldValue != null) {
												String docID = (String) modelAllDocuments.getValueAt(row, 2);
												listSelected.add(docID);
											}
										}

										// setting of number of copies when editing
										if (column != -1
												&& modelAllDocuments.getColumnClass(column).equals(Integer.class)) {
											Object oldValue = null;
											try {
												oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
												if (oldValue != null) {
													String docID = (String) modelAllDocuments.getValueAt(row, 2);
													for (int x = 0; x < listData.size(); x++) {
														if (listData.get(x)[2].equals(docID)) {
															listData.get(x)[1] = oldValue;
														}
													}
												}
											} catch (NullPointerException e) {
											}
										}
									}
								}
							});
						}
						{
							rowHeaderAllDocuments = tblAllDocuments.getRowHeader();
							rowHeaderAllDocuments.setModel(new DefaultListModel());
							scrollAllDocuments.setRowHeaderView(rowHeaderAllDocuments);
							scrollAllDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
							// scrollAllDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
							// FncTables.getRowHeader_Footer(Integer.toString(tblAllDocuments.getRowCount())));
						}
					}
					{
						JPanel pnlAllDocumentsNavigation = new JPanel(new GridLayout(1, 3, 3, 3));
						pnlAllDocumentsCenter.add(pnlAllDocumentsNavigation, BorderLayout.SOUTH);
						pnlAllDocumentsNavigation.setPreferredSize(new Dimension(0, 35));
						pnlAllDocumentsNavigation.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
						{
							JPanel pnlReceivedBy = new JPanel(new BorderLayout(10, 0));
							// pnlAllDocumentsNavigation.add(pnlReceivedBy, BorderLayout.NORTH);
							pnlReceivedBy.setPreferredSize(new Dimension(773, 20));
							// pnlReceivedBy.setBorder(LINE_BORDER);
							{
								JLabel lblReceivedBy = new JLabel("Received By:");
								pnlReceivedBy.add(lblReceivedBy, BorderLayout.WEST);
							}
							/*
							 * { txtReceivedBy = new JTextField(); pnlReceivedBy.add(txtReceivedBy,
							 * BorderLayout.CENTER); }
							 */
						}
						{
							chkDisplayOnlyRequiredDocs = new JCheckBox("Display Only Required Documents");
							pnlAllDocumentsNavigation.add(chkDisplayOnlyRequiredDocs, BorderLayout.WEST);
							chkDisplayOnlyRequiredDocs.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent arg0) {
									Boolean isSelected = (arg0.getStateChange() == ItemEvent.SELECTED);

									displayRecquiredDocs(isSelected);

								}
							});
						}
						// ADDED BY JLF 2016-05-20
						/*
						 * { JPanel pnlSearchDocument = new JPanel(new BorderLayout(3, 3));
						 * pnlAllDocumentsNavigation.add(pnlSearchDocument); { JLabel lblSearchDocument
						 * = new JLabel("Search Document"); pnlSearchDocument.add(lblSearchDocument,
						 * BorderLayout.WEST); } { JTextField txtSearchDocument = new JTextField();
						 * pnlSearchDocument.add(txtSearchDocument, BorderLayout.CENTER);
						 * txtSearchDocument.addKeyListener(new KeyAdapter() { public void
						 * keyReleased(KeyEvent e) { String search_document = ((JTextField)
						 * e.getSource()).getText();
						 * 
						 * searchDocument(search_document); } }); } }
						 */
						{
							btnSubmitDocuments = new JButton("Submit Documents");
							pnlAllDocumentsNavigation.add(btnSubmitDocuments, BorderLayout.EAST);
							btnSubmitDocuments.addActionListener(this);
						}
					}
				}
				{
					JPanel pnlEast = new JPanel(new BorderLayout());
					pnlAllDocuments.add(pnlEast, BorderLayout.EAST);
					pnlEast.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 3));
					pnlEast.setPreferredSize(new Dimension(221, 223));
					{
						JPanel pnlOtherDetails = new JPanel(new GridLayout(10, 1, 3, 3));
						pnlEast.add(pnlOtherDetails, BorderLayout.CENTER);
						pnlOtherDetails.setBorder(components.JTBorderFactory.createTitleBorder("Other Doc. Details"));
						{
							JLabel lblReceiveFrom = new JLabel("Received From");
							pnlOtherDetails.add(lblReceiveFrom);
						}
						{
							txtReceivedFrom = new JTextField();
							pnlOtherDetails.add(txtReceivedFrom);
						}
						{
							JLabel lblReceivedBy = new JLabel("Received By");
							pnlOtherDetails.add(lblReceivedBy);
						}
						{
							txtReceivedBy = new JTextField();
							pnlOtherDetails.add(txtReceivedBy);
						}
						{
							JLabel lblDateReceived = new JLabel("Date Received");
							pnlOtherDetails.add(lblDateReceived);
						}
						{
							dateReceived = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlOtherDetails.add(dateReceived);
							dateReceived.setEnabled(false);
							dateReceived.setDate(Calendar.getInstance().getTime());
						}
						/*
						 * { JPanel pnl1 = new JPanel(new BorderLayout(3, 3));
						 * pnlOtherDetails.add(pnl1);
						 * //pnl1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); } {
						 * JPanel pnl2 = new JPanel(new BorderLayout(3, 3)); pnlOtherDetails.add(pnl2);
						 * //pnl2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); }
						 */
					}
				}
			}
			{
				JPanel pnlSubmittedDocsCenter = new JPanel(new BorderLayout(3, 3));
				tabCenter.addTab("Submitted Docs", null, pnlSubmittedDocsCenter, null);
				{
					tabSubmittedDocuments = new _JTabbedPane();
					pnlSubmittedDocsCenter.add(tabSubmittedDocuments, BorderLayout.CENTER);
					{
						JPanel pnlPASubmittedDocs = new JPanel(new BorderLayout(3, 3));
						tabSubmittedDocuments.addTab("PA Submitted Docs", null, pnlPASubmittedDocs, null);
						{
							scrollSubmittedDocuments = new JScrollPane();
							pnlPASubmittedDocs.add(scrollSubmittedDocuments, BorderLayout.CENTER);
							scrollSubmittedDocuments
									.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollSubmittedDocuments.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									tblSubmittedDocuments.clearSelection();
								}
							});
							{
								modelSubmittedDocuments = new modelDM_SubmittedDocuments();
								modelSubmittedDocuments.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										// Addition of rows
										if (e.getType() == 1) {
											((DefaultListModel) rowHeaderSubmittedDocuments.getModel())
													.addElement(modelSubmittedDocuments.getRowCount());

											if (modelSubmittedDocuments.getRowCount() == 0) {
												rowHeaderSubmittedDocuments.setModel(new DefaultListModel());
											}
										}
									}
								});

								tblSubmittedDocuments = new _JTableMain(modelSubmittedDocuments);
								scrollSubmittedDocuments.setViewportView(tblSubmittedDocuments);
								tblSubmittedDocuments.hideColumns("ID", "Docs-OUT", "Docs-IN", "Remarks", "Date Issued",
										"Validity");
								tblSubmittedDocuments.setDefaultRenderer(JButton.class,
										new ButtonCell((JFrame) this.getTopLevelAncestor(), 11));
								tblSubmittedDocuments.setDefaultEditor(JButton.class,
										new ButtonCell((JFrame) this.getTopLevelAncestor(), 11));
								tblSubmittedDocuments.setSortable(false);

								tblSubmittedDocuments.addPropertyChangeListener(new PropertyChangeListener() {// XXX
																												// Work
																												// Items
									public void propertyChange(PropertyChangeEvent arg0) {
										_JTableMain table = (_JTableMain) arg0.getSource();
										String propertyName = arg0.getPropertyName();

										if (propertyName.equals("tableCellEditor")) {
											int column = table.convertColumnIndexToModel(table.getEditingColumn());
											int row = table.getEditingRow();

											if (column != -1 && modelSubmittedDocuments.getColumnClass(column)
													.equals(Boolean.class)) {
												Object oldValue = null;
												try {
													oldValue = ((BooleanEditor) arg0.getOldValue())
															.getCellEditorValue();
												} catch (NullPointerException e) {
												}

												if (oldValue != null) {
													String docID = (String) modelSubmittedDocuments.getValueAt(row, 2);
													listSelected.add(docID);
												}
											}

											// setting of number of copies when editing
											if (column != -1 && modelSubmittedDocuments.getColumnClass(column)
													.equals(Integer.class)) {
												Object oldValue = null;
												try {
													oldValue = ((NumberEditorExt) arg0.getOldValue())
															.getCellEditorValue();
													if (oldValue != null && ((Integer) oldValue) > 0) {
														String docID = (String) modelSubmittedDocuments.getValueAt(row,
																2);
														for (int x = 0; x < listData.size(); x++) {
															if (listData.get(x)[2].equals(docID)) {
																listData.get(x)[1] = oldValue;
															}
														}
													} else {
														modelSubmittedDocuments.setValueAt(1, row, 1);
														String docID = (String) modelSubmittedDocuments.getValueAt(row,
																2);
														for (int x = 0; x < listData.size(); x++) {
															if (listData.get(x)[2].equals(docID)) {
																listData.get(x)[1] = oldValue;
															}
														}
													}
												} catch (NullPointerException e) {
												}
											}
										}
									}
								});
							}
							{
								rowHeaderSubmittedDocuments = tblSubmittedDocuments.getRowHeader();
								rowHeaderSubmittedDocuments.setModel(new DefaultListModel());
								scrollSubmittedDocuments.setRowHeaderView(rowHeaderSubmittedDocuments);
								scrollSubmittedDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
								scrollSubmittedDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables
										.getRowHeader_Footer(Integer.toString(tblSubmittedDocuments.getRowCount())));
							}
						}
						/*
						 * { JPanel pnlSubmittedDocsSouth = new JPanel(new BorderLayout());
						 * pnlPASubmittedDocs.add(pnlSubmittedDocsSouth, BorderLayout.SOUTH);
						 * pnlSubmittedDocsSouth.setPreferredSize(new Dimension(0, 40));
						 * pnlSubmittedDocsSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						 * { btnPreview = new JButton("Preview"); pnlSubmittedDocsSouth.add(btnPreview,
						 * BorderLayout.WEST); btnPreview.addActionListener(this);
						 * btnPreview.setPreferredSize(new Dimension(100, 0)); } { JPanel
						 * pnlSubmittedDocsSouthEast = new JPanel(new GridLayout(1, 3, 5, 5));
						 * pnlSubmittedDocsSouth.add(pnlSubmittedDocsSouthEast, BorderLayout.EAST);
						 * pnlSubmittedDocsSouthEast.setPreferredSize(new Dimension(345, 0)); {
						 * btnReturnDocuments = new JButton("Docs-IN");
						 * pnlSubmittedDocsSouthEast.add(btnReturnDocuments);
						 * btnReturnDocuments.addActionListener(this); } { btnReleaseDocuments = new
						 * JButton("Docs-OUT"); pnlSubmittedDocsSouthEast.add(btnReleaseDocuments);
						 * btnReleaseDocuments.addActionListener(this); } } }
						 */
					}
					{
						JPanel pnlCoappSubmittedDocs = new JPanel(new BorderLayout(3, 3));
						tabSubmittedDocuments.addTab("Coapp Submitted Documents", null, pnlCoappSubmittedDocs, null);
						{
							scrollCoappSubmittedDocs = new JScrollPane();
							pnlCoappSubmittedDocs.add(scrollCoappSubmittedDocs, BorderLayout.CENTER);
							scrollCoappSubmittedDocs
									.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollCoappSubmittedDocs.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									tblCoappSubmittedDocs.clearSelection();
								}
							});
							{
								modelCoappSubmittedDocs = new modelDM_Coapp_Submitted_Docs();
								modelCoappSubmittedDocs.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										// Addition of rows
										if (e.getType() == 1) {
											((DefaultListModel) rowHeaderCoappSubmittedDocs.getModel())
													.addElement(modelCoappSubmittedDocs.getRowCount());

											if (modelCoappSubmittedDocs.getRowCount() == 0) {
												rowHeaderCoappSubmittedDocs.setModel(new DefaultListModel());
											}
										}
									}
								});

								tblCoappSubmittedDocs = new _JTableMain(modelCoappSubmittedDocs);
								scrollCoappSubmittedDocs.setViewportView(tblCoappSubmittedDocs);
								tblCoappSubmittedDocs.hideColumns("ID", "Doc-OUT", "Docs-IN", "Date Issued",
										"Validity");
								tblCoappSubmittedDocs.setDefaultRenderer(JButton.class,
										new ButtonCell((JFrame) this.getTopLevelAncestor(), 11));
								tblCoappSubmittedDocs.setDefaultEditor(JButton.class,
										new ButtonCell((JFrame) this.getTopLevelAncestor(), 11));

								tblCoappSubmittedDocs.addPropertyChangeListener(new PropertyChangeListener() {// XXX
																												// Work
																												// Items
									public void propertyChange(PropertyChangeEvent arg0) {
										_JTableMain table = (_JTableMain) arg0.getSource();
										String propertyName = arg0.getPropertyName();

										if (propertyName.equals("tableCellEditor")) {
											int column = table.convertColumnIndexToModel(table.getEditingColumn());
											int row = table.getEditingRow();

											// setting of selected documents when selecting
											if (column != -1 && modelCoappSubmittedDocs.getColumnClass(column)
													.equals(Boolean.class)) {
												Object oldValue = null;
												try {
													oldValue = ((BooleanEditor) arg0.getOldValue())
															.getCellEditorValue();
												} catch (NullPointerException e) {
												}

												if (oldValue != null) {
													String docID = (String) modelCoappSubmittedDocs.getValueAt(row, 2);
													listSelectedCoapp.add(docID);
												}
											}

											// setting of number of copies when editing
											if (column != -1 && modelCoappSubmittedDocs.getColumnClass(column)
													.equals(Integer.class)) {
												Object oldValue = null;
												try {
													oldValue = ((NumberEditorExt) arg0.getOldValue())
															.getCellEditorValue();
													if (oldValue != null && ((Integer) oldValue) > 0) {
														String docID = (String) modelCoappSubmittedDocs.getValueAt(row,
																2);
														for (int x = 0; x < listDataCoapp.size(); x++) {
															if (listDataCoapp.get(x)[2].equals(docID)) {
																listDataCoapp.get(x)[1] = oldValue;
															}
														}
													} else {
														modelSubmittedDocuments.setValueAt(1, row, 1);
														String docID = (String) modelSubmittedDocuments.getValueAt(row,
																2);
														for (int x = 0; x < listData.size(); x++) {
															if (listData.get(x)[2].equals(docID)) {
																listData.get(x)[1] = oldValue;
															}
														}
													}
												} catch (NullPointerException e) {
												}
											}
										}
									}
								});
							}
							{
								rowHeaderCoappSubmittedDocs = tblCoappSubmittedDocs.getRowHeader();
								rowHeaderCoappSubmittedDocs.setModel(new DefaultListModel());
								scrollCoappSubmittedDocs.setRowHeaderView(rowHeaderCoappSubmittedDocs);
								scrollCoappSubmittedDocs.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
								scrollCoappSubmittedDocs.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables
										.getRowHeader_Footer(Integer.toString(tblCoappSubmittedDocs.getRowCount())));
							}
						}
					}
				}
				{
					JPanel pnlSubmittedDocsSouth = new JPanel(new BorderLayout());
					pnlSubmittedDocsCenter.add(pnlSubmittedDocsSouth, BorderLayout.SOUTH);
					pnlSubmittedDocsSouth.setPreferredSize(new Dimension(0, 40));
					pnlSubmittedDocsSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						btnPreview = new JButton("Preview");
						pnlSubmittedDocsSouth.add(btnPreview, BorderLayout.WEST);
						btnPreview.addActionListener(this);
						btnPreview.setPreferredSize(new Dimension(100, 0));
					}
					{
						JPanel pnlSubmittedDocsSouthEast = new JPanel(new GridLayout(1, 3, 5, 5));
						pnlSubmittedDocsSouth.add(pnlSubmittedDocsSouthEast, BorderLayout.EAST);
						pnlSubmittedDocsSouthEast.setPreferredSize(new Dimension(345, 0));
						{
							btnSaveDocDetails = new JButton("Save Details");
							pnlSubmittedDocsSouthEast.add(btnSaveDocDetails);
							btnSaveDocDetails.addActionListener(this);
						}
						{
							btnReturnDocuments = new JButton("Docs-IN");
							pnlSubmittedDocsSouthEast.add(btnReturnDocuments);
							btnReturnDocuments.addActionListener(this);
						}
						{
							btnReleaseDocuments = new JButton("Docs-OUT");
							pnlSubmittedDocsSouthEast.add(btnReleaseDocuments);
							btnReleaseDocuments.addActionListener(this);
						}
					}
				}
			}
			/*
			 * { _JTabbedPane tabSubmittedDocuments = new _JTabbedPane();
			 * tabCenter.addTab("Submitted Docs", null,tabSubmittedDocuments, null); {
			 * JPanel pnlPASubmittedDocs = new JPanel(new BorderLayout(3, 3));
			 * tabSubmittedDocuments.addTab("PA Submitted Docs", null, pnlPASubmittedDocs,
			 * null); { scrollSubmittedDocuments = new JScrollPane();
			 * pnlPASubmittedDocs.add(scrollSubmittedDocuments, BorderLayout.CENTER);
			 * scrollSubmittedDocuments.setHorizontalScrollBarPolicy(JScrollPane.
			 * HORIZONTAL_SCROLLBAR_ALWAYS); scrollSubmittedDocuments.addMouseListener(new
			 * MouseAdapter() { public void mouseClicked(MouseEvent e) {
			 * tblSubmittedDocuments.clearSelection(); } }); { modelSubmittedDocuments = new
			 * modelDM_SubmittedDocuments();
			 * modelSubmittedDocuments.addTableModelListener(new TableModelListener() {
			 * public void tableChanged(TableModelEvent e) { //Addition of rows
			 * if(e.getType() == 1){
			 * ((DefaultListModel)rowHeaderSubmittedDocuments.getModel()).addElement(
			 * modelSubmittedDocuments.getRowCount());
			 * 
			 * if(modelSubmittedDocuments.getRowCount() == 0){
			 * rowHeaderSubmittedDocuments.setModel(new DefaultListModel()); } } } });
			 * 
			 * tblSubmittedDocuments = new _JTableMain(modelSubmittedDocuments);
			 * scrollSubmittedDocuments.setViewportView(tblSubmittedDocuments);
			 * tblSubmittedDocuments.hideColumns("ID", "Docs-OUT", "Docs-IN");
			 * 
			 * tblSubmittedDocuments.addPropertyChangeListener(new PropertyChangeListener()
			 * {//XXX Work Items public void propertyChange(PropertyChangeEvent arg0) {
			 * _JTableMain table = (_JTableMain) arg0.getSource(); String propertyName =
			 * arg0.getPropertyName();
			 * 
			 * if (propertyName.equals("tableCellEditor")) { int column =
			 * table.convertColumnIndexToModel(table.getEditingColumn()); int row =
			 * table.getEditingRow();
			 * 
			 * //setting of selected documents when selecting if (column != -1 &&
			 * modelSubmittedDocuments.getColumnClass(column).equals(Boolean.class)) {
			 * Object oldValue = null; try { oldValue = ((BooleanEditor)
			 * arg0.getOldValue()).getCellEditorValue(); } catch (NullPointerException e) {
			 * }
			 * 
			 * if (oldValue != null) { String docID = (String)
			 * modelSubmittedDocuments.getValueAt(row, 2); listSelected.add(docID); } }
			 * 
			 * //setting of number of copies when editing if (column != -1 &&
			 * modelSubmittedDocuments.getColumnClass(column).equals(Integer.class)) {
			 * Object oldValue = null; try { oldValue = ((NumberEditorExt)
			 * arg0.getOldValue()).getCellEditorValue(); if (oldValue != null &&
			 * ((Integer)oldValue) > 0) { String docID = (String)
			 * modelSubmittedDocuments.getValueAt(row, 2); for(int x=0; x< listData.size();
			 * x++){ if(listData.get(x)[2].equals(docID)){ listData.get(x)[1] = oldValue; }
			 * } }else{ modelSubmittedDocuments.setValueAt(1, row, 1); String docID =
			 * (String) modelSubmittedDocuments.getValueAt(row, 2); for(int x=0; x<
			 * listData.size(); x++){ if(listData.get(x)[2].equals(docID)){
			 * listData.get(x)[1] = oldValue; } } } } catch (NullPointerException e) { } } }
			 * } }); } { rowHeaderSubmittedDocuments = tblSubmittedDocuments.getRowHeader();
			 * rowHeaderSubmittedDocuments.setModel(new DefaultListModel());
			 * scrollSubmittedDocuments.setRowHeaderView(rowHeaderSubmittedDocuments);
			 * scrollSubmittedDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
			 * FncTables.getRowHeader_Header());
			 * scrollSubmittedDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
			 * FncTables.getRowHeader_Footer(Integer.toString(tblSubmittedDocuments.
			 * getRowCount()))); } } { JPanel pnlSubmittedDocsSouth = new JPanel(new
			 * BorderLayout()); pnlPASubmittedDocs.add(pnlSubmittedDocsSouth,
			 * BorderLayout.SOUTH); pnlSubmittedDocsSouth.setPreferredSize(new Dimension(0,
			 * 40)); pnlSubmittedDocsSouth.setBorder(BorderFactory.createEmptyBorder(5, 5,
			 * 5, 5)); { btnPreview = new JButton("Preview");
			 * pnlSubmittedDocsSouth.add(btnPreview, BorderLayout.WEST);
			 * btnPreview.addActionListener(this); btnPreview.setPreferredSize(new
			 * Dimension(100, 0)); } { JPanel pnlSubmittedDocsSouthEast = new JPanel(new
			 * GridLayout(1, 3, 5, 5)); pnlSubmittedDocsSouth.add(pnlSubmittedDocsSouthEast,
			 * BorderLayout.EAST); pnlSubmittedDocsSouthEast.setPreferredSize(new
			 * Dimension(345, 0)); { btnReturnDocuments = new JButton("Docs-IN");
			 * pnlSubmittedDocsSouthEast.add(btnReturnDocuments);
			 * btnReturnDocuments.addActionListener(this); } { btnReleaseDocuments = new
			 * JButton("Docs-OUT"); pnlSubmittedDocsSouthEast.add(btnReleaseDocuments);
			 * btnReleaseDocuments.addActionListener(this); } } } } { JPanel
			 * pnlCoappSubmittedDocs = new JPanel(new BorderLayout(3, 3));
			 * tabSubmittedDocuments.addTab("Coapp Submitted Documents", null,
			 * pnlCoappSubmittedDocs, null); { scrollCoappSubmittedDocs = new JScrollPane();
			 * pnlCoappSubmittedDocs.add(scrollCoappSubmittedDocs, BorderLayout.CENTER);
			 * scrollCoappSubmittedDocs.setHorizontalScrollBarPolicy(JScrollPane.
			 * HORIZONTAL_SCROLLBAR_ALWAYS); scrollCoappSubmittedDocs.addMouseListener(new
			 * MouseAdapter() { public void mouseClicked(MouseEvent e) {
			 * tblCoappSubmittedDocs.clearSelection(); } }); { modelCoappSubmittedDocs = new
			 * modelDM_Coapp_Submitted_Docs();
			 * modelCoappSubmittedDocs.addTableModelListener(new TableModelListener() {
			 * public void tableChanged(TableModelEvent e) { //Addition of rows
			 * if(e.getType() == 1){
			 * ((DefaultListModel)rowHeaderCoappSubmittedDocs.getModel()).addElement(
			 * modelCoappSubmittedDocs.getRowCount());
			 * 
			 * if(modelCoappSubmittedDocs.getRowCount() == 0){
			 * rowHeaderCoappSubmittedDocs.setModel(new DefaultListModel()); } } } });
			 * 
			 * tblCoappSubmittedDocs = new _JTableMain(modelCoappSubmittedDocs);
			 * scrollCoappSubmittedDocs.setViewportView(tblCoappSubmittedDocs);
			 * tblCoappSubmittedDocs.hideColumns("ID", "Doc-OUT", "Docs-IN");
			 * 
			 * tblCoappSubmittedDocs.addPropertyChangeListener(new PropertyChangeListener()
			 * {//XXX Work Items public void propertyChange(PropertyChangeEvent arg0) {
			 * _JTableMain table = (_JTableMain) arg0.getSource(); String propertyName =
			 * arg0.getPropertyName();
			 * 
			 * if (propertyName.equals("tableCellEditor")) { int column =
			 * table.convertColumnIndexToModel(table.getEditingColumn()); int row =
			 * table.getEditingRow();
			 * 
			 * //setting of selected documents when selecting if (column != -1 &&
			 * modelCoappSubmittedDocs.getColumnClass(column).equals(Boolean.class)) {
			 * Object oldValue = null; try { oldValue = ((BooleanEditor)
			 * arg0.getOldValue()).getCellEditorValue(); } catch (NullPointerException e) {
			 * }
			 * 
			 * if (oldValue != null) { String docID = (String)
			 * modelCoappSubmittedDocs.getValueAt(row, 2); listSelectedCoapp.add(docID); } }
			 * 
			 * //setting of number of copies when editing if (column != -1 &&
			 * modelCoappSubmittedDocs.getColumnClass(column).equals(Integer.class)) {
			 * Object oldValue = null; try { oldValue = ((NumberEditorExt)
			 * arg0.getOldValue()).getCellEditorValue(); if (oldValue != null &&
			 * ((Integer)oldValue) > 0) { String docID = (String)
			 * modelCoappSubmittedDocs.getValueAt(row, 2); for(int x=0; x<
			 * listDataCoapp.size(); x++){ if(listDataCoapp.get(x)[2].equals(docID)){
			 * listDataCoapp.get(x)[1] = oldValue; } } }else{
			 * modelSubmittedDocuments.setValueAt(1, row, 1); String docID = (String)
			 * modelSubmittedDocuments.getValueAt(row, 2); for(int x=0; x< listData.size();
			 * x++){ if(listData.get(x)[2].equals(docID)){ listData.get(x)[1] = oldValue; }
			 * } } } catch (NullPointerException e) { } } } } }); } {
			 * rowHeaderCoappSubmittedDocs = tblCoappSubmittedDocs.getRowHeader();
			 * rowHeaderCoappSubmittedDocs.setModel(new DefaultListModel());
			 * scrollCoappSubmittedDocs.setRowHeaderView(rowHeaderCoappSubmittedDocs);
			 * scrollCoappSubmittedDocs.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
			 * FncTables.getRowHeader_Header());
			 * scrollCoappSubmittedDocs.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
			 * FncTables.getRowHeader_Footer(Integer.toString(tblCoappSubmittedDocs.
			 * getRowCount()))); } } } }
			 */
			// XXX

			{
				JPanel pnlDocumentsForPrinting = new JPanel(new BorderLayout(5, 5));
				tabCenter.addTab("Docs for Printing", null, pnlDocumentsForPrinting, null);
				{
					scrollDocumentsForPrinting = new JScrollPane();
					pnlDocumentsForPrinting.add(scrollDocumentsForPrinting, BorderLayout.CENTER);
					scrollDocumentsForPrinting.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollDocumentsForPrinting.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblDocumentsForPrinting.clearSelection();
						}
					});
					{
						modelDocumentsForPrinting = new modelDM_DocumentsForPrinting();
						modelDocumentsForPrinting.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								// Addition of rows
								if (e.getType() == 1) {
									((DefaultListModel) rowHeaderDocumentsForPrinting.getModel())
											.addElement(modelDocumentsForPrinting.getRowCount());

									if (modelDocumentsForPrinting.getRowCount() == 0) {
										rowHeaderDocumentsForPrinting.setModel(new DefaultListModel());
									}
								}
							}
						});

						tblDocumentsForPrinting = new _JTableMain(modelDocumentsForPrinting);
						scrollDocumentsForPrinting.setViewportView(tblDocumentsForPrinting);
					}
					{
						rowHeaderDocumentsForPrinting = tblDocumentsForPrinting.getRowHeader();
						rowHeaderDocumentsForPrinting.setModel(new DefaultListModel());
						scrollDocumentsForPrinting.setRowHeaderView(rowHeaderDocumentsForPrinting);
						scrollDocumentsForPrinting.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
								FncTables.getRowHeader_Header());
						scrollDocumentsForPrinting.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
								FncTables.getRowHeader_Footer(Integer.toString(tblDocumentsForPrinting.getRowCount())));
					}
				}
				{
					JPanel pnlSouth = new JPanel(new BorderLayout());
					pnlDocumentsForPrinting.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(0, 40));
					pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JButton btnPrint = new JButton("Preview");
						pnlSouth.add(btnPrint, BorderLayout.EAST);
						btnPrint.setActionCommand("Preview Docs Printing");
						;
						btnPrint.setPreferredSize(new Dimension(100, 0));
						btnPrint.addActionListener(this);
					}
				}
			}
			{
				JPanel pnlDocsCompletion = new JPanel(new BorderLayout(5, 5));
				tabCenter.addTab("Docs Completion", null, pnlDocsCompletion, null);
				pnlDocsCompletion.setBorder(EMPTY_BORDER);
				{
					JPanel pnlDCCenter = new JPanel(new BorderLayout(5, 5));
					pnlDocsCompletion.add(pnlDCCenter, BorderLayout.CENTER);
					{
						JPanel pnlDP = new JPanel(new BorderLayout(3, 3));
						pnlDCCenter.add(pnlDP, BorderLayout.NORTH);
						pnlDP.setPreferredSize(new Dimension(0, 25));
						{
							JLabel lblDP = new JLabel("Total Downpayment to Pay: ");
							pnlDP.add(lblDP, BorderLayout.WEST);
						}
						{
							txtDP = new _JXFormattedTextField(JXFormattedTextField.CENTER);
							pnlDP.add(txtDP, BorderLayout.CENTER);
							txtDP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtDP.setEditable(false);
						}
					}
					{
						JTabbedPane tabPaymentLedger = new JTabbedPane();
						pnlDCCenter.add(tabPaymentLedger, BorderLayout.CENTER);
						{
							JPanel pnlPayment = new JPanel(new BorderLayout());
							tabPaymentLedger.add("Payment", pnlPayment);
							{
								scrollPayment = new _JScrollPaneMain();
								pnlPayment.add(scrollPayment, BorderLayout.CENTER);
								{
									modelPayment = new modelDM_Payments();

									tblPayment = new _JTableMain(modelPayment);
									scrollPayment.setViewportView(tblPayment);
									tblPayment.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if (tblPayment.rowAtPoint(e.getPoint()) == -1) {
												tblPaymentTotal.clearSelection();
											} else {
												tblPayment.setCellSelectionEnabled(true);
											}
										}

										public void mouseReleased(MouseEvent e) {
											if (tblPayment.rowAtPoint(e.getPoint()) == -1) {
												tblPaymentTotal.clearSelection();
											} else {
												tblPayment.setCellSelectionEnabled(true);
											}
										}
									});

									// Process after row add in tables
									modelPayment.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											((DefaultListModel) rowheaderPayment.getModel())
													.addElement(modelPayment.getRowCount());
											scrollPaymentTotal.setRowHeaderView(FncTables
													.getRowHeader_Footer(Integer.toString(modelPayment.getRowCount())));

											if (modelPayment.getRowCount() == 0) {
												rowheaderPayment.setModel(new DefaultListModel());
											}
										}
									});
								}
								{
									rowheaderPayment = tblPayment.getRowHeader();
									rowheaderPayment.setModel(new DefaultListModel());
									scrollPayment.setRowHeaderView(rowheaderPayment);
									scrollPayment.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
							}
							{
								scrollPaymentTotal = new _JScrollPaneTotal(scrollPayment);
								pnlPayment.add(scrollPaymentTotal, BorderLayout.SOUTH);
								{
									modelPaymentTotal = new modelDM_Payments();
									modelPaymentTotal.addRow(new Object[] { null, "Totals", null, null, null, null,
											null, null, null, null, null, null, null, null, 0.00 });

									tblPaymentTotal = new _JTableTotal(modelPaymentTotal, tblPayment);
									scrollPaymentTotal.setViewportView(tblPaymentTotal);

									tblPaymentTotal.setTotalLabel(1);
								}
							}
						}
						{
							JPanel pnlLedger = new JPanel(new BorderLayout());
							tabPaymentLedger.add("Ledger", pnlLedger);
							{
								scrollLedger = new JScrollPane();
								pnlLedger.add(scrollLedger, BorderLayout.CENTER);
								scrollLedger.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollLedger.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblLedger.clearSelection();
									}
								});
								{
									modelLedger = new modelCARD_Ledger();
									modelLedger.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent arg0) {
											if (arg0.getType() == TableModelEvent.INSERT) {
												((DefaultListModel) rowheaderLedger.getModel())
														.addElement(modelLedger.getRowCount());
											}
											if (arg0.getType() == TableModelEvent.DELETE) {
												if (modelLedger.getRowCount() == 0) {
													rowheaderLedger.setModel(new DefaultListModel());
												}
											}
										}
									});

									tblLedger = new _JTableMain(modelLedger);
									scrollLedger.setViewportView(tblLedger);
									tblLedger.hideColumns("CBP");
								}
								{
									rowheaderLedger = tblLedger.getRowHeader();
									rowheaderLedger.setModel(new DefaultListModel());
									scrollLedger.setRowHeaderView(rowheaderLedger);
									scrollLedger.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
							}
						}
					}
					{
						JPanel pnlSouth = new JPanel(new BorderLayout());
						pnlDCCenter.add(pnlSouth, BorderLayout.SOUTH);
						pnlSouth.setPreferredSize(new Dimension(0, 30));
						{
							JPanel pnlNavigation = new JPanel(new GridLayout(1, 5, 5, 5));
							pnlSouth.add(pnlNavigation, BorderLayout.EAST);
							pnlNavigation.setPreferredSize(new Dimension(520, 0));
							{
								btnDC_New = new JButton("New");
								pnlNavigation.add(btnDC_New);
								btnDC_New.setActionCommand("New DC");
								btnDC_New.addActionListener(this);
							}
							{
								btnDC_Edit = new JButton("Edit");
								pnlNavigation.add(btnDC_Edit);
								btnDC_Edit.setActionCommand("Edit DC");
								btnDC_Edit.addActionListener(this);
							}
							{
								btnDC_Delete = new JButton("Delete");
								pnlNavigation.add(btnDC_Delete);
								btnDC_Delete.setActionCommand("Delete DC");
								btnDC_Delete.addActionListener(this);
							}
							{
								btnDC_Save = new JButton("Save");
								pnlNavigation.add(btnDC_Save);
								btnDC_Save.setActionCommand("Save DC");
								btnDC_Save.addActionListener(this);
							}
							{
								btnDC_Cancel = new JButton("Cancel");
								pnlNavigation.add(btnDC_Cancel);
								btnDC_Cancel.setActionCommand("Cancel DC");
								btnDC_Cancel.addActionListener(this);
							}
						}
					}
				}
				{
					JPanel pnlDCEast = new JPanel(new GridLayout(11, 1));
					pnlDocsCompletion.add(pnlDCEast, BorderLayout.EAST);
					pnlDCEast.setBorder(components.JTBorderFactory.createTitleBorder("Client Status"));
					pnlDCEast.setPreferredSize(new Dimension(200, 0));
					{
						chkFullDPEquity = new JCheckBox("Full DP / Equity");
						pnlDCEast.add(chkFullDPEquity);
						chkFullDPEquity.setToolTipText("19 - Full Down/Equity");
						chkFullDPEquity.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								Boolean enabled = ((JCheckBox) e.getSource()).isEnabled();
								Boolean selected = (e.getStateChange() == ItemEvent.SELECTED);
								if (enabled) {
									dateFullDPEquity.setEnabled(selected);
									// dateFullDPEquity.setDate(Calendar.getInstance().getTime());
									dateFullDPEquity.setDate(FncGlobal.getDateToday());
								}
							}
						});
					}
					{
						dateFullDPEquity = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlDCEast.add(dateFullDPEquity);
						dateFullDPEquity.setEnabled(false);
						pnlDCEast.add(Box.createHorizontalGlue());
					}
					{
						chkOKMinorDocs = new JCheckBox("OK Minor Docs");
						pnlDCEast.add(chkOKMinorDocs);
						chkFullDPEquity.setToolTipText("20 - OK Minor Docs");
						chkOKMinorDocs.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								Boolean enabled = ((JCheckBox) e.getSource()).isEnabled();
								Boolean selected = (e.getStateChange() == ItemEvent.SELECTED);
								if (enabled) {
									dateOKMinorDocs.setEnabled(selected);
									// dateOKMinorDocs.setDate(Calendar.getInstance().getTime());
									dateOKMinorDocs.setDate(FncGlobal.getDateToday());
									newDesiredAmount();
								}

							}
						});
					}
					{
						dateOKMinorDocs = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlDCEast.add(dateOKMinorDocs);
						dateOKMinorDocs.setEnabled(false);
						pnlDCEast.add(Box.createHorizontalGlue());
					}
					{
						chkAccountsComeplete = new JCheckBox("Accounts Complete");
						pnlDCEast.add(chkAccountsComeplete);
						chkFullDPEquity.setToolTipText("18 - Documents Complete");
						chkAccountsComeplete.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								Boolean enabled = ((JCheckBox) e.getSource()).isEnabled();
								Boolean selected = (e.getStateChange() == ItemEvent.SELECTED);
								if (enabled) {
									dateAccountsComeplete.setEnabled(selected);
									// dateAccountsComeplete.setDate(Calendar.getInstance().getTime());
									dateAccountsComeplete.setDate(FncGlobal.getDateToday());
								}
							}
						});
					}
					{
						dateAccountsComeplete = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlDCEast.add(dateAccountsComeplete);
						dateAccountsComeplete.setEnabled(false);
						pnlDCEast.add(Box.createHorizontalGlue());
					}
					{
						chkReturnDocuments = new JCheckBox("Return Documents");
						pnlDCEast.add(chkReturnDocuments);
						chkFullDPEquity.setToolTipText("21 - Return Documents");
						chkReturnDocuments.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								Boolean enabled = ((JCheckBox) e.getSource()).isEnabled();
								Boolean selected = (e.getStateChange() == ItemEvent.SELECTED);
								if (enabled) {
									dateReturnDocuments.setEnabled(selected);
									dateReturnDocuments.setDate(Calendar.getInstance().getTime());
								}
							}
						});
					}
					{
						dateReturnDocuments = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlDCEast.add(dateReturnDocuments);
						dateReturnDocuments.setEnabled(false);
					}
				}
			}
			{
				JPanel pnlORDocumentsEvaluation = new JPanel(new BorderLayout(3, 3));
				tabCenter.addTab("OR Docs Evaluation", null, pnlORDocumentsEvaluation, null);
				pnlORDocumentsEvaluation.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlORDocumentsEvaluationNorth = new JPanel(new BorderLayout(3, 3));
					pnlORDocumentsEvaluation.add(pnlORDocumentsEvaluationNorth, BorderLayout.NORTH);
					pnlORDocumentsEvaluationNorth.setPreferredSize(new Dimension(783, 25));
					{
						JPanel pnlORDocumentsEvaluationNorthLookups = new JPanel(new BorderLayout(3, 3));
						pnlORDocumentsEvaluationNorth.add(pnlORDocumentsEvaluationNorthLookups, BorderLayout.WEST);
						pnlORDocumentsEvaluationNorthLookups.setPreferredSize(new Dimension(120, 25));
						{
							JLabel lblFindings = new JLabel("Findings  ");
							pnlORDocumentsEvaluationNorthLookups.add(lblFindings, BorderLayout.WEST);
						}
						{
							lookupFindings = new _JLookup(null, "Findings");
							pnlORDocumentsEvaluationNorthLookups.add(lookupFindings, BorderLayout.CENTER);
							lookupFindings.setReturnColumn(0);
							lookupFindings.setLookupSQL(_DocumentsMonitoring.sqlFindings());
							lookupFindings.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										String findings = (String) data[1];
										txtFindings.setText(findings);
									}
								}
							});
						}
					}
					{
						txtFindings = new _JXTextField();
						pnlORDocumentsEvaluationNorth.add(txtFindings, BorderLayout.CENTER);
					}
				}
				{
					pnlOtherFindings = new JPanel(new BorderLayout());
					pnlORDocumentsEvaluation.add(pnlOtherFindings, BorderLayout.WEST);
					{
						scrollOtherFindings = new JScrollPane();
						pnlOtherFindings.add(scrollOtherFindings, BorderLayout.WEST);
						scrollOtherFindings.setBorder(components.JTBorderFactory.createTitleBorder("Other Findings"));
						scrollOtherFindings.setPreferredSize(new Dimension(200, 197));
						{
							txtOtherFindings = new JXTextArea("Input other findings here...");
							scrollOtherFindings.setViewportView(txtOtherFindings);
							txtOtherFindings.setLineWrap(true);
							txtOtherFindings.setWrapStyleWord(true);
						}
					}
				}
				{
					JPanel pnlFindings = new JPanel(new BorderLayout(3, 3));
					pnlORDocumentsEvaluation.add(pnlFindings, BorderLayout.CENTER);
					{
						scrollFindings = new JScrollPane();
						pnlFindings.add(scrollFindings, BorderLayout.CENTER);
						scrollFindings.setBorder(LINE_BORDER);
						scrollFindings.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollFindings.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblFindings.clearSelection();
							}
						});
						{
							modelFindings = new modelDM_Findings();
							modelFindings.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									// Addition of rows
									if (e.getType() == 1) {
										((DefaultListModel) rowHeaderFindings.getModel())
												.addElement(modelFindings.getRowCount());

										if (modelFindings.getRowCount() == 0) {
											rowHeaderFindings.setModel(new DefaultListModel());
										}
									}
								}
							});

							tblFindings = new _JTableMain(modelFindings);
							scrollFindings.setViewportView(tblFindings);
							tblFindings.hideColumns("Rec. ID");
						}
						{
							rowHeaderFindings = tblFindings.getRowHeader();
							rowHeaderFindings.setModel(new DefaultListModel());
							scrollFindings.setRowHeaderView(rowHeaderFindings);
							scrollFindings.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
							scrollFindings.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
									FncTables.getRowHeader_Footer(Integer.toString(tblFindings.getRowCount())));
						}
					}
					{
						pnlFindingsOKBy = new JPanel(new BorderLayout(3, 3));
						pnlFindings.add(pnlFindingsOKBy, BorderLayout.SOUTH);
						pnlFindingsOKBy.setPreferredSize(new Dimension(570, 20));
						{
							JLabel lblOKBy = new JLabel("Eval./OK By  ");
							pnlFindingsOKBy.add(lblOKBy, BorderLayout.WEST);
						}
						{
							txtOKBy = new _JXTextField("Eval./OK By...");
							pnlFindingsOKBy.add(txtOKBy, BorderLayout.CENTER);
							txtOKBy.setEditable(true);
						}
					}
				}
				{
					JPanel pnlORDocumentsEvaluationSouth = new JPanel(new BorderLayout(3, 3));
					pnlORDocumentsEvaluation.add(pnlORDocumentsEvaluationSouth, BorderLayout.SOUTH);
					pnlORDocumentsEvaluationSouth.setPreferredSize(new Dimension(783, 30));
					{
						JPanel pnlFindingsNavigation = new JPanel(new GridLayout(1, 5, 5, 5));
						pnlORDocumentsEvaluationSouth.add(pnlFindingsNavigation, BorderLayout.EAST);
						pnlFindingsNavigation.setPreferredSize(new Dimension(520, 28));
						{
							btnFindingsNew = new JButton("New");
							pnlFindingsNavigation.add(btnFindingsNew);
							btnFindingsNew.setActionCommand("New Findings");
							btnFindingsNew.addActionListener(this);
							groupFindings.add(btnFindingsNew);
						}
						{
							btnFindingsEdit = new JButton("Edit");
							pnlFindingsNavigation.add(btnFindingsEdit);
							btnFindingsEdit.setActionCommand("Edit Findings");
							btnFindingsEdit.addActionListener(this);
							groupFindings.add(btnFindingsEdit);
						}
						{
							btnFindingsSave = new JButton("Save");
							pnlFindingsNavigation.add(btnFindingsSave);
							btnFindingsSave.setActionCommand("Save Findings");
							btnFindingsSave.addActionListener(this);
						}
						{
							btnFindingsDelete = new JButton("Delete");
							pnlFindingsNavigation.add(btnFindingsDelete);
							btnFindingsDelete.setActionCommand("Delete Findings");
							btnFindingsDelete.addActionListener(this);
						}
						{
							btnFindingsCancel = new JButton("Cancel");
							pnlFindingsNavigation.add(btnFindingsCancel);
							btnFindingsCancel.setActionCommand("Cancel Findings");
							btnFindingsCancel.addActionListener(this);
						}
					}
				}
			}
		}
		{
			JPanel pnlSouth = new JPanel(new BorderLayout(5, 5));
			// pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			// pnlSouth.setBorder(LINE_BORDER);
			pnlSouth.setPreferredSize(new Dimension(788, 30));
			{
				JPanel pnlButtons = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlButtons, BorderLayout.EAST);
				pnlButtons.setPreferredSize(new Dimension(205, 28));
				{
					btnSave = new JButton("Save");
					pnlButtons.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlButtons.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}

		// displayAllDocuments();

		enableFindings(true, false);

		setDocsCompletionEnabled(false);
		btnState_DC(false, false, false, false, false);

		btnFindingsNavigation(true, false, false, false, false);
	}

	private void displayClientInformationDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		Object[] data = _DocumentsMonitoring.dataClientInformation(entity_id, proj_id, pbl_id, seq_no);
		// String pbl_id = ((String) data[10]).trim();

		lookupClient.setValue(entity_id);
		txtClientName.setText((String) data[1]);

		txtBuyerType.setText((String) data[2]);
		txtBuyerType.setToolTipText((String) data[18]);
		txtBuyerTypeName.setText((String) data[3]);
		txtCurrentStatus.setText((String) data[4]);
		txtCurrentStatusName.setText((String) data[5]);
		txtReservation.setText((String) data[6]);
		txtOfficialReservation.setText((String) data[7]);
		txtBusinessClass.setText((String) data[16]);
		txtBusinessClassName.setText((String) data[17]);
		lookupCOApplicant.setValue(null);
		txtCoapplicant.setText("");
		lookupCOApplicant.setLookupSQL(_DocumentsMonitoring.sqlCoapplicant(entity_id));

		// XXX
		txtCompanyID.setText((String) data[14]);
		txtCompanyName.setText((String) data[15]);
		txtProjectID.setText((String) data[8]);
		txtProjectName.setText((String) data[9]);
		txtUnitID.setText(pbl_id);
		txtUnitID.setToolTipText((String) data[19]);
		txtUnitDescription.setText((String) data[11]);
		txtModelID.setText((String) data[12]);
		txtModelName.setText((String) data[13]);
		txtSequence.setText(Integer.toString(seq_no));

		displayAllDocuments(entity_id, proj_id, pbl_id, seq_no, (String) data[2], false);
		displaySubmittedDocuments(entity_id, proj_id, pbl_id, seq_no);
		displayDocumentsForPrinting(entity_id, proj_id, pbl_id, seq_no);

		// setDocsCompletionEnabled(true);
		displayDocsCompletionDetails(entity_id, proj_id, pbl_id, seq_no);
		btnState_DC(true, true, true, false, false);

		Boolean hasFindings = displayFindings(entity_id, proj_id, pbl_id, seq_no);
		// Boolean isOR = ((String) data[4]).equals("01");
		// btnFindingsNavigation(isOR, hasFindings, false, false, false);
		btnFindingsNavigation(true, hasFindings, false, false, false);
	}

	private void displayClientInformationDetails2(Object[] data) {
		String entity_id = (String) data[0];
		String proj_id = (String) data[8];
		// String unit_id = (String) data[10];
		String pbl_id = (String) data[14];
		Integer seq_no = (Integer) data[15];

		lookupClient.setValue(entity_id);
		txtClientName.setText((String) data[1]);

		txtBuyerType.setText((String) data[2]);
		txtBuyerTypeName.setText((String) data[3]);
		txtCurrentStatus.setText((String) data[4]);
		txtCurrentStatusName.setText((String) data[5]);
		txtReservation.setText((String) data[16]);
		txtOfficialReservation.setText(null);

		// XXX
		txtCompanyID.setText(null);
		txtCompanyName.setText(null);
		txtProjectID.setText((String) data[8]);
		txtProjectName.setText((String) data[9]);
		txtUnitID.setText(pbl_id);
		txtUnitID.setToolTipText((String) data[10]);
		txtUnitDescription.setText((String) data[11]);
		txtModelID.setText((String) data[12]);
		txtModelName.setText((String) data[13]);
		txtSequence.setText(Integer.toString(seq_no));

		displayAllDocuments(entity_id, proj_id, pbl_id, seq_no, (String) data[2], (String) data[4] == null);
		displaySubmittedDocuments(entity_id, proj_id, pbl_id, seq_no);
		displayDocumentsForPrinting(entity_id, proj_id, pbl_id, seq_no);

		displayDocsCompletionDetails(entity_id, proj_id, pbl_id, seq_no);
		btnState_DC(true, true, true, false, false);

		Boolean hasFindings = displayFindings(entity_id, proj_id, pbl_id, seq_no);
		btnFindingsNavigation(true, hasFindings, false, false, false);
	}

	private void displayDocsCompletionDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		Object[] data2 = _DocumentsMonitoring.getDocsCempletionData(entity_id, proj_id, pbl_id, seq_no);

		/*
		 * System.out.printf("Display value of data0: %s%n", data2[0]);
		 * System.out.printf("Display value of data1: %s%n", data2[1]);
		 * System.out.printf("Display value of data2: %s%n", data2[2]);
		 * System.out.printf("Display value of data3: %s%n", data2[3]);
		 * System.out.printf("Display value of data4: %s%n", data2[4]);
		 * System.out.printf("Display value of data5: %s%n", data2[5]);
		 * System.out.printf("Display value of data6: %s%n", data2[6]);
		 * System.out.printf("Display value of data7: %s%n", data2[7]);
		 * System.out.printf("Display value of data8: %s%n", data2[8]);
		 */

		chkFullDPEquity.setSelected((Boolean) data2[0]);
		dateFullDPEquity.setDate((Timestamp) data2[1]);
		chkOKMinorDocs.setSelected((Boolean) data2[2]);
		dateOKMinorDocs.setDate((Timestamp) data2[3]);
		chkReturnDocuments.setSelected((Boolean) data2[4]);
		dateReturnDocuments.setDate((Timestamp) data2[5]);
		chkAccountsComeplete.setSelected((Boolean) data2[6]);
		dateAccountsComeplete.setDate((Timestamp) data2[7]);

		txtDP.setValue((BigDecimal) data2[8]);

		displayPayments(entity_id, proj_id, pbl_id, seq_no);
		displayLedger(entity_id, proj_id, pbl_id, seq_no);
	}

	private void displayRecquiredDocs(Boolean isSelected) {
		modelAllDocuments.clear();
		rowHeaderAllDocuments.setModel(new DefaultListModel());
		int rowCount = listData.size();
		if (isSelected) {
			for (int x = 0; x < rowCount; x++) {
				Object[] data = listData.get(x);
				// Boolean docRequired = (Boolean) data[5];
				Boolean docMandatory = (Boolean) data[6];
				if (/* docRequired && */docMandatory) {
					modelAllDocuments.addRow(data);
				}
			}

			for (int x = 0; x < modelAllDocuments.getRowCount(); x++) {
				String docID = (String) modelAllDocuments.getValueAt(x, 2);
				modelAllDocuments.setValueAt(listSelected.contains(docID), x, 0);
			}
		} else {
			for (int x = 0; x < rowCount; x++) {
				modelAllDocuments.addRow(listData.get(x));
			}

			for (int x = 0; x < modelAllDocuments.getRowCount(); x++) {
				String docID = (String) modelAllDocuments.getValueAt(x, 2);
				modelAllDocuments.setValueAt(listSelected.contains(docID), x, 0);
			}
		}

		/*
		 * System.out.printf("Display Only Required Documents: %s%n", isSelected);
		 * _sorterAllDocuments.setRowFilter(startsWithAFilter);
		 * _sorterAllDocuments.sort(); tblAllDocuments.repaint();
		 */
		scrollAllDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblAllDocuments.getRowCount())));
		tblAllDocuments.packAll();
	}

	// SEARCH
	/*
	 * private void searchDocument(String search_document){
	 * modelAllDocuments.clear(); rowHeaderAllDocuments.setModel(new
	 * DefaultListModel());
	 * 
	 * for(int x=0; x<listData.size(); x++){ Object [] data = listData.get(x);
	 * 
	 * String document_name = (String) data[3];
	 * 
	 * pgSelect db = new pgSelect(); String SQL =
	 * "SELECT '"+document_name.replace("'", "''")+"' ~*'"+search_document+"'";
	 * db.select(SQL);
	 * 
	 * if((Boolean) db.getResult()[0][0]){ modelAllDocuments.addRow(data); } } }
	 */

	private void btnFindingsNavigation(boolean fNew, boolean fEdit, boolean fSave, boolean fDelete, boolean fCancel) {
		btnFindingsNew.setEnabled(fNew);
		btnFindingsEdit.setEnabled(fEdit);
		btnFindingsSave.setEnabled(fSave);
		btnFindingsDelete.setEnabled(fDelete);
		btnFindingsCancel.setEnabled(fCancel);
	}

	private void enableFindings(boolean isNew, boolean enable) {
		if (isNew) {
			setComponentsEnabled(pnlORDocumentsEvaluationNorth, enable);
			setComponentsEnabled(pnlOtherFindings, enable);
		}
		setComponentsEnabled(pnlFindingsOKBy, enable);
	}

	private void displayAllDocuments(String entity_id, String proj_id, String pbl_id, Integer seq_no,
			String buyertype_id, Boolean from_holding) {
		rowHeaderAllDocuments.setModel(new DefaultListModel());
		listData = _DocumentsMonitoring.displayAllDocuments(modelAllDocuments, buyertype_id, entity_id, proj_id, pbl_id,
				seq_no, from_holding);
		scrollAllDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblAllDocuments.getRowCount())));
		tblAllDocuments.packAll();
	}

	// ADDED BY JLF FOR DISPLAY OF COAPPLICANT DOCUMENTS 2016-05-01
	private void displayCoAppDocuments(String entity_id, String proj_id, String pbl_id, Integer seq_no,
			String coapplicant_id, String buyertype_id, Boolean from_holding) {
		rowHeaderAllDocuments.setModel(new DefaultListModel());
		listData = _DocumentsMonitoring.displayCoAppDocuments(modelAllDocuments, entity_id, proj_id, pbl_id, seq_no,
				coapplicant_id, buyertype_id, from_holding);
		scrollAllDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblAllDocuments.getRowCount())));
	}

	private void displaySubmittedDocuments(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		rowHeaderSubmittedDocuments.setModel(new DefaultListModel());
		_DocumentsMonitoring.displaySubmittedDocuments(modelSubmittedDocuments, entity_id, proj_id, pbl_id, seq_no);
		scrollSubmittedDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblSubmittedDocuments.getRowCount())));
		tblSubmittedDocuments.packAll();

		modelCoappSubmittedDocs.clear();
		rowHeaderCoappSubmittedDocs.setModel(new DefaultListModel());
		scrollCoappSubmittedDocs.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblCoappSubmittedDocs.getRowCount())));
	}

	private void displayCoAppSubmittedDocuments(String entity_id, String proj_id, String pbl_id, Integer seq_no,
			String coapplicant_id) {
		rowHeaderCoappSubmittedDocs.setModel(new DefaultListModel());
		_DocumentsMonitoring.displayCoAppSubmittedDocuments(modelCoappSubmittedDocs, entity_id, proj_id, pbl_id, seq_no,
				coapplicant_id);
		scrollCoappSubmittedDocs.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblCoappSubmittedDocs.getRowCount())));
		tblCoappSubmittedDocs.packAll();
	}

	private void displayDocumentsForPrinting(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		rowHeaderDocumentsForPrinting.setModel(new DefaultListModel());
		_DocumentsMonitoring.displayDocumentsForPrinting(modelDocumentsForPrinting, entity_id, proj_id, pbl_id, seq_no);
		scrollDocumentsForPrinting.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblDocumentsForPrinting.getRowCount())));
		tblDocumentsForPrinting.packAll();
	}

	private void displayPayments(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		_DocumentsMonitoring.displayPayments(modelPayment, entity_id, proj_id, pbl_id, seq_no, modelPaymentTotal);
		scrollPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblPayment.getRowCount())));
		tblPayment.packColumns("Trans. Date", "Actual Date", "Particulars", "Type", "Amount", "%", "Bank", "Branch",
				"Account #", "Check #", "Check Date", "Check Status", "Deposit Date", "OR No.", "OR Date", "AR No.",
				"Remarks", "OR Pending", "Branch", "Rec ID");
		// validatePayments();
	}

	private void displayLedger(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		_CARD.displayLedger(modelLedger, entity_id, proj_id, pbl_id, seq_no, false);
		scrollLedger.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblLedger.getRowCount())));
		tblLedger.packAll();
	}

	private Boolean displayFindings(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		rowHeaderFindings.setModel(new DefaultListModel());
		_DocumentsMonitoring.displayFindings(modelFindings, entity_id, proj_id, pbl_id, seq_no);
		scrollFindings.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblFindings.getRowCount())));
		tblFindings.packAll();

		return modelFindings.getRowCount() > 0;
	}

	/*
	 * private Boolean displayCoappFindings(String coapplicant_id, String proj_id,
	 * String pbl_id, String seq_no){ rowHeaderFindings.setModel(new
	 * DefaultListModel());
	 * 
	 * 
	 * return modelFindings.getRowCount() > 0; }
	 */

	private void newFindings(Boolean enable) {
		lookupClient.setEditable(enable);
		tabCenter.setEnabledAt(0, enable);
		tabCenter.setEnabledAt(1, enable);
		tabCenter.setEnabledAt(2, enable);
	}

	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for (int x = 0; x < modelAllDocuments.getRowCount(); x++) {
			listSelected.add((Boolean) modelAllDocuments.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	private void btnState_DC(Boolean sNew, Boolean sEdit, Boolean sDelete, Boolean sSave, Boolean sCancel) {
		btnDC_New.setEnabled(sNew);
		btnDC_Edit.setEnabled(sEdit);
		btnDC_Delete.setEnabled(sDelete);
		btnDC_Save.setEnabled(sSave);
		btnDC_Cancel.setEnabled(sCancel);
	}

	private void setDocsCompletionEnabled(Boolean enable) {
		chkFullDPEquity.setEnabled(enable);
		dateFullDPEquity.setEnabled(!enable ? enable : chkFullDPEquity.isSelected());

		if (enable) {
			if (txtBuyerType.getToolTipText().equals("04")) {
				chkOKMinorDocs.setEnabled(enable);
				dateOKMinorDocs.setEnabled(!enable ? enable : chkOKMinorDocs.isSelected());
				chkAccountsComeplete.setEnabled(false);
				dateAccountsComeplete.setEnabled(false);
			} else {
				chkOKMinorDocs.setEnabled(false);
				dateOKMinorDocs.setEnabled(false);
				chkAccountsComeplete.setEnabled(enable);
				dateAccountsComeplete.setEnabled(!enable ? enable : chkAccountsComeplete.isSelected());
			}
		} else {
			chkOKMinorDocs.setEnabled(enable);
			dateOKMinorDocs.setEnabled(!enable ? enable : chkOKMinorDocs.isSelected());
			chkAccountsComeplete.setEnabled(enable);
			dateAccountsComeplete.setEnabled(!enable ? enable : chkAccountsComeplete.isSelected());
		}
		chkReturnDocuments.setEnabled(enable);
		dateReturnDocuments.setEnabled(!enable ? enable : chkReturnDocuments.isSelected());
	}

	// commented Alvin Gonzales (2015-05-06) due to not required
	/*
	 * private Boolean checkDetails() { ArrayList<Boolean> listSelected = new
	 * ArrayList<Boolean>(); ArrayList<String> listRequired = new
	 * ArrayList<String>(); listRequired.add("42"); listRequired.add("47");
	 * listRequired.add("28"); listRequired.add("120"); listRequired.add("81");
	 * listRequired.add("40"); listRequired.add("43"); listRequired.add("122");
	 * listRequired.add("18"); listRequired.add("79"); listRequired.add("19");
	 * listRequired.add("51");
	 * 
	 * for(int x=0; x < modelAllDocuments.getRowCount(); x++){ String doc_id =
	 * (String) modelAllDocuments.getValueAt(x, 2);
	 * 
	 * if((Boolean) modelAllDocuments.getValueAt(x, 0) &&
	 * listRequired.contains(doc_id)){ System.out.printf("Doc ID: %s%n",
	 * modelAllDocuments.getValueAt(x, 2));
	 * listSelected.add(modelAllDocuments.getValueAt(x, 7) == null); } } return
	 * listSelected.contains(true); }
	 */

	@Override
	public void actionPerformed(ActionEvent e) {// XXX Action
		String action = e.getActionCommand();

		if (action.equals("Submit Documents")) {

			if (hasSelected()) {
				// if(!checkDetails()) { //commented Alvin Gonzales (2015-05-06) due to not
				// required
				if (checkRequiredFields()) {
					if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?",
							"Save", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						String entity_id = lookupClient.getValue();
						String proj_id = txtProjectID.getText();
						String pbl_id = txtUnitID.getText();
						Integer seq_no = Integer.parseInt(txtSequence.getText());
						String coapplicant_id = lookupCOApplicant.getValue();
						String unit_id = txtUnitID.getToolTipText();
						String buyertype_id = txtBuyerType.getText();
						String received_by = txtReceivedBy.getText().trim();
						String received_from = txtReceivedFrom.getText().trim();
						Date date_received = dateReceived.getDate();

						if (lookupCOApplicant.getValue() == null) {
							_DocumentsMonitoring.saveDocuments(modelAllDocuments, entity_id, proj_id, pbl_id, seq_no,
									unit_id, buyertype_id, received_by, received_from, date_received);
							_DocumentsMonitoring.saveDocumentsDetails(modelAllDocuments, entity_id, proj_id, pbl_id,
									seq_no, unit_id);
						} else {
							_DocumentsMonitoring.saveCoAppDocuments(modelAllDocuments, entity_id, proj_id, pbl_id,
									seq_no, coapplicant_id, buyertype_id, received_by, received_from, date_received);
							_DocumentsMonitoring.saveDocumentsDetails(modelAllDocuments, coapplicant_id, proj_id,
									pbl_id, seq_no, unit_id);
						}

						JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(),
								"Documents has been submitted.", action, JOptionPane.INFORMATION_MESSAGE);

						if (lookupCOApplicant.getValue() == null) {
							displayAllDocuments(entity_id, proj_id, pbl_id, seq_no, buyertype_id,
									txtCurrentStatusName.equals("HOLD"));
							displaySubmittedDocuments(entity_id, proj_id, pbl_id, seq_no);
						} else {
							displayCoAppDocuments(entity_id, proj_id, pbl_id, seq_no, coapplicant_id, buyertype_id,
									txtCurrentStatus.equals("HOLD"));
							displayCoAppSubmittedDocuments(entity_id, proj_id, pbl_id, seq_no, coapplicant_id);
						}

						txtReceivedBy.setText("");
						txtReceivedFrom.setText("");
					}
				}
			} else {
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(),
						"Please select document(s) to submit.", action, JOptionPane.WARNING_MESSAGE);
			}

		}

		if (action.equals("Preview Docs Printing")) {
			ArrayList<String> listDocID = new ArrayList<String>();
			ArrayList<String> listReport = new ArrayList<String>();
			ArrayList<String> listTitle = new ArrayList<String>();
			ArrayList<Boolean> listPrintables = new ArrayList<Boolean>();
			ArrayList<Map> listParameters = new ArrayList<Map>();

			String entity_id = lookupClient.getValue();
			String proj_id = txtProjectID.getText();
			String pbl_id = txtUnitID.getText();
			Integer seq_no = Integer.parseInt(txtSequence.getText());
			String business_class = txtBusinessClass.getText();
			String co_id = txtCompanyID.getText(); 

			if (hasnoTIN(entity_id) && business_class.equals("03") == false) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
						"Please kindly tag Tax Identification Numner (TIN) before printing of documents.", "Preview",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			Boolean individual = false;

			for (int x = 0; x < tblDocumentsForPrinting.getRowCount(); x++) {
				Boolean selected = (Boolean) tblDocumentsForPrinting.getValueAt(x, 0);
				String doc_id = (String) tblDocumentsForPrinting.getValueAt(x, 1);
				String doc_name = (String) tblDocumentsForPrinting.getValueAt(x, 2);
				// Boolean printable = (tblDocumentsForPrinting.getValueAt(x, 3) == null);
				Boolean printable = true;
				String url = (String) tblDocumentsForPrinting.getValueAt(x, 5);

				if (selected) {

					if (_DocumentsMonitoring.withMerged(entity_id, proj_id, pbl_id, seq_no)) {
						if (doc_id.equals("225")) {
							/*
							 * Object[] options = new Object[]{"Combined", "Individual"};
							 * 
							 * int index = JOptionPane.showOptionDialog(null, "Select Details to Preview",
							 * "This unit has 2 lots", JOptionPane.YES_NO_OPTION,
							 * JOptionPane.QUESTION_MESSAGE, null, options, options[0]); if(index == 1) {
							 * individual= true; }
							 */
							individual = true;
						}
					}
					System.out.printf("Display value of co_id: %s%n", co_id);	
					System.out.printf("Display value of entity_id: %s%n", entity_id);
					System.out.printf("Display value of proj_id: %s%n", proj_id);
					System.out.printf("Display value of pbl_id: %s%n", pbl_id);
					System.out.printf("Display value of seq_no: %s%n", seq_no);
					System.out.printf("Display value of individual: %s%n", individual);

					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("entity_id", entity_id);
					mapParameters.put("proj_id", proj_id);
					mapParameters.put("pbl_id", pbl_id);
					mapParameters.put("seq_no", seq_no);
					mapParameters.put("individual", individual);
					mapParameters.put("prepared_by", UserInfo.Alias);
					mapParameters.put("co_id", co_id); 
					
					if (txtCompanyID.getText().equals("01")) { // VERDANT
						mapParameters.put("logo",
								this.getClass().getClassLoader().getResourceAsStream("Images/" + "verdant_logo.bmp"));
					} else if (txtCompanyID.getText().equals("04")) { // ADC 
							mapParameters.put("logo",
									this.getClass().getClassLoader().getResourceAsStream("Images/" + "acer_logo.bmp"));
					} else if (txtCompanyID.getText().equals("05")) { // ADC 
						mapParameters.put("logo",
								this.getClass().getClassLoader().getResourceAsStream("Images/" + "eastmeridian_logo.bmp"));
					} else {
						mapParameters.put("logo",
								this.getClass().getClassLoader().getResourceAsStream("Images/" + "cenq_logo.jpg"));
					}
					
					if(entity_id.equals("1185209653") && doc_id.equals("35")) {
						url = "rptDeedOfAbsoluteSale_hernandez_karen";
					}

					FncSystem.out("url", url);
					listDocID.add(doc_id);
					listReport.add(String.format("/Reports/%s.jasper", url));
					listTitle.add(doc_name);
					listPrintables.add(printable);
					listParameters.add(mapParameters);

					if (doc_id.equals("39") || doc_id.equals("06")) {

					}

					/*
					 * if(doc_id.equals("39")){ //ADDENDUM TO CTS (Printing of Page 2 of Addendum to
					 * CTS) listDocID.add(doc_id);
					 * //listReport.add(String.format("/Reports/%s.jasper",
					 * "rptAddendumToCTS_Page2"));
					 * listReport.add(String.format("/Reports/%s.jasper",
					 * "subrptAddendumToCTS_HDMF_LastPage"));
					 * listTitle.add(String.format("%s - Page 2", doc_name));
					 * listPrintables.add(printable); listParameters.add(mapParameters); }
					 */
				}
			}

			FncReport.generateReport(listDocID.toArray(), listReport.toArray(), listTitle.toArray(),
					listPrintables.toArray(), listParameters.toArray(), "Documents Monitoring", true, entity_id,
					proj_id, pbl_id, seq_no, individual);

			/*
			 * if(entity_id.equals("2274829028")){ Map<String, Object> mapParameters2 = new
			 * HashMap<String, Object>(); mapParameters2.put("01_clientID",
			 * lookupClient.getValue()); mapParameters2.put("02_projID",
			 * txtProjectID.getText()); mapParameters2.put("03_pblID", txtUnitID.getText());
			 * mapParameters2.put("04_seqno", txtSequence.getText());
			 * mapParameters2.put("prepared_by", UserInfo.Alias); mapParameters2.put("logo",
			 * this.getClass().getClassLoader().getResourceAsStream("Images/"+
			 * "cenq_logo.jpg"));
			 * 
			 * FncReport.generateReport("/Reports/LesterReport3.jasper",
			 * "Submittted Documents1", txtClientName.getText(), mapParameters2); }
			 * 
			 * if(entity_id.equals("2274829028")){ Map<String, Object> mapParameters2 = new
			 * HashMap<String, Object>(); mapParameters2.put("01_clientID",
			 * lookupClient.getValue()); mapParameters2.put("02_projID",
			 * txtProjectID.getText()); mapParameters2.put("03_pblID", txtUnitID.getText());
			 * mapParameters2.put("04_seqno", txtSequence.getText());
			 * mapParameters2.put("prepared_by", UserInfo.Alias); mapParameters2.put("logo",
			 * this.getClass().getClassLoader().getResourceAsStream("Images/"+
			 * "cenq_logo.jpg")); FncReport.generateReport("/Reports/LesterReport2.jasper",
			 * "Submittted Documents2", txtClientName.getText(), mapParameters2); }
			 */
		}

		if (action.equals("Preview")) {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("entity_id", lookupClient.getValue());
			mapParameters.put("proj_id", txtProjectID.getText());
			mapParameters.put("pbl_id", txtUnitID.getText());
			mapParameters.put("seq_no", Integer.parseInt(txtSequence.getText()));
			mapParameters.put("client_name", txtClientName.getText());
			mapParameters.put("company", txtCompanyName.getText());

			FncReport.generateReport("/Reports/rptSubmittedDocuments.jasper", "Submittted Documents",
					txtClientName.getText(), mapParameters);
		}

		if (action.equals("Doc(s) Out")) {
			/*
			 * if(tblSubmittedDocuments.getRowCount() > 0){ String entity_id =
			 * lookupClient.getValue(); String proj_id = txtProjectID.getText(); String
			 * pbl_id = txtUnitID.getText(); Integer seq_no =
			 * Integer.parseInt(txtSequence.getText()); String unit_id =
			 * txtUnitID.getToolTipText();
			 * 
			 * dlgDM_DocsOut rr_documents = new dlgDM_DocsOut((JFrame)
			 * this.getTopLevelAncestor(), action, entity_id, proj_id, pbl_id, seq_no,
			 * unit_id);
			 * 
			 * if(rr_documents.hasDocuments()){ rr_documents.setLocationRelativeTo(null);
			 * rr_documents.setVisible(true);
			 * 
			 * displaySubmittedDocuments(entity_id, proj_id, pbl_id, seq_no); }else{
			 * JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(),
			 * "No documents to out.", action, JOptionPane.INFORMATION_MESSAGE); } }
			 */
		}

		if (action.equals("Save Details")) {
			if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				String entity_id = lookupClient.getValue();
				String proj_id = txtProjectID.getText();
				String pbl_id = txtUnitID.getText();
				Integer seq_no = Integer.parseInt(txtSequence.getText());
				String coapplicant_id = lookupCOApplicant.getValue();
				String unit_id = txtUnitID.getToolTipText();
				String buyertype_id = txtBuyerType.getText();

				if (lookupCOApplicant.getValue() == null) {
					_DocumentsMonitoring.updateDocDetails(modelSubmittedDocuments, entity_id, proj_id, pbl_id, seq_no,
							unit_id);
				} else {
					_DocumentsMonitoring.updateDocDetails(modelCoappSubmittedDocs, coapplicant_id, proj_id, pbl_id,
							seq_no, unit_id);
				}

				if (lookupCOApplicant.getValue() == null) {
					displayAllDocuments(entity_id, proj_id, pbl_id, seq_no, buyertype_id,
							txtCurrentStatusName.equals("HOLD"));
					displaySubmittedDocuments(entity_id, proj_id, pbl_id, seq_no);
				} else {
					displayCoAppDocuments(entity_id, proj_id, pbl_id, seq_no, coapplicant_id, buyertype_id,
							txtCurrentStatus.equals("HOLD"));
					displayCoAppSubmittedDocuments(entity_id, proj_id, pbl_id, seq_no, coapplicant_id);
				}

				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(),
						"Documents details has been updated.", action, JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (action.equals("Docs-OUT")) {
			if (tblSubmittedDocuments.getRowCount() > 0) {
				String entity_id = lookupClient.getValue();
				String proj_id = txtProjectID.getText();
				String pbl_id = txtUnitID.getText();
				Integer seq_no = Integer.parseInt(txtSequence.getText());
				String coapplicant_id = lookupCOApplicant.getValue();
				String unit_id = txtUnitID.getToolTipText();
				Integer selected_tab = tabSubmittedDocuments.getSelectedIndex();

				dlgDM_RRDocuments rr_documents = new dlgDM_RRDocuments((JFrame) this.getTopLevelAncestor(), action,
						entity_id, proj_id, pbl_id, seq_no, coapplicant_id, unit_id, true, selected_tab);

				if (rr_documents.hasDocuments()) {
					rr_documents.setLocationRelativeTo(null);
					rr_documents.setVisible(true);

					if (coapplicant_id == null) {
						if (tabSubmittedDocuments.getSelectedIndex() == 0) {
							displaySubmittedDocuments(entity_id, proj_id, pbl_id, seq_no);
						}
					} else {
						if (tabSubmittedDocuments.getSelectedIndex() == 1) {
							displayCoAppSubmittedDocuments(entity_id, proj_id, pbl_id, seq_no, coapplicant_id);
						}
					}

				} else {
					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "No documents to release.",
							action, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		if (action.equals("Docs-IN")) {
			if (tblSubmittedDocuments.getRowCount() > 0) {
				String entity_id = lookupClient.getValue();
				String proj_id = txtProjectID.getText();
				String pbl_id = txtUnitID.getText();
				Integer seq_no = Integer.parseInt(txtSequence.getText());
				String coapplicant_id = lookupCOApplicant.getValue();
				String unit_id = txtUnitID.getToolTipText();
				Integer selected_tab = tabSubmittedDocuments.getSelectedIndex();

				dlgDM_RRDocuments rr_documents = new dlgDM_RRDocuments((JFrame) this.getTopLevelAncestor(), action,
						entity_id, proj_id, pbl_id, seq_no, coapplicant_id, unit_id, false, selected_tab);
				if (rr_documents.hasDocuments()) {
					rr_documents.setLocationRelativeTo(null);
					rr_documents.setVisible(true);

					if (coapplicant_id == null) {
						if (tabSubmittedDocuments.getSelectedIndex() == 0) {
							displaySubmittedDocuments(entity_id, proj_id, pbl_id, seq_no);
						}
					} else {
						if (tabSubmittedDocuments.getSelectedIndex() == 1) {
							displayCoAppSubmittedDocuments(entity_id, proj_id, pbl_id, seq_no, coapplicant_id);
						}
					}
				} else {
					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "No documents to return.",
							action, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		// Docs Completion Tab
		if (action.equals("New DC")) {
			newDesiredAmount();
			newDocsCompletion();
		}
		if (action.equals("Edit DC")) {

		}
		if (action.equals("Delete DC")) {

		}
		if (action.equals("Save DC")) {
			saveDocsCompletion();
		}
		if (action.equals("Cancel DC")) {
			cancelDocsCompletion();
		}

		// Findings Tab
		if (action.equals("New Findings")) {
			newFindings(false);
			groupFindings.setSelectedButton(e);

			enableFindings(true, true);
			btnFindingsNavigation(false, false, true, false, true);
		}

		if (action.equals("Edit Findings")) {
			newFindings(false);
			groupFindings.setSelectedButton(e);
			modelFindings.setEditable(true);

			enableFindings(false, true);
			btnFindingsNavigation(false, false, true, false, true);
		}

		if (action.equals("Save Findings")) {
			String selection = groupFindings.getActionCommand();
			String entity_id = lookupClient.getValue();
			String proj_id = txtProjectID.getText();
			String pbl_id = txtUnitID.getText();
			Integer seq_no = Integer.parseInt(txtSequence.getText());

			if (selection.equals("New Findings")) {
				if (toSaveFindings()) {
					if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?",
							"Save", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						String gen_findings = null;
						if (lookupFindings.getValue() != null) {
							gen_findings = lookupFindings.getValue();
						} else {
							gen_findings = txtOtherFindings.getText().trim().replace("'", "''");
						}
						String eval_by = txtOKBy.getText().trim().replace("'", "''");
						if (eval_by.equals("")) {
							eval_by = UserInfo.Alias;
						}

						_DocumentsMonitoring.saveFindings(entity_id, proj_id, pbl_id, seq_no, gen_findings, eval_by);
						JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(),
								"New findings has been saved.", action, JOptionPane.INFORMATION_MESSAGE);
						btnFindingsCancel.doClick();
					}
				}
			} else {
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?",
						"Save", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					String eval_by = txtOKBy.getText().trim().replace("'", "''");
					/*
					 * if(eval_by.equals("")){ eval_by = UserInfo.Alias; }
					 */

					_DocumentsMonitoring.updateFindings(modelFindings, entity_id, proj_id, pbl_id, seq_no, eval_by);
					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "New findings has been saved.",
							action, JOptionPane.INFORMATION_MESSAGE);
					btnFindingsCancel.doClick();
				}
			}
		}

		// Findings Tab
		if (action.equals("Cancel Findings")) {
			newFindings(true);
			groupFindings.clearSelection();
			modelFindings.setEditable(false);

			String entity_id = lookupClient.getValue();
			String proj_id = txtProjectID.getText();
			String pbl_id = txtUnitID.getText();
			Integer seq_no = Integer.parseInt(txtSequence.getText());

			Boolean hasFindings = displayFindings(entity_id, proj_id, pbl_id, seq_no);

			enableFindings(true, false);
			lookupFindings.setValue(null);
			txtFindings.setText("");
			txtOtherFindings.setText("");
			txtOKBy.setText("");

			btnFindingsNavigation(true, hasFindings, false, false, false);
		}
	}

	private Boolean toSaveFindings() {
		if (lookupFindings.getValue() == null && txtOtherFindings.getText().equals("")) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Findings or input Other Findings.",
					"Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public void ancestorAdded(AncestorEvent arg0) {
		lookupClient.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent arg0) {
	}

	@Override
	public void ancestorRemoved(AncestorEvent arg0) {
	}

	/**
	 * added by Alvin Gonzales (2015-07-07)
	 */
	private void newDocsCompletion() {
		setDocsCompletionEnabled(true);
		btnState_DC(false, false, false, true, true);
	}

	/**
	 * added by Alvin Gonzales (2015-07-07)
	 */
	private void saveDocsCompletion() {

		if (!chkFullDPEquity.isSelected() && !chkOKMinorDocs.isSelected() && !chkAccountsComeplete.isSelected()
				&& !chkReturnDocuments.isSelected()) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select status to update.", "Save",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			String entity_id = lookupClient.getValue();
			String proj_id = txtProjectID.getText();
			String pbl_id = txtUnitID.getText();
			Integer seq_no = Integer.parseInt(txtSequence.getText());
			Boolean full_dp = chkFullDPEquity.isSelected();
			Date full_dp_date = dateFullDPEquity.getDate();
			Boolean ok_minor_docs = chkOKMinorDocs.isSelected();
			Date ok_minor_docs_date = dateOKMinorDocs.getDate();
			Boolean accounts_complete = chkAccountsComeplete.isSelected();
			Date accounts_complete_date = dateAccountsComeplete.getDate();
			Boolean docs_complete = chkReturnDocuments.isSelected();
			Date docs_complete_date = dateReturnDocuments.getDate();
			String buyer_type_id = txtBuyerType.getText();
			BigDecimal desiredAmount = (BigDecimal) txtDesiredAmount.getValued();
			Boolean saved = false;

			if (_DocumentsMonitoring.isORDocsComplete(entity_id, proj_id, pbl_id, seq_no, buyer_type_id)) {
				/*
				 * if(_DocumentsMonitoring.hasAC){
				 * 
				 * }
				 */
//				if(ok_minor_docs && withCoapp()) {
//					
//						saveDesiredAmount();
//					
//				}
				if (_DocumentsMonitoring.hasActiveOKMinor(entity_id, proj_id, pbl_id, seq_no)) {
					if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),
							String.format(
									"This client has already an active OK Minor Docs Status\nDo you want to proceed?",
									""),
							"Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						saved = _DocumentsMonitoring.saveDocsCompletion(entity_id, proj_id, pbl_id, seq_no, full_dp,
								full_dp_date, ok_minor_docs, ok_minor_docs_date, accounts_complete,
								accounts_complete_date, docs_complete, docs_complete_date, desiredAmount);
					} else {
						saved = false;
					}
				} else {
					saved = _DocumentsMonitoring.saveDocsCompletion(entity_id, proj_id, pbl_id, seq_no, full_dp,
							full_dp_date, ok_minor_docs, ok_minor_docs_date, accounts_complete, accounts_complete_date,
							docs_complete, docs_complete_date, desiredAmount);
				}

			} else {
				saved = false;
			}

			if (saved) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client status has been saved.", "Save",
						JOptionPane.INFORMATION_MESSAGE);
				setDocsCompletionEnabled(false);
				btnState_DC(true, true, true, false, false);

				displayDocsCompletionDetails(entity_id, proj_id, pbl_id, seq_no);

				// start - Added by Del Gonzales (02-04-2016) : Creation of Commission Schedule
				Object[] unit_dtl = CreateCommissionSchedule.getUnitDetails(entity_id, proj_id ,pbl_id, seq_no);
				String agent_id = unit_dtl[3].toString();
				String pmt_scheme_id = unit_dtl[4].toString();
				Double sellingprice = Double.parseDouble(unit_dtl[2].toString());
				String co_id = unit_dtl[1].toString();
				String datersvd = unit_dtl[5].toString();
				String hse_model_id = unit_dtl[6].toString();

				System.out.printf("Display Agent ID: %s%n", agent_id);
				String phase_code = unit_dtl[7].toString();
				// CreateCommissionSchedule.createCommHeader(agent_id, pmt_scheme_id, entity_id,
				// proj_id, pbl_id,
				// seq_no, sellingprice, co_id, datersvd, hse_model_id, phase_code, 1 ); //Added
				// by Del Gonzales (01-31-2017) :
				// end

				if (agent_id.equals("1212121212") == false || agent_id.equals("0000000003") == false || entity_id.equals("4053052077")) {
					if (Commission_Schedule_Generator.comm_Create_sched(agent_id, pmt_scheme_id, entity_id, proj_id,
							pbl_id, seq_no, sellingprice, co_id, datersvd, hse_model_id, phase_code) == true) {
						if (_DocumentsMonitoring.withCommission(pbl_id, seq_no)) {

						} else {
							JOptionPane.showMessageDialog(null, "Commission Schedule generation successful.",
									"Information", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Commission Schedule generation failed.", "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}

	/**
	 * added by Alvin Gonzales (2015-07-07)
	 */
	private void cancelDocsCompletion() {
		setDocsCompletionEnabled(false);
		btnState_DC(true, true, true, false, false);
	}

	private Boolean hasnoTIN(String entity_id) {
		pgSelect db = new pgSelect();
		// db.select("SELECT NOT EXISTS(SELECT * FROM rf_entity_submitted_id WHERE
		// entity_id = '"+ entity_id +"' AND id_type = '9' AND status_id = 'A');");
		db.select(
				"SELECT (CASE WHEN nullif(tin_no, '') is null then TRUE else FALSE end) from rf_entity_id_no where entity_id = '"
						+ entity_id + "';\n");
		return (Boolean) db.getResult()[0][0];
	}

	public boolean checkRequiredFields() {// CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;

		if (txtReceivedFrom.getText().isEmpty() || txtReceivedFrom.getText().equals("")) {
			required_fields = required_fields + "Received From \n";
			toSave = false;
		}
		if (txtReceivedBy.getText().isEmpty() || txtReceivedBy.getText().equals("")) {
			required_fields = required_fields + "Received By \n";
			toSave = false;
		}

		if (toSave == false) {
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n" + required_fields, "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public void saveDesiredAmount() {
		pgUpdate db = new pgUpdate();
		BigDecimal desiredAmount = (BigDecimal) txtDesiredAmount.getValue();
		String entity_id = lookupClient.getValue();

		System.out.println("desiredAmount.compareTo(new BigDecimal(\"0.00\")): "
				+ desiredAmount.compareTo(new BigDecimal("0.00")));

		if (desiredAmount.compareTo(new BigDecimal("0.00")) == 1)

		// if(desiredAmount!=new BigDecimal("0.00"))
		{
			String query = "UPDATE rf_pagibig_computation SET co_app_desired_loan ='" + desiredAmount
					+ "' WHERE entity_id = '" + entity_id + "'  ";
			db.executeUpdate(query, true);
			db.commit();
		} else {
			JOptionPane.showMessageDialog(getContentPane(), "Please Input Coapp Desired Loan Amount", "Save",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void newDesiredAmount() {

		if (chkOKMinorDocs.isSelected() && withCoapp()) {

			txtDesiredAmount.setEnabled(true);

		} else {
			txtDesiredAmount.setEnabled(false);
		}

	}

	public boolean withCoapp() {
		String entity_id = lookupClient.getValue();
		pgSelect db = new pgSelect();

		String query = "SELECT * FROM rf_entity_connect WHERE entity_id = '" + entity_id
				+ "' AND connect_type IN ('SC','CO')" + "AND status_id = 'A'";
		db.select(query);
		if (db.isNotNull()) {
			return true;
		} else {
			return false;
		}
	}

	public void lookDesiredAmountValue() {
		pgSelect db = new pgSelect();
		String entity_id = lookupClient.getValue();

		String query = "SELECT COALESCE(co_app_desired_loan,0) FROM rf_pagibig_computation where entity_id = '"
				+ entity_id + "'";
		db.select(query);

		if (db.isNotNull()) {
			txtDesiredAmount.setValue((BigDecimal) db.getResult()[0][0]);
			txtDesiredAmount.setEnabled(false);
		} else {

			txtDesiredAmount.setEnabled(false);
			txtDesiredAmount.setValue(FncBigDecimal.zeroValue());
		}

	}

}
