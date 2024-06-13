package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
//import javax.swing.border.LineBorder;
//import javax.swing.table.TableColumn;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.vdc.glasspane.JGlassLoading;

import Accounting.ContractorsPayment.ContractorsBilling.PopupTriggerListener_panel2;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import Reports.Accounting.CheckStatusListing;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelAssetMonitoring;
import tablemodel.modelRepair;
import tablemodel.modelapproval;

public class AssetMonitoring extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 652923134846245300L;

	public static String title = "Asset Monitoring";
	public static Dimension frameSize = new Dimension(922, 630);// 510, 250
	public static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	public static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JButton btnDispose;
	private JButton btnRetire;
	private JButton btnPullout;

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlButton1;
	private JPanel pnlCenter;
	private JPanel pnlRepairs;
	private JPanel pnlReplacement;
	private JLabel lblTransferform;
	private JLabel lblRemarks;
	private JLabel lblMovementno;
	private JLabel lblNewcustodian;
	private JLabel lblDepartment;
	private JLabel lblDivision;
	private _JLookup lookupCustodian;
	private _JLookup lookupRequestedby;
	public static _JLookup lookupCustodianid;
	private JPanel pnlTransfer;
	private JScrollPane scrollRepairs;

	private JTable tblRepairs;
	private JTable tblAsset;

	public static JTextField txtmovementno;
	public static JTextField txtCustodianid;
	private JTextField txtdepartment1;
	private JTextField txtdepartment2;
	private JTextField txtdivision1;
	private JTextField txtdivision2;
	public JTextField jtxttransfer;

	public static JTabbedPane tabAssets;
	private JScrollPane scrollAssets;
	public static _JTableMain tblAssets;
	private _JTableMain tblMovement;

	public static modelAssetMonitoring modelAssets;

	public static JList rowheaderAssets;
	private JList rowheaderMovement;

	private JScrollPane scrollMovement;
	private DefaultTableModel modelMovement;
	private panelAssetInformation pnlInformation;

	private JTextField txtCustodian;
	private JTextField jtRequestedby;
	protected DefaultTableModel modelRepair;
	private JCheckBox chkMyAssets;
	private JGlassLoading glass;
	private ListSelectionModel rowSM;
	private ListSelectionListener rowLSL;
	public static Thread loadAllAssets;
	private JTabbedPane tabbedAssets;
	private DefaultTableModel modelAsset;
	protected JCheckBox chkPrintAssetStickerInfo;
	protected DefaultTableCellRenderer rendererCenterAlign = new DefaultTableCellRenderer();
	protected DefaultTableCellRenderer rendererCenterRight = new DefaultTableCellRenderer();

	private JTextField jtxtRemarks;
	public static _JLookup lookupnewCustodian;
	private JTextField txtnewCustodian;
	private JTextField jtxtReplacement;
	private JButton btnRepsave;
	private JTextArea jtAreason;
	private JTextField txtsearchby;
	private JPanel pnlcompany;
	public static JTextField txtselectcompany;
	private JLabel txtcompany;
	private JPanel pnlButtton;
	public static JCheckBox chkinactiveemp;
	public static JCheckBox chkinactiveassets;
	private JPanel pnlNorthextra;
	private JPanel pnl1;
	private JLabel lblselectcompany;
	private JPanel pnlcheckbox;
	public static _JLookup lookupselectcompany;
	private JPanel pnlselectcompanyxtra;
	public static Boolean incl_Inc_emp = false;
	public Boolean incl_Inc_assets = false;

	private Runnable run;

	private Runnable runnable;

	protected Thread chkinactiveemprun;

	protected Thread chkinactiveassetsrun;

	private JPanel pnlsouthtransfer;

	private JPanel pnlbuttons;

	private JButton btn1;

	private JButton btn2;

	private JButton btn3;

	private JPanel pnlapproval;

	private JPanel pnlforapprovalNorth;

	private JScrollPane scrollapproval;

	public static modelapproval modelapproval;

	private _JTableMain tblapproval;

	public static JList rowheaderapproval;

	private JPanel pnlapprovalbuttons;

	private JPanel pnlapprovalbuttons1;

	private JPanel pnlforapprovalcenter;

	private JPanel pnlapprovalnorth;

	private JLabel lblcurrent_cust;

	private _JLookup lookupcurrent_cust;

	private JTextField txtpnlapprovalnorth;

	private JPanel pnlapprovalnorth1;

	private JPanel pnlcurrent_cust;

	private JTextField txtpnlapprovalnorthxtra;

	private JPanel pnlRefurbish;

	private JPanel pnlRefurbishnorth;

	private JPanel pnlRefurbishcenter;

	private JPanel pnlRefurbishsouth;

	private JLabel lblrecon;

	private JPanel pnlRefurbishnorth1;

	private _JLookup lookuprecon;

	private JTextField txtrecon1;

	private JTextField txtrecon2;

	private JPanel pnlRefurbishnorth2;

	private JTextField txtassetnorfrbsh1;

	private JLabel lblassetnorfrbsh;

	private JPanel pnlRefurbishnorth3;

	private JLabel lblcompanyrfrbsh;

	private _JLookup lookupcompanyrfrbsh;

	private JPanel pnlRefurbishnorth3_1;

	private JTextField txtcompanyrfrbsh1;

	private JTextField txtcompanyrfrbsh2;

	public static String dept_code = null;

	public static String co_id = "02";
	public static String co_name = "CENQHOMES DEVELOPMENT CORPORATION";
	public static String co_logo = "cenqlogo.png";
	private static boolean incl_Inc_asset;

	private JPopupMenu menu2;
	private JPopupMenu menu;
	private JMenuItem mniopenTag;

	protected static String loc_id = null;

	private JLabel lblLocation;

	public static _JLookup lookupLocation;

	public static JTextField txtLocation;

	public AssetMonitoring() {
		super(title, true, true, true, true);
		initGUI();
		loadAllAsset();
		// displayAllAssets(false,false);
		panelAssetInformation.btnState(true, true, false, true, false);
		// FncGlobal.startProgress("Please wait while loading assets.");
		/*
		 * runnable= new Runnable() { public void run() { loadAllAsset();
		 * FncGlobal.stopProgress(); } };
		 */
		// SwingUtilities.invokeLater(runnable);
	}

	public AssetMonitoring(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public AssetMonitoring(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			menu2 = new JPopupMenu("Popup");
			mniopenTag = new JMenuItem("Tagging");
			menu2.add(mniopenTag);
			mniopenTag.setEnabled(false);
			JSeparator sp = new JSeparator();
			menu2.add(sp);

			mniopenTag.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					// openDRF();
				}
			});

		}

		{// Main Panel
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				// pnlNorth.setBorder(lineBorder)
				pnlNorth.setBorder(new EmptyBorder(5, 5, 5, 5));
				pnlNorth.setPreferredSize(new Dimension(0, 60));
				// pnlNorth.setPreferredSize(new Dimension(900, 40));
				{
					pnlNorthextra = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlNorth.add(pnlNorthextra);
					// pnlNorthextra.setBackground(Color.green);
					{
						pnl1 = new JPanel(new BorderLayout(5, 5));
						pnlNorthextra.add(pnl1, BorderLayout.WEST);
						// pnl1.setBackground(Color.BLACK);
						{
							lblselectcompany = new JLabel("*Company");
							pnl1.add(lblselectcompany, BorderLayout.WEST);
							lblselectcompany.setPreferredSize(new Dimension(76, 0));

						}
						{
							lookupselectcompany = new _JLookup();
							pnl1.add(lookupselectcompany, BorderLayout.CENTER);
							// lookupselectcompany.setReturnColumn(0);
							lookupselectcompany.setPreferredSize(new Dimension(80, 0));
							lookupselectcompany.setValue(co_id);
							lookupselectcompany.setLookupSQL(_AssetMonitoring.getCompany());
							lookupselectcompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									FncSystem.out("Display SQL", lookupselectcompany.getLookupSQL());
									if (data != null) {
										co_id = (String) data[0];
										co_name = (String) data[1];
										co_logo = (String) data[3];
										lookupselectcompany.setValue(co_id);
										txtselectcompany.setText(co_name);
										lookupCustodianid.setValue("");
										txtCustodianid.setText("");
										// lookupCustodianid.setLookupSQL(_AssetMonitoring.getCustodian());
										lookupCustodianid
												.setLookupSQL(_AssetMonitoring.pnlinformationgetCustodian(co_id));
										panelAssetInformation.co_id = co_id;
										panelAssetInformation.co_name = co_name;
										panelAssetInformation.co_logo = co_logo;

										// panelAssetInformation.lookupCustodian.setLookupSQL(_AssetMonitoring.getCustodian(co_id));
										panelAssetInformation.lookupCustodian
												.setLookupSQL(_AssetMonitoring.pnlinformationgetCustodian(co_id));

										new Thread(new Runnable() {

											@Override
											public void run() {
												FncGlobal.startProgress("Please wait while loading assets.");
												// TODO Auto-generated method stub
												if (chkinactiveemp.isSelected() == true) {
													if (chkinactiveassets.isSelected() == true) {
														displayAllAssets(true, true);
													} else {
														displayAllAssets(true, false);
													}
												} else {
													if (chkinactiveassets.isSelected() == true) {
														displayAllAssets(false, true);
													} else {
														displayAllAssets(false, false);
													}
												}
												FncGlobal.stopProgress();
											}
										}).start();

										// Uncomment start for tabapproval
										/*
										 * if(UserInfo.EmployeeCode.equals("900430")){
										 * //UserInfo.EmployeeCode.equals("900430")||
										 * UserInfo.EmployeeCode==_AssetMonitoring.chckdepthead()){
										 * btn2.setEnabled(true); btn3.setEnabled(true); tblapproval.setEditable(true);
										 * modelapproval.setEditable(true);
										 * _AssetMonitoring.displayforapprovalbyhad(modelapproval, rowheaderapproval,
										 * lookupCustodianid.getValue()); }else if (_AssetMonitoring.chckdepthead()){
										 * btn2.setEnabled(true); btn3.setEnabled(false); tblapproval.setEditable(true);
										 * modelapproval.setEditable(true);
										 * _AssetMonitoring.displayforapprovalbydepthead(modelapproval,
										 * rowheaderapproval, lookupCustodianid.getValue(), dept_code);
										 * //_AssetMonitoring.displayforapprovalbyhad(modelapproval, rowheaderapproval,
										 * lookupCustodianid.getValue()); }else{ tblapproval.setEditable(false);
										 * modelapproval.setEditable(false); btn2.setEnabled(false);
										 * btn3.setEnabled(false); }
										 */

										// clearmodelaprvlchkbox();
										// Uncomment end for tabapproval

									}

								}
							});

						}

						{
							pnlselectcompanyxtra = new JPanel(new BorderLayout(5, 5));
							pnl1.add(pnlselectcompanyxtra, BorderLayout.EAST);
							pnlselectcompanyxtra.setPreferredSize(new Dimension(725, 0));
							// pnlselectcompanyxtra.setBackground(Color.gray);
							{
								txtselectcompany = new JTextField();
								pnlselectcompanyxtra.add(txtselectcompany, BorderLayout.WEST);
								txtselectcompany.setPreferredSize(new Dimension(358, 0));
								txtselectcompany.setText(co_name);
							}

						}
					}
					{
						pnlcheckbox = new JPanel(new BorderLayout(2, 2));
						pnlNorthextra.add(pnlcheckbox, BorderLayout.WEST);
						pnlcheckbox.setPreferredSize(new Dimension(100, 0));
						// pnlcheckbox.setBackground(Color.CYAN);
						{
							chkMyAssets = new JCheckBox("All asset");
							pnlcheckbox.add(chkMyAssets, BorderLayout.WEST);
							chkMyAssets.setSelected(true);
							chkMyAssets.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {

									/*
									 * if(chkMyAssets.isSelected()==true){ if(chkinactiveemp.isSelected()==true){ if
									 * (chkinactiveassets.isSelected()==true) {displayAllAssets(true, true);} else
									 * {displayAllAssets(true, false);} } else{ if
									 * (chkinactiveassets.isSelected()==true) {displayAllAssets(false, true);} else
									 * {displayAllAssets(false, false);} }
									 * 
									 * }else{ lookupCustodianid.setEditable(true);
									 * lookupCustodianid.setEnabled(true); lookupCustodianid.grabFocus();
									 * panelAssetInformation.resetInformation(); modelAssets.clear();
									 * rowheaderAssets.setModel(new DefaultListModel()); }
									 */
								}
							});
						}
					}
					{
						JPanel pnllookuptext = new JPanel(new BorderLayout(5, 5));
						pnlcheckbox.add(pnllookuptext, BorderLayout.CENTER);
						pnllookuptext.setPreferredSize(new Dimension(150, 10));
						{
							lookupCustodianid = new _JLookup("Custodian ID");
							// lookupCustodianid = new _JLookup(null, "Custodian ID", 0);
							pnllookuptext.add(lookupCustodianid, BorderLayout.WEST);
							lookupCustodianid.setPreferredSize(new Dimension(80, 0));
							// lookupCustodianid.setReturnColumn(0);
							// lookupCustodianid.setEditable(false);
							lookupCustodianid.setLookupSQL(_AssetMonitoring.pnlinformationgetCustodian(co_id));
							// lookupCustodianid.setLookupSQL(_AssetMonitoring.getCustodian(co_id));
							lookupCustodianid.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									// panelAssetInformation.resetInformation();
									FncSystem.out("Display SQL for Client", lookupCustodianid.getLookupSQL());
									if (data != null) {
										String emp_code = (String) data[0];
										System.out.printf("Display value of entity_id: %s%n", emp_code);
										String emp_name = (String) data[1];
										dept_code = (String) data[6];
										lookupCustodianid.setValue(emp_code);
										txtCustodianid.setText(emp_name);
										_AssetMonitoring.displayIndividualAssets(modelAssets, rowheaderAssets,
												emp_code);
										// _AssetMonitoring.displayforapprovalbydepthead(modelapproval,rowheaderapproval,lookupCustodianid.getValue(),dept_code);
									}

								}
							});
						}
						{
							txtCustodianid = new JTextField();
							pnllookuptext.add(txtCustodianid, BorderLayout.CENTER);
							txtCustodianid.setEditable(false);
						}
					}
					{
						JPanel pnlxtra = new JPanel(new BorderLayout(20, 20));
						pnlcheckbox.add(pnlxtra, BorderLayout.EAST);
						pnlxtra.setPreferredSize(new Dimension(365, 10));
						{
							chkinactiveemp = new JCheckBox("Include Inactive Employees");
							pnlxtra.add(chkinactiveemp, BorderLayout.WEST);
							chkinactiveemp.addItemListener(new ItemListener() {

								public void itemStateChanged(ItemEvent e) {
									new Thread(new Runnable() {

										@Override
										public void run() {
											FncGlobal.startProgress("Please wait while loading assets.");
											// TODO Auto-generated method stub
											if (chkinactiveemp.isSelected() == true) {
												if (chkinactiveassets.isSelected() == true)

												{
													displayAllAssets(true, true);
												} else {
													displayAllAssets(true, false);
													lookupCustodianid.setValue("");
													txtCustodianid.setText("");
												}
											} else {
												if (chkinactiveassets.isSelected() == true) {
													displayAllAssets(false, true);
												} else {
													displayAllAssets(false, false);
													lookupCustodianid.setValue("");
													txtCustodianid.setText("");
												}
											}
											FncGlobal.stopProgress();
										}
									}).start();
								}
							});
						}
						{
							chkinactiveassets = new JCheckBox("Include Inactive Assets");
							pnlxtra.add(chkinactiveassets, BorderLayout.EAST);
							chkinactiveassets.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									new Thread(new Runnable() {

										@Override
										public void run() {
											FncGlobal.startProgress("Please wait while loading assets.");
											// TODO Auto-generated method stub
											if (chkinactiveassets.isSelected() == true) {
												if (chkinactiveemp.isSelected() == true) {
													displayAllAssets(true, true);

												} else {
													displayAllAssets(false, true);
												}
											} else {
												if (chkinactiveemp.isSelected() == true) {
													displayAllAssets(true, false);
												} else {
													displayAllAssets(false, false);
												}
											}
											FncGlobal.stopProgress();
										}
									}).start();
								}
							});
						}
					}
				}

			}
			{

			}
			{ // Center Panel
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new Dimension(788, 50));
				pnlCenter.setBorder(lineBorder);
				/*
				 * { JPanel pnlsearchby= new JPanel(new BorderLayout(5, 5));
				 * pnlCenter.add(pnlsearchby, BorderLayout.NORTH);
				 * //pnlsearchby.setBackground(Color.RED); pnlsearchby.setPreferredSize(new
				 * Dimension(600, 20)); { JLabel lblSeachby= new JLabel("Search by");
				 * pnlsearchby.add(lblSeachby, BorderLayout.WEST);
				 * lblSeachby.setPreferredSize(new Dimension(90, 30)); } { JPanel
				 * pnlCombotxtfield= new JPanel(new BorderLayout(5,5));
				 * pnlsearchby.add(pnlCombotxtfield, BorderLayout.CENTER);
				 * //pnlCombotxtfield.setBackground(Color.RED); { ComboBoxModel jComboBox1Model
				 * = new DefaultComboBoxModel(new String[] { "Asset No", "Asset Code",
				 * "Asset Name", "Custodian" }); cmbSearchby = new JComboBox();
				 * pnlCombotxtfield.add(cmbSearchby, BorderLayout.WEST);
				 * cmbSearchby.setModel(jComboBox1Model); cmbSearchby.setName("cmbSearchby");
				 * 
				 * } { txtsearchby=new JTextField(); pnlCombotxtfield.add(txtsearchby,
				 * BorderLayout.CENTER); //txtsearchby.setPreferredSize(new Dimension(705, 0));
				 * txtsearchby.setEditable(true);
				 * 
				 * 
				 * 
				 * } { JPanel pnlxtra= new JPanel(); pnlCombotxtfield.add(pnlxtra,
				 * BorderLayout.EAST); pnlxtra.setPreferredSize(new Dimension(305, 0)); } } }
				 */

				{
					JPanel pnlscroll = new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlscroll, BorderLayout.CENTER);
					// pnlscroll.setBackground(Color.CYAN);
					{
						{
							scrollAssets = new JScrollPane();
							pnlscroll.add(scrollAssets, BorderLayout.CENTER);
							scrollAssets.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
							{
								modelAssets = new modelAssetMonitoring();
								tblAssets = new _JTableMain(modelAssets);
								scrollAssets.setViewportView(tblAssets);
								// tblAssets.setPreferredSize(new Dimension(1100, 250));
								tblAssets.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent e) {
										if (!e.getValueIsAdjusting()) {
											try {
												// System.out.println("Dumaan dito");
												int row = tblAssets.getSelectedRow();
												String asset_no = (String) modelAssets.getValueAt(row, 1);
												// System.out.println(row);
												// System.out.println(asset_no);
												_AssetMonitoring.displayMovementHistory(modelMovement,
														rowheaderMovement, asset_no);
												// _AssetMonitoring.displayRepairHistory(asset_no, modelRepair);
												panelAssetInformation.displayAssetDetail(asset_no);
												panelAssetInformation.btnPreview.setEnabled(true);
												btn1.setEnabled(true);
												btnDispose.setEnabled(true);
												btnPullout.setEnabled(true);
												btnRetire.setEnabled(true);

											} catch (ArrayIndexOutOfBoundsException ex) {
											}
										}
									}
								});

								tblAssets.getTableHeader().setEnabled(false);
								// tblAssets.setSortable(false);
								tblAssets.setFillsViewportHeight(false);
								tblAssets.getColumnModel().getColumn(0).setPreferredWidth(50);// checkbox
								tblAssets.getColumnModel().getColumn(1).setPreferredWidth(120);
								;// asset_no
								tblAssets.getColumnModel().getColumn(2).setPreferredWidth(130);// asset code
								tblAssets.getColumnModel().getColumn(3).setPreferredWidth(200);// asset name
								tblAssets.getColumnModel().getColumn(4).setPreferredWidth(100);// date acquired
								tblAssets.getColumnModel().getColumn(6).setPreferredWidth(230);// Custodian
								tblAssets.getColumnModel().getColumn(8).setPreferredWidth(70);// status
								tblAssets.hideColumns("Custodian ID", "Reference No.");

							}
							{
								rowheaderAssets = tblAssets.getRowHeader();
								scrollAssets.setRowHeaderView(rowheaderAssets);
								scrollAssets.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}

						}
					}
				}
				/*
				 * { JPanel pnlAssetimmage= new JPanel(new BorderLayout(2,2));
				 * pnlCenter.add(pnlAssetimmage, BorderLayout.EAST);
				 * pnlAssetimmage.setPreferredSize(new Dimension(320, 0)); { JPanel
				 * pnlImmage=new JPanel(); pnlAssetimmage.add(pnlImmage, BorderLayout.CENTER);
				 * pnlImmage.setBackground(Color.BLACK); } { JPanel pnlLabelUploadassetpicture=
				 * new JPanel(); pnlAssetimmage.add(pnlLabelUploadassetpicture,
				 * BorderLayout.SOUTH); pnlLabelUploadassetpicture.setPreferredSize(new
				 * Dimension(0, 15)); { JLabel lblUploadassetpicture= new
				 * JLabel("Upload Asset Picture");
				 * pnlLabelUploadassetpicture.add(lblUploadassetpicture); } } }
				 */
			}
			{
				tabAssets = new JTabbedPane();
				pnlMain.add(tabAssets, BorderLayout.SOUTH);
				tabAssets.setPreferredSize(new Dimension(500, 350));
				// tabAssets.setPreferredSize(new Dimension(500, 320));
				{
					pnlInformation = new panelAssetInformation();
					tabAssets.addTab(" Information ", null, pnlInformation, null);
					panelAssetInformation.co_id = co_id;
					panelAssetInformation.co_name = co_name;
					// panelAssetInformation.co_logo = co_logo;

				}
				{
					pnlTransfer = new JPanel(new BorderLayout(5, 5));
					tabAssets.addTab("  Tagging  ", null, pnlTransfer, null);
					// pnlTransfer.setBackground(Color.DARK_GRAY);;
					// pnlTransfer.setPreferredSize(new Dimension(400,80));
					{
						pnlNorth = new JPanel(new GridLayout(4, 1, 5, 5));
						pnlTransfer.add(pnlNorth, BorderLayout.NORTH);
						pnlNorth.setBorder(JTBorderFactory.createTitleBorder(""));
						pnlNorth.setPreferredSize(new Dimension(400, 120));
						{
							JPanel pnl1 = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnl1);
							pnl1.setPreferredSize(new Dimension(100, 30));
							// pnl1.setBackground(Color.BLUE);
							{
								lblMovementno = new JLabel("Movement No.");
								pnl1.add(lblMovementno, BorderLayout.WEST);
								lblMovementno.setPreferredSize(new Dimension(115, 0));
							}
							{
								txtmovementno = new JTextField();
								pnl1.add(txtmovementno, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnl2 = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnl2);
							setPreferredSize(new Dimension(115, 30));
							{
								lblNewcustodian = new JLabel("*New Custodian");
								pnl2.add(lblNewcustodian, BorderLayout.WEST);
								lblNewcustodian.setPreferredSize(new Dimension(115, 0));

							}
							{
								lookupnewCustodian = new _JLookup();
								pnl2.add(lookupnewCustodian, BorderLayout.CENTER);
								// lookupCompany.setPreferredSize(new Dimension(200, 0));
								// lookupCustodian.setEditable(true);
								lookupnewCustodian.setEditable(true);
								// lookupnewCustodian.setLookupSQL(_AssetMonitoring.getCustodian(co_id));
								lookupnewCustodian.addLookupListener(new LookupListener() {
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] setCustodian = ((_JLookup) event.getSource()).getDataSet();
										FncSystem.out("Display SQL for Client", lookupnewCustodian.getLookupSQL());
										if (setCustodian != null) {
											String emp_code = (String) setCustodian[0];
											// System.out.printf("Display value of entity_id: %s%n", emp_code);
											String emp_name = (String) setCustodian[1];
											lookupnewCustodian.setValue(emp_code);
											txtnewCustodian.setText(emp_name);

										}
									}
								});
							}
							{
								txtnewCustodian = new JTextField();
								pnl2.add(txtnewCustodian, BorderLayout.EAST);
								txtnewCustodian.setPreferredSize(new Dimension(650, 0));
								txtnewCustodian.setEditable(true);
							}
						}
						{
							JPanel pnl3 = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnl3);
							setPreferredSize(new Dimension(115, 30));
							{
								lblLocation = new JLabel("*Location");
								pnl3.add(lblLocation, BorderLayout.WEST);
								lblLocation.setPreferredSize(new Dimension(115, 0));

							}
							{
								lookupLocation = new _JLookup();
								pnl3.add(lookupLocation, BorderLayout.CENTER);
								// lookupCompany.setPreferredSize(new Dimension(200, 0));
								// lookupCustodian.setEditable(true);
								lookupLocation.setEditable(true);
								lookupLocation.setLookupSQL(getassetlocation());
								lookupLocation.addLookupListener(new LookupListener() {
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] setLocation = ((_JLookup) event.getSource()).getDataSet();
										FncSystem.out("Display SQL for Location", getassetlocation());
										if (setLocation != null) {
											loc_id = (String) setLocation[0];
											// System.out.printf("Display value of entity_id: %s%n", emp_code);
											String loc_name = (String) setLocation[1];
											lookupLocation.setValue(loc_id);
											txtLocation.setText(loc_name);

										}
									}
								});
							}
							{
								txtLocation = new JTextField();
								pnl3.add(txtLocation, BorderLayout.EAST);
								txtLocation.setPreferredSize(new Dimension(650, 0));
								txtLocation.setEditable(true);
							}
						}
						{
							JPanel pnl4 = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnl4);
							// pnl3.setBackground(Color.RED);
							{
								lblRemarks = new JLabel("*Remarks");
								pnl4.add(lblRemarks, BorderLayout.WEST);
								lblRemarks.setPreferredSize(new Dimension(115, 0));
							}
							{
								jtxtRemarks = new JTextField();
								pnl4.add(jtxtRemarks, BorderLayout.CENTER);
								jtxtRemarks.setEditable(true);
							}
						}
						/*
						 * { JPanel pnl4= new JPanel(new BorderLayout(5, 5)); pnlNorth.add(pnl4);
						 * //pnlNorth.setBackground(Color.orange); { lblTransferform = new
						 * JLabel("*Transfer Form No"); pnl4.add(lblTransferform, BorderLayout.WEST);
						 * lblTransferform.setPreferredSize(new Dimension(115, 0)); } { jtxttransfer=
						 * new JTextField(); pnl4.add(jtxttransfer, BorderLayout.CENTER); } { btn1=new
						 * JButton("Transfer"); pnl4.add(btn1, BorderLayout.WEST);
						 * //btn1=setEnabled(false); btn1.setEnabled(false); btn1.addActionListener(new
						 * ActionListener() {
						 * 
						 * public void actionPerformed(ActionEvent e) { int move_no =
						 * Integer.valueOf(txtmovementno.getText()) - 1;
						 * if(tblAssets.getSelectedRows().length>0){
						 * 
						 * if(hasCheckedAssets()){ int
						 * toSave=JOptionPane.showConfirmDialog(getTopLevelAncestor()
						 * ,"Are all entries correct?",btnTransfer.getText(),JOptionPane.YES_NO_OPTION);
						 * if(toSave==JOptionPane.YES_OPTION){
						 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
						 * "Asset has been transfered","Transfer", JOptionPane.INFORMATION_MESSAGE); int
						 * row = tblAssets.getSelectedRow(); String
						 * custodian_id=modelAssets.getValueAt(row, 5).toString();
						 * 
						 * System.out.println(row); System.out.println();
						 * _AssetMonitoring.savetransferforapproval(modelAssets,Integer.valueOf(
						 * lookupnewCustodian.getText()),Integer.valueOf(txtmovementno.getText()),
						 * jtxtRemarks.getText()); //_AssetMonitoring.transferAsset(modelAssets,
						 * move_no, custodian_id, lookupnewCustodian.getValue(), jtxtRemarks.getText(),
						 * txtnewCustodian.getText()); resetMovement();
						 * panelAssetInformation.resetInformation(); panelAssetInformation.co_id =
						 * co_id; panelAssetInformation.co_name = co_name;
						 * jtxtRemarks.setEditable(true);
						 * 
						 * } }else{ JOptionPane.showMessageDialog(getTopLevelAncestor(),
						 * "Please fill up the required fields",btnTransfer.getText(),JOptionPane.
						 * WARNING_MESSAGE); }
						 * 
						 * }else{ JOptionPane.showMessageDialog(getTopLevelAncestor(),
						 * "Please select asset to transfer","Transfer",JOptionPane.WARNING_MESSAGE);
						 * 
						 * }
						 * 
						 * } }); } }
						 */
					}
					{
						JPanel pnlCentertransfer = new JPanel(new BorderLayout(5, 5));
						pnlTransfer.add(pnlCentertransfer, BorderLayout.CENTER);
						// setPreferredSize(new Dimension(115,30));
						{
							scrollMovement = new JScrollPane();
							pnlCentertransfer.add(scrollMovement);
							{
								SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

								// Object[] colNames = {"Movement No.", "Old Asset Code", "From", "To", "Date
								// Transfered", "Reason", "Remarks"};
								// Object[][] data = null;
								modelMovement = new tablemodel.modelMovement();
								// modelMovement = new modelRepair();
								tblMovement = new _JTableMain(modelMovement);
								scrollMovement.setViewportView(tblMovement);

								tblMovement.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								tblMovement.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
								tblMovement.getColumnModel().getColumn(0).setPreferredWidth(100);// move_no
								tblMovement.getColumnModel().getColumn(1).setPreferredWidth(125);// old asset
								tblMovement.getColumnModel().getColumn(2).setPreferredWidth(135);// prev_cust
								tblMovement.getColumnModel().getColumn(3).setPreferredWidth(135);// current_cust
								tblMovement.getColumnModel().getColumn(4).setPreferredWidth(110);// trans_date
								tblMovement.getColumnModel().getColumn(5).setPreferredWidth(95);// reason
								tblMovement.getColumnModel().getColumn(6).setPreferredWidth(150);// remarks
								tblMovement.getColumnModel().getColumn(6).setPreferredWidth(150);// old location added
																									// by jari cruz as
																									// of july 8 2022
								tblMovement.setFont(new Font("DejaVu Sans", 0, 12));

								tblMovement.getColumnModel().getColumn(0).setCellRenderer(rendererCenterAlign);
								tblMovement.getColumnModel().getColumn(1).setCellRenderer(rendererCenterAlign);
								tblMovement.getColumnModel().getColumn(4).setCellRenderer(rendererCenterAlign);

								// tblMovement.getColumnModel().getColumn(8).setCellRenderer(new
								// DateRenderer(sdf));
								// tblMovement.getColumnModel().getColumn(10).setCellRenderer(new
								// DateRenderer(sdf));

							}
							{
								rowheaderMovement = tblMovement.getRowHeader();
								scrollMovement.setRowHeaderView(rowheaderMovement);
								scrollMovement.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
					}
					{
						pnlsouthtransfer = new JPanel(new BorderLayout(5, 5));
						pnlTransfer.add(pnlsouthtransfer, BorderLayout.SOUTH);
						pnlsouthtransfer.setPreferredSize(new Dimension(0, 30));
						{
							pnlbuttons = new JPanel(new GridLayout(1, 4, 5, 5));
							pnlsouthtransfer.add(pnlbuttons);
							{
								btn1 = new JButton("Transfer asset");
								// btn1=new JButton("Save for transfer approval");
								pnlbuttons.add(btn1, BorderLayout.WEST);
								btn1.setEnabled(false);
								btn1.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										int move_no = Integer.valueOf(txtmovementno.getText()) - 1;
										if (tblAssets.getSelectedRows().length > 0) {

											if (hasCheckedAssets()) {
												int toSave = JOptionPane.showConfirmDialog(getTopLevelAncestor(),
														"Are all entries correct?", btn1.getText(),
														JOptionPane.YES_NO_OPTION);
												if (toSave == JOptionPane.YES_OPTION) {
													// for direct transfer
													JOptionPane.showMessageDialog(getTopLevelAncestor(),
															"Asset is already transferd.", "Transfer",
															JOptionPane.INFORMATION_MESSAGE);
													// for Asset transfer approval only
													// JOptionPane.showMessageDialog(getTopLevelAncestor(), "Asset
													// trasfer is forwarded to dept head for approval.","Transfer",
													// JOptionPane.INFORMATION_MESSAGE);
													int row = tblAssets.getSelectedRow();
													String custodian_id = modelAssets.getValueAt(row, 5).toString();

													System.out.println(row);
													System.out.println();
													// _AssetMonitoring.savetransferforapproval(modelAssets,Integer.valueOf(lookupnewCustodian.getText()),Integer.valueOf(txtmovementno.getText()),jtxtRemarks.getText());
													// _AssetMonitoring.savefortransferapproval(modelAssets, move_no,
													// custodian_id, lookupnewCustodian.getValue(),
													// jtxtRemarks.getText(), txtnewCustodian.getText(),dept_code);//
													// For save asset for transfer
													transferAsset(move_no, custodian_id, lookupnewCustodian.getValue(),
															jtxtRemarks.getText(), txtnewCustodian.getText(),
															dept_code);
													resetMovement();
													panelAssetInformation.resetInformation();
													panelAssetInformation.co_id = co_id;
													panelAssetInformation.co_name = co_name;
													jtxtRemarks.setEditable(true);

												}
											} else {
												JOptionPane.showMessageDialog(getTopLevelAncestor(),
														"Please fill up the required fields", btn1.getText(),
														JOptionPane.WARNING_MESSAGE);
											}

										} else {
											JOptionPane.showMessageDialog(getTopLevelAncestor(),
													"Please select asset to transfer", "Transfer",
													JOptionPane.WARNING_MESSAGE);
										}
									}
								});
							}
							{
								btnDispose = new JButton("Dispose");
								pnlbuttons.add(btnDispose);
								btnDispose.setEnabled(false);
								btnDispose.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										if (hasCheckedAssets()) {
											if (UserInfo.EmployeeCode.equals("901250")) {
												Object[] options = { "OK", "CANCEL" };
												int todispose = JOptionPane.showOptionDialog(getTopLevelAncestor(),
														"Click OK to continue", "Dispose Asset?",
														JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
														null, options, options);

												if (todispose == JOptionPane.YES_OPTION) {
													JOptionPane.showMessageDialog(getTopLevelAncestor(),
															"Asset has been disposed.", btnDispose.getText(),
															JOptionPane.INFORMATION_MESSAGE);
													_AssetMonitoring.disposeAsset(tblAssets);
													// _AssetMonitoring.retireAsset(tblAsset);
													panelAssetInformation.resetInformation();
												}

											} else {
												JOptionPane.showMessageDialog(getTopLevelAncestor(),
														"You are not allowed to Dispose asset.", "Save",
														JOptionPane.PLAIN_MESSAGE);
											}
										} else {
											JOptionPane.showMessageDialog(getTopLevelAncestor(),
													"Please select asset to Dispose.", btnDispose.getText(),
													JOptionPane.INFORMATION_MESSAGE);
										}
									}
								});
							}
							{
								btnRetire = new JButton("Retire");
								pnlbuttons.add(btnRetire);
								btnRetire.setEnabled(false);
								btnRetire.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										if (hasCheckedAssets()) {
											if (UserInfo.EmployeeCode.equals("901250")) {
												Object[] options = { "OK", "CANCEL" };
												int toRetire = JOptionPane.showOptionDialog(getTopLevelAncestor(),
														"Click OK to continue.", "Retire Asset?",
														JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
														options, options);

												if (toRetire == JOptionPane.YES_OPTION) {
													JOptionPane.showMessageDialog(getTopLevelAncestor(),
															"Asset has been retired.", "Retire",
															JOptionPane.INFORMATION_MESSAGE);
													_AssetMonitoring.retireAsset(tblAssets);
													panelAssetInformation.resetInformation();
												}

											} else {
												JOptionPane.showMessageDialog(getTopLevelAncestor(),
														"You are not allowed to Retire asset.", "Save",
														JOptionPane.PLAIN_MESSAGE);
											}

										} else {
											JOptionPane.showMessageDialog(getTopLevelAncestor(),
													"Please select asset to retire.", btnRetire.getText(),
													JOptionPane.WARNING_MESSAGE);

										}
									}
								});
							}
							{
								btnPullout = new JButton("Pull-out");
								pnlbuttons.add(btnPullout);
								btnPullout.setEnabled(false);
								btnPullout.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										if (hasCheckedAssets()) {
											Object[] options = { "OK", "CANCEL" };
											int toPulloutAsset = JOptionPane.showOptionDialog(getTopLevelAncestor(),
													"Click OK to continue", "Pullout asset for Repair?",
													JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
													options, options);

											if (toPulloutAsset == JOptionPane.YES_OPTION) {
												JOptionPane.showMessageDialog(getTopLevelAncestor(),
														"Asset has been pullout for repair.", "Pull-out",
														JOptionPane.INFORMATION_MESSAGE);
												int row = tblAssets.getSelectedRow();
												String asset_no = modelAssets.getValueAt(row, 1).toString();
												System.out.println(row);
												System.out.println();
												panelAssetInformation.pulloutAsset(asset_no);
												clearcheckbox();

											}
										} else {
											JOptionPane.showMessageDialog(getTopLevelAncestor(),
													"Please select asset to pull-out.", btnPullout.getText(),
													JOptionPane.WARNING_MESSAGE);
										}
									}
								});

							}
						}
					}
				}
				// Uncomment pannel to display the tab
				/*
				 * { pnlapproval= new JPanel(new BorderLayout(3,3));
				 * tabAssets.addTab(" For approval ", null, pnlapproval, null); {
				 * pnlapprovalnorth= new JPanel(new BorderLayout(5,5));
				 * pnlapproval.add(pnlapprovalnorth, BorderLayout.NORTH);
				 * pnlapprovalnorth.setBorder(JTBorderFactory.createTitleBorder(""));
				 * pnlapprovalnorth.setPreferredSize(new Dimension(0, 55));
				 * //pnlapprovalnorth.setBackground(Color.GRAY); { pnlcurrent_cust=new
				 * JPanel(new BorderLayout(3,3));
				 * pnlapprovalnorth.add(pnlcurrent_cust,BorderLayout.WEST);
				 * //pnlcurrent_cust.setBackground(Color.green);
				 * pnlcurrent_cust.setPreferredSize(new Dimension(125, 0)); { lblcurrent_cust=
				 * new JLabel("Current custodian");
				 * pnlcurrent_cust.add(lblcurrent_cust,BorderLayout.WEST);
				 * //lblcurrent_cust.setPreferredSize(new Dimension(150, 0)); } }
				 * 
				 * { pnlapprovalnorth1= new JPanel(new BorderLayout(5,5));
				 * pnlapprovalnorth.add(pnlapprovalnorth1, BorderLayout.CENTER);
				 * //pnlapprovalnorth1.setBackground(Color.blue); { lookupcurrent_cust= new
				 * _JLookup(); lookupcurrent_cust.setReturnColumn(0);
				 * lookupcurrent_cust.setLookupSQL(_AssetMonitoring.getCustodian());
				 * pnlapprovalnorth1.add(lookupcurrent_cust, BorderLayout.WEST);
				 * lookupcurrent_cust.setPreferredSize(new Dimension(100, 0));
				 * lookupcurrent_cust.addLookupListener(new LookupListener() { public void
				 * lookupPerformed(LookupEvent event) { Object[] data = ((_JLookup)
				 * event.getSource()).getDataSet(); if(data!=null){ String emp_name = (String)
				 * data[1]; txtpnlapprovalnorth.setText(emp_name); } } }); } {
				 * txtpnlapprovalnorth=new JTextField();
				 * pnlapprovalnorth1.add(txtpnlapprovalnorth,BorderLayout.CENTER);
				 * txtpnlapprovalnorth.setPreferredSize(new Dimension(150, 0));
				 * //txtpnlapprovalnorth.setBorder(_EMPTY_BORDER);
				 * 
				 * } { txtpnlapprovalnorthxtra=new JTextField();
				 * pnlapprovalnorth1.add(txtpnlapprovalnorthxtra,BorderLayout.EAST);
				 * txtpnlapprovalnorthxtra.setPreferredSize(new Dimension(250, 0));
				 * txtpnlapprovalnorthxtra.setBorder(_EMPTY_BORDER);
				 * 
				 * }
				 * 
				 * }
				 * 
				 * 
				 * } { pnlforapprovalcenter= new JPanel(new BorderLayout());
				 * pnlapproval.add(pnlforapprovalcenter, BorderLayout.CENTER);
				 * pnlforapprovalcenter.setPreferredSize(new Dimension(0, 200));
				 * pnlforapprovalcenter.setBorder(JTBorderFactory.
				 * createTitleBorder("Asset transfer for approval"));
				 * 
				 * { scrollapproval = new JScrollPane();
				 * pnlforapprovalcenter.add(scrollapproval); { SimpleDateFormat sdf = new
				 * SimpleDateFormat("MM-dd-yyyy"); modelapproval= new
				 * tablemodel.modelapproval(); tblapproval = new _JTableMain(modelapproval);
				 * scrollapproval.setViewportView(tblapproval);
				 * //tblAssets.getSelectionModel().addListSelectionListener(new
				 * ListSelectionListener() {
				 * tblapproval.getSelectionModel().addListSelectionListener(new
				 * ListSelectionListener() { public void valueChanged(ListSelectionEvent e) {
				 * 
				 * if(!e.getValueIsAdjusting()){ //btn2.setEnabled(true);
				 * 
				 * } } }); tblapproval.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				 * tblapproval.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
				 * tblapproval.getColumnModel().getColumn(0).setPreferredWidth(125);//chkbox
				 * tblapproval.getColumnModel().getColumn(1).setPreferredWidth(125);//asset no.
				 * tblapproval.getColumnModel().getColumn(2).setPreferredWidth(125);//move_no
				 * tblapproval.getColumnModel().getColumn(3).setPreferredWidth(125);//old asset
				 * tblapproval.getColumnModel().getColumn(4).setPreferredWidth(135);//prev_cust
				 * tblapproval.getColumnModel().getColumn(5).setPreferredWidth(135);//
				 * current_cust
				 * tblapproval.getColumnModel().getColumn(6).setPreferredWidth(110);//trans_date
				 * tblapproval.getColumnModel().getColumn(7).setPreferredWidth(95);//reason
				 * tblapproval.getColumnModel().getColumn(8).setPreferredWidth(150);//remarks
				 * tblapproval.getColumnModel().getColumn(9).setPreferredWidth(100);//dept_head
				 * tblapproval.getColumnModel().getColumn(10).setPreferredWidth(100);//
				 * date_approved
				 * tblapproval.getColumnModel().getColumn(11).setPreferredWidth(100);//HAD
				 * tblapproval.getColumnModel().getColumn(12).setPreferredWidth(100);//
				 * date_approved by HAD tblapproval.setFont(new Font("DejaVu Sans", 0, 12));
				 * 
				 * tblapproval.getColumnModel().getColumn(6).setCellRenderer(new
				 * DateRenderer(sdf));
				 * tblapproval.getColumnModel().getColumn(10).setCellRenderer(new
				 * DateRenderer(sdf));
				 * tblapproval.getColumnModel().getColumn(12).setCellRenderer(new
				 * DateRenderer(sdf));
				 * //tblapproval.getColumnModel().getColumn(0).setCellRenderer(
				 * rendererCenterAlign);
				 * //tblapproval.getColumnModel().getColumn(1).setCellRenderer(
				 * rendererCenterAlign);
				 * //tblapproval.getColumnModel().getColumn(4).setCellRenderer(
				 * rendererCenterAlign); } { rowheaderapproval = tblapproval.getRowHeader();
				 * rowheaderapproval.setModel(new DefaultListModel());
				 * scrollapproval.setRowHeaderView(rowheaderapproval);
				 * scrollapproval.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
				 * FncTables.getRowHeader_Header()); } } } { pnlapprovalbuttons= new JPanel(new
				 * BorderLayout(5,5)); pnlapproval.add(pnlapprovalbuttons,BorderLayout.SOUTH); {
				 * pnlapprovalbuttons1= new JPanel(new GridLayout(1, 2));
				 * pnlapprovalbuttons.add(pnlapprovalbuttons1,BorderLayout.SOUTH);
				 * pnlapprovalbuttons1.setPreferredSize(new Dimension(0, 30)); { btn2 = new
				 * JButton("Dept head approval"); pnlapprovalbuttons1.add(btn2,
				 * BorderLayout.CENTER); btn2.setEnabled(false); btn2.addActionListener(new
				 * ActionListener() { private String depthead_id; //String
				 * chkdeptdead=_AssetMonitoring.chckdepthead();
				 * 
				 * public void actionPerformed(ActionEvent e) { Integer chkdept = (Integer)
				 * modelapproval.getValueAt(tblapproval.getSelectedRow(), 9); depthead_id=
				 * UserInfo.EmployeeCode; if(chkdept!=null) {
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Asset is already for approval by HAD");
				 * 
				 * }else { if(JOptionPane.showConfirmDialog(getTopLevelAncestor(),
				 * "Do you want to approve the transfer?", "Approve transfer",
				 * JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
				 * _AssetMonitoring.approvedbydepthead(modelapproval, depthead_id);
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Asset has been tagged for approval by HAD."); } }
				 * FncTables.clearTable(modelapproval);
				 * _AssetMonitoring.displayforapprovalbydepthead(modelapproval,
				 * rowheaderapproval, lookupCustodianid.getValue(), UserInfo.Department); } });
				 * } { btn3 = new JButton("HAD approval"); pnlapprovalbuttons1.add(btn3,
				 * BorderLayout.SOUTH); btn3.setEnabled(false); btn3.addActionListener(new
				 * ActionListener() { public void actionPerformed(ActionEvent e) { Integer
				 * chkdept = (Integer) modelapproval.getValueAt(tblapproval.getSelectedRow(),
				 * 9); Integer chkhad = (Integer)
				 * modelapproval.getValueAt(tblapproval.getSelectedRow(), 9); Integer prevcust=
				 * (Integer) modelapproval.getValueAt(tblapproval.getSelectedRow(), 4); Integer
				 * asset_no= (Integer) modelapproval.getValueAt(tblapproval.getSelectedRow(),
				 * 1);
				 * 
				 * if (chkdept==null) { JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Please ask you dept head to approve the transfer."); }else {
				 * if(JOptionPane.showConfirmDialog(getTopLevelAncestor(),
				 * "Do you want to approved the transfer?", "Approved transfer",
				 * JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				 * _AssetMonitoring.approvedbyhad(modelapproval,UserInfo.EmployeeCode,prevcust,
				 * asset_no); JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Assets already transfered."); }
				 * _AssetMonitoring.displayforapprovalbyhad(modelapproval, rowheaderapproval,
				 * lookupCustodianid.getText()); } } });
				 * 
				 * } }
				 * 
				 * }
				 * 
				 * }
				 */
				/*
				 * { pnlRepairs = new JPanel(new BorderLayout(3,0));
				 * tabAssets.addTab("  Repairs  ", null, pnlRepairs, null);
				 * //pnlRepairs.setPreferredSize(new Dimension(300, 20));
				 * //pnlRepairs.setBackground(Color.DARK_GRAY);;
				 * //pnlRepairs.setBorder(lineBorder);
				 * pnlRepairs.setBorder(JTBorderFactory.createTitleBorder("Repair Details")); {
				 * JPanel panel1 = new JPanel(new BorderLayout(1,0)); pnlRepairs.add(panel1,
				 * BorderLayout.NORTH); panel1.setPreferredSize(new Dimension(100,40));
				 * //panel1.setBackground(Color.lightGray); { JLabel TxtRepair = new
				 * JLabel("Repair Details"); panel1.add(TxtRepair, BorderLayout.NORTH); } } {
				 * JPanel panel2 = new JPanel(new BorderLayout()); pnlRepairs.add(panel2,
				 * BorderLayout.CENTER); { scrollRepairs = new JScrollPane();
				 * pnlRepairs.add(scrollRepairs); { //Object[] colNames = {"Repair Date",
				 * "Transacted by", "Old ULM", "Repair Cost", "Old Book Value",
				 * "Old Monthly Dep.", "Old Cost", "Details"}; //Object[][] data = null;
				 * modelRepair=new modelRepair(); tblRepairs=new _JTableMain(modelRepair);
				 * scrollRepairs.setViewportView(tblRepairs);
				 * scrollRepairs.setViewportView(tblRepairs);tblRepairs.getTableHeader().
				 * setReorderingAllowed(false); modelRepairs = new DefaultTableModel(data,
				 * colNames){ private static final long serialVersionUID = 8133076994859587789L;
				 * 
				 * public boolean isCellEditable(int rowIndex, int columnIndex) { return false;
				 * } }; tblRepairs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				 * tblRepairs.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
				 * tblRepairs.getColumnModel().getColumn(0).setPreferredWidth(100);//date_trans
				 * tblRepairs.getColumnModel().getColumn(1).setPreferredWidth(150);//trans_by
				 * tblRepairs.getColumnModel().getColumn(2).setPreferredWidth(60);//old_ulm
				 * tblRepairs.getColumnModel().getColumn(3).setPreferredWidth(100);//repair_cost
				 * tblRepairs.getColumnModel().getColumn(4).setPreferredWidth(100);//old_bk_val
				 * tblRepairs.getColumnModel().getColumn(5).setPreferredWidth(100);//old_mon_dep
				 * tblRepairs.getColumnModel().getColumn(6).setPreferredWidth(100);//old_cost
				 * tblRepairs.getColumnModel().getColumn(7).setPreferredWidth(150);//details
				 * tblRepairs.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
				 * 
				 * 
				 * } {
				 * 
				 * } } } { JPanel panel3 = new JPanel(new BorderLayout(1,3));
				 * pnlRepairs.add(panel3, BorderLayout.SOUTH); panel3.setPreferredSize(new
				 * Dimension(200,30)); //panel3.setBackground(Color.lightGray); { pnlButton1=
				 * new JPanel(new GridLayout(1, 3, 5, 5)); panel3.add(pnlButton1,
				 * BorderLayout.EAST); pnlRepairs.setPreferredSize(new Dimension(0, 35));
				 * 
				 * { btnDispose = new JButton("Dispose"); pnlButton1.add(btnDispose);
				 * btnDispose.setEnabled(false); btnDispose.addActionListener(new
				 * ActionListener() { public void actionPerformed(ActionEvent e) {
				 * 
				 * if(hasCheckedAssets()){ Object[] options = { "OK", "CANCEL" }; int
				 * todispose=JOptionPane.showOptionDialog(getTopLevelAncestor(),
				 * "Click OK to continue", "Dispose Asset?", JOptionPane.DEFAULT_OPTION,
				 * JOptionPane.INFORMATION_MESSAGE, null, options, options);
				 * 
				 * if(todispose==JOptionPane.YES_OPTION){
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Asset has been disposed.",
				 * btnDispose.getText(),JOptionPane.INFORMATION_MESSAGE);
				 * _AssetMonitoring.disposeAsset(tblAssets);
				 * //_AssetMonitoring.retireAsset(tblAsset);
				 * panelAssetInformation.resetInformation(); } }else{
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Please select asset to Dispose.",
				 * btnDispose.getText(),JOptionPane.INFORMATION_MESSAGE); } } }); } { btnRetire
				 * = new JButton("Retire"); pnlButton1.add(btnRetire);
				 * btnRetire.setEnabled(false); btnRetire.addActionListener(new ActionListener()
				 * { public void actionPerformed(ActionEvent e) {
				 * 
				 * if(hasCheckedAssets()){ Object[] options={"OK","CANCEL"}; int
				 * toRetire=JOptionPane.showOptionDialog(getTopLevelAncestor(),
				 * "Click OK to continue.", "Retire Asset?", JOptionPane.DEFAULT_OPTION,
				 * JOptionPane.WARNING_MESSAGE, null, options, options);
				 * 
				 * if(toRetire==JOptionPane.YES_OPTION){
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Asset has been retired.", "Retire", JOptionPane.INFORMATION_MESSAGE);
				 * _AssetMonitoring.retireAsset(tblAssets);
				 * panelAssetInformation.resetInformation(); } }else{
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Please select asset to retire.", btnRetire.getText(),
				 * JOptionPane.WARNING_MESSAGE);
				 * 
				 * } } }); } { btnPullout = new JButton("Pull-out"); pnlButton1.add(btnPullout);
				 * btnPullout.setEnabled(false); btnPullout.addActionListener(new
				 * ActionListener() { public void actionPerformed(ActionEvent e) {
				 * 
				 * if(hasCheckedAssets()){ Object[] options={"OK","CANCEL"}; int
				 * toPulloutAsset=JOptionPane.showOptionDialog(getTopLevelAncestor(),
				 * "Click OK to continue", "Pullout asset for Repair?",
				 * JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
				 * options);
				 * 
				 * if(toPulloutAsset==JOptionPane.YES_OPTION){
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Asset has been pullout for repair.", "Pull-out",
				 * JOptionPane.INFORMATION_MESSAGE); int row = tblAssets.getSelectedRow();
				 * String asset_no=modelAssets.getValueAt(row, 1).toString();
				 * System.out.println(row); System.out.println();
				 * panelAssetInformation.pulloutAsset(asset_no);
				 * 
				 * } }else{ JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Please select asset to pull-out.", btnPullout.getText(),
				 * JOptionPane.WARNING_MESSAGE); } } });
				 * 
				 * } } } }
				 */
				/*
				 * { pnlReplacement = new JPanel(new BorderLayout(4, 4));
				 * tabAssets.addTab("Replacement", null, pnlReplacement, null);
				 * pnlReplacement.setBorder(new EmptyBorder(5, 5, 5, 5));
				 * pnlReplacement.setBorder(JTBorderFactory.createTitleBorder("")); { JPanel
				 * pnlReplacement1= new JPanel(new GridLayout(4,1,5,5));
				 * pnlReplacement.add(pnlReplacement1, BorderLayout.NORTH);
				 * //pnlReplacement1.setBackground(Color.BLACK);
				 * pnlReplacement1.setPreferredSize(new Dimension(500, 110)); { { JPanel pnl1=
				 * new JPanel(new BorderLayout(5,5)); pnlReplacement1.add(pnl1); { { JLabel
				 * lblReplacement= new JLabel("Replacement No."); pnl1.add(lblReplacement,
				 * BorderLayout.WEST); lblReplacement.setPreferredSize(new Dimension(115,0)); }
				 * { jtxtReplacement= new JTextField(); pnl1.add(jtxtReplacement,
				 * BorderLayout.CENTER); jtxtReplacement.setEditable(false); } { JPanel
				 * pnlxtraReplacement= new JPanel(); pnl1.add(pnlxtraReplacement,
				 * BorderLayout.EAST); pnlxtraReplacement.setPreferredSize(new Dimension(650,
				 * 0)); } } } { JPanel pnl2= new JPanel(new BorderLayout(5,5));
				 * pnlReplacement1.add(pnl2); { { JLabel lblReplacement= new
				 * JLabel("Requested by"); pnl2.add(lblReplacement, BorderLayout.WEST);
				 * lblReplacement.setPreferredSize(new Dimension(115,0)); } { lookupRequestedby=
				 * new _JLookup(); pnl2.add(lookupRequestedby, BorderLayout.CENTER);
				 * lookupRequestedby.setLookupSQL(_AssetMonitoring.getCustodian2());
				 * lookupRequestedby.addLookupListener(new LookupListener() {
				 * 
				 * @Override public void lookupPerformed(LookupEvent event) { Object[] Reqby =
				 * lookupRequestedby.getDataSet(); FncSystem.out("Display SQL for Client",
				 * lookupRequestedby.getLookupSQL()); if (Reqby !=null){
				 * 
				 * String emp_code =(String) Reqby[0]; String emp_name =(String) Reqby[1];
				 * lookupRequestedby.setValue(emp_code); jtRequestedby.setText(emp_name);
				 * 
				 * txtdepartment1.setText(Reqby[2].toString());
				 * txtdepartment2.setText(Reqby[3].toString());
				 * txtdivision1.setText(Reqby[4].toString());
				 * txtdivision2.setText(Reqby[5].toString());
				 * 
				 * }
				 * 
				 * } }); } { jtRequestedby= new JTextField(); pnl2.add(jtRequestedby,
				 * BorderLayout.EAST); jtRequestedby.setPreferredSize(new Dimension(650, 0));
				 * jtRequestedby.setEditable(false); } } }
				 * 
				 * { JPanel pnl3= new JPanel(new BorderLayout(5,5)); pnlReplacement1.add(pnl3);
				 * { { lblDepartment=new JLabel("Department");
				 * pnl3.add(lblDepartment,BorderLayout.WEST); lblDepartment.setPreferredSize(new
				 * Dimension(115,0)); } { txtdepartment1= new JTextField();
				 * pnl3.add(txtdepartment1, BorderLayout.CENTER);
				 * txtdepartment1.setEditable(false); } { txtdepartment2= new JTextField();
				 * pnl3.add(txtdepartment2, BorderLayout.EAST);
				 * txtdepartment2.setPreferredSize(new Dimension(650, 0));
				 * txtdepartment2.setEditable(false); } } } { JPanel pnl4= new JPanel(new
				 * BorderLayout(5,5)); pnlReplacement1.add(pnl4); { lblDivision=new
				 * JLabel("Division"); pnl4.add(lblDivision,BorderLayout.WEST);
				 * lblDivision.setPreferredSize(new Dimension(115,0)); } { txtdivision1= new
				 * JTextField(); pnl4.add(txtdivision1, BorderLayout.CENTER);
				 * txtdivision1.setEditable(false); } { txtdivision2= new JTextField();
				 * pnl4.add(txtdivision2, BorderLayout.EAST); txtdivision2.setPreferredSize(new
				 * Dimension(650, 0)); txtdivision2.setEditable(false); } } } } { JPanel
				 * pnlReplacement2= new JPanel(new BorderLayout(5, 5));
				 * pnlReplacement.add(pnlReplacement2, BorderLayout.CENTER);
				 * pnlReplacement2.setBorder(JTBorderFactory.createTitleBorder("Reason")); {
				 * JPanel pnlReason= new JPanel(new BorderLayout());
				 * pnlReplacement2.add(pnlReason, BorderLayout.NORTH); { JLabel lblreason= new
				 * JLabel("Reason"); pnlReason.add(lblreason, BorderLayout.NORTH); } } { JPanel
				 * pnlCenterreason= new JPanel(new BorderLayout());
				 * pnlReplacement2.add(pnlCenterreason, BorderLayout.CENTER); { jtAreason= new
				 * JTextArea(""); pnlReplacement2.add(jtAreason, BorderLayout.CENTER);
				 * jtAreason.setPreferredSize(new Dimension(100, 50));
				 * //jtAreason.setBackground(Color.cyan); } } } { JPanel pnlReplacement3= new
				 * JPanel(new BorderLayout(5, 5)); pnlReplacement.add(pnlReplacement3,
				 * BorderLayout.SOUTH); pnlReplacement3.setPreferredSize(new Dimension(0, 30));
				 * //pnlReplacement3.setBackground(Color.BLUE); { JPanel pnlsouth= new
				 * JPanel(new GridLayout(1, 2, 3, 3)); pnlReplacement3.add(pnlsouth,
				 * BorderLayout.EAST); { btnRepsave= new JButton("Save");
				 * pnlsouth.add(btnRepsave); btnRepsave.setPreferredSize(new Dimension(100, 0));
				 * btnRepsave.addActionListener(new ActionListener() {
				 * 
				 * @Override public void actionPerformed(ActionEvent e) {
				 * 
				 * ArrayList<String> asset_no = new ArrayList<String>(); ArrayList<String>
				 * asset_code = new ArrayList<String>(); ArrayList<Boolean> toSave = new
				 * ArrayList<Boolean>();
				 * 
				 * int count = 0; String asset_nos = null;
				 * 
				 * for(int x=0; x < tblAssets.getRowCount(); x++){ if(tblAssets.getValueAt(x,
				 * 0).equals(true)){ asset_no.add(tblAssets.getValueAt(x, 1).toString());
				 * asset_code.add(tblAssets.getValueAt(x, 2).toString()); toSave.add(true);
				 * 
				 * if(count==0){ asset_nos = tblAssets.getValueAt(x, 1).toString(); }else{
				 * asset_nos = asset_nos + ","+tblAssets.getValueAt(x, 1).toString(); } count++;
				 * } }
				 * 
				 * if(toSave.contains(true)){ if(checkReplacement()){ int
				 * tosaverep=JOptionPane.showConfirmDialog(getTopLevelAncestor(),
				 * "Are all entries correct?","Save",JOptionPane.YES_NO_OPTION);
				 * if(tosaverep==JOptionPane.YES_OPTION){
				 * _AssetMonitoring.saveAsserForReplacement(jtxtReplacement.getText(),
				 * lookupRequestedby.getValue(), jtAreason.getText(), asset_no, asset_code,
				 * asset_nos, jtRequestedby.getText());
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Asset has been tagged for replacement.", "Asset Replacement",
				 * JOptionPane.INFORMATION_MESSAGE); resetReplacement(); } }else{
				 * JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Please fill-up required fields.", "Save", JOptionPane.WARNING_MESSAGE); }
				 * }else{ JOptionPane.showMessageDialog(getTopLevelAncestor(),
				 * "Please select asset for replacement.", "Save", JOptionPane.WARNING_MESSAGE);
				 * } } }); } { JButton btnreset= new JButton("Reset"); pnlsouth.add(btnreset);
				 * btnreset.setPreferredSize(new Dimension(100, 0));
				 * btnreset.addActionListener(new ActionListener() { public void
				 * actionPerformed(ActionEvent e) { resetReplacement(); } }); } } } }
				 */
				/*
				 * { pnlRefurbish= new panelRefurbish(); tabAssets.addTab(" Refurbish ", null,
				 * pnlRefurbish, null); pnlRefurbish.setBorder(new EmptyBorder(5, 5, 5, 5));
				 * pnlRefurbish.setBorder(JTBorderFactory.createTitleBorder("")); }
				 */
				/*
				 * { pnlRefurbish = new JPanel(new BorderLayout(4, 4));
				 * tabAssets.addTab("Refurbish", null, pnlRefurbish, null);
				 * pnlRefurbish.setBorder(new EmptyBorder(5, 5, 5, 5));
				 * pnlRefurbish.setBorder(JTBorderFactory.createTitleBorder("")); {
				 * pnlRefurbishnorth= new JPanel(new GridLayout(3,1,3,3));
				 * pnlRefurbish.add(pnlRefurbishnorth, BorderLayout.NORTH);
				 * pnlRefurbishnorth.setPreferredSize(new Dimension(0, 70)); {
				 * pnlRefurbishnorth1 = new JPanel(new BorderLayout(3,3));
				 * pnlRefurbishnorth.add(pnlRefurbishnorth1); { lblrecon= new
				 * JLabel("Recon no."); pnlRefurbishnorth1.add(lblrecon,BorderLayout.WEST);
				 * lblrecon.setPreferredSize(new Dimension(80,0)); } { txtrecon1 = new
				 * JTextField(); pnlRefurbishnorth1.add(txtrecon1, BorderLayout.CENTER); } {
				 * txtrecon2 = new JTextField();
				 * pnlRefurbishnorth1.add(txtrecon2,BorderLayout.EAST);
				 * txtrecon2.setPreferredSize(new Dimension(700, 0));
				 * txtrecon2.setBorder(_EMPTY_BORDER); }
				 * 
				 * } { pnlRefurbishnorth2 = new JPanel(new BorderLayout(3,3));
				 * pnlRefurbishnorth.add(pnlRefurbishnorth2); { lblassetnorfrbsh= new
				 * JLabel("Asset no.");
				 * pnlRefurbishnorth2.add(lblassetnorfrbsh,BorderLayout.WEST);
				 * lblassetnorfrbsh.setPreferredSize(new Dimension(80, 0)); } {
				 * txtassetnorfrbsh1= new JTextField();
				 * pnlRefurbishnorth2.add(txtassetnorfrbsh1,BorderLayout.CENTER); } {
				 * txtassetnorfrbsh1= new JTextField();
				 * pnlRefurbishnorth2.add(txtassetnorfrbsh1,BorderLayout.EAST);
				 * txtassetnorfrbsh1.setPreferredSize(new Dimension(700, 0));
				 * txtassetnorfrbsh1.setBorder(_EMPTY_BORDER); } } { pnlRefurbishnorth3= new
				 * JPanel(new BorderLayout(3,3)); pnlRefurbishnorth.add(pnlRefurbishnorth3); {
				 * lblcompanyrfrbsh= new JLabel("Company");
				 * pnlRefurbishnorth3.add(lblcompanyrfrbsh, BorderLayout.WEST);
				 * lblcompanyrfrbsh.setPreferredSize(new Dimension(80, 0)); } {
				 * pnlRefurbishnorth3_1=new JPanel(new BorderLayout(3,3));
				 * pnlRefurbishnorth3.add(pnlRefurbishnorth3_1,BorderLayout.CENTER); {
				 * lookupcompanyrfrbsh= new _JLookup();
				 * pnlRefurbishnorth3_1.add(lookupcompanyrfrbsh,BorderLayout.WEST);
				 * lookupcompanyrfrbsh.setPreferredSize(new Dimension(100, 0)); } {
				 * txtcompanyrfrbsh1=new JTextField();
				 * pnlRefurbishnorth3_1.add(txtcompanyrfrbsh1,BorderLayout.CENTER); } {
				 * txtcompanyrfrbsh2= new JTextField();
				 * pnlRefurbishnorth3_1.add(txtcompanyrfrbsh2,BorderLayout.EAST);
				 * txtcompanyrfrbsh2.setPreferredSize(new Dimension(400, 0));
				 * txtcompanyrfrbsh2.setBorder(_EMPTY_BORDER);
				 * 
				 * } }
				 * 
				 * }
				 * 
				 * 
				 * } { pnlRefurbishcenter = new JPanel(new BorderLayout(5,5));
				 * pnlRefurbish.add(pnlRefurbishcenter, BorderLayout.CENTER); } {
				 * pnlRefurbishsouth = new JPanel(new BorderLayout(5,5));
				 * pnlRefurbish.add(pnlRefurbishsouth, BorderLayout.SOUTH);
				 * pnlRefurbishsouth.setPreferredSize(new Dimension(0, 50)); }
				 * 
				 * }
				 */

				tabAssets.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						int selectedTab = ((JTabbedPane) e.getSource()).getSelectedIndex();
						if (selectedTab == 0) {
							modelAssets.setEditable(true);
							panelAssetInformation.btnState(true, true, false, true, false);
							clearcheckbox();

						}
						if (selectedTab == 1) {
							txtmovementno.setText(_AssetMonitoring.getMoveNo());
							lookupnewCustodian.setLookupSQL(_AssetMonitoring.getCustodian());
							// lookupnewCustodian.setLookupSQL(_AssetMonitoring.getCustodian(co_id));
							// displayAllAssets(false, false);
							tblAssets.setEnabled(true);
							modelAssets.setEditable(true);
							btn1.setEnabled(false);
							btnDispose.setEnabled(false);
							btnRetire.setEnabled(false);
							btnPullout.setEnabled(false);
							clearcheckbox();
							lookupnewCustodian.setValue("");
							txtnewCustodian.setText("");
							jtxtRemarks.setText("");
						}

						/*
						 * if (selectedTab==2) {
						 * 
						 * if (UserInfo.EmployeeCode.equals("900430")&& _AssetMonitoring.chckdepthead())
						 * { System.out.println("Code Block 1"); btn2.setEnabled(true);
						 * btn3.setEnabled(true); tblapproval.setEditable(true);
						 * modelapproval.setEditable(true);
						 * _AssetMonitoring.displayforapprovalbyhad(modelapproval, rowheaderapproval,
						 * lookupCustodianid.getValue());
						 * 
						 * } else if (_AssetMonitoring.chckdepthead()) {
						 * System.out.println("Code Block 2");
						 * 
						 * btn2.setEnabled(true); btn3.setEnabled(false); tblapproval.setEditable(true);
						 * modelapproval.setEditable(true);
						 * _AssetMonitoring.displayforapprovalbydepthead(modelapproval,
						 * rowheaderapproval, lookupCustodianid.getValue(), UserInfo.Department);
						 * 
						 * } else { System.out.println("Code Block 2");
						 * 
						 * tblapproval.setEditable(false); modelapproval.setEditable(false);
						 * btn2.setEnabled(false); btn3.setEnabled(false); }
						 * 
						 * tblapproval.clearSelection(); }
						 */

						if // (selectedTab == 3){
						(selectedTab == 2) {
							// jtxtReplacement.setText(_AssetMonitoring.getNewReplacementNo());
							tblAssets.setEnabled(true);
							modelAssets.setEditable(true);
							// btnRepsave.setEnabled(false);
							btnDispose.setEnabled(false);
							btnRetire.setEnabled(false);
							btnPullout.setEnabled(false);
							clearcheckbox();
						}

					}
				});
			}
		}
	}

	protected void clearcheckbox() {

		for (int x = 0; x < tblAssets.getRowCount(); x++) {
			tblAssets.setValueAt(false, x, 0);
		}

	}

	protected void clearmodelaprvlchkbox() {

		for (int x = 0; x < tblapproval.getRowCount(); x++) {
			tblapproval.setValueAt(false, x, 0);
		}
	}

	protected void resetReplacement() {
		clearcheckbox();
		jtxtReplacement.setText(_AssetMonitoring.getNewReplacementNo());
		lookupRequestedby.setText("");
		jtRequestedby.setText("");
		// lblRequestedby.setFont(italicFont);
		// lblRequestedby.setForeground(redFont);

		// tagRequestedby.setText("");
		txtdepartment1.setText("");
		txtdepartment2.setText("");
		txtdivision1.setText("");
		txtdivision2.setText("");

		jtAreason.setText("");
		// lblRepReason.setFont(italicFont);
		// lblRepReason.setForeground(redFont);
	}

	protected Boolean checkReplacement() {
		if (lookupRequestedby.getText().equals("") || jtAreason.getText().trim().equals(""))
			return false;
		else
			return true;
	}

	/*
	 * public static Boolean toTransfer(){ Integer tf_no = 0;
	 * 
	 * try { tf_no = Integer.parseInt(jtxttransfer.getText()); } catch
	 * (NumberFormatException e) { tf_no = 0; }
	 * 
	 * if(lookupnewCustodian.getValue()==null ||
	 * jtxtRemarks.getText().trim().equals("") || tf_no == 0) return false; else
	 * return true; }
	 */
	protected Boolean hasCheckedAssets() {
		ArrayList<Boolean> checkTable = new ArrayList<Boolean>();
		for (int x = 0; x < tblAssets.getRowCount(); x++) {
			if (tblAssets.getValueAt(x, 0).equals(true))
				checkTable.add(true);
		}
		return checkTable.contains(true);

	}

	public void loadIndividualAsset(final String emp_id, final Boolean fromDelete) {
		new Thread(new Runnable() {
			public void run() {
				glass.start("Loading assets of " + txtCustodian.getText().trim() + ". Please wait.");

				rowSM.removeListSelectionListener(rowLSL);

				_AssetMonitoring.displayIndividualAssets(modelAssets, rowheaderAssets, emp_id);
				if (tblAsset.getRowCount() > 0) {
					tblAsset.setRowSelectionInterval(0, 0);

					rowSM.addListSelectionListener(rowLSL);

					// displayAssetDetailswithPicture(tblAsset.getValueAt(0, 1).toString(),
					// fromDelete);
					// displayAssetMovement(tblAsset.getValueAt(0, 1).toString());
					displayAssetRepairs(tblAsset.getValueAt(0, 1).toString(), fromDelete);
				}

				glass.stop();
			}
		}).start();
	}

	protected void btnRetireState(Boolean sTransfer, Boolean sDispose, Boolean sRetire, Boolean sPullout) {
		btn1.setEnabled(sTransfer);
		btnDispose.setEnabled(sDispose);
		btnRetire.setEnabled(sRetire);
		btnPullout.setEnabled(sPullout);
	}

	protected class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
		private static final long serialVersionUID = -7913993723934191067L;
		Boolean isDisabled;

		CheckBoxRenderer(Boolean isDisabled) {
			setHorizontalAlignment(JLabel.CENTER);
			this.isDisabled = isDisabled;
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}
			setSelected((value != null && ((Boolean) value).booleanValue()));
			setEnabled(isDisabled);
			return this;
		}
	}

	protected void displayAssetRepairs(String asset_no, Boolean fromDelete) {
		FncTables.clearTable(modelRepair);
		_AssetMonitoring.displayRepairHistory(asset_no, modelRepair);
		// if(UserInfo.Department.equals("01")
		if (UserInfo.Department.equals("01") || UserInfo.Department.equals("82") || UserInfo.Department.equals("97")
				|| UserInfo.Department.equals("98")) {
			if (panelAssetInformation.txtStatus.equals("MISSING")
					|| panelAssetInformation.txtStatus.equals("INACTIVE")) {
				if (jtxtRemarks.getText().equals("UNDER REPAIR")) {
					btnRetireState(true, true, true, false);
				} else if (jtxtRemarks.getText().equals("RETIRED")) {
					btnRetireState(false, true, false, false);
				} else if (jtxtRemarks.getText().equals("DISPOSED")) {
					btnRetireState(false, false, false, false);
				} else {
					btnRetireState(false, false, false, false);
				}
			} else {
				if (jtxtRemarks.getText().equals("UNDER REPAIR")) {
					btnRetireState(true, true, true, false);
				} else if (jtxtRemarks.getText().equals("RETIRED")) {
					btnRetireState(false, true, false, false);
				} else if (jtxtRemarks.getText().equals("DISPOSED")) {
					btnRetireState(false, false, false, false);
				} else {
					btnRetireState(true, true, true, true);
				}
			}
		} else {
			btnRetireState(false, false, false, false);
		}

		if (fromDelete)
			btnRetireState(true, false, false, false);
	}

	public void loadAllAsset() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				FncGlobal.startProgress("Please wait while loading assets.");
				modelAssets.isCellEditable(0, 0);
				displayAllAssets(false, false);
				FncGlobal.stopProgress();
			}
		}).start();

		// FncGlobal.startProgress("Please wait while loading assets.");
		/*
		 * new Thread(new Runnable() {
		 * 
		 * public void run() {
		 * FncGlobal.startProgress("Please wait while loading assets.");
		 * displayAllAssets(false, false); FncGlobal.stopProgress();
		 * 
		 * }
		 * 
		 * });
		 */

		// SwingUtilities.invokeLater(run);

		/*
		 * loadAllAssets = new Thread(new Runnable() { public void run() {
		 * displayAllAssets(false, false); if(chkinactiveemp.isSelected()==true){ if
		 * (chkinactiveassets.isSelected()==true) {displayAllAssets(true, true);} else
		 * {displayAllAssets(true, false);} } else{ if
		 * (chkinactiveassets.isSelected()==true) {displayAllAssets(false, true);} else
		 * {displayAllAssets(false, false);} }
		 * 
		 * if(tblAsset.getRowCount()>0){ tblAsset.setRowSelectionInterval(0, 0);
		 * rowSM.addListSelectionListener(rowLSL);
		 * //displayAssetDetailswithPicture(tblAsset.getValueAt(0, 1).toString(),
		 * false); displayAssetRepairs(tblAsset.getValueAt(0, 1).toString(), false);
		 * //displayAssetMovement(tblAsset.getValueAt(0, 1).toString()); } } });
		 */
		// loadAllAssets.setName("loadAllAssets");
		// loadAllAssets.start();
	}

	protected void resetMovement() {
		txtmovementno.setText(_AssetMonitoring.getMoveNo());
		lookupnewCustodian.setText("");
		lblNewcustodian.setFont(null);
		// lblNewcustodian.setForeground(Color.RED);

		txtnewCustodian.setText("");

		jtxtRemarks.setText("");
		lblRemarks.setFont(null);
		// lblRemarks.setForeground(Color.RED);

		// jtxttransfer.setText("");
		// lblTransferform.setForeground(Color.RED);
	}

	public static void displayAllAssets(Boolean emp, Boolean assts) {
		modelAssets.clear();

		DefaultListModel listModel = new DefaultListModel();// Creating listModel for rowHeader.
		rowheaderAssets.setModel(listModel);// Setting of listModel into rowHeader.
		System.out.println(lookupselectcompany.getValue());
		String strSQL = " select * from view_allassetv2('" + lookupselectcompany.getValue() + "'," + assts + "," + emp
				+ ")";
		/*
		 * String strSQL = "select false, to_char(a.asset_no,'FM00000000'), \n" +
		 * "a.asset_code, \n" + "a.asset_name,\n" + "a.date_Acquired,\n" +
		 * "lpad(a.current_cust::text, 6, '0'::text),\n" +
		 * "get_employee_name(lpad(a.current_cust::text, 6, '0'::text)), \n" +
		 * "a.reference_no, \n" + "format('%s',left(a.status,1)) as status \n"+
		 * "from tbl_asset a \n" +
		 * "where get_employee_name(lpad(a.current_cust::text, 6, '0'::text)) != '' \n";
		 * if (assts==true){ strSQL = strSQL +
		 * "AND a.status in ('Active', 'A','I','INACTIVE')  \n"; } else {strSQL = strSQL
		 * + "AND a.status in ('Active', 'A')  \n"; } if (emp==true){ strSQL = strSQL +
		 * "AND lpad(a.current_cust::text, 6, '0'::text) in (select emp_code from em_employee)\n"
		 * ; } else {strSQL = strSQL +
		 * "AND lpad(a.current_cust::text, 6, '0'::text) in (select emp_code from em_employee where emp_status not in ('I') ) \n"
		 * ;} strSQL = strSQL +
		 * "AND  lpad(a.current_cust::text, 6, '0'::text) in (select emp_code from em_employee where co_id='"
		 * +co_id+"') \n"+
		 * "order by get_employee_name(lpad(a.current_cust::text, 6, '0'::text))";
		 */
		FncSystem.out("Display All Assets", strSQL);
		// System.out.println(co_id);

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {

				// You can only use this kind of adding row in model when you're query and model
				// has the same and exact unmber of columns and column types.
				modelAssets.addRow(db.getResult()[x]);

				// For every row added in model, the table header will also add the row number.
				listModel.addElement(modelAssets.getRowCount());
			}
		}

		tblAssets.packAll();
	}

	public static void disbletableAssets(Boolean isEnabled) {
		tblAssets.setEnabled(isEnabled);
	}

	public static void resettable(Boolean fromdelete) {
		// lookupselectcompany.setValue("")fromdelete;
		modelAssets.clear();
		displayAllAssets(false, false);
	}

	public static void transferAsset(Integer move_no, String prev_cust, String current_cust, String remarks,
			String new_cust_name, String dept_code) {
		pgUpdate db = new pgUpdate();
		ArrayList<String> listAsset_no = new ArrayList<String>();
		ArrayList<Boolean> isSaved = new ArrayList<Boolean>();
		for (int x = 0; x < modelAssets.getRowCount(); x++) {

			Boolean selected = (Boolean) modelAssets.getValueAt(x, 0);

			if (selected) {
				move_no += 1;
				String asset_no = ((String) modelAssets.getValueAt(x, 1)).trim();
				// String custodian_id=modelAssets.getValueAt(x, 5).toString();
				// String custodian_id = Integer.valueOf(model.getValueAt(x, 5));
				String custodian_id = (String) modelAssets.getValueAt(x, 5).toString();
				String strSQL = null;
				if (loc_id == null) {
					strSQL = "INSERT INTO tbl_asset_history( \n" + "asset_code, " + "prev_cust, " + "current_cust, "
							+ "trans_date, " + "reason, " + "remarks, \n" + "status, " + "move_no, " + "asset_no, "
							+ "trans_by,\n" +
							// "approvedby_had,\n"+
							// "approvedby_had,\n"+ --int
							// "date_approvedby_had,\n"+
							"dept_code) \n" + "VALUES (" + "(select asset_code from tbl_asset where asset_no='"
							+ asset_no + "' ), " + // asset_code
							"'" + custodian_id + "', \n" + // prev_cust
							"'" + current_cust + "', \n" + // current_cust
							"current_date, \n" + // trans_date
							"'TRANSFER', \n" + // reason
							"'" + remarks + "', \n" + // remarks
							"'A', \n" + // status
							"(select max(move_no)+1 from tbl_asset_history), \n" + // move_no
							"'" + asset_no + "', \n" + // asset_no
							"'" + UserInfo.EmployeeCode + "',\n" +
							// "'HAD',\n"+
							// "current_date,\n"+
							"'" + dept_code + "') \n";// trans_by
				} else {
					strSQL = "INSERT INTO tbl_asset_history( \n" + "asset_code, " + "prev_cust, " + "current_cust, "
							+ "trans_date, " + "reason, " + "remarks, \n" + "status, " + "move_no, " + "asset_no, "
							+ "trans_by,\n" +
							// "approvedby_had,\n"+
							// "approvedby_had,\n"+ --int
							// "date_approvedby_had,\n"+
							"old_location,\n" + "dept_code) \n" + "VALUES ("
							+ "(select asset_code from tbl_asset where asset_no='" + asset_no + "' ), " + // asset_code
							"'" + custodian_id + "', \n" + // prev_cust
							"'" + current_cust + "', \n" + // current_cust
							"current_date, \n" + // trans_date
							"'TRANSFER', \n" + // reason
							"'" + remarks + "', \n" + // remarks
							"'A', \n" + // status
							"(select max(move_no)+1 from tbl_asset_history), \n" + // move_no
							"'" + asset_no + "', \n" + // asset_no
							"'" + UserInfo.EmployeeCode + "',\n" +
							// "'HAD',\n"+
							// "current_date,\n"+
							"(select loc_id from tbl_asset where asset_no='" + asset_no + "' ),\n" + "'" + dept_code
							+ "') \n";// trans_by
				}
				System.out.println("move_no" + move_no);
				System.out.println("prev_cust" + prev_cust);
				System.out.println("current_cust" + current_cust);
				System.out.println("remarks" + remarks);
				System.out.println("new_cust_name" + new_cust_name);
				System.out.println("dept_code " + dept_code);
				System.out.println();
				db.executeUpdate(strSQL, true);
				String strSQL2 = null;
				if (loc_id == null) {
					strSQL2 = "update tbl_asset set current_cust='" + current_cust
							+ "',status='A',item_found='t'  where asset_no='" + asset_no + "'::int";
				} else {
					strSQL2 = "update tbl_asset set current_cust='" + current_cust
							+ "',status='A',item_found='t',loc_id='" + loc_id + "'  where asset_no='" + asset_no
							+ "'::int";
				}
				db.executeUpdate(strSQL2, false);
				listAsset_no.add(asset_no);
				isSaved.add(true);
				FncSystem.out("transfer asset", strSQL);
				FncSystem.out("transfer asset", strSQL2);

			}
		}
		db.commit();
	}

	public static String getassetlocation() {

		return "select loc_id,loc_name from tbl_asset_location ";
	}

}
