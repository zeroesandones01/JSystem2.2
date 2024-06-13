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
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.table.NumberEditorExt;

import tablemodel.modelFire_ForIssuancePolicy;
import tablemodel.modelFI_ForPmtInsurance;
import tablemodel.modelQualifiedFIEnroll;
import tablemodel.modelQualifiedFIRenew;
import tablemodel.modelQualifiedFITerminate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
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
public class FireInsurance extends _JInternalFrame implements _GUI,
ActionListener {

	private static final long serialVersionUID = -5245628832039916346L;

	private static String title = "Fire Insurance (FI)";
	static Dimension SIZE = new Dimension(900, 500);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTabbedPane tabCenter;
	private JPanel pnlQualifiedFI;
	private JPanel pnlQualifiedFINorth;
	private JPanel pnlQualifiedFIFor;
	private JComboBox cmbQualifiedFI;

	private JPanel pnlQualifiedFINorthCenter;
	private JPanel pnlQualifiedFINCLabel;
	private JLabel lblQualifiedCutOff;
	private JLabel lblQualifiedInsuranceCoUpper;
	private JLabel lblQualifiedProject;

	private JPanel pnlQualifiedFINCComponent;

	private JPanel pnlQualifiedFIDateCutOff;
	private _JDateChooser dateQualifiedCutOff;
	private JLabel lblQualifiedFIBatchNo;
	private JPanel pnlQualifiedBatchNo;
	private JCheckBox chkQualifiedBatchNo;
	private _JLookup lookupQualifiedFIBatchNo;

	private JPanel pnlQualifiedFIInsurance;
	private _JLookup lookupQualifiedInsurance;
	private _JXTextField txtQualifiedInsurance;

	private JPanel pnlQualifiedFIProject;
	private _JLookup lookupQualifiedProject;
	private _JXTextField txtQualifiedProject;

	private JPanel pnlQualifiedFINorthEast;
	private JButton btnQualifiedGenerate;

	private JPanel pnlQualifiedFICenter;

	private JScrollPane scrollQualifiedFIEnroll;
	private _JTableMain tblQualifiedFIEnroll;
	private JList rowHeaderQualifiedFIEnroll;
	private modelQualifiedFIEnroll modelQFIEnroll;

	private JScrollPane scrollQualifiedFIRenew;
	private _JTableMain tblQualifiedFIRenew;
	private JList rowHeaderQualifiedFIRenew;
	private modelQualifiedFIRenew modelQFIRenew;

	private JScrollPane scrollQualifiedFITerminate;
	private _JTableMain tblQualifiedFITerminate;
	private JList rowHeaderQualifiedFITerminate;
	private modelQualifiedFITerminate modelQFITerminate;

	private JPanel pnlApproveQualified;
	private JPanel pnlApproveQualifiedNorth;
	private JPanel pnlApproveQualifiedNorthWest;
	private JComboBox cmbApproveQualified;

	private JPanel pnlApproveQualifiedNorthCenter;

	private JPanel pnlApproveQualifiedNCLabel;
	private JLabel lblApproveQualifiedCompany;
	private JLabel lblApproveQualifiedBatchNo;
	private JLabel lblApproveQualifiedInsurance;

	private JPanel pnlApproveQualifiedNCComponent;

	private JPanel pnlApproveQualifiedCompany;
	private _JLookup lookupApproveQualifiedCompany;
	private _JXTextField txtApproveQualifiedCompany;

	private JPanel pnlApproveQualifiedBatchNo;
	private _JLookup lookupApproveQualifiedBatchNo;

	private JPanel pnlApproveQualifiedInsurance;
	private _JLookup lookupApproveQualifiedInsurance;
	private _JXTextField txtApproveQualifiedInsurance;

	private JPanel pnlApproveQualifiedNorthEast;
	private JButton btnApproveQualifiedGenerate;

	private JPanel pnlApproveQualifiedCenter;

	private JPanel pnlApproveQualifiedSouth;

	private JPanel pnlApproveQualifiedSouthLabel;
	private JLabel lblApproveQualifiedInsuranceCo;
	private JLabel lblApproveQualifiedInsuranceBroker;
	private JLabel lblApproveQualifiedPolicyNo;
	private JLabel lblApproveQualifiedInvoiceNo;

	private JPanel pnlApproveQualifiedSouthComponent;

	private JPanel pnlApproveQualifiedInsuranceCo;
	private _JLookup lookupApproveQualifiedInsuranceCo;
	private _JXTextField txtApproveQualifiedInsuranceCo;

	private JPanel pnlApproveQualifiedInsuranceBroker;
	private _JLookup lookupApproveQualifiedInsuranceBroker;
	private _JXTextField txtApproveQualifiedInsuranceBroker;

	private JPanel pnlApproveQualifiedPolicyNo;
	private _JXTextField txtApproveQualifiedPolicyNo;
	//private JLabel lblApproveQualifiedRPLFNo; COMMENTED BY MONIQUE DTD 2023-02-28 
	//private _JXTextField txtApproveQualifiedRPLFNo; COMMENTED BY MONIQUE DTD 2023-02-28 

	private JPanel pnlApproveQualifiedInvoiceNo;
	private _JXTextField txtApproveQualifiedInvoiceNo;

	private JPanel pnlApproveQualifiedCoverage;
	private JLabel lblApproveQualifiedDateFrom;
	private _JDateChooser dateApproveQualifiedFrom;
	private JLabel lblApproveQualifiedDateTo;
	private _JDateChooser dateApproveQualifiedDateTo;

	private JPanel pnlForPaymentInsuranceComp;
	private JPanel pnlForPaymentNorth;

	private JComboBox cmbApprovedFI;
	private JPanel pnlApprovedFINorthCenter;

	private JPanel pnlForPaymentNCLabel;
	private JLabel lblApprovedCompany;
	private JLabel lblApprovedProject;
	private JLabel lblApprovedPreview;

	private JPanel pnlForPaymentNCComponent;

	private JPanel pnlApprovedCompany;
	private _JLookup lookupApprovedCompany;
	private _JXTextField txtApprovedCompany;

	private JPanel pnlApprovedProject;
	private _JLookup lookupApprovedProject;
	private _JXTextField txtApprovedProject;

	private JPanel pnlApprovedPreviewBy;
	private JComboBox cmbApprovedPreviewBy;
	private JPanel pnlApprovedPreviewLookup;
	private _JLookup lookupApprovedPreview;

	private _JLookup lookupApprovedFIInsuranceUpper;

	private JPanel pnlApprovedFINorthEast;
	private JButton btnApprovedGenerate;

	private JPanel pnlApprovedFICenter;

	private JScrollPane scrollApproveQualifiedEnroll;

	private JScrollPane scrollFI_ForPmtInsurance;
	private _JTableMain tblApprovedFIRenew;
	private JList rowHeaderFI_ForPmtInsurance;
	private modelFI_ForPmtInsurance modelFI_ForPmtInsurance;

	private JScrollPane scrollFireForIssuancePolicy;
	private _JTableMain tblFireForIssuancePolicy;
	private JList rowHeaderFireForIssuancePolicy;
	private modelFire_ForIssuancePolicy modelFI_ForIssuancePolicy;

	private JLabel lblApprovedFIInsuranceLower;
	private JLabel lblApprovedFIInsuranceBrokerLower;
	private JLabel lblApprovedFIInvoiceNoLower;
	private JLabel lblApprovedFIPolicyNo;

	private _JLookup lookupApprovedFIInsuranceLower;

	private JLabel lblApprovedFIBatchNo;

	private _JLookup lookupApprovedFIInsuranceBrokerLower;

	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;

	private _JXTextField txtApprovedFIRPLFNo;

	private _JXTextField txtApprovedFIInvoiceNoLower;

	private _JXTextField txtApprovedFIPolicyNo;

	private JPanel pnlSouth;
	private JButton btnPost;
	private JButton btnPreview;
	private JButton btnClear;

	public FireInsurance() {
		super(title, true, true, true, true);
		initGUI();
	}

	public FireInsurance(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public FireInsurance(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);

		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		this.add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		{
			tabCenter = new _JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);
			{
				pnlQualifiedFI = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("Qualified Accounts", pnlQualifiedFI);
				{
					pnlQualifiedFINorth = new JPanel(new BorderLayout(3, 3));
					pnlQualifiedFI.add(pnlQualifiedFINorth, BorderLayout.NORTH);
					pnlQualifiedFINorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlQualifiedFIFor = new JPanel(new BorderLayout(3, 3));
						pnlQualifiedFINorth.add(pnlQualifiedFIFor, BorderLayout.WEST);
						pnlQualifiedFIFor.setBorder(JTBorderFactory.createTitleBorder("Accounts for:"));
						//pnlQualifiedFIFor.setPreferredSize(new Dimension(100, 50));
						{
							cmbQualifiedFI = new JComboBox(new String [] {"Enrollment", "Renewal", "Termination"});
							pnlQualifiedFIFor.add(cmbQualifiedFI);
							cmbQualifiedFI.setSelectedItem(null);
							cmbQualifiedFI.setPreferredSize(new Dimension(150, 0));
							cmbQualifiedFI.addItemListener(new ItemListener() {

								public void itemStateChanged(ItemEvent arg0) {
									//int selected_item = cmbQualifiedFI.getSelectedIndex();

									refreshQualifiedFI();
								}
							});
						}
					}
					{
						pnlQualifiedFINorthCenter = new JPanel(new BorderLayout(3, 3));
						pnlQualifiedFINorth.add(pnlQualifiedFINorthCenter);
						{
							pnlQualifiedFINCLabel = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlQualifiedFINorthCenter.add(pnlQualifiedFINCLabel, BorderLayout.WEST);
							{
								lblQualifiedCutOff = new JLabel("Cut Off Date");
								pnlQualifiedFINCLabel.add(lblQualifiedCutOff);
							}
							{
								lblQualifiedInsuranceCoUpper = new JLabel("Insurance Co.");
								pnlQualifiedFINCLabel.add(lblQualifiedInsuranceCoUpper);
							}
							{
								lblQualifiedProject = new JLabel("Project");
								pnlQualifiedFINCLabel.add(lblQualifiedProject);
							}
						}
						{
							pnlQualifiedFINCComponent = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlQualifiedFINorthCenter.add(pnlQualifiedFINCComponent, BorderLayout.CENTER);
							{
								pnlQualifiedFIDateCutOff = new JPanel(new BorderLayout(3, 3));
								pnlQualifiedFINCComponent.add(pnlQualifiedFIDateCutOff);
								{
									dateQualifiedCutOff = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlQualifiedFIDateCutOff.add(dateQualifiedCutOff, BorderLayout.WEST);
									dateQualifiedCutOff.setPreferredSize(new Dimension(150, 0));
								}
								{
									lblQualifiedFIBatchNo = new JLabel("Batch No", JLabel.TRAILING);
									pnlQualifiedFIDateCutOff.add(lblQualifiedFIBatchNo);
								}
								{
									pnlQualifiedBatchNo = new JPanel(new BorderLayout(3, 3));
									pnlQualifiedFIDateCutOff.add(pnlQualifiedBatchNo, BorderLayout.EAST);
									pnlQualifiedBatchNo.setPreferredSize(new Dimension(180, 0));
									{
										chkQualifiedBatchNo = new JCheckBox();
										pnlQualifiedBatchNo.add(chkQualifiedBatchNo, BorderLayout.WEST);
										chkQualifiedBatchNo.setEnabled(false);
										chkQualifiedBatchNo.addItemListener(new ItemListener() {

											@Override
											public void itemStateChanged(ItemEvent arg0) {
												Boolean isSelected = (arg0.getStateChange() == ItemEvent.SELECTED);

												if(isSelected){
													//lookupQualifiedFIBatchNo.setEditable(true);
													lookupQualifiedFIBatchNo.setEnabled(true);
													lookupQualifiedFIBatchNo.setValue(null);

													dateQualifiedCutOff.setDate(null);
													/*dateQualifiedCutOff.getCalendarButton().setEnabled(false);
													dateQualifiedCutOff.setEditable(false);*/
													dateQualifiedCutOff.setEnabled(false);
													//lookupQualifiedInsurance.setEditable(false);
													lookupQualifiedInsurance.setEnabled(false);
													lookupQualifiedInsurance.setValue(null);
													txtQualifiedInsurance.setText("");
													lookupQualifiedProject.setEnabled(false);
													lookupQualifiedProject.setValue(null);
													txtQualifiedProject.setText("");

													lblQualifiedCutOff.setText("Cut Off");
													lblQualifiedInsuranceCoUpper.setText("Insurance Co");
													lblQualifiedProject.setText("Project Name");

													btnState(true, false, false, false, true, true);

												}else{
													//lookupQualifiedFIBatchNo.setEditable(false);
													lookupQualifiedFIBatchNo.setEnabled(false);
													refreshQualifiedFI();

													btnState(true, false, false, true, true, true);
												}
											}
										});
									}
									{
										lookupQualifiedFIBatchNo = new _JLookup(null, "Batch No", 0);
										pnlQualifiedBatchNo.add(lookupQualifiedFIBatchNo, BorderLayout.EAST);
										lookupQualifiedFIBatchNo.setPreferredSize(new Dimension(150, 0));
										lookupQualifiedFIBatchNo.addLookupListener(new LookupListener() {

											@Override
											public void lookupPerformed(LookupEvent event) {
												Object [] data = ((_JLookup) event.getSource()).getDataSet();
												FncSystem.out("Display sql for lookup of batch No", lookupQualifiedFIBatchNo.getLookupSQL());

												if(data != null){
													String batch_no = (String) data[0];
													btnState(true, false, false ,false, true, true);
													if(cmbQualifiedFI.getSelectedIndex() == 0){
														_FireInsurance.displayEnrolledAccountsBatchNo(batch_no, modelQFIEnroll, rowHeaderQualifiedFIEnroll);
														tblQualifiedFIEnroll.packAll();
														scrollQualifiedFIEnroll.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFIEnroll.getRowCount())));
														tblQualifiedFIEnroll.setEditable(false);
													}
													if(cmbQualifiedFI.getSelectedIndex() == 1){
														_FireInsurance.displayRenewedAccountsBatchNo(batch_no, modelQFIRenew, rowHeaderQualifiedFIRenew);
														tblQualifiedFIRenew.packAll();
														scrollQualifiedFIRenew.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFIRenew.getRowCount())));
														tblQualifiedFIRenew.setEditable(false);
													}
													if(cmbQualifiedFI.getSelectedIndex() == 2){
														_FireInsurance.displayTerminatedAccountsBatchNo(batch_no, modelQFITerminate, rowHeaderQualifiedFITerminate);
														tblQualifiedFITerminate.packAll();
														scrollQualifiedFITerminate.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFITerminate.getRowCount())));
														tblQualifiedFITerminate.setEditable(false);
													}
												}
											}
										});
									}
								}
							}
							{
								pnlQualifiedFIInsurance = new JPanel(new BorderLayout(3, 3));
								pnlQualifiedFINCComponent.add(pnlQualifiedFIInsurance);
								{
									lookupQualifiedInsurance = new _JLookup(null, "Insurance Company", 0);
									pnlQualifiedFIInsurance.add(lookupQualifiedInsurance, BorderLayout.WEST);
									lookupQualifiedInsurance.setPreferredSize(new Dimension(100, 0));
									lookupQualifiedInsurance.setLookupSQL(_MortgageRedemptionInsurance.sqlInsuranceCompany());
									lookupQualifiedInsurance.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();

											if(data != null){
												String insurance_co = (String) data[1];
												txtQualifiedInsurance.setText(insurance_co);
												FncSystem.out("Display sql for Qualified Insurance Co.", lookupQualifiedInsurance.getLookupSQL());
											}
										}
									});
								}
								{
									txtQualifiedInsurance = new _JXTextField();
									pnlQualifiedFIInsurance.add(txtQualifiedInsurance, BorderLayout.CENTER);
								}
							}
							{
								pnlQualifiedFIProject = new JPanel(new BorderLayout(3, 3));
								pnlQualifiedFINCComponent.add(pnlQualifiedFIProject);
								{
									lookupQualifiedProject = new _JLookup(null, "Project", 0);
									pnlQualifiedFIProject.add(lookupQualifiedProject, BorderLayout.WEST);
									lookupQualifiedProject.setPreferredSize(new Dimension(100, 0));
									lookupQualifiedProject.setLookupSQL(_MortgageRedemptionInsurance.sqlProject());
									lookupQualifiedProject.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();

											if(data != null){
												//String proj_id = (String) data[0];
												String proj_name = (String) data[1];
												txtQualifiedProject.setText(proj_name);
												FncSystem.out("Display sql for Qualified Project", lookupQualifiedProject.getLookupSQL());
												/*if(cmbQualifiedFI.getSelectedIndex() == 1){
													txtQualifiedFIRPLFNo.setText(_FireInsurance.newRPLFNo(null, proj_id, true));
												}*/
												if(cmbQualifiedFI.getSelectedIndex() == 0){
													_FireInsurance.displayQualifiedFIEnroll(lookupQualifiedProject.getValue(),modelQFIEnroll, rowHeaderQualifiedFIEnroll);
													tblQualifiedFIEnroll.packAll();
													scrollQualifiedFIEnroll.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFIEnroll.getRowCount())));
													tblQualifiedFIEnroll.setEditable(true);
												}
												if(cmbQualifiedFI.getSelectedIndex() == 1){
													_FireInsurance.displayQualifiedFIRenew(dateQualifiedCutOff.getDate() ,lookupQualifiedInsurance.getValue(), lookupQualifiedProject.getValue(), modelQFIRenew, rowHeaderQualifiedFIRenew);
													tblQualifiedFIRenew.packAll();
													scrollQualifiedFIRenew.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFIRenew.getRowCount())));
													tblQualifiedFIRenew.setEditable(true);
												}
												if(cmbQualifiedFI.getSelectedIndex() == 2){
													_FireInsurance.displayQualifiedFITerminate(lookupQualifiedInsurance.getValue(), lookupQualifiedProject.getValue(), dateQualifiedCutOff.getDate() ,modelQFITerminate, rowHeaderQualifiedFITerminate);
													tblQualifiedFITerminate.packAll();
													scrollQualifiedFITerminate.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFITerminate.getRowCount())));
													tblQualifiedFITerminate.setEditable(true);
												}
											}
										}
									});
								}
								{
									txtQualifiedProject = new _JXTextField();
									pnlQualifiedFIProject.add(txtQualifiedProject, BorderLayout.CENTER);
								}
							}
						}
					}
					/*{
						pnlQualifiedFINorthEast = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlQualifiedFINorth.add(pnlQualifiedFINorthEast, BorderLayout.EAST);
						pnlQualifiedFINorthEast.setPreferredSize(new Dimension(200, 0));
						{
							btnQualifiedGenerate = new JButton("Generate"); 
							pnlQualifiedFINorthEast.add(btnQualifiedGenerate);
							btnQualifiedGenerate.setActionCommand("Qualified FI Generate");
							btnQualifiedGenerate.addActionListener(this);
						}
					}*/
				}
				{
					pnlQualifiedFICenter = new JPanel(new BorderLayout(3, 3));
					pnlQualifiedFI.add(pnlQualifiedFICenter, BorderLayout.CENTER);
					pnlQualifiedFICenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					Font fontTable = new Font(FncLookAndFeel.font_name, Font.PLAIN, 12);

					{
						scrollQualifiedFIEnroll = new JScrollPane();
						{
							modelQFIEnroll = new modelQualifiedFIEnroll();
							tblQualifiedFIEnroll = new _JTableMain(modelQFIEnroll);
							scrollQualifiedFIEnroll.setViewportView(tblQualifiedFIEnroll);
							scrollQualifiedFIEnroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblQualifiedFIEnroll.hideColumns("Proj. Alias", "Unit ID", "PBL ID", "Seq. No", "Entity ID", "Model ID");

							/*tblQualifiedFIEnroll.getTableHeader().setFont(fontTable.deriveFont(1));
							tblQualifiedFIEnroll.setRowHeight(25);*/
							
						}
						{
							rowHeaderQualifiedFIEnroll =tblQualifiedFIEnroll.getRowHeader();
							//rowHeaderQualifiedFIEnroll.setFixedCellHeight(25);
							rowHeaderQualifiedFIEnroll.setModel(new DefaultListModel());
							scrollQualifiedFIEnroll.setRowHeaderView(rowHeaderQualifiedFIEnroll);
							scrollQualifiedFIEnroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}

					{
						scrollQualifiedFIRenew = new JScrollPane();
						{
							modelQFIRenew = new modelQualifiedFIRenew();
							tblQualifiedFIRenew = new _JTableMain(modelQFIRenew);
							scrollQualifiedFIRenew.setViewportView(tblQualifiedFIRenew);
							scrollQualifiedFIRenew.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblQualifiedFIRenew.hideColumns("Rec. ID", "Unit ID", "PBL ID", "Seq. No", "Entity ID", "Insurance Line");

							/*tblQualifiedFIRenew.getTableHeader().setFont(fontTable.deriveFont(1));
							tblQualifiedFIRenew.setRowHeight(25);*/
						}
						{
							rowHeaderQualifiedFIRenew = tblQualifiedFIRenew.getRowHeader();
							//rowHeaderQualifiedFIRenew.setFixedCellHeight(25);
							rowHeaderQualifiedFIRenew.setModel(new DefaultListModel());
							scrollQualifiedFIRenew.setRowHeaderView(rowHeaderQualifiedFIRenew);
							scrollQualifiedFIRenew.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}

					{
						scrollQualifiedFITerminate = new JScrollPane();
						{
							modelQFITerminate = new modelQualifiedFITerminate();
							tblQualifiedFITerminate = new _JTableMain(modelQFITerminate);
							scrollQualifiedFITerminate.setViewportView(tblQualifiedFITerminate);
							scrollQualifiedFITerminate.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblQualifiedFITerminate.hideColumns("Rec ID", "Proj ID", "Seq" ,"Unit ID", "PBL ID", "Seq. No", "Entity ID");

							/*tblQualifiedFITerminate.getTableHeader().setFont(fontTable.deriveFont(1));
							tblQualifiedFITerminate.setRowHeight(25);*/
						}
						{
							rowHeaderQualifiedFITerminate = tblQualifiedFITerminate.getRowHeader();
							//rowHeaderQualifiedFITerminate.setFixedCellHeight(25);
							rowHeaderQualifiedFITerminate.setModel(new DefaultListModel());
							scrollQualifiedFITerminate.setRowHeaderView(rowHeaderQualifiedFITerminate);
							scrollQualifiedFITerminate.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}

				}
			}
			{
				pnlApproveQualified = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("For Issuance of Policy", pnlApproveQualified);
				{
					pnlApproveQualifiedNorth = new JPanel(new BorderLayout(3, 3));
					pnlApproveQualified.add(pnlApproveQualifiedNorth, BorderLayout.NORTH);
					pnlApproveQualifiedNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlApproveQualifiedNorthWest = new JPanel(new BorderLayout(3, 3));
						pnlApproveQualifiedNorth.add(pnlApproveQualifiedNorthWest, BorderLayout.WEST);
						pnlApproveQualifiedNorthWest.setBorder(JTBorderFactory.createTitleBorder("Accounts For"));
						{
							cmbApproveQualified = new JComboBox(new String[] {"Enrollment", "Renewal"});
							pnlApproveQualifiedNorthWest.add(cmbApproveQualified, BorderLayout.WEST);
							cmbApproveQualified.setSelectedItem(null);
							cmbApproveQualified.setPreferredSize(new Dimension(150, 0));
							cmbApproveQualified.addItemListener(new ItemListener() {

								@Override
								public void itemStateChanged(ItemEvent arg0) {
									refreshApproveQualified();

								}
							});
						}
					}
					{
						pnlApproveQualifiedNorthCenter = new JPanel(new BorderLayout(3, 3));
						pnlApproveQualifiedNorth.add(pnlApproveQualifiedNorthCenter, BorderLayout.CENTER);
						{
							pnlApproveQualifiedNCLabel = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlApproveQualifiedNorthCenter.add(pnlApproveQualifiedNCLabel, BorderLayout.WEST);
							{
								lblApproveQualifiedCompany = new JLabel("Company Name");
								pnlApproveQualifiedNCLabel.add(lblApproveQualifiedCompany);
							}
							{
								lblApproveQualifiedInsurance = new JLabel("Insurance Co");
								pnlApproveQualifiedNCLabel.add(lblApproveQualifiedInsurance);
							}
							{
								lblApproveQualifiedBatchNo = new JLabel("Batch No");
								pnlApproveQualifiedNCLabel.add(lblApproveQualifiedBatchNo);
							}
						}
						{
							pnlApproveQualifiedNCComponent = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlApproveQualifiedNorthCenter.add(pnlApproveQualifiedNCComponent, BorderLayout.CENTER);
							{
								pnlApproveQualifiedCompany = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedNCComponent.add(pnlApproveQualifiedCompany);
								{
									lookupApproveQualifiedCompany = new _JLookup(null, "Company", 0);
									pnlApproveQualifiedCompany.add(lookupApproveQualifiedCompany, BorderLayout.WEST);
									lookupApproveQualifiedCompany.setPreferredSize(new Dimension(100, 0));
									lookupApproveQualifiedCompany.setLookupSQL(_FireInsurance.sqlCompany());
									lookupApproveQualifiedCompany.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String co_id = (String) data[0];
												String company_name = (String) data[1];
												txtApproveQualifiedCompany.setText(company_name);
												//txtApproveQualifiedRPLFNo.setText(_FireInsurance.newRPLFNo(co_id, null, false));
											}
										}
									});
								}
								{
									txtApproveQualifiedCompany = new _JXTextField();
									pnlApproveQualifiedCompany.add(txtApproveQualifiedCompany, BorderLayout.CENTER);
								}
							}
							{
								pnlApproveQualifiedInsurance = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedNCComponent.add(pnlApproveQualifiedInsurance);
								{
									lookupApproveQualifiedInsurance = new _JLookup(null, "Insurance Co", 0);
									pnlApproveQualifiedInsurance.add(lookupApproveQualifiedInsurance, BorderLayout.WEST);
									lookupApproveQualifiedInsurance.setPreferredSize(new Dimension(100, 0));
									lookupApproveQualifiedInsurance.setLookupSQL(_FireInsurance.sqlInsuranceCompany());
									lookupApproveQualifiedInsurance.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String insurance_company = (String) data[1];
												txtApproveQualifiedInsurance.setText(insurance_company);
											}
										}
									});
								}
								{
									txtApproveQualifiedInsurance = new _JXTextField();
									pnlApproveQualifiedInsurance.add(txtApproveQualifiedInsurance, BorderLayout.CENTER);
								}
							}
							{
								pnlApproveQualifiedBatchNo = new JPanel(new BorderLayout(3, 3));
								pnlApproveQualifiedNCComponent.add(pnlApproveQualifiedBatchNo);
								{
									lookupApproveQualifiedBatchNo = new _JLookup(null, "Batch No", 0);
									pnlApproveQualifiedBatchNo.add(lookupApproveQualifiedBatchNo, BorderLayout.WEST);
									lookupApproveQualifiedBatchNo.setPreferredSize(new Dimension(100, 0));
									lookupApproveQualifiedBatchNo.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String batch_no = (String) data[0];

												if(cmbApproveQualified.getSelectedIndex() == 0){
													_FireInsurance.displayQualifiedFIEnrollment(lookupApproveQualifiedCompany.getValue(), batch_no, lookupApproveQualifiedInsurance.getValue(), modelFI_ForIssuancePolicy, rowHeaderFireForIssuancePolicy);
													tblFireForIssuancePolicy.packAll();
													scrollFireForIssuancePolicy.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblFireForIssuancePolicy.getRowCount())));
												}

												if(cmbApproveQualified.getSelectedIndex() == 1){
													_FireInsurance.displayQualifiedFIRenewal(lookupApproveQualifiedCompany.getValue(), batch_no, lookupApproveQualifiedInsurance.getValue(), modelFI_ForIssuancePolicy, rowHeaderFireForIssuancePolicy);
													tblFireForIssuancePolicy.packAll();
													scrollFireForIssuancePolicy.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblFireForIssuancePolicy.getRowCount())));
												}


											}
										}
									});
								}
							}
						}
					}
					/*{
						pnlApproveQualifiedNorthEast = new JPanel(new BorderLayout(3, 3));
						pnlApproveQualifiedNorth.add(pnlApproveQualifiedNorthEast, BorderLayout.EAST);
						pnlApproveQualifiedNorthEast.setPreferredSize(new Dimension(200, 0));
						{
							btnApproveQualifiedGenerate = new JButton("Generate");
							pnlApproveQualifiedNorthEast.add(btnApproveQualifiedGenerate);
							btnApproveQualifiedGenerate.setActionCommand("Approve Qualified Generate");
							btnApproveQualifiedGenerate.addActionListener(this);
						}
					}*/
				}
				{
					pnlApproveQualifiedCenter = new JPanel(new BorderLayout(3, 3));
					pnlApproveQualified.add(pnlApproveQualifiedCenter, BorderLayout.CENTER);
					pnlApproveQualifiedCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						scrollFireForIssuancePolicy = new JScrollPane();
						pnlApproveQualifiedCenter.add(scrollFireForIssuancePolicy, BorderLayout.CENTER);
						Font fontTable = new Font(FncLookAndFeel.font_name, Font.PLAIN, 12);
						{
							modelFI_ForIssuancePolicy = new modelFire_ForIssuancePolicy();
							tblFireForIssuancePolicy = new _JTableMain(modelFI_ForIssuancePolicy);
							scrollFireForIssuancePolicy.setViewportView(tblFireForIssuancePolicy);
							scrollFireForIssuancePolicy.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblFireForIssuancePolicy.hideColumns("Rec. ID", "Unit ID", "PBL", "Seq", "Entity ID");
							
							tblFireForIssuancePolicy.addPropertyChangeListener(new PropertyChangeListener() {
								
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

										if (column != -1 && modelFI_ForIssuancePolicy.getColumnClass(column).equals(BigDecimal.class)) {
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
									computeTotalPremium();
								}
							});

						}
						{
							rowHeaderFireForIssuancePolicy = tblFireForIssuancePolicy.getRowHeader();
							rowHeaderFireForIssuancePolicy.setModel(new DefaultListModel());
							scrollFireForIssuancePolicy.setRowHeaderView(rowHeaderFireForIssuancePolicy);
							scrollFireForIssuancePolicy.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
				{
					pnlApproveQualifiedSouth = new JPanel(new BorderLayout(3, 3));
					pnlApproveQualified.add(pnlApproveQualifiedSouth, BorderLayout.SOUTH);
					pnlApproveQualifiedSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlApproveQualifiedSouthLabel = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlApproveQualifiedSouth.add(pnlApproveQualifiedSouthLabel, BorderLayout.WEST);
						{
							lblApproveQualifiedInsuranceCo = new JLabel("Insurance Co");
							pnlApproveQualifiedSouthLabel.add(lblApproveQualifiedInsuranceCo);
						}
						{
							lblApproveQualifiedInsuranceBroker = new JLabel("Insurance Broker");
							pnlApproveQualifiedSouthLabel.add(lblApproveQualifiedInsuranceBroker);
						}
						{
							lblApproveQualifiedPolicyNo = new JLabel("Policy No");
							pnlApproveQualifiedSouthLabel.add(lblApproveQualifiedPolicyNo);
						}
						{
							lblApproveQualifiedInvoiceNo = new JLabel("Invoice No");
							pnlApproveQualifiedSouthLabel.add(lblApproveQualifiedInvoiceNo);
						}
					}
					{
						pnlApproveQualifiedSouthComponent = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlApproveQualifiedSouth.add(pnlApproveQualifiedSouthComponent, BorderLayout.CENTER);
						{
							pnlApproveQualifiedInsuranceCo = new JPanel(new BorderLayout(3, 3));
							pnlApproveQualifiedSouthComponent.add(pnlApproveQualifiedInsuranceCo);
							{
								lookupApproveQualifiedInsuranceCo = new _JLookup(null, "Insurance Co", 0);
								pnlApproveQualifiedInsuranceCo.add(lookupApproveQualifiedInsuranceCo, BorderLayout.WEST);
								lookupApproveQualifiedInsuranceCo.setPreferredSize(new Dimension(100, 0));
								lookupApproveQualifiedInsuranceCo.setLookupSQL(_FireInsurance.sqlInsuranceCompany());
								lookupApproveQualifiedInsuranceCo.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null){
											String insurance_company = (String) data[1];
											txtApproveQualifiedInsuranceCo.setText(insurance_company);
										}
									}
								});
							}
							{
								txtApproveQualifiedInsuranceCo = new _JXTextField();
								pnlApproveQualifiedInsuranceCo.add(txtApproveQualifiedInsuranceCo, BorderLayout.CENTER);
							}
						}
						{
							pnlApproveQualifiedInsuranceBroker = new JPanel(new BorderLayout(3, 3));
							pnlApproveQualifiedSouthComponent.add(pnlApproveQualifiedInsuranceBroker);
							{
								lookupApproveQualifiedInsuranceBroker = new _JLookup(null, "Insurance Broker", 0);
								pnlApproveQualifiedInsuranceBroker.add(lookupApproveQualifiedInsuranceBroker, BorderLayout.WEST);
								lookupApproveQualifiedInsuranceBroker.setPreferredSize(new Dimension(100, 0));
								lookupApproveQualifiedInsuranceBroker.setLookupSQL(_FireInsurance.sqlInsuranceCompany());
								lookupApproveQualifiedInsuranceBroker.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null){
											String insurance_broker = (String) data[1];
											txtApproveQualifiedInsuranceBroker.setText(insurance_broker);
										}
									}
								});
							}
							{
								txtApproveQualifiedInsuranceBroker = new _JXTextField();
								pnlApproveQualifiedInsuranceBroker.add(txtApproveQualifiedInsuranceBroker, BorderLayout.CENTER);
							}
						}
						{
							pnlApproveQualifiedPolicyNo = new JPanel(new BorderLayout(3, 3));
							pnlApproveQualifiedSouthComponent.add(pnlApproveQualifiedPolicyNo);
							{
								txtApproveQualifiedPolicyNo = new _JXTextField();
								pnlApproveQualifiedPolicyNo.add(txtApproveQualifiedPolicyNo, BorderLayout.WEST);
								txtApproveQualifiedPolicyNo.setHorizontalAlignment(_JXTextField.CENTER);
								txtApproveQualifiedPolicyNo.setPreferredSize(new Dimension(150, 0));
							}
							
							/* COMMENTED BY MONIQUE DTD 2023-02-28; CREATION OF RPLF APPLIED ON FUNCTION 
							{
								lblApproveQualifiedRPLFNo = new JLabel("RPLF No", JLabel.TRAILING);
								pnlApproveQualifiedPolicyNo.add(lblApproveQualifiedRPLFNo);
							}
							{
								txtApproveQualifiedRPLFNo = new _JXTextField();
								pnlApproveQualifiedPolicyNo.add(txtApproveQualifiedRPLFNo, BorderLayout.EAST);
								txtApproveQualifiedRPLFNo.setHorizontalAlignment(_JXTextField.CENTER);
								txtApproveQualifiedRPLFNo.setPreferredSize(new Dimension(150, 0));
							}*/
						}
						{
							pnlApproveQualifiedInvoiceNo = new JPanel(new BorderLayout(3, 3));
							pnlApproveQualifiedSouthComponent.add(pnlApproveQualifiedInvoiceNo);
							{
								txtApproveQualifiedInvoiceNo = new _JXTextField();
								pnlApproveQualifiedInvoiceNo.add(txtApproveQualifiedInvoiceNo, BorderLayout.WEST);
								txtApproveQualifiedInvoiceNo.setHorizontalAlignment(_JXTextField.CENTER);
								txtApproveQualifiedInvoiceNo.setPreferredSize(new Dimension(150, 0));
							}
							{
								pnlApproveQualifiedCoverage = new JPanel(new GridLayout(1, 3, 3, 3));
								pnlApproveQualifiedInvoiceNo.add(pnlApproveQualifiedCoverage, BorderLayout.EAST);
								{
									lblApproveQualifiedDateFrom = new JLabel("Date From", JLabel.TRAILING);
									pnlApproveQualifiedCoverage.add(lblApproveQualifiedDateFrom);
								}
								{
									dateApproveQualifiedFrom = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlApproveQualifiedCoverage.add(dateApproveQualifiedFrom);
									dateApproveQualifiedFrom.setPreferredSize(new Dimension(150, 0));
									dateApproveQualifiedFrom.addDateListener(new DateListener() {

										@Override
										public void datePerformed(DateEvent event) {
											setDateNextYear();
										}
									});
								}
								{
									lblApproveQualifiedDateTo = new JLabel("Date To", JLabel.TRAILING);
									pnlApproveQualifiedCoverage.add(lblApproveQualifiedDateTo);
								}
								{
									dateApproveQualifiedDateTo = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlApproveQualifiedCoverage.add(dateApproveQualifiedDateTo);
									dateApproveQualifiedDateTo.setPreferredSize(new Dimension(150, 0));
								}
							}
						}
					}
				}
			}
			{
				pnlForPaymentInsuranceComp = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("For Payment to Insurance Company", pnlForPaymentInsuranceComp);
				{
					pnlApprovedFICenter = new JPanel(new BorderLayout(3, 3));
					pnlForPaymentInsuranceComp.add(pnlApprovedFICenter, BorderLayout.CENTER);
					pnlApprovedFICenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

					{
						scrollFI_ForPmtInsurance = new JScrollPane();
						pnlApprovedFICenter.add(scrollFI_ForPmtInsurance, BorderLayout.CENTER);
						//Font fontTable = new Font(FncLookAndFeel.font_name, Font.PLAIN, 12);
						{
							modelFI_ForPmtInsurance = new modelFI_ForPmtInsurance();
							tblApprovedFIRenew = new _JTableMain(modelFI_ForPmtInsurance);
							scrollFI_ForPmtInsurance.setViewportView(tblApprovedFIRenew);
							scrollFI_ForPmtInsurance.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

							/*tblApprovedFIRenew.getTableHeader().setFont(fontTable.deriveFont(1));
							tblApprovedFIRenew.setFont(fontTable);*/

							tblApprovedFIRenew.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblApprovedFIRenew.hideColumns("Check Date", "Check Amt");
							tblApprovedFIRenew.addMouseListener(new MouseAdapter() {

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

									final String invoice_no = (String) modelFI_ForPmtInsurance.getValueAt(row, column);
									final String rplf_no = (String) modelFI_ForPmtInsurance.getValueAt(row, 2); //ADDED BY MONIQUE DTD 11-30-2023; ADDTL FILTER FOR ACCNTS WITH SAME INVOICE NO BUT DIFF RPLF NO

									JPopupMenu menu = new JPopupMenu();

									JMenuItem menuViewInvoice = new JMenuItem("View Invoice No");
									menu.add(menuViewInvoice);
									menuViewInvoice.setFont(menuViewInvoice.getFont().deriveFont(10f));
									menuViewInvoice.addActionListener(new ActionListener() {

										public void actionPerformed(ActionEvent e) {
											//generateFI_ForPaymentInsurance(invoice_no);
											generateFI_ForPaymentInsurance(invoice_no, rplf_no); //MODIFIED BY MONIQUE DTD 11-30-2023; ADDTL FILTER FOR ACCNTS WITH SAME INVOICE NO BUT DIFF RPLF NO
										}
									});

									return menu;
								}
							});
						}
						{
							rowHeaderFI_ForPmtInsurance = tblApprovedFIRenew.getRowHeader();
							rowHeaderFI_ForPmtInsurance.setModel(new DefaultListModel());
							scrollFI_ForPmtInsurance.setRowHeaderView(rowHeaderFI_ForPmtInsurance);
							scrollFI_ForPmtInsurance.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
			}

			tabCenter.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent arg0) {
					int selected_tab = ((_JTabbedPane) arg0.getSource()).getSelectedIndex();

					if(selected_tab == 0){
						clearQualifiedFI();
					}
					if(selected_tab == 1){
						clearApproveQualified();
					}
					if(selected_tab == 2){
						clearApprovedFI();
						_FireInsurance.displayForPaymentInsurance(modelFI_ForPmtInsurance, rowHeaderFI_ForPmtInsurance);
						scrollFI_ForPmtInsurance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblApprovedFIRenew.getRowCount())));
						tblApprovedFIRenew.packAll();
						//btnState(sQualifyGenerate, sApproveQualified, sApprovedGenerate, sPost, sPreview, sClear);
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
			}
			{
				btnPost = new JButton("Post");
				pnlSouth.add(btnPost);
				btnPost.setActionCommand("Post");
				btnPost.addActionListener(this);
				btnPost.setMnemonic(KeyEvent.VK_P);
			}
			{
				btnPreview = new JButton("Preview");
				pnlSouth.add(btnPreview);
				btnPreview.setActionCommand("Preview");
				btnPreview.addActionListener(this);
				btnPreview.setMnemonic(KeyEvent.VK_W);
			}
			{
				btnClear = new JButton("Clear");
				pnlSouth.add(btnClear);
				btnClear.setActionCommand("Clear");
				btnClear.addActionListener(this);
				btnClear.setMnemonic(KeyEvent.VK_R);
			}
		}
		clearQualifiedFI();
		clearApproveQualified();
		//clearApprovedFI();
	}//XXX END OF INITGUI

	private void pnlState(Boolean sQualifiedFI, Boolean sApproveQualified ,Boolean sApprovedFI){
		tabCenter.setEnabledAt(0, sQualifiedFI);
		tabCenter.setEnabledAt(1, sApproveQualified);
		tabCenter.setEnabledAt(2, sApprovedFI);
		//System.out.println("Dumaan dito sa enabling ng panel");
	}

	private void btnState(Boolean sQualifyGenerate, Boolean sApproveQualified ,Boolean sApprovedGenerate, Boolean sPost, Boolean sPreview ,Boolean sClear){
		//btnQualifiedAddAccount.setEnabled(sAddQualifiedAccounts);
		//btnQualifiedGenerate.setEnabled(sQualifyGenerate);
		//btnApproveQualifiedGenerate.setEnabled(sApproveQualified);
		//btnApprovedGenerate.setEnabled(sApprovedGenerate);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
		btnClear.setEnabled(sClear);
	}

	private void clearQualifiedFI(){
		lblQualifiedCutOff.setText("Cut Off Date");
		lblQualifiedInsuranceCoUpper.setText("Insurance Co.");
		lblQualifiedProject.setText("Project Name");

		dateQualifiedCutOff.setDate(null);
		dateQualifiedCutOff.setEnabled(false);
		lookupQualifiedFIBatchNo.setValue(null);
		lookupQualifiedFIBatchNo.setEnabled(false);

		lookupQualifiedInsurance.setValue(null);
		txtQualifiedInsurance.setText("");
		lookupQualifiedInsurance.setEnabled(false);
		lookupQualifiedProject.setValue(null);
		txtQualifiedProject.setText("");
		lookupQualifiedProject.setEnabled(false);

		lblQualifiedCutOff.setText("Cut Off");
		lblQualifiedInsuranceCoUpper.setText("Insurance Co.");
		lblQualifiedProject.setText("Project Name");

		chkQualifiedBatchNo.setEnabled(false);
		chkQualifiedBatchNo.setSelected(false);

		//this.setComponentsEditable(pnlQualifiedFINorthCenter, false);

		pnlQualifiedFICenter.removeAll();
		pnlQualifiedFICenter.repaint();

		modelQFIEnroll.clear();
		rowHeaderQualifiedFIEnroll.setModel(new DefaultListModel());
		scrollQualifiedFIEnroll.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));

		modelQFIRenew.clear();
		rowHeaderQualifiedFIRenew.setModel(new DefaultListModel());
		scrollQualifiedFIRenew.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));

		modelQFITerminate.clear();
		rowHeaderQualifiedFITerminate.setModel(new DefaultListModel());
		scrollQualifiedFITerminate.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));

		pnlState(true, true ,true);
		btnState(false, false, false, false, false, false);
		//setQualifybtnState(false, false, false, false, false);
	}

	private void clearApproveQualified(){

		lblApproveQualifiedCompany.setText("Company Name");
		lblApproveQualifiedInsurance.setText("Insurance Co");
		lblApproveQualifiedBatchNo.setText("Batch No");

		lblApproveQualifiedInsuranceCo.setText("Insurance Co");
		lblApproveQualifiedInsuranceBroker.setText("Insurance Broker");
		lblApproveQualifiedPolicyNo.setText("Policy No");
		lblApproveQualifiedInvoiceNo.setText("Invoice No");

		lookupApproveQualifiedCompany.setValue(null);
		txtApproveQualifiedCompany.setText("");
		lookupApproveQualifiedCompany.setEnabled(false);

		lookupApproveQualifiedInsurance.setValue(null);
		txtApproveQualifiedInsurance.setText("");
		lookupApproveQualifiedInsurance.setEnabled(false);

		lookupApproveQualifiedBatchNo.setValue(null);
		lookupApproveQualifiedBatchNo.setEnabled(false);

		lookupApproveQualifiedInsuranceCo.setValue(null);
		txtApproveQualifiedInsuranceCo.setText("");
		lookupApproveQualifiedInsuranceCo.setEnabled(false);

		lookupApproveQualifiedInsuranceBroker.setValue(null);
		txtApproveQualifiedInsuranceBroker.setText("");
		lookupApproveQualifiedInsuranceBroker.setEnabled(false);

		//txtApproveQualifiedRPLFNo.setText("");

		txtApproveQualifiedPolicyNo.setText("");
		txtApproveQualifiedInvoiceNo.setText("");

		dateApproveQualifiedFrom.setDate(null);
		dateApproveQualifiedDateTo.setDate(null);
		/*dateApproveQualifiedFrom.getCalendarButton().setEnabled(false);
		dateApproveQualifiedDateTo.getCalendarButton().setEnabled(false);*/

		dateApproveQualifiedFrom.setEnabled(false);
		dateApproveQualifiedDateTo.setEnabled(false);

		modelFI_ForIssuancePolicy.clear();
		rowHeaderFireForIssuancePolicy.setModel(new DefaultListModel());
		scrollFireForIssuancePolicy.setCorner(JScrollPane.LOWER_LEADING_CORNER, FncTables.getRowHeader_Footer(""));

		//this.setComponentsEditable(pnlApproveQualifiedNorth, false);
		//this.setComponentsEditable(pnlApproveQualifiedSouth, false);

		pnlState(true, true, true);
		btnState(false, false, false, false, false, false);
	}

	private void clearApprovedFI(){

		modelFI_ForPmtInsurance.clear();
		rowHeaderFI_ForPmtInsurance.setModel(new DefaultListModel());
		scrollFI_ForPmtInsurance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));

		pnlState(true, true ,true);
		btnState(false, false, true, false, false, false);
	}

	private void refreshQualifiedFI(){
		clearQualifiedFI();

		if(cmbQualifiedFI.getSelectedIndex() == 0){
			pnlQualifiedFICenter.add(scrollQualifiedFIEnroll, BorderLayout.CENTER);
			//txtQualifiedFIBatchTerminationNo.setText(_FireInsurance.newQualifiedEnrollmentBatchNo()); 

			//txtQualifiedFIBatchTerminationNo.setText(_FireInsurance.newQualifiedRenewalBatchNo());
			lookupQualifiedFIBatchNo.setValue(_FireInsurance.newQualifiedEnrollmentBatchNo());
			lookupQualifiedFIBatchNo.setLookupSQL(_FireInsurance.sqlBatchNoEnrollment());

			chkQualifiedBatchNo.setEnabled(true);
			lblQualifiedProject.setText("*Project Name");
			lookupQualifiedProject.setEnabled(true);
			//lookupQualifiedProject.setEditable(true);

			btnState(true, false, false, true, true, true);
			pnlState(true, false ,false);
		}

		if(cmbQualifiedFI.getSelectedIndex() == 1){
			pnlQualifiedFICenter.add(scrollQualifiedFIRenew, BorderLayout.CENTER);

			lookupQualifiedFIBatchNo.setValue(_FireInsurance.newQualifiedRenewalBatchNo());
			lookupQualifiedFIBatchNo.setLookupSQL(_FireInsurance.sqlBatchNoRenewal());

			chkQualifiedBatchNo.setEnabled(true);

			dateQualifiedCutOff.setDate(Calendar.getInstance().getTime());

			dateQualifiedCutOff.setEnabled(true);
			lookupQualifiedInsurance.setEnabled(true);
			lookupQualifiedProject.setEnabled(true);

			/*lookupQualifiedInsurance.setEditable(true);
			lookupQualifiedProject.setEditable(true);*/

			lblQualifiedCutOff.setText("*Cut Off");
			lblQualifiedInsuranceCoUpper.setText("*Insurance Co");
			lblQualifiedProject.setText("*Project Name");

			btnState(true, false, false, true, true, true);
			pnlState(true, false ,false);

		}

		if(cmbQualifiedFI.getSelectedIndex() == 2){
			pnlQualifiedFICenter.add(scrollQualifiedFITerminate, BorderLayout.CENTER);
			chkQualifiedBatchNo.setEnabled(true);

			lookupQualifiedFIBatchNo.setValue(_FireInsurance.newQualifiedTerminationBatchNo());
			lookupQualifiedFIBatchNo.setLookupSQL(_FireInsurance.sqlBatchNoTermination());

			/*lookupQualifiedInsurance.setEditable(true);
			lookupQualifiedProject.setEditable(true);*/
			lookupQualifiedInsurance.setEnabled(true);
			lookupQualifiedProject.setEnabled(true);
			//txtQualifiedFIBatchTerminationNo.setText(_FireInsurance.newTerminationBatchNo());
			
			dateQualifiedCutOff.setDate(Calendar.getInstance().getTime());

			dateQualifiedCutOff.setEnabled(true);

			lblQualifiedInsuranceCoUpper.setText("*Insurance Co");
			lblQualifiedProject.setText("*Project Name");

			//btnState(true, false, true, true ,true);
			btnState(true, false, false, true, true, true);
			pnlState(true, false ,false);
		}
	}

	private void refreshApproveQualified(){
		clearApproveQualified();

		if(cmbApproveQualified.getSelectedIndex() == 0){

			lblApproveQualifiedCompany.setText("*Company Name");
			lblApproveQualifiedInsurance.setText("*Insurance Co");
			lblApproveQualifiedBatchNo.setText("*Batch No");
			lblApproveQualifiedInsuranceCo.setText("*Insurance Co");
			lblApproveQualifiedInsuranceBroker.setText("*Insurance Broker");

			lblApproveQualifiedPolicyNo.setText("*Policy No");
			lblApproveQualifiedInvoiceNo.setText("*Invoice No");

			lookupApproveQualifiedBatchNo.setLookupSQL(_FireInsurance.sqlBatchNoEnrollment());

			lookupApproveQualifiedCompany.setEnabled(true);
			lookupApproveQualifiedInsurance.setEnabled(true);
			lookupApproveQualifiedBatchNo.setEnabled(true);

			lookupApproveQualifiedInsuranceCo.setEnabled(true);
			lookupApproveQualifiedInsuranceBroker.setEnabled(true);

			txtApproveQualifiedPolicyNo.setEditable(true);
			txtApproveQualifiedInvoiceNo.setEditable(true);

			dateApproveQualifiedFrom.setEnabled(true);
			dateApproveQualifiedDateTo.setEnabled(true);

			/*dateApproveQualifiedFrom.setEditable(true);
			dateApproveQualifiedFrom.getCalendarButton().setEnabled(true);

			dateApproveQualifiedDateTo.setEditable(true);
			dateApproveQualifiedDateTo.getCalendarButton().setEnabled(true);*/

			dateApproveQualifiedFrom.setDate(Calendar.getInstance().getTime());
			setDateNextYear();

			pnlState(false, true, false);
			btnState(false, true, false, true, true, true);
		}

		if(cmbApproveQualified.getSelectedIndex() == 1){

			lblApproveQualifiedCompany.setText("*Company Name");
			lblApproveQualifiedInsurance.setText("*Insurance Co");
			lblApproveQualifiedBatchNo.setText("*Batch No");

			lblApproveQualifiedPolicyNo.setText("*Policy No");
			lblApproveQualifiedInvoiceNo.setText("*Invoice No");

			lookupApproveQualifiedBatchNo.setLookupSQL(_FireInsurance.sqlBatchNoRenewal());

			lookupApproveQualifiedCompany.setEnabled(true);
			lookupApproveQualifiedInsurance.setEnabled(true);
			lookupApproveQualifiedBatchNo.setEnabled(true);

			txtApproveQualifiedPolicyNo.setEditable(true);
			txtApproveQualifiedInvoiceNo.setEditable(true);

			dateApproveQualifiedFrom.setEnabled(true);
			dateApproveQualifiedDateTo.setEnabled(true);

			/*dateApproveQualifiedFrom.setEditable(true);
			dateApproveQualifiedFrom.getCalendarButton().setEnabled(true);
			dateApproveQualifiedDateTo.setEditable(true);
			dateApproveQualifiedDateTo.getCalendarButton().setEnabled(true);*/

			dateApproveQualifiedFrom.setDate(Calendar.getInstance().getTime());
			setDateNextYear();

			pnlState(false, true, false);
			btnState(false, true, false, true, true, true);

		}
	}

	private void refreshApprovedFI(){
		clearApprovedFI();

		if(cmbApprovedFI.getSelectedIndex() == 0){
			pnlApprovedFICenter.add(scrollApproveQualifiedEnroll, BorderLayout.CENTER);

			lookupApprovedCompany.setEditable(true);
			//chkApprovedFIBatchNo.setEnabled(true);
			lookupApprovedProject.setEditable(true);
			lookupApprovedProject.setLookupSQL(_FireInsurance.sqlBatchNoEnrollment());
			lookupApprovedFIInsuranceLower.setEditable(true);
			lookupApprovedFIInsuranceBrokerLower.setEditable(true);
			/*dateApprovedFI.setDate(Calendar.getInstance().getTime());
			dateApprovedFI.setEnabled(true);*/
			dteFrom.setDate(Calendar.getInstance().getTime());
			setDateNextYear();
			dteFrom.getCalendarButton().setEnabled(true);
			dteFrom.setEditable(true);
			dteTo.getCalendarButton().setEnabled(true);
			dteTo.setEditable(true);

			txtApprovedFIInvoiceNoLower.setEditable(true);
			txtApprovedFIPolicyNo.setEditable(true);

			lblApprovedCompany.setText("*Company Name");
			lblApprovedFIBatchNo.setText("*Batch No");
			//lblApprovedFIInsuranceUpper.setText("*Insurance Co.");
			lblApprovedFIInsuranceLower.setText("*Insurance Co");
			lblApprovedFIInsuranceBrokerLower.setText("*Insurance Broker");
			lblApprovedFIPolicyNo.setText("*Policy No");
			lblApprovedFIInvoiceNoLower.setText("*Invoice No");

			//btnState(false, true, true, true ,true);
			pnlState(false, false ,true);
		}

		if(cmbApprovedFI.getSelectedIndex() == 1){
			pnlApprovedFICenter.add(scrollFI_ForPmtInsurance, BorderLayout.CENTER);

			//lookupApprovedFICompanyUpper.setEditable(true);
			lookupApprovedPreview.setEditable(true);
			lookupApprovedProject.setEditable(true);
			lookupApprovedProject.setLookupSQL(_FireInsurance.sqlBatchNoRenewal());
			lookupApprovedFIInsuranceUpper.setEditable(true);

			//btnState(false, true, true, true ,true);

			pnlState(false, false ,true);
		}
		
		if(cmbApprovedFI.getSelectedIndex() == 2){
			pnlApprovedFICenter.add(scrollFireForIssuancePolicy, BorderLayout.CENTER);

			lookupApprovedCompany.setEditable(true);
			lookupApprovedFIInsuranceUpper.setEditable(true);
			lookupApprovedFIInsuranceLower.setEditable(true);
			lookupApprovedFIInsuranceBrokerLower.setEditable(true);
			/*dateApprovedFI.setDate(Calendar.getInstance().getTime());
			dateApprovedFI.setEnabled(true);*/

			//txtApprovedFIBatchNo.setText(_FireInsurance.newQualifiedRenewalBatchNo());
			lookupApprovedProject.setValue(_FireInsurance.newQualifiedRenewalBatchNo());

			txtApprovedFIInvoiceNoLower.setEditable(true);
			txtApprovedFIPolicyNo.setEditable(true);

			//setApprovedbtnState(true, true, true);
			//btnState(false, true, true, false ,true);
			pnlState(false, false ,true);
		}
	}

	private void setDateNextYear(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 12);

		dateApproveQualifiedDateTo.setDate(cal.getTime());
		//dateQualifiedTo.setDate(cal.getTime());
	}
	
	private void computeTotalPremium(){
		
		if(tblFireForIssuancePolicy.getSelectedRows().length == 1){
			int selected_row = tblFireForIssuancePolicy.convertRowIndexToModel(tblFireForIssuancePolicy.getSelectedRow());
			
			BigDecimal sub_total_premium = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(selected_row, 18);
			BigDecimal doc_stamps = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(selected_row, 19);
			BigDecimal EVAT = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(selected_row, 20);
			BigDecimal FST = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(selected_row, 21);
			BigDecimal LGT = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(selected_row, 22);
			BigDecimal total_premium = sub_total_premium.add(doc_stamps).add(EVAT).add(FST).add(LGT);
			
			modelFI_ForIssuancePolicy.setValueAt(total_premium, selected_row, 23);
			
		}
	}

	//CHECKS FOR THE REQUIRED FIELDS BEFORE SAVING
	private boolean toSave(){
		if(tabCenter.getSelectedIndex() == 0){

			if(cmbQualifiedFI.getSelectedIndex() == 0){
				if(lookupQualifiedProject.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(_FireInsurance.getSelectedAccountsEnrollment(modelQFIEnroll) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}

			if(cmbQualifiedFI.getSelectedIndex() == 1){
				if(lookupQualifiedInsurance.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Co", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(lookupQualifiedProject.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(_FireInsurance.getSelectedAccountsRenewal(modelQFIRenew) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}

			if(cmbQualifiedFI.getSelectedIndex() == 2){//For Temination
				if(lookupQualifiedInsurance.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Co", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(lookupQualifiedProject.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(_FireInsurance.getSelectedAccountsTermination(modelQFITerminate) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		if(tabCenter.getSelectedIndex() == 1){
			if(cmbApproveQualified.getSelectedIndex() == 0){//For Enrollment
				if(lookupApproveQualifiedCompany.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Company", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(lookupApproveQualifiedInsurance.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Co", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(lookupApproveQualifiedBatchNo.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Batch No", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(lookupApproveQualifiedInsuranceCo.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Co", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(lookupApproveQualifiedInsuranceBroker.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Broker", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(txtApproveQualifiedPolicyNo.getText().isEmpty()){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Policy No", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(txtApproveQualifiedInvoiceNo.getText().isEmpty()){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Invoice No", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(_FireInsurance.getSelectedAccountsApproval(modelFI_ForIssuancePolicy) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
			if(cmbApproveQualified.getSelectedIndex() == 1){//For Enrollment
				if(lookupApproveQualifiedCompany.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Company", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(lookupApproveQualifiedInsurance.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Co", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(lookupApproveQualifiedBatchNo.getValue() == null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Batch No", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(txtApproveQualifiedPolicyNo.getText().isEmpty()){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Policy No", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(txtApproveQualifiedInvoiceNo.getText().isEmpty()){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Invoice No", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(_FireInsurance.getSelectedAccountsApproval(modelFI_ForIssuancePolicy) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}

		return true;
	}

	private void generateFI_Qualified_Enrollment(Boolean preview_batch_no, String batch_no){

		ArrayList<TD_FI_Qualified_Enroll> listFI_Qualified_Enroll = new ArrayList<TD_FI_Qualified_Enroll>();

		for(int x= 0; x<modelQFIEnroll.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelQFIEnroll.getValueAt(x, 0);

			if(isSelected){

				String proj_alias = (String) modelQFIEnroll.getValueAt(x, 1);
				String phase = (String) modelQFIEnroll.getValueAt(x, 5);
				String block = (String) modelQFIEnroll.getValueAt(x, 6);
				String lot = (String) modelQFIEnroll.getValueAt(x, 7);
				String client_name = (String) modelQFIEnroll.getValueAt(x, 9);
				String house_model = (String) modelQFIEnroll.getValueAt(x, 11);
				BigDecimal model_cost = (BigDecimal) modelQFIEnroll.getValueAt(x, 12);
				String percent_constructed = (String) modelQFIEnroll.getValueAt(x, 13);
				String term = (String) modelQFIEnroll.getValueAt(x, 14);
				
				listFI_Qualified_Enroll.add(new TD_FI_Qualified_Enroll(proj_alias, phase, block, lot, client_name, house_model, model_cost, percent_constructed, term));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("preview_batch", preview_batch_no);
		mapParameters.put("batch_no", batch_no);
		mapParameters.put("listFI_Qualified_Enroll", listFI_Qualified_Enroll);
		
		FncReport.generateReport("/Reports/rptFIQualifiedEnroll.jasper", "Qualified Accounts for Enrollment", mapParameters);
	}

	//GENERATION OF QUALIFIED ACCOUNTS FOR ENROLLMENT
	/*private void generateFI_QualifiedAccounts_Enrollment_Post(){

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		//mapParameters.put("proj_name", txtQualifiedProject.getText());
		mapParameters.put("qualified_enrollment", true);
		mapParameters.put("enrollment_batch_no", lookupQualifiedFIBatchNo.getValue());

		//mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptQualifiedFIEnroll.jasper"));

		FncReport.generateReport("/Reports/rptFIQualifiedEnroll.jasper", "Qualified Accounts for Enrollment", mapParameters);
		//FncReport.generateReport(/, batch_no, mapParameters);
	}*/

	private void generateFI_QualifiedAccounts_Renewal(Boolean preview_batch, String batch_no){
		ArrayList<TD_FI_Qualified_Renew> listFI_Qualified_Renew = new ArrayList<TD_FI_Qualified_Renew>();
		
		for(int x= 0; x<modelQFIRenew.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelQFIRenew.getValueAt(x, 0);
			if(isSelected){
				
				String proj_alias = (String) modelQFIRenew.getValueAt(x, 2);
				String phase =  (String) modelQFIRenew.getValueAt(x, 4);
				String block = (String) modelQFIRenew.getValueAt(x, 5);
				String lot = (String) modelQFIRenew.getValueAt(x, 6);
				String client_name = (String) modelQFIRenew.getValueAt(x, 10);
				String house_model = (String) modelQFIRenew.getValueAt(x, 11);
				BigDecimal model_cost = (BigDecimal) modelQFIRenew.getValueAt(x, 12);
				String percent_constructed = (String) modelQFIRenew.getValueAt(x, 13);
				String policy_no = (String) modelQFIRenew.getValueAt(x, 20);
				Date date_from = (Date) modelQFIRenew.getValueAt(x, 21);
				Date date_to = (Date) modelQFIRenew.getValueAt(x, 22);
				
				listFI_Qualified_Renew.add(new TD_FI_Qualified_Renew(proj_alias, phase, block, lot, client_name, house_model, model_cost, percent_constructed, policy_no, date_from, date_to));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		/*mapParameters.put("filter_invoice_no", true);
		mapParameters.put("invoice_no", txtApprovedFIInvoiceNoLower.getText());*/
		
		mapParameters.put("preview_batch", preview_batch);
		mapParameters.put("batch_no", batch_no);
		mapParameters.put("listFI_Qualified_Renew", listFI_Qualified_Renew);

		FncReport.generateReport("/Reports/rptFIQualifiedRenew.jasper", "Fire Insurance Renew Accounts" , mapParameters);
		
	}
	
	private void generateFI_QualifiedAccounts_Termination(Boolean preview_batch, String batch_no){
		ArrayList<TD_FI_Qualified_Terminate> listFI_Qualified_Terminate = new ArrayList<TD_FI_Qualified_Terminate>();

		for(int x = 0; x<modelQFITerminate.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelQFITerminate.getValueAt(x, 0);

			if(isSelected){

				String proj_alias = (String) modelQFITerminate.getValueAt(x, 6);
				String phase = (String) modelQFITerminate.getValueAt(x, 7);
				String block = (String) modelQFITerminate.getValueAt(x, 8);
				String lot = (String) modelQFITerminate.getValueAt(x, 9);
				String client_name = (String) modelQFITerminate.getValueAt(x, 11);
				BigDecimal house_cost = (BigDecimal) modelQFITerminate.getValueAt(x, 12);
				String insurance_term = (String) modelQFITerminate.getValueAt(x, 13);
				Date effectivity_date = (Date) modelQFITerminate.getValueAt(x, 14);
				String policy_no = (String) modelQFITerminate.getValueAt(x, 15);
				Date date_from = (Date) modelQFITerminate.getValueAt(x, 16);
				Date date_to = (Date) modelQFITerminate.getValueAt(x, 17);
				String reason = (String) modelQFITerminate.getValueAt(x, 18);

				listFI_Qualified_Terminate.add(new TD_FI_Qualified_Terminate(proj_alias, phase, block, lot, client_name, house_cost, insurance_term, effectivity_date, policy_no, date_from, date_to, reason));
			}
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("listFI_Qualified_Terminate", listFI_Qualified_Terminate);
		mapParameters.put("preview_batch", preview_batch);
		mapParameters.put("batch_no", batch_no);

		FncReport.generateReport("/Reports/rptFIQualifiedTerminate.jasper", "Qualified Accounts for Termination", mapParameters);
	}
	
	//XXX REMOVE
	/*private void generateFI_QualifiedAccounts_Renewal_Posted(){
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("renewal_batch_no", lookupQualifiedFIBatchNo.getValue());
		
		FncReport.generateReport("", "Fire Insurance Renewed Accounts", mapParameters);
		
	}*/
	
	private void generateFI_Issuance_Policy(){
		ArrayList<TD_Fire_Issuance_Policy> listFI_ForIssuancePolicy = new ArrayList<TD_Fire_Issuance_Policy>();
		Date date_from = dateApproveQualifiedFrom.getDate();
		Date date_to = dateApproveQualifiedDateTo.getDate();

		for(int x = 0; x<modelFI_ForIssuancePolicy.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelFI_ForIssuancePolicy.getValueAt(x, 0);

			if(isSelected){
					
				String proj_alias = (String) modelFI_ForIssuancePolicy.getValueAt(x, 2);
				String unit_desc = (String) modelFI_ForIssuancePolicy.getValueAt(x, 6);
				String client_name = (String) modelFI_ForIssuancePolicy.getValueAt(x, 8);
				String house_model = (String) modelFI_ForIssuancePolicy.getValueAt(x, 9);
				String pmt_stage = (String) modelFI_ForIssuancePolicy.getValueAt(x, 10);
				BigDecimal amt_insured = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 11);
				BigDecimal fire_lightning = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 12);
				BigDecimal full_earthquake = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 13);
				BigDecimal typhoon = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 14);
				BigDecimal flood = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 15);
				BigDecimal ext_cover = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 16);
				BigDecimal rsmd = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 17);
				BigDecimal premium_sub_total = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 18);
				BigDecimal doc_stamps = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 19);
				BigDecimal evat = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 20);
				BigDecimal fst = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 21);
				BigDecimal lgt = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 22);
				BigDecimal premium = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 23);

				listFI_ForIssuancePolicy.add(new TD_Fire_Issuance_Policy(proj_alias,txtApproveQualifiedPolicyNo.getText(), lookupApproveQualifiedBatchNo.getValue(), unit_desc ,client_name, house_model, 
						pmt_stage, amt_insured, date_from, date_to ,fire_lightning, full_earthquake, typhoon, flood, ext_cover, rsmd, premium_sub_total, doc_stamps, evat, fst, lgt, premium));

			}
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company_name", txtApproveQualifiedCompany.getText());
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("user_alias", UserInfo.Alias);
		
		mapParameters.put("listFI_ForIssuancePolicy", listFI_ForIssuancePolicy);

		FncReport.generateReport("/Reports/rptFI_ForIssuancePolicy.jasper", "Qualified Accounts for Renewal", mapParameters);
	}
	
	private void generateFI_Issuance_Policy_Batch(String batch_no){
		ArrayList<TD_Fire_Issuance_Policy> listFI_ForIssuancePolicy = new ArrayList<TD_Fire_Issuance_Policy>();
		Date date_from = dateApproveQualifiedFrom.getDate();
		Date date_to = dateApproveQualifiedDateTo.getDate();

		for(int x = 0; x<modelFI_ForIssuancePolicy.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelFI_ForIssuancePolicy.getValueAt(x, 0);

			if(isSelected){
								
				String proj_alias = (String) modelFI_ForIssuancePolicy.getValueAt(x, 2);
				String unit_desc = (String) modelFI_ForIssuancePolicy.getValueAt(x, 6);
				String client_name = (String) modelFI_ForIssuancePolicy.getValueAt(x, 8);
				String house_model = (String) modelFI_ForIssuancePolicy.getValueAt(x, 9);
				String pmt_stage = (String) modelFI_ForIssuancePolicy.getValueAt(x, 10);
				BigDecimal amt_insured = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 11);
				BigDecimal fire_lightning = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 12);
				BigDecimal full_earthquake = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 13);
				BigDecimal typhoon = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 14);
				BigDecimal flood = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 15);
				BigDecimal ext_cover = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 16);
				BigDecimal rsmd = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 17);
				BigDecimal premium_sub_total = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 18);
				BigDecimal doc_stamps = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 19);
				BigDecimal evat = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 20);
				BigDecimal fst = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 21);
				BigDecimal lgt = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 22);
				BigDecimal premium = (BigDecimal) modelFI_ForIssuancePolicy.getValueAt(x, 23);

				listFI_ForIssuancePolicy.add(new TD_Fire_Issuance_Policy(proj_alias,txtApproveQualifiedPolicyNo.getText(), lookupApproveQualifiedBatchNo.getValue(), unit_desc ,client_name, house_model, 
						pmt_stage, amt_insured, date_from, date_to ,fire_lightning, full_earthquake, typhoon, flood, ext_cover, rsmd, premium_sub_total, doc_stamps, evat, fst, lgt, premium));

			}
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company_name", txtApproveQualifiedCompany.getText());
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("user_alias", UserInfo.Alias);
		mapParameters.put("listFI_ForIssuancePolicy", listFI_ForIssuancePolicy);

		FncReport.generateReport("/Reports/rptFI_ForIssuancePolicy.jasper", "Qualified Accounts for Renewal", mapParameters);
	}


	

	//Generation of Terminated Accounts Based on Termination Batch No
	private void generateFI_QualifiedAccounts_Termination(){

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		//mapParameters.put("insurance_co", txtQualifiedInsurance.getText());
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("proj_name", txtQualifiedProject.getText());
		mapParameters.put("batch_no_termination", lookupQualifiedFIBatchNo.getValue());
		//System.out.printf("Display batch no: %s%n", txtQualifiedFIBatchTerminationNo.getText());

		FncReport.generateReport("/Reports/rptFIQualifiedTerminate.jasper", "Qualified Accounts for Termination", mapParameters);
	}

	//GENERATES REPORT OF ACCOUNTS FOR PAYMENT TO INSURANCE COMPANY
	private void generateFI_ForPaymentInsurance(String invoice_no, String rplf_no){
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		//System.out.printf("Display rplf no: %s%n", rplf_no);
		mapParameters.put("invoice_no", invoice_no);
		mapParameters.put("rplf_no", rplf_no); //ADDED BY MONIQUE DTD 11-30-2023; ADDTL FILTER FOR ACCNTS WITH SAME INVOICE NO BUT DIFF RPLF NO
		mapParameters.put("prepared_by", UserInfo.FullName);

		FncReport.generateReport("/Reports/rptFI_ForPmtInsurance.jasper", "Fire Insurance For Payment to Insurance Company", mapParameters);
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		int selected_tab = tabCenter.getSelectedIndex();
		int qualified_selected = cmbQualifiedFI.getSelectedIndex();
		int approve_qualified_selected = cmbApproveQualified.getSelectedIndex();
		/*int approved_selected = cmbApprovedFI.getSelectedIndex();*/

		if(actionCommand.equals("Qualified FI Generate")){
			if(qualified_selected == 0){
				if(chkQualifiedBatchNo.isSelected() == false){
					_FireInsurance.displayQualifiedFIEnroll(lookupQualifiedProject.getValue(),modelQFIEnroll, rowHeaderQualifiedFIEnroll);
					tblQualifiedFIEnroll.packAll();
					scrollQualifiedFIEnroll.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFIEnroll.getRowCount())));
					tblQualifiedFIEnroll.setEditable(true);
				}else{
					_FireInsurance.displayEnrolledAccountsBatchNo(lookupQualifiedFIBatchNo.getValue(), modelQFIEnroll, rowHeaderQualifiedFIEnroll);
					tblQualifiedFIEnroll.packAll();
					scrollQualifiedFIEnroll.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFIEnroll.getRowCount())));
					tblQualifiedFIEnroll.setEditable(false);
				}
			}
			
			if(qualified_selected == 1){
				if(chkQualifiedBatchNo.isSelected() == false){
					_FireInsurance.displayQualifiedFIRenew(dateQualifiedCutOff.getDate() ,lookupQualifiedInsurance.getValue(), lookupQualifiedProject.getValue(), modelQFIRenew, rowHeaderQualifiedFIRenew);
					tblQualifiedFIRenew.packAll();
					scrollQualifiedFIRenew.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFIRenew.getRowCount())));
					tblQualifiedFIRenew.setEditable(true);
				}else{
					_FireInsurance.displayRenewedAccountsBatchNo(lookupQualifiedFIBatchNo.getValue(), modelQFIRenew, rowHeaderQualifiedFIRenew);
					tblQualifiedFIRenew.packAll();
					scrollQualifiedFIRenew.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFIRenew.getRowCount())));
					tblQualifiedFIRenew.setEditable(false);
				}
			}
			if(qualified_selected == 2){
				if(chkQualifiedBatchNo.isSelected() == false){
					_FireInsurance.displayQualifiedFITerminate(lookupQualifiedInsurance.getValue(), lookupQualifiedProject.getValue(), dateQualifiedCutOff.getDate() ,modelQFITerminate, rowHeaderQualifiedFITerminate);
					tblQualifiedFITerminate.packAll();
					scrollQualifiedFITerminate.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFITerminate.getRowCount())));
					tblQualifiedFITerminate.setEditable(true);
				}else{
					_FireInsurance.displayTerminatedAccountsBatchNo(lookupQualifiedFIBatchNo.getValue(), modelQFITerminate, rowHeaderQualifiedFITerminate);
					tblQualifiedFITerminate.packAll();
					scrollQualifiedFITerminate.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedFITerminate.getRowCount())));
					tblQualifiedFITerminate.setEditable(false);
				}
			}
		}

		if(actionCommand.equals("Approve Qualified Generate")){
			if(approve_qualified_selected == 0){
				_FireInsurance.displayQualifiedFIEnrollment(lookupApproveQualifiedCompany.getValue(), lookupApproveQualifiedBatchNo.getValue(), lookupApproveQualifiedInsurance.getValue(), modelFI_ForIssuancePolicy, rowHeaderFireForIssuancePolicy);
				tblFireForIssuancePolicy.packAll();
				scrollFireForIssuancePolicy.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblFireForIssuancePolicy.getRowCount())));
			}
			if(approve_qualified_selected == 1){
				_FireInsurance.displayQualifiedFIRenewal(lookupApproveQualifiedCompany.getValue(), lookupApproveQualifiedBatchNo.getValue(), lookupApproveQualifiedInsurance.getValue(), modelFI_ForIssuancePolicy, rowHeaderFireForIssuancePolicy);
				tblFireForIssuancePolicy.packAll();
				scrollFireForIssuancePolicy.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblFireForIssuancePolicy.getRowCount())));
			}
		}

		if(actionCommand.equals("Approved FI Generate")){
			_FireInsurance.displayEnrolledAccounts(cmbApprovedPreviewBy.getSelectedIndex() ,lookupApprovedPreview.getValue(), modelFI_ForPmtInsurance, rowHeaderFI_ForPmtInsurance);
			scrollFI_ForPmtInsurance.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblApprovedFIRenew.getRowCount())));
			tblApprovedFIRenew.packAll();
		}

		if(actionCommand.equals("Post")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to post?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					if(selected_tab == 0){
						
						if(qualified_selected == 0){//Enrollment
							_FireInsurance.postQualify_Enrollment(lookupQualifiedProject.getValue(), lookupQualifiedFIBatchNo.getValue(), modelQFIEnroll);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected Account(s) Enrolled \nBatch No: %s%n", lookupQualifiedFIBatchNo.getValue()), actionCommand, JOptionPane.INFORMATION_MESSAGE);
							generateFI_Qualified_Enrollment(true, lookupQualifiedFIBatchNo.getValue());
							//generateFI_QualifiedAccounts_Enrollment_Post();
							cmbQualifiedFI.setSelectedItem(null);
							refreshQualifiedFI();
						}

						if(qualified_selected == 1){//Renewal

							_FireInsurance.postQualify_Renewal(lookupQualifiedFIBatchNo.getValue(), lookupQualifiedInsurance.getValue(), lookupQualifiedProject.getValue(), modelQFIRenew);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Select Account(s) Posted\nBatch No:%s%n", lookupQualifiedFIBatchNo.getValue()), actionCommand, JOptionPane.INFORMATION_MESSAGE);
							generateFI_QualifiedAccounts_Renewal(true, lookupQualifiedFIBatchNo.getValue());
							cmbQualifiedFI.setSelectedItem(null);
							refreshQualifiedFI();
						}

						if(qualified_selected == 2){//Termination
							_FireInsurance.postQualifiedFITerminate(lookupQualifiedFIBatchNo.getValue(), modelQFITerminate);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected Account(s) Terminated\nTermination Batch: %s%n", lookupQualifiedFIBatchNo.getValue()), actionCommand, JOptionPane.INFORMATION_MESSAGE);

							generateFI_QualifiedAccounts_Termination(true, lookupQualifiedFIBatchNo.getValue());
							cmbQualifiedFI.setSelectedItem(null);
							refreshQualifiedFI();
						}
					}
					if(selected_tab == 1){
						if(approve_qualified_selected == 0){//Enrollment
							_FireInsurance.postQualifiedEnrollment(lookupApproveQualifiedCompany.getValue(), lookupApproveQualifiedInsuranceCo.getValue(), 
									lookupApproveQualifiedInsuranceBroker.getValue(), /*txtApproveQualifiedRPLFNo.getText(),*/ txtApproveQualifiedPolicyNo.getText(), 
									txtApproveQualifiedInvoiceNo.getText(), dateApproveQualifiedFrom.getDate(), dateApproveQualifiedDateTo.getDate(), modelFI_ForIssuancePolicy);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected Account(s) Posted!" /*\nRPLF No:%s%n", txtApproveQualifiedRPLFNo.getText()*/), "Save", JOptionPane.INFORMATION_MESSAGE);
							cmbApproveQualified.setSelectedItem(null);
							refreshApproveQualified();

						}
						if(approve_qualified_selected == 1){//Renewal
							_FireInsurance.postQualifiedFIRenewal(lookupApproveQualifiedCompany.getValue(), lookupApproveQualifiedBatchNo.getValue(), 
									lookupApproveQualifiedInsurance.getValue(), /*txtApproveQualifiedRPLFNo.getText(),*/ txtApproveQualifiedPolicyNo.getText(), 
									txtApproveQualifiedInvoiceNo.getText(), dateApproveQualifiedFrom.getDate(), dateApproveQualifiedDateTo.getDate(), modelFI_ForIssuancePolicy);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Selected Account(s) Posted!" /*\nRPLF No:%s%n", txtApproveQualifiedRPLFNo.getText()*/), "Save", JOptionPane.INFORMATION_MESSAGE);
							cmbApproveQualified.setSelectedItem(null);
							refreshApproveQualified();
							//generateFI_Issuance_Policy(); //ADDED BY MONIQUE DTD 03-20-2023; REPORT PREVIEW OF POSTED ACCOUNTS; UNCOMMENT IF REQUESTED BY THE USER
						}
					}
				}
			}
		}

		if(actionCommand.equals("Preview")){

			if(selected_tab == 0){
				if(qualified_selected == 0){
					if(chkQualifiedBatchNo.isSelected() == false){
						generateFI_Qualified_Enrollment(false, null);
					}else{
						generateFI_Qualified_Enrollment(chkQualifiedBatchNo.isSelected(), lookupQualifiedFIBatchNo.getValue());
					}
				}

				if(qualified_selected == 1){

					if(chkQualifiedBatchNo.isSelected() == false){
						generateFI_QualifiedAccounts_Renewal(false, null);
					}else{
						generateFI_QualifiedAccounts_Renewal(chkQualifiedBatchNo.isSelected(), lookupQualifiedFIBatchNo.getValue());
					}
				}

				if(qualified_selected == 2){
					if(chkQualifiedBatchNo.isSelected() == false){
						generateFI_QualifiedAccounts_Termination(false, null);
					}else{
						generateFI_QualifiedAccounts_Termination(chkQualifiedBatchNo.isSelected(), lookupQualifiedFIBatchNo.getValue());
					}
				}
			}
			
			//For Issuance of Policy
			if(selected_tab == 1){
			
				if(approve_qualified_selected == 0){
					if(chkQualifiedBatchNo.isSelected() == false){
						generateFI_Qualified_Enrollment(false, null);
					}else{
						generateFI_Qualified_Enrollment(chkQualifiedBatchNo.isSelected(), lookupQualifiedFIBatchNo.getValue());
					}
				}

				if(approve_qualified_selected == 1){
				
					if(lookupApproveQualifiedBatchNo != null ){
						generateFI_Issuance_Policy_Batch(lookupApproveQualifiedBatchNo.getValue().toString());
					}
				}
				//generateFI_Issuance_Policy(); --COMMENTED BY MONIQUE DTD 03-20-2023
			}
			
			
		}

		if(actionCommand.equals("Clear")){
			if(selected_tab == 0){
				cmbQualifiedFI.setSelectedItem(null);
				clearQualifiedFI();
			}
			
			if(selected_tab == 1){
				cmbApproveQualified.setSelectedItem(null);
				clearApproveQualified();
			}
			
			if(selected_tab == 2){
				//cmbApprovedFI.setSelectedItem(null);
				clearApprovedFI();
			}
		}

	}
}
