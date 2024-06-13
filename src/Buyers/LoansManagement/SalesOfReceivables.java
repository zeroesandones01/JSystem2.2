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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
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
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import DateChooser._JDateChooser;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import tablemodel.modelSORApprovedAccounts;
import tablemodel.modelSORQualifiedAccounts;
import tablemodel.modelSORReturnedAccounts;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class SalesOfReceivables extends _JInternalFrame implements _GUI,
ActionListener {

	private static final long serialVersionUID = 4710172250487804596L;

	private static String title = "Sales of Receivables (SOR)";
	static Dimension SIZE = new Dimension(650, 550);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTabbedPane tabSORCenter;

	private JPanel pnlSORQualifiedAccounts;
	private JPanel pnlQANorth;
	private JPanel pnlQANorthCenter;

	private JPanel pnlQANorthLabel;
	private JLabel lblQACompany;
	private JLabel lblQAProject;
	private JLabel lblQAPhase;
	private JLabel lblQABank;
	private JLabel lblQAMADate;

	private JPanel pnlQANorthComponent;

	private JPanel pnlQACompany;
	private _JLookup lookupQACompany;
	private _JXTextField txtQACompany;

	private JPanel pnlQAProject;
	private _JLookup lookupQAProject;
	private _JXTextField txtQAProject;

	private JPanel pnlQAPhase;
	private _JLookup lookupQAPhase;
	private _JXTextField txtQAPhase;

	private JPanel pnlQABank;
	private _JLookup lookupQABank;
	private _JXTextField txtQABank;

	private JPanel pnlQAMADate;
	private _JDateChooser dateQAMADate;
	private JLabel lblQACutOff;
	private _JDateChooser dateQACutOff;

	private JButton btnQAQualify;

	private JPanel pnlQACenter;
	private JScrollPane scrollQualifiedAcct;
	private _JTableMain tblQualifiedAcct;
	private JList rowHeaderQualifiedAcct;
	private modelSORQualifiedAccounts modelSORQA;

	private JPanel pnlSORApprovedAccounts;
	private JPanel pnlAANorth;
	private JPanel pnlAANorthWest;
	private JPanel pnlAANorthWestLabel;
	private JLabel lblAASearchBy;
	private JPanel pnlAANorthWestComponent;
	private JComboBox cmbAASearchBy;
	private _JLookup lookupAASearchBy;
	private _JXTextField txtAASearchBy;

	private JPanel pnlAANorthEast;

	private JPanel pnlAANELabel;
	private JLabel lblAACreditDate;
	private JLabel lblAAPNNo;

	private JPanel pnlAANEComponent;

	private JPanel pnlAACreditDate;
	private _JDateChooser dateAACreditDate;
	private JLabel lblAABankBatch;
	private _JXTextField txtAABankBatch;

	private JPanel pnlAAPNNo;
	private _JXTextField txtAAPNNo;
	private JLabel lblAAORNo;
	private _JXTextField txtAAORNo;

	/*private JPanel pnlAANorthLabel;
	private JPanel pblAANorthComponent;*/

	private JPanel pnlAACenter;
	private JScrollPane scrollApprovedAcct;
	private _JTableMain tblApprovedAcct;
	private JList rowHeaderApprovedAcct;
	private modelSORApprovedAccounts modelSORAA;

	private JPanel pnlSORReturnedAccounts;
	private JPanel pnlRANorth;
	private JPanel pnlRANorthWest;

	private JPanel pnlRANorthWestLabel;
	private JPanel pnlRANorthWestComponent;
	private JComboBox cmbRASearchBy;
	private _JLookup lookupRASearchBy;
	private _JXTextField txtRASearchBy;

	private JPanel pnlRANorthEast;
	private JPanel pnlRANELabel;
	private JLabel lblRAReturnDate;
	private JPanel pnlRANEComponent;
	private _JDateChooser dateRAReturnDate;

	private JPanel pnlRACenter;
	private JScrollPane scrollReturnedAcct;
	private _JTableMain tblReturnedAcct;
	private JList rowHeaderReturnedAcct;
	private modelSORReturnedAccounts modelSORRA;

	private JPanel pnlSORSouth;
	private JButton btnSORPost;
	private JButton btnSORPreview;
	private JButton btnSORCancel;

	public SalesOfReceivables() {
		super(title, true, true, true, true);
		initGUI();
	}

	public SalesOfReceivables(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public SalesOfReceivables(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
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
			tabSORCenter = new _JTabbedPane();
			pnlMain.add(tabSORCenter, BorderLayout.CENTER);
			{
				pnlSORQualifiedAccounts = new JPanel(new BorderLayout(3, 3));
				tabSORCenter.add("Tag Qualified Accounts", pnlSORQualifiedAccounts);
				pnlSORQualifiedAccounts.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlQANorth = new JPanel(new BorderLayout(3, 3));
					pnlSORQualifiedAccounts.add(pnlQANorth, BorderLayout.NORTH);
					{
						pnlQANorthCenter = new JPanel(new BorderLayout(3, 3));
						pnlQANorth.add(pnlQANorthCenter, BorderLayout.CENTER);
						{
							pnlQANorthLabel = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlQANorthCenter.add(pnlQANorthLabel, BorderLayout.WEST);
							{
								lblQACompany = new JLabel("Company");
								pnlQANorthLabel.add(lblQACompany);
							}
							{
								lblQAProject = new JLabel("Project");
								pnlQANorthLabel.add(lblQAProject);
							}
							{
								lblQAPhase = new JLabel("Phase");
								pnlQANorthLabel.add(lblQAPhase);
							}
							{
								lblQABank = new JLabel("Bank");
								pnlQANorthLabel.add(lblQABank);
							}
							{
								lblQAMADate = new JLabel("MA Date");
								pnlQANorthLabel.add(lblQAMADate);
							}
						}
						{
							pnlQANorthComponent = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlQANorthCenter.add(pnlQANorthComponent, BorderLayout.CENTER);
							{
								pnlQACompany = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQACompany);
								{
									lookupQACompany = new _JLookup(null, "Select Company", 0);
									pnlQACompany.add(lookupQACompany, BorderLayout.WEST);
									lookupQACompany.setPreferredSize(new Dimension(50, 0));
									lookupQACompany.setLookupSQL(_SalesOfReceivables.sqlCompany());
									lookupQACompany.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String co_id = (String) data[0];
												String company_name = (String) data[1];
												
												FncSystem.out("Display sql for lookup of Company", lookupQACompany.getLookupSQL());
												txtQACompany.setText(company_name);
												lookupQAProject.setLookupSQL(_SalesOfReceivables.sqlProject(co_id));
											}
										}
									});
								}
								{
									txtQACompany = new _JXTextField();
									pnlQACompany.add(txtQACompany, BorderLayout.CENTER);
								}
							}
							{
								pnlQAProject = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQAProject);
								{
									lookupQAProject = new _JLookup(null, "Select Project", 0, "Please select Company first");
									pnlQAProject.add(lookupQAProject, BorderLayout.WEST);
									lookupQAProject.setPreferredSize(new Dimension(50, 0));
									lookupQAProject.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String proj_id = (String) data[0];
												String proj_name = (String) data[1];
												
												FncSystem.out("Display sql for lookup of Project", lookupQAProject.getLookupSQL());
												txtQAProject.setText(proj_name);
												lookupQAPhase.setLookupSQL(_SalesOfReceivables.sqlPhase(proj_id));
											}
										}
									});

								}
								{
									txtQAProject = new _JXTextField();
									pnlQAProject.add(txtQAProject, BorderLayout.CENTER);
								}
							}
							{
								pnlQAPhase = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQAPhase);
								{
									lookupQAPhase = new _JLookup(null, "Select Phase", 0, "Please select project first");
									pnlQAPhase.add(lookupQAPhase, BorderLayout.WEST);
									lookupQAPhase.setPreferredSize(new Dimension(50, 0));
									lookupQAPhase.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String sub_proj_name = (String) data[1];
												
												FncSystem.out("Display sql for lookup of Phase", lookupQAPhase.getLookupSQL());
												txtQAPhase.setText(sub_proj_name);
											}
										}
									});
								}
								{
									txtQAPhase = new _JXTextField();
									pnlQAPhase.add(txtQAPhase, BorderLayout.CENTER);
								}
							}
							{
								pnlQABank = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQABank);
								{
									lookupQABank = new _JLookup(null, "Select Bank", 0);
									pnlQABank.add(lookupQABank, BorderLayout.WEST);
									lookupQABank.setPreferredSize(new Dimension(50, 0));
									lookupQABank.setLookupSQL(_SalesOfReceivables.sqlBank());
									lookupQABank.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												String bank_name = (String) data[1];
												
												FncSystem.out("Display sql for lookup of Bank", lookupQABank.getLookupSQL());
												txtQABank.setText(bank_name);
											}
										}
									});
								}
								{
									txtQABank = new _JXTextField();
									pnlQABank.add(txtQABank, BorderLayout.CENTER);
								}
							}
							{
								pnlQAMADate = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQAMADate);
								{
									dateQAMADate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlQAMADate.add(dateQAMADate, BorderLayout.WEST);
									dateQAMADate.setPreferredSize(new Dimension(100, 0));
									dateQAMADate.addPropertyChangeListener(new PropertyChangeListener() {
										
										public void propertyChange(PropertyChangeEvent evt) {
											Date ma_date = dateQAMADate.getDate();
											
										}
									});
								}
								{
									lblQACutOff = new JLabel("Cut Off Date", JLabel.TRAILING);
									pnlQAMADate.add(lblQACutOff);
								}
								{
									dateQACutOff = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlQAMADate.add(dateQACutOff, BorderLayout.EAST);
									dateQACutOff.setPreferredSize(new Dimension(100, 0));
								}
							}
						}
					}
					{
						btnQAQualify = new JButton("Qualify");
						pnlQANorth.add(btnQAQualify, BorderLayout.EAST);
						btnQAQualify.setActionCommand("Qualified Accounts Qualify");
						btnQAQualify.addActionListener(this);
						btnQAQualify.setPreferredSize(new Dimension(200, 0));
					}
				}
				{
					pnlQACenter = new JPanel(new BorderLayout(3, 3));
					pnlSORQualifiedAccounts.add(pnlQACenter, BorderLayout.CENTER);
					{
						scrollQualifiedAcct = new JScrollPane();
						pnlQACenter.add(scrollQualifiedAcct, BorderLayout.CENTER);
						{
							modelSORQA = new modelSORQualifiedAccounts();
							tblQualifiedAcct = new _JTableMain(modelSORQA);
							scrollQualifiedAcct.setViewportView(tblQualifiedAcct);
							scrollQualifiedAcct.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						}
						{
							rowHeaderQualifiedAcct = tblQualifiedAcct.getRowHeader();
							rowHeaderQualifiedAcct.setModel(new DefaultListModel());
							scrollQualifiedAcct.setRowHeaderView(rowHeaderQualifiedAcct);
							scrollQualifiedAcct.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
			}
			{
				pnlSORApprovedAccounts = new JPanel(new BorderLayout(3, 3));
				tabSORCenter.add("Tag Approved Accounts", pnlSORApprovedAccounts);
				pnlSORApprovedAccounts.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlAANorth = new JPanel(new BorderLayout(3, 3));
					pnlSORApprovedAccounts.add(pnlAANorth, BorderLayout.NORTH);
					{
						pnlAANorthWest = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlAANorth.add(pnlAANorthWest, BorderLayout.CENTER);
						pnlAANorthWest.setBorder(JTBorderFactory.createTitleBorder("Search By"));
						{
							pnlAANorthWestLabel = new JPanel(new BorderLayout(3, 3));
							pnlAANorthWest.add(pnlAANorthWestLabel, BorderLayout.NORTH);
							/*{
								lblAASearchBy = new JLabel("Search By");
								pnlAANorthWestLabel.add(lblAASearchBy, BorderLayout.WEST);
							}*/
							{
								cmbAASearchBy = new JComboBox(new String [] {"Client", "Batch No"});
								pnlAANorthWestLabel.add(cmbAASearchBy, BorderLayout.WEST);
								cmbAASearchBy.setPreferredSize(new Dimension(100, 0));
								cmbAASearchBy.setSelectedItem(null);
								cmbAASearchBy.addItemListener(new ItemListener() {

									public void itemStateChanged(ItemEvent arg0) {
										int selected_item = cmbAASearchBy.getSelectedIndex();

										if(selected_item == 0){//Search by Client
											lookupAASearchBy.setLookupSQL(_SalesOfReceivables.sqlSearchByClient());
											SalesOfReceivables.this.setComponentsEditable(pnlAANorthEast, true);
											dateAACreditDate.getCalendarButton().setEnabled(true);
										}else if(selected_item == 1){//Search By Batch No
											lookupAASearchBy.setLookupSQL(_SalesOfReceivables.sqlAASearchByBatchNo());
											SalesOfReceivables.this.setComponentsEditable(pnlAANorthEast, true);
											dateAACreditDate.getCalendarButton().setEnabled(true);
										}else{
											lookupAASearchBy.setLookupSQL(null);
											SalesOfReceivables.this.setComponentsEditable(pnlAANorthEast, false);
										}
										lookupAASearchBy.setValue(null);
										txtAASearchBy.setText("");
										
									}
								});
							}
						}
						{
							pnlAANorthWestComponent = new JPanel(new BorderLayout(3, 3));
							pnlAANorthWest.add(pnlAANorthWestComponent, BorderLayout.SOUTH);
							{
								lookupAASearchBy = new _JLookup(null, "Search", 0, "Please choose search option");
								pnlAANorthWestComponent.add(lookupAASearchBy, BorderLayout.WEST);
								lookupAASearchBy.setPreferredSize(new Dimension(100, 0));
								lookupAASearchBy.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null){
											String search = (String) data[0];
											
											FncSystem.out("Display sql for lookup Search By", lookupAASearchBy.getLookupSQL());
											
											_SalesOfReceivables.displayApprovedAccounts(cmbAASearchBy.getSelectedIndex(), search, modelSORAA, rowHeaderApprovedAcct);
											tblApprovedAcct.packAll();
											scrollApprovedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblApprovedAcct.getRowCount())));
										}
									}
								});
							}
							{
								txtAASearchBy = new _JXTextField();
								pnlAANorthWestComponent.add(txtAASearchBy, BorderLayout.CENTER);
							}
						}
					}
					{
						pnlAANorthEast = new JPanel(new BorderLayout(3, 3));
						pnlAANorth.add(pnlAANorthEast, BorderLayout.EAST);
						pnlAANorthEast.setBorder(JTBorderFactory.createTitleBorder("Account Details"));
						{
							pnlAANELabel = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlAANorthEast.add(pnlAANELabel, BorderLayout.WEST);
							{
								lblAACreditDate = new JLabel("*Credit Date");
								pnlAANELabel.add(lblAACreditDate);
							}
							{
								lblAAPNNo = new JLabel("*PN No.");
								pnlAANELabel.add(lblAAPNNo);
							}
						}
						{
							pnlAANEComponent = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlAANorthEast.add(pnlAANEComponent, BorderLayout.CENTER);
							{
								pnlAACreditDate = new JPanel(new BorderLayout(3, 3));
								pnlAANEComponent.add(pnlAACreditDate);
								{
									dateAACreditDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlAACreditDate.add(dateAACreditDate, BorderLayout.WEST);
									dateAACreditDate.setPreferredSize(new Dimension(100, 0));
								}
								{
									lblAABankBatch = new JLabel("*Bank Batch", JLabel.TRAILING);
									pnlAACreditDate.add(lblAABankBatch);
								}
								{
									txtAABankBatch = new _JXTextField();
									pnlAACreditDate.add(txtAABankBatch, BorderLayout.EAST);
									txtAABankBatch.setPreferredSize(new Dimension(100, 0));
									txtAABankBatch.setHorizontalAlignment(JXTextField.CENTER);
								}
							}
							{
								pnlAAPNNo = new JPanel(new BorderLayout(3, 3));
								pnlAANEComponent.add(pnlAAPNNo);
								{
									txtAAPNNo = new _JXTextField();
									pnlAAPNNo.add(txtAAPNNo, BorderLayout.WEST);
									txtAAPNNo.setPreferredSize(new Dimension(100, 0));
									txtAAPNNo.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									lblAAORNo = new JLabel("*OR No.", JLabel.TRAILING);
									pnlAAPNNo.add(lblAAORNo);
								}
								{
									txtAAORNo = new _JXTextField();
									pnlAAPNNo.add(txtAAORNo, BorderLayout.EAST);
									txtAAORNo.setPreferredSize(new Dimension(100, 0));
									txtAAORNo.setHorizontalAlignment(JXTextField.CENTER);
								}
							}
						}
					}
				}
				{
					pnlAACenter = new JPanel(new BorderLayout(3, 3));
					pnlSORApprovedAccounts.add(pnlAACenter, BorderLayout.CENTER);
					{
						scrollApprovedAcct = new JScrollPane();
						pnlAACenter.add(scrollApprovedAcct, BorderLayout.CENTER);
						{
							modelSORAA = new modelSORApprovedAccounts();
							tblApprovedAcct = new _JTableMain(modelSORAA);
							scrollApprovedAcct.setViewportView(tblApprovedAcct);
							scrollApprovedAcct.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						}
						{
							rowHeaderApprovedAcct = tblApprovedAcct.getRowHeader();
							rowHeaderApprovedAcct.setModel(new DefaultListModel());
							scrollApprovedAcct.setRowHeaderView(rowHeaderApprovedAcct);
							scrollApprovedAcct.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
			}
			{
				pnlSORReturnedAccounts = new JPanel(new BorderLayout(3, 3));
				tabSORCenter.add("Tag Returned Accounts", pnlSORReturnedAccounts);
				pnlSORReturnedAccounts.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlRANorth = new JPanel(new BorderLayout(3, 3));
					pnlSORReturnedAccounts.add(pnlRANorth, BorderLayout.NORTH);
					{
						pnlRANorthWest = new JPanel(new BorderLayout(3, 3));
						pnlRANorth.add(pnlRANorthWest, BorderLayout.CENTER);
						pnlRANorthWest.setBorder(JTBorderFactory.createTitleBorder("Search by"));
						{
							pnlRANorthWestLabel = new JPanel(new BorderLayout(3, 3));
							pnlRANorthWest.add(pnlRANorthWestLabel, BorderLayout.WEST);
							{
								cmbRASearchBy = new JComboBox(new String []{"Client", "Batch No."});
								pnlRANorthWestLabel.add(cmbRASearchBy);
								cmbRASearchBy.setPreferredSize(new Dimension(100, 0));
								cmbRASearchBy.setSelectedItem(null);
								cmbRASearchBy.addItemListener(new ItemListener() {

									public void itemStateChanged(ItemEvent arg0) {
										int selected_item = cmbRASearchBy.getSelectedIndex();

										if(selected_item == 0){
											lookupRASearchBy.setLookupSQL(_SalesOfReceivables.sqlSearchByClient());
											dateRAReturnDate.setEditable(true);
											dateRAReturnDate.getCalendarButton().setEnabled(true);
										}else if(selected_item == 1){
											lookupRASearchBy.setLookupSQL(_SalesOfReceivables.sqlRASearchByBatchNo());
											dateRAReturnDate.setEditable(true);
											dateRAReturnDate.getCalendarButton().setEnabled(true);
										}else{
											lookupRASearchBy.setLookupSQL(null);
											dateRAReturnDate.setEditable(false);
											dateRAReturnDate.getCalendarButton().setEnabled(false);
										}
										lookupRASearchBy.setValue(null);
										txtRASearchBy.setText("");
										
										//System.out.println("Dumaan dito sa ComboBox ng Search By");
									}
								});
							}
						}
						{
							pnlRANorthWestComponent = new JPanel(new BorderLayout(3, 3));
							pnlRANorthWest.add(pnlRANorthWestComponent, BorderLayout.CENTER);
							{
								lookupRASearchBy = new _JLookup(null, "Search", 0, "Please select search option");
								pnlRANorthWestComponent.add(lookupRASearchBy, BorderLayout.WEST);
								lookupRASearchBy.setPreferredSize(new Dimension(100, 0));
								lookupRASearchBy.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data !=null){
											String search = (String) data[0];
											
											FncSystem.out("Display sql for lookup Search Returned Accounts", lookupRASearchBy.getLookupSQL());
											
											_SalesOfReceivables.displayReturnedAccounts(cmbRASearchBy.getSelectedIndex(), search, modelSORRA, rowHeaderReturnedAcct);
											tblReturnedAcct.packAll();
											scrollReturnedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReturnedAcct.getRowCount())));
										}
									}
								});
							}
							{
								txtRASearchBy = new _JXTextField();
								pnlRANorthWestComponent.add(txtRASearchBy, BorderLayout.CENTER);
							}
						}
					}
					{
						pnlRANorthEast = new JPanel(new BorderLayout(3, 3));
						pnlRANorth.add(pnlRANorthEast, BorderLayout.EAST);
						pnlRANorthEast.setBorder(JTBorderFactory.createTitleBorder("Accounts Details"));
						{
							pnlRANELabel = new JPanel(new BorderLayout(3, 3));
							pnlRANorthEast.add(pnlRANELabel, BorderLayout.WEST);
							{
								lblRAReturnDate = new JLabel("Return Date");
								pnlRANELabel.add(lblRAReturnDate);
							}
						}
						{
							pnlRANEComponent = new JPanel(new BorderLayout(3, 3));
							pnlRANorthEast.add(pnlRANEComponent, BorderLayout.CENTER);
							{
								dateRAReturnDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlRANEComponent.add(dateRAReturnDate);
								dateRAReturnDate.setPreferredSize(new Dimension(150, 0));
							}
						}
					}
				}
				{
					pnlRACenter = new JPanel(new BorderLayout(3, 3));
					pnlSORReturnedAccounts.add(pnlRACenter, BorderLayout.CENTER);
					{
						scrollReturnedAcct = new JScrollPane();
						pnlRACenter.add(scrollReturnedAcct, BorderLayout.CENTER);
						{
							modelSORRA = new modelSORReturnedAccounts();
							tblReturnedAcct = new _JTableMain(modelSORRA);
							scrollReturnedAcct.setViewportView(tblReturnedAcct);
							scrollReturnedAcct.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						}
						{
							rowHeaderReturnedAcct = tblReturnedAcct.getRowHeader();
							rowHeaderReturnedAcct.setModel(new DefaultListModel());
							scrollReturnedAcct.setRowHeaderView(rowHeaderReturnedAcct);
							scrollReturnedAcct.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
			}
		}
		{
			pnlSORSouth = new JPanel(new GridLayout(1, 2, 3, 3));
			pnlMain.add(pnlSORSouth, BorderLayout.SOUTH);
			{
				btnSORPost = new JButton("Post");
				pnlSORSouth.add(btnSORPost);
				btnSORPost.setActionCommand("SOR Post");
				btnSORPost.addActionListener(this);
			}
			/*{
				btnSORPreview = new JButton("Preview");
				pnlSORSouth.add(btnSORPreview);
				btnSORPreview.setActionCommand("SOR Preview");
				btnSORPreview.addActionListener(this);
			}*/
			{
				btnSORCancel = new JButton("Cancel");
				pnlSORSouth.add(btnSORCancel);
				btnSORCancel.setActionCommand("SOR Cancel");
				btnSORCancel.addActionListener(this);
			}
		}
		clearSOR(true,true, true);
	}//XXX END OF INIT GUI
	
	private void btnState(Boolean sQualify, Boolean sPost, Boolean sPreview, Boolean sCancel){
		btnQAQualify.setEnabled(sQualify);
		btnSORPost.setEnabled(sPost);
		btnSORPreview.setEnabled(sPreview);
		btnSORCancel.setEnabled(sCancel);
	}
	
	//CHECKS THE REQUIRED FIELD BEFORE SAVING
	private Boolean toSave(){

		if(tabSORCenter.getSelectedIndex() == 0){
			if(lookupQACompany.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Company", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(lookupQAProject.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(lookupQAPhase.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Phase", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(lookupQABank.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Bank", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(dateQAMADate.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input MA Date", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(dateQACutOff.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Cut Off Date", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(modelSORQA.getRowCount() == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts first", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(_SalesOfReceivables.getSelectedQualifiedAcct(modelSORQA) == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if(tabSORCenter.getSelectedIndex() == 1){
			
			if(modelSORAA.getRowCount() == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts first", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			if(_SalesOfReceivables.getSelectedApprovedAcct(modelSORAA) == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if(tabSORCenter.getSelectedIndex() == 2){
			
			if(modelSORRA.getRowCount() == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts first", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			if(_SalesOfReceivables.getSelectedReturnedAcct(modelSORRA) == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account(s) to tag", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		return true;
	}
	
	private void clearSOR(Boolean SORQA, Boolean SORAA, Boolean SORRA){
		System.out.println("Dumaan dito sa labas ng clear SOR");
		if(SORQA){//CLEARS THE FIELDS FOR QUALIFIED ACCOUNTS
			lookupQACompany.setValue(null);
			txtQACompany.setText("");
			lookupQAProject.setValue(null);
			txtQAProject.setText("");
			lookupQAPhase.setValue(null);
			txtQAPhase.setText("");
			lookupQABank.setValue(null);
			txtQABank.setText("");
			dateQAMADate.setDate(null);
			dateQACutOff.setDate(null);
			
			modelSORQA.clear();
			rowHeaderQualifiedAcct.setModel(new DefaultListModel());
			scrollQualifiedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			
			System.out.println("Dumaan dito sa clear ng field sa qualified accounts");
			
		}
		if(SORAA){//CLEARS THE FIELDS FOR THE APPROVED SOR ACCOUNTS
			
			cmbAASearchBy.setSelectedItem(null);
			lookupAASearchBy.setValue(null);
			txtAASearchBy.setText("");
			dateAACreditDate.setDate(null);
			txtAAPNNo.setText("");
			txtAABankBatch.setText("");
			txtAAORNo.setText("");
			dateAACreditDate.getCalendarButton().setEnabled(false);
			dateAACreditDate.setEditable(false);
			
			modelSORAA.clear();
			rowHeaderApprovedAcct.setModel(new DefaultListModel());
			scrollApprovedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			
			System.out.println("Dumaan dito sa clear ng fields sa approved accounts");
		}
		if(SORRA){//CLEARS THE FIELDS FOR THE RETURNED SOR ACCOUNTS
			cmbRASearchBy.setSelectedItem(null);
			lookupAASearchBy.setValue(null);
			txtAASearchBy.setText("");
			dateRAReturnDate.setDate(null);
			dateRAReturnDate.getCalendarButton().setEnabled(false);
			dateRAReturnDate.setEditable(false);
			
			//this.setComponentsEditable(pnlRACenter, false);
			
			modelSORRA.clear();
			rowHeaderReturnedAcct.setModel(new DefaultListModel());
			scrollReturnedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			
			System.out.println("Dumaan dito sa clear ng fields sa returned accounts");
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Qualified Accounts Qualify")){
			//if(toSave()){
				_SalesOfReceivables.displayQualifiedAccounts(lookupQAProject.getValue(), lookupQAPhase.getValue(), lookupQABank.getValue(),dateQACutOff.getDate() ,modelSORQA, rowHeaderQualifiedAcct);
				tblQualifiedAcct.packAll();
				scrollQualifiedAcct.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblQualifiedAcct.getRowCount())));
			//}
		}

		if(actionCommand.equals("SOR Post")){
			if(toSave()){
				if(tabSORCenter.getSelectedIndex() == 0){
					_SalesOfReceivables.saveQualifiedAccounts(lookupQABank.getValue(), dateQAMADate.getDate(), modelSORQA);
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Record(s) Saved", "Save", JOptionPane.INFORMATION_MESSAGE);
					clearSOR(true, false, false);
				}
				if(tabSORCenter.getSelectedIndex() == 1){
					_SalesOfReceivables.saveApprovedAccounts(dateAACreditDate.getDate(), txtAABankBatch.getText(), txtAAPNNo.getText(), txtAAORNo.getText(), modelSORAA);
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Record(s) succesfully tagged as approved", "Save", JOptionPane.INFORMATION_MESSAGE);
					clearSOR(false, true, false);
				}
				if(tabSORCenter.getSelectedIndex() == 2){
					_SalesOfReceivables.saveReturnAccounts(dateRAReturnDate.getDate(), modelSORRA);
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Record(s) successfully returned", "Save", JOptionPane.INFORMATION_MESSAGE);
					clearSOR(false, false, true);
				}
			}
		}
		
		if(actionCommand.equals("SOR Preview")){//XXX DELETE THIS
			if(toSave()){
				if(tabSORCenter.getSelectedIndex() == 0){
					ArrayList<TD_SORQualifiedAccounts> listSORQualifiedAccounts = new ArrayList<TD_SORQualifiedAccounts>();
					for(int x = 0;x< modelSORQA.getRowCount(); x++){
						Boolean isSelected = (Boolean) modelSORQA.getValueAt(x, 0);
						
						if(isSelected){
							String proj_id = (String) modelSORQA.getValueAt(x, 1);
							String unit_id = (String) modelSORQA.getValueAt(x, 2);
							String client_name = (String) modelSORQA.getValueAt(x, 8);
							BigDecimal nsp = (BigDecimal) modelSORQA.getValueAt(x, 9);
							BigDecimal sold_amt = (BigDecimal) modelSORQA.getValueAt(x, 10);
							Integer terms = (Integer) modelSORQA.getValueAt(x, 12);
							BigDecimal int_rate = (BigDecimal) modelSORQA.getValueAt(x, 13);
							
							listSORQualifiedAccounts.add(new TD_SORQualifiedAccounts(proj_id, unit_id, client_name, nsp, sold_amt, terms, int_rate));
						}
					}
					
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					
					mapParameters.put("company_name", txtQACompany.getText());
					mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptSORQualifiedAccounts.jasper"));
					mapParameters.put("listSORQualifiedAccounts", listSORQualifiedAccounts);
					
					FncReport.generateReport("/Reports/rptSORQualifiedAccounts.jasper", "SOR Qualified Accounts", mapParameters);
				}
				if(tabSORCenter.getSelectedIndex() == 1){//PREVIEW OF REPORTS FOR APPROVED ACCOUNTS
					
					ArrayList<TD_SORApprovedAccounts> listSORApprovedAccounts = new ArrayList<TD_SORApprovedAccounts>();
					
					for(int x = 0; x<modelSORAA.getRowCount(); x++){
						Boolean isSelected = (Boolean) modelSORAA.getValueAt(x, 0);
						
						if(isSelected){
							String phase = (String) modelSORAA.getValueAt(x, 5);
							String block = (String) modelSORAA.getValueAt(x, 6);
							String lot = (String) modelSORAA.getValueAt(x, 7);
							String client_name = (String) modelSORAA.getValueAt(x, 9);
							
							BigDecimal sold_amt = (BigDecimal) modelSORAA.getValueAt(x, 10);
							BigDecimal availed_amt = (BigDecimal) modelSORAA.getValueAt(x, 11);
						}
					}
					
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					
					mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptSORApprovedAccounts.jasper"));
					mapParameters.put("listSORApprovedAccounts", listSORApprovedAccounts);
					
					FncReport.generateReport("/Reports/rptSORApprovedAccounts.jasper", "SOR Approved Accounts", mapParameters);
					
					System.out.println("Dumaan dito sa preview ng approved accounts");
				}
				if(tabSORCenter.getSelectedIndex() == 2){
					
					for(int x = 0; x<modelSORRA.getRowCount(); x++){
						Boolean isSelected = (Boolean) modelSORRA.getValueAt(x, 0);
						
						if(isSelected){
							
						}
					}
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("listSORReturnedAccounts", "");
					mapParameters.put("SUBREPORT_DIR", "");
					
					System.out.println("Dumaan dito sa preview ng returned accounts");
				}
			}
		}
		
		if(actionCommand.equals("SOR Cancel")){
			if(tabSORCenter.getSelectedIndex() == 0){//CLEARS THE PANEL OF 
				clearSOR(true, false, false);
			}
			if(tabSORCenter.getSelectedIndex() == 1){
				clearSOR(false, true, false);
			}
			if(tabSORCenter.getSelectedIndex() == 2){
				clearSOR(false, false, true);
			}
		}
	}
}
