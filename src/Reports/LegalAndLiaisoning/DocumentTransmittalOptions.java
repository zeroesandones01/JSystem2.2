package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;

public class DocumentTransmittalOptions extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/*
	 * AS OF 2021-11-18
	 * 
	 * 1. EDIT REPORT - COLUMN NAMES PER DOC STATUS HAS BEEN CHANGE (EPEB MORTGAGE
	 * ADDED AS DOCS PROCESSING STATUS IN TCT) DCRF NO. 1856 2021-11-18
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Document Transmittal Options";
	Dimension frameSize = new Dimension(600, 430);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlSouth;

	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDocType;
	private JLabel lblDocStatus;
	private JLabel lblSortedBy;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupPhase;
	private _JLookup lookupBlock;
	private _JLookup lookupDocType;
	private _JLookup lookupDescription;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtBlock;
	private JTextField txtPhase;
	private JTextField txtDocType;
	private JTextField txtdescription;

	private JButton btnPreview;
	private JButton btnCancel;
	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JComboBox cmbSorting;

	String company;
	String company_logo;
	static String co_id = "";
	String branch_name = "";
	String branch_id = "";
	String proj_id = "";
	String proj_name = "";
	String bank_id = "";

	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	private JPanel pnlCenter_b;

	private JLabel lblDateTo;

	private String proj_alias;

	private JPanel pnlCenter;

	public DocumentTransmittalOptions() {
		super(title, true, true, true, true);
		initGUI();
	}

	public DocumentTransmittalOptions(String title) {
		super(title);
		initGUI();
	}

	public DocumentTransmittalOptions(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					{
						c.weightx = 1;
						c.weighty = 1.25;

						c.gridx = 0;
						c.gridy = 0;

						JPanel pnlDocument = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlDocument, c);
						pnlDocument.setBorder(components.JTBorderFactory.createTitleBorder("Document Details"));// XXX
						{
							GridBagConstraints cons_docs = new GridBagConstraints();
							cons_docs.fill = GridBagConstraints.BOTH;
							cons_docs.insets = new Insets(5, 5, 5, 5);

							/* LINE 1 company */
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 0;
								cons_docs.gridy = 0;

								lblCompany = new JLabel("Company", JLabel.CENTER);
								pnlDocument.add(lblCompany, cons_docs);
								lblCompany.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docs.weightx = 0.25;
								cons_docs.weighty = 1;

								cons_docs.gridx = 1;
								cons_docs.gridy = 0;

								lookupCompany = new _JLookup(null, "Company");
								pnlDocument.add(lookupCompany, cons_docs);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setPreferredSize(new Dimension(40, 0));
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											co_id = (String) data[0];
											company = (String) data[1];
											company_logo = (String) data[3];

											String name = (String) data[1];
											txtCompany.setText(name);
											lookupProject
													.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));

											lblProject.setEnabled(true);

											lblDocType.setEnabled(true);

											lblDateFrom.setEnabled(true);
											lblDateTo.setEnabled(true);
											dteDateFrom.setEnabled(true);
											dateDateTo.setEnabled(true);

											KEYBOARD_MANAGER.focusNextComponent();
											btnCancel.setEnabled(true);
											btnPreview.setEnabled(true);
										}
									}
								});
							}
							{
								cons_docs.weightx = 1.5;
								cons_docs.weighty = 1;

								cons_docs.gridx = 2;
								cons_docs.gridy = 0;

								txtCompany = new JTextField();
								pnlDocument.add(txtCompany, cons_docs);
								txtCompany.setEditable(false);
								txtCompany.setFont(FncGlobal.sizeTextValue);
							}

							/* LINE 2 project */
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 0;
								cons_docs.gridy = 1;

								lblProject = new JLabel("Project", JLabel.CENTER);
								pnlDocument.add(lblProject, cons_docs);
								lblProject.setEnabled(false);
								lblProject.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docs.weightx = 0.25;
								cons_docs.weighty = 1;

								cons_docs.gridx = 1;
								cons_docs.gridy = 1;

								lookupProject = new _JLookup(null, "Project");
								pnlDocument.add(lookupProject, cons_docs);
								lookupProject.setReturnColumn(0);
								lookupProject.setPreferredSize(new Dimension(40, 0));
								lookupProject.setFont(FncGlobal.sizeTextValue);
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											proj_id = (String) data[0];
											proj_name = (String) data[1];
											proj_alias = (String) data[2];
											txtProject.setText(proj_name);

											btnCancel.setEnabled(true);
											
											lookupPhase.setLookupSQL(SQL_PHASE(proj_id));

											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								cons_docs.weightx = 1.5;
								cons_docs.weighty = 1;

								cons_docs.gridx = 2;
								cons_docs.gridy = 1;

								txtProject = new JTextField();
								pnlDocument.add(txtProject, cons_docs);
								txtProject.setEditable(false);
								txtProject.setEnabled(false);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}

							/* LINE 3 payee */
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 0;
								cons_docs.gridy = 2;

								JLabel lblPhase = new JLabel("Phase", JLabel.CENTER);
								pnlDocument.add(lblPhase, cons_docs);
								lblPhase.setFont(FncGlobal.sizeLabel);
								// lblProject.setEnabled(false);
							}
							{
								cons_docs.weightx = 0.25;
								cons_docs.weighty = 1;

								cons_docs.gridx = 1;
								cons_docs.gridy = 2;

								lookupPhase = new _JLookup(null, "Phase");
								pnlDocument.add(lookupPhase, cons_docs);
								lookupPhase.setReturnColumn(0);
								lookupPhase.setLookupSQL(SQL_PHASE("015"));
								lookupPhase.setPreferredSize(new Dimension(40, 0));
								lookupPhase.setFont(FncGlobal.sizeTextValue);
								lookupPhase.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											txtPhase.setText(String.format("Phase %s", (String) data[0]));
											lookupBlock.setLookupSQL(SQL_BLOCK((String) data[0]));

											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 2;
								cons_docs.gridy = 2;

								txtPhase = new JTextField();
								pnlDocument.add(txtPhase, cons_docs);
								txtPhase.setEditable(false);
								txtPhase.setEnabled(false);
								txtPhase.setFont(FncGlobal.sizeTextValue);
							}

							/* LINE 4 block */
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 0;
								cons_docs.gridy = 3;

								JLabel lblBlock = new JLabel("Block", JLabel.CENTER);
								pnlDocument.add(lblBlock, cons_docs);
								// lblProject.setEnabled(false);
								lblBlock.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docs.weightx = 0.25;
								cons_docs.weighty = 1;

								cons_docs.gridx = 1;
								cons_docs.gridy = 3;

								lookupBlock = new _JLookup(null, "Block");
								pnlDocument.add(lookupBlock, cons_docs);
								lookupBlock.setReturnColumn(0);
								lookupBlock.setPreferredSize(new Dimension(40, 0));
								lookupBlock.setFont(FncGlobal.sizeTextValue);
								lookupBlock.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											txtBlock.setText(String.format("Block %s", (String) data[0]));

										}
									}
								});
							}
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 2;
								cons_docs.gridy = 3;

								txtBlock = new JTextField();
								pnlDocument.add(txtBlock, cons_docs);
								txtBlock.setEditable(false);
								txtBlock.setEnabled(false);
								txtBlock.setFont(FncGlobal.sizeTextValue);
							}

							/* LINE 5 doc type */
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 0;
								cons_docs.gridy = 4;

								lblDocType = new JLabel("Doc Type", JLabel.CENTER);
								pnlDocument.add(lblDocType, cons_docs);
								lblDocType.setEnabled(false);
								lblDocType.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docs.weightx = 0.25;
								cons_docs.weighty = 1;

								cons_docs.gridx = 1;
								cons_docs.gridy = 4;

								lookupDocType = new _JLookup(null, "Doc Type");
								pnlDocument.add(lookupDocType, cons_docs);
								lookupDocType.setReturnColumn(0);
								lookupDocType.setName("lookupDocType");
								lookupDocType.setPreferredSize(new Dimension(40, 0));
								lookupDocType.setFont(FncGlobal.sizeTextValue);
								lookupDocType.setLookupSQL(DocID());
								lookupDocType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											txtDocType.setText((String) data[1]);
										}
									}
								});
							}
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 2;
								cons_docs.gridy = 4;

								txtDocType = new JTextField();
								pnlDocument.add(txtDocType, cons_docs);
								txtDocType.setEditable(false);
								txtDocType.setEnabled(false);
								txtDocType.setFont(FncGlobal.sizeTextValue);
							}

							/* LINE 6 doc status */
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 0;
								cons_docs.gridy = 5;

								lblDocStatus = new JLabel("Doc Status", JLabel.CENTER);
								pnlDocument.add(lblDocStatus, cons_docs);
								lblDocStatus.setEnabled(false);
								lblDocStatus.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docs.weightx = 0.25;
								cons_docs.weighty = 1;

								cons_docs.gridx = 1;
								cons_docs.gridy = 5;

								lookupDescription = new _JLookup(null, "Description");
								pnlDocument.add(lookupDescription, cons_docs);
								lookupDescription.setReturnColumn(0);
								lookupDescription.setFont(FncGlobal.sizeTextValue);
								lookupDescription.setPreferredSize(new Dimension(40, 0));
								lookupDescription.setLookupSQL(Description());
								lookupDescription.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String status = (String) data[0];
											String desc = (String) data[1];

											txtdescription.setText(desc);

											/*
											 * ADDED BY JED 2021-09-07 DCRF NO. 1741 : CHANGE THE FORMAT OF BIR DOC
											 * STAMP REPORT ONLY
											 */
											if (status.equals("185")) {
												lblSortedBy.setEnabled(false);
												cmbSorting.setEnabled(false);
											} else {
												lblSortedBy.setEnabled(true);
												cmbSorting.setEnabled(true);
											}

											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 2;
								cons_docs.gridy = 5;

								txtdescription = new JTextField();
								pnlDocument.add(txtdescription, cons_docs);
								txtdescription.setEditable(false);
								txtdescription.setEnabled(false);
								txtdescription.setFont(FncGlobal.sizeTextValue);
							}

							/* LINE 7 doc status */
							{
								cons_docs.weightx = 0;
								cons_docs.weighty = 1;

								cons_docs.gridx = 0;
								cons_docs.gridy = 6;

								lblSortedBy = new JLabel("Sorted By", JLabel.CENTER);
								pnlDocument.add(lblSortedBy, cons_docs);
								lblSortedBy.setEnabled(false);
								lblSortedBy.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docs.weightx = 0.25;
								cons_docs.weighty = 1;

								cons_docs.gridx = 1;
								cons_docs.gridy = 6;

								cons_docs.gridwidth = 2;

								String[] combo1 = new String[] { "Document No.", "Phase", "Block", "Buyer's Name",
										"Serial No.","Location", "TCT under Company Name" };

								cmbSorting = new JComboBox(combo1);
								pnlDocument.add(cmbSorting, cons_docs);
								cmbSorting.setSelectedIndex(3);
								cmbSorting.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0;
						c.gridy = 1;

						JPanel pnlStatus = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlStatus, c);
						pnlStatus.setBorder(components.JTBorderFactory.createTitleBorder("Status Date"));
						{
							GridBagConstraints cons_status = new GridBagConstraints();
							cons_status.fill = GridBagConstraints.BOTH;
							cons_status.insets = new Insets(5, 5, 5, 5);

							{
								cons_status.weightx = 0;
								cons_status.weighty = 1;

								cons_status.gridx = 0;
								cons_status.gridy = 0;

								lblDateFrom = new JLabel("Date From:", JLabel.CENTER);
								pnlStatus.add(lblDateFrom, cons_status);
								lblDateFrom.setEnabled(false);
								lblDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_status.weightx = 1;
								cons_status.weighty = 1;

								cons_status.gridx = 1;
								cons_status.gridy = 0;

								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlStatus.add(dteDateFrom, cons_status);
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(false);
								dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteDateFrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_status.weightx = 0;
								cons_status.weighty = 1;

								cons_status.gridx = 2;
								cons_status.gridy = 0;

								lblDateTo = new JLabel("Date To:", JLabel.CENTER);
								pnlStatus.add(lblDateTo, cons_status);
								lblDateTo.setEnabled(false);
								lblDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_status.weightx = 1;
								cons_status.weighty = 1;

								cons_status.gridx = 3;
								cons_status.gridy = 0;

								dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlStatus.add(dateDateTo, cons_status);
								dateDateTo.setDate(null);
								dateDateTo.setEnabled(false);
								dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dateDateTo.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.addActionListener(this);
					btnCancel.setFont(FncGlobal.sizeControls);
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, dteDateFrom, dateDateTo, btnPreview));
		// this.setBounds(0, 0, 545, 400); //174

		// added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		// lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {

	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		String sort_by = (String) cmbSorting.getSelectedItem();
		if (actionCommand.equals("Preview")) {

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("co_name", company);
			mapParameters.put("proj_alias", proj_alias);
			mapParameters.put("phase", lookupPhase.getValue());
			mapParameters.put("block", lookupBlock.getValue());
			mapParameters.put("doc_type", lookupDocType.getValue());
			mapParameters.put("doc_desc", txtDocType.getText());
			mapParameters.put("status", lookupDescription.getValue());
			mapParameters.put("desc", txtdescription.getText());
			mapParameters.put("from", dteDateFrom.getDate());
			mapParameters.put("to", dateDateTo.getDate());
			// mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));/*COMMENTED BY
			// JED 2021-09-07 DCRF NO.1741 : MOVE INSIDE THE IF STATEMENT*/

			/*
			 * ADDED BY JED 2021-11-18 DCRF NO. 1856 : CHANGE COLUMN NAMES IN THE REPORT PER
			 * DOC STATUS(EPEB MORTGAGE ADDED)
			 */

			if (UserInfo.Department.equals("04") || UserInfo.Department.equals("98") || UserInfo.Department.equals("95")) {
				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
				FncReport.generateReport("/Reports/rptTCT_Stat.jasper", "Transmittal Form", mapParameters);
			} else {
				if (lookupDescription.getValue().equals("185")) {
					FncReport.generateReport("/Reports/rptTCT_BIR.jasper", "Transmittal Form", mapParameters);
				} else if (lookupDescription.getValue().equals("195")) {
					mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
					FncReport.generateReport("/Reports/rptTCT_195.jasper", "Transmittal Form", mapParameters);
				} else if (lookupDescription.getValue().equals("217")) {
					mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
					FncReport.generateReport("/Reports/rptTCT_199.jasper", "Transmittal Form", mapParameters);
				} else if (lookupDescription.getValue().equals("180")) {
					mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
					FncReport.generateReport("/Reports/rptTCT_180.jasper", "Transmittal Form", mapParameters);
				}
					else if (lookupDescription.getValue().equals("196")) {
				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
				FncReport.generateReport("/Reports/rptTCT_Stat_196.jasper", "Transmittal Form", mapParameters);
				}
				//ADDED BY MONIQUE DTD 10-05-2023; REFER TO DCRF#2800
					else if (lookupDescription.getValue().equals("222")) { 
				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
				FncReport.generateReport("/Reports/rptTCT_Stat_222.jasper", "Transmittal Form", mapParameters);	
				}
				//ADDED BY MONIQUE DTD 08-16-2024; REFER TO DCRF#3083
					else if (lookupDescription.getValue().equals("227")) { 
				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
				FncReport.generateReport("/Reports/rptTCT_Stat_227.jasper", "Transmittal Form", mapParameters);	
				}
				else {
					mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
					FncReport.generateReport("/Reports/rptTCT.jasper", "Transmittal Form", mapParameters);
				}
			}

//			if(lookupDescription.getValue() != null) {
//				if(lookupDescription.getValue().equals("185")) {
//					FncReport.generateReport("/Reports/rptTCT_BIR.jasper", "Transmittal Form", mapParameters);
//				}else if(lookupDescription.getValue().equals("195")) {
//					mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
//					FncReport.generateReport("/Reports/rptTCT_195.jasper", "Transmittal Form", mapParameters);
//				}else if(lookupDescription.getValue().equals("217")) {
//					mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
//					FncReport.generateReport("/Reports/rptTCT_199.jasper", "Transmittal Form", mapParameters);
//				}else if(lookupDescription.getValue().equals("180")) {
//					mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
//					FncReport.generateReport("/Reports/rptTCT_180.jasper", "Transmittal Form", mapParameters);
//				}
//			}else {
//				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
//				FncReport.generateReport("/Reports/rptTCT.jasper", "Transmittal Form", mapParameters);
//			}

			// if(look)

//			if(lookupDescription.getValue().equals("195")) {
//				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
//				FncReport.generateReport("/Reports/rptTCT_195.jasper", "Transmittal Form", mapParameters);
//			}
//			
//			if(lookupDescription.getValue().equals("199")) {
//				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
//				FncReport.generateReport("/Reports/rptTCT_199.jasper", "Transmittal Form", mapParameters);
//			}
//			else {
//				mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by));
//				FncReport.generateReport("/Reports/rptTCT.jasper", "Transmittal Form", mapParameters);
//			}

		}

		if (e.getActionCommand().equals("Cancel")) {
			cancel();
		}
	}

	private void initialize_comp() {

		co_id = "";
		company = "";
		proj_id = "";
		proj_alias = "";
		proj_name = "";
		company_logo = sql_getCompanyLogo();

		txtCompany.setText(company);
		lookupCompany.setValue(co_id);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

		txtProject.setText(proj_name);
		lookupProject.setValue(proj_id);

		lblProject.setEnabled(true);
		txtPhase.setEnabled(true);
		txtBlock.setEnabled(true);
		txtProject.setEnabled(true);
		txtDocType.setEnabled(true);
		txtdescription.setEnabled(true);

		lblDocType.setEnabled(true);
		lblDocStatus.setEnabled(true);
		lblSortedBy.setEnabled(true);
		/*
		 * lookupBranch.setText(""); lookupBranch.setEnabled(true);
		 * txtBranch.setEnabled(true); txtBranch.setText("");
		 */

		lblDateFrom.setEnabled(true);
		lblDateTo.setEnabled(true);
		dteDateFrom.setEnabled(true);
		dateDateTo.setEnabled(true);

		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);

	}

	private void cancel() {// **edited by jed 2019-03-11 : change setText to setValue for lookup fields**//
		/*
		 * lookupPhase.setText(""); lookupDescription.setText("");
		 * lookupDocType.setText(""); lookupBlock.setText("");
		 */
		lookupPhase.setValue(null);
		lookupDescription.setValue(null);
		lookupDocType.setValue(null);
		lookupBlock.setValue(null);
		txtPhase.setText("");
		txtBlock.setText("");
		txtDocType.setText("");
		txtdescription.setText("");
		dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		lblSortedBy.setEnabled(true);
		cmbSorting.setEnabled(true);

		btnPreview.setEnabled(true);

		branch_name = "";
		branch_id = "";
		proj_id = "";
		proj_name = "";
	}

	private static String DocID() {
		String sql = "select doc_id as \"Doc ID\", trim(doc_desc) as \"Doc Desc\" from mf_system_doc where tct_taxdec_doc = true and status_id = 'A' and server_id is null order by doc_desc;";
		System.out.printf("SQL: %s%n", sql);
		return sql;
	}

	private static String Description() {
		String sql = "select trim(status_code) as \"ID\", trim(status_desc) as \"Name\", trim(Status_alias) as \"Alias\"\n"
				+ "from mf_tct_taxdec_status \n" + "--where status_id = 'A'\n"
				+ "where status_id in ('A', 'I') and server_id is null \n" + // ***edited by jed 2019-02-11: to include
																				// old particulars***//
				"order by trim(status_desc);";
		return sql;
	}

	private List<JRSortField> sortBy(String sort_by) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("Document No.", new String[] { "c_doc_no" });
		map.put("Phase", new String[] { "c_phase" });
		map.put("Block", new String[] { "c_block" });
		map.put("Buyer's Name", new String[] { "c_client" });
		map.put("Serial No.", new String[] { "c_serial_no" });
		map.put("Location", new String[] { "c_latest_location" });
		map.put("TCT under Company Name", new String[] {"c_tct_under_company"}); //ADDED BY MONIQUE DTD 09-18-2023; DCRF#2765

		List<JRSortField> sortList = new ArrayList<JRSortField>();
		for (String fields : map.get(sort_by)) {
			JRDesignSortField sortField = new JRDesignSortField();
			sortField.setName(fields);
			sortField.setOrder(SortOrderEnum.ASCENDING);
			sortField.setType(SortFieldTypeEnum.FIELD);

			sortList.add(sortField);
		}

		return sortList;
	}
	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
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