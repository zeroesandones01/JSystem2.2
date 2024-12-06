/**
 * 
 */
package Buyers.LoansManagement;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.table.NumberEditorExt;

import Database.pgSelect;
import tablemodel.modelApproveQualifiedMRI;
import tablemodel.modelMRI_ForPaymentInsurance;
import tablemodel.modelQualifiedMRIEnroll;
import tablemodel.modelQualifiedMRIRenew;
import tablemodel.modelQualifiedMRITerminate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */

public class MortgageRedemptionInsurance extends _JInternalFrame implements
_GUI, ActionListener {

	private static final long serialVersionUID = -9152261237615158905L;

	private static String title = "Mortgage Redemption Insurance (MRI)";
	static Dimension SIZE = new Dimension(1000, 500);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTabbedPane tabCenter;
	private JPanel pnlQualifiedMRI;
	private JPanel pnlQualifiedMRINorth;

	private JPanel pnlQualifiedMRINW;

	private JPanel pnlQualifiedMRINE;
	private JPanel pnlQualifiedMRINEUpper;
	private JPanel pnlQualifiedMRINELabel;
	private JLabel lblDateCutOff;
	private JLabel lblInsuranceCo;
	private JLabel lblProject;

	private JPanel pnlQualifiedMRINECenter;

	private JPanel pnlQualifiedDateCutOff;
	private _JDateChooser dateCutOff;
	private JLabel lblQualifiedBatchNo;
	
	private JPanel pnlQualifiedBatchNo;
	private JCheckBox chkQualifiedBatchNo;
	private _JLookup lookupQualifiedBatchNo;
	
	private JPanel pnlQualifiedInsurance;
	private _JLookup lookupQualifiedInsurance;
	private _JXTextField txtQualifiedInsurance;
	private JPanel pnlQualifiedProject;
	private _JLookup lookupQualifiedProject;
	private _JXTextField txtQualifiedProject;

	private JPanel pnlQualifiedNorthButtons;
	private JButton btnQualifiedGenerate;

	private JPanel pnlQualifiedMRICenter;
	private JScrollPane scrollQualifiedMRIEnrollment;
	private _JTableMain tblQualifiedMRIEnrollment;
	private modelQualifiedMRIEnroll modelQMRIEnroll;
	private JList rowHeaderQualifyEnroll;

	private JScrollPane scrollQualifiedMRIRenewal;
	private _JTableMain tblQualifiedMRIRenewal;
	private modelQualifiedMRIRenew modelQMRIRenew;
	private JList rowHeaderQualifyRenew;

	private JScrollPane scrollQualifiedMRITermination;
	private _JTableMain tblQualifiedMRITermination;
	private modelQualifiedMRITerminate modelQMRITerminate;
	private JList rowHeaderQualifyTerminate;
	
	private JComboBox cmbQualifyMRI;

	private JPanel pnlApproveQualifiedMRI;
	private JPanel pnlApproveQualifiedMRINorth;
	private JComboBox cmbApprovedMRI;

	private JPanel pnlApproveQualifiedMRINorthCenter;
	private JPanel pnlApprovedQualifiedMRINCLabel;
	private JLabel lblApproveQualifiedInsuranceCoUpper;
	private JLabel lblApproveQualifiedCompany;
//	private JLabel lblApproveQualifiedRPLFNo;
	private JPanel pnlApproveQualifiedMRINCCenter;
	private JPanel pnlApproveQualifiedCompanyUpper;
	private _JLookup lookupApproveQualifiedCompanyUpper;
	private _JXTextField txtApproveQualifiedCompanyUpper;
	private JPanel pnlApproveQualifiedInsuranceUpper;
	private _JLookup lookupApproveQualifiedInsuranceUpper;
	private _JXTextField txtApproveQualifiedInsuranceUpper;
	//private JPanel pnlApprovedQualifiedBatchNo;
	//private _JLookup lookupApproveQualifiedBatchNo;

	private JPanel pnlApproveQualifiedMRINortEast;
	private JButton btnApproveQualifiedGenerate;

	private JPanel pnlApproveQualifiedMRICenter;
	private JScrollPane scrollApprovedQualified;
	private _JTableMain tblApproveQualifiedMRI;
	private JList rowHeaderApproveQualifiedMRI;
	private modelApproveQualifiedMRI modelApproveQualified;
	
	private JPanel pnlApproveQualifiedMRISouth;

	private JPanel pnlApproveQualifiedMRISouthWest;

	private JPanel pnlApproveQualifiedMRISWLabel;
	private JLabel lblApproveQualifiedInsuranceCoLower;
	private JLabel lblApproveQualifiedInsuranceBroker;
	private JLabel lblApproveQualifiedInvoiceNo;
	private JLabel lblApproveQualifiedPolicyNo;
	private JLabel lblApprovedDateApproved;

	private JPanel pnlApproveQualifiedMRISWCenter;
	private JPanel pnlApproveQualifiedInsuranceCoLower;
	private _JLookup lookupApprovedInsuranceCoLower;
	private _JXTextField txtApprovedInsuranceCoLower;
	private JPanel pnlApprovedInsuranceBrokerLower;
	private _JLookup lookupApprovedInsuranceBrokerLower;
	private _JXTextField txtApprovedInsuranceBrokerLower;
	private JPanel pnlApprovedInvoiceNoLower;
	private _JXTextField txtApprovedInvoiceNoLower;
	private JPanel pnlApprovedPolicyNoLower;
	private _JXTextField txtApprovedPolicyNoLower;
	private _JDateChooser dateApprovedMRI;

	/* COMMENTED BY MONIQUE DTD 2023-02-28
	private JLabel lblNewRPLFNo;
	private _JXTextField txtApproveQualifiedRPLFNo; */
	
	//private JPanel pnlApproveQualifiedRPLF; //XXX UNCOMMENT IF LOOKUP FOR RPLF IN APPROVE QUALIFIED
	//private JCheckBox chkApproveQualifiedRPLFNo;
	//private _JLookup lookupApproveQualifiedRPLFNo; //XXX UNCOMMENT IF LOOKUP FOR RPLF IN APPROVE QUALIFIED
	
	private JPanel pnlForPmtInsurance;
	
	private JPanel pnlAMANorthCenterLabel;
	private JLabel lblAMACompany;
	private JLabel lblAMAInsuranceCompany;
	private JLabel lblAMASearchBy;
	
	private JPanel pnlAMANorthCenterComponent;
	private JPanel pnlAMACompany;
	private _JLookup lookupAMACompany;
	private _JXTextField txtAMACompany;
	
	private JPanel pnlAMAInsuranceCompany;
	private _JLookup lookupAMAInsuranceCompany;
	private _JXTextField txtAMAInsuranceCompany;
	
	private JPanel pnlAMASearchBy;
	private JComboBox cmbAMASearchBy;
	private JPanel pnlAMALookupSearch;
	private _JLookup lookupAMASearch;
	
	private JPanel pnlAMANorthEast;
	private JButton btnApprovedGenerate;
	
	private JPanel pnlForPmtInsuranceCenter;
	private JScrollPane scrollForPaymentInsurance;
	private JList rowHeaderForPmtInsurance;
	private _JTableMain tblForPmtInsurance;
	private modelMRI_ForPaymentInsurance modelMRIPmtInsurance;
	
	private JPanel pnlSouth;
	private JButton btnPost;
	private JButton btnEdit;
	private JButton btnPreview;
	private JButton btnClear;

	private JPanel pnlForPmtInsuranceNorth;

	private _JLookup lookupProject;

	private JTextField txtProject;

	public MortgageRedemptionInsurance() {
		super(title, true, true, true, true);
		initGUI();
	}

	public MortgageRedemptionInsurance(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public MortgageRedemptionInsurance(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
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
			{
				pnlQualifiedMRI = new JPanel(new BorderLayout(5, 5));
				tabCenter.add(pnlQualifiedMRI, "Qualified Accounts");
				{
					pnlQualifiedMRINorth = new JPanel(new BorderLayout(3, 3));
					pnlQualifiedMRI.add(pnlQualifiedMRINorth, BorderLayout.NORTH);
					pnlQualifiedMRINorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlQualifiedMRINW = new JPanel(new BorderLayout(3, 3)); 
						pnlQualifiedMRINorth.add(pnlQualifiedMRINW, BorderLayout.WEST);
						pnlQualifiedMRINW.setBorder(JTBorderFactory.createTitleBorder("Accounts For"));
						{
							cmbQualifyMRI = new JComboBox(new String [] {"Enrollment", "Renewal", "Termination"});
							pnlQualifiedMRINW.add(cmbQualifyMRI);
							cmbQualifyMRI.setSelectedItem(null);
							cmbQualifyMRI.setPreferredSize(new Dimension(150, 0));
							cmbQualifyMRI.addItemListener(new ItemListener() {

								public void itemStateChanged(ItemEvent e) {
									refreshQualifiedMRI();
								}
							});
						}
					}
					{
						pnlQualifiedMRINE = new JPanel(new BorderLayout(5, 5));
						pnlQualifiedMRINorth.add(pnlQualifiedMRINE, BorderLayout.CENTER);
						{
							pnlQualifiedMRINEUpper = new JPanel(new BorderLayout(3, 3));
							pnlQualifiedMRINE.add(pnlQualifiedMRINEUpper, BorderLayout.CENTER);
							{
								pnlQualifiedMRINELabel = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlQualifiedMRINEUpper.add(pnlQualifiedMRINELabel, BorderLayout.WEST);
								{
									lblDateCutOff = new JLabel("Cut-Off Date");
									pnlQualifiedMRINELabel.add(lblDateCutOff);
								}
								{
									lblInsuranceCo = new JLabel("Insurance Co.");
									pnlQualifiedMRINELabel.add(lblInsuranceCo);
								}
								{
									lblProject = new JLabel("Project");
									pnlQualifiedMRINELabel.add(lblProject);
								}
							}
							{
								pnlQualifiedMRINECenter = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlQualifiedMRINEUpper.add(pnlQualifiedMRINECenter);
								{
									pnlQualifiedDateCutOff = new JPanel(new BorderLayout(3, 3));
									pnlQualifiedMRINECenter.add(pnlQualifiedDateCutOff);
									{
										dateCutOff = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlQualifiedDateCutOff.add(dateCutOff, BorderLayout.WEST);
										dateCutOff.setPreferredSize(new Dimension(150, 0));
									}
									{
										lblQualifiedBatchNo = new JLabel("Batch No", JLabel.TRAILING);
										pnlQualifiedDateCutOff.add(lblQualifiedBatchNo);
									}
									{
										pnlQualifiedBatchNo = new JPanel(new BorderLayout(3, 3));
										pnlQualifiedDateCutOff.add(pnlQualifiedBatchNo, BorderLayout.EAST);
										{
											chkQualifiedBatchNo = new JCheckBox();
											pnlQualifiedBatchNo.add(chkQualifiedBatchNo, BorderLayout.WEST);
											chkQualifiedBatchNo.addItemListener(new ItemListener() {
												
												@Override
												public void itemStateChanged(ItemEvent arg0) {
													Boolean isSelected = (arg0.getStateChange() == ItemEvent.SELECTED);
													
													if(isSelected){
														//lookupQualifiedBatchNo.setEnabled(true); Modified by Monique 2022-11-18; To enable on selection of project 
														lookupQualifiedBatchNo.setEnabled(false);
														lookupQualifiedBatchNo.setValue(null);
														
														dateCutOff.setDate(null);
														dateCutOff.setEnabled(false);
														
														lookupQualifiedInsurance.setValue(null);
														txtQualifiedInsurance.setText("");
														lookupQualifiedInsurance.setEnabled(false);
														
														lookupQualifiedProject.setValue(null);
														txtQualifiedProject.setText("");
														lookupQualifiedProject.setEnabled(true);
														
														lblDateCutOff.setText("Cut Off");
														lblInsuranceCo.setText("Insurance Co");
														lblProject.setText("Project Name");
														
														btnState(true, false, false, false, false, true, true);
														
														
													}else{
														
														//lookupQualifiedBatchNo.setEditable(false);
														//lookupQualifiedBatchNo.setEnabled(true); -- COMMENTED BY MONIQUE DTD 11-28-2022
														refreshQualifiedMRI();
														
														btnState(true, false, false, true, false, false, true);
													}
												}
											});
										}
										{
											lookupQualifiedBatchNo = new _JLookup(null, "Batch No", 0);
											pnlQualifiedBatchNo.add(lookupQualifiedBatchNo, BorderLayout.EAST);
											lookupQualifiedBatchNo.setPreferredSize(new Dimension(150, 0));
											lookupQualifiedBatchNo.addLookupListener(new LookupListener() {
												
												@Override
												public void lookupPerformed(LookupEvent event) {
													FncSystem.out("Display sql for lookup of Bathc NO", lookupQualifiedBatchNo.getLookupSQL());
													Object [] data = ((_JLookup) event.getSource()).getDataSet();
													
													if(data != null){
														//btnState(true, false, false, false, true, true);
														String batch_no = (String) data[0];
														String proj_id = lookupQualifiedProject.getValue().toString(); 
														
														btnState(true, false, false, false, false, true, true);
														
														if(cmbQualifyMRI.getSelectedIndex() == 0){
															_MortgageRedemptionInsurance.displayQualifiedEnrollment_BatchNo(lookupQualifiedBatchNo.getValue(), modelQMRIEnroll, rowHeaderQualifyEnroll);
															tblQualifiedMRIEnrollment.packAll();
															scrollQualifiedMRIEnrollment.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRIEnrollment.getRowCount())));
															tblQualifiedMRIEnrollment.setEditable(false);
															
														}
														
														if(cmbQualifyMRI.getSelectedIndex() == 1){
															_MortgageRedemptionInsurance.displayQualifiedRenewal_BatchNo(batch_no, proj_id,  modelQMRIRenew, rowHeaderQualifyRenew);
															tblQualifiedMRIRenewal.packAll();
															scrollQualifiedMRIRenewal.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRIRenewal.getRowCount())));
															tblQualifiedMRIRenewal.setEditable(false);
														}
														
														if(cmbQualifyMRI.getSelectedIndex() == 2){
															_MortgageRedemptionInsurance.displayQualifiedTerminated_BatchNo(batch_no, modelQMRITerminate , rowHeaderQualifyTerminate);
															tblQualifiedMRITermination.packAll();
															scrollQualifiedMRITermination.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRITermination.getRowCount())));
															tblQualifiedMRITermination.setEditable(true);
														}
													}
												}
											});
										}
									}
								}
								{
									pnlQualifiedInsurance = new JPanel(new BorderLayout(5, 5));
									pnlQualifiedMRINECenter.add(pnlQualifiedInsurance);
									{
										lookupQualifiedInsurance = new _JLookup(null, "Insurance CO", 0);
										pnlQualifiedInsurance.add(lookupQualifiedInsurance, BorderLayout.WEST);
										lookupQualifiedInsurance.setPreferredSize(new Dimension(100, 0));
										lookupQualifiedInsurance.setLookupSQL(_MortgageRedemptionInsurance.sqlInsuranceCompany());
										lookupQualifiedInsurance.addLookupListener(new LookupListener() {

											public void lookupPerformed(LookupEvent event) {
												//FncSystem.out("Display sql for lookup of Insurance company", lookupQualifiedInsurance.getLookupSQL());
												Object [] data = ((_JLookup) event.getSource()).getDataSet();

												if(data != null){
													String insurance_co = (String) data[1];
													txtQualifiedInsurance.setText(insurance_co);
												}
											}
										});
									}
									{
										txtQualifiedInsurance = new _JXTextField();
										pnlQualifiedInsurance.add(txtQualifiedInsurance, BorderLayout.CENTER);
									}
								}
								{
									pnlQualifiedProject = new JPanel(new BorderLayout(5, 5));
									pnlQualifiedMRINECenter.add(pnlQualifiedProject, BorderLayout.SOUTH);
									{
										lookupQualifiedProject = new _JLookup(null, "Project", 0);
										pnlQualifiedProject.add(lookupQualifiedProject, BorderLayout.WEST);
										lookupQualifiedProject.setLookupSQL(_MortgageRedemptionInsurance.sqlProject());
										lookupQualifiedProject.setPreferredSize(new Dimension(100, 0));
										lookupQualifiedProject.addLookupListener(new LookupListener() {

											public void lookupPerformed(LookupEvent event) {
												//FncSystem.out("Display sql for lookup of project", lookupQualifiedProject.getLookupSQL());
												Object [] data = ((_JLookup) event.getSource()).getDataSet();

												if(data != null){
													String proj_name = (String) data[1];
													txtQualifiedProject.setText(proj_name);
													
													/*if(cmbQualifyMRI.getSelectedIndex() == 1){
														txtRPLFNo.setText(_MortgageRedemptionInsurance.newQualifiedRPLFNo(proj_id));
													}else{
														txtRPLFNo.setText("");
													}*/
													
													if(cmbQualifyMRI.getSelectedIndex() == 0 && chkQualifiedBatchNo.isSelected() == false) {
														lookupQualifiedBatchNo.setEnabled(false);
														_MortgageRedemptionInsurance.displayQualifyMRIEnrollment(lookupQualifiedInsurance.getValue(),lookupQualifiedProject.getValue(),modelQMRIEnroll, rowHeaderQualifyEnroll);
														//lookupQualifiedBatchNo.setEnabled(false);
														
														tblQualifiedMRIEnrollment.packAll();
														scrollQualifiedMRIEnrollment.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRIEnrollment.getRowCount())));
														tblQualifiedMRIEnrollment.setEditable(true);
													}
													if(cmbQualifyMRI.getSelectedIndex() == 0 && chkQualifiedBatchNo.isSelected() == true) {
														lookupQualifiedBatchNo.setEnabled(true);
														lookupQualifiedBatchNo.setLookupSQL(_MortgageRedemptionInsurance.sqlApproveQualifiedEnrollment());
													}
													if(cmbQualifyMRI.getSelectedIndex() == 1 && chkQualifiedBatchNo.isSelected() == false){
														_MortgageRedemptionInsurance.displayQualifyMRIRenewal(dateCutOff.getDate(), lookupQualifiedProject.getValue(), lookupQualifiedInsurance.getValue(), modelQMRIRenew, rowHeaderQualifyRenew);
														//lookupApproveQualifiedBatchNo.setEnabled(false);
														tblQualifiedMRIRenewal.packAll();
														scrollQualifiedMRIRenewal.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRIRenewal.getRowCount())));
														tblQualifiedMRIRenewal.setEditable(true);
													}
													if(cmbQualifyMRI.getSelectedIndex() == 1 && chkQualifiedBatchNo.isSelected() == true) {
														lookupQualifiedBatchNo.setEnabled(true);
														lookupQualifiedBatchNo.setLookupSQL(_MortgageRedemptionInsurance.sqlRenewalBatchNo());
													}
													if(cmbQualifyMRI.getSelectedIndex() == 2 && chkQualifiedBatchNo.isSelected() == false){
														_MortgageRedemptionInsurance.displayQualifyMRITermination(dateCutOff.getDate(),lookupQualifiedInsurance.getValue(), lookupQualifiedProject.getValue(), modelQMRITerminate, rowHeaderQualifyTerminate);
														//lookupApproveQualifiedBatchNo.setEnabled(false);
														dateCutOff.setEnabled(true);
														tblQualifiedMRITermination.packAll();
														scrollQualifiedMRITermination.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRITermination.getRowCount())));
														tblQualifiedMRITermination.setEditable(true);
														
														FncSystem.out("Display proj_id: ", lookupQualifiedProject.getValue().toString());
													} 
													if(cmbQualifyMRI.getSelectedIndex() == 2 && chkQualifiedBatchNo.isSelected() == true) {
														lookupQualifiedBatchNo.setEnabled(true);
														lookupQualifiedBatchNo.setLookupSQL(_MortgageRedemptionInsurance.sqlTerminationBatchNo());
													} else {
														lookupQualifiedBatchNo.setEnabled(true); 
													}
												}
											}
										});
									}
									{
										txtQualifiedProject = new _JXTextField();
										pnlQualifiedProject.add(txtQualifiedProject, BorderLayout.CENTER);
									}
								}
							}
						}
					}
					/*{
						pnlQualifiedNorthButtons = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlQualifiedMRINorth.add(pnlQualifiedNorthButtons, BorderLayout.EAST);
						pnlQualifiedNorthButtons.setPreferredSize(new Dimension(200, 0));
						{
							btnQualifiedGenerate = new JButton("Generate");
							pnlQualifiedNorthButtons.add(btnQualifiedGenerate);
							btnQualifiedGenerate.setActionCommand("Qualified MRI Generate");
							btnQualifiedGenerate.addActionListener(this);
						}
					}*/
				}
				{
					pnlQualifiedMRICenter = new JPanel(new BorderLayout(3, 3));
					pnlQualifiedMRI.add(pnlQualifiedMRICenter, BorderLayout.CENTER);
					pnlQualifiedMRICenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					Font fontTable = new Font(FncLookAndFeel.font_name, Font.PLAIN, 12);
					
					{
						scrollQualifiedMRIEnrollment = new JScrollPane();
						{
							modelQMRIEnroll = new modelQualifiedMRIEnroll();
							tblQualifiedMRIEnrollment = new _JTableMain(modelQMRIEnroll);
							scrollQualifiedMRIEnrollment.setViewportView(tblQualifiedMRIEnrollment);
							scrollQualifiedMRIEnrollment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblQualifiedMRIEnrollment.hideColumns("Unit ID", "PBL", "Seq.", "Entity ID", "Insurance Co ID", "Insurance Company", "Amt of Loan", "Amt Insured", "Term", "Premium", "Expected Date of Insurance", "Certificate No.", "Invoice No", "Business Class");
							
							tblQualifiedMRIEnrollment.getTableHeader().setFont(fontTable.deriveFont(1));
							tblQualifiedMRIEnrollment.setRowHeight(25);
						}
						{
							rowHeaderQualifyEnroll = tblQualifiedMRIEnrollment.getRowHeader();
							rowHeaderQualifyEnroll.setFixedCellHeight(25);
							rowHeaderQualifyEnroll.setModel(new DefaultListModel());
							scrollQualifiedMRIEnrollment.setRowHeaderView(rowHeaderQualifyEnroll);
							scrollQualifiedMRIEnrollment.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollQualifiedMRIRenewal = new JScrollPane();
						{
							modelQMRIRenew = new modelQualifiedMRIRenew();
							tblQualifiedMRIRenewal = new _JTableMain(modelQMRIRenew);
							scrollQualifiedMRIRenewal.setViewportView(tblQualifiedMRIRenewal);
							scrollQualifiedMRIRenewal.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblQualifiedMRIRenewal.hideColumns("Applicant No.", "Unit ID", "PBL ID", "Seq.", "Entity ID", "Stage" ,"Insurance Line", "Buyer Status ID", "Amt of Loan", 
									"Expected Date of Insurance", "Certificate No", "Invoice No", "Business Class");
							tblQualifiedMRIRenewal.packAll();
							
							tblQualifiedMRIRenewal.getTableHeader().setFont(fontTable.deriveFont(1));
							tblQualifiedMRIRenewal.setRowHeight(25);
							
							tblQualifiedMRIRenewal.addPropertyChangeListener(new PropertyChangeListener() {
								
								@Override
								public void propertyChange(PropertyChangeEvent evt) {
									_JTableMain table = (_JTableMain) evt.getSource();
									String propertyName = evt.getPropertyName();

									if (!propertyName.equals("swingx.rollover")) {
										// System.out.printf("%n%n%s%n",
										// propertyName);
									}
									if (propertyName.equals("tableCellEditor")) {
										int column = table.convertColumnIndexToModel(table.getEditingColumn());

										if (column != -1 && modelQMRIRenew.getColumnClass(column).equals(BigDecimal.class)) {
											Object oldValue = null;
											try {
												oldValue = ((NumberEditorExt) evt.getOldValue()).getCellEditorValue();
											} catch (NullPointerException e) {
											}

											if (oldValue != null) {
												//System.out.printf("%n%n%s: (%s, %s) %s%n", propertyName, oldValue, newValue, oldValue.getClass().getSimpleName());
												if (oldValue instanceof Double) {
													table.setValueAt(new BigDecimal((Double) oldValue), table.getEditingRow(), table.getEditingColumn());
												}
												if (oldValue instanceof Long) {
													table.setValueAt(new BigDecimal((Long) oldValue), table.getEditingRow(), table.getEditingColumn());
												}
											}
										}
									}
									
								}
							});

						}
						{
							rowHeaderQualifyRenew = tblQualifiedMRIRenewal.getRowHeader();
							rowHeaderQualifyRenew.setFixedCellHeight(25);
							rowHeaderQualifyRenew.setModel(new DefaultListModel());
							scrollQualifiedMRIRenewal.setRowHeaderView(rowHeaderQualifyRenew);
							scrollQualifiedMRIRenewal.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollQualifiedMRITermination = new JScrollPane();
						{
							modelQMRITerminate = new modelQualifiedMRITerminate();
							tblQualifiedMRITermination = new _JTableMain(modelQMRITerminate);
							scrollQualifiedMRITermination.setViewportView(tblQualifiedMRITermination);
							scrollQualifiedMRITermination.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblQualifiedMRITermination.hideColumns("Rec. ID","Applicant No", "Unit ID", "PBL ID", "Seq. No", "Entity ID", "Insurance Line", "Buyer Status ID", "Remarks Reason", "Refund", "Effectivity Date", "Invoice No", "Cert. No", "Date From", "Date To");
							
							tblQualifiedMRITermination.getTableHeader().setFont(fontTable.deriveFont(1));
							tblQualifiedMRITermination.setRowHeight(25);
						}
						{
							rowHeaderQualifyTerminate = tblQualifiedMRITermination.getRowHeader();
							rowHeaderQualifyTerminate.setFixedCellHeight(25);
							rowHeaderQualifyTerminate.setModel(new DefaultListModel());
							scrollQualifiedMRITermination.setRowHeaderView(rowHeaderQualifyTerminate);
							scrollQualifiedMRITermination.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
			}
			{
				pnlApproveQualifiedMRI = new JPanel(new BorderLayout(3, 3));
				tabCenter.add(pnlApproveQualifiedMRI, "For Issuance of Policy");
				{
					pnlApproveQualifiedMRINorth = new JPanel(new BorderLayout(3, 3));
					pnlApproveQualifiedMRI.add(pnlApproveQualifiedMRINorth, BorderLayout.NORTH);
					pnlApproveQualifiedMRINorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlApproveQualifiedMRINorth.setPreferredSize(new Dimension(0, 70));
					/*{
						pnlApprovedMRIFor = new JPanel(new BorderLayout(3, 3));
						pnlApprovedMRINorth.add(pnlApprovedMRIFor, BorderLayout.WEST);
						pnlApprovedMRIFor.setBorder(JTBorderFactory.createTitleBorder("Accounts For"));
						{
							cmbApprovedMRI = new JComboBox(new String [] {"Enrollment", "Transfer"});
							pnlApprovedMRIFor.add(cmbApprovedMRI);
							cmbApprovedMRI.setSelectedItem(null);
							cmbApprovedMRI.setPreferredSize(new Dimension(150, 0));
							cmbApprovedMRI.addItemListener(new ItemListener() {

								public void itemStateChanged(ItemEvent e) {
									
									refreshApprovedMRI();
								}
							});
						}
					}*/
					{
						pnlApproveQualifiedMRINorthCenter = new JPanel(new BorderLayout(3, 3));
						pnlApproveQualifiedMRINorth.add(pnlApproveQualifiedMRINorthCenter, BorderLayout.CENTER);
						{
							pnlApprovedQualifiedMRINCLabel = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlApproveQualifiedMRINorthCenter.add(pnlApprovedQualifiedMRINCLabel, BorderLayout.WEST);
							{
								lblApproveQualifiedCompany = new JLabel("Company Name");
								pnlApprovedQualifiedMRINCLabel.add(lblApproveQualifiedCompany);
							}
							{
								lblApproveQualifiedInsuranceCoUpper = new JLabel("Insurance Co");
								pnlApprovedQualifiedMRINCLabel.add(lblApproveQualifiedInsuranceCoUpper);
							}
							/*{
								lblApproveQualifiedRPLFNo = new JLabel("RPLF No");
								pnlApprovedQualifiedMRINCLabel.add(lblApproveQualifiedRPLFNo);
							}*/
						}
						{
							pnlApproveQualifiedMRINCCenter = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlApproveQualifiedMRINorthCenter.add(pnlApproveQualifiedMRINCCenter, BorderLayout.CENTER);
							{
								pnlApproveQualifiedCompanyUpper = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedMRINCCenter.add(pnlApproveQualifiedCompanyUpper);
								{
									lookupApproveQualifiedCompanyUpper = new _JLookup(null, "Select Company", 0);
									pnlApproveQualifiedCompanyUpper.add(lookupApproveQualifiedCompanyUpper, BorderLayout.WEST);
									lookupApproveQualifiedCompanyUpper.setLookupSQL(_MortgageRedemptionInsurance.sqlCompany());
									lookupApproveQualifiedCompanyUpper.setPreferredSize(new Dimension(100, 0));
									lookupApproveQualifiedCompanyUpper.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											//FncSystem.out("Display sql for lookup of Company", lookupApproveQualifiedCompanyUpper.getLookupSQL());
											
											Object [] data = ((_JLookup) event.getSource()).getDataSet();
											
											if(data != null){
												String co_id = (String) data[0];
												String company_name = (String) data[1];
												txtApproveQualifiedCompanyUpper.setText(company_name);
												//txtApproveQualifiedRPLFNo.setText(_MortgageRedemptionInsurance.newApprovedRPLFNo(co_id));
											}
										}
									});
								}
								{
									txtApproveQualifiedCompanyUpper = new _JXTextField();
									pnlApproveQualifiedCompanyUpper.add(txtApproveQualifiedCompanyUpper, BorderLayout.CENTER);
								}
							}
							{
								pnlApproveQualifiedInsuranceUpper = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedMRINCCenter.add(pnlApproveQualifiedInsuranceUpper);
								{
									lookupApproveQualifiedInsuranceUpper = new _JLookup(null, "Insurance Company", 0);
									pnlApproveQualifiedInsuranceUpper.add(lookupApproveQualifiedInsuranceUpper, BorderLayout.WEST);
									lookupApproveQualifiedInsuranceUpper.setPreferredSize(new Dimension(100, 0));
									lookupApproveQualifiedInsuranceUpper.setLookupSQL(_MortgageRedemptionInsurance.sqlInsuranceCompany());
									lookupApproveQualifiedInsuranceUpper.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											//FncSystem.out("Display sql for lookup of Insurance Upper", lookupApproveQualifiedInsuranceUpper.getLookupSQL());
											
											Object [] data = ((_JLookup) event.getSource()).getDataSet();
											
											if(data != null){
												String insurance_co_id = (String) data[0];
												String insurance_company = (String) data[1];
												txtApproveQualifiedInsuranceUpper.setText(insurance_company);
												
												_MortgageRedemptionInsurance.displayApproveQualifiedMRI(lookupApproveQualifiedInsuranceUpper.getValue(), modelApproveQualified, rowHeaderApproveQualifiedMRI);
												tblApproveQualifiedMRI.packAll();
												scrollApprovedQualified.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblApproveQualifiedMRI.getRowCount())));
											}
										}
									});
								}
								{
									txtApproveQualifiedInsuranceUpper = new _JXTextField();
									pnlApproveQualifiedInsuranceUpper.add(txtApproveQualifiedInsuranceUpper, BorderLayout.CENTER);
								}
							}
						}
					}
					/*{
						pnlApproveQualifiedMRINortEast = new JPanel(new BorderLayout(3, 3));
						pnlApproveQualifiedMRINorth.add(pnlApproveQualifiedMRINortEast, BorderLayout.EAST);
						pnlApproveQualifiedMRINortEast.setPreferredSize(new Dimension(200, 0));
						{
							btnApproveQualifiedGenerate = new JButton("Generate");
							pnlApproveQualifiedMRINortEast.add(btnApproveQualifiedGenerate, BorderLayout.CENTER);
							btnApproveQualifiedGenerate.setActionCommand("Approve Qualified Generate");
							btnApproveQualifiedGenerate.addActionListener(this);
						}
					}*/
				}
				{
					pnlApproveQualifiedMRICenter = new JPanel(new BorderLayout(3, 3));
					pnlApproveQualifiedMRI.add(pnlApproveQualifiedMRICenter, BorderLayout.CENTER);
					//pnlApprovedMRICenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlApproveQualifiedMRICenter.setBorder(lineBorder);
					{
						scrollApprovedQualified = new JScrollPane();
						pnlApproveQualifiedMRICenter.add(scrollApprovedQualified, BorderLayout.CENTER);
						
						Font fontTable = new Font(FncLookAndFeel.font_name, Font.PLAIN, 12);
						{
							modelApproveQualified = new modelApproveQualifiedMRI();
							tblApproveQualifiedMRI = new _JTableMain(modelApproveQualified);
							scrollApprovedQualified.setViewportView(tblApproveQualifiedMRI);
							scrollApprovedQualified.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblApproveQualifiedMRI.hideColumns("Applicant No.", "Proj. ID", "Unit ID", "PBL ID", "Seq.", "Entity ID", "Insurance Co ID", "Reference No");
							tblApproveQualifiedMRI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblApproveQualifiedMRI.setSortable(false);
							
							tblApproveQualifiedMRI.getTableHeader().setFont(fontTable.deriveFont(1));
							tblApproveQualifiedMRI.setRowHeight(25);
							
							tblApproveQualifiedMRI.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								
								@Override
								public void valueChanged(ListSelectionEvent arg0) {
									if(!arg0.getValueIsAdjusting()){
										if(tblApproveQualifiedMRI.getSelectedRows().length == 1){
											//int selected_row = tblApproveQualifiedMRI.getSelectedRow();
											int selected_row = tblApproveQualifiedMRI.convertRowIndexToModel(tblApproveQualifiedMRI.getSelectedRow());
											
											/*String insurance_co_id = (String) modelApproveQualified.getValueAt(selected_row, 9);
											String insurance_comp = (String) modelApproveQualified.getValueAt(selected_row, 10);*/
											String invoice_no = (String) modelApproveQualified.getValueAt(selected_row, 20);
											String policy_no = (String) modelApproveQualified.getValueAt(selected_row, 21);
											Date date_approved = (Date) modelApproveQualified.getValueAt(selected_row, 22);
											
											/*lookupApprovedInsuranceCoLower.setValue(insurance_co_id);
											txtApprovedInsuranceCoLower.setText(insurance_comp);
											lookupApprovedInsuranceBrokerLower.setValue(insurance_co_id);
											txtApprovedInsuranceBrokerLower.setText(insurance_comp);*/ //XXX UNCOMMENT IF NEED TO SET VALUE OF INSURANCE COMP
											
											txtApprovedPolicyNoLower.setText(policy_no);
											txtApprovedInvoiceNoLower.setText(invoice_no);
											dateApprovedMRI.setDate(date_approved);
											
											//btnState(false, true, false, false, true, false, true);
										}
									}
								}
							});
						}
						{
							rowHeaderApproveQualifiedMRI = tblApproveQualifiedMRI.getRowHeader();
							rowHeaderApproveQualifiedMRI.setFixedCellHeight(25);
							rowHeaderApproveQualifiedMRI.setModel(new DefaultListModel());
							scrollApprovedQualified.setRowHeaderView(rowHeaderApproveQualifiedMRI);
							scrollApprovedQualified.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
				{
					pnlApproveQualifiedMRISouth = new JPanel(new BorderLayout(3, 3));
					pnlApproveQualifiedMRI.add(pnlApproveQualifiedMRISouth, BorderLayout.SOUTH);
					pnlApproveQualifiedMRISouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlApproveQualifiedMRISouthWest = new JPanel(new BorderLayout(3, 3));
						pnlApproveQualifiedMRISouth.add(pnlApproveQualifiedMRISouthWest, BorderLayout.CENTER);
						{
							pnlApproveQualifiedMRISWLabel = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlApproveQualifiedMRISouthWest.add(pnlApproveQualifiedMRISWLabel, BorderLayout.WEST);
							{
								lblApproveQualifiedInsuranceCoLower = new JLabel("Insurance Co.");
								pnlApproveQualifiedMRISWLabel.add(lblApproveQualifiedInsuranceCoLower);
							}
							{
								lblApproveQualifiedInsuranceBroker = new JLabel("Insurance Broker");
								pnlApproveQualifiedMRISWLabel.add(lblApproveQualifiedInsuranceBroker);
							}
							{
								lblApproveQualifiedPolicyNo = new JLabel("Policy No.");
								pnlApproveQualifiedMRISWLabel.add(lblApproveQualifiedPolicyNo);
							}
							{
								lblApproveQualifiedInvoiceNo = new JLabel("Invoice No.");
								pnlApproveQualifiedMRISWLabel.add(lblApproveQualifiedInvoiceNo);
							}
						}
						{
							pnlApproveQualifiedMRISWCenter = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlApproveQualifiedMRISouthWest.add(pnlApproveQualifiedMRISWCenter, BorderLayout.CENTER);
							{
								pnlApproveQualifiedInsuranceCoLower = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedMRISWCenter.add(pnlApproveQualifiedInsuranceCoLower);
								{
									lookupApprovedInsuranceCoLower = new _JLookup(null, "Insurance Company", 0);
									pnlApproveQualifiedInsuranceCoLower.add(lookupApprovedInsuranceCoLower, BorderLayout.WEST);
									lookupApprovedInsuranceCoLower.setPreferredSize(new Dimension(100, 0));
									lookupApprovedInsuranceCoLower.setLookupSQL(_MortgageRedemptionInsurance.sqlInsuranceCompany());
									lookupApprovedInsuranceCoLower.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											FncSystem.out("Display sql for lookup of Insurance Comp", lookupApprovedInsuranceCoLower.getLookupSQL());
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String insurance_co_name = (String) data[1];
												txtApprovedInsuranceCoLower.setText(insurance_co_name);
											}
										}
									});
								}
								{
									txtApprovedInsuranceCoLower = new _JXTextField();
									pnlApproveQualifiedInsuranceCoLower.add(txtApprovedInsuranceCoLower, BorderLayout.CENTER);
								}
							}
							{
								pnlApprovedInsuranceBrokerLower = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedMRISWCenter.add(pnlApprovedInsuranceBrokerLower);
								{
									lookupApprovedInsuranceBrokerLower = new _JLookup(null, "Insurance Broker", 0);
									pnlApprovedInsuranceBrokerLower.add(lookupApprovedInsuranceBrokerLower, BorderLayout.WEST);
									lookupApprovedInsuranceBrokerLower.setPreferredSize(new Dimension(100, 0));
									lookupApprovedInsuranceBrokerLower.setLookupSQL(_MortgageRedemptionInsurance.sqlInsuranceCompany());
									lookupApprovedInsuranceBrokerLower.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											FncSystem.out("Display sql for lookup of Approved Insurance Broker", lookupApprovedInsuranceBrokerLower.getLookupSQL());
											
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String insurance_broker = (String) data[1];
												txtApprovedInsuranceBrokerLower.setText(insurance_broker);
											}
										}
									});
								}
								{
									txtApprovedInsuranceBrokerLower = new _JXTextField();
									pnlApprovedInsuranceBrokerLower.add(txtApprovedInsuranceBrokerLower, BorderLayout.CENTER);
								}
							}
							{
								pnlApprovedPolicyNoLower = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedMRISWCenter.add(pnlApprovedPolicyNoLower);
								{
									txtApprovedPolicyNoLower = new _JXTextField();
									pnlApprovedPolicyNoLower.add(txtApprovedPolicyNoLower, BorderLayout.WEST);
									txtApprovedPolicyNoLower.setHorizontalAlignment(_JXTextField.CENTER);
									txtApprovedPolicyNoLower.setPreferredSize(new Dimension(200, 0));
									txtApprovedPolicyNoLower.addKeyListener(new KeyAdapter() {
										
										public void keyReleased(KeyEvent e){
											String policy_no = ((_JXTextField) e.getSource()).getText();
											
											if(tblApproveQualifiedMRI.getSelectedRows().length == 1){
												int selected_row = tblApproveQualifiedMRI.convertRowIndexToModel(tblApproveQualifiedMRI.getSelectedRow());
												
												//modelApproveQualified.setValueAt(policy_no, selected_row, 18);
												
												modelApproveQualified.setValueAt(policy_no, selected_row, 21);
											}
										}
									});
								}
								{
									lblApprovedDateApproved = new JLabel("Date Approved", JLabel.TRAILING);
									pnlApprovedPolicyNoLower.add(lblApprovedDateApproved);
								}
								{
									dateApprovedMRI = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlApprovedPolicyNoLower.add(dateApprovedMRI, BorderLayout.EAST);
									dateApprovedMRI.setPreferredSize(new Dimension(150, 0));
									dateApprovedMRI.addDateListener(new DateListener() {
										
										@Override
										public void datePerformed(DateEvent event) {
											Date date_approved = ((_JDateChooser) event.getSource()).getDate();
											
											if(tblApproveQualifiedMRI.getSelectedRows().length == 1){
												int selected_row = tblApproveQualifiedMRI.convertRowIndexToModel(tblApproveQualifiedMRI.getSelectedRow());
												
												//modelApproveQualified.setValueAt(date_approved, selected_row, 19);
												modelApproveQualified.setValueAt(date_approved, selected_row, 22);
											}
										}
									});
								}
							}
							{
								pnlApprovedInvoiceNoLower = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedMRISWCenter.add(pnlApprovedInvoiceNoLower);
								{
									txtApprovedInvoiceNoLower = new _JXTextField();
									pnlApprovedInvoiceNoLower.add(txtApprovedInvoiceNoLower, BorderLayout.WEST);
									txtApprovedInvoiceNoLower.setHorizontalAlignment(_JXTextField.CENTER);
									txtApprovedInvoiceNoLower.setPreferredSize(new Dimension(200, 0));
									txtApprovedInvoiceNoLower.addKeyListener(new KeyAdapter() {
										
										public void keyReleased(KeyEvent e){
											String invoice_no = ((_JXTextField) e.getSource()).getText();
											
											if(tblApproveQualifiedMRI.getSelectedRows().length == 1){
												int selected_row = tblApproveQualifiedMRI.convertRowIndexToModel(tblApproveQualifiedMRI.getSelectedRow());
												
												//modelApproveQualified.setValueAt(invoice_no, selected_row, 17);
												modelApproveQualified.setValueAt(invoice_no, selected_row, 20);
											}
										}
									});
								}
								/* COMMENTED BY MONIQUE DTD 2023-02-28; CREATION OF RPLF APPLIED ON FUNCTION 
								{
									lblNewRPLFNo = new JLabel("New RPLF No", JLabel.TRAILING);
									pnlApprovedInvoiceNoLower.add(lblNewRPLFNo);
								}
								{
									txtApproveQualifiedRPLFNo = new _JXTextField();
									pnlApprovedInvoiceNoLower.add(txtApproveQualifiedRPLFNo, BorderLayout.EAST);
									txtApproveQualifiedRPLFNo.setHorizontalAlignment(_JXTextField.CENTER);
									txtApproveQualifiedRPLFNo.setPreferredSize(new Dimension(100, 0));
								}*/
							}
						}
					}
				}
			}
			{
				pnlForPmtInsurance = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("For Payment to Insurance Company", pnlForPmtInsurance);
				pnlForPmtInsurance.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				/*{
					pnlApproveQualifiedMRINorth = new JPanel(new BorderLayout(3, 3));
					pnlForPmtInsurance.add(pnlApproveQualifiedMRINorth, BorderLayout.NORTH);
					{
						pnlApproveQualifiedMRINorthCenter = new JPanel(new BorderLayout(3, 3));
						pnlApproveQualifiedMRINorth.add(pnlApproveQualifiedMRINorthCenter, BorderLayout.CENTER);
						{
							pnlAMANorthCenterLabel = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlApproveQualifiedMRINorthCenter.add(pnlAMANorthCenterLabel, BorderLayout.WEST);
							{
								lblAMACompany = new JLabel("Company");
								pnlAMANorthCenterLabel.add(lblAMACompany);
							}
							{
								lblAMAInsuranceCompany = new JLabel("Insurance Company");
								pnlAMANorthCenterLabel.add(lblAMAInsuranceCompany);
							}
							{
								lblAMASearchBy = new JLabel("Search By");
								pnlAMANorthCenterLabel.add(lblAMASearchBy);
							}
						}
						{
							pnlAMANorthCenterComponent = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlApproveQualifiedMRINorthCenter.add(pnlAMANorthCenterComponent, BorderLayout.CENTER);
							{
								pnlAMACompany = new JPanel(new BorderLayout(3, 3));
								pnlAMANorthCenterComponent.add(pnlAMACompany);
								{
									lookupAMACompany = new _JLookup(null, "Select Company", 0);
									pnlAMACompany.add(lookupAMACompany, BorderLayout.WEST);
									lookupAMACompany.setPreferredSize(new Dimension(100, 0));
									lookupAMACompany.setLookupSQL(_MortgageRedemptionInsurance.sqlCompany());
									lookupAMACompany.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											//FncSystem.out("Display sql for lookup of company", lookupAMACompany.getValue());
											
											Object [] data = ((_JLookup) event.getSource()).getDataSet();
											
											if(data != null){
												
												String company_name = (String) data[1];
												txtAMACompany.setText(company_name);
											}
										}
									});
								}
								{
									txtAMACompany = new _JXTextField();
									pnlAMACompany.add(txtAMACompany, BorderLayout.CENTER);
								}
							}
							{
								pnlAMAInsuranceCompany = new JPanel(new BorderLayout(3, 3));
								pnlAMANorthCenterComponent.add(pnlAMAInsuranceCompany);
								{
									lookupAMAInsuranceCompany = new _JLookup(null, "Insurance Company", 0);
									pnlAMAInsuranceCompany.add(lookupAMAInsuranceCompany, BorderLayout.WEST);
									lookupAMAInsuranceCompany.setPreferredSize(new Dimension(100, 0));
									lookupAMAInsuranceCompany.setLookupSQL(_MortgageRedemptionInsurance.sqlInsuranceCompany());
									lookupAMAInsuranceCompany.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											//FncSystem.out("Display sql for lookup of Insurance Compant", lookupAMAInsuranceCompany.getLookupSQL());
											
											Object [] data = ((_JLookup) event.getSource()).getDataSet();
											
											if(data != null){
												String insurance_company= (String) data[1];
												txtAMAInsuranceCompany.setText(insurance_company);
											}
										}
									});
								}
								{
									txtAMAInsuranceCompany = new _JXTextField();
									pnlAMAInsuranceCompany.add(txtAMAInsuranceCompany, BorderLayout.CENTER);
								}
							}
							{
								pnlAMASearchBy = new JPanel(new BorderLayout(3, 3));
								pnlAMANorthCenterComponent.add(pnlAMASearchBy);
								{
									cmbAMASearchBy = new JComboBox(new String[]{"Invoice No", "RPLF No"});
									pnlAMASearchBy.add(cmbAMASearchBy, BorderLayout.WEST);
									cmbAMASearchBy.setPreferredSize(new Dimension(100, 0));
									cmbAMASearchBy.addItemListener(new ItemListener() {
										
										@Override
										public void itemStateChanged(ItemEvent arg0) {
											int selected_item = ((JComboBox) arg0.getSource()).getSelectedIndex();
											System.out.println("Dumaan dito sa lookup");
											
											if(selected_item == 0){
												lookupAMASearch.setValue(null);
												lookupAMASearch.setLookupSQL(_MortgageRedemptionInsurance.sqlInvoiceNo());
												FncSystem.out("Display sql for Lookup of Search", lookupAMASearch.getLookupSQL());
											}
											if(selected_item == 1){
												lookupAMASearch.setValue(null);
												lookupAMASearch.setLookupSQL(_MortgageRedemptionInsurance.sqlRPLFNo());
												FncSystem.out("Display sql for lookup of Search", lookupAMASearch.getLookupSQL());
											}
											
										}
									});
								}
								{
									pnlAMALookupSearch = new JPanel(new BorderLayout(3, 3));
									pnlAMASearchBy.add(pnlAMALookupSearch, BorderLayout.CENTER);
									{
										lookupAMASearch = new _JLookup(null, "Search", 0);
										pnlAMALookupSearch.add(lookupAMASearch, BorderLayout.WEST);
										lookupAMASearch.setPreferredSize(new Dimension(100, 0));
										lookupAMASearch.addLookupListener(new LookupListener() {
											
											@Override
											public void lookupPerformed(LookupEvent event) {
												
												Object data [] = ((_JLookup) event.getSource()).getDataSet();
												
												if(data != null){
													System.out.println("Dumaan dito sa sql ng Search Button");
												}
												
												FncSystem.out("Display sql for lookup Search", lookupAMASearch.getLookupSQL());
											}
										});
									}
								}
							}
						}
					}
					{
						pnlAMANorthEast = new JPanel(new BorderLayout(3, 3));
						pnlApproveQualifiedMRINorth.add(pnlAMANorthEast, BorderLayout.EAST);
						pnlAMANorthEast.setPreferredSize(new Dimension(200, 0));
						{
							btnApprovedGenerate = new JButton("Generate");
							pnlAMANorthEast.add(btnApprovedGenerate);
							btnApprovedGenerate.setActionCommand("Approved MRI Generate");
							btnApprovedGenerate.addActionListener(this);
						}
					}
				}*/
				{
					pnlForPmtInsuranceNorth = new JPanel(new BorderLayout(5, 5));
					pnlForPmtInsurance.add(pnlForPmtInsuranceNorth, BorderLayout.NORTH);
					pnlForPmtInsuranceNorth.setPreferredSize(new Dimension(0, 30)); 
					
					{
						JPanel pnlProjDetails = new JPanel(new GridLayout(1, 3, 5, 5)); 
						pnlForPmtInsuranceNorth.add(pnlProjDetails, BorderLayout.WEST); 
						pnlProjDetails.setPreferredSize(new Dimension(650, 0)); 
						
						JLabel lblProj_id = new JLabel("          Project: "); 
						pnlProjDetails.add(lblProj_id); 
						
						lookupProject =  new _JLookup(null, "Project", 0);
						pnlProjDetails.add(lookupProject);
						lookupProject.setLookupSQL(_MortgageRedemptionInsurance.sqlProject());
						lookupProject.setPreferredSize(new Dimension(100, 0));
						lookupProject.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();

								if(data != null){
									String proj_name = (String) data[1];
									txtProject.setText(proj_name);
								}		
								
							}
						});

						 txtProject = new JTextField(); 
						 pnlProjDetails.add(txtProject); 
						 txtProject.setHorizontalAlignment(JTextField.CENTER);
						
					}
				}
				
				
				
				{
					pnlForPmtInsuranceCenter = new JPanel(new BorderLayout(3, 3));
					pnlForPmtInsurance.add(pnlForPmtInsuranceCenter, BorderLayout.CENTER);
					{
						scrollForPaymentInsurance = new JScrollPane();
						pnlForPmtInsuranceCenter.add(scrollForPaymentInsurance, BorderLayout.CENTER);
						Font fontTable = new Font(FncLookAndFeel.font_name, Font.PLAIN, 12);
						
						{
							modelMRIPmtInsurance = new modelMRI_ForPaymentInsurance();
							tblForPmtInsurance = new _JTableMain(modelMRIPmtInsurance);
							scrollForPaymentInsurance.setViewportView(tblForPmtInsurance);
							scrollForPaymentInsurance.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblForPmtInsurance.setSortable(false);
							tblForPmtInsurance.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							
							tblForPmtInsurance.getTableHeader().setFont(fontTable.deriveFont(1));
							tblForPmtInsurance.setRowHeight(25);
							
							tblForPmtInsurance.addMouseListener(new MouseAdapter() {
								
								public void mouseReleased(MouseEvent e){
									if(e.isPopupTrigger()){
										try {
											initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
										} catch (NullPointerException e1) { }
									}
								}

								public void mousePressed(MouseEvent e){
									if(e.isPopupTrigger()){
										try {
											initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
										} catch (NullPointerException e1) { }
									}
								}
								
								public JPopupMenu initializeMenu(MouseEvent e){
									_JTableMain table = (_JTableMain) e.getSource();
									int row = table.getModelRow(table.getSelectedRow());
									int column = table.convertColumnIndexToModel(1);
									int column2 = table.convertColumnIndexToModel(3);
									int column3 = table.convertColumnIndexToModel(2);
									
									final String invoice_no = (String) modelMRIPmtInsurance.getValueAt(row, column);
									final String reference_no = (String) modelMRIPmtInsurance.getValueAt(row, column2);
									final String rplf_no = (String) modelMRIPmtInsurance.getValueAt(row, column3);
									
									System.out.printf("invoice_no: %s%n", invoice_no);
									System.out.printf("reference_no: %s%n", reference_no);
									System.out.printf("proj_id: %s%n", lookupProject.getValue().toString());
									
									JPopupMenu menu = new JPopupMenu();
									
									JMenuItem menuViewInvoice = new JMenuItem("View Invoice No");
									menu.add(menuViewInvoice);
									menuViewInvoice.setFont(menuViewInvoice.getFont().deriveFont(10f));
									menuViewInvoice.addActionListener(new ActionListener() {
										
										public void actionPerformed(ActionEvent e) {
											Map<String, Object> mapParameters = new HashMap<String, Object>();
											
											mapParameters.put("invoice_no", invoice_no);
											mapParameters.put("reference_no", reference_no);
											mapParameters.put("rplf_no", rplf_no); //ADDED BY LESTER FOR SAME REFERENCE NO
											mapParameters.put("proj_id", lookupProject.getValue().toString());
											
											FncReport.generateReport("/Reports/rptMRI_ForPmtInsurance.jasper", "Accounts For Payment to Insurance Company", mapParameters);
										}
									});
									
									return menu;
								}
							});
						}
						{
							rowHeaderForPmtInsurance = tblForPmtInsurance.getRowHeader();
							rowHeaderForPmtInsurance.setFixedCellHeight(25);
							rowHeaderForPmtInsurance.setModel(new DefaultListModel());
							scrollForPaymentInsurance.setRowHeaderView(rowHeaderForPmtInsurance);
							scrollForPaymentInsurance.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
			}
			tabCenter.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					int selected_tab = ((_JTabbedPane) arg0.getSource()).getSelectedIndex();
					
					if(selected_tab == 0){
						clearQualifiedMRI();
					}
					if(selected_tab == 1){
						clearApproveQualifiedMRI();
					}
					if(selected_tab == 2){
						//clearApprovedMRI();
						_MortgageRedemptionInsurance.displayForPaymentInsuranceCompany(modelMRIPmtInsurance, rowHeaderForPmtInsurance);
						scrollForPaymentInsurance.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblForPmtInsurance.getRowCount())));
						tblForPmtInsurance.packAll();
					}
				}
			});
		}
		{
			pnlSouth = new JPanel(new GridLayout(1, 5, 3, 3));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setPreferredSize(new Dimension(0, 35));
			{
				pnlSouth.add(Box.createHorizontalBox());
				pnlSouth.add(Box.createHorizontalBox());
				//pnlSouth.add(Box.createHorizontalBox());
			}
			{
				btnPost = new JButton("Post");
				pnlSouth.add(btnPost);
				btnPost.setActionCommand("Post");
				btnPost.addActionListener(this);
				btnPost.setMnemonic(KeyEvent.VK_P);
			}
			/*{
				btnEdit = new JButton("Edit");
				pnlSouth.add(btnEdit);
				btnEdit.setActionCommand("Edit");
				btnEdit.addActionListener(this);
				btnEdit.setMnemonic(KeyEvent.VK_E);
			}*/
			{
				btnPreview = new JButton("Preview");
				pnlSouth.add(btnPreview);
				btnPreview.setActionCommand("Preview");
				btnPreview.addActionListener(this);
				btnPreview.setMnemonic(KeyEvent.VK_R);
			}
			{
				btnClear = new JButton("Clear");
				pnlSouth.add(btnClear);
				btnClear.setActionCommand("Clear");
				btnClear.addActionListener(this);
				btnClear.setMnemonic(KeyEvent.VK_R);
			}
		}
		clearQualifiedMRI();
		clearApproveQualifiedMRI();
		//clearApprovedMRI();
	}//XXX END OF INIT GUI
	
	/*private void btnState(Boolean sQualifyGenerate, Boolean sApproveQualified, Boolean sApprovedMRI, Boolean sPost, Boolean sPreview, Boolean sClear){
		
		btnQualifiedGenerate.setEnabled(sQualifyGenerate);
		btnApproveQualifiedGenerate.setEnabled(sApprovedMRI);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
		btnClear.setEnabled(sClear);
		
	}*/
	
	private void btnState(Boolean sQualifyGenerate, Boolean sApprovedQualified, Boolean sApprovedMRIGenerate ,Boolean sPost, Boolean sEdit, Boolean sPreview, Boolean sClear){
		//btnQualifiedGenerate.setEnabled(sQualifyGenerate);
		//btnApproveQualifiedGenerate.setEnabled(sApprovedQualified);
		//btnApprovedGenerate.setEnabled(sApprovedMRIGenerate);
		btnPost.setEnabled(sPost);
		//btnEdit.setEnabled(sEdit);
		btnPreview.setEnabled(sPreview);
		btnClear.setEnabled(sClear);
		
	}
	
	private void pnlState(Boolean sQualifiedMRI, Boolean sApproveQualifiedMRI, Boolean sApprovedMRI){
		tabCenter.setEnabledAt(0, sQualifiedMRI);
		tabCenter.setEnabledAt(1, sApproveQualifiedMRI);
		tabCenter.setEnabledAt(2, sApprovedMRI);
	}

	//CLEAR COMPONENTS IN QUALIFIED MRI
	private void clearQualifiedMRI(){
		lblDateCutOff.setText("Cut Off");
		lblInsuranceCo.setText("Insurance Co");
		lblProject.setText("Project");
		
		dateCutOff.setDate(null);
		dateCutOff.getCalendarButton().setEnabled(false);
		dateCutOff.setEnabled(false);
		
		chkQualifiedBatchNo.setEnabled(false);
		chkQualifiedBatchNo.setSelected(false);
		lookupQualifiedBatchNo.setValue(null);
		lookupQualifiedBatchNo.setEnabled(false);
		
		lookupQualifiedInsurance.setValue(null);
		txtQualifiedInsurance.setText("");
		lookupQualifiedInsurance.setEnabled(false);
		lookupQualifiedProject.setValue(null);
		txtQualifiedProject.setText("");
		lookupQualifiedProject.setEnabled(false);
		
		pnlQualifiedMRICenter.removeAll();
		pnlQualifiedMRICenter.repaint();

		modelQMRIEnroll.clear();
		rowHeaderQualifyEnroll.setModel(new DefaultListModel());
		scrollQualifiedMRIEnrollment.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));

		modelQMRIRenew.clear();
		rowHeaderQualifyRenew.setModel(new DefaultListModel());
		scrollQualifiedMRIRenewal.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));

		modelQMRITerminate.clear();
		rowHeaderQualifyTerminate.setModel(new DefaultListModel());
		scrollQualifiedMRITermination.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));

		//this.setComponentsEditable(pnlQualifiedMRI, false);
		
		btnState(false, false, false, false, false, false, false);
		pnlState(true, true, true);
	}

	//CLEAR COMPONENTS IN APPROVE QUALIFIED MRI
	private void clearApproveQualifiedMRI(){
		
		lblApproveQualifiedCompany.setText("*Company Name");
		lblApproveQualifiedInsuranceCoUpper.setText("*Insurance Co.");
		//lblApproveQualifiedBatchNo.setText("*Batch No");
		
		lblApproveQualifiedInsuranceCoLower.setText("*Insurance Co.");
		lblApproveQualifiedInsuranceBroker.setText("*Insurance Broker");
		lblApproveQualifiedInvoiceNo.setText("*Invoice No.");
		lblApproveQualifiedPolicyNo.setText("*Policy No.");
		lblApprovedDateApproved.setText("*Date Approved");
		
		lookupApproveQualifiedCompanyUpper.setValue(null);
		//lookupApprovedCompanyUpper.setEditable(false);
		txtApproveQualifiedCompanyUpper.setText("");
		
		lookupApproveQualifiedInsuranceUpper.setValue(null);
		//lookupApprovedInsuranceUpper.setEditable(false);
		txtApproveQualifiedInsuranceUpper.setText("");
		
		//lookupApproveQualifiedBatchNo.setValue(null);
		//lookupApprovedBatchNo.setEditable(false);
		
		lookupApprovedInsuranceCoLower.setValue(null);
		txtApprovedInsuranceCoLower.setText("");
		//lookupApprovedInsuranceCoLower.setEnabled(false);
		
		lookupApprovedInsuranceBrokerLower.setValue(null);
		txtApprovedInsuranceBrokerLower.setText("");
		//lookupApprovedInsuranceBrokerLower.setEnabled(false);
		
		txtApprovedPolicyNoLower.setText("");
		txtApprovedPolicyNoLower.setEditable(true);
		txtApprovedInvoiceNoLower.setText("");
		txtApprovedInvoiceNoLower.setEditable(true);
		
		dateApprovedMRI.setDate(null);
		//dateApprovedMRI.setEnabled(false);
		
		//txtApproveQualifiedRPLFNo.setText("");
		
		modelApproveQualified.clear();
		rowHeaderApproveQualifiedMRI.setModel(new DefaultListModel());
		scrollApprovedQualified.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		
		//btnState(false, false, true, true, false, true);
		btnState(false, true, false, true, false, true, true);
		
		pnlState(true, true, true);
	}
	
	//CELAR COMPONENTS IN APPROVED MRI
	private void clearApprovedMRI(){
		cmbAMASearchBy.setSelectedItem(null);
		
		btnState(false, false, true, false, false, true, true);
	}

	private void refreshQualifiedMRI(){
		clearQualifiedMRI();
		
		if(cmbQualifyMRI.getSelectedIndex() == 0 ){//ENROLLMENT
			pnlQualifiedMRICenter.add(scrollQualifiedMRIEnrollment, BorderLayout.CENTER);
			
			lblProject.setText("*Project");
			
			lookupQualifiedProject.setEnabled(true);
			chkQualifiedBatchNo.setEnabled(true);
			//lookupQualifiedBatchNo.setEditable(false);
			lookupQualifiedBatchNo.setValue(_MortgageRedemptionInsurance.newReferenceNo());
			lookupQualifiedBatchNo.setLookupSQL(_MortgageRedemptionInsurance.sqlRenewalBatchNo());
			
			btnState(true, false, false, true, false, true, true);
			pnlState(true, false, false);
		}
		
		if(cmbQualifyMRI.getSelectedIndex() == 1){//RENEWAL
			pnlQualifiedMRICenter.add(scrollQualifiedMRIRenewal, BorderLayout.CENTER);
			//lookupQualifiedBatchNo.setEditable(false);
			lookupQualifiedBatchNo.setValue(_MortgageRedemptionInsurance.newReferenceNo());
			lookupQualifiedBatchNo.setLookupSQL(_MortgageRedemptionInsurance.sqlRenewalBatchNo());
			
			chkQualifiedBatchNo.setEnabled(true);
			
			lblDateCutOff.setText("*Cut Off");
			lblInsuranceCo.setText("*Insurance Co");
			lblProject.setText("*Project");
			
			dateCutOff.setDate(Calendar.getInstance().getTime());
			dateCutOff.setEnabled(true);
			lookupQualifiedInsurance.setEnabled(true);
			lookupQualifiedProject.setEnabled(true);
			
			btnState(true, false, false, true, false, true, true);
			pnlState(true, false, false);
		}
		
		if(cmbQualifyMRI.getSelectedIndex() == 2){//TERMINATION
			pnlQualifiedMRICenter.add(scrollQualifiedMRITermination, BorderLayout.CENTER);
			//lookupQualifiedBatchNo.setEditable(false);
			lookupQualifiedBatchNo.setValue(_MortgageRedemptionInsurance.newTerminationNo());
			lookupQualifiedBatchNo.setLookupSQL(_MortgageRedemptionInsurance.sqlTerminationBatchNo());
			
			chkQualifiedBatchNo.setEnabled(true);
			lblInsuranceCo.setText("*Insurance Co");
			lblProject.setText("*Project Name");
			
			dateCutOff.setDate(Calendar.getInstance().getTime());
			dateCutOff.setEnabled(true);
			lookupQualifiedInsurance.setEnabled(true);
			lookupQualifiedProject.setEnabled(true);
			
			btnState(true, false, false, true, false, true, true);
			pnlState(true, false, false);
		}
	}
	
	//REFRESH APPROVED MRI
	private void refreshApprovedMRI(){
		clearApproveQualifiedMRI();
		
		if(cmbApprovedMRI.getSelectedIndex() == 0 && chkQualifiedBatchNo.isSelected() == true){
			pnlApproveQualifiedMRICenter.add(scrollApprovedQualified);
			
			lblApproveQualifiedCompany.setText("*Company Name");
			lblApproveQualifiedInsuranceCoUpper.setText("*Insurance Co");
			//lblApproveQualifiedRPLFNo.setText("*Batch No");
			lblApproveQualifiedInsuranceBroker.setText("*Insurance Broker");
			lblApproveQualifiedInsuranceCoLower.setText("*Insurance Co");
			lblApproveQualifiedPolicyNo.setText("*Policy No");
			lblApproveQualifiedInvoiceNo.setText("*Invoice No");
			
			lookupApproveQualifiedCompanyUpper.setEditable(true);
			lookupApproveQualifiedInsuranceUpper.setEditable(true);
			
			//lookupApproveQualifiedBatchNo.setEditable(true);
			
			
			//lookupApproveQualifiedBatchNo.setLookupSQL(_MortgageRedemptionInsurance.sqlRenewalBatchNo());
			//lookupApproveQualifiedBatchNo.setLookupSQL(_MortgageRedemptionInsurance.sqlApproveQualifiedEnrollment());
			
			lookupApprovedInsuranceBrokerLower.setEditable(true);
			lookupApprovedInsuranceCoLower.setEditable(true);
			txtApprovedPolicyNoLower.setEditable(true);
			txtApprovedInvoiceNoLower.setEditable(true);
			
			dateApprovedMRI.getCalendarButton().setEnabled(true);
			dateApprovedMRI.setEditable(true);
			dateApprovedMRI.setDate(Calendar.getInstance().getTime());
			
			//txtApprovedRefNo.setText(_MortgageRedemptionInsurance.newReferenceNo());
			//txtApprovedRPLFNo.setText(_MortgageRedemptionInsurance.newApprovedRPLFNo(lookupA));
			//pnlState(false, true);
			pnlState(false, true, false);
			//btnState(false, false, true, true, true, true);
		}
		
		if(cmbApprovedMRI.getSelectedIndex() == 1){
			
		}
		
		if(cmbApprovedMRI.getSelectedIndex() == 2){
			
		}
	}

	//CHECK FOR THE REQUIRED FIELD BEFORE SAVING
	private boolean toSave(){
		if(tabCenter.getSelectedIndex() == 0){//Qualified MRI Accounts

			if(cmbQualifyMRI.getSelectedIndex() == 0){//Enrollment
				if(modelQMRIEnroll.getRowCount() == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				if(_MortgageRedemptionInsurance.getSelectedQualifiedMRIEnroll(modelQMRIEnroll) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to Enroll", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				if(lookupQualifiedProject.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}	
			}

			if(cmbQualifyMRI.getSelectedIndex() == 1){//Renewal
				
				if(modelQMRIRenew.getRowCount() == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}

				if(_MortgageRedemptionInsurance.getSelectedQualifiedMRIRenew(modelQMRIRenew) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to Renew", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				if(dateCutOff.getDate() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input date cut off", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				if(lookupQualifiedInsurance.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select insurance company", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				if(lookupQualifiedProject.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}

			if(cmbQualifyMRI.getSelectedIndex() == 2){//Termination
				if(modelQMRITerminate.getRowCount() == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				if(_MortgageRedemptionInsurance.getSelectedQualifiedMRITerminate(modelQMRITerminate) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to Terminate", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}

				if(lookupQualifiedInsurance.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Company", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				if(lookupQualifiedProject.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}

		if(tabCenter.getSelectedIndex() == 1){//Approve Qualified MRI Accounts
			if(modelApproveQualified.getRowCount() == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(_MortgageRedemptionInsurance.getSelectedApproveQualified(modelApproveQualified) == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(lookupApproveQualifiedCompanyUpper.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Company", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(lookupApproveQualifiedInsuranceUpper.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Company", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(lookupApprovedInsuranceCoLower.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Company", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(lookupApprovedInsuranceBrokerLower.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Broker", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			/*if(txtApprovedPolicyNoLower.getText().isEmpty()){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Policy No", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(txtApprovedInvoiceNoLower.getText().isEmpty()){//XXX UNCOMMENT IF NEED TO INVOICE IS REQUIRED
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Invoice No", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}*/
			
			if(dateApprovedMRI.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Date Approved", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	//GENERATES REPORT FOR MRI APPROVED ACCOUNTS
	private void generateMRI_ForPaymentInsurance(String invoice_no){
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		//mapParameters.put("company_name", txtAMACompany.getText());
		mapParameters.put("company", getCompany());
		mapParameters.put("insurance_company", txtAMAInsuranceCompany.getText());
		//mapParameters.put("search_by", cmbAMASearchBy.getSelectedIndex());
		mapParameters.put("invoice_no", invoice_no);
		
		//XXX RENAME HERE
		
		FncReport.generateReport("/Reports/rptMRIQualifiedEnroll.jasper", "Approved MRI Account", mapParameters);
	}
	
	private void generateMRI_Qualified_Enrolled(Boolean preview_batch, String batch_no){
		
		ArrayList<TD_MRI_Qualifed_Enroll> listMRI_Qualified_Enroll = new ArrayList<TD_MRI_Qualifed_Enroll>();
		
		String insurance_company = "";
		
		for(int x = 0; x<modelQMRIEnroll.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelQMRIEnroll.getValueAt(x, 0);
			
			if(isSelected){
				
				String phase = (String) modelQMRIEnroll.getValueAt(x, 4);
				String block = (String) modelQMRIEnroll.getValueAt(x, 5);
				String lot = (String) modelQMRIEnroll.getValueAt(x, 6);
				String client_name = (String) modelQMRIEnroll.getValueAt(x, 8);
				BigDecimal loan_amt = (BigDecimal) modelQMRIEnroll.getValueAt(x, 14);
				BigDecimal amt_insured = (BigDecimal) modelQMRIEnroll.getValueAt(x, 15);
				String term = (String) modelQMRIEnroll.getValueAt(x, 16);
				BigDecimal premium = (BigDecimal) modelQMRIEnroll.getValueAt(x, 17);
				Date date_insurance = (Date) modelQMRIEnroll.getValueAt(x, 18);
				String certificate_no = (String) modelQMRIEnroll.getValueAt(x, 19);
				String invoice_no = (String) modelQMRIEnroll.getValueAt(x, 20);
				String business_class = (String) modelQMRIEnroll.getValueAt(x, 21);
				insurance_company = (String) modelQMRIEnroll.getValueAt(x, 13);
				
				listMRI_Qualified_Enroll.add(new TD_MRI_Qualifed_Enroll(phase, block, lot, client_name, loan_amt, amt_insured, term, premium, date_insurance, certificate_no, invoice_no, business_class));
				
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		String proj_name = txtQualifiedProject.getText().isEmpty() ? "TERRAVERDE RESIDENCES":txtQualifiedProject.getText();
		String project_batch = String.format("%s (Batch No: %s)", proj_name, batch_no);
		
		mapParameters.put("company", getCompany());
		mapParameters.put("reference_no", lookupQualifiedBatchNo.getValue());
		
		mapParameters.put("preview_batch", preview_batch);
		mapParameters.put("project_batch", project_batch);
		
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("proj_name", txtQualifiedProject.getText());
		mapParameters.put("insurance_company", insurance_company);
		mapParameters.put("batch_no", batch_no);
		mapParameters.put("listMRI_Qualified_Enroll", listMRI_Qualified_Enroll);
		
		FncReport.generateReport("/Reports/rptMRIQualifiedEnroll.jasper", "Qualified Accounts Enrollment", mapParameters);
 	}
	
	private void generateMRI_Qualified_Renewal(Boolean preview_batch, String batch_no){

		ArrayList<TD_MRI_Qualified_Renew> listMRI_Qualified_Renew = new ArrayList<TD_MRI_Qualified_Renew>();
		
		String insurance_company = "";
		String proj_id = lookupQualifiedProject.getValue().toString(); 
		String company_name = FncGlobal.GetString("Select a.company_name FROM mf_company a \n" +
												"LEFT JOIN mf_project b on b.co_id = a.co_id \n"+
												"WHERE TRIM(b.proj_id) = '"+proj_id+"'");
		
		System.out.printf("Company: %s%n", company_name);
		
		for(int x = 0; x<modelQMRIRenew.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelQMRIRenew.getValueAt(x, 0);
			if(isSelected){
				
				String phase = (String) modelQMRIRenew.getValueAt(x, 5);
				String block = (String) modelQMRIRenew.getValueAt(x, 6);
				String lot = (String) modelQMRIRenew.getValueAt(x, 7);
				String client_name = (String) modelQMRIRenew.getValueAt(x, 9);
				BigDecimal balance = (BigDecimal) modelQMRIRenew.getValueAt(x, 10);
				BigDecimal amt_insured = (BigDecimal) modelQMRIRenew.getValueAt(x, 11);
				BigDecimal premium = (BigDecimal) modelQMRIRenew.getValueAt(x, 12);
				String term = modelQMRIRenew.getValueAt(x, 13).toString().equals("6") ? "6 mos.":"1 year";
				
				insurance_company = (String) modelQMRIRenew.getValueAt(x, 18);
				BigDecimal loan_amt = (BigDecimal) modelQMRIRenew.getValueAt(x, 22);
				Date date_insurance = (Date) modelQMRIRenew.getValueAt(x, 23);
				String certificate_no = (String) modelQMRIRenew.getValueAt(x, 24);
				String invoice_no = (String) modelQMRIRenew.getValueAt(x, 25);
				String business_class = (String) modelQMRIRenew.getValueAt(x, 26);
				
				listMRI_Qualified_Renew.add(new TD_MRI_Qualified_Renew(phase, block, lot, client_name, loan_amt, amt_insured, term, premium, date_insurance, certificate_no, invoice_no, business_class));
			}
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		String proj_name = txtQualifiedProject.getText().isEmpty() ? "TERRAVERDE RESIDENCES":txtQualifiedProject.getText();
		String project_batch = String.format("%s (Batch No: %s)", proj_name, batch_no);
		
		mapParameters.put("company_name", company_name); 
		mapParameters.put("reference_no", lookupQualifiedBatchNo.getValue());
		
		mapParameters.put("preview_batch", preview_batch);
		mapParameters.put("project_batch", project_batch);
		
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("proj_name", txtQualifiedProject.getText());
		mapParameters.put("insurance_company", insurance_company);
		mapParameters.put("batch_no", batch_no);
		
		mapParameters.put("listMRI_Qualified_Renew", listMRI_Qualified_Renew);

		FncReport.generateReport("/Reports/rptMRIQualifiedRenew.jasper", "Qualified Accounts for Renewal", mapParameters);
	}
	
	private void generateMRI_Qualified_Termination(Boolean preview_batch, String batch_no){
		ArrayList<TD_MRI_Qualified_Terminate> listMRI_Qualified_Terminate = new ArrayList<TD_MRI_Qualified_Terminate>();
		
		String insurance_company = "";
		
		for(int x = 0; x<modelQMRITerminate.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelQMRITerminate.getValueAt(x, 0);

			if(isSelected){
				String phase = (String) modelQMRITerminate.getValueAt(x, 6);
				String block = (String) modelQMRITerminate.getValueAt(x, 7);
				String lot = (String) modelQMRITerminate.getValueAt(x, 8);
				String client_name = (String) modelQMRITerminate.getValueAt(x, 10);
				BigDecimal amt_insured = (BigDecimal) modelQMRITerminate.getValueAt(x, 12);
				BigDecimal balance = (BigDecimal) modelQMRITerminate.getValueAt(x, 11);
				Integer term = (Integer) modelQMRITerminate.getValueAt(x, 14);
				BigDecimal premium = (BigDecimal) modelQMRITerminate.getValueAt(x, 13);
				Date effectivity = (Date) modelQMRITerminate.getValueAt(x, 21);
				String invoice_no = (String) modelQMRITerminate.getValueAt(x, 22);
				String certificate_no = (String) modelQMRITerminate.getValueAt(x, 23);
				Date date_from = (Date) modelQMRITerminate.getValueAt(x, 24);
				Date date_to = (Date) modelQMRITerminate.getValueAt(x, 25);
				String reason = (String) modelQMRITerminate.getValueAt(x, 18);
				insurance_company = (String) modelQMRITerminate.getValueAt(x, 16);
				
				listMRI_Qualified_Terminate.add(new TD_MRI_Qualified_Terminate(phase, block, lot, client_name, amt_insured, balance, term, premium, effectivity, invoice_no, certificate_no, date_from, date_to, reason));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("company", getCompany());
		mapParameters.put("batch_no", batch_no);
		mapParameters.put("proj_name", txtQualifiedInsurance.getText().isEmpty() ? "TERRAVERDE RESIDENCES":txtQualifiedProject.getText());
		mapParameters.put("insurance_company", insurance_company);
		mapParameters.put("prepared_by", UserInfo.FirstName);
		mapParameters.put("listMRI_Qualified_Terminate", listMRI_Qualified_Terminate);
		
		FncReport.generateReport("/Reports/rptMRIQualifiedTerminate.jasper", "Qualified Accounts for Termination", mapParameters);
	}
	
	//XXX REMOVE THIS
	//GENERATES A REPORT OF MRI TERMINATED ACCOUNTS
	private void generateMRI_QualifiedTerminated(){
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("termination_batch_no", lookupQualifiedBatchNo.getValue());
		mapParameters.put("prepared_by", UserInfo.FullName);
		
		FncReport.generateReport("/Reports/rptMRIQualifiedTerminate.jasper", "List of Qualified Accounts for Termination", mapParameters);
	}
	
	private void generateMRI_For_Issuance_Policy(){
		ArrayList<TD_MRI_Issuance_Policy> listMRI_ForIssuancePolicy = new ArrayList<TD_MRI_Issuance_Policy>();

		for(int x= 0; x<modelApproveQualified.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelApproveQualified.getValueAt(x, 0);
			
			if(isSelected){
				
				String proj_alias = (String) modelApproveQualified.getValueAt(x, 3);
				String unit_desc = (String) modelApproveQualified.getValueAt(x, 7);
				String client_name = (String) modelApproveQualified.getValueAt(x, 9);
				Date date_enrolled = (Date) modelApproveQualified.getValueAt(x, 18);
				Date date_approved = (Date) modelApproveQualified.getValueAt(x, 22);
				String policy_no = (String) modelApproveQualified.getValueAt(x, 21);
				String reference_no = (String) modelApproveQualified.getValueAt(x, 19);
				String invoice_no = modelApproveQualified.getValueAt(x, 20) != null ? (String) modelApproveQualified.getValueAt(x, 20).toString() : "";
				BigDecimal amt_insured = (BigDecimal) modelApproveQualified.getValueAt(x, 16);
				BigDecimal premium = (BigDecimal) modelApproveQualified.getValueAt(x, 14);
				String stage = (String) modelApproveQualified.getValueAt(x, 17);
				
				listMRI_ForIssuancePolicy.add(new TD_MRI_Issuance_Policy(proj_alias, unit_desc, client_name, date_enrolled, date_approved, policy_no, reference_no, invoice_no, amt_insured, premium, stage));
				
			}
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		//mapParameters.put("proj_name", txtQualifiedProject.getText());
		mapParameters.put("company_name", txtApproveQualifiedCompanyUpper.getText());
		mapParameters.put("insurance_company", txtApprovedInsuranceCoLower.getText());
		//mapParameters.put("rplf_no", txtApproveQualifiedRPLFNo.getText());
		
		mapParameters.put("listMRI_ForIssuancePolicy", listMRI_ForIssuancePolicy);
		
		FncReport.generateReport("/Reports/rptMRI_ForIssuancePolicy.jasper", "Qualified Accounts for Enrollment", mapParameters);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		int selected_tab = tabCenter.getSelectedIndex();
		
		int qualify_selected = cmbQualifyMRI.getSelectedIndex();
		//int approved_selected = cmbApprovedMRI.getSelectedIndex();

		if(actionCommand.equals("Qualified MRI Generate")){
			
			if(qualify_selected == 0){//ENROLLMENT
				if(chkQualifiedBatchNo.isSelected()){
					_MortgageRedemptionInsurance.displayQualifiedEnrollment_BatchNo(lookupQualifiedBatchNo.getValue(), modelQMRIEnroll, rowHeaderQualifyEnroll);
					tblQualifiedMRIEnrollment.packAll();
					scrollQualifiedMRIEnrollment.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRIEnrollment.getRowCount())));
					tblQualifiedMRIEnrollment.setEditable(false);
				}else{
					_MortgageRedemptionInsurance.displayQualifyMRIEnrollment(lookupQualifiedInsurance.getValue(),lookupQualifiedProject.getValue(),modelQMRIEnroll, rowHeaderQualifyEnroll);
					tblQualifiedMRIEnrollment.packAll();
					scrollQualifiedMRIEnrollment.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRIEnrollment.getRowCount())));
					tblQualifiedMRIEnrollment.setEditable(true);
					
				}
			}
			
			if(qualify_selected == 1){//RENEWAL
				if(chkQualifiedBatchNo.isSelected()){
					_MortgageRedemptionInsurance.displayQualifiedRenewal_BatchNo(lookupQualifiedBatchNo.getValue(), lookupQualifiedProject.getValue(), modelQMRIRenew, rowHeaderQualifyRenew);
					tblQualifiedMRIRenewal.packAll();
					scrollQualifiedMRIRenewal.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRIRenewal.getRowCount())));
					tblQualifiedMRIRenewal.setEditable(false);
				}else{
					_MortgageRedemptionInsurance.displayQualifyMRIRenewal(dateCutOff.getDate(), lookupQualifiedProject.getValue(), lookupQualifiedInsurance.getValue(), modelQMRIRenew, rowHeaderQualifyRenew);
					tblQualifiedMRIRenewal.packAll();
					scrollQualifiedMRIRenewal.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRIRenewal.getRowCount())));
					tblQualifiedMRIRenewal.setEditable(true);
				}
			}
			
			if(qualify_selected == 2){//TERMINATION
				
				if(chkQualifiedBatchNo.isSelected()){
					_MortgageRedemptionInsurance.displayQualifiedTerminated_BatchNo(lookupQualifiedBatchNo.getValue(), modelQMRITerminate , rowHeaderQualifyTerminate);
					tblQualifiedMRITermination.packAll();
					scrollQualifiedMRITermination.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRITermination.getRowCount())));
					tblQualifiedMRITermination.setEditable(true);
				}else{
					_MortgageRedemptionInsurance.displayQualifyMRITermination(dateCutOff.getDate(),lookupQualifiedInsurance.getValue(), lookupQualifiedProject.getValue(), modelQMRITerminate, rowHeaderQualifyTerminate);
					tblQualifiedMRITermination.packAll();
					scrollQualifiedMRITermination.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedMRITermination.getRowCount())));
					tblQualifiedMRITermination.setEditable(true);
				}
			}
		}
		
		if(actionCommand.equals("Approve Qualified Generate")){
			_MortgageRedemptionInsurance.displayApproveQualifiedMRI(lookupApproveQualifiedInsuranceUpper.getValue(), modelApproveQualified, rowHeaderApproveQualifiedMRI);
			tblApproveQualifiedMRI.packAll();
			scrollApprovedQualified.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblApproveQualifiedMRI.getRowCount())));
			
		}
		
		if(actionCommand.equals("Approved MRI Generate")){
			_MortgageRedemptionInsurance.displayMRIApprovedAccounts(cmbAMASearchBy.getSelectedIndex(), lookupAMASearch.getValue(), modelMRIPmtInsurance, rowHeaderForPmtInsurance);
			tblForPmtInsurance.packAll();
			scrollForPaymentInsurance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblForPmtInsurance.getRowCount())));
		}

		if(actionCommand.equals("Post")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to post?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					if(selected_tab == 0){
						if(qualify_selected == 0){//ENROLLMENT
							_MortgageRedemptionInsurance.post_Qualify_Enrollment(lookupQualifiedBatchNo.getValue(),lookupQualifiedProject.getValue(), modelQMRIEnroll);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected Accounts Enrolled\nBatch No: %s", lookupQualifiedBatchNo.getValue()) , "Save", JOptionPane.INFORMATION_MESSAGE);
							generateMRI_Qualified_Enrolled(true, lookupQualifiedBatchNo.getValue());
							cmbQualifyMRI.setSelectedItem(null);
							refreshQualifiedMRI();
						}
						
						if(qualify_selected == 1){//RENEWAL
							_MortgageRedemptionInsurance.post_Qualify_Renewal(lookupQualifiedBatchNo.getValue(), lookupQualifiedInsurance.getValue(), lookupQualifiedProject.getValue(), modelQMRIRenew);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected Accounts Enrolled\nBatch No: %s", lookupQualifiedBatchNo.getValue()), "Save", JOptionPane.INFORMATION_MESSAGE);
							generateMRI_Qualified_Renewal(true, lookupQualifiedBatchNo.getValue());
							cmbQualifyMRI.setSelectedItem(null);
							refreshQualifiedMRI();
						}
						
						if(qualify_selected == 2){//TERMINATION
							_MortgageRedemptionInsurance.postQualifiedMRITerminate(lookupQualifiedBatchNo.getValue(), modelQMRITerminate);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected Accounts Terminated\nBatch No: %s", lookupQualifiedBatchNo.getValue()), "Save", JOptionPane.INFORMATION_MESSAGE);
							generateMRI_Qualified_Termination(true, lookupQualifiedBatchNo.getValue());
							cmbQualifyMRI.setSelectedItem(null);
							refreshQualifiedMRI();
						}
					}
					
					if(selected_tab == 1){//FOR APPROVAL QUALIFIED ACCOUNTS
							_MortgageRedemptionInsurance.post_Qualified_Approval(lookupApproveQualifiedCompanyUpper.getValue(), 
							/*txtApproveQualifiedRPLFNo.getText(),*/ lookupApprovedInsuranceCoLower.getValue(), lookupApprovedInsuranceBrokerLower.getValue(), modelApproveQualified);
							
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected Accounts Posted! "/*\nRPLF No: %s", txtApproveQualifiedRPLFNo.getText()*/), "Save", JOptionPane.INFORMATION_MESSAGE);
							clearApproveQualifiedMRI();
					}
				}
			}
		}
		
		if(actionCommand.equals("Preview")){//Preview
			if(selected_tab == 0){
				if(qualify_selected == 0){//ENROLLMENT
					/*if(chkQualifiedBatchNo.isSelected() == false){
						generateMRI_Qualified_Enrolled(false, null);
					}else{*/
						generateMRI_Qualified_Enrolled(true, lookupQualifiedBatchNo.getValue());
					//}
				}
				
				if(qualify_selected == 1){//RENEWAL
					/*if(chkQualifiedBatchNo.isSelected() == false){
						generateMRI_Qualified_Renewal(false, null);
					}else{*/
						generateMRI_Qualified_Renewal(true, lookupQualifiedBatchNo.getValue());
					//}
				}
				
				if(qualify_selected == 2){//
					//if(chkQualifiedBatchNo.isSelected()){
					//}else{
						
					//}
						generateMRI_Qualified_Termination(true, lookupQualifiedBatchNo.getValue());
				}
				
			}
			if(selected_tab == 1){
				//CHANGE FOR ISSUANCE OF POLICY
				generateMRI_For_Issuance_Policy();
			}
			if(selected_tab == 2){
				//generateMRI_Approved_Accounts();
			}
		}

		if(actionCommand.equals("Clear")){//Clear
			if(selected_tab == 0){
				cmbQualifyMRI.setSelectedItem(null);
				clearQualifiedMRI();
			}
			if(selected_tab == 1){
				//cmbApprovedMRI.setSelectedItem(null);
				clearApproveQualifiedMRI();
			}
			if(selected_tab == 2){
				//clearApprovedMRI();
			}
		}
	}

	private String getCompany() {
		
		pgSelect db = new pgSelect();
		
		String sql = "Select company_name from mf_company where co_id = (Select co_id from mf_project where proj_id = '"+lookupQualifiedProject.getValue().toString()+"')";
		
		db.select(sql);
		
		return (String) db.getResult()[0][0];
	}
}
