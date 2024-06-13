/**
 * 
 */
package Buyers.LoansManagement;

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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Accounting.Disbursements.RequestForPayment;
import Buyers.ClientServicing.CARD;
import Buyers.ClientServicing.ClientInformation;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelPST_ForTCTAnnotation;
import tablemodel.modelPST_NOA_Expiring;
import tablemodel.modelPagibigStatusMonitoring_AccountQuery_HouseInspection;
import tablemodel.modelPagibigStatusMonitoring_AccountQuery_MSVS_StatusHistory;
import tablemodel.modelPagibigStatusMonitoring_AccountQuery_StatusHistory;
import tablemodel.modelPagibigStatusMonitoring_DeficientAccounts;
import tablemodel.modelPagibigStatusMonitoring_MSVSMonitoring;
import tablemodel.modelPagibigStatusMonitoring_QualifiedAccounts;
import tablemodel.modelPST_DOA_Signed;
import tablemodel.model_hdmf_FirstFiling;
import tablemodel.model_hdmf_borrower_val;
import tablemodel.model_hdmf_postInspection;
import tablemodel.model_hdmf_preInspection;

/**
 * @author John Lester Fatallo
 */

public class PagibigStatusMonitoring extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -6384768286369553635L;

	private static String title = "Pag-IBIG Status Monitoring";
	static Dimension SIZE = new Dimension(750, 570);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Border lineBorder_red = BorderFactory.createLineBorder(Color.RED);

	private _JTabbedPane tabPAGIBIGCenter;
	private JPanel pnlPAGIBIGQualifyAccounts;
	private JPanel pnlPQANorth;
	private JPanel pnlPQANorthCenter;
	private JPanel pnlPQANCLabel;
	private JLabel lblPQACompany;
	private JLabel lblPQAProject;
	private JLabel lblPQAPhase;
	private JLabel lblPQAStage;

	private JPanel pnlPQANCComponent;
	private JPanel pnlPQACompany;
	private _JLookup lookupPQACompany;
	private _JXTextField txtPQACompany;
	private JPanel pnlPQAProject;
	private _JLookup lookupPQAProject;
	private _JXTextField txtPQAProject;
	private JPanel pnlPQAPhase;
	private _JLookup lookupPQAPhase;
	private _JXTextField txtPQAPhase;
	private JPanel pnlPQAStage;
	private JComboBox cmbPQAStage;
	private JLabel lblPQATransDate;
	private _JDateChooser dtePQATransDate;

	private JPanel pnlPQANorthEast;
	private JButton btnPQAGenerate;

	private _JTabbedPane tabPQACenter;
	private JPanel pnlPQAQualifiedAccounts;
	private _JTableMain tblPQAQualifiedAccounts;
	private JScrollPane scrollPQAQualifiedAccounts;
	private JList rowHeaderPQAQualifiedAccounts;
	private modelPagibigStatusMonitoring_QualifiedAccounts modelPQAQualifiedAccounts;
	private modelPST_ForTCTAnnotation modelForTCTAnnotation;

	private JPanel pnlPQADeficientAccounts;
	private _JTableMain tblPQADeficientAccounts;
	private JScrollPane scrollPQADeficientAccounts;
	private JList rowHeaderPQADeficientAccounts;
	private modelPagibigStatusMonitoring_DeficientAccounts modelPQADeficientAccounts;

	private JPanel pnlPQASouth;
	private JButton btnPQAPost;
	private JButton btnPQAPreview;
	private JButton btnPQAClear;
	private JButton btnPQAExport;

	private JPanel pnlPAGIBIGStatusTagging;
	private JPanel pnlPSTNorth;
	private JPanel pnlPSTNorthCenter;
	private JPanel pnlPSTNCLabel;
	private JLabel lblPSTCompany;
	private JLabel lblPSTProject;
	private JLabel lblPSTPhase;
	private JLabel lblPSTStage;

	private JPanel pnlPSTNCComponent;

	private JPanel pnlPSTCompany;
	private _JLookup lookupPSTCompany;
	private _JXTextField txtPSTCompany;

	private JPanel pnlPSTProject;
	private _JLookup lookupPSTProject;
	private _JXTextField txtPSTProject;

	private JPanel pnlPSTPhase;
	private _JLookup lookupPSTPhase;
	private _JXTextField txtPSTPhase;

	private JPanel pnlPSTCurrentStage;
	private _JLookup lookupPSTCurrentStage;
	private _JXTextField txtPSTCurrentStage;
	private JComboBox cmbPSTStage;
	private JLabel lblPSTTransDate;
	private _JDateChooser dtePSTTransDate;

	private JPanel pnlPSTTransDate;
	private JLabel lblPSTBatchNo;
	private _JLookup lookupPSTBatchNo;

	private JPanel pnlPSTNorthEast;
	private JButton btnPSTGenerate;

	private JPanel pnlPSTCenter;
	private JScrollPane scrollPSTStatusTagging;
	private _JTableMain tblPSTStatusTagging;
	private JList rowHeaderPSTStatusTagging;
	private modelPST_DOA_Signed modelPSTStatusTagging;
	private modelPST_NOA_Expiring modelNOA_Expiring;

	private JPanel pnlPSTSouth;
	private JPanel pnlPSTSouthUpper;
	private JPanel pnlPSTSULabel;
	private JLabel lblPSTNewStatus;
	private JLabel lblPSTNOADate;

	private JPanel pnlPSTSUComponent;
	private JPanel pnlPSTNewStatus;
	private _JLookup lookupPSTNewStatus;
	private _JXTextField txtPSTNewStatus;

	private JPanel pnlPSTNOADate;
	private _JDateChooser dteNOA;

	private JPanel pnlPSTSouthLower;
	private JButton btnPSTPost;
	private JButton btnPSTPreview;
	private JButton btnPSTClear;
	private JButton btnPSTExport;

	private JPanel pnlPAGIBIGHouseInspection;
	private JPanel pnlPHINorth;
	/*
	 * private JPanel pnlPHINorthCenter; private JPanel pnlPHINCLabel; private
	 * JLabel lblPHICompany; private JLabel lblPHIProject; private JLabel
	 * lblPHIPhase; private JLabel lblPHIStage; private JLabel lblPHITransDate;
	 * 
	 * private JPanel pnlPHINCComponent; private JPanel pnlPHICompany; private
	 * _JLookup lookupPHICompany; private _JXTextField txtPHICompany;
	 * 
	 * private JPanel pnlPHIProject; private _JLookup lookupPHIProject; private
	 * _JXTextField txtPHIProject;
	 * 
	 * private JPanel pnlPHIPhase; private _JLookup lookupPHIPhase; private
	 * _JXTextField txtPHIPhase;
	 * 
	 * private JPanel pnlPHIStage; private _JLookup lookupPHIStage; private
	 * _JXTextField txtPHIStage;
	 * 
	 * private JPanel pnlPHITransDate; private _JDateChooser dtePHITransDate;
	 * 
	 * private JPanel pnlPHINorthEast; private JButton btnPHIGenerate;
	 * 
	 * private JPanel pnlPHICenter; private JScrollPane scrollPHIHouseInspection;
	 * private _JTableMain tblPHIHouseInspection; private JList
	 * rowHeaderPHIHouseInspection; private
	 * modelPagibigStatusMonitoring_HouseInspection modelPHIHouseInspection;
	 * 
	 * private JPanel pnlPHISouth; private JPanel pnlPHISouthUpper; private JPanel
	 * pnlPHISULabel; private JLabel lblPHINewStatus; private JLabel lblPHIHDMFRep;
	 * 
	 * private JPanel pnlPHISUComponent; private JPanel pnlPHINewStatus; private
	 * _JLookup lookupPHINewStatus; private _JXTextField txtPHINewStatus;
	 * 
	 * private JPanel pnlPHIHDMFRep; private _JXTextField txtPHIHDMFRep;
	 * 
	 * private JPanel pnlPHISouthLower; private JButton btnPHIPost; private JButton
	 * btnPHIClear;
	 */

	private JPanel pnlPAGIBIGMSVSMonitoring;
	private JPanel pnlPMMNorth;
	private JPanel pnlPMMNorthCenter;
	private JPanel pnlPMMNCLabel;
	private JLabel lblPMMCompany;
	private JLabel lblPMMProject;
	private JLabel lblPMMPhase;
	private JLabel lblPMMStage;
	private JLabel lblPMMTransDate;

	private JPanel pnlPMMNCComponent;
	private JPanel pnlPMMCompany;
	private _JLookup lookupPMMCompany;
	private _JXTextField txtPMMCompany;

	private JPanel pnlPMMProject;
	private _JLookup lookupPMMProject;
	private _JXTextField txtPMMProject;

	private JPanel pnlPMMPhase;
	private _JLookup lookupPMMPhase;
	private _JXTextField txtPMMPhase;

	private JPanel pnlPMMStage;
	private _JLookup lookupPMMStage;
	private _JXTextField txtPMMStage;

	private JPanel pnlPMMTransDate;
	private _JDateChooser dtePMMTransDate;

	private JPanel pnlPMMNorthEast;
	private JButton btnPMMGenerate;

	private JPanel pnlPMMCenter;
	private JScrollPane scrollPMMMSVSMonitoring;
	private _JTableMain tblPMMMSVSMonitoring;
	private JList rowHeaderPMMMSVSMonitoring;
	private modelPagibigStatusMonitoring_MSVSMonitoring modelPMMMSVSMonitoring;

	private JPanel pnlPMMSouth;
	private JPanel pnlPMMSouthUpper;
	private JPanel pnlPMMSULabel;
	private JLabel lblPMMNewStatus;
	private JPanel pnlPMMSUComponent;
	private JPanel pnlPMMNewStatus;
	private _JLookup lookupPMMNewStatus;
	private _JXTextField txtPMMNewStatus;

	private JPanel pnlPMMSouthLower;
	private JButton btnPMMPost;
	private JButton btnPMMClear;

	private JPanel pnlPAGIBIGAccountQuery;
	private JPanel pnlPAQNorth;
	private JPanel pnlPAQNorthLabel;
	private JLabel lblPAQClient;
	private JLabel lblPAQProject;
	private JLabel lblPAQAppliedAmt;
	private JLabel lblPAQNOAReleasedDate;

	private JPanel pnlPAQNorthComponent;
	private JPanel pnlPAQClient;
	private _JLookup lookupPAQClient;
	private _JXTextField txtPAQClient;

	private JPanel pnlPAQProject;
	private _JXTextField txtPAQProject;
	private JLabel lblPAQPhBlkLt;
	private _JXTextField txtPAQPhBlkLt;

	private JPanel pnlPAQAppliedAmt;
	private _JXFormattedTextField txtPAQAppliedAmt;
	private JLabel lblPAQApprovedAmt;
	private _JXFormattedTextField txtPAQApprovedAmt;

	private JPanel pnlPAQNOAReleasedDate;
	private _JDateChooser dtePAQNOAReleased;

	private JPanel pnlPAQCenter;
	private _JTabbedPane tabPAQCenter;
	private JPanel pnlPAQStatusHistory;
	private JScrollPane scrollPAQStatusHistory;
	private JList rowHeaderPAQStatusHistory;
	private _JTableMain tblPAQStatusHistory;
	private modelPagibigStatusMonitoring_AccountQuery_StatusHistory modelPAQ_StatusHistory;

	private JPanel pnlPAQHouseInspection;
	private JScrollPane scrollPAQHouseInspection;
	private JList rowHeaderPAQHouseInspection;
	private _JTableMain tblPAQHouseInspection;
	private modelPagibigStatusMonitoring_AccountQuery_HouseInspection modelPAQ_HouseInspection;

	private JPanel pnlPAQMSVSStatusHistory;
	private JScrollPane scrollPAQ_MSVS_StatusHistory;
	private JList rowHeaderPAQ_MSVS_StatusHistory;
	private _JTableMain tblPAQ_MSVS_StatusHistory;
	private modelPagibigStatusMonitoring_AccountQuery_MSVS_StatusHistory modelPAQ_MSVS_StatusHistory;

	/* Added by Mann2x; Date Added: 2016-12-29; HDMF First Filing Coding; */
	private JXPanel panFirstFiling;
	private JScrollPane scrFirstFiling;
	private model_hdmf_FirstFiling modelFirstFiling;
	public static _JTableMain tblFirstFiling;
	public static JList rowFirstFiling;

	/** House Inspection **/
	private JLabel hins_lblCo;
	private JLabel hins_lblPro;
	private JLabel hins_lblBatch;
	private JLabel hins_lblClient;
	private JLabel hins_lblUnit;
	private JLabel hins_lblDate;
	private JLabel hins_lblStatus;
	private JLabel hins_lblRep;
	private JLabel hins_lblRemarks;

	private _JLookup hins_txtCoID;
	private _JLookup hins_txtProID;
	private _JLookup hins_txtBatch;
	private _JLookup hins_txtClientID;
	private _JLookup hins_txtLookStatus;

	private JTextField hins_txtCo;
	private JTextField hins_txtPro;
	private JTextField hins_txtUnitID;
	private JTextField hins_txtUnit;
	private JTextField hins_txtClient;
	private JTextField hins_txtStatus;
	private JTextField hins_txtRep;
	private JTextField hins_txtRemarks;

	private _JDateChooser hins_dteDate;
	private _JDateChooser hins_dteInsp;

	private JComboBox hins_cboStatus;

	private JTabbedPane hins_jtpList;

	private model_hdmf_preInspection modelPreInsp;
	private model_hdmf_postInspection modelPostInsp;

	private _JTableMain tblPreInsp;
	private _JTableMain tblPostInsp;

	public static JList rowPreInsp;
	public static JList rowPostInsp;

	private JScrollPane scrollPreInsp;
	private JScrollPane scrollPostInsp;

	private JXPanel panPre;
	private JXPanel panPost;
	private JXPanel panDate;

	private JButton hins_btnGen;
	private JButton hins_btnPost;
	private JButton hins_btnPreview;
	private JButton hins_btnExport;

	private JButton hins_btnAdd;
	private JButton hins_btnSave;
	private JButton hins_btnCancel;

	/** House Inspection **/

	/** Borrower's Validation **/
	private JXPanel panBorVal;
	private JXPanel panBorValGrid;

	private JLabel bor_lblCo;
	private JLabel bor_lblPro;

	private _JLookup bor_txtCoID;
	private _JLookup bor_txtProID;
	private _JLookup bor_txtBat;

	private JTextField bor_txtCo;
	private JTextField bor_txtPro;

	private model_hdmf_borrower_val modelBorVal;
	private _JTableMain tblBorVal;
	public static JList rowBorVal;
	private JScrollPane scrollBorVal;

	private JButton bor_btnGen;
	private JButton bor_btnPost;
	private JButton bor_btnPreview;
	private JButton bor_btnExport;

	private _JDateChooser dteBor;

	private JCheckBox chkBor;
	/** Borrower's Validation **/

	private JButton btnPost;
	private JButton btnPreview;
	private JButton btnExport;

	public PagibigStatusMonitoring() {
		super(title, true, true, false, true);
		initGUI();
	}

	public PagibigStatusMonitoring(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public PagibigStatusMonitoring(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, false, true, false, true);
		initGUI();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		{
			tabPAGIBIGCenter = new _JTabbedPane();
			this.add(tabPAGIBIGCenter, BorderLayout.CENTER);
			tabPAGIBIGCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlPAGIBIGQualifyAccounts = new JPanel(new BorderLayout(3, 3));
				tabPAGIBIGCenter.add("Qualify Accounts", pnlPAGIBIGQualifyAccounts);
				{
					pnlPQANorth = new JPanel(new BorderLayout(5, 5));
					pnlPAGIBIGQualifyAccounts.add(pnlPQANorth, BorderLayout.NORTH);
					pnlPQANorth.setPreferredSize(new Dimension(0, 125));
					pnlPQANorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlPQANorthCenter = new JPanel(new BorderLayout(3, 3));
						pnlPQANorth.add(pnlPQANorthCenter, BorderLayout.CENTER);
						{
							pnlPQANCLabel = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlPQANorthCenter.add(pnlPQANCLabel, BorderLayout.WEST);
							pnlPQANCLabel.setPreferredSize(new Dimension(75, 0));
							{
								lblPQACompany = new JLabel("Company");
								pnlPQANCLabel.add(lblPQACompany);
							}
							{
								lblPQAProject = new JLabel("Project");
								pnlPQANCLabel.add(lblPQAProject);
							}
							{
								lblPQAPhase = new JLabel("Phase");
								pnlPQANCLabel.add(lblPQAPhase);
							}
							{
								lblPQAStage = new JLabel("Stage");
								pnlPQANCLabel.add(lblPQAStage);
							}
						}
						{
							pnlPQANCComponent = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlPQANorthCenter.add(pnlPQANCComponent, BorderLayout.CENTER);
							{
								pnlPQACompany = new JPanel(new BorderLayout(5, 3));
								pnlPQANCComponent.add(pnlPQACompany);
								{
									lookupPQACompany = new _JLookup(null, "Select Company", 0);
									pnlPQACompany.add(lookupPQACompany, BorderLayout.WEST);
									lookupPQACompany.setPreferredSize(new Dimension(100, 0));
									lookupPQACompany.setLookupSQL(_PagibigStatusMonitoring.sqlCompany());
									lookupPQACompany.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											if (data != null) {
												String co_id = (String) data[0];
												String co_name = (String) data[1];
												txtPQACompany.setText(co_name);
												lookupPQAProject
														.setLookupSQL(_PagibigStatusMonitoring.sqlProject(co_id));
											}
										}
									});
								}
								{
									txtPQACompany = new _JXTextField();
									pnlPQACompany.add(txtPQACompany, BorderLayout.CENTER);
								}
							}
							{
								pnlPQAProject = new JPanel(new BorderLayout(5, 3));
								pnlPQANCComponent.add(pnlPQAProject);
								{
									lookupPQAProject = new _JLookup(null, "Select Project", 0);
									pnlPQAProject.add(lookupPQAProject, BorderLayout.WEST);
									lookupPQAProject.setPreferredSize(new Dimension(100, 0));
									lookupPQAProject.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											FncSystem.out("Display sql for lookup of Project",
													lookupPQAProject.getLookupSQL());

											if (data != null) {
												String proj_id = (String) data[0];
												String proj_name = (String) data[1];
												txtPQAProject.setText(proj_name);

												lookupPQAPhase.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id));
											}
										}
									});
								}
								{
									txtPQAProject = new _JXTextField();
									pnlPQAProject.add(txtPQAProject, BorderLayout.CENTER);
								}
							}
							Setdefaults();
							{
								pnlPQAPhase = new JPanel(new BorderLayout(5, 3));
								pnlPQANCComponent.add(pnlPQAPhase);
								{
									lookupPQAPhase = new _JLookup(null, "Select Phase", 1);
									pnlPQAPhase.add(lookupPQAPhase, BorderLayout.WEST);
									lookupPQAPhase.setPreferredSize(new Dimension(100, 0));
									lookupPQAPhase.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											FncSystem.out("Display sql for lookup of phase",
													lookupPQAPhase.getLookupSQL());

											if (data != null) {

												// String sub_proj_name = (String) data[1];
												String sub_proj_name = (String) data[2];
												txtPQAPhase.setText(sub_proj_name);
											}
										}
									});
								}
								{
									txtPQAPhase = new _JXTextField();
									pnlPQAPhase.add(txtPQAPhase, BorderLayout.CENTER);
								}
							}
							{
								pnlPQAStage = new JPanel(new BorderLayout(3, 3));
								pnlPQANCComponent.add(pnlPQAStage);
								{
									cmbPQAStage = new JComboBox(new String[] { "", "For SCD RELEASE",
											"For DOCS COMPLETION", "For FIRST FILING", "For HDMF INSPECTION",
											"For TCT ANNOTATION", "For POST FILING" });

									pnlPQAStage.add(cmbPQAStage, BorderLayout.WEST);
									cmbPQAStage.setPreferredSize(new Dimension(200, 0));
									cmbPQAStage.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent arg0) {
											int selected_item = cmbPQAStage.getSelectedIndex();

											/*
											 * modelPQAQualifiedAccounts.clear();
											 * rowHeaderPQAQualifiedAccounts.setModel(new DefaultListModel());
											 * scrollPQAQualifiedAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER,
											 * FncTables.getRowHeader_Footer(""));
											 * 
											 * modelPQADeficientAccounts.clear();
											 * rowHeaderPQADeficientAccounts.setModel(new DefaultListModel());
											 * scrollPQADeficientAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER,
											 * FncTables.getRowHeader_Footer(""));
											 */

											/*
											 * Added by Mann2x; Date Added: December 2016; First filing report; Mann2x's
											 * Mark
											 */
											// tabPQACenter.removeAll();

											if (selected_item == 2 || selected_item == 3 || selected_item == 6) {
												tabPQACenter.add("Deficient Accounts", pnlPQADeficientAccounts);
											} else {
												tabPQACenter.remove(pnlPQADeficientAccounts);
											}

											/*
											 * try { tabPQACenter.remove(pnlPQAQualifiedAccounts); } catch
											 * (NullPointerException ex) { System.out.println("");
											 * System.out.println("pnlPQAQualifiedAccounts is not yet added."); }
											 */

											if (selected_item == 2 || selected_item == 6) {
												/*
												 * tabPQACenter.add("Qualified Accounts", pnlPQAQualifiedAccounts);
												 * tabPQACenter.add("Deficient Accounts", pnlPQADeficientAccounts);
												 */

												// tabPQACenter.remove(panFirstFiling);

												// modelPQAQualifiedAccounts.clear();
												// modelPQAQualifiedAccounts = new
												// modelPagibigStatusMonitoring_QualifiedAccounts();
												// tblPQAQualifiedAccounts = new _JTableMain(modelPQAQualifiedAccounts);
												scrollPQAQualifiedAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER,
														FncTables.getRowHeader_Footer(""));
												rowHeaderPQAQualifiedAccounts.setModel(new DefaultListModel());

												// modelPQADeficientAccounts.clear();
												// modelPQADeficientAccounts = new
												// modelPagibigStatusMonitoring_DeficientAccounts();
												// tblPQADeficientAccounts = new _JTableMain(modelPQADeficientAccounts);
												scrollPQADeficientAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER,
														FncTables.getRowHeader_Footer(""));
												rowHeaderPQADeficientAccounts.setModel(new DefaultListModel());

											} /*
												 * else if (selected_item == 3) { tabPQACenter.add("Qualified Accounts",
												 * panFirstFiling); tabPQACenter.remove(pnlPQAQualifiedAccounts);
												 * tabPQACenter.remove(pnlPQADeficientAccounts); } else {
												 * tabPQACenter.add("Qualified Accounts", pnlPQAQualifiedAccounts);
												 * tabPQACenter.remove(pnlPQADeficientAccounts);
												 * tabPQACenter.remove(panFirstFiling); }
												 */

											/*
											 * if(selected_item == 5){ tabPQACenter.add("Qualified Accounts",
											 * pnlPQAQualifiedAccounts);
											 * System.out.println("Dumaan dito sa For TCT Annotation");
											 * 
											 * modelForTCTAnnotation.clear(); tblPQAQualifiedAccounts = new
											 * _JTableMain(modelForTCTAnnotation);
											 * scrollPQAQualifiedAccounts.setViewportView(tblPQAQualifiedAccounts);
											 * scrollPQAQualifiedAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER,
											 * FncTables.getRowHeader_Footer(""));
											 * rowHeaderPQAQualifiedAccounts.setModel(new DefaultListModel());
											 * 
											 * tblPQAQualifiedAccounts.hideColumns("Proj. Name", "Entity ID",
											 * "Proj. ID", "PBL ID", "Seq", "Aff. Ratio", "% Constructed"); }
											 */

										}
									});
								}
								/*
								 * Added by Mann2x; Date Added: December 2016; First filing codes; Mann2x's Mark
								 */
								JXPanel panDiv = new JXPanel(new BorderLayout(5, 5));
								pnlPQAStage.add(panDiv, BorderLayout.CENTER);
								{
									lblPQATransDate = new JLabel("Trans. Date", JLabel.TRAILING);
									panDiv.add(lblPQATransDate, BorderLayout.LINE_START);
									lblPQATransDate.setHorizontalAlignment(JTextField.CENTER);
									lblPQATransDate.setPreferredSize(new Dimension(100, 0));
								}
								{
									dtePQATransDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									panDiv.add(dtePQATransDate, BorderLayout.CENTER);

								}
							}
						}
					}
					{
						pnlPQANorthEast = new JPanel(new BorderLayout(5, 5));
						pnlPQANorth.add(pnlPQANorthEast, BorderLayout.EAST);
						{
							btnPQAGenerate = new JButton("Generate");
							pnlPQANorthEast.add(btnPQAGenerate);
							btnPQAGenerate.setActionCommand("Generate");
							btnPQAGenerate.addActionListener(this);
							btnPQAGenerate.setPreferredSize(new Dimension(200, 0));
						}
					}
				}
				{
					tabPQACenter = new _JTabbedPane();
					pnlPAGIBIGQualifyAccounts.add(tabPQACenter, BorderLayout.CENTER);
					{
						pnlPQAQualifiedAccounts = new JPanel(new BorderLayout(3, 3));
						tabPQACenter.add("Qualified Accounts", pnlPQAQualifiedAccounts);
						{
							scrollPQAQualifiedAccounts = new JScrollPane();
							pnlPQAQualifiedAccounts.add(scrollPQAQualifiedAccounts, BorderLayout.CENTER);
							{
								modelPQAQualifiedAccounts = new modelPagibigStatusMonitoring_QualifiedAccounts();
								// modelForTCTAnnotation = new modelPST_ForTCTAnnotation();

								tblPQAQualifiedAccounts = new _JTableMain(modelPQAQualifiedAccounts);
								scrollPQAQualifiedAccounts.setViewportView(tblPQAQualifiedAccounts);
								scrollPQAQualifiedAccounts
										.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								tblPQAQualifiedAccounts.hideColumns("Proj. Name", "Entity ID", "Proj. ID", "PBL ID",
										"Seq");
								tblPQAQualifiedAccounts.addMouseListener(new MouseAdapter() {

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
										_JTableMain table = (_JTableMain) e.getSource();
										// int[] rows = table.getSelectedRows();
										int selected_row = tblPQAQualifiedAccounts
												.convertRowIndexToModel(tblPQAQualifiedAccounts.getSelectedRow());

										final String entity_id = (String) modelPQAQualifiedAccounts
												.getValueAt(selected_row, 3);
										final String entity_name = (String) modelPQAQualifiedAccounts
												.getValueAt(selected_row, 4);
										final String proj_id = (String) modelPQAQualifiedAccounts
												.getValueAt(selected_row, 5);
										final String pbl_id = (String) modelPQAQualifiedAccounts
												.getValueAt(selected_row, 6);
										final Integer seq_no = (Integer) modelPQAQualifiedAccounts
												.getValueAt(selected_row, 7);

										JPopupMenu menu = new JPopupMenu();

										JMenuItem menuViewCARD = new JMenuItem("View CARD");
										menu.add(menuViewCARD);
										menuViewCARD.setFont(menuViewCARD.getFont().deriveFont(10f));
										menuViewCARD.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {

												if (FncGlobal.homeMDI.isNotExisting("CARD")) {
													CARD c = new CARD(entity_id, proj_id, pbl_id, seq_no);
													Home_JSystem.addWindow(c, null);
												} else {
													JOptionPane.showMessageDialog(
															PagibigStatusMonitoring.this.getTopLevelAncestor(),
															"CARD is already open", "View CARD",
															JOptionPane.WARNING_MESSAGE);
												}
											}
										});

										JMenuItem menuViewClientInfo = new JMenuItem("View Client Info");
										menu.add(menuViewClientInfo);
										menuViewClientInfo.setFont(menuViewClientInfo.getFont().deriveFont(10f));
										menuViewClientInfo.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(ActionEvent e) {

												if (FncGlobal.homeMDI.isNotExisting("ClientInformation")) {
													ClientInformation ci = new ClientInformation("Client Information",
															entity_id, entity_name);
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
								rowHeaderPQAQualifiedAccounts = tblPQAQualifiedAccounts.getRowHeader();
								rowHeaderPQAQualifiedAccounts.setModel(new DefaultListModel());
								scrollPQAQualifiedAccounts.setRowHeaderView(rowHeaderPQAQualifiedAccounts);
								scrollPQAQualifiedAccounts.setCorner(JScrollPane.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
					}
					{
						pnlPQADeficientAccounts = new JPanel(new BorderLayout(3, 3));
						{
							scrollPQADeficientAccounts = new JScrollPane();
							pnlPQADeficientAccounts.add(scrollPQADeficientAccounts, BorderLayout.CENTER);
							{
								modelPQADeficientAccounts = new modelPagibigStatusMonitoring_DeficientAccounts();
								tblPQADeficientAccounts = new _JTableMain(modelPQADeficientAccounts);
								scrollPQADeficientAccounts.setViewportView(tblPQADeficientAccounts);
								scrollPQADeficientAccounts
										.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								tblPQADeficientAccounts.hideColumns("Proj. Name", "Entity ID", "Proj. ID", "PBL ID",
										"Seq No", "Contact No", "SCD In", "SCD Out");
							}
							{
								rowHeaderPQADeficientAccounts = tblPQADeficientAccounts.getRowHeader();
								rowHeaderPQADeficientAccounts.setModel(new DefaultListModel());
								scrollPQADeficientAccounts.setRowHeaderView(rowHeaderPQADeficientAccounts);
								scrollPQADeficientAccounts.setCorner(JScrollPane.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
					}

					tabPQACenter.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							int selected_tab = ((_JTabbedPane) e.getSource()).getSelectedIndex();

							if (selected_tab == 0) {
								btnPQAState(true, true, true);
							}

							if (selected_tab == 1) {
								btnPQAState(false, true, true);
							}
						}
					});
				}
				/*
				 * Added by Mann2x; Date Added: December 2016; First filing report; Mann2x's
				 * Mark
				 */
				CreateFirstFilingGrid();
				{
					pnlPQASouth = new JPanel(new GridLayout(1, 3, 5, 5));
					pnlPAGIBIGQualifyAccounts.add(pnlPQASouth, BorderLayout.SOUTH);
					pnlPQASouth.setPreferredSize(new Dimension(0, 30));
					// pnlPQASouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						btnPQAPost = new JButton("Post");
						pnlPQASouth.add(btnPQAPost);
						btnPQAPost.setActionCommand("Post");
						btnPQAPost.addActionListener(this);
					}
					{
						btnPQAPreview = new JButton("Preview");
						pnlPQASouth.add(btnPQAPreview);
						btnPQAPreview.setActionCommand("Preview");
						btnPQAPreview.addActionListener(this);
					}
					{
						btnPQAClear = new JButton("Clear");
						// pnlPQASouth.add(btnPQAClear);
						btnPQAClear.setActionCommand("Clear");
						btnPQAClear.addActionListener(this);
					}
					{
						btnPQAExport = new JButton("Export");
						pnlPQASouth.add(btnPQAExport);
						btnPQAExport.setActionCommand("Export");
						btnPQAExport.addActionListener(this);
					}
				}
			}
			{
				pnlPAGIBIGStatusTagging = new JPanel(new BorderLayout(3, 3));
				tabPAGIBIGCenter.add("Status Tagging", pnlPAGIBIGStatusTagging);
				{
					pnlPSTNorth = new JPanel(new BorderLayout(3, 3));
					pnlPAGIBIGStatusTagging.add(pnlPSTNorth, BorderLayout.NORTH);
					pnlPSTNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlPSTNorthCenter = new JPanel(new BorderLayout(3, 3));
						pnlPSTNorth.add(pnlPSTNorthCenter, BorderLayout.CENTER);
						{
							pnlPSTNCLabel = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlPSTNorthCenter.add(pnlPSTNCLabel, BorderLayout.WEST);
							{
								lblPSTCompany = new JLabel("Company");
								pnlPSTNCLabel.add(lblPSTCompany);
							}
							{
								lblPSTProject = new JLabel("Project");
								pnlPSTNCLabel.add(lblPSTProject);
							}
							{
								lblPSTPhase = new JLabel("Phase");
								pnlPSTNCLabel.add(lblPSTPhase);
							}
							{
								lblPSTStage = new JLabel("Stage");
								pnlPSTNCLabel.add(lblPSTStage);
							}
							/*
							 * { lblPSTTransDate = new JLabel("Trans. Date");
							 * pnlPSTNCLabel.add(lblPSTTransDate); }
							 */
						}
						{
							pnlPSTNCComponent = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlPSTNorthCenter.add(pnlPSTNCComponent, BorderLayout.CENTER);
							{
								pnlPSTCompany = new JPanel(new BorderLayout(3, 3));
								pnlPSTNCComponent.add(pnlPSTCompany);
								{
									lookupPSTCompany = new _JLookup(null, "Select Company", 0);
									pnlPSTCompany.add(lookupPSTCompany, BorderLayout.WEST);
									lookupPSTCompany.setPreferredSize(new Dimension(50, 0));
									lookupPSTCompany.setLookupSQL(_PagibigStatusMonitoring.sqlCompany());
									lookupPSTCompany.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											if (data != null) {
												String co_id = (String) data[0];
												String co_name = (String) data[1];

												txtPSTCompany.setText(co_name);
												lookupPSTProject
														.setLookupSQL(_PagibigStatusMonitoring.sqlProject(co_id));
											}
										}
									});
								}
								{
									txtPSTCompany = new _JXTextField();
									pnlPSTCompany.add(txtPSTCompany, BorderLayout.CENTER);
								}
							}
							{
								pnlPSTProject = new JPanel(new BorderLayout(3, 3));
								pnlPSTNCComponent.add(pnlPSTProject);
								{
									lookupPSTProject = new _JLookup(null, "Select Project", 0);
									pnlPSTProject.add(lookupPSTProject, BorderLayout.WEST);
									lookupPSTProject.setPreferredSize(new Dimension(50, 0));
									lookupPSTProject.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											if (data != null) {
												String proj_id = (String) data[0];
												String proj_name = (String) data[1];

												txtPSTProject.setText(proj_name);
												lookupPSTPhase.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id));
											}
										}
									});
								}
								{
									txtPSTProject = new _JXTextField();
									pnlPSTProject.add(txtPSTProject, BorderLayout.CENTER);
								}
							}
							{
								pnlPSTPhase = new JPanel(new BorderLayout(3, 3));
								pnlPSTNCComponent.add(pnlPSTPhase);
								{
									lookupPSTPhase = new _JLookup(null, "Select Phase", 1);
									pnlPSTPhase.add(lookupPSTPhase, BorderLayout.WEST);
									lookupPSTPhase.setPreferredSize(new Dimension(50, 0));
									lookupPSTPhase.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											if (data != null) {
												// String sub_proj_name = (String) data[1];
												String sub_proj_name = (String) data[2];

												txtPSTPhase.setText(sub_proj_name);
											}
										}
									});
								}
								{
									txtPSTPhase = new _JXTextField();
									pnlPSTPhase.add(txtPSTPhase, BorderLayout.CENTER);
								}
							}
							{
								pnlPSTCurrentStage = new JPanel(new BorderLayout(3, 3));
								pnlPSTNCComponent.add(pnlPSTCurrentStage);
								{
									cmbPSTStage = new JComboBox(
											new String[] { "", "With DOA Assigned", "With NOA Expiring", "With EPEB",
													"With Annotated TCT", "For Post Approval" });
									pnlPSTCurrentStage.add(cmbPSTStage, BorderLayout.WEST);
									cmbPSTStage.setPreferredSize(new Dimension(200, 0));
									cmbPSTStage.addItemListener(new ItemListener() {

										public void itemStateChanged(ItemEvent e) {
											int selected_item = cmbPSTStage.getSelectedIndex();

											rowHeaderPSTStatusTagging.setModel(new DefaultListModel());
											scrollPSTStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER,
													FncTables.getRowHeader_Footer(""));

											if (selected_item == 1) {
												modelPSTStatusTagging = new modelPST_DOA_Signed();
												modelPSTStatusTagging.clear();
												tblPSTStatusTagging = new _JTableMain(modelPSTStatusTagging);
												scrollPSTStatusTagging.setViewportView(tblPSTStatusTagging);
												tblPSTStatusTagging.hideColumns("Proj. Name", "Entity ID", "Proj. ID",
														"PBL ID", "Seq. No");
											}
											if (selected_item == 2) {
												modelNOA_Expiring = new modelPST_NOA_Expiring();

												tblPSTStatusTagging = new _JTableMain(modelNOA_Expiring);
												scrollPSTStatusTagging.setViewportView(tblPSTStatusTagging);
												tblPSTStatusTagging.hideColumns("No.", "Proj. Alias", "Proj. Name",
														"Entity ID", "Proj. ID", "PBL ID", "Seq");
											}
											if (selected_item == 3) {

											}
										}
									});
								}
								{
									lblPSTTransDate = new JLabel("Trans Date", JLabel.TRAILING);
									pnlPSTCurrentStage.add(lblPSTTransDate);
								}
								{
									dtePSTTransDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlPSTCurrentStage.add(dtePSTTransDate, BorderLayout.EAST);
									dtePSTTransDate.setPreferredSize(new Dimension(150, 0));
								}
								/*
								 * { lookupPSTCurrentStage = new _JLookup(null, "Select Stage", 0);
								 * pnlPSTCurrentStage.add(lookupPSTCurrentStage, BorderLayout.WEST);
								 * lookupPSTCurrentStage.setPreferredSize(new Dimension(50, 0));
								 * lookupPSTCurrentStage.setLookupSQL(_PagibigStatusMonitoring.sqlCurrentStage()
								 * ); lookupPSTCurrentStage.addLookupListener(new LookupListener() {
								 * 
								 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
								 * event.getSource()).getDataSet();
								 * 
								 * if(data != null){ String current_stage = (String) data[1];
								 * 
								 * txtPSTCurrentStage.setText(current_stage); } } }); } { txtPSTCurrentStage =
								 * new _JXTextField(); pnlPSTCurrentStage.add(txtPSTCurrentStage); }
								 */
							}
							/*
							 * { pnlPSTTransDate = new JPanel(new BorderLayout(3, 3));
							 * pnlPSTNCComponent.add(pnlPSTTransDate); { dtePSTTransDate = new
							 * _JDateChooser("MM/dd/yy", "##/##/##", '_');
							 * pnlPSTTransDate.add(dtePSTTransDate, BorderLayout.WEST);
							 * dtePSTTransDate.setPreferredSize(new Dimension(100, 0)); } { lblPSTBatchNo =
							 * new JLabel("Batch No", JLabel.TRAILING); pnlPSTTransDate.add(lblPSTBatchNo);
							 * } { lookupPSTBatchNo = new _JLookup(null, "Select Batch", 0);
							 * pnlPSTTransDate.add(lookupPSTBatchNo, BorderLayout.EAST);
							 * lookupPSTBatchNo.setPreferredSize(new Dimension(100, 0));
							 * lookupPSTBatchNo.setLookupSQL(_PagibigStatusMonitoring.sqlBatchNo()); } }
							 */
						}
					}
					{
						pnlPSTNorthEast = new JPanel(new BorderLayout(3, 3));
						pnlPSTNorth.add(pnlPSTNorthEast, BorderLayout.EAST);
						{
							btnPSTGenerate = new JButton("Generate");
							pnlPSTNorthEast.add(btnPSTGenerate);
							btnPSTGenerate.setActionCommand("Generate");
							btnPSTGenerate.addActionListener(this);
							btnPSTGenerate.setPreferredSize(new Dimension(200, 0));
						}
					}
				}
				{
					pnlPSTCenter = new JPanel(new BorderLayout(3, 3));
					pnlPAGIBIGStatusTagging.add(pnlPSTCenter, BorderLayout.CENTER);
					pnlPSTCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						scrollPSTStatusTagging = new JScrollPane();
						pnlPSTCenter.add(scrollPSTStatusTagging);
						{
							modelPSTStatusTagging = new modelPST_DOA_Signed();
							modelNOA_Expiring = new modelPST_NOA_Expiring();

							tblPSTStatusTagging = new _JTableMain(modelPSTStatusTagging);
							scrollPSTStatusTagging.setViewportView(tblPSTStatusTagging);
							scrollPSTStatusTagging
									.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblPSTStatusTagging.hideColumns("Proj. Name", "Entity ID", "Proj. ID", "PBL ID", "Seq. No");
						}
						{
							rowHeaderPSTStatusTagging = tblPSTStatusTagging.getRowHeader();
							rowHeaderPSTStatusTagging.setModel(new DefaultListModel());
							scrollPSTStatusTagging.setRowHeaderView(rowHeaderPSTStatusTagging);
							scrollPSTStatusTagging.setCorner(JScrollPane.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
				}
				{
					pnlPSTSouth = new JPanel(new BorderLayout(3, 3));
					pnlPAGIBIGStatusTagging.add(pnlPSTSouth, BorderLayout.SOUTH);
					pnlPSTSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					/*
					 * { pnlPSTSouthUpper = new JPanel(new BorderLayout(3, 3));
					 * pnlPSTSouth.add(pnlPSTSouthUpper, BorderLayout.CENTER); { pnlPSTSULabel = new
					 * JPanel(new GridLayout(2, 1, 3, 3)); pnlPSTSouthUpper.add(pnlPSTSULabel,
					 * BorderLayout.WEST); { lblPSTNewStatus = new JLabel("New Status");
					 * pnlPSTSULabel.add(lblPSTNewStatus); } { lblPSTNOADate = new
					 * JLabel("NOA Date"); pnlPSTSULabel.add(lblPSTNOADate); } } { pnlPSTSUComponent
					 * = new JPanel(new GridLayout(2, 1, 3, 3));
					 * pnlPSTSouthUpper.add(pnlPSTSUComponent, BorderLayout.CENTER); {
					 * pnlPSTNewStatus = new JPanel(new BorderLayout(3, 3));
					 * pnlPSTSUComponent.add(pnlPSTNewStatus); { lookupPSTNewStatus = new
					 * _JLookup(null, "Select Status", 0); pnlPSTNewStatus.add(lookupPSTNewStatus,
					 * BorderLayout.WEST); lookupPSTNewStatus.setPreferredSize(new Dimension(50,
					 * 0)); lookupPSTNewStatus.setLookupSQL(_PagibigStatusMonitoring.sqlNewStage());
					 * lookupPSTNewStatus.addLookupListener(new LookupListener() {
					 * 
					 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
					 * event.getSource()).getDataSet();
					 * 
					 * if(data != null){ String new_status_id = (String) data[0]; String new_status
					 * = (String) data[1]; txtPSTNewStatus.setText(new_status);
					 * 
					 * if(new_status_id.equals("06")){ dteNOA.setEditable(true);
					 * dteNOA.getCalendarButton().setEnabled(true); } } } }); } { txtPSTNewStatus =
					 * new _JXTextField(); pnlPSTNewStatus.add(txtPSTNewStatus,
					 * BorderLayout.CENTER); } } { pnlPSTNOADate = new JPanel(new BorderLayout(3,
					 * 3)); pnlPSTSUComponent.add(pnlPSTNOADate); { dteNOA = new
					 * _JDateChooser("MM/dd/yy", "##/##/##", '_'); pnlPSTNOADate.add(dteNOA,
					 * BorderLayout.WEST); dteNOA.setPreferredSize(new Dimension(100, 0)); } } } }
					 */
					{
						pnlPSTSouthLower = new JPanel(new GridLayout(1, 3, 3, 3));
						pnlPSTSouth.add(pnlPSTSouthLower, BorderLayout.CENTER);
						{
							btnPSTPost = new JButton("Post");
							pnlPSTSouthLower.add(btnPSTPost);
							btnPSTPost.setActionCommand(btnPSTPost.getText());
							btnPSTPost.addActionListener(this);
						}
						{
							btnPSTPreview = new JButton("Preview");
							pnlPSTSouthLower.add(btnPSTPreview);
							btnPSTPreview.setActionCommand("Preview");
							btnPSTPreview.addActionListener(this);
						}
						{
							btnPSTClear = new JButton("Clear");
							pnlPSTSouthLower.add(btnPSTClear);
							btnPSTClear.setActionCommand("Clear Status Tagging");
							btnPSTClear.addActionListener(this);
						}
					}
				}
			}
			{
				/** House Inspection **/
				pnlPAGIBIGHouseInspection = new JPanel(new BorderLayout(5, 0));
				tabPAGIBIGCenter.add("House Inspection", pnlPAGIBIGHouseInspection);
				{
					pnlPHINorth = new JPanel(new BorderLayout(3, 3));
					pnlPAGIBIGHouseInspection.add(pnlPHINorth, BorderLayout.NORTH);
					pnlPHINorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{

						JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
						pnlPHINorth.add(panPage, BorderLayout.PAGE_START);
						panPage.setPreferredSize(new Dimension(0, 90));
						panPage.setBorder(JTBorderFactory.createTitleBorder("Company and Project",
								FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel panLabel = new JXPanel(new GridLayout(2, 2, 5, 5));
							panPage.add(panLabel, BorderLayout.LINE_START);
							panLabel.setPreferredSize(new Dimension(200, 0));
							{
								hins_lblCo = new JLabel("Company");
								panLabel.add(hins_lblCo, BorderLayout.CENTER);
								hins_lblCo.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								hins_txtCoID = new _JLookup();
								panLabel.add(hins_txtCoID, BorderLayout.CENTER);
								hins_txtCoID.setReturnColumn(0);
								hins_txtCoID.setLookupSQL(_JInternalFrame.SQL_COMPANY());
								hins_txtCoID.setHorizontalAlignment(JTextField.CENTER);
								hins_txtCoID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();

										if (data != null) {
											hins_txtCo.setText(data[1].toString());
											hins_txtProID
													.setLookupSQL(_JInternalFrame.SQL_PROJECT(hins_txtCoID.getValue()));
										}
									}
								});
							}
							{
								hins_lblPro = new JLabel("Project");
								panLabel.add(hins_lblPro, BorderLayout.CENTER);
								hins_lblPro.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								hins_txtProID = new _JLookup();
								panLabel.add(hins_txtProID, BorderLayout.CENTER);
								hins_txtProID.setReturnColumn(0);
								hins_txtProID.setLookupSQL(_JInternalFrame.SQL_PROJECT());
								hins_txtProID.setHorizontalAlignment(JTextField.CENTER);
								hins_txtProID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();

										if (data != null) {
											hins_txtPro.setText(data[1].toString());
										}
									}
								});
							}
							JXPanel panBox = new JXPanel(new GridLayout(2, 1, 5, 5));
							panPage.add(panBox, BorderLayout.CENTER);
							{
								hins_txtCo = new JTextField();
								panBox.add(hins_txtCo, BorderLayout.CENTER);
								hins_txtCo.setHorizontalAlignment(JTextField.CENTER);
								hins_txtCo.setEditable(false);
							}
							{
								hins_txtPro = new JTextField();
								panBox.add(hins_txtPro, BorderLayout.CENTER);
								hins_txtPro.setHorizontalAlignment(JTextField.CENTER);
								hins_txtPro.setEditable(false);
							}
						}
						{
							JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
							pnlPHINorth.add(panCenter, BorderLayout.CENTER);
							{
								hins_jtpList = new JTabbedPane();
								panCenter.add(hins_jtpList, BorderLayout.CENTER);
								// jtpList.setPreferredSize(new Dimension(0, 600));
								{
									Hins_CreatePages();
									hins_jtpList.addTab("Accounts Ready for HDMF Inspection", panPre);
									hins_jtpList.addTab("HDMF Inspection Status", panPost);
								}
							}
						}

						Setdefaults(hins_txtCoID, hins_txtCo, hins_txtProID, hins_txtPro);

						panDate = new JXPanel(new BorderLayout(5, 5));
						panDate.setPreferredSize(new Dimension(100, 30));
						{
							JXPanel panMsgDiv = new JXPanel(new GridLayout(2, 1));
							panDate.add(panMsgDiv, BorderLayout.PAGE_START);
							panMsgDiv.setPreferredSize(new Dimension(0, 30));
							{
								JLabel lblMsg1 = new JLabel("Select the intended date");
								panMsgDiv.add(lblMsg1, BorderLayout.PAGE_START);
								lblMsg1.setHorizontalAlignment(JTextField.LEFT);
								lblMsg1.setPreferredSize(new Dimension(0, 30));
							}
							{
								JLabel lblMsg2 = new JLabel("then press the close button.");
								panMsgDiv.add(lblMsg2, BorderLayout.PAGE_START);
								lblMsg2.setHorizontalAlignment(JTextField.LEFT);
								lblMsg2.setPreferredSize(new Dimension(0, 30));
							}
						}
						{
							hins_dteInsp = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							panDate.add(hins_dteInsp, BorderLayout.CENTER);
							hins_dteInsp.getCalendarButton().setVisible(true);
							hins_dteInsp.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							hins_dteInsp.setPreferredSize(new Dimension(0, 30));
						}
						/** House Inspection **/
						/*
						 * pnlPHINorthCenter = new JPanel(new BorderLayout(5, 5));
						 * pnlPHINorth.add(pnlPHINorthCenter, BorderLayout.CENTER);
						 * pnlPHINorthCenter.setBorder(JTBorderFactory.
						 * createTitleBorder("Accounts Ready for HDMF Inspection",
						 * FncLookAndFeel.systemFont_Bold.deriveFont(10f))); { pnlPHINCLabel = new
						 * JPanel(new GridLayout(5, 1, 5, 5)); pnlPHINorthCenter.add(pnlPHINCLabel,
						 * BorderLayout.WEST); { lblPHICompany = new JLabel("Company");
						 * pnlPHINCLabel.add(lblPHICompany); } { lblPHIProject = new JLabel("Project");
						 * pnlPHINCLabel.add(lblPHIProject); } { lblPHIPhase = new JLabel("Phase");
						 * pnlPHINCLabel.add(lblPHIPhase); } { lblPHIStage = new JLabel("Stage");
						 * pnlPHINCLabel.add(lblPHIStage); } { lblPHITransDate = new
						 * JLabel("Trans. Date"); pnlPHINCLabel.add(lblPHITransDate); } } {
						 * pnlPHINCComponent = new JPanel(new GridLayout(5, 1, 3, 3));
						 * pnlPHINorthCenter.add(pnlPHINCComponent, BorderLayout.CENTER); {
						 * pnlPHICompany = new JPanel(new BorderLayout(3, 3));
						 * pnlPHINCComponent.add(pnlPHICompany); { lookupPHICompany = new _JLookup(null,
						 * "Select Company", 0); pnlPHICompany.add(lookupPHICompany, BorderLayout.WEST);
						 * lookupPHICompany.setPreferredSize(new Dimension(50, 0));
						 * lookupPHICompany.setLookupSQL(_PagibigStatusMonitoring.sqlCompany());
						 * lookupPHICompany.addLookupListener(new LookupListener() {
						 * 
						 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
						 * event.getSource()).getDataSet();
						 * 
						 * if(data != null){ String co_id = (String) data[0]; String co_name = (String)
						 * data[1]; txtPHICompany.setText(co_name);
						 * lookupPHIProject.setLookupSQL(_PagibigStatusMonitoring.sqlProject(co_id)); }
						 * } }); } { txtPHICompany = new _JXTextField();
						 * pnlPHICompany.add(txtPHICompany, BorderLayout.CENTER); } } { pnlPHIProject =
						 * new JPanel(new BorderLayout(3, 3)); pnlPHINCComponent.add(pnlPHIProject); {
						 * lookupPHIProject = new _JLookup(null, "Select Project", 1,
						 * "Select Company first"); pnlPHIProject.add(lookupPHIProject,
						 * BorderLayout.WEST); lookupPHIProject.setPreferredSize(new Dimension(50, 0));
						 * lookupPHIProject.addLookupListener(new LookupListener() {
						 * 
						 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
						 * event.getSource()).getDataSet();
						 * 
						 * if(data != null){ String proj_id = (String) data[0]; String proj_name =
						 * (String) data[1];
						 * 
						 * txtPHIProject.setText(proj_name);
						 * lookupPHIPhase.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id)); } }
						 * }); } { txtPHIProject = new _JXTextField(); pnlPHIProject.add(txtPHIProject,
						 * BorderLayout.CENTER); } } { pnlPHIPhase = new JPanel(new BorderLayout(3, 3));
						 * pnlPHINCComponent.add(pnlPHIPhase); { lookupPHIPhase = new _JLookup(null,
						 * "Select Phase", 0, "Select Project"); pnlPHIPhase.add(lookupPHIPhase,
						 * BorderLayout.WEST); lookupPHIPhase.setPreferredSize(new Dimension(50, 0));
						 * lookupPHIPhase.addLookupListener(new LookupListener() {
						 * 
						 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
						 * event.getSource()).getDataSet();
						 * 
						 * if(data != null){ //String sub_proj_name = (String) data[1]; String
						 * sub_proj_name = (String) data[0];
						 * 
						 * txtPHIPhase.setText(sub_proj_name); } } }); } { txtPHIPhase = new
						 * _JXTextField(); pnlPHIPhase.add(txtPHIPhase, BorderLayout.CENTER); } } {
						 * pnlPHIStage = new JPanel(new BorderLayout(3, 3));
						 * pnlPHINCComponent.add(pnlPHIStage); { lookupPHIStage = new _JLookup();
						 * pnlPHIStage.add(lookupPHIStage, BorderLayout.WEST);
						 * lookupPHIStage.setPreferredSize(new Dimension(50, 0));
						 * lookupPHIStage.setLookupSQL(_PagibigStatusMonitoring.sqlNewStatus());
						 * lookupPHIStage.addLookupListener(new LookupListener() {
						 * 
						 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
						 * event.getSource()).getDataSet();
						 * 
						 * if(data != null){ String new_stage = (String) data[1];
						 * 
						 * txtPHIStage.setText(new_stage); } } }); } { txtPHIStage = new _JXTextField();
						 * pnlPHIStage.add(txtPHIStage, BorderLayout.CENTER); } } { pnlPHITransDate =
						 * new JPanel(new BorderLayout(3, 3)); pnlPHINCComponent.add(pnlPHITransDate); {
						 * dtePHITransDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						 * pnlPHITransDate.add(dtePHITransDate, BorderLayout.WEST);
						 * dtePHITransDate.setPreferredSize(new Dimension(150, 0)); } } } } {
						 * pnlPHINorthEast = new JPanel(new BorderLayout(3, 3));
						 * pnlPHINorth.add(pnlPHINorthEast, BorderLayout.EAST); { btnPHIGenerate = new
						 * JButton("Generate"); pnlPHINorthEast.add(btnPHIGenerate);
						 * btnPHIGenerate.setActionCommand("Generate House Inspection");
						 * btnPHIGenerate.setPreferredSize(new Dimension(200, 0)); } } } { pnlPHICenter
						 * = new JPanel(new BorderLayout(3, 3));
						 * pnlPAGIBIGHouseInspection.add(pnlPHICenter, BorderLayout.CENTER);
						 * pnlPHICenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); {
						 * scrollPHIHouseInspection = new JScrollPane();
						 * pnlPHICenter.add(scrollPHIHouseInspection, BorderLayout.CENTER); {
						 * modelPHIHouseInspection = new modelPagibigStatusMonitoring_HouseInspection();
						 * tblPHIHouseInspection = new _JTableMain(modelPHIHouseInspection);
						 * scrollPHIHouseInspection.setViewportView(tblPHIHouseInspection);
						 * scrollPHIHouseInspection.setHorizontalScrollBarPolicy(JScrollPane.
						 * HORIZONTAL_SCROLLBAR_ALWAYS); } { rowHeaderPHIHouseInspection =
						 * tblPHIHouseInspection.getRowHeader();
						 * rowHeaderPHIHouseInspection.setModel(new DefaultListModel());
						 * scrollPHIHouseInspection.setRowHeaderView(rowHeaderPHIHouseInspection);
						 * scrollPHIHouseInspection.setCorner(JScrollPane.UPPER_LEFT_CORNER,
						 * FncTables.getRowHeader_Header()); } } } { pnlPHISouth = new JPanel(new
						 * BorderLayout(3, 3)); pnlPAGIBIGHouseInspection.add(pnlPHISouth,
						 * BorderLayout.SOUTH); pnlPHISouth.setBorder(BorderFactory.createEmptyBorder(5,
						 * 5, 5, 5)); { pnlPHISouthUpper = new JPanel(new BorderLayout(3, 3));
						 * pnlPHISouth.add(pnlPHISouthUpper, BorderLayout.CENTER); { pnlPHISULabel = new
						 * JPanel(new GridLayout(2, 1, 3, 3)); pnlPHISouthUpper.add(pnlPHISULabel,
						 * BorderLayout.WEST); { lblPHINewStatus = new JLabel("New Status");
						 * pnlPHISULabel.add(lblPHINewStatus); } { lblPHIHDMFRep = new
						 * JLabel("HDMF Rep"); pnlPHISULabel.add(lblPHIHDMFRep); } } { pnlPHISUComponent
						 * = new JPanel(new GridLayout(2, 1, 3, 3));
						 * pnlPHISouthUpper.add(pnlPHISUComponent, BorderLayout.CENTER); {
						 * pnlPHINewStatus = new JPanel(new BorderLayout(3, 3));
						 * pnlPHISUComponent.add(pnlPHINewStatus); { lookupPHINewStatus = new
						 * _JLookup(null, "Select Status", 0); pnlPHINewStatus.add(lookupPHINewStatus,
						 * BorderLayout.WEST); lookupPHINewStatus.setPreferredSize(new Dimension(50,
						 * 0));
						 * lookupPHINewStatus.setLookupSQL(_PagibigStatusMonitoring.sqlNewStatus());
						 * lookupPHINewStatus.addLookupListener(new LookupListener() {
						 * 
						 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
						 * event.getSource()).getDataSet();
						 * 
						 * if(data != null){ String new_status_id = (String) data[0]; String new_status
						 * = (String) data[1];
						 * 
						 * if(new_status_id.equals("02") || new_status_id.equals("05")){
						 * txtPHIHDMFRep.setEditable(true); }else{ txtPHIHDMFRep.setEditable(false); }
						 * txtPHINewStatus.setText(new_status); } } }); } { txtPHINewStatus = new
						 * _JXTextField(); pnlPHINewStatus.add(txtPHINewStatus, BorderLayout.CENTER); }
						 * } { pnlPHIHDMFRep = new JPanel(new BorderLayout(3, 3));
						 * pnlPHISUComponent.add(pnlPHIHDMFRep); { txtPHIHDMFRep = new _JXTextField();
						 * pnlPHIHDMFRep.add(txtPHIHDMFRep, BorderLayout.WEST);
						 * txtPHIHDMFRep.setPreferredSize(new Dimension(150, 0)); } } } } {
						 * pnlPHISouthLower = new JPanel(new GridLayout(1, 2, 3, 3));
						 * pnlPHISouth.add(pnlPHISouthLower, BorderLayout.SOUTH); { btnPHIPost = new
						 * JButton("Post"); pnlPHISouthLower.add(btnPHIPost);
						 * btnPHIPost.setActionCommand("Post House Inspection");
						 * btnPHIPost.addActionListener(this); } { btnPHIClear = new JButton("Clear");
						 * pnlPHISouthLower.add(btnPHIClear);
						 * btnPHIClear.setActionCommand("Clear House Inspection");
						 * btnPHIClear.addActionListener(this); }
						 */
					}
				}
				/** House Inspection **/
			}

			{
				pnlPAGIBIGMSVSMonitoring = new pnlMSVS_Monitoring(this);
				tabPAGIBIGCenter.addTab("MSVS Monitoring", null, pnlPAGIBIGMSVSMonitoring, null);

				/*
				 * {
				 * 
				 * pnlPMMNorth = new JPanel(new BorderLayout(3, 3));
				 * pnlPAGIBIGMSVSMonitoring.add(pnlPMMNorth, BorderLayout.NORTH);
				 * pnlPMMNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); {
				 * pnlPMMNorthCenter = new JPanel(new BorderLayout(3, 3));
				 * pnlPMMNorth.add(pnlPMMNorthCenter, BorderLayout.CENTER); { pnlPMMNCLabel =
				 * new JPanel(new GridLayout(5, 1, 3, 3)); pnlPMMNorthCenter.add(pnlPMMNCLabel,
				 * BorderLayout.WEST); { lblPMMCompany = new JLabel("Company");
				 * pnlPMMNCLabel.add(lblPMMCompany); } { lblPMMProject = new JLabel("Project");
				 * pnlPMMNCLabel.add(lblPMMProject); } { lblPMMPhase = new JLabel("Phase");
				 * pnlPMMNCLabel.add(lblPMMPhase); } { lblPMMStage = new JLabel("Stage");
				 * pnlPMMNCLabel.add(lblPMMStage); } { lblPMMTransDate = new
				 * JLabel("Trans. Date"); pnlPMMNCLabel.add(lblPMMTransDate); } } {
				 * pnlPMMNCComponent = new JPanel(new GridLayout(5, 1, 3, 3));
				 * pnlPMMNorthCenter.add(pnlPMMNCComponent, BorderLayout.CENTER); {
				 * pnlPMMCompany = new JPanel(new BorderLayout(3, 3));
				 * pnlPMMNCComponent.add(pnlPMMCompany); { lookupPMMCompany = new _JLookup(null,
				 * "Select Company", 0); pnlPMMCompany.add(lookupPMMCompany, BorderLayout.WEST);
				 * lookupPMMCompany.setPreferredSize(new Dimension(50, 0));
				 * lookupPMMCompany.setLookupSQL(_PagibigStatusMonitoring.sqlCompany());
				 * lookupPMMCompany.addLookupListener(new LookupListener() {
				 * 
				 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
				 * event.getSource()).getDataSet();
				 * 
				 * if(data != null){ String co_id = (String) data[0]; String company_name =
				 * (String) data[1];
				 * 
				 * lookupPMMProject.setLookupSQL(_PagibigStatusMonitoring.sqlProject(co_id));
				 * txtPMMCompany.setText(company_name); } } }); } { txtPMMCompany = new
				 * _JXTextField(); pnlPMMCompany.add(txtPMMCompany, BorderLayout.CENTER); } } {
				 * pnlPMMProject = new JPanel(new BorderLayout(3, 3));
				 * pnlPMMNCComponent.add(pnlPMMProject); { lookupPMMProject = new _JLookup(null,
				 * "Select Project", 1, "Please select Company First");
				 * pnlPMMProject.add(lookupPMMProject, BorderLayout.WEST);
				 * lookupPMMProject.setPreferredSize(new Dimension(50, 0));
				 * lookupPMMProject.addLookupListener(new LookupListener() {
				 * 
				 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
				 * event.getSource()).getDataSet();
				 * 
				 * if(data != null){ String proj_id = (String) data[0]; String proj_name =
				 * (String) data[1];
				 * 
				 * lookupPMMPhase.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id));
				 * txtPMMProject.setText(proj_name); } } }); } { txtPMMProject = new
				 * _JXTextField(); pnlPMMProject.add(txtPMMProject, BorderLayout.CENTER); } } {
				 * pnlPMMPhase = new JPanel(new BorderLayout(3, 3));
				 * pnlPMMNCComponent.add(pnlPMMPhase); { lookupPMMPhase = new _JLookup(null,
				 * "Select Phase", 0, "Please select Project First");
				 * pnlPMMPhase.add(lookupPMMPhase, BorderLayout.WEST);
				 * lookupPMMPhase.setPreferredSize(new Dimension(50, 0));
				 * lookupPMMPhase.addLookupListener(new LookupListener() {
				 * 
				 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
				 * event.getSource()).getDataSet();
				 * 
				 * if(data != null){ //String sub_proj_name = (String) data[1]; String
				 * sub_proj_name = (String) data[2];
				 * 
				 * txtPMMPhase.setText(sub_proj_name); } } }); } { txtPMMPhase = new
				 * _JXTextField(); pnlPMMPhase.add(txtPMMPhase, BorderLayout.CENTER); } } {
				 * pnlPMMStage = new JPanel(new BorderLayout(3, 3));
				 * pnlPMMNCComponent.add(pnlPMMStage); { lookupPMMStage = new _JLookup(null,
				 * "Select Stage", 0); pnlPMMStage.add(lookupPMMStage, BorderLayout.WEST);
				 * lookupPMMStage.setPreferredSize(new Dimension(50, 0));
				 * lookupPMMStage.setLookupSQL(_PagibigStatusMonitoring.sqlCurrentStage());
				 * lookupPMMStage.addLookupListener(new LookupListener() {
				 * 
				 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
				 * event.getSource()).getDataSet();
				 * 
				 * if(data != null){ String pmt_stage = (String) data[1];
				 * 
				 * txtPMMStage.setText(pmt_stage); } } }); } { txtPMMStage = new _JXTextField();
				 * pnlPMMStage.add(txtPMMStage, BorderLayout.CENTER); } } { pnlPMMTransDate =
				 * new JPanel(new BorderLayout(3, 3)); pnlPMMNCComponent.add(pnlPMMTransDate); {
				 * dtePMMTransDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				 * pnlPMMTransDate.add(dtePMMTransDate, BorderLayout.WEST);
				 * dtePMMTransDate.setPreferredSize(new Dimension(150, 0)); } } } } {
				 * pnlPMMNorthEast = new JPanel(new BorderLayout(3, 3));
				 * pnlPMMNorth.add(pnlPMMNorthEast, BorderLayout.EAST); { btnPMMGenerate = new
				 * JButton("Generate"); pnlPMMNorthEast.add(btnPMMGenerate);
				 * btnPMMGenerate.setActionCommand("Generate MSVS Monitoring");
				 * btnPMMGenerate.setPreferredSize(new Dimension(200, 0)); } } } { pnlPMMCenter
				 * = new JPanel(new BorderLayout(3, 3));
				 * pnlPAGIBIGMSVSMonitoring.add(pnlPMMCenter, BorderLayout.CENTER);
				 * pnlPMMCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); {
				 * scrollPMMMSVSMonitoring = new JScrollPane();
				 * pnlPMMCenter.add(scrollPMMMSVSMonitoring, BorderLayout.CENTER); {
				 * modelPMMMSVSMonitoring = new modelPagibigStatusMonitoring_MSVSMonitoring();
				 * tblPMMMSVSMonitoring = new _JTableMain(modelPMMMSVSMonitoring);
				 * scrollPMMMSVSMonitoring.setViewportView(tblPMMMSVSMonitoring);
				 * scrollPMMMSVSMonitoring.setHorizontalScrollBarPolicy(JScrollPane.
				 * HORIZONTAL_SCROLLBAR_ALWAYS); } { rowHeaderPMMMSVSMonitoring =
				 * tblPMMMSVSMonitoring.getRowHeader(); rowHeaderPMMMSVSMonitoring.setModel(new
				 * DefaultListModel());
				 * scrollPMMMSVSMonitoring.setRowHeaderView(rowHeaderPMMMSVSMonitoring);
				 * scrollPMMMSVSMonitoring.setCorner(JScrollPane.UPPER_LEFT_CORNER,
				 * FncTables.getRowHeader_Header()); } } } { pnlPMMSouth = new JPanel(new
				 * BorderLayout(3, 3)); pnlPAGIBIGMSVSMonitoring.add(pnlPMMSouth,
				 * BorderLayout.SOUTH); pnlPMMSouth.setBorder(BorderFactory.createEmptyBorder(5,
				 * 5, 5, 5)); { pnlPMMSouthUpper = new JPanel(new BorderLayout(3, 3));
				 * pnlPMMSouth.add(pnlPMMSouthUpper, BorderLayout.CENTER); { pnlPMMSULabel = new
				 * JPanel(new GridLayout(1, 1, 3, 3)); pnlPMMSouthUpper.add(pnlPMMSULabel,
				 * BorderLayout.WEST); { lblPMMNewStatus = new JLabel("New Status");
				 * pnlPMMSULabel.add(lblPMMNewStatus); } } { pnlPMMSUComponent = new JPanel(new
				 * GridLayout(1, 1, 3, 3)); pnlPMMSouthUpper.add(pnlPMMSUComponent,
				 * BorderLayout.CENTER); { pnlPMMNewStatus = new JPanel(new BorderLayout(3, 3));
				 * pnlPMMSUComponent.add(pnlPMMNewStatus); { lookupPMMNewStatus = new
				 * _JLookup(null, "Select New Status", 0);
				 * pnlPMMNewStatus.add(lookupPMMNewStatus, BorderLayout.WEST);
				 * lookupPMMNewStatus.setPreferredSize(new Dimension(50, 0));
				 * lookupPMMNewStatus.addLookupListener(new LookupListener() {
				 * 
				 * public void lookupPerformed(LookupEvent event) { Object [] data = ((_JLookup)
				 * event.getSource()).getDataSet();
				 * 
				 * if(data != null){ String new_status = (String) data[1];
				 * 
				 * txtPMMNewStatus.setText(new_status); } } }); } { txtPMMNewStatus = new
				 * _JXTextField(); pnlPMMNewStatus.add(txtPMMNewStatus, BorderLayout.CENTER); }
				 * } } } { pnlPMMSouthLower = new JPanel(new GridLayout(1, 2, 3, 3));
				 * pnlPMMSouth.add(pnlPMMSouthLower, BorderLayout.SOUTH); { btnPMMPost = new
				 * JButton("Post"); pnlPMMSouthLower.add(btnPMMPost);
				 * btnPMMPost.setActionCommand("Post MSVS Monitoring");
				 * btnPMMPost.addActionListener(this); } { btnPMMClear = new JButton("Clear");
				 * pnlPMMSouthLower.add(btnPMMClear);
				 * btnPMMClear.setActionCommand("Clear MSVS Monitoring");
				 * btnPMMClear.addActionListener(this); } } }
				 */
			}
			{
				pnlPAGIBIGAccountQuery = new JPanel(new BorderLayout(3, 3));
				tabPAGIBIGCenter.add("Account Query", pnlPAGIBIGAccountQuery);
				pnlPAGIBIGAccountQuery.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlPAQNorth = new JPanel(new BorderLayout(3, 3));
					pnlPAGIBIGAccountQuery.add(pnlPAQNorth, BorderLayout.NORTH);
					{
						pnlPAQNorthLabel = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlPAQNorth.add(pnlPAQNorthLabel, BorderLayout.WEST);
						{
							lblPAQClient = new JLabel("Client");
							pnlPAQNorthLabel.add(lblPAQClient);
						}
						{
							lblPAQProject = new JLabel("Project");
							pnlPAQNorthLabel.add(lblPAQProject);
						}
						{
							lblPAQAppliedAmt = new JLabel("Applied Amt");
							pnlPAQNorthLabel.add(lblPAQAppliedAmt);
						}
						{
							lblPAQNOAReleasedDate = new JLabel("NOA Released Date");
							pnlPAQNorthLabel.add(lblPAQNOAReleasedDate);
						}
					}
					{
						pnlPAQNorthComponent = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlPAQNorth.add(pnlPAQNorthComponent, BorderLayout.CENTER);
						{
							pnlPAQClient = new JPanel(new BorderLayout(3, 3));
							pnlPAQNorthComponent.add(pnlPAQClient);
							{
								lookupPAQClient = new _JLookup(null, "Select Client", 0);
								pnlPAQClient.add(lookupPAQClient, BorderLayout.WEST);
								lookupPAQClient.setPreferredSize(new Dimension(100, 0));
								lookupPAQClient.setLookupSQL(_PagibigStatusMonitoring.sqlClient());
								lookupPAQClient.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();

										if (data != null) {
											String entity_name = (String) data[1];
											// String proj_name = (String) data[2];
											String unit_desc = (String) data[3];
											String proj_name = (String) data[4];

											txtPAQClient.setText(entity_name);
											txtPAQPhBlkLt.setText(unit_desc);
											txtPAQProject.setText(proj_name);

										}
									}
								});
							}
							{
								txtPAQClient = new _JXTextField();
								pnlPAQClient.add(txtPAQClient, BorderLayout.CENTER);
							}
						}
						{
							pnlPAQProject = new JPanel(new BorderLayout(3, 3));
							pnlPAQNorthComponent.add(pnlPAQProject);
							{
								txtPAQProject = new _JXTextField();
								pnlPAQProject.add(txtPAQProject, BorderLayout.WEST);
								txtPAQProject.setPreferredSize(new Dimension(200, 0));
							}
							{
								lblPAQPhBlkLt = new JLabel("Ph-Blk-Lt", JLabel.TRAILING);
								pnlPAQProject.add(lblPAQPhBlkLt);
							}
							{
								txtPAQPhBlkLt = new _JXTextField();
								pnlPAQProject.add(txtPAQPhBlkLt, BorderLayout.EAST);
								txtPAQPhBlkLt.setPreferredSize(new Dimension(200, 0));
							}
						}
						{
							pnlPAQAppliedAmt = new JPanel(new BorderLayout(3, 3));
							pnlPAQNorthComponent.add(pnlPAQAppliedAmt);
							{
								txtPAQAppliedAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlPAQAppliedAmt.add(txtPAQAppliedAmt, BorderLayout.WEST);
								txtPAQAppliedAmt.setPreferredSize(new Dimension(200, 0));
								txtPAQAppliedAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtPAQAppliedAmt.setValue(new BigDecimal("0.00"));
							}
							{
								lblPAQApprovedAmt = new JLabel("Approved Amt", JLabel.TRAILING);
								pnlPAQAppliedAmt.add(lblPAQApprovedAmt);
							}
							{
								txtPAQApprovedAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlPAQAppliedAmt.add(txtPAQApprovedAmt, BorderLayout.EAST);
								txtPAQApprovedAmt.setPreferredSize(new Dimension(200, 0));
								txtPAQApprovedAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtPAQApprovedAmt.setValue(new BigDecimal("0.00"));
							}
						}
						{
							pnlPAQNOAReleasedDate = new JPanel(new BorderLayout(3, 3));
							pnlPAQNorthComponent.add(pnlPAQNOAReleasedDate);
							{
								dtePAQNOAReleased = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlPAQNOAReleasedDate.add(dtePAQNOAReleased, BorderLayout.WEST);
								dtePAQNOAReleased.setPreferredSize(new Dimension(150, 0));
							}
						}
					}
				}
				{
					/*
					 * pnlPAQCenter = new JPanel(new BorderLayout(3, 3));
					 * pnlPAGIBIGAccountQuery.add(pnlPAQCenter, BorderLayout.CENTER); {
					 * 
					 * }
					 */
					{
						tabPAQCenter = new _JTabbedPane();
						pnlPAGIBIGAccountQuery.add(tabPAQCenter, BorderLayout.CENTER);
						{
							pnlPAQStatusHistory = new JPanel(new BorderLayout(3, 3));
							tabPAQCenter.add("Pag-IBIG Status History", pnlPAQStatusHistory);
							{
								scrollPAQStatusHistory = new JScrollPane();
								pnlPAQStatusHistory.add(scrollPAQStatusHistory, BorderLayout.CENTER);
								{
									modelPAQ_StatusHistory = new modelPagibigStatusMonitoring_AccountQuery_StatusHistory();
									tblPAQStatusHistory = new _JTableMain(modelPAQ_StatusHistory);
									scrollPAQStatusHistory.setViewportView(tblPAQStatusHistory);
									scrollPAQStatusHistory
											.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								}
								{
									rowHeaderPAQStatusHistory = tblPAQStatusHistory.getRowHeader();
									rowHeaderPAQStatusHistory.setModel(new DefaultListModel());
									scrollPAQStatusHistory.setRowHeaderView(rowHeaderPAQStatusHistory);
									scrollPAQStatusHistory.setCorner(JScrollPane.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
							}
						}
						{
							pnlPAQHouseInspection = new JPanel(new BorderLayout(3, 3));
							tabPAQCenter.add("House Inspection Status History", pnlPAQHouseInspection);
							{
								scrollPAQHouseInspection = new JScrollPane();
								pnlPAQHouseInspection.add(scrollPAQHouseInspection, BorderLayout.CENTER);
								{
									modelPAQ_HouseInspection = new modelPagibigStatusMonitoring_AccountQuery_HouseInspection();
									tblPAQHouseInspection = new _JTableMain(modelPAQ_HouseInspection);
									scrollPAQHouseInspection.setViewportView(tblPAQHouseInspection);
									scrollPAQHouseInspection
											.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								}
								{
									rowHeaderPAQHouseInspection = tblPAQHouseInspection.getRowHeader();
									rowHeaderPAQHouseInspection.setModel(new DefaultListModel());
									scrollPAQHouseInspection.setRowHeaderView(rowHeaderPAQHouseInspection);
									scrollPAQHouseInspection.setCorner(JScrollPane.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
							}
						}
						{
							pnlPAQMSVSStatusHistory = new JPanel(new BorderLayout(3, 3));
							tabPAQCenter.add("MSVS Status History", pnlPAQMSVSStatusHistory);
							{
								scrollPAQ_MSVS_StatusHistory = new JScrollPane();
								pnlPAQMSVSStatusHistory.add(scrollPAQ_MSVS_StatusHistory, BorderLayout.CENTER);
								{
									modelPAQ_MSVS_StatusHistory = new modelPagibigStatusMonitoring_AccountQuery_MSVS_StatusHistory();
									tblPAQ_MSVS_StatusHistory = new _JTableMain(modelPAQ_MSVS_StatusHistory);
									scrollPAQ_MSVS_StatusHistory.setViewportView(tblPAQ_MSVS_StatusHistory);
									scrollPAQ_MSVS_StatusHistory
											.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								}
								{
									rowHeaderPAQ_MSVS_StatusHistory = tblPAQ_MSVS_StatusHistory.getRowHeader();
									rowHeaderPAQ_MSVS_StatusHistory.setModel(new DefaultListModel());
									scrollPAQ_MSVS_StatusHistory.setRowHeaderView(rowHeaderPAQ_MSVS_StatusHistory);
									scrollPAQ_MSVS_StatusHistory.setCorner(JScrollPane.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
							}
						}
					}
				}
			}
			/** Borrower's Validation **/
			{
				panBorVal = new JXPanel(new BorderLayout(3, 3));
				tabPAGIBIGCenter.add("Borrower's Validation", panBorVal);
				panBorVal.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
					panBorVal.add(panPage, BorderLayout.PAGE_START);
					panPage.setPreferredSize(new Dimension(0, 90));
					panPage.setBorder(JTBorderFactory.createTitleBorder("Company and Project",
							FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panLabel = new JXPanel(new GridLayout(2, 2, 5, 5));
						panPage.add(panLabel, BorderLayout.LINE_START);
						panLabel.setPreferredSize(new Dimension(200, 0));
						{
							bor_lblCo = new JLabel("Company");
							panLabel.add(bor_lblCo, BorderLayout.CENTER);
							bor_lblCo.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							bor_txtCoID = new _JLookup();
							panLabel.add(bor_txtCoID, BorderLayout.CENTER);
							bor_txtCoID.setReturnColumn(0);
							bor_txtCoID.setLookupSQL(_JInternalFrame.SQL_COMPANY());
							bor_txtCoID.setHorizontalAlignment(JTextField.CENTER);
							bor_txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									if (data != null) {
										bor_txtCo.setText(data[1].toString());
										bor_txtProID.setLookupSQL(_JInternalFrame.SQL_PROJECT(bor_txtCoID.getValue()));
									}
								}
							});
						}
						{
							bor_lblPro = new JLabel("Project");
							panLabel.add(bor_lblPro, BorderLayout.CENTER);
							bor_lblPro.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							bor_txtProID = new _JLookup();
							panLabel.add(bor_txtProID, BorderLayout.CENTER);
							bor_txtProID.setReturnColumn(0);
							bor_txtProID.setLookupSQL(_JInternalFrame.SQL_PROJECT());
							bor_txtProID.setHorizontalAlignment(JTextField.CENTER);
							bor_txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									if (data != null) {
										bor_txtPro.setText(data[1].toString());
									}
								}
							});
						}
						JXPanel panBox = new JXPanel(new GridLayout(2, 1, 5, 5));
						panPage.add(panBox, BorderLayout.CENTER);
						{
							bor_txtCo = new JTextField();
							panBox.add(bor_txtCo, BorderLayout.CENTER);
							bor_txtCo.setHorizontalAlignment(JTextField.CENTER);
							bor_txtCo.setEditable(false);
						}
						JXPanel panProChk = new JXPanel(new BorderLayout(5, 5));
						panBox.add(panProChk, BorderLayout.CENTER);
						{
							bor_txtPro = new JTextField();
							panProChk.add(bor_txtPro, BorderLayout.CENTER);
							bor_txtPro.setHorizontalAlignment(JTextField.CENTER);
							bor_txtPro.setEditable(false);
						}
						{
							chkBor = new JCheckBox("Show All Attendees");
							// panProChk.add(chkBor, BorderLayout.LINE_END);
							chkBor.setSelected(false);
							chkBor.setEnabled(false);
							chkBor.setHorizontalAlignment(JTextField.RIGHT);
							chkBor.setPreferredSize(new Dimension(150, 0));
							chkBor.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									if (e.getStateChange() == ItemEvent.SELECTED) {
										bor_btnPost.setEnabled(false);
										dteBor.setEnabled(false);
									} else {
										bor_btnPost.setEnabled(true);
										dteBor.setEnabled(true);
									}
								}
							});
						}
					}
					Setdefaults(bor_txtCoID, bor_txtCo, bor_txtProID, bor_txtPro);
					{
						JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
						panBorVal.add(panCenter, BorderLayout.CENTER);
						{
							CreateBorrowerValidationGrid();
							panCenter.add(panBorValGrid, BorderLayout.CENTER);
						}
					}
					{
						JXPanel panEnd = new JXPanel(new BorderLayout(5, 5));
						panBorVal.add(panEnd, BorderLayout.PAGE_END);
						panEnd.setPreferredSize(new Dimension(0, 60));
						{
							JXPanel panBorDate = new JXPanel(new GridLayout(1, 2, 1, 5));
							panEnd.add(panBorDate, BorderLayout.LINE_START);
							panBorDate.setPreferredSize(new Dimension(400, 0));
							{
								JXPanel panDte = new JXPanel(new BorderLayout(5, 5));
								panBorDate.add(panDte, BorderLayout.CENTER);
								panDte.setBorder(JTBorderFactory.createTitleBorder("Date Attended",
										FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									dteBor = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									panDte.add(dteBor, BorderLayout.CENTER);
									dteBor.getCalendarButton().setVisible(true);
									dteBor.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dteBor.setEnabled(false);
								}
								JXPanel panBor = new JXPanel(new BorderLayout(5, 5));
								panBorDate.add(panBor, BorderLayout.CENTER);
								panBor.setBorder(JTBorderFactory.createTitleBorder("Batch No.",
										FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									bor_txtBat = new _JLookup();
									panBor.add(bor_txtBat, BorderLayout.CENTER);
									bor_txtBat.setHorizontalAlignment(JTextField.CENTER);
									bor_txtBat.setReturnColumn(0);
									bor_txtBat.setLookupSQL(SQL_BATCH("Validation"));
									bor_txtBat.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											if (data != null) {
												DisplayBorVal(modelBorVal, rowBorVal, data[0].toString());
												EnableCtrl(4);
											}
										}
									});
								}
							}
						}
						{
							JXPanel panBorCtrl = new JXPanel(new GridLayout(1, 4, 1, 1));
							panEnd.add(panBorCtrl, BorderLayout.CENTER);
							{
								bor_btnGen = new JButton("Generate");
								panBorCtrl.add(bor_btnGen, BorderLayout.CENTER);
								bor_btnGen.setActionCommand("Bor_Gen");
								bor_btnGen.addActionListener(this);
								bor_btnGen.setEnabled(false);
							}
							{
								bor_btnPost = new JButton("Post");
								panBorCtrl.add(bor_btnPost, BorderLayout.CENTER);
								bor_btnPost.setActionCommand("Bor_Post");
								bor_btnPost.addActionListener(this);
								bor_btnPost.setEnabled(false);
							}
							{
								bor_btnPreview = new JButton("Preview");
								panBorCtrl.add(bor_btnPreview, BorderLayout.CENTER);
								bor_btnPreview.setActionCommand("Bor_Preview");
								bor_btnPreview.addActionListener(this);
								bor_btnPreview.setEnabled(false);
							}
							{
								bor_btnExport = new JButton("Export");
								panBorCtrl.add(bor_btnExport, BorderLayout.CENTER);
								bor_btnExport.setActionCommand("Bor_Export");
								bor_btnExport.addActionListener(this);
								bor_btnExport.setEnabled(false);
							}
							EnableCtrl(1);
						}
					}
				}
			}
			/** Borrower's Validation **/

		}
		clearQualifyAccounts();
		clearStatusTagging();
		clearHouseInspection();
		// clearMSVSMonitoring();
	}// XXX END OF INIT GUI

	private void btnPQAState(Boolean pqa_post, Boolean pqa_preview, Boolean pqa_clear) {
		btnPQAPost.setEnabled(pqa_post);
		btnPQAPreview.setEnabled(pqa_preview);
		btnPQAClear.setEnabled(pqa_clear);
	}

	private void btnPSTState(Boolean pst_post, Boolean pst_preview, Boolean pst_clear, Boolean pst_export) {
		btnPSTPost.setEnabled(pst_post);
		btnPSTPreview.setEnabled(pst_preview);
		btnPSTClear.setEnabled(pst_clear);
		btnPSTExport.setEnabled(pst_export);
	}

	private void btnHIState(Boolean hi_post, Boolean hi_preview, Boolean hi_clear, Boolean hi_export) {

	}

	private void btnMSVSState(Boolean msvs_post, Boolean msvs_preview, Boolean msvs_clear, Boolean msvs_export) {

	}

	private void btnAQState(Boolean aq_post, Boolean aq_preview, Boolean aq_clear, Boolean aq_export) {

	}

	private void btnBVState(Boolean bv_post, Boolean bv_preview, Boolean bv_clear, Boolean bv_export) {

	}

	private void clearQualifyAccounts() {

		lookupPQACompany.setValue(null);
		txtPQACompany.setText("");
		lookupPQAProject.setValue(null);
		txtPQAProject.setText("");
		lookupPQAPhase.setValue(null);
		txtPQAPhase.setText("");

		if (cmbPQAStage.getSelectedIndex() == 2) {
			modelPQAQualifiedAccounts.clear();
			scrollPQAQualifiedAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderPQAQualifiedAccounts.setModel(new DefaultListModel());

			modelPQADeficientAccounts.clear();
			scrollPQADeficientAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderPQADeficientAccounts.setModel(new DefaultListModel());
		}

		if (cmbPQAStage.getSelectedIndex() == 5) {
			modelForTCTAnnotation.clear();
			scrollPQAQualifiedAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderPQAQualifiedAccounts.setModel(new DefaultListModel());
		}

		cmbPQAStage.setSelectedIndex(0);

		dtePQATransDate.setDate(Calendar.getInstance().getTime());
	}

	private void clearStatusTagging() {

		lookupPSTCompany.setValue(null);
		txtPSTCompany.setText("");
		lookupPSTProject.setValue(null);
		txtPSTProject.setText("");
		txtPSTCompany.setText("");
		lookupPSTPhase.setValue(null);
		txtPSTPhase.setText("");

		dtePSTTransDate.setDate(Calendar.getInstance().getTime());
		dtePSTTransDate.setEditable(false);

		if (cmbPSTStage.getSelectedIndex() == 1) {
			modelPSTStatusTagging.clear();
			scrollPSTStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderPSTStatusTagging.setModel(new DefaultListModel());
		}

		if (cmbPSTStage.getSelectedIndex() == 2) {
			modelNOA_Expiring.clear();
			scrollPSTStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderPSTStatusTagging.setModel(new DefaultListModel());
		}

		cmbPSTStage.setSelectedIndex(0);

	}

	private void clearHouseInspection() {

	}

	/*
	 * private void clearMSVSMonitoring(){
	 * 
	 * lookupPMMCompany.setValue(null); txtPMMCompany.setText("");
	 * lookupPMMProject.setValue(null); txtPMMProject.setText("");
	 * lookupPMMPhase.setValue(null); txtPMMPhase.setText("");
	 * lookupPMMStage.setValue(null); txtPMMStage.setText("");
	 * 
	 * dtePMMTransDate.setDate(Calendar.getInstance().getTime());
	 * dtePMMTransDate.setEditable(false);
	 * dtePMMTransDate.getCalendarButton().setEnabled(false);
	 * 
	 * lookupPMMNewStatus.setValue(null); txtPMMNewStatus.setText("");
	 * 
	 * }
	 */

	private void previewPQADocsCompletion() {
		System.out.println("Report for Accounts for Docs Completion");

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("company_name", txtPQACompany.getText());
		mapParameters.put("proj_id", lookupPQAProject.getValue());
		mapParameters.put("phase", lookupPQAPhase.getValue());
		mapParameters.put("date_cut_off", dtePQATransDate.getDate());

		FncReport.generateReport("/Reports/rptPagibigStatusMonitoring_PQA_DocsCompletion.jasper",
				"For Doc Completion Accounts", mapParameters);
	}

	private void previewPQADeficientAccounts() {

		System.out.println("Report for Deficient Accounts");

		ArrayList<String> listParameters = new ArrayList<String>();

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("company_name", txtPQACompany.getText());
		mapParameters.put("date_cut_off", dtePQATransDate.getDate());

		FncReport.generateReport("/Reports/rptPagibigStatusMonitoring_PQA_DeficientAccts.jasper", "Deficient Accounts",
				mapParameters);

	}

	private void previewPQAForTCTAnnotation() {
		System.out.println("Dumaan dito sa For TCT Annotation");

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("company_name", txtPQACompany.getText().trim());
		mapParameters.put("proj_id", lookupPQAProject.getValue());
		mapParameters.put("phase", lookupPQAPhase.getValue());
		// mapParameters.put("date_cut_off", dtePQATransDate.getDate());

		FncReport.generateReport("/Reports/.jasper", "For TCT Annotaton", mapParameters);
	}

	private void previewPST_NOAExpiring() {

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("company_name", txtPSTCompany.getText().trim());
		mapParameters.put("proj_id", lookupPSTProject.getValue().trim());
		mapParameters.put("phase", lookupPSTPhase.getValue().trim());
		mapParameters.put("date_cut_off", dtePSTTransDate.getDate());

		FncReport.generateReport("/Reports/rptPagibigStatusMonitoring_PST_NOA_Expiring.jasper", "Expiring NOA",
				mapParameters);
	}

	private boolean toSave() {
		int psm_selected_tab = tabPAGIBIGCenter.getSelectedIndex();
		int pqa_selected_item = cmbPQAStage.getSelectedIndex();
		int pst_selected_item = cmbPSTStage.getSelectedIndex();

		if (psm_selected_tab == 0) {
			if (pqa_selected_item == 1) {

			}
			if (pqa_selected_item == 2) {

			}
			if (pqa_selected_item == 3) {

			}
			if (pqa_selected_item == 4) {

			}
			if (pqa_selected_item == 5) {

			}
			if (pqa_selected_item == 6) {

			}
		}
		if (psm_selected_tab == 1) {

		}
		if (psm_selected_tab == 2) {

		}
		if (psm_selected_tab == 3) {

		}
		if (psm_selected_tab == 4) {

		}
		if (psm_selected_tab == 5) {

		}

		if (tabPAGIBIGCenter.getSelectedIndex() == 0) {
			if (tabPQACenter.getSelectedIndex() == 0) {
				/*
				 * Added by Mann2x; Date Added: December 2016; First filing report; Mann2x's
				 * Mark
				 */
				/*
				 * if (modelPQAQualifiedAccounts.getRowCount() < 1) {
				 * JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
				 * "Generate Accounts Before Posting", "Post", JOptionPane.WARNING_MESSAGE);
				 * return false; }
				 */
				/*
				 * if (cmbPQAStage.getSelectedIndex()==3) {
				 * 
				 * } else { if (modelPQAQualifiedAccounts.getRowCount() < 1) {
				 * JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
				 * "Generate Accounts Before Posting", "Post", JOptionPane.WARNING_MESSAGE);
				 * return false; } }
				 */
			}
		}

		if (tabPAGIBIGCenter.getSelectedIndex() == 1) {
			if (pst_selected_item == 1) {
				if (modelPSTStatusTagging.getRowCount() < 1) {
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Generate Accounts Before Posting",
							"Post", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
			if (pst_selected_item == 2) {

			}
			if (pst_selected_item == 3) {

			}
			if (pst_selected_item == 4) {

			}
			if (pst_selected_item == 5) {

			}
			if (pst_selected_item == 6) {

			}
			if (pst_selected_item == 7) {

			}
		}
		if (tabPAGIBIGCenter.getSelectedIndex() == 2) {

		}
		if (tabPAGIBIGCenter.getSelectedIndex() == 3) {

		}

		return true;
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		int psm_selected_tab = tabPAGIBIGCenter.getSelectedIndex();

		int pqa_selected_item = cmbPQAStage.getSelectedIndex();

		int pqa_selected_tab = tabPQACenter.getSelectedIndex();
		int pst_selected_item = cmbPSTStage.getSelectedIndex();

		// XXX START OF EDITED CODE HERE
		if (actionCommand.equals("Generate")) {

			if (psm_selected_tab == 0) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						int pqa_selected_item = cmbPQAStage.getSelectedIndex();

						FncGlobal.startProgress("Generating Accounts");

						if (pqa_selected_item == 1) {// For SCD Release
							_PagibigStatusMonitoring.displayPQA_SCD_Release(lookupPQAProject.getValue(),
									lookupPQAPhase.getValue(), dtePQATransDate.getDate(), modelPQAQualifiedAccounts,
									rowHeaderPQAQualifiedAccounts);
							tblPQAQualifiedAccounts.packAll();
							scrollPQAQualifiedAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables
									.getRowHeader_Footer(Integer.toString(tblPQAQualifiedAccounts.getRowCount())));
						}

						if (pqa_selected_item == 2) {// For Docs Completion
							System.out.println("Display For Docs Completion");
							if (tabPQACenter.getSelectedIndex() == 0) {
								_PagibigStatusMonitoring.displayPQA_Docs_Completion(lookupPQACompany.getValue(),
										lookupPQAProject.getValue(), lookupPQAPhase.getValue(),
										dtePQATransDate.getDate(), modelPQAQualifiedAccounts,
										rowHeaderPQAQualifiedAccounts);
								tblPQAQualifiedAccounts.packAll();
								scrollPQAQualifiedAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables
										.getRowHeader_Footer(Integer.toString(tblPQAQualifiedAccounts.getRowCount())));
							}
							if (tabPQACenter.getSelectedIndex() == 1) {
								_PagibigStatusMonitoring.displayPQA_Deficient_Accounts(lookupPQAProject.getValue(),
										lookupPQAPhase.getValue(), dtePQATransDate.getDate(), modelPQADeficientAccounts,
										rowHeaderPQADeficientAccounts);
								tblPQADeficientAccounts.packAll();
								scrollPQADeficientAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables
										.getRowHeader_Footer(Integer.toString(tblPQADeficientAccounts.getRowCount())));
							}
							// btnPQAState(true, true, false);
						}

						if (pqa_selected_item == 3) {// For First Filing
							FncGlobal.startProgress("Generating Qualified Accounts");
							_PagibigStatusMonitoring.DisplayFirstFiling(modelFirstFiling, rowFirstFiling,
									lookupPQACompany.getValue(), lookupPQAProject.getValue(), lookupPQAPhase.getValue(),
									dtePQATransDate.getDate().toString(), false);
							FncGlobal.stopProgress();
						}

						if (pqa_selected_item == 4) {

						}

						/*
						 * if(pqa_selected_item == 5){
						 * _PagibigStatusMonitoring.displayPQA_ForTCTAnnotation(lookupPQAProject.
						 * getValue(), lookupPQAPhase.getValue(), dtePQATransDate.getDate(),
						 * modelForTCTAnnotation, rowHeaderPQAQualifiedAccounts);
						 * scrollPQAQualifiedAccounts.setCorner(JScrollPane.LOWER_LEFT_CORNER,
						 * FncTables.getRowHeader_Footer(Integer.toString(tblPQAQualifiedAccounts.
						 * getRowCount()))); tblPQAQualifiedAccounts.packAll(); }
						 */

						if (pqa_selected_item == 6) {

						}

						FncGlobal.stopProgress();
					}
				}).start();
			}

			if (psm_selected_tab == 1) {// Qualify Accounts
				new Thread(new Runnable() {

					@Override
					public void run() {
						int pst_selected_item = cmbPSTStage.getSelectedIndex();

						FncGlobal.startProgress("Generating Accounts for Status Tagging");

						if (pst_selected_item == 1) {// with DOA Signed
							_PagibigStatusMonitoring.displayPST_WithDOASigned(lookupPSTProject.getValue(),
									lookupPSTPhase.getValue(), dtePSTTransDate.getDate(), modelPSTStatusTagging,
									rowHeaderPSTStatusTagging);
							scrollPSTStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER,
									FncTables.getRowHeader_Footer(Integer.toString(tblPSTStatusTagging.getRowCount())));
							tblPSTStatusTagging.packAll();
						}

						if (pst_selected_item == 2) {// For TCT Annotation

							_PagibigStatusMonitoring.displayPST_NOA_Expiring(lookupPSTProject.getValue(),
									lookupPSTPhase.getValue(), dtePSTTransDate.getDate(), modelNOA_Expiring,
									rowHeaderPSTStatusTagging);
							scrollPSTStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER,
									FncTables.getRowHeader_Footer(Integer.toString(tblPSTStatusTagging.getRowCount())));
							tblPSTStatusTagging.packAll();
						}

						if (pst_selected_item == 3) {// With NOA Expiring

						}

						if (pst_selected_item == 4) {// With EPEB

						}

						if (pst_selected_item == 5) {// With Annotated TCT

						}

						if (pst_selected_item == 6) {// For Post Approval

						}

						FncGlobal.stopProgress();
					}
				}).start();
			}

			if (psm_selected_tab == 2) {

			}
			if (psm_selected_tab == 3) {

			}
			if (psm_selected_tab == 4) {

			}
			if (psm_selected_tab == 5) {

			}
		}

		if (actionCommand.equals("Post")) {
			if (toSave()) {
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to post?", "Post",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					if (psm_selected_tab == 0) {// Qualify Accounts

						if (pqa_selected_item == 1) {// For SCD Release

						}
						if (pqa_selected_item == 2) {// For Docs Completion
							_PagibigStatusMonitoring.saveHDMFStatus(cmbPQAStage.getSelectedIndex(),
									dtePQATransDate.getDate(), modelPQAQualifiedAccounts);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfuly posted",
									"Post", JOptionPane.INFORMATION_MESSAGE);
							clearQualifyAccounts();
							btnPQAState(false, true, true);
						}
						if (pqa_selected_item == 3) {// For First Filing
							// _PagibigStatusMonitoring.saveFFStatus(cmbPQAStage.getSelectedIndex(),
							// dtePQATransDate.getDate(), modelFirstFiling);
							_PagibigStatusMonitoring.DisplayFirstFiling(modelFirstFiling, rowFirstFiling,
									lookupPQACompany.getValue(), lookupPQAProject.getValue(), lookupPQAPhase.getValue(),
									dtePQATransDate.getDate().toString(), true);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfuly posted",
									"Post", JOptionPane.INFORMATION_MESSAGE);
							btnPQAState(false, true, true);
						}

						if (pqa_selected_item == 4) {// For HDMF Inspection

						}

						if (pqa_selected_item == 5) {// For TCT Annocation

							_PagibigStatusMonitoring.saveForTCTAnnotation(cmbPQAStage.getSelectedIndex(),
									dtePQATransDate.getDate(), modelForTCTAnnotation);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted",
									"Post", JOptionPane.INFORMATION_MESSAGE);
							btnPQAState(false, true, true);

						}

						if (pqa_selected_item == 6) {// For Post Filing

						}

					}

					if (psm_selected_tab == 1) {// Status Tagging

						// int pst_selected_item = cmbPSTStage.getSelectedIndex();

						if (pst_selected_item == 1) {
							_PagibigStatusMonitoring.saveDOASignedAccounts(dtePSTTransDate.getDate(),
									modelPSTStatusTagging);
							modelPSTStatusTagging.clear();
							rowHeaderPSTStatusTagging.setModel(new DefaultListModel());

							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted",
									"Post", JOptionPane.INFORMATION_MESSAGE, null);
							clearStatusTagging();
						}
						if (pst_selected_item == 2) {
							// _PagibigStatusMonitoring.saveTCTAnnotationAccounts(dtePSTTransDate.getDate(),
							// modelPSTStatusTagging);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted",
									"Post", JOptionPane.INFORMATION_MESSAGE);
						}
						if (pst_selected_item == 3) {

						}
						if (pst_selected_item == 4) {

						}
						if (pst_selected_item == 5) {

						}
						if (pst_selected_item == 6) {

						}

					}

					if (psm_selected_tab == 2) {// House Inspection

					}

					if (psm_selected_tab == 3) {

					}
					if (psm_selected_tab == 4) {

					}
					if (psm_selected_tab == 5) {

					}
				}
			}
		}

		if (actionCommand.equals("Preview")) {
			if (psm_selected_tab == 0) {
				if (pqa_selected_item == 2) {
					if (pqa_selected_tab == 0) {
						if (modelPQAQualifiedAccounts.getRowCount() != 0) {
							previewPQADocsCompletion();
						} else {
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Generate Accounts First",
									"Preview", JOptionPane.WARNING_MESSAGE);
						}
					}
					if (pqa_selected_tab == 1) {
						if (modelPQADeficientAccounts.getRowCount() != 0) {
							previewPQADeficientAccounts();
						} else {
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Generate Accounts First",
									"Preview", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
				if (pqa_selected_item == 5) {
					if (modelForTCTAnnotation.getRowCount() != 0) {
						previewPQAForTCTAnnotation();
					} else {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Generate Accounts First", "Preview",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			if (psm_selected_tab == 1) {

				if (pst_selected_item == 2) {// For NOA Extension
					if (modelNOA_Expiring.getRowCount() != 0) {
						System.out.println("Dumaan dito sa preview ng NOA Expiring");
						previewPST_NOAExpiring();
					} else {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Generate Accoutns First", "Preview",
								JOptionPane.WARNING_MESSAGE);
					}
				}

			}
			if (psm_selected_tab == 2) {

			}
			if (psm_selected_tab == 3) {

			}
			if (psm_selected_tab == 4) {

			}
			if (psm_selected_tab == 5) {

			}
		}

		if (actionCommand.equals("Export")) {
			if (psm_selected_tab == 0) {
				if (pqa_selected_item == 2) {
					String col_names[] = { "Div.", "Dept.", "Client", "Unit", "OR Date", "MSVS Approved",
							"MSVS Awaiting Release", "MSVS w/ Findings", "SCD Out", "SCD In", "Payslip", "ESAV",
							"Pmt Status", "1st Date Prepared", "1st Received Date", "1st Relation", "1st Received By",
							"Final Date Prepared", "Final Received Date", "Final Relation", "Final Received By" };

					String SQL = "SELECT * FROM view_docs_completion_deficient_accts_export(NULLIF('"
							+ lookupPQAProject.getValue() + "', 'null'), NULLIF('" + lookupPQAPhase.getValue()
							+ "', 'null'), NULLIF('" + dtePQATransDate.getDate()
							+ "', 'null')::TIMESTAMP WITHOUT TIME ZONE);";

					FncGlobal.startProgress("Creating Spreadsheet");
					FncGlobal.CreateXLS(col_names, SQL, "Deficient Accounts");
				}
				if (pqa_selected_item == 3) {

				}
				if (pqa_selected_item == 4) {

				}
				if (pqa_selected_item == 5) {

				}
			}
			if (psm_selected_tab == 1) {
				if (pst_selected_item == 1) {

				}
				if (pst_selected_item == 2) {
					String col_names[] = { "#", "Unit", "Name", "Loanable Amt", "Birthyear", "Age",
							"NOA Released Date" };

					String SQL = "SELECT c_count, c_unit_desc, c_entity_name, c_loanable_amt, c_birth_year, c_age, c_noa_released_date \n"
							+ "FROM sp_generate_pagibig_status_noa_expiring(NULLIF('" + lookupPSTProject.getValue()
							+ "', 'null'), NULLIF('" + lookupPSTPhase.getValue() + "', 'null'), NULLIF('"
							+ dtePQATransDate.getDate() + "', 'null')::TIMESTAMP WITHOUT TIME ZONE);";

					FncGlobal.startProgress("Creating Spreadsheet");
					FncGlobal.CreateXLS(col_names, SQL, "NOA Extension");
				}
				if (pst_selected_item == 3) {

				}

			}
			if (psm_selected_tab == 2) {

			}
			if (psm_selected_tab == 3) {

			}
			if (psm_selected_tab == 4) {

			}
			if (psm_selected_tab == 5) {

			}
		}
		// XXX END OF EDITED CODE HERE

		if (actionCommand.equals("Export")) {

			if (pqa_selected_item == 3) {
				String col_names[] = { "Name", "Project", "Phase", "Block", "Lot", "Class", "DP Stage", "DP Term",
						"Pay Status", "House Percentage", "Findings", "NTP", "Last Compliance", "Docs Complete" };

				String strSQL = "SELECT c_name, c_proj_name, c_phase, c_block, c_lot, c_class, c_dpstage, \n"
						+ "c_dpterm, c_paystatus, c_housepctg, c_findings, c_ntpdate, c_lastcomplied, c_docscomplete \n"
						+ "FROM view_hdmf_first_filing('" + lookupPQACompany.getValue() + "', '"
						+ lookupPQAProject.getValue() + "', '" + lookupPQAPhase.getValue() + "', '"
						+ dtePQATransDate.getDate().toString() + "', true);";

				FncGlobal.startProgress("Creating spreadsheet.");
				FncGlobal.CreateXLS(col_names, strSQL, "FirstFiling");
				FncGlobal.stopProgress();
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "This part is not finished yet.");
			}
		}

		if (actionCommand.equals("Preview Qualify Accounts")) {
			String company_logo = RequestForPayment.sql_getCompanyLogo();

			if (pqa_selected_item == 0) {

			}

			if (pqa_selected_item == 1) {// For SCD Release

			}

			if (pqa_selected_item == 2) {// For Docs Completion

			}

			if (pqa_selected_item == 3) {
				/*
				 * Added by Mann2x; Date Added: December 2016; First filing report; Mann2x's
				 * Mark
				 */
				System.out.println("");
				System.out.println("First Filing Selected");

				SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
				Date dateObj = new Date();
				String strDate = (String) sdfTo.format(dateObj);

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", txtPQACompany.getText());
				mapParameters.put("02_AsOfDate", strDate);
				mapParameters.put("03_CoID", lookupPQACompany.getValue());
				mapParameters.put("04_ProID", lookupPQAProject.getValue());
				mapParameters.put("05_Phase", lookupPQAPhase.getValue());
				mapParameters.put("06_Project", txtPQAProject.getText());
				mapParameters.put("07_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
				mapParameters.put("08_Logo",
						this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				FncReport.generateReport("/Reports/rpt_hdmf_first_filing.jasper", "Clients Qualified for First Filing",
						"", mapParameters);
			}

			if (pqa_selected_item == 4) {
				System.out.println("Wla pang laman yung preview");
			}
		}

		if (actionCommand.equals("Post Qualify Accounts")) {
			if (toSave()) {
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to post?", "Post",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					if (cmbPQAStage.getSelectedIndex() == 3) {
						_PagibigStatusMonitoring.saveFFStatus(dtePQATransDate.getDate(), modelFirstFiling);
						_PagibigStatusMonitoring.DisplayFirstFiling(modelFirstFiling, rowFirstFiling,
								lookupPQACompany.getValue(), lookupPQAProject.getValue(), lookupPQAPhase.getValue(),
								dtePQATransDate.getDate().toString(), true);
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfuly posted", "Post",
								JOptionPane.INFORMATION_MESSAGE);
						// btnStatePQAState(false, true, true);
					} else {
						_PagibigStatusMonitoring.saveHDMFStatus(cmbPQAStage.getSelectedIndex(),
								dtePQATransDate.getDate(), modelPQAQualifiedAccounts);
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfuly posted", "Post",
								JOptionPane.INFORMATION_MESSAGE);
						// btnStatePQAState(false, true, true);
					}
				}
			}
		}

		if (actionCommand.equals("Clear Qualify Accounts")) {
			clearQualifyAccounts();
		}

		if (actionCommand.equals("Clear Status Tagging")) {
			clearStatusTagging();
		}

		if (actionCommand.equals("Clear MSVS Monitoring")) {
			// clearMSVSMonitoring();
		}

		/** House Inspection **/
		if (actionCommand.equals("hins_Gen")) {// XXX MOVE THESE CODES TO POSTING
			if (displayPreIns(modelPreInsp, rowPreInsp)) {
				hins_txtBatch.setValue("");
				preButton(2);
			}
		} else if (actionCommand.equals("hins_Preview")) {
			String company_logo = RequestForPayment.sql_getCompanyLogo();

			SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
			Date dateObj = new Date();
			String strDate = (String) sdfTo.format(dateObj);

			strDate = "As Of: " + strDate;

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", hins_txtCo.getText());
			mapParameters.put("02_AsOfDate", strDate);
			mapParameters.put("03_CoID", hins_txtCoID.getValue());
			mapParameters.put("04_ProID", hins_txtProID.getValue());
			mapParameters.put("06_Project", hins_txtPro.getText());
			mapParameters.put("07_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("08_Logo",
					this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters.put("09_Batch", hins_txtBatch.getText());
			FncReport.generateReport("/Reports/rpt_hdmf_pre_inspection.jasper", "HDMF Inspection Report", "",
					mapParameters);
		} else if (actionCommand.equals("hins_Export")) {
			String[] strCol = { "Name", "Phase", "Block", "Lot", "Date Filed", "Noa Released", "House %", "to CMD",
					"CMD to PMD" };
			String strSQL = "select name, Phase, Block, Lot, date_filed, noa_released, pctg, to_cmd, cmd_pmd from view_hdmf_pre_inspection('"
					+ hins_txtBatch.getValue() + "') order by name";

			FncGlobal.startProgress("Posting");
			FncGlobal.CreateXLS(strCol, strSQL, "Accounts for HDMF Inspection");
			FncGlobal.stopProgress();
		} else if (actionCommand.equals("hins_Post")) {
			FncGlobal.startProgress("Posting");
			String strBat = "";
			String strDate = "";

			if (tblPreInsp.getRowCount() > 0) {
				strBat = GetBatch("Inspection");

				int Option = JOptionPane.showOptionDialog(getContentPane(), panDate, "Released Date",
						JOptionPane.DEFAULT_OPTION, JOptionPane.OK_CANCEL_OPTION, null, new Object[] {}, null);
				if (Option == JOptionPane.CLOSED_OPTION) {
					try {
						strDate = hins_dteInsp.getDate().toString();
					} catch (java.lang.ArrayIndexOutOfBoundsException e) {

					}
				}

				for (int x = 0; x < modelPreInsp.getRowCount(); x++) {
					if ((Boolean) modelPreInsp.getValueAt(x, 1)) {
						/* GetRec(); */
						pgUpdate dbIns = new pgUpdate();
						String strIns = "insert into rf_hdmf_insp_header (insp_rec_id, batch_no, entity_id, projcode, pbl_id, seq_no, date_filed, created_by, date_created) \n"
								+ "values (" + GetRec("Inspection") + ", '" + strBat + "', '"
								+ modelPreInsp.getValueAt(x, 10).toString() + "', '"
								+ modelPreInsp.getValueAt(x, 11).toString() + "', \n" + "'"
								+ modelPreInsp.getValueAt(x, 12).toString() + "', "
								+ modelPreInsp.getValueAt(x, 13).toString() + ", '" + strDate + "'::date, '"
								+ UserInfo.EmployeeCode + "', CURRENT_DATE::date)";
						dbIns.executeUpdate(strIns, false);
						dbIns.commit();
					}
				}
				hins_txtBatch.setText(strBat);
				displayInsBatch(modelPreInsp, rowPreInsp, strBat);
			}
			preButton(3);
			JOptionPane.showMessageDialog(getContentPane(), "Ready for inspection batch created.");
			FncGlobal.stopProgress();
		} else if (actionCommand.equals("hins_Add")) {
			ClearBox();
			EnableBox(true);
			postButton(2);
		} else if (actionCommand.equals("hins_Save")) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save these details?",
					"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				String strStat = "";
				strStat = hins_cboStatus.getSelectedItem().toString().substring(0, 2);

				pgUpdate dbRev = new pgUpdate();
				String strSQL = "insert into rf_hdmf_insp_detail (insp_detail_rec_id, entity_id, pbl_id, insp_status_id, trans_date, \n"
						+ "created_by, date_created, hdmf_rep, remarks, status_id) values (" + GetRec("Inspected")
						+ ", '" + hins_txtClientID.getValue() + "',  \n" + "'" + hins_txtUnitID.getText() + "', '"
						+ strStat + "', '" + hins_dteDate.getText() + "', \n" + "'" + UserInfo.EmployeeCode
						+ "', CURRENT_DATE, '" + hins_txtRep.getText() + "', '" + hins_txtRemarks.getText() + "', 'A')";

				dbRev.executeUpdate(strSQL, true);
				dbRev.commit();

				displayPostIns(modelPostInsp, rowPostInsp, hins_txtClientID.getValue(), hins_txtUnitID.getText());

				ClearBox();
				EnableBox(false);
				postButton(1);

				JOptionPane.showMessageDialog(getContentPane(), "Saving successful!");
			} else {
				ClearBox();
				EnableBox(false);
				postButton(1);
			}
		} else if (actionCommand.equals("hins_Cancel")) {
			ClearBox();
			EnableBox(false);
			postButton(1);
		} else if (actionCommand.equals("Bor_Gen")) {
			bor_txtBat.setText("");
			DisplayBorVal(modelBorVal, rowBorVal, "");
			EnableCtrl(2);
		} else if (actionCommand.equals("Bor_Post")) {
			if (JOptionPane.showConfirmDialog(getContentPane(),
					"Are you sure you want to proceed? \n" + "If so, " + dteBor.getDate().toString()
							+ " will be set as date attended. Continue?",
					"Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				Boolean blnChk = false;
				String strBat = GetBatch("Validation");
				bor_txtBat.setValue(strBat);

				System.out.println("");
				System.out.println("strBat: " + strBat);

				for (int x = 0; x < modelBorVal.getRowCount(); x++) {
					if ((Boolean) modelBorVal.getValueAt(x, 1)) {
						blnChk = true;
						String strEntID = (String) modelBorVal.getValueAt(x, 8).toString();
						String strProID = (String) modelBorVal.getValueAt(x, 9).toString();
						String strUnitID = (String) modelBorVal.getValueAt(x, 10).toString();
						String strSeq = (String) modelBorVal.getValueAt(x, 11).toString();
						String strStatus = "107";
						String strDate = dteBor.getDate().toString();
						String strUser = UserInfo.EmployeeCode;

						pgUpdate dbBor = new pgUpdate();
						String strSQL = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n"
								+ "VALUES ('" + strEntID + "', '" + strProID + "', '" + strUnitID + "', " + strSeq
								+ ", '" + strStatus + "', '" + strDate + "', '" + strDate + "', 'A', '" + strBat
								+ "', '" + strUser + "');";
						System.out.println("");
						System.out.println(strSQL);

						dbBor.executeUpdate(strSQL, false);
						dbBor.commit();
					}
				}

				if (!blnChk) {
					JOptionPane.showMessageDialog(getContentPane(), "No row was selected.");
				} else {
					DisplayBorVal(modelBorVal, rowBorVal, strBat);
					EnableCtrl(3);
					JOptionPane.showMessageDialog(getContentPane(), "Attendees are successfully tagged.");
				}
			}
		} else if (actionCommand.equals("Bor_Preview")) {
			String company_logo = RequestForPayment.sql_getCompanyLogo();

			SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
			Date dateObj = new Date();
			String strDate = (String) sdfTo.format(dateObj);

			strDate = "As Of: " + strDate;

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", bor_txtCo.getText());
			mapParameters.put("02_AsOfDate", strDate);
			mapParameters.put("03_CoID", bor_txtCoID.getValue());
			mapParameters.put("04_ProID", bor_txtProID.getValue());
			mapParameters.put("06_Project", bor_txtPro.getText());
			mapParameters.put("07_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("08_Logo",
					this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters.put("09_Batch", bor_txtBat.getValue());
			FncReport.generateReport("/Reports/rpt_hdmf_borrower_validation.jasper", "Borrower's Validation", "",
					mapParameters);
		} else if (actionCommand.equals("Bor_Export")) {
			String col_names[] = { "Name", "Phase", "Block", "Lot", "Date Attended", "Loan Filed", "Docs Complete" };
			String strSQL = "select name, phase, block, lot, date, docscomplete, loanfiled from view_hdmf_bor_validation('"
					+ bor_txtBat.getValue() + "');";

			FncGlobal.startProgress("Creating spreadsheet.");
			FncGlobal.CreateXLS(col_names, strSQL, "Borrower's Validation");
			FncGlobal.stopProgress();
		}
		/** House Inspection **/
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
				tblFirstFiling.getColumnModel().getColumn(11).setPreferredWidth(255);
				tblFirstFiling.getColumnModel().getColumn(13).setPreferredWidth(100);
				tblFirstFiling.getColumnModel().getColumn(14).setPreferredWidth(100);

				rowFirstFiling = tblFirstFiling.getRowHeader();
				rowFirstFiling.setModel(new DefaultListModel());
				scrFirstFiling.setRowHeaderView(rowFirstFiling);
				scrFirstFiling.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}

	private void CreateBorrowerValidationGrid() {
		panBorValGrid = new JXPanel(new GridLayout(1, 1, 0, 0));
		panBorValGrid.setOpaque(isOpaque());
		{
			scrollBorVal = new JScrollPane();
			panBorValGrid.add(scrollBorVal, BorderLayout.CENTER);
			scrollBorVal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelBorVal = new model_hdmf_borrower_val();
				modelBorVal.setEditable(false);

				tblBorVal = new _JTableMain(modelBorVal);
				tblBorVal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblBorVal.getSelectedRow());
						}
					}
				});

				rowBorVal = tblBorVal.getRowHeader();
				scrollBorVal.setViewportView(tblBorVal);

				tblBorVal.getColumnModel().getColumn(0).setPreferredWidth(255);
				tblBorVal.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblBorVal.getColumnModel().getColumn(2).setPreferredWidth(150);
				tblBorVal.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblBorVal.getColumnModel().getColumn(4).setPreferredWidth(50);
				tblBorVal.getColumnModel().getColumn(5).setPreferredWidth(50);
				tblBorVal.getColumnModel().getColumn(6).setPreferredWidth(150);
				tblBorVal.getColumnModel().getColumn(7).setPreferredWidth(150);

				tblBorVal.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");

				rowBorVal = tblBorVal.getRowHeader();
				rowBorVal.setModel(new DefaultListModel());
				scrollBorVal.setRowHeaderView(rowBorVal);
				scrollBorVal.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}

	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;

		/* Company Default */
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			lookupPQACompany.setValue("");
			txtPQACompany.setText("");
		} else {
			lookupPQACompany.setValue(_RealTimeDebit.GetValue("SELECT co_id FROM mf_company LIMIT 1"));

			try {
				String strCo = _RealTimeDebit.GetValue(
						"SELECT company_name FROM mf_company WHERE co_id = '" + lookupPQACompany.getValue() + "'");
				System.out.println(strCo);
				txtPQACompany.setText(strCo);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtCoID: " + lookupPQACompany.getValue());
				System.out.println("txtCoName: " + _RealTimeDebit.GetValue(
						"SELECT company_name FROM mf_company WHERE co_id = '" + lookupPQACompany.getValue() + "'"));
			}
		}

		/* Project Default */
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			lookupPQAProject.setText("");
			txtPQAProject.setText("[  Project  ]");
		} else {
			lookupPQAProject.setValue(_RealTimeDebit.GetValue("SELECT proj_id FROM mf_project LIMIT 1"));

			try {
				String strPro = _RealTimeDebit.GetValue(
						"SELECT proj_name FROM mf_project WHERE proj_id = '" + lookupPQAProject.getValue() + "'");
				System.out.println(strPro);
				txtPQAProject.setText(strPro);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtProjectID: " + lookupPQAProject.getValue());
				System.out.println(_RealTimeDebit.GetValue(
						"SELECT proj_name FROM mf_project WHERE proj_id = '" + lookupPQAProject.getValue() + "'"));
			}
		}
	}

	/** House Inspection **/
	public void Hins_CreatePages() {
		panPre = new JXPanel(new BorderLayout(5, 5));
		panPre.setBorder(new EmptyBorder(5, 5, 5, 5));
		panPre.setPreferredSize(new Dimension(0, 335));
		panPre.setOpaque(isOpaque());
		{
			JXPanel panBatBut = new JXPanel(new GridLayout(1, 2, 5, 5));
			panPre.add(panBatBut, BorderLayout.PAGE_END);
			panBatBut.setPreferredSize(new Dimension(0, 60));
			{
				JXPanel panBatch = new JXPanel(new BorderLayout(5, 5));
				panBatBut.add(panBatch, BorderLayout.LINE_START);
				panBatch.setBorder(
						JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					hins_lblBatch = new JLabel("Batch No.");
					panBatch.add(hins_lblBatch, BorderLayout.LINE_START);
					hins_lblBatch.setPreferredSize(new Dimension(100, 0));
				}
				{
					hins_txtBatch = new _JLookup();
					panBatch.add(hins_txtBatch, BorderLayout.CENTER);
					hins_txtBatch.setReturnColumn(0);
					hins_txtBatch.setLookupSQL(SQL_BATCH("Inspection"));
					hins_txtBatch.setHorizontalAlignment(JTextField.CENTER);
					hins_txtBatch.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();

							if (data != null) {
								displayInsBatch(modelPreInsp, rowPreInsp, hins_txtBatch.getText());
								preButton(3);
							}
						}
					});
				}
				JXPanel panTab1But = new JXPanel(new GridLayout(1, 4, 1, 1));
				panBatBut.add(panTab1But, BorderLayout.CENTER);
				{
					hins_btnGen = new JButton("Generate");
					panTab1But.add(hins_btnGen, BorderLayout.CENTER);
					hins_btnGen.setActionCommand("hins_Gen");
					hins_btnGen.addActionListener(this);
					hins_btnGen.setEnabled(false);
				}
				{
					hins_btnPost = new JButton("Post");
					panTab1But.add(hins_btnPost, BorderLayout.CENTER);
					hins_btnPost.setActionCommand("hins_Post");
					hins_btnPost.addActionListener(this);
					hins_btnPost.setEnabled(false);
				}
				{
					hins_btnPreview = new JButton("Preview");
					panTab1But.add(hins_btnPreview, BorderLayout.CENTER);
					hins_btnPreview.setActionCommand("hins_Preview");
					hins_btnPreview.addActionListener(this);
					hins_btnPreview.setEnabled(false);
				}
				{
					hins_btnExport = new JButton("Export");
					panTab1But.add(hins_btnExport, BorderLayout.CENTER);
					hins_btnExport.setActionCommand("hins_Export");
					hins_btnExport.addActionListener(this);
					hins_btnExport.setEnabled(false);
				}

				preButton(1);
			}
		}
		{
			scrollPreInsp = new JScrollPane();
			panPre.add(scrollPreInsp, BorderLayout.CENTER);
			scrollPreInsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPreInsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			{
				modelPreInsp = new model_hdmf_preInspection();
				tblPreInsp = new _JTableMain(modelPreInsp);

				rowPreInsp = tblPreInsp.getRowHeader();
				scrollPreInsp.setViewportView(tblPreInsp);

				tblPreInsp.getColumnModel().getColumn(0).setPreferredWidth(250);
				tblPreInsp.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblPreInsp.getColumnModel().getColumn(2).setPreferredWidth(60);
				tblPreInsp.getColumnModel().getColumn(3).setPreferredWidth(60);
				tblPreInsp.getColumnModel().getColumn(4).setPreferredWidth(60);

				tblPreInsp.getColumnModel().getColumn(5).setPreferredWidth(100);
				tblPreInsp.getColumnModel().getColumn(6).setPreferredWidth(100);
				tblPreInsp.getColumnModel().getColumn(7).setPreferredWidth(75);

				tblPreInsp.getColumnModel().getColumn(8).setPreferredWidth(100);
				tblPreInsp.getColumnModel().getColumn(9).setPreferredWidth(100);

				tblPreInsp.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
			}
			{
				rowPreInsp = tblPreInsp.getRowHeader();
				rowPreInsp.setModel(new DefaultListModel());
				scrollPreInsp.setRowHeaderView(rowPreInsp);
				scrollPreInsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}

		panPost = new JXPanel(new BorderLayout(5, 5));
		panPost.setBorder(new EmptyBorder(5, 5, 5, 5));
		panPost.setOpaque(isOpaque());
		panPost.setPreferredSize(new Dimension(0, 335));
		{
			JXPanel panClient = new JXPanel(new BorderLayout(5, 5));
			panPost.add(panClient, BorderLayout.PAGE_START);
			panClient.setPreferredSize(new Dimension(0, 90));
			panClient.setBorder(JTBorderFactory.createTitleBorder("Client Details",
					FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				JXPanel panLabel = new JXPanel(new GridLayout(2, 2, 5, 5));
				panClient.add(panLabel, BorderLayout.LINE_START);
				panLabel.setPreferredSize(new Dimension(200, 0));
				{
					hins_lblClient = new JLabel("Client");
					panLabel.add(hins_lblClient, BorderLayout.CENTER);
					hins_lblClient.setHorizontalAlignment(JTextField.LEADING);
				}
				{
					hins_txtClientID = new _JLookup();
					panLabel.add(hins_txtClientID, BorderLayout.CENTER);
					hins_txtClientID.setReturnColumn(2);
					hins_txtClientID.setLookupSQL(CLIENT());
					hins_txtClientID.setHorizontalAlignment(JTextField.CENTER);
					hins_txtClientID.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();

							if (data != null) {
								hins_txtClient.setText(data[1].toString());
								hins_txtUnitID.setText(data[3].toString());
								hins_txtUnit.setText(data[0].toString());

								FillInsCombo(data[2].toString());
								displayPostIns(modelPostInsp, rowPostInsp, data[2].toString(), data[3].toString());

								ClearBox();
								postButton(1);
							}
						}
					});
				}
				{
					hins_lblUnit = new JLabel("Unit");
					panLabel.add(hins_lblUnit, BorderLayout.CENTER);
					hins_lblUnit.setHorizontalAlignment(JTextField.LEADING);
				}
				{
					hins_txtUnitID = new JTextField("");
					panLabel.add(hins_txtUnitID, BorderLayout.CENTER);
					hins_txtUnitID.setHorizontalAlignment(JTextField.CENTER);
					hins_txtUnitID.setEditable(false);
				}
				JXPanel panBox = new JXPanel(new GridLayout(2, 1, 5, 5));
				panClient.add(panBox, BorderLayout.CENTER);
				{
					hins_txtClient = new JTextField("");
					panBox.add(hins_txtClient, BorderLayout.CENTER);
					hins_txtClient.setHorizontalAlignment(JTextField.CENTER);
					hins_txtClient.setEditable(false);
				}
				{
					hins_txtUnit = new JTextField("");
					panBox.add(hins_txtUnit, BorderLayout.CENTER);
					hins_txtUnit.setHorizontalAlignment(JTextField.CENTER);
					hins_txtUnit.setEditable(false);
				}
			}
			JXPanel panStatus = new JXPanel(new BorderLayout(5, 5));
			panPost.add(panStatus, BorderLayout.CENTER);
			panStatus.setBorder(JTBorderFactory.createTitleBorder("Inspection Status",
					FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				JXPanel panDet = new JXPanel(new BorderLayout(5, 5));
				panStatus.add(panDet, BorderLayout.CENTER);
				{
					JXPanel panDetDiv1 = new JXPanel(new GridLayout(3, 1, 5, 5));
					panDet.add(panDetDiv1, BorderLayout.PAGE_START);
					panDetDiv1.setPreferredSize(new Dimension(0, 90));
					{
						JXPanel panFirst = new JXPanel(new GridLayout(1, 2, 5, 5));
						panDetDiv1.add(panFirst, BorderLayout.CENTER);
						{
							JXPanel panDateInsp = new JXPanel(new BorderLayout(5, 5));
							panFirst.add(panDateInsp, BorderLayout.CENTER);
							{
								hins_lblDate = new JLabel("Trans. Date");
								panDateInsp.add(hins_lblDate, BorderLayout.LINE_START);
								hins_lblDate.setPreferredSize(new Dimension(100, 0));
							}
							{
								hins_dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panDateInsp.add(hins_dteDate, BorderLayout.CENTER);
								hins_dteDate.getCalendarButton().setVisible(true);
								hins_dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								hins_dteDate.setEnabled(false);
							}
							JXPanel panStat = new JXPanel(new BorderLayout(5, 5));
							panFirst.add(panStat, BorderLayout.CENTER);
							{
								hins_lblStatus = new JLabel("Status");
								panStat.add(hins_lblStatus, BorderLayout.LINE_START);
								hins_lblStatus.setPreferredSize(new Dimension(100, 0));
								hins_lblStatus.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								hins_cboStatus = new JComboBox();
								panStat.add(hins_cboStatus, BorderLayout.CENTER);
								hins_cboStatus.setEnabled(false);
							}
							FillInsCombo("");
							// hins_cboStatus.setSelectedIndex(0);
						}
						JXPanel panSecond = new JXPanel(new BorderLayout(5, 5));
						panDetDiv1.add(panSecond, BorderLayout.CENTER);
						{
							hins_lblRep = new JLabel("HDMF Rep.");
							panSecond.add(hins_lblRep, BorderLayout.LINE_START);
							hins_lblRep.setPreferredSize(new Dimension(100, 0));
						}
						{
							hins_txtRep = new JXTextField();
							panSecond.add(hins_txtRep, BorderLayout.CENTER);
							hins_txtRep.setHorizontalAlignment(JTextField.CENTER);
							hins_txtRep.setEnabled(false);
						}
						JXPanel panThird = new JXPanel(new BorderLayout(5, 5));
						panDetDiv1.add(panThird, BorderLayout.CENTER);
						{
							hins_lblRemarks = new JLabel("Remarks");
							panThird.add(hins_lblRemarks, BorderLayout.LINE_START);
							hins_lblRemarks.setVerticalAlignment(JTextField.TOP);
							hins_lblRemarks.setPreferredSize(new Dimension(100, 0));
						}
						{
							hins_txtRemarks = new JTextField();
							panThird.add(hins_txtRemarks, BorderLayout.CENTER);
							hins_txtRemarks.setHorizontalAlignment(JTextField.CENTER);
							hins_txtRemarks.setEnabled(false);
						}
					}
					JXPanel panDetDiv2 = new JXPanel(new BorderLayout(5, 5));
					panDet.add(panDetDiv2, BorderLayout.CENTER);
					{
						JXPanel panPostInsp = new JXPanel(new BorderLayout(5, 5));
						panDetDiv2.add(panPostInsp, BorderLayout.CENTER);
						{
							scrollPostInsp = new JScrollPane();
							panPostInsp.add(scrollPostInsp, BorderLayout.CENTER);
							scrollPostInsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							scrollPostInsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							{
								modelPostInsp = new model_hdmf_postInspection();
								tblPostInsp = new _JTableMain(modelPostInsp);

								rowPostInsp = tblPostInsp.getRowHeader();
								scrollPostInsp.setViewportView(tblPostInsp);

								tblPostInsp.getColumnModel().getColumn(5).setPreferredWidth(100);
								tblPostInsp.getColumnModel().getColumn(6).setPreferredWidth(150);
								tblPostInsp.getColumnModel().getColumn(7).setPreferredWidth(200);
								tblPostInsp.getColumnModel().getColumn(8).setPreferredWidth(250);

								tblPostInsp.hideColumns("Name", "Tag", "Phase", "Block", "Lot");
							}
							{
								rowPostInsp = tblPostInsp.getRowHeader();
								rowPreInsp.setModel(new DefaultListModel());
								scrollPostInsp.setRowHeaderView(rowPostInsp);
								scrollPostInsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
						JXPanel panPostBut = new JXPanel(new GridLayout(3, 1, 5, 5));
						panDetDiv2.add(panPostBut, BorderLayout.LINE_END);
						panPostBut.setPreferredSize(new Dimension(150, 0));
						{
							hins_btnAdd = new JButton("Add");
							panPostBut.add(hins_btnAdd, BorderLayout.CENTER);
							hins_btnAdd.setActionCommand("hins_Add");
							hins_btnAdd.addActionListener(this);
							hins_btnAdd.setEnabled(false);
						}
						{
							hins_btnSave = new JButton("Save");
							panPostBut.add(hins_btnSave, BorderLayout.CENTER);
							hins_btnSave.setActionCommand("hins_Save");
							hins_btnSave.addActionListener(this);
							hins_btnSave.setEnabled(false);
						}
						{
							hins_btnCancel = new JButton("Cancel");
							panPostBut.add(hins_btnCancel, BorderLayout.CENTER);
							hins_btnCancel.setActionCommand("hins_Cancel");
							hins_btnCancel.addActionListener(this);
							hins_btnCancel.setEnabled(false);
						}
					}
				}
			}
		}
	}

	private void preButton(Integer intBut) {
		if (intBut == 1) { /* Initial state */
			hins_btnGen.setEnabled(true);
			hins_btnPost.setEnabled(false);
			hins_btnPreview.setEnabled(false);
			hins_btnExport.setEnabled(false);
		} else if (intBut == 2) { /* Generate is pressed */
			hins_btnGen.setEnabled(false);
			hins_btnPost.setEnabled(true);
			hins_btnPreview.setEnabled(false);
			hins_btnExport.setEnabled(false);
		} else { /* Batch is retrieved */
			hins_btnGen.setEnabled(true);
			hins_btnPost.setEnabled(false);
			hins_btnPreview.setEnabled(true);
			hins_btnExport.setEnabled(true);
		}
	}

	private void postButton(Integer intBut) {
		if (intBut == 1) { /* Initial state */
			hins_btnAdd.setEnabled(true);
			hins_btnSave.setEnabled(false);
			hins_btnCancel.setEnabled(false);
		} else { /* Add is pressed */
			hins_btnAdd.setEnabled(false);
			hins_btnSave.setEnabled(true);
			hins_btnCancel.setEnabled(true);
		}
	}

	private static Boolean displayPreIns(DefaultTableModel modelMain, JList rowHeader) {
		Boolean blnRet = false;
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String SQL = "select * from view_hdmf_pre_inspection('') order by name";

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			blnRet = true;
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			blnRet = false;
		}
		;

		return blnRet;
	}

	private void displayPostIns(DefaultTableModel modelMain, JList rowHeader, String strID, String strUnit) {
		String strSQL = "select b.entity_name as name, false, c.phase, c.block, c.lot, \n"
				+ "a.trans_date, d.status_desc as status, a.hdmf_rep, a.remarks \n" + "from rf_hdmf_insp_detail a \n"
				+ "inner join rf_entity b on a.entity_id = b.entity_id \n"
				+ "inner join mf_unit_info c on a.pbl_id = c.pbl_id and c.proj_id = '" + hins_txtProID.getValue()
				+ "' \n" + "inner join mf_house_inspection_status d on a.insp_status_id = d.status_id \n"
				+ "where a.entity_id = '" + strID + "' and a.pbl_id = '" + strUnit + "' and a.status_id = 'A' \n"
				+ "order by a.trans_date";

		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
	}

	private static Boolean displayInsBatch(DefaultTableModel modelMain, JList rowHeader, String strBatch) {
		Boolean blnRet = false;
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String SQL = "select * from view_hdmf_pre_inspection('" + strBatch + "') order by name";

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			blnRet = true;
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			blnRet = false;
		}
		;

		return blnRet;
	}

	private static String GetBatch(String strType) {
		pgSelect dbBatch = new pgSelect();
		String strBat = "";
		String strSQL = "";

		if (strType.equals("Inspection")) {
			System.out.println("");
			System.out.println("Inspection");

			strSQL = "SELECT TRIM(to_char(max(COALESCE(batch_no::INT, 0)) + 1, '000000')) FROM rf_hdmf_insp_header";
			dbBatch.select(strSQL);

			if (dbBatch.isNotNull()) {
				strBat = (String) dbBatch.getResult()[0][0];
			} else {
				strBat = "000001";
			}

			if (strBat == null) {
				strBat = "000001";
			}
		} else if (strType.equals("Validation")) {
			System.out.println("");
			System.out.println("Inspection");

			strSQL = "SELECT CASE WHEN TRIM(to_char(max(COALESCE(trans_no::INT, 0)) + 1, '000000')) \n"
					+ "IS NULL THEN '000001' ELSE TRIM(to_char(max(COALESCE(trans_no::INT, 0)) + 1, '000000')) END \n"
					+ "FROM rf_buyer_status \n" + "WHERE byrstatus_id = '107' AND status_id = 'A'";

			dbBatch.select(strSQL);

			if (dbBatch.isNotNull()) {
				strBat = (String) dbBatch.getResult()[0][0];
			} else {
				strBat = "000001";
			}
		}

		return strBat;
	}

	private static String GetRec(String strType) {
		String strRec = "";
		String strSQL = "";

		if (strType.equals("Inspection")) {
			strSQL = "SELECT CASE WHEN TRIM((max(COALESCE(insp_rec_id::INT, 0)) + 1)::char(6)) is null THEN '1' ELSE trim((max(COALESCE(insp_rec_id::INT, 0)) + 1)::char(6)) END FROM rf_hdmf_insp_header";

			pgSelect dbBatch = new pgSelect();
			dbBatch.select(strSQL);

			if (dbBatch.isNotNull()) {
				strRec = (String) dbBatch.getResult()[0][0];
			} else {
				strRec = "000001";
			}
		} else if (strType.equals("Inspected")) {
			strSQL = "SELECT CASE WHEN TRIM((max(COALESCE(insp_detail_rec_id::INT, 0)) + 1)::char(6)) is null THEN '1' ELSE trim((max(COALESCE(insp_detail_rec_id::INT, 0)) + 1)::char(6)) END FROM rf_hdmf_insp_detail";

			pgSelect dbBatch = new pgSelect();
			dbBatch.select(strSQL);

			if (dbBatch.isNotNull()) {
				strRec = (String) dbBatch.getResult()[0][0];
			} else {
				strRec = "000001";
			}
		}

		return strRec;
	}

	private String SQL_BATCH(String strType) {
		String strSQL = "";
		if (strType == "Inspection") {
			strSQL = "select distinct on (batch_no::int) batch_no, date_filed as Date_Filed, date_created as Date_Created \n"
					+ "from rf_hdmf_insp_header order by batch_no::int";
		} else if (strType == "Validation") {
			strSQL = "SELECT DISTINCT trans_no, tran_date::date as date_created FROM rf_buyer_status WHERE byrstatus_id = '107' AND status_id = 'A' ORDER BY trans_no";
		}

		return strSQL;
	}

	private String CLIENT() {
		return "select d.description as unit, trim(c.entity_name) as name, a.entity_id, a.pbl_id \n"
				+ "from rf_sold_unit a \n" + "inner join mf_buyer_type b on a.buyertype = b.type_id \n"
				+ "inner join rf_entity c on a.entity_id = c.entity_id \n"
				+ "inner join mf_unit_info d on a.projcode = d.proj_id and a.pbl_id = d.pbl_id \n"
				+ "left join co_ntp_accomplishment e on a.pbl_id = e.pbl_id and a.seq_no::int = e.seq_no::int \n"
				+ "left join rf_hdmf_insp_header f on a.entity_id = f.entity_id and a.projcode = f.projcode and a.pbl_id = f.pbl_id and a.seq_no = f.seq_no \n"
				+ "where a.status_id != 'I' and b.type_group_id = '04' and coalesce(f.batch_no, '') <> '' \n"
				+ "order by c.entity_name";
	}

	private void ClearBox() {
		hins_dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		hins_cboStatus.setSelectedIndex(0);
		hins_txtRep.setText("");
		hins_txtRemarks.setText("");
	}

	private void EnableBox(Boolean blnDo) {
		hins_dteDate.setEnabled(blnDo);
		hins_cboStatus.setEnabled(blnDo);
		hins_txtRep.setEnabled(blnDo);
		hins_txtRemarks.setEnabled(blnDo);
	}

	private void FillInsCombo(String strID) {
		pgSelect dbCbo = new pgSelect();
		String SQL = "select trim(status_id) || ' - ' || status_desc \n" + "from mf_house_inspection_status x \n"
				+ "where x.status_id not in (select insp_status_id from rf_hdmf_insp_detail where entity_id = '" + strID
				+ "' and status_id = 'A' and insp_status_id != '02')";
		dbCbo.select(SQL);

		if (dbCbo.isNotNull()) {
			hins_cboStatus.removeAllItems();
			for (int x = 0; x < dbCbo.getRowCount(); x++) {
				hins_cboStatus.addItem((String) dbCbo.getResult()[x][0]);
			}
		}
	}

	/** House Inspection **/

	/** Borrower's Validation **/
	private void DisplayBorVal(DefaultTableModel modelMain, JList rowHeader, String strBatch) {
		String SQL = "select * from view_hdmf_bor_validation('" + strBatch + "');";

		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
	}

	private void EnableCtrl(Integer i) {
		if (i == 1) { /* Initial State */
			bor_btnGen.setEnabled(true);
			bor_btnPost.setEnabled(false);
			bor_btnPreview.setEnabled(false);
			bor_btnExport.setEnabled(false);
			chkBor.setEnabled(true);
			dteBor.setEnabled(false);
		} else if (i == 2) { /* Generate is pressed */
			bor_btnGen.setEnabled(true);

			if (chkBor.isSelected()) {
				bor_btnPost.setEnabled(false);
				dteBor.setEnabled(false);
			} else {
				bor_btnPost.setEnabled(true);
				dteBor.setEnabled(true);
			}

			bor_btnPreview.setEnabled(false);
			bor_btnExport.setEnabled(false);
		} else if (i == 3) { /* Post is pressed */
			bor_btnGen.setEnabled(true);
			bor_btnPost.setEnabled(false);
			bor_btnPreview.setEnabled(true);
			bor_btnExport.setEnabled(true);
			chkBor.setEnabled(true);
			dteBor.setEnabled(true);
		} else if (i == 4) { /* Batch is retrieved */
			bor_btnGen.setEnabled(true);
			bor_btnPost.setEnabled(false);
			bor_btnPreview.setEnabled(true);
			bor_btnExport.setEnabled(true);
			chkBor.setEnabled(false);
			dteBor.setEnabled(false);
		}
	}

	/** Borrower's Validation **/

	private void Setdefaults(_JLookup _txtCoID, JTextField _txtCo, _JLookup _txtProID, JTextField _txtPro) {
		String strCount = "";
		Integer intCount = 0;

		/* Company Default */
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			_txtCoID.setValue("");
			_txtCo.setText("[ Company ]");
		} else {
			_txtCoID.setValue(_RealTimeDebit.GetValue("SELECT co_id FROM mf_company LIMIT 1"));

			try {
				String strCo = _RealTimeDebit
						.GetValue("SELECT company_name FROM mf_company WHERE co_id = '" + _txtCoID.getValue() + "'");
				System.out.println(strCo);
				_txtCo.setText(strCo);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtCoID: " + _txtCoID.getValue());
				System.out.println("txtCoName: " + _RealTimeDebit
						.GetValue("SELECT company_name FROM mf_company WHERE co_id = '" + _txtCoID.getValue() + "'"));
			}
		}

		/* Project Default */
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			_txtProID.setValue("");
			_txtPro.setText("");
		} else {
			_txtProID.setValue(_RealTimeDebit.GetValue("SELECT proj_id FROM mf_project LIMIT 1"));

			try {
				String strPro = _RealTimeDebit
						.GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '" + _txtProID.getValue() + "'");
				System.out.println(strPro);
				_txtPro.setText(strPro);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtProjectID: " + _txtProID.getValue());
				System.out.println(_RealTimeDebit
						.GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '" + _txtProID.getValue() + "'"));
			}
		}
	}
}